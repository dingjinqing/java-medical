<template>
  <div class="frontEndDisplay">
    <div class="frontEndDisplayMain">
      <div class="frontEndTop">
        <img :src="$imageHost+'/image/admin/notice_img.png'">
        <span>请在此设置小程序前端"我的积分"页面展示内容</span>
      </div>
      <div class="content">
        <div class="left">
          <img :src="$imageHost+'/image/admin/score_qd.png'">
          <div class="show_area">
            已选择模板:<span>test</span>
          </div>
          <div class="show_area hidden">
            <div>
              <h1>自定义内容区域</h1>
              <p>可在右侧选择商品页模板</p>
              <p>未添加内容时,不显示此模块内容</p>
            </div>

          </div>
          <div class="score_get">积分收支明细</div>
          <div class="score_set bottom">
            <div class="score_setDiv">连续签到2天，获得1积分</div>
            <div class="score_setDiv">2019-04-29 12:00:00</div>
            <div class="score_setDiv">+1</div>
          </div>
        </div>
        <div class="right">
          <div class="right_div">
            <div class="r_top">
              <span>积分说明</span>
              <span>用于前端展示，向用户说明店铺积分相关规则</span>
            </div>
            <div class="r_bottom">
              <el-button
                type="primary"
                size="small"
                @click="handleToRightBtn(0)"
              >编写积分说明</el-button>
            </div>

          </div>
          <div class="right_div">
            <div class="r_top">
              <span>自定义内容</span>
              <span>用于向用户推荐积分相关活动商品</span>
            </div>
            <div class="r_bottom">
              <div
                class="templateList"
                v-if="templateList"
              >
                <span>{{templateList}}</span>
                <img
                  @click="handleTodel()"
                  :src="$imageHost+'/image/admin/icon_delete.png'"
                  style="cursor:pointer"
                >
              </div>
              <div>
                <el-button
                  plain
                  size="small"
                  @click="handleToRightBtn(1)"
                >选择模板</el-button>
              </div>
              <span
                @click="handleToRightBtn(2)"
                class="common"
              >刷新</span>
              <span
                @click="handleToRightBtn(3)"
                class="common"
              >添加模板</span>
            </div>

          </div>
        </div>
      </div>
    </div>
    <!--选择运费模板-->
    <SelectTemplateDialog @handleToSendData="handleToSendData" />
  </div>
</template>
<script>
export default {
  components: {
    SelectTemplateDialog: () => import('./selectTemplateDialog')
  },
  data () {
    return {
      templateList: 'test'
    }
  },
  methods: {
    // 右侧按钮点击汇总
    handleToRightBtn (flag) {
      switch (flag) {
        case 0:
          this.$router.push({
            name: 'integralDescription'
          })
          break
        case 1:
          console.log(111)
          this.$http.$emit('choiseTemplateDialog', true)
          break
        case 2:
          break
        case 3:
          break
      }
    },
    // 模板删除icon点击
    handleTodel () {
      this.templateList = ''
    },
    // 选择模板弹窗选中数据
    handleToSendData (res) {
      console.log(res)
      this.templateList = res.userID
    }
  }
}
</script>
<style lang="scss" scoped>
.frontEndDisplay {
  width: 100%;
  display: flex;
  justify-content: center;
  .frontEndDisplayMain {
    width: 55%;
    min-height: 700px;
    .frontEndTop {
      height: 30px;
      color: rgb(102, 102, 102);
      font-size: 12px;
      background-color: rgb(255, 247, 235);
      padding: 0px 20px;
      border: 1px solid rgb(255, 213, 163);
      border-image: initial;
      margin: 10px 0 0 0px;
      display: flex;
      align-items: center;
      span {
        display: inline-block;
        margin-left: 10px;
      }
    }
    .content {
      margin-top: 10px;
      overflow: hidden;
      display: flex;
      justify-content: space-between;
      .left {
        height: 561px;
        border: 1px solid #eee;
        .show_area {
          width: 100%;
          height: 150px;
          display: flex;
          align-items: center;
          justify-content: center;
          background: #fff;
          border: 1px dashed #5a8bff;
          div {
            display: flex;
            justify-content: center;
            flex-direction: column;
            align-items: center;
            h1 {
              font-size: 14px;
              color: #333;
              margin-bottom: 5px;
            }
            p {
              font-size: 12px;
              color: #999;
              margin-top: 5px;
            }
          }
          span {
            cursor: pointer;
            color: #5a8bff;
          }
        }
        .hidden {
          display: none;
        }
        .score_get {
          height: 40px;
          line-height: 40px;
          text-align: center;
          border-bottom: 1px solid #eee;
        }
        .bottom {
          position: relative;
          .score_setDiv {
            height: 25px;
            line-height: 25px;
            padding: 10px;
          }
          .score_setDiv:nth-of-type(1) {
            color: #000;
          }
          .score_setDiv:nth-of-type(2) {
            color: #666;
          }
          .score_setDiv:nth-of-type(3) {
            position: absolute;
            right: 10px;
            top: 1px;
            color: #ff6666;
            font-size: 16px;
          }
        }
      }
      .right {
        width: 580px;
        .right_div {
          border: 1px solid #eee;
          margin-bottom: 20px;
          .r_top {
            background: #f5f5f5;
            padding: 10px;
            span:nth-of-type(1) {
              color: #333;
              font-weight: 600;
              padding-right: 20px;
            }
            span:nth-of-type(2) {
              color: #999;
              font-size: 12px;
              border-bottom: 1px solid #eee;
            }
          }
          .r_bottom {
            padding: 10px;
            overflow: hidden;
            display: flex;
            .templateList {
              min-width: 40px;
              height: 25px;
              font-size: 14px;
              line-height: 22px;
              margin: 0 15 0 15px;
              position: relative;
              padding-right: 18px;
              display: flex;
              align-items: center;
              span {
                display: inline-block;
                margin-right: 5px;
              }
            }
            .common {
              display: inline-block;
              height: 32px;
              line-height: 32px;
              color: #5a8bff;
              margin-left: 10px;
              cursor: pointer;
            }
          }
        }
      }
    }
  }
}
</style>
