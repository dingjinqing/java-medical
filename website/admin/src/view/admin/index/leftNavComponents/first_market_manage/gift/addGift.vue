<!--
* 创建赠品活动
*
* @author 郑保乐
-->
<template>
  <div style="padding: 10px;">
    <div class="content">

      <!-- step -->
      <el-steps
        :active="step"
        simple
        finish-status="finish"
        style="margin-bottom: 20px"
      >
        <el-step
          v-for="(item, index) in steps"
          :key="index"
          :title="`${index+1}. ${item}`"
        ></el-step>
      </el-steps>

      <!-- 设置赠品规则 -->
      <div v-if="step===1">
        <el-form
          ref="param"
          :rules="paramRules"
          :model="param"
          label-width="130px"
        >
          <el-row style="margin-bottom:20px;">
            <el-col :span="2">
              <span class="label">{{ $t('gift.stepsUp') }}</span>
            </el-col>
            <el-col :span="20">
              <el-form-item
                :label="$t('gift.activityName') + '：'"
                prop="name"
              >
                <el-input
                  size="small"
                  v-model="param.name"
                  class="inputWidth"
                ></el-input>
                <span style="color: #999;">{{ $t('gift.nameTip') }}</span>
              </el-form-item>
              <el-form-item
                :label="$t('gift.activityLevel') + '：'"
                prop="level"
              >
                <el-input
                  size="small"
                  class="inputWidth"
                  v-model="param.level"
                  :disabled="ongoing"
                ></el-input>
                <span style="color: #999;">{{ $t('gift.levelTip') }}</span>
              </el-form-item>
              <el-form-item
                :label="$t('gift.activityTime') + '：'"
                prop="dateRange"
              >
                <el-date-picker
                  size="small"
                  v-model="param.dateRange"
                  type="datetimerange"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  format="yyyy-MM-dd HH:mm:ss"
                  :range-separator="$t('gift.to')"
                  :start-placeholder="$t('gift.startTime')"
                  :end-placeholder="$t('gift.endTime')"
                  :default-time="['00:00:00','23:59:59']"
                  :disabled="ongoing"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row style="margin-bottom:20px">
            <el-col :span="2">
              <span class="label">{{ $t('gift.stepsDown') }}</span>
            </el-col>
            <el-col :span="20">
              <el-form-item
                :label="$t('gift.commodity') + '：'"
                prop="goodsRange"
              >
                <el-radio
                  v-for="(item, index) in goodsRanges"
                  :key="index"
                  v-model="param.goodsRange"
                  :label="index"
                  :disabled="ongoing"
                >{{item}}</el-radio>
                <el-button
                  size="small"
                  v-show="param.goodsRange===1"
                  @click="showChoosingGoods"
                >{{goodsBtnName}}</el-button>
                <span
                  style="color: #e4393c; cursor: pointer;"
                  v-show="param.goodsRange===1"
                  @click="onlyShowChoosingGoods"
                >{{ $t('gift.selected') }}：{{goodslength}} {{ $t('gift.selectedNUm') }}</span>
              </el-form-item>
              <el-form-item
                :label="$t('gift.giftConditions') + '：'"
                prop="selectedRules"
              >
                <el-select
                  size="small"
                  v-model="param.selectedRules"
                  :placeholder="$t('gift.select')"
                  multiple
                  :multiple-limit="3"
                  :disabled="ongoing"
                >
                  <el-option
                    v-for="(item, index) in rules"
                    :disabled="ongoing"
                    :key="index"
                    :label="item.label"
                    :value="index"
                  >
                  </el-option>
                </el-select>
                <span style="color: #999;">{{ this.$t('gift.conditionsTip') }}</span>
              </el-form-item>
              <div style="background:rgb(245,245,245);margin-bottom:22px;padding: 20px;">
                <el-form-item
                  :label="$t('gift.conditions1')"
                  v-show="contains(0)"
                  prop="rules.fullPrice"
                >
                  <span>{{ $t('gift.conditionsTip1') }}</span>
                  <el-input
                    size="small"
                    v-model="param.rules.fullPrice"
                    class="input"
                    :disabled="ongoing"
                  ></el-input>
                  <span>{{ $t('gift.conditionsTip2') }} {{ $t('gift.conditionsTip3') }}</span>
                </el-form-item>
                <el-form-item
                  :label="$t('gift.conditions2')"
                  v-show="contains(1)"
                  prop="rules.fullNumber"
                >
                  <span>{{ $t('gift.conditionsTip1') }}</span>
                  <el-input
                    size="small"
                    v-model="param.rules.fullNumber"
                    class="input"
                    :disabled="ongoing"
                  ></el-input>
                  <span>{{ $t('gift.conditionsTip4') }} {{ $t('gift.conditionsTip3') }}</span>
                </el-form-item>
                <el-form-item
                  :label="$t('gift.conditions3')"
                  v-show="contains(2)"
                  prop="rules.tagId"
                >
                  <el-select
                    size="small"
                    v-model="param.rules.tagId"
                    multiple
                    :disabled="ongoing"
                    @remove-tag="removeTag"
                  >
                    <el-option
                      v-for="item in tagsList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  :label="$t('gift.conditions4')"
                  v-show="contains(3)"
                  prop="rules.cardId"
                >
                  <el-select
                    size="small"
                    v-model="param.rules.cardId"
                    multiple
                    :disabled="ongoing"
                    @remove-tag="removeCard"
                  >
                    <el-option
                      v-for="item in cardList"
                      :key="item.id"
                      :label="item.cardName"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  :label="$t('gift.conditions5')"
                  v-show="contains(4)"
                  prop="rules.payTop"
                >
                  {{ $t('gift.conditionsTip5') }} <el-input
                    size="small"
                    v-model="param.rules.payTop"
                    class="input"
                    :disabled="ongoing"
                  ></el-input>{{ $t('gift.conditionsTip6') }} {{ $t('gift.conditionsTip3') }}
                </el-form-item>
                <el-form-item
                  :label="$t('gift.conditions6')"
                  v-show="contains(5)"
                  prop="rules.minPayNum"
                >
                  <el-input
                    size="small"
                    v-model="param.rules.minPayNum"
                    class="input"
                    :disabled="ongoing"
                  ></el-input>
                  <span style="margin-right: 10px">{{ $t('gift.to') }}</span>
                  <el-input
                    size="small"
                    ref="maxPayNum"
                    v-model="param.rules.maxPayNum"
                    class="input"
                    :disabled="ongoing"
                  ></el-input> {{ $t('gift.conditionsTip7') }}
                </el-form-item>
                <el-form-item
                  :label="$t('gift.conditions7')"
                  v-show="contains(6)"
                  prop="payDateRange"
                >
                  <el-date-picker
                    size="small"
                    v-model="param.payDateRange"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    format="yyyy-MM-dd HH:mm:ss"
                    :range-separator="$t('gift.to')"
                    :start-placeholder="$t('gift.startTime')"
                    :end-placeholder="$t('gift.endTime')"
                    :default-time="['00:00:00','23:59:59']"
                    :disabled="ongoing"
                  >
                  </el-date-picker>
                </el-form-item>
                <el-form-item
                  :label="$t('gift.conditions8')"
                  v-show="contains(7)"
                  prop="rules.userAction"
                >
                  <el-select
                    size="small"
                    v-model="param.rules.userAction"
                    :disabled="ongoing"
                  >
                    <el-option
                      v-for="item in userAction"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
              <el-form-item
                :label="$t('gift.ruleDescription') + '：'"
                prop="explain"
              >
                <el-input
                  type="textarea"
                  :rows="5"
                  v-model="param.explain"
                  :placeholder="$t('gift.textareaTip')"
                >
                </el-input>
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
                  >{{ this.$t('gift.forExample') }}</el-button>
                </el-popover>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

      </div>

      <!-- 设置赠品 -->
      <div v-if="step===2">
        <el-row v-show="!ongoing">
          <el-col>
            <el-button
              size="small"
              type="primary"
              @click="showChoosingGoods"
            >{{ $t('gift.addFreebies') }}</el-button>
            <span class="remarks">{{ $t('gift.addTip') }}</span>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px;">
          <el-col>
            <el-table
              class="version-manage-table"
              header-row-class-name="tableClss"
              :data="tableData"
              border
              style="width: 100%"
            >
              <el-table-column
                prop="goodsName"
                :label="$t('gift.goodsName')"
                align="center"
              >
                <template slot-scope="scope">
                  <div class="name_cell">
                    <img
                      :src="scope.row.goodsImg"
                      class="goods_img"
                    >
                    <div>{{scope.row.goodsName}}</div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                prop="prdDesc"
                :label="$t('gift.catName')"
                align="center"
              > </el-table-column>
              <el-table-column
                prop="prdPrice"
                :label="$t('gift.goodsPrice')"
                align="center"
              > </el-table-column>
              <el-table-column
                prop="prdNumber"
                :label="$t('gift.goodsNumber')"
                align="center"
              ></el-table-column>
              <el-table-column
                label="赠品库存"
                align="center"
                v-if="update === false"
              >
                <template slot-scope="scope">
                  <el-input
                    size="small"
                    v-model="scope.row.productNumber"
                    @blue="productNumberCheck(scope.row.prdNumber, scope.row.productNumber)"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="offerNumber"
                label="已赠库存"
                align="center"
                v-if="update === true"
              ></el-table-column>
              <el-table-column
                prop="productNumber"
                label="剩余赠品库存"
                align="center"
                v-if="update === true"
              >
                <template slot-scope="scope">
                  <el-input
                    size="small"
                    v-model="scope.row.productNumber"
                    @blue="productNumberCheck(scope.row.prdNumber, scope.row.productNumber)"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('gift.option')"
                align="center"
                v-if="!ongoing"
              >
                <template slot-scope="scope">
                  <el-button
                    type="primary"
                    size="medium"
                    v-show="!ongoing"
                    @click="deleteHandler(scope.$index, scope.row)"
                  >{{ $t('gift.delete') }}</el-button>

                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </div>

      <div class="footer">
        <el-row>
          <el-col>
            <el-button
              size="small"
              type="primary"
              @click="lastStep"
              v-show="step > 1"
            >{{ $t('gift.lastStep') }}</el-button>
            <el-button
              size="small"
              type="primary"
              @click="nextStep"
              v-show="step < steps.length"
            >{{ $t('gift.nextStep') }}</el-button>
            <el-button
              size="small"
              type="primary"
              @click="addGift"
              v-show="step === steps.length"
            >{{ $t('gift.save') }}</el-button>
          </el-col>
        </el-row>
      </div>

      <!-- step1 - 商品弹窗 -->
      <choosingGoods
        :tuneUpChooseGoods="tuneUpGoods"
        @resultGoodsIds="getGoodsIds"
        :onlyShowChooseGoods="isOnlyShowChooseGoods"
        :chooseGoodsBack="this.param.goodsIds"
      />

      <!-- step2 - 规格弹窗-->
      <choosingGoods
        :tuneUpChooseGoods="tuneUpChooseGoods"
        :loadProduct="true"
        :checkedNumMax=20
        :chooseGoodsBack="this.specsIds"
        @resultGoodsIds="getSpecsIds"
        @resultGoodsDatas="getSpecsData"
      />

    </div>
  </div>

