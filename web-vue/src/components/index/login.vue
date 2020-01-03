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
          :src="$imageHost+'/image/admin/2020_logo.png'"
          alt=""
        />
      </div>
      <div class="main-right">
        <div class="main-right-title">
          <div
            class="title-head "
            :class="{'title-active': !isSubLogin}"
            @click="switchTab(false)"
          >{{$t('login_page.main_name')}}</div>
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
              <el-form
                :model="ruleForm"
                status-icon
                :rules="rules"
                ref="ruleForm"
                label-width="100px"
                class="demo-ruleForm"
                :key="1"
              >
                <el-form-item
                  :label="$t('login_page.main_name')"
                  prop="username"
                >
                  <el-input v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item
                  :label="$t('login_page.password')"
                  prop="password"
                >
                  <el-input
                    show-password
                    v-model="ruleForm.password"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    @click.native.prevent="onSubmit(1,'ruleForm')"
                    @keyup.enter.native="onSubmit(1,'ruleForm')"
                    class="btn"
                  >{{$t('login_page.login_main')}}</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
          <div
            class="content-zi content-account"
            data-type="1"
            v-if="isSubLogin"
          >
            <div class="mesg-error"></div>
            <div class="smart-form">

              <el-form
                :model="subRuleForm"
                status-icon
                :rules="subRules"
                ref="subRuleForm"
                label-width="100px"
                class="demo-ruleForm"
                :key="2"
              >
                <el-form-item
                  :label="$t('login_page.main_name')"
                  prop="username"
                >
                  <el-input v-model="subRuleForm.username"></el-input>
                </el-form-item>
                <el-form-item
                  :label="$t('login_page.subAccount')"
                  prop="subUsername"
                >
                  <el-input v-model="subRuleForm.subUsername"></el-input>
                </el-form-item>
                <el-form-item
                  :label="$t('login_page.password')"
                  prop="password"
                >
                  <el-input
                    show-password
                    v-model="subRuleForm.password"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    @click.native.prevent="onSubmit(2,'subRuleForm')"
                    @keyup.enter.native="onSubmit(2,'subRuleForm')"
                    class="btn"
                  >{{$t('login_page.login_main')}}</el-button>
                </el-form-item>
              </el-form>
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
    var validateUserName = (rule, value, callback) => {
      console.log(this.userNameReg, value)
      if (!this.userNameReg.test(value)) {
        callback(new Error('主账号用户名应为非中文且不能为空'))
      } else {
        callback()
      }
    }
    var validatePassword = (rule, value, callback) => {
      if (!this.passwordReg.test(value)) {
        callback(new Error('密码应为6至16位非中文且不能为空'))
      } else {
        callback()
      }
    }
    return {
      userNameReg: /^[^\u4e00-\u9fa5]{1,}$/,
      passwordReg: /^[^\u4e00-\u9fa5][\S+$]{5,16}$/,
      isSubLogin: false,
      imgUrl: [
        {
          img: this.$imageHost + '/image/admin/shop_logoswe.png'
        },
        {
          img: this.$imageHost + '/image/admin/login_new_new.png'
        }
      ],
      ruleForm: {
        username: '',
        password: ''
      },
      rules: {
        username: { validator: validateUserName, trigger: 'blur' },
        password: { validator: validatePassword, trigger: 'blur' }
      },
      subRuleForm: {
        username: '',
        subUsername: '',
        password: ''
      },
      subRules: {
        username: { validator: validateUserName, trigger: 'blur' },
        subUsername: { validator: validateUserName, trigger: 'blur' },
        password: { validator: validatePassword, trigger: 'blur' }
      }
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
          _self.onSubmit(2, 'subRuleForm')
        } else {
          _self.onSubmit(1, 'ruleForm')
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
    onSubmit (index, formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          localStorage.setItem('contentType', 'application/json;charset=UTF-8')
          if (index === 1) {
            loginRequest(this.ruleForm).then((res) => {
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
        } else {
          console.log('error submit!!')
          return false
        }
      })
      // let flag = this.JudgementForm(index)
      // console.log(this.flag)
      // if (flag === false) return
    },
    // tap切换
    switchTab (subLogin) {
      //  Object.keys(this.ruleForm).forEach(item=>{
      //    this.ruleForm[item] = ''
      //  })
      //  Object.keys(this.subRuleForm).forEach(item=>{
      //    this.ruleForm[item] = ''
      //  })
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
  width: 100%;
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
</style>
<style lang="scss" scoped>
.demo-ruleForm {
  /deep/ .el-form-item__label {
    width: 60px !important;
    line-height: 14px;
    height: 40px;
    display: flex;
    align-items: center;
  }
  /deep/ .el-form-item__content {
    height: 46px;
    margin-bottom: 10px;
    margin-left: 60px !important;
  }
}
</style>
