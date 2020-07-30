<template>
<!--  短信信息列表-->
  <div class="payContent">
    <div class="payContent_main">
      <div class="search_list">
        <el-form
          size="small"
          :inline="true"
          class="demo-form-inline"
        >
          <el-form-item :label="$t('actionRecord.optionTime')+'：'">
            <div class="block">
              <el-date-picker
                v-model="startCreateTime"
                type="date"
                style="width:170px;"
                value-format="yyyy-MM-dd 00:00:00"
                placeholder="开始时间"
              >
              </el-date-picker>
              <span>至</span>
              <el-date-picker
                v-model="endCreateTime"
                type="date"
                style="width:170px;"
                value-format="yyyy-MM-dd 00:00:00"
                placeholder="结束时间"
              >
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="search"
            >{{$t('actionRecord.search')}}</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="table_box">
        <el-table
          :data="tableData"
          border
          stripe
          style="width: 100%;margin-bottom: 10px;"
          :header-cell-style="{background:'#eef1f6',color:'#606266'}"
        >
          <el-table-column
            prop="userId"
            label="用户id"
            align="center"
            min-width="20%"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号码"
            align="center"
            :formatter="bindFormatter"
            min-width="10%"
          >
          </el-table-column>
          <el-table-column
            prop="ext"
            label="短信类型"
            align="center"
            min-width="10%"
            :formatter="actionTypeFormatter"
          >
          </el-table-column>
          <el-table-column
            prop="responseMsg"
            label="发送内容"
            align="center"
            min-width="55%"
          >
          </el-table-column>
          <el-table-column
            prop="responseTime"
            label="发送时间"
            align="center"
            min-width="20%"
          >

          </el-table-column>
          <el-table-column
            prop="responseMsgCode"
            label="发送状态"
            align="center"
            min-width="20%"
          >

          </el-table-column>

        </el-table>
        <div class="footer">
          <span>{{$t('authRoleList.everyPage')}}{{this.pageRows}}{{$t('authRoleList.record')}}，{{$t('authRoleList.currentPage')}}：{{this.currentPage}}，{{$t('authRoleList.pageCount')}}：{{this.pageCount}}，{{$t('authRoleList.totalRows')}}：{{this.totalRows}}</span>
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
    </div>
  </div>
</template>

<script>
  import { getSmsListPage } from '@/api/admin/basicConfiguration/shopConfig.js'
  export default {
    components: {
    },
    data () {
      return {
        startCreateTime: null,
        endCreateTime: null,
        totalRows: null,
        pageRows: 20,
        currentPage: 1,
        pagination_b: true,
        pageCount: null,
        firstPage: null,
        lastPage: null,
        nextPage: null,
        tableData: null
      }
    },
    mounted () {
      // 初始化数据
      this.langDefault()
      this.defaluteData()
    },
    methods: {
      defaluteData () {
        this.search()
      },
      search () {
        console.log(this.date)
        let params = {
          'currentPage': this.currentPage,
          'pageRows': this.pageRows,
          'startCreateTime': this.startCreateTime,
          'endCreateTime': this.endCreateTime
        }
        getSmsListPage(params).then((res) => {
          console.log('res-----------------------------------')
          console.log(res)
          if (res.error === 0) {
            this.tableData = res.content.dataList
            this.currentPage = res.content.page.currentPage
            this.pageRows = res.content.page.pageRows
            this.firstPage = res.content.page.firstPage
            this.lastPage = res.content.page.lastPage
            this.nextPage = res.content.page.nextPage
            this.pageCount = res.content.page.pageCount
            this.totalRows = res.content.page.totalRows
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // currnentPage 改变时会触发
      handleCurrentChange () {
        this.search()
      },
      bindFormatter (row, column) {
        switch (row.accountType) {
          case 1:
            row.accountTypeTran = this.$t('actionRecord.mAccount')
            break
          case 0:
            row.accountTypeTran = this.$t('actionRecord.mAccount2')
            break
        }
        return row.accountTypeTran
      },
      actionTypeFormatter (row, column) {
        switch (row.actionType) {
          case 1:
            row.actionTypeTran = this.$t('actionRecord.decoration')
            break
          case 2:
            row.actionTypeTran = this.$t('actionRecord.commodity')
            break
          case 3:
            row.actionTypeTran = this.$t('actionRecord.order')
            break
          case 4:
            row.actionTypeTran = this.$t('actionRecord.member')
            break
          case 5:
            row.actionTypeTran = this.$t('actionRecord.marketing')
            break
        }
        return row.actionTypeTran
      }
    }
  }

</script>
<style lang="scss" scoped>
  .payContent {
    padding: 10px;
    min-width: 100%;
    font-size: 14px;
    height: 100%;
  }
  // .payContent_main {
  //   background-color: #fff;
  //   padding: 10px 20px;
  // }
  .search_list {
    padding: 15px;
    background: #fff;
  }
  .table_box {
    background: #fff;
    margin-top: 10px;
    padding: 15px;
  }
  .footer {
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }
  .footer > span {
    font-size: 14px;
  }
  .block /deep/ .el-date-editor.el-input,
  .el-date-editor.el-input__inner {
    width: 150px;
  }
</style>
