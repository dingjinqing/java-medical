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
            value-format="yyyy-MM-dd"
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
          prop="haveChoiseData"
        >
          <div class="marketActivity">
            <div class="tips">温馨提示：删除营销事件中的活动，不会影响营销管理-对应活动列表中已创建的活动</div>
            <div class="activityMain">
              <ul>
                <li
                  class="incident_info_item"
                  v-for="(item,index) in ruleFormBottom.haveChoiseData"
                  :key="index"
                  :style="item.delFlag?'display:none':''"
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
                      >{{item.choiseActData.actStatus === 1?'未开始':item.choiseActData.actStatus === 2?'进行中':item.choiseActData.actStatus === 3?'已失效':'已结束'}}</span>
                    </p>
                    <p
                      v-if="item.choiseActData.id !==-1"
                      class="act_info"
                    >活动名称：{{item.choiseActData.actName}}</p>
                    <p
                      v-if="item.choiseActData.id !==-1"
                      class="act_info"
                    >有效期： {{item.choiseActData.isPermanent===1?'永久有效':(item.choiseActData.startTime+'至'+item.choiseActData.endTime)}}</p>
                    <div
                      v-if="item.choiseActData.id ===-1"
                      class="add_act_box"
                    >
                      <span
                        class="create_act"
                        @click="handleToAddNewAct(item)"
                      >新建活动</span>
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
                      <a
                        @click="handleToAllHiddenIcon(1,index,item)"
                        href="javascript:;"
                      ><i class="iconfont iconbianji"></i></a>
                      <a
                        @click="handleToAllHiddenIcon(2,index,item)"
                        href="javascript:;"
                      ><i class="iconfont iconchakanxiangqing"></i></a>
                      <a
                        @click="handleToAllHiddenIcon(3)"
                        href="javascript:;"
                      ><i class="iconfont iconxiugai"></i></a>
                      <a
                        @click="handleToAllHiddenIcon(4,index)"
                        href="javascript:;"
                      ><i class="iconfont iconshanchu2"></i></a>
                    </div>
                  </div>

                  <img
                    :src="$imageHost+'/image/admin/dele_service.png'"
                    class="del_new_box"
                    v-if="item.choiseActData.id ===-1"
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
      <div style="text-align:center;padding-top:20px;line-height:20px">
        {{isClickIconDel?'是否确认删除本活动？活动删除后关联的营销活动不会删除，如需修改对应活动可在营销管理中处理':'确认删除？'}}
      </div>
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
      width="730px"
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
          ref="singleTable"
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
            <template slot-scope="scope">
              {{scope.row.isPermanent===1?'永久有效':(scope.row.startTime+'至'+scope.row.endTime)}}
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="活动状态"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.actStatus === 1?'未开始':scope.row.actStatus === 2?'进行中':scope.row.actStatus === 3?'已失效':'已结束'}}
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="handleToInitActData"
        />
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
import { eventDeatil, allMarketList, saveEvent } from '@/api/admin/firstWebManage/calender/calender.js'
export default {
  components: {
    EventExplainDialog: () => import('./eventExplainDialog'),
    pagination: () => import('@/components/admin/pagination/pagination.vue') // 分页组件
  },
  data () {
    var validatePass = (rule, value, callback) => {
      console.log(value)
      if (!this.$route.query.isAdd) {
        let length = 0
        value.forEach((item, index) => {
          if (!item.delFlag) {
            length++
          }
        })
        if (!length) {
          callback(new Error('请选择营销活动'))
        } else {
          callback()
        }
      } else {
        if (!value.length) {
          callback(new Error('请选择营销活动'))
        } else {
          callback()
        }
      }
    }
    return {
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      calendarId: null, // 编辑时返回的事件id
      hasAct: false, // 编辑时判断是否有营销活动
      ruleForm: {
        eventName: '',
        date: ''
      },
      rules: {
        eventName: [
          { required: true, message: '请输入活动名称', trigger: 'blur' }
        ],
        date: [
          { type: 'string', required: true, message: '请选择日期', trigger: 'change' }
        ]
      },
      ruleFormBottom: {
        haveChoiseData: []
      },
      rulesBottom: {
        haveChoiseData: [
          { required: true, validator: validatePass, trigger: 'blur' }
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
            imgUrl: this.$imageHost + '/image/admin/new_market/drpt.png?v=1.0.0',
            title: '多人拼团',
            tips: '引导朋友邀请朋友拼团购买',
            activityType: 'pin_group'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/kj.png?v=1.0.0',
            title: '砍价',
            tips: '引导用户邀请朋友砍价',
            activityType: 'bargain'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/jfdh.png?v=1.0.0',
            title: '拼团抽奖',
            tips: '拼团参与抽奖,裂变式转化',
            activityType: 'group_draw'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/gfjf.png?v=1.0.0',
            title: '组团瓜分积分',
            tips: '提高用户活跃度,引导用户拼团得积分',
            activityType: 'pin_integration'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/xxdcj.png?v=1.0.0',
            title: '幸运大抽奖',
            tips: '九宫格式抽奖玩法',
            activityType: 'lottery'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/friend_promote_cion.png?v=1.0.0',
            title: '好友助力',
            tips: '好友帮忙获得奖励',
            activityType: 'promote'
          },

          {
            imgUrl: this.$imageHost + '/image/admin/new_market/wysl.png?v=11.0.0',
            title: '我要送礼',
            tips: '购买商品送好友',
            activityType: 'give_gift'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/new_market/fxyl.png?v=1.0.0',
            title: '分享有礼',
            tips: '分享商品获得优惠奖励，提升商品曝光度',
            activityType: 'share_award'
          }
        ],
        second: [
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/yhqlb.png?v=1.0.0',
            title: '优惠券礼包',
            tips: '用户一次获得多张优惠券',
            activityType: 'coupon_package'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/xsjj.png?v=1.0.0',
            title: '限时降价',
            tips: '设定商品在指定时间内降价促销',
            activityType: 'reduce_price'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/zfyl.png?v=1.0.0',
            title: '支付有礼',
            tips: '用户付款后引导参与营销互动',
            activityType: 'pay_reward'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/sdth.png?v=1.0.0',
            title: '首单特惠',
            tips: '用户首次下单享受降价优惠',
            activityType: 'first_special'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/djpz.png?v=1.0.0',
            title: '定金膨胀',
            tips: '预售定金翻倍，大促预热利器',
            activityType: 'pre_sale'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/ms.png?v=1.0.0',
            title: '秒杀',
            tips: '快速抢购引导用户更多购买',
            activityType: 'seckill_goods'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/zp.png?v=1.0.0',
            title: '赠品',
            tips: '通过丰富的赠品策略，向用户发放赠品',
            activityType: 'gift'
          }
        ],
        third: [
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/mzmj.png?v=1.0.0',
            title: '满折满减',
            tips: '购物满一定金额享受一定优惠',
            activityType: 'full_cut'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/dbykj.png?v=1.0.0',
            title: '打包一口价',
            tips: '多件商品一口价打包售卖',
            activityType: 'package_sale'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/jjg.png?v=1.0.0',
            title: '加价购',
            tips: '购买指定商品满一定金额加价换购',
            activityType: 'purchase_price'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/mby.png?v=1.0.0',
            title: '满包邮',
            tips: '购物包邮',
            activityType: 'free_ship'
          }
        ],
        fourth: [
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/jfdh.png?v=1.0.0',
            title: '积分兑换',
            tips: '使用积分兑换商品',
            activityType: 'integral_goods'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/kpyl.png?v=1.0.0',
            title: '开屏有礼（原活动有礼）',
            tips: '用户来到小程序引导参与营销互动',
            activityType: 'activity_reward'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/pjyl.png?v=1.0.0',
            title: '评价有礼',
            tips: '引导用户评价商品，参与营销活动',
            activityType: 'comment_gift'
          },
          {
            imgUrl: this.$imageHost + '/image/admin/market_calendar/cp.png?v=1.0.0',
            title: '测评',
            tips: '兴趣测评，让你更了解用户',
            activityType: 'assess'
          }
        ]
      },
      nowShowActivityData: [], // 当前选择营销活动弹窗展示的活动内容
      haveChoiseData: [], // 已经选出的活动数据
      delDialogVisible: false,
      detailActVisible: false, // 选择具体活动弹窗flag
      tableData: [], // 具体活动弹窗数据
      chioseDetailVal: '',
      clickChoiseIndex: '', // 记录点击得选择活动项index
      isClickIconDel: false, // 是否是点击的隐藏icon删除
      isClickIconChoiseAct: false // 是否是点击的隐藏icon重新选择活动
    }
  },
  mounted () {
    this.nowShowActivityData = this.activityData[this.activeName]
    // 初始化请求数据
    this.handleToInit()
    // 推荐理由跳转
    // recommendLink
    // this.$router.push({
    //   path: '/admin/home/shopMain',
    //   query: {
    //     id: 1,
    //     change_components: '8'
    //   }
    // })
  },
  methods: {
    // 初始化数据  eventDeatil
    handleToInit () {
      if (!this.$route.query.isAdd) {
        let id = this.$route.query.id
        console.log(id)
        eventDeatil(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            let { eventName, eventTime, eventDesc, calendarId, hasAct, actInfo } = res.content
            this.ruleForm.eventName = eventName
            this.ruleForm.date = eventTime
            this.richText = eventDesc
            this.backText = eventDesc
            this.calendarId = calendarId
            this.hasAct = hasAct
            // this.haveChoiseData = actInfo
            // 处理营销活动数据
            this.handleToActData(actInfo)
          }
        })
      }
    },
    // 处理营销活动数据
    handleToActData (actInfo) {
      console.log(actInfo)
      let arr = []
      actInfo.forEach((itemP, indexP) => {
        Object.keys(this.activityData).forEach((item, index) => {
          this.activityData[item].forEach((itemC, indexC) => {
            if (itemC.activityType === itemP.activityType) {
              let obj = {
                imgUrl: itemC.imgUrl,
                title: itemC.title,
                activityType: itemC.activityType,
                delFlag: 0,
                choiseActData: {
                  id: itemP.id,
                  actStatus: itemP.actStatus,
                  actName: itemP.actName,
                  endTime: itemP.endTime,
                  isPermanent: itemP.isPermanent,
                  startTime: itemP.startTime,
                  activityType: itemP.activityType,
                  calActId: itemP.calActId
                }
              }
              arr.push(obj)
            }
          })
        })
      })
      console.log(arr)
      this.ruleFormBottom.haveChoiseData = arr
    },
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
      // 查询所有可用营销活动

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
      let { imgUrl, title, activityType } = item
      console.log(item)
      let obj = {
        imgUrl: imgUrl,
        title: title,
        activityType: activityType,
        delFlag: 0,
        choiseActData: {
          id: -1,
          actStatus: '',
          actName: '',
          startTime: '',
          endTime: '',
          isPermanent: '',
          activityType: ''
        }
      }
      this.ruleFormBottom.haveChoiseData.push(obj)
      this.choiseActivity = false
    },
    // 删除icon点击
    handleToClickDel (index) {
      this.delIndex = index
      this.isClickIconDel = false
      this.delDialogVisible = true
    },
    // 二次删除确定
    handleToDelSure () {
      // this.haveChoiseData.splice(this.delIndex, 1)
      console.log(this.haveChoiseData, this.delIndex)
      // this.$set(this.haveChoiseData[this.delIndex], this.delFlag, 1)
      this.ruleFormBottom.haveChoiseData[this.delIndex].delFlag = 1
      this.delDialogVisible = false
      console.log(this.haveChoiseData)
    },
    // 选择具体活动
    handleToChoiseDetail (item, index) {
      console.log(item)
      this.handleToInitActData(item)
      this.clickChoiseIndex = index
      this.isClickIconChoiseAct = false
      this.detailActVisible = true
      this.$refs.singleTable.setCurrentRow()
    },
    // 具体活动弹窗初始化请求数据
    handleToInitActData (item) {
      let params = {
        activityType: item.activityType,
        currentPage: this.pageParams.currentPage
      }
      allMarketList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    },
    // 选择具体活动弹窗确定事件
    handleToChoiseDetilSure () {
      console.log('触发')
      // this.chioseDetailVal
      let { id, actName, actStatus, startTime, endTime, isPermanent, activityType } = this.chioseDetailVal
      let obj = {
        id: id,
        actStatus: actStatus,
        actName: actName,
        startTime: startTime,
        endTime: endTime,
        isPermanent: isPermanent,
        activityType: activityType
      }
      this.ruleFormBottom.haveChoiseData[this.clickChoiseIndex].choiseActData = obj
      this.detailActVisible = false
    },
    // 具体活动表格选中
    handleCurrentChange (val) {
      console.log(val)
      this.chioseDetailVal = val
    },
    // 点击保存
    handleToSave () {
      console.log(this.haveChoiseData)
      // calActId
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.$refs['ruleFormBottom'].validate((valid2) => {
            console.log(valid2)
            if (valid2) {
              let act = ''
              let calendarAct = []
              let params = {}
              if (this.$route.query.isAdd) {
                act = 'add'
                this.ruleFormBottom.haveChoiseData.forEach((item, index) => {
                  let obj = {
                    activityType: item.activityType,
                    activityId: 0
                  }
                  if (item.choiseActData.id !== -1) {
                    obj.activityId = item.choiseActData.id
                  }
                  calendarAct.push(obj)
                })
                params = {
                  'act': act,
                  'eventName': this.ruleForm.eventName,
                  'eventTime': this.ruleForm.date + ' 00:00:00',
                  'eventDesc': this.richText,
                  'calendarAct': calendarAct
                }
              } else {
                act = 'edit'
                this.ruleFormBottom.haveChoiseData.forEach((item, index) => {
                  let obj = {
                    activityType: item.activityType,
                    activityId: 0,
                    delFlag: 0
                  }
                  if (item.choiseActData.id !== -1) {
                    obj.activityId = item.choiseActData.id
                    obj.delFlag = item.delFlag
                    if (item.choiseActData.calActId !== undefined) {
                      obj.calActId = item.choiseActData.calActId
                    }
                    console.log(item.choiseActData.calActId)
                  }
                  calendarAct.push(obj)
                })
                params = {
                  'act': act,
                  'eventName': this.ruleForm.eventName,
                  'eventTime': this.ruleForm.date + ' 00:00:00',
                  'eventDesc': this.richText,
                  'calendarAct': calendarAct,
                  'calendarId': this.calendarId
                }
              }
              console.log(params, this.haveChoiseData)
              saveEvent(params).then(res => {
                console.log(res)
                if (res.error === 0) {
                  this.$message.success({
                    message: '保存成功',
                    showClose: true
                  })
                  this.$router.push({
                    name: 'calendar'
                  })
                }
              })
            }
          })
        }
      })
    },
    // 选中活动四个icon综合处理
    handleToAllHiddenIcon (flag, index, item) {
      console.log(flag, index, item)
      let arr = ['pin_group', 'bargain', 'group_draw', 'pin_integration', 'lottery', 'promote', 'share_award', 'coupon_package', 'reduce_price', 'pay_reward', 'first_special', 'pre_sale', 'seckill_goods', 'gift', 'full_cut', 'package_sale', 'purchase_price', 'free_ship', 'integral_goods', 'activity_reward', 'comment_gift']
      // 跳转
      let jmp = ['pin_group', 'kanjia', 'group_draw', 'pin_integration', 'lottery_activity', 'promote', 'share_award', 'coupon_package', 'reduce_price', 'payreward', 'first_special', 'presale', 'sec_kill', 'gift', 'full_cut', 'package', 'purchase_price', 'free_ship', 'integral_convert', 'market_gifted', 'comment_gift']
      switch (flag) {
        case 1:
          this.$router.push({
            name: jmp[arr.indexOf(item.activityType)],
            params: {
              calenderEdit: true,
              id: item.choiseActData.id
            }
          })
          break
        case 2:
          this.$router.push({
            name: jmp[arr.indexOf(item.activityType)]
          })
          break
        case 3:
          this.isClickIconChoiseAct = true
          this.detailActVisible = true
          this.$refs.singleTable.setCurrentRow()
          break
        case 4:
          this.delIndex = index
          this.isClickIconDel = true
          this.delDialogVisible = true
          break
      }
    },
    // 点击新建活动
    handleToAddNewAct (item) {
      console.log(item)
      // 多人拼团 pin_group
      let arr = ['pin_group', 'bargain', 'group_draw', 'pin_integration', 'lottery', 'promote', 'share_award', 'coupon_package', 'reduce_price', 'pay_reward', 'first_special', 'pre_sale', 'seckill_goods', 'gift', 'full_cut', 'package_sale', 'purchase_price', 'free_ship', 'integral_goods', 'activity_reward', 'comment_gift']
      // 跳转
      let jmp = ['pin_group', 'bargain_activity', 'group_draw', 'pin_integration', 'lottery_activity', 'promote_activity', 'share_polite_add', 'coupon_Package_add', 'reduce_price', 'addPayRewardAct', 'first_special_add', 'presale_add_view', 'sec_kill', 'gift_add_view', 'full_cut', 'package', 'add_increase_purchase', 'free_ship', 'integral_convert', 'market_gifted', 'comment_gift']
      this.$router.push({
        name: jmp[arr.indexOf(item.activityType)],
        params: {
          calenderAdd: true
        }
      })
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
    height: 470px;
    overflow: auto;
  }
}
</style>
