<template>
  <div class="content">
    <div class='main_content'>
      <span>分销员分组：</span>
      <el-input
        v-model="groupName"
        class="groupName"
        size="medium"
        placeholder="请输入内容"
      ></el-input>
      <el-button
        @click="groupList"
        size="small"
        type="primary"
      >筛选</el-button>
      <span class="showCfg">分组是否展示在小程序端：</span>
      <el-radio
        class="radio"
        v-model="radio"
        label="1"
      >展示</el-radio>
      <el-radio
        class="radio"
        v-model="radio"
        label="2"
      >不展示</el-radio>
      <el-button
        size="small"
        type="primary"
        plain
      >保存</el-button>
    </div>
    <div>
      <el-button
        type="primary"
        size="small"
        @click="addGroup"
      >添加分销员分组</el-button>
    </div>
    <el-dialog
      title="添加分销员分组"
      :visible.sync="dialogVisible"
      width="20%"
      center
    >
      <span>分销员分组名称</span>
      <el-input
        v-model="param.groupName"
        placeholder="请输入分组名称"
      ></el-input>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="confirm"
        >确 定</el-button>
      </span>
    </el-dialog>
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
          prop="distributorAmount"
          label="包含分销员数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="创建时间"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="isDefault"
          label="是否为默认分组"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span @click="setDefault(scope.row.id,scope.row.isDefault)">{{scope.row.isDefault}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span @click="edit(scope.row.id)">编辑</span>
              <span @click="del(scope.row.id)">删除</span>
              <span @click="addDistributor(scope.row.id)">添加分销员</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- 添加分销员弹窗 -->
      <el-dialog
        title="添加分销员"
        :visible.sync="centerDialogVisible"
        width="70%"
        center
      >
        <div>
          <span>分销员手机号:</span>
          <el-input
            class="optionInput"
            prop="title"
            size="small"
          ></el-input>
          <span>分销员昵称:</span>
          <el-input
            class="optionInput"
            prop="title"
            size="small"
          ></el-input>
          <span>真实姓名:</span>
          <el-input
            class="optionInput"
            prop="title"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>分销员ID:</span>
          <el-input
            class="optionInput"
            prop="title"
            size="small"
          ></el-input>
          <span>分销员等级:</span>
          <el-select
            class="optionInput"
            size="small"
            v-model="valueLevel"
            placeholder="请选择等级"
          >
            <el-option
              v-for="level in groupLevelList"
              :key="level.levelId"
              :label="level.label"
              :value="level.levelName"
            >
            </el-option>
          </el-select>
          <span>分销员分组:</span>
          <el-select
            class="optionInput"
            size="small"
            v-model="valueGroup"
            placeholder="请选择分组"
          >
            <el-option
              v-for="group in groupNameList"
              :key="group.id"
              :label="group.groupName"
              :value="group.groupName"
            >
            </el-option>
          </el-select>
        </div>
        <section style="padding: 10px 0;">
          <div>
            <el-button
              type="primary"
              size="small"
            >筛选</el-button>
            <el-button
              type="primary"
              size="small"
              plain
            >重置</el-button>
          </div>
        </section>

        <div>
          <table width='100%'>
            <!-- 表头部分 -->
            <thead>
              <tr>
                <td>
                  <el-checkbox
                    v-model="allChecked"
                    @change="handleAllcheck()"
                  >全选本页</el-checkbox>
                </td>
                <td>分销员ID</td>
                <td>分销员手机号</td>
                <td>分销员昵称</td>
                <td>真实姓名</td>
                <td>下级用户数</td>
                <td>累计获得佣金金额</td>
                <td>分销员组</td>
                <td>当前等级</td>
              </tr>
            </thead>
            <!-- 表格数据部分 -->
            <tbody>
              <tr
                v-for="(item,index) in distributorList"
                :key="index"
              >
                <td>
                  <div class="tdCenter">
                    <el-checkbox
                      @change="handleClick()"
                      v-model="item.ischecked"
                    ></el-checkbox>
                  </div>
                </td>
                <td class="isLeft">
                  {{item.userId}}

                </td>
                <td class="tb_decorate_a">
                  {{item.mobile}}
                </td>
                <td class="tb_decorate_a">
                  {{item.username}}
                </td>
                <td class="tb_decorate_a">
                  {{item.realName}}
                </td>
                <td class="tb_decorate_a">
                  {{item.nextNumber}}
                </td>
                <td class="tb_decorate_a">
                  {{item.totalFanliMoney}}
                </td>
                <td class="tb_decorate_a">
                  {{item.groupName}}
                </td>
                <td class="tb_decorate_a">
                  {{item.levelName}}
                </td>

              </tr>
            </tbody>
          </table>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="cancel">取 消</el-button>
          <el-button
            v-model="opt"
            type="primary"
            @click="addConfirm()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="groupList"
    />
  </div>
</template>

<script>
import {
  distributionGroup, distributionGroupDel, distributionGroupAdd,
  distributionGroupEdit, distributionGroupSave, distributorList,
  distributorLevelList, distributorGroupList, addDistributor, setDefaultGroup, cancleDefaultGroup
} from '@/api/admin/marketManage/distribution.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      groupNameList: [],
      groupLevelList: [],
      distributorList: [],
      valueGroup: '',
      valueLevel: '',
      tableData: [],
      radio: '1',
      currentPage: null,
      pageParams: {},
      param: {
        groupName: ''
      },
      groupName: null,
      opt: 0,
      dialogVisible: false,
      centerDialogVisible: false,
      id: '',
      allChecked: false,
      allCheckFlag: false,
      goodsIdsArr: [],
      hasCheck: '',
      addData: {
        userIds: [],
        groupId: ''
      }

    }
  },
  created () {
    this.groupList()
  },
  mounted () {
    this.groupList()
  },
  watch: {
    allChecked (newData) {
      console.log(newData)
      if (newData) {
        this.distributorList.map((item, index) => {
          item.ischecked = true
        })
      } else {
        if (this.allCheckFlag === false) {
          this.distributorList.map((item, index) => {
            item.ischecked = false
          })
        }
      }
    }
  },
  methods: {
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => { })
    },
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },
    groupList () {
      this.pageParams.groupName = this.groupName
      console.log(this.pageParams)
      distributionGroup(this.pageParams).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },
    handleData (data) {
      data.map((item, index) => {
        if (item.isDefault === 1) {
          item.isDefault = '是 取消默认'
        } else {
          item.isDefault = '否 设为默认'
        }
      })
      this.tableData = data
    },
    del (id) {
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
            this.groupList()
          }
        })
      }).catch(() => {

      })
    },
    addGroup () {
      this.dialogVisible = true
      this.param.groupName = ''
    },
    // 添加分组
    confirm () {
      this.dialogVisible = false
      if (this.opt === 0) {
        distributionGroupAdd(this.param).then(res => {
          if (res.error === 0) {
            this.$message.success({
              message: '添加成功!'
            })
            this.groupList()
          }
        })
      } else {
        this.param.id = this.id
        distributionGroupSave(this.param).then(res => {
          if (res.error === 0) {
            console.log(res)
            this.$message.success({
              message: '编辑成功!'
            })
            this.groupList()
          }
        })
      }
    },
    // 编辑分组
    edit (id) {
      distributionGroupEdit(id).then(res => {
        if (res.error === 0) {
          this.param.groupName = res.content.groupName
          this.id = res.content.id
        }
      })
      this.dialogVisible = true
      this.opt = 1
    },
    // 添加分销员
    addDistributor (id) {
      this.addData.groupId = id
      this.centerDialogVisible = true
      distributorGroupList().then(res => {
        this.groupNameList = res.content
      })

      distributorLevelList().then(res => {
        this.groupLevelList = res.content
        this.distributorLevel = res.content.dataList
      })
      distributorList(this.pageParams).then(res => {
        if (res.error === 0) {
          console.log(res.content.dataList)
          res.content.dataList.filter(item => {
            item.ischecked = false
          })
          this.allCheckFlag = false
          this.allChecked = false
          this.distributorList = res.content.dataList
        }

        console.log(res)
      })
      console.log(this.hasCheck)
    },
    // 表格对应行选中高亮
    handleClick () {
      console.log(this.distributorList)
      let noCheck = this.distributorList.filter((item, index) => {
        return item.ischecked === false
      })
      let hasCheck = this.distributorList.filter((item, index) => {
        return item.ischecked === true
      })
      this.hasCheck = hasCheck
      if (!noCheck.length) {
        this.allChecked = true
      } else {
        this.allCheckFlag = true
        this.allChecked = false
      }
      this.$forceUpdate()
    },
    // 全选本页 - 全部checkbox选中
    handleAllcheck () {
      this.allCheckFlag = false
    },
    cancel () {
      this.centerDialogVisible = false
    },
    addConfirm () {
      this.hasCheck.map((item, index) => {
        this.addData.userIds.push(item.userId)
      })
      addDistributor(this.addData).then(res => {
        if (res.error === 0) {
          this.$message.success({
            message: '添加成功'
          })
          this.groupList()
        }
      })

      this.centerDialogVisible = false
    },
    // 设置默认分组
    setDefault (id, v) {
      if (v === '否 设为默认') {
        setDefaultGroup(id).then(res => {
          if (res.error === 0) {
            this.$message.success({
              message: '设置成功!'
            })
            this.groupList()
          }
        })
      } else { // 取消默认分组
        cancleDefaultGroup(id).then(res => {
          if (res.error === 0) {
            this.$message.success({
              message: '取消成功!'
            })
            this.groupList()
          }
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

.groupName {
  width: 12%;
  margin-right: 10px;
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
