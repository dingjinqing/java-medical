<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="filter">
        <div class="top spDiv">
          <div :class="mixinleftDiv">
            <span>{{$t('membershipIntroduction.phoneNum')}}：</span>
            <el-input
              v-model="phoneNum"
              :placeholder="$t('membershipIntroduction.placePhoneNum')"
              size="small"
            ></el-input>
          </div>
          <div>
            <span>{{$t('membershipIntroduction.nickname')}}：</span>
            <el-input
              v-model="nameInput"
              :placeholder="$t('membershipIntroduction.placeNameNum')"
              size="small"
            ></el-input>
          </div>
          <div class="receiveDetailDate">
            <span>{{$t('membershipIntroduction.Collectiontime')}}：</span>
            <el-date-picker
              v-model="dateInput"
              type="daterange"
              size="small"
              align="right"
              unlink-panels
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.startdata')"
              :end-placeholder="$t('membershipIntroduction.enddate')"
              value-format='yyyy-MM-dd'
            >
            </el-date-picker>
          </div>
        </div>

        <div class="top middle">
          <div>
            <span>{{$t('membershipIntroduction.membershipCard')}}：</span>
            <el-select
              v-model="membershipCardValue"
              :placeholder="$t('membershipIntroduction.placeChoise')"
              size="small"
            >
              <el-option
                v-for="(item,index) in membershipCardOptins"
                :key="index"
                :label="item.cardName"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
          <div class="middleType">
            <span class="middleTypeSpan">{{$t('membershipIntroduction.membershipcard')}}：</span>
            <el-select
              v-model="CardTypeValue"
              :placeholder="$t('membershipIntroduction.placeChoise')"
              size="small"
            >
              <el-option
                v-for="item in CardTypeOptins"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div>
            <el-button
              type="primary"
              size="small"
              @click="loadUserCardData()"
            >{{$t('membershipIntroduction.screen')}}</el-button>
          </div>
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
              <td>{{$t('membershipIntroduction.Collectiontime')}}</td>
              <td>{{$t('membershipIntroduction.CardNumber')}}</td>
              <td>{{$t('membershipIntroduction.Member')}}</td>
              <td>{{$t('membershipIntroduction.membershipCard')}}|{{$t('membershipIntroduction.type')}}</td>
              <td>{{$t('membershipIntroduction.state')}}</td>
              <td>{{$t('membershipIntroduction.Balanceyuan')}}</td>
              <td>{{$t('membershipIntroduction.servicestimes')}}</td>
              <td>{{$t('membershipIntroduction.ExchangeFrequency')}}</td>
              <td>{{$t('membershipIntroduction.operation')}}</td>
            </tr>
          </thead>
          <tbody v-if="tbodyFlag">
            <tr
              v-for="(item,index) in trList"
              :key="index"
              :class="clickIindex===index?'clickClass':''"
              @click="handleClick(index)"
            >
              <td>{{item.createTime}}</td>
              <td>{{item.cardNo}}</td>
              <td>{{item.username}} </td>
              <td>{{item.cardName}}
                <div id="memberCard">
                  <div v-if="item.cardType==='0'">
                    {{$t('membershipIntroduction.normalCard')}}
                  </div>
                  <div v-else-if="item.cardType==='1'">
                    {{$t('membershipIntroduction.limiteCard')}}
                  </div>
                  <div v-else-if="item.cardType==='2'">
                    {{$t('membershipIntroduction.rankCard')}}
                  </div>
                </div>

              </td>
              <td>
                <span v-if="item.flag==0">
                  <!-- 已过期 -->
                  <span v-if="item.expireTime && Date.now()>Date.parse(item.expireTime)">
                    {{$t('membershipIntroduction.expired')}}
                  </span>
                  <span v-else>
                    <!-- 使用中 -->
                    {{$t('membershipIntroduction.using')}}
                  </span>
                </span>
                <span v-else-if="item.flag==1">
                  <!-- 已弃用 -->
                  {{$t('membershipIntroduction.abolished')}}
                </span>
              </td>
              <td>{{item.money}}</td>
              <td>{{item.surplus}}</td>
              <td>{{item.exchang_surplus}}</td>
              <td class="link">
                <div class="operateDiv">
                  <span>充值明细</span>
                  <span>-消费明细</span>
                  <span
                    v-if="item.deleteShow"
                    @click="deleteUserCard(item)"
                  >-废除</span>
                </div>
              </td>

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
          <span>{{$t('membershipIntroduction.noData')}}</span>
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
    </div>
  </div>
