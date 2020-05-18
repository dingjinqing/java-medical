<template>
  <div class="memberValueAddedHome">
    <div class="memberValueAddedHomeMain">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="会员卡购买"
          name="first"
        >
          <renewalCard
            :showHeader="activeName"
            @getRenewalHeaderData="handleToGetHeader"
          />
        </el-tab-pane>
        <el-tab-pane
          label="会员卡续费"
          name="second"
        >
          <renewalCard
            :showHeader="activeName"
            @getRenewalHeaderData="handleToGetHeader"
          />
        </el-tab-pane>
        <el-tab-pane
          label="会员卡充值"
          name="third"
        >
          <renewalCard
            :showHeader="activeName"
            @getRenewalHeaderData="handleToGetHeader"
          />
        </el-tab-pane>
        <el-tab-pane
          label="优惠卷礼包购买"
          name="fourth"
        >
          <renewalCard :showHeader="activeName" />
        </el-tab-pane>
      </el-tabs>
      <el-button
        class="toCheck"
        size="small"
        type="primary"
      >查看交易数据</el-button>
    </div>
    <!--会员卡续费和会员卡充值底部表格-->
    <memberForm
      :activeName="activeName"
      :bottomFormData="bottomFormData"
      v-if="activeName==='second' || activeName==='third'"
    />
  </div>
</template>
<script>
export default {
  components: {
    renewalCard: () => import('./renewalCard'), // 会员卡续费和会员卡充值表头
    memberForm: () => import('./memberForm') // 会员卡续费和会员卡充值表格
  },
  data () {
    return {
      activeName: 'first',
      bottomFormData: {}
    }
  },
  methods: {
    // tap切换
    handleClick (tab, event) {
      console.log(tab, event)
    },
    // 表头点击筛选回传表头信息数据
    handleToGetHeader (res) {
      this.bottomFormData = res
      console.log(res, this.activeName)
    }
  }
}
</script>
<style lang="scss" scoped>
.memberValueAddedHome {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .memberValueAddedHomeMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .toCheck {
      position: absolute;
      top: 12px;
      right: 26px;
    }
  }
}
</style>
