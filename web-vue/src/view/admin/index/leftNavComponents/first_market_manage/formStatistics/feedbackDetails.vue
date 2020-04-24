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
                <span
                  v-for="(item,index) in name"
                  :key="index"
                  style="margin-right:5px"
                >
                  {{item}}
                </span>
              </td>
            </tr>
            <tr>
              <td width="40%">手机号</td>
              <td
                width="60%"
                class="comm_message"
              >
                <span
                  v-for="(item,index) in phoneNum"
                  :key="index"
                  style="margin-right:5px"
                >
                  {{item}}
                </span>
              </td>
            </tr>
            <tr>
              <td width="40%">省/市/区</td>
              <td
                width="60%"
                class="comm_message"
              >
                <span
                  v-for="(item,index) in address"
                  :key="index"
                  style="margin-right:5px"
                >
                  {{item}}
                </span>
              </td>
            </tr>
            <tr>
              <td width="40%">邮箱</td>
              <td
                width="60%"
                class="comm_message"
              >
                <span
                  v-for="(item,index) in email"
                  :key="index"
                  style="margin-right:5px"
                >
                  {{item}}
                </span>
              </td>
            </tr>
            <tr>
              <td width="40%">性别</td>
              <td
                width="60%"
                class="comm_message"
              >
                <div class="sex">
                  <span
                    v-for="(item,index) in sexArr"
                    :key="index"
                    style="margin-right:20px"
                  >
                    <el-radio
                      :disabled="item.radio!=='1'"
                      v-model="item.radio"
                      label="1"
                    >男</el-radio>
                    <el-radio
                      :disabled="item.radio!=='2'"
                      v-model="item.radio"
                      label="2"
                    >女</el-radio>
                  </span>
                </div>
              </td>
            </tr>
            <tr>
              <td width="40%">下拉</td>
              <td
                width="60%"
                class="comm_message"
              >
                <div class="sex">
                  <span
                    v-for="(item,index) in xlData"
                    :key="index"
                    style="margin-right:10px"
                  >
                    <div
                      :style="item.slideIndex === itemC?'color:#0E70CA;margin-bottom:5px':'margin-bottom:5px'"
                      v-for="(itemC,indexC) in item.slideArr"
                      :key="indexC"
                    >
                      ·{{itemC}}
                    </div>
                  </span>
                </div>
              </td>
            </tr>
            <tr>
              <td width="40%">输入框</td>
              <td
                width="60%"
                class="comm_message"
              >
                <span
                  v-for="(item,index) in inputVal"
                  :key="index"
                >
                  {{item}}
                </span>
              </td>
            </tr>
            <tr>
              <td width="40%">选项</td>
              <td
                width="60%"
                class="comm_message"
              >
                <div class="sex">
                  <span
                    v-for="(item,index) in xxData"
                    :key="index"
                    style="margin-right:10px"
                  >
                    <div
                      v-for="(itemC,indexC) in item.choiseArr"
                      :key="indexC"
                      :style="item.chooseIndex === indexC?'color:#0E70CA;margin-bottom:5px':'margin-bottom:5px'"
                    >
                      ·{{itemC}}
                    </div>

                  </span>
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
                <span
                  v-for="(item,index) in date"
                  :key="index"
                  style="margin-right:10px"
                >
                  {{item}}
                </span>

              </td>
            </tr>
            <tr>
              <td width="40%">图片上传</td>
              <td
                width="60%"
                class="comm_message"
              >
                <img
                  v-for="(item,index) in picture"
                  :key="index"
                  :src="item"
                  style="display: inline-block;margin-right:10px"
                  width="65px"
                  height="65px"
                  class="click_comm"
                >
              </td>
            </tr>
            <tr>
              <td width="40%">视频上传</td>
              <td
                width="60%"
                class="comm_message"
              >
                <block
                  v-for="(item,index) in video"
                  :key="index"
                >
                  <a
                    :href="item.video.video_src"
                    target="_blank"
                    class="video_src"
                  >
                    <img
                      :src="item.video.video_img_src"
                      style="display: inline-block;"
                      width="140px"
                      height="80px"
                    >
                    <p class="video_time">00:00:02</p>
                    <div
                      :style="`background:url(${$imageHost}/image/admin/play_button.png) no-repeat center`"
                      class="play_bg"
                    >
                    </div>
                  </a>
                </block>

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
      name: [], // 姓名
      phoneNum: [], // 手机号
      email: [], // 邮箱
      sexArr: [], // 性别
      slideArr: [], // 下拉框
      slideIndex: '', // 下拉选中项
      inputVal: [], // 输入框
      choiseArr: [], // 选项
      chooseIndex: '', // 选中下标
      date: [], // 日期
      picture: [], // 图片上传
      video: [], // 视频模块
      address: [],
      xlData: [], // 下拉数据汇总
      xxData: [] // 选项数据汇总
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
        let { pageId, userId, submitId } = this.$route.query
        feedBackListDetailQuery({ pageId: pageId, userId: userId, submitId: submitId }).then(res => {
          console.log(res)

          if (res.error === 0) {
            res.content.forEach((item, index) => {
              switch (item.moduleName) {
                case 'm_input_name':
                  this.name.push(JSON.parse(item.moduleValue))
                  break
                case 'm_input_mobile':
                  this.phoneNum.push(JSON.parse(item.moduleValue))
                  break
                case 'm_address':
                  console.log(JSON.parse(item.moduleValue))
                  let text = ''
                  console.log(item)
                  JSON.parse(item.moduleValue).area.forEach((itemC, index) => {
                    text = text + itemC
                  })
                  if (JSON.parse(item.moduleValue).detail) {
                    text = text + JSON.parse(item.moduleValue).detail
                  }
                  console.log(text)
                  this.address.push(text)
                  break
                case 'm_input_email':
                  this.email.push(JSON.parse(item.moduleValue))
                  break
                case 'm_sex':
                  console.log(item)
                  let obj = {
                    radio: null
                  }
                  if (JSON.parse(item.moduleValue) === '男') {
                    obj.radio = '1'
                  } else {
                    obj.radio = '2'
                  }
                  this.sexArr.push(obj)
                  break
                case 'm_slide':
                  console.log(item)
                  let objMslide = {
                    slideIndex: '',
                    slideArr: ''
                  }
                  item.moduleValueList.forEach((itemC, indexC) => {
                    if (JSON.parse(item.moduleValue) === itemC) {
                      objMslide.slideIndex = itemC
                    }
                  })
                  objMslide.slideArr = item.moduleValueList
                  console.log(objMslide)
                  this.xlData.push(objMslide)
                  break
                case 'm_input_text':
                  console.log(item)
                  this.inputVal.push(item.moduleValue)
                  break
                case 'm_choose':
                  console.log(item)
                  let objMchoose = {
                    chooseIndex: '',
                    choiseArr: ''
                  }

                  item.moduleValueList.forEach((itemC, indexC) => {
                    if (JSON.parse(item.moduleValue) === JSON.stringify(indexC)) {
                      objMchoose.chooseIndex = indexC
                    }
                  })
                  objMchoose.choiseArr = item.moduleValueList
                  console.log(objMchoose)
                  this.xxData.push(objMchoose)
                  break
                case 'm_dates':
                  this.date.push(JSON.parse(item.moduleValue))
                  break
                case 'm_imgs':
                  console.log(item.moduleValue)
                  this.picture.push(JSON.parse(item.moduleValue))
                  break
                case 'm_upload_video':
                  this.video.push(JSON.parse(item.moduleValue))

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
  .comm_message {
    .click_comm {
      margin-right: 5px;
    }
    .play_bg {
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      opacity: 0;
      transition: opacity 0.4s ease;
      text-align: center;
    }
    .video_time {
      position: absolute;
      bottom: 0;
      height: auto;
      line-height: 20px;
      width: 100%;
      background: #000;
      color: white;
      font-size: 12px;
      display: block;
      text-align: center;
      transition: opacity 0.4s ease;
      opacity: 0.5;
    }

    .video_src {
      display: inline-block;
      position: relative;
      &:hover {
        text-decoration: none;
        color: #333;
        cursor: pointer;
        .play_bg {
          opacity: 0.5;
        }
        .video_time {
          display: none;
        }
      }
    }
  }
}
</style>
