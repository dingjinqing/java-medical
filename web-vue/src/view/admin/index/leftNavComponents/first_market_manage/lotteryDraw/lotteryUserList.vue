<template>
  <div class="content">

    <div class="main">
      <el-form label-width="100px">
        <el-form-item
          label="用户昵称："
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.nickName"
            placeholder="请输入用户昵称"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="参与时间："
          class="item"
        >
          <el-date-picker
            size="small"
            v-model="requestParams.startTime"
            type="datetime"
            clearable
            class="inputWidth"
            value-format="yyyy-MM-dd 00:00:00"
            :placeholder="$t('actionRecord.startTime')"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            size="small"
            v-model="requestParams.endTime"
            type="datetime"
            clearable
            class="inputWidth"
            value-format="yyyy-MM-dd 23:59:59"
            :placeholder="$t('actionRecord.endTime')"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="订单号："
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.orderSn"
            placeholder="请输入订单号"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="手机号："
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.mobile"
            placeholder="请输入手机号"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="邀请用户数："
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.minInviteUserCount"
            clearable
            class="inputWidth"
          ></el-input>
          至
          <el-input
            size="small"
            v-model="requestParams.maxInviteUserCount"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="团ID："
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.groupId"
            placeholder="请输入团ID"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="成团状态："
          class="item"
        >
          <el-select
            size="small"
            v-model="requestParams.grouped"
            class="inputWidth"
            placeholder="请选择"
          >
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="是否团长："
          class="item"
        >
          <el-select
            size="small"
            v-model="requestParams.isGrouper"
            class="inputWidth"
            placeholder="请选择"
          >
            <el-option
              v-for="item in groupList"
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
        >筛选</el-button>
        <el-button
          size="small"
          type="primary"
          class="item"
          style="margin-left: 10px;"
        >重置筛选</el-button>
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
          label="昵称"
          prop="username"
          align="center"
        ></el-table-column>
        <el-table-column
          label="手机号"
          prop="mobile"
          align="center"
        ></el-table-column>
        <el-table-column
          label="参与时间"
          prop="createTime"
          align="center"
        ></el-table-column>
        <el-table-column
          label="订单号"
          prop="orderSn"
          align="center"
        ></el-table-column>
        <el-table-column
          label="是否团长"
          align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.isGrouper === true">是</span>
            <span v-if="scope.row.isGrouper === false">否</span>
          </template>
        </el-table-column>
        <el-table-column
          label="团ID"
          prop="groupId"
          align="center"
        ></el-table-column>
        <el-table-column
          label="成团时间"
          prop=""
          align="center"
        ></el-table-column>
        <el-table-column
          label="抽奖码数量"
          prop="codeCount"
          align="center"
        ></el-table-column>
        <el-table-column
          label="邀请用户数"
          prop="inviteUserNum"
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
      statusList: [{
        value: '1',
        label: '已成团'
      }, {
        value: '2',
        label: '未成团'
      }],
      // 是否团长
      groupList: [{
        value: '1',
        label: '是'
      }, {
        value: '2',
        label: '否'
      }],
      loading: false,
      pageParams: {}, // 分页
      requestParams: {
        nickName: '', // 昵称
        startTime: '',
        endTime: '',
        orderSn: '', // 订单号
        mobile: '', // 手机号
        minInviteUserCount: '', // 最小邀请人数
        maxInviteUserCount: '', // 最大邀请人数
        groupId: '', // 团ID
        grouped: '', // 成团状态
        isGrouper: '' // 团长id
      },
      tableData: [] // 表格数据
    }
  },
  mounted () {
    if (this.$route.query.id > 0) {
      // 初始化数据
      this.initDataList()
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.groupDrawId = this.$route.query.id
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      userLotteryList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.loading = false
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
