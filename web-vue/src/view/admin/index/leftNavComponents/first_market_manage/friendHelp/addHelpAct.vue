
<template>
  <div class="actWrapper">
    <div class="content">
      <div class="main">
        <el-form
          :model="form"
          label-width="150px"
          labelPosition='right'
          :rules="formRules"
          ref="form"
        >
          <el-form-item
            :label="$t('promoteList.actName')"
            prop="actName"
          >
            <el-input
              size="small"
              :placeholder="$t('promoteList.actNamePlaceholder')"
              class="morelength"
              v-model="form.actName"
            ></el-input>
            <span style="margin-left: 10px">{{$t('promoteList.actRules')}}</span>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.actValidityPeriod')"
            prop=""
            required
          >
            <section style="display: flex">
              <el-form-item prop="startTime">
                <el-date-picker
                  v-model="form.startTime"
                  type="datetime"
                  :placeholder="$t('promoteList.startTime')"
                  class="morelength"
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
              </el-form-item>
              <span style="margin: 0 5px">{{$t('promoteList.to')}}</span>
              <el-form-item prop="endTime">
                <el-date-picker
                  v-model="form.endTime"
                  type="datetime"
                  :placeholder="$t('promoteList.endTime')"
                  class="morelength"
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
              </el-form-item>
            </section>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.rewardType')"
            prop=""
          >
            <el-radio
              v-model="form.rewardType"
              label=0
            >
              {{$t('promoteList.giftGoods')}}
            </el-radio>
            <el-radio
              v-model="form.rewardType"
              label=1
            >{{$t('promoteList.discountGoods')}}</el-radio>
            <el-radio
              v-model="form.rewardType"
              label=2
            >{{$t('promoteList.giftCoupons')}}</el-radio>
            <el-col v-if="form.rewardType==0 || form.rewardType==1">
              <el-button
                size="small"
                @click="showChoosingGoods"
              >+ {{$t('promoteList.chooseGoods')}}</el-button>
            </el-col>
            <el-col v-if="form.rewardType==2">
              <div
                class="addInfo"
                @click="isEditFlag?'':handleToCallDialog(1)"
              >
                <el-image
                  fit="scale-down"
                  :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                  style="width: 78px;height:78px;cursor:pointer"
                ></el-image>
                <p>{{$t('addBargainAct.addCoupon')}}</p>
              </div>
            </el-col>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.rewardSet')"
            prop=""
          >
            <el-table
              v-if="form.rewardType==0 || form.rewardType==1"
              :data="form.goodsInfo"
              border
              style="width:50%"
            >
              <el-table-column
                prop="goodsName"
                :label="$t('promoteList.goodsInfo')"
                align="center"
              ><template></template>
              </el-table-column>
              <el-table-column
                prop="shopPrice"
                :label="$t('promoteList.goodsPrice')"
                align="center"
              ><template></template>
              </el-table-column>

              <el-table-column
                prop="goodsNumber"
                :label="$t('promoteList.goodsStore')"
                align="center"
              ><template></template>
              </el-table-column>

              <el-table-column
                :label="$t('promoteList.actStore')"
                align="center"
              >
                <template slot-scope="data">
                  <el-input
                    v-model="data.row.market_store"
                    size="small"
                  ></el-input>
                </template>
              </el-table-column>

              <el-table-column
                v-if="form.rewardType==1"
                prop="market_price"
                :label="$t('promoteList.actPrice')"
                align="center"
              >
                <template slot-scope="data">
                  <el-input v-model="data.row.market_price"></el-input>
                </template>
              </el-table-column>
            </el-table>

            <el-table
              v-if="form.rewardType==2"
              :data="coupon_info"
              border
              style="width: 300px;"
            >
              <el-table-column
                :label="$t('promoteList.couponInfo')"
                align="center"
              >
                <template slot-scope="scope">
                  <div class="">
                    <span class="coupon_name">{{scope.row.actName}}</span>
                    <div
                      v-if="scope.row.actCode == 'voucher'"
                      style="color:red"
                    >￥<span>{{scope.row.denomination}}</span></div>
                    <div v-else><span>{{scope.row.denomination}}</span>折</div>
                    <div class="coupon_rule">{{scope.row.useConsumeRestrict > 0? `满${scope.row.leastConsume}元可用`  : `不限制`}}</div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('promoteList.couponNum')"
                width="130"
                align="center"
              >
                <template slot-scope="scope">
                  <div>
                    <el-input
                      v-model="scope.row.send_num"
                      size="small"
                      style="width:100px;"
                    ></el-input>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>

          <el-form-item
            :label="$t('promoteList.rewardValidityPeriod')"
            prop=""
          >
            <div style="display:flex">
              <el-form-item prop="rewardDuration">
                <el-input
                  size="small"
                  v-model="form.rewardDuration"
                ></el-input>
              </el-form-item>
              <el-select
                size="small"
                v-model="form.rewardDurationUnitSelect"
                style="margin: 0 10px; width: 90px"
              >
                <el-option
                  v-for="item in form.rewardDurationUnit"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <div class="gray">{{$t('promoteList.rewardValidityPeriodText')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.currentPromoteValue')"
            prop=""
          >
            <el-radio
              v-model="form.promoteType"
              label="0"
            >{{$t('promoteList.averageValue')}}</el-radio>
            <el-radio
              v-model="form.promoteType"
              label="1"
            >{{$t('promoteList.randomValue')}}</el-radio>
            <span>{{$t('promoteList.actRules')}}</span>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.requiredPromoteValue')"
            prop="promoteAmount"
          >
            <div style="display:flex">
              <el-input
                size="small"
                style="margin-right: 10px"
                v-model="form.promoteAmount"
              ></el-input>
              <div class="gray">{{$t('promoteList.requiredPromoteValueText')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.requiredPromoteTimes')"
            prop="promoteTimes"
          >
            <div style="display:flex">
              <el-input
                size="small"
                style="margin-right: 10px"
                v-model="form.promoteTimes"
              ></el-input>
              <div class="gray">{{$t('promoteList.requiredPromoteValueText')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.launchTimesLimit')"
            prop=""
          >
            <div style="display:flex">
              <span>{{$t('promoteList.userIn')}}</span>
              <el-form-item prop="launchLimitDuration">
                <el-input
                  style="margin: 0 5px"
                  size="small"
                  v-model="form.launchLimitDuration"
                ></el-input>
              </el-form-item>
              <el-select
                size="small"
                v-model="form.launchLimitUnitSelect"
                style="margin-right:5px; width: 90px"
              >
                <el-option
                  v-for="item in form.launchLimitUnit"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <span>{{$t('promoteList.mostLaunch')}}</span>
              <el-form-item prop="launchLimitTimes">
                <el-input
                  size="small"
                  style="margin:0 5px"
                  v-model="form.launchLimitTimes"
                ></el-input>
              </el-form-item>{{$t('promoteList.time')}}
              <div
                style="margin-left:10px"
                class="gray"
              >{{$t('promoteList.launchTimesLimitText')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.sharePromote')"
            prop="shareCreateTimes"
          >
            <div style="display:flex">
              <span>{{$t('promoteList.friendShare')}}</span>
              <el-input
                style="margin:0 5px"
                size="small"
                v-model="form.shareCreateTimes"
              ></el-input>
              <span>{{$t('promoteList.promoteOpportunity')}}</span>
              <div
                style="margin-left: 10px"
                class="gray"
              >{{$t('promoteList.sharePromoteText')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.promoteCondition')"
            prop=""
          >
            <el-radio
              v-model="form.promoteCondition"
              label="0"
            >{{$t('promoteList.authorizeNo')}}</el-radio>
            <el-radio
              v-model="form.promoteCondition"
              label="1"
            >{{$t('promoteList.authorizeYes')}}</el-radio>
            <span class="gray">{{$t('promoteList.promoteConditionText')}}</span>
          </el-form-item>

          <el-form-item
            v-if="form.rewardType == 1"
            :label="$t('promoteList.couponStrategy')"
            prop=""
          >
            <el-radio
              v-model="form.useDiscount"
              label="1"
            >{{$t('promoteList.useDiscount')}}</el-radio>
            <el-radio
              v-model="form.useDiscount"
              label="0"
            >{{$t('promoteList.noUseDiscount')}}</el-radio>
            <span class="gray">{{$t('promoteList.couponStrategyText')}}</span>
          </el-form-item>
          <el-form-item
            v-if="form.rewardType == 1"
            :label="$t('promoteList.scoreStrategy')"
            prop=""
          >
            <el-radio
              v-model="form.useScore"
              label="1"
            >{{$t('promoteList.useScore')}}</el-radio>
            <el-radio
              v-model="form.useScore"
              label="0"
            >{{$t('promoteList.noUseScore')}}</el-radio>
            <span class="gray">{{$t('promoteList.scoreStrategyText')}}</span>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.promoteFail')"
            prop=""
          >
            <el-radio
              v-model="form.failedSendType"
              label="0"
            >
              {{$t('promoteList.giftNothing')}}
            </el-radio>
            <el-radio
              v-model="form.failedSendType"
              label="1"
            >{{$t('promoteList.coupon')}}</el-radio>
            <el-radio
              v-model="form.failedSendType"
              label="2"
            >{{$t('promoteList.point')}}</el-radio>
            <div
              v-if="form.failedSendType==1"
              @click="isEditFlag?'':handleToCallDialog(2)"
            >

              <!--占位-->
              <div
                v-if="!coupon_duplicate.length"
                class="addInfo"
              >
                <el-image
                  fit="scale-down"
                  :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                  style="width: 78px;height:78px;cursor:pointer"
                ></el-image>
                <p>{{$t('addBargainAct.addCoupon')}}</p>
              </div>
              <div
                class="addInfo"
                v-else
              >
                <div class="couponImgWrapper">
                  <div class="coupon_list_top">
                    <span>￥</span>
                    <span class="number">{{coupon_duplicate[0].denomination}}</span>
                  </div>
                  <div class="coupon_center_limit">{{coupon_duplicate[0].useConsumeRestrict | formatLeastConsume(coupon_duplicate[0].leastConsume)}}</div>
                  <div class="coupon_center_number">剩余{{coupon_duplicate[0].surplus}}张</div>
                  <div
                    class="coupon_list_bottom"
                    style="font-size:12px"
                  >领取</div>
                </div>
              </div>

            </div>

            <div v-if="
              form.failedSendType==2">
              {{$t('promoteList.giftPoint')}}
              <el-input
                size="small"
                type="primary"
                v-model="form.failedSendContent"
              ></el-input>
            </div>
          </el-form-item>

          <div></div>
          <!-- 收起、展开更多配置 -->
          <el-collapse>
            <el-collapse-item>
              <template slot="title">
                {{$t('promoteList.moreSettings')}}
              </template>
              <el-form-item
                :label="$t('promoteList.actShare')"
                prop=""
              >
                <div>
                  <el-radio
                    v-model="form.activityShareType"
                    label="0"
                  >
                    {{$t('promoteList.defaultStyle')}}
                  </el-radio>
                  <!-- <span>{{$t('promoteList.sharePreview')}}</span>
                  <span>{{$t('promoteList.posterPreview')}}</span> -->
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
                    >{{$t('marketCommon.viewExample')}}</el-button>
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
                    >{{$t('marketCommon.downloadPoster')}}</el-button>
                  </el-popover>
                </div>
                <div>
                  <el-radio
                    v-model="form.activityShareType"
                    label="1"
                  >
                    {{$t('promoteList.customStyle')}}
                    <div
                      v-if="form.activityShareType == 1"
                      style="margin-left: 29px"
                    >
                      <div style="margin: 15px 0">
                        <span style="margin-right: 25px">{{$t('promoteList.words')}}</span>
                        <el-input
                          size="small"
                          style="width:200px"
                          v-model="form.customShareWord"
                        ></el-input>
                      </div>
                      <div>
                        <span>{{$t('promoteList.sharePicture')}}</span>
                        <el-radio
                          v-model="form.shareImgType"
                          label="0"
                          style="margin-left:10px"
                        >{{$t('promoteList.goodsPicture')}}</el-radio>

                        <div style="margin: 10px 0 0 57px">
                          <el-radio
                            v-model="form.shareImgType"
                            label="1"
                          >{{$t('promoteList.customPicture')}}</el-radio>

                          <div
                            style="display: flex;align-items: center;flex-wrap: wrap;"
                            v-if="form.shareImgType == 1"
                          >
                            <span
                              @click="deleteGoodsImg()"
                              v-if="this.srcList.src !==`${this.$imageHost}/image/admin/add_img.png`"
                              class="deleteIcon"
                            >×</span>
                            <div
                              @click="addGoodsImg"
                              class="ImgWrap"
                            >
                              <el-image
                                style="width: 80px; height: 80px"
                                :src="srcList.src"
                                fit="scale-down"
                              ></el-image>
                            </div>
                            <span class="inputTip">
                              {{$t('promoteList.pictureTip')}}
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>

                  </el-radio>
                </div>
              </el-form-item>
            </el-collapse-item>
          </el-collapse>
        </el-form>
      </div>

      <div class="footer">
        <el-button
          type="primary"
          size="small"
          @click="addAct"
        >{{$t('promoteList.save')}}</el-button>
      </div>
    </div>
    <choosingGoods
      @resultGoodsRow="choosingGoodsResult"
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="goodsIdList"
      :singleElection="true"
    >
    </choosingGoods>
    <!--奖励类型-添加优惠卷-->
    <AddCouponDialog
      singleElection="true"
      @handleToCheck="handleToCheck"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
    />

    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="showImageDialog"
      @handleSelectImg='imgDialogSelectedCallback'
    />
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import choosingGoods from '@/components/admin/choosingGoods'
import { addActive, selectOneInfo, updateInfo } from '@/api/admin/marketManage/friendHelp.js'
import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
import { selectGoodsApi } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods.js'
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: {
    choosingGoods,
    ImageDalog,
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
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
      couponFlag: null,
      promoteId: '',
      show: false,
      radio: 'one',
      isEditFlag: false,
      tuneUpChooseGoods: false,
      goodsIdList: [],
      goodsProductInfo: {
        // 基本信息
        goodsName: null,
        goodsAd: null,
        goodsSn: null,
        catId: null,
        goodsImgs: []

      },
      // 表单
      form: {
        test: '',
        actName: '',
        rewardType: '0',
        rewardContent: '',
        coupon_store: '',
        rewardSet: {
          goods_ids: '',
          reward_ids: '',
          market_price: '',
          market_store: ''
        },
        useDiscount: '0',
        useScore: '1',
        startTime: '',
        endTime: '',
        ruleForm: {},
        rewardDuration: '',
        rewardDurationUnit: [{
          value: 0,
          label: this.$t('promoteList.hour')
        }, {
          value: 1,
          label: this.$t('promoteList.day')
        }, {
          value: 2,
          label: this.$t('promoteList.week')
        }],
        rewardDurationUnitSelect: '',
        promoteType: '0',
        promoteAmount: '',
        promoteTimes: '',
        launchLimitDuration: '',
        launchLimitUnit: [{
          value: 0,
          label: this.$t('promoteList.day')
        }, {
          value: 1,
          label: this.$t('promoteList.week')
        }, {
          value: 2,
          label: this.$t('promoteList.month')
        }, {
          value: 3,
          label: this.$t('promoteList.year')
        }],
        launchLimitUnitSelect: '',
        launchLimitTimes: '',
        shareCreateTimes: '',
        promoteCondition: '0',
        failedSendType: '0',
        failedSendContent: '',
        activityShareType: '0',
        customShareWord: '',
        shareImgType: '0',
        // customImgPath: '',
        // 选中商品id
        goodsInfo: [{
          goodsIds: '',
          goodsName: '',
          shopPrice: '',
          goodsNumber: '',
          rewardType: '',
          market_price: '',
          market_store: ''
        }]

      },
      // 优惠券
      coupon_msg: [],
      coupon_info: [],
      coupon_duplicate: [],
      couponDialogFlag: false,
      couponDialogFlag1: false,
      couponSetDialogFlag: false,
      coupon_set: {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      },
      // coupon_set: {
      //   immediatelyGrantAmount: 0,
      //   timingEvery: 0,
      //   timingAmount: 0,
      //   timingTime: '1',
      //   timingUnit: '0'
      // },
      target: null,

      // 表单约束
      formRules: {
        actName: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'change' }
        ],
        endTime: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'change' }
        ],
        rewardDuration: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        promoteAmount: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        promoteTimes: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        launchLimitDuration: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        launchLimitTimes: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        shareCreateTimes: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ]
      },
      srcList: {
        src: `${this.$imageHost}/image/admin/add_img.png`,
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`,
        imageUrl: ``
      },
      showCouponDialog: false,
      couponIdList: [],
      showImageDialog: false,
      imgHost: `${this.$imageHost}`
    }
  },
  created () {
    this.form.rewardDurationUnitSelect = this.form.rewardDurationUnit[0].value
    this.form.launchLimitUnitSelect = this.form.launchLimitUnit[0].value
    this.promoteId = this.$route.params.id
    if (this.promoteId !== 'null') {
      console.log('id:', this.promoteId)
      this.loadData(this.promoteId)
    }
  },
  mounted () {
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    loadData (id) {
      let selectParam = {
        'id': id
      }
      selectOneInfo(selectParam).then(res => {
        console.log('message', res)
        console.log('pageInfo:', res.content[0])
        this.form.actName = res.content[0].actName
        this.form.startTime = res.content[0].startTime
        this.form.endTime = res.content[0].endTime
        this.form.rewardType = res.content[0].rewardType.toString()
        this.form.rewardDuration = res.content[0].rewardDuration
        this.form.rewardDurationUnitSelect = res.content[0].rewardDurationUnit
        this.form.promoteType = res.content[0].promoteType.toString()
        this.form.promoteAmount = res.content[0].promoteAmount
        this.form.promoteTimes = res.content[0].promoteTimes
        this.form.launchLimitDuration = res.content[0].launchLimitDuration
        this.form.launchLimitUnitSelect = res.content[0].launchLimitUnit
        this.form.launchLimitTimes = res.content[0].launchLimitTimes
        this.form.shareCreateTimes = res.content[0].shareCreateTimes
        this.form.promoteCondition = res.content[0].promoteCondition.toString()
        this.form.useDiscount = res.content[0].useDiscount.toString()
        this.form.useScore = res.content[0].useScore.toString()
        this.form.failedSendType = res.content[0].failedSendType.toString()
        this.form.failedSendContent = res.content[0].failedSendContent
        this.form.activityShareType = res.content[0].activityShareType.toString()
        this.form.customShareWord = res.content[0].customShareWord
        this.form.shareImgType = res.content[0].shareImgType.toString()
        this.srcList.src = res.content[0].customImgPath
        if (this.form.rewardType === '0') {
          this.form.rewardSet.market_store = JSON.parse(res.content[0].rewardContent.slice(1, -1)).market_store
          console.log(this.form.rewardSet.market_store)
          this.form.rewardSet.goods_ids = JSON.parse(res.content[0].rewardContent.slice(1, -1)).goods_ids
          console.log(this.form.rewardSet.goods_ids)
          let goodsIdParam = {
            'goodsId': this.form.rewardSet.goods_ids
          }
          selectGoodsApi(goodsIdParam).then(res => {
            this.form.goodsInfo = [res.content]
            this.form.goodsInfo.market_store = this.form.rewardSet.market_store
            console.log('goodsInfo:', res.content)
          })
        }
        if (this.form.rewardType === '1') {
          this.form.rewardSet.market_store = JSON.parse(res.content[0].rewardContent.slice(1, -1)).market_store
          this.form.rewardSet.market_price = JSON.parse(res.content[0].rewardContent.slice(1, -1)).market_price
          this.form.rewardSet.goods_ids = JSON.parse(res.content[0].rewardContent.slice(1, -1)).goods_ids
          console.log(this.form.rewardSet.goods_ids)
          let goodsIdParam = {
            'goodsId': this.form.rewardSet.goods_ids
          }
          selectGoodsApi(goodsIdParam).then(res => {
            this.form.goodsInfo = [res.content]
            this.form.goodsInfo[0].market_store = this.form.rewardSet.market_store
            this.form.goodsInfo[0].market_price = this.form.rewardSet.market_price
            console.log('goodsInfo:', res.content)
          })
        }
        if (this.form.rewardType === '2') {
          this.form.rewardSet.market_store = JSON.parse(res.content[0].rewardContent.slice(1, -1)).market_store
          this.form.rewardSet.reward_ids = JSON.parse(res.content[0].rewardContent.slice(1, -1)).reward_ids
          updateCoupon(this.form.rewardSet.reward_ids).then(res => {
            this.coupon_info = res.content
            this.coupon_info[0].send_num = this.form.rewardSet.market_store
            console.log('couponInfo:', this.coupon_info)
          })
        }
        if (this.form.failedSendType === '1') {
          updateCoupon(this.form.failedSendContent).then(res => {
            this.coupon_duplicate = res.content
            console.log('coupon_duplicate:', this.coupon_duplicate)
          })
        }
      })
    },
    addAct () {
      console.log('this.form.rewardType:', this.form.rewardType)
      if (this.form.rewardType === '0' || this.form.rewardType === '1') {
        if (this.form.goodsInfo[0].market_price == null) {
          this.form.goodsInfo[0].market_price = ''
        }
        this.form.rewardSet.market_price = this.form.goodsInfo[0].market_price
        this.form.rewardSet.market_store = this.form.goodsInfo[0].market_store
        console.log(this.form.goodsInfo[0].market_store)
        this.form.rewardContent = '[' + JSON.stringify(this.form.rewardSet) + ']'
        console.log('this.form.rewardSet.goods_ids:', this.form.rewardSet.goods_ids)
        console.log('rewardSet:', this.form.rewardSet)
        console.log('rewardContent:', this.form.rewardContent)
      }
      if (this.form.rewardType === '2') {
        this.form.rewardSet.market_store = this.coupon_info[0].send_num
        this.form.rewardContent = '[' + JSON.stringify(this.form.rewardSet) + ']'
        console.log('rewardSet:', this.form.rewardSet)
        console.log('rewardContent:', this.form.rewardContent)
      }
      let addParam = {
        'id': this.promoteId,
        'actName': this.form.actName,
        'startTime': this.form.startTime,
        'endTime': this.form.endTime,
        'rewardType': this.form.rewardType,
        'rewardContent': this.form.rewardContent,
        'rewardDuration': this.form.rewardDuration,
        'rewardDurationUnit': this.form.rewardDurationUnitSelect,
        'promoteType': this.form.promoteType,
        'promoteAmount': this.form.promoteAmount,
        'promoteTimes': this.form.promoteTimes,
        'launchLimitDuration': this.form.launchLimitDuration,
        'launchLimitUnit': this.form.launchLimitUnitSelect,
        'launchLimitTimes': this.form.launchLimitTimes,
        'shareCreateTimes': this.form.shareCreateTimes,
        'promoteCondition': this.form.promoteCondition,
        'useDiscount': this.form.useDiscount,
        'useScore': this.form.useScore,
        'failedSendType': this.form.failedSendType,
        'failedSendContent': this.form.failedSendContent,
        'activityShareType': this.form.activityShareType,
        'customShareWord': this.form.customShareWord,
        'shareImgType': this.form.shareImgType,
        'customImgPath': this.srcList.src
      }
      this.$refs['form'].validate((valid) => {
        console.log('submit', this.form)
        if (valid) {
          if (this.promoteId !== 'null') {
            console.log('I am updating!')
            updateInfo(addParam).then(res => {
              console.log(res)
              if (res.error === 0) {
                alert(this.$t('promoteList.successUpdate'))
                this.$router.push({
                  name: 'promote'
                })
              }
            }).catch(() => {
              this.$message.error(this.$t('promoteList.operationFailed'))
            })
          } else {
            console.log('I am adding!')
            addActive(addParam).then(res => {
              console.log(res)
              if (res.error === 0) {
                alert(this.$t('promoteList.successAdd'))
                this.$router.push({
                  name: 'promote'
                })
              }
            }).catch(() => {
              this.$message.error(this.$t('promoteList.operationFailed'))
            })
          }
        } else {
          this.$message.error(this.$t('promoteList.validCheck'))
          return false
        }
      })
    },
    // 活动分享 -- 添加图片点击事件，弹出图片选择组件
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    imgDialogSelectedCallback (src) {
      this.srcList.src = src.imgUrl
    },
    // 删除图片
    deleteGoodsImg () {
      this.srcList.src = `${this.$imageHost}/image/admin/add_img.png`
    },

    // 选择商品弹窗
    showChoosingGoods () {
      this.transmitEditGoodsId(this.form.goodsInfo.goodsIds)
      // console.log('初始化商品弹窗', this.form.rewardContent.goodsIds)
      this.$http.$emit('choosingGoodsFlag', true, 'choiseOne')
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    //  获取商品ids
    choosingGoodsResult (row) {
      console.log('获取商品行', row)
      this.goodsRow = row
      this.form.goodsInfo = []
      this.form.goodsInfo.push(row)
      this.goodsIdList = []
      this.goodsIdList.push(row.goodsId)
      console.log('goodsInfo:', this.form.goodsInfo[0])
      this.form.rewardSet.goods_ids = row.goodsId

      // })
    },
    // 选择优惠券弹窗
    handleToCallDialog (val) {
      switch (val) {
        case 1: {
          console.log(this.couponDialogFlag)
          console.log(this.coupon_info)
          this.couponFlag = 1
          let obj = {
            couponDialogFlag: !this.couponDialogFlag,
            couponList: this.coupon_info
          }
          this.$http.$emit('V-AddCoupon', obj)
          this.showCouponDialog = !this.showCouponDialog
        }
          break
        case 2: {
          this.couponFlag = 2
          let obj = {
            couponDialogFlag: !this.couponDialogFlag1,
            couponList: this.coupon_duplicate
          }
          this.$http.$emit('V-AddCoupon', obj)
          this.showCouponDialog = !this.showCouponDialog
        }
      }
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data) {
      console.log(data)
      console.log('couponInfo:', data)
      if (this.couponFlag === 1) {
        this.form.rewardSet.reward_ids = data[0].id
        this.coupon_info = data
        console.log(this.coupon_info)
      } else {
        this.form.failedSendContent = data[0].id
        this.coupon_duplicate = data
        console.log(this.coupon_duplicate)
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.coupon_info {
  display: flex;
  flex-direction: column;
  width: 200%;
  line-height: 25px;
  margin: 0 auto;
  margin-bottom: -10px;
  .coupon_rule {
    color: #999;
    font-size: 14px;
  }
  .coupon_name {
    font-weight: bold;
    font-size: 14px;
    margin-bottom: 5px;
    width: 100%;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }
}
.actWrapper {
  position: relative;
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  padding-bottom: 60px;

  .content {
    position: relative;
    // overflow-y: auto;
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
        width: 200px;
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

    .footer {
      position: fixed;
      bottom: 0;
      right: 27px;
      left: 160px;
      height: 52px;
      padding: 10px 0;
      background: #fff;
      text-align: center;
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
.addInfo {
  display: inline-block;
  position: relative;
  width: 100px;
  height: 101px;
  margin-bottom: 10px;
  background: #fff;
  border: 1px solid #e4e4e4;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  line-height: normal;
  img {
    margin-top: 10px;
  }
  p {
    line-height: normal;
    margin-top: -20px;
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
