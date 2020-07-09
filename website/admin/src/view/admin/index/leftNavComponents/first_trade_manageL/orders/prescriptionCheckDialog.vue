<template>
  <el-dialog
    title="电子处方"
    :visible.sync="showPrescription"
    custom-class="custom"
    width="477px"
  >
    <vuescroll :ops="ops">
      <div class="prescription-item">
        <div class="prescription-info">
          <div class="item-title">
            <span>电子处方</span>
            <span>收起</span>
          </div>
          <div class="item-content">
            <div class="item-list-title">
              <span class="dot"></span>
              <span>就诊人信息</span>
            </div>
            <div class="user-info">
              <div>姓名：<span>张三</span></div>
              <div>性别：<span>男</span></div>
              <div>年龄：<span>52岁</span></div>
              <div>科室：<span>外科</span></div>
            </div>
            <div class="item-list-title">
              <span class="dot"></span>
              <span>诊断：原发性高血压</span>
            </div>
            <div class="item-list-title">
              <span class="dot"></span>
              <span>治疗建议：苯磺酸氨氯地平片 用法用量：清晨空腹口服，一次 5mg，一日1次，用药84天苯磺酸氨氯地平片 用法用量：清晨空腹口服，一次 5mg，一日1次，用药84天</span>
            </div>
            <div class="doctor-info">
              <div class="doctor-item">
                <span>处方医师:</span>
                <img
                  src="http://medicaldevimg.weipubao.cn/image/admin/official/bottom_logo.png"
                  alt=""
                >
              </div>
              <div class="doctor-item">
                <span>审核药师:</span>
                <img
                  src="http://medicaldevimg.weipubao.cn/image/admin/official/bottom_logo.png"
                  alt=""
                >
              </div>
              <div class="doctor-item">
                <span>发药药师:</span>
                <img
                  src="http://medicaldevimg.weipubao.cn/image/admin/official/bottom_logo.png"
                  alt=""
                >
              </div>
              <div class="doctor-item date">
                <span>日期:2020-08-20</span>
              </div>
            </div>
          </div>
        </div>
        <div class="drug">
          <span class="title">关联药品：</span>
          <div>
            <span>仁和苯磺酸氨氯地平片5mg*28片/盒 X4</span>
            <span>仁和苯磺酸氨氯地平片5mg*28片/盒 X4</span>
          </div>
        </div>
        <div class="button-content">
          <span>驳回</span>
          <span>续方</span>
        </div>
      </div>
    </vuescroll>
  </el-dialog>
</template>

<script>
import vuescroll from 'vuescroll'
export default {
  components: {
    vuescroll
  },
  data () {
    return {
      ops: {
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
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    showPrescription: {
      get () {
        return this.show
      },
      set (val) {
        this.$emit('update:show', val)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .custom {
  .el-dialog__header {
    background: #f3f3f3;
    padding-top: 10px;
    .el-dialog__title {
      font-size: 14px;
    }
    .el-dialog__headerbtn {
      top: 10px;
    }
  }
  .el-dialog__body {
    padding: 0;
    height: 477px;
  }
}
/deep/ .__vuescroll {
  .__view {
    background-color: #fff;
    padding: 13px 16px 24px;
  }
}
.content {
  position: absolute;
  background-color: aqua;
  top: 13px;
  left: 16px;
  right: 16px;
  bottom: 24px;
}
.prescription-item {
  .prescription-info {
    box-shadow: 0 10px 30px rgba(13, 19, 24, 0.24),
      0 0 0px rgba(10, 16, 20, 0.12);
    background-color: transparent;
    position: relative;
    border-radius: 16px 16px 0 0;
    > .item-title {
      display: flex;
      height: 56px;
      line-height: 42px;
      justify-content: space-between;
      padding: 0 15px;
      background: skyblue;
      border-radius: 16px 16px 0 0;
      > span {
        color: #5a8bff;
        font-size: 14px;
        &:first-of-type {
          font-weight: 600;
        }
      }
    }
    > .item-content {
      background-color: #fff;
      border-radius: 16px 16px 0 0;
      margin-top: -14px;
      padding-top: 24px;
      height: 115px;
      overflow: hidden;
      > .item-list-title {
        display: flex;
        align-items: center;
        padding: 0 16px;
        line-height: 26.9px;
        color: #333;
        font-weight: 600;
        > .dot {
          width: 9px;
          height: 9px;
          background: rgba(255, 255, 255, 1);
          border: 3px solid rgba(90, 139, 255, 1);
          border-radius: 50%;
          margin-right: 8px;
          align-self: flex-start;
          margin-top: 8px;
          & + span {
            flex: 1;
          }
        }
      }
      > .user-info {
        display: flex;
        padding: 10px 16px 10px 33px;
        justify-content: space-between;
        > div {
          color: #999;
          > span {
            color: #333;
          }
        }
      }
      > .doctor-info {
        margin-left: 33px;
        margin-top: 20px;
        border-top: 2px solid #e5e5e5;
        padding-right: 16px;
        font-size: 0;
        flex-wrap: wrap;
        display: flex;
        > .doctor-item {
          width: 49%;
          display: inline-block;
          box-sizing: border-box;
          font-size: 14px;
          display: flex;
          align-items: center;
          color: #333;
          font-weight: 600;
          &:nth-child(2n) {
            justify-content: flex-end;
          }
          > img {
            width: 80px;
            height: 45px;
          }
          &.date {
            color: #999;
            font-weight: 400;
          }
        }
      }
    }
    &::after {
      content: '';
      position: absolute;
      height: 8px;
      width: 100%;
      bottom: -8px;
      left: 0;
      background-image: radial-gradient(
        5px circle at 12px 8px,
        transparent 7px,
        #fff 7px
      );
      background-size: 20px 10px;
    }
  }
  .drug {
    display: flex;
    color: #333;
    font-weight: 600;
    padding: 30px 0 24px;
    > .title {
      font-size: 14px;
      margin-top: 8px;
    }
    > div {
      flex: 1;
      display: flex;
      flex-direction: column;
      line-height: 30px;
    }
  }
  .button-content {
    display: flex;
    > span {
      flex: 1;
      line-height: 30px;
      text-align: center;
      color: #000;
      border-radius: 2px;
      border: 1px solid #ccc;
      & + span {
        margin-left: 20px;
      }
    }
  }
}
</style>
