<template>
  <div class="content">
    <div class="main">
      <el-form
        :model="form"
        label-width="140px"
        label-position="right"
      >
        <div>
          <el-form-item
            label="返利订单号："
            class="item"
          >
            <el-input
              v-model="form.rebateOrderSn"
              size="small"
              class="inputWidth"
              :placeholder="$t('distribution.contentTip')"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="下单用户昵称："
            class="item"
          >
            <el-input
              v-model="form.username"
              size="small"
              class="inputWidth"
              :placeholder="$t('distribution.contentTip')"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="下单用户手机号："
            class="item"
          >
            <el-input
              v-model="form.mobile"
              size="small"
              class="inputWidth"
              :placeholder="$t('distribution.contentTip')"
            ></el-input>
          </el-form-item>
        </div>
        <div>
          <el-form-item
            label="分销员昵称："
            class="item"
          >
            <el-input
              v-model="form.distributorName"
              size="small"
              class="inputWidth"
              :placeholder="$t('distribution.contentTip')"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="分销员手机号："
            class="item"
          >
            <el-input
              v-model="form.distributorMobile"
              size="small"
              class="inputWidth"
              :placeholder="$t('distribution.contentTip')"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="分销员真实姓名："
            class="item"
          >
            <el-input
              v-model="form.distributorRealName"
              size="small"
              class="inputWidth"
              :placeholder="$t('distribution.contentTip')"
            ></el-input>
          </el-form-item>
        </div>
        <div>
          <el-form-item
            label="返利时间："
            class="item"
          >
            <el-date-picker
              v-model="form.startRebateTime"
              type="datetime"
              :placeholder="$t('distribution.chooseDate')"
              value-format="yyyy-MM-dd 00:00:00"
              size="small"
              style="width: 190px;"
            >
            </el-date-picker>
            至
            <el-date-picker
              v-model="form.endRebateTime"
              type="datetime"
              :placeholder="$t('distribution.chooseDate')"
              value-format="yyyy-MM-dd 23:59:59"
              size="small"
              style="width: 190px;"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item
            label="返利状态："
            class="item"
          >
            <el-select
              v-model="form.rebateStatus"
              :placeholder="$t('distribution.selectTip')"
              size="small"
              class="inputWidth"
              clearable
            >
              <el-option
                v-for="item in statusList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div>
          <el-form-item
            label="返利关系："
            class="item"
          >
            <el-select
              v-model="form.rebateLevel"
              :placeholder="$t('distribution.selectTip')"
              size="small"
              class="inputWidth"
              clearable
            >
              <el-option
                v-for="item in relationList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-button
            @click="initData"
            type="primary"
            size="small"
            style="margin-left: 10px;"
          >筛选</el-button>
          <el-button
            size="small"
            @click="exportDataList"
          >导出</el-button>
        </div>
      </el-form>
    </div>

    <div class="main list_content">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <template slot="empty">
          <tableEmpty />
        </template>
        <el-table-column
          label="商品名称"
          align="center"
          width="120"
        >
          <template slot-scope="scope">
            <div class="goodsContent">
              <img
                :src="$imageHost + '/' + scope.row.goodsImg"
                alt=""
                class="imgContent"
              >
              <span class="nameContent">{{scope.row.goodsName}}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="goodsNumber"
          label="商品数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="canCalculateMoney"
          label="返利商品金额"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="商品订单号"
          align="center"
        >
          <template slot-scope="scope">
            <span
              @click="orderHandler(scope.row.orderSn)"
              class="highStyle"
            >{{scope.row.orderSn}}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="下单用户昵称"
          align="center"
        >
          <template slot-scope="scope">
            <span
              @click="userHandler(scope.row.userId)"
              class="highStyle"
            >{{scope.row.username}}</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="下单用户手机号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="rebateLevel"
          label="返利关系"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="分销员昵称"
          align="center"
        >
          <template slot-scope="scope">
            <span
              @click="userHandler(scope.row.distributorId)"
              class="highStyle"
            >{{scope.row.distributorName}}</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="distributorRealName"
          label="分销员真实姓名"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="distributorMobile"
          label="分销员手机号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="返利比例"
          align="center"
        >
          <template slot-scope="scope">
            <span>{{scope.row.rebatePercent ? scope.row.rebatePercent : 0}}%</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="realRebateMoney"
          label="商品返利佣金金额"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="settlementFlag"
          label="返利状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="finishedTime"
          label="返利日期"
          align="center"
          width="100"
        >
        </el-table-column>
      </el-table>
    </div>

    <pagination
      :page-params.sync="pageParams"
      @pagination="initData"
    />

    <!-- 导出数据弹窗 -->
    <exportDialog
      :tuneUp="exportDialog"
      :param="this.form"
      :totalRows="totalRows"
      :type="4"
      @export="exportHandler"
    />
  </div>
