<template>
    <div>

        <el-dialog
        :visible.sync="dialogVisiable"
        width="20%"
        title="审核不通过原因">
        <div class="content">
          <el-alert
            title="原因提交后不可修改，请谨慎提交"
            type="warning"
            :closable="false"
            show-icon
            class="tip">
          </el-alert>
          <el-input
            type="textarea"
            :rows="6"
            maxlength="150"
            show-word-limit
            placeholder="请输入内容"
            v-model="desc"
            style="width: 100%;">
          </el-input>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisiable = false">取 消</el-button>
            <el-button type="primary" @click="handleDesc">确 定</el-button>
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
        this.$emit('update:visiable', val)
      }
    }
  },
  data () {
    return {
      desc: ''
    }
  },
  methods: {
    handleDesc () {
      this.dialogVisiable = false
      this.$emit('handleDesc', this.desc)
    }
  }
}
</script>
<style scoped lang="scss">
  .content{
     width: 100%;
     margin:-30px 0;
     .tip{
       margin: 10px 0;
     }
  }
</style>
