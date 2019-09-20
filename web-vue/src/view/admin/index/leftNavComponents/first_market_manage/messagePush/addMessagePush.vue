
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
            <span>每人每天最多发送一条自定义模板消息，请勿发送骚扰信息，违者将受到微信官方相应处罚，严重者将被封禁小程序</span>
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
                <span>此处为小程序的名称</span>
                <span class="fr">...</span>
              </div>
              <div class="leftMainConCenter">
                <div class="leftMainConCenterTips">商家活动通知</div>
                <div class="leftMainConCenterTime">2018年6月11日</div>
                <div class="leftMainConCenterTitle">
                  <div class="title">业务标题</div>
                  <div class="h1title">{{formData.title===``?`业务标题`:formData.title}}</div>
                </div>
                <div class="leftMainConCenterCon"><span>业务内容</span><span class="xxx">{{formData.content===``?`xxx`:formData.content}}</span></div>
                <div class="leftMainConCenterComeIn"><span>进入小程序查看</span></div>
                <div class="leftMainConCenterReject"><span>拒收通知</span></div>

              </div>
            </div>
          </div>
          <div class="mainContentRight">
            <div class="mainContentRightForm">
              <el-form
                :rules="rules"
                :model="formData"
                label-width="128px"
                size="small"
              >
                <!-- 消息名称 -->
                <el-form-item :label="labels.label1">
                  <el-input
                    maxlength="20"
                    show-word-limit
                    size="small"
                    v-model="formData.name"
                    style="width:245px"
                  ></el-input>
                  <div class="mainContentRightFormText">只作为商家记录使用，用户不会看到这个名称</div>
                </el-form-item>

                <!-- 消息类型 -->
                <el-form-item :label="labels.label2">
                  <span>商家活动通知</span>
                </el-form-item>
                <!-- 业务标题 -->
                <el-form-item :label="labels.label3">
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
                <el-form-item :label="labels.label4">
                  <div>
                    <el-button
                      size="small"
                      @click="handleChooseLink"
                    > {{this.pageLink === ``?`添加链接`:this.pageLink}}</el-button>
                  </div>
                </el-form-item>
                <!-- 参与活动人群 -->
                <el-form-item :label="labels.label6">
                  <div>
                    <span style="color:#999;fontSize:12px">以下筛选条件为“或”关系</span>
                  </div>
                  <div>
                    <el-checkbox
                      v-model="onClickNoPay"
                      @change="handleOnClickNoPayChange"
                    >加购人群</el-checkbox>
                    <span style="color:#999;fontSize:12px;margin-left:-15px">30天内在本店内有加入购物车行为，但没有支付的用户</span>
                  </div>
                  <div>
                    <el-checkbox v-model="onClickGoods">指定购买商品人群 </el-checkbox>
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
                    <chooseSelect />
                  </div>

                  <div style="margin:10px 0">
                    <el-checkbox v-model="onClickUser">选择指定的会员 </el-checkbox>
                    <span style="margin-left:-15px">
                      <el-button
                        @click="handleAddMember"
                        type="text"
                      >+ 添加会员</el-button>
                    </span>
                    <!-- 测试 开始-->
                    <!-- <div>
                      <div>
                        <dg-table
                          title="弹窗组件选择数据"
                          :checked-data="checkedData"
                          @handleChooseData="handleChooseData"
                        ></dg-table>
                        <br /><br /><br />
                        <el-input
                          type="textarea"
                          :rows="10"
                          placeholder="已选择内容"
                          :value="JSON.stringify(checkedData)"
                        >
                        </el-input>
                      </div>
                    </div> -->
                    <!-- 测试结束 -->
                    <span>已选择会员{{memberNum}}人</span>
                  </div>
                  <div>
                    <el-checkbox v-model="onClickCustomRule">自定义</el-checkbox>
                    <el-select
                      :disabled="!onClickCustomRule"
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
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                    <!--  -->
                    <div style="margin:10px 0">
                      <ul class="ulList">
                        <li
                          v-for="(item,i) in optionsList"
                          :key="i"
                        >
                          <span>{{item}}：</span>
                          <span>
                            <el-input
                              :disabled="!onClickCustomRule"
                              style="width:120px"
                              size="small"
                            > </el-input>
                            <span>{{ item | filterA  }}</span>
                          </span>
                          <div class="img_span">
                            <el-image
                              :src="urls.url4"
                              class="img"
                              @click="handleDelCustomize(i)"
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
                            <dateTimePicker :showPicker=1 />
                          </span>

                        </li>
                      </ul>
                    </div>
                  </div>
                </el-form-item>
                <!-- 发送时间 -->
                <el-form-item :label="labels.label7">
                  <div>
                    <el-radio
                      :label=1
                      v-model="senAction"
                    >立即发送</el-radio>
                  </div>
                  <div>
                    <span style="color:#999;fontSize:12px;margin-right:10px">预计送达人数：0 </span>
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
          @click="handleSaveAndSend"
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
      <selectLinks @path="getPath" />
      <!-- 选择商品弹窗 -->
      <choosingGoods @res="getRes" />
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
//
import dialogTable from './chooseDialogTable'
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
import { allCardApi, contentAddApi, getUserNumberApi } from '@/api/admin/marketManage/messagePush.js'
import { delObj } from '@/util/formatData'
export default {
  name: 'addMessagePush',
  components: {
    'dgTable': dialogTable,
    chooseTemplateDialog,
    selectLinks,
    dateTimePicker,
    chooseSelect,
    memberListDialog,
    choosingGoods,
    getUserDialog },
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
       * params
       */
      senAction: 1,
      startTime: ``,
      endTime: ``,
      startTime1: ``,
      onClickNoPay: false, // 勾选加购人群
      onClickGoods: false, // 勾选购买指定商品人群
      goodsIdList: [], // 商品ID集合
      onClickCard: false, // 勾选指定会员卡人群
      cardIdsList: [],
      onClickTag: false,
      tagIdList: [],
      onClickUser: false, // 勾选指定会员
      userIdList: [],
      onClickCustomRule: false,
      disabledOnClickCustomRule: false,
      pageLink: ``,
      /**
       * 表单检验
       */
      rules: {
        name: [
          { required: true, message: '请填写消息名称', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
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
      noPayDay: ``,
      payedDay: ``,
      buyTimesMore: ``,
      buyTimesLess: ``,
      moneyAvgMore: ``,
      moneyAvgLess: ``,
      loginStart: ``,
      loginEnd: ``,
      customRuleInfoOptions: [
        {
          label: `N天内有交易记录`,
          value: `N天内有交易记录`
        },
        {
          label: `N天内没有交易记录`,
          value: `N天内没有交易记录`
        },
        {
          label: `累计购买次数小于N次`,
          value: `累计购买次数小于N次`
        },
        {
          label: `累计购买次数大于N次`,
          value: `累计购买次数大于N次`
        },
        {
          label: `购买商品均价大于N元`,
          value: `购买商品均价大于N元`
        },
        {
          label: `购买商品均价小于N元`,
          value: `购买商品均价小于N元`
        },
        {
          label: `指定时间内有登录记录`,
          value: `指定时间内有登录记录`
        }
      ],
      optionsList: [],
      showTime: false,
      isShowBtn: false,
      arrList: [],
      imgsList: []
    }
  },
  watch: {

  },
  created () {
    this.initData()
  },
  mounted () {

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
        // templateId: 2,
        content: this.formData.content,
        pageLink: this.pageLink,
        userInfo: {
          onClickNoPay: this.onClickNoPay,
          onClickGoods: this.onClickGoods,
          goodsIdList: this.goodsIdList,
          onClickCard: this.onClickCard,
          cardIdsList: this.cardIdsList,
          onClickTag: this.onClickTag,
          tagIdList: this.tagIdList,
          onClickUser: this.onClickUser,
          userIdList: this.userIdList,
          onClickCustomRule: this.onClickCustomRule,
          customRuleInfo: {
            noPayDay: `1`,
            payedDay: `1`,
            buyTimesMore: `1`,
            buyTimesLess: `1`,
            moneyAvgMore: `1`,
            moneyAvgLess: `1`,
            loginStart: `1`,
            loginEnd: `1`
          }
        },
        userKey: `1`,
        senAction: this.senAction
        // startTime: this.startTime,
        // endTime: this.endTime
      }
      console.log(params)
    },
    // 获取时间
    getTime (val) {
      this.startTime = val.startTime
      this.endTime = val.endTime
    },
    getTime2 (val) {
      this.startTime1 = val.startTime
    },
    getTime3 (val) {

    },
    // 会员卡下拉框选中出发
    getIdList (val) {
      console.log(val)
      if (val !== `请选择会员卡`) {
        const res = this.cardList.find((ele) => ele.id === val)
        // console.log(res)
        this.cardIdsList.push(res)
        // console.log(val)
        // console.log(this.cardList)
        this.cardList = this.cardList.filter((item) => item.id !== val)
        // console.log(this.cardList)
        this.cardValue = `请选择会员卡`
      }
    },
    // 关闭会员弹窗
    closeDialog () {
      this.dialogOff = false
      this.whetherShowDialog = false
      this.dialogVisible = false
    },
    // 添加会员
    handleAddMember () {
      if (this.onClickUser === false) {
        return
      }
      this.dialogOff = true
    },
    // getUserIdList
    getUserIdList (val) {
      console.log(val)
      this.memberNum = val.length // 把选中的数组长度赋值给已选会员数
    },
    // getContent
    getContent (res) {
      this.formData.content = res
    },
    customRuleInfoValChange (val) {
      if (val === `指定时间内有登录记录`) {
        console.log(1111111)
        this.showTime = true
        this.customRuleInfoOptions = delObj({ arr: this.customRuleInfoOptions, val })
        this.customRuleInfoVal = `请选择`
      } else {
        // this.showTime = false
        // console.log(val)
        if (val !== `请选择` && val !== `指定时间内有登陆记录`) {
          // console.log(this.customRuleInfoOptions)
          // console.log(val)
          this.optionsList.push(val)
          // console.log(this.optionsList)
          // 移除下拉框的option
          this.customRuleInfoVal = `请选择`

          this.customRuleInfoOptions = delObj({ arr: this.customRuleInfoOptions, val })
        }
      }
    },
    // 删除自定义
    handleDelCustomize (val) {
      if (this.onClickCustomRule === false) {
        console.log(1111111111)
        return
      }
      if (val === 6) {
        console.log(val)
        this.showTime = false
        this.customRuleInfoOptions.push({
          label: `指定时间内有登录记录`,
          value: `指定时间内有登录记录`
        })
      } else {
        console.log(val)
        this.optionsList.splice(val, 1)
        console.log(this.optionsList)
        console.log(this.customRuleInfoOptions)
        switch (val) {
          case 0:
            this.customRuleInfoOptions.push({
              label: `N天内有交易记录`,
              value: `N天内有交易记录`
            })
            break
          case 1:
            this.customRuleInfoOptions.push({
              label: `N天内没有交易记录`,
              value: `N天内没有交易记录`
            })
            break
          case 2:
            this.customRuleInfoOptions.push({
              label: `累计购买次数小于N次`,
              value: `累计购买次数小于N次`
            })
            break
          case 3:
            this.customRuleInfoOptions.push({
              label: `累计购买次数大于N次`,
              value: `累计购买次数大于N次`
            })
            break
          case 4:
            this.customRuleInfoOptions.push({
              label: `购买商品均价大于N元`,
              value: `购买商品均价大于N元`
            })
            break
          case 5:
            this.customRuleInfoOptions.push({
              label: `购买商品均价小于N元`,
              value: `购买商品均价小于N元`
            })
            break
          default:
            break
        }
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
      this.$http.$emit('linkDialogFlag', true)
    },
    // 选择模板
    choosTemplate () {
      this.whetherShowDialog = true
    },
    // 选择商品
    handleChooseGoods () {
      if (this.onClickGoods === false) {
        return
      }
      this.$http.$emit('choosingGoodsFlag', true)
    },
    getRes (ids, urls) {
      if (ids.length > 3) {
        this.$message.warning('最多选择3个商品')
      } else {
        console.log(ids)
        console.log(urls)
        this.goodsIdList = ids
        this.imgsList = urls
      }
    },
    // 删除图片
    handleDelImg (id) {
      console.log(id)
      if (this.onClickGoods === false) {
        return
      }
      this.imgsList = this.imgsList.filter(item => item.goodsId !== id)
      this.goodsIdList = this.goodsIdList.filter(item => item !== id)
    },
    // 获取选中的path
    getPath (res) {
      console.log(res)
      this.pageLink = res
    },
    // 查看
    handleGetUser () {
      this.dialogVisible = true
    },
    fetchUserList (params) {
      getUserNumberApi(params).then(res => {
        console.log(res)
      }).catch(err => console.log(err))
    },
    // 加购人群发生变化的时候
    handleOnClickNoPayChange (val) {
      console.log(val)
      // 获取发送人群的数量
      const params = {
        onClickNoPay: val
      }
      this.fetchUserList(params)
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
              background: url(../../../../../../../static/image/wxapp/click_look.png)
                no-repeat 95%;
              -webkit-background-size: 6px;
              background-size: 6px;
            }
            .leftMainConCenterComeIn {
              padding: 10px 12px;
              border-top: 1px solid #eee;
              background: url(../../../../../../../static/image/wxapp/click_look.png)
                no-repeat 95%;
              -webkit-background-size: 6px;
              background-size: 6px;
            }
          }
        }
      }
      .mainContentRight {
        margin-bottom: 100px;
        width: 520px;
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
