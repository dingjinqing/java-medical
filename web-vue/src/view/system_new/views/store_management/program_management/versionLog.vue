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
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-input
        v-model="text"
        placeholder="请输入appid"
        size="small"
        class="search-input ml-6"
      />
      <el-button
        size="small"
        class="ml-6"
        type="primary"
      >搜索</el-button>
    </div>

    <el-table
      class="version-log mt-10"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="time"
        label="时间"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="app_id"
        label="appid"
        align="center"
        width="200"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        align="center"
        label="昵称"
      >
      </el-table-column>
      <el-table-column
        prop="content"
        align="center"
        min-width="500"
        label="日志内容"
      >
      </el-table-column>
      <el-table-column
        prop="version"
        align="center"
        label="小程序版本"
      >
      </el-table-column>
      <el-table-column
        prop="operation"
        align="center"
        label="操作"
      >
      </el-table-column>
    </el-table>

    <div class="clearfixed pagination-wrap">
      <el-pagination
        class="flr"
        @current-change="handleChangePage"
        :current-page="page"
        :page-size="20"
        layout="total, sizes, prev, pager, next, jumper"
        :total="400"
      >
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
      tableData: [
        {
          time: '2019-08-07 14:03:35',
          app_id: 'wxeaeb5c37a376f415',
          name: '微铺宝b2c商城',
          content: '提交审核成功',
          version: '1.28.3',
          operation: '查看详情'
        },
        {
          time: '2019-08-07 14:03:35',
          app_id: 'wxeaeb5c37a376f415',
          name: '微铺宝b2c商城',
          content: '提交审核成功',
          version: '1.28.3',
          operation: '查看详情'
        },
        {
          time: '2019-08-07 14:03:35',
          app_id: 'wxeaeb5c37a376f415',
          name: '微铺宝b2c商城',
          content: '提交审核成功',
          version: '1.28.3',
          operation: '查看详情'
        }
      ]
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
