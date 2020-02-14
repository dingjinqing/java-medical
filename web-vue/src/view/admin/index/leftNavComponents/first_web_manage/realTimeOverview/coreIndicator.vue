<template>
<div class="coreIndicator">
  <div class="top">
    <div class="left" style="font-size: 18px">核心指标</div>
    <div class="right">
      时间筛选:
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
    </div>
  </div>
  <div class="mid">
    <table border="1" class="table">
      <tr>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">付款金额</div>
          <div style="padding-bottom: 10px;font-size:22px ">999</div>
          <div style="color: gray;font-size: 13px">较前一日 888</div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">访问-付款转化率</div>
          <div style="padding-bottom: 10px;font-size:22px ">999</div>
          <div style="color: gray;font-size: 13px">较前一日 888</div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">客单价</div>
          <div style="padding-bottom: 10px;font-size:22px ">999</div>
          <div style="color: gray;font-size: 13px">付款订单数 888</div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">访客数</div>
          <div style="padding-bottom: 10px;font-size:22px ">999</div>
          <div style="color: gray;font-size: 13px">较前一日 888</div>
        </td>
      </tr>
    </table>
  </div>
  <div class="bottom">
    <table border="1" class="table">
      <tr>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">付款人数</div>
          <div style="padding-bottom: 10px;font-size:22px ">999</div>
          <div style="color: gray;font-size: 13px">较前一日 888</div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">访客数</div>
          <div style="padding-bottom: 10px;font-size:22px ">999</div>
          <div style="color: gray;font-size: 13px">较前一日 888</div>
        </td>
        <td style="display:table-cell; vertical-align:middle">
          <div style="padding-bottom: 10px">浏览量</div>
          <div style="padding-bottom: 10px;font-size:22px ">999</div>
          <div style="color: gray;font-size: 13px">付款订单数 888</div>
        </td>
      </tr>
    </table>
  </div>

  <div></div>
</div>
</template>

<script>
import {coreIndicator} from '@/api/admin/firstWebManage/realTimeOverview/realTimeOverview'

export default {
  data () {
    return {
      param: {
        screeningTime: 1
      },
      timeSelect: 1,
      timeRange: [
        { value: 1, label: '昨日' },
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' }
      ]

    }
  },
  created () {
    this.loadData()
  },
  mounted () {
  },
  methods: {
    // 页面初始化数据
    loadData () {
      coreIndicator(this.param).then(res => {
        console.log('coreIndicator:', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    handleData (content) {
      console.log('coreIndicatorVo:', content)
    },
    // 时间选择
    timeChangeHandler (time) {
      console.log('time is:', time)
      this.param.screeningTime = time
      this.loadData()
    }
  }
}
</script>

<style lang="scss" scoped>
  .coreIndicator {
    padding: 10px;
    background: #fff;
    .top{
      height: 50px;
      .left{
        float: left;
      }
      .right{
        padding-right: 20px;
        float: right;
      }
    }
    .mid{
      height: 500px;
      .table{
        margin: 40px;
        border: 1px gray solid;
        border-spacing: 0px;
        border-collapse: collapse;
        width: 1240px;
        height: 180px;
        table-layout: fixed;
      }
      .table td{
        border: 1px gray solid;
        padding-left: 80px;
      }
    }
    .bottom{
      height: 500px;
      .table{
        margin: 40px;
        border: 1px gray solid;
        border-spacing: 0px;
        border-collapse: collapse;
        width: 1240px;
        height: 180px;
        table-layout: fixed;
      }
      .table td{
        border: 1px gray solid;
        padding-left: 80px;
      }
    }
  }
</style>
