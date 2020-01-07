<template>
  <div class="rightMembershipCard">
    <div class="rightMembershipCardMain">
      <h2>{{$t('membershipCard.membershipCardModule')}}</h2>

      <!-- <el-button @click='handleToSelectCuopon()'>会员模块测试</el-button> -->
      <div class="content">
        <div>
          {{$t('membershipCard.membershipCard')}}：
        </div>
        <div class="cardListContainer">
          <div class="cardList">

          </div>
          <div style="overflow: hidden;">
            <div
              class="card_add"
              @click="handlCallCardDialog()"
              v-if="!nowChecked.card_id"
            >
              <img :src="$imageHost+'/image/admin/shop_beautify/add_decorete.png'">
              <p>{{$t('membershipCard.addMembershipCard')}}</p>
            </div>
            <!--选中模块-->
            <div
              v-else
              @click="handlCallCardDialog()"
              class="selectCard"
              :style="(nowChecked.bgType===1&&nowChecked.bgImg)?`;backgroundImage:url(${nowChecked.bgImg})`:(nowChecked.bgType===1&&!nowChecked.bgImg)?`backgroundColor:${overallColor}`:nowChecked.bgColor?`backgroundColor:${nowChecked.bgColor}`:`backgroundColor:${overallColor}`"
            >
              <div>
                <span class="card_name">{{nowChecked.card_name}}</span>
                <span
                  style="margin-bottom:8px"
                  v-if="radio==='3'"
                >{{nowChecked.card_grade}}</span>
                <span
                  v-else
                  class="card_state"
                >{{nowChecked.card_state===1?'使用中':'停止使用'}}</span>
              </div>
              <!-- <p
                class="receive_day"
                v-if="typeof nowChecked.expired === 'number'&&radio!== '3'"
              >领取之日起{{nowChecked.expired}}日内有效</p>
              <p
                v-if="typeof nowChecked.expired !== 'number'&&radio!== '3'"
                class="receive_day"
              >有效期：永久有效</p> -->
              <p class="receive_day">{{nowChecked.receive_day}}</p>
            </div>

          </div>
          <div class="footer">
            <el-checkbox v-model="checked"></el-checkbox>
            {{$t('membershipCard.collectionConcealment')}}
          </div>
        </div>

      </div>
    </div>
    <!--页面装修右侧会员卡弹窗-->
    <el-dialog
      title="选择会员卡"
      :visible.sync="dialogVisible"
      width="46%"
      :append-to-body="true"
    >
      <div class="dialogMain">
        <div class="main">
          <div class="dialogMainLeft">
            <el-radio
              v-model="radio"
              label="1"
            >普通会员卡</el-radio>
            <el-radio
              v-model="radio"
              label="2"
            >限次会员卡</el-radio>
            <el-radio
              v-model="radio"
              label="3"
            >等级会员卡</el-radio>
          </div>
          <div class='dialogMainRight'>
            <div class="rightMain">
              <div class="mainRightTop">
                <div class="left">
                  <el-input
                    v-model="input"
                    size="small"
                    placeholder="请输入会员卡名称"
                  ></el-input>
                  <el-button
                    type="info"
                    size="small"
                    plain
                    icon="el-icon-search"
                    @click="handleToSearchCard()"
                  >搜索</el-button>
                </div>
                <div
                  class="right"
                  @click="handleToRefresh()"
                >
                  <span class="el-icon-refresh-left"></span>
                  <span>刷新</span>
                </div>
              </div>
              <div
                class="tips"
                v-if="radio==='3'"
              >
                <p>提示：</p>
                <p>1、等级卡装修1张即可，系统将根据等级卡升级条件及用户实际情况自动匹配用户当前可领取等级卡</p>
                <p>2、以下选中的等级卡仅影响装修时的显示样式。</p>
              </div>
              <!--右侧中部-->
              <div class="mainRightMiddle">
                <ul>
                  <li
                    v-for="(item,index) in showCardList"
                    :key="index"
                    @click="handleToClickCard(index)"
                    :style="(item.bgType===1&&item.bgImg)?`;backgroundImage:url(${item.bgImg})`:(item.bgType===1&&!item.bgImg)?`backgroundColor:${overallColor}`:item.bgColor?`backgroundColor:${item.bgColor}`:`backgroundColor:${overallColor}`"
                  >
                    <img
                      v-if="item.isChecked"
                      class="isCheck"
                      :src="$imageHost+'/image/admin/shop_beautify/checked_card.png'"
                    >
                    <div>
                      <span class="card_name">{{item.cardName}}</span>
                      <span
                        style="margin-bottom:8px"
                        v-if="radio==='3'"
                      >{{item.grade}}</span>
                      <span
                        v-else
                        class="card_state"
                      >{{item.cardState===1?'使用中':'停止使用'}}</span>
                    </div>
                    <!-- <p
                      class="receive_day"
                      v-if="typeof item.expired === 'number'&&radio!== '3'"
                    >领取之日起{{item.expired}}日内有效</p>
                    <p
                      v-if="typeof item.expired !== 'number'&&radio!== '3'"
                      class="receive_day"
                    >有效期：永久有效</p> -->
                    <p class="receive_day">{{item.receiveDay}}</p>
                  </li>
                  <!--添加会员卡-->
                  <li
                    class="addCard"
                    @click="handleToAddCard()"
                  >
                    <img :src="$imageHost+'/image/admin/add_card.png'">
                    <p>添加会员卡</p>
                  </li>
                </ul>
              </div>
            </div>

          </div>
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="dialogVisible = false"
        >取 消</el-button>
        <el-button
          size="small"
          type="primary"
          @click="handleToSelectCuopon()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { allCardData } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting'
