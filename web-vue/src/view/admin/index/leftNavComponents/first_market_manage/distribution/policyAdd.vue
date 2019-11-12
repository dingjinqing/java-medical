<template>
  <div class="container">

    <!-- 表单 -->
    <div>
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="200px"
        :label-position="'right'"
      >
        <el-form-item
          label="返利策略名称："
          prop="name"
        >
          <el-input placeholder="请输入返利策略名称"></el-input>
        </el-form-item>
        <el-form-item
          label="返利策略优先级："
          prop=""
        >
          <el-input placeholder="请输入返利策略优先级"></el-input>
          <div class="text">当一个商品被添加到多个策略时，执行优先级最高的，可填写1到100间的整数。允许优先级重复，若重复则返利商品执行最新创建的返利策略。</div>
        </el-form-item>
        <el-form-item
          label="有效期："
          prop=""
        >
          <el-date-picker
            :disabled="this.isEdite"
            v-model="form.validity"
            type="datetimerange"
            :range-separator="$t('seckill.to')"
            :start-placeholder="$t('seckill.startTime')"
            :end-placeholder="$t('seckill.endTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          label="分销员自购返利："
          prop=""
        >
          <el-radio-group v-model="form.radio1">
            <el-radio :label="1">开启</el-radio>
            <el-radio :label="0">关闭</el-radio>
          </el-radio-group>
          <span class="tips">开启后，分销员购买商品也会获得返利，返利比例为分销员当前等级的直接邀请返利比例。</span>
          <div class="text">注：当自购返利开关开启，若下单人是分销员，则该下单人的间接邀请人不会获得返利，其直接邀请人可获得返利，返利比例为直接邀请人所在等级的间接邀请返利比例</div>
        </el-form-item>
        <el-form-item prop="">
          <template slot="label">
            <el-tooltip
              effect="dark"
              placement="top"
            >
              <div slot="content">
                <p>成本价保护：</p>
                <p>当单件商品实付金额-成本价大于0时，按分销比例分配差额</p>
                <p>当单件商品实付金额-成本价小于等于0时，返利为0</p>
                <p>注：</p>
                <p>未设置成本价的商品无效</p>
              </div>
              <i class="el-icon-warning"></i>
            </el-tooltip>
            <span>成本价保护：</span>

          </template>
          <el-radio-group v-model="form.radio2">
            <el-radio :label="1">开启</el-radio>
            <el-radio :label="0">关闭</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="邀请新用户下首单返利配置："
          prop=""
        >
          <el-radio-group v-model="form.radio3">
            <el-radio :label="1">开启</el-radio>
            <el-radio :label="0">关闭</el-radio>
          </el-radio-group>
          <span class="tips">开启后，分销员邀请新用户并引导其在店铺下首单（直接邀请关系），可单独设置返利比例。帮助店铺快速拉新，提高新用户成单率。</span>
        </el-form-item>
        <el-form-item
          label="返利佣金比例："
          prop=""
        >
          <el-table
            border
            :data="tableData"
          >
            <el-table-column
              prop="level"
              label="等级"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              label="等级名称"
              align="center"
              width="350px"
            >
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.levelName"
                  disabled
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop=""
              align="center"
            >
              <template slot="header">
                <span>返利佣金比例</span>
                <el-tooltip
                  effect="dark"
                  placement="top"
                >
                  <div slot="content">
                    <p>直接邀请返利比例：分销员成功推广后获取的佣金</p>
                    <p>间接邀请返利比例：B是A发展的分销员，B成功推广后，A可获得邀请奖励佣金</p>
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </template>

              <template slot-scope="scope">
                <div style="width: 100%; height: 80px;">
                  <div style="width:50%;float: left;">
                    <div>直接邀请返利比例
                      <el-input
                        size="mini"
                        style="width: 50px;"
                      ></el-input> %
                    </div>
                    <div style="margin-top: 10px;">间接邀请返利比例
                      <el-input
                        size="mini"
                        style="width: 50px;"
                      ></el-input> %
                    </div>
                  </div>
                  <div
                    style="width:50%;float: left;"
                    v-if="scope.row.level === '1'"
                  >
                    当前等级分销员可获返利金额为下单商品金额的0%-0%
                  </div>
                </div>
                <div
                  v-if="form.radio3 === 1"
                  style="width: 100%;"
                >直接邀请新用户下首单返利比例
                  <el-input
                    size="mini"
                    style="width: 50px;"
                  ></el-input> %
                </div>
              </template>
              <template></template>
            </el-table-column>
          </el-table>
          <div
            class="text"
            style="line-height: 2;margin-top: 10px;"
          >该策略配置商品都按当前比例结算佣金，佣金值=商品实际支付金额*佣金比例，例如，分销商品价格100元，返利佣金比例20%，那么用户购买一件分销商品，邀请该用户分销员获得20元佣金。订单支付完成佣金返利到分销员分销中心的余额账户中，但是该佣金为待返利状态，订单完成后，佣金返利，分销员可以直接使用该佣金购物。限制小数点后一位数字。</div>
        </el-form-item>
        <el-form-item
          label="分销员权限："
          prop=""
        >
          <el-checkbox v-model="form.checked">推广赠送优惠券</el-checkbox>
          <span class="tips">允许分销员分销商品时赠送优惠券</span>
        </el-form-item>
        <el-form-item
          label="分销商品："
          prop=""
        >
          <el-radio-group v-model="form.radio4">
            <el-radio :label="1">全部商品</el-radio>
            <el-radio :label="0">指定商品</el-radio>
          </el-radio-group>
          <div v-if="form.radio4 === 0">
            <div
              v-for="(item,index) in storeArr"
              :key="index"
              class="storeContent"
            >
              <el-button @click="hanldeToAddGoodS(index)">
                <i class="el-icon-plus"></i> {{ item.name }}
              </el-button>
              <span v-if="index === 0">已选{{ goodsInfo.length > 0 ? goodsInfo.length : 0 }}件商品</span>
              <span v-if="index === 1">已选{{ busClass.length > 0 ? busClass.length : 0 }}个商家</span>
              <span v-if="index === 2">已选{{ platClass.length > 0 ? platClass.length : 0 }}个平台</span>
            </div>
          </div>
        </el-form-item>

      </el-form>

    </div>

    <!-- 底部 -->
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        :disabled="submitStatus"
        @click="saveClickHandler(form)"
      >保存</el-button>
    </div>

    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
      :chooseGoodsBack="goodsInfo"
    />
    <!-- 选择 1商家分类;2平台分类弹窗 -->
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpBusClassDialog"
      :classFlag="classFlag"
      @BusClassTrueDetailData="busClassDialogResult"
      @backDataArr="commInfo"
    />

  </div>
