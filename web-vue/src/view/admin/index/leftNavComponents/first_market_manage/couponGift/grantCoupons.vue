<template>
  <wrapper>
    <div class="content">
      <div class="main">
        <el-form
          label-width="150px"
          labelPosition='right'
          :rules="rules"
          :model="form"
          size="small"
        >
          <!-- 活动名称 -->
          <el-form-item
            label="活动名称"
            prop="actName"
          >
            <el-input
              size="small"
              placeholder="请输入活动名称"
              class="morelength"
              v-model="form.actName"
            ></el-input>
          </el-form-item>
          <!-- 参与活动人群 -->
          <el-form-item :label="labels.label6">
            <div>
              <span style="color:#999;fontSize:12px">以下筛选条件为“或”关系</span>
            </div>
            <div>
              <el-checkbox
                v-model="params.onClickNoPay"
                @change="handleOnClickNoPayChange"
              >加购人群</el-checkbox>
              <span style="color:#999;fontSize:12px;margin-left:-15px">30天内在本店内有加入购物车行为，但没有支付的用户</span>
            </div>
            <div>
              <el-checkbox
                v-model="params.onClickGoods"
                @change="handleOnClickGoodsChange"
              >指定购买商品人群 </el-checkbox>
              <span style="color:#999;fontSize:12px">最多可选择3件商品</span>
            </div>
            <div class="chooseGoods">
              <div class="chooseGoodsLeft">选择商品</div>
              <ul class="imgList">
                <li
                  v-for="(item) in imgsList"
                  :key="item.goodsId"
                >
                  <el-image
                    style="width: 80px; height: 80px"
                    :src="item.goodsImg"
                  ></el-image>
                  <el-image
                    class="delImg"
                    :src="urls.url4"
                    @click="handleDelImg(item.goodsId)"
                  >

                  </el-image>
                </li>
                <div
                  v-show="this.imgsList.length<3"
                  class="imageWraper"
                  @click="handleChooseGoods"
                >
                  <el-image :src="urls.url3"></el-image>
                </div>
              </ul>

            </div>
            <!-- 属于 -->
            <!-- 持有 -->
            <div style="margin:10px 0">
              <chooseSelect @chooseSelectVal="getChooseSelectVal" />
            </div>
            <div style="margin:10px 0">
              <el-checkbox
                v-model="params.onClickUser"
                @change="handleOnClickUserChange"
              >选择指定的会员 </el-checkbox>
              <span style="margin-left:-15px">
                <el-button
                  @click="handleAddMember"
                  type="text"
                >+ 添加会员</el-button>
              </span>
              <span>已选择会员{{memberNum}}人</span>
            </div>
            <div>
              <el-checkbox
                v-model="params.onClickCustomRule"
                @change="handleOnClickCustomRuleChange"
              >自定义</el-checkbox>
              <el-select
                :disabled="!params.onClickCustomRule"
                v-model="customRuleInfoVal"
                placeholder="请选择"
                size="small"
                @change="customRuleInfoValChange"
              >
                <el-option
                  label="请选择"
                  value="请选择"
                ></el-option>
                <el-option
                  v-for="item in customRuleInfoOptions"
                  :key="item.key"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <!-- 自定义集合 -->
              <div style="margin:10px 0;">
                <ul class="ulList">
                  <li
                    v-for="(item) in optionsList"
                    :key="item.key"
                  >
                    <span>{{item.label}}：</span>
                    <span>
                      <el-input
                        @blur="handleIpt(item)"
                        @focus="handleIpt(item)"
                        :disabled="!params.onClickCustomRule"
                        style="width:120px"
                        size="small"
                        v-model="item.ipt"
                      > </el-input>
                      <span>{{ item.label | filterA  }}</span>
                    </span>
                    <div class="img_span">
                      <el-image
                        :src="urls.url4"
                        class="img"
                        @click="handleDelCustomize(item)"
                      ></el-image>
                    </div>
                  </li>
                  <li v-show="showTime">
                    <span>指定时间内有登陆记录：</span>
                    <div class="img_span">
                      <el-image
                        :src="urls.url4"
                        class="img"
                        @click="handleDelCustomize(6)"
                      ></el-image>
                    </div>
                    <span>
                      <dateTimePicker
                        :showPicker=1
                        @time="loginStartAndLoginEnd"
                      />
                    </span>

                  </li>
                </ul>
              </div>
            </div>
          </el-form-item>
          <!-- 选择优惠券 -->
          <el-form-item
            label="选择优惠券"
            prop="coupon"
          >
            <div class="gray">最多添加5张优惠券，已过期和已停用的优惠券不能添加</div>
            <span>{{couponId}}</span>
            <el-button @click="handleToCallDialog">选择优惠券</el-button>
          </el-form-item>
          <!-- 发送时间 -->
          <el-form-item
            label="发送时间"
            prop="sendAction"
          >
            <el-radio
              label="0"
              v-model="form.sendAction"
            >立即发送</el-radio>
            <br>
            <el-radio
              label="1"
              v-model="form.sendAction"
            >定时发送</el-radio>
            <el-date-picker
              type="datetime"
              class="morelength"
              size="small"
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="form.startTime"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div class="footer">
        <el-button
          type="primary"
          size="small"
          @click="addAct"
        >确认发放</el-button>
      </div>
      <!-- 添加会员的弹窗 -->
      <memberListDialog
        v-if="dialogOff"
        @userIdList="getUserIdList"
        :memberListDialog="dialogOff"
        @dialog-cancel="closeDialog"
      />
      <!-- 选择商品弹窗 -->
      <choosingGoods
        @res="getRes"
        :tuneUpChooseGoods="tuneUpChooseGoods"
        :chooseGoodsBack="params.goodsIdList"
      />
      <!-- 获取人群弹窗 -->
      <getUserDialog
        @dialog-cancel="closeDialog"
        :dialogVisible="dialogVisible"
      />
      <!--添加优惠卷弹窗-->
      <addCouponDialog @checkReturnFormat="handleToCheck" />
    </div>
  </wrapper>
