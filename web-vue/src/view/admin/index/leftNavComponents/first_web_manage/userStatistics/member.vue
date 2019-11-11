<template>
  <section class="label">
    <div class="labelItem">会员统计</div>
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
          <div class="title">累积会员数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="累积会员数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #5A8BFF"
        >{{this.originalData.accumulationNumber}}</div>
        <div>较前一日{{this.originalData.accumulationNumberRate}}</div>
      </div>

      <div class="fromItem">
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">新增会员数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="新增会员数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #fc6181;"
        >{{this.originalData.addNumber}}</div>
        <div>较前一日{{this.originalData.addNumberRate}}</div>
      </div>

      <div
        class="fromItem"
        style="display: flex;"
      >
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">升级会员数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="升级会员数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #fdb64a;"
        >{{this.originalData.updateNumber}}</div>
        <div>较前一日{{this.originalData.updateNumberRate}}</div>
      </div>

      <div
        class="fromItem"
        style="display: flex;"
      >
        <div
          class="fromInfo"
          style="display: flex;"
        >
          <div class="title">储值会员数</div>
          <el-tooltip
            class="item"
            effect="light"
            content="储值会员数"
            placement="top"
          >
            <i class="el-icon-warning-outline icons"></i>
          </el-tooltip>
        </div>
        <div
          class="num"
          style="color: #fdb64a;"
        >{{this.originalData.chargeNumber}}</div>
        <div>较前一日{{this.originalData.chargeNumberRate}}</div>
      </div>
    </div>

  </section>
</template>

<script>
import echarts from 'echarts'
import { menberStatistics } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
    this.myUserChart = echarts.init(document.getElementById('memberCharts'))
  },

  data () {
    return {
      timeSelect: '',
      timeRange: [
        { value: 1, label: '最新1天' },
        { value: 7, label: '最新7天' },
        { value: 30, label: '最新30天' }
      ],
      params: 7,
      originalData: {
        accumulationNumber: '', // 积累会员数
        accumulationNumberRate: '',
        addNumber: '', // 新增会员数
        addNumberRate: '',
        updateNumber: '', // 升级会员数
        updateNumberRate: '',
        chargeNumber: '', // 储值会员数
        chargeNumberRate: ''
      }
    }
  },

  methods: {
    dateChangeHandler (time) {
      this.params = time
      // console.log(this.params)
      this.initData()
    },

    initData () {
      menberStatistics({ 'type': this.params }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
        }
      }).catch(err => console.log(err))
    },

    // 处理返回来的数据
    handleData (data) {
      console.log(data)
      // 累积会员数
      this.originalData.accumulationNumber = data.userData
      if (data.userDataRate > 0) {
        this.originalData.accumulationNumberRate = '↑' + (data.userDataRate * 100).toFixed(2) + '%'
      } else if (data.userDataRate < 0) {
        this.originalData.accumulationNumberRate = '↓ ' + Math.abs((data.userDataRate * 100)).toFixed(2) + '%'
      } else {
        this.originalData.accumulationNumberRate = '--'
      }

      // 新增会员数
      this.originalData.addNumber = data.regUserData
      if (data.regUserDataRate > 0) {
        this.originalData.addNumberRate = '↑' + (data.regUserDataRate * 100).toFixed(2) + '%'
      } else if (data.regUserDataRate < 0) {
        this.originalData.addNumberRate = '↓ ' + Math.abs((data.regUserDataRate * 100)).toFixed(2) + '%'
      } else {
        this.originalData.addNumberRate = '--'
      }

      // 升级会员数
      this.originalData.updateNumber = data.upgradeUserData
      if (data.upgradeUserDataRate > 0) {
        this.originalData.updateNumberRate = '↑' + (data.upgradeUserDataRate * 100).toFixed(2) + '%'
      } else if (data.upgradeUserDataRate < 0) {
        this.originalData.updateNumberRate = '↓ ' + Math.abs((data.upgradeUserDataRate * 100)).toFixed(2) + '%'
      } else {
        this.originalData.updateNumberRate = '--'
      }

      // 储值会员数
      this.originalData.chargeNumber = data.chargeUserData
      if (data.chargeUserDataRate > 0) {
        this.originalData.chargeNumberRate = '↑' + (data.chargeUserDataRate * 100).toFixed(2) + '%'
      } else if (data.chargeUserDataRate < 0) {
        this.originalData.chargeNumberRate = '↓ ' + Math.abs((data.chargeUserDataRate * 100)).toFixed(2) + '%'
      } else {
        this.originalData.chargeNumberRate = '--'
      }
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
    .icons {
      margin-left: 10px;
      position: relative;
    }
    .num {
      margin-top: 15px;
      font-size: 30px;
    }
    :nth-of-type(3) {
      margin-top: 10px;
    }
  }
}
</style>
