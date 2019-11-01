<template>
  <div class="userStatistics">
    <div class="userContainer">
      <section class="label overviewAndTrend">
        <div class="labelItem">地域分布</div>
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
        <span>2019年10月02日 - 2019年11月01日</span>

        <!-- 表格数据部分 -->
        <div class="fromWrapper">
          <div class="fromItem">
            <div
              class="fromInfo"
              style="display: flex;"
            >
              <div class="title">访客数</div>
              <el-tooltip
                class="item"
                effect="light"
                content="访客数"
                placement="top"
              >
                <i class="el-icon-warning-outline icons"></i>
              </el-tooltip>
            </div>
            <div
              class="num"
              style="color: #5A8BFF"
            >352</div>
            <div>较前一月 ↑ 23.5%</div>
          </div>
          <div class="fromItem">
            <div
              class="fromInfo"
              style="display: flex;"
            >
              <div class="title">累积用户数</div>
              <el-tooltip
                class="item"
                effect="light"
                content="累积用户数"
                placement="top"
              >
                <i class="el-icon-warning-outline icons"></i>
              </el-tooltip>
            </div>
            <div
              class="num"
              style="color: #fc6181;"
            >3362</div>
            <div>较前一月 ↑ 8.55%</div>
          </div>
          <div
            class="fromItem"
            style="display: flex;"
          >
            <div
              class="fromInfo"
              style="display: flex;"
            >
              <div class="title">用户成交数</div>
              <el-tooltip
                class="item"
                effect="light"
                content="用户成交数"
                placement="top"
              >
                <i class="el-icon-warning-outline icons"></i>
              </el-tooltip>
            </div>
            <div
              class="num"
              style="color: #fdb64a;"
            >28</div>
            <div>较前一月 ↓ 22.22%</div>
          </div>
        </div>

        <!-- echarts图表部分 -->
        <div id="charts"></div>

      </section>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import { customerTrend } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
    this.myChart = echarts.init(document.getElementById('charts'))
  },

  data () {
    return {
      timeSelect: '',
      timeRange: [
        { value: 1, label: '最新1天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' }
      ]
    }
  },

  methods: {
    dateChangeHandler (value) {
      // this.queryParams.screeningTime = value
      this.initData()
    },

    initData () {
      customerTrend({ 'lastNum': '1' }).then(res => {
        console.log(res)
      }).catch(err => console.log(err))
    }
  }
}

</script>
<style lang="scss" scoped>
.userStatistics {
  padding: 10px;
  font-size: 14px;
  .userContainer {
    padding: 10px;
    position: relative;
    background: #fff;
    .label {
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
    .fromWrapper {
      border: 1px solid #eee;
      height: 130px;
      width: 85%;
      display: flex;
      justify-content: center;
      align-items: center;
      margin: 30px auto 50px;
      .fromItem {
        flex: 1;
        height: 130px;
        position: relative;
        border-right: 1px solid #eee;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        // .fomrInfo {
        // display: flex;
        // .item {
        .icons {
          margin-left: 10px;
          position: relative;
        }
        // }
        .num {
          margin-top: 15px;
          font-size: 30px;
        }
        :nth-of-type(3) {
          margin-top: 10px;
        }
        // }
      }
    }

    #charts {
      width: 100%;
      height: 500px;
      left: -30px;
    }
  }
}
</style>
