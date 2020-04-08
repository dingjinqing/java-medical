<template>
    <div>
        <el-dialog
        :visible.sync="dialogVisiable"
        width="30%"
        title="内层 Dialog">
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisiable = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisiable = false">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
export default {
  props: {
    visiable: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    dialogVisiable: {
      get () {
        return this.visiable
      },
      set (val) {
        this.$emit('update: visiable', val)
      }
    }
  }
}
</script>
