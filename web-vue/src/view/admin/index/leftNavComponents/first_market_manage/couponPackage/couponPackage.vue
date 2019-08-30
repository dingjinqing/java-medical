<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <statusTab
          v-model="tabIndex"
          :activityName="activityName"
          :standard="true"
        />
        <el-button
          type="primary"
          @click="addCouponPackage()"
        >添加优惠券礼包</el-button>
      </div>
      <div class="table_box">
        <div class="filters">
          <div class="filters_item"><span>活动名称：</span>
            <el-input
              v-model="actName"
              placeholder="请输入活动名称"
              size="small"
            ></el-input>
          </div>
          <div class="filters_item"><span>礼包名称：</span>
            <el-input
              v-model="packName"
              placeholder="请输入礼包名称"
              size="small"
            ></el-input>
          </div>
          <div class="filters_item"><span>领取方式：</span>
            <el-select
              v-model="accessMode"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in get_type_option"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <el-button
              @click="initDataList"
              type="primary"
              size="small"
            >筛选</el-button>
          </div>
        </div>
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
          <el-table-column
            prop="actName"
            label="活动名称"
          ></el-table-column>
          <el-table-column
            prop="packName"
            label="礼包名称"
          ></el-table-column>
          <el-table-column
            prop="vaildDate"
            label="有效期"
            width="160"
          >
            <template slot-scope="scope">
              <span v-html="scope.row.vaildDate"></span>
            </template>
          </el-table-column>
          <el-table-column
            prop="voucherKindsNumber"
            label="优惠券种类数/礼包"
          ></el-table-column>
          <el-table-column
            prop="voucherNumber"
            label="优惠券数量/礼包"
          ></el-table-column>
          <el-table-column
            prop="totalAmount"
            label="可发放礼包数"
          ></el-table-column>
          <el-table-column
            prop="accessMode"
            label="领取方式"
          ></el-table-column>
          <el-table-column
            prop="accessCost"
            label="购买金额"
            width="80"
          ></el-table-column>
          <el-table-column
            prop="issueAmount"
            label="已领取礼包数"
          ></el-table-column>
          <el-table-column
            prop="statusName"
            label="活动状态"
            width="80"
          ></el-table-column>
          <el-table-column
            label="操作"
            width="130"
          >
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="编辑"
                  placement="top"
                  v-if="scope.row.status === 1"
                >
                  <i
                    class="el-icon-edit-outline"
                    @click="edit(scope)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="删除"
                  placement="top"
                  v-else
                >
                  <i
                    @click="delCouponPackage(scope.row.id)"
                    class="el-icon-delete"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="分享"
                  placement="top"
                >
                  <i
                    class="el-icon-share"
                    @click="shareCouponPackage(scope.row.id)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  v-if="scope.row.status === 1"
                  class="item"
                  effect="dark"
                  content="停用"
                  placement="top"
                >
                  <i
                    @click="puaseCouponPackage(scope.row.id)"
                    class="el-icon-remove-outline"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  v-else
                  class="item"
                  effect="dark"
                  content="启用"
                  placement="top"
                >
                  <i
                    @click="enableCouponPackage(scope.row.id)"
                    class="el-icon-check"
                  ></i>
                </el-tooltip>

                <el-tooltip
                  class="item"
                  effect="dark"
                  content="查看订单"
                  placement="top"
                >
                  <i class="el-icon-s-order"></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="领取明细"
                  placement="top"
                >
                  <i
                    class="el-icon-document"
                    @click="receiveDetails(scope.row.id)"
                  ></i>
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
  </div>
</template>

<script>
import { couponPackageList, updateCouponPackage, deleteCouponPackage, getCouponPackShareCode } from '@/api/admin/marketManage/couponPackage.js'
import statusTab from '@/components/admin/status/statusTab'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination, statusTab },
  data () {
    return {
      activityName: '优惠券礼包',
      // 默认显示进行中的活动
      tabIndex: 1,
      currentPage: 1,
      pageParams: {},
      loading: false,

      activeName: 'first',
      actName: '',
      packName: '',
      accessMode: -1,
      get_type_option: [
        { value: -1, label: '全部' },
        { value: 0, label: '现金' },
        { value: 1, label: '积分' },
        { value: 2, label: '免费' }
      ],
      tableData: [],
      totalRows: null
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      let param = {
        'state': parseInt(this.tabIndex),
        'accessMode': parseInt(this.accessMode),
        'actName': this.actName,
        'packName': this.packName,
        'currentPage': 1
      }

      couponPackageList(param).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        // TODO: 国际化
        switch (item.accessMode) {
          case 0:
            item.accessMode = '现金购买'
            break
          case 1:
            item.accessMode = '积分购买'
            break
          case 2:
            item.accessMode = '直接领取'
            break
        }
        item.vaildDate = `${item.startTime}<br/>至<br/>${item.endTime}`
        item.statusName = this.getActStatusString(item.status, item.startTime, item.endTime)
      })
      this.tableData = data
    },

    // 停用优惠券礼包活动
    puaseCouponPackage (id) {
      let param = {
        'id': id,
        'status': 0
      }
      this.$confirm('确定停用该优惠券礼包活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateCouponPackage(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '停用成功!'
            })
            this.initDataList()
          }
        })
      })
    },

    // 启用优惠券礼包活动
    enableCouponPackage (id) {
      let param = {
        'id': id,
        'status': 1
      }
      this.$confirm('确定启用该优惠券礼包活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateCouponPackage(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '启用成功!'
            })
            this.initDataList()
          }
        })
      })
    },

    // 删除优惠券礼包活动
    delCouponPackage (id) {
      let param = {
        'id': id
      }
      this.$confirm('确定删除该优惠券礼包活动?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCouponPackage(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.initDataList()
          }
        })
      })
    },
    // 领取明细
    receiveDetails (id) {
      console.log(id)
      this.$router.push({
        name: 'coupon_Package_receive_details',
        query: {
          id: id
        }
      })
    },
    // 取活动分享二维码
    shareCouponPackage (id) {
      getCouponPackShareCode(id).then((res) => {
        console.log(res)
      })
    },

    edit (scope) {
      console.log(scope)
    },
    addCouponPackage () {
      this.$router.push({
        name: 'coupon_Package_add'
      })
    }
  },
  watch: {
    'tabIndex' (n, o) {
      this.initDataList()
    }
  },
  mounted () {
    this.initDataList()
  }

}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  .navBox {
    background-color: #fff;
    padding: 0 15px 14px;
    margin-bottom: 10px;
  }
  .table_box {
    background-color: #fff;
    padding: 15px;
    .filters {
      display: flex;
      line-height: 32px;
      margin-left: -15px;
      margin-bottom: 10px;
      .filters_item {
        max-width: 250px;
        display: flex;
        margin-left: 15px;
        > span {
          min-width: 80px;
          font-size: 14px;
        }
      }
    }
    .operation {
      display: flex;
      justify-content: space-around;
      > .item {
        font-size: 22px;
        color: #66b1ff;
        cursor: pointer;
      }
    }
    .tapOneblock {
      display: flex;
      justify-content: flex-end;
      margin-top: 10px;
      > span {
        height: 32px;
        line-height: 32px;
      }
    }
  }
}
</style>
