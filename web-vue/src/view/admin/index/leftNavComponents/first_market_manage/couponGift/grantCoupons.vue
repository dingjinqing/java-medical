<template>
  <wrapper>
    <div class="content">
      <div class="main">
        <el-form
          :model="form"
          label-width="150px"
          labelPosition='right'
          :rules="formRules"
          ref="form"
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
          <!-- 选择人群 -->
          <el-form-item
            label="选择人群"
            prop=""
          >
            <div class="gray">以下筛选条件为"或"的关系</div>
            <el-checkbox-group v-model="form.checkList">
              <el-checkbox label="cartBox">
                <div style="display: flex">
                  <span>加购人群</span>
                  <div class="gray"> 30天内在本店内有加入购物车行为，但没有支付的用户</div>
                </div>
              </el-checkbox>
              <br />
              <el-checkbox label="goodsBox">
                <div style="display: flex">
                  <span>购买指定商品人群</span>
                  <div class="gray"> 最多可选择3件商品</div>
                </div>
              </el-checkbox><br>
              <el-checkbox label="cardBox">持有
                <el-select
                  v-model="membershipCardVal"
                  placeholder="请选择会员卡"
                  size="small"
                >
                  <el-option
                    v-for="(item,index) in membershipCardOptions"
                    :key="index"
                    :label="item.cardName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>会员卡人群
              </el-checkbox><br>
              <el-checkbox label="tagBox">属于
                <el-select
                  filterable
                  v-model="labelDialogInput"
                  multiple
                  placeholder="请选择会员标签"
                  size="small"
                >
                  <el-option
                    v-for="(item,index) in tagSource"
                    :key="index"
                    :label="item.value"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>标签人群
              </el-checkbox><br>
              <el-checkbox label="选择指定的会员 已选择会员 0 人"></el-checkbox><br>

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

              <br>
            </el-checkbox-group>
          </el-form-item>
          <!-- 选择优惠券 -->
          <el-form-item
            label="选择优惠券"
            prop="coupon"
          >
            <div class="gray">最多添加5张优惠券，已过期和已停用的优惠券不能添加</div><br>
          </el-form-item>
          <!-- 发送时间 -->
          <el-form-item
            label="发送时间"
            prop="sendAction"
          >
            <el-radio
              v-model="form.sendAction"
              label="0"
            >立即发送</el-radio>
            <br>
            <el-radio
              v-model="form.sendAction"
              label="1"
            >定时发送</el-radio>
            <el-date-picker
              v-model="form.startTime"
              type="datetime"
              class="morelength"
              size="small"
              value-format="yyyy-MM-dd HH:mm:ss"
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
    </div>
  </wrapper>
</template>>

<script>
// import { mapActions } from 'vuex'
import { allUserCardRequest, allTagRequest } from '@/api/admin/membershipList.js'

import wrapper from '@/components/admin/wrapper/wrapper'
// import choosingGoods from '@/components/admin/choosingGoods'
import { addActivity } from '@/api/admin/marketManage/couponGift.js'
// // import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
// // import { selectGoodsApi } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods.js'
import { delObj } from '@/util/formatData'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'

export default {
  components: {
    wrapper,
    dateTimePicker
    // choosingGoods,
    // AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  data () {
    return {
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
      urls: {
        url1: `${this.$imageHost}/image/admin/notice_img.png`,
        url2: `${this.$imageHost}/image/admin/shop_logo_default.png`,
        url3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        url4: `${this.$imageHost}/image/admin/icon_delete.png`
      },
      showTime: false,
      // 表单
      form: {
        actName: '',
        checkList: [],
        couponGiveGrantInfoParams: {},
        sendAction: '',
        startTime: ''
      },
      membershipCardVal: '',
      membershipCardOptions: [],
      labelDialogInput: '',
      tagSource: [],
      customInfo: '',
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
      // 表单约束
      formRules: {
        actName: [
          { required: true, message: '此处不能为空!', trigger: 'blur' }
        ],
        sendAction: [
          { required: true, message: '此处不能为空!', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    // 初始化会员卡下拉框列表
    this.getAllUserCard()

    // 初始化标签数据
    this.getAllTag()
  },

  methods: {
    // 获取会员卡
    getAllUserCard () {
      allUserCardRequest().then(res => {
        console.log('--------------------------')
        console.log(res.content)
        this.membershipCardOptions = res.content
      })
    },
    // 获取标签
    getAllTag () {
      console.log('-------------获取所有标签------------')
      allTagRequest().then(res => {
        console.log(res.content)
        this.tagSource = res.content
      })
    },
    // 发放优惠券
    addAct () {
      let addParam = {
        'actName': this.form.actName,
        'sendAction': this.form.sendAction,
        'startTime': this.form.startTimes
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          addActivity(addParam).then(res => {
            console.log(res)
            if (res.error === 0) {
              alert('操作成功')
              this.$router.push({
                name: 'couponGift'
              })
            }
          }).catch(() => {
            this.$message.error('操作失败')
          })
        } else {
          this.$message.error('数据不符合要求')
          return false
        }
      })
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
</style>
