<template>
  <div>
    <!-- 商品概况 -->
    <div class="label">
      <div class="labelItem">商品概况</div>
      <el-form
        label-width="100px"
        :inline="true"
      >
        <el-form-item>
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
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-if="timeSelect===0"
            v-model="timeValue"
            type="daterange"
            size="small"
            @change="customDate"
            value-format="yyyyMMdd"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            class="custom"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <span>{{this.startDate.year}}{{$t('userStatistics.year')}}{{this.startDate.month}}{{$t('userStatistics.month')}}{{this.startDate.day}}{{$t('userStatistics.day')}} - {{this.endDate.year}}{{$t('userStatistics.year')}}{{this.endDate.month}}{{$t('userStatistics.month')}}{{this.endDate.day}}{{$t('userStatistics.day')}}</span>
        </el-form-item>
        <!--商家分类树-->
        <sortCatTreeSelect
          ref="sortTree"
          :filterGoodsInfo="initSortCatParams"
          treeType="sort"
          :selectedId.sync="overviewParam.sortId"
        />
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.goodsLabel')+'：'"
          prop="labelId"
        >
          <!--商品标签-->
          <el-select
            v-model="overviewParam.labelId"
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
        </el-form-item>
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.goodsBrand')+'：'"
          prop="brandId"
        >
          <!--商品品牌-->
          <el-select
            v-model="overviewParam.brandId"
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
        </el-form-item>
        <el-form-item>
          <el-button
            size="small"
            type="primary"
            @click="overviewInit"
          >搜索</el-button>
        </el-form-item>
      </el-form>
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
                  <div style="width: 70%;color: #353535">当前时间点，在架的商品数量</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.onShelfGoodsNum}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.onShelfGoodsNum > 0"
            >↑{{overviewVo.changeRate.onShelfGoodsNum}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.onShelfGoodsNum < 0"
            >↓{{Math.abs(overviewVo.changeRate.onShelfGoodsNum)}}%</span>
            <span v-if="overviewVo.changeRate.onShelfGoodsNum === 0">{{overviewVo.changeRate.onShelfGoodsNum}}%</span>
          </div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>动销商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">动销商品数</div>
                  <div style="width: 70%;color: #353535">统计时间内，销量不为0的商品数量</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.soldGoodsNum}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.soldGoodsNum > 0"
            >↑{{overviewVo.changeRate.soldGoodsNum}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.soldGoodsNum < 0"
            >↓{{Math.abs(overviewVo.changeRate.soldGoodsNum)}}%</span>
            <span v-if="overviewVo.changeRate.soldGoodsNum === 0">{{overviewVo.changeRate.soldGoodsNum}}%</span>
          </div>
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
            <div>被访问商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">被访问商品数</div>
                  <div style="width: 70%;color: #353535">被访问商品数</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.visitedGoodsNum}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.visitedGoodsNum > 0"
            >↑{{overviewVo.changeRate.visitedGoodsNum}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.visitedGoodsNum < 0"
            >↓{{Math.abs(overviewVo.changeRate.visitedGoodsNum)}}%</span>
            <span v-if="overviewVo.changeRate.visitedGoodsNum === 0">{{overviewVo.changeRate.visitedGoodsNum}}%</span>
          </div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>商品访客数 (商品UV)</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">商品访客数</div>
                  <div style="width: 70%;color: #353535">商品UV</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.goodsUserVisit}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.goodsUserVisit > 0"
            >↑{{overviewVo.changeRate.goodsUserVisit}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.goodsUserVisit < 0"
            >↓{{Math.abs(overviewVo.changeRate.goodsUserVisit)}}%</span>
            <span v-if="overviewVo.changeRate.goodsUserVisit === 0">{{overviewVo.changeRate.goodsUserVisit}}%</span>
          </div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>商品浏览量 (商品pv)</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">商品浏览量</div>
                  <div style="width: 70%;color: #353535">商品pv</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.goodsPageviews}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.goodsPageviews > 0"
            >↑{{overviewVo.changeRate.goodsPageviews}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.goodsPageviews < 0"
            >↓{{Math.abs(overviewVo.changeRate.goodsPageviews)}}%</span>
            <span v-if="overviewVo.changeRate.goodsPageviews === 0">{{overviewVo.changeRate.goodsPageviews}}%</span>
          </div>
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
            <div>加购人数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">加购人数</div>
                  <div style="width: 70%;color: #353535">加购人数</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.purchaseNum}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.purchaseNum > 0"
            >↑{{overviewVo.changeRate.purchaseNum}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.purchaseNum < 0"
            >↓{{Math.abs(overviewVo.changeRate.purchaseNum)}}%</span>
            <span v-if="overviewVo.changeRate.purchaseNum === 0">{{overviewVo.changeRate.purchaseNum}}%</span>
          </div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>加购件数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">加购件数</div>
                  <div style="width: 70%;color: #353535">加购件数</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.purchaseQuantity}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.purchaseQuantity > 0"
            >↑{{overviewVo.changeRate.purchaseQuantity}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.purchaseQuantity < 0"
            >↓{{Math.abs(overviewVo.changeRate.purchaseQuantity)}}%</span>
            <span v-if="overviewVo.changeRate.purchaseQuantity === 0">{{overviewVo.changeRate.purchaseQuantity}}%</span>
          </div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>下单商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">下单商品数</div>
                  <div style="width: 70%;color: #353535">下单商品数</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.orderGoodsNum}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.orderGoodsNum > 0"
            >↑{{overviewVo.changeRate.orderGoodsNum}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.orderGoodsNum < 0"
            >↓{{Math.abs(overviewVo.changeRate.orderGoodsNum)}}%</span>
            <span v-if="overviewVo.changeRate.orderGoodsNum === 0">{{overviewVo.changeRate.orderGoodsNum}}%</span>
          </div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>付款商品数</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">付款商品数</div>
                  <div style="width: 70%;color: #353535">付款商品数</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.paidGoodsNum}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.paidGoodsNum > 0"
            >↑{{overviewVo.changeRate.paidGoodsNum}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.paidGoodsNum < 0"
            >↓{{Math.abs(overviewVo.changeRate.paidGoodsNum)}}%</span>
            <span v-if="overviewVo.changeRate.paidGoodsNum === 0">{{overviewVo.changeRate.paidGoodsNum}}%</span>
          </div>
        </div>
        <div class="fromItem">
          <div
            class="fromInfo"
            style="display: flex;"
          >
            <div>商品访问付款转化率</div>
            <el-tooltip
              effect="light"
              placement="top"
            >
              <div
                slot="content"
                style="width: 400px;line-height: 30px;font-size: 14px;"
              >
                <section style="display: flex">
                  <div style="width: 30%;color:#999">商品访问付款转化率</div>
                  <div style="width: 70%;color: #353535">商品访问付款转化率</div>
                </section>
              </div>
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >{{overviewVo.visit2paid}}</div>
          <div>较前{{dayNum}}日
            <span
              style="color: #ff0808;"
              v-if="overviewVo.changeRate.visit2paid > 0"
            >↑{{overviewVo.changeRate.visit2paid}}%</span>
            <span
              style="color: #2fae44;"
              v-if="overviewVo.changeRate.visit2paid < 0"
            >↓{{Math.abs(overviewVo.changeRate.visit2paid)}}%</span>
            <span v-if="overviewVo.changeRate.visit2paid === 0">{{overviewVo.changeRate.visit2paid}}%</span>
          </div>
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
            size="small"
            @change="customRankDate"
            value-format="yyyyMMdd"
            range-separator="-"
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
              <el-button
                size="small"
                @click="unitChange(1)"
              >日</el-button>
              <el-button
                size="small"
                @click="unitChange(2)"
              >周</el-button>
              <el-button
                size="small"
                @click="unitChange(3)"
              >月</el-button>
              <el-button
                size="small"
                @click="unitChange(4)"
              >年</el-button>
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
                  v-for="(item, index) in goodsName"
                  :key="index"
                >{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item, index) in tableData"
                :key="index"
              >
                <td>{{refDate[index]}}</td>
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
            <div class="tl_title">商品销售订单TOP10</div>
            <div class="tl_change">
              <el-button
                size="small"
                @click="unitChange(5)"
              >日</el-button>
              <el-button
                size="small"
                @click="unitChange(6)"
              >周</el-button>
              <el-button
                size="small"
                @click="unitChange(7)"
              >月</el-button>
              <el-button
                size="small"
                @click="unitChange(8)"
              >年</el-button>
            </div>
          </div>
          <div class="tc_right">
            <el-button size="small">数据导出</el-button>
          </div>
        </div>
        <!-- 销售订单折线/柱状图 -->
        <div style="width: 85%;margin-left:7%">
          <div class="tc_right">
            <i
              class="el-icon-s-operation"
              @click="changeType1(0)"
            ></i>
            <i
              class="el-icon-s-data"
              @click="changeType1(1)"
            ></i>
          </div>
          <ve-chart
            :data="chartData1"
            :settings="chartSettings1"
          ></ve-chart>
        </div>
        <!-- 销售订单表格 -->
        <div class="table">
          <table class="table_content">
            <thead>
              <tr>
                <th></th>
                <th
                  v-for="(item, index) in goodsName1"
                  :key="index"
                >{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item, index) in tableData1"
                :key="index"
              >
                <td>{{refDate1[index]}}</td>
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
import { productoverview, getDate, productRanking } from '@/api/admin/firstWebManage/goodsStatistics/goodsStatistics.js'
import { getGoodsFilterItem } from '@/api/admin/goodsManage/allGoods/allGoods'
import sortCatTreeSelect from '@/components/admin/sortCatTreeSelect'
export default {
  props: ['initSortCatParams'],
  components: { sortCatTreeSelect },
  mounted () {
    // 初始化form表单下拉框数据
    this.initFilterData()
    // 初始化国际语言
    this.langDefault()
    this.getDateValue(1)
    this.overviewInit()
    this.rankInit()
  },
  data () {
    // 图表切换，index为0是折线图，为1是柱状图
    this.typeArr = ['line', 'histogram']
    this.index = 0
    this.index1 = 0
    return {
      // 销售额表格数据
      refDate: ['2020-10-10', '2020-10-11', '2020-10-12'],
      goodsName: ['花露水', '毛衣', '手机'],
      tableData: [
        [12, 13, 14],
        [13, 14, 12],
        [13, 14, 12]
      ],
      // 销售订单表格数据
      refDate1: ['2020-10-10', '2020-10-11', '2020-10-12'],
      goodsName1: ['花露水', '毛衣', '手机'],
      tableData1: [
        [12, 13, 14],
        [13, 14, 12],
        [13, 14, 12]
      ],
      search: '',
      timeSelect: 1,
      timeValue: [],
      rangeDate: [],
      timeRange: this.$t('tradesStatistics.timeRange'),
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
      overviewVo: {
        goodsPageviews: 0,
        goodsUserVisit: 0,
        onShelfGoodsNum: 0,
        orderGoodsNum: 0,
        paidGoodsNum: 0,
        purchaseNum: 0,
        purchaseQuantity: 0,
        soldGoodsNum: 0,
        visit2paid: 0,
        visitedGoodsNum: 0,
        changeRate: {
          goodsPageviews: 0,
          goodsUserVisit: 0,
          onShelfGoodsNum: 0,
          orderGoodsNum: 0,
          paidGoodsNum: 0,
          purchaseNum: 0,
          purchaseQuantity: 0,
          soldGoodsNum: 0,
          visit2paid: 0,
          visitedGoodsNum: 0
        }
      },
      overviewParam: {
        dynamicDate: 1,
        startTime: '',
        endTime: '',
        sortId: null,
        brandId: null,
        labelId: null
      },
      dayNum: 1,
      // 商品排行
      // 销售额折线/柱状图数据
      rankParam: {
        startTime: '',
        endTime: ''
      },
      chartData: {
        columns: [],
        rows: []
      },
      chartSettings: { type: this.typeArr[this.index] },
      chartData1: {
        columns: [],
        rows: []
      },
      chartSettings1: { type: this.typeArr[this.index1] },
      salesChar: Object,
      salesOrderChar: Object,
      salesTable: Object,
      salesOrderTable: Object
    }
  },
  methods: {
    getDateValue (unit) {
      getDate(unit).then(res => {
        if (res.error === 0) {
          this.startDate.year = res.content.startTime.split('-')[0]
          this.startDate.month = res.content.startTime.split('-')[1]
          this.startDate.day = res.content.startTime.split('-')[2]
          this.endDate.year = res.content.endTime.split('-')[0]
          this.endDate.month = res.content.endTime.split('-')[1]
          this.endDate.day = res.content.endTime.split('-')[2]
        }
      }).catch(err => console.log(err))
    },
    /* 商品概览-初始化商品品牌/标签下拉框数据 */
    initFilterData () {
      getGoodsFilterItem({ needGoodsLabel: true, needGoodsBrand: true }).then(res => {
        let { content } = res
        this.goodsBrandOptions = content.goodsBrands
        this.goodsLabelOptions = content.goodsLabels
      })
    },
    // 商品概览-指定时间段
    dateChangeHandler (time) {
      if (time !== 0) {
        this.overviewParam.dynamicDate = time
        this.getDateValue(time)
        this.overviewInit()
      }
    },
    // 商品概览-自定义时间
    customDate () {
      this.overviewParam.dynamicDate = 0
      this.overviewParam.startTime = this.timeValue[0].substring(0, 4) + '-' + this.timeValue[0].substring(4, 6) + '-' + this.timeValue[0].substring(6, 8) + ' 00:00:00'
      this.overviewParam.endTime = this.timeValue[1].substring(0, 4) + '-' + this.timeValue[1].substring(4, 6) + '-' + this.timeValue[1].substring(6, 8) + ' 00:00:00'
      this.startDate.year = this.timeValue[0].substring(0, 4)
      this.startDate.month = this.timeValue[0].substring(4, 6)
      this.startDate.day = this.timeValue[0].substring(6, 8)
      this.endDate.year = this.timeValue[1].substring(0, 4)
      this.endDate.month = this.timeValue[1].substring(4, 6)
      this.endDate.day = this.timeValue[1].substring(6, 8)
      console.log('选择器的时间：', this.param)
      this.overviewInit()
    },
    // 商品概览
    overviewInit () {
      productoverview(this.overviewParam).then(res => {
        if (res.error === 0) {
          this.overviewVo = res.content
          if (this.overviewVo.changeRate === null) {
            this.overviewVo.changeRate = {
              goodsPageviews: 0,
              goodsUserVisit: 0,
              onShelfGoodsNum: 0,
              orderGoodsNum: 0,
              paidGoodsNum: 0,
              purchaseNum: 0,
              purchaseQuantity: 0,
              soldGoodsNum: 0,
              visit2paid: 0,
              visitedGoodsNum: 0
            }
          }
        }
      }).catch(err => console.log(err))
    },
    // 商品排行-自定义时间
    customRankDate () {
      this.rankParam.startTime = this.rangeDate[0].substring(0, 4) + '-' + this.rangeDate[0].substring(4, 6) + '-' + this.rangeDate[0].substring(6, 8)
      this.rankParam.endTime = this.rangeDate[1].substring(0, 4) + '-' + this.rangeDate[1].substring(4, 6) + '-' + this.rangeDate[1].substring(6, 8)
      this.rankInit()
    },
    rankInit () {
      productRanking(this.rankParam).then(res => {
        if (res.error === 0) {
          this.chartData = res.content.salesChar.DAY_CHAR_DATA
          this.chartData1 = res.content.salesOrderChar.DAY_CHAR_DATA

          // this.refDate = res.content.salesTable.DAY_TABLE_DATA.refDate
          // this.goodsName = res.content.salesTable.DAY_TABLE_DATA.goodsName
          // this.tableData = res.content.salesTable.DAY_TABLE_DATA.arrayData
          // this.refDate1 = res.content.salesOrderTable.DAY_TABLE_DATA.refDate
          // this.goodsName1 = res.content.salesOrderTable.DAY_TABLE_DATA.goodsName
          // this.tableData1 = res.content.salesOrderTable.DAY_TABLE_DATA.arrayData

          this.salesChar = res.content.salesChar
          this.salesOrderChar = res.content.salesOrderChar
          this.salesTable = res.content.salesTable
          this.salesOrderTable = res.content.salesOrderTable
          this.unitChange(1)
          this.unitChange(5)
        }
      }).catch(err => console.log(err))
    },
    // 销售额：日，周，月，年（1，2，3，4）；销售订单：日，周，月，年（5，6，7，8）
    unitChange (index) {
      switch (index) {
        case 1:
          this.chartData = this.salesChar.DAY_CHAR_DATA
          this.refDate = this.salesTable.DAY_TABLE_DATA.refDate
          this.goodsName = this.salesTable.DAY_TABLE_DATA.goodsName
          this.tableData = this.salesTable.DAY_TABLE_DATA.arrayData
          break
        case 2:
          this.chartData = this.salesChar.WEEK_CHAR_DATA
          this.refDate = this.salesTable.WEEK_TABLE_DATA.refDate
          this.goodsName = this.salesTable.WEEK_TABLE_DATA.goodsName
          this.tableData = this.salesTable.WEEK_TABLE_DATA.arrayData
          break
        case 3:
          this.chartData = this.salesChar.MONTH_CHAR_DATA
          this.refDate = this.salesTable.MONTH_TABLE_DATA.refDate
          this.goodsName = this.salesTable.MONTH_TABLE_DATA.goodsName
          this.tableData = this.salesTable.MONTH_TABLE_DATA.arrayData
          break
        case 4:
          this.chartData = this.salesChar.YEAR_CHAR_DATA
          this.refDate = this.salesTable.YEAR_TABLE_DATA.refDate
          this.goodsName = this.salesTable.YEAR_TABLE_DATA.goodsName
          this.tableData = this.salesTable.YEAR_TABLE_DATA.arrayData
          break
        case 5:
          this.chartData1 = this.salesOrderChar.DAY_CHAR_DATA
          this.refDate1 = this.salesOrderTable.DAY_TABLE_DATA.refDate
          this.goodsName1 = this.salesOrderTable.DAY_TABLE_DATA.goodsName
          this.tableData1 = this.salesOrderTable.DAY_TABLE_DATA.arrayData
          break
        case 6:
          this.chartData1 = this.salesOrderChar.WEEK_CHAR_DATA
          this.refDate1 = this.salesOrderTable.WEEK_TABLE_DATA.refDate
          this.goodsName1 = this.salesOrderTable.WEEK_TABLE_DATA.goodsName
          this.tableData1 = this.salesOrderTable.WEEK_TABLE_DATA.arrayData
          break
        case 7:
          this.chartData1 = this.salesOrderChar.MONTH_CHAR_DATA
          this.refDate1 = this.salesOrderTable.MONTH_TABLE_DATA.refDate
          this.goodsName1 = this.salesOrderTable.MONTH_TABLE_DATA.goodsName
          this.tableData1 = this.salesOrderTable.MONTH_TABLE_DATA.arrayData
          break
        case 8:
          this.chartData1 = this.salesOrderChar.YEAR_CHAR_DATA
          this.refDate1 = this.salesOrderTable.YEAR_TABLE_DATA.refDate
          this.goodsName1 = this.salesOrderTable.YEAR_TABLE_DATA.goodsName
          this.tableData1 = this.salesOrderTable.YEAR_TABLE_DATA.arrayData
          break
      }
    },
    // 图表切换
    changeType (index) {
      this.index = index
      this.chartSettings = { type: this.typeArr[this.index] }
    },
    // 图表切换
    changeType1 (index) {
      this.index1 = index
      this.chartSettings1 = { type: this.typeArr[this.index1] }
    },
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (index, row) {
      console.log(index, row)
    }
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
  min-height: 100px;
  max-height: 600px;
  overflow: auto;
  margin-top: 20px;
  margin-left: 15px;
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
      height: 40px;
      line-height: 40px;
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
