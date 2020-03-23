<!--
* 创建定金膨胀活动
* @author 赵鑫
-->
<template>
  <div>
    <div class="wrapper">
      <el-form
        label-width="130px"
        ref="param"
        :model="param"
        :rules="formRules"
      >
        <el-form-item
          label="活动类型："
          prop="presaleType"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-for="(item, index) in presaleTypes"
            :key="index"
            v-model="param.presaleType"
            :label="index"
          >{{item}}</el-radio>
        </el-form-item>
        <el-form-item
          label="活动名称："
          prop="presaleName"
        >
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
          :rules="[{required: true}]"
        >
          <template>
            <div style="color:#999">请设置定金支付时间以及尾款支付时间，最多可配置两个支付定金时段，定金支付的截止时间不能大于尾款支付的截止时间</div>
            <el-form-item
              label="定金支付时间："
              :rules="[{required: true, message:'请填写定金支付时间', trigger: ['blur','change']}]"
              :inline-message="true"
              prop="preTime1Range"
            >
              <el-date-picker
                :disabled="isEditeFlag"
                v-model="param.preTime1Range"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                @change="dateChange(param.preTime1Range)"
                value-format="yyyy-MM-dd HH:mm:ss"
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

            <!-- 定金支付时间 -->
            <el-form-item
              label="定金支付时间："
              :rules="[{required: true, message:'请填写定金支付时间', trigger: ['blur','change']}]"
              :inline-message="true"
              prop="preTime2Range"
              v-show="twoSteps"
            >
              <el-date-picker
                v-model="param.preTime2Range"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                size="small"
                :disabled="isEditeFlag"
                :default-time="['00:00:00', '23:59:59']"
                @change="dateChange2(param.preTime2Range)"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
              <el-button
                size="small"
                @click="handleDelete"
              >删除</el-button>
            </el-form-item>

            <!-- 尾款支付时间 -->
            <el-form-item
              label="尾款支付时间："
              prop="tailPayTimeRange"
              :rules="[{required: true, message:'请填写尾款支付时间', trigger: ['blur','change']}]"
              :inline-message="true"
            >
              <el-date-picker
                v-model="param.tailPayTimeRange"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                @change="endMoneyTime(param.tailPayTimeRange)"
                value-format="yyyy-MM-dd HH:mm:ss"
                size="small"
                :disabled="isEditeFlag"
                :default-time="['00:00:00', '23:59:59']"
              >
              </el-date-picker>
            </el-form-item>
          </template>
        </el-form-item>

        <!-- 全款预售 -->
        <el-form-item
          v-show="isFullPay"
          label="定金支付时间："
          :rules="[{required: true, message:'请填写定金支付时间', trigger: ['blur','change']}]"
          prop="preTime1Range"
        >
          <el-date-picker
            v-model="param.preTime1Range"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            size="small"
            :disabled="isEditeFlag"
            :default-time="['00:00:00', '23:59:59']"
          >
          </el-date-picker>
        </el-form-item>

        <!-- 活动商品 -->
        <el-form-item
          label="活动商品："
          prop="goodsId"
        >
          <el-input
            :disabled="true"
            v-model="param.goodsName"
            v-if="param.goodsName"
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
            :disabled="isEditeFlag"
            size="small"
            @click="showChoosingGoods"
          >选择商品
          </el-button>
        </el-form-item>
        <el-form-item
          label="发货时间："
          prop="deliverType"
        >
          <div style="display: flex">
            <el-radio
              v-model="param.deliverType"
              :label="1"
              style="line-height: 40px"
            >&nbsp;指定发货开始时间</el-radio>
            <!-- <el-form-item
              prop="deliverTime"
              :rules="[
                {required: true, message:'请填写发货开始时间', trigger: ['blur','change']},
                {validator: (rule, value, callback) => { validateSendTime(rule,value, callback)}}
              ]"
              :inline-message="true"
            > -->
            <el-date-picker
              v-model="param.deliverTime"
              type="datetime"
              size="small"
              style="width:190px"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
            <!-- </el-form-item> -->
          </div>
          <div style="display:flex">
            <el-radio
              v-model="param.deliverType"
              :label="2"
              style="line-height:40px"
            >
              <span>&nbsp;尾款支付完成</span>
            </el-radio>
            <!-- <el-form-item
              prop="deliverDays"
              :rules="[
                { required: true,trigger: 'blur' },
                { validator: (rule, value, callback)=>{validatePayment(rule, value, callback)}, trigger: ['blur', 'change'] }
              ]"
              :inline-message="true"
            > -->
            <el-input
              v-model="param.deliverDays"
              size="small"
              style="width:180px"
              :min=0
            />
            <span style="margin-left:10px">天后发货</span>
            <!-- </el-form-item> -->
          </div>
        </el-form-item>
        <el-form-item
          label="优惠叠加策略："
          prop="discountType"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.discountType"
            v-for="(item, index) in discountType"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">预售商品结算时是否可与会员卡折扣、优惠券叠加使用</span>
        </el-form-item>
        <el-form-item
          label="定金退款策略："
          prop="returnType"
          v-show="param.presaleType === 0"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.returnType"
            v-for="(item, index) in returnTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">选择自动退回定金，则在指定时间内未支付尾款的订单，将退回定金到原支付账户</span>
        </el-form-item>
        <el-form-item
          label="预售数量展示："
          prop="showSaleNumber"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.showSaleNumber"
            v-for="(item, index) in showSaleNumberTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">当前活动商品的预售数量是否展示在商品详情页</span>
        </el-form-item>
        <el-form-item
          label="商品购买方式："
          prop="buyType"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.buyType"
            v-for="(item, index) in buyTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">活动进行中是否可直接以原价购买此商品</span>
        </el-form-item>
      </el-form>
      <el-form
        ref="param-s"
        :model="param"
      >
        <el-form-item>
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
              prop="presalePrice"
              :show-overflow-tooltip="true"
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
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presalePrice'"
                  :rules="[
                  { required: true, message: '活动价格不能为空' },
                  { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.prdPrice)}, trigger: ['blur', 'change'] }
              ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.presalePrice"
                    size="small"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="presaleNumber"
              label="活动库存"
              :show-overflow-tooltip="true"
            >
              <template slot="append">
                <span>活动库存</span>
                <el-button
                  @click="setCurrent(2)"
                  size="mini"
                  icon="el-icon-edit"
                >
                </el-button>
              </template>
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presaleNumber'"
                  :rules="[
                { required: true, message: '活动库存不能为空' },
                { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.prdNumber)}, trigger: ['blur', 'change'] }
              ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.presaleNumber"
                    size="small"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="presaleMoney"
              label="定金"
              v-if="param.presaleType===0"
              :show-overflow-tooltip="true"
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
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presaleMoney'"
                  :rules="[
                    { required: true, message: '定金不能为空'},
                    { validator: (rule, value, callback)=>{validateReadyMoney(rule, value, callback, scope.row.presalePrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.presaleMoney"
                    size="small"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="preDiscountMoney1"
              label="1阶段定金可抵扣金额"
              v-if="!isFullPay"
              :show-overflow-tooltip="true"
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
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.preDiscountMoney1'"
                  :rules="[
                    { required: true, message: '1阶段定金不能为空'},
                    { validator: (rule, value, callback)=>{validateFirstStage(rule, value, callback, scope.row.presalePrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.preDiscountMoney1"
                    size="small"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="preDiscountMoney2"
              label="2阶段定金可抵扣金额"
              v-if="twoSteps&&!isFullPay"
              :show-overflow-tooltip="true"
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
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.preDiscountMoney2'"
                  :rules="[
                    { required: true, message: '2阶段定金不能为空'},
                    { validator: (rule, value, callback)=>{validateSecondStage(rule, value, callback, scope.row.presalePrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.preDiscountMoney2"
                    size="small"
                  />
                </el-form-item>
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
                v-if="param.presaleType===0"
              >定金
              </a>
              <a
                :class="activeIndex === 4 ? '' : 'settings'"
                @click="setCurrent(4)"
                v-show="!isFullPay"
              >1阶段定金可抵扣金额
              </a>
              <a
                :class="activeIndex === 5 ? '' : 'settings'"
                @click="setCurrent(5)"
                v-show="twoSteps&&!isFullPay"
              >2阶段定金可抵扣金额
              </a>
            </div>

          </el-table>
        </el-form-item>
      </el-form>

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
                :disabled="isEditeFlag"
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
                :label=1
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
                :label=2
              >自定义样式</el-radio>
              <div
                v-if="param.shareAction === 2"
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
                v-if="param.shareAction === 2"
                style="margin-left: 25px"
              >
                <!-- <span>分享图：</span> -->
                <span>分享图：</span>
                <el-radio
                  v-model="param.shareImgAction"
                  :label=1
                >活动商品信息图</el-radio>
                <div style="margin-left: 60px;">
                  <el-radio
                    v-model="param.shareImgAction"
                    :label=2
                  >自定义图片</el-radio>
                </div>

                <div
                  style="display: flex"
                  v-if="param.shareImgAction === 2"
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
        :chooseGoodsBack="goodsIdList"
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
  data () {
    // 活动商品校验
    var checkGoods = (rule, value, callback) => {
      console.log(value)
      if (!this.param.goodsId) {
        return callback(new Error('请选择活动商品'))
      } else {
        callback()
      }
    }
    // 发货时间校验
    var checkDeliverType = (rule, value, callback) => {
      console.log(value)
      if (value === 1 && !this.param.deliverTime) {
        callback(new Error('请选择发货开始时间'))
      } else if (value === 2 && (!this.param.deliverDays || this.param.deliverDays === null)) {
        callback(new Error('请填写尾款发货时间'))
      } else {
        callback()
      }
    }
    return {
      id: null,
      // 当前页为编辑页
      update: false,
      // 显示更多配置
      showMore: true,
      // 活动状态,
      status: null,
      // 全款支付时间
      payTimeRange: [],
      // 活动商品名称
      presaleTypes: ['定金膨胀', '全款预售'],
      discountType: ['不可叠加', '可叠加'],
      returnTypes: ['不自动退定金', '自动退定金'],
      showSaleNumberTypes: ['不展示', '展示'],
      buyTypes: ['不可原价购买', '可原价购买'],
      shareTypes: ['默认样式', '自定义样式'],
      shareImgTypes: ['活动商品信息图', '自定义图片'],
      isShowChoosingGoodsDialog: false,
      showImageDialog: false,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/presale_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/presale_pictorial.jpg`
      },
      ArrowArr: [
        { img_1: this.$imageHost + '/image/admin/show_more.png' },
        { img_2: this.$imageHost + '/image/admin/hid_some.png' }
      ],
      arrorFlag: true,
      activeIndex: 0,
      isEditeFlag: false,
      goodsIdList: [],

      /**
       * 请求参数
       */
      param: {
        preTime1Range: [], // 1段定金时间
        preTime2Range: [], // 2段定金时间
        tailPayTimeRange: [], // 尾款支付时间
        presaleType: 0, // 活动类型
        presaleName: '', // 活动名称
        prePayStep: 1, // 定金期数
        preStartTime: null, // 定金支付开始时间
        preEndTime: null,
        preStartTime2: null, // 2段定金支付开始时间
        preEndTime2: null,
        startTime: null, // 尾款开始支付时间
        endTime: null,
        goodsId: '',
        deliverType: 1, // 发货时间类型 1：指定，2：尾款支付
        deliverTime: null, // 发货时间
        deliverDays: null, // 几天后发货
        discountType: 0, // 优惠叠加策略
        returnType: 0, // 定金退款策略
        showSaleNumber: 0, // 预售数量展示
        buyType: 0, // 商品购买方式
        buyNumber: null, // 购买数量限制
        shareAction: 1,
        shareDoc: '',
        shareImgAction: 1,
        shareImg: '',
        products: [],
        goodsName: ''
      },
      formRules: {
        presaleType: { required: true },
        presaleName: { required: true, message: '请填写活动名称', trigger: 'blur' },
        goodsId: { required: true, validator: checkGoods, trigger: 'change' },
        deliverType: { required: true, validator: checkDeliverType, trigger: 'change' },
        discountType: { required: true },
        returnType: { required: true },
        showSaleNumber: { required: true },
        buyType: { required: true }
      }
    }
  },
  computed: {
    ongoing () {
      return this.status === status[1].status
    },
    isFullPay () {
      return this.param.presaleType === 1
    },
    twoSteps () {
      return this.param.prePayStep === 2
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 验证是否选择了商品
    validateMoney (rule, value, callback, prdPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > prdPrice) {
        callback(new Error('活动价格不能大于商品原价'))
      } else {
        callback()
      }
    },
    validateNum (rule, value, callback, prdNumber) {
      var re = /^[1-9]\d*$/
      if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else if (value > prdNumber) {
        callback(new Error('活动库存不能大于商品库存'))
      } else {
        callback()
      }
    },
    validateReadyMoney (rule, value, callback, presalePrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > Number(presalePrice)) {
        callback(new Error('定金不能大于活动价格'))
      } else {
        callback()
      }
    },
    validateFirstStage (rule, value, callback, presalePrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > Number(presalePrice)) {
        callback(new Error('1阶段定金不能大于活动价格'))
      } else {
        callback()
      }
    },
    validateSecondStage (rule, value, callback, presalePrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > Number(presalePrice)) {
        callback(new Error('2阶段定金不能大于活动价格'))
      } else {
        callback()
      }
    },
    // // 验证发货时间
    // validateSendTime (rule, value, callback) {
    //   console.log(value)
    //   if (!value && this.param.deliverTime === 1) {
    //     callback(new Error('请指定发货时间'))
    //   } else {
    //     callback()
    //   }
    // },
    // // 验证尾款支付完成
    // validatePayment (rule, value, callback) {
    //   console.log(value)
    //   if (!value && this.param.deliverType === 2) {
    //     callback(new Error('请填写尾款发货时间'))
    //   } else {
    //     callback()
    //   }
    // },
    // 一阶段定金支付时间
    dateChange (date) {
      this.param.preStartTime = date[0]
      this.param.preEndTime = date[1]
    },
    // 二阶段定金支付时间
    dateChange2 (date) {
      this.param.preStartTime2 = date[0]
      this.param.preEndTime2 = date[1]
    },
    endMoneyTime (val) {
      this.param.startTime = val[0]
      this.param.endTime = val[1]
    },
    // 保存
    add () {
      this.param.buyNumber = Number(this.param.buyNumber)
      const { param } = this

      console.log(param, 'get param')
      this.formatParam()
      if (!this.validateParam()) {
        return
      }
      if (this.update) {
        updatePreSale(param).then(res => {
          if (res.error === 0) {
            console.log(res)
            this.$message.success('更新成功')
            // this.gotoHome()
          } else {
            this.$message.error('更新失败')
          }
        })
      } else {
        createPreSale(param).then(res => {
          if (res.error === 0) {
            console.log(res)
            this.$message.success('添加成功')
            // this.gotoHome()
          }
        })
      }
    },
    formatParam () {
      this.formatTimes()
    },
    formatTimes () {
      const { isFullPay, payTimeRange, twoSteps } = this
      if (isFullPay) {
        this.param.startTime = format(payTimeRange[0])
        this.param.endTime = format(payTimeRange[1])
        this.param.preStartTime = format(this.param.preTime1Range[0])
        this.param.preEndTime = format(this.param.preTime1Range[1])
      } else {
        this.param.startTime = format(this.param.tailPayTimeRange[0])
        this.param.endTime = format(this.param.tailPayTimeRange[1])
        this.param.preStartTime = format(this.param.preTime1Range[0])
        this.param.preEndTime = format(this.param.preTime1Range[1])
        if (twoSteps) {
          this.param.preStartTime2 = format(this.param.preTime2Range[0])
          this.param.preEndTime2 = format(this.param.preTime2Range[1])
        }
      }
    },
    // 编辑活动初始化-回显数据加载
    loadData () {
      const { id } = this.$route.params
      getDetail(id).then(({ content }) => {
        this.param = content
        // this.param.shareImg = content.shareImg.split('.cn/')[1]
        console.log(this.param.shareImg)
        this.getImgeUrl(content.shareImg)

        this.loadStatus(content)
        // this.loadingGoods(content)
        console.log(this.param, 'get return param')
        if (content) {
          if (content.presaleType === 1) {
            // 全款购买 - 定金支付时间
            this.param.preTime1Range = [content.preStartTime, content.preEndTime]
          } else {
            // 定金膨胀 - 定金支付时间
            this.param.preTime1Range = [content.preStartTime, content.preEndTime]
            this.param.preTime2Range = [content.preStartTime2, content.preEndTime2]
          }
          // 尾款支付时间
          this.param.tailPayTimeRange = [content.startTime, content.endTime]
        }
      })
    },
    loadStatus: ({ status }) => {
      this.status = status
    },
    // 参数校验
    validateParam () {
      this.formatParam()
      // todo ......
      this.$refs['param'].validate((valid) => {
        console.log(valid, 'valid')
        if (valid) {
          if (this.param.preStartTime2) {
            if (this.param.preEndTime > this.param.preStartTime2) {
              this.$message.warning('一期结束时间应小于二期开始时间')
              return false
            }
            if (this.param.startTime < this.param.preStartTime) {
              this.$message.warning('尾款支付开始时间应大于一期开始时间')
              return false
            }
            if (this.param.endTime < this.param.preEndTime2) {
              this.$message.warning('尾款支付结束时间应大于二期结束时间')
              return false
            }
          } else if (!this.param.preStartTime2 || this.param.preStartTime2 === '') {
            if (this.param.startTime < this.param.preEndTime) {
              this.$message.warning('尾款支付的开始时间要大于定金支付的开始时间')
              return false
            }
          }
          if (this.param.deliverTime < this.param.endTime) {
            this.$message.warning('指定发货时间应大于尾款支付时间')
          }
        } else {
          console.log('error submit')
          return false
        }
      })

      this.$refs['param-s'].validate((valid) => {
        console.log(valid, 'valid')
        if (valid) {
          // alert('111')
        } else {
          // alert('222')
        }
      })
      return true
    },
    // 删除二阶段时间
    handleDelete () {
      this.param.prePayStep = 1
      this.param.preTime2Range = []
      this.param.preStartTime2 = ''
      this.param.preEndTime2 = ''
    },
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    gotoHome () {
      this.$router.push('/admin/home/main/presale')
    },
    setCurrent (index) {
      // 拷贝一份数据
      let price = JSON.parse(JSON.stringify(this.param.products))
      console.log(this.price, 'get-price')

      switch (index) {
        case 1:
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
      this.param.products = price
    },
    // 改变"收起、展开更多配置"事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    // 获取商品ids
    choosingGoodsResult (row) {
      console.log(row, 'goodsInfo')
      this.param.goodsName = row.goodsName
      this.param.goodsId = row.goodsId
      this.goodsIdList.push(row.goodsId)

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
    // 分享 - 调起图片弹窗
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    handleSelectImg (res) {
      if (res != null) {
        this.param.shareImg = res.imgPath
      }
    },
    getImgeUrl (data) {
      console.log(data)
      var imgUrl = data.split('//').pop().split('/').slice(1).join('/')
      this.param.shareImg = imgUrl
    }
  },
  watch: {
    'param.goodsId': function (value) {
      if (value) {
        this.$refs.param.validateField('goodsId')
      }
    },
    'param.deliverType': function (value) {
      if (value) {
        this.$refs.param.validateField('deliverType')
      }
    }
  },
  mounted () {
    const { id } = this.$route.params
    this.update = !!id
    this.id = id || null
    if (this.update) {
      // 编辑回显
      this.loadData()
    }
    this.langDefault()
    if (this.$route.query.id > 0) {
      // 编辑定金膨胀活动
      this.isEditeFlag = true // 编辑时部分信息不可以修改
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
