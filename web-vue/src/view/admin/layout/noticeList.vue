<template>
  <div class="containter">
    <div class="title">
      <span>
        通知中心
        <img
          style="margin-left: 5px;"
          :src="image + '/image/admin/expand.png'"
          alt=""
        ></span>
    </div>
    <div class="content">
      <div
        class="listContent"
        v-for="(item, index) in noticeList"
        :key="index"
      >
        <div class="leftContent">
          <p>{{ item.title }}</p>
          <p>{{ item.desc }}</p>
        </div>
        <div class="rightContent">
          <p>2019-09-06 10:10:16</p>
          <el-button
            size="small"
            @click="detailHandler(item.articleId)"
          >查看详情</el-button>
        </div>
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="handleClick"
      />
      <div class="imgContent">
        <img
          :src="image + '/image/admin/ad_img.png'"
          alt=""
        >
      </div>
    </div>
  </div>
</template>
<script>
// 引入组件
import pagination from '@/components/admin/pagination/pagination'
import { noticeListRequest } from '@/api/admin/survey.js'
export default {
  components: {
    pagination
  },
  data () {
    return {
      image: 'http://mpdevimg2.weipubao.cn',
      pageParams: {}, // 分页
      noticeList: []
    }
  },
  mounted () {
    // 初始化数据
    this.handleClick()
  },
  methods: {
    handleClick () {
      let obj = {
        page: {
          currentPage: '1',
          pageRows: '20'
        }
      }
      noticeListRequest(obj).then((res) => {
        if (res.error === 0) {
          this.noticeList = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },

    // 详情
    detailHandler (id) {
      this.$router.push({ path: '/admin/home/shopMain', query: { id: id, change_components: '8' } })
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
  margin-top: 100px;
  .title {
    padding: 17px 0px;
    border-bottom: 1px solid #eee;
    font-size: 16px;
  }
  .content {
    width: 100%;
    height: auto;
    background-color: #fff;
    margin: 0 auto;
    .listContent {
      border-bottom: 1px solid #eee;
      width: 100%;
      height: 100px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .leftContent {
        width: 450px;
        height: 80px;
        line-height: 2;
        margin-left: 10px;
        p {
          font-size: 14px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        p:nth-child(2) {
          color: #999;
        }
      }
      .rightContent {
        width: 200px;
        height: 80px;
        line-height: 2;
        text-align: right;
        p {
          font-size: 14px;
        }
        .el-button {
          margin-right: 25px;
        }
      }
    }
    .imgContent {
      margin-top: 10px;
      text-align: center;
    }
  }
}
</style>
