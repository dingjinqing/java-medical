<template>
  <!-- 标签成分分析 -->
  <section class="labelComponentAnalysic">
    <div class="tradesContainer">
      <section class="label">
        <div class="labelItem">标签成分分析</div>
        <div class="labelItem time">
          <span>时间筛选</span>
          <el-select
            v-model="timeSelect"
            size="small"
            clearable
            @change="dateChangeHandler"
            class="timeSelect"
          >
            <el-option
              v-for="item in timeRange"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
          <el-date-picker
            v-if="timeSelect===0"
            v-model="value1"
            type="daterange"
            size="small"
            @change="customDate"
            value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            class="custom"
          >
          </el-date-picker>
          <span>{{this.predate.year}}年{{this.predate.month}}月{{this.predate.day}}日 - {{date.year}}年{{date.month}}月{{date.day}}日</span>
        </div>

        <!-- 表格数据部分 -->
        <div class="table_list">
          <el-table
            header-row-class-name="tableClss"
            :data="tableData"
            :default-sort="{prop: 'tagName', order: 'ascending'}"
            border
            style="width: 100%"
          >
            <el-table-column
              prop="tagName"
              label="标签"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="userNum"
              label="用户数"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="userNumWithPhone"
              label="有手机号客户数"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidNum"
              label="付款笔数"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidMoney"
              label="付款金额（元）"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidUserNum"
              label="付款人数"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidGoodsNum"
              label="付款商品件数"
              sortable
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
  </section>
</template>

<script>
import { labelAnalysisApi } from '@/api/admin/firstWebManage/tradesStatistics/tradesStatistics.js'
import pagination from '@/components/admin/pagination/pagination'

export default {
  components: { pagination },
  name: 'echarts',
  created () {
    this.initDataList()
    this.initDate()
  },
  mounted () {
    this.langDefault()
  },
  data () {
    return {
      value1: '',
      value2: '',

      // 标签成分分析
      custom: true,
      timeRange: [
        { value: 1, label: '最新1天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' },
        { value: 0, label: '自定义' }
      ],
      date: {
        year: '',
        month: '',
        day: ''
      },
      predate: {
        year: '',
        month: '',
        day: ''
      },
      timeSelect: '',
      selectDate: [],
      tableData: [],
      pageParams: {},
      queryParams: {
        'screeningTime': '30',
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
    // 数据日期切换
    dateChangeHandler (value) {
      this.queryParams.screeningTime = value
      this.initDataList()
    },

    // 自定义筛选时间
    customDate (val) {
      console.log(val)
      this.queryParams.startTime = val[0]
      this.queryParams.endTime = val[1]
      this.predate.yaer = this.queryParams.startTime.slice(0, 4)
      this.predate.month = this.queryParams.startTime.slice(5, 7)
      this.predate.day = this.queryParams.startTime.slice(8, 10)
      this.date.yaer = this.queryParams.endTime.slice(0, 4)
      this.date.month = this.queryParams.endTime.slice(5, 7)
      this.date.day = this.queryParams.endTime.slice(8, 10)
      // console.log(this.queryParams.startTime, this.queryParams.endTime)
      this.initDataList()
    },

    // 初始化日期
    initDate () {
      let current = new Date()
      console.log(current)
      this.date.day = current.getDate()
      this.date.month = current.getMonth() + 1
      this.date.year = current.getFullYear()

      this.predate.day = current.getDate() - 30
      this.predate.month = current.getMonth()
      this.predate.year = current.getFullYear()
    },

    // 初始化加载数据
    initDataList () {
      // 标签成交分析接口
      let params = Object.assign({}, this.pageParams, this.queryParams)
      console.log(params)
      labelAnalysisApi(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      }).catch(err => console.log(err))
    }
  }
}

</script>
<style lang="scss" scoped>
.labelComponentAnalysic {
  padding: 0 10px 10px;
  font-size: 14px;
  .tradesContainer {
    position: relative;
    padding: 0 10px 10px;
    background: #fff;
    .label {
      .selectMonth {
        width: 150px;
      }
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
        .custom {
          width: 225px;
          margin-right: 10px;
        }
      }
      .wrapperCharts {
        display: flex;
        justify-content: space-between;
        width: 100%;
        height: 500px;
        // border: 1px solid #000;
        .mapArea {
          width: 45%;
          #charts {
            height: 500px;
            width: 100%;
            position: relative;
            // border: 10px solid #f40;
          }
        }
        .tableWrapper {
          width: 50%;
          margin-left: 2%;
          margin-right: 2%;
          margin-top: 50px;
          height: 500px;
          /deep/ .areaTable th {
            background-color: #f5f5f5;
            border: none;
            height: 36px;
            color: #000;
            font-size: 14px;
          }
          .table_lists {
            position: relative;
            background-color: #fff;
            padding: 10px 0 10px;
          }
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
