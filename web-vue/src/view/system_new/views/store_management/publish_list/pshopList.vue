<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
  >
    <el-tab-pane
      name="first"
      label="店铺发布列表"
    >
      <shopContent />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import shopContent from './shopContent.vue'

export default {
  name: 'pshopList',
  components: {
    shopContent
  },
  data () {
    return {
      tabActive: 'first'
    }
  }
}
</script>

<style scoped lang="scss">
.tab {
  margin-top: 18px;
  /deep/ .el-tabs__header .el-tabs__item.is-active {
    border-top: 3px solid #57889c;
    color: #333;
    font-size: 14px;
    font-weight: 700;
  }
  /deep/ .el-tabs__item {
    height: 46px;
    padding: 0 10px 3px 10px !important;
    border-top: 2px solid transparent;
    font-weight: 400;
  }
  /deep/ .el-tabs__content {
    padding: 10px 0px 0px 0px;
    background: #e0e3ec;
  }
}
</style>
