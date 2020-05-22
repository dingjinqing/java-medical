<template>
  <div class="calendarMain">
    <div class="main">
      <div class="mainTop">
        <el-select
          v-model="dateValue"
          placeholder="请选择"
          size="small"
          @change="handleToChange"
        >
          <el-option
            v-for="item in dateOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-button
          type="primary"
          size="small"
          @click="handleToAdd(true)"
        >添加营销事件</el-button>
      </div>
      <div class="mainBottom">
        <vue-scroll
          :ops="ops"
          style="height:520px"
        >
          <ul class="calendar_info_box">
            <li
              class="calendar_info_line"
              v-for="(item,index) in calenderData"
              :key="index"
              :id="item.targetbox?'targetbox':''"
            >
              <div
                class="left_line_content  content_none"
                :class="item.targetbox?'':'pass'"
              >
                <div class="month_box">
                  <div class="month">{{item.month}}月</div>
                </div>
              </div>
              <div
                v-if="item.data.length===0"
                class="no_style_line"
              ></div>
              <div
                v-if="item.data.length>0"
                class="calendar_info_container"
              >
                <div
                  class="calendar_info_item"
                  :class="itemC.eventStatus === 3?'':'in_progress'"
                  v-for="(itemC,indexC) in item.data"
                  :key="indexC"
                >
                  <div class="top_text">
                    {{itemC.eventTime}}
                    <span
                      v-if="itemC.source === 1"
                      class="ribbon"
                    >推荐</span>
                  </div>
                  <div class="middle_text">{{itemC.eventName}}</div>
                  <div
                    class="bottom_text"
                    :style="itemC.eventStatus===3?'corlor:#999':''"
                  >{{itemC.eventStatus===4?'已结束':itemC.eventStatus===2?'进行中':itemC.eventStatus===3?'已失效':''}}</div>
                  <div
                    class="bottom_text"
                    v-if="itemC.eventStatus===1"
                  >
                    剩<span>{{itemC.downTime}}</span>天
                  </div>
                  <div class="shadow_set">
                    <div class="shadow_setMain">
                      <a
                        href="javascript:;"
                        :style="itemC.source !== 1 || itemC.eventStatus===3?'margin-right:20px':''"
                        @click="handleToAdd(false,itemC)"
                      ><i class="iconfont iconbianji"></i></a>
                      <a
                        href="javascript:;"
                        style="margin-left:20px"
                        @click="handleToDel(index,indexC)"
                        v-if="itemC.source !== 1 || itemC.eventStatus===3"
                      ><i class="iconfont iconshanchu2"></i></a>
                    </div>

                  </div>
                </div>
              </div>
            </li>
          </ul>
        </vue-scroll>
      </div>
    </div>
    <!--二次删除缺人弹窗-->
    <el-dialog
      title="提醒"
      :visible.sync="dialogVisible"
      width="20%"
    >
      <div style="text-align:center">确认删除本事件么？</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToDelSure(delIndex,delIndexC)"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import vuescroll from 'vuescroll'
