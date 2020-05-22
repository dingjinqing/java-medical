<!--
* 列表通用分页组件
* 基于Element UI 的 el-pagination
* @pageParams.totalRows>0 当列表的数据为空时，不显示分页组件
* @author 杨万里
-->
<template>
  <div>
    <section
      class="pagination"
      v-show="pageParams.totalRows>0"
    >
      <!-- 当前页 -->
      <section class="current">{{$t('membershipIntroduction.currentPage')}} {{pageParams.currentPage}}/{{pageParams.pageCount}}</section>
      <!-- 分页信息 -->
      <section>
        <!-- el-pagination -->
        <el-pagination
          :current-page.sync="pageParams.currentPage"
          :page-size.sync="pageParams.pageRows"
          :layout="layout"
          :page-sizes="pageSizes"
          :total="pageParams.totalRows"
          v-bind="$attrs"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </section>
    </section>
  </div>
</template>

<script>

export default {
  name: 'Pagination',
  props: {
    pageParams: {
      type: Object // 接收外部传入的page信息
    },
    pageSizes: {
      type: Array,
      default () {
        return [1, 2, 3, 20] // 每页显示个数选择器的选项设置
      }
    },
    layout: {
      type: String,
      default: 'total, prev, pager, next, jumper' // 组件布局，子组件名用逗号分隔 sizes
    }
  },
  computed: {
    currentPage: {
      get () {
        return this.pageParams.currentPage
      },
      set (val) {
        this.$emit('update:pageParams', val)
      }
    },
    pageSize: {
      get () {
        return this.pageParams.pageSize
      },
      set (val) {
        this.$emit('update:pageParams', val)
      }
    }
  },
  methods: {
    // pageSize 每页的条数/行数改变时触发的函数
    handleSizeChange (val) {
      this.$emit('pagination', { currentPage: this.currentPage, pageRows: val })
    },
    // currentPage 当前页改变时触发的函数
    handleCurrentChange (val) {
      this.$emit('pagination', { currentPage: val, pageRows: this.pageSize })
    }
  }
}
</script>

<style scoped>
.pagination {
  background-color: #fff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 10px 16px;
}
.current {
  color: #606266;
  font-size: 13px;
}
</style>
