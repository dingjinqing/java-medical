<template>
  <div class="pointsExchangeOrder">
    <div class="main">
      <div class="mainBottom">
        <div class="mainTop">
          <div class="mainTopList">
            <div class="filters_item">
              <span>手机号：</span>
              <el-input
                size="small"
                v-model="mobileNum"
                placeholder="请填写用户手机号"
              ></el-input>
            </div>
            <div class="filters_item">
              <span>用户昵称：</span>
              <el-input
                size="small"
                v-model="userName"
                placeholder="请填写用户昵称"
              ></el-input>
            </div>
            <div class="filters_item">
              <span>邀请人：</span>
              <el-input
                size="small"
                v-model="visityUserName"
                placeholder="请填写邀请人昵称"
              ></el-input>
            </div>
            <div style="margin-left:20px">
              <el-button
                type="primary"
                size="small"
                @click="handleToSearch()"
              >筛选</el-button>
              <el-button
                type="info"
                size="small"
                plain
              >导出表格</el-button>
            </div>
          </div>
        </div>
        <div class="table_list">
          <el-table
            header-row-class-name="tableClss"
            :data="tableData"
            border
            style="width: 100%"
          >
            <el-table-column
              prop="activityName"
              label="活动名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="userId"
              label="新用户ID"
              align="center"
              width="200"
            ></el-table-column>
            <el-table-column
              label="新用户昵称"
              align="center"
              prop="userName"
              width="250"
            >
            </el-table-column>
            <el-table-column
              prop="mobile"
              label="新用户手机号"
              align="center"
            >
            </el-table-column>
            <el-table-column
              label="注册时间"
              align="center"
              prop="createTime"
            >
            </el-table-column>
            <el-table-column
              label="邀请人"
              align="center"
              prop="inviteUserName"
            >
            </el-table-column>
          </el-table>
          <div class="footer">
          </div>
          <pagination
            :page-params.sync="pageParams"
            @pagination="initDataList"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { integralNewUserList } from '@/api/admin/marketManage/integralExchange'
export default {
  components: {
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue'), // 省市区弹窗
    pagination: () => import('@/components/admin/pagination/pagination.vue') // 分页组件
  },
  data () {
    return {
      userName: '', // 用户昵称
      mobileNum: '', // 手机号
      visityUserName: '', // 邀请人姓名
      tableData: [

      ], // 列表表格数据
      pageParams: {} // 分页
    }
  },
  mounted () {
    console.log(this.$route)
    // 初始化请求数据
    this.initDataList()
  },
  methods: {
    // 初始化请求数据
    initDataList () {
      let params = {
        activityId: this.$route.query.activityId,
        mobile: this.mobileNum,
        userName: this.userName,
        inviteUserName: this.visityUserName,
        page: {
          'currentPage': this.pageParams.currentPage,
          'pageRows': this.pageParams.pageRows
        }
      }
      integralNewUserList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          console.log(this.$route.query)
          res.content.dataList.forEach((item, index) => {
            item.activityName = this.$route.query.activityName
          })
          this.tableData = res.content.dataList
        }
      })
    },
    // 点击筛选
    handleToSearch () {
      this.initDataList()
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.pointsExchangeOrder {
  padding: 10px;
  font-size: 14px;
  .main {
    .mainTop {
      background-color: #fff;
      padding: 20px 0 20px 0;
      .mainTopList {
        display: flex;
        .filters_item {
          display: flex;
          min-width: 275px;
          margin-bottom: 20px;
          max-width: 328px;
          /deep/ .el-input__inner {
            width: auto;
          }
          /deep/ .el-input {
            width: 188px;
          }
          span {
            white-space: nowrap;
            width: 90px;
            display: inline-block;
            height: 32px;
            line-height: 32px;
            text-align: right;
          }
        }
        .select {
          // padding-right: 43px;
          /deep/ .el-input__inner {
            width: 100%;
          }
        }
      }
      .bottom {
        display: flex;
        span {
          width: 140px;
          line-height: 32px;
          height: 32px;
          text-align: right;
        }
        /deep/ .el-select {
          margin-right: 5px;
        }
      }
    }
    .mainBottom {
      margin-top: 10px;
      background-color: #fff;
      padding: 10px;
      .opt {
        display: flex;
        .left {
          img {
            width: 48px;
            height: 48px;
            display: inline-block;
          }
        }
        .right {
          height: 50px;
          width: 150px;
          position: relative;
          margin-left: 12px;
          font-size: 12px;
          text-align: left;
          p {
            width: 150px;
            display: -webkit-box;
            text-overflow: ellipsis;
            overflow: hidden;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
            margin: 0;
          }
        }
      }
    }
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
}
</style>
