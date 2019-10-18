<template>
    <div class="luckyDrawAdd">
        <el-card>
            <div class="mainContent">
                <div class="mainContentLeft">
                    <section class="drawContent">
                        <div class="top">
                            <div>幸运大转盘</div>
                        </div>
                        <div class="drawArea">
                            <div class="loTop">
                                <div class="loArea">
                                    <div class="picBox">
                                        <ul class="picWrapper">
                                            <li class="picItems">1</li>
                                            <li class="picItems">2</li>
                                            <li class="picItems">3</li>
                                            <li class="picItems">4</li>
                                            <li class="picItems">5</li>
                                            <li class="picItems">6</li>
                                            <li class="picItems">7</li>
                                            <li class="picItems">8</li>
                                            <li class="picItems">9</li>
                                        </ul>
                                    </div>
                                    <div class="winningTips">
                                        <img
                                                src="http://mpdevimg2.weipubao.cn/image/admin/icon_lottery/lo_words.png"
                                                alt=""
                                        >
                                        张三获得
                                        <span>一等奖50积分</span>
                                    </div>
                                </div>
                            </div>
                            <div class="immediatelyDraw">
                                <div class="drawText">立即抽奖</div>
                            </div>
                            <div class="actRule">
                                <div class="ruleInfo">
                                    <img
                                            src="http://mpdevimg2.weipubao.cn/image/admin/icon_lottery/lo_rule_l.png"
                                            alt=""
                                    >
                                    <span>活动规则</span>
                                    <img
                                            src="http://mpdevimg2.weipubao.cn/image/admin/icon_lottery/lo_rule_r.png"
                                            alt=""
                                    >
                                </div>
                                <div class="ruleContent">
                                    <div class="timeRange">活动有效期：</div>
                                    <div>至：</div>
                                    <div>活动说明：</div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>

                <div class="mainContentRight">
                    <!-- 活动信息 -->
                    <div class="boxWrapper">
                        <div class="textDesc">活动信息</div>
                        <el-form label-width="100px"  ref="form">
                            <el-form-item label="活动名称：">
                                <el-input
                                        size="small"
                                        v-model="requestParam.lotteryName"
                                        placeholder="最多支持10个字"
                                        style="width: 180px;"
                                ></el-input>
                            </el-form-item>

                            <el-form-item label="活动有效期：">
                                <div>
                                    <span>生效时间：</span>
                                    <el-date-picker
                                            v-model="requestParam.startTime"
                                            type="datetime"
                                            placeholder="选择日期时间"
                                            size="small"
                                            style="width: 180px"
                                            value-format="yyyy-MM-dd HH:mm:ss"
                                    >
                                    </el-date-picker>
                                </div>
                                <div>
                                    <span>过期时间：</span>
                                    <el-date-picker
                                            v-model="requestParam.endTime"
                                            type="datetime"
                                            placeholder="选择日期时间"
                                            size="small"
                                            style="width: 180px"
                                            value-format="yyyy-MM-dd HH:mm:ss"
                                    >
                                    </el-date-picker>
                                </div>
                            </el-form-item>

                            <el-form-item label="活动说明：">
                                <el-input
                                        v-model="requestParam.lotteryExplain"
                                        type="textarea"
                                        :rows="4"
                                        placeholder="请输入内容"
                                        style="width: 300px;height: 100px;"
                                ></el-input>
                            </el-form-item>
                        </el-form>
                    </div>

                    <!-- 规则设置 -->
                    <div class="boxWrapper">
                        <div class="textDesc">规则设置</div>
                        <el-form label-width="125px">
