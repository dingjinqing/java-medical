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
      <div v-show="!showInput" class="input">{{input}}</div>
      <el-input type="number" v-show="showInput" v-model="value"></el-input>
      <el-button @click="switchEditState" size="mini"
        v-show="!disabled">{{btnContent}}</el-button>
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
    switchEditState () {
      this.showInput = !this.showInput
      if (!this.showInput) {
        this.$emit('update', this.value)
      }
    }
  },
  computed: {
    btnContent () {
      if (this.showInput) {
        return '确定'
      }
      return '编辑'
    }
  }
}
</script>
<style lang="scss" scoped>
  .container {
    display: flex;
    line-height: 45px;
    vertical-align: middle;
    .input {
      width: 30%;
      text-align: right;
      margin-right: 10px;
    }
  }
</style>
