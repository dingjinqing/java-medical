<!--
* 定金膨胀-活动明细页面
* @author:赵鑫
-->
<template>
  <div class="wrapper">
    <div class="info_content">
      <div>
        <span>用户名：</span>
        <el-input
          v-model="params.username"
          size="small"
          class="input_width"
          clearable
        ></el-input>
      </div>
      <div>
        <span>手机号：</span>
        <el-input
          v-model="params.mobile"
          size="small"
          class="input_width"
          clearable
        ></el-input>
      </div>
      <div>
        <span>订单号：</span>
        <el-input
          v-model="params.orderSn"
          size="small"
          class="input_width"
          clearable
        ></el-input>
      </div>
      <div style="margin-left: 30px">
        <el-button
          size="small"
          type="primary"
          @click="initDataList"
        >筛选</el-button>
      </div>
    </div>

    <div class="table_list">
      <el-table
        header-row-class-name="tableHeader"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="userId"
          label="用户ID"
          align="center"
          width="130"
        ></el-table-column>

        <!-- <el-table-column
          prop="userId"
          label="用户昵称"
          align="center"
        ></el-table-column> -->

        <el-table-column
          prop="mobile"
          label="手机号"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="orderSn"
          label="订单号"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="goodsName"
          label="下单商品"
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

        <!-- <el-table-column
          prop="goodsName"
          label="收货人信息"
          align="center"
        ></el-table-column> -->

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
import { getDetailPageList } from '@/api/admin/marketManage/preSale'

export default {
  components: {
    pagination
  },
  mounted () {
    console.log(this.$route.query.id)
    this.initDataList()
  },
  data () {
    return {
      pageParams: {},
      params: {
        id: Number(this.$route.query.id),
        username: '',
        mobile: '',
        orderSn: ''
      },
      tableData: []
    }
  },
  methods: {
    initDataList () {
      getDetailPageList(Object.assign(this.pageParams, this.params)).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList
        }
      }).catch((res) => {
        this.$messge.error(res.error)
      })
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
      width: 180px;
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
