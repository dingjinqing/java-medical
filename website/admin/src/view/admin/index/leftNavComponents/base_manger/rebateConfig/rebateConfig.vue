<template>
  <div class="base_manger">
    <div class="base_mangerMain">
      <el-tabs v-model="activeName">
        <el-tab-pane label="返利策略配置" name="first">
          <policySettings />
        </el-tab-pane>
        <el-tab-pane label="处方返利统计" name="second">
          <goodsRebateStatistical />
        </el-tab-pane>
        <el-tab-pane label="咨询订单返利统计" name="third">
          <consultRebateStatistical />
        </el-tab-pane>
        <el-tab-pane label="返利提现审核" name="fouth">
          <rebateCheck />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    policySettings: () => import('./policySettings'),
    goodsRebateStatistical: () => import('./goodsRebateStatistical'),
    consultRebateStatistical: () => import('./consultRebateStatistical'),
    rebateCheck: () => import('./rebateCheck')
  },
  data () {
    return {
      activeName: 'first',
      data: []
    }
  },
  mounted () {
  },
  methods: {
  }
}
</script>
<style scoped lang="scss">
.base_manger {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .base_mangerMain {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 0 20px;
  }
}
</style>
