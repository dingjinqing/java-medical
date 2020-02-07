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
              <el-form-item :label="$t('authRoleList.tipTitles6')">
                <div class="password_set">
                  <div class="password_tips">
                    <p>{{$t('authRoleList.tips1')}}</p>
                    <p>{{$t('authRoleList.tips2')}}</p>
                  </div>
                  <el-button
                    class="password_button"
                    size="mini"
                    @click="pwdShow=true"
                  >{{$t('authRoleList.tipsButton')}}</el-button>
                </div>
              </el-form-item>

            </el-form>
            <el-form
              label-width="100px"
              class="demo-dynamic2"
              size="mini"
              v-show="pwdShow"
            >
              <el-form-item
                :label="$t('authRoleList.pwd1')"
                :required='true'
              >

                <el-input
                  size="small"
                  style="width:35%"
                  :placeholder="$t('authRoleList.pwdTips1')"
                  v-model="loginPass"
                  show-password
                />
              </el-form-item>

              <el-form-item
                :label="$t('authRoleList.pwd2')"
                :required='true'
              >
                <el-input
                  size="small"
                  style="width:35%"
                  :placeholder="$t('authRoleList.pwdTips2')"
                  v-model="rolePass"
                  show-password
                />
                <span style="color:#999">{{$t('authRoleList.tips3')}}</span>
              </el-form-item>

            </el-form>
            <el-form
              class="demo-dynamic3"
              size="mini"
            >
              <el-form-item
                :label="$t('authRoleList.firstPageLab2')"
                display:
                block
              >
                <el-checkbox
                  name="checkAll"
                  v-model="checkAllState"
                  @change="checkAll"
                >{{$t('authRoleList.allCheck')}}</el-checkbox>
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
                <!--分模块全选，以后需要可以打开 -->
                <!-- <el-checkbox-group v-model="checkRowList">
                  <el-checkbox
                    v-if="!isEmpty(scope.row[0])"
                    v-model="scope.row[0].topIndex"
                    :label="scope.row[0].topIndex"
                    @change="checkRow()"
                  >{{$t('authRoleList.'+scope.row[0].topIndex)}}</el-checkbox>
                </el-checkbox-group> -->
                <span v-if="!isEmpty(scope.row[0])"> {{$t('authRoleList.pw'+scope.row[0].topIndex)}}</span>
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
                      >{{$t('authRoleList.'+subItem.prName)}}</el-checkbox>
                    </el-checkbox-group>
                    <div>
                      {{$t('authRoleList.passwd')}}
                      <el-switch
                        class="switchName"
                        v-model="subItem.prNameSwitch"
                        :active-value="subItem.prName"
                        :inactive-value="0"
                        active-color="#f7931e"
                        inactive-color="#ddd"
                      >
                      </el-switch>
                      <span v-if="subItem.prNameSwitch===0">{{$t('authRoleList.close')}}</span>
                      <span v-if="subItem.prNameSwitch!==0">{{$t('authRoleList.open')}}</span>
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
  },
  methods: {
    defaluteData () {
      console.log('是编辑吗2222')
      console.log(this.isEdit)
      this.search()
    },
    uploadData () {
      console.log('test22222222222')
      console.log('点点滴滴submitInfo')
      this.submitInfo()
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
      console.log('show')
      console.log(val)
      console.log(val2)
      var arr = document.getElementsByName(val2)
      if (arr[0].checked === true) {
        this.checkRowList.push(val)
      } else {
        this.checkRowList.splice(this.checkRowList.indexOf(val), 1)
      }
      this.checkAllState = this.checkedCount === this.privilegePass.length
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
      console.log(this.checkAllState)
      // var doc = document.getElementsByName('checkAll')
      if (this.checkAllState) {
        console.log('全选')
        for (let i = 0; i < this.tableData.length; i++) {
          this.checkRowList.push(i)
          for (let n = 0; n < this.tableData[i].length; n++) {
            this.privilegePass.push(this.tableData[i][n].prName)
          }
        }
        this.checkedCount = this.privilegePass.length
        this.checkAllState = true
      } else {
        console.log('没有全选')
        this.checkRowList = []
        this.privilegePass = []
      }
    },
    // 提交信息到父页面
    submitInfo () {
      console.log('密码页submitInfo')
      // 获取所有switch的数据
      this.privilegePwdList = []
      for (let i = 0; i < this.tableData.length; i++) {
        for (let n = 0; n < this.tableData[i].length; n++) {
          if (this.tableData[i][n].prNameSwitch !== 0) {
            this.privilegePwdList.push(this.tableData[i][n].prName)
          }
        }
      }
      console.log('开关' + this.privilegePwdList)
      if (this.privilegePwdList.length > 0) {
        console.log('大于0')
        console.log(this.privilegePwdList)
        if (this.isEmpty(this.loginPass)) {
          this.$message.error('请输入登录密码')
          return false
        }
        if (this.isEmpty(this.rolePass)) {
          this.$message.error('请设置密码')
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
  margin-left: 8px;
  width: 73%;
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
.demo-dynamic /deep/.el-form-item__content {
  border: 1px solid #eee;
}
.demo-dynamic2 {
  border: 1px solid #eee;
}
</style>
