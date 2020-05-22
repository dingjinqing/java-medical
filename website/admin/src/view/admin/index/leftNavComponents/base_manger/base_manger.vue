<template>
  <div class="base_manger">
    <div
      class="base_mangerMain"
      v-if="flag"
    >
      <el-tabs v-model="activeName">
        <el-tab-pane
          :label="$t('ShopConfiguration.ShopBasicInformation')"
          name="first"
        >
          <storeBasicInfo></storeBasicInfo>
        </el-tab-pane>
        <el-tab-pane
          :label="$t('ShopConfiguration.GeneralStoreSettings')"
          name="second"
        >
          <storeCommonSettings></storeCommonSettings>
        </el-tab-pane>
        <el-tab-pane
          :label="$t('ShopConfiguration.SmallProgramAuthorization')"
          name="third"
        >
          <programAuthDetails />
        </el-tab-pane>
      </el-tabs>
    </div>
    <programAuth v-else />
  </div>
</template>
<script>
import { queryAuthdritionRequest } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  components: {
    storeBasicInfo: () => import('@/view/admin/index/leftNavComponents/base_manger/store_basic_information'),
    storeCommonSettings: () => import('@/view/admin/index/leftNavComponents/base_manger/store_common_settings'),
    programAuthDetails: () => import('@/view/admin/index/leftNavComponents/base_manger/program_auth_details'),
    programAuth: () => import('@/view/admin/index/leftNavComponents/base_manger/program_auth')
  },
  data () {
    return {
      activeName: 'first',
      flag: true,
      data: []
    }
  },
  mounted () {
    this.langDefault()
    // 初始化
    this.defaultData()
  },
  methods: {
    defaultData () {
      // console.log(this.$route.params.otherTurn, 111)
      if (this.$route.params.isAuth) {
        this.activeName = 'first'
      }
      queryAuthdritionRequest().then(res => {
        if (res.error === 170016) {
          this.flag = false
        } else {
          this.flag = true
          if (res.error === 0) {
            if (window.location.href.split('/').pop() === 'authok') {
              console.log(11111)
              this.activeName = 'third'
            }
            console.log(2222)
            this.$http.$emit('handleToAuthData', res.content)
          }
          console.log(res)
        }
      })
    }
  }
}
</script>
<style scoped lang="scss">
.base_manger {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .base_mangerMain {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 0 20px;
  }
}
</style>
