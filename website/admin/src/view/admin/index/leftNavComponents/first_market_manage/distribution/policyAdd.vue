<template>
  <div class="container">

    <!-- 表单 -->
    <div>
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="200px"
        label-position="right"
      >
        <el-form-item
          :label="$t('distribution.strategyName') + '：'"
          prop="strategyName"
        >
          <el-input
            v-model="form.strategyName"
            size="small"
            class="inputWidth"
            :placeholder="$t('distribution.strategyTip1')"
            :disabled="status === false"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.ratioLevel') + '：'"
          prop="strategyLevel"
        >
        <hcInputNumber
          type="priority"
          v-model.number="form.strategyLevel"
          size="small"
          class="inputWidth"
          :placeholder="$t('distribution.strategyTip2')"
          :disabled="status === false"
          inline
        />
          <!-- <el-input
            v-model.number="form.strategyLevel"
            size="small"
            class="inputWidth"
            :placeholder="$t('distribution.strategyTip2')"
            :disabled="status === false"
          ></el-input> -->
          <div class="text">{{ $t('distribution.strategyTip3') }}</div>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.strategyValidity') + '：'"
          prop="startTime"
        >
          <!-- <el-date-picker
            v-model="form.validity"
            size="small"
            type="datetimerange"
            :range-separator="$t('seckill.to')"
            :start-placeholder="$t('seckill.startTime')"
            :end-placeholder="$t('seckill.endTime')"
            :default-time="['00:00:00','23:59:59']"
            value-format="yyyy-MM-dd HH:mm:ss"
            :disabled="status === false"
          ></el-date-picker> -->

          <el-date-picker
            v-model="form.startTime"
            size="small"
            type="datetime"
            :placeholder="$t('seckill.startTime')"
            default-time="00:00:00"
            value-format="yyyy-MM-dd HH:mm:ss"
            :picker-options="startDatePicker"
            :disabled="status === false"
          ></el-date-picker>
          {{$t('seckill.to')}}
          <el-date-picker
            v-model="form.endTime"
            size="small"
            type="datetime"
            :placeholder="$t('seckill.endTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="23:59:59"
            :picker-options="endDatePicker"
            :disabled="status === false"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.strategyType') + '：'"
          prop="strategyType"
        >
          <el-radio-group
            v-model="form.strategyType"
            @change="strategyTypeChange"
            :disabled="status === false"
          >
            <el-radio :label="0">{{ $t('distribution.strategyTypeTip1') }}</el-radio>
            <el-radio :label="1">{{ $t('distribution.strategyTypeTip2') }}</el-radio>
          </el-radio-group>
          <div class="text">{{ $t('distribution.strategyTypeTip3') }}</div>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.selfPurchase') + '：'"
          prop="selfPurchase"
        >
          <el-radio-group
            v-model="form.selfPurchase"
            :disabled="status === false"
          >
            <el-radio :label="1">{{ $t('distribution.purchaseOpen') }}</el-radio>
            <el-radio :label="0">{{ $t('distribution.purchaseClose') }}</el-radio>
          </el-radio-group>
          <span class="tips">{{ $t('distribution.strategyTip4') }}</span>
          <div class="text">{{ $t('distribution.strategyTip5') }}</div>
        </el-form-item>
        <el-form-item
          prop="costProtection"
          v-if="form.strategyType === 0"
        >
          <template slot="label">
            <el-tooltip
              effect="dark"
              placement="top"
            >
              <div slot="content">
                <p>{{ $t('distribution.costProtection') }}</p>
                <p>{{ $t('distribution.costProtectionTip1') }}</p>
                <p>{{ $t('distribution.costProtectionTip2') }}</p>
                <p>{{ $t('distribution.costProtectionTip3') }}</p>
                <p>{{ $t('distribution.costProtectionTip4') }}</p>
              </div>
              <i class="el-icon-warning"></i>
            </el-tooltip>
            <span>{{ $t('distribution.costProtection') }}</span>

          </template>
          <el-radio-group
            v-model="form.costProtection"
            :disabled="status === false"
          >
            <el-radio :label="1">{{ $t('distribution.purchaseOpen') }}</el-radio>
            <el-radio :label="0">{{ $t('distribution.purchaseClose') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.Invitation')"
          prop=""
        >
          <el-radio-group
            v-model="form.firstRebate"
            :disabled="status === false"
            @change="invitationChange()"
          >
            <el-radio :label="1">{{ $t('distribution.purchaseOpen') }}</el-radio>
            <el-radio :label="0">{{ $t('distribution.purchaseClose') }}</el-radio>
          </el-radio-group>
          <span class="tips">{{ $t('distribution.InvitationTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.proportion') + '：'"
          prop="tableData"
        >
          <el-table
            v-loading="loading"
            border
            :data="form.tableData"
            style="width: 100%;"
          >
            <template slot="empty">
              <tableEmpty />
            </template>
            <el-table-column
              prop="levelText"
              :label="$t('distribution.level')"
              align="center"
              min-width="1.5"
            ></el-table-column>
            <el-table-column
              :label="$t('distribution.levelName')"
              align="center"
              min-width="3"
            >
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.levelName"
                  size="small"
                  class="inputWidth"
                  disabled
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              min-width="5.5"
            >
              <template slot="header">
                <span>{{ $t('distribution.proportion') }}</span>
                <el-tooltip
                  effect="dark"
                  placement="top"
                >
                  <div slot="content">
                    <p>{{ $t('distribution.proportionTip1') }}</p>
                    <p>{{ $t('distribution.proportionTip2') }}</p>
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </template>

              <template slot-scope="scope">
                <div class="ratioContent">
                  <div class="leftItem">
                    <el-form-item
                      :prop="'tableData.'+scope.$index+'.fanliRatio'"
                      :rules="[{ validator: (rule, value, callback)=>{validatePercentage(rule, value, callback, scope.row.levelId)}, trigger: ['blur', 'change'] }]"
                    >
                      <div>{{ $t('distribution.proportionTip3') }}
                        <hcInputNumber
                          type="price"
                          v-model="scope.row.fanliRatio"
                          @input="percentageChange(scope.row.levelId)"
                          size="mini"
                          style="width: 55px;"
                          :disabled="status === false"
                          inline
                          :class="scope.row.fanliFlag ? 'errorStyle' : ''"
                        />
                        <!-- <el-input
                          v-model="scope.row.fanliRatio"
                          @input="percentageChange(scope.row.levelId)"
                          size="mini"
                          style="width: 55px;"
                          :disabled="status === false"
                          :class="scope.row.fanliFlag ? 'errorStyle' : ''"
                        ></el-input>  -->
                        %
                      </div>
                    </el-form-item>
                    <el-form-item
                      :prop="'tableData.'+scope.$index+'.rebateRatio'"
                      :rules="[{ validator: (rule, value, callback)=>{validatePercentage(rule, value, callback, scope.row.levelId)}, trigger: ['blur', 'change'] }]"
                    >
                      <div>{{ $t('distribution.proportionTip4') }}
                        <hcInputNumber
                          type="price"
                          v-model="scope.row.rebateRatio"
                          @input="percentageChange(scope.row.levelId)"
                          size="mini"
                          style="width: 55px;"
                          :disabled="status === false"
                          :class="scope.row.rebateFlag ? 'errorStyle' : ''"
                          inline
                        />
                        <!-- <el-input
                          v-model="scope.row.rebateRatio"
                          @input="percentageChange(scope.row.levelId)"
                          size="mini"
                          style="width: 55px;"
                          :disabled="status === false"
                          :class="scope.row.rebateFlag ? 'errorStyle' : ''"
                        ></el-input>  -->
                        %
                      </div>
                    </el-form-item>
                  </div>
                  <div
                    :class="{'rightItem':true, 'fullStyle': scope.row.fullFlag === true}"
                    v-if="scope.row.lowValue || scope.row.heightValue || scope.row.lowValue === 0 || scope.row.heightValue === 0"
                  >
                    {{ $t('distribution.proportionTip5') }} {{ scope.row.lowValue }}%-{{ scope.row.heightValue }}%
                  </div>
                </div>
                <div
                  v-if="form.firstRebate === 1"
                  class="bottomItem"
                >
                  <el-form-item
                    :prop="'tableData.'+scope.$index+'.firstRatio'"
                    :rules="[{ validator: (rule, value, callback)=>{validatePercentage(rule, value, callback, scope.row.levelId)}, trigger: ['blur', 'change'] }]"
                  >
                    <div>{{ $t('distribution.proportionTip6') }}
                      <hcInputNumber
                        type="price"
                        v-model="scope.row.firstRatio"
                        size="mini"
                        style="width: 55px;"
                        :disabled="status === false"
                        :class="scope.row.firstFlag ? 'errorStyle' : ''"
                        inline
                      />
                      <!-- <el-input
                        v-model="scope.row.firstRatio"
                        size="mini"
                        style="width: 55px;"
                        :disabled="status === false"
                        :class="scope.row.firstFlag ? 'errorStyle' : ''"
                      ></el-input>  -->
                      %
                    </div>
                  </el-form-item>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <div
            class="text"
            style="line-height: 2;margin-top: 10px;"
          >{{ $t('distribution.proportionTip7') }}</div>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.authority')"
          prop=""
        >
          <el-checkbox
            v-model="form.sendCoupon"
            :true-label='1'
            :false-label="0"
            :disabled="status === false"
          >{{ $t('distribution.authorityTip1') }}</el-checkbox>
          <span class="tips">{{ $t('distribution.authorityTip2') }}</span>
        </el-form-item>

        <el-form-item
          label="分销改价："
          prop="changePrice"
        >
          <el-checkbox
            v-model="form.changePrice"
            :true-label='1'
            :false-label="0"
            @change="changePriceHandler()"
            :disabled="status === false"
          >允许分销员分销商品时修改商品售价</el-checkbox>
          <el-form-item
            prop="changeEffectiveType"
            label="改价有效期："
            v-if="form.changePrice === 1"
            class="priceStyle"
          >
            <el-radio-group
              v-model="form.changeEffectiveType"
              :disabled="status === false"
              @change="changeEffectiveType()"
            >
              <el-radio :label="0">指定时间有效</el-radio>
              <el-radio :label="1">永久有效</el-radio>
            </el-radio-group>
            <div
              v-if="form.changeEffectiveType === 0"
              class="priceTypeStyle"
            >
              分享后
              <el-form-item prop="effectiveDate">
                <hcInputNumber
                  type="priority"
                  v-model="form.effectiveDate"
                  size="small"
                  style="width: 80px;"
                  :disabled="status === false"
                  inline
                />
                <!-- <el-input
                  v-model="form.effectiveDate"
                  size="small"
                  style="width: 80px;"
                  :disabled="status === false"
                ></el-input> -->
                <el-select
                  v-model="form.effectiveUnit"
                  size="small"
                  style="width: 80px;"
                >
                  <el-option
                    v-for="item in validityList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              内有效
            </div>
          </el-form-item>
        </el-form-item>

        <div style="height: 40px;line-height: 40px;background: #f8f8f8;padding-left: 10px;margin-bottom: 20px;">分销商品</div>
        <el-form-item
          label=""
          style="margin-left: -120px;margin-bottom: 100px;"
          prop="recommendType"
        >
          <div>
            <el-radio-group
              v-model="form.recommendType"
              :disabled="status === false"
              @change="recommendTypeChange()"
            >
              <el-radio :label="0">{{ $t('distribution.goodsRadio1') }}</el-radio>
              <el-radio :label="1">{{ $t('distribution.goodsRadio2') }}</el-radio>
            </el-radio-group>
            <div v-if="form.recommendType === 1">
              <div
                v-for="(item,index) in storeArr"
                :key="index"
                class="storeContent"
              >
                <el-button
                  @click="hanldeToAddGoodS(index)"
                  size="small"
                >
                  <i class="el-icon-plus"></i> {{ item.name }}
                </el-button>
                <span
                  style="color: #e4393c; cursor: pointer;"
                  v-if="index === 0"
                  @click="onlyHanldeToAddGoodS(index)"
                >{{ $t('distribution.goodsTip1') }} {{ goodsInfo && goodsInfo.length > 0 ? goodsInfo.length : 0 }} {{ $t('distribution.goodsTip2') }}</span>
                <span
                  style="cursor: pointer;"
                  v-if="index === 1"
                  @click="hanldeToAddGoodS(index)"
                >
                  <span v-if="busClass.length > 0">{{ $t('distribution.goodsTip1')}}{{ $t('distribution.goodsTip5') }}</span>
                  <span v-else>{{ $t('distribution.goodsTip6') }}{{ $t('distribution.goodsTip5') }}</span>
                </span>
                <!-- {{ $t('distribution.goodsTip1') }} {{ busClass.length > 0 ? busClass.length : 0 }} {{ $t('distribution.goodsTip3') }} -->
                <!-- <span v-if="index === 2">{{ $t('distribution.goodsTip1') }} {{ platClass.length > 0 ? platClass.length : 0 }} {{ $t('distribution.goodsTip4') }}</span> -->
              </div>
            </div>
          </div>
        </el-form-item>

      </el-form>

    </div>

    <!-- 底部 -->
    <div
      class="footer"
      v-if="status === true"
    >
      <el-button
        size="small"
        type="primary"
        :disabled="submitStatus"
        @click="saveClickHandler()"
      >{{ $t('distribution.rebateSave') }}</el-button>
    </div>

    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
      :onlyShowChooseGoods="isOnlyShowChooseGoods"
      :chooseGoodsBack="goodsInfo"
      :upperlowershelves="true"
    />

    <!-- 选择 1商家分类;2平台分类弹窗 -->
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpBusClassDialog"
      :classFlag="classFlag"
      @BusClassTrueDetailData="busClassDialogResult"
      :backDataArr="commInfo"
    />

  </div>
</template>
<script>
// 引入组件
import { addPolicy, getPolicyDetail, editPolicy, getDistributionLevel } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog')
  },
  props: ['isEdite', 'editId', 'status'],
  data () {
    // 自定义校验有效期
    var validateTime = (rule, value, callback) => {
      if (!value || !this.form.endTime) {
        callback(new Error('请完整填写有效期'))
      } else {
        callback()
      }
    }
    // 自定义校验可使用商品
    var validateRecommendType = (rule, value, callback) => {
      if (value === 1 && (this.goodsInfo.length === 0 && this.busClass.length === 0 && this.platClass.length === 0)) {
        callback(new Error(this.$t('ordinaryCoupon.validatesuitGoods1')))
      } else {
        callback()
      }
    }
    // 自定义校验改价有效期
    var validityTime = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (this.form.changeEffectiveType === 0) {
        if (!value) {
          callback(new Error('请填写改价有效期'))
        } else if (!re.test(value)) {
          callback(new Error('有效期类型为正整数'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    return {
      // 表单
      form: {
        strategyName: '', //  返利策略名称
        strategyLevel: '', //  返利策略优先级
        // validity: '', // 有效期
        startTime: '',
        endTime: '',
        strategyType: 0, // 佣金计算方式
        selfPurchase: 0, // 分销员自购返利
        costProtection: 0, // 成本价保护
        firstRebate: 0, // 邀请新用户下首单返利配置
        sendCoupon: 1, // 分销员权限
        recommendType: 0, // 分销商品
        recommendGoodsId: [], // 商品ID
        recommendCatId: [], // 分类ID
        recommendSortId: [], // 商家ID

        changePrice: 0, // 分销改价
        changeEffectiveType: 0, // 改价有效期
        effectiveDate: '24', // 有效期值
        effectiveUnit: 0, // 有效期值类型

        // 佣金比例表格数据
        tableData: [{
          levelId: 1,
          levelName: '普通分销员',
          levelStatus: 1,
          fanliRatio: 0, // 直接比例
          rebateRatio: 0, // 间接比例
          firstRatio: 0 // 首单返利
        }, {
          levelId: 2,
          levelName: '',
          levelStatus: 0,
          fanliRatio: null, // 直接比例
          rebateRatio: null, // 间接比例
          firstRatio: null // 首单返利
        }, {
          levelId: 3,
          levelName: '',
          levelStatus: 0,
          fanliRatio: null, // 直接比例
          rebateRatio: null, // 间接比例
          firstRatio: null // 首单返利
        }, {
          levelId: 4,
          levelName: '',
          levelStatus: 0,
          fanliRatio: null, // 直接比例
          rebateRatio: null, // 间接比例
          firstRatio: null // 首单返利
        }, {
          levelId: 5,
          levelName: '',
          levelStatus: 0,
          fanliRatio: null, // 直接比例
          rebateRatio: null, // 间接比例
          firstRatio: null // 首单返利
        }]
      },
      loading: false, // 表格加载
      // 校验表单
      fromRules: {
        strategyName: { required: true, message: '请填写返利策略名称', trigger: 'blur' },
        strategyLevel: { required: true, message: '请填写返利策略优先级', trigger: 'blur' },
        // validity: { required: true, message: '请填写有效期', trigger: 'change' },
        startTime: { required: true, validator: validateTime, trigger: 'change' },
        selfPurchase: { required: true, message: '请选择是否开启自购返利', trigger: 'change' },
        recommendType: { required: true, validator: validateRecommendType, trigger: 'change' },
        effectiveDate: { validator: validityTime, trigger: 'change' }
      },
      tipContent: `
        <p>成本价保护：</p>
        <p>当单件商品实付金额-成本价大于0时，按分销比例分配差额</p>
        <p>当单件商品实付金额-成本价小于等于0时，返利为0</p>
        <p>注：</p>
        <p>未设置成本价的商品无效</p>
      `,

      storeArr: [], // 分销商品
      tuneUpChooseGoods: false, // 商品弹窗
      isOnlyShowChooseGoods: false,
      tuneUpBusClassDialog: false, // 商家/平台弹窗
      classFlag: 0, // 商家/平台类型
      // 弹窗结果区分标识 1商家分类;2平台分类
      flag: 0,
      // 商品弹窗回调数据
      goodsInfo: [],
      goodsInfoRow: [],
      // 商家分类弹窗回调数据
      busClass: [],
      busClassRow: [],
      // 平台分类弹窗回调数据
      platClass: [],
      platClassRow: [],
      // 平台分类/商家分类共享变量
      commInfo: [],

      submitStatus: false,

      startDatePicker: this.beginDate(),
      endDatePicker: this.processDate(),

      // 改价有效期类型
      validityList: [{
        label: '小时',
        value: 0
      }, {
        label: '天',
        value: 1
      }]
    }
  },
  watch: {
    lang () {
      this.storeArr = this.$t('distribution.storeArr')
    }
  },
  mounted () {
    this.langDefault()
    this.getLevelList()
  },
  methods: {
    // 获取分销员等级
    async getLevelList () {
      this.loading = true
      var newData = []
      await getDistributionLevel().then((res) => {
        if (res.error === 0 && res.content && res.content.levelList && res.content.levelList.length > 0) {
          res.content.levelList.forEach(item => {
            newData.push({
              levelId: item.levelId,
              levelName: item.levelName,
              levelStatus: item.levelStatus,
              fanliRatio: null,
              rebateRatio: null,
              firstRatio: null
            })
          })
        } else {
          newData = this.form.tableData
        }
      })
      await this.handleData(newData)

      if (this.isEdite === true) {
        // 编辑初始化
        this.editSeckillInit(this.editId)
      }
    },

    // 表格数据处理
    handleData (data) {
      if (data && data.length > 0) {
        data.map(item => {
          // 佣金比例范围
          if (this.isEdite === false) {
            // 添加时的初始值
            if (item.levelId === 1) {
              item.fanliRatio = 0
              item.rebateRatio = 0
              item.firstRatio = 0

              item.lowValue = 0
              item.heightValue = 0
            } else {
              item.lowValue = null
              item.heightValue = null
            }
          }

          // 标红样式显示
          item.fanliFlag = false
          item.rebateFlag = false
          item.firstFlag = false
          item.fullFlag = false // 返利比例满半分比样式

          if (item.levelStatus === 0) {
            item.levelName = item.levelName + '(已停用)'
          } else {
            item.levelName = item.levelName + '(已启用)'
          }
          switch (item.levelId) {
            case 1:
              item.levelText = '一级'
              break
            case 2:
              item.levelText = '二级'
              break
            case 3:
              item.levelText = '三级'
              break
            case 4:
              item.levelText = '四级'
              break
            case 5:
              item.levelText = '五级'
              break
          }
        })
        this.form.tableData = data
        this.loading = false
      }
    },

    // 编辑初始化
    editSeckillInit (id) {
      getPolicyDetail(id).then((res) => {
        if (res.error === 0) {
          var data = res.content[0]
          this.form.strategyName = data.strategyName
          this.form.strategyLevel = data.strategyLevel
          this.form.strategyType = data.strategyType
          // 有效期
          // this.form.validity = [data.startTime, data.endTime]
          this.form.startTime = data.startTime
          this.form.endTime = data.endTime
          this.form.selfPurchase = data.selfPurchase
          this.form.costProtection = data.costProtection
          this.form.firstRebate = data.firstRebate
          this.form.sendCoupon = data.sendCoupon
          this.form.recommendType = data.recommendType
          // 直接返利
          this.form.tableData[0].fanliRatio = data.fanliRatio
          this.form.tableData[1].fanliRatio = data.fanliRatio_2
          this.form.tableData[2].fanliRatio = data.fanliRatio_3
          this.form.tableData[3].fanliRatio = data.fanliRatio_4
          this.form.tableData[4].fanliRatio = data.fanliRatio_5

          // 间接返利
          this.form.tableData[0].rebateRatio = data.rebateRatio
          this.form.tableData[1].rebateRatio = data.rebateRatio_2
          this.form.tableData[2].rebateRatio = data.rebateRatio_3
          this.form.tableData[3].rebateRatio = data.rebateRatio_4
          this.form.tableData[4].rebateRatio = data.rebateRatio_5

          // 首单返利
          this.form.tableData[0].firstRatio = data.firstRatio
          this.form.tableData[1].firstRatio = data.firstRatio_2
          this.form.tableData[2].firstRatio = data.firstRatio_3
          this.form.tableData[3].firstRatio = data.firstRatio_4
          this.form.tableData[4].firstRatio = data.firstRatio_5

          // 返利商品
          this.goodsInfo = data.recommendGoodsId !== '' ? data.recommendGoodsId.split(',') : []
          this.goodsInfo = this.goodsInfo.map(Number)

          this.busClass = data.recommendSortId !== '' ? data.recommendSortId.split(',') : []
          this.busClass = this.busClass.map(Number)

          // this.platClass = data.recommendCatId !== '' ? data.recommendCatId.split(',') : []
          // this.platClass = this.platClass.map(Number)

          // 比例区间
          this.form.tableData.forEach(item => {
            if (item.fanliRatio <= item.rebateRatio) {
              item.lowValue = item.fanliRatio
              item.heightValue = item.rebateRatio
            } else {
              item.lowValue = item.rebateRatio
              item.heightValue = item.fanliRatio
            }
          })

          // 分销改价
          this.form.changePrice = data.changePrice
          this.form.changeEffectiveType = data.changeEffectiveType
          this.form.effectiveDate = data.effectiveDate
          this.form.effectiveUnit = data.effectiveUnit
        }
      })
    },

    // 保存返利策略
    saveClickHandler () {
      // 比较直接间接之和
      var maxFanli = 0
      var maxRebate = 0
      var maxFanliIndex = 0
      var maxRebateIndex = 0
      var maxArray = []
      var type = 'fanli'
      this.form.tableData.forEach(item => {
        item.fanliRatio = item.fanliRatio || item.fanliRatio === 0 ? Number(item.fanliRatio) : null
        item.rebateRatio = item.rebateRatio || item.rebateRatio === 0 ? Number(item.rebateRatio) : null
        item.firstRatio = item.firstRatio || item.firstRatio === 0 ? Number(item.firstRatio) : null
        if (item.rebateRatio >= maxRebate) {
          maxRebate = item.rebateRatio
          maxRebateIndex = item.levelId
        }
        if (item.fanliRatio) {
          maxArray.push({
            value: item.fanliRatio,
            index: item.levelId,
            type: 'fanli'
          })
        }
        if (item.firstRatio) {
          maxArray.push({
            value: item.firstRatio,
            index: item.levelId,
            type: 'first'
          })
        }
      })
      // 直接比例最大值
      maxArray.forEach(item => {
        if (item.value >= maxFanli) {
          maxFanli = item.value
          maxFanliIndex = item.index
          type = item.type
        }
      })
      if (maxFanli + maxRebate > 100) {
        this.form.tableData.forEach(item => {
          item.fanliFlag = false
          item.rebateFlag = false
          item.firstFlag = false
          if (item.levelId === maxFanliIndex) {
            if (type === 'fanli') {
              item.fanliFlag = item.fanliRatio === maxFanli
            } else {
              item.firstFlag = item.firstRatio === maxFanli
            }
          }
          if (item.levelId === maxRebateIndex) {
            item.rebateFlag = true
          }
        })

        this.$message.warning('最大直接返利比例/首单返利比例与最大间接返利比例之和不能超过100%，请修改后重新保存')
        return
      }

      // 有效期
      // this.form.startTime = this.form.validity[0]
      // this.form.endTime = this.form.validity[1]

      // 直接返利
      this.form.fanliRatio = this.form.tableData[0].fanliRatio
      this.form.fanliRatio_2 = this.form.tableData[1].fanliRatio
      this.form.fanliRatio_3 = this.form.tableData[2].fanliRatio
      this.form.fanliRatio_4 = this.form.tableData[3].fanliRatio
      this.form.fanliRatio_5 = this.form.tableData[4].fanliRatio

      // 间接返利
      this.form.rebateRatio = this.form.tableData[0].rebateRatio
      this.form.rebateRatio_2 = this.form.tableData[1].rebateRatio
      this.form.rebateRatio_3 = this.form.tableData[2].rebateRatio
      this.form.rebateRatio_4 = this.form.tableData[3].rebateRatio
      this.form.rebateRatio_5 = this.form.tableData[4].rebateRatio

      // 首单返利
      this.form.firstRatio = this.form.tableData[0].firstRatio
      this.form.firstRatio_2 = this.form.tableData[1].firstRatio
      this.form.firstRatio_3 = this.form.tableData[2].firstRatio
      this.form.firstRatio_4 = this.form.tableData[3].firstRatio
      this.form.firstRatio_5 = this.form.tableData[4].firstRatio

      // 返利商品
      this.form.recommendGoodsId = this.goodsInfo.toString() // 商品
      this.form.recommendSortId = this.busClass.toString() // 商家
      this.form.recommendCatId = this.platClass.toString() // 平台

      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.submitStatus = true
          if (this.isEdite === false) {
            // 添加返利
            addPolicy(this.form).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: this.$t('distribution.addSuccess') })
                this.$emit('addPolicySubmit')
              } else {
                this.$message.warning({ message: this.$t('distribution.addFail') })
              }
            })
          } else {
            // 编辑返利
            var obj = this.form
            obj.id = this.editId
            editPolicy(obj).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: this.$t('distribution.editSuccess') })
                this.$emit('addPolicySubmit')
              } else {
                this.$message.warning({ message: this.$t('distribution.editFail') })
              }
            })
          }
        }
      })
      this.submitStatus = false
    },

    // 点击指定商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      switch (index) {
        case 0:
          this.isOnlyShowChooseGoods = false
          this.tuneUpChooseGoods = !this.tuneUpChooseGoods
          break
        case 1:
          this.tuneUpBusClassDialog = !this.tuneUpBusClassDialog
          this.classFlag = 1
          this.flag = 1
          this.commInfo = this.busClass
          break
        case 2:
          this.tuneUpBusClassDialog = !this.tuneUpBusClassDialog
          this.classFlag = 2
          this.flag = 2
          this.commInfo = this.platClass
          break
      }
    },

    // 点击指定商品出现的添加类弹窗汇总--部分
    onlyHanldeToAddGoodS (index) {
      console.log(index)
      switch (index) {
        case 0:
          this.isOnlyShowChooseGoods = true
          this.tuneUpChooseGoods = !this.tuneUpChooseGoods
          break
        case 1:
          this.tuneUpBusClassDialog = true
          this.classFlag = 1
          this.flag = 1
          this.commInfo = this.busClass
          break
        case 2:
          this.tuneUpBusClassDialog = true
          this.classFlag = 2
          this.flag = 2
          this.commInfo = this.platClass
          break
      }
    },

    // 选择商品弹窗回调显示
    choosingGoodsResult (row) {
      console.log('选择商品弹窗回调显示:', row)
      this.goodsInfoRow = row
      this.goodsInfo = []
      this.goodsInfoRow.map((item, index) => {
        this.goodsInfo.push(item.goodsId)
      })
      this.$refs['form'].validateField('recommendType')
    },
    // 选择商家分类/平台分类弹窗回调显示
    busClassDialogResult (row) {
      console.log('选择商家分类/平台分类弹窗回调显示:', row)
      if (this.flag === 1) {
        // 商家分类
        this.busClassRow = row
        this.busClass = []
        this.busClassRow.map((item, index) => {
          this.busClass.push(item.sortId)
        })
      } else {
        // 平台分类
        this.platClassRow = row
        this.platClass = []
        this.platClassRow.map((item, index) => {
          this.platClass.push(item.catId)
        })
      }
      this.$refs['form'].validateField('recommendType')
    },

    // 切换分销商品
    recommendTypeChange () {
      this.goodsInfo = []
    },

    // 切换首单返利配置
    invitationChange () {
      this.form.tableData.forEach(item => {
        item.firstRatio = null
      })
    },

    // 直接/间接返利变化
    percentageChange (levelId) {
      var currentData = this.form.tableData.find(item => { return item.levelId === levelId })
      var fanliRatio = currentData.fanliRatio
      var rebateRatio = currentData.rebateRatio

      // 满百分比样式变化
      if (fanliRatio === 100 || fanliRatio === '100' || rebateRatio === 100 || rebateRatio === '100') {
        currentData.fullFlag = true
      } else {
        currentData.fullFlag = false
      }

      // 比例区间变化
      var re = /^(100|([0-9]|([1-9][0-9]))(\.\d{1,2})?)$/
      if (fanliRatio && re.test(fanliRatio) && rebateRatio && re.test(rebateRatio)) {
        fanliRatio = Number(fanliRatio)
        rebateRatio = Number(rebateRatio)
        if (fanliRatio >= rebateRatio) {
          this.$set(currentData, 'lowValue', rebateRatio)
          this.$set(currentData, 'heightValue', fanliRatio)
        } else {
          this.$set(currentData, 'lowValue', fanliRatio)
          this.$set(currentData, 'heightValue', rebateRatio)
        }
      } else if (fanliRatio && re.test(fanliRatio)) {
        this.$set(currentData, 'lowValue', 0)
        this.$set(currentData, 'heightValue', fanliRatio)
      } else if (rebateRatio && re.test(rebateRatio)) {
        this.$set(currentData, 'lowValue', 0)
        this.$set(currentData, 'heightValue', rebateRatio)
      }
    },

    // 校验直接/间接/首单返利
    validatePercentage (rule, value, callback, levelId) {
      var re = /^(100|([0-9]|([1-9][0-9]))(\.\d{1,2})?)$/
      if (levelId === 1 && !value && value !== 0 && value !== '0') {
        callback(new Error('请填写返利比例'))
      } else if (value && !re.test(value)) {
        callback(new Error('返利比例在0%-100%之间, 可以保留两位小数'))
      } else {
        callback()
      }
    },

    // 切换佣金计算方式
    strategyTypeChange (val) {
      if (val === 1) {
        this.form.costProtection = 0
      }
    },

    // 有效期时间限制
    beginDate () {
      var that = this
      return {
        disabledDate (time) {
          if (that.form.endTime) {
            return new Date(that.form.endTime).getTime() < time.getTime() || time.getTime() > new Date('2037-12-31 23:59:59').getTime() // 如果结束时间不为空，则小于结束时间
          } else {
            return time.getTime() > new Date('2037-12-31 23:59:59').getTime()
          }
        }
      }
    },
    processDate () {
      var that = this
      return {
        disabledDate (time) {
          if (that.form.startTime) {
            return new Date(that.form.startTime).getTime() > time.getTime() || time.getTime() > new Date('2037-12-31 23:59:59').getTime() // 如果开始时间不为空，则结束时间大于开始时间
          } else {
            return time.getTime() > new Date('2037-12-31 23:59:59').getTime() // 开始时间不选时，结束时间最大值小于2038年
          }
        }
      }
    },

    // 切换分销改价
    changePriceHandler () {
      // 恢复默认值
      this.form.changeEffectiveType = 0
      this.form.effectiveDate = '24'
      this.form.effectiveUnit = 0
    },

    // 切换改价类型
    changeEffectiveType () {
      this.form.effectiveDate = '24'
      this.$refs['form'].validateField('effectiveDate')
    }
  }
}
</script>
<style scoped>
.inputWidth {
  width: 170px;
}
.container {
  margin-top: 10px;
  padding: 10px;
  background: #fff;
}
.footer {
  position: fixed;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 99;
}
.storeContent .el-button {
  margin: 10px 0;
  width: 150px;
  margin-right: 10px;
}
.tips {
  color: #999;
  margin-left: 20px;
}
.text {
  color: #999;
}
/deep/ .el-radio__input.is-disabled + span.el-radio__label,
/deep/ .el-checkbox__input.is-disabled + span.el-checkbox__label {
  color: #606266;
}

.ratioContent {
  display: flex;
  flex-wrap: nowrap;
  justify-content: flex-start;
  align-items: center;
}
.leftItem {
  flex: 1;
  text-align: left;
}
.rightItem {
  flex: 1;
}
.bottomItem {
  text-align: left;
}
.fullStyle {
  color: red;
}
.ratioContent /deep/ .el-form-item__error,
.bottomItem /deep/ .el-form-item__error {
  position: relative;
  /* text-align: center; */
}
.ratioContent /deep/ .el-input__inner,
.bottomItem /deep/ .el-input__inner {
  padding: 0;
  text-align: center;
}
.errorStyle /deep/ .el-input__inner {
  border: 1px solid red;
}

.priceStyle /deep/ .el-form-item__label {
  width: 100px !important;
}

.priceStyle /deep/ .el-form-item__content {
  margin-left: 0px !important;
}

.priceTypeStyle {
  margin-left: 100px;
}

.priceTypeStyle /deep/ .el-form-item {
  display: inline-block;
}
</style>
