<template>
  <div class="header_container">
    <div>
      <div class="left">
        <img :src="imageUrl[0].img_1" />
      </div>
      <div class="header_nav" :class="headerNavEn">
        <div
          v-for="(item, index) in header_navData"
          :key="index"
          :class="
            $route.meta.meta == item.meta || click_nav_index == index
              ? 'active_bg'
              : ''
          "
          @click="headerNavClick(index, item.url)"
          @mouseover="header_nav_over(index)"
          @mouseleave="header_nav_leave(index)"
        >
          <span>{{ item.title }}</span>
        </div>
      </div>
    </div>

    <div class="rightCon">
      <!-- <div class="langChange" @mouseover="langEnter()" @mouseout="langLeave()">
        <span>{{ $t('messages.lang') }}</span>
        <img :src="imageUrlData[1].image_4" class="head_down" />
        <div class="head_list_lang">
          <div
            class="lang_c"
            target="_blank"
            @click="handleChangeLang(index)"
            :class="item.login_active"
            v-for="(item, index) in langData_show"
            :key="index"
          >
            {{ item.show_lang }}
          </div>
        </div>
      </div> -->
      <el-popover
        placement="bottom"
        trigger="hover"
        content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。"
      >
        <img :src="miniPorgramQrcode" class="qrcode-image" />
        <div class="mini-program-info" slot="reference">
          <span>{{ this.miniPorgramName }}</span>
          <img :src="imageUrlData[1].image_4" class="head_down" />
        </div>
      </el-popover>

      <div class="middle" @mouseenter="user_enter()" @mouseleave="user_leave()">
        <div class="account">
          <div class="menu">
            <div class="menu_main">
              <span class="avatar" v-if="!isSubLogin">
                <img :src="imageUrl[1].img_2" />
              </span>
              <span>
                <label class="accountName">
                  {{ this.accountName }}
                </label>
                <img class="accountIcon" :src="imageUrl[2].img_3" />
              </span>
            </div>
          </div>
        </div>
        <div class="log-menu" v-show="log_menu_show" :class="menu_width">
          <div
            v-for="(item, index) in hiddle_menu_list"
            :key="index"
            @mouseenter="user_enter(index)"
            @mouseleave="user_leave(index)"
            @click="handle_user_list(index)"
            :class="[
              changeColorIndex === index ? 'changeColor' : '',
              item === 'empty' ? 'emptyClass' : 'logDiv',
            ]"
          >
            <div v-if="item !== 'empty'">
              {{ item }}
            </div>
          </div>
          <img :src="imageUrl[3].img_4" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from 'vuex'
