<template>
  <div class="actDetails">
    <!-- 筛选条件部分 -->
    <div class="mainContent">
      <div
        style="display: flex"
        class="item"
      >
        <span>{{$t('payReward.getTime')}}</span>
        <div :style="{'display': 'flex'}">
          <el-date-picker
            v-model="params.receiveTimeBegin"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
            :placeholder="$t('payReward.selectStartTime')"
            style="width:150px"
          >
          </el-date-picker>
          <span class="to">{{$t('payReward.to')}}</span>
          <el-date-picker
            v-model="params.receiveTimeEnd"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
            :placeholder="$t('payReward.selectEndTime')"
            style="width:150px"
          >
          </el-date-picker>
        </div>
      </div>
      <div>
        <span>{{$t('payReward.mobilePhone')}}</span>
        <el-input
          v-model="params.mobile"
          class="inputBox"
          size="small"
          :placeholder="$t('payReward.mobilePlaceholder')"
        ></el-input>
      </div>
      <div>
        <span>{{$t('payReward.nickName')}}</span>
        <el-input
          v-model="params.userName"
          class="inputBox"
          size="small"
          :placeholder="$t('payReward.inputNickName')"
        ></el-input>
      </div>
      <div>
        <span>{{$t('payReward.activityReward')}}</span>
        <el-select
          v-model="params.awardType"
          :placeholder="$t('payReward.pleaceSelect')"
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
        >{{$t('payReward.query')}}</el-button>
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
          :label='$t("payReward.userId")'
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="username"
          :label="$t('payReward.nickName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          :label="$t('payReward.mobilePhone')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="orderSn"
          :label="$t('payReward.order')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="giftType"
          :label="$t('payReward.activityReward')"
          align="center"
          :formatter="transformText"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          :label="$t('payReward.getTime')"
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
