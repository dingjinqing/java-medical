<template>
  <div class="applyMobile">
    <div
      class="top"
      :style="'background:url('+$imageHost+'/image/admin/apply_fortrial_bg2.jpg) no-repeat;background-size: 100% 100%'"
    >
    </div>
    <div class="formContainer">
      <div class="main">
        <div class="title">{{$t('indexApply.mobileTitle')}}</div>
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
                :placeholder="$t('apply.companyPlaceHolder')"
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
    </div>
    <div class="middleImg">
      <img :src="$imageHost+'/image/admin/new_apply_mobile/function_great.png'">
    </div>
    <div class="coreMain">
      <div
        class="coreList"
        v-for="(item,index) in coreData"
        :key="index"
      >
        <div class="img">
          <img :src="item.imgUrl">
        </div>
        <p>{{item.name}}</p>
        <h4>{{item.contentOne}}</h4>
        <h4>{{item.contentTow}}</h4>
      </div>

    </div>
    <div class="caseShow">
      <img :src="$imageHost+'/image/admin/new_apply_mobile/khal.png'">
    </div>
    <div
      class="customer"
      v-for="(item,index) in customerData"
      :key="index"
    >
      <div class="customerImg">
        <img :src="item.imgUrl">
      </div>
      <p>{{item.contentOne}}</p>
      <h4>{{item.contentTwo}}</h4>
    </div>
    <div
      class="footer"
      :style="'background:url('+$imageHost+'/image/admin/new_apply_mobile/oval.png) no-repeat;background-size: 100% 100%'"
    >
      <div class="content">
        <p
          :class="item.class"
          v-for="(item,index) in footerPData"
          :key="index"
        >
          {{item.text}}
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import { applyrequest } from '@/api/index/apply.js'
export default {
  data () {
    return {
      ruleForm: {
        username: '',
        phonenum: '',
        companyName: ''
      },
      columnFlag: false
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
    },
    coreData () {
      return this.$t('indexApply.coreData')
    },
    customerData () {
      return this.$t('indexApply.customerData')
    },
    footerPData () {
      return this.$t('indexApply.footerPData')
    }
  },
  mounted () {
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
    }
  }
}
</script>
<style lang="scss" scoped>
.applyMobile {
  position: absolute;
  right: 0;
  left: 0;
  top: 0;
  bottom: 0;
  overflow-y: auto;
  background-color: #e5e9f2;
  .top {
    height: 7.05rem;
  }
  .formContainer {
    display: flex;
    justify-content: center;
    background: #dee8ff;
    padding-bottom: 5%;
    .main {
      background: #fff;
      width: 90%;
      border-radius: 8px;
      display: flex;
      justify-content: center;
      flex-direction: column;
      position: relative;
      align-items: center;
      margin-bottom: 30px;
      .title {
        color: #333;
        font-size: 16px;
        height: 60px;
        line-height: 60px;
        text-align: center;
      }
      .demo-ruleForm {
        width: 300px;
        padding-bottom: 20px;
        /deep/ .el-form-item {
          display: flex;
          /deep/ .el-form-item__label {
            width: 60px !important;
            white-space: nowrap;
          }
          /deep/ .el-form-item__content {
            margin-left: 0 !important;
            width: 100%;
          }
        }
        .companyName {
          margin-left: -8px;
        }
      }
      .enFormStyle {
        /deep/ .el-form-item {
          /deep/ .el-form-item__label {
            width: 130px !important;
          }
        }
        .companyName {
          /deep/ .el-form-item {
            /deep/ .el-form-item__label {
              width: 146px !important;
            }
          }
        }
      }

      .p_apply_submit {
        width: 80%;
        height: 40px;
        line-height: 40px;
        color: #fff;
        background: -webkit-linear-gradient(left, #5a8bff, #28a6ff);
        border-radius: 20px;
        margin: 0 auto;
        text-align: center;
        cursor: pointer;
        font-size: 14px;
        position: absolute;
        bottom: -20px;
      }
    }
  }
  .middleImg {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: -15px;
    img {
      width: 50%;
    }
  }
  .coreMain {
    background: #fff;
    width: 90%;
    margin: 0 auto;
    border-radius: 8px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    position: relative;
    margin-top: 20px;
    padding: 10px 0px 30px 0px;
    margin-bottom: 10px;
    .coreList {
      width: 49%;
      display: flex;
      justify-content: center;
      flex-direction: column;
      margin-top: 10px;
      .img {
        display: flex;
        justify-content: center;
        img {
          width: 50%;
        }
      }
      p {
        font-size: 0.3rem;
        color: #333;
        text-align: center;
        margin-top: 5px;
      }
      h4 {
        color: #666;
        font-size: 0.25rem;
        text-align: center;
        margin-top: 5px;
      }
    }
  }
  .caseShow {
    margin-top: 30px;
    display: flex;
    justify-content: center;
    img {
      width: 50%;
    }
  }
  .customer {
    width: 90%;
    margin: 0 auto;
    margin-top: 20px;
    background: #fff;
    padding: 10px;
    margin-bottom: 10px;
    border-radius: 8px;
    position: relative;
    z-index: 3;
    .customerImg {
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        width: 50%;
      }
    }
    p {
      font-size: 0.3rem;
      color: #333;
      text-align: center;
      margin-top: 5px;
      font-weight: 600;
    }
    h4 {
      color: #666;
      font-size: 0.25rem;
      text-align: center;
      margin-top: 5px;
      margin-bottom: 5px;
    }
  }
  .footer {
    height: 4.5rem;
    margin-top: -80px;
    display: flex;
    justify-content: flex-end;
    flex-direction: column;
    align-items: center;
    padding-bottom: 30px;
    .content {
      p {
        text-align: center;
        width: 100%;
      }
      .p1 {
        color: #fff;
        font-size: 0.3rem;
        text-align: center;
        margin-top: 10px;
        margin-bottom: 10px;
        font-weight: 600;
      }
      .p2 {
        color: #fff;
        font-size: 0.25rem;
        text-align: center;
        margin-top: 5px;
        margin-bottom: 10px;
      }
    }
  }
}
</style>
