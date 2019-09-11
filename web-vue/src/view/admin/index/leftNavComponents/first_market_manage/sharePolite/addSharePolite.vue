<template>
  <div class="bottomNavigationContent">
    <div class="bottomNavigationContent_main">

      <!-- 左侧内容start -->
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
      <!-- 左侧内容end  -->
      <section class="right_main">
        <!-- 活动信息部分 -->
        <div class="contentRight">
          <div class="actInfo">{{$t('adSharePolite.activityInfo')}}</div>
          <el-form
            :model="param"
            ref="param"
            label-position="right"
            label-width="100px"
            :rules="fieldValidation"
          >
            <el-form-item
              :label="$t('adSharePolite.activityInfo')"
              prop="name"
            >
              <el-input
                size="mini"
                style="width:200px"
                v-model="param.name"
                :placeholder="$t('adSharePolite.namelimit')"
              ></el-input>
            </el-form-item>
            <br>
            <el-form-item
              :label="$t('adSharePolite.validityPeriod')"
              prop="isForever"
            >
              <el-radio-group v-model="param.isForever">
                <el-form-item>
                  <el-radio :label=0>{{$t('adSharePolite.fixTime')}}</el-radio>
                  <el-radio :label=1>{{$t('adSharePolite.forever')}}</el-radio>
                </el-form-item>
                <el-form-item
                  prop="effectiveDate"
                  ref="effectiveDate"
                >
                  <el-date-picker
                    v-if="this.param.isForever == 0"
                    v-model="param.effectiveDate"
                    style="width: 300px;"
                    type="datetimerange"
                    :range-separator=" $t('marketCommon.to') "
                    :start-placeholder="$t('marketCommon.startingTime')"
                    :end-placeholder="$t('marketCommon.endTime')"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    size="small"
                  >
                  </el-date-picker>
                </el-form-item>
                <br>
              </el-radio-group>
            </el-form-item>
            <el-form-item
              :label="$t('adSharePolite.priority')"
              prop="priority"
            >
              <el-input
                size="mini"
                style="width:200px"
                v-model.number="param.priority"
                placeholder="0"
              ></el-input>
              <br>
              <span>{{$t('adSharePolite.priorityComment')}}</span>
            </el-form-item>
            <br>
            <el-form-item
              :label="$t('adSharePolite.condition')"
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
              <el-button
                type="success"
                @click="showChoosingGoods"
              >{{$t('adSharePolite.chooseGoods')}}</el-button>
              <span>{{$t('adSharePolite.alreadyChoose')}}{{selectGoods}}{{$t('adSharePolite.goods')}}</span>
            </el-form-item>
            <el-form-item
              prop="goodsPv"
              ref="goodsPv"
              v-if="param.condition == 3"
            >
              <span>{{$t('adSharePolite.pvInFact')}}</span>
              <el-input
                size="mini"
                style="width:50px"
                v-model.number="param.goodsPv"
                placeholder="0"
              ></el-input> {{$t('adSharePolite.pvGoods')}}
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
          <el-form>
            <el-form-item>
              <el-checkbox v-model="param.visitFirst">{{$t('adSharePolite.visitNew')}}</el-checkbox>
            </el-form-item>

            <section
              v-for="(item,index) in shareRule"
              :key="index"
            >
              <el-form-item :label="(index+1)+$t('adSharePolite.level')">
                <div>{{$t('adSharePolite.invite')}} <el-input
                    size="mini"
                    style="width:60px"
                    v-model="item.invite_num"
                    placeholder="0"
                  ></el-input> {{$t('adSharePolite.person')}} <span style="color:#999">{{$t('adSharePolite.invireNum')}}</span>
                  <i
                    v-if="index>0"
                    class="el-icon-delete"
                    style="color:#409eff;cursor:pointer"
                    @click="deleteItem(index)"
                  ></i>
                </div>
                <div style="margin-left:43px">{{$t('adSharePolite.reward')}}
                  <el-radio-group v-model="item.reward_type">
                    <el-radio :label=1>{{$t('adSharePolite.socre')}}</el-radio>
                    <el-radio :label=2>{{$t('adSharePolite.coupon')}}</el-radio>
                    <el-radio :label=3>{{$t('adSharePolite.lottery')}}</el-radio>
                  </el-radio-group>
                </div>
                <div
                  style="margin-left:43px"
                  v-if="item.reward_type == 1"
                >{{$t('adSharePolite.socre')}}：
                  <el-input
                    v-model.number="item.score"
                    size="mini"
                    style="width: 150px"
                    placeholder="0"
                  ></el-input>
                </div>

                <div v-if="item.reward_type == 2">
                  <div style="margin-left:43px;margin-top: 10px;display:flex">
                    <div style="height:30px;line-height:30px">{{$t('adSharePolite.coupon')}}：</div>
                    <el-button @click="handleToCallDialog(index)">{{$t('adSharePolite.addCoupon')}}</el-button>
                    <div style="height:30px; line-height:30px">
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
                    </div>
                  </div>
                  <div style="margin-left: 120px">{{$t('adSharePolite.couponStock')}}{{couponNum}}{{$t('adSharePolite.number')}}</div>
                </div>

                <div v-if="item.reward_type == 3">
                  <div style="margin-left:43px;margin-top: 10px;display:flex">
                    <div style="height:30px;line-height:30px">{{$t('adSharePolite.lottery')}}：</div>
                    <el-button @click="handleToCallDialog(index)">{{$t('adSharePolite.addLottery')}}</el-button>
                    <div style="height:30px; line-height:30px">
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
                    </div>
                  </div>
                </div>
                <div style="margin-left:43px">
                  {{$t('adSharePolite.rewwardNum')}}
                  <el-input
                    size="mini"
                    style="width:150px"
                    v-model.number="item.totalNum"
                    placeholder="0"
                  ></el-input>{{$t('adSharePolite.number')}}</div>
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
    <choosingGoods @resultGoodsIds="choosingGoodsResult" />
    <!--添加优惠卷弹窗-->
    <addCouponDialog
      :singleElection="true"
      @handleToCheck="handleToCheck"
    />
  </div>
