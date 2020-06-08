<template>
  <div class="container">

    <!-- 表单 -->
    <div>
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="130px"
        :label-position="'right'"
      >
        <el-form-item
          :label="$t('seckill.activityName') + '：'"
          prop="name"
        >
          <el-input
            v-model="form.name"
            size="small"
            style="width: 170px"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.validDate') + '：'"
          prop="validity"
        >
          <el-date-picker
            :disabled="this.isEdite"
            v-model="form.validity"
            type="datetimerange"
            :range-separator="$t('seckill.to')"
            :start-placeholder="$t('seckill.startTime')"
            :end-placeholder="$t('seckill.endTime')"
            :default-time="['00:00:00','23:59:59']"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="活动预告："
          prop="preTime"
        >
          <div>
            <span class="noticeTip">活动开始前会在商品详情中展示活动预告信息</span>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="$imageHost + '/image/admin/share/advance_seckill.jpg'"></el-image>
              <el-button
                slot="reference"
                type="text"
                style="margin: 0 20 0 0px"
              >查看示例</el-button>
            </el-popover>
          </div>
          <div>
            <el-radio
              v-model="form.preTime"
              :label="1"
              @change="preTimeChange"
              :disabled="this.isEdite"
            >活动开始前
              <el-input
                v-model="form.preTimeValue"
                :disabled="this.isEdite"
                style="width: 80px;"
                size="small"
              ></el-input>小时进行预告
            </el-radio>
            <el-radio
              v-model="form.preTime"
              :label="-1"
              @change="preTimeChange"
              :disabled="this.isEdite"
            >活动创建完成后即进行预告</el-radio>
            <el-radio
              v-model="form.preTime"
              :label="0"
              @change="preTimeChange"
              :disabled="this.isEdite"
            >不进行活动预告</el-radio>
          </div>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.level') + '：'"
          prop="first"
        >
          <el-input-number
            v-model="form.first"
            controls-position="right"
            :min="1"
            size="small"
          ></el-input-number>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.limitNum') + '：'"
          prop="limitAmount"
        >
          <el-input-number
            :disabled="this.isEdite"
            v-model="form.limitAmount"
            controls-position="right"
            :min="0"
            size="small"
          ></el-input-number>
          <span style="color: #999; margin-left: 10px;">{{ $t('seckill.limitTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.orderAfter') + '：'"
          prop="limitPaytime"
        >
          <el-input-number
            :disabled="this.isEdite"
            v-model="form.limitPaytime"
            controls-position="right"
            :min="5"
            size="small"
          ></el-input-number>
          <span>{{ $t('seckill.orderTip') }}</span>
          <p style="color: #999;">{{ $t('seckill.langTip') }}</p>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.goodsName') + '：'"
          prop="goodsId"
        >
          <el-button
            :disabled="this.isEdite"
            class="el-icon-plus"
            size="small"
            @click="showChoosingGoods"
          >{{ $t('seckill.select') }}</el-button>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.seckillPrice') + '：'"
          prop="secKillProduct"
        >
          <el-table
            class="version-manage-table"
            header-row-class-name="tableClss"
            :data="form.secKillProduct"
            border
            style="width: 100%"
          >
            <el-table-column
              :label="$t('seckill.commodityName')"
              align="center"
            >
              <template slot-scope="scope">
                <div class="goodImge">
                  <div class="img"><img :src="scope.row.goodsImg"></div>
                  <div class="name">{{scope.row.goodsName}}</div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('seckill.shopPrice')"
              prop="shopPrice"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('seckill.prdPrice')"
              align="center"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'secKillProduct.'+scope.$index+'.secKillPrice'"
                  :rules="[{ validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.shopPrice, scope.row)}, trigger: ['blur', 'change'] }]"
                >
                  <el-input
                    v-model="scope.row.secKillPrice"
                    size="small"
                    @input="priceChange(scope.row)"
                  />
                  <!-- :disabled="isEdite || disabledFlag" -->
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts && scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('seckill.goodsStock')"
              prop="goodsNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              v-if="isEdite"
              :label="$t('seckill.prdStock')"
              align="center"
            >
              <template slot-scope="scope">
                <span>{{Number(scope.row.totalStock) + Number(scope.row.saleNum)}}</span>
              </template>
            </el-table-column>
            <el-table-column
              v-if="isEdite"
              :label="$t('seckill.goodsSold')"
              prop="saleNum"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="isEdite ? $t('seckill.prdNumber') : $t('seckill.prdStock')"
              align="center"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'secKillProduct.' + scope.$index+ '.stock'"
                  :rules="[{ validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.goodsNumber, scope.row, scope.$index)}, trigger: ['blur', 'change'] }]"
                >
                  <div
                    class="input-error"
                    v-if="scope.row.stockErrorMsg"
                  >{{scope.row.stockErrorMsg}}</div>
                  <el-input
                    v-model="scope.row.stock"
                    size="small"
                    @input="numChange(scope.row)"
                  />
                  <!-- :disabled="isEdite || disabledFlag" -->
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts && scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格；库存合计：{{scope.row.totalStock ? scope.row.totalStock : 0}}</div>
              </template>
            </el-table-column>
            <div
              slot="append"
              class="moreSetUp"
            >
              <span>{{ this.$t('groupBuy.moreSettings') }}</span>
              <a
                :class="activeIndex === 1 ? '' : 'settings'"
                @click="setCurrent(1)"
              >秒杀价
              </a>
              <a
                :class="activeIndex === 2 ? '' : 'settings'"
                @click="setCurrent(2)"
              >秒杀库存
              </a>
            </div>
          </el-table>
        </el-form-item>
        <el-form-item
          :label="$t('seckill.freeFreight') + '：'"
          prop="freeFreight"
        >
          <el-radio
            :disabled="this.isEdite"
            v-model="form.freeFreight"
            :label="1"
          >{{ $t('seckill.freeShipping') }}</el-radio>
          <el-radio
            v-model="form.freeFreight"
            :label="0"
          >{{ $t('seckill.template') }}</el-radio>
        </el-form-item>

        <el-form-item
          label="同步打标签："
          prop=""
        >
          <el-checkbox
            v-model="form.activityTag"
            :true-label="1"
            :false-label="0"
            :disabled="this.isEdite"
          >给参与活动用户打标签</el-checkbox>
          <el-tooltip
            content="成功下单并完成支付的算作参与活动用户"
            placement="top"
            effect="light"
          >
            <span
              class="el-icon-question"
              style="color: #666;cursor: pointer;"
            ></span>
          </el-tooltip>
          <span
            class="labelStyle"
            @click="selectLabel"
          >选择标签</span>
          <div v-if="pickLabel.length > 0">
            <p style="color: #999;">最多可设置3个标签</p>
            <div
              v-for="(item, index) in pickLabel"
              :key="index"
              class="labelContent"
            >
              {{item.value}}
              <i
                class="el-icon-close"
                @click="deleteLabel(index)"
                style="color: #999; margin-left: 3px;cursorL pointer;"
              ></i>
            </div>
          </div>

        </el-form-item>

        <el-form-item label="活动初始销量：">
          <!-- prop="limitAmount" -->
          <el-input-number
            :disabled="this.isEdite"
            v-model="form.baseSale"
            controls-position="right"
            :min="0"
            size="small"
          ></el-input-number>
          <!-- <span style="color: #999; margin-left: 10px;">初始销量必填</span> -->
        </el-form-item>

        <!-- 收起、展开更多配置 -->
        <div
          @click="handleToChangeArror"
          style="padding: 0 0 30px 30px; width: 20%;"
        >
          <div
            v-if="arrorFlag"
            style="color:rgb(90, 139, 255);cursor:pointer"
          >
            {{ $t('seckill.openConfigure') }}&nbsp;<img :src="ArrowArr[0].img_1">
          </div>
          <div
            v-if="!arrorFlag"
            style="color:rgb(90, 139, 255);cursor:pointer"
          >
            {{ $t('seckill.closeConfigure') }}&nbsp;<img :src="ArrowArr[1].img_2">
          </div>
        </div>
        <div v-if="!arrorFlag">
          <!-- 会员专享 -->
          <el-form-item
            :label="this.$t('seckill.cardTitle') + '：'"
            prop="cardId"
          >
            <el-checkbox
              :label="this.$t('seckill.cardSelect')"
              v-model="showMember"
            ></el-checkbox>
            <div
              v-if="showMember"
              style="display: flex"
            >
              <el-select
                :placeholder="this.$t('seckill.cardTip')"
                v-model="form.cardId"
                multiple
                size="small"
                @blur="cardIdBlur"
              >
                <el-option
                  v-for="item in cardList"
                  :key="item.id"
                  :label="item.cardName"
                  :value="item.id"
                ></el-option>
              </el-select>
              <div>
                <span
                  class="member"
                  @click="refresh()"
                >{{ this.$t('seckill.cardRefresh') }}</span><span> | </span>
                <span
                  class="member"
                  @click="addMemberCard()"
                >{{ this.$t('seckill.cardNew') }}</span><span> | </span>
                <span
                  class="member"
                  @click="manageMemberCard()"
                >{{ this.$t('seckill.cardManage') }}</span>
              </div>
            </div>
          </el-form-item>
          <!-- 活动分享 -->
          <el-form-item
            prop="shareConfig.shareAction"
            label="活动分享："
          >
            <div class="shareContent">
              <el-radio
                v-model="form.shareConfig.shareAction"
                :label="1"
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
                  style="margin: 0 20 0 0px"
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
                v-model="form.shareConfig.shareAction"
                :label="2"
              >自定义样式</el-radio>
              <div v-if="form.shareConfig.shareAction === 2">
                <span>文案：</span>
                <el-input
                  v-model="form.shareConfig.shareDoc"
                  size="small "
                  style="width: 180px;"
                ></el-input>
              </div>
              <div v-if="form.shareConfig.shareAction === 2">
                <span>分享图：</span>
                <el-radio
                  v-model="form.shareConfig.shareImgAction"
                  :label="1"
                >活动商品信息图</el-radio>
                <div style="margin-left: 60px;">
                  <el-radio
                    v-model="form.shareConfig.shareImgAction"
                    :label="2"
                  >自定义图片</el-radio>
                </div>

                <div
                  style="display: flex"
                  v-if="form.shareConfig.shareImgAction === 2"
                >
                  <div
                    class="imgContent"
                    @click="addGoodsImg"
                  >
                    <div>
                      <img
                        v-if="form.shareConfig.shareImg === ''"
                        :src="$imageHost + '/image/admin/shop_beautify/add_decorete.png'"
                        alt=""
                      >
                      <img
                        v-if="form.shareConfig.shareImg !== ''"
                        :src="form.shareConfig.shareImg"
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
        </div>

      </el-form>

      <!--添加商品弹窗-->
      <choosingGoods
        :singleElection="false"
        :showTips="true"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :chooseGoodsBack="form.goodsId"
        @resultGoodsDatas="choosingGoodsResult"
      />

    </div>

    <!-- 选择图片弹框 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="showImageDialog"
      @handleSelectImg='handleSelectImg'
      :imageSize="[800, 800]"
    />

    <!-- 底部 -->
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        :disabled="submitStatus"
        @click="saveClickHandler"
      >{{ $t('seckill.save') }}</el-button>
    </div>

    <!-- 规格信息弹框 -->
    <seckillSpecDialog
      :productDialog.sync="showSpecDialog"
      :product-info="productInfo"
      :isEdit="isEdite"
      @confrim="getProductdata"
    />

    <!-- 标签弹窗 -->
    <LabelDialog
      :dialogVisible="labelDialogVisible"
      :multipleLimit="3"
      @resultLabelDatas="resultLabelDatas"
      :chooseLabelBack="form.activityTagId"
    />

  </div>
</template>
<script>
import { getSeckillList, addSeckillList, updateSeckillList } from '@/api/admin/marketManage/seckill.js'
import { allCardApi, allTagApi } from '@/api/admin/marketManage/messagePush'
export default {
  components: {
    choosingGoods: () => import('@/components/admin/choosingGoods'),
    actShare: () => import('@/components/admin/marketManage/marketActivityShareSetting'),
    ImageDalog: () => import('@/components/admin/imageDalog'),
    seckillSpecDialog: () => import('./seckillSpecDialog'),
    LabelDialog: () => import('@/components/admin/labelDialog')
  },

  props: ['isEdite', 'editId', 'isGoing'],
  data () {
    // 会员专享
    var validateCard = (rule, value, callback) => {
      if (this.showMember && value.length === 0) {
        callback(new Error('请选择会员卡'))
      } else {
        callback()
      }
    }
    // 自定义活动预告
    var validatePreTime = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (!value) {
        callback(new Error('请选择活动预告类型'))
      } else if (value === 1 && this.form.preTimeValue === '') {
        callback(new Error('请填写活动预告时间'))
      } else if (value === 1 && !re.test(this.form.preTimeValue)) {
        callback(new Error('活动预告时间填写不正确'))
      } else {
        callback()
      }
    }
    // 活动专享
    // var validateShare = (rule, value, callback) => {
    //   if (value === 2 && this.form.shareConfig.shareDoc === '') {
    //     callback(new Error('请填写活动文案'))
    //   } else if (value === 2 && this.form.shareConfig.shareImgAction === 2 && !this.form.shareConfig.shareImg) {
    //     callback(new Error('请选择活动图片'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      activeIndex: 0, // 批量设置
      // 表单
      form: {
        name: '', // 活动名称
        goodsId: [], // 商品id
        validity: '', // 有效期
        startTime: '', // 开始时间
        endTime: '', // 结束时间
        preTime: 1, // 活动预告
        preTimeValue: '24', // 预告时间值
        limitAmount: '', // 限购数量
        limitPaytime: '', // 支付有效时间
        secKillProduct: [], // 秒杀价格表格数据
        freeFreight: 0, // 运费设置
        baseSale: 0,
        first: 1, // 活动优先级
        stock: 0, // 活动总库存
        cardId: [], // 会员卡id
        activityTag: 0, // 同步打标签
        activityTagId: [], // 标签id值
        shareConfig: {
          shareAction: 1,
          shareDoc: '',
          shareImgAction: 1,
          shareImg: ''
        }
      },
      // 校验表单
      fromRules: {
        name: [
          { required: true, message: '请填写活动名称', trigger: 'blur' }
        ],
        goodsId: [
          { required: true, message: '请选择活动商品', trigger: 'change' }
        ],
        validity: [
          { required: true, message: '请填写有效期', trigger: 'change' }
        ],
        preTime: [
          { required: true, validator: validatePreTime, trigger: 'change' }
        ],
        limitAmount: [
          { required: true, message: '请填写限购数量', trigger: 'change' }
        ],
        limitPaytime: [
          { required: true, message: '请填写支付时间', trigger: 'change' }
        ],
        freeFreight: [
          { required: true, message: '请填写运费设置', trigger: 'change' }
        ],
        first: [
          { required: true, message: '请填写优先级', trigger: 'change' }
        ],
        cardId: [
          { validator: validateCard, trigger: 'change' }
        ]
        // 'shareConfig.shareAction': [
        //   { validator: validateShare, trigger: 'change' }
        // ]
      },
      disabledFlag: true, // 是否可编辑
      submitStatus: false, // 提交

      // 展开设置箭头
      ArrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }],
      arrorFlag: true, // 展开更多配置

      showMember: false, // 会员专享
      cardList: [], // 会员卡列表

      // 分享
      showImageDialog: false,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/seckill_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/seckill_pictorial.jpg`
      },

      isShowChoosingGoodsDialog: false, // 商品弹窗
      showSpecDialog: false, // 规格弹窗
      productInfo: {}, // 规格回显数据

      labelDialogVisible: false, // 标签弹窗
      labelList: [], // 标签列表数据
      pickLabel: [] // 选中标签列表
    }
  },
  mounted () {
    // 编辑初始化
    if (this.isGoing === true) {
      this.editSeckillInit()
    }
    if (this.isEdite === true) {
      this.disabledFlag = true
    } else {
      this.disabledFlag = false
    }

    // 获取会员卡数据
    this.getAllCard()
    // 获取标签列表
    allTagApi().then(res => {
      if (res.error === 0) {
        this.labelList = res.content
      }
    })
  },
  watch: {
    lang () {

    },
    'form.goodsId': function (value) {
      if (value) {
        this.$refs.form.validateField('goodsId')
      }
    }
  },
  methods: {
    // 获取会员卡数据
    getAllCard () {
      allCardApi().then((res) => {
        if (res.error === 0) {
          this.cardList = res.content
        }
      })
    },

    // 编辑初始化
    editSeckillInit () {
      getSeckillList({ skId: this.editId }).then((res) => {
        console.log(res)
        if (res.error === 0) {
          var data = res.content
          this.form.name = data.name
          this.form.goodsId = data.goods.map(item => { return item.goodsId })
          this.form.secKillProduct = this.initEditProduct(data.goods)
          console.log(this.form.secKillProduct)
          this.form.startTime = data.startTime
          this.form.endTime = data.endTime
          this.form.validity = [data.startTime, data.endTime]
          this.form.limitAmount = data.limitAmount
          this.form.limitPaytime = data.limitPaytime
          this.form.freeFreight = data.freeFreight
          this.form.first = data.first
          this.form.baseSale = data.baseSale
          // 展开设置
          this.arrorFlag = false
          // 会员卡
          this.form.cardId = []
          data.memberCard.map((item, index) => {
            this.form.cardId.push(item.id)
          })
          if (this.form.cardId.length > 0) {
            this.showMember = true
          } else {
            this.showMember = false
          }
          // 活动分享
          if (data.shopShareConfig) {
            this.form.shareConfig.shareAction = data.shopShareConfig.shareAction
            this.form.shareConfig.shareDoc = data.shopShareConfig.shareDoc
            this.form.shareConfig.shareImgAction = data.shopShareConfig.shareImgAction
            this.form.shareConfig.shareImg = data.shopShareConfig.shareImg
          }
          // 总库存
          this.form.stock = 0
          // 活动预告
          this.form.preTime = data.preTime
          if (this.form.preTime > 0) {
            this.form.preTimeValue = this.form.preTime
            this.form.preTime = 1
          }
          // 标签id tagList
          if (res.content.tagList && res.content.tagList.length > 0) {
            this.pickLabel = res.content.tagList
            this.form.activityTagId = []
            res.content.tagList.forEach(item => {
              this.form.activityTagId.push(item.id)
            })
          } else {
            this.form.activityTagId = []
          }

          console.log(this.form)
        }
      })
    },

    // 编辑回显规格
    initEditProduct (goods) {
      let newdata = []
      goods.forEach(item => {
        let expand = item.secKillProduct.length < 2 ? { ...item.secKillProduct[0] } : { ...item.secKillProduct[0], goodsSpecProducts: item.secKillProduct }
        newdata.push({ ...item, ...expand })
      })
      newdata.forEach(item => {
        if (item.goodsSpecProducts && item.goodsSpecProducts.length > 0) {
          item.flag1 = true
          item.flag2 = true
          item.totalStock = 0
          item.goodsSpecProducts.forEach(val => {
            item.totalStock += parseInt(val.stock)
          })
        }
      })
      return newdata
    },

    // 保存秒杀活动
    saveClickHandler () {
      // 校验活动分享
      if (this.form.shareConfig.shareAction === 2 && this.form.shareConfig.shareDoc === '') {
        this.$message.warning('请填写活动文案')
        return false
      } else if (this.form.shareConfig.shareAction === 2 && this.form.shareConfig.shareImgAction === 2 && !this.form.shareConfig.shareImg) {
        this.$message.warning('请选择活动图片')
        return false
      }

      this.$refs['form'].validate((valid) => {
        let goodsId = this.form.goodsId.join(',')
        if (valid) {
          this.submitStatus = true
          // 有效期
          this.form.startTime = this.form.validity[0]
          this.form.endTime = this.form.validity[1]
          // 会员卡专享
          if (this.form.cardId !== undefined && this.form.cardId.length > 0) {
            this.form.cardId = this.form.cardId.toString()
          } else {
            this.form.cardId = ''
          }
          // 活动分享
          if (this.form.shareConfig.shareAction === 1) {
            this.form.shareConfig.shareDoc = ''
            this.form.shareConfig.shareImgAction = 1
            this.form.shareConfig.shareImg = ''
          }
          // 总库存
          this.form.secKillProduct.forEach((item, index) => {
            item.productId = item.prdId
            item.secKillPrice = Number(item.secKillPrice)
          })
          // 活动预告
          if (this.form.preTime === 1) {
            this.form.preTime = Number(this.form.preTimeValue)
          }
          // 同步打标签
          // if (this.form.activityTagId && this.form.activityTagId.length > 0) {
          //   this.form.activityTagId = this.form.activityTagId.toString()
          // }

          console.log(this.form)

          if (this.form.goodsId.length === 0) {
            this.$message.warning({ message: '请选择商品!' })
            this.submitStatus = false
            return
          }
          let secKillProduct = []
          this.form.stock = 0
          this.form.secKillProduct.forEach(item => {
            if (item.goodsSpecProducts) {
              item.goodsSpecProducts.forEach(specItem => {
                let { secKillPrice, stock } = specItem
                let goodsId = item.goodsId
                let productId = this.isEdite ? specItem.productId : specItem.prdId
                secKillProduct.push({ goodsId, productId, secKillPrice: Number(secKillPrice), stock: Number(stock) })
                this.form.stock += Number(stock)
              })
            } else {
              let { goodsId, prdId: productId, secKillPrice, stock } = item
              secKillProduct.push({ goodsId, productId, secKillPrice, stock: Number(stock) })
              this.form.stock += Number(stock)
            }
          })

          if (this.isGoing === false) {
            // 添加秒杀
            addSeckillList({ ...this.form, secKillProduct, goodsId }).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: '添加成功' })
                this.$emit('addSeckillSubmit')
              } else {
                this.$message.warning({ message: res.message })
              }
            })
          } else {
            // 编辑秒杀
            updateSeckillList({
              skId: this.editId,
              name: this.form.name,
              cardId: this.form.cardId,
              first: this.form.first,
              shareConfig: this.form.shareConfig,
              secKillProduct: secKillProduct
            }).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: '修改成功' })
                this.$emit('addSeckillSubmit')
              } else {
                this.$message.warning({ message: res.message })
              }
            })
          }
        }
      })
      this.submitStatus = false
    },

    // 显示商品弹窗
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },

    // 商品弹窗的回调函数
    choosingGoodsResult (row) {
      if (row.length === 0) {
        return
      }
      this.form.goodsId = row.map(item => { return item.goodsId })
      this.form.secKillProduct = row
      // 可编辑状态
      this.disabledFlag = false
    },

    // 批量设置数据
    setCurrent (index) {
      this.activeIndex = index
      if (index === 1) {
        this.form.secKillProduct.forEach((item, index) => {
          this.$set(item, 'secKillPrice', this.form.secKillProduct[0].secKillPrice)
          this.priceChange(item)
        })
      } else if (index === 2) {
        this.form.secKillProduct.forEach((item, index) => {
          this.$set(item, 'stock', this.form.secKillProduct[0].stock)
          this.numChange(item)
        })
      }
    },

    // 显示规格弹窗
    showSpec (goodsInfo) {
      console.log(goodsInfo)
      this.productInfo = goodsInfo
      // 数据回显 secKillPrice stock
      this.productInfo.goodsSpecProducts.forEach(item => {
        item.secKillPrice = this.productInfo.flag1 ? item.secKillPrice : ''
        item.stock = this.productInfo.flag2 ? item.stock : ''
      })
      this.showSpecDialog = !this.showSpecDialog
    },

    // 规格弹窗回调函数
    getProductdata ({ goodsId, prdInfo }) {
      console.log(goodsId, prdInfo)
      let target = this.form.secKillProduct.find(item => { return item.goodsId === goodsId })
      let index = this.form.secKillProduct.findIndex(item => { return item.goodsId === goodsId })
      this.$set(this.form.secKillProduct[index], 'secKillPrice', prdInfo[0].secKillPrice)
      this.$set(this.form.secKillProduct[index], 'stock', prdInfo[0].stock)
      this.$set(this.form.secKillProduct[index], 'flag1', true)
      this.$set(this.form.secKillProduct[index], 'flag2', true)
      target.goodsSpecProducts = prdInfo
      if (target.goodsSpecProducts && target.goodsSpecProducts.length > 0) {
        target.totalStock = 0
        target.goodsSpecProducts.forEach(item => {
          target.totalStock += parseInt(item.stock)
        })
      }
    },

    // 图片弹窗
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },

    // 图片点击回调函数
    handleSelectImg (res) {
      if (res != null) {
        this.form.shareConfig.shareImg = res.imgUrl
      }
    },

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 刷新
    refresh () {
      this.getAllCard()
      this.$nextTick(() => {
        this.$message.success('刷新成功')
      })
    },

    // 跳转新建会员卡
    addMemberCard () {
      let routeUrl = this.$router.resolve({
        path: '/admin/home/main/normalCardDetail'
      })
      window.open(routeUrl.href, '_blank')
    },

    // 跳转管理会员卡
    manageMemberCard () {
      let routeUrl = this.$router.resolve({
        path: '/admin/home/main/user_card'
      })
      window.open(routeUrl.href, '_blank')
    },

    // 校验秒杀价格
    validateMoney (rule, value, callback, prdPrice, row) {
      row.flag1 = false
      // 找到最低规格价
      if (row.goodsSpecProducts && row.goodsSpecProducts.length > 0) {
        row.goodsSpecProducts.forEach(item => {
          if (prdPrice > item.shopPrice) {
            prdPrice = item.shopPrice
          }
        })
      }

      var re = /^\d+(\.\d{1,2})?$/
      if (!value) {
        callback(new Error('秒杀价不能为空'))
      } else if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (Number(value) > Number(prdPrice)) {
        callback(new Error('秒杀价不能大于商品规格原价'))
      } else {
        callback()
        row.flag1 = true
      }
    },

    // 校验秒杀库存
    validateNum (rule, value, callback, prdNumber, row, index) {
      row.flag2 = false
      // 找到最低规格库存
      if (row.goodsSpecProducts && row.goodsSpecProducts.length > 0) {
        row.goodsSpecProducts.forEach(item => {
          if (prdNumber > item.prdNumber) {
            prdNumber = item.prdNumber
          }
        })
      }

      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value) {
        callback(new Error('秒杀库存不能为空'))
      } else if (!re.test(value)) {
        callback(new Error('请填写0或正整数'))
      } else if (Number(value) > Number(prdNumber)) {
        callback(new Error('秒杀库存不能大于商品规格库存'))
      } else {
        callback()
        row.flag2 = true
      }
    },

    // 价格切换
    priceChange (data) {
      if (data.goodsSpecProducts && data.goodsSpecProducts.length > 0) {
        var result = data.goodsSpecProducts.find(item => { return data.secKillPrice > item.prdPrice })
        var re = /^\d+(\.\d{1,2})?$/
        if (result === undefined && re.test(data.secKillPrice)) {
          data.goodsSpecProducts.forEach((item, index) => {
            item.secKillPrice = data.secKillPrice
          })
        }
      }
    },

    // 库存切换
    numChange (data) {
      if (data.goodsSpecProducts && data.goodsSpecProducts.length > 0) {
        data.totalStock = 0
        var result = data.goodsSpecProducts.find(item => { return data.stock > item.prdNumber })
        var re = /^(0|\+?[1-9][0-9]*)$/
        if (result === undefined && re.test(data.stock)) {
          data.goodsSpecProducts.forEach((item, index) => {
            item.stock = data.stock
            data.totalStock += parseInt(item.stock)
          })
        }
      }
    },

    cardIdBlur (e) {
      this.$refs['form'].validateField('cardId')
    },

    // 标签弹窗
    selectLabel () {
      if (this.isEdite === true) {
        return false
      }
      this.labelDialogVisible = !this.labelDialogVisible
    },

    // 删除标签
    deleteLabel (index) {
      if (this.isEdite === true) {
        this.$message.warning('编辑状态不可操作')
        return false
      }
      this.pickLabel.splice(index, 1)
      this.form.activityTagId = []
      this.pickLabel.forEach(item => {
        this.form.activityTagId.push(item.id)
      })
    },

    // 标签弹窗回调函数
    resultLabelDatas (row) {
      this.pickLabel = row
      this.form.activityTagId = []
      this.pickLabel.forEach(item => {
        this.form.activityTagId.push(item.id)
      })
    },

    // 活动预告类型切换
    preTimeChange (e) {
      this.$refs['form'].validateField('preTime')
    }

  }
}
</script>
<style scoped>
.container {
  margin-top: 10px;
  padding: 10px;
  margin-bottom: 100px;
  background: #fff;
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
  border-top: 1px solid #eee;
  z-index: 99;
}
.shareContent a {
  text-decoration: none;
  color: #409eff;
}
.shareContent a:first-child {
  margin-right: 10px;
}
.imgContent {
  width: 80px;
  height: 80px;
  text-align: center;
  line-height: 65px;
  margin-left: 60px;
  border: 1px solid #ccc;
  cursor: pointer;
  box-sizing: border-box;
}
.imgContent .shareImg {
  width: 100%;
  height: 100%;
}
.member {
  color: #409eff;
  margin: 0 5px;
  cursor: pointer;
}
.picSizeTips {
  display: block;
  line-height: 80px;
  margin-left: 20px;
  color: rgb(153, 153, 153);
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
/deep/ .el-form-item__error {
  position: relative;
  text-align: left;
}
.moreSetUp a {
  margin-right: 10px;
  cursor: pointer;
}
.settings {
  color: #5a8bff;
}
.spec-tips {
  text-align: center;
  color: #409eff;
  cursor: pointer;
}
.input-error {
  text-align: center;
  color: red;
  line-height: 1;
}

.labelStyle {
  color: #5a8bff;
  cursor: pointer;
}
.labelContent {
  display: inline-block;
  height: 30px;
  background: rgba(235, 241, 255, 1);
  border: 1px solid rgba(180, 202, 255, 1);
  border-radius: 2px;
  text-align: center;
  line-height: 30px;
  padding: 0 10px;
  margin-right: 10px;
  color: #666;
}
.goodImge {
  width: 100%;
  height: 100%;
  display: flex;
}
.goodImge .img {
  flex: 0.2;
}
.goodImge img {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border: 1px solid #ccc;
}
.goodImge .name {
  flex: 0.8;
  width: 115px;
  height: 50px;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  display: -webkit-box;
  margin-left: 12px;
  text-align: left;
}
</style>