</template>
<script>
import { download } from '@/util/excelUtil.js'
import { statisticsList, rebateGoodsExport } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    exportDialog: () => import('./moneyExportDialog')
  },
  data () {
    return {
      goodsId: '',
      form: {
        rebateOrderSn: '',
        username: '',
        mobile: '',
        distributorName: '',
        distributorMobile: '',
        distributorRealName: '',
        startRebateTime: '',
        endRebateTime: '',
        rebateStatus: '',
        rebateLevel: ''
      },
      tableData: [],
      pageParams: {},
      // 返利状态
      statusList: [{
        value: 0,
        label: '待返利'
      }, {
        value: 1,
        label: '已返利'
      }, {
        value: 2,
        label: '不返利'
      }],
      // 返利关系
      relationList: [{
        value: 0,
        label: '自购返利'
      }, {
        value: 1,
        label: '直接返利'
      }, {
        value: 2,
        label: '间接返利'
      }],

      exportDialog: false, // 导出数据弹窗
      totalRows: 0 // 筛选个数
    }
  },
  mounted () {
    if (this.$route.query.goodsId) {
      this.goodsId = this.$route.query.goodsId
      this.initData()
    }
  },
  methods: {
    initData () {
      return new Promise((resolve, reject) => {
        var obj = this.form
        obj.currentPage = this.pageParams.currentPage
        obj.pageRows = this.pageParams.pageRows
        obj.goodsId = this.goodsId
        statisticsList(obj).then(res => {
          if (res.error === 0) {
            this.tableData = res.content.dataList
            this.tableData.forEach(item => {
              // 返利状态
              this.statusList.forEach(val => {
                if (item.settlementFlag === val.value) {
                  item.settlementFlag = val.label
                }
              })
              // 返利关系
              this.relationList.forEach(val => {
                if (item.rebateLevel === val.value) {
                  item.rebateLevel = val.label
                }
              })
              //
            })
            this.pageParams = res.content.page
            resolve(this.pageParams)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 导出数据
    exportDataList () {
      this.initData().then(() => {
        this.totalRows = this.pageParams.totalRows
        this.exportDialog = !this.exportDialog
      })
    },

    // 导出数据弹窗回调函数
    exportHandler (data) {
      // 搜索条件
      var obj = data
      obj.goodsId = this.goodsId
      rebateGoodsExport(obj).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '商品返利明细列表导出.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    },

    // 跳转订单详情
    orderHandler (orderSn) {
      this.$router.push({
        path: '/admin/home/main/orders/info',
        query: {
          orderSn: orderSn
        }
      })
    },

    // 跳转会员详情
    userHandler (id) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: id
        }
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
    .item {
      display: inline-block;
    }
    .inputWidth {
      width: 170px;
    }
  }
  .list_content {
    margin-top: 10px;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
    }
    .highStyle {
      color: #5a8bff;
      cursor: pointer;
    }
    .goodsContent {
      width: 100%;
      height: 100%;
      display: flex;
      .imgContent {
        width: 50px;
      }
      .nameContent {
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
      }
    }
  }
}
</style>
