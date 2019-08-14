package com.vpu.mp.service.shop.market.increasepurchase;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.records.PurchasePriceDefineRecord;
import com.vpu.mp.db.shop.tables.records.PurchasePriceRuleRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.*;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.jooq.impl.DSL.sum;

/**
 * @author liufei
 * @date 2019/8/14
 * @description
 */
@Slf4j
@Service
public class IncreasePurchaseService extends ShopBaseService {
    private static OrderGoods og = OrderGoods.ORDER_GOODS.as("og");
    private static OrderInfo oi = OrderInfo.ORDER_INFO.as("oi");
    private static Goods g = Goods.GOODS.as("g");
    private static PurchasePriceDefine ppd = PurchasePriceDefine.PURCHASE_PRICE_DEFINE.as("ppd");
    private static PurchasePriceRule ppr = PurchasePriceRule.PURCHASE_PRICE_RULE.as("ppr");

    @Autowired
    QrCodeService qrCodeService;
    /**
     * 获取分享二维码常量参数
     */
    private static final String IDENTITY_ID = "identity_id";

    /**
     * 分页查询加价购活动信息
     *
     * @param param 查询条件
     * @return 分页数据
     */
    public PageResult<PurchaseShowVo> selectByPage(PurchaseShowParam param) {
        Condition categoryConditon = ppd.ID.isNotNull();
        switch (param.getCategory()) {
            /** 所有0 */
            case 0b0000:
                break;
            /** 已停用1 */
            case 0b0001:
                categoryConditon = categoryConditon.and(ppd.STATUS.eq((byte) 0));
                break;
            /** 已过期2 */
            case 0b0010:
                categoryConditon = categoryConditon.and(ppd.END_TIME.lessThan(new Timestamp(System.currentTimeMillis()))).and(ppd.STATUS.eq((byte) 1));
                break;
            /** 未开始4 */
            case 0b0100:
                categoryConditon = categoryConditon.and(ppd.START_TIME.greaterThan(new Timestamp(System.currentTimeMillis()))).and(ppd.STATUS.eq((byte) 1));
                break;
            /** 进行中8 */
            case 0b1000:
                categoryConditon = categoryConditon.and(ppd.START_TIME.lessThan(new Timestamp(System.currentTimeMillis()))).and(ppd.END_TIME.greaterThan(new Timestamp(System.currentTimeMillis()))).and(ppd.STATUS.eq((byte) 1));
                break;
            /** 默认进行中8 */
            default:
                categoryConditon = categoryConditon.and(ppd.START_TIME.lessThan(new Timestamp(System.currentTimeMillis()))).and(ppd.END_TIME.greaterThan(new Timestamp(System.currentTimeMillis()))).and(ppd.STATUS.eq((byte) 1));
                break;
        }
        Table<Record6<Integer, String, Short, Short, Timestamp, Timestamp>> conditionStep = db().
            select(ppd.ID, ppd.NAME, ppd.LEVEL, ppd.MAX_CHANGE_PURCHASE, ppd.START_TIME, ppd.END_TIME).from(ppd).where(categoryConditon).asTable("ppd");

        Condition selectConditon = ppd.ID.isNotNull();
        if (param.getName() != null) {
            selectConditon = selectConditon.and(ppd.NAME.like(this.likeValue(param.getName())));
        }
        if (param.getStatus() != null) {
            selectConditon = selectConditon.and(ppd.STATUS.eq(param.getStatus()));
        }
        if (param.getStartTime() != null) {
            selectConditon = selectConditon.and(ppd.START_TIME.greaterThan(param.getStartTime()));
        }
        if (param.getEndTime() != null) {
            selectConditon = selectConditon.and(ppd.END_TIME.lessThan(param.getEndTime()));
        }
        if (param.getFullPriceUp() != null) {
            selectConditon = selectConditon.and(ppr.FULL_PRICE.lessThan(param.getFullPriceUp()));
        }
        if (param.getFullPriceDown() != null) {
            selectConditon = selectConditon.and(ppr.FULL_PRICE.greaterThan(param.getFullPriceDown()));
        }
        if (param.getPurchasePriceUp() != null) {
            selectConditon = selectConditon.and(ppr.PURCHASE_PRICE.lessThan(param.getPurchasePriceUp()));
        }
        if (param.getPurchasePriceDown() != null) {
            selectConditon = selectConditon.and(ppr.PURCHASE_PRICE.greaterThan(param.getPurchasePriceDown()));
        }

        SelectConditionStep<Record6<Integer, String, Short, Short, Timestamp, Timestamp>> resultStep = db().
            select(ppd.ID, ppd.NAME, ppd.LEVEL, ppd.MAX_CHANGE_PURCHASE, ppd.START_TIME, ppd.END_TIME).from(conditionStep).where(selectConditon);
        PageResult<PurchaseShowVo> pageResult = this.getPageResult(resultStep, param.getCurrentPage(), param.getPageRows(), PurchaseShowVo.class);
        for (PurchaseShowVo vo : pageResult.getDataList()) {
            Integer purchaseId = vo.getId();
            vo.setResaleQuantity(getResaleQuantity(purchaseId));
            vo.setPurchaseInfo(getPurchaseDetailInfo(purchaseId));
        }
        return pageResult;
    }

    private BigDecimal[][] getPurchaseDetailInfo(Integer purchasePriceId) {
        Object[][] detailInfo = db().select(ppr.FULL_PRICE, ppr.PURCHASE_PRICE).from(ppr).where(ppr.PURCHASE_PRICE_ID.eq(purchasePriceId)).orderBy(ppr.ID).fetchArrays();
        return convert(detailInfo);
    }

