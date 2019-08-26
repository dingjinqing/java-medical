<template>
  <div>
    <section
      class="pagination"
      v-show="pageParams.totalRows>0"
    >
      <!-- 当前页 -->
      <section class="current">当前页面 {{pageParams.currentPage}}/{{Math.ceil(pageParams.totalRows/pageParams.pageRows)}}</section>
      <!-- 分页信息 -->

      <section>
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
      type: Object
    },
    pageSizes: {
      type: Array,
      default () {
        return [5, 10, 15, 20]
      }
    },
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    },
    background: {
      type: Boolean,
      default: true
    },
    hidden: {
      type: Boolean,
      default: false
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
    handleSizeChange (val) {
      // console.log({ currentPage: this.currentPage, pageRows: val })
      this.$emit('pagination', { currentPage: this.currentPage, pageRows: val })
    },
    handleCurrentChange (val) {
      // console.log({ currentPage: val, pageRows: this.pageSize })
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
