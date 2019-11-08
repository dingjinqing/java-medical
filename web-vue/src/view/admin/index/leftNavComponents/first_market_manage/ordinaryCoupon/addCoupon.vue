<template>
  <div class="content">
    <div class="main">
      <!-- <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部优惠券"
          name="first"
        ></el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        ></el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        ></el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        ></el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        ></el-tab-pane>
      </el-tabs> -->
      <div class="add_coupon_content">
        <div class="coupon_content">
          <div class="fl content_left">
            <div class="fl_title">
              <div>优惠券</div>
            </div>
            <div class="info">
              <div class="info_top">
                <div
                  class="vip_exclusive"
                  style="display:none;"
                >会员专享</div>
                <div class="coupon_name">{{param.actName ? param.actName : '优惠券名称'}}</div>
                <div class="coupon_vou">
                  <span v-if="param.preferentialType === 2">￥{{param.randomNum1 && param.randomNum2 ? param.randomNum1 + '-' + param.randomNum2 : '0.00 - 0.00'}}</span>
                  <span v-if="param.preferentialType === 0">￥{{param.denomination ? param.denomination : '0.00'}}</span>
                  <span v-if="param.preferentialType === 1">{{param.denomination2?param.denomination2:'0'}}折</span>
                </div>
                <div class="coupon_dis"></div>
              </div>
            </div>
            <div class="info_mid">
              <div class="clearfix">
                <span class="sub_title">有效日期</span>
                <span class="date">
                  <span v-if="param.validityType === 0 && param.couponDate === ''">xxxx-xx-xx xx:xx:xx-xxxx-xx-xx xx:xx:xx</span>
                  <span v-if="param.validityType === 0 && param.couponDate !== ''">{{param.couponDate[0]}} - {{param.couponDate[1]}}</span>
                  <span v-if="param.validityType === 1">领券日开始{{param.validity}}<span v-if="param.validity === ''">X</span>天{{param.validityHour}}<span v-if="param.validityHour === ''">X</span>小时{{param.validityMinute}}<span v-if="param.validityMinute === ''">X</span>分钟内有效</span>
                  <!-- <span v-if="param.validityType === '0'">{{coupon_date_datetimerange}}</span> -->
                  <!-- <span v-else>{{coupon_date_info}}</span> -->
                </span>
              </div>
              <div>
                <span class="sub_title">使用限制</span>
                <span
                  class="all"
                  v-if="param.useConsumeRestrict===0"
                >无限制</span>
                <span v-else>订单满{{param.leastConsume?param.leastConsume:'0'}}<span v-if="param.leastConsume === ''">X</span>元可用</span>
                <span
                  class="part"
                  v-if="param.suitGoods === 1"
                >部分商品可用</span>
              </div>
            </div>
            <div class="info_bot">
              <div
                class="code"
                v-if="param.validationCode != ''"
              >请输入领取码</div>
              <div
                class="use"
                v-if="this.param.type==0"
              >立即使用</div>
              <div
                class="use"
                v-if="this.param.type==1"
              >立即分享</div>
              <div>
                <span class="sub_title">使用说明</span>
                <div
                  class="instruction"
                  v-html="crlfFormat ? crlfFormat : '暂无使用说明'"
                ></div>
              </div>
            </div>
          </div>
          <div class="content_right">
            <div class="coupon_info">
              <div class="coupon_info_title">优惠券类型</div>
              <ul>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>优惠券类型：
                  </div>
                  <div>
                    <el-radio
                      v-model="param.type"
                      :label=0
                    >普通优惠券</el-radio>
                    <el-radio
                      v-model="param.type"
                      :label=1
                    >分裂优惠券 <el-tooltip
                        effect="dark"
                        content="买家领取到优惠券之后分享给好友, 自己和好友都可以获得一张优惠券"
                        placement="top"
                      >
                        <i class="el-icon-warning-outline"></i>
                      </el-tooltip>
                    </el-radio>
                  </div>
                </li>
              </ul>
            </div>
            <div class="coupon_info">
              <div
                class="coupon_info_title"
                v-if="param.type===0"
              >优惠券基础信息</div>
              <div
                class="coupon_info_title"
                v-if="param.type===1"
              >分裂优惠券信息 <span style="color: #999;margin-left: 15px;">用户将优惠券分享到微信，有好友领取后自己可获得一张优惠券</span></div>
              <ul>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>优惠券名称：
                  </div>
                  <div class="ft">
                    <el-input
                      prop="actName"
                      size="small"
                      class="coupon_name_input"
                      placeholder="最多输入10个字"
                      v-model="param.actName"
                      maxlength="10"
                    ></el-input>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>有效期：
                  </div>
                  <div>
                    <p>
                      <el-radio
                        v-model="param.validityType"
                        :label='0'
                      >固定日期</el-radio>
                    </p>
                    <p style="margin:15px 0;">
                      <el-date-picker
                        :disabled="param.validityType===0?false:true"
                        v-model="param.couponDate"
                        type="datetimerange"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        format="yyyy-MM-dd HH:mm:ss"
                        range-separator="-"
                        start-placeholder="生效时间"
                        end-placeholder="过期时间"
                        size="small"
                      >
                      </el-date-picker>
                    </p>
                    <p>
                      <el-radio
                        v-model="param.validityType"
                        :label='1'
                        style="margin-right: 25px;"
                      >领券开始</el-radio>
                      <span>
                        <el-input
                          :disabled="param.validityType===1?false:true"
                          @blur="checkNum"
                          v-model="param.validity"
                          size="small"
                          class="small_input"
                        ></el-input> 天
                        <el-input
                          :disabled="param.validityType===1?false:true"
                          @blur="checkNum"
                          v-model="param.validityHour"
                          size="small"
                          class="small_input"
                        ></el-input> 小时
                        <el-input
                          :disabled="param.validityType===1?false:true"
                          @blur="checkNum"
                          v-model="param.validityMinute"
                          size="small"
                          class="small_input"
                        ></el-input> 分钟内有效
                      </span>
                    </p>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>初始库存：
                  </div>
                  <div>
                    <div>
                      <el-radio
                        v-model="param.surplus"
                        :label='1'
                      >库存数量</el-radio>
                      <span>
                        <el-input
                          :disabled="param.surplus===1?false:true"
                          @blur="checkNum"
                          v-model.number="param.totalAmount"
                          size="small"
                          class="small_input"
                        ></el-input>张
                        <span style="color: #999;">优惠券可发放的总数量</span>
                      </span>
                    </div>
                    <div>
                      <el-radio
                        v-model="param.surplus"
                        :label='0'
                      >不限制库存</el-radio>
                    </div>

                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>优惠类型：
                  </div>
                  <div class="to_choose">
                    <p v-if="param.type==1">
                      <el-radio
                        v-model="param.preferentialType"
                        :label='2'
                      >随机金额</el-radio>
                      <el-input
                        :disabled="param.preferentialType==2?false:true"
                        v-model="param.randomNum1"
                        @blur="checkNum"
                        size="small"
                        class="small_input"
                      ></el-input>
                      至
                      <el-input
                        :disabled="param.preferentialType==2?false:true"
                        v-model="param.randomNum2"
                        @blur="checkNum"
                        size="small"
                        class="small_input"
                      ></el-input>
                      <span style="color: #999;display: block;">优惠金额将在指定范围内随机</span>
                    </p>

                    <p>
                      <el-radio
                        v-model="param.preferentialType"
                        :label='0'
                      >指定金额</el-radio>
                      <span>
                        面值：
                        <el-input
                          :disabled="param.preferentialType==0?false:true"
                          v-model.number="param.denomination"
                          @blur="checkNum"
                          size="small"
                          class="small_input"
                        ></el-input> 元
                      </span>
                    </p>

                    <p>
                      <el-radio
                        v-model="param.preferentialType"
                        :label='1'
                      >折扣</el-radio>
                      <el-input
                        :disabled="param.preferentialType==1?false:true"
                        v-model.number="param.denomination2"
                        @blur="checkDiscount"
                        size="small"
                        class="small_input"
                      ></el-input>折
                    </p>

                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>是否需要兑换：
                  </div>
                  <div>
                    <el-radio
                      v-model="param.isRandom"
                      :label='0'
                    >不需要</el-radio>
                    <el-radio
                      v-model="param.isRandom"
                      :label='1'
                    >需要兑换</el-radio>
                    <p v-if="param.isRandom== 1">
                      <el-input
                        v-model.number="param.userScore"
                        @blur="checkNum"
                        class="small_input"
                        size="small"
                      ></el-input>
                      积分兑换
                    </p>
                  </div>
                </li>
              </ul>
            </div>
            <div class="coupon_info">
              <div class="coupon_info_title">基本规则</div>
              <ul>
                <li
                  class="content_right_li clearfix"
                  v-if="param.type===0"
                >
                  <div class="content_left_title">
                    <em>*</em>每人限领：
                  </div>
                  <div class="ft">
                    <el-select
                      v-model="param.receivePerPerson"
                      size="small"
                    >
                      <el-option
                        v-for="(item, index) in get_limit"
                        :key="index"
                        :value="index"
                        :label="item"
                      ></el-option>
                    </el-select>
                  </div>
                </li>
                <li
                  class="content_right_li clearfix"
                  v-if="param.type===0"
                >
                  <div class="content_left_title">
                    会员专享：
                  </div>
                  <div>
                    <p>
                      <el-checkbox
                        v-model="param.isExclusive"
                        label="用户持有会员卡才可以参与活动"
                      ></el-checkbox>
                    </p>
                    <div v-if="param.isExclusive">
                      <el-select
                        v-model="param.cardId"
                        placeholder="请选择会员卡"
                        multiple
                        size="small"
                      >
                        <el-option
                          v-for="item in cardList"
                          :key="item.id"
                          :label="item.cardName"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                      <span class="card_links">
                        <a>刷新</a><span> | </span>
                        <a>新建会员卡</a><span> | </span>
                        <a>管理会员卡</a>
                      </span>
                    </div>
                  </div>
                </li>
                <li
                  class="content_right_li clearfix"
                  v-if="param.type===0"
                >
                  <div class="content_left_title">
                    领取码：
                  </div>
                  <div>
                    <el-input
                      v-model="param.validationCode"
                      @blur="checkCode"
                      size="small"
                      class="small_input"
                    ></el-input>
                  </div>
                </li>
                <li
                  class="content_right_li clearfix"
                  v-if="param.type===1"
                >
                  <div class="content_left_title">
                    <em>*</em>领券人数：
                  </div>
                  <div>
                    <el-radio
                      v-model="param.couponNum"
                      :label="0"
                    >不限制</el-radio>
                    <el-radio
                      v-model="param.couponNum"
                      :label="1"
                    >分享后</el-radio>
                    <el-input
                      :disabled="param.couponNum==1?false:true"
                      v-model="param.humanNum"
                      size="small"
                      class="small_input"
                      style="margin-left: -30px;"
                    ></el-input>名(包括送券人)用户可以领取此优惠券，最少2人
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    是否隐藏：
                  </div>
                  <div style="display:flex">
                    <div>
                      <el-radio
                        v-model="param.isHide"
                        :label="0"
                      >否</el-radio>
                      <el-radio
                        v-model="param.isHide"
                        :label="1"
                      >是</el-radio>
                    </div>
                    <span style="flex:1;padding-left:15px;color:red;">
                      隐藏则不显示在前端商品详情页。否则显示到前端商品详情页可以供用户领取。
                    </span>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>使用门槛：
                  </div>
                  <div>
                    <p>
                      <el-radio
                        v-model="param.useConsumeRestrict"
                        :label='0'
                      >不限制</el-radio>
                    </p>
                    <p>
                      <el-radio
                        v-model="param.useConsumeRestrict"
                        :label='1'
                      >满<el-input
                          :disabled="param.useConsumeRestrict === 0"
                          size="small"
                          v-model.number="param.leastConsume"
                          @blur="checkNum"
                          class="small_input"
                        ></el-input>
                        元可用
                      </el-radio>
                    </p>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>可使用商品：
                  </div>
                  <div>
                    <p>
                      <el-radio
                        v-model="param.suitGoods"
                        :label='0'
                      >全部商品</el-radio>
                    </p>
                    <p>
                      <el-radio
                        v-model="param.suitGoods"
                        :label='1'
                      >指定商品</el-radio>
                    </p>
                    <div v-if="param.suitGoods === 1">
                      <div
                        class="noneBlockList"
                        v-for="(item,index) in noneBlockDiscArr"
                        :key="index"
                        @click="hanldeToAddGoodS(index)"
                      >
                        <div class="noneBlockLeft">
                          <img :src="$imageHost+'/image/admin/icon_jia.png'">
                          {{item.name}}
                        </div>
                        <div v-if="index === 0">已选{{ goodsInfo.length > 0 ? goodsInfo.length : 0 }}件商品</div>
                        <div v-if="index === 1">已选{{ busClass.length > 0 ? busClass.length : 0 }}个商家</div>
                        <div v-if="index === 2">已选{{ platClass.length > 0 ? platClass.length : 0 }}个平台</div>
                        <!-- <div
                          v-if="item.num"
                          class="noneBlockRight"
                        >已选择{{index === 0 ? '商品':'分类'}}：{{item.num}}个{{index === 0 ? '商品':'分类'}}</div> -->
                      </div>
                    </div>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    使用说明：
                  </div>
                  <div>
                    <el-input
                      type="textarea"
                      :rows="5"
                      v-model="param.useExplain"
                      placeholder="请输入使用说明"
                      resize="none"
                    ></el-input>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        @click="saveCoupon"
      >保存</el-button>
    </div>
    <!--选择商品弹窗-->
    <!-- <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="goodsIdsList"
    /> -->
    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
      :chooseGoodsBack="goodsInfo"
    />
    <!-- 选择 1商家分类;2平台分类弹窗 -->
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpBusClassDialog"
      :classFlag="classFlag"
      @BusClassTrueDetailData="busClassDialogResult"
      @backDataArr="commInfo"
    />
  </div>
