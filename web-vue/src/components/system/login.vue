<template>
  <div class="sys_container">
    <div class="head_logo">
      <img
        :src="imgUrl[0].img"
        alt="微铺宝logo"
        width="200px"
      />
    </div>
    <div class="main clearfix">
      <div class="main-left">
        <img :src="imgUrl[1].img" />
      </div>
      <div class="main-right">
        <div class="well">
          <div class="smart-form">
            <div class="title">{{$t('systemLogin.login')}}
            </div>
            <div class="fildset">

              <div class="section_1">
                <el-form
                  :model="ruleForm"
                  status-icon
                  :rules="rules"
                  ref="ruleForm"
                  label-width="100px"
                  class="demo-ruleForm"
                  :key="1"
                >
                  <el-form-item prop="username">
                    <div class="label">{{$t('systemLogin.username')}}</div>
                    <el-input
                      suffix-icon="fa fa-user"
                      v-model="ruleForm.username"
                    ></el-input>
                  </el-form-item>
                  <el-form-item prop="password">
                    <div class="label">{{$t('systemLogin.password')}}</div>
                    <el-input
                      suffix-icon="fa fa-lock"
                      show-password
                      v-model="ruleForm.password"
                    ></el-input>
                  </el-form-item>
                </el-form>
              </div>
              <div class="footer">
                <el-button
                  type="primary"
                  class="button"
                  @click.native.prevent="onSubmit()"
                  @keyup.enter.native="onSubmit()"
                >{{$t('systemLogin.login')}}</el-button>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { loginRequest } from '@/api/system/login.js'
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
      placeholder_username: '请输入用户名或者手机号',
      placeholder_password: '请输入密码',
      flag: true,
      imgUrl: [
        {
          img: this.$imageHost + '/image/admin/shop_logoswe.png'
        },
        {
          img: this.$imageHost + '/image/admin/login_back.png'
        }
      ],
      ruleForm: {
        username: '',
        password: ''
      },
      rules: {
        username: { validator: validateUserName, trigger: 'blur' },
        password: { validator: validatePassword, trigger: 'blur' }
      }
    }
  },
  mounted () {
    this.langDefault()
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
        _self.onSubmit()
      }
    }
    console.log(this)
  },
  methods: {
    // 表单提交
    onSubmit () {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          localStorage.setItem('contentType', 'application/json;charset=UTF-8')
          console.log(this.ruleForm)
          loginRequest(this.ruleForm).then((res) => {
            console.log(res)
            if (res.error !== 0) {
              this.$message.success({
                showClose: true,
                message: res.message,
                type: 'error'
              })
            } else {
              document.onkeydown = undefined
              localStorage.setItem('V-loginType', 1)
              Cookies.set('V-System-Token', res.content.token, { expires: 1 / 48 })
              localStorage.setItem('System-Username', res.content.userName)
              this.$message.success({
                showClose: true,
                message: res.message,
                type: 'success'
              })
              this.$router.push({
                name: 'overviewMain'
              })
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
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

.sys_container {
  width: 810px;
  padding: 1% 0;
  margin: 0 auto !important;
}

.clearfix:after {
  display: block;
  content: "";
  clear: both;
}

.sys_container .main {
  width: 100%;
  height: 480px;
  background: white;
  color: #333;
  font-size: 16px;
  margin-top: 8px;
}

.sys_container .main-left {
  float: left;
  width: 43%;
  height: 100%;
  text-align: center;
  background: -webkit-linear-gradient(top, #4564e9, #55b0fd);
  background: -moz-linear-gradient(top, #4564e9, #55b0fd);
  background: linear-gradient(top, #4564e9, #55b0fd);
  line-height: 480px;
}

.sys_container .main-right {
  float: left;
  width: 57%;
  height: 100%;
  padding: 50px 40px;
  box-sizing: border-box;
}
.sys_container .well {
  border: 1px solid #ddd;
  min-height: 20px;
  margin-bottom: 20px;
}
.sys_container .smart-form {
  color: #666;
  position: relative;
}
.sys_container .title {
  padding: 15px 13px;
  margin: 0;
  background: rgba(248, 248, 248, 0.9);
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
  font-size: 16px;
  font-weight: 300;
  color: #232323;
}
.sys_container .fildset {
  padding: 25px 0 5px;
  position: relative;
}
.sys_container .section_1 {
  margin-bottom: 15px;
  position: relative;
}
.sys_container .label {
  margin-bottom: 6px;
  line-height: 19px;
  font-weight: 400;
  font-size: 13px;
  color: #333;
  text-align: left;
}
.sys_container .footer {
  padding: 14px 14px 15px;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  background: rgba(248, 248, 248, 0.9);
  overflow: hidden;
}

.sys_container .username_class {
  border-color: #5d98cc;
}
.sys_container .password_class {
  border-color: #5d98cc;
}
.sys_container .password {
  cursor: pointer;
}
.sys_container .button {
  float: right;
}
</style>
<style lang="scss" scoped>
.demo-ruleForm {
  padding: 0 14px;
  /deep/ .el-form-item__content {
    margin-left: 0 !important;
  }
}
</style>
