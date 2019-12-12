<template>
  <div class="yesterdayStatistics">
    <!--    标题-->
    <div class="text-title">
      昨日概况
    </div>
    <!--    表格数据-->
    <div class="table-data">
      <div
        class="fromItem"
        v-for="item in table"
        :key="item.name"
      >
        <div>{{item.name}}</div>
        <div
          class="num"
          style="color: #5A8BFF"
        >{{item.dataNumber}}
        </div>
        <div>日 {{item.dayRate}}</div>
        <div>月 {{item.monthRate}}</div>
        <div>周 {{item.weekRate}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import {yesterdayAnalysis} from '@/api/admin/firstWebManage/overviewStatistics/overviewStatistics.js'

export default {
  data () {
    return {
      table: [],
      originalData: []
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    loadData () {
      yesterdayAnalysis().then(res => {
        console.log('昨日概况数据', res.content)
        if (res.error === 0) {
          this.originalData = res.content
          this.table = [
            {
              name: '打开次数',
              dataNumber: this.originalData[0].dataNumber,
              dayRate: this.originalData[0].dayRate,
              weekRate: this.originalData[0].weekRate,
              monthRate: this.originalData[0].monthRate
            },
            {
              name: '访问次数',
              dataNumber: this.originalData[1].dataNumber,
              dayRate: this.originalData[1].dayRate,
              weekRate: this.originalData[1].weekRate,
              monthRate: this.originalData[1].monthRate
            },
            {
              name: '访问人数',
              dataNumber: this.originalData[2].dataNumber,
              dayRate: this.originalData[2].dayRate,
              weekRate: this.originalData[2].weekRate,
              monthRate: this.originalData[2].monthRate
            },
            {
              name: '新访问用户数',
              dataNumber: this.originalData[3].dataNumber,
              dayRate: this.originalData[3].dayRate,
              weekRate: this.originalData[3].weekRate,
              monthRate: this.originalData[3].monthRate
            },
            {
              name: '分享次数',
              dataNumber: this.originalData[4].dataNumber,
              dayRate: this.originalData[4].dayRate,
              weekRate: this.originalData[4].weekRate,
              monthRate: this.originalData[4].monthRate
            },
            {
              name: '分享人数',
              dataNumber: this.originalData[5].dataNumber,
              dayRate: this.originalData[5].dayRate,
              weekRate: this.originalData[5].weekRate,
              monthRate: this.originalData[5].monthRate
            }
          ]
        }
      }).catch(err => console.log(err))
    }
  }
}

</script>
<style lang="scss" scoped>
  .yesterdayStatistics {
    padding: 10px;
    background: #fff;

    .text-title {
      height: 50px;
      line-height: 50px;
      color: #333;
    }

    .table-data{
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
  }
</style>
