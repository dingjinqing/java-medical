<template>
  <div class="content">

    <div class="main">
      <el-form :inline="true">
        <el-form-item
          size="small"
          label="用户昵称"
        >
          <el-input
            v-model="username"
            placeholder="请输入用户昵称"
          />
        </el-form-item>
        <el-form-item
          size="small"
          label="手机号"
        >
          <el-input
            v-model="mobile"
            placeholder="请输入手机号"
          />
        </el-form-item>
        <el-form-item
          size="small"
          label="助力活动ID"
        >
          <el-input
            v-model="id"
            placeholder="请输入助力活动ID"
          ></el-input>
        </el-form-item>
        <br>
        <el-form-item
          size="small"
          label="订单号"
        >
          <el-input
            v-model="orderSn"
            placeholder="请输入订单号"
          ></el-input>
        </el-form-item>
        <el-form-item
          size="small"
          label="是否已领取"
        >
          <el-select v-model="promoteStatus">
            <el-option
              label="全部"
              value="-1"
            ></el-option>
            <el-option
              label="是"
              value="2"
            ></el-option>
            <el-option
              label="否"
              value="1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-button
          size="small"
          type="primary"
          @click="onSubmit"
        >筛选</el-button>
        <el-button size="small">导出数据</el-button>
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
          prop="username"
          label="领取用户昵称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          label="领取用户手机号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="id"
          label="助力活动ID"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="promoteStatus"
          label="是否已领取"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="recTime"
          label="领取时间"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          label="订单号"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="loadData"
      />

    </div>
  </div>
</template>
<script>
import { receiveDetails } from '@/api/admin/marketManage/friendHelp.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data: function () {
    return {
      promoteId: '',
      username: '',
      mobile: '',
      id: '',
      promoteStatus: '-1',
      orderSn: '',
      tableData: [],
      pageParams: {
      }
    }
  },
  methods: {
    loadData () {
      this.pageParams.promoteId = this.promoteId
      this.pageParams.id = this.id
      this.pageParams.mobile = this.mobile
      this.pageParams.username = this.username
      this.pageParams.orderSn = this.orderSn
      this.pageParams.promoteStatus = this.promoteStatus
      console.log('pageParams:', this.pageParams)
      receiveDetails(this.pageParams).then(res => {
        console.log('pageInfo:', res)
        this.handData(res.content.dataList)
        this.pageParams = res.content.page
      })
    },

    onSubmit () {
      this.pageParams.currentPage = 1
      this.loadData()
    },
    handData (data) {
      data.map((item, index) => {
        if (item.promoteStatus === 2) {
          item.promoteStatus = '是'
        } else {
          item.promoteStatus = '否'
        }
      })
      this.tableData = data
    }
  },
  mounted () {
    this.promoteId = this.$route.params.id
    this.loadData()
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
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.paginationfooter {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
</style>
