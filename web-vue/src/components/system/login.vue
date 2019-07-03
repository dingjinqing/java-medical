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
          src="http://mpdev.weipubao.cn//image/admin/login_back.png"
          alt=""
        />
      </div>
      <div class="main-right">
        <div class="well">
          <div class="smart-form">
            <div class="title">{{$t('systemLogin.login')}}
            </div>
            <div class="fildset">

              <div class="section_1">
                <div class="label">{{$t('systemLogin.username')}}</div>
                <div>
                  <el-input
                    suffix-icon="fa fa-user"
                    v-model="mainData.username"
                    :placeholder="placeholder_username"
                  >
                  </el-input>
                </div>
              </div>
              <div class="section_1">
                <div class="label">{{$t('systemLogin.password')}}</div>
                <div>
                  <el-input
                    suffix-icon="fa fa-lock"
                    v-model="mainData.password"
                    :placeholder="placeholder_password"
                  >
                  </el-input>
                </div>
              </div>
            </div>
            <div class="footer">
              <el-button
                type="primary"
                class="button"
                @click="onSubmit()"
              >{{$t('systemLogin.login')}}</el-button>

            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { loginRequest } from '@/api/system/login.js'
export default {
  data () {
    return {
      mainData: {
        username: '',
        password: ''
      },
      placeholder_username: '请输入用户名或者手机号',
      placeholder_password: '请输入密码',
      flag: true
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    // 表单校验
    JudgementForm (index) {
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en_US') {
        if (!this.mainData.username) {
          this.$message({
            showClose: true,
            message: 'Please enter your username or mobile phone number',
            type: 'warning'
          })
          this.flag = false
          return
        }
        if (!this.mainData.password) {
          this.$message({
            showClose: true,
            message: 'Please input a password',
            type: 'warning'
          })
          this.flag = false
        }
        return
      }
      if (!this.mainData.username) {
        this.$message({
          showClose: true,
          message: '请输入用户名或者手机号',
          type: 'warning'
        })
        this.flag = false
        return
      }
      if (!this.mainData.password) {
        this.$message({
          showClose: true,
          message: '请输入密码',
          type: 'warning'
        })
        this.flag = false
      }
    },
    // 表单提交
    onSubmit () {
      this.JudgementForm()
      if (this.flag === false) return
      localStorage.setItem('contentType', 'application/json;charset=UTF-8')
      loginRequest(this.mainData).then((res) => {
        console.log(res)
      })
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
.well {
  border: 1px solid #ddd;
  min-height: 20px;
  margin-bottom: 20px;
}
.smart-form {
  color: #666;
  position: relative;
}
.title {
  padding: 15px 13px;
  margin: 0;
  background: rgba(248, 248, 248, 0.9);
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
  font-size: 16px;
  font-weight: 300;
  color: #232323;
}
.fildset {
  padding: 25px 14px 5px;
  position: relative;
}
.section_1 {
  margin-bottom: 15px;
  position: relative;
}
.label {
  margin-bottom: 6px;
  line-height: 19px;
  font-weight: 400;
  font-size: 13px;
  color: #333;
  text-align: left;
}
.footer {
  padding: 14px 14px 15px;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  background: rgba(248, 248, 248, 0.9);
  overflow: hidden;
}

.username_class {
  border-color: #5d98cc;
}
.password_class {
  border-color: #5d98cc;
}
.password {
  cursor: pointer;
}
.button {
  float: right;
}
</style>
