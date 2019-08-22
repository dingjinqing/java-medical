<template>
  <div class="membershipCardDetail">
    <div class="membershipCardDetailMain">
      <div class="leftContainer">
        <div class="left_Top"></div>
        <div class="left_middle">
          <div class="example_card">
            <div class="card_detail">
              <img :src="$imageHost+'/image/admin/img_home/testImg.jpeg'">
            </div>
            <div class="effect_date">
              有效期：-
            </div>
          </div>
          <div
            class="score_power"
            v-for="(item,index) in leftNavData"
            :key="index"
          >
            <div
              class="s_power_title"
              :style="getStyle(item)"
            >{{item.title}}</div>
            <div class="s_power_detail">
              <div
                class="man"
                style="display: block;"
                v-for="(itemC,indexC) in item.children"
                :key="indexC"
              >
                <p>{{itemC}}</p>
              </div>
              <p
                class="every_man"
                style="display: none;"
              ></p>
            </div>
          </div>
        </div>
      </div>
      <div class="rightContainer">
        <div class="rightTile">基础设置</div>
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item
            label="会员卡名称："
            prop="name"
            class="userCardName first"
          >
            <el-input
              v-model="ruleForm.name"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="背景图："
            class="userCardName"
          >
            <div class="backgroundDiv">
              <div class="bgTop">
                <el-radio
                  v-model="ruleForm.bgFlag"
                  label="1"
                >背景色</el-radio>
                <!--颜色选择器-->
                <colorPicker
                  v-model="colorLeft_"
                  :defaultColor="defaultColorleft"
                  v-on:change="headleChangeColorLeft"
                />
              </div>
              <div class="bgBottom">
                <el-radio
                  v-model="ruleForm.bgFlag"
                  label="2"
                >背景图</el-radio>
                <img
                  :src="baImgUrl"
                  class="bgImgDiv"
                  @click="handleToAddImg()"
                  :style="`backgroundImage:url(${$imageHost}/image/admin/add_img.png);backgroundRepeat:no-repeat`"
                />

              </div>
            </div>
          </el-form-item>
          <el-form-item
            label="会员权益："
            prop="discount"
            class="userCardName"
          >
            <div class="discountDiv equity">
              <el-checkbox v-model="ruleForm.discount">会员折扣</el-checkbox>
              <el-input
                v-model="discountInput"
                size="small"
              ></el-input>
              &nbsp;&nbsp;折
            </div>
            <div class="allGoods">
              <div style="margin-right:25px">会员折扣商品</div>
              <el-radio
                v-model="ruleForm.allGoods"
                label="1"
              >全部商品</el-radio>
              <el-radio
                v-model="ruleForm.allGoods"
                label="2"
              >指定商品</el-radio>
            </div>
            <!--点击指定商品后显示模块-->
            <div
              class="noneBlock"
              v-if="ruleForm.allGoods==='2'"
            >
              <div
                class="noneBlockList"
                v-for="(item,index) in noneBlockDiscArr"
                :key="index"
              >
                <div class="noneBlockLeft">
                  <img :src="$imageHost+'/image/admin/icon_jia.png'">
                  {{item.name}}
                </div>
                <div
                  v-if="item.num"
                  class="noneBlockRight"
                >已选择分类：{{item.num}}个分类</div>
              </div>
            </div>
            <!--end-->
            <div>
              <div class="vipDiv">
                <el-checkbox v-model="ruleForm.vipFlag">
                  <span style="margin-right:25px">会员专享商品</span>
                  <span>选择仅供持有此会员卡用户购买的商品</span>
                </el-checkbox>
              </div>
            </div>
            <!--点击指定商品后显示模块-->
            <div
              class="noneBlock"
              v-if="ruleForm.vipFlag"
            >
              <div
                class="noneBlockList"
                v-for="(item,index) in noneBlockVipArr"
                :key="index"
              >
                <div class="noneBlockLeft">
                  <img :src="$imageHost+'/image/admin/icon_jia.png'">
                  {{item.name}}
                </div>
                <div
                  v-if="item.num"
                  class="noneBlockRight"
                >已选择分类：{{item.num}}个分类</div>
              </div>
            </div>
            <!--end-->
            <div class="discountDiv equity">
              <el-checkbox v-model="ruleForm.intGet">积分获取&nbsp;&nbsp;&nbsp;&nbsp;开卡赠送</el-checkbox>
              <el-input
                v-model="discountInput"
                size="small"
              ></el-input>
              &nbsp;&nbsp;积分
            </div>
            <!--积分获取下方子模块-->
            <div class="shoppingFull">
              <div class="shoppingFullTop">
                <el-radio
                  v-model="ruleForm.shoppingFull"
                  label="1"
                >购物满</el-radio>
                <el-input
                  size="small"
                  v-model="ruleForm.shopingInputLeft"
                ></el-input>&nbsp;&nbsp;送&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="ruleForm.shopingInputReft"
                ></el-input>&nbsp;&nbsp;积分&nbsp;&nbsp;<img
                  style="cursor:pointer"
                  :src="$imageHost +'/image/admin/sign_jia.png' "
                  @click="handleToAddIntegral()"
                >
              </div>
              <block
                v-for="(item,index) in ruleForm.addIntegralArr"
                :key="index"
              >
                <div class="noneIntegralDiv">
                  <span>购物满</span>
                  <el-input
                    size="small"
                    v-model="ruleForm.addIntegralArr[index].leftInput"
                  ></el-input>&nbsp;&nbsp;送&nbsp;&nbsp;
                  <el-input
                    size="small"
                    v-model="ruleForm.addIntegralArr[index].rightInput"
                  ></el-input>&nbsp;&nbsp;积分&nbsp;&nbsp;<img
                    style="cursor:pointer"
                    :src="$imageHost +'/image/admin/sign_del.png' "
                    @click="handleToDelIntegral(index)"
                  >
                </div>
              </block>
              <div class="shoppingFullBottom">
                <el-radio
                  v-model="ruleForm.shoppingFull"
                  label="2"
                >购物每满</el-radio>
                <el-input
                  size="small"
                  v-model="ruleForm.shopingInputLeftM"
                ></el-input>&nbsp;&nbsp;送&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="ruleForm.shopingInputReftM"
                ></el-input>&nbsp;&nbsp;积分

              </div>
            </div>
            <!--卡充值-->
            <div class="cardRecharge">
              <el-checkbox v-model="ruleForm.cardRechargeFlag">卡充值&nbsp;&nbsp;&nbsp;&nbsp;开卡赠送</el-checkbox>
              <el-input
                v-model="ruleForm.cardRechargeInput"
                size="small"
              ></el-input>
              &nbsp;&nbsp;元
            </div>
            <!--卡充值下方子模块-->
            <div class="shoppingFull">
              <div class="shoppingFullTop">
                <el-radio
                  v-model="ruleForm.rechargeInput"
                  label="1"
                >充值满</el-radio>
                <el-input
                  size="small"
                  v-model="ruleForm.rechargeInputLeft"
                ></el-input>&nbsp;&nbsp;送&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="ruleForm.rechargeInputReft"
                ></el-input>&nbsp;&nbsp;元&nbsp;&nbsp;<img
                  style="cursor:pointer"
                  :src="$imageHost +'/image/admin/sign_jia.png' "
                  @click="handleToAddRecharge()"
                >
              </div>
              <block
                v-for="(item,index) in ruleForm.addrechargeArr"
                :key="index"
              >
                <div class="noneIntegralDiv">
                  <span>充值满</span>
                  <el-input
                    size="small"
                    v-model="ruleForm.addrechargeArr[index].leftInput"
                  ></el-input>&nbsp;&nbsp;送&nbsp;&nbsp;
                  <el-input
                    size="small"
                    v-model="ruleForm.addrechargeArr[index].rightInput"
                  ></el-input>&nbsp;&nbsp;元&nbsp;&nbsp;<img
                    style="cursor:pointer"
                    :src="$imageHost +'/image/admin/sign_del.png' "
                    @click="handleToDelRecharge(index)"
                  >
                </div>
              </block>
              <div class="shoppingFullBottom">
                <el-radio
                  v-model="ruleForm.rechargeInput"
                  label="2"
                >充值每满</el-radio>
                <el-input
                  size="small"
                  v-model="ruleForm.rechargeInputLeftM"
                ></el-input>&nbsp;&nbsp;送&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="ruleForm.rechargeInputReftM"
                ></el-input>&nbsp;&nbsp;元

              </div>
            </div>
            <!--end-->
            <!--开卡送卷-->
            <div class="sendingPaper">
              <el-checkbox v-model="ruleForm.sendingPaperFlag">开卡送卷&nbsp;&nbsp;&nbsp;&nbsp;需要激活的会员卡,激活成功后送卷到个人账户中</el-checkbox>
            </div>
            <!--开卡送卷子模块-->
            <div
              class="couponDiv"
              v-if="ruleForm.sendingPaperFlag"
            >
              <div class="couponDivTop">
                <el-radio
                  v-model="ruleForm.couponDiv"
                  label="1"
                >送优惠卷&nbsp;&nbsp;&nbsp;&nbsp;最多可添加5种优惠卷,每种优惠卷赠送一张</el-radio>
                <div
                  class="card_add_clickDiv"
                  @click="handleToCallDialog()"
                >
                  <div class="card_add_click">
                    <img :src="$imageHost +'/image/admin/shop_beautify/add_decorete.png'">
                    <p>添加优惠卷</p>
                  </div>
                </div>
              </div>
              <div class="couponDivBottom">
                <el-radio
                  v-model="ruleForm.couponDiv"
                  label="2"
                >送优惠卷礼包</el-radio>
              </div>

            </div>
          </el-form-item>
        </el-form>
      </div>

    </div>
    <div class="footer">
      <div
        class="save"
        @click="handleToSave('ruleForm')"
      >{{$t('shopStyle.saveText')}}</div>
    </div>
    <!--图片弹窗-->
    <ImageDalog
      pageIndex='userCardAdd'
      @handleSelectImg='handleSelectImg'
    />
    <!--添加优惠卷-->
    <AddCouponDialog />
  </div>
