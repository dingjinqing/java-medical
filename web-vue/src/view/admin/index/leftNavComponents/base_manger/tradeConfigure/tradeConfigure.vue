<template>
  <div class="payContent">
    <el-tabs v-model="defaultBtn">
      <el-tab-pane
        label="支付配置"
        name="first"
      >
        <payConfigure />
      </el-tab-pane>
      <el-tab-pane
        label="交易流程相关配置"
        name="second"
      >
        <tradeProcessConfigure />
      </el-tab-pane>
      <el-tab-pane
        label="退换货配置"
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
import wrapper from '@/components/admin/wrapper/wrapper'

export default {
  components: {
    payConfigure,
    tradeProcessConfigure,
    returnGoodsConfigure,
    wrapper
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
