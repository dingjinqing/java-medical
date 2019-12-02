<template>
  <div class="main">
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  mounted () {
    console.log(this.$route)
  }
}
</script>
<style scoped>
.main {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #e6e9f0;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>
