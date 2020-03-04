<template>
  <div class="content">

    <div class="main">
      <el-form label-width="100px">
        <el-form-item
          :label="$t('lotteryDraw.groupName') + '：'"
          class="item"
        >
          <el-input
            size="small"
            v-model="requestParams.username"
            :placeholder="$t('lotteryDraw.groupNameTip')"
            maxlength="11"
            clearable
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.openGroupTime') + '：'"
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
          <span>{{ this.$t('lotteryDraw.to') }}</span>
          <el-date-picker
            size="small"
            v-model="requestParams.endTime"
            type="datetime"
            clearable
            class="inputWidth"
            value-format="yyyy-MM-dd 00:00:00"
            default-time="23:59:59"
            :placeholder="$t('actionRecord.endTime')"
          >
          </el-date-picker>
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
              v-for="item in statusList"
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
        >{{ this.$t('lotteryDraw.select') }}</el-button>
        <el-button
          size="small"
          type="primary"
          class="item"
          style="margin-left: 10px;"
          @click="resetDataList"
        >{{ this.$t('lotteryDraw.resetSelect') }}</el-button>
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
          :label="$t('lotteryDraw.groupId')"
          prop="groupId"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.joinUserCount')"
          prop="userCount"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.goodsId')"
          prop="goodsName"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.openGroupTime')"
          prop="openTime"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.groupName')"
          prop="grouperName"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.grouperMobile')"
          prop="mobile"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('lotteryDraw.groupTime')"
          prop="endTime"
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
import { groupLotteryList } from '@/api/admin/marketManage/lotteryDraw.js'
export default {

  components: {
    pagination
  },
  data () {
    return {
      // 成团状态
      statusList: [{
        value: true,
        label: '已成团'
      }, {
        value: false,
        label: '未成团'
      }],
      loading: false,
      pageParams: {}, // 分页
      requestParams: {
        username: '',
        startTime: '',
        endTime: '',
        groupId: '', // 团ID
        mobile: '', // 手机号
        grouped: null // 成团状态
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
      groupLotteryList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },

    // 重置筛选
    resetDataList () {
      this.requestParams = {
        username: '',
        startTime: '',
        endTime: '',
        groupId: '',
        mobile: '',
        grouped: null
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
