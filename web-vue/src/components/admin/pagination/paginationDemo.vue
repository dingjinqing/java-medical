<!--
* 分页组件Demo
-->
<template>
  <div>
    <el-button @click="handleClick">测试分页</el-button>
    <section class="table">
      <el-table
        border
        :data="tableData"
        style="width: 100%"
      >
        <el-table-column
          prop="brandName"
          label="品牌名"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="180"
        >
        </el-table-column>
      </el-table>
    </section>
    <pagination
      :page-params.sync="pageParams"
      @pagination="fetchDataList"
    />
  </div>
</template>
<script>
// 引入测试数据
import { brandAllGetRequest } from '@/api/admin/brandManagement.js'
// import { brandAddRequest } from '@/api/admin/brandManagement.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      data: [],
      pageParams: {},
      tableData: []
    }
  },
  created () {
    this.fetchDataList()
  },
  methods: {
    fetchDataList () {
      brandAllGetRequest(this.pageParams).then(res => {
        const { error, content: { dataList, page } } = res
        if (error === 0) {
          // 打印页码数据
          // console.log(page)
          // 打印内容
          // console.log(dataList)
          this.pageParams = page
          this.tableData = dataList
        }
      }).catch(err => console.log(err))
    },
    handleClick () {
      console.log(this.pageParams)
      // 获取全部
      // 添加单个
      // brandAddRequest({
      //   'brandName': '测试002',
      //   'ename': 'english',
      //   'first': 1
      // }).then(res => { console.log(res) }).catch(err => console.log(err))
    }

  }
}
</script>
<style lang="" scoped>
</style>
