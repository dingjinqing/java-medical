<template>
  <div>
    <Header />
    <div class="admin_contant">
      <LeftNavigation />
      <router-view class="right_container" />
    </div>
  </div>
</template>
<script>
import Header from '@/view/admin/index/header'
import LeftNavigation from '@/view/admin/index/leftNavigation'
export default {
  components: { Header, LeftNavigation }
}
</script>
<style scoped>
.admin_contant {
  width: 100%;
  display: flex;
  position: absolute;
  overflow: hidden;
  bottom: 0;
  top: 85px;
}
.right_container {
  flex: 1;
  background-color: #e6e9f0;
  min-width: 600px;
}
</style>
