<template>
  <div class="header_container">
    <div class="left">
      <img :src="imageUrl[0].img_1">
    </div>

    <div class="right">
      <div class="right_main">
        <img
          src="../../../assets/adminImg/notice_ld.png"
          @click="toList"
        >
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
              <img
                class="shopAvatar"
                :src="shopAvatar"
              >
            </span>
            <span>
              <label>
                {{this.accountName}}
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
          :class="[changeColorIndex === index?'changeColor':'',item=== 'empty'?'emptyClass':'logDiv']"
        >
          <div v-if="item!=='empty'">
            {{item}}
          </div>
        </div>
        <img :src="imageUrl[3].img_4">
      </div>
    </div>
    <div
      class="langChange"
      @mouseover="langEnter()"
      @mouseout="langLeave()"
    >
      <span>{{$t('messages.lang')}}</span>
      <img
        :src="imageUrlData[1].image_4"
        class="head_down"
      >
      <div class="head_list_lang">
        <div
          class="lang_c"
          target="_blank"
          @click="handleChangeLang(index)"
          :class="item.login_active"
          v-for="(item,index) in langData_show"
          :key="index"
        >{{item.show_lang}}</div>
      </div>
    </div>

  </div>
</template>
<script>
import { queryShopRequest } from '@/api/admin/shopsPages.js'
import Cookies from 'js-cookie'
import { loginRequestOut } from '@/api/index/login.js'
import { loadLanguageAsync } from '@/i18n/i18n.js'
export default {
  data () {
    return {
      langData_show: [],
      imageUrl: [
        {
          img_1: this.$imageHost + '/image/admin/official/bottom_logo.png'
        },
        {
          img_2: this.$imageHost + '/upload/0/image/20180528/crop_WOo1stJyM79N6k70.jpeg'
        },
        {
          img_3: this.$imageHost + '/image/admin/img1.png'
        },
        {
          img_4: this.$imageHost + '/image/admin/menu_top_1.png'
        }
      ],
      log_menu_show: false,
      hiddle_menu_list: [],
      changeColorIndex: '',
      username: '',
      accountName: '',
      menu_width: '',
      shopAvatar: '',
      imageUrlData: [
        { image_3: this.$imageHost + '/image/admin/official/blue_down.png' },
        { image_4: this.$imageHost + '/image/admin/img1.png' }
      ],
      langData_cn: [
        { lang: 'en', login_active: '', show_lang: 'English' },
        { lang: 'cn', login_active: '', show_lang: '中文' }
      ],
      langData_en: [
        { lang: 'en', login_active: '', show_lang: 'English' },
        { lang: 'cn', login_active: '', show_lang: 'Chinese' }
      ],
      isSubLogin: false
    }
  },
  mounted () {
    // 初始化登录
    this.judgeuserinfo()
    // 初始化语言
    this.langDefault()
  },
  watch: {
    lang (newData) {
      console.log(newData)
      let data = JSON.parse(JSON.stringify(this.$t('shopData')))
      console.log(data)
      // this.hiddle_menu_list = this.$t('shopData')
      this.$t('shopData').forEach((item, index) => {
        if (index === 2) {
          // this.$t('shopData').splice(index, 1)
          data[index] = 'empty'
        }
        if (this.isSubLogin) {
          data[1] = 'empty'
        }
      })
      this.hiddle_menu_list = data
      console.log(this.hiddle_menu_list)
    }
  },
  methods: {
    // 初始化登录
    judgeuserinfo () {
      console.log('去掉')
      if (localStorage.getItem('V-isSubLogin') === 'true') {
        // 子账户登录
        this.isSubLogin = true
      }
      console.log(this.isSubLogin)
      this.lang = localStorage.getItem('WEPUBAO_LANGUAGE')
      this.$http.$on('changeHead', () => {
        this.shopAvatar = localStorage.getItem('V-shopAvatar')
        this.accountName = localStorage.getItem('V-AccountName')
      })
      if (Cookies.get('V-Index-Token')) {
        this.user_flag = true
        this.username = localStorage.getItem('V-Username')
        this.accountName = localStorage.getItem('V-AccountName')
        queryShopRequest().then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.shopAvatar = res.content.shopAvatar

            localStorage.setItem('V-shopAvatar', res.content.shopAvatar)
          }
          console.log(res)
        })
      } else {
        this.user_flag = false
      }
    },
    // 用户选项点击
    handle_user_list (index) {
      console.log('外面点击', index)
      switch (index) {
        case 0:
          this.$router.push({
            path: '/admin/home/shopMain',
            query: {
              change_components: '0'
            }
          })
          break
        case 1:
          this.$router.push({
            path: '/admin/home/shopMain',
            query: {
              change_components: '1'
            }
          })
          break
        case 3:
          this.$router.push({
            path: '/admin/home/shopMain',
            query: {
              change_components: '3'
            }
          })
          break
        case 4:
          this.$router.push({
            path: '/admin/home/shopMain',
            query: {
              change_components: '4'
            }
          })
          break
        case 5:
          loginRequestOut().then((res) => {
            console.log(res)
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
      console.log('外面的header')
      this.$http.$emit('changeHeaderComponents')
    },
    // 鼠标划入
    user_enter (index) {
      this.log_menu_show = true
      if (index === 'undefined') return 0
      this.changeColorIndex = index
    },
    langEnter () {
      console.log('1')
      this.langData_cn.forEach(item => {
        item.login_active = 'login_active'
      })
      this.langData_en.forEach(item => {
        item.login_active = 'login_active'
      })
    },
    // 鼠标划出
    user_leave (index) {
      this.log_menu_show = false
      if (!index) return 0
      this.changeColorIndex = ''
    },
    langLeave () {
      console.log('2')
      this.langData_cn.forEach(item => {
        item.login_active = ''
      })
      this.langData_en.forEach(item => {
        item.login_active = ''
      })
    },
    // 语言选项点击
    handleChangeLang (index) {
      switch (index) {
        case 0:
          // this.$i18n.locale = this.langData_cn[index].lang
          this.lang_with = 'width:63px'
          this.langData_show = this.langData_en
          this.loginData_show = this.loginData_en
          localStorage.setItem('WEPUBAO_LANGUAGE', 'en_US')
          break
        case 1:
          // this.$i18n.locale = this.langData_cn[index].lang
          this.lang_with = 'width:43px'
          this.langData_show = this.langData_cn
          this.loginData_show = this.loginData_cn
          localStorage.setItem('WEPUBAO_LANGUAGE', 'zh_CN')
          break
      }
      loadLanguageAsync(this.langData_cn[index].lang).then(() => {
        this.$http.$emit('lang_change', index)
        this.$http.$emit('CHANGE_LANGUAGE', -1)
      })

      console.log(this.lang)
    },
    toList () {
      this.$router.push({
        path: '/admin/home/shopMain',
        query: {
          change_components: '7'
        }
      })
    }
  }
}
</script>
<style scoped>
.header_container {
  width: 100%;
  height: 85px;
  line-height: 85px;
  color: #fff;
  background: #5a8bff;
  padding: 0 25px 0 45px;
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
.shopAvatar {
  width: 52px;
  height: 52px;
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
  /* height: 210px !important; */
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

.log-menu .logDiv {
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  cursor: pointer;
}
.log-menu .emptyClass {
  height: 0;
}
.changeColor {
  color: red;
}
.admin_menu_width {
  width: 220px !important;
}
.langChange {
  float: right;
  /* margin-right: 30px; */
  padding-right: 30px;
  cursor: pointer;
}
.head_list_lang {
  position: absolute;
  width: 160px;
  background: #fff;
  -webkit-box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  -moz-box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  box-shadow: 0 0px 10px rgba(204, 204, 204, 0.3);
  color: #000;
  text-align: center;
  z-index: 1000;
  top: 85px;
  box-shadow: 1px 1px 1px #ccc;
  border-radius: 3px;
}
.lang_c {
  display: none;
  line-height: 40px;
  height: 40px;
  width: 100%;
  text-align: center;
  border-radius: 3px;
}
.login_active {
  /* background-color: #f8f8f8; */
  /* color: #5a8bff; */
  display: block !important;
}
</style>
