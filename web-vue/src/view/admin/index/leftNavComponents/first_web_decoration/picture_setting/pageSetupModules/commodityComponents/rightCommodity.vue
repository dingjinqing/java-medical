<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>商品模块</h2>
      <div class="main">
        <div class="title">
          <span>模块标题:</span>
          <div>
            <el-radio
              v-model="data.goods_module_title"
              label="0"
            >不设置</el-radio>
            <el-radio
              v-model="data.goods_module_title"
              label="1"
            >文字标题</el-radio>
            <el-radio
              v-model="data.goods_module_title"
              label="2"
            >图片标题</el-radio>
          </div>
        </div>
        <!--模块标题隐藏模块-->
        <div
          class="titleHidden"
          v-if="data.goods_module_title==='1' || data.goods_module_title==='2'"
        >
          <div class="titleHiddenMain">
            <div class="topTitle">
              <span>标题：</span>
              <el-input
                v-model="data.title"
                size="small"
              ></el-input>
              <span>最多10个字</span>
            </div>
            <div class="topLink">
              <span>标题链接：</span>
              <el-input
                v-model="data.title_link"
                size="small"
              ></el-input>
              <el-button
                size="small"
                @click="tuneUpSelectLink = !tuneUpSelectLink"
              >选择链接</el-button>
            </div>
            <div class="topPosition">
              <span>标题位置：</span>
              <el-checkbox v-model="data.tit_center">标题居中</el-checkbox>
            </div>
            <div class="bgImg">
              <span>{{data.goods_module_title==='1'?'图标':data.goods_module_title==='2'?'标题图片':''}}：</span>
              <div class="bgIcon">

                <img
                  v-if="data.goods_module_title==='1'?!iconImgUrl:!titleImgUrl"
                  :src="$imageHost+'/image/admin/add_img_bg.png'"
                  class="bgImgDiv"
                  @click="handleToAddModulesImg()"
                />
                <img
                  v-else
                  style="width:100%;height:40px"
                  :src="data.goods_module_title==='1'?iconImgUrl:titleImgUrl"
                  @click="handleToAddModulesImg()"
                >
              </div>
            </div>
          </div>
        </div>
        <!--模块标题隐藏模块end-->
        <!--列表样式模块-->
        <div class="listStyle">
          <div class="title">列表样式</div>
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
                  <span class="odd_left"></span>
                  <div class="odd_right">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                  </div>
                </div>
                <p>{{item.typeName}}</p>
              </div>
            </div>
          </div>
        </div>
        <!--商品模块-->
        <div class="commodityModule">
          <span style="margin-bottom:10px">商品模块</span>
          <div class="commodityMain">
            <div class="commodityContent">
              <span>显示内容：</span>
              <div class="contentRight">
                <div class="contentRightTop">
                  <el-checkbox v-model="commodityModule.name">商品名称</el-checkbox>
                  <el-checkbox v-model="commodityModule.price">商品价格</el-checkbox>
                  <el-checkbox v-model="commodityModule.label">商品标签</el-checkbox>
                </div>
                <div class="contentDiv">
                  <el-checkbox v-model="commodityModule.buyBtn">购买按钮</el-checkbox>
                  <span style="color:#999;white-space:nowrap">显示购买按钮时将不显示其他信息</span>
                </div>
                <!--选中购买按钮隐藏模块-->
                <div
                  class="buyBtnHidden"
                  v-if="commodityModule.buyBtn"
                >
                  <el-radio
                    v-model="commodityModule.hiddenRadio"
                    label="1"
                  >
                    <i
                      class="iconfont icontianjia icon_font_size new_class"
                      style="color: rgb(177, 78, 105);"
                    ></i>
                  </el-radio>
                  <el-radio
                    v-model="commodityModule.hiddenRadio"
                    label="2"
                  ><i
                      class="iconfont icongouwuche1 icon_font_size new_class"
                      style="color: rgb(177, 78, 105);"
                    ></i></el-radio>
                  <el-radio
                    v-model="commodityModule.hiddenRadio"
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
                    v-model="commodityModule.hiddenRadio"
                    label="4"
                  >
                    <i
                      class="cart_buy"
                      style="color: rgb(177, 78, 105); border-color: rgb(177, 78, 105);"
                    >购买</i>
                  </el-radio>
                </div>
                <!--end-->
                <div class="contentDiv">
                  <el-checkbox v-model="commodityModule.otherInfoFlag">其他信息</el-checkbox>
                  <span style="color:#999;white-space:nowrap">后台数据仅为参考请以实际显示为准</span>
                </div>
                <div
                  class="contentDiv"
                  v-if="commodityModule.otherInfoFlag"
                >
                  <el-radio
                    v-model="commodityModule.contentRadio"
                    label="1"
                  >市场价</el-radio>
                  <el-radio
                    v-model="commodityModule.contentRadio"
                    label="2"
                  >销量</el-radio>
                  <el-radio
                    v-model="commodityModule.contentRadio"
                    label="3"
                  >评价数</el-radio>
                </div>
              </div>
            </div>
            <div class="commodityAngle">
              <span>模块角度：</span>
              <div class="angleDiv">
                <el-radio
                  v-model="commodityModule.angleRadio"
                  label="1"
                >直角</el-radio>
                <el-radio
                  v-model="commodityModule.angleRadio"
                  label="2"
                >圆角</el-radio>
              </div>
            </div>
            <div class="commodityAngle">
              <span>模块样式：</span>
              <div class="angleDiv">
                <el-radio
                  v-model="commodityModule.styleRadio"
                  label="1"
                >白底无边框</el-radio>
                <el-radio
                  v-model="commodityModule.styleRadio"
                  label="2"
                >边框投影</el-radio>
                <el-radio
                  v-model="commodityModule.styleRadio"
                  label="3"
                >白底有边框</el-radio>
              </div>
            </div>
            <div class="commodityAngle">
              <span>背景颜色：</span>
              <div class="bgColorDiv">
                <el-radio
                  v-model="commodityModule.bgColorRadio"
                  label="1"
                >与页面背景一致</el-radio>
                <div class="customBgColor">
                  <el-radio
                    v-model="commodityModule.bgColorRadio"
                    label="2"
                  >自定义</el-radio>
                  <span class="colorSelect">
                    <colorPicker
                      v-model="commodityModule.bgColor"
                      :defaultColor="defaultColorright"
                      style="width:60px;height:30px;"
                    />
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
          </div>
        </div>
        <!--模块推荐-->
        <div class="moduleRecommendation">
          <span style="margin-bottom:10px">模块推荐：</span>
          <el-radio
            v-model="commodityModule.RecommendationRadio"
            label="1"
          >自动推荐</el-radio>
          <el-radio
            v-model="commodityModule.RecommendationRadio"
            label="2"
          >手动推荐</el-radio>
          <!--自动推荐选中显示模块-->
          <div
            class="moduleRecMain"
            v-if="commodityModule.RecommendationRadio === '1'"
          >
            <div class="manual">
              <div class="goodsNum">
                <span>商品数量：</span>
                <el-select
                  v-model="commodityModule.goodsNum"
                  placeholder="请选择"
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
                <span>商品价格：</span>
                <el-input
                  v-model="commodityModule.priceLeft"
                  size="small"
                ></el-input>
                <i style="display:inline-block;margin:0 5px">到</i>
                <el-input
                  v-model="commodityModule.proceRight"
                  size="small"
                ></el-input>
              </div>
              <div class="goodsPrice price keyWors">
                <span>关键词：</span>
                <el-autocomplete
                  class="inline-input"
                  v-model="commodityModule.keyWords"
                  :fetch-suggestions="querySearch"
                  @select="handleSelect"
                  size='small'
                ></el-autocomplete>
              </div>
              <div class="goodsPrice price commodityScope">
                <span>商品范围：</span>
                <el-select
                  v-model="commodityModule.commodityScope"
                  placeholder="请选择"
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
                v-if="commodityModule.commodityScope!=='0'"
              >
                <!--商家分类-->
                <div
                  class="rangeHiddenBtn"
                  @click="handleToClickRangeBtn(Number(commodityModule.commodityScope))"
                >
                  {{rangeList[Number(commodityModule.commodityScope)]}}
                </div>
                <div
                  style="height:30px;line-height:30px;margin-left:5px"
                  v-if="rangeCheckData.length>0"
                >已选择{{rangeHiddenRightText}}：{{rangeCheckData.length}}个{{rangeHiddenRightText}}</div>
              </div>
              <!--end-->
              <div class="goodsPrice price commodityScope">
                <span>活动商品：</span>
                <el-select
                  v-model="commodityModule.activeCommodities"
                  placeholder="请选择"
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
                <span>排序规则：</span>
                <el-select
                  v-model="commodityModule.sortRule"
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
            v-if="commodityModule.RecommendationRadio === '2'"
          >
            <div class="manual">
              <div class="goodsNum choiseGoodeDiv">
                <span>商品列表：</span>
                <div>
                  <el-button
                    size="small"
                    @click="handleToAddGoods()"
                  >添加商品</el-button>
                  <div :style="goodsList.length>=3?'height:250px;width:112%;overflow-y: auto;margin-top:10px':''">
                    <div
                      v-for="(item,index) in goodsList"
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
                          title="向上"
                          class="up_arrow"
                          @click="handleToClickOpera(index,0)"
                        >↑</a>
                        <a
                          href="javascript:void(0)"
                          title="向上"
                          class="up_arrow"
                          @click="handleToClickOpera(index,1)"
                        >↓</a>
                        <a
                          href="javascript:void(0)"
                          title="向上"
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
    <!--添加商家分类弹窗-->
    <AddingBusClassDialog />
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
export default {
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
          label: '请选择'
        }, {
          value: '1',
          label: '商家分类'
        }, {
          value: '2',
          label: '平台分类'
        }, {
          value: '3',
          label: '商品品牌'
        }, {
          value: '4',
          label: '商品标签'
        }],
        activeCommodities: '0', // 活动商品选中值
        activeCommoditiesOptions: [{ // 活动商品下拉框数据
          value: '0',
          label: '请选择'
        }, {
          value: '1',
          label: '会员专享'
        }, {
          value: '2',
          label: '砍价'
        }],
        sortRule: '0', // 排序规则选中值
        sortRuleOptions: [{ // 排序规则下拉框数据
          value: '0',
          label: '按商品上架时间倒叙排序'
        }, {
          value: '1',
          label: '按商品销量倒序排列'
        }, {
          value: '2',
          label: '按商品价格正序排列(由低到高)'
        }]
      },
      defaultColorright: '#f5f5f5', // 背景颜色自定义默认颜色
      listTypeData: [ // 列表样式数据
        {
          typeName: '单列',
          isChecked: true
        },
        {
          typeName: '双列',
          isChecked: false
        },
        {
          typeName: '三列',
          isChecked: false
        },
        {
          typeName: '横向滑动',
          isChecked: false
        },
        {
          typeName: '大图',
          isChecked: false
        }
      ],
      goodsList: [ // 手动推荐显示模块商品列表数据
      ],
      rangeList: [null, '+添加商家分类', '+添加平台分类', '+添加商品品牌', '+添加商品标签'], // 商品范围选中后按钮文本列表
      rangeData: [null, { data: [] }, { data: [] }, { data: [] }, { data: [] }], // 商品范围四类弹框选中数据池
      // 模块保存数据
      data: {

      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        if (this.modulesData) {
          if (this.modulesData.tit_center === '0') {
            this.modulesData.tit_center = false
          } else {
            this.modulesData.tit_center = true
          }
          this.data = this.modulesData
          this.$forceUpdate()
        }
        console.log(newData, this.modulesData, this.data)
      },
      immediate: true
    },
    'commodityModule.buyBtn' (newData) {
      if (newData) {
        this.commodityModule.otherInfoFlag = false
      }
    },
    'commodityModule.otherInfoFlag' (newData) {
      if (newData) {
        this.commodityModule.buyBtn = false
      }
    },
    // 监控该模块右边数据操作
    data: {
      handler (newData) {
        if (this.modulesData.tit_center === false) {
          this.modulesData.tit_center = '0'
        } else {
          this.modulesData.tit_center = '1'
        }
      },
      deep: true
    }
  },
  mounted () {
    this.restaurants = this.loadAll()
  },
  methods: {
    // 点击列表样式
    handleToClickType (index) {
      this.listTypeData.forEach(item => {
        item.isChecked = false
      })
      this.listTypeData[index].isChecked = true
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
        this.iconImgUrl = res.imgUrl
      } else {
        this.titleImgUrl = res.imgUrl
      }
    },
    // 商品模块颜色自定义重置点击
    handleToReset () {
      this.commodityModule.bgColor = this.defaultColorright
    },
    // 关键词带建议输入框相关
    querySearch (queryString, cb) {
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    // 关键词带建议输入框相关
    createFilter (queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    // 关键词带建议输入框相关
    loadAll () {
      return [
        { 'value': 'test1' },
        { 'value': 'test2' },
        { 'value': 'test3' },
        { 'value': 'test4' },
        { 'value': 'test5' }
      ]
    },
    // 关键词下方列表选中
    handleSelect (item) {
      console.log(item)
    },
    // 模块推荐商品列表icon点击统一处理
    handleToClickOpera (index, flag) {
      let arr = JSON.parse(JSON.stringify(this.goodsList))
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
          break
        case 1:
          if (next === -1) return
          arr[index] = next
          arr[(index + 1)] = temp
          break
        case 2:
          arr.splice(index, 1)
          break
      }
      this.goodsList = arr
    },
    //  添加商品点击
    handleToAddGoods () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    // 选中商品信息回传
    handleToGetGoods (res) {
      console.log(res)
      res.forEach((item, index) => {
        let obj = {
          goodsName: item.goodsName,
          goodsImg: item.goodsImg,
          goodsId: item.goodsId
        }
        this.goodsList.push(obj)
      })
    },
    // 商品范围选中后显示添加按钮点击统一处理
    handleToClickRangeBtn (index) {
      console.log(index)
      switch (index) {
        case 1:
          break
        case 2:
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
      this.rangeCheckData = this.rangeData[Number(this.commodityModule.commodityScope)]
      switch (Number(this.commodityModule.commodityScope)) {
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
    },
    // 商品范围弹窗选中回传事件
    handleToGetBackData (data) {
      console.log(data)
      let arr = this.rangeData[Number(this.commodityModule.commodityScope)] = data
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
            padding: 8px 10px;
            width: 30%;
            margin-right: 3%;
            height: 96px;
            border: 1px solid #e5e5e5;
            margin-bottom: 10px;
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
                  width: 62px;
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
