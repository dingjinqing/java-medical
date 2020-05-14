<template>
  <div class="container">
    <div class="top">
      <el-form
        size="small"
        :inline="true"
        label-suffix="："
        label-width="110px"
      >
        <el-form-item :label="$t('fullCuti18n.orderNum')">
          <el-input
            v-model="queryParams.orderSn"
            class="form-input"
            :placeholder="$t('fullCuti18n.peOrN')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('fullCuti18n.productInfor')">
          <el-input
            v-model="queryParams.goodsName"
            class="form-input"
            :placeholder="$t('fullCuti18n.peProductName')"
          >
          </el-input>
        </el-form-item>
        <el-form-item :label="$t('fullCuti18n.order')">
          <el-input
            v-model="queryParams.userInfo"
            class="form-input"
            :placeholder="$t('fullCuti18n.peNickname')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('fullCuti18n.orderStatus')">
          <el-select
            v-model="queryParams.orderStatus"
            class="form-input"
            :multiple="true"
          >
            <el-option
              v-for="(item, index) in $t('order.orderStatusList')"
              :key="index"
              :label="item[1]"
              :value="item[0]"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="initDataList"
          >{{$t('fullCuti18n.inquire')}}</el-button>
          <el-button @click="exportData">{{$t('fullCuti18n.dataOutput')}}</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="content">
      <el-table
        :data="tableData"
        header-row-class-name="tableClss"
        :cell-class-name="cellClass"
        border
      >
        <el-table-column
          align="center"
          :label="$t('fullCuti18n.orderNum')"
          prop="orderSn"
          width="180"
        ></el-table-column>
        <el-table-column
          align="center"
          :label="$t('fullCuti18n.commodity')"
          width="220"
          class="goods-wrap"
        >
          <template slot-scope="{row}">
            <div
              v-for="(item,index) in row.goods"
              :key="index"
              class="goods-info"
            >
              <el-image
                :src="item.goodsImg"
                fit="fill"
              ></el-image>
              <div class="goods-item">
                <p class="goods-name">{{item.goodsName}}</p>
                <p class="goods-desc">{{item.goodsAttr}}</p>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('fullCuti18n.unitPrice')"
        >
          <template slot-scope="{row}">
            <div
              v-for="(item,index) in row.goods"
              :key="index"
              class="goods-info"
            >
              <div class="goods-price">{{(item.goodsPrice||0).toFixed(2)}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('fullCuti18n.eventDiscountAmount')"
        >
          <template slot-scope="{row}">
            <div
              v-for="(item,index) in row.goods"
              :key="index"
              class="goods-info"
            >
              <div class="goods-price">{{(item.perDiscount||0).toFixed(2)}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('fullCuti18n.aactualPaid')"
        >
          <template slot-scope="{row}">
            <div
              v-for="(item,index) in row.goods"
              :key="index"
              class="goods-info"
            >
              <div class="goods-price">{{(item.discountedGoodsPrice||0).toFixed(2)}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('fullCuti18n.quantity')"
        >
          <template slot-scope="{row}">
            <div
              v-for="(item,index) in row.goods"
              :key="index"
              class="goods-info"
            >
              <div class="goods-price">{{(item.goodsNumber||0)}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('fullCuti18n.order')"
        >
          <template slot-scope="{row}">
            <div>
              <el-link
                href="javascript:void(0);"
                style="color: #5a8bff;"
                @click="goUserDetail(row, $event)"
              >
                <div>{{row.username}}</div>
                <div>{{row.mobile}}</div>
              </el-link>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('fullCuti18n.orderStatus')"
          prop="orderStatusName"
        ></el-table-column>
      </el-table>
      <!-- 翻页 -->
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
  </div>
</template>

<script>
import { fullcutOrderApi, fullcutOrderExportApi } from '@/api/admin/marketManage/fullDiscountFullCut.js'
import { download } from '@/util/excelUtil.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      id: '',
      tableData: [],
      pageParams: {},
      queryParams: {
        goodsName: '',
        orderSn: '',
        orderStatus: null,
        userInfo: ''
      }
    }
  },
  mounted () {
    if (this.$route.query.id) {
      this.id = this.$route.query.id
      this.initDataList()
    }
  },
  methods: {
    initDataList () {
      let that = this
      let params = Object.assign({ activityId: this.id }, this.queryParams, this.pageParams)
      fullcutOrderApi(params).then(res => {
        if (res.error === 0) {
          let content = res.content
          console.log(content)
          that.pageParams = content.page
          that.tableData = content.dataList
        }
      })
    },
    cellClass ({ column, columnIndex, row, rowIndex }) {
      if (columnIndex === 1 || columnIndex === 2 || columnIndex === 3 || columnIndex === 4) {
        return 'cell-noborder'
      }
    },
    goUserDetail (row, e) {
      e.preventDefault()
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: row.userId
        }
      })
    },
    // 数据导出
    exportData () {
      let params = Object.assign({ activityId: this.id }, this.queryParams)
      fullcutOrderExportApi(params).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '批量发货失败列表.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .top {
    background-color: #fff;
    padding: 15px;
  }
  .content {
    background-color: #fff;
    margin-top: 10px;
    padding: 15px;
  }
  .form-input {
    width: 190px;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: 0;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  /deep/ .cell-noborder {
    padding: 0;
    .cell {
      padding: 0;
    }
  }
  .goods-info {
    display: flex;
    height: 90px;
    padding: 10px;
    &:not(:last-child) {
      border-bottom: 1px solid #ebeef5;
    }
    .el-image {
      flex: 0 0 70px;
      width: 70px;
      height: 70px;
    }
    .goods-item {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: flex-start;
      margin-left: 10px;
      .goods-name {
        width: 100%;
        text-align: left;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
      }
      .goods-desc {
        color: #666;
        font-size: 12px;
        width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        text-align: left;
      }
    }
    .goods-price {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100%;
      text-align: center;
    }
  }
}
</style>
