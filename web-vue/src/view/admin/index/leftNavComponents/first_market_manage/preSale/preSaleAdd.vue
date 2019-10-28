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
              <el-form-item v-show="!isFullPay" label="活动时间">
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
                      <el-button size="mini" v-show="!twoSteps"
                      @click="param.prePayStep=2">添加定金支付时段</el-button>
                    </el-form-item>
                    <el-form-item label="定金支付时间" v-show="twoSteps">
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
              <el-form-item v-show="isFullPay" label="定金支付时间">
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
                <el-radio v-model="param.deliverType" :label="1">&nbsp;指定发货开始时间</el-radio>
                <el-date-picker v-model="deliverTime" type="datetime">
                </el-date-picker>
                <el-radio v-model="param.deliverType" :label="2" >&nbsp;</el-radio>
                尾款支付完成<el-input v-model="param.deliverDays" type="number"/>天后发货
              </el-form-item>
              <el-form-item label="优惠叠加策略">
                <el-radio v-model="param.discountType" v-for="(item, index) in discountTypes"
                 :key="index" :label="index" >{{item}}</el-radio>
              </el-form-item>
              <el-form-item label="定金退款策略">
                <el-radio v-model="param.returnType" v-for="(item, index) in returnTypes"
                 :key="index" :label="index" >{{item}}</el-radio>
              </el-form-item>
              <el-form-item label="预售数量展示">
                <el-radio v-model="param.showSaleNumber" v-for="(item, index) in showSaleNumberTypes"
                 :key="index" :label="index" >{{item}}</el-radio>
              </el-form-item>
              <el-form-item label="商品购买方式">
                <el-radio v-model="param.buyType" v-for="(item, index) in buyTypes" :key="index"
                :label="index" >{{item}}</el-radio>
              </el-form-item>
            </el-form>
          </el-col>
          <el-table
            class="version-manage-table"
            header-row-class-name="tableHeader"
            :data="param.products"
            border
            style="width: 100%"
          >
            <el-table-column
              prop="prdDesc"
              label="规格"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="prdPrice"
              label="商品原价"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="prdNumber"
              label="商品库存"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="presalePrice"
              label="活动价格(元)"
              align="center"
            >
              <template slot-scope="scope">
                <el-input type="number" v-model="scope.row.presalePrice"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="presaleNumber"
              label="活动库存"
              align="center"
            >
              <template slot-scope="scope">
                <el-input type="number" v-model="scope.row.presaleNumber"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="presaleMoney"
              label="定金(元)"
              align="center"
            >
              <template slot-scope="scope">
                <el-input type="number" v-model="scope.row.presaleMoney"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="preDiscountMoney1"
              label="1阶段定金可抵扣金额"
              align="center"
            >
              <template slot-scope="scope">
                <el-input type="number" v-model="scope.row.preDiscountMoney1"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="preDiscountMoney2"
              label="2阶段定金可抵扣金额"
              align="center"
              v-if="twoSteps"
            >
              <template slot-scope="scope">
                <el-input type="number" v-model="scope.row.preDiscountMoney2"></el-input>
              </template>
            </el-table-column>
          </el-table>
          <el-form label-width="100px" >
            <el-form-item label="批量设置">
              <el-button @click="batchSetPrice" size="mini">活动价格</el-button>
              <el-button @click="batchSetNumber" size="mini">活动库存</el-button>
              <el-button @click="batchSetMoney" size="mini">定金</el-button>
              <el-button @click="batchSetMoney1" size="mini">1段定金可抵扣金额</el-button>
              <el-button @click="batchSetMoney2" size="mini">2段定金可抵扣金额</el-button>
            </el-form-item>
          </el-form>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-button @click="showMore=!showMore">展开更多配置</el-button>
            <el-form label-width="100px" v-show="showMore">
              <el-form-item label="购买数量限制">
                <template>单用户最多可以购买
                  <el-input type="number" v-model="param.buyNumber"></el-input>件该商品
                </template>
              </el-form-item>
              <el-form-item label="活动分享">
                <el-radio v-model="param.shareType" v-for="(item, index) in shareTypes"
                 :key="index" :label="index">{{item}}</el-radio>
                 <el-row v-show="param.shareType===1">
                   <el-col>
                     <el-form label-width="80px">
                       <el-form-item label="文案">
                         <el-input v-model="param.shareText"></el-input>
                       </el-form-item>
                       <el-form-item label="分享图">
                         <el-radio v-model="param.shareImgType" v-for="(item, index) in shareImgTypes"
                          :key="index" :label="index">{{item}}</el-radio>
                       </el-form-item>
                       <el-form-item>
                         <el-button>选择图片</el-button>
                       </el-form-item>
                     </el-form>
                   </el-col>
                 </el-row>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <div style="margin-top:20px">
          <el-row>
            <el-col>
              <el-button type="primary" @click="add">保存</el-button>
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
import status from '@/components/admin/marketManage/status/status'
import { format } from '@/util/date'
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
      // 显示更多配置
      showMore: false,
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
      shareTypes: ['默认样式', '自定义样式'],
      shareImgTypes: ['活动商品信息图', '自定义图片'],
      /**
       * 请求参数
       */
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
        goodsId: 0,
        deliverType: 1,
        deliverTime: null,
        deliverDays: null,
        discountType: 0,
        returnType: 0,
        showSaleNumber: 0,
        buyType: 0,
        buyNumber: 0,
        shareType: 0,
        shareText: '',
        shareImgType: 0,
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
          },
          {
            goodsId: 1,
            productId: 2,
            presalePrice: 1850,
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
    },
    isFullPay () {
      return this.param.presaleType === 1
    },
    twoSteps () {
      return this.param.prePayStep === 2
    },
    deliverTimeSpecified () {
      return this.param.deliverType === 1
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 保存
    add () {
      const then = r => this.gotoHome()
      const { param } = this
      this.formatParam()
      if (!this.validateParam()) {
        return
      }
      if (this.update) {
        updatePreSale(param).then(then)
      } else {
        createPreSale(param).then(then)
      }
    },
    formatParam () {
      this.formatTimes()
    },
    formatTimes () {
      const { isFullPay, payTimeRange, twoSteps, preTime1Range, preTime2Range, tailPayTimeRange, deliverTime, deliverTimeSpecified } = this
      if (isFullPay) {
        this.param.startTime = format(payTimeRange[0])
        this.param.endTime = format(payTimeRange[1])
      } else {
        this.param.startTime = format(tailPayTimeRange[0])
        this.param.endTime = format(tailPayTimeRange[1])
        this.param.preStartTime = format(preTime1Range[0])
        this.param.preEndTime = format(preTime1Range[1])
        if (twoSteps) {
          this.param.preStartTime2 = format(preTime2Range[0])
          this.param.preEndTime2 = format(preTime2Range[1])
        }
      }
      if (deliverTimeSpecified) {
        this.param.deliverTime = format(deliverTime)
      }
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
      this.formatParam()
      // todo ......

      return true
    },
    fail (message) {
      this.$message({
        showClose: true,
        message,
        type: 'warning'
      })
    },
    listenGoodsResult () {
      this.$http.$on('result', ids => {
        let goodsId = ids[0]
        this.param.goodsId = goodsId
        this.addProductRows(ids)
      })
    },
    showChoosingGoods () {
      this.$http.$emit('choosingGoodsFlag', true)
    },
    addProductRows (goodsId) {
      // todo 调用查询商品接口获取 goodsId 下所有 product，返回结果填入表格
    },
    batchSetPrice () {
      const { presalePrice } = this.param.products[0]
      this.param.products.forEach(r => {
        r.presalePrice = presalePrice
      })
    },
    batchSetNumber () {
      const { presaleNumber } = this.param.products[0]
      this.param.products.forEach(r => {
        r.presaleNumber = presaleNumber
      })
    },
    batchSetMoney () {
      const { presaleMoney } = this.param.products[0]
      this.param.products.forEach(r => {
        r.presaleMoney = presaleMoney
      })
    },
    batchSetMoney1 () {
      const { preDiscountMoney1 } = this.param.products[0]
      this.param.products.forEach(r => {
        r.preDiscountMoney1 = preDiscountMoney1
      })
    },
    batchSetMoney2 () {
      const { preDiscountMoney2 } = this.param.products[0]
      this.param.products.forEach(r => {
        r.preDiscountMoney2 = preDiscountMoney2
      })
    },
    gotoHome () {
      this.$router.replace('/admin/home/main/presale')
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
