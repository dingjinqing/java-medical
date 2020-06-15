<!--
* 定金膨胀活动列表
* @author 赵鑫
-->
<template>
  <div class="container">
    <section class="tab_switch">
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="true"
        @click="initDataList()"
      />
      <section>
        <div class="tab_info1">
          <div>
            <span class="info_title">{{$t('preSale.activityName')+'：'}}</span>
            <el-input
              v-model="param.name"
              :placeholder="$t('preSale.activityName')"
              style="width:180px"
              size="small"
              clearable
            ></el-input>
          </div>
          <div class="money_paytime">
            <span class="info_title">{{$t('preSale.prePayTime')+'：'}}</span>
            <el-date-picker
              v-model="param.preStartTime"
              type="datetime"
              :placeholder="$t('preSale.selectStartTime')"
              size="small"
              style="width:185px"
              value-format="yyyy-MM-dd HH:mm:ss"
              clearable
              default-time="00:00:00"
            >
            </el-date-picker>
            <span style="margin: 0 5px">{{$t('preSale.to')}}</span>
            <el-date-picker
              v-model="param.preEndTime"
              type="datetime"
              :placeholder="$t('preSale.selectEndTime')"
              size="small"
              style="width:185px"
              value-format="yyyy-MM-dd HH:mm:ss"
              clearable
              default-time="23:59:59"
            >
            </el-date-picker>
          </div>
        </div>
        <div class="tab_info2">
          <div>
            <span class="info_title">{{$t('preSale.payEndTime')+'：'}}</span>
            <el-date-picker
              v-model="param.startTime"
              type="datetime"
              :placeholder="$t('preSale.selectStartTime')"
              size="small"
              style="width:185px"
              value-format="yyyy-MM-dd HH:mm:ss"
              clearable
              default-time="00:00:00"
            >
            </el-date-picker>
            <span style="margin: 0 5px">{{$t('preSale.to')}}</span>
            <el-date-picker
              v-model="param.endTime"
              type="datetime"
              :placeholder="$t('preSale.selectEndTime')"
              size="small"
              style="width:185px"
              value-format="yyyy-MM-dd HH:mm:ss"
              clearable
              default-time="23:59:59"
            >
            </el-date-picker>
          </div>

          <el-button
            type='primary'
            size="small"
            class="choose"
            @click="initDataList"
          >
            {{$t('preSale.filtrate')}}
          </el-button>
        </div>
        <el-button
          size="small"
          type="primary"
          @click="gotoAdd"
          class="add_activity"
        >{{$t('preSale.addActivity')}}</el-button>
      </section>
    </section>

    <div class="table_list">
      <el-table
        header-row-class-name="tableHeader"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="presaleName"
          :label="$t('preSale.activityName')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop=""
          :label="$t('preSale.prePayTime')"
          align="center"
          width="160"
        >
          <template slot-scope="scope">
            {{scope.row.preStartTime}}<br>{{$t('preSale.to')}}<br>{{scope.row.preEndTime}}
            <div v-if="scope.row.preStartTime2 && scope.row.preEndTime2">
              {{scope.row.preStartTime2}}<br>{{$t('preSale.to')}}<br>{{scope.row.preEndTime2}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          :label="$t('preSale.payEndTime')"
          align="center"
          width="160"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.presaleType === 0">
              {{scope.row.startTime}}<br>{{$t('preSale.to')}}<br>{{scope.row.endTime}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="boughtGoodsQuantity"
          :label="$t('preSale.purchasedGoodsNumber')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="orderQuantity"
          :label="$t('preSale.orderNumber')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="bargainPaidOrderQuantity"
          :label="$t('preSale.paidStartMoneyOrder')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="tailPaidOrderQuantity"
          :label="$t('preSale.paidEndMoneyOrder')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="orderUserQuantity"
          :label="$t('preSale.orderUserNumber')"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="statusText"
          :label="$t('preSale.activityStatus')"
          align="center"
        > </el-table-column>
        <el-table-column
          :label="$t('preSale.operate')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('preSale.edit')"
                placement="top"
                v-if="scope.row.status === 1 || scope.row.status === 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconbianji"
                  @click="gotoEdit(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.share')"
                placement="top"
                v-if="scope.row.status === 1 || scope.row.status === 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconfenxiang1"
                  @click="share(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.disableUse')"
                placement="top"
                v-if="scope.row.status === 1 || scope.row.status === 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont icontingyong"
                  @click="disable(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.enableUse')"
                placement="top"
                v-if="scope.row.status === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconqiyong"
                  @click="enable(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- hello world  -->
              <el-tooltip
                :content="$t('preSale.viewActivityOrder')"
                placement="top"
                v-if="scope.row.status !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont icondingdan"
                  @click="receiveDetails(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('preSale.activityDetail')"
                placement="top"
                v-if="scope.row.status !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconmingxi1"
                  @click="activityDetails(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.delete')"
                placement="top"
                v-if="scope.row.status === 3 || scope.row.status === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconshanchu2"
                  @click="deleteActivity(scope.row.id)"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="param"
        @pagination="initDataList"
      />
    </div>

    <!-- 分享弹窗 -->
    <shareDialog
      :show="showShareDialog"
      :imgPath="shareImg"
      :pagePath="sharePath"
      @close="showShareDialog=false"
    />
  </div>
</template>
<script>
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination.vue'
import shareDialog from '@/components/admin/shareDialog'
// import { couldEdit, couldStop, couldStart, couldDelete, getNameById } from '@/components/admin/marketManage/status/status'
import { getPageList, disablePreSale, sharePreSale, enablePreSale, deletePreSale } from '@/api/admin/marketManage/preSale'

export default {

  components: {
    statusTab,
    pagination,
    shareDialog
  },

  watch: {
    'param.status' (n, o) {
      this.initDataList()
    }
  },
  mounted () {
    this.initDataList()
    if (this.$route.params.calenderEdit) {
      this.gotoEdit(this.$route.params.id)
    }
  },
  data () {
    return {
      name: '',
      activityName: '定金膨胀',
      startTime: '',
      param: {
        status: 1,
        name: '',
        startTime: null,
        endTime: null,
        preStartTime: null,
        preEndTime: null,
        currentPage: 1,
        pageRows: 20
      },
      pageParams: {},
      tableData: [],
      showShareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: ''
    }
  },
  methods: {
    // 列表查询
    initDataList () {
      const { param } = this
      getPageList(param).then(res => {
        if (res.error === 0) {
          console.log(res, 'res')
          this.param = Object.assign(this.param, res.content.page)
          this.tableData = res.content.dataList
          this.tableData.map((item, index) => {
            item.statusText = this.getActStatusString(item.status)
          })
        }
      }).catch(err => console.log(err))
    },
    gotoAdd () {
      this.$router.push('/admin/home/main/presale/add')
    },
    // 编辑活动
    gotoEdit (id, val) {
      console.log(id, 'get id', val)
      this.$router.push({
        path: `/admin/home/main/presale/edit/${id}`,
        query: {
          id: id,
          currentState: val.currentStatus
        }
      })
    },
    // 分享活动
    share (id) {
      // alert(id)
      sharePreSale(id).then(res => {
        if (res.error === 0) {
          this.shareImg = res.content.imageUrl
          this.sharePath = res.content.pagePath
          this.showShareDialog = !this.showShareDialog
        }
      })
    },
    // 停用活动
    disable (id) {
      this.$confirm(this.$t('payReward.confirmStop'), {
        confirmButtonText: this.$t('payReward.confirm'),
        cancelButtonText: this.$t('payReward.cancel'),
        type: 'warning'
      }).then(() => {
        disablePreSale(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('payReward.stopSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('payReward.cancelStop') })
      })
    },
    // 启用活动
    enable (id) {
      this.$confirm(this.$t('payReward.confirmEnable'), {
        confirmButtonText: this.$t('payReward.confirm'),
        cancelButtonText: this.$t('payReward.cancel'),
        type: 'warning'
      }).then(() => {
        enablePreSale(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('payReward.enableSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('payReward.cancelEnable') })
      })
    },
    // 删除活动
    deleteActivity (id) {
      this.$confirm(this.$t('payReward.confirmDelete'), {
        confirmButtonText: this.$t('payReward.confirm'),
        cancelButtonText: this.$t('payReward.cancel'),
        type: 'warning'
      }).then(() => {
        deletePreSale(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('payReward.deleteSucess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('payReward.cancelDelete') })
      })
    },
    // 领取明细
    receiveDetails (id) {
      console.log(id)
      this.$router.push({
        path: `/admin/home/main/presale/order_detail/${id}`,
        query: {
          id: id
        }
      })
    },
    // 活动明细
    activityDetails (id) {
      this.$router.push({
        path: `/admin/home/main/presale/detail/${id}`,
        query: {
          id: id
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.container {
  .tab_switch {
    margin: 10px;
    padding: 20px;
    background: #fff;
    font-size: 14px;
    .tab_info1,
    .tab_info2 {
      display: flex;
      margin: 10px 0;
      .money_paytime {
        margin-left: 70px;
      }
      .choose {
        margin-left: 15px;
      }
      .info_title {
        display: inline-block;
        width: 110px;
        text-align: right;
      }
    }
    .tab_info2 {
      margin: 20px 0;
    }
    .add_activity {
      display: block;
      margin-top: 10px;
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
  .opt {
    text-align: left;
    color: #5a8bff;
    span {
      cursor: pointer;
    }
  }
}
</style>