<!--                            <el-form-item-->
<!--                                    label="活动规则："-->
<!--                                    prop=""-->
<!--                            >-->
<!--                                <el-radio-group v-model="times">-->
<!--                                    <el-radio :label="1">每人N次</el-radio>-->
<!--                                    <el-radio :label="2">每人每天N次</el-radio>-->
<!--                                </el-radio-group>-->
<!--                            </el-form-item>-->

                            <el-form-item label="免费抽奖：">
                                <el-input
                                        size="small"
                                        placeholder="为空表示不限制"
                                        style="width:125px"
                                        v-model="requestParam.freeChances"
                                ></el-input>
                                <span style="color:#999;">用户可免费抽奖几次</span>
                            </el-form-item>

                            <el-form-item label="分享抽奖：">
                                <el-radio-group v-model="requestParam.canShare">
                                    <el-radio :label="1">允许</el-radio>
                                    <el-radio :label="2">不允许</el-radio>
                                </el-radio-group>
                                <div v-if="requestParam.canShare === 1">
                                    <span style="color: #999">用户无免费抽奖机会时可通过分享给好友获得抽奖机会</span>
                                    <div>
                                        分享最多获得
                                        <el-input
                                                size="small"
                                                placeholder="为空表示不限制"
                                                style="width:125px"
                                                v-model="requestParam.shareChances"
                                        ></el-input>
                                        次抽奖机会
                                    </div>
                                </div>
                            </el-form-item>

                            <el-form-item label="付费抽奖：">
                                <el-radio-group v-model="requestParam.canUserScore">
                                    <el-radio :label="1">允许</el-radio>
                                    <el-radio :label="2">不允许</el-radio>
                                </el-radio-group>
                                <div v-if="requestParam.canUserScore===1">
                                    <span style="color: #999;">用户无法通过分享获取抽奖机会时可通过消耗积分获得抽奖机会</span>
                                    <div>
                                        每次抽奖消耗积分：
                                        <el-input
                                                size="small"
                                                placeholder="为空表示不消耗积分"
                                                style="width:125px"
                                                v-model="requestParam.scorePerChance"
                                        ></el-input>
                                        <span style="color:#999">用户每次参与需要消耗积分数</span>
                                    </div>
                                    <div>
                                        付费最多获取
                                        <el-input
                                                size="small"
                                                placeholder="为空表示不限制"
                                                style="width:125px"
                                                v-model="requestParam.scoreChance"
                                        ></el-input>
                                        次抽奖机会
                                    </div>
                                </div>
                            </el-form-item>

                            <el-form-item label="未中奖赠送积分：">
                                <el-input
                                        size="small"
                                        placeholder="为空表示不赠送积分"
                                        style="width: 185px"
                                        v-model="requestParam.noAwardScore"
                                ></el-input>
                                <span style="color: #999">仅送给未中奖用户</span>
                                <section class="upInfo">
                                    <div class="upIcons">
                                        <div class="leftContent">
                                            <img
                                                    :src="this.$imageHost + this.requestParam.noAwardImage"
                                                    alt=""
                                            >
                                        </div>
                                        <div class="rightContent">
                                            <div class="operate">
                                                <span>修改</span>
                                                <span class="operateBtn fixstyle" @click="changeImgHandler(1)">上传未中奖图表</span>
                                                <span class="operateBtn">清空</span>
                                            </div>
                                            <p>仅支持jpg/png/尺寸80*80 不超过1M</p>
                                        </div>
                                    </div>
                                    <div class="iconDesc">
                                        <span>icon描述：</span>
                                        <el-input
                                                size="small"
                                                placeholder="最多可填写4个字"
                                                style="width: 180px"
                                                v-model="requestParam.noAwardIcon"
                                        ></el-input>
                                    </div>
                                </section>
                            </el-form-item>
                        </el-form>
                    </div>

                    <!-- 抽奖设置 -->
                    <div class="boxWrapper">
                        <div class="textDesc">抽奖设置</div>
                        <p class="drawSetting">不同等级分别设置不同的奖项、每个奖项的份数和中奖概率，四个等级中奖概率之和小于等于100%。</p>
                        <p class="drawSetting">
                            例如：一等奖1份，中奖概率为2%；二等奖2份，中奖概率为3%；三等奖3份，中奖概率为4%；四等奖4份，中奖概率为5%。则用户A抽奖时，中奖概率为（2%+3%+4%+5%）=14%。</p>
                        <!-- 一等奖~四等奖 tab切换 -->
                        <el-tabs
                                type="border-card"
                                class="tabs"
                                v-model="tabSwitch"
                                @tab-click="handleTabClick"
                        >
                            <el-tab-pane
                                    v-for="(item,index) in requestParam.prizeList"
                                    :key="index"
                                    :label="item.iconImgs"
                                    :name="item.name"
                            >
                                {{item}}
                                <el-form label="100px">
                                    <el-form-item label="中奖概率：">
                                        <el-input
                                                size="small"
                                                style="width:120px"
                                                v-model="item.chance"
                                                @change="prizeRateChange(item.chance)"
                                        ></el-input>&nbsp;&nbsp;%
                                    </el-form-item>

                                    <el-form-item label="选择奖品：">
                                        <el-radio-group v-model="item.lotteryType">
                                            <el-radio :label="1">积分</el-radio>
                                            <el-radio :label="2">用户余额</el-radio>
                                            <el-radio :label="3">优惠券</el-radio>
                                            <el-radio :label="4">赠品</el-radio>
                                            <el-radio :label="5">自定义</el-radio>
                                        </el-radio-group>
                                        <section style="margin-left: 81px;">
                                            <div v-if="item.lotteryType===1">
                                                <span>赠送积分：</span>
                                                <el-input
                                                        size="small"
                                                        placeholder="请填写积分数"
                                                        style="width: 120px"
                                                        v-model="item.integralScore"
                                                ></el-input>
                                            </div>
                                            <div v-if="item.lotteryType===2">
                                                <span>赠送余额：</span>
                                                <el-input
                                                        size="small"
                                                        placeholder="请填写余额数"
                                                        style="width: 120px"
                                                        v-model="item.integralScore"
                                                ></el-input>
                                            </div>
                                            <div v-if="item.lotteryType===3">
                                                <span>优惠券：</span>
                                                <el-select
                                                        v-model="item.couponId"
                                                        size="small"
                                                        style="width: 120px"
                                                >
                                                    <el-option
                                                            v-for="itema in options"
                                                            :key="itema.value"
                                                            :value="itema.value"
                                                            :label="itema.label"
                                                    ></el-option>
                                                </el-select>
                                                <span>刷新</span>
                                                |
                                                <span>新建</span>
                                                <p style="color: #999;">优惠券可用库存{{1}}份数</p>
                                            </div>
                                            <div v-if="item.lotteryType===4">
                                                <div>
                                                    <span>赠送赠品：</span>
                                                    <span style="border: 1px solid #ccc; cursor:pointer"> + 选择商品</span>
                                                </div>
                                                <div>
                                                    赠品有效期：
                                                    <el-input
                                                            size="small"
                                                            style="width:100px"
                                                            v-model="item.prdKeepDays"
                                                    ></el-input>
                                                    天
                                                    <p style="margin-left:80px;color:#999;">中奖用户需在有效期内领取，过期后将无法领取</p>
                                                </div>
                                            </div>
                                            <div v-if="item.lotteryType===5">
                                                <span>自定义：</span>
                                                <el-input
                                                        size="small"
                                                        placeholder="请填写自定义文字"
                                                        style="width: 150px"
                                                        v-model="item.lotteryDetail"
                                                ></el-input>
                                            </div>
                                            <div>
                                                <span>奖品份数：</span>
                                                <el-input
                                                        size="small"
                                                        placeholder="请填写积分数"
                                                        style="width: 120px"
                                                        v-model="item.lotteryNumber"
                                                ></el-input>&nbsp;份
                                                <div class="numberTips">奖品份数限制中奖人数，中奖人数达到奖品份数，则后续抽奖用户不再中奖</div>
                                                <div class="numberTips bottomTips">份数为空则不设此奖项，即该奖项中奖概率为0</div>
                                            </div>

                                        </section>

                                    </el-form-item>
                                </el-form>
                            </el-tab-pane>
                        </el-tabs>

                        <!-- 一等奖~四等奖 icon图表及其文字设置 -->
                        <div class="levelBoxSetting">
                            <section class="upInfo levelUpInfo">
                                <div class="upIcons">
                                    <div class="leftContent">
                                        <img
                                                src=""
                                                alt=""
                                        >
                                    </div>
                                    <div class="rightContent">
                                        <div class="operate">
                                            <span>修改</span>
                                            <span class="operateBtn fixstyle">上传未中奖图表</span>
                                            <span
                                                    class="operateBtn"
                                                    @click="handleClear()"
                                            >清空
                                            </span>
                                        </div>
                                        <p style="margin-top:10px;">仅支持jpg/png/尺寸80*80 不超过1M</p>
                                    </div>
                                </div>
                                <div class="iconDesc">
                                    <span>icon描述：</span>
                                    <el-input
                                            size="small"
                                            placeholder="最多可填写4个字"
                                            style="width: 180px"
                                            v-model="requestParam.prizeList[tabSwitch].iconImgs"
                                    ></el-input>
                                </div>
                            </section>
                        </div>

                    </div>
                </div>
            </div>

            <div class="save">
                <el-button
                        size="small"
                        type="primary"
                        @click="submitData"
                >保存
                </el-button>
            </div>
            <!--添加商品弹窗-->
            <choosingGoods
                    @resultGoodsRow="choosingGoodsResult"
                    :chooseGoodsBack="[requestParam.prizeList[tabSwitch].iconImgs.prdId]"
                    :tuneUpChooseGoods="isShowChoosingGoodsDialog"
                    :singleElection="true"
                    :showTips="true"
                    :loadProduct="true"
            />
            <!--图片dialog-->
            <ImageDalog
                    :tuneUp="selfImgDialogShow"
                    pageIndex="pictureSpace"
                    :imageSize="[80, 80]"
                    :isDraggable='false'
                    @handleSelectImg='imgDialogSelectedCallback'
            />
        </el-card>
    </div>
