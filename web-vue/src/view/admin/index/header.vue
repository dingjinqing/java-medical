<template>
  <div class="header_container">
    <div class="left">
      <img :src="imageUrl[0].img_1">
    </div>
    <div class="header_nav">
      <div
        v-for="(item,index) in header_navData"
        :key="index"
        :class="$route.meta.meta == item.meta||click_nav_index==index?'active_bg':''"
        @click="headerNavClick(index,item.name)"
        @mouseover="header_nav_over(index)"
        @mouseleave="header_nav_leave(index)"
      >
        <span>{{item.title}}</span>
      </div>

    </div>

    <div class="right">
      <div class="right_main">
        <img src="../../../assets/adminImg/notice_ld.png">
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
            <span class="avatar">
              <img :src="imageUrl[1].img_2">
            </span>
            <span>
              <label>
                {{this.username}}
              </label>
              <img :src="imageUrl[2].img_3">
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
        <img :src="imageUrl[3].img_4">
      </div>
    </div>

  </div>
</template>
<script>
import { mapActions } from 'vuex'
import Cookies from 'js-cookie'
import { loginRequestOut } from '@/api/index/login.js'
export default {
  data () {
    return {
      imageUrl: [
        {
          img_1: this.$imageHost + '/image/admin/official/bottom_logo.png'
        },
        {
          img_2: ''
        },
        {
          img_3: this.$imageHost + '/image/admin/img1.png'
        },
        {
          img_4: this.$imageHost + '/image/admin/menu_top_1.png'
        }
      ],
      log_menu_show: false,
      hiddle_menu_list: [this.$t('shopData.set'), this.$t('shopData.administration_J'), this.$t('shopData.public'), this.$t('shopData.choice'), this.$t('shopData.loginOut')],
      changeColorIndex: '',
      username: '',
      menu_width: '',
      header_navData: [
        { title: '概况', index: '', meta: 'first_web_manage', name: 'shop_view' },
        { title: '小程序管理', index: '', meta: 'first_web_decoration', name: 'picture_setting' },
        { title: '商品管理', index: '', meta: 'goods_manage', name: 'sale_on' },
        { title: '订单管理', index: '', meta: 'first_trade_manage', name: 'order' },
        { title: '营销管理', index: '', meta: 'first_market_manage', name: 'first_market_manage' },
        { title: '会员管理', index: '', meta: 'user_manger', name: 'user_list' },
        { title: '门店管理', index: '', meta: 'store_manage', name: 'store_list' },
        { title: '基础配置', index: '', meta: 'base_manger', name: 'config_list' }
      ],
      active_bg: 'active_bg',
      nav_index: '',
      click_nav_index: null
    }
  },
  mounted () {
    // console.log(this.$route)
    // 初始化登录
    this.judgeuserinfo()
    // 初始化语言
    this.langDefault()
  },
  methods: {
    ...mapActions(['ToTurnMemberShipDetail', 'judgeActiveMeunAll']),
    // 初始化登录
    judgeuserinfo () {
      this.imageUrl[1].img_2 = localStorage.getItem('V-shopAvatar')
      if (Cookies.get('V-Token')) {
        this.user_flag = true
        this.username = localStorage.getItem('V-Username')
        // console.log(Cookies.get('V-Token'), '----', localStorage.getItem('V-Username'))
      } else {
        this.user_flag = false
      }
    },
    // 用户选项点击
    handle_user_list (index) {
      switch (index) {
        case 0:
          this.$emit('change_components', '0')
          this.$router.push({
            path: '/admin/home/shop_main',
            query: {
              change_components: '0'
            }
          })
          break
        case 3:
          this.$emit('change_components', '3')
          this.$router.push({
            path: '/admin/home/shop_main',
            query: {
              change_components: '3'
            }
          })
          break
        case 4:
          loginRequestOut().then((res) => {
            // console.log(res)
            if (res.error === 0) {
              Cookies.remove('V-Token')
              localStorage.removeItem('V-Username')
              this.$router.push({
                path: '/index/login'
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
    // 鼠标划入
    user_enter (index) {
      this.log_menu_show = true
      if (index === 'undefined') return 0
      this.changeColorIndex = index
    },
    header_nav_over (index) {
      this.click_nav_index = index
    },
    // 鼠标划出
    user_leave (index) {
      this.log_menu_show = false
      if (!index) return 0
      this.changeColorIndex = ''
    },
    header_nav_leave (index) {
      this.click_nav_index = null
    },
    // 顶部导航点击
    headerNavClick (index, name) {
      this.$http.$emit('resit', false)
      this.click_nav_index = index
      console.log(name)
      if (name === 'base_manger') {
        this.$router.push({
          name: name,
          params: {
            isAuth: '-1'
          }
        })
      } else {
        this.$router.push({
          name: name
        })
      }

      this.judgeActiveMeunAll(name)
      // console.log(name)
      if (name === 'membershipList') {
        this.ToTurnMemberShipDetail('0')
      }
    }
  }
}
</script>
<style scoped>
.header_container {
  min-width: 800px;
  width: 100%;
  height: 85px;
  line-height: 85px;
  color: #fff;
  background: #5a8bff;
  padding: 0 25px 0 45px;
  padding-left: 34px;
  position: absolute;
}
.left {
  float: left;
  height: 100%;
}
.left img {
  height: 49px;
  margin-top: 18px;
}
.middle {
  float: right;
  height: 100%;
  margin-right: 38px;
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
.right {
  height: 100%;
  float: right;
}
.right_main {
  cursor: pointer;
}
.log-menu {
  color: #000;
  height: 210px !important;
  background: #fff;
  position: absolute;
  left: -60px;
  width: 170px;
  z-index: 1000;
  top: 70px;
  padding: 10px 20px;
  box-shadow: 1px 1px 1px #ccc;
  border-radius: 3px;
}
.log-menu img {
  position: absolute;
  right: 33px;
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
  margin-left: 6%;
}
.header_nav > div {
  height: 85px;
  display: flex;
  padding: 15px 25px;
  cursor: pointer;
}
.header_nav > div > span {
  display: block;
  margin: auto;
  height: 52px;
  line-height: 52px;
  font-size: 14px;
  color: #fff;
}
.active_bg {
  background-color: rgb(67, 122, 249);
}
.avatar img {
  display: inline-block;
  width: 52px;
  height: 52px;
}
</style>
