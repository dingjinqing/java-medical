<template>
  <div class="payContent">
    <el-tabs
      v-model="defaultBtn"
      @tab-click="handleClick"
    >
      <!--店铺子账户管理 -->
      <el-tab-pane
        :label="$t('authRoleList.authlabel1')"
        name="first"
      >
        <childConfig
          v-if="showFlag"
          @showAuthConfig="show"
        />
      </el-tab-pane>
      <!--权限组管理 -->
      <el-tab-pane
        :label="$t('authRoleList.authlabel2')"
        name="second"
      >
        <authorityConfigure
          v-if="!showFlag"
          @showAuthConfig="show"
        />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import childConfig from './childConfig'
import authorityConfigure from './authorityConfigure'

export default {
  components: {
    childConfig,
    authorityConfigure
  },
  data () {
    return {
      showFlag: true,
      defaultBtn: 'first'
    }
  },
  methods: {
    show (data) {
      console.log('收到')
      console.log(data)
      if (data) {
        if (data.flag === 2) {
          this.defaultBtn = 'second'
          this.showFlag = false
        }
        if (data.flag === 1) {
          this.defaultBtn = 'first'
          this.showFlag = true
        }
      }
    },
    handleClick (tab, event) {
      if (tab.name === 'first') {
        this.defaultBtn = 'first'
        this.showFlag = true
      }
      if (tab.name === 'second') {
        this.defaultBtn = 'second'
        this.showFlag = false
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.payContent {
  margin: 10px;
  padding: 10px 20px 0 20px;
  min-width: 1000px;
  font-size: 14px;
  height: 100%;
  background: #fff;
}
</style>
