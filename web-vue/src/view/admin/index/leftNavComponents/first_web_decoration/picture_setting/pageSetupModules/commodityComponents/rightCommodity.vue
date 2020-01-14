<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('commodity.commodityModule')}}</h2>
      <div class="main">
        <div class="title">
          <span>{{$t('commodity.moduleHeader')}}:</span>
          <div>
            <el-radio
              v-model="data.goods_module_title"
              label="0"
            >{{$t('commodity.notSetUp')}}</el-radio>
            <el-radio
              v-model="data.goods_module_title"
              label="1"
            >{{$t('commodity.textTitle')}}</el-radio>
            <el-radio
              v-model="data.goods_module_title"
              label="2"
            >{{$t('commodity.pictureTitle')}}</el-radio>
          </div>
        </div>
        <!--模块标题隐藏模块-->
        <div
          class="titleHidden"
          v-if="data.goods_module_title==='1' || data.goods_module_title==='2'"
        >
          <div class="titleHiddenMain">
            <div class="topTitle">
              <span>{{$t('commodity.title')}}：</span>
              <el-input
                v-model="data.title"
                size="small"
              ></el-input>
              <span>{{$t('commodity.upToWords')}}</span>
            </div>
            <div class="topLink">
              <span>{{$t('commodity.titleLink')}}：</span>
              <el-input
                v-model="data.title_link"
                size="small"
              ></el-input>
              <el-button
                size="small"
                @click="tuneUpSelectLink = !tuneUpSelectLink"
              >{{$t('commodity.selectLink')}}</el-button>
            </div>
            <div class="topPosition">
              <span>{{$t('commodity.titleLocation')}}：</span>
              <el-checkbox v-model="data.tit_center">{{$t('commodity.titleCentered')}}</el-checkbox>
            </div>
            <div class="bgImg">
              <span>{{data.goods_module_title==='1'?$t('commodity.icon'):data.goods_module_title==='2'?$t('commodity.titlePicture'):''}}：</span>
              <div class="bgIcon">

                <img
                  v-if="data.goods_module_title==='1'?!data.img_url:!data.img_title_url"
                  :src="$imageHost+'/image/admin/add_img_bg.png'"
                  class="bgImgDiv"
                  @click="handleToAddModulesImg()"
                />
                <img
                  v-else
                  style="width:100%;height:40px"
                  :src="data.goods_module_title==='1'?data.img_url:data.img_title_url"
                  @click="handleToAddModulesImg()"
                >
                <i
                  v-if="(data.img_url&&data.goods_module_title==='1')||(data.img_title_url&&data.goods_module_title==='2')"
                  @click="handleToDelIcon()"
                ></i>
              </div>
            </div>
          </div>
        </div>
        <!--模块标题隐藏模块end-->
        <!--列表样式模块-->
        <div class="listStyle">
          <div class="title">{{$t('commodity.listStyle')}}</div>
          <div class="content">
            <div
              class="typeContainer"
              v-for="(item,index) in listTypeData"
              :key="index"
              :style="item.isChecked?'border: 1px solid #5c81f4;':''"
              @click="handleToClickType(index)"
            >
              <div class="type">
                <div class="typeTop">
                  <span
                    class="odd_left"
                    v-if="index===0"
                  ></span>
                  <div
                    class="odd_right"
                    :style="(index===1||index===2 || index===3)?'display:flex;width:100%;':index===4?'display:flex;width:100%;flex-direction: column':''"
                  >
                    <span :style="index===1?'width:45%;height:20px;margin-right:5px':index===2?'width:30%;height:20px;':index===3?'width:30%;height:40px;':index===4?'width:100%;height:20px;':''"></span>
                    <span :style="index===1?'width:45%;height:20px;margin-top:0':index===2?'width:30%;height:20px;margin:0 5px':index===3?'width:30%;height:40px;margin:0 5px;':index===4?'width:100%;height:20px;':''"></span>
                    <span
                      v-if="index!==4"
                      :style="index===1?'width:45%;height:20px;margin-right:5px':index===2?'width:30%;height:20px;margin-top:0':index===3?'width:30%;height:40px;margin-top:0':''"
                    ></span>
                    <span
                      v-if="(index!==3 && index!==4)"
                      :style="index===1?'width:45%;height:20px;margin-top:8px':index===2?'width:30%;height:20px;':''"
                    ></span>
                    <span
                      v-if="index===2"
                      :style="index===2?'width:30%;height:20px;margin:0 5px':''"
                    ></span>
                    <span
                      v-if="index===2"
                      :style="index===2?'width:30%;height:20px':''"
                    ></span>
                  </div>
                </div>
                <p>{{item.typeName}}</p>
              </div>
            </div>
          </div>
        </div>
        <!--商品模块-->
        <div class="commodityModule">
          <span style="margin-bottom:10px">{{$t('commodity.commodityModule')}}</span>
          <div class="commodityMain">
            <div
              class="commodityAngle"
              v-if="this.listTypeData[4].isChecked"
            >
              <span>{{$t('commodity.bigPictureDisplay')}}：</span>
              <div class="angleDiv">
                <el-radio
                  v-model="data.goods_display"
                  label="0"
                >{{$t('commodity.tiling')}}</el-radio>
                <el-radio
                  v-model="data.goods_display"
                  label="1"
                >{{$t('commodity.centered')}}</el-radio>
                <el-radio
                  v-model="data.goods_display"
                  label="2"
                >{{$t('commodity.fullGraph')}}</el-radio>
              </div>
            </div>
            <div class="commodityContent">
              <div class="compatibleContent"><span style="width:80px;text-align:center;margin-right:8px">{{$t('commodity.showContents')}}：</span></div>

              <div class="contentRight">
                <div class="contentRightTop">
                  <el-checkbox v-model="data.hide_name">{{$t('commodity.tradeName')}}</el-checkbox>
                  <el-checkbox v-model="data.hide_price">{{$t('commodity.commodityPrice')}}</el-checkbox>
                  <el-checkbox v-model="data.hide_label">{{$t('commodity.commodityLabel')}}</el-checkbox>
                </div>
                <div class="contentDiv">
                  <el-checkbox v-model="data.cart_btn">{{$t('commodity.purchaseButton')}}</el-checkbox>
                  <span
                    class="specialTips"
                    style="color:#999;"
                  >{{$t('commodity.purchaseTips')}}</span>
                </div>
                <!--选中购买按钮隐藏模块-->
                <div
                  class="buyBtnHidden"
                  v-if="data.cart_btn"
                >
                  <el-radio
                    v-model="data.cart_btn_choose"
                    label="0"
                  >
                    <i
                      class="iconfont icontianjia icon_font_size new_class"
                      style="color: rgb(177, 78, 105);"
                    ></i>
                  </el-radio>
                  <el-radio
                    v-model="data.cart_btn_choose"
                    label="1"
                  ><i
                      class="iconfont icongouwuche1 icon_font_size new_class"
                      style="color: rgb(177, 78, 105);"
                    ></i></el-radio>
                  <el-radio
                    v-model="data.cart_btn_choose"
                    label="2"
                  >
                    <i
                      class="right_buy new_back"
                      style="background-color: rgb(177, 78, 105);"
                    >
                      {{$t('commodity.grabAtOnce')}}
                    </i>
                  </el-radio>
                  <el-radio
                    v-model="data.cart_btn_choose"
                    label="3"
                  >
                    <i
                      class="cart_buy"
                      style="color: rgb(177, 78, 105); border-color: rgb(177, 78, 105);"
                    >{{$t('commodity.purchase')}}</i>
                  </el-radio>
                </div>
                <!--end-->
                <div
                  class="contentDiv"
                  v-if="!this.listTypeData[2].isChecked"
                >
                  <el-checkbox v-model="data.other_message">{{$t('commodity.otherInformation')}}</el-checkbox>
                  <span
                    class="specialTips"
                    style="color:#999"
                  >{{$t('commodity.otherInformationTips')}}</span>
                </div>
                <div
                  class="contentDiv"
                  v-if="data.other_message"
                >
                  <el-radio
                    v-model="data.show_market"
                    label="1"
                  >{{$t('commodity.marketValue')}}</el-radio>
                  <el-radio
                    v-model="data.show_market"
                    label="2"
                  >{{$t('commodity.salesVolume')}}</el-radio>
                  <el-radio
                    v-model="data.show_market"
                    label="3"
                  >{{$t('commodity.evaluationNumber')}}</el-radio>
                </div>
              </div>
            </div>
            <div class="commodityAngle">
              <div class="compatibleContent">
                <span style="width:80px;text-align:center;margin-right:8px">{{$t('commodity.moduleAngle')}}：</span>
              </div>

              <div class="angleDiv">
                <el-radio
                  v-model="data.if_radius"
                  label="0"
                >{{$t('commodity.rightAngle')}}</el-radio>
                <el-radio
                  v-model="data.if_radius"
                  label="1"
                >{{$t('commodity.fillet')}}</el-radio>
              </div>
            </div>
            <div class="commodityAngle">
              <div class="compatibleContent">
                <span style="width:80px;text-align:center;margin-right:8px">{{$t('commodity.moduleStyle')}}：</span>
              </div>

              <div class="angleDiv">
                <el-radio
                  v-model="data.goods_module_style"
                  label="0"
                >{{$t('commodity.NoborderonwhiteBackground')}}</el-radio>
                <el-radio
                  v-model="data.goods_module_style"
                  label="1"
                >{{$t('commodity.frameProjection')}}</el-radio>
                <el-radio
                  v-model="data.goods_module_style"
                  label="2"
                >{{$t('commodity.whiteWithBorder')}}</el-radio>
              </div>
            </div>
            <div class="commodityAngle">
              <div class="compatibleContent">
                <span style="width:80px;text-align:center;margin-right:8px">{{$t('commodity.backgroundColor')}}：</span>
              </div>

              <div class="bgColorDiv">
                <el-radio
                  v-model="data.goods_module_bg"
                  label="0"
                >{{$t('commodity.consistentWithPageBackground')}}</el-radio>
                <div class="customBgColor">
                  <el-radio
                    v-model="data.goods_module_bg"
                    label="1"
                  >{{$t('commodity.custom')}}</el-radio>
                  <span class="colorSelect">
                    <el-color-picker
                      size="small"
                      v-model="data.goods_bg_color"
                      show-alpha
                      :predefine="predefineColors"
                      :disabled="data.goods_module_bg==='0'"
                    >
                    </el-color-picker>
                  </span>
                  <div style="margin-left:10px;margin-top:-1px">
                    <el-button
                      @click="handleToReset()"
                      size="small"
                    >{{$t('commodity.reset')}}</el-button>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
        <!--模块推荐-->
        <div class="moduleRecommendation">
          <div class="moduleTitleDiv">
            <span style="margin-bottom:10px">{{$t('commodity.moduleRecommendation')}}：</span>
            <div class="moduleTitleClass">
              <el-radio
                v-model="data.recommend_type"
                label="0"
              >{{$t('commodity.automaticRecommendation')}}</el-radio>
              <el-radio
                v-model="data.recommend_type"
                label="1"
              >{{$t('commodity.manualRecommendation')}}</el-radio>
            </div>
          </div>

          <!--自动推荐选中显示模块-->
          <div
            class="moduleRecMain"
            v-if="data.recommend_type === '0'"
          >
            <div class="manual">
              <div class="goodsNum">
                <span>{{$t('commodity.quantityOfCommodities')}}：</span>
                <el-select
                  v-model="data.goods_num"
                  :placeholder="$t('commodity.placeChiose')"
                  size="small"
                >
                  <el-option
                    v-for="item in commodityModule.goodsOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
              <div class="goodsPrice price">
                <span>{{$t('commodity.commodityPrice')}}：</span>
                <el-input
                  v-model="data.min_price"
                  size="small"
                ></el-input>
                <i style="display:inline-block;margin:0 5px">{{$t('commodity.reach')}}</i>
                <el-input
                  v-model="data.max_price"
                  size="small"
                ></el-input>
              </div>
              <div class="goodsPrice price keyWors">
                <span>{{$t('commodity.keyWord')}}：</span>
                <el-input
                  size="small"
                  v-model="data.keywords"
                ></el-input>
              </div>
              <div class="goodsPrice price commodityScope">
                <span>{{$t('commodity.commodityScope')}}：</span>
                <el-select
                  v-model="data.goods_area"
                  :placeholder="$t('commodity.placeChiose')"
                  size="small"
                  @change='handleToSelectRange()'
                >
                  <el-option
                    v-for="item in commodityModule.commodityScopeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
              <!--选中商品范围后显示的对应隐藏弹窗按钮-->
              <div
                class="hiddenRangeCheck"
                style="margin-left:100px"
                v-if="data.goods_area!=='0'"
              >
                <!--商家分类-->
                <div
                  class="rangeHiddenBtn"
                  @click="handleToClickRangeBtn(Number(data.goods_area))"
                >
                  {{rangeList[Number(data.goods_area)]}}
                </div>
                <div
                  style="height:30px;line-height:30px;margin-left:5px"
                  v-if="rangeCheckData.length>0"
                >{{$t('commodity.haveChosen')}}{{rangeHiddenRightText}}：{{rangeCheckData.length}}个{{rangeHiddenRightText}}</div>
              </div>
              <!--end-->
              <div class="goodsPrice price commodityScope">
                <span>{{$t('commodity.activeCommodities')}}：</span>
                <el-select
                  v-model="data.goods_type"
                  :placeholder="$t('commodity.placeChiose')"
                  size="small"
                >
                  <el-option
                    v-for="item in commodityModule.activeCommoditiesOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
              <div class="goodsPrice price commodityScope">
                <span>{{$t('commodity.sortRule')}}：</span>
                <el-select
                  v-model="data.sort_type"
                  size="small"
                >
                  <el-option
                    v-for="item in commodityModule.sortRuleOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
            </div>
          </div>
          <!--手动推荐选中显示模块-->
          <div
            class="moduleRecMain"
            v-if="data.recommend_type === '1'"
          >
            <div class="manual">
              <div class="goodsNum choiseGoodeDiv">
                <span>{{$t('commodity.listOfCommodities')}}：</span>
                <div>
                  <el-button
                    size="small"
                    @click="handleToAddGoods()"
                  >{{$t('commodity.addMerchandise')}}</el-button>
                  <div :style="goodsList.length>=3?'height:250px;width:112%;overflow-y: auto;margin-top:10px':''">
                    <div
                      v-for="(item,index) in data.goods_items"
                      :key="index"
                    >
                      <div class="goodsList">
                        <span>
                          <img :src="item.goodsImg">
                        </span>
                        <span>
                          {{item.goodsName}}
                        </span>
                      </div>
                      <div class="operation">
                        <a
                          href="javascript:void(0)"
                          :title="$t('commodity.upward')"
                          class="up_arrow"
                          @click="handleToClickOpera(index,0)"
                        >↑</a>
                        <a
                          href="javascript:void(0)"
                          :title="$t('commodity.down')"
                          class="up_arrow"
                          @click="handleToClickOpera(index,1)"
                        >↓</a>
                        <a
                          href="javascript:void(0)"
                          :title="$t('commodity.delete')"
                          class="up_arrow"
                          @click="handleToClickOpera(index,2)"
                        >X</a>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath='handleToSelectLinkPath'
    />
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp='tuneUp'
      :imageSize='imageSize'
      @handleSelectImg='handleSelectImg'
    />
    <!--选择商品弹窗-->
    <ChoosingGoods
      @resultGoodsDatas='handleToGetGoods'
      :tuneUpChooseGoods='tuneUpChooseGoods'
    />
    <!--添加商家分类、平台分类弹窗-->
    <AddingBusClassDialog
      :dialogVisible.sync="dialogVisible"
      :classFlag="classFlag"
      @BusClassTrueArr="handleToGetBackData"
    />
    <!--添加商品品牌弹窗-->
    <AddBrandDialog
      @handleToGetBackData='handleToGetBackData'
      :callAddBrand.sync='callAddBrand'
    />
    <!--添加商品标签弹窗-->
    <AddProductLabel
      @handleToGetBackData='handleToGetBackData'
      :callAddProductLabel.sync='callAddProductLabel'
    />
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
import { queryDataList } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting'
Vue.use(vcolorpicker)
export default {
  mixins: [decMixins],
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'), // 图片弹窗组件
    SelectLinks: () => import('@/components/admin/selectLinks'), // 选择链接弹窗
    ChoosingGoods: () => import('@/components/admin/choosingGoods'), // 选择商品弹窗
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog'), // 添加商家分类弹窗
    AddBrandDialog: () => import('@/components/admin/addBrandDialog'), // 添加商品品牌弹窗
    AddProductLabel: () => import('@/components/admin/addProductLabel') // 添加商品标签弹窗
  },
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      predefineColors: [ // 颜色选择器预设颜色池
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
        '#FF0000'
      ],
      classFlag: null, // 区分商家分类和平台分类flag
      dialogVisible: false, // 商家分类和平台分类弹窗调起flag
      imageSize: [], // 模块标题图标点击宽高限制
      callAddProductLabel: false, // 添加商品标签弹窗调起
      rangeCheckData: [], // 选择商品范围后当前范围显示的数据数组
      rangeHiddenRightText: '', // 选择商品范围后右侧出现的文本
      callAddBrand: false, // 调起选择商品品牌弹窗
      chooseGoodsBack: [], // 选择商品回显
      tuneUpChooseGoods: false, // 选择商品弹窗
      titleRadio: '1', // 模块标题radio
      titleInput: '', // 标题输入框
      titleLinkInput: '', // 标题链接输入框
      positionChecked: false, // 标题位置复选按钮
      iconImgUrl: '', // 图标icon url
      titleImgUrl: '', // 标题图片
      tuneUpSelectLink: false, // 调起选择链接弹窗
      tuneUp: false, // 调起选择图片弹窗
      commodityModule: {
        name: false, // 商品名称
        price: false, // 商品价格
        label: false, // 商品标签
        buyBtn: false, // 购买按钮
        contentRadio: '1', // 显示内容底部radio
        angleRadio: '1', // 模块角度radio
        styleRadio: '1', // 模块样式radio
        bgColorRadio: '1', // 背景颜色radio
        bgColor: '#f5f5f5', // 背景颜色自定义
        hiddenRadio: '1', // 选中购买按钮隐藏模块里radio
        otherInfoFlag: false, // 其它信息选中
        RecommendationRadio: '1', // 模块推荐  自动推荐  手动推荐
        goodsNum: '4', // 商品数量
        goodsOptions: [{ // 商品数量options
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }, {
          value: '6',
          label: '6'
        },
        {
          value: '7',
          label: '7'
        },
        {
          value: '8',
          label: '8'
        },
        {
          value: '9',
          label: '9'
        }, {
          value: '10',
          label: '10'
        }, {
          value: '11',
          label: '11'
        }, {
          value: '12',
          label: '12'
        }, {
          value: '13',
          label: '13'
        }, {
          value: '14',
          label: '14'
        }, {
          value: '15',
          label: '15'
        }, {
          value: '16',
          label: '16'
        }, {
          value: '17',
          label: '17'
        }, {
          value: '18',
          label: '18'
        }, {
          value: '19',
          label: '19'
        }, {
          value: '20',
          label: '20'
        }],
        priceLeft: '', // 模块推荐商品价格左输入框
        proceRight: '', // 模块推荐商品价格右输入框
        keyWords: '', // 模块推荐关键词输入框
        commodityScope: '0', // 商品范围选中值
        commodityScopeOptions: [{ // 商品范围下拉框数据
          value: '0',
          label: ''
        }, {
          value: '1',
          label: ''
        }, {
          value: '2',
          label: ''
        }, {
          value: '3',
          label: ''
        }, {
          value: '4',
          label: ''
        }],
        activeCommodities: '0', // 活动商品选中值
        activeCommoditiesOptions: [{ // 活动商品下拉框数据
          value: '0',
          label: ''
        }, {
          value: '1',
          label: ''
        }, {
          value: '2',
          label: ''
        }, {
          value: '3',
          label: ''
        }, {
          value: '4',
          label: ''
        }, {
          value: '5',
          label: ''
        }],
        sortRule: '0', // 排序规则选中值
        sortRuleOptions: [{ // 排序规则下拉框数据
          value: '0',
          label: ''
        }, {
          value: '1',
          label: ''
        }, {
          value: '2',
          label: ''
        }]
      },
      defaultColorright: '#f5f5f5', // 背景颜色自定义默认颜色
      listTypeData: [ // 列表样式数据
        {
          typeName: '',
          isChecked: false
        },
        {
          typeName: '',
          isChecked: false
        },
        {
          typeName: '',
          isChecked: false
        },
        {
          typeName: '',
          isChecked: false
        },
        {
          typeName: '',
          isChecked: false
        }
      ],
      goodsList: [ // 手动推荐显示模块商品列表数据
      ],
      rangeList: [null, '+添加商家分类', '+添加平台分类', '+添加商品品牌', '+添加商品标签'], // 商品范围选中后按钮文本列表
      rangeData: [null, { data: [] }, { data: [] }, { data: [] }, { data: [] }], // 商品范围四类弹框选中数据池
      needToSwitchData: ['hide_name', 'hide_price', 'hide_label', 'cart_btn', 'other_message'], // 需要转换的checkbox数据
      goodsListData: [],
      isClickGoodsUpOrDownIcon: false, // 是否点击了模块推荐里商品列表的上下icon按钮
      // 模块保存数据
      data: {
        module_name: 'm_goods', // 模块名称
        title: '', // 标题  y
        title_link: '', // 标题链接 y
        tit_center: false, // 标题位置 false不选中标题居中  true选中标题居中 y
        recommend_type: '0', // 模块推荐 0自动推荐  1手动推荐 y
        goods_items: [
          // 商品列表数据 y
        ],
        col_type: '4', // 4  1  2  3  0  y
        goods_display: '0', // 大图展示显示模块radio值
        goods_num: '4', // 模块推荐-商品数量  y
        min_price: '', // 模块推荐-商品价格最低价格  y
        max_price: '', // 模块推荐-商品价格最高价格 y
        keywords: '', // 模块推荐-关键词 y
        sort_type: '1', // 模块推荐-排序规则 y
        img_url: '', // 图标 y
        goods_module_title: '0', // 模块标题类型  0不设置  1文字标题 2图片标题  y
        img_title_url: '', // 标题图片 y
        hide_name: '1', // 商品名称 y
        hide_price: '0', // 商品价格 y
        hide_label: '0', // 商品标签 y
        cart_btn: '1', // 购买按钮 y
        cart_btn_choose: '0', // 购买按钮radio y
        other_message: '0', // 其它信息 y
        if_radius: '0', // 模块角度 0直角  1圆角 y
        goods_module_style: '0', // 0白底无边框  1边框投影 2白底有边框 y
        goods_area: 'sort', // 模块推荐-商品范围  //all 没选  sort：商家分类 cat:平台分类  brand:品牌分类  label:标签分类 y
        goods_area_data: [], // 商品范围选中后弹窗选中的数据  //多个数据也是字符串，非数组  y
        goods_type: '0', // 活动商品 0请选择 .. y
        show_market: '1', // 其他信息选中 隐藏radio字段  1市场价 2销量 3评价数 y
        goods_module_bg: '0', // 背景颜色 0与页面一致  1自定义 y
        goods_bg_color: '#f5f5f5', // 背景自定义颜色 y
        goodsListData: [] // 传递商品列表数据
      },
      initRequestFlag: false, // 初始化接收的数据是否已存在商品数据
      temporaryStorageGoods: [], // 手动推荐暂存商品信息
      temporaryRightGoods: []
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        let flag = false
        if (this.modulesData) {
          Object.keys(this.modulesData).forEach((item, index) => {
            console.log(11)
            flag = true
          })
          if (flag) {
            let turnToString = this.handleToTurnNumToStr(this.modulesData)
            console.log(turnToString)
            // this.modulesData = turnToString
            if (turnToString.recommend_type === '1') {
              this.temporaryStorageGoods = turnToString.goods_items
            }
            console.log(this.modulesData)
            // 需要转换的checkbox字段数组集合
            let getModulesData = JSON.parse(JSON.stringify(turnToString))
            this.needToSwitchData.forEach(itemC => {
              let m = this.handleToTurnModulesData(turnToString[itemC]) // 将数据种checkbox的值由stying数字转为Boolean
              getModulesData[itemC] = m
            })
            console.log(getModulesData)
            // 转换列表样式
            this.handleToChangeStyle(0, getModulesData)
            // 转换商品范围字段数据
            let d = this.handleToTransformationRangeData(turnToString.goods_area)
            getModulesData.goods_area = d
            // 赋值
            Object.keys(turnToString).forEach((item, index) => { // 将数据赋值给当前页面数据池
              this.$set(this.data, item, getModulesData[item])
            })

            // 初始化调取模块推荐接口
            if (!turnToString.goodsListData.length) {
              this.initRequestFlag = true
              this.handleToGetModulesGoods(turnToString)
            } else {
              this.initRequestFlag = false
            }
          }
          console.log(this.data)
        }
        console.log(newData, this.data)
      },
      immediate: true
    },
    'data.cart_btn' (newData) {
      if (newData) {
        this.data.other_message = false
      }
    },
    'data.other_message' (newData) {
      console.log(newData)
      if (newData) {
        this.data.cart_btn = false
      }
    },
    'data.recommend_type' (newData) {
      if (newData === '1') {
        console.log(this.data)
        // this.data.goodsListData = []
        this.handleToGetModulesGoods(this.data, true)
      }
    },
    // 监控该模块右边数据操作
    copyData: {
      handler (newData, oldData) {
        console.log(newData, oldData)
        console.log('触发')
        // 判断是否是模块推荐中的数据改变
        let judgeChangeFlag = this.handleToJudgeDataChange(newData, oldData)
        // 转换选择商品范围字段数据
        let d = this.handleToTransformationRangeData(newData.goods_area)
        console.log(d)
        let callBackData = JSON.parse(JSON.stringify(newData))
        callBackData.goods_area = d
        // 将数据种checkbox的值由stying数字转为Boolean
        console.log(callBackData, this.modulesData)
        this.needToSwitchData.forEach(itemC => {
          let m = this.handleToTurnModulesData(callBackData[itemC])
          callBackData[itemC] = m
        })
        // 转换样式列表字段
        let styleParams = this.handleToChangeStyle(1)
        callBackData.col_type = styleParams

        console.log(this.goodsListData, this.data.goods_items)
        console.log(this.initRequestFlag, judgeChangeFlag, callBackData)
        // 若模块推荐中数据改变处理函数
        if (this.initRequestFlag) {
          console.log(judgeChangeFlag, callBackData)
          if (judgeChangeFlag && callBackData.recommend_type === '0') {
            this.handleToGetModulesGoods(callBackData, false)
          } else {
            this.$emit('handleToBackData', callBackData)
          }
          // } else if (callBackData.recommend_type === '1') {
          //   // callBackData.goodsListData = this.data.goods_items
          //   console.log(this.temporaryStorageGoods)
          //   callBackData.goodsListData = this.temporaryStorageGoods
          //   this.$emit('handleToBackData', callBackData)
          // } else {
          //   // callBackData.goodsListData = this.temporaryStorageGoods
          //   // this.$emit('handleToBackData', callBackData)
          // }
        } else {
          this.$emit('handleToBackData', callBackData)
        }
        this.initRequestFlag = true
        console.log(styleParams)
      },
      deep: true
    },
    lang () {
      this.listTypeData.forEach((item, index) => {
        item.typeName = this.$t('commodity.listTypeData')[index]
      })
      console.log(this.commodityModule.commodityScopeOptions)
      this.commodityModule.commodityScopeOptions.forEach((item, index) => {
        item.label = this.$t('commodity.commodityScopeOptions')[index]
      })

      this.commodityModule.activeCommoditiesOptions.forEach((item, index) => {
        item.label = this.$t('commodity.activeCommoditiesOptions')[index]
      })

      this.commodityModule.sortRuleOptions.forEach((item, index) => {
        item.label = this.$t('commodity.sortRuleOptions')[index]
      })
      this.rangeList = this.$t('commodity.rangeList')
      console.log(this.commodityModule.commodityScopeOptions)
    }
  },
  computed: {
    copyData () {
      return JSON.parse(JSON.stringify(this.data))
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    console.log(this.data.tit_center)
  },
  methods: {
    // 调取模块推荐中商品数据
    handleToGetModulesGoods (initData, flag, clickFlag) {
      console.log(initData, flag)
      let goodsId = []
      let num = null
      let obj = {}
      if (flag) {
        // if (!initData.goods_items.length) return
        // if (this.temporaryStorageGoods.length) {
        // this.temporaryStorageGoods.forEach(item => {
        // goodsId.push(item.goodsId)
        // })
        // } else {
        if (clickFlag) {
          initData.goods_items.forEach(item => {
            goodsId.push(item.goodsId)
          })
        } else {
          this.temporaryStorageGoods.forEach(item => {
            goodsId.push(item.goodsId)
          })
        }
        // if (initData.goods_items.length) {
        //   initData.goods_items.forEach(item => {
        //     goodsId.push(item.goodsId)
        //   })
        // }
        // }

        obj = {
          'goods_num': goodsId.length,
          'recommend_type': '1',
          'goods_items': goodsId
        }
      } else {
        // if (this.temporaryRightGoods.length) {
        // this.temporaryRightGoods.forEach(item => {
        // goodsId.push(item.goodsId)
        // })
        // } else {
        // initData.goods_items.forEach(item => {
        // goodsId.push(item.goodsId)
        // })
        // }
        // if (initData.goods_items.length) {
        this.temporaryRightGoods.forEach(item => {
          goodsId.push(item.goodsId)
        })
        // }
        num = Number(initData.goods_num)
        obj = {
          'recommend_type': initData.recommend_type, // 商品显示方式 0自动推荐 1手动推荐
          'goods_num': num, // 商品数量
          'min_price': initData.min_price, // 商品最低价格
          'max_price': initData.max_price, // 商品最高价格
          'keywords': initData.keywords, // 关键词
          'goods_area': initData.goods_area, // 商品范围
          'goods_area_data': initData.goods_area_data, // 商品范围选定后弹窗选定的数据
          'goods_type': Number(initData.goods_type), // 活动类型
          'sort_type': Number(initData.sort_type), // 排序规则
          'goods_items': goodsId // 商品列表数据
        }
      }

      console.log(goodsId)
      // 初始化接口传递参数
      queryDataList(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          console.log(res.content)
          // this.goodsListData = res.content
          this.data.goodsListData = res.content
          console.log(initData)
          if (flag) {
            this.temporaryStorageGoods = res.content
          } else {
            this.temporaryRightGoods = res.content
          }
          this.$emit('handleToBackData', this.data)
        }
        console.log(res)
      })
      console.log(obj)
      // 调取接口 获取商品数据赋值给回传对象中回传
    },
    // 判断是否是模块推荐中的数据改变
    handleToJudgeDataChange (newData, oldData) {
      console.log(newData, oldData)
      let arr = ['recommend_type', 'goods_num', 'min_price', 'max_price', 'keywords', 'goods_area', 'sort_type'] // 需要判断的模块推荐中数据字段数据池
      let flag = arr.filter((item, index) => {
        return newData[item] !== oldData[item]
      })
      let goodsItemFlag = false // 商品列表字段单独判断是否更改
      if (newData.goods_items.length !== oldData.goods_items.length) {
        goodsItemFlag = true
      } else if (this.isClickGoodsUpOrDownIcon) {
        goodsItemFlag = true
      }
      let goodsAreaData = false // 商品范围弹窗选中判断是否更改
      if (newData.goods_area_data.length !== oldData.goods_area_data.length) {
        goodsAreaData = true
      }
      console.log(flag, goodsItemFlag)
      // 两种情况下综合判断
      let returnFlag = !!((flag.length || goodsItemFlag || goodsAreaData))
      console.log(returnFlag)
      this.isClickGoodsUpOrDownIcon = false
      return returnFlag
    },
    // 转换列表样式字段数据
    handleToChangeStyle (flag, data) {
      if (flag === 0) {
        switch (data.col_type) {
          case '4':
            this.listTypeData[0].isChecked = true
            break
          case '1':
            this.listTypeData[1].isChecked = true
            break
          case '2':
            this.listTypeData[2].isChecked = true
            break
          case '3':
            this.listTypeData[3].isChecked = true
            break
          case '0':
            this.listTypeData[4].isChecked = true
        }
        console.log(this.listTypeData)
      } else {
        let indexFlag = ''
        let save = ''
        this.listTypeData.forEach((item, index) => {
          if (item.isChecked) {
            indexFlag = index
          }
        })
        console.log(indexFlag)
        switch (indexFlag.toString()) {
          case '0':
            save = '4'
            break
          case '1':
            save = '1'
            break
          case '2':
            save = '2'
            break
          case '3':
            save = '3'
            break
          case '4':
            save = '0'
            break
        }
        return save
      }
    },
    // 中间模块数据传来checkbox数据转化函数
    handleToTurnModulesData (params) { // flag 数据类型  数据key 数据值
      console.log(params)
      let d = ''
      switch (params) {
        case '0':
          d = false
          break
        case '1':
          d = true
          break
        case true:
          d = '1'
          break
        case false:
          d = '0'
      }
      console.log(d)
      return d
    },
    // 转换商品范围字段
    handleToTransformationRangeData (params) {
      console.log(params)
      let d = ''
      switch (params) {
        case 'all':
          d = '0'
          break
        case 'sort':
          d = '1'
          break
        case 'cat':
          d = '2'
          break
        case 'brand':
          d = '3'
          break
        case 'label':
          d = '4'
          break
        case 0:
          d = 'all'
          break
        case '1':
          d = 'sort'
          break
        case '2':
          d = 'cat'
          break
        case '3':
          d = 'brand'
          break
        case '4':
          d = 'label'
      }
      console.log(d)
      return d
    },
    // 模块标题 图标和标题图片点击删除icon事件
    handleToDelIcon () {
      if (this.data.goods_module_title === '1') {
        this.data.img_url = ''
      } else if (this.data.goods_module_title === '2') {
        this.data.img_title_url = ''
      }
    },
    // 点击列表样式
    handleToClickType (index) {
      this.listTypeData.forEach(item => {
        item.isChecked = false
      })
      this.listTypeData[index].isChecked = true
      if (index === 0) {
        this.data.col_type = '4'
      } else if (index === 4) {
        this.data.col_type = '0'
      } else {
        this.data.col_type = index.toString()
      }
    },
    // 模块标题图标点击
    handleToAddModulesImg () {
      console.log(this.data.goods_module_title)
      if (this.data.goods_module_title === '1') {
        this.imageSize = [25, 25]
      } else {
        this.imageSize = []
      }
      console.log(this.imageSize, 111111)
      this.tuneUp = !this.tuneUp
    },
    // 选择链接选中回传
    handleToSelectLinkPath (path) {
      this.data.title_link = path
      console.log(path)
    },
    // 选择图片选中回传
    handleSelectImg (res) {
      console.log(res)
      if (this.data.goods_module_title === '1') {
        this.data.img_url = res.imgUrl
      } else {
        this.data.img_title_url = res.imgUrl
      }
    },
    // 商品模块颜色自定义重置点击
    handleToReset () {
      this.data.goods_bg_color = null
    },
    // 模块推荐商品列表icon点击统一处理
    handleToClickOpera (index, flag) {
      let arr = JSON.parse(JSON.stringify(this.data.goods_items))
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
      this.data.goods_items = arr
      this.handleToGetModulesGoods(this.data, true, true)
    },
    //  添加商品点击
    handleToAddGoods () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    // 选中商品信息回传
    handleToGetGoods (res) {
      console.log(res)
      let resCopy = JSON.parse(JSON.stringify(res))
      // 过滤
      res.forEach((item, index) => {
        this.data.goods_items.forEach((itemC, indexC) => {
          if (item.goodsId === itemC.goodsId) {
            resCopy.splice(index, 1, -1)
          }
        })
      })

      console.log(resCopy, this.data.goods_items)
      resCopy.forEach((item, index) => {
        this.data.goods_items.push(item)
      })
      // 添加
      this.handleToGetModulesGoods(this.data, true, true)
      console.log(this.data)
    },
    // 商品范围选中后显示添加按钮点击统一处理
    handleToClickRangeBtn (index) {
      console.log(index)
      switch (index) {
        case 1:
          this.classFlag = 1
          this.dialogVisible = true
          break
        case 2:
          this.classFlag = 2
          this.dialogVisible = true
          break
        case 3:
          this.callAddBrand = true
          break
        case 4:
          this.callAddProductLabel = true
      }
    },
    // 商品范围下拉框选中值变化事件
    handleToSelectRange () {
      console.log(this.data.goods_area)
      this.rangeCheckData = this.rangeData[Number(this.data.goods_area)]
      console.log(this.rangeCheckData)
      switch (Number(this.data.goods_area)) {
        case 1:
          this.rangeHiddenRightText = '分类'
          break
        case 2:
          this.rangeHiddenRightText = '分类'
          break
        case 3:
          this.rangeHiddenRightText = '品牌'
          break
        case 4:
          this.rangeHiddenRightText = '标签'
      }
      console.log(111)
    },
    // 商品范围弹窗选中回传事件
    handleToGetBackData (data) {
      console.log(data)
      this.handleToSelectRange()
      this.data.goods_area_data = data
      console.log(this.data.goods_area_data)
      let arr = this.rangeData[Number(this.data.goods_area)] = data
      this.rangeCheckData = arr
    }
    // this.$emit('handleToBackData', obj) 数据回传调用
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
    .main {
      margin-top: 20px;
      .title {
        span {
          display: inline-block;
          margin-right: 10px;
        }
        display: flex;
        /deep/ .el-radio {
          margin-right: 10px;
        }
      }
      .titleHidden {
        background: #fff;
        border: 1px solid #ddd;
        height: 253px;
        margin-top: 10px;
        .titleHiddenMain {
          height: 46px;
          padding-top: 20px;
          .topTitle,
          .topLink,
          .topPosition,
          .bgImg {
            margin-bottom: 10px;
            display: flex;
            justify-content: flex-start;
            span {
              display: inline-block;
              width: 100px;
              height: 32px;
              line-height: 32px;
            }
            span {
              white-space: nowrap;
              &:nth-of-type(1) {
                text-align: right;
              }
              &:nth-of-type(2) {
                text-align: left;
                color: #a7a7a7;
                margin-left: 3px;
              }
            }
            /deep/ .el-input {
              width: 252px;
            }
            /deep/ .el-button {
              margin-left: 3px;
            }
            /deep/ .el-checkbox {
              display: flex;
              justify-content: center;
              align-items: center;
              .el-checkbox__input {
                margin-top: 2px;
              }
            }
            .bgIcon {
              position: relative;
              width: 70px;
              height: 70px;
              display: flex !important;
              justify-content: center;
              align-items: center;
              border: 1px solid #ccc;
              //   background-position: center;
              .bgImgDiv {
                width: 47px;
                height: 44px;

                cursor: pointer;
              }
              i {
                display: inline-block;
                width: 15px;
                height: 15px;
                position: absolute;
                right: -7px;
                top: -8px;
                background: url("../../../../../../../../assets/adminImg/icon_delete.png")
                  no-repeat;
                cursor: pointer;
              }
            }
          }
        }
      }
      .listStyle {
        margin-top: 10px;
        .title {
          margin-bottom: 10px;
        }
        .content {
          display: flex;
          flex-wrap: wrap;
          background-color: #fff;
          padding: 8px 10px 0;
          border: 1px solid #e5e5e5;
          cursor: pointer;
          .typeContainer {
            padding: 8px 5px;
            width: 30%;
            margin-right: 3%;
            height: 96px;
            border: 1px solid #e5e5e5;
            margin-bottom: 10px;
            min-width: 124px;
            .type {
              p {
                margin-top: 10px;
                text-align: center;
              }
              .typeTop {
                padding: 5px 5px 0 5px;
                display: flex;
                justify-content: space-between;
                span {
                  background: #e9f8fd;
                }
                .odd_left {
                  width: 50%;
                  height: 50px;
                }
                .odd_right {
                  height: 50px;
                  width: 44%;
                  flex-wrap: wrap;
                  justify-content: center;
                  span {
                    display: block;
                    &:nth-of-type(1) {
                      height: 12px;
                    }
                    &:nth-of-type(2) {
                      width: 60%;
                      height: 12px;
                      margin-top: 8px;
                    }
                    &:nth-of-type(3) {
                      height: 12px;
                      width: 60%;
                      margin-top: 6px;
                      display: inline-block;
                    }
                    &:nth-of-type(4) {
                      height: 12px;
                      width: 20%;
                      height: 12px;
                      display: inline-block;
                    }
                  }
                }
              }
            }
          }
        }
      }
      .commodityModule {
        margin-top: 10px;
        .commodityMain {
          background-color: #fff;
          padding: 8px 10px 0;
          border: 1px solid #e5e5e5;
          margin: 10px 0;
          .commodityContent {
            display: flex;
            padding-top: 20px;
            .compatibleContent {
              width: 97px;
              display: flex;
              justify-content: flex-end;
            }
            span {
              display: inline-block;
              width: 97px;
              height: 18px;
              line-height: 18px;
              text-align: right;
            }
            .contentRight {
              .contentRightTop {
                /depp/ .el-checkbox {
                  margin-right: 5px;
                }
              }
              .contentDiv {
                margin: 5px 0;
                /deep/ .el-radio {
                  margin-right: 5px;
                }
                /deep/ .el-checkbox {
                  margin-right: 22px;
                }
                .specialTips {
                  display: inline-block;
                  width: 240px;
                  white-space: pre-wrap;
                  text-align: justify;
                }
              }
              .buyBtnHidden {
                padding-left: 45px;
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
                  // width: 70px;
                  padding: 0 5px;
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
                  // width: 55px;
                  padding: 0 5px;
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
          .commodityAngle {
            display: flex;
            margin: 10px 0;
            span {
              display: inline-block;
              width: 97px;
              height: 18px;
              line-height: 18px;
              text-align: right;
            }
            .angleDiv {
              /deep/ .el-radio {
                margin-right: 5px;
              }
            }
            .bgColorDiv {
              display: flex;
              flex-direction: column;
              /deep/ .el-radio {
                margin-bottom: 10px;
              }
              .customBgColor {
                display: flex;
                /deep/ .el-radio {
                  margin-right: 5px;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  padding-top: 5px;
                }
                .colorSelect {
                  display: inline-block;
                  height: 32px;
                  width: 34px;
                  margin-left: 5px;
                  background-color: #fff;
                  border: 1px solid #ccc;
                  /deep/ .m-colorPicker {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    /deep/ .colorBtn {
                      width: 50px;
                      height: 20px;
                      border: 1px solid #000;
                    }
                    .open {
                      margin-top: 60px;
                      z-index: 10000;
                    }
                  }
                }
              }
            }
          }
        }
      }
      .moduleRecommendation {
        .moduleTitleDiv {
          display: flex;
        }
        .hiddenRangeCheck {
          display: flex;
        }
        margin-top: 10px;
        /deep/ .el-radio {
          margin-right: 5px;
        }
        .moduleRecMain {
          background-color: #fff;
          padding: 8px 10px 0;
          border: 1px solid #e5e5e5;
          margin: 10px 0;
          .manual {
            padding-top: 20px;
            .goodsNum,
            .goodsPrice {
              margin-bottom: 10px;
              span {
                display: inline-block;
                width: 97px;
                text-align: right;
              }
            }
            .price {
              display: flex;
              /deep/ .el-input {
                width: 88px;
              }
              span {
                height: 32px;
                line-height: 32px;
                display: inline-block;
                margin-right: 3px;
              }
              i {
                display: inline-block;
                height: 32px;
                line-height: 32px;
              }
            }
            .keyWors {
              /deep/ .el-input {
                width: 200px;
              }
            }
            .commodityScope {
              /deep/ .el-input {
                width: 132px;
              }
            }
            .choiseGoodeDiv {
              display: flex;
              span {
                display: flex;
                justify-content: center;
                align-items: center;
              }
              .goodsList {
                padding-top: 10px;
                display: flex;
                span {
                  display: inline-block;
                  overflow-wrap: break-word;
                  &:nth-of-type(1) {
                    width: 50px;
                    margin-right: 5px;
                  }
                  &:nth-of-type(2) {
                    width: 220px;
                    justify-content: space-between;
                    text-align: justify;
                  }
                }
                img {
                  display: inline-block;
                  width: 50px;
                  height: 50px;
                }
              }
              a {
                text-decoration: none;
              }
              .operation {
                margin-top: 15px;
                .up_arrow {
                  text-align: center;
                  cursor: pointer;
                  border: 1px solid transparent;
                  border-radius: 2px;
                  padding: 3px 12px;
                  border-color: #ccc;
                  color: #333;
                }
              }
            }
            .rangeHiddenBtn {
              width: 110px;
              height: 30px;
              line-height: 30px;
              text-align: center;
              color: #5a8bff;
              border: 1px solid #ccc;
              background: #fff;
              cursor: pointer;
              // margin-top: 17px;
              margin-bottom: 10px;
              &:hover {
                color: #333;
                text-decoration: underline;
              }
            }
          }
        }
      }
    }
  }
}
</style>
