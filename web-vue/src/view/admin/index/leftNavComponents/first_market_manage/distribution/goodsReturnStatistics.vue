<template>
  <div>
    <div class="searchInfo">
      <el-select
        size="small"
        v-model="value"
        placeholder="请选择"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-input
        size="small"
        placeholder="请输入内容"
        prefix-icon="el-icon-search"
        v-model="input2"
        class="input"
      >
      </el-input>
      <el-button
        type="primary"
        size="small"
        style="margin: 0 20px 0 20px"
      >筛选</el-button>
      <el-button size="small">导出</el-button>
    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="商品名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="商品价格"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="商品所属分类"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="商品总销量"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="已返利总数量"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="已返利总佣金"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
        </el-table-column>
      </el-table>
    </div>
  </div>

</template>

<script>

export default {
  data () {
    return {
      value: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      input2: ''
    }
  }

}

</script>
<style lang="scss" scoped>
.searchInfo {
  display: flex;
  .input {
    width: 200px;
    margin-left: 50px;
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
