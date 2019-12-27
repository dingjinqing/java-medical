<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>商品分组模块</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="tips">
          商品分组菜单：<span style="color:#999">最多可选择10个分组</span>
        </div>
        <!--添加商家分类、商品标签、商品品牌数据后显示的隐藏模块-->
        <div v-if='linkageData.goodsItems.length'>
          <div
            class="hiddenGoodsModules"
            v-for="(item,index) in linkageData.goodsItems"
            :key="index"
          >
            <div>
              <span>{{item.type===0?'商家分类':item.type===1?'商家标签':'商家品牌'}}：</span>
              <span style="display:inline-block;width:100px">{{item.typeName}}</span>
              <span
                @click="handleToEditData(index)"
                style="padding-left: 20px;color: #5A8BFF;cursor: pointer;"
              >修改</span>
            </div>
            <div class="nameContainer">
              <span>自定义分组名称：</span>
              <el-input
                v-model="item.customName"
                size="small"
              ></el-input>
            </div>
            <div>
              <span>展示商品数量：</span>
              <el-radio
                v-model="item.radio"
                label="1"
              >全部{{item.goodsNum}}件</el-radio>
              <el-radio
                v-model="item.radio"
                label="2"
              >指定商品</el-radio>
            </div>
            <div class="groupItemOperation">
              <img
                @click="handleToClickTopIcon(0,index)"
                :src="$imageHost+'/image/admin/new_shop_beautify/add_up_use.png'"
              >
              <img
                @click="handleToClickTopIcon(1,index)"
                :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
                style="padding:0 5px"
              >
              <img
                @click="handleToClickTopIcon(2,index)"
                :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
              >
            </div>
          </div>

        </div>
        <!--end-->
        <div class="add_sort_cat">
          <span
            @click="handleToCallDialog(0)"
            style="border-right:1px dashed #D3D3D3"
          >+添加商家分类</span>
          <span
            @click="handleToCallDialog(1)"
            style="border-right:1px dashed #D3D3D3"
          >+添加商品标签</span>
          <span @click="handleToCallDialog(2)">+添加商品品牌</span>
        </div>
        <div class="mainList">
          <span>菜单样式</span>
          <el-radio
            v-model="linkageData.menuStyle"
            label="1"
          >顶部展示商品分组</el-radio>
          <el-radio
            v-model="linkageData.menuStyle"
            label="2"
          >左侧展示商品分组</el-radio>
        </div>
        <div class="mainList bgContainer">
          <span>背景颜色</span>
          <div class="bgDiv">
            <el-radio
              v-model="linkageData.bgColorRadio"
              label="1"
            >与页面背景颜色一致</el-radio>
            <div class="customBgColor">
              <el-radio
                v-model="linkageData.bgColorRadio"
                label="2"
              >自定义</el-radio>
              <span>
                <el-color-picker
                  v-model="linkageData.bg_color"
                  show-alpha
                  :disabled="linkageData.bgColorRadio==='1'?true:false"
                  :predefine="predefineColors"
                >
                </el-color-picker>
              </span>
              <div style="margin-left:10px;margin-top:-1px">
                <el-button
                  @click="handleToReset()"
                  size="small"
                >重置</el-button>
              </div>
            </div>

          </div>

        </div>
        <div v-if="linkageData.menuStyle === '1'">
          <div class="mainList allGroup">
            <span>全部分组</span>
            <div>
              <div style="color:#999;margin-bottom:10px">全部分组将展示已选商品分组种的所有商品</div>
              <div>
                <el-radio
                  v-model="linkageData.allGrouped"
                  label="1"
                >展示</el-radio>
                <el-radio
                  v-model="linkageData.allGrouped"
                  label="2"
                >不展示</el-radio>
              </div>
            </div>

          </div>
          <div class="mainList menuLocation">
            <span>菜单位置</span>
            <el-radio
              v-model="linkageData.menuLocation"
              label="1"
            >一般样式</el-radio>
            <el-radio
              v-model="linkageData.menuLocation"
              label="2"
            >滚动至顶部固定</el-radio>
          </div>
          <div class="mainList listStyle">
            <span>商品列表样式</span>
            <div>
              <div class="listStyleFirstDiv">
                <el-radio
                  v-model="linkageData.goodsListStyle"
                  label="1"
                >大图展示</el-radio>
                <el-radio
                  v-model="linkageData.goodsListStyle"
                  label="2"
                >一行两个</el-radio>
                <el-radio
                  v-model="linkageData.goodsListStyle"
                  label="3"
                >一行三个</el-radio>
              </div>
              <div>
                <el-radio
                  v-model="linkageData.goodsListStyle"
                  label="4"
                >商品列表</el-radio>
                <el-radio
                  v-model="linkageData.goodsListStyle"
                  label="5"
                >一行横滑</el-radio>
              </div>
            </div>

          </div>
        </div>

        <!--底部模块-->
        <div class="foorter">
          <div>
            <span>模块样式</span>
            <el-radio
              v-model="linkageData.modulesStyle"
              label="1"
            >白底无边框</el-radio>
            <el-radio
              v-model="linkageData.modulesStyle"
              label="2"
            >边框投影</el-radio>
            <el-radio
              v-model="linkageData.modulesStyle"
              label="3"
            >白底有边框</el-radio>
          </div>
          <div style="margin:10px 0">
            <span>模块角度</span>
            <el-radio
              v-model="linkageData.modulesAngle"
              label="1"
            >直角</el-radio>
            <el-radio
              v-model="linkageData.modulesAngle"
              label="2"
            >圆角</el-radio>
          </div>
          <div class="endDiv">
            <span>显示内容</span>
            <div>
              <div>
                <el-checkbox v-model="linkageData.whiteNoBoder">白底无边框</el-checkbox>
                <el-checkbox v-model="linkageData.projection">边框投影</el-checkbox>
                <el-checkbox v-model="linkageData.whiteHaveBoder">白底有边框</el-checkbox>
              </div>
              <div style="margin:10px 0">
                <el-checkbox v-model="linkageData.bugBtn">购买按钮</el-checkbox>
                <span style="color:#999">显示购买按钮时将不显示其他信息</span>
              </div>
              <!--购买按钮checkbox选中后显示的隐藏模块-->
              <div
                class="buyBtnHidden"
                v-if="linkageData.bugBtn"
              >
                <el-radio
                  v-model="linkageData.cart_btn_choose"
                  label="1"
                >
                  <i
                    class="iconfont icontianjia icon_font_size new_class"
                    style="color: rgb(177, 78, 105);"
                  ></i>
                </el-radio>
                <el-radio
                  v-model="linkageData.cart_btn_choose"
                  label="2"
                ><i
                    class="iconfont icongouwuche1 icon_font_size new_class"
                    style="color: rgb(177, 78, 105);"
                  ></i></el-radio>
                <el-radio
                  v-model="linkageData.cart_btn_choose"
                  label="3"
                >
                  <i
                    class="right_buy new_back"
                    style="background-color: rgb(177, 78, 105);"
                  >
                    马上抢
                  </i>
                </el-radio>
                <el-radio
                  v-model="linkageData.cart_btn_choose"
                  label="4"
                >
                  <i
                    class="cart_buy"
                    style="color: rgb(177, 78, 105); border-color: rgb(177, 78, 105);"
                  >购买</i>
                </el-radio>
              </div>
              <!--end-->
              <div style="margin-bottom:10px">
                <el-checkbox v-model="linkageData.otherInfo">其他信息</el-checkbox>
                <span style="color:#999">后台数据仅为参考请以实际显示为准</span>
              </div>
              <!--其他信息checkbox选中后显示的隐藏模块-->
              <div v-if="linkageData.otherInfo">
                <el-radio
                  v-model="linkageData.hiddenOtherInfoRadio"
                  label="1"
                >市场价</el-radio>
                <el-radio
                  v-model="linkageData.hiddenOtherInfoRadio"
                  label="2"
                >销量</el-radio>
                <el-radio
                  v-model="linkageData.hiddenOtherInfoRadio"
                  label="3"
                >评价数</el-radio>
              </div>
              <!--end-->
            </div>

          </div>
        </div>
      </div>
      <!--end-->
    </div>
    <!--商家分类弹窗-->
    <AddingBusClassDialog
      :dialogVisible.sync='classificationDialogVisible'
      :classFlag='1'
      :backDataArr='backDataArr'
      @BusClassTrueDetailData='busClassTrueDetailData'
      :singleElection="classSingleElection"
    />
    <!--商品标签弹窗-->
    <AddProductLabel
      :callAddProductLabel.sync='callAddProductLabel'
      @handleToGetBackData='handleToGetBackData'
      :brandBackData="[]"
      :singleElection="classSingleElection"
    />
    <!--商品品牌弹窗-->
    <AddBrandDialog
      :callAddBrand.sync='callAddBrand'
      @handleToGetBackData='handleToGetBrandBackData'
      :brandBackData="[]"
      :singleElection="true"
    />
  </div>
