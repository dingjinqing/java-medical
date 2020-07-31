<template>
  <div class="allDepartment">
    <allDepartmentHeaderTab :tabIndex="0" />
    <div class="goodsSortForm">
      <el-button
        type="primary"
        size="small"
        @click="addDepartmentClicked"
      >添加科室</el-button>
    </div>
    <div>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="departmentData"
        border
        style="width: 100%"
      >
        <el-table-column
          align="left"
          label="科室名称"
        >
          <template v-slot="{row,$index}">
            <template v-if="row.isLeaf === 0 && row.level === 1">
              <span
                v-if="!row.open"
                class="collapseIcon el-icon-folder-add"
                @click="collapseIconClicked(row,$index)"
              ></span>
              <span
                v-else
                class="collapseIcon el-icon-folder-remove"
                @click="collapseIconClicked(row,$index)"
              ></span>
              <span class="n-bold"> {{row.name}}</span>

            </template>
            <template v-else-if="row.level === 1">
              <span
                class="collapseTab"
                style='width:48px;'
              ></span>

              <span class="n-bold"> {{row.name}}</span>
            </template>
            <template v-else>
              <span class="collapseTab"></span>
              {{row.name}}
            </template>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="科室代码"
          prop="code"
        />
        <el-table-column
          align="center"
          label="操作"
        >
          <template v-slot="scope">
            <el-tooltip
              content="编辑"
              placement="top"
            >
              <span
                class="iconfont iconbianji"
                @click="editDepartmentClicked(scope.row)"
              ></span>
            </el-tooltip>
            <el-tooltip
              content="删除"
              placement="top"
            >
              <span
                class="iconfont iconshanchu2"
                @click="deleteDepartmentClicked(scope.row,scope.$index)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="handleQuery"
      />
    </div>
  </div>
</template>

<script>
// 导入api
import { getDepartmentList, getBatchDepartmentList, deleteDepartment } from '@/api/admin/doctorManage/allDepartment/departmentManagement.js'
// 组件导入
import allDepartmentHeaderTab from './officesHeaderTab'

import pagination from '@/components/admin/pagination/pagination.vue'
export default {
  name: 'allDepartment',
  components: {
    allDepartmentHeaderTab, pagination
  },
  data () {
    return {
      departmentData: [],
      pageParams: {
        pageRows: 10,
        currentPage: 1
      },
      requestParams: {
        keywords: null,
        pageRows: 10,
        currentPage: 1
      }
    }
  },
  methods: {

    // 查询
    handleQuery () {
      this.loading = true
      let params = Object.assign({}, this.requestParams, this.pageParams)
      getDepartmentList(params).then(res => {
        this.loading = false
        console.log(res)
        this.pageParams = res.content.page
        this.requestParams.pageRows = res.content.page.pageRows
        this.requestParams.currentPage = res.content.page.currentPage
        this.departmentData = res.content.dataList
      }).catch(err => {
        this.loading = false
        console.log(err)
      })
    },
    addDepartmentClicked () {
      this.$router.push({ name: 'addOffices' })
      console.log(this.$router)
    },
    collapseIconClicked (row, $index) {
      row.open = !row.open
      let id = row.id
      getBatchDepartmentList(id).then(res => {
        console.log(res)
        if (row.open) {
          console.log(this.departmentData)
          this.departmentData.splice($index + 1, 0, ...res.content)
          console.log(this.departmentData)
        } else {
          this.departmentData.splice($index + 1, res.content.length)
        }
      }).catch(err => console.log(err))
    },
    /* 修改科室 */
    editDepartmentClicked (row) {
      console.log(this.$router, row)
      this.$router.push({ name: 'updateOffices', params: { id: row.id } })
    },
    /* 删除商品科室 */
    deleteDepartmentClicked (row, $index) {
      let deleteMsg = row.level === 1 ? '删除一级科室，会删除该分类下的所有二级科室，确定删除？' : '确定删除？'
      this.$confirm(deleteMsg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDepartment(row.id).then(res => {
          row.level === 1 ? this.handleQuery() : this.departmentData.splice($index, 1)
        })
      })
    }
  },
  mounted () {
    this.langDefault()
    this.handleQuery()
  }
}
</script>

<style lang="scss" scoped>
@import '@/assets/aliIcon/iconfont.scss';
.allDepartment {
  .goodsSortForm {
    margin: 10px 0px;
  }
  .collapseIcon {
    font-size: 20px;
    margin-right: 4px;
    margin-left: 20px;
    color: #5a8bff;
    cursor: pointer !important;
  }
  .collapseTab {
    display: inline-block;
    width: 120px;
  }
  .operateSpan {
    font-size: 22px;
    color: #5a8bff;
    cursor: pointer !important;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .iconfont {
    font-size: 22px;
    color: #5a8bff;
  }
  .n-bold {
    font-weight: 800;
  }
}
</style>
