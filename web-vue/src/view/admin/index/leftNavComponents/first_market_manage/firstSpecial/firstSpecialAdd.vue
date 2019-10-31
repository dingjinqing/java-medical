<template>
  <div class="content first-special-add-page">
    <div class="main">
      <div class="nav_list">
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            label="全部首单特惠活动"
            name="0"
          ></el-tab-pane>
          <el-tab-pane
            label="进行中"
            name="1"
          ></el-tab-pane>
          <el-tab-pane
            label="未开始"
            name="2"
          ></el-tab-pane>
          <el-tab-pane
            label="已过期"
            name="3"
          ></el-tab-pane>
          <el-tab-pane
            label="已停用"
            name="4"
          ></el-tab-pane>
          <el-tab-pane
            label="添加首单特惠活动"
            name="5"
          ></el-tab-pane>
        </el-tabs>
      </div>
      <div class="first-special-add-content">
        <div class="info-top">
          <h3>说明</h3>
          <ol>
            <li>1.首单特惠价格仅“未在店铺内支付购买过任何商品”的用户可见，老用户查看首单特惠商品时将显示商品的当前价格。</li>
            <li>2.首单特惠活动建议与“开屏有礼-自定义”活动配合开展</li>
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
            label="活动名称："
            prop="name"
          >
            <el-input
              class="form_input"
              v-model="form.name"
            ></el-input>
            <p class="form_tip">只作为商家记录使用，用户不会看到这个名称</p>
          </el-form-item>
          <el-form-item
            class="clearfix"
            label="有效期："
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
                >固定时间</el-radio>
                <el-radio
                  :label="1"
                  style="line-height:32px;"
                >永久有效</el-radio>
              </el-radio-group>
            </div>
            <div class="fl">
              <el-date-picker
                v-model="form.startTime"
                type="datetime"
                class="form_input"
                placeholder="选择日期时间"
                :disabled="!!form.isForever"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
              至
              <el-date-picker
                v-model="form.endTime"
                type="datetime"
                class="form_input"
                placeholder="选择日期时间"
                :disabled="!!form.isForever"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item
            label="活动优先级："
            prop="first"
          >
            <el-input-number v-model="form.first"></el-input-number>
            <p class="form_tip">用于区分不同首单特惠活动的优先级，请填写正整数，数值越大优先级越高</p>
          </el-form-item>
          <el-form-item
            label="限购数量："
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
              >不限制</el-radio>
              <el-radio
                :label="1"
                style="line-height:32px;"
              >
                限制数量
                <el-input-number
                  v-model="form.limitAmount"
                  :disabled="!limit"
                  style="margin-left: 10px;"
                ></el-input-number>
              </el-radio>
            </el-radio-group>
            <el-checkbox
              v-model="form.limitFlag"
              style="margin-left: 30px;"
              :true-label="1"
              :false-label="0"
            >超出限购数量后，买家不可继续添加购买该商品</el-checkbox>
          </el-form-item>
          <el-form-item
            label="活动商品："
            required
          >
            <el-button @click="selectGoodsHandle">+选择商品</el-button>
            <p class="form_tip">最多选择100个商品</p>
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
                label="设置折扣："
                required
              >
                <el-radio-group
                  class="setting_group"
                  v-model="discountType"
                >
                  <el-radio label="0">
                    <span>
                      批量打<el-input
                        class="num_input"
                        v-model="form.batchDiscount"
                        min="0"
                        max="10"
                        @focus="inputFocus(0)"
                      ></el-input>折
                    </span>
                  </el-radio>
                  <el-radio label="1">
                    <span>
                      批量减价<el-input
                        class="num_input"
                        v-model="form.batchReduce"
                        @focus="inputFocus(1)"
                      ></el-input>元
                    </span>
                  </el-radio>
                  <el-radio label="2">
                    <span>
                      批量首单价<el-input
                        class="num_input"
                        v-model="form.batchFinalPrice"
                        @focus="inputFocus(2)"
                      ></el-input>元
                    </span>
                  </el-radio>
                </el-radio-group>
                <el-button
                  type="primary"
                  style="margin-left:20px;"
                  @click="volumeDiscountHandle"
                >确定</el-button>
                <el-button @click="resetTableData">取消</el-button>
              </el-form-item>
              <div
                class="fr"
                style="padding-right:10px;"
              >
                <el-button
                  type="text"
                  @click="deleteSelectGoods"
                >批量删除</el-button>
                <el-button
                  type="text"
                  @click="roundingPrice"
                >批量价格取整</el-button>
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
                label="商品名称"
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
                label="原价"
                prop="shopPrice"
              ></el-table-column>
              <el-table-column
                label="库存"
                prop="goodsNumber"
              ></el-table-column>
              <el-table-column label="折扣">
                <template slot-scope="{row}">
                  <el-input
                    style="width:50px;"
                    size="small"
                    v-model="row.batchDiscount"
                    @change="tableBatchDiscountChange(row)"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column label="减价">
                <template slot-scope="{row}">
                  <el-input
                    style="width:80px;"
                    size="small"
                    v-model="row.batchReduce"
                    @change="tableBatchReduceChange(row)"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                label="首单价"
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
                  >{{row.goodsProductParams?row.goodsProductParams.length:0}}个规格降价</el-button>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                align="center"
              >
                <template slot-scope="{row}">
                  <div style="align: center;">
                    <span
                      class="iconSpan"
                      style="font-size:14px;"
                      @click.stop="deleteGood(row.goodsId)"
                    >删除</span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-button
            type="text"
            @click="showmore = !showmore"
          >
            <span v-if="showmore">收起更多配置<img :src="$imageHost + '/image/admin/info_up.png'"></span>
            <span v-else>展开更多配置<img :src="$imageHost + '/image/admin/info_down.png'"></span>
          </el-button>
          <div
            class="more-setting"
            v-if="showmore"
          >
            <el-form-item
              label="活动分享："
              required
            >
              <el-radio-group v-model="form.shareConfig.share_action">
                <div>
                  <el-radio
                    :label="1"
                    class="active_radio"
                  >
                    <el-tooltip
                      style="margin-left:40px;"
                      placement="right"
                      effect="light"
                    >
                      <div
                        slot="content"
                        class="active_tool"
                      >
                        <el-image
                          style="width:240px;"
                          :src="$imageHost +'/image/admin/share/first_share1.jpg'"
                        ></el-image>
                      </div>
                      <span>默认样式</span>
                    </el-tooltip>
                    <el-tooltip
                      style="margin-left:40px;"
                      placement="right"
                      effect="light"
                    >
                      <div
                        slot="content"
                        class="active_tool"
                      >
                        <el-image
                          style="width:240px;"
                          :src="$imageHost +'/image/admin/share/first_share2.jpg'"
                        ></el-image>
                      </div>
                      <span>下载海报</span>
                    </el-tooltip>
                  </el-radio>
                </div>
                <div>
                  <el-radio
                    :label="2"
                    class="active_radio"
                  >
                    自定义样式
                  </el-radio>
                </div>
              </el-radio-group>
              <div
                class="custom_style"
                v-show="form.shareConfig.share_action === 2"
              >
                <div>
                  <label>文案：</label>
                  <el-input
                    class="form_input"
                    size="small"
                    v-model="form.shareConfig.share_doc"
                  ></el-input>
                </div>
                <div>
                  <label>分享图：</label>
                  <div>
                    <el-radio-group
                      class="share_img_group"
                      v-model="form.shareConfig.share_img_action"
                    >
                      <el-radio
                        class="share_img_radio"
                        :label="1"
                      >活动商品信息图</el-radio>
                      <el-radio
                        class="share_img_radio"
                        :label="2"
                      >自定义图片</el-radio>
                    </el-radio-group>
                    <div class="upload_wrap">
                      <div
                        class="upload_img"
                        @click="uploadImgHandle"
                      >
                        <el-image
                          style="width: 100%; height:100%;"
                          fit="contain"
                          :src="form.shareConfig.share_img"
                        ></el-image>
                      </div>
                      <p class="tips">建议尺寸800*800</p>
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
      >保存</el-button>
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
    return {
      id: '',
      isEditFlag: false, // 区分新增还是编辑
      activeName: '5', // 高亮tab
      form: {
        name: '',
        isForever: 0, // 是否永久有效
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
          share_action: 1,
          share_doc: '',
          share_img_action: 1,
          share_img: this.$imageHost + '/image/admin/btn_add.png'
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
        name: { required: true, message: '请填写活动名称', trigger: 'blur' },
        isForever: { required: true, message: '请选择有效期' },
        first: { required: true, message: '请填写活动优先级' }
      }
    }
  },
  created () {
    if (this.$route.query.id) {
      this.isEditFlag = true
      this.initEditData()
    }
  },
  methods: {
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
          _this.tableData = datas.firstSpecialGoods.map(function (item, i) {
            item.firstSpecialProduct.forEach(function (d) {
              console.log(d)
            })
            let rowData = Object.assign({
              goodsId: item.goodsId,
              batchDiscount: item.discount,
              batchReduce: item.reducePrice,
              batchFinalPrice: item.goodsPrice,
              goodsProductParams: item.firstSpecialProduct,
              unit: item.goods
            }, item.goodsView)
            return rowData
          })
          if (datas.limitAmount > 0) {
            _this.limit = 1
          }
          _this.form.shareConfig = Object.assign({}, datas.shopShareConfig)
        }
      })
    },
    // 点击tab框
    handleClick (tab) {
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
                debugger
                spec.productId = spec.prdId
                spec.originalPrice = spec.prdPrice
              })
            }
          })
          console.log(datas)
          this.tableData = datas
        }
      })
    },
    deleteGood (goodsId) {
      this.$confirm('确定要删除吗？', '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
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
        this.$message.warning('请选择商品后再进行操作')
        return false
      }
      if (this.discountType === '') {
        this.$message.warning('请选择折扣方式')
        return false
      }
      switch (this.discountType) {
        case '0':
          if (this.form.batchDiscount === '' || this.form.batchDiscount < 0 || this.form.batchDiscount > 10) {
            this.$message.warning('折扣只能输入0-10之间')
            return false
          }
          this.$set(this.form, 'batchReduce', '')
          this.$set(this.form, 'batchFinalPrice', '')
          break
        case '1':
          if (this.form.batchReduce === '') {
            this.$message.warning('设置值不可为空')
            return false
          }
          this.$set(this.form, 'batchDiscount', '')
          this.$set(this.form, 'batchFinalPrice', '')
          break
        case '2':
          if (this.form.batchReduce === '') {
            this.$message.warning('设置值不可为空')
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
          this.$message.error(item.goodsName + '的原价已为0，不能再打折')
          return false
        }
        if (this.discountType === '0') {
          item.batchDiscount = this.form.batchDiscount
          item.batchFinalPrice = (item.batchDiscount / 10 * price).toFixed(3)
          item.batchReduce = price - item.batchFinalPrice
        } else if (this.discountType === '1') {
          item.batchReduce = this.form.batchReduce
          item.batchFinalPrice = Number(price - item.batchReduce)
          item.batchDiscount = (item.batchFinalPrice / price).toFixed(3) * 10
        } else if (this.discountType === '2') {
          item.batchFinalPrice = this.form.batchFinalPrice
          item.batchDiscount = (item.batchFinalPrice / price).toFixed(3) * 10
          item.batchReduce = price - item.batchFinalPrice
        }
        // 验证计算值安全性
        console.log(item)
        if (item.batchFinalPrice < 0) {
          item.tips = '降价后首单价不得小于0'
        } else if (item.batchFinalPrice > price) {
          item.tips = '降价后金额需小于原价'
        }
        if (item.goodsProductParams && item.goodsProductParams.length) {
          item.goodsProductParams.map(item2 => {
            item2.productId = item2.prdId
            // item2.originalPrice = item2.prdPrice
            let originalPrice = item2.originalPrice
            let prdPrice = (originalPrice * (parseFloat(item.batchDiscount / 10))).toFixed(2)
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
    tableBatchDiscountChange (row) {
      console.log('BatchDiscountChange')
      let price = Number(row.shopPrice)
      let batchDiscount = Number(row.batchDiscount)
      if (batchDiscount < 0 || batchDiscount > 10) {
        this.$message.warning('折扣只能输入0-10之间')
        this.$set(row, 'batchDiscount', '')
        return false
      }
      let batchFinalPrice = (batchDiscount / 10 * price).toFixed(3)
      let batchReduce = price - batchFinalPrice
      this.$set(row, 'batchFinalPrice', batchFinalPrice)
      this.$set(row, 'batchReduce', batchReduce)
      this.watchbatchFinalPrice(price, batchFinalPrice, row)
    },
    tableBatchReduceChange (row) {
      console.log('BatchReduceChange')
      let price = Number(row.shopPrice)
      let batchReduce = Number(row.batchReduce)
      let batchFinalPrice = price - batchReduce
      let batchDiscount = (batchFinalPrice / price).toFixed(3)
      this.$set(row, 'batchFinalPrice', batchFinalPrice)
      if (!Number.isFinite(batchDiscount)) {
        batchDiscount = 0
      }
      this.$set(row, 'batchDiscount', batchDiscount)
      this.watchbatchFinalPrice(price, batchFinalPrice, row)
    },
    tableBatchFinalPriceChange (row) {
      console.log('BatchFinalPriceChange')
      let price = Number(row.shopPrice)
      let batchFinalPrice = Number(row.batchFinalPrice)
      let batchReduce = price - batchFinalPrice
      let batchDiscount = (batchFinalPrice / price).toFixed(3) * 10
      this.$set(row, 'batchReduce', batchReduce)
      if (!Number.isFinite(batchDiscount)) {
        batchDiscount = 0
      }
      this.$set(row, 'batchDiscount', batchDiscount)
      this.watchbatchFinalPrice(price, batchFinalPrice, row)
    },
    watchbatchFinalPrice (price, batchFinalPrice, row) {
      let tips
      if (batchFinalPrice < 0) {
        tips = '降价后首单价不得小于0'
      } else if (batchFinalPrice > price) {
        tips = '降价后金额需小于原价'
      }
      this.$set(row, 'tips', tips)
      if (row.goodsProductParams && row.goodsProductParams.length) {
        row.goodsProductParams.map(item2 => {
          item2.productId = item2.prdId
          let originalPrice = item2.originalPrice
          let prdPrice = (originalPrice * (parseFloat(row.batchDiscount / 10))).toFixed(3)
          item2.prdPrice = prdPrice
          this.$set(item2, 'prdPrice', prdPrice)
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
        this.$message.warning('请选择商品后再进行操作')
        return false
      }
      let selects = this.selectGoods
      let that = this
      this.$confirm('确定要删除吗？', '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
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
        that.$message.warning('请选择商品后再进行操作')
        return false
      }
      that.$confirm('确认要取整吗?', '提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (that.selectGoods.length > 0) {
          that.selectGoods.forEach(function (item, i) {
            let index = that.tableData.findIndex((row, j) => row.goodsId === item.goodsId)
            let batchFinalPrice = Math.ceil(that.tableData[index].batchFinalPrice)
            that.$set(that.tableData[index], 'batchFinalPrice', batchFinalPrice)
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
      this.$set(this.form.shareConfig, 'share_img', image.imgUrl)
    },
    getProductdata (datas) {
      console.log(datas)
    },
    paramsAssign () {
      this.form.firstSpecialGoodsParams = this.tableData.map((item, i) => {
        return {
          goodsId: item.goodsId,
          goodsName: item.goodsName,
          discount: item.batchDiscount,
          reducePrice: item.batchReduce,
          goodsPrice: item.batchFinalPrice,
          goodsProductParams: item.goodsProductParams,
          tips: item.tips
        }
      })
      return Object.assign({}, this.form)
    },
    paramsValid (params, suFn) {
      this.$refs.firstSpecialAddForm.validate((valid) => {
        if (valid) {
          if (params.isForever === 0 && (params.startTime === '' || params.endTime === '')) {
            this.$message.warning('请填写有效期')
            return false
          }
          let goods = params.firstSpecialGoodsParams
          if (goods.length === 0) {
            this.$message.warning('请选择活动商品')
            return false
          }
          let noPriceGoods = goods.find((item, i) => {
            return !item.goodsPrice
          })
          if (noPriceGoods) {
            this.$message.warning('请设置折扣价')
            return false
          }
          // 验证特惠价是否超过原价
          let hasTips = goods.find(item => {
            return item.tips
          })
          if (hasTips) {
            this.$message.warning('商品' + hasTips.goodsName + hasTips.tips)
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
      // 传参校验
      this.paramsValid(params, function (params) {
        addFirstSpecial(params).then(res => {
          if (res.error === 0) {
            _this.$message.success('保存成功')
            _this.$router.push({
              path: '/admin/home/main/firstSpecial/list',
              query: {
                tabIndex: 0
              }
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
            _this.$message.success('更新成功')
            _this.$router.push({
              path: '/admin/home/main/firstSpecial/list',
              query: {
                tabIndex: 0
              }
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
    width: 50px;
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
