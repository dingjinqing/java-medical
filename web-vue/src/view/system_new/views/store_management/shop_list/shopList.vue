<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
  >
    <el-tab-pane
      label="体验版"
      name="first"
    >
      <experienceVersion />
    </el-tab-pane>
    <el-tab-pane
      label="付费版"
      name="second"
    >
      <payVersion />
    </el-tab-pane>
    <el-tab-pane
      label="店铺添加"
      name="third"
      v-if="isShowShopList"
      @getUserName="revice()"
    >
      <newShop />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import experienceVersion from './experienceVersion.vue'
import payVersion from './payVersion'
import newShop from './newShop'

export default {
  name: 'shopList',
  components: {
    experienceVersion,
    payVersion,
    newShop
  },
  data () {
    return {
      tabActive: 'first',
      isShowShopList: false
    }
  },
  created () {
    console.log(this.$route.params.name)
    if (this.$route.params.flag === true) {
      this.tabActive = 'third'
      this.isShowShopList = true
    }
  },
  // beforeRouteEnter: (to, from, next) => {
  //   // if (this.$route.path === `/system/store_management/account_list`) {
  //   next(vm => {
  //     alert(vm.name)
  //   })
  //   // }
  // },

  // mounted () {
  //   console.log(this.$route.params)
  //   if (this.$route.params.flag === true) {
  //     this.tabActive = 'third'
  //     this.isShowShopList = true
  //   }
  // },
  // created () {
  //   console.log(this.$route.params.name)
  //   if (this.$route.params.flag === true) {
  //     console.log(111)
  //     this.tabActive = 'third'
  //     this.isShowEditAccount = true
  //   }
  // },
  methods: {
    getUserName () {
      console.log(this.$route.params.name)
      if (this.$route.params.name) {
        this.isShowEditAccount = true
        this.tabActive = 'third'
      }
      // bus.$on('revice', data => {
      //   console.log(data)
      // })
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
    padding: 10px 0 0 0;
    background: #e0e3ec;
  }
  /deep/ .el-form-item__label {
    width: 140px;
  }
}
</style>
