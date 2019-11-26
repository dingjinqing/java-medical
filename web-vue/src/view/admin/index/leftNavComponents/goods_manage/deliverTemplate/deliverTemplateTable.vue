<template>
  <div class="tableDataList">
    <!-- 列表的内容 -->
    <section
      class="list_content"
      v-loading="loading"
    >
      <section
        class="list"
        v-for="(item,index) in lists"
        :key="index"
      >
        <section class="table_header">
          <section class="left"></section>
          <section class="title">
            <span>{{item.templateName}}</span>
          </section>
          <section class="right">
            <el-button
              type="text"
              @click="handleCopyTemplate(item.deliverTemplateId)"
            >复制模板</el-button>
            <el-button
              type="text"
              @click="upDateTemplate(item.deliverTemplateId)"
            >修改</el-button>
            <el-button
              type="text"
              @click="handleDelTemplate(item.deliverTemplateId)"
            >删除</el-button>
          </section>
        </section>
        <section class="table_list">
          <!-- 表格 -->
          <el-table
            border
            v-for="(val, key) in item.tableList"
            :key="key"
            :data="val"
          >
            <el-table-column
              prop="area_text"
              label="可配送区域"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="first_num"
              label="首件（件）"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="first_fee"
              label="运费（元）"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="continue_num"
              label="续件（件）"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="continue_fee"
              label="续费（元）"
              align="center"
            >
            </el-table-column>
          </el-table>
        </section>
      </section>

    </section>
    <!-- 分页 -->
    <section class="paginationContainer">
      <pagination
        :pageParams="pageParams"
        @pagination="initData"
      />
    </section>
  </div>
</template>

<script>
import { fetchDeliverTemplateList, deliverDelete, copyDeliverTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
import pagination from '@/components/admin/pagination/pagination'
export default {
  name: `deliverTemplateTable`,
  activated: function () {
    this.initData()
  },
  components: { pagination },
  props: {
    listType: String
  },
  data () {
    return {
      tableList: [], // 表格的数据
      tableData: [], // 表格的数据
      content: ``, // 后台返回的数据
      lists: [],
      pageParams: {},
      loading: true
    }
  },
  mounted () {
    this.initData()
  },
  watch: {
    $router () {
      this.initData()
    }
  },
  methods: {
    // 初始化运费模板数据
    initData () {
      if (this.listType === `list`) {
        // 运费模板列表
        fetchDeliverTemplateList({
          flag: 0,
          page: {
            currentPage: this.pageParams.currentPage,
            pageRows: this.pageParams.pageRows
          }
        }).then(res => {
          const { error, content: { pageResult: { dataList, page } } } = res
          if (error === 0) {
            this.loading = false
            this.pageParams = page
            // 表格数据处理
            dataList.map((item, index) => {
              this.tableData = []
              this.tableList = []
              if (item.content.limitParam.limit_deliver_area === 0) {
                this.tableData.push(item.content.limitParam)
                if (item.content.areaParam.length > 0) {
                  this.tableData.push(item.content.areaParam[0])
                  item.content.areaParam[0].area_text = item.content.areaParam[0].area_text.join('，')
                }
              } else {
                this.tableData.push(item.content.areaParam[0])
                item.content.areaParam[0].area_text = item.content.areaParam[0].area_text.join('，')
              }
              this.tableList.push(this.tableData)
              item.tableList = this.tableList
            })
            this.lists = dataList
          }
        })
      } else {
        // 重量运费模板列表
        fetchDeliverTemplateList({
          flag: 1,
          page: {
            currentPage: this.pageParams.currentPage,
            pageRows: this.pageParams.pageRows
          }
        }).then(res => {
          const { error, content: { pageResult: { dataList, page } } } = res
          if (error === 0) {
            this.loading = false
            this.pageParams = page
            // 表格数据处理
            dataList.map((item, index) => {
              this.tableData = []
              this.tableList = []
              if (item.content.limitParam.limit_deliver_area === 0) {
                this.tableData.push(item.content.limitParam)
                if (item.content.areaParam.length > 0) {
                  this.tableData.push(item.content.areaParam[0])
                  item.content.areaParam[0].area_text = item.content.areaParam[0].area_text.join('，')
                }
              } else {
                this.tableData.push(item.content.areaParam[0])
                item.content.areaParam[0].area_text = item.content.areaParam[0].area_text.join('，')
              }
              this.tableList.push(this.tableData)
              item.tableList = this.tableList
            })
            this.lists = dataList
          }
        })
      }
    },
    // 复制运费模板
    handleCopyTemplate (deliverTemplateId) {
      copyDeliverTemplateApi({ deliverTemplateId }).then(res => {
        const { error } = res
        if (error === 0) {
          console.log(`复制成功`)
          this.initData()
          // this.reload() // 局部刷新
        }
      }).catch(error => console.log(error))
    },
    // 修改运费模板
    upDateTemplate (deliverTemplateId) {
      var type = 0
      if (this.listType === `list`) {
        type = 0
      } else {
        type = 1
      }
      this.$router.push({
        path: `/admin/home/main/goodsManage/deliverTemplate/deliverTemplateUpdate`,
        query: { deliverTemplateId, type }
      })
    },
    // 删除运费模板
    handleDelTemplate (id) {
      // 删除操作的提示
      this.$confirm('此操作将永久删除该模板, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deliverDelete({
          'deliverTemplateId': id
        }).then(res => {
          const { error } = res
          if (error === 0) {
            this.initData()
            this.$message.success({ message: '删除成功!' })
          }
        }).catch(err => console.log(err))
      }).catch((error) => console.log(error))
    }
  }
}
</script>

<style lang="scss" scoped>
// body {
//   margin: 200px;
// }
.block {
  background-color: #e6e9f0;
  width: 100%;
  height: 10px;
}
.table_header {
  position: relative;
  padding-right: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 50px;
  width: 100%;
  background: #eef1f6;
}
.left {
  background-color: #5a8bff;
  height: 100%;
  width: 2px;
}
.title {
  position: absolute;
  left: 20px;
  top: 0;
  height: 50px;
  line-height: 50px;
  color: #333;
  font-size: 14px;
  font-weight: 700;
}
/* .right {
  margin-left: 250px;
} */
.list {
  margin-bottom: 10px;
}
table,
th,
td {
  border: 1px solid #ddd;
}
table {
  width: 100%;
  margin-top: 2px;
}
th {
  height: 30px;
  text-align: center;
  vertical-align: middle;
}
td {
  text-align: center;
  min-height: 30px;
  vertical-align: middle;
  padding: 10px;
  width: 20%;
}

.list {
  background: #fff;
  margin-bottom: 10px;
}
</style>
