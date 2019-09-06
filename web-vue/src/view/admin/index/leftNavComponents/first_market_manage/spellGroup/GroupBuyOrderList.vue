<template>
    <div>
        <wrapper>

            <el-form label-width="100px">
                <el-row :gutter=24>
                    <el-col :span="6">
                        <el-form-item :label="$t('groupBuy.goodsName')">
                            <el-input
                                    v-model="requestParams.goodsName"
                                    :placeholder="$t('groupBuy.goodsName')"
                                    size="small"
                                    clearable
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item :label="$t('groupBuy.orderSn')">
                            <el-input
                                    v-model="requestParams.orderSn"
                                    :placeholder="$t('groupBuy.orderSn')"
                                    size="small"
                                    clearable
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="订单状态">
                            <el-select
                                    v-model="requestParams.orderStatus"
                                    placeholder="请选择"
                                    size="small"
                            >
                                <el-option
                                        v-for="item in orderStatus"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter=24>
                    <el-col :span="6">
                        <el-form-item label="收货人姓名">
                            <el-input
                                    v-model="requestParams.consignee"
                                    placeholder="收货人姓名"
                                    size="small"
                                    clearable
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="收货人手机号">
                            <el-input
                                    v-model="requestParams.mobile"
                                    placeholder="收货人手机号"
                                    size="small"
                                    clearable
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="下单时间">
                            <el-date-picker
                                    v-model="requestParams.createTimeStart"
                                    type="datetime"
                                    placeholder="下单时间"
                                    size="small"
                                    class="date_picker"
                            ></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter=24>
                    <el-col :span="10">
                        <el-form-item label="收货地址">
                            <areaLinkage
                                    @areaData="handleAreaData"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" :offset="4">
                        <el-button
                                @click="initDataList()"
                                type="primary"
                                size="small"
                        >筛选
                        </el-button>
                        <el-button
                                type="default"
                                size="small"
                        >导出表格
                        </el-button>
                    </el-col>

                </el-row>
            </el-form>
        </wrapper>
        <wrapper>
            <el-table
                    class="version-manage-table"
                    header-row-class-name="tableHeader"
                    :data="tableData"
                    border
                    style="width: 100%"
            >
                <el-table-column
                        prop="orderSn"
                        label="订单号"
                        align="center"
                > </el-table-column>
                <el-table-column
                        label="拼团商品"
                        align="center"
                        width="200px"
                >
                    <template   slot-scope="scope" >
                        <el-table
                                :data="scope.row.goods"
                                :show-header=false
                        >
                            <el-table-column
                                    prop="goodsName"
                                    align="center"
                            >
                            </el-table-column>
                            <el-table-column
                                    prop="goodsPrice"
                                    label="单价"
                                    align="center"
                            > </el-table-column>
                        </el-table>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="下单时间"
                        align="center"
                > </el-table-column>
                <el-table-column
                        prop="username"
                        label="下单人信息"
                        align="center"
                > </el-table-column>
                <el-table-column
                        prop="consignee"
                        label="收货人信息"
                        align="center"
                > </el-table-column>
                <el-table-column
                        prop="moneyPaid"
                        label="支付金额"
                        align="center"
                > </el-table-column>
                <el-table-column
                        prop="orderStatusText"
                        label="订单状态"
                        align="center"
                > </el-table-column>
            </el-table>
            <pagination
                    :page-params.sync="pageParams"
                    @pagination="initDataList"
            />
        </wrapper>
    </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination.vue'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
import {groupBuyOrderList} from '@/api/admin/marketManage/spellGroup.js'

export default {
  components: {
    pagination,
    areaLinkage,
    wrapper
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.initDataList()
    }
  },
  data () {
    return {
      loading: false,
      pageParams: {},
      requestParams: {},
      tableData: [],
      orderStatus: [
        {value: -1, label: '全部订单'},
        {value: 1, label: '待付款'},
        {value: 2, label: '订单取消'},
        {value: 3, label: '订单关闭'},
        {value: 4, label: '代发货/待核销'},
        {value: 5, label: '已发货'},
        {value: 6, label: '已收货/已自提'},
        {value: 7, label: '订单完成'},
        {value: 8, label: '退货中'},
        {value: 9, label: '退货完成'},
        {value: 10, label: '退款中'},
        {value: 11, label: '退款完成'},
        {value: 12, label: '送礼完成'}
      ]
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.activityId = this.$route.query.id
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      groupBuyOrderList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.forEach(item => {
        item.orderStatusText = this.getOrderStatusText(item.orderStatus)
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
    goodsInfo (data) {
      if (data.columnIndex === 2) {
        return 'no_padding'
      } else {
        return ''
      }
    },
    handleAreaData (data) {
      this.provinceCode = data.province
      this.cityCode = data.city
      this.districtCode = data.district
    }
  }
}
</script>

<style lang="scss" scoped>
    .el-form-item {
        margin-bottom: 1px;
    }

    .el-main {
        padding: inherit;
    }
</style>
