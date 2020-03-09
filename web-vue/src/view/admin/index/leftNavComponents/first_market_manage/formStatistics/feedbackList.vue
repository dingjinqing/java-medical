<template>
  <div class="formStatisticsHome">
    <div class="formStatisticsBottom">
      <div class="formStatisticsTop">
        <div
          class="list"
          style="margin-left:10px"
        >
          <span>提交时间：</span>
          <el-date-picker
            size="small"
            v-model="createStartTime"
            type="datetime"
            :placeholder="$t('allGoods.batchDialog.selectDateTime')"
            default-time="12:00:00"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          &nbsp;
          至
          &nbsp;
          <el-date-picker
            size="small"
            v-model="createEndTime"
            type="datetime"
            :placeholder="$t('allGoods.batchDialog.selectDateTime')"
            default-time="12:00:00"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <div
          class="list"
          style="margin-left:10px;margin-right:10px"
        >
          <span>提交人昵称：</span>
          <el-input
            size="small"
            v-model="formNameInput"
            placeholder="请输入表单名称"
          ></el-input>
        </div>
        <el-button
          size="small"
          type="primary"
          @click="handleToScreen(1)"
        >筛选</el-button>
        <el-button
          size="small"
          type="primary"
          @click="handleToScreen(0)"
        >导出表格</el-button>
      </div>
      <div class="formStatisticsBottom">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="nickName"
            label="提交用户昵称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="提交用户电话"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="提交时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            :label="$t('pictureSetting.operation')"
            align="center"
            width="150"
          >
            <!-- slot-scope="scope" -->
            <template slot-scope="scope">
              <div class="operation">
                <span @click="handleToCheckData(scope.row)">查看操作数据</span>

              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="footer">
          <span>当前页面{{currentPage}}/{{totalPage}}，总记录{{totalRows}}条</span>
          <el-pagination
            @current-change="handleDetailCurrentChange"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totalRows"
          >
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { feedBackListQuery, exportFeedBackListQuery } from '@/api/admin/marketManage/formDecoration'
import { download } from '@/util/excelUtil.js'
export default {
  data () {
    return {
      createStartTime: '',
      createEndTime: '',
      formNameInput: '', // 提交人昵称
      tableData: [],
      rowData: [], // 传递过来得行数据
      currentPage: 1, // 当前页
      totalPage: 0, // 总页数
      totalRows: 0 // 总条数
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.handleToInit()
  },
  methods: {
    // 初始化数据
    handleToInit () {
      console.log(this.$route)
      if (this.$route.query.row) {
        localStorage.setItem('feedbackListId', JSON.stringify(this.$route.query.row))
        this.rowData = this.$route.query.row
        this.handleToQueryData()
      } else {
        console.log(localStorage.getItem('feedbackListId'))
        this.rowData = JSON.parse(localStorage.getItem('feedbackListId'))
        this.handleToQueryData()
      }
    },
    // 请求数据
    handleToQueryData () {
      console.log(this.rowData)
      let params = {
        pageId: this.rowData.pageId,
        shopId: Number(localStorage.getItem('V-ShopId')),
        nickName: this.formNameInput,
        startTime: this.createStartTime,
        endTime: this.createEndTime,
        currentPage: this.currentPage,
        pageRows: 20
      }
      feedBackListQuery(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.totalPage = res.content.page.pageCount
          this.totalRows = res.content.page.totalRows
        }
      })
    },
    // 当前页变化
    handleDetailCurrentChange () {
      this.handleToQueryData()
    },
    // 筛选和导出表格
    handleToScreen (flag) {
      console.log(flag)
      if (flag) {
        this.handleToQueryData()
      } else {
        let params = {
          pageId: this.rowData.pageId,
          shopId: Number(localStorage.getItem('V-ShopId')),
          nickName: this.formNameInput,
          startTime: this.createStartTime,
          endTime: this.createEndTime,
          currentPage: this.currentPage,
          pageRows: 20
        }
        exportFeedBackListQuery(params).then(res => {
          console.log(res)
          let fileName = localStorage.getItem('V-content-disposition')
          fileName = fileName.split(';')[1].split('=')[1]
          console.log(fileName)
          download(res, decodeURIComponent(fileName))
        })
      }
    },
    // 点击查看操作数据
    handleToCheckData ({ userId, pageId, submitId }) {
      console.log(pageId, pageId)
      this.$router.push({
        path: '/admin/home/main/feedbackDetails',
        query: {
          userId: userId,
          pageId: pageId,
          submitId: submitId
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.formStatisticsHome {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .formStatisticsBottom {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 10px 0;
    .formStatisticsTop {
      /deep/ .el-input {
        width: 150px;
      }
      display: flex;
      .list {
        display: flex;
        align-items: center;
        span {
          white-space: nowrap;
          display: inline-block;
          margin: 0 10px;
        }
      }
      /deep/ .el-button {
        margin: 0 10px;
      }
    }
    .operation {
      span {
        cursor: pointer;
        color: #5a8bff;
      }
    }
    .footer {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      margin-top: 10px;
    }
    .formStatisticsBottom {
      /deep/ .tableClss th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        font-weight: bold;
        color: #000;
        padding: 8px 10px;
      }
    }
  }
}
</style>
