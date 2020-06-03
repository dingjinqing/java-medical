<!--
** 渠道分析 - 商品数据页面
** @author：zhaoxin
--->
<template>
  <div class="data_content">
    <section class="filter_conditions">
      <div>
        <span>时间：</span>
        <el-select
          v-model="param.timeType"
          placeholder="请选择时间"
          size="small"
          class="default_width"
          clearable
          @change="changeDate"
        >
          <el-option
            v-for="item in dateRange"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>

      <div>
        <span>查询指标：</span>
        <el-select
          v-model="param.searchType"
          placeholder="请选择查询指标"
          size="small"
          class="default_width"
          clearable
        >
          <el-option
            v-for="item in searchRange"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>

      <div>
        <span>访客类型：</span>
        <el-select
          v-model="param.visitorsType"
          placeholder="请选择访客类型"
          size="small"
          class="default_width"
          clearable
        >
          <el-option
            v-for="item in visitorsRange"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>

      <div>
        <span>查询页面：</span>
        <el-select
          v-model="param.searchPage"
          placeholder="请选择查询页面"
          size="small"
          class="default_width"
          clearable
        >
          <el-option
            v-for="item in searchPageRange"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>

      <div>
        <el-button
          size="small"
          type="primary"
          @click="filtrate()"
        >筛选</el-button>
      </div>

      <div>
        <el-button
          size="small"
          @click="exportData()"
        >导出数据</el-button>
      </div>
    </section>

    <section class="chart_content">
      <div id="myChart"></div>
    </section>

    <section class="table">
      <div class="table_list">
        <el-table
          :data="tableData"
          header-row-class-name="tableClss"
          style="width: 100%"
          border
        >
          <el-table-column
            prop="name"
            label="页面名称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="allPv"
            label="访问次数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="allUv"
            label="访问人数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="newPv"
            label="新用户访问次数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="oldPv"
            label="老用户访问次数"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
    </section>

  </div>
</template>

<script>
import echarts from 'echarts'
import { channelData } from '@/api/admin/marketManage/channelPage.js'

export default {
  mounted () {
    this.langDefault()
    this.initData()
  },
  data () {
    return {
      param: {
        channelId: this.$route.query.id,
        timeType: 2,
        searchType: 1,
        visitorsType: 1,
        searchPage: 1
      },
      echartsData: {
        date: '',
        title: '',
        series: []
      },
      dateRange: [
        { value: 2, label: '最近7天' },
        { value: 3, label: '最近30天' },
        { value: 4, label: '自定义' }
      ],
      searchRange: [
        { value: 1, label: '访问次数' },
        { value: 2, label: '访问人数' }
      ],
      visitorsRange: [
        { value: 1, label: '全部' },
        { value: 2, label: '老用户' },
        { value: 3, label: '新用户' }
      ],
      searchPageRange: [
        { value: 1, label: '全部' },
        { value: 2, label: '11' },
        { value: 3, label: '22' }
      ],
      tableData: [],
      myChart: {},
      option: {}
    }
  },
  methods: {
    changeDate () {

    },
    // 筛选
    filtrate () {
      this.initData()
    },
    // 导出数据
    exportData () {

    },
    // 初始化数据
    initData () {
      channelData(this.param).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.statisticalList
          this.initEcharts(res.content)
        } else {
          this.$message.error(res.$message)
        }
      })
    },
    initEcharts (data) {
      console.log(data)

      for (var i in data.series) {
        console.log(i)
        var obj = {
          name: data.series[i].channelName,
          type: 'line',
          data: data.series[i].accessData
        }
        console.log(obj)
        // this.echartsData.title.push(data.channelNameList[i])
        this.echartsData.series.push(obj)
        console.log(this.echartsData.series)
      }

      this.echartsData.date = data.dateList
      this.echartsData.title = data.channelNameList
      console.log(this.echartsData.date)
      console.log(this.echartsData.series)
      console.log(this.echartsData)
      let myChart = echarts.init(document.getElementById('myChart'))

      myChart.setOption({
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          type: 'scroll',
          data: this.echartsData.title,
          backgroundColor: '#f5f5f5',
          borderRadius: 5,
          itemGap: 30,
          padding: 10,
          width: 1000
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.echartsData.date
        },
        yAxis: {
          type: 'value'
        },
        series: this.echartsData.series
      })
    }
  }
}
</script>

<style lang="scss" scoped>
* {
  font-size: 14px;
}
.data_content {
  padding: 15px;
  margin: 10px;
  background: #fff;
  .filter_conditions {
    padding: 10px 0 0 10px;
    display: flex;
    div {
      margin-right: 20px;
      .default_width {
        width: 140px;
      }
    }
  }
  .table {
    margin: 0 10px 10px;
    background: #fff;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }
  .chart_content {
    margin: 20px 0 10px 0;
    #myChart {
      width: 100%;
      height: 500px;
    }
  }
}
</style>
