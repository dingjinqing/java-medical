<template>
  <div class="distributorListContent">
    <div class="searchInfo_main">
      <el-form
        :model="param"
        label-width="140px"
        :label-position="'right'"
      >
        <div>
          <el-form-item
            label="手机号："
            class="item"
          >
            <el-input
              v-model="param.mobile"
              placeholder="请填写手机号"
              size="small"
              class="inputWidth"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="微信昵称："
            class="item"
          >
            <el-input
              v-model="param.username"
              placeholder="请填写微信昵称"
              size="small"
              class="inputWidth"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="真实姓名："
            class="item"
          >
            <el-input
              v-model="param.realName"
              placeholder="请填写真实姓名"
              size="small"
              class="inputWidth"
            ></el-input>
          </el-form-item>
        </div>
        <div>
          <el-form-item
            label="被邀请用户手机号："
            class="item"
          >
            <el-input
              v-model="param.invitedMobile"
              placeholder="请填写被邀请用户手机号"
              size="small"
              class="inputWidth"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="注册时间："
            class="item"
          >
            <el-date-picker
              v-model="param.startCreateTime"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd 00:00:00"
              size="small"
              class="inputWidth"
            >
            </el-date-picker>
            <span>至</span>
            <el-date-picker
              v-model="param.endCreateTime"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd 23:59:59"
              size="small"
              class="inputWidth"
            >
            </el-date-picker>
          </el-form-item>
        </div>
        <div>
          <el-form-item
            label="被邀请用户昵称："
            class="item"
          >
            <el-input
              v-model="param.invitedUserName"
              placeholder="请填写被邀请用户昵称"
              size="small"
              class="inputWidth"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="分销员等级："
            class="item"
          >
            <el-select
              v-model="param.distributorLevel"
              placeholder="请选择等级"
              size="small"
              class="inputWidth"
            >
              <el-option
                v-for="level in groupLevelList"
                :key="level.levelId"
                :label="level.levelName"
                :value="level.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="分销员分组："
            class="item"
          >
            <el-select
              v-model="param.distributorGroup"
              placeholder="请选择分组"
              size="small"
              class="inputWidth"
            >
              <el-option
                v-for="group in groupNameList"
                :key="group.id"
                :label="group.groupName"
                :value="group.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div style="overflow: auto;">
          <div
            class="item"
            style="float: left;"
          >
            <el-checkbox
              v-model="param.haveNextUset"
              :true-label="1"
              :false-label="0"
            >有下级用户</el-checkbox>
            <el-checkbox
              v-model="param.haveMobile"
              :true-label="1"
              :false-label="0"
            >有手机号</el-checkbox>
            <el-checkbox
              v-model="param.haveRealName"
              :true-label="1"
              :false-label="0"
            >有真实姓名</el-checkbox>
          </div>
          <div
            class="item"
            style="float: right;"
          >
            <el-button
              @click="initDataList"
              type="primary"
              size="small"
            >筛选</el-button>
            <el-button
              size="small"
              @click="exportDataList"
            >导出</el-button>
          </div>
        </div>
      </el-form>

    </div>
    <div class="tableInfo">
      <div class="notice">
        <span>注：</span>
        <span>未开启分销员审核时，列表只展示有下级用户的分销员</span>
      </div>
      <div class="table_list">
        <el-table
          ref="multipleTable"
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            type="selection"
            width="55"
          >
          </el-table-column>
          <el-table-column
            prop="userId"
            label="ID"
            align="center"
          >
          </el-table-column>

          <el-table-column
            label="分销员昵称"
            align="center"
          >
            <template slot-scope="scope">
              <span
                class="nameStyle"
                @click="userNameHandler(scope.row.userId)"
              >{{ scope.row.username }}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="mobile"
            label="分销员手机号"
            align="center"
          >
          </el-table-column>

          <el-table-column
            label="邀请人"
            align="center"
          >
            <template slot-scope="scope">
              <span
                class="nameStyle"
                @click="userNameHandler(scope.row.inviteId)"
                v-if="scope.row.inviteName !== 'null'"
              >{{ scope.row.inviteName }}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="createTime"
            label="注册时间"
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
            align="center"
            v-if="inviteFlag === true"
          >
            <template slot="header">
              <el-tooltip
                effect="dark"
                content="用户在申请成为分销员时，填写其邀请人的邀请码，则成为分销员后将成为该邀请人的下级"
                placement="top"
              >
                <span>邀请码 <i class="el-icon-question"></i></span>
              </el-tooltip>
            </template>
            <template slot-scope="scope">
              <p v-if="scope.row.invitationCode">{{ scope.row.invitationCode }}</p>
              <p
                class="nameStyle"
                @click="invitationCodeHandler(scope.row.userId, scope.row.invitationCode)"
              >设置</p>
            </template>
          </el-table-column>

          <el-table-column
            label="分销员分组"
            align="center"
          >
            <template slot-scope="scope">
              <p v-if="scope.row.groupName">{{ scope.row.groupName }}</p>
              <p v-if="!scope.row.groupName">未分组</p>
              <p
                class="nameStyle"
                @click="groupNameHandler(scope.row.userId, scope.row.groupName)"
              >编辑</p>
            </template>
          </el-table-column>

          <el-table-column
            prop="levelName"
            label="分销员等级"
            align="center"
          >
          </el-table-column>

          <el-table-column align="center">
            <template slot="header">
              下级用户数 <i class="el-icon-bottom"></i>
            </template>
            <template slot-scope="scope">
              <span
                class="nameStyle"
                @click="nextNumberHandler"
              >{{ scope.row.nextNumber }}</span>
            </template>
          </el-table-column>

          <el-table-column
            label="间接邀请用户数"
            align="center"
          >
            <template slot-scope="scope">
              <span
                class="nameStyle"
                @click="sublayerNumberHandler"
              >{{ scope.row.sublayerNumber }}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="totalCanFanliMoney"
            label="累计返利商品总额"
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
            prop="waitFanliMoney"
            label="待返利佣金金额"
            align="center"
          >
          </el-table-column>

          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="opt">
                <p @click="inviteUserList(scope.row.userId)">查看已邀请用户</p>
                <p>查看返利佣金明细</p>
                <p @click="remarksHandler(scope.row.userId)">备注</p>
                <p @click="del(scope.row.userId)">清除</p>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <!-- 全选修改分销员分组 -->
        <div class="checkedStyle">
          <el-checkbox
            v-model="allChecked"
            @change="checkChange"
          ></el-checkbox>
          全选当前页
          <el-select
            v-model="checkedValue"
            placeholder="请选择等级"
            size="small"
            class="checkboxWidth"
            @change="selectChange"
          >
            <el-option
              v-for="(item, index) in checkList"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>

        </div>
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>

    <!-- 邀请码弹窗 -->
    <inviteCodeDialog
      :tuneUp="invitationDialog"
      :inviteCodeBack="inviteCode"
      @resultCodeRow="choosingCodeResult"
    />

    <!-- 分销员分组 -->
    <groupDialog
      :tuneUp="groupDialog"
      :groupBack="groupValue"
      @resultGroupRow="choosingGroupResult"
    />

    <!-- 备注弹窗 -->
    <remarksDialog
      :tuneUp="remarksDialog"
      :userId="remarksUserId"
    />

    <!-- 导出数据提示弹窗 -->
    <el-dialog
      title="提示"
      :visible.sync="exportDialog"
      width="30%"
      center
    >
      <el-alert
        :title="exportTip"
        type="warning"
        show-icon
      ></el-alert>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="exportDialog = false"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="sureExportHandler"
        >确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { distributorList, distributorLevelList, distributorAllGroup, delDistributor, setInviteCode, setBatchGroup, distributorListExport } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    groupDialog: () => import('./groupDialog'),
    inviteCodeDialog: () => import('./inviteCodeDialog'),
    remarksDialog: () => import('./remarksDialog')
  },
  props: {
    inviteFlag: {
      type: Boolean,
      default: true
    },
    optGroupId: {
      type: Number,
      default: () => 0
    }
  },
  data () {
    return {
      groupNameList: [],
      groupLevelList: [],
      // 搜索
      param: {
        mobile: '',
        username: '',
        realName: '',
        invitedMobile: '',
        startCreateTime: '',
        endCreateTime: '',
        invitedUserName: '',
        distributorLevel: '',
        distributorGroup: '',
        haveNextUset: 0,
        haveMobile: 0,
        haveRealName: 0
      },
      requestParams: {},
      // 表格
      tableData: [],
      // 分页
      pageParams: {},

      // 全选按钮
      allChecked: false,
      checkedValue: '0',
      checkList: [{
        label: '批量修改分员分组',
        value: '0'
      }, {
        label: '对选中的分销员修改分组',
        value: '1'
      }, {
        label: '对筛选出的0人修改分组',
        value: '2'
      }],

      // 邀请码弹窗
      invitationDialog: false,
      invitationUserId: null,
      inviteCode: '',

      // 分销员分组
      groupDialog: false,
      groupUserId: [],
      groupValue: 0,

      // 会员备注
      remarksDialog: false,
      remarksUserId: null,

      // 导出数据弹窗
      exportDialog: false,
      exportTip: ''
    }
  },
  watch: {
    optGroupId () {
      console.log(this.optGroupId)
      this.initDataList()
    }
  },
  mounted () {
    this.initDataList()
    this.levelList() // 分销员等级
    this.groupList() // 分销员分组
  },

  methods: {
    // 分销员列表
    initDataList () {
      // 搜索条件
      var obj = {}
      for (var i in this.param) {
        if (this.param[i]) {
          obj[i] = this.param[i]
        }
      }
      this.requestParams = obj
      // this.requestParams = this.param
      if (this.optGroupId) {
        this.requestParams.optGroupId = this.optGroupId
      }
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      distributorList(this.requestParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.checkList[2].label = '对筛选出的' + res.content.page.totalRows + '人修改分组'
        }
      })
    },
    // 等级下拉列表
    levelList () {
      distributorLevelList().then(res => {
        this.groupLevelList = res.content
        this.distributorLevel = res.content.dataList
      })
    },
    // 分组所有数据
    groupList () {
      distributorAllGroup().then(res => {
        this.groupNameList = res.content
      })
    },
    // 分销员邀请用户列表
    inviteUserList (userId) {
      this.$router.push({
        path: '/admin/home/main/distribution/inviteUserList',
        query: {
          userId: userId
        }
      })
    },
    // 清除分销员身份
    del (userId) {
      this.$confirm('清除分销员，则该分销员被清除分销员资格，被清除的用户可以重新申请成为分销员，确定清除吗？', '清除分销员', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delDistributor(userId).then(res => {
          if (res.error === 0) {
            this.$message.success('清除成功!')
            this.initDataList()
          }
        })
      }).catch(() => {

      })
    },

    // 用户昵称跳转
    userNameHandler (id) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: id
        }
      })
    },

    // 邀请码设置
    invitationCodeHandler (userId, invitationCode) {
      this.invitationDialog = !this.invitationDialog
      this.invitationUserId = userId // 要操作的用户
      this.inviteCode = invitationCode
    },

    // 邀请码弹窗回调函数
    choosingCodeResult (data) {
      setInviteCode({
        userId: this.invitationUserId,
        inviteCode: data
      }).then(res => {
        if (res.error === 0) {
          if (res.content === 1) {
            this.$message.success('设置成功')
          } else if (res.content === 0) {
            this.$message.warning('该邀请码已存在')
          }
          this.initDataList()
        } else {
          this.$message.success(res.message)
        }
      })
    },

    // 分销员分组设置
    groupNameHandler (userId, groupName) {
      this.groupDialog = !this.groupDialog
      this.groupUserId = []
      this.groupUserId.push(userId)

      var result = this.groupNameList.find((item, index) => {
        return item.groupName === groupName
      })
      this.groupValue = result === undefined ? 0 : result.id
    },

    // 分销员分组弹窗回调函数
    choosingGroupResult (data) {
      setBatchGroup({
        userId: this.groupUserId,
        groupId: data
      }).then(res => {
        if (res.error === 0) {
          this.$message.success('设置成功')
          this.initDataList()
        } else {
          this.$message.success(res.message)
        }
      })
    },

    // 备注会员信息
    remarksHandler (userId) {
      this.remarksUserId = userId // 要操作的用户
      this.remarksDialog = !this.remarksDialog
    },

    // 下级用户数跳转
    nextNumberHandler () {

    },

    // 间接邀请用户数跳转
    sublayerNumberHandler () {

    },

    // 全选切换
    checkChange (val) {
      if (val === true) {
        this.tableData.forEach(item => {
          this.$refs.multipleTable.toggleRowSelection(item)
        })
      } else {
        this.checkedValue = '0'
        this.$refs.multipleTable.clearSelection()
      }
    },

    // 切换修改选项
    selectChange (val) {
      var selected = this.$refs.multipleTable.selection
      if (this.checkedValue === '1' && selected.length === 0) {
        this.$message.warning('请选择分销员')
        this.checkedValue = '0'
      } else {
        // 批量设置分组
        if (this.checkedValue === '1') {
          this.groupDialog = !this.groupDialog
          this.groupUserId = []
          selected.forEach((item, index) => {
            this.groupUserId.push(item.userId)
          })
        }
        // 筛选数据设置
        if (this.checkedValue === '2') {
          this.groupDialog = !this.groupDialog
          this.groupUserId = []
          this.tableData.forEach((item, index) => {
            this.groupUserId.push(item.userId)
          })
        }
      }
    },

    // 导出数据
    exportDataList () {
      this.initDataList()
      this.$nextTick(() => {
        this.exportTip = `根据以下条件筛选出${this.pageParams.totalRows}条数据,是否确认导出？`
        this.exportDialog = !this.exportDialog
      })
    },

    // 确定导出
    sureExportHandler () {
      this.exportDialog = false
      // 搜索条件
      var obj = {}
      for (var i in this.param) {
        if (this.param[i]) {
          obj[i] = this.param[i]
        }
      }
      if (this.optGroupId) {
        obj.optGroupId = this.optGroupId
      }
      distributorListExport(obj).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '分销员列表导出.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    }

  }
}

