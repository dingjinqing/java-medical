<template>
  <div class="header">
    <div class="logo">
      <img :src="imageUrlData[0].image_1">
    </div>
    <div
      class="nav2"
      v-if="user_flag"
    >
      <div class="f_div">
        <img
          :src="imageUrlData[1].image_2"
          width="30px"
          height="30px"
          style="border: 1px solid #ddd;"
        >
        <span style="display:inline-block;white-space:normal;height:82px;">{{username}}</span>
        <img
          :src="imageUrlData[2].image_3"
          class="head_down"
        >
        <div class="head_list">
          <a
            @mouseenter="con_enter(index)"
            @mouseleave="con_leave(index)"
            :class="item.login_active"
            v-for="(item,index) in loginData_show"
            :key="index"
            @click="handleLogin(index)"
          >{{item.title}}</a>
        </div>
      </div>

    </div>
    <div
      class="nav2"
      v-else
    >
      <ul class="login_class">
        <li>
          <div
            class="login"
            @mouseenter="login_enter()"
            @mouseleave="login_leave()"
            :class='lo_class'
            @click="to_log_rej_page('login')"
          >{{$t('login_reg.login')}}</div>
        </li>
        <li>
          <div
            class="reg"
            :class='rej_class'
            @mouseenter="rej_enter()"
            @mouseleave="rej_leave()"
            @click="to_log_rej_page('rej')"
          >{{$t('login_reg.rej')}}</div>
        </li>
      </ul>
    </div>

    <div class="nav3">
      <div
        class="f_div"
        :style="lang_with"
      >
        <span>{{$t('messages.lang')}}</span>
        <img
          :src="imageUrlData[3].image_4"
          class="head_down"
        >
        <div class="head_list_lang">
          <div
            class="lang_c"
            target="_blank"
            @mouseenter="lang_enter(index)"
            @mouseleave="lang_leave(index)"
            @click="handleChangeLang(index)"
            :class="item.login_active"
            v-for="(item,index) in langData_show"
            :key="index"
          >{{item.show_lang}}</div>
        </div>
      </div>

    </div>
    <div class="nav">
      <ul>
        <li
          :class="[activeIndex == 1?'active':'',mar_class]"
          @click="handlenav(1)"
        ><a>{{$t('messages.index_nav_main')}}</a></li>
        <li
          :class="[activeIndex == 2?'active':'',mar_class]"
          @click="handlenav(2)"
        ><a>{{$t('messages.index_nav_new')}}</a></li>
        <li
          :class="[activeIndex == 3?'active':'',mar_class]"
          @click="handlenav(3)"
        ><a>{{$t('messages.index_nav_forum')}}</a></li>
        <li
          :class="[activeIndex == 4?'active':'',mar_class]"
          @click="handlenav(4)"
        ><a>{{$t('messages.index_nav_link')}}</a></li>
        <li
          :class="[activeIndex == 5?'active':'',mar_class]"
          @click="handlenav(5)"
        ><a>{{$t('messages.index_nav_apply')}}</a></li>
      </ul>
    </div>
  </div>
