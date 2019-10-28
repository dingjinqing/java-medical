<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="top spDiv">
        <div>
          <span>{{$t('membershipIntroduction.memberName')}}</span>
          <el-input
            v-model="username"
            :placeholder="$t('membershipIntroduction.memberName')"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>{{$t('membershipIntroduction.orderSn')}}</span>
          <el-input
            v-model="orderSn"
            :placeholder="$t('membershipIntroduction.placeHolderOrderSn')"
            size="small"
          ></el-input>
        </div>
        <div class="receiveDetailDate">
          <span>{{$t('membershipIntroduction.time')}}</span>
          <el-date-picker
            v-model="dateInput"
            type="daterange"
            align="right"
            unlink-panels
            :range-separator="$t('membershipIntroduction.to')"
            :start-placeholder="$t('membershipIntroduction.Starttime')"
            :end-placeholder="$t('membershipIntroduction.Endtime')"
            value-format='yyyy-MM-dd'
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

      <!--底部表格-->
      <div
        class="content"
        v-if="page_one"
      >
        <table width='100%'>
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
              <td>{{item.amount}}</td>
              <td class="link">{{item.createTime}}</td>
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
      orderSn: '',
      nameInput: '',
      dateInput: '',
      membershipCardOptins: [
        {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }
      ],
      membershipCardValue: '',
      CardTypeOptins: [],
      CardTypeValue: '',
      page_one: true,
      tbodyFlag: false,
      trList: [],
      clickIindex: null,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      pageParams: null

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
    // 获取会员余额详细信息
    getUserDetailAcountData () {
      console.log(this.dateInput)
      if (this.dateInput) {
        this.dateInput[0] = this.dateInput[0] + ' 00:00:00'
        this.dateInput[1] = this.dateInput[1] + ' 23:59:59'
      }

      let obj = {
        'userId': this.id,
        'userName': this.username,
        'orderSn': this.orderSn,
        'startTime': this.dateInput[0],
        'endTime': this.dateInput[1]
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
  padding: 15px 25px;
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
}
.top {
  display: flex;
}
.top > div {
  display: flex;
  width: 280px;
}
.top > div:nth-of-type(2) {
  margin: 0 100px;
}
.top > div > span {
  display: inline-block;
  width: 80px;
  line-height: 30px;
  line-height: 30px;
  text-align: right;
  margin-right: 25px;
  color: #333;
}
.receiveDetailDate {
  width: 480px !important;
}
.middle {
  margin-top: 20px;
}
.spDiv {
  padding-left: 22px;
}
.middleType {
  margin: 0 122px !important;
  margin-left: 80px !important;
  width: 330px !important;
}
.middleTypeSpan {
  width: 100px !important;
}

.content {
  margin-top: 30px;
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
  width: 150px !important;
}
.receiveDetail .receiveDetailMain .receiveDetailDate .el-input__inner {
  width: 350px !important;
}
</style>