import Vue from 'vue'
import { getCalendarList, deltCalendarEvent } from '@/api/admin/firstWebManage/calender/calender.js'
Vue.use(vuescroll)
export default {
  data () {
    return {
      dateOptions: [],
      dateValue: null,
      calenderData: [{
        month: '01',
        data: [],
        isInvalid: true
      },
      {
        month: '02',
        data: [],
        isInvalid: true
      },
      {
        month: '03',
        data: [],
        isInvalid: true
      },
      {
        month: '04',
        data: [],
        isInvalid: false
      },
      {
        month: '05',
        data: [],
        isInvalid: false
      },
      {
        month: '06',
        data: [],
        isInvalid: false
      },
      {
        month: '07',
        data: [],
        isInvalid: false
      },
      {
        month: '08',
        data: [],
        isInvalid: false
      },
      {
        month: '09',
        data: [],
        isInvalid: false
      },
      {
        month: '10',
        data: [],
        isInvalid: false
      },
      {
        month: '11',
        data: [],
        isInvalid: false
      },
      {
        month: '12',
        data: [],
        isInvalid: false
      }],
      target: -1,
      dialogVisible: false, // 二次删除弹窗flag
      delIndex: -1,
      delIndexC: -1,
      ops: { // 滚动相关设置
        vuescroll: {
          mode: 'native'
        },
        scrollPanel: {},
        rail: {
          keepShow: true
        },
        bar: {
          hoverStyle: true,
          onlyShowBarOnScroll: false, // 是否只有滚动的时候才显示滚动条
          background: '#eee'
        }
      }
    }
  },
  mounted () {
    // 初始数据处理
    var myDate = new Date()
    var tYear = myDate.getFullYear()
    console.log(tYear)
    this.handleToInit(tYear)
  },
  methods: {
    // 初始数据处理
    handleToInit (tYear) {
      // this.dateValue = this.dateOptions[this.dateOptions.length - 1].value
      console.log(this.dateValue)
      let params = null
      if (tYear) {
        params = tYear
      } else {
        if (this.dateValue !== null) {
          this.dateOptions.forEach((item, index) => {
            if (item.value === this.dateValue) {
              console.log(item.label.slice(0, 4))
              params = item.label.slice(0, 4)
            }
          })
        }
      }
      console.log(params)
      getCalendarList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          // 处理年份下拉框数据
          let yearList = res.content.yearList || []
          let yearArr = []
          let currentYear = res.content.currentDate.split('-')[0]
          let currentMouth = res.content.currentDate.split('-')[1]
          let dateValue = null
          yearList.forEach((item, index) => {
            if (item === currentYear) {
              dateValue = index
            }
            let label = item + '年'
            let obj = {
              value: index,
              label: label
            }
            yearArr.push(obj)
          })
          let obj = {
            value: null,
            label: '请选择'
          }
          yearArr.unshift(obj)
          console.log(yearArr)
          this.dateOptions = yearArr
          if (tYear) {
            this.dateValue = dateValue
          }
          // 处理初始化滑动的位置
          console.log(currentMouth)
          res.content.data.forEach((item, index) => {
            if (Number(item.month) < Number(currentMouth)) {
              item.targetbox = false
            } else {
              item.targetbox = true
            }
            item.data.forEach((itemC, indexC) => {
              itemC.eventTime = itemC.eventTime.split('-')[1] + '-' + itemC.eventTime.split('-')[2]
            })
          })
          console.log(res.content.data)
          this.calenderData = res.content.data
          this.$nextTick(() => {
            document.getElementById('targetbox').scrollIntoView({ behavior: 'smooth' })
          })
        }
      })
    },
    // 点击编辑
    handleToEdit (itemC) {
      console.log(itemC)
    },
    // 点击删除
    handleToDel (index, indexC) {
      this.delIndex = index
      this.delIndexC = indexC
      this.dialogVisible = true
    },
    handleToDelSure (index, indexC) {
      this.dialogVisible = false
      let id = this.calenderData[index].data[indexC].id
      deltCalendarEvent(id).then(res => {
        if (res.error === 0) {
          this.calenderData[index].data.splice(indexC, 1)
          this.$message.success({
            message: '删除成功',
            showClose: true
          })
        }
      })
    },
    // 点击添加营销事件
    handleToAdd (flag, item) {
      console.log(item)
      if (flag) {
        this.$router.push({
          name: 'addCalendarMain',
          query: {
            isAdd: true
          }
        })
      } else {
        this.$router.push({
          name: 'addCalendarMain',
          query: {
            isAdd: false,
            id: item.id,
            eventStatus: item.eventStatus
          }
        })
      }
    },
    // 年份下拉框选中值变化
    handleToChange (res) {
      console.log(res)
      this.handleToInit()
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.calendarMain {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  .main {
    background-color: #fff;
    padding: 15px;
    height: 100%;
    .mainTop {
      display: flex;
      justify-content: space-between;
      padding-bottom: 20px;
      border-bottom: 1px solid #eee;
    }
    .mainBottom {
      position: absolute;
      left: 15px;
      right: 15px;
      top: 80px;
      bottom: 0;
      overflow: auto;
      /deep/ .__view {
        background-color: #fff;
      }
      .calendar_info_box {
        display: flex;
        flex-direction: column;
        margin-left: 50px;
        border-left: 2px dashed #eee;
        .calendar_info_line {
          display: flex;
          padding: 20px;
          min-width: 0;
          flex-wrap: wrap;
          margin-bottom: -20px;
          position: relative;
          .left_line_content {
            position: absolute;
            width: 52px;
            height: 75px;
            background-color: #fff;
            left: -26px;
            top: 52px;
            .month_box {
              width: 52px;
              height: 52px;
              background-color: #ffe0e0;
              border-radius: 50%;
              position: absolute;
              top: 50%;
              transform: translateY(-50%);
              .month {
                width: 38px;
                line-height: 38px;
                background-color: #ff7777;
                border-radius: 50%;
                text-align: center;
                color: #fff;
                position: absolute;
                left: 50%;
                top: 50%;
                transform: translate(-50%, -50%);
              }
            }
          }
          .content_none {
            top: 22px;
          }
          .pass {
            .month_box {
              background-color: rgba(204, 204, 204, 0.2);
              .month {
                background-color: rgba(204, 204, 204, 1);
              }
            }
          }
          .no_style_line {
            border-top: 2px dashed #eee;
            width: 100%;
            margin: 40px 0;
            margin-left: 26px;
          }
          .calendar_info_container {
            display: flex;
            flex-wrap: wrap;
            .calendar_info_item {
              width: 195px;
              height: 140px;
              border-radius: 6px;
              text-align: center;
              padding-bottom: 10px;
              position: relative;
              min-width: 0;
              margin-left: 24px;
              margin-bottom: 20px;
              border: solid 1px #dddddd;
              box-sizing: border-box;

              .top_text {
                line-height: 40px;
                border-top-left-radius: 4px;
                border-top-right-radius: 4px;
                background: #ccc;
                color: #fff;
                margin: -1px -1px 0 -1px;
                font-size: 16px;
                overflow: hidden;
                position: relative;
                .ribbon {
                  display: inline-block;
                  text-align: center;
                  width: 90px;
                  height: 22px;
                  line-height: 22px;
                  position: absolute;
                  top: 6px;
                  right: -28px;
                  z-index: 2;
                  overflow: hidden;
                  transform: rotate(45deg);
                  -ms-transform: rotate(45deg);
                  -moz-transform: rotate(45deg);
                  -webkit-transform: rotate(45deg);
                  -o-transform: rotate(45deg);
                  background: #ffdc1b;
                  font-size: 12px;
                  color: #ff4444;
                }
              }
              .middle_text {
                color: #333;
                font-size: 18px;
                font-weight: 600;
                margin-top: 18px;
                margin-bottom: 11px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
              }
              .bottom_text {
                line-height: 28px;
                font-size: 16px;
                color: #999;
                span {
                  display: inline-block;
                  padding: 0 12px;
                  background-color: #eee;
                  color: #999;
                  border-radius: 3px;
                  margin: 0 3px;
                  font-weight: 600;
                  color: #f66;
                  background-color: #ffe1e1;
                }
              }

              .shadow_setMain {
                top: 0;
                left: 0;
                width: 195px;
                height: 140px;
                display: flex;
                position: absolute;
                background-color: rgba(0, 0, 0, 0);
                border-radius: 4px;
                z-index: 3;
                justify-content: center;
                align-items: center;
                transition: background-color 0.5s ease;
                a {
                  text-decoration: none !important;
                  i {
                    color: #fff;
                    font-size: 24px;
                  }
                }
              }
              .shadow_set {
                display: none;
              }
              &:hover {
                .shadow_set {
                  display: block !important;
                  .shadow_setMain {
                    background-color: rgba(0, 0, 0, 0.5);
                  }
                }
              }
            }
            .in_progress {
              .top_text {
                background: #f66;
              }
              .bottom_text {
                color: #333;
              }
            }
          }
        }
      }
    }
  }
}
</style>
