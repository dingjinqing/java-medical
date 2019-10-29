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
              <el-form-item label="子账户手机号">
                <el-select
                  filterable
                  v-model="AccountId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in mobileList"
                    :key="item.accountId"
                    :label="item.accountName"
                    :value="item.accountId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="权限组">
                <el-select
                  filterable
                  v-model="RoleId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in groupRoleList"
                    :key="item.roleId"
                    :label="item.roleName"
                    :value="item.roleId"
                  >
                  </el-option>
                </el-select>
                <el-link
                  type="primary"
                  @click="toAuthPage()"
                >添加权限组</el-link>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  size="mini"
                  @click="setAndUpdate()"
                >保存</el-button>
              </el-form-item>
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
              prop="accountName"
              label="子账号用户名"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="mobile"
              label="子账户手机号"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="roleName"
              label="角色名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="isBind"
              label="第三方公众号"
              align="center"
              :formatter="bindFormatter"
            >
            </el-table-column>
            <el-table-column
              prop="officialNickName"
              label="已绑定用户"
              align="center"
            >
              <template
                slot-scope="scope"
                v-if="scope.row.isBind===1"
              >
                <div style="display: flex;justify-content: center;align-items: center;">
                  <el-image
                    :src="scope.row.headImgUrl"
                    style="width: 45px; border-radius: 25px; margin-right: 10px;"
                  ></el-image>
                  {{scope.row.officialNickName}}
                </div>
              </template>
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
                <el-button
                  v-if="scope.row.officialOpenId!=null&& scope.row.isBind===1"
                  type="text"
                  @click="binfOption(scope.row,'del_bind')"
                >解绑</el-button>
                <el-button
                  v-if="scope.row.officialOpenId!=null&&scope.row.isBind===0"
                  type="text"
                  @click="binfOption(scope.row,'bind')"
                >绑定</el-button>
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
          <el-dialog
            title="编辑用户信息"
            :visible.sync="centerDialogVisible"
            width="30%"
            center
          >
            <div>
              <el-form
                label-width="100px"
                class="demo-dynamic"
                size="mini"
              >
                <el-form-item label="子账户用户名">
                  {{AccountName}}
                </el-form-item>
                <el-form-item label="担任角色">
                  <el-select
                    filterable
                    v-model="RoleId"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in groupRoleList"
                      :key="item.roleId"
                      :label="item.roleName"
                      :value="item.roleId"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>

              </el-form>
            </div>
            <span
              slot="footer"
              class="dialog-footer"
            >
              <el-button
                @click="centerDialogVisible = false"
                size="small"
              >取 消</el-button>
              <el-button
                type="primary"
                size="small"
                @click="edit()"
              >保存</el-button>
            </span>
          </el-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getRoleGroupRequest, setRoleRequest, editRoleRequest, delRoleRequest, bindUnBindOfficialRequest } from '@/api/admin/basicConfiguration/shopAuthority.js'
export default {
  name: 'childConfig',
  data () {
    return {
      tableData: [{}],
      mobileList: [],
      groupRoleList: [],
      mobile: null,
      groupRole: null,
      totalRows: null,
      pageRows: '',
      currentPage: 1,
      pageCount: null,
      pagination_b: true,
      page: null,
      AccountId: null,
      RoleId: null,
      centerDialogVisible: false,
      AccountName: null,
      RecId: null,
      Act: null
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
      let params = {
        'currentPage': this.currentPage,
        'pageRows': 20
      }
      getRoleGroupRequest(params).then((res) => {
        console.log('res-----------------------------------')
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.totalList.dataList
          this.mobileList = res.content.mobileList
          this.groupRoleList = res.content.groupRoleList
          this.page = res.content.totalList.page
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
    // 保存和更新
    setAndUpdate () {
      if (this.isEmpty(this.AccountId)) {
        return this.$message.error('请选择子账户手机号')
      }
      if (this.isEmpty(this.RoleId)) {
        return this.$message.error('请选择权限组')
      }
      let params = {
        'accountId': this.AccountId,
        'roleId': this.RoleId
      }
      setRoleRequest(params).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.search()
          this.$message.success('添加成功')
        } else {
          this.$message.error(res.message)
        }
      })
    },

    edit () {
      this.centerDialogVisible = false
      if (this.isEmpty(this.AccountId)) {
        return this.$message.error('请选择子账户手机号')
      }
      if (this.isEmpty(this.RoleId)) {
        return this.$message.error('请选择角色')
      }
      let params = {
        'accountId': this.AccountId,
        'roleId': this.RoleId,
        'recId': this.RecId
      }
      editRoleRequest(params).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.search()
          this.$message.success('添加成功')
        } else {
          this.$message.error(res.message)
        }
        this.RecId = null
      })
    },
    del () {
      let params = {
        'accountId': this.AccountId,
        'roleId': this.RoleId,
        'recId': this.RecId
      }
      delRoleRequest(params).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.search()
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.message)
        }
        this.RecId = null
      })
    },
    bindAndUn () {
      let params = {
        'act': this.Act,
        'accountId': this.AccountId
      }
      bindUnBindOfficialRequest(params).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.search()
          this.$message.success('解绑成功')
        } else {
          this.$message.error(res.message)
        }
        this.RecId = null
      })
    },
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.search()
    },
    bindFormatter (row, column) {
      switch (row.isBind) {
        case 1:
          row.isBindTran = '已绑定'
          break
        case 0:
          row.isBindTran = '未绑定'
          break
      }
      return row.isBindTran
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    editOption (row) {
      this.centerDialogVisible = true
      this.AccountName = row.accountName
      this.AccountId = row.accountId
      this.RoleId = row.roleId
      this.RecId = row.recId
    },
    delOption (row) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.AccountId = row.accountId
        this.RoleId = row.roleId
        this.RecId = row.recId
        this.del()
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    binfOption (row, data) {
      this.$confirm('确认解绑吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.AccountId = row.accountId
        this.Act = data
        this.bindAndUn()
      }).catch(() => {
        this.$message.info('已取消')
      })
    },
    toAuthPage () {
      console.log('发送')
      let params = {
        'flag': 2
      }
      this.$emit('showAuthConfig', params)
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
