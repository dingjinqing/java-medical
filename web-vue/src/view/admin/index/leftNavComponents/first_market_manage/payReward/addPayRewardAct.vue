<template>
  <div class="addPayRewardAct">
    <div class="addPayRewardAct_main">

      <!-- 左侧内容区域 -->
      <div class="leftContent">
        <!-- 标题图片部分 -->
        <div class="titles">
          <img
            :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'"
            alt=""
          >
        </div>
        <div class="carousel">
          <el-carousel
            trigger="click"
            height="573px"
            arrow="never"
          >
            <!-- :autoplay="false" -->
            <el-carousel-item
              v-for="(item,index) in carouselList"
              :key="index"
            >
              <img
                :src="item.src"
                alt=""
                style="height: 100%; width: 100%;"
              >
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="rightContent">
        <!-- 活动配置区域 -->
        <section class="container">
          <div class="title">活动配置</div>
          <el-form
            label-position="right"
            label-width="113px"
            ref="payRewardForm"
            :rules="rules"
            :model="params"
          >
            <el-form-item
              label="活动名称："
              style="margin-top:15px"
              prop="activityNames"
            >
              <el-input
                v-model="params.activityNames"
                size="small"
                style="width: 170px"
                placeholder="最多支持10个字"
                maxlength="10"
                show-word-limit
              ></el-input>
            </el-form-item>

            <el-form-item
              label="活动有效期："
              prop="timeType"
            >
              <div>
                <el-radio
                  v-model="params.timeType"
                  :label="1"
                  style="margin-right: 20px;"
                >固定时间</el-radio>
                <el-date-picker
                  v-model="dateInterval"
                  type="daterange"
                  style="width:240px;"
                  size="small"
                  range-separator="至"
                  start-placeholder="生效时间"
                  end-placeholder="过期时间"
                ></el-date-picker>
                <div>
                  <el-radio
                    v-model="params.timeType"
                    :label="2"
                  >永久有效</el-radio>
                </div>
              </div>
            </el-form-item>

            <el-form-item
              label="优先级："
              prop="actFirst"
            >
              <el-input
                v-model="params.actFirst"
                size="small"
                style="width:170px"
              ></el-input>
              <div class="tips">用于区分不同支付有礼活动的优先级，请填写正整数，数值越大优先级越高</div>
            </el-form-item>

            <el-form-item
              label="触发条件："
              class="triggerCondition"
              prop="goodsAreaType"
            >
              <span style="color: #999">以下条件为"且"的关系</span>
              <div>
                <span>商品条件：</span>
                <el-radio-group v-model="params.goodsAreaType">
                  <el-radio :label="1">全部商品</el-radio>
                  <el-radio :label="2">部分商品</el-radio>
                </el-radio-group>
                <div
                  class="noneBlock"
                  v-if="params.goodsAreaType === 2"
                >
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
                    <div
                      v-if="item.num"
                      class="noneBlockRight"
                    >已选择分类：{{item.num}}个分类</div>
                  </div>
                </div>
              </div>
              <div>
                <span>支付条件：</span>
                <span>每笔订单满</span>
                <el-input
                  v-model="params.minPayMoney"
                  size="small"
                  style="width: 100px"
                ></el-input>
                <span>元，参与活动</span>
              </div>
            </el-form-item>

            <el-form-item
              label="参与限制："
              prop="limitTimes"
            >
              <div style="display: flex">
                <span>每个用户可参加</span>
                <el-input
                  v-model="params.limitTimes"
                  size="small"
                  style="width: 100px;margin:0 5px"
                ></el-input>
                <span>次活动</span>
              </div>
            </el-form-item>
          </el-form>
        </section>

        <!-- 支付奖励区域 -->
        <section class="container">
          <div class="pay_rewards">
            <div class="name">支付奖励</div>
            <div>
              <span>最多可添加5次支付的奖励</span>
              <div
                class="addReward"
                @click="addItem()"
              >+添加奖励</div>
            </div>
          </div>
          <el-form
            label-width="113px"
            label-position="right"
            v-for="(item,index) in params.awardList"
            :key="index"
            class="order_form"
          >

            <el-form-item
              :label="'第'+(index+1)+'次支付奖励'"
              class="order"
              label-width="112px"
            >
              <div class="delIcon">
                <i
                  v-if="index>0"
                  class="el-icon-delete "
                  style="color:#409eff;cursor:pointer"
                  @click="deleteItem(index)"
                ></i>
              </div>
            </el-form-item>

            <el-form-item
              label="支付奖励："
              required
            >
              <el-radio-group
                v-model="params.awardList[index].giftType"
                class="itemOptions"
              >
                <div style="margin-top:13px">
                  <el-radio label="1">无奖品</el-radio>
                  <el-radio label="2">普通优惠券</el-radio>
                  <el-radio label="3">分裂优惠券</el-radio>
                </div>
                <div style="margin-top:10px">
                  <el-radio label="4">幸运大抽奖</el-radio>
                  <el-radio label="5">余额</el-radio>
                  <el-radio label="6">奖品</el-radio>
                </div>
                <div style="margin-top:10px">
                  <el-radio label="7">积分</el-radio>
                  <el-radio label="8">自定义</el-radio>
                </div>
              </el-radio-group>
            </el-form-item>

            <el-form-item
              v-if="item.giftType==='2'"
              label="普通优惠券："
              prop="awardList[index].ordinaryCoupon"
              :rules="{
                required: true
              }"
            >
              <div class="middleContainer">
                <div
                  v-for="(itemC,indexC) in params.awardList[index].ordinaryCoupon"
                  :key="indexC"
                  class="addInfo clear"
                >
                  <section
                    class="couponImgWrapper"
                    style="line-height: normal"
                  >
                    <div
                      class="coupon_list_top"
                      v-if="itemC.actCode==='voucher'"
                    >
                      <span>￥</span>
                      <span>{{itemC.denomination}}</span>
                    </div>
                    <div
                      class="coupon_list_top"
                      v-if="itemC.actCode==='discount'"
                    >
                      <span style="font-size: 20px">{{itemC.denomination}}</span>
                      <span style="font-size: 14px">折</span>
                    </div>
                    <div class="coupon_center_limit">{{itemC.useConsumeRestrict | formatLeastConsume(itemC.leastConsume)}}</div>
                    <div class="coupon_center_number">剩余{{itemC.surplus}}张</div>
                    <div
                      class="coupon_list_bottom"
                      style="font-size:12px"
                    >
                      <span v-if="itemC.scoreNumber === 0">领取</span>
                      <div v-if="itemC.scoreNumber !== 0">
                        <span>{{itemC.scoreNumber}}</span>积分 兑换
                      </div>
                    </div>
                  </section>
                  <span
                    @click="deleteCouponImg(itemC,indexC,index)"
                    class="deleteIcon"
                  >×</span>
                </div>

                <div
                  class="addInfo"
                  @click="handleToCallDialog1(item,index)"
                >
                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                    style="width: 78px;height:78px;cursor:pointer"
                  ></el-image>
                  <p>添加优惠券</p>
                </div>
              </div>
              <div class="textTips">最多可以添加5张优惠券，已过期和已停用的优惠券不能添加</div>

            </el-form-item>

            <el-form-item
              v-if=" params.awardList[index].giftType ==='3'"
              label="分裂优惠券："
              prop="awardList[index].splitCoupon"
              :rules="{
                required: true, message:'请选择分裂优惠券'

              }"
            >
              <div class="middleContainer">
                <div
                  v-for="(itemD,indexD) in params.awardList[index].splitCoupon"
                  :key="indexD"
                  class="addInfo"
                >
                  <section
                    class="couponImgWrapper"
                    style="line-height: normal"
                  >
                    <div
                      class="coupon_list_top"
                      v-if="itemD.actCode==='voucher'"
                    >
                      <span>￥</span>
                      <span>{{itemD.denomination}}</span>
                    </div>
                    <div
                      class="coupon_list_top"
                      v-else-if="itemD.actCode=='random'"
                    >
                      <span>￥</span>
                      <span>{{itemD.randomMax}}</span>
                      <span>最高</span>
                    </div>
                    <div
                      class="coupon_list_top"
                      v-else
                    >
                      <span style="font-size: 20px">{{itemD.denomination}}</span>
                      <span style="font-size: 14px">折</span>
                    </div>
                    <div class="coupon_center_limit">{{itemD.useConsumeRestrict | formatLeastConsume(itemD.leastConsume)}}</div>
                    <div class="coupon_center_number">剩余{{itemD.surplus}}张</div>
                    <div
                      class="coupon_list_bottom"
                      style="font-size:12px"
                    >
                      <span v-if="itemD.scoreNumber === 0">领取</span>
                      <div v-if="itemD.scoreNumber !== 0">
                        <span>{{itemD.scoreNumber}}</span>积分 兑换
                      </div>
                    </div>
                  </section>
                  <span
                    @click="deleteCouponImg2(itemD, indexD, index)"
                    class="deleteIcon"
                  >×</span>
                </div>

                <div
                  class="addInfo"
                  @click="handleToCallDialog2(item, index)"
                  style="line-height: normal"
                  v-if="params.awardList[index].splitCoupon.length < 1"
                >
                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                    style="width: 78px;height:78px;cursor:pointer"
                  ></el-image>
                  <p>{{$t('addBargainAct.addCoupon')}}</p>
                </div>
              </div>
              <div class="textTips">最多可以添加1张优惠券，已过期和已停用的优惠券不能添加</div>
            </el-form-item>

            <el-form-item
              v-if="item.giftType==='4'"
              label="幸运大抽奖："
              class="luckyDraw"
              prop="awardList[index].lotteryId"
              :rules="{
                required: true, message: '请选择幸运大抽奖活动', trigger: 'blur'
              }"
            >
              <el-select
                size="small"
                style="width: 120px"
                v-model="params.awardList[index].lotteryId"
                placeholder="请选择抽奖活动"
              >
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.lotteryName"
                  :value="item.id"
                ></el-option>
              </el-select>&nbsp;
              <span>刷新</span> | <span @click="create">新建</span> | <span @click="manage">管理</span>
            </el-form-item>

            <el-form-item
              v-if="params.awardList[index].giftType==='5'"
              label="余额："
              prop='awardList[index].accountNumber'
              :rules="{
                  required: true, message: '请选择余额', trigger: 'blur'
                }"
            >
              <el-input
                v-model="params.awardList[index].accountNumber"
                size="small"
                style="width:120px"
                placeholder="请输入余额"
              ></el-input>
            </el-form-item>

            <el-form-item
              v-if="item.giftType==='6'"
              label="奖品："
              required
            >
              <div
                class="addGoodsWrapper"
                @click="addGoods(index)"
              >+&nbsp;添加奖品</div>
              <div
                v-if="true"
                class="goods_modal"
              >
                <table>
                  <thead>
                    <tr style="background: #F8F8F8;">
                      <th width="50%">商品名称</th>
                      <th width="10%">价格</th>
                      <th width="20%">库存</th>
                      <th width="20%">操作</th>
                    </tr>
                  </thead>
                  <tbody class="tbody">
                    <tr>
                      <td>
                        <section style="overflow:hidden">
                          <div
                            class="goods_img"
                            style="width:40px;height:40px; float: left"
                          >
                            <el-image
                              :src="params.awardList[index].customImage"
                              fit="contain"
                              style="width:100%; height: 100%;"
                            ></el-image>
                          </div>
                          <span
                            class="goods_name"
                            style="float: right;;"
                          >
                            {{ params.awardList[index].goodsName}}
                          </span>
                        </section>
                      </td>
                      <td>
                        ￥{{params.awardList[index].goodsPrice}}
                      </td>
                      <td>
                        {{ params.awardList[index].prdId}}
                      </td>
                      <td>
                        上架
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </el-form-item>

            <el-form-item
              v-if="item.giftType==='6'"
              label="赠品有效期："
              prop="awardList[idnex].keepDays"
              :rules="{
                required: true, message:'请输入赠品有效期', trigger: 'blur'
              }"
            >
              <div>
                <el-input
                  v-model="params.awardList[index].keepDays"
                  size="small"
                  style="width:100px"
                ></el-input>
                <span>天</span>
                <div>中奖用户需在有效期内领取，过期后将无法领取</div>
              </div>
            </el-form-item>

            <el-form-item
              v-if="item.giftType==='7'"
              label="积分："
              prop="awardList[index].scoreNumber"
              :rules="{
                required: true, message: '请输入积分', trigger: 'blur'
              }"
            >
              <el-input
                v-model="params.awardList[index].scoreNumber"
                size="small"
                style="width:120px"
                placeholder="请输入积分"
              ></el-input>
            </el-form-item>

            <el-form-item
              v-if="item.giftType === '8' "
              label="活动图片："
              prop="awardList[index].image"
              :rules="{
                required: true, message:'请选择活动图片', trigger: 'blur'
              }"
            >
              <div style="display: flex">
                <div
                  class="size"
                  @click="handleImage(index)"
                >
                  <el-image
                    :src="$imageHost + '/' + params.awardList[index].image"
                    fit="contain"
                    style=" width:100%; height: 100%;"
                  ></el-image>
                  <!-- <span class="logo_span">重新选择</span> -->
                </div>
                <div style="margin-top:10px">建议尺寸：560px * 700px</div>
              </div>
            </el-form-item>
            <el-form-item
              v-if="item.giftType ==='8'"
              label="设置链接："
              prop="awardList[index].customLink"
              :rules="{
                required: true, message: '请选择链接', trigger: 'blur'
              }"
            >
              <el-input
                v-model="params.awardList[index].customLink"
                size="small"
                style="width:200px"
              ></el-input>
              <span
                @click="chooseSelect(index)"
                class="selectLink"
              >选择链接</span>
            </el-form-item>

            <el-form-item
              v-if="item.giftType !== '1'"
              label="奖品份数："
              prop="awardList[index].awardNumber"
              :rules="{
                required: true, message: '请输入奖品份数', trigger:'blur'
              }"
            >
              <div>
                <el-input
                  v-model="params.awardList[index].awardNumber"
                  size="small"
                  style="width:100px"
                ></el-input>
                <span>份(已发0份) </span>
                <span>填写0表示不限制</span>
                <div class="tips">发放人数达到奖品份数，后续用户无法再获取支付奖励</div>
              </div>
            </el-form-item>
          </el-form>
        </section>
      </div>

      <!-- 保存按钮 -->
      <div class="save">
        <el-button
          size="small"
          type="primary"
          @click="actSave"
        >保存
        </el-button>
      </div>
    </div>

    <!-- 添加普通优惠卷 -->
    <AddCouponDialog
      @handleToCheck="addCouponHandle"
      :tuneUpCoupon="addCouponVisible"
      :couponBack.sync="emptySelect"
      :type=0
    />

    <!--添加分裂优惠卷-->
    <AddCouponDialog
      @handleToCheck="addDisCouponHandle"
      :tuneUpCoupon="showCouponDialog2"
      :couponBack="disCouponIdList"
      :singleElection="true"
      :type="1"
    />

    <!-- 选择商品弹窗 -->
    <ChoosingGoods
      @resultGoodsIds='getGoodsIdFromChoosingGoods'
      :tuneUpChooseGoods='controlChoosingGoodsDialog'
    />

    <!--选择商家分类弹窗-->
    <AddingBusClassDialog
      :dialogVisible.sync="businessDialogVisible"
      :classFlag="classFlag"
      @BusClassTrueArr="BusClassTrueArr()"
    />

    <!--添加品牌弹窗-->
    <AddBrandDialog
      :callAddBrand.sync='callAddBrand'
      @handleToGetBackData='handleToGetBrandBackData'
    />

    <!-- 选择链接弹窗 -->
    <SelectLinks
      :tuneUpSelectLink="tuneUpSelectLinkDialog"
      @selectLinkPath='handleToSelectLinkPath'
    />

    <!--选择图片弹窗 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="tuneUpImageDialog"
      :imageSize="[560,780]"
      @handleSelectImg='avatarSelectHandle'
    />

    <!--添加奖品弹窗-->
    <choosingGoods
      @resultGoodsRow="addGiftDialog"
      :tuneUpChooseGoods="isShowChoosingGoodsDialog"
      :singleElection="true"
      :chooseGoodsBack="chooseGoodsIdList"
    />

  </div>

