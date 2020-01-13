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

      <!--自定义时间-->
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
    <!--表格数据-->
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
          sortable
          prop="pageVisitPv"
          label="访问次数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          sortable
          prop="pageVisitUv"
          label="访问人数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          sortable
          prop="pageStayTimePv"
          label="次均时长"
          align="center"
        >
        </el-table-column>
        <el-table-column
          sortable
          prop="entryPagePv"
          label="入口页次数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          sortable
          prop="exitPagePv"
          label="退出页次数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="exitRate"
          label="退出率"
          align="center"
        >
        </el-table-column>
        <el-table-column
          sortable
          prop="pageSharePv"
          label="分享次数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          sortable
          prop="pageShareUv"
          label="分享人数"
          align="center"
        >
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {pageAnalysis} from '@/api/admin/firstWebManage/visitAnalysis/visitAnalysis.js'

export default {
  components: {},

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
        action: 1,
        type: 7,
        asc: 2,
        startDate: '',
        endDate: ''
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
        this.param.type = time
        this.loadData()
      } else {
        this.param.type = time
      }
    },

    // 自定义时间
    customDate () {
      console.log('选择器的时间：', this.timeValue)
      this.param.startDate = this.timeValue[0]
      this.param.endDate = this.timeValue[1]
      this.loadData()
    },
    // 页面初始化数据
    loadData () {
      pageAnalysis(this.param).then(res => {
        console.log('访问页面', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    // 数据处理
    handleData (content) {
      // 时间展示
      this.startDate.year = content.startDate.substring(0, 4)
      this.startDate.month = content.startDate.substring(4, 6)
      this.startDate.day = content.startDate.substring(6, 8)

      this.endDate.year = content.endDate.substring(0, 4)
      this.endDate.month = content.endDate.substring(4, 6)
      this.endDate.day = content.endDate.substring(6, 8)
      // 表格数据
      this.tableData = content.list
      content.list.map((item, index) => {
        if (item.exitRate > 0) {
          this.tableData[index].exitRate = (item.exitRate + '%')
        }
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
    .noData{
      height: 300px;
      text-align: center;
      padding-top: 150px;
    }
  }

</style>
