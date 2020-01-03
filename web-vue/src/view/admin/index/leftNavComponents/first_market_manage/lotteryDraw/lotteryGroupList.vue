<template>
  <div class="content">

    <div class="main">
      <el-form label-width="100px">
        <el-form-item
          label="用户昵称："
          class="item"
        >
          <el-input
            size="small"
            placeholder="请输入用户昵称"
            maxlength="11"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="开团时间："
          class="item"
        >
          <el-input
            size="small"
            clearable
            class="inputWidth"
          ></el-input>
          至
          <el-input
            size="small"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="团ID："
          class="item"
        >
          <el-input
            size="small"
            placeholder="请输入团ID"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="手机号："
          class="item"
        >
          <el-input
            size="small"
            placeholder="请输入手机号"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="成团状态："
          class="item"
        >
          <el-select
            size="small"
            class="inputWidth"
            placeholder="请选择"
          >
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-button
          size="small"
          type="primary"
          class="item"
          style="margin-left: 10px;"
        >筛选</el-button>
        <el-button
          size="small"
          type="primary"
          class="item"
          style="margin-left: 10px;"
        >重置筛选</el-button>
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
          label="团ID"
          prop=""
          align="center"
        ></el-table-column>
        <el-table-column
          label="参团人数"
          prop=""
          align="center"
        ></el-table-column>
        <el-table-column
          label="活动商品"
          prop=""
          align="center"
        ></el-table-column>
        <el-table-column
          label="开团时间"
          prop=""
          align="center"
        ></el-table-column>
        <el-table-column
          label="团长昵称"
          prop=""
          align="center"
        ></el-table-column>
        <el-table-column
          label="团长手机号"
          prop=""
          align="center"
        ></el-table-column>
        <el-table-column
          label="成团时间"
          prop=""
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
import { groupLotteryList } from '@/api/admin/marketManage/lotteryDraw.js'
export default {

  components: {
    pagination
  },
  data () {
    return {
      // 成团状态
      statusList: [{
        value: '1',
        label: '已成团'
      }, {
        value: '2',
        label: '未成团'
      }],
      loading: false,
      pageParams: {}, // 分页
      requestParams: {},
      tableData: [] // 表格数据
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      // 初始化数据
      this.initDataList()
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.skId = this.$route.query.id
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      groupLotteryList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
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
    .item {
      display: inline-block;
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
  padding: 15px;
}
.inputWidth {
  width: 175px;
}
.el-form-item {
  margin-bottom: 1px;
}
/deep/ .el-form-item__label {
  padding: 0;
}
.el-row {
  margin-bottom: 2px !important;
}
</style>
