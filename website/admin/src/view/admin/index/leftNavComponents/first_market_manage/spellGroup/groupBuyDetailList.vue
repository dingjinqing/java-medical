<!--
*** 多人拼团-参团明细
-->
<template>
  <div class="content">
    <div class="main">
      <section class="filter-condition">
        <div>
          <span>{{$t('groupBuy.userMobileNumber')+'：'}}</span>
          <el-input
            size="small"
            v-model="mobile"
            :placeholder="$t('groupBuy.mobileNumber')"
            maxlength="11"
            clearable
            class="inputWidth"
          ></el-input>
        </div>
        <div>
          <span>{{$t('groupBuy.userNickname') + '：'}}</span>
          <el-input
            size="small"
            v-model="nickname"
            :placeholder="$t('groupBuy.nickname')"
            clearable
            class="inputWidth"
          ></el-input>
        </div>
        <div>
          <span>{{$t('groupBuy.grouponState') + '：'}}</span>
          <el-select
            size="small"
            v-model="status"
            class="inputWidth"
          >
            <el-option
              v-for="item in stateOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>

        <div>
          <el-button
            size="small"
            type="primary"
            @click="searchData"
          >{{$t('groupBuy.searchDataText')}}
          </el-button>
        </div>
      </section>

    </div>

    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="commanderName"
          :label="$t('groupBuy.commanderName')"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="commanderMobile"
          :label="$t('groupBuy.commanderMobile')"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="username"
          :label="$t('groupBuy.username')"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="mobile"
          :label="$t('groupBuy.mobile')"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="statusText"
          :label="$t('groupBuy.grouponState')"
          align="center"
        ></el-table-column>

        <el-table-column
          :label="$t('groupBuy.isDefault')"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.status === 3 ? '是':'否'}}
          </template>
        </el-table-column>

        <el-table-column
          prop="orderSn"
          :label="$t('groupBuy.orderSn')"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="startTime"
          :label="$t('groupBuy.startTime')"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="endTime"
          :label="$t('groupBuy.endTime')"
          align="center"
        ></el-table-column>
      </el-table>

      <pagination
        :page-params.sync="pageParams"
        @pagination="initialize"
      />
    </div>

  </div>
</template>

<script>
import { detailGroupBuy } from '@/api/admin/marketManage/spellGroup.js'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: {
    pagination
  },
  data () {
    return {
      mobile: null,
      nickname: null,
      status: null,
      tableData: [],
      pageParams: {},
      stateOptions: {}
    }
  },
  watch: {
    lang () {
      this.stateOptions = this.$t('groupBuy.stateOptions')
    }
  },
  mounted () {
    // 初始化
    this.langDefault()
    this.initialize()
  },
  methods: {
    // 初始化
    initialize () {
      console.log('初始化', this.$route.query.id)
      if (!this.$route.query.id) {
        this.$router.push({ path: `/admin/home/main/spellGroup` })
      }
      this.searchData()
    },
    // 成团状态文字转化，0成团中，1已成团，2未成团，3已成团，-1未付款
    getActStatusString (currentState) {
      switch (currentState) {
        case 0:
          return '成团中'
        case 1:
          return '已成团'
        case 2:
          return '未成团'
        case 3:
          return '已成团'
        case -1:
          return '未付款'
      }
    },
    // 查询
    searchData () {
      this.tableData.loading = true
      let obj = {
        nickName: this.nickname,
        mobile: this.mobile,
        status: this.status,
        activityId: this.$route.query.id,
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows
      }
      detailGroupBuy(obj).then(res => {
        console.log(res, 'get-res')
        this.pageParams = res.content.page
        this.tableData = res.content.dataList
        this.tableData.loading = true
        this.tableData.map(item => {
          item.statusText = this.getActStatusString(item.status)
        })
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
    .filter-condition {
      display: flex;
      margin-left: 15px;
      padding: 10px 0;
      div {
        margin-right: 20px;
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
.inputWidth {
  width: 170px;
}
</style>
