<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
    @tab-click="handleClick"
  >
    <el-tab-pane
      name="first"
      label="版本列表"
      v-if="showFlag"
    >
      <versionListContent @showDtail="show" />
    </el-tab-pane>

    <el-tab-pane
      name="second"
      label="版本功能"
      v-if="showFlagTwo"
    >
      <versionListDetail
        :sendVersion="version"
        :isEdit="isEdit"
        :sendShopId="shopId"
      />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import versionListContent from './versionListContent.vue'
import versionListDetail from './versionListDetail.vue'

export default {
  name: 'pshopList',
  components: {
    versionListContent,
    versionListDetail
  },
  data () {
    return {
      showFlag: true,
      showFlagTwo: false,
      version: null,
      tabActive: 'first',
      isEdit: false,
      shopId: 0
    }
  },
  methods: {
    handleClick (tab, event) {
      if (tab.name === 'first') {
        this.tabActive = 'first'
        this.showFlag = true
        this.showFlagTwo = false
      }
      if (tab.name === 'second') {
        this.tabActive = 'second'
        this.showFlagTwo = true
        this.showFlag = false
      }
    },
    show (data) {
      this.showFlagTwo = data.showFlagTwo
      this.version = data.level
      this.isEdit = data.isEdit
      console.log('22222222222222222')
      console.log(this.isEdit)
      this.tabActive = 'second'
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
