<template>
  <div class="content">

    <div class="main">
      <el-form label-width="100px">
        <el-form-item
          :label="$t('lotteryDraw.nickName') + '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.nickName"
            :placeholder="$t('lotteryDraw.nickNameTip')"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.startTime') + '：'"
          class="item"
        >
          <el-date-picker
            size="small"
            v-model="requestParams.startTime"
            type="date"
            clearable
            class="inputWidth"
             value-format="yyyy-MM-dd HH:mm:ss"
            :placeholder="$t('actionRecord.startTime')"
          >
          </el-date-picker>
          <span>{{ $t('lotteryDraw.to') }}</span>
          <el-date-picker
            size="small"
            v-model="requestParams.endTime"
            type="date"
            clearable
            class="inputWidth"
            value-format="yyyy-MM-dd [23]:[59]:[59]"
            default-time="23:59:59"
            :placeholder="$t('actionRecord.endTime')"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.orderSn') + '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.orderSn"
            :placeholder="$t('lotteryDraw.orderSnTip')"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
      </el-form>
      <el-form label-width="100px">
        <el-form-item
          :label="$t('lotteryDraw.mobile') + '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.mobile"
            :placeholder="$t('lotteryDraw.mobileTip')"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.minInviteUserCount') + '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.minInviteUserCount"
            clearable
            class="inputWidth"
             type="number"
          ></el-input>
          {{ $t('lotteryDraw.to') }}
          <el-input
            size="small"
            v-model="requestParams.maxInviteUserCount"
            clearable
            class="inputWidth"
            type="number"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.groupId') + '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.groupId"
            :placeholder="$t('lotteryDraw.groupIdTip')"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
      </el-form>
      <el-form label-width="100px">
        <el-form-item
          :label="$t('lotteryDraw.grouped') + '：'"
          class="item"
        >
          <el-select
            size="small"
            v-model="requestParams.grouped"
            class="inputWidth"
            :placeholder="$t('lotteryDraw.groupedTip')"
          >
            <el-option
              v-for="item in $t('lotteryDraw.statusList')"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.isGrouper') + '：'"
          class="item"
        >
          <el-select
            size="small"
            v-model="requestParams.isGrouper"
            class="inputWidth"
            :placeholder="$t('lotteryDraw.groupedTip')"
          >
            <el-option
              v-for="item in $t('lotteryDraw.groupList')"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-button
          size="small"
          type="primary"
          class="item"
          style="margin-left: 10px;"
          @click="initDataList"
        >{{ $t('lotteryDraw.select') }}</el-button>
        <el-button
          size="small"
          type="primary"
          class="item"
          style="margin-left: 10px;"
          @click="resetDataList"
        >{{ $t('lotteryDraw.resetSelect') }}</el-button>
      </el-form>
    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          :label="$t('lotteryDraw.username')"
          prop="userName"
          align="center"
        >
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" @click="viewUserHanlder(scope.row.userId)">{{scope.row.userName}}</el-link>
         </template>
        </el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.mobile')"
          prop="mobile"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.startTime')"
          prop="openTime"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.orderSn')"
          prop="orderSn"
          align="center"
        >
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" @click="getOrder(scope.row.orderSn)">{{scope.row.orderSn}}</el-link>
         </template>
        </el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.isGrouper')"
          align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.isGrouper === true">{{ $t('lotteryDraw.isYes') }}</span>
            <span v-if="scope.row.isGrouper === false">{{ $t('lotteryDraw.isNo') }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.groupId')"
          prop="groupId"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.groupTime')"
          prop="endTime"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.codeCount')"
          prop="drawNum"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.minInviteUserCount')"
          prop="inviteNum"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>

  </div>
</template>
<script>
// 引入组件
import pagination from '@/components/admin/pagination/pagination.vue'
import { userLotteryList } from '@/api/admin/marketManage/lotteryDraw.js'
export default {

  components: {
    pagination
  },
  data () {
    return {
      // 成团状态
      statusList: this.$t('lotteryDraw.statusList'),
      // 是否团长
      groupList: this.$t('lotteryDraw.groupList'),
      loading: false,
      pageParams: {}, // 分页
      requestParams: {
        nickName: '', // 昵称
        startTime: '',
        endTime: '',
        orderSn: '', // 订单号
        mobile: '', // 手机号
        minInviteUserCount: null, // 最小邀请人数
        maxInviteUserCount: null, // 最大邀请人数
        groupId: '', // 团ID
        grouped: '', // 成团状态
        isGrouper: '' // 团长id
      },
      tableData: [] // 表格数据
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.requestParams.groupDrawId = Number(this.$route.query.id)
      // 初始化数据
      this.initDataList()
    }
    if (this.$route.query.groupId > 0) {
      this.requestParams.groupId = this.$route.query.groupId
      this.requestParams.groupDrawId = this.$route.query.groupDrawId
      this.initDataList()
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows

      userLotteryList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 查看用户明细
    viewUserHanlder (tagId) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: tagId
        }
      })
    },
    getOrder (orderSn) {
      // 跳转订单详情页面
      this.$router.push({
        path: '/admin/home/main/orders/info',
        query: {
          orderSn: orderSn
        }
      })
    },
    resetDataList () {
      this.requestParams = {
        nickName: '', // 昵称
        startTime: '',
        endTime: '',
        orderSn: '', // 订单号
        mobile: '', // 手机号
        minInviteUserCount: null, // 最小邀请人数
        maxInviteUserCount: null, // 最大邀请人数
        groupId: '', // 团ID
        grouped: '', // 成团状态
        isGrouper: '' // 团长id
      }
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
    .wrapper {
      .el-button {
        margin-left: 5px;
      }
    }
    .item {
      display: inline-block;
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
  padding: 15px;
}
.inputWidth {
  width: 175px;
}
.el-form-item {
  margin-bottom: 1px;
}
/deep/ .el-form-item__label {
  padding: 0;
}
.el-row {
  margin-bottom: 2px !important;
}
</style>
