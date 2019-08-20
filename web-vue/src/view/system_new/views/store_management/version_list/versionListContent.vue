<template>
  <div class="experience-version">
    <div class="select-menu top">
      <div class="title">{{$t('versionList.title.versionList')}}</div>
      <el-input
        v-model="mainData.keywords"
        size="small"
        class="select-input ml-6"
        clearable
      ></el-input>
      <el-button
        size="small"
        class="ml-6"
        type="primary"
        @click="search()"
      >
        {{$t('versionList.infoContent.serarch')}}
      </el-button>
    </div>

    <el-table
      class="experience-log mt-10 formTable"
      header-row-class-name="table-th"
      :data="formTable"
      border
      style="width: 100%"
      height="400"
    >
      <el-table-column
        prop="userName"
        :label="$t('versionList.table.versionID')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="accountName"
        :label="$t('versionList.table.versionName')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="company"
        align="center"
        :label="$t('versionList.table.versionLevel')"
      >
      </el-table-column>
      <el-table-column
        prop="state"
        align="center"
        :label="$t('versionList.table.createTime')"
      >
      </el-table-column>
      <el-table-column
        prop="shopGrade"
        align="center"
        :label="$t('versionList.table.updateTime')"
      >
      </el-table-column>
      <el-table-column
        prop="shopNumber"
        align="center"
        :label="$t('versionList.table.operate')"
      >
      </el-table-column>
    </el-table>

    <div class="footer clearfixed pagination-wrap">
      <span>每页{{this.pageRows}}行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数为：{{this.totalRows}}</span>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage3"
        layout="prev, pager, next, jumper"
        :page-count="pageCount"
        :small="pagination_b"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
// import { searchAccountRequest } from '@/api/system/accountList.js'

export default {
  name: 'experienceVersion',
  data () {
    return {

      mainData: {
        state: '',
        keywords: '',
        company: ''
      },
      formTable: [{
        usreName: '',
        company: '',
        state: '',
        shopGrade: '',
        shopNumber: '',
        addTime: '',
        buyTime: '',
        endTime: '',
        renewMoney: '',
        mobile: ''
      }],
      totalRows: null,
      pageRows: '',
      currentPage: '',
      currentPage3: 1,
      pageCount: null,
      pagination_b: true,
      value: '',
      text: ''
    }
  },
  created () {
    this.search()
  },
  methods: {
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.search()
    },

    // 接口调用
    search () {
      // let obj1 = {
      // }
      // let parameter = Object.assign(obj1, this.mainData)
      // searchAccountRequest(parameter).then((res) => {
      //   // console.log(res)
      //   const { error, content } = res
      //   if (error === 0) {
      //     let formList = content.dataList
      //     let pageObj = content.page
      //     this.totalRows = pageObj.totalRows
      //     this.currentPage = pageObj.currentPage
      //     this.firstPage = pageObj.firstPage
      //     this.lastPage = pageObj.lastPage
      //     this.nextPage = pageObj.nextPage
      //     this.pageCount = pageObj.pageCount
      //     this.pageRows = pageObj.pageRows

      //     this.formTable = formList
      //   }
      // }).catch(() => {
      //   this.$message.error('保存失败')
      // })
    }
  }
}
</script>

<style lang="scss" scoped>
.top {
  padding: 50px 10px;
}
.title {
  height: 30px;
  line-height: 30px;
  font-size: 14px;
}
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
</style>
