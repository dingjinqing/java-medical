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
            :autoplay="false"
          >
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
            label-width="102px"
          >
            <el-form-item
              label="活动名称："
              style="margin-top:15px"
            >
              <el-input
                v-model="params.activityNames"
                size="small"
                style="width: 200px"
                placeholder="最多支持10个字"
              ></el-input>
            </el-form-item>

            <el-form-item label="活动有效期：">
              <div>
                <el-radio
                  v-model="params.timeType"
                  label="1"
                  style="margin-right: 20px;"
                >固定时间</el-radio>
                <el-date-picker
                  v-model="params.startTime"
                  type="datetime"
                  placeholder="选择开始时间"
                  style="width:130px"
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
                <span>至</span>
                <el-date-picker
                  v-model="params.endTime"
                  type="datetime"
                  placeholder="选择结束时间"
                  style="width:130px"
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
                <div>
                  <el-radio
                    v-model="params.timeType"
                    label="2"
                  >永久有效</el-radio>
                </div>
              </div>
            </el-form-item>

            <el-form-item label="优先级：">
              <el-input
                v-model="params.actFirst"
                size="small"
                style="width:200px"
              ></el-input>
              <div style="line-height:30px;font-size:12px">用于区分不同支付有礼活动的优先级，请填写正整数，数值越大优先级越高</div>
            </el-form-item>

            <el-form-item
              label="触发条件："
              class="triggerCondition"
            >
              <span>以下条件为"且"的关系</span>
              <div>
                <span>商品条件：</span>
                <el-radio-group v-model="params.goodsAreaType">
                  <el-radio label="1">全部商品</el-radio>
                  <el-radio label="2">部分商品</el-radio>
                </el-radio-group>
                <div
                  class="noneBlock"
                  v-if="params.goodsAreaType === '2'"
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

            <el-form-item label="参与限制：">
              <div style="display: flex">
                <span>每个用户可参加</span>
                <el-input
                  v-model="params.limitTimes"
                  size="small"
                  style="width: 100px"
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
            label-width="100px"
            label-position="right"
            v-for="(item,index) in shareRules"
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

            <el-form-item label="支付奖励：">
              <el-radio-group
                v-model="params.awardList.giftType"
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
              v-if="params.awardList.giftType==='2'"
              label="普通优惠券："
            >
              <div class="middleContainer">
                <div
                  v-for="(item,index) in mrkingVoucherObjs"
                  :key="index"
                  class="addInfo"
                  style="margin-right: 5px;"
                >
                  <section
                    class="couponImgWrapper"
                    style="line-height: normal"
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

                <div
                  class="addInfo"
                  @click="handleToCallDialog1()"
                  v-if="mrkingVoucherObjs.length < 5"
                  style="line-height: normal"
                >
                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                    style="width: 78px;height:78px;cursor:pointer"
                  ></el-image>
                  <p>{{$t('addBargainAct.addCoupon')}}</p>
                </div>
              </div>
              <div class="textTips">最多可以添加5张优惠券，已过期和已停用的优惠券不能添加</div>
            </el-form-item>

            <el-form-item
              v-if="params.awardList.giftType==='3'"
              label="分裂优惠券："
            >
              <!-- <div class="coupon">
                <img
                  :src="$imageHost+'/image/admin/shop_beautify/add_decorete.png'"
                  alt=""
                >
                <p>添加优惠券</p>
              </div> -->
              <div class="middleContainer">
                <div
                  v-for="(item,index) in rewardCouponObjs"
                  :key="index"
                  class="addInfo"
                  style="margin-right: 5px;"
                >
                  <section
                    class="couponImgWrapper"
                    style="line-height: normal"
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
                    @click="deleteCouponImg2(index)"
                    class="deleteIcon"
                  >×</span>
                </div>

                <div
                  class="addInfo"
                  @click="handleToCallDialog2()"
                  v-if="rewardCouponObjs.length < 1"
                  style="line-height: normal"
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
              v-if="params.awardList.giftType==='4'"
              label="幸运大抽奖："
            >
              <el-select
                size="small"
                style="width: 120px"
                v-model="params.awardList.lotteryId"
                placeholder="请选择抽奖活动"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item
              v-if="params.awardList.giftType==='5'"
              label="余额："
            >
              <el-input
                v-model="params.awardList.accountNumber"
                size="small"
                style="width:120px"
                placeholder="请输入余额"
              ></el-input>
            </el-form-item>

            <el-form-item
              v-if="params.awardList.giftType==='6'"
              label="奖品："
            >
              <div
                class="addGoods"
                @click="addGoods()"
              >+&nbsp;添加奖品</div>
              <div v-if="true">
                <table
                  class="goods_modal"
                  style="display: block;"
                >
                  <thead>
                    <tr style="background: #F8F8F8;">
                      <th>商品名称</th>
                      <th>价格</th>
                      <th>库存</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody class="tbody">
                    <tr>
                      <td>
                        <div class="goods_img">
                          <img :src="params.awardList.customImage">
                        </div>
                        <div
                          class="goods_info clearfix"
                          num="50"
                          prd_id="5410"
                        >
                          <div class="goods_name">
                            {{ params.awardList.goodsName}}
                          </div>
                        </div>
                      </td>
                      <td>
                        ￥{{params.awardList.goodsPrice}}
                      </td>
                      <td>
                        {{ params.awardList.prdId}}
                      </td>
                      <td>
                        <a
                          href="##"
                          item="5410"
                          class="change_goods_del"
                        >
                          删除
                        </a>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </el-form-item>
            <el-form-item
              v-if="params.awardList.giftType==='6'"
              label="赠品有效期："
            >
              <div>
                <el-input
                  v-model="params.awardList.keepDays"
                  size="small"
                  style="width:100px"
                ></el-input>
                <span>天</span>
                <div>中奖用户需在有效期内领取，过期后将无法领取</div>
              </div>
            </el-form-item>

            <el-form-item
              v-if="params.awardList.giftType==='7'"
              label="积分："
            >
              <el-input
                v-model="params.awardList.scoreNumber"
                size="small"
                style="width:120px"
                placeholder="请输入积分"
              ></el-input>
            </el-form-item>

            <el-form-item
              v-if="params.awardList.giftType==='8'"
              label="活动图片："
            >
              <div style="display: flex">
                <div
                  class="size"
                  @click="handleImage"
                >
                  <el-image
                    :src="$imageHost + '/' + params.awardList[0].image"
                    fit="contain"
                    style="padding:4px; width:100%; height: 100%;"
                  ></el-image>
                  <!-- <span class="logo_span">重新选择</span> -->
                </div>
                <div style="margin-top:10px">建议尺寸：560px * 700px</div>
              </div>
            </el-form-item>
            <el-form-item
              v-if="params.awardList.giftType==='8'"
              label="设置链接："
            >
              <el-input
                v-model="params.awardList.customLink"
                size="small"
                style="width:200px"
              ></el-input>
              <span
                @click="chooseSelect"
                class="selectLink"
              >选择链接</span>
            </el-form-item>

            <el-form-item
              v-if="params.awardList.giftType !== '1'"
              label="奖品份数："
            >
              <div>
                <el-input
                  v-model="params.awardList.awardNumber"
                  size="small"
                  style="width:100px"
                ></el-input>
                <span>份(已发0份) </span>
                <span>填写0表示不限制</span>
                <div>发放人数达到奖品份数，后续用户无法再获取支付奖励</div>
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
          @click="submitData"
        >保存
        </el-button>
      </div>
    </div>

    <!-- 选择商品弹窗 -->
    <ChoosingGoods />
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
      :tuneUpSelectLink="tuneUpSelectLink"
      @selectLinkPath='handleToSelectLinkPath'
    />

    <!--选择图片弹窗 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="tuneUp"
      @handleSelectImg='avatarSelectHandle'
    />

    <!--添加奖品弹窗-->
    <choosingGoods
      @resultGoodsRow="choosingGoodsResult"
      :tuneUpChooseGoods="isShowChoosingGoodsDialog"
      :singleElection="true"
      :showTips="true"
      :loadProduct="true"
    />

    <!--添加优惠卷-->
    <AddCouponDialog
      @handleToCheck="handleToCheck"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
    />
  </div>

