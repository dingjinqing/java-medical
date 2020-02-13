<!--
* 砍价 - 获取新用户明细页面
*
* @author: 赵鑫
-->
<template>
  <div>
    <wrapper>
      <section class="newuserDetailContent">
        <div>
          <span>{{$t('marketCommon.mobile')+"："}}</span>
          <el-input
            v-model="requestParams.mobile"
            class="inputWidth"
            size="small"
            :placeholder="$t('marketCommon.usernamePlaceholder')"
          ></el-input>
        </div>
        <div>
          <span>{{$t('marketCommon.username')+"："}}</span>
          <el-input
            v-model="requestParams.userName"
            class="inputWidth"
            size="small"
            :placeholder="$t('marketCommon.usernamePlaceholder')"
          ></el-input>
        </div>
        <div>
          <span>{{$t('marketCommon.inviter')+"："}}</span>
          <el-input
            v-model="requestParams.inviteUserName"
            class="inputWidth"
            size="small"
            :placeholder="$t('marketCommon.inviterPlaceholder')"
          ></el-input>
        </div>
        <el-button
          @click="initDataList"
          class="btn"
          type="primary"
          size="small"
        >{{$t('marketCommon.filter')}}</el-button>
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
            :label="$t('marketCommon.newUserId')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="userName"
            :label="$t('marketCommon.newUserNickname')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="mobile"
            :label="$t('marketCommon.newUserMobile')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="createTime"
            :label="$t('marketCommon.registrationTime')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="inviteUserName"
            :label="$t('marketCommon.inviter')"
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
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination'
import { getBargainSourceUserList } from '@/api/admin/marketManage/bargain.js'

export default {
  components: { wrapper, pagination },
  mounted () {
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.initDataList()
    }
  },
  data () {
    return {
      loading: false,
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
      this.requestParams.activityId = this.actId
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getBargainSourceUserList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
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
    span {
      margin-right: 5px;
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
  color: #000;
  padding: 8px 10px;
}
</style>
