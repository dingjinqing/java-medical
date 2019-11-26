<template>
  <div class="container">
    <div class="main">
      <div class="top">
        <ul class="query-ul">
          <li>
            <span>领取时间：</span>
            <el-date-picker
              v-model="value1"
              type="daterange"
              size="small"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </li>
          <li>
            <span>手机号：</span>
            <el-input
              size="small"
              style="width: 170px;"
              v-model="queryParams.mobile"
              placeholder="请输入手机号"
            ></el-input>
          </li>
          <li>
            <span>用户昵称：</span>
            <el-input
              size="small"
              style="width: 170px;"
              v-model="queryParams.username"
              placeholder="请输入用户昵称"
            ></el-input>
          </li>
          <li>
            <el-button
              size="small"
              type="primary"
              @click="initDataList"
            >查询</el-button>
          </li>
        </ul>
      </div>
      <div class="center">
        <el-table
          :data="tableData"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'border':'none'
          }"
        >
          <el-table-column
            label="用户ID"
            prop="userId"
          ></el-table-column>
          <el-table-column
            label="用户昵称"
            prop="username"
          ></el-table-column>
          <el-table-column
            label="手机号"
            prop="mobile"
          ></el-table-column>
          <el-table-column
            label="活动奖励"
            prop="comment"
          ></el-table-column>
          <el-table-column
            label="领取时间"
            prop="receiveTime"
          ></el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        >
        </pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { detailOpenScreen } from '@/api/admin/marketManage/openScreen.js'
import('@/util/date')
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      value1: [], // 开始时间和结束时间
      queryParams: {
        startTime: '',
        endTime: '',
        mobile: '',
        username: ''
      },
      pageParams: {},
      tableData: []
    }
  },
  watch: {
    value1: function (newVal) {
      if (newVal && newVal.length === 2) {
        this.$set(this.queryParams, 'startTime', newVal[0].format('yyyy-MM-dd hh:mm:ss'))
        this.$set(this.queryParams, 'endTime', newVal[1].format('yyyy-MM-dd hh:mm:ss'))
      }
    }
  },
  mounted () {
    if (this.$route.query.id) {
      this.id = this.$route.query.id
      this.initDataList()
    } else {
      this.$alert('抱歉，未找到该活动', '提示', {
        confirmButtonText: '确定',
        callback: action => {
          this.$router.go(-1)
        }
      })
    }
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, {
        activityId: this.id
      }, this.queryParams, this.pageParams)
      detailOpenScreen(params).then(res => {
        if (res.error === 0) {
          this.tableData = [...res.content.dataList]
          this.pageParams = Object.assign({}, res.content.page)
        } else {
          this.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    .top {
      padding: 15px;
      background: #fff;
      overflow: hidden;
      &::after {
        clear: both;
      }
      .query-ul {
        display: flex;
        float: left;
        margin-right: 10px;
        li:not(:first-child) {
          margin-left: 10px;
        }
        span {
          display: inline-block;
          width: 80px;
        }
      }
    }
    .center {
      margin-top: 10px;
      padding: 15px;
      background: #fff;
    }
  }
}
</style>
