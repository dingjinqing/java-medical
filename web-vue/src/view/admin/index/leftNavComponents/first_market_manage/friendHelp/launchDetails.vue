<template>
  <div class="content">

    <div class="main">
      <el-form :inline="true">
        <el-form-item
          size="small"
          :label="$t('promoteList.username')"
        >
          <el-input
            :placeholder="$t('promoteList.usernamePlaceholder')"
            v-model="username"
          />
        </el-form-item>
        <el-form-item
          size="small"
          :label="$t('promoteList.mobile')"
        >
          <el-input
            :placeholder="$t('promoteList.mobilePlaceholder')"
            v-model="mobile"
          />
        </el-form-item>
        <el-form-item
          size="small"
          :label="$t('promoteList.actId')"
        >
          <el-input
            :placeholder="$t('promoteList.actIdPlaceholder')"
            v-model="id"
          ></el-input>
        </el-form-item>
        <br>

        <el-form-item
          size="small"
          :label="$t('promoteList.successfulPromotion')"
        >
          <el-select v-model="promoteStatus">
            <el-option
              :label="$t('promoteList.all')"
              value="-1"
            ></el-option>
            <el-option
              :label="$t('promoteList.yes')"
              value="2"
            ></el-option>
            <el-option
              :label="$t('promoteList.no')"
              value="0"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-button
          size="small"
          type="primary"
          @click="onSubmit"
        >{{$t('promoteList.filter')}}</el-button>
        <el-button size="small">{{$t('promoteList.export')}}</el-button>
      </el-form>
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
          prop="username"
          :label="$t('promoteList.launchUsername')"
          align="center"
        >
          <template slot-scope="scope">
            <el-button
              @click="getUser()"
              type="text"
            > {{scope.row.username}} </el-button>
          </template>
        </el-table-column>

        <el-table-column
          prop="mobile"
          :label="$t('promoteList.launchMobile')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="id"
          :label="$t('promoteList.actId')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="joinNum"
          :label="$t('promoteList.joinNum')"
          align="center"
        >
          <template slot-scope="scope">
            <el-button
              @click="getJoinDetails(scope.row.id)"
              type="text"
            > {{scope.row.joinNum}} </el-button>
          </template></el-table-column>
        <el-table-column
          prop="promoteTimes"
          :label="$t('promoteList.promoteTimes')"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="promoteValue"
          :label="$t('promoteList.promoteValue')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="promoteStatus"
          :label="$t('promoteList.successfulPromotion')"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="loadData"
      />

    </div>
  </div>
</template>
<script>
import { launchDetails } from '@/api/admin/marketManage/friendHelp.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data: function () {
    return {
      username: '',
      mobile: '',
      id: '',
      promoteStatus: '-1',
      tableData: [],
      pageParams: {
      }
    }
  },
  methods: {
    loadData () {
      this.pageParams.promoteId = this.promoteId
      this.pageParams.id = this.id
      this.pageParams.mobile = this.mobile
      this.pageParams.username = this.username
      this.pageParams.promoteStatus = this.promoteStatus
      console.log('pageParams:', this.pageParams)
      launchDetails(this.pageParams).then(res => {
        console.log('pageInfo:', res)
        this.handData(res.content.dataList)
        this.pageParams = res.content.page
      })
    },

    onSubmit () {
      this.pageParams.currentPage = 1
      this.loadData()
    },
    handData (data) {
      data.map((item, index) => {
        if (item.promoteStatus === 2) {
          item.promoteStatus = this.$t('promoteList.yes')
        } else {
          item.promoteStatus = this.$t('promoteList.no')
        }
      })
      this.tableData = data
    },
    // 用户
    getUser () {
      this.$router.push({
        path: `/admin/home/main/membershipInformation`
      })
    },
    // 参与人
    getJoinDetails (launchId) {
      this.$router.push({
        path: `/admin/home/main/friendHelp/participateDetails/${this.promoteId}/${launchId}`
      })
    }
  },
  mounted () {
    this.promoteId = this.$route.params.id
    this.loadData()
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
    padding: 10px 20px 10px 20px;
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
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.paginationfooter {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
</style>
