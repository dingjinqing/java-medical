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
          <!-- <el-input
            v-model="params.orderSn"
            size="small"
            class="input_width"
          ></el-input> -->
          <el-select
            v-model="orderStatus"
            placeholder="请选择"
            size="small"
            class="input_width"
          >
            <el-option
              v-for="item in orderStatusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <div class="info2">
        <div>
          <span>收货人姓名：</span>
          <el-input
            v-model="params.consigneeName"
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
          <!-- {{$t('order.shippingAddress')}}： -->
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
          prop="goodsName"
          label="商品信息"
          align="center"
        >
          <template slot-scope="scope">
            <div class="goodImge">
              <div>
                <img :src="$imageHost+'/'+scope.row.goodsImg">
              </div>
              <div class="name">
                {{scope.row.goodsName}}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column
          prop="goodsAmount"
          label="商品数量"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="createTime"
          label="下单时间"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="orderStatusName"
          label="收货人信息"
          align="center"
        >
          <template slot-scope="scope">
            <div>{{scope.row.consigneeRealName}}</div>
            <div>{{scope.row.mobile}}</div>
          </template>
        </el-table-column>

        <el-table-column
          prop="orderAmount"
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
        <p><img :src="`${$imageHost}/image/admin/notice_img.png`"><span>&nbsp;&nbsp;根据以下条件筛选出37条数据,是否确认导出？</span></p>
      </div>
      <div class="export_title ">
        <p>筛选条件：无</p>
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
import pagination from '@/components/admin/pagination/pagination.vue'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'

import { getOrderList } from '@/api/admin/marketManage/preSale'

export default {
  components: {
    pagination,
    areaLinkage
  },
  mounted () {
    console.log(this.$route.query.id)
    this.initDataList()
  },
  data () {
    return {
      params: {
        id: this.$route.query.id,
        goodsName: '',
        mobile: '',
        orderSn: '',
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      },
      areaLinkage: {
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      },
      tableData: [],
      dialogVisible: false,
      orderStatus: -1,
      orderStatusOptions: [{
        value: -1,
        label: '全部订单'
      }, {
        value: 0,
        label: '待付款'
      }, {
        value: 1,
        label: '订单取消'
      }, {
        value: 2,
        label: '订单关闭'
      }, {
        value: 3,
        label: '待发货/待核销'
      }, {
        value: 4,
        label: '已发货'
      }, {
        value: 5,
        label: '已发货/已自提'
      }, {
        value: 6,
        label: '订单完成'
      }, {
        value: 7,
        label: '售后中'
      }, {
        value: 8,
        label: '售后完成'
      }, {
        value: 9,
        label: '送礼完成'
      }, {
        value: 10,
        label: '待接单'
      }, {
        value: 11,
        label: '待接单-取件中'
      }, {
        value: 12,
        label: '已取件-配送中'
      }]
    }
  },
  methods: {
    initDataList () {
      getOrderList(this.params).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
        }
      }).catch(err => console.log(err))
    },
    handleAreaData (data) {
      console.log(data)
      this.params.provinceCode = data.province
      this.params.cityCode = data.city
      this.params.districtCode = data.district
    },
    // 筛选
    filter () {

    },
    // 导出
    handleToExport () {
      this.dialogVisible = true
    },
    // 导出弹窗确定事件
    handleToClickSure () {

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
