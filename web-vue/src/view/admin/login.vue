<template>
  <div class="container">
    <div class="head_logo">
      <img v-bind:src="'http://'+imageDomain + '/static/image/admin/shop_logoswe.png'" alt="微铺宝logo" width="200px"/>
    </div>
    <div class="main clearfix">
      <div class="main-left">
        <img v-bind:src="'http://'+imageDomain + '/static/image/admin/login_new_new.png'" alt=""/>
      </div>
      <div class="main-right">
        <div class="main-right-title">
          <div class="title-head " :class="{'title-active': !isSubLogin}" @click="switchTab(false)">登录</div>
          <div class="title-head " :class="{'title-active': isSubLogin}" @click="switchTab(true)">子账号登录</div>
        </div>
        <div class="main-right-content ">
          <div class="content-zhu content-account" data-type="0" v-show="!isSubLogin">
            <div class="mesg-error"></div>
            <form name="loginForm" method="post" id="loginForm" class="smart-form client-form"
                  @submit.prevent="onSubmit">
              <div class="account-user"><input type="text" v-model="mainForm.username" placeholder="主账号用户名"/></div>
              <div class="account-pawd"><input type="password" v-model="mainForm.password" placeholder="密码"></div>
              <div class="account-login clearfix">
                <input type="button" class="one-login to-login btn-login" @click="onSubmit" value="登录">
              </div>
            </form>
          </div>
          <div class="content-zi content-account"  data-type="1" v-show="isSubLogin">
            <div class="mesg-error"></div>
            <form name="loginForm" method="post" id="loginFormSub" class="smart-form client-form"
                  @submit.prevent="onSubmit">
              <div class="account-name"><input type="text" name="username" v-model="subForm.username"
                                               placeholder="主账号用户名"/></div>
              <div class="account-user"><input type="text" name="sub_username" v-model="subForm.subUsername"
                                               placeholder="子账号用户名/手机号"/></div>
              <div class="account-pawd"><input type="password" name="password" v-model="subForm.password"
                                               placeholder="密码"/></div>
              <div class="account-login clearfix">
                <input type="button" class="child-login to-login btn-login" @click="onSubmit" value="登录">
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import env from '@/config/env'
import api from '@/util/api'

export default {
  name: 'adminLogin',
  data () {
    return {
      imageDomain: env.imageDomain,
      mainForm: {
        username: '',
        password: '',
        isSubLogin: false
      },
      subForm: {
        username: '',
        password: '',
        subUsername: '',
        isSubLogin: true
      },
      isSubLogin: false
    }
  },
  mounted () {
    console.log('login mounted')
  },
  methods: {
    switchTab (subLogin) {
      this.isSubLogin = subLogin
    },
    onSubmit () {
      var param = this.isSubLogin ? this.subForm : this.mainForm
      api('/admin/login', function (d) {
        if (d && d.error === 0) {
          window.layer.msg('登录成功')
        } else if (d && d.error !== 0) {
          window.layer.msg(d.message)
        }
      }, param)
      console.log('isSubLogin', this.isSubLogin)
    }
  }
}
</script>

<style lang="less">
  @import '../../style/mixin';
  @import '../../style/admin/base';
  @import '../../style/admin/common';
  .fr a {
    color: #5a8bff;
  }

  .fr a:focus, .fr a:hover {
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
  }

  .clearfix:after {
    display: block;
    content: '';
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

  .main-left > img {
    margin-top: 46px;
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
</style>
