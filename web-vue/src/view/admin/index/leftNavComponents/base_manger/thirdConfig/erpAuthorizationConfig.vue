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
        <el-form-item label="服务名称">
          <p
            class="label-con"
            v-text="formData.appBo.appName"
          ></p>
        </el-form-item>
        <el-form-item label="ERP产品版本">
          <el-select
            v-model="formData.appAuthBo.product"
            @change="versionChange"
          >
            <el-option
              label="企业版"
              :value="1"
            ></el-option>
            <el-option
              label="旗舰版"
              :value="2"
            ></el-option>
            <el-option
              label="e快帮"
              :value="3"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="SessionKey">
          <span class="label-con">{{formData.appAuthBo.sessionKey}}</span>
          <span>(<el-button type="text">重置</el-button>)</span>
        </el-form-item>
        <el-form-item label="卖家账号">
          <el-input class="form-input">{{formData.appAuthBo.appKey}}</el-input>
          <el-button
            type="primary"
            @click="saveAppKeyHandle"
          >提交</el-button>
        </el-form-item>
        <el-form-item label="是否已授权">
          <p class="label-con">{{formData.appAuthBo.status|fmtStatus}}</p>
        </el-form-item>
        <el-form-item label="操作">
          <el-button
            v-if="formData.appAuthBo.status === 1"
            type="text"
          >删除授权</el-button>
          <el-button
            v-if="formData.appAuthBo.status === 0"
            type="text"
            @click="authorizeHandle"
          >授权</el-button>
        </el-form-item>
        <el-form-item label="自提订单核销后推送">
          <el-switch
            v-model="formData.push"
            active-color="#F7931E"
            inactive-color="#ccc"
            :active-value="1"
            :inactive-value="0"
          ></el-switch>
        </el-form-item>
        <el-form-item label="同城配送订单已收货后推送">
          <el-switch
            v-model="formData.push"
            active-color="#F7931E"
            inactive-color="#ccc"
            :active-value="1"
            :inactive-value="0"
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
      title="提醒"
      width="260px"
      :visible.sync="switchErpVersionVisible"
    >
      <div>
        <span style="color:red;">请谨慎操作，切换产品版本对应不同的ERP产品，</span>确定要操作吗?
      </div>
      <div slot="footer">
        <el-button
          type="primary"
          size="small"
          @click="confirmSwitchVersion"
        >确认</el-button>
        <el-button
          size="small"
          @click="cancelSwitchVersion"
        >取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { switchVersionApi } from '@/api/admin/basicConfiguration/thirdConfig'
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
        }
      },
      oldVersion: '',
      newVersion: '',
      switchErpVersionVisible: false
    }
  },
  mounted () {
    this.oldVersion = this.formData.appAuthBo.product
    this.initData()
  },
  filters: {
    fmtStatus (val) {
      if (val === 0) {
        return '未授权'
      } else {
        return '已授权'
      }
    }
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
          this.$message.success('切换版本成功')
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
