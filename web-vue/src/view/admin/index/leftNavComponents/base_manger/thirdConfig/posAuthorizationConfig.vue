<template>
  <div class="pos-authorization-config-page">
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
        <el-form-item label="卖家账号">
          <el-input
            class="form-input"
            v-model="formData.appAuthBo.appKey"
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
          <template v-if="formData.appAuthBo.status === 1">
            <el-button type="text">删除授权</el-button>
          </template>
          <el-button
            type="text"
            @click="syncPosHandle('sync_store')"
          >同步POS门店</el-button>
          <span class="label-con">注：仅同步未对接门店</span>
          <el-button
            type="text"
            @click="syncPosHandle('sync_goods')"
          >同步POS商品到系统商品</el-button>
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
      action: 2,
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
    syncPosHandle (opera) {
      syncPosApi({
        action: opera || '',
        is: this.formData.appAuthBo.id
      }).then(res => {
        if (res.error === 0) {
          this.$message.success('同步成功')
          this.initData()
        } else {
          this.$message.error(res.message)
        }
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
