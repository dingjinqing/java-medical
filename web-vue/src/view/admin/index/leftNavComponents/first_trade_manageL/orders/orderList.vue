<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <span>{{$t('order.goodsName')}}：</span>
            <el-input
              v-model="searchParams.goodsName"
              :placeholder="$t('order.goodsName')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.orderSn')}}：</span>
            <el-input
              v-model="searchParams.orderSn"
              :placeholder="$t('order.orderSn')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.orderStatusText')}}：</span>
            <el-select
              v-model="searchParams.orderStatus"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.orderStatusList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{$t('order.goodsTypeText')}}：</span>
            <el-select
              v-model="searchParams.goodsType"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.goodsTypeList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{$t('order.consigneeName')}}：</span>
            <el-input
              v-model="searchParams.consignee"
              :placeholder="$t('order.consigneeName')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.mobile')}}：</span>
            <el-input
              v-model="searchParams.mobile"
              :placeholder="$t('order.mobile')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.deliverTypeText')}}：</span>
            <el-select
              v-model="searchParams.deliverType"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.deliverTypeList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <span>{{$t('order.userName')}}：</span>
            <el-input
              v-model="searchParams.userName"
              :placeholder="$t('order.userName')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('order.userSource')}}：</span>
            <el-select
              v-model="searchParams.source"
              :placeholder="$t('order.defaultSelect')"
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
            <span>{{$t('order.verifyCode')}}：</span>
            <el-input
              v-model="searchParams.verifyCode"
              :placeholder="$t('order.verifyCode')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.store')}}：</span>
            <el-select
              v-model="searchParams.storeId"
              :placeholder="$t('order.defaultSelect')"
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
            <span>{{$t('order.paymentType')}}：</span>
            <el-select
              v-model="searchParams.paymentType"
              :placeholder="$t('order.defaultSelect')"
              size="small"
              class="default_input"
              filterable
            >
              <el-option
                v-for="item in $t('order.paymentTypeList')"
                :key="item[0]"
                :label="item[1]"
                :value="item[0]"
              ></el-option>
            </el-select>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.tag')}}：</span>
            <el-select
              v-model="searchParams.tagIds"
              multiple
              filterable
              allow-create
              default-first-option
              :placeholder="$t('order.tagDescribe')"
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
            <span>{{$t('order.shippingAddress')}}：</span>
            <areaLinkage
              @areaData="handleAreaData"
              style="width:365px;"
            />
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.specCode')}}：</span>
            <el-input
              v-model="searchParams.specCode"
              :placeholder="$t('order.specCode')"
              size="small"
              class="default_input"
            ></el-input>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.orderTime')}}：</span>
            <el-date-picker
              v-model="orderTime"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00','23:59:59']"
              size="small"
            >
            </el-date-picker>
          </div>
          <div
            class="filters_item"
            v-show="moreFilters"
          >
            <span>{{$t('order.completeTime')}}：</span>
            <el-date-picker
              v-model="completeTime"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00','23:59:59']"
              size="small"
            >
            </el-date-picker>
          </div>
        </div>
      </div>

      <div class="search_button_box">
        <span @click="moreFilters = !moreFilters">{{moreFilters ? $t('order.collapse') : $t('order.more')}}</span>
        <div class="button_box">
          <el-button
            type="primary"
            size="small"
            @click="search"
          >{{$t('order.filter')}}</el-button>
          <el-button
            type="default"
            size="small"
            @click="showExportColumnSelect = true"
          >{{$t('order.export')}}</el-button>
        </div>
      </div>
      <div class="table_box">
        <el-tabs
          v-model="searchParams.orderStatus2"
          @tab-click="handleClick"
        >
          <template v-for="item in tabsOrderStatus">
            <el-tab-pane
              :label="item.label"
              :key="item.value"
              v-if="item.value === '4'"
            >
              <span slot="label">
                <span>待发货<span class="wait_num">0</span></span>/<span>待核销<span class="wait_num">0</span></span>
              </span>
            </el-tab-pane>
            <el-tab-pane
              :label="item.label"
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
              <th width="300px">{{$t('order.goods')}}</th>
              <th width="10%">{{$t('order.goodsSnAndProductSn')}}</th>
              <th width="10%">{{$t('order.goodsPrice')}}</th>
              <th width="10%">{{$t('order.goodsNumber')}}</th>
              <th width="10%">{{$t('order.consignee')}}</th>
              <th>{{$t('order.orderTime')}}</th>
              <th width="10%">{{$t('order.orderStatusText')}}</th>
              <th width="10%">{{$t('order.moneyPaid')}}</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="8"></td>
            </tr>
          </tbody>
          <template v-for="(orderItem,orderIndex) in orderList">
            <!-- $set(orderItem,'goodsTypeArray',orderItem.goodsType.split(',')) 为该对象设置新属性 -->
            <tbody
              :key="orderItem.orderSn"
              class="hasborder"
              :value="setGoodsTypeArray(orderItem)"
            >
              <tr class="order-tb-head">
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <el-tooltip
                        class="item"
                        effect="light"
                        :content="$t('order.orderSn')+'：'+orderItem.orderSn"
                        placement="top-start"
                      >
                        <span>{{(orderItem.mainOrderSn == '' ? $t('order.orderSn') : orderItem.mainOrderSn == orderItem.orderSn ? $t('order.mainOrderSn') : $t('order.childOrderSn')) + '：'+orderItem.orderSn}}
                        </span>
                      </el-tooltip>
                      <span class="paymentType">{{$t('order.paymentType')}}：

                        <el-tooltip
                          v-for="(payCode,index) in orderItem.payCodeList"
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
                      <el-tooltip
                        class="item"
                        effect="light"
                        :content="$t('order.deliverTypeText')+'：'+deliverTypeMap.get(orderItem.deliverType)"
                        placement="top-start"
                      >
                        <span>{{$t('order.deliverTypeText')}}：{{deliverTypeMap.get(orderItem.deliverType)}}</span>
                      </el-tooltip>
                      <el-tooltip
                        class="item"
                        effect="light"
                        :content="goodsTypeFilter(orderItem.goodsTypeArray)"
                        placement="top-start"
                      >
                        <span>{{$t('order.goodsTypeText')}}：
                          <span
                            v-for="(goodsType,index) in orderItem.goodsTypeArray"
                            :key="index"
                          >
                            <template v-if="index != 0">,</template>
                            {{goodsTypeMap.get(Number(goodsType))}}
                          </span>
                        </span>
                      </el-tooltip>
                    </div>
                    <div class="right">
                      <span class="icon_collect"><i
                          :class="{'el-icon-star-off':!orderItem.starFlag,'el-icon-star-on':orderItem.starFlag}"
                          @click="toggleStar(orderItem.orderSn,orderItem.starFlag)"
                        ></i></span>
                      <span @click="addNodes(orderItem.orderSn)">{{$t('order.remark')}}</span>
                      <span @click="seeDetails(orderItem.orderSn)">{{$t('order.details')}}</span>
                      <span>{{$t('order.comment')}}</span>
                    </div>
                  </div>
                </td>
              </tr>
              <tr
                v-if="orderItem.goodsTypeArray.indexOf('10') != -1 "
                class="order-tb-head"
              >
                <td colspan="8">
                  <div class="tb-head_box">
                    <div class="left">
                      <el-tooltip
                        class="item"
                        effect="light"
                        :content="$t('order.deposit')+'：' + currencyPool[orderItem.currency][lang][1] +(orderItem.moneyPaid + orderItem.memberCardBalance + orderItem.scoreDiscount + orderItem.useAccount).toFixed(2)"
                        placement="top-start"
                      >
                        <span>{{$t('order.deposit')+'：' + currencyPool[orderItem.currency][lang][1] +(orderItem.moneyPaid + orderItem.memberCardBalance + orderItem.scoreDiscount + orderItem.useAccount).toFixed(2)}}</span>
                      </el-tooltip>
                      <el-tooltip
                        v-if="orderItem.bkOrderMoney != 0"
                        class="item"
                        effect="light"
                        :content="$t('order.tail')+'：' + currencyPool[orderItem.currency][lang][1] + (orderItem.bkOrderMoney).toFixed(2)"
                        placement="top-start"
                      >
                        <span>{{$t('order.tail')+'：' + currencyPool[orderItem.currency][lang][1] + (orderItem.bkOrderMoney).toFixed(2)}}</span>
                      </el-tooltip>
                      <el-tooltip
                        class="item"
                        effect="light"
                        :content="orderItem.deliverType == 1 ? ($t('order.collectGoodsTime') + '：' + orderItem.pickupTime) : ($t('order.shippingTimeText') + '：' + orderItem.bkShippingTime)"
                        placement="top-start"
                      >
                        <span>{{orderItem.deliverType == 1 ? ($t('order.collectGoodsTime') + '：' + orderItem.pickupTime) : ($t('order.shippingTimeText') + '：' + orderItem.bkShippingTime)}}</span>
                      </el-tooltip>
                    </div>
                  </div>
                </td>
              </tr>
              <template v-for="(goodsItem,goodsIndex) in orderItem.goods">
                <tr
                  class="order-tb-body"
                  :key="goodsIndex"
                >
                  <td>
                    <div class="goods_info">
                      <img
                        :src="$imageHost+goodsItem.goodsImg"
                        alt=""
                      >
                      <div class="right_info">
                        <div class="goods_name">{{goodsItem.goodsName}}</div>
                        <div class="goods_spec">{{goodsItem.goodsAttr}}</div>
                      </div>
                    </div>
                  </td>
                  <td>{{goodsItem.goodsSn}}
                    <template v-if="goodsItem.productSn != '' && goodsItem.productSn != null">
                      /{{goodsItem.productSn}}
                    </template>
                  </td>
                  <td>
                    <template v-if="orderItem.goodsTypeArray.indexOf('4') != -1 ">
                      {{goodsItem.marketPrice.toFixed(2)}}
                    </template>
                    <template v-else>
                      {{goodsItem.goodsPrice.toFixed(2)}}
                    </template>
                  </td>
                  <td>{{goodsItem.goodsNumber}}</td>
                  <td
                    v-if="goodsIndex === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    <p>{{orderItem.consignee}}</p>
                    <p>{{orderItem.mobile}}</p>
                  </td>
                  <td
                    v-if="goodsIndex === 0"
                    :rowspan="orderItem.goods.length"
                  >

                    {{orderItem.createTime}}
                  </td>
                  <td
                    v-if="goodsIndex === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    <template v-if="orderItem.goodsTypeArray.indexOf('17') != -1 && orderItem.orderSn == orderItem.mainOrderSn && [8,10,13].indexOf(orderItem.orderStatus)">
                      {{$t('order.waitReceive')}}
                    </template>
                    <template v-else>
                      <template v-if="orderItem.orderStatus != 3 && orderItem.partShipFlag != 5">
                        <template v-if="orderItem.orderStatus == 0 && orderItem.goodsTypeArray.indexOf('10') != -1">
                          <template v-if="orderItem.bkOrderPaid == 0">
                            {{$t('order.waitDeposit')}}
                          </template>
                          <template v-else>
                            {{$t('order.waitTail')}}
                          </template>
                        </template>
                        <template v-else>
                          {{orderStatusMap.get(orderItem.orderStatus)}}
                        </template>
                      </template>
                      <template v-else>
                        <template v-if="orderItem.deliverType == 1 && orderItem.orderStatus == 3">
                          {{$t('order.waitverify')}}
                        </template>
                        <template v-else-if="orderItem.deliverType == 0 && orderItem.orderStatus == 3 && searchParams.pinStatus.length == 0">
                          {{$t('order.waitShip')}}
                        </template>
                        <template v-else-if="orderItem.deliverType == 1 && orderItem.orderStatus == 5">
                          {{$t('order.takeByself')}}
                        </template>
                        <template v-else-if="orderItem.deliverType == 0 && orderItem.orderStatus == 5">
                          {{$t('order.received')}}
                        </template>
                      </template>
                      <template v-if="orderItem.orderStatus == 3 && orderItem.partShipFlag == 1">
                        <br />
                        ({{$t('order.partShip')}})
                      </template>
                      <template v-if="orderItem.orderStatus == 3 && orderItem.deliverType != 1 && orderItem.canDeliver == true && searchParams.pinStatus.length == 0">
                        <!-- 非自提且待发货自提 -->
                        <br />
                        <el-button
                          type="primary"
                          size="small"
                          @click="deliver(orderItem)"
                        >{{$t('order.delivery')}}</el-button>
                        <template v-if="orderItem.canVerify == true">
                          <!-- 核销 -->
                          <br />
                          <el-button
                            type="primary"
                            size="small"
                            @click="verify(orderItem)"
                          >{{$t('order.verify')}}</el-button>
                        </template>
                      </template>
                    </template>
                    <template v-if="orderItem.refundStatus > 0">
                      <br />
                      <template v-if="[1,2,4].indexOf(orderItem.refundStatus) != -1">
                        <el-button type="text">{{$t('order.applyRetrunView')}}</el-button>
                      </template>
                      <template v-else>
                        <el-button type="text">{{$t('order.retrunView')}}</el-button>
                      </template>
                    </template>
                    <template v-if="orderItem.canClose == true">
                      <!-- 关闭 -->
                      <br />
                      <el-button
                        type="primary"
                        size="small"
                        @click="close(orderItem)"
                      >{{$t('order.close')}}</el-button>
                    </template>
                    <template v-if="orderItem.canFinish == true">
                      <!-- 完成 -->
                      <br />
                      <el-button
                        type="primary"
                        size="small"
                        @click="finish(orderItem)"
                      >{{$t('order.finish')}}</el-button>
                    </template>
                  </td>
                  <td
                    v-if="goodsIndex === 0"
                    :rowspan="orderItem.goods.length"
                  >
                    <template v-if="orderItem.goodsTypeArray.indexOf('17') != -1">
                      <span>
                        {{currencyPool[orderItem.currency][lang][1]}}
                        {{childOrder.subGoodsPrice.toFixed(2)}}
                      </span>
                      <br />
                      <span>
                        ({{
                        $t('order.freeShipping')
                      }})
                      </span>
                    </template>
                    <template v-else>
                      <template v-if="orderItem.goodsTypeArray.indexOf('4') == -1">
                        <span>
                          {{currencyPool[orderItem.currency][lang][1] +
                             (orderItem.bkOrderPaid > 1 ? (orderItem.moneyPaid + orderItem.bkOrderMoney) : orderItem.moneyPaid).toFixed(2)
                          }}
                        </span>
                        <br />
                        <span v-if="orderItem.deliverType != 1">
                          ({{
                             currencyPool[orderItem.currency][lang][1] + $t('order.includeExpress') + '：' + orderItem.shippingFee.toFixed(2)
                          }})
                        </span>
                      </template>
                      <template v-else>
                        <span>
                          {{currencyPool[orderItem.currency][lang][1] + orderItem.moneyPaid.toFixed(2) + ' + ' + (orderItem.scoreDiscount * 100) + $t('order.score')}}
                        </span>
                        <br />
                        <span>
                          ({{
                           $t('order.freeShipping')
                         }})
                        </span>
                      </template>
                    </template>
                  </td>
                </tr>
              </template>
              <template v-for="childOrder in orderItem.childOrders">
                <template v-for="(childGoods,childGoodsIndex) in childOrder.goods">
                  <tr
                    class="order-tb-body"
                    :key="orderItem.orderId + '' + childGoods.recId"
                  >
                    <td>
                      <p
                        style="text-align:left; margin-bottom:6px;"
                        v-if="childGoodsIndex == 0"
                      >{{$t('order.childOrderSn') + '：' + childOrder.orderSn}}</p>
                      <div class="goods_info">
                        <img
                          :src="$imageHost+childGoods.goodsImg"
                          alt=""
                        >
                        <div class="right_info">
                          <div class="goods_name">{{childGoods.goodsName}}</div>
                          <div class="goods_spec">{{childGoods.goodsAttr}}</div>
                        </div>
                      </div>
                    </td>
                    <td>{{childGoods.goodsSn}}</td>
                    <td>{{childGoods.goodsPrice.toFixed(2)}}</td>
                    <td>{{childGoods.goodsNumber}}</td>
                    <td
                      v-if="childGoodsIndex === 0"
                      :rowspan="childOrder.goods.length"
                    >
                      <p>{{childOrder.consignee}}</p>
                      <p>{{childOrder.mobile}}</p>
                    </td>
                    <td
                      v-if="childGoodsIndex === 0"
                      :rowspan="childOrder.goods.length"
                    >
                      {{orderItem.createTime}}
                    </td>
                    <td
                      v-if="childGoodsIndex === 0"
                      :rowspan="childOrder.goods.length"
                    >
                      <template>
                        <template v-if="childOrder.orderStatus != 3 && childOrder.partShipFlag != 5">
                          {{orderStatusMap.get(childOrder.orderStatus)}}
                        </template>
                        <template v-else>
                          <template v-if="childOrder.deliverType == 1 && childOrder.orderStatus == 3">
                            {{$t('order.waitverify')}}
                          </template>
                          <template v-else-if="childOrder.deliverType == 0 && childOrder.orderStatus == 3 && searchParams.pinStatus.length == 0">
                            {{$t('order.waitShip')}}
                          </template>
                          <template v-else-if="childOrder.deliverType == 1 && childOrder.orderStatus == 5">
                            {{$t('order.takeByself')}}
                          </template>
                          <template v-else-if="childOrder.deliverType == 0 && childOrder.orderStatus == 5">
                            {{$t('order.received')}}
                          </template>
                        </template>
                        <template v-if="childOrder.orderStatus == 3 && childOrder.partShipFlag == 1">
                          <br />
                          ({{$t('order.partShip')}})
                        </template>
                        <template v-if="childOrder.orderStatus == 3 && childOrder.deliverType != 1 && childOrder.canDeliver == true && searchParams.pinStatus.length == 0">
                          <!-- 非自提且待发货自提 -->
                          <br />
                          <el-button
                            type="primary"
                            size="small"
                            @click="deliver(childOrder)"
                          >{{$t('order.delivery')}}</el-button>
                        </template>
                        <template v-if="childOrder.canVerify == true">
                          <!-- 核销 -->
                          <br />
                          <el-button
                            type="primary"
                            size="small"
                            @click="verify(childOrder)"
                          >{{$t('order.verify')}}</el-button>
                        </template>
                      </template>
                      <template v-if="childOrder.refundStatus > 0">
                        <br />
                        <template v-if="[1,2,4].indexOf(childOrder.refundStatus) != -1">
                          <el-button type="text">{{$t('order.applyRetrunView')}}</el-button>
                        </template>
                        <template v-else>
                          <el-button type="text">{{$t('order.retrunView')}}</el-button>
                        </template>
                      </template>
                      <template v-if="childOrder.canClose == true">
                        <!-- 关闭 -->
                        <br />
                        <el-button
                          type="primary"
                          size="small"
                          @click="close(childOrder)"
                        >{{$t('order.close')}}</el-button>
                      </template>
                      <template v-if="childOrder.canFinish == true">
                        <!-- 完成 -->
                        <br />
                        <el-button
                          type="primary"
                          size="small"
                          @click="finish(childOrder)"
                        >{{$t('order.finish')}}</el-button>
                      </template>
                    </td>
                    <td
                      v-if="childGoodsIndex === 0"
                      :rowspan="childOrder.goods.length"
                    >
                      <span>
                        {{currencyPool[orderItem.currency][lang][1]}}
                        {{childOrder.subGoodsPrice.toFixed(2)}}
                      </span>
                      <br />
                      <span>
                        ({{
                        $t('order.freeShipping')
                      }})
                      </span>
                    </td>
                  </tr>
                </template>
              </template>
            </tbody>
            <tbody :key="orderIndex">
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
    <!-- 添加备注弹窗 -->
    <nodesDialog
      :show.sync="showNodes"
      :orderSn="notesOrderSn"
    />
    <!-- 发货弹窗 -->
    <deliveryDialog
      v-if="showDelivery"
      :show.sync="showDelivery"
      :orderData="orderItemInfo"
      @handlerResetData="search"
    />
    <!-- 订单导出选择列弹窗 -->
    <orderExportColumnSelectDialog
      :show.sync="showExportColumnSelect"
      @exportColumnSelectConfirm="handleExportColumnSelect"
    />
    <!-- 订单导出确认弹窗 -->
    <orderExportConfirmDialog
      :show.sync="showExportConfirm"
      :param="this.searchParams"
    />
  </div>
