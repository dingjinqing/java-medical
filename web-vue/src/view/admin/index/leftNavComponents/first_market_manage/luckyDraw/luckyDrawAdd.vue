<template>
  <div class="luckyDrawAdd">
    <el-card>
      <div class="mainContent">
        <div class="mainContentLeft">
          <section class="drawContent">
            <div
              class="top"
              :style="`background-image: url(${$imageHost}/image/admin/shop_beautify/phone_tops.png)`"
            >
              <div>{{$t('luckyDraw.luckyWhee')}}</div>
            </div>
            <div class="drawArea">
              <div
                class="loTop"
                :style="`background-image: url(${$imageHost}/image/admin/icon_lottery/lo_bg3.jpg)`"
              >
                <div class="loArea">
                  <div class="picBox">
                    <ul class="picWrapper">
                      <li class="picItems">
                        <img
                          :src="$imageHost + requestParam.prizeList[3].iconImgsImage "
                          alt=""
                        >
                        <div>{{requestParam.prizeList[3].iconImgs}}</div>
                      </li>
                      <li class="picItems">
                        <img
                          :src="$imageHost+requestParam.noAwardImage"
                          alt=""
                        >
                        <div>{{requestParam.noAwardIcon}}</div>
                      </li>
                      <li class="picItems">
                        <img
                          :src="$imageHost + requestParam.prizeList[2].iconImgsImage "
                          alt=""
                        >
                        <div>{{requestParam.prizeList[2].iconImgs}}</div>
                      </li>
                      <li class="picItems">
                        <img
                          :src="$imageHost + requestParam.prizeList[0].iconImgsImage "
                          alt=""
                        >
                        <div>{{requestParam.prizeList[0].iconImgs}}</div>
                      </li>
                      <li class="picItems">
                        <img
                          :src="$imageHost + requestParam.prizeList[1].iconImgsImage "
                          alt=""
                        >
                        <div>{{requestParam.prizeList[1].iconImgs}}</div>
                      </li>
                      <li class="picItems">
                        <img
                          :src="$imageHost + requestParam.prizeList[3].iconImgsImage "
                          alt=""
                        >
                        <div>{{requestParam.prizeList[3].iconImgs}}</div>
                      </li>
                      <li class="picItems">
                        <img
                          :src="$imageHost + requestParam.prizeList[2].iconImgsImage "
                          alt=""
                        >
                        <div>{{requestParam.prizeList[2].iconImgs}}</div>
                      </li>
                      <li class="picItems">
                        <img
                          :src="$imageHost + requestParam.prizeList[3].iconImgsImage "
                          alt=""
                        >
                        <div>{{requestParam.prizeList[3].iconImgs}}</div>
                      </li>
                      <li class="picItems">
                        <img
                          :src="$imageHost + requestParam.prizeList[1].iconImgsImage "
                          alt=""
                        >
                        <div>{{requestParam.prizeList[1].iconImgs}}</div>
                      </li>
                    </ul>
                  </div>
                  <div class="winningTips">
                    <img
                      src="http://mpdevimg2.weipubao.cn/image/admin/icon_lottery/lo_words.png"
                      alt=""
                    >
                    张三{{$t('luckyDraw.get')}}
                    <span>{{requestParam.prizeList[0].iconImgs}}{{$t('luckyDraw.publicityInfo')}}
                    </span>
                  </div>
                </div>
              </div>
              <div class="immediatelyDraw">
                <div class="drawText">{{$t('luckyDraw.immediatelyDraw')}}</div>
              </div>
              <div class="actRule">
                <div class="ruleInfo">
                  <img
                    src="http://mpdevimg2.weipubao.cn/image/admin/icon_lottery/lo_rule_l.png"
                    alt=""
                  >
                  <span>{{$t('luckyDraw.activityRules')}}</span>
                  <img
                    src="http://mpdevimg2.weipubao.cn/image/admin/icon_lottery/lo_rule_r.png"
                    alt=""
                  >
                </div>
                <div class="ruleContent">
                  <div class="timeRange">{{$t('luckyDraw.activityValidity')}}：</div>
                  <div>{{$t('luckyDraw.to')}}：</div>
                  <div>{{$t('luckyDraw.activityDescription')}}：</div>
                </div>
              </div>
            </div>
          </section>
        </div>

        <div class="mainContentRight">
          <el-form
            label-width="125px"
            ref="form"
            :model="requestParam"
            :rules="formRules"
          >
            <!-- 活动信息 -->
            <div class="boxWrapper">
              <div class="textDesc">{{$t('luckyDraw.activityInformation')}}</div>

              <el-form-item
                :label="$t('luckyDraw.activityName')+'：'"
                prop="lotteryName"
              >
                <el-input
                  size="small"
                  v-model="requestParam.lotteryName"
                  :placeholder="$t('luckyDraw.supportUpTo10Words')"
                  style="width: 180px;"
                ></el-input>
              </el-form-item>

              <el-form-item
                :label="$t('luckyDraw.activityValidity')+'：'"
                required
              >
                <el-form-item
                  prop="startTime"
                  :inline-message="true"
                >
                  <span>{{$t('luckyDraw.effectiveTime')}}：</span>
                  <el-date-picker
                    v-model="requestParam.startTime"
                    type="datetime"
                    :placeholder="$t('luckyDraw.selectDateTime')"
                    size="small"
                    style="width: 185px"
                    value-format="yyyy-MM-dd HH:mm:ss"
                  >
                  </el-date-picker>
                </el-form-item>
                <el-form-item
                  prop="endTime"
                  :inline-message="true"
                >
                  <span>{{$t('luckyDraw.expirationTime')}}：</span>
                  <el-date-picker
                    v-model="requestParam.endTime"
                    type="datetime"
                    :placeholder="$t('luckyDraw.selectDateTime')"
                    size="small"
                    style="width: 185px"
                    default-time="23:59:59"
                    value-format="yyyy-MM-dd HH:mm:ss"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-form-item>

              <el-form-item
                :label="$t('luckyDraw.activityDescription')+'：'"
                prop="lotteryExplain"
              >
                <el-input
                  v-model="requestParam.lotteryExplain"
                  type="textarea"
                  :rows="4"
                  :placeholder="$t('luckyDraw.pleaseEnterContent')"
                  style="width: 300px;height: 100px;"
                ></el-input>
              </el-form-item>

            </div>

            <!-- 规则设置 -->
            <div class="boxWrapper">
              <div class="textDesc">{{$t('luckyDraw.rulesSetting')}}</div>
              <!--                            <el-form-item-->
              <!--                                    label="活动规则："-->
              <!--                                    prop=""-->
              <!--                            >-->
              <!--                                <el-radio-group v-model="times">-->
              <!--                                    <el-radio :label="1">每人N次</el-radio>-->
              <!--                                    <el-radio :label="2">每人每天N次</el-radio>-->
              <!--                                </el-radio-group>-->
              <!--                            </el-form-item>-->

              <el-form-item :label="$t('luckyDraw.freePrizeDraw')+'：'">
                <el-input
                  size="small"
                  :placeholder="$t('luckyDraw.nullUnrestricted')"
                  style="width:125px"
                  v-model="requestParam.freeChances"
                ></el-input>
                <span style="color:#999;">{{$t('luckyDraw.freePizeTimes')}}</span>
              </el-form-item>

              <el-form-item :label="$t('luckyDraw.shareTheLuckyDraw')+'：'">
                <el-radio-group v-model="requestParam.canShare">
                  <el-radio :label="1">{{$t('luckyDraw.allow')}}</el-radio>
                  <el-radio :label="2">{{$t('luckyDraw.notAllow')}}</el-radio>
                </el-radio-group>
                <div v-if="requestParam.canShare === 1">
                  <span style="color: #999">{{$t('luckyDraw.shareTheLuckyDrawTips')}}</span>
                  <div>
                    {{$t('luckyDraw.shareTheLuckyDrawTips1')}}
                    <el-input
                      size="small"
                      :placeholder="$t('luckyDraw.nullUnrestricted')"
                      style="width:125px"
                      v-model="requestParam.shareChances"
                    ></el-input>
                    {{$t('luckyDraw.shareTheLuckyDrawTips2')}}
                  </div>
                </div>
              </el-form-item>

              <el-form-item :label="$t('luckyDraw.payLuckyDraw')+'：'">
                <el-radio-group v-model="requestParam.canUseScore">
                  <el-radio :label="1">{{$t('luckyDraw.allow')}}</el-radio>
                  <el-radio :label="2">{{$t('luckyDraw.notAllow')}}</el-radio>
                </el-radio-group>
                <div v-if="requestParam.canUseScore===1">
                  <span style="color: #999;">{{$t('luckyDraw.payLuckyDrawTips1')}}</span>
                  <div>
                    {{$t('luckyDraw.payLuckyDrawTips2')}}：
                    <el-input
                      size="small"
                      placeholder="为空表示不消耗积分"
                      style="width:125px"
                      v-model="requestParam.scorePerChance"
                    ></el-input>
                    <span style="color:#999">{{$t('luckyDraw.payLuckyDrawTips3')}}</span>
                  </div>
                  <div>
                    {{$t('luckyDraw.payLuckyDrawTips4')}}
                    <el-input
                      size="small"
                      :placeholder="$t('luckyDraw.nullUnrestricted')"
                      style="width:125px"
                      v-model="requestParam.scoreChances"
                    ></el-input>
                    {{$t('luckyDraw.payLuckyDrawTips5')}}
                  </div>
                </div>
              </el-form-item>

              <el-form-item :label="$t('luckyDraw.noWinningBonusPoints')+'：'">
                <el-input
                  size="small"
                  :placeholder="$t('luckyDraw.nullIsNoPoints')"
                  style="width: 185px"
                  v-model="requestParam.noAwardScore"
                ></el-input>
                <span style="color: #999">{{$t('luckyDraw.noWinningBonusPoints')}}</span>
                <section class="upInfo">
                  <div class="upIcons">
                    <div class="leftContent">
                      <img
                        :src="this.$imageHost + this.requestParam.noAwardImage"
                        alt=""
                      >
                    </div>
                    <div class="rightContent">
                      <div class="operate">
                        <span @click="showLotteryDialog(1)">{{$t('luckyDraw.modification')}}
                        </span>
                        <span
                          class="operateBtn fixstyle"
                          @click="changeImgHandler(1)"
                        >
                          {{$t('luckyDraw.uploadChartForNoWinning')}}
                        </span>
                        <span class="operateBtn">{{$t('luckyDraw.empty')}}</span>
                      </div>
                      <p>{{$t('luckyDraw.uploadChartForNoWinningTips')}}</p>
                    </div>
                  </div>
                  <div class="iconDesc">
                    <span>{{$t('luckyDraw.iconDescribe')}}：</span>
                    <el-input
                      size="small"
                      :placeholder="$t('luckyDraw.iconDescribeTips')"
                      style="width: 180px"
                      v-model="requestParam.noAwardIcon"
                    ></el-input>
                  </div>
                </section>
              </el-form-item>
            </div>

            <!-- 抽奖设置 -->
            <div class="boxWrapper">
              <div class="textDesc">{{$t('luckyDraw.lotteryIsSet')}}</div>
              <p class="drawSetting">{{$t('luckyDraw.lotteryIsSetTips1')}}</p>
              <p class="drawSetting">{{$t('luckyDraw.lotteryIsSetTips2')}}</p>
              <!-- 一等奖~四等奖 tab切换 -->
              <el-tabs
                type="border-card"
                class="tabs"
                v-model="tabSwitch"
                @tab-click="handleTabClick"
              >
                <el-tab-pane
                  v-for="(item,index) in requestParam.prizeList"
                  :key="index"
                  :label="item.iconImgs"
                  :name="item.lotteryGrade"
                >
                  <el-form-item
                    :label="$t('luckyDraw.prizeRate')+':'"
                    label-width="80px"
                  >
                    <el-input
                      size="small"
                      style="width:120px"
                      v-model="item.chance"
                      @change="prizeRateChange(item.chance)"
                    ></el-input>&nbsp;&nbsp;%
                  </el-form-item>

                  <el-form-item
                    :label="$t('luckyDraw.selectPrize')+':'"
                    label-width="80px"
                  >
                    <el-radio-group
                      v-model="item.lotteryType"
                      @change="lotteryTypeChange"
                    >
                      <el-radio :label="1">{{$t('luckyDraw.prizeType')[0][1]}}</el-radio>
                      <el-radio :label="2">{{$t('luckyDraw.prizeType')[1][1]}}</el-radio>
                      <el-radio :label="3">{{$t('luckyDraw.prizeType')[2][1]}}</el-radio>
                      <el-radio :label="4">{{$t('luckyDraw.prizeType')[3][1]}}</el-radio>
                      <el-radio :label="5">{{$t('luckyDraw.prizeType')[4][1]}}</el-radio>
                    </el-radio-group>
                    <section>
                      <el-form-item
                        v-if="item.lotteryType===1"
                        required
                        :prop="`prizeList[${index}].integralScore`"
                        :rules="{ required: true, message: $t('luckyDraw.validScore'), trigger: 'blur' }"
                        :inline-message="true"
                      >
                        <span>{{$t('luckyDraw.presentExp')}}：</span>
                        <el-input
                          size="small"
                          :placeholder="$t('luckyDraw.presentExpTips')"
                          style="width: 120px"
                          v-model="item.integralScore"
                        ></el-input>
                      </el-form-item>
                      <el-form-item
                        v-if="item.lotteryType===2"
                        :prop="`prizeList[${index}].integralScore`"
                        :rules="{ required: true, message: $t('luckyDraw.validBounsAmount'), trigger: 'blur' }"
                        :inline-message="true"
                      >
                        <span>{{$t('luckyDraw.giveTheBalance')}}：</span>
                        <el-input
                          size="small"
                          :placeholder="$t('luckyDraw.giveTheBalanceTips')"
                          style="width: 120px"
                          v-model="item.integralScore"
                        ></el-input>
                      </el-form-item>
                      <el-form-item
                        v-if="item.lotteryType===3"
                        :prop="`prizeList[${index}].couponId`"
                        :rules="{ required: true, message: $t('luckyDraw.validCoupon'), trigger: 'blur' }"
                        :inline-message="true"
                      >
                        <span>{{$t('luckyDraw.coupons')}}：</span>
                        <el-select
                          v-model="item.couponId"
                          size="small"
                          style="width: 120px"
                          @change="couponChange"
                        >
                          <el-option
                            v-for="itema in couponlist"
                            :key="itema.id"
                            :value="itema.id"
                            :label="itema.actName"
                          ></el-option>
                        </el-select>
                        <span
                          style="margin-right: 10px;color: #5a8bff;"
                          @click="refreshCouponList()"
                        >{{$t('luckyDraw.refreshs')}}
                        </span>
                        |
                        <span
                          style="margin-left: 10px;color: #5a8bff;"
                          @click="createCouponList()"
                        >{{$t('luckyDraw.make')}}
                        </span>
                        <p style="color: #999;">
                          {{$t('luckyDraw.couponsTips',[couponNumber])}}</p>
                      </el-form-item>
                      <el-form-item
                        v-if="item.lotteryType===4"
                        :prop="`prizeList[${index}].goodsName`"
                        :rules="{ required: true, message: $t('luckyDraw.validGivea'), trigger: 'blur' }"
                        :inline-message="true"
                      >
                        <div>
                          <span>{{$t('luckyDraw.givinggifts')}}：</span>
                          <el-button
                            size="small"
                            @click="showGoodsDialog"
                          > + {{$t('luckyDraw.selectGoods')}}
                          </el-button>
                        </div>
                        <div v-if="requestParam.prizeList[tabSwitch-1].goodsShow">
                          <table
                            class="goods_modal"
                            style="display: block;"
                          >
                            <thead>
                              <tr style="background: #F8F8F8;">
                                <th>{{$t('luckyDraw.goodsName')}}</th>
                                <th>{{$t('luckyDraw.goodsPrice')}}</th>
                                <th>{{$t('luckyDraw.goodsNum')}}</th>
                                <th>{{$t('luckyDraw.goodsOptions')}}</th>
                              </tr>
                            </thead>
                            <tbody class="tbody">
                              <tr>
                                <td>
                                  <div class="goods_img"><img :src="requestParam.prizeList[tabSwitch-1].goodsImage">
                                  </div>
                                  <div
                                    class="goods_info clearfix"
                                    num="50"
                                    prd_id="5410"
                                  >
                                    <div class="goods_name">
                                      {{requestParam.prizeList[tabSwitch-1].goodsName}}
                                    </div>
                                  </div>
                                </td>
                                <td>
                                  {{currency[1]}}{{requestParam.prizeList[tabSwitch-1].goodsPrice}}
                                </td>
                                <td>
                                  {{requestParam.prizeList[tabSwitch-1].goodsNumber}}
                                </td>
                                <td>
                                  <a
                                    href="##"
                                    item="5410"
                                    class="change_goods_del"
                                  >
                                    {{$t('luckyDraw.goodsRemove')}}
                                  </a>
                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                        <div>
                          {{$t('luckyDraw.giftValidity')}}：
                          <el-input
                            size="small"
                            style="width:100px"
                            v-model="item.prdKeepDays"
                          ></el-input>
                          {{$t('luckyDraw.giftValidityTips1')}}
                          <p style="margin-left:80px;color:#999;">
                            {{$t('luckyDraw.giftValidityTips2')}}</p>
                        </div>
                      </el-form-item>
                      <el-form-item v-if="item.lotteryType===5">
                        <span>{{$t('luckyDraw.custom')}}：</span>
                        <el-input
                          size="small"
                          :placeholder="$t('luckyDraw.customTips')"
                          style="width: 150px"
                          v-model="item.lotteryDetail"
                        ></el-input>
                      </el-form-item>
                      <el-form-item>
                        <span>{{$t('luckyDraw.prizeNumber')}}：</span>
                        <el-input
                          size="small"
                          :placeholder="$t('luckyDraw.prizeNumberTips1')"
                          style="width: 120px"
                          v-model="item.lotteryNumber"
                        ></el-input>&nbsp;{{$t('luckyDraw.prizeNumberTips2')}}
                        <div class="numberTips">{{$t('luckyDraw.prizeNumberTips3')}}</div>
                        <div class="numberTips bottomTips">
                          {{$t('luckyDraw.prizeNumberTips4')}}
                        </div>
                      </el-form-item>

                    </section>

                  </el-form-item>
                </el-tab-pane>
              </el-tabs>

              <!-- 一等奖~四等奖 icon图表及其文字设置 -->
              <div class="levelBoxSetting">
                <section class="upInfo levelUpInfo">
                  <div class="upIcons">
                    <div class="leftContent">
                      <img
                        :src="this.$imageHost + this.requestParam.prizeList[this.tabSwitch - 1].iconImgsImage "
                        alt=""
                      >
                    </div>
                    <div class="rightContent">
                      <div class="operate">
                        <span @click="showLotteryDialog(2)">{{$t('luckyDraw.modification')}}</span>
                        <span
                          class="operateBtn fixstyle"
                          @click="changeImgHandler(2)"
                        >
                          {{$t('luckyDraw.uploadChartForWinning')}}
                        </span>
                        <span
                          class="operateBtn"
                          @click="handleClear()"
                        >{{$t('luckyDraw.empty')}}
                        </span>
                      </div>
                      <p style="margin-top:10px;">{{$t('luckyDraw.uploadChartForNoWinningTips')}}</p>
                    </div>
                  </div>
                  <div class="iconDesc">
                    <span>{{$t('luckyDraw.iconDescribe')}}：</span>
                    <el-input
                      size="small"
                      :placeholder="$t('luckyDraw.iconDescribeTips')"
                      style="width: 180px"
                      v-model="requestParam.prizeList[tabSwitch-1].iconImgs"
                    ></el-input>
                  </div>
                </section>
              </div>

            </div>
          </el-form>
        </div>
      </div>

      <div class="save">
        <el-button
          size="small"
          type="primary"
          @click="submitData"
        >{{$t('luckyDraw.save')}}
        </el-button>
      </div>
      <!--添加商品弹窗-->
      <choosingGoods
        @resultGoodsRow="choosingGoodsResult"
        :chooseGoodsBack="[requestParam.prizeList[tabSwitch-1].prdId]"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :singleElection="true"
        :showTips="true"
        :loadProduct="true"
      />
      <!--图片dialog-->
      <ImageDalog
        :tuneUp="selfImgDialogShow"
        pageIndex="pictureSpace"
        :imageSize="imageSize"
        :isDraggable='false'
        @handleSelectImg='imgDialogSelectedCallback'
      />
      <lotteryImageDialog
        :tuneUp="lotteryImgDialogShow"
        @handleSelectImg='imgDialogSelectedCallback'
      />

    </el-card>
  </div>