</template>
<script>
import { mapActions } from 'vuex'
// import inputEdit from '@/components/admin/inputEdit'
// import giftEdit from './giftEdit'
import wrapper from '@/components/admin/wrapper/wrapper'
import choosingGoods from '@/components/admin/choosingGoods'
import status from '@/components/admin/marketManage/status/status'
// import { format, range } from '@/util/date'
// import { getGoodsInfosByGoodIds } from '@/api/admin/goodsManage/allGoods/allGoods'
import { addGift, getGiftDetail, updateGift, getMemberCardList, getTagList, getProductDetail } from '@/api/admin/marketManage/gift'

export default {
  components: {
    wrapper,
    // giftEdit,
    // inputEdit,
    choosingGoods
  },
  data () {
    var validatelevel = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (!value) {
        callback(new Error('请填写活动优先级'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    var validategoodsRange = (rule, value, callback) => {
      if (value === 1 && this.goodslength === 0) {
        callback(new Error('请选择指定活动商品'))
      } else {
        callback()
      }
    }
    var validateselectedRules = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('请选择赠品条件'))
      } else {
        callback()
      }
    }
    // 满金额
    var validatMoney = (rule, value, callback) => {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value) && this.contains(0)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else {
        callback()
      }
    }
    // 满件数
    var validatNum = (rule, value, callback) => {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!re.test(value) && this.contains(1)) {
        callback(new Error('请填写0或者正整数'))
      } else {
        callback()
      }
    }
    // 会员标签
    var validattagId = (rule, value, callback) => {
      if ((!value || value.length === 0) && this.contains(2)) {
        callback(new Error('请选择会员标签'))
      } else {
        callback()
      }
    }
    // 会员卡
    var validatcardId = (rule, value, callback) => {
      if ((!value || value.length === 0) && this.contains(3)) {
        callback(new Error('请选择会员卡'))
      } else {
        callback()
      }
    }
    // 付款排名
    var validatInt = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (!re.test(value) && this.contains(4)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    // 已购买次数
    var validatMin = (rule, value, callback) => {
      let maxPayNum = this.$refs.maxPayNum.value
      var re = /^(0|\+?[1-9][0-9]*)$/
      if ((!re.test(value) || !re.test(maxPayNum)) && this.contains(5)) {
        callback(new Error('请填写0或者正整数'))
      } else if (Number(value) > Number(maxPayNum)) {
        callback(new Error('最小购买次数不能大于最大购买次数'))
      } else {
        callback()
      }
    }
    // 付款时间
    var validatPayDateRange = (rule, value, callback) => {
      if ((!value || value === null || value.length === 0) && this.contains(6)) {
        callback(new Error('请选择付款时间'))
      } else {
        callback()
      }
    }
    // 用户类别
    var validatUserAction = (rule, value, callback) => {
      if (!value && this.contains(7)) {
        callback(new Error('请选择用户类别'))
      } else {
        callback()
      }
    }

    return {
      id: null,
      step: 1,
      steps: this.$t('gift.steps'),
      // dateRange: [], // 活动时间范围
      tagsList: [], // 会员标签列表
      cardList: [], // 会员卡列表
      // goodsRange: 0, // 0：全部商品，1：指定商品
      goodsRanges: this.$t('gift.goodsRanges'),
      // 当前页为编辑页
      update: false,
      // 活动状态,
      status: null,
      param: {
        name: '',
        level: '',
        dateRange: '', // 活动时间范围
        startTime: '',
        endTime: '',
        goodsRange: 0, // 0：全部商品，1：指定商品
        goodsIds: [],
        selectedRules: [], // 当前已选规则序号
        explain: '',
        payDateRange: [], // 付款时间范围
        rules: {
          fullPrice: null,
          fullNumber: null,
          tagId: null,
          userAction: null,
          payTop: null,
          minPayNum: 1,
          maxPayNum: 10,
          cardId: [],
          payStartTime: null,
          payEndTime: null
        },
        gifts: []
      },
      // 校验
      paramRules: {
        name: [{ required: true, message: '请填写活动名称', trigger: 'change' }],
        level: [{ required: true, validator: validatelevel, trigger: 'change' }],
        dateRange: [{ required: true, message: '请填写活动时间', trigger: 'change' }],
        goodsRange: [{ required: true, validator: validategoodsRange, trigger: 'change' }],
        selectedRules: [{ required: true, validator: validateselectedRules, trigger: 'change', type: 'array' }],
        'rules.fullPrice': [{ validator: validatMoney, trigger: 'change' }],
        'rules.fullNumber': [{ validator: validatNum, trigger: 'change' }],
        'rules.tagId': [{ validator: validattagId, trigger: 'change' }],
        'rules.cardId': [{ validator: validatcardId, trigger: 'change' }],
        'rules.payTop': [{ validator: validatInt, trigger: 'change' }],
        'rules.minPayNum': [{ validator: validatMin, trigger: 'change' }],
        payDateRange: [{ validator: validatPayDateRange, trigger: 'blur' }], // 付款时间
        'rules.userAction': [{ validator: validatUserAction, trigger: 'change' }],
        explain: [{ required: true, message: '请填写赠品规则说明', trigger: 'change' }]
      },
      userAction: this.$t('gift.userAction'),
      // 系统中的全部赠品规则
      rules: this.$t('gift.rules'),
      // 当前已选规则序号
      // selectedRules: [],
      // 赠品表格
      tableData: [],
      // 选中商品id
      tmpGoodsIds: [],
      // 选中赠品商品id
      tmpGiftGoodsIds: [],
      srcList: {
        src1: `${this.$imageHost}/image/admin/new_preview_image/gift.jpg`
      },
      tuneUpChooseGoods: false, // 商品弹窗
      isOnlyShowChooseGoods: false,
      tuneUpGoods: false,
      // 商品弹窗回调数据
      goodslength: 0, // 商品个数
      specsIds: [], // 规格id
      specsList: [], // 规格数据

      giftsList: [] // 编辑初始化赠品信息
    }
  },
  computed: {
    stepButtonOffset () {
      if (this.step === 2) {
        return 0
      }
      return 4
    },
    ongoing () {
      return this.status === status[1].status
    },
    goodsBtnName () {
      if (this.ongoing) {
        return this.$t('gift.viewCommodity')
      }
      return this.$t('gift.addCommodity')
    }
  },
  mounted () {
    this.langDefault()
    this.param.selectedRules = []
    const { id } = this.$route.params
    this.update = !!id
    this.id = id || null
    if (this.$route.params.id) {
      // 编辑回显
      this.loadData()
      this.listenGoodsResult()
    }
    // 获取会员标签
    getTagList().then(res => {
      if (res.error === 0) {
        this.tagsList = res.content
      }
    })
    // 获取会员卡数据
    getMemberCardList().then((res) => {
      if (res.error === 0) {
        this.cardList = res.content
      }
    })
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 下一步
    nextStep () {
      this.formatParam()
      this.$refs['param'].validate((valid) => {
        if (valid) {
          this.step++
        }
      })
    },
    // 上一步
    lastStep () {
      this.step--
    },
    // 保存
    addGift () {
      // 校验
      if (this.tableData.length < 1) {
        this.$message.warning('请选择赠品')
        return false
      }
      var result = this.tableData.map(item => {
        var re = /^(0|\+?[1-9][0-9]*)$/
        if (!item.productNumber) {
          this.$message.warning({ message: '请填写赠品库存' })
          return false
        } else if (!re.test(item.productNumber)) {
          this.$message.warning({ message: '赠品库存只能是0或者正整数' })
          return false
        } else if (item.productNumber > Number(item.prdNumber)) {
          this.$message.warning('赠品当前库存不能大于商品库存')
          return false
        } else {
          return true
        }
      })
      if (result.indexOf(false) !== -1) {
        return false
      }

      // 赠品数据
      this.param.gifts = []
      this.tableData.forEach(item => {
        this.param.gifts.push({
          productId: item.prdId || item.productId,
          productNumber: item.productNumber
        })
      })
      // 活动商品
      if (this.param.goodsRange === 0) {
        this.param.goodsIds = []
      }
      console.log(this.param)
      if (this.update) {
        // 编辑保存
        var obj = this.param
        obj.id = this.id
        updateGift(obj).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('gift.editSuccess') })
            this.$router.replace('/admin/home/main/gift')
          } else {
            this.$message.warning({ message: this.$t('gift.editDefault') })
          }
        })
      } else {
        // 添加保存
        addGift(this.param).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('gift.saveSuccess') })
            this.$router.replace('/admin/home/main/gift')
          } else {
            this.$message.warning({ message: this.$t('gift.saveDefault') })
          }
        })
      }
    },
    formatParam () {
      this.formatTime()
      this.formatRules()
    },
    // 格式化入参时间
    formatTime () {
      // 活动时间
      if (this.param.dateRange) {
        this.param.startTime = this.param.dateRange[0]
        this.param.endTime = this.param.dateRange[1]
      }
      // 付款时间
      if (this.param.payDateRange) {
        this.param.rules.payStartTime = this.param.payDateRange[0]
        this.param.rules.payEndTime = this.param.payDateRange[1]
      }
    },
    // 处理商品规则
    formatRules () {
      const { selectedRules } = this.param
      // 将 param.rules 中，不在已选规则序号中的属性赋值 null
      Object.keys(this.param.rules).forEach(key => {
        const index = this.rules.findIndex(rule => undefined !== rule.keys.find(k => k === key))
        if (undefined === selectedRules.find(rule => rule === index)) {
          this.param.rules[key] = null
        }
      })
    },
    // 回显数据加载
    loadData () {
      var id = this.$route.params.id
      getGiftDetail(id).then((res) => {
        if (res.error === 0) {
          var data = res.content
          this.param.name = data.name
          this.param.level = data.level
          this.param.dateRange = [data.startTime, data.endTime]
          this.param.startTime = data.startTime
          this.param.endTime = data.endTime
          this.param.rules = data.rules
          this.param.explain = data.explain
          this.param.goodsIds = data.goodsIds
          // 过滤失效会员标签
          var arrTag = []
          if (this.tagsList.length > 0 && this.param.rules.tagId) {
            this.tagsList.forEach(item => {
              this.param.rules.tagId.forEach(val => {
                if (item.id === val) {
                  arrTag.push(val)
                }
              })
            })
            this.param.rules.tagId = arrTag
          }
          // 过滤失效会员卡
          var arrCard = []
          if (this.cardList.length > 0 && this.param.rules.cardId) {
            this.cardList.forEach(item => {
              this.param.rules.cardId.forEach(val => {
                if (item.id === val) {
                  arrCard.push(val)
                }
              })
            })
            this.param.rules.cardId = arrCard
          }

          // 保存赠品信息
          this.giftsList = res.content.gifts

          this.loadRules(res.content)
          this.loadGoods(res.content)
          this.loadGifts(res.content.gifts)
          this.loadStatus(res.content)
        }
      })
    },
    // 加载赠品规则
    loadRules (content) {
      const { rules } = content
      const { payStartTime, payEndTime } = rules
      if (payStartTime && payEndTime) {
        this.param.payDateRange = [payStartTime, payEndTime]
      }
      this.param.rules.payStartTime = payStartTime || null
      this.param.rules.payEndTime = payEndTime || null
      // 获取接口返回结果的赠品规则中，属性值不为 null 的规则对应的序号
      Object.keys(rules).forEach(key => {
        if (rules[key] !== null) {
          const index = this.rules.findIndex(rule => undefined !== rule.keys.find(k => k === key))
          if (this.param.selectedRules.findIndex(rule => rule === index) === -1) {
            this.param.selectedRules.push(index)
          }
        }
      })
    },
    // 加载活动商品
    loadGoods (content) {
      let { goodsIds } = content
      if (!goodsIds || goodsIds.length === 0) {
        // 全部商品
        this.param.goodsRange = 0
      } else {
        // 指定商品
        this.param.goodsRange = 1
        this.goodslength = goodsIds.length
        // this.tmpGoodsIds = goodsIds
        // this.transmitEditGoodsId(goodsIds)
      }
    },
    // 加载赠品信息
    loadGifts (gifts) {
      this.tableData = gifts
      this.tableData.forEach(item => {
        item.goodsImg = this.$imageHost + '/' + item.goodsImg
        this.specsIds.push(item.productId)
      })
    },
    loadStatus ({ status }) {
      this.status = status
    },
    // 已选赠品规则中是否包含某个规则序号
    contains (ruleIndex) {
      return this.param.selectedRules.find(i => i === ruleIndex) !== undefined
    },
    // 查找规则
    findRule (key) {
      return this.rules.find(rule => rule.keys.findIndex(k => k === key) !== -1)
    },

    // 选择商品/规格弹窗
    showChoosingGoods () {
      if (this.step === 1) {
        this.isOnlyShowChooseGoods = false
        this.tuneUpGoods = !this.tuneUpGoods
      } else {
        this.tuneUpChooseGoods = !this.tuneUpChooseGoods
        console.log(this.specsIds)
      }
    },

    // 选择商品弹窗-部分
    onlyShowChoosingGoods () {
      if (this.step === 1) {
        this.isOnlyShowChooseGoods = true
        this.tuneUpGoods = !this.tuneUpGoods
      } else {
        this.tuneUpChooseGoods = !this.tuneUpChooseGoods
        console.log(this.specsIds)
      }
    },

    // 选择商品弹窗回调显示
    getGoodsIds (data) {
      this.goodslength = data.length
      this.param.goodsIds = data
    },

    // 选择规格弹窗id回调显示
    getSpecsIds (ids) {
      console.log('getSpecsIds', ids)
      this.specsIds = ids
    },

    // 规格弹窗数据回调
    getSpecsData (data) {
      console.log('getSpecsData', data)
      // this.specsData = data

      if (this.update === false) {
        // 添加状态
        data.forEach(item => {
          this.$set(item, 'productNumber', item.prdNumber)
        })
      } else {
        // 编辑状态
        data.forEach(item => {
          this.$set(item, 'productNumber', item.prdNumber)
          this.$set(item, 'offerNumber', 0)
          this.giftsList.forEach(val => {
            if (item.prdId === val.productId) {
              this.$set(item, 'productNumber', val.productNumber)
              this.$set(item, 'offerNumber', val.offerNumber)
            }
          })
        })
      }
      this.tableData = data
      console.log(this.tableData)
    },

    // 规格表格删除
    deleteHandler (index) {
      this.tableData.splice(index, 1)
      this.specsIds = []
      this.tableData.forEach(row => {
        this.specsIds.push(row.prdId)
      })
    },

    // 添加一行赠品商品
    addProductRow (productId) {
      getProductDetail(this.id, productId).then(({ content }) => {
        const { goodsImg, prdImg } = content
        const row = {
          ...content,
          goodsImg: prdImg || goodsImg
        }
        const index = this.tableData.findIndex(row => row.productId === productId)
        if (index !== -1) {
          this.tableData.splice(index, 1)
        }
        this.tableData.push({
          ...row
          // productNumber: row.prdNumber + offerNumber
        })
      })
    },
    // 参数校验
    validateParam () {
      let result = true
      this.param.selectedRules.forEach(index => {
        const { label, keys } = this.rules[index]
        keys.forEach(key => {
          const value = this.param.rules[key]
          if (!value || (typeof value === 'object' && value.length < 1)) {
            this.$message.warning(`请输入${label}`)
            result = false
          }
        })
      })
      return result
    },
    handleChoosingGoods (ids) {
      if (this.ongoing) {
        return
      }
      this.tmpGoodsIds = ids
    },
    handleChoosingProduct (ids) {
      this.tmpGiftGoodsIds = ids
      ids.forEach(prdId => this.addProductRow(prdId))
    },
    listenGoodsResult () {
      this.$http.$on('result', ids => {
        switch (this.step) {
          case 1:
            this.handleChoosingGoods(ids)
            break
          case 2:
            this.handleChoosingProduct(ids)
            break
        }
      })
    },

    // 会员卡移除校验
    removeCard () {
      this.$refs['param'].validateField('rules.cardId')
    },
    // 会员标签移除校验
    removeTag () {
      this.$refs['param'].validateField('rules.tagId')
    },

    // 校验输入库存
    productNumberCheck (value1, value2) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value2) {
        this.$message.warning({ message: '请填写赠品库存' })
        return false
      }
      if (!re.test(value2)) {
        this.$message.warning({ message: '赠品库存只能是0或者正整数' })
        return false
      }
      if (value2 > Number(value1)) {
        this.$message.warning('赠品当前库存不能大于商品库存')
        return false
      }
    }
  },
  watch: {
    goodsRange (v) {
      if (v === 0) {
        // 选择了”全部商品“
        this.param.goodsIds = []
      }
    },

    lang () {
      this.steps = this.$t('gift.steps')
      this.goodsRanges = this.$t('gift.goodsRanges')
      this.rules = this.$t('gift.rules')
      this.userAction = this.$t('gift.userAction')
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 20px;
  margin-bottom: 60px;
  background: #fff;
}
.label {
  line-height: 40px;
}
.input {
  margin-right: 10px;
  width: 70px;
}
.numberStyle {
  display: flex;
  line-height: 45px;
  vertical-align: middle;
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
  border-top: 1px solid #eee;
  z-index: 99;
}

.name_cell {
  display: flex;
  div {
    line-height: 45px;
    margin-left: 10px;
  }
}
.goods_img {
  width: 45px;
  height: 45px;
}
.description {
  color: #999;
}
.inputWidth {
  width: 180px;
}
.remarks {
  color: #999;
  margin-left: 20px;
  height: 30px;
  line-height: 30px;
  font-size: 14px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
</style>
