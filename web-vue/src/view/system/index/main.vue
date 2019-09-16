<template>
  <div>
    <CustomHeader />
    <div class="system_contant">
      <LeftNavigation />

      <router-view />
    </div>
  </div>
</template>

<script>
import CustomHeader from '@/view/system/index/header'
import LeftNavigation from '@/view/system/index/leftNavigation'
export default {
  components: { CustomHeader, LeftNavigation }
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
  left: 0;
}
</style>
