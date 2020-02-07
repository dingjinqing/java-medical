<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('commodityGrouping.commodityGroupingModule')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="tips">
          {{$t('commodityGrouping.menu')}}<span style="color:#999">{{$t('commodityGrouping.groupsCanBeSelected')}}</span>
        </div>
        <!--添加商家分类、商品标签、商品品牌数据后显示的隐藏模块-->
        <div v-if='linkageData.sort_group_arr.length'>
          <div
            class="hiddenGoodsModules"
            v-for="(item,index) in linkageData.sort_group_arr"
            :key="index"
          >
            <div>
              <span>{{item.sort_type===0?$t('commodityGrouping.merchantClassification'):item.sort_type===1?$t('commodityGrouping.merchantLabel'):$t('commodityGrouping.merchantBrand')}}：</span>
              <span style="display:inline-block;width:100px">{{item.sort_name}}</span>
              <span
                @click="handleToEditData(index)"
                style="padding-left: 20px;color: #5A8BFF;cursor: pointer;"
              >{{$t('commodityGrouping.modify')}}</span>
            </div>
            <div class="nameContainer">
              <span>{{$t('commodityGrouping.customGroupName')}}</span>
              <el-input
                v-model="item.group_name"
                size="small"
              ></el-input>
            </div>
            <div>
              <span>{{$t('commodityGrouping.displayQuantity')}}</span>
              <el-radio
                v-model="item.is_all"
                @change="handleToClickShowNumRadio(index)"
                :label="1"
              >{{$t('commodityGrouping.whole')}}{{item.sort_goods_num}}{{$t('commodityGrouping.piece')}}</el-radio>
              <el-radio
                v-model="item.is_all"
                @change="handleToClickShowNumRadio(index)"
                :label="2"
              >{{$t('commodityGrouping.designatedCommodity')}}</el-radio>
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
          >+{{$t('commodityGrouping.addMerchantCategory')}}</span>
          <span
            @click="handleToCallDialog(1)"
            style="border-right:1px dashed #D3D3D3"
          >+{{$t('commodityGrouping.addProductLabel')}}</span>
          <span @click="handleToCallDialog(2)">+{{$t('commodityGrouping.addBrand')}}</span>
        </div>
        <div
          class="mainList"
          style="display:flex"
        >
          <span>{{$t('commodityGrouping.menuStyle')}}</span>
          <div :style="columnFlag?'display:flex;flex-direction: column':''">
            <el-radio
              v-model="linkageData.position_style"
              label="0"
            >{{$t('commodityGrouping.showProductGroups')}}</el-radio>
            <el-radio
              v-model="linkageData.position_style"
              label="1"
            >{{$t('commodityGrouping.groupsOnTheLeft')}}</el-radio>
          </div>

        </div>
        <div class="mainList bgContainer">
          <span>{{$t('commodityGrouping.backgroundColor')}}</span>
          <div class="bgDiv">
            <el-radio
              v-model="linkageData.goods_module_bg"
              label="0"
            >{{$t('commodityGrouping.pageBackgroundColor')}}</el-radio>
            <div class="customBgColor">
              <el-radio
                v-model="linkageData.goods_module_bg"
                label="1"
              >{{$t('commodityGrouping.custom')}}</el-radio>
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
                >{{$t('commodityGrouping.reset')}}</el-button>
              </div>
            </div>

          </div>

        </div>
        <div v-if="linkageData.position_style === '0'">
          <div class="mainList allGroup">
            <span>{{$t('commodityGrouping.allGrouped')}}</span>
            <div>
              <div style="color:#999;margin-bottom:10px">{{$t('commodityGrouping.allGroupedTip')}}</div>
              <div>
                <el-radio
                  v-model="linkageData.group_display"
                  label="1"
                >{{$t('commodityGrouping.exhibition')}}</el-radio>
                <el-radio
                  v-model="linkageData.group_display"
                  label="0"
                >{{$t('commodityGrouping.notShow')}}</el-radio>
              </div>
            </div>

          </div>
          <div class="mainList menuLocation">
            <span>{{$t('commodityGrouping.menuLocation')}}</span>
            <el-radio
              v-model="linkageData.menu_style"
              label="0"
            >{{$t('commodityGrouping.generaSstyle')}}</el-radio>
            <el-radio
              v-model="linkageData.menu_style"
              label="1"
            >{{$t('commodityGrouping.scrollToTopFixed')}}</el-radio>
          </div>
          <div class="mainList listStyle">
            <span>{{$t('commodityGrouping.productListStyle')}}</span>
            <div>
              <div class="listStyleFirstDiv">
                <el-radio
                  v-model="linkageData.shop_style"
                  label="1"
                >{{$t('commodityGrouping.bigPictureDisplay')}}</el-radio>
                <el-radio
                  v-model="linkageData.shop_style"
                  label="2"
                >{{$t('commodityGrouping.twoRows')}}</el-radio>
                <el-radio
                  v-model="linkageData.shop_style"
                  label="3"
                >{{$t('commodityGrouping.threeRows')}}</el-radio>
              </div>
              <div>
                <el-radio
                  v-model="linkageData.shop_style"
                  label="4"
                >{{$t('commodityGrouping.listOfCommodities')}}</el-radio>
                <el-radio
                  v-model="linkageData.shop_style"
                  label="5"
                >{{$t('commodityGrouping.rowSlip')}}</el-radio>
              </div>
            </div>

          </div>
        </div>

        <!--底部模块-->
        <div class="foorter">
          <div style="display:flex">
            <span>{{$t('commodityGrouping.moduleStyle')}}</span>
            <div :style="columnFlag?'display:flex;flex-direction: column':''">
              <el-radio
                v-model="linkageData.module_style"
                label="1"
              >{{$t('commodityGrouping.whiteBackground')}}</el-radio>
              <el-radio
                v-model="linkageData.module_style"
                label="2"
              >{{$t('commodityGrouping.frameProjection')}}</el-radio>
              <el-radio
                v-model="linkageData.module_style"
                label="3"
              >{{$t('commodityGrouping.whiteWithBorder')}}</el-radio>
            </div>

          </div>
          <div style="margin:10px 0">
            <span>{{$t('commodityGrouping.moduleAngle')}}</span>
            <el-radio
              v-model="linkageData.if_radius"
              label="0"
            >{{$t('commodityGrouping.rightAngle')}}</el-radio>
            <el-radio
              v-model="linkageData.if_radius"
              label="1"
            >{{$t('commodityGrouping.fillet')}}</el-radio>
          </div>
          <div class="endDiv">
            <span>{{$t('commodityGrouping.showContents')}}</span>
            <div>
              <div>
                <el-checkbox v-model="linkageData.show_name">{{$t('commodityGrouping.tradeName')}}</el-checkbox>
                <el-checkbox v-model="linkageData.show_price">{{$t('commodityGrouping.commodityPrice')}}</el-checkbox>
              </div>
              <div :style="columnFlag?'margin:10px 0':'margin:10px 0;display:flex'">
                <el-checkbox v-model="
                linkageData.cart_btn">{{$t('commodityGrouping.purchaseButton')}}</el-checkbox>
                <div>
                  <span style="color:#999;white-space: pre-wrap;width: 330px">{{$t('commodityGrouping.purchaseButtonTip')}}</span>

                </div>
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
                    {{$t('commodityGrouping.grabAtOnce')}}
                  </i>
                </el-radio>
                <el-radio
                  v-model="linkageData.cart_btn_choose"
                  label="3"
                >
                  <i
                    class="cart_buy"
                    style="color: rgb(177, 78, 105); border-color: rgb(177, 78, 105);"
                  >{{$t('commodityGrouping.purchase')}}</i>
                </el-radio>
              </div>
              <!--end-->
              <div :style="columnFlag?'margin-bottom:10px':'margin-bottom:10px;display:flex'">
                <el-checkbox v-model="linkageData.other_message">{{$t('commodityGrouping.otherInformation')}}</el-checkbox>
                <div>
                  <span style="color:#999;white-space: pre-wrap;width: 330px">{{$t('commodityGrouping.otherInformationTip')}}</span>
                </div>
              </div>
              <!--其他信息checkbox选中后显示的隐藏模块-->
              <div v-if="linkageData.other_message">
                <el-radio
                  v-model="linkageData.show_market"
                  label="1"
                >{{$t('commodityGrouping.marketValue')}}</el-radio>
                <el-radio
                  v-model="linkageData.show_market"
                  label="2"
                >{{$t('commodityGrouping.salesVolume')}}</el-radio>
                <el-radio
                  v-model="linkageData.show_market"
                  label="3"
                >{{$t('commodityGrouping.evaluationNumber')}}</el-radio>
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
      :title="$t('commodityGrouping.tips')"
      :visible.sync="delVisible"
      :append-to-body='true'
      width="30%"
    >
      <div style="width:100%;text-align:center"><span>{{$t('commodityGrouping.wantToDelete')}}</span></div>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="delVisible = false">{{$t('commodityGrouping.cancel')}}</el-button>
        <el-button
          type="primary"
          @click="handleToDel()"
        >{{$t('commodityGrouping.Determine')}}</el-button>
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
      @resultGoodsIds="resultGoodsDatas"
      :initialConditionRender="initialConditionRender"
    />
  </div>
