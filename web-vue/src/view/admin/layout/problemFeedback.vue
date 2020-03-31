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
                  @click="handleToClick(index)"
                  :style="index === ruleForm.feedBackTypt?'border: 1px solid rgb(90, 139, 255);':''"
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
            <el-form-item
              label="问题"
              prop="questions"
            >
              <div>
                <el-input
                  type="textarea"
                  v-model="ruleForm.questions"
                  placeholder="请描述您遇到的问题"
                  :maxlength="200"
                  :minlength="20"
                  show-word-limit
                ></el-input>
              </div>
            </el-form-item>
          </el-form>
          <!--图片-->
          <div class="imgContaner">
            <div
              class="selectImg"
              v-for="(item,index) in imgSlectArr"
              :key="index"
            >
              <img :src="item.imgUrl">
              <img
                :src="`${$imageHost}/image/admin/icon_delete.png`"
                class="goodImgDelete"
                style="display: inline-block;"
                @click="handleToDelete(index)"
              >
            </div>
            <div
              @click="handleToAddImg()"
              class="list addImg"
              v-if="imgSlectArr.length<5"
            >
              <img :src="`${$imageHost}/image/admin/add_img_bg.png`">
            </div>

          </div>
          <!--练习电话-->
          <div class="phone">
            <div class="phoneLeft">联系电话：</div>
            <div class="phoneRight">
              <el-input
                v-model="phoneNum"
                placeholder="请填写您的手机号"
              ></el-input>
              <span class="tips">
                为方便我们尽快把结果反馈给您，请留下您的联系方式
              </span>
              <el-button
                @click="handleToSubmit()"
                type="primary"
              >提交</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex="pictureSpace"
      :tuneUp="imgTuneUp"
      :imageSize=[800,800]
      @handleSelectImg="handleSelectImg"
    />
  </div>
</template>
<script>
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog') // 选择图片弹窗
  },
  data () {
    var validatePass = (rule, value, callback) => {
      console.log(value.length)
      if (value.length < 20) {
        callback(new Error('问题描述最少输入20字'))
      } else {
        callback()
      }
    }
    return {
      imgTuneUp: false, // 选择图片弹窗调起
      imgSlectArr: [], // 已选择图片数据
      phoneNum: '', // 手机号
      feedBackTyptArr: ['产品建议', '网页异常', '功能使用咨询', '其他'],
      ruleForm: {
        feedBackTypt: '',
        questions: ''
      },
      rules: {
        feedBackTypt: [{ required: true, message: '没有选择问题类型', trigger: 'blur' }],
        questions: [{ required: true, validator: validatePass, trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 点击问题子项
    handleToClick (index) {
      this.ruleForm.feedBackTypt = index
    },
    // 点击添加图片
    handleToAddImg () {
      this.imgTuneUp = !this.imgTuneUp
    },
    // 选中图片返回数据
    handleSelectImg (res) {
      console.log(res)
      this.imgSlectArr.push(res)
    },
    // 点击删除icon
    handleToDelete (index) {
      this.imgSlectArr.splice(index, 1)
    },
    // 点击提交
    handleToSubmit () {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          alert('submit!')
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
            &:hover {
              border: 1px solid rgb(90, 139, 255);
            }
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
  /deep/ .el-textarea .el-textarea__inner {
    // width: 700px;
    height: 250px;
    resize: none;
  }
  .imgContaner {
    display: flex;
    padding-left: 100px;
    .list {
      border: 1px solid #ccc;
      width: 80px;
      height: 81px;
      text-align: center;
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        max-width: 100%;
        max-height: 100%;
      }
    }
    .addImg {
      cursor: pointer;
    }
    .selectImg {
      margin-right: 15px;
      background: #f7f7f7;
      border: 1px solid #ccc;
      width: 80px;
      height: 80px;
      text-align: center;
      position: relative;
      img {
        max-width: 100%;
        max-height: 100%;
      }
    }
    .goodImgDelete {
      position: absolute;
      right: -10px;
      top: -7px;
      cursor: pointer;
    }
  }
  .phone {
    margin-top: 20px;
    display: flex;
    /deep/ .el-input__inner {
      width: auto;
    }
    .phoneLeft {
      padding-top: 10px;
    }
    .phoneRight {
      display: flex;
      flex-direction: column;
      /deep/ .el-button {
        max-width: max-content;
      }
      .tips {
        margin: 10px 0;
      }
    }
  }
}
</style>