</template>
<script>
import 'swiper/dist/css/swiper.css'
import vueSwiper from 'vue-awesome-swiper'
import Vue from 'vue'
import { mapActions } from 'vuex'
import { addShareReward } from '@/api/admin/marketManage/sharePolite.js'
import choosingGoods from '@/components/admin/choosingGoods'
import addCouponDialog from '@/components/admin/addCouponDialog'
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
      // 延迟显示提示信息
      // setTimeout(() => {
      //   if (this.param.isForever === 0) {
      //     if (this.effectiveDate === null || this.effectiveDate === '') {
      //       return callback(new Error('请选择固定日期'))
      //     }
      //   }
      // }, 1000)
      if (this.param.isForever === 0) {
        if (this.param.effectiveDate === null || this.param.effectiveDate === '') {
          return callback(new Error(this.$t('adSharePolite.pleaseChooseFixedDate')))
        } else {
          // 移除该表单项的校验结果
          this.$refs.effectiveDate.clearValidate()
        }
      } else {
        // 对该表单项进行重置，将其值重置为初始值并移除校验结果
        this.$refs.effectiveDate.resetField()
      }
    }
    // 有效期切换验证
    var switchForever = (rule, value, callback) => {
      if (this.param.isForever === 1) {
        this.$refs.effectiveDate.resetField()
      }
    }
    // 触发条件切换验证
    var switchCondition = (rule, value, callback) => {
      if (value === 1) {
        this.$refs.goodsIds.clearValidate()
        this.$refs.goodsPv.clearValidate()
      }
      if (value === 2) {
        this.$refs.goodsPv.clearValidate()
      }
      if (value === 3) {
        this.$refs.goodsIds.clearValidate()
      }
    }
    // 触发条件验证
    var checkConditionId = (rule, value, callback) => {
      if (this.param.condition === 2) {
        if (value === null || value === '') {
          return callback(new Error(this.$t('adSharePolite.pleaseAtLeastChooseOne')))
        } else {
          value.clearValidate()
          // this.$refs.goodsIds.clearValidate()
        }
      }
    }
    var checkConditionPv = (rule, value, callback) => {
      if (this.param.condition === 3) {
        if (value === null || value === 0) {
          return callback(new Error(this.$t('adSharePolite.pleaseInputPV')))
        } else {
          value.clearValidate()
        }
        if (!Number.isInteger(value)) {
          return callback(new Error(this.$t('adSharePolite.PVMsutBeNumber')))
        }
      }
    }
    return {
      swiperOption: {
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
      options: [{
        value: '1',
        label: '黄金糕'
      }, {
        value: '2',
        label: '双皮奶'
      }],
      value: '',
      // 已选择商品件数
      selectGoods: 0,
      // 优惠券可用库存
      couponNum: 0,
      // 分享奖励规则数组，最多定义三个规则
      shareRule: [
        {
          invite_num: '',
          reward_type: 1,
          score: '',
          coupon: '',
          lottery: '',
          totalNum: '',
          score_num: '',
          coupon_num: '',
          lottery_num: ''
        }
      ],
      index: 0,
      param: {
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
        firstRule: null,
        secondRule: null,
        thirdRule: null,
        firstAwardNum: 0,
        secondAwardNum: 0,
        thirdAwardNum: 0
      },
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
          { type: 'date', required: true, validator: checkValidityPeriod, trigger: 'change' }
        ],
        // 优先级
        priority: [
          { required: true, message: this.$t('adSharePolite.priorityNotNull'), trigger: 'blur' },
          { type: 'number', message: this.$t('adSharePolite.priorityMushBeNumber'), trigger: 'blur' }
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
        ],
        // 积分
        score: [
          { required: true, message: this.$t('adSharePolite.scoreNotNull'), trigger: 'blur' },
          { type: 'number', message: this.$t('adSharePolite.scoreMushBeNumber'), trigger: 'blur' }
        ],
        // 优惠券
        coupon: [
          { required: true, message: this.$t('adSharePolite.pleaseChooseCoupon'), trigger: 'blur' }
        ],
        // 幸运大抽奖
        lottery: [
          { required: true, message: this.$t('adSharePolite.pleaseChooseLottery'), trigger: 'blur' }
        ],
        // 奖品份数
        totalNum: [
          { required: true, message: this.$t('adSharePolite.rewardNumNotNull'), trigger: 'blur' },
          { type: 'number', message: this.$t('adSharePolite.rewardNumMushBeNumber'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 选择优惠券弹窗
    handleToCallDialog (index) {
      this.index = index
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.couponList
      }
      this.$http.$emit('V-AddCoupon', obj, 'choiseOne')
    },
    // 优惠卷回调
    handleToCheck (data) {
      console.log('优惠卷', data)
      let arr = []
      let stock = []
      data.forEach(item => {
        arr.push(item.id)
        stock.push(item.remainAmount)
      })
      this.shareRule[this.index].coupon = arr.toString()
      this.couponNum = arr[0]
      console.log('conpon', arr.toString())
    },
    // 选择商品弹窗
    showChoosingGoods () {
      this.transmitEditGoodsId(this.param.goodsIds)
      this.$http.$emit('choosingGoodsFlag', true)
    },
    //  获取商品ids
    choosingGoodsResult (ids) {
      console.log('获取商品行信息', ids)
      this.param.goodsIds = ids.toString()
      this.selectGoods = ids.length
    },
    addItem () {
      let obj = {
        coupon: ''
      }
      if (this.shareRule.length < 3) {
        this.shareRule.push(obj)
      } else {
        alert('最多可添加3个规则！')
      }
    },
    deleteItem (index) {
      console.log(this.shareRule)
      this.shareRule.splice(index, 1)
      console.log(index)
    },
    add () {
      // 分享规则处理逻辑
      this.shareRule.map((item, index) => {
        switch (item.reward_type) {
          case 1:
            item.score_num = item.totalNum
            break
          case 2:
            item.coupon_num = item.totalNum
            break
          case 3:
            item.lottery_num = item.totalNum
            break
        }
        switch (this.shareRule.length) {
          case 0:
            break
          case 1:
            this.param.firstAwardNum = this.shareRule[0].totalNum
            this.param.firstRule = this.shareRule[0]
            break
          case 2:
            this.param.firstAwardNum = this.shareRule[0].totalNum
            this.param.firstRule = this.shareRule[0]
            this.param.secondAwardNum = this.shareRule[1].totalNum
            this.param.secondRule = this.shareRule[1]
            break
          case 3:
            this.param.firstAwardNum = this.shareRule[0].totalNum
            this.param.firstRule = this.shareRule[0]
            this.param.secondAwardNum = this.shareRule[1].totalNum
            this.param.secondRule = this.shareRule[1]
            this.param.thirdAwardNum = this.shareRule[2].totalNum
            this.param.thirdRule = this.shareRule[2]
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
          addShareReward(this.param).then((res) => {
            console.log(JSON.parse(JSON.stringify(res)))
          }).catch(() => {
            this.$message.error('操作失败')
          })
        } else {
          this.$message.error('数据不合法')
          return false
        }
      })
    },
    resetForm () {
      this.$refs['param'].resetFields()
    }
  }
}
</script>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  font-size: 12px;
}
.bottomNavigationContent {
  position: relative;
  height: 100%;
  width: 100%;
}
.bottomNavigationContent_main {
  background-color: #fff;
  height: 100%;
  overflow: hidden;
  overflow-y: auto;
  padding-bottom: 96px;
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
  margin: 70px 0 0 224px;
}
.right_main {
  float: left;
  margin: 80px 0 0 30px;
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
.actInfo {
  width: 100%;
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
  border: 1px solid #5a8bff;
  color: #5a8bff;
  border-radius: 4px;
  cursor: pointer;
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
