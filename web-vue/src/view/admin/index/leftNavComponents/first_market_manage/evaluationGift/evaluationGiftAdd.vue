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
            <div
              class="custom-popup-wrap"
              v-show="form.awardType === 5"
            >
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
                    v-if="!form.awardImg"
                  >
                    <el-image
                      :src="$imageHost+ '/image/admin/no_custom_img.png'"
                      style="width: 80px;height: 80px;"
                    ></el-image>
                    <span>{{$t('openScreenAdd.eventPictures')}}</span>
                  </div>
                  <el-image
                    v-else
                    :src="$imageHost + '/' +form.awardImg"
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
        <el-form
          ref="activityInfoForm"
          size="small"
          label-width="110px"
          :model="form"
          :rules="rules"
        >
          <div class="right-top">
            <header>活动信息</header>

            <el-form-item
              label="活动名称："
              prop="name"
            >
              <el-input
                v-model="form.name"
                maxlength="10"
                show-word-limit
                style="width:200px;"
                placeholder="最多支持10个字"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="活动有效期："
              prop="isForever"
            >
              <div>
                <el-radio
                  v-model="form.isForever"
                  :label="0"
                >固定时间</el-radio>
                <el-date-picker
                  v-show="form.isForever === 0"
                  v-model="period"
                  type="datetimerange"
                  start-placeholder="生效时间"
                  range-separator="至"
                  end-placeholder="过期时间"
                  style="width: 320px;"
                  format="yyyy-MM-dd hh:mm"
                ></el-date-picker>
              </div>
              <div>
                <el-radio
                  v-model="form.isForever"
                  :label="1"
                >永久有效</el-radio>
              </div>
            </el-form-item>
            <el-form-item
              label="优先级："
              prop="level"
            >
              <el-input-number
                v-model.number="form.level"
                controls-position="right"
                :min="0"
                :max="100"
              ></el-input-number>
              <p class="tips">用于区分不同评价有礼活动的优先级，请填写正整数，数值越大优先级越高</p>
            </el-form-item>
            <el-form-item
              label="触发条件："
              prop="goodsType"
            >
              <div>
                <label style="font-weight:bold;">商品条件</label>
                <el-radio-group
                  v-model="form.goodsType"
                  class="goods-type-radio-group"
                  @change="goodsTypeChange"
                >
                  <el-radio :label="1">全部商品</el-radio>
                  <el-radio :label="2">指定商品</el-radio>
                  <el-radio :label="3">实际评价数量较少商品</el-radio>
                </el-radio-group>
              </div>
              <div v-if="form.goodsType === 2">
                <el-button @click="chooseGoodsDialog">+ 选择商品</el-button>
                <el-button
                  v-if="chooseGoods && chooseGoods.length > 0"
                  type="text"
                  @click="chooseGoodsDialog"
                >已选择商品：{{chooseGoods.length}}件商品</el-button>
              </div>
              <div v-else-if="form.goodsType === 3">
                实际评论数量少于<el-input-number
                  v-model="form.commentNum"
                  style="width:90px;"
                  controls-position="right"
                  :min="0"
                ></el-input-number>的商品
              </div>
              <div>
                <label style="font-weight:bold;">评价条件</label>
                <el-radio-group
                  v-model="form.commentType"
                  class="goods-type-radio-group"
                >
                  <el-radio :label="1">评价即送</el-radio>
                  <el-radio :label="2">自定义</el-radio>
                </el-radio-group>
              </div>
              <div v-if="form.commentType === 2">
                <div class="flex-block">
                  <el-checkbox
                    v-model="form.hasPicNum"
                    :true-label="1"
                    :false-label="0"
                  >晒图</el-checkbox>
                  <el-checkbox
                    v-model="form.hasFiveStars"
                    :true-label="1"
                    :false-label="0"
                  >五星好评</el-checkbox>
                  <div>
                    <el-checkbox
                      v-model="form.hasCommentWords"
                      :true-label="1"
                      :false-label="0"
                      @change="hasCommentWordsChange"
                    >心得超过</el-checkbox>
                    <span>
                      <el-input-number
                        v-model="form.commentWords"
                        style="width:90px;"
                        controls-position="right"
                        :min="0"
                      ></el-input-number>字
                    </span>
                  </div>
                </div>
                <p class="tips">以上条件为"且"的关系</p>
              </div>
            </el-form-item>
          </div>
          <div class="right-bottom">
            <header>评价奖励</header>

            <el-form-item
              label="评价奖励："
              prop="awardType"
              class="award-wrap"
            >
              <el-radio-group
                v-model="form.awardType"
                class="award-type-radio-group"
              >
                <el-radio :label="1">积分</el-radio>
                <el-radio :label="2">优惠券</el-radio>
                <el-radio :label="3">余额</el-radio>
                <el-radio :label="4">幸运大抽奖</el-radio>
                <el-radio :label="5">自定义</el-radio>
              </el-radio-group>
              <el-form-item
                label="积分："
                v-if="form.awardType === 1"
                label-width="110px"
                prop="score"
              >
                <el-input-number
                  v-model="form.score"
                  controls-position="right"
                  :min="0"
                ></el-input-number>
              </el-form-item>
              <el-form-item
                label="优惠券："
                v-else-if="form.awardType === 2"
                label-width="110px"
                prop="activityId"
              >
                <selectCouponAct v-model="form.activityId"></selectCouponAct>
                <p class="tips">优惠券可用库存0份</p>
              </el-form-item>
              <el-form-item
                label="余额："
                v-else-if="form.awardType === 3"
                label-width="110px"
                prop="account"
              >
                <el-input-number
                  v-model.number="form.account"
                  placeholder="请输入金额"
                  controls-position="right"
                  :min="0"
                ></el-input-number>
              </el-form-item>
              <el-form-item
                label="幸运大抽奖："
                v-else-if="form.awardType === 4"
                label-width="110px"
                prop="activityId"
              >
                <selectPayRewardAct v-model="form.activityId"></selectPayRewardAct>
              </el-form-item>
              <div v-else-if="form.awardType === 5">
                <el-form-item
                  label="活动图片："
                  label-width="110px"
                  prop="awardImg"
                >
                  <div
                    class="uploaded-add"
                    v-if="form.awardImg"
                    @click="selectImgHandle"
                    @mouseenter="hoverImgHandle"
                    @mouseleave="hoverImgHandle"
                  >
                    <el-image
                      :src="$imageHost+ '/' + form.awardImg"
                      class="uploaded-img"
                    ></el-image>
                    <p
                      class="uploaded-tip"
                      :hidden="!uploadHover"
                    >{{$t('openScreenAdd.reselect')}}</p>
                  </div>
                  <div
                    v-if="!form.awardImg"
                    class="upload-add"
                    :style="'background-image:url('+$imageHost+'/image/admin/btn_add.png);'"
                    @click="selectImgHandle"
                  >
                  </div>
                  <span class="upload-tip">{{$t('openScreenAdd.recommendedSize')}}560px * 700px</span>
                </el-form-item>
                <el-form-item
                  label="活动链接："
                  label-width="110px"
                  prop="awardPath"
                >
                  <el-input
                    v-model="form.awardPath"
                    size="small"
                    style="width:170px;"
                  ></el-input>
                  <el-button
                    size="small"
                    @click="selectLinksVisible = !selectLinksVisible"
                  >选择链接</el-button>
                </el-form-item>
              </div>
              <el-form-item
                label="奖品份数："
                prop="awardNum"
                label-width="110px"
              >
                <el-input-number
                  v-model="form.awardNum"
                  controls-position="right"
                  :min="0"
                ></el-input-number>
              </el-form-item>
            </el-form-item>
            <el-form-item label="赠送限制：">
              <el-checkbox
                v-model="form.firstCommentGoods"
                :true-label="1"
                :false-label="0"
              >同一商品仅首次评价送礼</el-checkbox>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        style="width: 90px;"
        @click="saveOrderInfo"
      >保存</el-button>
    </div>

    <!-- 选择商品 -->
    <choosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="chooseGoods"
      @result="chooseGoodsHandle"
    ></choosingGoods>

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
import { addEvaluationGift, updateEvaluationGift, getEvaluationGift } from '@/api/admin/marketManage/evaluationGift.js'
import('@/util/date')

