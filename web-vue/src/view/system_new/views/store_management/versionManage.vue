<template>
  <div class="version-manage">
      <div class="btn-wrap">
        <el-button type="primary" size="small">
          同步微信开放平台小程序代码模板
        </el-button>
      </div>

    <el-table
      class="version-manage-table"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        prop="date"
        label="模板ID"
        align="center"
        >
      </el-table-column>
      <el-table-column
        prop="name"
        label="模板版本号"
        align="center"
        >
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="当前包版本">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="模板描述">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="模板添加时间">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="开发appid">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="开发app名称">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="开发者">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="是否删除">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="是否当前使用">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="操作">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'versionManage',
  data () {
    return {
      tableData: [

      ]
    }
  }
}
</script>

<style scoped lang="scss">
.version-manage {
  .btn-wrap {
    background: #fff;
    padding: 15px;

    .el-button--primary {
      background: #7495bf;
      border-color: #7495bf;

      &:active {
        background: #6f97c2;
        border-color: #6f97c2;
      }
    }
  }
}

.version-manage {
  .version-manage-table {
    margin-top: 10px;
  }
}
</style>
