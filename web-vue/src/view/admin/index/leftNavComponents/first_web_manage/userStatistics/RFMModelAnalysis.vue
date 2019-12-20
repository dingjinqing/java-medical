<template>
  <div class="modelAnalysis">
    <div class="modelAnalysis_content">
      <div class="description">{{$t('userStatistics.RFMInstructions')}}</div>
      <div class="payTime">
        <span>{{$t('userStatistics.lastPayTime')}} </span>
        <el-date-picker
          v-model="payTime"
          type="date"
          size="small"
          :placeholder="$t('userStatistics.selectDte')"
          class="inputTime"
          value-format="yyyy-MM-dd"
          @change="changeTime"
        >
        </el-date-picker>
        <span>{{$t('userStatistics.beforeTradeData')}}</span>
      </div>
      <div class="queryIndex">
        {{$t('userStatistics.queryIndex')}}
        <el-radio-group
          v-model="queryIndex"
          v-for="(item,index) in queryList"
          :key="index"
          style="line-height: 30px"
        >
          <div class="radioSelect">
            <el-radio :label=index+1>{{item.name}}</el-radio>
            <div>
              <el-tooltip
                effect="light"
                placement="top"
              >
                <div slot="content">{{item.content}}</div>
                <img
                  :src="$imageHost + '/image/admin/analysis_tishi.png'"
                  alt=""
                  class="icon"
                >
              </el-tooltip>
            </div>
          </div>
        </el-radio-group>
        <el-button
          size="small"
          type="primary"
          class="searchBtn"
          @click="handleSearch"
        >{{$t('userStatistics.search')}}</el-button>
      </div>
      <div class="table">
        <table class="table_content">
          <thead>
            <tr>
              <th style="width: 200px;">
                <div class="out">
                  <b>{{$t('userStatistics.consumeFrequency')}}</b>
                  <em>{{$t('userStatistics.lastCustomeTime')}}</em>
                </div>
              </th>
              <th>F=1</th>
              <th>F=2</th>
              <th>F=3</th>
              <th>F=4</th>
              <th>F>=5</th>
              <th>{{$t('userStatistics.summation')}}</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>R&lt;=5</td>
              <td
                style="background-color: #fff8ed;"
                v-for="item in this.table.row1"
                :key="item.userRate+Math.random()"
              >
                <div v-if="queryIndex === 1">
                  <div>{{item.payUserNum}}</div>
                  <div>{{item.userRate}}</div>
                </div>
                <div v-if="queryIndex === 2">{{item.totalPaidMoney}}</div>
                <div v-if="queryIndex === 3">{{item.price === null ? '--' : item.price}}</div>
              </td>
            </tr>
            <tr>
              <td>5&lt;R&lt;=10</td>
              <td
                style="background-color: #f2f9ff;"
                v-for="item in this.table.row2"
                :key="item.userRate+Math.random()"
              >
                <div v-if="queryIndex === 1">
                  <div>{{item.payUserNum}}</div>
                  <div>{{item.userRate}}</div>
                </div>
                <div v-if="queryIndex === 2">{{item.totalPaidMoney.toFixed(2)}}</div>
                <div v-if="queryIndex === 3">{{item.price === null ? '--' : item.price}}</div>
              </td>
              <!-- <td style="background-color: #f2f9ff;"></td>
              <td style="background-color: #fff8ed;">2</td>
              <td style="background-color: #ffedd2;">3</td>
              <td style="background-color: #fee2b7;">4</td>
              <td style="background-color: #fed494;">5</td>
              <td>34</td> -->
            </tr>
            <tr>
              <td>10&lt;R&lt;=30</td>
              <td
                style="background-color: #e2f2ff;"
                v-for="item in this.table.row3"
                :key="item.userRate+Math.random()"
              >
                <div v-if="queryIndex === 1">
                  <div>{{item.payUserNum}}</div>
                  <div>{{item.userRate}}</div>
                </div>
                <div v-if="queryIndex === 2">{{item.totalPaidMoney}}</div>
                <div v-if="queryIndex === 3">{{item.price === null ? '--' : item.price}}</div>
              </td>
              <!-- <td style="background-color: #f2f9ff;">2</td>
              <td style="background-color: #fff8ed;">3</td>
              <td style="background-color: #ffedd2;">4</td>
              <td style="background-color: #fee2b7;">5</td>
              <td>34</td> -->
            </tr>
            <tr>
              <td>30&lt;R&lt;=90</td>
              <td
                style="background-color: #cbe6ff;"
                v-for="item in this.table.row4"
                :key="item.userRate+Math.random()"
              >
                <div v-if="queryIndex === 1">
                  <div>{{item.payUserNum}}</div>
                  <div>{{item.userRate}}</div>
                </div>
                <div v-if="queryIndex === 2">{{item.totalPaidMoney}}</div>
                <div v-if="queryIndex === 3">{{item.price === null ? '--' : item.price}}</div>
              </td>
              <!-- <td style="background-color: #e2f2ff;">2</td>
              <td style="background-color: #f2f9ff;">3</td>
              <td style="background-color: #fff8ed;">4</td>
              <td style="background-color: #ffedd2;">5</td>
              <td style="">34</td> -->
            </tr>
            <tr>
              <td style="">90&lt;R&lt;=180</td>
              <td
                style="background-color: #b4dbff;"
                v-for="item in this.table.row5"
                :key="item.userRate+Math.random()"
              >
                <div v-if="queryIndex === 1">
                  <div>{{item.payUserNum}}</div>
                  <div>{{item.userRate}}</div>
                </div>
                <div v-if="queryIndex === 2">{{item.totalPaidMoney}}</div>
                <div v-if="queryIndex === 3">{{item.price === null ? '--' : item.price}}</div>
              </td>
              <!-- <td style="background-color: #cbe6ff;">2</td>
              <td style="background-color: #e2f2ff;">3</td>
              <td style="background-color: #f2f9ff;">4</td>
              <td style="background-color: #fff8ed;">5</td>
              <td>34</td> -->
            </tr>
            <tr>
              <td>180&lt;R&lt;=365</td>
              <td
                style="background-color: #9bd0ff;"
                v-for="item in this.table.row6"
                :key="item.userRate+Math.random()"
              >
                <div v-if="queryIndex === 1">
                  <div>{{item.payUserNum}}</div>
                  <div>{{item.userRate}}</div>
                </div>
                <div v-if="queryIndex === 2">{{item.totalPaidMoney}}</div>
                <div v-if="queryIndex === 3">{{item.price === null ? '--' : item.price}}</div>
              </td>
              <!-- <td style="background-color: #b4dbff;">2</td>
              <td style="background-color: #cbe6ff;">3</td>
              <td style="background-color: #e2f2ff;">4</td>
              <td style="background-color: #f2f9ff;">5</td>
              <td>34</td> -->
            </tr>
            <tr>
              <td>R&gt;365</td>
              <td
                style="background-color: #7cc2ff;"
                v-for="item in this.table.row7"
                :key="item.userRate+Math.random()"
              >
                <div v-if="queryIndex === 1">
                  <div>{{item.payUserNum}}</div>
                  <div>{{item.userRate}}</div>
                </div>
                <div v-if="queryIndex === 2">{{item.totalPaidMoney}}</div>
                <div v-if="queryIndex === 3">{{item.price === null ? '--' : item.price}}</div>
              </td>
              <!-- <td style="background-color: #9bd0ff;">2</td>
              <td style="background-color: #b4dbff;">3</td>
              <td style="background-color: #cbe6ff;">4</td>
              <td style="background-color: #e2f2ff;">5</td>
              <td>34</td> -->
            </tr>
            <tr>
              <td>{{$t('userStatistics.summation')}}</td>
              <td
                v-for="item in this.table.row8"
                :key="item.userRate+Math.random()"
              >
                <div v-if="queryIndex === 1">
                  <div>{{item.payUserNum}}</div>
                  <div>{{item.userRate}}</div>
                </div>
                <div v-if="queryIndex === 2">{{item.totalPaidMoney}}</div>
                <div v-if="queryIndex === 3">{{item.price === null ? '--' : item.price}}</div>
              </td>
              <!--
              <td>2</td>
              <td>3</td>
              <td>4</td>
              <td>5</td>
              <td>34</td> -->
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { RFMmodelAnalysis } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  computed: {
    dateDefault () {
      var date = new Date()
      var s1 = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + (date.getDate())
      return s1
    }
  },

  watch: {
    lang () {
      this.queryList = this.$t('userStatistics.queryList')
    }
  },

  created () {
    this.payTime = this.dateDefault
  },
  mounted () {
    this.fetchTableData()
  },
  data () {
    return {
      payTime: '',
      params: {
        currentRow: '',
        rate: '',
        totalPayMoney: '',
        singlePrice: ''
      },
      table: {
        row1: [],
        row2: [],
        row3: [],
        row4: [],
        row5: [],
        row6: [],
        row7: []
      },
      queryIndex: 1,
      queryList: this.$t('userStatistics.queryList')
    }
  },
  methods: {
    // 查询按钮 -> 获取table表格的数据
    fetchTableData () {
      RFMmodelAnalysis({ 'refDate': this.payTime }).then(res => {
        if (res.error === 0) {
          console.log(res.content)
          // res.content.map(item => {
          //   console.log(item)
          //   this.params.currentRow = item
          //   console.log(this.params.currentRow, 'item')
          // })
          this.table.row1 = res.content[0].rfmRowVo
          this.table.row2 = res.content[1].rfmRowVo
          this.table.row3 = res.content[2].rfmRowVo
          this.table.row4 = res.content[3].rfmRowVo
          this.table.row5 = res.content[4].rfmRowVo
          this.table.row6 = res.content[5].rfmRowVo
          this.table.row7 = res.content[6].rfmRowVo
          this.table.row8 = res.content[7].rfmRowVo

          console.log(this.table.row2, 'row2')
        }
      }).catch(err => console.log(err))
    },
    // 改变支付时间
    changeTime (newTime) {
      this.payTime = newTime
      this.fetchTableData()
    },
    handleSearch () {

    }
  }
}

