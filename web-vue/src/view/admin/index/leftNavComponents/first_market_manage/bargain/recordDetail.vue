<!--
* 砍价 - 帮忙砍价用户列表
*
* @author: 王兵兵
-->
<template>
  <div>
    <wrapper>
      <section class="newuserDetailContent">
        <div class="username">
          <span>{{$t('marketCommon.username')+'：'}}</span>
          <el-input
            v-model="requestParams.username"
            class="inputWidth"
            size="small"
            :placeholder="$t('marketCommon.usernamePlaceholder')"
            clearable
          ></el-input>
        </div>
        <div>
          <span>{{$t('marketCommon.mobile')+'：'}}</span>
          <el-input
            v-model="requestParams.mobile"
            class="inputWidth"
            size="small"
            :placeholder="$t('marketCommon.mobilePlaceholder')"
            clearable
          ></el-input>
        </div>
        <el-button
          @click="initDataList"
          class="btn"
          type="primary"
          size="small"
        >{{$t('marketCommon.filter')}}</el-button>
        <el-button
          @click="exportDataList"
          class="btn button"
          type="primary"
          size="small"
        >{{$t('marketCommon.export')}}</el-button>
      </section>
    </wrapper>

    <wrapper>
      <div class="table_list">
        <el-table
          v-loading="loading"
          :data="tableData"
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="userId"
            :label="$t('marketCommon.userId')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="username"
            :label="$t('marketCommon.username')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="mobile"
            :label="$t('marketCommon.mobile')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="createTime"
            :label="$t('bargainList.cutTime')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="bargainMoney"
            :label="$t('bargainList.cutAmount')"
            align="center"
          >
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </wrapper>
  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination'
import { getBargainUserPageList, exportBargainUserList } from '@/api/admin/marketManage/bargain.js'

export default {
  components: { wrapper, pagination },
  mounted () {
    if (this.$route.query.recordId > 0) {
      this.recordId = this.$route.query.recordId
      this.initDataList()
    }
  },
  data () {
    return {
      loading: false,
      recordId: null,
      requestParams: {},
      pageParams: {},
      tableData: [],

      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.recordId = this.recordId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getBargainUserPageList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    exportDataList () {
      this.requestParams.recordId = this.recordId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      exportBargainUserList(this.requestParams).then((res) => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '帮忙砍价用户列表.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    },
    // 表格数据处理
    handleData (data) {
      this.tableData = data
    }
  }
}

</script>
<style lang="scss" scoped>
* {
  font-size: 14px;
}
.newuserDetailContent {
  display: flex;
  div {
    margin-right: 20px;
    .inputWidth {
      width: 150px;
    }
  }
  .username {
    margin-left: 30px;
  }
  .btn {
    margin-left: 5px;
  }
  .button {
    margin-left: 20px;
  }
}

/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
}
</style>
