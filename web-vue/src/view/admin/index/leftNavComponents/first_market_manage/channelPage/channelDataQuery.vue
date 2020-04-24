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
            prop="channelName"
            label="页面名称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="times"
            label="访问次数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="visit"
            label="访问人数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="new"
            label="新用户访问次数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="old"
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
    console.log(this.$route.query)
    this.handleData()
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
      tableData: [
        { channelName: '慧策渠道', times: 1, visit: 10, new: 1, old: 4 },
        { channelName: '店+小程序', times: 1, visit: 10, new: 1, old: 4 },
        { channelName: '卡米全渠道', times: 1, visit: 10, new: 1, old: 4 }
      ],
      myChart: {}
    }
  },
  methods: {
    changeDate () {

    },
    // 筛选
    filtrate () {
      channelData(this.param).then(res => {
        console.log(res)
        if (res.error === 0) {

        } else {
          this.$message.error(res.$message)
        }
      })
    },
    // 导出数据
    exportData () {

    },
    handleData () {
      let myChart = echarts.init(document.getElementById('myChart'))

      myChart.setOption({
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎']
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
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '邮件营销',
            type: 'line',
            data: [120, 132, 101, 134, 90, 230, 210]
          },
          {
            name: '联盟广告',
            type: 'line',
            data: [220, 182, 191, 234, 290, 330, 310]
          },
          {
            name: '视频广告',
            type: 'line',
            data: [150, 232, 201, 154, 190, 330, 410]
          },
          {
            name: '直接访问',
            type: 'line',
            data: [320, 332, 301, 334, 390, 330, 320]
          },
          {
            name: '搜索引擎',
            type: 'line',
            data: [820, 932, 901, 934, 1290, 1330, 1320]
          }
        ]
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
