
<template>
  <wrapper>
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
                type="primary"
                @click="showChoosingGoods"
              >+ {{$t('promoteList.chooseGoods')}}</el-button>
            </el-col>
            <el-col v-if="form.rewardType==2">
              <el-button
                size="small"
                type="primary"
                @click="isEditFlag?'':handleToCallDialog(1)"
              >+ {{$t('promoteList.chooseCoupons')}}</el-button>
            </el-col>
            <div></div>
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
              >
              </el-table-column>
              <el-table-column
                prop="shopPrice"
                :label="$t('promoteList.goodsPrice')"
                align="center"
              >
              </el-table-column>

              <el-table-column
                prop="goodsNumber"
                :label="$t('promoteList.goodsStore')"
                align="center"
              >
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
              style="width: 230px;"
            >
              <el-table-column
                :label="$t('promoteList.couponInfo')"
                align="center"
              >
                <template slot-scope="scope">
                  <div class="coupon_info">
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
              <span>用户在</span>
              <el-input
                style="margin: 0 5px"
                size="small"
                v-model="form.launchLimitDuration"
              ></el-input>
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
              <span>内最多可发起</span>
              <el-input
                size="small"
                style="margin:0 5px"
                v-model="form.launchLimitTimes"
              ></el-input>次
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
              <span>好友可通过分享获得最多</span>
              <el-input
                style="margin:0 5px"
                size="small"
                v-model="form.shareCreateTimes"
              ></el-input>
              <span>次助力机会</span>
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
              style="width: 80px;height:80px;border:1px solid #000"
              @click="isEditFlag?'':handleToCallDialog(2)"
            >
              <template slot-scope="coupon">
                <div class="coupon_info">
                  <span class="coupon_name">{{coupon.row.actName}}</span>
                  <div
                    v-if="coupon.row.actCode == 'voucher'"
                    style="color:red"
                  >￥<span>{{coupon.row.denomination}}</span></div>
                  <div v-else><span>{{coupon.row.denomination}}</span>折</div>
                  <div class="coupon_rule">{{coupon.row.useConsumeRestrict > 0? `满${coupon.row.leastConsume}元可用`  : `不限制`}}</div>
                </div>
              </template>
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
                  <span>{{$t('promoteList.sharePreview')}}</span>
                  <span>{{$t('promoteList.posterPreview')}}</span>
                </div>
                <div>
                  <el-radio
                    v-model="form.activityShareType"
                    label="1"
                  >
                    {{$t('promoteList.customStyle')}}
                    <div v-if="form.activityShareType == 1">
                      <div style="margin: 15px 0">
                        <span>{{$t('promoteList.words')}}</span>
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
                        >{{$t('promoteList.goodsPicture')}}</el-radio>
                        <div style="margin: 10px 0 0 60px">
                          <el-radio
                            v-model="form.shareImgType"
                            label="1"
                          >{{$t('promoteList.customPicture')}}</el-radio>

                          <div
                            style="display: flex;align-items: center;flex-wrap: wrap;"
                            v-if="form.shareImgType == 1"
                          >
                            <div
                              v-for="(src,index) in goodsProductInfo.goodsImgs"
                              :key="index"
                              class="goodsImgWrap"
                            >
                              <el-image
                                fit="cover"
                                :src="src"
                                style="width: 78px; height: 78px;"
                              ></el-image>
                              <span
                                @click="deleteGoodsImg(index)"
                                class="deleteIcon"
                              >×</span>

                            </div>
                            <div
                              class="goodsImgWrap"
                              @click="addGoodsImg"
                              v-if="goodsProductInfo.goodsImgs.length < 1"
                            >
                              <el-image
                                fit="scale-down"
                                :src="imgHost+'/image/admin/add_img.png'"
                                style="width: 78px; height: 78px;cursor: pointer;"
                              />
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
    <choosingGoods @resultGoodsRow="choosingGoodsResult">
    </choosingGoods>
    <!--奖励类型-添加优惠卷-->
    <AddCouponDialog
      singleElection="true"
      @handleToCheck="handleToCheck"
    />
    <!--失败赠送-添加优惠卷-->
    <!-- <AddCouponDialog
      singleElection="true"
      @handleToCheck="handleToCheck2"
    /> -->
    <ImageDalog
      pageIndex='pictureSpace'
      @handleSelectImg='imgDialogSelectedCallback'
    />
  </wrapper>
</template>

