<template>
  <div class="content">
    <div class="main">
      <div class="search_box">
        <div class="filters">
          <div class="filters_item">
            <el-form
              label-width="auto"
              class="demo-dynamic"
              size="small"
            >
              <el-form-item
                display:block
                :label="$t('authRoleList.mobileList')+'：'"
              >
                <el-select
                  filterable
                  v-model="AccountId"
                  style="width:170px;"
                  :placeholder="$t('authRoleList.please')"
                >
                  <el-option
                    v-for="item in mobileList"
                    :key="item.accountId"
                    :label="item.accountName"
                    :value="item.accountId"
                  >
                    <span style="float: left">{{ item.accountName }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ item.mobile }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item :label="$t('authRoleList.roleIdList')+'：'">
                <el-select
                  filterable
                  v-model="RoleId"
                  style="width:170px;"
                  :placeholder="$t('authRoleList.please')"
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
                >{{$t('authRoleList.addRoleId')}}</el-link>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  size="small"
                  @click="setAndUpdate()"
                >{{$t('authRoleList.save')}}</el-button>
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
              :label="$t('authRoleList.accountName')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="mobile"
              :label="$t('authRoleList.mobileList')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="roleName"
              :label="$t('authRoleList.roleName')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="isBind"
              :label="$t('authRoleList.thrid')"
              align="center"
              :formatter="bindFormatter"
            >
            </el-table-column>
            <el-table-column
              prop="officialNickName"
              :label="$t('authRoleList.officialNickName')"
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
              :label="$t('authRoleList.option')"
              align="center"
            >
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="editOption(scope.row)"
                >{{$t('authRoleList.eidt')}}</el-button>
                <el-button
                  type="text"
                  @click="delOption(scope.row)"
                >{{$t('authRoleList.del')}}</el-button>
                <el-button
                  v-if="scope.row.officialOpenId!=null&& scope.row.isBind===1"
                  type="text"
                  @click="binfOption(scope.row,'del_bind')"
                >{{$t('authRoleList.unbind')}}</el-button>
                <el-button
                  v-if="scope.row.officialOpenId!=null&&scope.row.isBind===0"
                  type="text"
                  @click="binfOption(scope.row,'bind')"
                >{{$t('authRoleList.bind')}}</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="footer">
            <span>{{$t('authRoleList.everyPage')}}{{this.pageRows}}{{$t('authRoleList.record')}}，{{$t('authRoleList.currentPage')}}：{{this.currentPage}}，{{$t('authRoleList.pageCount')}}：{{this.pageCount}}，{{$t('authRoleList.totalRows')}}：{{this.totalRows}}</span>
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
            :title="$t('authRoleList.eidtAccountInfo')"
            :visible.sync="centerDialogVisible"
            width="30%"
            center
          >
            <div>
              <el-form
                label-width="100px"
                class="demo-dynamic"
                size="small"
              >
                <el-form-item :label="$t('authRoleList.accountName')">
                  {{AccountName}}
                </el-form-item>
                <el-form-item :label="$t('authRoleList.haveRole')">
                  <el-select
                    filterable
                    v-model="RoleId"
                    style="width:170px;"
                    :placeholder="$t('authRoleList.please')"
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
              >{{$t('authRoleList.cancel')}}</el-button>
              <el-button
                type="primary"
                size="small"
                @click="edit()"
              >{{$t('authRoleList.save')}}</el-button>
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
        return this.$message.error(this.$t('authRoleList.please1'))
      }
      if (this.isEmpty(this.RoleId)) {
        return this.$message.error(this.$t('authRoleList.please2'))
      }
      let params = {
        'accountId': this.AccountId,
        'roleId': this.RoleId
      }
      setRoleRequest(params).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.search()
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    },

    edit () {
      this.centerDialogVisible = false
      if (this.isEmpty(this.AccountId)) {
        return this.$message.error(this.$t('authRoleList.please1'))
      }
      if (this.isEmpty(this.RoleId)) {
        return this.$message.error(this.$t('authRoleList.please3'))
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
          this.$message.success(res.message)
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
          this.$message.success(res.message)
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
          this.$message.success(res.message)
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
          row.isBindTran = this.$t('authRoleList.isBind')
          break
        case 0:
          row.isBindTran = this.$t('authRoleList.isNoBind')
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
      this.$confirm(this.$t('authRoleList.sure1'), this.$t('authRoleList.tip'), {
        confirmButtonText: this.$t('authRoleList.sure'),
        cancelButtonText: this.$t('authRoleList.cancel'),
        type: 'warning'
      }).then(() => {
        this.AccountId = row.accountId
        this.RoleId = row.roleId
        this.RecId = row.recId
        this.del()
      }).catch(() => {
        this.$message.info(this.$t('authRoleList.canle1'))
      })
    },
    binfOption (row, data) {
      this.$confirm(this.$t('authRoleList.sure2'), this.$t('authRoleList.tip'), {
        confirmButtonText: this.$t('authRoleList.sure'),
        cancelButtonText: this.$t('authRoleList.cancel'),
        type: 'warning'
      }).then(() => {
        this.AccountId = row.accountId
        this.Act = data
        this.bindAndUn()
      }).catch(() => {
        this.$message.info(this.$t('authRoleList.cancel'))
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
