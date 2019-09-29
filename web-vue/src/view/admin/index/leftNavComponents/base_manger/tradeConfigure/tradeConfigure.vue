<template>
  <div class="payContent">
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
  margin: 10px;
  padding: 10px 20px 0 20px;
  min-width: 1000px;
  font-size: 14px;
  height: 100%;
  background: #fff;
}
</style>
