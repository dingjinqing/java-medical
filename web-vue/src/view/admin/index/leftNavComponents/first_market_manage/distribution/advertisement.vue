<template>
  <div class="content">
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>创建时间</span>
          <el-date-picker
            v-model="time.startCreateTime"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="time.endCreateTime"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
        <div class="leftarea">
          <span>最后修改时间</span>
          <el-date-picker
            v-model="time.startUpdateTime"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="time.endUpdateTime"
            type="date"
            placeholder="选择日期"
            size="small"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
        <div class="midarea">
          <span>推广语内容</span>
          <el-input
            v-model="promotionLanguage"
            size="small"
            placeholder="请输入推广语"
          ></el-input>
        </div>
        <div class="rightarea">
          <el-button
            type="primary"
            size="small"
            @click="search()"
          >查询</el-button>
        </div>
      </div>
      <el-button
        type="primary"
        size="small"
        @click="centerDialogVisible = true"
      >添加推广语</el-button>
      <el-dialog
        title="添加推广语"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
      >
        <div class="title">
          <span>标题：</span>
          <el-input size="small"></el-input>
        </div>
        <div class="languageContent">
          <span>请输入分销员推广语，将帮助分销员朋友圈推广：</span>
          <el-input
            v-model="youAD"
            type="textarea"
            placeholder="请输入"
            rows=6
          ></el-input>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="cancel">取 消</el-button>
          <el-button
            type="primary"
            @click="confirm"
          >确 定</el-button>
        </span>
      </el-dialog>
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
          prop="title"
          label="推广语标签"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="promotionLanguage"
          label="推广语内容"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="创建时间"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="updateTime"
          label="更新时间"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="isBlock"
          label="状态"
          align="center"
          :formatter="changeState"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
          <div class="opt">
            <span @click="edit()">编辑</span>
            <span @click="stop()">停用</span>
            <span @click="del">删除</span>
          </div>
        </el-table-column>
      </el-table>
      <div class="footer">
        <span>当前页面1/1，总记录4条</span>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="20"
          layout="prev, pager, next, jumper"
          :total="4"
        >
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
import { advertisementList } from '@/api/admin/marketManage/distribution.js'
export default {
  data () {
    return {
      tableData: [],
      pageData: {},
      createTime: '',
      currentPage: null,
      time: {
        startCreateTime: '',
        endCreateTime: '',
        startUpdateTime: '',
        endUpdateTime: ''
      },
      promotionLanguage: '',
      centerDialogVisible: false,
      youAD: ''
    }
  },
  created () {
    this.search()
  },
  mounted () {
    // 初始化数据
    // this.list()
  },
  methods: {
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },
    // 状态的数字转化为文字
    changeState (row, col) {
      switch (row.isBlock) {
        case 0: row.isBlock = '已停用'
          break
        case 1: row.isBlock = '已启用'
          break
      }
      return row.isBlock
    },
    search () {
      let obj = {
        'currentPage': 1,
        'pageRows': 1
      }

      advertisementList(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageData = res.content.page
        }
      })
    },
    handleData (data) {
      data.map((item, index) => {
        console.log(data)
      })
      this.tableData = data
    },
    edit () {
      console.log('编辑')
    },
    stop () {
      console.log('停用')
    },
    del () {
      console.log('删除')
    },
    cancel () {
      this.centerDialogVisible = false
      console.log('cancel')
    },
    confirm () {
      this.centerDialogVisible = false
      console.log('conirm')
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
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
  padding: 0px 20px 10px 20px;
  .select_info {
    display: flex;
    margin: 10px 0px;
    .leftarea {
      display: flex;
      margin-right: 30px;
      :first-child {
        margin-right: 10px;
      }
      :nth-of-type(1) {
        margin: 0 10px 0 0;
      }
      :nth-of-type(2) {
        margin: 0 10px 0 0;
      }
    }
    .midarea {
      display: flex;
      margin-right: 30px;
      :first-child {
        margin-right: 10px;
      }
    }
    span {
      white-space: nowrap;
      height: 32px;
      line-height: 32px;
    }
    /deep/ .el-input {
      width: 150px;
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
  .el-dialog__footer {
    text-align: left;
    .el-button {
      padding: 10px;
    }
  }
  .el-dialog__body {
    .title {
      display: flex;
      span {
        height: 30px;
        line-height: 30px;
      }
      .el-input {
        width: 200px !important;
      }
    }
    .languageContent {
      margin-top: 20px;
      span {
        margin-bottom: 10px;
      }
      .el-textarea {
        margin-top: 10px;
      }
    }
  }
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
