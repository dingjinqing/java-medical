<template>
  <!-- 店铺通用设置 -->
  <div class="container store_common_setting_page">
    <div class="content">
      <ul>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>销量展示设置</span>
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
              v-show="value===0"
            >已关闭</span>
            <span
              class="con4-hide"
              v-show="value===1"
            >已开启</span>
            <span class="tip">开关开启，商品详情页会展示商品的销量</span>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>购买按钮展示设置</span>
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
                v-show="value===0"
              >已关闭</span>
              <span
                class="con4-hide"
                v-show="value===1"
              >已开启</span>
              <span class="tip">开关开启，店铺商品页以及推荐商品列表中会显示购买按钮</span>
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
                  >马上抢</el-button>
                </el-radio>
                <el-radio :label="3">
                  <el-button
                    size="small"
                    round
                  >购买</el-button>
                </el-radio>
              </el-radio-group>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>划线价(市场价/销量/评论)展示设置</span>
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
                v-show="value===0"
              >已关闭</span>
              <span
                class="con4-hide"
                v-show="value===1"
              >已开启</span>
              <span class="tip">开关开启，店铺商品页以及推荐商品列表中会显示商品划线价（市场价/销量/评论）。注：为保证页面展示效果，若开启购买按钮，则小程序前端不会显示划线价</span>
            </div>
            <div class="bottom">
              <el-radio-group v-model="info.del_market">
                <el-radio :label="1">
                  市场价
                </el-radio>
                <el-radio :label="2">
                  销量
                </el-radio>
                <el-radio :label="3">
                  评价数
                </el-radio>
              </el-radio-group>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>售罄商品展示设置</span>
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
              v-show="value===0"
            >已关闭</span>
            <span
              class="con4-hide"
              v-show="value===1"
            >已开启</span>
            <span class="tip">开关开启，前端装修商品模块、店铺商品页、推荐商品列表、活动商品列表会展示已售罄商品</span>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>商品默认排序</span>
          </div>
          <div class="text-set clearfix">
            <p>请选择小程序端"店铺商品页"默认排序规则：</p>
            <el-radio-group
              v-model="info.goods_sort"
              text-color="#333333"
            >
              <el-radio
                class="goods_sort_radio"
                label="add_time"
              >
                按照商品上新时间倒序排列
                <span class="tips">最新添加的商品将排在商品列表最上方</span>
              </el-radio>
              <el-radio
                class="goods_sort_radio"
                label="goods_sale_num"
              >
                按照商品销量倒序排列
                <span class="tips">销量最高的商品将排在商品列表最上方</span>
              </el-radio>
              <el-radio
                class="goods_sort_radio"
                label="comment_num"
              >
                按照商品评价数量倒序排列
                <span class="tips">评价数最多的商品将排在商品列表最上方</span>
              </el-radio>
              <el-radio
                class="goods_sort_radio"
                label="pv"
              >
                按照商品访问次数倒序排列
                <span class="tips">7天内访问次数最多的商品将排在商品列表最上方</span>
              </el-radio>
            </el-radio-group>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>购买记录展示设置</span>
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
              v-show="value===0"
            >已关闭</span>
            <span
              class="con4-hide"
              v-show="value===1"
            >已开启</span>
            <span class="tip">开关开启，会在商品详情页滚动展示最近的5条购买记录 </span>
            <el-tooltip
              placement="right"
              effect="light"
            >
              <span style="color: #5a8bff;">查看示例</span>
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
            <span>客服配置</span>
          </div>
          <div class="text-set">
            <span>客服展示位置：
              <el-checkbox v-model="info.custom_service">商品详情页</el-checkbox>
              <el-checkbox v-model="info.return_service">退/换货中心 勾选后,客服入口将会展示在小程序端对应位置</el-checkbox>
              <a
                href="http://bbs.weipubao.cn/forum.php?mod=viewthread&amp;tid=685&amp;fromuid=1"
                target="_blank"
                class="to_bbc"
                style="color: #5a8bff;"
              >功能介绍</a>
            </span>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>商品默认平台分类</span>
          </div>
          <div class="text-set clearfix">
            <span>商品默认平台分类： </span>
            <el-select
              v-model="info.default_sort"
              size="small"
              filterable
            >
              <el-option
                :label="$t('storeGoodsList.goodsSort')"
                :value="null"
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
            <span>店铺分享</span>
          </div>
          <div class="text-set clearfix">
            <div class="share_div">
              <el-radio
                v-model="info.share_config.share_action"
                :label="0"
              >默认样式</el-radio>
              <el-tooltip
                placement="right"
                effect="light"
              >
                <span style="color: #5a8bff;">查看示例</span>
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
              >自定义样式</el-radio>
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
                  <label>分享图：</label>
                  <div>
                    <el-radio-group
                      v-model="info.share_config.share_img_action"
                      style="width: 100px; line-height:30px;margin-top:5px;"
                    >
                      <el-radio
                        :label="1"
                        style="color: #333;"
                      >店铺首页截图</el-radio>
                      <el-radio
                        :label="2"
                        style="color: #333;"
                      >自定义图片</el-radio>
                    </el-radio-group>
                    <div class="img_wrap">
                      <el-image
                        :src="$imageHost + '/' + info.share_config.share_img"
                        @click="selectShareImgHandle"
                        style="width: 70px; height:70px;"
                      ></el-image>
                      <span class="tips">建议尺寸：800*800</span>
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
            <span>手机号授权配置</span>
          </div>
          <div class="text-set clearfix">
            <div class="top">
              <span>手机号授权</span>
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
                v-show="value===0"
              >已关闭</span>
              <span
                class="con4-hide"
                v-show="value===1"
              >已开启</span>
              <span class="tip">开关开启，用户在购买、预约以及申请成为分销员时需绑定手机号 </span>
            </div>
            <div class="bottom">
              <span>地理位置授权</span>
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
                v-show="value===0"
              >已关闭</span>
              <span
                class="con4-hide"
                v-show="value===1"
              >已开启</span>
              <span class="tip">开关开启，用户进入小程序时会弹出地理位置授权申请。用户在小程序中触发需要
                授权地理位置的操作时，默认会调起授权提示，不受此开关控制 </span>
            </div>
          </div>
        </li>
        <li>
          <div class="text-prompt">
            <span class="blue_border"></span>
            <span>短信设置</span>
          </div>
          <div class="text-set">
            <p>
              当前充值账号是： <span class="bold">微铺宝电商运营</span>， 剩余金额： <span class="bold">￥0.0000</span>， 剩余短信条数： <span class="bold">0 </span>
              <a
                target="_blank"
                href="http://101.200.202.174/sms/api/alipay/index.php?sms_account=微铺宝电商运营"
                style="color: #5a8bff; cursor: pointer;"
              >前往充值</a>
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
      >保存</el-button>
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
          that.$message.success('更新成功')
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
