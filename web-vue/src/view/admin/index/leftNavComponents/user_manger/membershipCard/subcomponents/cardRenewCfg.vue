<template>
    <div class="container">
        <el-form :model="$data" :rules="myRules" label-width="200px" :inline-message="true" >
            <el-form-item label="会员续费(续有效时长):" :rules="[{required: true}]">
                <el-radio v-model="renewMemberCard" :label="0">不可续费</el-radio>
                 <el-radio v-model="renewMemberCard" :label="1">可续费</el-radio>
            </el-form-item>
            <el-form-item v-if="renewMemberCard===1" class="sub-container">

                    <el-form-item label="续费金额:">
                        <el-radio v-model="renewType" :label="0">元</el-radio>
                        <el-radio v-model="renewType" :label="1">积分</el-radio>
                        <el-form-item prop="renewNum">
                            <el-input v-model="renewNum" size="small" style="width:155px"></el-input>
                            <span>{{renewTypeName}}</span>
                        </el-form-item>
                    </el-form-item>
                    <el-form-item label="续费时长:" prop="renewTime">
                        <el-input v-model="renewTime" size="small" style="width:155px"></el-input>
                        <el-select v-model="renewDateType" placeholder="请选择" size="small" style="width: 20%;">
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
      type: Number,
      default: null
    }
  },
  computed: {
    renewTypeName () {
      return this.renewType === 0 ? '元' : '积分'
    }
  },
  data () {
    return {
      // 续费数值
      renewNum: null,
      // 续费时常
      renewTime: null,
      // 时间选项
      timeOpt: [
        {
          value: 0,
          label: '日'
        },
        {
          value: 1,
          label: '周'
        },
        {
          value: 2,
          label: '月'
        }
      ],
      myRules: {
        renewNum: [{required: true, message: '请输入', trigger: 'blur'}],
        renewTime: [
          {message: '请输入', trigger: 'blur'}
        ]
      }
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
