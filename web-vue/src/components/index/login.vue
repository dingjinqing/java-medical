<template>
  <div class="container login-container">
    <div class="head_logo">
      <img
        :src="imgUrl[0].img"
        alt="微铺宝logo"
        width="200px"
      />
    </div>
    <div class="main clearfix">
      <div class="main-left">
        <img
          :src="imgUrl[1].img"
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
            v-if="!isSubLogin"
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
                @click.native.prevent="onSubmit(1)"
                @keyup.enter.native="onSubmit(1)"
                class="btn"
              >{{$t('login_page.login_main')}}</el-button>
            </div>
          </div>
          <div
            class="content-zi content-account"
            data-type="1"
            v-if="isSubLogin"
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
                  type="password"
                  v-model="subData.password"
                  :placeholder="$t('login_page.password')"
                ></el-input>
              </div>
              <el-button
                type="primary"
                class="btn"
                @click.native.prevent="onSubmit(2)"
                @keyup.enter.native="onSubmit(2)"
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
      isSubLogin: false,
      imgUrl: [
        {
          img: this.$imageHost + '/image/admin/shop_logoswe.png'
        },
        {
          img: this.$imageHost + '/image/admin/login_new_new.png'
        }
      ]
    }
  },

  created () {
    var _self = this
    document.onkeydown = function (e) {
      let key = ''
      if (window.event === undefined) {
        key = e.keyCode
      } else {
        key = window.event.keyCode
      }
      if (key === 13) {
        if (_self.isSubLogin === true) {
          _self.onSubmit(2)
        } else {
          _self.onSubmit(1)
        }
      }
    }
  },

  mounted () {
    this.langDefault()
    localStorage.setItem('contentType', 'application/json;charset=UTF-8')
    // window.addEventListener('keyup', this.keyupEnter, false)
  },
  methods: {
    // 表单校验
    JudgementForm (index) {
      let userNameReg = /^[^\u4e00-\u9fa5]{0,}$/
      let passwordReg = /^[^\u4e00-\u9fa5][\S+$]{5,16}$/
      if (index === 1) {
        if (!userNameReg.test(this.mainData.username)) {
          this.$message.success({
            message: '主账号用户名应为非中文且不能为空',
            type: 'warning'
          })
          return false
        }
        if (!passwordReg.test(this.mainData.password)) {
          this.$message.success({
            message: '密码应为6至16位非中文且不能为空',
            type: 'warning'
          })
          return false
        }
      } else {
        if (!userNameReg.test(this.subData.username)) {
          this.$message.success({
            message: '主账号用户名应为非中文且不能为空',
            type: 'warning'
          })
          return false
        }
        if (!userNameReg.test(this.subData.subUsername)) {
          this.$message.success({
            message: '子账号用户名或手机号应为非中文且不能为空',
            type: 'warning'
          })
          return false
        }
        if (!passwordReg.test(this.subData.password)) {
          this.$message.success({
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
      localStorage.setItem('contentType', 'application/json;charset=UTF-8')
      if (index === 1) {
        loginRequest(this.mainData).then((res) => {
          // test
          console.log('第一')
          console.log(res)
          if (res.error === 0) {
            document.onkeydown = undefined
            Cookies.set('V-Index-Token', res.content.token)
            localStorage.setItem('V-Username', res.content.userName)
            localStorage.setItem('V-loginType', 0)
            localStorage.setItem('V-isSubLogin', this.isSubLogin)
            console.log('子账户登录')
            console.log(this.isSubLogin)
            localStorage.setItem('V-AccountName', res.content.accountName)
            console.log(this.$message)
            this.$message.success({
              showClose: true,
              message: res.message,
              type: 'success'
            })
            console.log(1111)
            this.$router.push({
              name: 'shopMain'
            })
            console.log(2222)
          }
          // test
        })
      } else {
        loginRequest(this.subData).then((res) => {
          console.log('第二')
          if (res.error === 0) {
            document.onkeydown = undefined
            localStorage.setItem('V-loginType', 0)
            Cookies.set('V-Index-Token', res.content.token)
            localStorage.setItem('V-Username', res.content.userName)
            localStorage.setItem('V-AccountName', res.content.accountName)
            localStorage.setItem('V-isSubLogin', this.isSubLogin)
            console.log('子账户登录')
            console.log(this.isSubLogin)
            this.$message.success({
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

  background: -moz-linear-gradient(top, #4564e9, #55b0fd);
  background: -o-linear-gradient(top, #4564e9, #55b0fd);
  background: -ms-linear-gradient(top, #4564e9, #55b0fd);
  background: linear-gradient(top, #4564e9, #55b0fd);
  background: -webkit-linear-gradient(top, #4564e9, #55b0fd);
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

.login-container /deep/ .el-button {
  padding: 12px 20px;
}
/*作用于组件内部的样式，可以全局覆盖，但是要添加页面类名隔离，避免污染全局组件，也可以使用deep修复。因影响到小程序版本页面的组件*/
</style>
<!--/*作用于组件内部的样式，可以全局覆盖，但是要添加页面类名隔离，避免污染全局组件，也可以使用deep修复。因影响到小程序版本页面的组件*/-->
