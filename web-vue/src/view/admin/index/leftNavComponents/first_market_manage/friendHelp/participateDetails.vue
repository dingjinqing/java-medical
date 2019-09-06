<template>
  <div class="content">

    <div class="main">
      <el-form :inline="true">
        <el-form-item
          size="small"
          label="用户昵称"
        >
          <el-input placeholder="请输入用户昵称" />
        </el-form-item>
        <el-form-item
          size="small"
          label="手机号"
        >
          <el-input placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item
          size="small"
          label="助力活动ID"
        >
          <el-input placeholder="请输入助力活动ID"></el-input>
        </el-form-item>
        <br>
        <el-form-item
          size="small"
          label="是否是新用户"
        >
          <el-select>
            <el-option
              label="全部"
              value="-1"
            ></el-option>
            <el-option
              label="是"
              value="1"
            ></el-option>
            <el-option
              label="否"
              value="0"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-button
          size="small"
          type="primary"
        >筛选</el-button>
        <el-button size="small">导出数据</el-button>
      </el-form>
    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="username"
          label="参与用户昵称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          label="参与用户手机号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="groupId"
          label="是否是新用户"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="groupId"
          label="助力活动ID"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="inviteNum"
          label="活动发起人"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="integration"
          label="助力次数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="isGrouper"
          label="助力值"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="loadData"
      />

    </div>
  </div>
</template>
<script>
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data: function () {
    return {
      username: '',
      mobile: '',
      id: '',
      promoteStatus: '',
      recTime: '',
      orderSn: ''
    }
  },
  mounted () {
    this.loadData()
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
  padding: 10px 20px 0 20px;
}
.paginationfooter {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
</style>
