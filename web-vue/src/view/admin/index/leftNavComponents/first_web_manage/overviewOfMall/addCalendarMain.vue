<template>
  <div class="addCalendarMain">
    <div class="mainTop">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item
          label="事件名称："
          prop="eventName"
        >
          <el-input
            size="small"
            v-model="ruleForm.eventName"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="活动时间："
          required
          prop="date"
        >
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="ruleForm.date"
            size="small"
            style="width:188px"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div class="explain">
        <div class="explainTop">
          <span>事件说明：</span>
          <div>
            <el-button
              size="small"
              type="primary"
              @click="handleToClickExplain()"
            >编辑</el-button>
            <div
              class="rich"
              v-html="richText"
            >
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="mainBottom"
      style="margin-top:10px"
    >
      <el-form
        :model="ruleFormBottom"
        :rules="rulesBottom"
        ref="ruleFormBottom"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item
          label="营销活动"
          prop="marketActivity"
        >
          <div class="marketActivity">
            <div class="tips">温馨提示：删除营销事件中的活动，不会影响营销管理-对应活动列表中已创建的活动</div>
            <div class="activityMain">
              <ul>
                <li
                  @click="handleToClick()"
                  class="incident_info_item add_new_list"
                >
                </li>
              </ul>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <!--事件说明弹窗-->
    <EventExplainDialog
      :explainVisible.sync="explainVisible"
      :backText="backText"
      @input="handleToGetRich"
    />
    <!--选择营销活动弹窗-->
    <el-dialog
      title="选择营销活动"
      :visible.sync="choiseActivity"
      width="48%"
    >
      <div class="choiseActivityMain">
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
          :stretch="true"
        >
          <el-tab-pane
            label="获客拉新"
            name="first"
          ></el-tab-pane>
          <el-tab-pane
            label="促单转化"
            name="second"
          ></el-tab-pane>
          <el-tab-pane
            label="提高客单价"
            name="third"
          ></el-tab-pane>
          <el-tab-pane
            label="留存复购"
            name="fourth"
          ></el-tab-pane>
          <div class="activityContent">
            <ul>
              <li
                class="act_item"
                v-for="(item,index) in activityData"
                :key="index"
              >
                <img
                  :src="item.imgUrl"
                  class="info_left_img"
                >
                <div class="act_right_box">
                  <p class="act_name_status">{{item.title}}</p>
                  <p class="act_introduce">{{item.tips}}</p>
                </div>
              </li>
            </ul>
          </div>
        </el-tabs>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  components: {
    EventExplainDialog: () => import('./eventExplainDialog')
  },
  data () {
    return {
      ruleForm: {
        eventName: '',
        date: ''
      },
      rules: {
        eventName: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        date: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ]
      },
      ruleFormBottom: {
        marketActivity: ''
      },
      rulesBottom: {
        marketActivity: [
          { required: true, message: '请选择营销活动', trigger: 'blur' }
        ]
      },
      explainVisible: false,
      backText: '',
      richText: '',
      choiseActivity: false,
      activeName: 'first',
      activityData: {
        first: [
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/jfdh.png?v=1.0.0',
            title: '积分兑换',
            tips: '使用积分兑换商品'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/kj.png?v=1.0.0',
            title: '砍价',
            tips: '引导用户邀请朋友砍价'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/gfjf.png?v=1.0.0',
            title: '组团瓜分积分',
            tips: '提高用户活跃度,引导用户拼团得积分'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/xxdcj.png?v=1.0.0',
            title: '幸运大抽奖',
            tips: '九宫格式抽奖玩法'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/friend_promote_cion.png?v=1.0.0',
            title: '好友助力',
            tips: '好友帮忙获得奖励'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/jfdh.png?v=1.0.0',
            title: '积分兑换',
            tips: '使用积分兑换商品'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/wysl.png?v=11.0.0',
            title: '我要送礼',
            tips: '购买商品送好友'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/fxyl.png?v=1.0.0',
            title: '分享有礼',
            tips: '分享商品获得优惠奖励，提升商品曝光度'
          }
        ]
      },
      nowShowActivityData: []
    }
  },
  methods: {
    // 点击事件说明编辑
    handleToClickExplain () {
      this.explainVisible = true
    },
    // 获取富文本输入内容
    handleToGetRich (res) {
      console.log(res)
      this.richText = res
    },
    // 点击添加营销活动
    handleToClick () {
      this.choiseActivity = true
    },
    // 选择营销活动弹窗切换
    handleClick (tab, event) {
      console.log(tab, event)
    }
  }
}
</script>
<style lang="scss" scoped>
.addCalendarMain {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  /deep/ .el-dialog__body {
    padding-top: 0 !important;
  }
  .mainTop,
  .mainBottom {
    background-color: #fff;
    padding: 15px;
  }
  .mainTop {
    /deep/ .el-input {
      width: auto;
    }
    .explain {
      padding-left: 18px;
      .explainTop {
        display: flex;
        span {
          display: block;
          margin-right: 13px;
          height: 32px;
          line-height: 32px;
        }
      }
      .rich {
        display: flex;
        flex-wrap: wrap;
        margin-top: 10px;
      }
    }
  }
  .mainBottom {
    padding-left: 0;
    /deep/ .el-form-item {
      display: flex;
      flex-direction: column;
    }
    /deep/ .el-form-item__content {
      margin-left: 23px !important;
      line-height: normal !important;
    }
    .marketActivity {
      padding-left: 9px;
      .tips {
        color: #999;
        font-size: 14px;
      }
      .activityMain {
        .incident_info_item {
          width: 358px;
          height: 96px;
          background-color: #f2f6ff;
          position: relative;
          min-width: 0;
          margin-top: 12px;
          margin-bottom: 20px;
          border: solid 1px #d9e4ff;
          box-sizing: border-box;
          display: flex;
          align-items: center;
          padding-left: 22px;
        }
        .add_new_list {
          background-color: #f5f5f5;
          border-color: #eeeeee;
          cursor: pointer;
        }
        .add_new_list::before {
          content: "";
          position: absolute;
          width: 34px;
          height: 3px;
          background-color: #ddd;
          left: 50%;
          transform: translateX(-50%);
        }
        .add_new_list::after {
          content: "";
          position: absolute;
          width: 3px;
          height: 34px;
          background-color: #ddd;
          left: 50%;
          transform: translateX(-50%);
        }
      }
    }
  }
  .choiseActivityMain {
    .activityContent {
      min-height: 308px;
      ul {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        .act_item {
          width: 333px;
          height: 61px;
          background-color: #f2f6ff;
          position: relative;
          min-width: 0;
          margin-bottom: 16px;
          border: solid 1px #d9e4ff;
          box-sizing: border-box;
          display: flex;
          align-items: center;
          padding-left: 18px;
          cursor: pointer;
          .info_left_img {
            width: 41px;
            height: 41px;
            margin-right: 17px;
          }
          .act_right_box {
            flex: 1;
            display: flex;
            flex-flow: column;
            justify-items: center;
            .act_name_status {
              font-size: 14px;
              margin-bottom: 10px;
            }
            .act_introduce {
              font-size: 12px;
              color: #666;
            }
          }
        }
      }
    }
  }
}
</style>
