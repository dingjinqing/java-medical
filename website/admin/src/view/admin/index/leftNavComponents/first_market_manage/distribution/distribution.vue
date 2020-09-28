<template>
  <div class="content">
    <div class="main">
      <el-tabs
        type="border-card"
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          :label="$t('distribution.distributionCfg')"
          name="first"
        >
          <distributeSetting
            v-if="activeName === 'first'"
            @tabChange="tabChange"
            @inviteCode="inviteCodeHandler"
            @distributionSetting="distributionSetting"
          />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorLevelCfg')"
          name="second"
        >
          <distributorLevelSetting
            v-if="activeName === 'second'"
            @tabChange="tabChange"
            @distributorLevel="getLevelId"
          />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.rebateStrategyCfg')"
          name="third"
        >
          <returnPolicySetting v-if="activeName === 'third'" />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorList')"
          name="fouth"
        >
          <distributorList
            v-if="activeName === 'fouth'"
            :inviteFlag="inviteCode"
            :distributorGroup="distributorGroup"
            :distributorLevel="distributorLevel"
            @commissionHandler="commissionHandler"
          />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorGroup')"
          name="fifth"
        >
          <distributorGroup
            v-if="activeName === 'fifth'"
            @tabChange="tabChange"
            @distributorGroup="getGroupId"
          />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.commissionStatistics')"
          name="sixth"
        >
          <moneyStatistics
            :userId="userId"
            :username="username"
            :mobile="mobile"
            :settlementFlag="settlementFlag"
            v-if="activeName === 'sixth'"
          />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.rebateGoodsStatistics')"
          name="seventh"
        >
          <goodsReturnStatistics v-if="activeName === 'seventh'" />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.withdrawAudit')"
          name="eighth"
        >
          <withdrawDepositCheck
            v-if="activeName === 'eighth'"
            :username="username"
            :status="status"
          />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorAudit')"
          name="ninth"
          v-if="judgeStatus === '1'"
        >
          <distributorCheck v-if="activeName === 'ninth'" />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.advertisement')"
          name="tenth"
        >
          <advertisement v-if="activeName === 'tenth'" />
        </el-tab-pane>
      </el-tabs>

      <distributorCheck v-if="overViewFlag === '1'" />
    </div>
  </div>
</template>

<script>
import distributeSetting from './distributeSetting'
import distributorGroup from './distributorGroup'
import distributorLevelSetting from './distributorLevelSetting'
import distributorList from './distributorList'
import goodsReturnStatistics from './goodsReturnStatistics'
import moneyStatistics from './moneyStatistics'
import returnPolicySetting from './returnPolicySetting'
import withdrawDepositCheck from './withdrawDepositCheck'
import distributorCheck from './distributorCheck'

export default {
  components: {
    advertisement: () => import('./advertisement'),
    distributeSetting,
    distributorGroup,
    distributorLevelSetting,
    distributorList,
    goodsReturnStatistics,
    moneyStatistics,
    returnPolicySetting,
    withdrawDepositCheck,
    distributorCheck
  },
  inject: ['reload'],
  data () {
    return {
      activeName: 'first',
      inviteCode: true,
      distributorLevel: 0,
      distributorGroup: 0,
      userId: null,
      username: '',
      mobile: '',
      settlementFlag: [],
      status: null,
      judgeStatus: '0', // 分销配置是否开启
      overViewFlag: '0'
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      if (from.name === null || (to.params.distributorName === 'fouth' && to.name === 'distribution_info' && (from.name === 'distribution_info_inviteUser' || from.name === 'distribution_info_indirectUser')) ||
        (to.params.distributorName === 'seventh' && to.name === 'distribution_info' && from.name === 'distribution_info_goodsStatictics') ||
        (to.params.distributorName === 'eighth' && to.name === 'distribution_info' && from.name === 'distribution_info_withdrawDetail') ||
        ((to.params.distributorName === 'sixth' || to.params.distributorName === 'fouth' || to.params.distributorName === 'eighth') && to.name === 'distribution_info' && from.name === 'membershipInformation')) {
        vm.activeName = localStorage.getItem('distributionTap')
      } else {
        localStorage.removeItem('distributionTapIndex')
      }
      vm.judgeStatus = localStorage.getItem('distributionJudgeStatus')

      // 代办事项跳转
      if ((localStorage.getItem('fromPage') !== 'overView' || !localStorage.getItem('fromPage')) && from.name !== null) {
        localStorage.setItem('overViewFlag', 0)
      }
      vm.overViewFlag = localStorage.getItem('overViewFlag')
      localStorage.removeItem('fromPage')
    })
  },
  mounted () {
    this.$http.$on('toChangeActiveName', (flag) => {
      if (flag) {
        this.activeName = 'first'
      }
    })

    // 店铺助手跳转分销审核
    if (this.$route.params.distributorName) {
      // tab重新赋值
      this.activeName = this.$route.params.distributorName
    }

    // 会员详情跳转
    this.userId = this.$route.params.userId
    this.username = this.$route.params.username
    this.mobile = this.$route.params.mobile
    this.settlementFlag = this.$route.params.settlementFlag
    this.status = this.$route.params.status
    this.distributorLevel = this.$route.params.distributorLevel
    this.distributorGroup = this.$route.params.distributorGroup

    // console.log(this.$route.params)
    // if (this.$route.params.flag === 1) {
    //   if (this.$route.params.distributorName) {
    //     // tab重新赋值
    //     this.activeName = this.$route.params.distributorName
    //   }
    // }
  },
  methods: {
    handleClick (tab) {
      console.log(tab.index)
      this.distributorGroup = 0
      this.distributorLevel = 0
      this.$http.$emit('distributionTap', tab.name)

      localStorage.setItem('distributionTap', tab.name) // 刷新保持当前tab名称
      localStorage.setItem('distributionTapIndex', tab.name)
      localStorage.setItem('overViewFlag', 0)
      this.overViewFlag = localStorage.getItem('overViewFlag')

      this.userId = null
      this.username = ''
      this.mobile = ''
      this.settlementFlag = []
      this.status = null
      this.distributorLevel = 0
      this.distributorGroup = 0
    },
    tabChange () {
      this.activeName = 'fouth'
    },
    getGroupId (data) {
      this.distributorGroup = data
    },
    getLevelId (data) {
      this.distributorLevel = data
    },
    inviteCodeHandler (val) {
      this.inviteCode = val
    },
    // 跳转佣金统计
    commissionHandler (data) {
      this.userId = data
      this.activeName = 'sixth'
    },
    distributionSetting () {
      this.judgeStatus = localStorage.getItem('distributionJudgeStatus')
    }
  }
}

</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px;
  }
}

/deep/ .el-tabs--border-card {
  background: #fff;
  border: none;
  box-shadow: none;
}
/deep/ .el-tabs--border-card > .el-tabs__header {
  background: #f5f5f5;
}
/deep/ .el-tabs__nav-wrap {
  border: 1px solid #f3f3f3;
}
/deep/ .el-tabs--border-card > .el-tabs__header .el-tabs__item.is-active,
/deep/ .el-tabs--border-card > .el-tabs__header .el-tabs__item {
  color: #333;
}
</style>
