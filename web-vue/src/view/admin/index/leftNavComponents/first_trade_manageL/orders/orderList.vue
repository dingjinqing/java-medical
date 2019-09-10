<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <span>商品名称：</span>
            <el-input
              v-model="searchParams.goodsName"
              placeholder="商品名称"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>订单号：</span>
            <el-input
              v-model="searchParams.orderSn"
              placeholder="订单号"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>订单状态：</span>
            <el-select
              v-model="searchParams.orderStatus"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in orderStatus"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>订单类型：</span>
            <el-select
              v-model="searchParams.goodsType"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in goodsType"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>收货人姓名：</span>
            <el-input
              v-model="searchParams.consignee"
              placeholder="收货人姓名"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>收货人手机号：</span>
            <el-input
              v-model="searchParams.mobile"
              placeholder="收货人手机号"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>配送方式：</span>
            <el-select
              v-model="searchParams.deliverType"
              size="small"
              class="default_input"
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
          <div class="filters_item">
            <span>买家昵称：</span>
            <el-input
              v-model="searchParams.userName"
              placeholder="买家昵称"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>买家来源：</span>
            <el-select
              v-model="searchParams.source"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in sourceList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>核销码：</span>
            <el-input
              v-model="searchParams.verifyCode"
              placeholder="核销码"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>门店：</span>
            <el-select
              v-model="searchParams.storeId"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in storeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>支付方式：</span>
            <el-select
              v-model="searchParams.paymentType"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in paymentType"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>标签：</span>
            <el-select
              v-model="searchParams.tagIds"
              multiple
              filterable
              allow-create
              default-first-option
              placeholder="最多选择3个标签"
              :multiple-limit="3"
              size="small"
              style="width:auto"
            >
              <el-option
                v-for="item in tagList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>收货地址：</span>
            <areaLinkage
              @areaData="handleAreaData"
              style="width:365px;"
            />
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>规格编码：</span>
            <el-input
              v-model="searchParams.specCode"
              placeholder="规格编码"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>下单时间：</span>
            <el-date-picker
              v-model="orderTime"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              size="small"
            >
            </el-date-picker>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>完成时间：</span>
            <el-date-picker
              v-model="completeTime"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              size="small"
            >
            </el-date-picker>
          </div>
        </div>
      </div>

      <div class="search_button_box">
        <span @click="moreFilters = !moreFilters">{{moreFilters ? '收起' : '更多'}}</span>
        <div class="button_box">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button
            type="default"
            size="small"
          >导出列表</el-button>
        </div>
      </div>
      <div class="table_box">
        <el-tabs
          v-model="searchParams.orderStatus"
          @tab-click="handleClick"
        >
          <template v-for="item in tabsOrderStatus">
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-if="item.value === '4'"
            >
              <span slot="label">
                <span>待发货<span class="wait_num">0</span></span>/<span>待核销<span class="wait_num">0</span></span>
              </span>
            </el-tab-pane>
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-else-if="item.value === '8'"
            >
              <span slot="label">
                <span>退货/退款中<span class="wait_num">0</span></span>
              </span>
            </el-tab-pane>
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-else
            >
            </el-tab-pane>
          </template>

        </el-tabs>
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
                  >123</td>
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
    </div>
  </div>