</template>
<script>
// getGoodsNum
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
import { getGoodsNum } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting.js'
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
      nowClickAppointIndex: null,
      initialConditionRender: [], // 选择商品弹窗初始渲染条件
      reLoad: true,
      columnFlag: false
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
        // newData['sort_length'] = newData.sort_group_arr.length
        // newData['goods_img'] = [
        //   'http://mpdevimg2.weipubao.cn/upload/0/image/20191018/crop_KXCyQS7bFi7w4RgL.jpeg',
        //   'http://mpdevimg2.weipubao.cn/upload/4748160/image/20191218/SQzKExx7QTSH1kzu.jpeg'
        // ]
        // newData['goods_name'] = [
        //   '海阔跳的高',
        //   '门店商品8--勿动'
        // ]
        // newData['goods_price'] = [
        //   '200.00',
        //   '100.00'
        // ]
        // newData['market_price'] = [
        //   '500.00',
        //   null
        // ]
        // newData['goods_tag'] = [
        //   [
        //     '满减'
        //   ],
        //   [
        //     '满减',
        //     '领券减￥1'
        //   ]
        // ]
        // newData['label'] = [
        //   {
        //     'label_class': 'label-style1',
        //     'label_parttern': 1,
        //     'label_name': '特价商品',
        //     'new_label_img': ''
        //   },
        //   {
        //     'label_class': 'label-style4',
        //     'label_parttern': 4,
        //     'label_name': '新品首发',
        //     'new_label_img': ''
        //   }
        // ]
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
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 商品数据获取
    handleToRequestGoodsData () {
      let arr = []
      console.log(this.linkageData.sort_group_arr)
      this.linkageData.sort_group_arr.forEach((item, index) => {
        let obj = {}
        switch (item.sort_type) {
          case 0:
            obj['sortId'] = item.sort_id
            break
          case 1:
            obj['labelId'] = item.sort_id
            break
          case 2:
            obj['brandId'] = item.sort_id
        }
        arr.push(obj)
      })
      let params = {
        'goodsNumCountParams': arr
      }
      getGoodsNum(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          res.content.forEach((item, index) => {
            this.linkageData.sort_group_arr[index].sort_goods_num = item
          })
        }
      })
    },
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
            if (newData[item] === '0') {
              newData[item] = true
            } else if (newData[item] === '1') {
              newData[item] = false
            }
          } else {
            if (newData[item]) {
              newData[item] = '0'
            } else {
              newData[item] = '1'
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
      console.log(flag)
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
        let obj = { sort_type: 0 }
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
      this.handleToRequestGoodsData() // 查包含商品数量
    },
    // 商品标签弹窗选中回传数据
    handleToGetBackData (data) {
      console.log(data)
      let arr = []
      data.forEach((item, index) => {
        //  obj
        let obj = { sort_type: 1 }
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
      this.handleToRequestGoodsData()
    },
    handleToGetBrandBackData (data) { // 商品品牌弹窗数据回传
      console.log(data)
      let arr = []
      data.forEach((item, index) => {
        //  obj
        let obj = { sort_type: 2 }
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
      this.handleToRequestGoodsData()
    },
    handleToEditData (index) { // 点击修改
      console.log()
      this.clickEditBtn = true
      this.editIndex = index
      let flag = this.linkageData.sort_group_arr[index].sort_type
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
      this.handleToRequestGoodsData()
    },
    // 删除框确认
    handleToDel () {
      this.linkageData.sort_group_arr.splice(this.delIndex, 1)
      this.delVisible = false
      this.handleToRequestGoodsData()
    },
    // 点击指定商品
    handleToClickShowNumRadio (index) {
      this.reLoad = false
      console.log(this.linkageData.sort_group_arr[index])
      this.nowClickAppointIndex = index
      console.log(index, this.linkageData.sort_group_arr[index].is_all)
      if (this.linkageData.sort_group_arr[index].is_all === 2) {
        this.tuneUpChooseGoods = !this.tuneUpChooseGoods
        let arr = []
        arr[0] = this.linkageData.sort_group_arr[index].sort_type
        arr[1] = this.linkageData.sort_group_arr[index].sort_id
        this.initialConditionRender = arr
      }
    },
    // 选择商品弹窗数据回传
    resultGoodsDatas (res) {
      // group_goods_id
      console.log(res, this.linkageData.sort_group_arr, this.nowClickAppointIndex)
      console.log(res.join(','))
      this.linkageData.sort_group_arr[this.nowClickAppointIndex].group_goods_id = res.join(',')
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
        display: flex;
        align-items: center;
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
