<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
  >
    <el-tab-pane
      label="商家账号列表"
      name="first"
    >
      <list @send="send" />
    </el-tab-pane>
    <el-tab-pane
      label="商家账号添加"
      name="second"
    >
      <addAccount />
    </el-tab-pane>
    <el-tab-pane
      label="编辑商家账户"
      name="third"
      v-if="isShowEditAccount"
    >
      <editAccount />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import list from './list.vue'
import addAccount from './addAccount.vue'
import editAccount from './editAccount.vue'
export default {
  name: 'accountList',
  components: {
    list,
    addAccount,
    editAccount
  },
  data () {
    return {
      tabActive: 'first',
      isShowEditAccount: false
    }
  },
  methods: {
    send (val) {
      if (val === 'third') {
        this.isShowEditAccount = true
        this.tabActive = 'third'
      }
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
