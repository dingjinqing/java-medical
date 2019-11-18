<template>
  <div class="userStatistics">
    <div class="userStatistics_content">
      <el-tabs v-model="userStatistics">
        <el-tab-pane
          label="用户概况"
          name="first"
        >
          <userOverview />
        </el-tab-pane>
        <el-tab-pane
          label="RFM模型分析"
          name="second"
        >
          <RFMModelAnalysis />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import userOverview from './userOverview'
import RFMModelAnalysis from './RFMModelAnalysis'

export default {
  components: { userOverview, RFMModelAnalysis },
  data () {
    return {
      userStatistics: 'second'
    }
  }
}

</script>
<style lang="scss" scoped>
.userStatistics {
  padding: 10px;
  .userStatistics_content {
    background: #fff;
    padding-left: 10px;
  }
}
</style>
