<template>
  <div
    class="scoreterms"
    :style="navHeight?`patting-top:${navHeight}px`:''"
  >
    <p v-html="htmlData">小程序</p>
  </div>
</template>
<script>
import { scoreDocumentRequest } from '@/api/admin/util.js'
export default {
  data () {
    return {
      htmlData: '',
      navHeight: null
    }
  },
  inject: ['changeIsFillcontain'],
  mounted () {
    this.changeIsFillcontain()
    console.log(window.location.href)
    let part = window.location.href.split('?')[1].split('&')
    let navHeight = Number(part[0].split('=')[1])
    let shopId = Number(part[1].split('=')[1])
    let userId = Number(part[2].split('=')[1])
    let obj = {
      shop_id: shopId,
      user_id: userId
    }
    console.log(obj, navHeight)
    this.navHeight = navHeight / 2
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
.scoreterms .headline {
  font-weight: bold;
  margin: 20px 0;
}
</style>
