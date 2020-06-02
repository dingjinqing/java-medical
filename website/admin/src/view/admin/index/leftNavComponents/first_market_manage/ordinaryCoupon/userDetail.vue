<template>
  <div class="content">
    <div class="main">
      <el-form
        label-width="100px"
        style="margin-bottom: 10px;"
      >
        <div class="tabContent">
          <el-form-item
            :label="this.$t('couponReceive.mobile') + '：'"
            class="item"
          >
            <el-input
              v-model="searchData.mobile"
              :placeholder="$t('couponReceive.mobilePlaceholder')"
              size="small"
              class="inputWidth"
            ></el-input>
          </el-form-item>
          <el-form-item
            :label="this.$t('couponReceive.userNickName') + '：'"
            class="item"
          >
            <el-input
              v-model="searchData.userName"
              :placeholder="$t('couponReceive.userNickNamePlaceholder')"
              size="small"
              class="inputWidth"
            ></el-input>
          </el-form-item>
          <el-form-item
            :label="this.$t('couponReceive.useState') + '：'"
            class="item"
          >
            <el-select
              v-model="searchData.isUsed"
              size="small"
              class="inputWidth"
            >
              <el-option
                v-for="item in usedOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-button
            type="primary"
            size="small"
            @click="initDataList"
            class="item"
            style="margin-left: 10px;"
          >{{$t('couponReceive.search')}}</el-button>
        </div>

      </el-form>

      <div class="table_box">
        <el-table
          v-loading="loading"
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          style="width:100%;"
          border
        >
          <el-table-column
            :label="$t('couponReceive.userNickName')"
            align="center"
          >
            <template slot-scope="scope">
              <span
                class="jumpStyle"
                @click="infoClickHandler(scope.row.userId)"
              >{{ scope.row.username }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('couponReceive.mobile')"
            align="center"
          ></el-table-column>
          <el-table-column
            :label="$t('couponReceive.receiveMethods')"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.accessMode === 0 ? '发放' : '直接领取'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="isUsed"
            :label="$t('couponReceive.WhetherToUse')"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.isUsed === 0 ? '否' : '是'}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="优惠内容"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{scope.row.denomination ? scope.row.denomination : 0}}元</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="validityTime"
            :label="$t('couponReceive.validDate')"
            sortable
            align="center"
            width="160px"
          >
            <template slot-scope="scope">
              {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('couponReceive.receiveTime')"
            sortable
            align="center"
            width="160px"
          ></el-table-column>
          <el-table-column
            prop="usedTime"
            :label="$t('couponReceive.useTime')"
            sortable
            align="center"
            width="160px"
          ></el-table-column>
          <el-table-column
            :label="$t('couponReceive.operate')"
            align="center"
          >
            <template slot-scope="scope">
              <span
                style="font-size: 22px;color: #5a8bff;"
                class="el-icon-delete"
                @click="deleteCoupon(scope.row.id)"
                v-if="scope.row.isUsed === 0 && scope.row.endFlag === 0 && scope.row.delFlag === 0"
              ></span>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { deleteCouponDetail, couponUserDetail } from '@/api/admin/marketManage/couponList.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      loading: false,
      id: '', // 优惠券id
      tableData: [],
      pageParams: {}, // 分页
      // 搜索
      searchData: {
        mobile: '',
        userName: '',
        isUsed: -1
      },
      // 使用状态
      usedOption: [
        { value: -1, label: '全部' },
        { value: 1, label: '未使用' },
        { value: 2, label: '已使用' },
        { value: 3, label: '已过期' },
        { value: 4, label: '已废除' }
      ],
      shareUserId: null // 分享者id
    }
  },
  mounted () {
    this.id = this.$route.query.id
    this.shareUserId = this.$router.query.shareUserId

    // 初始化数据
    this.initDataList()
  },
  methods: {
    // 领取明细列表
    initDataList () {
      let requestParams = {}
      requestParams.id = this.id
      requestParams.shareId = this.shareUserId
      requestParams.currentPage = this.pageParams.currentPage
      requestParams.pageRows = this.pageParams.pageRows
      requestParams.mobile = this.searchData.mobile
      requestParams.userName = this.searchData.userName
      if (this.searchData.isUsed === -1) {
        requestParams.isUsed = null
      } else {
        requestParams.isUsed = this.searchData.isUsed
      }
      couponUserDetail(requestParams).then(res => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },

    // 表格处理数据
    handleData (data) {
      var timestamp = new Date().getTime()
      data.map((item, index) => {
        if (timestamp <= new Date(item.endTime).getTime()) {
          // 未过期
          item.endFlag = 0
        } else {
          // 已过期
          item.endFlag = 1
        }
      })
      this.tableData = data
    },

    // 删除明细
    deleteCoupon (id) {
      this.$confirm('是否删除该张优惠券?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCouponDetail(id).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '删除成功!' })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消删除' })
      })
    },

    // 个人信息跳转
    infoClickHandler (id) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: id
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    .tabContent {
      background-color: #fff;
      .item {
        display: inline-block;
        margin: 10px 0;
      }
    }

    .filters {
      display: flex;
      line-height: 32px;
      margin-bottom: 10px;
      background-color: #fff;
      padding: 15px;
      .filters_item {
        max-width: 250px;
        display: flex;
        margin-left: 25px;
        font-size: 14px;
        .mobile {
          width: 60px;
        }
        .itemWidth {
          width: 80px;
        }
        .inputWidth {
          width: 170px;
        }
      }
    }
    .table_box {
      background-color: #fff;
      padding: 15px;

      .operation {
        display: flex;
        justify-content: space-around;
        > .item {
          font-size: 22px;
          color: #66b1ff;
          cursor: pointer;
        }
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
}
.jumpStyle {
  color: #5a8bff;
  cursor: pointer;
}
</style>
