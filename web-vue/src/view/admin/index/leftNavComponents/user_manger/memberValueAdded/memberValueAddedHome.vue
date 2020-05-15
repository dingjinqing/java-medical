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
          <renewalCard :showHeader="activeName" />
        </el-tab-pane>
        <el-tab-pane
          label="会员卡续费"
          name="second"
        >
          <renewalCard :showHeader="activeName" />
        </el-tab-pane>
        <el-tab-pane
          label="会员卡充值"
          name="third"
        >
          <renewalCard :showHeader="activeName" />
        </el-tab-pane>
        <el-tab-pane
          label="优惠卷礼包购买"
          name="fourth"
        >
          <renewalCard :showHeader="activeName" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    renewalCard: () => import('./renewalCard') // 会员卡续费
  },
  data () {
    return {
      activeName: 'first'
    }
  },
  methods: {
    // tap切换
    handleClick (tab, event) {
      console.log(tab, event)
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
  }
}
</style>
