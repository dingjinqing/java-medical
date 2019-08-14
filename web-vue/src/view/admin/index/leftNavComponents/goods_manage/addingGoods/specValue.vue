<template>
  <div class="specValue">
    规格值：
    <el-tag
      :key="tag"
      v-for="tag in dynamicTags"
      closable
      effect="plain"
      :disable-transitions="false"
      @close="handleClose(tag)"
    >
      {{tag}}
    </el-tag>
    <el-input
      class="input-new-tag"
      v-if="inputVisible"
      v-model="inputValue"
      ref="saveTagInput"
      size="small"
      @keyup.enter.native="handleInputConfirm"
      @blur="handleInputConfirm"
    >
    </el-input>
    <el-button
      v-else
      class="button-new-tag"
      size="small"
      @click="showInput"
    >+ 新规格值</el-button>
  </div>
</template>
<script>
export default {
  name: 'specValue',
  data () {
    return {
      dynamicTags: [],
      inputVisible: false,
      inputValue: ''

    }
  },

  methods: {
    handleClose (tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    },

    showInput () {
      this.inputVisible = true
      this.$nextTick(_ => {
        console.log(this.$refs.saveTagInput.$refs)
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleInputConfirm () {
      let inputValue = this.inputValue
      console.log(inputValue)
      if (inputValue) {
        this.dynamicTags.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
    }
  }
}
</script>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
