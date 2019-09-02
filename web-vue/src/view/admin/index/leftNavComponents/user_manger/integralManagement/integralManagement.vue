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
          <IntegralRule />
        </el-tab-pane>
        <el-tab-pane
          label="前端展示设置"
          name="second"
        >前端展示设置</el-tab-pane>
      </el-tabs>
    </div>
    <!--积分使用规则模块-->
    <div class="integralManagementMain integralUsage">
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
              label="1"
            >不限制</el-radio>
            <el-radio
              v-model="limitRadio"
              label="2"
            >自定义</el-radio>
            <div
              v-if="limitRadio==='2'"
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
          v-model="signInValue"
          active-color="#13ce66"
          inactive-color="#ff4949"
        >
        </el-switch>
        <div class="signHiddenDiv">
          <span style="display:inline-block;margin:0 20px">{{signInValue?'已开启':'已关闭'}}</span>
          <span style="color:#999;margin-right:10px;display:inline-block">开关开启，则系统开启签到14天</span><i style="cursor:pointer;color:#5a8bff">查看签到会员</i>
          <div
            v-if="true"
            class="hiddenLoginDiv"
          >
            <div
              v-for="(item,index) in signData"
              :key="index"
              style="margin-bottom:5px"
            >
              <span>连续签到{{item}}天，送</span>
              <el-input
                v-model="signInput.index"
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
            </div>

          </div>
        </div>
      </div>
    </div>
    <!--保存-->
    <div class="footer">
      <div
        class="save"
        @click="handleToSave()"
      >{{$t('shopStyle.saveText')}}</div>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    IntegralRule: () => import('./integralRule')
  },
  data () {
    return {
      activeName: 'first',
      limitRadio: '1',
      limitIntegralNum: '',
      shopValue: true,
      doorValue: true,
      loginValue: true,
      signInValue: true,
      loginIntegralNum: '',
      signData: 1,
      signInput: []
    }
  },
  methods: {
    handleClick (tab, event) {
      console.log(tab, event)
    },
    // 签到送积分点击添加icon
    handleToAdd () {
      this.signData++
    },
    // 保存
    handleToSave () {
      console.log(this.signInput)
    }
  }
}
</script>
<style lang="scss" scoped>
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
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    z-index: 2;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
    margin-right: 1%;
    .save {
      width: 70px;
      height: 30px;
      line-height: 30px;
      border: none;
      background: #5a8bff;
      color: #fff;
      margin: auto;
      cursor: pointer;
    }
  }
}
</style>