</template>
<script>
// 引入组件
export default {

  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog')
  },
  props: ['isEdite', 'editId'],
  data () {
    return {
      // 表单
      form: {
        name: '', //  返利策略名称
        level: '', //  返利策略优先级
        validity: '', // 有效期
        startTime: '',
        endTime: '',
        radio1: 1, // 分销员自购返利
        radio2: 1, // 成本价保护
        radio3: 1, // 邀请新用户下首单返利配置
        checked: true, // 分销员权限
        radio4: 1 // 分销商品
      },
      // 校验表单
      fromRules: {
        name: { required: true, message: '请填写活动名称', trigger: 'blur' }
      },
      tipContent: `
        <p>成本价保护：</p>
        <p>当单件商品实付金额-成本价大于0时，按分销比例分配差额</p>
        <p>当单件商品实付金额-成本价小于等于0时，返利为0</p>
        <p>注：</p>
        <p>未设置成本价的商品无效</p>
      `,
      // 表格数据
      tableData: [{
        level: '1',
        levelName: '分销员测试',
        rule: 1,
        num: '36',
        status: 0
      }, {
        level: '2',
        levelName: 'v2',
        rule: 1,
        num: '22',
        status: 0
      }, {
        level: '3',
        levelName: '分销员组3',
        rule: 2,
        num: '2',
        status: 1
      }, {
        level: '4',
        levelName: '分销员组4',
        rule: 2,
        num: '2',
        status: 1
      }, {
        level: '5',
        levelName: '分销员组5',
        rule: 2,
        num: '2',
        status: 0
      }],
      // 分销商品
      storeArr: [
        {
          name: '添加商品',
          value: '1',
          num: ''
        },
        {
          name: '添加商品分类',
          value: '2',
          num: ''
        },
        {
          name: '添加平台分类',
          value: '3',
          num: ''
        }
      ],
      tuneUpChooseGoods: false, // 商品弹窗
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

      submitStatus: false
    }
  },
  mounted () {
    // 编辑初始化
    if (this.isEdite === true) {
      this.editSeckillInit()
    }
  },
  methods: {
    // 编辑初始化
    editSeckillInit () {

    },

    // 保存秒杀活动
    saveClickHandler () {
    },

    // 点击指定商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      console.log(index)
      switch (index) {
        case 0:
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

    // 选择商品弹窗回调显示
    choosingGoodsResult (row) {
      console.log('选择商品弹窗回调显示:', row)
      this.goodsInfoRow = row
      this.goodsInfo = []
      this.goodsInfoRow.map((item, index) => {
        this.goodsInfo.push(item.goodsId)
      })
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
    }
  }
}
</script>
<style scoped>
.el-input {
  width: 200px;
}
.container {
  margin-top: 10px;
  padding: 10px;
  margin-bottom: 100px;
  background: #fff;
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
</style>
