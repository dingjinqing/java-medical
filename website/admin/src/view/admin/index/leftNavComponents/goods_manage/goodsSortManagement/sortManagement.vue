<template>
  <!-- 分类模块入口 -->
  <div class="contentWrap">
    <div class="content">
      <!-- 商品分类，推荐分类，添加分类，添加推荐分类入口 -->
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'sortManagement'
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
