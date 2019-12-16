<template>
  <div class="s_container">
    <Header />
    <SelectShop v-if="flag4" />
    <Contact v-if="flag" />
    <AccountSettings v-if="flag0" />
    <ServiceAuthList v-if="flag3" />
    <ServiceAuthListDetail v-if="flag6" />
    <serviceSubUserList v-if="flag1" />
    <noticeList v-if="flag7" />
    <noticeDetail v-if="flag8" />
  </div>
</template>
<script>
// import Header from './header'
// import SelectShop from './selectShop'
// import Contact from './contact'
// import AccountSettings from './accountSettings'
// import ServiceAuthList from './serviceAuthList'
// import ServiceAuthListDetail from './serviceAuthListDetail'
export default {
  components: {
    Header: () => import('./header'),
    SelectShop: () => import('./selectShop'),
    Contact: () => import('./contact'),
    AccountSettings: () => import('./accountSettings'),
    ServiceAuthList: () => import('./serviceAuthList'),
    ServiceAuthListDetail: () => import('./serviceAuthListDetail'),
    serviceSubUserList: () => import('./serviceSubUserList'),
    noticeList: () => import('./noticeList'),
    noticeDetail: () => import('./noticeDetail')
  },

  data () {
    return {
      flag: false,
      flag0: false,
      flag1: false,
      flag2: false,
      flag3: false,
      flag4: false,
      flag6: false,
      flag7: false,
      flag8: false
    }
  },
  watch: {
    '$route' (newData) {
      console.log(newData)
      this.changeComponents()
    }

  },
  mounted () {
    console.log(this.$route)
    // 初始化组建切换
    this.$http.$on('changeHeaderComponents', res => {
      console.log('触发了')
      this.changeComponents()
    })
    // 初始化数据
    this.defaluteData()
    this.changeComponents()
  },
  methods: {
    defaluteData () {
      this.flag = false
      this.flag0 = false
      this.flag1 = false
      this.flag2 = false
      this.flag3 = false
      this.flag4 = false
      this.flag6 = false
      this.flag7 = false
      this.flag8 = false
    },
    changeComponents () {
      this.defaluteData()
      console.log(this.$route.query)
      if (this.$route.query) {
        let query = this.$route.query.change_components
        switch (query) {
          case '0': this.flag0 = true
            break
          case '1': this.flag1 = true
            break
          case '2':
            this.$router.push({
              name: 'child_config'
            })
            break
          case '3': this.flag3 = true
            break
          case '4': this.flag4 = true
            this.flag = true
            break
          case '6':
            this.flag6 = true
            break
          case '7':
            this.flag7 = true
            break
          case '8':
            this.flag8 = true
            console.log('传的id')
            console.log(this.$route.query.id)
            break
          default:
            this.flag4 = true
            this.flag = true
            break
        }
      }
    }
  }
}
</script>
<style scoped>
.s_container {
  height: 100%;
}
</style>
