<template>
  <div class="experience-version">
    <div class="select-menu top">
      <el-select
        v-model="mainData.categoryId"
        placeholder="请选择分类"
        size="small"
        class="select-input ml-6"
        clearable
        @change="changeOption"
      >
        <el-option
          v-for="item in categoryOptions"
          :key="item.categoryId"
          :label="item.categoryName"
          :value="item.categoryId"
        >
        </el-option>
      </el-select>
      <el-input
        v-model="mainData.keywords"
        placeholder="文章标题"
        size="small"
        class="select-input ml-6"
        clearable
      ></el-input>
      <el-button
        size="small"
        class="ml-6"
        type="primary"
        @click="searchAccount()"
      >
        搜索
      </el-button>
    </div>

    <el-table
      class="experience-log mt-10 formTable"
      header-row-class-name="table-th"
      :data="formTable"
      border
      :style="{maxWidth:'100%',width: '100%',height:tableHeight+ 'px',overflowY:'auto'}"
    >
      <el-table-column
        prop="title"
        label="标题"
        align="center"
      >
      </el-table-column>

      <el-table-column
        prop="categoryName"
        label="分类"
        align="center"
      >
      </el-table-column>

      <el-table-column
        prop="headPic"
        align="center"
        :formatter="headPicFormatter"
        label="头图"
      >
      </el-table-column>

      <el-table-column
        prop="updateTime"
        align="center"
        label="更新时间"
      >
      </el-table-column>

      <el-table-column
        prop="status"
        align="center"
        label="状态"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.status ===1"
          >已发布</span>
          <span v-if="scope.row.status ===0">未发布</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="isRecommend"
        align="center"
        label="是否推荐"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.isRecommend ===1"
          >是</span>
          <span v-if="scope.row.isRecommend ===0">否</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="operation"
        align="center"
        label="操作"
      >
        <template slot-scope="scope">
          <div style="display: inline-flex;">
            <el-button
              type="text"
              style="color:#000"
              @click="handleEditAccount(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              type="text"
              style="color:#000"
              @click="beforeUpdateStatus(scope.row)"
            >
              <div v-if="scope.row.status===1">标记为未发布</div>
              <div v-if="scope.row.status===0">重新发布</div>
            </el-button>
            <el-button
              type="text"
              style="color:#000"
              @click="beforedeleteOption(scope.row)"
            >
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <div class="footer clearfixed pagination-wrap">
      <span>每页{{this.pageRows}}行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数为：{{this.totalRows}}</span>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        layout="prev, pager, next, jumper"
        :page-count="pageCount"
        :small="pagination_b"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getListRequest, getCategoryRequest, updateStatusRequest, deleteRequest } from '@/api/system/essayAdmin.js'

export default {
  name: 'list',
  data () {
    return {
      categoryOptions: [{
        'categoryId': '',
        'categoryName': '请选择分类'
      }],
      mainData: {
        keywords: '',
        categoryId: ''
      },
      formTable: [{
      }],
      totalRows: null,
      pageRows: '',
      currentPage: 1,
      pageCount: null,
      pagination_b: true,
      value: '',
      text: '',
      tableHeight: document.documentElement.clientHeight - 330
    }
  },
  mounted () {
    this.searchCategory()
    this.searchAccount()
  },
  methods: {
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.searchAccount()
    },

    searchCategory () {
      getCategoryRequest(this.mainData).then((res) => {
        console.log('分类')
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.categoryOptions = this.categoryOptions.concat(content)
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
    },

    headPicFormatter (row, col) {
      if (row.headPic === null) {
        let stateForma = '无'
        return stateForma
      } else {
        let stateForma = row.headPic
        return stateForma
      }
    },
    changeOption (value) {
      console.log(value)
      this.searchAccount()
    },
    searchAccount () {
      this.mainData.currentPage = this.currentPage
      this.mainData.pageRows = this.pageRows
      getListRequest(this.mainData).then((res) => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          let formList = content.dataList
          let pageObj = content.page
          this.totalRows = pageObj.totalRows
          this.currentPage = pageObj.currentPage
          this.firstPage = pageObj.firstPage
          this.lastPage = pageObj.lastPage
          this.nextPage = pageObj.nextPage
          this.pageCount = pageObj.pageCount
          this.pageRows = pageObj.pageRows

          this.formTable = formList
        }
      }).catch(() => {
        this.$message.error('查询失败')
      })
    },
    beforeUpdateStatus (data) {
      this.$confirm('确认修改状态吗, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.updateStatus(data)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    updateStatus (data) {
      let param = {
        'articleId': data.articleId,
        'status': data.status === 1 ? 'cancel' : 'ok'
      }
      updateStatusRequest(param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
        this.searchAccount()
      }).catch(() => {
        this.$message.error('失败')
      })
    },
    beforedeleteOption (data) {
      this.$confirm('确认删除此文章吗, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteOption(data)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    deleteOption (data) {
      let param = {
        'articleId': data.articleId
      }
      deleteRequest(param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
        this.searchAccount()
      }).catch(() => {
        this.$message.error('失败')
      })
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    handleEditAccount (data) {
      let params = {
        'articleId': data.articleId,
        'flag': 4
      }
      this.$emit('editShow', params)
    }
  }
}
</script>

<style lang="scss" scoped>
.select-menu {
  display: flex;
  padding: 10px;
  background: #fff;
}
.select-input {
  width: 200px;
}
.footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.footer > span {
  font-size: 14px;
}
.useSpan {
  font-weight: 700;
  color: #fff;
  padding: 4px;
  border-radius: 5px;
  background-color: #739e73;
}
</style>
