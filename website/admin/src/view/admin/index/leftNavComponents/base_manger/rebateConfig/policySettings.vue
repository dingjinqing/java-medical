<template>
  <div class="container">
    <el-form
      ref="smsSetting"
      :model="formData"
      label-width="80px"
      :rules="rules"
    >
      <div class="main-title">
        <span>返利开关</span>
      </div>
      <div class="item-setting-content">
        <div class="item">
          <el-form-item prop="status">
            开启返利：<el-switch
              v-model="formData.status"
              active-color="#F7931E"
              inactive-color="#ccc"
            >
            </el-switch>
          </el-form-item>
          <el-form-item prop="isAutomaticAudit" style="margin-left: 200px">
            自动审核：<el-switch
              v-model="formData.isAutomaticAudit"
              active-color="#F7931E"
              inactive-color="#ccc"
            >
            </el-switch>
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="withdrawCashMax">
            每人每日最多提现金额：<el-input-number
              controls-position="right"
              :min="1"
              size="small"
              v-model="formData.withdrawCashMax"
            ></el-input-number
            >元
          </el-form-item>
          <el-form-item prop="withdrawCashMix">
            每人每次最少提现金额：<el-input-number
              controls-position="right"
              :min="1"
              size="small"
              v-model="formData.withdrawCashMix"
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
          <el-form-item prop="goodsSharingProportion">
            分成比例：<el-input-number
              controls-position="right"
              :min="0"
              :max="100"
              size="small"
              v-model="formData.goodsSharingProportion"
            ></el-input-number
            >%
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="rxMedicalDoctorProportion">
            处方药医生佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              :max="100 - formData.rxMedicalPlatformProportion"
              size="small"
              v-model="formData.rxMedicalDoctorProportion"
            ></el-input-number
            >%，
          </el-form-item>
          <el-form-item prop="rxMedicalPlatformProportion">
            处方药平台佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              :max="100 - formData.rxMedicalDoctorProportion"
              size="small"
              v-model="formData.rxMedicalPlatformProportion"
            ></el-input-number
            >%
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item prop="noRxMedicalDoctorProportion">
            非处方药医生佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              :max="100 - formData.noRxMedicalPlatformProportion"
              size="small"
              v-model="formData.noRxMedicalDoctorProportion"
            ></el-input-number
            >%，
          </el-form-item>
          <el-form-item prop="noRxMedicalPlatformProportion">
            非处方药平台佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              :max="100 - formData.noRxMedicalDoctorProportion"
              size="small"
              v-model="formData.noRxMedicalPlatformProportion"
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
          <el-form-item prop="inquiryOrderDoctorProportion">
            医生佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              :max="100 - formData.inquiryOrderPlatformProportion"
              size="small"
              v-model="formData.inquiryOrderDoctorProportion"
            ></el-input-number
            >%，
          </el-form-item>
          <el-form-item prop="inquiryOrderPlatformProportion">
            平台佣金比例：<el-input-number
              controls-position="right"
              :min="0"
              :max="100 - formData.inquiryOrderDoctorProportion"
              size="small"
              v-model="formData.inquiryOrderPlatformProportion"
            ></el-input-number
            >%
          </el-form-item>
        </div>
        <div class="item">
          <el-form-item> 返利提现金额 = 咨询费*佣金比例 </el-form-item>
        </div>
      </div>
    </el-form>
    <el-row type="flex" justify="center">
      <el-button type="primary" @click="setSetting">保存</el-button>
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
}
</style>
