<template>
  <div class="fullCut">
    <div class="mainContent">
      <!-- tab切换内容部分 -->
      <statusTab
        v-model="params.navType"
        :activityName="activityName"
        :standard="true"
        @click="tableDataSearch()"
      />

      <div class="addActivity">
        <el-button
          type="primary"
          size="small"
          @click="addActivity()"
        >添加满折满减活动</el-button>
      </div>
    </div>

    <!-- 表格内容部分 -->
    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="actName"
          label="活动名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="type"
          label="活动类型"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.type | formatStatus(item.status)}}
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          label="优先级"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="优惠规则"
          align="center"
        >
          <template slot-scope="scope">
            <div
              v-for="(item, index) in scope.row.condition"
              :key="index"
            >
              满{{item.fullMoney}}元减{{item.reduceMoney}}
              <!-- 满{{item.fullMoney}}元打{{item.discount}}折
              满{{item.fullMoney}}件减{{item.reduceMoney}}
              每满{{item.fullMoney}}件减{{iteme.reduceMoney}} -->
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          label="有效期"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br />至<br />{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="statusText"
          label="状态"
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

    </div>
  </div>
</template>

<script>
import { fullCutTableDataSearchApi } from '@/api/admin/marketManage/fullDiscountFullCut'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: { statusTab, pagination },
  data () {
    return {
      activityName: '满折满减',
      params: {
        navType: '1'
      },
      tableData: []
    }
  },
  mounted () {
    this.tableDataSearch()
  },
  methods: {
    tableDataSearch () {
      let obj = {
        'currentPage': 1
      }
      fullCutTableDataSearchApi(obj).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList
          console.log(this.tableData, 'tableData')
          this.tableData.map((item, index) => {
            item.statusText = this.getActStatusString(item.status)
          })
        }
      }).catch(err => console.log(err))
    },
    addActivity () {
      this.$router.push({
        name: 'fullCutActivity'
      })
    }
  }

}

</script>
<style lang="scss" scoped>
.fullCut {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  font-size: 14px;
  .mainContent {
    padding: 15px;
    background: #fff;
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
    padding: 15px;
    margin-top: 10px;
    background-color: #fff;
  }
}
</style>
