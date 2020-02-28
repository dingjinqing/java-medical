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
            v-if="storeList.wxDataShop"
          >
            <div v-if="storeList.wxDataShop.registerApplet.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.registerApplet.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.registerApplet.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.registerApplet.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.registerApplet.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTipSet1') }}</span>
              <a href="/admin/home/main/base_manger/-1">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.appletService.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.appletService.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.appletService.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.appletService.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.appletService.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTipSet2') }}</span>
              <a href="/admin/home/main/base_manger/-1">{{ $t('overview.storeGo') }}</a>

            </div>
            <div v-if="storeList.wxDataShop.authApplet.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.authApplet.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.authApplet.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.authApplet.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.authApplet.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTipSet3') }}</span>
              <a href="/admin/home/main/base_manger/-1">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.wxPayment.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.wxPayment.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.wxPayment.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.wxPayment.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.wxPayment.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTipSet4') }}</span>
              <a href="/admin/home/main/base_manger/-1">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.configWxPayment.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.configWxPayment.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.configWxPayment.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.configWxPayment.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.configWxPayment.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTipSet5') }}</span>
              <a href="/admin/home/main/base_manger/-1">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.childAccountConf.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.childAccountConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.childAccountConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.childAccountConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.childAccountConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip2') }}</span>
              <a href="/admin/home/main/shopAuthority">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.officialAccountConf.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.officialAccountConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.officialAccountConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.officialAccountConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.officialAccountConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip3') }}</span>
              <a href="/admin/home/shopMain?change_components=3">{{ $t('overview.storeGo') }}</a>
            </div>

          </div>
          <div
            class="type_content"
            v-if="storeList.dataShop"
          >
            <div v-if="storeList.dataShop.homePageConf.status === 0">
              <span
                class="tips"
                v-if="storeList.dataShop.homePageConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataShop.homePageConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataShop.homePageConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataShop.homePageConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip4') }}</span>
              <a href="/admin/home/main/pictureSetting">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataShop.shopRecommendConf.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.shopRecommendConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.shopRecommendConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.shopRecommendConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.shopRecommendConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip5') }}</span>
              <a :href="'/admin/home/main/base_manger/' + this.storeList.dataShop.shopRecommendLink">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataShop.customServiceConf.status === 0">
              <span
                class="tips"
                v-if="storeList.wxDataShop.customServiceConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.wxDataShop.customServiceConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.wxDataShop.customServiceConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.wxDataShop.customServiceConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.unShopTip6') }}</span>
              <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
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
            <div v-if="storeList.dataGoods.shipTemplateConf.status === 0">
              <span
                class="tips"
                v-if="storeList.dataGoods.shipTemplateConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataGoods.shipTemplateConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataGoods.shipTemplateConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataGoods.shipTemplateConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.unStoreTip1') }}</span>
              <a href="/admin/home/main/goodsManage/deliverTemplate/list">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsConf.status === 0">
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataGoods.goodsConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataGoods.goodsConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.unStoreTip2') }}</span>
              <a href="/admin/home/main/goodsManage/goodsForSale">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsStoreConf.status === 0">
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsStoreConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsStoreConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataGoods.goodsStoreConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataGoods.goodsStoreConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ storeList.dataGoods.goodsStoreConf.value }} {{ $t('overview.unStoreTip3') }}</span>
              <a
                href="javascript: void(0);"
                @click="clickJumpHandler('goodsForSale', ShopData.storeSizeNum, 1)"
              >{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsUnsalableConf.status === 0">
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsUnsalableConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsUnsalableConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataGoods.goodsUnsalableConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataGoods.goodsUnsalableConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ storeList.dataGoods.goodsUnsalableConf.value }} {{ $t('overview.unStoreTip4') }}</span>
              <a
                href="javascript: void(0);"
                @click="clickJumpHandler('goodsForSale', 3, 1)"
              >{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsComment.status === 0">
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsComment.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsComment.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataGoods.goodsComment.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataGoods.goodsComment.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ storeList.dataGoods.goodsComment.value }} {{ $t('overview.unStoreTip5') }}</span>
              <a
                href="javascript: void(0);"
                @click="clickJumpHandler('comment', ShopData.commentOver, 1)"
              >{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.goodsRecommend.status === 0">
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsRecommend.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataGoods.goodsRecommend.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataGoods.goodsRecommend.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataGoods.goodsRecommend.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.unStoreTip6') }}</span>
              <a href="/admin/home/main/goodsManage/goodsRecommend">{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataGoods.shopSort.status === 0">
              <span
                class="tips"
                v-if="storeList.dataGoods.shopSort.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataGoods.shopSort.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataGoods.shopSort.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataGoods.shopSort.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.unStoreTip7') }}</span>
              <a href="/admin/home/main/goodsManage/goodsSortManagement/allGoodsSort">{{ $t('overview.storeGo') }}</a>
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
            <div v-if="storeList.dataOrder.deliver.status === 0">
              <span
                class="tips"
                v-if="storeList.dataOrder.deliver.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataOrder.deliver.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataOrder.deliver.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataOrder.deliver.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ storeList.dataOrder.deliver.value }} {{ $t('overview.unOrderTip1') }}</span>
              <a
                href="javascript: void(0);"
                @click="clickJumpHandler('order_wait', ShopData.deliverOver, 0, 'deliver')"
              >{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataOrder.refund.status === 0">
              <span
                class="tips"
                v-if="storeList.dataOrder.refund.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataOrder.refund.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataOrder.refund.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataOrder.refund.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ storeList.dataOrder.refund.value }} {{ $t('overview.unOrderTip2') }}</span>
              <a
                href="javascript: void(0);"
                @click="clickJumpHandler('order_wait', ShopData.refundOver, 2, 'refund')"
              >{{ $t('overview.storeGo') }}</a>
            </div>
            <div v-if="storeList.dataOrder.remind.status === 0">
              <span
                class="tips"
                v-if="storeList.dataOrder.remind.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataOrder.remind.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataOrder.remind.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataOrder.remind.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ storeList.dataOrder.remind.value }} {{ $t('overview.unOrderTip3') }}</span>
              <a
                href="javascript: void(0);"
                @click="clickJumpHandler('order_wait', ShopData.remindOver, 1, 'deliver')"
              >{{ $t('overview.storeGo') }}</a>
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
            <div v-if="storeList.dataMarket.examine.status === 0">
              <span
                class="tips"
                v-if="storeList.dataMarket.examine.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips"
                v-if="storeList.dataMarket.examine.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips ff4444"
                v-if="storeList.dataMarket.examine.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips fdb64a"
                v-if="storeList.dataMarket.examine.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ storeList.dataMarket.examine.value }} {{ $t('overview.unMarketTip1') }}</span>
              <a
                href="javascript:void(0);"
                @click="clickJumpHandler('distribution_info', ShopData.applyOver, 1)"
              >{{ $t('overview.storeGo') }}</a>
            </div>
            <div
              v-if="storeList.dataMarket.member.status === 0"
              style="border-bottom: none;"
            >
              <div class="task_list_item">
                <span
                  class="tips"
                  v-if="storeList.dataMarket.member.type === 0"
                >{{ $t('overview.storeConfig') }}</span>
                <span
                  class="tips"
                  v-if="storeList.dataMarket.member.type === 1"
                >{{ $t('overview.storeTask') }}</span>
                <span
                  class="tips ff4444"
                  v-if="storeList.dataMarket.member.type === 2"
                >{{ $t('overview.storeTip') }}</span>
                <span
                  class="tips fdb64a"
                  v-if="storeList.dataMarket.member.type === 3"
                >{{ $t('overview.storeRecommend') }}</span>
                <span class="task_list_desc">{{ storeList.dataMarket.member.content.card_name }} {{ $t('overview.unMarketTip5') }} {{ storeList.dataMarket.member.value }} {{ storeList.dataMarket.member.content.card_num }} {{ $t('overview.unMarketTip2') }}</span>
                <a :href="'/admin/home/main/activateAudit?cardId=' + storeList.dataMarket.member.content.card_id">{{ $t('overview.storeGo') }}</a>
              </div>
            </div>
            <div
              v-if="storeList.dataMarket.voucher.status === 0"
              style="border-bottom: none;"
            >
              <div
                v-for="(val, key, index) in storeList.dataMarket.voucher.content"
                :key="index"
              >
                <span
                  class="tips"
                  v-if="storeList.dataMarket.voucher.type === 0"
                >{{ $t('overview.storeConfig') }}</span>
                <span
                  class="tips"
                  v-if="storeList.dataMarket.voucher.type === 1"
                >{{ $t('overview.storeTask') }}</span>
                <span
                  class="tips ff4444"
                  v-if="storeList.dataMarket.voucher.type === 2"
                >{{ $t('overview.storeTip') }}</span>
                <span
                  class="tips fdb64a"
                  v-if="storeList.dataMarket.voucher.type === 3"
                >{{ $t('overview.storeRecommend') }}</span>
                <span class=" task_list_desc">{{ $t('overview.unMarketTip3') }} "{{ val }}" {{ $t('overview.unMarketTip4') }}</span>
                <a href="/admin/home/main/ordinaryCoupon">{{ $t('overview.storeGo') }}</a>
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
            v-if="storeList.wxDataShop"
          >
            <div v-if="storeList.wxDataShop.registerApplet.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.registerApplet.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.registerApplet.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.wxDataShop.registerApplet.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.wxDataShop.registerApplet.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTipSet1') }}</span>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2189&fromuid=1"
                target="_blank"
              >{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.appletService.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.appletService.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.appletService.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.wxDataShop.appletService.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.wxDataShop.appletService.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTipSet2') }}</span>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=685&extra=page%3D1%26filter%3Dsortid%26sortid%3D17"
                target="_blank"
              >{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.authApplet.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.authApplet.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.authApplet.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.wxDataShop.authApplet.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.wxDataShop.authApplet.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTipSet3') }}</span>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=19&extra=page%3D1%26filter%3Dsortid%26sortid%3D27"
                target="_blank"
              >{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.wxPayment.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.wxPayment.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.wxPayment.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.wxDataShop.wxPayment.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.wxDataShop.wxPayment.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTipSet4') }}</span>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2123&fromuid=1"
                target="_blank"
              >{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.configWxPayment.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.configWxPayment.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.configWxPayment.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.wxDataShop.configWxPayment.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.wxDataShop.configWxPayment.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTipSet5') }}</span>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=71&fromuid=1"
                target="_blank"
              >{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.childAccountConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.childAccountConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.childAccountConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.wxDataShop.childAccountConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.wxDataShop.childAccountConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip2') }}</span>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=1&fromuid=1"
                target="_blank"
              >{{ $t('overview.storeCourse') }}</a>
            </div>
            <div v-if="storeList.wxDataShop.officialAccountConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.officialAccountConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.wxDataShop.officialAccountConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.wxDataShop.officialAccountConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.wxDataShop.officialAccountConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip3') }}</span>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=733&fromuid=1"
                target="_blank"
              >{{ $t('overview.storeCourse') }}</a>
            </div>
          </div>

          <div
            class="type_content"
            v-if="storeList.dataShop"
          >
            <div v-if="storeList.dataShop.homePageConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataShop.homePageConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataShop.homePageConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataShop.homePageConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataShop.homePageConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip4') }}</span>
              <!-- <a href="javascript:void(0);" target="_blank">{{ $t('overview.storeCourse') }}</a> -->
            </div>
            <div v-if="storeList.dataShop.shopRecommendConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataShop.shopRecommendConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataShop.shopRecommendConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataShop.shopRecommendConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataShop.shopRecommendConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip5') }}</span>
              <a
                href="/admin/home/main/wechateGoodsCicle"
                target="_blank"
              >{{ $t('overview.storeIntroduction') }}</a>
            </div>
            <div v-if="storeList.dataShop.customServiceConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataShop.customServiceConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataShop.customServiceConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataShop.customServiceConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataShop.customServiceConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enShopTip6') }}</span>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=685&fromuid=1"
                target="_blank"
              >{{ $t('overview.storeIntroduction') }}</a>
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
            <div v-if="storeList.dataGoods.shipTemplateConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.shipTemplateConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.shipTemplateConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataGoods.shipTemplateConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataGoods.shipTemplateConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip1') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div v-if="storeList.dataGoods.goodsConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataGoods.goodsConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataGoods.goodsConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip2') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div v-if="storeList.dataGoods.goodsStoreConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsStoreConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsStoreConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataGoods.goodsStoreConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataGoods.goodsStoreConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip3') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div v-if="storeList.dataGoods.goodsUnsalableConf.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsUnsalableConf.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsUnsalableConf.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataGoods.goodsUnsalableConf.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataGoods.goodsUnsalableConf.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip4') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div v-if="storeList.dataGoods.goodsComment.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsComment.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsComment.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataGoods.goodsComment.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataGoods.goodsComment.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip5') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div v-if="storeList.dataGoods.goodsRecommend.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsRecommend.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.goodsRecommend.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataGoods.goodsRecommend.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataGoods.goodsRecommend.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip6') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div v-if="storeList.dataGoods.shopSort.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.shopSort.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataGoods.shopSort.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataGoods.shopSort.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataGoods.shopSort.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enStoreTip7') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
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
            <div v-if="storeList.dataOrder.deliver.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataOrder.deliver.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataOrder.deliver.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataOrder.deliver.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataOrder.deliver.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enOrderTip1') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div v-if="storeList.dataOrder.refund.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataOrder.refund.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataOrder.refund.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataOrder.refund.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataOrder.refund.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enOrderTip2') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div v-if="storeList.dataOrder.remind.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataOrder.remind.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataOrder.remind.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataOrder.remind.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataOrder.remind.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class=" task_list_desc">{{ $t('overview.enOrderTip3') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
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
            <div v-if="storeList.dataMarket.examine.status === 1">
              <span
                class="tips_finish"
                v-if="storeList.dataMarket.examine.type === 0"
              >{{ $t('overview.storeConfig') }}</span>
              <span
                class="tips_finish"
                v-if="storeList.dataMarket.examine.type === 1"
              >{{ $t('overview.storeTask') }}</span>
              <span
                class="tips_finish ff4444"
                v-if="storeList.dataMarket.examine.type === 2"
              >{{ $t('overview.storeTip') }}</span>
              <span
                class="tips_finish fdb64a"
                v-if="storeList.dataMarket.examine.type === 3"
              >{{ $t('overview.storeRecommend') }}</span>
              <span class="task_list_desc">{{ $t('overview.enMarketTip4') }}</span>
              <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
            </div>
            <div
              v-if="storeList.dataMarket.member.status === 1"
              style="border-bottom: none;"
            >
              <div>
                <span
                  class="tips_finish"
                  v-if="storeList.dataMarket.member.type === 0"
                >{{ $t('overview.storeConfig') }}</span>
                <span
                  class="tips_finish"
                  v-if="storeList.dataMarket.member.type === 1"
                >{{ $t('overview.storeTask') }}</span>
                <span
                  class="tips_finish ff4444"
                  v-if="storeList.dataMarket.member.type === 2"
                >{{ $t('overview.storeTip') }}</span>
                <span
                  class="tips_finish fdb64a"
                  v-if="storeList.dataMarket.member.type === 3"
                >{{ $t('overview.storeRecommend') }}</span>
                <span class="task_list_desc">{{ $t('overview.enMarketTip2') }}</span>
                <!-- <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a> -->
              </div>
            </div>
            <!-- 已完成优惠券不显示 -->
            <!-- <div
              v-if="storeList.dataMarket.voucher.status === 1"
              style="border-bottom: none;"
            >
              <div>
                <span
                  class="tips_finish"
                  v-if="storeList.dataMarket.voucher.type === 0"
                >{{ $t('overview.storeConfig') }}</span>
                <span
                  class="tips_finish"
                  v-if="storeList.dataMarket.voucher.type === 1"
                >{{ $t('overview.storeTask') }}</span>
                <span
                  class="tips_finish ff4444"
                  v-if="storeList.dataMarket.voucher.type === 2"
                >{{ $t('overview.storeTip') }}</span>
                <span
                  class="tips_finish fdb64a"
                  v-if="storeList.dataMarket.voucher.type === 3"
                >{{ $t('overview.storeRecommend') }}</span>
                <span class="task_list_desc">{{ $t('overview.enMarketTip3') }}</span>
              </div>
            </div> -->
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
      storeList: [], // 数据

      // 店铺助手默认值
      ShopData: {
        isAuthOk: 1,
        storeSizeNum: 5, // 商品库存偏小参数
        // unsalableNum: 3, // 商品滞销
        commentOver: 3, // 商品评价审核逾期

        deliverOver: 3, // 订单发货逾期参数
        refundOver: 3, // 退款申请处理预期参数
        remindOver: 3, // 提醒发货参数

        applyOver: 3, // 分销员审核超时参数,
        examineOver: 2, // 会员卡激活审核参数

        couponSizeNum: 10 // 优惠券库存参数
      }
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
    },

    // type用于区别订单的tab名
    clickJumpHandler (name, IntegerDays, flag, type) {
      var obj = {
        IntegerDays: IntegerDays,
        flag: flag
      }
      // 商品评价
      if (name === 'comment') {
        obj.activeName = 'second'
      }
      // 订单发货
      if (name === 'order_wait') {
        if (type === 'deliver') {
          obj.orderStatus = 3
        } else if (type === 'refund') {
          obj.orderStatus = 7
        }
      }
      // 分销员审核
      if (name === 'distribution_info') {
        obj.distributorName = 'ninth'
      }
      this.$router.push({
        name: name,
        params: obj
      })
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
  border: 1px solid #5a8bff;
  color: #5a8bff;
  border-radius: 10px;
  letter-spacing: 1px;
  padding: 0 4px 0 5px;
  line-height: 16px;
}

.type_content > div .tips_finish {
  font-size: 12px;
  border: 1px solid #5a8bff;
  color: #5a8bff;
  border-radius: 10px;
  letter-spacing: 1px;
  padding: 0 4px 0 5px;
  line-height: 16px;
}

.type_content > div .tips_recommend {
  font-size: 12px;
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

.fdb64a {
  color: #fdb64a !important;
  border: 1px solid #fdb64a !important;
}

.ff4444 {
  color: #ff4444 !important;
  border: 1px solid #ff4444 !important;
}
</style>
