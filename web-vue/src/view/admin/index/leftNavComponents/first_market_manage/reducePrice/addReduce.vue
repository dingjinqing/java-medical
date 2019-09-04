<template>
  <div class="content">
    <div class="main">
      <div class="set_box">
        <div class="set_item">
          <div class="item_title">
            <em>*</em> 活动名称：
          </div>
          <div class="item_right">
            <el-input
              v-model="reduceData.name"
              placeholder="请填写活动名称"
              size="small"
              class="default_input"
            ></el-input>
            <p class="item_tips">只作为商家记录使用，用户不会看到这个名称</p>
          </div>
        </div>
        <div class="set_item">
          <div class="item_title">
            <em>*</em> 有效期：
          </div>
          <div class="item_right">
            <el-date-picker
              v-model="reduceData.effectiveDate"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM-dd HH:mm:ss"
              size="small"
            >
            </el-date-picker>
            <el-checkbox
              v-model="reduceData.isCycle"
              @change="showCycleDialog"
            >按周期重复</el-checkbox>
          </div>
        </div>
        <div class="set_item">
          <div class="item_title">
            <em>*</em> 限购数量：
          </div>
          <div class="item_right">
            <el-radio
              v-model="reduceData.isLimit"
              label="0"
            >不限制</el-radio>
            <br />
            <el-radio
              v-model="reduceData.isLimit"
              label="1"
            >限制数量
              <el-input-number
                v-model="reduceData.limitAmount"
                controls-position="right"
                :min="1"
                size="small"
                class="small_input"
              ></el-input-number>
              件
            </el-radio>
            <div
              class="limit"
              v-if="reduceData.isLimit === '1'"
            >
              <el-checkbox v-model="reduceData.isLimit2">超出限购数量后，买家不可继续添加购买该商品</el-checkbox>
            </div>
          </div>
        </div>
        <div class="set_item">
          <div class="item_title">
            <em>*</em> 活动商品：
          </div>
          <div class="item_right">
            <div class="choose_list">
              <div class="choose_item">
                <div
                  class="item_left"
                  @click="showChoosingGoods"
                >
                  <img :src="$imageHost+'/image/admin/icon_jia.png'">
                  选择商品
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-if="pageShowGoodsList.length">
          <div class="set_item batch_item">
            <div class="item_title">
              <em>*</em> 设置折扣：
            </div>
            <div class="item_right">
              <el-radio-group v-model="batchFlag">
                <el-radio :label="1">批量打<el-input
                    :disabled="batchFlag != 1 ? true : false"
                    v-model="reduceData.batchDiscount"
                    size="small"
                    class="small_input"
                  ></el-input>折</el-radio>
                <el-radio :label="2">批量减价<el-input
                    :disabled="batchFlag != 2 ? true : false"
                    v-model="reduceData.batchReduce"
                    size="small"
                    class="small_input"
                  ></el-input>元</el-radio>
                <el-radio :label="3">批量折后价<el-input
                    :disabled="batchFlag != 3 ? true : false"
                    v-model="reduceData.batchFinalPrice"
                    size="small"
                    class="small_input"
                  ></el-input>元</el-radio>
              </el-radio-group>
              <el-button
                type="primary"
                size="small"
                @click="batchSet"
              >确定</el-button>
              <el-button
                type="default"
                size="small"
                @click="resetPrice"
              >取消</el-button>
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
                width="400"
              >
                <template slot-scope="scope">
                  <div class="goods_info">
                    <img
                      :src="$imageHost+'/image/admin/icon_jia.png'"
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
                width="130"
              >
                <template slot-scope="scope">
                  <el-input
                    v-model="scope.row.discount"
                    size="small"
                    class="small_input"
                    @input="changeItemDiscount(scope.row)"
                  ></el-input> 折
                </template>
              </el-table-column>
              <el-table-column
                :prop="item.prop"
                :label="item.label"
                :key="item.index"
                v-else-if="item.index === 5"
                width="130"
              >
                <template slot-scope="scope">
                  <el-input
                    v-model="scope.row.reducePrice"
                    size="small"
                    class="small_input"
                    @input="changeItemReducePrice(scope.row)"
                  ></el-input> 元
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
                  <el-input
                    v-model="scope.row.goodsPrice"
                    size="small"
                    class="small_input"
                    @input="changeItemGoodsPrice(scope.row)"
                  ></el-input> 元
                  <p
                    class="price_blue"
                    @click="getProductInfo(scope.row)"
                    v-if="scope.row.goodsProductAddParams && scope.row.goodsProductAddParams.length>0"
                  >{{scope.row.goodsProductAddParams.length}}个规格降价</p>
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
                  >删除</a>
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
            :title="activeName === '1' ? '收起更多配置':'展开更多配置'"
            name="1"
          >
            <div class="set_item">
              <div class="item_title">
                <em>*</em> 活动分享：
              </div>
              <div class="item_right">
                <el-radio
                  v-model="reduceData.shareConfig.share_action"
                  label="1"
                >默认样式</el-radio>
                <br />
                <el-radio
                  v-model="reduceData.shareConfig.share_action"
                  label="2"
                >自定义样式</el-radio>
                <div
                  v-if="reduceData.shareConfig.share_action ==='2'"
                  class="custom_share"
                >
                  <div class="custom_share_item">
                    <div class="item_title">文案：</div>
                    <div class="item_right">
                      <el-input
                        v-model="reduceData.shareConfig.share_doc"
                        placeholder="请输入分享图文案"
                        size="small"
                        class="default_input"
                      ></el-input>
                    </div>
                  </div>
                  <div class="custom_share_item">
                    <div class="item_title">自定义图片：</div>
                    <div class="item_right">
                      <el-radio
                        v-model="reduceData.shareConfig.share_img_action"
                        label="1"
                      >活动商品信息图</el-radio>
                      <br />
                      <el-radio
                        v-model="reduceData.shareConfig.share_img_action"
                        label="2"
                      >自定义图片</el-radio>
                      <div
                        v-if="reduceData.shareConfig.share_img_action === '2'"
                        class="upload_img"
                      >
                        <img
                          :src="reduceData.shareConfig.share_img ? $imageHost +'/' + reduceData.shareConfig.share_img : ' '"
                          class="bgImgDiv"
                          @click="handleToAddImg()"
                          :style="`backgroundImage:url(${$imageHost}/image/admin/add_img.png);backgroundRepeat:no-repeat`"
                        />
                      </div>
                    </div>
                  </div>

                </div>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <!--图片弹窗-->
    <ImageDalog
      pageIndex='userCardAdd'
      @handleSelectImg='handleSelectImg'
    />
    <!-- 重复周期弹窗 -->
    <CycleDialog
      :cycleDialog="cycleDialogShow"
      @handelCycleData="getCycleData"
    />
    <choosingGoods @resultGoodsIds="getGoodsIds" />
    <!-- 规格信息弹框 -->
    <productInfo
      :productDialog.sync="productDialogFlag"
      :product-info="productInfo"
      @confrim="getProductdata"
    />

  </div>
