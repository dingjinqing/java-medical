<template>
    <div class="content">
        <div class="main">
            <el-row>
                <el-col :span='24'>
                    <el-form label-width="100px">
                        <el-row :gutter="20">
                            <el-col :span="6">
                                <div class="grid-content">
                                    <el-form-item :label="$t('giveGift.givePeopleMobile')">
                                        <el-input
                                                v-model="requestParams.mobile"
                                                :placeholder="$t('giveGift.givePeopleMobile')"
                                                size="small"
                                                clearable
                                        ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="grid-content">
                                    <el-form-item :label="$t('giveGift.givePeopleName')">
                                        <el-input
                                                v-model="requestParams.userName"
                                                :placeholder="$t('giveGift.givePeopleName')"
                                                size="small"
                                                clearable
                                        ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="grid-content ">
                                    <el-form-item :label="$t('giveGift.goodsName')">
                                            <el-input
                                                    v-model="requestParams.goodsName"
                                                    :placeholder="$t('giveGift.goodsName')"
                                                    size="small"
                                                    clearable
                                            ></el-input>
                                        </el-form-item>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="6">
                                <div class="grid-content ">
                                    <el-form-item :label="$t('giveGift.goodsSn')">
                                        <el-input
                                                v-model="requestParams.goodsSn"
                                                :placeholder="$t('giveGift.goodsSn')"
                                                size="small"
                                                clearable
                                        ></el-input>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="grid-content ">
                                    <el-form-item :label="$t('giveGift.giftStatus')">
                                        <el-select
                                                v-model="requestParams.orderStatus"
                                                :placeholder="$t('giveGift.giftStatus')"
                                                size="small"
                                                class="inputWidth"
                                                filterable
                                        >
                                            <el-option
                                                    v-for="item in $t('giveGift.giftStatusList')"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value"
                                            ></el-option>
                                        </el-select>
                                    </el-form-item>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="grid-content ">
                                    <el-button
                                            class="btn"
                                            type="primary"
                                            size="small"
                                            @click="initDataList"
                                    >{{$t('giveGift.filter')}}</el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-col>
            </el-row>
        </div>
        <div class="table_list">
            <el-table
                    class="version-manage-table"
                    header-row-class-name="tableClss"
                    :data="tableData"
                    v-loading="loading"
                    border
                    style="width: 100%">
                <el-table-column
                        prop="mainOrderSn"
                        :label="$t('giveGift.mainOrderSn')"
                        align="center"
                >
                    <template slot-scope="scope">
                        <el-button @click="mainOrderPage(scope.row.mainOrderSn)" type="text" size="small">
                            {{scope.row.mainOrderSn}}
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="people"
                        :label="$t('giveGift.givePeople')"
                        align="center"
                >
                    <template slot-scope="scope">
                        <el-button @click="userDetailPage(scope.row.userId)" type="text" size="small">
                            {{scope.row.username}}<br/>{{scope.row.mobile}}
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="recommendGoodsId"
                        :label="$t('giveGift.giftGoods')"
                        align="center"
                >
                    <template slot-scope="scope">
                        {{scope.row.goodsList}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="payTime"
                        :label="$t('giveGift.payTime')"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="giftType"
                        :label="$t('giveGift.activityType')"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="receiveNum"
                        :label="$t('giveGift.receivePoepleNum')"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="returnNum"
                        :label="$t('giveGift.returnMoneyNum')"
                        align="center"
                >
                </el-table-column>
                <el-table-column
                        prop="orderStatusName"
                        :label="$t('giveGift.giftStatus')"
                        align="center"
                >
                </el-table-column>
            </el-table>
            <div class="footer">
            </div>
            <pagination
                    :page-params.sync="pageParams"
                    @pagination="initDataList"/>
        </div>
    </div>
</template>
<script>
import pagination from '@/components/admin/pagination/pagination.vue'
import {
  giveGiftRecordList
} from '@/api/admin/marketManage/giveGift'

export default {
  components: {
    pagination
  },
  data: function () {
    return {
      tableData: [],
      loading: false,
      activityName: this.$route.params.activityName,
      pageParams: {},
      requestParams: {
        currentPage: 1,
        pageRows: 20,
        activityId: this.$route.params.id,
        userName: null,
        mobile: null,
        goodsName: null,
        goodsSn: null,
        orderStatus: null
      },
      orderStatusMap: this.$t('order.orderStatusList')

    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  watch: {
    land () {
      this.tabInfo = this.$t('giveGift.tabInfo')
      this.orderStatusMap = this.$t('order.orderStatusList')
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      giveGiftRecordList(this.requestParams).then(res => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.resDataFilter(res.content.dataList)
          this.pageParams = res.content.page
        } else {
          this.$message.warning(res.error, res.message)
        }
        this.loading = false
      }).catch(e => {
        this.$message.warning(e.toString())
        this.loading = false
      })
    },
    resDataFilter (data) {
      data.forEach(item => {
        item.orderStatusName = new Map(this.orderStatusMap).get(item.orderStatus)
        item.people = {name: item.username, mobile: item.mobile, userId: item.userId}
        item.goodsList = JSON.stringify(item.giftGoodsList)
      })
      console.log('resDataFilter', data)
      this.tableData = data
    },
    mainOrderPage (orderSn) {
      // 跳转订单详情页面
      this.$router.push({
        name: 'orderInfo',
        query: {
          orderSn: orderSn
        }
      })
    },
    userDetailPage (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: userId
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
        }
    }

    .p_top_right {
        display: flex;

        span {
            white-space: nowrap;
            height: 32px;
            line-height: 32px;
            margin-right: 10px;
        }

        .topRightDiv {
            &:nth-of-type(2) {
                margin: 0 10px 0 30px;
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

    .opt {
        text-align: left;
        color: #5a8bff;

        span {
            cursor: pointer;
        }
    }

    .balanceDialo .el-dialog__body {
        padding-bottom: 0 !important;
    }

    .balanceDialo .el-dialog__footer {
        border-top: 1px solid #eee;
    }

    .setUpDialog .el-dialog__body {
        padding-top: 10px !important;
    }

    .add_coupon {
        float: left;
        margin-left: 65%;
    }

    .footer {
        padding: 20px 0 20px 20px;
        display: flex;
        justify-content: flex-end;

        span {
            display: block;
            height: 32px;
            line-height: 32px;
        }
    }
</style>