<script>
import { mapActions } from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
import choosingGoods from '@/components/admin/choosingGoods'
import { addActive, selectOneInfo, updateInfo } from '@/api/admin/marketManage/friendHelp.js'
import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
import { selectGoodsApi } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods.js'
import { getGoodsProductList } from '@/api/admin/brandManagement.js'
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: {
    wrapper,
    choosingGoods,
    ImageDalog,
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  data () {
    return {
      couponFlag: null,
      promoteId: '',
      show: false,
      radio: 'one',
      isEditFlag: false,
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
        shareCreateTimes: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ]
      }
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
    // console.log(222)
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
        this.form.customImgPath = res.content[0].customImgPath

        if (this.form.rewardType === '2') {
          this.form.rewardSet.market_store = JSON.parse(res.content[0].rewardContent.slice(1, -1)).market_store
          console.log(this.form.rewardSet.market_store)
          this.form.rewardSet.reward_ids = JSON.parse(res.content[0].rewardContent.slice(1, -1)).reward_ids
          console.log(this.form.rewardSet.reward_ids)
          updateCoupon(this.form.rewardSet.reward_ids).then(res => {
            this.coupon_info = res.content
            console.log('couponInfo:', this.coupon_info)
          })
        }
        if (this.form.rewardType === '0') {
          this.form.rewardSet.market_store = JSON.parse(res.content[0].rewardContent.slice(1, -1)).market_store
          console.log(this.form.rewardSet.market_store)
          this.form.rewardSet.goods_ids = JSON.parse(res.content[0].rewardContent.slice(1, -1)).goods_ids
          console.log(this.form.rewardSet.goods_ids)
          let goodsIdParam = {
            'goodsId': this.form.rewardSet.goods_ids
          }
          selectGoodsApi(goodsIdParam).then(res => {
            console.log('goodsInfo:', res)
          })
        }
      })
    },
    addAct () {
      console.log('this.form.rewardType:', this.form.rewardType)
      if (this.form.rewardType === 0 || this.form.rewardType === 1) {
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
      if (this.form.rewardType === 2) {
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
        'shareImgType': this.form.shareImgType
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
    /* 添加图片点击事件，弹出图片选择组件 */
    addGoodsImg () {
      this.$http.$emit('dtVisible')
    },
    /* 商品图片点击回调函数 */
    imgDialogSelectedCallback (src) {
      if (this.goodsProductInfo.goodsImgs.length >= 1) {
        return
      }
      this.goodsProductInfo.goodsImgs.push(src)
    },
    /* 删除商品图片 */
    deleteGoodsImg (index) {
      this.goodsProductInfo.goodsImgs.splice(index, 1)
    },

    // 选择商品弹窗
    showChoosingGoods () {
      this.transmitEditGoodsId(this.form.goodsInfo.goodsIds)
      // console.log('初始化商品弹窗', this.form.rewardContent.goodsIds)
      this.$http.$emit('choosingGoodsFlag', true, 'choiseOne')
    },
    //  获取商品ids
    choosingGoodsResult (row) {
      console.log('获取商品行', row)
      this.goodsRow = row
      this.form.goodsInfo.goodsIds = row.goodsId
      this.form.rewardSet.goods_ids = row.goodsId
      // 初始化规格表格
      let obj = {
        goodsId: this.form.goodsInfo.goodsIds,
        currentPage: 1,
        pageRows: 1024
      }
      getGoodsProductList(obj).then(res => {
        const { content } = res
        const { dataList } = content

        this.form.goodsInfo = [dataList[obj.goodsId]]
        console.log('goodsInfo:', this.form.goodsInfo)
      })
    },
    // 奖励类型 - 选择优惠券弹窗
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
        }
          break
        case 2: {
          this.couponFlag = 2
          let obj = {
            couponDialogFlag: !this.couponDialogFlag1,
            couponList: this.coupon_duplicate
          }
          this.$http.$emit('V-AddCoupon', obj)
        }
      }
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data) {
      console.log(data)
      console.log('couponInfo:', data)
      if (this.couponFlag === 1) {
        this.form.rewardSet.reward_ids = data[0].id
        // console.log('data[0].id', data[0].id)
        let couponArr = this.formatCoupon(data)
        let oldArr = this.unique([...this.coupon_info, ...couponArr], 'id')
        let couponKey = []
        couponArr.map((item) => {
          couponKey.push(item.id)
        })

        this.coupon_info = oldArr.filter((item) => {
          return couponKey.includes(item.id)
        })
      } else {
        this.handleToCheck2()
      }

      console.log(this.coupon_info)
    },
    // 确认选择优惠券-新增-删除
    handleToCheck2 (data) {
      console.log('couponInfo:', data)
      this.form.failedSendContent = data[0].id
      // console.log('data[0].id', data[0].id)
      let couponArr = this.formatCoupon(data)
      let oldArr = this.unique([...this.coupon_duplicate, ...couponArr], 'id')
      let couponKey = []
      couponArr.map((item) => {
        couponKey.push(item.id)
      })
      this.coupon_duplicate = oldArr.filter((item) => {
        return couponKey.includes(item.id)
      })
      // console.log(this.coupon_duplicate)
    },
    // 添加优惠券初始项
    formatCoupon (data) {
      let arry = []
      let couponData = {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      }
      data.map(item => {
        arry.push(Object.assign({}, item, { send_num: '', coupon_set: couponData }))
      })
      console.log(arry)
      return arry
    },
    // 设置优惠券内容弹窗
    handleCouponSet (scope) {
      let target = this.coupon_info[scope.$index]
      this.coupon_set = JSON.parse(JSON.stringify(target.coupon_set))
      this.couponSetDialogFlag = true
      this.target = scope.$index
    },
    // 确认设置优惠券
    confrimCouponSet () {
      this.coupon_info[this.target].coupon_set = JSON.parse(JSON.stringify(this.coupon_set))
      this.couponSetDialogFlag = false
    },
    // 同id去重
    unique (arr, key) {
      let map = new Map()
      arr.forEach((item, index) => {
        if (!map.has(item[key])) {
          map.set(item[key], item)
        }
      })
      return [...map.values()]
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
  width: 97%;
  margin: 0 auto;
  margin-bottom: -10px;
  .coupon_item {
    margin-bottom: 10px;
    height: 100px;
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
}
.goodsImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
}
.goodsImgWrap .deleteIcon {
  width: 17px;
  height: 17px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 17px;
  text-align: center;
  position: absolute;
  top: -8px;
  right: -8px;
  cursor: pointer;
  opacity: 0.8;
}
.goodsImgWrap .moveIcon {
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
.goodsImgWrap:hover .moveIcon {
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
  text-align: center;
  background: #f8f8f8;
  margin-top: 10px;
  position: fixed;
  bottom: 0;
  z-index: 1;
  width: 100%;
}
</style>
