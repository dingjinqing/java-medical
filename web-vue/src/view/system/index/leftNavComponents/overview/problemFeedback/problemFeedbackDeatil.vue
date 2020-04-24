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
        <img
          v-if="img"
          :src="img"
        >
        {{!img?'无':''}}
      </div>
    </div>

  </div>
</template>
<script>
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
      img: ''
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
