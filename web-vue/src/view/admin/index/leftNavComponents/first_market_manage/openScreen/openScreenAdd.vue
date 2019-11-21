<template>
  <div class="container">
    <div class="content">
      <div class="open-screen-add-page">
        <div class="page-left">
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
        <div class="page-right">
          <div class="right-top">
            <header>活动配置</header>
            <el-form
              size="small"
              label-width="110px;"
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
                ></el-input>
              </el-form-item>
              <el-form-item label="活动宣传语：">
                <el-input
                  style="width:170px;"
                  size="small"
                  placeholder="最多支持20个字"
                  maxlength="20"
                ></el-input>
                <p>展示在前端页面，用于优惠活动通知</p>
              </el-form-item>
              <el-form-item label="活动有效期：">
                <div>
                  <el-radio>固定时间</el-radio>
                  <el-date-picker
                    type="daterange"
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
                  size="small"
                  style="width:170px;"
                  min="0"
                ></el-input>
                <p>用于区分不同开屏有礼活动的优先级，请填写正整数，数值越大优先级越高</p>
              </el-form-item>
              <el-form-item
                label="触发条件："
                required
              >
                <el-radio-group>
                  <el-radio>初次访问店铺的用户</el-radio>
                  <el-radio>未在店铺内支付的用户</el-radio>
                  <el-radio>全部用户</el-radio>
                </el-radio-group>
                <p>只针对新用户的活动只对初次进入小程序的用户可见，通常在拉新活动中使用较为常见</p>
              </el-form-item>
            </el-form>
          </div>
          <div class="right-bottom">
            <header>开屏奖励</header>
            <el-form>
              <el-form-item
                label="支付奖励："
                required
              >
                <el-radio-group>
                  <el-radio>积分</el-radio>
                  <el-radio>优惠券</el-radio>
                  <el-radio>幸运大抽奖</el-radio>
                  <el-radio>余额</el-radio>
                  <el-radio>分裂优惠券</el-radio>
                  <el-radio>自定义</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item
                label="活动图片："
                required
              >
                <div
                  class="upload-add"
                  :style="'background-image:url('+$imageHost+'/image/admin/btn_add.png);'"
                >
                </div>
                <span>建议尺寸：560px * 700px</span>
              </el-form-item>
              <el-form-item
                label="设置链接："
                required
              >
                <el-input
                  size="small"
                  style="width:170px;"
                ></el-input>
                <el-button size="small">选择链接</el-button>
              </el-form-item>
              <el-form-item label="奖品份数：">
                <el-number-input size="small"></el-number-input>
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
        @click="saveOpenScreenHandle"
      >保存</el-button>
    </footer>
  </div>
</template>

<script>
import { addOpenScreen } from '@/api/admin/marketManage/openScreen.js'
export default {
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
        action: '', // 触发条件

        customizeImgUrl: '', // 自定义图片
        customizePagePath: '', // 自定义链接

        activityId: '', // 抽奖活动id

        title: '', // 活动宣传语
        bgAction: '', // 背景图
        couponId: [] // 优惠券id
      },
      rules: {
        name: [{ required: true, message: '请输入活动名称', trigger: 'blur' }]
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
        }
        header {
          height: 40px;
          line-height: 40px;
          border-bottom: 1px solid #eee;
        }
      }
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