export default {
  components: {
    selectPayRewardAct: () => import('@/components/admin/marketManage/selectPayRewardAct'),
    selectCouponAct: () => import('@/components/admin/marketManage/selectCouponAct'),
    ImageDalog: () => import('@/components/admin/imageDalog'),
    selectLinks: () => import('@/components/admin/selectLinks'),
    choosingGoods: () => import('@/components/admin/choosingGoods')
  },
  data () {
    let that = this
    // 验证活动时间
    function validValidityPeriod (rule, value, callback) {
      if (that.form.isForever === 0) {
        if (!that.form.startTime || !that.form.endTime) {
          callback(new Error('请输入活动时间'))
        }
      }
      callback()
    }
    // 验证触发条件
    function validGoodsType (rule, value, callback) {
      if (that.form.goodsType === 2 && that.form.goodsIds === '') {
        callback(new Error('请选择活动商品'))
      } else if (that.form.goodsType === 3 && !that.form.commentNum) {
        callback(new Error('请输入限定的评价数量'))
      }
      if (that.form.commentType === 2) {
        if (that.form.hasFiveStars === 0 && that.form.hasPicNum === 0 && !that.form.hasCommentWords) {
          callback(new Error('请选择至少一种评价条件'))
        }
      }
      callback()
    }
    return {
      id: '',
      period: [], // 活动时间段
      form: {
        name: '',
        isForever: 0,
        startTime: '',
        endTime: '',
        level: '',
        goodsType: 1, // 商品类型 1全部商品 2指定商品 3 实际品论比较少的商品
        goodsIds: '', // 对应商品
        commentNum: '', // 评论数量
        commentType: 1, // 评价类型 1评价即送 2自定义
        commentWords: 0, // 评论字数限制字数 0 时不限制，大于0 时限制
        hasCommentWords: 0, // 评论字数限制
        hasPicNum: 0, // 晒图数量
        hasFiveStars: 0, // 五星好评
        awardType: 1, // 奖品类型 1积分 2优惠卷 3 余额 4 幸运大抽奖 5 自定义
        score: '', // 积分数
        activityId: '', // 活动id,优惠价或者抽奖
        account: '', // 用户余额
        awardNum: '', // 奖品份数
        sendNum: '', // 奖品送出数量
        awardPath: '', // 设置链接
        awardImg: '', // 活动图片
        firstCommentGoods: 0 // 同一商品仅首次评价送礼
      },
      rules: {
        name: [{ required: true, message: '请输入活动名称', trigger: 'blur' }],
        isForever: [{ required: true }, { validator: validValidityPeriod }],
        level: [{ required: true, message: '请输入活动优先级', trigger: 'blur' }],
        goodsType: [{ required: true }, { validator: validGoodsType }],
        score: [
          { required: true, message: '请输入积分', trigger: 'blur' },
          { type: 'number', min: 1, message: '请输入大于0的数字', trigger: 'blur' }
        ],
        account: [
          { required: true, message: '请输入余额', trigger: 'blur' },
          { type: 'number', min: 1, message: '请输入大于0的数字', trigger: 'blur' }
        ],
        activityId: [
          { required: true, message: '请选择活动', trigger: 'blur' }
        ],
        awardImg: [{ required: true, message: '请选择活动图片', trigger: 'blur' }],
        awardPath: [{ required: true, message: '请设置活动链接', trigger: 'blur' }],
        awardNum: [
          { required: true, message: '请输入奖品份数', trigger: 'blur' },
          { type: 'number', min: 1, message: '请输入大于0的数字', trigger: 'blur' }
        ]
      },
      tuneUpChooseGoods: false, // 选择商品
      chooseGoods: [], // 商品回显 id数组
      imageDalogVisible: false, // 选择图片
      uploadHover: false,
      selectLinksVisible: false // 选择链接
    }
  },
  watch: {
    period: function (newVal) {
      if (newVal) {
        this.$set(this.form, 'startTime', newVal[0].format('yyyy-MM-dd hh:mm:ss'))
        this.$set(this.form, 'endTime', newVal[1].format('yyyy-MM-dd hh:mm:ss'))
      }
    },
    'form.commentWords': {
      handler: function (newVal) {
        if (newVal > 0) {
          if (this.form.hasCommentWords === 0) {
            this.$set(this.form, 'hasCommentWords', 1)
          }
        } else {
          if (this.form.hasCommentWords === 1) {
            this.$set(this.form, 'hasCommentWords', 0)
          }
        }
      },
      immediate: true
    }
  },
  mounted () {
    if (this.$route.query.id) {
      this.id = this.$route.query.id
      this.initEvaluationInfo()
    }
  },
  methods: {
    initEvaluationInfo () {
      let params = {
        id: this.id
      }
      getEvaluationGift(params).then(res => {
        if (res.error === 0) {
          if (res.content.goodsIds) {
            this.chooseGoods = JSON.parse(res.content.goodsIds)
          }
          this.period = [new Date(res.content.startTime), new Date(res.content.endTime)]
          this.form = Object.assign({}, this.form, res.content)
        }
      })
    },
    // 选择商品
    chooseGoodsDialog () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    // 选择商品回调
    chooseGoodsHandle (goods) {
      this.chooseGoods = goods
      this.$set(this.form, 'goodsIds', JSON.stringify(goods))
    },
    goodsTypeChange (val) {
      this.chooseGoods = []
      this.$set(this.form, 'goodsIds', '')
      this.$set(this.form, 'commentNum', '')
    },
    hasCommentWordsChange (val) {
      if (val === 0) {
        this.$set(this.form, 'commentWords', '')
      }
    },
    // 选择图片
    selectImgHandle () {
      this.imageDalogVisible = !this.imageDalogVisible
    },
    hoverImgHandle () {
      this.uploadHover = !this.uploadHover
    },
    selectLinkHandle (path) {
      this.$set(this.form, 'awardPath', path)
    },
    handleSelectImg (img) {
      console.log(img)
      this.$set(this.form, 'awardImg', img.imgPath)
    },
    saveOrderInfo () {
      let that = this
      that.$refs.activityInfoForm.validate((valid) => {
        if (valid) {
          let params = Object.assign({}, that.form)
          console.log(params)
          if (this.id) {
            // 修改
            updateEvaluationGift(params).then(res => {
              if (res.error === 0) {
                that.$message.success('更新成功')
                that.$emit('changeTabAct', '1')
              }
            })
          } else {
            // 新增
            addEvaluationGift(params).then(res => {
              if (res.error === 0) {
                that.$message.success('添加成功')
                that.$emit('changeTabAct', '1')
              }
            })
          }
        }
      })
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
    .flex-block {
      display: flex;
      justify-content: space-around;
      width: 100%;
    }
    .tips {
      color: #999;
    }
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
      // 修改element-ui默认样式
      .el-input {
        width: 170px;
      }
      .el-radio {
        margin-right: 10px;
      }
      // 修改element-ui默认样式结束
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
      .award-type-radio-group {
        margin-bottom: 10px;
        .el-radio {
          line-height: 32px;
        }
      }
      .goods-type-radio-group {
        .el-radio {
          margin-right: 10px;
        }
      }
    }
  }
  .footer {
    position: fixed;
    z-index: 10;
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
