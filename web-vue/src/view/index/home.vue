<template>
  <div class="main">
    <Header />
    <router-view />
    <Footer />
    <Contact />
  </div>
</template>
<script>
import Header from '@/components/index/header'
import Footer from '@/components/index/footer'
import Contact from '@/components/index/contact'
export default {
  components: { Header, Footer, Contact },
  data () {
    return {
      index: 1
    }
  }
}
</script>
<style scoped>
.main {
  display: flex;
  flex-direction: column;
}
</style>
