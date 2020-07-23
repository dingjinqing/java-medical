<template>
  <div class="main">
    <div class="nav_box">
        <div class="filters">
            <div class="filters_item">
                <span class="fil_span">时间筛选：</span>
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
                    v-model="timeValue"
                    type="daterange"
                    size="small"
                    value-format="yyyyMMdd"
                    @change="changeDate"
                    range-separator="-"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                >
                </el-date-picker>
                <span class="choosed_time">{{this.startDate.year}}年{{this.startDate.month}}月{{this.startDate.day}}日 - {{this.endDate.year}}年{{this.endDate.month}}月{{this.endDate.day}}日</span>
            </div>
            <div class="btn_wrap">
                <el-button
                    type='primary'
                    size='small'
                    @click="initData"
                >查询</el-button>
                <el-button
                    type="primary"
                    size="small"
                >导出</el-button>
            </div>
        </div>
    </div>
    <div class="table_box">
        <el-table
          v-loading='loading'
          :data='tableData'
          style="width:100%"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none',
            'color': '#000'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
            <el-table-column
                prop=''
                label='日期'
            ></el-table-column>
            <el-table-column
                prop=''
                label='销售金额'
            ></el-table-column>
            <el-table-column
                prop=''
                label='销售单数'
            ></el-table-column>
            <el-table-column
                prop=''
                label='处方药销售金额'
            ></el-table-column>
            <el-table-column
                prop=''
                label='处方药销售单数'
            ></el-table-column>
            <el-table-column
                prop=''
                label='退货金额'
            ></el-table-column>
            <el-table-column
                prop=''
                label='退货单数'
            ></el-table-column>
            <el-table-column
                prop=''
                label='笔单价'
            ></el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initData"
        />
    </div>
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  watch: {
    lang () {
      this.timeRange = this.$t('tradesStatistics.timeRange')
    }
  },
  data () {
    return {
      loading: false,
      isChange: true,
      timeValue: [],
      timeSelect: 1,
      screeningTime: '1',
      pageParams: {},
      tableData: [],
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
      param: {
        type: 1,
        startDate: '',
        endDate: ''
      }
    }
  },
  methods: {
    // 选择时间段
    dateChangeHandler (time) {
      if (time !== 0) {
        this.screeningTime = time
        this.isChange = true
        this.initData()
      }
    },
    // 自定义时间
    changeDate () {
      this.screeningTime = 0
      this.param.startDate = this.timeValue[0].substring(0, 4) + this.timeValue[0].substring(4, 6) + this.timeValue[0].substring(6, 8)
      this.param.endDate = this.timeValue[1].substring(0, 4) + this.timeValue[1].substring(4, 6) + this.timeValue[1].substring(6, 8)
      this.isChange = true
      this.initData()
    },
    initData () {
      this.param.type = this.screeningTime
      this.startDate.year = this.param.startDate.substring(0, 4)
      this.startDate.month = this.param.startDate.substring(4, 6)
      this.startDate.day = this.param.startDate.substring(6, 8)
      this.endDate.year = this.param.endDate.substring(0, 4)
      this.endDate.month = this.param.endDate.substring(4, 6)
      this.endDate.day = this.param.endDate.substring(6, 8)
    }
  }
}
</script>

<style lang='scss' scoped>
.main{
    .nav_box{
        display: flex;
        width: 100%;
        background-color: #fff;
        padding: 15px;
        .filters{
            flex: 2;
            display: flex;
            flex-wrap: wrap;
            line-height: 32px;
            margin-left: -15px;
            .filters_item {
                display: flex;
                justify-content: flex-end;
                margin-left: 15px;
                .fil_span {
                    width: 100px;
                    font-size: 14px;
                    text-align: right;
                }
                .timeSelect {
                    width: 140px;
                    margin: 0 10px 0 10px;
                }
                .choosed_time{
                    margin-left: 20px;
                }
            }
            .btn_wrap{
                margin-left: 20px;
            }
        }
    }
    .table_box{
        padding: 10px;
        background: #fff;
        margin-top: 10px;
    }
}
</style>
