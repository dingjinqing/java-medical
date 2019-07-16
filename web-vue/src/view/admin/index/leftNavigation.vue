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
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      navLeftData: '',
      first_web_manage: [
        {
          imgUrl: '/static/image/admin/icon_left/shop_look.png',
          imgUrl_h: '/static/image/admin/icon_left/shop_look_h.png',
          span: '商城概览'
        },
        {
          imgUrl: '/static/image/admin/icon_left/analysis_basic.png',
          imgUrl_h: '/static/image/admin/icon_left/analysis_basic_h.png',
          span: '概况统计'
        },
        {
          imgUrl: '/static/image/admin/icon_left/situation.png',
          imgUrl_h: '/static/image/admin/icon_left/situation_h.png',
          span: '实时概况'
        },
        {
          imgUrl: '/static/image/admin/icon_left/new_analysis_portrait.png',
          imgUrl_h: '/static/image/admin/icon_left/new_analtsis_portrait_h.png',
          span: '用户画像'
        },
        {
          imgUrl: '/static/image/admin/icon_left/analysis_visit.png',
          imgUrl_h: '/static/image/admin/icon_left/analysis_visit_h.png',
          span: '访问分析'
        },
        {
          imgUrl: '/static/image/admin/icon_left/laiyuan.png',
          imgUrl_h: '/static/image/admin/icon_left/laiyuan_h.png',
          span: '来源分析'
        },
        {
          imgUrl: '/static/image/admin/icon_left/new_yonghu.png',
          imgUrl_h: '/static/image/admin/icon_left/new_yonghu_h.png',
          span: '用户统计'
        },
        {
          imgUrl: '/static/image/admin/icon_left/goods_new_ana.png',
          imgUrl_h: '/static/image/admin/icon_left/goods_new_ana_h.png',
          span: '商品统计'
        },
        {
          imgUrl: '/static/image/admin/icon_left/jiaoyi.png',
          imgUrl_h: '/static/image/admin/icon_left/jiaoyi_h.png',
          span: '交易统计'
        },
        {
          imgUrl: '/static/image/admin/icon_left/asset_manage.png',
          imgUrl_h: '/static/image/admin/icon_left/asset_manage_h.png',
          span: '资产管理'
        },
        {
          imgUrl: '/static/image/admin/icon_left/search_config.png',
          imgUrl_h: '/static/image/admin/icon_left/search_config_h.png',
          span: '搜索统计'
        }
      ],
      first_web_decoration: [
        {
          imgUrl: '/static/image/admin/icon_left/page_decoration.png',
          imgUrl_h: '/static/image/admin/icon_left/page_decoration_h.png',
          span: '页面装修'
        },
        {
          imgUrl: '/static/image/admin/icon_left/picture_setting.png',
          imgUrl_h: '/static/image/admin/icon_left/picture_setting_h.png',
          span: '页面分类'
        },
        {
          imgUrl: '/static/image/admin/icon_left/image_list.png',
          imgUrl_h: '/static/image/admin/icon_left/image_list_h.png',
          span: '图片空间'
        },
        {
          imgUrl: '/static/image/admin/icon_left/shop_style.png',
          imgUrl_h: '/static/image/admin/icon_left/shop_style_h.png',
          span: '店铺风格'
        },
        {
          imgUrl: '/static/image/admin/icon_left/picture_space.png',
          imgUrl_h: '/static/image/admin/icon_left/picture_space_h.png',
          span: '底部导航'
        },
        {
          imgUrl: '/static/image/admin/icon_left/user_config.png',
          imgUrl_h: '/static/image/admin/icon_left/user_config_h.png',
          span: '个人中心'
        },
        {
          imgUrl: '/static/image/admin/icon_left/search_config.png',
          imgUrl_h: '/static/image/admin/icon_left/search_config_h.png',
          span: '搜索配置'
        },
        {
          imgUrl: '/static/image/admin/icon_left/mobile_deco.png',
          imgUrl_h: '/static/image/admin/icon_left/mobile_deco_h.png',
          span: '小程序跳转'
        }
      ],
      goods_manage: [
        {
          imgUrl: '/static/image/admin/icon_left/product_in.png',
          imgUrl_h: '/static/image/admin/icon_left/product_in_h.png',
          span: '全部商品'
        },
        {
          imgUrl: '/static/image/admin/icon_left/picture_add.png',
          imgUrl_h: '/static/image/admin/icon_left/picture_add_h.png',
          span: '添加商品'
        },
        {
          imgUrl: '/static/image/admin/icon_left/deliver_tmpl.png',
          imgUrl_h: '/static/image/admin/icon_left/deliver_tmpl_h.png',
          span: '运费模板'
        },
        {
          imgUrl: '/static/image/admin/icon_left/img_sort.png',
          imgUrl_h: '/static/image/admin/icon_left/img_sort_h.png',
          span: '商家分类管理'
        },
        {
          imgUrl: '/static/image/admin/icon_left/brand_icon.png',
          imgUrl_h: '/static/image/admin/icon_left/brand_icon_h.png',
          span: '品牌管理'
        },
        {
          imgUrl: '/static/image/admin/icon_left/comment_man.png',
          imgUrl_h: '/static/image/admin/icon_left/comment_man_h.png',
          span: '评价管理'
        },
        {
          imgUrl: '/static/image/admin/icon_left/recommend_icon.png',
          imgUrl_h: '/static/image/admin/icon_left/recommend_icon_h.png',
          span: '商品推荐'
        },
        {
          imgUrl: '/static/image/admin/icon_left/goods_label1.png',
          imgUrl_h: '/static/image/admin/icon_left/goods_label1_h.png',
          span: '商品标签'
        },
        {
          imgUrl: '/static/image/admin/icon_left/user_import.png',
          imgUrl_h: '/static/image/admin/icon_left/user_import_h.png',
          span: '商品导入'
        }
      ],
      first_trade_manageL: [
        {
          imgUrl: '/static/image/admin/icon_left/all_order.png',
          imgUrl_h: '/static/image/admin/icon_left/all_order_h.png',
          span: '全部订单'
        },
        {
          imgUrl: '/static/image/admin/icon_left/wait_order.png',
          imgUrl_h: '/static/image/admin/icon_left/wait_order_h.png',
          span: '待发货订单'
        },
        {
          imgUrl: '/static/image/admin/icon_left/return_order.png',
          imgUrl_h: '/static/image/admin/icon_left/return_order_h.png',
          span: '退货退款订单'
        },
        {
          imgUrl: '/static/image/admin/icon_left/self_order.png',
          imgUrl_h: '/static/image/admin/icon_left/self_order_h.png',
          span: '自提订单'
        },
        {
          imgUrl: '/static/image/admin/icon_left/pin_group_fail.png',
          imgUrl_h: '/static/image/admin/icon_left/pin_group_fail_h.png',
          span: '拼团退款失败单'
        },
        {
          imgUrl: '/static/image/admin/icon_left/checkout.png',
          imgUrl_h: '/static/image/admin/icon_left/checkout_h.png',
          span: '买单订单'
        },
        {
          imgUrl: '/static/image/admin/icon_left/fake_icon.png',
          imgUrl_h: '/static/image/admin/icon_left/fake_icon_h.png',
          span: '虚拟商品订单'
        }
      ],
      first_market_manage: [
        {
          imgUrl: '/static/image/admin/new_market/tj.png',
          imgUrl_h: '/static/image/admin/new_market/tj.png',
          span: ''
        },
        {
          imgUrl: '/static/image/admin/icon_left/bargain.png',
          imgUrl_h: '/static/image/admin/icon_left/bargain_h.png',
          span: '砍价'
        },
        {
          imgUrl: '/static/image/admin/icon_left/icon_group.png',
          imgUrl_h: '/static/image/admin/icon_left/icon_group_h.png',
          span: '多人拼团'
        },
        {
          imgUrl: '/static/image/admin/icon_left/img_distribution.png',
          imgUrl_h: '/static/image/admin/icon_left/img_distribution_h.png',
          span: '分销'
        },
        {
          imgUrl: '/static/image/admin/icon_left/groupdraw.png',
          imgUrl_h: '/static/image/admin/icon_left/groupdraw_h.png',
          span: '拼团抽奖'
        },
        {
          imgUrl: '/static/image/admin/icon_left/pinintegration.png',
          imgUrl_h: '/static/image/admin/icon_left/pinintegration_h.png',
          span: '瓜分积分'
        },
        {
          imgUrl: '/static/image/admin/icon_left/friend_promote.png',
          imgUrl_h: '/static/image/admin/icon_left/friend_promote_h.png',
          span: '好友助力'
        },
        {
          imgUrl: '/static/image/admin/icon_left/icon_lottery.png',
          imgUrl_h: '/static/image/admin/icon_left/icon_lottery_h.png',
          span: '幸运大抽奖'
        },
        {
          imgUrl: '/static/image/admin/icon_left/icon_gifted.png',
          imgUrl_h: '/static/image/admin/icon_left/icon_gifted_h.png',
          span: '活动有礼'
        },
        {
          imgUrl: '/static/image/admin/icon_left/icon_payreward.png',
          imgUrl_h: '/static/image/admin/icon_left/icon_payreward_h.png',
          span: '支付有礼'
        }
      ],
      user_manger: [
        {
          imgUrl: '/static/image/admin/icon_left/card.png',
          imgUrl_h: '/static/image/admin/icon_left/card_h.png',
          span: '会员列表'
        },
        {
          imgUrl: '/static/image/admin/icon_left/user_import.png',
          imgUrl_h: '/static/image/admin/icon_left/user_import_h.png',
          span: '会员导入'
        },
        {
          imgUrl: '/static/image/admin/icon_left/user_code.png',
          imgUrl_h: '/static/image/admin/icon_left/user_code_h.png',
          span: '会员卡'
        },
        {
          imgUrl: '/static/image/admin/icon_left/label_man.png',
          imgUrl_h: '/static/image/admin/icon_left/label_man_h.png',
          span: '标签管理'
        },
        {
          imgUrl: '/static/image/admin/icon_left/essay_admin.png',
          imgUrl_h: '/static/image/admin/icon_left/essay_admin_h.png',
          span: '积分管理'
        }
      ],
      store_manage: [
        {
          imgUrl: '/static/image/admin/icon_left/store_list.png',
          imgUrl_h: '/static/image/admin/icon_left/store_list_h.png',
          span: '门店列表'
        },
        {
          imgUrl: '/static/image/admin/icon_left/store_add.png',
          imgUrl_h: '/static/image/admin/icon_left/store_add_h.png',
          span: '新增门店'
        },
        {
          imgUrl: '/static/image/admin/icon_left/store_group.png',
          imgUrl_h: '/static/image/admin/icon_left/store_group_h.png',
          span: '分组管理'
        },
        {
          imgUrl: '/static/image/admin/icon_left/service_config.png',
          imgUrl_h: '/static/image/admin/icon_left/service_config_h.png',
          span: '门店服务配置'
        }
      ],
      base_manger: [
        {
          imgUrl: '/static/image/admin/icon_left/config_list.png',
          imgUrl_h: '/static/image/admin/icon_left/config_list_h.png',
          span: '店铺基础配置'
        },
        {
          imgUrl: '/static/image/admin/icon_left/pay_config.png',
          imgUrl_h: '/static/image/admin/icon_left/pay_config_h.png',
          span: '交易配置'
        },
        {
          imgUrl: '/static/image/admin/icon_left/child_config.png',
          imgUrl_h: '/static/image/admin/icon_left/child_config_h.png',
          span: '店铺权限'
        },
        {
          imgUrl: '/static/image/admin/icon_left/message_config.png',
          imgUrl_h: '/static/image/admin/icon_left/message_config_h.png',
          span: '模版消息'
        },
        {
          imgUrl: '/static/image/admin/icon_left/third_party_config.png',
          imgUrl_h: '/static/image/admin/icon_left/third_party_config_h.png',
          span: '第三方对接'
        },
        {
          imgUrl: '/static/image/admin/icon_left/action_record.png',
          imgUrl_h: '/static/image/admin/icon_left/action_record_h.png',
          span: '操作记录'
        },
        {
          imgUrl: '/static/image/admin/icon_left/pledge_config.png',
          imgUrl_h: '/static/image/admin/icon_left/pledge_config_h.png',
          span: '服务承诺'
        }
      ],
      nav_index: '',
      click_nav_index: '',
      nav_s_class_index: false,
      flag: ''
    }
  },
  computed: {
    ...mapGetters(['admin_leftVav_flag']),
    admin_leftVav_flag1 () {
      return this.admin_leftVav_flag
    }
  },
  watch: {
    admin_leftVav_flag1 (newData, oldData) {
      this.judgeNav()
    }
  },
  mounted () {
    // 初始化左侧菜单及顶部点击左侧菜单显示
    this.judgeNav()
  },
  methods: {
    judgeNav () {
      console.log(this.admin_leftVav_flag)
      this.flag = this.admin_leftVav_flag
      switch (this.flag) {
        case 'first_web_manage':
          this.handleLeftNav(this.first_web_manage, false, this.flag)
          break
        case 'first_web_decoration':
          this.handleLeftNav(this.first_web_decoration, false, this.flag)
          break
        case 'goods_manage':
          this.handleLeftNav(this.goods_manage, false)
          break
        case 'first_trade_manageL':
          this.handleLeftNav(this.first_trade_manageL, false)
          break
        case 'first_market_manage':
          this.handleLeftNav(this.first_market_manage, true)
          break
        case 'user_manger':
          this.handleLeftNav(this.user_manger, false)
          break
        case 'store_manage':
          this.handleLeftNav(this.store_manage, false)
          break
        case 'base_manger':
          this.handleLeftNav(this.base_manger, false)
          break
      }
    },
    // 页面主动刷新初始化函数
    handleLeftNav (show, boolean, flag) {
      this.navLeftData = show
      this.nav_s_class_index = boolean
      this.nav_index = ''
      this.click_nav_index = ''
      let name = this.$route.name
      console.log(name, flag)

      switch (name) {
        // 小程序管理
        case 'page_classification':
          if (flag === 'first_web_decoration') {
            this.nav_index = 1
            this.click_nav_index = 1
          }
          break
        case 'freight_template':
          if (flag === 'first_web_decoration') {
            this.nav_index = 2
            this.click_nav_index = 2
          }
          break
        case 'overviewStatistics':
          console.log('sun==' + flag)
          if (flag === 'first_web_manage') {
            this.nav_index = 1
            this.click_nav_index = 1
          }
      }
    },
    // 左侧菜单栏点击事件
    leftNavClick (index) {
      this.click_nav_index = index
      console.log(this.flag)
      // 概况模块左侧点击
      if (this.flag === 'first_web_manage') {
        switch (index) {
          case 0:
            this.$router.push({
              name: 'overviewOfMall'
            })
            break
          case 1:
            this.$router.push({
              name: 'overviewStatistics'
            })
            break
        }
      }
      // 小程序管理模块左侧点击
      if (this.flag === 'first_web_decoration') {
        switch (index) {
          case 0:
            this.$router.push({
              name: 'first_web_decoration'
            })
            break
          case 1:
            console.log('qqq')
            this.$router.push({
              name: 'page_classification'
            })
            break
          case 2:
            this.$router.push({
              name: 'freight_template'
            })
            break
          case 3:
            this.$router.push({
              name: 'shopStyle'
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
