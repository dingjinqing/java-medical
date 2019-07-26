<template>
  <div class="addBrand">
    <div class="addBrandContent">
      <div class="addBrandMain">
        <ul>
          <li>
            <div class="brand_title">
              <span style="color:red">*</span>
              <span class="nameClass">品牌名称：</span>
              <el-input
                v-model="NameInput"
                placeholder="请输入内容"
                size="mini"
              ></el-input>
            </div>
          </li>
          <li>
            <div class="brand_title">
              <span style="color:red">*</span>
              <span class="nameClass">品牌Logo：</span>
              <span
                @click="handleImgDailog()"
                class="addImgClass"
              ></span>
            </div>
          </li>
          <li>
            <div
              class="brand_title"
              style="margin-left:10px"
            >
              <span class="nameClass">品牌分类：</span>
              <el-select
                v-model="classSelectValue"
                placeholder="请选择"
                size="mini"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <div class="classDiv">
                <span>&nbsp;刷新</span> &nbsp;|&nbsp;
                <span @click="handleNewBuild()">新建品牌分类</span>&nbsp;|&nbsp;
                <span @click="handleTurnManClassPage()">管理品牌分类</span>
              </div>
            </div>
          </li>
          <li>
            <div
              class="brand_title"
              style="margin-left:-2px"
            >
              <span class="nameClass">品牌优先级：</span>
              <el-input
                v-model="firstInput"
                placeholder="请输入内容"
                size="mini"
              ></el-input>
              <span style="color:#999;margin-left:3px">请填写正整数，数值越大，优先级越高，在小程序前端展示位置越靠前</span>
            </div>
          </li>
          <li>
            <div
              class="brand_title"
              style="margin-left:-19px"
            >
              <span style="color:red">*</span>
              <span class="nameClass">设为推荐品牌：</span>
              <el-radio
                v-model="radio"
                label="1"
              >是</el-radio>
              <el-radio
                v-model="radio"
                label="2"
              >否</el-radio>
              <span style="color:#999">设为推荐品牌，将展示在小程序推荐品牌中列表中</span>
            </div>
          </li>
          <li>
            <div
              class="brand_title"
              style="margin-left:16px"
            >
              <span class="nameClass">添加商品：</span>
              <div
                class="choiseDivClass"
                @click="handleClickChoiseGood()"
              >
                <img :src="choiseGoodImgUrl">
                选择商品
              </div>
              <span style="color:#5a8bff;margin-left:20px">已选择商品数量：0</span>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <!--保存-->
    <div class="footer">
      <div
        class="save"
        @click="saveShopStyle()"
      >{{$t('shopStyle.saveText')}}</div>
    </div>
    <!--添加品牌分类弹窗-->
    <el-dialog
      title="添加品牌分类"
      :visible.sync="dialogVisible"
      width="30%"
      :center='true'
    >
      <div class="dialogMain">
        <p>品牌分类名称：<el-input
            v-model="brandName"
            placeholder="请输入内容"
            size="mini"
          ></el-input>
        </p>
        <p style="margin-top:10px"><span style="margin-right:11px">分类优先级：</span>
          <el-input
            v-model="classificationName"
            placeholder="请输入内容"
            size="mini"
          ></el-input>
        </p>
        <p>请填写正整数，数值越大，优先级越高，在小程序前端展示位置越靠前</p>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--选择商品弹窗-->
    <el-dialog
      title="选择商品"
      :visible.sync="choiseGooddialogVisible"
      width="70%"
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
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
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
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
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
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </li>
            <li class="rangeLi">商品价格范围:
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
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
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
                    :src="tdHiddenImg"
                  >
                  <span>{{item.title}}</span>

                </td>
                <td class="tb_decorate_a">
                  {{item.path}}
                </td>
                <td class="tb_decorate_a">
                  {{item.path}}
                </td>
                <td class="tb_decorate_a">
                  {{item.path}}
                </td>
                <td class="tb_decorate_a">
                  {{item.path}}
                </td>
                <td class="tb_decorate_a">
                  {{item.path}}
                </td>
                <td class="tb_decorate_a">
                  {{item.path}}
                </td>
                <td class="tb_decorate_a">
                  {{item.path}}
                </td>
              </tr>
            </tbody>

          </table>
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
          @click="choiseGooddialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--选择图片弹窗 -->
    <div class="specialDialog">
      <ImageDalog
        pageIndex='pictureSpace'
        @handleSelectImg='handleSelectImg'
      />
    </div>
  </div>
