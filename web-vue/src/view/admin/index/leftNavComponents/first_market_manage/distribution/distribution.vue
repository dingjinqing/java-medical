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
          <distributeSetting @tabChange="tabChange" />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorLevelCfg')"
          name="second"
        >
          <distributorLevelSetting @tabChange="tabChange" />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.rebateStrategyCfg')"
          name="third"
        >
          <returnPolicySetting />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorList')"
          name="fouth"
        >
          <distributorList />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorGroup')"
          name="fifth"
        >
          <distributorGroup />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.commissionStatistics')"
          name="sixth"
        >
          <moneyStatistics />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.rebateGoodsStatistics')"
          name="seventh"
        >
          <goodsReturnStatistics />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.withdrawAudit')"
          name="eighth"
        >
          <withdrawDepositCheck />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.distributorAudit')"
          name="ninth"
        >
          <distributorCheck />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.advertisement')"
          name="tenth"
        >
          <advertisement />
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
      activeName: 'first'
    }
  },
  mounted () {
    this.$http.$on('toChangeActiveName', (flag) => {
      if (flag) {
        this.activeName = 'first'
      }
    })
  },
  methods: {
    handleClick (tab) {
      console.log(tab.index)
      this.$http.$emit('distributionTap', tab.index)
    },
    tabChange () {
      this.activeName = 'fouth'
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
