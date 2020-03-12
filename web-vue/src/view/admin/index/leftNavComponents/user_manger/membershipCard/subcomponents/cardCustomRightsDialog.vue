<template>
    <div>
        <el-dialog
            :visible.sync="dialogVisiable"
            title="自定义权限"
            width="30%"
        >

        </el-dialog>
    </div>
</template>
<script>
export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    dialogVisiable: {
      get () {
        return this.visible
      },
      set (val) {
        console.log(val)
        this.$emit('update:visible', val)
      }
    }
  }
}
</script>
<style scoped lang="scss">

</style>
