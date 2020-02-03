<template>
  <div class="main" v-loading="loading">
    <div class="order_info">
      <p>{{ $t("order.orderSn") }}：{{ $route.query.orderSn }}</p>
      <p>{{ $t("order.orderTime") }}：{{ $route.query.orderTime }}</p>
      <div>
        <el-button type="primary" size="small">{{
          $t("order.backRefundList")
        }}</el-button>
      </div>
    </div>
    <div class="return_info">
      <p>
        {{ $t("order.returnRefundType") }}：
        <el-select
          v-model="params.returnType"
          size="small"
          class="mini_input"
          @change="returnTypeChange"
        >
          <el-option
            v-for="(item, index) in returnType"
            :key="index"
            :label="new Map($t('order.returnTypeList')).get(item)"
            :value="item"
          >
          </el-option>
        </el-select>
      </p>
      <el-table
        :data="refundGoods"
        border
        ref="multipleTable"
        v-if="params.returnType != 2"
        :header-cell-style="{
          'background-color': '#f5f5f5',
          'text-align': 'center',
          border: 'none'
        }"
        :cell-style="{
          'text-align': 'center'
        }"
        @selection-change="selectionChange"
      >
        <el-table-column type="selection" width="55" :selectable="selectable">
        </el-table-column>
        <el-table-column :label="$t('order.goodsName')">
          <template slot-scope="scope">
            <div>
              <el-tag
                type="danger"
                effect="plain"
                size="mini"
                v-if="scope.row.isGift"
              >
                赠品 </el-tag
              >{{ scope.row.goodsName }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsAttr"
          :label="$t('order.specText')"
        ></el-table-column>
        <el-table-column
          prop="goodsPrice"
          :formatter="formatMoney"
          :label="$t('order.goods') + $t('order.goodsPrice')"
        ></el-table-column>
        <el-table-column
          prop="discountedGoodsPrice"
          :formatter="formatMoney"
          :label="$t('order.purchaseDiscountedPrice')"
        ></el-table-column>
        <el-table-column
          :label="
            `${$t('order.canReturnNum')}/${$t('order.submitted')}/${$t(
              'order.totalNum'
            )}`
          "
        >
          <template slot-scope="scope">
            <div>
              {{ scope.row.returnable }}/{{ scope.row.submitted }}/{{
                scope.row.total
              }}
            </div>
          </template>
        </el-table-column>
        <template v-if="[0, 1].indexOf(params.returnType) != -1">
          <el-table-column
            prop="returnable"
            :label="`${$t('order.refund')}/${$t('order.returnNumText')}`"
          >
            <template
              v-if="[0, 1].indexOf(params.returnType) != -1"
              slot-scope="scope"
            >
              <!-- template重复判断if,切换退款类型会占位 -->
              <el-input-number
                v-model="scope.row.canRefundNum"
                class="mini_input"
                size="small"
                controls-position="right"
                @change="selectionChange"
                :min="0"
                :max="scope.row.returnable"
              ></el-input-number>
            </template>
          </el-table-column>
          <el-table-column
            prop="discountedGoodsPrice"
            :formatter="formatMoney"
            :label="$t('order.totalPriceAfterReturningGoods')"
          ></el-table-column>
        </template>
        <template v-else>
          <el-table-column
            prop="returnMoney"
            :formatter="formatMoney"
            :label="$t('order.returned')"
          ></el-table-column>
          <el-table-column :label="$t('order.returnMostAmount')">
            <template v-if="params.returnType == 3" slot-scope="scope">
              <!-- template重复判断if,切换退款类型会占位 -->
              {{
                computeReturnMostAmount(
                  scope.row,
                  scope.row.returnNumber,
                  scope.row.discountedGoodsPrice,
                  scope.row.returnMoney
                ).toFixed(2)
              }}
            </template>
          </el-table-column>
          <el-table-column :label="$t('order.returnMoney')">
            <template v-if="params.returnType == 3" slot-scope="scope">
              <!-- template重复判断if,切换退款类型会占位 -->
              <el-input-number
                v-model="scope.row.returnOneGoodsAmount"
                size="small"
                controls-position="right"
                :precision="2"
                :controls="false"
                @change="selectionChange"
                :min="0"
                :max="scope.row.returnMostAmount"
              ></el-input-number>
            </template>
          </el-table-column>
        </template>
      </el-table>
      <div class="return_item">
        <div class="item_title">{{ $t("order.refundPrice") }}：</div>
        <div class="item_content money_set">
          <p>
            <span v-if="params.returnType != 2"
              >{{ $t("order.refundGoodsPrice") }}：
              <el-input-number
                v-model="canRefundPrice"
                :disabled="params.returnType == 3"
                :precision="2"
                class="mini_input"
                size="small"
                controls-position="right"
                :min="0"
                :max="max_refund_price"
              >
              </el-input-number
              >{{ currency[0] }}，</span
            >
            <span>
              {{ $t("order.returnShippingFee") }}：
              <el-input-number
                v-model="params.shippingFee"
                :precision="2"
                class="mini_input"
                size="small"
                controls-position="right"
                :min="0"
                :max="returnShippingFee"
              ></el-input-number
              >{{ currency[0] }}, {{ $t("order.maxRefundShippingFee") }}：{{
                returnShippingFee.toFixed(2)
              }}{{ currency[0] }}
            </span>
          </p>
          <p>
            {{ $t("order.totalRefundPrice") }}：{{ currency[1]
            }}<span class="text-warning">{{
              refundtotalPrice.toFixed(2)
            }}</span>
            ={{ $t("order.refundMemberCardBalance") }}：{{ currency[1]
            }}<span class="text-warning">{{
              member_card_balance.toFixed(2)
            }}</span>
            +{{ $t("order.refundBalanceMoney") }}：{{ currency[1]
            }}<span class="text-warning">{{
              refund_balance_money.toFixed(2)
            }}</span
            >+{{ $t("order.refundScoreMoney") }}：{{ currency[1]
            }}<span class="text-warning">{{
              refund_score_money.toFixed(2)
            }}</span>
            + {{ $t("order.refundPayMoney") }}：{{ currency[1]
            }}<span class="text-warning">{{
              refund_pay_money.toFixed(2)
            }}</span>
          </p>
          <p class="text-warning">{{ $t("order.refundTips") }}</p>
        </div>
      </div>
      <div class="return_item">
        <div class="item_title">{{ $t("order.refundReason") }}：</div>
        <div class="item_content">
          <el-select
            v-model="params.reasonType"
            size="small"
            class="default_input"
          >
            <el-option
              v-for="(item, index) in $t('order.reasonTypeList')"
              :key="index"
              :value="index"
              :label="item"
            ></el-option>
          </el-select>
        </div>
      </div>
      <div class="return_item">
        <div class="item_title">
          {{ $t("order.refundReasonDescription") }}：
        </div>
        <div class="item_content">
          <el-input
            type="textarea"
            v-model="params.refundRefuseReason"
            :rows="5"
            class="textarea_width"
            resize="none"
          ></el-input>
        </div>
      </div>
      <div class="return_item">
        <div class="item_title">{{ $t("order.voucherPicture") }}：</div>
        <div class="item_content">
          <img
            :src="
              params.voucherImages
                ? $imageHost + '/' + params.voucherImages
                : ' '
            "
            class="bgImgDiv"
            @click="handleToAddImg()"
            :style="
              `backgroundImage:url(${$imageHost}/image/admin/add_img.png);backgroundRepeat:no-repeat`
            "
          />
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button type="primary" size="small" @click="comfirmRefund"
        >确定</el-button
      >
    </div>
    <ImageDalog
      pageIndex="userCardAdd"
      :tuneUp="tuneUp"
      @handleSelectImg="handleSelectImg"
    />
  </div>
</template>

<script>
import { manualReturnInfo, manualReturn } from '@/api/admin/orderManage/order.js'

export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog')
  },
  data () {
    return {
      params: {
        returnType: 0,
        reasonType: 0,
        refundRefuseReason: null,
        voucherImages: null,
        returnMoney: null,
        shippingFee: 0
      },
      refundData: null,
      returnType: [],
      reasonType: [],
      returnShippingFee: 0.00,
      refundGoods: null,
      returnAmountMap: {
        bk_order_money: 0,
        member_card_balance: 0,
        use_account: 55.00,
        score_discount: 0,
        money_paid: 0
      },
      tuneUp: false,
      canRefundPrice: 0.00,
      member_card_balance: 0.00,
      refund_balance_money: 0.00,
      refund_score_money: 0.00,
      refund_pay_money: 0.00,
      bk_order_money: 0.00,
      max_refund_price: 0.00,
      loading: false
    }
  },
  methods: {
    handleToAddImg () {
      this.tuneUp = !this.tuneUp
    },
    handleSelectImg (res) {
      console.log(res.imgPath)
      this.params.voucherImages = res.imgPath
    },
    selectionChange (val) {
      this.canRefundPrice = this.$refs.multipleTable.selection.reduce((total, currentValue) => {
        if (this.params.returnType === 3) {
          return total + currentValue.returnOneGoodsAmount
        } else {
          return total + currentValue.discountedGoodsPrice * currentValue.canRefundNum
        }
      }, 0)
      this.max_refund_price = this.canRefundPrice
    },
    returnTypeChange (changeVal) {
      if (changeVal === 2) {
        this.canRefundPrice = 0.00
      }
      this.$refs.multipleTable && this.$refs.multipleTable.clearSelection()
    },
    initData () {
      this.loading = true
      let obj = {
        orderId: this.$route.query.orderId,
        orderSn: this.$route.query.orderSn,
        action: 1
      }
      manualReturnInfo(obj).then(res => {
        if (res.error === 0) {
          this.refundGoods = res.content.refundGoods.map(item => {
            item.canRefundNum = item.returnable
            return item
          })
          let returnTypeArr = []
          res.content.returnType.forEach((item, index) => {
            if (item === true) {
              returnTypeArr.push(index)
            }
          })
          this.returnType = returnTypeArr
          this.params.returnType = this.returnType[0]
          this.returnAmountMap = res.content.returnAmountMap
          this.returnShippingFee = res.content.returnShippingFee
          this.loading = false
        }
      })
    },
    comfirmRefund () {
      if (this.params.returnType === 2) {
        if (this.params.shippingFee <= 0) {
          this.$message.error('仅退运费时运费必须大于0')
          return
        }
      }
      let obj = {
        orderId: this.$route.query.orderId,
        orderSn: this.$route.query.orderSn,
        action: 1,
        returnType: this.params.returnType,
        returnMoney: this.canRefundPrice,
        shippingFee: this.params.shippingFee,
        reasonType: this.params.reasonType,
        voucherImages: this.params.voucherImages
      }
      obj.returnGoods = this.params.returnType === 2 ? null : []
      if (Array.isArray(obj.returnGoods)) {
        let returnGoods = []
        this.$refs.multipleTable.selection.forEach(item => {
          returnGoods = [...returnGoods, {
            recId: item.recId,
            returnNumber: this.params.returnType !== 3 ? item.canRefundNum : 0,
            money: this.params.returnType !== 3 ? null : item.returnOneGoodsAmount }]
        })
        obj.returnGoods = returnGoods
      }
      console.log(obj)
      manualReturn(obj).then(res => {
        console.log(res)
      })
    },
    formatMoney (row, column, cellValue) {
      return cellValue.toFixed(2)
    },
    computeReturnMostAmount (row, returnNumber, discountedGoodsPrice, returnMoney) {
      let returnMostAmount = returnNumber * discountedGoodsPrice - returnMoney
      if (row !== null) {
        row.returnMostAmount = returnMostAmount
        // 设置当前商品行退款金额
        if (!row.returnOneGoodsAmount) {
          row.returnOneGoodsAmount = returnMostAmount
        }
      }
      return returnMostAmount
    },
    selectable (row, index) {
      if (this.params.returnType !== 3) {
        return true
      }
      if (this.computeReturnMostAmount(null, row.returnNumber, row.discountedGoodsPrice, row.returnMoney) === 0) {
        return false
      }
      return true
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initData()
  },
  watch: {
    lang () {
      this.langDefault()
    },
    refundtotalPrice (newVal) {
      let totalPrice = newVal
      this.bk_order_money = totalPrice > this.returnAmountMap.bk_order_money ? this.returnAmountMap.bk_order_money : totalPrice
      totalPrice -= this.bk_order_money

      this.member_card_balance = totalPrice > this.returnAmountMap.member_card_balance ? this.returnAmountMap.member_card_balance : totalPrice
      totalPrice -= this.member_card_balance

      this.refund_balance_money = totalPrice > this.returnAmountMap.use_account ? this.returnAmountMap.use_account : totalPrice
      totalPrice -= this.refund_balance_money

      this.refund_score_money = totalPrice > this.returnAmountMap.score_discount ? this.returnAmountMap.score_discount : totalPrice
      totalPrice -= this.refund_score_money

      this.refund_pay_money = totalPrice > this.returnAmountMap.money_paid ? this.returnAmountMap.money_paid : totalPrice
      totalPrice -= this.refund_pay_money
    }
  },
  computed: {
    refundtotalPrice () {
      return this.params.shippingFee + this.canRefundPrice
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  font-size: 14px;
  color: #333;
  .order_info {
    display: flex;
    flex-direction: column;
    background-color: #fff;
    padding: 10px;
    > p {
      margin-bottom: 15px;
    }
  }
  .return_info {
    margin-top: 10px;
    background-color: #fff;
    padding: 10px;
    margin-bottom: 52px;
    > .el-table {
      margin-top: 10px;
    }
    .return_item {
      display: flex;
      align-items: center;
      margin-top: 10px;
      .item_title {
        width: 130px;
      }
      .item_content {
        flex: 1;
        &.money_set {
          margin-bottom: -10px;
          > p {
            margin-bottom: 10px;
          }
        }
        .bgImgDiv {
          width: 65px;
          height: 65px;
          border: 1px solid #ccc;
          background-position: center;
          cursor: pointer;
        }
      }
    }
  }
  .footer {
    position: fixed;
    bottom: 0;
    left: 160px;
    right: 10px;
    height: 52px;
    padding: 10px 0;
    background-color: #fff;
    text-align: center;
    z-index: 3;
  }
  .default_input {
    width: 180px;
  }
  .textarea_width {
    width: 300px;
  }
  .mini_input {
    width: 120px;
  }
  .text-warning {
    color: #c09853;
  }
}
</style>
