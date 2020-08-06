<template>
  <div class="container">
    <div class="left_menu">
      <ul>
        <li
          v-for="(item,index) in navLeftData"
          :key="index"
          :class="nav_index==index||click_nav_index==index?'active_bg':''"
          @click="leftNavClick(index)"
          @mouseover="left_nav_over(index)"
          @mouseleave="left_nav_leave(index)"
          style="cursor:pointer"
        >
          <img
            :src="nav_index==index||click_nav_index==index?item.imgUrl_h:item.imgUrl"
            :class="nav_s_class_index&&index==0?'nav_s_class':''"
          >
          <span>{{item.span}}</span>
        </li>
      </ul>
      <!-- <div>尾部图片</div> -->
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      navLeftData: '',
      overview: [
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
          span: '概览'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
          span: '升级续费申请'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '官网申请试用'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '问题反馈'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '店铺登录日志'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
          span: '日志管理'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/essay_admin.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/essay_admin_h.png',
          span: '文章管理'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
          span: '图片空间'
        }
      ],
      shop_manage: [
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/mobile_deco.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/mobile_deco_h.png',
          span: '店铺账户列表'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/mobile_deco.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/mobile_deco_h.png',
          span: '店铺列表'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/mobile_deco.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/mobile_deco_h.png',
          span: '发布列表'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/mobile_deco.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/mobile_deco_h.png',
          span: '小程序版本'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
          span: '版本列表'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
          span: '微信全链路'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
          span: '申请发布列表'
        }
      ],
      data_statistics: [
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
          span: '概览'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
          span: '用户画像'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
          span: '访问趋势'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
          span: '访问分析'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
          span: '店铺访问量'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
          span: '功能使用'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
          span: '收入统计'
        }
      ],
      goods_manage: [
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/product_list.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/product_list_h.png',
          span: '商品统计'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/product_list.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/product_list_h.png',
          span: '商品列表'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/picture_attr.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_attr_h.png',
          span: '分类管理'
        }
      ],
      member_manage: [
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '会员统计'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '会员列表'
        }
      ],
      order_manage: [
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/order_admin.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/order_admin_h.png',
          span: '订单统计'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/order_admin.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/order_admin_h.png',
          span: '订单列表'
        },
          {
          imgUrl: this.$imageHost + '/image/system/icon_left/order_admin.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/order_admin_h.png',
          span: '咨询订单'
        }
      ],
      setting: [
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '装修模板'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '子账号管理'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: 'Enter Admin'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '数据库管理'
        },
        {
          imgUrl: this.$imageHost + '/image/system/icon_left/get_user.png',
          imgUrl_h: this.$imageHost + '/image/system/icon_left/get_user_h.png',
          span: '图片存储'
        }
      ],
      nav_index: '',
      click_nav_index: '',
      nav_s_class_index: false,
      flag: ''
    }
  },
  computed: {
    ...mapGetters(['system_leftVav_flag']),
    system_leftVav_flag1 () {
      return this.system_leftVav_flag
    }
  },
  watch: {
    system_leftVav_flag1 (newData, oldData) {
      this.judgeNav()
    }
  },
  mounted () {
    // 初始化左侧菜单及顶部点击左侧菜单显示
    this.judgeNav()
  },
  methods: {
    judgeNav () {
      // console.log(this.system_leftVav_flag)
      this.flag = this.system_leftVav_flag
      switch (this.flag) {
        case 'overview':
          this.handlesysLeftNav(this.overview, false, this.flag)
          break
        case 'shop_manage':
          this.handlesysLeftNav(this.shop_manage, false, this.flag)
          break
        case 'data_statistics':
          this.handlesysLeftNav(this.data_statistics, false, this.flag)
          break
        case 'goods_manage':
          this.handlesysLeftNav(this.goods_manage, false, this.flag)
          break
        case 'member_manage':
          this.handlesysLeftNav(this.member_manage, false, this.flag)
          break
        case 'order_manage':
          this.handlesysLeftNav(this.order_manage, false, this.flag)
          break
        case 'setting':
          this.handlesysLeftNav(this.setting, false, this.flag)
          break
      }
    },
    // 页面主动刷新初始化函数
    handlesysLeftNav (show, boolean, flag) {
      this.navLeftData = show
      this.nav_s_class_index = boolean
      this.nav_index = ''
      this.click_nav_index = ''
      let name = this.$route.name

      switch (name) {
        // 概览
        case 'overview':
          if (flag === 'overview') {
            this.nav_index = 0
            this.click_nav_index = 0
          }
          break
        // 店铺管理
        case 'account_list':
          if (flag === 'shop_manage') {
            this.nav_index = 0
            this.click_nav_index = 0
          }
          break
        case 'shop_lists':
          if (flag === 'shop_manage') {
            this.nav_index = 1
            this.click_nav_index = 1
          }
          break
      }
    },
    // 左侧菜单栏点击事件
    leftNavClick (index) {
      this.click_nav_index = index
      // 概况模块左侧点击
      if (this.flag === 'overview') {
        switch (index) {
          case 0:
            this.$router.push({
              name: 'overview'
            })
            break
          case 1:
            this.$router.push({
              name: 'upgrade_renewal'
            })
            break
          case 2:
            this.$router.push({
              name: 'web_apply'
            })
            break
          case 3:
            this.$router.push({
              name: 'problem_feedback'
            })
            break
          case 4:
            this.$router.push({
              name: 'login_log'
            })
            break
          case 5:
            this.$router.push({
              name: 'log_manage'
            })
            break
          case 6:
            this.$router.push({
              name: 'article_manage'
            })
            break
          case 7:
            this.$router.push({
              name: 'picture_space'
            })
            break
        }
      }
      // 店铺管理模块左侧点击
      if (this.flag === 'shop_manage') {
        switch (index) {
          case 0:
            this.$router.push({
              name: 'account_list'
            })
            break
          case 1:
            this.$router.push({
              name: 'shop_list'
            })
            break
          case 2:
            this.$router.push({
              name: 'publish_list'
            })
            break
          case 3:
            this.$router.push({
              name: 'small_program'
            })
            break
          case 4:
            this.$router.push({
              name: 'versition_list'
            })
            break
          case 5:
            this.$router.push({
              name: 'wechat_link'
            })
            break
          case 6:
            this.$router.push({
              name: 'apply_public'
            })
            break
        }
      }
    },
    // 左侧菜单栏划入事件
    left_nav_over (index) {
      this.nav_index = index
    },
    // 左侧菜单栏划出事件
    left_nav_leave (index) {
      this.nav_index = null
    }
  }
}
</script>
<style scoped>
.container {
  height: 100%;
  width: 150px;
  z-index: 10;
}
.left_menu {
  background: #aac1e0;
  width: 150px;
  z-index: 100;
  height: 100%;
}
ul li {
  height: 45px;
  line-height: 45px;
  padding-left: 12px;
  overflow: hidden;
  display: flex;
}
ul li img {
  display: block;
  float: left;
  width: 20px;
  height: 20px;
  margin: auto 0;
}
ul li span {
  display: block;
  float: left;
  color: #fff;
  margin-left: 17px;
  font-size: 14px;
  height: 45px;
  line-height: 45px;
  opacity: 1;
}
.active_bg {
  background: #e7f1ff;
  color: #86a7cb;
}
.nav_s_class {
  width: 66px;
  height: 26px;
  position: relative;
  left: -11px;
  bottom: -9px;
}
</style>
