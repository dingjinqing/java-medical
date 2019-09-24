<template>
  <div class="main">
    <div class="order_info">
      <p>{{$t('order.orderSn')}}：</p>
      <p>{{$t('order.orderTime')}}：</p>
      <div>
        <el-button
          type="primary"
          size="small"
        >{{$t('order.backRefundList')}}</el-button>
      </div>
    </div>
    <div class="return_info">
      <p>
        {{$t('order.returnRefundType')}}：
        <el-select
          v-model="params.returnType"
          size="small"
          class="mini_input"
          @change="returnTypeChange"
        >
          <el-option
            v-for="item in returnType"
            :key="item.key"
            :label="item.label"
            :value="item.key"
          >
          </el-option>
        </el-select>
      </p>
      <el-table
        :data="refundGoods"
        style="width:100%;"
        border
        ref="multipleTable"
        v-if="params.returnType != 2"
        :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
        :cell-style="{
            'text-align':'center'
          }"
        @selection-change="selectionChange"
      >
        <el-table-column
          type="selection"
          width="55"
        >
        </el-table-column>
        <el-table-column label="商品名称">
          <template slot-scope="scope">
            <div>
              <el-tag
                type="danger"
                effect="plain"
                size="mini"
              >
                赠品
              </el-tag>{{scope.row.goodsName}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsAttr"
          label="规格"
        ></el-table-column>
        <el-table-column
          prop="goodsPrice"
          label="商品价格"
        ></el-table-column>
        <el-table-column
          prop="discountedGoodsPrice"
          label="购买折后价格"
        ></el-table-column>
        <el-table-column label="可退货数量/已提交/总数量">
          <template slot-scope="scope">
            <div>
              {{scope.row.returnable}}/{{scope.row.submitted}}/{{scope.row.total}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="returnable"
          label="退款/退货数量"
        >
          <template slot-scope="scope">
            <el-input-number
              v-model="scope.row.canRefundNum"
              class="mini_input"
              size="small"
              @change="selectionChange"
              :min="0"
              :max="scope.row.returnable"
            ></el-input-number>
          </template>
        </el-table-column>
        <el-table-column
          prop="discountedGoodsPrice"
          label="退货商品折后总计金额"
        ></el-table-column>
      </el-table>
      <div class="return_item">
        <div class="item_title">退款/退货金额：</div>
        <div class="item_content money_set">
          <p>
            <span v-if="params.returnType != 2">退商品金额：<el-input-number
                v-model="canRefundPrice"
                :precision="2"
                class="mini_input"
                size="small"
                :min="0"
                :max="max_refund_price"
              >
              </el-input-number>{{currency[0]}}，</span>
            <span>
              退运费金额：
              <el-input-number
                v-model="params.shippingFee"
                :precision="2"
                class="mini_input"
                size="small"
                :min="0"
              ></el-input-number>{{currency[0]}},
              可退最大运费：0.00{{currency[0]}}
            </span>
          </p>
          <p>总退款金额：{{currency[1]}}<span class="text-warning">{{canRefundPrice.toFixed(2)}}</span> =退会员卡余额：{{currency[1]}}<span class="text-warning">{{member_card_balance.toFixed(2)}}</span> +退余额：{{currency[1]}}<span class="text-warning">{{refund_balance_money.toFixed(2)}}</span>+退积分抵扣：{{currency[1]}}<span class="text-warning">{{refund_score_money.toFixed(2)}}</span> + 退支付金额：{{currency[1]}}<span class="text-warning">{{refund_pay_money.toFixed(2)}}</span> </p>
          <p class="text-warning">注：总退款金额 = 退商品金额 + 退运费金额，扣款优先级： 员卡余额，余额，积分，支付金额</p>
        </div>
      </div>
      <div class="return_item">
        <div class="item_title">退款/退货原因：</div>
        <div class="item_content">
          <el-select
            v-model="params.reasonType"
            size="small"
            class="default_input"
          >
            <el-option
              v-for="item in reasonType"
              :key="item.key"
              :value="item.key"
              :label="item.label"
            ></el-option>
          </el-select>
        </div>
      </div>
      <div class="return_item">
        <div class="item_title">退款/退货原因说明：</div>
        <div class="item_content">
          <el-input
            type="textarea"
            v-model="params.reason"
            placeholder="请输入退款/退货原因说明"
            :rows="5"
            class="textarea_width"
            resize="none"
          ></el-input>
        </div>
      </div>
      <div class="return_item">
        <div class="item_title">凭证图片：</div>
        <div class="item_content">
          <img
            :src="params.voucherImages ? $imageHost +'/' + params.voucherImages : ' '"
            class="bgImgDiv"
            @click="handleToAddImg()"
            :style="`backgroundImage:url(${$imageHost}/image/admin/add_img.png);backgroundRepeat:no-repeat`"
          />
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
      >确定</el-button>
    </div>
    <ImageDalog
      pageIndex='userCardAdd'
      :tuneUp="tuneUp"
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>

<script>
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog')
  },
  data () {
    return {
      params: {
        returnType: 0,
        reasonType: 0,
        reason: null,
        voucherImages: null,
        returnMoney: null,
        shippingFee: null
      },
      returnType: [
        { key: 0, label: '仅退款' },
        { key: 1, label: '退货退款' },
        { key: 2, label: '仅退运费' },
        { key: 3, label: '手动退款' }
      ],
      reasonType: [
        { key: 0, label: '协商一致退款' },
        { key: 1, label: '未按约定时间发货' },
        { key: 2, label: '缺货' },
        { key: 3, label: '排错/多拍/不想要' },
        { key: 4, label: '其他' }
      ],
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
      max_refund_price: 0.00
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
        return total + currentValue.discountedGoodsPrice * currentValue.returnable
      }, 0)
      this.max_refund_price = this.canRefundPrice
    },
    returnTypeChange (changeVal) {
      if (changeVal === 2) {
        this.canRefundPrice = 0.00
      }
    },
    initData () {
      let res = [
        {
          recId: 8313,
          orderId: 6751,
          orderSn: 'P201908261402467301',
          productId: 4726,
          goodsName: '首单限时优化首单限时优化首单限时优化首单限时优化首单限时优化-CJ',
          goodsAttr: '颜色:白色',
          goodsPrice: 55.00,
          discountedGoodsPrice: 25.00,
          returnable: 1,
          submitted: 0,
          total: 1,
          isCanReturn: 1,
          isGift: 0
        },
        {
          recId: 8314,
          orderId: 6752,
          orderSn: 'P201908261402467301',
          productId: 4726,
          goodsName: '首单限时优化-CJ',
          goodsAttr: '颜色:白色',
          goodsPrice: 55.00,
          discountedGoodsPrice: 30.00,
          returnable: 1,
          submitted: 0,
          total: 1,
          isCanReturn: 1,
          isGift: 0
        }
      ]
      this.refundGoods = res.map(item => {
        item.canRefundNum = item.returnable
        return item
      })
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
    canRefundPrice (newVal) {
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