export default {
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      checked: false,
      dialogVisible: false,
      radio: '1',
      input: '',
      showCardList: [],
      ordinaryCardData: [
        {
          'card_name': '普通卡续费测试1',
          'card_id': 1,
          'isChecked': false,
          'card_state': '使用中',
          'card_grade': 'v1',
          'card_type': '0',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#ecca90',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'

        },
        {
          'card_name': '普通卡续费测试2',
          'card_id': 2,
          'isChecked': false,
          'card_state': '使用中',
          'card_grade': 'v1',
          'card_type': '0',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#990000',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        },
        {
          'card_name': '普通卡续费测试3',
          'card_id': 3,
          'isChecked': false,
          'card_state': '使用中',
          'card_grade': 'v1',
          'card_type': '0',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#66FF2B',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        },
        {
          'card_name': '普通卡续费测试4',
          'card_id': 4,
          'isChecked': false,
          'card_state': '使用中',
          'card_grade': 'v1',
          'card_type': '0',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '1',
          'bg_color': '#666666',
          'bg_img': this.$imageHost + '/image/admin/fighting_group_draw1.jpg',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        }
      ],
      limitCardData: [
        {
          'card_name': '限次卡续费测试1',
          'card_id': 5,
          'isChecked': false,
          'card_state': '使用中',
          'card_grade': 'v1',
          'card_type': '1',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#ecca90',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        },
        {
          'card_name': '限次卡续费测试2',
          'card_id': 6,
          'isChecked': false,
          'card_state': '使用中',
          'card_grade': 'v1',
          'card_type': '1',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#990000',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        },
        {
          'card_name': '限次卡续费测试3',
          'card_id': 7,
          'isChecked': false,
          'card_state': '使用中',
          'card_grade': 'v1',
          'card_type': '1',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#66FF2B',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        },
        {
          'card_name': '限次卡续费测试4',
          'card_id': 8,
          'isChecked': false,
          'card_state': '使用中',
          'card_grade': 'v1',
          'card_type': '1',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '1',
          'bg_color': '#666666',
          'bg_img': this.$imageHost + '/image/admin/fighting_group_draw1.jpg',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        }
      ],
      gradeCardData: [
        {
          'card_name': '等级卡续费测试1',
          'card_id': 5,
          'card_grade': 'v9',
          'isChecked': false,
          'card_state': '使用中',
          'card_type': '2',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#ecca90',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        },
        {
          'card_name': '等级卡续费测试2',
          'card_id': 6,
          'card_grade': 'v6',
          'isChecked': false,
          'card_state': '使用中',
          'card_type': '2',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#990000',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        },
        {
          'card_name': '等级卡续费测试3',
          'card_id': 7,
          'card_grade': 'v2',
          'isChecked': false,
          'card_state': '使用中',
          'card_type': '2',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '0',
          'bg_color': '#66FF2B',
          'bg_img': '',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        },
        {
          'card_name': '等级卡续费测试4',
          'card_id': 8,
          'card_grade': 'v4',
          'isChecked': false,
          'card_state': '使用中',
          'card_type': '2',
          'receive_day': '有效期:永久有效',
          'legal': '会员折扣9折',
          'exchang_count_legal': '开卡赠送10次兑换商品机会',
          'bg_type': '1',
          'bg_color': '#666666',
          'bg_img': this.$imageHost + '/image/admin/fighting_group_draw1.jpg',
          'is_pay': '2',
          'pay_type': '0',
          'pay_fee': '0.00'
        }
      ],
      nowChecked: '',
      zcCheckedData: '',
      overallColor: null
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        if (this.modulesData) {
          this.nowChecked = this.modulesData // 回显数据
          if (this.modulesData.hidden_card) { // 是否选中用户领取后隐藏会员卡回显
            this.checked = true
          } else {
            this.checked = false
          }
        }
        console.log(newData, this.modulesData)
      },
      immediate: true
    },
    radio (newData) {
      this.showCardList.forEach(item => {
        item.isChecked = false
      })
      switch (newData) {
        case '1':
          this.showCardList = this.ordinaryCardData
          break
        case '2':
          this.showCardList = this.limitCardData
          break
        case '3':
          this.showCardList = this.gradeCardData
      }
    },
    checked (newData) {
      // 如果已经有选中数据则直接改变数据里的isHidden项，若果没有则等待弹窗选中确认后，将是否隐藏卡片checked值赋予选中的数据中的isHidden.
      if (this.nowChecked.id) {
        if (newData) { // 将checked得值转化为0 1
          this.nowChecked.hidden_card = 1
        } else {
          this.nowChecked.hidden_card = 0
        }

        console.log(this.nowChecked)
        this.$emit('handleToBackData', this.nowChecked)
      }
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化获取会员卡数据
    this.handleToGetCardData()
  },
  methods: {
    // 初始化获取会员卡数据
    handleToGetCardData () {
      // 获取初始全局颜色
      this.overallColor = localStorage.getItem('V-backgroundColor') || '#e6cb96'
      allCardData({}).then(res => {
        console.log(res)
        if (res.error === 0) {
          // 处理数据
          this.handleToAllData(res)
        }
      })
    },
    // 处理起始数据
    handleToAllData (res) {
      res.content.normalCard.forEach((item, index) => {
        // 处理每种卡的权益、过期类型
        this.handleToExptreType(item)
      })
      res.content.limitNumCard.forEach((item, index) => {
        // 处理每种卡的权益、过期类型
        this.handleToExptreType(item)
      })
      res.content.rankCard.forEach((item, index) => {
        // 处理每种卡的权益、过期类型
        this.handleToExptreType(item)
      })
      this.ordinaryCardData = res.content.normalCard
      this.limitCardData = res.content.limitNumCard
      this.gradeCardData = res.content.rankCard
      this.showCardList = this.ordinaryCardData
    },
    // 处理每种卡的权益、过期类型
    handleToExptreType (item) {
      item.isChecked = false
      switch (item.expireType) { // 处理有效期
        case 0:
          item.receiveDay = item.startTime + '-' + item.endTime
          break
        case 1:
          item.receiveDay = '自领取之日起' + item.receiveDay + '日内有效'
          break
        case 2:
          item.receiveDay = '有效期：永久有效'
      }
      switch (item.legalFlag) { // 处理权益
        case 0:
          item.legal = ''
          item.exchangCountLegal = ''
          break
        case 1:
          item.legal = `会员${item.legal}折扣权益`
          item.exchangCountLegal = ''
          break
        case 2:
          item.legal = `开卡赠送${item.legal}积分`
          item.exchangCountLegal = ''
          break
        case 3:
          item.legal = `购物满${item.legal[0]}赠${item.legal[1]}积分`
          item.exchangCountLegal = ''
          break
        case 4:
          item.legal = `购物每满${item.legal[0]}赠${item.legal[1]}积分`
          item.exchangCountLegal = ''
          break
        case 5:
          item.legal = `开卡赠送${item.legal}元`
          item.exchangCountLegal = ''
          break
        case 6:
          item.legal = `充值满${item.legal[0]}赠${item.legal[1]}积分`
          item.exchangCountLegal = ''
          break
        case 7:
          item.legal = `充值每满${item.legal[0]}赠送${item.legal[1]}积分`
          item.exchangCountLegal = ''
          break
        case 8:
          item.legal = '仅充值'
          item.exchangCountLegal = ''
          break
        case 9:
          item.exchangCountLegal = `开卡赠送${item.exchangCountLegal}次门店服务机会`
          item.legal = ''
          break
        case 10:
          item.legal = ''
          item.exchangCountLegal = `开卡赠送${item.exchangCountLegal}兑换商品机会`
      }
    },
    // 调起弹窗
    handlCallCardDialog () {
      this.dialogVisible = true
    },
    // 弹窗确定事件
    handleToSelectCuopon () {
      let obj = {
        card_id: this.zcCheckedData.id, // 会员卡id
        card_name: this.zcCheckedData.cardName, // 会员卡名称
        card_state: this.zcCheckedData.cardState, // 会员卡使用状态
        card_grade: this.zcCheckedData.cardGrade, // 会员卡等级
        receive_day: this.zcCheckedData.receiveDay, // 有效期
        card_type: this.zcCheckedData.cardType, // 会员卡等级
        legal: this.zcCheckedData.legal, // 会员卡描述
        exchang_count_legal: this.zcCheckedData.exchangCountLegal, // 会员卡折扣描述
        bg_type: this.zcCheckedData.bgType, // 背景类型
        bg_color: this.zcCheckedData.bgColor, // 背景颜色
        bg_img: this.zcCheckedData.bgImg, // 背景图片
        is_pay: this.zcCheckedData.isPay,
        pay_type: this.zcCheckedData.payType,
        pay_fee: this.zcCheckedData.payFee
      }
      console.log(this.zcCheckedData)
      let data = Object.assign(this.nowChecked, obj)
      console.log(this.nowChecked, data)
      this.$emit('handleToBackData', data)
      this.dialogVisible = false
    },
    // 卡片点击
    handleToClickCard (index) {
      this.showCardList.forEach(item => {
        item.isChecked = false
      })
      this.zcCheckedData = this.showCardList[index]
      // this.nowChecked.index = index
      this.nowChecked = this.showCardList[index]
      this.showCardList[index].isChecked = !this.showCardList[index].isChecked
    },
    //  点击添加会员卡
    handleToAddCard () {
      let obj = { query: { 'cardType': Number(this.radio) } }
      switch (this.radio) {
        case '1':
          obj.name = 'normalCardDetail'
          break
        case '2':
          obj.name = 'limitCardDetail'
          break
        case '3':
          obj.name = 'gradeCardDetail'
      }
      this.$router.push(obj)
    },
    // 点击搜索
    handleToSearchCard () {
      console.log(this.input)
      let obj = {
        'cardType': (Number(this.radio) - 1),
        'cardName': this.input
      }
      allCardData(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          // 处理数据
          this.handleToAllData(res)
        }
      })
    },
    // 点击刷新
    handleToRefresh () {
      this.handleToSearchCard()
    }
  }
}
</script>
<style lang="scss" scoped>
.rightMembershipCard {
  .rightMembershipCardMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    .content {
      display: flex;
      margin: 20px 0 0 20px;
      .cardListContainer {
        overflow: hidden;
        .selectCard {
          width: 205px;
          height: 80px;
          // background: #ecca90;
          border-radius: 10px;
          color: #fff;
          font-size: 14px;
          // margin-top: 20px;
          cursor: pointer;
          padding: 16px 8px 0 20px;
          margin-bottom: 10px;
          float: left;
          position: relative;
          div {
            display: flex;
            justify-content: space-between;
            .card_name {
              max-width: 100px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              display: inline-block;
            }
            .card_state {
              display: inline-block;
              margin-left: 12px;
              border: 1px solid #fff;
              padding: 4px 12px;
              font-size: 12px;
              -webkit-border-radius: 25px;
              -moz-border-radius: 25px;
              border-radius: 25px;
              position: relative;
              top: -3px;
              white-space: nowrap;
            }
          }
          .receive_day {
            font-size: 12px;
            margin-top: 6px;
          }
        }
        .card_add {
          background: #fff;
          border: 1px solid #e4e4e4;
          text-align: center;
          width: 130px;
          padding: 13px 0;
          cursor: pointer;
          img {
            margin-top: 10px;
          }
          p {
            color: #999;
            font-size: 12px;
            margin: 8px 0 0 0;
          }
        }
        .footer {
          margin-top: 20px;
        }
      }
    }
  }
}
.dialogMain {
  margin: -30px -20px;
  height: 319px;
  .main {
    display: flex;
    .dialogMainLeft {
      width: 140px;
      border-right: 1px solid #eee;
      padding-top: 30px;
      text-align: center;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      /deep/ .el-radio {
        margin-bottom: 10px;
        margin-right: 0;
      }
    }
    .dialogMainRight {
      flex: 1;
      height: 319px;
      padding: 12px 12px 12px 20px;
      overflow-y: auto;
      .rightMain {
        // height: 1000px;
        .mainRightTop {
          display: flex;
          justify-content: space-between;
          .left {
            display: flex;
            /deep/ .el-button {
              margin-left: 10px;
            }
          }
          .right {
            display: flex;
            align-items: center;
            color: #5a8bff;
            cursor: pointer;
          }
        }
        .tips {
          background-color: rgb(255, 247, 235);
          padding: 3px 11px;
          border-width: 1px;
          border-style: solid;
          border-color: rgb(255, 213, 163);
          margin-top: 15px;
          p {
            line-height: 20px;
          }
        }
        .mainRightMiddle {
          ul {
            display: flex;
            flex-wrap: wrap;
            li {
              width: 205px;
              height: 80px;
              // background: #ecca90;
              border-radius: 10px;
              color: #fff;
              font-size: 14px;
              margin-top: 20px;
              cursor: pointer;
              padding: 16px 8px 0 20px;
              // float: left;
              position: relative;
              // &:nth-of-type(odd) {
              margin-right: 20px;
              // }
              .isCheck {
                position: absolute;
                top: -5px;
                right: -5px;
              }
              div {
                display: flex;
                justify-content: space-between;
                .card_name {
                  max-width: 100px;
                  overflow: hidden;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                  display: inline-block;
                }
                .card_state {
                  display: inline-block;
                  margin-left: 12px;
                  border: 1px solid #fff;
                  padding: 4px 12px;
                  font-size: 12px;
                  -webkit-border-radius: 25px;
                  -moz-border-radius: 25px;
                  border-radius: 25px;
                  position: relative;
                  top: -3px;
                  white-space: nowrap;
                }
              }
              .receive_day {
                font-size: 12px;
                margin-top: 6px;
              }
            }
            .addCard {
              background-color: #d5d6d8;
              display: flex;
              flex-direction: column;
              align-items: center;
              justify-content: center;
              padding: 0 !important;
              img {
                width: 25px;
              }
              p {
                font-size: 12px;
                margin-top: 5px;
              }
            }
          }
        }
      }
    }
  }
}
</style>
