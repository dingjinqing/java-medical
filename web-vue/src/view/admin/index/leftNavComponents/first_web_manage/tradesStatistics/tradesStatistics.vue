<template>
  <div class="tradesStatistics">
    <div class="tradesContainer">

      <!-- 地域分布 -->
      <section class="area"></section>

      <!-- 标签成分分析 -->
      <section class="label">
        <div class="labelItem">标签成分分析</div>
        <div class="labelItem time">
          <span>时间筛选</span>
          <el-select
            v-model="timeSelect"
            size="small"
            class="timeSelect"
          >
            <el-option
              v-for="(item, index) in timeRange"
              :key=index
              :label="item.label"
              :value="item.label"
            ></el-option>
          </el-select>
          <span>2019年09月30日 - {{2019}}年{{10}}月{{date}}日</span>
        </div>

        <!-- 表格数据部分 -->
        <div class="table_list">
          <el-table
            header-row-class-name="tableClss"
            :data="tableData"
            border
            style="width: 100%"
          >
            <el-table-column
              prop="tagName"
              label="标签"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="userNum"
              label="用户数"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="userNumWithPhone"
              label="有手机号客户数"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidNum"
              label="付款笔数"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidMoney"
              label="付款金额（元）"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidUserNum"
              label="付款人数"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidGoodsNum"
              label="付款商品件数"
              align="center"
            ></el-table-column>
          </el-table>
          <div>
            <pagination
              :page-params.sync="pageParams"
              @pagination="initDataList"
            ></pagination>
          </div>

        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { labelAnalysisApi } from '@/api/admin/firstWebManage/tradesStatistics/tradesStatistics.js'
import pagination from '@/components/admin/pagination/pagination'

export default {
  components: { pagination },
  created () {
    this.initDataList()
    this.initDate()
  },
  data () {
    return {
      timeSelect: '',
      timeRange: [
        { label: '最新1天' },
        { label: '最新7天' },
        { label: '最新30天' },
        { label: '自定义' }
      ],
      data: {
        year: '',
        month: '',
        day: ''
      },
      tableData: [],
      pageParams: {},
      queryParams: {
        'screeningTime': 30,
        'startTime': '',
        'endTime': '',
        'orderByField': '',
        'orderByType': 'asc',
        'currentPage': 1,
        'pageRows': 20
      }
    }
  },
  methods: {
    initDate () {
      this.date.day = new Date()
      // this.date.year =
    },
    initDataList () {
      let params = Object.assign({}, this.pageParams, this.queryParams)
      labelAnalysisApi(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          console.log(this.pageParams)
          this.tableData = res.content.dataList
        }
      }).catch(err => console.log(err))
    }
  }
}

</script>
<style lang="scss" scoped>
.tradesStatistics {
  padding: 10px;
  font-size: 14px;
  .tradesContainer {
    position: relative;
    padding: 0 10px 10px;
    background: #fff;
    .label {
      .labelItem {
        height: 50px;
        line-height: 50px;
        color: #333;
      }
      .time {
        margin-top: -10px;
        .timeSelect {
          width: 140px;
          margin: 0 10px 0 2px;
        }
      }

      /deep/ .tableClss th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        color: #000;
        padding: 8px 10px;
        font-size: 14px;
      }
      .table_list {
        position: relative;
        background-color: #fff;
        padding: 10px 0 10px;
      }
    }
  }
}
</style>
