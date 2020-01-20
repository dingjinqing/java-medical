<!--活动订单页面-->
<template>
  <div class="wrapper">
    <section class="info_content">
      <div class="info1">
        <div>
          <span>用户昵称：</span>
          <el-input
            v-model="pageParams.id"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>手机号：</span>
          <el-input
            v-model="pageParams.mobile"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>订单号：</span>
          <el-input
            v-model="pageParams.orderSn"
            size="small"
            class="input_width"
          ></el-input>
        </div>
      </div>
      <div class="info2">
        <div>
          <span>收货人姓名：</span>
          <el-input
            v-model="pageParams.consigneeName"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>收货人手机号：</span>
          <el-input
            v-model="pageParams.mobile"
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
            @areaData="handleAreaData"
            style="width:365px;"
          />
        </div>
        <el-button
          size="small"
          type="primary"
          class="btn"
        >筛选</el-button>
        <el-button size="small">导出表格</el-button>
      </div>
    </section>

    <div class="table_list">
      <el-table
        header-row-class-name="tableHeader"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="userId"
          label="用户ID"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="presaleName"
          label="用户昵称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          label="手机号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderId"
          label="订单号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="goodsName"
          label="下单商品"
          align="center"
        ></el-table-column>
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
          label="订单状态"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderAmount"
          label="下单金额"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
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
  },
  data () {
    return {
      pageParams: {
        id: '',
        mobile: '',
        orderSn: '',
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      }
    }
  },
  methods: {
    initDataList () {
      getOrderList(this.pageParams).then(res => {
        if (res.error === 0) {
          console.log(res)
        }
      }).catch(err => console.log(err))
    },
    handleAreaData (data) {
      console.log(data)
      this.pageParams.provinceCode = data.province
      this.pageParams.cityCode = data.city
      this.pageParams.districtCode = data.district
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
}
</style>
