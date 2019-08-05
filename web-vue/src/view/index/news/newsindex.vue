<template>
  <div class="news_container">
    <div class="news_list">
      <div
        class="article_list"
        v-for="(item,index) in article_list_show"
        :key="index"
        @click="to_detail(item.articleId)"
      >
        <div class="article_container">
          <div class="article_left">
            <img :src="item.imgUrl">
          </div>
          <div class="article_right">
            <div class="acticle_title">{{item.title}}</div>
            <div class="acticle_content">{{item.content}}</div>
            <div class="acticle_time">{{item.updateTime}}</div>
          </div>
        </div>
      </div>

      <div class="pagination">
        <el-pagination
          background
          layout="prev, pager, next"
          :page-size="pagesize"
          :total="totalNum"
          :current-page.sync="currentPage"
          @current-change='currentChange'
          @prev-click="prevClick"
          @next-click='nextClick'
        >
        </el-pagination>
      </div>
    </div>

  </div>
</template>
<script>
import { articleRequest } from '@/api/index/news.js'
export default {
  data () {
    return {
      article_list_show: [],
      currentPage: 1,
      totalNum: 10,
      pagesize: 5
    }
  },
  mounted () {
    this.getData(2)
    document.documentElement.scrollTop = 0
  },
  methods: {
    // 页码改变
    currentChange () {
      this.getData(this.currentPage)
    },
    // 获取数据
    getData (pageSize) {
      let obj = {
        'categoryId': '4',
        'status': '1',
        'keywords': '',
        'sortName': 'is_recommend,desc;update_time,asc',
        'page': {
          'currentPage': pageSize,
          'pageRows': '5'
        }
      }
      articleRequest(obj).then((res) => {
        console.log(res.content.page.totalRows)
        this.totalNum = res.content.page.totalRows
        console.log(this.totalNum)
        this.handleDataList(res.content.dataList)
      })
    },
    // 处理首页新闻列表数据
    handleDataList (res) {
      var obj = []

      for (let i = 0; i < res.length; i++) {
        obj.push(res[i])
      }

      for (let i = 0; i < obj.length; i++) {
        let string = ''
        string = obj[i].updateTime.split(' ')[0].split('-')[1] + '/' + obj[i].updateTime.split(' ')[0].split('-')[2]
        console.log(string)
        obj[i].updateTime = string
      }

      this.article_list_show = obj
    },
    // 上一页
    prevClick () {
      console.log(this.currentPage - 1)
      this.getData(this.currentPage - 1)
    },
    // 下一页
    nextClick () {
      console.log(this.currentPage + 1)
      this.getData(this.currentPage + 1)
    },
    // 跳转到详情页
    to_detail (articleId) {
      console.log(articleId)
      this.$router.push({
        name: 'newsDetail',
        query: {
          articleId: articleId
        }
      })
    }
  }
}
</script>
<style scoped>
.news_container {
  padding: 60px 0;
  background: #f2f2f2;
}
.news_list {
  width: 1100px;
  margin: 0 auto;
  background: #fff;
  padding: 0 80px;
}
.article_list {
  padding: 40px 0;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}
.article_container {
  overflow: hidden;
}
.article_left {
  float: left;
  width: 290px;
  height: 220px;
}
.article_right {
  float: right;
  width: 600px;
  height: 220px;
  position: relative;
}
.article_left img {
  display: block;
  width: 100%;
}
.acticle_title {
  color: rgb(51, 51, 51);
  font-size: 20px;
}
.acticle_content {
  margin-top: 20px;
  font-size: 16px;
  color: #666;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
.acticle_time {
  position: absolute;
  bottom: 0;
  font-size: 14px;
  color: #999;
}
</style>
<style>
.news_container .el-pagination {
  padding: 20px 0 20px 570px !important;
}
</style>
