<template>
  <div class="experience-version">
    <div class="select-menu top">
      <el-form
        size="small"
        :inline="true"
        :model="mainData"
        class="demo-form-inline"
      >
        <el-form-item label="文章分类">
          <el-input
            v-model="mainData.keyWord"
            placeholder="文章分类名称"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="searchAccount"
          >查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="addDialog"
          >添加</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table
      class="experience-log mt-10 formTable"
      header-row-class-name="table-th"
      :data="formTable"
      border
      :style="{maxWidth:'100%',width: '100%',height:tableHeight+ 'px',overflowY:'auto'}"
    >
      <el-table-column
        prop="categoryId"
        label="编号"
        align="center"
      >
      </el-table-column>

      <el-table-column
        prop="categoryName"
        label="文章分类"
        align="center"
      >
      </el-table-column>

      <el-table-column
        prop="useFooterNav"
        align="center"
        :formatter="useFooterNavFormatter"
        label="是否用于底部导航"
      >
      </el-table-column>

      <el-table-column
        prop="addTime"
        align="center"
        label="创建时间"
      >
      </el-table-column>

      <el-table-column
        prop="updateTime"
        align="center"
        label="更新时间"
      >
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
              @click="editDialog(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              type="text"
              style="color:#000"
              @click="getNumber(scope.row)"
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
    <div
      class="renew"
      v-if="dialogFormVisible"
    >
      <el-dialog
        :title="dialogTitle"
        width="30%"
        :visible.sync="dialogFormVisible"
      >
        <el-form
          :model="form"
          :rules="rules"
          ref="ruleForm"
        >
          <el-form-item
            label="分类名称"
            :label-width="formLabelWidth"
            prop='categoryName'
          >
            <el-input
              size="small"
              style="width:200px"
              v-model="form.categoryName"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="用于底部导航"
            :label-width="formLabelWidth"
            prop='useFootNav'
          >
            <el-select
              style="width:200px"
              v-model="form.useFootNav"
              placeholder="请选择活动区域"
            >
              <el-option
                label="否"
                value="0"
              ></el-option>
              <el-option
                label="是"
                value="1"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="submitForm('ruleForm')"
          >确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { searchOneAccountRequest, updatecategoryRequest, deleteCategoryRequest, addCategoryRequest, editCategoryRequest, beforedeleteCategoryRequest } from '@/api/system/essayAdmin.js'

export default {
  name: 'list',
  data () {
    return {
      categoryOptions: [{
        'categoryId': '',
        'categoryName': '请选择分类'
      }],
      mainData: {
        keywords: ''
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
      dialogFormVisible: false,
      categoryName: null,
      formLabelWidth: '120px',
      form: {
        categoryName: '',
        useFootNav: '',
        categoryId: ''
      },
      tableHeight: document.documentElement.clientHeight - 330,
      rules: {
        categoryName: [
          { required: true, message: '请填写分类名称', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        useFootNav: [
          { required: true, message: '请填写活动形式', trigger: 'change' }
        ]
      },
      dialogTitle: '添加文章分类',
      isAdd: true,
      number: 0
    }
  },
  mounted () {
    this.searchAccount()
  },
  methods: {
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.searchAccount()
    },
    useFooterNavFormatter (row, col) {
      let useFooterNav = 0
      switch (row.useFooterNav) {
        case 0:
          useFooterNav = '否'
          break
        case 1:
          useFooterNav = '是'
          break
      }
      return useFooterNav
    },
    changeOption (value) {
      console.log(value)
      this.searchAccount()
    },
    searchAccount () {
      this.mainData.currentPage = this.currentPage
      this.mainData.pageRows = this.pageRows
      searchOneAccountRequest(this.mainData).then((res) => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          let pageObj = content.page
          this.totalRows = pageObj.totalRows
          this.currentPage = pageObj.currentPage
          this.firstPage = pageObj.firstPage
          this.lastPage = pageObj.lastPage
          this.nextPage = pageObj.nextPage
          this.pageCount = pageObj.pageCount
          this.pageRows = pageObj.pageRows
          this.formTable = content.dataList
        }
      }).catch(() => {
        this.$message.error('查询失败')
      })
    },
    handleEditOption (data) {
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
        'status': data.status === 1 ? 'ok' : 'cancel'
      }
      updatecategoryRequest(param).then((res) => {
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
      this.$confirm('确认删除此文章分类，当前分类下有<strong style="color: red;">' + this.number + '</strong>篇文章 是否继续?', '提示', {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.number === 0) {
          this.deleteOption(data)
        } else {
          this.$confirm('你真的确定吗？删除将导致相应文章不可显示', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteOption(data)
          }).catch(() => {
            this.$message.info('已取消')
          })
        }
      }).catch(() => {
        this.$message.info('已取消')
      })
    },
    deleteOption (data) {
      console.log('删除')
      console.log(data)
      let param = {
        'categoryId': data.categoryId,
        'categoryName': data.categoryName
      }
      deleteCategoryRequest(param).then((res) => {
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
    addOption () {
      let param = {
        'categoryName': this.form.categoryName,
        'useFooterNav': this.form.useFootNav === '1' ? 'ok' : 'cancel'
      }
      addCategoryRequest(param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
          this.form = {}
        } else {
          this.$message.error(res.message)
        }
        this.dialogFormVisible = false
        this.searchAccount()
      }).catch(() => {
        this.$message.error('失败')
      })
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isAdd) {
            this.addOption()
          } else {
            this.editOption()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 添加
    addDialog () {
      this.dialogFormVisible = true
      this.dialogTitle = '添加文章分类'
      this.isAdd = true
      this.form = {}
    },
    // 编辑
    editDialog (data) {
      console.log('data')
      console.log(data)
      this.dialogFormVisible = true
      this.dialogTitle = '编辑文章分类'
      this.isAdd = false
      this.$set(this.form, 'categoryName', data.categoryName)
      this.$set(this.form, 'useFootNav', data.useFooterNav + '')
      this.form.categoryId = data.categoryId
    },
    editOption () {
      let param = {
        'categoryName': this.form.categoryName,
        'useFooterNav': this.form.useFootNav === '1' ? 'ok' : 'cancel',
        'categoryId': this.form.categoryId
      }
      editCategoryRequest(param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
          this.form = {}
        } else {
          this.$message.error(res.message)
        }
        this.dialogFormVisible = false
        this.searchAccount()
      }).catch(() => {
        this.$message.error('失败')
      })
    },
    getNumber (data) {
      let param = {
        'categoryId': data.categoryId,
        'categoryName': data.categoryName
      }
      beforedeleteCategoryRequest(param).then((res) => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.number = content
          console.log(this.number)
          this.beforedeleteOption(data)
        }
      }).catch(() => {
        this.$message.error('查询失败')
      })
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
.renew {
  z-index: 9999;
  border: 1px solid #eee;
  background: #ffffff;
  /deep/ .el-dialog__header {
    border-bottom: 1px solid #ccc;
  }
}
</style>
