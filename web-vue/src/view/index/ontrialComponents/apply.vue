<template>
  <div class="p_apply">
    <div class="p_apply_free">
      <div class="apply_left">
        <div class="tiao"></div>
        <h5 class="p_h2">{{$t('apply.title')}}</h5>

        <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
          :class="columnFlag?'enFormStyle':''"
        >
          <el-form-item
            :label="$t('indexApply.name')"
            prop="username"
          >
            <el-input
              :placeholder="$t('apply.placeholder_name')"
              v-model="ruleForm.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            :label="$t('indexApply.phonenum')"
            prop="phonenum"
          >
            <el-input
              :placeholder="$t('apply.placeholder_name')"
              v-model.number="ruleForm.phonenum"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <div class="companyName">
            <el-form-item :label="$t('indexApply.companyName')">
              <el-input
                :placeholder="$t('indexApply.companyPlaceHolder')"
                v-model="ruleForm.companyName"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </div>

        </el-form>
        <div
          class="p_apply_submit"
          @click="handlesubmit()"
        >{{$t('apply.submit')}}</div>
      </div>
      <div class="apply_right">
        <img
          :src="imageUrlData"
          alt=""
        >
        <h3>{{$t('apply.content')}}</h3>
        <h4>{{$t('apply.content_bottom')}}</h4>
      </div>
    </div>
  </div>
</template>
<script>
import { applyrequest } from '@/api/index/apply.js'
export default {
  data () {
    return {
      imageUrlData: this.$imageHost + '/image/admin/index_circle.png',
      ruleForm: {
        username: '',
        phonenum: '',
        companyName: ''
      },
      columnFlag: null
    }
  },
  computed: {
    rules () {
      let rules = {
        username: [{ required: true, message: this.$t('indexApply.fillName'), trigger: 'blur' }],
        phonenum: [
          { type: 'number', message: this.$t('indexApply.fillNumber') },
          { required: true, message: this.$t('indexApply.mobileNumberEmpty') }
        ],
        companyName: [{ message: this.$t('indexApply.fillCompanyName'), trigger: 'blur' }]

      }
      return rules
    }
  },
  mounted () {
    this.$http.$emit('to_rej')
    // 初始化判断打开的设备
    this.isMobile()
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 提交表单
    handlesubmit () {
      localStorage.setItem('contentType', 'application/json;charset=UTF-8')
      let obj = {
        contact: this.ruleForm.username,
        mobile: this.ruleForm.phonenum,
        company: this.ruleForm.companyName
      }
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          applyrequest(obj).then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.$message.success({
                showClose: true,
                message: '提交申请成功，请等待业务员联系',
                type: 'success'
              })
              this.$refs['ruleForm'].resetFields()
              this.ruleForm.companyName = ''
            } else {
              this.$message.error({
                showClose: true,
                message: res.message,
                type: 'error'
              })
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 判断当前打开页面的设备
    isMobile () {
      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      console.log(flag)
      if (flag) {
        console.log('触发')
        this.$router.push({ name: 'applyMobile' })
      }
    }
  }
}
</script>
<style scoped>
.p_apply {
  background: url(../../../assets/applyImg/apply_back.png) no-repeat;
  padding: 45px 0;
}
.p_apply_free {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: #666;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(219, 188, 245, 0.35);
  padding: 20px;
  width: 1000px;
  margin: 0 auto;
}
.apply_left,
.apply_right {
  width: 50%;
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.tiao {
  border: 1px solid #5a8bff;
  width: 20px;
  left: -140px;
  position: relative;
  margin-bottom: 5px;
}
.p_h2 {
  font-size: 18px;
  color: #333;
  margin-bottom: 25px;
  width: 300px;
  font-weight: normal;
}
.input_icon {
  position: relative;
}
.apply_left input {
  width: 300px;
  height: 50px;
  margin-bottom: 20px;
  padding-left: 70px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
.input_icon span {
  font-size: 14px;
  display: inline-block;
  width: 55px;
  height: 16px;
  line-height: 16px;
  color: #333;
  position: absolute;
  text-align: center;
  top: 17px;
  left: 5px;
  border-right: 1px solid #ccc;
}
.p_apply_free h3 {
  font-size: 16px;
  color: #333;
  margin-bottom: 5px;
  margin-top: 5px;
}
.p_apply_free h4 {
  font-size: 14px;
  color: #666;
}
.p_apply_submit {
  width: 300px;
  height: 40px;
  line-height: 40px;
  color: #fff;
  background: #5a8bff;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  margin: 0 auto;
  text-align: center;
  cursor: pointer;
}
</style>
<style lang="scss" scoped>
.demo-ruleForm {
  width: 300px;
  /deep/ .el-form-item {
    display: flex;
    /deep/ .el-form-item__label {
      width: auto !important;
      white-space: nowrap;
    }
    /deep/ .el-form-item__content {
      margin-left: 0 !important;
      width: 100%;
    }
  }
  .companyName {
    /deep/ .el-form-item__label {
      padding-left: 10px;
    }
  }
}
.enFormStyle {
  /deep/ .el-form-item {
    /deep/ .el-form-item__label {
      width: 130px !important;
    }
  }
}
</style>
