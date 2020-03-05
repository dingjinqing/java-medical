<!--活动明细页面-->
<template>
  <div class="wrapper">
    <div class="info_content">
      <div>
        <span>商品名称：</span>
        <el-input
          v-model="pageParams.id"
          size="small"
          class="input_width"
        ></el-input>
      </div>
      <div>
        <span>订单号：</span>
        <el-input
          v-model="pageParams.mobile"
          size="small"
          class="input_width"
        ></el-input>
      </div>
      <div>
        <span>订单类型：</span>
        <el-input
          v-model="pageParams.orderSn"
          size="small"
          class="input_width"
        ></el-input>
      </div>
    </div>

    <div class="table_list">
      <el-table
        header-row-class-name="tableHeader"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="userId"
          label="订单号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="presaleName"
          label="商品信息"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          label="商品数量"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderId"
          label="下单时间"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="goodsName"
          label="收货人信息"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="goodsAmount"
          label="订单状态"
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
import { getDetailPageList } from '@/api/admin/marketManage/preSale'

export default {
  components: {
    pagination
  },
  mounted () {
    console.log(this.$route.query.id)
  },
  data () {
    return {
      pageParams: {
        id: '',
        mobile: '',
        orderSn: ''
      }
    }
  },
  methods: {
    initDataList () {
      getDetailPageList(this.pageParams).then(res => {
        if (res.error === 0) {
          console.log(res)
        }
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
    display: flex;
    background: #fff;
    .input_width {
      width: 175px;
    }
    :nth-of-type(2) {
      margin: 0 50px;
    }
    .btn {
      margin-left: 10px;
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
