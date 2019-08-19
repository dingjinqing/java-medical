<template>
  <div class="addBrandDialog">
    <el-dialog
      title="添加品牌"
      :visible.sync="dialogVisible"
      width="50%"
      center
      :before-close="modalClose"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >

      <el-form :model="formData">

        <el-row>
          <el-col :span="9">
            <el-form-item
              label="品牌名称："
              :label-width="formLabelWidth"
            >
              <el-input
                style="width:160px"
                size="small"
                v-model="formData.brandName"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="品牌分类："
              :label-width="formLabelWidth"
            >
              <el-select
                size="small"
                style="width:160px"
                v-model="formData.classifyId"
                placeholder="请选择品牌分类"
              >
                <el-option
                  v-for="item in classifyIdOPtions"
                  :key="item.classifyName"
                  :label="item.classifyName"
                  :value="item.classifyId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-button
              @click="filter"
              type="primary"
              size="small"
            >筛选</el-button>

          </el-col>
        </el-row>

      </el-form>
      <el-table
        :data="tableData"
        class="table"
        style="width: 100%"
      >
        <el-table-column
          property="brandName"
          label="品牌名称"
          width="210"
        ></el-table-column>
        <el-table-column
          property="classifyId"
          label="品牌分类"
          width="210"
        ></el-table-column>
        <el-table-column
          property="createTime"
          label="创建时间"
          width="210"
        ></el-table-column>
      </el-table>
      <!-- 分页功能 -->
      <div class="tabListPage">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageRows"
          :page-sizes="pageSizes"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          type="primary"
          @click="handleSave"
        >确 定</el-button>
        <el-button
          @click="handleCancel"
          size="small"
        > 取 消</el-button>

      </div>
      <!-- <el-button @click="handleTest">测试数据</el-button> -->
    </el-dialog>

  </div>
</template>
<script>
import { brandAllGetRequest, classificationSelectRequest } from '@/api/admin/brandManagement'

// import { mapGetters, mapMutations } from 'vuex'
export default {
  name: 'addBrandDialog',
  created() {
    this.fetchTableData(this.currentPage, this.pageRows)
    this.initData()
  },
  computed: {
    // ...mapGetters(['dialogVisible'])
  },
  props: ['dialogVisible'],
  data() {
    return {
      formData: {
        brandName: '',
        classifyId: ''
      },
      formLabelWidth: '120px',
      tableData: [],
      currentPage: 1,
      pageSizes: [3, 5, 8],
      pageRows: 3,
      total: null,
      classifyIdOPtions: []
    }
  },

  methods: {
    handleTest() {
      console.log(this.classifyIdOPtions)
      // let params = {
      //   'currentPage': '1',
      //   'pageRows': '3' }

      // brandAllGetRequest(params).then(res => console.log(res)).catch(err => console.log(err))
      // brandAddRequest({
      //   'brandName': '匡威24',
      //   'ename': 'converse',

      //   'first': 13

      // }).then(res => console.log(res)).catch(err => console.log(err))
    },
    // ...mapMutations(['SHOW_DIALOG']),
    handleSave() {
      this.$emit('update:dialogVisible', false) // 直接修改父组件的属性
    },
    handleCancel() {
      this.$emit('update:dialogVisible', false) // 直接修改父组件的属性
    },
    modalClose() {
      this.$emit('update:dialogVisible', false) // 直接修改父组件的属性
    },
    filter() {
      let params = {
        'brandName': this.formData.brandName,
        'classifyId': this.formData.classifyId,

        'currentPage': 1,
        'pageRows': 20
      }
      brandAllGetRequest(params).then(res => {
        const { error, content } = res
        if (error === 0) {
          let pageInfo = content.page
          this.total = pageInfo.totalRows
          this.tableData = content.dataList
        }
      }).catch(err => console.log(err))
    },
    fetchTableData(currentPage, pageRows) {
      console.log(currentPage, pageRows)
      brandAllGetRequest({
        'currentPage': currentPage,
        'pageRows': pageRows
      }).then(res => {
        const { error, content } = res
        if (error === 0) {
          let pageInfo = content.page
          //  currentPage: 1//当前页码
          //   firstPage: 1//首页
          //   lastPage: 1//最后一页
          //   nextPage: 1//下一页
          //   pageCount: 1//总页数
          //   pageRows: 20//单页行数
          //   prePage: 1//上一页
          //   totalRows: 2//总行数
          this.total = pageInfo.totalRows
          this.tableData = content.dataList
        }
      }).catch(err => console.log(err))
    },
    handleSizeChange(val) {
      console.log(val)
      this.pageRows = val
      this.fetchTableData(1, val)
      this.currentPage = 1
    },
    handleCurrentChange(val) {
      console.log(val)
      this.currentPage = val
      // 切换页码时，要获取每页显示的条数
      this.fetchTableData(this.currentPage, this.pageRows)
      // this.fetchTableData((val) * (this.pageSize), this.PageSize)
    },
    // init Data
    initData() {
      classificationSelectRequest().then(res => {
        this.classifyIdOPtions = res.content
      }).catch(err => console.log(err))
    }
  }
}
</script>
<style scoped>
.table {
  margin-left: 25px;
}
</style>
