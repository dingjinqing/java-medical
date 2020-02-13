<template>
  <div class="userStatistics">
    <div class="userStatistics_content">
      <el-tabs v-model="userStatistics">
        <el-tab-pane
          label="商品概览"
          name="first"
        >
        </el-tab-pane>

        <el-tab-pane
          label="商品效果"
          name="second"
        >
          <goodsEffect />
        </el-tab-pane>
      </el-tabs>
    </div>
    <goodsOverview v-if="userStatistics === 'first'" />

  </div>
</template>

<script>
import goodsOverview from './goodsOverview'
import goodsEffect from './goodsEffect'
export default {
  components: { goodsOverview, goodsEffect },
  data () {
    return {
      userStatistics: 'first'
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
