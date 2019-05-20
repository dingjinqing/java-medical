/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_1;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;

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
public class Lottery extends TableImpl<LotteryRecord> {

    private static final long serialVersionUID = -1334799561;

    /**
     * The reference instance of <code>mini_shop_1.b2c_lottery</code>
     */
    public static final Lottery LOTTERY = new Lottery();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LotteryRecord> getRecordType() {
        return LotteryRecord.class;
    }

    /**
     * The column <code>mini_shop_1.b2c_lottery.id</code>.
     */
    public final TableField<LotteryRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_1.b2c_lottery.lottery_name</code>. 抽奖名称
     */
    public final TableField<LotteryRecord, String> LOTTERY_NAME = createField("lottery_name", org.jooq.impl.SQLDataType.VARCHAR(120).nullable(false), this, "抽奖名称");

    /**
     * The column <code>mini_shop_1.b2c_lottery.start_time</code>. 开始时间
     */
    public final TableField<LotteryRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "开始时间");

    /**
     * The column <code>mini_shop_1.b2c_lottery.end_time</code>. 结束时间
     */
    public final TableField<LotteryRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "结束时间");

    /**
     * The column <code>mini_shop_1.b2c_lottery.lottery_explain</code>. 抽奖说明
     */
    public final TableField<LotteryRecord, String> LOTTERY_EXPLAIN = createField("lottery_explain", org.jooq.impl.SQLDataType.VARCHAR(299).nullable(false), this, "抽奖说明");

    /**
     * The column <code>mini_shop_1.b2c_lottery.free_chances</code>. 免费抽奖次数
     */
    public final TableField<LotteryRecord, Integer> FREE_CHANCES = createField("free_chances", org.jooq.impl.SQLDataType.INTEGER, this, "免费抽奖次数");

    /**
     * The column <code>mini_shop_1.b2c_lottery.can_share</code>. 是否分享获得次数
     */
    public final TableField<LotteryRecord, Byte> CAN_SHARE = createField("can_share", org.jooq.impl.SQLDataType.TINYINT, this, "是否分享获得次数");

    /**
     * The column <code>mini_shop_1.b2c_lottery.share_chances</code>. 分享最多获得次数
     */
    public final TableField<LotteryRecord, Integer> SHARE_CHANCES = createField("share_chances", org.jooq.impl.SQLDataType.INTEGER, this, "分享最多获得次数");

    /**
     * The column <code>mini_shop_1.b2c_lottery.can_use_score</code>. 是否可以积分抽奖
     */
    public final TableField<LotteryRecord, Byte> CAN_USE_SCORE = createField("can_use_score", org.jooq.impl.SQLDataType.TINYINT, this, "是否可以积分抽奖");

    /**
     * The column <code>mini_shop_1.b2c_lottery.score_per_chance</code>. 抽奖一次使用积分
     */
    public final TableField<LotteryRecord, Integer> SCORE_PER_CHANCE = createField("score_per_chance", org.jooq.impl.SQLDataType.INTEGER, this, "抽奖一次使用积分");

    /**
     * The column <code>mini_shop_1.b2c_lottery.score_chances</code>. 积分最多抽奖次数
     */
    public final TableField<LotteryRecord, Integer> SCORE_CHANCES = createField("score_chances", org.jooq.impl.SQLDataType.INTEGER, this, "积分最多抽奖次数");

    /**
     * The column <code>mini_shop_1.b2c_lottery.no_award_score</code>. 未中奖奖励积分
     */
    public final TableField<LotteryRecord, Integer> NO_AWARD_SCORE = createField("no_award_score", org.jooq.impl.SQLDataType.INTEGER, this, "未中奖奖励积分");

    /**
     * The column <code>mini_shop_1.b2c_lottery.no_award_image</code>. 未中奖图片
     */
    public final TableField<LotteryRecord, String> NO_AWARD_IMAGE = createField("no_award_image", org.jooq.impl.SQLDataType.VARCHAR(199), this, "未中奖图片");

    /**
     * The column <code>mini_shop_1.b2c_lottery.no_award_icon</code>. 未中奖提示
     */
    public final TableField<LotteryRecord, String> NO_AWARD_ICON = createField("no_award_icon", org.jooq.impl.SQLDataType.VARCHAR(20), this, "未中奖提示");

    /**
     * The column <code>mini_shop_1.b2c_lottery.first_award</code>. 一等奖设置（json）
     */
    public final TableField<LotteryRecord, String> FIRST_AWARD = createField("first_award", org.jooq.impl.SQLDataType.VARCHAR(500), this, "一等奖设置（json）");

    /**
     * The column <code>mini_shop_1.b2c_lottery.first_award_times</code>. 中奖数
     */
    public final TableField<LotteryRecord, Integer> FIRST_AWARD_TIMES = createField("first_award_times", org.jooq.impl.SQLDataType.INTEGER, this, "中奖数");

    /**
     * The column <code>mini_shop_1.b2c_lottery.second_award</code>. 二等奖设置（json）
     */
    public final TableField<LotteryRecord, String> SECOND_AWARD = createField("second_award", org.jooq.impl.SQLDataType.VARCHAR(500), this, "二等奖设置（json）");

    /**
     * The column <code>mini_shop_1.b2c_lottery.second_award_times</code>. 中奖数
     */
    public final TableField<LotteryRecord, Integer> SECOND_AWARD_TIMES = createField("second_award_times", org.jooq.impl.SQLDataType.INTEGER, this, "中奖数");

    /**
     * The column <code>mini_shop_1.b2c_lottery.third_award</code>. 三等奖设置（json）
     */
    public final TableField<LotteryRecord, String> THIRD_AWARD = createField("third_award", org.jooq.impl.SQLDataType.VARCHAR(500), this, "三等奖设置（json）");

    /**
     * The column <code>mini_shop_1.b2c_lottery.third_award_times</code>. 中奖数
     */
    public final TableField<LotteryRecord, Integer> THIRD_AWARD_TIMES = createField("third_award_times", org.jooq.impl.SQLDataType.INTEGER, this, "中奖数");

    /**
     * The column <code>mini_shop_1.b2c_lottery.fourth_award</code>. 四等奖设置（json）
     */
    public final TableField<LotteryRecord, String> FOURTH_AWARD = createField("fourth_award", org.jooq.impl.SQLDataType.VARCHAR(500), this, "四等奖设置（json）");

    /**
     * The column <code>mini_shop_1.b2c_lottery.fourth_award_times</code>. 中奖数
     */
    public final TableField<LotteryRecord, Integer> FOURTH_AWARD_TIMES = createField("fourth_award_times", org.jooq.impl.SQLDataType.INTEGER, this, "中奖数");

    /**
     * The column <code>mini_shop_1.b2c_lottery.add_time</code>. 添加时间
     */
    public final TableField<LotteryRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "添加时间");

    /**
     * The column <code>mini_shop_1.b2c_lottery.status</code>. 状态：1停用
     */
    public final TableField<LotteryRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "状态：1停用");

    /**
     * The column <code>mini_shop_1.b2c_lottery.del_flag</code>. 1删除
     */
    public final TableField<LotteryRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "1删除");

    /**
     * The column <code>mini_shop_1.b2c_lottery.update_time</code>. 更新时间
     */
    public final TableField<LotteryRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>mini_shop_1.b2c_lottery</code> table reference
     */
    public Lottery() {
        this(DSL.name("b2c_lottery"), null);
    }

    /**
     * Create an aliased <code>mini_shop_1.b2c_lottery</code> table reference
     */
    public Lottery(String alias) {
        this(DSL.name(alias), LOTTERY);
    }

    /**
     * Create an aliased <code>mini_shop_1.b2c_lottery</code> table reference
     */
    public Lottery(Name alias) {
        this(alias, LOTTERY);
    }

    private Lottery(Name alias, Table<LotteryRecord> aliased) {
        this(alias, aliased, null);
    }

    private Lottery(Name alias, Table<LotteryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Lottery(Table<O> child, ForeignKey<O, LotteryRecord> key) {
        super(child, key, LOTTERY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniShop_1.MINI_SHOP_1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.LOTTERY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<LotteryRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_LOTTERY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LotteryRecord> getPrimaryKey() {
        return Keys.KEY_B2C_LOTTERY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LotteryRecord>> getKeys() {
        return Arrays.<UniqueKey<LotteryRecord>>asList(Keys.KEY_B2C_LOTTERY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lottery as(String alias) {
        return new Lottery(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lottery as(Name alias) {
        return new Lottery(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Lottery rename(String name) {
        return new Lottery(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Lottery rename(Name name) {
        return new Lottery(name, null);
    }
}
