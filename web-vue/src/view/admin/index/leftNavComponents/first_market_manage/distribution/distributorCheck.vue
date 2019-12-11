<template>
  <div class="tab_content">
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>手机号：</span>
          <el-input
            v-model="searchForm.mobile"
            size="small"
            clearable
            class="inputWidth"
          ></el-input>
        </div>
        <div class="leftarea">
          <span>昵称：</span>
          <el-input
            v-model="searchForm.username"
            size="small"
            clearable
            class="inputWidth"
          ></el-input>
        </div>
        <div class="midarea">
          <span>申请时间：</span>
          <el-date-picker
            v-model="searchForm.startTime"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="inputWidth"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="searchForm.endTime"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="inputWidth"
          >
          </el-date-picker>
        </div>
        <div class="rightarea">
          <el-button
            type="primary"
            size="small"
            @click="initDataList"
          >查询</el-button>
        </div>
      </div>

      <el-tabs
        v-model="activeName"
        @tab-click="initDataList"
      >
        <el-tab-pane
          label="待审核分销员"
          name="first"
        ></el-tab-pane>
        <el-tab-pane
          label="审核通过"
          name="second"
        ></el-tab-pane>
        <el-tab-pane
          label="未通过"
          name="third"
        ></el-tab-pane>
      </el-tabs>
      <div>
        <table
          class="checkList"
          v-for="(item, index) in tableData"
          :key="index"
        >
          <tr class='title'>
            <td
              colspan="6"
              style="text-align: left;"
            >
              <div class="header">ID：{{ item.userId }}</div>
              <div class="header">昵称：<span class="active">{{ item.username }}</span></div>
              <div
                class="header"
                v-if="item.mobile"
              >手机号：{{ item.mobile }}</div>
              <div
                class="header"
                v-if="item.createTime"
              >申请时间：{{ item.createTime }}</div>
              <!-- <div class="header">邀请码：</div> -->
            </td>

            <td style="width: 120px;">分销员分组</td>
            <td style="width: 100px;">审核状态</td>
            <td
              style="width: 170px;"
              v-if="item.status === 0"
            >操作</td>
            <td
              style="width: 170px;"
              v-if="item.status === 2"
            >未通过原因</td>
          </tr>

          <tr>
            <td v-if="item.userId !== ''">真实姓名</td>
            <td v-if="item.userId !== ''">{{ item.realName ? item.realName : '无' }}</td>
            <td v-if="item.userId !== ''">手机号</td>
            <td v-if="item.userId !== ''">{{ item.mobile ? item.mobile : '无' }}</td>
            <td v-if="item.userId !== ''">身份证号</td>
            <td v-if="item.userId !== ''">{{ item.userId ? item.userId : '无' }}</td>
            <td
              colspan="6"
              v-if="item.userId === ''"
              class="middle"
            >无需提交个人信息</td>
            <td
              rowspan="5"
              class="middle"
            >
              <!-- <p>{{ item.group }}</p> -->
              <!-- <p
                class="active"
                @click="setGroupHandler(item.userId, item.group)"
              >设置</p> -->
            </td>
            <td
              rowspan="5"
              class="middle"
            >{{ item.status }}</td>
            <td
              rowspan="5"
              class="middle"
              v-if="item.status === 0"
            >
              <el-button
                size="small"
                type="primary"
                plain
                @click="passHandler(item.id)"
              >通过</el-button>

              <el-button
                size="small"
                type="info"
                plain
                @click="noPassHandler(item.id)"
              >不通过</el-button>
            </td>
            <td
              rowspan="5"
              class="middle"
              v-if="item.status === 2"
            >

            </td>
          </tr>
          <tr v-if="item.userId !== ''">
            <td>性别</td>
            <td>{{ item.sex ? item.sex : '无' }}</td>
            <td>生日</td>
            <td>{{ item.birthdayYear ? item.birthdayYear - item.birthdayMonth - item.birthdayDay : '无' }}</td>
            <td>婚姻状况</td>
            <td>{{ item.maritalStatus ? item.maritalStatus : '无' }}</td>

          </tr>
          <tr v-if="item.userId !== ''">
            <td>教育程度</td>
            <td>{{ item.educationName ? item.educationName : '无' }}</td>
            <td>所在行业</td>
            <td>{{ item.industryName ? item.industryName : '无' }}</td>
            <td>所在地</td>
            <td>{{ item.address ? item.address : '无' }}</td>

          </tr>
          <tr v-if="item.userId !== ''">
            <td>备注</td>
            <td colspan="5"></td>
          </tr>
          <tr v-if="item.userId !== ''">
            <td>图片</td>
            <td colspan="5"></td>
          </tr>
        </table>

        <!-- 分页 -->
        <Pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>

      <!-- 分销员分组弹窗 -->
      <el-dialog
        title="设置分销员分组"
        :visible.sync="dialogVisible"
        width="25%"
        center
        close-on-click-modal=false
      >
        <span>选择分组：</span>
        <el-select
          v-model="selectValue"
          placeholder="请选择分组"
          size="small"
          style="width: 170px;"
        >
          <el-option
            v-for="(item, index) in selectData"
            :key="index"
            :label="item.groupName"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="dialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="saveGroupHandler"
          >确 定</el-button>
        </span>
      </el-dialog>

      <!-- 不通过弹窗 -->
      <el-dialog
        title="审核不通过原因"
        :visible.sync="failDialogVisible"
        width="35%"
        center
        close-on-click-modal=false
      >
        <div class="failTip">
          <i
            class="el-icon-warning"
            style="color: #E6A23C; font-size: 14px;"
          ></i>
          <span> 原因提交后不可修改，请谨慎提交</span>
        </div>
        <el-input
          v-model="textarea"
          type="textarea"
          :rows="8"
          maxlength="150"
          show-word-limit
        >
        </el-input>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="cancelPassHandler"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="surePassHandler"
          >确 定</el-button>
        </span>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import { getCheckList, distributionGroup } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      // 搜索
      searchForm: {
        mobile: '',
        username: '',
        startTime: '',
        endTime: ''
      },
      activeName: 'first',
      // 分页
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      requestParams: {},
      // 表格数据
      tableData: [{
        id: 1,
        userId: '111111', // 用户id
        status: 0,
        username: '待审核', // 昵称
        mobile: '17823456789', // 手机号
        createTime: '2019-01-01 00:00:00', // 申请时间
        // 邀请码
        realName: '', // 真实姓名
        // 身份证号
        sex: '', // 性别
        birthdayYear: '', // 出生年
        birthdayMonth: '', // 出生月
        birthdayDay: '', // 出生日
        maritalStatus: '', // 婚姻状况
        educationName: '', // 受教育程度
        industryName: '', // 行业
        address: '' // 所在地址
        // 备注
        // 图片
      }, {
        id: 2,
        userId: '222222', // 用户id
        status: 1,
        username: '审核通过', // 昵称
        mobile: '', // 手机号
        createTime: '', // 申请时间
        // 邀请码
        realName: '', // 真实姓名
        // 身份证号
        sex: '', // 性别
        birthdayYear: '', // 出生年
        birthdayMonth: '', // 出生月
        birthdayDay: '', // 出生日
        maritalStatus: '', // 婚姻状况
        educationName: '', // 受教育程度
        industryName: '', // 行业
        address: '' // 所在地址
        // 备注
        // 图片
      }, {
        id: 3,
        userId: '333333', // 用户id
        status: 2,
        username: '未通过', // 昵称
        mobile: '', // 手机号
        createTime: '', // 申请时间
        // 邀请码
        realName: '', // 真实姓名
        // 身份证号
        sex: '', // 性别
        birthdayYear: '', // 出生年
        birthdayMonth: '', // 出生月
        birthdayDay: '', // 出生日
        maritalStatus: '', // 婚姻状况
        educationName: '', // 受教育程度
        industryName: '', // 行业
        address: '' // 所在地址
        // 备注
        // 图片
      }],

      // 分销员分组弹窗
      dialogVisible: false,
      groupId: '',
      groupName: '',
      selectValue: '',
      selectData: [],

      // 审核不通过弹窗
      failDialogVisible: false,
      textarea: ''
    }
  },
  watch: {
    lang () {

    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
    this.getDistributionGroup()
  },
  methods: {
    initDataList () {
      this.requestParams.mobile = this.searchForm.mobile
      this.requestParams.username = this.searchForm.username
      this.requestParams.startTime = this.searchForm.startTime
      this.requestParams.endTime = this.searchForm.endTime
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getCheckList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.forEach(item => {
        // 性别
        if (item.sex === 'f') {
          item.sex = '女'
        } else if (item.sex === 'm') {
          item.sex = '男'
        }
        // 婚姻状况
        if (item.maritalStatus === 1) {
          item.maritalStatus = '未婚'
        } else if (item.maritalStatus === 2) {
          item.maritalStatus = '已婚'
        }
        // 审核状态
        if (item.status === 0) {
          item.status = '待审核'
        } else if (item.status === 1) {
          item.status = '已通过'
        } else if (item.status === 2) {
          item.status = '未通过'
        }
      })
      this.tableData = data
    },

    // 获取分销员分组
    getDistributionGroup () {
      distributionGroup(this.pageParams).then((res) => {
        if (res.error === 0) {
          this.selectData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },

    // 设置分销员分组
    setGroupHandler (id, value) {
      this.dialogVisible = !this.dialogVisible
      // 数据回显
      this.groupId = id
      this.selectData.forEach((item, index) => {
        if (item.groupName === value) {
          this.selectValue = item.id
          this.groupName = item.groupName
        }
      })
    },

    // 保存分销员分组
    saveGroupHandler () {
      this.dialogVisible = false
      // 获取下拉框的label
      this.selectData.forEach((item, index) => {
        if (item.id === this.selectValue) {
          this.groupName = item.groupName
        }
      })
      // 赋值给表格
      this.tableData.forEach((item, index) => {
        if (item.userId === this.groupId) {
          item.group = this.groupName
        }
      })
    },

    // 审核通过
    passHandler (id) {
      this.$message.success('审核通过!')
      // this.initDataList()
    },

    // 审核不通过
    noPassHandler (id) {
      this.failDialogVisible = !this.failDialogVisible
    },

    // 取消审核不通过
    cancelPassHandler () {
      this.failDialogVisible = false
      this.textarea = ''
    },

    // 确定审核不通过
    surePassHandler () {
      this.failDialogVisible = false
      this.textarea = ''
      // this.initDataList()
    }
  }

}

</script>
<style lang="scss" scoped>
.tab_content {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
  }
}
.table_list {
  position: relative;
  background-color: #fff;
  .select_info {
    display: flex;
    margin: 10px 0px;
    .leftarea {
      display: flex;
      margin-right: 50px;
      .inputWidth {
        width: 170px;
      }
    }
    .rightarea {
      display: flex;
      :nth-of-type(1) {
        margin-right: 5px;
      }
    }
    .midarea {
      display: flex;
      margin-right: 30px;
      :first-child {
        margin-right: 10px;
      }
      :nth-of-type(1) {
        margin-right: 10px;
      }
      :nth-of-type(2) {
        margin: 0 10px 0 0;
      }
      /deep/ .el-input {
        width: 200px !important;
      }
    }
    span {
      white-space: nowrap;
      height: 32px;
      line-height: 32px;
    }
    /deep/ .el-input__inner {
      width: 200px;
      display: inline-block;
    }
  }
  .footer {
    padding: 20px 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    span {
      display: block;
      height: 32px;
      line-height: 32px;
    }
  }
  tr {
    height: 35px;
    line-height: 35px;
    border: 1px solid lightgray;
  }
  td {
    text-align: center;
    border: 1px solid lightgray;
    min-width: 120px;
  }
  .checkList {
    width: 100%;
    margin-bottom: 20px;
  }
  .title {
    background-color: #eee;
  }
  .header {
    display: inline-block;
    width: 15%;
  }
  .header:nth-child(1) {
    margin-left: 10px;
  }
  .header:nth-child(4) {
    width: 25%;
  }
  .header:nth-child(3),
  .header:nth-child(5) {
    width: 20%;
  }
  .active {
    color: #5a8bff;
    cursor: pointer;
  }
  .middle {
    display: table-cell;
    vertical-align: middle;
  }
}
.failTip {
  height: 30px;
  line-height: 30px;
  color: rgb(102, 102, 102);
  font-size: 12px;
  background-color: rgb(255, 247, 235);
  padding: 0px 20px;
  border-width: 1px;
  border-style: solid;
  border-color: rgb(255, 213, 163);
  border-image: initial;
  margin-bottom: 10px;
}
</style>