</template>
<script>
import Cookies from 'js-cookie'
import { loginRequestOut, isloginRequest } from '@/api/index/login.js'
import { loadLanguageAsync } from '@/i18n/i18n.js'
export default {
  data () {
    return {
      mar_class: '',
      activeIndex: 1,
      lang_with: 'width:43px',
      loginData_show: [],
      loginData_cn: [
        { title: '进入后台', login_active: '', a_href: '' },
        { title: '退出登录', login_active: '', a_href: '' }
      ],
      loginData_en: [
        { title: 'Enter the background', login_active: '', a_href: '' },
        { title: 'Log out', login_active: '', a_href: '' }
      ],
      langData_show: [],
      langData_cn: [
        { lang: 'en', login_active: '', show_lang: 'English' },
        { lang: 'cn', login_active: '', show_lang: '中文' }
      ],
      langData_en: [
        { lang: 'en', login_active: '', show_lang: 'English' },
        { lang: 'cn', login_active: '', show_lang: 'Chinese' }
      ],
      imageUrlData: [
        { image_1: this.$imageHost + '/image/admin/official/head_logo.png' },
        { image_2: this.$imageHost + '/image/admin/head_icon.png' },
        { image_3: this.$imageHost + '/image/admin/official/blue_down.png' },
        { image_4: this.$imageHost + '/image/admin/official/blue_down.png' }
      ],
      lo_class: '',
      rej_class: '',
      user_flag: '',
      username: ''
    }
  },
  watch: {
    $route (to) {
      console.log(to)
      if (to.name === 'newsList') {
        this.navshow()
      }
    }
  },
  mounted () {
    this.$http.$on('to_rej', () => {
      this.navshow()
    })
    this.langDefault()
    this.navshow()
    console.log(this.$router.history.current.name)
    // 初始化登录
    this.judgeuserinfo()
  },
  methods: {
    // 初始化登录
    judgeuserinfo () {
      isloginRequest().then((res) => {
        console.log(res)
        if (res.error !== 0) {
          Cookies.remove('V-Token')
          localStorage.removeItem('V-Username')
          this.user_flag = false
        } else {
          if (localStorage.getItem('V-Username')) {
            this.username = localStorage.getItem('V-Username')
            this.user_flag = true
          } else {
            Cookies.remove('V-Token')
            localStorage.removeItem('V-Username')
            this.user_flag = false
          }
        }
      })
    },
    // 初始化顶部导航
    navshow () {
      let that = this
      let current = this.$router.history.current.name
      console.log(this.$router)
      switch (current) {
        case 'indexHome':
          that.activeIndex = 1
          break
        case 'indexHomeOntrial':
          that.activeIndex = 5
          break
        case 'aboutUs':
          that.activeIndex = 4
          break
        case 'newsList':
          that.activeIndex = 2
          break
      }
    },
    // 进入后台以及推出登录
    handleLogin (index) {
      console.log('进入后台')
      if (index === 0) {
        this.$router.push({
          name: 'shopMain'
        })
      } else {
        loginRequestOut().then((res) => {
          console.log(res)
          if (res.error === 0) {
            Cookies.remove('V-Token')
            localStorage.removeItem('V-Username')
            this.user_flag = false
          } else {
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error'
            })
          }
        })
      }
    },
    // 导航栏子元素点击
    handlenav (index) {
      switch (index) {
        case 1:
          this.activeIndex = 1
          this.$router.push({
            path: '/'
          })
          break
        case 2:
          this.activeIndex = 2
          this.$router.push({
            name: 'newsList'
          })
          break
        case 3:
          this.activeIndex = 3
          window.location.href = 'http://bbs.weipubao.cn/'
          // http://bbs.weipubao.cn/
          break
        case 4:
          this.activeIndex = 4
          this.$router.push({
            name: 'aboutUs'
          })
          break
        case 5:
          this.activeIndex = 5
          this.$router.push({
            name: 'indexHomeOntrial'
          })
          break
      }
    },
    // 鼠标划入事件
    con_enter (index) {
      this.loginData_cn[index].login_active = 'login_active'
      this.loginData_en[index].login_active = 'login_active'
    },
    lang_enter (index) {
      this.langData_cn[index].login_active = 'login_active'
      this.langData_en[index].login_active = 'login_active'
    },
    login_enter () {
      this.lo_class = 'lo_class'
    },
    rej_enter () {
      this.rej_class = 'rej_class'
    },
    // 鼠标划出事件
    con_leave (index) {
      this.loginData_cn[index].login_active = ''
      this.loginData_en[index].login_active = ''
    },
    lang_leave (index) {
      this.langData_cn[index].login_active = ''
      this.langData_en[index].login_active = ''
    },
    login_leave () {
      this.lo_class = ''
    },
    rej_leave () {
      this.rej_class = ''
    },
    // 语言选项点击
    handleChangeLang (index) {
      switch (index) {
        case 0:
          // this.$i18n.locale = this.langData_cn[index].lang
          this.lang_with = 'width:63px'
          this.langData_show = this.langData_en
          this.loginData_show = this.loginData_en
          this.mar_class = 'mar_class'

          localStorage.setItem('WEPUBAO_LANGUAGE', 'en_US')
          break
        case 1:
          // this.$i18n.locale = this.langData_cn[index].lang
          this.lang_with = 'width:43px'
          this.langData_show = this.langData_cn
          this.loginData_show = this.loginData_cn
          this.mar_class = ''
          localStorage.setItem('WEPUBAO_LANGUAGE', 'zh_CN')

          break
      }
      console.log(1)
      console.log(this.langData_cn[index].lang)
      loadLanguageAsync(this.langData_cn[index].lang).then(() => {
        this.$store.commit('TOCHANGE_LANGCURRENT', this.langData_cn[index].lang)
        this.$http.$emit('lang_change', index)
      })
    },
    // 登录注册跳转
    to_log_rej_page (res) {
      if (res === 'login') {
        this.$router.push({
          path: '/index/login'
        })
      } else {
        this.$router.push({
          path: '/index/home/ontrial'
        })
      }
    }
  }
}
</script>
<style scoped>
a {
  color: #333;
  display: block;
  text-decoration: none;
  font-size: 14px;
}
.header {
  /* position: absolute; */
  padding: 0px 100px;
  width: 100%;
  min-width: 100%;
  box-sizing: border-box;
  height: 82px;
  background: #fff !important;
}
.logo {
  float: left;
  padding: 10px 0;
  box-sizing: border-box;
  height: 82px;
}
.nav2 {
  float: right;
  height: 100%;
}
.nav3 {
  float: right;
  height: 100%;
  margin-right: 20px;
}
.turn_button {
  float: left;
  height: 100%;
}
.head_list {
  position: absolute;
  width: 160px;
  background: #fff;
  -webkit-box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  -moz-box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  color: #333;
  text-align: center;
  z-index: 2;
  display: none;
  top: 80px;
  z-index: 10000;
}

