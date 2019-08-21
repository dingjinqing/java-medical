<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
  >
    <el-tab-pane
      name="first"
      :label="$t('shopAccountList.title.list')"
    >
      <list @send="send(arguments)" />
    </el-tab-pane>
    <el-tab-pane
      name="second"
      :label="$t('shopAccountList.title.addAccount')"
    >
      <addAccount />
    </el-tab-pane>
    <el-tab-pane
      name="third"
      :label="$t('shopAccountList.title.editAccount')"
      v-if="isShowEditAccount"
      @getInfo="revice()"
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
    }
  },
  created () {
    console.log(this.$.params)
    if (this.$route.params.flag === true) {
      this.tabActive = 'third'
      this.isShowShopList = true
    }
  },
  methods: {
    // send (val) {
    //   console.log(val)
    //   let receiveSysId = val[1]
    //   let receiveUserName = val[0]
    //   if (val[0] === 'third') {
    //     this.isShowEditAccount = true
    //     this.tabActive = 'third'
    //   }
    // }
    getInfo () {
      // console.log(111)
      console.log(this.$router)
      if (this.$route.params.name) {
        this.isShowEditAccount = true
        this.tabActive = 'third'
      }
    }
  },
  watch: {
    '$route': 'revice'
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
