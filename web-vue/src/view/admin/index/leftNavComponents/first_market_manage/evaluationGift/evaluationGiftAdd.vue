<template>
  <div class="container">
    <div class="center">
      <div class="page-left">
        <div class="left-wrap">
          <div
            class="left-title"
            :style="'background:url('+ $imageHost +'/image/admin/shop_beautify/phone_tops.png) no-repeat;'"
          ></div>
          <div class="left-content">
            <el-image :src="$imageHost + '/image/admin/comment_gift_bg.jpg'"></el-image>
            <div class="custom-popup-wrap">
              <div class="custom-popup">
                <img
                  class="custom-top"
                  :src="$imageHost + '/image/wxapp/split_btn1.png'"
                  alt=""
                >
                <i class="custom-xian"></i>
                <div class="img-content">
                  <div
                    class="no-custom-img"
                    v-if="!form.customizeImgPath"
                  >
                    <el-image
                      :src="$imageHost+ '/image/admin/no_custom_img.png'"
                      style="width: 80px;height: 80px;"
                    ></el-image>
                    <span>{{$t('openScreenAdd.eventPictures')}}</span>
                  </div>
                  <el-image
                    v-else
                    :src="$imageHost + '/' +form.customizeImgPath"
                    style="width:100%;height:100%;"
                    fit="fit"
                  ></el-image>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="page-right">
        <div class="right-top">
          <header>活动信息</header>
          <el-form
            ref="activityInfoForm"
            size="small"
            label-width="110px"
            :mode="form"
            :rules="rules"
          >
            <el-form-item label="活动名称：">
              <el-input placeholder="最多支持10个字"></el-input>
            </el-form-item>
            <el-form-item label="活动有效期：">
              <div>
                <el-radio>固定时间</el-radio>
                <el-date-picker
                  type="datetimerange"
                  start-placeholder="生效时间"
                  range-separator="至"
                  end-placeholder="过期时间"
                  style="width: 320px;"
                ></el-date-picker>
              </div>
              <div>
                <el-radio>永久有效</el-radio>
              </div>
            </el-form-item>
            <el-form-item label="优先级：">
              <el-input-number controls-position="right"></el-input-number>
            </el-form-item>
            <el-form-item label="触发条件：">
              <div>
                <label>商品条件</label>
                <el-radio-group>
                  <el-radio>全部商品</el-radio>
                  <el-radio>指定商品</el-radio>
                  <el-radio>实际评价数量较少商品</el-radio>
                </el-radio-group>
              </div>
              <div>
                <label>评价条件</label>
                <el-radio-group>
                  <el-radio>评价即送</el-radio>
                  <el-radio>自定义</el-radio>
                </el-radio-group>
              </div>
            </el-form-item>
          </el-form>
        </div>
        <div class="right-bottom">
          <header>评价奖励</header>
          <el-form
            ref="evaluationAwardForm"
            size="small"
            label-width="110px"
            :mode="form"
            :rules="rules"
          >
            <el-form-item label="评价奖励：">
              <el-radio-group>
                <el-radio>积分</el-radio>
                <el-radio>优惠券</el-radio>
                <el-radio>余额</el-radio>
                <el-radio>幸运大抽奖</el-radio>
                <el-radio>自定义</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="积分：">
              <el-input-number controls-position="right"></el-input-number>
            </el-form-item>
            <el-form-item label="优惠券：">
              <el-select v-model="form.selectCoupon">
                <el-option
                  label="未选择"
                  value=""
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="余额：">
              <el-input placeholder="请输入金额"></el-input>
            </el-form-item>
            <el-form-item label="幸运大抽奖：">
              <selectPayRewardAct></selectPayRewardAct>
            </el-form-item>
            <el-form-item label="自定义：">
              <div
                class="uploaded-add"
                v-if="form.customizeImgPath"
                @click="selectImgHandle"
                @mouseenter="hoverImgHandle"
                @mouseleave="hoverImgHandle"
              >
                <el-image
                  :src="$imageHost+ '/' + form.customizeImgPath"
                  class="uploaded-img"
                ></el-image>
                <p
                  class="uploaded-tip"
                  :hidden="!uploadHover"
                >{{$t('openScreenAdd.reselect')}}</p>
              </div>
              <div
                v-if="!form.customizeImgPath"
                class="upload-add"
                :style="'background-image:url('+$imageHost+'/image/admin/btn_add.png);'"
                @click="selectImgHandle"
              >
              </div>
              <span class="upload-tip">{{$t('openScreenAdd.recommendedSize')}}560px * 700px</span>
            </el-form-item>
            <el-form-item label="奖品份数：">
              <el-input-number controls-position="right"></el-input-number>
            </el-form-item>
            <el-form-item label="赠送限制：">
              <el-checkbox>同一商品仅首次评价送礼</el-checkbox>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        style="width: 90px;"
      >保存</el-button>
    </div>

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
  </div>
</template>

<script>
export default {
  components: {
    selectPayRewardAct: () => import('@/components/admin/marketManage/selectPayRewardAct'),
    ImageDalog: () => import('@/components/admin/imageDalog'),
    selectLinks: () => import('@/components/admin/selectLinks')
  },
  data () {
    return {
      form: {},
      rules: {},
      imageDalogVisible: false, // 选择图片
      uploadHover: false,
      selectLinksVisible: false // 选择链接
    }
  },
  methods: {
    selectImgHandle () {
      this.imageDalogVisible = !this.imageDalogVisible
    },
    hoverImgHandle () {
      this.uploadHover = !this.uploadHover
    },
    selectLinkHandle (path) {
    },
    handleSelectImg (img) {
      console.log(img)
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  .center {
    display: flex;
    min-height: 500px;
    margin: 0 auto 60px;
    padding-top: 20px;
    max-width: 900px;
    min-width: 700px;
    .page-left {
      width: 323px;
      .left-wrap {
        border: 1px solid #ccc;
        background: #eee;
        position: relative;
        .left-title {
          height: 55px;
          color: #fff;
          text-align: center;
        }
        .left-content {
          height: 570px;
          .custom-popup-wrap {
            position: absolute;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
          }
          .custom-popup {
            position: absolute !important;
            left: 50%;
            transform: translateX(-50%);
            width: 230px;
            top: 150px;
            transition: 0.6s ease-in-out left;
            .custom-top {
              position: absolute;
              width: 20px;
              height: auto;
              right: 24px;
              line-height: 1;
            }
            .custom-xian {
              position: absolute;
              width: 1px;
              background-color: #fff;
              height: 20px;
              top: 20px;
              right: 34px;
            }
            .img-content {
              width: 100%;
              background: #fff;
              height: 270px;
              margin-top: 40px;
              border-radius: 5px;
              display: flex;
              justify-content: center;
              align-items: center;
              overflow: hidden;
              .no-custom-img {
                text-align: center;
                span {
                  color: #999;
                  display: block;
                  margin-top: 15px;
                }
              }
            }
          }
        }
      }
    }
    .page-right {
      flex: 1;
      margin-left: 20px;
      header {
        height: 40px;
        line-height: 40px;
        border-bottom: 1px solid #eee;
        margin-bottom: 10px;
      }
      .el-input {
        width: 170px;
      }
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
      .span-tip {
        color: #999;
      }
    }
  }
  .footer {
    position: fixed;
    right: 0;
    bottom: 0;
    width: calc(100% - 150px);
    padding: 10px 0;
    text-align: center;
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    overflow: hidden;
  }
}
</style>
