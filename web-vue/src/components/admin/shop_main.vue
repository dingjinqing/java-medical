<template>
  <div class="s_container">
    <Header @change_components="handleChange" />
    <SelectShop v-if="flag" />
    <Contact v-if="flag" />
    <AccountSettings v-if="!flag" />
  </div>
</template>
<script>
import Header from '@/components/admin/header'
import SelectShop from '@/components/admin/selectShop'
import Contact from '@/components/admin/contact'
import AccountSettings from '@/components/admin/accountSettings'
export default {
  components: { Header, SelectShop, Contact, AccountSettings },
  data () {
    return {
      flag: true
    }
  },
  methods: {
    // 切换组件
    handleChange (index) {
      console.log(index)
      switch (index) {
        case '0': this.flag = false
          break
        case '3': this.flag = true
          break
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
