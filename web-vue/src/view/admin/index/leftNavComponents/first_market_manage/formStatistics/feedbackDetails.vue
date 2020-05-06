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
            <tr
              v-for="(item,index) in allData"
              :key="index"
            >
              <td width="40%">{{item.moduleType}}</td>
              <td
                width="60%"
                class="comm_message"
              >
                <span v-if="item.simple">{{item.moduleValue}}</span>
                <!--性别-->
                <div
                  v-if="item.moduleName === 'm_sex'"
                  class="sex"
                >
                  <span style="margin-right:20px">
                    <el-radio
                      :disabled="item.moduleValue!=='1'"
                      v-model="item.moduleValue"
                      label="1"
                    >男</el-radio>
                    <el-radio
                      :disabled="item.moduleValue!=='2'"
                      v-model="item.moduleValue"
                      label="2"
                    >女</el-radio>
                  </span>
                </div>
                <!--下拉-->
                <div
                  class="sex"
                  v-if="item.moduleName === 'm_slide'"
                >
                  <span>
                    <div
                      :style="item.moduleValue === itemC?'color:#0E70CA;margin-bottom:5px':'margin-bottom:5px'"
                      v-for="(itemC,indexC) in item.moduleValueList"
                      :key="indexC"
                    >
                      ·{{itemC}}
                    </div>
                  </span>
                </div>
                <!--选项-->
                <div
                  class="sex"
                  v-if="item.moduleName === 'm_choose'"
                >
                  <span>
                    <div
                      v-for="(itemC,indexC) in item.moduleValueList"
                      :key="indexC"
                      :style="item.moduleValue === indexC?'color:#0E70CA;margin-bottom:5px':'margin-bottom:5px'"
                    >
                      ·{{itemC}}
                    </div>
                  </span>
                </div>
                <!--图片-->
                <div v-if="item.moduleName === 'm_imgs'">
                  <img
                    v-for="(itemC,indexC) in item.moduleValue"
                    :key="indexC"
                    :src="itemC"
                    style="display: inline-block;margin-right:10px"
                    width="65px"
                    height="65px"
                    class="click_comm"
                  >
                </div>

                <!--上传视频-->
                <a
                  :href="item.moduleValue.video_src"
                  target="_blank"
                  class="video_src"
                  v-if="item.moduleName === 'm_upload_video'"
                >
                  <img
                    :src="item.moduleValue.video_img_src"
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
      allData: []
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
          // allData
          /*
          moduleName: "m_input_name"
moduleType: "姓名1"
moduleValue: ""腾飞1""
moduleValueList: null
          */
          if (res.error === 0) {
            res.content.forEach((item, index) => {
              switch (item.moduleName) {
                case 'm_input_name':
                  // this.name.push(JSON.parse(item.moduleValue))
                  item.moduleValue = JSON.parse(item.moduleValue)
                  item.simple = true
                  break
                case 'm_input_mobile':
                  item.simple = true
                  item.moduleValue = JSON.parse(item.moduleValue)
                  // this.phoneNum.push(JSON.parse(item.moduleValue))
                  break
                case 'm_address':
                  item.simple = true
                  console.log(JSON.parse(item.moduleValue))
                  let text = ''
                  console.log(item)
                  if (item.moduleValue !== '{}') {
                    JSON.parse(item.moduleValue).area.forEach((itemC, index) => {
                      text = text + itemC
                    })
                    if (JSON.parse(item.moduleValue).detail) {
                      text = text + JSON.parse(item.moduleValue).detail
                    }
                  }
                  console.log(text)
                  // this.address.push(text)
                  item.moduleValue = text
                  break
                case 'm_input_email':
                  item.simple = true
                  item.moduleValue = JSON.parse(item.moduleValue)
                  // this.email.push(JSON.parse(item.moduleValue))
                  break
                case 'm_sex':
                  item.simple = false
                  console.log(item)
                  // let obj = {
                  //   radio: null
                  // }
                  if (JSON.parse(item.moduleValue) === '男') {
                    item.moduleValue = '1'
                  } else {
                    item.moduleValue = '2'
                  }
                  // this.sexArr.push(obj)
                  break
                case 'm_slide':
                  item.simple = false
                  console.log(item)
                  // let objMslide = {
                  //   slideIndex: '',
                  //   slideArr: ''
                  // }
                  let value = JSON.parse(item.moduleValue)
                  item.moduleValueList.forEach((itemC, indexC) => {
                    if (value === itemC) {
                      item.moduleValue = itemC
                    }
                  })
                  // objMslide.slideArr = item.moduleValueList
                  // console.log(objMslide)
                  // this.xlData.push(objMslide)
                  break
                case 'm_input_text':
                  console.log(item)
                  item.simple = true
                  // this.inputVal.push(item.moduleValue)
                  break
                case 'm_choose':
                  item.simple = false
                  // console.log(item)
                  // let objMchoose = {
                  //   chooseIndex: '',
                  //   choiseArr: ''
                  // }

                  item.moduleValueList.forEach((itemC, indexC) => {
                    if (JSON.parse(item.moduleValue) === JSON.stringify(indexC)) {
                      item.moduleValue = indexC
                    }
                  })

                  break
                case 'm_dates':
                  item.moduleValue = JSON.parse(item.moduleValue)
                  item.simple = true
                  break
                case 'm_imgs':
                  console.log(item)
                  item.simple = false
                  item.moduleValue = JSON.parse(item.moduleValue)
                  break
                case 'm_upload_video':
                  console.log(item)
                  item.simple = false
                  item.moduleValue = JSON.parse(item.moduleValue)
                  break
              }
            })
            console.log(res.content)
            this.allData = res.content
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
