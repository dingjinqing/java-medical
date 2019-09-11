<template>
  <div class="choosingGoods_Container">
    <!--选择商品弹窗-->
    <el-dialog
      title="选择商品"
      :visible.sync="choiseGooddialogVisible"
      width="70%"
      :modal-append-to-body="false"
    >
      <div class="choiseDialog">
        <div>
          <ul>
            <li>平台分类：
              <el-select
                v-model="bottomDialogSelectOne"
                placeholder="请选择平台分类"
                size="small"
              >
                <el-option
                  v-for="item in bottomOptionsOne"
                  :key="item.catId"
                  :label="item.catName"
                  :value="item.catId"
                  :class="[item.level ===1?'level_1':'',item.level ===2?'level_2':'']"
                >
                </el-option>
              </el-select>
            </li>
            <li>商家分类：
              <el-select
                v-model="bottomDialogSelectTwo"
                placeholder="请选择商家分类"
                size="small"
              >
                <el-option
                  v-for="item in bottomOptionsTwo"
                  :key="item.sortId"
                  :label="item.sortName"
                  :value="item.sortId"
                  :class="[item.level ===1?'level_1':'',item.level ===2?'level_2':'']"
                >
                </el-option>
              </el-select>
            </li>
            <li>商品标签：
              <el-select
                v-model="bottomDialogSelectThree"
                placeholder="请选择商品标签"
                size="small"
              >
                <el-option
                  v-for="item in bottomOptionsThree"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </li>
            <li class="rangeLi">商品价格范围：
              <el-input
                v-model="inputBottomRange"
                placeholder="请输入内容"
                size="small"
              ></el-input>&nbsp;元至&nbsp;
              <el-input
                v-model="inputBottomRangeRight"
                placeholder="请输入内容"
                size="small"
              ></el-input>
            </li>
          </ul>
          <ul>
            <li>商品名称：
              <el-input
                v-model="goodsName"
                placeholder="请输入商品名称"
                size="small"
              ></el-input>
            </li>
            <li>商品货号：
              <el-input
                v-model="goodsNum"
                placeholder="请输入商品货号"
                size="small"
              ></el-input>
            </li>
            <li>商品品牌：
              <el-select
                v-model="goodsGrandVal"
                placeholder="请选择商品品牌"
                size="small"
              >
                <el-option
                  v-for="item in goodsGrandOptions"
                  :key="item.id"
                  :label="item.brandName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </li>
          </ul>
          <div class="middleBbtnDiv">
            <el-button
              type="primary"
              size="small"
              style="margin-right:10px"
            >筛选</el-button>
            <el-button
              type="info"
              plain
              size="small"
            >重置筛选条件</el-button>
          </div>
        </div>
        <!--选择商品弹窗表格-->
        <div class="table_container">
          <table width='100%'>
            <thead>
              <tr>
                <td>
                  <el-checkbox v-model="checkedAll"></el-checkbox><i class="tdTopText">全选本页</i>
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
                v-for="(item,index) in trList"
                :key="index"
                :class="clickIindex===index?'clickClass':''"
                @click.prevent="handleClick(index,item)"
              >
                <td>
                  <div class="tdCenter">
                    <el-checkbox v-model="item.ischecked"></el-checkbox>
                  </div>

                </td>
                <td
                  class="isLeft"
                  :class="isCenterFlag?'tdCenter':''"
                >
                  <img
                    v-if="!isCenterFlag"
                    :src="item.prdImg || item.goodsImg"
                  >
                  <span>{{item.goodsName}}</span>
                  <!-- 规格描述 -->
                  <span v-if="!!item.prdDesc">{{item.prdDesc}}</span>
                </td>
                <td class="tb_decorate_a">
                  {{item.goodsSn}}
                </td>
                <td class="tb_decorate_a">
                  {{item.shopPrice}}
                </td>
                <td class="tb_decorate_a">
                  {{item.goodsNumber}}
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
            <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.total}}</div>
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="20"
              layout="prev, pager, next, jumper"
              :total="total"
            >
            </el-pagination>
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
          @click="choiseGooddialogVisible = false"
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
  initGrandgetRequest,
  queryGoodsIdRequest,
  classificationSelectRequest,
  allGoodsQueryRequest,
  getGoodsProductList
} from '@/api/admin/brandManagement.js'
import { mapActions, mapGetters } from 'vuex'
export default {
  props: {
    // 是否加载规格
    loadProduct: Boolean
  },
  data () {
    return {
      pageCount: 1,
      total: null,
      currentPage: 1,
      choiseGooddialogVisible: false,
      bottomDialogSelectOne: '',
      bottomOptionsOne: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      bottomDialogSelectTwo: '',
      bottomOptionsTwo: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      bottomDialogSelectThree: '',
      bottomOptionsThree: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      inputBottomRange: '',
      inputBottomRangeRight: '',
      goodsName: '',
      goodsNum: '',
      goodsGrandVal: '',
      goodsGrandOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      checkedAll: false,
      tbodyFlag: true,
      trList: [],
      clickIindex: '',
      isCenterFlag: '',
      tdHiddenImg: this.$imageHost + '/upload/7467397/image/20190507/crop_N7Fu7EaKRtaZri18.gif',
      logoImgUrl: '',
      NameEnlishInput: '',
      goodsIdsArr: [],
      goodsRow: {},
      selectgoodsNum: 0,
      hxgoodsIds: [],
      choiseOne: false
    }
  },
  computed: {
    ...mapGetters(['goodsIds', 'editGoodsId'])
  },
  watch: {
    checkedAll (newData, oldData) {
      if (newData === true) {
        this.trList.map((item, index) => {
          item.ischecked = true
        })
      } else {
        this.trList.map((item, index) => {
          item.ischecked = false
        })
      }
    }
    // goodsIds_: {
    //   handler (newData, oldData) {
    //     console.log(newData)
    //     this.goodsIdsArr = [...new Set(newData)]
    //     this.selectgoodsNum = this.goodsIdsArr.length
    //     console.log(this.goodsIdsArr)
    //   },
    //   immediate: true
    // }
  },
  mounted () {
    this.$http.$on('choosingGoodsFlag', (res, flag) => {
      console.log(res, flag)
      console.log(this.trList)

      this.choiseGooddialogVisible = true
      this.trList.forEach(item => {
        item.ischecked = false
      })
      if (flag === 'choiseOne') {
        console.log('choiseOne', this.editGoodsId)
        this.choiseOne = true
        this.trList.forEach(item => {
          if (item.goodsId === this.editGoodsId) {
            item.ischecked = true
          }
        })
        return
      }
      if (flag) {
        this.trList.forEach(item => {
          flag.forEach(itemC => {
            if (item.goodsId === itemC) {
              item.ischecked = true
            }
          })
        })
      }
    })
    // 品牌分类初始化获取及页编辑回显
    this.defaultGrandClass()
  },
  methods: {
    ...mapActions(['changeCrumbstitle', 'transmitGoodsIds']),
    defaultGrandClass () {
      console.log('defaultGrandClass()', this.editGoodsId)

      if (this.editGoodsId !== 'add' && this.editGoodsId) {
        let obj = {
          'id': this.editGoodsId
        }
        queryGoodsIdRequest(obj).then((res) => {
          console.log(res.content)
          this.NameInput = res.content.brandName
          this.NameEnlishInput = res.content.ename
          this.logoImgUrl = res.content.logo
          this.classSelectValue = res.content.classifyId
          this.firstInput = res.content.first
          console.log(res.content.isRecommend)
          this.radio = res.content.isRecommend.toString()
          this.hxgoodsIds = res.content.goodsIds
          this.selectgoodsNum = res.content.goodsIds.length
        })
      }
      classificationSelectRequest().then((res) => {
        console.log(res)
        if (!res) return
        if (res.error === 0) {
          this.options = res.content
        }
      })
      this.handleClickChoiseGood()
    },
    // 初始数据获取
    handleClickChoiseGood () {
      let obj = {
        goodsName: this.goodsName,
        catId: this.bottomDialogSelectOne,
        sortId: this.bottomDialogSelectTwo,
        labelId: this.bottomDialogSelectThree,
        brandId: this.goodsGrandVal,
        lowShopPrice: this.inputBottomRange,
        highShopPrice: this.inputBottomRangeRight,
        currentPage: 1,
        pageRows: 20
      }
      // 弹窗上方下拉框统一数据获取
      initGrandgetRequest().then((res) => {
        if (!res) return
        if (res.error === 0) {
          this.bottomOptionsOne = res.content.sysCates
          this.bottomOptionsTwo = res.content.goodsSorts
          this.bottomOptionsThree = res.content.goodsLabels
          this.goodsGrandOptions = res.content.goodsBrands
        }

        console.log(res)
      })
      // 弹窗下方表格数据获取
      let query = allGoodsQueryRequest
      if (this.loadProduct) {
        query = getGoodsProductList
      }
      query(obj).then((res) => {
        if (!res) return
        if (res.error === 0) {
          // res.content.dataList.catName = res.content.dataList.catName.replace(',', '、')
          console.log(res.content.dataList)
          res.content.dataList.catName = res.content.dataList.map((item, index) => {
            console.log(item.catName)
            item.catName = item.catName.replace('，', '、')
            item.ischecked = false
            this.hxgoodsIds.map((childrenItem, childrenIndex) => {
              let condition = childrenItem === item.goodsId
              if (this.loadProduct) {
                // 规格id
                condition = childrenItem.prdId === childrenItem
              }
              if (condition) {
                item.ischecked = true
              }
            })
          })
          let flag = res.content.dataList.filter((item, index) => {
            return item.ischecked === false
          })
          console.log(flag)
          if (flag.length === 0 && this.choiseOne === false) {
            this.checkedAll = true
          }
          this.trList = res.content.dataList
        }
        console.log(res)
      })
    },
    // 行选中高亮
    handleClick (index, item) {
      this.clickIindex = index
      console.log(this.trList[index].ischecked)
      let flagBefore = this.trList.filter((tmpitem, index) => {
        return tmpitem.ischecked === true
      })
      console.log('flagBefore', flagBefore)
      console.log('item', item)
      // 单选模式
      if (this.choiseOne) {
        // item.ischecked = false
        this.trList.forEach(tmpitem => {
          if (item.goodsId === tmpitem.goodsId) {
            tmpitem.ischecked = true
          } else {
            tmpitem.ischecked = false
          }
        })
        this.goodsRow = item
        return
      }
      this.trList[index].ischecked = !this.trList[index].ischecked
      let flag = this.trList.filter((item, index) => {
        return item.ischecked === false
      })
      console.log(this.choiseOne)
      if (!flag.length) {
        this.checkedAll = true
      } else {
        this.checkedAll = false
      }
      // let arr = []
      console.log(this.trList)
      console.log(this.goodsIdsArr)
      console.log('选中', index, item)
    },
    // 选择商品弹窗确定
    handleChoiseGooddialog () {
      this.$http.$emit('choseGoodsId', this.goodsIdsArr)

      this.goodsIdsArr = []
      this.trList.forEach(item => {
        if (item.ischecked) {
          let _goodsId = item.goodsId
          if (this.loadProduct) {
            _goodsId = item.prdId
          }

          this.goodsIdsArr.push(_goodsId)
        }
      })
      console.log(this.goodsIdsArr)

      this.transmitGoodsIds(this.goodsIdsArr)
      // 关闭对话框
      this.choiseGooddialogVisible = false
      this.$http.$emit('result', this.goodsIdsArr)
      this.$emit('resultGoodsRow', this.goodsRow)
      this.$emit('resultGoodsIds', this.goodsIdsArr)
    },
    // 页数改变
    handleCurrentChange () {
      this.defaultGrandClass()
    }
  }
}
</script>
<style scoped>
.middleBbtnDiv {
  padding: 10px 30px;
}
.table_container {
  padding: 10px 30px;
  overflow-y: auto;
  height: 300px;
}
.clickClass {
  background-color: #eee !important;
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
.choiseDialog {
  overflow-y: auto;
}
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
.middleBbtnDiv {
  padding: 10px 30px;
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
/* .choosingGoods_Container .el-input__inner {
  width: 140px !important;
} */
.choosingGoods_Container .rangeLi .el-input__inner {
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
  height: 50px;
  line-height: 50px;
  color: #333;
  font-size: 14px;
  display: flex;
  justify-content: flex-end;
  padding-right: 10px;
  /deep/ .el-pagination {
    display: flex;
    align-items: center;
    .el-pager {
      display: flex;
      align-items: center;
    }
  }
}
</style>
