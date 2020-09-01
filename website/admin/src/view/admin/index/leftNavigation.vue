<template>
  <div class="container">
    <el-menu
      class="el-menu-vertical-demo"
      background-color="#323a4d"
      text-color="#ccc"
      active-text-color="#fff"
      :unique-opened="true"
      :router="true"
      :default-active="$route.path"
      v-if="isRouterAlive"
    >
      <template v-for="(listItem, listIndex) in defaultList.list">
        <el-submenu
          :index="listItem.path"
          :key="listIndex"
          v-if="listItem.children && listItem.children.length"
        >
          <template slot="title">
            <img :src="listItem.imgUrl" />
            <span>{{ listItem.name | getNavName }}</span>
          </template>
          <el-menu-item
            :index="childrenItem.path"
            v-for="(childrenItem, childrenIndex) in listItem.children"
            :key="childrenIndex"
          >
            <span slot="title">{{ childrenItem.name | getNavName }}</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item :index="listItem.path" :key="listIndex" v-else>
          <img :src="listItem.imgUrl" />
          <span slot="title" v-if="!listItem.onlyPic">{{
            listItem.name | getNavName
          }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
import { jurisdictionQueryRequest } from '@/api/admin/jurisdiction'
import vm from '@/main'
export default {
  data () {
    return {
      defaultList: {},
      first_web_manage: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/shop_look.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/shop_look_h.png',
          path: '/admin/home/main/overviewOfMall',
          span: '',
          name: 'shop_view',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/analysis_basic.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/analysis_basic_h.png',
          path: '/admin/home/main/overviewStatistics',
          span: '',
          name: 'analysis_basic',
          flag: false,
          children: [
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/analysis_basic.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/analysis_basic_h.png',
              path: '/admin/home/main/overviewStatistics',
              span: '',
              name: 'analysis_basic',
              flag: false
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/situation.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/situation_h.png',
              path: '/admin/home/main/realTimeOverview',
              span: '',
              name: 'situation',
              flag: false
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/new_analysis_portrait.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/new_analtsis_portrait_h.png',
              path: '/admin/home/main/userportrait',
              span: '',
              name: 'analysis_portrait',
              flag: false
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/analysis_visit.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/analysis_visit_h.png',
              path: '/admin/home/main/visitAnalysis',
              span: '',
              name: 'analysis_visit',
              flag: false
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/laiyuan.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/laiyuan_h.png',
              path: '/admin/home/main/sourceAnalysis',
              span: '',
              name: 'analysis_visit_source',
              flag: false
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/new_yonghu.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/new_yonghu_h.png',
              path: '/admin/home/main/userStatistics',
              span: '',
              name: 'user_summary',
              flag: false
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/goods_new_ana.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/goods_new_ana_h.png',
              path: '/admin/home/main/goodsStatistics',
              span: '',
              name: 'goods_summary',
              flag: false
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/jiaoyi.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/jiaoyi_h.png',
              path: '/admin/home/main/tradesStatistics',
              span: '',
              name: 'trades_summary',
              flag: false
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/search_config.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/search_config_h.png',
              path: '/admin/home/main/searchStatistics',
              span: '',
              name: 'search_summary',
              flag: false
            }
          ]
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/sales_report.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/sale_report_h.png',
          path: '/admin/home/main/salesreport',
          span: '',
          name: 'sales_report',
          flag: true,
          children: [
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/sales_report.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/sale_report_h.png',
              path: '/admin/home/main/salesreport',
              span: '',
              name: 'sales_report',
              flag: true
            },
            {
              imgUrl: this.$imageHost + '/image/admin/icon_left/advisory_order.png',
              imgUrl_h: this.$imageHost + '/image/admin/icon_left/advisory_order_h.png',
              path: '/admin/home/main/advisory/total',
              span: '',
              name: 'advisoryTotal',
              flag: true
            }
          ]
        }
      ],
      first_web_decoration: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/page_decoration.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/page_decoration_h.png',
          path: '/admin/home/main/pictureSetting',
          span: '',
          name: 'picture_setting',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/picture_setting.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_setting_h.png',
          path: '/admin/home/main/page_classification',
          span: '',
          name: 'page_classification',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/image_list.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/image_list_h.png',
          path: '/admin/home/main/freight_template',
          span: '',
          name: 'image_list',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/shop_style.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/shop_style_h.png',
          path: '/admin/home/main/shopStyle',
          span: '',
          name: 'shop_style',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/picture_space.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_space_h.png',
          path: '/admin/home/main/bottomNavigation',
          span: '',
          name: 'image_manager',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/user_config.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_config_h.png',
          path: '/admin/home/main/personalCenter',
          span: '',
          name: 'user_center_config',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/search_config.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/search_config_h.png',
          path: '/admin/home/main/searchConfig',
          span: '',
          name: 'search_config',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/suspend_window.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/suspend_window_h.png',
          path: '/admin/home/main/suspendWindow',
          span: '',
          name: 'suspendWindow',
          flag: true
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
          path: '/admin/home/main/goodsManage/goodsForSale',
          span: '',
          name: 'sale_on',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/sale_end.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/sale_end_h.png',
          path: '/admin/home/main/goodsManage/goodsForSaleOut',
          span: '',
          name: 'sale_out',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/picture_add.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/picture_add_h.png',
          path: '/admin/home/main/goodsManage/addGoods',
          span: '',
          name: 'goods_add',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/deliver_tmpl.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/deliver_tmpl_h.png',
          path: '/admin/home/main/goodsManage/deliverTemplate',
          span: '',
          name: 'deliver',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/img_sort.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/img_sort_h.png',
          path: '/admin/home/main/goodsManage/goodsSortManagement/allGoodsSort',
          span: '',
          name: 'sort',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/brand_icon.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/brand_icon_h.png',
          path: '/admin/home/main/goodsManage/brandManagement',
          span: '',
          name: 'brand',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/comment_man.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/comment_man_h.png',
          path: '/admin/home/main/goodsManage/evaluationManagement',
          span: '',
          name: 'comment',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/recommend_icon.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/recommend_icon_h.png',
          path: '/admin/home/main/goodsManage/goodsRecommend',
          span: '',
          name: 'recommend',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/goods_label1.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/goods_label1_h.png',
          path: '/admin/home/main/goodsManage/goodsLabel',
          span: '',
          name: 'label',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/user_import.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_import_h.png',
          path: '/admin/home/main/goodsManage/goodsImport',
          span: '',
          name: 'goods_import',
          flag: false
        }
      ],
      first_trade_manage: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/all_order.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/all_order_h.png',
          path: '/admin/home/main/orders/all',
          span: '',
          name: 'order',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/wait_order.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/wait_order_h.png',
          path: '/admin/home/main/orders/waiting',
          span: '',
          name: 'order_wait',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/return_order.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/return_order_h.png',
          path: '/admin/home/main/orders/refund/list',
          span: '',
          name: 'order_return',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/self_order.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/self_order_h.png',
          path: '/admin/home/main/orders/self',
          span: '',
          name: 'shop_setting',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/pin_group_fail.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/pin_group_fail_h.png',
          path: '/admin/home/main/orders/pinGroup/fail',
          span: '',
          name: 'pin_group_fail',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/checkout.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/checkout_h.png',
          path: '/admin/home/main/orders/check/list',
          span: '',
          name: 'check_order',
          flag: false
        },
        // {
        //   imgUrl: this.$imageHost + '/image/admin/icon_left/fake_icon.png',
        //   imgUrl_h: this.$imageHost + '/image/admin/icon_left/fake_icon_h.png',
        //   span: '',
        //   name: 'member_card_order',
        //   flag: false
        // },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/bullship.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/bullship_h.png',
          path: '/admin/home/main/orders/bulkShipment/list',
          span: '',
          name: 'bulk_shipment',
          flag: true
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/advisory_order.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/advisory_order_h.png',
          path: '/admin/home/main/orders/advisoryOrder/list',
          span: '',
          name: 'advisory_order',
          flag: true
        }
      ],
      first_market_manage: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_market/tj.png',
          imgUrl_h: this.$imageHost + '/image/admin/new_market/tj.png',
          path: '/admin/home/main/first_market_manage',
          onlyPic: true,
          span: '',
          name: 'first_market_manage',
          flag: true
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/bargain.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/bargain_h.png',
          path: '/admin/home/main/bargain',
          span: '',
          name: 'kanjia',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/icon_group.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_group_h.png',
          path: '/admin/home/main/spellGroup',
          span: '',
          name: 'pin_group',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/img_distribution.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/img_distribution_h.png',
          path: '/admin/home/main/distribution',
          span: '',
          name: 'distribution_info',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/groupdraw.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/groupdraw_h.png',
          path: '/admin/home/main/lotteryDraw',
          span: '',
          name: 'group_draw',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/pinintegration.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/pinintegration_h.png',
          path: '/admin/home/main/integration/list',
          span: '',
          name: 'pin_integration',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/friend_promote.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/friend_promote_h.png',
          path: '/admin/home/main/friendHelp',
          span: '',
          name: 'promote',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/icon_lottery.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_lottery_h.png',
          path: '/admin/home/main/luckyDraw',
          span: '',
          name: 'lottery_activity',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/icon_gifted.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_gifted_h.png',
          path: '/admin/home/main/openScreen/list',
          span: '',
          name: 'market_gifted',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/icon_payreward.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/icon_payreward_h.png',
          path: '/admin/home/main/payReward',
          span: '',
          name: 'payreward',
          flag: false
        }
      ],
      user_manger: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/card.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/card_h.png',
          path: '/admin/home/main/membershipList',
          span: '',
          name: 'user_list',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/user_import.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_import_h.png',
          path: '/admin/home/main/membershipIntroduction',
          span: '',
          name: 'user_import',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/user_code.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/user_code_h.png',
          path: '/admin/home/main/user_card',
          span: '',
          name: 'user_card',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/label_man.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/label_man_h.png',
          path: '/admin/home/main/labelManagement',
          span: '',
          name: 'user_tag',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/essay_admin.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/essay_admin_h.png',
          path: '/admin/home/main/integralManagement',
          span: '',
          name: 'score',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/member_value_add.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/member_value_add_h.png',
          path: '/admin/home/main/memberValueAdded',
          span: '',
          name: 'member_value_added',
          flag: true
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/member_value_add.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/member_value_add_h.png',
          path: '/admin/home/main/patientList',
          span: '',
          name: 'patient_list',
          flag: true
        }
      ],
      store_manage: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/store_list.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_list_h.png',
          path: '/admin/home/main/store/list',
          span: '',
          name: 'store_list',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/store_add.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_add_h.png',
          path: '/admin/home/main/store/addStore',
          span: '',
          name: 'add_store',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/store_group.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_group_h.png',
          path: '/admin/home/main/store/group/list',
          span: '',
          name: 'group_manage',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/service_config.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/service_config_h.png',
          path: '/admin/home/main/store/serviceConfig',
          span: '',
          name: 'store_service_config',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/store_authority.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_authority_h.png',
          path: '/admin/home/main/store/storePermission/list',
          span: '',
          name: 'store_permission',
          flag: true
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/store_announce.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/store_announce_h.png',
          path: '/admin/home/main/store/storeAnnouncement',
          span: '',
          name: 'store_announcement',
          flag: true
        }
      ],
      base_manger: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/config_list.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/config_list_h.png',
          path: '/admin/home/main/base_manger/-1',
          span: '',
          name: 'config_list',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/pay_config.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/pay_config_h.png',
          path: '/admin/home/main/tradeConfigure',
          span: '',
          name: 'pay',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/child_config.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/child_config_h.png',
          path: '/admin/home/main/shopAuthority',
          span: '',
          name: 'child_config',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/message_config.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/message_config_h.png',
          path: '/admin/home/main/basicConfig/templateMessage',
          span: '',
          name: 'message_config',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/third_party_config.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/third_party_config_h.png',
          path: '/admin/home/main/thirdConfig',
          span: '',
          name: 'third_auth',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/action_record.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/action_record_h.png',
          path: '/admin/home/main/actionRecord',
          span: '',
          name: 'action_record',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/pledge_config.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/pledge_config_h.png',
          path: '/admin/home/main/basicConfig/servicePledge',
          span: '',
          name: 'pledge',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/smsConfig.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/smsConfig_h.png',
          path: '/admin/home/main/basicConfig/smsConfig',
          span: '',
          name: 'smsConfig',
          flag: true
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/smsConfig.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/smsConfig_h.png',
          path: '/admin/home/main/basicConfig/rebateConfig',
          span: '',
          name: 'rebateConfig',
          flag: true
        }
      ],
      doctor_manger: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/doctor_list.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/doctor_list_h.png',
          path: '/admin/home/main/doctor/list',
          span: '',
          name: 'doctorList',
          flag: true
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/doctor_professional_title.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/doctor_professional_title_h.png',
          path: '/admin/home/main/doctor/professionalTitle',
          span: '',
          name: 'doctorProfessionalTitle',
          flag: true
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/offices_list.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/offices_list_h.png',
          path: '/admin/home/main/doctor/offices/list',
          span: '',
          name: 'officesList',
          flag: true
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/comment_man.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/comment_man_h.png',
          path: '/admin/home/main/doctor/comment/list',
          span: '',
          name: 'commentList',
          flag: true
        }
      ],
      prescription_manger: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/prescription_list.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/prescription_list_h.png',
          path: '/admin/home/main/prescription/list',
          span: '',
          name: 'prescriptionList',
          flag: true
        }
      ],
      menuParam: null,
      activeIndex: null,
      isRouterAlive: false
    }
  },
  watch: {
    $route: {
      handler: function (router) {
        let { meta: { meta = null }, path } = router
        this.initLeftNav(meta)
        this.activeIndex = path
      },
      immediate: true
    }
  },
  created () {
    this.filterNavShow()
  },
  mounted () {
  },
  methods: {
    async initLeftNav (meta) {
      if (!this.menuParam) await this.filterNavShow()
      if (!this.hasOwnProperty(meta) || this.defaultList.meta === meta) return
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
      this.defaultList = {
        meta: meta,
        list: this[meta]
      }
    },
    filterNavShow () {
      return new Promise((resolve, reject) => {
        jurisdictionQueryRequest().then(({ content: { menuParam } }) => {
          this.menuParam = menuParam
          Object.keys(menuParam).forEach(keyItem => {
            if (!this.hasOwnProperty(keyItem)) return
            this[keyItem] = this[keyItem].reduce((defaultData, item) => {
              if (item.children && item.children.length) {
                item.children = item.children.reduce((childrenDefault, childrenItem) => {
                  if (menuParam[keyItem].includes(childrenItem.name)) childrenDefault.push(childrenItem)
                  return childrenDefault
                }, [])
              }
              if (menuParam[keyItem].includes(item.name)) defaultData.push(item)
              return defaultData
            }, [])
          })
          resolve()
        })
      })
    }
  },
  computed: {
    // activeIndex () {
    //   if (!this.$route.meta.category) return this.$route.path
    //   let topNavList = ['first_web_manage', 'first_web_decoration', 'goods_manage', 'first_trade_manage', 'first_market_manage', 'user_manger', 'store_manage', 'base_manger', 'doctor_manger', 'prescription_manger']
    //   let activePath = null
    //   try {
    //     topNavList.forEach(item => {
    //       let newList = this[item].reduce((defaultList, listItem) => {
    //         let routerItem = []
    //         if (listItem.children && listItem.children.length) {
    //           routerItem = JSON.parse(JSON.stringify(listItem.children))
    //           delete listItem.children
    //         }
    //         defaultList.push(listItem)
    //         if (routerItem.length) {
    //           defaultList = [...defaultList, ...routerItem]
    //         }
    //         return defaultList
    //       }, [])
    //       let target = newList.find(item => item.name === this.$route.meta.category)
    //       if (target) {
    //         activePath = target.path
    //         throw Error()
    //       }
    //     })
    //   } catch (error) {
    //   }
    //   return activePath
    //   return this.$route.path
    // }
  },
  filters: {
    getNavName (name) {
      return vm.$t(`adminPageFramework.leftNavName.${name}`)
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  height: 100%;
  width: 150px;
  z-index: 10;
  /deep/ .el-menu-vertical-demo {
    height: 100%;
    .el-menu-item {
      &:hover {
        background-color: #181e2e !important;
      }
    }
    .el-submenu {
      .el-menu-item {
        width: 100%;
        min-width: 0;
        background-color: #242a3a !important;
        padding-left: 44px !important;
        &:hover {
          background-color: #181e2e !important;
        }
      }
    }
    .el-submenu__title {
      &:hover {
        background-color: #181e2e !important;
      }
    }
    .el-menu-item {
      &.is-active {
        background-color: #181e2e !important;
      }
    }
  }
}
</style>