</template>

<script>
import { addPayRewardAct, updatePayReward, getPayRewardInfo, isGoingAct } from '@/api/admin/marketManage/payReward.js'
import('@/util/date')

export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog'),
    AddBrandDialog: () => import('@/components/admin/addBrandDialog'),
    SelectLinks: () => import('@/components/admin/selectLinks'),
    ImageDalog: () => import('@/components/admin/imageDalog'),
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  mounted () {
    console.log(this.params.awardList[this.currentModelIndex], 'mounted func awradList item value')
    this.getIsGonigAct()
    if (this.$route.query.id > 0) {
      this.idInfo = this.$route.query.id
      this.fetchData()
    }
  },
  updated () {
    console.log(this.params.awardList[this.currentModelIndex], this.currentModelIndex, 'log awardList item value::')
  },
  filters: {
    formatLeastConsume (useConsumeRestrict, leastConsume) {
      if (useConsumeRestrict === 0) {
        return `不限制`
      } else {
        return `满${leastConsume}元可用`
      }
    }
  },
  data () {
    var validateSiForever = (rule, value, callback) => {
      if (value === 1 && (!this.params.startTime || !this.params.endTime)) {
        return callback(new Error('请选择活动生效时间'))
      }
      callback()
    }
    var validatelevel = (rule, value, callback) => {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value) {
        callback(new Error('请填写优先级'))
      } else if (!re.test(value)) {
        callback(new Error('请填写0或者正整数'))
      } else {
        callback()
      }
    }
    var validateGoodsAreaType = (rule, value, callback) => {
      console.log(value)
      // var re = /^(0|\+?[1-9][0-9]*)$/
      if (value === 1 && this.params.goodsAreaType === null) {
        callback(new Error('请输入支付条件'))
      } else if (value === 2 && this.params.goodsAreaType === null) {
        callback(new Error('请选择参加活动的商品'))
      } else {
        callback()
      }
    }
    return {
      emptySelect: [],
      idInfo: null,
      carouselList: [
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift1.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift2.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift3.jpg' }
      ],
      options: [],
      noneBlockDiscArr: [
        { name: '添加商品', num: '' },
        { name: '添加商品分类', num: '' },
        { name: '添加平台分类', num: '' },
        { name: '添加品牌', num: '' }
      ],
      controlChoosingGoodsDialog: false,
      userDialogFlag: null,
      classFlag: null, // 区分商家分类和平台分类flag
      businessDialogVisible: false, // 商家分类和平台分类flag
      choosingGoodsDateFlag1: '', // 指定商品-选择商品选中数据,
      ownGoodsId: null, // 会员专享商品: 添加的商品Id
      GoodsBrandDateFlag1: '',
      ownBrandId: null,
      callAddBrand: false,
      tuneUpSelectLinkDialog: false,
      tuneUpImageDialog: false,
      isShowChoosingGoodsDialog: false,
      showCouponDialog2: false,
      disCouponIdList: [],
      addCouponVisible: false, // 优惠券
      currentPage1: 1,
      currentModelIndex: 0, // 优惠券索引
      currentDisCouponIndex: 0, // 分裂优惠券索引
      currentLinkIndex: 0, // 选择链接索引
      totalRows: null,
      dialogFlag: 1,
      imgHost: `${this.$imageHost}`,
      dateInterval: [], // 时间范围
      dialogFlags: '',
      chooseGoodsIdList: [], // 选择商品
      currentGoodIds: 0,
      currentGoodsIndex: 0,

      params: {
        activityNames: '',
        startTime: '',
        endTime: '',
        actFirst: '', //  优先级
        timeType: 1, // 时间类型
        goodsAreaType: 1, // 商品范围类型
        goodsIds: '1003,1002', // 商品id
        goodsCatIds: '229,230,233,235,329', // 商品平台分类
        goodsSortIds: '225,226', // 商品商家分类
        minPayMoney: '', // 最少支付金额
        limitTimes: '', // 每个用户参与次数
        lotteryId: '', // 下拉框
        ordinaryCouponIdList: [],

        awardList: [
          {
            flag: null,
            giftType: '1',
            productId: '',
            keepDays: '',
            accountNumber: '',
            scoreNumber: '',
            awardNumber: '',
            // mrkingVoucherId: '',
            customLink: '',
            image: 'image/admin/btn_add.png',
            // 普通优惠券
            ordinaryCoupon: [],
            // 分裂优惠券
            splitCoupon: [],
            ordinaryCouponList: [],
            ordinaryCouponIdList: [],
            splitCouponIdList: [],
            couponList: [], // 页面优惠券
            ordinaryCouponDialogList: [], // 弹窗优惠券
            chooseGoodsList: [] // 选择商品ID列表

          }
        ]
      },
      rules: {
        activityNames: [{ required: true, message: '请输入活动名称', trigger: 'blur' }],
        timeType: [{ required: true, validator: validateSiForever }],
        actFirst: { required: true, validator: validatelevel, trigger: 'blur' },
        goodsAreaType: { required: true, validator: validateGoodsAreaType, trigger: 'change' },
        limitTimes: { required: true, message: '请输入参与限制', trigger: 'blur' }
        // lotteryId: { required: true, message: '请选择幸运大抽奖活动', triggger: 'blur' }
        // 'item.giftType': { required: true }
        // 'awardList[0].scoreNumber': { required: true }
        // 'awardList[index].scoreNumber': { required: true }
      }
    }
  },
  watch: {
    dateInterval: function (newVal) {
      if (newVal) {
        this.$set(this.params, 'startTime', newVal[0].format('yyyy-MM-dd hh:mm:ss'))
        this.$set(this.params, 'endTime', newVal[1].format('yyyy-MM-dd hh:mm:ss'))
      } else {
        this.$set(this.params, 'startTime', '')
        this.$set(this.params, 'endTime', '')
      }
    }
  },
  methods: {
    // 添加奖励 - 增加对应的支付奖励次数
    addItem () {
      let obj = {
        'flag': null,
        'giftType': '',
        'productId': '',
        'keepDays': '',
        'accountNumber': '',
        'scoreNumber': '',
        'awardNumber': '',
        'ordinaryCoupon': [],
        'splitCoupon': [],
        'splitCouponIdList': [],
        'ordinaryCouponIdList': [],
        'ordinaryCouponList': [],
        'ordinaryCouponDialogList': [],
        'chooseGoodsList': [],
        'image': 'image/admin/btn_add.png',
        'customLink': '',
        'couponList': [
          {
            'actCode': '',
            'denomination': '',
            'id': '',
            'randomMin': '',
            'randomMax': '',
            'couponCenterLimit': '',
            'couponCenterNumber': ''
          }
        ]
      }
      if (this.params.awardList.length < 5) {
        this.params.awardList.push(obj)
        // this.addCouponVisible.push(false)
        console.log(this.params.awardList)
      } else {
        this.$message.warning('最多可添加5个规则！')
      }
    },
    // 删除奖励
    deleteItem (index) {
      console.log(this.params.awardList)
      this.params.awardList.splice(index, 1)
      console.log(index)
    },

    // 普通优惠券弹窗调起 currentIndex为当前的添加的Item的索引值
    handleToCallDialog1 (item, currentIndex) {
      // console.log(item, currentIndex, 'currentIndex---')
      let arr = []
      item.ordinaryCoupon.forEach(item => {
        arr.push(item.id)
      })
      this.emptySelect = arr
      this.$nextTick(() => {
        this.addCouponVisible = !this.addCouponVisible
      })
      // 改变对应的当前模块的index
      this.currentModelIndex = currentIndex
    },

    // 普通优惠券数据处理回显处理
    addCouponHandle (data) {
      this.params.awardList[this.currentModelIndex].ordinaryCoupon = data
    },

    // 删除普通优惠券
    deleteCouponImg (item, index, currentIndex) {
      console.log(currentIndex)
      let added = this.params.awardList[currentIndex].ordinaryCouponIdList.map(item => item.id)
      this.emptySelect = added
      this.params.awardList[currentIndex].ordinaryCoupon.splice(index, 1)
    },

    // 分裂优惠券弹窗调起
    handleToCallDialog2 (item, receiveCurrentDisCouponIndex) {
      console.log(item)
      let disCouponArr = []
      item.splitCoupon.forEach(item => {
        disCouponArr.push(item.id)
      })
      this.disCouponIdList = disCouponArr
      this.showCouponDialog2 = !this.showCouponDialog2
      this.currentDisCouponIndex = receiveCurrentDisCouponIndex
    },
    // 分裂优惠券处理
    addDisCouponHandle (disCouponData) {
      this.params.awardList[this.currentDisCouponIndex].splitCoupon = disCouponData
      // console.log(data)
    },

    // 删除分裂优惠券
    deleteCouponImg2 (item, index, receiveCurrentDisCouponIndex) {
      let disCoupons = this.params.awardList[receiveCurrentDisCouponIndex].splitCouponIdList.map(item => item.id)
      this.disCouponIdList = disCoupons
      this.params.awardList[receiveCurrentDisCouponIndex].splitCoupon.splice(index, 1)
    },

    // 跳转到幸运大抽奖创建页面
    create () {
      this.$router.push({
        path: '/admin/home/main/luckyDraw'
      })
    },

    manage () {
      this.$router.push({
        path: '/admin/home/main/luckyDraw'
      })
    },

    // 点击会员专享商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      // this.controlChoosingGoodsDialog = !this.controlChoosingGoodsDialog
      console.log('指定商品')
      this.userDialogFlag = '1'
      console.log(index)
      switch (index) {
        case 0:
          // 商品弹窗显示
          this.controlChoosingGoodsDialog = !this.controlChoosingGoodsDialog
          break
        case 1:
          // this.AtreeType = 1
          console.log('商家分类')
          this.businessDialogVisible = true
          this.classFlag = 1
          break
        case 2:
          // this.AtreeType = 2
          console.log('平台分类')
          this.businessDialogVisible = true
          this.classFlag = 2
          break
        case 3:
          // 添加品牌弹窗显示
          this.callAddBrand = !this.callAddBrand
        //   console.log('detail', index, this.addBrandDialogDataFlag1)
        //   this.$http.$emit('CallAddBrand', this.chioseSureData, this.addBrandDialogDataFlag1)
      }
    },

    // 11- 获取会员权益选择商品弹窗的商品id
    getGoodsIdFromChoosingGoods (data) {
      // 添加商品id
      if (this.userDialogFlag === '1') {
        this.choosingGoodsDateFlag1 = data
        this.noneBlockDiscArr[0].num = data.length
      } else {
        this.ownGoodsId = data
        this.noneBlockVipArr[0].num = data.length
      }
    },

    // 调起链接弹窗
    chooseSelect (index) {
      this.tuneUpSelectLinkDialog = !this.tuneUpSelectLinkDialog
      console.log(this.currentModelIndex)
      console.log(index, 'current')
      this.currentModelIndex = index
    },

    // 链接数据选中回传
    handleToSelectLinkPath (link) {
      console.log(link, 'link')
      this.params.awardList[this.currentModelIndex].customLink = link
      console.log(this.params.awardList[this.currentModelIndex].customLink)
    },

    // 活动图片弹窗调起
    handleImage (index) {
      this.currentModelIndex = index
      console.log(this.currentModelIndex)

      this.tuneUpImageDialog = !this.tuneUpImageDialog
    },

    // 活动图片替换
    avatarSelectHandle (val) {
      this.params.awardList[this.currentModelIndex].image = val.imgPath
      console.log(val.imgPath, this.params.awardList[this.currentModelIndex], 'current awardList item')
    },

    // 添加商品品牌弹窗
    handleToGetBrandBackData (data) {
      console.log(data)
      if (this.userDialogFlag === '1') {
        this.GoodsBrandDateFlag1 = data
        this.noneBlockDiscArr[3].num = data.length
      } else {
        this.ownBrandId = data
        this.noneBlockVipArr[3].num = data.length
      }
    },

    // 商品分类和平台分类弹窗选中回传数据
    BusClassTrueArr (data) {
      // 根据this.AtreeType 的值判断是指定商品里面的弹窗还是会员专享里面的弹窗   backDataArr字段是回显wiki应该有写
      console.log(data)
    },

    // 调起选择商品弹窗
    addGoods (index) {
      console.log(index)
      this.currentModelIndex = index
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },

    // 返回商品信息 选状态选中的商品全部信息
    addGiftDialog (res) {
      console.log(res, 'goodInfo')
      this.params.awardList[this.currentModelIndex].prdId = res.prdId
      this.params.awardList[this.currentModelIndex].goodsShow = true
      this.params.awardList[this.currentModelIndex].goodsName = res.goodsName
      this.params.awardList[this.currentModelIndex].customImage = res.goodsImg
      this.params.awardList[this.currentModelIndex].goodsPrice = res.prdPrice
      this.params.awardList[this.currentModelIndex].goodsNumber = res.prdNumber
      console.log(this.params.awardList, this.params.awardList[this.currentModelIndex], 'console log awardiList value===')
      this.$forceUpdate()
    },

    // 点击编辑按钮跳转过来获取对应的信息
    fetchData () {
      getPayRewardInfo({ id: this.idInfo }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content)
          console.log(res.content)
          this.params = Object.assign({}, this.params, res.content)
          if (res.content.startTime && res.content.endTime) {
            this.dateInterval = [new Date(res.content.startTime), new Date(res.content.endTime)]
          }
        }
      }).catch(err => console.log(err))
    },

    // 对获取到的信息进行处理
    handleData (data) {
      this.params.activityNames = data.activityNames
      // this.params.startTime = data.startTime
      // this.params.endTime = data.endTime
      // this.params.timeType = data.timeType //  缺少时间类型字段
      this.params.actFirst = data.actFirst
      this.params.goodsAreaType = data.goodsAreaType
      // this.params.goodsIds = data.goodsIds
      // this.params.goodsCatIds = data.goodsCatIds
      // this.params.goodsSortIds = data.goodsSortIds
      this.params.minPayMoney = data.minPayMoney
      this.params.limitTimes = data.limitTimes
      // this.params.lotteryId = data.lotteryId
    },

    // 添加支付有礼活动接口调用
    actSave () {
      console.log(this.params, 'this.params')
      addPayRewardAct(this.params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('保存成功')
        } else {
          this.$message.warning('保存失败')
        }
      }).catch(err => console.log(err))
    },

    getIsGonigAct () {
      isGoingAct().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.options = res.content.dataList
        }
      }).catch(err => console.log(err))
    },

    // 更新支付有礼活动接口调用
    updateActSave () {
      updatePayReward(this.params).then(res => {
        console.log(res)
      }).catch(err => console.log(err))
    }

  }
}

