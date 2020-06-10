<template>
  <div class="content">
    <div class='main_content'>
      <span>{{$t('distribution.distributorGroup')}}：</span>
      <el-input
        v-model="groupName"
        class="inputWidth"
        size="small"
        :placeholder="$t('distribution.contentTip')"
      ></el-input>
      <el-button
        size="small"
        type="primary"
        @click="initGroupList"
        style="margin-right: 10px;"
      >{{$t('distribution.screen')}}</el-button>
      <span class="showCfg">{{$t('distribution.switchDesc')}}：</span>
      <el-radio
        class="radio"
        v-model="v"
        :label="0"
      >{{$t('distribution.show')}}</el-radio>
      <el-radio
        class="radio"
        v-model="v"
        :label="1"
      >{{$t('distribution.notShow')}}</el-radio>
      <el-button
        size="small"
        type="primary"
        plain
        @click="saveShowHandler"
      >{{$t('distribution.save')}}</el-button>
    </div>
    <div>
      <el-button
        type="primary"
        size="small"
        @click="addGroupHandler"
      >{{$t('distribution.addDistributorGroup')}}</el-button>
    </div>
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="groupName"
          :label="$t('distribution.groupName')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('distribution.includeDistributorNum')"
          align="center"
        >
          <template slot-scope="scope">
            <p
              class="nameStyle"
              @click="amountHandler(scope.row.id)"
            >{{ scope.row.distributorAmount }}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          :label="$t('distribution.createTime')"
          align="center"
        >
        </el-table-column>
        <el-table-column align="center">
          <template slot="header">
            <el-tooltip
              effect="dark"
              :content="$t('distribution.isDefaultTip')"
              placement="top"
            >
              <span>{{$t('distribution.isDefaultGroup')}} <i class="el-icon-warning-outline"></i></span>
            </el-tooltip>
          </template>

          <template slot-scope="scope">
            <el-checkbox
              v-model="scope.row.isDefault"
              class="checkStyle"
              :true-label="1"
              :false-label="0"
              @change="setDefault(scope.row.id,scope.row.isDefault)"
            ></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column align="center">
          <template slot="header">
            <el-tooltip
              effect="dark"
              :content="$t('distribution.canSelectTip')"
              placement="top"
            >
              <span>{{$t('distribution.canSelect')}} <i class="el-icon-warning-outline"></i></span>
            </el-tooltip>
          </template>

          <template slot-scope="scope">
            <el-checkbox
              v-model="scope.row.canSelect"
              :true-label="1"
              :false-label="0"
              @change="setSelect(scope.row.id,scope.row.canSelect)"
            ></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          :label="$t('distribution.opt')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span @click="editHandler(scope.row.id, scope.row.groupName)">{{$t('distribution.groupEdit')}}</span>
              <span @click="delHandler(scope.row.id)">{{$t('distribution.groupDelete')}}</span>
              <span @click="addDistributor(scope.row.id)">{{$t('distribution.addGrouper')}}</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <Pagination
      :page-params.sync="pageParams"
      @pagination="initGroupList"
    />

    <!-- 添加分销员弹窗 -->
    <DistributorDialog
      :turnUp="turnUpDialog"
      :distributorGroup="distributorId"
      @handleSelect="handleSelectRow"
    />

    <!-- 添加分组弹窗 -->
    <el-dialog
      :title="typeFlag === 0 ? $t('distribution.addGroupTitle') : $t('distribution.editGroupTitle')"
      :visible.sync="groupDialogVisible"
      :close-on-click-modal="false"
      width="40%"
      center
    >
      <el-form
        ref="param"
        :model="param"
        :rules="paramRules"
        label-width="150px"
        labelPosition="right"
      >
        <el-form-item
          :label="$t('distribution.distributorGroupName') + '：'"
          prop="groupName"
        >
          <el-input
            v-model="param.groupName"
            size="small"
            class="inputWidth"
            :placeholder="$t('distribution.contentTip')"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('distribution.supportSelect') + '：'"
          prop="canSelect"
        >
          <el-radio-group v-model="param.canSelect">
            <el-radio :label="1">{{$t('distribution.selectTip1')}}</el-radio>
            <el-radio :label="0">{{$t('distribution.selectTip2')}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <p class="groupTip">{{$t('distribution.selectTip3')}}</p>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="cancelGroupHandler"
        >{{$t('distribution.inviteCancel')}}</el-button>
        <el-button
          type="primary"
          size="small"
          @click="saveGroupHandler"
        >{{$t('distribution.inviteSure')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  distributionGroup, distributionGroupDel, distributionGroupAdd,
  distributionGroupEdit, distributionGroupSave, addDistributor, setDefaultGroup, cancleDefaultGroup,
  setCanSelect, setGroupShow, getGroupShow
} from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination'),
    DistributorDialog: () => import('@/components/admin/distributorDialog')
  },
  data () {
    // 自定义校验名称
    var validatelevel = (rule, value, callback) => {
      let addResult = this.tableData.findIndex(item => { return value === item.groupName })
      let editResult = this.tableData.findIndex(item => { return value === item.groupName && value !== this.currentName })
      if (!value) {
        callback(new Error('请填写分组名称'))
      } else if (addResult !== -1 && this.typeFlag === 0) {
        callback(new Error('分组名称已存在, 请重新填写'))
      } else if (editResult !== -1 && this.typeFlag === 1) {
        callback(new Error('分组名称已存在, 请重新填写'))
      } else {
        callback()
      }
    }
    return {
      groupName: '', // 搜索条件
      v: 1, // 保存配置
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      // 添加分组
      param: {
        groupName: '',
        canSelect: 1
      },
      // 表单校验
      paramRules: {
        groupName: [
          { required: true, validator: validatelevel, trigger: 'change' }
        ],
        canSelect: [
          { required: true, message: '请选择用户是否支持', trigger: 'change' }
        ]
      },
      typeFlag: 0, // 类型: 0添加, 1编辑
      editId: '', // 编辑id
      currentName: '', // 编辑时当前分组名
      groupDialogVisible: false, // 分组弹窗
      turnUpDialog: false, // 分销员弹窗
      distributorId: null,
      selectRow: [], // 选中分销员id

      distributorList: []
    }
  },
  mounted () {
    // 初始化页面
    this.initGroupList()
    this.getGroupShow()
  },
  methods: {
    // 获取列表数据
    initGroupList () {
      var requestParams = {}
      if (this.groupName) {
        requestParams.groupName = this.groupName
      }
      requestParams.currentPage = this.pageParams.currentPage
      requestParams.pageRows = this.pageParams.pageRows
      distributionGroup(requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },

    // 获取小程序展示配置
    getGroupShow () {
      getGroupShow().then(res => {
        if (res.error === 0) {
          this.v = res.content
        }
      })
    },

    // 保存小程序展示配置
    saveShowHandler () {
      setGroupShow({ v: this.v }).then(res => {
        if (res.error === 0) {
          this.$message.success('展示设置成功')
        }
      })
    },

    // 添加按钮
    addGroupHandler () {
      this.groupDialogVisible = true
      this.param.groupName = ''
      this.param.canSelect = 1
    },

    // 编辑按钮
    editHandler (id, groupName) {
      this.currentName = groupName // 编辑当前分组名
      this.typeFlag = 1
      this.editId = id // 要操作的id
      this.groupDialogVisible = true
      distributionGroupEdit(id).then(res => {
        if (res.error === 0) {
          this.param.groupName = res.content.groupName
          this.param.canSelect = res.content.canSelect
        }
      })
    },

    // 保存分销员分组
    saveGroupHandler () {
      this.$refs['param'].validate((valid) => {
        if (valid) {
          // 关闭弹窗
          this.groupDialogVisible = false
          this.$refs['param'].resetFields()
          if (this.typeFlag === 0) {
            // 添加保存
            distributionGroupAdd(this.param).then(res => {
              if (res.error === 0) {
                this.$message.success({
                  message: '添加成功!'
                })
                this.initGroupList()
              }
            })
          } else {
            // 编辑保存
            this.param.id = this.id
            distributionGroupSave(this.param).then(res => {
              if (res.error === 0) {
                this.$message.success({
                  message: '编辑成功!'
                })
                this.initGroupList()
              }
            })
          }
        }
      })
    },

    // 取消添加分销员分组
    cancelGroupHandler () {
      // 关闭弹窗
      this.groupDialogVisible = false
      this.$refs['param'].resetFields()
    },

    // 删除分组
    delHandler (id) {
      this.$confirm('此操作将永久删除该分组, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        distributionGroupDel(id).then(res => {
          if (res.error === 0) {
            this.$message.success({
              message: '删除成功!'
            })
            this.initGroupList()
          }
        })
      }).catch(() => {

      })
    },

    // 添加分销员
    addDistributor (id) {
      this.turnUpDialog = !this.turnUpDialog
      this.distributorId = id // 要操作的id
    },

    // 分销员回调函数
    handleSelectRow (row) {
      this.selectRow = row
      addDistributor({
        groupId: this.distributorId,
        userIds: this.selectRow
      }).then(res => {
        if (res.error === 0) {
          this.$message.success({ message: '添加成功' })
          this.initGroupList()
        }
      })
    },

    // 设置默认分组
    setDefault (id, value) {
      if (value === 0) {
        // 取消默认分组
        this.$confirm('确认要取消默认分组吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          cancleDefaultGroup(id).then(res => {
            if (res.error === 0) {
              this.$message.success({
                message: '取消成功!'
              })
              this.initGroupList()
            }
          })
        }).catch(() => {
          // 恢复原值
          this.tableData.forEach((item, index) => {
            if (item.id === id) {
              item.isDefault = 1
            }
          })
        })
      } else {
        // 设置默认分组
        this.$confirm('确认要设为默认分组吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          setDefaultGroup(id).then(res => {
            if (res.error === 0) {
              this.$message.success({
                message: '设置默认分组成功!'
              })
              this.initGroupList()
            }
          })
        }).catch(() => {
          // 恢复原值
          this.tableData.forEach((item, index) => {
            if (item.id === id) {
              item.isDefault = 0
            }
          })
        })
      }
    },

    // 设置是否支持
    setSelect (id, value) {
      setCanSelect({
        groupId: id,
        canSelect: value
      }).then(res => {
        if (res.error === 0) {
          this.$message.success({
            message: '设置选择成功!'
          })
          this.initGroupList()
        }
      })
    },

    // 分销员数量跳转
    amountHandler (id) {
      this.$emit('tabChange')
      this.$emit('optGroupId', id)
    }

  }
}

</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main_content {
    position: flex;
    background-color: #fff;
    padding: 10px 20px 10px 0px;
  }
}

.inputWidth {
  width: 170px;
}
.nameStyle {
  color: #5a8bff;
  cursor: pointer;
}
.checkStyle /deep/.el-checkbox__input .el-checkbox__inner {
  border-radius: 50%;
}
.groupTip {
  color: #999;
  margin-left: 10px;
}
span {
  height: 40px;
  line-height: 40px;
}
.showCfg {
  margin-left: 30px;
}
.radio {
  line-height: 40px;
  margin-right: 10px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  .footer_right {
    padding: 20px 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    span {
      display: block;
      height: 32px;
      line-height: 32px;
    }
  }
}
.opt {
  text-align: center;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
.optionInput {
  width: 15%;
  margin-left: 10px;
  margin-right: 30px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 7px;
  vertical-align: middle !important;
  text-align: center;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}
</style>
