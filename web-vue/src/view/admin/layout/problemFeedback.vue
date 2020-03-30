<template>
  <div class="problemFeedback">
    <div class="problemFeedbackMain">
      <div class="main">
        <div class="question_header">
          <span>问题反馈</span>
        </div>
        <div class="question_body">
          <el-form
            :model="ruleForm"
            :rules="rules"
            ref="ruleForm"
            label-width="100px"
            class="demo-ruleForm"
          >
            <el-form-item
              label="类型"
              prop="feedBackTypt"
            >
              <div class="question_label">
                <div
                  class="ql_style"
                  v-for="(item,index) in feedBackTyptArr"
                  :key="index"
                >
                  <span>{{item}}</span>
                  <img
                    v-if="index === ruleForm.feedBackTypt"
                    :src="`${$imageHost}/image/admin/question_gou.png`"
                    class="ql_style_img"
                  >
                </div>
              </div>

            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      feedBackTyptArr: ['产品建议', '网页异常', '功能使用咨询', '其他'],
      ruleForm: {
        feedBackTypt: ''
      },
      rules: {
        feedBackTypt: [{ required: true, message: '没有选择问题类型', trigger: 'blur' }]
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.problemFeedback {
  padding-top: 105px;
  display: flex;
  justify-content: center;
  background-color: #e6e9f0;
  .problemFeedbackMain {
    width: 1000px;
    background-color: #fff;
    padding: 10px;
    display: flex;
    justify-content: center;
    .main {
      width: 850px;
      height: auto;
      min-height: 600px;
      .question_header {
        width: 100%;
        height: 50px;
        background-color: white;
        border-bottom: 1px solid #ddd;
        text-align: center;
        line-height: 50px;
        color: #333;
        font-size: 16px;
      }
      .question_body {
        width: 100%;
        height: auto;
        min-height: 550px;
        padding-top: 20px;
        .question_label {
          width: 500px;
          height: 40px;
          background-color: white;
          float: left;
          margin-left: 15px;
          .ql_style {
            width: 110px;
            height: 40px;
            border: 1px solid #ddd;
            border-radius: 2px;
            background-color: white;
            float: left;
            margin-right: 8px;
            text-align: center;
            line-height: 40px;
            position: relative;
            cursor: pointer;
            span {
              font-size: 14px;
              color: #333;
            }
            .ql_style_img {
              position: absolute;
              right: 0px;
              bottom: 0px;
            }
          }
        }
      }
    }
  }
}
</style>
