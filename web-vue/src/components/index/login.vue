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
              <div class="account-user">
                <el-input
                  type="text"
                  v-model="mainData.username"
                  :placeholder="$t('login_page.main_name')"
                >
                </el-input>
              </div>
              <div class="account-pawd">
                <el-input
                  type="password"
                  v-model="mainData.password"
                  :placeholder="$t('login_page.password')"
                ></el-input>
              </div>
              <el-button
                type="primary"
                @click="onSubmit(1)"
                class="btn"
              >{{$t('login_page.login_main')}}</el-button>
            </div>
          </div>
          <div
            class="content-zi content-account"
            data-type="1"
            v-show="isSubLogin"
          >
            <div class="mesg-error"></div>
            <div class="smart-form">
              <div class="account-name">
                <el-input
                  v-model="subData.username"
                  :placeholder="$t('login_page.main_name')"
                ></el-input>
              </div>
              <div class="account-user">
                <el-input
                  v-model="subData.subUsername"
                  :placeholder="$t('login_page.z_phone')"
                ></el-input>
              </div>
              <div class="account-pawd">
                <el-input
                  v-model="subData.password"
                  :placeholder="$t('login_page.password')"
                ></el-input>
              </div>
              <el-button
                type="primary"
                class="btn"
                @click="onSubmit(2)"
                :value="$t('login_page.login_main')"
              >{{$t('login_page.login_main')}}</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { loginRequest } from '@/api/index/login.js'
import Cookies from 'js-cookie'
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
      isSubLogin: false

    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    // 表单校验
    JudgementForm (index) {
      let userNameReg = /^[^\u4e00-\u9fa5][\S+$]{0,}$/
      let passwordReg = /^[^\u4e00-\u9fa5][\S+$]{5,16}$/
      if (index === 1) {
        if (!userNameReg.test(this.mainData.username)) {
          this.$message({
            message: '主账号用户名应为非中文且不能为空',
            type: 'warning'
          })
          return false
        }
        if (!passwordReg.test(this.mainData.password)) {
          this.$message({
            message: '密码应为6至16位非中文且不能为空',
            type: 'warning'
          })
          return false
        }
      } else {
        if (!userNameReg.test(this.subData.username)) {
          this.$message({
            message: '主账号用户名应为非中文且不能为空',
            type: 'warning'
          })
          return false
        }
        if (!userNameReg.test(this.subData.subUsername)) {
          this.$message({
            message: '子账号用户名或手机号应为非中文且不能为空',
            type: 'warning'
          })
          return false
        }
        if (!passwordReg.test(this.subData.password)) {
          this.$message({
            message: '密码应为6至16位非中文且不能为空',
            type: 'warning'
          })
          return false
        }
      }
    },
    onSubmit (index) {
      let flag = this.JudgementForm(index)
      // console.log(this.flag)
      if (flag === false) return
      // console.log(index)
      localStorage.setItem('contentType', 'application/json;charset=UTF-8')
      if (index === 1) {
        loginRequest(this.mainData).then((res) => {
          // test
          if (res.error !== 0) {
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error'
            })
          } else {
            Cookies.set('V-Token', res.content.token, { expires: 1 / 48 })
            localStorage.setItem('V-Username', res.content.userName)
            this.$message({
              showClose: true,
              message: res.message,
              type: 'success'
            })
            this.$router.push({
              name: 'shopMain'
            })
          }
          // test
        })
      } else {
        loginRequest(this.subData).then((res) => {
          // console.log(res)
          if (res.error !== 0) {
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error'
            })
          } else {
            Cookies.set('V-Token', res.content.token, { expires: 1 / 48 })
            localStorage.setItem('V-Username', res.content.userName)
            this.$message({
              showClose: true,
              message: res.message,
              type: 'success'
            })
            this.$router.push({
              name: 'shopMain'
            })
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

<style scoped>
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

.container .main {
  width: 100%;
  background: white;
  color: #333;
  font-size: 16px;
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
.btn {
  width: 160px;
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
<style>
.el-button {
  padding: 12px 20px !important;
}
</style>
