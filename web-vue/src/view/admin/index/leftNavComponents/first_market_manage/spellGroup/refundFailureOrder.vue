<!--
* 拼团退款失败订单页面
*
* @author:赵鑫
-->
<template>
    <div class="refundFailureOrder">
        <wrapper class="refundFailureOrder_main">
            <ul>
                <li class="liItem">
                    <div class="liNav">
                        <span>商品名称</span>
                        <el-input
                                v-model="searchParams.goodsName"

                                placeholder="商品名称"
                                size="small"
                                class="inputWidth"
                        ></el-input>
                    </div>
                    <div class="liNav">
                        <span>订单号</span>
                        <el-input
                                v-model="searchParams.orderSn"

                                placeholder="订单号"
                                size="small"
                                class="inputWidth"
                        ></el-input>
                    </div>
                    <div class="liNav">
                        <span>订单状态</span>
                        <el-select
                                size="small"
                                v-model="searchParams.orderStatus"
                                placeholder="请选择订单状态"
                                class="inputWidth"
                                filterable
                        >
                            <el-option
                                    v-for="item in orderStatus"
                                    :key="item.value"
                                    :value="item.value"
                                    :label="item.label"
                            >
                            </el-option>
                        </el-select>
                    </div>
                </li>
                <li class="liItem">
                    <div class="liNav">
                        <span>收货人姓名</span>
                        <el-input
                                v-model="searchParams.consignee"
                                placeholder="收货人姓名"
                                size="small"
                               lass="inputWidth"
                        ></el-input
                        >
                    </div>
                    <div class="liNav">
                        <span>收货人手机号</span>
                        <el-input
                                v-model="searchParams.mobile"
                                placeholder="收货人手机号"
                                size="small"
                                class="inputWidth"
                        ></el-input
                        >
                    </div>
                </li>
                <li class="liItem">
                    <div class="liNav date">
                        <span>下单时间</span>
                        <el-date-picker
                                v-model="orderTime"
                                class="pickerWidth"
                                size="small"
                                type="datetimerange"
                                range-separator="至"
                                start-placeholder="下单时间"
                                end-placeholder="下单时间"
                        >
                        </el-date-picker>
                    </div>
                    <div
                            class="liNav"
                            style="margin-left:180px"
                    >
                        <span>配送方式</span>
                        <el-select
                                v-model="searchParams.deliverType"
                                size="small"
                                class="inputWidth"
                                filterable
                        >
                            <el-option
                                    label="全部"
                                    :value="-1"
                            ></el-option>
                            <el-option
                                    label="快递"
                                    :value="1"
                            ></el-option>
                            <el-option
                                    label="自提"
                                    :value="2"
                            ></el-option>
                        </el-select>
                    </div>
                </li>
            </ul>
            <ul
                    class="uls"
                    v-if='!arrorFlag'
            >
                <li class="liItem">
                    <div class="liNav">
                        <span>买家昵称</span>
                        <el-input
                                v-model="searchParams.userName"
                                placeholder="买家昵称"
                                size="small"
                                class="inputWidth"
                        ></el-input>
                    </div>
                    <div class="liNav">
                        <span>买家来源</span>
                        <el-select
                                v-model="searchParams.source"
                                size="small"
                                placeholder="请选择买家来源"
                                class="inputWidth"
                        >
                            <el-option
                                    v-for="item in sourceList"
                                    :key="item.value"
                                    :value="item.value"
                                    :label="item.label"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div class="liNav">
                        <span>核销码</span>
                        <el-input
                                v-model="searchParams.verifyCode"
                                placeholder="核销码"
                                size="small"
                                class="inputWidth"
                        ></el-input>
                    </div>
                </li>
                <li class="liItem">
                    <div class="liNav">
                        <span>门店</span>
                        <el-select
                                size="small"
                                v-model="searchParams.storeId"
                                placeholder="请选择门店"
                                class="inputWidth"
                        >
                            <el-option
                                    v-for="item in storeList"
                                    :key="item.value"
                                    :value="item.value"
                                    :label="item.label"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div class="liNav">
                        <span>标签</span>
                        <el-select
                                v-model="searchParams.tagIds"
                                multiple
                                filterable
                                allow-create
                                default-first-option
                                size="small"
                                multiple-limit=3
                                placeholder="最多选择3个标签"
                                class="inputWidth"
                        >
                            <el-option
                                    v-for="item in tagList"
                                    :key="item.value"
                                    :value="item.value"
                                    :label="item.label"
                            >
                            </el-option>
                        </el-select>
                    </div>
                </li>
                <li class="liItem">
                    <div class="liNav date">
                        <span>完成时间</span>
                        <el-date-picker
                                v-model="completeTime"
                                class="pickerWidth"
                                size="small"
                                type="datetimerange"
                                range-separator="至"
                                start-placeholder="完成时间"
                                end-placeholder="完成时间"
                        >
                        </el-date-picker>
                    </div>
                    <div class="liNav address">
                        <span>收货地址</span>
                        <areaLinkage/>
                    </div>
                </li>
            </ul>
            <ul>
                <li class="liItem">
                    <div class="liNav">
                        <span>支付方式</span>
                        <el-select
                                size="small"
                                v-model="searchParams.paymentType"
                                placeholder="请选择支付方式"
                                class="inputWidth"
                        >
                            <el-option
                                    v-for="item in paymentType"
                                    :key="item.value"
                                    :value="item.value"
                                    :label="item.label"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div class="liNav">
                        <span>规格编码</span>
                        <el-input
                                v-model="searchParams.specCode"
                                placeholder="规格编码"
                                size="small"
                                class="inputWidth"
                        ></el-input>
                    </div>
                </li>
                <li class="liItem">
                    <div class="liNav">
                        <span>订单来源</span>
                        <el-select
                                size="small"
                                v-model="orderNumber"
                                placeholder="请选择订单来源"
                                class="inputWidth"

                        >
                            <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :value="item.value"
                                    :label="item.label"
                            >
                            </el-option>
                        </el-select>
                    </div>
                    <div class="liNav">
                        <el-button
                                type="primary"
                                size="small"
                                @click="initDataList"
                        >筛选
                        </el-button>
                        <el-button size="small  ">导出表格</el-button>
                    </div>
                </li>
            </ul>

            <ul class="arrowUl">
                <li>
                    <div @click="handleToChangeArror()">
                        <div
                                v-if="arrorFlag"
                                style="color:rgb(90, 139, 255);cursor:pointer"
                        >{{$t('membershipIntroduction.more')}}&nbsp;<img :src="ArrowArr[0].img_1"></div>
                        <div
                                v-if="!arrorFlag"
                                style="color:rgb(90, 139, 255);cursor:pointer"
                        >{{$t('membershipIntroduction.retract')}}&nbsp;<img :src="ArrowArr[1].img_2"></div>
                    </div>
                </li>
            </ul>
        </wrapper>

        <wrapper>
            <div class="table_box">
                <table>
                    <thead>
                    <tr>
                        <th width="300px">商品</th>
                        <th width="10%">货号</th>
                        <th width="10%">单价</th>
                        <th width="10%">数量</th>
                        <th width="10%">收货人</th>
                        <th>下单时间</th>
                        <th width="10%">订单状态</th>
                        <th width="10%">支付金额</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="8"></td>
                    </tr>
                    </tbody>
                    <template v-for="orderItem in orderList">
                        <tbody
                                :key="orderItem.orderId"
                                class="hasborder"
                        >
                        <tr class="order-tb-head">
                            <td colspan="8">
                                <div class="tb-head_box">
                                    <div class="left">
                                        <span>订单号：{{orderItem.orderSn}}</span>
                                        <span>支付方式：<i class="el-icon-shopping-cart-full"></i></span>
                                        <span>配送方式：{{orderItem.deliverType === 0 ? '快递' : '自提'}}</span>
                                        <span>订单类型：普通订单</span>
                                    </div>
                                    <div class="right">
                                        <span class="icon_collect"><i class="el-icon-star-off"></i></span>
                                        <span>添加备注</span>
                                        <span @click="seeDetails(orderItem.orderId)">查看详情</span>
                                        <span>查看评价</span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <template v-for="(goodsItem,index) in orderItem.goods">
                            <tr
                                    class="order-tb-body"
                                    :key="index"
                            >
                                <td>
                                    <div class="goods_info">
                                        <img
                                                :src="$imageHost+'/image/admin/icon_jia.png'"
                                                alt=""
                                        >
                                        <div class="right_info">
                                            <div class="goods_name">{{goodsItem.goodsName}}</div>
                                            <div class="goods_spec">{{goodsItem.goodsAttr}}</div>
                                        </div>
                                    </div>
                                </td>
                                <td>{{goodsItem.goodsSn}}</td>
                                <td>{{goodsItem.goodsPrice}}</td>
                                <td>{{goodsItem.goodsNumber}}</td>
                                <td
                                        v-if="index === 0"
                                        :rowspan="orderItem.goods.length"
                                >
                                    <p>{{orderItem.consignee}}</p>
                                    <p>{{orderItem.mobile}}</p>
                                </td>
                                <td
                                        v-if="index === 0"
                                        :rowspan="orderItem.goods.length"
                                >
                                    {{orderItem.createTime}}
                                </td>
                                <td
                                        v-if="index === 0"
                                        :rowspan="orderItem.goods.length"
                                >
                                    {{orderItem.orderStatus}}
                                </td>
                                <td
                                        v-if="index === 0"
                                        :rowspan="orderItem.goods.length"
                                >123
                                </td>
                            </tr>
                        </template>
                        </tbody>
                        <tbody :key="orderItem.orderId">
                        <tr>
                            <td colspan="8"></td>
                        </tr>
                        </tbody>
                    </template>

                </table>
                <pagination
                        :page-params.sync="pageParams"
                        @pagination="initDataList"
                />
            </div>

        </wrapper>
    </div>
