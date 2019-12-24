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
              <div>
                <span style="color:#999;fontSize:12px">{{$t('couponGive.peopleTip')}}</span>
              </div>
              <div>
                <el-checkbox
                  :label="$t('couponGive.addCartPeople')"
                  @change="handleOnClickNoPayChange"
                  v-model="params.onClickNoPay"
                ></el-checkbox>
                <span style="color:#999;fontSize:12px;">{{$t('couponGive.addCartTip')}}</span>
              </div>
              <div>
                <el-checkbox
                  :label="$t('couponGive.buyGoodsPeople')"
                  @change="handleOnClickGoodsChange"
                  v-model="params.onClickGoods"
                ></el-checkbox>
                <span
                  style="color:#999;fontSize:12px"
                  v-if="params.onClickGoods"
                >{{$t('couponGive.buyGoodsTip')}}</span>
              </div>
              <div
                class="chooseGoods"
                v-if="params.onClickGoods"
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
                  @change="handleOnClickUserChange"
                  v-model="params.onClickUser"
                ></el-checkbox>
                <span v-if="params.onClickUser">
                  <el-button
                    @click="handleAddMember"
                    type="text"
                  >+ {{$t('couponGive.addMember')}}</el-button>
                </span>
                <span v-if="params.onClickUser">{{$t('couponGive.selected')}} {{memberNum}} {{$t('couponGive.people')}}</span>
              </div>
              <!-- 自定义 -->
              <div>
                <el-checkbox
                  :label="$t('couponGive.custom')"
                  @change="handleOnClickCustomRuleChange"
                  v-model="params.onClickCustomRule"
                ></el-checkbox>
                <el-select
                  v-model="customRuleInfoVal"
                  :disabled="!params.onClickCustomRule"
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
                <div style="margin:10px 0;">
                  <ul class="ulList">
                    <li
                      v-for="(item) in optionsList"
                      :key="item.key"
                    >
                      <span>{{item.label}}：</span>
                      <span>
                        <el-input
                          @blur="handleIpt(item)"
                          @focus="handleIpt(item)"
                          :disabled="!params.onClickCustomRule"
                          style="width:170px"
                          size="small"
                          v-model="item.ipt"
                        > </el-input>
                        <div>{{ item.label | filterA  }}</div>
                      </span>
                      <div class="img_span">
                        <el-image
                          :src="urls.url4"
                          class="img"
                          @click="handleDelCustomize(item)"
                        ></el-image>
                      </div>
                    </li>
                    <li v-show="showTime">
                      <span>{{$t('couponGive.timeLoginRecord')}}</span>
                      <div class="img_span">
                        <el-image
                          :src="urls.url4"
                          class="img"
                          @click="handleDelCustomize(6)"
                        ></el-image>
                      </div>
                      <span>
                        <dateTimePicker
                          :showPicker=1
                          @time="loginStartAndLoginEnd"
                        />
                      </span>

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
                        <span>￥</span>
                        <span class="number">{{item.denomination}}</span>
                      </div>
                      <div class="coupon_center_limit">{{item.useConsumeRestrict | formatLeastConsume(item.leastConsume)}}</div>
                      <div class="coupon_center_number">剩余{{item.surplus}}张</div>
                      <div
                        class="coupon_list_bottom"
                        style="font-size:12px"
                      >领取</div>
                    </section>
                    <span
                      @click="deleteCouponImg(index)"
                      class="deleteIcon"
                    >×</span>
                  </div>
                </div>
                <div
                  class="addInfo"
                  @click="handleToCallDialog1()"
                  v-if="couponData.length < 5"
                >
                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                    style="width: 78px;height:78px;cursor:pointer"
                  ></el-image>
                </div>
              </div>
            </el-form-item>

            <!-- 发送时间 -->
            <el-form-item
              :label="$t('couponGive.grantTime') + '：'"
              prop="startTime"
            >
              <el-radio
                label="0"
                v-model="params.sendAction"
                @change="sendActionChange"
              >{{$t('couponGive.immediatelyGrant')}}</el-radio>
              <br>
              <el-radio
                label="1"
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
            <div style="width: 90%;
                    height: 45px;
                    line-height: 45px;
                    background-color: #fff7eb;
                    border: 1px solid #ffd5a3;
                    border-radius: 4px;
                    color: #666;
                    margin: 0 auto;
                    padding-left: 10px;
                    font-size: 14px;">预计发放用户数：{{this.userNumber}}人</div>
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
    <!-- 添加会员的弹窗 -->
    <memberListDialog
      v-if="dialogOff"
      @userIdList="getUserIdList"
      :memberListDialog="dialogOff"
      @dialog-cancel="closeDialog"
    />
    <!-- 选择商品弹窗 -->

    <choosingGoods
      @resultGoodsDatas="choosingGoodsResult"
      :tuneUpChooseGoods='isShowChoosingGoodsDialog'
      :checkedNumMax=3
      :chooseGoodsBack='this.params.goodsIdList'
    />
    <choosingGoods
      @res="getRes"
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :checkedNumMax=3
      :chooseGoodsBack='this.params.goodsIdList'
    />
    <!-- 获取人群弹窗 -->
    <getUserDialog
      @dialog-cancel="closeDialog"
      :dialogVisible="dialogVisible"
    />
    <!--添加优惠券弹窗-->
    <addCouponDialog
      @checkReturnFormat="handleToCheck"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
    />
  </div>
