<template>
  <div class="freight_container">
    <Crumbs :mTitle="M_title" />
    <div class="main_acontainer">
      <PictureSpaceTree />
    </div>
  </div>
</template>
<script>
import Crumbs from '@/components/admin/crumbs'
import PictureSpaceTree from '@/view/admin/index/leftNavComponents/first_web_decoration/PictureSpace/pictureSpaceTree'
export default {
  components: { Crumbs, PictureSpaceTree },
  data () {
    return {
      M_title: [
        '小程序管理', '图片空间'
      ]
    }
  }
}
</script>
<style scoped>
</style>