</template>

<script>
import choosingGoods from '@/components/admin/choosingGoods'
import ImageDalog from '@/components/admin/imageDalog'
import {addLottery} from '@/api/admin/marketManage/luckyDraw'
export default {
  name: `luckyDrawAdd`,
  components: {
    choosingGoods,
    ImageDalog
  },
  data () {
    return {
      requestParam: {
        lotteryName: '',
        lotteryExplain: '',
        startTime: '',
        endTime: '',
        freeChances: 0,
        canShare: 0,
        shareChances: 0,
        canUserScore: 0,
        scorePerChance: 0,
        scoreChance: 0,
        noAwardScore: '',
        noAwardIcon: '',
        prizeList: [
          {iconImgs: '一等奖', name: '0'},
          {iconImgs: '二等奖', name: '1'},
          {iconImgs: '三等奖', name: '2'},
          {iconImgs: '四等奖', name: '3'}
        ]
      },
      times: 1,
      // 商品弹窗
      isShowChoosingGoodsDialog: false,
      // 图片弹窗
      selfImgDialogShow: false,
      // 提交状态
      submitStatus: false,
      // 是否是设置
      isEdite: false,
      imgDialogIndex: 0,
      options: [
        {value: 1, label: '前端'},
        {value: 2, label: '后端'},
        {value: 3, label: '产品经理'}
      ],
      tabSwitch: '1',
      tabInfo: [
        {title: '一等奖', name: '1'},
        {title: '二等奖', name: '2'},
        {title: '三等奖', name: '3'},
        {title: '四等奖', name: '4'}
      ]
    }
  },
  watch: {

  },
  methods: {
    // 保存
    submitData () {
      this.submitStatus = true
      console.log('this.requestParam', this.requestParam)
      if (this.isEdite) {
        addLottery(this.requestParam).then(res => {
          console.log('update', res)
          if (res.error === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.warning(res.message)
          }
        }).catch(e => {
          console.log(e)
          this.$message.warning(e.message)
        })
      } else {
        addLottery(this.requestParam).then(res => {
          console.log('addLottery', res)
          if (res.error === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.warning(res.message)
          }
        })
      }
      this.submitStatus = false
    },
    handleClear () {
      console.log(1111)
    },
    handleTabClick (tab, event) {
      console.log(tab, event)
    },
    // 显示商品弹窗
    showGoodsDialog (index) {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 放回商品信息
    choosingGoodsResult (res) {
      console.log(res)
    },
    // 显示图片弹窗
    changeImgHandler (index) {
      this.imgDialogIndex = index
      this.selfImgDialogShow = !this.selfImgDialogShow
    },
    // 图片弹窗回调函数
    imgDialogSelectedCallback (image) {
      console.log('图片弹窗回调函数', image)
      if (this.imgDialogIndex === 1) {
        this.requestParam.noAwardImage = '/' + image.imgPath
      } else if (this.imgDialogIndex === 2) {
        this.requestParam.iconImgsImage = this.$imageHost + image.imgPath
      }
      // 更新
      this.$forceUpdate()
    },
    // 中奖率
    prizeRateChange (prizeRate) {
      let den = 100
      let i = String(prizeRate).length - String(prizeRate).indexOf('.')
      while (--i > 0) {
        console.log('changePriae', prizeRate, String(prizeRate).indexOf('.'))
        prizeRate = prizeRate * 10
        den = den * 10
      }
      this.requestParam.prizeList[this.tabSwitch].chanceDenominator = den
      this.requestParam.prizeList[this.tabSwitch].chanceNumerator = Math.round(prizeRate.valueOf())
      // 更新
      this.$forceUpdate()
    }
  }
}
</script>

<style lang="scss" scoped>
    .luckyDrawAdd {
        .mainContent {
            display: flex;
            justify-content: center;
            padding-bottom: 60px;

            .mainContentLeft {
                width: 344px;
                height: 720px;
                border: 1px solid #ccc;
                background: #eee;
                overflow-x: hidden;

                .top {
                    height: 55px;
                    width: 100%;
                    background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
                    color: white;
                    text-align: center;
                    background-size: 100%;

                    div {
                        padding-top: 25px;
                        font-size: 15px;
                    }
                }

                .drawArea {
                    width: 330px;
                    background: #fff5ba;
                    overflow-y: auto;
                }

                .loTop {
                    height: 490px;
                    width: 330px;
                    background: url(../../../../../../../static/image/admin/icon_lottery/lo_bg3.jpg) no-repeat;
                    background-size: 330px 405px;
                    overflow-x: hidden;

                    .loArea {
                        width: 290px;
                        // background: #fc6b58;
                        border-radius: 10px;
                        margin: 0 auto;
                        padding: 12px 0;
                        position: relative;
                        margin-top: 130px;

                        .picBox {
                            width: 270px;
                            padding: 5px;
                            background: #ed503b;
                            border-radius: 8px;
                            margin: 0 auto;
                            overflow: hidden;
                            height: 290px;

                            .picWrapper {
                                padding: 0;
                                overflow: hidden;
                                margin-right: -10px;
                                margin-bottom: -15px;
                                margin-top: 0;

                                .picItems {
                                    float: left;
                                    width: 80px;
                                    height: 80px;
                                    margin-right: 10px;
                                    margin-bottom: 15px;
                                    background: #fbe5e2;
                                    border-radius: 7px;
                                    box-shadow: 0 10px #ff9c80;
                                    display: flex;
                                    justify-content: center;
                                    align-items: center;
                                    flex-direction: column;
                                }
                            }
                        }

                        .winningTips {
                            width: 270px;
                            height: 30px;
                            padding: 0 10px;
                            border-radius: 10px;
                            display: flex;
                            justify-content: flex-start;
                            align-items: center;
                            background: #ff9c80;
                            color: #fff;
                            margin: 0 auto;
                            margin-top: 10px;

                            img {
                                width: 15px;
                                margin-right: 15px;
                            }

                            span {
                                margin-left: 10px;
                                color: #ffe61e;
                            }
                        }
                    }
                }

                .immediatelyDraw {
                    width: 290px;
                    height: 40px;
                    position: relative;
                    box-shadow: 0 5px 5px #fdc247;
                    margin: 10px auto;
                    border-radius: 20px;

                    .drawText {
                        position: absolute;
                        top: 0;
                        left: 0;
                        width: 290px;
                        height: 40px;
                        background: linear-gradient(top, #feb83c, #fccc52);
                        color: #fff;
                        text-align: center;
                        line-height: 40px;
                        border-radius: 20px;
                        font-size: 14px;
                    }
                }

                .actRule {
                    width: 290px;
                    background: #feec99;
                    color: #e89a0f;
                    border-radius: 10px;
                    padding: 15px 17px;
                    margin: 10px auto;
                    margin-top: 20px;

                    .ruleInfo {
                        text-align: center;
                        margin-bottom: 10px;
                    }

                    .ruleContent {
                        div {
                            margin-bottom: 10px;
                        }
                    }
                }
            }

            .mainContentRight {
                margin-left: 30px;
                width: 630px;

                .boxWrapper {
                    border: 1px solid #e5e5e5;
                    background: #f8f8f8;
                    padding: 0 12px 22px;
                    border-radius: 3px;
                    margin-bottom: 10px;

                    .numberTips {
                        width: 375px;
                        line-height: 20px;
                        color: #999;
                        margin-left: 75px;
                        margin-top: 5px;
                    }

                    .textDesc {
                        line-height: 40px;
                        border-bottom: 1px solid #e5e5e5;
                    }

                    .upInfo {
                        width: 350px;
                        border: 1px solid #c0c4cc;
                        background: #fff;
                        border-radius: 3px;
                        padding: 10px 12px;

                        .upIcons {
                            display: flex;
                            padding-bottom: 10px;
                            border-bottom: 1px solid #c0c4cc;
                            margin-bottom: 10px;

                            .leftContent {
                                width: 70px;
                                height: 70px;
                                border: 1px solid #c0c4cc;

                                img {
                                    width: 70px;
                                    height: 70px;
                                }
                            }

                            .rightContent {
                                height: 70px;
                                background: #fff;

                                .operate {
                                    display: flex;
                                    margin-left: 10px;
                                    height: 30px;
                                    line-height: 30px;

                                    :nth-of-type(1) {
                                        color: #5a8bff;
                                        cursor: pointer;
                                    }

                                    .operateBtn {
                                        display: block;
                                        height: 30px;
                                        padding: 0 10px;
                                        background: #f5f5f5;
                                        border: 1px solid #ccc;
                                        cursor: pointer;
                                    }

                                    :hover {
                                        color: #5a8bff;
                                        background-color: #fff;
                                        border-color: #5a8bff;
                                    }

                                    .fixstyle {
                                        margin: 0 13px;
                                    }
                                }

                                p {
                                    margin: 0 0 0 10px;
                                    bottom: 5px;
                                    color: #999;
                                }
                            }
                        }
                    }

                    .drawSetting {
                        color: #999;
                        padding-top: 10px;
                        line-height: 24px;
                    }

                    .tabs {
                        margin-top: 10px;
                    }

                    .levelBoxSetting {
                        width: 602px;
                        margin-top: 10px;
                        box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12),
                        0 0 6px 0 rgba(0, 0, 0, 0.04);

                        .levelUpInfo {
                            width: 602px;
                        }
                    }
                }
            }
        }

        .save {
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
    }
</style>>
