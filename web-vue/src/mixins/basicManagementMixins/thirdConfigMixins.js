import vm from '../../main.js'
import { getThirdAuth, authorizeThird, resetsessionkey, saveAppKeyApi } from '@/api/admin/basicConfiguration/thirdConfig'

export default {
  mounted () {
    this.initData()
  },
  filters: {
    fmtStatus (val) {
      if (val === 0) {
        return vm.$t('thirdPartyConfig.unauthorized')
      } else {
        return vm.$t('thirdPartyConfig.authorized')
      }
    }
  },
  methods: {
    // 初始化数据
    initData () {
      getThirdAuth({
        action: this.action
      }).then(res => {
        if (res.error === 0) {
          this.formData = Object.assign({}, this.formData, res.content)
        }
      })
    },
    // 授权
    authorizeHandle () {
      authorizeThird({
        id: this.formData.appAuthBo.id,
        action: this.action,
        status: this.formData.appAuthBo.status ? 0 : 1
      }).then(res => {
        if (res.error === 0) {
          this.$message.success(vm.$t('thirdPartyConfig.suAu'))
          this.initData()
        } else {
          this.$message.error(vm.$t('thirdPartyConfig.failAu'))
        }
      })
    },
    // 删除授权
    deleteAuthorize () {
      this.$confirm('<p><span style="color:red;">' + vm.$t('thirdPartyConfig.cautionAu') + '</span>' + this.$t('thirdPartyConfig.ayCancelAu') + '?</p>', this.$t('thirdPartyConfig.remind'), {
        dangerouslyUseHTMLString: true,
        customClass: 'confirmCls'
      }).then(() => {
        authorizeThird({
          id: this.formData.appAuthBo.id,
          action: this.action,
          status: this.formData.appAuthBo.status ? 0 : 1
        }).then(res => {
          if (res.error === 0) {
            this.$message.success(vm.$t('thirdPartyConfig.suDA'))
            this.initData()
          } else {
            this.$message.error(vm.$t('thirdPartyConfig.failDA'))
          }
        }).catch(() => {
          this.$message.info(vm.$t('thirdPartyConfig.cancelM'))
        })
      })
    },
    // 重置sessionkey
    resetSessionKey () {
      this.$confirm('<p><span style="color:red;">' + vm.$t('thirdPartyConfig.cautionR') + '</span>' + vm.$t('thirdPartyConfig.ayOso') + '</p>', this.$t('thirdPartyConfig.remind'), {
        dangerouslyUseHTMLString: true,
        customClass: 'confirmCls'
      }).then(() => {
        resetsessionkey({
          id: this.formData.appAuthBo.id
        }).then(res => {
          if (res.error === 0) {
            this.$message.success(vm.$t('thirdPartyConfig.resetS'))
            this.initData()
          } else {
            this.$message.error(vm.$t('thirdPartyConfig.resetF'))
          }
        })
      }).catch(() => {
        this.$message.info(vm.$t('thirdPartyConfig.cancelM'))
      })
    },
    // 保存appKey
    saveAppKeyHandle () {
      saveAppKeyApi({
        id: this.formData.appAuthBo.id,
        action: this.action,
        appKey: this.formData.appAuthBo.appKey,
        appSecret: this.formData.appAuthBo.appSecret
      }).then(res => {
        if (res.error === 0) {
          this.$message.success(vm.$t('thirdPartyConfig.saveS'))
          this.initData()
        } else {
          this.$message.error(vm.$t('thirdPartyConfig.saveF'))
        }
      })
    }
  }
}
