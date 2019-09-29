<template>
  <div class="viewOrders">
    <div class="viewOrdersMain">
      <div class="topDiv">
        <div>
          <span>订单号：</span>
          <el-input
            v-model="orderNumInput"
            size="small"
            :placeholder="$t('memberCard.pleaseInputOrderSn')"
          ></el-input>
        </div>
        <div>
          <span>{{ $t('memberCard.memberName') }}：</span>
          <el-input
            v-model="userNameInput"
            :placeholder="$t('memberCard.pleaseInputUsername')"
            size="small"
          ></el-input>
        </div>
        <div style="margin-right:20px">
          <span>手机号：</span>
          <el-input
            v-model="phoneNumInput"
            :placeholder="$t('memberCard.pleaseInputMobile')"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>{{ $t('memberCard.useTimesType') }}</span>
          <el-select
            v-model="selectValue"
            size="small"
          >
            <el-option
              v-for="item in selectOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>

      </div>
      <div class="topDiv middle">
        <div>
          <span>{{ $t('memberCard.changeTime') }}：</span>
          <el-date-picker
            size="small"
            v-model="dateValue"
            type="daterange"
            :range-separator="$t('memberCard.to')"
            :start-placeholder="$t('memberCard.startDate')"
            :end-placeholder="$t('memberCard.overDate')"
            value-format="yyyy-MM-dd HH:mm:ss"
            :default-time="['00:00:00','23:59:59']"
          >
          </el-date-picker>
        </div>
        <div style="margin-left:20px">
          <el-button
            type="primary"
            size="small"
            @click="search"
          >{{ $t('memberCard.filter') }}</el-button>
          <el-button
            type="info"
            plain
            size="small"
          >{{ $t('memberCard.exportExcel') }}</el-button>
        </div>
      </div>
    </div>
    <div class="viewOrdersMain">
      <div class="tableMain">
        <el-table
          class="version-manage-table"
          :data="tableData"
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="orderSn"
            align="center"
            :label="$t('memberCard.orderSn')"
          >
            <template slot-scope="scope">

              <span
                @click="hanldeToClick(0,scope.row)"
                style="cursor:pointer;color:#5a8bff"
              >{{scope.row.orderSn}}</span>

            </template>
          </el-table-column>
          <el-table-column
            prop="userID"
            :label="$t('memberCard.goodsName')"
            align="center"
          >
            <template slot-scope="scope">
              <div class="content">
                <img :src="$imageHost+scope.row.goodsImg">
                <span>{{scope.row.goodsName}}</span>
              </div>

            </template>
          </el-table-column>
          <el-table-column
            prop="username"
            :label="$t('memberCard.memberName')"
            align="center"
          >
            <template slot-scope="scope">
              <span
                @click="hanldeToClick(1,scope.row)"
                style="cursor:pointer;color:#5a8bff"
              >{{scope.row.username}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('memberCard.mobile')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            :label="$t('memberCard.changeNumbers')"
            align="center"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.count !== 0">
                {{scope.row.count }}
              </span>
              <span v-else-if="scope.row.exchangCount !== 0">
                {{scope.row.exchangCount }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('memberCard.useTimesType')"
            align="center"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.count !== 0">
                {{ $t('memberCard.storeService') }}
              </span>
              <span v-else-if="scope.row.exchangCount !== 0">
                {{ $t('memberCard.exchangGoods') }}
              </span>
            </template>

          </el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('memberCard.changeTime')"
            align="center"
          >
          </el-table-column>
        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="search"
        />
      </div>
    </div>
  </div>
</template>
<script>
import { getCardConsumeOrderList } from '@/api/admin/memberManage/memberCard.js'
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 20
      },
      cardId: null, // 会员卡id
      orderNumInput: '',
      userNameInput: '',
      phoneNumInput: '',
      selectValue: 0,
      selectOptions: []
    }
  },
  created () {
    this.cardId = this.$route.query.cardId
    this.loadDefaultData()
  },
  watch: {
    lang () {
      this.selectOptions = this.$t('memberCard.selectOptions')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 1- 加载数据
    loadDefaultData () {
      let obj = {
        'pageRows': this.pageParams.pageRows,
        'currentPage': this.pageParams.currentPage,
        'cardId': this.cardId,
        'orderSn': this.orderNumInput,
        'username': this.userNameInput,
        'type': this.selectValue,
        'mobile': this.phoneNumInput,
        'firstTime': this.dateValue ? this.dataValue[0] : null,
        'secondTime': this.dataValue ? this.dataValue[1] : null
      }
      getCardConsumeOrderList(obj).then(res => {
        if (res.error === 0) {
          // success
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    },
    // 分页-筛选
    search (data) {
      console.log(data)
      this.loadDefaultData()
    },
    // 表格中单号和会员昵称点击
    hanldeToClick (flag, row) {
      switch (flag) {
        case 0:
          break
        case 1:
          this.$router.push({
            name: 'membershipInformation'
          })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.viewOrders {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .viewOrdersMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px 10px;
    margin-bottom: 10px;
    .topDiv {
      display: flex;
      margin-bottom: 20px;
      div {
        /deep/ .el-input {
          width: 140px;
        }
        span {
          white-space: nowrap;
          display: inline-block;
          width: 80px;
          text-align: right;
          margin-right: 20px;
        }
        display: flex;
        align-items: center;
        /deep/ .el-button {
          width: 85px;
        }
      }
    }
    .middle {
      display: flex;
      justify-content: space-between;
      & div:nth-of-type(2) {
        display: flex;
      }
    }
    .tableMain {
      position: relative;
      background-color: #fff;
      overflow: hidden;
      overflow-y: auto;
      margin-top: 10px;
      /deep/ .tableClss th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        font-weight: bold;
        color: #000;
        padding: 8px 10px;
        .el-checkbox {
          margin-left: -4px;
        }
      }
      .operation {
        display: flex;
        justify-content: space-around;
        span {
          cursor: pointer;
          color: #5a8bff;
        }
      }
      .content {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        img {
          width: 68px;
          height: 68px;
        }
      }
    }
  }
}
</style>
