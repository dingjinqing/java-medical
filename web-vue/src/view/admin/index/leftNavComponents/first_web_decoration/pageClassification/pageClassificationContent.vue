<template>
  <div class="pageClassificationContent">
    <div class="main">
      <el-row>
        <el-col :span='24'>
          <el-form label-width="100px">
            <el-row style="line-height:40px;">
              <el-col :span="6">
                <div class="grid-content ">
                  <el-form-item
                    :label="$t('pageClassification.catergory')+'：'"
                    style="margin:0;"
                  >
                    <el-input
                      v-model="requestParams.keywords"
                      :placeholder="$t('pageClassification.catergoryNamePlease')"
                      size="small"
                      clearable
                    ></el-input>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="6">
                <div
                  class="grid-content "
                  style="margin-left:20px;"
                >
                  <div class="grid-content ">
                    <el-button
                      class="btn"
                      type="primary"
                      size="small"
                      @click="handleQuery"
                    >{{$t('pageClassification.query')}}
                    </el-button>
                  </div>
                </div>
              </el-col>
              <el-col
                :span="6"
                :offset="6"
              >
                <div
                  class="grid-content "
                  style="text-align: right;"
                >
                  <el-button
                    class="btn"
                    type="primary"
                    size="small"
                    @click="dialogVisible = true"
                  >{{$t('pageClassification.addCatergory')}}
                  </el-button>
                </div>
              </el-col>
            </el-row>
          </el-form>
        </el-col>
      </el-row>
    </div>
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        v-loading="loading"
        border
        style="width: 100%"
      >

        <el-table-column
          prop="name"
          :label="$t('pageClassification.catergory')"
          align="center"
        >
          <template slot-scope="scope">
            <el-popover
              placement="top"
              :title="$t('pageClassification.edit')"
              v-model="scope.row.popover"
              width="260"
            >
              <el-form label-width="100px">
                <el-form-item :label="$t('pageClassification.catergoryName')">
                  <el-input
                    v-model="scope.row.name"
                    size="small"
                  ></el-input>
                </el-form-item>
              </el-form>
              <div style="text-align: right; margin: 0">
                <el-button
                  @click="scope.row.popover = false"
                  size="mini"
                  type="text"
                >取消</el-button>
                <el-button
                  type="primary"
                  size="mini"
                  @click="editCatergory(scope.row)"
                >确定</el-button>
              </div>
              <span
                slot="reference"
                style="color: #5A8BFF;"
              >{{scope.row.name}}</span>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column
          prop="subPageCount"
          :label="$t('pageClassification.catergoryNum')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          :label="$t('pageClassification.createTime')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="receiveNum"
          :label="$t('pageClassification.operate')"
          align="center"
        >
          <template slot-scope="scope">
            <a
              style="color: #5A8BFF;margin-right:10px;cursor: pointer;"
              @click="handleEdit(scope.row)"
            >{{$t('pageClassification.edit')}}</a>
            <a
              style="color: #5A8BFF;margin-right:10px;cursor: pointer;"
              @click="removeCatergory(scope.row)"
            >{{$t('pageClassification.remove')}}</a>
            <a
              style="color: #5A8BFF;cursor: pointer;"
              @click="jumpCatergory(scope.row)"
            >{{$t('pageClassification.viewPage')}}</a>
          </template>
        </el-table-column>

      </el-table>
      <div class="footer">
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="handleQuery"
      />
    </div>
    <div>
      <el-dialog
        :title="$t('pageClassification.addCatergory')"
        :visible.sync="dialogVisible"
        width="30%"
        center
      >
        <el-form label-width="100px">
          <el-form-item :label="$t('pageClassification.catergoryName')">
            <el-input
              v-model="requestFrom.pageName"
              size="small"
              clearable
            ></el-input>
          </el-form-item>
        </el-form>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="dialogVisible = false"
          >{{$t('pageClassification.cancel')}}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="addCatergory"
          >{{$t('pageClassification.confirm')}}</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {
  getPageclassificationData,
  addPageclassification,
  updateCategoryName,
  deleteCategoryById
} from '@/api/admin/smallProgramManagement/pageClassification/pageClassification'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  name: 'pageClassificationContent',
  components: {
    pagination
  },
  data () {
    return {
      requestParams: {
        keywords: null,
        pageRows: 3,
        currentPage: 1
      },
      pageParams: {
        pageRows: 3,
        currentPage: 1
      },
      requestFrom: {
        pageName: null
      },
      tableData: [],
      loading: false,
      dialogVisible: false,
      editVisible: false

    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.handleQuery()
  },
  methods: {
    handleFetchData () {
      addPageclassification({
        'pageName': '页面分类的测试'
      }).then(res => console.log(res)).catch(err => console.log(err))
    },
    // 查询
    handleQuery () {
      this.loading = true
      this.requestParams.pageRows = this.pageParams.pageRows
      this.requestParams.currentPage = this.pageParams.currentPage
      getPageclassificationData(this.requestParams).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.requestParams.pageRows = res.content.page.pageRows
        this.requestParams.currentPage = res.content.page.currentPage
        this.tableData = res.content.dataList
      }).catch(err => console.log(err))
      this.loading = false
    },
    addCatergory () {
      addPageclassification(this.requestFrom).then(res => {
        console.log(res)
        this.handleQuery()
        this.requestParams.keywords = null
      }).catch(err => console.log(err))
      this.dialogVisible = false
    },
    editCatergory (row) {
      console.log('editCatergory', row)
      updateCategoryName({ pageId: row.id, pageName: row.name }).then(res => {
        console.log(res)
        this.handleQuery()
        row.popover = false
      }).catch(err => console.log(err))
    },
    removeCatergory (row) {
      this.$confirm(this.$t('pageClassification.cancel'), {
        confirmButtonText: this.$t('pageClassification.confirm'),
        cancelButtonText: this.$t('pageClassification.cancel'),
        type: 'warning'
      }).then(() => {
        deleteCategoryById({ pageId: row.id }).then(res => {
          console.log(res)
          this.handleQuery()
        }).catch(err => console.log(err))
      }).catch(() => {
      })
    },
    jumpCatergory (row) {
      console.log('跳转到页面装修 id = ', row)
      this.$router.push({ path: '/admin/home/main/pictureSetting', query: { page: row.id } })
    },
    handleEdit (row) {
      this.$prompt(this.$t('pageClassification.catergoryNamePlease'), {
        confirmButtonText: this.$t('pageClassification.confirm'),
        cancelButtonText: this.$t('pageClassification.cancel')
      }).then(({ value }) => {
        row.name = value
        this.editCatergory(row)
        this.handleQuery()
      }).catch(() => {
      })
    }
  }
}
</script>
<style lang="css" scoped>
.pageClassificationContent {
  padding: 10px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
}
.main {
  background-color: #fff;
  padding: 15px;
}
.table_list {
  background-color: white;
  margin-top: 10px;
  padding: 15px;
}
</style>
