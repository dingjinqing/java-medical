<template>
  <div class="choosingGoods_Container">
    <!--选择商品弹窗-->
    <el-dialog
      title="选择商品"
      :visible.sync="choiseGooddialogVisible"
      width="1075px"
      :modal-append-to-body="false"
    >
      <div
        class="tips"
        v-if="showTips"
      >
        <i class="el-icon-warning-outline"></i>
        <span>规则说明：同一个商品若同时参加多个营销活动在商品列表中优先显示规则为 秒杀 > 定金膨胀 > 砍价 > 多人拼团 > 首单特惠 > 限时降价</span>
      </div>
      <div class="choiseDialog">
        <div>
          <section
            style="padding-left:30px;display: flex;"
            class="chooseCondiction"
          >
            <sortCatTreeSelect
              ref="catTree"
              :filterGoodsInfo="initSortCatParams"
              treeType="cat"
              :treeStyle="initPlateformWidth"
              :selectedId.sync="requestParam.catId"
            />
            <div style="margin: 0 30px;">
              <sortCatTreeSelect
                ref="sortTree"
                :filterGoodsInfo="initSortCatParams"
                treeType="sort"
                :treeStyle="initPlateformWidth"
                :selectedId.sync="requestParam.sortId"
              />
            </div>
            <div>商品标签：
              <el-select
                v-model="requestParam.labelId"
                placeholder="请选择商品标签"
                size="small"
                style="width:140px;margin-left:-4px"
              >
                <el-option
                  v-for="item in goodsLabelOptions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </div>
            <div
              class="rangeLi"
              style="margin-left:30px;"
            >商品价格范围：
              <el-input
                v-model="requestParam.lowShopPrice"
                placeholder="请输入内容"
                size="small"
              ></el-input>&nbsp;元至&nbsp;
              <el-input
                v-model="requestParam.highShopPrice"
                placeholder="请输入内容"
                size="small"
              ></el-input>
            </div>
          </section>
          <ul>
            <li>商品名称：
              <el-input
                v-model="requestParam.goodsName"
                placeholder="请输入商品名称"
                size="small"
                style="width:140px"
              ></el-input>
            </li>
            <li>商品货号：
              <el-input
                v-model="requestParam.goodsSn"
                placeholder="请输入商品货号"
                size="small"
                style="width:140px"
              ></el-input>
            </li>
            <li>商品品牌：
              <el-select
                v-model="requestParam.brandId"
                placeholder="请选择商品品牌"
                size="small"
                style="width:140px"
              >
                <el-option
                  v-for="item in goodsBrandOptions"
                  :key="item.id"
                  :label="item.brandName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </li>
          </ul>
          <div class="middleBbtnDiv">
            <div>
              <el-button
                @click="selectGoodsData"
                type="primary"
                size="small"
                style="margin-right:10px"
              >筛选</el-button>
              <el-button
                @click="resetFilterData"
                type="info"
                plain
                size="small"
                class="resetCondition"
              >重置筛选条件</el-button>
            </div>
            <div style="padding: 10px;">
              <span>已经选择{{checkedIdList.length}}件商品</span>
            </div>
          </div>
        </div>
        <!--选择商品弹窗表格-->
        <div class="table_container">
          <table width='100%'>
            <thead>
              <tr>
                <td v-if="!singleElection">
                  <el-checkbox
                    v-model="checkPageAllFlag"
                    @change="checkedPageRow(checkPageAllFlag)"
                  ></el-checkbox><i class="tdTopText">全选本页</i>
                </td>

                <td>商品信息</td>
                <td>商品货号</td>
                <td>售价</td>
                <td>库存</td>
                <td>平台分类</td>
                <td>商家分类</td>
                <td>商品标签</td>
                <td>品牌</td>
              </tr>
            </thead>
            <tbody v-if="tbodyFlag">
              <tr
                v-for="(item,index) in tableData"
                :key="index"
                :class="{goods_tr_choose: item.ischecked}"
                @click.prevent="handleRowClick(index,item,$event)"
              >
                <td v-if="!singleElection">
                  <div class="tdCenter">
                    <el-checkbox v-model="item.ischecked"></el-checkbox>
                  </div>

                </td>
                <td
                  class="isLeft"
                  :class="loadProduct?'tdCenter':''"
                >
                  <img :src="item.prdImg || item.goodsImg">
                  <span>{{item.goodsName}}</span>
                  <!-- 规格描述 -->
                  <span v-if="loadProduct">{{item.prdDesc}}</span>
                </td>
                <td class="tb_decorate_a">
                  {{item.goodsSn}}
                </td>
                <td class="tb_decorate_a">
                  <span v-if="!loadProduct">{{item.shopPrice}}</span>
                  <span v-if="loadProduct">{{item.prdPrice}}</span>
                </td>
                <td class="tb_decorate_a">
                  <span v-if="!loadProduct">{{item.goodsNumber}}</span>
                  <span v-if="loadProduct">{{item.prdNumber}}</span>
                </td>
                <td class="tb_decorate_a">
                  {{item.catName}}
                </td>
                <td class="tb_decorate_a">
                  {{item.sortName}}
                </td>
                <td class="tb_decorate_a">
                  <span
                    v-for="(childrenItem,childrenIndex) in item.goodsLabels"
                    :key='childrenIndex'
                  >{{childrenItem.name}}</span>

                </td>
                <td class="tb_decorate_a">
                  {{item.brandName}}
                </td>
              </tr>
            </tbody>

          </table>

          <div class="tablefooter">
            <div
              v-if="!singleElection"
              class="selectAll"
            >
              <el-checkbox
                v-model="checkAllFlag"
                @change="checkedAllRow(checkAllFlag)"
              >选择全部</el-checkbox>
            </div>
            <div class="paginationInfo">
              <pagination
                :page-params.sync="pageParams"
                @pagination="paginationChange"
              />
            </div>
          </div>

        </div>
        <div
          class="noData"
          v-if="!tbodyFlag"
        >
          <img :src="noImg">
          <span>暂无相关数据</span>
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="cancleBtnHandle"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="handleChoiseGooddialog()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  getProductIdsListAll,
  getGoodsIdsListAll,
  getGoodsListByIds,
  getProductListByIds,
  allGoodsQueryRequest,
  getGoodsProductList
} from '@/api/admin/brandManagement.js'
import pagination from '@/components/admin/pagination/pagination.vue'
import { getGoodsFilterItem } from '@/api/admin/goodsManage/allGoods/allGoods'
import { mapActions } from 'vuex'

