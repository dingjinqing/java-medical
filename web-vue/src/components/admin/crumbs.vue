<template>
  <div class="container">
    <span>{{mTitle}} /</span>
    <span style="color:#666">{{subtitle}}</span>
  </div>
</template>
<script>
export default {
  props: ['mTitle', 'subtitle']
}
</script>
<style scoped>
.container {
  height: 55px;
  line-height: 55px;
  padding-left: 25px;
  color: #333;
  background: #fff;
}
</style>
