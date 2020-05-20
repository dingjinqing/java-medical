<template>
  <div class="pos-authorization-config-page">
    <div class="content">
      <el-form
        v-loading="loading"
        :model="formData"
        label-width="30%"
        label-position="left"
        label-suffix="："
        size="small"
      >
        <el-form-item :label="$t('thirdPartyConfig.servicen')">
          <p class="label-con">{{formData.appBo.appName}}</p>
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
          <template v-if="formData.appAuthBo.status === 0">
            <el-button
              type="text"
              @click="authorizeHandle"
            >{{$t('thirdPartyConfig.authorizate')}}</el-button>
          </template>
          <template v-if="formData.appAuthBo.status === 1">
            <el-button
              type="text"
              @click="deleteAuthorize"
            >{{$t('thirdPartyConfig.deleteA')}}</el-button>
          </template>
          <el-button
            type="text"
            @click="syncPosHandle('sync_store')"
          >{{$t('thirdPartyConfig.syncPos')}}</el-button>
          <span class="label-con">{{$t('thirdPartyConfig.syncPnote')}}</span>
          <el-button
            type="text"
            @click="syncPosHandle('sync_goods')"
          >{{$t('thirdPartyConfig.syncPG')}}</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { syncPosApi } from '@/api/admin/basicConfiguration/thirdConfig'
import thirdConfigMixins from '@/mixins/basicManagementMixins/thirdConfigMixins.js'
export default {
  mixins: [thirdConfigMixins],
  data () {
    return {
      loading: false,
      action: 2,
      formData: {
        appBo: {},
        appAuthBo: {}
      }
    }
  },
  methods: {
    // 同步Pos门店
    syncPosHandle (opera) {
      this.loading = true
      syncPosApi({
        action: opera || '',
        id: this.formData.appAuthBo.id
      }).then(res => {
        this.loading = false
        if (res.error === 0) {
          this.$message.success(this.$t('thirdPartyConfig.successSync'))
          this.initData()
        } else {
          this.$message.error(res.message)
        }
      }).catch(err => {
        this.loading = false
        console.error(err)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.pos-authorization-config-page {
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
