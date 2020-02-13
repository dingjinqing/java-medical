<template>
  <div>
    <!-- 商品概况 -->
    <div class="label">
      <div class="labelItem">商品概况</div>
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
      <span>{{this.startDate.year}}{{$t('userStatistics.year')}}{{this.startDate.month}}{{$t('userStatistics.month')}}{{this.startDate.day}}{{$t('userStatistics.day')}} - {{this.endDate.year}}{{$t('userStatistics.year')}}{{this.endDate.month}}{{$t('userStatistics.month')}}{{this.endDate.day}}{{$t('userStatistics.day')}}</span>
      <!--商家分类树-->
      <sortCatTreeSelect
        ref="sortTree"
        :filterGoodsInfo="initSortCatParams"
        treeType="sort"
        :selectedId.sync="goodsFilterFormData.sortId"
      />
      <!--商品标签-->
      <el-select
        v-model="goodsFilterFormData.labelId"
        :style="goodsFilterInputStyle"
        size="small"
      >
        <el-option
          :label="$t('allGoods.allGoodsHeaderData.chooseGoodsLabel')"
          :value="null"
        />
        <el-option
          v-for="(item,index) in goodsLabelOptions"
          :label="item.name"
          :value="item.id"
          :key="index"
        />
      </el-select>
      <!--商品品牌-->
      <el-select
        v-model="goodsFilterFormData.brandId"
        :style="goodsFilterInputStyle"
        size="small"
      >
        <el-option
          :label="$t('allGoods.allGoodsHeaderData.chooseGoodsBrand')"
          :value="null"
        />
        <el-option
          v-for="(item, index) in goodsBrandOptions"
          :label="item.brandName"
          :value="item.id"
          :key="index"
        />
      </el-select>
      <!-- 表格数据部分 -->
      <!-- 商品分布 -->
      <div
        class="fromWrapper"
        style="background-color: rgba(90, 139, 255, 0.1)"
      >
        <div class="fromItem">
          <div
            class="cateTitle"
            style="color: #5A8BFF"
          >商品<br>分布</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>访客数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">访客数</div>
                  <div style="width: 70%;color: #353535">访客数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">访客数占比</div>
                  <div style="width: 70%;color: #353535">访客数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>在架商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数</div>
                  <div style="width: 70%;color: #353535">在架商品数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数占比</div>
                  <div style="width: 70%;color: #353535">在架商品数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
      </div>
      <!-- 商品访问 -->
      <div
        class="fromWrapper"
        style="background-color: rgba(47, 174, 68, 0.1)"
      >
        <div class="fromItem">
          <div
            class="cateTitle"
            style="color: rgb(47, 174, 68)"
          >商品<br>访问</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>访客数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">访客数</div>
                  <div style="width: 70%;color: #353535">访客数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">访客数占比</div>
                  <div style="width: 70%;color: #353535">访客数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>在架商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数</div>
                  <div style="width: 70%;color: #353535">在架商品数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数占比</div>
                  <div style="width: 70%;color: #353535">在架商品数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>在架商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数</div>
                  <div style="width: 70%;color: #353535">在架商品数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数占比</div>
                  <div style="width: 70%;color: #353535">在架商品数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
      </div>
      <!-- 商品转化 -->
      <div
        class="fromWrapper"
        style="background-color: rgba(255, 175, 90, 0.1)"
      >
        <div class="fromItem">
          <div
            class="cateTitle"
            style="color: #ffaf5a"
          >商品<br>转化</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>访客数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">访客数</div>
                  <div style="width: 70%;color: #353535">访客数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">访客数占比</div>
                  <div style="width: 70%;color: #353535">访客数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>在架商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数</div>
                  <div style="width: 70%;color: #353535">在架商品数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数占比</div>
                  <div style="width: 70%;color: #353535">在架商品数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>在架商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数</div>
                  <div style="width: 70%;color: #353535">在架商品数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数占比</div>
                  <div style="width: 70%;color: #353535">在架商品数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>在架商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数</div>
                  <div style="width: 70%;color: #353535">在架商品数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数占比</div>
                  <div style="width: 70%;color: #353535">在架商品数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>在架商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数</div>
                  <div style="width: 70%;color: #353535">在架商品数解释</div>
                </section>
                <section style="display: flex">
                  <div style="width: 30%;color:#999">在架商品数占比</div>
                  <div style="width: 70%;color: #353535">在架商品数占比解释</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >100</div>
          <div>占比 ↑34.78%</div>
        </div>
      </div>
    </div>
    <!-- 商品排行 -->
    <div class="label">
      <div
        class="labelItem"
        style="display: flex;"
      >
        <div class="tc_left">商品排行</div>
        <div class="tc_right">
          查询时间
          <el-date-picker
            v-model="rangeDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </div>
      </div>
      <!-- 销售额TOP10 -->
      <div class="goodsRanking">
        <!-- 销售额TOP10 -->
        <div class="tc_content">
          <div class="tc_left">
            <div class="tl_title">商品销售额TOP10</div>
            <div class="tl_change">
              <el-button size="small">日</el-button>
              <el-button size="small">周</el-button>
              <el-button size="small">月</el-button>
              <el-button size="small">年</el-button>
            </div>
          </div>
          <div class="tc_right">
            <el-button size="small">数据导出</el-button>
          </div>
        </div>
        <!-- 销售额折线/柱状图 -->
        <div style="width: 85%;margin-left:7%">
          <div class="tc_right">
            <i
              class="el-icon-s-operation"
              @click="changeType(0)"
            ></i>
            <i
              class="el-icon-s-data"
              @click="changeType(1)"
            ></i>
          </div>
          <ve-chart
            :data="chartData"
            :settings="chartSettings"
          ></ve-chart>
        </div>
        <!-- 销售额表格 -->
        <div class="table">
          <table class="table_content">
            <thead>
              <tr>
                <th></th>
                <th
                  v-for="(item, index) in refDate"
                  :key="index"
                >{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item, index) in tableData"
                :key="index"
              >
                <td>{{goodsName[index]}}</td>
                <td
                  v-for="(item1, index1) in item"
                  :key="index1"
                >{{item1}}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 销售订单TOP10 -->
      <div class="goodsRanking">
        <!-- 销售订单TOP10 -->
        <div class="tc_content">
          <div class="tc_left">
            <div class="tl_title">商品销售额TOP10</div>
            <div class="tl_change">
              <el-button size="small">日</el-button>
              <el-button size="small">周</el-button>
              <el-button size="small">月</el-button>
              <el-button size="small">年</el-button>
            </div>
          </div>
          <div class="tc_right">
            <el-button size="small">数据导出</el-button>
          </div>
        </div>
        <!-- 销售额折线/柱状图 -->
        <div style="width: 85%;margin-left:7%">
          <div class="tc_right">
            <i
              class="el-icon-s-operation"
              @click="changeType(0)"
            ></i>
            <i
              class="el-icon-s-data"
              @click="changeType(1)"
            ></i>
          </div>
          <ve-chart
            :data="chartData"
            :settings="chartSettings"
          ></ve-chart>
        </div>
        <!-- 销售额表格 -->
        <div class="table">
          <table class="table_content">
            <thead>
              <tr>
                <th></th>
                <th
                  v-for="(item, index) in refDate"
                  :key="index"
                >{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item, index) in tableData"
                :key="index"
              >
                <td>{{goodsName[index]}}</td>
                <td
                  v-for="(item1, index1) in item"
                  :key="index1"
                >{{item1}}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 组件导入
