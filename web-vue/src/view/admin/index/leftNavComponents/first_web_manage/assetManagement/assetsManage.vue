<template>
  <div class="userStatistics">
    <div class="userStatistics_content">
      <el-tabs v-model="assetManage" @tab-click="handleClick">
        <el-tab-pane
          label="现金资产管理"
          name="first"
        >
          <cashAssets />
        </el-tab-pane>
        <el-tab-pane
          label="积分资产管理"
          name="second"
        >
          <scoreAssets />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import cashAssets from './cashAssets'
import scoreAssets from './scoreAssets'
export default {
  components: { cashAssets, scoreAssets },
  data () {
    return {
      assetManage: 'first'
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
