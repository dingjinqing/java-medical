<template>
  <div class="content">

    <div class="main">
      <el-form label-width="100px">
        <el-row :gutter=24>
          <el-col :span="6">
            <el-form-item label="商品名称">
              <el-input
                placeholder="商品名称"
                v-model="requestParams.goodsName"
                size="small"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="订单号">
              <el-input
                placeholder="订单号"
                v-model="requestParams.orderSn"
                size="small"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="订单状态">
              <el-select
                size="small"
                v-model="requestParams.orderStatus"
                class="inputWidth"
              >
                <el-option
                  v-for="(val,key) in orderStatusArr"
                  :key="key"
                  :label="val"
                  :value="val"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter=24>
          <el-col :span="6">
            <el-form-item label="收件人姓名">
              <el-input
                placeholder="收件人姓名"
                v-model="requestParams.consignee"
                size="small"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="收件人手机号">
              <el-input
                placeholder="收件人手机号"
                v-model="requestParams.mobile"
                size="small"
                clearable
                class="inputWidth"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="6"
            :offset="0"
          >
            <el-form-item label="下单时间">
              <el-date-picker
                type="datetime"
                placeholder="下单时间"
                v-model="requestParams.createTimeStart"
                size="small"
                value-format="yyyy-MM-dd HH:mm:ss"
                class="date_picker inputWidth"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter=24>
          <el-col :span="10">
            <el-form-item label="收货地址">
              <areaLinkage
                @areaData="handleAreaData"
                style="width:380px;"
              />
            </el-form-item>
          </el-col>
          <el-col
            :span="4"
            style="margin:4px 0 0 -50px"
          >
            <el-button
              type="primary"
              size="small"
              @click="initDataList"
            >筛选</el-button>
            <el-button
              type="default"
              size="small"
            >导出数据</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          label="活动名称"
          align="center"
        ></el-table-column>
        <el-table-column
          label="订单号"
          align="center"
        ></el-table-column>
        <el-table-column
          label="秒杀商品"
          align="center"
        ></el-table-column>
        <el-table-column
          label="单价"
          align="center"
        ></el-table-column>
        <el-table-column
          label="下单时间"
          align="center"
        ></el-table-column>
        <el-table-column
          label="下单人信息"
          align="center"
        ></el-table-column>
        <el-table-column
          label="收货人信息"
          align="center"
        ></el-table-column>
        <el-table-column
          label="支付金额"
          align="center"
        ></el-table-column>
        <el-table-column
          label="订单状态"
          align="center"
        >
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>

  </div>
</template>
<script>
// 引入组件
import pagination from '@/components/admin/pagination/pagination.vue'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
import { orderSeckillList } from '@/api/admin/marketManage/seckill.js'
export default {

  components: {
    pagination,
    areaLinkage
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {},
      tableData: [],
      orderStatusArr: this.$t('groupBuy.orderStatusArr')
    }
  },
  watch: {
    lang () {
      this.orderStatusArr = this.$t('groupBuy.orderStatusArr')
      this.initDataList()
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.initDataList()
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.$route.query.id
      orderSeckillList(this.requestParams).then((res) => {
        if (res.error === 0) {
          // this.handleData(res.content.dataList)
          // this.pageParams = res.content.page
          // this.loading = false
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      console.log('订单状态', this.orderStatusArr)

      data.forEach(item => {
        item.orderStatusText = this.orderStatusArr[item.orderStatus]
      })
      this.tableData = data
    },

    getOrderStatusText (index) {
      this.orderStatus.forEach(item => {
        if (item.value === index) {
          return item.label
        }
      })
    },

    handleAreaData (data) {
      this.requestParams.provinceCode = data.province
      this.requestParams.cityCode = data.city
      this.requestParams.districtCode = data.district
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
    .wrapper {
      .el-button {
        margin-left: 5px;
      }
    }
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 10px 20px;
}
.inputWidth {
  width: 175px;
}
.el-form-item {
  margin-bottom: 1px;
}
.el-row {
  margin-bottom: 14px !important;
}
.el-main {
  padding: inherit;
}
/deep/ .areaLinkage {
  .el-select {
    margin-left: 10px;
    &:first-of-type {
      margin-left: 0;
    }
  }
}
</style>
