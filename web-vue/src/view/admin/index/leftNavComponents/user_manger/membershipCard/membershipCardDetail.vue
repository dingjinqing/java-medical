<template>
  <div class="membershipCardDetail">
    <div class="membershipCardDetailMain">
      <div class="leftContainer">
        <div class="left_Top"></div>
        <div class="left_middle">
          <div
            class="example_card"
            :style="bgStyleComputed"
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
              prop="ruleForm.name"
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
                    :src="baImgUrl"
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
              <div class="discountDiv equity">
                <el-checkbox v-model="ruleForm.intGet">{{ $t('memberCard.getScore') }}&nbsp;&nbsp;&nbsp;&nbsp;</el-checkbox>{{ $t('memberCard.openCardSend') }}
                <el-input
                  v-model="ruleForm.IntegralInput"
                  size="small"
                ></el-input>
                &nbsp;&nbsp;{{ $t('memberCard.score') }}
              </div>
              <!--积分获取下方子模块-->
              <div class="shoppingFull">
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
                  <div class="noneIntegralDiv">
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
                <div class="shoppingFullBottom">
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
              <!--卡充值-->
              <div class="cardRecharge">
                <el-checkbox v-model="ruleForm.cardRechargeFlag">{{ $t('memberCard.powerCard') }}&nbsp;&nbsp;&nbsp;&nbsp;{{ $t('memberCard.openCardSend') }}</el-checkbox>
                <el-input
                  v-model="ruleForm.cardRechargeInput"
                  size="small"
                ></el-input>
                &nbsp;&nbsp;{{ $t('memberCard.yuan') }}
              </div>
              <!--卡充值下方子模块-->
              <div class="shoppingFull">
                <el-radio
                  v-model="ruleForm.rechargeInput"
                  label="2"
                >{{ $t('memberCard.justCharge') }}</el-radio>
              </div>
              <div class="shoppingFull">
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
                <block
                  v-for="(item,index) in ruleForm.addrechargeArr"
                  :key="index"
                >
                  <div class="noneIntegralDiv">
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
                </block>
                <div class="shoppingFullBottom">
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
              <div class="sendingPaper">
                <el-checkbox v-model="ruleForm.sendingPaperFlag">{{ $t('memberCard.openCardSendVolume') }}&nbsp;&nbsp;&nbsp;&nbsp;{{ $t('memberCard.volumeActiveInfo') }}</el-checkbox>
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
                  >{{ $t('memberCard.offerVolume') }}&nbsp;&nbsp;&nbsp;&nbsp;{{ $t('memberCard.offerInfo') }}</el-radio>
                  <div
                    class="couponList"
                    v-if="ruleForm.couponDiv==='1'"
                  >
                    <!--添加的优惠卷列表-->
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
                        ￥<span>{{item.price}}</span>
                      </div>
                      <div class="coupon_list_center">
                        <div class="coupon_center_limit">{{item.isLimit?`$t('memberCard.enough') ${item.nolimitPrice}$t('memberCard.usage')`:'$t(\'memberCard.unlimit\')'}}</div>
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
              prop="ruleForm.fixedDate"
              class="userCardName useDate"
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
                  size="small"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :default-time="['00:00:00','23:59:59']"
                >
                </el-date-picker>
              </div>
            </el-form-item>
            <div class="dateTips">{{ $t('memberCard.exampleInfo') }}</div>
            <el-form-item
              prop="fromDateInput"
              class="userCardName"
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
            <el-form-item class="userCardName">
              <div class="dateList">
                <el-radio
                  v-model="ruleForm.dateRadio"
                  label="2"
                >{{ $t('memberCard.ForeverEffective') }}</el-radio>
              </div>
            </el-form-item>
            <el-form-item
              :label="$t('memberCard.usingStore')"
              prop="useStore"
              class="userCardName"
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
                  v-if="chioseSureData.length"
                  class="table"
                >
                  <el-table
                    class="version-manage-table"
                    header-row-class-name="tableClss"
                    :data="chioseSureData"
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
                <div
                  class="buyTableHidden"
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
                      v-model="ruleFormBottom.integralRadio"
                      label="1"
                    >{{ $t('memberCard.scoreBuy') }}</el-radio>
                    <el-input
                      v-model="ruleFormBottom.integralInput"
                      :placeholder="$t('memberCard.pleaseInput')"
                      size="small"
                    ></el-input>&nbsp;&nbsp;{{ $t('memberCard.unitM') }}
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
                            v-model="ruleFormBottom.pcNameinput"
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
    <!--添加优惠卷-->
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
    />
    <!--选择商家分类弹窗-->
    <AddingBusClassDialog />

    <!--添加品牌弹窗-->
    <AddBrandDialog />
    <!--添加门店弹窗-->
    <ChioseStoreDialog />
    <!--领取码弹窗-->
    <ReceivingCodeDialog />
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
    AddBrandDialog: () => import('./addBrandDialog')
  },
  data () {
    var validiscount = (rule, value, callback) => {
      console.log(rule, value, callback)
      let reg = /^\d{0,1}$/
      let flag = reg.test(Number(value))
      console.log(Number(value), flag)
      if (!this.ruleForm.discount) {
        callback()
        return
      }
      if (value === '' || !reg.test(Number(value))) {
        callback(new Error('请输入0-10之间的数字'))
      } else {
        callback()
      }
    }
    var validatorDate = (rule, value, callback) => {
      console.log(value)
      console.log(this.ruleForm.dateRadio)
      if (this.ruleForm.dateRadio !== '0') {
        callback()
        return
      }
      if (!value) {
        callback(new Error('请输入有效期'))
      } else {
        callback()
      }
    }
    var validatorDateInput = (rule, value, callback) => {
      console.log(this.ruleForm.dateRadio)
      if (this.ruleForm.dateRadio !== '1') {
        callback()
        return
      }
      if (value === '') {
        callback(new Error('请输入有效期'))
      } else {
        callback()
      }
    }
    return {
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
        bgFlag: '0',
        discount: true,
        discountInput: '',
        allGoods: '1',
        IntegralInput: '',
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
        phoneNuminput: ''
      },
      rules: {
        name: [{ required: true, message: '请输入会员卡名称', trigger: 'blur' }],
        discountInput: [{ validator: validiscount, required: true, trigger: 'blur' }],
        fixedDate: [{ type: 'date', validator: validatorDate, required: true, trigger: 'change' }],
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
        integralInput: '',
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
      clickArr: [],
      treeType: null,
      AtreeType: null,
      codeArr: [],
      codeAddDivArr: ['null'],
      codeAddDivArrBottom: ['null'],
      chioseSureData: [],
      userDialogFlag: null,
      addBrandDialogDataFlag1: '', // 指定商品-添加品牌弹窗选中数据
      addBrandDialogDataFlag2: '', // 会员专享-添加品牌弹窗选中数据
      choosingGoodsDateFlag1: '', // 指定商品-选择商品选中数据,
      controlChoosingGoodsDialog: 1,
      choosingGoodsDateFlag2: '', // 会员专享-选择商品选中数据
      tuneUp: false,
      couponBack: ''
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
  created () {
    this.cardType = this.$route.query.cardType
    this.cardId = this.$route.query.cardId
  },
  computed: {
    // 动态变化背景色或背景图片
    bgStyleComputed () {
      let bg = ''
      if (this.ruleForm.bgFlag === '0') {
        bg = `background-color:${this.colorLeft_}`
      } else {
        bg = `backgroundImage:url(${this.baImgUrl})`
      }
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
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          // alert('submit!')
          this.$refs['ruleForm'].resetFields()
        } else {
          //   console.log('error submit!!')
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
        this.leftNavData[1].children = ['购物满100宋100积分']
      } else {
        this.leftNavData[1].children = ['购物每满100宋100积分']
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
        'goodsId': [],
        'shopCategoryIds': [],
        'platformCategoryIds': [],
        'powerScore': this.ruleForm.intGet ? 1 : 0,
        'score': this.ruleForm.IntegralInput,
        'scoreJson': {
          'offset': this.ruleForm.shoppingFull,
          'goodsMoney': this.goodsMoney,
          'getScores': this.getScores,
          'perGoodsMoney': this.ruleForm.shopingInputLeftM,
          'perGetScores': this.ruleForm.shopingInputReftM
        },
        'sendMoney': this.ruleForm.cardRechargeInput,
        'expiredType': this.ruleForm.dateRadio,
        'startTime': this.ruleForm.fixedDate ? this.ruleForm.fixedDate[0] : null,
        'endTime': this.ruleForm.fixedDate ? this.ruleForm.fixedDate[1] : null,
        'receiveDay': this.ruleForm.fromDateInput,
        'dateType': this.ruleForm.dateSelectvalue,
        'storeListType': this.ruleForm.useStoreRadio,
        'storeList': [],
        'desc': this.ruleForm.textarea,
        'mobile': this.ruleForm.phoneNuminput,
        'isPay': this.ruleFormBottom.isBuyRadio,
        'payType': this.ruleFormBottom.cashRadio,
        'payMoney': this.ruleFormBottom.cashInput,
        'payScore': this.ruleFormBottom.integralInput,
        'activation': this.ruleFormBottom.activationRadio,
        'activationCfgBox': this.ruleFormBottom.checkList,
        'examine': this.ruleFormBottom.examineRadio
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
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.prepareCardData()
          this.$refs['ruleForm'].resetFields()
        } else {
          this.$message.error(this.$t('memberCard.cardCreateFailed'))
          //  console.log('error submit!!')
          return false
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
      this.ruleForm.name = data.cardName
      this.ruleForm.bgFlag = String(data.bgType)
      console.log('Hello World', this.ruleForm.bgFlag)
      this.colorLeft_ = data.bgColor
      this.baImgUrl = data.bgImg
      this.ruleForm.discount = data.powerCount === 1
      this.ruleForm.discountInput = data.disCount
      this.ruleForm.allGoods = String(data.discountIsAll)
      this.ruleForm.vipFlag = data.powerPayOwnGood === 'on'

      /** 待处理
        'goodsId': [],
        'shopCategoryIds': [],
        'platformCategoryIds': [],
        'storeList': [],
        'activationCfgBox': [],
      */
      this.ruleForm.intGet = data.powerScore === 1
      this.ruleForm.IntegralInput = data.score
      this.ruleForm.shoppingFull = String(data.scoreJson.offset)
      this.goodsMoney = data.scoreJson.goodsMoney
      this.getScores = data.scoreJson.getScores

      this.ruleForm.shopingInputLeftM = data.perGoodsMoney
      this.ruleForm.shopingInputReftM = data.perGetScores
      this.ruleForm.cardRechargeFlag = data.powerCard === 1
      this.ruleForm.cardRechargeInput = data.sendMoney
      this.ruleForm.rechargeInput = String(data.powerCardJson.offsetMoney)
      this.money = data.powerCardJson.money
      this.getMoney = data.powerCardJson.getMoney
      this.ruleForm.rechargeInputLeftM = data.powerCardJson.perMoney
      this.ruleForm.rechargeInputReftM = data.powerCardJson.perGetMoney
      this.ruleForm.dateRadio = String(data.expireType)
      // this.ruleForm.fixedDate[0] = data.startTime
      // this.ruleForm.fixedDate[1] = data.endTime
      this.ruleForm.fixedDate = [data.startTime, data.endTime]
      // this.ruleForm.fixedDate = ['2019-10-17 00:00:00', '2019-11-12 23:59:59']
      console.log(this.ruleForm.fixedDate)
      this.ruleForm.fromDateInput = data.receiveDay
      this.ruleForm.dateSelectvalue = data.dateType
      this.ruleForm.useStoreRadio = data.storeListType
      this.ruleForm.textarea = data.desc
      this.ruleForm.phoneNuminput = data.mobile
      this.ruleFormBottom.isBuyRadio = String(data.isPay)
      this.ruleFormBottom.cashRadio = String(data.payType)
      this.ruleFormBottom.cashInput = String(data.payMoney)
      this.ruleFormBottom.integralInput = data.payScore
      this.ruleFormBottom.activationRadio = String(data.activation)
      this.ruleFormBottom.examineRadio = String(data.examine)
      this.ruleFormBottom.checkList = data.activationCfgBox ? data.activationCfgBox : []

      // 处理json数据
      this.dealWithDataFromBackEnd()
    },
    // 8- 处理动态数据
    dealWithDynamicArrayData () {
      // 积分
      console.log(this.goodsMoney, this.getScores)

      if (this.ruleForm.shoppingFull === '0') {
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
        this.money.push(this.ruleForm.rechargeInputLeft)
        this.getMoney.push(this.ruleForm.rechargeInputReft)
        for (let item of this.ruleForm.addrechargeArr) {
          this.money.push(item.leftInput)
          this.getMoney.push(item.rightInput)
        }
      }
    },
    // 8-1 处理从后端获取的数据
    dealWithDataFromBackEnd () {
      // 积分
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

      // 卡充值
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
    },
    // 9- 更新会员卡信息
    updateCardInfo (data) {
      updateCardRequest(data).then(res => {
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
      // 添加商品id
      this.choosingGoodsDateFlag1 = data
      this.noneBlockDiscArr[0].num = data.length
    },
    dataDefalut () {
      this.$http.$on('result', res => {
        if (this.userDialogFlag === '1') {
          this.choosingGoodsDateFlag1 = res
          this.noneBlockDiscArr[0].num = res.length
        } else {
          this.choosingGoodsDateFlag2 = res
          this.noneBlockVipArr[0].num = res.length
        }
        console.log(res)
      })
      this.$http.$on('chioseSureData', res => {
        console.log(res)
        this.chioseSureData = res
      })
      this.$http.$on('BusClassTrueArr', res => {
        console.log(res)
        console.log(this.AtreeType)
        switch (this.userDialogFlag) {
          case '1':
            if (this.AtreeType === 1) {
              this.noneBlockDiscArr[1].num = res.length
            } else {
              this.noneBlockDiscArr[2].num = res.length
            }

            break
          case '2':
            if (this.AtreeType === 1) {
              this.noneBlockVipArr[1].num = res.length
            } else {
              this.noneBlockVipArr[2].num = res.length
            }
        }

        console.log(res)
      })
      this.$http.$on('ABusClassTrueArr', res => {
        console.log(res)
        console.log(this.AtreeType)
        if (this.AtreeType === 1) {
          this.noneBlockDiscArr[1].num = res.length
        } else {
          this.noneBlockDiscArr[2].num = res.length
        }
        console.log(res)
      })
      this.$http.$on('addBrandDialogSure', res => {
        console.log('接收结果', res)
        if (this.userDialogFlag === '1') {
          this.noneBlockDiscArr[3].num = res.length
          this.addBrandDialogDataFlag1 = res
        } else {
          this.addBrandDialogDataFlag2 = res
          this.noneBlockVipArr[3].num = res.length
        }
        console.log(res)
      })
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
    },
    // 删除充值满模块
    handleToDelRecharge (index) {
      this.ruleForm.addrechargeArr.splice(index, 1)
    },
    // 调起添加优惠卷弹窗
    handleToCallDialog () {
      let arr = [41, 40]
      this.tuneUpCoupon = !this.tuneUpCoupon

      this.couponBack = arr
      // this.$http.$emit('V-AddCoupon', obj)
    },
    // 添加优惠卷弹窗回传
    handleToCheck (data) {
      console.log(data)
      this.couponList = data
    },
    // 删除优惠卷项
    handlToDelCouList (index) {
      this.couponList.splice(index, 1)
    },
    // 点击指定商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      console.log('指定商品')
      this.userDialogFlag = '1'
      let arr = ['21']
      console.log(index)
      switch (index) {
        case 0:
          // this.$http.$emit('choosingGoodsFlag', index, this.choosingGoodsDateFlag1)
          // 商品弹窗显示
          this.controlChoosingGoodsDialog = !this.controlChoosingGoodsDialog
          break
        case 1:
          this.AtreeType = 1
          this.$http.$emit('addingBusClassDialog', arr)
          break
        case 2:
          this.AtreeType = 2
          this.$http.$emit('addingBusClassDialog', arr, this.AtreeType)
          break
        case 3:
          console.log('detail', index, this.addBrandDialogDataFlag1)
          this.$http.$emit('CallAddBrand', index, this.addBrandDialogDataFlag1)
      }
    },
    // 点击会员专享商品出现的添加类弹窗汇总
    hanldeToAddGoodSUser (index) {
      console.log('会员专享')
      this.userDialogFlag = '2'
      let arr = ['21', '25']
      console.log(index)
      switch (index) {
        case 0:
          this.$http.$emit('choosingGoodsFlag', index, this.choosingGoodsDateFlag2)
          break
        case 1:
          this.AtreeType = 1
          this.$http.$emit('addingBusClassDialog', arr)
          break
        case 2:
          this.AtreeType = 2
          this.$http.$emit('addingBusClassDialog', arr, this.AtreeType)
          break
        case 3:
          this.$http.$emit('CallAddBrand', index, this.addBrandDialogDataFlag2)
      }
      console.log(index)
    },
    // 调起添加门店弹窗
    handleToCallChioseStore () {
      this.$http.$emit('CallChioseStore')
    },
    // 调起领取码弹窗
    handleCallCodeDialog (index, indexH) {
      switch (index) {
        case 0:

          this.$http.$emit('CallCodeDialog')
          break
        case 1:
          this.codeAddDivArr.push('null')
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
      this.chioseSureData.splice($index, 1)
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
            width: 24%;
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
            padding: 5px 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            margin: 20px 0 0 -100px;
            border-radius: 4px;
            /deep/ .el-input {
              width: auto;
            }
            .buyHiddenDiv {
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
