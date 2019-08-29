<template>
  <div class="activateAudit">
    <div class="activateAuditMain">
      <div class="topDiv">
        <div>
          <span>真实姓名</span>
          <el-input
            v-model="userNameInput"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>手机号</span>
          <el-input
            v-model="phoneNumInput"
            placeholder="请输入用户昵称"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>申请时间</span>
          <el-date-picker
            size="small"
            v-model="dataValue"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </div>
        <div style="margin-left:20px">
          <el-button
            type="primary"
            size="small"
          >查询</el-button>
        </div>
      </div>
    </div>
    <div class="activateAuditMain">
      <el-tabs v-model="activeName">
        <el-tab-pane
          label="待审核"
          name="first"
        >
        </el-tab-pane>
        <el-tab-pane
          label="审核通过"
          name="second"
        ></el-tab-pane>
        <el-tab-pane
          label="未通过"
          name="third"
        ></el-tab-pane>
      </el-tabs>
      <div
        class="member_info"
        v-for="(item,index) in tabData"
        :key="index"
      >
        <div class="member_title">
          <span>ID：<strong>{{item.ID}}</strong></span>
          <span>昵称：<strong>{{item.nackName}}</strong></span>
          <span>手机号：<strong>{{item.phoneNum}}</strong></span>
          <span>申请时间：<strong>{{item.date}}</strong></span>
          <span class="operate">操作</span>
        </div>
        <div class="member_content">
          <ul>
            <li>真实姓名：<strong>{{item.realName}}</strong></li>
          </ul>
          <div class="operate_box">
            <div
              class="content"
              v-if='item.opStatus === 0'
            >
              <div style="margin-bottom:5px">
                <el-button
                  type="primary"
                  plain
                  size="small"
                  @click="handleToIsAdopt(0)"
                >通过</el-button>
              </div>
              <div>
                <el-button
                  type="info"
                  plain
                  size="small"
                  @click="handleToIsAdopt(1)"
                >不通过</el-button>
              </div>

            </div>
            <div
              class="content"
              v-if='item.opStatus === 1'
            >
              <div>审核通过</div>
            </div>
            <div
              class="content"
              v-if='item.opStatus === 2'
            >
              <div>审核未通过</div>
            </div>
          </div>
        </div>
      </div>

      <Pagination
        :page-params.sync="pageParams"
        @pagination="search"
      />
    </div>
  </div>
</template>
<script>
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 10
      },
      userNameInput: '',
      phoneNumInput: '',
      dataValue: '',
      activeName: 'first',
      tabData: [],
      tabOneData: [
        {
          ID: 1,
          nackName: '帅飞1',
          phoneNum: '18236936252',
          date: '2019-08-27 18:00:29',
          realName: '王义博',
          opStatus: 0
        }
      ],
      tabTwoData: [
        {
          ID: 2,
          nackName: '帅飞2',
          phoneNum: '18236936252',
          date: '2019-08-27 18:00:29',
          realName: '王义博',
          opStatus: 1
        }
      ],
      tabThreeData: [
        {
          ID: 3,
          nackName: '帅飞3',
          phoneNum: '18236936252',
          date: '2019-08-27 18:00:29',
          realName: '王义博',
          opStatus: 2
        }
      ]
    }
  },
  watch: {
    activeName (newData) {
      console.log(newData)
      this.handleToData(newData)
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      this.handleToData(this.activeName)
    },
    search (data) {
      console.log(data)
    },
    // 数据处理
    handleToData (newData) {
      switch (newData) {
        case 'first':
          this.tabData = this.tabOneData
          break
        case 'second':
          this.tabData = this.tabTwoData
          break
        case 'third':
          this.tabData = this.tabThreeData
          break
      }
    },
    // 是否通过点击
    handleToIsAdopt (flag) {
      if (flag === 0) {

      } else {

      }
    }
  }
}
</script>
<style lang="scss" scoped>
.activateAudit {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .activateAuditMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px 10px;
    margin-bottom: 10px;
    .topDiv {
      display: flex;
      margin-bottom: 20px;
      div {
        /deep/ .el-input {
          width: 140px;
        }
        span {
          white-space: nowrap;
          display: inline-block;
          width: 80px;
          text-align: right;
          margin-right: 20px;
        }
        display: flex;
        align-items: center;
        /deep/ .el-button {
          width: 85px;
        }
      }
    }
    .member_info {
      border: 1px solid #eee;
      margin-bottom: 10px;
      .member_title {
        padding: 0 0 0 15px;
        background-color: #f5f5f5;
        span {
          margin-right: 50px;
          padding: 8px 0;
          display: inline-block;
        }
        .operate {
          float: right;
          width: 20%;
          margin-right: 0;
          text-align: center;
        }
      }
      .member_content {
        padding: 0 0 0 15px;
        ul {
          padding: 10px 0 10px 0;
          width: 80%;
          display: inline-flex;
          flex-direction: row;
          flex-wrap: wrap;
          float: left;
        }
        .operate_box {
          width: 20%;
          border-left: 1px solid #eee;
          height: 97px;
          display: flex;
          justify-content: center;
          align-items: center;
          .content {
            display: flex;
            flex-direction: column;
            /deep/ .el-button {
              width: 100px;
            }
          }
        }
      }
    }
  }
}
</style>
