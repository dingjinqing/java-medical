<template>
  <div>
    <Header />
    <div class="admin_contant">
      <LeftNavigation />
      <div class="rightContainer">
        <Crumbs />
        <router-view class="right_container" />
      </div>

    </div>
  </div>
</template>
<script>
import Crumbs from '@/components/admin/crumbs'
import Header from '@/view/admin/index/header'
import LeftNavigation from '@/view/admin/index/leftNavigation'
export default {
  components: { Header, LeftNavigation, Crumbs }
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
  background-color: #e6e9f0;
  min-width: 600px;
  flex: 1;
  overflow-y: auto;
}
.rightContainer {
  flex: 1;
  display: flex;
  flex-direction: column;
  /* overflow-y: auto; */
}
</style>
