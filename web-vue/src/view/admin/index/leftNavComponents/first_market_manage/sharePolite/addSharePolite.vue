<template>
  <div class="bottomNavigationContent">
    <div class="bottomNavigationContent_main">

      <!-- 左侧轮播图内容-->
      <div class="cententLleft">
        <div class="cententLleft_title"></div>
        <div id="slider">
          <div class="scroll">
            <swiper :options="swiperOption">
              <swiper-slide>
                <div class="advan_li_left">
                  <img
                    :src="imageUrlData[0].image_1"
                    alt=""
                  >
                </div>
              </swiper-slide>
              <swiper-slide>
                <div class="advan_li_left">
                  <img
                    :src="imageUrlData[1].image_2"
                    alt=""
                  >
                </div>
              </swiper-slide>
              <div
                class="swiper-pagination"
                slot="pagination"
              ></div> <!-- 标页码 -->
            </swiper>
          </div>
        </div>
      </div>

      <!-- 右侧内容部分 -->
      <section class="right_main">
        <!-- 活动信息部分 -->
        <div class="contentRight">
          <div class="actInfo">{{$t('adSharePolite.activityInfo')}}</div>
          <el-form
            :model="param"
            ref="param"
            label-position="right"
            label-width="100px"
            style="margin-top:20px;"
            :rules="fieldValidation"
          >
            <el-form-item
              :label="$t('adSharePolite.activityName')+'：'"
              prop="name"
            >
              <el-input
                size="small"
                style="width:200px"
                v-model="param.name"
                :placeholder="$t('adSharePolite.namelimit')"
                maxlength="10"
                show-word-limit
              ></el-input>
            </el-form-item>
            <br>
            <el-form-item
              :label="$t('adSharePolite.validityPeriod')+'：'"
              prop="isForever"
            >
              <el-radio-group v-model="param.isForever">
                <el-form-item>
                  <el-radio :label=0>{{$t('adSharePolite.fixTime')}}</el-radio>
                  <el-radio
                    :label=1
                    style="margin-left:50px;"
                  >{{$t('adSharePolite.forever')}}</el-radio>
                </el-form-item>
                <el-form-item
                  prop="effectiveDate"
                  ref="effectiveDate"
                >
                  <el-date-picker
                    v-if="this.param.isForever == 0"
                    v-model="param.effectiveDate"
                    style="width: 380px;"
                    type="datetimerange"
                    :range-separator=" $t('marketCommon.to') "
                    :start-placeholder="$t('marketCommon.startTime')"
                    :end-placeholder="$t('marketCommon.endTime')"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :default-time="['00:00:00', '23:59:59']"
                    size="small"
                  >
                  </el-date-picker>
                </el-form-item>
                <br>
              </el-radio-group>
            </el-form-item>
            <el-form-item
              :label="$t('adSharePolite.priority')+'：'"
              prop="priority"
            >
              <el-input
                size="small"
                style="width:200px"
                v-model.number="param.priority"
              ></el-input>
              <div class="tips">{{$t('adSharePolite.priorityComment')}}</div>
            </el-form-item>
            <br>
            <el-form-item
              :label="$t('adSharePolite.condition')+'：'"
              prop="condition"
            >
              <span>{{$t('adSharePolite.conditionInfo')}}</span>
              <el-radio-group v-model="param.condition">
                <el-radio :label=1>{{$t('adSharePolite.allGoods')}}</el-radio>
                <el-radio :label=2>{{$t('adSharePolite.specifyGoods')}}</el-radio>
                <el-radio :label=3>{{$t('adSharePolite.pvLessGoods')}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item
              prop="goodsIds"
              ref="goodsIds"
              v-if="param.condition == 2"
            >
              <div
                @click="showChoosingGoods"
                class="add_btn specify_goods"
              >{{$t('adSharePolite.chooseGoods')}}</div>
              <span>{{$t('adSharePolite.alreadyChoose')}}{{selectGoods}}{{$t('adSharePolite.goods')}}</span>
            </el-form-item>
            <el-form-item
              prop="goodsPv"
              ref="goodsPv"
              v-if="param.condition == 3"
            >
              <div style="margin-left:60px;">
                <span>{{$t('adSharePolite.pvInFact')}}</span>
                <el-input
                  size="small"
                  style="width:70px"
                  v-model.number="param.goodsPv"
                  placeholder="0"
                ></el-input> {{$t('adSharePolite.pvGoods')}}
              </div>
            </el-form-item>
          </el-form>
        </div>

        <!-- 分享奖励部分 -->
        <div
          class="contentRight"
          style="margin-top:10px;"
        >
          <div style="display:flex;border-bottom:1px solid #e5e5e5;">
            <div class="actInfo">{{$t('adSharePolite.shareReward')}}</div>
            <div style="display:flex">
              <span style="width: 120px">{{$t('adSharePolite.limitRule')}}</span>
              <span
                class="addRules"
                @click="addItem()"
              >{{$t('adSharePolite.addRule')}}</span>
            </div>
          </div>
          <el-form
            ref="shareRules"
            label-position="right"
          >
            <el-form-item>
              <el-checkbox v-model="param.visitFirst">{{$t('adSharePolite.visitNew')}}</el-checkbox>
            </el-form-item>

            <section
              v-for="(item,index) in shareRules"
              :key="index"
              class="rules_part"
            >
              <el-form-item :label="(index+1)+$t('adSharePolite.level')">
                <el-form-item
                  :label="$t('adSharePolite.invite')"
                  label-width="70px"
                  :prop="`shareRules[${index}].invite_num`"
                  :rules="[
                    {required: true, validator: (rule, value, callback) => {validatePersonNumber(rule, value, callback, item.invite_num)}, trigger: ['blur','change']}
                  ]"
                >
                  <el-input
                    size="small"
                    style="width:60px"
                    v-model="item.invite_num"
                    placeholder="0"
                  ></el-input> {{$t('adSharePolite.person')}} <span style="color:#999">{{$t('adSharePolite.invireNum')}}</span>
                  <i
                    v-if="index>0"
                    class="el-icon-delete"
                    style="color:#409eff;cursor:pointer;font-size:18px;margin-left:190px"
                    @click="deleteItem(index)"
                  ></i>
                </el-form-item>
                <el-form-item
                  :label="$t('adSharePolite.reward')+'：'"
                  label-width="100px"
                  prop="reward_type"
                  ref="reward_type"
                  style="padding: 10px 0"
                >
                  <el-radio-group v-model="item.reward_type">
                    <el-radio :label=1>{{$t('adSharePolite.socre')}}</el-radio>
                    <el-radio
                      :label=2
                      style="margin: 0 20px"
                    >{{$t('adSharePolite.coupon')}}</el-radio>
                    <el-radio :label=3>{{$t('adSharePolite.lottery')}}</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item
                  v-if="item.reward_type == 1"
                  :label="$t('adSharePolite.socre')+'：'"
                  :prop="`shareRules[${index}].score`"
                  ref="rewardScore"
                  label-width="100px"
                  inline-message=true
                  style="padding: 0 0 10px"
                  :rules="[
                    { required: true, validator: (rule,value, callback)=>(validateIntegral(rule, value, callback, item.score)), trigger:['blur', 'change']}
                  ]"
                >
                  <el-input
                    v-model.number="item.score"
                    size="small"
                    style="width: 150px"
                    placeholder="0"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  prop="rewardCoupon"
                  ref="rewardCoupon"
                  v-if="item.reward_type == 2"
                  :label="$t('adSharePolite.coupon')+'：'"
                  label-width="100px"
                >
                  <!-- <el-button
                    type="primary"
                    @click="handleToCallDialog(index)"
                  >{{$t('adSharePolite.addCoupon')}}</el-button> -->
                  <!-- <div
                    class="add_btn"
                    @click="handleToCallDialog(index)"
                  >{{$t('adSharePolite.addCoupon')}}</div> -->
                  <!-- hello world -->
                  <el-select
                    v-model="shareRules[index].lottery"
                    placeholder="请选择"
                    size="small"
                    style="width:150px"
                  >
                    <el-option
                      v-for="item in selectCoupon"
                      :key="item.id"
                      :lable="item.actName"
                      :value="item.actName"
                    ></el-option>
                  </el-select>
                  <el-link
                    type="primary"
                    :underline="false"
                    href="#"
                    style="margin:0 5px;"
                  >{{$t('adSharePolite.refresh')}}
                  </el-link>
                  |
                  <el-link
                    type="primary"
                    :underline="false"
                    href="#"
                    style="margin:0 5px;"
                  >{{$t('adSharePolite.createLabel')}}</el-link>
                  |
                  <el-link
                    type="primary"
                    :underline="false"
                    href="#"
                    style="margin:0 5px;"
                  >{{$t('adSharePolite.manageLabel')}}</el-link>
                  <el-row style="padding: 10px 0">
                    <el-col :offset="4">
                      {{$t('adSharePolite.couponStock')}}
                      <el-input
                        size="small"
                        style="width:70px"
                        v-model.number="item.couponStock"
                        :disabled="true"
                        placeholder="0"
                      ></el-input>
                      {{$t('adSharePolite.number')}}
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item
                  prop="rewardLottery"
                  ref="rewardLottery"
                  v-if="item.reward_type == 3"
                  :label="$t('adSharePolite.lottery')+'：'"
                  label-width="100px"
                  style="padding:0 0 10px;"
                >
                  <!-- <el-button
                    type="primary"
                    @click="handleToCallDialog(index)"
                  >{{$t('adSharePolite.addLottery')}}</el-button> -->
                  <!-- <div
                    class="add_btn"
                    @click="handleToCallDialog(index)"
                  >{{$t('adSharePolite.addLottery')}}</div> -->
                  <el-select
                    v-model="shareRules[index].lottery"
                    placeholder="请选择"
                    size="small"
                    style="width:150px"
                  >
                    <el-option
                      v-for="item in lotteryOption"
                      :key="item.id"
                      :lable="item.lotteryName"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                  <el-link
                    type="primary"
                    :underline="false"
                    href="#"
                    style="margin:0 5px;"
                  >{{$t('adSharePolite.refresh')}}
                  </el-link>
                  |
                  <el-link
                    type="primary"
                    :underline="false"
                    href="#"
                    style="margin:0 5px;"
                  >{{$t('adSharePolite.createLabel')}}</el-link>
                  |
                  <el-link
                    type="primary"
                    :underline="false"
                    href="#"
                    style="margin:0 5px;"
                  >{{$t('adSharePolite.manageLabel')}}</el-link>
                </el-form-item>
              </el-form-item>
              <el-form-item
                ref="lottery_num"
                :prop="`shareRules[${index}].lottery_num`"
                :label="$t('adSharePolite.rewwardNum')+'：'"
                label-width="100px"
                :rules="[
                  {required: true, validator:(rule, value, callback) =>{ validateRewardNumber(rule, value, callback, item.lottery_num)}, trigger: 'blur'}
                ]"
              >
                <el-input
                  size="small"
                  style="width:150px"
                  v-model.number="item.lottery_num"
                  placeholder="0"
                ></el-input>
                {{$t('adSharePolite.number')}}
              </el-form-item>
            </section>
          </el-form>
        </div>
      </section>

      <!--保存-->
      <div class="footer">
        <div
          class="save"
          @click="add"
        >{{$t('marketCommon.save')}}</div>
      </div>
    </div>

    <!--添加商品弹窗-->
    <choosingGoods
      @result="choosingGoodsResult"
      :chooseGoodsBack="goodIdList"
      :tuneUpChooseGoods="tuneUpChooseGoods"
    />
    <!--添加优惠券弹窗-->
    <!-- <addCouponDialog
      :singleElection="true"
      :tuneUpCoupon="tuneUpCoupon"
      @handleToCheck="handleToCheck"
    /> -->
  </div>
