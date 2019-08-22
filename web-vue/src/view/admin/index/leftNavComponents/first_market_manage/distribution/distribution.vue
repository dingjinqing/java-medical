<template>
  <div>
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
    >
      <el-tab-pane
        label="分销配置"
        name="first"
      >
        <distributeSetting />
      </el-tab-pane>
      <el-tab-pane
        label="分销员等级配置"
        name="second"
      >
        <distributorLevelSetting />
      </el-tab-pane>
      <el-tab-pane
        label="返利策略配置"
        name="third"
      >
        <returnPolicySetting />
      </el-tab-pane>
      <el-tab-pane
        label="分销员列表"
        name="fouth"
      >
        <distributorList />
      </el-tab-pane>
      <el-tab-pane
        label="分销员分组"
        name="fifth"
      >
        <distributorGroup />
      </el-tab-pane>
      <el-tab-pane
        label="佣金统计"
        name="sixth"
      >
        <moneyStatistics />
      </el-tab-pane>
      <el-tab-pane
        label="商品返利统计"
        name="seventh"
      >
        <goodsReturnStatistics />
      </el-tab-pane>
      <el-tab-pane
        label="返利体现审核"
        name="eighth"
      >
        <withdrawDepositCheck />
      </el-tab-pane>
      <el-tab-pane
        label="分销推广语"
        name=""
      >
        <advertisement />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import advertisement from './advertisement'
import distributeSetting from './distributeSetting'
import distributorGroup from './distributorGroup'
import distributorLevelSetting from './distributorLevelSetting'
import distributorList from './distributorList'
import goodsReturnStatistics from './goodsReturnStatistics'
import moneyStatistics from './moneyStatistics'
import returnPolicySetting from './returnPolicySetting'
import withdrawDepositCheck from './withdrawDepositCheck'

// import {
//   advertisement,
//   distributeSetting,
//   distributorGroup,
//   distributorLevelSetting,
//   distributorList,
//   goodsReturnStatistics,
//   moneyStatistics,
//   returnPolicySetting,
//   withdrawDepositCheck
// } from '@/view/admin/index/leftNavComponents/first_market_manage/distribution'

export default {
  components: {
    advertisement,
    distributeSetting,
    distributorGroup,
    distributorLevelSetting,
    distributorList,
    goodsReturnStatistics,
    moneyStatistics,
    returnPolicySetting,
    withdrawDepositCheck
  },
  data () {
    return {
      activeName: 'first'
    }
  },
  methods: {
    handleClick () {
      // console.log(tab)
    }
  }
}

</script>
<style scoped>
</style>
