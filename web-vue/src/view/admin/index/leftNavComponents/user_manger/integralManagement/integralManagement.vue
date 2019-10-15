<template>
  <div class="integralManagement">
    <div class="integralManagementMain">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="积分规则设置"
          name="first"
        >
          <IntegralRule @toNoticeSend="toNoticeSend" />
        </el-tab-pane>
        <el-tab-pane
          label="前端展示设置"
          name="second"
        >
          <FrontEndDisplay />
        </el-tab-pane>
      </el-tabs>
    </div>
    <!--积分使用规则模块-->
    <div
      class="integralManagementMain integralUsage"
      v-if="activeName==='first'"
    >
      <div class="title">
        <span></span>
        积分使用规则
      </div>
      <div class="intLimit">
        <div class="intLimitTop">
          <span>积分支付限制</span>
          <div>
            <el-radio
              v-model="limitRadio"
              label="0"
            >不限制</el-radio>
            <el-radio
              v-model="limitRadio"
              label="1"
            >自定义</el-radio>
            <div
              v-if="limitRadio==='1'"
              style="margin-top:10px"
            >
              <span>每单支付的积分数量少于
                <el-input-number
                  size="small"
                  v-model="limitIntegralNum"
                  controls-position="right"
                  :min="1"
                  :max="100000"
                ></el-input-number>积分，不可使用积分支付
                <span style="margit-left:20px;color:#999">请填写大于100积分且为100积分的整数倍的数值</span>
              </span>
            </div>
          </div>
        </div>
        <div class="intLimitTop intLimitFooter">
          <span>积分抵扣比例</span>
          <div>
            <span>用户可使用积分抵扣订单折后金额的
              <el-input-number
                size="small"
                v-model="limitIntegralNum"
                controls-position="right"
                :min="1"
                :max="100000"
              ></el-input-number>%，不可使用积分支付
              <span style="margit-left:20px;color:#999">不填默认积分折扣订单折后金额的50%</span>
            </span>
          </div>
        </div>
      </div>
      <div class="title">
        <span></span>
        积分获取规则
        <i style="color:#999">和会员卡获取积分相互排斥，以会员卡优先</i>
      </div>
      <!--积分获取规则下方代码块-->
      <div class="intContent">
        <span class="intTitle">购物送积分</span>
        <el-switch
          v-model="shopValue"
          active-color="#13ce66"
          inactive-color="#ff4949"
        >
        </el-switch>
        <span style="display:inline-block;margin:0 20px">{{shopValue?'已开启':'已关闭'}}</span>
        <span style="color:#999">开关开启，则订单完成后会给用户按照积分获取规则赠送积分，关闭则不赠送</span>
        <div v-if="shopValue">
          <div
            v-for="(item,index) in shopFullArr"
            :key="index"
          >
            <div class="noneIntegralDiv">
              <span v-if="index===0">
                <el-radio
                  v-model="scoreType"
                  label="0"
                >{{ $t('memberCard.shopFull') }}&nbsp;&nbsp;</el-radio>
              </span>
              <span v-else>
                <span
                  v-for="i in 5"
                  :key=i
                >
                  &nbsp;&nbsp;&nbsp;
                </span>
              </span>
              <el-input
                size="small"
                v-model="item.left"
              ></el-input>&nbsp;&nbsp; {{ $t('memberCard.send') }} &nbsp;&nbsp;
              <el-input
                size="small"
                v-model="item.right"
              >
              </el-input>&nbsp;&nbsp;{{ $t('memberCard.score') }}&nbsp;&nbsp;<img
                v-if="index === 0"
                style="cursor:pointer"
                :src="$imageHost +'/image/admin/sign_jia.png' "
                @click="handleToAddIntegral()"
              >

              <img
                v-else
                style="cursor:pointer"
                :src="$imageHost +'/image/admin/sign_del.png' "
                @click="handleToDelIntegral(index)"
              >
            </div>
          </div>
          <div class="shoppingFullBottom">
            <el-radio
              v-model="scoreType"
              label="1"
            >{{ $t('memberCard.shopEachFull') }}</el-radio>
            <el-input
              size="small"
              v-model="buyEach"
            ></el-input>&nbsp;&nbsp;{{ $t('memberCard.send') }}&nbsp;&nbsp;
            <el-input
              size="small"
              v-model="scoreEach"
            ></el-input>&nbsp;&nbsp;{{ $t('memberCard.score') }}

          </div>
        </div>

      </div>
      <div class="intContent">
        <span class="intTitle">门店买单送积分</span>
        <el-switch
          v-model="doorValue"
          active-color="#13ce66"
          inactive-color="#ff4949"
        >
        </el-switch>
        <span style="display:inline-block;margin:0 20px">{{doorValue?'已开启':'已关闭'}}</span>
        <span style="color:#999">开关开启，则会在用户门店买单后按照购物送积分规则给用户赠送积分，关闭则不赠送</span>
      </div>
      <div class="intContent">
        <div class="loginDiv">
          <span
            class="intTitle"
            style="margin-right:15px"
          >登陆送积分</span>
          <el-switch
            v-model="loginValue"
            active-color="#13ce66"
            inactive-color="#ff4949"
          >
          </el-switch>
          <div>
            <span style="display:inline-block;margin:0 20px">{{loginValue?'已开启':'已关闭'}}</span>
            <span style="color:#999">开关开启，则登陆后会给用户按照积分获取规则赠送积分，关闭则不赠送</span>
            <div
              v-if="loginValue"
              class="hiddenLoginDiv"
            >
              <span>登陆送</span>
              <div>
                <el-input-number
                  size="small"
                  v-model="loginIntegralNum"
                  controls-position="right"
                  :min="1"
                  :max="100000"
                ></el-input-number>积分
                <span style="color:#f66">每日仅首次登陆赠送积分</span>
              </div>

            </div>
          </div>
        </div>
      </div>
      <div class="intContent signDiv">
        <span
          class="intTitle"
          style="margin-right:15px"
        >签到送积分</span>
        <el-switch
          v-model="signInvalue"
          active-color="#13ce66"
          inactive-color="#ff4949"
        >
        </el-switch>
        <div class="signHiddenDiv">
          <span style="display:inline-block;margin:0 20px">{{signInvalue?'已开启':'已关闭'}}</span>
          <span style="color:#999;margin-right:10px;display:inline-block">开关开启，则系统开启签到14天</span><i
            @click="handleToCheckMember()"
            style="cursor:pointer;color:#5a8bff"
          >查看签到会员</i>
          <div
            v-if="signInvalue"
            class="hiddenLoginDiv"
          >
            <div
              v-for="(item,index) in signInput.length"
              :key="index"
              style="margin-bottom:5px"
            >
              <span>连续签到{{item}}天，送</span>
              <el-input
                v-model="signInput[index].input"
                size="small"
              ></el-input>
              <span>积分</span>
              <span
                @click="handleToAdd()"
                v-if="index===0"
                style="cursor:pointer"
              >
                <img :src="$imageHost+'/image/admin/sign_jia.png'">
              </span>
              <span
                @click="handleToDel(index)"
                v-if="index>0&&index===(signInput.length-1)"
                style="cursor:pointer"
              >
                <img :src="$imageHost+'/image/admin/sign_del.png'">
              </span>
            </div>

          </div>
        </div>
      </div>
    </div>
    <!--保存-->
    <saveComponent />
  </div>
