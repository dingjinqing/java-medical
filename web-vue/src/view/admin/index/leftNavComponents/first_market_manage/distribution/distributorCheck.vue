<template>
  <div class="tab_content">
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>手机号：</span>
          <el-input
            v-model="searchForm.mobile"
            size="small"
            class="inputWidth"
          ></el-input>
        </div>
        <div class="leftarea">
          <span>昵称：</span>
          <el-input
            v-model="searchForm.nickName"
            size="small"
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
            value-format="yyyy-MM-dd"
            class="inputWidth"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="searchForm.endTime"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd"
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
              <div class="header">ID: {{ item.idCard }}</div>
              <div class="header">昵称：<span class="active">{{ item.name }}</span></div>
              <div class="header">手机号：{{ item.mobile }}</div>
              <div class="header">申请时间：{{ item.startTime }}</div>
              <div class="header">邀请码：</div>
            </td>

            <td style="width: 120px;">分销员分组</td>
            <td style="width: 100px;">审核状态</td>
            <td style="width: 170px;">操作</td>
          </tr>

          <tr>
            <td v-if="item.idCard !== ''">真实姓名</td>
            <td v-if="item.idCard !== ''">{{ item.name }}</td>
            <td v-if="item.idCard !== ''">手机号</td>
            <td v-if="item.idCard !== ''">{{ item.mobile }}</td>
            <td v-if="item.idCard !== ''">身份证号</td>
            <td v-if="item.idCard !== ''">{{ item.idCard }}</td>
            <td
              colspan="6"
              v-if="item.idCard === ''"
              class="middle"
            >无需提交个人信息</td>
            <td
              rowspan="5"
              class="middle"
            >
              <p>{{ item.group }}</p>
              <p
                class="active"
                @click="setGroupHandler"
              >设置</p>
            </td>
            <td
              rowspan="5"
              class="middle"
            >{{ item.status }}</td>
            <td
              rowspan="5"
              class="middle"
            >
              <el-button
                size="small"
                type="primary"
                plain
              >通过</el-button>

              <el-button
                size="small"
                type="info"
                plain
              >不通过</el-button>
            </td>
          </tr>
          <tr v-if="item.idCard !== ''">
            <td>性别</td>
            <td>{{ item.sex }}</td>
            <td>生日</td>
            <td>{{ item.birthday }}</td>
            <td>婚姻状况</td>
            <td>{{ item.marry }}</td>

          </tr>
          <tr v-if="item.idCard !== ''">
            <td>教育程度</td>
            <td>{{ item.education }}</td>
            <td>所在行业</td>
            <td>{{ item.industry }}</td>
            <td>所在地</td>
            <td>{{ item.location }}</td>

          </tr>
          <tr v-if="item.idCard !== ''">
            <td>备注</td>
            <td colspan="5">{{ item.note }}</td>
          </tr>
          <tr v-if="item.idCard !== ''">
            <td>图片</td>
            <td colspan="5">6</td>
          </tr>
        </table>

        <Pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />

        <!-- 分销员分组弹窗 -->
        <el-dialog
          title="设置分销员分组"
          :visible.sync="dialogVisible"
          width="25%"
          center
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
              @click="dialogVisible = false"
            >确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import { distributionGroup } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      // 搜索
      searchForm: {
        mobile: '',
        nikeName: '',
        startTime: '',
        endTime: ''
      },
      activeName: 'first',
      pageParams: {}, // 分页
      requestParams: {},
      // 表格数据
      tableData: [{
        name: 'name1',
        mobile: 'mobile1',
        idCard: 'idCard1',
        group: 'group1',
        status: '待审核',
        sex: 'sex1',
        birthday: 'birthday1',
        marry: 'marry1',
        education: 'education1',
        industry: 'industry1',
        location: 'location1',
        note: '7个工作日没审核通过，联系110'
      }, {
        name: 'name2',
        mobile: 'mobile2',
        idCard: 'idCard2',
        group: 'group2',
        status: '待审核',
        sex: 'sex2',
        birthday: 'birthday2',
        marry: 'marry2',
        education: 'education2',
        industry: 'industry2',
        location: 'location2',
        note: '备注信息'
      }, {
        name: '',
        mobile: '',
        idCard: '',
        group: 'group1',
        status: '待审核',
        sex: '',
        birthday: '',
        marry: '',
        education: '',
        industry: '',
        location: '',
        note: ''
      }],

      // 分销员分组弹窗
      dialogVisible: false,
      selectValue: '',
      selectData: []
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
    setGroupHandler () {
      this.dialogVisible = !this.dialogVisible
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
  .header:nth-child(4),
  .header:nth-child(5) {
    width: 25%;
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
</style>
