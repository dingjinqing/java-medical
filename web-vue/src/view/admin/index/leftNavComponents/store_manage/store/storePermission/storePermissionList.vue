<template>
  <div class="list-con">
    <div class="top">
      <el-form
        size="small"
        :inline="true"
        class="store-permission-form"
      >
        <el-form-item :label="$t('storePermission.storeAccount')+'：'">
          <el-input
            class="form-input"
            v-model="queryParams.accountName"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('storePermission.phone')+'：'">
          <el-input
            class="form-input"
            v-model="queryParams.mobile"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('storePermission.roles')+'：'">
          <el-select
            v-model="queryParams.accountType"
            class="form-input"
          >
            <el-option
              :label="$t('storePermission.all')"
              :value="0"
            ></el-option>
            <el-option
              :label="$t('storePermission.storeManager')"
              :value="2"
            ></el-option>
            <el-option
              :label="$t('storePermission.clerk')"
              :value="1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('storePermission.accountStatus')+'：'">
          <el-select
            v-model="queryParams.status"
            class="form-input"
          >
            <el-option
              :label="$t('storePermission.all')"
              :value="-1"
            ></el-option>
            <el-option
              :label="$t('storePermission.using')"
              :value="1"
            ></el-option>
            <el-option
              :label="$t('storePermission.terminated')"
              :value="0"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('storePermission.filter')}}</el-button>
      </el-form>
      <div>
        <el-button
          type="primary"
          size="small"
          @click="addAccountHandle"
        >{{$t('storePermission.addAccount')}}</el-button>
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
          :label="$t('storePermission.storeAccount')"
          prop="accountName"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('storePermission.phone')"
          prop="mobile"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('storePermission.roles')"
          prop="accountType"
          :formatter="accountTypeFmt"
          align="center"
        ></el-table-column>
        <el-table-column :label="$t('storePermission.AuthorizedStore')">
          <template slot-scope="{row}">
            <div style="text-align:center;">
              <span v-text="row.storeLists.length"></span>
              <el-button
                type="text"
                @click="setStoreListDialog(row)"
              >{{$t('storePermission.settings')}}</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('storePermission.accountStatus')"
          :formatter="statusFmt"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('storePermission.operate')"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              <el-tooltip
                v-if="row.status === 1 || row.stauts==='1'"
                :content="$t('storePermission.disable')"
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
                :content="$t('storePermission.enable')"
                effect="light"
                placement="top"
              >
                <i
                  class="el-icon-circle-check iconSpan"
                  @click="edit('start', row)"
                ></i>
              </el-tooltip>
              <el-tooltip
                :content="$t('storePermission.delete')"
                effect="light"
                placement="top"
              >
                <i
                  class="el-icon-delete iconSpan"
                  @click="edit('del', row)"
                ></i>
              </el-tooltip>
              <el-tooltip
                :content="$t('storePermission.edit')"
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
      :title="isEdit?$t('storePermission.editAccount'):$t('storePermission.addAccount')"
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
            :label="$t('storePermission.accountRole')+'：'"
            prop="accountType"
          >
            <el-select
              class="form-input"
              v-model="accountForm.accountType"
            >
              <el-option
                :label="$t('storePermission.storeManager')"
                :value="2"
              ></el-option>
              <el-option
                :label="$t('storePermission.clerk')"
                :value="1"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            :label="$t('storePermission.storeAccount')+'：'"
            prop="accountName"
          >
            <el-input
              class="form-input"
              v-model="accountForm.accountName"
              maxlength="20"
              :placeholder="$t('storePermission.storeLimit1')"
            ></el-input>
            <p class="form-tip">{{$t('storePermission.storeLimit2')}}</p>
          </el-form-item>
          <el-form-item
            :label="$t('storePermission.phone')+'：'"
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
            :label="$t('storePermission.pwd')+'：'"
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
            <p class="form-tip">{{$t('storePermission.pwdLimit')}}</p>
          </el-form-item>
          <el-form-item
            :label="$t('storePermission.AuthorizedStore')+'：'"
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
        >{{$t('storePermission.save')}}</el-button>
        <el-button
          size="small"
          @click="accountDialogVisible = false"
        >{{$t('storePermission.cancel')}}</el-button>
      </div>
    </el-dialog>
    <!-- 设置权限门店 -->
    <el-dialog
      :title="$t('storePermission.templateSet')"
      :visible.sync="setStoresVisible"
      width="500px"
    >
      <div>
        <span style="width:110px;">{{$t('storePermission.chooseStore')}}：</span>
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
        >{{$t('storePermission.yes')}}</el-button>
        <el-button
          @click="setStoresVisible = false"
          size="small"
        >{{$t('storePermission.cancel')}}</el-button>
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
        callback(new Error(that.$t('storePermission.peAccountName')))
      }
      if (!/^[^\u4e00-\u9fa5]{1,20}$/.test(value)) {
        callback(new Error(that.$t('storePermission.peCorrectAN')))
      }
      callback()
    }
    // 校验手机号
    function validMobile (rule, value, callback) {
      if (!value) {
        callback(new Error(that.$t('storePermission.pePhone')))
      }
      let reg = /^1[3|7|8]\d{9}$|^19[8-9]\d{8}$|^166\d{8}|^15[0-3|5-9]\d{8}|^14[5|7]\d{8}$/
      if (!reg.test(value)) {
        callback(new Error(that.$t('storePermission.peCorrectP')))
      }
      callback()
    }
    function validPassword (rule, value, callback) {
      if (!value && !that.isEdit) {
        callback(new Error(that.$t('storePermission.pePwd')))
      }
      if (value === '' && that.isEdit) {
        callback()
      }
      if (!/^[^\u4e00-\u9fa5]{6,16}$/.test(value)) {
        callback(new Error(that.$t('storePermission.peCorrectPwd')))
      }
      callback()
    }
    function validStoreList (rule, value, callback) {
      if (!value || value.length === 0) {
        callback(new Error(that.$t('storePermission.psPStore')))
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
        accountType: [{ required: true, message: that.$t('storePermission.psAr'), trigger: 'blur' }],
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
        return this.$t('storePermission.clerk')
      } else if (val === 2 || val === '2') {
        return this.$t('storePermission.storeManager')
      }
    },
    statusFmt (row, column) {
      let val = row.status
      if (val === 0 || val === '0') {
        return this.$t('storePermission.terminated')
      } else if (val === 1 || val === '1') {
        return this.$t('storePermission.using')
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
          that.$confirm(that.$t('storePermission.ayStop'), that.$t('storePermission.remind'), {
            confirmButtonText: that.$t('storePermission.yes'),
            cancelButtonText: that.$t('storePermission.cancel'),
            type: 'warning'
          }).then(() => {
            that.editRequest(operate, row)
          })
          break
        case 'start':
          that.$confirm(that.$t('storePermission.ayStart'), that.$t('storePermission.remind'), {
            confirmButtonText: that.$t('storePermission.yes'),
            cancelButtonText: that.$t('storePermission.cancel'),
            type: 'warning'
          }).then(() => {
            that.editRequest(operate, row)
          })
          break
        case 'del':
          that.$confirm(that.$t('storePermission.ayDelete'), that.$t('storePermission.remind'), {
            confirmButtonText: that.$t('storePermission.yes'),
            cancelButtonText: that.$t('storePermission.cancel'),
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
