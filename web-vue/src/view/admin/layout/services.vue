<template>
  <div class="main-container">
  </div>
</template>

<script>
import { getMiniProgramServices } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  data () {
    return {
      shopId: null
    }
  },
  mounted () {
    console.log(this.$route.query.shop_id)
    this.shopId = this.$route.query.shop_id ? this.$route.query.shop_id : null
    if (this.shopId) this.requestServices()
  },
  methods: {
    requestServices () {
      getMiniProgramServices({shopId: this.shopId}).then(res => {
        console.log(res)
      })
    }
  }
}
</script>

<style>

</style>