import sortCatTreeSelect from '@/components/admin/sortCatTreeSelect'
export default {
  components: { sortCatTreeSelect },
  data () {
    // 图表切换，index为0是折线图，为1是柱状图
    this.typeArr = ['line', 'histogram']
    this.index = 0
    return {
      // 销售额表格数据
      refDate: ['2020-10-10', '2020-10-11', '2020-10-12'],
      goodsName: ['花露水', '毛衣', '手机'],
      tableData: [
        [12, 13, 14],
        [13, 14, 12],
        [13, 14, 12]
      ],
      search: '',
      timeSelect: 1,
      timeRange: this.$t('userStatistics.timeRange'),
      startDate: {
        year: '',
        month: '',
        day: ''
      },
      endDate: {
        year: '',
        month: '',
        day: ''
      },
      // 商品相关下拉框
      // 查询过滤对象
      goodsFilterFormData: {
        // 商家分类
        sortId: null,
        // 标签id
        labelId: null,
        // 品牌id
        brandId: null
      },
      goodsBrandOptions: [],
      goodsLabelOptions: [],
      // 商品排行
      rangeDate: '',
      // 销售额折线/柱状图数据
      chartData: {
        columns: ['日期', '访问用户', '下单用户', '下单率'],
        rows: [
          { '日期': '1/1', '访问用户': 1393, '下单用户': 1093, '下单率': 0.32 },
          { '日期': '1/2', '访问用户': 3530, '下单用户': 3230, '下单率': 0.26 },
          { '日期': '1/3', '访问用户': 2923, '下单用户': 2623, '下单率': 0.76 },
          { '日期': '1/4', '访问用户': 1723, '下单用户': 1423, '下单率': 0.49 },
          { '日期': '1/5', '访问用户': 3792, '下单用户': 3492, '下单率': 0.323 },
          { '日期': '1/6', '访问用户': 4593, '下单用户': 4293, '下单率': 0.78 }
        ]
      },
      chartSettings: { type: this.typeArr[this.index] }
    }
  },
  methods: {
    // 图表切换
    changeType (index) {
      this.index = index
      this.chartSettings = { type: this.typeArr[this.index] }
    },
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (index, row) {
      console.log(index, row)
    },
    dateChangeHandler () { }
  }
}

</script>
<style lang="scss" scoped>
.label {
  padding: 10px;
  background: #fff;
  margin-top: 5px;
  .labelItem {
    height: 50px;
    line-height: 50px;
    color: #333;
    margin-left: 10px;
    .tc_left {
      flex: 1;
      display: flex;
      align-items: center;
    }
    .tc_right {
      flex: 1;
      text-align: right;
      margin-right: 15px;
    }
  }
  .timeSelect {
    width: 140px;
    margin: 0 10px 10px 10px;
  }
}
.goodsRanking {
  margin-top: 5px;
  margin-bottom: 20px;
  .tc_content {
    background-color: #f5f5f5;
    height: 56px;
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    .tc_left {
      flex: 1;
      display: flex;
      align-items: center;
      .tl_title {
        margin-left: 15px;
        border-left: 2px solid #5a8bff;
        padding-left: 10px;
        margin-right: 30px;
      }
      .tl_change {
        display: flex;
      }
    }
    .tc_right {
      flex: 1;
      text-align: right;
      margin-right: 15px;
    }
  }
  .tc_right {
    flex: 1;
    text-align: right;
    margin-right: 15px;
  }
}
.fromWrapper {
  // border: 1px solid #eee;
  height: 110px;
  width: 98%;
  display: flex;
  justify-content: left;
  align-items: left;
  margin-left: 10px;
  margin-top: 5px;
  .fromItem {
    height: 110px;
    width: 200px;
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
    .cateTitle {
      margin-top: 15px;
      font-size: 20px;
    }
    :nth-of-type(3) {
      margin-top: 10px;
    }
  }
}
.table {
  width: 98%;
  margin-top: 20px;
  margin-left: 15px;
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
</style>
