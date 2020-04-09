<template>
  <div class="list-con">
    <div class="top">
      <el-form
        size="small"
        :inline="true"
        class="store-permission-form"
      >
        <el-form-item label="门店账户：">
          <el-input
            class="form-input"
            v-model="queryParams.accountName"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input
            class="form-input"
            v-model="queryParams.mobile"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="角色：">
          <el-select
            v-model="queryParams.accountType"
            class="form-input"
          >
            <el-option
              label="全部"
              :value="0"
            ></el-option>
            <el-option
              label="店长"
              :value="2"
            ></el-option>
            <el-option
              label="店员"
              :value="1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账户状态：">
          <el-select
            v-model="queryParams.status"
            class="form-input"
          >
            <el-option
              label="全部"
              :value="-1"
            ></el-option>
            <el-option
              label="使用中"
              :value="1"
            ></el-option>
            <el-option
              label="已停用"
              :value="0"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >筛选</el-button>
      </el-form>
      <div>
        <el-button
          type="primary"
          size="small"
          @click="addAccountHandle"
        >添加账户</el-button>
      </div>
    </div>
    <div class="center">
      <el-table
        :data="tableData"
        class="tableClass"
        header-row-class-name="tableClss"
        border
      >
        <el-table-column
          label="门店账户"
          prop="accountName"
          align="center"
        ></el-table-column>
        <el-table-column
          label="手机号"
          prop="mobile"
          align="center"
        ></el-table-column>
        <el-table-column
          label="角色"
          prop="accountType"
          :formatter="accountTypeFmt"
          align="center"
        ></el-table-column>
        <el-table-column label="有权限门店">
          <template slot-scope="{row}">
            <div style="text-align:center;">
              <span v-text="row.storeLists.length"></span>
              <el-button
                type="text"
                @click="setStoreListDialog(row)"
              >设置</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="账户状态"
          :formatter="statusFmt"
          align="center"
        ></el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              <el-tooltip
                v-if="row.status === 1 || row.stauts==='1'"
                content="停用"
                effect="light"
                placement="top"
              >
                <i
                  class="el-icon-circle-close iconSpan"
                  @click="edit('stop', row)"
                ></i>
              </el-tooltip>
              <el-tooltip
                v-if="row.status === 0 || row.stauts === '0'"
                content="启用"
                effect="light"
                placement="top"
              >
                <i
                  class="el-icon-circle-check iconSpan"
                  @click="edit('start', row)"
                ></i>
              </el-tooltip>
              <el-tooltip
                content="删除"
                effect="light"
                placement="top"
              >
                <i
                  class="el-icon-delete iconSpan"
                  @click="edit('del', row)"
                ></i>
              </el-tooltip>
              <el-tooltip
                content="编辑"
                effect="light"
                placement="top"
              >
                <i
                  class="el-icon-edit-outline iconSpan"
                  @click="edit('edit', row)"
                ></i>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      ></pagination>
    </div>
    <!-- 添加账户 -->
    <el-dialog
      :title="isEdit?'编辑账户':'添加账户'"
      :visible.sync="accountDialogVisible"
      width="500px"
    >
      <div class="dialog-content">
        <el-form
          ref="accountForm"
          class="account-form"
          label-width="110px"
          size="small"
          :model="accountForm"
          :rules="accountFormRules"
        >
          <el-form-item
            label="账户角色："
            prop="accountType"
          >
            <el-select
              class="form-input"
              v-model="accountForm.accountType"
            >
              <el-option
                label="店长"
                :value="2"
              ></el-option>
              <el-option
                label="店员"
                :value="1"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="门店账户："
            prop="accountName"
          >
            <el-input
              class="form-input"
              v-model="accountForm.accountName"
              maxlength="20"
              placeholder="最长可输入20位"
            ></el-input>
            <p class="form-tip">支持输入字母/数字</p>
          </el-form-item>
          <el-form-item
            label="手机号："
            prop="mobile"
          >
            <el-input
              type="text"
              class="form-input"
              v-model="accountForm.mobile"
              autocomplete="off"
              auto-complete="new-password"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="密码："
            prop="accountPasswd"
          >
            <el-input
              type="password"
              class="form-input"
              v-model="accountForm.accountPasswd"
              show-password
              autocomplete="off"
              auto-complete="new-password"
            ></el-input>
            <p class="form-tip">编辑时密码为空即不修改密码</p>
          </el-form-item>
          <el-form-item
            label="有权限门店："
            prop="storeList"
          >
            <el-select
              v-model="accountForm.storeList"
              class="form-input"
              multiple
            >
              <el-option
                v-for="item in storeList"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="saveAccountHandle"
        >保存</el-button>
        <el-button
          size="small"
          @click="accountDialogVisible = false"
        >取消</el-button>
      </div>
    </el-dialog>
    <!-- 设置权限门店 -->
    <el-dialog
      title="模板应用门店设置"
      :visible.sync="setStoresVisible"
      width="500px"
    >
      <div>
        <span style="width:110px;">选择门店：</span>
        <el-select
          v-model="accountStoreList"
          class="form-input"
          multiple
        >
          <el-option
            v-for="item in storeList"
            :key="item.id"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <div slot="footer">
        <el-button
          type="primary"
          size="small"
          @click="setStoreList"
        >确定</el-button>
        <el-button
          @click="setStoresVisible = false"
          size="small"
        >取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAccountListApi, addAccountApi, editAccountApi, getAccountApi, updateAccountApi } from '@/api/admin/storeManage/storePermission'
