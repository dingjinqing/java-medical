<template>
  <div class="CRM-authorization-config-page">
    <div class="content">
      <el-form
        :model="formData"
        label-width="30%"
        label-position="left"
        label-suffix="："
        size="small"
      >
        <el-form-item label="服务名称">
          <p class="label-con">{{formData.appBo.appName}}</p>
        </el-form-item>
        <el-form-item label="SessionKey">
          <span class="label-con">{{formData.appAuthBo.sessionKey}}</span>
          <span>(<el-button type="text">重置</el-button>)</span>
        </el-form-item>
        <el-form-item label="AppKey">
          <el-input
            class="form-input"
            v-model="formData.appAuthBo.appKey"
          ></el-input>
        </el-form-item>
        <el-form-item label="AppSecret">
          <el-input
            class="form-input"
            v-model="formData.appAuthBo.appSecret"
          ></el-input>
          <el-button
            type="primary"
            @click="saveAppKeyHandle"
          >提交</el-button>
        </el-form-item>
        <el-form-item label="是否已授权">
          <p class="label-con">{{formData.appAuthBo.status|fmtStatus}}</p>
        </el-form-item>
        <el-form-item label="操作">
          <template v-if="formData.appAuthBo.status === 0">
            <el-button
              type="text"
              @click="authorizeHandle"
            >授权</el-button>
          </template>
          <template v-else>
            <el-button type="text">删除授权</el-button>
          </template>
          <el-button type="text">同步CRM门店</el-button>
          <span class="label-con">注：仅同步未对接门店</span>
          <el-button type="text">同步CRM商品到系统商品</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import thirdConfigMixins from '@/mixins/basicManagementMixins/thirdConfigMixins.js'
export default {
  mixins: [thirdConfigMixins],
  data () {
    return {
      formData: {
        appBo: {},
        appAuthBo: {}
      }
    }
  },
  mounted () {
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

  }
}
</script>

<style lang="scss" scoped>
.CRM-authorization-config-page {
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
}
</style>
