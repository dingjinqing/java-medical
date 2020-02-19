<template>
  <div class="content">
    <div class='main_content'>
      <span>分销员分组：</span>
      <el-input
        v-model="groupName"
        class="inputWidth"
        size="small"
        placeholder="请输入内容"
      ></el-input>
      <el-button
        size="small"
        type="primary"
        @click="initGroupList"
        style="margin-right: 10px;"
      >筛选</el-button>
      <span class="showCfg">分组是否展示在小程序端：</span>
      <el-radio
        class="radio"
        v-model="showRadio"
        label="1"
      >展示</el-radio>
      <el-radio
        class="radio"
        v-model="showRadio"
        label="2"
      >不展示</el-radio>
      <el-button
        size="small"
        type="primary"
        plain
        @click="saveShowHandler"
      >保存</el-button>
    </div>
    <div>
      <el-button
        type="primary"
        size="small"
        @click="addGroupHandler"
      >添加分销员分组</el-button>
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
          label="分组名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="包含分销员数量"
          align="center"
        >
          <template slot-scope="scope">
            <p class="nameStyle">{{ scope.row.distributorAmount }}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          align="center"
        >
        </el-table-column>
        <el-table-column align="center">
          <template slot="header">
            <el-tooltip
              effect="dark"
              content="用户成为分销员时，若未主动修改，将被默认添加到的分组"
              placement="top"
            >
              <span>是否为默认分组 <i class="el-icon-warning-outline"></i></span>
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
              content="用户在提交成为分销员申请时，是否可选择加入该分组"
              placement="top"
            >
              <span>可否选择 <i class="el-icon-warning-outline"></i></span>
            </el-tooltip>
          </template>

          <template slot-scope="scope">
            <el-checkbox></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span @click="editHandler(scope.row.id)">编辑</span>
              <span @click="delHandler(scope.row.id)">删除</span>
              <span @click="addDistributor(scope.row.id)">添加分销员</span>
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
    <!-- :selectRowIds="addData.userIds" -->
    <DistributorDialog
      :turnUp="turnUpDialog"
      @handleSelect="handleSelectRow"
    />

    <!-- 添加分组弹窗 -->
    <el-dialog
      title="添加分销员分组"
      :visible.sync="addGroupDialog"
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
          label="分销员分组名称："
          prop="groupName"
        >
          <el-input
            v-model="param.groupName"
            size="small"
            class="inputWidth"
            placeholder="请输入分组名称"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="是否支持用户选择："
          prop="groupRadio"
        >
          <el-radio-group v-model="param.groupRadio">
            <el-radio :label="1">支持</el-radio>
            <el-radio :label="0">不支持</el-radio>
          </el-radio-group>
        </el-form-item>
        <p class="groupTip">用户在申请成为分销员时，是否可以选择加入此分组</p>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="addGroupDialog = false"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="saveGroupHandler"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  distributionGroup, distributionGroupDel, distributionGroupAdd,
  distributionGroupEdit, distributionGroupSave, addDistributor, setDefaultGroup, cancleDefaultGroup
} from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination'),
    DistributorDialog: () => import('@/components/admin/distributorDialog')
  },
  data () {
    return {
      groupName: '', // 搜索条件
      showRadio: '1', // 保存配置
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      // 添加分组
      param: {
        groupName: '',
        groupRadio: 1
      },
      // 表单校验
      paramRules: {
        groupName: [
          { required: true, message: '请填写分组名称', trigger: 'blur' }
        ],
        groupRadio: [
          { required: true, message: '请选择用户是否支持', trigger: 'change' }
        ]
      },
      typeFlag: 0, // 类型: 0添加, 1编辑
      editId: '', // 编辑id
      addGroupDialog: false, // 分组弹窗
      turnUpDialog: false, // 分销员弹窗
      distributorId: '',
      selectRow: [], // 选中分销员id

      distributorList: []
      // allChecked: false,
      // allCheckFlag: false,
      // hasCheck: '',
      // addData: {
      //   userIds: [],
      //   groupId: ''
      // }
    }
  },
  // watch: {
  //   allChecked (newData) {
  //     console.log(newData)
  //     if (newData) {
  //       this.distributorList.map((item, index) => {
  //         item.ischecked = true
  //       })
  //     } else {
  //       if (this.allCheckFlag === false) {
  //         this.distributorList.map((item, index) => {
  //           item.ischecked = false
  //         })
  //       }
  //     }
  //   }
  // },
  mounted () {
    // 初始化页面
    this.initGroupList()
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

    // 保存小程序展示配置
    saveShowHandler () {

    },

    // 添加按钮
    addGroupHandler () {
      this.addGroupDialog = true
      this.param.groupName = ''
      this.param.groupRadio = 1
    },

    // 编辑按钮
    editHandler (id) {
      this.typeFlag = 1
      this.editId = id // 要操作的id
      this.addGroupDialog = true
      distributionGroupEdit(id).then(res => {
        if (res.error === 0) {
          this.param.groupName = res.content.groupName
          this.param.groupRadio = res.content.groupRadio
        }
      })
    },

    // 保存分销员分组
    saveGroupHandler () {
      this.$refs['param'].validate((valid) => {
        if (valid) {
          // 关闭弹窗
          this.addGroupDialog = false
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

    // 表格对应行选中高亮
    // handleClick () {
    //   console.log(this.distributorList)
    //   let noCheck = this.distributorList.filter((item, index) => {
    //     return item.ischecked === false
    //   })
    //   let hasCheck = this.distributorList.filter((item, index) => {
    //     return item.ischecked === true
    //   })
    //   this.hasCheck = hasCheck
    //   if (!noCheck.length) {
    //     this.allChecked = true
    //   } else {
    //     this.allCheckFlag = true
    //     this.allChecked = false
    //   }
    //   this.$forceUpdate()
    // },
    // 全选本页 - 全部checkbox选中
    // handleAllcheck () {
    //   this.allCheckFlag = false
    // },
    // cancel () {
    //   this.centerDialogVisible = false
    // },

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
                message: '设置成功!'
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
