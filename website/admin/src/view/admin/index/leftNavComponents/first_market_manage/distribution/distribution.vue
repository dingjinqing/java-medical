<template>
  <div class="content">
    <div class="main">
      <el-tabs
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
          <withdrawDepositCheck v-if="activeName === 'eighth'" />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorAudit')"
          name="ninth"
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
  data () {
    return {
      activeName: 'first',
      inviteCode: '',
      distributorLevel: 0,
      distributorGroup: 0,
      userId: null
    }
  },
  mounted () {
    this.$http.$on('toChangeActiveName', (flag) => {
      if (flag) {
        this.activeName = 'first'
      }
    })

    // 店铺助手跳转分销审核
    console.log(this.$route.params)
    if (this.$route.params.flag === 1) {
      if (this.$route.params.distributorName) {
        // tab重新赋值
        this.activeName = this.$route.params.distributorName
      }
    }
  },
  methods: {
    handleClick (tab) {
      console.log(tab.index)
      this.$http.$emit('distributionTap', tab.index)
    },
    tabChange () {
      this.activeName = 'fouth'
    },
    getGroupId (data) {
      this.distributorGroup = data
      console.log(this.distributorGroup)
    },
    getLevelId (data) {
      debugger
      this.distributorLevel = data
      console.log(this.distributorLevel)
    },
    inviteCodeHandler (val) {
      this.inviteCode = val
    },
    // 跳转佣金统计
    commissionHandler (data) {
      this.userId = data
      this.activeName = 'sixth'
    }
    // advertisementList()
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
</style>