</template>

<script>
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    CycleDialog: () => import('./repeatCycle'),
    choosingGoods: () => import('@/components/admin/choosingGoods'),
    productInfo: () => import('./productInfo')
  },
  data () {
    return {
      batchFlag: null,
      reduceData: {
        name: '',
        effectiveDate: [],
        periodAction: '1',
        isCycle: false,
        isLimit: '0',
        isLimit2: false,
        limitAmount: 1,
        batchDiscount: '',
        batchReduce: '',
        batchFinalPrice: '',
        shareConfig: {
          share_action: '1',
          share_doc: '',
          share_img_action: '1',
          share_img: null
        }
      },
      activeName: null,
      cycleDialogShow: false,
      changeFlag: false,
      productDialogFlag: false,
      productInfo: {},
      pageShowGoodsList: [
        {
          goodsId: 1,
          shopPrice: 321,
          goodsNumber: 10,
          goodsName: '第一古拉良品kookastyle原创2019新款收腰气质显瘦印花',
          goodsImg: '',
          discount: '',
          reducePrice: '',
          goodsPrice: '',
          goodsProductAddParams: [
            {
              productId: 12,
              prdDesc: '828272',
              originalPrice: '',
              prdPrice: 200
            }
          ]
        },
        {
          goodsId: 2,
          shopPrice: 400,
          goodsNumber: 10,
          goodsName: '第二古拉良品kookastyle原创2019新款收腰气质显瘦印花',
          goodsImg: '',
          discount: '',
          reducePrice: '',
          goodsPrice: '',
          goodsProductAddParams: [
            {
              productId: 12,
              prdDesc: '828272',
              originalPrice: '',
              prdPrice: 200
            },
            {
              productId: 12,
              prdDesc: '828272',
              originalPrice: '',
              prdPrice: 200
            }
          ]
        },
        {
          goodsId: 3,
          shopPrice: 400,
          goodsNumber: 10,
          goodsName: '第一古拉良品kookastyle原创2019第二古拉良品kookastyle原创2019新款收腰气质显瘦印花第二古拉良品kookastyle原创2019新款收腰气质显瘦印花新款收腰气质显瘦印花',
          goodsImg: '',
          discount: '',
          reducePrice: '',
          goodsPrice: '',
          goodsProductAddParams: [
            {
              productId: 12,
              prdDesc: '828272',
              originalPrice: '',
              prdPrice: 200
            }
          ]
        },
        {
          goodsId: 4,
          shopPrice: 400,
          goodsNumber: 10,
          goodsName: '第二古拉良品kookastyle原创2019新款收腰气质显瘦印花第二古拉良品kookastyle原创2019新款收腰气质显瘦印花第二古拉良品kookastyle原创2019新款收腰气质显瘦印花',
          goodsImg: '',
          discount: '',
          reducePrice: '',
          goodsPrice: '',
          goodsProductAddParams: [

          ]
        }
      ],
      tableLabel: [
        { index: 1, prop: 'goodsName', label: '商品名称' },
        { index: 2, prop: 'shopPrice', label: '原价' },
        { index: 3, prop: 'goodsNumber', label: '库存' },
        { index: 4, prop: 'discount', label: '折扣' },
        { index: 5, prop: 'reducePrice', label: '减价' },
        { index: 6, prop: 'goodsPrice', label: '折后价' },
        { index: 7, prop: '', label: '操作' }
      ]
    }
  },
  mounted () {
    this.changeFlag = true
  },
  methods: {
    // 添加图片
    handleToAddImg () {
      this.$http.$emit('dtVisible')
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
      this.$http.$emit('choosingGoodsFlag', true)
    },
    getGoodsIds (data) {
      console.log(data)
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
      console.log(goodsTarget)
      this.pageShowGoodsList[goodsTarget].goodsProductAddParams = ProductInfo
    },
    batchSet () {
      console.log(this.$refs.multipleTable.selection)
      if (this.$refs.multipleTable.selection.length === 0) {
        this.$message.error('请勾选商品后再试')
        return false
      } else {
        switch (this.batchFlag) {
          case 1:
            if (!this.reduceData.batchDiscount) {
              this.$message.error('请输入折扣数')
              return false
            } else if (parseFloat(this.reduceData.batchDiscount) <= 0 || parseFloat(this.reduceData.batchDiscount) >= 10) {
              this.$message.error('折扣只能输入0-10之间')
              return false
            }
            this.$refs.multipleTable.selection.map(item => {
              let shopPrice = parseFloat(item.shopPrice)
              let goodsPrice = (shopPrice * parseFloat(this.reduceData.batchDiscount / 10)).toFixed(2)
              let reducePrice = parseFloat(shopPrice - goodsPrice).toFixed(2)
              item.goodsPrice = goodsPrice
              item.reducePrice = reducePrice
              item.discount = this.reduceData.batchDiscount
              if (item.goodsProductAddParams && item.goodsProductAddParams.length) {
                item.goodsProductAddParams.map(item2 => {
                  let prdPrice = item2.prdPrice
                  let originalPrice = (prdPrice * (parseFloat(this.reduceData.batchDiscount / 10))).toFixed(2)
                  item2.originalPrice = originalPrice
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
              if (item.goodsProductAddParams && item.goodsProductAddParams.length) {
                item.goodsProductAddParams.map(item2 => {
                  let prdPrice = item2.prdPrice
                  item2.originalPrice = parseFloat(prdPrice - this.reduceData.batchReduce).toFixed(2)
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
              if (item.goodsProductAddParams && item.goodsProductAddParams.length) {
                item.goodsProductAddParams.map(item2 => {
                  item2.originalPrice = this.reduceData.batchFinalPrice
                })
              }
            })
            break
          default:
            this.$message.error('请选择批量类型')
            break
        }
      }
    },
    resetPrice () {
      this.pageShowGoodsList.map(item => {
        item.goodsPrice = ''
        item.reducePrice = ''
        item.discount = ''
        if (item.goodsProductAddParams && item.goodsProductAddParams.length) {
          item.goodsProductAddParams.map(item2 => {
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
      if (itemData.goodsProductAddParams && itemData.goodsProductAddParams.length) {
        itemData.goodsProductAddParams.map(item => {
          let prdPrice = item.prdPrice
          item.originalPrice = (prdPrice * (parseFloat(rowData.discount / 10))).toFixed(2)
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
      if (itemData.goodsProductAddParams && itemData.goodsProductAddParams.length) {
        itemData.goodsProductAddParams.map(item => {
          let prdPrice = item.prdPrice
          item.originalPrice = parseFloat(prdPrice - rowData.reducePrice).toFixed(2)
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
      if (itemData.goodsProductAddParams && itemData.goodsProductAddParams.length) {
        itemData.goodsProductAddParams.map(item => {
          item.originalPrice = rowData.goodsPrice
        })
      }
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
    width: 80px;
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
  .del_item {
    color: #66b1ff;
    cursor: pointer;
  }
}
</style>
