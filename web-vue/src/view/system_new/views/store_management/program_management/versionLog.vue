<template>
  <div class="version-log">
    <div class="filter-menu">
      <el-select
        v-model="value"
        placeholder="请选择"
        size="small"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-input v-model="text"
                placeholder="请输入appid"
                size="small"
                class="search-input ml-6" />
      <el-button
        size="small"
        class="ml-6"
        type="primary">搜索</el-button>
    </div>

    <el-table
      class="version-log mt-10"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        prop="date"
        label="时间"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="appid"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="昵称">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        min-width="500"
        label="日志内容">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="小程序版本">
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="操作">
      </el-table-column>
    </el-table>

    <div class="clearfixed pagination-wrap">
      <el-pagination
        class="flr"
        @current-change="handleChangePage"
        :current-page="page"
        :page-size="20"
        layout="total, sizes, prev, pager, next, jumper"
        :total="400">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'versionLog',
  data () {
    return {
      page: 1,
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
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      text: '',
      tableData: []
    }
  },
  methods: {
    handleChangePage (page) {
      console.log(`当前页${page}`)
    }
  }
}
</script>

<style scoped lang="scss">
.filter-menu {
  display: flex;
  padding: 10px;
  background: #fff;
}
.search-input {
  width: 200px;
}

</style>