</template>
<script>
import { brandAddGetRequest } from '@/api/admin/brandManagement.js'
import { mapActions } from 'vuex'
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: { ImageDalog },
  data () {
    return {
      NameInput: '',
      classSelectValue: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      firstInput: '',
      radio: '1',
      choiseGoodImgUrl: this.$imageHost + '/image/admin/icon_jia.png',
      dialogVisible: false,
      choiseGooddialogVisible: false,
      brandName: '',
      classificationName: '',
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
      inputBottomRangeRight: '',
      goodsName: '',
      goodsNum: '',
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
      goodsGrandVal: '',
      trList: [
        {
          title: '111',
          path: 'pages/index/index',
          spanId: '',
          ischecked: false,
          children: [
            {
              title: '456',
              path: 'pages/index/index'
            },
            {
              title: 'lalala',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '门店列表页',
          ischecked: false,
          path: 'pages/storelist/storelist',
          spanId: ''

        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          spanId: '',
          ischecked: false,
          children: [
            {
              title: '789',
              path: 'pages/index/index'
            },
            {
              title: '789123',
              path: 'pages/index/index'
            },
            {
              title: 'aaa',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '子页',
          ischecked: false,
          path: 'pages/cart/cart',
          spanId: '',
          children: [
            {
              title: 'zzzzz',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '子页',
          ischecked: false,
          path: 'pages/cart/cart',
          spanId: '',
          children: [
            {
              title: 'zzzzz',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '子页',
          ischecked: false,
          path: 'pages/cart/cart',
          spanId: '',
          children: [
            {
              title: 'zzzzz',
              path: 'pages/index/index'
            }
          ]
        }

      ],
      isCenterFlag: '',
      tbodyFlag: true,
      tdHiddenImg: this.$imageHost + '/upload/7467397/image/20190507/crop_N7Fu7EaKRtaZri18.gif',
      inputBottomRange: '',
      clickIindex: '',
      checkedAll: false
    }
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
  },
  mounted () {
    // 传递crumbsTitle
    let arr = ['商品管理', '品牌管理', '添加品牌']
    this.changeCrumbstitle(arr)
  },
  methods: {
    ...mapActions(['changeCrumbstitle']),
    // 新建品牌分类弹窗
    handleNewBuild () {
      this.dialogVisible = true
    },
    // 跳转到品牌分类
    handleTurnManClassPage () {
      let obj = {
        index: 1,
        turnIndex: 1
      }
      this.$emit('turnComponents', obj)
    },
    // 点击添加商品按钮
    handleClickChoiseGood () {
      this.choiseGooddialogVisible = true
    },
    // 行选中高亮
    handleClick (index, item) {
      this.clickIindex = index
      // console.log(this.trList[index].ischecked)
      this.trList[index].ischecked = !this.trList[index].ischecked
      console.log('选中', index, item)
    },
    // 调用图片弹窗
    handleImgDailog () {
      this.$http.$emit('dtVisible')
    },
    // 图片弹窗选中
    handleSelectImg (res) {
      console.log(res)
    },
    // 保存
    saveShopStyle () {
      let obj = {
        'brandName': this.NameInput,
        'ename': '',
        'logo': 'afasdfasdf',
        'first': 1,
        'desc': '描述',
        'isRecommend': 1,
        'classifyId': 0
      }
    }
  }
}
</script>
<style scoped>
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: fixed;
  z-index: 2;
  bottom: 0;
  width: 89%;
  padding: 10px 0;
}
.save {
  width: 70px;
  height: 30px;
  line-height: 30px;
  border: none;
  background: #5a8bff;
  color: #fff;
  margin: auto;
  cursor: pointer;
}
.addBrand {
  padding: 10px;
  padding-right: 23px;
  min-width: 1400px;
  font-size: 14px;
  height: 100%;
  position: relative;
}
.addBrandContent {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
  padding-top: 20px;
  padding-bottom: 20px;
}
.addBrandMain {
  padding-left: 30px;
}
ul {
  padding-left: 30px;
}
ul li {
  line-height: 30px;
}
ul li {
  margin-top: 30px;
}
ul li:nth-of-type(1) {
  margin-top: 0;
}
.brand_title {
  display: flex;
  justify-content: flex-start;
}
.nameClass {
  white-space: nowrap;
  margin: 0 5px;
}
.addImgClass {
  width: 70px;
  height: 70px;
  background: url(../../../../../../assets/adminImg/btn_add.png) no-repeat;
  box-shadow: 0 0 0 #fff;
  padding-top: 40px;
  padding-left: 8px;
  color: #9a9a9a;
  border: none;
  margin-right: 10px;
}
.classDiv > span {
  color: #5a8bff;
  cursor: pointer;
}
.choiseDivClass {
  width: 120px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  color: #5a8bff;
  border: 1px solid #ccc;
  cursor: pointer;
  display: inline-block;
}
.dialogMain {
  margin: 30px 30px 0 30px;
}
.dialogMain p:nth-of-type(3) {
  margin-left: 97px;
  margin-top: 10px;
  line-height: 30px;
  color: #999;
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
  width: 63px;
}
thead td:nth-of-type(6) {
  width: 73px;
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
  padding: 8px 10px;
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
</style>
<style>
.addBrand .el-input {
  width: auto !important;
}
.table_container label {
  display: flex;
  align-items: center;
}
.table_container label:nth-of-type(2) {
  margin-right: 10px !important;
}
.addBrand .el-dialog__header {
  background-color: #f3f3f3 !important;
  text-align: center !important;
}
.addBrand .el-dialog__body {
  padding: 0 !important;
}
.rangeLi .el-input__inner {
  width: 70px !important;
}
.table_container .el-checkbox {
  width: 14px !important;
  float: left !important;
  margin-right: 0 !important;
}
.specialDialog .el-input__inner {
  width: 40px !important;
}
</style>