</template>
<script>
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue')
  },
  data () {
    return {
      moreFilters: false,
      pageParams: {
        'totalRows': 4,
        'currentPage': 1,
        'firstPage': 1,
        'prePage': 1,
        'nextPage': 1,
        'lastPage': 1,
        'pageRows': 20,
        'pageCount': 1
      },
      searchParams: {
        searchType: 0,
        pinStatus: null,
        goodsName: '',
        orderSn: '',
        orderStatus: '-1',
        goodsType: -1,
        consignee: '',
        mobile: '',
        createTimeStart: null,
        createTimeEnd: null,
        deliverType: -1,
        paymentType: -1,
        userName: '',
        source: -1,
        tagIds: [],
        storeId: -1,
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
        { value: '-1', label: '全部订单' },
        { value: '1', label: '待付款' },
        { value: '2', label: '订单取消' },
        { value: '3', label: '订单关闭' },
        { value: '4', label: '待发货/待核销' },
        { value: '5', label: '已发货' },
        { value: '6', label: '已收货/已自提' },
        { value: '7', label: '订单完成' },
        { value: '8', label: '退货中' },
        { value: '9', label: '退货完成' },
        { value: '10', label: '退款中' },
        { value: '11', label: '退款完成' },
        { value: '13', label: '拼团中' },
        { value: '14', label: '已成团' },
        { value: '15', label: '送礼完成' }
      ],
      goodsType: [
        { value: -1, label: '全部' },
        { value: 1, label: '普通订单' },
        { value: 2, label: '拼团订单' },
        { value: 3, label: '返利订单' },
        { value: 4, label: '砍价订单' },
        { value: 5, label: '积分兑换订单' },
        { value: 6, label: '秒杀订单' },
        { value: 7, label: '限时降价订单' },
        { value: 8, label: '首单特惠订单' },
        { value: 9, label: '加价购订单' },
        { value: 10, label: '拼团抽奖订单' },
        { value: 11, label: '一口价订单' },
        { value: 12, label: '定金膨胀订单' },
        { value: 13, label: '赠品订单' },
        { value: 14, label: '幸运抽奖订单' },
        { value: 15, label: '限次卡兑换订单' },
        { value: 16, label: '好友助力订单' },
        { value: 17, label: '满包邮' },
        { value: 18, label: '测评订单' },
        { value: 19, label: '送礼订单' },
        { value: 20, label: '代付订单' },
        { value: 21, label: '扫码购订单' }
      ],
      sourceList: [
        { value: -1, label: '全部' },
        { value: -2, label: '未获取' },
        { value: -3, label: '后台' }
      ],
      storeList: [
        { value: -1, label: '请选门店' },
        { value: 1, label: '牡丹园门店' },
        { value: 2, label: '西直门门店' },
        { value: 3, label: '永泰庄门店' }
      ],
      tagList: [
        { value: 1, label: '年轻人年轻人年轻人人' },
        { value: 2, label: '中年人中年人中年人人' },
        { value: 3, label: '老年人' },
        { value: 4, label: '儿童' }
      ],
      paymentType: [
        { value: -1, label: '全部' },
        { value: 1, label: '微信支付' },
        { value: 2, label: '余额支付' },
        { value: 3, label: '积分支付' },
        { value: 4, label: '积分兑换' },
        { value: 5, label: '货到付款' },
        { value: 6, label: '活动奖品' }
      ],
      tabsOrderStatus: [
        { value: '-1', label: '全部' },
        { value: '1', label: '待付款' },
        { value: '4', label: '待发货/待核销' },
        { value: '5', label: '已发货' },
        { value: '6', label: '已收货/已核销' },
        { value: '7', label: '已完成' },
        { value: '8', label: '退货退款中' },
        { value: '3', label: '已关闭' },
        { value: '16', label: '追星订单' }
      ],
      orderList: [
        {
          'orderId': 1000,
          'orderSn': 'P201900000000000000',
          'mainOrderSn': 'P201900000000000000',
          'goodsType': '0',
          'childOrders': [
            {
              'orderId': 1003,
              'orderSn': 'P201900000000000003',
              'mainOrderSn': 'P201900000000000000',
              'goodsType': '0',
              'childOrders': null,
              'goods': [
                {
                  'recId': null,
                  'orderId': 1003,
                  'orderSn': 'P201900000000000003',
                  'goodsId': 695,
                  'goodsSn': 'G101010694',
                  'goodsName': '首单限时优化-CJ222',
                  'goodsNumber': 1,
                  'goodsPrice': 55,
                  'goodsAttr': '颜色:黑色',
                  'productId': 4727,
                  'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
                  'sendNumber': null,
                  'discountedGoodsPrice': null
                },
                {
                  'recId': null,
                  'orderId': 1003,
                  'orderSn': 'P201900000000000003',
                  'goodsId': 695,
                  'goodsSn': 'G101010694',
                  'goodsName': '首单限时优化-CJ222',
                  'goodsNumber': 1,
                  'goodsPrice': 55,
                  'goodsAttr': '颜色:黑色',
                  'productId': 4727,
                  'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
                  'sendNumber': null,
                  'discountedGoodsPrice': null
                }
              ],
              'orderStatus': 10,
              'consignee': '测试520',
              'mobile': '12345678910',
              'payCode': 'balance',
              'deliverType': 0,
              'createTime': '2019-08-26 14:02:46',
              'shippingFee': 22,
              'moneyPaid': 0,
              'partShipFlag': 0,
              'refundStatus': 5
            }
          ],
          'goods': [
            {
              'recId': null,
              'orderId': 1000,
              'orderSn': 'P201900000000000000',
              'goodsId': 694,
              'goodsSn': 'G101010694G101010694G101010694G101010694G101010694G101010694G101010694G101010694G101010694',
              'goodsName': '首单限时优化-CJ111',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:白色',
              'productId': 4726,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': null,
              'discountedGoodsPrice': null
            }
          ],
          'orderStatus': 10,
          'consignee': '测试520',
          'mobile': '12345678910',
          'payCode': 'balance',
          'deliverType': 0,
          'createTime': '2019-08-26 14:02:46',
          'shippingFee': 22,
          'moneyPaid': 0,
          'partShipFlag': 0,
          'refundStatus': 5
        },
        {
          'orderId': 1001,
          'orderSn': 'P201900000000000001',
          'mainOrderSn': 'P201900000000000001',
          'goodsType': '0',
          'childOrders': null,
          'goods': [
            {
              'recId': null,
              'orderId': 1001,
              'orderSn': 'P201900000000000001',
              'goodsId': 694,
              'goodsSn': 'G101010694',
              'goodsName': '首单限时优化-CJ111',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:白色',
              'productId': 4726,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': null,
              'discountedGoodsPrice': null
            },
            {
              'recId': null,
              'orderId': 1001,
              'orderSn': 'P201900000000000001',
              'goodsId': 695,
              'goodsSn': 'G101010694',
              'goodsName': '首单限时优化-CJ222',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:黑色',
              'productId': 4727,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': null,
              'discountedGoodsPrice': null
            }
          ],
          'orderStatus': 10,
          'consignee': '测试520',
          'mobile': '12345678910',
          'payCode': 'balance',
          'deliverType': 0,
          'createTime': '2019-08-26 14:02:46',
          'shippingFee': 22,
          'moneyPaid': 0,
          'partShipFlag': 0,
          'refundStatus': 5
        },
        {
          'orderId': 1002,
          'orderSn': 'P201900000000000002',
          'mainOrderSn': 'P201900000000000002',
          'goodsType': '0',
          'childOrders': null,
          'goods': [
            {
              'recId': null,
              'orderId': 1002,
              'orderSn': 'P201900000000000002',
              'goodsId': 694,
              'goodsSn': 'G101010694',
              'goodsName': '首单限时优化-CJ111',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:白色',
              'productId': 4726,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': null,
              'discountedGoodsPrice': null
            },
            {
              'recId': null,
              'orderId': 1002,
              'orderSn': 'P201900000000000002',
              'goodsId': 695,
              'goodsSn': 'G101010694',
              'goodsName': '首单限时优化-CJ222',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:黑色',
              'productId': 4727,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': null,
              'discountedGoodsPrice': null
            }
          ],
          'orderStatus': 3,
          'consignee': '测试520',
          'mobile': '12345678910',
          'payCode': 'balance',
          'deliverType': 0,
          'createTime': '2019-08-26 14:02:46',
          'shippingFee': 22,
          'moneyPaid': 0,
          'partShipFlag': 0,
          'refundStatus': 4
        },
        {
          'orderId': 6751,
          'orderSn': 'P201908261402467301',
          'mainOrderSn': 'P201908261402467301',
          'goodsType': '0',
          'childOrders': null,
          'goods': [
            {
              'recId': null,
              'orderId': 6751,
              'orderSn': 'P201908261402467301',
              'goodsId': 694,
              'goodsSn': 'G101010694',
              'goodsName': '首单限时优化-CJ111',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:白色',
              'productId': 4726,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': null,
              'discountedGoodsPrice': null
            },
            {
              'recId': null,
              'orderId': 6751,
              'orderSn': 'P201908261402467301',
              'goodsId': 695,
              'goodsSn': 'G101010694',
              'goodsName': '首单限时优化-CJ222',
              'goodsNumber': 1,
              'goodsPrice': 55,
              'goodsAttr': '颜色:黑色',
              'productId': 4727,
              'goodsImg': 'http://mpdevimg2.weipubao.cn/upload/0/image/20190807/crop_0mf3fRQPXxNt1Kot.jpeg',
              'sendNumber': null,
              'discountedGoodsPrice': null
            }
          ],
          'orderStatus': 3,
          'consignee': '测试520',
          'mobile': '12345678910',
          'payCode': 'balance',
          'deliverType': 0,
          'createTime': '2019-08-26 14:02:46',
          'shippingFee': 22,
          'moneyPaid': 0,
          'partShipFlag': 0,
          'refundStatus': 4
        }
      ]
    }
  },
  methods: {
    handleClick (data) {
      console.log(data)
    },
    handleAreaData (data) {
      this.searchParams.provinceCode = data.province
      this.searchParams.cityCode = data.city
      this.searchParams.districtCode = data.district
    },
    initDataList (source) {

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
              font-weight: 600;
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
</style>
