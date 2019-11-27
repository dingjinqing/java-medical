<template>
  <!-- 店铺通用设置 -->
  <div class="container store_common_setting_page">
    <div class="content">
      <ul>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.salesDisplaySettings')}}</span>
          </div>
          <div class="text-set clearfix">
            <el-switch
              v-model="info.sales_number"
              active-color="#F7931E"
              inactive-color="#ccc"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
            <span
              class="con4-hide"
              v-show="info.sales_number===0"
            >{{$t('storeCommonSettings.closed')}}</span>
            <span
              class="con4-hide"
              v-show="info.sales_number===1"
            >{{$t('storeCommonSettings.activated')}}</span>
            <span class="tip">{{$t('storeCommonSettings.salesNumberTip')}}</span>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.showCartTitle')}}</span>
          </div>
          <div class="text-set">
            <div class="top">
              <el-switch
                v-model="info.show_cart.show_cart"
                active-color="#F7931E"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
              <span
                class="con4-hide"
                v-show="info.show_cart.show_cart===0"
              >{{$t('storeCommonSettings.closed')}}</span>
              <span
                class="con4-hide"
                v-show="info.show_cart.show_cart===1"
              >{{$t('storeCommonSettings.activated')}}</span>
              <span class="tip">{{$t('storeCommonSettings.showCartTip')}}</span>
            </div>
            <div class="btn_cart">
              <el-radio-group v-model="info.show_cart.cart_type">
                <el-radio :label="0">
                  <i class="iconfont icontianjia icon_font_size new_class"></i>
                </el-radio>
                <el-radio :label="1">
                  <i class="iconfont icongouwuche1 icon_font_size new_class"></i>
                </el-radio>
                <el-radio :label="2">
                  <el-button
                    type="primary"
                    round
                    size="small"
                  >{{$t('storeCommonSettings.rushNow')}}</el-button>
                </el-radio>
                <el-radio :label="3">
                  <el-button
                    size="small"
                    round
                  >{{$t('storeCommonSettings.buy')}}</el-button>
                </el-radio>
              </el-radio-group>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.linePrice')}}</span>
          </div>
          <div class="text-set">
            <div class="top">
              <el-switch
                v-model="del_market_radio"
                active-color="#F7931E"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
              <span
                class="con4-hide"
                v-show="del_market_radio===0"
              >{{$t('storeCommonSettings.closed')}}</span>
              <span
                class="con4-hide"
                v-show="del_market_radio===1"
              >{{$t('storeCommonSettings.activated')}}</span>
              <span class="tip">{{$t('storeCommonSettings.delMarketTip')}}</span>
            </div>
            <div class="bottom">
              <el-radio-group v-model="info.del_market">
                <el-radio :label="1">
                  {{$t('storeCommonSettings.marketPrice')}}
                </el-radio>
                <el-radio :label="2">
                  {{$t('storeCommonSettings.sales')}}
                </el-radio>
                <el-radio :label="3">
                  {{$t('storeCommonSettings.evaluationNum')}}
                </el-radio>
              </el-radio-group>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.soldOutTitle')}}</span>
          </div>
          <div class="text-set clearfix">
            <el-switch
              v-model="info.sold_out_goods"
              active-color="#F7931E"
              inactive-color="#ccc"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
            <span
              class="con4-hide"
              v-show="info.sold_out_goods===0"
            >{{$t('storeCommonSettings.closed')}}</span>
            <span
              class="con4-hide"
              v-show="info.sold_out_goods===1"
            >{{$t('storeCommonSettings.activated')}}</span>
            <span class="tip">{{$t('storeCommonSettings.soldOutTip')}}</span>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.defaultSort')}}</span>
          </div>
          <div class="text-set clearfix">
            <p>{{$t('storeCommonSettings.defaultSortTip')}}：</p>
            <el-radio-group
              v-model="info.goods_sort"
              text-color="#333333"
            >
              <el-radio
                class="goods_sort_radio"
                label="add_time"
              >
                {{$t('storeCommonSettings.newTimeSort')}}
                <span class="tips">{{$t('storeCommonSettings.newTimeSortTip')}}</span>
              </el-radio>
              <el-radio
                class="goods_sort_radio"
                label="goods_sale_num"
              >
                {{$t('storeCommonSettings.salesVolume')}}
                <span class="tips">{{$t('storeCommonSettings.salesVolumeTip')}}</span>
              </el-radio>
              <el-radio
                class="goods_sort_radio"
                label="comment_num"
              >
                {{$t('storeCommonSettings.eveluationSort')}}
                <span class="tips">{{$t('storeCommonSettings.eveluationSortTip')}}</span>
              </el-radio>
              <el-radio
                class="goods_sort_radio"
                label="pv"
              >
                {{$t('storeCommonSettings.reverseSort')}}
                <span class="tips">{{$t('storeCommonSettings.reverseSortTip')}}</span>
              </el-radio>
            </el-radio-group>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.purchaseRecord')}}</span>
          </div>
          <div class="text-set clearfix">
            <el-switch
              v-model="info.goods_record"
              active-color="#F7931E"
              inactive-color="#ccc"
              :active-value="1"
              :inactive-value="0"
            >
            </el-switch>
            <span
              class="con4-hide"
              v-show="info.goods_record===0"
            >{{$t('storeCommonSettings.closed')}}</span>
            <span
              class="con4-hide"
              v-show="info.goods_record===1"
            >{{$t('storeCommonSettings.activated')}}</span>
            <span class="tip">{{$t('storeCommonSettings.goodsRecordTip')}} </span>
            <el-tooltip
              placement="right"
              effect="light"
            >
              <span style="color: #5a8bff;">{{$t('storeCommonSettings.viewExample')}}</span>
              <div slot="content">
                <el-image
                  :src="$imageHost + '/image/admin/purchase_record.jpg'"
                  style="width: 200px;height: 355.74px;border: 1px solid #eee;"
                ></el-image>
              </div>
            </el-tooltip>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.customerServiceConf')}}</span>
          </div>
          <div class="text-set">
            <span>{{$t('storeCommonSettings.customerServicePlacement')}}：
              <el-checkbox v-model="info.custom_service">{{$t('storeCommonSettings.productDetails')}}</el-checkbox>
              <el-checkbox v-model="info.return_service">{{$t('storeCommonSettings.returnServiceTip')}}</el-checkbox>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&amp;tid=685&amp;fromuid=1"
                target="_blank"
                class="to_bbc"
                style="color: #5a8bff;"
              >{{$t('storeCommonSettings.features')}}</a>
            </span>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.defaultPlatformClass')}}</span>
          </div>
          <div class="text-set clearfix">
            <span>{{$t('storeCommonSettings.defaultPlatformClass')}}： </span>
            <el-select
              v-model="info.default_sort"
              size="small"
              filterable
            >
              <el-option
                :label="$t('storeGoodsList.goodsSort')"
                :value="0"
              ></el-option>
              <el-option
                v-for="item in catIds"
                :key="item.catId"
                :label="item.catName"
                :value="item.catId"
              >
                <span :style="'paddingLeft:' + item.level*20 + 'px'">{{item.catName}}</span>
              </el-option>
            </el-select>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.storeSharing')}}</span>
          </div>
          <div class="text-set clearfix">
            <div class="share_div">
              <el-radio
                v-model="info.share_config.share_action"
                :label="0"
              >{{$t('storeCommonSettings.defaultStyle')}}</el-radio>
              <el-tooltip
                placement="right"
                effect="light"
              >
                <span style="color: #5a8bff;">{{$t('storeCommonSettings.viewExample')}}</span>
                <div
                  slot="content"
                  effect="light"
                >
                  <el-image
                    :src="$imageHost+ '/image/admin/share/shop_share.jpg'"
                    style="width: 200px;height: 355.74px;border: 1px solid #eee;"
                  ></el-image>
                </div>
              </el-tooltip>
            </div>
            <div class="share_div">
              <el-radio
                v-model="info.share_config.share_action"
                :label="1"
              >{{$t('storeCommonSettings.customStyle')}}</el-radio>
              <div
                class="custom_share"
                v-if="info.share_config.share_action == 1"
              >
                <div>
                  <label>文案：</label>
                  <el-input
                    size="small"
                    style="width: 150px;"
                    v-model="info.share_config.share_doc"
                  ></el-input>
                </div>
                <div class="share_img_wrap">
                  <label>{{$t('storeCommonSettings.shareTheMap')}}：</label>
                  <div>
                    <el-radio-group
                      v-model="info.share_config.share_img_action"
                      style="width: 100px; line-height:30px;margin-top:5px;"
                    >
                      <el-radio
                        :label="1"
                        style="color: #333;"
                      >{{$t('storeCommonSettings.storeHomeScreenshot')}}</el-radio>
                      <el-radio
                        :label="2"
                        style="color: #333;"
                      >{{$t('storeCommonSettings.customPicture')}}</el-radio>
                    </el-radio-group>
                    <div
                      class="img_wrap"
                      @click="selectShareImgHandle"
                      style="cursor: pointer;"
                    >
                      <el-image
                        :src="$imageHost + '/' + info.share_config.share_img"
                        style="width: 70px; height:70px;"
                      ></el-image>
                      <span class="tips">{{$t('storeCommonSettings.recommendedSize')}}：800*800</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.phoneAuthConf')}}</span>
          </div>
          <div class="text-set clearfix">
            <div class="top">
              <span>{{$t('storeCommonSettings.phoneNumAuth')}}</span>
              <el-switch
                v-model="info.bind_mobile"
                active-color="#F7931E"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
              <span
                class="con4-hide"
                v-show="info.bind_mobile===0"
              >{{$t('storeCommonSettings.closed')}}</span>
              <span
                class="con4-hide"
                v-show="info.bind_mobile===1"
              >{{$t('storeCommonSettings.activated')}}</span>
              <span class="tip">{{$t('storeCommonSettings.phoneNumAuthTip')}} </span>
            </div>
            <div class="bottom">
              <span>{{$t('storeCommonSettings.locationAuth')}}</span>
              <el-switch
                v-model="info.geographic_location"
                active-color="#F7931E"
                inactive-color="#ccc"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
              <span
                class="con4-hide"
                v-show="info.geographic_location===0"
              >{{$t('storeCommonSettings.closed')}}</span>
              <span
                class="con4-hide"
                v-show="info.geographic_location===1"
              >{{$t('storeCommonSettings.activated')}}</span>
              <span class="tip">{{$t('storeCommonSettings.locationAuthTip')}} </span>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>{{$t('storeCommonSettings.smsSettings')}}</span>
          </div>
          <div class="text-set">
            <p>
              {{$t('storeCommonSettings.currentRecharge')}}： <span class="bold">{{$t('storeCommonSettings.weipubao')}}</span>， {{$t('storeCommonSettings.balance')}}： <span class="bold">￥0.0000</span>， {{$t('storeCommonSettings.numMessages')}}： <span class="bold">0 </span>
              <a
                target="_blank"
                href="http://101.200.202.174/sms/api/alipay/index.php?sms_account=微铺宝电商运营"
                style="color: #5a8bff; cursor: pointer;"
              >{{$t('storeCommonSettings.gotoRecharge')}}</a>
            </p>
          </div>
        </li>
      </ul>
    </div>
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        class="footer-btn"
        @click="saveCommonInfoHandle"
      >{{$t('storeCommonSettings.save')}}</el-button>
    </div>

    <!-- 上传图片 -->
    <ImageDalog
      :tuneUp="tuneUp"
      pageIndex="pictureSpace"
      :imageSize="[800, 800]"
      @handleSelectImg="shareImgSelectHandle"
    ></ImageDalog>
  </div>
