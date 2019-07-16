<template>
<div style="overflow:auto;width:100%">
<el-form ref="form" :model="form" label-width="100px" @submit.prevent="onSubmit" style="margin:20px;width:20%;min-width:200px;"
  label-position: left
>
  <el-form-item label="用户名">
    <el-input v-model="form.username"></el-input>
  </el-form-item>
  <el-form-item label="密码">
    <el-input v-model="form.password"></el-input>
  </el-form-item>
  <el-form-item label="昵称">
    <el-input v-model="form.nickname"></el-input>
  </el-form-item>
  <el-form-item label="审核状态">
    <el-select v-model="form.state" placeholder="请选择状态">
      <el-option label="申请中" value="on"></el-option>
      <el-option label="审核通过" value="pass"></el-option>
      <el-option label="审核不通过" value="off"></el-option>
      <el-option label="已禁用" value="no"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="最大SKU数量">
    <el-input v-model="form.skunumber"></el-input>
  </el-form-item>
  <el-form-item label="最大店铺数量">
    <el-input v-model="form.shopnumber"></el-input>
  </el-form-item>
  <el-form-item label="首次续费时间">
    <div class="block">
      <el-date-picker
        v-model="value1"
        type="datetime"
        placeholder="选择日期时间">
      </el-date-picker>
    </div>
  </el-form-item>
  <el-form-item label="到期时间">
    <div class="block">
      <el-date-picker
        v-model="value2"
        type="datetime"
        placeholder="选择日期时间">
      </el-date-picker>
    </div>
  </el-form-item>
   <el-form-item label="手机号">
    <el-input v-model="form.phone"></el-input>
  </el-form-item>
   <el-form-item label="公司名称">
    <el-input v-model="form.company"></el-input>
  </el-form-item>
   <el-form-item label="销售员">
    <el-input v-model="form.salesman"></el-input>
  </el-form-item>
  <el-form-item label="地理位置">
    <el-switch on-text="" off-text=""></el-switch>
  </el-form-item>
  <el-form-item label="详细地址">
    <el-input v-model="form.address"></el-input>
  </el-form-item>
  <el-form-item label="初始销量">
    <el-switch
      active-text="已开启"
      inactive-text="已关闭"
      active-color="#f7931e"
      inactive-color="#ddd"
      v-model="form.state">
    </el-switch>
  </el-form-item>
  <el-form-item label="添加评价">
    <el-switch
      active-text="已开启"
      inactive-text="已关闭"
      active-value="1"
      inactive-value="0"
      active-color="#f7931e"
      inactive-color="#ddd"
      v-model="form.delivery"
      @change="changeSwitch(form.delivery)"
      >
    </el-switch>
  </el-form-item>
</el-form>
</div>
</template>

<script>
export default {
  data () {
    return {
      form: {
        username: '',
        password: '',
        nickname: '',
        skunumber: '',
        shopnumber: '',
        phone: '',
        company: '',
        salesman: '',
        address: '',
        delivery: false,
        state: false
      },
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      value1: '',
      value2: ''
    }
  },
  methods: {
    onSubmit () {
      console.log('submit!')
    }
  }
}

</script>
<style scoped>
  .el-switch__label {
    position: absolute;
    display: none;
    color: #fff;
  }
  .el-switch__label--right {
    z-index: 1;
    right: 5px;
  }
  .el-switch__label--left {
    z-index: 1;
    right: 10px;
  }
  .el-switch__label .is-active {
    display: block;
  }
  label {
    text-align: left !important;
  }
</style>