import sortCatTreeSelect from '@/components/admin/sortCatTreeSelect'

export default {
  components: {
    pagination,
    sortCatTreeSelect
  },
  props: {
    // 是否加载规格
    loadProduct: {
      type: Boolean,
      default: false
    },
    // 单选/多选
    singleElection: {
      type: Boolean,
      default: false
    },
    // 多选最大数量
    checkedNumMax: Number,
    // 是否显示提示
    showTips: {
      type: Boolean,
      default: false
    },
    // 弹出窗口
    tuneUpChooseGoods: Boolean,
    // 选择的商品id
    chooseGoodsBack: {
      type: Array,
      default () {
        return []
      }
    }
  },
  computed: {
    initSortCatParams: function () {
      return {
        needGoodsNum: true,
        isOnSale: 1,
        isSaleOut: false,
        // 查询商品时值为1，规格查询值为2
        selectType: this.loadProduct ? 2 : 1
      }
    },
    initPlateformWidth: function () {
      return {
        selectSize: 'small'
      }
    }
  },
  data () {
    return {
      // 弹窗
      choiseGooddialogVisible: false,
      // 分页
      pageParams: {
        currentPage: 1,
        pageRows: 3
      },
      // 筛选条件
      requestParam: {
        currentPage: 1,
        pageRows: 3,
        // 在售商品
        isOnSale: 1,
        isSaleOut: false,
        catId: null,
        sortId: null,
        labelId: null,
        lowShopPrice: null,
        highShopPrice: null,
        goodsName: null,
        goodsSn: null,
        brandId: null
      },
      // 表格数据
      tableData: [],
      // 下拉框数据
      goodsLabelOptions: [],
      goodsBrandOptions: [],
      allGoodsProductId: [],
      // 本页全选
      checkPageAllFlag: false,
      // 全部选择
      checkAllFlag: false,
      // 选中的数据
      checkedId: {},
      checkedIdList: [],
      checkedRow: {},
      checkedRowList: [],
      checkedUrlList: [],
      tbodyFlag: true
    }
  },
  watch: {
    chooseGoodsBack (newData) {
      console.log(newData)
      console.log('chooseGoodsBack')
      this.checkedIdList = this.chooseGoodsBack
    },
    tuneUpChooseGoods () {
      console.log('tuneUpChooseGoods', this.chooseGoodsBack)
      this.choiseGooddialogVisible = true
      this.selectGoodsData()
      if (this.loadProduct) {
        getProductListByIds({ productId: this.chooseGoodsBack }).then(res => {
          console.log('getGoodslistByIds', res)
          this.clearCheckedRow()
          res.content.forEach(item => {
            this.addCheckedRow(item)
          })
        })
      } else {
        getGoodsListByIds({ goodsIds: this.chooseGoodsBack }).then(res => {
          console.log('getGoodslistByIds', res)
          this.clearCheckedRow()
          res.content.forEach(item => {
            this.addCheckedRow(item)
          })
        })
      }
    }
  },
  mounted () {
    // 初始化数据

    console.log('choosingGoods', 'mounted')
    this._initFilterDatas().then(res => {
      // this.selectGoodsData()
    })
  },
  methods: {
    ...mapActions(['changeCrumbstitle', 'transmitGoodsIds']),
    /* 初始化 */
    _initFilterDatas () {
      // 过滤条件下拉框数据
      return getGoodsFilterItem({ needGoodsLabel: true, needGoodsBrand: true }).then((res) => {
        if (!res) return
        if (res.error === 0) {
          this.goodsLabelOptions = res.content.goodsLabels
          this.goodsBrandOptions = res.content.goodsBrands
        }
      })
    },
    /* 筛选商品或规格数据 */
    selectGoodsData () {
      let query = null
      let queryAllId = null
      if (this.loadProduct) {
        query = getGoodsProductList
        queryAllId = getProductIdsListAll
      } else {
        query = allGoodsQueryRequest
        queryAllId = getGoodsIdsListAll
      }
      this.requestParam.currentPage = this.pageParams.currentPage
      this.requestParam.pageRows = this.pageParams.pageRows
      // 分页请求
      query(this.requestParam).then((res) => {
        if (!res) return
        if (res.error === 0) {
          console.log('selectGoodsData', this.checkedIdList, this.tableData)
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          // 数据回显
          this.hxTableData()
        }
      })
      // 全部数据
      if (!this.singleElection) {
        queryAllId(this.requestParam).then(res => {
          if (!res) return
          if (res.error === 0) {
            this.allGoodsProductId = res.content
            console.log('getALLId', res)
          }
        })
      }
    },
    /* 数据回显,全选 */
    hxTableData () {
      this.tableData.forEach(item => {
        item.ischecked = false
        this.checkedIdList.forEach(checkeId => {
          if (this.loadProduct) {
            if (item.prdId === checkeId) { item.ischecked = true }
          } else {
            if (item.goodsId === checkeId) { item.ischecked = true }
          }
        })
      })
      let flag = this.tableData.filter((item, index) => {
        return item.ischecked === false
      })
      if (flag.length === 0 && this.singleElection === false) {
        this.checkPageAllFlag = true
      } else {
        this.checkPageAllFlag = false
        this.checkAllFlag = false
      }
    },
    /* 重置过滤条件数据 */
    resetFilterData () {
      this.requestParam = {
        // 在售商品
        isOnSale: 1,
        isSaleOut: false
      }
      this.$refs['catTree'].clearData()
      this.$refs['sortTree'].clearData()
      this.selectGoodsData()
    },
    // 取消
    cancleBtnHandle () {
      this.choiseGooddialogVisible = false
      this.requestParam = {
        currentPage: 1,
        pageRows: 3,
        // 在售商品
        isOnSale: 1,
        isSaleOut: false,
        catId: null,
        sortId: null,
        labelId: null,
        lowShopPrice: null,
        highShopPrice: null,
        goodsName: null,
        goodsSn: null,
        brandId: null
      }
    },
    /* 确定 */
    handleChoiseGooddialog () {
      if (this.checkedNumMax && this.checkedNumMax > 0) {
        if (this.checkedIdList.length > this.checkedNumMax) {
          this.$message.warning('最多选择' + this.checkedNumMax + '件商品')
          return
        }
      }
      // 关闭对话框
      this.choiseGooddialogVisible = false
      // 单选id
      this.$emit('resultGoodsRow', this.checkedRow)
      // 多选id
      this.$http.$emit('choseGoodsId', this.checkedIdList)
      this.transmitGoodsIds(this.checkedIdList)
      this.$emit('resultGoodsIds', this.checkedIdList)
      this.$emit('result', this.checkedIdList)
      this.$emit('resultGoodsDatas', this.checkedRowList)
      // 把选中的id集合和url集合回传
      this.$emit('res', this.checkedIdList, this.checkedUrlList)
    },
    /* 翻页方法 */
    paginationChange () {
      this.selectGoodsData()
    },
    /* 选择一行 */
    handleRowClick (index, item, e) {
      console.log('handleRowClik', index, item, e)
      item.ischecked = !item.ischecked
      // 单选
      if (this.singleElection) {
        this.clearCheckedRow(item)
        this.tableData.forEach(i => {
          if (i !== item && i.ischecked) {
            i.ischecked = false
          }
        })
      }
      if (item.ischecked) {
        this.addCheckedRow(item)
      } else {
        this.removeCheckedRow(item)
      }
      // 更新
      this.$forceUpdate()
    },
    /* 获取id */
    getRowId: function (row) {
      if (this.loadProduct) {
        return row.prdId
      }
      return row.goodsId
    },
    /* 选中添加 */
    addCheckedRow (row) {
      this.checkedId = this.getRowId(row)
      this.checkedRow = row
      this.checkedIdList.push(this.getRowId(row))
      this.checkedRowList.push(row)
      this.checkedUrlList.push({
        goodsImg: row.goodsImg,
        goodsId: this.getRowId(row)
      })
      row.ischecked = true
      if (!this.singleElection) {
        let flag = this.tableData.filter((item, index) => {
          return item.ischecked === false
        })
        if (flag.length === 0) {
          this.checkPageAllFlag = true
        }
        if (this.allGoodsProductId.every(val => this.checkedIdList.includes(val))) {
          this.checkAllFlag = true
        }
      }
    },
    /* 取消选择删除 */
    removeCheckedRow (row) {
      this.checkPageAllFlag = false
      this.checkAllFlag = false
      this.checkedId = {}
      this.checkedRow = {}
      this.checkedIdList.splice(this.checkedIdList.lastIndexOf(this.getRowId(row)), 1)
      this.checkedRowList = this.checkedRowList.filter(item => {
        return this.getRowId(item) !== this.getRowId(row)
      })
      this.checkedUrlList = this.checkedUrlList.filter(item => {
        return item.goodsId !== this.getRowId(row)
      })
      row.ischecked = false
    },
    clearCheckedRow () {
      this.checkedId = {}
      this.checkedRow = {}
      this.checkedIdList = []
      this.checkedRowList = []
    },
    /* 选择本页商品 */
    checkedPageRow (newData) {
      console.log('checkedPageRow')
      this.tableData.forEach(item => {
        if (item.ischecked !== newData) {
          if (newData) {
            this.addCheckedRow(item)
          } else {
            this.removeCheckedRow(item)
          }
        }
        item.ischecked = newData
      })
    },
    /* 选择全部商品 */
    checkedAllRow (flag) {
      console.log('checkedAllRow')
      this.checkPageAllFlag = flag
      this.checkedPageRow(flag)
      if (flag) {
        // 选择全部只有全部商品id
        this.checkedIdList = this.allGoodsProductId
      } else {
        this.clearCheckedRow()
      }
    }

  }
}
</script>
<style scoped>
.middleBbtnDiv {
  padding: 15px 30px 5px;
  display: flex;
}
.middleBbtnDiv .resetCondition {
  margin: 0;
}
.table_container {
  padding: 10px 30px;
  overflow-y: auto;
  height: 300px;
}
.clickClass {
  background-color: #eee !important;
}
.goods_tr_choose {
  background: #eee;
}
.tips {
  margin-top: 10px;
  margin-left: 20px;
}
.tips span {
  display: inline-block;
  margin-left: 5px;
  color: #888;
  font-size: 14px;
}
ul {
  padding-left: 30px;
}
ul li {
  line-height: 30px;
  display: flex;
  white-space: nowrap;
  margin-top: 30px;
}
ul li:nth-of-type(1) {
  margin-top: 0;
}
/* .choiseDialog {
  overflow-y: auto;
} */
.choiseDialog ul {
  display: flex;
  margin-top: 10px;
}
.choiseDialog ul li {
  margin-top: 0;
}
.choiseDialog ul li:nth-of-type(2) {
  margin: 0 30px;
}
.choiseDialog ul li:nth-of-type(3) {
  margin-right: 30px;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
  width: 100%;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}

