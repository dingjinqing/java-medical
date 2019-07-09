package com.vpu.mp.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.RecommendGoodsRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsRecommend;
import com.vpu.mp.service.pojo.shop.goods.recommend.GoodsRecommendPageListParam;
import com.vpu.mp.service.shop.goods.GoodsRecommendService;

/**
 * @author 黄荣刚
 * @date 2019年7月9日
 *
 */
@RestController
@RequestMapping("/api/admin/goods")
public class AdminGoodsRecommendController extends AdminBaseController {
	
	@PostMapping("/recommend/list")
	public JsonResult getPageList(@RequestBody GoodsRecommendPageListParam param) {
		PageResult<RecommendGoodsRecord> pageList = shop().goodsRecommend.getPageList(param);
		List<GoodsRecommend> dataList = new ArrayList();
		if(pageList.getDataList()!= null) {
			List<RecommendGoodsRecord> list = pageList.getDataList();
			for (RecommendGoodsRecord record : list) {
				GoodsRecommend goodsRecommend = GoodsRecommend.fromRecord(record);
				dataList.add(goodsRecommend);
				if(!GoodsRecommend.PARTTYPE.equals(record.getRecommendType())) {
					continue;
				}
				String recommendGoodsId = record.getRecommendGoodsId();
				if(recommendGoodsId!=null) {
					String[] split = recommendGoodsId.split(GoodsRecommend.DELIMITER);
					List<Integer> goodsIdList = Util.valueOf(split);
					List<GoodsView> goodsViewList = shop().goods.selectGoodsViewList(goodsIdList);
					goodsRecommend.setRecommendGoods(goodsViewList);
				}
			}
		}
		PageResult<GoodsRecommend> result = new PageResult<GoodsRecommend>();
		result.setDataList(dataList);
		result.setPage(pageList.getPage());
		return success(result);
	}
}
