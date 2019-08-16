<template>
  <div class="base_manger">
    <div
      class="base_mangerMain"
      v-if="flag"
    >
      <el-tabs v-model="activeName">
        <el-tab-pane
          label="店铺基础信息"
          name="first"
        >店铺基础信息</el-tab-pane>
        <el-tab-pane
          label="店铺通用设置"
          name="second"
        >店铺通用设置</el-tab-pane>
        <el-tab-pane
          label="小程序授权"
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
    // 初始化
    this.defaultData()
    console.log(window.location.href)
  },
  methods: {
    defaultData () {
      queryAuthdritionRequest().then(res => {
        if (res.error === 170016) {
          this.flag = false
        } else {
          this.flag = true
          if (res.error === 0) {
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