</template>>

<script>
// import { mapActions } from 'vuex'
// 选择商品弹窗
import choosingGoods from '@/components/admin/choosingGoods'

import memberListDialog from '../messagePush/memberListDialog'
import getUserDialog from '../messagePush/getUserDialog'
import chooseSelect from '@/components/admin/chooseSelect/chooseSelect'
import wrapper from '@/components/admin/wrapper/wrapper'
// import choosingGoods from '@/components/admin/choosingGoods'
import { addActivity } from '@/api/admin/marketManage/couponGift.js'
// // import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
// // import { selectGoodsApi } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods.js'
import { delObj } from '@/util/formatData'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
import { getUserNumberApi } from '@/api/admin/marketManage/messagePush.js'
import addCouponDialog from '@/components/admin/addCouponDialog'

export default {
  components: {
    wrapper,
    dateTimePicker,
    chooseSelect,
    memberListDialog,
    choosingGoods,
    getUserDialog,
    addCouponDialog

    // choosingGoods,
    // AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  data () {
    return {
      // 优惠卷弹窗
      couponDialogFlag: false,
      couponData: [],
      couponId: '',
      checkedData: [],
      // 初始化弹窗选中的行
      urls: {
        url1: `${this.$imageHost}/image/admin/notice_img.png`,
        url2: `${this.$imageHost}/image/admin/shop_logo_default.png`,
        url3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        url4: `${this.$imageHost}/image/admin/icon_delete.png`
      },
      /**
       * form表单的数据
       */
      form: {
        actName: ``,
        sendAction: ``,
        startTime: ``
      },
      /**
       * 表单检验
       */
      rules: {
        actName: [
          { required: true, message: '请填写消息名称', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ]
      },
      templateId: null,
      labels: {
        label1: `消息名称：`,
        label2: `消息类型：`,
        label3: `业务标题：`,
        label4: `业务内容：`,
        label5: `进入小程序查看：`,
        label6: `参与活动人群：`,
        label7: `发送时间：`
      },
      cardList: [

      ],
      cardValue: `请选择会员卡`,
      /**
      * 动态获取人数的参数集合
      */
      params: {
        userKey: null,
        onClickNoPay: false,
        onClickGoods: false,
        // 商品列表idList
        onClickCard: false,
        cardIdsList: [],
        onClickTag: false,
        tagIdList: [],
        onClickUser: false,
        userIdList: [],
        onClickCustomRule: false,
        customRuleInfo: {
          payedDay: ``,
          noPayDay: ``,
          buyTimesMore: ``,
          buyTimesLess: ``,
          moneyAvgMore: ``,
          moneyAvgLess: ``,
          loginStart: ``,
          loginEnd: ``
        }
      },
      /**
       * params
       */
      senAction: 1,
      startTime: ``,
      endTime: ``,
      startTime1: ``,
      onClickNoPay: false, // 勾选加购人群
      onClickGoods: false, // 勾选购买指定商品人群
      goodsIdList: [], // 商品ID集合
      onClickCard: false,
      cardIdsList: [],
      onClickTag: false,
      tagIdList: [],
      onClickUser: false, // 勾选指定会员
      userIdList: [],
      onClickCustomRule: false,
      disabledOnClickCustomRule: false,
      pageLink: ``,
      time: {},

      /**
       * 传给组件chooseSelect的数据
       */

      dialogOff: false,
      whetherShowDialog: false,
      dialogVisible: false,
      memberNum: 0, // 已选择的会员人数
      /**
       * 自定义实体
       */
      customRuleInfoVal: `请选择`,
      loginStart: ``,
      loginEnd: ``,
      customRuleInfoOptions: [
        {
          label: `N天内有交易记录`,
          value: `N天内有交易记录`,
          key: `payedDay`,
          ipt: ``
        },
        {
          label: `N天内没有交易记录`,
          value: `N天内没有交易记录`,
          key: `noPayDay`,
          ipt: ``

        },
        {
          label: `累计购买次数小于N次`,
          value: `累计购买次数小于N次`,
          key: `buyTimesLess`,
          ipt: ``

        },
        {
          label: `累计购买次数大于N次`,
          value: `累计购买次数大于N次`,
          key: `buyTimesMore`,
          ipt: ``

        },
        {
          label: `购买商品均价大于N元`,
          value: `购买商品均价大于N元`,
          key: `moneyAvgMore`,
          ipt: ``

        },
        {
          label: `购买商品均价小于N元`,
          value: `购买商品均价小于N元`,
          key: `moneyAvgLess`,
          ipt: ``

        },
        {
          label: `指定时间内有登录记录`,
          value: `指定时间内有登录记录`,
          key: `time`

        }
      ],
      optionsList: [],
      showTime: false,
      isShowBtn: false,
      arrList: [],
      imgsList: [],
      userNumber: 0,
      tuneUpChooseGoods: false,
      tuneUpSelectLink: false

    }
  },
  created () {

  },

  methods: {
    // 选择优惠券弹窗
    handleToCallDialog () {
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.couponList
      }
      this.$http.$emit('V-AddCoupon', obj)
    },
    // 优惠卷回调
    handleToCheck (data) {
      console.log('优惠卷', data)
      let couponKey = []
      data.map((item) => {
        couponKey.push(item.id)
      })
      this.couponData = data
      this.couponId = couponKey.toString()
      console.log('conponId', couponKey.toString())
      console.log('conponData', this.couponData)
    },
    // 发放优惠券
    addAct () {
      console.log('params:', this.params)
      let param = {
        'actName': this.form.actName,
        'couponGiveGrantInfoParams': {
          'custom_box': Number(this.params.onClickCustomRule),
          'point_start_time': this.params.customRuleInfo.loginStart,
          'point_end_time': this.params.customRuleInfo.loginEnd,
          'cart_box': Number(this.params.onClickNoPay),
          'card_box': Number(this.params.onClickCard),
          'tag_box': Number(this.params.onClickTag),
          'goods_box': Number(this.params.onClickGoods),
          'goods_ids': '',
          'member_box': Number(this.params.onClickUser),
          'coupon_ids': this.couponId
        },
        'cardId': this.params.cardIdsList.toString(),
        'tagId': this.params.tagIdList.toString(),
        'user': this.params.userIdList.toString(),
        'havePay': this.params.customRuleInfo.payedDay,
        'noPay': this.params.customRuleInfo.noPayDay,
        'maxCount': this.params.customRuleInfo.buyTimesLess,
        'minCount': this.params.customRuleInfo.buyTimesMore,
        'maxAvePrice': this.params.customRuleInfo.moneyAvgLess,
        'minAvePrice': this.params.customRuleInfo.moneyAvgMore,
        'sendAction': this.form.sendAction,
        'startTime': this.form.startTime
      }
      console.log('param:', param)

      addActivity(param).then(res => {
        console.log(res)
        if (res.error === 0) {
          alert('操作成功')
          this.$router.push({
            path: `/admin/home/main/couponGift`
          })
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },
    handleChooseData (data) {
      this.$message({ message: `已经选择了${data.length}条数据！`, type: 'success' })
      this.checkedData = data
    },
    // 关闭会员弹窗
    closeDialog () {
      this.dialogOff = false
      this.whetherShowDialog = false
      this.dialogVisible = false
    },
    // 添加会员
    handleAddMember () {
      if (this.params.onClickUser === false) {
        return
      }
      this.dialogOff = true
    },
    // getUserIdList
    getUserIdList (val) {
      console.log(val)
      this.params.userIdList = this.formatUserIdList(val)
      this.memberNum = val.length // 把选中的数组长度赋值给已选会员数
      // 当添加会员后 发送获取人数接口
      this.fetchUserList(this.params)
    },
    formatUserIdList (userIdList) {
      let arr = []
      userIdList.forEach(item => {
        arr.push(item.userId
        )
      })
      return arr
    },
    // getContent
    getContent (res) {
      this.formData.content = res.content
      this.templateId = res.id
    },
    // 选择商品
    handleChooseGoods () {
      if (this.params.onClickGoods === false) {
        return
      }
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    getRes (ids, urls) {
      if (ids.length > 3) {
        this.$message.warning('最多选择3个商品')
      } else {
        this.params.goodsIdList = ids
        this.imgsList = urls
        // 发送获取人数
        this.fetchUserList(this.params)
      }
    },
    // 删除图片
    handleDelImg (id) {
      if (this.params.onClickGoods === false) {
        return
      }
      this.imgsList = this.imgsList.filter(item => item.goodsId !== id)
      this.params.goodsIdList = this.goodsIdList.filter(item => item !== id)
      this.fetchUserList(this.params)
    },
    // 获取选中的path
    getPath (res) {
      console.log(res)
      this.pageLink = res
    },
    // 获取持有属于的值
    getChooseSelectVal (val) {
      const { onClickCard, cardIdsList, onClickTag, tagIdList } = val
      this.params.onClickCard = onClickCard
      this.params.cardIdsList = cardIdsList
      this.params.onClickTag = onClickTag
      this.params.tagIdList = tagIdList
      // 请选择会员卡
      switch (onClickCard) {
        case true:
          console.log(this.params)
          this.fetchUserList(this.params)
          break
        case false:
          this.fetchUserList(this.params)
          break
        default:
          break
      }
      // 请选择会员标签
      switch (onClickTag) {
        case true:
          this.fetchUserList(this.params)
          break
        case false:
          this.fetchUserList(this.params)
          break
        default:
          break
      }
    },
    handleGetUser () {
      this.dialogVisible = true
    },
    // 获取发送人群数量
    fetchUserList (params) {
      getUserNumberApi(params).then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(res) // 返回发送人群的数量
          const { userKey, userNumber } = content
          console.log(`key+num${userKey}, ${userNumber}`)
          this.userNumber = userNumber
          this.params.userKey = userKey
        }
      }).catch(err => console.log(err))
    },
    // 加购人群发生变化的时候
    handleOnClickNoPayChange (val) {
      // 获取发送人群的数量
      console.log(this.params)
      this.fetchUserList(this.params)
    },
    // 指定购买商品人群发生变化的时候
    handleOnClickGoodsChange (val) {
      console.log(this.params)
      this.fetchUserList(this.params)
    },
    // 选择指定的会员状态发生变化的时候
    handleOnClickUserChange (val) {
      console.log(this.params)
      this.fetchUserList(this.params)
    },
    // 当自定义发生变化的时候
    handleOnClickCustomRuleChange (val) {
      this.fetchUserList(this.params)
    },
    loginStartAndLoginEnd (val) {
      const { startTime, endTime } = val
      this.loginStart = startTime
      this.loginEnd = endTime
      this.params.customRuleInfo.loginStart = this.loginStart
      this.params.customRuleInfo.loginEnd = this.loginEnd
      this.fetchUserList(this.params)
    },
    //
    customRuleInfoValChange (val) {
      console.log(val)
      if (val === `指定时间内有登录记录`) {
        this.showTime = true
        this.customRuleInfoOptions = delObj({ arr: this.customRuleInfoOptions, val })
        this.customRuleInfoVal = `请选择`
      } else {
        if (val !== `请选择` && val !== `指定时间内有登陆记录`) {
          const res = this.customRuleInfoOptions.find(item => item.value === val)
          this.optionsList.push(res)
          this.customRuleInfoOptions = this.customRuleInfoOptions.filter(item => item.value !== res.value)
          this.customRuleInfoVal = `请选择`
        }
      }
    },
    // 输入框发生变化的时候
    handleIpt (item) {
      console.log(item)
      const { key, ipt } = item
      for (let a in this.params.customRuleInfo) {
        if (a === key) {
          switch (a) {
            case `payedDay`: this.params.customRuleInfo.payedDay = ipt
              break
            case `noPayDay`: this.params.customRuleInfo.noPayDay = ipt
              break
            case `buyTimesMore`: this.params.customRuleInfo.buyTimesMore = ipt
              break
            case `buyTimesLess`: this.params.customRuleInfo.buyTimesLess = ipt
              break
            case `moneyAvgMore`: this.params.customRuleInfo.moneyAvgMore = ipt
              break
            case `moneyAvgLess`: this.params.customRuleInfo.moneyAvgLess = ipt
              break
            default:
              break
          }
        }
      }
      console.log(this.params.customRuleInfo)
      this.fetchUserList(this.params)
    },
    // 删除自定义
    handleDelCustomize (val) {
      // 自定义勾选状态为false 删除不可点
      if (this.params.onClickCustomRule === false) {
        return
      }
      if (val === 6) {
        this.showTime = false
        this.customRuleInfoOptions.push({
          label: `指定时间内有登录记录`,
          value: `指定时间内有登录记录`
        })
        this.params.customRuleInfo.loginEnd = ``
        this.params.loginStart.loginStart = ``
        this.fetchUserList(this.params)
      } else {
        console.log(val)
        for (let a in this.params.customRuleInfo) {
          if (a === val.key) {
            switch (a) {
              case `payedDay`: this.params.customRuleInfo.payedDay = ``
                break
              case `noPayDay`: this.params.customRuleInfo.noPayDay = ``
                break
              case `buyTimesMore`: this.params.customRuleInfo.buyTimesMore = ``
                break
              case `buyTimesLess`: this.params.customRuleInfo.buyTimesLess = ``
                break
              case `moneyAvgMore`: this.params.customRuleInfo.moneyAvgMore = ``
                break
              case `moneyAvgLess`: this.params.customRuleInfo.moneyAvgLess = ``
                break
              default:
                break
            }
          }
        }
        console.log(this.params.customRuleInfo)
        this.optionsList = this.optionsList.filter(item => item.key !== val.key)
        this.customRuleInfoOptions.unshift(val)
        this.fetchUserList(this.params)
      }
    },
    // handleShowBtn
    handleShowBtn () {
      if (this.formData.content !== ``) {
        this.isShowBtn = true
      } else {
        this.isShowBtn = false
      }
    }
  },
  filters: {
    filterA: function (val) {
      // console.log(val.search('N'))
      if (val.search('N') !== -1) {
        return val.substr(val.search('N') + 1, 1)
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.inputTip {
  color: #999;
  margin-left: 15px;
}
.coupon_info {
  display: flex;
  flex-direction: column;
  width: 200%;
  line-height: 25px;
  margin: 0 auto;
  margin-bottom: -10px;
  .coupon_item {
    margin-bottom: 10px;
    height: 50px;
    display: flex;
    > div {
      background-color: #fff;
      height: 100px;
    }
    .coupon_left {
      width: 100px;
      text-align: center;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      .coupon_price {
        color: #f66;
        margin-bottom: 10px;
        > span {
          font-size: 18px;
          font-weight: 600;
        }
      }
      .coupon_rule {
        color: #999;
        font-size: 14px;
      }
    }
    .coupon_middle {
      width: 25px;
      background: none;
      > img {
        width: 100%;
        height: 100%;
      }
    }
    .coupon_right {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: flex-start;
      padding-left: 10px;
      position: relative;
      overflow: hidden;
      .coupon_name {
        font-weight: bold;
        font-size: 14px;
        margin-bottom: 5px;
        width: 100%;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
      .coupon_limits {
        margin-bottom: 15px;
        font-size: 13px;
      }
      .coupon_time {
        color: #999;
        font-size: 12px;
      }
      .coupon_icon {
        position: absolute;
        right: -15px;
        top: 8px;
        background: #fead2d;
        width: 64px;
        font-size: 12px;
        color: #fff;
        transform: rotate(40deg);
        text-align: center;
      }
    }
  }
}
.content {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  padding-bottom: 50px;
  .main {
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    .el-form-item {
      margin-bottom: 16px;
    }
    .el-input {
      width: 90px;
    }
    .morelength {
      width: 200px;
    }
    .chooseGoods {
      width: 120px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      border: 1px solid #ccc;
    }
    .gray {
      color: #999;
    }
  }
}
.ImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
}
.deleteIcon {
  width: 17px;
  height: 17px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 17px;
  text-align: center;
  position: relative;
  top: -41px;
  right: -95px;
  cursor: pointer;
  opacity: 0.8;
}
.ImgWrap .moveIcon {
  width: 17px;
  height: 17px;
  display: none;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  line-height: 17px;
  text-align: center;
  position: absolute;
  bottom: 0px;
  cursor: pointer;
  opacity: 0.8;
}
.ImgWrap:hover .moveIcon {
  display: block;
}
.selectedWrap {
  min-width: 70px;
  height: 22px;
  border: 1px solid #ccc;
  line-height: 22px;
  text-align: center;
  padding: 0px 5px;
  margin: 0px 5px;
  background-color: #fff;
  position: relative;
}
.footer {
  padding: 10px 0px 10px 0px;
  text-align: center;
  background: #f8f8f8;
  margin-top: 10px;
  position: fixed;
  bottom: 0;
  z-index: 1;
  width: 100%;
}
// .ulList {
//   width: 100%;
//   li {
//     margin: 5px 0;
//     span {
//       margin: 0 5px;
//     }
//     .img_span {
//       position: relative;
//       .img {
//         position: absolute;
//         right: 25px;
//         top: -22px;
//         cursor: pointer;
//       }
//     }
//   }
// }
</style>
