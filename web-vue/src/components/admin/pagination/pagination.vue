<template>
  <div class="pagination">
    <span class="now">当前页面{{currentPage}}/{{pageCount}}，总记录{{totalRows}}条 </span>
    <el-button
      plain
      size="small"
      @click="firstPage"
    >首页</el-button>
    <el-button
      plain
      size="small"
      @click="prePage"
    >上一页</el-button>
    <el-button
      plain
      size="small"
      @click="nextPage"
    >下一页</el-button>
    <el-button
      plain
      size="small"
      @click="lastPage"
    >末页</el-button>
    <el-button
      plain
      size="small"
      @click="toPage"
    >跳转</el-button>
  </div>
</template>

<script>
export default {
  name: 'pagination',
  props: ['currentPage', 'pageCount', 'totalRows'],
  data () {

  },
  methods: {
    firstPage () {

    },
    prePage () {

    },
    nextPage () {

    },
    lastPage () {

    }
  }
}
</script>

<style scope>
.now {
  color: #666;
}
</style>
