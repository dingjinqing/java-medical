<template>
  <div class="version-log">
    <div class="filter-menu">
      <el-select
        v-model="value"
        :placeholder="$t('programVersion.placeChoise')"
        size="small"
        @change="handleSelect()"
      >
        <el-option
          v-for="item in selectIdTemId"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-input
        v-model="text"
        placeholder="请输入appid"
        size="small"
        class="search-input ml-6"
      />
      <el-button
        size="small"
        class="ml-6"
        type="primary"
        @click="handleQuery()"
      >{{$t('programVersion.search')}}</el-button>
    </div>

    <el-table
      class="version-log mt-10"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="createTime"
        :label="$t('programVersion.date')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="appId"
        label="appid"
        align="center"
        width="200"
      >
      </el-table-column>
      <el-table-column
        prop="nickName"
        align="center"
        :label="$t('programVersion.nickName')"
      >
      </el-table-column>
      <el-table-column
        prop="memo"
        align="center"
        min-width="400"
        :label="$t('programVersion.logContent')"
      >
        <template slot-scope="scope">
          <p v-html="scope.row.memo"></p>

        </template>
      </el-table-column>
      <el-table-column
        prop="userVersion"
        align="center"
        :label="$t('programVersion.smallProgramVersion')"
      >
      </el-table-column>
      <el-table-column
        prop="operation"
        align="center"
        :label="$t('programVersion.operation')"
      >
        <template slot-scope="scope">
          <span
            style="cursor:pointer"
            @click="handleDetail(scope.row)"
          >{{$t('programVersion.seeDetails')}}</span>

        </template>
      </el-table-column>
    </el-table>

    <div class="footer">
      <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.totle}}</div>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="20"
        layout="prev, pager, next, jumper"
        :total="totle"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { operateLogRequest, SpinnerListRequest } from '@/api/system/programManage'
export default {
  name: 'versionLog',
  data () {
    return {
      page: 1,
      value: null,
      selectIdTemId: [],
      currentPage: 1,
      pageCount: 1,
      totle: 0,
      text: null,
      tableData: [
      ]
    }
  },
  watch: {
    $route (to) {
      console.log(to.params.userVersion)
      console.log(this.selectIdTemId)
      this.selectIdTemId.map((item, index) => {
        console.log(item.value)
        if (item.label === to.params.userVersion) {
          this.value = item.label
        }
      })
      console.log(this.value)
      this.handleQueryTableData(to.params.id)
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    async defaultData () {
      if (this.$route.params.appId === -1) {
        this.text = this.$route.params.appId
      }
      let spinnerList = await SpinnerListRequest()
      console.log(spinnerList)
      if (spinnerList.error === 0) {
        let arr = []
        let defaultObj = {}
        defaultObj.value = ''
        defaultObj.label = this.$t('programVersion.placeChoise')
        arr.push(defaultObj)
        spinnerList.content.map((item, index) => {
          let obj = {}
          obj.value = item.templateId
          obj.label = item.userVersion
          arr.push(obj)
        })

        console.log(arr)
        this.selectIdTemId = arr
        this.handleQueryTableData()
      }
    },
    handleQueryTableData (params) {
      console.log(params)
      let templateId = ''
      if (params) {
        templateId = params
      } else {
        templateId = this.value
      }
      let obj = {
        'templateId': templateId,
        'appId': this.text,
        'currentPage': this.currentPage,
        'pageRows': 0
      }

      operateLogRequest(obj).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageCount = res.content.page.pageCount
          this.totle = res.content.page.totalRows
        }
        console.log(res)
      })
    },
    // 当前页变化
    handleCurrentChange (page) {
      this.handleQueryTableData()
    },
    // 点击搜索
    handleQuery () {
      this.handleQueryTableData()
    },
    // 点击查看详情
    handleDetail (row) {
      console.log(row)
      this.$router.push({
        name: 'programManage',
        params: {
          page: 'authMsg',
          appId: row.appId
        }
      })
      console.log(row)
    },
    // 下拉框事件
    handleSelect () {
      this.handleQueryTableData()
    }
  }
}
</script>

<style scoped lang="scss">
.version-log {
  .search-input {
    width: 200px;
  }
  .filter-menu {
    display: flex;
    padding: 10px;
    background: #fff;
  }
  .footer {
    background-color: #fff;
    height: 50px;
    line-height: 50px;
    color: #333;
    font-size: 14px;
    display: flex;
    justify-content: flex-end;
    padding-right: 10px;
    /deep/ .el-pagination {
      display: flex;
      align-items: center;
      .el-pager {
        display: flex;
        align-items: center;
      }
    }
  }
}
</style>
