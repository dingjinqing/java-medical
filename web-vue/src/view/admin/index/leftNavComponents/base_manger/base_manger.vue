<template>
  <div class="">
    <!-- 店铺基础配置
    <router-link :to="{name: 'program_auth'}">
      跳转到小程序授权页面
    </router-link>
    <router-link :to="{name: 'program_auth_details'}">
      跳转到小程序授权成功页面
    </router-link> -->
  </div>
</template>
<script>
export default {
  data () {
    return {

    }
  },
  mounted () {
    // 初始化
    this.defaultData()
  },
  methods: {
    defaultData () {
      this.$router.push({
        name: 'program_auth'
      })
    }
  }
}
</script>
<style scoped>
</style>
