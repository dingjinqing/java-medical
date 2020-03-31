<!--
添加拼团活动
@author 孔德成
-->
<template>
  <div>
    <div class="wrapper">
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="150px"
        :label-position="'right'"
      >
        <el-form-item
          :label="$t('groupBuy.groupBuyActivity') + '：'"
          prop="resource"
        >
          <el-radio-group
            :disabled='isEdite'
            v-model="form.activityType"
          >
            <el-radio :label=1>{{$t('groupBuy.grouponType')[0].label}}</el-radio>
            <el-radio :label=2>{{$t('groupBuy.grouponType')[1].label}}</el-radio>
          </el-radio-group>
          <div class="prompt">
            {{$t('groupBuy.groupBuyActivityComment')}}
          </div>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.activityName') + '：'"
          prop="name"
        >
          <el-col :span="8">
            <el-input
              size="small"
              v-model="form.name"
              style="width: 170px"
              placeholder="请输入活动名称"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.activtiyLevel') + '：'"
          prop="level"
        >
          <el-col :span="12">
            <el-input
              v-model="form.level"
              controls-position="right"
              style="width:170px"
              size="small"
              placeholder="请输入活动名称"
            >
            </el-input>
            <div class="prompt">
              {{$t('groupBuy.activtiyLevelComment')}}
            </div>
            <!-- <span>用于区分不同拼团活动的优先级，请填写正整数，数值越大优先级越高</span> -->
          </el-col>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.goodsName') + '：'"
          prop="goodsId"
        >
          <el-input
            :disabled="true"
            v-model="goodsRow.goodsName"
            v-if="goodsRow.ischecked"
            size="small"
            style="width: 170px;"
          ></el-input>
          <el-input
            :disabled="true"
            v-if="false"
            v-model="form.goodsId"
            size="small"
            style="width: 170px;"
          ></el-input>

          <el-button
            :disabled="isEdite"
            size="small"
            @click="showChoosingGoods"
          >{{$t('groupBuy.selectGoods')}}
          </el-button>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.commanderDiscounts') + '：'">
          <section style="display: flex">
            <div style="width: 70px">
              <el-switch
                :disabled="isEdite"
                v-model="form.isGrouperCheap"
                :active-value=1
                :inactive-value=0
                active-color="#f7931e"
              ></el-switch>
            </div>
            <div class="prompt">
              <div>
                {{$t('groupBuy.commanderDiscountsComment1')}}
              </div>
              <p style="line-height:20px">
                <span style="color: red;">{{$t('marketCommon.note')}}：</span>
                {{$t('groupBuy.commanderDiscountsComment2')}}
              </p>
            </div>
          </section>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.discountsOption') + '：'">
          <el-table
            header-row-class-name="tableHeader"
            :data="form.product"
            border
            style="width: 100%"
            :empty-text="$t('groupBuy.noData')"
          >
            <el-table-column
              align="center"
              prop="goodsName"
              label="商品名称"
            >
            </el-table-column>

            <!-- 原价 -->
            <el-table-column
              align="center"
              prop="shopPrice"
              :label="$t('groupBuy.originalPrice')"
            >
            </el-table-column>

            <!-- 拼团价 -->
            <el-table-column
              align="center"
              :label="$t('groupBuy.groupBuyPrice')"
            >
              <!-- <template slot="append">
                <span>{{$t('groupBuy.groupBuyPrice')}}</span>
                <el-button
                  @click="setCurrent(1)"
                  size="small"
                  icon="el-icon-edit"
                >{{$t('groupBuy.batchOption')}}
                </el-button>
              </template> -->
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.groupPrice'"
                  :rules="[
                    { required: true, message: '拼团价不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.shopPrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <div
                    class="input-error"
                    v-if="scope.row.priceErrorMsg"
                  >{{scope.row.priceErrorMsg}}</div>
                  <el-input
                    v-model="scope.row.groupPrice"
                    size="small"
                    :disabled="isEdite || disabledFlag"
                    @input="changePriceInput(scope.row)"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts && scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>

            <!-- 团长价 -->
            <el-table-column
              align="center"
              prop="grouperPrice"
              :label="$t('groupBuy.commanderPrice')"
              v-if="form.isGrouperCheap === 1"
            >
              <!-- <template slot="append">
                <span>{{$t('groupBuy.commanderPrice')}}</span>
                <el-button
                  @click="setCurrent(2)"
                  size="mini"
                  icon="el-icon-edit"
                >{{$t('groupBuy.batchOption')}}
                </el-button>
              </template> -->
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.grouperPrice'"
                  :rules="[
                    { required: true, message: '团长价不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.shopPrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.grouperPrice"
                    size="small"
                  />
                </el-form-item>
              </template>
            </el-table-column>

            <!-- 原库存 -->
            <el-table-column
              align="center"
              prop="goodsNumber"
              :label="$t('groupBuy.originalStock')"
            >
            </el-table-column>

            <!-- 剩余库存 -->
            <el-table-column
              align="center"
              prop="stock"
              :label="$t('groupBuy.groupBuyStock')"
            >
              <!-- <template slot="append">
                <span>{{$t('groupBuy.groupBuyStock')}}</span>
                <el-button
                  @click="setCurrent(3)"
                  size="mini"
                  icon="el-icon-edit"
                >{{$t('groupBuy.batchOption')}}
                </el-button>
              </template> -->
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.stock'"
                  :rules="[
                    { required: true, message: '拼团库存不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.goodsNumber)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <div
                    class="input-error"
                    v-if="scope.row.stockErrorMsg"
                  >{{scope.row.stockErrorMsg}}</div>
                  <el-input
                    v-model="scope.row.stock"
                    size="small"
                    :disabled="isEdite || disabledFlag"
                    @input="changeStockInput(scope.row)"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts && scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格；库存合计：{{scope.row.totalStock ? scope.row.totalStock : 0}}</div>
              </template>
            </el-table-column>

            <!-- 操作 -->
            <el-table-column
              label="操作"
              align="center"
            >删除</el-table-column>
            <template
              slot="empty"
              style="height：0"
            >
            </template>

            <div
              slot="append"
              class="moreSetUp"
            >
              <span>{{ this.$t('groupBuy.moreSettings') }}</span>
              <a
                :class="activeIndex === 1 ? '' : 'settings'"
                @click="setCurrent(1)"
              >{{ this.$t('groupBuy.groupBuyPrice') }}
              </a>
              <a
                :class="activeIndex === 2 ? '' : 'settings'"
                @click="setCurrent(2)"
              >{{ this.$t('groupBuy.commanderDiscounts') }}
              </a>
              <a
                :class="activeIndex === 3 ? '' : 'settings'"
                @click="setCurrent(3)"
              >{{ this.$t('groupBuy.groupBuyStock') }}
              </a>
            </div>
          </el-table>
        </el-form-item>

        <el-form-item
          :label="$t('groupBuy.validDate') + '：'"
          prop="validityDate"
        >
          <el-date-picker
            size="small"
            v-model="form.validityDate"
            type="datetimerange"
            @change="dateChange(form.validityDate)"
            :range-separator="$t('groupBuy.to')"
            :start-placeholder="$t('groupBuy.startDate')"
            :end-placeholder="$t('groupBuy.endDate')"
            value-format="yyyy-MM-dd HH:mm:ss"
            :default-time="['00:00:00','23:59:59']"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.limitAmount') + '：'"
          prop="limitAmount"
        >
          <el-input-number
            :disabled="isEdite"
            v-model="form.limitAmount"
            controls-position="right"
            :min="2"
          ></el-input-number>
          <div class="prompt">{{$t('groupBuy.limitAmountComment')}}</div>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.orderGoodsNum') + '：'">
          <div class="prompt fontColor">{{$t('groupBuy.orderGoodsNumComment1')}}</div>
          <el-input-number
            v-model="form.limitBuyNum"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <div class="prompt fontColor">
            {{$t('groupBuy.jian')}}
            <span class="prompt">{{$t('groupBuy.orderGoodsNumComment3')}}</span>
          </div>
        </el-form-item>
        <el-form-item>
          <div class="prompt fontColor"> {{$t('groupBuy.orderGoodsNumComment2')}}</div>
          <el-input-number
            v-model="form.limitMaxNum"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <div class="prompt fontColor">
            {{$t('groupBuy.jian')}}
            <span class="prompt">{{$t('groupBuy.orderGoodsNumComment3')}}</span>
          </div>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.joinLimit') + '：'"
          prop="joinLimit"
        >
          <div class="prompt fontColor"> {{$t('groupBuy.joinLimitComment1')}}</div>
          <el-input-number
            v-model="form.joinLimit"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <div class="prompt fontColor">
            {{ $t('groupBuy.joinLimitComment2')}}
            <span class="prompt">{{ $t('groupBuy.joinLimitComment3')}}</span>
          </div>
        </el-form-item>
        <el-form-item
          :label=" $t('groupBuy.openLimit') + '：'"
          prop="openLimit"
        >
          <div class="prompt fontColor"> {{ $t('groupBuy.openLimitComment1')}}</div>
          <el-input-number
            v-model="form.openLimit"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <div class="prompt fontColor">
            {{$t('groupBuy.joinLimitComment2')}}
            <span class="prompt"> {{$t('groupBuy.openLimitComment2')}}</span>
          </div>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.openIsDefault') + '：'">
          <el-switch
            v-model="form.isDefault"
            :active-value=1
            :inactive-value=0
            active-color="#f7931e"
          ></el-switch>
          <div class="prompt">{{$t('groupBuy.openIsDefaultComment')}}</div>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.shippingOption') + '：'">
          <el-radio-group v-model="form.shippingType">
            <el-radio :label=1>{{$t('groupBuy.freeShipping')}}</el-radio>
            <el-radio :label=2>{{$t('groupBuy.shippingOptionComment')}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.beginNum') + '：'">
          <el-input-number
            v-model="form.beginNum"
            controls-position="right"
            :min="0"
          >
          </el-input-number>
          <div class="prompt fontColor">
            {{$t('groupBuy.beginNumComment')}}
          </div>
        </el-form-item>

        <!-- 收起、展开更多配置 -->
        <div
          @click="handleToChangeArror()"
          style="margin: 0 0 10px 33px"
        >
          <div
            v-if="arrorFlag"
            style="color:rgb(90, 139, 255);cursor:pointer"
          >{{$t('groupBuy.moreConfigure')}}&nbsp;<img :src="ArrowArr[0].img_1"></div>
          <div
            v-if="!arrorFlag"
            style="color:rgb(90, 139, 255);cursor:pointer"
          >{{$t('groupBuy.packUpConfigure')}}&nbsp;<img :src="ArrowArr[1].img_2"></div>
        </div>
        <div v-if="!arrorFlag">
          <!-- 鼓励奖部分内容 -->
          <el-form-item :label="$t('groupBuy.consolationPrize') + '：'">
            <el-card class="box-card">
              <div class="fontColor"> {{$t('groupBuy.consolationPrizeComment1')}}</div>
              <div class="middleContainer">
                <div
                  v-for="(item,index) in rewardCouponList"
                  :key="index"
                  class="rewardCouponInfo"
                  style="margin-right: 5px;"
                >
                  <section
                    class="couponImgWrapper"
                    style="line-height: normal"
                  >
                    <div
                      class="coupon_list_top"
                      v-if="item.actCode==='voucher'"
                    >
                      <span>￥</span>
                      <span class="number">{{item.denomination}}</span>
                    </div>
                    <div
                      class="coupon_list_top"
                      v-if="item.actCode==='discount'"
                    >
                      <span>{{item.denomination}}</span>
                      <span>{{$t('payReward.discount')}}</span>
                    </div>
                    <div class="coupon_center_limit">{{item.useConsumeRestrict |
                      formatLeastConsume(item.leastConsume)}}
                    </div>
                    <div class="coupon_center_number">剩余{{item.surplus}}张</div>
                    <div
                      class="coupon_list_bottom"
                      :style="`background-image: url(${$imageHost}/image/admin/coupon_border.png)`"
                    >
                      <span v-if="item.scoreNumber === 0">领取</span>
                      <div v-if="item.scoreNumber !== 0">
                        <span>{{item.scoreNumber}}</span>积分 兑换
                      </div>
                    </div>
                  </section>
                  <span
                    class="deleteIcon"
                    @click="deleteCouponImg(index)"
                  >×
                  </span>
                </div>

                <div
                  class="rewardCouponInfo"
                  @click="handleToCallDialog()"
                  v-if="rewardCouponList.length<5"
                  style="line-height:normal"
                >
                  <div>
                    <el-image
                      fit="scale-down"
                      :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                      style="width:78px;height:78px;cursor:pointer;"
                    ></el-image>
                  </div>
                  <br>
                  <p class="textDesc">{{$t('groupBuy.addCoupon')}}</p>
                </div>
              </div>

              <div class="fontColor">{{$t('groupBuy.consolationPrizeComment2')}}</div>
            </el-card>
          </el-form-item>
          <!-- 引入活动分享模块 -->
          <el-form-item
            :label="$t('groupBuy.activitySharing') + '：'"
            prop="shareInfo"
          >
            <!-- <actShare :shareConfig="form.share" /> -->
            <div class="shareContent">
              <el-radio
                v-model="form.share.shareAction"
                :label=1
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
                v-model="form.share.shareAction"
                :label=2
              >自定义样式</el-radio>
              <div
                v-if="form.share.shareAction === 2"
                style="margin-left: 25px"
              >
                <span>文案：</span>
                <el-input
                  v-model="form.share.shareDoc"
                  size="small "
                  style="width: 180px;"
                ></el-input>
              </div>
              <div
                v-if="form.share.shareAction === 2"
                style="margin-left: 25px"
              >
                <span>分享图：</span>
                <el-radio
                  v-model="form.share.shareImgAction"
                  :label=1
                >活动商品信息图</el-radio>
                <div style="margin-left: 60px;">
                  <el-radio
                    v-model="form.share.shareImgAction"
                    :label=2
                  >自定义图片</el-radio>
                </div>

                <div
                  style="display: flex"
                  v-if="form.share.shareImgAction=== 2"
                >
                  <div
                    class="imgContent"
                    @click="addGoodsImg"
                  >
                    <div>
                      <img
                        v-if="form.share.shareImg === '' || form.share.shareImg === null"
                        :src="$imageHost + '/image/admin/btn_add.png'"
                        alt=""
                      >
                      <img
                        v-if="form.share.shareImg !== ''"
                        :src="$imageHost + '/' + form.share.shareImg"
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
      <!-- :chooseGoodsBack="[form.goodsId]" -->
      <!-- goodsIdList -->
      <choosingGoods
        @resultGoodsDatas="choosingGoodsResult"
        :chooseGoodsBack="goodsIdList"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :singleElection="false"
        :showTips="true"
      />

      <!--添加优惠券-->
      <addCouponDialog
        @handleToCheck="handleToCheck"
        :tuneUpCoupon="showCouponDialog"
        :couponBack="couponIdList"
      />

      <!-- 选择图片弹框 -->
      <ImageDalog
        pageIndex='pictureSpace'
        :tuneUp="showImageDialog"
        @handleSelectImg='handleSelectImg'
        :imageSize="[800, 800]"
      />

      <div class="footer">
        <el-button
          size="small"
          type="primary"
          :disabled="submitStatus"
          @click="submitForm(form)"
        >{{$t('marketCommon.ok')}}
        </el-button>
      </div>
    </div>

    <!-- 规格信息弹框 -->
    <spellGroupDialog
      :productDialog.sync="showSpecDialog"
      :product-info="productInfo"
      :isEdit="isEdite"
      @confrim="getProductdata"
    />

  </div>
</template>
<script>

import { mapActions } from 'vuex'
import choosingGoods from '@/components/admin/choosingGoods'
import addCouponDialog from '@/components/admin/addCouponDialog'
import ImageDalog from '@/components/admin/imageDalog'
import actShare from '@/components/admin/marketManage/marketActivityShareSetting'
// import { getAllGoodsProductList } from '@/api/admin/brandManagement.js'
// import { getGoodsInfosByGoodIds } from '@/api/admin/goodsManage/allGoods/allGoods'
import { addGroupBuyActivity, updateGroupBuy } from '@/api/admin/marketManage/spellGroup.js'
import { getSelectGoods } from '@/api/admin/marketManage/distribution.js'
import { updateCoupon } from '@/api/admin/marketManage/couponList.js'

export default {
  components: {
    choosingGoods,
    addCouponDialog,
    actShare,
    ImageDalog,
    spellGroupDialog: () => import('./spellGroupDialog')
  },
  props: ['isEdite', 'editData'],
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
    // 自定义校验器
    var dateValid = (rule, value, callback) => {
      console.log('校验时间')
      if (value === [] || value.length === 0) {
        return callback(new Error(this.$t('groupBuy.validityDateRules')))
      }
      callback()
    }
    var shareDocValid = (rule, value, callback) => {
      console.log('校验文案')
      console.log(rule, 'rule', value, 'value')
      if (value === [] || value.length === 0) {
        return callback(new Error('请输入活动文案'))
      }
      callback()
    }
    var shareInfoValid = (rule, value, callback) => {
      if (this.form.share.shareAction === 2 && (this.form.share.shareDoc === '' || this.form.share.shareDoc === null)) {
        return callback(new Error('请选择文案'))
      }
      if (this.form.share.shareImgAction === 2 && (this.form.share.shareImg === null || this.form.share.shareImg === '')) {
        return callback(new Error('请选择图片'))
      }
      callback()
    }
    var levelValid = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (value === '') {
        return callback(new Error('请输入活动优先级'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    return {
      // from 表单数据
      form: {
        id: null,
        name: '',
        level: '',
        goodsId: '',
        limitAmount: 2,
        joinLimit: 0,
        openLimit: 0,
        isDefault: 0,
        startTime: '',
        endTime: '',
        validityDate: [],
        shippingType: 1,
        activityType: 1,
        isGrouperCheap: 0,
        rewardCouponId: '',
        limitMaxNum: 0,
        limitBuyNum: 0,
        beginNum: 0,
        share: {
          shareAction: 1,
          shareDoc: '',
          shareImgAction: 1,
          shareImg: ''
        },
        product: [],
        shareInfo: '',
        stock: 0 // 活动总库存
      },
      // 校验表单
      fromRules: {
        name: [
          { required: true, message: this.$t('groupBuy.activityNameRequiredRules'), trigger: 'blur' },
          { max: 20, message: this.$t('groupBuy.lengthMax20'), trigger: 'blur' }
        ],
        level: [
          { required: true, validator: levelValid, trigger: ['blur', 'change'] }
          // { max: 20, message: this.$t('groupBuy.lengthMax20'), trigger: 'blur' }
        ],
        goodsId: [
          { required: true, message: this.$t('groupBuy.goodsIdRequireRules'), trigger: 'change' }
        ],
        limitAmount: [
          { type: 'integer', required: true, message: this.$t('groupBuy.limitAmountRequireRules'), trigger: 'blur' }
        ],
        joinLimit: [
          { type: 'integer', required: true, message: this.$t('groupBuy.joinLimitRequireRules'), trigger: 'blur' }
        ],
        openLimit: [
          { type: 'integer', required: true, message: this.$t('groupBuy.openLimitRequireRules'), trigger: 'blur' }
        ],
        validityDate: [
          { required: true, validator: dateValid, trigger: 'blur' }
        ],
        'share.shareDoc': [{ validator: shareDocValid, trigger: 'blur' }],
        shareInfo: [{ required: true, validator: shareInfoValid, trigger: 'blur' }]
      },
      // 选中商品id
      goodsRow: {},
      goodsIds: [],
      // 优惠券弹窗
      couponDialogFlag: false,
      couponList: [],

      rewardCouponList: [],
      rewardCouponIds: [],
      props: ['isEdite'],
      submitStatus: false,
      grouponType: [],
      isShowChoosingGoodsDialog: false,
      arrorFlag: true,
      ArrowArr: [
        {
          img_1: this.$imageHost + '/image/admin/show_more.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/hid_some.png'
        }
      ],

      imgHost: `${this.$imageHost}`,
      showCouponDialog: false,
      couponIdList: [],

      activeIndex: 0,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`,
        src3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`
      },
      showImageDialog: false,
      goodsIdList: [],
      showSpecDialog: false,
      productInfo: {},
      disabledFlag: true // 是否可编辑
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    if (this.isEdite) {
      this.editActivityInit()
      this.arrorFlag = false
    }
  },
  watch: {
    lang () {
      this.grouponType = this.$t('groupBuy.grouponType')
    },
    'form.goodsId': function (value) {
      if (value) {
        this.$refs.form.validateField('goodsId')
      }
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 校验表格
    validateMoney (rule, value, callback, shopPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > shopPrice) {
        callback(new Error('拼团价或团长价不能大于商品原价'))
      } else {
        callback()
      }
    },
    validateNum (rule, value, callback, prdNumber) {
      console.log(prdNumber)
      // var re = /(^0|\+?[1-9][0-9]\d*)$/
      var re = /^([1-9]\d*|[0]{1,1})$/
      if (!re.test(value)) {
        callback(new Error('请填写0和正整数'))
      } else if (value > prdNumber) {
        callback(new Error('拼团库存不能大于商品库存'))
      } else {
        callback()
      }
    },
    validatePrdPrice (item) {
      console.log(item)
      if (item.shopPrice < item.groupPrice) return true
      return false
    },
    validatePrdStock (item) {
      console.log(item)
      if (item.prdNumber < item.stock) return true
      return false
    },

    // 编辑活动初始化
    editActivityInit () {
      console.log('this.isEdite', this.isEdite)
      if (this.isEdite) {
        let data = this.editData
        console.log(data, 'init data')
        this.form.id = data.id
        this.form.activityType = data.activityType
        this.form.name = data.name
        this.form.goodsId = data.goodsId
        this.form.level = data.level
        // this.getGoodsInfo(data.goodsId)
        this.form.product = this.initEditProduct(data.productList)
        this.form.isGrouperCheap = data.isGrouperCheap
        // this.form.product = data.productList
        this.form.startTime = data.startTime
        this.form.endTime = data.endTime
        this.form.validityDate = [data.startTime, data.endTime]
        this.form.limitAmount = data.limitAmount
        this.form.limitBuyNum = data.limitBuyNum
        this.form.limitMaxNum = data.limitMaxNum
        this.form.joinLimit = data.joinLimit
        this.form.openLimit = data.openLimit
        this.form.isDefault = data.isDefault
        this.form.shippingType = data.shippingType
        this.form.beginNum = data.beginNum
        if (data.rewardCouponId) {
          this.form.rewardCouponId = data.rewardCouponId.split(',')
          this.rewardCouponIds = data.rewardCouponId.split(',')
          this.getCouponList(this.rewardCouponIds.map(Number))
        }
        this.form.share = data.share
      }
    },

    // 获取商品信息
    getGoodsInfo (id) {
      getSelectGoods({ goodsId: id }).then((res) => {
        if (res.error === 0) {
          this.goodsRow = res.content
          this.goodsRow.ischecked = true
        }
      })
    },

    // 获取优惠券信息
    getCouponList (ids) {
      console.log(ids, 'ids--')
      this.rewardCouponList = []
      ids.map((item, index) => {
        updateCoupon(item).then((res) => {
          if (res.error === 0) {
            this.rewardCouponList.push(res.content[0])
            console.log(this.rewardCouponList)
            this.couponIdList = this.rewardCouponList.map(item => item.id)
            console.log(this.couponIdList)
          }
        })
      })
    },

    // 提交表单 保存
    submitForm (formName) {
      this.submitStatus = true
      if (this.rewardCouponIds) {
        this.form.rewardCouponId = this.rewardCouponIds.join(',')
      }
      this.$refs['form'].validate((valid) => {
        console.log('提交表单', formName)
        console.log('this.form', this.form)
        if (valid) {
          // let goodsId = this.form.goodsId.join(',')
          this.form.product.forEach((item, index) => {
            item.productId = item.prdId
            item.groupPrice = Number(item.groupPrice)
          })

          let product = []
          this.form.stock = 0
          this.form.product.forEach(item => {
            if (item.goodsSpecProducts) {
              item.goodsSpecProducts.forEach(specItem => {
                let { prdId, groupPrice, stock } = specItem
                let goodsId = item.goodsId
                product.push({ goodsId, prdId, groupPrice: Number(groupPrice), stock: Number(stock) })
                this.form.stock += Number(stock)
              })
            } else {
              let { goodsId, prdId, groupPrice, stock } = item
              product.push({ goodsId, prdId, groupPrice, stock: Number(stock) })
              this.form.stock += Number(stock)
            }
          })
          console.log(product)

          if (this.isEdite) {
            console.log(this.form, 'form--')
            updateGroupBuy(this.form).then(res => {
              console.log('updateGroupBuy', res)
              if (res.error === 0) {
                this.$message.success(res.message)
                this.$emit('addGroupBuySubmit')
              } else {
                this.$message.warning(res.message)
              }
            }).catch(e => {
              console.log(e)
              this.$message.warning(e.message)
            })
          } else {
            // this.form.product = groupProduct
            console.log({ ...this.form, product })
            addGroupBuyActivity({ ...this.form, product }).then(res => {
              console.log('addGroupBuyActivity', res)
              if (res.error === 0) {
                this.$message.success(res.message)
                this.$emit('addGroupBuySubmit')
              } else {
                this.$message.warning(res.message)
              }
            })
          }
        } else {
          this.$message.warning('请正确填写表单!')
        }
      })
      this.submitStatus = false
    },
    // 选择商品弹窗
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 获取商品ids
    choosingGoodsResult (row) {
      console.log(row, 'get row')

      this.goodsIdList = row.map(item => { return item.goodsId })

      this.goodsRow = row
      this.form.goodsId = this.goodsIdList.join(',')
      console.log(this.form.goodsId, 'goodsId--')

      this.form.product = row
      // 可编辑状态
      this.disabledFlag = false
    },
    getProductdata ({ goodsId, prdInfo }) {
      console.log(goodsId, prdInfo)
      let target = this.form.product.find(item => { return item.goodsId === goodsId })
      let index = this.form.product.findIndex(item => { return item.goodsId === goodsId })
      this.$set(this.form.product[index], 'groupBuyPrice', prdInfo[0].secKillPrice)
      this.$set(this.form.product[index], 'stock', prdInfo[0].stock)
      target.goodsSpecProducts = prdInfo
      this.changePriceInput(target, true)
      this.changeStockInput(target, true)
    },
    initEditProduct (goods) {
      let newdata = []
      goods.forEach(item => {
        console.log(item)
        // let expand = item.product.length < 2 ? { ...item.product[0] } : { ...item.product[0], goodsSpecProducts: item.product }
        newdata.push({ ...item })
      })
      return newdata
    },
    showSpec (goodsInfo) {
      this.productInfo = goodsInfo
      this.showSpecDialog = true
      console.log(this.showSpecDialog)
    },
    changePriceInput (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.priceErrorMsg = null
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          if (!isDialog) item.groupPrice = goodsInfo.groupPrice
          if (this.validatePrdPrice(item) && !goodsInfo.priceErrorMsg) {
            goodsInfo.priceErrorMsg = '有规格秒杀价大于原价，请修改'
          }
        })
      }
    },
    changeStockInput (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.stockErrorMsg = null
        goodsInfo.totalStock = 0
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          if (!isDialog) item.stock = goodsInfo.stock
          goodsInfo.totalStock += parseInt(item.stock)
          if (this.validatePrdStock(item) && !goodsInfo.stockErrorMsg) {
            goodsInfo.stockErrorMsg = '有规格秒杀库存大于原库存，请修改'
          }
        })
      }
    },
    // 确认选择优惠券-新增
    handleToCheck (data, index) {
      console.log(data, 'coupon data---', index, 'index---')
      this.couponIdList = data.map(item => item.id)
      if (this.rewardCouponList.length < 5) {
        this.rewardCouponList = data
        this.rewardCouponIds = []
        this.rewardCouponList.map((item, index) => {
          this.rewardCouponIds.push(item.id)
        })
      } else {
        this.$message.warning('优惠券不能超过五张')
        return false
      }
    },
    // 删除鼓励奖优惠券图片
    deleteCouponImg (index) {
      this.rewardCouponList.splice(index, 1)
      this.rewardCouponIds.splice(index, 1)
      this.couponIdList.splice(index, 1)
    },
    // 优惠券点击事件
    handleToCallDialog () {
      this.showCouponDialog = !this.showCouponDialog
    },
    // 活动时间时间选择
    dateChange (date) {
      this.form.startTime = date[0]
      this.form.endTime = date[1]
      console.log(date)
    },
    // 设置数据
    setCurrent (index) {
      // 拷贝一份数据
      let price = JSON.parse(JSON.stringify(this.form.product))
      switch (index) {
        case 1:
          price.forEach(row => {
            row.groupPrice = price[0].groupPrice
            console.log(row)
            this.changePriceInput(row)
          })
          this.activeIndex = 1
          break
        case 2:
          price.forEach(row => {
            row.grouperPrice = price[0].grouperPrice
          })
          this.activeIndex = 2
          break
        case 3:
          price.forEach(row => {
            row.stock = price[0].stock
            this.changeStockInput(row)
          })
          this.activeIndex = 3
          break
      }
      this.form.product = price
    },

    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 校验拼团价格
    checkGroupPriceVaild (rule, value, callback) {
      console.log('拼团价格校验', rule, value, callback)
      if (value === undefined || JSON.stringify(value) === '{}') {
        callback(new Error('拼团价格不能为空'))
      }
      callback()
    },
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    handleSelectImg (res) {
      if (res != null) {
        this.form.share.shareImg = res.imgPath
      }
    }
  }
}
</script>
<style scoped>
.prompt {
  color: #999;
  margin-left: 20px;
  display: inline;
}

