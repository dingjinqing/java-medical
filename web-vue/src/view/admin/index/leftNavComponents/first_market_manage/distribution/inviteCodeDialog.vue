<template>
  <el-dialog
    title="邀请码"
    :visible.sync="invitationDialog"
    :close-on-click-modal="false"
    width="30%"
    center
  >
    邀请码：
    <el-input
      v-model="inviteCode"
      class="inputWidth"
      size="small"
    ></el-input>

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
    return {
      invitationDialog: false, // 邀请码弹窗
      inviteCode: ''
    }
  },
  watch: {
    tuneUp (newData) {
      this.invitationDialog = true
      this.inviteCode = this.inviteCodeBack
    }
  },
  mounted () {
  },
  methods: {
    // 确定邀请码
    sureInvitation () {
      this.$emit('resultCodeRow', this.inviteCode)
      this.invitationDialog = false
    },

    // 取消邀请码
    cancelInvitation () {
      this.invitationDialog = false
    }
  }
}
</script>
<style lang="scss" scoped>
.inputWidth {
  width: 170px;
}
</style>
