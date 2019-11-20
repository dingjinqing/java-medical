<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <div class="filters_item"><span>手机号：</span>
          <el-input
            v-model="searchData.mobile"
            placeholder="请输入手机号"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>用户昵称：</span>
          <el-input
            v-model="searchData.userName"
            placeholder="请输入用户昵称"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>使用状态：</span>
          <el-select
            v-model="searchData.useStatus"
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
          >搜索</el-button>
        </div>
      </div>
      <div class="table_box">
        <el-table
          v-loading="loading"
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          style="width:100%;"
          border
        >
          <!-- <template v-for="item in tableLabel">
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-if="item.index === 11"
            >
              <template slot-scope="scope">

                <div class="operation">
                  <el-tooltip
                    class="item"
                    effect="dark"
                    content="废除"
                    placement="top"
                    :popper-options="{
                        trigger: 'hover'
                    }"
                  >
                    <i
                      @click="deleteCoupon(scope.row.id)"
                      class="el-icon-delete"
                    ></i>
                  </el-tooltip>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="item.index"
              v-else
            ></el-table-column>
          </template> -->
          <el-table-column
            label="用户昵称"
            align="center"
          >
            <template slot-scope="scope">
              <a
                href="javascript:void(0);"
                @click="infoClickHandler(scope.row.userId)"
              >{{ scope.row.username }}</a>
            </template>
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号码"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="couponName"
            label="优惠券名称"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="accessMode"
            label="领取方式"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="scoreNumber"
            label="兑换积分数"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="isUsed"
            label="是否使用"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="orderSn"
            label="使用订单号"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="validityTime"
            label="有效期"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="领取时间"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="usedTime"
            label="使用时间"
            align="center"
          ></el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <span
                style="font-size: 22px;"
                class="el-icon-delete"
                @click="deleteCoupon(scope.row.id)"
              ></span>
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
import { couponGetDetail, deleteCouponDetail } from '@/api/admin/marketManage/couponList.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      loading: false,
      id: '',
      tableData: [],
      pageParams: {}, // 分页
      requestParams: {},
      // 搜索
      searchData: {
        mobile: '',
        userName: '',
        useStatus: -1
      },
      get_type_option: [
        { value: -1, label: '全部' },
        { value: 1, label: '未使用' },
        { value: 2, label: '已使用' },
        { value: 3, label: '已过期' },
        { value: 4, label: '已废除' }
      ]
      // tableLabel: [
      //   { index: 1, prop: 'username', label: '用户昵称' },
      //   { index: 2, prop: 'mobile', label: '手机号码' },
      //   { index: 3, prop: 'couponName', label: '优惠券名称' },
      //   { index: 4, prop: 'accessMode', label: '领取方式' },
      //   { index: 5, prop: 'scoreNumber', label: '兑换积分数' },
      //   { index: 6, prop: 'isUsed', label: '是否使用' },
      //   { index: 7, prop: 'orderSn', label: '使用订单号' },
      //   { index: 8, prop: 'validityTime', label: '有效期' },
      //   { index: 9, prop: 'createTime', label: '领取时间' },
      //   { index: 10, prop: 'usedTime', label: '使用时间' },
      //   { index: 11, prop: 'delflag', label: '操作' }
      // ]
    }
  },
  mounted () {
    this.id = this.$route.query.id
    // 初始化数据
    this.initDataList()
  },
  methods: {
    // 领取明细列表
    initDataList () {
      this.requestParams.id = this.id
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      this.requestParams.mobile = this.searchData.mobile
      this.requestParams.userName = this.searchData.userName
      this.requestParams.useStatus = this.searchData.useStatus
      couponGetDetail(this.requestParams).then(res => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          // this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },

    // 表格处理数据
    handleData (data) {
      data.map((item, index) => {
        if (item.accessMode === 0) {
          item.accessMode = '发放'
        } else {
          item.accessMode = '领取'
        }
        item.validityTime = `${item.startTime} 至  ${item.endTime}`
      })
      this.tableData = data
    },

    // 删除明细
    deleteCoupon (id) {
      this.$confirm('是否删除该张优惠券明细?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCouponDetail(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '删除成功!' })
            this.initDataList()
          }
        }).catch(() => {
          this.$message.info({ message: '已取消删除' })
        })
      })
    },

    // 个人信息跳转
    infoClickHandler () {

    },

    foramtUseStatus (data) {
      switch (data) {
        case 2:
          return '是'
        case 3:
          return '已过期'
        case 4:
          return '已废除'
        default:
          return '否'
      }
    },
    foramtGetType (data) {
      switch (data) {
        case 2:
          return '活动送券'
        case 3:
          return '支付送券'
        case 4:
          return '优惠券礼包'
        case 5:
          return '好友帮助砍价发券'
        default:
          return '直接领取'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    .filters {
      display: flex;
      line-height: 32px;
      margin-bottom: 10px;
      background-color: #fff;
      padding: 10px 15px 10px 0;
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
    .table_box {
      background-color: #fff;
      padding: 15px;

      .operation {
        display: flex;
        justify-content: space-around;
        > .item {
          font-size: 22px;
          color: #66b1ff;
          cursor: pointer;
        }
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
</style>
