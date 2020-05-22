<template>
  <div class="payContent">
    <div class="payContent_main">
      <el-tabs v-model="defaultBtn">
        <el-tab-pane
          :label="$t('payConfiguration.title')"
          name="first"
        >
          <payConfigure />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('tradeConfiguration.pagetitle')"
          name="second"
        >
          <tradeProcessConfigure />
        </el-tab-pane>
        <el-tab-pane
          :label="$t('returnconfiguration.title')"
          name="third"
        >
          <returnGoodsConfigure />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import payConfigure from './payConfigure'
import tradeProcessConfigure from './tradeProcessConfigure'
import returnGoodsConfigure from './returnGoodsConfigure'

export default {
  components: {
    payConfigure,
    tradeProcessConfigure,
    returnGoodsConfigure
  },
  data () {
    return {
      defaultBtn: 'first'
    }
  }
}

</script>
<style lang="scss" scoped>
.payContent {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .payContent_main {
    background-color: #fff;
    padding: 10px 20px;
  }
}
</style>
