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
          <el-input
            size="small"
            v-model="param.title"
          ></el-input>
        </div>
        <div class="languageContent">
          <span>请输入分销员推广语，将帮助分销员朋友圈推广：</span>

          <el-input
            v-model="param.promotionLanguage"
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
          <template slot-scope="scope">
            <div class="opt">
              <span @click="edit(scope.row.id)">编辑</span>
              <span @click="stop(scope.row.id)">停用</span>
              <span @click="del(scope.row.id)">删除</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="search"
      />
    </div>

  </div>
</template>

<script>
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
import { advertisementList, advertisementAdd, advertisementPause, advertisementDelete } from '@/api/admin/marketManage/distribution.js'
export default {
  components: { pagination },
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
      promotionLanguage: null,
      centerDialogVisible: false,
      youAD: '',
      param: {
        promotionLanguage: '',
        title: ''
      },

      pageParams: {}
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
        case 0: row.isBlock = '已启用'
          break
        case 1: row.isBlock = '已停用'
          break
      }
      return row.isBlock
    },
    search () {
      advertisementList(this.pageParams).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
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
    stop (id) {
      this.$confirm('是否确认停用该推广语', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        advertisementPause(id).then(res => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '停用成功!'
            })
            this.search()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消停用操作'
        })
      })
    },
    del (id) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        advertisementDelete(id).then(res => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.search()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    cancel () {
      this.centerDialogVisible = false
      console.log('cancel')
    },
    confirm () {
      this.centerDialogVisible = false
      console.log(this.param)
      advertisementAdd(this.param).then(res => {
        if (res.error === 0) {
          this.search()
        }
      })
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
