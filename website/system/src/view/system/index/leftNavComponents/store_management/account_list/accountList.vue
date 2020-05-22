<template>
  <el-tabs
    v-model="tabActive"
    type="border-card"
    class="tab"
    @tab-click="handleClick"
  >
    <el-tab-pane
      name="first"
      :label="$t('shopAccountList.title.list')"
    >
      <list
        v-if="firstShow"
        @send="send"
      />
    </el-tab-pane>
    <el-tab-pane
      name="second"
      :label="$t('shopAccountList.title.addAccount')"
    >
      <addAccount
        @send="send"
        v-if="secondShow"
      />
    </el-tab-pane>
    <el-tab-pane
      name="third"
      :label="$t('shopAccountList.title.editAccount')"
      v-if="isShowEditAccount"
    >
      <editAccount
        v-if="thirdShow"
        :sendData="sendData"
        @send="send"
      />
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
      isShowEditAccount: false,
      sendData: null,
      firstShow: true,
      secondShow: true,
      thirdShow: false
    }
  },
  created () {
    console.log(this.$route.params)
    if (this.$route.params.flag === true) {
      this.tabActive = 'third'
      this.isShowShopList = true
    }
  },
  methods: {
    send (data) {
      console.log('接收')
      console.log(data)
      if (data) {
        if (data.name === 'third') {
          this.isShowEditAccount = true
          this.tabActive = 'third'
          this.thirdShow = true
          this.firstShow = false
          this.sendData = data
          console.log('发送sendData')
          console.log(this.sendData)
        }
        if (data.name === 'secondOver') {
          this.firstShow = true
          this.tabActive = 'first'
          this.secondShow = false
          this.isShowEditAccount = false
        }
        if (data.name === 'thirdOver') {
          this.firstShow = true
          this.tabActive = 'first'
          this.thirdShow = false
          this.isShowEditAccount = false
        }
      }
    },
    handleClick (tab, event) {
      if (this.tabActive === 'first') {
        this.firstShow = true
        this.secondShow = false
        this.thirdShow = false
        this.isShowEditAccount = false
      } if (this.tabActive === 'second') {
        this.firstShow = false
        this.secondShow = true
        this.thirdShow = false
        this.isShowEditAccount = false
      }
    }
  },
  watch: {
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
