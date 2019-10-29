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
              label-position="top"
            >
              <el-form-item label="功能权限密码">
                <div class="password_set">
                  <div class="password_tips">
                    <p>设置功能权限密码后，开启密码开关</p>
                    <p>此权限组用户在进行对应功能操作时，需要先输入此密码</p>
                  </div>
                  <el-button
                    class="password_button"
                    size="mini"
                    @click="pwdShow=true"
                  >设置密码</el-button>
                </div>
              </el-form-item>

            </el-form>
            <el-form
              label-width="100px"
              class="demo-dynamic"
              size="mini"
              v-show="pwdShow"
            >
              <el-form-item label="登录密码">

                <el-input
                  size="small"
                  style="width:35%"
                  placeholder="请输入管理员登录密码"
                  v-model="loginPass"
                />
              </el-form-item>

              <el-form-item label="设置密码">
                <el-input
                  size="small"
                  style="width:35%"
                  placeholder="请输入功能权限密码"
                  v-model="rolePass"
                />
                <span style="color:#999">修改后的新密码将替换原有密码</span>
              </el-form-item>

            </el-form>
            <el-form
              label-width="100px"
              class="demo-dynamic"
              size="mini"
            >
              <el-form-item label="权限组权限">
                <el-checkbox
                  name="checkAll"
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
            <el-table-column>
              <template slot-scope="scope">
                <ul
                  class="role_ul"
                  v-for="(subItem,index) in scope.row"
                  :key="index"
                >
                  <li style="display: flex;">
                    <el-checkbox-group v-model="privilegePass">
                      <el-checkbox
                        :class="subItem.prName"
                        :label="subItem.prName"
                        :name="subItem.prName"
                        @change="show(scope.row[0].topIndex,subItem.prName)"
                      >{{subItem.name}}</el-checkbox>
                    </el-checkbox-group>

                    <div>
                      密码
                      <el-switch
                        class="switchName"
                        v-model="subItem.prNameSwitch"
                        :active-value="subItem.prName"
                        :inactive-value="0"
                        active-color="#f7931e"
                        inactive-color="#ddd"
                      >
                      </el-switch>
                      <span>已关闭</span>
                    </div>
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
      pwdShow: false,
      loginPass: null,
      rolePass: null,
      privilegePwdList: [],
      finprList: [],
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
      privilegePass: [],
      checkRowList: []
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
      console.log('是编辑吗2222')
      console.log(this.isEdit)
      this.search()
    },
    search () {
      let params = {
        'currentPage': this.currentPage,
        'pageRows': 20
      }
      defRoleListRequest(params).then((res) => {
        console.log('res-----------------------------------')
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.shopPriPassList
          if (this.isEdit !== 0) {
            // 编辑前的查询
            console.log('编辑前的查询2222222222')
            this.beforeEdit()
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
          var con = res.content.privilegePass
          console.log(con)
          if (!this.isEmpty(con)) {
            var conte = con.splice(',')
            var conte0 = conte[0]
            var conte1 = conte[1]
            this.privilegePass = conte0.split(',')
            if (!this.isEmpty(conte1)) {
              var pwd = conte1.split(',')
              for (let i = 0; i < this.tableData.length; i++) {
                for (let n = 0; n < this.tableData[i].length; n++) {
                  for (let m = 0; m < pwd.length; m++) {
                    if (this.tableData[i][n].prName === pwd[m]) {
                      this.tableData[i][n].prNameSwitch = this.tableData[i][n].prName
                    }
                  }
                }
              }
            }
          }
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

    // 点击右侧后左侧checkbox是否选择
    show (val, val2) {
      var arr = document.getElementsByName(val2)
      if (arr[0].checked === true) {
        this.checkRowList.push(val)
      } else {
        this.checkRowList.splice(this.checkRowList.indexOf(val), 1)
      }
      console.log(this.checkRowList)
    },
    checkRow () {
      console.log('checkRowList')
      console.log(this.checkRowList)
      this.privilegePass = []
      for (let i = 0; i < this.checkRowList.length; i++) {
        var data = this.tableData[this.checkRowList[i]]
        for (let n = 0; n < data.length; n++) {
          this.privilegePass.push(data[n].prName)
        }
      }
    },
    checkAll () {
      var doc = document.getElementsByName('checkAll')
      if (doc[0].checked === true) {
        console.log('全选')
        for (let i = 0; i < this.tableData.length; i++) {
          this.checkRowList.push(i)
          for (let n = 0; n < this.tableData[i].length; n++) {
            this.privilegePass.push(this.tableData[i][n].prName)
          }
        }
      } else {
        console.log('没有全选')
        this.checkRowList = []
        this.privilegePass = []
      }
    },
    // 提交信息到父页面
    submitInfo () {
      // 获取所有switch的数据
      for (let i = 0; i < this.tableData.length; i++) {
        for (let n = 0; n < this.tableData[i].length; n++) {
          if (this.tableData[i][n].prNameSwitch !== 0) {
            this.privilegePwdList.push(this.tableData[i][n].prName)
          }
        }
      }
      if (this.privilegePwdList.length > 0) {
        if (this.isEmpty(this.loginPass)) {
          this.$message.error('请输入登录密码')
          this.toFath()
          return false
        }
        if (this.isEmpty(this.rolePass)) {
          this.$message.error('请设置密码')
          this.toFath()
          return false
        }
      }
      var pList = this.privilegePass.join(',')
      var pwdList = this.privilegePwdList.join(',')
      this.finprList = []
      this.finprList.push(pList)
      this.finprList.push(pwdList)
      console.log('join之后')
      console.log(pList)
      console.log(pwdList)
      let param = {
        'loginPass': this.loginPass,
        'rolePass': this.rolePass,
        'privilegePass': this.finprList
      }
      this.$emit('privilegePassInfo', param)
    },
    toFath () {
      let params = {
        'faClick': false
      }
      this.$emit('faClickChange', params)
      return false
    },
    test () {
      for (let i = 0; i < this.tableData.length; i++) {
        for (let n = 0; n < this.tableData[i].length; n++) {
          if (this.tableData[i][n].prNameSwitch !== 0) {
            this.privilegePwdList.push(this.tableData[i][n].prName)
          }
        }
      }
      var pList = this.privilegePass.join(',')
      var pwdList = this.privilegePwdList.join(',')
      this.finprList = []
      this.finprList.push(pList)
      this.finprList.push(pwdList)
      console.log('join之后')
      console.log(pList)
      console.log(pwdList)
      let param = {
        'loginPass': this.loginPass,
        'rolePass': this.rolePass,
        'privilegePass': this.finprList
      }
      console.log(param)
      // this.$emit('privilegeInfo', param)
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  height: 400px;
  overflow-y: auto;
}
.footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.footer > span {
  font-size: 14px;
}
.password_set {
  padding: 5px 0;
  // border: 1px solid #eee;
}
.password_tips {
  float: left;
  color: #999;
}
.password_set .password_button {
  float: right;
  margin-right: 10px;
  margin-top: 6px;
}
.role_ul {
  li {
    display: flex;
    justify-content: space-between;
  }
}
</style>
