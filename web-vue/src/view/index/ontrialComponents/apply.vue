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
        >
          <el-form-item
            label="姓名"
            prop="username"
          >
            <el-input
              :placeholder="$t('apply.placeholder_name')"
              v-model="ruleForm.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="电话"
            prop="phonenum"
          >
            <el-input
              :placeholder="$t('apply.placeholder_name')"
              v-model.number="ruleForm.phonenum"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <div class="companyName">
            <el-form-item label="公司">
              <el-input
                placeholder="请填写公司名称"
                v-model="ruleForm.companyName"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </div>

        </el-form>

        <!-- <div class="input_icon">
          <input
            type="contact"
            :placeholder="$t('apply.placeholder_name')"
            name="contact"
            v-model="username"
          >
          <span>{{$t('apply.name')}}</span>
        </div>
        <div class="input_icon">
          <input
            type="mobile"
            :placeholder="$t('apply.placeholder_tel')"
            name="mobile"
            onkeyup="value=value.replace(/[^\d\-]/g,'')"
            v-model="phonenum"
          >
          <span>{{$t('apply.mobile')}}</span>
        </div> -->
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
      rules: {
        username: [{ required: true, message: '请填写您的姓名', trigger: 'blur' }],
        phonenum: [
          { type: 'number', message: '请填写您的手机号' },
          { required: true, message: '手机号不能为空' }
        ],
        companyName: [{ message: '请填写您的公司名称', trigger: 'blur' }]
      }
    }
  },
  mounted () {
    this.$http.$emit('to_rej')
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
</style>
