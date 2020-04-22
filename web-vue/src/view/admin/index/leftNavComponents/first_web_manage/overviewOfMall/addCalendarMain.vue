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
                  class="incident_info_item"
                  v-for="(item,index) in haveChoiseData"
                  :key="index"
                >
                  <img
                    :src="item.imgUrl"
                    class="info_left_img"
                  >
                  <div class="info_right_box">
                    <p class="act_name_status">{{item.title}}
                      <span
                        v-if="item.choiseActData.id !==-1"
                        class="act_status"
                      >进行中</span>
                    </p>
                    <p
                      v-if="item.choiseActData.id !==-1"
                      class="act_info"
                    >活动名称：{{item.choiseActData.actName}}</p>
                    <p
                      v-if="item.choiseActData.id !==-1"
                      class="act_info"
                    >有效期：{{item.choiseActData.dateTime}}</p>
                    <div
                      v-if="item.choiseActData.id ===-1"
                      class="add_act_box"
                    >
                      <span class="create_act">新建活动</span>
                      <span
                        class="add_act"
                        @click="handleToChoiseDetail(item,index)"
                      >选择活动</span>
                    </div>

                  </div>
                  <div
                    class="shadow_set"
                    v-if="item.choiseActData.id !==-1"
                  >
                    <div class="shadow_setMain">
                      <a href="javascript:;"><i class="iconfont iconbianji"></i></a>
                      <a href="javascript:;"><i class="iconfont iconchakanxiangqing"></i></a>
                      <a href="javascript:;"><i class="iconfont iconxiugai"></i></a>
                      <a href="javascript:;"><i class="iconfont iconshanchu2"></i></a>
                    </div>
                  </div>

                  <img
                    :src="$imageHost+'/image/admin/dele_service.png'"
                    class="del_new_box"
                    @click="handleToClickDel(index)"
                  >
                </li>
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
      <div class="footer">
        <div
          class="save"
          @click="handleToSave()"
        >{{$t('shopStyle.saveText')}}</div>
      </div>
    </div>
    <!--事件说明弹窗-->
    <EventExplainDialog
      :explainVisible.sync="explainVisible"
      :backText="backText"
      @input="handleToGetRich"
    />
    <!--二次删除确认弹窗-->
    <el-dialog
      title="提醒"
      :visible.sync="delDialogVisible"
      width="20%"
    >
      <div style="text-align:center;padding-top:20px">确认删除？</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="delDialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToDelSure()"
        >确 定</el-button>
      </span>
    </el-dialog>
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
                v-for="(item,index) in nowShowActivityData"
                :key="index"
                @click="handleToClickActivity(item)"
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
    <!--选择具体活动弹窗-->
    <el-dialog
      title="选择营销活动"
      :visible.sync="detailActVisible"
      width="45%"
    >
      <div class="choiseDetail">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          highlight-current-row
          @current-change="handleCurrentChange"
          style="width: 100%;margin-top:10px"
        >
          <el-table-column
            prop="actName"
            label="活动名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="dateTime"
            label="有效期"
            align="center"
            width="200"
          >
          </el-table-column>
          <el-table-column
            prop="status"
            label="活动状态"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="handleToChoiseDetilSure()"
        >确 定</el-button>
      </span>
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
        ],
        second: [
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/yhqlb.png?v=1.0.0',
            title: '优惠券礼包',
            tips: '用户一次获得多张优惠券'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/xsjj.png?v=1.0.0',
            title: '限时降价',
            tips: '设定商品在指定时间内降价促销'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/zfyl.png?v=1.0.0',
            title: '支付有礼',
            tips: '用户付款后引导参与营销互动'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/sdth.png?v=1.0.0',
            title: '首单特惠',
            tips: '用户首次下单享受降价优惠'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/djpz.png?v=1.0.0',
            title: '定金膨胀',
            tips: '预售定金翻倍，大促预热利器'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/ms.png?v=1.0.0',
            title: '秒杀',
            tips: '快速抢购引导用户更多购买'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/zp.png?v=1.0.0',
            title: '赠品',
            tips: '通过丰富的赠品策略，向用户发放赠品'
          }
        ],
        third: [
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/mzmj.png?v=1.0.0',
            title: '满折满减',
            tips: '购物满一定金额享受一定优惠'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/dbykj.png?v=1.0.0',
            title: '打包一口价',
            tips: '多件商品一口价打包售卖'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/jjg.png?v=1.0.0',
            title: '加价购',
            tips: '购买指定商品满一定金额加价换购'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/mby.png?v=1.0.0',
            title: '满包邮',
            tips: '购物包邮'
          }
        ],
        fourth: [
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/jfdh.png?v=1.0.0',
            title: '积分兑换',
            tips: '使用积分兑换商品'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/kpyl.png?v=1.0.0',
            title: '开屏有礼（原活动有礼）',
            tips: '用户来到小程序引导参与营销互动'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/pjyl.png?v=1.0.0',
            title: '评价有礼',
            tips: '引导用户评价商品，参与营销活动'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/cp.png?v=1.0.0',
            title: '测评',
            tips: '兴趣测评，让你更了解用户'
          }
        ]
      },
      nowShowActivityData: [], // 当前选择营销活动弹窗展示的活动内容
      haveChoiseData: [], // 已经选出的活动数据
      delDialogVisible: false,
      detailActVisible: false, // 选择具体活动弹窗flag
      tableData: [
        {
          actName: '腾飞测试1',
          dateTime: '2020-04-20至2020-04-24',
          status: '进行中',
          id: 1
        },
        {
          actName: '腾飞测试2',
          dateTime: '2020-04-20至2020-04-24',
          status: '进行中',
          id: 2
        },
        {
          actName: '腾飞测试3',
          dateTime: '2020-04-20至2020-04-24',
          status: '进行中',
          id: 3
        }
      ],
      chioseDetailVal: '',
      clickChoiseIndex: '' // 记录点击得选择活动项index
    }
  },
  mounted () {
    this.nowShowActivityData = this.activityData[this.activeName]
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
      this.nowShowActivityData = this.activityData[tab.name]
    },
    // 点击营销活动弹窗的项
    handleToClickActivity (item) {
      // haveChoiseData
      let { imgUrl, title } = item
      console.log(item)
      let obj = {
        imgUrl: imgUrl,
        title: title,
        choiseActData: {
          id: -1,
          status: '',
          actName: '',
          dateTime: ''
        }
      }
      this.haveChoiseData.push(obj)
      this.choiseActivity = false
    },
    // 删除icon点击
    handleToClickDel (index) {
      this.delIndex = index
      this.delDialogVisible = true
    },
    // 二次删除确定
    handleToDelSure () {
      this.haveChoiseData.splice(this.delIndex, 1)
      this.delDialogVisible = false
    },
    // 选择具体活动
    handleToChoiseDetail (item, index) {
      this.clickChoiseIndex = index
      this.detailActVisible = true
    },
    // 选择具体活动弹窗确定事件
    handleToChoiseDetilSure () {
      // this.chioseDetailVal
      let { id, actName, dateTime, status } = this.chioseDetailVal
      let obj = {
        id: id,
        status: status,
        actName: actName,
        dateTime: dateTime
      }
      this.haveChoiseData[this.clickChoiseIndex].choiseActData = obj
      this.detailActVisible = false
    },
    // 具体活动表格选中
    handleCurrentChange (val) {
      console.log(val)
      this.chioseDetailVal = val
    },
    // 点击保存
    handleToSave () {

    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
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
        ul {
          display: flex;
          flex-wrap: wrap;
          .incident_info_item {
            width: 358px;
            height: 96px;
            background-color: #f2f6ff;
            position: relative;
            min-width: 0;
            margin-bottom: 20px;
            margin-right: 16px;
            border: solid 1px #d9e4ff;
            box-sizing: border-box;
            display: flex;
            align-items: center;
            padding-left: 22px;
            .del_new_box {
              position: absolute;
              right: -9px;
              top: -7px;
              cursor: pointer;
            }
            .info_left_img {
              width: 50px;
              height: 50px;
              margin-right: 19px;
            }
            .info_right_box {
              flex: 1;
              display: flex;
              flex-flow: column;
              .act_status {
                color: #f29b16;
                font-size: 12px;
                display: inline-block;
                margin-left: 10px;
              }
              .act_info {
                font-size: 12px;
                color: #666;
              }
              .act_name_status {
                font-size: 14px;
                margin-bottom: 10px;
              }
              .add_act_box {
                line-height: 28px;
                .create_act {
                  width: 66px;
                  height: 28px;
                  background-color: #5a8bff;
                  border-radius: 2px;
                  text-align: center;
                  font-size: 12px;
                  color: #fff;
                  display: inline-block;
                  cursor: pointer;
                }
                .add_act {
                  width: 66px;
                  height: 28px;
                  background-color: #5a8bff;
                  border-radius: 2px;
                  text-align: center;
                  font-size: 12px;
                  color: #fff;
                  display: inline-block;
                  cursor: pointer;
                  margin-left: 10px;
                }
              }
            }
          }
        }
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
          .shadow_set {
            display: none;
            position: absolute;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            .shadow_setMain {
              display: flex;
              align-items: center;
              height: 100%;
              background-color: rgba(0, 0, 0, 0);
              z-index: 3;
              align-items: center;
              justify-content: space-evenly;
              transition: all 1s ease;
              a {
                text-decoration: none;
                color: #fff;
                i {
                  font-size: 24px;
                }
              }
            }
          }

          &:hover {
            .shadow_set {
              display: block;
              .shadow_setMain {
                background-color: rgba(0, 0, 0, 0.5);
              }
            }
          }
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
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    z-index: 2;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
    margin-right: 10px;
  }
  .save {
    width: 70px;
    height: 30px;
    line-height: 30px;
    border: none;
    background: #5a8bff;
    color: #fff;
    margin: auto;
    cursor: pointer;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .dialog-footer {
    display: block;
    width: 100%;
    display: flex;
    justify-content: center;
  }
  .choiseDetail {
    min-height: 308px;
    overflow: auto;
  }
}
</style>
