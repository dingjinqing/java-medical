<template>
  <div class="erp-authorization-config-page">
    <div class="content">
      <el-form
        :model="formData"
        label-width="30%"
        label-position="left"
        label-suffix="："
        size="small"
      >
        <el-form-item :label="$t('thirdPartyConfig.servicen')">
          <p
            class="label-con"
            v-text="formData.appBo.appName"
          ></p>
        </el-form-item>
        <el-form-item :label="$t('thirdPartyConfig.erppv')">
          <el-select
            v-model="formData.appAuthBo.product"
            @change="versionChange"
          >
            <el-option
              :label="$t('thirdPartyConfig.enterpriseE')"
              :value="1"
            ></el-option>
            <el-option
              :label="$t('thirdPartyConfig.flagship')"
              :value="2"
            ></el-option>
            <el-option
              :label="$t('thirdPartyConfig.equick')"
              :value="3"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="SessionKey">
          <span class="label-con">{{formData.appAuthBo.sessionKey}}</span>
          <span>(<el-button
              type="text"
              @click="resetSessionKey"
            >{{$t('thirdPartyConfig.reset')}}</el-button>)</span>
        </el-form-item>
        <el-form-item :label="$t('thirdPartyConfig.sellerA')">
          <el-input
            class="form-input"
            v-model="formData.appAuthBo.appKey"
          ></el-input>
          <el-button
            type="primary"
            @click="saveAppKeyHandle"
          >{{$t('thirdPartyConfig.submit')}}</el-button>
        </el-form-item>
        <el-form-item :label="$t('thirdPartyConfig.whetherA')">
          <p class="label-con">{{formData.appAuthBo.status|fmtStatus}}</p>
        </el-form-item>
        <el-form-item :label="$t('thirdPartyConfig.operate')">
          <el-button
            v-if="formData.appAuthBo.status === 1"
            type="text"
            @click="deleteAuthorize"
          >{{$t('thirdPartyConfig.deleteA')}}</el-button>
          <el-button
            v-if="formData.appAuthBo.status === 0"
            type="text"
            @click="authorizeHandle"
          >{{$t('thirdPartyConfig.authorizate')}}</el-button>
        </el-form-item>
        <el-form-item :label="$t('thirdPartyConfig.aftermention')">
          <el-switch
            v-model="formData.verifyOrder"
            active-color="#F7931E"
            inactive-color="#ccc"
            :active-value="1"
            :inactive-value="0"
            @change="pushSetHandle('verify_order', $event)"
          ></el-switch>
        </el-form-item>
        <el-form-item :label="$t('thirdPartyConfig.afterReceived')">
          <el-switch
            v-model="formData.cityOrderPush"
            active-color="#F7931E"
            inactive-color="#ccc"
            :active-value="1"
            :inactive-value="0"
            @change="pushSetHandle('city_order_push', $event)"
          ></el-switch>
        </el-form-item>
        <!-- <div class="module-name">
          商家收货地址
        </div>
        <el-form-item label="收件人">
          <el-input class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="退货地址">
          <el-input class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="商家电话">
          <el-input class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="邮编">
          <el-input class="form-input"></el-input>
        </el-form-item> -->
      </el-form>
    </div>
    <!-- <div class="footer">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
    </div> -->
    <!-- 确认提交 -->
    <el-dialog
      :title="$t('thirdPartyConfig.remind')"
      width="260px"
      :visible.sync="switchErpVersionVisible"
    >
      <div>
        <span style="color:red;">{{$t('thirdPartyConfig.cautionS')}}</span>{{$t('thirdPartyConfig.ayO')}}?
      </div>
      <div slot="footer">
        <el-button
          type="primary"
          size="small"
          @click="confirmSwitchVersion"
        >{{$t('thirdPartyConfig.yes')}}</el-button>
        <el-button
          size="small"
          @click="cancelSwitchVersion"
        >{{$t('thirdPartyConfig.no')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { switchVersionApi, pushSetApi } from '@/api/admin/basicConfiguration/thirdConfig'
import thirdConfigMixins from '@/mixins/basicManagementMixins/thirdConfigMixins.js'
export default {
  mixins: [thirdConfigMixins],
  data () {
    return {
      action: 1,
      formData: {
        appBo: {},
        appAuthBo: {
          product: 1
        },
        verifyOrder: 0,
        cityOrderPush: 0
      },
      oldVersion: '',
      newVersion: '',
      switchErpVersionVisible: false
    }
  },
  mounted () {
    this.oldVersion = this.formData.appAuthBo.product
  },
  methods: {
    // ERP 产品版本更改
    versionChange (val) {
      if (this.oldVersion) {
        this.newVersion = val
        this.$set(this.formData.appAuthBo, 'product', this.oldVersion)
      }
      this.switchErpVersionVisible = !this.switchErpVersionVisible
    },
    // 确认更改版本
    confirmSwitchVersion () {
      switchVersionApi({
        id: this.formData.appAuthBo.id,
        product: this.newVersion
      }).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('thirdPartyConfig.successS'))
          this.$set(this.formData.appAuthBo, 'product', this.newVersion)
          this.oldVersion = this.newVersion
          this.switchErpVersionVisible = false
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 取消更改版本
    cancelSwitchVersion () {
      this.switchErpVersionVisible = false
    },
    // 更改推送
    pushSetHandle (operate, val) {
      console.log(operate, val)
      let param = {
        id: this.formData.appAuthBo.id,
        status: val,
        type: operate
      }
      pushSetApi(param).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('thirdPartyConfig.successC'))
        } else {
          this.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.erp-authorization-config-page {
  .el-form {
    width: 840px;
    padding: 0px 30px;
    margin-bottom: 20px;
    border: 1px solid #ededed;
  }
  .el-form-item {
    line-height: 50px;
    border-bottom: 1px solid #eee;
  }
  /deep/ .el-form-item__label {
    line-height: 50px;
  }
  /deep/ .el-form-item__content {
    line-height: 50px;
  }
  .label-con {
    color: #999999;
    font-size: 12px;
  }
  .form-input {
    width: 215px;
  }
  .footer {
    padding: 10px 0;
    text-align: center;
  }
  .module-name {
    width: 100%;
    height: 40px;
    line-height: 40px;
    padding-left: 16px;
    background: #eef1f6;
    font-size: 13px;
  }
  .switch-dialog {
    width: 200px;
  }
}
</style>
