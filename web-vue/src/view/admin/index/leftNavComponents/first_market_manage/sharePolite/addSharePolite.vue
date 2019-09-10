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
      <!-- 活动信息部分 -->
      <div class="contentRight">
        <div class="actInfo">活动信息</div>
        <el-form
          :model="param"
          ref="param"
          label-position="right"
          label-width="100px"
          :rules="fieldValidation"
        >
          <el-form-item
            label="活动名称:"
            prop="name"
          >
            <el-input
              size="mini"
              style="width:200px"
              v-model="param.name"
              placeholder="最多支持10汉字"
            ></el-input>
          </el-form-item>
          <br>
          <br>
          <el-form-item
            label="活动有效期:"
            prop="isForever"
          >
            <el-radio-group v-model="param.isForever">
              <el-form :inline="true">
                <el-form-item>
                  <el-radio :label=0>固定时间</el-radio>
                  <el-date-picker
                    v-if="this.param.isForever == 0"
                    v-model="effectiveDate"
                    style="width: 300px;"
                    type="datetimerange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    size="small"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-form>
              <el-row>
                <el-radio :label=1>永久有效</el-radio>
              </el-row>
            </el-radio-group>
          </el-form-item>
          <br>
          <el-form-item
            label="优先级:"
            prop="priority"
          >
            <el-input
              size="mini"
              style="width:200px"
              v-model.number="param.priority"
              placeholder="0"
            ></el-input>
            <br>
            <span>用于区分不同分享有礼活动的优先级，请填写正整数，数值越大优先级越高</span>
          </el-form-item>
          <br>
          <el-form-item
            label="触发条件："
            prop="condition"
          >
            <span>用户分享</span>
            <el-radio-group v-model="param.condition">
              <el-radio :label=1>全部商品</el-radio>
              <el-radio :label=2>指定商品</el-radio>
              <el-radio :label=3>实际访问量较少商品</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-if="param.condition == 2">
            <el-button
              type="success"
              @click="showChoosingGoods"
            >+选择商品</el-button>
            <span>已选：{{selectGoods}}件商品</span>
          </el-form-item>
          <el-form-item v-if="param.condition == 3">
            <span>实际访问量少于</span>
            <el-input
              size="mini"
              style="width:50px"
              v-model.number="param.goodsPv"
              placeholder="0"
            ></el-input> 条的商品
          </el-form-item>
        </el-form>
      </div>
      <!-- 分享奖励部分 -->
      <div
        class="contentRight"
        style="margin-top:10px;"
      >
        <div style="display:flex;border-bottom:1px solid #e5e5e5;">
          <div class="actInfo">分享奖励</div>
          <div style="display:flex">
            <span style="width: 100px">最多可添加三级</span>
            <span
              class="addRules"
              @click="addItem()"
            >+ 添加规则</span>
          </div>
        </div>
        <el-form>
          <el-form-item>
            <el-checkbox v-model="param.visitFirst">仅邀请未访问过店铺的用户有效</el-checkbox>
          </el-form-item>

          <section
            v-for="(item,index) in shareRule"
            :key="index"
          >
            <el-form-item :label="(index+1)+'级'">
              <div>邀请满 <el-input
                  size="mini"
                  style="width:60px"
                  v-model="item.invite_num"
                  placeholder="0"
                ></el-input> 人 <span style="color:#999">可填写1-5人</span>
                <i
                  v-if="index>0"
                  class="el-icon-delete"
                  style="color:#409eff;cursor:pointer"
                  @click="deleteItem(index)"
                ></i>
              </div>
              <div style="margin-left:43px">可获得
                <el-radio-group v-model="item.reward_type">
                  <el-radio :label=1>积分</el-radio>
                  <el-radio :label=2>优惠券</el-radio>
                  <el-radio :label=3>幸运大抽奖</el-radio>
                </el-radio-group>
              </div>
              <div
                style="margin-left:43px"
                v-if="item.reward_type == 1"
              >积分：
                <el-input
                  v-model.number="item.score"
                  size="mini"
                  style="width: 150px"
                  placeholder="0"
                ></el-input>
              </div>

              <div v-if="item.reward_type == 2">
                <div style="margin-left:43px;margin-top: 10px;display:flex">
                  <div style="height:30px;line-height:30px">优惠券：</div>
                  <el-button @click="handleToCallDialog(index)">添加优惠卷</el-button>
                  <div style="height:30px; line-height:30px">
                    <el-link
                      type="primary"
                      :underline="false"
                      href="#"
                      style="margin:0 5px;"
                    >刷新
                    </el-link>
                    |
                    <el-link
                      type="primary"
                      :underline="false"
                      href="#"
                      style="margin:0 5px;"
                    >新建标签</el-link>
                    |
                    <el-link
                      type="primary"
                      :underline="false"
                      href="#"
                      style="margin:0 5px;"
                    >管理标签</el-link>
                  </div>
                </div>
                <div style="margin-left: 120px">优惠券可用库存{{couponNum}}份数</div>
              </div>

              <div v-if="item.reward_type == 3">
                <div style="margin-left:43px;margin-top: 10px;display:flex">
                  <div style="height:30px;line-height:30px">幸运大抽奖：</div>
                  <el-button @click="handleToCallDialog(index)">添加幸运大抽奖</el-button>
                  <div style="height:30px; line-height:30px">
                    <el-link
                      type="primary"
                      :underline="false"
                      href="#"
                      style="margin:0 5px;"
                    >刷新
                    </el-link>
                    |
                    <el-link
                      type="primary"
                      :underline="false"
                      href="#"
                      style="margin:0 5px;"
                    >新建标签</el-link>
                    |
                    <el-link
                      type="primary"
                      :underline="false"
                      href="#"
                      style="margin:0 5px;"
                    >管理标签</el-link>
                  </div>
                </div>
              </div>

              <div style="margin-left:43px">
                奖品份数
                <el-input
                  size="mini"
                  style="width:150px"
                  v-model.number="item.totalNum"
                  placeholder="0"
                ></el-input>份</div>
            </el-form-item>
          </section>
        </el-form>
      </div>
      <!--保存-->
      <div class="footer">
        <div
          class="save"
          @click="add"
        >保存</div>
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
      if (this.param.isForever === 0) {
        if (this.effectiveDate === null || this.effectiveDate === '') {
          return callback(new Error('请选择固定日期'))
        }
      }
    }
    // 触发条件验证
    var checkCondition = (rule, value, callback) => {
      if (this.param.condition === 2) {
        if (this.param.goodsIds === null || this.param.goodsIds === '') {
          return callback(new Error('请至少选择一件商品'))
        }
      }
      if (this.param.condition === 3) {
        if (this.param.goodsPv === null || this.param.goodsPv === '') {
          return callback(new Error('请输入访问量！'))
        }
        if (!Number.isInteger(this.param.goodsPv)) {
          callback(new Error('访问量必须为数字值'))
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
      effectiveDate: '',
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
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        // 有效期
        isForever: [
          { validator: checkValidityPeriod, trigger: 'blur' }
        ],
        // 有效期为固定时间时不能为空
        effectiveDate: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        // 优先级
        priority: [
          { required: true, message: '优先级不能为空' },
          { type: 'number', message: '优先级必须为数字值' }
        ],
        // 触发条件
        condition: [
          { validator: checkCondition, trigger: 'blur' }
        ],
        // 积分
        score: [
          { required: true, message: '积分不能为空' },
          { type: 'number', message: '积分必须为数字值' }
        ],
        // 优惠券
        coupon: [
          { required: true, message: '请选择优惠券' },
          { type: 'number', message: '积分必须为数字值' }
        ],
        // 幸运大抽奖
        lottery: [
          { required: true, message: '请选择幸运大抽奖' },
          { type: 'number', message: '积分必须为数字值' }
        ],
        // 奖品份数
        totalNum: [
          { required: true, message: '奖品份数不能为空' },
          { type: 'number', message: '奖品份数必须为数字值' }
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
      this.param.startTime = this.effectiveDate[0]
      this.param.endTime = this.effectiveDate[1]
      console.log(JSON.parse(JSON.stringify(this.param)))

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
  float: left;
  margin: 80px 0 0 30px;
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
