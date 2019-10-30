<template>
  <div class="containter">
    <div class="title">
      <span>
        通知中心 / {{ title }}
        <img
          style="margin-left: 5px;"
          :src="image + '/image/admin/expand.png'"
          alt=""
        ></span>
    </div>
  </div>
</template>
<script>
// 引入组件
import { noticeDetailRequest } from '@/api/admin/survey.js'
export default {
  components: {
  },
  data () {
    return {
      image: 'http://mpdevimg2.weipubao.cn',
      title: this.$route.query.keyword,
      id: this.$route.query.id
    }
  },
  mounted () {
    // 初始化数据
    this.handleClick()
  },
  methods: {
    handleClick () {
      noticeDetailRequest({ articleId: this.id }).then((res) => {
        if (res.error === 0) {

        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.containter {
  width: 75%;
  height: auto;
  min-height: 520px;
  margin: 20px auto;
  padding: 0 100px 30px;
  box-shadow: 0 5px 16px rgba(0, 0, 0, 0.2);
  position: relative;
  background: #fff;
  .title {
    padding: 17px 0px;
    border-bottom: 1px solid #eee;
    font-size: 16px;
  }
}
</style>
