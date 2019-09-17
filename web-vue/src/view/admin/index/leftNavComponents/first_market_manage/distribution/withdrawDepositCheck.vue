<template>
  <div class="distributorListContent">
    <div class="searchInfo_main">
      <ul>
        <li class="li">
          <div class="liNav">
            <span class="labelClass">申请人昵称</span>
            <el-input
              placeholder="请填写手机号"
              size="small"
            ></el-input>
          </div>
          <div
            class="liNav"
            style="margin: 0 100px"
          >
            <span class="labelClass">手机号</span>
            <el-input
              placeholder="请填写微信昵称"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span class="labelClass">真实姓名</span>
            <el-input
              placeholder="请填写真实姓名"
              size="small"
            >
            </el-input>
          </div>
        </li>
        <li class="li">
          <div class="liNav">
            <span class="labelClass">提现单号</span>
            <el-input
              placeholder="请填写手机号"
              size="small"
            ></el-input>
          </div>
          <div
            class="liNav1"
            style="margin: 0 100px"
          >
            <span class="labelClass">申请时间</span>
            <el-date-picker
              class="timeInput"
              type="datetime"
              size="small"
              placeholder="选择开始时间"
            >
            </el-date-picker>至
            <el-date-picker
              class="timeInput"
              type="datetime"
              size="small"
              placeholder="选择结束时间"
            >
            </el-date-picker>
          </div>
        </li>

        <li class="li">
          <div class="liNav">
            <span class="labelClass">提现金额</span>
            <el-input
              placeholder="请填写手机号"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span class="labelClass">返利状态</span>
            <el-select
              size="small"
              v-model="value"
              placeholder="请选择"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>

          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button size="small">导出</el-button>
        </li>
      </ul>
    </div>
    <div class="list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
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
          prop="distributorGroup"
          label="分销员分组"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="ordersn"
          label="返利订单号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="totalOrderMoney"
          label="订单总金额"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="userMobile"
          label="下单用户手机号"
          align="
          center"
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
          label="订单返利商品总金额"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="realRebateMoney"
          label="返利佣金金额"
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

    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="list"
    />
  </div>
</template>

<script>
import { brokerageList } from '@/api/admin/marketManage/distribution.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      tableData: [],
      options: {
        value: '',
        label: ''
      },
      value: '',
      pageParams: {}
    }
  },
  mounted () {
    this.list()
  },
  methods: {
    list () {
      brokerageList(this.pageParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
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
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.searchInfo_main {
  position: relative;
  background-color: #fff;
  height: 100%;
  overflow: hidden;
  margin-bottom: 10px;
}
.li {
  padding: 8px 0;
  display: flex;
}
.liNav {
  width: 280px;
  display: flex;
}
.liNav span {
  display: block;
  width: 80px;
  line-height: 30px;
  height: 30px;
  text-align: right;
  color: #333;
  margin-right: 25px;
}
.labelClass {
  width: 180px !important;
}
.timeInput {
  width: 180px;
}
.list {
  margin-top: 10px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
</style>
