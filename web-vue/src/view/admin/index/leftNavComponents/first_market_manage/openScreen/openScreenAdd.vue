<template>
  <div class="container">
    <div class="content">
      <div class="open-screen-add-page">
        <div class="page-left">
          <div class="left-wrap">
            <div
              class="left-title"
              :style="'background:url('+ $imageHost +'/image/admin/shop_beautify/phone_tops.png) no-repeat;'"
            ></div>
            <div class="left-content">
              <el-image
                v-if="form.type === 0"
                :src="images.show_score"
              ></el-image>
              <el-image
                v-else-if="form.type === 1"
                :src="images.show_coupon"
              ></el-image>
              <el-image
                v-else-if="form.type === 2"
                :src="images.show_lottery"
              ></el-image>
              <el-image
                v-else-if="form.type === 3"
                :src="images.show_yue"
              ></el-image>
              <el-image
                v-else
                :src="images.show_custom"
              ></el-image>
            </div>
          </div>
        </div>
        <div class="page-right">
          <div class="right-top">
            <header>活动配置</header>
            <el-form
              size="small"
              label-width="110px"
              :rules="rules"
            >
              <el-form-item
                label="活动名称："
                prop="name"
              >
                <el-input
                  v-model="form.name"
                  style="width:170px;"
                  size="small"
                  placeholder="最多支持10个字"
                  maxlength="10"
                  show-word-limit
                ></el-input>
              </el-form-item>
              <el-form-item label="活动宣传语：">
                <el-input
                  style="width:170px;"
                  size="small"
                  placeholder="最多支持20个字"
                  maxlength="20"
                  show-word-limit
                ></el-input>
                <p class="tips">展示在前端页面，用于优惠活动通知</p>
              </el-form-item>
              <el-form-item label="活动有效期：">
                <div>
                  <el-radio>固定时间</el-radio>
                  <el-date-picker
                    type="daterange"
                    style="width:240px;"
                    range-separator="至"
                    start-placeholder="生效时间"
                    end-placeholder="过期时间"
                  ></el-date-picker>
                </div>
                <div>
                  <el-radio>永久有效</el-radio>
                </div>
              </el-form-item>
              <el-form-item
                label="优先级："
                required
              >
                <el-input
                  v-model.number="form.priority"
                  size="small"
                  style="width:170px;"
                  min="0"
                ></el-input>
                <p class="tips">用于区分不同开屏有礼活动的优先级，请填写正整数，数值越大优先级越高</p>
              </el-form-item>
              <el-form-item
                label="触发条件："
                required
              >
                <el-radio-group v-model="form.activityAction">
                  <el-radio>初次访问店铺的用户</el-radio>
                  <el-radio>未在店铺内支付的用户</el-radio>
                  <el-radio>全部用户</el-radio>
                </el-radio-group>
                <p class="tips">只针对新用户的活动只对初次进入小程序的用户可见，通常在拉新活动中使用较为常见</p>
              </el-form-item>
            </el-form>
          </div>
          <div class="right-bottom">
            <header>开屏奖励</header>
            <el-form
              label-width="110px"
              size="small"
            >
              <el-form-item
                label="支付奖励："
                required
              >
                <el-radio-group>
                  <div class="radio-layout">
                    <el-radio>积分</el-radio>
                    <el-radio>优惠券</el-radio>
                    <el-radio>幸运大抽奖</el-radio>
                  </div>
                  <div class="radio-layout">
                    <el-radio>余额</el-radio>
                    <el-radio>分裂优惠券</el-radio>
                    <el-radio>自定义</el-radio>
                  </div>
                </el-radio-group>
              </el-form-item>
              <el-form-item
                label="积分："
                required
              >
                <el-input
                  placeholder="请输入积分"
                  style="width:170px;"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="优惠券："
                required
              >
                <div
                  class="coupon-added"
                  v-for="(item,index) in couponSelected"
                  :key="item.id"
                >
                  <h3 class="ca-value">￥<span>{{item.denomination}}</span></h3>
                  <!-- 是否限制使用方式 -->
                  <p
                    v-if="item.useConsumeRestrict == 0"
                    class="ca-condition"
                  >无门槛</p>
                  <p
                    v-else
                    class="ca-condition"
                  >满{{item.leastConsume}}使用</p>
                  <!-- 是否限制库存 -->
                  <p
                    class="ca-stock-limit"
                    v-if="item.limitSurplusFlag == 0"
                  >剩余{{item.surplus}}张</p>
                  <p
                    v-else
                    class="ca-stock-limit"
                  >库存不限制</p>
                  <div
                    class="ca-bottom"
                    :style="'background-image:url(' + $imageHost + '/image/admin/coupon_border.png'"
                  ></div>
                  <img
                    @click="deleteCoupon(index)"
                    class="ca-close"
                    :src="$imageHost + '/image/admin/sign_del.png'"
                  >
                </div>
                <div
                  class="coupon-add"
                  @click="addCoupon"
                >
                  <p
                    class="add-icon"
                    :style="'background-image:url('+$imageHost+'/image/admin/shop_beautify/add_decorete.png);'"
                  ></p>
                  <p class="add-tip">添加优惠券</p>
                </div>
              </el-form-item>
              <el-form-item
                label="幸运大抽奖："
                required
              >
                <selectPayRewardAct v-model="form.activityId"></selectPayRewardAct>
              </el-form-item>
              <el-form-item
                label="余额："
                required
              >
                <el-input
                  style="width:170px;"
                  placeholder="请输入余额"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="分裂优惠券："
                required
              >
                <div
                  class="coupon-added"
                  v-for="item in disCouponSelected"
                  :key="item.id"
                >
                  <h3 class="ca-value">￥<span>{{item.denomination}}</span></h3>
                  <!-- 是否限制使用方式 -->
                  <p
                    v-if="item.useConsumeRestrict == 0"
                    class="ca-condition"
                  >无门槛</p>
                  <p
                    v-else
                    class="ca-condition"
                  >满{{item.leastConsume}}使用</p>
                  <!-- 是否限制库存 -->
                  <p
                    class="ca-stock-limit"
                    v-if="item.limitSurplusFlag == 0"
                  >剩余{{item.surplus}}张</p>
                  <p
                    v-else
                    class="ca-stock-limit"
                  >库存不限制</p>
                  <div
                    class="ca-bottom"
                    :style="'background-image:url(' + $imageHost + '/image/admin/coupon_border.png'"
                  ></div>
                  <img
                    @click="deleteDisCoupon()"
                    class="ca-close"
                    :src="$imageHost + '/image/admin/sign_del.png'"
                  >
                </div>
                <div
                  v-if="!disCouponSelected || disCouponSelected.length === 0"
                  class="coupon-add"
                  @click="addDisCoupon"
                >
                  <p
                    class="add-icon"
                    :style="'background-image:url('+$imageHost+'/image/admin/shop_beautify/add_decorete.png);'"
                  ></p>
                  <p class="add-tip">添加分裂优惠券</p>
                </div>
              </el-form-item>
              <el-form-item
                label="活动图片："
                required
              >
                <div
                  class="uploaded-add"
                  v-if="selectImg"
                  @click="selectImgHandle"
                  @mouseenter="hoverImgHandle"
                  @mouseleave="hoverImgHandle"
                >
                  <el-image
                    :src="selectImg.imgUrl"
                    class="uploaded-img"
                  ></el-image>
                  <p
                    class="uploaded-tip"
                    :hidden="!uploadHover"
                  >重新选择</p>
                </div>
                <div
                  v-if="!selectImg"
                  class="upload-add"
                  :style="'background-image:url('+$imageHost+'/image/admin/btn_add.png);'"
                  @click="selectImgHandle"
                >
                </div>
                <span class="upload-tip">建议尺寸：560px * 700px</span>
              </el-form-item>
              <el-form-item
                label="设置链接："
                required
              >
                <el-input
                  v-model="selectLink"
                  size="small"
                  style="width:170px;"
                ></el-input>
                <el-button
                  size="small"
                  @click="selectLinksVisible = !selectLinksVisible"
                >选择链接</el-button>
              </el-form-item>
              <el-form-item label="奖品份数：">
                <el-input-number
                  v-model="form.activeNum"
                  size="small"
                ></el-input-number>
                <span>份</span>
                <span class="span-tip">填写0表示不限制</span>
                <p class="tips">发放人数达到奖品份数，后续用户无法再获取支付奖励</p>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
    <footer class="footer">
      <el-button
        size="small"
        type="primary"
        @click="saveOpenScreenHandle"
      >保存</el-button>
    </footer>

    <!-- 选择图片 -->
    <ImageDalog
      pageIndex="pictureSpace"
      :tuneUp="imageDalogVisible"
      :imageSize="[560, 700]"
      @handleSelectImg="handleSelectImg"
    ></ImageDalog>

    <!-- 选择链接 -->
    <selectLinks
      :tuneUpSelectLink="selectLinksVisible"
      @selectLinkPath="selectLinkHandle"
    ></selectLinks>

    <!-- 选择优惠券 -->
    <addCouponDialog
      :tuneUpCoupon="addCouponVisible"
      :couponBack="couponAdded"
      @handleToCheck="addCouponHandle"
    ></addCouponDialog>

    <!-- 选择分裂优惠券 -->
    <addCouponDialog
      :tuneUpCoupon="addDisCouponVisible"
      :couponBack="disCouponAdded"
      @handleToCheck="addDisCouponHandle"
    ></addCouponDialog>
  </div>
