<template>
  <div class="content">

    <div class="main">
      <el-form label-width="100px">
        <el-form-item
          :label="this.$t('seckill.mobile') + '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.mobile"
            :placeholder="this.$t('seckill.mobile')"
            maxlength="11"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="this.$t('seckill.name')+ '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.username"
            :placeholder="this.$t('seckill.name')"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="this.$t('seckill.inviter')+ '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.inviteUserName"
            :placeholder="this.$t('seckill.inviter')"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-button
          size="small"
          type="primary"
          @click="initDataList"
          class="item"
          style="margin-left: 10px;"
        >查询</el-button>
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
          :label="this.$t('seckill.activityName')"
          prop="name"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.id')"
          prop="userId"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.name')"
          prop="userName"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.mobile')"
          prop="mobile"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.createTime')"
          prop="createTime"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="this.$t('seckill.inviter')"
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
    padding: 15px;
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
.el-col-offset-2 {
  padding-left: 0 !important;
  margin-left: 6.33333% !important;
}
</style>
