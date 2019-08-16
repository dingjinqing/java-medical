<template>
  <div class="container">
    <div class="left_menu">
      <ul>
        <li
          v-for="(item,index) in navLeftData"
          :key="index"
          :class="$route.name == item.name||click_nav_index==index||saveIndex == index||$route.meta.category == item.name?'active_bg':''"
          @click="leftNavClick(index,item.name)"
          @mouseover="left_nav_over(index)"
          @mouseleave="left_nav_leave(index)"
          style="cursor:pointer"
        >
          <div
            class="lestList"
            v-if="item.flag"
          >
            <img
              :src="nav_index==index||click_nav_index==index?item.imgUrl_h:item.imgUrl"
              :class="nav_s_class_index&&index==0||$route.meta.category == item.name?'nav_s_class':''"
            >
            <span>{{item.span}}</span>
          </div>

        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import { jurisdictionQueryRequest } from '@/api/admin/jurisdiction'
export default {
  data () {
    return {
      navLeftData: '',
      dataList: {
        first_web_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/shop_look.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/shop_look_h.png',
            span: '商城概览',
            name: 'shop_view',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/analysis_basic.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/analysis_basic_h.png',
            span: '概况统计',
            name: 'analysis_basic',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/situation.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/situation_h.png',
            span: '实时概况',
            name: 'situation',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/new_analysis_portrait.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/new_analtsis_portrait_h.png',
            span: '用户画像',
            name: 'analysis_portrait',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/analysis_visit.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/analysis_visit_h.png',
            span: '访问分析',
            name: 'analysis_visit',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/laiyuan.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/laiyuan_h.png',
            span: '来源分析',
            name: 'analysis_visit_source',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/new_yonghu.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/new_yonghu_h.png',
            span: '用户统计',
            name: 'user_summary',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/goods_new_ana.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/goods_new_ana_h.png',
            span: '商品统计',
            name: 'goods_summary',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/jiaoyi.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/jiaoyi_h.png',
            span: '交易统计',
            name: 'trades_summary',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/asset_manage.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/asset_manage_h.png',
            span: '资产管理',
            name: 'asset_summary',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/search_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/search_config_h.png',
            span: '搜索统计',
            name: 'search_summary',
            flag: false
          }
        ],
        first_web_decoration: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/page_decoration.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/page_decoration_h.png',
            span: '页面装修',
            name: 'picture_setting',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/picture_setting.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_setting_h.png',
            span: '页面分类',
            name: 'page_classification',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/image_list.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/image_list_h.png',
            span: '图片空间',
            name: 'image_list',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/shop_style.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/shop_style_h.png',
            span: '店铺风格',
            name: 'shop_style',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/picture_space.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_space_h.png',
            span: '底部导航',
            name: 'image_manager',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/user_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_config_h.png',
            span: '个人中心',
            name: 'user_center_config',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/search_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/search_config_h.png',
            span: '搜索配置',
            name: 'search_config',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/mobile_deco.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/mobile_deco_h.png',
            span: '小程序跳转',
            name: 'mp_jump_list',
            flag: false
          }
        ],
        goods_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
            span: '全部商品',
            name: 'sale_on',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/picture_add.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_add_h.png',
            span: '添加商品',
            name: 'goods_add',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/deliver_tmpl.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/deliver_tmpl_h.png',
            span: '运费模板',
            name: 'deliver',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/img_sort.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/img_sort_h.png',
            span: '商家分类管理',
            name: 'sort',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/brand_icon.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/brand_icon_h.png',
            span: '品牌管理',
            name: 'brand',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/comment_man.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/comment_man_h.png',
            span: '评价管理',
            name: 'comment',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/recommend_icon.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/recommend_icon_h.png',
            span: '商品推荐',
            name: 'recommend',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/goods_label1.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/goods_label1_h.png',
            span: '商品标签',
            name: 'label',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/user_import.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_import_h.png',
            span: '商品导入',
            name: 'goods_import',
            flag: false
          }
        ],
        first_trade_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/all_order.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/all_order_h.png',
            span: '全部订单',
            name: 'order',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/wait_order.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/wait_order_h.png',
            span: '待发货订单',
            name: 'order_wait',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/return_order.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/return_order_h.png',
            span: '退货退款订单',
            name: 'order_return',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/self_order.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/self_order_h.png',
            span: '自提订单',
            name: 'shop_setting',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/pin_group_fail.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/pin_group_fail_h.png',
            span: '拼团退款失败单',
            name: 'pin_group_fail',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/checkout.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/checkout_h.png',
            span: '买单订单',
            name: 'check_order',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/fake_icon.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/fake_icon_h.png',
            span: '虚拟商品订单',
            name: 'member_card_order',
            flag: false
          }
        ],
        first_market_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/tj.png',
            imgUrl_h: this.$imageHost + '/image/admin/new_market/tj.png',
            span: '',
            name: 'first_market_manage',
            flag: true
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/bargain.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/bargain_h.png',
            span: '砍价',
            name: 'kanjia',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/icon_group.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_group_h.png',
            span: '多人拼团',
            name: 'pin_group',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/img_distribution.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/img_distribution_h.png',
            span: '分销',
            name: 'distribution_info',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/groupdraw.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/groupdraw_h.png',
            span: '拼团抽奖',
            name: 'group_draw',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/pinintegration.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/pinintegration_h.png',
            span: '瓜分积分',
            name: 'pin_integration',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/friend_promote.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/friend_promote_h.png',
            span: '好友助力',
            name: 'promote',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/icon_lottery.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_lottery_h.png',
            span: '幸运大抽奖',
            name: 'lottery_activity',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/icon_gifted.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_gifted_h.png',
            span: '活动有礼',
            name: 'market_gifted',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/icon_payreward.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_payreward_h.png',
            span: '支付有礼',
            name: 'payreward',
            flag: false
          }
        ],
        user_manger: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/card.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/card_h.png',
            span: '会员列表',
            name: 'user_list',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/user_import.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_import_h.png',
            span: '会员导入',
            name: 'user_import',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/user_code.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_code_h.png',
            span: '会员卡',
            name: 'user_card',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/label_man.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/label_man_h.png',
            span: '标签管理',
            name: 'user_tag',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/essay_admin.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/essay_admin_h.png',
            span: '积分管理',
            name: 'score',
            flag: false
          }
        ],
        store_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/store_list.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_list_h.png',
            span: '门店列表',
            name: 'store_list',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/store_add.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_add_h.png',
            span: '新增门店',
            name: 'add_store',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/store_group.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_group_h.png',
            span: '分组管理',
            name: 'group_manage',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/service_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/service_config_h.png',
            span: '门店服务配置',
            name: 'store_service_config',
            flag: false
          }
        ],
        base_manger: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/config_list.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/config_list_h.png',
            span: '店铺基础配置',
            name: 'config_list',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/pay_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/pay_config_h.png',
            span: '交易配置',
            name: 'pay',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/child_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/child_config_h.png',
            span: '店铺权限',
            name: 'child_config',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/message_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/message_config_h.png',
            span: '模版消息',
            name: 'message_config',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/third_party_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/third_party_config_h.png',
            span: '第三方对接',
            name: 'third_auth',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/action_record.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/action_record_h.png',
            span: '操作记录',
            name: 'action_record',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/pledge_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/pledge_config_h.png',
            span: '服务承诺',
            name: 'pledge',
            flag: false
          }
        ]
      },

      nav_index: '',
      click_nav_index: null,
      nav_s_class_index: false,
      saveIndex: null
    }
  },
  watch: {
    $route (to, from) {
      console.log(to)

      console.log(this.flag)
      this.defaultNav(to.meta.meta)
    }
  },
  mounted () {
    console.log(this.$route)
    // 权限处理
    this.handleJurisdiction()
  },
  methods: {
    ...mapActions(['judgeMenuAll']),
    handleJurisdiction () {
      jurisdictionQueryRequest().then((res) => {
        console.log(res)
        for (let i in this.dataList) {
          this.dataList[i].map((itemp, indexp) => {
            res.content.menuParam[i].map((itemchildren, indexchildren) => {
              if (itemp.name === itemchildren) {
                console.log(itemp.name, itemchildren)
                itemp.flag = true
              }
            })
          })
        }
        // 初始化左侧菜单及顶部点击左侧菜单显示
        this.defaultNav(this.$route.meta.meta)
        console.log(this.dataList)
      })
    },
    defaultNav (meta) {
      console.log(meta)
      console.log(this.dataList)
      switch (meta) {
        case 'first_web_manage':
          this.navLeftData = this.dataList['first_web_manage']
          break
        case 'first_web_decoration':
          this.navLeftData = this.dataList['first_web_decoration']
          break
        case 'goods_manage':
          console.log(this.dataList['goods_manage'])
          this.navLeftData = this.dataList['goods_manage']
          break
        case 'first_trade_manage':
          this.navLeftData = this.dataList['first_trade_manage']
          break
        case 'first_market_manage':
          this.navLeftData = this.dataList['first_market_manage']
          break
        case 'user_manger':
          this.navLeftData = this.dataList['user_manger']
          break
        case 'store_manage':
          this.navLeftData = this.dataList['store_manage']
          break
        case 'base_manger':
          this.navLeftData = this.dataList['base_manger']
          break
      }
      if (this.$route.name === 'first_market_manage') {
        this.nav_s_class_index = true
      } else {
        this.nav_s_class_index = false
      }
      this.$http.$on('resit', (res) => {
        this.saveIndex = 0
      })
      console.log(this.navLeftData)
      if (this.navLeftData.length === 0) {
        this.judgeMenuAll(false)
      } else {
        this.judgeMenuAll(true)
      }
      console.log(this.saveIndex)
      console.log(this.navLeftData)
    },
    // 左侧菜单栏点击事件
    leftNavClick (index, name) {
      console.log(index, name)

      if (name === 'first_market_manage') this.nav_s_class_index = true
      this.click_nav_index = index
      this.saveIndex = index
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

.lestList img {
  display: block;
  float: left;
  width: 20px;
  height: 20px;
  margin: auto 0;
}
.lestList span {
  display: block;
  float: left;
  color: #fff;
  margin-left: 17px;
  font-size: 14px;
  height: 45px;
  line-height: 45px;
  opacity: 0.7;
}
.lestList {
  height: 45px;
  line-height: 45px;
  padding-left: 12px;
  overflow: hidden;
  display: flex;
}
.active_bg {
  background: #2e3144 !important;
  color: #fff;
}
.active_bg span {
  opacity: 1 !important;
}
.nav_s_class {
  width: 66px !important;
  height: 26px !important;
  position: relative;
  left: -11px;
  bottom: -9px;
}
</style>
