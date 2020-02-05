<!--
* 创建定金膨胀活动
*
* @author 郑保乐
-->
<template>
  <div>
    <div class="wrapper">
      <el-form label-width="120px">
        <el-form-item label="活动类型：">
          <el-radio
            v-for="(item, index) in presaleTypes"
            :key="index"
            v-model="param.presaleType"
            :label="index"
          >{{item}}</el-radio>
        </el-form-item>
        <el-form-item label="活动名称：">
          <el-input
            v-model="param.presaleName"
            size="small"
            style="width:180px"
          ></el-input>
          <span style="color:#999;margin-left:10px;">只作为商家记录使用，用户不会看到这个名称</span>
        </el-form-item>
        <!-- 定金膨胀 -->
        <el-form-item
          v-show="!isFullPay"
          label="活动时间："
        >
          <template>
            <div style="color:#999">请设置定金支付时间以及尾款支付时间，最多可配置两个支付定金时段，定金支付的截止时间不能大于尾款支付的截止时间</div>
            <el-form label-width="120px">
              <el-form-item label="定金支付时间：">
                <el-date-picker
                  v-model="preTime1Range"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  size="small"
                  :default-time="['00:00:00', '23:59:59']"
                >
                </el-date-picker>
                <el-button
                  size="small"
                  v-show="!twoSteps"
                  @click="param.prePayStep=2"
                >添加定金支付时段</el-button>
              </el-form-item>
              <el-form-item
                label="定金支付时间："
                v-show="twoSteps"
              >
                <el-date-picker
                  v-model="preTime2Range"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始时间"
                  size="small"
                  end-placeholder="结束时间"
                >
                </el-date-picker>
                <el-button
                  size="small"
                  @click="param.prePayStep=1"
                >删除</el-button>
              </el-form-item>
              <el-form-item label="尾款支付时间：">
                <el-date-picker
                  v-model="tailPayTimeRange"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  size="small"
                  :default-time="['00:00:00', '23:59:59']"
                >
                </el-date-picker>
              </el-form-item>
            </el-form>
          </template>
        </el-form-item>
        <!-- 全款预售 -->
        <el-form-item
          v-show="isFullPay"
          label="定金支付时间："
        >
          <el-date-picker
            v-model="preTime1Range"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            size="small"
            :default-time="['00:00:00', '23:59:59']"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="活动商品：">
          <el-input
            :disabled="true"
            v-model="goodsRow.goodsName"
            v-if="goodsRow.ischecked"
            size="small"
            style="width: 170px;"
          ></el-input>
          <el-input
            :disabled="true"
            v-if="false"
            v-model="param.goodsId"
            size="small"
            style="width: 170px;"
          ></el-input>
          <el-button
            :disabled="isEdite"
            size="small"
            @click="showChoosingGoods"
          >选择商品
          </el-button>
        </el-form-item>
        <el-form-item label="发货时间：">
          <div>
            <el-radio
              v-model="param.deliverType"
              :label="1"
            >&nbsp;指定发货开始时间</el-radio>
            <el-date-picker
              v-model="param.deliverTime"
              type="datetime"
              size="small"
              style="width:190px"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
          <div style="display:inline-block">
            <el-radio
              v-model="param.deliverType"
              :label="2"
            >&nbsp;尾款支付完成</el-radio>
            <el-input
              v-model="param.deliverDays"
              type="number"
              size="small"
              style="width:180px"
            />
            <span style="margin-left:10px">天后发货</span>
          </div>
        </el-form-item>
        <el-form-item label="优惠叠加策略：">
          <el-radio
            v-model="param.discountType"
            v-for="(item, index) in discountTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">预售商品结算时是否可与会员卡折扣、优惠券叠加使用</span>
        </el-form-item>
        <el-form-item label="定金退款策略：">
          <el-radio
            v-model="param.returnType"
            v-for="(item, index) in returnTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">选择自动退回定金，则在指定时间内未支付尾款的订单，将退回定金到原支付账户</span>
        </el-form-item>
        <el-form-item label="预售数量展示：">
          <el-radio
            v-model="param.showSaleNumber"
            v-for="(item, index) in showSaleNumberTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">当前活动商品的预售数量是否展示在商品详情页</span>
        </el-form-item>
        <el-form-item label="商品购买方式：">
          <el-radio
            v-model="param.buyType"
            v-for="(item, index) in buyTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">活动进行中是否可直接以原价购买此商品</span>
        </el-form-item>
      </el-form>
      <el-table
        header-row-class-name="tableHeader"
        :data="param.products"
        border
        style="width: 100%"
        empty-text="暂无数据"
      >
        <el-table-column
          prop="prdDesc"
          label="规格"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="prdPrice"
          label="商品原价(元)"
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
          align="center"
          label="活动价格"
        >
          <template slot="append">
            <span>活动价格</span>
            <el-button
              @click="setCurrent(1)"
              size="small"
              icon="el-icon-edit"
            >批量设置
            </el-button>
          </template>
          <template slot-scope="scope">
            <!-- <el-form-item
              :prop="'products.' +  scope.$index+ '.presalePrice'"
              :rules="[
                { required: true, message: '活动价格不能为空', trigger: 'blur' },
                { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.shopPrice)}, trigger: ['blur', 'change'] }
              ]"
              style="height: 56px;line-height: 56px;"
            > -->
            <el-input
              v-model="scope.row.presalePrice"
              size="small"
            />
            <!-- </el-form-item> -->
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="presaleNumber"
          label="活动库存"
        >
          <template slot="append">
            <span>活动库存</span>
            <el-button
              @click="setCurrent(2)"
              size="mini"
              icon="el-icon-edit"
            >更多设施123
            </el-button>
          </template>
          <template slot-scope="scope">
            <!-- <el-form-item
              :prop="'products.' +  scope.$index+ '.presaleNumber'"
              :rules="[
                { required: true, message: '活动库存不能为空', trigger: 'blur' },
                { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.goodsNumber)}, trigger: ['blur', 'change'] }
              ]"
              style="height: 56px;line-height: 56px;"
            > -->
            <el-input
              v-model="scope.row.presaleNumber"
              size="small"
            />
            <!-- </el-form-item> -->
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="stock"
          label="定金"
        >
          <template slot="append">
            <span>定金</span>
            <el-button
              @click="setCurrent(3)"
              size="mini"
              icon="el-icon-edit"
            >定金说明
            </el-button>
          </template>
          <template slot-scope="scope">
            <!-- <el-form-item
              :prop="'products.' +  scope.$index+ '.presaleMoney'"
              :rules="[
                    { required: true, message: '定金不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.presaleMoney)}, trigger: ['blur', 'change'] }
                  ]"
              style="height: 56px;line-height: 56px;"
            > -->
            <el-input
              v-model="scope.row.presaleMoney"
              size="small"
            />
            <!-- </el-form-item> -->
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="preDiscountMoney1"
          label="1阶段定金可抵扣金额"
        >
          <template slot="append">
            <span>1阶段定金可抵扣金额</span>
            <el-button
              @click="setCurrent(4)"
              size="mini"
              icon="el-icon-edit"
            >1阶段
            </el-button>
          </template>
          <template slot-scope="scope">
            <!-- <el-form-item
              :prop="'products.' +  scope.$index+ '.preDiscountMoney1'"
              :rules="[
                { required: true, message: '定金不能为空', trigger: 'blur' },
                { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.preDiscountMoney1)}, trigger: ['blur', 'change'] }
              ]"
              style="height: 56px;line-height: 56px;"
            > -->
            <el-input
              v-model="scope.row.preDiscountMoney1"
              size="small"
            />
            <!-- </el-form-item> -->
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="preDiscountMoney2"
          label="2阶段定金可抵扣金额"
          v-if="twoSteps"
        >
          <template slot="append">
            <span>2阶段定金可抵扣金额</span>
            <el-button
              @click="setCurrent(5)"
              size="mini"
              icon="el-icon-edit"
            >2阶段可以抵扣的金额
            </el-button>
          </template>
          <template slot-scope="scope">
            <!-- <el-form-item
              :prop="'products.' +  scope.$index+ '.preDiscountMoney2'"
              :rules="[
                { required: true, message: '定金不能为空', trigger: 'blur' },
                { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.preDiscountMoney2)}, trigger: ['blur', 'change'] }
              ]"
              style="height: 56px;line-height: 56px;"
            > -->
            <el-input
              v-model="scope.row.preDiscountMoney2"
              size="small"
            />
            <!-- </el-form-item> -->
          </template>
        </el-table-column>
        <template
          slot="empty"
          style="height：0"
        >
        </template>
        <div
          slot="append"
          class="moreSetUp"
        >
          <span style="display: inline-block">更多设置：</span>
          <a
            :class="activeIndex === 1 ? '' : 'settings'"
            @click="setCurrent(1)"
          >活动价格
          </a>
          <a
            :class="activeIndex === 2 ? '' : 'settings'"
            @click="setCurrent(2)"
          >活动库存
          </a>
          <a
            :class="activeIndex === 3 ? '' : 'settings'"
            @click="setCurrent(3)"
          >定金
          </a>
          <a
            :class="activeIndex === 4 ? '' : 'settings'"
            @click="setCurrent(4)"
          >1阶段定金可抵扣金额
          </a>
          <a
            :class="activeIndex === 5 ? '' : 'settings'"
            @click="setCurrent(5)"
          >2阶段定金可抵扣金额
          </a>

        </div>
      </el-table>

      <!-- 收起、展开更多配置 -->
      <div
        @click="handleToChangeArror()"
        style="margin: 20px 0 10px"
      >
        <div
          v-if="arrorFlag"
          style="color:rgb(90, 139, 255);cursor:pointer"
        >{{$t('groupBuy.moreConfigure')}}&nbsp;<img :src="ArrowArr[0].img_1"></div>
        <div
          v-if="!arrorFlag"
          style="color:rgb(90, 139, 255);cursor:pointer"
        >{{$t('groupBuy.packUpConfigure')}}&nbsp;<img :src="ArrowArr[1].img_2"></div>
      </div>
      <div v-if="!arrorFlag">
        <el-form
          label-width="120px"
          v-show="showMore"
        >
          <el-form-item label="购买数量限制：">
            <div style="display:flex">
              <span>单用户最多可以购买</span>
              <el-input
                type="number"
                v-model="param.buyNumber"
                size="small"
                style="width:180px;margin:0 10px;"
              ></el-input>
              <span>件该商品</span>
              <span
                class="textColor"
                style="margin-left:20px;"
              >单用户可购买活动商品的数量，不填或填写0表示不限制</span>
            </div>
          </el-form-item>
          <el-form-item label="活动分享：">
            <div>
              <el-radio
                v-model="param.shareAction"
                :label=0
              >默认样式</el-radio>
              <el-popover
                placement="right-start"
                width="220"
                trigger="hover"
              >
                <el-image :src="srcList.src1"></el-image>
                <el-button
                  slot="reference"
                  type="text"
                  style="margin: 0 20px"
                >查看示例</el-button>
              </el-popover>
              <el-popover
                placement="right-start"
                width="220"
                trigger="hover"
              >
                <el-image :src="srcList.src2"></el-image>
                <el-button
                  slot="reference"
                  type="text"
                >下载海报</el-button>
              </el-popover>
            </div>
            <div>
              <el-radio
                v-model="param.shareAction"
                :label=1
              >自定义样式</el-radio>
              <div
                v-if="param.shareAction === 1"
                style="margin-left: 25px"
              >
                <span>文案：</span>
                <el-input
                  v-model="param.shareDoc"
                  size="small"
                  style="width:150px"
                ></el-input>
              </div>
              <div
                v-if="param.shareAction === 1"
                style="margin-left: 25px"
              >
                <!-- <span>分享图：</span> -->
                <span>分享图：</span>
                <el-radio
                  v-model="param.shareImgAction"
                  :label=0
                >活动商品信息图</el-radio>
                <div style="margin-left: 60px;">
                  <el-radio
                    v-model="param.shareImgAction"
                    :label=1
                  >自定义图片</el-radio>
                </div>

                <div
                  style="display: flex"
                  v-if="param.shareImgAction === 1"
                >
                  <div
                    class="imgContent"
                    @click="addGoodsImg"
                  >
                    <div>
                      <img
                        v-if="param.shareImg === '' || param.shareImg === null"
                        :src="$imageHost + '/image/admin/btn_add.png'"
                        alt=""
                      >
                      <img
                        v-if="param.shareImg !== ''"
                        :src="$imageHost + '/' + param.shareImg"
                        alt=""
                        class="shareImg"
                      >
                    </div>
                  </div>
                  <span class="picSizeTips">建议尺寸：800*800像素</span>
                </div>

              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>

      <div class="footer">
        <el-button
          size="small"
          type="primary"
          @click="add"
        >保存
        </el-button>
      </div>

      <!--添加商品弹窗-->
      <choosingGoods
        @resultGoodsRow="choosingGoodsResult"
        :chooseGoodsBack="[param.goodsId]"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :singleElection="true"
        :showTips="true"
      />

      <!-- 选择图片弹框 -->
      <ImageDalog
        pageIndex='pictureSpace'
        :tuneUp="showImageDialog"
        @handleSelectImg='handleSelectImg'
        :imageSize="[800, 800]"
      />
    </div>

  </div>
