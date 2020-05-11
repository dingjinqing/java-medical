<template>
  <div class="distributorListContent">
    <el-form
      :model="searchParam"
      label-width="130px"
      :label-position="right"
      :inline="true"
    >
      <div>
        <el-form-item label="手机号：">
          <el-input
            v-model="searchParam.mobile"
            size="small"
            class="inputWidth"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="微信昵称：">
          <el-input
            v-model="searchParam.username"
            size="small"
            class="inputWidth"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="下单用户手机号：">
          <el-input
            v-model="searchParam.orderMobile"
            size="small"
            class="inputWidth"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
      </div>
      <div>
        <el-form-item label="下单用户昵称：">
          <el-input
            v-model="searchParam.orderName"
            size="small"
            class="inputWidth"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="下单时间：">
          <el-date-picker
            v-model="searchParam.startOrderTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            placeholder="选择日期时间"
          ></el-date-picker>
          至
          <el-date-picker
            v-model="searchParam.endOrderTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            default-time="23:59:59"
            placeholder="选择日期时间"
          ></el-date-picker>
        </el-form-item>
      </div>
      <div>
        <el-form-item label="返利订单号：">
          <el-input
            v-model="searchParam.orderSn"
            size="small"
            class="inputWidth"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="返利日期：">
          <el-date-picker
            v-model="searchParam.startReturnTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            placeholder="选择日期时间"
          ></el-date-picker>
          至
          <el-date-picker
            v-model="searchParam.endReturnTime"
            type="datetime"
            size="small"
            align="right"
            class="selectWidth"
            default-time="23:59:59"
            placeholder="选择日期时间"
          ></el-date-picker>
        </el-form-item>
      </div>
      <div>
        <el-form-item label="返利状态：">
          <el-select
            v-model="searchParam.status"
            size="small"
            class="inputWidth"
            placeholder="请选择"
          >
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分销员分组：">
          <el-select
            v-model="searchParam.group"
            size="small"
            class="inputWidth"
            placeholder="请选择"
          >
            <el-option
              v-for="item in groupList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="返利关系：">
          <el-select
            v-model="searchParam.relationship"
            size="small"
            class="inputWidth"
            placeholder="请选择"
          >
            <el-option
              v-for="item in relationshipList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            @click="inviteList"
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button size="small">导出</el-button>
        </el-form-item>
      </div>
    </el-form>

    <el-table
      class="version-manage-table"
      header-row-class-name="tableClss"
      :data="tableData"
      border
      style="width: 100%; margin-top: 10px;"
    >
      <el-table-column
        prop="distributorName"
        label="分销员昵称"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="distributorMobile"
        label="手机号"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="realName"
        label="真实姓名"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="groupName"
        label="分销员分组"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="orderSn"
        label="返利订单号"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="orderAmount"
        label="订单总金额"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="userMobile"
        label="下单用户手机号"
        align="enter"
      >
      </el-table-column>
      <el-table-column
        prop=""
        label="下单用户昵称"
        align="enter"
      >
      </el-table-column>
      <el-table-column
        prop="rebateLevel"
        label="返利关系"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="totalRebateMoney"
        label="订单商品参与返利总金额"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="realRebateMoney"
        label="返利佣金"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="下单时间"
        align="center"
      >
      </el-table-column>
      <el-table-column
        label="返利状态"
        align="center"
      >
      </el-table-column>
      <el-table-column
        label="返利日期"
        align="center"
      >
      </el-table-column>
    </el-table>
    <pagination
      :page-params.sync="pageParams"
      @pagination="initData"
    />
  </div>
</template>

<script>
import { brokerageList } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      // 搜索
      searchParam: {
        mobile: '',
        username: '',
        orderMobile: '',
        orderName: '',
        startOrderTime: '',
        endOrderTime: '',
        orderSn: '',
        startReturnTime: '',
        endReturnTime: '',
        status: '',
        group: '',
        relationship: ''
      },
      tableData: [], // 表格
      pageParams: {}, // 分页
      requestParam: {},
      statusList: [{
        label: '',
        value: 0
      }], // 返利状态列表
      groupList: [{
        label: '',
        value: 0
      }], // 分销员分组列表
      relationshipList: [{
        label: '',
        value: 0
      }] // 返利关系列表
    }
  },
  mounted () {
    if (this.$route.query.userId > 0) {
      this.userId = this.$route.query.userId
    }
    this.initData()
  },
  methods: {
    initData () {
      this.requestParam = this.searchParam
      this.requestParam.userId = this.userId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      brokerageList(this.requestParam).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.tableData.map(item => {
            if (item.rebateLevel === 0) {
              item.rebateLevel = '自购返利'
            }
            if (item.rebateLevel === 1) {
              item.rebateLevel = '一级返利'
            }
            if (item.rebateLevel === 2) {
              item.rebateLevel = '二级返利'
            }
          })
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.distributorListContent {
  padding: 8px;
  padding-bottom: 38px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.inputWidth {
  width: 170px;
}
.selectWidth {
  width: 200px;
}
</style>
