<template>
  <div class="actDetails">
    <!-- 筛选条件部分 -->
    <div class="mainContent">
      <div
        style="display: flex"
        class="item"
      >
        <span>领取时间</span>
        <div :style="{'display': 'flex'}">
          <el-date-picker
            v-model="params.receiveTimeBegin"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
            placeholder="选择开始时间"
            style="width:150px"
          >
          </el-date-picker>
          <span class="to">至</span>
          <el-date-picker
            v-model="params.receiveTimeEnd"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
            placeholder="选择结束时间"
            style="width:150px"
          >
          </el-date-picker>
        </div>
      </div>
      <div>
        <span>手机号</span>
        <el-input
          v-model="params.mobile"
          class="inputBox"
          size="small"
          placeholder="请输入手机号"
        ></el-input>
      </div>
      <div>
        <span>用户昵称</span>
        <el-input
          v-model="params.userName"
          class="inputBox"
          size="small"
          placeholder="请输入用户昵称"
        ></el-input>
      </div>
      <div>
        <span>活动奖励</span>
        <el-select
          v-model="params.awardType"
          placeholder="请选择"
          size="small"
          class="inputBox"
          clearable
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <div class="item">
        <el-button
          size="small"
          type="primary"
          @click="handleSearch"
        >查询</el-button>
      </div>
    </div>

    <!-- tab表格内容部分 -->
    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        border
        :data="tableData"
        style="width: 100%"
      >
        <el-table-column
          prop="userId"
          label="用户ID"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="username"
          label="用户昵称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="手机号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="orderSn"
          label="订单号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="giftType"
          label="活动奖励"
          align="center"
          :formatter="transformText"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="领取时间"
          align="center"
        >
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="pageParams"
          @pagination="initData()"
        />
      </div>
    </div>

  </div>
</template>

<script>
import { payRewardDetails } from '@/api/admin/marketManage/payReward.js'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: { pagination },
  mounted () {
    if (this.$route.query.id > 0) {
      this.params.id = this.$route.query.id
      console.log(this.params.id)
      this.initData()
    }
  },
  data () {
    return {
      options: [{
        value: '0',
        label: '无奖品'
      }, {
        value: '1',
        label: '普通优惠券'
      }, {
        value: '2',
        label: '分裂优惠券'
      }, {
        value: '3',
        label: '幸运大抽奖'
      }, {
        value: '4',
        label: '余额'
      }, {
        value: '5',
        label: '商品'
      }, {
        value: '6',
        label: '积分 '
      }, {
        value: '7',
        label: '自定义'
      }],
      tableData: [],
      pageParams: {},
      params: {
        'id': null,
        'mobile': '',
        'receiveTimeBegin': '',
        'receiveTimeEnd': '',
        'userName': '',
        'awardType': '',
        'currentPage': 1,
        'pageRows': 20
      }

    }
  },
  methods: {
    // 页面进来初始化获取获取
    initData () {
      payRewardDetails(this.params).then(res => {
        console.log(res)
        this.tableData = res.content.dataList
        this.pageParams = res.content.page
      }).catch(err => console.log(err))
    },
    // 查询按钮点击事件
    handleSearch () {
      this.initData()
    },
    // 活动奖励数组转化为文字
    transformText (row, col) {
      switch (row.giftType) {
        case 0: row.giftType = '无奖品'
          break
        case 1: row.giftType = '普通优惠券'
          break
        case 2: row.giftType = '分裂优惠券'
          break
        case 3: row.giftType = '幸运大抽奖'
          break
        case 4: row.giftType = '余额'
          break
        case 5: row.giftType = '商品'
          break
        case 6: row.giftType = '积分'
          break
        case 7: row.giftType = '自定义'
          break
      }
      return row.giftType
    }
  }

}

</script>
<style lang="scss" scoped>
.actDetails {
  padding: 10px;
  min-height: 100%;
  height: 100%;
  font-size: 14px;
  .mainContent {
    background: #fff;
    padding: 25px 30px;
    display: flex;
    .item {
      margin-right: 30px;
      span {
        margin-right: 8px;
      }
    }
    :nth-of-type(1) {
      span {
        line-height: 28px;
      }
      .to {
        margin: 0 5px;
      }
    }
    .inputBox {
      width: 150px;
      margin: 0 10px 0 5px;
    }
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }
  .table_list {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 20px;
  }
}
</style>
