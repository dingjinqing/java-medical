<template>
  <div>
    <Crumbs
      :mTitle="M_title"
      :subtitle="Subtitle"
    />
  </div>
</template>
<script>
import Crumbs from '@/components/admin/crumbs'
export default {
  components: { Crumbs },
  data () {
    return {
      M_title: '小程序管理',
      Subtitle: '图片空间'
    }
  }
}
</script>
<style scoped>
</style>
