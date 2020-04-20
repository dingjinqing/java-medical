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
        >添加营销事件</el-button>
      </div>
      <div class="mainBottom">
        <ul class="calendar_info_box">
          <li class="calendar_info_line">
            <div class="left_line_content  pass   content_none ">
              <div class="month_box">
                <div class="month">01月</div>
              </div>
            </div>
            <div class="no_style_line"></div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
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
      dateValue: 2
    }
  }
}
</script>
<style lang="scss" scoped>
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
        }
      }
    }
  }
}
</style>
