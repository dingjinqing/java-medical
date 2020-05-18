<template>
  <div class="memberForm">
    <el-table
      ref="tableData1"
      :data="tableData1"
      border
      highlight-current-row
      @current-change="handleCurrentChange"
      height="300px"
      style="width: 100%;"
      header-row-class-name="tableClss"
      v-if="activeName==='second'"
    >
      <el-table-column
        prop="pageName"
        label="续费单号"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="createTime"
        label="会员卡"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="卡ID"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="会员昵称"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="手机号"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="续费时间"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="续费金额"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="续费时长"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="当次续费后有效期"
        align="center"
        width="160"
      ></el-table-column>
    </el-table>
    <el-table
      ref="tableData1"
      :data="tableData1"
      border
      highlight-current-row
      @current-change="handleCurrentChange"
      height="300px"
      style="width: 100%;"
      header-row-class-name="tableClss"
      v-if="activeName==='third'"
    >

      <el-table-column
        prop="pageName"
        label="充值单号"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="createTime"
        label="会员卡"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="卡ID"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="会员昵称"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="手机号"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="typeText"
        label="充值时间"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="typeText"
        label="充值金额(元)"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="typeText"
        label="当次充值后卡余额(元)"
        align="center"
        width="160"
      ></el-table-column>
      <el-table-column
        prop="type"
        label="备注"
        align="center"
        width="160"
      ></el-table-column>
    </el-table>
    <Pagination
      :page-params.sync="pageParams"
      @pagination="handleToQueryData"
    />
  </div>
</template>
<script>
export default {
  components: {
    Pagination: () => ('@/components/admin/pagination/pagination') // 分页组件
  },
  props: {
    activeName: {
      Type: String,
      default: ''
    },
    bottomFormData: {
      Type: Object,
      default: {}
    }
  },
  data () {
    return {
      pageParams: {}, // 分页
      tableData1: [] // 表格
    }
  },
  watch: {
    bottomFormData: {
      handler (newData) {
        this.handleToQueryData(newData)
      },
      deep: true
    }
  },
  methods: {
    // 初始请求数据
    handleToQueryData (newData) {
      console.log(this.activeName, newData)
    },
    // 选中数据
    handleCurrentChange (row) {
      console.log(row)
    }
  }
}
</script>
<style lang="scss" scoped>
.memberForm {
  margin-top: 10px;
  padding: 8px 23px;
  background-color: #fff;
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    // padding: 8px 10px;
    .el-checkbox {
      margin-left: -4px;
    }
  }
}
</style>
