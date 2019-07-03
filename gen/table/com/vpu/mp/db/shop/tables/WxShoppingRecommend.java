/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.WxShoppingRecommendRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WxShoppingRecommend extends TableImpl<WxShoppingRecommendRecord> {

    private static final long serialVersionUID = -1607851056;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_wx_shopping_recommend</code>
     */
    public static final WxShoppingRecommend WX_SHOPPING_RECOMMEND = new WxShoppingRecommend();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WxShoppingRecommendRecord> getRecordType() {
        return WxShoppingRecommendRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_wx_shopping_recommend.id</code>.
     */
    public final TableField<WxShoppingRecommendRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_wx_shopping_recommend.user_id</code>.
     */
    public final TableField<WxShoppingRecommendRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_wx_shopping_recommend.goods_id</code>. 商品ID
     */
    public final TableField<WxShoppingRecommendRecord, Integer> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "商品ID");

    /**
     * The column <code>mini_shop_471752.b2c_wx_shopping_recommend.order_sn</code>. 订单orderSn
     */
    public final TableField<WxShoppingRecommendRecord, Integer> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.INTEGER, this, "订单orderSn");

    /**
     * The column <code>mini_shop_471752.b2c_wx_shopping_recommend.click_num</code>. 点击次数
     */
    public final TableField<WxShoppingRecommendRecord, Integer> CLICK_NUM = createField("click_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "点击次数");

    /**
     * The column <code>mini_shop_471752.b2c_wx_shopping_recommend.add_time</code>.
     */
    public final TableField<WxShoppingRecommendRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_wx_shopping_recommend</code> table reference
     */
    public WxShoppingRecommend() {
        this(DSL.name("b2c_wx_shopping_recommend"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_wx_shopping_recommend</code> table reference
     */
    public WxShoppingRecommend(String alias) {
        this(DSL.name(alias), WX_SHOPPING_RECOMMEND);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_wx_shopping_recommend</code> table reference
     */
    public WxShoppingRecommend(Name alias) {
        this(alias, WX_SHOPPING_RECOMMEND);
    }

    private WxShoppingRecommend(Name alias, Table<WxShoppingRecommendRecord> aliased) {
        this(alias, aliased, null);
    }

    private WxShoppingRecommend(Name alias, Table<WxShoppingRecommendRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> WxShoppingRecommend(Table<O> child, ForeignKey<O, WxShoppingRecommendRecord> key) {
        super(child, key, WX_SHOPPING_RECOMMEND);
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
        return Arrays.<Index>asList(Indexes.WX_SHOPPING_RECOMMEND_GOODS_ID, Indexes.WX_SHOPPING_RECOMMEND_PRIMARY, Indexes.WX_SHOPPING_RECOMMEND_USER_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<WxShoppingRecommendRecord, Integer> getIdentity() {
        return Keys.IDENTITY_WX_SHOPPING_RECOMMEND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WxShoppingRecommendRecord> getPrimaryKey() {
        return Keys.KEY_B2C_WX_SHOPPING_RECOMMEND_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WxShoppingRecommendRecord>> getKeys() {
        return Arrays.<UniqueKey<WxShoppingRecommendRecord>>asList(Keys.KEY_B2C_WX_SHOPPING_RECOMMEND_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxShoppingRecommend as(String alias) {
        return new WxShoppingRecommend(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WxShoppingRecommend as(Name alias) {
        return new WxShoppingRecommend(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WxShoppingRecommend rename(String name) {
        return new WxShoppingRecommend(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WxShoppingRecommend rename(Name name) {
        return new WxShoppingRecommend(name, null);
    }
}