import Cookies from 'js-cookie'
import { loginRequestOut } from '@/api/index/login.js'
import { loadLanguageAsync } from '@/i18n/i18n.js'
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
      hiddle_menu_list: [],
      changeColorIndex: '',
      username: '',
      accountName: '',
      menu_width: '',
      header_navData: [
        { title: '门店概况', index: '', meta: 'overView', name: 'overView' },
        { title: '全部门店', index: '', meta: 'storeList', name: 'storeList' },
        { title: '门店商品', index: '', meta: 'storeGoods', name: 'storeGoods' },
        { title: '门店订单', index: '', meta: 'storeOrder', name: 'storeOrder' },
        { title: '店员管理', index: '', meta: 'storeMember', name: 'storeMember' }
      ],
      active_bg: 'active_bg',
      nav_index: '',
      click_nav_index: null,
      headerNavEn: '',
      imageUrlData: [
        { image_3: this.$imageHost + '/image/admin/official/blue_down.png' },
        { image_4: this.$imageHost + '/image/admin/img1.png' }
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
      isSubLogin: false,
      miniPorgramName: '',
      miniPorgramQrcode: ''
    }
  },
  mounted () {
    // console.log(this.$route)
    // 初始化登录
    this.judgeuserinfo()
    // 初始化语言
    console.log(1)
    this.langDefault()
    this.filterHeaderData()
  },

  watch: {
    lang (newData) {
      console.log(newData)
      let data = JSON.parse(JSON.stringify(this.$t('shopData')))
      // this.hiddle_menu_list = this.$t('shopData')
      // this.$t('shopData').forEach((item, index) => {
      //   if (index === 1) {
      //     data[index] = 'empty'
      //   }
      //   if (this.isSubLogin) {
      //     data[0] = '修改密码'
      //     data[2] = 'empty'
      //   }
      // })
      this.hiddle_menu_list = data
    }
  },
  methods: {
    ...mapActions(['ToTurnMemberShipDetail', 'judgeActiveMeunAll']),
    // 初始化登录
    judgeuserinfo () {
      if (localStorage.getItem('V-isSubLogin') === 'true') {
        // 子账户登录
        this.isSubLogin = true
      }
      this.lang = localStorage.getItem('WEPUBAO_LANGUAGE')
      this.imageUrl[1].img_2 = localStorage.getItem('V-AccountShopAvatar')
      this.accountName = localStorage.getItem('V-AccountName')
      this.miniPorgramName = localStorage.getItem('V-StoreMiniProgramName')
      this.miniPorgramQrcode = localStorage.getItem('V-StoreMiniProgramQrcode')
      if (Cookies.get('V-Token')) {
        this.user_flag = true
        this.username = localStorage.getItem('V-Username')
        console.log(Cookies.get('V-Token'), '----', localStorage.getItem('V-Username'))
      } else {
        this.user_flag = false
      }
    },
    // 用户选项点击
    handle_user_list (index) {
      console.log(1111)
      switch (index) {
        // case 0:
        //   // this.$emit('change_components', '0')
        //   this.$router.push({
        //     path: '/admin/home/shopMain',
        //     query: {
        //       change_components: '0'
        //     }
        //   })
        //   break
        // case 2:
        //   // this.$emit('change_components', '3')
        //   this.$router.push({
        //     path: '/admin/home/shopMain',
        //     query: {
        //       change_components: '2'
        //     }
        //   })
        //   break
        // case 3:
        //   this.$router.push({
        //     path: '/admin/home/shopMain',
        //     query: {
        //       change_components: '3'
        //     }
        //   })
        //   break
        // case 4:
        //   this.$router.push({
        //     path: '/admin/home/shopMain',
        //     query: {
        //       change_components: '4'
        //     }
        //   })
        //   break
        case 0:
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
      }

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
    header_nav_over (index) {
      this.click_nav_index = index
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
    header_nav_leave (index) {
      this.click_nav_index = null
    },
    // 顶部导航点击
    headerNavClick (index, url) {
      console.log(this.$route)
      this.$http.$emit('resit', false)
      this.click_nav_index = index
      console.log(name)
      this.$router.push({
        path: url
      })

      this.judgeActiveMeunAll(name)
      // console.log(name)
      if (name === 'membershipList') {
        this.ToTurnMemberShipDetail('0')
      }
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
    filterHeaderData () {
      this.header_navData = this.showMenuData.reduce((defaultData, item) => {
        let target = this.header_navData.find(navItem => { return item.enName === navItem.meta })
        if (item.check) {
          target.url = item.linkUrl
          defaultData.push(target)
        }
        return defaultData
      }, [])
    }
  },
  computed: {
    ...mapGetters(['showMenuData'])
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
  padding-left: 20px;
  position: absolute;
  z-index: 40;
  display: flex;
  justify-content: space-between;
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
.header_nav {
  display: flex;
  height: 85px;
  float: left;
  margin-left: 10px;
}
.header_nav > div {
  height: 85px;
  display: flex;
  padding: 15px 15px;
  cursor: pointer;
}
/* .headerNavEn {
  margin-left: 4% !important;
}
*/
.headerNavEn > div {
  padding: 15px 4px !important;
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
.langChange {
  float: right;
  margin-right: 30px;
  cursor: pointer;
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
  top: 85px;
  z-index: 1000;
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
  /* background-color: #f8f8f8;
  color: #5a8bff; */
  display: block !important;
}
.accountName {
  white-space: nowrap;
  width: 84px;
  overflow: hidden;
  text-overflow: ellipsis;
}
.accountIcon {
  position: absolute;
  bottom: 38px;
}
.rightCon {
  display: flex;
}
.mini-program-info {
  margin-right: 15px;
}
.qrcode-image {
  width: 200px;
  height: 200px;
}
</style>
