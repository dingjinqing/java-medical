package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.BrandClassifyRecord;
import com.vpu.mp.db.shop.tables.records.GoodsBrandRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ChineseToPinYinUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.brand.*;
import com.vpu.mp.service.pojo.wxapp.goods.brand.*;
import com.vpu.mp.service.shop.image.ImageService;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 商品品牌
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
@Service
public class GoodsBrandService extends ShopBaseService {

    private final static String BRAND_NUM = "brand_num";

    private final static int DEFAULT_GOODS_BRAND_ID = 0;

    @Autowired
    ImageService imageService;

    /**
     * 分页获取品牌信息
     *
     * @param param
     * @return
     */
    public PageResult<GoodsBrand> getPageList(GoodsBrandPageListParam param) {
        String goodsNumFieldName = "goods_num";
        String classifyNameFieldName = "classify_name";
        Field<Object> goodsNumField = db().selectCount().from(GOODS)
            .where(GOODS.BRAND_ID.eq(GOODS_BRAND.ID)).and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .asField(goodsNumFieldName);

        Field<Object> classifyNameField = db().select(BRAND_CLASSIFY.CLASSIFY_NAME).from(BRAND_CLASSIFY)
            .where(BRAND_CLASSIFY.CLASSIFY_ID.eq(GOODS_BRAND.CLASSIFY_ID)).asField(classifyNameFieldName);

        SelectJoinStep<Record11<Integer, String, String, String, Byte, Timestamp, String, Byte, Integer, Object, Object>> selectFrom = db().select(GOODS_BRAND.ID,
            GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.CREATE_TIME, GOODS_BRAND.DESC, GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID,
            goodsNumField, classifyNameField).from(GOODS_BRAND);

        SelectConditionStep<?> select = this.buildOptions(selectFrom, param);

        select.orderBy(GOODS_BRAND.FIRST.desc(), GOODS_BRAND.CREATE_TIME.desc());

        PageResult<GoodsBrand> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsBrand.class);

        pageResult.dataList.forEach(goodsBrand -> {
            goodsBrand.setLogoUrl(getImgFullUrlUtil(goodsBrand.getLogo()));
        });