.head_list_lang {
  position: absolute;
  width: 160px;
  background: #fff;
  -webkit-box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  -moz-box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  color: #333;
  text-align: center;
  z-index: 2;
  display: none;
  top: 80px;
  z-index: 10000;
}
.nav2:hover .head_list {
  display: block;
}
.nav3:hover .head_list_lang {
  display: block;
}
.f_div {
  display: flex;
  align-items: center;
  justify-content: space-between;
  /* width: 84px; */
  height: 100%;
}
.nav2 > div {
  font-size: 14px;
  color: #5a8bff;
  line-height: 80px;
  cursor: pointer;
  position: relative;
}
.nav2 div img:first-child {
  -webkit-border-radius: 100%;
  -moz-border-radius: 100%;
  border-radius: 100%;
}
.nav3 > div {
  font-size: 14px;
  color: #5a8bff;
  line-height: 80px;
  cursor: pointer;
  position: relative;
}
.nav3 div img:first-child {
  -webkit-border-radius: 100%;
  -moz-border-radius: 100%;
  border-radius: 100%;
}
.nav {
  float: right;
  margin-right: 10px;
  height: 100%;
}
.nav ul {
  height: 82px;
  list-style: none;
  margin: 0;
  padding: 0;
}
.nav ul li {
  margin-right: 35px;
  line-height: 80px;
  cursor: pointer;
}
ul li {
  list-style: none;
  float: left;
}
.nav ul li a:hover {
  color: #5a8bff;
}
.nav ul li.active a {
  border-bottom: 2px solid #5a8bff;
}
.nav2 .head_list a {
  line-height: 40px;
  height: 40px;
  width: 100%;
  text-align: center;
  border-radius: 3px;
}
.lang_c {
  line-height: 40px;
  height: 40px;
  width: 100%;
  text-align: center;
  border-radius: 3px;
}
.login_active {
  background-color: #f8f8f8;
  color: #5a8bff;
}
.login_class {
  overflow: hidden;
}
.nav2 ul li {
  margin: 0 0 0 10px;
  line-height: 80px;
  float: left;
}
.login,
.reg {
  display: inline-block;
  width: 66px;
  height: 26px;
  line-height: 26px;
  text-align: center;
  border-radius: 3px;
}
.login {
  background: #5a8bff;
  color: #fff;
  font-size: 14px;
}
.reg {
  border: 1px solid #b4b4b4;
  color: #999;
  font-size: 14px;
}
.lo_class {
  background-color: #447af9;
  cursor: pointer;
}
.rej_class {
  color: #447af9;
  border-color: #447af9;
  cursor: pointer;
}
.mar_class {
  margin-right: 12px !important;
}
</style>
