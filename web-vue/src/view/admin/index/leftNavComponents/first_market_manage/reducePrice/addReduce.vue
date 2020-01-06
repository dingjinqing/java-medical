<template>
  <div class="container">
    <el-form
      ref="reduceData"
      :model="reduceData"
      :rules="reduceRules"
      label-width="130px"
      :label-position="'right'"
    >
      <el-form-item
        :label="$t('marketCommon.actName') + '：'"
        prop="name"
      >
        <el-input
          :disabled="isEditFlag"
          size="small"
          :placeholder="$t('marketCommon.actNamePlaceholder')"
          v-model="reduceData.name"
          class="inputWidth"
        ></el-input>
        <p style="color: #999;">{{$t('reducePriceList.actNameTip')}}</p>
      </el-form-item>
      <el-form-item
        :label="$t('marketCommon.validDate') + '：'"
        prop="effectiveDate"
      >
        <el-date-picker
          :disabled="isEditFlag"
          v-model="reduceData.effectiveDate"
          type="datetimerange"
          :range-separator="$t('marketCommon.to')"
          :start-placeholder="$t('marketCommon.startTime')"
          :end-placeholder="$t('marketCommon.endTime')"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          size="small"
        ></el-date-picker>
        <el-checkbox
          :disabled="isEditFlag"
          v-model="reduceData.isCycle"
          @change="showCycleDialog"
        >{{$t('reducePriceList.repeatByCycle')}}</el-checkbox>
      </el-form-item>
      <el-form-item
        :label="$t('marketCommon.first') + '：'"
        prop="first"
      >
        <el-input-number
          :disabled="isEditFlag"
          size="small"
          :min="0"
          :max="127"
          v-model="reduceData.first"
          class="inputWidth"
          controls-position="right"
        ></el-input-number>
        <p style="color: #999;">{{$t('reducePriceList.actNameTip')}}</p>
      </el-form-item>
      <el-form-item
        :label="$t('reducePriceList.purchaseQuantity') + '：'"
        prop="isLimit"
      >
        <el-radio
          :disabled="isEditFlag"
          v-model="reduceData.isLimit"
          label="0"
        >{{$t('reducePriceList.noLimit')}}</el-radio>
        <br />
        <el-radio
          :disabled="isEditFlag"
          v-model="reduceData.isLimit"
          label="1"
        >{{$t('reducePriceList.limitQuantity')}}
          <el-input-number
            :disabled="isEditFlag"
            v-model="reduceData.limitAmount"
            controls-position="right"
            :min="1"
            size="small"
            class="small_input"
          ></el-input-number>
          {{$t('reducePriceList.piece')}}
        </el-radio>
        <div v-if="reduceData.isLimit === '1'">
          <el-checkbox
            :disabled="isEditFlag"
            v-model="reduceData.limitFlag"
            :true-label="1"
            :false-label="0"
          >{{$t('reducePriceList.limitQuantityTip')}}</el-checkbox>
        </div>
      </el-form-item>
      <el-form-item
        :label="$t('marketCommon.activityGoods') + '：'"
        prop=""
      >
        <el-button
          :disabled="isEditFlag"
          size="small"
          plain
          @click="showChoosingGoods"
        >
          <i class="el-icon-plus"></i> {{$t('marketCommon.selectGoods')}}
        </el-button>
      </el-form-item>
      <div
        v-if="pageShowGoodsList.length"
        style="padding: 0 20px;margin-bottom: 20px;"
      >
        <div class="set_item batch_item">
          <div class="item_title">
            <em>*</em> {{$t('reducePriceList.setDiscount')}}：
          </div>
          <div class="item_right">
            <el-radio-group v-model="batchFlag">
              <el-radio
                :label="1"
                :disabled="isEditFlag"
              >{{$t('reducePriceList.batch')}}
                <el-input-number
                  :disabled="batchFlag != 1 ? true : false"
                  v-model="reduceData.batchDiscount"
                  :controls="false"
                  size="small"
                  controls-position="right"
                  class="small_input"
                  :min="0"
                  :max="10"
                ></el-input-number>{{$t('reducePriceList.discount')}}</el-radio>
              <el-radio
                :label="2"
                :disabled="isEditFlag"
              >{{$t('reducePriceList.batch')}}{{$t('reducePriceList.priceReduction')}}
                <el-input-number
                  :disabled="batchFlag != 2 ? true : false"
                  v-model="reduceData.batchReduce"
                  :controls="false"
                  size="small"
                  controls-position="right"
                  class="small_input"
                  :min="0"
                ></el-input-number>{{$t('marketCommon.yuan')}}</el-radio>
              <el-radio
                :label="3"
                :disabled="isEditFlag"
              >{{$t('reducePriceList.batch')}}{{$t('reducePriceList.priceAfterDiscount')}}
                <el-input-number
                  :disabled="batchFlag != 3 ? true : false"
                  v-model="reduceData.batchFinalPrice"
                  :controls="false"
                  size="small"
                  controls-position="right"
                  class="small_input"
                  :min="0"
                ></el-input-number>{{$t('marketCommon.yuan')}}</el-radio>
            </el-radio-group>
            <el-button
              :disabled="isEditFlag"
              type="primary"
              size="small"
              @click="batchSet"
            >{{$t('marketCommon.ok')}}</el-button>
            <el-button
              :disabled="isEditFlag"
              type="default"
              size="small"
              @click="resetPrice"
            >{{$t('marketCommon.cancel')}}</el-button>
          </div>
        </div>
        <el-table
          :data="pageShowGoodsList"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
          ref="multipleTable"
        >
          <el-table-column
            type="selection"
            width="55"
          >
          </el-table-column>
          <template v-for="item in tableLabel">
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-if="item.index === 1"
            >
              <template slot-scope="scope">
                <div class="goods_info">
                  <img
                    :src="$imageHost+scope.row.goodsImg"
                    alt=""
                    class="goods_img"
                  >
                  <div class="goods_name">
                    {{scope.row.goodsName}}
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 4"
              width="150"
            >
              <template slot-scope="scope">
                <el-input-number
                  :disabled="isEditFlag"
                  v-model="scope.row.discount"
                  :controls="false"
                  size="small"
                  controls-position="right"
                  class="small_input"
                  @input="changeItemDiscount(scope.row)"
                ></el-input-number> {{$t('reducePriceList.discount')}}
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 5"
              width="150"
            >
              <template slot-scope="scope">
                <el-input-number
                  :disabled="isEditFlag"
                  v-model="scope.row.reducePrice"
                  :controls="false"
                  size="small"
                  controls-position="right"
                  class="small_input"
                  @input="changeItemReducePrice(scope.row)"
                ></el-input-number> {{$t('marketCommon.yuan')}}
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else-if="item.index === 6"
              width="150"
            >
              <template slot-scope="scope">
                <p class="price_red">{{reduceError(scope.row)}}</p>
                <el-input-number
                  :disabled="isEditFlag"
                  v-model="scope.row.goodsPrice"
                  :controls="false"
                  size="small"
                  controls-position="right"
                  class="small_input"
                  @input="changeItemGoodsPrice(scope.row)"
                ></el-input-number> {{$t('marketCommon.yuan')}}
                <p
                  class="price_blue"
                  @click="isEditFlag ? clickHandler() : getProductInfo(scope.row)"
                  v-if="scope.row.reducePriceProduct && scope.row.reducePriceProduct.length>0"
                >{{scope.row.reducePriceProduct.length}}{{$t('reducePriceList.productReducePriceTip')}}</p>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              width="80"
              v-else-if="item.index === 7"
            >
              <template slot-scope="scope">
                <span
                  class="del_item"
                  @click="isEditFlag ? clickHandler() : delReduceData(scope.row.goodsId)"
                >{{$t('marketCommon.delete')}}</span>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else
            ></el-table-column>
          </template>
        </el-table>
      </div>

      <!-- 收起、展开更多配置 -->
      <div
        @click="handleToChangeArror"
        style="padding: 0 0 30px 30px; width: 20%;"
      >
        <div
          v-if="arrorFlag"
          style="color:rgb(90, 139, 255);cursor:pointer"
        >
          {{ $t('seckill.openConfigure') }}&nbsp;<img :src="ArrowArr[0].img_1">
        </div>
        <div
          v-if="!arrorFlag"
          style="color:rgb(90, 139, 255);cursor:pointer"
        >
          {{ $t('seckill.closeConfigure') }}&nbsp;<img :src="ArrowArr[1].img_2">
        </div>
      </div>

      <div v-if="!arrorFlag">
        <!-- 活动分享 -->
        <el-form-item
          prop="shareConfig.shareAction"
          label="活动分享："
        >
          <div class="shareContent">
            <el-radio
              v-model="reduceData.shareConfig.shareAction"
              :label="1"
              :disabled="isEditFlag"
            >默认样式</el-radio>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src1"></el-image>
              <el-button
                slot="reference"
                type="text"
                style="margin: 0 20 0 0px"
              >查看示例</el-button>
            </el-popover>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src2"></el-image>
              <el-button
                slot="reference"
                type="text"
              >下载海报</el-button>
            </el-popover>
          </div>
          <div>
            <el-radio
              v-model="reduceData.shareConfig.shareAction"
              :label="2"
              :disabled="isEditFlag"
            >自定义样式</el-radio>
            <div v-if="reduceData.shareConfig.shareAction === 2">
              <span>文案：</span>
              <el-input
                v-model="reduceData.shareConfig.shareDoc"
                size="small "
                :disabled="isEditFlag"
                style="width: 170px;"
              ></el-input>
            </div>
            <div v-if="reduceData.shareConfig.shareAction === 2">
              <span>分享图：</span>
              <el-radio
                v-model="reduceData.shareConfig.shareImgAction"
                :label="1"
                :disabled="isEditFlag"
              >活动商品信息图</el-radio>
              <div style="margin-left: 60px;">
                <el-radio
                  v-model="reduceData.shareConfig.shareImgAction"
                  :label="2"
                  :disabled="isEditFlag"
                >自定义图片</el-radio>
              </div>

              <div
                style="display: flex"
                v-if="reduceData.shareConfig.shareImgAction === 2"
                :disabled="isEditFlag"
              >
                <div
                  class="imgContent"
                  @click="addGoodsImg"
                >
                  <div style="width: 100%; height: 100%;">
                    <img
                      v-if="reduceData.shareConfig.shareImg === ''"
                      src="http://jmpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png"
                      alt=""
                    >
                    <img
                      v-if="reduceData.shareConfig.shareImg !== ''"
                      :src="reduceData.shareConfig.shareImg"
                      alt=""
                      class="shareImg"
                    >
                  </div>
                </div>
                <span class="picSizeTips">建议尺寸：800*800像素</span>
              </div>
            </div>
          </div>
        </el-form-item>
      </div>

    </el-form>

    <div class="footer">
      <el-button
        @click="saveClickHandler"
        type="primary"
        size="small"
      >{{$t('marketCommon.save')}}</el-button>
    </div>

    <!--图片弹窗-->
    <ImageDalog
      pageIndex='userCardAdd'
      :tuneUp="tuneUp"
      @handleSelectImg='handleSelectImg'
    />

    <!-- 重复周期弹窗 -->
    <CycleDialog
      :cycleDialog="cycleDialogShow"
      :CycleType="periodAction"
      :CycleDate="extendTime"
      :CycleData="CycleData"
      @handelCycleData="getCycleData"
    />

    <!-- 选择商品 -->
    <choosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="goodsIdList"
      @resultGoodsIds="getGoodsIds"
    />

    <!-- 规格信息弹框 -->
    <productInfo
      :productDialog.sync="productDialogFlag"
      :product-info="productInfo"
      @confrim="getProductdata"
    />

  </div>