</template>

<script>
import {refundFailOrderList} from '@/api/admin/marketManage/spellGroup.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'

export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    wrapper,
    areaLinkage
  },
  data () {
    return {
      orderNumber: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
      arrorFlag: true,
      ArrowArr: [
        {
          img_1: this.$imageHost + '/image/admin/show_more.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/hid_some.png'
        }
      ],
      pageParams: {},
      searchParams: {
        currentPage: null,
        pageRows: null,
        searchType: 0,
        pinStatus: [],
        goodsName: '',
        orderSn: '',
        orderStatus: [],
        goodsType: null,
        consignee: '',
        mobile: '',
        createTimeStart: null,
        createTimeEnd: null,
        deliverType: null,
        paymentType: null,
        userName: '',
        source: null,
        tagIds: [],
        storeId: null,
        verifyCode: '',
        specCode: '',
        finishedTimeStart: null,
        finishedTimeEnd: null,
        countryCode: null,
        provinceCode: null,
        cityCode: null,
        districtCode: null
      },
      orderTime: null,
      completeTime: null,
      orderStatus: [
        {value: '-1', label: '全部订单'},
        {value: '1', label: '待付款'},
        {value: '2', label: '订单取消'},
        {value: '3', label: '订单关闭'},
        {value: '4', label: '待发货/待核销'},
        {value: '5', label: '已发货'},
        {value: '6', label: '已收货/已自提'},
        {value: '7', label: '订单完成'},
        {value: '8', label: '退货中'},
        {value: '9', label: '退货完成'},
        {value: '10', label: '退款中'},
        {value: '11', label: '退款完成'},
        {value: '13', label: '拼团中'},
        {value: '14', label: '已成团'},
        {value: '15', label: '送礼完成'}
      ],
      goodsType: [
        {value: -1, label: '全部'},
        {value: 1, label: '普通订单'},
        {value: 2, label: '拼团订单'},
        {value: 3, label: '返利订单'},
        {value: 4, label: '砍价订单'},
        {value: 5, label: '积分兑换订单'},
        {value: 6, label: '秒杀订单'},
        {value: 7, label: '限时降价订单'},
        {value: 8, label: '首单特惠订单'},
        {value: 9, label: '加价购订单'},
        {value: 10, label: '拼团抽奖订单'},
        {value: 11, label: '一口价订单'},
        {value: 12, label: '定金膨胀订单'},
        {value: 13, label: '赠品订单'},
        {value: 14, label: '幸运抽奖订单'},
        {value: 15, label: '限次卡兑换订单'},
        {value: 16, label: '好友助力订单'},
        {value: 17, label: '满包邮'},
        {value: 18, label: '测评订单'},
        {value: 19, label: '送礼订单'},
        {value: 20, label: '代付订单'},
        {value: 21, label: '扫码购订单'}
      ],
      sourceList: [
        {value: -1, label: '全部'},
        {value: -2, label: '未获取'},
        {value: -3, label: '后台'}
      ],
      storeList: [
        {value: -1, label: '请选门店'},
        {value: 1, label: '牡丹园门店'},
        {value: 2, label: '西直门门店'},
        {value: 3, label: '永泰庄门店'}
      ],
      tagList: [
        {value: 1, label: '年轻人'},
        {value: 2, label: '中年人'},
        {value: 3, label: '老年人'},
        {value: 4, label: '儿童'}
      ],
      paymentType: [
        {value: -1, label: '全部'},
        {value: 1, label: '微信支付'},
        {value: 2, label: '余额支付'},
        {value: 3, label: '积分支付'},
        {value: 4, label: '积分兑换'},
        {value: 5, label: '货到付款'},
        {value: 6, label: '活动奖品'}
      ],
      tabsOrderStatus: [
        {value: '-1', label: '全部'},
        {value: '1', label: '待付款'},
        {value: '4', label: '待发货/待核销'},
        {value: '5', label: '已发货'},
        {value: '6', label: '已收货/已核销'},
        {value: '7', label: '已完成'},
        {value: '8', label: '退货退款中'},
        {value: '3', label: '已关闭'},
        {value: '16', label: '追星订单'}
      ],
      orderList: []
    }
  },
  mounted () {
    // 初始化
    this.initDataList()
  },
  methods: {
    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    initDataList (data) {
      this.searchParams.pageRows = this.pageParams.pageRows
      this.searchParams.currentPage = this.pageParams.currentPage
      refundFailOrderList(this.searchParams).then(res => {
        this.orderList = res.content.dataList
        this.pageParams = res.content.page
        console.log(res)
      })
    },
    handleAreaData (data) {
      this.searchParams.provinceCode = data.province
      this.searchParams.cityCode = data.city
      this.searchParams.districtCode = data.district
    },
    seeDetails (orderId) {
      console.log(orderId)
      this.$router.push({
        name: 'orderInfo',
        query: {
          id: orderId
        }
      })
    }

  }
}