</template>

<script>
import { addOpenScreen } from '@/api/admin/marketManage/openScreen.js'
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    selectLinks: () => import('@/components/admin/selectLinks'),
    selectPayRewardAct: () => import('@/components/admin/marketManage/selectPayRewardAct'),
    addCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  data () {
    return {
      images: {
        show_score: this.$imageHost + '/image/admin/activity_score.jpg', // 积分
        show_coupon: this.$imageHost + '/image/admin/activity_coupon.jpg', // 优惠券，分裂优惠券
        show_lottery: this.$imageHost + '/image/admin/activity_lottery.jpg', // 幸运大抽奖
        show_yue: this.$imageHost + '/image/admin/activity_yue.jpg', // 余额
        show_custom: this.$imageHost + '/image/admin/activity_custom.jpg' // 自定义
      },
      form: {
        type: 0,
        name: '',
        startDate: '',
        endDate: '',
        action: '',
        activityAction: 1, // 触发条件
        activeNum: 0,
        priority: '',

        customizeImgUrl: '', // 自定义图片
        customizePagePath: '', // 自定义链接

        activityId: '', // 抽奖活动id

        title: '', // 活动宣传语
        bgAction: '', // 背景图
        couponId: [] // 优惠券id
      },
      rules: {
        name: [{ required: true, message: '请输入活动名称', trigger: 'blur' }]
      },

      addCouponVisible: false, // 优惠券
      couponSelected: [],
      addDisCouponVisible: false, // 分裂优惠券
      disCouponSelected: [],
      imageDalogVisible: false, // 选择图片
      selectImg: null,
      uploadHover: false,
      selectLinksVisible: false, // 选择链接
      selectLink: ''
    }
  },
  computed: {
    couponAdded: function () {
      return this.couponSelected.map(item => item.id)
    },
    disCouponAdded: function () {
      if (this.disCouponSelected && this.disCouponSelected.length) {
        return [this.disCouponSelected[0].id]
      } else {
        return []
      }
    }
  },
  mounted () {
    console.log('$imageHost:', this.$imageHost)
  },
  methods: {
    saveOpenScreenHandle () {
      let params = {}
      addOpenScreen(params).then(res => {
        if (res.error === 0) {
          console.log(res.content)
        }
      })
    },
    handleSelectImg (img) {
      this.selectImg = img
    },
    selectLinkHandle (path) {
      this.selectLink = path
    },
    addCoupon () {
      this.addCouponVisible = !this.addCouponVisible
    },
    addCouponHandle (data) {
      this.couponSelected = data
    },
    addDisCoupon () {
      this.addDisCouponVisible = !this.addDisCouponVisible
    },
    addDisCouponHandle (data) {
      this.disCouponSelected = data
    },
    deleteCoupon (index) {
      this.couponSelected.splice(index, 1)
    },
    deleteDisCoupon (index) {
      this.disCouponSelected = []
    },
    selectImgHandle () {
      this.imageDalogVisible = !this.imageDalogVisible
    },
    hoverImgHandle () {
      this.uploadHover = !this.uploadHover
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  .content {
    padding: 40px 20px;
    margin-bottom: 50px;
    background: #fff;
    .open-screen-add-page {
      max-width: 900px;
      min-width: 700px;
      margin: 0 auto;
      display: flex;
      .page-left {
        width: 323px;
        .left-wrap {
          border: 1px solid #ccc;
          background: #eee;
          .left-title {
            height: 55px;
            color: white;
            text-align: center;
          }
          .left-content {
            height: 570px;
          }
        }
      }
      .page-right {
        flex: 1;
        margin-left: 20px;
        .right-top {
          border: 1px solid #e5e5e5;
          background: #f8f8f8;
          padding-left: 10px;
          border-radius: 4px;
          margin-bottom: 10px;
        }
        .right-bottom {
          margin-top: 10px;
          border: 1px solid #e5e5e5;
          background: #f8f8f8;
          padding-left: 10px;
          border-radius: 4px;
          margin-bottom: 10px;
          .radio-layout {
            display: flex;
            justify-content: space-around;
          }
          .coupon-added {
            width: 100px;
            margin-right: 20px;
            background: white;
            margin-bottom: 10px;
            position: relative;
            float: left;
            border: 1px solid #fbb;
            border-radius: 10px;
            text-align: center;
            color: #f66;
            font-size: 12px;
            line-height: 1;
            overflow: inherit;
            .ca-value {
              margin-top: 10px;
              font-size: 14px;
              span {
                font-size: 20px;
                font-weight: bold;
              }
            }
            .ca-condition {
              height: 20px;
            }
            .ca-stock-limit {
              height: 20px;
              color: #fbb;
            }
            .ca-bottom {
              background-color: #f66;
              background-repeat: repeat-x;
              background-position: top;
              background-size: 12px;
              height: 24px;
              line-height: 30px;
              color: #fff;
            }
            .ca-close {
              position: absolute;
              right: -8px;
              top: -8px;
            }
          }
          .coupon-add {
            background: #fff;
            border: 1px solid #e4e4e4;
            text-align: center;
            padding: 13px 0;
            cursor: pointer;
            float: left;
            width: 100px;
            height: 90px;
          }
          .add-icon {
            width: 20px !important;
            height: 20px !important;
            margin: 10px auto 8px;
            background-repeat: no-repeat;
            background-position: center;
            background-size: 100% 100%;
          }
          .add-tip {
            color: #999;
            font-size: 12px;
            text-align: center;
            line-height: 1;
          }
          .upload-add {
            width: 70px;
            height: 70px;
            box-shadow: 0 0 0 #fff;
            padding-top: 40px;
            padding-left: 8px;
            color: #9a9a9a;
            border: none;
            margin-right: 10px;
            float: left;
            cursor: pointer;
          }
          .upload-tip {
            margin-top: 18px;
            margin-left: 5px;
            display: inline-block;
            font-size: 14px;
            color: #999;
          }
          .uploaded-tip {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            line-height: 18px;
            background: rgba(0, 0, 0, 0.5);
            font-size: 12px;
            text-align: center;
            color: #fff;
          }
          .uploaded-add {
            width: 70px;
            height: 70px;
            position: relative;
            cursor: pointer;
            float: left;
          }
          .uploaded-img {
            width: 100%;
            height: 100%;
          }
        }
        header {
          height: 40px;
          line-height: 40px;
          border-bottom: 1px solid #eee;
        }
      }
    }
    .tips {
      margin-top: 10px;
      line-height: 1;
      font-size: 12px;
      color: #999;
    }
  }
  .footer {
    position: fixed;
    bottom: 0;
    right: 0;
    width: calc(100% - 150px);
    padding: 10px 0;
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    z-index: 2;
    text-align: center;
  }
}
</style>
