<template>
  <div class="scoreterms">
    <div class='main-container'>
      <p class='title'>1.签到周期</p>
      <p class='content'>每日00:00:00至次日00:00:00为一个签到日</p>
      <p class='title rule'>2.积分获取规则</p>
      <ul class="example">
        <li
          class="lishow"
          v-for="(item,index) in htmlData"
          :key="index"
          :value="item"
        >
          连续签到{{ index+1}}天，得{{ item}}积分
        </li>
      </ul>
      <p class='title'>3.如果中断签到，连续签到记录也将清零</p>
      <p class='title'>4.连续签到超过N天，积分获取按照连续签到N天赠送</p>
    </div>
  </div>
</template>
<script>
import { signruleRequest } from '@/api/admin/util.js'
export default {
  data () {
    return {
      htmlData: []
    }
  },
  inject: ['changeIsFillcontain'],
  mounted () {
    this.changeIsFillcontain()
    console.log(window.location.href)
    let part = window.location.href.split('?')[1].split('&')
    let shopId = Number(part[0].split('=')[1])
    let userId = Number(part[1].split('=')[1])
    let obj = {
      shop_id: shopId,
      user_id: userId
    }
    console.log(obj)
    signruleRequest(obj).then(res => {
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
.scoreterms .headline {
  font-weight: bold;
  margin: 20px 0;
}
.main-container {
  padding: 0.2rem;
  line-height: 2;
}
.main-container p {
  margin-bottom: 0.2rem;
}
.title {
  padding: 0.2rem;
  color: #000;
  font-size: 0.75rem; /* 12/16 */
}
.content {
  color: #888;
  padding-left: 0.27rem;
  text-indent: 2em;
  font-size: 0.75rem; /* 12/16 */
}
.common-footer .footer-text {
  background: #fff;
}
.main-container .example {
  padding: 0.2rem;
  color: #888;
}
.lishow {
  padding: 0.2rem;
  text-indent: 3em;
  font-size: 0.75rem; /* 12/16 */
}
</style>
