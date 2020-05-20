// import vm from '../../main.js'
import { getThirdAuth, authorizeThird, resetsessionkey, saveAppKeyApi } from '@/api/admin/basicConfiguration/thirdConfig'

export default {
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
          this.$message.success('授权成功')
          this.initData()
        } else {
          this.$message.error('授权失败')
        }
      })
    },
    // 删除授权
    deleteAuthorize () {
      this.$confirm('<p><span style="color:red;">请谨慎操作，取消授权后数据将不再推送</span>，确定要取消授权吗?</p>', '提醒', {
        dangerouslyUseHTMLString: true,
        customClass: 'confirmCls'
      }).then(() => {
        authorizeThird({
          id: this.formData.appAuthBo.id,
          action: this.action,
          status: this.formData.appAuthBo.status ? 0 : 1
        }).then(res => {
          if (res.error === 0) {
            this.$message.success('删除授权成功')
            this.initData()
          } else {
            this.$message.error('删除授权失败')
          }
        }).catch(() => {
          this.$message.info('取消修改')
        })
      })
    },
    // 重置sessionkey
    resetSessionKey () {
      this.$confirm('<p><span style="color:red;">请谨慎重置</span>，确认要这样操作吗？</p>', '提醒', {
        dangerouslyUseHTMLString: true,
        customClass: 'confirmCls'
      }).then(() => {
        resetsessionkey({
          id: this.formData.appAuthBo.id
        }).then(res => {
          if (res.error === 0) {
            this.$message.success('重置成功')
            this.initData()
          } else {
            this.$message.error('重置失败')
          }
        })
      }).catch(() => {
        this.$message.info('取消修改')
      })
    },
    // 保存appKey
    saveAppKeyHandle () {
      saveAppKeyApi({
        id: this.formData.appAuthBo.id,
        action: this.action,
        appKey: this.formData.appAuthBo.appKey,
        appSecret: this.formData.appAuthBo.appAuthBo
      }).then(res => {
        if (res.error === 0) {
          this.$message.success('保存成功')
          this.initData()
        } else {
          this.$message.error('保存失败')
        }
      })
    }
  }
}
