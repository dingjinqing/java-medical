<template>
  <div class="backgroundTaskList">
    <div class="backgroundTaskListMain">
      <div class="TaskListTop">
        <span>执行状态：</span>
        <el-select
          v-model="value"
          size="small"
          @change="changeState"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div>
      <div class="footer">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            align="center"
            label="任务名称"
            prop="jobName"
          >
          </el-table-column>
          <el-table-column
            prop="parameters"
            label="参数"
            align="center"
            width="300"
          >
          </el-table-column>
          <el-table-column
            prop="state"
            label="状态"
            align="center"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.state ===0">初始</span>
              <span v-if="scope.row.state ===1">执行中</span>
              <span v-if="scope.row.state ===2">完成</span>
              <span v-if="scope.row.state ===3">失败</span>
              <span v-if="scope.row.state ===4">终止</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="failReason"
            label="失败原因"
            align="center"
          >

          </el-table-column>
          <el-table-column
            label="进度"
            align="center"
          >
            <template slot-scope="scope">
              <el-progress
                :text-inside="true"
                :stroke-width="24"
                :percentage="scope.row.progress"
                status="success"
              ></el-progress>

            </template>
          </el-table-column>

          <el-table-column
            prop="progressInfo"
            label="进度信息"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="memo"
            label="备注"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="created"
            label="创建时间"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="endTime"
            label="结束时间"
            align="center"
          >

          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div
                v-if="scope.row.state===0||scope.row.state===1"
                class="operation"
                @click="handleToStop(scope.row)"
              >强行终止</div>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.totle}}</div>
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totle"
          >
          </el-pagination>

          <el-dialog
            class="dialog"
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
          >
            <span class="el-dialog-span">确定强行停止此任务吗？</span>
            <span
              slot="footer"
              class="dialog-footer"
            >
              <el-button @click="handleToBatchSubmit(0)">取 消</el-button>
              <el-button
                type="primary"
                @click="handleToBatchSubmit(1)"
              >确 定</el-button>
            </span>
          </el-dialog>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { processList, killBatchUpload } from '@/api/system/programManage'
export default {
  data () {
    return {
      options: [{
        value: '-1',
        label: '--请选择--'
      }, {
        value: '0',
        label: '初始'
      }, {
        value: '1',
        label: '执行中'
      }, {
        value: '2',
        label: '执行完成'
      }, {
        value: '3',
        label: '执行失败'
      },
      {
        value: '4',
        label: '执行停止'
      }],
      value: '-1',
      tableData: [],
      currentPage: 1,
      totle: 0,
      pageCount: 1,
      recId: null,
      dialogVisible: false
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      let obj = {
        'pageRows': 20,
        'currentPage': this.currentPage,
        'state': this.value
      }
      processList(obj).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.totle = res.content.page.totalRows
          this.currentPage = res.content.page.currentPage
          this.pageCount = res.content.page.pageCount
          console.log(this.pageCount)
        }
        console.log(res)
      })
    },
    // 当前页改变
    handleCurrentChange () {
      this.defaultData()
    },
    // 每页显示条数变化
    handleSizeChange () {

    },
    // 强行终止
    handleToStop (row) {
      this.recId = row.recId
      this.dialogVisible = true
      console.log(row)
    },
    changeState (item) {
      this.value = item
      this.defaultData()
    },
    handleToBatchSubmit (flag) {
      if (flag === 1) {
        // 确定时调用接口
        killBatchUpload(this.recId).then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.defaluteData()
          } else {
            this.$message.error(res.message)
          }
        })
        this.defaultData()
      }
      this.dialogVisible = false
    }
  }
}
</script>
<style lang="scss" scoped>
/deep/ .el-dialog__body {
  padding: 0px 20px;
}

.backgroundTaskList {
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .backgroundTaskListMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .TaskListTop {
      height: 80px;
      display: flex;
      align-items: center;
    }
  }
  .footer {
    margin-top: 10px;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
      .el-checkbox {
        margin-left: -4px;
      }
    }
    .operation {
      cursor: pointer;
      &:hover {
        color: #333;
        text-decoration: underline;
      }
    }
  }
  .pagination {
    background-color: #fff;
    height: 50px;
    line-height: 50px;
    color: #333;
    font-size: 14px;
    display: flex;
    justify-content: flex-end;
    padding-right: 10px;
    /deep/ .el-pagination {
      display: flex;
      align-items: center;
      .el-pager {
        display: flex;
        align-items: center;
      }
    }
  }
}
</style>
