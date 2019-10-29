<template>
  <div class="content">

    <div class="main">
      <el-form label-width="100px">
        <el-row :gutter=24>
          <el-col :span="5">
            <el-form-item label="手机号">
              <el-input
                size="small"
                v-model="requestParams.mobile"
                placeholder="手机号"
                maxlength="11"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="5"
            style="margin-left: -20px"
          >
            <el-form-item label="用户昵称">
              <el-input
                size="small"
                v-model="requestParams.username"
                placeholder="用户昵称"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="4"
            style="margin-left: -20px"
          >
            <el-form-item label="邀请人">
              <el-input
                size="small"
                v-model="requestParams.inviteUserName"
                placeholder="邀请人"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="2"
            :offset="2"
          >
            <el-button
              size="small"
              type="primary"
              @click="initDataList"
              style="margin: 4px 0 0 0"
            >查询</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          label="活动名称"
          prop="name"
          align="center"
        ></el-table-column>
        <el-table-column
          label="新用户ID"
          prop="userId"
          align="center"
        ></el-table-column>
        <el-table-column
          label="新用户昵称"
          prop="userName"
          align="center"
        ></el-table-column>
        <el-table-column
          label="新用户手机号"
          prop="mobile"
          align="center"
        ></el-table-column>
        <el-table-column
          label="注册时间"
          prop="createTime"
          align="center"
        ></el-table-column>
        <el-table-column
          label="邀请人"
          prop="inviteUserName"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>

  </div>
</template>
<script>
// 引入组件
import pagination from '@/components/admin/pagination/pagination.vue'
import { detailSeckillList } from '@/api/admin/marketManage/seckill.js'
export default {

  components: {
    pagination
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {},
      tableData: []
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.initDataList()
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.$route.query.id
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      detailSeckillList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.forEach(item => {
        item.name = this.$route.query.name
      })
      this.tableData = data
    }

  }

}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    .wrapper {
      .el-button {
        margin-left: 5px;
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
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 10px 20px;
}
.inputWidth {
  width: 175px;
}
.el-form-item {
  margin-bottom: 1px;
}
.el-row {
  margin-bottom: 14px !important;
}
</style>
