<template>
  <el-dialog
    :title="$t('allGoods.allGoodsData.tip')"
    :visible.sync="showNodes"
    custom-class="custom"
    v-loading="loading"
    width="700"
    center
  >
    <el-alert
      type="warning"
      :title="$t('order.orderExportConfirmTip_1') + totalRows + $t('order.orderExportConfirmTip_2')"
      show-icon
    ></el-alert>
    <div style="margin-top: 10px;">
      {{$t('order.filterCondition')}}
    </div>
    <div
      v-if="Object.keys(paramString).length!=0"
      style="margin-top: 10px;"
    >
      <div
        v-for="(item,key,index) in paramString"
        :key="index"
      >
        <div v-if="ok(key,item)">
          <div>{{$t('allGoods.allGoodsHeaderInputLabel.'+key)}}:{{item}}</div>
        </div>
      </div>
    </div>
    <div
      v-else
      style="margin-top: 10px;"
    >
      {{$t('allGoods.allGoodsData.no')}}
    </div>
    <div class="goods-export-block">
      <div style="margin-bottom: 10px;">{{$t('order.orderExportLimitTip')}}</div>
      <el-input-number
        v-model="exportRowStart"
        placeholder=""
        :min="1"
        :max="exportRowEnd"
        :precision="0"
        size="small"
        controls-position="right"
        style="width: 150px;"
      ></el-input-number>
      {{$t('orderCommon.to')}}
      <el-input-number
        v-model="exportRowEnd"
        placeholder=""
        size="small"
        controls-position="right"
        :min="exportRowStart"
        :max="param.exportRowEnd"
        :precision="0"
        style="width: 150px;"
      ></el-input-number>
    </div>
    <div class="goods-export-block">
      <div style="margin-bottom: 10px;">导出数据</div>
      <div>
        <table
          border="1"
          class="export-table-items"
        >
          <tbody>
            <tr>
              <td class="table-title">
                <el-checkbox
                  :indeterminate="baseisIndeterminate"
                  v-model="baseCheck"
                  @change="baseCheckAllChange"
                >{{ $t('allGoods.exportDialog.basicInformation') }}:</el-checkbox>
              </td>
              <td class="table-list">
                <el-checkbox-group
                  v-model="baseChecked"
                  @change="baseCheckGroupChange"
                >
                  <ul class="check-list">
                    <li style="color:red;">
                      <el-checkbox label="createTime">{{ $t('allGoods.exportDialog.createTime') }}</el-checkbox>
                    </li>
                    <!-- <li>
                      <el-checkbox label="catName">{{ $t('allGoods.exportDialog.platformClass') }}</el-checkbox>
                    </li> -->
                    <li>
                      <el-checkbox label="sortNameChild">{{ $t('allGoods.exportDialog.sortNameFirst') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="sortNameParent">{{ $t('allGoods.exportDialog.sortNameSecond') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="brandName">{{ $t('allGoods.exportDialog.brand') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="goodsSn">{{ $t('allGoods.exportDialog.goodsSn') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="goodsName">{{ $t('allGoods.exportDialog.goodsName') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="prdDesc">{{ $t('allGoods.exportDialog.prdDesc') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="prdSn">{{ $t('allGoods.exportDialog.prdSn') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="goodsAd">{{ $t('allGoods.exportDialog.goodsAd') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="prdNumber">{{ $t('allGoods.exportDialog.prdNumber') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="prdCostPrice">{{ $t('allGoods.exportDialog.prdCostPrice') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="marketPrice">{{ $t('allGoods.exportDialog.marketPrice') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="shopPrice">{{ $t('allGoods.exportDialog.shopPrice') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="isOnSale">{{ $t('allGoods.exportDialog.isOnSale') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="limitBuyNum">{{ $t('allGoods.exportDialog.limitBuyNum') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="goodsWeight">{{ $t('allGoods.exportDialog.weight') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="unit">{{ $t('allGoods.exportDialog.unit') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="goodsImg">{{ $t('allGoods.exportDialog.goodsImg') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="imgUrl">{{ $t('allGoods.exportDialog.detailImg') }}</el-checkbox>
                    </li>
                    <li>
                      <el-checkbox label="prdCodes">{{ $t('allGoods.exportDialog.barcode') }}</el-checkbox>
                    </li>
                  </ul>
                </el-checkbox-group>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        size="small"
        @click="showNodes = false"
      >{{$t('orderCommon.cancel')}}</el-button>
      <el-button
        type="primary"
        size="small"
        @click="confirm"
      >{{$t('orderCommon.ok')}}</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { goodsExport, getExportTotalRows } from '@/api/admin/goodsManage/allGoods/allGoods.js'
export default {
  data () {
    return {
      totalRows: 0,
      showNodes: false,
      loading: false,
      orderStatusMap: new Map(this.$t('order.orderStatusList')),
      exportRowStart: 1,
      exportRowEnd: 5000,
      baseOptions: [
        'createTime',
        'sortNameParent',
        'sortNameChild',
        'brandName',
        'goodsSn',
        'goodsName',
        'prdDesc',
        'prdSn',
        'goodsAd',
        'prdNumber',
        'prdCostPrice',
        'marketPrice',
        'shopPrice',
        'isOnSale',
        'limitBuyNum',
        'goodsWeight',
        'unit',
        'goodsImg',
        'imgUrl',
        'prdCodes'
      ],
      baseChecked: [],
      baseisIndeterminate: false,
      baseCheck: false
    }
  },
  props: {
    show: Boolean,
    param: Object,
    paramString: Object // 用于展示已选择条件
  },
  watch: {
    showNodes (newval) {
      this.closeDialog()
    },
    show (newVal) {
      if (newVal === true) {
        this.showNodes = true
        Object.keys(this.paramString).forEach((item, index) => {
          if (!this.paramString[item]) {
            delete this.paramString[item]
          }
        })
        this.initData()
      }
    },
    baseChecked: {
      handler: function (val, oldVal) {
        if (val === null || val === undefined) {
          val = []
        }
        let len = val.length
        let allLen = this.baseOptions.length
        if (len >= allLen) {
          this.baseCheck = true
          this.baseisIndeterminate = false
        } else {
          if (len > 0 && len < allLen) {
            this.baseisIndeterminate = true
          } else {
            this.baseisIndeterminate = false
          }
          this.baseCheck = false
        }
      },
      immediate: true
    }
  },
  methods: {
    initData () {
      let that = this
      // this.param.exportRowStart = this.exportRowStart
      // this.param.exportRowEnd = this.exportRowEnd
      getExportTotalRows(this.param).then(res => {
        if (res.error === 0) {
          this.totalRows = res.content.rows
          if (this.totalRows < 5000) {
            this.exportRowEnd = this.totalRows
            this.param.exportRowEnd = this.totalRows
          }
          if (!res.content.columns) {
            this.baseChecked = this.baseOptions
          } else {
            this.baseChecked = res.content.columns
          }
        } else {
          that.$message.error(res.message)
        }
      }).catch((err) => {
        console.error(err)
      })
    },
    closeDialog () {
      this.$emit('update:show', false)
    },
    confirm () {
      this.loading = true
      let params = Object.assign({}, this.param, {
        columns: this.baseChecked,
        exportRowStart: this.exportRowStart,
        exportRowEnd: this.exportRowEnd
      })
      goodsExport(params).then(res => {
        this.showNodes = false
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      })
    },
    ok (key, item) {
      if (Array.isArray(item)) {
        if (item.length !== 0) return true
      } else {
        if (key === 'currentPage' || key === 'pageRows' || key === 'exportRowStart' || key === 'exportRowEnd') {
          return false
        }
        if (item) return true
      }
      return false
    },
    // 基础信息选中改变
    baseCheckAllChange (val) {
      this.baseChecked = val ? this.baseOptions : []
      this.baseisIndeterminate = false
    },
    baseCheckGroupChange (val) {
      let checkedCount = val.length
      let baseOptionsCount = this.baseOptions.length
      this.baseCheck = checkedCount === baseOptionsCount
      this.baseisIndeterminate = checkedCount > 0 && checkedCount < baseOptionsCount
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .custom {
  .el-dialog__header {
    background: #f3f3f3;
    padding-top: 10px;
    .el-dialog__title {
      font-size: 14px;
    }
    .el-dialog__headerbtn {
      top: 10px;
    }
  }
  .el-checkbox-button.is-disabled .el-checkbox-button__inner {
    background-color: #f5f7fa;
  }
}
.goods-export-block {
  margin-top: 10px;
  border-top: 1px dashed #606266;
  padding-top: 10px;
}
.export-table-items {
  td {
    border: 1px solid #ccc;
    padding: 8px 10px;
  }
  .table-title {
    width: 25%;
    vertical-align: middle;
  }
  .check-list {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    li {
      width: 150px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      line-height: 20px;
      cursor: pointer;
      .el-checkbox {
        margin-right: 10px;
      }
    }
  }
}
</style>
