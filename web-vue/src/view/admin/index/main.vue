<template>
  <div>
    <Header />
    <LeftNavigation />
    <div class="admin_right_contant">
      <router-view />
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
.admin_right_contant {
  margin: 85px 0 0 150px;
}
</style>
