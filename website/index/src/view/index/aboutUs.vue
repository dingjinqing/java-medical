<template>
  <div class="container">
    <AboutUs />
    <Development />
    <Environmental />
    <ContactUs />
  </div>
</template>
<script>
import AboutUs from '@/view/index/aboutUs/aboutUsIndex'
// import Development from '@/view/index/aboutUs/development'
// import Environmental from '@/view/index/aboutUs/environmental'
// import ContactUs from '@/view/index/aboutUs/contactUs'
export default {
  components: { AboutUs }
}
</script>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
}
</style>
