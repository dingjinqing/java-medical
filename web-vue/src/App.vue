<template>
  <div
    id="app"
    class="fillcontain"
  >
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      isRouterAlive: true
    }
  },
  provide () { // 提供
    return {
      reload: this.reload
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    }
  }
}
</script>
<style lang="scss" src="./style/system/common.scss"></style> <!--引入common全局样式-->
<style src="./style/reset.css">
</style>
<style lang="scss" scoped>
.fillcontain {
  // position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgb(241, 247, 252);
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
