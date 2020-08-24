<template>
  <el-dialog
    title="邀请码"
    :visible.sync="invitationDialog"
    :close-on-click-modal="false"
    width="30%"
    center
  >
    <el-form
      ref="form"
      :model="form"
      :rules="fromRules"
      label-width="80px"
    >
      <el-form-item
        label="邀请码："
        prop="inviteCode"
      >
        <el-input
          v-model="form.inviteCode"
          class="inputWidth"
          size="small"
        ></el-input>
      </el-form-item>

    </el-form>

    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="cancelInvitation"
        size="small"
      >取 消</el-button>
      <el-button
        type="primary"
        size="small"
        @click="sureInvitation"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  props: {
    // 调起弹窗
    tuneUp: {
      type: Boolean,
      default: () => false
    },
    // 回显数据
    inviteCodeBack: {
      type: String,
      default: () => ''
    }
  },
  data () {
    // 自定义校验邀请码
    var validateInviteCode = (rule, value, callback) => {
      var re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6}$/
      var re1 = /^[a-zA-Z]{6}$/
      if (!value) {
        callback(new Error('请填写邀请码'))
      } else if (!re.test(value) && !re1.test(value)) {
        callback(new Error('邀请码六位是以纯字母或数字和字母随机组合'))
      } else {
        callback()
      }
    }
    return {
      invitationDialog: false, // 邀请码弹窗
      form: {
        inviteCode: ''
      },
      // 校验表单
      fromRules: {
        inviteCode: [
          { required: true, validator: validateInviteCode, trigger: 'change' }
        ]
      }

    }
  },
  watch: {
    tuneUp (newData) {
      this.invitationDialog = true
      this.form.inviteCode = this.inviteCodeBack
    }
  },
  mounted () {
  },
  methods: {
    // 确定邀请码
    sureInvitation () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$emit('resultCodeRow', this.form.inviteCode)
          this.invitationDialog = false
        }
      })
    },

    // 取消邀请码
    cancelInvitation () {
      this.invitationDialog = false
      this.form.inviteCode = ''
    }
  }
}
</script>
<style lang="scss" scoped>
.inputWidth {
  width: 170px;
}
</style>
