<template>
  <div class="content">
    <div class="main">
      <div class="coupon_settings">
        <!-- 左侧内容 -->
        <div class="left_preview">
          <div
            class="left_top"
            :style="`background-image: url(${$imageHost}/image/admin/cou_top_bg.png)`"
          >
            {{$t('couponPackage.couponPackage')}}
          </div>
          <div class="left_content">
            <div class="content_bg">
              <img :src="$imageHost + '/image/admin/cou_package_bg.png'">
            </div>
            <div class="content_info">
              <div class="text_title">{{param.packName?param.packName:$t('couponPackage.couponPackage')}}</div>
              <div class="package_info">
                <div class="package_title">
                  ——— <span>{{$t('couponPackage.couponPackage')}}</span> ———
                </div>
                <div class="coupon_info">
                  <div
                    class="coupon_item"
                    v-for="(item, index) in coupon_info"
                    :key="index"
                  >
                    <div class="coupon_left">
                      <div
                        class="coupon_price"
                        v-if="item.actCode == 'voucher'"
                      >￥<span>{{item.denomination}}</span></div>
                      <div
                        class="coupon_price"
                        v-else
                      ><span>{{item.denomination}}</span>{{$t('addCouponPackage.discount')}}</div>
                      <div class="coupon_rule">{{item.useConsumeRestrict > 0? `$t('addCouponPackage.full')${item.leastConsume}$t('addCouponPackage.yuan')$t('addCouponPackage.available')` : $t('addCouponPackage.unrestricted')}}</div>
                    </div>
                    <div class="coupon_middle">
                      <img
                        :src="$imageHost + '/image/admin/cou_midd_icon.png'"
                        alt=""
                      >
                    </div>
                    <div class="coupon_right">
                      <div class="coupon_name">{{item.actName}}</div>
                      <div class="coupon_limits">{{item.recommendCatId || item.recommendGoodsId || item.recommendSortId ? $t('addCouponPackage.forSpecifiedGoods') : $t('addCouponPackage.forAllGoods')}}></div>
                      <div class="coupon_time">2019-08-21--2019-08-31</div>
                      <div class="coupon_icon">{{item.send_num ?item.send_num:0}}{{$t('addCouponPackage.sheet')}}</div>
                    </div>
                  </div>
                </div>
                <div class="btn_get">{{$t('addCouponPackage.receiveNow')}}</div>
              </div>
              <div class="package_rule">
                <div class="rule_title">{{$t('addCouponPackage.rule')}}</div>
                <div
                  class="rule_info"
                  v-html="crlfFormat"
                ></div>
              </div>
            </div>
          </div>
        </div>
        <!-- 左侧内容end -->
        <!-- 右侧内容 -->
        <el-form
          class="right_settings"
          :model="param"
          :rules="formRules"
          ref="form"
        >
          <div class="set_box">
            <p class="set_title">{{$t('addCouponPackage.basicSettings')}}</p>
            <div class="set_item">
              <div class="item_title">
                <em>*</em> {{$t('addCouponPackage.activityName')}}：
              </div>
              <el-form-item
                class="item_right"
                prop="actName"
              >
                <el-input
                  v-model="param.actName"
                  :placeholder="$t('addCouponPackage.actNamePlaceholder')"
                  size="small"
                  class="default_input"
                  maxlength="10"
                ></el-input>
                <span class="item_tips">{{$t('addCouponPackage.actNameTip')}}</span>
              </el-form-item>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> {{$t('marketCommon.validDate')}}：</div>
              <el-form-item
                class="item_right"
                prop="effectiveDate"
              >
                <el-date-picker
                  v-model="param.effectiveDate"
                  type="datetimerange"
                  :range-separator="$t('marketCommon.to')"
                  :start-placeholder="$t('marketCommon.startTime')"
                  :end-placeholder="$t('marketCommon.endTime')"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  size="small"
                >
                </el-date-picker>
              </el-form-item>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> {{$t('couponPackage.packName')}}：</div>
              <el-form-item
                class="item_right"
                prop="packName"
              >
                <el-input
                  v-model="param.packName"
                  :placeholder="$t('addCouponPackage.packNamePlaceholder')"
                  size="small"
                  class="default_input"
                  maxlength="8"
                ></el-input>
                <span class="item_tips">{{$t('addCouponPackage.packNameTip')}}</span>
              </el-form-item>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> {{$t('addCouponPackage.packageContent')}}：</div>
              <div class="item_right">
                <a
                  style="color:#409eff;font-size:12px;cursor: pointer;"
                  @click="isEditFlag?'':handleToCallDialog()"
                >{{$t('addCouponPackage.addCoupon')}}</a>
                <span class="item_tips">{{$t('addCouponPackage.addCouponTip')}}</span>
              </div>
            </div>
            <div
              class="coupon_set_box"
              v-if="coupon_info.length"
            >
              <div class="coupon_set_table">
                <el-table
                  :data="coupon_info"
                  border
                  style="width: 100%"
                >
                  <el-table-column :label="$t('addCouponPackage.couponInfo')">
                    <template slot-scope="scope">
                      <div class="coupon_info">
                        <span class="coupon_name">{{scope.row.actName}}</span>
                        <div
                          class="coupon_price"
                          v-if="scope.row.actCode == 'voucher'"
                        >￥<span>{{scope.row.denomination}}</span></div>
                        <div
                          class="coupon_price"
                          v-else
                        ><span>{{scope.row.denomination}}</span>{{$t('addCouponPackage.discount')}}</div>
                        <div class="coupon_rule">{{scope.row.useConsumeRestrict > 0? $t('addCouponPackage.full') + scope.row.leastConsume + $t('addCouponPackage.yuan') + $t('addCouponPackage.available')  : $t('addCouponPackage.unrestricted')}}</div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    :label="$t('addCouponPackage.sendNum')"
                    width="120"
                  >
                    <template slot-scope="scope">
                      <div>
                        <el-input-number
                          :disabled="isEditFlag"
                          v-model="scope.row.send_num"
                          size="small"
                          :min="scope.row.strategyAmount ? scope.row.strategyAmount : 1"
                          :max="6"
                          :precision="0"
                          style="width:100px;"
                        ></el-input-number>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    :label="$t('addCouponPackage.sendStrategy')"
                    width="180"
                  >
                    <template slot-scope="scope">
                      <div class="coupon_set_p">
                        <p v-if="scope.row.coupon_set.immediatelyGrantAmount > 0">{{$t('addCouponPackage.sendStrategyImmediatelyTip')}}{{scope.row.coupon_set.immediatelyGrantAmount}}{{$t('addCouponPackage.sheet')}}</p>
                        <p v-if="scope.row.coupon_set.timingEvery > 0 && scope.row.coupon_set.timingAmount > 0">
                          <span v-if="scope.row.coupon_set.timingUnit ==='0'">{{$t('addCouponPackage.afterPurchase')}}{{$t('addCouponPackage.every')}}{{scope.row.coupon_set.timingEvery}}{{$t('addCouponPackage.day')}}{{$t('addCouponPackage.issue')}}{{scope.row.coupon_set.timingAmount}}{{$t('addCouponPackage.sheet')}}</span>
                          <span v-if="scope.row.coupon_set.timingUnit ==='1'">{{$t('addCouponPackage.afterPurchase')}}{{$t('addCouponPackage.every')}}{{scope.row.coupon_set.timingEvery}}{{$t('addCouponPackage.week')}}的周{{scope.row.coupon_set.timingTime}}{{$t('addCouponPackage.issue')}}{{scope.row.coupon_set.timingAmount}}{{$t('addCouponPackage.sheet')}}</span>
                          <span v-if="scope.row.coupon_set.timingUnit ==='2'">{{$t('addCouponPackage.afterPurchase')}}{{$t('addCouponPackage.every')}}{{scope.row.coupon_set.timingEvery}}{{$t('addCouponPackage.month')}}月的{{scope.row.coupon_set.timingTime}}号{{$t('addCouponPackage.issue')}}{{scope.row.coupon_set.timingAmount}}{{$t('addCouponPackage.sheet')}}</span>
                        </p>
                        <a
                          style="color:#409eff;cursor: pointer;"
                          @click.prevent="isEditFlag?'':handleCouponSet(scope)"
                        >{{scope.row.coupon_set.immediatelyGrantAmount > 0 || (scope.row.coupon_set.timingEvery > 0 && scope.row.coupon_set.timingTime > 0) ? $t('addCouponPackage.reset') : $t('addCouponPackage.setting')}}</a>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    :label="$t('marketCommon.operate')"
                    width="60"
                  >
                    <template slot-scope="scope">
                      <div style="text-align:center">
                        <a
                          style="color:#409eff;cursor: pointer;"
                          @click.prevent="isEditFlag?'':handleCouponDel(scope.$index)"
                        >{{$t('marketCommon.delete')}}</a>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <div class="bottom_tips_box">
                <span>{{$t('addCouponPackage.tips')}}:</span>
                <div class="tips_content">
                  <p>1.{{$t('addCouponPackage.tip1')}}</p>
                  <p>2.{{$t('addCouponPackage.tip2')}}</p>
                  <p>3.{{$t('addCouponPackage.tip3')}}</p>
                </div>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> {{$t('addCouponPackage.limitGetTimes')}}：</div>
              <el-form-item
                class="item_right"
                prop="limitGetTimes"
              >
                <el-input-number
                  v-model="param.limitGetTimes"
                  placeholder=""
                  :precision="0"
                  :min="0"
                  size="small"
                  class="small_input"
                ></el-input-number>
                <span class="item_tips">{{$t('addCouponPackage.limitGetTimesTip')}}</span>
              </el-form-item>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> {{$t('addCouponPackage.totalAmount')}}：</div>
              <el-form-item
                class="item_right"
                prop="totalAmount"
              >
                <el-input-number
                  v-model="param.totalAmount"
                  placeholder=""
                  :precision="0"
                  :min='1'
                  size="small"
                  class="small_input"
                ></el-input-number>
                <span class="item_tips">{{$t('addCouponPackage.totalAmountTip')}}</span>
              </el-form-item>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> {{$t('addCouponPackage.packAccessMode')}}：</div>
              <div class="item_right">
                <p>
                  <el-radio
                    v-model="param.accessMode"
                    :label='0'
                  >{{$t('couponPackage.accessModeCash')}}</el-radio>
                  <el-radio
                    v-model="param.accessMode"
                    :label='1'
                  >{{$t('couponPackage.accessModeTntegral')}}</el-radio>
                  <el-radio
                    v-model="param.accessMode"
                    :label='2'
                  >{{$t('couponPackage.accessModeFree')}}</el-radio>
                </p>
                <p class="package_get_choose">
                  <span v-if="param.accessMode===0">
                    {{$t('addCouponPackage.payable')}}：<el-input-number
                      v-model="param.accessCost"
                      class="small_input"
                      :precision="2"
                      size="small"
                    ></el-input-number> {{$t('addCouponPackage.yuan')}}，
                  </span>
                  <span v-if="param.accessMode===1">
                    {{$t('addCouponPackage.payable')}}：<el-input-number
                      v-model="param.accessCost"
                      class="small_input"
                      :precision="0"
                      size="small"
                    ></el-input-number> {{$t('addCouponPackage.integral')}}，
                  </span>
                  <span v-if="param.accessMode!==2">
                    {{$t('addCouponPackage.accessModeTip')}} <span>{{totalCouponAmount}}</span>{{$t('addCouponPackage.yuan')}}
                  </span>
                </p>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> {{$t('addCouponPackage.rule')}}：</div>
              <el-form-item
                class="item_right"
                prop="actRule"
              >
                <el-input
                  v-model="param.actRule"
                  :placeholder="$t('addCouponPackage.rulePlaceholder')"
                  type="textarea"
                  :rows="5"
                  resize="none"
                ></el-input>
              </el-form-item>
            </div>
          </div>
        </el-form>
        <!-- 右侧内容end -->
      </div>
    </div>
    <div class="footer">
      <el-button
        @click="isEditFlag?updateSubmit():addSubmit()"
        type="primary"
        size="small"
      >{{$t('marketCommon.save')}}</el-button>
    </div>
    <!--添加优惠卷-->
    <AddCouponDialog
      origin="couponPackage"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
      @handleToCheck="handleToCheck"
    />

    <!-- 设置优惠券内容 -->
    <el-dialog
      :visible.sync="couponSetDialogFlag"
      :title="$t('addCouponPackage.setSendStrategy')"
      custom-class="couponSetDialog"
      center
    >
      <div class="coupon_info_set">
        <p>{{$t('addCouponPackage.sendStrategyImmediatelyTip')}} <el-input-number
            v-model="coupon_set.immediatelyGrantAmount"
            placeholder=""
            size="small"
            style="width:100px;"
          ></el-input-number> {{$t('addCouponPackage.sheet')}}</p>
        <p>{{$t('addCouponPackage.afterPurchase')}}{{$t('addCouponPackage.every')}} <el-input-number
            v-model="coupon_set.timingEvery"
            placeholder=""
            size="small"
            style="width:100px;"
          ></el-input-number>
          <el-select
            v-model="coupon_set.timingUnit"
            size="small"
            style="width:100px;"
          >
            <el-option
              v-for="item in coupon_set_date"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </p>
        <p style="padding-left: 28px;">
          <span v-if="coupon_set.timingUnit === '1'">
            {{$t('addCouponPackage.every')}}
            <el-select
              v-model="coupon_set.timingTime"
              size="small"
              style="width:100px;"
            >
              <el-option
                v-for="(item,index) in 7"
                :key="index"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
          </span>
          <span v-else-if="coupon_set.timingUnit === '2'">
            {{$t('addCouponPackage.every')}}月
            <el-select
              v-model="coupon_set.timingTime"
              size="small"
              style="width:100px;"
            >
              <el-option
                v-for="(item,index) in 31"
                :key="index"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
            号
          </span>
          <span></span>
          {{$t('addCouponPackage.issue')}} <el-input-number
            v-model="coupon_set.timingAmount"
            placeholder=""
            size="small"
            style="width:100px;"
          ></el-input-number> {{$t('addCouponPackage.sheet')}}</p>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          @click="couponSetDialogFlag = false"
          size="small"
        >{{$t('marketCommon.cancel')}}</el-button>
        <el-button
          type="primary"
          @click="confrimCouponSet()"
          size="small"
        >{{$t('marketCommon.ok')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addCouponPackage, getCouponPackById, updateCouponPackage } from '@/api/admin/marketManage/couponPackage.js'
export default {
  components: {
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  data () {
    return {
      param: {
        actName: '',
        packName: '',
        limitGetTimes: '',
        totalAmount: '',
        actRule: '',
        accessMode: 0,
        effectiveDate: '',
        couponPackVoucher: []
      },
      coupon_info: [],
      couponDialogFlag: false,
      couponSetDialogFlag: false,
      accessCost: {
        access_cost1: '',
        access_cost2: ''
      },
      coupon_set: {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      },

      coupon_set_date: [
        {
          value: '0',
          label: this.$t('addCouponPackage.day')
        }, {
          value: '1',
          label: this.$t('addCouponPackage.week')
        }, {
          value: '2',
          label: this.$t('addCouponPackage.month')
        }
      ],
      target: null,
      isEditFlag: false,
      actId: null,
      showCouponDialog: false,
      // 表单约束
      formRules: {
        actName: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        packName: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        limitGetTimes: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        totalAmount: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        actRule: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'blur' }
        ],
        effectiveDate: [
          { required: true, message: this.$t('promoteList.check'), trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    // 选择优惠券弹窗
    handleToCallDialog () {
      // let obj = {
      //   couponDialogFlag: !this.couponDialogFlag,
      //   couponList: this.coupon_info
      // }
      this.showCouponDialog = !this.showCouponDialog
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data) {
      let couponArr = this.formatCoupon(data)
      let oldArr = this.unique([...this.coupon_info, ...couponArr], 'id')
      let couponKey = []
      couponArr.map((item) => {
        couponKey.push(item.id)
      })
      this.coupon_info = oldArr.filter((item) => {
        return couponKey.includes(item.id)
      })
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
      let temStrategy = JSON.parse(JSON.stringify(this.coupon_set))
      if (temStrategy.immediatelyGrantAmount > this.coupon_info[this.target].send_num) {
        this.$message.warning(this.$t('addCouponPackage.validStrategy1'))
        return false
      }
      if (temStrategy.timingEvery > 0 && (temStrategy.immediatelyGrantAmount + temStrategy.timingAmount > this.coupon_info[this.target].send_num)) {
        this.$message.warning(this.$t('addCouponPackage.validStrategy2'))
        return false
      }
      this.coupon_info[this.target].strategyAmount = temStrategy.immediatelyGrantAmount + temStrategy.timingAmount
      this.coupon_info[this.target].coupon_set = temStrategy
      this.couponSetDialogFlag = false
      console.log(this.coupon_info)
    },
    // 删除
    handleCouponDel (index) {
      this.$confirm(this.$t('addCouponPackage.delCouponConfirm'), this.$t('addCouponPackage.prompt'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        this.coupon_info.splice(index, 1)
      }).catch(() => {

      })
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
    },
    addSubmit () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // 新建活动
          this.param.couponPackVoucher = []
          this.coupon_info.forEach(element => {
            var voucher = {}
            voucher.voucherId = element.id
            voucher.totalAmount = element.send_num
            voucher.immediatelyGrantAmount = element.coupon_set.immediatelyGrantAmount
            voucher.timingAmount = element.coupon_set.timingAmount
            voucher.timingEvery = element.coupon_set.timingEvery
            voucher.timingTime = element.coupon_set.timingTime
            voucher.timingUnit = element.coupon_set.timingUnit
            this.param.couponPackVoucher.push(voucher)
          })
          this.param.startTime = this.param.effectiveDate[0]
          this.param.endTime = this.param.effectiveDate[1]
          if (this.validParam()) {
            addCouponPackage(this.param).then((res) => {
              if (res.error === 0) {
                this.$message.success({
                  message: this.$t('marketCommon.successfulOperation')
                })
                this.$router.push({
                  name: 'coupon_package'
                })
              } else {
                this.$message.error({
                  message: this.$t('marketCommon.failureOperation')
                })
              }
            })
          }
        }
      })
    },
    updateSubmit () {
      // 更新活动
      this.param.id = this.actId
      this.param.startTime = this.param.effectiveDate[0]
      this.param.endTime = this.param.effectiveDate[1]
      updateCouponPackage(this.param).then((res) => {
        if (res.error === 0) {
          this.$message.success({
            message: this.$t('marketCommon.successfulOperation')
          })
          this.$router.push({
            name: 'coupon_package'
          })
        } else {
          this.$message.error({
            message: this.$t('marketCommon.failureOperation')
          })
        }
      })
    },
    validParam () {
      if (this.param.accessMode !== 2) {
        if (!this.param.accessCost) {
          this.$message.warning(this.$t('addCouponPackage.validaAccessCost'))
          return false
        }
      }
      if (!this.param.couponPackVoucher || this.param.couponPackVoucher.length === 0) {
        this.$message.warning(this.$t('addCouponPackage.validVoucher'))
        return false
      } else {
        let app = this
        let validFlag = false
        this.param.couponPackVoucher.forEach(function (item) {
          if (!item.totalAmount || (!item.immediatelyGrantAmount && !item.timingEvery && !item.timingAmount)) {
            app.$message.warning(app.$t('addCouponPackage.validStrategy3'))
            validFlag = true
          }
        })
        if (validFlag) {
          return false
        }
      }
      return true
    }
  },
  computed: {
    crlfFormat () {
      return this.param.actRule.replace(/\n/g, '<br />')
    },
    couponIdList () {
      return this.coupon_info.map(item => {
        return item.id
      })
    },
    totalCouponAmount () {
      let r = 0
      this.coupon_info.forEach(item => {
        if (item.actCode === 'voucher' && item.denomination && item.send_num) {
          r += item.denomination * item.send_num
        }
      })
      return r
    }
  },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      // 编辑时优惠券信息不可修改
      this.isEditFlag = true
      // 点击编辑按钮进来，初始化页面数据
      let SimpleCouponPackParam = {
        'id': this.$route.query.id
      }
      getCouponPackById(SimpleCouponPackParam).then((res) => {
        if (res.error === 0) {
          this.param = res.content
          this.param.effectiveDate = []
          this.param.effectiveDate.push(res.content.startTime)
          this.param.effectiveDate.push(res.content.endTime)
          let couponList = []
          this.param.couponPackVoucher.map(item => {
            let couponInfo = Object.assign({}, item.couponView)
            couponInfo.ischeck = true
            couponInfo.send_num = item.totalAmount
            couponInfo.coupon_set = {}
            couponInfo.coupon_set.immediatelyGrantAmount = item.immediatelyGrantAmount
            couponInfo.coupon_set.timingAmount = item.timingAmount
            couponInfo.coupon_set.timingEvery = item.timingEvery
            couponInfo.coupon_set.timingTime = item.timingTime
            couponInfo.coupon_set.timingUnit = item.timingUnit
            couponList.push(couponInfo)
          })
          this.coupon_info = couponList
        }
      })
    }
  },
  watch: {
    // data内变量国际化
    lang () {

    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  background-color: #fff;
  margin: 10px;
  margin-bottom: 62px;
  .coupon_settings {
    max-width: 960px;
    margin: 0 auto;
    display: flex;
    .left_preview {
      width: 310px;
      // max-height: 600px;
      overflow-y: auto;
      &::-webkit-scrollbar {
        width: 9px;
        height: 9px;
      }
      &::-webkit-scrollbar-track {
        border-radius: 8px;
        background-color: rgba(0, 0, 0, 0);
      }

      &::-webkit-scrollbar-track:hover {
        background-color: rgba(0, 0, 0, 0.06);
      }

      &::-webkit-scrollbar-track:active {
        background-color: rgba(0, 0, 0, 0.1);
      }

      &::-webkit-scrollbar-thumb {
        border-radius: 8px;
        background-color: rgba(0, 0, 0, 0.1);
      }

      &::-webkit-scrollbar-thumb:hover {
        background-color: rgba(0, 0, 0, 0.4);
      }

      &::-webkit-scrollbar-thumb:active {
        background: rgba(0, 0, 0, 0.6);
      }
      .left_top {
        width: 100%;
        height: 55px;
        color: #000;
        background: url("$imageHost + '/image/admin//cou_top_bg.png") no-repeat
          100%/100%;
        text-align: center;
        line-height: 75px;
      }
      .left_content {
        width: 100%;
        min-height: 450px;
        background: linear-gradient(left, #ff5666, #e6558e);
        position: relative;
        padding-bottom: 20px;
        .content_bg {
          width: 100%;
          position: absolute;
          > img {
            width: 100%;
          }
        }
        .content_info {
          position: relative;
          z-index: 2;
          .text_title {
            font-size: 35px;
            color: #fff;
            text-align: center;
            padding-top: 60px;
            font-weight: bold;
            font-style: italic;
          }
          .package_info {
            background: #f0a6b5;
            margin: 0 auto;
            width: 97%;
            margin-top: 200px;
            overflow: hidden;
            .package_title {
              line-height: 44px;
              color: #fff;
              font-size: 14px;
              text-align: center;
              > span {
                display: inline-block;
                margin: 0 10px;
                position: relative;
                &::before {
                  content: "";
                  width: 4px;
                  height: 4px;
                  background-color: #fff;
                  border-radius: 50%;
                  position: absolute;
                  top: 22px;
                  left: -14px;
                }
                &::after {
                  content: "";
                  width: 4px;
                  height: 4px;
                  background-color: #fff;
                  border-radius: 50%;
                  position: absolute;
                  top: 22px;
                  right: -14px;
                }
              }
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
            .btn_get {
              width: 280px;
              line-height: 34px;
              text-align: center;
              color: #c93f0e;
              background: linear-gradient(top, #ffd47b, #fcc02a);
              border-radius: 30px;
              font-weight: bold;
              font-size: 13px;
              margin: 10px auto;
            }
          }
          .package_rule {
            background: #f0a6b5;
            margin: 0 auto;
            width: 97%;
            margin-top: 20px;
            overflow: hidden;
            padding: 10px;
            > .rule_title {
              color: #fff;
              font-size: 14px;
              font-weight: 600;
            }
            > .rule_info {
              word-break: break-all;
              color: #fff;
              font-size: 14px;
              font-weight: 600;
            }
          }
        }
      }
    }
    .right_settings {
      flex: 1;
      margin-left: 15px;
      .set_box {
        border: 1px solid #e5e5e5;
        background: #f8f8f8;
        padding: 0 12px 22px;
        border-radius: 3px;
        .set_title {
          border-bottom: 1px solid #e5e5e5;
          line-height: 40px;
          font-size: 14px;
        }
        .set_item {
          display: flex;
          margin-top: 15px;
          > .item_title {
            width: 150px;
            font-size: 14px;
            text-align: right;
            line-height: 32px;
            color: #333;
            > em {
              color: red;
            }
          }
          > .item_right {
            flex: 1;
            line-height: 32px;
            > .package_get_choose {
              font-size: 14px;
              color: #333;
            }
          }
        }
        > .coupon_set_box {
          padding-left: 80px;
          > .coupon_set_table {
            .coupon_info {
              display: flex;
              flex-direction: column;
              justify-content: space-between;
              align-items: center;
              min-width: 0;
              .coupon_rule {
                color: #999;
                font-size: 12px;
              }
              .coupon_price {
                color: #f66;
                font-size: 12px;
                > span {
                  font-size: 14px;
                  font-weight: 600;
                }
              }
              .coupon_name {
                font-weight: bold;
                font-size: 12px;
                text-overflow: ellipsis;
                overflow: hidden;
                white-space: nowrap;
                min-width: 0;
              }
            }
          }
          > .bottom_tips_box {
            font-size: 12px;
            color: #999;
            padding-left: 20px;
            display: flex;
            line-height: 32px;
            > .tips_content {
              flex: 1;
            }
          }
        }
      }
    }
  }
}
.coupon_set_p {
  text-align: center;
  > p {
    font-size: 12px;
  }
}
.default_input {
  width: 150px;
}
.small_input {
  width: 160px;
}
.coupon_info_set {
  display: flex;
  flex-direction: column;
  margin-bottom: -15px;
  > p {
    margin-bottom: 15px;
  }
}
.content {
  /deep/ .couponSetDialog {
    width: 390px;
    .el-dialog__header {
      background: #f3f3f3;
      padding-top: 10px;
      .el-dialog__title {
        font-size: 14px;
      }
      .el-dialog__headerbtn {
        top: 10px;
      }
    }
  }
}
.footer {
  position: fixed;
  bottom: 0;
  left: 160px;
  right: 10px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  z-index: 3;
}
.item_tips {
  color: #999;
  font-size: 12px;
}
</style>