</script>
<style lang="scss" scoped>
    .refundFailureOrder {
        font-size: 14px;

        .refundFailureOrder_main {
            padding: 10px 25px;

            ul {
                .liItem {
                    display: flex;

                    .liNav {
                        display: flex;
                        width: 340px;
                        margin-bottom: 20px;

                        span {
                            display: block;
                            width: 85px;
                            line-height: 30px;
                            height: 30px;
                            text-align: right;
                            color: #333;
                            margin-right: 25px;
                        }

                        .inputWidth {
                            width: 150px;
                        }
                    }

                    .date {
                        width: 500px;
                    }

                    .address {
                        width: 500px;
                        margin-left: 100px;

                        span {
                            width: 85px;
                        }

                        .areaLinkage {
                            width: 415px;

                            /deep/ .el-select {
                                margin-right: 5px;
                            }
                        }
                    }
                }
            }

            .arrowUl {
                margin-top: 15px;
                display: flex;
                justify-content: center;
            }
        }

        /deep/ .tableClss th {
            background-color: #f5f5f5;
            border: none;
            height: 36px;
            color: #000;
            padding: 8px 10px;
        }

        .table_list {
            position: relative;
        }
    }

    .content {
        padding: 10px;

        .main {
            .search_box {
                background-color: #fff;
                padding: 10px 10px 0 0;

                .filters {
                    display: flex;
                    line-height: 32px;
                    flex-wrap: wrap;
                    justify-content: space-between;
                    // max-width: 1226px;
                    .filters_item {
                        display: flex;
                        max-width: 480px;
                        min-width: 320px;
                        margin-left: 15px;
                        margin-bottom: 10px;

                        /deep/ .areaLinkage {
                            .el-select {
                                margin-left: 10px;

                                &:first-of-type {
                                    margin-left: 0;
                                }
                            }
                        }

                        > span {
                            min-width: 100px;
                            font-size: 14px;
                            text-align: right;
                        }
                    }
                }
            }

            .table_box {
                margin-top: 10px;
                background-color: #fff;
                padding: 10px;

                .wait_num {
                    position: relative;
                    top: -7px;
                    right: 0;
                    border-radius: 10px;
                    background: #ff9d0e;
                    color: #fff;
                    line-height: 1;
                    font-size: 11px;
                    text-align: center;
                    padding: 2px 5px;
                }

                table {
                    width: 100%;
                    margin-top: 15px;

                    &:first-of-type {
                        margin-top: 0;
                    }

                    > thead {
                        > tr {
                            background: #f5f5f5;

                            > th {
                                text-align: center;
                                padding: 8px 0;
                                font-size: 14px;
                                color: #333;
                            }
                        }
                    }

                    > tbody {
                        .order-tb-head {
                            background: #f5f5f5;
                        }

                        > tr {
                            > td {
                                text-align: center;
                                font-size: 14px;
                                padding: 8px 10px;
                                word-break: break-all;

                                .tb-head_box {
                                    display: flex;
                                    line-height: 24px;
                                    padding: 0 10px;

                                    .left {
                                        flex: 1;
                                        display: flex;
                                        font-size: 14px;
                                        color: #666;
                                        justify-content: space-between;
                                    }

                                    .right {
                                        width: 265px;
                                        margin-left: 200px;
                                        display: flex;
                                        justify-content: space-between;
                                        color: #409eff;
                                        font-size: 14px;

                                        > span {
                                            cursor: pointer;
                                        }

                                        .icon_collect {
                                            font-size: 20px;

                                            .el-icon-star-off {
                                                color: #666;
                                            }

                                            .el-icon-star-on {
                                                color: red;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        .order-tb-body {
                            td {
                                vertical-align: middle;
                                color: #666;
                            }

                            .goods_info {
                                display: flex;
                                padding: 8px 10px;
                                border-bottom: 1px solid #eee;

                                &:last-of-type {
                                    border-bottom: 0;
                                }

                                > img {
                                    width: 60px;
                                    height: 60px;
                                    margin-right: 5px;
                                }

                                > .right_info {
                                    flex: 1;
                                    display: flex;
                                    flex-direction: column;
                                    text-align: left;
                                    justify-content: space-between;

                                    .goods_name {
                                        overflow: hidden;
                                        text-overflow: ellipsis;
                                        display: -webkit-box;
                                        -webkit-line-clamp: 2;
                                        overflow: hidden;
                                        /*! autoprefixer: off */
                                        -webkit-box-orient: vertical;
                                        text-align: left;
                                    }
                                }
                            }

                            .goods_sn,
                            .goods_number,
                            .goods_price {
                                display: block;
                                border-bottom: 1px solid #eee;
                                word-break: break-all;
                                height: 77px;
                                position: relative;

                                > span {
                                    overflow: hidden;
                                    text-overflow: ellipsis;
                                    display: -webkit-box;
                                    -webkit-line-clamp: 4;
                                    overflow: hidden;
                                    /*! autoprefixer: off */
                                    -webkit-box-orient: vertical;
                                    position: absolute;
                                    top: 50%;
                                    left: 50%;
                                    transform: translate(-50%, -50%);
                                }

                                &:last-of-type {
                                    border-bottom: 0;
                                }
                            }
                        }
                    }

                    .hasborder {
                        border: 1px solid #eee;

                        td {
                            border: 1px solid #eee;
                        }
                    }
                }
            }

            .search_button_box {
                background-color: #fff;
                text-align: center;
                line-height: 55px;

                > span {
                    color: #409eff;
                    font-size: 14px;
                    margin-left: 150px;
                    cursor: pointer;
                }

                > .button_box {
                    float: right;
                    margin-right: 10px;
                }
            }
        }

        .default_input {
            width: 180px;
        }
    }

    .table_box {
        margin-top: 10px;
        background-color: #fff;
        padding: 10px;

        .wait_num {
            position: relative;
            top: -7px;
            right: 0;
            border-radius: 10px;
            background: #ff9d0e;
            color: #fff;
            line-height: 1;
            font-size: 11px;
            text-align: center;
            padding: 2px 5px;
        }

        table {
            width: 100%;
            margin-top: 15px;

            &:first-of-type {
                margin-top: 0;
            }

            > thead {
                > tr {
                    background: #f5f5f5;

                    > th {
                        text-align: center;
                        padding: 8px 0;
                        font-size: 14px;
                        color: #333;
                    }
                }
            }

            > tbody {
                .order-tb-head {
                    background: #f5f5f5;
                }

                > tr {
                    > td {
                        text-align: center;
                        font-size: 14px;
                        padding: 8px 10px;
                        word-break: break-all;

                        .tb-head_box {
                            display: flex;
                            line-height: 24px;
                            padding: 0 10px;

                            .left {
                                flex: 1;
                                display: flex;
                                font-size: 14px;
                                color: #666;
                                justify-content: space-between;
                            }

                            .right {
                                width: 265px;
                                margin-left: 200px;
                                display: flex;
                                justify-content: space-between;
                                color: #409eff;
                                font-size: 14px;

                                > span {
                                    cursor: pointer;
                                }

                                .icon_collect {
                                    font-size: 20px;

                                    .el-icon-star-off {
                                        color: #666;
                                    }

                                    .el-icon-star-on {
                                        color: red;
                                    }
                                }
                            }
                        }
                    }
                }

                .order-tb-body {
                    td {
                        vertical-align: middle;
                        color: #666;
                    }

                    .goods_info {
                        display: flex;
                        padding: 8px 10px;
                        border-bottom: 1px solid #eee;

                        &:last-of-type {
                            border-bottom: 0;
                        }

                        > img {
                            width: 60px;
                            height: 60px;
                            margin-right: 5px;
                        }

                        > .right_info {
                            flex: 1;
                            display: flex;
                            flex-direction: column;
                            text-align: left;
                            justify-content: space-between;

                            .goods_name {
                                overflow: hidden;
                                text-overflow: ellipsis;
                                display: -webkit-box;
                                -webkit-line-clamp: 2;
                                overflow: hidden;
                                /*! autoprefixer: off */
                                -webkit-box-orient: vertical;
                                text-align: left;
                            }
                        }
                    }

                    .goods_sn,
                    .goods_number,
                    .goods_price {
                        display: block;
                        border-bottom: 1px solid #eee;
                        word-break: break-all;
                        height: 77px;
                        position: relative;

                        > span {
                            overflow: hidden;
                            text-overflow: ellipsis;
                            display: -webkit-box;
                            -webkit-line-clamp: 4;
                            overflow: hidden;
                            /*! autoprefixer: off */
                            -webkit-box-orient: vertical;
                            position: absolute;
                            top: 50%;
                            left: 50%;
                            transform: translate(-50%, -50%);
                        }

                        &:last-of-type {
                            border-bottom: 0;
                        }
                    }
                }
            }

            .hasborder {
                border: 1px solid #eee;

                td {
                    border: 1px solid #eee;
                }
            }
        }
    }
</style>
