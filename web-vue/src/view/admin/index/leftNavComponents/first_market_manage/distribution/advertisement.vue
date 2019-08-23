<template>
  <div class="content">

    <div class="main">
      <div class="create">
        <span>创建时间</span>
        <el-date-picker
          class="timeSelect"
          v-model="createTime"
          type="date"
          placeholder="选择日期"
        >
        </el-date-picker>
        <span>至</span>
        <el-date-picker
          class="timeSelect"
          v-model="createTime"
          type="date"
          placeholder="选择日期"
        >
        </el-date-picker>
      </div>
      <div class="end">
        <span>最后修改时间</span>
        <el-date-picker
          class="timeSelect"
          v-model="createTime"
          type="date"
          placeholder="选择日期"
        >
        </el-date-picker>
        <span>至</span>
        <el-date-picker
          class="timeSelect"
          v-model="createTime"
          type="date"
          placeholder="选择日期"
        >
        </el-date-picker>
      </div>
      <div class="ad">
        <span>推广语内容</span>
        <el-input class="adInput"></el-input>
      </div>
      <el-button
        type="primary"
        size="small"
      >筛选</el-button>
    </div>
    <el-button
      size="small"
      type="primary"
      class="addAd"
    >添加推广语</el-button>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="推广语标签"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="推广语内容"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="创建时间"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="更新时间"
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
      <div class="footer">
        <span>当前页面1/1，总记录4条</span>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="20"
          layout="prev, pager, next, jumper"
          :total="4"
        >
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>

export default {
  data () {
    return {
      createTime: '',
      currentPage: null
    }
  },
  methods: {
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    }
  }

}

</script>
<style lang="scss" scoped>
.main {
  display: flex;
  .timeSelect {
    width: 150px;
  }
  .adInput {
    width: 150px;
  }
  span {
    height: 30px;
    line-height: 30px;
  }
  .create {
    display: flex;
  }
  .end {
    display: flex;
  }
  .addAd {
    margin: 20px 0 0;
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
  padding: 10px 20px 10px 20px;
}
</style>
