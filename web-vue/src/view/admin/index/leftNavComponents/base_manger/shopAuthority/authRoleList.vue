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
              <el-form-item label="权限组名称">

                <el-input
                  size="mini"
                  style="width:35%"
                  placeholder="请输入角色名称，如：管理员"
                  prefix-icon="el-icon-search"
                  v-model="roleName"
                />
              </el-form-item>
              <el-form-item label="权限组权限">

                <el-checkbox
                  name="checkAll"
                  v-model="checkAllState"
                  @change="checkAll"
                >全选</el-checkbox>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div class="table_box">

          <el-table
            :data="tableData"
            border
            stripe
            :show-header='false'
            style="width: 100%"
          >
            <el-table-column
              align="left"
              width="200px"
            >
              <template slot-scope="scope">
                <el-checkbox-group v-model="checkRowList">
                  <el-checkbox
                    v-if="!isEmpty(scope.row[0])"
                    v-model="scope.row[0].topIndex"
                    :label="scope.row[0].topIndex"
                    @change="checkRow()"
                  >{{$t('authRoleList.'+scope.row[0].topIndex)}}</el-checkbox>
                </el-checkbox-group>
              </template>
            </el-table-column>
            <el-table-column align="left">
              <template slot-scope="scope">
                <ul
                  class="role_ul"
                  v-for="(subItem,index) in scope.row"
                  :key="index"
                >
                  <li style="display: inline-block; width: 122px">
                    <el-checkbox-group v-model="privilegeList">
                      <el-checkbox
                        :class="subItem.enName"
                        :label="subItem.enName"
                        :name="subItem.enName"
                        @change="show(scope.row[0].topIndex,subItem.enName)"
                      >{{subItem.name}}</el-checkbox>
                    </el-checkbox-group>
                  </li>
                </ul>
              </template>

            </el-table-column>
          </el-table>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defRoleListRequest, editViewRoleRequest } from '@/api/admin/basicConfiguration/shopAuthority.js'
export default {
  name: 'childConfig',
  props: {
    faClick: { // 父组件的点击
      type: Boolean,
      default: () => false
    },
    isEdit: { // 父组件的点击
      type: Number,
      default: () => 0
    }
  },
  data () {
    return {
      roleName: null,
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
      Act: null,
      privilegeList: [],
      checkRowList: [],
      checkAllState: false,
      checkedCount: null
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.defaluteData()
  },
  watch: {
    faClick (newData) {
      if (newData === true) {
        this.submitInfo()
      }
      console.log(newData)
    }
  },
  methods: {
    defaluteData () {
      console.log('是编辑吗1111')
      console.log(this.isEdit)
      this.search()
    },
    search () {
      defRoleListRequest().then((res) => {
        console.log('res-----------------------------------')
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.shopMenuList
          if (this.isEdit !== 0) {
            // 编辑前的查询
            console.log('编辑前的查询')
            this.beforeEdit()
          } else {
            console.log('赋值所有')
            this.checkedAll()
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    beforeEdit () {
      let param = {
        'roleId': this.isEdit
      }
      editViewRoleRequest(param).then((res) => {
        console.log('编辑之前的查询')
        console.log(res)
        if (res.error === 0) {
          this.privilegeList = res.content.privilegeList
          this.roleName = res.content.roleName
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
    // 点击右侧后左侧checkbox是否选择
    show (val, val2) {
      console.log(val, val2)
      var arr = document.getElementsByName(val2)
      console.log(arr[0].checked === true)
      if (arr[0].checked === true) {
        this.checkRowList.push(val)
      } else {
        var a = this.checkRowList.indexOf(val)
        if (a === -1) {
          console.log('不存在，不删')
        } else {
          this.checkRowList.splice(a, 1)
        }
      }
      this.checkAllState = this.checkedCount === this.privilegeList.length
      console.log(this.checkRowList)
    },
    checkRow () {
      console.log('checkRowList')
      console.log(this.checkRowList)
      this.privilegeList = []
      for (let i = 0; i < this.checkRowList.length; i++) {
        var data = this.tableData[this.checkRowList[i]]
        for (let n = 0; n < data.length; n++) {
          this.privilegeList.push(data[n].enName)
        }
      }
      this.checkAllState = this.checkedCount === this.privilegeList.length
    },
    checkAll () {
      var doc = document.getElementsByName('checkAll')
      if (doc[0].checked === true) {
        this.checkedAll()
      } else {
        this.checkRowList = []
        this.privilegeList = []
      }
    },
    checkedAll () {
      this.checkRowList = []
      this.privilegeList = []
      console.log('所有')
      console.log(this.tableData)
      for (let i = 0; i < this.tableData.length; i++) {
        this.checkRowList.push(i)
        console.log(i)
        for (let n = 0; n < this.tableData[i].length; n++) {
          this.privilegeList.push(this.tableData[i][n].enName)
        }
      }
      this.checkedCount = this.privilegeList.length
      this.checkAllState = true
    },
    // 提交信息到父页面
    submitInfo () {
      if (this.isEmpty(this.roleName)) {
        this.$message.error('权限组名称不能为空')
        let params = {
          'faClick': false
        }
        this.$emit('faClickChange', params)
        return false
      }
      let param = {
        'roleName': this.roleName,
        'privilegeList': this.privilegeList
      }
      this.$emit('privilegeInfo', param)
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  height: 400px;
  overflow-y: auto;
}
.role_ul {
  list-style: none;
  padding: 0;
  margin: 0;
  max-width: 560px;
}

.role_ul li {
  min-width: 80px;
  float: left;
  margin: 0;
  padding: 0;
}
</style>
