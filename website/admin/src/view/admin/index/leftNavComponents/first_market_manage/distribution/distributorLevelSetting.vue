<template>
  <div
    class="content"
    style="margin-bottom: 60px;"
  >
    <div class="main">
      <div class="mainInfo">
        <i
          class="el-icon-warning"
          style="color: #E6A23C;margin-right: 5px;"
        ></i>{{ $t('distribution.levelTip') }}</div>
    </div>

    <el-button
      type="text"
      @click="centerDialogVisible = true"
    >{{ $t('distribution.levelText') }}</el-button>

    <el-form
      ref="form"
      :model="form"
      :rules="fromRules"
    >
      <!-- <el-form-item>
        <el-checkbox
          v-model="form.config"
          @change="configChange"
        >
          邀请新用户下首单且订单中参与返利的商品支付金额达到
          <el-form-item
            prop="configMoney"
            style="display: inline-block;"
          >
            <el-input
              v-model="form.configMoney"
              size="small"
              style="width: 100px;margin: 0 5px;"
            ></el-input>
          </el-form-item>
          元, 则给分销员返佣
          <el-tooltip
            placement="top"
            effect="light"
          >
            <div slot="content">订单支付金额：包括微信支付、账户余额支付及会员卡余额支付部分；返利订单若满足此条件，则不再执行"返利策略"</div>
            <span
              class="el-icon-question"
              style="color: #666;cursor: pointer;"
            ></span>
          </el-tooltip>
        </el-checkbox>
      </el-form-item> -->

      <el-form-item prop="tableData">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="form.tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="levelText"
            :label="$t('distribution.level')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            :label="$t('distribution.levelName')"
            align="center"
          >
            <template slot-scope="scope">
              <el-input
                v-model="scope.row.levelName"
                size="small"
                style="width: 170px;"
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('distribution.levelText')"
            align="center"
            width="300px"
          >
            <template slot-scope="scope">
              <el-form-item :prop="'tableData.' + scope.$index+ '.levelUpRoute'">
                <div v-if="scope.row.levelId === 1">{{ $t('distribution.level1') }}</div>
                <el-radio-group
                  v-model="scope.row.levelUpRoute"
                  v-if="scope.row.levelId !== 1"
                >
                  <el-radio :label="0">{{ $t('distribution.levelRadio1') }}</el-radio>
                  <el-radio :label="1">{{ $t('distribution.levelRadio2') }}</el-radio>
                </el-radio-group>
                <div
                  v-if="scope.row.levelUpRoute === 0 && scope.row.levelId !== 1"
                  style="margin: 15px 0;"
                >
                  <el-form-item
                    :prop="'tableData.' + scope.$index+ '.inviteNumber'"
                    :rules="[{ validator: (rule, value, callback)=>{validateLevelNum(rule, value, callback)}, trigger: ['blur', 'change'] }]"
                    style="display: inline-block;"
                  >
                    <div>{{ $t('distribution.levelTip1') }}
                      <el-input
                        v-model="scope.row.inviteNumber"
                        size="mini"
                        style="width: 70px;"
                      ></el-input> {{ $t('distribution.levelTip2') }} {{ $t('distribution.levelTip3') }}</div>
                  </el-form-item>
                  <el-form-item
                    :prop="'tableData.' + scope.$index+ '.totalDistributionMoney'"
                    :rules="[{ validator: (rule, value, callback)=>{validateLevelMoney(rule, value, callback)}, trigger: ['blur', 'change'] }]"
                    style="display: inline-block;"
                  >
                    <div>{{ $t('distribution.levelTip4') }}
                      <el-input
                        v-model="scope.row.totalDistributionMoney"
                        size="mini"
                        style="width: 70px;"
                      ></el-input> {{ $t('distribution.levelTip5') }} {{ $t('distribution.levelTip3') }}</div>
                  </el-form-item>
                  <el-form-item
                    :prop="'tableData.' + scope.$index+ '.totalBuyMoney'"
                    :rules="[{ validator: (rule, value, callback)=>{validateLevelMoney(rule, value, callback)}, trigger: ['blur', 'change'] }]"
                    style="display: inline-block;"
                  >
                    <div>{{ $t('distribution.levelTip6') }}
                      <el-input
                        v-model="scope.row.totalBuyMoney"
                        size="mini"
                        style="width: 70px;"
                      ></el-input> {{ $t('distribution.levelTip5') }}</div>
                  </el-form-item>
                </div>
                <div
                  v-if="scope.row.levelUpRoute === 1 && scope.row.levelStatus == 1"
                  style="margin: 15px 0;"
                >
                  <el-button
                    size="mini"
                    @click="addDistributor(scope.row.levelId, scope.row.levelUserIds)"
                  ><i class="el-icon-plus"></i> {{ $t('distribution.addDistributor') }}</el-button>
                </div>
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column
            :label="$t('distribution.distributorsNum')"
            align="center"
          >
            <template slot-scope="scope">
              <span
                @click="numClickHandler(scope.row.levelId)"
                style="color: #5a8bff;cursor: pointer;"
              >{{ scope.row.users ? scope.row.users : 0 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('distribution.option')"
            align="center"
          >
            <template slot-scope="scope">
              <p v-if="scope.row.levelId === 1">{{ $t('distribution.levelAlready') }}</p>
              <div v-if="scope.row.levelId !== 1">
                <p v-if="scope.row.levelStatus === 1">{{ $t('distribution.levelStart2') }}</p>
                <p v-if="scope.row.levelStatus === 0">{{ $t('distribution.levelStop2') }}</p>
                <el-button
                  type="primary"
                  size="mini"
                  @click="startHandler(scope.row.id)"
                  v-if="scope.row.levelStatus === 0"
                >{{ $t('distribution.levelStart1') }}</el-button>
                <el-button
                  size="mini"
                  @click="stopHandler(scope.row.id)"
                  v-if="scope.row.levelStatus === 1"
                >{{ $t('distribution.levelStop1') }}</el-button>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="首单获佣金(元)"
            align="center"
            v-if="form.config === true"
          >
            <template slot-scope="scope">
              <el-form-item
                :prop="'tableData.' + scope.$index+ '.amount'"
                :rules="[ { validator: (rule, value, callback)=>{validateMoney(rule, value, callback)}, trigger: ['blur', 'change'] }]"
              >
                <el-input
                  v-model="scope.row.amount"
                  size="small"
                  style="width: 100px;"
                ></el-input>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>

    <div class="listFooter">
      <el-button
        type="primary"
        size="small"
        @click="setDistributionLevel"
      >{{ $t('distribution.rebateSave') }}</el-button>
    </div>

    <!-- 添加分销员弹窗 -->
    <DistributorDialog
      :turnUp="turnUpDialog"
      @handleSelect="handleSelectRow"
      :userIds="userIds"
    />

    <!-- 升级规则弹窗 -->
    <el-dialog
      :title="$t('distribution.dialogTitle')"
      :visible.sync="centerDialogVisible"
      width="25%"
      center
      :close-on-click-modal="false"
    >
      <div class="textInfo">{{ $t('distribution.dialogText1') }}</div>
      <div class="textInfo">{{ $t('distribution.dialogText2') }}</div>
      <div class="textInfo">{{ $t('distribution.dialogText3') }}</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="centerDialogVisible = false"
        >{{ $t('distribution.dialogSure') }}</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { getDistributionLevel, setDistributionLevel, startDistribution, stopDistribution, manualAddDistributor } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination'),
    DistributorDialog: () => import('@/components/admin/distributorDialog')
  },
  props: {

  },
  data () {
    // 自定义订单金额
    var validateMoney = (rule, value, callback) => {
      var re = /^\d+(\.\d{1,2})?$/
      if (this.form.config === true && !value) {
        callback(new Error('请填写订单金额'))
      } else if (this.form.config === true && !re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else {
        callback()
      }
    }
    return {
      levelId: null,
      centerDialogVisible: false, // 规则弹框
      turnUpDialog: false, // 等级弹窗
      userIds: '', // 手动升级分销员数据回显

      // 表单
      form: {
        config: false, // 邀请新客下首单返佣配置
        configMoney: '', // 返佣金额
        // 表格数据
        tableData: [{
          levelId: 1,
          levelName: '分销员测试',
          levelUpRoute: 0,
          inviteNumber: 0,
          totalDistributionMoney: 0,
          totalBuyMoney: 0,
          levelUserIds: null,
          users: '',
          levelStatus: 1,
          amount: '',
          levelText: '一级'
        }, {
          levelId: 2,
          levelName: 'v2',
          levelUpRoute: 0,
          inviteNumber: 0,
          totalDistributionMoney: 0,
          totalBuyMoney: 0,
          levelUserIds: null,
          users: '',
          levelStatus: 0,
          amount: '',
          levelText: '二级'
        }, {
          levelId: 3,
          levelName: '分销员组3',
          levelUpRoute: 1,
          inviteNumber: 0,
          totalDistributionMoney: 0,
          totalBuyMoney: 0,
          levelUserIds: null,
          users: '',
          levelStatus: 1,
          amount: '',
          levelText: '三级'
        }, {
          levelId: 4,
          levelName: '分销员组4',
          levelUpRoute: 1,
          inviteNumber: 0,
          totalDistributionMoney: 0,
          totalBuyMoney: 0,
          levelUserIds: null,
          users: '',
          levelStatus: 1,
          amount: '',
          levelText: '四级'
        }, {
          levelId: 5,
          levelName: '分销员组5',
          levelUpRoute: 1,
          inviteNumber: 0,
          totalDistributionMoney: 0,
          totalBuyMoney: 0,
          levelUserIds: null,
          users: '',
          levelStatus: 0,
          amount: '',
          levelText: '五级'
        }]
      },
      // 校验表单
      fromRules: {
        configMoney: [{ validator: validateMoney, trigger: 'change' }]
      }
    }
  },
  mounted () {
    // 初始化数据
    this.initDataList()
  },
  methods: {
    // 获取分销员等级
    initDataList () {
      getDistributionLevel().then((res) => {
        if (res.error === 0 && res.content && res.content.length > 0) {
          this.handleData(res.content.levelList)
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        if (item.levelId === 1) {
          item.levelStatus = 1
        }
        if (item.levelUserIds !== null) {
          item.levelUserIds = item.levelUserIds.split(',')
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
    },

    // 设置分销员等级
    setDistributionLevel () {
      console.log(this.form.tableData)
      this.$refs['form'].validate((valid) => {
        if (valid) {
          var result = this.form.tableData.find(item => { return item.levelStatus === 1 && item.levelUpRoute === 0 && !item.inviteNumber && !item.totalDistributionMoney && !item.totalBuyMoney })
          if (result === undefined) {
            setDistributionLevel(this.form.tableData).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: this.$t('distribution.rebateSaveSuccess') })
              } else {
                this.$message.warning(res.message)
              }
            })
          } else {
            this.$message.warning('已启用等级设置不能为空')
          }
        }
      })
    },

    // 启用
    startHandler (id) {
      this.$confirm(this.$t('seckill.startTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        startDistribution(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.startSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.startFail') })
      })
    },

    // 停用
    stopHandler (id) {
      this.$confirm(this.$t('seckill.stopTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        stopDistribution(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.stopSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.stopFail') })
      })
    },

    // 显示等级弹窗
    addDistributor (levelId, levelUserIds) {
      this.levelId = levelId
      this.userIds = levelUserIds
      this.turnUpDialog = !this.turnUpDialog
    },

    // 弹窗回显数据
    handleSelectRow (row) {
      console.log(row)
      manualAddDistributor({
        level: this.levelId,
        userIds: row
      }).then(res => {
        if (res.error === 0) {
          this.$message.success({ message: '添加成功!' })
          this.initDataList()
        }
      })
    },

    // 跳转分销员列表
    numClickHandler (id) {
      this.$emit('tabChange')
      this.$emit('distributorLevel', id)
    },

    // 校验规则个数
    validateLevelNum (rule, value, callback) {
      var re = /^[1-9]\d*$/
      if ((value && !re.test(value)) || value === 0 || value === '0') {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    },

    // 校验规则金额
    validateLevelMoney (rule, value, callback) {
      var re = /^[1-9]\d*(\.\d{1,2})?$/
      var re1 = /^0\.\d{1,2}$/
      if ((value && !re.test(value) && !re1.test(value)) || value === 0 || value === '0') {
        callback(new Error('请填写可以保留两位小数的有效数字'))
      } else {
        callback()
      }
    },

    // 校验首单佣金
    validateMoney (rule, value, callback) {
      var re = /^\d+(\.\d{1,2})?$/
      if (this.form.config === true) {
        if (!value) {
          callback(new Error('请填写佣金'))
        } else if (!re.test(value)) {
          callback(new Error('请填写非负数, 可以保留两位小数'))
        }
      } else {
        callback()
      }
    },

    // 首单返佣金配置
    configChange () {
      this.$refs['form'].validateField('configMoney')
    }
  }
}
</script>
<style lang="scss" scoped>
a {
  text-decoration: none;
  color: #5a8bff;
}
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    .mainInfo {
      width: 100%;
      height: 40px;
      line-height: 37px;
      border: 1px solid #f2e1c8;
      background: #fff7ec;
      color: #666;
      margin-bottom: 10px;
      padding-left: 12px;
    }
  }
  .textInfo {
    margin-bottom: 20px;
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.version-manage-table /deep/ .el-form-item__error {
  position: relative;
  text-align: center;
}
.table_list {
  position: relative;
  background-color: #fff;
  .footer_right {
    padding: 20px 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    span {
      display: block;
      height: 32px;
      line-height: 32px;
    }
  }
}

.el-input {
  width: 180px;
}

.listFooter {
  position: fixed;
  bottom: 0;
  right: 27px;
  width: 87.8%;
  margin: 0 auto;
  height: 50px;
  line-height: 50px;
  background: #f8f8f8;
  text-align: center;
  z-index: 9;
}
</style>
