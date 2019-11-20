<template>
  <div class="payContent">
    <div class="sexAndAge">
      <el-form
        label-position="top"
        label-width="80px"
      >
        <el-form-item :label="$t('userportrait.title4')">
          <el-select
            v-model="userNum"
            :placeholder="$t('userportrait.please')"
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
            :placeholder="$t('userportrait.please')"
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
      <el-table
        :data="chartDataSex"
        style="width: 100%"
        border
        :header-cell-style="{background:'#eef1f6',color:'#606266'}"
      >
        <el-table-column
          prop="name"
          :label="$t('userportrait.gender')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="value"
          :label="label1"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="percentage"
          :label="$t('userportrait.proportion')"
          sortable
        >
        </el-table-column>
      </el-table>
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
    return {
      type: 0,
      userNum: 1,
      startDate: null,
      endDate: null,
      tableData: null,
      chartDataSex: null,
      userNumOptions: this.$t('userportrait.userNumOptions'),
      visitTrendOptions: this.$t('userportrait.visitTrendOptions'),
      sum: 0,
      label1: this.$t('userportrait.activeUser')
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
      this.userNumOptions = this.$t('userportrait.userNumOptions')
      this.visitTrendOptions = this.$t('userportrait.visitTrendOptions')
      this.label1 = this.$t('userportrait.activeUser')
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
          var chartData = needData === null ? [] : needData.genders
          console.log('chartData')
          console.log(chartData)
          let sum = 0
          for (let i = 0; i < chartData.length; i++) {
            sum = sum + chartData[i].value
          }
          this.sum = sum
          console.log('总数')
          console.log(sum)
          for (let i = 0; i < chartData.length; i++) {
            let number = chartData[i].value / sum
            let str = Number(number * 100).toFixed(2)
            str += '%'
            chartData[i].percentage = str
          }
          this.chartDataSex = chartData
          console.log('chartDataSex')
          console.log(this.chartDataSex)
          console.log('chartDataSexHis')
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
        this.label1 = this.$t('userportrait.activeUser')
      } if (this.userNum === 2) {
        // 新增用户
        this.label1 = this.$t('userportrait.newUsers')
        needData = this.tableData.newAddUser
      }
      var chartData = needData === null ? [] : needData.genders
      console.log('啦啦啦chartData')
      console.log(chartData)
      for (let i = 0; i < chartData.length; i++) {
        chartData[i].percentage = null
        let number = chartData[i].value / this.sum
        let str = Number(number * 100).toFixed(2)
        str += '%'
        chartData[i].percentage = str
      }
      console.log(chartData)
      this.chartDataSex = chartData
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
