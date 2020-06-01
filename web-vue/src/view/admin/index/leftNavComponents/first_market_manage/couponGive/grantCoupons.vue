<template>
  <div class="mainContent">
    <div class="mainContentMiddle">
      <div class="mainContentRight">
        <div class="mainContentRightForm">
          <el-form
            label-width="150px"
            labelPosition='right'
            :rules="rules"
            :model="params"
            size="small"
            ref="params"
          >
            <!-- 活动名称 -->
            <el-form-item
              :label="$t('couponGive.actName') + '：'"
              prop="actName"
            >
              <el-input
                size="small"
                :placeholder="$t('couponGive.actNamePlaceholder')"
                v-model="params.actName"
                style="width: 170px"
              ></el-input>
            </el-form-item>
            <!-- 参与活动人群 -->
            <el-form-item
              :label="$t('couponGive.participants') + '：'"
              prop="crowd"
            >
              <div style="color:#999;fontSize:12px">{{$t('couponGive.peopleTip')}}</div>
              <!-- 加购人群 -->
              <div>
                <el-checkbox
                  :label="$t('couponGive.addCartPeople')"
                  @change="cartBoxHandler"
                  v-model="params.couponGiveGrantInfoParams.cart_box"
                  :true-label="1"
                  :false-label="0"
                ></el-checkbox>
                <span style="color:#999;fontSize:12px;">{{$t('couponGive.addCartTip')}}</span>
              </div>
              <!-- 购买指定商品人群 -->
              <div>
                <el-checkbox
                  :label="$t('couponGive.buyGoodsPeople')"
                  @change="goodsBoxHandler"
                  v-model="params.couponGiveGrantInfoParams.goods_box"
                  :true-label="1"
                  :false-label="0"
                ></el-checkbox>
                <span
                  style="color:#999;fontSize:12px"
                  v-if="params.couponGiveGrantInfoParams.goods_box === 1"
                >{{$t('couponGive.buyGoodsTip')}}</span>
              </div>
              <div
                class="chooseGoods"
                v-if="params.couponGiveGrantInfoParams.goods_box === 1"
              >
                <div class="chooseGoodsLeft">{{$t('couponGive.chooseGoods')}}</div>
                <ul class="imgList">
                  <li
                    v-for="(item) in imgsList"
                    :key="item.goodsId"
                  >
                    <el-image
                      style="width: 80px; height: 80px"
                      :src="item.goodsImg"
                    ></el-image>
                    <el-image
                      class="delImg"
                      :src="urls.url4"
                      @click="handleDelImg(item.goodsId)"
                    >
                    </el-image>
                  </li>
                  <div
                    v-show="this.imgsList.length<3"
                    class="imageWraper"
                    @click="showChoosingGoods"
                  >
                    <el-image :src="urls.url3"></el-image>
                  </div>
                </ul>

              </div>
              <!-- 属于 -->
              <!-- 持有 -->
              <div style="margin:10px 0">
                <chooseSelect @chooseSelectVal="getChooseSelectVal" />
              </div>
              <!-- 选择指定会员 -->
              <div style="margin:10px 0">
                <el-checkbox
                  :label="$t('couponGive.chooseMember')"
                  @change="memberBoxHandler"
                  v-model="params.couponGiveGrantInfoParams.member_box"
                  :true-label="1"
                  :false-label="0"
                ></el-checkbox>
                <el-button
                  @click="handleAddMember"
                  v-if="params.couponGiveGrantInfoParams.member_box === 1"
                  type="text"
                >+ {{$t('couponGive.addMember')}}</el-button>
                <span v-if="params.couponGiveGrantInfoParams.member_box === 1">{{$t('couponGive.selected')}} {{memberNum}} {{$t('couponGive.people')}}</span>
              </div>
              <!-- 自定义 -->
              <div>
                <el-checkbox
                  :label="$t('couponGive.custom')"
                  @change="customBoxHandler"
                  v-model="params.couponGiveGrantInfoParams.custom_box"
                  :true-label="1"
                  :false-label="0"
                ></el-checkbox>
                <el-select
                  v-model="customRuleInfoVal"
                  :disabled="params.couponGiveGrantInfoParams.custom_box === 0"
                  :placeholder="$t('couponGive.choose')"
                  size="small"
                  style="width: 170px;"
                  @change="customRuleInfoValChange"
                >
                  <el-option
                    :label="$t('couponGive.choose')"
                    :value="$t('couponGive.choose')"
                  ></el-option>
                  <el-option
                    v-for="item in customRuleInfoOptions"
                    :key="item.key"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
                <!-- 自定义集合 -->
                <div>
                  <ul class="ulList">
                    <li
                      v-for="(item) in optionsList"
                      :key="item.key"
                    >
                      <span>{{item.label}}：</span>
                      <el-input
                        @change="handleIpt(item)"
                        :disabled="params.couponGiveGrantInfoParams.custom_box === 0"
                        style="width:170px"
                        size="small"
                        v-model="item.ipt"
                      ></el-input>
                      <img
                        :src="urls.url4"
                        @click="handleDelCustomize(item)"
                      >
                    </li>
                    <li v-show="showTime">
                      <span>{{$t('couponGive.timeLoginRecord') + '：'}}</span>
                      <el-date-picker
                        v-model="params.couponGiveGrantInfoParams.validity"
                        type="datetimerange"
                        :range-separator="$t('seckill.to')"
                        :start-placeholder="$t('seckill.startTime')"
                        :end-placeholder="$t('seckill.endTime')"
                        :default-time="['00:00:00','23:59:59']"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        size="small"
                      ></el-date-picker>
                      <img
                        :src="urls.url4"
                        @click="handleDelCustomize(6)"
                      >
                    </li>
                  </ul>
                </div>
              </div>
            </el-form-item>
            <!-- 选择优惠券 -->
            <el-form-item
              :label="$t('couponGive.chooseCoupons') + '：'"
              prop="coupon"
            >
              <div class="gray">{{$t('couponGive.couponTip')}}</div>
              <div class="middleContainer">
                <div>
                  <div
                    v-for="(item,index) in couponData"
                    :key="index"
                    class="addInfo"
                    style="margin-right: 5px;"
                  >
                    <section
                      class="couponImgWrapper"
                      style="line-height:normal"
                    >
                      <div class="coupon_list_top">
                        <span
                          v-if="item.actCode==='voucher'"
                          class="number"
                        >￥{{item.denomination}}</span>
                        <span v-if="item.actCode==='discount'">
                          <span class="number">{{item.denomination}}</span> 折
                        </span>
                        <span v-if="item.actCode==='random'">
                          <span class="number">￥{{item.randomMax}}</span>最高
                        </span>
                      </div>
                      <div class="coupon_center_limit">{{item.useConsumeRestrict | formatLeastConsume(item.leastConsume)}}</div>
                      <div
                        class="coupon_center_number"
                        v-if="item.surplus ===0"
                      >库存不限制</div>
                      <div
                        class="coupon_center_number"
                        v-if="item.surplus !==0"
                      >剩余{{item.surplus}}张</div>
                      <div
                        class="coupon_list_bottom"
                        style="font-size:12px"
                      >领取</div>
                    </section>
                    <span
                      @click="deleteCouponImg(index)"
                      class="deleteIcon"
                    >
                      <img
                        :src="imgHost+'/image/admin/sign_del.png'"
                        alt=""
                      >
                    </span>
                  </div>
                </div>
                <div
                  class="addInfo"
                  @click="handleToCallDialog"
                  v-if="couponData.length < 5"
                >
                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                    style="width: 78px;height:78px;cursor:pointer"
                  ></el-image>
                  <p>添加优惠券</p>
                </div>
              </div>
            </el-form-item>

            <!-- 发送时间 -->
            <el-form-item
              :label="$t('couponGive.grantTime') + '：'"
              prop="startTime"
            >
              <el-radio
                :label="0"
                v-model="params.sendAction"
                @change="sendActionChange"
              >{{$t('couponGive.immediatelyGrant')}}</el-radio>
              <br>
              <el-radio
                :label="1"
                v-model="params.sendAction"
                @change="sendActionChange"
              >{{$t('couponGive.regularlyGrant')}}</el-radio>
              <el-date-picker
                type="datetime"
                class="morelength"
                size="small"
                value-format="yyyy-MM-dd HH:mm:ss"
                v-model="params.startTime"
              >
              </el-date-picker>
            </el-form-item>
            <div class="grantNum">预计发放用户数：{{this.userNumber}}人</div>
          </el-form>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        @click="addAct"
      >{{$t('couponGive.confirmGrant')}}</el-button>
    </div>
    <!-- 选择指定商品弹窗 -->
    <choosingGoods
      @resultGoodsDatas="choosingGoodsResult"
      :tuneUpChooseGoods='isShowChoosingGoodsDialog'
      :checkedNumMax=3
      :chooseGoodsBack='this.params.couponGiveGrantInfoParams.goods_ids'
    />

    <!-- 选择指定会员弹窗 -->
    <memberListDialog
      v-if="dialogOff"
      @userIdList="getUserIdList"
      @dialog-cancel="closeDialog"
      :memberListDialog="dialogOff"
      :chooseMemberBack='this.params.user'
    />

    <!--添加优惠券弹窗-->
    <addCouponDialog
      @checkReturnFormat="handleToCheck"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
      :type="-1"
    />
  </div>