thead td:nth-of-type(1) {
  width: 105px;
  clear: both;
  overflow: hidden;
  /* display: flex;
  align-items: center; */
}
thead td:nth-of-type(1) .tdTopText {
  float: left;
  margin-left: 3px;
}
thead td:nth-of-type(2) {
  width: 276px;
}
thead td:nth-of-type(3) {
  width: 104px;
}
thead td:nth-of-type(4) {
  width: 71px;
}
thead td:nth-of-type(5) {
  width: 65px;
}
thead td:nth-of-type(6) {
  width: 80px;
}
thead td:nth-of-type(7) {
  width: 106px;
}
thead td:nth-of-type(8) {
  width: 127px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 7px;
  vertical-align: middle !important;
  text-align: center;
}
img {
  margin-left: 10px;
}
.isLeft {
  text-align: left;
}
.isLeft img {
  width: 40px;
}
.isLeft span {
  display: inline-block;
  vertical-align: top;
  margin-top: 9px;
  margin-left: 5px;
}
.tdCenter {
  text-align: center;
}
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 650px; */
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.noData span {
  margin: 10px;
}
.clickClass {
  background-color: #eee !important;
}
.table_container {
  padding: 10px 30px;
  overflow-y: auto;
  height: 300px;
}
.tdCenter {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
}
.level_1 {
  margin-left: 10px;
}
.level_2 {
  margin-left: 15px;
}
</style>
<style>
.choosingGoods_Container .rangeLi .el-input {
  width: 70px !important;
}
.table_container .el-checkbox {
  width: 14px !important;
  float: left !important;
  margin-right: 0 !important;
}
._Container .rangeLi .el-input__inner {
  width: 70px !important;
}
.choosingGoods_Container .el-dialog__body {
  padding: 0 !important;
}
.choosingGoods_Container .el-dialog__header {
  background-color: #f3f3f3 !important;
  text-align: center !important;
}
.el-select-dropdown__item {
  margin-top: 0 !important;
}
</style>
<style lang="scss" scoped>
.tablefooter {
  background-color: #fff;
  color: #333;
  padding: 20px 0 20px 20px;
  .selectAll {
    float: left;
  }
  .paginationInfo {
    float: right;
  }
  .el_pagination__editor {
    .el-input {
      /deep/.el-input__inner {
        width: 50px;
      }
    }
  }
}
</style>
<style lang="scss" scoped>
.choiseDialog .chooseCondiction {
  margin-top: 10px;
  height: 40px;
  line-height: 40px;
  /deep/ .el-form--inline {
    .el-form-item {
      margin-bottom: 0;
      margin-right: 0;
      .el-form-item__label {
        padding: 0;
      }
      /deep/ .el-form-item__content {
        .el-input__inner {
          width: 140px;
        }
      }
    }
  }
}
</style>
