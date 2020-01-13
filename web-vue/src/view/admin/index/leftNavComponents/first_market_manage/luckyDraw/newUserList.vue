<template>
  <div>
    <wrapper>
      <section class="newuserDetailContent">
        <div>
          <span>{{$t('luckyDraw.mobile')}}：</span>
          <el-input
            class="inputWidth"
            size="small"
            v-model="requestParams.mobile"
            :placeholder="$t('luckyDraw.mobile')"
          ></el-input>
        </div>
        <div>
          <span>{{$t('luckyDraw.userNickname')}}：</span>
          <el-input
            class="inputWidth"
            size="small"
            v-model="requestParams.userName"
            :placeholder="$t('luckyDraw.userNickname')"
          ></el-input>
        </div>
        <div>
          <span>{{$t('luckyDraw.invitePeople')}}：</span>
          <el-input
            class="inputWidth"
            size="small"
            v-model="requestParams.inviteUserName"
            :placeholder="$t('luckyDraw.invitePeople')"
          ></el-input>
        </div>
        <el-button
          class="btn"
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('luckyDraw.query')}}</el-button>
      </section>
    </wrapper>

    <wrapper>
      <div class="table_list">
        <el-table
          :data="tableData"
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            :label="$t('luckyDraw.activityName')"
            align="center"
          >
            {{requestParams.activityName}}
          </el-table-column>

          <el-table-column
            prop="userId"
            :label="$t('luckyDraw.newUserId')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="userName"
            :label="$t('luckyDraw.newUserNickname')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="mobile"
            :label="$t('luckyDraw.newUserMobile')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="createTime"
            :label="$t('luckyDraw.registrationTime')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="inviteUserName"
            :label="$t('luckyDraw.invitePeople')"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </wrapper>

  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import { getLotteryUserList } from '@/api/admin/marketManage/luckyDraw.js'
export default {
  components: {
    wrapper,
    pagination: () => import('@/components/admin/pagination/pagination')

  },
  data () {
    return {
      requestParams: {
        activityId: this.$route.query.id, // 活动id
        activityName: this.$route.query.activityName, // 活动名称
        mobile: null, // 电话
        userName: null, // 昵称
        inviteUserName: null, // 邀请人
        currentPage: null,
        pageRows: null
      },
      tableData: [],
      pageParams: {}
    }
  },
  mounted () {
    console.log(this.$route)
    // 初始化语言
    this.langDefault()
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.requestParams, this.pageParams)
      getLotteryUserList(params).then(res => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.newuserDetailContent {
  display: flex;
  div {
    margin-right: 10px;
    span {
      margin-right: 10px;
      font-size: 14px;
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
.table_list {
  position: relative;
}
</style>
