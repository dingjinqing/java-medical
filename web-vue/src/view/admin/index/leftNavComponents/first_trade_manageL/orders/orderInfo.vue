<template>
  <div class="main">
    <div class="since-info">
      <div class="since-info-top">
        <span>{{$t('order.orderSn')}}：{{searchParam.orderSn}}</span>
        <span>{{$t('order.orderStatusText')}}：{{orderStatusMap.get(order.orderStatus)}}</span>
      </div>
      <div class="since-info-detail">
        <div class="order_info">
          <div class="title">{{$t('order.orderInfo')}}</div>
          <div class="item_box">
            <div class="item">{{$t('order.orderStatusText')}}： {{orderStatusMap.get(order.orderStatus)}}</div>
            <div class="item">{{$t('order.orderAmount')}}：
              <template v-if="goodsTypeArray.indexOf($t('order.goodsTypeList')[5][0].toString()) > -1">
                {{order.moneyPaid.toFixed(2)}}{{currencyPool[order.currency][lang][0]}} +
                {{order.scoreDiscount * monetToScore}}{{$t('order.score')}}
              </template>
              <template v-else>
                {{order.moneyPaid.toFixed(2)}}{{currencyPool[order.currency][lang][0]}}
              </template>
            </div>
            <div class="item">{{$t('order.orderTime')}}：{{order.createTime}}</div>
            <div class="item">{{$t('order.deliverTypeText')}}：{{deliverTypeMap.get(order.deliverType)}}</div>
            <div class="item">{{$t('order.paymentType')}}：</div>
            <div class="item">{{$t('order.orderSn')}}：{{order.orderSn}}</div>
            <div class="item">{{$t('order.userNameText')}}：{{order.username}}</div>
            <div class="item">{{$t('order.userMobileText')}}：{{order.userMobile}}</div>
            <div
              class="item"
              v-if="order.deliverType == 1"
            >
              {{$t('order.storeName')}}：{{order.storeName}}
            </div>
            <template v-if="order.verifierId > 0 ">
              <div class="item">
                {{$t('order.verifierName')}}：{{order.verifierName}}
              </div>
              <div class="item">
                {{$t('order.confirmTime')}}：{{order.confirmTime}}
              </div>
            </template>
            <template v-if="order.deliverType == 0 && order.orderStatus > 2">
              <template v-if="order.partShipFlag != 1 && order.orderStatus != 3">
                <template v-if="order.shippingTime != null">
                  <div class="item">
                    {{$t('order.shippingTimeText')}}：{{order.shippingTime}}
                  </div>
                </template>
              </template>
              <template v-else-if="order.partShipFlag == 1">
                <div class="item">
                  {{$t('order.partShipText')}}
                </div>
              </template>
            </template>
          </div>

        </div>
        <div class="user_info">
          <div class="title">{{$t('order.userInfoText')}}</div>
          <div class="item_box">
            <div class="item">{{$t('order.receivingCustomer')}}：{{order.consignee}}</div>
            <div class="item">{{$t('order.mobile')}}：{{order.mobile}}</div>
            <div class="item">{{$t('order.shippingAddress')}}：待实现</div>
            <div class="item">{{$t('order.customerMessage')}}：{{order.addMessage}}</div>
          </div>
        </div>
      </div>
      <div class="order-remark">
        <i
          class="icon el-icon-edit-outline"
          @click="addNodes"
        ></i>
        <span>{{$t('order.sellerMessage')}}：{{order.sellerRemark}}</span>
      </div>
    </div>
    <div
      v-if="showShippingInfo"
      class="order-list-table"
    >
      <div class="title">{{$t('order.shippingInfoText')}}</div>
      <div class="table_box">
        <table width="100%">
          <thead>
            <tr>
              <th width="300px">{{$t('order.goodsName')}}</th>
              <th width="100px">{{$t('order.specText')}}</th>
              <template v-if="order.deliverType == 0">
                <th width="100px">{{$t('order.shippingNum')}}</th>
                <th>{{$t('order.shippingCompany')}}</th>
                <th>{{$t('order.shippingNo')}}</th>
                <th>{{$t('order.shippingTimeText')}}</th>
                <th>{{$t('order.confirmTimeText')}}</th>
              </template>
              <template v-else>
                <th width="100px">{{$t('order.collectGoodsNum')}}</th>
                <th width="100px">{{$t('order.storeName')}}</th>
                <th width="100px">{{$t('order.collectGoodsTime')}}</th>
              </template>
            </tr>

          </thead>
          <tbody>
            <template v-for="item in showShippingList">
              <tr
                v-for="(shipGoods , index) in item.goods"
                :key="shipGoods.recId"
              >
                <td>{{shipGoods.goodsName}}</td>
                <td>{{shipGoods.goodsAttr}}</td>
                <td>{{shipGoods.sendNumber}}</td>
                <template v-if="order.deliverType == 0">
                  <td
                    v-if="index === 0"
                    :rowspan="item.goods.length"
                  >{{$t('expressList.company')[item.shippingId]}}</td>
                  <td
                    v-if="index === 0"
                    :rowspan="item.goods.length"
                  >{{item.shippingNo}}</td>
                  <td
                    v-if="index === 0"
                    :rowspan="item.goods.length"
                  >{{item.shippingTime}}</td>
                  <td
                    v-if="index === 0"
                    :rowspan="item.goods.length"
                  >{{item.confirmTime}}</td>
                </template>
                <template v-else>
                  <th width="100px">{{order.storeId}}</th>
                  <th width="100px">{{order.confirmTime}}</th>
                </template>
              </tr>
            </template>
          </tbody>
        </table>
      </div>
    </div>
    <div
      v-if="showReturnInfo"
      class="order-list-table"
    >
      <div class="title">{{$t('order.returnInfoText')}}</div>
      <div class="table_box">
        <table width="100%">
          <thead>
            <tr>
              <th width="300px">{{$t('order.goodsName')}}</th>
              <th width="100px">{{$t('order.specText')}}</th>
              <th>{{$t('order.returnNumText')}}</th>
              <th>{{$t('order.returnType')}}</th>
              <th>{{$t('order.returnStatus')}}</th>
              <th width="100px">{{$t('order.returnMoney')}}</th>
              <th width="100px">{{$t('order.applyTime')}}</th>
              <th width="100px">{{$t('order.finishTime')}}</th>
            </tr>

          </thead>
          <tbody>
            <template v-for="item in showReturnList">
              <template v-if="item.orderReturnGoodsVo != null">
                <tr
                  v-for="(returnGoods , index) in item.orderReturnGoodsVo"
                  :key="returnGoods.recId"
                >
                  <td>{{returnGoods.goodsName}}</td>
                  <td>{{returnGoods.goodsAttr}}</td>
                  <td>{{returnGoods.goodsNumber}}</td>
                  <template v-if="order.deliverType == 0">
                    <td
                      v-if="index === 0"
                      :rowspan="item.orderReturnGoodsVo.length"
                    >{{returnTypeMap.get(item.returnType)}}</td>
                    <td
                      v-if="index === 0"
                      :rowspan="item.orderReturnGoodsVo.length"
                    >
                      {{
                      returnStatusToShowMapping[item.refundStatus + ''] == null ?
                      returnStatusToShowMapping[item.refundStatus + '-' + item.returnType] :
                      returnStatusToShowMapping[item.refundStatus + '']
                    }}
                    </td>
                    <td
                      v-if="index === 0"
                      :rowspan="item.orderReturnGoodsVo.length"
                    >{{(item.money + item.shippingFee).toFixed(2)}}
                      <br />
                      <el-link
                        type="primary"
                        :underline="false"
                      >{{[1,2,4].indexOf(item.refundStatus) == -1 ? $t('order.returnInfo') : $t('order.approval')}}</el-link>

                    </td>
                    <td
                      v-if="index === 0"
                      :rowspan="item.orderReturnGoodsVo.length"
                    >
                      <template v-if="item.returnType == 1">
                        {{item.applyTime}}
                      </template>
                      <template v-else>
                        {{item.shippingOrRefundTime}}
                      </template>
                    </td>
                    <td
                      v-if="index === 0"
                      :rowspan="item.orderReturnGoodsVo.length"
                    >{{item.refundSuccessTime}}
                    </td>
                  </template>
                </tr>
              </template>
              <template v-else>
                <tr :key="item.retId">
                  <td>{{$t('order.nullValue')}}</td>
                  <td>{{$t('order.nullValue')}}</td>
                  <td>{{$t('order.nullValue')}}</td>
                  <td>{{returnTypeMap.get(item.returnType)}}</td>
                  <td>
                    {{
                      returnStatusToShowMapping[item.refundStatus + ''] == null ?
                      returnStatusToShowMapping[item.refundStatus + '-' + item.returnType] :
                      returnStatusToShowMapping[item.refundStatus + '']
                    }}
                  </td>
                  <td>
                    {{item.shippingFee.toFixed(2)}}
                    <br />
                    <el-link
                      type="primary"
                      :underline="false"
                    >{{$t('order.returnInfo')}}</el-link>
                  </td>
                  <td>
                    <template>
                      {{item.shippingOrRefundTime}}
                    </template>
                  </td>
                  <td>{{item.refundSuccessTime}}
                  </td>
                </tr>
              </template>
            </template>
          </tbody>
        </table>
      </div>
    </div>
    <div class="order-list-table">
      <div class="table_box">
        <table width="100%">
          <thead>
            <thead>
              <tr>
                <th width="270px">{{$t('order.goods')}}</th>
                <th width="100px">{{$t('order.goodsSn')}}</th>
                <th width="100px">{{$t('order.goodsPrice')}}</th>
                <th width="100px">{{$t('order.goodsNumber')}}</th>
                <th>{{$t('order.consigneeInfo')}}</th>
                <th>{{$t('order.orderTime')}}</th>
                <th>{{$t('order.orderStatusText')}}</th>
                <th>{{$t('order.moneyPaid')}}</th>
              </tr>
              <tr class="jiange">
                <th :colspan="order.fanliType > 0 ? 9 : 8"></th>
              </tr>
            </thead>
          <tbody>
            <template v-for="oneOrder in orderList">
              <tr
                v-if="oneOrder.goods !== null && oneOrder.goods.length !== 0"
                class="order-tb-head"
                :key="oneOrder.orderSn"
              >
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <span>
                        {{
                          (oneOrder.mainOrderSn == '' ? $t('order.orderSn') : oneOrder.mainOrderSn == oneOrder.orderSn ? $t('order.mainOrderSn') : $t('order.childOrderSn')) + '：'+order.orderSn
                        }}
                      </span>
                      <span class="paymentType">{{$t('order.paymentType')}}：
                        <el-tooltip
                          v-for="(payCode,index) in oneOrder.payCodeList"
                          :key="index"
                          class="item"
                          effect="light"
                          :content="paymentTypeMap.get(payCode)"
                          placement="top-start"
                        >
                          <img
                            :src="payCodeIconClassMap[payCode]"
                            :alt="paymentTypeMap[payCode]"
                          >
                        </el-tooltip>

                      </span>
                      <span>{{$t('order.deliverTypeText')}}：{{deliverTypeMap.get(oneOrder.deliverType)}}</span>
                      <span v-if="oneOrder.partShipFlag == 1 ">
                        {{$t('order.partShipText')}}</span>
                    </div>
                    <div class="right">
                      <span @click="manualReturn(oneOrder.orderSn,oneOrder.orderId,oneOrder.createTime)">{{$t('order.manualReturnText')}}</span>
                      <span>{{$t('order.comment')}}</span>
                    </div>
                  </div>
                </td>
              </tr>
              <tr
                class="order-tb-body"
                v-for="(goodsInfo,index) in oneOrder.goods"
                :key="goodsInfo.recId"
              >
                <td>
                  <div class="goods_info">
                    <img
                      :src="$imageHost+'/image/admin/icon_jia.png'"
                      alt=""
                    >
                    <div class="right_info">
                      <div class="goods_name">{{goodsInfo.goodsName}}</div>
                      <div class="goods_spec">{{goodsInfo.goodsAttr}}</div>
                    </div>
                  </div>
                </td>
                <td>{{goodsInfo.goodsSn}}</td>
                <td>{{goodsInfo.goodsPrice.toFixed(2)}}</td>
                <td>{{goodsInfo.goodsNumber}}</td>
                <td
                  v-if="index == 0"
                  :rowspan="oneOrder.goods.length"
                >
                  <p>{{oneOrder.consignee}}</p>
                  <p>{{oneOrder.mobile}}</p>
                  <p v-if="oneOrder.mainOrderSn != '' && oneOrder.mainOrderSn != oneOrder.orderSn"><span
                      class="shipping_address"
                      @click="showAddressInfo(oneOrder.completeAddress)"
                    >{{$t('order.shippingAddress')}}</span></p>
                </td>
                <td
                  v-if="index == 0"
                  :rowspan="oneOrder.goods.length"
                >{{oneOrder.createTime}}</td>
                <td
                  v-if="index == 0"
                  :rowspan="oneOrder.goods.length"
                >

                  <template v-if="goodsTypeArray.indexOf('17') != -1 && oneOrder.orderSn == oneOrder.mainOrderSn && [8,10,13].indexOf(oneOrder.orderStatus)">
                    {{$t('order.waitReceive')}}
                  </template>
                  <template v-else>
                    <template v-if="oneOrder.orderStatus != 3 && oneOrder.partShipFlag != 5">
                      <template v-if="oneOrder.orderStatus == 0 && goodsTypeArray.indexOf('10') != -1">
                        <template v-if="order.bkOrderPaid == 0">
                          {{$t('order.waitDeposit')}}
                        </template>
                        <template v-else>
                          {{$t('order.waitTail')}}
                        </template>
                      </template>
                      <template v-else>
                        {{orderStatusMap.get(order.orderStatus)}}
                      </template>
                    </template>
                    <template v-else>
                      <template v-if="oneOrder.deliverType == 1 && oneOrder.orderStatus == 3">
                        {{$t('order.waitverify')}}
                      </template>
                      <template v-else-if="oneOrder.deliverType == 0 && oneOrder.orderStatus == 3">
                        {{$t('order.waitShip')}}
                      </template>
                      <template v-else-if="oneOrder.deliverType == 1 && oneOrder.orderStatus == 5">
                        {{$t('order.takeByself')}}
                      </template>
                      <template v-else-if="oneOrder.deliverType == 0 && oneOrder.orderStatus == 5">
                        {{$t('order.received')}}
                      </template>
                    </template>
                    <template v-if="oneOrder.orderStatus == 3 && oneOrder.partShipFlag == 1">
                      <br />
                      ({{$t('order.partShip')}})
                    </template>
                    <template v-if="oneOrder.orderStatus == 3 && oneOrder.deliverType != 1 && oneOrder.canDeliver == true">
                      <!-- 非自提且待发货自提 -->
                      <br />
                      <el-button
                        type="primary"
                        size="small"
                        @click="deliver(oneOrder)"
                      >{{$t('order.delivery')}}</el-button>
                      <template v-if="oneOrder.canVerify == true">
                        <!-- 核销 -->
                        <br />
                        <el-button
                          type="primary"
                          size="small"
                          @click="verify(oneOrder)"
                        >{{$t('order.verify')}}</el-button>
                      </template>
                    </template>
                  </template>
                  <template v-if="oneOrder.refundStatus > 0">
                    <br />
                    <template v-if="[1,2,4].indexOf(oneOrder.refundStatus) != -1">
                      <el-button type="text">{{$t('order.applyRetrunView')}}</el-button>
                    </template>
                    <template v-else>
                      <el-button type="text">{{$t('order.retrunView')}}</el-button>
                    </template>
                  </template>
                  <template v-if="oneOrder.canClose == true">
                    <!-- 关闭 -->
                    <br />
                    <el-button
                      type="primary"
                      size="small"
                      @click="close(oneOrder)"
                    >{{$t('order.close')}}</el-button>
                  </template>
                  <template v-if="oneOrder.canFinish == true">
                    <!-- 完成 -->
                    <br />
                    <el-button
                      type="primary"
                      size="small"
                      @click="finish(oneOrder)"
                    >{{$t('order.finish')}}</el-button>
                  </template>

                </td>
                <td
                  v-if="index == 0"
                  :rowspan="oneOrder.goods.length"
                > <span>
                    {{currencyPool[order.currency][lang][1]}}{{oneOrder.moneyPaid.toFixed(2)}}
                  </span>
                  <template v-if="goodsTypeArray.indexOf($t('order.goodsTypeList')[5][0].toString()) > -1">
                    <span>
                      + {{order.scoreDiscount * monetToScore}}{{$t('order.score')}}
                    </span>
                    <span>
                      ({{$t('order.freeShipping')}})
                    </span>
                  </template>
                  <template v-else-if="order.mainOrderSn == null">
                    <span>({{$t('order.freeFhipping')}})</span>
                  </template>
                  <span v-else-if="order.deliverType == 0">
                    ({{
                        $t('order.includeExpress')
                      }}:
                    {{currencyPool[order.currency][lang][1]}}
                    {{oneOrder.shippingFee.toFixed(2)}}
                    )
                  </span>
                </td>
              </tr>
            </template>
            <tr>
              <td
                :colspan="order.fanliType> 0 ? 9 : 8"
                class="total_box"
              >
                <p>
                  {{$t('order.goodsAmount')}}：
                  <!-- 订单金额、优惠金额、支付明细 -->
                  <span class="text-amount">
                    {{
                      goodsTypeArray.indexOf('4') == -1 ?
                        (currencyPool[order.currency][lang][1] + order.orderAmount.toFixed(2)) :
                        (currencyPool[order.currency][lang][1] + (order.orderAmount - order.scoreDiscount).toFixed(2) + '+' + order.scoreDiscount * monetToScore + $t('order.score'))
                    }}
                  </span>
                </p>
                <p v-if="order.deliverType == 0">
                  {{$t('order.shipping')}}：
                  <span>
                    +{{currencyPool[order.currency][lang][1] + order.shippingFee.toFixed(2)}}
                  </span>
                </p>
                <p v-if="order.packageDiscount > 0">
                  {{$t('order.packageDiscount')}}：
                  <span>
                    -{{currencyPool[order.currency][lang][1] + order.packageDiscount.toFixed(2)}}
                  </span>
                </p>
                <p v-if="order.memberCardReduce > 0">
                  <span>
                    {{
                       (order.exchang == 1 && goodsTypeArray.indexOf($t('goodsTypeList')[14][0]) !== -1) ?
                         $t('order.limitedCard',{
                           cardName:order.cardName,
                           cardNo:order.cardNo,
                           currency:'-' + currencyPool[order.currency][lang][1],
                           reduceMoney:(order.memberCardReduce / order.goodsAmount).toFixed(2),
                           goodsAmount:order.goodsAmount}) :
                       ($t('order.memberCard')+'：'+ '-' + currencyPool[order.currency][lang][1] + order.memberCardReduce)
                    }}
                  </span>
                </p>
                <p v-if="order.promotionReduce > 0">
                  {{$t('order.packageDiscount')}}：
                  <span>
                    -{{currencyPool[order.currency][lang][1] + order.promotionReduce.toFixed(2)}}
                  </span>
                </p>
                <p v-if="order.couponDiscount > 0">
                  {{$t('order.couponDiscount')}}：
                  <span>
                    -{{currencyPool[order.currency][lang][1] + order.couponDiscount.toFixed(2)}}
                  </span>
                </p>
                <p v-if="order.grouperCheapReduce > 0">
                  {{$t('order.grouperCheapReduce')}}：
                  <span>
                    -{{currencyPool[order.currency][lang][1] + order.grouperCheapReduce.toFixed(2)}}
                  </span>
                </p>
                <p v-if="order.useAccount > 0">
                  {{$t('order.useAccount')}}：
                  <span>
                    -{{currencyPool[order.currency][lang][1] + order.useAccount.toFixed(2)}}
                  </span>
                </p>
                <p v-if="order.scoreDiscount > 0">
                  <span>
                    {{
                       (goodsTypeArray.indexOf($t('goodsTypeList')[5][0] == -1)) ?
                       ($t('order.scoreDiscount') +'-'+ currencyPool[order.currency][lang][1] + order.useAccount.toFixed(2)) :
                       ($t('order.scoreExchange') +'-'+ currencyPool[order.currency][lang][1] + order.useAccount.toFixed(2))
                    }}
                  </span>
                </p>
                <p v-if="order.discountPrice > 0">
                  {{$t('order.discountPrice')}}：
                  <span>
                    -{{currencyPool[order.currency][lang][1] + order.discountPrice.toFixed(2)}}
                  </span>
                </p>
                <p v-if="order.dapeiReduceAmount > 0">
                  {{$t('order.dapeiReduceAmount')}}：
                  <span>
                    -{{currencyPool[order.currency][lang][1] + order.dapeiReduceAmount.toFixed(2)}}
                  </span>
                </p>
                <p v-if="order.memberCardBalance > 0">
                  {{$t('order.memberCardBalance')}}：
                  <span>
                    -{{currencyPool[order.currency][lang][1] + order.memberCardBalance.toFixed(2)}}
                  </span>
                </p>
                <p>
                  {{$t('order.amountSum')}}：
                  <span>
                    {{
                      currencyPool[order.currency][lang][1] +
                      order.bk_order_paid > 0 ?
                      (order.moneyPaid + order.bkOrderMoney).toFixed(2):
                      order.moneyPaid.toFixed(2)
                    }}
                  </span>
                </p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="order-list-table">
      <div class="title">返利详情</div>
      <div class="table_box">
        <table width="100%">
          <thead>
            <tr>
              <th>分销员手机号</th>
              <th>分销员微信昵称</th>
              <th>分销员真实姓名</th>
              <th>商品名称</th>
              <th>返利商品总金额</th>
              <th>成本价</th>
              <th>商品可返利金额</th>
              <th>返利关系</th>
              <th>返利比例</th>
              <th>返利佣金金额</th>
              <th>返利状态</th>
              <th>返利日期</th>
            </tr>
          </thead>
          <tbody>
            <template>
            </template>
          </tbody>
        </table>
      </div>
    </div>
    <div
      class="status_box"
      :class="{hide:!showStatus}"
    >
      <div
        class="view"
        @click="toogleShow"
      >{{showStatus ? '收起':'打开'}}</div>
      <div class="step_content">
        <template v-for="(item,index) in timeFlowDiagramMapping[statusKey]">

          <div
            class="item_content"
            :key="index"
            :class="{'is_end':index === timeFlowDiagramMapping[statusKey].length -1,'is-finish':order[item],'is_wait': !order[item] && order[timeFlowDiagramMapping[statusKey][index - 1]]}"
          >
            <div class="item_head">
              <div class="item_line"></div>
              <div class="item_text">{{index + 1}}</div>
            </div>
            <div class="item_main">
              <div class="item_title">{{$t('order.timeFlowDiagram')[item]}}</div>
              <div class="item_description">{{order[item]}}</div>
            </div>
          </div>
        </template>
      </div>
    </div>
    <nodesDialog
      :show.sync="showNodes"
      :orderSn="notesOrderSn"
      @handlerResetData="search"
    />
    <el-dialog
      title="收货地址"
      :visible.sync="showAddress"
      width="30%"
    >
      <div>
        {{$t('order.shippingAddress')}}：{{itemAddressInfo}}
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="showAddress = false"
        >确 定</el-button>
      </span>
    </el-dialog>
    <deliveryDialog
      v-if="showDelivery"
      :show.sync="showDelivery"
      :orderData="orderItemInfo"
      @handlerResetData="search"
    />
  </div>
