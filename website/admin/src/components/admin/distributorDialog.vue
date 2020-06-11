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
      <el-form label-width="120px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="分销员手机号：">
              <el-input
                v-model="form.mobile"
                size="small"
                placeholder="请输入手机号"
                class="inputStyle"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员昵称：">
              <el-input
                v-model="form.username"
                size="small"
                placeholder="请输入昵称"
                class="inputStyle"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="真实姓名：">
              <el-input
                v-model="form.realName"
                size="small"
                placeholder="请输入姓名"
                class="inputStyle"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="分销员ID：">
              <el-input
                v-model="form.distributorId"
                size="small"
                placeholder="请输入分销员ID"
                class="inputStyle"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员等级：">
              <el-select
                v-model="form.distributorLevel"
                size="small"
                placeholder="请选择等级"
                class="inputStyle"
                clearable
              >
                <el-option
                  v-for="(item, index) in levelDataList"
                  :key="index"
                  :label="item.levelName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员分组：">
              <el-select
                v-model="form.distributorGroup"
                size="small"
                placeholder="请选择分组"
                class="inputStyle"
                clearable
              >
                <el-option
                  v-for="(item, index) in groupDataList"
                  :key="index"
                  :label="item.groupName"
                  :value="item.id"
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
            @click="initDataList()"
          >筛选</el-button>
          <el-button
            type="primary"
            size="small"
            plain
            @click="resetFromHandler()"
          >重置</el-button>
        </el-row>
      </el-form>

      <el-table
        header-row-class-name="tableClss"
        ref="multipleTable"
        :data="distributorList"
        @selection-change="handleSelectionChange"
        border
        style="margin-top: 20px;"
      >
        <el-table-column
          type="selection"
          align="center"
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
          width="150"
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
import { distributorList, distributorLevelList, distributorAllGroup } from '@/api/admin/marketManage/distribution.js'
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
    // 当前分销员分组
    // distributorGroup: {
    //   type: Number
    // },
    // 当前分销员等级
    // distributorLevel: {
    //   type: Number
    // },
    // 选中的数据id
    selectRowIds: {
      type: Array,
      default () {
        return []
      }
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
      levelDataList: [], // 分销员等级
      groupDataList: [], // 分销员分组
      form: {
        mobile: '',
        username: '',
        realName: '',
        distributorId: '',
        distributorLevel: '',
        distributorGroup: ''
      },
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      distributorList: [], // 分销员表格
      multipleData: [] // 分销员选中
    }
  },
  watch: {
    turnUp (newData) {
      this.dialogTableVisible = true

      // this.form.distributorGroup = this.distributorGroup ? this.distributorGroup : '' // 当前分销员分组
      // this.form.distributorLevel = this.distributorLevel ? this.distributorLevel : '' // 当前分销员等级
      this.initDataList()
    }
  },
  mounted () {
    // 获取分销员等级数据
    distributorLevelList().then(res => {
      if (res.error === 0) {
        var data = res.content
        data.map((item, index) => {
          switch (item.levelId) {
            case 1:
              item.levelText = '一级'
              break
            case 2:
              item.levelText = '二级'
              break
            case 3:
              item.levelText = '三级'
              break
            case 4:
              item.levelText = '四级'
              break
            case 5:
              item.levelText = '五级'
              break
          }
        })
        this.levelDataList = data
      }
    })
    // 获取分销员分组数据
    distributorAllGroup().then(res => {
      this.groupDataList = res.content
    })
  },
  methods: {
    initDataList () {
      var data = this.form
      for (var key in data) {
        if (data[key] === '') {
          delete data[key]
        }
      }
      data.currentPage = this.pageParams.currentPage
      data.pageRows = this.pageParams.pageRows
      distributorList(data).then(res => {
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

    // 重置筛选
    resetFromHandler () {
      this.form = {
        mobile: '',
        username: '',
        realName: '',
        distributorId: '',
        distributorLevel: '',
        distributorGroup: ''
      }
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

      this.$emit('handleSelect', userIds)
      this.dialogTableVisible = false
    },

    // 取消添加
    cancelHandler () {
      this.dialogTableVisible = false
    }
  }
}

</script>
<style lang="scss" scoped>
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 0;
}
.inputStyle {
  width: 170px;
}
</style>