</template>
<script>
import { getAllMemberCardDetailRequest, deleteUserCardRequest } from '@/api/admin/memberManage/memberCard.js'
import { allUserCardRequest } from '@/api/admin/membershipList.js'

export default {
  data () {
    return {
      userId: '', // 用户id
      username: '', // 用户名
      phoneNum: '',
      nameInput: '',
      dateInput: '',
      membershipCardOptins: [],
      membershipCardValue: '',
      CardTypeOptins: [],
      CardTypeValue: '',
      page_one: true,
      tbodyFlag: false,
      trList: [], // 表格数据
      clickIindex: null,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      mixinleftDiv: ''
    }
  },
  created () {
    // 从路由获取userId
    this.userId = this.$route.query.id
    this.username = this.$route.query.name
    this.nameInput = this.$route.query.name
    console.log(this.userId, this.username)

    // 加载数据
    this.loadAllPageDate()
  },
  mounted () {
    this.langDefault()
  },
  watch: {
    lang () {
      // 加载数据
      this.loadAllOptionsData()
    }
  },
  methods: {
    // 1- 行点击
    handleClick (index) {
      this.clickIindex = index
    },
    // 2- 初始化页面所有数据
    loadAllPageDate () {
      // 加载表格数据
      this.loadUserCardData()
      // 加载选项数据
      this.loadAllOptionsData()
    },
    // 3- 加载表格数据
    loadUserCardData () {
      console.log(this.dateInput, this.dateInput.length)
      console.log(typeof this.dateInput)
      if (this.dateInput) {
        this.dateInput[0] = this.dateInput[0] + ' 00:00:00'
        this.dateInput[1] = this.dateInput[1] + ' 23:59:59'
      }
      let obj = {
        'userId': this.userId,
        'mobile': this.phoneNum,
        'username': this.nameInput,
        'createTimeFirst': this.dateInput[0],
        'createTimeSecond': this.dateInput[1],
        'cardId': this.membershipCardValue,
        'cardType': this.CardTypeValue
      }
      console.log(obj)
      getAllMemberCardDetailRequest(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          // 绑定数据
          if (res.content.length > 0) {
            this.trList = res.content
            this.trList.forEach(item => {
              item.deleteShow = false
              if (Number(item.flag) === 0) {
                item.deleteShow = true
              }
            })
            console.log(this.trList)
            // 显示数据
            this.tbodyFlag = true
          } else {
            this.trList = []
            this.tbodyFlag = false
          }
        }
      })
    },

    // 3- 加载选项数据
    loadAllOptionsData () {
      this.getAllUserCard()
      this.getAllCardType()
    },
    // 3.1- 获取所有所有会员卡下拉框数据
    getAllUserCard () {
      allUserCardRequest().then(res => {
        console.log(this.membershipCardOptins)
        this.membershipCardOptins = []
        this.membershipCardOptins.push(this.$t('membershipIntroduction.allCard'))
        console.log(this.membershipCardOptins)
        if (res.error === 0) {
          this.membershipCardOptins.push(...res.content)
          console.log(this.membershipCardOptins)
        }
      })
    },
    // 3.2- 获取所有会员卡类型
    getAllCardType () {
      console.log(this.CardTypeOptins)
      this.CardTypeOptins = this.$t('membershipIntroduction.cardTypeArray')
      console.log(this.CardTypeOptins)
    },
    // 4- 清空缓存
    clearCacheData () {

    },
    // 5- 成功消息弹框
    getSuccessMessagePrompt () {
      var message = this.$t('membershipIntroduction.success')
      this.$message.success({
        showClose: true,
        message: message,
        type: 'success'
      })
    },
    deleteUserCard (card) {
      console.log('delete card:', card)
      deleteUserCardRequest({ cardNo: card.cardNo }).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('membershipIntroduction.deleteCardSuccess'))
          this.loadAllPageDate()
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
  /* margin-top: 10px; */
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
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
}
.filter {
  padding: 15px;
  margin-bottom: 10px;
  background: #fff;
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
  text-align: left;
  /* margin-right: 25px; */
  color: #333;
}
.top > div > .el-input {
  flex: 1;
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
  margin-left: 60px !important;
}
.middleTypeSpan {
  width: 100px !important;
}

.content {
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

.mixinleftDiv {
  width: 280px !important;
}
.operateDiv {
  color: #0e70ca;
}
.operateDiv span {
  cursor: pointer;
  margin-left: -3px;
}
</style>
<style>
.receiveDetailMain .top .el-input__inner {
  width: 150px !important;
}
.receiveDetail .receiveDetailMain .receiveDetailDate .el-input__inner {
  width: 350px !important;
}
#memberCard {
  margin: 10px auto;
}
</style>