</template>

<script>
import { addReducePrice, getReducePriceById, updateReducePrice } from '@/api/admin/marketManage/reducePrice.js'
import { getGoodsInfosByGoodIds } from '@/api/admin/goodsManage/allGoods/allGoods'
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    CycleDialog: () => import('./repeatCycle'),
    choosingGoods: () => import('@/components/admin/choosingGoods'),
    productInfo: () => import('./productInfo'),
    actShare: () => import('@/components/admin/marketManage/marketActivityShareSetting')
  },
  props: ['isEdite', 'editId'],
  data () {
    return {
      // 展开设置箭头
      ArrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }],
      // 展开更多配置
      arrorFlag: true,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`
      },

      isEditFlag: false, // 编辑时部分信息不可修改
      batchFlag: null,
      // 周期弹窗
      cycleDialogShow: false,
      periodAction: null,
      extendTime: '',
      CycleData: '',

      reduceData: {
        name: '',
        effectiveDate: '',
        first: 1, // 优先级
        isCycle: false,
        isLimit: '0',
        limitFlag: 0,
        limitAmount: 1,
        batchDiscount: '', // 设置折扣
        batchReduce: '',
        batchFinalPrice: '',
        shareConfig: {
          shareAction: 1,
          shareDoc: '',
          shareImgAction: 1,
          shareImg: ''
        }
      },
      // 表单校验
      reduceRules: {
        name: [
          { required: true, message: '请填写活动名称', trigger: 'blur' }
        ],
        effectiveDate: [
          { required: true, message: '请填写有效期', trigger: 'change' }
        ],
        first: [
          { required: true, message: '请填写优先级', trigger: 'blur' }
        ],
        isLimit: [
          { required: true, message: '请填写限购数量', trigger: 'change' }
        ]
      },

      changeFlag: false,
      productDialogFlag: false,
      productInfo: {},
      pageShowGoodsList: [],
      tableLabel: [
        { index: 1, prop: 'goodsName', label: this.$t('marketCommon.goodsName') },
        { index: 2, prop: 'shopPrice', label: this.$t('reducePriceList.originalPrice') },
        { index: 3, prop: 'goodsNumber', label: this.$t('reducePriceList.stock') },
        { index: 4, prop: 'discount', label: this.$t('reducePriceList.discount') },
        { index: 5, prop: 'reducePrice', label: this.$t('reducePriceList.priceReduction') },
        { index: 6, prop: 'goodsPrice', label: this.$t('reducePriceList.priceAfterDiscount') },
        { index: 7, prop: '', label: this.$t('marketCommon.operate') }
      ],
      goodsIdList: [],
      tuneUp: false,
      tuneUpChooseGoods: false
    }
  },
  watch: {
    // 国际化
    lang () {
      this.tableLabel = [
        { index: 1, prop: 'goodsName', label: this.$t('marketCommon.goodsName') },
        { index: 2, prop: 'shopPrice', label: this.$t('reducePriceList.originalPrice') },
        { index: 3, prop: 'goodsNumber', label: this.$t('reducePriceList.stock') },
        { index: 4, prop: 'discount', label: this.$t('reducePriceList.discount') },
        { index: 5, prop: 'reducePrice', label: this.$t('reducePriceList.priceReduction') },
        { index: 6, prop: 'goodsPrice', label: this.$t('reducePriceList.priceAfterDiscount') },
        { index: 7, prop: '', label: this.$t('marketCommon.operate') }
      ]
    }
  },
  mounted () {
    this.langDefault()

    this.changeFlag = true

    // 编辑初始化
    if (this.isEdite === true) {
      this.editReduceInit()
    }
  },
  methods: {
    // 展开更多配置
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 图片弹窗
    addGoodsImg () {
      this.tuneUp = !this.tuneUp
    },

    // 图片选中
    handleSelectImg (res) {
      this.reduceData.shareConfig.shareImg = res.imgUrl
    },

    // 周期弹窗
    showCycleDialog (val) {
      if (this.changeFlag) {
        this.cycleDialogShow = val
      }
    },

    // 周期回显数据
    getCycleData (arr) {
      // 按周期重复
      Object.assign(this.reduceData, arr)
    },

    // 商品弹窗
    showChoosingGoods () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },

    // 选择商品
    getGoodsIds (data) {
      this.goodsIdList = data
      var param = {
        goodsIds: this.goodsIdList
      }
      getGoodsInfosByGoodIds(param).then(res => {
        if (res.error === 0) {
          res.content.forEach(item => {
            item.reducePriceProduct = item.goodsSpecProducts
            item.discount = 10
            item.reducePrice = 0
            item.goodsPrice = item.shopPrice
            if (item.reducePriceProduct != null && item.reducePriceProduct.length > 0) {
              item.reducePriceProduct.forEach(spec => {
                spec.originalPrice = spec.prdPrice
              })
            }
          })
          this.pageShowGoodsList = res.content
          console.log(this.pageShowGoodsList)
        }
      })
    },
    delReduceData (id) {
      let goodsTarget = this.pageShowGoodsList.findIndex(item => {
        return id === item.goodsId
      })
      this.pageShowGoodsList.splice(goodsTarget, 1)
    },
    getProductInfo (data) {
      this.productDialogFlag = true
      this.productInfo = data
      console.log(this.productInfo)
    },
    getProductdata (goodsId, ProductInfo) {
      let goodsTarget = this.pageShowGoodsList.findIndex(item => {
        return goodsId === item.goodsId
      })
      this.pageShowGoodsList[goodsTarget].reducePriceProduct = ProductInfo
    },
    batchSet () {
      console.log(this.$refs.multipleTable.selection)
      if (this.$refs.multipleTable.selection.length === 0) {
        this.$message.warning(this.$t('reducePriceList.batchValidSet'))
        return false
      } else {
        switch (this.batchFlag) {
          case 1:
            if (!this.reduceData.batchDiscount) {
              this.$message.warning(this.$t('reducePriceList.batchValidDiscount1'))
              return false
            } else if (parseFloat(this.reduceData.batchDiscount) <= 0 || parseFloat(this.reduceData.batchDiscount) >= 10) {
              this.$message.warning(this.$t('reducePriceList.batchValidDiscount2'))
              return false
            }
            this.$refs.multipleTable.selection.map(item => {
              let shopPrice = parseFloat(item.shopPrice)
              let goodsPriceFloat = (shopPrice * parseFloat(this.reduceData.batchDiscount / 10)).toFixed(3)
              let reducePriceFloat = parseFloat(shopPrice - goodsPriceFloat).toFixed(3)
              item.goodsPrice = parseFloat(goodsPriceFloat.substring(0, goodsPriceFloat.length - 1))
              item.reducePrice = parseFloat(reducePriceFloat.substring(0, reducePriceFloat.length - 1))
              item.discount = this.reduceData.batchDiscount
              if (item.reducePriceProduct && item.reducePriceProduct.length) {
                item.reducePriceProduct.map(item2 => {
                  let originalPrice = item2.originalPrice
                  let prdPriceFloat = (originalPrice * (parseFloat(this.reduceData.batchDiscount / 10))).toFixed(2)
                  item2.prdPrice = parseFloat(prdPriceFloat.substring(0, prdPriceFloat.length - 1))
                })
              }
            })
            break
          case 2:
            this.$refs.multipleTable.selection.map(item => {
              let shopPrice = parseFloat(item.shopPrice)
              let goodsPriceFloat = parseFloat(shopPrice - this.reduceData.batchReduce).toFixed(3)
              let discountFloat = (parseFloat(goodsPriceFloat / shopPrice) * 10).toFixed(3)
              item.goodsPrice = parseFloat(goodsPriceFloat.substring(0, goodsPriceFloat.length - 1))
              this.$nextTick(() => {
                item.reducePrice = this.reduceData.batchReduce
                item.discount = parseFloat(discountFloat.substring(0, discountFloat.length - 1))
                if (item.reducePriceProduct && item.reducePriceProduct.length) {
                  item.reducePriceProduct.map(item2 => {
                    let originalPrice = item2.originalPrice
                    let prdPriceFloat = parseFloat(originalPrice - this.reduceData.batchReduce).toFixed(3)
                    item2.prdPrice = parseFloat(prdPriceFloat.substring(0, prdPriceFloat.length - 1))
                  })
                }
              })
            })
            break
          case 3:
            this.$refs.multipleTable.selection.map(item => {
              let shopPrice = parseFloat(item.shopPrice)
              let reducePriceFloat = parseFloat(shopPrice - this.reduceData.batchFinalPrice).toFixed(3)
              let discountFloat = (parseFloat(this.reduceData.batchFinalPrice / shopPrice) * 10).toFixed(3)
              item.goodsPrice = this.reduceData.batchFinalPrice
              this.$nextTick(() => {
                item.reducePrice = parseFloat(reducePriceFloat.substring(0, reducePriceFloat.length - 1))
                item.discount = parseFloat(discountFloat.substring(0, discountFloat.length - 1))
                if (item.reducePriceProduct && item.reducePriceProduct.length) {
                  item.reducePriceProduct.map(item2 => {
                    item2.prdPrice = this.reduceData.batchFinalPrice
                  })
                }
              })
            })
            break
          default:
            this.$message.warning(this.$t('reducePriceList.batchValidSetType'))
            break
        }
      }
    },
    resetPrice () {
      this.pageShowGoodsList.map(item => {
        item.goodsPrice = ''
        item.reducePrice = ''
        item.discount = ''
        if (item.reducePriceProduct && item.reducePriceProduct.length) {
          item.reducePriceProduct.map(item2 => {
            item2.originalPrice = ''
          })
        }
      })
    },
    changeItemDiscount (rowData) {
      let goodsTarget = this.pageShowGoodsList.findIndex(item => {
        return rowData.goodsId === item.goodsId
      })
      let itemData = this.pageShowGoodsList[goodsTarget]
      let shopPrice = parseFloat(itemData.shopPrice)
      let goodsPriceFloat = (shopPrice * parseFloat(rowData.discount / 10)).toFixed(3)
      let reducePriceFloat = parseFloat(shopPrice - goodsPriceFloat).toFixed(3)
      itemData.goodsPrice = parseFloat(goodsPriceFloat.substring(0, goodsPriceFloat.length - 1))
      itemData.reducePrice = parseFloat(reducePriceFloat.substring(0, reducePriceFloat.length - 1))
      if (itemData.reducePriceProduct && itemData.reducePriceProduct.length) {
        itemData.reducePriceProduct.map(item => {
          let originalPrice = item.originalPrice
          let prdPriceFloat = (originalPrice * (parseFloat(rowData.discount / 10))).toFixed(3)
          item.prdPrice = parseFloat(prdPriceFloat.substring(0, prdPriceFloat.length - 1))
        })
      }
    },
    changeItemReducePrice (rowData) {
      let goodsTarget = this.pageShowGoodsList.findIndex(item => {
        return rowData.goodsId === item.goodsId
      })
      let itemData = this.pageShowGoodsList[goodsTarget]
      let shopPrice = parseFloat(itemData.shopPrice)
      let goodsPriceFloat = parseFloat(shopPrice - rowData.reducePrice).toFixed(3)
      let discountFloat = (parseFloat(goodsPriceFloat / shopPrice) * 10).toFixed(3)
      itemData.goodsPrice = parseFloat(goodsPriceFloat.substring(0, goodsPriceFloat.length - 1))
      itemData.discount = parseFloat(discountFloat.substring(0, discountFloat.length - 1))
      if (itemData.reducePriceProduct && itemData.reducePriceProduct.length) {
        itemData.reducePriceProduct.map(item => {
          let originalPrice = item.originalPrice
          let prdPriceFloat = parseFloat(originalPrice - rowData.reducePrice).toFixed(3)
          item.prdPrice = parseFloat(prdPriceFloat.substring(0, prdPriceFloat.length - 1))
        })
      }
    },
    changeItemGoodsPrice (rowData) {
      let goodsTarget = this.pageShowGoodsList.findIndex(item => {
        return rowData.goodsId === item.goodsId
      })
      let itemData = this.pageShowGoodsList[goodsTarget]
      let shopPrice = parseFloat(itemData.shopPrice)
      let reducePriceFloat = parseFloat(shopPrice - rowData.goodsPrice).toFixed(3)
      let discountFloat = (parseFloat(rowData.goodsPrice / shopPrice) * 10).toFixed(3)
      itemData.reducePrice = parseFloat(reducePriceFloat.substring(0, reducePriceFloat.length - 1))
      itemData.discount = parseFloat(discountFloat.substring(0, discountFloat.length - 1))
      if (itemData.reducePriceProduct && itemData.reducePriceProduct.length) {
        itemData.reducePriceProduct.map(item => {
          item.prdPrice = rowData.goodsPrice
        })
      }
    },
    reduceError (rowData) {
      if (rowData.discount || rowData.reducePrice || rowData.goodsPrice) {
        if (rowData.goodsPrice > rowData.shopPrice) {
          return this.$t('reducePriceList.batchValidPrice1')
        } else if (rowData.goodsPrice < 0) {
          return this.$t('reducePriceList.batchValidPrice2')
        } else if (rowData.shopPrice > 0 && rowData.shopPrice < 0.01) {
          return this.$t('reducePriceList.batchValidPrice2')
        } else {
          return ''
        }
      }
    },

    editReduceInit () {
      // 展开更多配置
      this.arrorFlag = false
      // 编辑时部分信息不可修改
      this.isEditFlag = true
      getReducePriceById({ id: this.editId }).then((res) => {
        if (res.error === 0) {
          this.reduceData = res.content
          // 有效期
          this.reduceData.effectiveDate = [res.content.startTime, res.content.endTime]
          // 周期
          if (this.reduceData.periodAction === 1 || this.reduceData.periodAction === 2 || this.reduceData.periodAction === 3) {
            this.reduceData.isCycle = true
          }
          // 周期弹窗数据回显
          this.periodAction = this.reduceData.periodAction
          this.extendTime = this.reduceData.extendTime
          this.CycleData = this.reduceData.pointTime
          // 限购数量
          if (this.reduceData.limitAmount > 0 || this.reduceData.limitAmount !== null) {
            this.reduceData.isLimit = '1'
          } else {
            this.reduceData.isLimit = '0'
          }
          // 活动分享
          this.reduceData.shareConfig = res.content.shopShareConfig

          // 活动商品
          res.content.reducePriceGoods.map(item => {
            item.goodsName = item.goodsView.goodsName
            item.goodsNumber = item.goodsView.goodsNumber
            item.goodsImg = item.goodsView.goodsImg
            item.shopPrice = item.goodsView.shopPrice
          })
          this.pageShowGoodsList = res.content.reducePriceGoods
        }
      })
    },

    // 保存限时降价
    saveClickHandler () {
      this.$refs['reduceData'].validate((valid) => {
        if (valid) {
          // 有效期
          this.reduceData.startTime = this.reduceData.effectiveDate[0]
          this.reduceData.endTime = this.reduceData.effectiveDate[1]
          // 限购数量
          if (this.reduceData.isLimit === '0') {
            this.reduceData.limitAmount = 0
          }
          // 改价的商品数组
          this.reduceData.reducePriceGoodsAddParams = this.pageShowGoodsList
          // 周期类型
          if (!this.reduceData.isCycle) {
            this.reduceData.periodAction = 0
          }

          console.log(this.reduceData)
          if (this.isEdite === false) {
            // 添加限时降价
            addReducePrice(this.reduceData).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: this.$t('marketCommon.successfulOperation') })
                this.$emit('addReduceSubmit')
              } else {
                this.$message.warning({ message: this.$t('marketCommon.failureOperation') })
              }
            })
          } else {
            // 编辑限时降价
            updateReducePrice(this.reduceData).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: '编辑成功' })
                this.$emit('addReduceSubmit')
              } else {
                this.$message.warning({ message: '编辑失败' })
              }
            })
          }
        }
      })
    },

    clickHandler () {

    }

    // 提交前校验
    // validParam() {
    //   if (!this.reduceData.name) {
    //     this.$message.warning(this.$t('reducePriceList.validActName'))
    //     return false
    //   }
    //   if (!this.reduceData.startTime || !this.reduceData.endTime) {
    //     this.$message.warning(this.$t('reducePriceList.validActDate'))
    //     return false
    //   }
    //   if (!this.reduceData.reducePriceGoodsAddParams || this.reduceData.reducePriceGoodsAddParams.length === 0) {
    //     this.$message.warning(this.$t('reducePriceList.validActGoods'))
    //     return false
    //   }
    //   let flag = false
    //   this.reduceData.reducePriceGoodsAddParams.forEach(item => {
    //     if (!item.discount || !item.reducePrice || !item.goodsPrice) {
    //       flag = true
    //     } else {
    //       if (item.reducePriceProduct) {
    //         item.reducePriceProduct.forEach(prdItem => {
    //           if (!prdItem.prdPrice) {
    //             flag = true
    //           }
    //         })
    //       }
    //     }
    //   })
    //   if (flag) {
    //     this.$message.warning(this.$t('reducePriceList.validActGoodsPrice'))
    //     return false
    //   }
    //   return true
    // }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding-top: 10px;
  background: #fff;
  margin-bottom: 10px;
  .main {
    padding: 10px;
    background-color: #fff;
    .set_box {
      padding: 0 12px 0;
      border-radius: 3px;
      /deep/ .el-collapse {
        border: none;
        .el-collapse-item__wrap {
          border-bottom: none;
        }
        .el-collapse-item__header {
          border-bottom: none;
          color: #409eff;
          width: 105px;
        }
        .el-collapse-item__content {
          padding-bottom: 0;
        }
      }
    }
  }
  .small_input {
    width: 90px;
  }
  .default_input {
    width: 150px;
  }
  .goods_info {
    display: flex;
    > img {
      width: 45px;
      height: 45px;
      margin-right: 5px;
    }
    > .goods_name {
      flex: 1;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      overflow: hidden;
      /*! autoprefixer: off */
      -webkit-box-orient: vertical;
      text-align: left;
    }
  }
  .price_blue {
    color: #5a8bff;
    padding-top: 2px;
    cursor: pointer;
  }
  .price_red {
    color: red;
    padding-bottom: 2px;
    cursor: pointer;
    font-size: 12px;
  }
  .del_item {
    color: #66b1ff;
    cursor: pointer;
  }
}
.inputWidth {
  width: 170px;
}
.footer {
  width: 100%;
  height: 50px;
  padding: 10px 0;
  background: #f8f8f8;
  text-align: center;
}
.set_item {
  display: flex;
  &.batch_item {
    border-top: 1px solid #ebeef5;
    border-left: 1px solid #ebeef5;
    border-right: 1px solid #ebeef5;
    > .item_title {
      line-height: 60px;
    }
    > .item_right {
      line-height: 60px;
    }
  }
  > .item_title {
    width: 150px;
    font-size: 14px;
    text-align: right;
    line-height: 32px;
    color: #333;
    > em {
      color: red;
    }
  }
  > .item_right {
    flex: 1;
    line-height: 32px;
    > .item_tips {
      color: #999;
      font-size: 14px;
    }
    > .limit {
      padding-left: 25px;
    }
    .choose_list {
      > .choose_item {
        display: flex;
        line-height: 30px;
        > .item_left {
          width: 120px;
          background-color: #fff;
          color: #5a8bff;
          border: 1px solid #ddd;
          cursor: pointer;
          text-align: center;
          font-size: 14px;
        }
        > .item_right {
          flex: 1;
        }
      }
    }
  }
  .custom_share_item {
    display: flex;
    > .item_title {
      font-size: 14px;
      text-align: right;
      line-height: 32px;
    }
    > .item_right {
      flex: 1;
      line-height: 32px;
      .upload_img {
        padding-left: 25px;
        .bgImgDiv {
          width: 65px;
          height: 65px;
          border: 1px solid #ccc;
          background-position: center;
          cursor: pointer;
        }
      }
    }
  }
}
.shareContent a {
  text-decoration: none;
  color: #409eff;
}
.shareContent a:first-child {
  margin-right: 10px;
}
.imgContent {
  width: 80px;
  height: 80px;
  text-align: center;
  line-height: 65px;
  margin-left: 60px;
  border: 1px solid #ccc;
  cursor: pointer;
  box-sizing: border-box;
}
.imgContent .shareImg {
  width: 100%;
  height: 100%;
}
.picSizeTips {
  display: block;
  line-height: 80px;
  margin-left: 20px;
  color: rgb(153, 153, 153);
}
</style>
