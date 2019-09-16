<template>
  <div class="s_container">
    <CustomHeader @change_components="handleChange" />
    <SelectShop v-if="flag4" />
    <Contact v-if="flag" />
    <AccountSettings
      v-if="flag0"
      @change_components="handleChange"
    />
    <ServiceAuthList v-if="flag3" />
  </div>
</template>
<script>
import CustomHeader from './header'
import SelectShop from './selectShop'
import Contact from './contact'
import AccountSettings from './accountSettings'
import ServiceAuthList from './serviceAuthList'
export default {
  components: { CustomHeader, SelectShop, Contact, AccountSettings, ServiceAuthList },
  data () {
    return {
      flag: false,
      flag0: false,
      flag1: false,
      flag2: false,
      flag3: false,
      flag4: false
    }
  },
  mounted () {
    console.log(this.$route)
    // 初始化组建切换
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
    },
    // 切换组件
    handleChange (index) {
      // console.log(index)
      this.defaluteData()
      switch (index) {
        case '0': this.flag0 = true
          break
        case '1': this.flag1 = true
          break
        case '2': this.flag2 = true
          break
        case '3': this.flag3 = true
          break
        case '4': this.flag4 = true
          this.flag = true
          break
        default:
          this.flag4 = true
          this.flag = true
          break
      }
    },
    changeComponents () {
      if (this.$route.query) {
        let query = this.$route.query.change_components
        this.defaluteData()
        switch (query) {
          case '0': this.flag0 = true
            break
          case '1': this.flag1 = true
            break
          case '2': this.flag2 = true
            break
          case '3': this.flag3 = true
            break
          case '4': this.flag4 = true
            this.flag = true
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
