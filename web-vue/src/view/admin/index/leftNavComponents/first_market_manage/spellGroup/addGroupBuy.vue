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
        :label="$t('groupBuy.groupBuyActivity')"
        prop="resource"
      >
        <el-radio-group :disabled ='isEdite' v-model="form.activityType">
          <el-radio :label=1>{{$t('groupBuy.grouponType')[0].label}}</el-radio>
          <el-radio :label=2>{{$t('groupBuy.grouponType')[1].label}}</el-radio>
        </el-radio-group>
        <div class="prompt">
          {{$t('groupBuy.groupBuyActivityComment')}}
        </div>
      </el-form-item>
      <el-form-item
        :label="$t('groupBuy.activityName')"
        prop="name"
      >
        <el-col :span="8">
          <el-input v-model="form.name"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item
        :label="$t('groupBuy.goodsName')"
        prop="goodsId"
      >
        <el-button
                :disabled="isEdite"
          size="small"
          @click="showChoosingGoods"
        >{{$t('groupBuy.selectGoods')}}</el-button>
        <el-col :span="8">
          <el-input
            :disabled="true"
            v-model="goodsRow.goodsName"
            v-if="goodsRow.ischecked"
          ></el-input>
          <el-input
            :disabled="true"
            v-if="false"
            v-model="form.goodsId"
          ></el-input>
        </el-col>
      </el-form-item>
      <el-form-item :label="$t('groupBuy.commanderDiscounts')">
        <el-switch
          :disabled="isEdite"
          v-model="form.isGrouperCheap"
          :active-value=1
          :inactive-value=0
        ></el-switch>
        <div class="prompt">{{$t('groupBuy.commanderDiscountsComment1')}}
          <p>
            <span style="color: red;">{{$t('marketCommon.note')}}：</span>
            {{$t('groupBuy.commanderDiscountsComment2')}} </p>
        </div>
      </el-form-item>
      <el-form-item :label="$t('groupBuy.discountsOption')">
        <el-table
          :data="form.product"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="prdDesc"
            :label="$t('groupBuy.goodsNmaeProduct')"
          >
          </el-table-column>
          <el-table-column
            prop="prdPrice"
            :label="$t('groupBuy.originalPrice')"
          >
          </el-table-column>
          <el-table-column
            prop="groupPrice"
            :label="$t('groupBuy.groupBuyPrice')"
          >
            <template
              slot="header"
            >
              <span>{{$t('groupBuy.groupBuyPrice')}}</span>
              <el-button
                @click="setCurrent(1)"
                size="mini"
                icon="el-icon-edit"
              >{{$t('groupBuy.batchOption')}}</el-button>
            </template>
            <template slot-scope="scope">
              <el-input v-model="scope.row.groupPrice" />
            </template>
          </el-table-column>
          <el-table-column
            v-if="form.isGrouperCheap === 1"
            prop="grouperPrice"
            :label="$t('groupBuy.commanderPrice')"
          >
            <template
              slot="header"
            >
              <span>{{$t('groupBuy.commanderPrice')}}</span>
              <el-button
                @click="setCurrent(2)"
                size="mini"
                icon="el-icon-edit"
              >{{$t('groupBuy.batchOption')}}</el-button>
            </template>
            <template slot-scope="scope">
              <el-input v-model="scope.row.grouperPrice" />
            </template>
          </el-table-column>
          <el-table-column
            prop="prdNumber"
            :label="$t('groupBuy.originalStock')"
          >
          </el-table-column>
          <el-table-column
            prop="stock"
            :label="$t('groupBuy.groupBuyStock')"
          >
            <template
              slot="header">
              <span>{{$t('groupBuy.groupBuyStock')}}</span>
              <el-button
                @click="setCurrent(3)"
                size="mini"
                icon="el-icon-edit"
              >{{$t('groupBuy.batchOption')}}</el-button>
            </template>
            <template slot-scope="scope">
              <el-input v-model="scope.row.stock" />
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>

      <el-form-item :label="$t('groupBuy.validDate')">
        <el-date-picker
          v-model="validityDate"
          type="datetimerange"
          @change="dateChange(validityDate)"
          :picker-options="pickerOptions"
          range-separator="~"
          :start-placeholder="$t('groupBuy.startDate')"
          :end-placeholder="$t('groupBuy.endDate')"
          align="right"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('groupBuy.limitAmount')"  prop="limitAmount">
        <el-input-number
          :disabled="isEdite"
          v-model="form.limitAmount"
          controls-position="right"
          :min="2"
        ></el-input-number>
        <div class="prompt">{{$t('groupBuy.limitAmountComment')}}</div>
      </el-form-item>
      <el-form-item :label="$t('groupBuy.orderGoodsNum')">
        <div class="prompt">{{$t('groupBuy.orderGoodsNumComment1')}}</div>
        <el-input-number
          v-model="form.limitBuyNum"
          controls-position="right"
          :min="0"
        ></el-input-number>
        <div class="prompt">{{$t('groupBuy.jian')}} {{$t('groupBuy.orderGoodsNumComment3')}}</div>
      </el-form-item>
      <el-form-item>
        <div class="prompt"> {{$t('groupBuy.orderGoodsNumComment2')}}</div>
        <el-input-number
          v-model="form.limitMaxNum"
          controls-position="right"
          :min="0"
        ></el-input-number>
        <div class="prompt">{{$t('marketCommon.jian')}} {{$t('groupBuy.orderGoodsNumComment3')}}</div>
      </el-form-item>
      <el-form-item :label="$t('groupBuy.joinLimit')" prop="joinLimit">
        <div class="prompt"> {{$t('groupBuy.joinLimitComment1')}}</div>
        <el-input-number
          v-model="form.joinLimit"
          controls-position="right"
          :min="0"
        ></el-input-number>
        <div class="prompt"> {{ $t('groupBuy.joinLimitComment2')}}{{ $t('groupBuy.joinLimitComment3')}}</div>
      </el-form-item>
      <el-form-item :label=" $t('groupBuy.openLimit')" prop="openLimit">
        <div class="prompt"> {{ $t('groupBuy.openLimitComment1')}}</div>
        <el-input-number
          v-model="form.openLimit"
          controls-position="right"
          :min="0"
        ></el-input-number>
        <div class="prompt">{{$t('groupBuy.joinLimitComment2')}} {{$t('groupBuy.openLimitComment2')}}</div>
      </el-form-item>
      <el-form-item :label="$t('groupBuy.openIsDefault')">
        <el-switch
          v-model="form.isDefault"
          :active-value=1
          :inactive-value=0
        ></el-switch>
        <div class="prompt">{{$t('groupBuy.openIsDefaultComment')}}</div>
      </el-form-item>
      <el-form-item :label="$t('groupBuy.shippingOption')">
        <el-radio-group v-model="form.shippingType">
          <el-radio :label=1>{{$t('groupBuy.freeShipping')}}</el-radio>
          <el-radio :label=2>{{$t('groupBuy.shippingOptionComment')}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="$t('groupBuy.consolationPrize')">
        <span>{{form.rewardCouponId}}</span>
        <span>
          {{$t('groupBuy.consolationPrizeComment1')}}
        </span>
        <el-button @click="handleToCallDialog">{{$t('groupBuy.addCoupon')}}</el-button>
        <span>
          {{$t('groupBuy.consolationPrizeComment2')}}
        </span>

      </el-form-item>
      <el-form-item>
        <!-- 引入活动分享模块 -->
        <actShare :shareConfig="form.share" />

      </el-form-item>
    </el-form>
    <!--添加商品弹窗-->
    <choosingGoods @resultGoodsRow="choosingGoodsResult" :tuneUpChooseGoods="isShowChoosingGoodsDialog"/>
    <!--添加优惠卷弹窗-->
    <addCouponDialog @checkReturnFormat="handleToCheck" />
  </wrapper>

  <wrapper>
    <el-row  type="flex" justify="center">
      <el-col :span="6">
        <el-button
                type="primary"
                style="width: 100%"
                :disabled="submitStatus"
                @click="submitForm(form)"
        >{{$t('marketCommon.ok')}}</el-button>
      </el-col>
    </el-row>
  </wrapper>
  </div>
</template>
<script>

import { mapActions } from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
import choosingGoods from '@/components/admin/choosingGoods'
import addCouponDialog from '@/components/admin/addCouponDialog'
import actShare from '@/components/admin/marketActivityShareSetting'
import { getAllGoodsProductList } from '@/api/admin/brandManagement.js'
import { addGroupBuyActivity, updateGroupBuy } from '@/api/admin/marketManage/spellGroup.js'
import { format } from '@/util/date'

export default {
  components: {
    wrapper,
    choosingGoods,
    addCouponDialog,
    actShare
  },
  props: ['isEdite', 'editData'],
  data () {
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
        product: [{}]
      },
      // 校验表单
      fromRules: {
        name: [
          { required: true, message: this.$t('groupBuy.activityNameRequiredRules'), trigger: 'blur' },
          { max: 20, message: this.$t('groupBuy.lengthMax20'), trigger: 'blur' }
        ],
        goodsId: [
          { required: true, message: this.$t('groupBuy.goodsIdRequireRules'), trigger: 'blur' }
        ],
        limitAmount: [
          { type: 'integer', required: true, message: this.$t('groupBuy.limitAmountRequireRules'), trigger: 'blur' }
        ],
        joinLimit: [
          { type: 'integer', required: true, message: this.$t('groupBuy.joinLimitRequireRules'), trigger: 'blur' }
        ],
        openLimit: [
          { type: 'integer', required: true, message: this.$t('groupBuy.openLimitRequireRules'), trigger: 'blur' }
        ]
      },
      // 选中商品id
      goodsRow: {},
      goodsIds: [],
      // 优惠卷弹窗
      couponDialogFlag: false,
      couponList: [],
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
      validityDate: [],
      props: ['isEdite'],
      submitStatus: false,
      grouponType: [],
      isShowChoosingGoodsDialog: false
    }
  },
  mounted () {
    console.log('addGroupBuy')
    // 初始化语言
    this.langDefault()
    this.editActivityInit()
  },
  watch: {
    lang () {
      this.grouponType = this.$t('groupBuy.grouponType')
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 提交表单
    submitForm (formName) {
      this.submitStatus = true
      this.$refs['form'].validate((valid) => {
        console.log('提交表单', formName)
        console.log('this.form', this.form)
        if (valid) {
          if (this.isEdite) {
            updateGroupBuy(this.form).then(res => {
              console.log('updateGroupBuy', res)
              if (res.error === 0) {
                this.success(res.content)
                this.$emit('addGroupBuySubmit')
              } else {
                this.fail(res.message)
              }
            }).catch(e => {
              console.log(e)
              this.fail(e.toString())
            })
          } else {
            addGroupBuyActivity(this.form).then(res => {
              console.log('addGroupBuyActivity', res)
              if (res.error === 0) {
                this.success(res.content)
                this.$emit('addGroupBuySubmit')
              } else {
                this.fail(res.message)
              }
            })
          }
        } else {
          this.fail('error submit!!')
        }
      })
      this.submitStatus = false
    },
    // 选择商品弹窗
    showChoosingGoods () {
      console.log('初始化商品弹窗', this.form.goodsId)
      this.transmitEditGoodsId(this.form.goodsId)
      // this.$http.$emit('choosingGoodsFlag', true, 'choiseOne')
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 获取商品ids
    choosingGoodsResult (row) {
      console.log('获取商品行', row)
      this.goodsRow = row
      console.log('goodsRow', this.goodsRow)
      console.log('tmpGoodsIds', this.form.goodsId)
      this.form.goodsId = row.goodsId
      // 初始化规格表格
      getAllGoodsProductList(this.form.goodsId).then(res => {
        console.log('product', res.content)
        this.form.product = res.content
      })
    },
    // 选择优惠券弹窗
    handleToCallDialog () {
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.couponList
      }
      this.$http.$emit('V-AddCoupon', obj)
    },
    arrayToString (arr) {

    },
    // 活动时间时间选择
    dateChange (date) {
      this.form.startTime = date[0]
      this.form.endTime = date[1]
      console.log(date)
    },
    fail (message) {
      this.$message({
        showClose: true,
        message,
        type: 'warning'
      })
    },
    success (message) {
      this.$message({
        showClose: true,
        message,
        type: 'success'
      })
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
          break
        case 2:
          price.forEach(row => {
            row.grouperPrice = price[0].grouperPrice
          })
          break
        case 3:
          price.forEach(row => {
            row.stock = price[0].stock
          })
          break
      }
      this.form.product = price
    },
    // 优惠卷回调
    handleToCheck (data) {
      console.log('优惠卷', data)
      let couponKey = []
      data.map((item) => {
        couponKey.push(item.id)
      })
      this.couponList = data
      this.form.rewardCouponId = couponKey.toString()
      console.log('conpon', couponKey.toString())
      console.log('conpon', this.couponList)
    },
    // 编辑活动初始化
    editActivityInit () {
      console.log('this.isEdite', this.isEdite)
      if (this.isEdite) {
        let data = this.editData
        console.log('编辑活动初始化', data)
        console.log('活动名称', this.form.name)
        this.goodsRow.ischecked = true
        this.goodsRow.goodsName = data.goodsName
        this.validityDate = [format(data.startTime), format(data.endTime)]
        this.form.id = data.id
        this.form.name = data.name
        this.form.goodsId = data.goodsId
        this.form.limitAmount = data.limitAmount
        this.form.joinLimit = data.joinLimit
        this.form.openLimit = data.openLimit
        this.form.isDefault = data.isDefault
        this.form.startTime = data.startTime
        this.form.endTime = data.endTime
        this.form.shippingType = data.shippingType
        this.form.activityType = data.activityType
        this.form.isGrouperCheap = data.isGrouperCheap
        this.form.rewardCouponId = data.rewardCouponId
        this.form.limitMaxNum = data.limitMaxNum
        this.form.limitBuyNum = data.limitBuyNum
        this.form.share = data.share
        this.form.product = data.productList
        console.log(this.form)
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
</style>
