<template>
  <div>
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
.system_contant {
  padding: 100px 10px 0 160px;
  background: #e0e3ec;
  min-height: 100vh;
}
</style>
