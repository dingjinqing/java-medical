<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
  >
    <el-tab-pane
      name="first"
      :label="$t('shopAccountList.list')"
    >
      <list @send="send" />
    </el-tab-pane>
    <el-tab-pane
      name="second"
      :label="$t('shopAccountList.addAccount')"
    >
      <addAccount />
    </el-tab-pane>
    <el-tab-pane
      name="third"
      :label="$t('shopAccountList.editAccount')"
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

    // list: () => import('./list'),
    // addAccount: () => import('./addAccount'),
    // editAccount: () => import('./editAccount')
  },
  data () {
    return {
      tabActive: 'first',
      isShowEditAccount: false

      // tabActive: this.$route.params.page
    }
  },
  // watch: {
  //   $route: { // 带选项watch写法。兼容国际化刷新立即显示
  //     immediate: true,
  //     handler (to) {
  //       this.tabActive = to.params.page
  //       this.$store.commit('UPDATE_BREADCRUMB_TITLE', this.$t(`shopAccountList.${this.$route.params.page}`))
  //     }
  //   },
  //   tabActive (val) {
  //     this.$router.push({
  //       name: this.$route.name,
  //       params: {
  //         page: val
  //       }
  //     })
  //   }
  // },
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
