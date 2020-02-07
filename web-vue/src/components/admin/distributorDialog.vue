<template>
  <div v-if="dialogTableVisible">
    <el-dialog
      title="添加分销员"
      :visible.sync="dialogTableVisible"
      :close-on-click-modal="false"
      :append-to-body='true'
      width="70%"
      center
    >
      <el-form label-width="140px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="分销员手机号：">
              <el-input
                size="small"
                placeholder="请输入手机号"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员昵称：">
              <el-input
                size="small"
                placeholder="请输入昵称"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="真实姓名：">
              <el-input
                size="small"
                placeholder="请输入姓名"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="分销员ID：">
              <el-input
                size="small"
                placeholder="请输入分销员ID"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员等级：">
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
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员分组：">
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
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="text-align: center;">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button
            type="primary"
            size="small"
            plain
          >重置</el-button>
        </el-row>
      </el-form>

      <el-table
        ref="multipleTable"
        :data="distributorList"
        @selection-change="handleSelectionChange"
        border
        style="margin-top: 20px;"
      >
        <el-table-column
          type="selection"
          width="55"
        >
        </el-table-column>
        <el-table-column
          prop="userId"
          label="分销员ID"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="mobile"
          label="分销员手机号"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="username"
          label="分销员昵称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="realName"
          label="真实姓名"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="nextNumber"
          label="下级用户数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="totalFanliMoney"
          label="累计获得佣金金额"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="groupName"
          label="分销员组"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="levelName"
          label="当前等级"
          align="center"
        >
        </el-table-column>
      </el-table>

      <Pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />

      <span slot="footer">
        <el-button
          size="small"
          @click="cancelHandler()"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="sureHandler()"
        >确 定</el-button>
      </span>

    </el-dialog>
  </div>
</template>

<script>
import { distributorList, distributorLevelList, distributorGroupList, manualAddDistributor } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination')
  },
  props: {
    // 调起弹窗
    turnUp: {
      type: Boolean,
      default: () => false
    },
    // 选中的数据id
    selectRowIds: {
      type: Array,
      default () {
        return []
      }
    },
    // 当前等级
    level: {
      type: Number
    },
    // 当前已选中分销员
    userIds: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      dialogTableVisible: false, // 分销员弹框
      pageParams: {}, // 分页
      valueLevel: '',
      valueGroup: '',
      groupLevelList: [], // 分销员等级
      groupNameList: [], // 分销员分组
      distributorList: [], // 分销员表格
      multipleData: [] // 分销员选中
    }
  },
  watch: {
    turnUp (newData) {
      this.dialogTableVisible = true
      this.initDataList()
    }
  },
  methods: {
    initDataList () {
      // 等级下拉框
      distributorLevelList().then(res => {
        if (res.error === 0) {
          this.groupLevelList = res.content
        }
      })
      // 分组下拉框
      distributorGroupList().then(res => {
        if (res.error === 0) {
          this.groupNameList = res.content
        }
      })
      // 分销员表格
      distributorList(this.pageParams).then(res => {
        if (res.error === 0) {
          this.distributorList = res.content.dataList
          this.pageParams = res.content.page
          // 数据回显
          this.$nextTick(() => {
            if (this.userIds !== '' && this.userIds !== null) {
              this.userIds = this.userIds.split(',')
              this.userIds.map(item => {
                this.distributorList.map((row, index) => {
                  if (item === row.userId) {
                    this.$refs.multipleTable.toggleRowSelection(row, true)
                  }
                })
              })
            }
          })
        }
      })
    },

    // 获取选中数据
    handleSelectionChange (val) {
      this.multipleData = val
    },

    // 确定添加
    sureHandler () {
      var userIds = []
      this.multipleData.filter((item, index) => {
        userIds.push(item.userId)
      })
      manualAddDistributor({
        level: this.level,
        userIds: userIds
      }).then(res => {
        if (res.error === 0) {
          this.$emit('handleSelect', userIds)
          this.dialogTableVisible = false
          this.$message.success({ message: '添加成功!' })
        }
      })
    },

    // 取消添加
    cancelHandler () {
      this.dialogTableVisible = false
      this.$message.info({ message: '已取消添加!' })
    }
  }
}

</script>
<style lang="scss" scoped>
</style>
