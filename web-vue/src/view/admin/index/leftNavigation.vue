<template>
  <div class="container">
    <div class="left_menu">
      <ul>
        <li
          v-for="(item,index) in navLeftData"
          :key="index"
          :class="$route.name == item.name||click_nav_index===index?'active_bg':''"
          @click="leftNavClick(index,item.name)"
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
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      navLeftData: '',
      first_web_manage: [
        {
          imgUrl: '/static/image/admin/icon_left/shop_look.png',
          imgUrl_h: '/static/image/admin/icon_left/shop_look_h.png',
          span: '商城概览',
          name: 'overviewOfMall'
        },
        {
          imgUrl: '/static/image/admin/icon_left/analysis_basic.png',
          imgUrl_h: '/static/image/admin/icon_left/analysis_basic_h.png',
          span: '概况统计',
          name: 'overviewStatistics'
        },
        {
          imgUrl: '/static/image/admin/icon_left/situation.png',
          imgUrl_h: '/static/image/admin/icon_left/situation_h.png',
          span: '实时概况',
          name: 'realtimeoverview'
        },
        {
          imgUrl: '/static/image/admin/icon_left/new_analysis_portrait.png',
          imgUrl_h: '/static/image/admin/icon_left/new_analtsis_portrait_h.png',
          span: '用户画像',
          name: 'userportrait'
        },
        {
          imgUrl: '/static/image/admin/icon_left/analysis_visit.png',
          imgUrl_h: '/static/image/admin/icon_left/analysis_visit_h.png',
          span: '访问分析',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/laiyuan.png',
          imgUrl_h: '/static/image/admin/icon_left/laiyuan_h.png',
          span: '来源分析',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/new_yonghu.png',
          imgUrl_h: '/static/image/admin/icon_left/new_yonghu_h.png',
          span: '用户统计',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/goods_new_ana.png',
          imgUrl_h: '/static/image/admin/icon_left/goods_new_ana_h.png',
          span: '商品统计',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/jiaoyi.png',
          imgUrl_h: '/static/image/admin/icon_left/jiaoyi_h.png',
          span: '交易统计',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/asset_manage.png',
          imgUrl_h: '/static/image/admin/icon_left/asset_manage_h.png',
          span: '资产管理',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/search_config.png',
          imgUrl_h: '/static/image/admin/icon_left/search_config_h.png',
          span: '搜索统计',
          name: ''
        }
      ],
      first_web_decoration: [
        {
          imgUrl: '/static/image/admin/icon_left/page_decoration.png',
          imgUrl_h: '/static/image/admin/icon_left/page_decoration_h.png',
          span: '页面装修',
          name: 'first_web_decoration'
        },
        {
          imgUrl: '/static/image/admin/icon_left/picture_setting.png',
          imgUrl_h: '/static/image/admin/icon_left/picture_setting_h.png',
          span: '页面分类',
          name: 'page_classification'
        },
        {
          imgUrl: '/static/image/admin/icon_left/image_list.png',
          imgUrl_h: '/static/image/admin/icon_left/image_list_h.png',
          span: '图片空间',
          name: 'freight_template'
        },
        {
          imgUrl: '/static/image/admin/icon_left/shop_style.png',
          imgUrl_h: '/static/image/admin/icon_left/shop_style_h.png',
          span: '店铺风格',
          name: 'shopStyle'
        },
        {
          imgUrl: '/static/image/admin/icon_left/picture_space.png',
          imgUrl_h: '/static/image/admin/icon_left/picture_space_h.png',
          span: '底部导航',
          name: 'bottomNavigation'
        },
        {
          imgUrl: '/static/image/admin/icon_left/user_config.png',
          imgUrl_h: '/static/image/admin/icon_left/user_config_h.png',
          span: '个人中心',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/search_config.png',
          imgUrl_h: '/static/image/admin/icon_left/search_config_h.png',
          span: '搜索配置',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/mobile_deco.png',
          imgUrl_h: '/static/image/admin/icon_left/mobile_deco_h.png',
          span: '小程序跳转',
          name: ''
        }
      ],
      goods_manage: [
        {
          imgUrl: '/static/image/admin/icon_left/product_in.png',
          imgUrl_h: '/static/image/admin/icon_left/product_in_h.png',
          span: '全部商品',
          name: 'goods_manage'
        },
        {
          imgUrl: '/static/image/admin/icon_left/picture_add.png',
          imgUrl_h: '/static/image/admin/icon_left/picture_add_h.png',
          span: '添加商品',
          name: 'addingGoods'
        },
        {
          imgUrl: '/static/image/admin/icon_left/deliver_tmpl.png',
          imgUrl_h: '/static/image/admin/icon_left/deliver_tmpl_h.png',
          span: '运费模板',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/img_sort.png',
          imgUrl_h: '/static/image/admin/icon_left/img_sort_h.png',
          span: '商家分类管理',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/brand_icon.png',
          imgUrl_h: '/static/image/admin/icon_left/brand_icon_h.png',
          span: '品牌管理',
          name: 'brandManagement'
        },
        {
          imgUrl: '/static/image/admin/icon_left/comment_man.png',
          imgUrl_h: '/static/image/admin/icon_left/comment_man_h.png',
          span: '评价管理',
          name: 'evaluationManagement'
        },
        {
          imgUrl: '/static/image/admin/icon_left/recommend_icon.png',
          imgUrl_h: '/static/image/admin/icon_left/recommend_icon_h.png',
          span: '商品推荐',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/goods_label1.png',
          imgUrl_h: '/static/image/admin/icon_left/goods_label1_h.png',
          span: '商品标签',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/user_import.png',
          imgUrl_h: '/static/image/admin/icon_left/user_import_h.png',
          span: '商品导入',
          name: ''
        }
      ],
      first_trade_manageL: [
        {
          imgUrl: '/static/image/admin/icon_left/all_order.png',
          imgUrl_h: '/static/image/admin/icon_left/all_order_h.png',
          span: '全部订单',
          name: 'first_trade_manageL'
        },
        {
          imgUrl: '/static/image/admin/icon_left/wait_order.png',
          imgUrl_h: '/static/image/admin/icon_left/wait_order_h.png',
          span: '待发货订单',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/return_order.png',
          imgUrl_h: '/static/image/admin/icon_left/return_order_h.png',
          span: '退货退款订单',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/self_order.png',
          imgUrl_h: '/static/image/admin/icon_left/self_order_h.png',
          span: '自提订单',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/pin_group_fail.png',
          imgUrl_h: '/static/image/admin/icon_left/pin_group_fail_h.png',
          span: '拼团退款失败单',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/checkout.png',
          imgUrl_h: '/static/image/admin/icon_left/checkout_h.png',
          span: '买单订单',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/fake_icon.png',
          imgUrl_h: '/static/image/admin/icon_left/fake_icon_h.png',
          span: '虚拟商品订单',
          name: ''
        }
      ],
      first_market_manage: [
        {
          imgUrl: '/static/image/admin/new_market/tj.png',
          imgUrl_h: '/static/image/admin/new_market/tj.png',
          span: '',
          name: 'first_market_manage'
        },
        {
          imgUrl: '/static/image/admin/icon_left/bargain.png',
          imgUrl_h: '/static/image/admin/icon_left/bargain_h.png',
          span: '砍价',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/icon_group.png',
          imgUrl_h: '/static/image/admin/icon_left/icon_group_h.png',
          span: '多人拼团',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/img_distribution.png',
          imgUrl_h: '/static/image/admin/icon_left/img_distribution_h.png',
          span: '分销',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/groupdraw.png',
          imgUrl_h: '/static/image/admin/icon_left/groupdraw_h.png',
          span: '拼团抽奖',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/pinintegration.png',
          imgUrl_h: '/static/image/admin/icon_left/pinintegration_h.png',
          span: '瓜分积分',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/friend_promote.png',
          imgUrl_h: '/static/image/admin/icon_left/friend_promote_h.png',
          span: '好友助力',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/icon_lottery.png',
          imgUrl_h: '/static/image/admin/icon_left/icon_lottery_h.png',
          span: '幸运大抽奖',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/icon_gifted.png',
          imgUrl_h: '/static/image/admin/icon_left/icon_gifted_h.png',
          span: '活动有礼',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/icon_payreward.png',
          imgUrl_h: '/static/image/admin/icon_left/icon_payreward_h.png',
          span: '支付有礼',
          name: ''
        }
      ],
      user_manger: [
        {
          imgUrl: '/static/image/admin/icon_left/card.png',
          imgUrl_h: '/static/image/admin/icon_left/card_h.png',
          span: '会员列表',
          name: 'membershipList'
        },
        {
          imgUrl: '/static/image/admin/icon_left/user_import.png',
          imgUrl_h: '/static/image/admin/icon_left/user_import_h.png',
          span: '会员导入',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/user_code.png',
          imgUrl_h: '/static/image/admin/icon_left/user_code_h.png',
          span: '会员卡',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/label_man.png',
          imgUrl_h: '/static/image/admin/icon_left/label_man_h.png',
          span: '标签管理',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/essay_admin.png',
          imgUrl_h: '/static/image/admin/icon_left/essay_admin_h.png',
          span: '积分管理',
          name: ''
        }
      ],
      store_manage: [
        {
          imgUrl: '/static/image/admin/icon_left/store_list.png',
          imgUrl_h: '/static/image/admin/icon_left/store_list_h.png',
          span: '门店列表',
          name: 'store_manage'
        },
        {
          imgUrl: '/static/image/admin/icon_left/store_add.png',
          imgUrl_h: '/static/image/admin/icon_left/store_add_h.png',
          span: '新增门店',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/store_group.png',
          imgUrl_h: '/static/image/admin/icon_left/store_group_h.png',
          span: '分组管理',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/service_config.png',
          imgUrl_h: '/static/image/admin/icon_left/service_config_h.png',
          span: '门店服务配置',
          name: ''
        }
      ],
      base_manger: [
        {
          imgUrl: '/static/image/admin/icon_left/config_list.png',
          imgUrl_h: '/static/image/admin/icon_left/config_list_h.png',
          span: '店铺基础配置',
          name: 'base_manger'
        },
        {
          imgUrl: '/static/image/admin/icon_left/pay_config.png',
          imgUrl_h: '/static/image/admin/icon_left/pay_config_h.png',
          span: '交易配置',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/child_config.png',
          imgUrl_h: '/static/image/admin/icon_left/child_config_h.png',
          span: '店铺权限',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/message_config.png',
          imgUrl_h: '/static/image/admin/icon_left/message_config_h.png',
          span: '模版消息',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/third_party_config.png',
          imgUrl_h: '/static/image/admin/icon_left/third_party_config_h.png',
          span: '第三方对接',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/action_record.png',
          imgUrl_h: '/static/image/admin/icon_left/action_record_h.png',
          span: '操作记录',
          name: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/pledge_config.png',
          imgUrl_h: '/static/image/admin/icon_left/pledge_config_h.png',
          span: '服务承诺',
          name: ''
        }
      ],
      nav_index: '',
      click_nav_index: null,
      nav_s_class_index: false
    }
  },
  watch: {
    $route (to, from) {
      console.log(to.meta)
      this.defaultNav(to.meta)
    }
  },
  mounted () {
    // 初始化左侧菜单及顶部点击左侧菜单显示
    this.defaultNav(this.$route.meta)
  },
  methods: {
    defaultNav (meta) {
      switch (meta) {
        case 'first_web_manage':
          this.navLeftData = this.first_web_manage
          break
        case 'first_web_decoration':
          this.navLeftData = this.first_web_decoration
          break
        case 'goods_manage':
          this.navLeftData = this.goods_manage
          break
        case 'first_trade_manageL':
          this.navLeftData = this.first_trade_manageL
          break
        case 'first_market_manage':
          this.navLeftData = this.first_market_manage
          break
        case 'user_manger':
          this.navLeftData = this.user_manger
          break
        case 'store_manage':
          this.navLeftData = this.store_manage
          break
        case 'base_manger':
          this.navLeftData = this.base_manger
          break
      }
      if (this.$route.name === 'first_market_manage') {
        this.nav_s_class_index = true
      } else {
        this.nav_s_class_index = false
      }
    },
    // 左侧菜单栏点击事件
    leftNavClick (index, name) {
      console.log(name)
      if (name === 'first_market_manage') this.nav_s_class_index = true
      this.click_nav_index = index
      this.$router.push({
        name: name
      })
    },
    // 左侧菜单栏划入事件
    left_nav_over (index) {
      this.click_nav_index = index
    },
    // 左侧菜单栏划出事件
    left_nav_leave (index) {
      this.click_nav_index = null
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
  background: #323a4d;
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
  opacity: 0.7;
}
.active_bg {
  background: #2e3144 !important;
}
.nav_s_class {
  width: 66px;
  height: 26px;
  position: relative;
  left: -11px;
  bottom: -9px;
}
</style>
