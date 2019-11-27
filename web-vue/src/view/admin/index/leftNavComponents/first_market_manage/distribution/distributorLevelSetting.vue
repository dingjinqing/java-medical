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
        ></i>提示：每次修改分销员等级，将会有大量分销员受到影响，请谨慎操作</div>
    </div>

    <el-button
      type="text"
      @click="centerDialogVisible = true"
    >升级规则</el-button>

    <el-dialog
      title="提醒"
      :visible.sync="centerDialogVisible"
      width="25%"
      center
      :close-on-click-modal="false"
    >
      <div class="textInfo">累计邀请用户数：分销员累积邀请的用户数。</div>
      <div class="textInfo">累积推广金：分销员推广商品的订单累计金额。</div>
      <div class="textInfo">累积消费金：分销员在店铺累积消费金额。</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="centerDialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>

    <!-- 表格 -->
    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="levelText"
          label="等级"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="等级名称"
          align="center"
        >
          <template slot-scope="scope">
            <el-input v-model="scope.row.levelName"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          label="升级规则"
          align="center"
          width="300px"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.levelId === 1">成为分销员后，默认即是该等级</div>
            <el-radio-group
              v-model="scope.row.levelUpRoute"
              v-if="scope.row.levelId !== 1"
            >
              <el-radio :label="0">自动升级</el-radio>
              <el-radio :label="1">手动升级</el-radio>
            </el-radio-group>
            <div
              v-if="scope.row.levelUpRoute === 0 && scope.row.levelId !== 1"
              style="margin: 15px 0;"
            >
              <div>累计邀请用户数达
                <el-input
                  v-model="scope.row.inviteNumber"
                  @blur="checkNum(scope.row.inviteNumber)"
                  size="mini"
                  style="width: 70px;"
                ></el-input> 个</div>
              <div>或</div>
              <div>累计推广金达
                <el-input
                  v-model="scope.row.totalDistributionMoney"
                  @blur="checkMoney(scope.row.totalDistributionMoney)"
                  size="mini"
                  style="width: 70px;"
                ></el-input> 元</div>
              <div>或</div>
              <div>累积推广金与消费金总和达
                <el-input
                  v-model="scope.row.totalBuyMoney"
                  @blur="checkMoney(scope.row.totalBuyMoney)"
                  size="mini"
                  style="width: 70px;"
                ></el-input> 元</div>
            </div>
            <div
              v-if="scope.row.levelUpRoute === 1 && (scope.row.levelId === 2 || scope.row.levelId === 3)"
              style="margin: 15px 0;"
            >
              <el-button
                size="mini"
                @click="addDistributor(scope.row.levelId)"
              ><i class="el-icon-plus"></i> 添加分销员</el-button>
            </div>
          </template>
        </el-table-column>

        <el-table-column
          label="分销员数量"
          align="center"
        >
          <template slot-scope="scope">
            <a
              href="javascript:void(0);"
              @click="numClickHandler"
            >{{ scope.row.users }}</a>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <p v-if="scope.row.levelId === 1">已启动</p>
            <div v-if="scope.row.levelId !== 1">
              <p v-if="scope.row.levelStatus === 1">已启用</p>
              <p v-if="scope.row.levelStatus === 0">已停用</p>
              <el-button
                type="primary"
                size="mini"
                @click="startHandler(scope.row.id)"
                v-if="scope.row.levelStatus === 0"
              >启用</el-button>
              <el-button
                size="mini"
                @click="stopHandler(scope.row.id)"
                v-if="scope.row.levelStatus === 1"
              >停用</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="listFooter">
      <el-button
        type="primary"
        size="small"
        @click="setDistributionLevel"
      >保存</el-button>
    </div>

    <!-- 添加分销员弹窗 -->
    <DistributorDialog
      :turnUp="turnUpDialogTwo"
      @handleSelect="handleSelectRowTwo"
      :selectRowIds="selectRowTwoIds"
    />

    <DistributorDialog
      :turnUp="turnUpDialogThree"
      @handleSelect="handleSelectRowThree"
      :selectRowIds="selectRowThreeIds"
    />

  </div>
</template>

<script>
import { getDistributionLevel, setDistributionLevel, startDistribution, stopDistribution } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination'),
    DistributorDialog: () => import('@/components/admin/distributorDialog')
  },
  props: {

  },
  data () {
    return {
      levelId: null,
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
        levelStatus: 0
      }, {
        levelId: 2,
        levelName: 'v2',
        levelUpRoute: 0,
        inviteNumber: 0,
        totalDistributionMoney: 0,
        totalBuyMoney: 0,
        levelUserIds: null,
        users: '',
        levelStatus: 0
      }, {
        levelId: 3,
        levelName: '分销员组3',
        levelUpRoute: 1,
        inviteNumber: 0,
        totalDistributionMoney: 0,
        totalBuyMoney: 0,
        levelUserIds: null,
        users: '',
        levelStatus: 1
      }, {
        levelId: 4,
        levelName: '分销员组4',
        levelUpRoute: 1,
        inviteNumber: 0,
        totalDistributionMoney: 0,
        totalBuyMoney: 0,
        levelUserIds: null,
        users: '',
        levelStatus: 1
      }, {
        levelId: 5,
        levelName: '分销员组5',
        levelUpRoute: 1,
        inviteNumber: 0,
        totalDistributionMoney: 0,
        totalBuyMoney: 0,
        levelUserIds: null,
        users: '',
        levelStatus: 0
      }],
      centerDialogVisible: false, // 规则弹框
      turnUpDialogTwo: false, // 等级2弹窗
      selectRowTwo: [],
      selectRowTwoIds: [],
      turnUpDialogThree: false, // 等级3弹窗
      selectRowThree: [],
      selectRowThreeIds: []
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
        if (res.error === 0) {
          this.handleData(res.content.levelList)
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
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
      this.tableData = data
    },

    // 设置分销员等级
    setDistributionLevel () {
      this.tableData.map((item, index) => {
        if (index === 1) {
          item.levelUserIds = this.selectRowTwoIds.toString()
        } else if (index === 2) {
          item.levelUserIds = this.selectRowThreeIds.toString()
        }
      })
      setDistributionLevel(this.tableData).then((res) => {
        if (res.error === 0) {
          this.$message.success({ message: '保存成功!' })
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
    addDistributor (levelId) {
      this.levelId = levelId
      if (levelId === 2) {
        this.turnUpDialogTwo = !this.turnUpDialogTwo
      } else if (levelId === 3) {
        this.turnUpDialogThree = !this.turnUpDialogThree
      }
    },

    // 等级2弹框回调函数
    handleSelectRowTwo (row) {
      this.selectRowTwo = row
      this.selectRowTwoIds = []
      this.selectRowTwo.map(item => {
        this.selectRowTwoIds.push(item.userId)
      })
    },

    // 等级3弹框回调函数
    handleSelectRowThree (row) {
      this.selectRowThree = row
      this.selectRowThreeIds = []
      this.selectRowThree.map(item => {
        this.selectRowThreeIds.push(item.userId)
      })
    },

    // 校验累计数
    checkNum (value) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!re.test(value)) {
        this.$message.warning({ message: '请填写0或者正整数' })
      }
    },

    // 校验金额
    checkMoney (value) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        this.$message.warning({ message: '请填写非负数, 可以保留两位小数' })
      }
    },

    // 跳转分销员列表
    numClickHandler () {
      this.$emit('tabChange')
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
  z-index: 99;
}
</style>
