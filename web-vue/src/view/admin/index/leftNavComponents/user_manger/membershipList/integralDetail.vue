<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="top spDiv">
        <div>
          <span>{{$t('membershipIntroduction.memberName')}}：</span>
          <el-input
            v-model="userName"
            :placeholder="$t('membershipIntroduction.memberName')"
            size="small"
          ></el-input>
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
            align="right"
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
            @click="getUserDetailScoreData()"
          >{{$t('membershipIntroduction.filter')}}</el-button>
        </div>
      </div>

      <!--底部表格-->
      <div
        class="content"
        v-if="page_one"
      >
        <table width='100%'>
          <thead>
            <tr>
              <td>{{$t('membershipIntroduction.memberName')}}</td>
              <td>{{$t('membershipIntroduction.phoneNum')}}</td>
              <td>{{$t('membershipIntroduction.orderSn')}}</td>
              <td>{{$t('membershipIntroduction.score')}}</td>
              <td>{{$t('membershipIntroduction.time')}}</td>
              <td>{{$t('membershipIntroduction.expiredTime')}}</td>
              <td>{{$t('membershipIntroduction.remark')}}</td>

            </tr>
          </thead>
          <tbody v-if="tbodyFlag">
            <tr
              v-for="(item,index) in trList"
              :key="index"
              :class="clickIindex===index?'clickClass':''"
              @click="handleClick(index)"
            >

              <td>{{item.username}}</td>
              <td>{{item.mobile}}</td>
              <td>{{item.orderSn}}</td>
              <td>{{item.score}}</td>
              <td class="link">{{item.createTime}}</td>
              <td class="link">{{item.expireTime}}</td>
              <td class="tb_decorate_a">
                {{item.remark}}
              </td>
            </tr>
          </tbody>

        </table>
        <div
          class="noData"
          v-if="!tbodyFlag"
        >
          <img :src="noImg">
          <span>暂无相关数据</span>
        </div>
      </div>
      <div
        class="content_two"
        v-else
      >
        <table width='100%'>
          <thead>
            <tr>
              <td>名称</td>

              <td>链接</td>
            </tr>
          </thead>
          <tbody v-if="tbodyFlag">
            <tr
              v-for="(item,index) in trList"
              :key="index"
              :class="clickIindex===index?'clickClass':''"
              @click="handleClick(index)"
            >
              <td>{{item.title}}</td>

              <td class="tb_decorate_a">
                {{item.path}}
              </td>
            </tr>
          </tbody>

        </table>
        <div
          class="noData"
          v-if="!tbodyFlag"
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

import { scoreDetailRequest } from '@/api/admin/membershipList.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      id: '', // 用户id
      userName: '', // 用户名称
      pageParams: {
        pageRows: 20
      }, // 分页信息
      orderSn: '', // 订单号码
      nameInput: '',
      dateInput: null,
      membershipCardOptins: [
      ],
      membershipCardValue: '',
      CardTypeOptins: [
      ],
      CardTypeValue: '',
      page_one: true,
      tbodyFlag: false,
      trList: [],
      clickIindex: null,
      noImg: this.$imageHost + '/image/admin/no_data.png'
    }
  },
  methods: {
    // 处理分页查询
    dealWithPagination () {
      this.getUserDetailScoreData()
    },
    // 行点击
    handleClick (index) {
      this.clickIindex = index
    },
    getUserDetailScoreData () {
      // 准备查询数据
      let obj = {
        'pageRows': this.pageParams.pageRows,
        'currentPage': this.pageParams.currentPage,
        'userId': this.id,
        'userName': this.userName,
        'orderSn': this.orderSn,
        'startTime': this.dateInput ? this.dateInput[0] : null,
        'endTime': this.dateInput ? this.dateInput[1] : null

      }

      console.log(obj)
      scoreDetailRequest(obj).then(res => {
        console.log((res.content.dataList))
        if (res.error === 0) {
          // 装载数据
          this.trList = res.content.dataList
          // 表格可视化
          if (this.trList.length > 0) {
            this.tbodyFlag = true
          }
          // 分页信息
          this.pageParams = res.content.page
          // 清除id
          delete this.id
        }
      })
    }
  },
  created () {
    // 从路由中获取id,username
    this.userName = this.$route.query.name
    this.id = this.$route.query.id
    // 加载数据： 积分 | 分页数据
    this.getUserDetailScoreData()
  }
}
</script>
<style scoped>
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 650px; */
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.noData span {
  margin: 10px;
}
.receiveDetail {
  padding: 10px;
  padding-bottom: 68px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.receiveDetailMain {
  /* padding: 15px 25px; */
  position: relative;
  /* background-color: #fff; */
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
}
.top {
  padding: 15px;
  background: #fff;
  display: flex;
}
.top > div {
  display: flex;
  /* min-width: 220px; */
}
.top > div:nth-of-type(3) {
  margin-right: 100px;
}
.top > div > span {
  display: inline-block;
  min-width: 70px;
  line-height: 30px;
  line-height: 30px;
  text-align: left;
  /* margin-right: 25px; */
  color: #333;
}
.top > div > .el-input {
  flex: 1;
}
.top > div {
  margin-right: 10px;
}
.receiveDetailDate {
  width: 480px !important;
}
.middle {
  margin-top: 20px;
}
/* .spDiv {
  padding-left: 22px;
} */
.middleType {
  margin: 0 122px !important;
  margin-left: 80px !important;
  width: 330px !important;
}
.middleTypeSpan {
  width: 100px !important;
}

.content {
  margin-top: 10px;
  padding: 15px;
  background: #fff;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
.clickClass {
  background-color: #eee !important;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}
/* thead td:nth-of-type(1) {
  width: 220px;
}
thead td:nth-of-type(2) {
  width: 104px;
} */

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
.content_two td:nth-of-type(2) {
  width: 490px !important;
}
</style>
<style>
.receiveDetailMain .top .el-input__inner {
  width: 170px !important;
}
.receiveDetail .receiveDetailMain .receiveDetailDate .el-input__inner {
  width: 350px !important;
}
</style>
