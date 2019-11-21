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
            :data="item.templateContent.areaParam"
            v-if="item.templateContent !== null"
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

          <table border="1">
            <!-- <thead>
              <tr>
                <th>可配送区域</th>
                <th>首件（件）</th>
                <th>运费（元）</th>
                <th>续件（件）</th>
                <th>续费（元）</th>
              </tr>
            </thead> -->
            <tbody>
              <!-- <tr v-show="item.templateContent[0].datalist[0].limit_deliver_area === 0">
                <td>{{item.templateContent[0].datalist[0].area_text}}</td>
                <td>{{item.templateContent[0].datalist[0].first_num}}</td>
                <td>{{item.templateContent[0].datalist[0].first_fee}}</td>
                <td>{{item.templateContent[0].datalist[0].continue_num}}</td>
                <td>{{item.templateContent[0].datalist[0].continue_fee}}</td>
              </tr> -->
              <!-- 其他区域以外的表格 -->
              <!-- <tr
                v-for="(it22,i) in item.templateContent[0].datalist[1]"
                :key="i"
                v-show="it22.area_text!== null"
              >
                <td>{{it22.area_text}}</td>
                <td>{{it22.first_num}}</td>
                <td>{{it22.first_fee}}</td>
                <td>{{it22.continue_num}}</td>
                <td>{{it22.continue_fee}}</td>
              </tr> -->
              <!-- 条件 -->

            </tbody>
          </table>
          <!-- 表格 -->

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
import { formatTemplateData } from '@/util/formatData.js'

import { fetchDeliverTemplateList, deliverDelete, copyDeliverTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
import pagination from '@/components/admin/pagination/pagination'
export default {
  name: `deliverTemplateTable`,
  components: { pagination },
  props: {
    listType: String
  },
  data () {
    return {
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
  methods: {
    // 初始化运费模板数据
    initData () {
      if (this.listType === `list`) {
        // 运费模板列表
        fetchDeliverTemplateList({
          flag: 0,
          currentPage: this.pageParams.currentPage,
          pageRows: this.pageParams.pageRows
        }).then(res => {
          const { error, content: { config, pageResult: { dataList, page } } } = res
          if (error === 0) {
            this.loading = false

            console.log(res)
            this.pageParams = page
            this.content = res.content
            this.formData = JSON.parse(config)
            let resData = formatTemplateData(dataList)
            this.lists = resData
            console.log(this.lists)
          }
        }).catch(err => console.log(err))
      } else {
        // 重量运费模板列表
        fetchDeliverTemplateList({
          flag: 1,
          currentPage: this.pageParams.currentPage,
          pageRows: this.pageParams.pageRows
        }).then(res => {
          const { error, content: { page } } = res
          if (error === 0) {
            this.loading = false
            console.log(res)
            this.pageParams = page
            let resData = formatTemplateData(res.content.dataList)
            this.lists = resData
            console.log(this.lists)
          }
        }).catch(err => console.log(err))
      }
    },
    // 复制运费模板
    handleCopyTemplate (deliverTemplateId) {
      // copyDeliverTemplateApi
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
      this.$router.push({
        path: `/admin/home/main/goodsManage/deliverTemplate/deliverTemplateUpdate`,
        query: { deliverTemplateId }
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
            // 删除成功提示
            this.$message({
              type: 'success',
              center: 'true',
              message: '删除成功!'
            })
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
