<!--
* 带有编辑按钮的输入框，默认不显示输入框，点击编辑按钮即显示输入框
* 用于表格中可编辑的单元格
* 事件：
*     update：点击确定后传递输入内容
* 用法 <input-edit v-model="x" :init="y" @update="z"/>
* props：
*     init：初始值
* model 输入内容
* @author 郑保乐
-->
<template>
  <div class="container">
    <div
      v-show="!showInput"
      class="input"
    >
      <slot name="before"></slot>{{input}}<slot name="after"></slot>
    </div>
    <el-input
      ref="input"
      v-show="showInput"
      v-model="value"
      size="small"
      @blur="closeBlur(value)"
      style="width: 80px"
    ></el-input>
    <el-tooltip
      class="item"
      effect="dark"
      content="编辑"
      placement="top"
    >
      <i
        @click="switchEditState(value)"
        v-show="!disabled"
        class="el-icon-edit-outline"
        style="color:#409EFF;fontSize:20px;height:45px;line-height:45px;"
      ></i>
    </el-tooltip>
  </div>
</template>
<script>
export default {
  props: {
    input: String | Number,
    init: Number,
    disabled: Boolean
  },
  model: {
    prop: 'input',
    event: 'update'
  },
  data () {
    return {
      showInput: false,
      value: this.init || this.input
    }
  },
  methods: {
    switchEditState (value) {
      if (value) {
        var re = /^(0|\+?[1-9][0-9]*)$/
        if (!re.test(value)) {
          this.$message.warning({ message: '请填写0或者正整数' })
        } else {
          this.showInput = !this.showInput
          if (!this.showInput) {
            this.$emit('update', this.value)
          }
        }
      } else {
        this.showInput = !this.showInput
      }
    },
    closeBlur (value) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!re.test(value)) {
        this.$message.warning({ message: '请填写0或者正整数' })
      } else {
        this.showInput = false
        if (!this.showInput) {
          this.$emit('update', this.value)
        }
      }
    }
  },
  computed: {
    // btnContent () {
    //   if (this.showInput) {
    //     return '确定'
    //   }
    //   return '编辑'
    // }
  },
  watch: {
    showInput (v) {
      if (v) {
        this.$refs.input.focus()
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.container {
  display: flex;
  line-height: 45px;
  vertical-align: middle;
  // text-align: center;
  .input {
    width: 45%;
    text-align: right;
    margin-right: 10px;
  }
}
</style>
