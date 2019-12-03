<template>
  <div class="systemMain">
    <CustomHeader />
    <div class="system_contant">
      <LeftNavigation />
      <BreadCrumb />
      <!--面包屑导航-->
      <router-view />
    </div>
  </div>
</template>

<script>
import CustomHeader from './components/header'
import LeftNavigation from './components/leftNavigation'
import BreadCrumb from './components/breadcrumb'

export default {
  name: 'layout',
  components: {
    CustomHeader,
    LeftNavigation,
    BreadCrumb
  }
}
</script>

<style scoped lang="scss">
.systemMain {
  position: absolute;
  right: 0;
  left: 0;
  bottom: 0;
  top: 0;
  .system_contant {
    padding: 100px 10px 0 160px;
    background: #e0e3ec;
    min-height: 100vh;
  }
}
</style>
