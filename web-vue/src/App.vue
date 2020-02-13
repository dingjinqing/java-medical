<template>
  <div
    id="app"
    class="fillcontain"
    :style="isFillcontain?'position: absolute':''"
  >
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      isRouterAlive: true,
      isFillcontain: true
    }
  },
  provide () { // 提供
    return {
      reload: this.reload,
      changeIsFillcontain: this.changeIsFillcontain
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
    changeIsFillcontain () {
      this.isFillcontain = false
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
