<template>
  <div>
    <Crumbs :mTitle="M_title" />
    <bottomNavigationContent />
  </div>
</template>
<script>
import Crumbs from '@/components/admin/crumbs'
import bottomNavigationContent from '@/view/admin/index/leftNavComponents/first_web_decoration/bottomNavigation/bottomNavigationContent'
export default {
  components: { Crumbs, bottomNavigationContent },
  data () {
    return {
      M_title: [
        '小程序管理', '底部导航配置'
      ]
    }
  }
}
</script>
<style scoped>
</style>
