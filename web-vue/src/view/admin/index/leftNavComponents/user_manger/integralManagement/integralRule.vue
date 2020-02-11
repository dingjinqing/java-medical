<template>
  <div>
    <div>
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="140px"
        :label-position="'right'"
      >
        <div class="integralRule">
          <div class="top">{{$t('scoreCfg.prompt')}}</div>
          <div class="title">
            <span></span>
            {{$t('scoreCfg.scoreNormalRule')}}
          </div>
          <el-form-item
            prop="scoreLimit"
            :label="$t('scoreCfg.scoreEffective') + '：'"
          >
            <el-radio
              v-model="form.scoreLimit"
              label="0"
            >{{$t('scoreCfg.forever')}}</el-radio>
            <div>
              <el-radio
                v-model="form.scoreLimit"
                label="1"
              >{{$t('scoreCfg.fromTime')}}</el-radio>
              <el-select
                v-model="form.scoreYear"
                :placeholder="$t('scoreCfg.choose')"
                size="small"
                class="selectWidth"
              >
                <el-option
                  v-for="item in yearOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <el-select
                v-model="form.scoreMonth"
                :placeholder="$t('scoreCfg.choose')"
                size="small"
                class="selectWidth"
              >
                <el-option
                  v-for="item in mounthOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>{{$t('scoreCfg.month')}}
              <el-select
                v-model="form.scoreDay"
                :placeholder="$t('scoreCfg.choose')"
                size="small"
                :disabled="form.scoreMonth==='0'"
                class="selectWidth"
              >
                <el-option
                  v-for="item in dayOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>{{$t('scoreCfg.day')}}
              <div style="color:#FF0000">{{$t('scoreCfg.exampleTime')}}</div>
            </div>
            <div>
              <el-radio
                v-model="form.scoreLimit"
                label="2"
              >{{$t('scoreCfg.fromGetScore')}}</el-radio>
              <el-input-number
                size="small"
                v-model="form.scoreLimitNumber"
                controls-position="right"
                :min="1"
                :max="100"
              ></el-input-number>
              <el-select
                v-model="form.scorePeriod"
                :placeholder="$t('scoreCfg.choose')"
                size="small"
                class="selectWidth"
              >
                <el-option
                  v-for="item in integralDateOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>

              </el-select>{{$t('scoreCfg.innerEffective')}}
            </div>
          </el-form-item>
          <el-form-item :label="$t('scoreCfg.exchange') + '：'">
              <el-select v-model="form.scoreProportion" size="small" class="selectWidth">
                <el-option
                  v-for="item in scoreProportionOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
           {{$t('scoreCfg.formula')}}
           <span style="color:#FF0000;margin-left: 10px;">{{$t('scoreCfg.titelMsg')}}</span>
          </el-form-item>
        </div>

        <div class="integralRule">
          <div class="title">
            <span></span>
            {{$t('scoreCfg.scoreRule')}}
          </div>
          <el-form-item
            prop="scorePayLimit"
            :label="$t('scoreCfg.scorePayLimit') + '：'"
          >
            <el-radio-group v-model="form.scorePayLimit">
              <el-radio label="0">{{$t('scoreCfg.unlimit')}}</el-radio>
              <el-radio label="1">{{$t('scoreCfg.defineSelf')}}</el-radio>
            </el-radio-group>
            <div
              v-if="form.scorePayLimit==='1'"
              style="margin-top:10px;marginleft: 120px;"
            >
              <span>{{$t('scoreCfg.scorePayDesOne')}}
                <el-input-number
                  size="small"
                  v-model="form.scorePayNum"
                  controls-position="right"
                  :min="100"
                  :max="10000"
                ></el-input-number>{{$t('scoreCfg.scorePayDesTwo')}}
                <span style="margit-left:20px;color:#999">{{$t('scoreCfg.print')}}</span>
              </span>
            </div>
          </el-form-item>
          <el-form-item
            prop="scoreDiscountRatio"
            :label="$t('scoreCfg.scoreScale') + '：'"
          >
            <span>{{$t('scoreCfg.scoreScaleDesOne')}}
              <el-input-number
                size="small"
                v-model="form.scoreDiscountRatio"
                controls-position="right"
                :min="0"
                :max="100"
              ></el-input-number>{{$t('scoreCfg.scoreScaleDesTwo')}}
              <span style="margit-left:20px;color:#999">{{$t('scoreCfg.prineTwo')}}</span>
            </span>
            <div> {{$t('scoreCfg.titelMsg2')}}
              <el-radio v-model="form.discountHasShipping" label="1" style="margin-left:15px">包含运费</el-radio>
              <el-radio v-model="form.discountHasShipping" label="2">不包含运费</el-radio></div>
          </el-form-item>
        </div>

        <div class="integralRule">
          <div class="title">
            <span></span>
            {{$t('scoreCfg.scoreGetRule')}}
            <i style="color:#999;margin-left:30px;">{{$t('scoreCfg.scoreGetDescOne')}}</i>
          </div>
          <el-form-item
            prop="shoppingScore"
            :label="$t('scoreCfg.buySend') + '：'"
          >
            <el-switch
              v-model="form.shoppingScore"
              active-color="#f7931e"
              active-value='on'
              inactive-value=''
            >
            </el-switch>
            <span style="display:inline-block;margin:0 20px">{{form.shoppingScore?$t('scoreCfg.alreadyOpen'):$t('scoreCfg.alreadyClose')}}</span>
            <span style="color:#999">{{$t('scoreCfg.scoreGetDescTwo')}}</span>
            <div v-if="form.shoppingScore === 'on'">
              <div
                v-for="(item,index) in shopFullArr"
                :key="index"
              >
                <div class="noneIntegralDiv">
                  <span v-if="index===0">
                    <el-radio
                      v-model="form.scoreType"
                      label="0"
                    >{{ $t('memberCard.shopFull') }}&nbsp;&nbsp;</el-radio>
                  </span>
                  <span v-else>
                    <span
                      v-for="i in 5"
                      :key=i
                    >
                      &nbsp;&nbsp;&nbsp;
                    </span>
                  </span>
                  <el-input
                    size="small"
                    v-model.number="item.left"
                    class="inputWidth"
                  ></el-input>&nbsp;&nbsp; {{ $t('memberCard.send') }} &nbsp;&nbsp;
                  <el-input
                    size="small"
                    v-model.number="item.right"
                    class="inputWidth"
                  >
                  </el-input>&nbsp;&nbsp;{{ $t('memberCard.score') }}&nbsp;&nbsp;<img
                    v-if="index === 0"
                    style="cursor:pointer"
                    :src="$imageHost +'/image/admin/sign_jia.png' "
                    @click="handleToAddIntegral()"
                  >

                  <img
                    v-else
                    style="cursor:pointer"
                    :src="$imageHost +'/image/admin/sign_del.png' "
                    @click="handleToDelIntegral(index)"
                  >
                </div>
              </div>
              <div class="shoppingFullBottom">
                <span>
                  <el-radio
                    v-model="form.scoreType"
                    label="1"
                  >{{ $t('memberCard.shopEachFull') }}&nbsp;&nbsp;</el-radio>
                </span>
                <el-input
                  size="small"
                  v-model="buyEach"
                  class="inputWidth"
                ></el-input>&nbsp;&nbsp;{{ $t('memberCard.send') }}&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="scoreEach"
                  class="inputWidth"
                ></el-input>&nbsp;&nbsp;{{ $t('memberCard.score') }}
              </div>
            </div>
          </el-form-item>
          <el-form-item
            prop="storeScore"
            :label="$t('scoreCfg.storeSend') + '：'"
          >
            <el-switch
              v-model="form.storeScore"
              active-color="#f7931e"
              active-value='on'
              inactive-value=''
            >
            </el-switch>
            <span style="display:inline-block;margin:0 20px">{{form.storeScore? $t('scoreCfg.alreadyOpen'):$t('scoreCfg.alreadyClose')}}</span>
            <span style="color:#999">{{$t('scoreCfg.storeSendDescOne')}}</span>
          </el-form-item>
          <el-form-item
            prop="loginScore"
            :label="$t('scoreCfg.loginSendScore') + '：'"
          >
            <el-switch
              v-model="form.loginScore"
              active-color="#f7931e"
              active-value='on'
              inactive-value=''
            >
            </el-switch>
            <span style="display:inline-block;margin:0 20px">{{form.loginScore?$t('scoreCfg.alreadyOpen'):$t('scoreCfg.alreadyClose')}}</span>
            <span style="color:#999">{{$t('scoreCfg.loginDescOne')}}</span>
            <div
              v-if="form.loginScore === 'on'"
              class="hiddenLoginDiv"
            >
              <span>{{$t('scoreCfg.loginSend')}}</span>
              <span>
                <el-input-number
                  size="small"
                  v-model="form.scoreLogin"
                  controls-position="right"
                  :min="1"
                  :max="100000"
                ></el-input-number>{{$t('scoreCfg.score')}}
                <span style="color:#f66">{{$t('scoreCfg.loginDescTwo')}}</span>
              </span>
            </div>
          </el-form-item>
          <el-form-item
            prop="signInScore"
            :label="$t('scoreCfg.signSendScore') + '：'"
          >
            <el-switch
              v-model="form.signInScore"
              active-color="#f7931e"
              active-value='on'
              inactive-value=''
            >
            </el-switch>
            <span style="display:inline-block;margin:0 20px">{{form.signInScore?$t('scoreCfg.alreadyOpen'):$t('scoreCfg.alreadyClose')}}</span>
            <span style="color:#999;margin-right:10px;display:inline-block">{{$t('scoreCfg.signDescOne')}}</span><i
              @click="handleToCheckMember()"
              style="cursor:pointer;color:#5a8bff"
            >{{$t('scoreCfg.view')}}</i>

            <div>
             <el-radio v-model="form.signInRules" label="0" style="margin-right:1px">连续签到</el-radio>
                <el-popover trigger="hover" content="连续签到N+1天时，将循环按第N天签到积分数量赠送（N为连续签到上限）"><img :src="iconUrl" slot="reference"></el-popover>
             <el-radio v-model="form.signInRules" label="1" style="margin-right:1px;margin-left: 12px;">循环签到</el-radio>
                 <el-popover trigger="hover" content="连续签到N+1天时，将循环按第一天签到积分数量赠送（N为连续签到上限）"><img :src="iconUrl" slot="reference"></el-popover>
            </div>
            <div
              v-if="form.signInScore === 'on'"
              class="hiddenLoginDiv"
            >
              <div
                v-for="(item,index) in signInput"
                :key="index"
                style="margin-bottom:5px"
              >
                <span>{{$t('scoreCfg.continueSign')}}{{ index + 1 }}{{$t('scoreCfg.daySend')}}</span>
                <el-input
                  v-model.number="item.input"
                  size="small"
                  style="width: 80px;"
                ></el-input>
                <span>{{$t('scoreCfg.score')}}</span>
                <span
                  @click="handleToAdd()"
                  v-if="index===0"
                  style="cursor:pointer"
                >
                  <img :src="$imageHost+'/image/admin/sign_jia.png'">
                </span>
                <span
                  @click="handleToDel(index)"
                  v-if="index>0&&index===(signInput.length-1)"
                  style="cursor:pointer"
                >
                  <img :src="$imageHost+'/image/admin/sign_del.png'">
                </span>
              </div>

            </div>
          </el-form-item>
        </div>

      </el-form>

    </div>

    <!-- 底部 -->
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        :disabled="submitStatus"
        @click="saveScoreHandler"
      >保存</el-button>
    </div>
  </div>
