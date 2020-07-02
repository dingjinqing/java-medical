<template>
    <div class="doctorInfo">
      <div
        v-if='reload'
        id='doctorDiv'
        class="doctorContent"
      >
        <!-- 添加内容 -->
        <el-form
          ref='doctorForm'
          :model='doctorFormInfo'
          :rules='doctorFormRules'
          label-width="150px"
          size="small"
          label-suffix="："
        >
          <el-form-item
            label='注册医院'
            prop='registeredHospital'
          >
            <span>医院名称</span>
          </el-form-item>
          <el-form-item
            label='医生姓名'
            prop='doctorName'
          >
            <el-input
              v-model="doctorFormInfo.doctorName"
              placeholder="请输入医生姓名"
            ></el-input>
          </el-form-item>
          <el-form-item
            label='医生院内编号'
            prop='doctorHospitalNumber'
          >
            <el-input
              v-model="doctorFormInfo.doctorHospitalNumber"
              placeholder="请输入医生院内编号"
            ></el-input>
          </el-form-item>
          <el-form-item
            label='医生资格编码'
            prop='doctorQualNumber'
          >
            <el-input
              v-model="doctorFormInfo.doctorQualNumber"
              placeholder="请输入医生资格编码"
            ></el-input>
          </el-form-item>
          <el-form-item
            label='医生执业编码'
            prop='doctorPracticeNumber'
          >
            <el-input
              v-model="doctorFormInfo.doctorPracticeNumber"
              placeholder="请输入医生执业编码"
            ></el-input>
          </el-form-item>
          <el-form-item
            label='医生职称'
            prop='doctorJobTitle'
          >
            <el-select
              v-model="doctorFormInfo.doctorJobTitle"
              placeholder="请选择医生职称"
            >
              <el-option
                v-for="item in doctorJobTitles"
                :key="item.titleId"
                :label="item.titleName"
                :value="item.titleId"
                ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label='所属科室'
            prop='belongedDepartment'
          >
            <el-select
              v-model="doctorFormInfo.belongedDepartment"
              placeholder="请选择所属科室"
            >
              <el-option
                v-for="item in belongedDepartments"
                :key="item.partId"
                :label="item.partName"
                :value="item.partId"
                ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label='性别'
            prop='gender'
          >
            <el-radio-group v-model="doctorFormInfo.gender">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
                <el-radio :label="-1">未知</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item
            label='手机号'
            prop='mobile'
          >
            <el-input
              v-model.number="doctorFormInfo.mobile"
              placeholder="请输入手机号码"
            ></el-input>
          </el-form-item>
          <el-form-item
            label='子账号名'
            prop='childAccount'
          >
            <el-input
              v-model="doctorFormInfo.childAccount"
              placeholder="请输入子账号名"
            ></el-input>
          </el-form-item>
          <el-form-item
            label='密码'
            prop='password'
          >
            <el-input
              type='password'
              v-model="doctorFormInfo.password"
              placeholder="请输入子账号密码(6-12字符)"
            ></el-input>
          </el-form-item>
          <el-form-item
            label='确认密码'
            prop='checkPassword'
          >
            <el-input
              type='password'
              v-model="doctorFormInfo.checkPassword"
              placeholder="请确认子账号密码(6-12字符)"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="addDoctorFooter">
        <el-button
          type="primary"
          size="small"
          @click="handleSubmit"
        >保存</el-button>
      </div>
    </div>
</template>

<script>
export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入子账号密码'))
      } else if (value.length < 6 || value.length > 12) {
        callback(new Error('请输入6-12个字符'))
      } else {
        if (this.doctorFormInfo.checkPassword !== '') {
          this.$refs.doctorForm.validateField('checkPassword')
        }
        callback()
      }
    }
    var validatePassCheck = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请再次输入子账号密码'))
      } else if (value !== this.doctorFormInfo.password) {
        callback(new Error('两次密码不一致'))
      } else {
        callback()
      }
    }
    return {
      reload: true,
      doctorFormInfo: {
        doctorName: '',
        doctorHospitalNumber: '',
        doctorQualNumber: '',
        doctorPracticeNumber: '',
        doctorJobTitle: '',
        belongedDepartment: '',
        gender: 1,
        mobile: '',
        childAccount: '',
        password: '',
        checkPassword: ''
      },
      doctorFormRules: {
        doctorName: [{required: true, message: '请输入医生姓名', trigger: 'blur'}],
        doctorHospitalNumber: [{required: true, message: '请输入医生院内编号', trigger: 'blur'}],
        doctorQualNumber: [{required: true, message: '请输入医生资格编码', trigger: 'blur'}],
        doctorPracticeNumber: [{required: true, message: '请输入医生执业编码', trigger: 'blur'}],
        doctorJobTitle: [{required: false, message: '请选择医生职称', trigger: 'change'}],
        belongedDepartment: [{required: false, message: '请选择医生所属科室', trigger: 'change'}],
        gender: [{required: true, message: '请选择医生性别', trigger: 'change'}],
        mobile: [
          {required: true, message: '请填写手机号', trigger: 'blur'},
          {type: 'number', message: '请填写数字', trigger: 'blur'}
        ],
        childAccount: [{required: true, message: '请输入子账号名称', trigger: 'blur'}],
        password: [{required: true, validator: validatePass, trigger: 'blur'}],
        checkPassword: [{required: true, validator: validatePassCheck, trigger: 'blur'}]
      },
      doctorJobTitle: {},
      belongedDepartments: {}
    }
  },
  methods: {
    handleSubmit () {
      let that = this
      that.$refs.doctorForm.validate((valid) => {
        if (valid) {
          this.$message.success({
            message: 'success'
          })
          this.$router.push({ name: 'doctorList' })
        } else {
          alert('err submit')
        }
      })
    }
  }
}
</script>

<style scoped lang='scss'>
.doctorInfo{
  padding: 10px 10px;
  overflow-y: auto;
  .doctorContent{
    background-color: white;
    padding: 10px 10px 100px;
    .el-input{
      width: 300px;
    }
  }
  .addDoctorFooter{
    width: calc(100% - 150px);
    transform: translateX(150px);
    background: #f8f8fa;
    text-align: center;
    box-sizing: border-box;
    height: 50px;
    padding-top: 10px;
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 2;
  }
}
</style>
