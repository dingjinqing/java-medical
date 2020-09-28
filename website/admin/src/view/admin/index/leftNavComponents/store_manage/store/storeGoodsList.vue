<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="order-container">
        <div class="order-info">
          <p class="list-store-name">{{$t('storeGoodsList.storeName')}}：<span>{{storeName}}</span></p>
          <ul class="list-store-filters">
            <li>
              <el-select
                v-model="queryParams.catId"
                size="small"
                filterable
                @change="selectChangeHandle"
                style="width:170px;"
              >
                <el-option
                  :label="$t('storeGoodsList.goodsSort')"
                  :value="null"
                ></el-option>
                <el-option
                  v-for="item in catIds"
                  :key="item.catId"
                  :label="item.catName"
                  :value="item.catId"
                >
                  <span :style="'paddingLeft:' + item.level*20 + 'px'">{{item.catName}}</span>
                </el-option>
              </el-select>
            </li>
            <li>
              <el-select
                v-model="queryParams.isOnSale"
                size="small"
                @change="selectChangeHandle"
                style="width:170px;"
              >
                <el-option
                  :label="$t('storeGoodsList.shelfStatus')"
                  :value="null"
                ></el-option>
                <el-option
                  v-for="item in isOnSale"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </li>
            <li>
              <el-select
                v-model="queryParams.isSync"
                size="small"
                @change="selectChangeHandle"
                style="width:170px;"
              >
                <el-option
                  :label="$t('storeGoodsList.syncPos')"
                  :value="null"
                ></el-option>
                <el-option
                  v-for="item in isSync"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </li>
            <li>
              <el-input
                :placeholder="$t('storeGoodsList.searchProductsbarcode')"
                v-model="queryParams.keywords"
                size="small"
                style="width:170px;"
              >
                <i
                  slot="suffix"
                  class="el-input__icon el-icon-search"
                  @click="searchHandle"
                ></i>
              </el-input>
            </li>
            <div class="list-store-filters-right">
              <el-button
                type="primary"
                size="small"
                @click="updateGoodsHandle"
                :loading="loading"
              >{{loading?$t('storeGoodsList.updatingGoods'):$t('storeGoodsList.updateGoods')}}</el-button>
            </div>
          </ul>
        </div>
        <div class="order-content">
          <el-table
            ref="goodsTable"
            :data="goodsData"
            class="tableClass"
            border
            :max-height="remainingHeight"
            @selection-change="selectionChangeHandle"
            :header-cell-style="{
              'background-color':'#f5f5f5',
              'border':'none'
            }"
          >
            <el-table-column
              type="selection"
              width="60"
              align="center"
            >
            </el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.name')"
              prop="goodsName"
              width="300"
              align="left"
            >
              <template slot-scope="{row}">
                <div>
                  <img
                    style="width: 70px;height: 70px;float: left;"
                    :src="row.goodsImg"
                    alt="no picture"
                  >
                  <div style="margin-left:70px; padding-left:5px; line-height: 1; vertical-align: top;">
                    {{row.goodsName}}
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.specification')"
              prop="prdDesc"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.classification')"
              prop="catName"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.price')"
              align="center"
            >
              <template slot-scope="{row}">
                <span v-if="row.isSync === 1">{{row.productPrice}}</span>
                <span v-else>{{row.prdPrice}}</span>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.inStock')"
              align="center"
            >
              <template slot-scope="{row}">
                <span v-if="row.isSync === 1">{{row.productNumber}}</span>
                <span v-else>{{row.prdNumber}}</span>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.merchantCoding')"
              prop="prdSn"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.barcode')"
              prop="prdCodes"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('storeGoodsList.operate')"
              prop="goodsName"
              width=""
              align="center"
            >
              <template slot-scope="{row}">
                <el-tooltip
                  v-if="row.isOnSale === 0"
                  :content="$t('storeGoodsList.shelf')"
                  placement="top"
                >
                  <!-- <span
                    class="iconSpan"
                    @click=shelfGoodsHandle(row.prdId)
                  >{{$t('storeGoodsList.shelf')}}</span> -->
                  <span
                    class="el-icon-top iconSpan"
                    style="font-size:20px;"
                    @click=shelfGoodsHandle(row.prdId)
                  ></span>
                </el-tooltip>
                <el-tooltip
                  v-else
                  :content="$t('storeGoodsList.obtain')"
                  placement="top"
                >
                  <!-- <span
                    class="iconSpan"
                    @click=obtainGoodsHandle(row.prdId)
                  >{{$t('storeGoodsList.obtain')}}</span> -->
                  <span
                    class="el-icon-bottom iconSpan"
                    style="font-size: 20px;"
                    @click=obtainGoodsHandle(row.prdId)
                  ></span>
                </el-tooltip>
              </template>
            </el-table-column>
            <div slot="empty">
              <div class="noData">
                <img :src="noImg">
              </div>
              <span>{{$t('selectLinks.noDataAvailable')}}</span>
            </div>
          </el-table>
          <div class="table-page">
            <div class="table-page-left">
              <el-checkbox
                v-model="isSelectAll"
                @change="selectAllChange"
              >{{$t('imgsSpace.allCheckedText')}}</el-checkbox>
              <el-button
                size="small"
                @click="shelfGoodsHandle()"
              >{{$t('storeGoodsList.shelf')}}</el-button>
              <el-button
                size="small"
                @click="obtainGoodsHandle()"
              >{{$t('storeGoodsList.obtain')}}</el-button>
            </div>
            <div class="table-page-right">
              <pagination
                :page-params.sync="pageParams"
                @pagination="initDataList"
              ></pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-dialog
      :title="$t('storeGoodsList.prompt')"
      :visible.sync="goodsUpdateVisible"
      width="500px"
    >
      <div class="dialog-content">
        <ul>
          <li>
            <el-radio
              v-model="goodsUpdateType"
              :label="0"
            >{{$t('storeGoodsList.incrementalUp')}}</el-radio>
            <el-date-picker
              v-model="datepicker"
              type="datetimerange"
              class="update-date-picker"
              size="small"
              :range-separator="$t(`dateTimePicker.to`)"
              :start-placeholder="$t(`dateTimePicker.pleaseSelectTheStartTime`)"
              :end-placeholder="$t(`dateTimePicker.pleaseSelectTheEndTime`)"
              :default-time="['00:00:00','23:59:59']"
            ></el-date-picker>
          </li>
          <li>
            <el-radio
              v-model="goodsUpdateType"
              :label="1"
            >{{$t('storeGoodsList.allUp')}}</el-radio>
          </li>
        </ul>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="confirmUpdateHandle"
        >{{$t('storeGoodsList.deter')}}</el-button>
        <el-button
          size="small"
          @click="goodsUpdateVisible = false"
        >{{$t('storeGoodsList.cancel')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getGoodsList, getCateList, updateGoods, shelfGoods, obtainGoods, judgeCompile } from '@/api/admin/storeManage/storeGoods'
import pagination from '@/components/admin/pagination/pagination'
import '@/util/date.js'
export default {
  components: { pagination },
  data () {
    let that = this
    return {
      loading: false,
      remainingHeight: '316px',
      storeName: '',
      queryParams: {
        storeId: '', // 商家id
        catId: null, // 平台分类
        isOnSale: null, // 是否上架
        isSync: null, // 是否同步POS
        keywords: null // 商品名或条码
      },
      catIds: [],
      isOnSale: [
        { id: 1, name: that.$t('storeGoodsList.onShelves') },
        { id: 0, name: that.$t('storeGoodsList.offShelf') }
      ],
      isSync: [
        { id: 1, name: that.$t('storeGoodsList.yes') },
        { id: 0, name: that.$t('storeGoodsList.no') }
      ],
      selected: [],
      tableData: [],
      noImg: this.$imageHost + '/image/admin/no_data.png',
      pageParams: {},
      isSelectAll: false, // 标识是否全选
      goodsUpdateVisible: false, // 商品更新弹窗
      datepicker: [],
      goodsUpdateType: 0, // 商品更新方式
      judgetimer: '' // 循环判断是否更新完成
    }
  },
  computed: {
    goodsData: function () {
      return this.tableData
    }
  },
  created () {
    this.queryParams.storeId = this.$route.query.id
    this.storeName = this.$route.query.name
    this.langDefault()
  },
  mounted () {
    this.langDefault()
    this.initCategory()
    this.initDataList()
    // 计算table所占高度
    if (document.documentElement) {
      this.remainingHeight = document.documentElement.clientHeight - 410
    }
  },
  methods: {
    searchHandle () {
      console.log('search...' + this.queryParams)
      this.initDataList()
    },
    updateGoodsHandle () {
      this.goodsUpdateVisible = true
    },
    // 判断门店商品是否更新完成
    judgeUpdate () {
      let that = this
      return new Promise((resolve, reject) => {
        judgeCompile({
          storeId: this.queryParams.storeId
        }).then(res => {
          if (res.error === 0) {
            if (that.judgetimer) {
              that.loading = false
              clearInterval(that.judgetimer)
              that.$message.success(that.$t('storeGoodsList.upComplete'))
              that.initDataList()
            }
            resolve(true)
          } else {
            resolve(false)
          }
        }).catch(err => {
          console.error(err)
          if (that.judgetimer) {
            that.loading = false
            clearInterval(that.judgetimer)
          }
          resolve(false)
        })
      })
    },
    // 更新商品
    updateGoodsRequest (params) {
      updateGoods(params).then(res => {
        if (res.error === 0) {
          this.$message.warning(this.$t('storeGoodsList.updating'))
          this.pollingJudge()
        }
      }).catch(err => {
        console.error(err)
      })
    },
    pollingJudge () {
      let that = this
      this.loading = true
      that.judgetimer = setInterval(() => {
        this.judgeUpdate()
      }, 2000)
    },
    // 更新商品确认
    async confirmUpdateHandle () {
      let params = {
        storeId: this.queryParams.storeId,
        updateTye: this.goodsUpdateType
      }
      if (this.goodsUpdateType === 0) {
        params.updateBegin = this.datepicker[0] ? this.datepicker[0].format('yyyy-MM-dd hh:mm:ss') : ''
        params.updateEnd = this.datepicker[1] ? this.datepicker[1].format('yyyy-MM-dd hh:mm:ss') : ''
      }
      if (this.goodsUpdateType === 0 && (!params.updateBegin || !params.updateEnd)) {
        this.$message.warning(this.$t('storeGoodsList.psTime'))
        return false
      }
      let result = await this.judgeUpdate()
      if (result) {
        this.updateGoodsRequest(params)
      } else {
        this.$message.warning(this.$t('storeGoodsList.lastUp'))
        this.pollingJudge()
      }
      this.goodsUpdateVisible = false
    },
    selectAllChange (val) {
      console.log(val)
      this.$refs.goodsTable.toggleAllSelection()
    },
    selectionChangeHandle (rows) {
      if (rows && rows.length === this.tableData.length) {
        this.isSelectAll = true
      } else {
        this.isSelectAll = false
      }
      this.selected = rows
    },
    shelfGoodsHandle (data) {
      let params = {
        storeId: this.queryParams.storeId,
        prdId: []
      }
      if (!data) {
        data = this.selected.map(item => item.prdId)
      }
      if (Array.isArray(data)) {
        params.prdId = data
      } else {
        params.prdId.push(data)
      }
      shelfGoods(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('storeGoodsList.success'))
          this.initDataList()
        }
      })
    },
    obtainGoodsHandle (data) {
      let params = {
        storeId: this.queryParams.storeId,
        prdId: []
      }
      if (!data) {
        data = this.selected.map(item => item.prdId)
      }
      if (Array.isArray(data)) {
        params.prdId = data
      } else {
        params.prdId.push(data)
      }
      obtainGoods(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('storeGoodsList.success'))
          this.initDataList()
        }
      }).catch(err => {
        console.error(err)
      })
    },
    initCategory () {
      getCateList().then(res => {
        if (res.error === 0) {
          this.catIds = this.formatCateDatas(res.content)
        }
      })
    },
    formatCateDatas (datas) {
      const treeDatas = this.disposeGoodsSortAndCatData(datas, 'catId')
      return treeDatas
    },
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getGoodsList(params).then(res => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      }).catch(err => {
        console.error(err)
      })
    },
    selectChangeHandle () {
      this.initDataList()
    }
  }
}
</script>