</template>

<script>
import { getCommonInfo, updateCommonInfo } from '@/api/admin/basicConfiguration/shopConfig'
import { getCateList } from '@/api/admin/storeManage/storeGoods'

export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog')
  },
  data () {
    return {
      value: 1,
      info: {
        sales_number: 1,
        show_cart: {
          show_cart: 0,
          cart_type: 0 // 购买按钮类型 0, 1, 2, 3
        },
        del_market: 0,
        sold_out_goods: 0, // 是否显示售罄商品
        goods_sort: 'add_time', // 小程序商品默认排序规则 add_time; goods_sale_num; comment_num; pv;
        goods_record: 0, // 是否显示购买记录
        custom_service: 0, // 客服入口开关-商品详情页是否展示
        return_service: 1, // 客服入口开关-退/换货中心是否展示
        default_sort: 0, // 商品默认平台分类id
        share_config: {
          share_action: 0,
          share_doc: '',
          share_img_action: 1,
          share_img: 'image/admin/btn_add.png'
        },
        bind_mobile: 0, // 是否强制用户在购买、预约以及申请成为分销员时绑定手机号
        geographic_location: 0 // 地理位置授权申请
      },
      catIds: [],
      tuneUp: false
    }
  },
  computed: {
    del_market_radio: {
      get: function () {
        if (this.info.del_market === 0) {
          return 0
        } else if (this.info.del_market > 0) {
          return 1
        }
      },
      set: function (val) {
        console.log('val:', val)
        if (val === 0) {
          this.info.del_market = 0
        } else {
          this.info.del_market = 1
        }
      }
    }
  },
  created () {
    this.langDefault()
    this.initCombo()
    this.initData()
  },
  methods: {
    initCombo () {
      let that = this
      getCateList().then(res => {
        if (res.error === 0) {
          that.catIds = that.formatCateDatas(res.content)
        }
      })
    },
    // 将平台分类树格式化成数组
    formatCateDatas (datas) {
      const treeDatas = this.disposeGoodsSortAndCatData(datas, 'catId')
      return treeDatas
    },
    initData () {
      getCommonInfo().then(res => {
        if (res.error === 0) {
          this.info = Object.assign({}, res.content)
        }
      })
    },
    selectShareImgHandle () {
      this.tuneUp = !this.tuneUp
    },
    shareImgSelectHandle (img) {
      console.log('img: ', img)
      this.$set(this.info.share_config, 'share_img', img.imgPath)
    },
    saveCommonInfoHandle () {
      const that = this
      let params = Object.assign({}, this.info)
      updateCommonInfo(params).then(res => {
        if (res.error === 0) {
          console.log(res.content)
          that.$message.success(that.$t('storeCommonSettings.updated'))
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.store_common_setting_page {
  a {
    text-decoration: none;
  }
  .content {
    min-height: 760px;
    background: #fff;
    padding: 0 20px;
    margin-bottom: 50px;
  }
  .text-prompt {
    width: 100%;
    height: 50px;
    line-height: 50px;
    padding-left: 25px;
    background: #eef1f6;
    font-size: 14px;
    .blue_border {
      border-left: 2px solid #5a8bff;
      height: 14px;
      width: 8px;
      display: inline-block;
      margin-bottom: -1px;
    }
  }
  .text-set {
    line-height: 60px;
    color: #666;
    padding: 0 32px;
    font-size: 13px;
  }
  .con4-hide {
    margin: 22px 15px 15px 0px;
    color: #333;
    font-size: 14px;
    line-height: 1;
  }
  .btn_cart {
    height: 55px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }
  .new_class {
    color: rgb(91, 158, 164);
    vertical-align: middle;
    font-size: 32px;
  }
  .goods_sort_radio {
    display: block;
    margin-bottom: 15px;
  }
  .share_div {
    line-height: 30px;
  }
  .tips {
    color: #999;
    margin-left: 10px;
  }
  .custom_share {
    margin-left: 22px;
    & > label {
      width: 60px;
    }
  }
  .share_img_wrap {
    display: flex;
  }
  .bold {
    font-weight: bold;
  }
  .footer {
    position: fixed;
    z-index: 100;
    bottom: 0;
    display: flex;
    justify-content: center;
    width: calc(100% - 186px);
    padding: 10px;
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    .footer-btn {
      width: 105px;
      margin: 0 10px;
    }
  }
}
</style>
