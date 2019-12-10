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
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="活动名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="活动类型"
          align="center"
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
        </el-table-column>
        <el-table-column
          prop=""
          label="有效期"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
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
// import {}
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: { statusTab, pagination },
  data () {
    return {
      activityName: '满折满减',
      params: {
        navType: '1'
      }
    }
  },
  methods: {
    tableDataSearch () {

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
