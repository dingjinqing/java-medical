<template>
  <div class="payContent">
    <div class="sexAndAge">
      <el-form
        label-position="top"
        label-width="80px"
      >
        <el-form-item label="性别及年龄分布">
          <el-select
            v-model="userNum"
            placeholder="请选择"
            size="mini"
            style="width:160px"
            @change="changeUserNum"
          >
            <el-option
              v-for="item in userNumOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>

          <el-select
            v-model="type"
            placeholder="请选择"
            size="mini"
            style="width:160px"
            @change="search"
          >
            <el-option
              v-for="item in visitTrendOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <span class="itemDate">{{this.startDate}}</span>
          <span>-</span>
          <span class="itemDate">{{this.endDate}}</span>
        </el-form-item>
      </el-form>
      <el-row>
        <el-col :span="12">
          <div>
            <ve-ring
              :data="chartDataSex"
              ref="chart1"
            ></ve-ring>
            <span class="buttomTitle">性别分布</span>
          </div>
        </el-col>
        <el-col :span="12">
          <div>
            <ve-histogram
              :legend-visible="false"
              :data="chartDataSexHis"
              :settings="chartSettings"
              ref="chart2"
            ></ve-histogram>
            <span class="buttomTitle">年龄分布</span>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getPortraitRequest } from '@/api/admin/basicConfiguration/userportrait.js'
import VCharts from 'v-charts'
export default {
  components: {
    VCharts
  },
  data () {
    this.chartSettings = {
      labelMap: {
        'value': '数量'
      }
    }
    return {
      type: 0,
      userNum: 1,
      startDate: null,
      endDate: null,
      tableData: null,
      chartDataSex: {
        columns: ['name', 'value'],
        rows: []
      },
      chartDataSexHis: {
        columns: ['name', 'value'],
        rows: []
      },
      rows1: [],
      rows2: [],
      userNumOptions: [{
        value: 1,
        label: '活跃用户'
      }, {
        value: 2,
        label: '新增用户'
      }],
      visitTrendOptions: [
        {
          value: 0,
          label: '昨天'
        }, {
          value: 1,
          label: '最近七天'
        }, {
          value: 2,
          label: '最近三十天'
        }]
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.defaluteData()
  },
  watch: {
    lang () {
      this.defaluteData()
    },
    rows1 (data) {
      // 在一个初始宽度未知的容器内绘制图表时，因为无法获取宽度，所以图表会绘制出问题
      this.$nextTick(_ => {
        this.$refs.chart2.echarts.resize()
      })
    },
    rows2 (data) {
      this.$nextTick(_ => {
        this.$refs.chart1.echarts.resize()
      })
    }
  },
  methods: {
    defaluteData () {
      this.search()
    },
    search () {
      console.log(this.date)
      let params = {
        'type': this.type
      }
      getPortraitRequest(params).then((res) => {
        console.log('res-----------------------------------')
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content
          this.startDate = res.content.startDate
          this.endDate = res.content.endDate
          var needData = null
          if (this.userNum === 1) {
            // 活跃用户
            needData = res.content.activeUser
          } if (this.userNum === 2) {
            // 新增用户
            needData = res.content.newAddUser
          }
          var chartData = needData.genders
          var chartData2 = needData.ages
          this.chartDataSex.rows = chartData
          this.rows1 = chartData
          this.chartDataSexHis.rows = chartData2
          this.rows2 = chartData2
          console.log('chartDataSex')
          console.log(this.chartDataSex)
          console.log('chartDataSexHis')
          console.log(this.chartDataSexHis)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 性别及年龄分布 用户变化
    changeUserNum () {
      var needData = null
      if (this.userNum === 1) {
        // 活跃用户
        needData = this.tableData.activeUser
      } if (this.userNum === 2) {
        // 新增用户
        needData = this.tableData.newAddUser
      }
      var chartData = needData.genders
      var chartData2 = needData.ages
      this.chartDataSex.rows = chartData
      this.chartDataSexHis.rows = chartData2
    }

  }
}

</script>
<style lang="scss" scoped>
.payContent {
  padding: 10px 20px 0 20px;
  font-size: 14px;
  height: 100%;
}
.sexAndAge {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.itemDate {
  color: #9a9a9a;
}
.buttomTitle {
  width: 30px;
  padding-left: 46%;
}
.inner {
  position: relative;
  padding: 24px 20px 0 20px;
}
.regional {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
</style>
