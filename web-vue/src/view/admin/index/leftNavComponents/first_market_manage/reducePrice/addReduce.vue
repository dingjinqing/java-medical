<template>
  <div class="content">
    <div class="main">
      <div class="set_box">
        <div class="set_item">
          <div class="item_title">
            <em>*</em> {{$t('marketCommon.actName')}}：
          </div>
          <div class="item_right">
            <el-input
              v-model="reduceData.name"
              :placeholder="$t('marketCommon.actNamePlaceholder')"
              minlength="1"
              size="small"
              class="default_input"
            ></el-input>
            <p class="item_tips">{{$t('reducePriceList.actNameTip')}}</p>
          </div>
        </div>
        <div class="set_item">
          <div class="item_title">
            <em>*</em> {{$t('marketCommon.validDate')}}：
          </div>
          <div class="item_right">
            <el-date-picker
              v-model="reduceData.effectiveDate"
              type="datetimerange"
              :range-separator="$t('marketCommon.to')"
              :start-placeholder="$t('marketCommon.startTime')"
              :end-placeholder="$t('marketCommon.endTime')"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              size="small"
            >
            </el-date-picker>
            <el-checkbox
              v-model="reduceData.isCycle"
              @change="showCycleDialog"
            >{{$t('reducePriceList.repeatByCycle')}}</el-checkbox>
          </div>
        </div>
        <div class="set_item">
          <div class="item_title">
            <em>*</em> {{$t('reducePriceList.purchaseQuantity')}}：
          </div>
          <div class="item_right">
            <el-radio
              v-model="reduceData.isLimit"
              label="0"
            >{{$t('reducePriceList.noLimit')}}</el-radio>
            <br />
            <el-radio
              v-model="reduceData.isLimit"
              label="1"
            >{{$t('reducePriceList.limitQuantity')}}
              <el-input-number
                v-model="reduceData.limitAmount"
                controls-position="right"
                :min="1"
                size="small"
                class="small_input"
              ></el-input-number>
              {{$t('reducePriceList.piece')}}
            </el-radio>
            <div
              class="limit"
              v-if="reduceData.isLimit === '1'"
            >
              <el-checkbox v-model="reduceData.limitFlag">{{$t('reducePriceList.limitQuantityTip')}}</el-checkbox>
            </div>
          </div>
        </div>
        <div class="set_item">
          <div class="item_title">
            <em>*</em> {{$t('marketCommon.activityGoods')}}：
          </div>
          <div class="item_right">
            <div class="choose_list">
              <div class="choose_item">
                <div
                  class="item_left"
                  @click="showChoosingGoods"
                >
                  <img :src="$imageHost+'/image/admin/icon_jia.png'">
                  {{$t('marketCommon.selectGoods')}}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-if="pageShowGoodsList.length">
          <div class="set_item batch_item">
            <div class="item_title">
              <em>*</em> {{$t('reducePriceList.setDiscount')}}：
            </div>
            <div class="item_right">
              <el-radio-group v-model="batchFlag">
                <el-radio :label="1">{{$t('reducePriceList.batch')}}<el-input-number
                    :disabled="batchFlag != 1 ? true : false"
                    v-model="reduceData.batchDiscount"
                    :controls="false"
                    size="small"
                    class="small_input"
                    :min="0"
                    :max="10"
                  ></el-input-number>{{$t('reducePriceList.discount')}}</el-radio>
                <el-radio :label="2">{{$t('reducePriceList.batch')}}{{$t('reducePriceList.priceReduction')}}<el-input-number
                    :disabled="batchFlag != 2 ? true : false"
                    v-model="reduceData.batchReduce"
                    :controls="false"
                    size="small"
                    class="small_input"
                    :min="0"
                  ></el-input-number>{{$t('marketCommon.yuan')}}</el-radio>
                <el-radio :label="3">{{$t('reducePriceList.batch')}}{{$t('reducePriceList.priceAfterDiscount')}}<el-input-number
                    :disabled="batchFlag != 3 ? true : false"
                    v-model="reduceData.batchFinalPrice"
                    :controls="false"
                    size="small"
                    class="small_input"
                    :min="0"
                  ></el-input-number>{{$t('marketCommon.yuan')}}</el-radio>
              </el-radio-group>
              <el-button
                type="primary"
                size="small"
                @click="batchSet"
              >{{$t('marketCommon.ok')}}</el-button>
              <el-button
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
                    v-model="scope.row.discount"
                    :controls="false"
                    :precision="2"
                    size="small"
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
                    v-model="scope.row.reducePrice"
                    :controls="false"
                    :precision="2"
                    size="small"
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
                    v-model="scope.row.goodsPrice"
                    :controls="false"
                    :precision="2"
                    size="small"
                    class="small_input"
                    @input="changeItemGoodsPrice(scope.row)"
                  ></el-input-number> {{$t('marketCommon.yuan')}}
                  <p
                    class="price_blue"
                    @click="getProductInfo(scope.row)"
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
                  <a
                    class="del_item"
                    @click="delReduceData(scope.row.goodsId)"
                  >{{$t('marketCommon.delete')}}</a>
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
        <el-collapse
          v-model="activeName"
          accordion
        >
          <el-collapse-item
            :title="activeName === '1' ? $t('marketCommon.collapseMoreConfigurations'):$t('marketCommon.expandMoreConfigurations')"
            name="1"
          >
            <div class="set_item">
              <div class="item_title">
                <em>*</em> {{$t('reducePriceList.setDiscount')}}：
              </div>
              <div class="item_right">
                <!-- 引入活动分享模块 -->
                <actShare :shareConfig="reduceData.shareConfig" />
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <div class="footer">
      <el-button
        @click="isEditFlag?updateSubmit():addSubmit()"
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
      @handelCycleData="getCycleData"
    />
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
import { addReducePrice, getReducePriceById } from '@/api/admin/marketManage/reducePrice.js'
import { getGoodsInfosByGoodIds } from '@/api/admin/goodsManage/allGoods/allGoods'
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    CycleDialog: () => import('./repeatCycle'),
    choosingGoods: () => import('@/components/admin/choosingGoods'),
    productInfo: () => import('./productInfo'),
    actShare: () => import('@/components/admin/marketManage/marketActivityShareSetting')
  },
  data () {
    return {
      isEditFlag: false,
      actId: null,
      batchFlag: null,
      reduceData: {
        name: '',
        effectiveDate: '',
        periodAction: '0',
        isCycle: false,
        isLimit: '0',
        limitFlag: '0',
        limitAmount: 1,
        batchDiscount: '',
        batchReduce: '',
        batchFinalPrice: '',
        shareConfig: {
          share_action: '1',
          share_doc: '',
          share_img_action: '1',
          share_img: ''
        }
      },
      activeName: null,
      cycleDialogShow: false,
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

    if (this.$route.query.id > 0) {
      // 编辑限时降价活动
      this.actId = this.$route.query.id
      // 编辑时部分信息不可修改
      this.isEditFlag = true
      // 点击编辑按钮进来，初始化页面数据
      let SimpleBargainParam = {
        'id': this.$route.query.id
      }
      getReducePriceById(SimpleBargainParam).then((res) => {
        if (res.error === 0) {
          // 解析返回的数据结构，回显
          this.reduceData = res.content
          this.reduceData.effectiveDate = []
          this.reduceData.effectiveDate.push(res.content.startTime)
          this.reduceData.effectiveDate.push(res.content.endTime)
          this.reduceData.shareConfig = res.content.shopShareConfig
          res.content.reducePriceGoods.map(item => {
            item.goodsName = item.goodsView.goodsName
            item.goodsNumber = item.goodsView.goodsNumber
            item.goodsImg = item.goodsView.goodsImg
            item.shopPrice = item.goodsView.shopPrice
          })
          this.pageShowGoodsList = res.content.reducePriceGoods
        }
      })
    }
  },
  methods: {
    // 添加图片
    handleToAddImg () {
      this.tuneUp = !this.tuneUp
    },
    // 图片选中
    handleSelectImg (res) {
      this.reduceData.shareConfig.share_img = res.imgPath
      console.log(res)
    },
    showCycleDialog (val) {
      if (this.changeFlag) {
        this.cycleDialogShow = val
      }
    },
    getCycleData (arr) {
      console.log(1)
    },
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
              let goodsPrice = (shopPrice * parseFloat(this.reduceData.batchDiscount / 10)).toFixed(2)
              let reducePrice = parseFloat(shopPrice - goodsPrice).toFixed(2)
              item.goodsPrice = goodsPrice
              item.reducePrice = reducePrice
              item.discount = this.reduceData.batchDiscount
              if (item.reducePriceProduct && item.reducePriceProduct.length) {
                item.reducePriceProduct.map(item2 => {
                  let originalPrice = item2.originalPrice
                  let prdPrice = (originalPrice * (parseFloat(this.reduceData.batchDiscount / 10))).toFixed(2)
                  item2.prdPrice = prdPrice
                })
              }
            })
            break
          case 2:
            this.$refs.multipleTable.selection.map(item => {
              let shopPrice = parseFloat(item.shopPrice)
              let goodsPrice = parseFloat(shopPrice - this.reduceData.batchReduce).toFixed(2)
              let discount = (parseFloat(goodsPrice / shopPrice) * 10).toFixed(2)
              item.goodsPrice = goodsPrice
              item.reducePrice = this.reduceData.batchReduce
              item.discount = discount
              if (item.reducePriceProduct && item.reducePriceProduct.length) {
                item.reducePriceProduct.map(item2 => {
                  let originalPrice = item2.originalPrice
                  item2.prdPrice = parseFloat(originalPrice - this.reduceData.batchReduce).toFixed(2)
                })
              }
            })
            break
          case 3:
            this.$refs.multipleTable.selection.map(item => {
              let shopPrice = parseFloat(item.shopPrice)
              let reducePrice = parseFloat(shopPrice - this.reduceData.batchFinalPrice).toFixed(2)
              let discount = (parseFloat(this.reduceData.batchFinalPrice / shopPrice) * 10).toFixed(2)
              item.goodsPrice = this.reduceData.batchFinalPrice
              item.reducePrice = reducePrice
              item.discount = discount
              if (item.reducePriceProduct && item.reducePriceProduct.length) {
                item.reducePriceProduct.map(item2 => {
                  item2.prdPrice = this.reduceData.batchFinalPrice
                })
              }
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
      let goodsPrice = (shopPrice * parseFloat(rowData.discount / 10)).toFixed(2)
      let reducePrice = parseFloat(shopPrice - goodsPrice).toFixed(2)
      itemData.goodsPrice = goodsPrice
      itemData.reducePrice = reducePrice
      if (itemData.reducePriceProduct && itemData.reducePriceProduct.length) {
        itemData.reducePriceProduct.map(item => {
          let originalPrice = item.originalPrice
          item.prdPrice = (originalPrice * (parseFloat(rowData.discount / 10))).toFixed(2)
        })
      }
    },
    changeItemReducePrice (rowData) {
      let goodsTarget = this.pageShowGoodsList.findIndex(item => {
        return rowData.goodsId === item.goodsId
      })
      let itemData = this.pageShowGoodsList[goodsTarget]
      let shopPrice = parseFloat(itemData.shopPrice)
      let goodsPrice = parseFloat(shopPrice - rowData.reducePrice).toFixed(2)
      let discount = (parseFloat(goodsPrice / shopPrice) * 10).toFixed(2)
      itemData.goodsPrice = goodsPrice
      itemData.discount = discount
      if (itemData.reducePriceProduct && itemData.reducePriceProduct.length) {
        itemData.reducePriceProduct.map(item => {
          let originalPrice = item.originalPrice
          item.prdPrice = parseFloat(originalPrice - rowData.reducePrice).toFixed(2)
        })
      }
    },
    changeItemGoodsPrice (rowData) {
      let goodsTarget = this.pageShowGoodsList.findIndex(item => {
        return rowData.goodsId === item.goodsId
      })
      let itemData = this.pageShowGoodsList[goodsTarget]
      let shopPrice = parseFloat(itemData.shopPrice)
      let reducePrice = parseFloat(shopPrice - rowData.goodsPrice).toFixed(2)
      let discount = (parseFloat(rowData.goodsPrice / shopPrice) * 10).toFixed(2)
      itemData.reducePrice = reducePrice
      itemData.discount = discount
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
    addSubmit () {
      this.reduceData.startTime = this.reduceData.effectiveDate[0]
      this.reduceData.endTime = this.reduceData.effectiveDate[1]
      this.reduceData.reducePriceGoodsAddParams = this.pageShowGoodsList
      this.reduceData.limitFlag = this.reduceData.limitFlag ? 1 : 0

      if (this.validParam()) {
        addReducePrice(this.reduceData).then((res) => {
          if (res.error === 0) {
            this.$message.success({
              message: this.$t('marketCommon.successfulOperation')
            })
            this.$router.push({
              name: 'reduce'
            })
          } else {
            this.$message.error({
              message: this.$t('marketCommon.failureOperation')
            })
          }
        })
      }
    },
    updateSubmit () {
      // 更新活动
    },
    // 提交前校验
    validParam () {
      if (!this.reduceData.name) {
        this.$message.warning(this.$t('reducePriceList.validActName'))
        return false
      }
      if (!this.reduceData.startTime || !this.reduceData.endTime) {
        this.$message.warning(this.$t('reducePriceList.validActDate'))
        return false
      }
      if (!this.reduceData.reducePriceGoodsAddParams || this.reduceData.reducePriceGoodsAddParams.length === 0) {
        this.$message.warning(this.$t('reducePriceList.validActGoods'))
        return false
      }
      let flag = false
      this.reduceData.reducePriceGoodsAddParams.forEach(item => {
        if (!item.discount || !item.reducePrice || !item.goodsPrice) {
          flag = true
        } else {
          item.reducePriceProduct.forEach(prdItem => {
            if (!prdItem.prdPrice) {
              flag = true
            }
          })
        }
      })
      if (flag) {
        this.$message.warning(this.$t('reducePriceList.validActGoodsPrice'))
        return false
      }
      return true
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
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
      .set_item {
        display: flex;
        margin-bottom: 15px;
        &.batch_item {
          margin-bottom: 0;
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
.footer {
  width: 100%;
  height: 50px;
  padding: 10px 0;
  background: #f8f8f8;
  text-align: center;
}
</style>
