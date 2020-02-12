<template>
<div class="realTime">
  <div class="top">
    实时概况
  </div>
  <div class="down">
    <div class="left">
      <div style="padding-bottom: 10px">付款金额</div>
      <div style="color: gray;font-size: 13px">昨日全天: </div>
    </div>
    <div class="right">
      <table border="1" class="table">
        <tr>
          <td style="display:table-cell; vertical-align:middle">
            <div style="padding-bottom: 10px">访客数</div>
            <div style="padding-bottom: 10px">{{this.vo.visitUsers.e1}}</div>
            <div style="color: gray;font-size: 13px">昨日全天: {{this.vo.visitUsers.e2}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            <div style="padding-bottom: 10px">浏览量</div>
            <div style="padding-bottom: 10px">{{this.vo.pageViews.e1}}</div>
            <div style="color: gray;font-size: 13px">昨日全天: {{this.vo.pageViews.e2}}</div>
          </td>
        </tr>
        <tr>
          <td style="display:table-cell; vertical-align:middle">
            <div style="padding-bottom: 10px">付款订单数</div>
            <div style="padding-bottom: 10px">{{this.vo.payOrderNum.e1}}</div>
            <div style="color: gray;font-size: 13px">昨日全天: {{this.vo.payOrderNum.e2}}</div>
          </td>
          <td style="display:table-cell; vertical-align:middle">
            <div style="padding-bottom: 10px">付款人数</div>
            <div style="padding-bottom: 10px">{{this.vo.payUserNum.e1}}</div>
            <div style="color: gray;font-size: 13px">昨日全天: {{this.vo.payUserNum.e2}}</div>
          </td>
        </tr>
      </table>
    </div>
  </div>
</div>
</template>

<script>
import {realTime} from '@/api/admin/firstWebManage/realTimeOverview/realTimeOverview'

export default {
  data () {
    return {
      param: {
        screeningTime: 7
      },
      vo: ''
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    // 页面初始化数据
    loadData () {
      realTime(this.param).then(res => {
        console.log('realTime:', res)
        if (res.error === 0) {
          this.handleData(res.content)
        }
      }).catch(err => console.log(err))
    },
    handleData (content) {
      this.vo = content.e1
      console.log('vo:', this.vo)
    }
  }
}

</script>

<style lang="scss" scoped>
  .realTime {
    padding: 10px;
    background: #fff;
    .top{
      height: 24px;
      font-size: 18px;
    }
    .down{
      height: 300px;
      .left{
        padding-left: 20px;
        padding-top: 10px;
        float: left;
        height: 500px;
        width: 50%;
      }
      .right{
        float: left;
        height: 500px;
        width: 50%;
        .table{
          border: 1px gray solid;
          border-spacing: 0px;
          border-collapse: collapse;
          width: 650px;
          height: 280px;
          table-layout: fixed;
          /*text-align: center;*/
        }
        .table td{
          border: 1px gray solid;
          padding-left: 80px;
        }

        .table th{
          border: 1px gray solid;
        }
      }
    }

  }
</style>