</template>
<script>

import {
  list, star, close, finish, verify
} from '@/api/admin/orderManage/order.js'

export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue'),
    nodesDialog: () => import('./addNotes'),
    deliveryDialog: () => import('./deliveryDialog'),
    orderExportColumnSelectDialog: () => import('./orderExportColumnSelect.vue'),
    orderExportConfirmDialog: () => import('./orderExportConfirmDialog.vue')
  },
  data () {
    return {
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
      moreFilters: false,
      pageParams: {},
      searchParams: {
        pinStatus: [],
        goodsName: '',
        orderSn: '',
        orderStatus: null,
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
        provinceCode: null,
        cityCode: null,
        districtCode: null,
        orderStatus2: null
      },
      orderTime: null,
      completeTime: null,
      sourceList: [
      ],
      storeList: [
      ],
      tagList: [
      ],
      tabsOrderStatus: [
        { value: null, label: '全部' },
        { value: '1', label: '待付款' },
        { value: '2', label: '待发货/待核销' },
        { value: '3', label: '已发货' },
        { value: '4', label: '已收货/已核销' },
        { value: '5', label: '已完成' },
        { value: '6', label: '退货退款中' },
        { value: '7', label: '已关闭' },
        { value: '8', label: '追星订单' }
      ],
      orderList: [
      ],
      showNodes: false,
      showDelivery: false,
      orderItemInfo: {},
      notesOrderSn: null,
      showExportColumnSelect: false,
      showExportConfirm: false
    }
  },
  inject: ['adminReload'],
  mounted () {
    console.log('mounted-----------------------')
    this.searchParams.orderStatus = this.$route.query.orderStatus ? this.$route.query.orderStatus : null
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  watch: {
    lang () {
      this.langDefault()
      this.arrayToMap()
    },
    '$route': {
      handler () {
        this.adminReload()
      }
    },
    completeTime (val) {
      this.searchParams.finishedTimeStart = val ? val[0] : null
      this.searchParams.finishedTimeEnd = val ? val[1] : null
    },
    orderTime (val) {
      this.searchParams.createTimeStart = val ? val[0] : null
      this.searchParams.createTimeEnd = val ? val[1] : null
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
    arrayToMap () {
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
      console.log(this.orderStatusMap)
      this.goodsTypeMap = new Map(this.$t('order.goodsTypeList'))
      this.deliverTypeMap = new Map(this.$t('order.deliverTypeList'))
      this.paymentTypeMap = new Map(this.$t('order.paymentTypeList'))
    },
    search () {
      this.searchParams.pinStatus = this.$route.query.pinStatus ? this.$route.query.pinStatus.split(',') : []
      this.searchParams.currentPage = this.pageParams.currentPage
      this.searchParams.pageRows = this.pageParams.pageRows
      this.searchType = 0
      let obj = {
        ...this.searchParams,
        orderStatus: this.searchParams.orderStatus ? [this.searchParams.orderStatus] : []
      }
      list(obj).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.orderList = res.content.dataList
      }).catch(() => {
      })
    },
    initDataList () {
      this.search()
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
    addNodes (orderSn) {
      this.showNodes = true
      this.notesOrderSn = orderSn
    },
    goodsTypeFilter (goodsType) {
      let goodsTypeStr = this.$t('order.goodsTypeText') + '：'
      goodsType.map((item, index) => {
        goodsTypeStr += this.goodsTypeMap.get(Number(item))
        if (index !== goodsType.length - 1) {
          goodsTypeStr += ','
        }
      })
      return goodsTypeStr
    },
    setGoodsTypeArray (orderItem) {
      orderItem.goodsTypeArray = orderItem.goodsType.substring(1, orderItem.goodsType.length - 1).split('][')
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
      this.$confirm('确认关闭订单吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        close(obj).then(res => {
          if (res.error === 0) {
            this.search()
          }
        })
      }).catch(() => {
      })
    },
    finish (orderInfo) {
      let obj = {
        orderId: orderInfo.orderId,
        orderSn: orderInfo.orderSn,
        action: 5
      }
      finish(obj).then(res => {
        if (res.error === 0) {
          this.search()
        }
      })
    },
    toggleStar (orderSn, starFlag) {
      let obj = {
        orderSn: [orderSn],
        type: 0,
        starFlag: starFlag === 1 ? 0 : 1
      }
      star(obj).then(res => {
        if (res.error === 0) {
          this.$message.success(starFlag === 1 ? '取消标星成功' : '标星成功')
          this.search()
        }
      })
    },
    handleExportColumnSelect () {
      this.showExportConfirm = true
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
                  > span {
                    width: 190px;
                    text-align: left;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    cursor: pointer;
                    &:first-of-type {
                      width: 260px;
                    }
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
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  }
                  .icon_collect {
                    font-size: 20px;
                    .el-icon-star-off {
                      color: #666;
                    }
                    .el-icon-star-on {
                      color: #e96463;
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
              line-height: 24px;
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
                  line-height: 1;
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
