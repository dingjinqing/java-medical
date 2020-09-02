<template>
  <div
    class="hc-container"
    :style="inline?'display:inline-block;':''"
  >
    <template v-if="!type || type === 'price' || type === 'weight'">
      <el-input
        ref="floatInput"
        v-model="hcvalue"
        :value="hcvalue"
        :placeholder="placeholder"
        :disabled="disabled"
        :maxlength="hcMaxLength"
        :minlength="hcMinLength"
        :min="min"
        :max="hcMax"
        :controls="controls"
        :show-word-limit="showWordLimit"
        size="small"
        @change="changeHandle"
        @input="inputHandle"
      ></el-input>
    </template>
    <template v-else>
      <el-input
        ref="integerInput"
        v-model.number="hcvalue"
        :value="hcvalue"
        :placeholder="placeholder"
        :disabled="disabled"
        :maxlength="hcMaxLength"
        :minlength="hcMinLength"
        :min="min"
        :max="max"
        :controls="controls"
        :show-word-limit="showWordLimit"
        size="small"
        @change="changeHandle"
        @input="inputHandle"
      ></el-input>
    </template>
  </div>
</template>

<script>
export default {
  name: 'HcInputNumber',
  model: {
    prop: 'value',
    event: 'input'
  },
  props: {
    // el-input原有属性
    value: [Number, String],
    placeholder: String,
    disabled: Boolean,
    maxlength: Number,
    minlength: Number,
    min: Number,
    max: Number,
    'show-word-limit': Boolean,
    controls: {
      type: Boolean,
      default: true
    },
    /**
     * hc-input-number自有属性
     * type:
     * priority 优先级(5位),
     * integer 整数(9位),
     * price 价格(整数9位，小数2位),
     * weight 重量(整数9位，小数3位)
     */
    type: String,
    inline: Boolean,
    negative: {
      type: Boolean, // 是否允许负数
      default: false
    }
  },
  computed: {
    hcvalue: {
      get () {
        return this.value
      },
      set (val) {
        console.log('inputSet:', val)
        // 根据type对输入值限制
        if (this.negative) {
          if (String(val).indexOf('-') === 0) {
            // eslint-disable-next-line no-useless-escape
            val = '-' + String(val).replace(/[^\d^\.]/g, '')
          } else {
            // eslint-disable-next-line no-useless-escape
            val = String(val).replace(/[^\d^\.]/g, '')
          }
        } else {
          // eslint-disable-next-line no-useless-escape
          val = String(val).replace(/[^\d^\.]/g, '')
        }
        switch (this.type) {
          case 'priority':
            if (Number(val) === 0) {
              val = ''
            }
            val = String(val).replace(/[^-^\d]/g, '')
            if (this.hcMax && Number(val) > this.hcMax) {
              val = this.hcMax
            }
            break
          case 'integer':
            val = String(val).replace(/[^-^\d]/g, '')
            if (this.hcMax && Number(val) > this.hcMax) {
              val = this.hcMax
            }
            break
          case 'price':
            if (val.indexOf('.') > -1) {
              let inputArr = val.split('.')
              if (inputArr.length >= 3) {
                inputArr = [inputArr[0], inputArr[1]]
              }
              if (inputArr[1].length > 2) {
                inputArr[1] = inputArr[1].slice(0, 2)
              }
              val = inputArr.join('.')
              if (this.hcMax && Number(val) > this.hcMax) {
                val = Number(this.hcMax).toFixed(2)
              }
              break
            } else if (val.length >= 9) {
              val = val.slice(0, 9)
            }
            if (this.hcMax && Number(val) > this.hcMax) {
              val = this.hcMax
            }
            break
          case 'weight':
            if (val.indexOf('.') > -1) {
              let inputArr = val.split('.')
              if (inputArr.length >= 3) {
                inputArr = [inputArr[0], inputArr[1]]
              }
              if (inputArr[1].length > 3) {
                inputArr[1] = inputArr[1].slice(0, 3)
              }
              val = inputArr.join('.')
              if (this.hcMax && Number(val) > this.hcMax) {
                val = Number(this.hcMax).toFixed(3)
              }
            } else if (val.length >= 9) {
              val = val.slice(0, 9)
            }
            if (this.hcMax && Number(val) > this.hcMax) {
              val = Number(this.hcMax)
            }
            break
        }
        this.$emit('input', val)
      }
    },
    hcMaxLength: {
      get () {
        if (this.maxlength) {
          return this.maxlength
        } else {
          let length = ''
          switch (this.type) {
            case 'priority':
              length = 5
              break
            case 'integer':
              length = 9
              break
            case 'price':
              length = 12
              break
            case 'weight':
              length = 13
              break
          }
          return length
        }
      }
    },
    hcMinLength: {
      get () {
        return this.minlength
      }
    },
    hcMax: function () {
      if (isNaN(Number(this.max))) {
        return ''
      }
      return Number(this.max)
    },
    hcMin: function () {
      if (isNaN(Number(this.min))) {
        return ''
      }
      return Number(this.min || 0)
    }
  },
  mounted () {
    console.log('hc mounted...', this)
  },
  methods: {
    changeHandle (val) {
      console.log('changeHandle: ', val)
      if (val && !(isNaN(val - 0))) {
        console.log(typeof val)
        val = Number(val)
      }
      if (this.min && val < Number(this.min)) {
        val = Number(this.min)
      }
      this.$emit('input', val)
      this.$emit('change', val)
    },
    inputHandle (val) {
      console.log('inputHandle：', val)
    },
    focus () {
      this.getInput().focus()
    },
    blur () {
      this.getInput().blur()
    },
    select () {
      this.getInput().select()
    },
    getInput () {
      return this.$refs.integerInput || this.$refs.floatInput
    }
  }
}
</script>

<style lang="scss" scoped>
.hc-container {
  /deep/ .el-input {
    text-align: inherit;
    input.el-input__inner {
      text-align: inherit;
    }
  }
}
</style>