</template>

<script>
import {
  info, close, finish, verify
} from '@/api/admin/orderManage/order'
export default {
  components: {
    nodesDialog: () => import('./addNotes'),
    deliveryDialog: () => import('./deliveryDialog')
  },
  data () {
    return {
      order: {},
      showNodes: false,
      showAddress: false,
      itemAddressInfo: null,
      showStatus: true,
      searchParam: {
        orderSn: null
      },
      orderStatusMap: {},
      goodsTypeMap: {},
      deliverTypeMap: {},
      paymentTypeMap: {},
      payCodeIconClassMap: {
        1: `${this.$imageHost}/image/admin/pay_account.png`,
        2: `${this.$imageHost}/image/admin/pay_score.png`,
        3: `${this.$imageHost}/image/admin/pay_lottery.png`,
        4: `${this.$imageHost}/image/admin/pay_cod.png`,
        5: `${this.$imageHost}/image/admin/pay_rewards_points.png`,
        6: `${this.$imageHost}/image/admin/pay_wx.png`
      },
      // n-m(n订单状态，m配送方式0快递1自提);n(不区分配送方式)
      timeFlowDiagramMapping: {
        '0': ['createTime', 'payTime', 'shippingTime', 'finishedTime'],
        '1': ['createTime', 'cancelledTime'],
        '2': ['createTime', 'closedTime'],
        '3-0': ['createTime', 'payTime', 'shippingTime', 'finishedTime'],
        '3-1': ['createTime', 'payTime', 'confirmTime', 'finishedTime'],
        '4': ['createTime', 'payTime', 'shippingTime', 'finishedTime'],
        '5-0': ['createTime', 'payTime', 'shippingTime', 'finishedTime'],
        '5-1': ['createTime', 'payTime', 'confirmTime', 'finishedTime'],
        '6-0': ['createTime', 'payTime', 'shippingTime', 'finishedTime'],
        '6-1': ['createTime', 'payTime', 'confirmTime', 'finishedTime'],
        '7': null,
        '8': ['createTime', 'payTime', 'returnFinishTime'],
        '9': null,
        '10': ['createTime', 'payTime', 'refundFinishTime'],
        '11': ['createTime', 'payTime', 'pinStartTime', 'shippingTime', 'finishedTime'],
        '12': ['createTime', 'payTime', 'pinEndTime', 'shippingTime', 'finishedTime'],
        '13': ['createTime', 'payTime', 'giftGiving', 'giveCompletion']
      },
      returnTypeMap: null,
      returnStatusToShowMapping: null,
      monetToScore: 100,
      goodsTypeArray: [],
      notesOrderSn: '',
      // 控制发货
      showDelivery: false
    }
  },
  created () {
    this.arrayToMap()
    this.search(this.$route.query.orderSn)
    this.langDefault()
  },
  watch: {
    lang () {
      this.arrayToMap()
    }
  },
  methods: {
    shipTableMethod ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return {
          colspan: 3,
          rowspan: 1
        }
      }
    },
    columnStyle ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return 'no_padding'
      }
    },
    search (orderSn) {
      this.searchParam.orderSn = orderSn
      info(this.searchParam).then(res => {
        this.order = res.content
        this.goodsTypeArray = this.order.goodsType.substring(1, this.order.goodsType.length - 1).split('][')
      }).catch(() => {
      })
    },
    arrayToMap () {
      this.returnTypeMap = new Map(this.$t('order.returnTypeList'))
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
      this.goodsTypeMap = new Map(this.$t('order.goodsTypeList'))
      this.deliverTypeMap = new Map(this.$t('order.deliverTypeList'))
      this.paymentTypeMap = new Map(this.$t('order.paymentTypeList'))
      this.returnStatusToShowMapping = {
        '1': this.$t('order.returnStatusMapping_1'),
        '2': this.$t('order.returnStatusList')[3][1],
        '3': this.$t('order.returnStatusList')[2][1],
        '4-0': this.$t('order.returnStatusMapping_4_0'),
        '4-1': this.$t('order.returnStatusList')[4][1],
        '6-0': this.$t('order.returnStatusList')[6][1],
        '6-1': this.$t('order.returnStatusList')[5][1],
        '7': this.$t('order.returnStatusList')[7][1],
        '5': this.$t('order.returnStatusList')[8][1]
      }
    },
    addNodes () {
      this.showNodes = true
      this.notesOrderSn = this.searchParam.orderSn
    },
    seeDetails (orderSn) {
      console.log(orderSn)
      this.$router.push({
        name: 'orderInfo',
        query: {
          orderSn: orderSn
        }
      })
    },
    manualReturn (orderSn, orderId, orderTime) {
      this.$router.push({
        name: 'manualRefund',
        query: {
          orderSn: orderSn,
          orderId: orderId,
          orderTime: orderTime
        }
      })
    },
    showAddressInfo (Address) {
      this.showAddress = true
      this.itemAddressInfo = Address
    },
    toogleShow () {
      this.showStatus = !this.showStatus
    },
    deliver (orderInfo) {
      this.showDelivery = true
      this.orderItemInfo = orderInfo
    },
    verify (orderInfo) {
      let obj = {
        orderId: orderInfo.orderId,
        orderSn: orderInfo.orderSn,
        isCheck: false,
        // TODO
        verifyCode: '',
        action: 4
      }
      verify(obj).then(res => {
      })
    },
    close (orderInfo) {
      let obj = {
        orderId: orderInfo.orderId,
        orderSn: orderInfo.orderSn,
        action: 3
      }
      close(obj).then(res => {
      })
    },
    finish (orderInfo) {
      let obj = {
        orderId: orderInfo.orderId,
        orderSn: orderInfo.orderSn,
        action: 5
      }
      finish(obj).then(res => {
      })
    }
  },
  computed: {
    statusKey () {
      if (this.order.orderStatus === 3 || this.order.orderStatus === 5 || this.order.orderStatus === 6) {
        return `${this.order.orderStatus}-${this.order.deliverType}`
      } else {
        return `${this.order.orderStatus}`
      }
    },
    showShippingInfo () {
      let mainFlag = this.order.shippingList
      let childFlag = null
      if (this.order.childOrders != null) {
        childFlag = this.order.childOrders.forEach(childOrder => {
          if (childOrder.shippingList != null) {
            return true
          }
        })
      }
      if (mainFlag || childFlag) {
        return true
      }
    },
    showReturnInfo () {
      let mainFlag = this.order.refundList
      let childFlag = null
      if (this.order.childOrders != null) {
        childFlag = this.order.childOrders.forEach(childOrder => {
          if (childOrder.refundList != null) {
            return true
          }
        })
      }
      if (mainFlag || childFlag) {
        return true
      }
    },
    showShippingList () {
      let shippingList = []
      if (this.order.shippingList != null) {
        shippingList = shippingList.concat(this.order.shippingList)
      }
      if (this.order.childOrders != null) {
        this.order.childOrders.forEach(childOrder => {
          if (childOrder.shippingList != null) {
            shippingList = shippingList.concat(childOrder.shippingList)
          }
        })
      }
      return shippingList
    },
    showReturnList () {
      let refundList = []
      if (this.order.refundList != null) {
        refundList = refundList.concat(this.order.refundList)
      }
      if (this.order.childOrders != null) {
        this.order.childOrders.forEach(childOrder => {
          if (childOrder.refundList != null) {
            refundList = refundList.concat(childOrder.refundList)
          }
        })
      }
      return refundList
    },
    orderList () {
      let orderList = []
      orderList = orderList.concat(this.order)
      if (this.order.childOrders != null) {
        this.order.childOrders.forEach(childOrder => {
          if (childOrder != null) {
            childOrder.payCodeList = this.order.payCodeList
            orderList = orderList.concat(childOrder)
          }
        })
      }
      return orderList
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  > div {
    margin-bottom: 10px;
  }
  .since-info {
    background-color: #fff;
    padding: 0 25px;
    font-size: 14px;
    overflow: hidden;
    .since-info-top {
      display: flex;
      line-height: 44px;
      color: #333;
      > span {
        margin-right: 60px;
      }
    }
    .since-info-path {
      margin-bottom: 10px;
    }
    .since-info-detail {
      display: flex;
      margin-left: -30px;
      margin-bottom: 10px;
      > div {
        margin-left: 30px;
        border: 1px solid #cfd6ff;
        flex: 1;
        padding: 0 30px;
        > .title {
          margin-top: 10px;
        }
        > .item_box {
          display: flex;
          justify-content: space-between;
          flex-wrap: wrap;
          line-height: 30px;
          color: #666;
          > .item {
            min-width: 210px;
          }
        }
      }
    }
    .order-remark {
      font-size: 14px;
      color: #333;
      line-height: 50px;
      > .icon {
        font-size: 20px;
        color: #f8a039;
        vertical-align: middle;
        cursor: pointer;
      }
    }
  }
  .order-list-table {
    padding: 10px 25px 0;
    background-color: #fff;
    overflow: hidden;
    > .table_box {
      margin-bottom: 10px;
      > table {
        > thead tr {
          background: #f5f5f5;
          th {
            padding: 8px 10px;
            color: #333;
            font-size: 14px;
          }
        }
        > tbody {
          margin-bottom: 15px;
          border: 1px solid #eee;
          td {
            border: 1px solid #eee;
            color: #666;
            padding: 8px 10px;
            color: #666;
            font-size: 14px;
            text-align: center;
            vertical-align: middle;
          }
          .total_box {
            text-align: right;
            > p {
              line-height: 30px;
            }
          }
          .order-tb-head {
            background-color: #f5f5f5;
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
                > span {
                  width: 207px;
                  text-align: left;
                  overflow: hidden;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                  cursor: pointer;
                  &.paymentType {
                    img {
                      vertical-align: middle;
                      & + img {
                        margin-left: 5px;
                      }
                    }
                  }
                }
              }
              .right {
                width: 250px;
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
          .order-tb-body {
            .goods_info {
              display: flex;
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
          }
        }
        .jiange {
          background-color: #fff;
        }
      }
    }
  }
  .title {
    font-weight: 600;
    color: #333;
    font-size: 14px;
    margin-bottom: 10px;
  }
  .no_padding {
    padding: 0 !important;
  }
  .shipping_address {
    color: #409eff;
    cursor: pointer;
  }
}
.status_box {
  position: fixed;
  right: 0;
  width: 215px;
  top: 140px;
  bottom: 0;
  margin-bottom: 0 !important;
  display: flex;
  align-items: center;
  transition: right 0.3s ease-in;
  padding: 0;
  z-index: 2;
  &.hide {
    right: -180px;
  }
  .view {
    width: 35px;
    cursor: pointer;
  }
  .step_content {
    flex: 1;
    display: flex;
    flex-direction: column;
    height: 100%;
    background-color: #fff;
    padding: 10px 0 10px 10px;
    .item_content {
      flex-shrink: 1;
      flex-basis: 50%;
      display: flex;
      color: #666;
      &.is-finish {
        color: skyblue;
      }
      &.is_wait {
        color: #000;
      }
      &.is_end {
        flex-basis: auto !important;
        flex-shrink: 0;
        flex-grow: 0;
      }
      &:last-of-type {
        .item_line {
          display: none;
        }
      }
      .item_head {
        width: 24px;
        position: relative;
        .item_line {
          position: absolute;
          width: 2px;
          top: 0;
          bottom: 0;
          left: 11px;
          background-color: #c0c4cc;
        }
        .item_text {
          border-radius: 50%;
          border: 2px solid;
          border-color: inherit;
          position: relative;
          z-index: 1;
          display: inline-flex;
          justify-content: center;
          align-items: center;
          width: 24px;
          height: 24px;
          font-size: 14px;
          box-sizing: border-box;
          background: #fff;
          transition: 0.15s ease-out;
          font-weight: 600;
          line-height: 1;
          color: inherit;
        }
      }
      .item_main {
        flex: 1;
        padding-left: 10px;
        .item_title {
          line-height: 24px;
          padding-bottom: 8px;
        }
        .item_description {
          margin-top: -5px;
          font-size: 12px;
          line-height: 20px;
          font-weight: 400;
        }
      }
    }
  }
}
</style>
