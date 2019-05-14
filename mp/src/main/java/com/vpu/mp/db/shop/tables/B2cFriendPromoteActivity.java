/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cFriendPromoteActivityRecord;

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
import org.jooq.types.UByte;
import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class B2cFriendPromoteActivity extends TableImpl<B2cFriendPromoteActivityRecord> {

    private static final long serialVersionUID = -1967000365;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_friend_promote_activity</code>
     */
    public static final B2cFriendPromoteActivity B2C_FRIEND_PROMOTE_ACTIVITY = new B2cFriendPromoteActivity();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cFriendPromoteActivityRecord> getRecordType() {
        return B2cFriendPromoteActivityRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.id</code>.
     */
    public final TableField<B2cFriendPromoteActivityRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.shop_id</code>. 店铺ID
     */
    public final TableField<B2cFriendPromoteActivityRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.act_code</code>. 活动编码
     */
    public final TableField<B2cFriendPromoteActivityRecord, String> ACT_CODE = createField("act_code", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "活动编码");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.act_name</code>. 活动名称
     */
    public final TableField<B2cFriendPromoteActivityRecord, String> ACT_NAME = createField("act_name", org.jooq.impl.SQLDataType.VARCHAR(120).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "活动名称");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.start_time</code>. 活动起始时间
     */
    public final TableField<B2cFriendPromoteActivityRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "活动起始时间");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.end_time</code>. 活动截止时间
     */
    public final TableField<B2cFriendPromoteActivityRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "活动截止时间");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.reward_type</code>. 奖励类型：0赠送商品，1折扣商品，2赠送优惠券
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> REWARD_TYPE = createField("reward_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "奖励类型：0赠送商品，1折扣商品，2赠送优惠券");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.reward_content</code>. 奖励内容
     */
    public final TableField<B2cFriendPromoteActivityRecord, String> REWARD_CONTENT = createField("reward_content", org.jooq.impl.SQLDataType.CLOB, this, "奖励内容");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.reward_duration</code>. 奖励有效期
     */
    public final TableField<B2cFriendPromoteActivityRecord, Integer> REWARD_DURATION = createField("reward_duration", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "奖励有效期");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.reward_duration_unit</code>. 奖励有效期单位：0小时，1天，2周，3月，4年
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> REWARD_DURATION_UNIT = createField("reward_duration_unit", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "奖励有效期单位：0小时，1天，2周，3月，4年");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.promote_amount</code>. 所需助力值
     */
    public final TableField<B2cFriendPromoteActivityRecord, BigDecimal> PROMOTE_AMOUNT = createField("promote_amount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "所需助力值");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.promote_times</code>. 所需助力次数
     */
    public final TableField<B2cFriendPromoteActivityRecord, UInteger> PROMOTE_TIMES = createField("promote_times", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "所需助力次数");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_duration</code>. 发起次数限制时长，0不限制
     */
    public final TableField<B2cFriendPromoteActivityRecord, UInteger> LAUNCH_LIMIT_DURATION = createField("launch_limit_duration", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "发起次数限制时长，0不限制");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_unit</code>. 发起次数限制时长单位：0天，1周，2月，3年
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> LAUNCH_LIMIT_UNIT = createField("launch_limit_unit", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "发起次数限制时长单位：0天，1周，2月，3年");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_times</code>. 发起限制次数，0不限制
     */
    public final TableField<B2cFriendPromoteActivityRecord, UByte> LAUNCH_LIMIT_TIMES = createField("launch_limit_times", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINTUNSIGNED)), this, "发起限制次数，0不限制");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.share_add_times</code>. 好友分享可获得助力次数
     */
    public final TableField<B2cFriendPromoteActivityRecord, UByte> SHARE_ADD_TIMES = createField("share_add_times", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINTUNSIGNED)), this, "好友分享可获得助力次数");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.promote_type</code>. 单次助力值类型：0平均，1随机
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> PROMOTE_TYPE = createField("promote_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "单次助力值类型：0平均，1随机");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.promote_condition</code>. 好友助力条件：0可不授权个人信息，1必须授权
     */
    public final TableField<B2cFriendPromoteActivityRecord, UByte> PROMOTE_CONDITION = createField("promote_condition", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINTUNSIGNED)), this, "好友助力条件：0可不授权个人信息，1必须授权");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.failed_send_type</code>. 助力失败赠送类型：0不赠送，1优惠券，2积分
     */
    public final TableField<B2cFriendPromoteActivityRecord, UByte> FAILED_SEND_TYPE = createField("failed_send_type", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINTUNSIGNED)), this, "助力失败赠送类型：0不赠送，1优惠券，2积分");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.failed_send_content</code>. 助力失败赠送内容
     */
    public final TableField<B2cFriendPromoteActivityRecord, Integer> FAILED_SEND_CONTENT = createField("failed_send_content", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "助力失败赠送内容");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.activity_share_type</code>. 助力活动分享样式类型：0默认样式，1自定义样式
     */
    public final TableField<B2cFriendPromoteActivityRecord, UByte> ACTIVITY_SHARE_TYPE = createField("activity_share_type", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINTUNSIGNED)), this, "助力活动分享样式类型：0默认样式，1自定义样式");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.custom_share_word</code>. 自定义分享样式文案
     */
    public final TableField<B2cFriendPromoteActivityRecord, String> CUSTOM_SHARE_WORD = createField("custom_share_word", org.jooq.impl.SQLDataType.VARCHAR(400).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定义分享样式文案");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.share_img_type</code>. 自定义分享图片类型：0首页截图，1自定义图片
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> SHARE_IMG_TYPE = createField("share_img_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "自定义分享图片类型：0首页截图，1自定义图片");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.custom_img_path</code>. 自定义分享样式图片路径
     */
    public final TableField<B2cFriendPromoteActivityRecord, String> CUSTOM_IMG_PATH = createField("custom_img_path", org.jooq.impl.SQLDataType.VARCHAR(100).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定义分享样式图片路径");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.is_block</code>. 活动状态：0未停用，1已停用
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> IS_BLOCK = createField("is_block", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "活动状态：0未停用，1已停用");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.del_flag</code>. 删除标识：0未删除，1已删除
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "删除标识：0未删除，1已删除");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.in_time</code>. 添加时间
     */
    public final TableField<B2cFriendPromoteActivityRecord, Timestamp> IN_TIME = createField("in_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "添加时间");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.up_time</code>. 更新时间
     */
    public final TableField<B2cFriendPromoteActivityRecord, Timestamp> UP_TIME = createField("up_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "更新时间");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.use_discount</code>. 是否可与会员卡折扣、优惠券叠加使用：0不可叠加，1可叠加
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> USE_DISCOUNT = createField("use_discount", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否可与会员卡折扣、优惠券叠加使用：0不可叠加，1可叠加");

    /**
     * The column <code>mini_shop_471752.b2c_friend_promote_activity.use_score</code>. 是否可使用积分抵扣部分金额：0不可抵扣，1可抵扣
     */
    public final TableField<B2cFriendPromoteActivityRecord, Byte> USE_SCORE = createField("use_score", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "是否可使用积分抵扣部分金额：0不可抵扣，1可抵扣");

    /**
     * Create a <code>mini_shop_471752.b2c_friend_promote_activity</code> table reference
     */
    public B2cFriendPromoteActivity() {
        this(DSL.name("b2c_friend_promote_activity"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_friend_promote_activity</code> table reference
     */
    public B2cFriendPromoteActivity(String alias) {
        this(DSL.name(alias), B2C_FRIEND_PROMOTE_ACTIVITY);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_friend_promote_activity</code> table reference
     */
    public B2cFriendPromoteActivity(Name alias) {
        this(alias, B2C_FRIEND_PROMOTE_ACTIVITY);
    }

    private B2cFriendPromoteActivity(Name alias, Table<B2cFriendPromoteActivityRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cFriendPromoteActivity(Name alias, Table<B2cFriendPromoteActivityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cFriendPromoteActivity(Table<O> child, ForeignKey<O, B2cFriendPromoteActivityRecord> key) {
        super(child, key, B2C_FRIEND_PROMOTE_ACTIVITY);
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
        return Arrays.<Index>asList(Indexes.B2C_FRIEND_PROMOTE_ACTIVITY_ACT_CODE, Indexes.B2C_FRIEND_PROMOTE_ACTIVITY_ACT_NAME, Indexes.B2C_FRIEND_PROMOTE_ACTIVITY_PRIMARY, Indexes.B2C_FRIEND_PROMOTE_ACTIVITY_SHOP_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cFriendPromoteActivityRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_FRIEND_PROMOTE_ACTIVITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cFriendPromoteActivityRecord> getPrimaryKey() {
        return Keys.KEY_B2C_FRIEND_PROMOTE_ACTIVITY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cFriendPromoteActivityRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cFriendPromoteActivityRecord>>asList(Keys.KEY_B2C_FRIEND_PROMOTE_ACTIVITY_PRIMARY, Keys.KEY_B2C_FRIEND_PROMOTE_ACTIVITY_ACT_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteActivity as(String alias) {
        return new B2cFriendPromoteActivity(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteActivity as(Name alias) {
        return new B2cFriendPromoteActivity(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cFriendPromoteActivity rename(String name) {
        return new B2cFriendPromoteActivity(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cFriendPromoteActivity rename(Name name) {
        return new B2cFriendPromoteActivity(name, null);
    }
}
