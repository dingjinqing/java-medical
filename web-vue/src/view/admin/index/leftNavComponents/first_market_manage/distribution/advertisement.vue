<template>
  <div class="content">
    <div class="table_list">
      <div class="select_info">
        <div class="leftarea">
          <span>{{$t('distribution.createTime')}}</span>
          <el-date-picker
            v-model="time.startCreateTime"
            type="datetime"
            :placeholder="$t('distribution.chooseDate')"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span>{{$t('distribution.to')}}</span>
          <el-date-picker
            v-model="time.endCreateTime"
            type="datetime"
            :placeholder="$t('distribution.chooseDate')"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <div class="leftarea">
          <span>{{$t('distribution.endModifyTime')}}</span>
          <el-date-picker
            v-model="time.startUpdateTime"
            type="datetime"
            :placeholder="$t('distribution.chooseDate')"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span>{{$t('distribution.to')}}</span>
          <el-date-picker
            v-model="time.endUpdateTime"
            type="datetime"
            :placeholder="$t('distribution.chooseDate')"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <div class="midarea">
          <span>{{$t('distribution.advertisementContent')}}</span>
          <el-input
            v-model="promotionLanguage"
            size="small"
            :placeholder="$t('distribution.inputAdvertisement')"
          ></el-input>
        </div>
        <div class="rightarea">
          <el-button
            type="primary"
            size="small"
            @click="search()"
          >{{$t('distribution.search')}}</el-button>
        </div>
      </div>
      <el-button
        :plain="true"
        type="primary"
        size="small"
        @click="add"
      >{{$t('distribution.addAdvertisement')}}</el-button>
      <el-dialog
        title="添加推广语"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
      >
        <div class="title">
          <span>标题：</span>
          <el-input
            prop="title"
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
            v-model="opt"
            type="primary"
            @click="confirm()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>

    <div class="table_list">
      <el-table
        v-loading="loading"
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
              <span
                prop="isPause"
                @click="stop(scope.row.id)"
                v-if="scope.row.isPause"
              >停用</span>
              <span
                prop="isPause"
                @click="start(scope.row.id)"
                v-else
              >启用</span>
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
import { advertisementList, advertisementAdd, advertisementPause, advertisementDelete, advertisementStart, advertisementGetOne, advertisementSave } from '@/api/admin/marketManage/distribution.js'
export default {
  components: { pagination },
  data () {
    return {
      tableData: [],
      editData: [],
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
      isPause: '',
      pageParams: {},
      opt: 1,
      id: '',
      loading: false

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
      this.loading = true
      this.pageParams.promotionLanguage = this.promotionLanguage
      this.pageParams.startCreateTime = this.time.startCreateTime
      this.pageParams.endCreateTime = this.time.endCreateTime
      this.pageParams.startUpdateTime = this.time.startUpdateTime
      this.pageParams.endUpdateTime = this.time.endUpdateTime
      console.log(this.pageParams)
      advertisementList(this.pageParams).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    handleData (data) {
      data.map((item, index) => {
        if (item.isBlock === 0) {
          item.isPause = true
        } else {
          item.isPause = false
        }
      })
      this.tableData = data
    },
    add () {
      let param = {
        promotionLanguage: '',
        title: ''
      }
      this.param = param
      this.centerDialogVisible = true
      this.opt = 1
    },
    edit (id) {
      advertisementGetOne(id).then(res => {
        let param = {
          promotionLanguage: res.content.promotionLanguage,
          title: res.content.title
        }
        this.param = param
        this.opt = 0
        this.id = res.content.id
      })

      this.centerDialogVisible = true
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

      })
    },
    start (id) {
      this.$confirm('是否确认启用该推广语', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        advertisementStart(id).then(res => {
          if (res.error === 0) {
            this.$message({
              type: 'success',
              message: '启用成功!'
            })
            this.search()
          }
        })
      }).catch(() => {

      })
    },
    del (id) {
      this.$confirm('此操作将永久删除该推广语, 是否继续?', '提示', {
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

      })
    },
    cancel () {
      this.centerDialogVisible = false
    },
    confirm () {
      this.centerDialogVisible = false
      if (this.opt === 1) {
        advertisementAdd(this.param).then(res => {
          if (res.error === 0) {
            this.$message({
              message: '添加成功',
              type: 'success'
            })
            this.search()
          }
        })
      } else {
        this.param.id = this.id
        advertisementSave(this.param).then(res => {
          if (res.error === 0) {
            this.$message({
              message: '编辑成功',
              type: 'success'
            })
            this.search()
          }
        })
      }
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
  text-align: center;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
