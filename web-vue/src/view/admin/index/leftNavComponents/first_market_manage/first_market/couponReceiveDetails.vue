<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <div class="filters_item"><span>手机号：</span>
          <el-input
            v-model="searchCondition.mobile"
            placeholder="请输入手机号"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>用户昵称：</span>
          <el-input
            v-model="searchCondition.userName"
            placeholder="请输入用户昵称"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>使用状态：</span>
          <el-select
            v-model="searchCondition.useStatus"
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
          <template v-for="item in tableLabel">
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
                      @click="del(scope.row.id)"
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
          </template>
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
import { deleteCoupon, couponGetDetail } from '@/api/admin/marketManage/couponList.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      loading: false,
      id: '',
      pageParams: {},
      searchCondition: {
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
      ],
      tableData: [],
      tableLabel: [
        { index: 1, prop: 'userName', label: '用户昵称' },
        { index: 2, prop: 'mobile', label: '手机号码' },
        { index: 3, prop: 'couponName', label: '优惠券名称' },
        { index: 4, prop: 'getType', label: '领取方式' },
        { index: 5, prop: 'score', label: '兑换积分数' },
        { index: 6, prop: 'useStatus', label: '是否使用' },
        { index: 7, prop: 'orderSn', label: '使用订单号' },
        { index: 8, prop: 'validityPeriod', label: '有效期' },
        { index: 9, prop: 'getTime', label: '领取时间' },
        { index: 10, prop: 'useTime', label: '使用时间' },
        { index: 11, prop: '', label: '操作' }
      ]
    }
  },
  methods: {
    initDataList () {
      this.pageParams.id = this.id
      couponGetDetail(this.pageParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.page
        }
      })
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
    },
    del (id) {
      let param = {
        'id': id
      }
      this.$confirm('是否废除该张优惠券?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCoupon(param).then((res) => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '废除成功!'
            })
            this.initDataList()
          }
        })
      })
    }
  },
  mounted () {
    this.initDataList()
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
</style>
