<template>
  <div class="decRightContainer">
    <div class="decRightTop">
      <div>{{$t('pageSetUp.pageSetTitle')}}</div>
      <div
        @click="handleToChangeIcon"
        class="topRight"
      >
        <span>{{topIconFlag?retract:open}}</span>
        <img :src="topIconFlag?topIconUp:topIconDown">
      </div>
    </div>
    <!--页面设置模块-->
    <div
      v-if="topIconFlag"
      class="pageSetContent"
    >
      <pageSetupMain
        :pageSet='pageSet'
        @hanelToPageSet='hanelToPageSet'
      />
    </div>
    <!--模块配置-->
    <div v-if="!topIconFlag">
      <components
        :is="showModule"
        :modulesData='modulesData'
        :sortIndex='sortIndex'
        @handleToBackData='handleToBackData'
      ></components>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    pageSetupMain: () => import('./pageSetupModules/pageSetupMain'), // 页面配置
    // 营销组件库
    RightMembershipCard: () => import('./pageSetupModules/marketingComponents/rightMembershipCard'), // 右侧会员卡配置页面
    RightCoupon: () => import('./pageSetupModules/marketingComponents/rightCoupon'), // 右侧优惠券配置页面
    RightBargain: () => import('./pageSetupModules/marketingComponents/rightBargain'), // 右侧砍价配置页面
    RightSpike: () => import('./pageSetupModules/marketingComponents/rightSpike'), // 右侧秒杀配置页面
    RightFightGroup: () => import('./pageSetupModules/marketingComponents/rightFightGroup'), // 右侧拼团抽奖配置页面
    RiIntegralExchange: () => import('./pageSetupModules/marketingComponents/riIntegralExchange'), // 右侧积分兑换页面
    // 商品组件库
    RightCommodity: () => import('./pageSetupModules/commodityComponents/rightCommodity'), // 右侧商品配置页面
    RightCommoditySearch: () => import('./pageSetupModules/commodityComponents/rightCommoditySearch'), // 右侧页面商品搜索配置页面
    RightCommodityGrouping: () => import('./pageSetupModules/commodityComponents/rightCommodityGrouping'), // 右侧页面商品分组配置页面
    // 图文组件库
    RightPictureNavigation: () => import('./pageSetupModules/graphicAndTextComponents/rightPictureNavigation'), // 右侧图片导航配置页面
    RightCarouselPicture: () => import('./pageSetupModules/graphicAndTextComponents/rightCarouselPicture'), // 右侧轮播图配置页面
    RiPictureAds: () => import('./pageSetupModules/graphicAndTextComponents/riPictureAds'), // 右侧图片广告模块
    RiMagicMap: () => import('./pageSetupModules/graphicAndTextComponents/riMagicMap'), // 右侧魔方多图模块
    RiShopRecruit: () => import('./pageSetupModules/graphicAndTextComponents/riShopRecruit'), // 右侧店招设置模块
    RiMapModule: () => import('./pageSetupModules/graphicAndTextComponents/riMapModule'), // 右侧地图模块
    RiLeftWingRightPicture: () => import('./pageSetupModules/graphicAndTextComponents/riLeftWingRightPicture'), // 右侧左文右图配置模块\
    RiTextModule: () => import('./pageSetupModules/graphicAndTextComponents/riTextModule'), // 右侧文本模块配置页面
    RiRichText: () => import('./pageSetupModules/graphicAndTextComponents/riRichText'), // 右侧富文本模块
    RiAuxiliaryBlank: () => import('./pageSetupModules/graphicAndTextComponents/riAuxiliaryBlank'), // 右侧辅助空白配置页面
    RiTitleModule: () => import('./pageSetupModules/graphicAndTextComponents/riTitleModule'), // 标题模块
    RiGuide: () => import('./pageSetupModules/graphicAndTextComponents/riGuide'), // 辅助线占位
    RiVideoModule: () => import('./pageSetupModules/graphicAndTextComponents/riVideoModule'), // 视频模块
    RiShopNotices: () => import('./pageSetupModules/graphicAndTextComponents/riShopNotices'), // 店铺公告
    RiOfficialAccount: () => import('./pageSetupModules/graphicAndTextComponents/riOfficialAccount'), // 公众号模块
    RiCustomerServiceModule: () => import('./pageSetupModules/graphicAndTextComponents/riCustomerServiceModule'), // 客服模块
    RiPictureHotSpot: () => import('./pageSetupModules/graphicAndTextComponents/riPictureHotSpot') // 右侧图片热区模块
  },
  props: {
    nowRightShowMoudlesIndex: Number,
    nowRightModulesData: Object,
    nowRightShowIndex: Number,
    pageSetData: Object
  },
  data () {
    return {
      topIconUp: this.$imageHost + '/image/admin/shop_deco/icon_up.png',
      topIconDown: this.$imageHost + '/image/admin/shop_deco/icon_down.png',
      topIconFlag: true,
      modulesShow: false,
      thirdRightModulesList: [
        {
          id: 1,
          name: 'RightMembershipCard'
        },
        {
          id: 2,
          name: 'RightCoupon'
        },
        {
          id: 3,
          name: 'RightBargain'
        },
        {
          id: 4,
          name: 'RiIntegralExchange'
        },
        {
          id: 5,
          name: 'RightSpike'
        },
        {
          id: 6,
          name: 'RightFightGroup'
        },
        {
          id: 8,
          name: 'RightCommodity'
        },
        {
          id: 9,
          name: 'RightCommoditySearch'
        },
        {
          id: 11,
          name: 'RightCarouselPicture'
        },
        {
          id: 12,
          name: 'RightPictureNavigation'
        },
        {
          id: 10,
          name: 'RightCommodityGrouping'
        },
        {
          id: 13,
          name: 'RiPictureAds'
        },
        {
          id: 14,
          name: 'RiMagicMap'
        },
        {
          id: 15,
          name: 'RiPictureHotSpot'
        },
        {
          id: 16,
          name: 'RiLeftWingRightPicture'
        },
        {
          id: 17,
          name: 'RiTextModule'
        },
        {
          id: 18,
          name: 'RiRichText'
        },
        {
          id: 19,
          name: 'RiAuxiliaryBlank'
        },
        {
          id: 20,
          name: 'RiGuide'
        },
        {
          id: 21,
          name: 'RiTitleModule'
        },
        {
          id: 22,
          name: 'RiVideoModule'
        },
        {
          id: 23,
          name: 'RiShopNotices'
        },
        {
          id: 24,
          name: 'RiOfficialAccount'
        },
        // {
        //   id: 25,
        //   name: 'RiCustomerServiceModule'
        // },
        {
          id: 27,
          name: 'RiShopRecruit'
        },
        {
          id: 28,
          name: 'RiMapModule'
        }
      ],
      showModule: null,
      modulesData: {},
      sortIndex: -1,
      pageSet: {},
      retract: '收起', // 收起文本
      open: '展开' // 展开文本
    }
  },
  watch: {
    nowRightShowMoudlesIndex: {
      handler (newData) {
        console.log(newData)
        if (newData) {
          this.topIconFlag = false
          this.thirdRightModulesList.forEach(item => {
            if (item.id === newData) {
              this.showModule = item.name
            }
          })
        } else {
          this.showModule = ''
        }
        console.log(newData)
      },
      immediate: true
    },
    nowRightShowIndex: {
      handler (newData) {
        this.$nextTick(() => {
          console.log(newData, this.nowRightModulesData)
          // this.modulesData = this.nowRightModulesData
          // this.sortIndex = newData
        })
        setTimeout(() => {
          console.log(newData, this.nowRightModulesData)
          this.modulesData = this.nowRightModulesData
          this.sortIndex = newData
        }, 50)
      },
      immediate: true
    },
    pageSetData: {
      handler (newData) {
        console.log(newData)
        if (newData) {
          this.pageSet = newData
        }
      },
      immediate: true,
      deep: true
    },
    lang () {
      this.retract = this.$t('pageSetUp.retract')
      this.open = this.$t('pageSetUp.open')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 点击顶部icon
    handleToChangeIcon () {
      this.$emit('handleToClearIndex')
      this.topIconFlag = !this.topIconFlag
      this.showModule = ''
    },
    // 右侧模块编辑向上层传回数据
    handleToBackData (data) {
      console.log(data)
      this.$emit('handleToBackMiddleData', data)
    },
    // 页面设置回显
    hanelToPageSet (res) {
      console.log(res)
      this.$emit('hanelToPageSet', res)
    }
  }
}
</script>
<style lang="scss" scoped>
.decRightContainer {
  .decRightTop {
    background-color: #f8f8f8;
    border: 1px solid #e5e5e5;
    padding: 10px 2%;
    margin-bottom: 10px;
    border-radius: 4px;
    display: flex;
    justify-content: space-between;
    .topRight {
      color: #5a8bff;
      margin-right: 20px;
      display: flex;
      align-items: center;
      cursor: pointer;
      span {
        display: inline-block;
        margin-right: 5px;
      }
    }
  }
  .pageSetContent {
    height: 500px;
    background-color: #f8f8f8;
    border: 1px solid #e5e5e5;
    padding: 10px 2%;
    border-radius: 4px;
    overflow-y: auto;
    overflow-x: hidden;
    padding-bottom: 20px;
  }
}
</style>
