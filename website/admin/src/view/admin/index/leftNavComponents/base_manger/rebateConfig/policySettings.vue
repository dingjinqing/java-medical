<template>
  <div class="container">
    <el-form ref="smsSetting" :model="formData" label-width="80px">
      <div class="main-title">
        <span>返利开关</span>
      </div>
      <div class="item-setting-content">
        <div class="item">
          <el-form-item prop="setting8">
            开启返利：<el-switch
              v-model="formData.setting8"
              active-color="#F7931E"
              inactive-color="#ccc"
            >
            </el-switch>
          </el-form-item>
          <el-form-item prop="setting9" style="margin-left: 200px;">
            自动审核：<el-switch
              v-model="formData.setting9"
              active-color="#F7931E"
              inactive-color="#ccc"
            >
            </el-switch>
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="setting10">
            每人每日最多提现金额：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting10"
            ></el-input-number
            >元
          </el-form-item>
          <el-form-item prop="setting11">
            每人每次最少提现金额：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting11"
            ></el-input-number
            >元
          </el-form-item>
        </div>
      </div>
      <div class="main-title">
        <span>商品订单返利策略</span>
      </div>
      <div class="item-setting-content">
        <div class="item">
          <el-form-item prop="setting1">
            分成比例：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting1"
            ></el-input-number
            >%
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="setting2">
            处方药医生佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting2"
            ></el-input-number
            >%，
          </el-form-item>
          <el-form-item prop="setting3">
            处方药平台佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting3"
            ></el-input-number
            >%
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="setting4">
            非处方药医生佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting4"
            ></el-input-number
            >%，
          </el-form-item>
          <el-form-item prop="setting5">
            非处方药平台佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting5"
            ></el-input-number
            >%
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item>
            返利提现金额 = 已完成订单该品类药品总金额*分成比例*佣金比例
          </el-form-item>
        </div>
      </div>
      <div class="main-title">
        <span>咨询订单返利策略</span>
      </div>
      <div class="item-setting-content">
        <div class="item">
          <el-form-item prop="setting6">
            医生佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting1"
            ></el-input-number
            >%，
          </el-form-item>
          <el-form-item prop="setting7">
            平台佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              size="small"
              v-model="formData.setting1"
            ></el-input-number
            >%
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item>
            返利提现金额 = 咨询费*佣金比例
          </el-form-item>
        </div>
      </div>
    </el-form>
    <el-row type="flex" justify="center">
      <el-button type="primary" @click="setSetting">保存</el-button>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      formData: {
        setting1: '',
        setting2: '',
        setting3: '',
        setting4: '',
        setting5: '',
        setting6: '',
        setting7: '',
        setting8: false,
        setting9: false,
        setting10: '',
        setting11: ''
      }
    }
  },
  methods: {
    setSetting () {
      console.log(111)
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  background-color: #fff;
  margin: 0 10px;
  overflow: hidden;
  padding: 0 16px 20px;
  .main-title {
    height: 40px;
    background-color: #eef1f6;
    line-height: 40px;
    margin-top: 20px;
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
    p {
      font-size: 14px;
      color: #333;
      .bold {
        font-weight: 600;
      }
    }
    /deep/ .item {
      display: flex;
      .el-form-item {
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
