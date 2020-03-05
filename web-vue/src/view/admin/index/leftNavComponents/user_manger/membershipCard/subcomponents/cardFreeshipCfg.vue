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
                    <!-- <el-select  size="small" style="width: 150px;">
                        <el-option v-for="(item,index) in shipTime"
                            :label="item.label"
                            :value="item.value"
                            :key="index">
                        </el-option>
                    </el-select> -->
                    <span>享受</span>
                    <el-form :model="$data" style="display: inline-block">
                      <el-form-item prop="shipNum" :rules="shipRule.shipNum">
                          <el-input v-model="$data.shipNum" size="small" style="width: 80px;"></el-input>
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
        if ([0, 1].includes(this.type)) {
          res = this.type
        } else if (this.type > 1) {
          res = 1
        } else {
          res = 0
        }
        return String(res)
      },
      set (val) {
        this.$emit('update:type', Number(val))
      }

    },
    /**
     * 包邮次数
     */
    shipNumTwo: {
      get () {
        return this.num
      },
      set (val) {
        this.$emit('update:num', Number(val))
      }
    }
  },
  data () {
    return {
      cacheType: 0,
      shipTime: null,
      // 包邮有效期值
      shipTimeOptVal: [1, 2, 3, 4, 5, 6],
      // 校验规则
      shipNum: null,
      shipRule: {
        shipNum: [
          {
            required: true,
            message: '请输入有效值',
            trigger: 'blur'
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
      this.ruleData.shipNum = val
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
      console.log(val)
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
    }

}
</style>
