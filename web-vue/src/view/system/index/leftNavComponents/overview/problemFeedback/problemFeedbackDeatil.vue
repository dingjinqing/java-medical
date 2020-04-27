<template>
  <div class="problemFeedbackDeatil">
    <div class="problemFeedbackDeatilMain">
      <div class="list">
        <span>客户登录账号名称：</span>
        <el-input
          size="small"
          disabled
          v-model="accountNumber"
        ></el-input>
      </div>
      <div class="list">
        <span>登录账号手机号：</span>
        <el-input
          size="small"
          disabled
          v-model="phoneNum"
        ></el-input>
      </div>
      <div class="list">
        <span>填写手机号：</span>
        <el-input
          size="small"
          disabled
          v-model="fillMobileNumber"
        ></el-input>
      </div>
      <div class="list">
        <span>反馈时间：</span>
        <el-input
          size="small"
          disabled
          v-model="feedbackTime"
        ></el-input>
      </div>
      <div class="list">
        <span>类型：</span>
        <el-input
          size="small"
          disabled
          v-model="questionType"
        ></el-input>
      </div>
      <div class="list">
        <span>使用版本：</span>
        <el-input
          size="small"
          disabled
          v-model="useVersion"
        ></el-input>
      </div>
      <div class="list">
        <span>问题：</span>
        <el-input
          size="small"
          type="textarea"
          v-model="questionContent"
          disabled
        >
        </el-input>
      </div>
      <div class="list">
        <span>图片：</span>
        <div
          class="display:flex"
          v-if="img.length"
        >
          <img
            v-for="(item,index) in img"
            :key="index"
            :src="item"
            style="margin-right:10px"
          >
        </div>
        {{img.length?'':'无'}}
      </div>
    </div>

  </div>
</template>
<script>
import { problemFeedbackDetail } from '@/api/system/problemFeedback/problemFeedback.js'
export default {
  data () {
    return {
      accountNumber: '',
      phoneNum: '',
      fillMobileNumber: '',
      feedbackTime: '',
      questionType: '',
      useVersion: '',
      questionContent: '',
      img: []
    }
  },
  mounted () {
    // 初始化数据
    this.handleToInit()
  },
  methods: {
    // 初始化数据
    handleToInit () {
      problemFeedbackDetail(this.$route.query.id).then(res => {
        console.log(res)
        if (res.error === 0) {
          let { submitUser, submitUserPhone, mobile, createTime, content, version, categoryId, imageUrls } = res.content
          this.accountNumber = submitUser
          this.phoneNum = submitUserPhone
          this.fillMobileNumber = mobile
          this.feedbackTime = createTime
          this.questionContent = content
          this.useVersion = version
          switch (categoryId) {
            case 1:
              this.questionType = '产品建议'
              break
            case 2:
              this.questionType = '网页异常'
              break
            case 3:
              this.questionType = '功能使用咨询'
              break
            case 4:
              this.questionType = '其他'
              break
          }
          this.img = imageUrls
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.problemFeedbackDeatil {
  margin-top: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  .problemFeedbackDeatilMain {
    background-color: #fff;
    min-height: 500px;
    padding: 25px;
    .list {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
      /deep/ .el-input {
        width: auto;
      }
      /deep/ .el-textarea {
        width: auto;
      }
      span {
        white-space: nowrap;
        width: 150px;
        text-align: right;
      }
      img {
        width: 50px;
        height: 48px;
        border-radius: 2px;
        border: 1px solid #ccc;
      }
    }
  }
}
</style>
