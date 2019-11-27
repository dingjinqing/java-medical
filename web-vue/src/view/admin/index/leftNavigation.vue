<template>
  <div
    class="container"
    :class="leftMenuEn"
  >
    <div
      class="left_menu"
      :class="leftMenuEn"
    >
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
              :class="(nav_s_class_index||$route.meta.meta==='first_market_manage')&&index==0?'nav_s_class':''"
            >
            <span>{{item.span}}</span>
          </div>

        </li>
      </ul>
    </div>
  </div>
</template>
<script>
/* eslint-disable */
import {
  judgeJurisdictionRequest
} from '@/api/admin/util.js'
import { mapActions } from 'vuex'
import { jurisdictionQueryRequest } from '@/api/admin/jurisdiction'
export default {
  data() {
    return {
      navLeftData: '',
      dataList: {
        first_web_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/shop_look.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/shop_look_h.png',
            span: '',
            name: 'shop_view',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/analysis_basic.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/analysis_basic_h.png',
            span: '',
            name: 'analysis_basic',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/situation.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/situation_h.png',
            span: '',
            name: 'situation',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/new_analysis_portrait.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/new_analtsis_portrait_h.png',
            span: '',
            name: 'analysis_portrait',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/analysis_visit.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/analysis_visit_h.png',
            span: '',
            name: 'analysis_visit',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/laiyuan.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/laiyuan_h.png',
            span: '',
            name: 'analysis_visit_source',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/new_yonghu.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/new_yonghu_h.png',
            span: '',
            name: 'user_summary',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/goods_new_ana.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/goods_new_ana_h.png',
            span: '',
            name: 'goods_summary',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/jiaoyi.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/jiaoyi_h.png',
            span: '',
            name: 'trades_summary',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/asset_manage.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/asset_manage_h.png',
            span: '',
            name: 'asset_summary',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/search_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/search_config_h.png',
            span: '',
            name: 'search_summary',
            flag: false
          }
        ],
        first_web_decoration: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/page_decoration.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/page_decoration_h.png',
            span: '',
            name: 'picture_setting',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/picture_setting.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_setting_h.png',
            span: '',
            name: 'page_classification',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/image_list.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/image_list_h.png',
            span: '',
            name: 'image_list',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/shop_style.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/shop_style_h.png',
            span: '',
            name: 'shop_style',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/picture_space.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_space_h.png',
            span: '',
            name: 'image_manager',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/user_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_config_h.png',
            span: '',
            name: 'user_center_config',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/search_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/search_config_h.png',
            span: '',
            name: 'search_config',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/mobile_deco.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/mobile_deco_h.png',
            span: '',
            name: 'mp_jump_list',
            flag: false
          }
        ],
        goods_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
            span: '',
            name: 'sale_on',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/picture_add.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_add_h.png',
            span: '',
            name: 'goods_add',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/deliver_tmpl.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/deliver_tmpl_h.png',
            span: '',
            name: 'deliver',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/img_sort.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/img_sort_h.png',
            span: '',
            name: 'sort',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/brand_icon.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/brand_icon_h.png',
            span: '',
            name: 'brand',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/comment_man.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/comment_man_h.png',
            span: '',
            name: 'comment',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/recommend_icon.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/recommend_icon_h.png',
            span: '',
            name: 'recommend',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/goods_label1.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/goods_label1_h.png',
            span: '',
            name: 'label',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/user_import.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_import_h.png',
            span: '',
            name: 'goods_import',
            flag: false
          }
        ],
        first_trade_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/all_order.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/all_order_h.png',
            span: '',
            name: 'order',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/wait_order.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/wait_order_h.png',
            span: '',
            name: 'order_wait',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/return_order.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/return_order_h.png',
            span: '',
            name: 'order_return',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/self_order.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/self_order_h.png',
            span: '',
            name: 'shop_setting',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/pin_group_fail.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/pin_group_fail_h.png',
            span: '',
            name: 'pin_group_fail',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/checkout.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/checkout_h.png',
            span: '',
            name: 'check_order',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/fake_icon.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/fake_icon_h.png',
            span: '',
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
            span: '',
            name: 'bargain',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/icon_group.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_group_h.png',
            span: '',
            name: 'pin_group',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/img_distribution.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/img_distribution_h.png',
            span: '',
            name: 'distribution_info',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/groupdraw.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/groupdraw_h.png',
            span: '',
            name: 'group_draw',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/pinintegration.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/pinintegration_h.png',
            span: '',
            name: 'pin_integration',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/friend_promote.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/friend_promote_h.png',
            span: '',
            name: 'promote',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/icon_lottery.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_lottery_h.png',
            span: '',
            name: 'lottery_activity',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/icon_gifted.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_gifted_h.png',
            span: '',
            name: 'market_gifted',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/icon_payreward.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_payreward_h.png',
            span: '',
            name: 'payreward',
            flag: false
          }
        ],
        user_manger: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/card.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/card_h.png',
            span: '',
            name: 'user_list',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/user_import.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_import_h.png',
            span: '',
            name: 'user_import',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/user_code.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_code_h.png',
            span: '',
            name: 'user_card',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/label_man.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/label_man_h.png',
            span: '',
            name: 'user_tag',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/essay_admin.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/essay_admin_h.png',
            span: '',
            name: 'score',
            flag: false
          }
        ],
        store_manage: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/store_list.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_list_h.png',
            span: '',
            name: 'store_list',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/store_add.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_add_h.png',
            span: '',
            name: 'add_store',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/store_group.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_group_h.png',
            span: '',
            name: 'group_manage',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/service_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/service_config_h.png',
            span: '',
            name: 'store_service_config',
            flag: false
          }
        ],
        base_manger: [
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/config_list.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/config_list_h.png',
            span: '',
            name: 'config_list',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/pay_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/pay_config_h.png',
            span: '',
            name: 'pay',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/child_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/child_config_h.png',
            span: '',
            name: 'child_config',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/message_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/message_config_h.png',
            span: '',
            name: 'message_config',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/third_party_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/third_party_config_h.png',
            span: '',
            name: 'third_auth',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/action_record.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/action_record_h.png',
            span: '',
            name: 'action_record',
            flag: false
          },
          {
            imgUrl: this.$imageHost + '/image/admin/icon_left/pledge_config.png',
            imgUrl_h: this.$imageHost + '/image/admin/icon_left/pledge_config_h.png',
            span: '',
            name: 'pledge',
            flag: false
          }
        ]
      },

      nav_index: '',
      click_nav_index: null,
      nav_s_class_index: false,
      saveIndex: null,
      leftMenuEn: '',

    }
  },
  watch: {
    $route(to, from) {
      console.log(to)
      console.log(this.$route, this.click_nav_index, this.saveIndex)
      this.saveIndex = -1
      this.defaultNav(to.meta.meta)
    },
    lang(newData) {
      console.log(newData)
      this.handleJurisdiction()
    }
  },
  mounted() {
    console.log(this.$route)
    //初始化语言
    this.langDefault()
    // 权限处理
    // this.handleJurisdiction()
  },
  methods: {
    ...mapActions(['judgeMenuAll']),
    handleJurisdiction() {
      jurisdictionQueryRequest().then((res) => {
        console.log(res)
        for (let i in this.dataList) {
          this.dataList[i].map((itemp, indexp) => {
            res.content.menuParam[i].map((itemchildren, indexchildren) => {
              if (itemp.name === itemchildren) {
                // console.log(itemp.name, itemchildren)
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
    async defaultNav(meta) {
      console.log(meta)
      console.log(this.dataList)
      switch (meta) {
        case 'first_web_manage':
          // this.dataList['first_web_manage'][0].span = this.$t('adminPageFramework.shop_view')
          this.dataList['first_web_manage'].map((item, index) => {
            this.dataList['first_web_manage'][index].span = this.$t(`adminPageFramework.leftNavArr.nav1[${index}]`)
          })
          this.navLeftData = this.dataList['first_web_manage']
          break
        case 'first_web_decoration':
          this.dataList['first_web_decoration'].map((item, index) => {
            this.dataList['first_web_decoration'][index].span = this.$t(`adminPageFramework.leftNavArr.nav2[${index}]`)
          })
          this.navLeftData = this.dataList['first_web_decoration']
          break
        case 'goods_manage':
          console.log(this.dataList['goods_manage'])
          this.dataList['goods_manage'].map((item, index) => {
            this.dataList['goods_manage'][index].span = this.$t(`adminPageFramework.leftNavArr.nav3[${index}]`)
          })
          this.navLeftData = this.dataList['goods_manage']
          break
        case 'first_trade_manage':
          this.dataList['first_trade_manage'].map((item, index) => {
            this.dataList['first_trade_manage'][index].span = this.$t(`adminPageFramework.leftNavArr.nav4[${index}]`)
          })
          this.navLeftData = this.dataList['first_trade_manage']
          break
        case 'first_market_manage':
          this.dataList['first_market_manage'].map((item, index) => {
            this.dataList['first_market_manage'][index].span = this.$t(`adminPageFramework.leftNavArr.nav5[${index}]`)
          })
          this.navLeftData = this.dataList['first_market_manage']
          break
        case 'user_manger':
          this.dataList['user_manger'].map((item, index) => {
            this.dataList['user_manger'][index].span = this.$t(`adminPageFramework.leftNavArr.nav6[${index}]`)
          })
          this.navLeftData = this.dataList['user_manger']
          break
        case 'store_manage':
          this.dataList['store_manage'].map((item, index) => {
            this.dataList['store_manage'][index].span = this.$t(`adminPageFramework.leftNavArr.nav7[${index}]`)
          })
          this.navLeftData = this.dataList['store_manage']
          break
        case 'base_manger':
          this.dataList['base_manger'].map((item, index) => {
            this.dataList['base_manger'][index].span = this.$t(`adminPageFramework.leftNavArr.nav8[${index}]`)
          })
          this.navLeftData = this.dataList['base_manger']
          break
      }
      console.log(this.$route.name)
      if (this.$route.name === 'first_market_manage' || this.$route.name === 'ordinary_coupon') {
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
    leftNavClick(index, name) {
      // 判断二级菜单事件
      this.handleToJudgeTwoDiction(name)

      if (name === 'first_market_manage') this.nav_s_class_index = true
      this.click_nav_index = index
      console.log(index)
      this.saveIndex = index
      if (name === 'config_list') {
        this.$router.push({
          name: name,
          params: {
            isAuth: '-1'
          }
        })
      } else if (name === "order_wait") {
        this.$router.push({
          name: name,
          query: {
            order_status: 3
          }
        })
      } else if (name === "pin_group_fail") {
        this.$router.push({
          name: name,
          query: {
            pinStatus: '2'
          }
        })
      } else {
        this.$router.push({
          name: name
        })
      }
    },
    // 左侧菜单栏划入事件
    left_nav_over(index) {
      this.click_nav_index = index
    },
    // 左侧菜单栏划出事件
    left_nav_leave(index) {
      this.click_nav_index = null
    },
    handleToJudgeTwoDiction(name) {
      console.log(name)
      // 请求功能或者菜单对应的版本名字池
      let vsNameArr = ['basic_yesterday', 'portrait_user', 'second_view', 'visit_source', 'analysis_visit_source']
      // 二级菜单需要校验的元素池
      let enNameArr = ['analysis_basic', 'analysis_portrait', 'analysis_visit', 'analysis_visit_source', 'trades_summary', 'sort']
      console.log(enNameArr.indexOf(name))
      if (enNameArr.indexOf('name') !== -1) {

        judgeJurisdictionRequest({
          'V-EnName': name
        }).then()
      }

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
.leftMenuEn {
  width: 190px !important;
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
  /* left: -11px;
  bottom: -9px; */
}
</style>