</template>

<script>
import choosingGoods from '@/components/admin/choosingGoods'
import ImageDalog from '@/components/admin/imageDalog'
import lotteryImageDialog from '@/components/admin/lotteryImageDialog'
import { addLottery, editLottery, getLottery } from '@/api/admin/marketManage/luckyDraw'
import { getCouponAll } from '@/api/admin/marketManage/couponList'

export default {
  name: `luckyDrawAdd`,
  // 是否是设置
  props: {
    id: {
      type: Number
    }, // 数据库选择
    isEdite: { // 调起弹窗
      type: Boolean,
      default: () => false
    }
  },
  components: {
    choosingGoods,
    ImageDalog,
    lotteryImageDialog
  },
  data () {
    let that = this
    function validTime (rule, value, callback) {
      if (new Date(that.requestParam.startTime) > new Date(that.requestParam.endTime)) {
        callback(new Error('结束时间必须大于开始时间'))
      }
      callback()
    }
    return {
      requestParam: {
        lotteryName: '',
        lotteryExplain: '',
        startTime: '',
        endTime: '',
        freeChances: 0,
        canShare: 0,
        shareChances: 0,
        canUseScore: 0,
        scorePerChance: 0,
        scoreChances: 0,
        noAwardScore: 0,
        noAwardIcon: this.$t('luckyDraw.thanksParticipation'),
        noAwardImage: '/image/admin/icon_lottery/thank.png',
        prizeList: [
          { iconImgs: this.$t('luckyDraw.firstPrize'), lotteryGrade: '1', iconImgsImage: '/image/admin/icon_lottery/1.png' },
          { iconImgs: this.$t('luckyDraw.secondPrize'), lotteryGrade: '2', iconImgsImage: '/image/admin/icon_lottery/2.png' },
          { iconImgs: this.$t('luckyDraw.thirdPrize'), lotteryGrade: '3', iconImgsImage: '/image/admin/icon_lottery/3.png' },
          { iconImgs: this.$t('luckyDraw.fourthPrize'), lotteryGrade: '4', iconImgsImage: '/image/admin/icon_lottery/4.png' }
        ]
      },
      times: 1,
      // 商品弹窗
      isShowChoosingGoodsDialog: false,
      // 图片弹窗
      selfImgDialogShow: false,
      lotteryImgDialogShow: false,
      // 提交状态
      submitStatus: false,
      // 图片弹窗标识
      imgDialogIndex: 0,
      imageSize: [80, 80],
      // 优惠劵列表
      couponlist: [],
      couponNumber: 5,
      tabSwitch: '1',
      imgHost: `${this.$imageHost}`,
      formRules: {
        lotteryName: [{ required: true, message: that.$t('luckyDraw.validName'), trigger: 'blur' }],
        startTime: [{ required: true, message: that.$t('luckyDraw.validStartTime'), trigger: 'blur' }],
        endTime: [
          { required: true, message: that.$t('luckyDraw.validEndTime'), trigger: 'blur' },
          { validator: validTime, trigger: 'blur' }
        ],
        lotteryExplain: [{ required: true, message: that.$t('luckyDraw.validDesc'), trigger: 'blur' }]
      }
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.refreshCouponList()
    this.isEditeShowData()
  },
  watch: {},
  methods: {
    lotteryTypeChange (val) {
      console.log(val)
      this.$refs.form.clearValidate()
    },
    // 保存
    submitData () {
      this.submitStatus = true
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log('this.requestParam', this.requestParam)
          if (this.isEdite) {
            editLottery(this.requestParam).then(res => {
              console.log('update', res)
              if (res.error === 0) {
                this.$message.success(res.message)
              } else {
                this.$message.warning(res.message)
              }
            }).catch(e => {
              console.log(e)
              this.$message.warning(e.message)
            })
          } else {
            addLottery(this.requestParam).then(res => {
              console.log('addLottery', res)
              if (res.error === 0) {
                this.$message.success(res.message)
              } else {
                this.$message.warning(res.message)
              }
            })
          }
        }
      })
      this.submitStatus = false
    },
    handleClear () {
      console.log(1111)
    },
    handleTabClick (tab, event) {
      console.log(tab, event)
    },
    // 显示商品弹窗
    showGoodsDialog (index) {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 放回商品信息
    choosingGoodsResult (res) {
      console.log(res)
      this.requestParam.prizeList[this.tabSwitch - 1].prdId = res.prdId
      this.requestParam.prizeList[this.tabSwitch - 1].goodsShow = true
      this.requestParam.prizeList[this.tabSwitch - 1].goodsName = res.goodsName
      this.requestParam.prizeList[this.tabSwitch - 1].goodsImage = res.goodsImg
      this.requestParam.prizeList[this.tabSwitch - 1].goodsPrice = res.prdPrice
      this.requestParam.prizeList[this.tabSwitch - 1].goodsNumber = res.prdNumber
      this.$forceUpdate()
    },
    // 显示图片弹窗
    changeImgHandler (index) {
      this.imgDialogIndex = index
      this.selfImgDialogShow = !this.selfImgDialogShow
    },
    showLotteryDialog (index) {
      this.imgDialogIndex = index
      this.lotteryImgDialogShow = !this.lotteryImgDialogShow
    },
    // 图片弹窗回调函数
    imgDialogSelectedCallback (image) {
      console.log('图片弹窗回调函数', image)
      if (this.imgDialogIndex === 1) {
        this.requestParam.noAwardImage = '/' + image.imgPath
      } else if (this.imgDialogIndex === 2) {
        // 上传的是中奖的图标
        this.requestParam.prizeList[this.tabSwitch - 1].iconImgsImage = '/' + image.imgPath
      }
      // 更新
      this.$forceUpdate()
    },
    // 中奖率
    prizeRateChange (prizeRate) {
      let den = 100
      let i = String(prizeRate).indexOf('.')
      if (i > 0) {
        i = String(prizeRate).length - i
      }
      while (--i > 0) {
        console.log('changePriae', prizeRate, String(prizeRate).indexOf('.'))
        prizeRate = prizeRate * 10
        den = den * 10
      }
      this.requestParam.prizeList[this.tabSwitch - 1].chanceDenominator = den
      this.requestParam.prizeList[this.tabSwitch - 1].chanceNumerator = Math.round(prizeRate.valueOf())
      // 更新
      this.$forceUpdate()
    },
    // 获取优惠价列表
    refreshCouponList () {
      getCouponAll({}).then(res => {
        console.log('getCouponlist', res)
        this.couponlist = res.content
      })
    },
    // 新建优惠券(跳转)
    createCouponList () {
      this.$router.push({ path: '/admin/home/main/addyCoupon', query: {} })
    },
    // 设置数据回显
    isEditeShowData () {
      console.log('isEditeShowData', this.isEdite)
      if (this.isEdite) {
        getLottery({ id: this.id }).then(res => {
          res.content.prizeList.map((item, index) => {
            item.lotteryGrade = item.lotteryGrade.toString()
            item.chance = 100 * item.chanceNumerator / item.chanceDenominator
          })
          this.requestParam = res.content
          console.log('数据回显', res, this.requestParam)
          this.$forceUpdate()
        })
      }
    },
    couponChange (id) {
      console.log('couponitem', id)
      if (!id) {
        this.couponNumber = 0
      } else {
        this.couponNumber = this.couponlist.filter(item => {
          return item.id === id
        })[0].surplus
      }
      console.log('this.couponNumber', this.couponNumber)
    }
  }
}
</script>

