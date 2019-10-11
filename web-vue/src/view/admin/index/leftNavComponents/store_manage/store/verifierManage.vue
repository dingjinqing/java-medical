<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="order-container">
        <div class="order-info">
          <ul class="list-verifier-filters">
            <li>
              <label class="list-verifier-filters-label">{{$t('verifierManage.phone')}}：</label>
              <el-input
                size="small"
                class="input-narrow"
                :placeholder="$t('verifierManage.phoneTips')"
                v-model="queryParams.mobile"
              ></el-input>
            </li>
            <li>
              <label class="list-verifier-filters-label">{{$t('verifierManage.wechatNickname')}}：</label>
              <el-input
                size="small"
                class="input-narrow"
                :placeholder="$t('verifierManage.wechatNicknameTips')"
                v-model="queryParams.username"
              ></el-input>
            </li>
            <li>
              <el-button
                size="small"
                type="primary"
                @click="searchHandle"
              >{{$t('verifierManage.filter')}}</el-button>
              <el-button size="small">{{$t('verifierManage.export')}}</el-button>
            </li>
            <div class="list-verifier-filters-right">
              <addVerifierDialog
                :storeId='queryParams.storeId'
                @select-change="selectVerifierChangeHandle"
              ></addVerifierDialog>
            </div>
          </ul>
        </div>
        <div class="order-content">
          <el-table
            ref="goodsTable"
            :data="goodsData"
            class="tableClass"
            max-height="500"
            border
            :header-cell-style="{
              'background-color':'#f5f5f5',
              'border':'none'
            }"
          >
            <el-table-column
              :label="$t('verifierManage.userID')"
              prop="userId"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('verifierManage.nickname')"
              prop="username"
              align="center"
            >
              <template slot-scope="{row}">
                <router-link
                  class="iconSpan"
                  :to="{path: '/admin/home/main/membershipInformation', query: {userId: row.userId}}"
                >{{row.username}}</router-link>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('verifierManage.phoneNumber')"
              prop="mobile"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('verifierManage.checkOrderQuantity')"
              prop="verifyOrders"
              align="center"
            ></el-table-column>
            <el-table-column
              :label="$t('verifierManage.operate')"
              prop="goodsName"
              width=""
              align="center"
            >
              <template slot-scope="{row}">
                <el-tooltip
                  :content="$t('verifierManage.checkOrder')"
                  placement="top"
                >
                  <span class="iconSpan">{{$t('verifierManage.checkOrder')}}</span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('verifierManage.delete')"
                  placement="top"
                >
                  <span
                    class="iconSpan"
                    @click="deleteHandle(row)"
                  >{{$t('verifierManage.delete')}}</span>
                </el-tooltip>
              </template>
            </el-table-column>
            <div slot="empty">
              <div class="noData">
                <img :src="noImg">
              </div>
              <span>{{$t('verifierManage.NoDataYet')}}</span>
            </div>
          </el-table>
          <div class="table-page">
            <pagination
              :page-params.sync="pageParams"
              @pagination="initDataList"
            ></pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getVerifierList, addVerifier } from '@/api/admin/storeManage/verifierManage'
import pagination from '@/components/admin/pagination/pagination'
import addVerifierDialog from '@/components/admin/addVerifierDialog'
export default {
  components: { pagination, addVerifierDialog },
  data () {
    return {
      storeName: '',
      queryParams: {
        storeId: '',
        mobile: null,
        username: null
      },
      selected: [],
      noImg: this.$imageHost + '/image/admin/no_data.png',
      pageParams: {},
      tableData: []
    }
  },
  computed: {
    goodsData: function () {
      return this.tableData
    }
  },
  created () {
    this.queryParams.storeId = this.$route.query.id
    this.storeName = this.$route.query.name
  },
  mounted () {
    console.log(this.$route, 111)
    this.initDataList()
  },
  methods: {
    searchHandle () {
      this.initDataList()
    },
    selectionChangeHandle () {
      console.log(arguments)
    },
    deleteHandle (row) {
      console.log(row)
    },
    selectVerifierChangeHandle (datas) {
      console.log(datas)
      const userIds = datas.map((item, i) => item.userId)
      this.addVerifierHandle(userIds)
    },
    addVerifierHandle (userIds) {
      let params = {
        storeId: this.queryParams.storeId,
        userIds: userIds
      }
      addVerifier(params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.$message.success(this.$t('verifierManage.addVerifierSuccess'))
          this.initDataList()
        } else {
          this.$message.success(this.$t('verifierManage.addVerifierFail'))
        }
      }).catch(err => {
        this.$message.success(this.$t('verifierManage.addVerifierFail'))
        throw err
      })
    },
    initDataList () {
      let params = Object.assign(this.queryParams, this.pageParams)
      getVerifierList(params).then(res => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    }
  }
}
</script>

<style scoped>
.order-container {
  padding: 10px;
}
.order-info {
  background: #fff;
  padding: 15px 18px;
}
.order-content {
  margin-top: 10px;
}
.list-store-name {
  margin-left: 10px;
  margin-bottom: 4px;
  padding-top: 10px;
  font-size: 14px;
}
.list-verifier-filters {
  overflow: hidden;
  margin-top: 10px;
  margin-left: 10px;
  line-height: 1.428571429;
}
.list-verifier-filters li {
  float: left;
  width: 360px;
  padding-right: 18px;
}
.list-verifier-filters-label {
  margin-right: 25px;
  line-height: 30px;
  font-size: 14px;
  color: #333;
}
.list-verifier-filters .input-narrow {
  width: 175px;
  color: #555;
}
.list-verifier-filters-right {
  float: right;
}
.goodsTypeSpanWrap {
  border: 1px solid #ff3f3f;
  color: #ff3f3f;
  border-radius: 3px;
  padding: 2px;
  margin-right: 2px;
}
.goodsLabelSpanWrap {
  border: 1px solid #cccccc;
  color: #666;
  border-radius: 3px;
  padding: 2px;
  margin-right: 2px;
  display: inline-block;
}
.iconSpan {
  color: #5a8bff;
  text-decoration: none;
  cursor: pointer !important;
}
.noData {
  width: 30px;
  height: 33px;
  margin: 25px auto auto auto;
}
.noData span {
  margin: 10px;
}
.table-page {
  height: 52px;
  padding: 10px;
  background: #fff;
}
</style>
