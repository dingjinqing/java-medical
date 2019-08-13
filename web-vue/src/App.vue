<template>
  <div
    id="app"
    class="fillcontain"
  >
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>
<style lang="scss" src="./style/system/common.scss"></style> <!--引入common全局样式-->
<style src="./style/reset.css">
#app {
  /* height: 100%;
  position: absolute; */
  width: 100%;
}
</style>
