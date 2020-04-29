<template>
  <!-- 小程序-拼团规则页面 -->
  <div class="group-buy-page">
    <div v-if="myRules">
      <div v-html="myRules"></div>
    </div>
    <div v-else>
      <h2>如何才算拼团成功？</h2>
      <p>每个团的有效期为24小时，有效期内找到满足人数的小伙伴参与拼团，拼团即会成功。</p>
      <h2>拼团失败怎么办？</h2>
      <p>若24小时内未凑够拼团人数，则拼团失败，失败后系统会自动将所有支付的货款原路返回，具体到账时间按照各支付渠道为准。</p>
      <h2>拼团流程</h2>
      <div>
        <img
          class="group-buy-img"
          :src="$imageHost + '/image/wxapp/icon_group4.png'"
          alt="拼团流程"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { pinRulesApi } from '@/api/admin/util.js'
export default {
  data () {
    return {
      myRules: ''
    }
  },
  mounted () {
    document.title = '拼团规则'
    this.initGroupRules()
  },
  methods: {
    initGroupRules () {
      let param = {
        id: this.$route.query.gid,
        shopId: this.$route.query.shop_id
      }
      pinRulesApi(param).then(res => {
        console.log(res)
        if (res.error === 0) {
          let content = JSON.parse(res.content)
          console.log(content)
          this.myRules = content.document
          this.isUseDefault = content.is_use_default
        }
      })
    }
  }
}
</script>

<style>
strong {
  font-weight: bold;
}
</style>
<style scoped>
.group-buy-page {
  height: 100%;
  line-height: 1.4;
  padding: 0.2rem 0.27rem;
  background: #fff;
  word-break: break-all;
}
strong {
  font-weight: bold;
}
.group-buy-page h2 {
  color: #000;
  font-size: 0.29rem;
  font-weight: bold;
  margin-top: 0.2rem;
  margin-bottom: 0.13rem;
}
.group-buy-page p {
  color: #888;
  font-size: 0.29rem;
}
.group-buy-img {
  width: 100%;
}
</style>
