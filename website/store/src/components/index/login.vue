<template>
  <div class="container login-container">
    <!-- <div class="head_logo">
      <img
        :src="imgUrl[0].img"
        alt="微铺宝logo"
        width="200px"
      />
    </div> -->
    <div class="main clearfix">
      <div class="main-left">
        <img
          :src="$imageHost+'/image/admin/hc_new_logo.jpg'"
          alt=""
        />
      </div>
      <div class="main-right">
        <div class="main-right-title">
          <div
            class="title-head "
            :class="{'title-active': !isSubLogin}"
            @click="switchTab(false)"
          >店长登录</div>
          <div
            class="title-head "
            :class="{'title-active': isSubLogin}"
            @click="switchTab(true)"
          >店员登录</div>
        </div>
        <div class="main-right-content ">
          <div
            class="content-zhu content-account"
            data-type="0"
          >
            <div class="mesg-error"></div>
            <div class="smart-form">
              <el-form
                :model="ruleForm"
                status-icon
                :rules="rulesList"
                ref="ruleForm"
                label-width="100px"
                class="demo-ruleForm"
                :key="1"
              >
                <el-form-item
                  label="商家账户"
                  prop="username"
                >
                  <el-input v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item
                  label="门店编码"
                  prop="storeNum"
                  v-if="isSubLogin"
                >
                  <el-input v-model="ruleForm.storeNum"></el-input>
                </el-form-item>
                <el-form-item
                  label="门店员工账户"
                  prop="storeUsername"
                >
                  <el-input v-model="ruleForm.storeUsername"></el-input>
                </el-form-item>
                <el-form-item
                  label="密码"
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
                    @click.native.prevent="onSubmit('ruleForm')"
                    @keyup.enter.native="onSubmit('ruleForm')"
                    class="btn"
                  >{{$t('login_page.login_main')}}</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
      </div>
      <span class="hc">慧策集团出品</span>
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
        callback(new Error('商家账户应为非中文且不能为空'))
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
    var validateStoreNum = (rule, value, callback) => {
      if (!this.userNameReg.test(value)) {
        callback(new Error('门店编码应为非中文且不能为空'))
      } else {
        callback()
      }
    }
    var validateStoreUsername = (rule, value, callback) => {
      if (!this.userNameReg.test(value)) {
        callback(new Error('门店员工账户应为非中文且不能为空'))
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
        password: '',
        storeNum: '',
        storeUsername: '',
        isSubLogin: false
      },
      rules1: {
        username: { validator: validateUserName, trigger: 'blur' },
        password: { validator: validatePassword, trigger: 'blur' },
        storeUsername: { validator: validateStoreUsername, trigger: 'blur' }
      },
      rules2: {
        username: { validator: validateUserName, trigger: 'blur' },
        password: { validator: validatePassword, trigger: 'blur' },
        storeNum: { validator: validateStoreNum, trigger: 'blur' },
        storeUsername: { validator: validateStoreUsername, trigger: 'blur' }
      }
      // subRuleForm: {
      //   username: '',
      //   subUsername: '',
      //   password: '',
      //   isSubLogin: true
      // },
      // subRules: {
      //   username: { validator: validateUserName, trigger: 'blur' },
      //   subUsername: { validator: validateUserName, trigger: 'blur' },
      //   password: { validator: validatePassword, trigger: 'blur' }
      // }
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
    onSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          localStorage.setItem('contentType', 'application/json;charset=UTF-8')
          loginRequest(this.ruleForm).then((res) => {
            // test
            console.log('第一')
            console.log(res)
            if (res.content.storeAuthInfoVo.isOk) {
              document.onkeydown = undefined
              Cookies.set('V-Index-Token', res.content.token)
              localStorage.setItem('V-Username', res.content.storeAccountName)
              localStorage.setItem('V-isSubLogin', this.isSubLogin)
              localStorage.setItem('V-AccountName', res.content.storeAccountName)
              this.$message.success({
                showClose: true,
                message: res.message,
                type: 'success'
              })
              this.$router.push({
                name: 'shopView'
              })
            }
          })
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
  },
  computed: {
    rulesList () {
      if (this.ruleForm.isSubLogin) {
        return this.rules1
      } else {
        return this.rules2
      }
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
  content: '';
  clear: both;
}

.container .main {
  width: 100%;
  background: white;
  color: #333;
  font-size: 16px;
  position: relative;
}

.main-left {
  float: left;
  width: 43%;
  height: 100%;
  text-align: center;

  background: -moz-linear-gradient(top, #4564e9, #55b0fd);
  background: -o-linear-gradient(top, #4564e9, #55b0fd);
  background: -ms-linear-gradient(top, #4564e9, #55b0fd);
  background: linear-gradient(to top, #4564e9, #55b0fd);
  background: -webkit-linear-gradient(top, #4564e9, #55b0fd);
  line-height: 480px;
}

.main-right {
  float: left;
  width: 57%;
  height: 100%;
  padding: 50px 40px;
  box-sizing: border-box;
  position: relative;
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
    width: 100px !important;
    line-height: 14px;
    height: 40px;
    display: flex;
    align-items: center;
  }
  /deep/ .el-form-item__content {
    height: 46px;
    margin-bottom: 10px;
  }
}
.hc {
  position: absolute;
  bottom: 12px;
  // left: 0;
  right: 0;
  font-size: 12px;
  color: #989898;
  text-align: center;
  width: 445px;
}
</style>
