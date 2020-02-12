<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="tabClickHandler"
        :lazy="true"
      >
        <el-tab-pane
          v-for="(item, index) in tabInfo"
          :key="index"
          :label="item.title"
          :name="item.name"
        >
        </el-tab-pane>
      </el-tabs>

      <div v-if="tabSwitch === '1'">
        <div>
          <p class="type_title">
            <img
              :src="image + '/image/admin/task_shop.png'"
              alt=""
            >{{ $t('overview.shop') }}
          </p>
          <div
            class="type_content"
            v-if="storeList.dataShop"
          >
            <div v-if="storeList.dataShop.wxPayConfigInfo === 2">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip1') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.childAccountConf !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip2') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.officialAccountConf !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip3') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.homePageConf !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip4') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.shopRecommendConf !== 1">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip5') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.customServiceConf !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip6') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
          </div>
        </div>
        <div>
          <p class="type_title">
            <img
              :src="image + '/image/admin/task_goods.png'"
              alt=""
            >{{ $t('overview.commodity') }}
          </p>
          <div
            class="type_content"
            v-if="storeList.dataGoods"
          >
            <div v-if="storeList.dataGoods.shipTemplateConf !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ $t('overview.unShopTip1') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsConf !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ $t('overview.unShopTip2') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsStoreConf !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ storeList.dataGoods.goodsStoreConf }} {{ $t('overview.unShopTip3') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsUnsalableConf !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ storeList.dataGoods.goodsUnsalableConf }} {{ $t('overview.unShopTip4') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsComment !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ storeList.dataGoods.goodsComment }} {{ $t('overview.unShopTip5') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsRecommend === 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ $t('overview.storeRecommend') }} {{ $t('overview.unShopTip6') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.shopSort === 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ $t('overview.unShopTip7') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
          </div>
        </div>
        <div>
          <p class="type_title">
            <img
              :src="image + '/image/admin/task_order.png'"
              alt=""
            >{{ $t('overview.order') }}
          </p>
          <div
            class="type_content"
            v-if="storeList.dataOrder"
          >
            <div v-if="storeList.dataOrder.deliver !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ storeList.dataOrder.deliver }} {{ $t('overview.unOrderTip1') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataOrder.refund !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ storeList.dataOrder.refund }} {{ $t('overview.unOrderTip2') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
          </div>
        </div>
        <div>
          <p class="type_title">
            <img
              :src="image + '/image/admin/task_market.png'"
              alt=""
            >{{ $t('overview.market') }}
          </p>
          <div
            class="type_content"
            v-if="storeList.dataMarket"
          >
            <div v-if="storeList.dataMarket.examine !== 0">
              <span class="tips">{{ $t('overview.storeTip') }}</span>
              <span class=" task_list_desc">{{ storeList.dataMarket.examine }} {{ $t('overview.unMarketTip1') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div
              v-if="storeList.dataMarket.member !== null"
              style="border-bottom: none;"
            >
              <div class="task_list_item">
                <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                <span class="task_list_desc">{{ storeList.dataMarket.member.card_name }} {{ $t('overview.unMarketTip5') }} {{ storeList.dataMarket.member.card_num }} {{ $t('overview.unMarketTip6') }}</span>
                <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
              </div>
            </div>
            <div
              v-if="storeList.dataMarket.voucher !== null"
              style="border-bottom: none;"
            >
              <div
                v-for="(val, key, index) in storeList.dataMarket.voucher"
                :key="index"
              >
                <span class="tips">{{ $t('overview.storeTip') }}</span>
                <span class=" task_list_desc">{{ $t('overview.unMarketTip3') }} "{{ val }}" {{ $t('overview.unMarketTip4') }}</span>
                <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="tabSwitch === '2'">
        <div>
          <p class="type_title">
            <img
              :src="image + '/image/admin/task_shop.png'"
              alt=""
            >{{ $t('overview.shop') }}
          </p>
          <div
            class="type_content"
            v-if="storeList.dataShop"
          >
            <div v-if="storeList.dataShop.wxPayConfigInfo !== 2">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip1') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.childAccountConf === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip2') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.officialAccountConf === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip3') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.homePageConf === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip4') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.shopRecommendConf === 1">
              <span class="tips_recommend">{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip5') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.dataShop.customServiceConf === 0">
              <span class="tips_recommend">{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip6') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeCourse') }}</a>
            </div>
          </div>
        </div>
        <div>
          <p class="type_title">
            <img
              :src="image + '/image/admin/task_goods.png'"
              alt=""
            >{{ $t('overview.commodity') }}
          </p>
          <div
            class="type_content"
            v-if="storeList.dataGoods"
          >
            <div v-if="storeList.dataGoods.shipTemplateConf === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip1') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsConf === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip2') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsStoreConf === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip3') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsUnsalableConf === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip4') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsComment === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip5') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsRecommend !== 0">
              <span class="tips_recommend">{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.storeRecommend') }} {{ $t('overview.enStoreTip6') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.shopSort !== 0">
              <span class="tips_recommend">{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip7') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
          </div>
        </div>
        <div>
          <p class="type_title">
            <img
              :src="image + '/image/admin/task_order.png'"
              alt=""
            >{{ $t('overview.order') }}
          </p>
          <div
            class="type_content"
            v-if="storeList.dataOrder"
          >
            <div v-if="storeList.dataOrder.deliver === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enOrderTip1') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataOrder.refund === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enOrderTip2') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
          </div>
        </div>
        <div>
          <p class="type_title">
            <img
              :src="image + '/image/admin/task_market.png'"
              alt=""
            >{{ $t('overview.market') }}
          </p>
          <div
            class="type_content"
            v-if="storeList.dataMarket"
          >
            <div v-if="storeList.dataMarket.examine === 0">
              <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
              <span class="task_list_desc">{{ storeList.dataMarket.examine }} {{ $t('overview.enMarketTip1') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
            </div>
            <div
              v-if="storeList.dataMarket.member === null"
              style="border-bottom: none;"
            >
              <div>
                <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
                <span class="task_list_desc">{{ $t('overview.enMarketTip2') }}</span>
                <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
              </div>
            </div>
            <div
              v-if="storeList.dataMarket.voucher === null"
              style="border-bottom: none;"
            >
              <div>
                <span class="tips_finish">{{ $t('overview.storeTask') }}</span>
                <span class="task_list_desc">{{ $t('overview.enMarketTip3') }}</span>
                <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>
<script>
// 引入组件
import { shopAssistantRequest } from '@/api/admin/survey.js'
export default {
  components: {
  },
  data () {
    return {
      image: `${this.$imageHost}`,
      tabSwitch: '1',
      tabInfo: this.$t('overview.tabInfo'),
      storeList: [] // 数据
    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('overview.tabInfo')
    }
  },
  mounted () {
    // 初始化数据
    this.handleClick()
    this.langDefault()
  },
  methods: {
    // 店铺助手列表
    handleClick () {
      let obj = {
        isAuthOk: 1,
        storeSizeNum: 5,
        commentOver: 3,
        deliverOver: 3,
        refundOver: 3,
        applyOver: 3,
        examineOver: 2,
        couponSizeNum: 10
      }
      shopAssistantRequest(obj).then((res) => {
        if (res.error === 0) {
          this.storeList = res.content
        }
      })
    },

    // tab栏切换
    tabClickHandler () {
      this.handleClick()
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    .wrapper {
      .el-button {
        margin-left: 5px;
      }
    }
  }
}
.type_title {
  font-weight: 600;
  line-height: 40px;
}
.type_title img {
  vertical-align: middle;
  margin-right: 6px;
}
.type_content {
  padding-left: 20px;
}
.type_content > div {
  // display: flex;
  align-items: center;
  line-height: 42px;
  border-bottom: 1px solid #e5e5e5;
}

.type_content > div > div {
  border-bottom: 1px solid #e5e5e5;
}

.type_content > div .task_list_desc {
  flex: 1;
  margin-left: 4px;
}
.type_content > div .tips {
  font-size: 12px;
  border: 1px solid #ff4444;
  color: #ff4444;
  // border: 1px solid #5a8bff;
  // color: #5a8bff;
  border-radius: 10px;
  letter-spacing: 1px;
  padding: 0 4px 0 5px;
  line-height: 16px;
}

.type_content > div .tips_finish {
  font-size: 12px;
  // border: 1px solid #ff4444;
  // color: #ff4444;
  border: 1px solid #5a8bff;
  color: #5a8bff;
  border-radius: 10px;
  letter-spacing: 1px;
  padding: 0 4px 0 5px;
  line-height: 16px;
}

.type_content > div .tips_recommend {
  font-size: 12px;
  // border: 1px solid #ff4444;
  // color: #ff4444;
  border: 1px solid #fdb64a;
  color: #fdb64a;
  border-radius: 10px;
  letter-spacing: 1px;
  padding: 0 4px 0 5px;
  line-height: 16px;
}

.type_content > div a {
  float: right;
  margin-left: 15px;
  color: #5a8bff;
  text-decoration: none;
}

// .type_content li {
//   display: flex;
//   align-items: center;
//   line-height: 42px;
//   border-bottom: 1px solid #e5e5e5;
// }
// .type_content li .task_list_desc {
//   flex: 1;
//   margin-left: 4px;
// }
// .type_content li .tips {
//   font-size: 12px;
//   border: 1px solid #5a8bff;
//   color: #5a8bff;
//   border-radius: 10px;
//   letter-spacing: 1px;
//   padding: 0 4px 0 5px;
//   line-height: 16px;
// }
// .type_content li a {
//   margin-left: 15px;
//   color: #5a8bff;
//   text-decoration: none;
// }
</style>
