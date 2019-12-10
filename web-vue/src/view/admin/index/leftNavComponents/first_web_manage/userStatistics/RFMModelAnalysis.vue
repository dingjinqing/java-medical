<template>
  <div class="modelAnalysis">
    <div class="modelAnalysis_content">
      <div class="description">通过分析用户的最近消费时间(R)、消费频次(F)、消费金额(M)，可有效将用户分层。消费频次越大、消费金额越高的客户，其客户生命价值(LTV)越高。通过营销活动、会员体系等运营 方式，可有效提升用户贡献。</div>
      <div class="payTime">
        <span>最近付款时间在 </span>
        <el-date-picker
          v-model="payTime"
          type="date"
          size="small"
          placeholder="选择日期"
          class="inputTime"
          value-format="yyyy-MM-dd"
          @change="changeTime"
        >
        </el-date-picker>
        <span>之前的成交客户数据</span>
      </div>
      <div class="queryIndex">
        查询指标：
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
        >查询</el-button>
      </div>
      <div class="table">
        <table class="table_content">
          <thead>
            <tr>
              <th style="width: 200px;">
                <div class="out">
                  <b>消费频次(F)</b>
                  <em>最近消费时间(R)</em>
                </div>
              </th>
              <th>F=1</th>
              <th>F=2</th>
              <th>F=3</th>
              <th>F=4</th>
              <th>F>=5</th>
              <th>合计</th>
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
              <td>合计</td>
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
      queryList: [
        { name: '用户数/占比', content: '在查询时间内各类已成交的用户数/占比' },
        { name: '累计支付金额(元) ', content: '在查询时间内各类用户累计支付金额' },
        { name: '客单价', content: '在查询时间内各区间的客单价' }
      ]
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
      // display: flex;
      // span {
      //   display: block;
      //   height: 30px;
      //   line-height: 30px;
      // }
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