    private BigDecimal[][] convert(Object[][] source) {
        BigDecimal[][] target = new BigDecimal[source.length][2];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                target[i][j] = BigDecimal.valueOf(Double.valueOf(source[i][j].toString()));
            }
        }
        return target;
    }

    /**
     * 计算已换购数量
     */
    private Short getResaleQuantity(Integer purchasePriceId) {
        //TODO ORDER_GOODS表重构后如何计算已换购数量
        return 0;
    }

    /**
     * 添加加价购活动
     *
     * @param param 加价购活动详情参数
     */
    public void addIncreasePurchase(AddPurchaseParam param) {
        PurchasePriceDefineRecord defineRecord = new PurchasePriceDefineRecord();
        PurchasePriceRuleRecord ruleRecord = new PurchasePriceRuleRecord();
        FieldsUtil.assignNotNull(param, defineRecord);
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            db.executeInsert(defineRecord);
            Integer pruchaseId = db.lastID().intValue();
            log.debug("获取刚刚插入PurchasePriceDefine表后改表自增主键自动生成的值：{}", pruchaseId);
            for (PurchaseRule rule : param.getRules()) {
                FieldsUtil.assignNotNull(rule, ruleRecord);
                ruleRecord.setPurchasePriceId(pruchaseId);
                db.executeInsert(ruleRecord);
                ruleRecord.reset();
            }
        });
    }

    /**
     * 更新加价购活动
     *
     * @param param 加价购活动详情参数
     */
    public void updateIncreasePurchase(UpdatePurchaseParam param) {
        Integer count = db().selectCount().from(ppd).where(ppd.ID.eq(param.getId())).fetchOptionalInto(Integer.class).orElseThrow(() -> new RuntimeException("Information doesn't exist!"));
        if (count == 0) {
            throw new RuntimeException("Information doesn't exist!");
        }
        PurchasePriceDefineRecord defineRecord = new PurchasePriceDefineRecord();
        PurchasePriceRuleRecord ruleRecord = new PurchasePriceRuleRecord();
        FieldsUtil.assignNotNull(param, defineRecord);
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            db.executeUpdate(defineRecord);
            for (PurchaseRule rule : param.getRules()) {
                FieldsUtil.assignNotNull(rule, ruleRecord);
                ruleRecord.setPurchasePriceId(param.getId());
                db.executeInsert(ruleRecord);
                ruleRecord.reset();
            }
        });
    }

    /**
     * 获取加价购活动详细信息
     *
     * @param param 加价购活动id
     */
    public PurchaseDetailVo getPurchaseDetail(PurchaseDetailParam param) {
        PurchaseDetailVo vo = db().select(ppd.ID, ppd.NAME, ppd.LEVEL, ppd.MAX_CHANGE_PURCHASE, ppd.START_TIME, ppd.END_TIME).from(ppd).where(ppd.ID.eq(param.getPurchaseId())).fetchOptionalInto(PurchaseDetailVo.class).orElseThrow(() -> new RuntimeException("Information doesn't exist!"));
        vo.setPurchaseInfo(getPurchaseDetailInfo(param.getPurchaseId()));
        String goodsId = vo.getGoodsId();
        //主商品详情
        Integer[] goodsIdArray = stringArray2Int(goodsId.split(","));
        vo.setMainGoods(db().select(g.GOODS_NAME, g.SHOP_ID, g.GOODS_NUMBER).from(g).where(g.GOODS_ID.in(goodsIdArray)).fetchInto(GoodsInfo.class));
        //换购商品详情
        List<String> redemptionGoods = db().select(ppr.PRODUCT_ID).from(ppr).where(ppr.PURCHASE_PRICE_ID.eq(param.getPurchaseId())).orderBy(ppr.ID).fetchInto(String.class);
        List<GoodsInfo>[] redemptionresult = vo.getRedemptionGoods();
        Integer integer = 0;
        for (String s : redemptionGoods) {
            Integer[] array = stringArray2Int(s.split(","));
            redemptionresult[integer++] = db().select(g.GOODS_NAME, g.SHOP_ID, g.GOODS_NUMBER).from(g).where(g.GOODS_ID.in(array)).fetchInto(GoodsInfo.class);
        }
        vo.setRedemptionGoods(redemptionresult);
        return vo;
    }

    private Integer[] stringArray2Int(String[] source) {
        Integer[] target = new Integer[source.length];
        int i = 0;
        for (String s : source) {
            target[i++] = Integer.valueOf(s);
        }
        return target;
    }

    /**
     * 停用/启用加价购活动
     *
     * @param param 活动id和被修改的状态值
     */
    public void changeTheStatus(PurchaseStatusParam param) {
        db().update(ppd).set(ppd.STATUS, param.getStatus()).where(ppd.ID.eq(param.getId())).execute();
    }

    /**
     * 分享,获取小程序二维码
     *
     * @param param 表单id
     * @return 图片路径
     */
    public ShareQrCodeVo share(PurchaseStatusParam param) {
        String pathParam = IDENTITY_ID + param.getId();
        String imageUrl = qrCodeService.getMpQrCode(QrCodeTypeEnum.RAISE_PRICE_BUY_MAIN_GOODS, pathParam);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.SECKILL_GOODS_ITEM_INFO.getPathUrl(pathParam));
        return vo;

    }

}
