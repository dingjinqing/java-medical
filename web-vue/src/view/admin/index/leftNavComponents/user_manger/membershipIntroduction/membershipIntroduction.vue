<template>
  <div class="introductionContainer">
    <div class="introductionMain">
      <div class="filter-list">
        <div class="introductionMain_top">
          <div class="topLeft">说明：会员导入适用于会员迁移得场景，系统会导入增量会员并更新未激活会员得信息，不会更新已激活会员得信息</div>
          <div class="topRight">
            <el-button
              type="primary"
              size="small"
              @click="handleSetAll(0)"
            >设置激活通知</el-button>
            <el-button
              type="primary"
              size="small"
              @click="handleSetAll(1)"
            >会员导入</el-button>
          </div>
        </div>
        <div class="introductionMain_middle">
          <div class="batchNumber">
            <span>批次号</span>
            <el-input
              v-model="input"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
          <div
            class="batchNumber"
            style="margin-right:10px"
          >
            <span>操作时间</span>
            <el-date-picker
              v-model="introducionDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              size="small"
            >
            </el-date-picker>
          </div>
          <el-button
            type="primary"
            size="small"
            @click="handleSetAll(2)"
          >筛选</el-button>
        </div>
      </div>
      <div class="table-list">
        <el-table
          class="auth-list"
          header-row-class-name='tableClss'
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="versionNum"
            label="批次号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="isAuth"
            label="操作时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            align="center"
            label="会员卡"
          >
            <template slot-scope="scope">
              <div
                class="num"
                @click="handleClickMem(scope.row.ispay)"
              >{{scope.row.ispay}}</div>
            </template>
          </el-table-column>
          <el-table-column
            prop="status1"
            align="center"
            label="成功数量"
          >
          </el-table-column>
          <el-table-column
            prop="status2"
            align="center"
            label="失败数量"
          >
          </el-table-column>
          <el-table-column
            prop="num"
            align="center"
            label="激活数量"
          >

          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <div
                class="num"
                @click="handleClickO(scope.row)"
              >下载激活数据</div>
            </template>
          </el-table-column>
        </el-table>

        <div class="footer">
          <span>当前页面1/2,总记录3条</span>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="1"
            layout="prev, pager, next, jumper"
            :total="3"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!--设置激活通知弹窗-->
    <ActivationNotificationDialog />
  </div>
</template>
<script>
import ActivationNotificationDialog from '@/view/admin/index/leftNavComponents/user_manger/membershipIntroduction/activationNotificationDialog'
export default {
  components: { ActivationNotificationDialog },
  data () {
    return {
      input: '',
      introducionDate: '',
      tableData: [
        {
          versionNum: '22',
          isAuth: '2019-07-30 19:50:05',
          ispay: '会员卡',
          status1: '成功数量',
          status2: '失败数量',
          num: '2'
        },
        {
          versionNum: '23',
          isAuth: '2019-07-30 19:50:05',
          ispay: '会员卡',
          status1: '成功数量',
          status2: '失败数量',
          num: '2'
        },
        {
          versionNum: '23',
          isAuth: '2019-07-30 19:50:05',
          ispay: '会员卡',
          status1: '成功数量',
          status2: '失败数量',
          num: '2'
        }
      ],
      currentPage: null

    }
  },
  methods: {
    // 下载激活数据
    handleClickO () {

    },
    // 当前页改变
    handleCurrentChange () {

    },
    // 会员卡项点击
    handleClickMem (data) {
      this.$router.push({
        name: 'membershipCardDetail'
      })
    },
    // 设置激活通知  会员导入  筛选 综合处理
    handleSetAll (flag) {
      switch (flag) {
        case 0:
          break
        case 1:
          break
        case 2:
          break
      }
    }
  }
}
</script>

<style scoped lang="scss">
.introductionContainer {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .introductionMain {
    position: relative;
    // background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    // padding: 10px;
    .introductionMain_top {
      display: flex;
      justify-content: space-between;
      background-color: #f5f5f5;
      padding: 10px;
      .topLeft {
        height: 32px;
        line-height: 32px;
        color: #666;
      }
    }
    .introductionMain_middle {
      padding: 8px 0;
      display: flex;
      span {
        white-space: normal;
        display: block;
        height: 32px;
        line-height: 32px;
        margin: 0 25px;
      }
      .batchNumber {
        display: flex;
        /deep/ .el-input {
          width: 150px;
        }
      }
    }
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .num {
    color: #5a8bff;
    cursor: pointer;
    &:hover {
      color: #000;
    }
  }
  .footer {
    height: 50px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  .filter-list {
    padding: 15px;
    background: #fff;
  }
  .table-list {
    padding: 15px;
    background: #fff;
    margin-top: 10px;
  }
}
</style>