</script>

<style lang="scss" scoped>
.addPayRewardAct {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  font-size: 14px;
  .addPayRewardAct_main {
    position: relative;
    display: flex;
    justify-content: center;
    background: #fff;
    padding: 40px 0 96px;
    .leftContent {
      width: 325px;
      height: 573px;
      background: #f5f5f5;
      .titles {
        height: 55px;
        img {
          width: 324px;
        }
      }
      .carousel {
        width: 324px;
        height: 55px;
      }
    }
    .rightContent {
      margin-left: 20px;
      .container {
        border: 1px solid #e5e5e5;
        background: #f8f8f8;
        width: 550px;
        padding: 0 10px;
        border-radius: 4px;
        margin-bottom: 10px;
        .title {
          height: 40px;
          line-height: 40px;
          border-bottom: 1px solid #eee;
        }

        /deep/ .el-form-item {
          margin-bottom: 16px;
        }

        .pay_rewards {
          display: flex;
          justify-content: space-between;
          height: 40px;
          line-height: 40px;
          border-bottom: 1px solid #eee;
          :nth-of-type(2) {
            display: flex;
            .addReward {
              margin-left: 10px;
              padding: 0 5px;
              height: 25px;
              line-height: 25px;
              border: 1px solid #5a8bff;
              color: #5a8bff;
              cursor: pointer;
              border-radius: 4px;
              margin-right: 10px;
              margin-top: 8px;
            }
          }
        }
        .order_form {
          .order {
            position: relative;
            border-bottom: 1px dashed #ccc;
            border-top: 1px dashed #ccc;

            font-weight: bold;
            .delIcon {
              font-size: 20px;
              position: relative;
              .el-icon-delete {
                position: absolute;
                right: 20px;
                margin-top: 10px;
              }
            }
          }

          .order_form:first-child {
            border: 1px solid red;
          }
        }
        .topOrder {
          border-top: 1px dashed #eee;
        }
        .triggerCondition {
          .noneBlock {
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
          }
        }
        .itemOptions {
          div {
            .el-radio {
              width: 100px;
            }
          }
        }
        .coupon {
          background: #fff;
          border: 1px solid #e4e4e4;
          text-align: center;
          padding: 13px 0;
          cursor: pointer;
          float: none;
          width: 100px;
          height: 90px;
          img {
            margin-top: 10px;
          }
          p {
            color: #999;
            font-size: 12px;
            margin-top: -10px;
          }
        }
        .textTips {
          color: #999;
          margin-top: 10px;
          font-size: 12px;
          clear: both;
          display: block;
          content: "";
        }
        .addGoodsWrapper {
          width: 120px;
          height: 30px;
          line-height: 30px;
          text-align: center;
          color: #5a8bff;
          border: 1px solid #ccc;
          background: #fff;
          cursor: pointer;
        }
        // 添加表格奖品样式
        .goods_modal {
          display: block;
          margin-top: 10px;
          th {
            padding: 10px 0;
            border: 1px solid #eee;
          }
          td {
            border: 1px solid #ddd;
            background: #fff;
            padding: 8px 10px;
          }
        }
        // .logo_span {
        //   display: inline-block;
        //   width: 15%;
        //   height: 20px;
        //   line-height: 20px;
        //   position: absolute;
        //   bottom: 0;
        //   left: 0;
        //   color: #fff;
        //   background: rgba(0, 0, 0, 0.5);
        //   text-align: center;
        //   font-size: 12px;
        // }
        .tips {
          margin-top: 10px;
          line-height: 1.4;
          font-size: 12px;
          color: #999;
        }
        .middleContainer {
          .addInfo {
            float: left;
            width: 100px;
            height: 101px;
            margin-bottom: 10px;
            background: #fff;
            border: 1px solid #e4e4e4;
            margin-right: 20px;
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
            .couponImgWrapper {
              width: 100%;
              height: 100%;
              border: 1px solid #fbb;
              border-radius: 10px;
              .coupon_list_top {
                margin-top: 10px;
                color: #f60;
                span {
                  font-weight: bold;
                }
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
        }

        .selectLink {
          background-color: #fff;
          width: 80px;
          height: 30px;
          line-height: 30px;
          padding: 6px 12px;
          border: 1px solid #dbdbdb;
          border-radius: 4px;
        }

        .size {
          width: 70px;
          height: 70px;
          box-shadow: 0 0 0 #fff;
          color: #9a9a9a;
          border: none;
          margin-right: 10px;
        }
        .luckyDraw {
          span {
            cursor: pointer;
          }
        }
      }
    }
    .save {
      border-top: 1px solid #f2f2f2;
      display: flex;
      justify-content: center;
      align-items: center;
      position: fixed;
      bottom: 0;
      z-index: 2;
      right: 20px;
      left: 160px;
      height: 50px;
      background: #f8f8fa;
    }
  }
}
</style>