</template>
<script>
import { saveCoupon, updateCoupon, updateSaveCoupon } from '@/api/admin/marketManage/couponList.js'
import { allCardApi } from '@/api/admin/marketManage/messagePush'
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog')
  },
  data () {
    return {
      editType: false, // 编辑保存状态
      couponInfo: {},
      param: {
        type: 0, // 优惠券类型
        actCode: '',
        actName: '', // 优惠券名
        // aliasCode: '', // 唯一活动代码
        preferentialType: 0,
        useConsumeRestrict: 0, // 使用限制
        isRandom: 0, // 是否需要积分
        receivePerPerson: 0,
        // cardId: [],
        validityType: 0, // 有效期
        couponDate: '', // 总时间
        startTime: '', // 生效时间
        endTime: '', // 到期时间
        validity: '',
        validityHour: '',
        validityMinute: '',
        surplus: 0, // 初始库存
        totalAmount: null, // num发行量
        // totalAmount: null, // 发放总量
        validationCode: '',
        recommendGoodsId: '', // 指定商品
        recommendCatId: '', // 指定平台分类
        recommendSortId: '', // 指定商家分类
        randomNum1: null, // 随机金额1
        randomNum2: null, // 随机金额2
        couponNum: 0, // 领券人数
        humanNum: null, // 人数
        isHide: 0,
        suitGoods: 0, // 适用商品
        useExplain: '',
        denomination: null, // num面额
        denomination2: null,
        leastConsume: null,
        userScore: null, // num积分数
        AtreeType: null,
        isExclusive: false
      },
      couponId: '',
      AtreeType: null,
      activeName: 'second',
      currentPage: 1,
      get_limit: ['不限制', 1, 2, 3, 4, 5, 8, 10, 20],

      cardList: [], // 会员卡列表

      noneBlockDiscArr: [
        {
          name: '添加商品',
          value: '1',
          num: ''
        },
        {
          name: '添加商品分类',
          value: '2',
          num: ''
        },
        {
          name: '添加平台分类',
          value: '3',
          num: ''
        }
      ],
      tuneUpChooseGoods: false, // 商品弹窗
      tuneUpBusClassDialog: false, // 商家/平台弹窗
      classFlag: 0, // 商家/平台类型
      // 弹窗结果区分标识 1商家分类;2平台分类
      flag: 0,
      // 商品弹窗回调数据
      goodsInfo: [],
      goodsInfoRow: [],
      // 商家分类弹窗回调数据
      busClass: [],
      busClassRow: [],
      // 平台分类弹窗回调数据
      platClass: [],
      platClassRow: [],
      // 平台分类/商家分类共享变量
      commInfo: []
    }
  },
  mounted () {
    this.couponId = this.$route.query.id

    this.dataDefalut()
    if (this.couponId) {
      this.getOneInfo()
      this.editType = true
    }
  },
  methods: {
    handleClick () {

    },
    dataDefalut () {
      this.$http.$on('result', res => {
        this.param.recommendGoodsId = res.join()
        this.noneBlockDiscArr[0].num = res.length
      })
      this.$http.$on('BusClassTrueArr', res => {
        if (this.AtreeType === 1) {
          this.param.recommendSortId = res.join()
          this.noneBlockDiscArr[1].num = res.length
        } else {
          this.param.recommendCatId = res.join()
          this.noneBlockDiscArr[2].num = res.length
        }
      })
      // 会员卡数据
      allCardApi().then((res) => {
        if (res.error === 0) {
          this.cardList = res.content
        }
      })
    },
    // 编辑回显
    getOneInfo () {
      updateCoupon(this.couponId).then(res => {
        if (res.error === 0) {
          this.param = res.content[0]
          // 有效期
          this.param.couponDate = [this.param.startTime, this.param.endTime]
          // 面额/折
          if (this.param.denomination < 10) {
            this.param.preferentialType = 1
            this.param.denomination2 = this.param.denomination
          }
          // 发放的总数量
          // if (this.param.totalAmount === 0) {
          //   this.param.surplus = 0
          // }
          this.param.surplus = this.param.totalAmount
          // 使用门槛
          if (this.param.leastConsume === 0) {
            this.param.useConsumeRestrict = 0
          }
        }
      })
    },
    // 点击指定商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      console.log(index)
      switch (index) {
        case 0:
          this.tuneUpChooseGoods = !this.tuneUpChooseGoods
          break
        case 1:
          this.tuneUpBusClassDialog = !this.tuneUpBusClassDialog
          this.classFlag = 1
          this.flag = 1
          this.commInfo = this.busClass
          // this.AtreeType = 1
          // console.log(this.param.recommendSortId)
          // this.$http.$emit('addingBusClassDialog', this.param.recommendSortId ? this.param.recommendSortId.split(',') : null)
          break
        case 2:
          this.tuneUpBusClassDialog = !this.tuneUpBusClassDialog
          this.classFlag = 2
          this.flag = 2
          this.commInfo = this.platClass
          // this.AtreeType = 2
          // console.log(this.param.recommendCatId)
          // this.$http.$emit('addingBusClassDialog', this.param.recommendCatId ? this.param.recommendCatId.split(',') : null, this.AtreeType)
          break
      }
    },
    // 校验文本框
    checkNum (e) {
      // 非0正整数
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!re.test(e.target.value)) {
        this.$message.warning({ message: '请输入0或者正整数！' })
      }
    },
    // 校验打折
    checkDiscount (e) {
      // var re = /^((0\.[1-9]{1})|(([1-9]{1})(\.\d{1})?))$/
      // if (!re.test(e.target.value)) {
      //   this.$message.warning({ message: '请输入正确的数字！' })
      // }
    },
    // 校验领取码
    checkCode (e) {
      // var re = /^(0|\+?[1-9][0-9]*)$/
      // if (!re.test(e.target.value)) {
      //   this.$message.warning({ message: '请输入正确的领取码！' })
      // }
    },
    // 保存优惠券
    saveCoupon () {
      // 面额/折
      if (this.param.preferentialType === 1) {
        this.param.denomination = this.param.denomination2
      }
      // 发放的总数量
      // if (this.param.surplus === 0) {
      //   this.param.totalAmount = 0
      // }
      this.param.surplus = this.param.totalAmount
      // 使用门槛
      if (this.param.useConsumeRestrict === 0) {
        this.param.leastConsume = 0
      }
      this.param.recommendGoodsId = this.goodsInfo
      this.param.recommendCatId = this.busClass
      this.param.recommendSortId = this.platClass
      if (this.editType === false) {
        // 添加保存
        // 面额/折
        if (this.param.preferentialType === 1) {
          this.param.actCode = 'discount'
          this.param.denomination = this.param.denomination2
        } else {
          this.param.actCode = 'voucher'
          this.param.denomination = this.param.denomination
        }
        // 发放的总数量
        if (this.param.surplus === 0) {
          this.param.totalAmount = 0
        }
        // 使用门槛
        if (this.param.useConsumeRestrict === 0) {
          this.param.leastConsume = 0
        }
        this.param.recommendGoodsId = this.goodsInfo.toString()
        this.param.recommendCatId = this.busClass.toString()
        this.param.recommendSortId = this.platClass.toString()
        this.param.startTime = this.param.couponDate[0]
        this.param.endTime = this.param.couponDate[1]
        if (this.param.cardId !== undefined && this.param.cardId.length > 0) {
          this.param.cardId = this.param.cardId.toString()
        }
        saveCoupon(this.param).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '添加成功' })
            this.$router.push({ 'name': 'ordinary_coupon' })
          }
        })
      } else {
        // 编辑保存
        // alert('编辑保存')
        this.param.recommendGoodsId = this.goodsInfo.toString()
        this.param.recommendCatId = this.busClass.toString()
        this.param.recommendSortId = this.platClass.toString()
        this.param.startTime = this.param.couponDate[0]
        this.param.endTime = this.param.couponDate[1]
        if (this.param.cardId !== undefined && this.param.cardId.length > 0) {
          this.param.cardId = this.param.cardId.toString()
        }
        console.log(this.param)
        updateSaveCoupon(this.param).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '修改成功' })
            this.$router.push({ 'name': 'ordinary_coupon' })
          }
        })
      }
    },
    // 选择商品弹窗回调显示
    choosingGoodsResult (row) {
      console.log('选择商品弹窗回调显示:', row)
      this.goodsInfoRow = row
      this.goodsInfo = []
      this.goodsInfoRow.map((item, index) => {
        this.goodsInfo.push(item.goodsId)
      })
    },
    // 选择商家分类/平台分类弹窗回调显示
    busClassDialogResult (row) {
      console.log('选择商家分类/平台分类弹窗回调显示:', row)
      if (this.flag === 1) {
        // 商家分类
        this.busClassRow = row
        this.busClass = []
        this.busClassRow.map((item, index) => {
          this.busClass.push(item.sortId)
        })
      } else {
        // 平台分类
        this.platClassRow = row
        this.platClass = []
        this.platClassRow.map((item, index) => {
          this.platClass.push(item.catId)
        })
      }
    }
  },

  filters: {
  },
  computed: {
    coupon_date_info () {
      if (this.param.validity || this.param.validityHour || this.param.validityMinute) {
        return `领券日开始${this.param.validity ? this.param.validity + '天' : ''}${this.param.validityHour ? this.param.validityHour + '时' : ''}${this.param.validityMinute ? this.param.validityMinute + '分' : ''}内有效`
      } else {
        return `领券日开始X天X时X分内有效`
      }
    },
    coupon_date_datetimerange () {
      if (this.param.couponDate) {
        let dateStr = ''
        this.param.couponDate.map(item => {
          let date = new Date(item)
          let year = date.getFullYear()
          let month = (date.getMonth() + 1).toString().padStart(2, '0')
          let day = date.getDate().toString().padStart(2, '0')
          let hour = date.getHours().toString().padStart(2, '0')
          let minute = date.getMinutes().toString().padStart(2, '0')
          let second = date.getSeconds().toString().padStart(2, '0')
          dateStr += `${year}-${month}-${day} ${hour}:${minute}:${second} `
        })
        return dateStr
      } else {
        return `xxxx-xx-xx xx:xx:xx xxxx-xx-xx xx:xx:xx`
      }
    },
    crlfFormat () {
      return this.param.useExplain.replace(/\n/g, '<br />')
    }
    // 商品回调函数
    // goodsIdsList () {
    //   if (!this.param.recommendGoodsId) return null
    //   return this.param.recommendGoodsId.split(',').map(item => {
    //     return Number(item)
    //   })
    // }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    margin-bottom: 52px;
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
}
.main_coupon {
  padding: 0 20px 20px;
  background: #fff;
  width: 100%;
}
.coupon_content {
  display: flex;
  width: 950px;
  margin: 0 auto;
}
.add_coupon_content .coupon_content .content_left {
  width: 323px;
  height: 510px;
  border: 1px solid #ccc;
  background: #eee;
}
.fl_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  color: white;
  text-align: center;
}
.fl_title div {
  padding-top: 25px;
  font-size: 15px;
}
.info {
  width: 95%;
  margin: 10px auto 0px auto;
  background: white;
}
.info_mid,
.info_bot {
  background: white;
  margin: 0px 10px;
  color: #666;
  font-size: 13px;
}
.info_bot {
  padding: 10px;
}
.vip_exclusive {
  position: absolute;
  top: 10px;
  left: 10px;
  width: 70px;
  line-height: 5px;
  color: #fff;
  border-radius: 15px;
  background-color: rgba(0, 0, 0, 0.1);
  font-size: 12px;
}
.info_top {
  padding: 10px;
  color: white;
  background: url(../../../../../../assets/adminImg/coupon_bg.png) left top
    no-repeat;
  height: 100px;
  background-size: 100% 100%;
  text-align: center;
  font-size: 15px;
}
.info_top div {
  padding: 10px;
}
.info_mid,
.info_bot {
  background: white;
  margin: 0px 10px;
  color: #666;
  font-size: 13px;
}
.info_mid div {
  border-bottom: 1px solid #ddd;
  padding: 15px;
}
.sub_title {
  color: #aaa;
}
.date {
  width: 75%;
  float: right;
}
.small_input {
  width: 70px;
}
.info_bot {
  padding: 10px;
}
.code {
  width: 60%;
  margin: 0 auto;
  text-align: center;
  background: #f6f6f6;
  border-radius: 5px;
  padding: 6px;
  color: #aaa;
}
.use {
  border: 1px solid #ff6666;
  border-radius: 5px;
  padding: 5px;
  width: 30%;
  text-align: center;
  margin: 15px auto;
  color: #ff6666;
}
.instruction {
  background-color: #f6f6f6;
  min-height: 50px;
  margin: 10px auto;
  border-radius: 5px;
  padding: 5px;
  font-size: 12px;
}
.add_coupon_content .coupon_content .content_right {
  flex: 1;
  margin-left: 15px;
}
.coupon_info_title {
  border-bottom: 1px solid #e5e5e5;
  line-height: 40px;
}
.coupon_info {
  border: 1px solid #e5e5e5;
  background: #f8f8f8;
  padding: 0 12px 22px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
  margin-bottom: 10px;
  > ul {
    display: flex;
    flex-direction: column;
    > li {
      display: flex;
      line-height: 32px;
      > .content_left_title {
        width: 130px;
        text-align: right;
        line-height: 32px;
      }
    }
  }
}
.card_links > a {
  color: #409eff;
}
.content_right_li {
  padding: 10px 0 0 0;
}
.content_right_li > div:nth-child(2) {
  flex: 1;
}
.content_right_li em {
  color: #f66;
}
.content_right_li .fl:first-child {
  width: 110px;
  text-align: right;
}
.ft {
  width: 110px;
  float: left;
  line-height: 30px;
  margin-right: 15px;
}
.coupon_name_input {
  width: 160px;
}
.noneBlockList {
  margin-bottom: 10px;
  display: flex;
  .noneBlockLeft {
    line-height: 30px;
    height: 30px;
    width: 120px;
    text-align: left;
    color: #5a8bff;
    border: 1px solid #ccc;
    background: #fff;
    cursor: pointer;
    padding-left: 5px;
    margin-right: 20px;
  }
  .noneBlockRight {
    color: #5a8bff;
    cursor: pointer;
    height: 30px;
    line-height: 30px;
  }
}
</style>