import { allSourceRequest } from '@/api/admin/membershipList.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    let that = this
    function validName (rule, value, callback) {
      if (!value) {
        callback(new Error('请输入账户名称'))
      }
      if (!/^[^\u4e00-\u9fa5]{1,20}$/.test(value)) {
        callback(new Error('请正确填写账户名称'))
      }
      callback()
    }
    // 校验手机号
    function validMobile (rule, value, callback) {
      if (!value) {
        callback(new Error('请填写手机号'))
      }
      let reg = /^1[3|7|8]\d{9}$|^19[8-9]\d{8}$|^166\d{8}|^15[0-3|5-9]\d{8}|^14[5|7]\d{8}$/
      if (!reg.test(value)) {
        callback(new Error('请填写有效的手机号'))
      }
      callback()
    }
    function validPassword (rule, value, callback) {
      if (!value && !that.isEdit) {
        callback(new Error('请填写密码(6~16位)'))
      }
      if (value === '' && that.isEdit) {
        callback()
      }
      if (!/^[^\u4e00-\u9fa5]{6,16}$/.test(value)) {
        callback(new Error('请正确填写的密码(6~16位)'))
      }
      callback()
    }
    function validStoreList (rule, value, callback) {
      if (!value || value.length === 0) {
        callback(new Error('请选择有权限门店'))
      }
      callback()
    }
    // 校验密码
    return {
      tableData: [],
      queryParams: {},
      pageParams: {
        currentPage: 1,
        totalRows: 0
      },
      accountDialogVisible: false,
      isEdit: false,
      editAccountId: '', // 编辑的id
      baseAccountForm: {
        accountType: 1,
        accountName: '',
        mobile: '',
        accountPasswd: '',
        storeList: []
      },
      accountForm: {},
      storeList: [], // 门店列表
      accountFormRules: {
        accountType: [{ required: true, message: '请选择账户角色', trigger: 'blur' }],
        accountName: [{ required: true, validator: validName, trigger: 'blur' }],
        mobile: [{ required: true, validator: validMobile, trigger: 'blur' }],
        accountPasswd: [{ required: true, validator: validPassword, trigger: 'blur' }],
        storeList: [{ required: true, validator: validStoreList, trigger: 'blur' }]
      },
      setStoresVisible: false, // 模板应用门店弹窗
      accountStoreList: [], // 模板应用门店设置
      editAccount: {}
    }
  },
  mounted () {
    this.initStoreList()
    this.initDataList()
    this.accountForm = this.baseAccountForm
  },
  methods: {
    initDataList () {
      let that = this
      let params = Object.assign({}, this.pageParams, this.queryParams)
      getAccountListApi(params).then(res => {
        if (res.error === 0) {
          let content = res.content
          console.log(content)
          that.pageParams = content.page
          that.tableData = content.dataList
        } else {
          that.$message.error(res.message)
        }
      })
    },
    // 初始化门店列表
    initStoreList () {
      let that = this
      allSourceRequest().then(res => {
        if (res.error === 0) {
          that.storeList = res.content
        } else {
          that.$message.error(res.message)
        }
      })
    },
    // 添加账户弹窗
    addAccountHandle () {
      this.accountDialogVisible = !this.accountDialogVisible
      this.isEdit = false
    },
    // 添加/修改账户
    saveAccountHandle () {
      let that = this
      let params = this.accountForm
      this.$refs.accountForm.validate(valid => {
        if (valid) {
          if (!that.isEdit) {
            addAccountApi(params).then(res => {
              if (res.error === 0) {
                that.$message.success(res.message)
                that.accountDialogVisible = false
                that.initDataList()
              } else {
                that.$message.error(res.message)
              }
            })
          } else {
            updateAccountApi(params).then(res => {
              if (res.error === 0) {
                that.$message.success(res.message)
                that.accountDialogVisible = false
                that.initDataList()
              } else {
                that.$message.error(res.message)
              }
            })
          }
        }
      })
    },
    accountTypeFmt (row, column) {
      let val = row.accountType
      if (val === 1 || val === '1') {
        return '店员'
      } else if (val === 2 || val === '2') {
        return '店长'
      }
    },
    statusFmt (row, column) {
      let val = row.status
      if (val === 0 || val === '0') {
        return '已停用'
      } else if (val === 1 || val === '1') {
        return '使用中'
      }
    },
    setStoreListDialog (row) {
      this.accountStoreList = row.storeLists
      this.editAccount = row
      this.setStoresVisible = true
    },
    // 设置有权限门店
    setStoreList () {
      let params = {
        accountId: this.editAccount.accountId,
        act: 'config',
        storeList: this.accountStoreList
      }
      editAccountApi(params).then(res => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.setStoresVisible = false
          this.initDataList()
        } else {
          this.$message.error(res.content)
          this.setStoresVisible = false
        }
      })
    },
    // 操作
    edit (operate, row) {
      let that = this
      switch (operate) {
        case 'stop':
          that.$confirm('确认要停用吗？', '提醒', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            that.editRequest(operate, row)
          })
          break
        case 'start':
          that.$confirm('确认要启用吗？', '提醒', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            that.editRequest(operate, row)
          })
          break
        case 'del':
          that.$confirm('确认要删除吗？', '提醒', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            that.editRequest(operate, row)
          })
          break
        case 'edit':
          that.isEdit = true
          that.editAccountId = row.accountId
          that.accountDialogVisible = true
          getAccountApi(that.editAccountId).then(res => {
            if (res.error === 0) {
              res.content.storeList = res.content.storeLists
              that.accountForm = Object.assign({}, that.baseAccountForm, res.content)
            } else {
              that.$message.error(res.message)
            }
          })
          break
      }
    },
    // 停用，启用，删除操作
    editRequest (operate, row) {
      let params = {
        act: operate,
        accountId: row.accountId
      }
      editAccountApi(params).then(res => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.initDataList()
        } else {
          this.$message.error(res.content)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.list-con {
  .tableClass {
    min-height: 300px;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
    text-align: center;
  }
  .center {
    margin-top: 15px;
  }
  .form-input {
    width: 170px;
  }
  .form-tip {
    font-size: 12px;
    color: #999;
    line-height: 25px;
  }
  .iconSpan {
    font-size: 22px;
    color: #5a8bff;
    cursor: pointer;
  }
}
</style>
