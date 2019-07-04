<template>
  <div class="main">
    <Header />
    <SelectShop />
    <Contact />
  </div>
</template>
<script>
import Header from '@/components/admin/header'
import SelectShop from '@/components/admin/selectShop'
import Contact from '@/components/admin/contact'
export default {
  components: { Header, SelectShop, Contact }
}
</script>
<style scoped>
.main {
  display: flex;
  flex-direction: column;
  background-color: #e6e9f0;
}
</style>
