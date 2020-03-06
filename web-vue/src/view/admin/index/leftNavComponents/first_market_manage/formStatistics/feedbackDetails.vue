<template>
  <div class="feedbackDetails">
    <div class="feedbackDetailsMain">
      <div class="top">
        <div class="navActive">
          <div class="nav">
            <a href="#">
              <span class="wait_re">反馈详情 </span>
            </a>
          </div>
        </div>
      </div>
      <div class="reserve-list-table">
        <table
          width="80%"
          style="border-color:#eee;text-align: center;"
        >
          <tbody>
            <tr>
              <td width="40%">姓名</td>
              <td
                width="60%"
                class="comm_message"
              >
                {{name}}
              </td>
            </tr>
            <tr>
              <td width="40%">手机号</td>
              <td
                width="60%"
                class="comm_message"
              >
                {{phoneNum}}
              </td>
            </tr>
            <tr>
              <td width="40%">省/市/区</td>
              <td
                width="60%"
                class="comm_message"
              >
                北京市北京市东城区
              </td>
            </tr>
            <tr>
              <td width="40%">邮箱</td>
              <td
                width="60%"
                class="comm_message"
              >
                {{email}}
              </td>
            </tr>
            <tr>
              <td width="40%">性别</td>
              <td
                width="60%"
                class="comm_message"
              >
                <div class="sex">
                  <el-radio
                    disabled
                    v-model="radio"
                    label="1"
                  >男</el-radio>
                  <el-radio
                    v-model="radio"
                    label="2"
                  >女</el-radio>
                </div>
              </td>
            </tr>
            <tr>
              <td width="40%">下拉</td>
              <td
                width="60%"
                class="comm_message"
              >
                <div
                  :style="slideIndex === item?'color:#0E70CA;margin-bottom:5px':'margin-bottom:5px'"
                  v-for="(item,index) in slideArr"
                  :key="index"
                >
                  ·{{item}}
                </div>
                <!-- <span style="font-weight: bolder;display:inline-block;margin-bottom:5px">·</span><span>{{slideArr[0]}}</span>
                <br>
                <span style="font-weight: bolder">·</span><span style="color: #0E70CA">{{slideArr[1]}}</span>
                <br> -->
              </td>
            </tr>
            <tr>
              <td width="40%">输入框</td>
              <td
                width="60%"
                class="comm_message"
              >
                {{inputVal}}
              </td>
            </tr>
            <tr>
              <td width="40%">选项</td>
              <td
                width="60%"
                class="comm_message"
              >
                <div
                  v-for="(item,index) in choiseArr"
                  :key="index"
                  :style="chooseIndex === index?'color:#0E70CA;margin-bottom:5px':'margin-bottom:5px'"
                >
                  ·{{item}}
                </div>
                <!-- <span :class="choose === '1'?'choose':''" style="font-weight: bolder;display:inline-block;margin-bottom:5px">·</span><span>选项1</span>
                <br>
                <span :class="choose === '2'?'choose':''" style="font-weight: bolder">·</span><span style="color: #0E70CA">选项2</span>
                <br> -->
              </td>
            </tr>
            <tr>
              <td width="40%">日期</td>
              <td
                width="60%"
                class="comm_message"
              >
                {{date}}
              </td>
            </tr>
            <tr>
              <td width="40%">图片上传</td>
              <td
                width="60%"
                class="comm_message"
              >
                {{picture}}
              </td>
            </tr>
            <tr>
              <td width="40%">视频上传</td>
              <td
                width="60%"
                class="comm_message"
              >
                {{video}}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
import { feedBackListDetailQuery } from '@/api/admin/marketManage/formDecoration'
export default {
  data () {
    return {
      radio: '2',
      name: '', // 姓名
      phoneNum: '', // 手机号
      email: '', // 邮箱
      sexArr: [], // 性别
      slideArr: [], // 下拉框
      slideIndex: '', // 下拉选中项
      inputVal: '', // 输入框
      choiseArr: [], // 选项
      chooseIndex: '', // 选中下标
      date: '', // 日期
      picture: '', // 图片上传
      video: '' // 视频模块
    }
  },
  mounted () {
    console.log(this.$route)
    // 初始化请求数据
    this.handleToInitQuery()
  },
  methods: {
    // 初始化请求数据
    handleToInitQuery () {
      if (this.$route.query) {
        let { pageId, userId } = this.$route.query
        feedBackListDetailQuery({ pageId: pageId, userId: userId }).then(res => {
          console.log(res)
          if (res.error === 0) {
            res.content.forEach((item, index) => {
              switch (item.moduleName) {
                case 'm_input_name':
                  this.name = item.moduleValue
                  break
                case 'm_input_mobile':
                  this.phoneNum = item.moduleValue
                  break
                case 'm_address':
                  break
                case 'm_input_email':
                  this.email = item.moduleValue
                  break
                case 'm_sex':
                  if (item.moduleValue === item.moduleValueList[0]) {
                    this.radio = '1'
                  } else {
                    this.radio = '2'
                  }
                  this.sexArr = item.moduleValueList
                  break
                case 'm_slide':
                  this.slideIndex = item.moduleValue
                  this.slideArr = item.moduleValueList
                  break
                case 'm_input_text':
                  this.inputVal = item.moduleValue
                  break
                case 'm_choose':
                  this.chooseIndex = Number(item.moduleValue) - 1
                  console.log(this.chooseIndex)
                  this.choiseArr = item.moduleValueList
                  break
                case 'm_dates':
                  this.date = item.moduleValue
                  break
                case 'm_imgs':
                  //   this.picture =
                  break
                case 'm_upload_video':
                  //   this.video =
                  break
              }
            })
          }
        })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.feedbackDetails {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .feedbackDetailsMain {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 10px 25px;
    .top {
      margin-bottom: 15px;
      border-bottom: 1px solid #eee;
      .navActive {
        height: 50px;
        line-height: 50px;
        display: flex;
        .nav {
          border-bottom: 2px solid #5a8bff;
          padding: 0 15px;
          a {
            text-decoration: none;
            color: #333;
            cursor: pointer;
          }
        }
      }
    }
    .reserve-list-table {
      tbody {
        margin-bottom: 15px;
        border: 1px solid #eee;
        td {
          text-align: left;
          padding: 8px 10px;
          border: 1px solid #eee;
          vertical-align: middle !important;
          .sex {
            display: flex;
          }
        }
      }
    }
  }
}
</style>
