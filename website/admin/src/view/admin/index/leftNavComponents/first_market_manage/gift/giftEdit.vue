<template>
  <div class="inputEditContainer">
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
    prdNumber: String | Number
  },
  model: {
    prop: 'input',
    event: 'update'
  },
  data () {
    return {
      showInput: false, // input框
      value: this.input
    }
  },
  methods: {
    switchEditState (value) {
      if (value) {
        var re = /^(0|\+?[1-9][0-9]*)$/
        if (!value) {
          this.$message.warning({ message: '请填写赠品库存' })
          return false
        }
        if (!re.test(value)) {
          this.$message.warning({ message: '赠品库存只能是0或者正整数' })
          return false
        }
        if (value > Number(this.prdNumber)) {
          this.$message.warning('赠品当前库存不能大于商品库存')
          return false
        }

        this.showInput = !this.showInput
        if (!this.showInput) {
          this.$emit('update', Number(this.value))
        }
      } else {
        this.showInput = !this.showInput
      }
    },
    closeBlur (value) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value) {
        this.$message.warning({ message: '请填写赠品库存' })
        return false
      }
      if (!re.test(value)) {
        this.$message.warning({ message: '赠品库存只能是0或者正整数' })
        return false
      }
      if (value > Number(this.prdNumber)) {
        this.$message.warning('赠品当前库存不能大于商品库存')
        return false
      }

      this.showInput = !this.showInput
      if (!this.showInput) {
        this.$emit('update', Number(this.value))
      }
    }
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
.inputEditContainer {
  display: flex;
  line-height: 45px;
  vertical-align: middle;
  .input {
    // width: 45%;
    text-align: right;
    margin-right: 10px;
  }
}
</style>