.fontColor {
  color: #606266;
}

.box-card {
  width: 630px;
  background: #f5f5f5;
}

.middleContainer {
  display: flex;
}

.deleteIcon {
  position: relative;
  width: 17px;
  height: 17px;
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

.rewardCouponInfo {
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
}

.picture {
  margin-top: 10px;
}

.textDesc {
  line-height: normal;
  margin-top: -38px;
  color: #999;
}

.couponImgWrapper {
  width: 100%;
  height: 100%;
  border: 1px solid #fbb;
  border-radius: 10px;
}

.coupon_list_top {
  margin-top: 10px;
  color: #f60;
}

.coupon_list_top:nth-of-type(2) {
  font-size: 20px;
  font-weight: bold;
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
  font-size: 12px;
  background-repeat: repeat-x;
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

.wrapper {
  margin: 10px 0;
  padding: 10px 0;
  margin-bottom: 80px;
  background: #fff;
}

.tableHeader th {
  border: none;
  line-height: 0 !important;
  height: 36px !important;
  font-weight: bold;
  padding: 8px 10px;
  font-weight: bold;
}

.moreSetUp a {
  margin-right: 10px;
  cursor: pointer;
}

.settings {
  color: #5a8bff;
}

.el-table tbody .el-table__row {
  height: 200px !important;
}
.shareContent a {
  text-decoration: none;
  color: #409eff;
}
.imgContent {
  width: 70px;
  height: 70px;
  text-align: center;
  line-height: 65px;
  margin-left: 60px;
  border: 1px solid #e4e4e4;
  cursor: pointer;
  box-sizing: border-box;
}
.imgContent .shareImg {
  width: 100%;
  height: 100%;
}
.picSizeTips {
  display: block;
  line-height: 80px;
  margin-left: 20px;
  color: rgb(153, 153, 153);
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
</style>
