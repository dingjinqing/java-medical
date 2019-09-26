package com.vpu.mp.service.pojo.shop.goods.label;

import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 黄荣刚
 * @date 2019年7月11日
 *
 */
@Data
public class GoodsLabelVo {

	private Integer id;
	private String name;
	private Byte goodsDetail;
	private Byte goodsList;
	private Byte isAll;
	private Short level;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Short listPattern;
	private Byte goodsSelect;
	
	private List<GoodsView> goodsViewList;
    /**
     * 旧的类结构，后期优化建议删除
     */
	private List<Integer> catIdList;
	private List<Integer> sortIdList;
    /**
     * 李晓冰添加，修改标签回显使用
     */
	private List<SysCatevo> catList;
	private List<Sort> sortList;

}