</template>
<script>
import { userScoreConfigUpdate, getScoreConfigRequest } from '@/api/admin/memberManage/scoreManage/scoreCfg.js'
export default {
  data () {
    // 自定义校验
    var validateScoreLimit = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请选择积分有效期'))
      } else if (value === '1' && (!this.form.scoreYear || !this.form.scoreMonth || !this.form.scoreDay)) {
        callback(new Error('请完整填写积分有效期'))
      } else if (value === '2' && (!this.form.scoreLimitNumber || !this.form.scorePeriod)) {
        callback(new Error('请完整填写积分有效期'))
      } else {
        callback()
      }
    }
    var validatePayLimit = (rule, value, callback) => {
      var re = /^[1-9]\d*00$/
      if (!value) {
        callback(new Error('请选择积分支付限制'))
      } else if (value === '1' && !this.form.scorePayNum) {
        callback(new Error('请完整填写积分支付限制'))
      } else if (value === '1' && !re.test(this.form.scorePayNum)) {
        callback(new Error('请正确填写积分支付限制'))
      } else {
        callback()
      }
    }
    var validateRatio = (rule, value, callback) => {
      var re = /^(?:[1-9]?\d|100)$/
      if (!value) {
        callback()
        this.form.scoreDiscountRatio = 50
      } else if (!re.test(value)) {
        callback(new Error('请正确填写积分抵扣比例'))
      } else {
        callback()
      }
    }
    // 购物积分
    var validateshopping = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (value === 'on' && this.form.scoreType === '0') {
        this.shopFullArr.forEach((item, index) => {
          for (let i in item) {
            if (!item[i] || !re.test(item[i])) {
              callback(new Error('购物送积分填写不正确'))
            }
          }
        })
        callback()
      } else if (value === 'on' && this.form.scoreType === '1' && (!this.buyEach || !this.scoreEach || !re.test(this.buyEach) || !re.test(this.scoreEach))) {
        callback(new Error('购物送积分填写不正确'))
      } else {
        callback()
      }
    }
    var validateLogin = (rule, value, callback) => {
      if (value === 'on' && !this.form.scoreLogin) {
        callback(new Error('请填写登录积分'))
      } else {
        callback()
      }
    }
    var validateSignIn = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (value === 'on') {
        this.signInput.forEach((item, index) => {
          for (let i in item) {
            if (!item[i] || !re.test(item[i])) {
              callback(new Error('签到积分填写不正确'))
            }
          }
        })
        callback()
      } else {
        callback()
      }
    }
    return {
      submitStatus: false,
      yearOptions: null,
      mounthOptions: [
        {
          value: '0',
          label: '请选择：'
        }, {
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }, {
          value: '6',
          label: '6'
        }, {
          value: '7',
          label: '7'
        }, {
          value: '8',
          label: '8'
        }, {
          value: '9',
          label: '9'
        }, {
          value: '10',
          label: '10'
        }, {
          value: '11',
          label: '11'
        }, {
          value: '12',
          label: '12'
        }],
      dayOptions: [
        {
          value: '0',
          label: '请选择：'
        }, {
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }, {
          value: '6',
          label: '6'
        }, {
          value: '7',
          label: '7'
        }, {
          value: '8',
          label: '8'
        }, {
          value: '9',
          label: '9'
        }, {
          value: '10',
          label: '10'
        }, {
          value: '11',
          label: '11'
        }, {
          value: '12',
          label: '12'
        }, {
          value: '13',
          label: '13'
        }, {
          value: '14',
          label: '14'
        }, {
          value: '15',
          label: '15'
        }, {
          value: '16',
          label: '16'
        }, {
          value: '17',
          label: '17'
        }, {
          value: '18',
          label: '18'
        }, {
          value: '19',
          label: '19'
        }, {
          value: '20',
          label: '20'
        }, {
          value: '21',
          label: '21'
        }, {
          value: '22',
          label: '22'
        }, {
          value: '23',
          label: '23'
        }, {
          value: '24',
          label: '24'
        }, {
          value: '25',
          label: '25'
        }, {
          value: '26',
          label: '26'
        }, {
          value: '27',
          label: '27'
        }, {
          value: '28',
          label: '28'
        }, {
          value: '29',
          label: '29'
        }, {
          value: '30',
          label: '30'
        }, {
          value: '31',
          label: '31'
        }],

      integralDateOptions: [{
        value: '1',
        label: '日'
      }, {
        value: '7',
        label: '周'
      },
      {
        value: '30',
        label: '月'
      }],
      // 购物送积分
      shopFullArr: [
        {
          left: 100,
          right: 100
        }
      ],
      buyEach: null,
      scoreEach: null,
      // 签到送积分
      signData: 1,
      signInput: [
        {
          input: 10
        }
      ],

      form: {
        scoreLimit: '0', // 积分有效期类型
        scoreDay: '',
        scoreMonth: '',
        scoreYear: '',
        scoreLimitNumber: '',
        scorePeriod: '',

        scorePayLimit: '0', // 积分支付限制
        scorePayNum: '',
        scoreDiscountRatio: '', // 积分抵扣比例

        shoppingScore: 'off', // 购物送积分
        scoreType: '0', // 类型
        buy: [], // 满多少送多少
        score: [],
        buyEach: [], // 每满多少送多少
        scoreEach: [],
        storeScore: 'off', // 门店买单送积分
        loginScore: 'off', // 登录送积分
        scoreLogin: 1, // 登录积分
        signInScore: 'off', // 签到送积分
        signScore: [], // 签到积分
        signInRules: '0',
        scoreProportion: 100,
        discountHasShipping: '1'
      },
      // 校验表单
      fromRules: {
        scoreLimit: { required: true, validator: validateScoreLimit, trigger: 'blur' },
        scorePayLimit: { required: true, validator: validatePayLimit, trigger: 'blur' },
        scoreDiscountRatio: { required: true, validator: validateRatio, trigger: 'blur' },
        shoppingScore: { required: true, validator: validateshopping, trigger: 'change' },
        loginScore: { required: true, validator: validateLogin, trigger: 'change' },
        signInScore: { required: true, validator: validateSignIn, trigger: 'change' }
      },
      iconUrl: this.$imageHost + '/image/admin/system_icon.png',
      scoreProportionOptions: [{
        value: 1,
        label: '1'
      }, {
        value: 10,
        label: '10'
      },
      {
        value: 100,
        label: '100'
      },
      {
        value: 1000,
        label: '1000'
      }]
    }
  },
  watch: {
    lang () {
      this.integralDateOptions = [{
        value: '1',
        label: this.$t('scoreCfg.day')
      }, {
        value: '7',
        label: this.$t('scoreCfg.week')
      },
      {
        value: '30',
        label: this.$t('scoreCfg.month')
      }]
      this.dayOptions[0].label = this.$t('scoreCfg.choose')
      this.mounthOptions[0].label = this.$t('scoreCfg.choose')
      this.yearOptions = this.$t('scoreCfg.yearOptions')
    },
    'form.scoreMonth' (newData) {
      if (newData === '0') {
        this.form.scoreDay = ''
      }
    }
  },
  mounted () {
    this.langDefault()
    this.getScoreHandler()
  },
  methods: {
    // 获取积分配置
    getScoreHandler () {
      getScoreConfigRequest().then((res) => {
        if (res.error === 0 && res.content) {
          var data = res.content
          this.form.scoreLimit = data.scoreLimit
          this.form.scoreDay = data.scoreDay
          this.form.scoreMonth = data.scoreMonth
          this.form.scoreYear = data.scoreYear
          this.form.scoreLimitNumber = data.scoreLimitNumber
          this.form.scorePeriod = data.scorePeriod
          this.form.signInRules = String(data.signInRules)
          this.form.scoreProportion = data.scoreProportion
          this.form.discountHasShipping = String(data.discountHasShipping)
          if (!data.scorePayLimit === null) {
            this.form.scorePayLimit = '0'
          } else {
            this.form.scorePayLimit = data.scorePayLimit
          }
          this.form.scorePayNum = data.scorePayNum
          this.form.scoreDiscountRatio = data.scoreDiscountRatio

          // 购物送积分
          this.form.shoppingScore = data.shoppingScore
          this.form.scoreType = data.scoreType
          this.shopFullArr = []
          if (this.form.scoreType === '0') {
            this.form.buy = data.buy
            if (this.form.buy.length === 0) {
              this.shopFullArr = [{
                left: 100,
                right: 100
              }]
            } else {
              data.buy.forEach((item, index) => {
                this.shopFullArr.push({ left: item })
              })
              this.form.score = data.buyScore
              this.shopFullArr.forEach((val, key) => {
                data.buyScore.forEach((item, index) => {
                  if (key === index) {
                    val.right = item
                  }
                })
              })
            }
          } else {
            this.form.buyEach = data.buyEach
            this.form.scoreEach = data.buyEachScore
            this.buyEach = this.form.buyEach[0]
            this.scoreEach = this.form.scoreEach[0]
            this.shopFullArr.push({
              left: null,
              right: null
            })
          }

          // 门店买单送积分
          this.form.storeScore = data.storeScore

          // 登陆送积分
          this.form.loginScore = data.loginScore
          this.form.scoreLogin = data.scoreLogin

          // 签到送积分
          this.form.signInScore = data.signInScore
          this.form.signScore = data.signScore
          this.signInput = []
          if (data.signScore.length === 0) {
            this.signInput = [{
              input: 10
            }]
          } else {
            data.signScore.forEach((item, index) => {
              this.signInput.push({ input: item })
            })
          }

          console.log(this.form)
        }
      })
    },

    // 保存积分配置
    saveScoreHandler () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // 购物送积分
          if (this.form.scoreType === '0') {
            this.form.buy = []
            this.form.score = []
            this.shopFullArr.forEach((item, index) => {
              this.form.buy.push(item.left)
              this.form.score.push(item.right)
            })
          } else {
            console.log(this.buyEach)
            this.form.buyEach[0] = this.buyEach
            console.log(this.scoreEach)
            this.form.scoreEach[0] = this.scoreEach
          }
          // 签到积分
          this.form.signScore = []
          this.signInput.forEach((item, index) => {
            this.form.signScore.push(item.input)
          })

          console.log(this.form)
          userScoreConfigUpdate(this.form).then((res) => {
            if (res.error === 0) {
              this.$message.success(this.$t('memberCard.scoreSaveSuccess'))
            }
          })
        }
      })
      this.submitStatus = false
    },

    // 3- 添加购物满
    handleToAddIntegral () {
      this.shopFullArr.push({
        left: null,
        right: null
      })
    },
    // 4- 删除购物满
    handleToDelIntegral (index) {
      this.shopFullArr.splice(index, 1)
    },
    // 签到送积分点击添加icon
    handleToAdd () {
      let obj = {
        input: '1'
      }
      if (this.signInput.length < 30) {
        this.signInput.push(obj)
        this.signData++
      } else {
        this.$message.warning(this.$t('scoreCfg.titelError'))
      }
    },
    // 签到送积分点击删除icon
    handleToDel (index) {
      this.signData--
      this.signInput.splice(index, 1)
      console.log(this.signInput, index)
    },
    //  查看签到会员点击
    handleToCheckMember () {
      this.$router.push({
        name: 'viewSigninMembers'
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.inputWidth {
  width: 170px;
}
.selectWidth {
  width: 100px;
}
.integralRule {
  background: #fff;
  // padding: 15px 25px;
  padding-top: 0;
  .top {
    padding-bottom: 10px;
    margin-bottom: 10px;
  }
  .title {
    margin-bottom: 20px;
    span {
      display: inline-block;
      margin-left: 20px;
      border-left: 2px solid #5a8bff;
      height: 14px;
      width: 8px;
      margin-bottom: -1px;
    }
    height: 40px;
    line-height: 40px;
    background: #eef1f6;
    font-size: 14px;
  }
  .content {
    margin-top: 30px;
    display: flex;
    .radioDiv {
      display: flex;
      flex-direction: column;
      margin-left: 30px;
    }
    .radioDiv > div {
      display: flex;
      margin-bottom: 30px;
      align-items: center;
      /deep/ .el-radio__label {
        color: #333;
      }
      /deep/ .el-input {
        width: 110px;
      }
    }
    .radioDiv > div > div {
      margin-right: 20px;
    }
    .integralNumDiv {
      /deep/ .el-input {
        width: 130px !important;
      }
    }
  }
}
// 开始
.shoppingFullBottom,
.noneIntegralDiv {
  margin-top: 20px;
  // padding-left: 54px;
  display: flex;
  align-items: center;

  /deep/ .el-input {
    width: 8%;
    .el-input__inner {
      width: 100%;
    }
  }
}
.shoppingFullBottom .el-input {
  width: 8%;
  .el-input__inner {
    width: 100%;
  }
}
.integralManagement {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .integralManagementMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
  }
  .integralUsage {
    margin-top: 10px;
    .title {
      span {
        display: inline-block;
        margin-left: 20px;
        border-left: 2px solid #5a8bff;
        height: 14px;
        width: 8px;
        margin-bottom: -1px;
      }
      height: 40px;
      line-height: 40px;
      background: #eef1f6;
      font-size: 14px;
      i {
        display: inline-block;
        margin-left: 30px;
      }
    }
    .intLimit {
      .intLimitTop {
        margin: 20px 0;
        display: flex;
        /deep/ .el-input-number {
          margin: 0 10px;
        }
        span {
          margin-right: 10px;
        }
      }
      .intLimitFooter {
        span {
          height: 32px;
          line-height: 32px;
        }
      }
    }
    .intContent {
      margin-top: 20px;
      .intTitle {
        display: inline-block;
        width: 100px;
        text-align: right;
        margin-right: 10px;
      }
      .loginDiv {
        display: flex;
        .hiddenLoginDiv {
          padding-left: 20px;
          margin: 20px 0;
          display: flex;
          span {
            height: 32px;
            line-height: 32px;
          }
          div {
            margin: 0 10px;
          }
        }
      }
    }
    .signDiv {
      display: flex;
      .hiddenLoginDiv {
        padding-left: 20px;
        margin: 20px 0;
        div {
          /deep/ .el-input {
            width: 70px;
            margin: 0 20px;
          }
          display: flex;
          span {
            white-space: nowrap;
            display: flex;
            align-items: center;
          }
          span:nth-of-type(2) {
            margin-right: 20px;
          }
        }
      }
    }
  }
}
// 结束
.footer {
  height: 50px;
  line-height: 50px;
  text-align: center;
  background: #fff;
  border-top: 1px solid #e4e7ed;
}
</style>
