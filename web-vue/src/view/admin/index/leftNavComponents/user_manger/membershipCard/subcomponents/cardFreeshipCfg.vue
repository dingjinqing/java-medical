<template>
    <div class="freeship_container">
        <el-form label-width="200px">
            <el-form-item class="on_free_ship">
                    <el-checkbox v-model="turnOn">包邮</el-checkbox>
                    <span>持有此会员卡的用户可享受包邮</span>
            </el-form-item>
            <el-form-item class="free_ship_content" v-if="turnOn" >
                <div>
                    <el-radio v-model="freeshipType" label="0">不限制包邮次数</el-radio>
                </div>
                <div>
                    <el-radio v-model="freeshipType" label="1">持卡会员</el-radio>
                    <el-form :model="$data"  style="display: inline-block;">
                      <el-form-item prop="shipTimeVal" :rules="shipRules.shipTimeVal">
                        <el-select  :value="$data.shipTimeVal" size="small" style="width: 150px;" @change="updateShipTimeVal">
                            <el-option v-for="(item,index) in shipTime"
                                :label="item.label"
                                :value="item.value"
                                :key="index">
                            </el-option>
                        </el-select>
                      </el-form-item>
                    </el-form>
                    {{shipTimeVal}}
                    <span>享受</span>
                    <el-form :inline="true"   :model="$data" ref="shipNum" :rules="shipRules" style="display: inline-block">
                      <el-form-item prop="shipNum">
                        <!-- 当通过校验时，触发input事件(但是目前有elementui有bug通过第一个校验后，就会调用) -->
                          <el-input v-model.number="$data.shipNum" size="small" style="width: 80px;" @input="changeShipNum('shipNum')"></el-input>
                      </el-form-item>
                    </el-form>
                    <span>次包邮</span>
                </div>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
  props: {
    num: {
      type: Number,
      default: -1
    },
    type: {
      type: Number,
      default: 0
    }
  },
  computed: {
    /**
     * 是否包邮
     */
    turnOn: {
      get () {
        return this.type !== -1
      },
      set (val) {
        let res = null
        if (val) {
          res = this.cacheType
        } else {
          // 为false的时候，更新包邮类型值为不包邮，其余值为包邮
          if (this.type !== -1) {
            // 缓存之前的选项
            this.cacheType = this.type
          }
          res = -1
        }
        this.$emit('update:type', res)
      }

    },
    /**
     * 包邮类型
     */
    freeshipType: {
      get () {
        let res = null
        if ([0, -1].includes(this.type)) {
          res = 0
        } else if (this.type > 0) {
          res = 1
        } else {
          res = 0
        }
        if (this.holdCardOptOn) {
          res = 1
        }
        return String(res)
      },
      set (val) {
        let res = null
        if (val === '1') {
          this.holdCardOptOn = true
          if (this.shipTimeVal !== '') {
            res = this.shipTimeVal
          } else {
            return
          }
        } else {
          this.holdCardOptOn = false
          res = val
        }
        this.$emit('update:type', Number(res))
      }

    },
    /**
     * 包邮的时间类型
     */
    shipTimeVal2: {
      get () {
        if ([-1, 0].includes(this.type)) {
          return ''
        } else {
          return this.type
        }
      },
      set (val) {
        this.$emit('update:type', Number(val))
      }
    }

  },
  data () {
    return {
      cacheType: 0,
      shipTime: null,
      holdCardOptOn: false,
      // 包邮有效期值
      shipTimeOptVal: [1, 2, 3, 4, 5, 6],
      shipTimeVal: null,
      shipNum: null,
      // 校验规则
      shipRules: {
        // 包邮有效期规则
        shipTimeVal: [
          {required: true, type: 'number', message: '请选择', trigger: ['blur', 'change']}
        ],

        // 包邮次数规则
        shipNum: [
          {
            required: true,
            message: '请输入有效值'
          // trigger: 'blur'
          }, {
          // 1到7位整数校验
            pattern: /^[0-9]\d{0,6}$/,
            message: '请输入1到7位的整数'
          }
        ]
      }

    }
  },
  watch: {
    lang () {
      this.initShipTimeOption()
    },
    num (val) {
      this.shipNum = val
    },
    type (val) {
      console.log(val)
      if ([-1, 0].includes(this.type)) {
        // this.shipTimeVal = ''
      } else {
        this.shipTimeVal = this.type
      }
    }
  },
  mounted () {
    this.langDefault()
    this.initShipTimeOption()
  },
  methods: {
    /**
     * 初始化选项
     */
    initShipTimeOption () {
      this.shipTime = []
      let res = this.$t('memberCard.shipTime')
      for (let index = 0; index < this.shipTimeOptVal.length; index++) {
        this.shipTime.push({
          label: res[index],
          value: this.shipTimeOptVal[index]
        })
      }
    },
    changeShipNum (val) {
      this.$refs[val].validate((valid) => {
        if (valid) {
          this.$emit('update:num', Number(this.shipNum))
        } else {
          // todo 可以在这里，校验不通过时，进行一些处理。
        }
      })
    },
    /**
     * 包邮选择框
     */
    updateShipTimeVal (val) {
      this.$emit('update:type', Number(val))
    }
  }
}
</script>

<style scoped lang="scss">
.freeship_container{
    /deep/ .el-form-item {
        margin-bottom: 2px;
    }
    background-color: white;
    height: 400px;
    width: 100%;
    .on_free_ship{
        span{
            margin-left: 52px;
            color: #9d9d9d;
        }
    }
    .free_ship_content{
        margin-left: 72px;
        /deep/ .el-select{
            width: 150px;
        }
        /deep/ .el-input__inner{
          text-align: center;
        }
    }

}
</style>
