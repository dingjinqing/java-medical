<template>
  <div class="card-suite-div">
    <el-form
      :model="ruleForm"
      status-icon
      ref="ruleForm"
      label-width="100px"
      @submit.native.prevent
    >
      <el-form-item
        :label="$t('memberCard.suiteableGoods')"
        class="suite-goods-item"
      >
        <div class="suite-goods-top">
          <el-radio
            v-model="ruleForm.isExchange"
            :label="0"
          >不可兑换商品</el-radio>
          <el-radio
            v-model="ruleForm.isExchange"
            :label="1"
          >部分商品</el-radio>
          <el-radio
            v-model="ruleForm.isExchange"
            :label="2"
          >全部商品</el-radio>
        </div>
        <div
          v-if="Number(ruleForm.isExchange) !== 0"
          class="suite-goods-middle"
        >
          <div class="goods-exchange-times">
              <p class="tip">
                注：以下"次"数均为兑换商品次数,例：兑换一瓶水计为一次
              </p>
              <div>
                <span>商品兑换总次数：</span>
                <el-input-number
                  v-model="ruleForm.exchangCount"
                  :controls="false"
                  :min="0"
                  :max="999999999"
                  size="small"
                ></el-input-number>
                <span>次</span>
              </div>
              <div v-if="ruleForm.isExchange === 2">
                <span>每件商品可兑换：</span>
                 <el-input-number
                  v-model="ruleForm.everyGoodsMaxNum"
                  :controls="false"
                  :min="0"
                  :max="999999999"
                  size="small"
                ></el-input-number>
                <span>次</span>
              </div>
          </div>
        </div>

        <div class="goods-container"
            v-if="ruleForm.isExchange === 1"
           >
           <div class="row-container"
            v-for="(item,index) in ruleForm.exchangGoods"
            :key=index>
              <div class="suite-goods-bottom">
                <p class="left-title">选择商品: </p>
                <div
                  class="choose-goods"
                  @click="callGoodsDialog(false,index)"
                >
                  <img :src="$imageHost+'/image/admin/icon_jia.png'">
                  <span>添加商品</span>
                </div>
                <div
                  class="already-choosed-goods"
                  @click="callGoodsDialog(true,index)"
                >
                  <span v-if="item.goodsIds.length">已选择商品：{{item.goodsIds.length}}件</span>
                  <span class="choose-tip">
                    最多可选择20件
                  </span>
                </div>
              </div>
              <div class="goods-num">
                <p class="left-title">每件商品可兑换： </p>
                <div class="num"><el-input v-model="ruleForm.exchangGoods[index].maxNum" size="small"></el-input></div>
                <span style="margin-left: 10px;">次</span>
              </div>
              <el-tooltip content="删除" placement="top" effect="light" class="del-icon">
                  <i class="el-icon-delete icon-style" @click="deleteGoodsItem(index)"></i>
              </el-tooltip>
           </div>
          <div class="add-goods">
            <span class="btn" @click="addNewGoodsCfg">添加商品限购配置</span>
            <span class="tip">最多可添加5个配置</span>
          </div>
        </div>

        <div class="goods-exchange-freight" v-if="Number(ruleForm.isExchange) !== 0">
            <span>运费策略：</span>
            <el-radio
              v-model="ruleForm.exchangFreight"
              :label='0'
            >
              免运费
            </el-radio>
            <el-radio
              v-model="ruleForm.exchangFreight"
              :label="1"
            >使用商品运费策略
            </el-radio>
        </div>
        <div class="exchang-time" v-if="Number(ruleForm.isExchange) !== 0">
          <ul class="time-row">
            <li class="time-cell">兑换时间限制：</li>
            <li class="time-cell">
                <el-radio
                  v-model="ruleForm.exchangTimeRadio"
                  label="0"
                >
                    不限制
                </el-radio>
            </li>
          </ul>
          <ul class="time-row">
            <li class="time-cell"></li>
            <li class="time-cell">
                <el-radio
                  v-model="ruleForm.exchangTimeRadio"
                  label="1"
                >
                    有效期内每
                </el-radio>
                <el-select v-model="ruleForm.exchangTimeType" size="small" style="width: 110px;">
                  <el-option v-for="item in exchangTimeOpt"
                    :key="item.id"
                    :label="item.label"
                    :value="item.id">
                  </el-option>
                </el-select>
                <span>兑换</span>
                <el-input v-model.number="ruleForm.exchangTimeNum" size="small" style="width: 110px;"></el-input>
                <span>次</span>
            </li>
          </ul>

        </div>
      </el-form-item>
    </el-form>
    <!--选择商品弹窗-->
    <ChoosingGoods
      @resultGoodsIds='getGoodsIdFromChoosingGoods'
      :tuneUpChooseGoods='chooseGoodsVisable'
      :chooseGoodsBack='ruleForm.exchangGoods[currentIndex].goodsIds'
      :onlyShowChooseGoods="isOnlyShowChooseGoods"
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
          isExchange: 1,
          exchangCount: '',
          everyGoodsMaxNum: '',
          exchangFreight: 0,
          exchangGoods: [{goodsIds: [], maxNum: null}],
          exchangTimeType: '0',
          exchangTimeNum: null,
          exchangTimeRadio: '0'
        }
      }
    }
  },
  watch: {
    lang (newValue, oldValue) {
      this.initExchangTimeOpt()
    }
  },
  mounted () {
    this.langDefault()
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
      chooseGoodsVisable: false,
      isOnlyShowChooseGoods: false,
      currentGoodsId: null,
      currentIndex: 0,
      suiteGoodsCfg: {goodsIds: [], maxNum: null},
      exchangTimeOpt: []
    }
  },
  methods: {
    callGoodsDialog (only, index) {
      this.chooseGoodsVisable = !this.chooseGoodsVisable
      this.isOnlyShowChooseGoods = only
      this.currentIndex = index
    },
    getGoodsIdFromChoosingGoods (val) {
      this.ruleForm.exchangGoods[this.currentIndex].goodsIds = val
    },
    addNewGoodsCfg () {
      if (this.ruleForm.exchangGoods.length > 5) {
        this.$message.warning('最多添加5个配置')
      } else {
        this.ruleForm.exchangGoods.push({goodsIds: [], maxNum: null})
      }
    },
    deleteGoodsItem (index) {
      console.log(index)
      this.ruleForm.exchangGoods.splice(index, 1)
    },
    initExchangTimeOpt () {
      this.exchangTimeOpt = []
      const options = this.$t('memberCard.exchangTime')
      for (let id = 1; id < 6; id++) {
        this.exchangTimeOpt.push({
          id,
          label: options[id - 1]
        })
      }
    }
  }
}
</script>
<style scoped lang="scss">
*,/deep/ .el-form-item__label,
/deep/ .el-radio__label,
/deep/ .el-checkbox__label{
  font-size: 13px;
}
.card-suite-div {
  .suite-goods-item {
    padding-left: 75px;
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
      p.tip{
        color: #999;
        height: 30px;
        line-height: 30px;
      }
    }
    div.row-container{
      border: 1px solid #ddd;
      width: 75%;
      padding: 20px 10px;
      background-color: #ffffff;
      margin-bottom: 10px;
      position: relative;
    }
    .row-container .del-icon{
      position: absolute;
      top: 15px;
      right: 15px;
    }
    .suite-goods-bottom {
      display: flex;
      vertical-align: middle;
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
    p.left-title{
        height: 30px;
        line-height: 30px;
        margin-right: 10px;
      }
    .goods-num{
      display: flex;
      margin-top: 20px;
      vertical-align: middle;
      height: 30px;
      line-height: 30px;
      div.num{
        width: 110px;
      }
    }
  }
  span.btn{
    color: #5a8bff;
    border: 1px solid #5a8bff;
    border-radius: 4px;
    padding: 5px 6px;
    line-height: 30px;
    cursor: pointer;
  }

  .icon-style{
        font-size: 22px;
        color: #5a8bff;
        cursor: pointer;
    }
    .add-goods .tip{
      color: #999;
      margin-left: 10px;
    }

    div.exchang-time{
      display: table;
      empty-cells: hide;
      .time-row{
        display: table-row;
      }
      .time-cell{
        display: table-cell;
      }
    }
}
</style>
