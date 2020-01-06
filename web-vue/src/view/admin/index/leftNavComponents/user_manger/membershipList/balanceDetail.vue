<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="top spDiv">
        <div>
          <span>{{$t('membershipIntroduction.memberName')}}：</span>
          <el-input
            v-model="username"
            :placeholder="$t('membershipIntroduction.memberName')"
            size="small"
          >
          </el-input>
        </div>
        <div>
          <span>{{$t('membershipIntroduction.orderSn')}}：</span>
          <el-input
            v-model="orderSn"
            :placeholder="$t('membershipIntroduction.placeHolderOrderSn')"
            size="small"
          ></el-input>
        </div>
        <div class="receiveDetailDate">
          <span>{{$t('membershipIntroduction.time')}}：</span>
          <el-date-picker
            v-model="dateInput"
            type="daterange"
            align="center"
            unlink-panels
            :range-separator="$t('membershipIntroduction.to')"
            :start-placeholder="$t('membershipIntroduction.Starttime')"
            :end-placeholder="$t('membershipIntroduction.Endtime')"
            value-format="yyyy-MM-dd HH:mm:ss"
            :default-time="['00:00:00','23:59:59']"
            size="small"
          >
          </el-date-picker>
        </div>
        <div>
          <el-button
            type="primary"
            size="small"
            @click="getUserDetailAcountData()"
          >{{$t('membershipIntroduction.filter')}}</el-button>
        </div>
      </div>

      <div class="table-content">
        <table
          class="content"
          width="100%"
        >
          <thead>
            <tr>
              <td>会员昵称</td>
              <td>手机号码</td>
              <td>订单号</td>
              <td>余额</td>
              <td>时间</td>
              <td>备注</td>
            </tr>
          </thead>
          <tbody v-if="showUserFlag">
            <tr
              v-for="(item,index) in trList"
              :key="index"
              :class="clickIindex===index?'clickClass':''"
              @click="handleClick(index)"
            >
              <td>{{item.username}}</td>
              <td>{{item.mobile}}</td>
              <td>{{item.orderSn}}</td>
              <td>{{item.amount}}</td>
              <td>{{item.createTime}}</td>
              <td>{{item.remark}}</td>
            </tr>
          </tbody>
        </table>
        <div
          class="noData"
          v-if="!showUserFlag"
        >
          <img :src="noImg">
          <span>暂无相关数据</span>
        </div>
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="dealWithPagination"
      />
    </div>
  </div>
</template>
<script>
import { accountDetailRequest } from '@/api/admin/membershipList.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      // 用户id
      id: '',
      username: '',
      pageParams: {
        pageRows: 20,
        currentPage: 0
      },
      orderSn: '',
      nameInput: '',
      dateInput: null,
      tbodyFlag: false,
      clickIindex: null,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      trList: [],
      showUserFlag: false

    }
  },
  created () {
    // 获取会员的id

    this.id = this.$route.query.id
    this.username = this.$route.query.name
    console.log(this.id)
    // 加载用户余额明细数据
    this.getUserDetailAcountData()
  },
  mounted () {
    console.log('mounted method')
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 行点击
    handleClick (index) {
      this.clickIindex = index
    },
    clearData () {
      this.showUserFlag = false
      this.trList = []
    },
    // 获取会员余额详细信息
    getUserDetailAcountData () {
      this.clearData()
      console.log(this.dateInput)
      let obj = {
        'currentPage': this.pageParams.currentPage,
        'pageRows': this.pageParams.pageRows,
        'userId': this.id,
        'userName': this.username,
        'orderSn': this.orderSn,
        'startTime': this.dateInput ? this.dateInput[0] : null,
        'endTime': this.dateInput ? this.dateInput[1] : null
      }
      console.log(obj)

      accountDetailRequest(obj).then(res => {
        console.log(res)
        if (res) {
          // 设置tbody出现
          this.tbodyFlag = true
          // 将数据绑定到data中
          this.trList = res.content.dataList
          // 获取分页信息
          this.pageParams = res.content.page
          console.log(this.pageParams)
          console.log(this.trList)
          if (this.trList.length > 0) {
            this.showUserFlag = true
          }
          // 清空id
          this.id = null
        }
      })
    },
    // 分页信息改变
    dealWithPagination () {
      console.log(this.pageParams)
      let obj = {
        'currentPage': this.pageParams.currentPage,
        'pageRows': this.pageParams.pageRows,
        'userName': this.username
      }
      console.log(obj)
      accountDetailRequest(obj).then(res => {
        if (res) {
          // 更新数据
          this.trList = res.content.dataList
          console.log(this.trList)
        }
      })
    }
  }
}
</script>
<style scoped>
.receiveDetail {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.receiveDetailMain {
  /* background-color: #fff;
  padding: 15px 25px; */
  overflow: hidden;
}
.top {
  padding: 15px;
  background: #fff;
  display: flex;
}
.table-content {
  padding: 15px;
  background: #fff;
  margin-top: 10px;
}

.top > div {
  display: flex;
  min-width: 220px;
}

.receiveDetailDate {
  min-width: 430px !important;
}

.receiveDetailDate > span {
  min-width: 67px !important;
}
.top > div:nth-of-type(3) {
  margin-right: 100px;
}
.top > div > span {
  display: inline-block;
  line-height: 30px;
  text-align: right;
  margin-right: 20px;
  min-width: 70px;
  color: #333;
}

.spDiv {
  padding-left: 22px;
}

table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 10px;
  vertical-align: middle !important;
  text-align: center;
}
.clickClass {
  background-color: #eee !important;
}
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.noData span {
  display: flex;
  margin: 10px;
}
</style>
<style>
.receiveDetailDate .el-input__inner .el-range-separator {
  width: 20%;
}
</style>