</template>

<script>
import { addPayRewardAct } from '@/api/admin/marketManage/payReward.js'

export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog'),
    AddBrandDialog: () => import('@/components/admin/addBrandDialog'),
    SelectLinks: () => import('@/components/admin/selectLinks'),
    ImageDalog: () => import('@/components/admin/imageDalog'),
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  created () {
    // this.initData()
  },
  mounted () {
    // this.dataDefalut()
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
    return {
      carouselList: [
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift1.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift2.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift3.jpg' }
      ],
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
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
      GoodsBrandDateArr: '',
      callAddBrand: false,
      shareRules: [{}],
      tuneUpSelectLink: false,
      tuneUp: false,
      isShowChoosingGoodsDialog: false,
      storeImgs: [],
      showCouponDialog: false,
      couponIdList: [],
      // 普通优惠券
      mrkingVoucherObjs: [],
      // 分裂优惠券
      rewardCouponObjs: [],
      // 优惠券弹窗区分，1普通优惠券，0分裂优惠券
      currentPage1: 1,
      totalRows: null,
      dialogFlag: 1,
      imgHost: `${this.$imageHost}`,
      params: {
        activityNames: '',
        startTime: '',
        endTime: '',
        actFirst: '',
        timeType: '1',
        goodsAreaType: '1', // 商品范围类型
        goodsIds: '1003,1002', // 商品id
        goodsCatIds: '229,230,233,235,329', // 商品平台分类
        goodsSortIds: '225,226', // 商品商家分类
        minPayMoney: '', // 最少支付金额
        limitTimes: '', // 每个用户参与次数
        lotteryId: '', // 下拉框
        awardList: [
          {
            giftType: '1',
            productId: 5473,
            keepDays: '',
            accountNumber: '',
            scoreNumber: '',
            awardNumber: '',
            customLink: '',
            image: 'image/admin/btn_add.png',
            couponList: [
              {
                actCode: 'voucher',
                denomination: 50,
                id: 470,
                randomMin: 0,
                randomMax: 0,
                couponCenterLimit: '满100使用',
                couponCenterNumber: '剩余196张'
              }
            ],
            couponIds: [
              479,
              476
            ]
          }
        ]
      }
    }
  },
  methods: {
    // 添加奖励 - 增加对应的支付奖励次数
    addItem () {
      let obj = {
        coupon: ''
      }
      if (this.shareRules.length < 5) {
        this.shareRules.push(obj)
      } else {
        alert('最多可添加5个规则！')
      }
    },
    // 删除奖励
    deleteItem (index) {
      console.log(this.shareRules)
      this.shareRules.splice(index, 1)
      console.log(index)
    },

    // 普通优惠券和分裂优惠券处理
    // 普通优惠券弹窗调起
    handleToCallDialog1 () {
      this.dialogFlag = 0
      this.couponIdList = this.getCouponIdsArray(this.mrkingVoucherObjs)
      this.showCouponDialog = !this.showCouponDialog
    },
    // 分裂优惠券弹窗调起
    handleToCallDialog2 () {
      this.dialogFlag = 1
      this.couponIdList = this.getCouponIdsArray(this.rewardCouponObjs)
      this.showCouponDialog = !this.showCouponDialog
    },
    // 删除优惠券
    handleToCheck (data, index) {
      console.log(data)
      console.log(this.rewardCouponObjs)
      if (this.dialogFlag === 1) {
        // console.log(this.rewardCouponObjs)
        if (this.rewardCouponObjs.length === 1) {
          return
        }
        this.rewardCouponObjs = data
      } else {
        if (this.mrkingVoucherObjs.length >= 5) {
          return
        }
        this.mrkingVoucherObjs = data
      }
    },
    // 删除好友砍价优惠券图片
    deleteCouponImg (index) {
      this.mrkingVoucherObjs.splice(index, 1)
    },
    // 删除鼓励奖优惠券图片
    deleteCouponImg2 (index) {
      this.rewardCouponObjs.splice(index, 1)
    },
    getCouponIdsArray (data) {
      let res = []
      data.forEach((item, index) => {
        res.push(item.id)
      })
      return res
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
      console.log(data)
      // 添加商品id
      if (this.userDialogFlag === '1') {
        this.choosingGoodsDateFlag1 = data
        this.noneBlockDiscArr[0].num = data.length
      } else {
        this.ownGoodsId = data
        this.noneBlockVipArr[0].num = data.length
      }
    },

    // 调起选择商品链接弹窗
    chooseSelect () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },

    // 选择链接选中回传
    handleToSelectLinkPath (link) {
      this.params.awardList.customLink = link
      // console.log(path)
    },

    // 活动图片调起
    handleImage () {
      this.tuneUp = !this.tuneUp
    },
    // 活动图片替换
    avatarSelectHandle (val) {
      this.params.awardList.image = val.imgPath
      console.log(val.imgPath)
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
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 返回商品信息
    choosingGoodsResult (res) {
      console.log(res)
      this.params.awardList.prdId = res.prdId
      this.params.awardList.goodsShow = true
      this.params.awardList.goodsName = res.goodsName
      this.params.awardList.customImage = res.goodsImg
      this.params.awardList.goodsPrice = res.prdPrice
      this.params.awardList.goodsNumber = res.prdNumber
      this.$forceUpdate()
    },

    submitData () {
      addPayRewardAct(this.params).then(res => {
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
      width: 323px;
      height: 573px;
      border: 1px solid #ccc;
      background: #f5f5f5;
      .titles {
        height: 55px;
        img {
          width: 323px;
        }
      }
      .carousel {
        width: 323px;
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

        // .order_form:first-child {
        //   border-top: none;
        // }
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
        }
        .addGoods {
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
          border: none;
          margin-top: 10px;
          margin-bottom: 0;
          margin-left: 80px;
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