</template>>

<script>
import { mapActions } from 'vuex'
// 选择商品弹窗
import choosingGoods from '@/components/admin/choosingGoods'

import memberListDialog from '../messagePush/memberListDialog'
import getUserDialog from '../messagePush/getUserDialog'
import chooseSelect from '@/components/admin/chooseSelect/chooseSelect'
import wrapper from '@/components/admin/wrapper/wrapper'
// import choosingGoods from '@/components/admin/choosingGoods'
import { addActivity } from '@/api/admin/marketManage/couponGive.js'
// // import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
// // import { selectGoodsApi } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods.js'
import { delObj } from '@/util/formatData'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
import { getUserNumberApi } from '@/api/admin/marketManage/messagePush.js'
import addCouponDialog from '@/components/admin/addCouponDialog'

export default {
  components: {
    wrapper,
    dateTimePicker,
    chooseSelect,
    memberListDialog,
    choosingGoods,
    getUserDialog,
    addCouponDialog

    // choosingGoods,
    // AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  data () {
    // 发放时间
    var validateTime = (rule, value, callback) => {
      if (this.params.sendAction === '1') {
        if (!value) {
          callback(new Error('请填写发送时间'))
        } else {
          callback()
        }
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
      if (this.params.onClickNoPay === false &&
        this.params.onClickGoods === false &&
        this.params.onClickCard === false &&
        this.params.onClickTag === false &&
        this.params.onClickUser === false &&
        this.params.onClickCustomRule === false) {
        callback(new Error('请至少选择一种类型发送人群'))
      } else if (this.params.onClickGoods === true && (this.imgsList === undefined || this.imgsList.length <= 0)) {
        callback(new Error('请选择商品'))
      } else if (this.params.onClickCard === true && (this.params.cardIdsList === undefined || this.params.cardIdsList.length <= 0)) {
        callback(new Error('请选择会员卡'))
      } else if (this.params.onClickTag === true && (this.params.tagIdsList === undefined || this.params.tagIdsList.length <= 0)) {
        callback(new Error('请选择标签'))
      } else if (this.params.onClickUser === true && (this.params.userIdList === undefined || this.params.userIdList.length <= 0)) {
        callback(new Error('请选择会员'))
      } else {
        callback()
      }
    }
    return {
      goodsNum: '',
      // 优惠券弹窗
      couponDialogFlag: false,
      couponData: [],
      couponId: '',
      showCouponDialog: false,
      couponIdList: [],
      imgHost: `${this.$imageHost}`,
      checkedData: [],
      // 初始化弹窗选中的行
      urls: {
        url1: `${this.$imageHost}/image/admin/notice_img.png`,
        url2: `${this.$imageHost}/image/admin/shop_logo_default.png`,
        url3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        url4: `${this.$imageHost}/image/admin/icon_delete.png`
      },
      /**
       * form表单的数据
       */
      // form: {
      //   actName: ``,
      //   sendAction: `0`,
      //   startTime: ``
      // },

      templateId: null,
      labels: {
        label6: `${this.$t('couponGive.participants')}`
      },
      cardList: [

      ],
      cardValue: `${this.$t('couponGive.chooseCard')}`,
      /**
      * 动态获取人数的参数集合
      */
      params: {
        userKey: null,
        onClickNoPay: false,
        onClickGoods: false,
        goodsIdList: [],
        onClickCard: false,
        cardIdsList: [],
        onClickTag: false,
        tagIdList: [],
        onClickUser: false,
        userIdList: [],
        onClickCustomRule: false,
        customRuleInfo: {
          payedDay: ``,
          noPayDay: ``,
          buyTimesMore: ``,
          buyTimesLess: ``,
          moneyAvgMore: ``,
          moneyAvgLess: ``,
          loginStart: ``,
          loginEnd: ``
        },

        actName: ``,
        coupon: ``,
        sendAction: `0`,
        startTime: ``
      },

      /**
       * 表单检验
       */
      rules: {
        actName: [
          { required: true, message: `${this.$t('couponGive.actNameTip')}`, trigger: 'blur' }
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
      isShowChoosingGoodsDialog: false,
      pageLink: ``,
      time: {},

      /**
       * 传给组件chooseSelect的数据
       */

      dialogOff: false,
      whetherShowDialog: false,
      dialogVisible: false,
      memberNum: 0, // 已选择的会员人数
      /**
       * 自定义实体
       */
      customRuleInfoVal: `${this.$t('couponGive.choose')}`,
      loginStart: ``,
      loginEnd: ``,
      customRuleInfoOptions: [
        {
          label: `N ${this.$t('couponGive.haveRecord')}`,
          value: `N ${this.$t('couponGive.haveRecord')}`,
          key: `payedDay`,
          ipt: ``
        },
        {
          label: `N ${this.$t('couponGive.noRecord')}`,
          value: `N ${this.$t('couponGive.noRecord')}`,
          key: `noPayDay`,
          ipt: ``

        },
        {
          label: `${this.$t('couponGive.buyLess')} N`,
          value: `${this.$t('couponGive.buyLess')} N`,
          key: `buyTimesLess`,
          ipt: ``

        },
        {
          label: `${this.$t('couponGive.buyMore')} N`,
          value: `${this.$t('couponGive.buyMore')} N`,
          key: `buyTimesMore`,
          ipt: ``

        },
        {
          label: `${this.$t('couponGive.priceHigher')} N`,
          value: `${this.$t('couponGive.priceHigher')} N`,
          key: `moneyAvgMore`,
          ipt: ``

        },
        {
          label: `${this.$t('couponGive.priceLess')} N`,
          value: `${this.$t('couponGive.priceLess')} N`,
          key: `moneyAvgLess`,
          ipt: ``

        },
        {
          label: `${this.$t('couponGive.timeLoginRecord')}`,
          value: `${this.$t('couponGive.timeLoginRecord')}`,
          key: `time`

        }
      ],
      optionsList: [],
      showTime: false,
      isShowBtn: false,
      arrList: [],
      imgsList: [],
      userNumber: 0,
      tuneUpChooseGoods: false,
      tuneUpSelectLink: false

    }
  },
  mounted () {
    // 初始化国际化语言
    this.langDefault()
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 选择优惠券弹窗
    handleToCallDialog () {
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.couponList
      }
      this.$http.$emit('V-AddCoupon', obj)
      this.showCouponDialog = !this.showCouponDialog
    },
    // 优惠券回调
    handleToCheck (data) {
      console.log('coupon', data)
      let couponKey = []
      data.map((item) => {
        couponKey.push(item.id)
      })
      this.couponData = data
      // 实时校验
      this.$refs['params'].validateField('coupon')

      this.couponId = couponKey.toString()
      console.log('conponId', couponKey.toString())
      console.log('conponData', this.couponData)
    },
    // 删除优惠券图片
    deleteCouponImg (index) {
      this.couponIdTemp = this.couponId.split(',')
      this.couponIdTemp.splice(index, 1)
      this.couponId = this.couponIdTemp.toString()
      this.couponData.splice(index, 1)
    },
    // 选择优惠券弹窗-
    handleToCallDialog1 () {
      this.dialogFlag = 0
      this.couponIdList = this.getCouponIdsArray(this.couponData)
      this.showCouponDialog = !this.showCouponDialog
    },
    getCouponIdsArray (data) {
      let res = []
      data.forEach((item, index) => {
        res.push(item.id)
      })
      return res
    },
    // 发放优惠券
    addAct () {
      console.log('params:', this.params)
      console.log('couponId:', this.couponId)
      console.log('onClickNoPay:', Number(this.params.onClickNoPay))
      console.log('onClickGoods:', Number(this.params.onClickGoods))
      console.log('onClickCard:', Number(this.params.onClickCard))
      console.log('onClickTag:', Number(this.params.onClickTag))
      console.log('onClickUser:', Number(this.params.onClickUser))
      console.log('onClickCustomRule:', Number(this.params.onClickCustomRule))
      let param = {
        'actName': this.params.actName,
        'couponGiveGrantInfoParams': {
          'custom_box': Number(this.params.onClickCustomRule),
          'point_start_time': this.params.customRuleInfo.loginStart,
          'point_end_time': this.params.customRuleInfo.loginEnd,
          'cart_box': Number(this.params.onClickNoPay),
          'card_box': Number(this.params.onClickCard),
          'tag_box': Number(this.params.onClickTag),
          'goods_box': Number(this.params.onClickGoods),
          'goods_ids': this.params.goodsIdList.toString(),
          'member_box': Number(this.params.onClickUser),
          'coupon_ids': this.couponId
        },
        'cardId': this.params.cardIdsList.toString(),
        'tagId': this.params.tagIdList.toString(),
        'user': this.params.userIdList.toString(),
        'havePay': this.params.customRuleInfo.payedDay,
        'noPay': this.params.customRuleInfo.noPayDay,
        'maxCount': this.params.customRuleInfo.buyTimesLess,
        'minCount': this.params.customRuleInfo.buyTimesMore,
        'maxAvePrice': this.params.customRuleInfo.moneyAvgLess,
        'minAvePrice': this.params.customRuleInfo.moneyAvgMore,
        'sendAction': this.params.sendAction,
        'startTime': this.params.startTime
      }
      console.log('param:', param)

      this.$refs['params'].validate((valid) => {
        if (valid) {
          addActivity(param).then(res => {
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
    handleChooseData (data) {
      this.$message.success({
        message: `${this.$t('couponGive.hasBeenChosen')}${data.length}${this.$t('couponGive.data')}`
      })
      this.checkedData = data
    },
    // 关闭会员弹窗
    closeDialog () {
      this.dialogOff = false
      this.whetherShowDialog = false
      this.dialogVisible = false
    },
    // 添加会员
    handleAddMember () {
      if (this.params.onClickUser === false) {
        return
      }
      this.dialogOff = true
    },
    // getUserIdList
    getUserIdList (val) {
      console.log(val)
      this.params.userIdList = this.formatUserIdList(val)
      this.memberNum = val.length // 把选中的数组长度赋值给已选会员数
      // 当添加会员后 发送获取人数接口
      this.fetchUserList(this.params)
    },
    formatUserIdList (userIdList) {
      let arr = []
      userIdList.forEach(item => {
        arr.push(item.userId
        )
      })
      return arr
    },
    // getContent
    getContent (res) {
      this.formData.content = res.content
      this.templateId = res.id
    },
    // 初始化商品弹窗
    showChoosingGoods () {
      if (this.params.onClickGoods === false) {
        return
      }
      console.log('初始化商品弹窗', this.params.goodsIdList.toString())
      let goodsIdArr = []
      if (this.params.goodsIdList.toString() !== null) {
        goodsIdArr = this.params.goodsIdList
      }
      this.transmitEditGoodsId(goodsIdArr)
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 接收商品弹窗放回数据
    choosingGoodsResult (row) {
      console.log('接收商品弹窗返回数据', row)
      this.imgsList = []
      row.forEach((item, index) => {
        console.log('接收商品弹窗返回id', item)
        console.log('这轮之前', this.imgsList)
        console.log('*********************')
        this.imgsList.push(item)
        console.log('数组中加入', item.goodsId)
        console.log('这轮过后', this.imgsList)
      })
      this.params.goodsIdList = []
      this.imgsList.forEach((item, index) => {
        this.params.goodsIdList.push(item.goodsId)
      })
      console.log('点击确定后的数据：', this.imgsList)
      console.log('点击确定后的id：', this.params.goodsIdList)
      this.goodsNum = row.length
      // 发送获取人数
      this.fetchUserList(this.params)
    },
    // 选择商品
    handleChooseGoods () {
      if (this.params.onClickGoods === false) {
        return
      }
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    getRes (ids, urls) {
      if (ids.length > 3) {
        this.$message.warning('最多选择3个商品')
      } else {
        this.params.goodsIdList = ids
        this.imgsList = urls
        console.log('数据库存储：', this.params.goodsIdList.toString())
        // 发送获取人数
        this.fetchUserList(this.params)
      }
    },
    // 删除图片
    handleDelImg (id) {
      if (this.params.onClickGoods === false) {
        return
      }
      this.imgsList = this.imgsList.filter((item) => item.goodsId !== id)
      console.log('现在的数据：', this.imgsList)
      this.params.goodsIdList = []
      this.imgsList.forEach((item, index) => {
        this.params.goodsIdList.push(item.goodsId)
      })
      console.log('现在的id：', this.params.goodsIdList)
      // this.params.goodsIdList = this.goodsIdList.filter((item) => item !== id)
      this.fetchUserList(this.params)
    },
    // 获取选中的path
    getPath (res) {
      console.log(res)
      this.pageLink = res
    },
    // 获取持有属于的值
    getChooseSelectVal (val) {
      const { onClickCard, cardIdsList, onClickTag, tagIdList } = val
      this.params.onClickCard = onClickCard
      this.params.cardIdsList = cardIdsList
      this.params.onClickTag = onClickTag
      this.params.tagIdList = tagIdList
      // 请选择会员卡
      switch (onClickCard) {
        case true:
          console.log(this.params)
          this.fetchUserList(this.params)
          break
        case false:
          this.fetchUserList(this.params)
          break
        default:
          break
      }
      // 请选择会员标签
      switch (onClickTag) {
        case true:
          this.fetchUserList(this.params)
          break
        case false:
          this.fetchUserList(this.params)
          break
        default:
          break
      }
    },
    handleGetUser () {
      this.dialogVisible = true
    },
    // 获取发送人群数量
    fetchUserList (params) {
      getUserNumberApi(params).then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(res) // 返回发送人群的数量
          const { userKey, userNumber } = content
          console.log(`key+num${userKey}, ${userNumber}`)
          this.userNumber = userNumber
          this.params.userKey = userKey
        }
      }).catch(err => console.log(err))
    },
    // 加购人群发生变化的时候
    handleOnClickNoPayChange (val) {
      // this.params.onClickNoPay = !this.params.onClickNoPay
      // 获取发送人群的数量
      console.log(this.params)
      this.fetchUserList(this.params)
    },
    // 指定购买商品人群发生变化的时候
    handleOnClickGoodsChange (val) {
      // this.params.onClickGoods = !this.params.onClickGoods
      console.log(this.params)
      this.fetchUserList(this.params)
    },
    // 选择指定的会员状态发生变化的时候
    handleOnClickUserChange (val) {
      // this.params.onClickUser = !this.params.onClickUser
      console.log(this.params)
      this.fetchUserList(this.params)
    },
    // 当自定义发生变化的时候
    handleOnClickCustomRuleChange (val) {
      // this.params.onClickCustomRule = !this.params.onClickCustomRule
      this.fetchUserList(this.params)
    },
    loginStartAndLoginEnd (val) {
      const { startTime, endTime } = val
      this.loginStart = startTime
      this.loginEnd = endTime
      this.params.customRuleInfo.loginStart = this.loginStart
      this.params.customRuleInfo.loginEnd = this.loginEnd
      this.fetchUserList(this.params)
    },
    //
    customRuleInfoValChange (val) {
      console.log(val)
      if (val === `${this.$t('couponGive.timeLoginRecord')}`) {
        this.showTime = true
        this.customRuleInfoOptions = delObj({ arr: this.customRuleInfoOptions, val })
        this.customRuleInfoVal = `${this.$t('couponGive.choose')}`
      } else {
        if (val !== `${this.$t('couponGive.choose')}` && val !== `${this.$t('couponGive.timeLoginRecord')}`) {
          const res = this.customRuleInfoOptions.find(item => item.value === val)
          this.optionsList.push(res)
          this.customRuleInfoOptions = this.customRuleInfoOptions.filter((item) => item.value !== res.value)
          this.customRuleInfoVal = `${this.$t('couponGive.choose')}`
        }
      }
    },
    // 输入框发生变化的时候
    handleIpt (item) {
      console.log(item)
      const { key, ipt } = item
      for (let a in this.params.customRuleInfo) {
        if (a === key) {
          switch (a) {
            case `payedDay`: this.params.customRuleInfo.payedDay = ipt
              break
            case `noPayDay`: this.params.customRuleInfo.noPayDay = ipt
              break
            case `buyTimesMore`: this.params.customRuleInfo.buyTimesMore = ipt
              break
            case `buyTimesLess`: this.params.customRuleInfo.buyTimesLess = ipt
              break
            case `moneyAvgMore`: this.params.customRuleInfo.moneyAvgMore = ipt
              break
            case `moneyAvgLess`: this.params.customRuleInfo.moneyAvgLess = ipt
              break
            default:
              break
          }
        }
      }
      console.log(this.params.customRuleInfo)
      this.fetchUserList(this.params)
    },
    // 删除自定义
    handleDelCustomize (val) {
      // 自定义勾选状态为false 删除不可点
      if (this.params.onClickCustomRule === false) {
        return
      }
      if (val === 6) {
        this.showTime = false
        this.customRuleInfoOptions.push({
          label: `${this.$t('couponGive.timeLoginRecord')}`,
          value: `${this.$t('couponGive.timeLoginRecord')}`
        })
        this.params.customRuleInfo.loginEnd = ``
        this.params.loginStart.loginStart = ``
        this.fetchUserList(this.params)
      } else {
        console.log(val)
        for (let a in this.params.customRuleInfo) {
          if (a === val.key) {
            switch (a) {
              case `payedDay`: this.params.customRuleInfo.payedDay = ``
                break
              case `noPayDay`: this.params.customRuleInfo.noPayDay = ``
                break
              case `buyTimesMore`: this.params.customRuleInfo.buyTimesMore = ``
                break
              case `buyTimesLess`: this.params.customRuleInfo.buyTimesLess = ``
                break
              case `moneyAvgMore`: this.params.customRuleInfo.moneyAvgMore = ``
                break
              case `moneyAvgLess`: this.params.customRuleInfo.moneyAvgLess = ``
                break
              default:
                break
            }
          }
        }
        console.log(this.params.customRuleInfo)
        this.optionsList = this.optionsList.filter((item) => item.key !== val.key)
        this.customRuleInfoOptions.unshift(val)
        this.fetchUserList(this.params)
      }
    },
    // handleShowBtn
    handleShowBtn () {
      if (this.formData.content !== ``) {
        this.isShowBtn = true
      } else {
        this.isShowBtn = false
      }
    },
    sendActionChange (e) {
      this.$refs['params'].validateField('startTime')
    }
  },
  filters: {
    filterA: function (val) {
      // console.log(val.search('N'))
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
.deleteIcon {
  width: 17px;
  height: 17px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 17px;
  text-align: center;
  position: relative;
  top: -41px;
  right: -95px;
  cursor: pointer;
  opacity: 0.8;
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
        li {
          margin: 5px 0;
          span {
            margin: 0 5px;
          }
          .img_span {
            position: relative;
            .img {
              position: absolute;
              left: 400px;
              top: -50px;
              cursor: pointer;
            }
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
    top: -118px;
    left: 45px;
    cursor: pointer;
    opacity: 0.8;
    color: #fff;
    background: #ccc;
    border: 1px solid #ccc;
    border-radius: 50%;
    text-align: center;
  }
}
.addInfo {
  display: inline-block;
  position: relative;
  width: 100px;
  height: 101px;
  margin-bottom: 10px;
  background: #fff;
  border: 1px solid #e4e4e4;
  cursor: pointer;
  text-align: center;
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
      margin-top: 10px;
      color: #f60;
      :nth-of-type(2) {
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
      background-image: url("http://mpdevimg2.weipubao.cn/image/admin/coupon_border.png");
      background-repeat: repeat-x;
    }
  }
}
</style>
