<template>
  <div class="header_container">
    <div class="left">
      <img :src="imageUrl[0].img_1">
    </div>
    <div class="header_nav">
      <div
        v-for="(item,index) in header_navData"
        :key="index"
        :class="{active_bg: $route.matched[0].meta.title === item.title}"
        @click="headerNavClick(item.routeName)"
        class="wrapper"
      >
        <img
          :src="item.img"
          alt=""
        >
        <span :class="{active_bg: $route.matched[0].meta.title === item.title}">{{item.title}}</span>
      </div>
    </div>

    <div
      class="middle"
      @mouseenter="user_enter()"
      @mouseleave="user_leave()"
    >
      <div class="account">
        <div class="menu">
          <div class="menu_main">
            <span>
              <label>
                {{this.username}}
              </label>
              <img :src="imageUrl[1].img_2">
            </span>
          </div>
        </div>
      </div>
      <div
        class="log-menu"
        v-show="log_menu_show"
        :class="menu_width"
      >
        <div
          v-for="(item,index) in hiddle_menu_list"
          :key="index"
          @mouseenter="user_enter(index)"
          @mouseleave="user_leave(index)"
          @click="handle_user_list(index)"
          :class="changeColorIndex === index?'changeColor':''"
        >
          {{item}}
        </div>
        <img :src="imageUrl[2].img_3">
      </div>
    </div>

  </div>
