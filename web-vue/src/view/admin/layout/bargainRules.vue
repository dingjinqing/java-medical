<template>
  <!-- 小程序-砍价规则页面 -->
  <div class="bargainRules">
    <div v-html="bargainRules"></div>
  </div>
</template>

<script>
import { bargainRules } from '@/api/admin/util.js'
export default {
  data () {
    return {
      bargainRules: '' // 规则内容
    }
  },
  mounted () {
    // 初始化数据
    this.initData()
  },
  methods: {
    initData () {
      // 获取传参
      let obj = {
        shopId: this.$route.query.shop_id,
        id: this.$route.query.id
      }
      bargainRules(obj).then(res => {
        if (res.error === 0 && res.content !== null) {
          this.bargainRules = res.content
        }
      })
    }
  }
}
</script>

<style scoped>
.bargainRules {
  width: 100%;
  height: 100%;
  line-height: 3;
  padding: 0 10px;
  background: #fff;
}
.bargainRules p {
  color: #666;
  font-size: 14px;
  /* font-size: 0.75rem; */
}
</style>
