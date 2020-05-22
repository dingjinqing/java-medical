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
      >
        <div style="color: #9a9a9a">{{this.table[0].name}}</div>
        <div
          class="num"
          style="color: black"
        >{{this.table[0].dataNumber}}
        </div>
        <div class ="rate"><span style="color: #9a9a9a">日</span><span class="demo">{{this.table[0].dayRate}}</span></div>
        <div class ="rate"><span style="color: #9a9a9a">周</span> <span class="demo">{{this.table[0].weekRate}}</span></div>
        <div class ="rate"><span style="color: #9a9a9a">月</span> <span class="demo">{{this.table[0].monthRate}}</span></div>
      </div>
      <div
        class="fromItem"
      >
        <div style="color: #9a9a9a">{{this.table[1].name}}/{{this.table[2].name}}</div>
        <div
          class="num"
          style="color: black"
        >{{this.table[1].dataNumber}}/{{this.table[2].dataNumber}}
        </div>
        <div class ="rate"><span style="color: #9a9a9a">日</span> <span class="demo">{{this.table[1].dayRate}}</span> / <span class="demo">{{this.table[2].dayRate}}</span></div>
        <div class ="rate"><span style="color: #9a9a9a">周</span> <span class="demo">{{this.table[1].weekRate}}</span> / <span class="demo">{{this.table[2].weekRate}}</span></div>
        <div class ="rate"><span style="color: #9a9a9a">月</span> <span class="demo">{{this.table[1].monthRate}}</span> / <span class="demo">{{this.table[2].monthRate}}</span></div>
      </div>
      <div
        class="fromItem"
      >
        <div style="color: #9a9a9a">{{this.table[3].name}}</div>
        <div
          class="num"
          style="color: black"
        >{{this.table[3].dataNumber}}
        </div>
        <div class ="rate"><span style="color: #9a9a9a">日</span> {{this.table[3].dayRate}}</div>
        <div class ="rate"><span style="color: #9a9a9a">周</span> {{this.table[3].weekRate}}</div>
        <div class ="rate"><span style="color: #9a9a9a">月</span> {{this.table[3].monthRate}}</div>
      </div>
      <div
        class="fromItem"
      >
        <div style="color: #9a9a9a">{{this.table[4].name}}/{{this.table[5].name}}</div>
        <div
          class="num"
          style="color: black"
        >{{this.table[4].dataNumber}}/{{this.table[5].dataNumber}}
        </div>
        <div class ="rate"><span style="color: #9a9a9a">日</span> {{this.table[4].dayRate}} / {{this.table[5].dayRate}}</div>
        <div class ="rate"><span style="color: #9a9a9a">周</span> {{this.table[4].weekRate}} / {{this.table[5].weekRate}}</div>
        <div class ="rate"><span style="color: #9a9a9a">月</span> {{this.table[4].monthRate}} / {{this.table[5].monthRate}}</div>
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
    handleColor () {
      var numColor = document.getElementsByClassName('demo')
      console.log('数组:', numColor)
      console.log('数组长度:', numColor.length)
      for (var i = 0; i < numColor.length; i++) {
        console.log('单个:', numColor[i])
        console.log('循环内的数:', numColor[i].innerText)
        if (numColor[i].innerText === '-') {
          numColor[i].style.color = 'green'
        } else if (numColor[i].innerText.substring(0, numColor[i].innerText.length - 1) < 0) {
          console.log('循环内的数去掉百分号:', numColor[i].innerText.substring(0, numColor[i].innerText.length - 1))
          numColor[i].style.color = 'red'
        } else {
          numColor[i].style.color = 'blue'
        }
      }
    },
    handleNull (rate) {
      console.log(rate)
      if (rate == null) {
        return '-'
      } else {
        return rate + '%'
      }
      // console.log(rate)
      // return rate
    },
    loadData () {
      yesterdayAnalysis().then(res => {
        console.log('昨日概况数据', res.content)
        if (res.error === 0) {
          this.originalData = res.content
          this.table = [
            {
              name: '打开次数',
              dataNumber: this.originalData[0].dataNumber,
              dayRate: this.handleNull(this.originalData[0].dayRate),
              weekRate: this.handleNull(this.originalData[0].weekRate),
              monthRate: this.handleNull(this.originalData[0].monthRate)
            },
            {
              name: '访问次数',
              dataNumber: this.originalData[1].dataNumber,
              dayRate: this.handleNull(this.originalData[1].dayRate),
              weekRate: this.handleNull(this.originalData[1].weekRate),
              monthRate: this.handleNull(this.originalData[1].monthRate)
            },
            {
              name: '人数',
              dataNumber: this.originalData[2].dataNumber,
              dayRate: this.handleNull(this.originalData[2].dayRate),
              weekRate: this.handleNull(this.originalData[2].weekRate),
              monthRate: this.handleNull(this.originalData[2].monthRate)
            },
            {
              name: '新访问用户数',
              dataNumber: this.originalData[3].dataNumber,
              dayRate: this.handleNull(this.originalData[3].dayRate),
              weekRate: this.handleNull(this.originalData[3].weekRate),
              monthRate: this.handleNull(this.originalData[3].monthRate)
            },
            {
              name: '分享次数',
              dataNumber: this.originalData[4].dataNumber,
              dayRate: this.handleNull(this.originalData[4].dayRate),
              weekRate: this.handleNull(this.originalData[4].weekRate),
              monthRate: this.handleNull(this.originalData[4].monthRate)
            },
            {
              name: '人数',
              dataNumber: this.originalData[5].dataNumber,
              dayRate: this.handleNull(this.originalData[5].dayRate),
              weekRate: this.handleNull(this.originalData[5].weekRate),
              monthRate: this.handleNull(this.originalData[5].monthRate)
            }
          ]
          this.handleColor()
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
        .rate{
          margin-bottom: 5px;
        }
      }
    }
  }
</style>
