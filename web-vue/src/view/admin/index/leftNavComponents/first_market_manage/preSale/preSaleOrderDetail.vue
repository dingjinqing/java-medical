<!--
* 定金膨胀-活动订单页面
* @author:赵鑫
-->
<template>
  <div class="wrapper">
    <section class="info_content">
      <div class="info1">
        <div>
          <span>商品名称：</span>
          <el-input
            v-model="params.goodsName"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>订单号：</span>
          <el-input
            v-model="params.orderSn"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>订单状态：</span>
          <el-select
            v-model="params.selectedOrderStatus"
            placeholder="请选择"
            size="small"
            class="input_width"
          >
            <el-option
              v-for="item in orderStatusOptions"
              :key="item[0]"
              :label="item[1]"
              :value="item[0]"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <div class="info2">
        <div>
          <span>收货人姓名：</span>
          <el-input
            v-model="params.consignee"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>收货人手机号：</span>
          <el-input
            v-model="params.mobile"
            size="small"
            class="input_width"
          ></el-input>
        </div>
      </div>
      <div class="info3">
        <div>
          <span style="margin-top:7px">收获地址：</span>
          <areaLinkage
            :areaCode="areaLinkage"
            @areaData="handleAreaData"
            style="width:365px;"
          />
        </div>
        <el-button
          size="small"
          type="primary"
          class="btn"
          @click="filter()"
        >筛选</el-button>
        <el-button
          size="small"
          @click="handleToExport()"
        >导出表格</el-button>
      </div>
    </section>

    <div class="table_list">
      <el-table
        header-row-class-name="tableHeader"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="orderSn"
          label="订单号"
          align="center"
        ></el-table-column>

        <el-table-column
          label="商品信息"
          align="center"
        >
          <template slot-scope="scope">
            <div class="goodImge">
              <div>
                <img :src="$imageHost+'/'+scope.row.goods[0].goodsImg">
              </div>
              <div class="name">
                {{scope.row.goods[0].goodsName}}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="goodsAmount"
          label="商品数量"
          align="center"
        >
          <template slot-scope="scope">
            <div>
              {{scope.row.goods[0].goodsNumber}}
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="下单时间"
          align="center"
        ></el-table-column>

        <el-table-column
          prop=""
          label="收货人信息"
          align="center"
        >
          <template slot-scope="scope">
            <div>{{scope.row.consignee}}</div>
            <div>{{scope.row.mobile}}</div>
          </template>
        </el-table-column>

        <el-table-column
          prop="orderStatusText"
          label="订单状态"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="params"
        @pagination="initDataList"
      />
    </div>

    <!--导出弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div class="export_title ">
        <p><img :src="`${$imageHost}/image/admin/notice_img.png`"><span>&nbsp;&nbsp;根据以下条件筛选出{{screenLength}}条数据,是否确认导出？</span></p>
      </div>
      <div class="export_title ">
        <p>筛选条件：无</p>
        <!-- <div
          class="have_export_info"
          v-if="this.params"
        >
          <p>筛选条件：</p>
          <p v-show="params.goodsName">商品名称：{{params.goodsName}}</p>
          <p v-show="params.orderSn">订单号：{{params.orderSn}}</p>
          <p v-show="params.orderStatus">订单状态：{{params.orderStatus}}</p>
          <p v-show="params.consignee">收货人姓名：{{params.consignee}}</p>
          <p v-show="params.mobile">收货人手机号：{{params.mobile}}</p>
        </div> -->
      </div>
      <div class="export_title ">
        <p style="font-weight: bold;">导出数据</p>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToClickSure()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import pagination from '@/components/admin/pagination/pagination.vue'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
import { getOrderList, exporOrderExcel } from '@/api/admin/marketManage/preSale'

export default {
  components: {
    pagination,
    areaLinkage
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.initDataList()
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
    }
  },
  data () {
    return {
      params: {
        activityId: Number(this.$route.query.id),
        goodsName: '',
        mobile: '',
        orderSn: '',
        selectedOrderStatus: null,
        orderStatus: [],
        consignee: '',
        provinceCode: '',
        cityCode: '',
        districtCode: '',
        createTime: '',
        currentPage: 1,
        pageRows: 20
      },
      areaLinkage: {
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      },
      tableData: [],
      dialogVisible: false,
      orderStatusMap: {},
      orderStatusOptions: [
        [null, '全部订单'],
        [0, '待付款'],
        [1, '订单取消'],
        [2, '订单关闭'],
        [3, '待发货/待核销'],
        [4, '已发货'],
        [5, '已收货/已自提'],
        [6, '订单完成']
      ],
      screenLength: 0,
      showFilterInfo: false
    }
  },
  methods: {
    initDataList () {
      if (this.params.selectedOrderStatus !== null) {
        this.params.orderStatus = []
        this.params.orderStatus.push(this.params.selectedOrderStatus)
      } else if (this.params.selectedOrderStatus === null) {
        this.params.orderStatus = []
      }
      getOrderList(this.params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList
          let data = res.content.dataList
          data.forEach(item => {
            item.orderStatusText = this.orderStatusMap.get(item.orderStatus)
          })
          if (data.length) {
            this.screenLength = data.length
          }
        }
      }).catch(err => console.log(err))
    },
    handleAreaData (data) {
      let returnProvince = data.province
      if (returnProvince === 1) {
        this.params.provinceCode = ''
      } else {
        this.params.provinceCode = returnProvince
      }
      this.params.cityCode = data.city
      this.params.districtCode = data.district
    },
    // 筛选
    filter () {
      this.initDataList()
    },
    // 导出
    handleToExport () {
      this.dialogVisible = true
    },
    // 导出弹窗确定事件
    handleToClickSure () {
      exporOrderExcel(this.params).then(res => {
        console.log(res)
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      }).catch(err => console.log(err))
    }
  }
}
</script>

<style lang="scss" scoped>
.wrapper {
  font-size: 14px;
  .info_content {
    padding: 15px 30px;
    margin: 10px;
    background: #fff;
    .info1,
    .info2,
    .info3 {
      display: flex;
      div {
        span {
          display: inline-block;
          width: 105px;
          text-align: right;
        }
      }
    }
    .info1,
    .info2 {
      margin-bottom: 20px;
      :nth-of-type(2) {
        margin: 0 60px;
      }
    }
    .info3 {
      > div {
        display: flex;
      }
      /deep/ .areaLinkage {
        .el-select {
          margin-left: 10px;
          &:first-of-type {
            margin-left: 0;
          }
        }
      }
      span {
        min-width: 100px;
        font-size: 14px;
        text-align: right;
      }
      .btn {
        margin: 0 0 0 10px;
      }
    }
    .input_width {
      width: 175px;
    }
  }
  .table_list {
    margin: 0 10px;
    padding: 15px;
    background: #fff;
  }
  /deep/ .tableHeader th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }
  .export_title {
    margin-bottom: 10px;
  }
  .have_export_info {
    p {
      margin-bottom: 10px;
    }
  }
  .goodImge {
    display: flex;
    img {
      width: 50px;
      height: 50px;
      line-height: 50px;
      border: 1px solid #ccc;
    }
    .name {
      width: 115px;
      height: 40px;
      text-overflow: ellipsis;
      overflow: hidden;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      display: -webkit-box;
      margin-left: 12px;
      text-align: left;
    }
  }
}
</style>
