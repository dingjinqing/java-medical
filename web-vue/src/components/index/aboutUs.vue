<template>
  <div class="container">
    <AboutUs />
    <Development />
    <Environmental />
    <ContactUs />
  </div>
</template>
<script>
import AboutUs from '@/components/index/aboutUs/aboutUsIndex'
import Development from '@/components/index/aboutUs/development'
import Environmental from '@/components/index/aboutUs/environmental'
import ContactUs from '@/components/index/aboutUs/contactUs'
export default {
  components: { AboutUs, Development, Environmental, ContactUs }
}
</script>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
}
</style>
