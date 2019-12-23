<template>
  <div class="membershipCardDetail">
    <div class="membershipCardDetailMain">
      <div class="leftContainer">
        <div class="left_Top"></div>
        <div class="left_middle">
          <div
            class="example_card"
            :style="`${bgStyleComputed}`"
          >
            <div class="card_detail">
              <img :src="$imageHost+'/image/admin/img_home/testImg.jpeg'">
            </div>
            <div
              v-if="ruleForm.dateRadio === '0'"
              class="effect_date"
            >
              {{ $t('memberCard.effectiveTime') }}：{{ruleForm.fixedDate | handleDate}}
            </div>
            <div
              v-if="ruleForm.dateRadio === '1'"
              class="effect_date"
            >
              {{ $t('memberCard.effectiveTime') }}：{{ $t('memberCard.fromTime') }}{{ruleForm.fromDateInput}}{{ruleForm.dateSelectvalue}}有效
            </div>
            <div
              v-if="ruleForm.dateRadio === '2'"
              class="effect_date"
            >
              {{ $t('memberCard.effectiveTime') }}：{{ $t('memberCard.ForeverEffective') }}
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
        <div class="rightContainerTop">
          <div class="rightTile">{{ $t('memberCard.basicSetting') }}</div>
          <el-form
            :model="ruleForm"
            status-icon
            :rules="rules"
            ref="ruleForm"
            label-width="100px"
            class="demo-ruleForm"
          >
            <el-form-item
              :label="$t('memberCard.memberCardName')"
              prop="name"
              class="userCardName first"
            >
              <el-input
                v-model="ruleForm.name"
                size="small"
              ></el-input>
            </el-form-item>
            <el-form-item
              :label="$t('memberCard.backImg')"
              class="userCardName"
            >
              <div class="backgroundDiv">
                <div class="bgTop">
                  <el-radio
                    v-model="ruleForm.bgFlag"
                    label="0"
                  >{{ $t('memberCard.bgColor') }}</el-radio>
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
                    label="1"
                  >{{ $t('memberCard.backImg') }}</el-radio>
                  <img
                    v-if="ruleForm.bgFlag==='1'"
                    :src="$imageHost+'/'+baImgUrl"
                    class="bgImgDiv"
                    @click="handleToAddImg()"
                    :style="`backgroundImage:url(${$imageHost}/image/admin/add_img.png);backgroundRepeat:no-repeat`"
                  />
                  <img
                    v-else
                    class="bgImgDiv"
                    @click="handleToAddImg()"
                    :style="`backgroundImage:url(${$imageHost}/image/admin/add_img.png);backgroundRepeat:no-repeat`"
                  />

                </div>
              </div>
            </el-form-item>
            <el-form-item
              :label="$t('memberCard.memberPower')"
              prop="discountInput"
              class="userCardName"
              v-if="cardType!==1"
            >
              <div class="discountDiv equity">
                <el-checkbox v-model="ruleForm.discount">{{ $t('memberCard.memberDiscount') }}</el-checkbox>
                <el-input
                  v-model="ruleForm.discountInput"
                  size="small"
                ></el-input>
                &nbsp;&nbsp;{{ $t('memberCard.discount') }}
              </div>
            </el-form-item>
            <el-form-item
              label=""
              class="userCardName"
              v-if="cardType!==1"
            >
              <div class="allGoods">
                <div style="margin-right:25px">{{ $t('memberCard.memberDiscountGoods') }}</div>
                <el-radio
                  v-model="ruleForm.allGoods"
                  label="1"
                >{{ $t('memberCard.allGoods') }}</el-radio>
                <el-radio
                  v-model="ruleForm.allGoods"
                  label="0"
                >{{ $t('memberCard.assignGoods') }}</el-radio>
              </div>
              <!--点击指定商品后显示模块-->
              <div
                class="noneBlock"
                v-if="ruleForm.allGoods==='0'"
              >
                <div
                  class="noneBlockList"
                  v-for="(item,index) in noneBlockDiscArr"
                  :key="index"
                  @click="hanldeToAddGoodS(index)"
                >
                  <div class="noneBlockLeft">
                    <img :src="$imageHost+'/image/admin/icon_jia.png'">
                    {{item.name}}
                  </div>
                  <div
                    v-if="item.num"
                    class="noneBlockRight"
                  >{{ $t('memberCard.alreadyChoosedCate') }}：{{item.num}}{{ $t('memberCard.unitCate') }}</div>
                </div>
              </div>
              <!--end-->
              <div>
                <div class="vipDiv">
                  <el-checkbox v-model="ruleForm.vipFlag">
                    <span style="margin-right:25px">{{ $t('memberCard.memberOwnGoods') }}</span>
                    <span>{{ $t('memberCard.buyPrompt') }}</span>
                  </el-checkbox>
                </div>
              </div>
              <!--点击会员专享后显示模块-->
              <div
                class="noneBlock"
                v-if="ruleForm.vipFlag"
              >
                <div
                  class="noneBlockList"
                  v-for="(item,index) in noneBlockVipArr"
                  :key="index"
                  @click="hanldeToAddGoodSUser(index)"
                >
                  <div class="noneBlockLeft">
                    <img :src="$imageHost+'/image/admin/icon_jia.png'">
                    {{item.name}}
                  </div>
                  <div
                    v-if="item.num"
                    class="noneBlockRight"
                  >{{ $t('memberCard.alreadyChoosedCate') }}：{{item.num}}{{ $t('memberCard.unitCate') }}</div>
                </div>
              </div>
              <!--end-->
              <div
                class="discountDiv equity"
                v-if="cardType!==1"
              >
                <el-form-item prop="integralInputOne">
                  <el-checkbox v-model="ruleForm.intGet">{{ $t('memberCard.getScore') }}&nbsp;&nbsp;{{$t('memberCard.openCardSend')}}</el-checkbox>
                  <el-input
                    v-model="ruleForm.integralInputOne"
                    size="small"
                  ></el-input>
                  &nbsp;&nbsp;{{ $t('memberCard.score') }}
                </el-form-item>
                <div style="margin-bottom: 22px;"></div>
              </div>
              <!--积分获取下方子模块-->
              <div style="margin-bottom: 22px;"></div>
              <div style="margin-bottom: 22px;"></div>
              <div
                class="shoppingFull"
                v-if="cardType!==1"
              >
                <div class="shoppingFullTop">
                  <el-radio
                    v-model="ruleForm.shoppingFull"
                    label="0"
                  >{{ $t('memberCard.shopFull') }}</el-radio>
                  <el-input
                    size="small"
                    v-model="ruleForm.shopingInputLeft"
                  ></el-input>&nbsp;&nbsp;{{ $t('memberCard.send') }}&nbsp;&nbsp;
                  <el-input
                    size="small"
                    v-model="ruleForm.shopingInputReft"
                  ></el-input>&nbsp;&nbsp;{{ $t('memberCard.score') }}&nbsp;&nbsp;<img
                    style="cursor:pointer"
                    :src="$imageHost +'/image/admin/sign_jia.png' "
                    @click="handleToAddIntegral()"
                  >
                </div>
                <div
                  v-for="(item,index) in ruleForm.addIntegralArr"
                  :key="index"
                >
                  <div
                    class="noneIntegralDiv"
                    v-if="cardType!==1"
                  >
                    <span>{{ $t('memberCard.shopFull') }}</span>
                    <el-input
                      size="small"
                      v-model="ruleForm.addIntegralArr[index].leftInput"
                    ></el-input>&nbsp;&nbsp; {{ $t('memberCard.send') }} &nbsp;&nbsp;
                    <el-input
                      size="small"
                      v-model="ruleForm.addIntegralArr[index].rightInput"
                    ></el-input>&nbsp;&nbsp;{{ $t('memberCard.score') }}&nbsp;&nbsp;<img
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="handleToDelIntegral(index)"
                    >
                  </div>
                </div>
                <div
                  class="shoppingFullBottom"
                  v-if="cardType!==1"
                >
                  <el-radio
                    v-model="ruleForm.shoppingFull"
                    label="1"
                  >{{ $t('memberCard.shopEachFull') }}</el-radio>
                  <el-input
                    size="small"
                    v-model="ruleForm.shopingInputLeftM"
                  ></el-input>&nbsp;&nbsp;送&nbsp;&nbsp;
                  <el-input
                    size="small"
                    v-model="ruleForm.shopingInputReftM"
                  ></el-input>&nbsp;&nbsp;{{ $t('memberCard.score') }}

                </div>
              </div>
              <div style="margin-bottom: 22px;"></div>
              <!--卡充值-->
              <div
                class="cardRecharge"
                v-if="cardType===0"
              >
                <el-form-item prop="cardRechargeInput">
                  <el-checkbox v-model="ruleForm.cardRechargeFlag">{{ $t('memberCard.powerCard') }}&nbsp;&nbsp;{{ $t('memberCard.openCardSend') }}</el-checkbox>
                  <el-input
                    v-model="ruleForm.cardRechargeInput"
                    size="small"
                  ></el-input>
                  &nbsp;{{ $t('memberCard.yuan') }}
                </el-form-item>
              </div>
              <div style="margin-bottom: 22px;"></div>
              <!--卡充值下方子模块-->
              <div
                class="shoppingFull"
                v-if="cardType===0"
              >
                <el-radio
                  v-model="ruleForm.rechargeInput"
                  label="2"
                >{{ $t('memberCard.justCharge') }}</el-radio>
              </div>
              <div
                class="shoppingFull"
                v-if="cardType===0"
              >
                <div class="shoppingFullTop">
                  <el-radio
                    v-model="ruleForm.rechargeInput"
                    label="0"
                  >{{ $t('memberCard.chargeFull') }}</el-radio>
                  <el-input
                    size="small"
                    v-model="ruleForm.rechargeInputLeft"
                  ></el-input>&nbsp;&nbsp;{{ $t('memberCard.send') }}&nbsp;&nbsp;
                  <el-input
                    size="small"
                    v-model="ruleForm.rechargeInputReft"
                  ></el-input>&nbsp;&nbsp;{{ $t('memberCard.yuan') }}&nbsp;&nbsp;<img
                    style="cursor:pointer"
                    :src="$imageHost +'/image/admin/sign_jia.png' "
                    @click="handleToAddRecharge()"
                  >
                </div>
                <div
                  v-for="(item,index) in ruleForm.addrechargeArr"
                  :key="index"
                >
                  <div
                    class="noneIntegralDiv"
                    v-if="cardType===0"
                  >
                    <span>{{ $t('memberCard.chargeFull') }}</span>
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
                </div>
                <div
                  class="shoppingFullBottom"
                  v-if="cardType===0"
                >
                  <el-radio
                    v-model="ruleForm.rechargeInput"
                    label="1"
                  >{{ $t('memberCard.chargeEachFull') }}</el-radio>
                  <el-input
                    size="small"
                    v-model="ruleForm.rechargeInputLeftM"
                  ></el-input>&nbsp;&nbsp;{{ $t('memberCard.send') }}&nbsp;&nbsp;
                  <el-input
                    size="small"
                    v-model="ruleForm.rechargeInputReftM"
                  ></el-input>&nbsp;&nbsp;{{ $t('memberCard.yuan') }}

                </div>
              </div>
              <!--end-->
              <!--开卡送卷-->
              <div
                class="sendingPaper"
                v-if="cardType===0"
              >
                <el-checkbox v-model="ruleForm.sendingPaperFlag">{{ $t('memberCard.openCardSendVolume') }}</el-checkbox>&nbsp;&nbsp;&nbsp;&nbsp;{{ $t('memberCard.volumeActiveInfo') }}
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
                  >{{ $t('memberCard.offerVolume') }}</el-radio>&nbsp;&nbsp;&nbsp;&nbsp;{{ $t('memberCard.offerInfo') }}
                  <div
                    class="couponList"
                    v-if="ruleForm.couponDiv==='1'"
                  >
                    <!--添加的优惠券列表-->
                    <div
                      class="couponLi"
                      v-for="(item,index) in couponList"
                      :key="index"
                    >
                      <img
                        @click="handlToDelCouList(index)"
                        v-if="item.ischeck"
                        :src="$imageHost +'/image/admin/sign_del.png'"
                      >
                      <div class="coupon_list_top">
                        {{item.actCode==='discount'?'':'¥'}}<span>{{item.denomination}}<i style="font-size:14px">{{item.actCode==='discount'?'折':''}}</i></span>
                      </div>
                      <div class="coupon_list_center">
                        <div class="coupon_center_limit">{{item.useConsumeRestrict>0?`${$t('memberCard.enough')} ${item.leastConsume}${$t('memberCard.usage')}`:`${$t('memberCard.unlimit')}`}}</div>
                        <div class="coupon_center_number">{{ $t('memberCard.remaining') }}<span>{{item.surplus}}</span>{{ $t('memberCard.unitZhang') }}</div>
                        <div
                          class="coupon_list_bottom"
                          :style="`backgroundImage:url('${$imageHost}/image/admin/coupon_border.png')`"
                        >{{ $t('memberCard.receive') }} </div>
                      </div>
                    </div>
                    <!--end-->
                    <div
                      class="card_add_clickDiv"
                      @click="handleToCallDialog()"
                      v-if="couponList.length<5"
                    >
                      <div class="card_add_click">
                        <img :src="$imageHost +'/image/admin/shop_beautify/add_decorete.png'">
                        <p>{{ $t('memberCard.addVolume') }}</p>
                      </div>
                    </div>
                  </div>

                </div>
                <div class="couponDivBottom">
                  <el-radio
                    v-model="ruleForm.couponDiv"
                    label="2"
                  >{{ $t('memberCard.sendCoupon') }}</el-radio>
                </div>
              </div>
            </el-form-item>

            <el-form-item
              :label="$t('memberCard.memberEffectiveTime')"
              prop="fixedDate"
              class="userCardName useDate"
              v-if="cardType!==2"
            >
              <div class="dateList">
                <el-radio
                  v-model="ruleForm.dateRadio"
                  label="0"
                >{{ $t('memberCard.fixData') }}</el-radio>
                <el-date-picker
                  v-model="ruleForm.fixedDate"
                  type="daterange"
                  :range-separator="$t('memberCard.to')"
                  :start-placeholder="$t('memberCard.startDate')"
                  :end-placeholder="$t('memberCard.overDate')"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :default-time="['00:00:00','23:59:59']"
                  size="large"
                >
                </el-date-picker>
              </div>
            </el-form-item>
            <div
              class="dateTips"
              v-if="cardType!==2"
            >{{ $t('memberCard.exampleInfo') }}</div>
            <el-form-item
              prop="fromDateInput"
              class="userCardName"
              v-if="cardType!==2"
            >
              <div class="dateList">
                <el-radio
                  v-model="ruleForm.dateRadio"
                  label="1"
                >{{ $t('memberCard.fromDate') }}</el-radio>
                <el-input
                  size="small"
                  v-model="ruleForm.fromDateInput"
                ></el-input>
                <el-select
                  v-model="ruleForm.dateSelectvalue"
                  :placeholder="$t('memberCard.pleaseChoose')"
                  size="small"
                >
                  <el-option
                    v-for="item in ruleForm.dateSelectOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
                {{ $t('memberCard.inEffective') }}
              </div>
            </el-form-item>
            <el-form-item
              class="userCardName"
              v-if="cardType!==2"
            >
              <div class="dateList">
                <el-radio
                  v-model="ruleForm.dateRadio"
                  label="2"
                >{{ $t('memberCard.ForeverEffective') }}</el-radio>
              </div>
            </el-form-item>
            <!--限次卡出现的使用商品模块-->
            <el-form-item
              label="适用商品"
              prop="useStore"
              class="userCardName"
              v-if="cardType===1"
            >
              <div class="useStoreDiv">
                <el-radio
                  v-model="limitCardRadio"
                  label="0"
                >不可兑换商品</el-radio>
                <el-radio
                  v-model="limitCardRadio"
                  label="1"
                >部分商品</el-radio>
                <el-radio
                  v-model="limitCardRadio"
                  label="2"
                >全部商品</el-radio>
              </div>
              <!--部分商品选中出现的模块-->
              <div v-if="cardType===1">
                <div v-if="limitCardRadio !=='0'">
                  <div class="applyDiv">
                    <span>允许兑换：</span>
                    <el-input
                      v-model="limitCardInput"
                      size="small"
                    ></el-input> &nbsp;&nbsp;次
                  </div>
                  <div>
                    <span>运费策略：</span>
                    <el-radio
                      v-model="strategyRadio"
                      label="0"
                    >免运费</el-radio>
                    <el-radio
                      v-model="strategyRadio"
                      label="1"
                    >使用商品运费策略</el-radio>
                  </div>
                  <div
                    class="noneBlockList"
                    v-if="limitCardRadio==='1'"
                  >
                    <div
                      class="noneBlockLeft"
                      @click="handleToCallGoodsDialog('limit')"
                    >
                      <img :src="$imageHost+'/image/admin/icon_jia.png'">
                      选择商品
                    </div>
                    <div
                      class="noneBlockRight"
                      v-if="suitebleGoods.length"
                    >已选择商品：{{suitebleGoods.length}}&nbsp;&nbsp;件</div>
                    <div style="margin-left: 20px;color: #999;height:30px;line-height:30px">最多可选择20件</div>
                  </div>
                </div>

              </div>
            </el-form-item>
            <!--限次卡出现的使用商品模块end-->
            <el-form-item
              :label="$t('memberCard.usingStore')"
              prop="useStore"
              class="userCardName"
              v-if="cardType!==2"
            >
              <div class="useStoreDiv">
                <el-radio
                  v-model="ruleForm.useStoreRadio"
                  label="0"
                >{{ $t('memberCard.allStores') }}</el-radio>
                <el-radio
                  v-model="ruleForm.useStoreRadio"
                  label="1"
                >{{ $t('memberCard.partStores') }}</el-radio>
                <el-radio
                  v-model="ruleForm.useStoreRadio"
                  label="-1"
                >{{ $t('memberCard.banStore') }}</el-radio>
              </div>
              <div class="useStoreTips">{{ $t('memberCard.storeConfig') }}</div>
              <!--点击部分门店时显示模块-->
              <div v-if="ruleForm.useStoreRadio==='1'">
                <div
                  v-if="chooseStore.length"
                  class="table"
                >
                  <el-table
                    class="version-manage-table"
                    header-row-class-name="tableClss"
                    :data="chooseStore"
                    border
                    style="width: 40%"
                  >
                    <el-table-column
                      prop="storeName"
                      align="center"
                      :label="$t('memberCard.storeName')"
                    >
                    </el-table-column>
                    <el-table-column
                      align="center"
                      :label="$t('memberCard.options')"
                    >
                      <template slot-scope="scope">
                        <span
                          @click="handleToStoreRowDel(scope)"
                          style="color:#5A8BFF;cursor:pointer"
                        >{{ $t('memberCard.delete') }}</span>

                      </template>
                    </el-table-column>

                  </el-table>
                </div>
                <div
                  class="add_brand"
                  style="display: inline-block;"
                  @click="handleToCallChioseStore()"
                >
                  <img :src="$imageHost+'/image/admin/icon_jia.png'">
                  {{ $t('memberCard.addStore') }}
                </div>

              </div>
              <div v-if="cardType===1 && ruleForm.useStoreRadio !== '-1'">
                <div class="useTimeDiv">
                  <span>允许使用时间:</span>
                  <el-checkbox-group
                    v-model="suitableGoodsUseTime"
                    @change="handleSuitableGoodsUseTime"
                  >
                    <el-checkbox label="工作日"></el-checkbox>
                    <el-checkbox label="双休日"></el-checkbox>
                  </el-checkbox-group>
                </div>
                <div class="partSuitableDiv">
                  <span>
                    允许使用
                  </span>
                  <el-input
                    v-model="limitCardAllowToUseTime"
                    size="small"
                  >
                  </el-input>&nbsp;&nbsp;次
                </div>

              </div>
            </el-form-item>
            <el-form-item
              :label="$t('memberCard.useNeedKnow')"
              class="userCardName"
            >
              <el-input
                type="textarea"
                :rows="2"
                v-model="ruleForm.textarea"
              >
              </el-input>
            </el-form-item>
            <el-form-item
              :label="$t('memberCard.contactPhone')"
              class="userCardName  phoneNum"
            >
              <el-input
                v-model="ruleForm.phoneNuminput"
                :placeholder="$t('memberCard.pleaseInput')"
                size="small"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>

        <div
          class="gradeDiv"
          v-if="cardType===2"
        >
          <div class="gradeDivTitle">升级设置</div>
          <div class="gradeSet">
            <div style="display:flex;justify-content: center">
              <div>
                <span class="must">*</span>
                升级条件：
              </div>

              <div>
                <p class="countPromptTwo">低等级用户满足升级条件会自动升级为高等级卡</p>
                <div class="scoreReceiveDiv">
                  <span>累积积分达到</span>
                  <el-input
                    v-model="ruleForm.gradeScore"
                    size="small"
                  ></el-input>
                  <span>分</span>
                </div>
                <div>
                  或
                </div>
                <div class="gradeConsumeDiv">
                  <span style="white-space:nowrap;margin-right:25px">累积消费总额达到</span>
                  <el-input
                    v-model="ruleForm.gradecrash"
                    size="small"
                  ></el-input>
                  <span style="color:#999;height:32px;line-height:32px;margin-left:5px">元</span>
                  <span style="white-space:nowrap;color:#999;height:32px;line-height:32px">仅包含微信、余额支付</span>
                </div>
              </div>
            </div>
            <div class="userCardBox">
              <div class="specialBox">
                <div style="margin-right:5px">
                  <span style="height:32px;line-height:32px;">*</span>会员卡等级:
                </div>
                <div>
                  <el-select
                    v-model="gradeValue"
                    placeholder="请选择等级"
                    size="small"
                  >
                    <el-option
                      v-for="(item,index) in gradeOptions"
                      :key="index"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                  <span
                    v-if="!gradeValue"
                    style="color: #F56C6C;font-size: 12px"
                  >请选择等级</span>
                  <p style="color:#999;margin-top:5px">数字越大等级越高，当会员满足相应条件时会自动发放对应等级的会员卡</p>
                </div>
              </div>

            </div>
          </div>

        </div>

        <!--底部部分-->
        <div class="rightContainerBottom">
          <div class="rightTile">{{ $t('memberCard.getSetting') }}</div>
          <div class="rightBottom">
            <el-form
              :model="ruleFormBottom"
              :rules="rulesBottom"
              ref="ruleForm b ottom"
              label-width="100px"
              class="demo-ruleForm"
            >

              <el-form-item
                :label="$t('memberCard.isNeedtoBuy')"
                class="userCardName  phoneNum"
                v-if="cardType!==2"
              >
                <el-radio
                  v-model="ruleFormBottom.isBuyRadio"
                  label="0"
                >{{ $t('memberCard.receiveDirect') }}</el-radio>
                <el-radio
                  v-model="ruleFormBottom.isBuyRadio"
                  label="1"
                >{{ $t('memberCard.needBuy') }}</el-radio>
                <el-radio
                  v-model="ruleFormBottom.isBuyRadio"
                  label="2"
                >{{ $t('memberCard.needReceiveCode') }}</el-radio>
                <div class="buyTableHidden">
                  <div
                    style="padding: 5px 0px;"
                    v-if="ruleFormBottom.isBuyRadio === '1'"
                  >
                    <div>
                      <el-radio
                        v-model="ruleFormBottom.cashRadio"
                        label="0"
                      >{{ $t('memberCard.crashBuy') }}</el-radio>
                      <el-input
                        v-model="ruleFormBottom.cashInput"
                        :placeholder="$t('memberCard.pleaseInput')"
                        size="small"
                      ></el-input>&nbsp;&nbsp;{{ $t('memberCard.yuan') }}
                    </div>

                    <div>
                      <el-radio
                        v-model="ruleFormBottom.cashRadio"
                        label="1"
                      >{{ $t('memberCard.scoreBuy') }}</el-radio>
                      <el-input
                        v-model="ruleFormBottom.integralInputTwo"
                        :placeholder="$t('memberCard.pleaseInput')"
                        size="small"
                      ></el-input>&nbsp;&nbsp;{{ $t('memberCard.unitM') }}
                    </div>
                  </div>
                  <div
                    class="receiveLimitNum"
                    v-if="cardType===1 && ruleFormBottom.isBuyRadio!=='2'"
                  >
                    <div
                      class="separateDiv"
                      v-if="ruleFormBottom.isBuyRadio === '1'"
                    >
                    </div>
                    <div>
                      <span>发放总数量:</span>
                      <el-input
                        v-model="stock"
                        size="small"
                      ></el-input>&nbsp;&nbsp;张
                      <span class="zeroDesc">填0为不限制</span>
                      <span
                        class="hasSend"
                        v-if="limitCardHasSend>0"
                      >当前已发放: {{limitCardHasSend}}张</span>
                    </div>
                    <div>
                      <span>领取限制: 每人限领</span>
                      <el-input
                        v-model="perSonLimit"
                        size="small"
                      >
                      </el-input>&nbsp;&nbsp;张
                      <span class="zeroDesc">填0为不限制</span>
                    </div>
                  </div>
                </div>

                <!--点击需要领取码出现模块-->
                <div
                  class="buyTableHidden"
                  v-if="ruleFormBottom.isBuyRadio === '2'"
                >
                  <div class="buyHiddenDiv">

                    <el-radio
                      v-model="ruleFormBottom.needGetRadio"
                      label="1"
                    >{{ $t('memberCard.receiveCodeSetting') }}</el-radio>
                    <div v-if="ruleFormBottom.needGetRadio=== '1'">
                      <div
                        v-for="(itemH,indexH) in codeAddDivArr"
                        :key="indexH"
                      >
                        <div>
                          <span>{{ $t('memberCard.batchOne') }}</span>
                          <span>{{ $t('memberCard.batchName') }}</span>
                          <el-input
                            v-model="codeAddDivArr[indexH].batchName"
                            size="small"
                          ></el-input>
                          <span
                            v-for="(item,index) in codeArr"
                            :key="index"
                            @click="handleCallCodeDialog(index,indexH)"
                          >{{item}}</span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="buyHiddenDiv">
                    <el-radio
                      v-model="ruleFormBottom.needGetRadio"
                      label="2"
                    >{{ $t('memberCard.cardPassAndNo') }}</el-radio>
                    <div v-if="ruleFormBottom.needGetRadio=== '2'">
                      <div
                        v-for="(itemH,indexH) in codeAddDivArrBottom"
                        :key="indexH"
                      >
                        <div>
                          <span>{{ $t('memberCard.batchOne') }}</span>
                          <span>{{ $t('memberCard.batchName') }}</span>
                          <el-input
                            v-model="ruleFormBottom.pcNameinput"
                            size="small"
                          ></el-input>
                          <span
                            v-for="(item,index) in codeArr"
                            :key="index"
                            @click="handleCallCodeDialogBottom(index,indexH)"
                          >{{item}}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div
                    class="receiveLimitNum"
                    v-if="cardType===1"
                  >
                    <div
                      class="separateDiv"
                      v-if="ruleFormBottom.isBuyRadio === '2'"
                    >
                    </div>
                    <div>
                      <span>发放总数量:</span>
                      <el-input
                        v-model="stock"
                        size="small"
                      ></el-input>&nbsp;&nbsp;张
                      <span class="zeroDesc">填0为不限制</span>
                      <span
                        class="hasSend"
                        v-if="limitCardHasSend>0"
                      >当前已发放: {{limitCardHasSend}}张</span>
                    </div>
                    <div>
                      <span>领取限制: 每人限领</span>
                      <el-input
                        v-model="perSonLimit"
                        size="small"
                      >
                      </el-input>&nbsp;&nbsp;张
                      <span class="zeroDesc">填0为不限制</span>
                    </div>
                  </div>
                </div>
              </el-form-item>
              <el-form-item
                :label="$t('memberCard.isActiveted')"
                class="userCardName  phoneNum"
              >
                <el-radio
                  v-model="ruleFormBottom.activationRadio"
                  label="0"
                >{{ $t('memberCard.no') }}</el-radio>
                <el-radio
                  v-model="ruleFormBottom.activationRadio"
                  label="1"
                >{{ $t('memberCard.yes') }}</el-radio>
                <div class="activationHidden">
                  <div style="color:#9D9D9D">{{ $t('memberCard.chooseUserInfo') }}</div>
                  <!--选择需要激活后显示模块-->
                  <div v-if="ruleFormBottom.activationRadio==='1'">
                    <div class="activationTop">
                      <el-checkbox-group v-model="ruleFormBottom.checkList">
                        <el-checkbox label="realName">{{ $t('memberCard.realName') }}</el-checkbox>
                        <el-checkbox label="mobile">{{ $t('memberCard.mobile') }}</el-checkbox>
                        <el-checkbox label="cid">{{ $t('memberCard.cid') }}</el-checkbox>
                        <el-checkbox label="sex">{{ $t('memberCard.sex') }}</el-checkbox>
                        <el-checkbox label="birthday">{{ $t('memberCard.birthday') }}</el-checkbox>
                        <el-checkbox label="maritalStatus">{{ $t('memberCard.maritalStatus') }}</el-checkbox>
                        <el-checkbox label="education">{{ $t('memberCard.educationInfo') }}</el-checkbox>
                        <el-checkbox label="industryInfo">{{ $t('memberCard.industry') }}</el-checkbox>
                        <el-checkbox label="address">{{ $t('memberCard.address') }}</el-checkbox>
                      </el-checkbox-group>
                    </div>
                    <div class="activationBottom">
                      <div style="color:#9D9D9D">{{ $t('memberCard.isactiveInfo') }}</div>
                      <el-radio
                        v-model="ruleFormBottom.examineRadio"
                        label="0"
                      >{{ $t('memberCard.noExmine') }}</el-radio>
                      <el-radio
                        v-model="ruleFormBottom.examineRadio"
                        label="1"
                      >{{ $t('memberCard.yesExmine') }}</el-radio>
                    </div>
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </div>

        </div>
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
      :tuneUp="tuneUp"
      @handleSelectImg='handleSelectImg'
    />
    <!--添加优惠券-->
    <AddCouponDialog
      :tuneUpCoupon="tuneUpCoupon"
      :couponBack="couponBack"
      @handleToCheck="handleToCheck"
    />
    <!--选择商品弹窗-->
    <ChoosingGoods />
    <ChoosingGoods
      @resultGoodsIds='getGoodsIdFromChoosingGoods'
      :tuneUpChooseGoods='controlChoosingGoodsDialog'
      :chooseGoodsBack='choosingGoodsDateTmpContainer'
    />
    <!--选择商家,平台分类弹窗-->
    <AddingBusClassDialog
      :dialogVisible.sync="businessDialogVisible"
      :classFlag="classFlag"
      :backDataArr="shopAndPlatformBackDataArr"
      @BusClassTrueArr="BusClassTrueArr"
    />

    <!--添加品牌弹窗-->
    <AddBrandDialog
      :callAddBrand.sync="callAddBrandDialogSignal"
      :brandBackData="chooseBrandDataTmpContainer"
      @handleToGetBackData="handleBrandResult"
    />
    <!--添加门店弹窗-->
    <ChioseStoreDialog
      :dialogVisible.sync="chooseStoreDialogFlag"
      :storeBackData="chooseStore"
      @getChoosedStore="dealWithChooseStore"
    />
    <!--领取码弹窗-->
    <ReceivingCodeDialog
      :dialogVisible.sync="receiveCodeDialogVisible"
      :batchName="currentBatchName"
      :batchId="currentBatchId"
      @generateReceiveCodeId="dealWithReceiveCodeId"
    />
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import { createMemberCardRequest, getCardDetailInfoRequest, updateCardRequest } from '@/api/admin/memberManage/memberCard.js'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    AddCouponDialog: () => import('@/components/admin/addCouponDialog'),
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('@/components/admin/addingBusClassDialog'),
    ChioseStoreDialog: () => import('./chioseStoreDialog'),
    ReceivingCodeDialog: () => import('./receivingCodeDialog'),
    AddBrandDialog: () => import('@/components/admin/addBrandDialog')
  },
  data () {
    var validName = (rule, value, callback) => {
      console.log(value)
      if (value === '') {
        callback(new Error('请输入会员卡名称'))
      }
      callback()
    }
    // 积分验证
    var validScore = (rule, value, callback) => {
      // 限次卡不检测
      if (this.cardType !== 1) {
        let reg = /^[+]?\d+$/
        let flag = reg.test(value)
        if (!flag) {
          callback(new Error('请输入大于等于0的整数'))
        }
      }
      callback()
    }
    var validCharge = (rule, value, callback) => {
      // 只检测普通卡
      if (this.cardType === 0) {
        const reg = /^[+]?\d+(\.\d+)?$/
        let flag = reg.test(value)
        if (!flag) {
          callback(new Error('请输入大于等于0的数值'))
        }
      }
      callback()
    }

    var validiscount = (rule, value, callback) => {
      // 不检测限次卡
      if (this.cardType !== 1) {
        console.log(rule, value, callback)
        let reg = /^\d{0,1}$/
        let flag = reg.test(Number(value))
        console.log(Number(value), flag)
        if (!this.ruleForm.discount) {
          callback()
        }
        if (value === '' || !reg.test(Number(value))) {
          callback(new Error('请输入0-10之间的数字'))
        } else {
          callback()
        }
      }
      callback()
    }
    var validatorDate = (rule, value, callback) => {
      // 检测固定日期

      console.log(value, typeof value)
      console.log(this.ruleForm.dateRadio)
      if (this.ruleForm.dateRadio !== '0') {
        callback()
      }
      if (value.length !== 2) {
        callback(new Error('请输入有效期'))
      } else {
        callback()
      }
    }
    var validatorDateInput = (rule, value, callback) => {
      // 检测自领取之日起

      console.log(value)
      console.log(this.ruleForm.dateRadio)
      if (this.ruleForm.dateRadio === '1') {
        if (value === '') {
          callback(new Error('请输入有效期'))
        }
      }
      callback()
    }
    return {
      limitCardHasSend: 0,
      stock: null,
      perSonLimit: null,
      suitableGoodsUseTime: ['工作日'],
      useTimeContent: [['工作日', '双休日'], ['工作日'], ['双休日']],
      useTime: 1,
      currentBatchName: null,
      currentBatchId: null,
      classFlag: null, // 区分商家分类和平台分类flag
      businessDialogVisible: false, // 商家分类和平台分类flag
      receiveCodeDialogVisible: false,
      cardType: null,
      colorLeft_: '',
      defaultColorleft: '#fff',
      leftNavData: [
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/discount.png',
          title: this.$t('memberCard.memberAd'),
          children: []
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/score_mem.png',
          title: this.$t('memberCard.memberDiscountD'),
          children: [this.$t('memberCard.detailSend')]
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/charge_icon.png',
          title: this.$t('memberCard.chargeRule'),
          children: [this.$t('memberCard.justCharge')]
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/article.png',
          title: this.$t('memberCard.cardUsage'),
          children: []
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/store_icon.png',
          title: this.$t('memberCard.usingStore'),
          children: [this.$t('memberCard.allStores')]
        }
      ],
      ruleForm: {
        name: '',
        bgFlag: 0,
        discount: true,
        discountInput: '',
        allGoods: '1',
        integralInputOne: '', // 开卡赠送积分
        vipFlag: false,
        shoppingFull: '0',
        intGet: true,
        shopingInputLeft: '100',
        shopingInputReft: '100',
        shopingInputLeftM: '100',
        shopingInputReftM: '100',
        addIntegralArr: [], // 购物每满
        goodsMoney: [],
        getScores: [],
        cardRechargeFlag: true,
        cardRechargeInput: '',
        rechargeInput: '2',
        rechargeInputLeft: '100',
        rechargeInputReft: '100',
        rechargeInputLeftM: '100',
        rechargeInputReftM: '100',
        addrechargeArr: [], // 充值每满
        money: [],
        getMoney: [],
        sendingPaperFlag: false,
        couponDiv: '1',
        termValidity: '',
        dateRadio: '0',
        fixedDate: [],
        fromDateInput: '',
        dateSelectvalue: '1',
        dateSelectOptions: [{
          value: '0',
          label: '日'
        }, {
          value: '1',
          label: '周'
        }, {
          value: '2',
          label: '月'
        }],
        useStoreRadio: '0',
        useNotice: '',
        textarea: '',
        phoneNuminput: '',
        gradeScore: '',
        gradecrash: ''
      },
      rules: {
        name: [{ validator: validName, required: true, trigger: 'blur' }],
        discountInput: [{ validator: validiscount, required: true, trigger: 'blur' }],
        integralInputOne: [{ validator: validScore, trigger: 'blur' }],
        cardRechargeInput: [{ validator: validCharge, trigger: 'blur' }],
        fixedDate: [{ validator: validatorDate, required: true, trigger: 'blur' }],
        fromDateInput: [{ validator: validatorDateInput, trigger: 'blur' }]
      },
      ruleFormBottom: {
        checkList: [],
        phoneNuminput: '',
        isBuyRadio: '0',
        activationRadio: '0',
        cashRadio: '0',
        cashInput: '',
        integralRadio: '',
        integralInputTwo: '',
        needGetRadio: '1',
        pcNameinput: '',
        activation: {
          nameCheck: false,
          phoneCheck: false,
          idheck: false,
          faxCheck: false,
          birthDayCheck: false,
          marryCheck: false,
          eduCheck: false,
          jobCheck: false,
          adressCheck: false
        },
        examineRadio: '0'
      },
      rulesBottom: {

      },
      baImgUrl: null,
      tuneUpCoupon: false,
      noneBlockDiscArr: [
        {
          name: '添加商品',
          num: ''
        },
        {
          name: '添加商家分类',
          num: ''
        },
        {
          name: '添加平台分类',
          num: ''
        },
        {
          name: '添加品牌',
          num: ''
        }
      ],
      noneBlockVipArr: [
        {
          name: '添加商品',
          num: ''
        },
        {
          name: '添加商品分类',
          num: ''
        },
        {
          name: '添加平台分类',
          num: ''
        },
        {
          name: '添加品牌',
          num: ''
        }
      ],
      couponDialogFlag: false,
      couponList: [],
      couponIds: null,
      clickArr: [],
      treeType: null,
      AtreeType: null,
      codeArr: [],
      codeAddDivArr: [{ batchName: null, batchId: null }],
      receiveCodeTmpIndex: 0,
      codeAddDivArrBottom: ['null'],
      chioseSureData: [],
      chooseStore: [],
      chooseStoreDialogFlag: false,
      userDialogFlag: null,
      addBrandDialogDataFlag1: '', // 指定商品-添加品牌弹窗选中数据
      shopAndPlatformBackDataArr: null, // 商家，平台弹窗的数据传输载体
      shopCategoryIds: null, // 指定商品-商家分类
      platformCategoryIds: null, // 指定商品-平台分类
      addBrandDialogDataFlag2: '', // 会员专享-添加品牌弹窗选中数据
      choosingGoodsDateFlag1: [], // 指定商品-选择商品选中数据,
      choosingGoodsDateTmpContainer: null,
      chooseBrandDataTmpContainer: [], // 品牌临时容器
      ownGoodsId: null, // 会员专享商品: 添加的商品Id
      ownStoreCategoryIds: null, // 会员专享商品:添加的商家分类Id
      ownPlatFormCategoryIds: null, // 会员专享商品:平台分类id
      ownBrandId: null, // 会员专享商品:品牌分类id
      controlChoosingGoodsDialog: false, // 商品弹窗显示标记
      callAddBrandDialogSignal: false, // 品牌弹窗显示信号标记
      choosingGoodsDateFlag2: '', // 会员专享-选择商品选中数据
      tuneUp: false,
      limitFlag: false,
      couponBack: '',
      // 限次卡出现的隐藏模块调试字段
      limitCardRadio: '0', // 试用商品radio
      limitCardInput: '', // 允许兑换input
      strategyRadio: '0', // 运费策略radio
      suitebleGoods: [],
      limitCardAllowToUseTime: '',
      currentFlag: '',
      chioseGoodsList: [
        { goodsId: 1 }
      ],
      gradeOptions: [
        { label: 'v1', value: 'v1' },
        { label: 'v2', value: 'v2' },
        { label: 'v3', value: 'v3' },
        { label: 'v4', value: 'v4' },
        { label: 'v5', value: 'v5' },
        { label: 'v6', value: 'v6' },
        { label: 'v7', value: 'v7' },
        { label: 'v8', value: 'v8' },
        { label: 'v9', value: 'v9' }
      ],
      gradeValue: null
    }
  },
  filters: {
    handleDate (value) {
      console.log(value)
      if (value) {
        return `${value[0]}-${value[1]}`
      } else {
        return '-'
      }
    }
  },
  computed: {
    // 动态变化背景色或背景图片
    bgStyleComputed () {
      let bg = ''
      if (this.ruleForm.bgFlag === '0') {
        bg = `background-color:${this.colorLeft_}`
      } else {
        bg = `backgroundImage:url(${this.$imageHost}/${this.baImgUrl})`
        bg = `backgroundImage:url(${this.$imageHost}/${this.baImgUrl});backgroundRepeat:no-repeat;background-size: 100% 100%;`
      }
      console.log(this.$imageHost)
      console.log(this.ruleForm.bgFlag, bg)

      return bg
    }
  },
  watch: {
    lang () {
      this.codeArr = this.$t('memberCard.codeArr')
      console.log(this.codeArr)
    },
    'ruleForm.dateRadio' (newData) {
      console.log(newData)
      this.$refs['ruleForm'].validate((valid) => {
        console.log(valid)
        if (valid) {
          // this.$refs['ruleForm'].resetFields()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    'ruleForm.vipFlag' (newData) {
      if (newData) {
        let obj = {
          backGroundImgUrl: this.$imageHost + '/image/wxapp/card_info_goods.png',
          title: '会员专享商品',
          children: []
        }
        this.leftNavData.splice(2, 0, obj)
      } else {
        this.leftNavData.splice(2, 1)
      }
    },
    'ruleForm.shoppingFull' (newData) {
      if (newData === '1') {
        this.leftNavData[1].children = ['购物满100送100积分']
      } else {
        this.leftNavData[1].children = ['购物每满100送100积分']
      }
    },
    'ruleForm.rechargeInput' (newData) {
      let fn = (text) => {
        this.leftNavData.forEach(item => {
          if (item.title === '卡充值规则') {
            item.children = [text]
          }
        })
      }

      switch (newData) {
        case '1':
          fn('卡充值')
          break
        case '2':
          fn('充值满100送100')
          break
        case '3':
          fn('充值每满100送100')
      }
    },
    'ruleForm.useStoreRadio' (newData) {
      let fn = (text) => {
        this.leftNavData.forEach(item => {
          if (item.title === '使用门店') {
            item.children = [text]
          }
        })
      }
      switch (newData) {
        case '1':
          fn('全部门店')
          break
        case '2':
          if (!this.chioseSureData.length) {
            fn('')
          }
          break
        case '3':
          fn('不可在门店使用')
      }
    },
    chioseSureData (newData) {
      console.log(newData)
      let str = ''
      newData.forEach(item => {
        if (!str) {
          str = item.storeName
        } else {
          str = str + ',' + item.storeName
        }
      })
      this.leftNavData.forEach(item => {
        if (item.title === '使用门店') {
          item.children = [str]
        }
      })
      console.log(str)
    }
  },
  mounted () {
    console.log(this.$route)
    // 初始化接受数据
    this.dataDefalut()
    this.langDefault()
    if (this.cardId) {
      // 单张会员卡信息
      this.getCardDetailInfoById(this.cardId)
    }
  },
  methods: {

    // 1- 准备数据
    prepareCardData () {
      this.dealWithDynamicArrayData()
      let obj = {
        'id': this.cardId,
        'cardType': this.cardType,
        'cardName': this.ruleForm.name,
        'bgType': this.ruleForm.bgFlag,
        'bgColor': this.colorLeft_,
        'bgImg': this.baImgUrl,
        'powerCount': this.ruleForm.discount ? 1 : 0,
        'powerCard': this.ruleForm.cardRechargeFlag ? 1 : 0,
        'powerCardJson': {
          'offsetMoney': this.ruleForm.rechargeInput,
          'money': this.money,
          'getMoney': this.getMoney,
          'perMoney': this.ruleForm.rechargeInputLeftM,
          'perGetMoney': this.ruleForm.rechargeInputReftM
        },
        'disCount': this.ruleForm.discountInput,
        'discountIsAll': this.ruleForm.allGoods,
        'powerPayOwnGood': this.ruleForm.vipFlag ? 'on' : '',
        'goodsId': this.choosingGoodsDateFlag1,
        'shopCategoryIds': this.shopCategoryIds,
        'platformCategoryIds': this.platformCategoryIds,
        'discountBrandId': this.chioseSureData,
        'ownGoodsId': this.ownGoodsId,
        'ownStoreCategoryIds': this.ownStoreCategoryIds,
        'ownPlatFormCategoryIds': this.ownPlatFormCategoryIds,
        'ownBrandId': this.ownBrandId,
        'powerScore': this.ruleForm.intGet ? 1 : 0,
        'score': this.ruleForm.integralInputOne,
        'scoreJson': {
          'offset': this.ruleForm.shoppingFull,
          'goodsMoney': this.goodsMoney,
          'getScores': this.getScores,
          'perGoodsMoney': this.ruleForm.shopingInputLeftM,
          'perGetScores': this.ruleForm.shopingInputReftM
        },
        'sendCoupon': this.ruleForm.sendingPaperFlag ? 'on' : '',
        'couponType': Number(this.ruleForm.couponDiv) - 1,
        'couponIds': this.couponIds,
        'couponPackage': null,
        'sendMoney': this.ruleForm.cardRechargeInput,
        'expiredType': this.ruleForm.dateRadio,
        'startTime': this.ruleForm.fixedDate ? this.ruleForm.fixedDate[0] : null,
        'endTime': this.ruleForm.fixedDate ? this.ruleForm.fixedDate[1] : null,
        'receiveDay': this.ruleForm.fromDateInput,
        'dateType': this.ruleForm.dateSelectvalue,
        'isExchange': this.limitCardRadio,
        'exchangCount': this.limitCardInput,
        'exchangFreight': this.strategyRadio,
        'exchangGoods': this.suitebleGoods,
        'useTime': this.useTime,
        'count': this.limitCardAllowToUseTime,
        'storeListType': this.ruleForm.useStoreRadio,
        'storeList': this.chooseStore.map(({ storeId }) => storeId),
        'desc': this.ruleForm.textarea,
        'mobile': this.ruleForm.phoneNuminput,
        'stock': this.stock,
        'limits': this.perSonLimit,
        'isPay': this.ruleFormBottom.isBuyRadio,
        'payType': this.ruleFormBottom.cashRadio,
        'payMoney': this.ruleFormBottom.cashInput,
        'payScore': this.ruleFormBottom.integralInputTwo,
        'batchIdList': this.codeAddDivArr.map(({ batchId }) => batchId),
        'receiveAction': this.ruleFormBottom.needGetRadio,
        'activation': this.ruleFormBottom.activationRadio,
        'activationCfgBox': this.ruleFormBottom.checkList,
        'examine': this.ruleFormBottom.examineRadio,
        'gradeConditionJson': { gradeScore: this.ruleForm.gradeScore, gradeMoney: this.ruleForm.gradecrash },
        'grade': this.gradeValue
      }
      console.log(obj)
      if (this.cardId) {
        // 更新会员卡
        console.log('更新会员卡')

        this.updateCardInfo(obj)
      } else {
        // 创建会员卡
        console.log('创建会员卡')
        this.createMemberCard(obj)
      }
    },

    // 2- 创建会员卡接口
    createMemberCard (data) {
      createMemberCardRequest(data).then(res => {
        console.log(res)
        if (res.error === 0) {
          // success
          // 清除数据，并进行跳转
          this.successOptions()
        }
      })
    },
    // 3- 点击保存
    handleToSave (formName) {
      this.$refs['ruleForm'].validate((valid) => {
        console.log(valid)
        if (valid) {
          console.log(this.cardType)
          if (Number(this.cardType) === 2) {
            console.log(this.gradeValue)
            if (this.gradeValue) {
              this.prepareCardData()
              this.$refs['ruleForm'].resetFields()
            }
          } else {
            this.prepareCardData()
            this.$refs['ruleForm'].resetFields()
          }
        } else {
          this.$message.error('填写错误')
        }
      })
    },

    // 4- 清空数据
    clearInputData () {
      this.colorLeft_ = ''
      this.ruleForm.textarea = ''
      this.ruleForm.phoneNuminput = ''
      this.money = []
      this.getMoney = []
      this.goodsMoney = []
      this.getScores = []
      this.ruleFormBottom.checkList = []
    },
    // 5- 根据会员卡类型获取相应url
    getCardPageUrl () {
      console.log(this.cardType, typeof this.cardType)
      switch (Number(this.cardType)) {
        case 0:
          return 'user_card'
        case 1:
          return 'limitTimes'
        case 2:
          return 'GradeCard'
        default:
          break
      }
    },
    // 6- 获取单张会员卡详细信息
    getCardDetailInfoById (id) {
      let obj = {
        id
      }
      getCardDetailInfoRequest(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          // success
          // bind data from backend to frontend
          this.bindBackAndFrontEndData(res.content)
        }
      })
    },

    // 7- 绑定数据
    bindBackAndFrontEndData (data) {
      console.log(data)
      this.ruleForm.name = data.cardName
      this.ruleForm.bgFlag = String(data.bgType)
      console.log('Hello World', this.ruleForm.bgFlag)
      this.colorLeft_ = data.bgColor
      this.baImgUrl = data.bgImg
      this.ruleForm.discount = data.powerCount === 1
      this.ruleForm.discountInput = data.disCount
      if (this.isValidValue(data.discountIsAll)) {
        this.ruleForm.allGoods = String(data.discountIsAll)
      }
      console.log(this.ruleForm.allGoods)
      this.ruleForm.vipFlag = data.powerPayOwnGood === 'on'
      // 指定商品 - 商品id
      if (this.isValidValue(data.goodsId)) {
        this.choosingGoodsDateFlag1 = data.goodsId
        this.noneBlockDiscArr[0].num = data.goodsId.length
      }

      // 指定商品 - 商家id
      if (this.isValidValue(data.shopCategoryIds)) {
        this.shopCategoryIds = data.shopCategoryIds
        this.noneBlockDiscArr[1].num = data.shopCategoryIds.length
      }

      // 指定商品 - 平台id
      if (this.isValidValue(data.platformCategoryIds)) {
        this.platformCategoryIds = data.platformCategoryIds
        this.noneBlockDiscArr[2].num = data.platformCategoryIds.length
      }

      // 指定品牌 - 品牌id
      if (this.isValidValue(data.brandId)) {
        this.chioseSureData = data.brandId.map(item => Number(item))
        this.noneBlockDiscArr[3].num = data.brandId.length
      }

      this.ownGoodsId = data.ownGoodsId ? data.ownGoodsId : []
      this.noneBlockVipArr[0].num = data.ownGoodsId ? data.ownGoodsId.length : 0

      this.ownStoreCategoryIds = data.ownStoreCategoryIds ? data.ownStoreCategoryIds : []
      this.noneBlockVipArr[1].num = data.ownStoreCategoryIds ? data.ownStoreCategoryIds.length : 0

      this.ownPlatFormCategoryIds = data.ownPlatFormCategoryIds ? data.ownPlatFormCategoryIds : []
      this.noneBlockVipArr[2].num = data.ownPlatFormCategoryIds ? data.ownPlatFormCategoryIds.length : 0

      this.ownBrandId = data.ownBrandId ? data.ownBrandId : []
      this.noneBlockVipArr[3].num = data.ownBrandId ? data.ownBrandId.length : 0

      this.chooseStore = []
      if (data.storeList) {
        this.chooseStore = data.storeList.map(({ value, label }) => {
          return { storeId: value, storeName: label }
        })
      }
      console.log(this.chooseStore)
      this.ruleForm.intGet = data.powerScore ? data.powerScore === 1 : true
      this.ruleForm.integralInputOne = data.score

      this.ruleForm.shoppingFull = data.scoreJson ? String(data.scoreJson.offset) : '0'
      this.goodsMoney = data.scoreJson ? data.scoreJson.goodsMoney : []
      this.getScores = data.scoreJson ? data.scoreJson.getScores : []

      this.ruleForm.shopingInputLeftM = data.perGoodsMoney
      this.ruleForm.shopingInputReftM = data.perGetScores
      this.ruleForm.cardRechargeFlag = data.powerCard ? data.powerCard === 1 : true
      this.ruleForm.cardRechargeInput = data.sendMoney
      this.ruleForm.rechargeInput = data.powerCardJson ? String(data.powerCardJson.offsetMoney) : '2'
      this.money = data.powerCardJson ? data.powerCardJson.money : this.money
      this.getMoney = data.powerCardJson ? data.powerCardJson.getMoney : this.getMoney
      this.ruleForm.rechargeInputLeftM = data.powerCardJson ? data.powerCardJson.perMoney : this.ruleForm.rechargeInputLeftM
      this.ruleForm.rechargeInputReftM = data.powerCardJson ? data.powerCardJson.perGetMoney : this.ruleForm.rechargeInputReftM
      this.ruleForm.dateRadio = data.expireType ? String(data.expireType) : this.ruleForm.dateRadio

      this.ruleForm.fixedDate = [data.startTime, data.endTime]
      console.log(this.ruleForm.fixedDate)
      this.ruleForm.fromDateInput = data.receiveDay
      this.ruleForm.dateSelectvalue = data.dateType ? String(data.dateType) : this.ruleForm.dateSelectvalue
      this.ruleForm.useStoreRadio = data.storeListType ? String(data.storeListType) : this.ruleForm.useStoreRadio
      this.ruleForm.textarea = data.desc
      this.ruleForm.phoneNuminput = data.mobile
      this.ruleFormBottom.isBuyRadio = String(data.isPay)
      if (this.isValidValue(data.payType)) {
        this.ruleFormBottom.cashRadio = String(data.payType)
      }
      this.ruleFormBottom.cashInput = data.payMoney ? data.payMoney : ''
      this.ruleFormBottom.integralInputTwo = data.payScore
      this.ruleFormBottom.activationRadio = String(data.activation)
      this.ruleFormBottom.examineRadio = String(data.examine)
      this.ruleFormBottom.checkList = data.activationCfgBox ? data.activationCfgBox : []
      // 优惠券
      this.ruleForm.sendingPaperFlag = data.sendCouponSwitch === 1
      this.ruleForm.couponDiv = String(data.sendCouponType + 1)
      this.couponList = data.couponList ? data.couponList : this.couponList

      console.log(this.codeAddDivArr)

      // 适用商品

      this.limitCardRadio = data.isExchange ? String(data.isExchange) : this.limitCardRadio
      this.limitCardInput = data.exchangCount ? data.exchangCount : this.limitCardInput
      this.strategyRadio = data.exchangFreight ? String(data.exchangFreight) : this.strategyRadio
      this.suitebleGoods = data.exchangGoods ? data.exchangGoods : this.suitebleGoods

      this.useTime = data.useTime !== null ? data.useTime : this.useTime
      this.suitableGoodsUseTime = this.useTimeContent[this.useTime]
      this.limitCardAllowToUseTime = data.count ? data.count : this.limitCardAllowToUseTime

      // 领取数量
      this.stock = data.stock ? data.stock : this.stock
      this.perSonLimit = data.limit ? data.limit : this.perSonLimit
      this.limitCardHasSend = data.hasSend ? data.hasSend : this.limitCardHasSend
      // 领取码
      if (data.batchList) {
        if (data.batchList.length > 0) {
          this.codeAddDivArr = []
          data.batchList.forEach(item => {
            this.codeAddDivArr.push({ batchName: item.name, batchId: item.batchId })
          })
        }
      } else {
        this.codeAddDivArr = [{ batchName: null, batchId: null }]
      }
      console.log(this.codeAddDivArr)
      this.ruleFormBottom.needGetRadio = data.receiveAction === 0 ? '1' : String(data.receiveAction)
      // 等级信息
      if (data.gradeConditionJson) {
        this.ruleForm.gradeScore = data.gradeConditionJson.gradeScore ? data.gradeConditionJson.gradeScore : this.ruleForm.gradeScore
        this.ruleForm.gradecrash = data.gradeConditionJson.gradeMoney ? data.gradeConditionJson.gradeMoney : this.ruleForm.gradecrash
      }
      this.gradeValue = data.grade ? data.grade : this.gradeValue
      // 处理json数据
      this.dealWithDataFromBackEnd()
    },
    // 8- 处理动态数据
    dealWithDynamicArrayData () {
      // 积分

      console.log(this.goodsMoney, this.getScores)

      if (this.ruleForm.shoppingFull === '0') {
        this.goodsMoney = []
        this.getScores = []
        this.goodsMoney.push(this.ruleForm.shopingInputLeft)
        this.getScores.push(this.ruleForm.shopingInputReft)
        for (let item of this.ruleForm.addIntegralArr) {
          this.goodsMoney.push(item.leftInput)
          this.getScores.push(item.rightInput)
        }
      }

      console.log(this.goodsMoney, this.getScores)
      // 充值
      if (this.ruleForm.rechargeInput === '0') {
        this.money = []
        this.getMoney = []
        this.money.push(this.ruleForm.rechargeInputLeft)
        this.getMoney.push(this.ruleForm.rechargeInputReft)
        for (let item of this.ruleForm.addrechargeArr) {
          this.money.push(item.leftInput)
          this.getMoney.push(item.rightInput)
        }
      }
      console.log(this.money, this.getMoney)
      // 优惠券

      console.log(this.couponList)
      this.couponIds = this.couponList.map(({ id }) => id)
    },
    // 8-1 处理从后端获取的数据
    dealWithDataFromBackEnd () {
      // 积分
      if (this.isValidValue(this.goodsMoney) && this.isValidValue(this.getScores)) {
        if (this.goodsMoney.length > 0 && this.getScores.length > 0) {
          this.ruleForm.shopingInputLeft = this.goodsMoney.splice(0, 1)[0]
          this.ruleForm.shopingInputReft = this.getScores.splice(0, 1)[0]
          for (let index in this.goodsMoney) {
            let obj = {
              leftInput: this.goodsMoney[index],
              rightInput: this.getScores[index]
            }
            this.ruleForm.addIntegralArr.push(obj)
          }

          // 清空积分容器
          [this.goodsMoney, this.getScores] = [[], []]
        }
      }

      // 卡充值
      if (this.isValidValue(this.money) && this.isValidValue(this.getMoney)) {
        if (this.money.length > 0 && this.getMoney.length > 0) {
          this.ruleForm.rechargeInputLeft = this.money.splice(0, 1)[0]
          this.ruleForm.rechargeInputReft = this.getMoney.splice(0, 1)[0]
          for (let index in this.money) {
            this.ruleForm.addrechargeArr.push({
              leftInput: this.money[index],
              rightInput: this.getMoney[index]
            })
          }
          // 清空充值容器
          [this.money, this.getMoney] = [[], []]
        }
      }
    },
    // 9- 更新会员卡信息
    updateCardInfo (data) {
      updateCardRequest(data).then(res => {
        console.log(res)
        if (res.error === 0) {
          // success
          this.successOptions()
        }
      })
    },
    // 10- 操作成功后的提示，清除数据，路由跳转
    successOptions () {
      this.$message.success(this.$t('memberCard.cardCreatedSuccess'))
      this.clearInputData()
      let urlCard = this.getCardPageUrl()
      console.log(urlCard)
      // 页面跳转
      this.$router.push({
        name: urlCard
      })
    },
    // 11- 获取会员权益选择商品弹窗的商品id
    getGoodsIdFromChoosingGoods (data) {
      console.log(data)
      if (this.currentFlag === 'limit') {
        this.currentFlag = ''
        this.suitebleGoods = data
      } else {
        if (this.userDialogFlag === '1') {
          // 折扣商品
          this.choosingGoodsDateFlag1 = data
          this.noneBlockDiscArr[0].num = data.length
        } else {
          // 专享商品
          this.ownGoodsId = data
          this.noneBlockVipArr[0].num = data.length
        }
      }
    },
    // 12- 接收四个弹窗的信息
    dataDefalut () {
      console.log(this.$route.query)
      this.cardType = Number(this.$route.query.cardType)
      this.cardId = this.$route.query.cardId
    },
    // 动态添加样式
    getStyle (item) {
      console.log(item)
      return 'backgroundImage:url(' + item.backGroundImgUrl + ')'
    },

    // 颜色选择器选中
    headleChangeColorLeft () {
      console.log(this.colorLeft_)
    },
    // 添加图片
    handleToAddImg () {
      this.tuneUp = !this.tuneUp
      this.$http.$emit('dtVisible')
    },
    // 图片选中
    handleSelectImg (res) {
      console.log(res)
      this.baImgUrl = res.imgPath
      console.log(res.imgPath)
    },
    // 增加购物满积分模块
    handleToAddIntegral () {
      // addIntegralArr
      let obj = {
        leftInput: '',
        rightInput: ''
      }
      this.ruleForm.addIntegralArr.push(obj)
      console.log(this.ruleForm.addIntegralArr)
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
      console.log(this.ruleForm.addrechargeArr)
    },
    // 删除充值满模块
    handleToDelRecharge (index) {
      this.ruleForm.addrechargeArr.splice(index, 1)
    },
    // 调起添加优惠券弹窗
    handleToCallDialog () {
      let arr = [41, 40]
      this.tuneUpCoupon = !this.tuneUpCoupon

      this.couponBack = arr
      // this.$http.$emit('V-AddCoupon', obj)
    },
    // 添加优惠券弹窗回传
    handleToCheck (data) {
      console.log(data)
      this.couponList = data
    },
    // 删除优惠券项
    handlToDelCouList (index) {
      this.couponList.splice(index, 1)
    },
    // 点击指定商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      console.log('指定商品')
      this.userDialogFlag = '1'
      console.log(index)
      switch (index) {
        case 0:
          // 商品弹窗显示
          this.controlChoosingGoodsDialog = !this.controlChoosingGoodsDialog
          this.choosingGoodsDateTmpContainer = this.choosingGoodsDateFlag1
          break
        case 1:
          this.AtreeType = 1
          console.log('商家分类')
          this.businessDialogVisible = true
          this.classFlag = 1
          this.shopAndPlatformBackDataArr = this.shopCategoryIds

          break
        case 2:
          this.AtreeType = 2
          console.log('平台分类')
          this.businessDialogVisible = true
          this.classFlag = 2
          this.shopAndPlatformBackDataArr = this.platformCategoryIds
          break
        case 3:

          console.log(this.chioseSureData)
          this.callAddBrandDialogSignal = true
          this.chooseBrandDataTmpContainer = this.chioseSureData
          console.log(this.chooseBrandDataTmpContainer)
      }
    },
    // 点击会员专享商品出现的添加类弹窗汇总
    hanldeToAddGoodSUser (index) {
      console.log('会员专享')
      this.userDialogFlag = '2'
      console.log(index)
      switch (index) {
        case 0:
          // 商品弹窗显示
          this.controlChoosingGoodsDialog = !this.controlChoosingGoodsDialog
          this.choosingGoodsDateTmpContainer = this.ownGoodsId
          break
        case 1:
          this.AtreeType = 1
          this.businessDialogVisible = true
          this.classFlag = 1
          this.shopAndPlatformBackDataArr = this.ownStoreCategoryIds
          break
        case 2:
          this.AtreeType = 2
          this.businessDialogVisible = true
          this.classFlag = 2
          this.shopAndPlatformBackDataArr = this.ownPlatFormCategoryIds
          break
        case 3:

          console.log(this.ownBrandId)
          this.callAddBrandDialogSignal = true
          this.chooseBrandDataTmpContainer = this.ownBrandId
          console.log(this.chooseBrandDataTmpContainer)
      }
      console.log(index)
    },
    // 调起添加门店弹窗
    handleToCallChioseStore () {
      this.chooseStoreDialogFlag = true
      // this.$http.$emit('CallChioseStore')
    },
    // 调起领取码弹窗
    handleCallCodeDialog (index, indexH) {
      switch (index) {
        case 0:

          console.log(this.codeAddDivArr[indexH].batchName)
          if (!this.codeAddDivArr[indexH].batchName) {
            this.$message.error('请填写批次名称')
            break
          }
          this.receiveCodeDialogVisible = true
          this.receiveCodeTmpIndex = indexH
          this.currentBatchName = this.codeAddDivArr[indexH].batchName
          this.currentBatchId = this.codeAddDivArr[indexH].batchId
          break
        case 1:
          this.codeAddDivArr.push({ batchName: null, batchId: null })
          break
        case 2:
          if (this.codeAddDivArr.length === 1) {
            this.$message.error('最少保留一个批次')
          } else {
            this.codeAddDivArr.splice(indexH, 1)
          }
          break
        case 3:
          this.$message.error('暂无记录')
      }
    },
    // 卡号类点击统一处理
    handleCallCodeDialogBottom (index, indexH) {
      switch (index) {
        case 0:
          this.$http.$emit('CallCodeDialog')
          break
        case 1:
          this.codeAddDivArrBottom.push('null')
          break
        case 2:
          if (this.codeAddDivArrBottom.length === 1) {
            this.$message.error('最少保留一个批次')
          } else {
            this.codeAddDivArrBottom.splice(indexH, 1)
          }
          break
        case 3:
          this.$message.error('暂无记录')
      }
    },
    // 部分门店表格列表删除点击
    handleToStoreRowDel (row) {
      console.log(row.$index)
      let { $index } = row
      this.chooseStore.splice($index, 1)
    },
    // 商品分类和平台分类弹窗选中回传数据
    BusClassTrueArr (data) {
      // 根据this.AtreeType 的值判断是指定商品里面的弹窗还是会员专享里面的弹窗   backDataArr字段是回显wiki应该有写
      if (this.userDialogFlag === '1') {
        // 折扣
        if (this.AtreeType === 1) {
          // 商家分类
          this.shopCategoryIds = data
          this.noneBlockDiscArr[1].num = data.length
        }

        if (this.AtreeType === 2) {
          // 平台分类
          this.platformCategoryIds = data
          this.noneBlockDiscArr[2].num = data.length
        }
      } else {
        // 专享
        if (this.AtreeType === 1) {
          // 商家分类
          this.ownStoreCategoryIds = data
          this.noneBlockVipArr[1].num = data.length
        }

        if (this.AtreeType === 2) {
          // 平台分类
          this.ownPlatFormCategoryIds = data
          this.noneBlockVipArr[2].num = data.length
        }
      }
    },

    handleBrandResult (res) {
      console.log(res)
      if (this.userDialogFlag === '1') {
        this.chioseSureData = res.map(({ id }) => id)
        this.noneBlockDiscArr[3].num = res.length
      } else {
        this.ownBrandId = res.map(({ id }) => id)
        this.noneBlockVipArr[3].num = res.length
      }
    },
    // 限次会员卡中显示的适用商品里面的选择商品调起事件
    handleToCallGoodsDialog (flag) {
      // 商品弹窗显示
      this.currentFlag = flag
      this.controlChoosingGoodsDialog = !this.controlChoosingGoodsDialog
      this.choosingGoodsDateTmpContainer = this.suitebleGoods
    },
    dealWithReceiveCodeId (id) {
      console.log(id, this.receiveCodeDialogVisible)
      this.codeAddDivArr[this.receiveCodeTmpIndex].batchId = id
      console.log(this.codeAddDivArr)
    },
    dealWithChooseStore (data) {
      this.chooseStore = data.map(({ storeId, storeName }) => {
        return { storeId, storeName }
      })
      console.log(this.chooseStore)
    },
    handleSuitableGoodsUseTime () {
      console.log(this.suitableGoodsUseTime)
      if (this.suitableGoodsUseTime.length === 2) {
        this.useTime = 0
      } else if (this.suitableGoodsUseTime.length === 1) {
        let val = this.suitableGoodsUseTime[0]
        if (val === '工作日') {
          this.useTime = 1
        }
        if (val === '双休日') {
          this.useTime = 2
        }
      } else {
        this.useTime = 1
      }
      console.log(this.useTime)
    },
    isValidValue (data) {
      return data !== null && data !== undefined
    },
    isValidArray (data) {
      return data !== null && data !== undefined && data.length !== 0
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
    // height: 100%;
    display: flex;
    padding-bottom: 57px;

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

      font-size: 13px;
      margin-bottom: 10px;
      /deep/ .el-form-item__label {
        white-space: nowrap;
        text-align: right;
      }
      .rightContainerTop {
        padding: 10px 1%;
        background: #f8f8f8;
        border: 1px solid #e4e4e4;
        margin-bottom: 20px;
        .rightTile {
          padding-bottom: 10px;
          border-bottom: 1px solid #ddd;
          margin-bottom: 10px;
        }
        .userCardName {
          /deep/ .el-textarea__inner {
            width: 65%;
          }
          padding-left: 100px;
          /deep/ .el-input__inner {
            width: 41%;
          }
          /deep/ .tableClss {
            th {
              padding: 0 !important;
              background-color: #f8f8f8;
              color: #333;
            }
          }
          .add_brand {
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
            /deep/ .open {
              z-index: 100;
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
              .card_add_click {
                background: #fff;
                border: 1px solid #e4e4e4;
                cursor: pointer;
                width: 108px;
                height: 100px;
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
          .couponList {
            width: 385px;
            margin-left: 30px;
            .couponLi {
              float: left;
              width: 108px;
              text-align: center;
              margin-right: 10px;
              margin-bottom: 15px;
              cursor: pointer;
              position: relative;
              img {
                position: absolute;
                top: -5px;
                right: -5px;
              }
              .coupon_list_top {
                height: 20px;
                color: #f66;
                font-size: 14px;
                border: 1px solid #fbb;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                border-bottom: none;
                span {
                  font-size: 20px;
                  font-weight: bold;
                  display: inline-block;
                }
              }
              .coupon_list_center {
                color: #f66;
                font-size: 12px;
                border-left: 1px solid #fbb;
                border-right: 1px solid #fbb;
                .coupon_center_number {
                  height: 20px;
                  line-height: 20px;
                  margin-top: -10px;
                  color: #fbb;
                  margin-bottom: 5px;
                }
              }
              .coupon_list_bottom {
                background-color: #f66;
                font-size: 12px;
                -webkit-background-size: 12px;
                background-size: 12px;
                height: 24px;
                line-height: 30px;
                color: #fff;
                border-left: 1px solid #fbb;
                border-right: 1px solid #fbb;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
                background-repeat: repeat-x;
                margin-left: -1px;
                width: 102%;
              }
              .coupon_name {
                font-size: 14px;
                line-height: 30px;
                border: 1px solid #fff;
                margin: 0 -1px -1px -1px;
                text-overflow: ellipsis;
                white-space: nowrap;
                overflow: hidden;
                color: #333;
                width: 110px;
              }
            }
          }
          .applyDiv {
            display: flex;
            span {
              white-space: nowrap;
            }
            /deep/ .el-input {
              width: 100px;
              .el-input__inner {
                width: 100%;
              }
            }
          }
          .partSuitableDiv {
            display: flex;
            span {
              white-space: nowrap;
              margin-right: 10px;
            }
            /deep/ .el-input {
              width: 100px;
              .el-input__inner {
                width: 100%;
              }
            }
          }
          .useTimeDiv {
            display: flex;
            span {
              margin-right: 10px;
            }
          }
          .noneBlockList {
            margin-bottom: 10px;
            display: flex;
            .noneBlockLeft {
              line-height: 30px;
              height: 30px;
              width: 95px;
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
        .first {
          /deep/ .el-form-item__label {
            margin-left: -8px;
          }
        }
        .dateList {
          /deep/ .el-input {
            width: auto;
            .el-input__inner {
              width: 100%;
            }
          }
        }
        .dateTips {
          color: #9d9d9d;
          padding-left: 224px;
          margin-bottom: 20px;
        }
        .useStoreTips {
          color: #9d9d9d;
        }
        /deep/ .el-textarea {
          width: 702px;
        }
        .phoneNum {
          /deep/ .el-input__inner {
            width: 70%;
          }
        }
      }
      .rightContainerBottom {
        background: #f8f8f8;
        border: 1px solid #e4e4e4;
        padding: 10px 1%;
        .rightTile {
          padding-bottom: 10px;
          border-bottom: 1px solid #ddd;
          margin-bottom: 10px;
        }
        .rightBottom {
          padding-left: 100px;

          .buyTableHidden {
            width: 100%;
            padding: 0px 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            margin: 20px 0 0 -100px;
            border-radius: 4px;
            /deep/ .el-input {
              width: auto;
            }
            .buyHiddenDiv {
              padding: 5px 20px;
              display: flex;
              color: #333;
              /deep/ .el-input {
                width: 60px;
                margin-right: 10px;
              }
              /deep/ .el-radio__label {
                height: 41px;
                line-height: 41px;
              }
              span {
                display: inline-block;
                margin-right: 10px;
                &:nth-of-type(3),
                &:nth-of-type(4),
                &:nth-of-type(5),
                &:nth-of-type(6) {
                  color: #5a83f9;
                  cursor: pointer;
                }
              }
            }
          }
          .receiveLimitNum {
            padding-bottom: 5px;
            .separateDiv {
              border-bottom: 1px solid #eee;
              margin: 10px 0;
            }
            .zeroDesc {
              margin-left: 20px;
              color: #999;
            }
            .hasSend {
              margin-left: 15px;
              color: #ff6666;
            }
            span {
              margin-right: 10px;
            }
          }
          .activationHidden {
            padding-left: 24px;
          }
          // .useDate{
          //   /deep/
          // }
        }
      }
    }
  }
}

.gradeDiv {
  background: #f8f8f8;
  border: 1px solid #e4e4e4;
  padding-left: 10px;
  margin-bottom: 10px;
  .gradeDivTitle {
    line-height: 38px;
    border-bottom: 1px solid #eee;
    margin-bottom: 7px;
  }
}
.userCardBox {
  display: flex;
  justify-content: center;
  margin-top: 10px;
  .specialBox {
    width: 547px;
    display: flex;
  }
}
.must {
  color: red;
}
.gradeSet {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 10px;
}
.countPromptTwo {
  color: #999;
}
.scoreReceiveDiv {
  margin-top: 10px;
  display: flex;
  width: 300px;
}
.scoreReceiveDiv span:first-child {
  line-height: 33px;
  min-width: 90px;
}

.scoreReceiveDiv span:last-child {
  line-height: 33px;
  margin-left: 10px;
}
.gradeConsumeDiv {
  display: flex;
}

.gradeConsumeDiv span:first-child {
  line-height: 33px;
  min-width: 90px;
}

.gradeConsumeDiv span:nth-child(2) {
  color: red;
  line-height: 33px;
  margin-left: 10px;
}

.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: fixed;
  bottom: 0;
  padding: 10px 0;
  left: 0;
  right: 0;
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
