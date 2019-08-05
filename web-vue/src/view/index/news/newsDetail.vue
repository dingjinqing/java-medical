<template>
  <div class="article_list_back">
    <div class="article_list_div">
      <div class="article_detail">
        <div class="article_detail_title">{{this.detailData.title}}</div>
        <div class="article_detail_time">{{this.detailData.updateTime}}</div>
        <p class="article_detail_content">{{this.detailData.content}}</p>

        <p style="text-align:center;">
          <img
            src="http://img.weipubao.cn/upload/0/image/20190326/bbYLTMyfHZSwHJIY.jpeg"
            alt=""
          >
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import { newsDetailRequest } from '@/api/index/news.js'
export default {
  data () {
    return {
      detailData: ''
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
    // 获取详情数据
    getData () {
      console.log(this.$route.query.articleId)
      let obj = {
        articleId: this.$route.query.articleId
      }
      newsDetailRequest(obj).then((res) => {
        console.log(res)
        // console.log(res.content.updateTime.split(' ')[0].split('-'))
        res.content.updateTime = res.content.updateTime.split(' ')[0].split('-')[0].split('0')[1] + '-' + res.content.updateTime.split(' ')[0].split('-')[1] + '-' + res.content.updateTime.split(' ')[0].split('-')[2]
        this.detailData = res.content
      })
    }
  }
}
</script>
<style scoped>
.article_list_back {
  padding: 60px 0;
  background: #f2f2f2;
}
.article_list_div {
  width: 1100px;
  margin: 0 auto;
  background: #fff;
  padding: 0 80px;
}
.article_detail {
  padding: 60px 10px;
}
.article_detail_title {
  text-align: center;
  font-size: 30px;
  color: #333;
}
.article_detail_time {
  text-align: center;
  font-size: 14px;
  color: #999;
  margin: 10px 0 30px;
}
.article_detail p {
  font-size: 16px;
  color: #666;
  margin-top: 30px;
}
</style>
