<template>
  <div class="main">
    <CustomHeader />
    <router-view v-if="!defaultFlag" />
    <Main v-else />
    <Footer />
    <Contact />
  </div>
</template>
<script>
import CustomHeader from '@/view/index/header'
import Footer from '@/view/index/footer'
import Contact from '@/view/index/contact'
import Main from '@/view/index/main'
export default {
  components: { CustomHeader, Footer, Contact, Main },
  data () {
    return {
      defaultFlag: true
    }
  },
  watch: {
    '$route' (to) {
      console.log(to)
      let arr = Object.keys(to.meta)

      if (arr.length === 0) {
        this.defaultFlag = false
      } else {
        this.defaultFlag = true
      }
    }
  },
  mounted () {
    console.log(this.$route)
    let arr = [undefined, 'newsList', 'aboutUs', 'indexHomeOntrial']
    if (arr.indexOf(this.$route.name) !== -1) {
      this.defaultFlag = false
      this.$router.push({ path: this.$route.fullPath })
    }

    if (this.$route.meta.defaultTurn) {
      this.defaultFlag = this.$route.meta.defaultTurn
    }
    // 初始化判断打开的设备
    this.isMobile()
  },
  methods: {
    // 判断当前打开页面的设备
    isMobile () {
      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      console.log(flag)
      if (flag) {
        this.$router.push({ name: 'indexMobile' })
      }
    }
  }
}
</script>
<style scoped>
.main {
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
}
</style>
