<template>
  <div>
    <el-tabs
      v-model="tabActive"
      type="border-card"
      class="tab"
      @tab-click="handleClick"
      v-if="!isShopRenenw"
    >
      <el-tab-pane
        :label="$t('shopList.title.experienceVersion')"
        name="first"
      >
        <experienceVersion
          v-if="firstShow"
          @sendShopId="show"
          :shopType="shopTypes"
        />
      </el-tab-pane>
      <el-tab-pane
        :label="$t('shopList.title.payVersion')"
        name="second"
      >
        <experienceVersion
          v-if="secondShow"
          @sendShopId="show"
          :shopType="shopTypes"
        />
        <!-- <payVersion /> -->
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

      <el-tab-pane
        label="版本权限"
        name="fifth"
        v-if="isShopVersion"
      >
        <versionListDetail
          :sendVersion="version"
          :isEdit="isEdit"
          :sendShopId="shopId"
          @goHome="show"
        />

      </el-tab-pane>
    </el-tabs>
    <shopRenenwList
      v-if="isShopRenenw"
      @sendShopId="show"
      :sendShowReData="sendShowRe"
    />
  </div>
</template>

<script>
import experienceVersion from './experienceVersion.vue'
import newShop from './newShop'
import editShop from './editShop'
import shopRenenwList from './shopRenenwList'
import versionListDetail from '../version_list/versionListDetail'
export default {
  name: 'shopList',
  components: {
    experienceVersion,
    newShop,
    editShop,
    shopRenenwList,
    versionListDetail
  },
  data () {
    return {
      tabActive: 'second',
      isShowShopList: false,
      isEditShop: false,
      editParam: null,
      shopTypes: '2',
      firstShow: false,
      secondShow: true,
      isShopRenenw: false,
      sendShowRe: null,
      isEdit: true,
      version: null,
      shopId: 0,
      isShopVersion: false

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
        if (data.flag === 5) {
          this.$store.commit('UPDATE_BREADCRUMB_TITLE', '店铺续费明细')
          this.isShopRenenw = true
          this.sendShowRe = data
        }
        if (data.flag === 6) {
          this.shopId = data.shopId
          this.version = data.shopType
          this.isShopVersion = true
          this.tabActive = 'fifth'
        } if (data.flag === 16) {
          this.tabActive = 'first'
          this.isShopVersion = false
        }
      }
    },
    handleClick (tab, event) {
      if (this.tabActive === 'first') {
        this.shopTypes = '1'
        this.firstShow = true
        this.secondShow = false
        this.isShowShopList = false
        this.isShopVersion = false
      } if (this.tabActive === 'second') {
        this.shopTypes = '2'
        this.firstShow = false
        this.secondShow = true
        this.isShowShopList = false
        this.isShopVersion = false
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
