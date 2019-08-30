<!--
* 创建定金膨胀活动
*
* @author 郑保乐
-->
<template>
  <div>
    <wrapper>
        <el-row style="margin-bottom:20px">
          <el-col :span="8">
            <el-form label-width="100px" >
              <el-form-item label="活动类型">
                <el-radio v-for="(item, index) in presaleTypes" :key="index"
                    v-model="param.presaleType" :label="index" >{{item}}</el-radio>
              </el-form-item>
              <el-form-item label="活动名称">
                <el-input v-model="param.presaleName"></el-input>
              </el-form-item>
              <!-- 定金膨胀 -->
              <el-form-item v-show="param.presaleType===0" label="活动时间">
                <template>
                  <el-row></el-row>
                  <el-form label-width="100px" >
                    <el-form-item label="定金支付时间">
                      <el-date-picker
                        v-model="preTime1Range"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间">
                      </el-date-picker>
                      <el-button size="mini" v-show="param.prePayStep===1" @click="param.prePayStep=2">添加定金支付时段</el-button>
                    </el-form-item>
                    <el-form-item label="定金支付时间" v-show="param.prePayStep===2">
                      <el-date-picker
                        v-model="preTime2Range"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间">
                      </el-date-picker>
                      <el-button size="mini" @click="param.prePayStep=1">删除</el-button>
                    </el-form-item>
                    <el-form-item label="尾款支付时间">
                      <el-date-picker
                        v-model="tailPayTimeRange"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间">
                      </el-date-picker>
                    </el-form-item>
                  </el-form>
                </template>
              </el-form-item>
              <!-- 全款预售 -->
              <el-form-item v-show="param.presaleType===1" label="定金支付时间">
                <el-date-picker
                  v-model="preTime1Range"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="活动商品">
                <span>{{goodsName}}</span>
                <el-button v-show="this.param.products.length<1" @click="showChoosingGoods">选择商品</el-button>
                <el-button v-show="this.param.products.length>0" @click="showChoosingGoods">修改</el-button>
              </el-form-item>
              <el-form-item label="发货时间">
                <el-radio v-model="param.presaleType" :label="0">&nbsp;指定发货开始时间</el-radio>
                <el-date-picker v-model="deliverTime" type="datetime">
                </el-date-picker>
                <el-radio v-model="param.presaleType" :label="1" >&nbsp;</el-radio>尾款支付完成<el-input v-model="param.deliverDays" type="number"/>天后发货
              </el-form-item>
              <el-form-item label="优惠叠加策略">
                <el-radio v-model="param.discountType" v-for="(item, index) in discountTypes" :key="index" :label="index" >{{item}}</el-radio>
              </el-form-item>
              <el-form-item label="定金退款策略">
                <el-radio v-model="param.returnType" v-for="(item, index) in returnTypes" :key="index" :label="index" >{{item}}</el-radio>
              </el-form-item>
              <el-form-item label="预售数量展示">
                <el-radio v-model="param.showSaleNumber" v-for="(item, index) in showSaleNumberTypes" :key="index" :label="index" >{{item}}</el-radio>
              </el-form-item>
              <el-form-item label="商品购买方式">
                <el-radio v-model="param.buyType" v-for="(item, index) in buyTypes" :key="index" :label="index" >{{item}}</el-radio>
              </el-form-item>
             </el-form>
          </el-col>
        </el-row>
        <div style="margin-top:20px">
          <el-row>
            <el-col>
              <el-button type="primary" @click="addGift">保存</el-button>
            </el-col>
          </el-row>
        </div>
        <choosingGoods/>
    </wrapper>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
import inputEdit from '@/components/admin/inputEdit'
import choosingGoods from '@/components/admin/choosingGoods'
import status from '@/components/admin/status/status'
import { createPreSale, updatePreSale, getDetail } from '@/api/admin/marketManage/preSale'

export default {
  components: {
    wrapper,
    inputEdit,
    choosingGoods
  },
  data () {
    return {
      id: null,
      // 当前页为编辑页
      update: false,
      // 活动状态,
      status: null,
      // 1段定金时间
      preTime1Range: [],
      // 2段定金时间
      preTime2Range: [],
      // 尾款支付时间
      tailPayTimeRange: [],
      // 全款支付时间
      payTimeRange: [],
      // 指定发货时间
      deliverTime: null,
      // 活动商品名称
      goodsName: '',
      presaleTypes: ['定金膨胀', '全款预售'],
      discountTypes: ['可叠加', '不可叠加'],
      returnTypes: ['不自动退定金', '自动退定金'],
      showSaleNumberTypes: ['不展示', '展示'],
      buyTypes: ['不可原价购买', '可原价购买'],
      param: {
        presaleType: 0,
        presaleName: '',
        prePayStep: 1,
        preStartTime: null,
        preEndTime: null,
        preStartTime2: null,
        preEndTime2: null,
        startTime: null,
        endTime: null,
        goodsId: 1,
        deliverType: 0,
        deliverTime: null,
        deliverDays: null,
        discountType: 0,
        returnType: 0,
        showSaleNumber: 0,
        buyType: 0,
        buyNumber: 0,
        shareType: 1,
        shareText: '',
        shareImgType: 1,
        shareImg: '',
        products: [
          {
            goodsId: 1,
            productId: 1,
            presalePrice: 1800,
            presaleNumber: 100,
            presaleMoney: 100,
            preDiscountMoney1: 200,
            preDiscountMoney2: 300
          }
        ]
      }
    }
  },
  computed: {
    ongoing () {
      return this.status === status[1].status
    },
    goodsBtnName () {
      if (this.ongoing) {
        return '查看商品'
      }
      return '添加商品'
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 保存
    addGift () {
      const then = r => this.gotoGifts()
      const { param } = this
      this.formatParam()
      if (!this.validateGiftParam()) {
        return
      }
      if (this.update) {
        updatePreSale(param).then(then)
      } else {
        createPreSale(param).then(then)
      }
    },
    formatParam () {

    },
    // 回显数据加载
    loadData () {
      const { id } = this.$route.params
      getDetail(id).then(({ content }) => {
        this.param = content
        this.loadStatus(content)
        this.loadingGoods(content)
      })
    },
    loadStatus: ({ status }) => {
      this.status = status
    },
    loadingGoods: ({ goodsName }) => {
      this.goodsName = goodsName
    },
    // 参数校验
    validateParam () {

    },
    fail (message) {
      this.$message({
        showClose: true,
        message,
        type: 'warning'
      })
    },
    handleChoosingGoods (ids) {
      this.tmpGoodsIds = ids
    },
    listenGoodsResult () {
      this.$http.$on('result', ids => {
        let goodsId = ids[0]
        this.param.goodsId = goodsId
        // todo 查询 goodsIs 下所有 product
      })
    },
    showChoosingGoods () {
      this.$http.$emit('choosingGoodsFlag', true)
    }
  },
  watch: {

  },
  mounted () {
    const { id } = this.$route.params
    this.update = !!id
    this.id = id || null
    if (this.update) {
      // 编辑回显
      this.loadData()
    }
    this.listenGoodsResult()
  }
}
</script>
<style lang="scss" scoped>
  .label {
    line-height: 40px
  }
  .input {
    margin-right: 10px;
    width: 70px;
  }
  .name_cell {
    display: flex;
    div {
      line-height: 45px;
      margin-left: 10px;
    }
  }
  .goods_img {
    width: 45px;
    height: 45px;
  }
  .description {
    color: #999;
  }
</style>