</template>
<script>
import Vuex from 'vuex'
import Cookies from 'js-cookie'
import { logOut } from '@/api/system/login.js'
// import { loginRequestOut } from '@/api/index/login.js'
export default {
  data () {
    return {
      imageUrl: [
        { img_1: this.$imageHost + '/image/system/b2c_logo.png' },
        { img_2: this.$imageHost + '/image/admin/img1.png' },
        { img_3: this.$imageHost + '/image/admin/menu_top_1.png' }
      ],
      log_menu_show: false,
      hiddle_menu_list: [this.$t('useNameOption.pasModify'), this.$t('useNameOption.accountMange'), this.$t('useNameOption.exit')],
      changeColorIndex: '',
      username: '',
      menu_width: '',
      header_navData: [
        {
          title: '概览',
          img: this.$imageHost + '/image/system/first_1.png',
          routeName: 'overviewMain'
        },
        {
          title: '店铺管理',
          img: this.$imageHost + '/image/system/first_2.png',
          routeName: 'storeManagementMain'
        },
        {
          title: '数据统计',
          img: this.$imageHost + '/image/system/first_3.png'
        },
        {
          title: '商品管理',
          img: this.$imageHost + '/image/system/first_3.png'
        },
        {
          title: '会员管理',
          img: this.$imageHost + '/image/system/get_user.png'
        },
        {
          title: '订单管理',
          img: this.$imageHost + '/image/system/first_4.png',
          routeName: 'orderMain'
        },
        {
          title: '设置',
          img: this.$imageHost + '/image/system/first_4.png'
        }
      ]
    }
  },
  created () {
    // 初始化登录
    this.judgeuserinfo()
    // 初始化语言
    this.langDefault()
  },

  methods: {
    ...Vuex.mapActions(['changesysNavLeft']),
    // 初始化顶部导航 通过传入的路由的meta值来进行对应的匹配
    judgeHeader () {
      // console.log(this.$route)
      this.changesysNavLeft(this.$route.meta)
      let meta = this.$route.meta
      switch (meta) {
        case 'overview':
          this.nav_index = 0
          this.click_nav_index = 0
          break
        case 'shop_manage':
          this.nav_index = 1
          this.click_nav_index = 1
          break
        case 'data_statistics':
          this.nav_index = 2
          this.click_nav_index = 2
          break
        case 'goods_manage':
          this.nav_index = 3
          this.click_nav_index = 3
          break
        case 'member_manage':
          this.nav_index = 4
          this.click_nav_index = 4
          break
        case 'order_manger':
          this.nav_index = 5
          this.click_nav_index = 5
          break
        case 'setting':
          this.nav_index = 6
          this.click_nav_index = 6
          break
      }
    },
    // 初始化登录
    judgeuserinfo () {
      console.log(this)
      if (Cookies.get('V-System-Token')) {
        this.user_flag = true
        this.username = localStorage.getItem('System-Username') // 获取到登录用户的名字
      } else {
        this.user_flag = false
      }
    },
    // 用户选项点击
    handle_user_list (index) {
      switch (index) {
        case 2:
          logOut().then((res) => {
            console.log(res)
            if (res.error === 0) {
              Cookies.remove('V-Token')
              localStorage.removeItem('System-Username')
              this.$router.push({
                path: '/system/login'
              })
            } else {
              this.$message({
                showClose: true,
                message: res.message,
                type: 'error'
              })
            }
          })
          break
      }
    },
    header_nav_over (index) {
      this.nav_index = index
    },
    // 顶部导航点击 通过传入路由的name来默认匹配每一次点击后的第一个页面
    headerNavClick (routeName) {
      this.$router.push({ name: routeName })
    },
    user_enter (index) {
      this.log_menu_show = true
      if (index === 'undefined') return 0
      this.changeColorIndex = index
    },
    user_leave (index) {
      this.log_menu_show = false
      if (!index) return 0
      this.changeColorIndex = ''
    }
  }
}
</script>
<style scoped lang="scss">
.header_container {
  min-width: 100%;
  width: 100%;
  height: 85px;
  line-height: 85px;
  color: #fff;
  background: #86a7cb;
  padding: 0 25px 0 24px;
  position: fixed;
  z-index: 998;
}
.left {
  float: left;
  height: 100%;
}
.left img {
  height: 40px;
  width: 128px;
  margin-top: 20px;
}
.middle {
  float: right;
  height: 100%;
  margin-right: 35px;
  position: relative;
}
.account {
  height: 85px;
  line-height: 85px;
}
.menu,
.menu_main {
  height: 100%;
}
.menu_main span {
  cursor: pointer;
  display: block;
  float: left;
  height: 100%;
}
.menu_main span:nth-of-type(1) {
  display: flex;
  align-items: center;
  margin-right: 5px;
}
.menu_main span:nth-of-type(2) span {
  display: flex;
  align-items: center;
}
.menu_main span:nth-of-type(1) img {
  border-radius: 100%;
}
label {
  margin-right: 10px;
  cursor: pointer;
  display: inline-block;
  max-width: 100%;
  margin-bottom: 5px;
  font-weight: 700;
  font-size: 14px;
}
.log-menu {
  color: #000;
  height: 135px !important;
  width: 170px !important;
  background: #fff;
  position: absolute;
  left: -60px;
  z-index: 1000;
  top: 70px;
  padding: 10px 20px;
  box-shadow: 1px 1px 1px #ccc;
  border-radius: 3px;
}
.log-menu img {
  position: absolute;
  right: 75px;
  top: -8px;
}
.log-menu div {
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  cursor: pointer;
}
.changeColor {
  color: red;
}
.admin_menu_width {
  width: 220px !important;
}
.header_nav {
  display: flex;
  height: 85px;
  float: left;
  margin-left: 208px;
}
.header_nav > div {
  height: 85px;
  display: flex;
  padding: 10px 25px;
  cursor: pointer;
}
.header_nav > div > img {
  position: absolute;
  left: 50%;
  transform: translateX(-50%); // 修复原代码图片不居中问题
  width: 26px;
  height: 24px;
}
.header_nav > div > span {
  display: block;
  height: 52px;
  font-size: 14px;
  color: #fff;
  padding: 8px 0;
}
.wrapper {
  position: relative;
  border-left: 1px solid #749dc9;
  text-align: center;

  &:hover {
    background-color: #e7f1ff;
    border-color: #e7f1ff;
    span {
      color: #749dc9 !important;
    }
  }
}
.wrapper img {
  margin: 0 auto;
}
.wrapper:nth-last-child(1) {
  border-right: 1px solid #749dc9;
}
.active_bg {
  background-color: #e7f1ff;
  border-color: #e7f1ff;
  color: #749dc9 !important;
}
</style>
