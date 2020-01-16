<!--
* 定金膨胀活动列表
*
* @author 郑保乐
-->
<template>
  <div class="container">
    <section class="tab_switch">
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="false"
      />
      <section>
        <div class="tab_info1">
          <div>
            活动名称：
            <el-input
              v-model="param.presaleName"
              placeholder="活动名称"
              style="width:180px"
              size="small"
            ></el-input>
          </div>
          <div class="money_paytime">
            定金支付时间：
            <el-date-picker
              v-model="param.preStartTime"
              type="datetime"
              placeholder="选择开始日期"
              size="small"
              style="width:185px"
            >
            </el-date-picker>
            <span style="margin: 0 5px">至</span>
            <el-date-picker
              v-model="param.preEndTime"
              type="datetime"
              placeholder="选择结束日期"
              size="small"
              style="width:185px"
            >
            </el-date-picker>
          </div>
        </div>
        <div class="tab_info2">
          <span>尾款支付时间：</span>
          <el-date-picker
            v-model="param.startTime"
            type="datetime"
            placeholder="选择开始日期"
            size="small"
            style="width:185px"
          >
          </el-date-picker>
          <span style="margin: 0 5px">至</span>
          <el-date-picker
            v-model="param.endTime"
            type="datetime"
            placeholder="选择结束日期"
            size="small"
            style="width:185px"
          >
          </el-date-picker>
          <el-button
            type='primary'
            size="small"
            class="choose"
            @click="initDataList"
          >
            筛选
          </el-button>
        </div>
        <el-button
          size="small"
          type="primary"
          @click="gotoAdd"
          class="add_activity"
        >添加定金膨胀活动</el-button>
      </section>
    </section>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableHeader"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="presaleName"
          label="活动名称"
          align="center"
        > </el-table-column>
        <el-table-column
          prop=""
          label="定金支付时间"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          label="尾款支付时间"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.preStartTime}}<br>至<br>{{scope.row.preEndTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="boughtGoodsQuantity"
          label="已购商品数量"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="orderQuantity"
          label="订单数量"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="bargainPaidOrderQuantity"
          label="已付定金订单数"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="tailPaidOrderQuantity"
          label="已付尾款订单数"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="orderUserQuantity"
          label="下单用户数"
          align="center"
        > </el-table-column>
        <el-table-column
          prop="status"
          label="活动状态"
          align="center"
        > </el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                content="编辑"
                placement="top"
                v-if="scope.row.status === 0 || scope.row.status === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-edit-outline"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.share')"
                placement="top"
                v-if="scope.row.status === 0 || scope.row.status === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-share"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.disableUse')"
                placement="top"
                v-if="scope.row.status === 0 || scope.row.status === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-close"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.enableUse')"
                placement="top"
                v-if="scope.row.status === 3"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-check"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.reveiveDetails')"
                placement="top"
                v-if="scope.row.status !== 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-tickets"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('ordinaryCouponList.delete')"
                placement="top"
                v-if="scope.row.status === 2 || scope.row.status === 3"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-delete"
                ></span>
              </el-tooltip>
            </div>
          </template>
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
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination.vue'
// import { couldEdit, couldStop, couldStart, couldDelete, getNameById } from '@/components/admin/marketManage/status/status'
import { getPageList, disablePreSale } from '@/api/admin/marketManage/preSale'

export default {

  components: {
    statusTab,
    pagination
  },

  watch: {
    'param.status' (n, o) {
      this.initDataList()
    }
  },
  mounted () {
    this.initDataList()
  },
  data () {
    return {
      name: '',
      activityName: '定金膨胀',
      startTime: '',
      param: {
        status: 0,
        presaleName: '',
        startTime: null,
        endTime: null,
        preStartTime: null,
        preEndTime: null
      },
      pageParams: {},
      tableData: []
    }
  },
  methods: {
    // 列表查询
    initDataList () {
      const { param } = this
      getPageList(param).then(res => {
        if (res.error === 0) {
          console.log(res, 'res')
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      }).catch(err => console.log(err))
    },
    // 停用活动
    disable (id) {
      disablePreSale(id).then(r => {
        this.initDataList()
        this.success('停用成功')
      })
    },
    gotoAdd () {
      this.$router.push('/admin/home/main/presale/add')
    }
  }
}
</script>
<style lang="scss" scoped>
.container {
  .tab_switch {
    margin: 10px;
    padding: 20px;
    background: #fff;
    font-size: 14px;
    .tab_info1 {
      display: flex;
      margin: 10px 0;
      .money_paytime {
        margin-left: 70px;
      }
    }
    .tab_info2 {
      display: inline-block;
      margin: 10px 0;
      .choose {
        margin-left: 20px;
      }
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
