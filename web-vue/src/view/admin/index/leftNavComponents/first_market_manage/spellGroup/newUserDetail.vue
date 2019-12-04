<!--
* 获取新用户明细页面
*
* @author: 赵鑫
-->
<template>
  <div class="content">
    <div class="main">
      <section class="newuserDetailContent">
        <div>
          <span>{{$t('groupBuy.mobileNumber')}}</span>
          <el-input
            class="inputWidth"
            size="small"
            :placeholder="$t('groupBuy.mobileNumber')"
          ></el-input>
        </div>
        <div>
          <span>{{$t('groupBuy.userNickname')}}</span>
          <el-input
            class="inputWidth"
            size="small"
            :placeholder="$t('groupBuy.userNickname')"
          ></el-input>
        </div>
        <div>
          <span>{{$t('groupBuy.invitePeople')}}</span>
          <el-input
            class="inputWidth"
            size="small"
            :placeholder="$t('groupBuy.invitePeople')"
          ></el-input>
        </div>
        <el-button
          class="btn"
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('groupBuy.searchDataText')}}</el-button>
      </section>
    </div>

    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="userName"
          :label="$t('groupBuy.activityName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="userId"
          :label="$t('groupBuy.newUserId')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="userName"
          :label="$t('groupBuy.newUserNickname')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          :label="$t('groupBuy.newUserMobile')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          :label="$t('groupBuy.registrationTime')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="inviteUserName"
          :label="$t('groupBuy.invitePeople')"
          align="center"
        >
        </el-table-column>
      </el-table>

      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>
  </div>
</template>

<script>
import { newUserList } from '@/api/admin/marketManage/spellGroup.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')

  },
  data () {
    return {
      requestParams: {
        activityId: this.$route.query.id,
        mobile: null,
        userName: null,
        inviteUserName: null,
        currentPage: null,
        pageRows: null
      },
      tableData: [],
      pageParams: {}
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.initDataList()
  },
  methods: {
    initDataList () {
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      newUserList(this.requestParams).then(res => {
        this.pageParams = res.content.page
        this.tableData = res.content.dataList
      })
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
  .main {
    position: relative;
    background-color: #fff;
    padding: 15px;
    .wrapper {
      .el-button {
        margin-left: 5px;
      }
    }
  }
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
  margin-top: 10px;
  background-color: #fff;
  padding: 15px;
}
.newuserDetailContent {
  display: flex;
  font-size: 14px;
  div {
    margin-right: 10px;
    span {
      margin-right: 10px;
    }
    .inputWidth {
      width: 150px;
    }
  }
  .btn {
    margin-left: 5px;
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
</style>