</template>>

<script>

import { addActivity, getUserNum } from '@/api/admin/marketManage/couponGive.js'

// 选择商品弹窗
import choosingGoods from '@/components/admin/choosingGoods'
import memberListDialog from '../messagePush/memberListDialog'
import getUserDialog from '../messagePush/getUserDialog'
import chooseSelect from '@/components/admin/chooseSelect/chooseSelect'
import wrapper from '@/components/admin/wrapper/wrapper'
import addCouponDialog from '@/components/admin/addCouponDialog'

export default {
  components: {
    wrapper,
    chooseSelect,
    memberListDialog,
    choosingGoods,
    getUserDialog,
    addCouponDialog
  },
  data () {
    // 发放时间
    var validateTime = (rule, value, callback) => {
      if (this.params.sendAction === 1 && !value) {
        callback(new Error('请填写发送时间'))
      } else {
        callback()
      }
    }
    // 选择优惠券
    var validateCoupon = (rule, value, callback) => {
      if (this.couponData === undefined || this.couponData.length <= 0) {
        callback(new Error('请选择优惠券'))
      } else {
        callback()
      }
    }
    // 参与活动人群
    var validateCrowd = (rule, value, callback) => {
      if (this.params.couponGiveGrantInfoParams.cart_box === 0 &&
        this.params.couponGiveGrantInfoParams.goods_box === 0 &&
        this.params.couponGiveGrantInfoParams.card_box === 0 &&
        this.params.couponGiveGrantInfoParams.tag_box === 0 &&
        this.params.couponGiveGrantInfoParams.member_box === 0 &&
        this.params.couponGiveGrantInfoParams.custom_box === 0) {
        callback(new Error('请至少选择一种类型发送人群'))
      } else if (this.params.couponGiveGrantInfoParams.goods_box === 1 && (this.imgsList === undefined || this.imgsList.length <= 0)) {
        callback(new Error('请选择商品'))
      } else if (this.params.couponGiveGrantInfoParams.card_box === 1 && (this.params.cardId === undefined || this.params.cardId.length <= 0)) {
        callback(new Error('请选择会员卡'))
      } else if (this.params.couponGiveGrantInfoParams.tag_box === 1 && (this.params.tagId === undefined || this.params.tagId.length <= 0)) {
        callback(new Error('请选择标签'))
      } else if (this.params.couponGiveGrantInfoParams.member_box === 1 && (this.params.user === undefined || this.params.user.length <= 0)) {
        callback(new Error('请选择会员'))
      } else {
        callback()
      }
    }
    return {
      imgHost: `${this.$imageHost}`,
      // 初始化弹窗选中的行
      urls: {
        url1: `${this.$imageHost}/image/admin/notice_img.png`,
        url2: `${this.$imageHost}/image/admin/shop_logo_default.png`,
        url3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        url4: `${this.$imageHost}/image/admin/icon_delete.png`
      },
      // 参数
      params: {
        actName: '', // 活动名称
        sendAction: 0, // 发送状态
        startTime: '', // 定时发送时间
        couponGiveGrantInfoParams: {
          cart_box: 0, // 加购会员
          goods_box: 0, // 加购指定商品
          goods_ids: [], // 指定商品id
          card_box: 0, // 会员卡
          tag_box: 0, // 会员标签
          member_box: 0, // 指定会员
          custom_box: 0, // 自定义
          validity: '',
          point_start_time: '',
          point_end_time: '',
          coupon_ids: [] // 选择优惠券
        },
        cardId: [],
        tagId: [],
        user: [],
        havePay: '',
        noPay: '',
        maxCount: '',
        minCount: '',
        maxAvePrice: '',
        minAvePrice: ''
      },
      // 表单检验
      rules: {
        actName: [
          { required: true, message: `${this.$t('couponGive.actNameTip')}`, trigger: 'change' }
        ],
        startTime: [
          { required: true, validator: validateTime, trigger: 'change' }
        ],
        coupon: [
          { required: true, validator: validateCoupon, trigger: 'change' }
        ],
        crowd: [
          { required: true, validator: validateCrowd, trigger: 'change' }
        ]
      },
      userNumber: 0, // 预计发放人数
      isShowChoosingGoodsDialog: false, // 指定商品弹窗
      imgsList: [], // 指定商品列表数据
      dialogOff: false, // 指定会员弹窗
      memberNum: 0, // 已选择的会员人数
      showCouponDialog: false, // 优惠券弹窗
      couponData: [], // 优惠券列表
      couponIdList: [], // 优惠券回显数据

      customRuleInfoVal: `${this.$t('couponGive.choose')}`, // 自定义选值
      // 自定义选项
      customRuleInfoOptions: [{
        label: `N ${this.$t('couponGive.haveRecord')}`,
        value: `N ${this.$t('couponGive.haveRecord')}`,
        key: `payedDay`,
        ipt: ``
      }, {
        label: `N ${this.$t('couponGive.noRecord')}`,
        value: `N ${this.$t('couponGive.noRecord')}`,
        key: `noPayDay`,
        ipt: ``
      }, {
        label: `${this.$t('couponGive.buyLess')} N`,
        value: `${this.$t('couponGive.buyLess')} N`,
        key: `buyTimesLess`,
        ipt: ``
      }, {
        label: `${this.$t('couponGive.buyMore')} N`,
        value: `${this.$t('couponGive.buyMore')} N`,
        key: `buyTimesMore`,
        ipt: ``
      }, {
        label: `${this.$t('couponGive.priceHigher')} N`,
        value: `${this.$t('couponGive.priceHigher')} N`,
        key: `moneyAvgMore`,
        ipt: ``
      }, {
        label: `${this.$t('couponGive.priceLess')} N`,
        value: `${this.$t('couponGive.priceLess')} N`,
        key: `moneyAvgLess`,
        ipt: ``
      }, {
        label: `${this.$t('couponGive.timeLoginRecord')}`,
        value: `${this.$t('couponGive.timeLoginRecord')}`,
        key: `time`
      }],
      optionsList: [], // 自定义填写
      showTime: false // 自定义-登录记录
    }
  },
  mounted () {
    // 初始化国际化语言
    this.langDefault()
  },
  methods: {
    // 指定商品弹窗
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 指定商品弹窗的回调函数
    choosingGoodsResult (row) {
      this.imgsList = row
      this.params.couponGiveGrantInfoParams.goods_ids = []
      row.forEach((item, index) => {
        this.params.couponGiveGrantInfoParams.goods_ids.push(item.goodsId)
      })
      // 发送获取人数
      this.fetchUserList(this.params)
      this.$refs['params'].validateField('crowd')
    },
    // 删除指定商品
    handleDelImg (id) {
      this.imgsList = this.imgsList.filter((item) => item.goodsId !== id)
      this.params.couponGiveGrantInfoParams.goods_ids = []
      this.imgsList.forEach((item, index) => {
        this.params.couponGiveGrantInfoParams.goods_ids.push(item.goodsId)
      })
      // 发送获取人数接口
      this.fetchUserList(this.params)
      this.$refs['params'].validateField('crowd')
    },

    // 属于 / 持有的回调函数
    getChooseSelectVal (val) {
      this.params.cardId = val.cardIdsList
      this.params.tagId = val.tagIdList
      this.params.couponGiveGrantInfoParams.card_box = val.onClickCard === true ? 1 : 0
      this.params.couponGiveGrantInfoParams.tag_box = val.onClickTag === true ? 1 : 0
      // 发送获取人数接口
      this.fetchUserList(this.params)
      this.$refs['params'].validateField('crowd')
    },

    // 指定会员弹窗
    handleAddMember () {
      this.dialogOff = !this.dialogOff
    },
    // 指定会员弹窗回调函数
    getUserIdList (row) {
      this.memberNum = row.length // 把选中的数组长度赋值给已选会员数
      this.params.user = []
      row.forEach(item => {
        this.params.user.push(item.userId)
      })
      // 发送获取人数接口
      this.fetchUserList(this.params)
      this.$refs['params'].validateField('crowd')
    },
    // 关闭指定会员弹窗
    closeDialog () {
      this.dialogOff = false
    },

    // 自定义选值切换
    customRuleInfoValChange (val) {
      if (val === `${this.$t('couponGive.timeLoginRecord')}`) {
        this.showTime = true
      } else {
        if (val !== `${this.$t('couponGive.choose')}` && val !== `${this.$t('couponGive.timeLoginRecord')}`) {
          // 已选择自定义选项
          var data = this.customRuleInfoOptions.find(item => item.value === val)
          this.optionsList.push(data)
        }
      }
      this.customRuleInfoOptions = this.customRuleInfoOptions.filter((item) => item.value !== val)
      this.customRuleInfoVal = `${this.$t('couponGive.choose')}`
    },
    // 自定义值输入框变化
    handleIpt (item) {
      if (item.key === 'payedDay') {
        this.params.havePay = item.ipt
      } else if (item.key === 'noPayDay') {
        this.params.noPay = item.ipt
      } else if (item.key === 'buyTimesLess') {
        this.params.maxCount = item.ipt
      } else if (item.key === 'buyTimesMore') {
        this.params.minCount = item.ipt
      } else if (item.key === 'moneyAvgLess') {
        this.params.maxAvePrice = item.ipt
      } else if (item.key === 'moneyAvgMore') {
        this.params.minAvePrice = item.ipt
      }
      // 发送获取人数
      this.fetchUserList(this.params)
      this.$refs['params'].validateField('crowd')
    },
    // 删除自定义选择
    handleDelCustomize (val) {
      if (this.params.couponGiveGrantInfoParams.custom_box === 0) {
        return
      }
      if (val === 6) {
        this.showTime = false
        this.customRuleInfoOptions.push({
          label: `${this.$t('couponGive.timeLoginRecord')}`,
          value: `${this.$t('couponGive.timeLoginRecord')}`
        })
        this.params.couponGiveGrantInfoParams.validity = ''
      } else {
        if (val.key === 'payedDay') {
          this.params.havePay = ''
        } else if (val.key === 'noPayDay') {
          this.params.noPay = ''
        } else if (val.key === 'buyTimesLess') {
          this.params.maxCount = ''
        } else if (val.key === 'buyTimesMore') {
          this.params.minCount = ''
        } else if (val.key === 'moneyAvgLess') {
          this.params.maxAvePrice = ''
        } else if (val.key === 'moneyAvgMore') {
          this.params.minAvePrice = ''
        }
        this.optionsList = this.optionsList.filter((item) => item.key !== val.key)
        this.customRuleInfoOptions.unshift(val)
      }
      // 发送获取人数
      this.fetchUserList(this.params)
      this.$refs['params'].validateField('crowd')
    },

    // 选择优惠券弹窗
    handleToCallDialog () {
      this.showCouponDialog = !this.showCouponDialog
    },
    // 优惠券弹窗的回调函数
    handleToCheck (data) {
      this.couponData = data
      this.params.couponGiveGrantInfoParams.coupon_ids = []
      this.couponData.forEach(item => {
        this.params.couponGiveGrantInfoParams.coupon_ids.push(item.id)
      })
      this.$refs['params'].validateField('coupon')
    },
    // 删除优惠券
    deleteCouponImg (index) {
      this.couponData.splice(index, 1)
      this.params.couponGiveGrantInfoParams.coupon_ids = []
      this.couponData.forEach(item => {
        this.params.couponGiveGrantInfoParams.coupon_ids.push(item.id)
      })
      this.couponIdList = this.params.couponGiveGrantInfoParams.coupon_ids
      this.$refs['params'].validateField('coupon')
    },

    // 发放时间切换
    sendActionChange (e) {
      this.$refs['params'].validateField('startTime')
    },

    // 保存活动
    addAct () {
      this.$refs['params'].validate((valid) => {
        if (valid) {
          var data = this.params
          // 会员卡
          data.cardId = data.cardId.join(',')
          // 会员标签
          data.tagId = data.tagId.join(',')
          // 指定会员
          data.user = data.user.join(',')
          // 优惠券
          data.couponGiveGrantInfoParams.coupon_ids = data.couponGiveGrantInfoParams.coupon_ids.join(',')
          // 指定商品id
          data.couponGiveGrantInfoParams.goods_ids = data.couponGiveGrantInfoParams.goods_ids.join(',')
          // 指定登陆时间
          if (data.couponGiveGrantInfoParams.validity) {
            data.couponGiveGrantInfoParams.point_start_time = data.couponGiveGrantInfoParams.validity[0]
            data.couponGiveGrantInfoParams.point_end_time = data.couponGiveGrantInfoParams.validity[1]
          }
          addActivity(data).then(res => {
            if (res.error === 0) {
              this.$message.success('添加成功!')
              this.$router.push({
                path: `/admin/home/main/couponGive`
              })
            }
          }).catch(() => {
            this.$message.error(`${this.$t('couponGive.operationFailed')}`)
          })
        } else {
          return false
        }
      })
    },

    // 获取发送人群数量
    fetchUserList (params) {
      let data = {
        couponGiveGrantInfoParams: {
          cart_box: params.couponGiveGrantInfoParams.cart_box, // 加购会员
          goods_box: params.couponGiveGrantInfoParams.goods_box, // 加购指定商品
          goods_ids: params.couponGiveGrantInfoParams.goods_ids, // 指定商品id
          card_box: params.couponGiveGrantInfoParams.card_box, // 会员卡
          tag_box: params.couponGiveGrantInfoParams.tag_box, // 会员标签
          member_box: params.couponGiveGrantInfoParams.member_box, // 指定会员
          custom_box: params.couponGiveGrantInfoParams.custom_box, // 自定义
          validity: params.couponGiveGrantInfoParams.validity,
          point_start_time: '',
          point_end_time: ''
        },
        cardId: params.cardId,
        tagId: params.tagId,
        user: params.user,
        havePay: params.havePay,
        noPay: params.noPay,
        maxCount: params.maxCount,
        minCount: params.minCount,
        maxAvePrice: params.maxAvePrice,
        minAvePrice: params.minAvePrice
      }
      // 会员卡
      data.cardId = data.cardId.join(',')
      // 会员标签
      data.tagId = data.tagId.join(',')
      // 指定会员
      data.user = data.user.join(',')
      // 指定商品id
      data.couponGiveGrantInfoParams.goods_ids = data.couponGiveGrantInfoParams.goods_ids.join(',')
      // 指定登陆时间
      data.couponGiveGrantInfoParams.point_start_time = data.couponGiveGrantInfoParams.validity[0]
      data.couponGiveGrantInfoParams.point_end_time = data.couponGiveGrantInfoParams.validity[1]
      getUserNum(data).then((res) => {
        if (res.error === 0) {
          this.userNumber = res.content.userNum
        }
      })
    },
    // 加购人群发生变化的时候
    cartBoxHandler (val) {
      this.fetchUserList(this.params)
    },
    // 指定购买商品人群发生变化的时候
    goodsBoxHandler (val) {
      this.imgsList = []
      this.params.couponGiveGrantInfoParams.goods_ids = []
      this.fetchUserList(this.params)
    },
    // 选择指定的会员状态发生变化的时候
    memberBoxHandler (val) {
      this.params.user = []
      this.memberNum = 0
      this.fetchUserList(this.params)
    },
    // 当自定义发生变化的时候
    customBoxHandler (val) {
      this.fetchUserList(this.params)
    }
  },
  filters: {
    filterA: function (val) {
      if (val.search('N') !== -1) {
        return val.substr(val.search('N') + 1, 1)
      }
    },
    formatLeastConsume (useConsumeRestrict, leastConsume) {
      if (useConsumeRestrict === 0) {
        return `不限制`
      } else {
        return `满${leastConsume}元可用`
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.inputTip {
  color: #999;
  margin-left: 15px;
}
.coupon_info {
  display: flex;
  flex-direction: column;
  width: 200%;
  line-height: 25px;
  margin: 0 auto;
  margin-bottom: -10px;
  .coupon_item {
    margin-bottom: 10px;
    height: 50px;
    display: flex;
    > div {
      background-color: #fff;
      height: 100px;
    }
    .coupon_left {
      width: 100px;
      text-align: center;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      .coupon_price {
        color: #f66;
        margin-bottom: 10px;
        > span {
          font-size: 18px;
          font-weight: 600;
        }
      }
      .coupon_rule {
        color: #999;
        font-size: 14px;
      }
    }
    .coupon_middle {
      width: 25px;
      background: none;
      > img {
        width: 100%;
        height: 100%;
      }
    }
    .coupon_right {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: flex-start;
      padding-left: 10px;
      position: relative;
      overflow: hidden;
      .coupon_name {
        font-weight: bold;
        font-size: 14px;
        margin-bottom: 5px;
        width: 100%;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
      .coupon_limits {
        margin-bottom: 15px;
        font-size: 13px;
      }
      .coupon_time {
        color: #999;
        font-size: 12px;
      }
      .coupon_icon {
        position: absolute;
        right: -15px;
        top: 8px;
        background: #fead2d;
        width: 64px;
        font-size: 12px;
        color: #fff;
        transform: rotate(40deg);
        text-align: center;
      }
    }
  }
}
.content {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  padding-bottom: 50px;
  .main {
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    .el-form-item {
      margin-bottom: 16px;
    }
    .el-input {
      width: 90px;
    }
    .morelength {
      width: 100px;
    }
    .chooseGoods {
      width: 120px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      border: 1px solid #ccc;
    }
    .gray {
      color: #999;
    }
  }
}
.ImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
}
.ImgWrap .moveIcon {
  width: 17px;
  height: 17px;
  display: none;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  line-height: 17px;
  text-align: center;
  position: absolute;
  bottom: 0px;
  cursor: pointer;
  opacity: 0.8;
}
.ImgWrap:hover .moveIcon {
  display: block;
}
.selectedWrap {
  min-width: 70px;
  height: 22px;
  border: 1px solid #ccc;
  line-height: 22px;
  text-align: center;
  padding: 0px 5px;
  margin: 0px 5px;
  background-color: #fff;
  position: relative;
}
.footer {
  padding: 10px 0px 10px 0px;
  // text-align: center;
  background: #f8f8f8;
  margin-top: 10px;
  position: fixed;
  bottom: 0;
  z-index: 1;
  width: 100%;
  padding-left: 43%;
}
.mainContent {
  width: 100%;
  .mainContentMiddle {
    padding: 15px;
    background: #e6e9f0;
    .mainContentRight {
      margin-bottom: 100px;
      min-width: 520px;
      min-height: 1010px;
      background-color: white;
      padding-top: 15px;
      .mainContentRightForm {
        .mainContentRightFormText {
          color: #999;
        }
        .addTemplateBtnWrap {
          position: relative;
          .addTemplateBtn {
            position: absolute;
            bottom: 10px;
            left: 10px;
          }
        }
      }
      .chooseGoods {
        display: flex;
        margin: 20px 0;
        .chooseGoodsLeft {
          margin-left: 40px;
          margin-right: 20px;
        }
        .imgList {
          display: flex;
          li {
            margin-right: 10px;
            position: relative;
            .delImg {
              position: absolute;
              top: -6px;
              right: -6px;
            }
          }
        }
        .imageWraper {
          width: 80px;
          height: 80px;
          cursor: pointer;
          border: 1px solid #ccc;
          background: #f7f7f7;
          display: flex;
          justify-content: center;
          align-items: center;
        }
      }
      .selectedCard {
        border: 1px solid #eee;
        width: 382px;
        min-height: 56px;
        margin: 10px 0;
        display: flex;
        flex-wrap: wrap;
        .oneCardWraper {
          position: relative;
          margin: 2px 6px;
          .oneCard {
            padding: 0 10px;
            min-width: 70px;
            // margin: 10px;
            // display: flex;
            text-align: center;
            line-height: 24px;
            background-color: #fff;
            height: 24px;
            border: 1px solid #ccc;
            display: inline-block;
          }
          .oneCardDel {
            position: absolute;
            right: -5px;
            top: -5px;
            cursor: pointer;
          }
        }
      }
      .ulList {
        width: 100%;
        li:first-child {
          margin-top: 10px;
        }
        li {
          height: 50px;
          position: relative;
          span {
            margin: 0 5px;
          }
        }
      }
    }
  }
}
.middleContainer {
  display: flex;
  .deleteIcon {
    position: relative;
    width: 17px !important;
    height: 17px;
    line-height: 17px;
    top: -112px;
    left: 45px;
    text-align: center;
    cursor: pointer;
  }
}
.addInfo {
  display: inline-block;
  position: relative;
  width: 100px;
  height: 96px;
  margin-bottom: 10px;
  background: #fff;
  border: 1px solid #e4e4e4;
  cursor: pointer;
  text-align: center;
  border-radius: 10px;
  img {
    margin-top: 10px;
  }
  p {
    line-height: normal;
    margin-top: -30px;
    color: #999;
  }
  .couponImgWrapper {
    width: 100%;
    height: 100%;
    border: 1px solid #fbb;
    border-radius: 10px;
    .coupon_list_top {
      // margin-top: 10px;
      color: #f60;
      .number {
        font-size: 20px;
        font-weight: bold;
      }
    }
    .coupon_center_limit {
      height: 20px;
      color: #f60;
      font-size: 12px !important;
    }
    .coupon_center_number {
      height: 20px;
      color: #fbb;
    }
    .coupon_list_bottom {
      height: 24px;
      line-height: 30px;
      border-bottom-left-radius: 8px;
      border-bottom-right-radius: 8px;
      color: #fff;
      background: #f66;
      background-image: url("https://mpdev.weipubao.cn/image/admin/coupon_border.png");
      background-repeat: repeat-x;
    }
  }
}
.grantNum {
  width: 90%;
  height: 45px;
  line-height: 45px;
  background-color: #fff7eb;
  border: 1px solid #ffd5a3;
  border-radius: 4px;
  color: #666;
  margin: 0 auto;
  padding-left: 10px;
  font-size: 14px;
}
</style>