</script>
<style lang="scss" scoped>
.item {
  display: inline-block;
}
.inputWidth {
  width: 170px;
}
.checkboxWidth {
  width: 200px;
}
.nameStyle {
  color: #5a8bff;
  cursor: pointer;
}
.el-tooltip__popper {
  max-width: 120px;
}
.checkedStyle {
  margin-top: 20px;
  margin-left: 10px;
}
.distributorListContent {
  padding: 10px;
  padding-bottom: 68px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.searchInfo_main {
  position: relative;
  background-color: #fff;
  height: 100%;
  overflow: hidden;
}
.li {
  padding: 10px 0;
  display: flex;
}
.liNav {
  width: 280px;
  display: flex;
}
.liNav span {
  display: block;
  width: 80px;
  line-height: 30px;
  height: 30px;
  text-align: right;
  color: #333;
  margin-right: 25px;
}
.labelClass {
  width: 100px !important;
}
.invitation {
  width: 200px !important;
}
.invitationPhone {
  width: 250px !important;
}
.timeLine {
  width: 500px !important;
}
.uls {
  margin-top: 10px;
  display: flex;
}
.uls span {
  width: 56px;
}
.date {
  width: 350px !important;
}
.tableInfo {
  margin-top: 30px;
}
.notice {
  margin-bottom: 20px;
}
.notice :first-child {
  color: red;
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
  background-color: #fff;
}
.footer {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
}
.footer > span {
  display: block;
  height: 32px;
  line-height: 32px;
}
.opt {
  text-align: center;
  color: #5a8bff;
}
.opt p {
  cursor: pointer;
}
</style>
