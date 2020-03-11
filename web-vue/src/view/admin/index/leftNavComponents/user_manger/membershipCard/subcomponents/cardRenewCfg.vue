<template>
    <div class="container">
        <el-form :model="$data" :rules="myRules" label-width="200px" :inline-message="true" >
            <el-form-item label="会员续费(续有效时长):" :rules="[{required: true}]">

                <el-radio :value="renewMemberCard" :label="0" @change="change('renewMemberCard',0)">不可续费</el-radio>
                <el-radio :value="renewMemberCard" :label="1" @change="change('renewMemberCard',1)">可续费</el-radio>

            </el-form-item>
            <el-form-item v-if="renewMemberCard===1" class="sub-container">

                    <el-form-item label="续费金额:">
                        <el-radio :value="renewType" :label="0" @change="change('renewType',0)">元</el-radio>
                        <el-radio :value="renewType" :label="1" @change="change('renewType',1)">积分</el-radio>
                        <el-form-item prop="renewNum">
                            <el-input :value="renewNum" size="small" style="width:155px" @input="change('renewNum',$event)"></el-input>
                            <span>{{renewTypeName}}</span>
                        </el-form-item>
                    </el-form-item>
                    <el-form-item label="续费时长:" prop="renewTime">

                        <el-input :value="renewTime" size="small" style="width:155px" @input="change('renewTime',$event)">
                        </el-input>

                        <el-select :value="renewDateType" placeholder="请选择"
                            size="small" style="width: 20%;"
                            @change="change('renewDateType',$event)">
                            <el-option v-for="(item,index) in timeOpt"
                            :key="index"
                            :value="item.value"
                            :label="item.label">
                            </el-option>
                        </el-select>

                        <span>内有效</span>
                    </el-form-item>

            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
  props: {
    renewMemberCard: {
      type: Number,
      default: 0
    },
    renewType: {
      type: Number,
      default: 0
    },
    renewDateType: {
      type: String,
      default: null
    },
    renewNum: {
      type: Number,
      default: null
    },
    renewTime: {
      type: Number,
      default: null
    }
  },
  watch: {
  },
  computed: {
    renewTypeName () {
      return this.renewType === 0 ? '元' : '积分'
    }
  },
  data () {
    return {
      // 续费数值
      num: null,
      // 续费时常
      time: null,
      // 时间选项
      timeOpt: [
        {
          value: 'day',
          label: '日'
        },
        {
          value: 'week',
          label: '周'
        },
        {
          value: 'month',
          label: '月'
        }
      ],
      myRules: {
        num: [{required: true, message: '请输入', trigger: 'blur'}],
        time: [
          {message: '请输入', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    changeNum () {
      this.$emit('update:renewNum', this.num)
    },
    changeTime (val) {
      console.log(val)
      this.$emit('update:renewTime', this.time)
    },

    change (key, val) {
      console.log(key, val)
      if (val === '') {
        val = null
      } else {
        val = isNaN(Number(val)) ? val : Number(val)
      }

      this.$emit(`update:${key}`, val)
    }

  }
}
</script>
<style scoped lang="scss">
    .container{
        margin-bottom: 22px;
        position: relative;
        /deep/ .el-form-item {
            margin-bottom: 2px;
        }
        .sub-container{
            margin-left: -106px;
        }
    }
</style>
