<template>
  <div class="serviceAuth">
    <div class="serviceAuthMain">
      <div class="p_top">
        <div class="p_top_left">
          <div class=abutton>
            <a
              class="link"
              target="_blank"
            >
              <el-button
                @click="open()"
                type="primary"
              >{{$t('authRoleList.addUser')}}</el-button>
            </a>
          </div>
        </div>

        <div class="p_middle">
          <el-table
            class="version-manage-table"
            header-row-class-name="tableClss"
            :data="tableData"
            border
            style="width: 100%"
          >
            <el-table-column
              prop="accountName"
              :label="$t('authRoleList.userName')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="mobile"
              :label="$t('authRoleList.userMobile')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="createTime"
              :label="$t('authRoleList.addTime')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="lastAuthTime"
              store
              align="center"
            >
              <template
                slot-scope="scope"
                v-if="scope.row.shopInfo!==null"
              >
                <div
                  v-for='(item,index) in scope.row.shopInfo'
                  :key="index"
                >
                  {{item.shopName}}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('authRoleList.userMenu')"
              width="150"
              align="center"
            >
              <template
                slot-scope="scope"
                v-if="scope.row.shopInfo!==null"
              >
                <div
                  v-for='(item,index) in scope.row.shopInfo'
                  :key="index"
                >
                  <el-link
                    type="primary"
                    :underline="false"
                    @click="toHome(item.shopId)"
                  > {{item.roleName}}</el-link>
                </div>
              </template>
            </el-table-column>

            <el-table-column
              :label="$t('authRoleList.option')"
              align="center"
              width="150"
            >
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="editRoleOption(scope.row)"
                >{{$t('authRoleList.eidt')}}</el-button>
                <el-button
                  type="text"
                  @click="delRoleOpton(scope.row.accountId)"
                >{{$t('authRoleList.del')}}</el-button>

              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="footer">
          <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.totle}}</div>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totle"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!--添加账户弹窗-->
    <div class="pageDialogMy">
      <el-dialog
        :title="$t('authRoleList.addUser')"
        :visible.sync="dialogFormVisible"
        v-if="dialogFormVisible"
        width="450px"
      >
        <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleForm"
          label-width="auto"
          class="demo-ruleForm"
        >
          <el-form-item
            :label="$t('authRoleList.userName')"
            prop="accountName"
          >
            <el-input v-model="ruleForm.accountName"></el-input>
          </el-form-item>
          <el-form-item
            :label="$t('authRoleList.userMobile')"
            prop="mobile"
          >
            <el-input v-model="ruleForm.mobile"></el-input>
          </el-form-item>
          <el-form-item
            :label="$t('authRoleList.passwd')"
            prop="pass"
          >
            <el-input
              type="password"
              v-model="ruleForm.pass"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            :label="$t('authRoleList.passwd2')"
            prop="checkPass"
          >
            <el-input
              type="password"
              v-model="ruleForm.checkPass"
              autocomplete="off"
            ></el-input>
          </el-form-item>

        </el-form>
        <div
          slot="footer"
          class="dialog-footer changeMa"
        >
          <el-button @click="dialogFormVisible = false">{{$t('authRoleList.cancel')}}</el-button>
          <el-button
            type="primary"
            @click="addRoleBefore('ruleForm')"
          >{{$t('authRoleList.sure')}}</el-button>
        </div>
      </el-dialog>
    </div>

    <div class="pageDialogMy">
      <el-dialog
        :title="$t('authRoleList.eidtUser')"
        :visible.sync="editFormVisible"
        width="450px"
      >
        <el-form
          :model="ruleForm2"
          status-icon
          :rules="rules2"
          ref="ruleForm2"
          label-width="auto"
          class="demo-ruleForm2"
        >
          <el-form-item
            :label="$t('authRoleList.userName')"
            prop="accountName"
          >
            <span style="display:block;text-align: left;">{{ruleForm2.accountName}}</span>
          </el-form-item>
          <el-form-item
            :label="$t('authRoleList.userMobile')"
            prop="mobile"
          >
            <el-input v-model="ruleForm2.mobile"></el-input>
          </el-form-item>
          <el-form-item
            :label="$t('authRoleList.passwd')"
            prop="pass"
          >
            <el-input
              type="password"
              v-model="ruleForm2.pass"
              :placeholder="$t('authRoleList.pwdTips3')"
            ></el-input>
          </el-form-item>

        </el-form>
        <div
          slot="footer"
          class="dialog-footer changeMa"
        >
          <el-button @click="editFormVisible = false">{{$t('authRoleList.cancel')}}</el-button>
          <el-button
            type="primary"
            @click="editRoleBefore('ruleForm2')"
          >{{$t('authRoleList.sure')}}</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { subUserListRequest, authorizationRequest, oneListRequest, payManageRequest } from '@/api/admin/serviceSubUser'
