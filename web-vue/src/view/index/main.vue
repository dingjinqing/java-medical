<template>
  <div class="main_container">
    <Swiper />
    <Application />
    <Advantage />
    <Industry />
    <Entrance />
    <Case />
    <Information />
  </div>
</template>
<script>
import Swiper from '@/view/index/mainComponents/swiper'
import Application from '@/view/index/mainComponents/application'
import Advantage from '@/view/index/mainComponents/advantage'
import Industry from '@/view/index/mainComponents/industry'
import Entrance from '@/view/index/mainComponents/entrance'
import Case from '@/view/index/mainComponents/case'
import Information from '@/view/index/mainComponents/information'
export default {
  components: { Swiper, Application, Advantage, Industry, Entrance, Case, Information }
}

</script>
<style scoped>
.main_container {
  background-color: #fff;
}
</style>
