<template>
  <section class="label">
    <div class="labelItem">用户复购趋势</div>
    <el-select
      v-model="timeSelect"
      size="small"
      clearable
      class="timeSelect"
    >
      <el-option
        v-for="item in timeRange"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      ></el-option>
    </el-select>
    {{value1}}周
    <el-date-picker
      v-model="value1"
      type="week"
      format="yyyy 第 WW 周"
      :picker-options="{'firstDayOfWeek': 1}"
      placeholder="选择周"
      @change='handleWeek'
    >
    </el-date-picker>

  </section>
</template>

<script>
// import echarts from 'echarts'
import { userReBuy } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
    // this.myUserChart = echarts.init(document.getElementById('memberCharts'))
  },
  watch: {
    value1 (val) {
      console.log(val)
    }
  },
  data () {
    return {
      timeSelect: '自然周',
      timeRange: [
        { value: 1, label: '自然周' }
      ],
      value1: '',
      params: {
        'weekNum': 44,
        'monday': '2019-11-04',
        'sunday': '2019-11-09'
      }
    }
  },

  methods: {

    handleWeek (val) {
      console.log(val)
    },

    initData () {
      userReBuy(this.params).then(res => {
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
        }
      }).catch(err => console.log(err))
    },

    // 处理接口返回来的数据
    handleData (data) {
      console.log(data)
    }
  }
}

</script>
<style lang="scss" scoped>
.label {
  padding: 10px;
  background: #fff;
  .labelItem {
    height: 50px;
    line-height: 50px;
    color: #333;
  }
  .timeSelect {
    width: 140px;
    margin: 0 10px 0 2px;
  }
}
</style>