<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.order-container {
  padding: 10px;
}
.order-info {
  background: #fff;
  padding: 15px;
}
.order-content {
  margin-top: 10px;
  padding: 15px;
  background: #fff;
}
.list-store-name {
  margin-left: 10px;
  margin-bottom: 4px;
  padding-top: 10px;
  font-size: 14px;
}
.list-store-filters {
  overflow: hidden;
  margin-top: 10px;
  margin-left: 10px;
  line-height: 1.428571429;
}
.list-store-filters li {
  float: left;
  padding-right: 18px;
}
.list-store-filters .el-input__inner {
  color: #555;
}
.list-store-filters-right {
  float: right;
}
.goodsTypeSpanWrap {
  border: 1px solid #ff3f3f;
  color: #ff3f3f;
  border-radius: 3px;
  padding: 2px;
  margin-right: 2px;
}
.goodsLabelSpanWrap {
  border: 1px solid #cccccc;
  color: #666;
  border-radius: 3px;
  padding: 2px;
  margin-right: 2px;
  display: inline-block;
}
.iconSpan {
  color: #5a8bff;
  cursor: pointer !important;
}
.noData {
  width: 30px;
  height: 33px;
  margin: 25px auto auto auto;
}
.noData span {
  margin: 10px;
}
.table-page {
  height: 100px;
  overflow: hidden;
  padding: 10px;
  background: #fff;
}
.table-page-left {
  line-height: 52px;
  float: left;
}
.table-page-right {
  float: right;
}
.update-date-picker {
  margin: 10px 24px 10px;
}
/deep/ .el-dialog__body {
  padding: 10px 20px;
}
</style>