</template>
<script>
import { userScoreConfigUpdate, getScoreConfigRequest } from '@/api/admin/memberManage/scoreManage/scoreCfg.js'
export default {
  components: {
    IntegralRule: () => import('./integralRule'),
    FrontEndDisplay: () => import('./frontEndDisplay'),
    saveComponent: () => import('./saveComponent')
  },
  data () {
    return {
      activeName: 'first',
      limitRadio: '1',
      limitIntegralNum: '',
      shopValue: true,
      doorValue: true,
      loginValue: true,
      signInvalue: true,
      scoreType: '0',
      loginIntegralNum: '',
      signData: 1,
      signInput: [
        {
          input: ''
        }
      ],
      shopFullArr: [
        {
          left: 100,
          right: 100
        }
      ],
      buyEach: null,
      scoreEach: null
    }
  },
  created () {
    this.LoadDefaultData()
  },
  methods: {
    // 加载默认数据
    LoadDefaultData () {
      getScoreConfigRequest().then(res => {
        if (res.error === 0) {
          // success

          console.log(res.content)
          let data = {
            radio: res.content.scoreLimit,
            yearValue: res.content.scoreYear,
            mounthValue: res.content.scoreMonth,
            dayValue: res.content.scoreDay,
            integralNum: res.content.scoreLimitNumber,
            integralDateValue: res.content.scorePeriod
          }
          console.log(data)

          this.limitRadio = res.content.scorePayLimit
          this.limitIntegralNum = res.content.scorePayNum
          this.shopValue = res.content.shoppingScore === '1'
          console.log(this.shopValue)
          this.doorValue = res.content.storeScore === '1'
          this.loginValue = res.content.loginScore === '1'
          this.loginIntegralNum = res.content.scoreLogin
          this.signInvalue = res.content.signInScore === 'on'
          this.signInput = []

          for (let i in res.content.signScore) {
            this.signInput.push({
              input: res.content.signScore[i]
            })
          }

          for (let i in res.content.buy) {
            this.shopFullArr.push({
              left: res.content.buy[i],
              right: res.content.buyScore[i]
            })
          }
          this.buyEach = res.content.buyEach[0]
          this.scoreEach = res.content.buyEachScore[0]
        }
      })
    },
    // 2- 保存
    saveScoreCfg (data) {
      console.log('正在准备保存积分配置数据')
      let obj = {
        'scoreLimit': data.radio,
        'scoreYear': data.yearValue,
        'scoreMonth': data.mounthValue,
        'scoreDay': data.dayValue,
        'scoreLimitNumber': data.integralNum,
        'scorePeriod': data.integralDateValue,
        'scorePayLimit': this.limitRadio,
        'scorePayNum': this.limitIntegralNum,
        'shoppingScore': this.shopValue ? 'on' : '',
        'storeScore': this.doorValue ? 'on' : '',
        'loginScore': this.loginValue ? 'on' : '',
        'scoreLogin': this.loginIntegralNum,
        'signInScore': this.signInvalue ? 'on' : '',
        'scoreType': this.scoreType,
        'signScore': this.signInput.map(item => Number(item.input)),
        'buy': this.shopFullArr.map(item => Number(item.left)),
        'score': this.shopFullArr.map(item => Number(item.right)),
        'buyEach': [Number(this.buyEach)],
        'scoreEach': [Number(this.scoreEach)]
      }
      console.log('你需要得数据', obj)
      this.userScoreConfigUpdate(obj)
    },
    // 1- 更新数据
    userScoreConfigUpdate (data) {
      userScoreConfigUpdate(data).then(res => {
        if (res.error === 0) {
          // success
          this.LoadDefaultData()
          this.$message.success(this.$t('memberCard.scoreSaveSuccess'))
        }
      })
    },
    // 3- 添加购物满
    handleToAddIntegral () {
      this.shopFullArr.push({
        left: null,
        right: null
      })
    },
    // 4- 删除购物满
    handleToDelIntegral (index) {
      this.shopFullArr.splice(index, 1)
    },
    handleClick (tab, event) {
      console.log(tab, event)
    },
    // 签到送积分点击添加icon
    handleToAdd () {
      let obj = {
        input: ''
      }
      this.signInput.push(obj)
      this.signData++
    },
    // 签到送积分点击删除icon
    handleToDel (index) {
      this.signData--
      this.signInput.splice(index, 1)
      console.log(this.signInput, index)
    },
    // 保存
    handleToSave () {
      console.log(this.signInput)
    },
    //  查看签到会员点击
    handleToCheckMember () {
      this.$router.push({
        name: 'viewSigninMembers'
      })
    },
    // 积分规则第一部分子组件传递数据
    toNoticeSend (data, flag) {
      console.log('父组件获取数据，准备提交')
      console.log(data, flag)
      let newData = {}
      switch (flag) {
        // 积分规则保存
        case 0:
          //  积分规则底部数据
          let obj = {}
          newData = Object.assign(data, obj)
          console.log(newData)
          this.saveScoreCfg(newData)
          break
        // 前端展示保存
        case 1:
          console.log(data)
          break
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.shoppingFullBottom,
.noneIntegralDiv {
  margin-top: 20px;
  padding-left: 54px;
  display: flex;
  align-items: center;

  /deep/ .el-input {
    width: 8%;
    .el-input__inner {
      width: 100%;
    }
  }
}
.shoppingFullBottom .el-input {
  width: 8%;
  .el-input__inner {
    width: 100%;
  }
}
.integralManagement {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .integralManagementMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
  }
  .integralUsage {
    margin-top: 10px;
    .title {
      span {
        display: inline-block;
        margin-left: 20px;
        border-left: 2px solid #5a8bff;
        height: 14px;
        width: 8px;
        margin-bottom: -1px;
      }
      height: 40px;
      line-height: 40px;
      background: #eef1f6;
      font-size: 14px;
      i {
        display: inline-block;
        margin-left: 30px;
      }
    }
    .intLimit {
      .intLimitTop {
        margin: 20px 0;
        display: flex;
        /deep/ .el-input-number {
          margin: 0 10px;
        }
        span {
          margin-right: 10px;
        }
      }
      .intLimitFooter {
        span {
          height: 32px;
          line-height: 32px;
        }
      }
    }
    .intContent {
      margin-top: 20px;
      .intTitle {
        display: inline-block;
        width: 100px;
        text-align: right;
        margin-right: 10px;
      }
      .loginDiv {
        display: flex;
        .hiddenLoginDiv {
          padding-left: 20px;
          margin: 20px 0;
          display: flex;
          span {
            height: 32px;
            line-height: 32px;
          }
          div {
            margin: 0 10px;
          }
        }
      }
    }
    .signDiv {
      display: flex;
      .hiddenLoginDiv {
        padding-left: 20px;
        margin: 20px 0;
        div {
          /deep/ .el-input {
            width: 70px;
            margin: 0 20px;
          }
          display: flex;
          span {
            white-space: nowrap;
            display: flex;
            align-items: center;
          }
          span:nth-of-type(2) {
            margin-right: 20px;
          }
        }
      }
    }
  }
}
</style>
