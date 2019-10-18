<template>
  <div class="scoreterms">
    <p v-html="htmlData">小程序</p>
  </div>
</template>
<script>
import { scoreDocumentRequest } from '@/api/admin/util.js'
export default {
  data () {
    return {
      htmlData: ''
    }
  },
  mounted () {
    console.log(window.location.href)
    let part = window.location.href.split('?')[1].split('&')
    let shopId = Number(part[0].split('=')[1])
    let userId = Number(part[1].split('=')[1])
    let obj = {
      shop_id: shopId,
      user_id: userId
    }
    console.log(obj)
    scoreDocumentRequest(obj).then(res => {
      console.log(res)
      if (res.error === 0) {
        this.htmlData = res.content
      }
    })
  }
}
</script>
<style lang="scss" scoped>
.scoreterms {
  padding: 0 10px 20px;
}
</style>
<style >
.headline {
  font-weight: bold;
  margin: 20px 0;
}
</style>
