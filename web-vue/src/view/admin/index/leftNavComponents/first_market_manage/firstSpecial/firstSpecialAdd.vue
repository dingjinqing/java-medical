<template>
  <div class="content first-special-add-page">
    <div class="main">
      <div class="nav_list">
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            :label="$t('firstSpecialAdd.allActive')"
            name="0"
          ></el-tab-pane>
          <el-tab-pane
            :label="$t('firstSpecialAdd.processActive')"
            name="1"
          ></el-tab-pane>
          <el-tab-pane
            :label="$t('firstSpecialAdd.staredActive')"
            name="2"
          ></el-tab-pane>
          <el-tab-pane
            :label="$t('firstSpecialAdd.expired')"
            name="3"
          ></el-tab-pane>
          <el-tab-pane
            :label="$t('firstSpecialAdd.terminated')"
            name="4"
          ></el-tab-pane>
          <el-tab-pane
            :label="getTabName()"
            name="5"
          ></el-tab-pane>
        </el-tabs>
      </div>
      <div class="first-special-add-content">
        <div class="info-top">
          <h3>{{$t('firstSpecialAdd.description')}}</h3>
          <ol>
            <li>{{$t('firstSpecialAdd.des1')}}</li>
            <li>{{$t('firstSpecialAdd.des2')}}</li>
          </ol>
        </div>
        <el-form
          :model="form"
          ref="firstSpecialAddForm"
          label-width="115px"
          size="small"
          :rules="rules"
        >
          <el-form-item
            :label="$t('firstSpecialAdd.eventName')+'：'"
            prop="name"
          >
            <el-input
              class="form_input"
              v-model="form.name"
            ></el-input>
            <p class="form_tip">{{$t('firstSpecialAdd.eventTip')}}</p>
          </el-form-item>
          <el-form-item
            class="clearfix"
            :label="$t('firstSpecialAdd.validity')"
            prop="isForever"
          >
            <div
              class="fl"
              style="width: 95px;"
            >
              <el-radio-group v-model="form.isForever">
                <el-radio
                  :label="0"
                  style="line-height:32px;"
                >{{$t('firstSpecialAdd.fixedTime')}}</el-radio>
                <el-radio
                  :label="1"
                  style="line-height:32px;"
                >{{$t('firstSpecialAdd.permanent')}}</el-radio>
              </el-radio-group>
            </div>
            <div class="fl">
              <!-- <el-date-picker
                v-model="form.startTime"
                type="datetime"
                class="form_input"
                :placeholder="$t('firstSpecialAdd.chooseTime')"
                :disabled="!!form.isForever"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
              {{$t('firstSpecialAdd.to')}}
              <el-date-picker
                v-model="form.endTime"
                type="datetime"
                class="form_input"
                :placeholder="$t('firstSpecialAdd.chooseTime')"
                :disabled="!!form.isForever"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker> -->
              <el-date-picker
                v-model="form.timeInterval"
                type="datetimerange"
                range-separator="至"
                :start-placeholder="$t('firstSpecialAdd.chooseTime')"
                :end-placeholder="$t('firstSpecialAdd.chooseTime')"
                :disabled="!!form.isForever"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                :default-time="['00:00:00','23:59:59']"
              ></el-date-picker>
            </div>
          </el-form-item>
          <el-form-item
            :label="$t('firstSpecialAdd.priority')+'：'"
            prop="first"
          >
            <el-input-number
              v-model="form.first"
              :min='0'
              controls-position="right"
            ></el-input-number>
            <p class="form_tip">{{$t('firstSpecialAdd.priorityTip')}}</p>
          </el-form-item>
          <el-form-item
            :label="$t('firstSpecialAdd.restricted')+'：'"
            style="width:95px;"
            required
          >
            <el-radio-group
              v-model="limit"
              @change="limitChange"
            >
              <el-radio
                :label="0"
                style="line-height:32px;"
              >{{$t('firstSpecialAdd.notlimited')}}</el-radio>
              <el-radio
                :label="1"
                style="line-height:32px;"
              >
                {{$t('firstSpecialAdd.limitedNum')}}
                <el-input-number
                  v-model="form.limitAmount"
                  :disabled="!limit"
                  :min='1'
                  style="margin-left: 10px;"
                  controls-position="right"
                ></el-input-number>
              </el-radio>
            </el-radio-group>
            <el-checkbox
              v-model="form.limitFlag"
              style="margin-left: 30px;"
              :true-label="1"
              :false-label="0"
              :disabled="!limit"
            >{{$t('firstSpecialAdd.limitedTip')}}</el-checkbox>
          </el-form-item>
          <!-- 活动商品 -->
          <el-form-item
            :label="$t('firstSpecialAdd.activeGoods')+'：'"
            required
          >
            <el-button @click="selectGoodsHandle">+{{$t('firstSpecialAdd.chooseGoods')}}</el-button>
            <p class="form_tip">{{$t('firstSpecialAdd.selectUp')}}</p>
          </el-form-item>
          <!-- 设置商品首单优惠 -->
          <div
            class="add_content"
            v-if="tableData.length > 0"
          >
            <div class="table_head clearfix">
              <el-form-item
                class="table_head_setting fl"
                label-width="115px"
                :label="$t('firstSpecialAdd.setDiscount')+'：'"
                required
              >
                <el-radio-group
                  class="setting_group"
                  v-model="discountType"
                >
                  <el-radio label="0">
                    <span>
                      {{$t('firstSpecialAdd.batchDiscount')}} <el-input
                        class="num_input"
                        v-model="form.batchDiscount"
                        min="0"
                        max="10"
                        @focus="inputFocus(0)"
                      ></el-input>
                    </span>
                  </el-radio>
                  <el-radio label="1">
                    <span>
                      {{$t('firstSpecialAdd.batchPrice')}} <el-input
                        class="num_input"
                        v-model="form.batchReduce"
                        @focus="inputFocus(1)"
                      ></el-input> {{$t('firstSpecialAdd.yuan')}}
                    </span>
                  </el-radio>
                  <el-radio label="2">
                    <span>
                      {{$t('firstSpecialAdd.batch')}} <el-input
                        class="num_input"
                        v-model="form.batchFinalPrice"
                        @focus="inputFocus(2)"
                      ></el-input> {{$t('firstSpecialAdd.yuan')}}
                    </span>
                  </el-radio>
                </el-radio-group>
                <el-button
                  type="primary"
                  style="margin-left:60px;"
                  @click="volumeDiscountHandle"
                >{{$t('firstSpecialAdd.determine')}}</el-button>
                <el-button @click="resetTableData">{{$t('firstSpecialAdd.cancel')}}</el-button>
              </el-form-item>
              <div
                class="fr"
                style="padding-right:10px;"
              >
                <el-button
                  type="text"
                  @click="deleteSelectGoods"
                >{{$t('firstSpecialAdd.batchDeletion')}}</el-button>
                <el-button
                  type="text"
                  @click="roundingPrice"
                >{{$t('firstSpecialAdd.rounding')}}</el-button>
              </div>
            </div>
            <el-table
              ref="firstSpecialTable"
              :data="tableData"
              style="width:100%;"
              border
              align="center"
              :header-cell-style="{
                'background-color':'#f5f5f5',
                'border':'none'
              }"
              row-key="goodsId"
              reserve-selection
              @selection-change="handleSelectionChange"
              @row-click="toggleRowSelection"
            >
              <el-table-column type="selection"></el-table-column>
              <el-table-column
                :label="$t('firstSpecialAdd.productName')"
                prop="goodsName"
                width="230"
              >
                <template slot-scope="{row}">
                  <div>
                    <el-image
                      :src="row.goodsImg"
                      style="width:45px;height:45px;"
                    ></el-image>
                    <span>{{row.goodsName}}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('firstSpecialAdd.originalPrice')"
                prop="shopPrice"
              ></el-table-column>
              <el-table-column
                :label="$t('firstSpecialAdd.inStock')"
                prop="goodsNumber"
              ></el-table-column>
              <el-table-column :label="$t('firstSpecialAdd.discount')">
                <template slot-scope="{row}">
                  <el-input
                    style="width:80px;"
                    size="small"
                    v-model="row.batchDiscount"
                    @change="tableBatchDiscountChange(row)"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column :label="$t('firstSpecialAdd.priceReduction')">
                <template slot-scope="{row}">
                  <el-input
                    style="width:60px;"
                    size="small"
                    v-model="row.batchReduce"
                    @change="tableBatchReduceChange(row)"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('firstSpecialAdd.firstUnitPrice')"
                width="150"
              >
                <template slot-scope="{row}">
                  <p
                    v-if="row.tips"
                    style="color:red;font-size:12px;"
                  >{{row.tips}}</p>
                  <el-input
                    style="width:80px;"
                    size="small"
                    v-model="row.batchFinalPrice"
                    @change="tableBatchFinalPriceChange(row)"
                  ></el-input>
                  <el-button
                    type="text"
                    v-if="row.goodsProductParams && row.goodsProductParams.length > 0"
                    @click="getProductInfo(row)"
                  >{{row.goodsProductParams?row.goodsProductParams.length:0}}{{$t('firstSpecialAdd.specialPrice')}}</el-button>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('firstSpecialAdd.operate')"
                align="center"
              >
                <template slot-scope="{row}">
                  <div style="align: center;">
                    <span
                      class="iconSpan"
                      style="font-size:14px;"
                      @click.stop="deleteGood(row.goodsId)"
                    >{{$t('firstSpecialAdd.delete')}}</span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-button
            type="text"
            @click="showmore = !showmore"
          >
            <span v-if="showmore">{{$t('firstSpecialAdd.putAway')}}<img :src="$imageHost + '/image/admin/info_up.png'"></span>
            <span v-else>{{$t('firstSpecialAdd.expand')}}<img :src="$imageHost + '/image/admin/info_down.png'"></span>
          </el-button>
          <div
            class="more-setting"
            v-if="showmore"
          >
            <el-form-item
              :label="$t('firstSpecialAdd.sharing')"
              required
            >
              <el-radio-group v-model="form.shareConfig.shareAction">
                <div>
                  <el-radio
                    :label="1"
                    class="active_radio"
                  >{{$t('firstSpecialAdd.defaultStyle')}}
                    <el-popover
                      style="margin-left:40px;"
                      placement="right"
                      trigger="hover"
                    >
                      <div class="active_tool">
                        <el-image
                          style="width:240px;"
                          :src="$imageHost +'/image/admin/share/first_share1.jpg'"
                          :preview-src-list="srcList"
                        ></el-image>
                      </div>
                      <span slot="reference">{{$t('firstSpecialAdd.viewExample')}}</span>
                    </el-popover>
                    <el-popover
                      style="margin-left:40px;"
                      placement="right"
                      trigger="hover"
                    >
                      <div class="active_tool">
                        <el-image
                          style="width:240px;"
                          :src="$imageHost +'/image/admin/share/first_share2.jpg'"
                          :preview-src-list="srcList2"
                        ></el-image>
                      </div>
                      <span slot="reference">{{$t('firstSpecialAdd.downloadPoster')}}</span>
                    </el-popover>
                  </el-radio>
                </div>
                <div>
                  <el-radio
                    :label="2"
                    class="active_radio"
                  >
                    {{$t('firstSpecialAdd.customStyle')}}
                  </el-radio>
                </div>
              </el-radio-group>
              <div
                class="custom_style"
                v-show="form.shareConfig.shareAction === 2"
              >
                <div>
                  <label>{{$t('firstSpecialAdd.copywriting')}}：</label>
                  <el-input
                    class="form_input"
                    size="small"
                    v-model="form.shareConfig.shareDoc"
                  ></el-input>
                </div>
                <div>
                  <label>{{$t('firstSpecialAdd.shareMap')}}：</label>
                  <div>
                    <el-radio-group
                      class="share_img_group"
                      v-model="form.shareConfig.shareImgAction"
                    >
                      <el-radio
                        class="share_img_radio"
                        :label="1"
                      >{{$t('firstSpecialAdd.informationMap')}}</el-radio>
                      <el-radio
                        class="share_img_radio"
                        :label="2"
                      >{{$t('firstSpecialAdd.customPicture')}}</el-radio>
                    </el-radio-group>
                    <div class="upload_wrap">
                      <div style="display: flex">
                        <div
                          class="upload_img"
                          @click="uploadImgHandle"
                        >
                          <el-image
                            style="width: 100%; height:100%;"
                            fit="contain"
                            :src="form.shareConfig.shareImg"
                          ></el-image>
                        </div>
                        <p class="sizeTips">{{$t('firstSpecialAdd.size')}}</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
    <div class="footer">
      <el-button
        @click="isEditFlag?updateSubmit():addSubmit()"
        class="footer-btn"
        type="primary"
        size="small"
      >{{$t('firstSpecialAdd.save')}}</el-button>
    </div>
    <!--商品选择-->
    <choosingGoods
      @resultGoodsIds="getGoodsIds"
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="goodsIdList"
    />
    <!-- 图片上传 -->
    <imageDalog
      :tuneUp="tuneUp"
      pageIndex='pictureSpace'
      :imageSize=[800,800]
      @handleSelectImg="handleSelectImg"
    ></imageDalog>
    <!-- 规格弹窗 -->
    <productInfo
      :productDialog.sync="productDialogFlag"
      :product-info="productInfo"
      @confrim="getProductdata"
    ></productInfo>
  </div>
