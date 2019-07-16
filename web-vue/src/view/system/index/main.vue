<template>
  <div>
    <Header />
    <div class="system_contant">
      <LeftNavigation />

      <router-view />
    </div>
  </div>
</template>

<script>
import Header from '@/view/system/index/header'
import LeftNavigation from '@/view/system/index/leftNavigation'
export default {
  components: { Header, LeftNavigation }
}
</script>

<style scoped>
.system_contant {
  width: 100%;
  display: flex;
  position: absolute;
  overflow: hidden;
  bottom: 0;
  top: 85px;
}
</style>
