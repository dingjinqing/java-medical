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
    // 重置sessionkey
    resetSessionKey () {
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