</template>

<script>
import choosingGoods from '@/components/admin/choosingGoods'
import imageDalog from '@/components/admin/imageDalog'
import productInfo from './productInfo'
import { getGoodsInfosByGoodIds } from '@/api/admin/goodsManage/allGoods/allGoods'
import { addFirstSpecial, getFirstSpecialById, updateFirstSpecial } from '@/api/admin/marketManage/firstSpecial.js'

export default {
  components: { choosingGoods, imageDalog, productInfo },
  data () {
    let that = this
    // 验证时间
    let validateTime = function (rule, value, callback) {
      if (that.form.isForever === 0 && (!that.form.timeInterval || !that.form.timeInterval[0] || !that.form.timeInterval[1])) {
        callback(new Error(that.$t('firstSpecialAdd.validityPeriodTip')))
      }
      callback()
    }
    return {
      id: '',
      isEditFlag: false, // 区分新增还是编辑
      activeName: '5', // 高亮tab
      form: {
        name: '',
        isForever: 0, // 是否永久有效
        timeInterval: [],
        startTime: '',
        endTime: '',
        first: '', // 活动优先级
        limitAmount: 0, // 限购数量
        limitFlag: 0, // 超限购买设置标记，1禁止超限购买，0超限全部恢复原价
        firstSpecialGoodsParams: [], // 改价的商品数组
        batchDiscount: '', // 批量打几折
        batchReduce: '', // 批量减多少
        batchFinalPrice: '', // 批量折后价
        isBatchInteger: 0, // 是否批量取整
        shareConfig: {
          shareAction: 1,
          shareDoc: '',
          shareImgAction: 1,
          shareImg: this.$imageHost + '/image/admin/btn_add.png'
        } // 分享设置
      },
      limit: 0,
      tuneUpChooseGoods: false,
      goodsIdList: [],
      tableData: [],
      selectGoods: [],
      discountType: '', // 批量折扣方式
      showmore: false,
      activeShare: 0,
      tuneUp: false,
      productDialogFlag: false,
      productInfo: {},
      rules: {
        name: { required: true, message: this.$t('firstSpecialAdd.validName'), trigger: 'blur' },
        isForever: [
          { required: true, message: this.$t('firstSpecialAdd.validIsForver') },
          { validator: validateTime }
        ],
        first: { required: true, message: this.$t('firstSpecialAdd.validFirst') }
      },
      srcList: [this.$imageHost + '/image/admin/share/first_share1.jpg'],
      srcList2: [this.$imageHost + '/image/admin/share/first_share2.jpg']
    }
  },
  created () {
    if (this.$route.query.id) {
      this.isEditFlag = true
      this.initEditData()
    }
  },
  methods: {
    getTabName () {
      if (this.$route.query.id) {
        return this.$t('firstSpecialAdd.editActive')
      } else {
        return this.$t('firstSpecialAdd.addActive')
      }
    },
    // 编辑进来的时候
    initEditData () {
      let _this = this
      _this.id = _this.$route.query.id
      let params = {
        id: _this.id
      }
      getFirstSpecialById(params).then(res => {
        if (res.error === 0) {
          let datas = res.content
          console.log('datas ', datas)
          for (const key in datas) {
            if (_this.form.hasOwnProperty(key)) {
              _this.form[key] = datas[key]
            }
          }
          if (datas.startTime && datas.endTime) {
            _this.form.timeInterval = [datas.startTime, datas.endTime]
          }
          let goodsIdList = []
          _this.tableData = datas.firstSpecialGoods.map(function (item, i) {
            // item.firstSpecialProduct.forEach(function (d) {
            //   console.log(d)
            // })
            let rowData = Object.assign({
              id: item.id,
              goodsId: item.goodsId,
              batchDiscount: item.discount,
              batchReduce: item.reducePrice,
              batchFinalPrice: item.goodsPrice,
              goodsProductParams: item.firstSpecialProduct,
              unit: item.goods
            }, item.goodsView)
            goodsIdList.push(item.goodsId)
            return rowData
          })
          _this.goodsIdList = goodsIdList
          if (datas.limitAmount > 0) {
            _this.limit = 1
          }
          _this.form.shareConfig = Object.assign({}, datas.shopShareConfig)
        }
      })
    },
    // 点击tab框
    handleClick (tab) {
      console.log(tab)
      this.$nextTick(() => {
        if (tab.index !== '5') {
          this.$router.push({
            path: '/admin/home/main/firstSpecial/list',
            query: {
              tabIndex: tab.index
            }
          })
        }
      })
    },
    limitChange (val) {
      if (val === 0) {
        this.$set(this.form, 'limitAmount', 0)
      } else {
        this.$set(this.form, 'limitAmount', 1)
      }
    },
    inputFocus (index) {
      this.discountType = String(index)
    },
    selectGoodsHandle () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    // 选择商品后，得到商品id
    getGoodsIds (ids) {
      this.goodsIdList = ids
      let params = {
        goodsIds: ids
      }
      getGoodsInfosByGoodIds(params).then(res => {
        if (res.error === 0) {
          let datas = res.content
          datas.forEach(function (item, i) {
            item.goodsProductParams = item.goodsSpecProducts
            if (item.goodsProductParams != null && item.goodsProductParams.length > 0) {
              item.goodsProductParams.forEach(spec => {
                spec.productId = spec.prdId
                spec.originalPrice = spec.prdPrice
              })
            }
          })
          // this.tableData = datas
          // 选择的商品与表格的商品比较，表格中如果有该商品，那么不进行覆盖
          let addData = []
          datas.forEach((data, i) => {
            let added = this.tableData.find(item => item.goodsId === data.goodsId)
            if (added) {
              addData.push(added)
            } else {
              addData.push(data)
            }
          })
          this.tableData = addData
        }
      })
    },
    deleteGood (goodsId) {
      this.$confirm(this.$t('firstSpecialAdd.tipDelete'), this.$t('firstSpecialAdd.remind'), {
        confirmButtonText: this.$t('firstSpecialAdd.determine'),
        cancelButtonText: this.$t('firstSpecialAdd.cancel'),
        type: 'warning'
      }).then(() => {
        this.tableData = this.tableData.filter(function (item, i) {
          return item.goodsId !== goodsId
        })
      })
    },
    handleSelectionChange (rows) {
      this.selectGoods = rows
    },
    toggleRowSelection (row) {
      this.$refs.firstSpecialTable.toggleRowSelection(row)
    },
    // 批量设置折扣
    volumeDiscountHandle () {
      // 验证是否选择商品
      if (this.selectGoods.length === 0) {
        this.$message.warning(this.$t('firstSpecialAdd.selectGoodsTip'))
        return false
      }
      if (this.discountType === '') {
        this.$message.warning(this.$t('firstSpecialAdd.discountTip1'))
        return false
      }
      switch (this.discountType) {
        case '0':
          if (this.form.batchDiscount === '' || this.form.batchDiscount < 0 || this.form.batchDiscount > 10) {
            this.$message.warning(this.$t('firstSpecialAdd.discountTip2'))
            return false
          }
          this.$set(this.form, 'batchReduce', '')
          this.$set(this.form, 'batchFinalPrice', '')
          break
        case '1':
          if (this.form.batchReduce === '') {
            this.$message.warning(this.$t('firstSpecialAdd.settingTip'))
            return false
          }
          this.$set(this.form, 'batchDiscount', '')
          this.$set(this.form, 'batchFinalPrice', '')
          break
        case '2':
          if (this.form.batchFinalPrice === '') {
            this.$message.warning(this.$t('firstSpecialAdd.settingTip'))
            return false
          }
          this.$set(this.form, 'batchReduce', '')
          this.$set(this.form, 'batchDiscount', '')
          break
      }
      // 更改商品列表折后价
      this.selectGoods.forEach((item, i) => {
        let price = Number(item.shopPrice)
        if (price <= 0) {
          this.$message.error(item.goodsName + this.$t('firstSpecialAdd.priceLimitTip'))
          return false
        }
        if (this.discountType === '0') {
          item.batchDiscount = this.form.batchDiscount
          item.batchFinalPrice = (item.batchDiscount / 10 * price).toFixed(2)
          item.batchReduce = price - item.batchFinalPrice
        } else if (this.discountType === '1') {
          item.batchReduce = this.form.batchReduce
          item.batchFinalPrice = Number(price - item.batchReduce).toFixed(2)
          item.batchDiscount = (item.batchFinalPrice / price).toFixed(2) * 10
        } else if (this.discountType === '2') {
          item.batchFinalPrice = this.form.batchFinalPrice
          item.batchDiscount = (item.batchFinalPrice / price).toFixed(2) * 10
          item.batchReduce = price - item.batchFinalPrice
        }
        // 验证计算值安全性
        console.log(item)
        if (item.batchFinalPrice < 0) {
          item.tips = this.$t('firstSpecialAdd.batchFinalPriceTip1')
        } else if (item.batchFinalPrice > price) {
          item.tips = this.$t('firstSpecialAdd.batchFinalPriceTip2')
        }
        if (item.goodsProductParams && item.goodsProductParams.length) {
          item.goodsProductParams.map(item2 => {
            item2.productId = item2.prdId
            // item2.originalPrice = item2.prdPrice
            let originalPrice = item2.originalPrice
            let prdPrice = originalPrice
            if (this.discountType === '0') {
              prdPrice = (originalPrice * (parseFloat(item.batchDiscount / 10))).toFixed(2)
            } else if (this.discountType === '1') {
              prdPrice = Number(originalPrice - item.batchReduce)
            } else if (this.discountType === '2') {
              prdPrice = Number(item.batchFinalPrice)
            }
            item2.prdPrice = prdPrice
          })
        }
        // 数据回显
        let index = this.tableData.findIndex((data, i) =>
          data.goodsId === item.goodsId
        )
        this.$set(this.tableData, index, item)
        this.$set(this.form, 'isBatchInteger', 0)
      })
    },
    // 表格内输入折扣
    tableBatchDiscountChange (row) {
      console.log('BatchDiscountChange')
      let price = Number(row.shopPrice)
      let batchDiscount = Number(row.batchDiscount)
      if (batchDiscount < 0 || batchDiscount > 10) {
        this.$message.warning(this.$t('firstSpecialAdd.discountTip2'))
        this.$set(row, 'batchDiscount', '')
        return false
      }
      let batchFinalPrice = (batchDiscount / 10 * price).toFixed(2)
      let batchReduce = price - batchFinalPrice
      this.$set(row, 'batchFinalPrice', batchFinalPrice)
      this.$set(row, 'batchReduce', batchReduce)
      this.watchbatchFinalPrice(price, batchFinalPrice, row, 'discount')
    },
    // 表格内输入减价
    tableBatchReduceChange (row) {
      console.log('BatchReduceChange')
      let price = Number(row.shopPrice)
      let batchReduce = Number(row.batchReduce)
      let batchFinalPrice = price - batchReduce
      let batchDiscount = (batchFinalPrice / price).toFixed(2) * 10
      if (isNaN(Number(batchDiscount))) {
        batchDiscount = 0
      }
      this.$set(row, 'batchFinalPrice', batchFinalPrice)
      this.$set(row, 'batchDiscount', batchDiscount)
      this.watchbatchFinalPrice(price, batchFinalPrice, row, 'reduce')
    },
    // 表格内输入首单价
    tableBatchFinalPriceChange (row) {
      console.log('BatchFinalPriceChange')
      let price = Number(row.shopPrice)
      let batchFinalPrice = Number(row.batchFinalPrice)
      let batchReduce = price - batchFinalPrice
      let batchDiscount = (batchFinalPrice / price).toFixed(2) * 10
      this.$set(row, 'batchReduce', batchReduce)
      if (isNaN(Number(batchDiscount))) {
        batchDiscount = 0
      }
      this.$set(row, 'batchDiscount', batchDiscount)
      this.watchbatchFinalPrice(price, batchFinalPrice, row, 'final')
    },
    watchbatchFinalPrice (price, batchFinalPrice, row, operate) {
      let tips
      if (batchFinalPrice < 0) {
        tips = this.$t('firstSpecialAdd.batchFinalPriceTip1')
      } else if (batchFinalPrice > price) {
        tips = this.$t('firstSpecialAdd.batchFinalPriceTip2')
      }
      this.$set(row, 'tips', tips)
      if (row.goodsProductParams && row.goodsProductParams.length) {
        row.goodsProductParams.map(item2 => {
          item2.productId = item2.prdId
          let originalPrice = item2.originalPrice
          let prdPrice = originalPrice
          if (operate === 'discount') {
            prdPrice = (originalPrice * (parseFloat(row.batchDiscount / 10))).toFixed(2)
          } else if (operate === 'reduce') {
            prdPrice = (originalPrice - row.batchReduce)
          } else if (operate === 'final') {
            prdPrice = row.batchFinalPrice
          }
          item2.prdPrice = prdPrice
          this.$set(item2, 'prdPrice', prdPrice)
          let tips2 = ''
          if (prdPrice < 0) {
            tips2 = this.$t('firstSpecialAdd.batchFinalPriceTip1')
          } else if (prdPrice > price) {
            tips2 = this.$t('firstSpecialAdd.batchFinalPriceTip2')
          }
          this.$set(item2, 'tips', tips2)
        })
      }
    },
    // 多个规格
    getProductInfo (row) {
      this.productDialogFlag = true
      row.reducePriceProduct = row.goodsProductParams
      this.productInfo = row
      console.log(productInfo)
    },
    // 批量删除
    deleteSelectGoods () {
      if (this.selectGoods.length === 0) {
        this.$message.warning(this.$t('firstSpecialAdd.selectGoodsTip'))
        return false
      }
      let selects = this.selectGoods
      let that = this
      this.$confirm(this.$t('firstSpecialAdd.tipDelete'), this.$t('firstSpecialAdd.remind'), {
        confirmButtonText: this.$t('firstSpecialAdd.determine'),
        cancelButtonText: this.$t('firstSpecialAdd.cancel'),
        type: 'warning'
      }).then(function () {
        that.tableData = that.tableData.filter(function (item, i) {
          let judgment = selects.find((data, j) => data.goodsId === item.goodsId)
          if (!judgment) {
            return item
          }
        })
      })
    },
    // 批量价格取整
    roundingPrice () {
      let that = this
      if (that.selectGoods.length === 0) {
        that.$message.warning(this.$t('firstSpecialAdd.selectGoodsTip'))
        return false
      }
      that.$confirm(this.$t('firstSpecialAdd.tipRound'), this.$t('firstSpecialAdd.remind'), {
        confirmButtonText: this.$t('firstSpecialAdd.determine'),
        cancelButtonText: this.$t('firstSpecialAdd.cancel'),
        type: 'warning'
      }).then(() => {
        if (that.selectGoods.length > 0) {
          that.selectGoods.forEach(function (item, i) {
            let index = that.tableData.findIndex((row, j) => row.goodsId === item.goodsId)
            let batchFinalPrice = Math.ceil(that.tableData[index].batchFinalPrice)
            item.batchFinalPrice = batchFinalPrice
            item.goodsProductParams = item.goodsProductParams.map((good, k) => {
              if (good.prdPrice) {
                good.prdPrice = Math.ceil(good.prdPrice)
              }
              return good
            })
            that.$set(that.tableData, index, item)
            console.log(that.tableData[index])
          })
          that.$refs.firstSpecialTable.clearSelection() // 触发表格数据刷新
          this.$set(this.form, 'isBatchInteger', 1)
        }
      })
    },
    // 取消
    resetTableData () {
      this.discountType = ''
      this.$set(this.form, 'batchReduce', '')
      this.$set(this.form, 'batchDiscount', '')
      this.$set(this.form, 'batchFinalPrice', '')
      this.tableData = this.tableData.map(function (item, i) {
        item.batchDiscount = ''
        item.batchReduce = ''
        item.batchFinalPrice = ''
        return item
      })
    },
    uploadImgHandle () {
      this.tuneUp = !this.tuneUp
    },
    handleSelectImg (image) {
      this.$set(this.form.shareConfig, 'shareImg', image.imgUrl)
    },
    getProductdata (goodsId, datas) {
      this.tableData.forEach((row, index) => {
        if (row.goodsId === goodsId) {
          row.goodsProductParams = datas
        }
      })
    },
    paramsAssign () {
      this.form.firstSpecialGoodsParams = this.tableData.map((item, i) => {
        let param = {
          goodsId: item.goodsId,
          goodsName: item.goodsName,
          discount: item.batchDiscount,
          reducePrice: item.batchReduce,
          goodsPrice: item.batchFinalPrice,
          goodsProductParams: item.goodsProductParams,
          tips: item.tips
        }
        if (item.id) {
          param.id = item.id
        }
        return param
      })
      this.form.startTime = this.form.timeInterval[0]
      this.form.endTime = this.form.timeInterval[1]
      return Object.assign({}, this.form)
    },
    paramsValid (params, suFn) {
      this.$refs.firstSpecialAddForm.validate((valid) => {
        if (valid) {
          // if (params.isForever === 0 && (params.startTime === '' || params.endTime === '')) {
          //   this.$message.warning(this.$t('firstSpecialAdd.validityPeriodTip'))
          //   return false
          // }
          let goods = params.firstSpecialGoodsParams
          if (goods.length === 0) {
            this.$message.warning(this.$t('firstSpecialAdd.selectEventTip'))
            return false
          }
          let noPriceGoods = goods.find((item, i) => {
            return !item.goodsPrice
          })
          if (noPriceGoods) {
            this.$message.warning(this.$t('firstSpecialAdd.setDiscountTip'))
            return false
          }
          // 验证特惠价是否超过原价
          let hasTips = goods.find(item => {
            return item.tips
          })
          if (hasTips) {
            this.$message.warning(this.$t('firstSpecialAdd.commodity') + hasTips.goodsName + hasTips.tips)
            return false
          }
          suFn(params)
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    addSubmit () {
      let _this = this
      let params = this.paramsAssign()
      console.log(params)
      传参校验
      this.paramsValid(params, function (params) {
        addFirstSpecial(params).then(res => {
          if (res.error === 0) {
            _this.$message.success(_this.$t('firstSpecialAdd.successSaved'))
            _this.$nextTick(function () {
              _this.$router.push({
                path: '/admin/home/main/firstSpecial/list',
                query: {
                  tabIndex: '0'
                }
              })
            })
          } else {
            console.error(res.message)
          }
        })
      })
    },
    updateSubmit () {
      let _this = this
      let params = this.paramsAssign()
      params.id = this.id
      console.log(params)
      // 传参校验
      _this.paramsValid(params, function (params) {
        updateFirstSpecial(params).then(res => {
          if (res.error === 0) {
            _this.$message.success(_this.$t('firstSpecialAdd.successUpdated'))
            _this.$nextTick(() => {
              _this.$router.push({
                path: '/admin/home/main/firstSpecial/list',
                query: {
                  tabIndex: '0'
                }
              })
            })
          } else {
            console.error(res.message)
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
  }
  .form_input {
    width: 185px;
  }
  .form_tip {
    display: inline-block;
    color: #999;
  }
  .clearfix:after {
    content: "";
    display: block;
    height: 0;
    clear: both;
  }
  .fl {
    float: left;
  }
  .fr {
    float: right;
  }
  .num_input {
    width: 70px;
  }
  .iconSpan {
    font-size: 20px;
    color: #5a8bff;
    cursor: pointer !important;
  }
  .footer {
    position: fixed;
    bottom: 0;
    display: flex;
    justify-content: center;
    width: calc(100% - 186px);
    padding: 10px;
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    .footer-btn {
      width: 105px;
      margin: 0 10px;
    }
  }
}
.first-special-add-page {
  margin-bottom: 50px;
  .first-special-add-content {
    .info-top {
      padding: 10px 15px;
      background: #f5f5f5;
      margin-bottom: 15px;
      h3 {
        font-size: 16px;
        color: #333;
        font-weight: bold;
        margin-bottom: 10px;
      }
      li {
        font-size: 13px;
        margin-bottom: 5px;
      }
    }
    .add_content {
      border: 1px solid #eee;
      .table_head {
        position: relative;
        height: 60px;
        line-height: 60px;
        .table_head_setting {
          position: absolute;
          top: 50%;
          transform: translateY(-50%);
          margin-bottom: 0;
        }
      }
    }
    .active_radio {
      line-height: 32px;
    }
    .upload_img {
      width: 70px;
      height: 70px;
      box-shadow: 0 0 0 #fff;
    }
    .custom_style {
      margin-left: 28px;
      label {
        width: 80px;
        float: left;
      }
      .share_img_group {
        width: 90px;
      }
      .share_img_radio {
        line-height: 32px;
      }
      .upload_wrap {
        margin-left: 78px;
        .sizeTips {
          margin-left: 30px;
          line-height: 70px;
        }
      }
    }
    .active_tool {
      padding: 20px;
      border-radius: 5px;
      background: #fff;
      box-shadow: 1px 1px 10px 5px #eee;
    }
  }
}
</style>
