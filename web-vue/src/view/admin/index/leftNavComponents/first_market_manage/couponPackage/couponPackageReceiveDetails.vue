<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <div class="filters_item">
          <span>用户昵称：</span>
          <el-input
            v-model="paramsData.username"
            placeholder="请输入用户昵称"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>手机号：</span>
          <el-input
            v-model="paramsData.mobile"
            placeholder="请输入用户手机号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>领取时间：</span>
          <el-date-picker
            v-model="effectiveDate"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
          >
          </el-date-picker>
        </div>
        <div class="filters_item">
          <span>领取方式：</span>
          <el-select
            v-model="paramsData.accessMode"
            size="small"
            class="default_input"
          >
            <el-option
              v-for="item in access_mode_list"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <span>订单号：</span>
          <el-input
            v-model="paramsData.orderSn"
            placeholder="请输入订单号"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList"
            type="primary"
            size="small"
          >筛选</el-button>
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
          <template v-for="(item,index) in tableItem">
            <el-table-column
              :prop="item.prop"
              :label="item.label"
              :key="index"
            >
            </el-table-column>
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
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      currentPage: 1,
      pageParams: {},
      effectiveDate: '',
      paramsData: {
        id: '',
        username: '',
        mobile: '',
        startTime: '',
        endTime: '',
        accessMode: -1,
        orderSn: '',
        currentPage: 1
      },
      tableData: [
        {
          'username': 'sdsa',
          'mobile': '13533333333',
          'accessMode': 0,
          'orderSn': 'qweqwew',
          'createTime': '2019-08-21 17:10:22',
          'voucherAccessCount': 0
        },
        {
          'username': 'sdsa',
          'mobile': '13533333333',
          'accessMode': 0,
          'orderSn': 'qweqwew',
          'createTime': '2019-08-21 17:10:22',
          'voucherAccessCount': 0
        },
        {
          'username': 'sdsa',
          'mobile': '13533333333',
          'accessMode': 0,
          'orderSn': 'qweqwew',
          'createTime': '2019-08-21 17:10:22',
          'voucherAccessCount': 0
        },
        {
          'username': 'sdsa',
          'mobile': '13533333333',
          'accessMode': 0,
          'orderSn': 'qweqwew',
          'createTime': '2019-08-21 17:10:22',
          'voucherAccessCount': 0
        }
      ],
      loading: false,
      tableItem: [
        { prop: 'username', label: '用户昵称' },
        { prop: 'mobile', label: '手机号' },
        { prop: 'accessMode', label: '领取方式' },
        { prop: 'orderSn', label: '订单号' },
        { prop: 'createTime', label: '领取时间' },
        { prop: 'voucherAccessCount', label: '已领取优惠券数量' }
      ],
      access_mode_list: [
        { value: -1, label: '全部' },
        { value: 0, label: '现金' },
        { value: 1, label: '积分' },
        { value: 2, label: '全部' }
      ]
    }
  },
  methods: {
    initDataList () {
      this.loading = false
      console.log(this.paramsData)
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        // TODO: 国际化
        item.vaildDate = `${item.startTime}至${item.endTime}`
        item.statusName = this.getActStatusString(item.status, item.startTime, item.endTime)
        item.accessMode = this.getAccessModeString(item.accessMode)
      })
      this.tableData = data
    },
    getAccessModeString (accessMode) {
      if (accessMode === 0) {
        return '现金购买'
      } else if (accessMode === 1) {
        return '积分购买'
      } else {
        return '直接领取'
      }
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
      padding: 10px 10px 0 0;
      flex-wrap: wrap;
      .filters_item {
        display: flex;
        max-width: 480px;
        margin-left: 15px;
        margin-bottom: 10px;
        > span {
          min-width: 80px;
          font-size: 14px;
        }
      }
    }
    .navBox {
      background-color: #fff;
      padding: 10px;
      margin-bottom: 10px;
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
    .default_input {
      width: 150px;
    }
  }
}
</style>
