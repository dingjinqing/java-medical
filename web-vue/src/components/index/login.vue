<template>
  <div class="container">
    <div class="head_logo">
      <img
        src="/static/image/admin/shop_logoswe.png"
        alt="微铺宝logo"
        width="200px"
      />
    </div>
    <div class="main clearfix">
      <div class="main-left">
        <img
          src="/static/image/admin/login_new_new.png"
          alt=""
        />
      </div>
      <div class="main-right">
        <div class="main-right-title">
          <div
            class="title-head "
            :class="{'title-active': !isSubLogin}"
            @click="switchTab(false)"
          >{{$t('login_page.login_main')}}</div>
          <div
            class="title-head "
            :class="{'title-active': isSubLogin}"
            @click="switchTab(true)"
          >{{$t('login_page.login_f')}}</div>
        </div>
        <div class="main-right-content ">
          <div
            class="content-zhu content-account"
            data-type="0"
            v-show="!isSubLogin"
          >
            <div class="mesg-error"></div>
            <div class="smart-form">
              <div class="account-user"><input
                  type="text"
                  v-model="mainData.username"
                  :placeholder="langData.main_name"
                /></div>
              <div class="account-pawd"><input
                  type="password"
                  v-model="mainData.password"
                  :placeholder="langData.password"
                ></div>
              <div class="account-login clearfix">
                <input
                  type="button"
                  class="one-login to-login btn-login"
                  @click="onSubmit(1)"
                  :value="langData.login"
                >
              </div>
            </div>
          </div>
          <div
            class="content-zi content-account"
            data-type="1"
            v-show="isSubLogin"
          >
            <div class="mesg-error"></div>
            <div class="smart-form">
              <div class="account-name"><input
                  type="text"
                  name="username"
                  v-model="subData.username"
                  :placeholder="langData.main_name"
                /></div>
              <div class="account-user"><input
                  type="text"
                  name="sub_username"
                  v-model="subData.subUsername"
                  :placeholder="langData.nameandnum"
                /></div>
              <div class="account-pawd"><input
                  type="password"
                  name="password"
                  v-model="subData.password"
                  :placeholder="langData.password"
                /></div>
              <div class="account-login clearfix">
                <input
                  type="button"
                  class="child-login to-login btn-login"
                  @click="onSubmit(2)"
                  :value="langData.login"
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { loginRequest } from '@/api/index/login.js'
export default {
  data () {
    return {
      mainData: {
        username: '',
        password: '',
        isSubLogin: false
      },
      subData: {
        username: '',
        password: '',
        subUsername: '',
        isSubLogin: true
      },
      isSubLogin: false,
      langData: '',
      langData_cn:
      { main_name: '主账号用户名',
        password: '密码',
        login: '登录',
        nameandnum: '子账号用户名/手机号' },
      langData_en:
      { main_name: 'Main Account User Name',
        password: 'Password',
        login: 'Sign in',
        nameandnum: 'Subaccount username/cell phone number' }

    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    // 初始化语言
    langDefault () {
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en_US') {
        this.$i18n.locale = 'en'
        this.langData = this.langData_en
      } else {
        this.langData = this.langData_cn
        this.$i18n.locale = 'cn'
      }
    },
    // 表单校验
    JudgementForm (index) {
      if (index === 1) {
        if (!this.mainData.username) {
          this.$message({
            showClose: true,
            message: '请填写您的主账号用户名',
            type: 'warning'
          })
          return
        }
        if (!this.mainData.password) {
          this.$message({
            showClose: true,
            message: '请填写您的密码',
            type: 'warning'
          })
        }
      } else {
        if (!this.subData.username) {
          this.$message({
            showClose: true,
            message: '请填写您的主账号用户名',
            type: 'warning'
          })
          return
        }
        if (!this.subData.password) {
          this.$message({
            showClose: true,
            message: '请填写您的子账号用户名或手机号',
            type: 'warning'
          })
          return
        }
        if (!this.subData.subUsername) {
          this.$message({
            showClose: true,
            message: '请填写您的密码',
            type: 'warning'
          })
        }
      }
    },
    onSubmit (index) {
      console.log(index)
      localStorage.setItem('contentType', 'application/json;charset=UTF-8')
      if (index === 1) {
        loginRequest(this.mainData).then((res) => {
          console.log(res)
          if (res.error) {
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error'
            })
          }
        })
      } else {
        loginRequest(this.subData).then((res) => {
          console.log(res)
          if (res.error !== 0) {
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error'
            })
          } else {

          }
        })
      }
    },
    // tap切换
    switchTab (subLogin) {
      this.isSubLogin = subLogin
    }
  }
}
</script>

<style>
.fr a {
  color: #5a8bff;
}

.fr a:focus,
.fr a:hover {
  text-decoration: none;
  color: #5a8bff;
}

* {
  margin: 0;
  padding: 0;
}

.container {
  width: 810px;
  padding: 5% 0;
  margin: 0 auto !important;
}

.clearfix:after {
  display: block;
  content: "";
  clear: both;
}

.main {
  width: 100%;
  height: 480px;
  background: white;
  color: #333;
  font-size: 16px;
  margin-top: 8px;
}

.main-left {
  float: left;
  width: 43%;
  height: 100%;
  text-align: center;
  background: -webkit-linear-gradient(top, #4564e9, #55b0fd);
  background: -moz-linear-gradient(top, #4564e9, #55b0fd);
  background: linear-gradient(top, #4564e9, #55b0fd);
  line-height: 480px;
}

.main-right {
  float: left;
  width: 57%;
  height: 100%;
  padding: 50px 40px;
  box-sizing: border-box;
}

.main-right-title {
  border-bottom: 1px solid #e4e4e4;
  line-height: 40px;
  text-align: center;
  display: flex;
}

.main-right-title > div {
  flex: 1;
  cursor: pointer;
}

.title-active {
  color: #5a8bff;
  border-bottom: 2px solid #5a8bff;
}

.main-right-content {
  margin-top: 20px;
}

.main-right-content input {
  width: 100%;
  height: 45px;
  border: 1px solid #ccc;
  padding-left: 18px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
  box-sizing: border-box;
}

.content-account div {
  margin-bottom: 18px;
}

.account-login .btn-login {
  width: 160px;
  height: 45px;
  background: #5a8bff;
  color: #fff;
  border: 1px solid #5a8bff;
  cursor: pointer;
  padding-left: none !important;
}

.account-login span {
  display: inline-block;
  line-height: 45px;
  color: #5a8bff;
  cursor: pointer;
}

.mesg-error {
  position: relative;
  background: #ffebeb;
  color: #e4393c;
  border: 1px solid #faccc6;
  padding: 3px 10px 3px 18px;
  line-height: 20px;
  height: auto;
  margin-bottom: 10px;
  font-size: 13px;
  display: none;
}

img {
  vertical-align: middle;
}

input::-webkit-input-placeholder {
  color: #999;
}

.fr {
  float: right;
}
.smart-form input {
  font-size: 16px !important;
}
</style>
