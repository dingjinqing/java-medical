<template>
  <div class="card-suite-div">
    <el-form
      :model="ruleForm"
      status-icon
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item
        :label="$t('memberCard.suiteableGoods')"
        class="suite-goods-item"
      >
        <div class="suite-goods-top">
          <el-radio
            v-model="ruleForm.isExchange"
            label="0"
          >不可兑换商品</el-radio>
          <el-radio
            v-model="ruleForm.isExchange"
            label="1"
          >部分商品</el-radio>
          <el-radio
            v-model="ruleForm.isExchange"
            label="2"
          >全部商品</el-radio>
        </div>
        <div
          v-if="ruleForm.isExchange !== '0'"
          class="suite-goods-middle"
        >
          <div class="goods-exchange-times">
            <span>允许兑换：</span>
            <el-input-number
              v-model="ruleForm.exchangCount"
              :controls="false"
              :min="0"
              :max="999999999"
              size="small"
            ></el-input-number>
            <span>次</span>
          </div>
          <div class="goods-exchange-freight">
            <span>运费策略：</span>
            <el-radio
              v-model="ruleForm.exchangFreight"
              label='0'
            >
              免运费
            </el-radio>
            <el-radio
              v-model="ruleForm.exchangFreight"
              label="1"
            >使用商品运费策略
            </el-radio>
          </div>
        </div>
        <div
          v-if="ruleForm.isExchange === '1'"
          class="suite-goods-bottom"
        >
          <div
            class="choose-goods"
            @click="callGoodsDialog()"
          >
            <img :src="$imageHost+'/image/admin/icon_jia.png'">
            <span>选择商品</span>
          </div>
          <div class="already-choosed-goods">
            <span v-if="ruleForm.exchangGoods.length">已选择商品：{{ruleForm.exchangGoods.length}}件</span>
            <span class="choose-tip">
              最多可选择20件
            </span>
          </div>
        </div>
      </el-form-item>
    </el-form>
    <!--选择商品弹窗-->
    <ChoosingGoods
      @resultGoodsIds='getGoodsIdFromChoosingGoods'
      :tuneUpChooseGoods='chooseGoodsVisable'
      :chooseGoodsBack='ruleForm.exchangGoods'
    />
  </div>
</template>
<script>
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods')
  },
  props: {
    val: {
      type: Object,
      default: () => {
        return {
          isExchange: '1',
          exchangCount: '',
          exchangFreight: '0',
          exchangGoods: []
        }
      }
    }
  },
  computed: {
    ruleForm: {
      get () {
        return this.val
      },
      set () {
        this.$emit('input', this.ruleForm)
      }
    }
  },
  data () {
    return {
      chooseGoodsVisable: false
    }
  },
  methods: {
    callGoodsDialog () {
      this.chooseGoodsVisable = !this.chooseGoodsVisable
    },
    getGoodsIdFromChoosingGoods (val) {
      console.log('选择适用商品id', val)
      this.ruleForm.exchangGoods = val
    }
  }
}
</script>
<style scoped lang="scss">
.card-suite-div {
  .suite-goods-item {
    padding-left: 100px;
    /deep/ .el-form-item__label {
      width: 86px !important;
    }
    .suite-goods-top {
    }
    .suite-goods-middle {
      .goods-exchange-times {
        /deep/ .el-input {
          width: 90%;
        }
      }
      .goods-exchange-freight {
      }
    }
    .suite-goods-bottom {
      display: flex;
      .choose-goods {
        color: #5a8bff;
        border: 1px solid #ccc;
        background: #fff;
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        margin-right: 20px;
        cursor: pointer;
      }
      .already-choosed-goods {
        color: #5a8bff;
        cursor: pointer;
        height: 30px;
        line-height: 30px;
        .choose-tip {
          margin-left: 20px;
          color: #999;
          height: 30px;
          line-height: 30px;
        }
      }
    }
  }
}
</style>
