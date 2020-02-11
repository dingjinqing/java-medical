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
              v-model="param.inviterMobile"
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
              size="small"
              class="inputWidth"
            >
            </el-date-picker>
            <span>至</span>
            <el-date-picker
              v-model="param.endCreateTime"
              type="date"
              placeholder="选择日期"
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
              v-model="param.invitedUsername"
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
              v-model="param.valueLevel"
              placeholder="请选择等级"
              size="small"
              class="inputWidth"
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
          <el-form-item
            label="分销员分组："
            class="item"
          >
            <el-select
              v-model="param.valueGroup"
              placeholder="请选择分组"
              size="small"
              class="inputWidth"
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
        </div>
        <div style="overflow: auto;">
          <div
            class="item"
            style="float: left;"
          >
            <el-checkbox v-model="param.hasSubordinate">有下级用户</el-checkbox>
            <el-checkbox v-model="param.hasMobile">有手机号</el-checkbox>
            <el-checkbox v-model="param.hasRealName">有真实姓名</el-checkbox>
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
            <el-button size="small">导出</el-button>
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
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            label="ID"
            align="center"
          >
            <template slot-scope="scope">
              <el-checkbox></el-checkbox> {{ scope.row.userId }}
            </template>
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
            <!-- <template slot-scope="scope">
              <span
                class="nameStyle"
                @click="userNameHandler(scope.row.userId)"
              >{{ scope.row.username }}</span>
            </template> -->
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

          <el-table-column align="center">
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
              <p
                class="nameStyle"
                @click="invitationCodeHandler"
              >设置</p>
            </template>
          </el-table-column>

          <el-table-column
            label="分销员分组"
            align="center"
          >
            <template slot-scope="scope">
              <p>{{ scope.row.groupName }}</p>
              <p
                class="nameStyle"
                @click="groupNameHandler"
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
            prop=""
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="opt">
                <p @click="inviteUserList(scope.row.userId)">查看已邀请用户</p>
                <p>查看返利佣金明细</p>
                <p @click="del(scope.row.userId)">清除</p>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!-- 全选修改分销员分组 -->
        <div class="checkedStyle">
          <el-checkbox v-model="allChecked"></el-checkbox>
          全选当前页
          <el-select
            v-model="checkedValue"
            placeholder="请选择等级"
            size="small"
            class="checkboxWidth"
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
  </div>
</template>

<script>
import { distributorList, distributorLevelList, distributorGroupList, delDistributor } from '@/api/admin/marketManage/distribution.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'

export default {
  components: { pagination },
  data () {
    return {
      groupNameList: [],
      groupLevelList: [],
      // 表格
      tableData: [],
      // 分页
      pageParams: {},
      // 搜索
      param: {
        mobile: '',
        username: '',
        realName: '',
        inviterMobile: '',
        startCreateTime: '',
        endCreateTime: '',
        invitedUsername: '',
        valueLevel: '',
        valueGroup: '',
        hasSubordinate: false,
        hasMobile: false,
        hasRealName: false
      },
      requestParams: {},
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
        label: '对筛选出的66人修改分组',
        value: '2'
      }]
    }
  },
  mounted () {
    this.initDataList()
    this.levelList()
    this.groupList()
  },

  methods: {
    // 分销员列表
    initDataList () {
      // this.requestParams = this.param
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      distributorList(this.requestParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
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
    // 分组下拉列表
    groupList () {
      distributorGroupList().then(res => {
        this.groupNameList = res.content
        console.log(this.groupNameList)
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
            this.$message({
              type: 'success',
              message: '清除成功!'
            })
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
    invitationCodeHandler () {

    },

    // 分销员分组编辑
    groupNameHandler () {

    },

    // 下级用户数跳转
    nextNumberHandler () {

    },

    // 间接邀请用户数跳转
    sublayerNumberHandler () {

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
</style>
