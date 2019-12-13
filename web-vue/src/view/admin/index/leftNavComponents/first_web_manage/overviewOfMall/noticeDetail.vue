<template>
  <div class="containter">
    <div class="title">
      <span>
        通知中心 / {{ detailData.title }}
        <img
          style="margin-left: 5px;"
          :src="image + '/image/admin/expand.png'"
          alt=""
        ></span>
    </div>
    <div class="content">
      <div class="titleContent">
        <p>{{ detailData.title }}</p>
        <p>{{ detailData.updateTime }}</p>
      </div>
      <div class="keyContent">
        <div>
          <span>关键字：</span>
          <span>{{ detailData.keyword }}</span>
        </div>
        <div>
          <span class="summary">摘要：</span>
          <span>{{ detailData.keyword }}</span>
        </div>
      </div>
      <div
        class="sectionContent"
        v-html="detailData.content"
      ></div>
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
      id: this.$route.query.id,
      detailData: {}
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
          this.detailData = res.content
          console.log('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!')
          console.log(this.detailData)
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
  .content {
    width: 80%;
    margin: 0 auto;
    text-align: center;
    min-height: 100px;

    .titleContent {
      margin: 15px 0;
      p:first-child {
        font-size: 18px;
        margin-bottom: 30px;
      }
      p:last-child {
        font-size: 14px;
        color: #666;
      }
    }
    .keyContent {
      width: 100%;
      border: 1px solid #dcdcdc;
      border-radius: 5px;
      background-color: #f5f5f5;
      padding-bottom: 10px;
      div {
        margin-left: 10px;
        margin-top: 10px;
        display: flex;
        span {
          color: #666;
          font-size: 14px;
        }
        .summary {
          text-indent: 1em;
        }
      }
    }
    .sectionContent {
      width: 100%;
      margin-top: 20px;
      min-height: 300px;
      background: #f1f1f1;
      text-align: left;
    }
  }
}
</style>