</template>
<script>
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: {
    ImageDalog,
    AddCouponDialog: () => import('./addCouponDialog')
  },
  data () {
    return {
      colorLeft_: '',
      defaultColorleft: '#fff',
      leftNavData: [
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/discount.png',
          title: '会员权益(折扣)',
          children: []
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/score_mem.png',
          title: '会员折扣(积分)',
          children: ['购物满100宋100积分']
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/charge_icon.png',
          title: '卡充值规则',
          children: ['仅充值']
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/article.png',
          title: '会员卡使用说明',
          children: []
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/store_icon.png',
          title: '使用门店',
          children: ['全部门店']
        }
      ],
      ruleForm: {
        name: '',
        bgFlag: '1',
        discount: false,
        allGoods: '1',
        vipFlag: false,
        shoppingFull: '1',
        intGet: false,
        shopingInputLeft: '100',
        shopingInputReft: '100',
        shopingInputLeftM: '100',
        shopingInputReftM: '100',
        addIntegralArr: [],
        cardRechargeFlag: false,
        cardRechargeInput: '',
        rechargeInput: '1',
        rechargeInputLeft: '100',
        rechargeInputReft: '100',
        rechargeInputLeftM: '100',
        rechargeInputReftM: '100',
        addrechargeArr: [],
        sendingPaperFlag: false,
        couponDiv: '1'
      },
      rules: {
        name: [{ required: true, message: '请输入会员卡名称', trigger: 'blur' }]
        // discount: [{ type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }]
      },
      baImgUrl: null,
      discountInput: '',
      noneBlockDiscArr: [
        {
          name: '添加商品',
          num: '1'
        },
        {
          name: '添加商品分类',
          num: ''
        },
        {
          name: '添加平台分类',
          num: '2'
        },
        {
          name: '添加品牌',
          num: ''
        }
      ],
      noneBlockVipArr: [
        {
          name: '添加商品',
          num: '1'
        },
        {
          name: '添加商品分类',
          num: ''
        },
        {
          name: '添加平台分类',
          num: '2'
        },
        {
          name: '添加品牌',
          num: ''
        }
      ],
      couponDialogFlag: false
    }
  },
  methods: {
    // 动态添加样式
    getStyle (item) {
      console.log(item)
      return 'backgroundImage:url(' + item.backGroundImgUrl + ')'
    },
    // 点击保存
    handleToSave (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
          this.$refs[formName].resetFields()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 颜色选择器选中
    headleChangeColorLeft () {
      console.log(this.colorLeft_)
    },
    // 添加图片
    handleToAddImg () {
      this.$http.$emit('dtVisible')
    },
    // 图片选中
    handleSelectImg (res) {
      this.baImgUrl = res
      console.log(res)
    },
    // 增加购物满积分模块
    handleToAddIntegral () {
      // addIntegralArr
      let obj = {
        leftInput: '',
        rightInput: ''
      }
      this.ruleForm.addIntegralArr.push(obj)
    },
    // 删除购物满积分模块
    handleToDelIntegral (index) {
      console.log(this.ruleForm.addIntegralArr)
      this.ruleForm.addIntegralArr.splice(index, 1)
      console.log(index)
    },
    // 增加充值满模块
    handleToAddRecharge () {
      let obj = {
        leftInput: '',
        rightInput: ''
      }
      this.ruleForm.addrechargeArr.push(obj)
    },
    // 删除充值满模块
    handleToDelRecharge (index) {
      this.ruleForm.addrechargeArr.splice(index, 1)
    },
    // 调起添加优惠卷弹窗
    handleToCallDialog () {
      this.couponDialogFlag = !this.couponDialogFlag
      this.$http.$emit('V-AddCoupon', this.couponDialogFlag)
    }
  }
}
</script>
<style scoped lang="scss">
.membershipCardDetail {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-x: hidden;
  .membershipCardDetailMain {
    position: relative;
    background-color: #fff;
    overflow-x: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    height: 100%;
    display: flex;
    padding-bottom: 50px;
    .leftContainer {
      width: 300px;
      margin-right: 20px;
      height: 594px;
      overflow-y: auto;
      border: 1px solid #ccc;
      background: #f5f5f5;
      .left_Top {
        height: 50px;
        width: 100%;
        background: url(../../../../../../assets/adminImg/mem_card.png)
          no-repeat;
        background-size: 100%;
      }
      .left_middle {
        .example_card {
          width: 90%;
          margin: 10px auto;
          border-radius: 10px;
          background: #e6cb96;
          color: #ffffff;
          padding: 10px 12px;
          .card_detail {
            margin-bottom: 30px;
            img {
              width: 50px;
              height: 50px;
              border-radius: 120px;
              margin-right: 10px;
            }
          }
          .effect_date {
            font-size: 13px;
          }
        }
        .score_power {
          background-color: #fff;
          .s_power_title {
            padding: 10px 0;
            background: url(../../../../../../assets/adminImg/score_mem.png)
              no-repeat;
            background-size: 6%;
            background-position: center left;
            padding-left: 25px;
            font-size: 13px;
            color: #333;
            margin-left: 12px;
          }
          .man {
            color: #878787;
            padding: 0 0 10px;
            padding-left: 25px;
            margin-left: 12px;
            p {
              padding-top: 10px;
              color: #878787;
              padding: 0 0 10px;
            }
          }
        }
      }
    }
    .rightContainer {
      width: 60%;
      background: #f8f8f8;
      border: 1px solid #e4e4e4;
      padding: 10px 1%;
      font-size: 13px;
      margin-bottom: 10px;
      height: 1000px;
      /deep/ .el-form-item__label {
        white-space: nowrap;
        text-align: right;
      }
      .rightTile {
        padding-bottom: 10px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 10px;
      }
      .userCardName {
        padding-left: 100px;
        /deep/ .el-input__inner {
          width: 41%;
        }
        .bgTop {
          height: 40px;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          /deep/ .colorBtn {
            width: 65px;
            height: 30px;
            border: 1px solid #ccc;
          }
        }
        .bgBottom {
          height: 65px;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          .bgImgDiv {
            width: 65px;
            height: 65px;
            border: 1px solid #ccc;
            background-position: center;
            cursor: pointer;
          }
        }
        .discountDiv {
          display: flex;
          height: 40px;
          justify-content: flex-start;
          align-items: center;
        }
        .allGoods {
          display: flex;
          align-items: center;
        }
        .equity {
          /deep/ .el-radio {
            margin-right: 17px;
          }
          /deep/ .el-input {
            width: 20%;
            .el-input__inner {
              width: 100%;
            }
          }
        }
        .noneBlock {
          .noneBlockList {
            margin-bottom: 10px;
            display: flex;
            .noneBlockLeft {
              line-height: 30px;
              height: 30px;
              width: 120px;
              text-align: left;
              color: #5a8bff;
              border: 1px solid #ccc;
              background: #fff;
              cursor: pointer;
              padding-left: 5px;
              margin-right: 20px;
            }
            .noneBlockRight {
              color: #5a8bff;
              cursor: pointer;
              height: 30px;
              line-height: 30px;
            }
          }
        }
        .shoppingFull {
          padding-left: 54px;
          .shoppingFullTop,
          .shoppingFullBottom,
          .noneIntegralDiv {
            display: flex;
            align-items: center;
            /deep/ .el-radio {
              margin-right: 25px;
            }
            /deep/ .el-input {
              width: 20%;
              .el-input__inner {
                width: 100%;
              }
            }
          }
          .shoppingFullBottom {
            /deep/ .el-radio {
              margin-right: 12px;
            }
          }
          .noneIntegralDiv {
            margin-left: 25px;
            span {
              margin-right: 24px;
            }
            /deep/ .el-input {
              width: 20.7%;
            }
          }
        }
        .cardRecharge {
          display: flex;
          /deep/ .el-radio {
            margin-right: 25px;
          }
          /deep/ .el-checkbox {
            margin-right: 40px;
          }
          /deep/ .el-input {
            width: 20%;
            .el-input__inner {
              width: 100%;
            }
          }
        }
        .couponDiv {
          padding-left: 30px;
          display: flex;
          flex-direction: column;
          .card_add_clickDiv {
            padding-left: 30px;
            .card_add_click {
              background: #fff;
              border: 1px solid #e4e4e4;
              cursor: pointer;
              width: 100px;
              display: flex;
              justify-content: center;
              align-items: center;
              flex-direction: column;
              img {
                margin-top: 14px;
              }
              p {
                color: #999;
                font-size: 12px;
                margin: 8px 0 0 0;
              }
            }
          }
        }
      }
      .first {
        /deep/ .el-form-item__label {
          margin-left: -8px;
        }
      }
    }
  }
}
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: absolute;
  z-index: 2;
  bottom: 0;
  padding: 10px 0;
  left: 0;
  right: 0;
  margin-right: 10px;
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
}
</style>
