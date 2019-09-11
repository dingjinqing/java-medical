<template>
  <div class="content">

    <div class="main">
      <el-form :inline="true">
        <el-form-item
          size="small"
          :label="$t('promoteList.username')"
        >
          <el-input
            v-model="username"
            :placeholder="$t('promoteList.usernamePlaceholder')"
          />
        </el-form-item>
        <el-form-item
          size="small"
          :label="$t('promoteList.mobile')"
        >
          <el-input
            v-model="mobile"
            :placeholder="$t('promoteList.mobilePlaceholder')"
          />
        </el-form-item>
        <el-form-item
          size="small"
          :label="$t('promoteList.actId')"
        >
          <el-input
            v-model="id"
            :placeholder="$t('promoteList.actIdPlaceHolder')"
          ></el-input>
        </el-form-item>
        <br>
        <el-form-item
          size="small"
          :label="$t('promoteList.orderSn')"
        >
          <el-input
            v-model="orderSn"
            :placeholder="$t('promoteList.orderSnPlaceholder')"
          ></el-input>
        </el-form-item>
        <el-form-item
          size="small"
          :label="$t('promoteList.isReceive')"
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
              value="1"
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
          :label="$t('promoteList.recUsername')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          :label="$t('promoteList.recMobile')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="id"
          :label="$t('promoteList.actId')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="promoteStatus"
          :label="$t('promoteList.isReceive')"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="recTime"
          :label="$t('promoteList.recTime')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          :label="$t('promoteList.orderSn')"
          align="center"
        >
          <template slot-scope="scope">
            <el-button
              @click="getOrder()"
              type="text"
            > {{scope.row.orderSn}} </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="loadData"
      />

    </div>
  </div>
</template>
<script>
import { receiveDetails } from '@/api/admin/marketManage/friendHelp.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data: function () {
    return {
      promoteId: '',
      username: '',
      mobile: '',
      id: '',
      promoteStatus: '-1',
      orderSn: '',
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
      this.pageParams.orderSn = this.orderSn
      this.pageParams.promoteStatus = this.promoteStatus
      console.log('pageParams:', this.pageParams)
      receiveDetails(this.pageParams).then(res => {
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