</template>
<script>
import { mapActions } from 'vuex'
import inputEdit from '@/components/admin/inputEdit'
import choosingGoods from '@/components/admin/choosingGoods'
import ImageDalog from '@/components/admin/imageDalog'
import status from '@/components/admin/marketManage/status/status'
import { getAllGoodsProductList } from '@/api/admin/brandManagement.js'
import { format } from '@/util/date'
import { createPreSale, updatePreSale, getDetail } from '@/api/admin/marketManage/preSale'

export default {
  components: {
    inputEdit,
    choosingGoods,
    ImageDalog
  },
  props: ['isEdite', 'editData'],
  data () {
    return {
      id: null,
      // 当前页为编辑页
      update: false,
      // 显示更多配置
      showMore: true,
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
      isShowChoosingGoodsDialog: false,
      showImageDialog: false,
      props: ['isEdite'],
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`
      },
      ArrowArr: [
        { img_1: this.$imageHost + '/image/admin/show_more.png' },
        { img_2: this.$imageHost + '/image/admin/hid_some.png' }
      ],
      arrorFlag: true,
      activeIndex: 0,
      goodsRow: {},

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
        goodsId: '',
        deliverType: 1,
        deliverTime: null,
        deliverDays: null,
        discountType: 0,
        returnType: 0,
        showSaleNumber: 0,
        buyType: 0,
        buyNumber: 0,
        shareAction: 0,
        shareDoc: '',
        shareImgAction: 0,
        shareImg: '',
        products: []
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
    validateMoney (rule, value, callback, shopPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > shopPrice) {
        callback(new Error('拼团价或团长价不能大于商品原价'))
      } else {
        callback()
      }
    },
    validateNum (rule, value, callback, goodsNumber) {
      var re = /^[1-9]\d*$/
      if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else if (value > goodsNumber) {
        callback(new Error('拼团库存不能大于商品库存'))
      } else {
        callback()
      }
    },
    // 保存
    add () {
      const then = r => this.gotoHome()
      const { param } = this
      console.log(param, 'get param')
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
      const { isFullPay, payTimeRange, twoSteps, preTime1Range, preTime2Range, tailPayTimeRange } = this
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
      // if (deliverTimeSpecified) {
      //   this.param.deliverTime = format(deliverTime)
      // }
    },
    // 回显数据加载
    loadData () {
      const { id } = this.$route.params
      getDetail(id).then(({ content }) => {
        this.param = content
        this.loadStatus(content)
        this.loadingGoods(content)
        console.log(this.param, 'get return param')
        if (content) {
          if (content.presaleType === 1) {
            // 全款购买 - 定金支付时间
            this.preTimeRange = [content.startTime, content.endTime]
          } else {
            // 定金膨胀 - 定金支付时间
            this.preTime1Range = [content.preStartTime, content.preEndTime]
            this.preTime2Range = [content.preStartTimeTwo, content.preEndTimeTwo]
          }
          // 尾款支付时间
          this.tailPayTimeRange = [content.startTime, content.endTime]
        }
      })
      // getDetail(id).then(res => {
      //   console.log(res)
      // })
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
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    gotoHome () {
      // this.$router.replace('/admin/home/main/presale')
    },
    setCurrent (index) {
      // 拷贝一份数据
      let price = JSON.parse(JSON.stringify(this.param.products))

      switch (index) {
        case 1:
          // console.log(price, 'setCurrent')
          price.forEach(row => {
            row.presalePrice = Number(price[0].presalePrice)
          })
          this.activeIndex = 1
          break
        case 2:
          price.forEach(row => {
            row.presaleNumber = Number(price[0].presaleNumber)
          })
          this.activeIndex = 2
          break
        case 3:
          price.forEach(row => {
            row.presaleMoney = Number(price[0].presaleMoney)
          })
          this.activeIndex = 3
          break
        case 4:
          price.forEach(row => {
            row.preDiscountMoney1 = Number(price[0].preDiscountMoney1)
          })
          this.activeIndex = 4
          break
        case 5:
          price.forEach(row => {
            row.preDiscountMoney2 = Number(price[0].preDiscountMoney2)
          })
          this.activeIndex = 5
          break
      }
      // console.log(price, 'setCurrent')

      this.param.products = price
    },
    // 改变"收起、展开更多配置"事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    // 获取商品ids
    choosingGoodsResult (row) {
      console.log(row, 'goodsInfo')

      this.goodsRow = row
      // this.param.products.push(this.goodsRow)
      this.param.goodsId = row.goodsId
      if (Object.keys(row).length === 0) {

      }

      // 初始化规格表格
      getAllGoodsProductList(this.param.goodsId).then(res => {
        console.log(res.content, 'param')
        res.content.forEach((item, index) => {
          item.index = index
          item.productId = item.prdId
        })
        this.param.products = res.content
        console.log(this.param.products, 'this.form.products')
      })
    },
    // 调起图片弹窗
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    handleSelectImg (res) {
      if (res != null) {
        this.param.shareImg = res.imgPath
      }
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
    // this.listenGoodsResult()
    this.langDefault()
    if (this.isEdite) {
      this.arrorFlag = false
    }
  }
}
</script>
<style lang="scss" scoped>
.wrapper {
  margin: 10px;
  padding: 20px;
  margin-bottom: 80px;
  background: #fff;
}
.textColor {
  color: #999;
}
.imgContent {
  width: 70px;
  height: 70px;
  text-align: center;
  line-height: 65px;
  margin-left: 60px;
  cursor: pointer;
}
.imgContent .shareImg {
  width: 100%;
  height: 100%;
}
.picSizeTips {
  display: block;
  line-height: 80px;
  margin-left: 20px;
  color: rgb(153, 153, 153);
}
.label {
  line-height: 40px;
}
.footer {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
}
.moreSetUp {
  height: 40px;
  line-height: 40px;
  margin-left: 15px;
}
.moreSetUp a {
  margin-right: 10px;
  cursor: pointer;
}
.settings {
  color: #5a8bff;
}
</style>
