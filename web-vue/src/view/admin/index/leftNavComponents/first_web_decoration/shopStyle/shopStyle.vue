<template>
  <div class="">
    <Crumbs :mTitle="M_title" />
    <ShopStyleContent />
  </div>

</template>
<script>
import Crumbs from '@/components/admin/crumbs'
import ShopStyleContent from '@/view/admin/index/leftNavComponents/first_web_decoration/shopStyle/shopStyleContent'
export default {
  components: { Crumbs, ShopStyleContent },
  data () {
    return {
      M_title: [
        '小程序管理', '店铺风格'
      ]
    }
  }
}
</script>
<style scoped>
</style>
