<template>
  <div class="membershipCard">
    <div class="membershipCardMain">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="普通会员卡"
          name="first"
        >
          <div class="firstDiv">
            <div
              class="firstListDiv"
              v-for="(item,index) in cardData"
              :key="index"
            >
              <div
                class="firstTop"
                :style="getStyle(item,index)"
              >
                <img
                  v-if="item.flag === 2"
                  class="hidden"
                  :src="$imageHost+'/image/admin/card_no_use.png'"
                >
                <div class="card_status">
                  <span v-if="item.flag === 1">
                    {{$t('memberCard.tips')}}
                  </span>
                  <span v-else-if="item.flag === 2">
                    {{$t('memberCard.noUser')}}
                  </span>
                  <span v-else-if="item.flag === 3">
                    {{$t('memberCard.expired')}}
                  </span>
                </div>
                <div class="card_info_box">
                  <img
                    class="user_head"
                    :src="item.avatar"
                  >
                  <div class="card_info_Right">
                    <p class="cardName">{{item.cardName}}</p>
                    <div class="time">
                      <span v-if="item.expireType === 0">
                        <!-- 固定日期 -->
                        {{item.startTime}}到{{item.endTime}}
                      </span>
                      <span v-else-if="item.expireType === 1">
                        <!-- 自领取多少内有效 -->

                        {{$t('memberCard.fromDate')}} {{item.receiveDay}}
                        {{ item.dateType===0 ? $t('memberCard.endDayDate'):
                            item.dateType===1 ? $t('memberCard.endWeekDate'): $t('memberCard.endMonthDate')}}
                      </span>
                      <span v-else-if="item.expireType === 2">
                        <!-- 永久有效 -->
                        {{$t('memberCard.ForeverEffective')}}
                      </span>
                    </div>
                  </div>
                  <div class="card_edit">
                    <div @click="handleToTips(0,item,index,0)">

                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="编辑"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_edit.png'">
                      </el-tooltip>
                    </div>
                    <div
                      style="margin:0 5px"
                      @click="handleToTips(1,item,index,0)"
                    >
                      <el-tooltip
                        class="item"
                        effect="dark"
                        :content="item.flag===1?'分享':'删除'"
                        placement="top-start"
                      >
                        <img :src="item.flag===1?item.useIcon.img2:item.noUseIcon.img1">
                      </el-tooltip>
                    </div>

                    <div @click="handleToTips(2,item,index,0)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        :content="item.flag===2?'启用':'停用'"
                        placement="top-start"
                      >
                        <img :src="item.flag===2?item.useIcon.img1:item.noUseIcon.img2">
                      </el-tooltip>
                    </div>

                  </div>
                </div>
              </div>
              <div
                class="card_condition"
                style="width: 260px"
              >
                <p>{{ $t('memberCard.receiveCondition') }}:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">{{ $t('memberCard.memberPower') }}:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                  @click="handleToCardBottom(item,0,itemC)"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div
              class="new_card"
              @click="handleToCardDetail(0)"
            >
              <img :src="new_card_img">
              <span style="color: #9e9e9e;font-size: 15px;padding: 12px 0">{{ $t('memberCard.addMemberCard') }}</span>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="限次会员卡"
          name="second"
        >
          <div class="firstDiv">
            <div
              class="firstListDiv"
              v-for="(item,index) in cardDataSecond"
              :key="index"
            >
              <div
                class="firstTop"
                :style="getStyle(item,index)"
              >
                <img
                  v-if="item.flag === 2"
                  class="hidden"
                  :src="$imageHost+'/image/admin/card_no_use.png'"
                >
                <div class="card_status">
                  <span v-if="item.flag === 1">
                    {{$t('memberCard.tips')}}
                  </span>
                  <span v-else-if="item.flag === 2">
                    {{$t('memberCard.noUser')}}
                  </span>
                  <span v-else-if="item.flag === 3">
                    {{$t('memberCard.expired')}}
                  </span>
                </div>
                <div class="card_info_box">
                  <img
                    class="user_head"
                    :src="item.avatar"
                  >
                  <div class="card_info_Right">
                    <p class="cardName">{{item.cardName}}</p>
                    <div class="time">
                      <span v-if="item.expireType === 0">
                        <!-- 固定日期 -->
                        {{item.startTime}}到{{item.endTime}}
                      </span>
                      <span v-else-if="item.expireType === 1">
                        <!-- 自领取多少内有效 -->

                        {{$t('memberCard.fromDate')}} {{item.receiveDay}}
                        {{ item.dateType===0 ? $t('memberCard.endDayDate'):
                            item.dateType===1 ? $t('memberCard.endWeekDate'): $t('memberCard.endMonthDate')}}
                      </span>
                      <span v-else-if="item.expireType === 2">
                        <!-- 永久有效 -->
                        {{$t('memberCard.ForeverEffective')}}
                      </span>
                    </div>
                  </div>
                  <div class="card_edit">
                    <div @click="handleToTips(0,item,index,1)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="编辑"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_edit.png'">
                      </el-tooltip>
                    </div>
                    <div
                      style="margin:0 5px"
                      @click="handleToTips(1,item,index,1)"
                    >
                      <el-tooltip
                        class="item"
                        effect="dark"
                        :content="item.flag===1?'分享':'删除'"
                        placement="top-start"
                      >
                        <img :src="item.flag===1?item.useIcon.img2:item.noUseIcon.img1">
                      </el-tooltip>
                    </div>
                    <div @click="handleToTips(2,item,index,1)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        :content="item.flag===2?'启用':'停用'"
                        placement="top-start"
                      >
                        <img :src="item.flag===2?item.useIcon.img1:item.noUseIcon.img2">
                      </el-tooltip>
                    </div>

                  </div>
                </div>
              </div>
              <div class="card_condition">
                <p>{{ $t('memberCard.receiveCondition') }}:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">{{ $t('memberCard.memberPower') }}:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                  @click="handleToCardBottom(item,1,itemC)"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div
              class="new_card"
              @click="handleToCardDetail(1)"
            >
              <img :src="new_card_img">
              <span style="color: #9e9e9e;font-size: 15px;padding: 12px 0">{{ $t('memberCard.addMemberCard') }}</span>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="等级会员卡"
          name="third"
        >
          <div class="firstDiv">
            <div
              class="firstListDiv"
              v-for="(item,index) in cardDataThird"
              :key="index"
            >
              <div
                class="firstTop"
                :style="getStyle(item,index)"
              >
                <img
                  v-if="item.flag === 2"
                  class="hidden"
                  :src="$imageHost+'/image/admin/card_no_use.png'"
                >
                <div class="card_status">
                  <span v-if="item.flag === 1">
                    {{$t('memberCard.tips')}}
                  </span>
                  <span v-else-if="item.flag === 2">
                    {{$t('memberCard.noUser')}}
                  </span>
                  <span v-else-if="item.flag === 3">
                    {{$t('memberCard.expired')}}
                  </span>
                </div>
                <div class="card_info_box">
                  <img
                    class="user_head"
                    :src="item.avatar"
                  >
                  <div class="card_info_Right">
                    <p class="cardName">{{item.cardName}}</p>
                    <div class="time">
                      <span v-if="item.expireType === 0">
                        <!-- 固定日期 -->
                        {{item.startTime}}到{{item.endTime}}
                      </span>
                      <span v-else-if="item.expireType === 1">
                        <!-- 自领取多少内有效 -->

                        {{$t('memberCard.fromDate')}} {{item.receiveDay}}
                        {{ item.dateType===0 ? $t('memberCard.endDayDate'):
                            item.dateType===1 ? $t('memberCard.endWeekDate'): $t('memberCard.endMonthDate')}}
                      </span>
                      <span v-else-if="item.expireType === 2">
                        <!-- 永久有效 -->
                        {{$t('memberCard.ForeverEffective')}}
                      </span>
                    </div>
                  </div>
                  <div class="card_edit">
                    <div @click="handleToTips(0,item,index,2)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="编辑"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_edit.png'">
                      </el-tooltip>
                    </div>
                    <div
                      style="margin:0 5px"
                      @click="handleToTips(1,item,index,2)"
                    >
                      <el-tooltip
                        class="item"
                        effect="dark"
                        :content="item.flag===1?'分享':'删除'"
                        placement="top-start"
                      >
                        <img :src="item.flag===1?item.useIcon.img2:item.noUseIcon.img1">
                      </el-tooltip>
                    </div>
                    <div @click="handleToTips(2,item,index,2)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        :content="item.flag===2?'启用':'停用'"
                        placement="top-start"
                      >
                        <img :src="item.flag===2?item.useIcon.img1:item.noUseIcon.img2">
                      </el-tooltip>
                    </div>
                  </div>
                </div>
              </div>
              <div class="card_condition">
                <p>{{ $t('memberCard.receiveCondition') }}:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">{{ $t('memberCard.memberPower') }}:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                  @click="handleToCardBottom(item,2,itemC)"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div
              class="new_card"
              @click="handleToCardDetail(2)"
              v-show="addStatus"
            >
              <img :src="new_card_img">
              <span style="color: #9e9e9e;font-size: 15px;padding: 12px 0">{{ $t('memberCard.addMemberCard') }}</span>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!--二维码弹窗-->
    <ShareCodeDialog />
  </div>
