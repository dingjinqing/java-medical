<template>
  <div class="allGoodsFooter">
    <!-- 左侧操作 -->
    <section>
      <span>全选</span>
      <el-button
        plain
        type="primary"
        size="small"
        @click="handleShelf"
      >上架</el-button>
      <el-button
        plain
        type="primary"
        size="small"
      >删除</el-button>
      <el-button
        plain
        type="primary"
        size="small"
      >批量设置</el-button>
      <!-- 批量导出 -->
      <el-select
        style="width:150px"
        size="small"
        v-model="value"
        filterable
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </section>
    <!-- 右侧分页 -->
    <section>
      <pagination
        :total="total"
        :currentPage.sync="list.currentPage"
        :pageRows.sync="list.pageRows"
        @pagination="getList"
      />
    </section>
  </div>
</template>
<script>
import pagination from '@/components/admin/pagination/pagination'
import { brandAllGetRequest } from '@/api/admin/brandManagement'
export default {
  name: 'allGoodsFooter',
  components: { pagination },
  data () {
    return {
      value: '批量导出',
      options: [
        { value: '1', label: '批量导出' },
        { value: '2', label: '批量导出筛选结果' },
        { value: '3', label: '批量导出勾选结果' }
      ],
      total: 0,
      list: {
        currentPage: 1,
        pageRows: 5
      }
    }
  },
  created () {
    // this.getList()
  },
  methods: {
    // 上架
    handleShelf () {
      this.getList()
    },
    // 获取表格数据
    getList () {
      brandAllGetRequest(this.list).then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(content.page)
          console.log(content.dataList)
          this.list = content.page
          this.total = content.page.totalRows
        }
      }).catch(err => console.log(err))
    }
  }
}
</script>
<style lang="css" scoped>
.allGoodsFooter {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
