<template>
  <div>
    <el-dialog
      title="事件说明"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <tinymceEditor
        ref='editor'
        @input="handleToGetText"
        :value='backText'
      />
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleToClickSure()"
        >确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {
  props: {
    explainVisible: {
      type: Boolean,
      default: false
    },
    backText: {
      type: String,
      default: ''
    }
  },

  components: {
    tinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor') // 富文本编译器
  },
  data () {
    return {
      backData: '',
      dialogVisible: false,
      richText: ''
    }
  },
  watch: {
    explainVisible (newData) {
      console.log(newData)
      if (newData) {
        this.dialogVisible = newData
      }
    },
    dialogVisible (newData) {
      if (!newData) {
        this.$emit('update:explainVisible', false)
      }
    }
  },
  methods: {
    // 获取富文本内容
    handleToGetText (res) {
      console.log(res)
      this.richText = res
    },
    // 点击确定
    handleToClickSure () {
      this.dialogVisible = false
      this.$emit('input', this.richText)
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
