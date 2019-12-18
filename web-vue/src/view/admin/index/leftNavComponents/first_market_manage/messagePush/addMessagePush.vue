
<template>
  <div class="addMessagePush">
    <!-- 添加消息推送的卡片 -->
    <el-card>
      <div class="mainContent">
        <div class="mainContentHeader">
          <div class="mainContentTop">
            <el-image
              style="margin-right:10px"
              :src="urls.url1"
              fit="fill"
            ></el-image>
            <span>{{$t(`messagePush.top`)}}</span>
          </div>
        </div>

        <div class="mainContentMiddle">
          <div class="mainContentLeft">
            <div class="leftTitle">
            </div>
            <div class="leftMainCon">
              <div class="leftMainConName">
                <el-image
                  style="width:20px;height:20px"
                  :src="urls.url2"
                  fit="fill"
                ></el-image>
                <span>{{$t(`messagePush.appletName`)}}</span>
                <span class="fr">...</span>
              </div>
              <div class="leftMainConCenter">
                <div class="leftMainConCenterTips">{{$t(`messagePush.notice`)}}</div>
                <div class="leftMainConCenterTime">{{$t(`messagePush.september242019`)}}</div>
                <div class="leftMainConCenterTitle">
                  <div class="title">{{$t(`messagePush.businessTitle`)}}</div>
                  <div class="h1title">{{formData.title===``?this.$t(`messagePush.businessTitle`):formData.title}}</div>
                </div>
                <div class="leftMainConCenterCon"><span>{{$t(`messagePush.businessContent`)}}</span><span class="xxx">{{formData.content===``?`xxx`:formData.content}}</span></div>
                <div
                  class="leftMainConCenterComeIn"
                  :style="'background: url('+ $imageHost +'/image/wxapp/click_look.png) no-repeat 95%;'"
                ><span>进入小程序查看</span></div>
                <div
                  class="leftMainConCenterReject"
                  :style="'background: url('+$imageHost + '/image/wxapp/click_look.png) no-repeat 95%;'"
                ><span>拒收通知</span></div>

              </div>
            </div>
          </div>
          <div class="mainContentRight">
            <div class="mainContentRightForm">
              <el-form
                :rules="rules"
                :model="formData"
                label-width="140px"
                size="small"
                ref="form"
              >

                <!-- 消息名称 -->
                <el-form-item
                  :label="labels.label1"
                  prop="name"
                >
                  <el-input
                    maxlength="20"
                    show-word-limit
                    size="small"
                    v-model="formData.name"
                    style="width:245px"
                  >
                  </el-input>
                  <div class="mainContentRightFormText">只作为商家记录使用，用户不会看到这个名称</div>
                </el-form-item>

                <!-- 消息类型 -->
                <el-form-item
                  :label="labels.label2"
                  :rules="[ { required: true}]"
                >
                  <span>商家活动通知</span>
                </el-form-item>
                <!-- 业务标题 -->
                <el-form-item
                  :label="labels.label3"
                  prop="title"
                >
                  <el-input
                    maxlength="7"
                    show-word-limit
                    size="small"
                    v-model="formData.title"
                    style="width:245px"
                  ></el-input>
                </el-form-item>
                <!-- 业务内容 -->
                <el-form-item
                  :label="labels.label4"
                  class="addTemplateBtnWrap"
                  prop="content"
                >
                  <div>
                    <el-button
                      @click="choosTemplate"
                      type="text"
                    >选择模板</el-button>
                  </div>
                  <div>
                    <el-input
                      style="width:250px;"
                      :rows="7"
                      type="textarea"
                      placeholder="请输入小程序推送内容"
                      v-model="formData.content"
                      maxlength="50"
                      show-word-limit
                      @blur="handleShowBtn"
                    >
                    </el-input>
                  </div>
                  <div
                    class="addTemplateBtn"
                    v-if="isShowBtn"
                  >
                    <el-button
                      size="small"
                      type="primary"
                      @click="handleAddTemplate"
                    >添加为模板</el-button>
                  </div>
                </el-form-item>
                <el-form-item
                  :label="labels.label5"
                  :rules="[
                {required: true}
                ]"
                >
                  <div>
                    <el-button
                      size="small"
                      @click="handleChooseLink"
                    > {{this.pageLink === ``?`添加链接`:this.pageLink}}</el-button>
                  </div>
                </el-form-item>
                <!-- 参与活动人群 -->
                <el-form-item
                  :label="labels.label6"
                  :rules="[{required: true }]"
                >
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
                <!-- 发送时间 -->
                <el-form-item
                  :label="labels.label7"
                  :rules="[{required: true }]"
                >
                  <div>
                    <el-radio
                      :label=1
                      v-model="senAction"
                    >立即发送</el-radio>
                  </div>
                  <div>
                    <span style="color:#999;fontSize:12px;margin-right:10px">预计送达人数：{{userNumber}} </span>
                    <el-button
                      type="text"
                      @click="handleGetUser"
                    >查看</el-button>
                  </div>
                  <div>
                    <el-radio
                      :label=2
                      v-model="senAction"
                    >持续发送</el-radio>
                  </div>
                  <div class="timePicker">
                    <dateTimePicker
                      :showPicker=1
                      @time="getTime"
                    />
                  </div>
                  <div style="color:#999;fontSize:12px;margin:5px 0">
                    所有可送达的用户均会第一时间收到一次此消息
                  </div>
                  <div>
                    <el-radio
                      :label=4
                      v-model="senAction"
                    >定时发送</el-radio>
                  </div>
                  <div>
                    <dateTimePicker
                      :showPicker=2
                      @startTime="getTime2"
                    />
                  </div>
                </el-form-item>
              </el-form>
            </div>

          </div>
        </div>
      </div>
      <div class="saveAndSend">
        <el-button
          @click="handleSaveAndSend()"
          size="small"
          type="primary"
        >保存并发送</el-button>
      </div>
      <!-- 添加会员的弹窗 -->
      <memberListDialog
        v-if="dialogOff"
        @userIdList="getUserIdList"
        :memberListDialog="dialogOff"
        @dialog-cancel="closeDialog"
      />
      <!-- 选择链接弹窗 -->
      <selectLinks
        @selectLinkPath="getPath"
        :tuneUpSelectLink="tuneUpSelectLink"
      />
      <!-- 选择商品弹窗 -->
      <choosingGoods
        @res="getRes"
        :tuneUpChooseGoods="tuneUpChooseGoods"
        :chooseGoodsBack="params.goodsIdList"
      />
      <!-- 选择内容模板消息 -->
      <chooseTemplateDialog
        v-if="whetherShowDialog"
        @content="getContent"
        :showDialog="whetherShowDialog"
        @dialog-cancel="closeDialog"
      />
      <!-- 获取人群弹窗 -->
      <getUserDialog
        @dialog-cancel="closeDialog"
        :dialogVisible="dialogVisible"
      />
    </el-card>
  </div>
