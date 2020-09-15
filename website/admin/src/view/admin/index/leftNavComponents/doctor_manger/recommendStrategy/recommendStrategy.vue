<template>
  <div class="container">
    <el-form
      ref="smsSetting"
      :model="formData"
      label-width="80px"
      :rules="rules"
    >
      <div class="main-title">
        <span>医师推荐权重配置</span>
      </div>
      <div class="item-setting-content">
        <div class="item">
          <el-form-item prop="status">
            <span class="fil_span">时间筛选：</span>
            <el-select
              v-model="timeSelect"
              size="small"
              @change="dateChangeHandler"
              class="timeSelect"
              style="width:120px;"
            >
              <el-option
                v-for="item in timeRange"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="withdrawCashMax">
            接诊量权重<el-input-number
              controls-position="right"
              :min="1"
              size="small"
              v-model="formData.withdrawCashMax"
            ></el-input-number>元
          </el-form-item>
          <el-form-item prop="withdrawCashMix">
            咨询费用权重：<el-input-number
              controls-position="right"
              :min="1"
              size="small"
              v-model="formData.withdrawCashMix"
            ></el-input-number>元
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item>
            推荐指数 = 接诊量指数*接诊量权重 + 咨询费用指数*咨询金额权重
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item>
            （接诊量/咨询金额）指数* = （接诊量/咨询费用）/（接诊量/咨询金额）平均数
          </el-form-item>
        </div>
      </div>
      <div class="main-title">
        <span>科室推荐权重配置</span>
      </div>
      <div class="item-setting-content">
        <div class="item">
          <el-form-item prop="status">
            <span class="fil_span">时间筛选：</span>
            <el-select
              v-model="timeSelect"
              size="small"
              @change="dateChangeHandler"
              class="timeSelect"
              style="width:120px;"
            >
              <el-option
                v-for="item in timeRange"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="withdrawCashMax">
            接诊量权重<el-input-number
              controls-position="right"
              :min="1"
              size="small"
              v-model="formData.withdrawCashMax"
            ></el-input-number>元
          </el-form-item>
          <el-form-item prop="withdrawCashMix">
            咨询金额权重：<el-input-number
              controls-position="right"
              :min="1"
              size="small"
              v-model="formData.withdrawCashMix"
            ></el-input-number>元
          </el-form-item>
          <el-form-item prop="withdrawCashMix">
            医生人数权重：<el-input-number
              controls-position="right"
              :min="1"
              size="small"
              v-model="formData.withdrawCashMix"
            ></el-input-number>元
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item>
            推荐指数 = 接诊量指数*接诊量权重 + 咨询金额指数*咨询金额权重 = 医生人数指数*医生人数权重
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
import { getWithDrawConfig, setWithDrawConfig } from '@/api/admin/basicConfiguration/doctorWithDraw'
export default {
  data () {
    return {
      formData: {
      },
      timeRange: [
        { value: 1, label: '最近1天' },
        { value: 7, label: '最近7天' },
        { value: 30, label: '最近30天' },
        { value: 90, label: '最近90天' }
      ],
      rules: {
        withdrawCashMax: [
          { required: true, message: '请输入最多提现', trigger: 'blur' }
        ],
        withdrawCashMix: [
          { required: true, message: '请输入最少提现', trigger: 'change' }
        ],
        goodsSharingProportion: [
          { required: true, message: '请输入分成比例', trigger: 'change' }
        ],
        rxMedicalDoctorProportion: [
          { required: true, message: '请输入医生佣金比例', trigger: 'change' }
        ],
        rxMedicalPlatformProportion: [
          { required: true, message: '请输入平台佣金比例', trigger: 'change' }
        ],
        noRxMedicalDoctorProportion: [
          { required: true, message: '请输入医生佣金比例', trigger: 'change' }
        ],
        noRxMedicalPlatformProportion: [
          { required: true, message: '请输入平台佣金比例', trigger: 'blur' }
        ],
        inquiryOrderDoctorProportion: [
          { required: true, message: '请输入医生佣金比例', trigger: 'blur' }
        ],
        inquiryOrderPlatformProportion: [
          { required: true, message: '请输入平台佣金比例', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.getSetting()
  },
  methods: {
    getSetting () {
      getWithDrawConfig().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.formData = { ...res.content, status: !!res.content.status, isAutomaticAudit: !!res.content.isAutomaticAudit }
        } else {
          this.$message.error({ message: res.message })
        }
      })
    },
    setSetting () {
      this.$refs['smsSetting'].validate((valid) => {
        if (valid) {
          let params = { ...this.formData, status: this.formData.status ? 1 : 0, isAutomaticAudit: this.formData.isAutomaticAudit ? 1 : 0 }
          setWithDrawConfig(params).then(res => {
            console.log(params)
            if (res.error === 0) {
              this.$message.success({ message: '保存成功' })
            } else {
              this.$message.error({ message: '保存失败' })
            }
          })
        } else {
          return false
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

  .fil_span {
    width: 100px;
    font-size: 14px;
    text-align: right;
  }
}
</style>
