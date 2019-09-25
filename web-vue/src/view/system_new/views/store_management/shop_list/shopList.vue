<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
  >
    <el-tab-pane
      :label="$t('shopList.title.experienceVersion')"
      name="first"
    >
      <experienceVersion @sendShopId="show" />
    </el-tab-pane>
    <el-tab-pane
      :label="$t('shopList.title.payVersion')"
      name="second"
    >
      <payVersion />
    </el-tab-pane>
    <el-tab-pane
      :label="$t('shopList.title.newShop')"
      name="third"
      v-if="isShowShopList"
      @getUserName="revice()"
    >
      <newShop />
    </el-tab-pane>

    <el-tab-pane
      :label="$t('shopList.title.editShop')"
      name="fourth"
      v-if="isEditShop"
    >
      <editShop
        :editParam="editParam"
        @goHome="show"
      />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import experienceVersion from './experienceVersion.vue'
import payVersion from './payVersion'
import newShop from './newShop'
import editShop from './editShop'

export default {
  name: 'shopList',
  components: {
    experienceVersion,
    payVersion,
    newShop,
    editShop
  },
  data () {
    return {
      tabActive: 'first',
      isShowShopList: false,
      isEditShop: false,
      editParam: null
    }
  },
  mounted () {
    console.log('this.$route.params')
    console.log(this.$route.params)
    if (this.$route.params.flag === true) {
      this.tabActive = 'third'
      this.isShowShopList = true
    }
  },
  methods: {
    getUserName () {
      console.log(this.$route.params.name)
      if (this.$route.params.name) {
        this.isShowEditAccount = true
        this.tabActive = 'third'
      }
    },
    show (data) {
      console.log('收到')
      console.log(data)
      if (data) {
        if (data.flag === 4) {
          this.tabActive = 'fourth'
          this.isEditShop = true
          this.editParam = data
        }
        if (data.flag === 1) {
          this.tabActive = 'first'
        }
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
    padding: 10px 0 0 0;
    background: #e0e3ec;
  }
  /deep/ .el-form-item__label {
    width: 140px;
  }
}
</style>
