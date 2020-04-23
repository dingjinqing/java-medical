<template>
  <div class="calendarMain">
    <div class="main">
      <div class="mainTop">
        <el-select
          v-model="dateValue"
          placeholder="请选择"
          size="small"
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
              :id="item.isInvalid?'':'targetbox'"
            >
              <div
                class="left_line_content  content_none"
                :class="item.isInvalid?'pass':''"
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
                  :class="item.isInvalid?'':'in_progress'"
                  v-for="(itemC,indexC) in item.data"
                  :key="indexC"
                >
                  <div class="top_text">
                    {{itemC.dateTime}}
                  </div>
                  <div class="middle_text">{{itemC.eventName}}</div>
                  <div class="bottom_text">{{itemC.status===1?'已结束':''}}</div>
                  <div class="shadow_set">
                    <div class="shadow_setMain">
                      <a
                        href="javascript:;"
                        @click="handleToPublish(index,indexC)"
                      ><i class="iconfont iconfabu"></i></a>
                      <a
                        href="javascript:;"
                        style="margin:0 30px"
                        @click="handleToAdd(false,1)"
                      ><i class="iconfont iconbianji"></i></a>
                      <a
                        href="javascript:;"
                        @click="handleToDel(index,indexC)"
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
Vue.use(vuescroll)
export default {
  data () {
    return {
      dateOptions: [{
        value: -1,
        label: '请选择'
      }, {
        value: 1,
        label: '2019年'
      }, {
        value: 2,
        label: '2020年'
      }],
      dateValue: -1,
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
        data: [
          {
            dateTime: '04-20',
            eventName: '常乐事件添加',
            status: 1
          },
          {
            dateTime: '04-20',
            eventName: '常乐事件添加',
            status: 1
          },
          {
            dateTime: '04-20',
            eventName: '常乐事件添加',
            status: 1
          },
          {
            dateTime: '04-20',
            eventName: '常乐事件添加',
            status: 1
          },
          {
            dateTime: '04-20',
            eventName: '常乐事件添加',
            status: 1
          },
          {
            dateTime: '04-20',
            eventName: '常乐事件添加',
            status: 1
          }
        ],
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
    this.handleToInit()
  },
  methods: {
    // 初始数据处理
    handleToInit () {
      this.dateValue = this.dateOptions[this.dateOptions.length - 1].value
      document.getElementById('targetbox').scrollIntoView({ behavior: 'smooth' })
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
      this.calenderData[index].data.splice(indexC, 1)
      this.dialogVisible = false
    },
    // 点击添加营销事件
    handleToAdd (flag, item) {
      if (flag) {
        this.$router.push({
          name: 'addEvent',
          query: {
            isAdd: true
          }
        })
      } else {
        this.$router.push({
          name: 'addEvent',
          query: {
            isAdd: false,
            id: item
          }
        })
      }
    },
    // 点击发布icon
    handleToPublish (index, indexC) {
      console.log(index, indexC)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.calendarMain {
  // padding: 10px;
  margin-top: 10px;
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
      // position: absolute;
      // left: 15px;
      // right: 15px;
      // top: 80px;
      // bottom: 0;
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
