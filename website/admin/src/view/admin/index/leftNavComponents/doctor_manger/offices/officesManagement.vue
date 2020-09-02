<template>
  <div class="contentWrap">
    <div class="content">
      <!-- 科室管理入口 -->
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'officesManagement'
}
</script>

<style scoped>
.contentWrap {
  padding: 10px 10px;
}
.content {
  background-color: white;
  padding: 10px 10px 20px 10px;
}
</style>
