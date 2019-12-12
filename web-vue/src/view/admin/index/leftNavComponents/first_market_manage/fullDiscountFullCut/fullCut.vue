<!--
***  满折满减列表页面
-->
<template>
  <div class="fullCut">
    <div class="mainContent">
      <!-- tab切换内容部分 -->
      <statusTab
        v-model="params.state"
        :activityName="activityName"
        :standard="true"
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
          :formatter="formatStatus"
        >
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
          prop="operate"
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                content="编辑"
                placement="top"
                v-if="scope.row.status === 1 || scope.row.status === 2"
              >
                <span
                  class="el-icon-edit-outline"
                  @click="editHandle(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="启用"
                placement="top"
                v-if=" scope.row.status === 4"
              >
                <span
                  class="el-icon-circle-check"
                  @click="openHandle(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="停用"
                placement="top"
                v-if="scope.row.status === 1 || scope.row.status === 2"
              >
                <span
                  class="el-icon-circle-close"
                  @click="closeHandle(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="删除"
                placement="top"
                v-if="scope.row.status === 0 || scope.row.status === 3 || scope.row.status === 4"
              >
                <span
                  class="el-icon-delete"
                  @click="deleteHandle(scope.row.id)"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="params"
          @pagination="tableDataSearch"
        />
      </div>

    </div>
  </div>
</template>

<script>
import { fullCutTableDataSearchApi, memberCardActivity } from '@/api/admin/marketManage/fullDiscountFullCut'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: { statusTab, pagination },
  data () {
    return {
      activityName: '满折满减',
      params: {
        'state': 1,
        'currentPage': 1,
        'pageRows': 20
      },
      tableData: []
    }
  },
  watch: {
    'params.state' (n, o) {
      console.log(123)
      this.tableDataSearch()
    }
  },
  created () {
    this.tableDataSearch()
  },
  methods: {
    tableDataSearch () {
      fullCutTableDataSearchApi(this.params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.params = Object.assign(res.content.page, this.params)
          this.tableData = res.content.dataList
          this.tableData.map((item, index) => {
            item.statusText = this.getActStatusString(item.status)
          })
        }
      }).catch(err => console.log(err))
    },
    formatStatus (row, column) {
      switch (row.type) {
        case 1: row.type = '每满减'
          break
        case 2: row.type = '满减'
          break
        case 3: row.type = '满折'
          break
        case 4: row.type = '仅第X件打折'
          break
      }
      return row.type
    },
    addActivity () {
      this.$router.push({
        name: 'fullCutActivity'
      })
    },
    editHandle () {

    },
    openHandle () {

    },
    closeHandle () {

    },
    deleteHandle () {

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
    .opt {
      text-align: center;
      color: #5a8bff;
      span {
        cursor: pointer;
        font-size: 22px;
      }
    }
  }
}
</style>
