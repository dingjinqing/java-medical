/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 会员卡信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberCard extends TableImpl<MemberCardRecord> {

    private static final long serialVersionUID = 239792925;

    /**
     * The reference instance of <code>jmini_shop_489258.b2c_member_card</code>
     */
    public static final MemberCard MEMBER_CARD = new MemberCard();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MemberCardRecord> getRecordType() {
        return MemberCardRecord.class;
    }

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.id</code>.
     */
    public final TableField<MemberCardRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.card_name</code>.
     */
    public final TableField<MemberCardRecord, String> CARD_NAME = createField("card_name", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.card_type</code>. 0:普通会员卡，1:次卡,2:登记卡
     */
    public final TableField<MemberCardRecord, Byte> CARD_TYPE = createField("card_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:普通会员卡，1:次卡,2:登记卡");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.bg_type</code>. 0:背景色，1:背景图
     */
    public final TableField<MemberCardRecord, Byte> BG_TYPE = createField("bg_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:背景色，1:背景图");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.bg_color</code>. 背景色
     */
    public final TableField<MemberCardRecord, String> BG_COLOR = createField("bg_color", org.jooq.impl.SQLDataType.CHAR(10), this, "背景色");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.bg_img</code>. 背景图
     */
    public final TableField<MemberCardRecord, String> BG_IMG = createField("bg_img", org.jooq.impl.SQLDataType.VARCHAR(100), this, "背景图");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.discount</code>. 折扣
     */
    public final TableField<MemberCardRecord, BigDecimal> DISCOUNT = createField("discount", org.jooq.impl.SQLDataType.DECIMAL(10, 2), this, "折扣");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.sorce</code>. 开卡送积分
     */
    public final TableField<MemberCardRecord, Integer> SORCE = createField("sorce", org.jooq.impl.SQLDataType.INTEGER, this, "开卡送积分");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.buy_score</code>. 购物送积分策略json数据
     */
    public final TableField<MemberCardRecord, String> BUY_SCORE = createField("buy_score", org.jooq.impl.SQLDataType.CLOB, this, "购物送积分策略json数据");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.expire_type</code>. 0:固定日期 1：自领取之日起 2:不过期
     */
    public final TableField<MemberCardRecord, Byte> EXPIRE_TYPE = createField("expire_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:固定日期 1：自领取之日起 2:不过期");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.start_time</code>. 开始日期
     */
    public final TableField<MemberCardRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "开始日期");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.end_time</code>. 结束日期
     */
    public final TableField<MemberCardRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "结束日期");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.receive_day</code>. 领取之日起n
     */
    public final TableField<MemberCardRecord, Integer> RECEIVE_DAY = createField("receive_day", org.jooq.impl.SQLDataType.INTEGER, this, "领取之日起n");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.date_type</code>. 0:日，1:周 2: 月
     */
    public final TableField<MemberCardRecord, Byte> DATE_TYPE = createField("date_type", org.jooq.impl.SQLDataType.TINYINT, this, "0:日，1:周 2: 月");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.activation</code>. 0：不用激活，1：需要激活
     */
    public final TableField<MemberCardRecord, Byte> ACTIVATION = createField("activation", org.jooq.impl.SQLDataType.TINYINT, this, "0：不用激活，1：需要激活");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.receive_code</code>. 领取码
     */
    public final TableField<MemberCardRecord, String> RECEIVE_CODE = createField("receive_code", org.jooq.impl.SQLDataType.CHAR(10), this, "领取码");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.desc</code>. 使用须知
     */
    public final TableField<MemberCardRecord, String> DESC = createField("desc", org.jooq.impl.SQLDataType.CLOB, this, "使用须知");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.mobile</code>. 联系电话
     */
    public final TableField<MemberCardRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.CHAR(11), this, "联系电话");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.create_time</code>. 添加时间
     */
    public final TableField<MemberCardRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "添加时间");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.update_time</code>. 最后修改时间
     */
    public final TableField<MemberCardRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.flag</code>. 1:使用中，2:停止使用
     */
    public final TableField<MemberCardRecord, Byte> FLAG = createField("flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "1:使用中，2:停止使用");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.send_money</code>. 开卡送钱
     */
    public final TableField<MemberCardRecord, Integer> SEND_MONEY = createField("send_money", org.jooq.impl.SQLDataType.INTEGER, this, "开卡送钱");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.charge_money</code>. 充值活动策略
     */
    public final TableField<MemberCardRecord, String> CHARGE_MONEY = createField("charge_money", org.jooq.impl.SQLDataType.CLOB, this, "充值活动策略");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.use_time</code>. 使用时间 1工作日 2双休 0不限制
     */
    public final TableField<MemberCardRecord, Integer> USE_TIME = createField("use_time", org.jooq.impl.SQLDataType.INTEGER, this, "使用时间 1工作日 2双休 0不限制");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.store_list</code>. 可用门店
     */
    public final TableField<MemberCardRecord, String> STORE_LIST = createField("store_list", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("[]", org.jooq.impl.SQLDataType.VARCHAR)), this, "可用门店");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.count</code>. 卡总次数
     */
    public final TableField<MemberCardRecord, Integer> COUNT = createField("count", org.jooq.impl.SQLDataType.INTEGER, this, "卡总次数");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.del_flag</code>. 1为删除状态
     */
    public final TableField<MemberCardRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "1为删除状态");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.grade</code>. 等级卡的等级
     */
    public final TableField<MemberCardRecord, String> GRADE = createField("grade", org.jooq.impl.SQLDataType.CHAR(10).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.CHAR)), this, "等级卡的等级");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.grade_condition</code>. 等级卡的条件
     */
    public final TableField<MemberCardRecord, String> GRADE_CONDITION = createField("grade_condition", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "等级卡的条件");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.activation_cfg</code>. 激活信息配置
     */
    public final TableField<MemberCardRecord, String> ACTIVATION_CFG = createField("activation_cfg", org.jooq.impl.SQLDataType.VARCHAR(200), this, "激活信息配置");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.examine</code>. 是否审核 0不审核 1审核
     */
    public final TableField<MemberCardRecord, Byte> EXAMINE = createField("examine", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否审核 0不审核 1审核");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.discount_goods_id</code>. 折扣商品id
     */
    public final TableField<MemberCardRecord, String> DISCOUNT_GOODS_ID = createField("discount_goods_id", org.jooq.impl.SQLDataType.VARCHAR(299), this, "折扣商品id");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.discount_cat_id</code>. 折扣平台分类id
     */
    public final TableField<MemberCardRecord, String> DISCOUNT_CAT_ID = createField("discount_cat_id", org.jooq.impl.SQLDataType.VARCHAR(299), this, "折扣平台分类id");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.discount_sort_id</code>. 折扣商家分类id
     */
    public final TableField<MemberCardRecord, String> DISCOUNT_SORT_ID = createField("discount_sort_id", org.jooq.impl.SQLDataType.VARCHAR(299), this, "折扣商家分类id");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.discount_is_all</code>. 折扣商品范围： 0:部分商品，1:全部商品
     */
    public final TableField<MemberCardRecord, Byte> DISCOUNT_IS_ALL = createField("discount_is_all", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "折扣商品范围： 0:部分商品，1:全部商品");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.is_pay</code>. 0:直接购买 1:需要购买 2: 需要领取码
     */
    public final TableField<MemberCardRecord, Byte> IS_PAY = createField("is_pay", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:直接购买 1:需要购买 2: 需要领取码");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.pay_type</code>. 0:不支持现金购买，1:支持现金购买
     */
    public final TableField<MemberCardRecord, Byte> PAY_TYPE = createField("pay_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:不支持现金购买，1:支持现金购买");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.pay_fee</code>. 购买资金
     */
    public final TableField<MemberCardRecord, BigDecimal> PAY_FEE = createField("pay_fee", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "购买资金");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.pay_own_good</code>. 是否专属购买商品 0不是 1是
     */
    public final TableField<MemberCardRecord, Byte> PAY_OWN_GOOD = createField("pay_own_good", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否专属购买商品 0不是 1是");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.receive_action</code>. 领取方式 1:领取码 2：卡号+密码
     */
    public final TableField<MemberCardRecord, Byte> RECEIVE_ACTION = createField("receive_action", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "领取方式 1:领取码 2：卡号+密码");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.is_exchang</code>. 0不可 1部分 2全部
     */
    public final TableField<MemberCardRecord, Byte> IS_EXCHANG = createField("is_exchang", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0不可 1部分 2全部");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.store_use_switch</code>. 可否在门店使用  0不可以 1可以
     */
    public final TableField<MemberCardRecord, Byte> STORE_USE_SWITCH = createField("store_use_switch", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "可否在门店使用  0不可以 1可以");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.exchang_goods</code>. 可兑换商品id
     */
    public final TableField<MemberCardRecord, String> EXCHANG_GOODS = createField("exchang_goods", org.jooq.impl.SQLDataType.VARCHAR(299), this, "可兑换商品id");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.exchang_freight</code>. 运费策略 0免运费 1使用商品运费策略
     */
    public final TableField<MemberCardRecord, Byte> EXCHANG_FREIGHT = createField("exchang_freight", org.jooq.impl.SQLDataType.TINYINT, this, "运费策略 0免运费 1使用商品运费策略");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.exchang_count</code>. 允许兑换次数
     */
    public final TableField<MemberCardRecord, Integer> EXCHANG_COUNT = createField("exchang_count", org.jooq.impl.SQLDataType.INTEGER, this, "允许兑换次数");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.stock</code>. 发放总量
     */
    public final TableField<MemberCardRecord, Integer> STOCK = createField("stock", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "发放总量");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.limit</code>. 领取限制
     */
    public final TableField<MemberCardRecord, Integer> LIMIT = createField("limit", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "领取限制");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.discount_brand_id</code>. 商品品牌id
     */
    public final TableField<MemberCardRecord, String> DISCOUNT_BRAND_ID = createField("discount_brand_id", org.jooq.impl.SQLDataType.VARCHAR(299), this, "商品品牌id");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.send_coupon_switch</code>. 是否开卡送券：0不是，1是
     */
    public final TableField<MemberCardRecord, Byte> SEND_COUPON_SWITCH = createField("send_coupon_switch", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否开卡送券：0不是，1是");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.send_coupon_type</code>. 送惠类型：0优惠券，1优惠券礼包
     */
    public final TableField<MemberCardRecord, Byte> SEND_COUPON_TYPE = createField("send_coupon_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "送惠类型：0优惠券，1优惠券礼包");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.send_coupon_ids</code>. 赠送优惠券或礼包id，字符串逗号隔开
     */
    public final TableField<MemberCardRecord, String> SEND_COUPON_IDS = createField("send_coupon_ids", org.jooq.impl.SQLDataType.VARCHAR(20), this, "赠送优惠券或礼包id，字符串逗号隔开");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.custom_rights</code>. 自定义权益
     */
    public final TableField<MemberCardRecord, String> CUSTOM_RIGHTS = createField("custom_rights", org.jooq.impl.SQLDataType.CLOB, this, "自定义权益");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.freeship_limit</code>. -1：不包邮，0:不限制，1：持卡有效期内，2：年，3：季，4：月，5：周，6：日
     */
    public final TableField<MemberCardRecord, Byte> FREESHIP_LIMIT = createField("freeship_limit", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("-1", org.jooq.impl.SQLDataType.TINYINT)), this, "-1：不包邮，0:不限制，1：持卡有效期内，2：年，3：季，4：月，5：周，6：日");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.freeship_num</code>. 周期内包邮次数
     */
    public final TableField<MemberCardRecord, Integer> FREESHIP_NUM = createField("freeship_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "周期内包邮次数");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.renew_member_card</code>. 0:不可续费，1:可续费
     */
    public final TableField<MemberCardRecord, Byte> RENEW_MEMBER_CARD = createField("renew_member_card", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:不可续费，1:可续费");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.renew_type</code>. 0:现金 1：积分
     */
    public final TableField<MemberCardRecord, Byte> RENEW_TYPE = createField("renew_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:现金 1：积分");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.renew_num</code>. 现金或积分数量
     */
    public final TableField<MemberCardRecord, BigDecimal> RENEW_NUM = createField("renew_num", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "现金或积分数量");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.renew_time</code>. 续费时间
     */
    public final TableField<MemberCardRecord, Integer> RENEW_TIME = createField("renew_time", org.jooq.impl.SQLDataType.INTEGER, this, "续费时间");

    /**
     * The column <code>jmini_shop_489258.b2c_member_card.renew_date_type</code>. 0:日，1:周 2: 月
     */
    public final TableField<MemberCardRecord, Byte> RENEW_DATE_TYPE = createField("renew_date_type", org.jooq.impl.SQLDataType.TINYINT, this, "0:日，1:周 2: 月");

    /**
     * Create a <code>jmini_shop_489258.b2c_member_card</code> table reference
     */
    public MemberCard() {
        this(DSL.name("b2c_member_card"), null);
    }

    /**
     * Create an aliased <code>jmini_shop_489258.b2c_member_card</code> table reference
     */
    public MemberCard(String alias) {
        this(DSL.name(alias), MEMBER_CARD);
    }

    /**
     * Create an aliased <code>jmini_shop_489258.b2c_member_card</code> table reference
     */
    public MemberCard(Name alias) {
        this(alias, MEMBER_CARD);
    }

    private MemberCard(Name alias, Table<MemberCardRecord> aliased) {
        this(alias, aliased, null);
    }

    private MemberCard(Name alias, Table<MemberCardRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("会员卡信息"));
    }

    public <O extends Record> MemberCard(Table<O> child, ForeignKey<O, MemberCardRecord> key) {
        super(child, key, MEMBER_CARD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniShop_471752.MINI_SHOP_471752;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MEMBER_CARD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MemberCardRecord, Integer> getIdentity() {
        return Keys.IDENTITY_MEMBER_CARD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MemberCardRecord> getPrimaryKey() {
        return Keys.KEY_B2C_MEMBER_CARD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MemberCardRecord>> getKeys() {
        return Arrays.<UniqueKey<MemberCardRecord>>asList(Keys.KEY_B2C_MEMBER_CARD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberCard as(String alias) {
        return new MemberCard(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberCard as(Name alias) {
        return new MemberCard(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MemberCard rename(String name) {
        return new MemberCard(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MemberCard rename(Name name) {
        return new MemberCard(name, null);
    }
}
