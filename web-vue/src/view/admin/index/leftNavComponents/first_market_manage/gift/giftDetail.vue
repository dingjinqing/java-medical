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
          <el-col :span="5">
            <el-form-item label="微信昵称">
              <el-input
                size="small"
                v-model="requestParams.username"
                placeholder="微信昵称"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="赠送时间">
              <el-date-picker
                type="datetime"
                placeholder="开始时间"
                v-model="requestParams.statrTime"
                size="small"
                value-format="yyyy-MM-dd HH:mm:ss"
                class="date_picker inputWidth"
              ></el-date-picker>
              至
              <el-date-picker
                type="datetime"
                placeholder="结束时间"
                v-model="requestParams.endTime"
                size="small"
                value-format="yyyy-MM-dd HH:mm:ss"
                class="date_picker inputWidth"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="2">
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
          prop="orderSn"
          label="订单号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="userId"
          label="用户ID"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="username"
          label="昵称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          label="手机号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="createTime"
          label="赠送时间"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="giftAmount"
          label="赠品件数"
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
import { getGiftGiftDetail } from '@/api/admin/marketManage/gift'
export default {
  components: {
    pagination
  },
  data () {
    return {
      pageParams: {},
      requestParams: {},
      tableData: [] // 表格数据
    }
  },
  mounted () {
    // 初始化数据
    this.initDataList()
  },
  methods: {
    // 明细列表
    initDataList () {
      this.loading = true
      this.requestParams.giftId = this.$route.params.id
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getGiftGiftDetail(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.dataList = res.content.dataList
          this.pageParams = res.content.page
          this.loading = false
        }
      })
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
