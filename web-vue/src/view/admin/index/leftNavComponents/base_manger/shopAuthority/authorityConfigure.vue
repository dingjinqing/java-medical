<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <el-form
              label-width="100px"
              class="demo-dynamic"
              size="mini"
            >
              <el-button
                type="primary"
                size="small"
                @click="clickUp"
              >添加权限组</el-button>
            </el-form>
          </div>
        </div>
        <div class="table_box">
          <el-table
            :data="tableData"
            border
            stripe
            style="width: 100%"
            :header-cell-style="{background:'#eef1f6',color:'#606266'}"
          >
            <el-table-column
              prop="roleName"
              label="角色名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="createTime"
              label="创建时间"
              align="center"
            >
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
            >
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="editOption(scope.row)"
                >编辑</el-button>
                <el-button
                  type="text"
                  @click="delOption(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="footer">
            <span>每页{{this.pageRows}}行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数为：{{this.totalRows}}</span>
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              layout="prev, pager, next, jumper"
              :page-count="pageCount"
              :small="pagination_b"
            >
            </el-pagination>
          </div>
          <!--添加权限的弹窗-->
          <div v-if="showdig">
            <shopRoleDig
              :tuneUp.sync="tuneUp"
              @refreshFa="refresh"
              :isEdit="isEdit"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { groupListRequest, delGroupRoleRequest } from '@/api/admin/basicConfiguration/shopAuthority.js'
import shopRoleDig from './shopRoleDig'
export default {
  name: 'childConfig',
  components: {
    shopRoleDig
  },
  data () {
    return {
      tableData: [{}],
      mobileList: [],
      groupRoleList: [],
      mobile: null,
      groupRole: null,
      totalRows: null,
      pageRows: '20',
      currentPage: 1,
      pageCount: null,
      pagination_b: true,
      page: null,
      AccountId: null,
      RoleId: null,
      tuneUp: false,
      AccountName: null,
      RecId: null,
      Act: null,
      isEdit: 0,
      showdig: false
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.defaluteData()
  },
  watch: {

  },
  methods: {
    defaluteData () {
      this.search()
    },
    search () {
      this.showdig = false
      this.tuneUp = false
      this.isEdit = 0
      let params = {
        'currentPage': this.currentPage,
        'pageRows': this.pageRows
      }
      groupListRequest(params).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.page = res.content.page
          this.currentPage = this.page.currentPage
          this.pageRows = this.page.pageRows
          this.firstPage = this.page.firstPage
          this.lastPage = this.page.lastPage
          this.nextPage = this.page.nextPage
          this.pageCount = this.page.pageCount
          this.totalRows = this.page.totalRows
        } else {
          this.$message.error(res.message)
        }
      })
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    editOption (row) {
      this.clickUp()
      this.isEdit = row.roleId
    },
    delOption (row) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.del(row.roleId)
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    handleCurrentChange () {
      this.search()
    },
    refresh (data) {
      if (data.refresh === true) {
        this.search()
      }
    },
    del (data) {
      let params = {
        'roleId': data
      }
      delGroupRoleRequest(params).then((res) => {
        if (res.error === 0) {
          this.search()
          this.$message.success(res.message)
        } else {
          this.search()
          this.$message.error(res.message)
        }
      })
    },
    clickUp () {
      this.showdig = true
      this.$nextTick(() => {
        this.tuneUp = true
      })

      console.log(this.tuneUp)
    }
  }
}
</script>

<style lang="scss" scoped>
.footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.footer > span {
  font-size: 14px;
}
</style>
