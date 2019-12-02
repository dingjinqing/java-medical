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
    if (this.$route.meta.defaultTurn) {
      this.defaultFlag = this.$route.meta.defaultTurn
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      // 因为当钩子执行前，组件实例还没被创建
      // vm 就是当前组件的实例相当于上面的 this，所以在 next 方法里你就可以把 vm 当 this 来用了。
      // console.log(from)// 当前组件的实例
      // if (from.name === 'indexLogin') {
      //   vm.$destroy()
      // }
    })
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