</template>
<script>
import 'swiper/dist/css/swiper.css'
import vueSwiper from 'vue-awesome-swiper'
import Vue from 'vue'
import { mapActions } from 'vuex'
import { addShareReward, getShareRewardInfo, updateShareReward } from '@/api/admin/marketManage/sharePolite.js'
import { isGoingAct } from '@/api/admin/marketManage/payReward.js'
import { getAllCoupon } from '@/api/admin/marketManage/evaluationGift.js'
import choosingGoods from '@/components/admin/choosingGoods'
import addCouponDialog from '@/components/admin/addCouponDialog'
import { coupondetail } from '@/api/admin/marketManage/couponList.js'
Vue.use(vueSwiper)
export default {
  components: {
    choosingGoods,
    addCouponDialog
  },
  data () {
    // 自定义表单字段验证
    // 有效期验证
    var checkValidityPeriod = (rule, value, callback) => {
      if (this.param.isForever === 0) {
        if (this.param.effectiveDate === null || this.param.effectiveDate === '') {
          return callback(new Error(this.$t('adSharePolite.pleaseChooseFixedDate')))
        } else {
          // 移除该表单项的校验结果
          this.$refs.effectiveDate.clearValidate()
          callback()
        }
      } else {
        // 对该表单项进行重置，将其值重置为初始值并移除校验结果
        this.$refs.effectiveDate.resetField()
        callback()
      }
    }
    // 优先级验证
    var validatorPriority = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (!value) {
        callback(new Error('请输入优先级'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    // 有效期切换验证
    var switchForever = (rule, value, callback) => {
      if (this.param.isForever === 1) {
        this.$refs.effectiveDate.resetField()
      }
      callback()
    }
    // 触发条件切换验证
    var switchCondition = (rule, value, callback) => {
      callback()
    }
    // 触发条件验证
    var checkConditionId = (rule, value, callback) => {
      if (this.param.condition === 2) {
        var re = /^(0|\+?[1-9][0-9]*)$/
        if (value === null || value === '' || value === 0) {
          return callback(new Error(this.$t('adSharePolite.pleaseAtLeastChooseOne')))
        } else if (!re.test(value)) {
          return callback((new Error('请输入0和正整数')))
        } else {
          callback()
        }
      }
    }
    var checkConditionPv = (rule, value, callback) => {
      var re = /^[0-9]*[1-9][0-9]*$/
      if (value === null || value === 0 || value === '') {
        return callback(new Error(this.$t('adSharePolite.pleaseInputPV')))
      } else if (!re.test(value)) {
        callback(new Error('请输入正整数'))
      }
      if (!Number.isInteger(value)) {
        return callback(new Error(this.$t('adSharePolite.PVMsutBeNumber')))
      } else {
        callback()
      }
    }
    return {
      // tuneUpCoupon: false,
      tuneUpChooseGoods: false,
      swiperOption: {
        loop: true,
        autoplay: {
          delay: 3000, // 自动切换的时间间隔，单位ms
          stopOnLastSlide: false, // 当切换到最后一个slide时停止自动切换
          disableOnInteraction: false, // 用户操作swiper之后，是否禁止autoplay。
          waitForTransition: true // 等待过渡完毕。自动切换会在slide过渡完毕后才开始计时。
        },
        // 分页器设置
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        }
      },
      imageUrlData: [
        { image_1: this.$imageHost + '/image/admin/share_pop1.jpg' },
        { image_2: this.$imageHost + '/image/admin/share_pop2.jpg' }
      ],
      // 添加和更新页面共用一个，此标识用于区别这两个操作，弹窗添加成功(标识为0)，更新成功(标识为1)
      flag: 0,
      lotteryOption: [],
      selectCoupon: [],
      value: '',
      // 已选择商品件数
      selectGoods: 0,
      // 分享奖励规则数组，最多定义三个规则
      shareRules: [
        {
          invite_num: '',
          reward_type: 1,
          score: '',
          coupon: '',
          lottery: '',
          score_num: '',
          coupon_num: '',
          lottery_num: '',
          // 优惠券可用库存
          couponStock: 0
        }
      ],
      index: 0,
      param: {
        id: this.$route.params.id,
        name: '',
        effectiveDate: '',
        startTime: null,
        endTime: null,
        isForever: 1,
        priority: '',
        condition: 1,
        goodsIds: '',
        goodsPv: '',
        visitFirst: false,
        shareRules: [],
        firstRule: null,
        secondRule: null,
        thirdRule: null,
        firstAwardNum: 0,
        secondAwardNum: 0,
        thirdAwardNum: 0
      },
      goodIdList: [],
      // 表单字段校验
      fieldValidation: {
        // 活动名称
        name: [
          { required: true, message: this.$t('adSharePolite.pleaseInputActivityName'), trigger: 'blur' },
          { min: 1, max: 10, message: this.$t('adSharePolite.lengthLimit'), trigger: 'blur' }
        ],
        // 有效期切换
        isForever: [
          { required: true, validator: switchForever, trigger: 'change' }
        ],
        // 日期选择
        effectiveDate: [
          { type: 'date', required: true, validator: checkValidityPeriod, trigger: ['blur', 'change'] }
        ],
        // 优先级
        priority: [
          { required: true, validator: validatorPriority, trigger: 'blur' }
        ],
        // 触发条件切换
        condition: [
          { required: true, validator: switchCondition, trigger: 'change' }
        ],
        // 商品选择
        goodsIds: [
          { required: true, validator: checkConditionId, trigger: 'blur' }
        ],
        // 访问量输入
        goodsPv: [
          { required: true, validator: checkConditionPv, trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.initForm()
    this.getIsGonigLotteryActivity()
    this.getSelectCoupon()
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 选择优惠券弹窗
    // handleToCallDialog (index) {
    //   this.index = index
    //   this.tuneUpCoupon = !this.tuneUpCoupon
    // },
    // 优惠券回调
    // handleToCheck (data) {
    //   console.log('优惠券', data)
    //   let arr = []
    //   let stock = []
    //   data.forEach(item => {
    //     arr.push(item.id)
    //     stock.push(item.surplus)
    //   })
    //   this.shareRules[this.index].coupon = arr.toString()
    //   this.shareRules[this.index].couponStock = stock.toString()
    //   console.log('conpon', arr.toString())
    // },
    // 选择商品弹窗
    showChoosingGoods () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    //  获取商品ids
    choosingGoodsResult (ids) {
      console.log('ids--', ids)
      this.param.goodsIds = ids.toString()
      console.log(this.param.goodsIds, 'goodsIds')
      this.goodIdList = ids
      this.selectGoods = ids.length
    },
    addItem () {
      let obj = {
        coupon: ''
      }
      if (this.shareRules.length < 3) {
        this.shareRules.push(obj)
      } else {
        this.$message.warning('最多可添加3个规则！')
      }
    },
    deleteItem (index) {
      console.log(this.shareRules)
      this.shareRules.splice(index, 1)
      console.log(index)
    },
    add () {
      // 分享规则处理逻辑
      this.shareRules.map((item, index) => {
        switch (this.shareRules.length) {
          case 0:
            break
          case 1:
            this.param.firstRule = this.shareRules[0]
            break
          case 2:
            this.param.firstRule = this.shareRules[0]
            this.param.secondRule = this.shareRules[1]
            break
          case 3:
            this.param.firstRule = this.shareRules[0]
            this.param.secondRule = this.shareRules[1]
            this.param.thirdRule = this.shareRules[2]
            break
        }
      })
      // 仅邀请未访问过的用户有效；0否，1是
      switch (this.param.visitFirst) {
        case true:
          this.param.visitFirst = 1
          break
        case false:
          this.param.visitFirst = 0
          break
      }
      this.param.startTime = this.param.effectiveDate[0]
      this.param.endTime = this.param.effectiveDate[1]
      console.log(JSON.parse(JSON.stringify(this.param)))
      // 字段校验，校验通过后提交表单信息
      this.$refs['param'].validate((valid) => {
        if (valid) {
          console.log('校验通过！')
          console.log(JSON.parse(JSON.stringify(this.param)))
          if (this.flag === 0) {
            delete this.param.id
            addShareReward(this.param).then((res) => {
              if (res.error === 0) {
                console.log(JSON.parse(JSON.stringify(res)))
                this.$message.success('添加成功！')
                this.$router.push({
                  name: 'share_polite'
                })
              } else {
                this.$message.error(res.message)
              }
            }).catch(() => {
              this.$message.error('操作失败')
            })
          } else if (this.flag === 1) {
            updateShareReward(this.param).then((res) => {
              if (res.error === 0) {
                console.log(JSON.parse(JSON.stringify(res)))
                this.$message.success('更新成功！')
                this.$router.push({
                  name: 'share_polite'
                })
              } else {
                this.$message.error(res.message)
              }
            }).catch(() => {
              this.$message.error('操作失败')
            })
          }
        } else {
          this.$message.error('数据不合法')
          return false
        }
      })
    },
    // 更新时初始化表单内容
    initForm () {
      if (this.param.id != null) {
        this.flag = 1
      }
      if (this.param.id != null) {
        // this.$route.params.id
        console.log(JSON.parse(JSON.stringify('route:' + this.$route.params.id)))
        console.log(JSON.parse(JSON.stringify('param:' + this.param.id)))
        getShareRewardInfo(this.param.id).then((res) => {
          console.log(JSON.parse(JSON.stringify(res)))
          if (res.error === 0) {
            this.param = res.content
            this.shareRules = res.content.shareRules
            // 获取优惠券库存
            this.shareRules.map((item, index) => {
              if (item.reward_type === 2) {
                coupondetail(item.coupon).then((res) => {
                  console.log(JSON.parse(JSON.stringify(res)))
                  item.couponStock = res.content['0'].surplus
                }).catch(() => {
                  this.$message.error('优惠券库存查询失败！')
                })
              }
            })
            this.param.effectiveDate = [this.param.startTime, this.param.endTime]
            if (res.content.condition === 2) {
              this.selectGoods = res.content.goodsIds.split(',').length
            }
          }
        }).catch(() => {
          this.$message.error('活动查询失败！')
        })
      }
    },

    // 获取优惠券下拉框数据
    getSelectCoupon () {
      getAllCoupon({
        'isHasStock': true
      }).then(res => {
        if (res.error === 0) {
          console.log(res, 'data coupon')
          this.selectCoupon = res.content
        }
      })
    },
    // 支付奖励-幸运大抽奖-下拉框选项
    getIsGonigLotteryActivity () {
      isGoingAct().then(res => {
        if (res.error === 0) {
          console.log(res, 'get data--')
          this.lotteryOption = res.content.dataList
        }
      }).catch(err => console.log(err))
    },
    // 优惠券库存查询
    getCouponStock (id) {
      console.log(JSON.parse(JSON.stringify(id)))
      coupondetail(id).then((res) => {
        console.log(JSON.parse(JSON.stringify(res)))
        console.log(JSON.parse(JSON.stringify(res.content['0'].surplus)))
        return res.content['0'].surplus
      }).catch(() => {
        this.$message.error('优惠券库存查询失败！')
      })
    },
    resetForm () {
      this.$refs['param'].resetFields()
    },
    // 校验邀请人数
    validatePersonNumber (rule, value, callback, inviteNum) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!inviteNum) {
        callback(new Error('请输入要邀请的人数'))
      } else if (!re.test(value)) {
        callback(new Error('请输入正整数'))
      } else {
        return callback()
      }
    },
    // 校验奖品份数
    validateRewardNumber (rule, value, callback, lotteryNum) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!lotteryNum) {
        callback(new Error('请输入奖品份数'))
      } else if (re.test(value)) {
        callback(new Error('请输入正整数'))
      } else {
        callback()
      }
    },
    // 验证积分
    validateIntegral (rule, value, callback, score) {
      var re = /^(0|\+?[1-9][0-9]*)$/
      if (score === '') {
        callback(new Error('请输入积分'))
      } else if (!re.test(score)) {
        callback(new Error('请填写0或者正整数'))
      } else {
        callback()
      }
    }

  }
}
</script>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  font-size: 14px;
}
.bottomNavigationContent {
  position: relative;
  height: 100%;
  width: 100%;
}
.bottomNavigationContent_main {
  display: flex;
  position: relative;
  justify-content: center;
  background-color: #fff;
  padding-bottom: 96px;
  margin: 10px;
}

.cententLleft_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  text-align: center;
  padding-top: 9px;
}
.advan_li_left {
  width: 321px;
  height: 570px;
  float: left;
}
.advan_li_left > img {
  width: 100%;
  height: 100%;
}
#slider {
  width: 100%;
}
.cententLleft {
  width: 323px;
  height: 627px;
  border: 1px solid #ccc;
  background: #eee;
  position: relative;
  float: left;
  margin: 40px 0 60px 0;
}
.right_main {
  float: left;
  margin: 40px 0 0 15px;
}
.contentRight {
  border: 1px solid #e5e5e5;
  background: #f8f8f8;
  border-radius: 3px;
  padding: 10px;
  width: 515px;
}
.actInfo_content_item {
  display: flex;
}
.tips {
  margin-top: 10px;
  line-height: 1.4;
  font-size: 12px;
  color: #999;
}
.add_btn {
  width: 120px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  color: #5a8bff;
  border: 1px solid #ccc;
  background: #fff;
  cursor: pointer;
  margin: 10px 10px 10px 0;
  display: inline-block;
}
.specify_goods {
  margin-left: 60px;
}
.actInfo {
  width: 100%;
  font-size: 14px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e5e5e5;
}
.selectGoods {
  margin-left: 190px;
  width: 120px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  border: 1px solid #ccc;
  cursor: pointer;
  color: #5a8bff;
}
.addRules {
  display: inline-block;
  width: 95px;
  height: 25px;
  line-height: 25px;
  padding: 0 5px;
  margin-top: -5px;
  border: 1px solid #5a8bff;
  color: #5a8bff;
  border-radius: 4px;
  cursor: pointer;
}
.rules_part {
  border-top: 1px dashed #ccc;
  padding: 10px 0 20px;
}
.footer {
  position: fixed;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  z-index: 2;
  background: #f2f2f2;
  text-align: center;
}
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
</style>
