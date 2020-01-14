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
        <div v-if='linkageData.sort_group_arr.length'>
          <div
            class="hiddenGoodsModules"
            v-for="(item,index) in linkageData.sort_group_arr"
            :key="index"
          >
            <div>
              <span>{{item.sort_type===0?'商家分类':item.sort_type===1?'商家标签':'商家品牌'}}：</span>
              <span style="display:inline-block;width:100px">{{item.sort_name}}</span>
              <span
                @click="handleToEditData(index)"
                style="padding-left: 20px;color: #5A8BFF;cursor: pointer;"
              >修改</span>
            </div>
            <div class="nameContainer">
              <span>自定义分组名称：</span>
              <el-input
                v-model="item.group_name"
                size="small"
              ></el-input>
            </div>
            <div>
              <span>展示商品数量：</span>
              <el-radio
                v-model="item.radio"
                @change="handleToClickShowNumRadio(index)"
                label="1"
              >全部{{item.sort_goods_num}}件</el-radio>
              <el-radio
                v-model="item.radio"
                @change="handleToClickShowNumRadio(index)"
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
            v-model="linkageData.menu_style"
            label="0"
          >顶部展示商品分组</el-radio>
          <el-radio
            v-model="linkageData.menu_style"
            label="1"
          >左侧展示商品分组</el-radio>
        </div>
        <div class="mainList bgContainer">
          <span>背景颜色</span>
          <div class="bgDiv">
            <el-radio
              v-model="linkageData.goods_module_bg"
              label="0"
            >与页面背景颜色一致</el-radio>
            <div class="customBgColor">
              <el-radio
                v-model="linkageData.goods_module_bg"
                label="1"
              >自定义</el-radio>
              <span>
                <el-color-picker
                  v-model="linkageData.goods_bg_color"
                  show-alpha
                  :disabled="linkageData.goods_module_bg==='0'?true:false"
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
        <div v-if="linkageData.menu_style === '0'">
          <div class="mainList allGroup">
            <span>全部分组</span>
            <div>
              <div style="color:#999;margin-bottom:10px">全部分组将展示已选商品分组种的所有商品</div>
              <div>
                <el-radio
                  v-model="linkageData.group_display"
                  label="1"
                >展示</el-radio>
                <el-radio
                  v-model="linkageData.group_display"
                  label="0"
                >不展示</el-radio>
              </div>
            </div>

          </div>
          <div class="mainList menuLocation">
            <span>菜单位置</span>
            <el-radio
              v-model="linkageData.position_style"
              label="0"
            >一般样式</el-radio>
            <el-radio
              v-model="linkageData.position_style"
              label="1"
            >滚动至顶部固定</el-radio>
          </div>
          <div class="mainList listStyle">
            <span>商品列表样式</span>
            <div>
              <div class="listStyleFirstDiv">
                <el-radio
                  v-model="linkageData.shop_style"
                  label="0"
                >大图展示</el-radio>
                <el-radio
                  v-model="linkageData.shop_style"
                  label="1"
                >一行两个</el-radio>
                <el-radio
                  v-model="linkageData.shop_style"
                  label="2"
                >一行三个</el-radio>
              </div>
              <div>
                <el-radio
                  v-model="linkageData.shop_style"
                  label="3"
                >商品列表</el-radio>
                <el-radio
                  v-model="linkageData.shop_style"
                  label="4"
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
              v-model="linkageData.module_style"
              label="1"
            >白底无边框</el-radio>
            <el-radio
              v-model="linkageData.module_style"
              label="2"
            >边框投影</el-radio>
            <el-radio
              v-model="linkageData.module_style"
              label="3"
            >白底有边框</el-radio>
          </div>
          <div style="margin:10px 0">
            <span>模块角度</span>
            <el-radio
              v-model="linkageData.if_radius"
              label="0"
            >直角</el-radio>
            <el-radio
              v-model="linkageData.if_radius"
              label="1"
            >圆角</el-radio>
          </div>
          <div class="endDiv">
            <span>显示内容</span>
            <div>
              <div>
                <el-checkbox v-model="linkageData.show_name">商品名称</el-checkbox>
                <el-checkbox v-model="linkageData.show_price">商品价格</el-checkbox>
              </div>
              <div style="margin:10px 0">
                <el-checkbox v-model="linkageData.cart_btn">购买按钮</el-checkbox>
                <span style="color:#999">显示购买按钮时将不显示其他信息</span>
              </div>
              <!--购买按钮checkbox选中后显示的隐藏模块-->
              <div
                class="buyBtnHidden"
                v-if="linkageData.cart_btn"
              >
                <el-radio
                  v-model="linkageData.cart_btn_choose"
                  label="0"
                >
                  <i
                    class="iconfont icontianjia icon_font_size new_class"
                    style="color: rgb(177, 78, 105);"
                  ></i>
                </el-radio>
                <el-radio
                  v-model="linkageData.cart_btn_choose"
                  label="1"
                ><i
                    class="iconfont icongouwuche1 icon_font_size new_class"
                    style="color: rgb(177, 78, 105);"
                  ></i></el-radio>
                <el-radio
                  v-model="linkageData.cart_btn_choose"
                  label="2"
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
                  label="3"
                >
                  <i
                    class="cart_buy"
                    style="color: rgb(177, 78, 105); border-color: rgb(177, 78, 105);"
                  >购买</i>
                </el-radio>
              </div>
              <!--end-->
              <div style="margin-bottom:10px">
                <el-checkbox v-model="linkageData.other_message">其他信息</el-checkbox>
                <span style="color:#999">后台数据仅为参考请以实际显示为准</span>
              </div>
              <!--其他信息checkbox选中后显示的隐藏模块-->
              <div v-if="linkageData.other_message">
                <el-radio
                  v-model="linkageData.show_market"
                  label="1"
                >市场价</el-radio>
                <el-radio
                  v-model="linkageData.show_market"
                  label="2"
                >销量</el-radio>
                <el-radio
                  v-model="linkageData.show_market"
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
    <!--删除提示弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="delVisible"
      :append-to-body='true'
      width="30%"
    >
      <div style="width:100%;text-align:center"><span>确认要删除吗？</span></div>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToDel()"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--商家分类弹窗-->
    <AddingBusClassDialog
      :dialogVisible.sync='classificationDialogVisible'
      :classFlag='1'
      :backDataArr='backDataArr'
      @BusClassTrueDetailData='busClassTrueDetailData'
      :singleElection="true"
    />
    <!--商品标签弹窗-->
    <AddProductLabel
      :callAddProductLabel.sync='callAddProductLabel'
      @handleToGetBackData='handleToGetBackData'
      :brandBackData="[]"
      :singleElection="true"
    />
    <!--商品品牌弹窗-->
    <AddBrandDialog
      :callAddBrand.sync='callAddBrand'
      @handleToGetBackData='handleToGetBrandBackData'
      :brandBackData="[]"
      :singleElection="true"
    />
    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="chooseGoodsBack"
      @resultGoodsDatas="resultGoodsDatas"
    />
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  mixins: [decMixins],
  components: {
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog'), // 选择商家分类标签弹窗
    AddProductLabel: () => import('@/components/admin/addProductLabel'), // 选择商品标签弹窗
    AddBrandDialog: () => import('@/components/admin/addBrandDialog'), // 选择商品品牌弹窗
    ChoosingGoods: () => import('@/components/admin/choosingGoods') // 选择商品弹窗
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
        // menu_style: '0', // 菜单样式radio
        // goods_module_bg: '0', // 背景颜色radio
        // goods_bg_color: '', // 背景自定义颜色
        // group_display: '1', // 全部分组radio
        // position_style: '0', // 菜单位置radio
        // shop_style: '1', // 商品列表样式radio
        // module_style: '1', // 模块样式radio
        // if_radius: '0', // 模块角度radio
        // show_name: false, // 商品名称
        // show_price: false, // 商品价格
        // cart_btn: false, // 购买按钮checkbox
        // cart_btn_choose: '0', // 购买按钮选中显示模块radio
        // other_message: false, // 其他信息按钮
        // show_market: '1', // 其它信息按钮下隐藏模块radio
        // sort_group_arr: [] // 商品分组菜单隐藏模块数据列表
      },
      clickEditBtn: false, // 是否点击修改按钮
      editIndex: null, // 当前修改的index
      delVisible: false, // 删除提示框flag
      delIndex: null, // 删除下标
      tuneUpChooseGoods: false, // 选择商品弹窗调起
      chooseGoodsBack: [], // 选择商品弹窗回显
      nowClickAppointIndex: null
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        let turnToString = this.handleToTurnNumToStr(this.modulesData)
        console.log(turnToString)
        // 转换checkbox字段
        let moduleData = this.handleToTurnCheckbox(turnToString, true)
        console.log(moduleData)
        if (moduleData) {
          this.linkageData = moduleData
          this.$forceUpdate()
        }
      },
      immediate: true
    },
    // 监听数据变换
    linkageData: {
      handler (newData) {
        console.log(newData)
        // 测试数据
        newData['sort_length'] = newData.sort_group_arr.length
        newData['goods_img'] = [
          'http://mpdevimg2.weipubao.cn/upload/0/image/20191018/crop_KXCyQS7bFi7w4RgL.jpeg',
          'http://mpdevimg2.weipubao.cn/upload/4748160/image/20191218/SQzKExx7QTSH1kzu.jpeg'
        ]
        newData['goods_name'] = [
          '海阔跳的高',
          '门店商品8--勿动'
        ]
        newData['goods_price'] = [
          '200.00',
          '100.00'
        ]
        newData['market_price'] = [
          '500.00',
          null
        ]
        newData['goods_tag'] = [
          [
            '满减'
          ],
          [
            '满减',
            '领券减￥1'
          ]
        ]
        newData['label'] = [
          {
            'label_class': 'label-style1',
            'label_parttern': 1,
            'label_name': '特价商品',
            'new_label_img': ''
          },
          {
            'label_class': 'label-style4',
            'label_parttern': 4,
            'label_name': '新品首发',
            'new_label_img': ''
          }
        ]
        console.log(newData)
        let moduleData = this.handleToTurnCheckbox(newData, false)
        this.$emit('handleToBackData', moduleData)
      },
      deep: true
    },
    // 购买按钮
    'linkageData.cart_btn' (newData) {
      if (newData) {
        this.linkageData.other_message = false
      }
    },
    // 其他信息按钮
    'linkageData.other_message' (newData) {
      if (newData) {
        this.linkageData.cart_btn = false
      }
    }
  },
  methods: {
    // 转换checkbox字段
    handleToTurnCheckbox (data, flag) {
      console.log(data)
      let newData = JSON.parse(JSON.stringify(data))
      let arr = ['show_name', 'show_price', 'cart_btn', 'other_message']
      Object.keys(newData).forEach((item, index) => {
        console.log(item)
        if (arr.indexOf(item) !== -1) {
          console.log(flag, newData[item])
          if (flag) {
            if (newData[item] === '1') {
              newData[item] = true
            } else if (newData[item] === '0') {
              newData[item] = false
            }
          } else {
            if (newData[item]) {
              newData[item] = '1'
            } else {
              newData[item] = '0'
            }
          }
        }
      })
      console.log(newData, flag)
      return newData
    },
    // 背景颜色自定义点击重置
    handleToReset () {
      this.linkageData.goods_bg_color = this.defaultBgColor
    },
    // 调起弹窗
    handleToCallDialog (flag) {
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
        let obj = { sort_type: 0, radio: '1' }
        console.log(item.goodsSumNum)
        if (item.goodsSumNum !== undefined) {
          obj.sort_name = item.sortName
          obj.group_name = item.sortName
          obj.sort_goods_num = item.goodsSumNum
          obj.sort_id = item.sortId
          obj.group_goods_num = ''
          obj.group_goods_id = ''
          obj.is_all = 1
          arr.push(obj)
        }
      })
      console.log(this.clickEditBtn, this.editIndex, arr)
      if (this.clickEditBtn) {
        this.linkageData.sort_group_arr[this.editIndex] = arr[0]
      } else {
        let newArr = this.linkageData.sort_group_arr.concat(arr)
        this.linkageData.sort_group_arr = newArr
      }
      this.clickEditBtn = false
      console.log(this.linkageData.sort_group_arr, arr)
    },
    // 商品标签弹窗选中回传数据
    handleToGetBackData (data) {
      console.log(data)
      let arr = []
      data.forEach((item, index) => {
        //  obj
        let obj = { type: 1, radio: '1' }
        console.log(item.goodsSumNum)
        obj.sort_name = item.name
        obj.group_name = item.name
        obj.sort_goods_num = item.goodsNum
        obj.sort_id = item.id
        obj.group_goods_num = ''
        obj.group_goods_id = ''
        obj.is_all = 1
        arr.push(obj)
      })
      console.log(this.clickEditBtn, this.editIndex, arr)
      if (this.clickEditBtn) {
        this.linkageData.sort_group_arr[this.editIndex] = arr[0]
      } else {
        let newArr = this.linkageData.sort_group_arr.concat(arr)
        this.linkageData.sort_group_arr = newArr
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
        obj.sort_name = item.brandName
        obj.group_name = item.brandName
        obj.sort_goods_num = item.goodsNum
        obj.sort_id = item.id
        obj.group_goods_num = ''
        obj.group_goods_id = ''
        obj.is_all = 1
        arr.push(obj)
      })
      if (this.clickEditBtn) {
        this.linkageData.sort_group_arr[this.editIndex] = arr[0]
      } else {
        let newArr = this.linkageData.sort_group_arr.concat(arr)
        this.linkageData.sort_group_arr = newArr
      }
      this.clickEditBtn = false
    },
    handleToEditData (index) { // 点击修改
      this.clickEditBtn = true
      this.editIndex = index
      let flag = this.linkageData.sort_group_arr[index].type
      this.handleToCallDialog(flag, true)
    },
    handleToClickTopIcon (flag, index) { // 顶部icon点击统一处理
      let arr = JSON.parse(JSON.stringify(this.linkageData.sort_group_arr))
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
          this.delVisible = true
          this.delIndex = index
          break
      }
      console.log(arr)
      this.linkageData.sort_group_arr = arr
    },
    // 删除框确认
    handleToDel () {
      this.linkageData.sort_group_arr.splice(this.delIndex, 1)
      this.delVisible = false
    },
    // 点击指定商品
    handleToClickShowNumRadio (index) {
      this.nowClickAppointIndex = index
      console.log(index, this.linkageData.sort_group_arr[index].radio)
      if (this.linkageData.sort_group_arr[index].radio === '2') {
        this.tuneUpChooseGoods = !this.tuneUpChooseGoods
      }
    },
    // 选择商品弹窗数据回传
    resultGoodsDatas (res) {
      console.log(res, this.linkageData.sort_group_arr, this.nowClickAppointIndex)
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