</template>
<script>
// 引入表单校验混入
import RulesMixins from '@/mixins/RulesMixins'
// 引入选择链接弹窗
import selectLinks from '@/components/admin/selectLinks'
// 选择商品弹窗
import choosingGoods from '@/components/admin/choosingGoods'
// 选择内容模板弹窗
import chooseTemplateDialog from './chooseTemplateDialog'
import memberListDialog from './memberListDialog'
import getUserDialog from './getUserDialog'
import chooseSelect from '@/components/admin/chooseSelect/chooseSelect'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
import { allCardApi, contentAddApi, getUserNumberApi, addMessageApi } from '@/api/admin/marketManage/messagePush.js'
import { delObj } from '@/util/formatData'
export default {
  name: 'addMessagePush',
  mixins: [RulesMixins],
  components: {
    chooseTemplateDialog,
    selectLinks,
    dateTimePicker,
    chooseSelect,
    memberListDialog,
    choosingGoods,
    getUserDialog
  },
  data () {
    return {

      checkedData: [], // 初始化弹窗选中的行
      urls: {
        url1: `${this.$imageHost}/image/admin/notice_img.png`,
        url2: `${this.$imageHost}/image/admin/shop_logo_default.png`,
        url3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        url4: `${this.$imageHost}/image/admin/icon_delete.png`
      },
      /**
       * form表单的数据
       */
      formData: {
        name: ``,
        title: ``,
        content: ``
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
       * 表单检验
       */
      rules: {
        // 消息名称
        name: [
          { validator: this.checkMessageName, trigger: 'blur', required: true }
        ],
        // 业务标题
        title: [
          { validator: this.checkMessageTitle, trigger: 'blur', required: true }
        ],
        // 业务内容
        content: [
          { validator: this.checkMessageContent, trigger: 'blur', required: true }

        ]
      },
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
      optionsList: [
        // {
        //   label: `N天内有交易记录`,
        //   ipt: ``,
        //   key: `payedDay`
        // },
        // {
        //   label: `N天内没有交易记录`,
        //   ipt: ``,
        //   key: `noPayDay`
        // },

        // {
        //   label: `累计购买次数大于N次`,
        //   ipt: ``,
        //   key: `buyTimesMore`
        // },
        // {
        //   label: `累计购买次数小于N次`,
        //   ipt: ``,
        //   key: `buyTimesLess`
        // },
        // {
        //   label: `购买商品均价大于N元`,
        //   ipt: ``,
        //   key: `moneyAvgMore`
        // },
        // {
        //   label: `购买商品均价小于N元`,
        //   ipt: ``,
        //   key: `moneyAvgLess`
        // }
      ],
      showTime: false,
      isShowBtn: false,
      arrList: [],
      imgsList: [],
      userNumber: 0,
      tuneUpChooseGoods: false,
      tuneUpSelectLink: false

    }
  },
  watch: {
    senAction (newVal, oldVal) {
      switch (newVal) {
        case 1:
          this.startTime = this.moment().format('YYYY-MM-DD HH:mm:ss')
          break
        case 2:
          this.startTime = this.time.startTime
          this.endTime = this.time.endTime
          break
        case 4:
          this.startTime = this.time.startTime
          break
        default:
          break
      }
    }
  },
  created () {
    this.initData()
  },
  mounted () {
    // 初始化国际化语言
    this.langDefault()
  },
  filters: {
    filterA: function (val) {
      // console.log(val.search('N'))
      if (val.search('N') !== -1) {
        return val.substr(val.search('N') + 1, 1)
      }
    }
  },
  methods: {
    handleChooseData (data) {
      this.$message({ message: `已经选择了${data.length}条数据！`, type: 'success' })
      this.checkedData = data
    },
    // 初始化数据
    initData () {
      this.$http.$on('result', res => {
        console.log(res)
      })
      allCardApi().then(res => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.cardList = content
        }
      }).catch(err => console.log(err))
    },
    // 保存并发送
    handleSaveAndSend () {
      const params = {
        name: this.formData.name,
        title: this.formData.title,
        action: 7,
        templateId: this.templateId,
        content: this.formData.content,
        pageLink: this.pageLink,
        userInfo: this.params,
        senAction: this.senAction,
        userKey: this.params.userKey,
        startTime: this.startTime,
        endTime: this.endTime
      }
      console.log(params)
      console.log(this.$refs.form)
      // 验证输入框是否为空
      if (this.formData.name === `` || this.formData.title === `` || this.formData.content === ``) {
        return
      }
      addMessageApi(params).then(res => {
        const { error } = res
        if (error === 0) {
          this.$message.success({
            type: 'success',
            message: `保存成功`
          })

          this.$router.push({
            name: `all_message_push`
          })
        }
      }).catch(err => console.log(err))
    },
    // 获取时间
    getTime (val) {
      this.time = val
      this.startTime = val.startTime
      this.endTime = val.endTime
    },
    getTime2 (val) {
      this.time = val
      this.startTime = val.startTime
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
    // 输入框发生变化的shih
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
    // 添加为模板
    handleAddTemplate () {
      console.log(this.formData.content)
      if (this.formData.content === ``) { }
      contentAddApi({
        'action': 2,
        'content': this.formData.content
      }).then(res => {
        console.log(res)
        const { error } = res
        if (error === 0) {
          this.$message.success('添加模板成功')
        }
      }).catch(err => console.log(err))
    },
    // handleShowBtn
    handleShowBtn () {
      if (this.formData.content !== ``) {
        this.isShowBtn = true
      } else {
        this.isShowBtn = false
      }
    },
    // 选择链接
    handleChooseLink () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择模板
    choosTemplate () {
      this.whetherShowDialog = true
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
    }
  }
}
</script>
<style lang="scss" scoped>
.saveAndSend {
  border-top: 1px solid #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  z-index: 2;
  width: 88%;
  height: 50px;
  background: #f8f8fa;
  margin-left: -20px;
}
.addMessagePush {
  padding: 10px;
  .mainContent {
    .mainContentHeader {
      display: flex;
      justify-content: center;
    }
    .mainContentTop {
      display: flex;
      align-items: center;
      justify-content: center;
      border: 1px solid #f2e1c8;
      background: #fff7ec;
      color: #666;
      margin-bottom: 12px;
      height: 40px;
      width: 870px;
    }
    .mainContentMiddle {
      display: flex;
      justify-content: center;
      .mainContentLeft {
        width: 320px;
        height: 510px;
        background-color: #eee;
        margin-right: 20px;
        .leftTitle {
          height: 55px;
          width: 100%;
          background: url(../../../../../../assets/adminImg/phone_tops.png)
            no-repeat;
        }
        .leftMainCon {
          width: 285px;
          min-height: 300px;
          background-color: #fff;
          margin-top: 30px;
          margin-left: 15px;
          .leftMainConName {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            width: 100%;
            height: 45px;
            line-height: 45px;
            border-bottom: 1px solid #eee;
            .fr {
              font-size: 34px;
              // line-height: 25px;
              color: #999;
              margin-left: 70px;
              margin-bottom: 20px;
            }
          }
          .leftMainConCenter {
            width: 100%;
            height: 172px;
            padding: 15px 12px 0;
            .leftMainConCenterTips {
              padding: 10px 12px;
            }
            .xxx {
              color: #000;
              font-size: 12px;
              margin-left: 5px;
            }
            .leftMainConCenterTime {
              padding: 10px 12px;
            }
            .leftMainConCenterTitle {
              padding: 10px 12px;
              height: 50px;
              .title {
                color: #999;
                font-size: 12px;
                display: flex;
                justify-content: center;
                align-items: center;
              }
              .h1title {
                color: #000;
                font-size: 22px;
                display: flex;
                justify-content: center;
                padding: 5px;
              }
            }
            .leftMainConCenterCon {
              border-top: 1px solid #eee;
              border-bottom: 1px solid #eee;
              min-height: 36px;
              padding: 10px 12px;
            }
            .leftMainConCenterReject {
              padding: 10px 12px;
              border-top: 1px solid #eee;
              // background: url(../../../../../../../static/image/wxapp/click_look.png)
              //   no-repeat 95%;
              -webkit-background-size: 6px;
              background-size: 6px;
            }
            .leftMainConCenterComeIn {
              padding: 10px 12px;
              border-top: 1px solid #eee;
              // background: url(../../../../../../../static/image/wxapp/click_look.png)
              //   no-repeat 95%;
              -webkit-background-size: 6px;
              background-size: 6px;
            }
          }
        }
      }
      .mainContentRight {
        margin-bottom: 100px;
        min-width: 520px;
        min-height: 1010px;
        background-color: #f8f8f8;
        margin-left: 20px;
        padding-top: 15px;
        .mainContentRightForm {
          .mainContentRightFormText {
            color: #999;
          }
          .addTemplateBtnWrap {
            position: relative;
            .addTemplateBtn {
              position: absolute;
              bottom: 10px;
              left: 10px;
            }
          }
        }
        .chooseGoods {
          display: flex;
          margin: 20px 0;
          .chooseGoodsLeft {
            margin-left: 40px;
            margin-right: 20px;
          }
          .imgList {
            display: flex;
            li {
              margin-right: 10px;
              position: relative;
              .delImg {
                position: absolute;
                top: -6px;
                right: -6px;
              }
            }
          }
          .imageWraper {
            width: 80px;
            height: 80px;
            cursor: pointer;
            border: 1px solid #ccc;
            background: #f7f7f7;
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
        .selectedCard {
          border: 1px solid #eee;
          width: 382px;
          min-height: 56px;
          margin: 10px 0;
          display: flex;
          flex-wrap: wrap;
          .oneCardWraper {
            position: relative;
            margin: 2px 6px;
            .oneCard {
              padding: 0 10px;
              min-width: 70px;
              // margin: 10px;
              // display: flex;
              text-align: center;
              line-height: 24px;
              background-color: #fff;
              height: 24px;
              border: 1px solid #ccc;
              display: inline-block;
            }
            .oneCardDel {
              position: absolute;
              right: -5px;
              top: -5px;
              cursor: pointer;
            }
          }
        }
        .ulList {
          width: 100%;
          li {
            margin: 5px 0;
            span {
              margin: 0 5px;
            }
            .img_span {
              position: relative;
              .img {
                position: absolute;
                right: 25px;
                top: -22px;
                cursor: pointer;
              }
            }
          }
        }
      }
    }
  }
}
</style>
