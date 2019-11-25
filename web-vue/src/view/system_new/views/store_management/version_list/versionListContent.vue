<template>
  <div class="experience-version">
    <div class="select-menu top">
      <div class="title">版本名称：</div>
      <el-input
        v-model="versionName"
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
    >
      <el-table-column
        prop="id"
        :label="$t('versionList.table.versionID')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="versionName"
        :label="$t('versionList.table.versionName')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="level"
        align="center"
        :label="$t('versionList.table.versionLevel')"
      >
      </el-table-column>
      <el-table-column
        prop="created"
        align="center"
        :label="$t('versionList.table.createTime')"
      >
      </el-table-column>
      <el-table-column
        prop="updateTime"
        align="center"
        :label="$t('versionList.table.updateTime')"
      >
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('versionList.table.operate')"
      >
        <template slot-scope="scope">
          <el-link
            type="primary"
            :underline="false"
            @click="show(scope.row)"
          >查看</el-link>
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
import { versionListRequest } from '@/api/system/versionList.js'

export default {
  name: 'versionListContent',
  data () {
    return {
      formTable: null,
      totalRows: null,
      pageRows: '',
      currentPage: 1,
      pageCount: null,
      pagination_b: true,
      value: '',
      text: '',
      versionName: null
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
      let obj1 = {
        'currentPage': this.currentPage,
        'pageRows': this.pageRows,
        'versionName': this.versionName
      }
      versionListRequest(obj1).then((res) => {
        if (res.error === 0) {
          let formList = res.content.dataList
          let pageObj = res.content.page
          this.totalRows = pageObj.totalRows
          this.currentPage = pageObj.currentPage
          this.firstPage = pageObj.firstPage
          this.lastPage = pageObj.lastPage
          this.nextPage = pageObj.nextPage
          this.pageCount = pageObj.pageCount
          this.pageRows = pageObj.pageRows
          this.formTable = formList
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
    },
    show (data) {
      console.log('查看')
      let params = {
        'level': data.level,
        'showFlagTwo': true,
        'isEdit': false
      }
      this.$emit('showDtail', params)
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
