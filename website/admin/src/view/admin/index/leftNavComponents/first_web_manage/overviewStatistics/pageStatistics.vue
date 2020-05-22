<template>
  <div class="visitTrendStatistics">
    <!--    下拉框-->
    <div class="top">
      <!--     选择时间-->
      <el-select
        v-model="timeSelect"
        size="small"
        clearable
        @change="timeChangeHandler"
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
        @change="customDate"
        value-format="yyyyMMdd"
        range-separator="-"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        class="custom"
      >
      </el-date-picker>
      <span>{{this.startDate.year}}年{{this.startDate.month}}月{{this.startDate.day}}日 - {{this.endDate.year}}年{{this.endDate.month}}月{{this.endDate.day}}日</span>
    </div>
    <!--    表格数据-->
    <div class="table">
      <el-table
        class="version-manage-table"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="pagePath"
          label="页面路径"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="pageName"
          label="页面名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="pageVisitPv"
          label="访问次数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="rate"
          label="占比"
          align="center"
        >
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {getPageInfo} from '@/api/admin/firstWebManage/overviewStatistics/overviewStatistics.js'

export default {
  data () {
    return {
      custom: true,
      timeValue: [],
      timeSelect: 7,
      timeRange: [
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
        { value: 0, label: '自定义' }
      ],
      param: {
        type: '7',
        startTime: '',
        endTime: ''
      },
      chartChange: {
        date: [],
        number: []
      },
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
      tableData: []
    }
  },
  created () {
    this.loadData()
  },
  mounted () {
  },
  methods: {
    // time下拉框变化
    timeChangeHandler (time) {
      console.log(time)
      if (time === 7 || time === 30) {
        this.chartChange = {
          date: [],
          number: []
        }
        this.param.type = time
        this.loadData()
      } else {
        this.param.type = time
      }
    },
    // 自定义时间
    customDate () {
      this.chartChange = {
        date: [],
        number: []
      }
      console.log('选择器的时间：', this.timeValue)
      this.param.startTime = this.timeValue[0]
      this.param.endTime = this.timeValue[1]
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      getPageInfo(this.param).then(res => {
        console.log('页面统计', res)
        if (res.error === 0) {
          this.tableData = res.content.list
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
      this.startDate.year = content.startTime.substring(0, 4)
      this.startDate.month = content.startTime.substring(4, 6)
      this.startDate.day = content.startTime.substring(6, 8)

      this.endDate.year = content.endTime.substring(0, 4)
      this.endDate.month = content.endTime.substring(4, 6)
      this.endDate.day = content.endTime.substring(6, 8)

      content.list.map((item, index) => {
        this.tableData[index].rate = (item.rate + '%')
      })
    }
  }
}

</script>
<style lang="scss" scoped>
  .visitTrendStatistics {
    padding: 10px;
    background: #fff;

    .top {
      height: 50px;
      line-height: 50px;
      color: #333;

      .timeSelect {
        width: 140px;
        margin: 0 10px 0 2px;
      }
    }
  }

</style>
