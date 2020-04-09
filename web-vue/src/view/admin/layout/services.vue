<template>
  <div
    class="main-container"
    v-html="data"
  >

  </div>
</template>

<script>
import { getMiniProgramServices } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  data () {
    return {
      shopId: null,
      data: null
    }
  },
  mounted () {
    console.log(this.$route.query.shop_id)
    this.shopId = this.$route.query.shop_id ? this.$route.query.shop_id : null
    console.log(this.shopId)
    if (this.shopId) this.requestServices()
  },
  methods: {
    requestServices () {
      getMiniProgramServices(this.shopId).then(res => {
        console.log(res)
      })
    }
  }
}
</script>

<style>
.main-container {
  height: 100%;
  background-color: #fff;
  padding: 0 10px;
}
</style>
