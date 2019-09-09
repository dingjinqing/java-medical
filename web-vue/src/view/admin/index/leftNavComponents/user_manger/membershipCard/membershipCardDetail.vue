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
              v-if="ruleForm.dateRadio === '1'"
              class="effect_date"
            >
              有效期：{{ruleForm.fixedDate | handleDate}}
            </div>
            <div
              v-if="ruleForm.dateRadio === '2'"
              class="effect_date"
            >
              有效期：自领取之日内{{ruleForm.fromDateInput}}{{ruleForm.dateSelectvalue}}有效
            </div>
            <div
              v-if="ruleForm.dateRadio === '3'"
              class="effect_date"
            >
              有效期：永久有效
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
              prop="discountInput"
              class="userCardName"
            >
              <div class="discountDiv equity">
                <el-checkbox v-model="ruleForm.discount">会员折扣</el-checkbox>
                <el-input
                  v-model="ruleForm.discountInput"
                  size="small"
                ></el-input>
                &nbsp;&nbsp;折
              </div>
            </el-form-item>
            <el-form-item
              label=""
              class="userCardName"
            >
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
                  @click="hanldeToAddGoodS(index)"
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
                  >已选择分类：{{item.num}}个分类</div>
                </div>
              </div>
              <!--end-->
              <div class="discountDiv equity">
                <el-checkbox v-model="ruleForm.intGet">积分获取&nbsp;&nbsp;&nbsp;&nbsp;开卡赠送</el-checkbox>
                <el-input
                  v-model="ruleForm.IntegralInput"
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
                <el-radio
                  v-model="ruleForm.rechargeInput"
                  label="1"
                >仅充值</el-radio>
              </div>
              <div class="shoppingFull">
                <div class="shoppingFullTop">
                  <el-radio
                    v-model="ruleForm.rechargeInput"
                    label="2"
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
                    label="3"
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
                        <div class="coupon_center_limit">{{item.isLimit?`满${item.nolimitPrice}使用`:'不限制'}}</div>
                        <div class="coupon_center_number">剩余<span>{{item.surplus}}</span>张</div>
                        <div
                          class="coupon_list_bottom"
                          :style="`backgroundImage:url('${$imageHost}/image/admin/coupon_border.png')`"
                        >领取 </div>
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
                        <p>添加优惠卷</p>
                      </div>
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

            <el-form-item
              label="会员有效期："
              prop="fixedDate"
              class="userCardName useDate"
            >
              <div class="dateList">
                <el-radio
                  v-model="ruleForm.dateRadio"
                  label="1"
                >固定日期</el-radio>
                <el-date-picker
                  v-model="ruleForm.fixedDate"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  size="small"
                  value-format='yyyy-MM-dd'
                >
                </el-date-picker>
              </div>
            </el-form-item>
            <div class="dateTips">例如：选择日期2018-1-2到2018-1-5，表示有效期为2018-1-2 00:00:00到2018-1-5 24:00:00</div>
            <el-form-item
              prop="fromDateInput"
              class="userCardName"
            >
              <div class="dateList">
                <el-radio
                  v-model="ruleForm.dateRadio"
                  label="2"
                >自领取之日起</el-radio>
                <el-input
                  size="small"
                  v-model="ruleForm.fromDateInput"
                ></el-input>
                <el-select
                  v-model="ruleForm.dateSelectvalue"
                  placeholder="请选择"
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
                内有效
              </div>
            </el-form-item>
            <el-form-item class="userCardName">
              <div class="dateList">
                <el-radio
                  v-model="ruleForm.dateRadio"
                  label="3"
                >永久有效</el-radio>
              </div>
            </el-form-item>
            <el-form-item
              label="使用门店："
              prop="useStore"
              class="userCardName"
            >
              <div class="useStoreDiv">
                <el-radio
                  v-model="ruleForm.useStoreRadio"
                  label="1"
                >全部门店</el-radio>
                <el-radio
                  v-model="ruleForm.useStoreRadio"
                  label="2"
                >部分门店</el-radio>
                <el-radio
                  v-model="ruleForm.useStoreRadio"
                  label="3"
                >不可再门店使用</el-radio>
              </div>
              <div class="useStoreTips">配置仅限限制门店买单、门店自提、核销门店预约服务时的会员卡使用，线上购买发货默认所有会员卡均可以使用</div>
              <!--点击部分门店时显示模块-->
              <div v-if="ruleForm.useStoreRadio==='2'">
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
                      label="门店名称"
                    >
                    </el-table-column>
                    <el-table-column
                      align="center"
                      label="操作"
                    >
                      <template slot-scope="scope">
                        <span
                          @click="handleToStoreRowDel(scope)"
                          style="color:#5A8BFF;cursor:pointer"
                        >删除</span>

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
                  添加门店
                </div>
              </div>

            </el-form-item>
            <el-form-item
              label="使用须知："
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
              label="联系电话："
              class="userCardName  phoneNum"
            >
              <el-input
                v-model="ruleForm.phoneNuminput"
                placeholder="请输入内容"
                size="small"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>
        <!--底部部分-->
        <div class="rightContainerBottom">
          <div class="rightTile">领取设置</div>
          <div class="rightBottom">
            <el-form
              :model="ruleFormBottom"
              :rules="rulesBottom"
              ref="ruleForm b ottom"
              label-width="100px"
              class="demo-ruleForm"
            >

              <el-form-item
                label="是否需要购买："
                class="userCardName  phoneNum"
              >
                <el-radio
                  v-model="ruleFormBottom.isBuyRadio"
                  label="1"
                >直接领取</el-radio>
                <el-radio
                  v-model="ruleFormBottom.isBuyRadio"
                  label="2"
                >需要购买</el-radio>
                <el-radio
                  v-model="ruleFormBottom.isBuyRadio"
                  label="3"
                >需要领取码</el-radio>
                <div
                  class="buyTableHidden"
                  v-if="ruleFormBottom.isBuyRadio === '2'"
                >
                  <div>
                    <el-radio
                      v-model="ruleFormBottom.cashRadio"
                      label="1"
                    >现金购买</el-radio>
                    <el-input
                      v-model="ruleFormBottom.cashInput"
                      placeholder="请输入内容"
                      size="small"
                    ></el-input>&nbsp;&nbsp;元
                  </div>

                  <div>
                    <el-radio
                      v-model="ruleFormBottom.integralRadio"
                      label="2"
                    >积分购买</el-radio>
                    <el-input
                      v-model="ruleFormBottom.integralInput"
                      placeholder="请输入内容"
                      size="small"
                    ></el-input>&nbsp;&nbsp;分
                  </div>

                </div>
                <!--点击需要领取码出现模块-->
                <div
                  class="buyTableHidden"
                  v-if="ruleFormBottom.isBuyRadio === '3'"
                >
                  <div class="buyHiddenDiv">

                    <el-radio
                      v-model="ruleFormBottom.needGetRadio"
                      label="1"
                    >领取码领取</el-radio>
                    <div v-if="ruleFormBottom.needGetRadio=== '1'">
                      <div
                        v-for="(itemH,indexH) in codeAddDivArr"
                        :key="indexH"
                      >
                        <div>
                          <span>批次1</span>
                          <span>批次名称</span>
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
                    >卡号+密码领取</el-radio>
                    <div v-if="ruleFormBottom.needGetRadio=== '2'">
                      <div
                        v-for="(itemH,indexH) in codeAddDivArrBottom"
                        :key="indexH"
                      >
                        <div>
                          <span>批次1</span>
                          <span>批次名称</span>
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
                label="是否需要激活："
                class="userCardName  phoneNum"
              >
                <el-radio
                  v-model="ruleFormBottom.activationRadio"
                  label="1"
                >否</el-radio>
                <el-radio
                  v-model="ruleFormBottom.activationRadio"
                  label="2"
                >是</el-radio>
                <div class="activationHidden">
                  <div style="color:#9D9D9D">选择后，请勾选你需要用户填写的信息</div>
                  <!--选择需要激活后显示模块-->
                  <div v-if="ruleFormBottom.activationRadio==='2'">
                    <div class="activationTop">
                      <el-checkbox v-model="ruleFormBottom.activation.nameCheck">真实姓名</el-checkbox>
                      <el-checkbox v-model="ruleFormBottom.activation.phoneCheck">手机号</el-checkbox>
                      <el-checkbox v-model="ruleFormBottom.activation.idheck">身份证号码</el-checkbox>
                      <el-checkbox v-model="ruleFormBottom.activation.faxCheck">性别</el-checkbox>
                      <el-checkbox v-model="ruleFormBottom.activation.birthDayCheck">生日</el-checkbox>
                      <el-checkbox v-model="ruleFormBottom.activation.marryCheck">婚姻状况</el-checkbox>
                      <el-checkbox v-model="ruleFormBottom.activation.eduCheck">教育程度</el-checkbox>
                      <el-checkbox v-model="ruleFormBottom.activation.jobCheck">所在行业</el-checkbox>
                      <el-checkbox v-model="ruleFormBottom.activation.adressCheck">所在地</el-checkbox>
                    </div>
                    <div class="activationBottom">
                      <div style="color:#9D9D9D">激活信息是否需要审核</div>
                      <el-radio
                        v-model="ruleFormBottom.examineRadio"
                        label="1"
                      >无需审核</el-radio>
                      <el-radio
                        v-model="ruleFormBottom.examineRadio"
                        label="2"
                      >需要审核</el-radio>
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
      @handleSelectImg='handleSelectImg'
    />
    <!--添加优惠卷-->
    <AddCouponDialog @handleToCheck="handleToCheck" />
    <!--选择商品弹窗-->
    <ChoosingGoods />
    <!--选择商家分类弹窗-->
    <AddingBusClassDialog />
    <!--添加平台分类弹窗-->
    <BrandDialog />
    <!--指定商品添加商品分类弹窗-->
    <AppointBusDialog />
    <!--指定商品添加平台分类弹窗-->
    <AppointBrandDialog />
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
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'),
    AddCouponDialog: () => import('@/components/admin/addCouponDialog'),
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AddingBusClassDialog: () => import('./addingBusClassDialog'),
    BrandDialog: () => import('./brandDialog'),
    AppointBusDialog: () => import('@/view/admin/layout/addingBusClassDialog'),
    AppointBrandDialog: () => import('@/view/admin/layout/brandDialog'),
    AddBrandDialog: () => import('./addBrandDialog'),
    ChioseStoreDialog: () => import('./chioseStoreDialog'),
    ReceivingCodeDialog: () => import('./receivingCodeDialog')
  },
  data () {
    var validiscount = (rule, value, callback) => {
      console.log(rule, value, callback)
      let reg = /^\d{0,1}$/
      let flag = reg.test(Number(value))
      console.log(Number(value), flag)
      if (!this.ruleForm.discount) return
      if (value === '' || !reg.test(Number(value))) {
        callback(new Error('请输入0-10之间的数字'))
      }
    }
    var validatorDate = (rule, value, callback) => {
      console.log(value)
      console.log(this.ruleForm.dateRadio)
      if (this.ruleForm.dateRadio !== '1') return
      if (value === '') callback(new Error('请输入有效期'))
    }
    var validatorDateInput = (rule, value, callback) => {
      console.log(this.ruleForm.dateRadio)
      if (this.ruleForm.dateRadio !== '2') return
      if (value === '') callback(new Error('请输入有效期'))
    }
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
        discount: true,
        discountInput: '',
        allGoods: '1',
        IntegralInput: '',
        vipFlag: false,
        shoppingFull: '1',
        intGet: true,
        shopingInputLeft: '100',
        shopingInputReft: '100',
        shopingInputLeftM: '100',
        shopingInputReftM: '100',
        addIntegralArr: [],
        cardRechargeFlag: true,
        cardRechargeInput: '',
        rechargeInput: '1',
        rechargeInputLeft: '100',
        rechargeInputReft: '100',
        rechargeInputLeftM: '100',
        rechargeInputReftM: '100',
        addrechargeArr: [],
        sendingPaperFlag: false,
        couponDiv: '1',
        termValidity: '',
        dateRadio: '1',
        fixedDate: '',
        fromDateInput: '',
        dateSelectvalue: '日',
        dateSelectOptions: [{
          value: '日',
          label: '日'
        }, {
          value: '周',
          label: '周'
        }, {
          value: '月',
          label: '月'
        }],
        useStoreRadio: '1',
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
        phoneNuminput: '',
        isBuyRadio: '1',
        activationRadio: '1',
        cashRadio: '1',
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
        examineRadio: '1'
      },
      rulesBottom: {

      },
      baImgUrl: null,
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
      codeArr: ['领取码', '增加批次', '废除批次', '生成/导入记录'],
      codeAddDivArr: ['null'],
      codeAddDivArrBottom: ['null'],
      chioseSureData: [],
      userDialogFlag: null,
      addBrandDialogDataFlag1: '', // 指定商品-添加品牌弹窗选中数据
      addBrandDialogDataFlag2: '', // 会员专享-添加品牌弹窗选中数据
      choosingGoodsDateFlag1: '', // 指定商品-选择商品选中数据
      choosingGoodsDateFlag2: '' // 会员专享-选择商品选中数据
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
      if (this.ruleForm.bgFlag === '1') {
        bg = `background-color:${this.colorLeft_}`
      } else {
        bg = `backgroundImage:url(${this.baImgUrl})`
      }
      console.log(this.ruleForm.bgFlag, bg)
      return bg
    }
  },
  watch: {
    'ruleForm.dateRadio' (newData) {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          alert('submit!')
          this.$refs['ruleForm'].resetFields()
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
  },
  methods: {
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
        console.log(this.treeType)
        if (this.treeType === 1) {
          this.noneBlockVipArr[1].num = res.length
        } else {
          this.noneBlockVipArr[2].num = res.length
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
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.couponList
      }

      this.$http.$emit('V-AddCoupon', obj)
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
      this.userDialogFlag = '1'
      console.log(index)
      switch (index) {
        case 0:
          this.$http.$emit('choosingGoodsFlag', index, this.choosingGoodsDateFlag1)
          break
        case 1:
          this.AtreeType = 1
          this.$http.$emit('AaddingBusClassDialog', index)
          break
        case 2:
          this.AtreeType = 2
          this.$http.$emit('AuserBrandDialog', index)
          break
        case 3:
          this.$http.$emit('CallAddBrand', index, this.addBrandDialogDataFlag1)
      }
    },
    // 点击会员专享商品出现的添加类弹窗汇总
    hanldeToAddGoodSUser (index) {
      this.userDialogFlag = '2'
      console.log(index)
      switch (index) {
        case 0:
          this.$http.$emit('choosingGoodsFlag', index, this.choosingGoodsDateFlag2)
          break
        case 1:
          this.treeType = 1
          this.$http.$emit('addingBusClassDialog', index)
          break
        case 2:
          this.treeType = 2
          this.$http.$emit('userBrandDialog', index)
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