        return pageResult;
    }

    /**
     * 根据过滤条件构造对应的sql语句
     *
     * @param select
     * @param param
     * @return
     */
    private SelectConditionStep<?> buildOptions(SelectWhereStep<?> select, GoodsBrandPageListParam param) {
        SelectConditionStep<?> scs = select.where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));

        if (!StringUtils.isBlank(param.getBrandName())) {
            scs = scs.and(GOODS_BRAND.BRAND_NAME.like(this.likeValue(param.getBrandName())));
        }

        if (param.getStartCreateTime() != null) {
            scs = scs.and(GOODS_BRAND.CREATE_TIME.ge(param.getStartCreateTime()));
        }

        if (param.getEndCreateTime() != null) {
            scs = scs.and(GOODS_BRAND.CREATE_TIME.le(param.getEndCreateTime()));
        }

        if (param.getClassifyId() != null) {
            scs = scs.and(GOODS_BRAND.CLASSIFY_ID.eq(param.getClassifyId()));
        }

        if (param.getIsRecommend() != null) {
            scs = scs.and(GOODS_BRAND.IS_RECOMMEND.eq(param.getIsRecommend()));
        }

        return scs;
    }

    /**
     * 添加品牌
     *
     * @param goodsBrand
     * @return 数据库受影响行数
     */
    public void insert(GoodsBrand goodsBrand) {

        transaction(() -> {
            GoodsBrandRecord goodsBrandRecord = db().newRecord(GOODS_BRAND);
            assign(goodsBrand, goodsBrandRecord);
            goodsBrandRecord.insert();

            if (goodsBrand.getGoodsIds() != null && goodsBrand.getGoodsIds().size() > 0) {
                db().update(GOODS).set(GOODS.BRAND_ID, goodsBrandRecord.getId())
                    .where(GOODS.GOODS_ID.in(goodsBrand.getGoodsIds()))
                    .execute();
            }

        });

    }

    /**
     * 假删除指定品牌
     *
     * @param goodsBrand
     * @return 数据库受影响行数
     */
    public void delete(GoodsBrand goodsBrand) {
        transaction(() -> deleteGoodsBrand(Arrays.asList(goodsBrand.getId())));
    }

    private void deleteGoodsBrand(List<Integer> brandIds) {
        db().update(GOODS_BRAND)
            .set(GOODS_BRAND.DEL_FLAG, DelFlag.DISABLE.getCode())
            .set(GOODS_BRAND.BRAND_NAME, DSL.concat(DelFlag.DEL_ITEM_PREFIX)
                .concat(GOODS_BRAND.ID)
                .concat(DelFlag.DEL_ITEM_SPLITER)
                .concat(GOODS_BRAND.BRAND_NAME))
            .where(GOODS_BRAND.ID.in(brandIds))
            .execute();

        db().update(GOODS).set(GOODS.BRAND_ID, DEFAULT_GOODS_BRAND_ID)
            .where(GOODS.BRAND_ID.in(brandIds))
            .execute();
    }

    /**
     * 更新指定商品
     *
     * @param goodsBrand
     * @return
     */
    public void update(GoodsBrand goodsBrand) {
        GoodsBrandRecord goodsBrandRecord = new GoodsBrandRecord();
        assign(goodsBrand, goodsBrandRecord);
        transaction(() -> {
            db().executeUpdate(goodsBrandRecord);
            if (goodsBrand.getGoodsIds() != null && goodsBrand.getGoodsIds().size() > 0) {
                db().update(GOODS).set(GOODS.BRAND_ID, goodsBrandRecord.getId())
                    .where(GOODS.GOODS_ID.in(goodsBrand.getGoodsIds()))
                    .execute();
            }
        });
    }

    /**
     * 查询单个
     *
     * @param goodsBrand
     * @return
     */
    public GoodsBrand select(GoodsBrand goodsBrand) {

        GoodsBrand gb = db().select(GOODS_BRAND.ID,
            GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO, GOODS_BRAND.FIRST, GOODS_BRAND.CREATE_TIME, GOODS_BRAND.DESC, GOODS_BRAND.IS_RECOMMEND, GOODS_BRAND.CLASSIFY_ID)
            .from(GOODS_BRAND).where(GOODS_BRAND.ID.eq(goodsBrand.getId()))
            .fetchOne().into(GoodsBrand.class);

        List<Integer> goodsIds = db().select(GOODS.GOODS_ID).from(GOODS).where(GOODS.BRAND_ID.eq(goodsBrand.getId()))
            .fetch().into(Integer.class);

        gb.setGoodsIds(goodsIds);

        gb.setLogoUrl(getImgFullUrlUtil(gb.getLogo()));
        return gb;
    }

    /**
     * 判断商品名称是否存在，新增使用
     *
     * @param goodsBrand
     * @return
     */
    public boolean isBrandNameExist(GoodsBrand goodsBrand) {

        Record1<Integer> countRecord = db().selectCount().from(GOODS_BRAND)
            .where(GOODS_BRAND.BRAND_NAME.eq(goodsBrand.getBrandName()))
            .and(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .fetchOne();
        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断其他商品名称是否存在同名，修改使用
     *
     * @param goodsBrand
     * @return
     */
    public boolean isOtherBrandNameExist(GoodsBrand goodsBrand) {
        Record1<Integer> countRecord = db().selectCount().from(GOODS_BRAND)
            .where(GOODS_BRAND.BRAND_NAME.eq(goodsBrand.getBrandName()))
            .and(GOODS_BRAND.ID.ne(goodsBrand.getId()))
            .and(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .fetchOne();
        Integer count = countRecord.getValue(0, Integer.class);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 根据brandId列出所有品牌
     *
     * @return
     */
    public Map<Integer,GoodsBrandVo>  listGoodsBrandNameByIds(List<Integer> ids) {
        return db().select(GOODS_BRAND.ID, GOODS_BRAND.BRAND_NAME)
            .from(GOODS_BRAND)
            .where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(GOODS_BRAND.ID.in(ids))
            .fetch().into(GoodsBrandVo.class)
            .stream().collect(Collectors.toMap(GoodsBrandVo::getId,x->x));
    }
    /**
     * 列出所有品牌
     *
     * @return
     */
    public List<GoodsBrandVo> listGoodsBrandName() {
        List<GoodsBrandVo> goodsBrandNames = db().select(GOODS_BRAND.ID, GOODS_BRAND.BRAND_NAME)
            .from(GOODS_BRAND)
            .where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .fetch().into(GoodsBrandVo.class);

        return goodsBrandNames;
    }


    /**
     * 品牌分类列表
     *
     * @return
     */
    public List<GoodsBrandClassifyVo> getBrandClassifyList() {
        List<GoodsBrandClassifyVo> voList = db().select(BRAND_CLASSIFY.CLASSIFY_ID, BRAND_CLASSIFY.CLASSIFY_NAME)
            .from(BRAND_CLASSIFY)
            .where(BRAND_CLASSIFY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .fetch().into(GoodsBrandClassifyVo.class);

        return voList;
    }

    /**
     * 品牌分类分页查询
     *
     * @param param
     * @return
     */
    public PageResult<GoodsBrandClassifyVo> getBrandClassifyList(GoodsBrandClassifyParam param) {

        SelectOnConditionStep<Record5<Integer, String, Timestamp, Short, Integer>> selectFrom = db().select(BRAND_CLASSIFY.CLASSIFY_ID, BRAND_CLASSIFY.CLASSIFY_NAME, BRAND_CLASSIFY.CREATE_TIME,
            BRAND_CLASSIFY.FIRST,
            DSL.count(GOODS_BRAND.ID).as(BRAND_NUM))
            .from(BRAND_CLASSIFY).leftJoin(GOODS_BRAND).on(BRAND_CLASSIFY.CLASSIFY_ID.eq(GOODS_BRAND.CLASSIFY_ID));

        SelectConditionStep<?> select = this.buildBrandClassifyCondition(selectFrom, param);

        select.groupBy(BRAND_CLASSIFY.CLASSIFY_ID, BRAND_CLASSIFY.CLASSIFY_NAME, BRAND_CLASSIFY.FIRST, BRAND_CLASSIFY.CREATE_TIME);
        select.orderBy(BRAND_CLASSIFY.FIRST.desc(), BRAND_CLASSIFY.CREATE_TIME.desc());

        PageResult<GoodsBrandClassifyVo> pageResult = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsBrandClassifyVo.class);

        return pageResult;
    }

    private SelectConditionStep<?> buildBrandClassifyCondition(SelectOnConditionStep<?> selectFrom, GoodsBrandClassifyParam param) {
        SelectConditionStep<?> scs = selectFrom.where(BRAND_CLASSIFY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));

        if (!StringUtils.isBlank(param.getClassifyName())) {
            scs = scs.and(BRAND_CLASSIFY.CLASSIFY_NAME.like(this.likeValue(param.getClassifyName())));
        }

        if (param.getStartCreateTime() != null) {
            scs = scs.and(BRAND_CLASSIFY.CREATE_TIME.ge(param.getStartCreateTime()));
        }

        if (param.getEndCreateTime() != null) {
            scs = scs.and(BRAND_CLASSIFY.CREATE_TIME.le(param.getStartCreateTime()));
        }
        return scs;
    }


    /**
     * 新增品牌分类
     *
     * @param param
     */
    public void insertBrandClassify(GoodsBrandClassifyParam param) {
        db().insertInto(BRAND_CLASSIFY, BRAND_CLASSIFY.CLASSIFY_NAME, BRAND_CLASSIFY.FIRST)
            .values(param.getClassifyName(), param.getFirst()).execute();
    }

    /**
     * 根据classifyId查询品牌分类
     * @param param
     * @return
     */
    public GoodsBrandClassifyVo selectBrandClassify(GoodsBrandClassifyParam param) {
        GoodsBrandClassifyVo vo = db().select(BRAND_CLASSIFY.CLASSIFY_ID, BRAND_CLASSIFY.CLASSIFY_NAME, BRAND_CLASSIFY.FIRST)
            .from(BRAND_CLASSIFY)
            .where(BRAND_CLASSIFY.CLASSIFY_ID.eq(param.getClassifyId()))
            .fetchOne().into(GoodsBrandClassifyVo.class);

        return vo;
    }

    /**
     * 修改分类
     *
     * @param param
     */
    public void updateBrandClassify(GoodsBrandClassifyParam param) {
        BrandClassifyRecord brandClassifyRecord = new BrandClassifyRecord();

        brandClassifyRecord.setClassifyId(param.getClassifyId());

        if (param.getClassifyName() != null) {
            brandClassifyRecord.setClassifyName(param.getClassifyName());
        }
        if (param.getFirst() != null) {
            brandClassifyRecord.setFirst(param.getFirst());
        }

        db().executeUpdate(brandClassifyRecord);
    }

    public void deleteBrandClassify(GoodsBrandClassifyParam param) {
        transaction(() -> {
            db().update(BRAND_CLASSIFY)
                .set(BRAND_CLASSIFY.DEL_FLAG, DelFlag.DISABLE.getCode())
                .set(BRAND_CLASSIFY.CLASSIFY_NAME, DSL.concat(getDelPrefix(param.getClassifyId()))
                    .concat(BRAND_CLASSIFY.CLASSIFY_NAME))
                .where(BRAND_CLASSIFY.CLASSIFY_ID.eq(param.getClassifyId())).execute();

            List<Integer> brandIds = db().select(GOODS_BRAND.ID)
                .from(GOODS_BRAND)
                .where(GOODS_BRAND.CLASSIFY_ID.eq(param.getClassifyId()))
                .fetch().into(Integer.class);

            //解除品牌关联关系
           db().update(GOODS_BRAND).set(GOODS_BRAND.CLASSIFY_ID,0).where(GOODS_BRAND.ID.in(brandIds)).execute();
        });
    }

    public void batchUpdateBrand(GoodsBrandBatchParam param) {
        db().update(GOODS_BRAND).set(GOODS_BRAND.CLASSIFY_ID, param.getClassifyId())
            .where(GOODS_BRAND.ID.in(param.getIds()))
            .execute();
    }

    public boolean isClassifyNameExist(GoodsBrandClassifyParam param) {
        Integer count = db().selectCount().from(BRAND_CLASSIFY)
            .where(BRAND_CLASSIFY.CLASSIFY_NAME.eq(param.getClassifyName()))
            .fetchOne().into(Integer.class);

        return count > 0;
    }

    public boolean isOtherClassifyNameExist(GoodsBrandClassifyParam param) {
        Integer count = db().selectCount().from(BRAND_CLASSIFY)
            .where(BRAND_CLASSIFY.CLASSIFY_NAME.eq(param.getClassifyName()))
            .and(BRAND_CLASSIFY.CLASSIFY_ID.ne(param.getClassifyId()))
            .fetchOne().into(Integer.class);

        return count > 0;
    }

    /**
     * 查询所有有效的品牌，按照名称拼音进行组织
     * @return 相同开头名称的处于同一个list集合中。
     */
    public List<GoodsBrandMpPinYinVo> getAllBrandGroupByPinYinNameMp(){
        List<GoodsBrandMpVo> pinYinVos = db().select(GOODS_BRAND.ID, GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO)
            .from(GOODS_BRAND)
            .where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(GOODS_BRAND.FIRST.desc(), GOODS_BRAND.CREATE_TIME)
            .fetchInto(GoodsBrandMpVo.class);

        // 处理拼音
        TreeMap<String,List<GoodsBrandMpVo>> treeMap = new TreeMap<>();
        for (GoodsBrandMpVo pinYinVo : pinYinVos) {
            String c = ChineseToPinYinUtil.getStartAlphabet(pinYinVo.getBrandName());
            pinYinVo.setLogo(getImgFullUrlUtil(pinYinVo.getLogo()));

            List<GoodsBrandMpVo> list = treeMap.get(c);
            if (list == null) {
                list = new ArrayList<>();
                treeMap.put(c,list);
            }
            list.add(pinYinVo);
        }

        // 处理为返回值
        LinkedList<GoodsBrandMpPinYinVo> retList = new LinkedList<>();
        treeMap.forEach((c,goodsBrandsList)->{
            GoodsBrandMpPinYinVo vo =new GoodsBrandMpPinYinVo();
            vo.setCharacter(c);
            vo.setGoodsBrands(goodsBrandsList);
            retList.add(vo);
        });

        // 处理#符合的问题
        if (retList.peek() != null&&ChineseToPinYinUtil.OHTER_CHARACTER.equals(retList.peek().getCharacter())) {
            GoodsBrandMpPinYinVo t = retList.removeFirst();
            retList.addLast(t);
        }

        return retList;
    }

    /**
     * 获取所有推荐品牌（前端按照品牌列表显示）
     * @return 推荐品牌集合
     */
    public List<GoodsBrandMpVo> getAllRecommendBrandMp(){
        List<GoodsBrandMpVo> goodsBrandMpVos = db().selectFrom(GOODS_BRAND).where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(GOODS_BRAND.IS_RECOMMEND.eq(GoodsConstant.RECOMMEND_BRAND)).orderBy(GOODS_BRAND.FIRST.desc(), GOODS_BRAND.CREATE_TIME.desc())
            .fetchInto(GoodsBrandMpVo.class);

        goodsBrandMpVos.forEach(goodsBrandMpVo -> goodsBrandMpVo.setLogo(getImgFullUrlUtil(goodsBrandMpVo.getLogo())));
        return goodsBrandMpVos;
    }

    /**
     * 获取所有推荐品牌（前端按照品牌分类显示）,
     * @return {@link GoodsBrandClassifyNameMpVo} 的list,每一个元素是分类名称和该分类下所有品牌
     */
    public  List<GoodsBrandClassifyNameMpVo> getAllRecommendBrandGroupByClassifyMp(){
        // 先获取所有有效推荐品牌（按照first和createTime排序）和其所属分类id对应的map
        Map<Integer, List<GoodsBrandMpVo>> goodsBrandMap =
            db().select(GOODS_BRAND.ID, GOODS_BRAND.BRAND_NAME, GOODS_BRAND.E_NAME, GOODS_BRAND.LOGO,GOODS_BRAND.CLASSIFY_ID)
            .from(GOODS_BRAND)
            .where(GOODS_BRAND.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GOODS_BRAND.IS_RECOMMEND.eq(GoodsConstant.RECOMMEND_BRAND))
            .orderBy(GOODS_BRAND.FIRST.desc(), GOODS_BRAND.CREATE_TIME.desc())
            .fetchGroups(GOODS_BRAND.CLASSIFY_ID, GoodsBrandMpVo.class);

        // 获取品牌分类
        List<BrandClassifyRecord> brandClassify = db().selectFrom(BRAND_CLASSIFY).where(BRAND_CLASSIFY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .orderBy(BRAND_CLASSIFY.FIRST.desc(), BRAND_CLASSIFY.CREATE_TIME.desc()).fetchInto(BrandClassifyRecord.class);

        List<GoodsBrandClassifyNameMpVo> retList = new ArrayList<>();
        // 根据分类获取其分类下的所有品牌，并转换为结果数据
        for (BrandClassifyRecord record : brandClassify) {
            Integer classifyId = record.getClassifyId();
            if (goodsBrandMap.get(classifyId) != null) {
                GoodsBrandClassifyNameMpVo vo =new GoodsBrandClassifyNameMpVo();
                vo.setClassifyName(record.getClassifyName());
                // 处理图片地址
                List<GoodsBrandMpVo> goodsBrandMpVos = goodsBrandMap.get(classifyId);
                goodsBrandMpVos.forEach(brand-> brand.setLogo(getImgFullUrlUtil(brand.getLogo())));
                vo.setGoodsBrands(goodsBrandMpVos);

                retList.add(vo);
            }
        }
        if (goodsBrandMap.get(GoodsConstant.NO_CLASSIFY_ID) != null) {
            GoodsBrandClassifyNameMpVo vo =new GoodsBrandClassifyNameMpVo();
            List<GoodsBrandMpVo> goodsBrandMpVos = goodsBrandMap.get(GoodsConstant.NO_CLASSIFY_ID);
            goodsBrandMpVos.forEach(brand-> brand.setLogo(getImgFullUrlUtil(brand.getLogo())));
            vo.setGoodsBrands(goodsBrandMpVos);

            retList.add(vo);
        }
        return retList;
    }

    /**
     * 将相对路劲修改为全路径
     *
     * @param relativePath 相对路径
     * @return null或全路径
     */
    private String getImgFullUrlUtil(String relativePath) {
        if (org.apache.commons.lang3.StringUtils.isBlank(relativePath)) {
            return null;
        } else {
            return imageService.imageUrl(relativePath);
        }
    }
}
