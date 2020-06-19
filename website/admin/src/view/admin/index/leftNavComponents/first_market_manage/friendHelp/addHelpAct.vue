
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
          v-if="!ruleShow"
        >
          <el-form-item
            :label="$t('promoteList.actName') + '：'"
            prop="actName"
          >
            <el-input
              size="small"
              :placeholder="$t('promoteList.actNamePlaceholder')"
              class="morelength"
              v-model="form.actName"
            ></el-input>
            <span
              style="margin-left: 10px;color: #5a8bff; cursor: pointer;"
              @click="ruleHandler"
            >{{$t('promoteList.actRules')}}</span>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.actValidityPeriod') + '：'"
            prop="validity"
          >
            <el-date-picker
              :disabled="isEditFlag"
              v-model="form.validity"
              type="datetimerange"
              :range-separator="$t('promoteList.to')"
              :start-placeholder="$t('promoteList.startTime')"
              :end-placeholder="$t('promoteList.endTime')"
              :default-time="['00:00:00','23:59:59']"
              value-format="yyyy-MM-dd HH:mm:ss"
              size="small"
            >
            </el-date-picker>

            <!-- <section style="display: flex">
              <el-form-item prop="startTime">
                <el-date-picker
                  v-model="form.startTime"
                  type="date"
                  :placeholder="$t('promoteList.startTime')"
                  class="morelength"
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :disabled="isEditFlag"
                >
                </el-date-picker>
              </el-form-item>
              <span style="margin: 0 5px">{{$t('promoteList.to')}}</span>
              <el-form-item prop="endTime">
                <el-date-picker
                  v-model="form.endTime"
                  type="date"
                  :placeholder="$t('promoteList.endTime')"
                  class="morelength"
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :disabled="isEditFlag"
                >
                </el-date-picker>
              </el-form-item>
            </section> -->
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.rewardType') + '：'"
            prop="rewardType"
          >
            <el-radio
              v-model="form.rewardType"
              label=0
              :disabled="isEditFlag"
              @change="rewardTypeChange"
            >{{$t('promoteList.giftGoods')}}</el-radio>
            <el-radio
              v-model="form.rewardType"
              label=1
              :disabled="isEditFlag"
              @change="rewardTypeChange"
            >{{$t('promoteList.discountGoods')}}</el-radio>
            <el-radio
              v-model="form.rewardType"
              label=2
              :disabled="isEditFlag"
              @change="rewardTypeChange"
            >{{$t('promoteList.giftCoupons')}}</el-radio>
            <el-col v-if="(form.rewardType==0 || form.rewardType==1) && !this.isEditFlag">
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
            :label="((form.rewardType==0 || form.rewardType==1)&& goodDataFlag === true) ||  (form.rewardType==2 && couponDataFlag === true)? $t('promoteList.rewardSet') + '：' : ''"
            prop=""
            :required="true"
          >
            <el-table
              v-if="(form.rewardType==0 || form.rewardType==1)&& goodDataFlag === true"
              :data="form.goodsInfo"
              key="goodsList"
              border
              style="width:700px;"
            >
              <el-table-column
                prop="goodsName"
                :label="$t('promoteList.goodsInfo')"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="prdPrice"
                :label="$t('promoteList.goodsPrice')"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="prdNumber"
                :label="$t('promoteList.goodsStore')"
                align="center"
              ></el-table-column>
              <el-table-column
                :label="$t('promoteList.actStore')"
                align="center"
                width="180px"
              >
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'goodsInfo.' +  scope.$index+ '.market_store'"
                    :rules="[
                    { required: true, message: '请填写活动库存', trigger: 'change' },
                    { validator: (rule, value, callback)=>{validateStore(rule, value, callback, scope.row.prdNumber)}, trigger: ['blur', 'change'] }
                  ]"
                  >
                    <el-input
                      v-model="scope.row.market_store"
                      size="small"
                      :disabled="isEditFlag"
                    ></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                v-if="form.rewardType==1"
                :label="$t('promoteList.actPrice')"
                align="center"
                width="180px"
              >
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'goodsInfo.' +  scope.$index+ '.market_price'"
                    :rules="[
                    { required: true, message: '请填写活动价', trigger: 'change' },
                    { validator: (rule, value, callback)=>{validatePrice(rule, value, callback, scope.row.prdPrice)}, trigger: ['blur', 'change'] }
                  ]"
                  >
                    <el-input
                      v-model="scope.row.market_price"
                      size="small"
                      :disabled="isEditFlag"
                    ></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
            </el-table>
            <el-form-item
              v-if="(form.rewardType==0 || form.rewardType==1)&& goodDataFlag === false"
              style="color: red;"
            >活动商品已删除</el-form-item>

            <el-table
              v-if="form.rewardType==2 && couponDataFlag === true"
              :data="form.coupon_info"
              key="couponList"
              border
              style="width: 400px;"
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
                    <div
                      v-if="scope.row.actCode == 'discount'"
                      style="color:red"
                    ><span>{{scope.row.denomination}}</span>折</div>
                    <div
                      v-if="scope.row.actCode == 'random'"
                      style="color:red"
                    ><span>{{scope.row.randomMax}}</span>最高</div>
                    <div class="coupon_rule">{{scope.row.useConsumeRestrict > 0? `满${scope.row.leastConsume}元可用`  : `不限制`}}</div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('promoteList.couponNum')"
                align="center"
                width="180px"
              >
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'coupon_info.' +  scope.$index+ '.send_num'"
                    :rules="[
                    { required: true, message: '请填写发券数量', trigger: 'change' },
                    { validator: (rule, value, callback)=>{validateSendNum(rule, value, callback, scope.row.surplus)}, trigger: ['blur', 'change'] }
                  ]"
                  >
                    <el-input
                      v-model="scope.row.send_num"
                      size="small"
                      style="width:100px;"
                    ></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
            </el-table>
            <el-form-item
              v-if="form.rewardType==2 && couponDataFlag === false"
              style="color: red;"
            >活动优惠券已删除</el-form-item>
          </el-form-item>

          <el-form-item
            :label="$t('promoteList.rewardValidityPeriod') + '：'"
            prop=""
            :required="true"
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
            :label="$t('promoteList.currentPromoteValue') + '：'"
            prop="promoteType"
          >
            <el-radio
              v-model="form.promoteType"
              label="0"
            >{{$t('promoteList.averageValue')}}</el-radio>
            <el-radio
              v-model="form.promoteType"
              label="1"
            >{{$t('promoteList.randomValue')}}</el-radio>
            <span
              style="margin-left: 10px;color: #5a8bff; cursor: pointer;"
              @click="ruleHandler"
            >{{$t('promoteList.actRules')}}</span>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.requiredPromoteValue') + '：'"
            prop="promoteAmount"
          >
            <div style="display:flex">
              <el-input
                size="small"
                style="margin-right: 10px"
                v-model="form.promoteAmount"
                :disabled="isEditFlag"
              ></el-input>
              <div class="gray">{{$t('promoteList.requiredPromoteValueText')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.requiredPromoteTimes') + '：'"
            prop="promoteTimes"
          >
            <div style="display:flex">
              <el-input
                size="small"
                style="margin-right: 10px"
                v-model="form.promoteTimes"
                :disabled="isEditFlag"
              ></el-input>
              <div class="gray">{{$t('promoteList.requiredPromoteTimes')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.launchTimesLimit') + '：'"
            prop=""
            :required="true"
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
              </el-form-item>
              {{$t('promoteList.time')}}
              <div
                style="margin-left:10px"
                class="gray"
              >{{$t('promoteList.launchTimesLimitText')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.sharePromote') + '：'"
            prop=""
            :required="true"
          >
            <div style="display:flex">
              <span>{{$t('promoteList.friendShare')}}</span>
              <el-form-item prop="shareCreateTimes">
                <el-input
                  style="margin:0 5px"
                  size="small"
                  v-model="form.shareCreateTimes"
                ></el-input>
              </el-form-item>
              <span>{{$t('promoteList.promoteOpportunity')}}</span>
              <div
                style="margin-left: 10px"
                class="gray"
              >{{$t('promoteList.sharePromoteText')}}</div>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('promoteList.promoteCondition') + '：'"
            prop="promoteCondition"
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
            label="助力次数限制："
            prop=""
            :required="true"
          >
            <div style="display:flex">
              <div>单个用户每天最多可帮忙助力</div>
              <el-form-item prop="promoteTimesPerDay">
                <el-input
                  style="margin:0 5px"
                  size="small"
                  v-model="form.promoteTimesPerDay"
                ></el-input>
              </el-form-item>
              <div>{{$t('promoteList.time')}}</div>
              <div
                style="margin-left:12px"
                class="gray"
              >默认为0，表示不限制</div>
            </div>
          </el-form-item>
          <el-form-item
            v-if="form.rewardType == 1"
            :label="$t('promoteList.couponStrategy') + '：'"
            prop="useDiscount"
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
            :label="$t('promoteList.scoreStrategy') + '：'"
            prop="useScore"
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
            :label="$t('promoteList.promoteFail') + '：'"
            prop="failedSendType"
          >
            <el-radio
              v-model="form.failedSendType"
              label="0"
              @change="failedSendTypeChange"
            >{{$t('promoteList.giftNothing')}}</el-radio>
            <el-radio
              v-model="form.failedSendType"
              label="1"
              @change="failedSendTypeChange"
            >{{$t('promoteList.coupon')}}</el-radio>
            <el-radio
              v-model="form.failedSendType"
              label="2"
              @change="failedSendTypeChange"
            >{{$t('promoteList.point')}}</el-radio>
            <div v-if="form.failedSendType==1">
              <div
                v-if="!coupon_duplicate.length"
                class="addInfo"
                @click="isEditFlag?'':handleToCallDialog(2)"
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
                @click="isEditFlag?'':handleToCallDialog(2)"
              >
                <div
                  class="couponImgWrapper"
                  :class="coupon_duplicate[0].status === 0 ? 'couponImgWrapper' : 'couponImgWrapperGray'"
                >
                  <div
                    class="coupon_list_top"
                    :class="coupon_duplicate[0].status === 0 ? 'coupon_list_top' : 'couponListTop'"
                  >
                    <span v-if="coupon_duplicate[0].actCode === 'voucher'">
                      ￥<span class="number">{{coupon_duplicate[0].denomination}}</span>
                    </span>
                    <span v-if="coupon_duplicate[0].actCode === 'discount'">
                      <span class="number">{{coupon_duplicate[0].denomination}}</span>折
                    </span>
                    <span v-if="coupon_duplicate[0].actCode === 'random'">
                      ￥<span class="number">{{coupon_duplicate[0].randomMax}}</span>最高
                    </span>
                  </div>
                  <div
                    class="coupon_center_limit"
                    :class="coupon_duplicate[0].status === 0 ? 'coupon_center_limit' : 'couponCenterLimit'"
                  >{{coupon_duplicate[0].useConsumeRestrict | formatLeastConsume(coupon_duplicate[0].leastConsume)}}</div>
                  <div
                    class="coupon_center_number"
                    :class="coupon_duplicate[0].status === 0 ? 'coupon_center_number' : 'couponCenterNumber'"
                    v-if="coupon_duplicate[0].surplus === 0"
                  >库存不限制</div>
                  <div
                    class="coupon_center_number"
                    :class="coupon_duplicate[0].status === 0 ? 'coupon_center_number' : 'couponCenterNumber'"
                    v-if="coupon_duplicate[0].surplus > 0"
                  >剩余{{coupon_duplicate[0].surplus}}张</div>
                  <div
                    class="coupon_list_bottom"
                    :class="coupon_duplicate[0].status === 0 ? 'coupon_list_bottom' : 'couponListBottom'"
                    :style="`backgroundImage:url('${$imageHost}/image/admin/coupon_border.png')`"
                    v-if="coupon_duplicate[0].scoreNumber === 0"
                  >领取</div>
                  <div
                    class="coupon_list_bottom"
                    :class="coupon_duplicate[0].status === 0 ? 'coupon_list_bottom' : 'couponListBottom'"
                    :style="`backgroundImage:url('${$imageHost}/image/admin/coupon_border.png')`"
                    v-if="coupon_duplicate[0].scoreNumber > 0"
                  >{{coupon_duplicate[0].scoreNumber}}积分 兑换</div>
                </div>
              </div>
            </div>
            <div v-if="form.failedSendType==2">
              {{$t('promoteList.giftPoint')}}
              <el-input
                size="small"
                type="primary"
                v-model="form.failedSendContent"
              ></el-input>
            </div>
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
              {{ $t('promoteList.openConfigure') }}&nbsp;<img :src="ArrowArr[0].img_1">
            </div>
            <div
              v-if="!arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >
              {{ $t('promoteList.closeConfigure') }}&nbsp;<img :src="ArrowArr[1].img_2">
            </div>
          </div>
          <div v-if="!arrorFlag">
            <el-form-item
              :label="$t('promoteList.actShare') + '：'"
              prop=""
              :required="true"
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
                    style="margin: 0px 20px 0px 0px"
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
            <el-form-item
              label="活动规则说明："
              prop=""
              :required="true"
            >
              <el-button
                type="primary"
                size="small"
                @click="friendHelpRule"
              >设置规则说明</el-button>
            </el-form-item>
          </div>
        </el-form>

        <!-- 规则说明 -->
        <ActivityRule
          v-if="ruleShow"
          @ActivityMsg="activityMsg"
          :sendMsg="sendMsg"
          :template="template"
        />
      </div>

      <div
        class="footer"
        v-if="!ruleShow"
      >
        <el-button
          type="primary"
          size="small"
          @click="addAct"
        >{{$t('promoteList.save')}}</el-button>
      </div>
    </div>
    <choosingGoods
      @resultGoodsRow="choosingGoodsResult"
      :loadProduct="true"
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="goodsIdList"
      :singleElection="true"
    >
    </choosingGoods>
    <!--奖励类型-添加普通优惠券-->
    <AddCouponDialog
      singleElection="true"
      @handleToCheck="handleToCheck"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
      :type="this.flag === 1 ? 0 : -1"
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
import { addActive, selectOneInfo, updateInfo, getGoodsInfo } from '@/api/admin/marketManage/friendHelp.js'
import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: {
    choosingGoods,
    ImageDalog,
    ActivityRule: () => import('@/components/admin/activityRule'),
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
    // 自定义奖励有效期
    var validateRewardDuration = (rule, value, callback) => {
      var re = /^[1-9]\d*$/ // 正整数
      if (!value) {
        callback(new Error('请填写有效期'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    // 自定义校验所需助力值
    var validatePromoteAmount = (rule, value, callback) => {
      var re = /^[1-9]\d*$/ // 正整数
      if (!value) {
        callback(new Error('请填写助力值'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    // 自定义校验所需助力次数
    var validatePromoteTimes = (rule, value, callback) => {
      var re = /^[1-9]\d*$/ // 正整数
      if (!value) {
        callback(new Error('请填写助力次数'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    // 自定义校验所需助力次数
    var validateLaunchLimit = (rule, value, callback) => {
      var re = /^[1-9]\d*$/ // 正整数
      if (!value) {
        callback(new Error('请填写时间'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    // 自定义校验所需助力次数限制
    var validateLaunchLimitTimes = (rule, value, callback) => {
      var re = /^(0|\+?[1-9][0-9]*)$/ // 0或正整数
      if (value === '') {
        callback(new Error('请填写次数限制'))
      } else if (!re.test(value)) {
        callback(new Error('请填写0或正整数'))
      } else {
        callback()
      }
    }
    // 自定义校验助力机会
    var validateShareCreateTimes = (rule, value, callback) => {
      var re = /^[1-9]\d*$/ // 正整数
      if (!value) {
        callback(new Error('请填写助力机会'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    // 自定义校验助力次数限制
    var validatePromoteTimesPerDay = (rule, value, callback) => {
      var re = /^(0|\+?[1-9][0-9]*)$/ // 0或正整数
      if (value === '') {
        callback(new Error('请填写次数限制'))
      } else if (!re.test(value)) {
        callback(new Error('请填写0或正整数'))
      } else {
        callback()
      }
    }
    // 自定义校验助力失败赠送
    var validateFailedSendType = (rule, value, callback) => {
      var re = /^[1-9]\d*$/ // 正整数
      if (!value) {
        callback(new Error('请选择助力失败赠送条件'))
      } else if (value === '1' && (this.coupon_duplicate.length === 0 || this.coupon_duplicate === [])) {
        callback(new Error('请选择赠送优惠券'))
      } else if (value === '2' && (!this.form.failedSendContent || !re.test(this.form.failedSendContent))) {
        callback(new Error('请正确填写赠送积分'))
      } else {
        callback()
      }
    }
    return {
      couponFlag: null, // 优惠券弹窗类型
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
        timeInterval: [],
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
        validity: '',
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
        launchLimitTimes: '0',
        shareCreateTimes: '1',
        promoteCondition: '0',
        failedSendType: '0',
        failedSendContent: '',
        activityShareType: '0',
        customShareWord: '',
        shareImgType: '0',
        // customImgPath: '',
        // 选中商品id
        goodsInfo: [
          // {
          //   goodsIds: '',
          //   goodsName: '',
          //   prdPrice: '',
          //   goodsNumber: '',
          //   rewardType: '',
          //   market_price: '',
          //   market_store: ''
          // }
        ],
        coupon_info: [],
        promoteTimesPerDay: '0',
        // 规则说明
        actCopywriting: {
          document: '',
          isUseDefault: 0
        }
      },
      // 表单约束
      formRules: {
        actName: [
          { required: true, message: '请填写活动名称', trigger: 'change' }
        ],
        validity: [
          { required: true, message: '请填写活动有效期', trigger: 'change' }
        ],
        rewardType: [
          { required: true, message: '请选择奖励类型', trigger: 'change' }
        ],
        rewardDuration: [
          { required: true, validator: validateRewardDuration, trigger: 'change' }
        ],
        promoteType: [
          { required: true, message: '请选择单次助力值', trigger: 'change' }
        ],
        promoteAmount: [
          { required: true, validator: validatePromoteAmount, trigger: 'change' }
        ],
        promoteTimes: [
          { required: true, validator: validatePromoteTimes, trigger: 'change' }
        ],
        launchLimitDuration: [
          { required: true, validator: validateLaunchLimit, message: '请填写时间', trigger: 'change' }
        ],
        launchLimitTimes: [
          { required: true, validator: validateLaunchLimitTimes, trigger: 'change' }
        ],
        shareCreateTimes: [
          { required: true, validator: validateShareCreateTimes, trigger: 'change' }
        ],
        promoteCondition: [
          { required: true, message: '请选择好友助力条件', trigger: 'change' }
        ],
        promoteTimesPerDay: [
          { required: true, validator: validatePromoteTimesPerDay, trigger: 'change' }
        ],
        useDiscount: [
          { required: true, message: '请选择优惠叠加策略', trigger: 'change' }
        ],
        useScore: [
          { required: true, message: '请选择积分抵扣策略', trigger: 'change' }
        ],
        failedSendType: [
          { required: true, validator: validateFailedSendType, trigger: 'change' }
        ]
      },
      srcList: {
        src: `${this.$imageHost}/image/admin/add_img.png`,
        src1: `${this.$imageHost}/image/admin/share/promote_share_goods.jpg`,
        src2: `${this.$imageHost}/image/admin/share/promote_pictorial_goods.jpg`,
        imageUrl: ``
      },
      showCouponDialog: false, // 优惠券弹窗
      coupon_duplicate: [], // 失败送优惠券数据
      couponIdList: [], // 优惠券回显数据id
      flag: 1, // 两个优惠券弹窗区分
      showImageDialog: false,
      imgHost: `${this.$imageHost}`,

      ruleShow: false, // 规则组件
      sendMsg: null, // 回显规则内容
      // 默认模板内容
      template: `
        <div style="line-height: 1.5;">
          <p>参与步骤</p>
          <p>1.通过活动海报或好友分享，进入活动页面，通过活动发起按钮，发起助力活动，按页面提示分享给好友帮忙助力；</p>
          <p>2.好友通过小程序落地页查看活动现状，帮忙助力，获得助力值；</p>
          <p>3.在活动有效期内，助力值满，则发起人可获得奖励；</p>
          <p>4.在奖励有效期内，获奖用户领取奖励并完成下单（或查看商品），即可等待商家发货。</p>
          <p>参与规则</p>
          <p>1.同一时间，同一用户只能发起一个助力活动；</p>
          <p>2.发起助力后24小时助力值未满的，则助力失败，不可获得奖励。</p>
        </div>
      `,
      // 展开设置箭头
      ArrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }],
      arrorFlag: true, // 展开更多配置

      goodDataFlag: true, // 商品信息是否存在
      couponDataFlag: true // 奖励优惠券是否存在
    }
  },
  created () {
    this.form.rewardDurationUnitSelect = this.form.rewardDurationUnit[0].value
    this.form.launchLimitUnitSelect = this.form.launchLimitUnit[0].value
    this.promoteId = this.$route.params.id
    if (this.promoteId !== null) {
      if (this.$route.query.currentStatue === '进行中') {
        this.isEditFlag = true
      } else {
        this.isEditFlag = false
      }
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
        console.log('pageInfo:', res.content)
        // 展开设置
        this.arrorFlag = false
        this.form.actName = res.content.actName
        this.form.startTime = res.content.startTime
        this.form.endTime = res.content.endTime
        this.form.validity = [res.content.startTime, res.content.endTime]
        this.form.rewardType = res.content.rewardType.toString()
        this.form.rewardSet = JSON.parse(res.content.rewardContent)
        this.form.rewardDuration = res.content.rewardDuration
        this.form.rewardDurationUnitSelect = res.content.rewardDurationUnit
        this.form.promoteType = res.content.promoteType.toString()
        this.form.promoteAmount = res.content.promoteAmount
        this.form.promoteTimes = res.content.promoteTimes
        this.form.launchLimitDuration = res.content.launchLimitDuration
        this.form.launchLimitUnitSelect = res.content.launchLimitUnit
        this.form.launchLimitTimes = res.content.launchLimitTimes
        this.form.shareCreateTimes = res.content.shareCreateTimes
        this.form.promoteCondition = res.content.promoteCondition.toString()
        this.form.useDiscount = res.content.useDiscount.toString()
        this.form.useScore = res.content.useScore.toString()
        this.form.failedSendType = res.content.failedSendType.toString()
        this.form.failedSendContent = res.content.failedSendContent
        this.form.activityShareType = res.content.activityShareType.toString()
        this.form.customShareWord = res.content.customShareWord
        this.form.shareImgType = res.content.shareImgType.toString()
        this.srcList.src = res.content.customImgPath
        this.form.promoteTimesPerDay = res.content.promoteTimesPerDay
        this.form.actCopywriting = res.content.actCopywriting
        if (this.form.rewardType === '0') {
          // this.form.rewardSet.market_store = JSON.parse(res.content[0].rewardContent.slice(1, -1)).market_store
          console.log('market_store???', this.form.rewardSet.market_store)
          // this.form.rewardSet.goods_ids = JSON.parse(res.content[0].rewardContent.slice(1, -1)).goods_ids
          console.log('goods_ids???', this.form.rewardSet.goods_ids)
          // let goodsIdParam = {
          //   'goodsId': this.form.rewardSet.goods_ids
          // }
          let idParam = {
            'id': this.form.rewardSet.goods_ids
          }
          getGoodsInfo(idParam).then(res => {
            console.log('goodsInfoByPrdId:', res)
            if (res.error === 0) {
              this.goodDataFlag = true
              let goodsItem = {
                'goodsName': res.content.goodsName,
                'prdPrice': res.content.goodsPrice,
                'prdNumber': res.content.goodsStore,
                'market_store': this.form.rewardSet.market_store
              }
              this.form.goodsInfo = []
              this.form.goodsInfo.push(goodsItem)
            } else {
              this.goodDataFlag = false
            }
          })
          // selectGoodsApi(goodsIdParam).then(res => {
          //   this.form.goodsInfo = [res.content]
          //   this.form.goodsInfo.market_store = this.form.rewardSet.market_store
          //   console.log('goodsInfo:', res.content)
          // })
        }
        if (this.form.rewardType === '1') {
          let idParam = {
            'id': this.form.rewardSet.goods_ids
          }
          getGoodsInfo(idParam).then(res => {
            console.log('goodsInfoByPrdId:', res)
            if (res.error === 0) {
              this.goodDataFlag = true
              let goodsItem = {
                'goodsName': res.content.goodsName,
                'prdPrice': res.content.goodsPrice,
                'prdNumber': res.content.goodsStore,
                'market_store': this.form.rewardSet.market_store,
                'market_price': this.form.rewardSet.market_price
              }
              this.form.goodsInfo = []
              this.form.goodsInfo.push(goodsItem)
            } else {
              this.goodDataFlag = false
            }
          })
        }
        if (this.form.rewardType === '2') {
          updateCoupon(this.form.rewardSet.reward_ids).then(res => {
            if (res.error === 0) {
              this.couponDataFlag = true
              this.form.coupon_info = res.content
              this.form.coupon_info[0].send_num = this.form.rewardSet.market_store
              console.log('couponInfo:', this.form.coupon_info)
            } else {
              this.couponDataFlag = false
            }
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
      if ((this.form.rewardType === '0' || this.form.rewardType === '1') && this.form.goodsInfo.length === 0) {
        this.$message.warning('请选择商品奖励设置')
        return false
      } else if (this.form.rewardType === '2' && this.form.coupon_info.length === 0) {
        this.$message.warning('请选择优惠券奖励设置')
        return false
      }
      this.$refs['form'].validate((valid) => {
        console.log('submit', this.form)
        if (valid) {
          console.log('this.form.rewardType:', this.form.rewardType)
          if (this.form.rewardType === '0' || this.form.rewardType === '1') {
            this.form.goodsInfo[0].shopPrice = this.form.goodsInfo[0].prdPrice
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
            this.form.rewardSet.market_store = this.form.coupon_info[0].send_num
            // this.form.rewardContent = '[' + JSON.stringify(this.form.rewardSet) + ']'
            console.log('rewardSet:', this.form.rewardSet)
            console.log('rewardContent:', this.form.rewardContent)
          }
          let addParam = {
            'id': this.promoteId,
            'actName': this.form.actName,
            'startTime': this.form.validity[0],
            'endTime': this.form.validity[1],
            'rewardType': this.form.rewardType,
            'fpRewardContent': this.form.rewardSet,
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
            'customImgPath': this.srcList.src,
            'promoteTimesPerDay': this.form.promoteTimesPerDay,
            'actCopywriting': this.form.actCopywriting
          }
          console.log('submit', this.form)
          if (this.promoteId !== 'null') {
            console.log('I am updating!')
            updateInfo(addParam).then(res => {
              console.log(res)
              if (res.error === 0) {
                this.$message.success(this.$t('promoteList.successUpdate'))
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
                this.$message.success(this.$t('promoteList.successAdd'))
                this.$router.push({
                  name: 'promote'
                })
              }
            }).catch(() => {
              this.$message.error(this.$t('promoteList.operationFailed'))
            })
          }
        } else {
          // this.$message.error(this.$t('promoteList.validCheck'))
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
      console.log('初始化商品弹窗', this.form.rewardContent.goodsIds)
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
      // this.form.rewardSet.goods_ids = row.goodsId
      this.form.rewardSet.goods_ids = row.prdId

      // })
    },
    // 选择优惠券弹窗
    handleToCallDialog (val) {
      this.flag = val
      this.showCouponDialog = !this.showCouponDialog
      this.couponIdList = []
      if (val === 1) {
        this.couponFlag = 1
        this.couponIdList.push(this.form.coupon_info[0].id)
      } else {
        this.couponFlag = 2
        this.couponIdList.push(this.coupon_duplicate[0].id)
      }
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data) {
      console.log('couponInfo:', data)
      if (this.couponFlag === 1) {
        this.form.rewardSet.reward_ids = data[0].id
        this.form.coupon_info = data
        console.log(this.form.coupon_info)
      } else {
        this.form.failedSendContent = data[0].id
        this.coupon_duplicate = data
        console.log(this.coupon_duplicate)

        this.$refs['form'].validateField('failedSendType')
      }
    },

    // 查看活动规则
    ruleHandler () {
      window.open('http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=736&fromuid=1')
    },

    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 切换奖励类型
    rewardTypeChange () {
      this.form.goodsInfo = []
      this.form.coupon_info = []
      this.goodsIdList = []
      this.couponIdList = []
    },

    // 校验活动库存
    validateStore (rule, value, callback, prdNumber) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value) {
        callback(new Error('请填写活动库存'))
      } else if (!re.test(value)) {
        callback(new Error('请正确填写活动库存'))
      } else if (Number(value) > prdNumber) {
        callback(new Error('活动库存不能大于商品库存'))
      } else {
        callback()
      }
    },

    // 校验活动价
    validatePrice (rule, value, callback, prdPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!value) {
        callback(new Error('请填写活动价'))
      } else if (!re.test(value)) {
        callback(new Error('请正确填写活动价'))
      } else if (Number(value) > prdPrice) {
        callback(new Error('活动价不能大于原价'))
      } else {
        callback()
      }
    },

    // 校验发券数量
    validateSendNum (rule, value, callback, surplus) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value) {
        callback(new Error('请填写数量'))
      } else if (!re.test(value)) {
        callback(new Error('请正确填写数量'))
      } else if (surplus !== 0 && Number(value) > surplus) {
        callback(new Error('数量不能大于剩余量'))
      } else {
        callback()
      }
    },

    // 切换助力失败赠送条件
    failedSendTypeChange () {
      this.coupon_duplicate = []
      this.form.failedSendContent = ''
      this.$refs['form'].validateField('failedSendType')
    },

    // 设置规则说明
    friendHelpRule () {
      this.ruleShow = true
      this.sendMsg = this.form.actCopywriting
    },
    // 规则说明回调函数
    activityMsg (data) {
      this.ruleShow = false
      this.form.actCopywriting = data
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
      z-index: 1;
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
    margin-top: -1px;
    border: 1px solid #fbb;
    border-radius: 10px;
    .coupon_list_top {
      margin-top: 10px;
      color: #f60;
      :nth-of-type(2) {
        font-size: 20px;
        font-weight: bold;
      }
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
      font-size: 12px;
      height: 24px;
      line-height: 30px;
      border-bottom-left-radius: 8px;
      border-bottom-right-radius: 8px;
      color: #fff;
      background: #f66;
      background-repeat: repeat-x;
    }
  }
  .couponImgWrapperGray {
    width: 100%;
    height: 100%;
    margin-top: -1px;
    border: 1px solid #d5d7d9;
    border-radius: 10px;
    .couponListTop {
      margin-top: 10px;
      color: #d5d7d9;
      :nth-of-type(2) {
        font-size: 20px;
        font-weight: bold;
      }
      .number {
        font-size: 20px;
        font-weight: bold;
      }
    }
    .couponCenterLimit {
      height: 20px;
      color: #d5d7d9;
      font-size: 12px !important;
    }
    .couponCenterNumber {
      height: 20px;
      color: #d5d7d9;
    }
    .couponListBottom {
      font-size: 12px;
      height: 24px;
      line-height: 30px;
      border-bottom-left-radius: 8px;
      border-bottom-right-radius: 8px;
      color: #fff;
      background: #d5d7d9;
      background-repeat: repeat-x;
    }
  }
}
</style>
