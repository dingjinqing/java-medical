<template>
  <div class="container">
    <div class="main">
      <div class="top">
        <ul class="query-ul">
          <li>
            <span>{{$t('openScreenDetail.receiveTime')}}：</span>
            <!-- <el-date-picker
              v-model="value1"
              type="daterange"
              size="small"
              :range-separator="$t('openScreenDetail.to')"
              :start-placeholder="$t('openScreenDetail.startDate')"
              :end-placeholder="$t('openScreenDetail.endDate')"
            >
            </el-date-picker> -->
            <el-date-picker
              v-model="queryParams.startTime"
              type="date"
              size="small"
              style="width:170px;"
              :placeholder="$t('openScreenDetail.startDate')"
              value-format="yyyy-MM-dd hh:mm:ss"
            ></el-date-picker>
            {{$t('openScreenDetail.to')}}
            <el-date-picker
              v-model="queryParams.endTime"
              type="date"
              size="small"
              style="width:170px;"
              :placeholder="$t('openScreenDetail.endDate')"
              value-format="yyyy-MM-dd hh:mm:ss"
            ></el-date-picker>
          </li>
          <li>
            <span>{{$t('openScreenDetail.phoneNum')}}：</span>
            <el-input
              size="small"
              style="width: 170px;"
              v-model="queryParams.mobile"
              :placeholder="$t('openScreenDetail.piPhone')"
            ></el-input>
          </li>
          <li>
            <span>{{$t('openScreenDetail.nickname')}}：</span>
            <el-input
              size="small"
              style="width: 170px;"
              v-model="queryParams.username"
              :placeholder="$t('openScreenDetail.piNickname')"
            ></el-input>
          </li>
          <li>
            <el-button
              size="small"
              type="primary"
              @click="initDataList"
            >{{$t('openScreenDetail.inquire')}}</el-button>
          </li>
        </ul>
      </div>
      <div class="center">
        <el-table
          :data="tableData"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'border':'none'
          }"
        >
          <el-table-column
            label="用户ID"
            prop="userId"
          ></el-table-column>
          <el-table-column
            :label="$t('openScreenDetail.nickname')"
            prop="username"
          ></el-table-column>
          <el-table-column
            :label="$t('openScreenDetail.phoneNum')"
            prop="mobile"
          ></el-table-column>
          <el-table-column
            :label="$t('openScreenDetail.phoneNum')"
            prop="comment"
          ></el-table-column>
          <el-table-column
            :label="$t('openScreenDetail.receiveTime')"
            prop="receiveTime"
          ></el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        >
        </pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { detailOpenScreen } from '@/api/admin/marketManage/openScreen.js'
import('@/util/date')
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      // value1: [], // 开始时间和结束时间
      queryParams: {
        startTime: '',
        endTime: '',
        mobile: '',
        username: ''
      },
      pageParams: {},
      tableData: []
    }
  },
  // watch: {
  //   value1: function (newVal) {
  //     if (newVal && newVal.length === 2) {
  //       this.$set(this.queryParams, 'startTime', newVal[0].format('yyyy-MM-dd hh:mm:ss'))
  //       this.$set(this.queryParams, 'endTime', newVal[1].format('yyyy-MM-dd hh:mm:ss'))
  //     }
  //   }
  // },
  mounted () {
    if (this.$route.query.id) {
      this.id = this.$route.query.id
      this.initDataList()
    } else {
      this.$alert(this.$t('openScreenDetail.sorry'), this.$t('openScreenDetail.prompt'), {
        confirmButtonText: this.$t('openScreenDetail.determine'),
        callback: action => {
          this.$router.go(-1)
        }
      })
    }
    this.langDefault()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, {
        activityId: this.id
      }, this.queryParams, this.pageParams)
      detailOpenScreen(params).then(res => {
        if (res.error === 0) {
          this.tableData = [...res.content.dataList]
          this.pageParams = Object.assign({}, res.content.page)
        } else {
          this.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    .top {
      padding: 15px;
      background: #fff;
      overflow: hidden;
      &::after {
        clear: both;
      }
      .query-ul {
        display: flex;
        float: left;
        margin-right: 10px;
        li:not(:first-child) {
          margin-left: 10px;
        }
        span {
          display: inline-block;
          width: 80px;
        }
      }
    }
    .center {
      margin-top: 10px;
      padding: 15px;
      background: #fff;
    }
  }
}
</style>
