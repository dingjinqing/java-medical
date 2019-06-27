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
import Swiper from '@/components/index/mainComponents/swiper'
import Application from '@/components/index/mainComponents/application'
import Advantage from '@/components/index/mainComponents/advantage'
import Industry from '@/components/index/mainComponents/industry'
import Entrance from '@/components/index/mainComponents/entrance'
import Case from '@/components/index/mainComponents/case'
import Information from '@/components/index/mainComponents/information'
export default {
  components: { Swiper, Application, Advantage, Industry, Entrance, Case, Information }
}
</script>
<style scoped>
.main_container {
  background-color: #fff;
}
</style>
