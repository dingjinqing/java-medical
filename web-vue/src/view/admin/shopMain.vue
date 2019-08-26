<template>
  <div class="s_container">
    <Header @change_components="handleChange" />
    <SelectShop v-if="flag" />
    <Contact v-if="flag" />
    <AccountSettings
      v-if="!flag"
      @change_components="handleChange"
    />
  </div>
</template>
<script>
import Header from '@/view/admin/header'
import SelectShop from '@/view/admin/selectShop'
import Contact from '@/view/admin/contact'
import AccountSettings from '@/view/admin/accountSettings'
export default {
  components: { Header, SelectShop, Contact, AccountSettings },
  data () {
    return {
      flag: true
    }
  },
  mounted () {
    console.log(this.$route)
    // 初始化组建切换
    this.changeComponents()
  },
  methods: {
    // 切换组件
    handleChange (index) {
      // console.log(index)
      switch (index) {
        case '0': this.flag = false
          break
        case '3': this.flag = true
          break
      }
    },
    changeComponents () {
      if (this.$route.query) {
        let query = this.$route.query.change_components
        switch (query) {
          case '0': this.flag = false
            break
          case '3': this.flag = true
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
