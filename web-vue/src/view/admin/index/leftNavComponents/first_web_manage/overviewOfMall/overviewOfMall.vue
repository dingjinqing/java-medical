<template>
  <div>

    <div class="title">
      <el-tooltip
        :content="this.shopInfo.businessStateNes"
        placement="bottom"
        effect="light"
      >
        <div class="fl">

          <img
            class="shop_defu"
            :src="this.shopInfo.shopAvatar===null?this.avatarImage:this.shopInfo.shopAvatar"
          >
          <img
            v-if="this.shopInfo.businessState===1&&this.shopInfo.expireTimeStatus==='0'"
            class="shop_type"
            :src="image + '/image/admin/img_home/type_open.png'"
          >
          <img
            v-if="this.shopInfo.businessState===0"
            class="shop_type"
            :src="image + '/image/admin/img_home/type_none.png'"
          >
          <img
            v-if="this.shopInfo.expireTimeStatus==='1'"
            class="shop_type"
            :src="image + '/image/admin/img_home/type_over.png'"
          >
        </div>
      </el-tooltip>
      <span>{{ this.shopInfo.shopName }}</span>
      <el-tooltip
        effect="light"
        placement="bottom-start"
      >
        <div slot="content">
          <div class="system_info_content_top">{{ this.$t('overview.editionTip1') }}<span class="version_name">{{ this.shopInfo.shopTypeNes }}</span>，{{ this.$t('overview.editionTip2') }}：<span
              class="expire_time"
              v-if="this.shopInfo.expireTime!==null"
            >{{moment(this.shopInfo.expireTime).format('YYYY-MM-DD')}}</span></div>
          <div class="system_info_content_bottom">
            <el-button
              type="primary"
              size="mini"
            >{{ this.$t('overview.renew') }}</el-button>
            <el-button
              type="primary"
              size="mini"
            >{{ this.$t('overview.upgrade') }}</el-button>
          </div>
        </div>
        <span class="title_type_par">
          <span class="title_type">{{ this.$t('overview.edition') }}</span>
        </span>
      </el-tooltip>

      <el-tooltip
        effect="light"
        placement="bottom-start"
        v-if="shareData.imageUrl"
      >
        <div
          slot="content"
          style="width: 250px;text-align: center;"
        >
          <div style="border-bottom: 1px solid #eee;margin-bottom: 10px;">
            <p style="font-weight: bold; font-size: 14px;height: 30px; line-height:30px;">{{ this.$t('overview.shareTitle') }}</p>
            <!-- :src="image + '/upload/4748160/qrcode/1/T1P0_20191025150038.jpg'" -->
            <img
              style="width: 120px;margin: 10px 0;"
              :src="shareData.imageUrl"
              alt=""
            >
            <div style="margin-bottom: 10px;">
              <!-- :href="image + '/upload/4748160/qrcode/1/T1P0_20191025150038.jpg'" -->
              <a
                :href="shareData.imageUrl"
                download
                style="color:#999;cursor:pointer;"
              >{{ this.$t('overview.shareDownload') }}</a>
            </div>
          </div>
          <div>
            <el-input v-model="shareData.sharePath">
              <el-button
                slot="append"
                v-clipboard:copy="shareData.sharePath"
                v-clipboard:success="copyHandler"
              >{{ this.$t('overview.copy') }}</el-button>
            </el-input>
          </div>

        </div>
        <div class="title_share">
          <img
            :src="image + '/image/admin/img_home/share_shop.png'"
            alt=""
          >{{ this.$t('overview.shareShop') }}
        </div>
      </el-tooltip>

      <el-tooltip
        effect="light"
        placement="bottom-start"
        v-if="!shareData.imageUrl"
      >
        <div slot="content">{{ $t('overview.shareDefault') }}</div>
        <div class="title_share">
          <img
            :src="image + '/image/admin/img_home/share_shop.png'"
            alt=""
          >{{ this.$t('overview.shareShop') }}
        </div>
      </el-tooltip>

    </div>

    <div class="main-container">
      <div class="over-left">
        <div class="left-agency">
          <div class="left-title">
            {{ this.$t('overview.agencyTitle') }}
            <span
              class="custom_title"
              @click="customizeHandler"
            >{{ this.$t('overview.agencyCustom') }}</span>
            <div class="one_piece">
              <!-- 账号绑定 -->
              <bindAccount />
            </div>
          </div>
          <div class="left-order-content">
            <div
              class="new_order"
              v-for="(item, index) in checkList"
              :key="index"
              v-if="item.isCheck === true"
            >
              <a href="javascript:void(0);">
                <div class="order_top">{{ item.num }}</div>
                <p>{{ item.label }}</p>
              </a>
            </div>
          </div>
        </div>
        <div class="left-data">
          <div
            class="left-title clearfix"
            style="margin-bottom: 30px"
          >
            <span>{{ this.$t('overview.dataTitle') }}</span>
            <el-tooltip
              effect="dark"
              placement="right"
            >
              <div
                slot="content"
                style="width: 700px;line-height: 1.5"
              >
                {{ this.$t('overview.dataTip1') }}<br />
                {{ this.$t('overview.dataTip2') }}<br />
                {{ this.$t('overview.dataTip3') }}<br />
                {{ this.$t('overview.dataTip4') }}<br />
                {{ this.$t('overview.dataTip5') }}<br />
                {{ this.$t('overview.dataTip6') }}<br />
                {{ this.$t('overview.dataTip7') }}<br />
                {{ this.$t('overview.dataTip8') }}<br />
              </div>
              <i class="item-image">
                <img
                  :src="image + '/image/admin/analysis_tishi.png'"
                  alt=""
                  width="14"
                  height="14"
                  style="vertical-align: middle;margin-bottom: 2px;"
                >
              </i>
            </el-tooltip>
            <div style="display:inline-block;float:right;">
              <el-select
                v-model="screeningTime"
                @change="dateChangeHandler"
                size="mini"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </div>

          </div>
          <div class="left-data-content">
            <div class="left-datas">
              <div class="single-data">
                <h4>{{ this.$t('overview.userVisitNum') }}</h4>
                <h3>{{ dataList.userVisitNum }}</h3>
              </div>
              <div class="single-data">
                <h4>{{ this.$t('overview.orderNum') }}</h4>
                <h3>{{ dataList.orderNum }}</h3>
              </div>
              <div class="single-data">
                <h4>{{ this.$t('overview.orderUserNum') }}</h4>
                <h3>{{ dataList.orderUserNum }}</h3>
              </div>
              <div class="single-data">
                <h4>{{ this.$t('overview.paidOrderNum') }}</h4>
                <h3>{{ dataList.paidOrderNum }}</h3>
              </div>
              <div class="single-data">
                <h4>{{ this.$t('overview.totalPaidSum') }}</h4>
                <h3>{{ dataList.totalPaidSum }}</h3>
              </div>
              <div class="single-data">
                <h4>{{ this.$t('overview.paidUserNum') }}</h4>
                <h3>{{ dataList.paidUserNum }}</h3>
              </div>
            </div>
            <div class="right-datas">
              <div class="data-img clearfix">
                <div class="fw-app">
                  <div class="data-title">{{ this.$t('overview.uv2paid') }}</div>
                  <div class="data-text">{{ dataList.uv2paid }}%</div>
                </div>
                <div class="fw-xd">
                  <div class="data-title">{{ this.$t('overview.uv2order') }}</div>
                  <div class="data-text">{{ dataList.uv2order }}%</div>
                </div>
                <div class="xd-app">
                  <div class="data-title">{{ this.$t('overview.order2paid') }}</div>
                  <div class="data-text">{{ dataList.order2paid }}%</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="left-function">
          <div class="left-title">{{ this.$t('overview.functionTitle') }}</div>
          <div class="function-content">
            <a
              :href="item.link"
              target="_blank"
              class="single-func"
              v-for="(item, index) in functionList"
              :key="index"
            >
              <img
                :src="image + item.icon"
                alt=""
              >
              <span :style="{'position':(index === 1 || index === 5) ? 'relative':''}">{{ item.title }}
                <img
                  v-if="index === 1"
                  style="position: absolute;left: 40px"
                  :src="image + '/image/admin/new_ov/Hot.png'"
                  alt=""
                >
                <img
                  v-if="index === 5"
                  style="position: absolute;left: 75px"
                  :src="image + '/image/admin/new_ov/Hot.png'"
                  alt=""
                >
              </span>
            </a>
          </div>
        </div>
        <div class="left-store">
          <div class="left-title">{{ this.$t('overview.storeTitle') }}</div>
          <div class="task_content clearfix">
            <div class="task_left">
              <div class="progress_content">
                <el-progress
                  type="circle"
                  :stroke-width="15"
                  :width="140"
                  :percentage="percentage"
                  :show-text="false"
                ></el-progress>
                <div class="progress-info">
                  <div class="status-text">
                    <p>
                      <span
                        class="status-text_count"
                        v-if="storeList.totalNum > 0"
                      >{{ storeList.totalNum }}</span>
                      <span
                        class="status-text_count"
                        v-if="storeList.totalNum === 0"
                      >0</span>
                      {{ this.$t('overview.storeItems') }}
                    </p>
                    <p>{{ this.$t('overview.storePending') }}</p>
                  </div>
                </div>
              </div>
              <button
                class="task_test_btn"
                @click="storeRefresh"
              >{{ this.$t('overview.storeRefresh') }}</button>
            </div>
            <div class="task_right">
              <div class="task_type clearfix">

                <el-tabs
                  v-model="tabSwitch"
                  :lazy="true"
                  style="height: 40px;"
                >
                  <el-tab-pane
                    v-for="(item, index) in storeTabs"
                    :key="index"
                    :label="item.label"
                    :name="item.name"
                    label-width="200px;"
                  >
                  </el-tab-pane>
                </el-tabs>
                <a
                  href="/admin/home/main/overviewOfMall/taskList"
                  target="_blank"
                  class="view_more"
                >{{ this.$t('overview.storeMore') }}</a>
              </div>
              <div
                class="task_list_content"
                v-if="tabSwitch === '1'"
              >
                <div
                  class="task_list"
                  v-if="storeList.dataShop && storeList.dataGoods && storeList.dataOrder && storeList.dataMarket"
                >
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.wxPayConfigInfo === 2"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip1') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.childAccountConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip2') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.officialAccountConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip3') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.homePageConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip4') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.shopRecommendConf !== 1"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip5') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.customServiceConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip6') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.shipTemplateConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unStoreTip1') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unStoreTip2') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsStoreConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataGoods.goodsStoreConf }} {{ $t('overview.unStoreTip3') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsUnsalableConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataGoods.goodsUnsalableConf }} {{ $t('overview.unStoreTip4') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsComment !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataGoods.goodsComment }} {{ $t('overview.unStoreTip5') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsRecommend === 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unStoreTip6') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.shopSort === 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unStoreTip7') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataOrder.deliver !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataOrder.deliver }} {{ $t('overview.unOrderTip1') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataOrder.refund !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataOrder.refund }} {{ $t('overview.unOrderTip2') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataMarket.examine !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataMarket.examine }} {{ $t('overview.unMarketTip1') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div v-if="storeList.dataMarket.member !== null">
                    <div class="task_list_item">
                      <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                      <span class="task_list_desc">{{ storeList.dataMarket.member.card_name }} {{ $t('overview.unMarketTip5') }} {{ storeList.dataMarket.member.card_num }} {{ $t('overview.unMarketTip6') }}</span>
                      <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                    </div>
                  </div>
                  <div v-if="storeList.dataMarket.voucher !== null">
                    <div
                      class="task_list_item"
                      v-for="(val, key, index) in storeList.dataMarket.voucher"
                      :key="index"
                    >
                      <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                      <span class="task_list_desc">{{ $t('overview.unMarketTip3') }} "{{ val }}" {{ $t('overview.unMarketTip4') }}</span>
                      <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                    </div>
                  </div>
                </div>
              </div>
              <div
                class="task_list_content"
                v-if="tabSwitch === '2'"
              >
                <div
                  class="task_list"
                  v-if="storeList.dataShop"
                >
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.wxPayConfigInfo === 2"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip1') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.childAccountConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip2') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.officialAccountConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip3') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.homePageConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip4') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.shopRecommendConf !== 1"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip5') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataShop.customServiceConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unShopTip6') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                </div>
              </div>
              <div
                class="task_list_content"
                v-if="tabSwitch === '3'"
              >
                <div
                  class="task_list"
                  v-if="storeList.dataGoods"
                >
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.shipTemplateConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unStoreTip1') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unStoreTip2') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsStoreConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataGoods.goodsStoreConf }} {{ $t('overview.unStoreTip3') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsUnsalableConf !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataGoods.goodsUnsalableConf }} {{ $t('overview.unStoreTip4') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsComment !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataGoods.goodsComment }} {{ $t('overview.unStoreTip5') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.goodsRecommend === 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unStoreTip6') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataGoods.shopSort === 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ $t('overview.unStoreTip7') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                </div>
              </div>
              <div
                class="task_list_content"
                v-if="tabSwitch === '4'"
              >
                <div
                  class="task_list"
                  v-if="storeList.dataOrder"
                >
                  <div
                    class="task_list_item"
                    v-if="storeList.dataOrder.deliver !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataOrder.deliver }} {{ $t('overview.unOrderTip1') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div
                    class="task_list_item"
                    v-if="storeList.dataOrder.refund !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataOrder.refund }} {{ $t('overview.unOrderTip2') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                </div>
              </div>
              <div
                class="task_list_content"
                v-if="tabSwitch === '5'"
              >
                <div
                  class="task_list"
                  v-if="storeList.dataMarket"
                >
                  <div
                    class="task_list_item"
                    v-if="storeList.dataMarket.examine !== 0"
                  >
                    <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                    <span class="task_list_desc">{{ storeList.dataMarket.examine }} {{ $t('overview.unMarketTip1') }}</span>
                    <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                  </div>
                  <div v-if="storeList.dataMarket.member !== null">
                    <!-- <div
                      class="task_list_item"
                      v-for="(val, key, index) in storeList.dataMarket.member"
                      :key="index"
                    >
                      <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                      <span class="task_list_desc">{{ val }} {{ $t('overview.unMarketTip2') }}</span>
                      <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                    </div> -->
                    <div class="task_list_item">
                      <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                      <span class="task_list_desc">{{ storeList.dataMarket.member.card_name }} {{ $t('overview.unMarketTip5') }} {{ storeList.dataMarket.member.card_num }} {{ $t('overview.unMarketTip6') }}</span>
                      <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                    </div>
                  </div>
                  <div v-if="storeList.dataMarket.voucher !== null">
                    <div
                      class="task_list_item"
                      v-for="(val, key, index) in storeList.dataMarket.voucher"
                      :key="index"
                    >
                      <span class="tips ff4444">{{ $t('overview.storeTip') }}</span>
                      <span class="task_list_desc">{{ $t('overview.unMarketTip3') }} "{{ val }}" {{ $t('overview.unMarketTip4') }}</span>
                      <a href="javascript:void(0);">{{ $t('overview.storeGo') }}</a>
                    </div>
                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="over-right">
        <div class="right-notice">
          <div class="right-title">
            <span>{{ this.$t('overview.noticeTitle') }}</span>
            <a class="gengduo"><span @click="toList">{{ this.$t('overview.noticeMore') }}</span>
              <img
                :src="image + '/image/admin/new_ov/go.png'"
                alt=""
              >
            </a>
          </div>
          <div class="one-zx">
            <div
              class="single-zx"
              v-for="(item, index) in noticeList"
              :key="index"
              @click="noticeDetail(item.articleId)"
            >
              <span class="circle"></span>
              <a
                href="javascript:void(0);"
                class="zx-text"
              >{{ item.title }}</a>
              <span class="zx-time">{{ item.formatTime }}</span>
            </div>
          </div>
        </div>
        <div class="right-calendar">
          <div class="right-title">
            <span>{{ this.$t('overview.calendarTitle') }}</span>
            <a href="javascript:void(0);">
              <img
                :src="image + '/image/admin/new_ov/calendar_icon.png'"
                alt=""
              >
            </a>
          </div>
          <div class="calendar_line"></div>
          <div class="flex_calendar_box">
            <a href="javascript:void(0);">
              <p class="data">10-24</p>
              <p class="act_name">叶已纷黄，霜降已至</p>
              <p class="remaining_time">剩<span>0</span>天</p>
              <div class="dot"></div>
            </a>
            <a href="javascript:void(0);">
              <p class="data">10-24</p>
              <p class="act_name">叶已纷黄，霜降已至</p>
              <p class="remaining_time">剩<span>0</span>天</p>
              <div class="dot"></div>
            </a>
          </div>
        </div>
        <div class="right-carousel">
          <el-carousel
            height="200px"
            arrow="never"
            ref="carousel"
            indicator-position="none"
            @change="carouselChange"
          >
            <el-carousel-item
              style="height: 100%; width: 100%;"
              v-for="(item, index) in carouselList"
              :key="index"
            >
              <a
                :href="item.link"
                target="_blank"
              >
                <img
                  style="height: 100%; width: 100%;"
                  :src="image + item.img"
                  alt=""
                >
              </a>
            </el-carousel-item>
          </el-carousel>
          <div class="rounds">
            <ul>
              <li
                v-for="(item, index) in carouselList"
                :key="index"
                :class="indValue === index ? 'active' : '' "
                @click="indexClickHandler(index)"
              ></li>
            </ul>
          </div>

        </div>
        <div class="right-serve">
          <div class="right-title">{{ this.$t('overview.serveTitle') }}</div>
          <div class="two-zx">
            <a
              class="single-icon"
              target="_blank"
              :href="item.link"
              v-for="(item, index) in serveList"
              :key="index"
            >
              <div class="icon-img">
                <img
                  :src="image + item.icon"
                  alt=""
                >
              </div>
              <div class="icon-name">{{ item.title }}</div>
            </a>

          </div>
        </div>
      </div>
    </div>

    <!-- 自定义代办事项 -->
    <el-dialog
      :title="this.$t('overview.agencyCustom') + this.$t('overview.agencyTitle')"
      :visible.sync="dataDialog"
      :close-on-click-modal="false"
      :before-close="closeCheckHandler"
      center
      width="40%"
    >
      <p style="color: #999;font-size: 14px;">{{ this.$t('overview.agencyTip') }}</p>
      <el-checkbox-group
        v-model="checkData"
        :max='5'
        style="margin-top: 20px;width: 100%;"
      >
        <el-checkbox
          v-for="item in checkList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
          style="margin-bottom: 10px;width: 25%;"
        ></el-checkbox>
      </el-checkbox-group>
    </el-dialog>

  </div>
</template>
<script>
// 引入组件
import VCharts from 'v-charts'
import bindAccount from './overviewBindAccount.vue'
import { getAllOverview, shopShareRequest, toDoItemRequest, dataRequest, shopInfoRequest } from '@/api/admin/survey.js'
export default {
  components: {
    VCharts,
    bindAccount
  },
  data () {
    return {
      image: 'http://mpdevimg2.weipubao.cn',
      shareData: {}, // 分享店铺
      dataDialog: false, // 自定义事项弹框
      // 选中自定义事项
      checkData: this.$t('overview.checkData'),
      // 自定义列表
      checkList: this.$t('overview.checkList'),
      screeningTime: 1, // 数据日期范围
      // 数据日期列表
      options: this.$t('overview.options'),
      // 数据列表信息
      dataList: {},
      // 功能列表
      functionList: this.$t('overview.functionList'),
      tabSwitch: '1',
      // 店铺列表
      flag: 1, // 进度条状态
      percentage: 0,
      storeList: {},
      storeTabs: this.$t('overview.storeTabs'),
      // 公告列表
      noticeList: [],
      // 轮播图数据
      carouselList: [{
        id: '1',
        img: '/image/admin/overview_banner/banner1.jpg',
        link: ''
      }, {
        id: '2',
        img: '/image/admin/overview_banner/banner2.jpg',
        link: 'http://www.wangdian.cn/'
      }, {
        id: '3',
        img: '/image/admin/overview_banner/banner3.jpg',
        link: 'http://pos.wangdian.cn/'
      }],
      indValue: '', // 轮播的索引
      // 服务列表
      serveList: this.$t('overview.serveList'),
      shopInfo: {},
      avatarImage: this.$imageHost + '/image/admin/shop_logo_default.png'

    }
  },
  created () {
    // 初始化数据
    this.getAllOverview()
    this.getShopShare()
    this.getShopInfo()
  },
  mounted () {
    this.langDefault()
    // 定时器
    clearInterval(this.timer)
  },
  watch: {
    lang () {
      this.checkData = this.$t('overview.checkData')
      this.checkList = this.$t('overview.checkList')
      this.options = this.$t('overview.options')
      this.functionList = this.$t('overview.functionList')
      this.storeTabs = this.$t('overview.storeTabs')
      this.serveList = this.$t('overview.serveList')
      this.getAllOverview()
      this.getShopShare()
      this.getShopInfo()
    }
  },
  methods: {
    // 获取全部数据
    getAllOverview () {
      let obj = {
        // 代办参数
        fixedAnnouncementParam: {
          fixedNum: 6,
          orderBy: 'desc'
        },
        // 数据参数
        dataDemonstrationParam: {
          screeningTime: this.screeningTime
        },
        // 店铺参数
        shopAssistantParam: {
          isAuthOk: 1,
          storeSizeNum: 5,
          commentOver: 3,
          deliverOver: 3,
          refundOver: 3,
          applyOver: 3,
          examineOver: 2,
          couponSizeNum: 10
        }
      }
      getAllOverview(obj).then((res) => {
        if (res.error === 0) {
          // 代办
          let data = res.content.toDoItemVo
          for (var i = 0; i < this.checkList.length; i++) {
            if (this.checkList[i].label === '待发货订单' || this.checkList[i].label === 'To be shipped') {
              this.checkList[i].num = data.toBeDelivered
            } else if (this.checkList[i].label === '待处理退款退货' || this.checkList[i].label === 'Pending disposal') {
              this.checkList[i].num = data.refunds
            } else if (this.checkList[i].label === '已售罄商品' || this.checkList[i].label === 'Sold out') {
              this.checkList[i].num = data.soldOutGoods
            } else if (this.checkList[i].label === '商品评价待审核' || this.checkList[i].label === 'To be audited') {
              this.checkList[i].num = data.productEvaluationPr
            } else if (this.checkList[i].label === '待提货订单' || this.checkList[i].label === 'Waiting for delivery') {
              this.checkList[i].num = data.pendingOrder
            } else if (this.checkList[i].label === '分销员待审核' || this.checkList[i].label === 'Reviewed by distributor') {
              this.checkList[i].num = data.distributorPr
            } else if (this.checkList[i].label === '会员卡激活待审核' || this.checkList[i].label === 'Membership activation') {
              this.checkList[i].num = data.membershipCardPr
            } else if (this.checkList[i].label === '分销提现待审核' || this.checkList[i].label === 'Cash withdrawal') {
              this.checkList[i].num = data.distributionWithdrawalPr
            } else if (this.checkList[i].label === '服务评价待审核' || this.checkList[i].label === 'Service evaluation') {
              this.checkList[i].num = data.serviceEvaluationPr
            }
            this.checkList[i].isCheck = false
            for (var j = 0; j < this.checkData.length; j++) {
              if (this.checkList[i].label === this.checkData[j]) {
                this.checkList[i].isCheck = true
              }
            }
          }
          // 数据
          this.dataList = res.content.dataDemonstrationVo
          // 店铺
          this.storeList = res.content.shopAssistantVo
          // 公告
          this.noticeList = res.content.announcementVoList
          if (this.storeList.totalNum === 0) {
            this.percentage = 100
          } else {
            this.percentage = 45
          }
        }
      })
    },

    // 店铺分享
    getShopShare () {
      shopShareRequest().then((res) => {
        if (res.error === 0) {
          this.shareData = res.content
        }
      })
    },

    // 复制
    copyHandler (e) {
      this.$message.success({ message: this.$t('seckill.copySuccess') })
    },

    // 代办事项
    getTodoDate () {
      toDoItemRequest().then((res) => {
        let data = res.content
        if (res.error === 0) {
          for (var i = 0; i < this.checkList.length; i++) {
            if (this.checkList[i].label === '待发货订单' || this.checkList[i].label === 'Order to be shipped') {
              this.checkList[i].num = data.toBeDelivered
            } else if (this.checkList[i].label === '待处理退款退货' || this.checkList[i].label === 'Refund return to be processed') {
              this.checkList[i].num = data.refunds
            } else if (this.checkList[i].label === '已售罄商品' || this.checkList[i].label === 'Sold out') {
              this.checkList[i].num = data.soldOutGoods
            } else if (this.checkList[i].label === '商品评价待审核' || this.checkList[i].label === 'Commodity evaluation to be reviewed') {
              this.checkList[i].num = data.productEvaluationPr
            } else if (this.checkList[i].label === '待提货订单' || this.checkList[i].label === 'Order to be picked up') {
              this.checkList[i].num = data.pendingOrder
            } else if (this.checkList[i].label === '分销员待审核' || this.checkList[i].label === 'Distributor to be reviewed') {
              this.checkList[i].num = data.distributorPr
            } else if (this.checkList[i].label === '会员卡激活待审核' || this.checkList[i].label === 'Membership card activation to be reviewed') {
              this.checkList[i].num = data.membershipCardPr
            } else if (this.checkList[i].label === '分销提现待审核' || this.checkList[i].label === 'Distribution withdrawal to be reviewed') {
              this.checkList[i].num = data.distributionWithdrawalPr
            } else if (this.checkList[i].label === '服务评价待审核' || this.checkList[i].label === 'Service evaluation to be reviewed') {
              this.checkList[i].num = data.serviceEvaluationPr
            }
            this.checkList[i].isCheck = false
            for (var j = 0; j < this.checkData.length; j++) {
              if (this.checkList[i].label === this.checkData[j]) {
                this.checkList[i].isCheck = true
              }
            }
          }
        }
      })
    },

    // 自定义事项弹框
    customizeHandler () {
      this.dataDialog = true
    },

    // 关闭弹窗
    closeCheckHandler (done) {
      if (this.checkData.length === 5) {
        done()
        this.getTodoDate()
      } else {
        this.$message.warning({ message: this.$t('overview.agencyTip') })
      }
    },

    // 数据展示
    getShowData () {
      dataRequest({ screeningTime: this.screeningTime }).then((res) => {
        if (res.error === 0) {
          this.dataList = res.content
        }
      })
    },

    // 数据日期切换
    dateChangeHandler (value) {
      this.screeningTime = value
      this.getShowData()
    },

    // 公告详情
    noticeDetail (id) {
      let routeUrl = this.$router.resolve({ path: '/admin/home/shopMain', query: { id: id, change_components: '8' } })
      window.open(routeUrl.href, '_blank')
    },

    // 切换轮播图
    carouselChange (index) {
      this.indValue = index
    },

    // 索引切换
    indexClickHandler (index) {
      this.$refs.carousel.setActiveItem(index)
      this.indValue = index
    },

    // 店铺信息
    getShopInfo () {
      shopInfoRequest().then((res) => {
        if (res.error === 0) {
          this.shopInfo = res.content
          switch (this.shopInfo.shopType) {
            case 'v1':
              this.shopInfo.shopTypeNes = this.$t('overview.experienceVersion')
              break
            case 'v2':
              this.shopInfo.shopTypeNes = this.$t('overview.basicEdition')
              break
            case 'v3':
              this.shopInfo.shopTypeNes = this.$t('overview.advancedVersion')
              break
            case 'v4':
              this.shopInfo.shopTypeNes = this.$t('overview.Ultimate')
              break
          }
          switch (this.shopInfo.businessState) {
            case 0:
              this.shopInfo.businessStateNes = this.$t('overview.unUserTip')
              break
            case 1:
              this.shopInfo.businessStateNes = this.$t('overview.enUserTip')
              break
          }
          if (this.shopInfo.expireTimeStatus === '1') {
            this.shopInfo.businessStateNes = this.$t('overview.userExpired')
          }
        }
      })
    },
    toList () {
      let routeUrl = this.$router.resolve({
        path: '/admin/home/shopMain',
        query: {
          change_components: '7'
        }
      })
      window.open(routeUrl.href, '_blank')
    },

    // 刷新
    storeRefresh () {
      let _this = this
      _this.timer = setInterval(() => {
        if (_this.flag === 1) {
          _this.percentage += 15
          if (_this.percentage > 100) {
            _this.percentage = 100
            _this.flag = 0
          }
        } else {
          _this.percentage -= 15
          if (_this.percentage < 45) {
            _this.percentage = 45
            _this.flag = 1
            clearInterval(_this.timer)
          }
        }
      }, 300)
    }

  },
  beforeDestroy () {
    // 清除定时器
    clearInterval(this.timer)
  }
}
</script>
<style lang="scss" scoped>
.title {
  background: #f5f5f5 !important;
  position: relative;

  width: 100%;
  height: 55px;
  line-height: 55px;
  padding-left: 25px;
  font-size: 16px;
  color: #333;
  background: #fff;
  padding-right: 25px;
}

.fl {
  float: left;
  position: relative;
  margin-top: -1px;
  cursor: pointer;
}

.title .fl > span img {
  position: absolute;
  right: 0;
  bottom: -8px;
  z-index: 9;
}

.title .fl > span > span {
  position: absolute;
  right: -33px;
  bottom: -33px;
  display: inline-block;
  width: 55px;
  height: 26px;
  line-height: 26px;
  background: #fff;
  text-align: center;
  border: 1px solid #ddd;
  font-size: 12px;
}

.title > span {
  color: #333;
  font-size: 16px;
  display: inline-block;
  margin-left: 20px;
}

.title_type_par {
  position: relative;
  height: 35px;
}

.title .title_type {
  background: #457bf9;
  color: #fff;
  font-size: 14px;
  width: 60px;
  height: 23px;
  line-height: 23px;
  text-align: center;
  -webkit-border-radius: 12px;
  -moz-border-radius: 12px;
  border-radius: 12px;
  cursor: pointer;
  display: inline-block;
}

.system_shadow {
  top: 22px;

  position: absolute;
  left: 19px;
  z-index: 109;
  top: 29px;
  display: none;
}

.system_info_content {
  font-size: 12px;
  top: 34px;

  width: 300px;
  padding: 10px;
  background: #fff;
  box-shadow: 0px 0px 10px #f0f0f0;
  position: absolute;
  z-index: 100;
  display: none;
}

.system_info_content_top {
  width: 100%;
  border-bottom: 1px solid #eee;
  line-height: 2;
}

.system_info_content_bottom {
  text-align: center;
  margin: 10px 0;
}

// .system_info_content_top {
//   padding-bottom: 10px;
//   border-bottom: 1px solid #eee;
//   line-height: 20px;
// }

// .system_info_content_bottom {
//   text-align: center;
// }

// .system_info_content_bottom a {
//   display: inline-block;
//   margin: 10px auto 0;
//   width: 70px;
//   text-align: center;
//   height: 30px;
//   line-height: 30px;
//   background: #5a8bff;
//   color: #fff;
//   cursor: pointer;
//   font-size: 12px;
// }

// .system_info_content_bottom a:first-child {
//   margin-right: 20px;
// }

.title_share {
  width: 120px;

  float: right;
  padding-right: 25px;
  font-size: 14px;
  cursor: pointer;
  width: 102px;
  position: relative;
}

.title_share .share_span {
  right: 10px;
  z-index: 99;

  padding: 15px 12px;
  border: 1px solid #eee;
  background: #fff;
  font-size: 14px;
  position: absolute;
  right: 0;
  top: 50px;
  width: 285px;
  text-align: center;
  display: none;
}

.share_sj {
  position: absolute;
  right: 75px;
  top: -7px;
}

.title_share span {
  display: inline-block;
}

.share_span .share_span_top {
  width: 100%;
  border-bottom: 1px solid #eee;
  line-height: 0;
  padding-bottom: 10px;
}

.share_span .share_span_top > span {
  color: #000;
  font-weight: bold;
  font-size: 14px;
  height: 30px;
  line-height: 30px;
}

.share_span .share_span_top img {
  display: block;
  margin: 0 auto;
}

.share_span .share_span_top a {
  color: #999;
  font-size: 13px;
  display: inline-block;
  height: 30px;
  line-height: 30px;
}

.title_share span {
  display: inline-block;
}

.share_link {
  padding-top: 15px;
  width: 100%;
}

.share_link input {
  background: #f7f7f7;
  border: 1px solid #f2f2f2;
  height: 35px;
  width: 220px;
  padding-left: 8px;
  float: left;
  font-size: 13px;
  color: #666;
}

.share_link button {
  float: right;
  color: #5a8bff;
  background: #fff;
  border: none;
  height: 35px;
  line-height: 35px;
}

.shop_defu {
  border-radius: 100%;
  width: 44px;
  border: 1px solid #fff;
}

.shop_type {
  position: absolute;
  right: 0;
  bottom: 0;
}

.main-container {
  width: 100%;
  overflow: hidden;
  padding: 10px;
}

.over-left {
  width: 74%;
  float: left;
}

.left-agency,
.left-data,
.left-function,
.left-store {
  width: 100%;
  background-color: #fff;
  border-radius: 2px;
  padding: 20px 20px;
}

.left-agency {
  height: 180px;
}

.left-title {
  font-size: 16px;
  color: #333;
  font-weight: 600;
  margin-bottom: 20px;
  position: relative;
}

.el-tooltip__popper {
  max-width: 600px;
}

.custom_title {
  float: none;
  margin-left: 20px;

  color: #5a8bff;
  font-weight: 400;
  font-size: 14px;
  line-height: 21px;
  cursor: pointer;
}

.one_piece {
  float: right;
  font-size: 14px;
  color: #333;
  font-weight: normal;

  width: 75%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.one_piece a {
  border: 1px solid #5a8bff;
  color: #5a8bff;
  padding: 5px 10px;
  border-radius: 2px;
  margin-left: 10px;
}

.left-order-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.new_order {
  text-align: center;
  width: 18%;
}

a {
  color: #333;
  text-decoration: none;
}

.left-order-content > .new_order:nth-child(1) > a .order_top {
  background-color: #dfecff;
  color: #5a8bff;
}

.left-order-content > .new_order:nth-child(2) > a .order_top {
  background-color: #ffdee4;
  color: #fc6181;
}

.left-order-content > .new_order:nth-child(3) > a .order_top {
  background-color: #fff0c4;
  color: #fdb64a;
}

.left-order-content > .new_order:nth-child(4) > a .order_top {
  background-color: #dfecff;
  color: #5a8bff;
}

.left-order-content > .new_order:nth-child(5) > a .order_top {
  background-color: #ffdee4;
  color: #fc6181;
}

.order_top {
  line-height: 56px;
  border-radius: 6px;
  font-size: 30px;
}

.new_order > a > p {
  margin-top: 12px;
  color: #666666;
}

.left-data {
  height: 230px;
  margin-top: 10px;
}

.left-data-content {
  display: flex;
  justify-content: flex-start;
}

.left-datas {
  width: 50%;
  height: 145px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: wrap;
}

.single-data {
  width: 30%;
  height: 60px;
  margin-right: 10px;
}

.single-data h4 {
  font-size: 12px;
  color: #666;
  margin-bottom: 5px;
}

.single-data h3 {
  font-size: 24px;
  color: #5a8bff;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.right-datas {
  width: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.data-img {
  margin-bottom: 10px;
  position: relative;
  background: url(http://mpdev.weipubao.cn/image/admin/new_ov/apply_order.png)
    no-repeat;
  width: 206px;
  height: 126px;
  margin-right: 30px;
}

.clearfix {
  zoom: 1;
}

.fw-app {
  position: absolute;
  left: -110px;
  top: 50px;
}

.data-title {
  font-size: 12px;
  color: #666;
  margin-bottom: 3px;
}

.data-text {
  font-size: 12px;
  color: #f96a6b;
  text-align: center;
}

.fw-xd {
  position: absolute;
  right: -100px;
  top: 20px;
}

.xd-app {
  position: absolute;
  bottom: 25px;
  right: -90px;
}

.left-function {
  margin-top: 10px;
}

.function-content {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  width: 90%;
  margin-bottom: -20px;
}

.single-func {
  width: 25%;
  margin-bottom: 20px;
  cursor: pointer;
}

img {
  vertical-align: middle;
}

.single-func span {
  padding-left: 8px;
}

.left-store {
  margin-top: 10px;
}

/*滚动条凹槽的颜色，还可以设置边框属性 */
::-webkit-scrollbar-track-piece {
  background-color: #f8f8f8;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
/*滚动条的宽度*/
::-webkit-scrollbar {
  width: 5px;
}
/*滚动条的设置*/
::-webkit-scrollbar-thumb {
  background-color: #dddddd;
  background-clip: padding-box;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
/*滚动条鼠标移上去*/
::-webkit-scrollbar-thumb:hover {
  background-color: #bbb;
}

.task_left {
  width: 195px;
  height: 257px;
  float: left;
}

.progress_content {
  width: 140px;
  height: 140px;
  margin: auto;
  position: relative;
  margin-top: 50px;
}

.progress-info {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  text-align: center;
  line-height: 140px;
}

.progress-info > .status-text {
  display: flex;
  height: 100%;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  line-height: 14px;
}

.progress-info > .status-text > p {
  font-size: 14px;
  color: #999;
}

.progress-info > .status-text > p > .status-text_count {
  font-size: 30px;
  color: #ff4444;
}

.task_right {
  height: 257px;
  margin-left: 200px;
}

.task_type {
  position: relative;

  // border-bottom: 1px solid #e5e5e5;
  // line-height: 39px;
  // height: 40px;
}

.task_type a {
  position: absolute;
  bottom: 8px;
  right: 0;
}

.view_more {
  color: #5a8bff;
  float: right;
}

.task_right a:link,
.task_right a:visited,
.task_right a:hover,
.task_right a:active {
  color: #5a8bff;
  margin-left: 30px;
}

.task_list_content {
  height: 100%;
  max-height: 215px;
  overflow-y: auto;
  padding-right: 10px;
}

.task_list_content .task_list {
  padding-bottom: 12px;
}

.task_list_content .task_list:first-child {
  border-top: 0;
}

.task_list .task_list_item {
  margin-top: 12px;
  line-height: 18px;
  display: flex;
  align-items: center;
}

// .tips .ff4444 {
//   color: #ff4444;
//   border-color: #ff4444;
// }

.task_list_item .tips {
  font-size: 12px;
  color: #ff4444;
  border: 1px solid #ff4444;
  // border: 1px solid #5a8bff;
  // color: #5a8bff;
  border-radius: 10px;
  letter-spacing: 1px;
  padding: 0 4px 0 5px;
  line-height: 16px;
}

.task_list_item .task_list_desc {
  flex: 1;
  margin-left: 4px;
  font-size: 14px;
  line-height: 18px;
}

.task_right a:link,
.task_right a:visited,
.task_right a:hover,
.task_right a:active {
  color: #5a8bff;
  margin-left: 30px;
}

.task_test_btn {
  display: block;
  width: 100px;
  line-height: 30px;
  margin: 20px auto 0;
  border: 1px solid #5a8bff;
  background-color: #fff;
  color: #5a8bff;
  border-radius: 4px;
  cursor: pointer;
}

.over-right {
  width: 25%;
  float: left;
  margin-left: 10px;
}

.right-notice {
  width: 100%;
  height: 250px;
  background-color: #fff;
  margin-bottom: 10px;
  padding: 10px 15px;
}

.right-title {
  font-size: 16px;
  color: #333;
  font-weight: 600;
  margin-bottom: 17px;
}

.gengduo {
  font-size: 12px;
  color: #666;
  display: inline-block;
  float: right;
  font-weight: 400;
}

.one-zx {
  width: 100%;
  height: 85%;
  overflow: hidden;
}

.single-zx {
  width: 100%;
  height: 32px;
  border-bottom: 1px solid #eee;
  font-size: 12px;
  color: #666;
  line-height: 32px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.single-zx span {
  display: inline-block;
}

.circle {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background-color: #666;
}

.zx-text {
  padding-left: 0px;
  cursor: pointer;
  width: 66%;
  display: inline-block;
  height: 32px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.zx-time {
  width: 13%;
  float: right;
  line-height: 32px;
  height: 32px;
}

.right-calendar {
  background-color: #fff;
  width: 100%;
  padding: 18px;
  margin-bottom: 10px;
}

.right-calendar > .right-title > a {
  float: right;
}

.calendar_line {
  height: 2px;
  background-color: #f66;
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 20px;
}

.flex_calendar_box {
  display: flex;
}

.flex_calendar_box > a {
  flex: 1;
  background-color: #fff;
  border-radius: 6px;
  border: 1px solid #eee;
  text-align: center;
  padding-bottom: 10px;
  position: relative;
  min-width: 0;
}

.flex_calendar_box > a > .data {
  line-height: 24px;
  background-color: #f66;
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
  margin: -1px -1px 0 -1px;
  color: #fff;
}

.flex_calendar_box > a > p {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.flex_calendar_box > a > .act_name {
  line-height: 30px;
  color: #333;
}

.flex_calendar_box > a > .remaining_time {
  line-height: 24px;
  color: #333;
}

.flex_calendar_box > a > .dot {
  width: 12px;
  height: 12px;
  position: absolute;
  background-color: #ff6666;
  border: 3px solid rgba(255, 210, 210, 0.8);
  border-radius: 50%;
  left: 50%;
  top: -28.5px;
  transform: translate(-50%);
}

.right-carousel {
  width: 100%;
  height: 200px;
  background-color: #fff;
  margin-bottom: 10px;

  position: relative;
}

.right-carousel .rounds {
  width: 100%;
  position: absolute;
  bottom: 10px;
  left: 50%;
  margin-left: -22px;
  text-align: center;
  z-index: 999;
}

.right-carousel .rounds ul li {
  float: left;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-right: 7px;
  border: 1px solid #fff;
}

.right-carousel .rounds .active {
  background: #fff;
}

.right-serve {
  width: 100%;
  height: 270px;
  background-color: #fff;
  margin-bottom: 10px;
  padding: 10px 15px;
}

.two-zx {
  width: 100%;
  height: 85%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  margin-top: 20px;
}

.single-icon {
  width: 33.33%;
  height: 100px;
  cursor: pointer;
}

.single-icon {
  width: 33.33%;
  height: 100px;
  cursor: pointer;
}

.icon-img {
  margin-bottom: 10px;
  text-align: center;
}

.icon-name {
  font-size: 12px;
  color: #333;
  text-align: center;
}

// .one_piece {
//   margin: 0;
//   padding: 0;
//   float: right;
//   font-size: 14px;
//   color: #333;
//   background-color: #fff;
//   font-weight: normal;
// }
// /deep/ .btn_follow {
//   border: 1px solid #5a8bff;
//   color: #5a8bff;
//   padding: 5px 10px;
//   border-radius: 2px;
//   margin-left: 10px;
// }
// .off-area {
//   display: flex;
//   flex-direction: column;
//   align-items: center;
//   justify-content: center;
//   color: #666;
// }
</style>
