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
          :label="$t('distribution.strategyName') + '：'"
          prop="strategyName"
        >
          <el-input
            v-model="form.strategyName"
            :placeholder="$t('distribution.strategyTip1')"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.ratioLevel') + '：'"
          prop="strategyLevel"
        >
          <el-input
            v-model.number="form.strategyLevel"
            :placeholder="$t('distribution.strategyTip2')"
          ></el-input>
          <div class="text">{{ $t('distribution.strategyTip3') }}</div>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.strategyValidity') + '：'"
          prop="validity"
        >
          <el-date-picker
            v-model="form.validity"
            type="datetimerange"
            :range-separator="$t('seckill.to')"
            :start-placeholder="$t('seckill.startTime')"
            :end-placeholder="$t('seckill.endTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.selfPurchase') + '：'"
          prop="selfPurchase"
        >
          <el-radio-group v-model="form.selfPurchase">
            <el-radio :label="1">{{ $t('distribution.purchaseOpen') }}</el-radio>
            <el-radio :label="0">{{ $t('distribution.purchaseClose') }}</el-radio>
          </el-radio-group>
          <span class="tips">{{ $t('distribution.strategyTip4') }}</span>
          <div class="text">{{ $t('distribution.strategyTip5') }}</div>
        </el-form-item>
        <el-form-item prop="costProtection">
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
          <el-radio-group v-model="form.costProtection">
            <el-radio :label="1">{{ $t('distribution.purchaseOpen') }}</el-radio>
            <el-radio :label="0">{{ $t('distribution.purchaseClose') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="邀请新用户下首单返利配置："
          prop=""
        >
          <el-radio-group v-model="form.firstRebate">
            <el-radio :label="1">{{ $t('distribution.purchaseOpen') }}</el-radio>
            <el-radio :label="0">{{ $t('distribution.purchaseClose') }}</el-radio>
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
              prop="levelText"
              :label="$t('distribution.level')"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              :label="$t('distribution.levelName')"
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
            <el-table-column align="center">
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
                        v-model.number="scope.row.fanliRatio"
                        size="mini"
                        style="width: 50px;"
                      ></el-input> %
                    </div>
                    <div style="margin-top: 10px;">间接邀请返利比例
                      <el-input
                        v-model.number="scope.row.rebateRatio"
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
                  v-if="form.firstRebate === 1"
                  style="width: 100%;"
                >直接邀请新用户下首单返利比例
                  <el-input
                    v-model="scope.row.firstRatio"
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
          <el-checkbox
            v-model="form.sendCoupon"
            :true-label='1'
            :false-label="0"
          >推广赠送优惠券</el-checkbox>
          <span class="tips">允许分销员分销商品时赠送优惠券</span>
        </el-form-item>
        <el-form-item
          label="分销商品："
          prop=""
        >
          <el-radio-group v-model="form.recommendType">
            <el-radio :label="0">全部商品</el-radio>
            <el-radio :label="1">指定商品</el-radio>
          </el-radio-group>
          <div v-if="form.recommendType === 1">
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
        @click="saveClickHandler()"
      >{{ $t('distribution.rebateSave') }}</el-button>
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
import { addPolicy, getPolicyDetail, editPolicy, getDistributionLevel } from '@/api/admin/marketManage/distribution.js'
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
        strategyName: '', //  返利策略名称
        strategyLevel: '', //  返利策略优先级
        validity: '', // 有效期
        startTime: '',
        endTime: '',
        selfPurchase: 1, // 分销员自购返利
        costProtection: 1, // 成本价保护
        firstRebate: 1, // 邀请新用户下首单返利配置
        sendCoupon: 1, // 分销员权限
        recommendType: 0, // 分销商品
        recommendGoodsId: [], // 商品ID
        recommendCatId: [], // 分类ID
        recommendSortId: [] // 商家ID
      },
      // 校验表单
      fromRules: {
        strategyName: { required: true, message: '请填写返利策略名称', trigger: 'blur' },
        strategyLevel: { required: true, message: '请填写返利策略优先级', trigger: 'blur' },
        validity: { required: true, message: '请填写有效期', trigger: 'change' },
        selfPurchase: { required: true, message: '请选择是否开启自购返利', trigger: 'change' }
      },
      tipContent: `
        <p>成本价保护：</p>
        <p>当单件商品实付金额-成本价大于0时，按分销比例分配差额</p>
        <p>当单件商品实付金额-成本价小于等于0时，返利为0</p>
        <p>注：</p>
        <p>未设置成本价的商品无效</p>
      `,
      // 佣金比例表格数据
      tableData: [{
        levelId: 1,
        levelName: '分销员测试',
        fanliRatio: 0, // 直接比例
        rebateRatio: 0, // 间接比例
        firstRatio: null // 首单返利
      }, {
        levelId: 2,
        levelName: 'v2',
        fanliRatio: null, // 直接比例
        rebateRatio: null, // 间接比例
        firstRatio: null // 首单返利
      }, {
        levelId: 3,
        levelName: '分销员组3',
        fanliRatio: null, // 直接比例
        rebateRatio: null, // 间接比例
        firstRatio: null // 首单返利
      }, {
        levelId: 4,
        levelName: '分销员组4',
        fanliRatio: null, // 直接比例
        rebateRatio: null, // 间接比例
        firstRatio: null // 首单返利
      }, {
        levelId: 5,
        levelName: '分销员组5',
        fanliRatio: null, // 直接比例
        rebateRatio: null, // 间接比例
        firstRatio: null // 首单返利
      }],
      // 分销商品
      storeArr: [
        {
          name: '添加商品',
          value: '1'
        },
        {
          name: '添加商品分类',
          value: '2'
        },
        {
          name: '添加平台分类',
          value: '3'
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
    this.initDataList()
    // 编辑初始化
    if (this.isEdite === true) {
      this.editSeckillInit(this.editId)
    }
  },
  methods: {
    // 获取分销员等级
    initDataList () {
      getDistributionLevel().then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.levelList)
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      console.log(this.tableData)
      this.tableData.map((item, index) => {
        item.levelId = data[index].levelId
        item.levelName = data[index].levelName
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
      console.log(this.tableData)
    },

    // 编辑初始化
    editSeckillInit (id) {
      getPolicyDetail(id).then((res) => {
        if (res.error === 0) {
          var data = res.content[0]
          this.form.strategyName = data.strategyName
          this.form.strategyLevel = data.strategyLevel
          // 有效期
          this.form.validity = [data.startTime, data.endTime]
          this.form.selfPurchase = data.selfPurchase
          this.form.costProtection = data.costProtection
          this.form.firstRebate = data.firstRebate
          this.form.sendCoupon = data.sendCoupon
          this.form.recommendType = data.recommendType
          // 直接返利
          this.tableData[0].fanliRatio = data.fanliRatio
          this.tableData[1].fanliRatio = data.fanliRatio_2
          this.tableData[2].fanliRatio = data.fanliRatio_3
          this.tableData[3].fanliRatio = data.fanliRatio_4
          this.tableData[4].fanliRatio = data.fanliRatio_5

          // 间接返利
          this.tableData[0].rebateRatio = data.rebateRatio
          this.tableData[1].rebateRatio = data.rebateRatio_2
          this.tableData[2].rebateRatio = data.rebateRatio_3
          this.tableData[3].rebateRatio = data.rebateRatio_4
          this.tableData[4].rebateRatio = data.rebateRatio_5

          // 首单返利
          this.tableData[0].firstRatio = data.firstRatio
          this.tableData[1].firstRatio = data.firstRatio_2
          this.tableData[2].firstRatio = data.firstRatio_3
          this.tableData[3].firstRatio = data.firstRatio_4
          this.tableData[4].firstRatio = data.firstRatio_5

          // 返利商品
          this.goodsInfo = data.recommendGoodsId.split(',')
          this.goodsInfo = this.goodsInfo.map(Number)

          this.busClass = data.recommendCatId.split(',')
          this.busClass = this.busClass.map(Number)

          this.platClass = data.recommendSortId.split(',')
          this.platClass = this.platClass.map(Number)
        }
      })
    },

    // 保存返利策略
    saveClickHandler () {
      this.submitStatus = true

      // 有效期
      this.form.startTime = this.form.validity[0]
      this.form.endTime = this.form.validity[1]

      // 直接返利
      this.form.fanliRatio = this.tableData[0].fanliRatio
      this.form.fanliRatio_2 = this.tableData[1].fanliRatio
      this.form.fanliRatio_3 = this.tableData[2].fanliRatio
      this.form.fanliRatio_4 = this.tableData[3].fanliRatio
      this.form.fanliRatio_5 = this.tableData[4].fanliRatio

      // 间接返利
      this.form.rebateRatio = this.tableData[0].rebateRatio
      this.form.rebateRatio_2 = this.tableData[1].rebateRatio
      this.form.rebateRatio_3 = this.tableData[2].rebateRatio
      this.form.rebateRatio_4 = this.tableData[3].rebateRatio
      this.form.rebateRatio_5 = this.tableData[4].rebateRatio

      // 首单返利
      this.form.firstRatio = this.tableData[0].firstRatio
      this.form.firstRatio_2 = this.tableData[1].firstRatio
      this.form.firstRatio_3 = this.tableData[2].firstRatio
      this.form.firstRatio_4 = this.tableData[3].firstRatio
      this.form.firstRatio_5 = this.tableData[4].firstRatio

      // 返利商品
      this.form.recommendGoodsId = this.goodsInfo.toString() // 商品
      this.form.recommendSortId = this.busClass.toString() // 商家
      this.form.recommendCatId = this.platClass.toString() // 平台

      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.isEdite === false) {
            // 添加返利
            addPolicy(this.form).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: '添加成功!' })
                this.$emit('addPolicySubmit')
              } else {
                this.$message.warning({ message: '添加失败!' })
              }
            })
          } else {
            // 编辑返利
            var obj = this.form
            obj.id = this.editId
            editPolicy(obj).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: '编辑成功!' })
                this.$emit('addPolicySubmit')
              } else {
                this.$message.warning({ message: '编辑失败!' })
              }
            })
          }
        }
      })
      this.submitStatus = false
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