<style lang="scss" scoped>
.luckyDrawAdd {
  .mainContent {
    display: flex;
    justify-content: center;
    padding-bottom: 60px;

    .mainContentLeft {
      width: 344px;
      height: 720px;
      border: 1px solid #ccc;
      background: #eee;
      overflow-x: hidden;

      .top {
        height: 55px;
        width: 100%;
        background: url("$imageHost + '/image/admin/icon_lottery/lo_bg3.jpg")
          no-repeat;
        color: white;
        text-align: center;
        background-size: 100%;

        div {
          padding-top: 25px;
          font-size: 15px;
        }
      }

      .drawArea {
        width: 330px;
        background: #fff5ba;
        overflow-y: auto;
      }

      .loTop {
        height: 490px;
        width: 330px;
        background: url("$imageHost + '/image/admin/shop_beautify/phone_tops.png")
          no-repeat;
        background-size: 330px 405px;
        overflow-x: hidden;

        .loArea {
          width: 290px;
          // background: #fc6b58;
          border-radius: 10px;
          margin: 0 auto;
          padding: 12px 0;
          position: relative;
          margin-top: 130px;

          .picBox {
            width: 270px;
            padding: 5px;
            background: #ed503b;
            border-radius: 8px;
            margin: 0 auto;
            overflow: hidden;
            height: 290px;

            .picWrapper {
              padding: 0;
              overflow: hidden;
              margin-right: -10px;
              margin-bottom: -15px;
              margin-top: 0;

              .picItems {
                float: left;
                width: 80px;
                height: 80px;
                margin-right: 10px;
                margin-bottom: 15px;
                background: #fbe5e2;
                border-radius: 7px;
                box-shadow: 0 10px #ff9c80;
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
                img {
                  width: 40px;
                  height: 40px;
                  margin-bottom: 10px;
                }
              }
            }
          }

          .winningTips {
            width: 270px;
            height: 30px;
            padding: 0 10px;
            border-radius: 10px;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            background: #ff9c80;
            color: #fff;
            margin: 0 auto;
            margin-top: 10px;

            img {
              width: 15px;
              margin-right: 15px;
            }

            span {
              margin-left: 10px;
              color: #ffe61e;
            }
          }
        }
      }

      .immediatelyDraw {
        width: 290px;
        height: 40px;
        position: relative;
        box-shadow: 0 5px 5px #fdc247;
        margin: 10px auto;
        border-radius: 20px;

        .drawText {
          position: absolute;
          top: 0;
          left: 0;
          width: 290px;
          height: 40px;
          background: linear-gradient(top, #feb83c, #fccc52);
          color: #fff;
          text-align: center;
          line-height: 40px;
          border-radius: 20px;
          font-size: 14px;
        }
      }

      .actRule {
        width: 290px;
        background: #feec99;
        color: #e89a0f;
        border-radius: 10px;
        padding: 15px 17px;
        margin: 10px auto;
        margin-top: 20px;

        .ruleInfo {
          text-align: center;
          margin-bottom: 10px;
        }

        .ruleContent {
          div {
            margin-bottom: 10px;
          }
        }
      }
    }

    .mainContentRight {
      margin-left: 30px;
      width: 630px;

      .boxWrapper {
        border: 1px solid #e5e5e5;
        background: #f8f8f8;
        padding: 0 12px 22px;
        border-radius: 3px;
        margin-bottom: 10px;

        .numberTips {
          width: 375px;
          line-height: 20px;
          color: #999;
          margin-left: 75px;
          margin-top: 5px;
        }

        .textDesc {
          line-height: 40px;
          border-bottom: 1px solid #e5e5e5;
        }

        .upInfo {
          width: 350px;
          border: 1px solid #c0c4cc;
          background: #fff;
          border-radius: 3px;
          padding: 10px 12px;

          .upIcons {
            display: flex;
            padding-bottom: 10px;
            border-bottom: 1px solid #c0c4cc;
            margin-bottom: 10px;

            .leftContent {
              width: 70px;
              height: 70px;
              border: 1px solid #c0c4cc;

              img {
                width: 70px;
                height: 70px;
              }
            }

            .rightContent {
              height: 70px;
              background: #fff;

              .operate {
                display: flex;
                margin-left: 10px;
                height: 30px;
                line-height: 30px;

                :nth-of-type(1) {
                  color: #5a8bff;
                  cursor: pointer;
                }

                .operateBtn {
                  display: block;
                  height: 30px;
                  padding: 0 10px;
                  background: #f5f5f5;
                  border: 1px solid #ccc;
                  cursor: pointer;
                }

                :hover {
                  color: #5a8bff;
                  background-color: #fff;
                  border-color: #5a8bff;
                }

                .fixstyle {
                  margin: 0 13px;
                }
              }

              p {
                margin: 0 0 0 10px;
                bottom: 5px;
                color: #999;
              }
            }
          }
        }

        .drawSetting {
          color: #999;
          padding-top: 10px;
          line-height: 24px;
        }

        .tabs {
          margin-top: 10px;

          .goods_img {
            float: left;
            margin: 0 5px 0 0 !important;
            width: 40px;
            height: 40px;
            border: 1px solid #ddd;
          }

          .goods_name {
            width: 135px;
            height: 40px;
            float: left;
            line-height: 20px !important;
            margin-left: 0px !important;
            margin-bottom: 0px !important;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
          }

          .goods_info {
            margin: 0 auto !important;
            width: 180px;
          }

          .goods_img img {
            width: 100%;
            height: 100%;
          }

          .goods_modal {
            display: block;
            border: none;
            margin-top: 10px;
            margin-bottom: 0;
            margin-left: 80px;
          }

          .coupon_set a {
            color: #5a8bff;
            border: none;
            padding: 0 5px;
            background: none;
            margin-left: 0;
          }

          .goods_modal th {
            padding: 10px 0;
            border: 1px solid #eee;
          }

          .goods_table td,
          .goods_modal td,
          .cat_modal td,
          .sort_table td {
            border: 1px solid #ddd;
            background: #fff;
            padding: 8px 10px;
          }

          .td {
            padding: 8px 10px;
          }
        }

        .levelBoxSetting {
          width: 602px;
          margin-top: 10px;
          box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12),
            0 0 6px 0 rgba(0, 0, 0, 0.04);

          .levelUpInfo {
            width: 602px;
          }
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
    width: 88%;
    height: 50px;
    background: #f8f8fa;
    margin-left: -20px;
  }

  .change_goods_del {
    text-decoration: none;
  }
}
</style>>