</script>
<style lang="scss" scoped>
.modelAnalysis {
  padding: 10px 10px 20px 0;
  font-size: 14px;
  .modelAnalysis_content {
    .description {
      padding: 10px 15px;
      background-color: #ebf1ff;
      border-radius: 2px;
      color: #5a8bff;
    }
    .payTime {
      display: flex;
      margin: 20px 0;
      .inputTime {
        width: 160px;
        margin: 0 5px;
      }
      span {
        line-height: 30px;
      }
    }
    .queryIndex {
      .radioSelect {
        display: flex;
        line-height: 30px;
        height: 30px;
        margin-right: 20px;
        .el-radio {
          line-height: 25px;
          margin-right: 5px;
        }
        .icon {
          width: 15px;
          height: 15px;
          margin-top: 6px;
        }
      }
      .searchBtn {
        position: absolute;
        right: 20px;
      }
    }
    .table {
      margin-top: 20px;
      border: 1px solid #efedee;
      .table_content {
        width: 100%;
        tr {
          th {
            height: 60px;
            line-height: 60px;
            background: #f5f5f5;
            border: 1px solid #efedee;
          }
        }
        td {
          text-align: center;
          height: 60px;
          line-height: 60px;
          border: 1px solid #efedee;
        }
        .out {
          width: 200px;
          height: 60px;
          position: relative;
        }
        .out::before {
          content: "";
          position: absolute;
          left: 25px;
          top: 10px;
          width: 74%;
          box-sizing: border-box;
          border-bottom: 1px solid #e5e5e5;
          transform-origin: bottom center;
          transform: rotateZ(16deg) scale(1.414);
        }
      }
    }
  }
}
</style>
