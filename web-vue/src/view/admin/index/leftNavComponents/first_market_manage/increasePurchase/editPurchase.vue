<!--
****
** 编辑加价购活动页面
****
-->
<template>
  <div class="content">
    <div class="setpTitle">
      <el-steps
        :active="step"
        finish-status="success"
        simple
        align-center
      >
        <el-step :title="$t('purchase.setActivityRule')"></el-step>
        <el-step :title="$t('purchase.setMainGoods')"></el-step>
        <el-step :title="$t('purchase.setRedemptionGoods')"></el-step>
      </el-steps>
    </div>
    <!-- 活动规则 -->
    <div
      class="form1"
      v-if="step === 0"
    >
      <el-form
        class="form"
        ref="form1"
        :rules="rules"
        :model="form1"
        label-width="180px"
      >
        <el-form-item
          :label="$t('purchase.activityName')"
          prop="name"
        >
          <el-input
            v-model="form1.name"
            class="input"
          ></el-input>
          <span class="span">{{$t('purchase.content')}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('purchase.activityprioty')"
          prop="level"
        >
          <el-input
            v-model.number="form1.level"
            class="input"
          ></el-input>
          <span class="span">{{$t('purchase.content1')}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('purchase.activityTime')"
          prop="activityDate"
        >
          <el-date-picker
            v-model="form1.activityDate"
            type="datetimerange"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="-"
            :start-placeholder="$t('purchase.startdate')"
            :end-placeholder="$t('purchase.enddate')"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('purchase.singlemax')">
          <el-input
            v-model.number="form1.maxChangePurchase"
            class="input"
          ></el-input>
          <span class="span">{{$t('purchase.content2')}}</span>
        </el-form-item>
        <el-form-item :label="$t('purchase.redemptionGoodsFeright')">
          <el-radio-group v-model.number="form1.redemptionFreight">
            <el-radio :label=0>{{$t('purchase.free')}}</el-radio>
            <el-radio :label=1>{{$t('purchase.noFree')}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          :label="$t('purchase.activityRule')"
          prop="rule_setting"
        >
          <span class="span">{{$t('purchase.content3')}}</span>
        </el-form-item>
        <el-form-item :label="$t('purchase.rule1')">
          {{$t('purchase.mainFull')}}<el-input
            class="input1"
            v-model.number="purcahse_rule1.fullPrice"
          ></el-input>{{$t('purchase.add')}}<el-input
            class="input1"
            v-model.number="purcahse_rule1.purchasePrice"
          ></el-input>{{$t('purchase.redemp')}}
          <el-button
            type="primary"
            size="small"
            style="margin-left:5px"
            v-if="rule_button1"
            @click="ruleButton1"
          >+{{$t('purchase.addRule')}}</el-button>
        </el-form-item>
        <el-form-item
          label="换购规则2"
          v-if="rule_line2"
        >
          {{$t('purchase.mainFull')}}<el-input
            class="input1"
            v-model.number="purcahse_rule2.fullPrice"
          ></el-input>{{$t('purchase.add')}}<el-input
            class="input1"
            v-model.number="purcahse_rule2.purchasePrice"
          ></el-input>{{$t('purchase.redemp')}}
          <el-button
            type="primary"
            size="small"
            style="margin-left:5px"
            v-if="rule_button2"
            @click="ruleButton2"
          >+{{$t('purchase.addRule')}}</el-button>
          <el-link
            type="primary"
            style="margin-left:5px"
            @click="ruleDelete2"
          >{{$t('purchase.deleteRule')}}</el-link>
        </el-form-item>
        <el-form-item
          :label="$t('purchase.rule3')"
          v-if="rule_line3"
        >
          {{$t('purchase.mainFull')}}<el-input
            class="input1"
            v-model.number="purcahse_rule3.fullPrice"
          ></el-input>{{$t('purchase.add')}}<el-input
            class="input1"
            v-model.number="purcahse_rule3.purchasePrice"
          ></el-input>{{$t('purchase.redemp')}}
          <el-link
            type="primary"
            style="margin-left:5px"
            @click="ruleDelete3"
          >{{$t('purchase.deleteRule')}}</el-link>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="nextStep(1)"
          >{{$t('purchase.nextStep')}}</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 主商品 -->
    <div
      class="main_table"
      v-if="step === 1"
    >
      <div>
        <el-button
          type="primary"
          @click="showChoosingGoods"
        >{{$t('purchase.chooseGoods')}}</el-button>
        <!--选择商品弹窗-->
        <ChoosingGoods
          :tuneUpChooseGoods="tuneUpChooseGoods"
          @resultGoodsDatas="choosingGoodsResult"
          :chooseGoodsBack="goodsId"
        />
      </div>
      <div class="table">
        <el-table
          :data="main_table"
          style="width: 100%"
        >
          <el-table-column :label="$t('purchase.goodsName')">
            <template slot-scope="{ row }">
              <img
                :src="row.goodsImg"
                style="width: 45px; height: 45px"
              >
              <label>{{row.goodsName}}</label>
            </template>
          </el-table-column>
          <el-table-column
            prop="shopPrice"
            :label="$t('purchase.goodsPrice')"
          >
          </el-table-column>
          <el-table-column
            prop="goodsNumber"
            :label="$t('purchase.goodsSupply')"
          >
          </el-table-column>
          <el-table-column :label="$t('purchase.opration')">
            <template slot-scope="{ row }">
              <el-link
                type="primary"
                @click="deleteGoods(row.goodsId)"
              >{{$t('purchase.delete')}}</el-link>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="bottom">
        <el-button
          type="primary"
          @click="preStep"
        >{{$t('purchase.preStep')}}</el-button>
        <el-button
          type="primary"
          @click="nextStep(2)"
        >{{$t('purchase.nextStep')}}</el-button>
      </div>
    </div>
    <!-- 换购商品 -->
    <div
      class="purchase_tab"
      v-if="step === 2"
    >
      <el-tabs
        v-model="purchase_tab"
        @tab-click="handleClick"
      >
        <el-tab-pane
          :label="$t('purchase.rule1')"
          name="first"
        >
          <div style="margin_top:10px">
            <el-button
              type="primary"
              @click="showChoosingGoods1"
            >{{$t('purchase.chooseRedempGoods')}}</el-button>
            <!--选择商品弹窗-->
            <ChoosingGoods
              :tuneUpChooseGoods="tuneUpChooseGoods1"
              @resultGoodsDatas="choosingGoodsResult1"
              :chooseGoodsBack="purcahse_rule1.productId"
            />
          </div>
          <div class="table">
            <el-table
              :data="purchase_table1"
              style="width: 100%"
            >
              <el-table-column :label="$t('purchase.goodsName')">
                <template slot-scope="{ row }">
                  <img
                    :src="row.goodsImg"
                    style="width: 45px; height: 45px"
                  >
                  <label>{{row.goodsName}}</label>
                </template>
              </el-table-column>
              <el-table-column
                prop="shopPrice"
                :label="$t('purchase.goodsPrice')"
              >
              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                :label="$t('purchase.goodsSupply')"
              >
              </el-table-column>
              <el-table-column :label="$t('purchase.opration')">
                <template slot-scope="{ row }">
                  <el-link
                    type="primary"
                    @click="deleteGoods1(row.goodsId)"
                  >{{$t('purchase.delete')}}</el-link>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        <el-tab-pane
          :label="$t('purchase.rule2')"
          name="second"
          v-if="rule_num >= 2"
        >
          <div style="margin_top:10px">
            <el-button
              type="primary"
              @click="showChoosingGoods2"
            >{{$t('purchase.chooseRedempGoods')}}</el-button>
            <!--选择商品弹窗-->
            <ChoosingGoods
              :tuneUpChooseGoods="tuneUpChooseGoods2"
              @resultGoodsDatas="choosingGoodsResult2"
              :chooseGoodsBack="purcahse_rule2.productId"
            />
          </div>
          <div class="table">
            <el-table
              :data="purchase_table2"
              style="width: 100%"
            >
              <el-table-column :label="$t('purchase.goodsName')">
                <template slot-scope="{ row }">
                  <img
                    :src="row.goodsImg"
                    style="width: 45px; height: 45px"
                  >
                  <label>{{row.goodsName}}</label>
                </template>
              </el-table-column>
              <el-table-column
                prop="shopPrice"
                :label="$t('purchase.goodsPrice')"
              >
              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                :label="$t('purchase.goodsSupply')"
              >
              </el-table-column>
              <el-table-column :label="$t('purchase.opration')">
                <template slot-scope="{ row }">
                  <el-link
                    type="primary"
                    @click="deleteGoods2(row.goodsId)"
                  >{{$t('purchase.delete')}}</el-link>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        <el-tab-pane
          :label="$t('purchase.rule3')"
          name="third"
          v-if="rule_num === 3"
        >
          <div style="margin_top:10px">
            <el-button
              type="primary"
              @click="showChoosingGoods3"
            >{{$t('purchase.chooseRedempGoods')}}</el-button>
            <!--选择商品弹窗-->
            <ChoosingGoods
              :tuneUpChooseGoods="tuneUpChooseGoods3"
              @resultGoodsDatas="choosingGoodsResult3"
              :chooseGoodsBack="purcahse_rule3.productId"
            />
          </div>
          <div class="table">
            <el-table
              :data="purchase_table3"
              style="width: 100%"
            >
              <el-table-column :label="$t('purchase.goodsName')">
                <template slot-scope="{ row }">
                  <img
                    :src="row.goodsImg"
                    style="width: 45px; height: 45px"
                  >
                  <label>{{row.goodsName}}</label>
                </template>
              </el-table-column>
              <el-table-column
                prop="shopPrice"
                :label="$t('purchase.goodsPrice')"
              >
              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                :label="$t('purchase.goodsSupply')"
              >
              </el-table-column>
              <el-table-column :label="$t('purchase.opration')">
                <template slot-scope="{ row }">
                  <el-link
                    type="primary"
                    @click="deleteGoods3(row.goodsId)"
                  >{{$t('purchase.delete')}}</el-link>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
      <div class="bottom">
        <el-button
          type="primary"
          @click="preStep"
        >{{$t('purchase.preStep')}}</el-button>
        <el-button
          type="primary"
          @click="updatePurchase"
        >{{$t('purchase.save')}}</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import { } from '@/api/admin/marketManage/sharePolite.js'
import ChoosingGoods from '@/components/admin/choosingGoods'
import { getDetail, update } from '@/api/admin/marketManage/increasePurchase.js'
export default {
  components: {
    ChoosingGoods
  },
  data () {
    var validateRule = (rule, value, callback) => {
      if (this.rule_num === 1) {
        if (this.purcahse_rule1.fullPrice === '' || this.purcahse_rule1.purchasePrice === '') {
          callback(new Error(this.$t('purchase.content4')))
        } else { callback() }
      } else if (this.rule_num === 2) {
        if (this.purcahse_rule1.fullPrice === '' || this.purcahse_rule1.purchasePrice === '' ||
          this.purcahse_rule2.fullPrice === '' || this.purcahse_rule2.purchasePrice === '') {
          callback(new Error(this.$t('purchase.content4')))
        } else { callback() }
      } else if (this.rule_num === 3) {
        if (this.purcahse_rule1.fullPrice === '' || this.purcahse_rule1.purchasePrice === '' ||
          this.purcahse_rule2.fullPrice === '' || this.purcahse_rule2.purchasePrice === '' ||
          this.purcahse_rule3.fullPrice === '' || this.purcahse_rule3.purchasePrice === '') {
          callback(new Error(this.$t('purchase.content4')))
        } else { callback() }
      } else { callback() }
    }
    return {
      step: 0,
      // 换购规则的2个按钮是否显示
      rule_button1: true,
      rule_button2: true,
      // 换购规则2和3是否显示
      rule_line2: false,
      rule_line3: false,
      // 换购规则数量
      rule_num: 1,
      // 换购规则页面参数
      form1: {
        id: 0,
        name: '',
        level: '',
        activityDate: [],
        startTime: '',
        endTime: '',
        maxChangePurchase: 0,
        redemptionFreight: 0,
        rule_setting: '非空'
      },
      // 换购规则
      purcahse_rule1: {
        id: 0,
        fullPrice: '',
        purchasePrice: '',
        // 换购商品id集合
        productId: []
      },
      purcahse_rule2: {
        id: 0,
        fullPrice: '',
        purchasePrice: '',
        productId: []
      },
      purcahse_rule3: {
        id: 0,
        fullPrice: '',
        purchasePrice: '',
        productId: []
      },
      rules: {
        name: [
          { required: true, message: this.$t('purchase.inputName'), trigger: 'blur' }
        ],
        level: [
          { required: true, message: this.$t('purchase.choosepriority'), trigger: 'blur' }
        ],
        activityDate: [
          { type: 'array', required: true, message: this.$t('purchase.chooseTime'), trigger: 'change' }
        ],
        rule_setting: [
          { required: true, validator: validateRule }
        ]
      },
      form_check: false,
      // 主商品页面参数
      main_table: [],
      // 商品弹窗初始数据，编辑页面时用(同时也是主商品id集合)
      goodsId: [],
      tuneUpChooseGoods: false,
      // 换购商品页面参数
      purchase_table1: [],
      purchase_table2: [],
      purchase_table3: [],
      purchase_tab: 'first',
      tuneUpChooseGoods1: false,
      tuneUpChooseGoods2: false,
      tuneUpChooseGoods3: false,
      queryParam: {
        purchaseId: 0
      },
      imgHost: `${this.$imageHost}`
    }
  },
  mounted () {
    this.queryParam.purchaseId = this.$route.params.id
    this.form1.id = this.$route.params.id
    this.initData()
  },
  methods: {
    initData () {
      // getDetail, update
      getDetail(this.queryParam).then(res => {
        if (res.error === 0) {
          this.form1 = res.content
          this.form1.activityDate = []
          this.form1.activityDate.push(this.form1.startTime)
          this.form1.activityDate.push(this.form1.endTime)
          this.main_table = res.content.mainGoods
          this.setDomainImg(this.main_table)
          this.main_table.map((item, index) => {
            this.goodsId.push(item.goodsId)
          })
          console.log('goodsId' + this.goodsId)
          this.setPurchaseRule(res.content.purchaseInfo, res.content.redemptionGoods)
        }
      })
    },
    // 将查询规则转化为展示数据
    setPurchaseRule (dataRule, dataGoods) {
      if (dataRule.length >= 1) {
        var array = dataRule[0].split(' --- ')
        this.purcahse_rule1.id = parseFloat(array[0])
        this.purcahse_rule1.fullPrice = parseFloat(array[1])
        this.purcahse_rule1.purchasePrice = parseFloat(array[2])
        this.purchase_table1 = dataGoods[0]
        this.setDomainImg(this.purchase_table1)
        this.purchase_table1.map((item, index) => {
          this.purcahse_rule1.productId.push(item.goodsId)
        })
      }
      if (dataRule.length >= 2) {
        var array1 = dataRule[1].split(' --- ')
        this.purcahse_rule2.id = parseFloat(array1[0])
        this.purcahse_rule2.fullPrice = parseFloat(array1[1])
        this.purcahse_rule2.purchasePrice = parseFloat(array1[2])
        this.purchase_table2 = dataGoods[1]
        this.setDomainImg(this.purchase_table2)
        this.purchase_table2.map((item, index) => {
          this.purcahse_rule2.productId.push(item.goodsId)
        })
        this.rule_line2 = true
        this.rule_button1 = false
      }
      if (dataRule.length >= 3) {
        var array2 = dataRule[2].split(' --- ')
        this.purcahse_rule3.id = parseFloat(array2[0])
        this.purcahse_rule3.fullPrice = parseFloat(array2[1])
        this.purcahse_rule3.purchasePrice = parseFloat(array2[2])
        this.purchase_table3 = dataGoods[2]
        this.setDomainImg(this.purchase_table3)
        this.purchase_table3.map((item, index) => {
          this.purcahse_rule3.productId.push(item.goodsId)
        })
        this.rule_line3 = true
        this.rule_button2 = false
      }
      this.rule_num = dataRule.length
    },
    // 图片加域名
    setDomainImg (data) {
      data.map((item, index) => {
        item.goodsImg = this.imgHost + '/' + item.goodsImg
      })
    },
    nextStep (value) {
      if (value === 1) {
        this.formCheck()
        if (this.form_check) {
          if (this.step++ > 2) this.step = 0
        }
      } else if (value === 2) {
        if (this.main_table.length === 0) {
          this.$message.info({
            message: this.$t('purchase.chooseMain'),
            showClose: true
          })
        } else {
          if (this.step++ > 2) this.step = 0
        }
      } else {
        if (this.step++ > 2) this.step = 0
      }
    },
    preStep () {
      this.form_check = false
      if (this.step-- < 0) this.step = 0
    },
    // 设置换购规则
    ruleButton1 () {
      this.rule_line2 = true
      this.rule_button1 = false
      this.rule_num++
    },
    ruleButton2 () {
      this.rule_line3 = true
      this.rule_button2 = false
      this.rule_num++
    },
    ruleDelete2 () {
      if (this.rule_line3 === true) {
        this.rule_line3 = false
        this.rule_button2 = true
        this.rule_num--
      } else {
        this.rule_line2 = false
        this.rule_button1 = true
        this.rule_num--
      }
    },
    ruleDelete3 () {
      this.rule_line3 = false
      this.rule_button2 = true
      this.rule_num--
    },
    // 设置活动规则表单验证
    formCheck () {
      this.$refs['form1'].validate((valid) => {
        console.log('valid:' + valid)
        if (valid === true) {
          this.form_check = true
        } else {
          this.form_check = false
        }
      })
    },
    // 设置主商品
    // 选择商品弹窗调起
    showChoosingGoods () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    // 选择商品弹窗回调显示
    choosingGoodsResult (row) {
      this.main_table = row
      this.updateGoodsId(this.main_table)
    },
    updateGoodsId (data) {
      this.goodsId = []
      data.map((item, index) => {
        this.goodsId.push(item.goodsId)
      })
    },
    // 删除选中的主商品
    deleteGoods (goodsId) {
      this.main_table.map((item, index) => {
        if (item.goodsId === goodsId) {
          this.main_table.splice(index, 1)
        }
      })
      this.updateGoodsId(this.main_table)
    },
    // 设置换购商品
    // 换购规则tab切换
    handleClick (tab, event) {
      console.log(tab, event)
    },
    // 选择换购商品弹窗调起
    showChoosingGoods1 () {
      this.tuneUpChooseGoods1 = !this.tuneUpChooseGoods1
    },
    showChoosingGoods2 () {
      this.tuneUpChooseGoods2 = !this.tuneUpChooseGoods2
    },
    showChoosingGoods3 () {
      this.tuneUpChooseGoods3 = !this.tuneUpChooseGoods3
    },
    // 选择换购商品弹窗回调显示
    choosingGoodsResult1 (row) {
      this.purchase_table1 = row
      this.purcahse_rule1.productId = []
      this.purchase_table1.map((item, index) => {
        this.purcahse_rule1.productId.push(item.goodsId)
      })
    },
    // 删除选中的换购商品-规则1
    deleteGoods1 (goodsId) {
      this.purchase_table1.map((item, index) => {
        if (item.goodsId === goodsId) {
          this.purchase_table1.splice(index, 1)
        }
      })
      this.purcahse_rule1.productId = []
      this.purchase_table1.map((item, index) => {
        this.purcahse_rule1.productId.push(item.goodsId)
      })
    },
    choosingGoodsResult2 (row) {
      this.purchase_table2 = row
      this.purcahse_rule2.productId = []
      this.purchase_table2.map((item, index) => {
        this.purcahse_rule2.productId.push(item.goodsId)
      })
    },
    // 删除选中的换购商品-规则2
    deleteGoods2 (goodsId) {
      this.purchase_table2.map((item, index) => {
        if (item.goodsId === goodsId) {
          this.purchase_table2.splice(index, 1)
        }
      })
      this.purcahse_rule2.productId = []
      this.purchase_table2.map((item, index) => {
        this.purcahse_rule2.productId.push(item.goodsId)
      })
    },
    choosingGoodsResult3 (row) {
      this.purchase_table3 = row
      this.purcahse_rule3.productId = []
      this.purchase_table3.map((item, index) => {
        this.purcahse_rule3.productId.push(item.goodsId)
      })
    },
    // 删除选中的换购商品-规则3
    deleteGoods3 (goodsId) {
      this.purchase_table3.map((item, index) => {
        if (item.goodsId === goodsId) {
          this.purchase_table3.splice(index, 1)
        }
      })
      this.purcahse_rule3.productId = []
      this.purchase_table3.map((item, index) => {
        this.purcahse_rule3.productId.push(item.goodsId)
      })
    },
    purchaseCheck () {
      if (this.rule_num === 1) {
        if (this.purchase_table1.length === 0) {
          this.purchaseInfo()
          return false
        } else { return true }
      } else if (this.rule_num === 2) {
        if (this.purchase_table1.length === 0) {
          this.purchaseInfo()
          return false
        } else if (this.purchase_table2.length === 0) {
          this.purchaseInfo()
          return false
        } else { return true }
      } else if (this.rule_num === 3) {
        if (this.purchase_table1.length === 0) {
          this.purchaseInfo()
          return false
        } else if (this.purchase_table2.length === 0) {
          this.purchaseInfo()
          return false
        } else if (this.purchase_table3.length === 0) {
          this.purchaseInfo()
          return false
        } else {
          return true
        }
      }
    },
    purchaseInfo () {
      this.$message.info({
        message: this.$t('purchase.chooseRedemp'),
        showClose: true
      })
    },
    // 更新加价购
    updatePurchase () {
      if (this.purchaseCheck()) {
        this.form1.startTime = this.form1.activityDate[0]
        this.form1.endTime = this.form1.activityDate[1]
        let param = Object.assign({}, this.form1)
        param.goodsId = this.goodsId.join()
        param.rules = this.getPurchaseRules()
        update(param).then(res => {
          if (res.error === 0) {
            this.$message.success({
              message: '更新成功！',
              showClose: true
            })
            this.jump2ListPage()
          } else {
            this.$message.error({
              message: '更新失败！',
              showClose: true
            })
          }
        })
      }
    },
    getPurchaseRules () {
      let rules = []
      if (this.rule_num >= 1) {
        this.purcahse_rule1.productId = this.purcahse_rule1.productId.join()
        rules.push(this.purcahse_rule1)
      }
      if (this.rule_num >= 2) {
        this.purcahse_rule2.productId = this.purcahse_rule2.productId.join()
        rules.push(this.purcahse_rule2)
      }
      if (this.rule_num >= 3) {
        this.purcahse_rule3.productId = this.purcahse_rule3.productId.join()
        rules.push(this.purcahse_rule3)
      }
      return rules
    },
    // 编辑成功后跳转到列表页
    jump2ListPage () {
      this.$router.push({
        name: 'purchase_price',
        params: {
          flag: true
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  background: #fff;
  margin-top: 10px;
  margin: 10px, 10px, 10px, 10px;
  .setpTitle {
    margin-top: 10px;
    margin-left: 10%;
    width: 80%;
  }
  .form1 {
    margin-top: 10px;
    margin-left: 10%;
    width: 80%;
    .form {
      width: 80%;
      margin-left: 10%;
      .input {
        width: 250px;
      }
      .input1 {
        width: 100px;
        margin-left: 5px;
        margin-right: 5px;
      }
      .span {
        margin-left: 5px;
        color: #999;
        font-size: 14px;
      }
    }
  }
  .main_table {
    margin-top: 10px;
    margin-left: 10%;
    width: 80%;
    .table {
      margin-top: 10px;
      margin-left: 10%;
      width: 80%;
    }
    .bottom {
      margin-top: 10px;
      margin-left: 40%;
    }
  }
  .purchase_tab {
    margin-top: 10px;
    margin-left: 10%;
    width: 80%;
    .table {
      margin-top: 10px;
      margin-left: 10%;
      width: 80%;
    }
    .bottom {
      margin-top: 10px;
      margin-left: 40%;
    }
  }
}
</style>
