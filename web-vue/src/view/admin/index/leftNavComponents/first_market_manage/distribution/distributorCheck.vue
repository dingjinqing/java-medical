<template>
  <div class="tab_content">
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>{{ $t('distribution.reviewMobile') + '：' }}</span>
          <el-input
            v-model="searchForm.mobile"
            size="small"
            clearable
            class="inputWidth"
          ></el-input>
        </div>
        <div class="leftarea">
          <span>{{ $t('distribution.reviewName') + '：' }}</span>
          <el-input
            v-model="searchForm.username"
            size="small"
            clearable
            class="inputWidth"
          ></el-input>
        </div>
        <div class="midarea">
          <span>{{ $t('distribution.reviewTime') + '：' }}</span>
          <el-date-picker
            v-model="searchForm.startTime"
            type="date"
            :placeholder="$t('distribution.reviewSelect')"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="inputWidth"
          >
          </el-date-picker>
          <span>{{ $t('distribution.to') }}</span>
          <el-date-picker
            v-model="searchForm.endTime"
            type="date"
            :placeholder="$t('distribution.reviewSelect')"
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
          >{{ $t('distribution.reviewSearch') }}</el-button>
        </div>
      </div>

      <el-tabs
        v-model="activeName"
        @tab-click="initDataList"
      >
        <el-tab-pane
          :label="$t('distribution.reviewBy')"
          name="0"
        ></el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.reviewPass')"
          name="1"
        ></el-tab-pane>
        <el-tab-pane
          :label="$t('distribution.reviewNoPass')"
          name="2"
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
              <div class="header">{{ $t('distribution.reviewName') + '：' }}<span
                  class="active"
                  @click="detailHandler(item.userId)"
                >{{ item.username }}</span></div>
              <div
                class="header"
                v-if="item.mobile"
              >{{ $t('distribution.reviewMobile') + '：' }}{{ item.mobile }}</div>
              <div
                class="header"
                v-if="item.createTime"
              >{{ $t('distribution.reviewTime') + '：' }}{{ item.createTime }}</div>
              <!-- <div class="header">邀请码：</div> -->
            </td>

            <td
              style="width: 120px;"
              v-if="activeName === '0'"
            >{{ $t('distribution.reviewGroup') }}</td>
            <td
              style="width: 170px;"
              v-if="activeName !== '0'"
            >{{ $t('distribution.reviewDate') }}</td>
            <td style="width: 100px;">{{ $t('distribution.reviewStatus') }}</td>
            <td
              style="width: 170px;"
              v-if="activeName === '0'"
            >{{ $t('distribution.reviewOption') }}</td>
            <td
              style="width: 170px;"
              v-if="activeName === '2'"
            >{{ $t('distribution.noPassReason') }}</td>
          </tr>

          <tr>
            <td v-if="item.userId !== ''">{{ $t('distribution.reviewRealName') }}</td>
            <td v-if="item.userId !== ''">{{ item.realName ? item.realName : $t('distribution.reviewNo') }}</td>
            <td v-if="item.userId !== ''">{{ $t('distribution.reviewMobile') }}</td>
            <td v-if="item.userId !== ''">{{ item.mobile ? item.mobile : $t('distribution.reviewNo') }}</td>
            <td v-if="item.userId !== ''">{{ $t('distribution.reviewId') }}</td>
            <td v-if="item.userId !== ''">{{ item.userId ? item.userId : $t('distribution.reviewNo') }}</td>
            <td
              colspan="6"
              v-if="item.userId === ''"
              class="middle"
            >{{ $t('distribution.reviewTip') }}</td>
            <td
              rowspan="5"
              class="middle"
              v-if="activeName === '0'"
            >
              <p v-if="item.groupData">{{ item.groupData.groupName }}</p>
              <p
                class="active"
                v-if="item.groupData"
                @click="setGroupHandler(item.userId, item.groupData.id)"
              >{{ $t('distribution.reviewSet') }}</p>
              <p
                class="active"
                v-if="!item.groupData"
                @click="setGroupHandler(item.userId)"
              >{{ $t('distribution.reviewSet') }}</p>
            </td>
            <td
              rowspan="5"
              class="middle"
              v-if="activeName !== '0'"
            >{{ item.updateTime }}</td>
            <td
              rowspan="5"
              class="middle"
            >{{ item.status }}</td>
            <td
              rowspan="5"
              class="middle"
              v-if="activeName === '0'"
            >
              <el-button
                size="small"
                type="primary"
                plain
                @click="reviewPassHandler(item)"
              >{{ $t('distribution.passBtn') }}</el-button>

              <el-button
                size="small"
                type="info"
                plain
                @click="reviewNoPassHandler(item)"
              >{{ $t('distribution.noPassBtn') }}</el-button>
            </td>
            <td
              rowspan="5"
              class="middle"
              v-if="activeName === '2'"
            >不通过理由</td>

          </tr>
          <tr v-if="item.userId !== ''">
            <td>{{ $t('distribution.reviewSex') }}</td>
            <td>{{ item.sex ? item.sex : $t('distribution.reviewNo') }}</td>
            <td>{{ $t('distribution.reviewBirthday') }}</td>
            <td>{{ item.birthdayYear ? item.birthdayYear - item.birthdayMonth - item.birthdayDay : $t('distribution.reviewNo') }}</td>
            <td>{{ $t('distribution.reviewMarital') }}</td>
            <td>{{ item.maritalStatus ? item.maritalStatus : $t('distribution.reviewNo') }}</td>

          </tr>
          <tr v-if="item.userId !== ''">
            <td>{{ $t('distribution.reviewEducation') }}</td>
            <td>{{ item.educationName ? item.educationName : $t('distribution.reviewNo') }}</td>
            <td>{{ $t('distribution.reviewIndustry') }}</td>
            <td>{{ item.industryName ? item.industryName : $t('distribution.reviewNo') }}</td>
            <td>{{ $t('distribution.reviewAddress') }}</td>
            <td>{{ item.address ? item.address : $t('distribution.reviewNo') }}</td>

          </tr>
          <tr v-if="item.userId !== ''">
            <td>{{ $t('distribution.reviewNote') }}</td>
            <td colspan="5"></td>
          </tr>
          <tr v-if="item.userId !== ''">
            <td>{{ $t('distribution.reviewImg') }}</td>
            <td colspan="5"></td>
          </tr>
        </table>

      </div>

      <!-- 分页 -->
      <Pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />

      <!-- 分销员分组弹窗 -->
      <el-dialog
        :title="$t('distribution.reviewTitle1')"
        :visible.sync="dialogVisible"
        width="25%"
        center
        :close-on-click-modal="false"
      >
        <span>{{ $t('distribution.selectGroup') + '：' }}</span>
        <el-select
          v-model="selectValue"
          :placeholder="$t('distribution.selectGroup')"
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
          >{{ $t('distribution.cancleBtn') }}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="saveGroupHandler"
          >{{ $t('distribution.confirmBt') }}</el-button>
        </span>
      </el-dialog>

      <!-- 审核不通过弹窗 -->
      <el-dialog
        :title="$t('distribution.reviewTitle2')"
        :visible.sync="failDialogVisible"
        width="35%"
        center
        :close-on-click-modal="false"
      >
        <div class="failTip">
          <i
            class="el-icon-warning"
            style="color: #E6A23C; font-size: 14px;"
          ></i>
          <span> {{ $t('distribution.reviewTitleTip') }}</span>
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
          >{{ $t('distribution.cancleBtn') }}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="surePassHandler"
          >{{ $t('distribution.confirmBt') }}</el-button>
        </span>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import { getCheckList, distributionGroup, getCheckPass, getCheckRefuse } from '@/api/admin/marketManage/distribution.js'
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
      activeName: '0', // tab值
      // 分页
      pageParams: {
        currentPage: 1,
        pageRows: 10
      },
      requestParams: {},
      tableData: [], // 表格数据

      // 分销员分组弹窗
      dialogVisible: false,
      groupId: '', // 编辑表格id
      selectValue: '', // 分销分组值
      selectData: [], // 分销分组数据

      // 审核不通过弹窗
      failDialogVisible: false,
      failData: {}, // 审核不通过数据
      textarea: '' // 审核不通过说明
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
      this.requestParams.nav = Number(this.activeName)
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
        data.groupData = {}
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
        if (this.activeName === '0') {
          item.status = '待审核'
        } else if (this.activeName === '1') {
          item.status = '已通过'
        } else if (this.activeName === '2') {
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
    setGroupHandler (userId, id) {
      this.dialogVisible = !this.dialogVisible
      // 数据回显
      this.groupId = userId
      if (id) {
        this.selectData.forEach((item, index) => {
          if (item.id === id) {
            this.selectValue = item.id
          }
        })
      } else {
        this.selectValue = ''
      }
    },

    // 保存分销员分组
    saveGroupHandler () {
      this.dialogVisible = false
      // 获取下拉框的值
      this.selectData.forEach((item, index) => {
        if (item.id === this.selectValue) {
          // 赋值给表格
          this.tableData.forEach((val, key) => {
            if (val.userId === this.groupId) {
              val.groupData = item
            }
          })
        }
      })
    },

    // 审核通过
    reviewPassHandler (data) {
      var groupId = 0
      if (data.groupData) {
        groupId = data.groupData.id
      }
      getCheckPass({
        id: data.id,
        groupId: groupId
      }).then((res) => {
        if (res.error === 0) {
          this.$message.success(this.$t('distribution.reviewPass') + '!')
          this.initDataList()
        }
      })
    },

    // 审核不通过弹窗
    reviewNoPassHandler (data) {
      this.failDialogVisible = !this.failDialogVisible
      this.failData = data
    },

    // 取消审核不通过
    cancelPassHandler () {
      this.failDialogVisible = false
      this.textarea = ''
    },

    // 确定审核不通过
    surePassHandler () {
      var groupId = 0
      if (this.failData.groupData) {
        groupId = this.failData.groupData.id
      }
      getCheckRefuse({
        id: this.failData.id,
        groupId: groupId,
        msg: this.textarea
      }).then((res) => {
        if (res.error === 0) {
          this.$message.success('审核不通过成功!')
          this.failDialogVisible = false
          this.textarea = ''
          this.initDataList()
        }
      })
    },

    // 跳转会员详情
    detailHandler (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: userId
        }
      })
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
    width: 25%;
  }
  .header:first-child {
    margin-left: 10px;
    width: 15%;
  }
  .header:nth-child(2) {
    width: 18%;
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
