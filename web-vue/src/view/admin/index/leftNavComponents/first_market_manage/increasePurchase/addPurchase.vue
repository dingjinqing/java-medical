<!--
****
** 添加加价购活动页面
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
        <el-step title="设置活动规则"></el-step>
        <el-step title="设置主商品"></el-step>
        <el-step title="设置换购商品"></el-step>
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
        <el-form-item label="活动名称">
          <el-input
            v-model="form1.name"
            class="input"
          ></el-input>
          <span class="span">只作为商家记录使用，用户不会看到这个名称</span>
        </el-form-item>
        <el-form-item label="活动优先级">
          <el-input
            v-model="form1.priority"
            class="input"
          ></el-input>
          <span class="span">用于区分不同加价购活动的优先级，请填写正整数，数值越大优先级越高</span>
        </el-form-item>
        <el-form-item label="活动时间">
          <el-date-picker
            v-model="form1.time"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="每单最多换购件数">
          <el-input
            v-model="form1.maxNum"
            class="input"
          ></el-input>
          <span class="span">每单最多换购商品数，填写0表示不限制</span>
        </el-form-item>
        <el-form-item label="换购商品运费计算策略">
          <el-radio-group v-model="form1.strategy">
            <el-radio label="免运费"></el-radio>
            <el-radio label="使用原商品运费模板"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="活动规则">
          <span class="span">最多可设置3档换购规则，需满足金额依次递增</span>
        </el-form-item>
        <el-form-item label="换购规则1">
          主商品购满<el-input class="input1"></el-input>元可加<el-input class="input1"></el-input>元换购
          <el-button
            type="primary"
            size="small"
            style="margin-left:5px"
            v-if="rule_button1"
            @click="ruleButton1"
          >+添加规则</el-button>
        </el-form-item>
        <el-form-item
          label="换购规则2"
          v-if="rule_line2"
        >
          主商品购满<el-input class="input1"></el-input>元可加<el-input class="input1"></el-input>元换购
          <el-button
            type="primary"
            size="small"
            style="margin-left:5px"
            v-if="rule_button2"
            @click="ruleButton2"
          >+添加规则</el-button>
          <el-link
            type="primary"
            style="margin-left:5px"
            @click="ruleDelete2"
          >删除</el-link>
        </el-form-item>
        <el-form-item
          label="换购规则3"
          v-if="rule_line3"
        >
          主商品购满<el-input class="input1"></el-input>元可加<el-input class="input1"></el-input>元换购
          <el-link
            type="primary"
            style="margin-left:5px"
            @click="ruleDelete3"
          >删除</el-link>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="nextStep(1)"
          >下一步</el-button>
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
        >选择商品</el-button>
        <!--选择商品弹窗-->
        <ChoosingGoods
          :tuneUpChooseGoods="tuneUpChooseGoods"
          @resultGoodsDatas="choosingGoodsResult"
          :chooseGoodsBack="goodsInfo"
        />
      </div>
      <div class="table">
        <el-table
          :data="main_table"
          style="width: 100%"
        >
          <el-table-column label="商品名称">
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
            label="商品原价"
          >
          </el-table-column>
          <el-table-column
            prop="goodsNumber"
            label="商品库存"
          >
          </el-table-column>
          <el-table-column label="操作">
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
        >上一步</el-button>
        <el-button
          type="primary"
          @click="nextStep(2)"
        >下一步</el-button>
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
          label="规则1"
          name="first"
        >
          <div style="margin_top:10px">
            <el-button
              type="primary"
              @click="showChoosingGoods1"
            >选择换购商品</el-button>
            <!--选择商品弹窗-->
            <ChoosingGoods
              :tuneUpChooseGoods="tuneUpChooseGoods1"
              @resultGoodsDatas="choosingGoodsResult1"
              :chooseGoodsBack="goodsInfo"
            />
          </div>
          <div class="table">
            <el-table
              :data="purchase_table1"
              style="width: 100%"
            >
              <el-table-column label="商品名称">
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
                label="商品原价"
              >
              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                label="商品库存"
              >
              </el-table-column>
              <el-table-column label="操作">
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
          label="规则2"
          name="second"
          v-if="rule_num >= 2"
        >
          <div style="margin_top:10px">
            <el-button
              type="primary"
              @click="showChoosingGoods2"
            >选择换购商品</el-button>
            <!--选择商品弹窗-->
            <ChoosingGoods
              :tuneUpChooseGoods="tuneUpChooseGoods2"
              @resultGoodsDatas="choosingGoodsResult2"
              :chooseGoodsBack="goodsInfo"
            />
          </div>
          <div class="table">
            <el-table
              :data="purchase_table2"
              style="width: 100%"
            >
              <el-table-column label="商品名称">
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
                label="商品原价"
              >
              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                label="商品库存"
              >
              </el-table-column>
              <el-table-column label="操作">
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
          label="规则3"
          name="third"
          v-if="rule_num === 3"
        >
          <div style="margin_top:10px">
            <el-button
              type="primary"
              @click="showChoosingGoods3"
            >选择换购商品</el-button>
            <!--选择商品弹窗-->
            <ChoosingGoods
              :tuneUpChooseGoods="tuneUpChooseGoods3"
              @resultGoodsDatas="choosingGoodsResult3"
              :chooseGoodsBack="goodsInfo"
            />
          </div>
          <div class="table">
            <el-table
              :data="purchase_table3"
              style="width: 100%"
            >
              <el-table-column label="商品名称">
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
                label="商品原价"
              >
              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                label="商品库存"
              >
              </el-table-column>
              <el-table-column label="操作">
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
        >上一步</el-button>
        <el-button
          type="primary"
          @click="addPurchase"
        >保存</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import { } from '@/api/admin/marketManage/sharePolite.js'
