<template>
  <div class="payReward">
    <div class="mainContent">
      <!-- tab切换导航 -->
      <statusTab
        v-model="param.navType"
        :activityName="activityName"
        :standard="true"
      />

      <!-- 添加支付有礼活动 -->
      <div class="wrapper">
        <el-button
          type="primary"
          @click="addActivity"
        >添加支付有礼活动</el-button>
        <div class="right">注：同一时间段仅会开展一个优先级最高的支付有礼活动</div>
      </div>
    </div>

    <!-- tab表格内容部分 -->
    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="activityNames"
          label="活动名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="name"
          label="触发条件"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="timeType"
          label="活动有效期"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="awardContentList"
          label="活动奖励"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="actFirst"
          label="优先级"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="status"
          label="活动状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="pageParams"
          @pagination="seacherList"
        />
      </div>
    </div>

  </div>
</template>

<script>
import { payRewardList } from '@/api/admin/marketManage/payReward.js'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: { statusTab, pagination },
  mounted () {
    this.langDefault()
  },
  watch: {
    'param.navType' (n, o) {
      this.seacherList()
    }
  },
  created () {
    this.seacherList()
  },
  data () {
    return {
      activityName: '支付有礼',
      tableData: [],
      pageParams: {},
      param: {
        'navType': 1,
        'currentPage': 1,
        'pageRows': 20
      }
    }
  },

  methods: {
    // 添加支付有礼活动
    addActivity () {
      this.$router.push({
        name: 'addPayRewardAct'
        // name: 'activityDetails'
      })
    },
    // tab对应分类查询数据
    seacherList () {
      payRewardList(this.param).then((res) => {
        const { error } = res
        if (error === 0) {
          this.handleData(res.content)
          console.log(res.content)
        }
      })
    },
    handleData (data) {
      console.log(data)
      this.tableData = data.dataList
      console.log(this.tableData)
    }
  }

}

</script>
<style lang="scss" scoped>
.payReward {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .mainContent {
    position: relative;
    background-color: #fff;
    padding: 10px 20px;
    .wrapper {
      display: flex;
      justify-content: space-between;
      .right {
        color: #999;
        margin-top: 10px;
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
    padding: 10px 20px 20px;
  }
}
</style>
