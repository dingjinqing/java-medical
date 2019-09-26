<template>
  <div class="rightMembershipCard">
    <div class="rightMembershipCardMain">
      <h2>会员卡模块</h2>

      <!-- <el-button @click='handleToSelectCuopon()'>会员模块测试</el-button> -->
      <div class="content">
        <div>
          会员卡：
        </div>
        <div class="cardListContainer">
          <div class="cardList">

          </div>
          <div>
            <div
              class="card_add"
              @click="handlCallCardDialog()"
              v-if="nowChecked.tips==='********'"
            >
              <img :src="$imageHost+'/image/admin/shop_beautify/add_decorete.png'">
              <p>添加会员卡</p>
            </div>
            <!--选中模块-->
            <div
              v-else
              @click="handlCallCardDialog()"
              class="selectCard"
              :style="nowChecked.backgroundColor?`backgroundColor:${nowChecked.backgroundColor}`:`backgroundImage:url('${nowChecked.bgImgUrl}')`"
            >
              <div>
                <span class="card_name">{{nowChecked.cardName}}</span>
                <span
                  style="margin-bottom:8px"
                  v-if="radio==='3'"
                >{{nowChecked.grade}}</span>
                <span
                  v-else
                  class="card_state"
                >使用中</span>
              </div>
              <p
                class="receive_day"
                v-if="typeof nowChecked.expired === 'number'&&radio!== '3'"
              >领取之日起{{nowChecked.expired}}日内有效</p>
              <p
                v-if="typeof nowChecked.expired !== 'number'&&radio!== '3'"
                class="receive_day"
              >有效期：永久有效</p>
            </div>
            <div class="footer">
              <el-checkbox v-model="checked"></el-checkbox>
              用户领取后隐藏会员卡
            </div>
          </div>

        </div>

      </div>
    </div>
    <!--页面装修右侧会员卡弹窗-->
    <el-dialog
      title="选择会员卡"
      :visible.sync="dialogVisible"
      width="45%"
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
              <!--右侧中部-->
              <div class="mainRightMiddle">
                <ul>
                  <li
                    v-for="(item,index) in showCardList"
                    :key="index"
                    :style="item.backgroundColor?`backgroundColor:${item.backgroundColor}`:`backgroundImage:url('${item.bgImgUrl}')`"
                    @click="handleToClickCard(index)"
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
                      >使用中</span>
                    </div>
                    <p
                      class="receive_day"
                      v-if="typeof item.expired === 'number'&&radio!== '3'"
                    >领取之日起{{item.expired}}日内有效</p>
                    <p
                      v-if="typeof item.expired !== 'number'&&radio!== '3'"
                      class="receive_day"
                    >有效期：永久有效</p>
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
          cardName: '普通卡续费测试1',
          expired: 1,
          backgroundColor: '#ecca90',
          cardId: 1,
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '普通卡续费测试2',
          expired: '永久有效',
          backgroundColor: '#990000',
          cardId: 2,
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '普通卡续费测试3',
          expired: 3,
          backgroundColor: '#66FF2B',
          cardId: 3,
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '普通卡续费测试4',
          expired: '永久有效',
          backgroundColor: '',
          cardId: 4,
          isChecked: false,
          bgImgUrl: this.$imageHost + '/image/admin/fighting_group_draw1.jpg',
          tips: '购物满100赠100积分',
          isHidden: false
        }
      ],
      limitCardData: [
        {
          cardName: '限次卡续费测试1',
          expired: 1,
          backgroundColor: '#ecca90',
          cardId: 5,
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '限次卡续费测试2',
          expired: '永久有效',
          backgroundColor: '#990000',
          cardId: 6,
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '限次卡续费测试3',
          expired: 3,
          backgroundColor: '#66FF2B',
          cardId: 7,
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '限次卡续费测试4',
          expired: '永久有效',
          backgroundColor: '',
          cardId: 8,
          isChecked: false,
          bgImgUrl: this.$imageHost + '/image/admin/fighting_group_draw1.jpg',
          tips: '购物满100赠100积分',
          isHidden: false
        }
      ],
      gradeCardData: [
        {
          cardName: '限次卡续费测试1',
          expired: 1,
          backgroundColor: '#ecca90',
          cardId: 5,
          grade: 'v1',
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '限次卡续费测试2',
          expired: '永久有效',
          backgroundColor: '#990000',
          cardId: 6,
          grade: 'v3',
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '限次卡续费测试3',
          expired: 3,
          backgroundColor: '#66FF2B',
          cardId: 7,
          grade: 'v6',
          isChecked: false,
          tips: '购物满100赠100积分',
          isHidden: false
        },
        {
          cardName: '限次卡续费测试4',
          expired: '永久有效',
          backgroundColor: '',
          cardId: 8,
          grade: 'v10',
          isChecked: false,
          bgImgUrl: this.$imageHost + '/image/admin/fighting_group_draw1.jpg',
          tips: '购物满100赠100积分',
          isHidden: false
        }
      ],
      nowChecked: '',
      zcCheckedData: ''
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        if (this.modulesData) {
          this.nowChecked = this.modulesData
          this.checked = this.modulesData.isHidden
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
      if (this.nowChecked) {
        this.nowChecked.isHidden = newData

        console.log(this.nowChecked)
        this.$emit('handleToBackData', this.nowChecked)
      }
    }
  },
  mounted () {
    this.showCardList = this.ordinaryCardData
  },
  methods: {
    // 调起弹窗
    handlCallCardDialog () {
      this.dialogVisible = true
    },
    // 弹窗确定事件
    handleToSelectCuopon () {
      console.log(this.nowChecked)
      this.nowChecked = this.zcCheckedData
      let obj = {
        carClass: this.radio

      }
      this.nowChecked.isHidden = this.checked
      let data = Object.assign(obj, this.nowChecked)
      console.log(data)
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
      this.showCardList[index].isChecked = !this.showCardList[index].isChecked
    },
    //  点击添加会员卡
    handleToAddCard () {

    },
    // 点击搜索
    handleToSearchCard () {
      console.log(this.input)
    },
    // 点击刷新
    handleToRefresh () {

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
<style>
.el-dialog__header {
  text-align: center;
  font-size: 14px;
  background-color: #f3f3f3;
  padding-top: 10px;
}
.el-dialog__header .el-dialog__title {
  font-size: 14px;
}
.el-dialog__header .el-dialog__headerbtn {
  top: 12px;
}
.el-dialog__footer {
  padding-bottom: 10px;
  border-top: 1px solid #f3f3f3;
}
</style>