</template>
<script>
import { deleteCardRequest, getAllMemberCardRequest, changeCardStatueRequest } from '@/api/admin/memberManage/memberCard.js'
export default {
  components: {
    ShareCodeDialog: () => import('@/components/admin/shareCodeDialog')
  },
  data () {
    return {
      currentCardType: 0, // 默认的会员卡为普通会员卡
      activeName: 'first',
      cardData: [], // 普通会员卡容器
      detailsOfRights: [], // 功能
      cardDataSecond: [],
      cardDataThird: [],
      addStatus: true, // 等级会员卡，九张时值为none
      new_card_img: this.$imageHost + '/image/admin/add_card.png'
    }
  },
  created () {
    this.clearCardData()
  },
  watch: {

    lang () {
      this.detailsOfRights = this.$t('memberCard.detailsOfRights')
      this.cardData.map(card => {
        this.dealWithAllInfo(card)
      })

      this.cardDataSecond.map(card => {
        this.dealWithAllInfo(card)
      })

      this.cardDataThird.map(card => {
        this.dealWithAllInfo(card)
      })
    },
    //  检测会员卡类型变化 加载数据
    currentCardType () {
      this.loadAllPageData()
    },
    // 标签页变化
    activeName () {
      switch (this.activeName) {
        case 'first':
          this.currentCardType = 0
          break
        case 'second':
          this.currentCardType = 1
          break
        case 'third':
          this.currentCardType = 2
          break
        default:
          break
      }
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化tap切换
    let name = this.$route.name
    switch (name) {
      case 'user_card':
        this.activeName = 'first'
        this.currentCardType = 0
        break
      case 'limitTimes':
        this.activeName = 'second'
        this.currentCardType = 1
        break
      case 'GradeCard':
        this.activeName = 'third'
        this.currentCardType = 2
        break
    }
    this.loadAllPageData()
  },
  methods: {
    // 1- 获取页面所有数据
    loadAllPageData () {
      console.log(this.cardData)
      console.log(this.currentCardType)
      // 如果数据已经存在该组件中就不再向后端请求数据
      switch (Number(this.currentCardType)) {
        case 0:
          if (this.cardData.length > 0) {
            return
          }
          break
        case 1:
          if (this.cardDataSecond.length > 0) {
            return
          }
          break
        case 2:
          if (this.cardDataThird.length > 0) {
            return
          }
          break
        default:
          break
      }
      // 准备数据
      let obj = {
        'currentPage': 0,
        'pageRows': 100,
        'cardType': this.currentCardType
      }
      console.log(obj)
      // 请求后台api
      this.getBackEndData(obj)
    },
    // 2- 处理会员卡权益
    dealWithCardBehavior (card) {
      let content = ''
      // 会员折扣
      if (card.powerCount === 1) {
        content += `${this.$t('membershipIntroduction.memberCount')}${card.disCount}${this.$t('membershipIntroduction.disCount')}`
      }

      // 会员专享商品
      if (card.powerPayOwnGood === 'on') {
        content += this.$t('membershipIntroduction.powerPayOwnGood')
      }

      // 充值奖励
      if (card.powerCard === 1) {
        content += this.$t('membershipIntroduction.powerCard')
      }

      // 积分奖励
      if (card.powerScore === 1) {
        content += this.$t('membershipIntroduction.powerScore')
      }

      // 门店兑换次数
      if (card.count > 0) {
        content += `${this.$t('membershipIntroduction.storeExchange')}${card.count}${this.$t('membershipIntroduction.times')}`
      }

      // 商品兑换次数
      if (card.exchangCount > 0) {
        content += `${this.$t('membershipIntroduction.goodsExchange')}${card.exchangCount}${this.$t('membershipIntroduction.times')}`
      }

      card.equity = content
    },
    // 3- 领取条件
    dealWithReceiveConditions (card) {
      let content = ''
      // 0：直接领取；1：需要购买；2：需要领取码
      if (card.isPay === 0) {
        content += this.$t('memberCard.receiveDirect') + ';'
      } else if (card.isPay === 1) {
        content += this.$t('memberCard.needBuy') + ';'
      } else if (card.isPay === 2) {
        content += this.$t('memberCard.needReceiveCode') + ';'
      }

      // 是否需要激活 0： 否；1： 是
      if (card.activation) {
        content += this.$t('memberCard.activationYes') + ';'
        //  是否审核 0： 否；1： 是
        if (card.examine) {
          content += this.$t('memberCard.examineYes') + ';'
        } else {
          content += this.$t('memberCard.examineNo') + ';'
        }
      } else {
        content += this.$t('memberCard.activationNo') + ';'
      }

      card.conditions = content
    },
    // 4- 处理额外的会员卡功能
    dealWithMemCardExtralRight (card) {
      card.detailsOfRights = []
      // 持卡会员
      card.detailsOfRights.push(this.detailsOfRights[0])
      // 领取详情
      if (card.isPay === 2) {
        card.detailsOfRights.push(this.detailsOfRights[1])
      }
      // 激活审核
      if (card.examine) {
        card.detailsOfRights.push(this.detailsOfRights[2])
      }
      // 普通会员卡与限次卡
      if ([0, 1].includes(this.currentCardType)) {
        card.detailsOfRights.push(...this.detailsOfRights.slice(3))
      }
    },
    // 5- tap切换 会员卡类型切换
    handleClick (tab, event) {
      // 主要目的是更换路由连接，以及面包屑
      this.currentCardType = tab.index
      switch (tab.index) {
        case '0':
          this.$router.push({
            name: 'user_card'
          })
          break
        case '1':
          this.$router.push({
            name: 'limitTimes'
          })
          break
        case '2':
          this.$router.push({
            name: 'GradeCard'
          })
      }

      console.log(tab, event)
    },
    // 6- 清空数据
    clearCardData () {
      this.cardData = []
      this.cardDataSecond = []
      this.cardDataThird = []
    },

    // 删除会员卡
    deleteCard (id) {
      this.$confirm('确认要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteCardReq(id)
        this.$message.success('删除成功!')
      }).catch(() => {
        this.$message.info(
          '已取消删除'
        )
      })
    },
    deleteCardReq (id) {
      deleteCardRequest({ id }).then(res => {
        if (res.error === 0) {
          console.log('删除成功')
          this.getBackEndData({ 'currentPage': 0,
            'pageRows': 100,
            'cardType': this.currentCardType })
        }
      })
    },
    // 停止或启动会员卡
    stopCardStatus (id) {
      let data = {
        id,
        'flag': 2
      }
      changeCardStatueRequest(data).then(res => {
        if (res.error === 0) {
          console.log('停止成功')
          this.loadAllPageData()
          this.getBackEndData({ 'currentPage': 0,
            'pageRows': 100,
            'cardType': this.currentCardType })
        }
      })
    },

    powerCardStatus (id) {
      let data = {
        id,
        'flag': 1
      }
      changeCardStatueRequest(data).then(res => {
        if (res.error === 0) {
          console.log('启动成功')

          this.loadAllPageData()
        }
      })
    },

    // 7- 请求后台数据
    getBackEndData (obj) {
      getAllMemberCardRequest(obj).then(res => {
        if (res.error === 0) {
          let cardData = {}
          // 请求成功
          console.log(res.content.dataList)
          cardData = res.content.dataList
          console.log(cardData)
          // 添加头像
          cardData.map(item => {
            item.useIcon = {
              img2: this.$imageHost + '/image/admin/card_share_new.png',
              img1: this.$imageHost + '/image/admin/card_disable.png'
            }
            item.noUseIcon = {
              img1: this.$imageHost + '/image/admin/card_del.png',
              img2: this.$imageHost + '/image/admin/card_enable.png'
            }

            // 时间格式
            item.startTime = String(item.startTime).split(' ')[0]
            item.endTime = String(item.endTime).split(' ')[0]
            item.avatar = this.$imageHost + '/' + item.avatar

            // 处理会员卡权益
            this.dealWithCardBehavior(item)
            // 处理领取条件
            this.dealWithReceiveConditions(item)

            // 处理会员卡的额外功能
            this.dealWithMemCardExtralRight(item)
            console.log(item)
          })
          // 会员卡
          console.log(this.currentCardType)
          switch (Number(this.currentCardType)) {
            case 0:
              // 普通会员卡
              this.cardData = cardData
              break
            case 1:
              // 限次会员卡
              this.cardDataSecond = cardData
              console.log(this.cardDataSecond)
              break
            case 2:
              // 等级会员卡
              this.cardDataThird = cardData
              // 等级会员卡添加按钮样式控制
              this.changeAddStatus()
              break
            default:
              break
          }
        }
      })
    },
    // 8- 控制等级会员卡添加按钮
    changeAddStatus () {
      if (this.cardDataThird.length >= 9) {
        this.addStatus = false
      }
    },
    // 9- 批量处理格式信息
    dealWithAllInfo (card) {
      this.dealWithCardBehavior(card)
      this.dealWithReceiveConditions(card)
      this.dealWithMemCardExtralRight(card)
    },
    // 动态改变行间样式
    getStyle (item, index) {
      if (item.id === 864) {
        console.log(item)
      }
      /** 会员卡状态 flag 1:使用中，2:停止使用 ,3：过期 */
      if ([2, 3].includes(item.flag)) {
        // 停止状态灰色
        return 'background-color:#ddd'
      }

      /** 会员卡背景 bgType 0： 背景色；1：背景图 */
      if (item.bgType === 0) {
        if (item.bgColor) { // 有颜色，设置为改颜色，无则为默认背景色
          return 'background-color:' + item.bgColor
        } else {
          // 获取初始全局颜色
          let defaultColor = localStorage.getItem('V-backgroundColor')
          if (defaultColor) {
            return `background-color:${defaultColor}`
          }
          return 'background-color:#e6cb96'
        }
      } else {
        if (item.bgImg) { // 有背景图，设置为该背景图，无则为默认背景色
          return 'backgroundImage:url(' + item.bgImg + ')' + ';backgroundRepeat:no-repeat;background-size: 100% 100%;'
        } else {
          // 获取初始全局颜色
          let defaultColor = localStorage.getItem('V-backgroundColor')
          if (defaultColor) {
            return `background-color:${defaultColor}`
          }
          return 'background-color:#e6cb96'
        }
      }
    },
    // 跳转到会员卡详情页
    handleToCardDetail (type, cardId) {
      let obj = { query: { 'cardType': type, cardId } }
      if (type === 0) {
        obj.name = 'normalCardDetail'
      } else if (type === 1) {
        obj.name = 'limitCardDetail'
      } else if (type === 2) {
        obj.name = 'gradeCardDetail'
      }
      console.log(obj)
      this.$router.push(obj)
    },
    // tips系列点击
    handleToTips (flag, item, index, type) {
      console.log(flag, type)
      switch (flag) {
        case 0:
          this.handleToCardDetail(type, item.id)
          break
        case 1:
          // 删除或分享
          console.log('删除或分享')
          if (type === 0) {
            if (item.flag === 1) {
              // 分享
              this.$http.$emit('shareCodeDialog', item)
            } else {
              // 删除
              this.deleteCard(item.id)
            }
          } else if (type === 1) {
            if (item.flag === 1) {
              // 分享
              this.$http.$emit('shareCodeDialog', item)
            } else {
              // 删除
              this.deleteCard(item.id)
            }
          } else if (type === 2) {
            if (item.flag === 1) {
              // 分享
              this.$http.$emit('shareCodeDialog', item)
            } else {
              // 删除
              this.deleteCard(item.id)
            }
          }

          break
        case 2:
          // 停用-启动
          console.log(item)
          console.log(item.flag)
          if (type === 0) {
            // 普通卡
            if (item.flag === 1) {
              // 使用中转化成停用
              this.stopCardStatus(item.id)
              this.cardData[index].flag = 2
            } else {
              // 停止使用转化成使用
              this.powerCardStatus(item.id)
              this.cardData[index].flag = 1
            }
          } else if (item.flag === 1) {
            // 限次卡
            if (item.flag === 1) {
              // 使用中转化成停用
              this.stopCardStatus(item.id)
              this.cardDataSecond[index].flag = 2
            } else {
              // 停止使用转化成使用
              this.powerCardStatus(item.id)
              this.cardDataSecond[index].flag = 1
            }
          } else if (item.flag === 2) {
            console.log('等级卡')
            if (item.flag === 1) {
              // 使用中转化成停用
              this.stopCardStatus(item.id)
              this.cardDataThird[index].flag = 2
            } else {
              // 停止使用转化成使用
              this.powerCardStatus(item.id)
              this.cardDataThird[index].flag = 1
            }
          }
      }
    },
    // 会员卡底部系列点击
    handleToCardBottom (item, flag, type) {
      console.log(type)
      let obj = {
        item,
        flag,
        type
      }
      switch (type) {
        case '持卡会员':
          this.$store.commit('TOCHANFE_CARDCRUMDATA', obj)
          this.$router.push({
            name: 'Cardholder',
            query: {
              cardId: item.id
            }
          })
          break
        case '领取详情':
          this.$store.commit('TOCHANFE_CARDCRUMDATA', obj)
          this.$router.push({
            name: 'receivingDetails',
            query: {
              cardId: item.id
            }
          })
          break
        case '激活审核':
          this.$store.commit('TOCHANFE_CARDCRUMDATA', obj)
          this.$router.push({
            name: 'activateAudit',
            query: {
              cardId: item.id
            }
          })
          break
        case '查看订单':
          this.$store.commit('TOCHANFE_CARDCRUMDATA', obj)
          this.$router.push({
            name: 'viewOrders',
            query: {
              cardId: item.id
            }
          })
          break
        case '充值记录':
          this.$store.commit('TOCHANFE_FILLDETAILCRUMB', obj)
          this.$router.push({
            name: 'refillDetails',
            query: {
              cardId: item.id
            }
          })
      }

      console.log(item, flag)
    }
  }
}
</script>
<style lang="scss" scoped>
.membershipCard {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .membershipCardMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .firstDiv {
      background: #fff;
      padding: 0 1%;
      overflow: hidden;
      min-width: 1000px;
      padding-right: 0;
      .firstListDiv {
        width: 267px;
        float: left;
        margin-right: 1%;
        margin-bottom: 20px;
        background-color: #fff;
        border-radius: 10px;
        height: 255px;
        box-shadow: 0px 2px 16px 0px #e5e5e5;
        .firstTop {
          // background: #990000;
          .hidden {
            position: absolute;
            top: 0;
            left: 0;
          }
          width: 270px;
          height: 150px;
          border-radius: 10px;
          padding-top: 35px;
          position: relative;
          .card_status {
            line-height: 24px;
            padding: 0 10px;
            background-color: rgba(0, 0, 0, 0.3);
            color: #fff;
            position: absolute;
            right: 0;
            top: 0;
            border-top-right-radius: 10px;
            border-bottom-left-radius: 10px;
            font-size: 13px;
          }
          .card_info_box {
            padding: 0 10px;
            .user_head {
              float: left;
              width: 40px;
              height: 40px;
              border-radius: 50%;
            }
            .card_info_Right {
              margin-left: 50px;
              .cardName {
                color: #fff;
              }
              .time {
                margin-top: 4px;
                color: #fff;
                span {
                  width: 200px;
                }
              }
            }
            .card_edit {
              display: flex;
              justify-content: flex-end;
              height: 22px;
              text-align: right;
              position: absolute;
              bottom: 11px;
              right: 14px;
              .item {
                cursor: pointer;
              }
            }
          }
        }
        .card_condition {
          line-height: 15px;
          height: 52px;
          margin: 0 7px;
          border-bottom: 1px solid #eee;
          padding-bottom: 20px;
          margin-top: 10px;
          p {
            font-size: 12px;
            font-weight: 600;
            span {
              font-weight: normal;
            }
          }
        }
        .card_footer {
          margin-top: 10px;
          padding-left: 7px;
          line-height: 20px;
          color: #5a8bff;
          font-size: 12px;
        }
      }
      .new_card {
        float: left;
        width: 270px;
        height: 150px;
        padding: 10px 1%;
        background: #f0f0f0;
        border-radius: 10px;
        margin-bottom: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        cursor: pointer;
      }
    }
  }
}
</style>
