<template>
  <!-- 医师管理模块入口 -->
  <div class="contentWrap">
    <div class="content">
      <!-- 医师列表，新增、编辑医生 -->
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'doctorManagement'
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