</template>
<script>
export default {
  components: {
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog'), // 选择商家分类标签弹窗
    AddProductLabel: () => import('@/components/admin/addProductLabel'), // 选择商品标签弹窗
    AddBrandDialog: () => import('@/components/admin/addBrandDialog') // 选择商品品牌弹窗
  },
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      predefineColors: [ // 预定义颜色池
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ],
      callAddBrand: false, // 调起商品品牌弹窗flag
      callAddProductLabel: false, // 调起商品标签弹窗flag
      classificationDialogVisible: false, // 调起商家分类弹窗flag
      backDataArr: [], // 商家分类弹窗回显数据
      defaultBgColor: '#f5f5f5', // 背景自定义颜色默认
      linkageData: {
        menuStyle: '1', // 菜单样式radio
        bgColorRadio: '1', // 背景颜色radio
        bg_color: '', // 背景自定义颜色
        allGrouped: '1', // 全部分组radio
        menuLocation: '1', // 菜单位置radio
        goodsListStyle: '1', // 商品列表样式radio
        modulesStyle: '1', // 模块样式radio
        modulesAngle: '1', // 模块角度radio
        whiteNoBoder: true, // 白底无边框checkbox
        projection: false, // 边框投影
        whiteHaveBoder: false, // 白底有边框checkbox
        bugBtn: false, // 购买按钮checkbox
        otherInfo: true, // 其他信息chexkbox
        cart_btn_choose: '1', // 购买按钮选中显示模块radio
        hiddenOtherInfoRadio: '1', // 其他信息选中显示模块radio
        goodsItems: [], // 商品分组菜单隐藏模块数据列表
        input: '',
        radio: '1'

      },
      clickEditBtn: false, // 是否点击修改按钮
      editIndex: null, // 当前修改的index
      classSingleElection: false
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        this.data = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    linkageData: {
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    },
    // 购买按钮
    'linkageData.bugBtn' (newData) {
      if (newData) {
        this.linkageData.otherInfo = false
      }
    },
    // 其他信息按钮
    'linkageData.otherInfo' (newData) {
      if (newData) {
        this.linkageData.bugBtn = false
      }
    }
  },
  mounted () {

  },
  methods: {
    // 背景颜色自定义点击重置
    handleToReset () {
      this.linkageData.bg_color = this.defaultBgColor
    },
    // 调起弹窗
    handleToCallDialog (flag, isEdit) {
      if (isEdit) {
        this.classSingleElection = true // 控制弹窗单选
      } else {
        this.classSingleElection = false
      }
      switch (flag) {
        case 0:
          this.classificationDialogVisible = true
          break
        case 1:
          this.callAddProductLabel = true
          break
        case 2:
          this.callAddBrand = true
      }
    },
    // 商家分类弹窗选中回传数据
    busClassTrueDetailData (data) {
      console.log(data)
      let arr = []
      data.forEach((item, index) => {
        //  obj
        let obj = { type: 0, radio: '1' }
        console.log(item.goodsSumNum)
        if (item.goodsSumNum !== undefined) {
          obj.typeName = item.sortName
          obj.customName = item.sortName
          obj.goodsNum = item.goodsSumNum
          arr.push(obj)
        }
      })
      console.log(this.clickEditBtn, this.editIndex, arr)
      if (this.clickEditBtn) {
        this.linkageData.goodsItems[this.editIndex] = arr
      } else {
        let newArr = this.linkageData.goodsItems.concat(arr)
        this.linkageData.goodsItems = newArr
      }
      this.clickEditBtn = false
      console.log(this.linkageData.goodsItems, arr)
    },
    // 商品标签弹窗选中回传数据
    handleToGetBackData (data) {
      console.log(data)
      let arr = []
      data.forEach((item, index) => {
        //  obj
        let obj = { type: 1, radio: '1' }
        console.log(item.goodsSumNum)
        obj.typeName = item.name
        obj.customName = item.name
        obj.goodsNum = item.goodsNum
        arr.push(obj)
      })
      console.log(this.clickEditBtn, this.editIndex, arr)
      if (this.clickEditBtn) {
        this.linkageData.goodsItems[this.editIndex] = arr
      } else {
        let newArr = this.linkageData.goodsItems.concat(arr)
        this.linkageData.goodsItems = newArr
      }
      this.clickEditBtn = false
    },
    handleToGetBrandBackData (data) { // 商品品牌弹窗数据回传
      console.log(data)
      let arr = []
      data.forEach((item, index) => {
        //  obj
        let obj = { type: 2, radio: '1' }
        console.log(item.goodsSumNum)
        obj.typeName = item.brandName
        obj.customName = item.brandName
        obj.goodsNum = item.goodsNum
        arr.push(obj)
      })
      if (this.clickEditBtn) {
        this.linkageData.goodsItems[this.editIndex] = arr
      } else {
        let newArr = this.linkageData.goodsItems.concat(arr)
        this.linkageData.goodsItems = newArr
      }
      this.clickEditBtn = false
    },
    handleToEditData (index) { // 点击修改
      this.clickEditBtn = true
      this.editIndex = index
      let flag = this.linkageData.goodsItems[index].type
      this.handleToCallDialog(flag, true)
    },
    handleToClickTopIcon (flag, index) { // 顶部icon点击统一处理
      let arr = JSON.parse(JSON.stringify(this.linkageData.goodsItems))
      let pre, next, temp
      if ((index - 1) < 0) {
        pre = -1
      } else {
        pre = arr[(index - 1)]
      }
      if ((index + 1) > (arr.length - 1)) {
        next = -1
      } else {
        next = arr[(index + 1)]
      }
      temp = arr[index]
      switch (flag) {
        case 0:
          if (pre === -1) return
          arr[index] = pre
          arr[(index - 1)] = temp
          this.isClickGoodsUpOrDownIcon = true
          break
        case 1:
          if (next === -1) return
          arr[index] = next
          arr[(index + 1)] = temp
          this.isClickGoodsUpOrDownIcon = true
          break
        case 2:
          arr.splice(index, 1)
          break
      }
      console.log(arr)
      this.linkageData.goodsItems = arr
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    //模块私有样式
    .main {
      .tips {
        margin: 10px 0;
      }
      .add_sort_cat {
        width: 440px;
        padding: 10px 20px;
        background: #fff;
        border: 1px dashed #d3d3d3;
        position: relative;
        padding-left: 45px;
        margin: 10px 0;
        span {
          display: inline-block;
          width: 30%;
          color: #5a88ff;
          cursor: pointer;
        }
      }
      .mainList {
        padding-left: 20px;
        span {
          display: inline-block;
          margin-right: 5px;
        }
        /deep/ .el-radio {
          margin-right: 10px;
        }
        .bgDiv {
          display: flex;
          flex-direction: column;
          .customBgColor {
            display: flex;
            /deep/ .el-radio {
              margin-right: 5px;
              display: flex;
              justify-content: center;
              align-items: center;
              padding-top: 5px;
            }
          }
        }
      }
      .bgContainer {
        display: flex;
        margin: 10px 0;
        span {
          margin-right: 9px;
          display: inline-block;
        }
        /deep/ .el-radio {
          margin-bottom: 10px;
        }
      }
      .allGroup {
        display: flex;
        margin-top: 10px;
        span {
          margin-right: 9px;
        }
      }
      .menuLocation {
        margin-top: 10px;
      }
      .listStyle {
        display: flex;
        margin-top: 10px;
        span {
          margin-right: 9px;
        }
        .listStyleFirstDiv {
          margin-bottom: 10px;
        }
      }
      .foorter {
        width: 440px;
        padding: 10px 20px;
        background: #fff;
        border: 1px dashed #d3d3d3;
        position: relative;
        margin-top: 10px;
        span {
          display: inline-block;
          margin-right: 6px;
        }
        /deep/ .el-radio {
          margin-right: 5px;
        }
        .endDiv {
          display: flex;
          span {
            white-space: nowrap;
            display: inline-block;
            margin-right: 10px;
          }
          /deep/ .el-checkbox {
            margin-right: 5px;
          }
          .buyBtnHidden {
            padding-left: 45px;
            margin-bottom: 10px;
            /deep/ .el-radio__label {
              padding-left: 3px;
            }
            /deep/ .el-radio {
              margin-right: 0px;
            }
            .new_class {
              position: relative;
              top: 2px;
              font-size: 32px !important;
            }
            .right_buy {
              width: 70px;
              height: 30px;
              text-align: center;
              line-height: 30px;
              background: rebeccapurple;
              color: white;
              font-size: 12px;
              border-radius: 15px;
              display: inline-block;
            }
            .cart_buy {
              width: 55px;
              height: 30px;
              text-align: center;
              line-height: 30px;
              border: 1px solid rebeccapurple;
              color: rebeccapurple;
              font-size: 12px;
              border-radius: 15px;
              background: white;
              display: inline-block;
            }
          }
        }
      }
      .hiddenGoodsModules {
        position: relative;
        width: 440px;
        padding: 10px 20px;
        background: #fff;
        margin-top: 10px;
        border: 1px dashed #d3d3d3;
        .nameContainer {
          display: flex;
          margin: 10px 0;
          span {
            white-space: nowrap;
            height: 32px;
            line-height: 32px;
          }
          /deep/ .el-input {
            width: 137px;
          }
        }
      }
      .groupItemOperation {
        position: absolute;
        right: 8px;
        top: 2px;
        display: block;
        z-index: 1;
        img {
          cursor: pointer;
        }
      }
    }
    //end
  }
}
</style>
