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
                        <el-form-item :label="$t('groupBuy.orderStatus')">
                            <el-select
                                    v-model="requestParams.orderStatus"
                                    size="small"
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
                        <el-form-item :label="$t('groupBuy.consigneeName')">
                            <el-input
                                    v-model="requestParams.consignee"
                                    :placeholder="$t('groupBuy.consigneeName')"
                                    size="small"
                                    clearable
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item :label="$t('groupBuy.consigneeMobile')">
                            <el-input
                                    v-model="requestParams.mobile"
                                    :placeholder="$t('groupBuy.consigneeMobile')"
                                    size="small"
                                    clearable
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item :label="$t('groupBuy.orderTime')">
                            <el-date-picker
                                    v-model="requestParams.createTimeStart"
                                    type="datetime"
                                    :placeholder="$t('groupBuy.orderTime')"
                                    size="small"
                                    class="date_picker"
                            ></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter=24>
                    <el-col :span="10">
                        <el-form-item :label="$t('groupBuy.shippingAddress')">
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
                        >{{$t('marketCommon.filter')}}
                        </el-button>
                        <el-button
                                type="default"
                                size="small"
                        >{{$t('marketCommon.export')}}
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
                        :label="$t('groupBuy.orderSn')"
                        align="center"
                > </el-table-column>
                <el-table-column
                        :label="$t('groupBuy.goodsName')"
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
                                    align="center"
                            > </el-table-column>
                        </el-table>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        :label="$t('groupBuy.orderTime')"
                        align="center"
                > </el-table-column>
                <el-table-column
                        prop="username"
                        :label="$t('groupBuy.buyerName')"
                        align="center"
                > </el-table-column>
                <el-table-column
                        prop="consignee"
                        :label="$t('groupBuy.consigneeInfo')"
                        align="center"
                > </el-table-column>
                <el-table-column
                        prop="moneyPaid"
                        :label="$t('groupBuy.paymentAmount')"
                        align="center"
                > </el-table-column>
                <el-table-column
                        prop="orderStatusText"
                        :label="$t('groupBuy.orderStatus')"
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
      // 初始化语言
      this.langDefault()
    }
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
