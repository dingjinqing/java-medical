<template>
  <div class="container">
    <div class="left_menu">
      <ul class="menu_wrap">
        <li
          class="left_menu_item"
          :class="{active_bg: $route.meta.title === item.span||$route.meta.parentName === item.span}"
          v-for="(item, index) in filterNavArr"
          :key="index"
          @mouseenter="hoverIndex = index"
          @mouseout="hoverIndex = -1"
        >
          <img
            :src="isShowHighLight(item, index)"
            alt=""
          >
          <router-link
            :to="{name: item.routeName, params: item.params}"
            class="link_item"
          >
            {{item.span}}
          </router-link>
        </li>
      </ul>
      <!-- <div>尾部图片</div> -->
    </div>
  </div>
</template>
<script>

export default {
  data () {
    return {
      navLeftData: '',
      navLeftArr: {
        // welcome: [
        //   {
        //     imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
        //     imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
        //     span: 'welcome',
        //     routeName: 'welcome'
        //   }
        // ],
        overviewMain: [
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/shop_view.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/shop_view_h.png',
            span: '概览',
            routeName: 'overview'
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
            span: '升级续费申请',
            routeName: 'upgradeRenewal'
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
            span: '店铺登录日志',
            routeName: 'loginLog'
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
            span: '日志管理'
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/essay_admin.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/essay_admin_h.png',
            span: '文章管理',
            routeName: 'essayAdmin'
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
            span: '图片空间',
            routeName: 'picSpace'
          }
        ],
        storeManagementMain: [
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/mobile_deco.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/mobile_deco_h.png',
            span: '店铺账户列表',
            routeName: 'accountList'
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/mobile_deco.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/mobile_deco_h.png',
            span: '店铺列表',
            routeName: 'shopList'
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/mobile_deco.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/mobile_deco_h.png',
            span: '发布列表',
            routeName: 'pshopList'
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/mobile_deco.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/mobile_deco_h.png',
            span: '小程序版本',
            routeName: 'programManage',
            params: {
              page: 'versionManage',
              appId: '-1'
            }
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/picture_space.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/picture_space_h.png',
            span: '版本列表',
            routeName: 'versionList'
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
        orderMain: [
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/order_admin.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/order_admin_h.png',
            span: '订单统计',
            routeName: 'orderStatistics'
          },
          {
            imgUrl: this.$imageHost + '/image/system/icon_left/order_admin.png',
            imgUrl_h: this.$imageHost + '/image/system/icon_left/order_admin_h.png',
            span: '订单列表',
            routeName: 'orderList'
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
        ]
      },
      nav_s_class_index: false,
      hoverIndex: -1
    }
  },
  created () {
    // console.log(this.$router, 'router')
    // console.log(this.$route, 'route')
  },
  methods: {
    isShowHighLight (route, index) {
      if (route.span === this.$route.meta.title) {
        return route.imgUrl_h
      } else if (this.hoverIndex > 0 && this.hoverIndex === index) {
        return route.imgUrl_h
      } else {
        return route.imgUrl
      }
    }
  },
  computed: {
    filterNavArr () {
      for (let key in this.navLeftArr) {
        if (this.$route.matched[0].name === key) {
          return this.navLeftArr[key]
        }
      }
    }
  }
}
</script>
<style scoped lang="scss">
.container {
  height: 100%;
  width: 150px;
  z-index: 10;
}

.menu_wrap {
  padding-top: 85px;
}

.left_menu {
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
  background: #aac1e0;
  width: 150px;
  z-index: 100;
  height: 100%;
  min-height: 100vh;

  .link_item {
    text-decoration: none;
    color: #fff;
  }
}

ul li {
  height: 45px;
  line-height: 45px;
  padding-left: 12px;
  overflow: hidden;
  display: flex;
  cursor: pointer;
  &:hover {
    background: #e7f1ff;
    .link_item {
      color: #86a7cb;
    }
  }
}

ul li img {
  display: block;
  float: left;
  width: 20px;
  height: 20px;
  margin: auto 0;
}

ul li a {
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
  a {
    color: #86a7cb !important;
  }
}

.nav_s_class {
  width: 66px;
  height: 26px;
  position: relative;
  left: -11px;
  bottom: -9px;
}
</style>
