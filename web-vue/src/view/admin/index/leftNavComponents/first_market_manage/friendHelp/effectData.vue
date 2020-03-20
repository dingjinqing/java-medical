<template>
  <div class="effectData">
    <div class="top">
      这里是时间
    </div>
    <div class="mid">
      <table border="1" class="table">
        <tr>
          <td style="display:table-cell; vertical-align:middle">
            发起用户数
            <div style="padding-top: 15px;padding-left: 20px;font-size:25px;color: #2766f6 ">{{this.launchTotal}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            帮助力用户数
            <div style="padding-top: 15px;padding-left: 20px;font-size:25px;color: #e666a9 ">{{this.promoteTotal}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            助力成功用户数
            <div style="padding-top: 15px;padding-left: 20px;font-size:25px;color: #ff7a24 ">{{this.successTotal}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            拉新用户数
            <div style="padding-top: 15px;padding-left: 20px;font-size:25px;color: #39c840 ">{{this.newUserTotal}}</div>
          </td>
        </tr>
      </table>
    </div>
    <div class="bottom">
这里是折线图
      <!--    第二个折线图-->
<!--      <div id="secondCharts" v-show="this.controlShow"></div>-->
<!--      <div v-show="!this.controlShow"><span>暂无数据</span></div>-->
    </div>

    <div></div>
  </div>
</template>

<script>
import { effectData } from '@/api/admin/marketManage/friendHelp.js'

export default {
  data () {
    return {
      param: {
        id: '',
        startTime: '',
        endTime: ''
      },
      launchTotal: '',
      promoteTotal: '',
      successTotal: '',
      newUserTotal: ''
    }
  },
  created () {
    console.log('加载页面：', this.param)
    this.param.id = this.$route.params.id
    this.loadData()
  },
  mounted () {
    this.param.id = this.$route.params.id
    this.loadData()
  },
  methods: {
    loadData () {
      effectData(this.param).then(res => {
        console.log('res???', res)
        this.launchTotal = res.content.launchTotal
        this.promoteTotal = res.content.promoteTotal
        this.successTotal = res.content.successTotal
        this.newUserTotal = res.content.newUserTotal
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .effectData {
    padding: 10px;
    min-width: 100%;
    font-size: 14px;
    height: 100%;
  .top{
    background-color: white;
    height: 100px;
  }
  .mid{
    background-color: white;
    height: 250px;
  .table{
    margin-left: 45px;
    border: 1px solid#45c3ec;
    border-spacing: 0px;
    border-collapse: collapse;
    width: 1200px;
    height: 160px;
    table-layout: fixed;
  }
  .table td{
    border: 1px gray solid;
    padding-left: 80px;
  }
  }
  .bottom{
    background-color: white;
    height: 550px;

  }
  }
</style>