import ChoosingGoods from '@/components/admin/choosingGoods'
export default {
  components: {
    ChoosingGoods
  },
  data () {
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
        name: '',
        priority: 0,
        time: '',
        maxNum: 0,
        strategy: 0
      },
      rules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        priority: [
          { required: true, message: '请选择活动优先级', trigger: 'blur' }
        ],
        time: [
          { type: 'date', required: true, message: '请选择活动时间', trigger: 'change' }
        ]
      },
      // 主商品页面参数
      main_table: [],
      // 商品弹窗初始数据，编辑页面时用
      goodsInfo: [],
      tuneUpChooseGoods: false,
      // 换购商品页面参数
      purchase_table1: [],
      purchase_table2: [],
      purchase_table3: [],
      purchase_tab: 'first',
      tuneUpChooseGoods1: false,
      tuneUpChooseGoods2: false,
      tuneUpChooseGoods3: false
    }
  },
  mounted () {
  },
  methods: {
    nextStep (value) {
      if (value === 1) {
        if (this.step++ > 2) this.step = 0
      } else if (value === 2) {
        if (this.main_table.length === 0) {
          this.$message.info({
            message: '请选择主商品！',
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
        if (valid) {
          return true
        } else {
          return false
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
      console.log('选择商品弹窗回调显示:', row)
      this.main_table = row
    },
    // 删除选中的主商品
    deleteGoods (goodsId) {
      this.main_table.map((item, index) => {
        if (item.goodsId === goodsId) {
          this.main_table.splice(index, 1)
        }
      })
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
    },
    // 删除选中的换购商品-规则1
    deleteGoods1 (goodsId) {
      this.purchase_table1.map((item, index) => {
        if (item.goodsId === goodsId) {
          this.purchase_table1.splice(index, 1)
        }
      })
    },
    choosingGoodsResult2 (row) {
      this.purchase_table2 = row
    },
    // 删除选中的换购商品-规则2
    deleteGoods2 (goodsId) {
      this.purchase_table2.map((item, index) => {
        if (item.goodsId === goodsId) {
          this.purchase_table2.splice(index, 1)
        }
      })
    },
    choosingGoodsResult3 (row) {
      this.purchase_table3 = row
    },
    // 删除选中的换购商品-规则3
    deleteGoods3 (goodsId) {
      this.purchase_table3.map((item, index) => {
        if (item.goodsId === goodsId) {
          this.purchase_table3.splice(index, 1)
        }
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
        message: '请选择换购商品！',
        showClose: true
      })
    },
    // 添加加价购
    addPurchase () {
      if (this.purchaseCheck()) {

      }
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
