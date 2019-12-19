<template>
  <!-- 标签成分分析 -->
  <section class="labelComponentAnalysic">
    <div class="tradesContainer">
      <section class="label">
        <div class="labelItem">
          {{$t('tradesStatistics.labelComponentsAnalysis')}}
          <el-tooltip
            effect="light"
            placement="right-start"
          >
            <div
              slot="content"
              style="width: 500px;line-height: 30px;font-size: 14px;padding:10px 5px 10px 10px"
            >
              <section
                v-for="item in labelTipsList"
                :key="item.title"
                style="display: flex"
              >
                <div style="width: 30%;color:#999">{{item.title}}</div>
                <div style="width: 70%;color: #353535">{{item.content}}</div>
              </section>
            </div>
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div class="labelItem time">
          <span>{{$t('tradesStatistics.timeSelect')}}</span>
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
            :start-placeholder="$t('tradesStatistics.startTime')"
            :end-placeholder="$t('tradesStatistics.endTime')"
            class="custom"
          >
          </el-date-picker>
          <span>{{this.startDate.year}}{{$t('tradesStatistics.year')}}{{this.startDate.month}}{{$t('tradesStatistics.month')}}{{this.startDate.day}}{{$t('tradesStatistics.day')}} - {{this.endDate.year}}{{$t('tradesStatistics.year')}}{{this.endDate.month}}{{$t('tradesStatistics.month')}}{{this.endDate.day}}{{$t('tradesStatistics.day')}}</span>
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
              :label="$t('tradesStatistics.label')"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="userNum"
              :label="$t('tradesStatistics.userNumber')"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="userNumWithPhone"
              :label="$t('tradesStatistics.hasMobileNumber')"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidNum"
              :label="$t('tradesStatistics.payTimes')"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidMoney"
              :label="$t('tradesStatistics.payMoney')"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidUserNum"
              :label="$t('tradesStatistics.paymentNumber')"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              prop="paidGoodsNum"
              :label="$t('tradesStatistics.payGoodsNumber')"
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
  watch: {
    lang () {
      this.labelTipsList = this.$t('tradesStatistics.labelTipsList')
      this.timeRange = this.$t('tradesStatistics.timeRange')
    }
  },
  created () {
    this.initDataList()
  },
  mounted () {
    this.langDefault()
  },
  data () {
    return {
      value1: '',
      // 标签成分分析
      custom: true,
      // timeRange: [
      //   { value: 1, label: '最新1天' },
      //   { value: 7, label: '最新7天' },
      //   { value: 30, label: '最新30天' },
      //   { value: 0, label: '自定义' }
      // ],
      timeRange: this.$t('tradesStatistics.timeRange'),
      startDate: {
        year: '',
        month: '',
        day: ''
      },
      endDate: {
        year: '',
        month: '',
        day: ''
      },
      timeSelect: 1,
      tableData: [],
      pageParams: {},
      queryParams: {
        'screeningTime': '1',
        'startTime': '',
        'endTime': '',
        'orderByField': '',
        'orderByType': 'asc',
        'currentPage': 1,
        'pageRows': 20
      },
      // labelTipsList: [
      //   { title: '付款笔数', content: '统计时间内，该标签下客户的成功付款订单数，一个订单对应唯一一个订单号（拼团在成团时计入付款订单；货到付款在发货时计入付款订单，不剔除退款订单）' },
      //   { title: '付款金额（元）', content: '统计时间内，该标签下客户的所有付款订单金额之和（拼团在成团时计入付款金额；货到付款在发货时计入付款金额，不剔除退款金额）' },
      //   { title: '付款人数', content: '统计时间内，该标签下客户下单并且付款成功的客户数，一人多次付款记为一人（不剔除退款订单）' },
      //   { title: '付款商品件数', content: '统计时间内， 该标签下客户成功付款订单的商品件数之和（不剔除退款订单）' },
      //   { title: '有手机号客户数', content: '统计时间内， 该标签下下单并且付款成功的有手机号码的客户数（不剔除退款订单）' }
      // ]
      labelTipsList: this.$t('tradesStatistics.labelTipsList')
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
      console.log(val, 'val')
      this.queryParams.startTime = val[0]
      this.queryParams.endTime = val[1]

      this.startDate.year = this.queryParams.startTime.slice(0, 4)
      this.startDate.month = this.queryParams.startTime.slice(5, 7)
      this.startDate.day = this.queryParams.startTime.slice(8, 10)

      this.endDate.year = this.queryParams.endTime.slice(0, 4)
      this.endDate.month = this.queryParams.endTime.slice(5, 7)
      this.endDate.day = this.queryParams.endTime.slice(8, 10)
      // console.log(this.queryParams)
      this.initDataList()
    },

    // 初始化加载数据
    initDataList () {
      // 标签成交分析接口
      let params = Object.assign({}, this.pageParams, this.queryParams)
      console.log(params)
      labelAnalysisApi(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.pageData.page
          console.log(this.pageParams)
          this.tableData = res.content.pageData.dataList
          console.log(this.tableData)

          // 时间处理
          this.startDate.year = res.content.dateMap.startTime.split('-')[0]
          this.startDate.month = res.content.dateMap.startTime.split('-')[1]
          this.startDate.day = res.content.dateMap.startTime.split('-')[2]

          this.endDate.year = res.content.dateMap.endTime.split('-')[0]
          this.endDate.month = res.content.dateMap.endTime.split('-')[1]
          this.endDate.day = res.content.dateMap.endTime.split('-')[2]
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
