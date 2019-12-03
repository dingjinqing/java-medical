<!--
添加拼团活动
@author 孔德成
-->
<template>
  <div>
    <wrapper>
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
              v-model.trim="form.name"
              style="width: 170px"
            ></el-input>
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
            empty-text='暂无数据'
          >
            <el-table-column
              align="center"
              prop="prdDesc"
              :label="$t('groupBuy.goodsNmaeProduct')"
            >
            </el-table-column>
            <el-table-column
              align="center"
              prop="prdPrice"
              :label="$t('groupBuy.originalPrice')"
            >
            </el-table-column>
            <el-table-column
              align="center"
              :label="$t('groupBuy.groupBuyPrice')"
            >
              <template slot="append">
                <span>{{$t('groupBuy.groupBuyPrice')}}</span>
                <el-button
                  @click="setCurrent(1)"
                  size="small"
                  icon="el-icon-edit"
                >{{$t('groupBuy.batchOption')}}
                </el-button>
              </template>
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.groupPrice'"
                  :rules="[
                    { required: true, message: '拼团价不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.prdPrice)}, trigger: 'blur' }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.groupPrice"
                    size="small"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              prop="grouperPrice"
              :label="$t('groupBuy.commanderPrice')"
              v-if="form.isGrouperCheap === 1"
            >
              <template slot="append">
                <span>{{$t('groupBuy.commanderPrice')}}</span>
                <el-button
                  @click="setCurrent(2)"
                  size="mini"
                  icon="el-icon-edit"
                >{{$t('groupBuy.batchOption')}}
                </el-button>
              </template>
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.grouperPrice'"
                  :rules="[
                    { required: true, message: '团长价不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.prdPrice)}, trigger: 'blur' }
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
            <el-table-column
              align="center"
              prop="prdNumber"
              :label="$t('groupBuy.originalStock')"
            >
            </el-table-column>
            <el-table-column
              align="center"
              prop="stock"
              :label="$t('groupBuy.groupBuyStock')"
            >
              <template slot="append">
                <span>{{$t('groupBuy.groupBuyStock')}}</span>
                <el-button
                  @click="setCurrent(3)"
                  size="mini"
                  icon="el-icon-edit"
                >{{$t('groupBuy.batchOption')}}
                </el-button>
              </template>
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.stock'"
                  :rules="[
                    { required: true, message: '拼团库存不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.prdNumber)}, trigger: 'blur' }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <el-input
                    v-model="scope.row.stock"
                    size="small"
                  />
                </el-form-item>
              </template>
            </el-table-column>
            <template
              slot="empty"
              style="height：0"
            >
            </template>

            <div
              slot="append"
              class="moreSetUp"
            >
              <span>更多设置:</span>
              <a
                :class="activeIndex === 1 ? '' : 'settings'"
                @click="setCurrent(1)"
              >拼团价
              </a>
              <a
                :class="activeIndex === 2 ? '' : 'settings'"
                @click="setCurrent(2)"
              >团长优惠价
              </a>
              <a
                :class="activeIndex === 3 ? '' : 'settings'"
                @click="setCurrent(3)"
              >拼团库存
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
            :picker-options="pickerOptions"
            range-separator="-"
            :start-placeholder="$t('groupBuy.startDate')"
            :end-placeholder="$t('groupBuy.endDate')"
            align="right"
            value-format="yyyy-MM-dd HH:mm:ss"
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
          ></el-switch>
          <div class="prompt">{{$t('groupBuy.openIsDefaultComment')}}</div>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.shippingOption') + '：'">
          <el-radio-group v-model="form.shippingType">
            <el-radio :label=1>{{$t('groupBuy.freeShipping')}}</el-radio>
            <el-radio :label=2>{{$t('groupBuy.shippingOptionComment')}}</el-radio>
          </el-radio-group>
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
                    <div class="coupon_list_top">
                      <span>￥</span>
                      <span class="number">{{item.denomination}}</span>
                    </div>
                    <div class="coupon_center_limit">{{item.useConsumeRestrict |
                      formatLeastConsume(item.leastConsume)}}
                    </div>
                    <div class="coupon_center_number">剩余{{item.surplus}}张</div>
                    <div
                      class="coupon_list_bottom"
                      style="font-size: 12px"
                    >领取
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
          <el-form-item label="活动分享：">
            <actShare :shareConfig="form.share" />
          </el-form-item>
        </div>

      </el-form>

      <!--添加商品弹窗-->
      <choosingGoods
        @resultGoodsRow="choosingGoodsResult"
        :chooseGoodsBack="[form.goodsId]"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :singleElection="true"
        :showTips="true"
      />

      <!--添加优惠卷-->
      <addCouponDialog
        @handleToCheck="handleToCheck"
        :tuneUpCoupon="showCouponDialog"
        :couponBack="couponIdList"
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
    </wrapper>

  </div>
</template>
<script>

import { mapActions } from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
import choosingGoods from '@/components/admin/choosingGoods'
import addCouponDialog from '@/components/admin/addCouponDialog'
import actShare from '@/components/admin/marketManage/marketActivityShareSetting'
import { getAllGoodsProductList } from '@/api/admin/brandManagement.js'
import { addGroupBuyActivity, updateGroupBuy } from '@/api/admin/marketManage/spellGroup.js'
import { getSelectGoods } from '@/api/admin/marketManage/distribution.js'
import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
// import couponStyles from './couponStyle_s'

export default {
  components: {
    wrapper,
    choosingGoods,
    addCouponDialog,
    actShare
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
    return {
      // from 表单数据
      form: {
        id: null,
        name: '',
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
        share: {
          share_action: 1,
          share_doc: '',
          share_img_action: 1,
          share_img: ''
        },
        product: []
      },
      // 校验表单
      fromRules: {
        name: [
          { required: true, message: this.$t('groupBuy.activityNameRequiredRules'), trigger: 'blur' },
          { max: 20, message: this.$t('groupBuy.lengthMax20'), trigger: 'blur' }
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
          { validator: dateValid, trigger: 'blur' }
        ]
      },
      // 选中商品id
      goodsRow: {},
      goodsIds: [],
      // 优惠卷弹窗
      couponDialogFlag: false,
      couponList: [],

      rewardCouponList: [],
      rewardCouponIds: [],
      // 时间控件
      pickerOptions: {
        shortcuts: [{
          text: this.$t('groupBuy.lastWeek'),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('groupBuy.lastmonth'),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('groupBuy.lastThreeMonths'),
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
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

      activeIndex: 0
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
    validateMoney (rule, value, callback, prdPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > prdPrice) {
        callback(new Error('拼团价不能大于商品原价'))
      } else {
        callback()
      }
    },
    validateNum (rule, value, callback, prdNumber) {
      var re = /^[1-9]\d*$/
      if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else if (value > prdNumber) {
        callback(new Error('拼团库存不能大于商品库存'))
      } else {
        callback()
      }
    },

    // 编辑活动初始化
    editActivityInit () {
      console.log('this.isEdite', this.isEdite)
      if (this.isEdite) {
        let data = this.editData
        this.form.id = data.id
        this.form.activityType = data.activityType
        this.form.name = data.name
        this.form.goodsId = data.goodsId
        this.getGoodsInfo(data.goodsId)
        this.form.isGrouperCheap = data.isGrouperCheap
        this.form.product = data.productList
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
      this.rewardCouponList = []
      ids.map((item, index) => {
        updateCoupon(item).then((res) => {
          if (res.error === 0) {
            this.rewardCouponList.push(res.content[0])
          }
        })
      })
      this.couponIdList = this.getCouponIdsArray(this.rewardCouponList)
    },

    // 提交表单
    submitForm (formName) {
      this.submitStatus = true
      if (this.rewardCouponIds) {
        this.form.rewardCouponId = this.rewardCouponIds.join(',')
      }
      this.$refs['form'].validate((valid) => {
        console.log('提交表单', formName)
        console.log('this.form', this.form)
        if (valid) {
          if (this.isEdite) {
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
            addGroupBuyActivity(this.form).then(res => {
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
      console.log('初始化商品弹窗', this.form.goodsId)
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 获取商品ids
    choosingGoodsResult (row) {
      this.goodsRow = row
      this.form.goodsId = row.goodsId
      if (Object.keys(row).length === 0) {
        return
      }
      // 初始化规格表格
      getAllGoodsProductList(this.form.goodsId).then(res => {
        console.log('product', res.content)
        res.content.forEach((item, index) => {
          item.index = index
        })
        this.form.product = res.content
        console.log(' this.form.product ', this.form.product)
      })
    },
    // 确认选择优惠券-新增
    handleToCheck (data, index) {
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
    },
    // 选择优惠券弹窗-砍价失败后向买家赠送
    handleToCallDialog () {
      this.showCouponDialog = !this.showCouponDialog
      this.couponIdList = this.getCouponIdsArray(this.rewardCouponList)
    },
    getCouponIdsArray (data) {
      let res = []
      data.forEach((item, index) => {
        res.push(item)
      })
      return res
    },

    arrayToString (arr) {

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
  background-image: url("http://mpdevimg2.weipubao.cn/image/admin/coupon_border.png");
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
  margin: 10px 0 !important;
  padding: 10px 0;
  margin-bottom: 80px !important;
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
</style>