import { changeShopRequest } from '@/api/admin/shopsPages.js'
export default {
  name: 'serviceSubUser',
  data () {
    var checkAccountName = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        return callback(new Error(this.$t('authRoleList.tips6')))
      } else {
        if (!(this.nameReg.test(value))) {
          callback(new Error(this.$t('authRoleList.tips13')))
        }
        callback()
      }
    }
    var validatePass = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error(this.$t('authRoleList.tips7')))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        } if (!(this.passWd.test(value))) {
          callback(new Error(this.$t('authRoleList.tips8')))
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error(this.$t('authRoleList.tips9')))
      } if (value !== this.ruleForm.pass) {
        callback(new Error(this.$t('authRoleList.tips10')))
      } if (!(this.passWd.test(value))) {
        callback(new Error(this.$t('authRoleList.tips8')))
      } else {
        callback()
      }
    }
    var checkMobile = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error(this.$t('authRoleList.tips11')))
      } else if (!(this.phonereg.test(value))) {
        callback(new Error(this.$t('authRoleList.tips12')))
      } else {
        callback()
      }
    }
    var validatePass3 = (rule, value, callback) => {
      if (!this.isEmpty(value)) {
        if (!(this.passWd.test(value))) {
          callback(new Error(this.$t('authRoleList.tips8')))
        }
      }
      callback()
    }
    var checkMobile2 = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error(this.$t('authRoleList.tips11')))
      } else if (!(this.phonereg.test(value))) {
        callback(new Error(this.$t('authRoleList.tips12')))
      } else {
        console.log('校验')
        callback()
      }
    }
    return {

      tableData: null,
      currentPage: 1,
      totle: null,
      pageCount: null,
      dialogFormVisible: false,
      editFormVisible: false,
      pageRows: 20,
      ruleForm: {
        accountName: null,
        mobile: null,
        pass: null,
        checkPass: null
      },
      ruleForm2: {
        accountName: null,
        mobile: null,
        pass: null,
        accountId: null
      },
      phonereg: /^1[3|7|8]\d{9}$|^19[8-9]\d{8}$|^166\d{8}|^15[0-3|5-9]\d{8}|^14[5|7]\d{8}$/,
      passWd: /^[^\u4e00-\u9fa5][\S+$]{5,16}$/,
      nameReg: /^[^\u4e00-\u9fa5]{0,}$/,
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        accountName: [
          { validator: checkAccountName, trigger: 'blur' }
        ],
        mobile: [
          { validator: checkMobile, trigger: 'blur' }
        ]
      },
      rules2: {
        pass: [
          { validator: validatePass3, trigger: 'blur' }
        ],
        mobile: [
          { validator: checkMobile2, trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.defaluteData()
  },
  methods: {
    defaluteData () {
      this.ruleForm = {}
      let params = {
        'currentPage': this.currentPage,
        'pageRows': 20
      }
      subUserListRequest(params).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          console.log('this.tableData')
          console.log(this.tableData)
          this.totle = res.content.page.totalRows
          this.currentPage = res.content.page.currentPage
          this.pageCount = res.content.page.pageCount
          this.pageRows = res.content.page.pageRows
        }
      })
    },
    handleCurrentChange () {
      this.defaultData()
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj === null || obj === '') {
        return true
      } else {
        return false
      }
    },
    addRoleBefore (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('提交')
          this.addRole()
        } else {
          this.$message.error('失败请检查')
          return false
        }
      })
    },
    // 添加账户
    addRole () {
      this.dialogFormVisible = false
      let params = {
        'accountName': this.ruleForm.accountName,
        'mobile': this.ruleForm.mobile,
        'accountPwd': this.ruleForm.pass
      }
      authorizationRequest(params).then((res) => {
        if (res.error === 0) {
          this.defaluteData()
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    delRoleOpton (accountId) {
      this.$confirm('确认要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delRole(accountId)
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 删除账户
    delRole (accountId) {
      oneListRequest(accountId).then((res) => {
        if (res.error === 0) {
          this.defaluteData()
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    editRoleBefore (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('编辑')
          this.editRole()
        } else {
          this.$message.error('失败请检查')
          return false
        }
      })
    },
    // 编辑
    editRole () {
      let params = {
        'accountName': this.ruleForm2.accountName,
        'mobile': this.ruleForm2.mobile,
        'accountPwd': this.ruleForm2.pass,
        'accountId': this.ruleForm2.accountId
      }
      payManageRequest(params).then((res) => {
        if (res.error === 0) {
          this.defaluteData()
          this.editFormVisible = false
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 编辑账户
    editRoleOption (data) {
      this.ruleForm2 = {}
      this.ruleForm2.accountName = data.accountName
      this.ruleForm2.accountId = data.accountId
      this.editFormVisible = true
      // 确保这个新属性同样是响应式的，且触发视图更新
      this.$set(this.ruleForm2, 'mobile', data.mobile)
    },
    open () {
      this.ruleForm = {}
      this.dialogFormVisible = true
    },
    toHome (obj) {
      let param = {
        'shopId': obj
      }
      changeShopRequest(param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          console.log('切换成功')
          this.$router.push({
            name: 'child_config'
          })
        } else {
          this.$message.error(res.message)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.serviceAuth {
  width: 100%;
  height: 100%;
  background-color: #fff;
  padding: 107px;
}
.p_top_left {
  display: flex;
  /deep/ .el-button {
    padding: 6px 20px;
  }
  .abutton {
    display: inline-block;
    text-align: center;
    margin: 0 -30px 0 0px;
  }
  .tipsDiv {
    position: relative;
    cursor: pointer;
    height: auto;
    font-size: 14px;
    line-height: 30px;
    color: #999;
    padding: 0 12px;
    margin-bottom: 10px;
    margin-left: 30px;
    &:hover .tipsHidden {
      display: block;
    }
    img {
      margin-left: 5px;
      position: relative;
      top: 2px;
    }
    .tipsHidden {
      display: none;
      position: absolute;
      top: 32px;
      right: 0;
      width: 300px;
      background: #fff;
      box-shadow: 0px 0px 3px #f0f0f0;
      z-index: 100;
      .tipsTop {
        border: 1px solid #ccc;
        background-color: white;
        z-index: 2;
        padding: 10px;
        p {
          color: #666;
          font-size: 12px;
          border-bottom: 1px solid #efedee;
        }
      }
      .tipsBottom {
        display: flex;
        justify-content: center;
      }
    }
  }
}
.p_middle {
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .operation {
    display: flex;
    justify-content: space-around;
    .iconSpn {
      display: block;
      font-size: 20px;
      color: #5a8bff;
      cursor: pointer;
    }
  }
}
.footer {
  background-color: #fff;
  height: 50px;
  line-height: 50px;
  color: #333;
  font-size: 14px;
  display: flex;
  justify-content: flex-end;
  padding-right: 10px;
  /deep/ .el-pagination {
    display: flex;
    align-items: center;
    .el-pager {
      display: flex;
      align-items: center;
    }
  }
}
.inputClass {
  width: 270px;
  height: 25px;
  margin-left: -160px;
}
.textareaClass {
  width: 270px;
  height: 100px;
  margin-left: -160px;
}
.pageDialogMy {
  text-align: center;
  /deep/ .el-form-item {
    margin-bottom: 15px;
  }
  /deep/ .el-form--label-left .el-form-item__label {
    text-align: center;
  }
  /deep/ .el-dialog__header {
    background-color: #f1f1f1;
    margin-bottom: -18px;
  }
}
.changeMa {
  margin-top: -36px;
}
</style>
<style>
</style>
