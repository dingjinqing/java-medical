<template>
  <div class="container">
    <el-form
      ref="smsSetting"
      :model="formData"
      label-width="80px"
    >
      <div class="main-title">
        <span>短信设置</span>
      </div>
      <div class="item-setting-content">
        <div class="item-title">验证码短信</div>
        <div class="item">
          <el-form-item prop="userCheckCodeNum">
            每位用户每天最多发送<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.userCheckCodeNum"
            ></el-input-number>条
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="patientCheckCodeNum">
            每位患者每天最多发送<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.patientCheckCodeNum"
            ></el-input-number>条
          </el-form-item>
        </div>
      </div>
      <div class="item-setting-content">
        <div class="item-title">营销短信</div>
        <div class="item">
          <el-form-item prop="marketingNum">
            每天最多发送<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.marketingNum"
            ></el-input-number>条
          </el-form-item>
        </div>
      </div>
      <div class="item-setting-content">
        <div class="item-title">行业短信</div>
        <div class="item">
          <el-form-item prop="industryNum">
            每天最多发送<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.industryNum"
            ></el-input-number>条
          </el-form-item>
        </div>
      </div>
    </el-form>
    <el-row
      type="flex"
      justify="center"
    >
      <el-button
        type="primary"
        @click="setSetting"
      >保存</el-button>
    </el-row>
  </div>
</template>

<script>
import { getSmsSetting, setSmsSetting } from '@/api/admin/basicConfiguration/shopConfig.js'
export default {
  data () {
    return {
      formData: {
        industryNum: null,
        marketingNum: null,
        patientCheckCodeNum: null,
        userCheckCodeNum: null,
        smsAccountInfo: null
      }
    }
  },
  mounted () {
    this.getSetting()
  },
  methods: {
    getSetting () {
      getSmsSetting().then(res => {
        if (res.error === 0) {
          this.formData = res.content
        }
      })
    },
    setSetting () {
      let { industryNum, marketingNum, patientCheckCodeNum, userCheckCodeNum } = this.formData
      setSmsSetting({ industryNum, marketingNum, patientCheckCodeNum, userCheckCodeNum }).then(res => {
        if (res.error === 0) {
          this.getSetting()
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  background-color: #fff;
  margin: 0 10px;
  padding: 20px 16px;
  .main-title {
    height: 40px;
    background-color: #eef1f6;
    line-height: 40px;
    span {
      color: #333;
      font-size: 14px;
      font-weight: 600;
      margin-left: 28px;
      position: relative;
      &::before {
        content: ' ';
        position: absolute;
        height: 100%;
        width: 2px;
        background-color: #5a8bff;
        top: 0;
        left: -14px;
      }
    }
  }
  .item-setting-content {
    margin-top: 22px;
    display: flex;
    flex-direction: column;
    .item-title {
      height: 28px;
      line-height: 28px;
      padding-left: 18px;
      font-weight: 600;
      font-size: 14px;
      background: linear-gradient(
        270deg,
        rgba(255, 255, 255, 1) 0%,
        rgba(238, 241, 246, 1) 100%
      );
      background-size: 100px;
      background-repeat: no-repeat;
    }
    /deep/ .item {
      .el-form-item {
        margin-top: 16px !important;
        margin-bottom: 0;
        .el-form-item__content {
          margin-left: 16px !important;
          .el-input-number {
            display: inline-block;
            width: 100px;
            margin: 0 10px;
          }
        }
      }
    }
  }
  .save-button {
    width: 100px;
    line-height: 80px;
    border-radius: 8px;
    color: #fff;
  }
}
</style>
