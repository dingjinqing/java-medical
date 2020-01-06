<template>
  <div class="userStatistics">
    <div class="userStatistics_content">
      <el-tabs v-model="assetManage" @tab-click="handleClick">
        <el-tab-pane
          label="现金资产管理"
          name="first"
        >
        </el-tab-pane>
        <el-tab-pane
          label="积分资产管理"
          name="second"
        >
        </el-tab-pane>
      </el-tabs>
      <div>
        明细内容
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: { },
  created () {
    if (this.$route.params.flag === 0) {
      this.assetManage = 'first'
    } else if (this.$route.params.flag === 1) {
      this.assetManage = 'second'
    }
  },
  data () {
    return {
      assetManage: 'first',
      // 标示是积分还是现金
      flag: this.$route.params.flag
    }
  },
  methods: {
    handleClick (tab, event) {
      this.assetManage = tab.name
    }
  }
}

</script>
<style lang="scss" scoped>
  .userStatistics {
    padding: 10px;
    .userStatistics_content {
      background: #fff;
      padding: 15px 0 0 20px;
    }
  }
</style>
