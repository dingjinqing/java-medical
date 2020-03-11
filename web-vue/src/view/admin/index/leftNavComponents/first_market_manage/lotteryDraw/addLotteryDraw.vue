<template>
  <div class="container">

    <!-- 表单 -->
    <div>
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="130px"
        :label-position="'right'"
      >
        <el-form-item
          :label="$t('lotteryDraw.activityName') + '：'"
          prop="name"
        >
          <el-input
            size="small"
            v-model="form.name"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.validDate') + '：'"
          prop="validity"
        >
          <el-date-picker
            :disabled="this.isEdite"
            v-model="form.validity"
            type="datetimerange"
            :range-separator="$t('seckill.to')"
            :start-placeholder="$t('seckill.startTime')"
            :end-placeholder="$t('seckill.endTime')"
            :default-time="['00:00:00','23:59:59']"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
          >
          </el-date-picker>
          <span class="tips">{{ $t('lotteryDraw.validDateTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.joinNum') + '：'"
          prop="minJoinNum"
        >
          <el-input-number
            :disabled="this.isEdite"
            size="small"
            :min="0"
            v-model="form.minJoinNum"
            class="inputWidth"
            controls-position="right"
          ></el-input-number>
          <span class="tips">{{ $t('lotteryDraw.joinNumTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.payMoney') + '：'"
          prop="payMoney"
        >
          <el-input
            :disabled="this.isEdite"
            size="small"
            v-model="form.payMoney"
            class="inputWidth"
          ></el-input>
          <span>{{ $t('lotteryDraw.money') }}</span>
          <span class="tips">{{ $t('lotteryDraw.payMoneyTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.maxJinNum') + '：'"
          prop="joinLimit"
        >
          <el-input-number
            :disabled="this.isEdite"
            size="small"
            :min="0"
            v-model="form.joinLimit"
            class="inputWidth"
            controls-position="right"
          ></el-input-number>
          <span class="tips">{{ $t('lotteryDraw.maxJinNumTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.openLimit') + '：'"
          prop="openLimit"
        >
          <el-input-number
            :disabled="this.isEdite"
            size="small"
            :min="0"
            v-model="form.openLimit"
            class="inputWidth"
            controls-position="right"
          ></el-input-number>
          <span class="tips">{{ $t('lotteryDraw.openLimitTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.limitAmount') + '：'"
          prop="limitAmount"
        >
          <el-input-number
            :disabled="this.isEdite"
            size="small"
            :min="0"
            v-model="form.limitAmount"
            class="inputWidth"
            controls-position="right"
          ></el-input-number>
          <span class="tips">{{ $t('lotteryDraw.limitAmountTip') }}</span>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.toNumShow') + '：'"
          prop="toNumShow"
        >
          <el-input-number
            :disabled="this.isEdite"
            size="small"
            :min="0"
            v-model="form.toNumShow"
            class="inputWidth"
            controls-position="right"
          ></el-input-number>
          <span class="tips">{{ $t('lotteryDraw.toNumShowTip') }}</span>
          <el-popover
            placement="right-start"
            width="220"
            trigger="hover"
          >
            <el-image src="http://jmpdevimg.weipubao.cn/image/admin/new_preview_image/pin_lottery.jpg"></el-image>
            <el-button
              slot="reference"
              type="text"
              style="margin: 0 20 0 0px"
            >{{ $t('lotteryDraw.example') }}</el-button>
          </el-popover>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.reward') + '：'"
          prop=""
        >
          <el-card class="box-card">
            <div class="fontColor">{{$t('groupBuy.consolationPrizeComment1')}}</div>
            <div class="middleContainer">
              <div
                v-for="(item,index) in rewardCouponList"
                :key="index"
                class="rewardCouponInfo"
                style="margin-right: 5px;"
              >
                <section
                  class="couponImgWrapper"
                  style="line-height: normal"
                >
                  <div
                    class="coupon_list_top"
                    v-if="item.actCode==='voucher'"
                  >
                    <span>￥</span>
                    <span class="number">{{item.denomination}}</span>
                  </div>
                  <div
                    class="coupon_list_top"
                    v-if="item.actCode==='discount'"
                  >
                    <span>{{item.denomination}}</span>
                    <span>{{$t('payReward.discount')}}</span>
                  </div>
                  <div class="coupon_center_limit">{{item.useConsumeRestrict | formatLeastConsume(item.leastConsume)}}</div>
                  <div
                    class="coupon_center_number"
                    v-if="item.surplus !==0"
                  >{{ $t('lotteryDraw.rewardTip1') }}{{item.surplus}}{{ $t('lotteryDraw.rewardTip2') }}</div>
                  <div
                    class="coupon_center_number"
                    v-if="item.surplus ===0"
                  >{{ $t('lotteryDraw.rewardTip3') }}</div>
                  <div
                    class="coupon_list_bottom"
                    :style="`background-image: url(${$imageHost}/image/admin/coupon_border.png)`"
                  >
                    <span v-if="item.scoreNumber === 0">{{ $t('lotteryDraw.rewardTip4') }}</span>
                    <div v-if="item.scoreNumber !== 0">
                      <span>{{item.scoreNumber}}</span>{{ $t('lotteryDraw.rewardTip5') }}
                    </div>
                  </div>
                </section>
                <span
                  class="deleteIcon"
                  @click="deleteCouponImg(index)"
                >
                  <img
                    :src="imgHost+'/image/admin/sign_del.png'"
                    alt=""
                  >
                </span>
              </div>

              <div
                class="rewardCouponInfo"
                @click="handleToCallDialog()"
                v-if="rewardCouponList.length<5 && !this.isEdite"
                style="line-height:normal"
              >
                <div>
                  <el-image
                    fit="scale-down"
                    :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                    style="width:78px;height:78px;cursor:pointer;"
                  ></el-image>
                </div>
                <br>
                <p class="textDesc">{{$t('groupBuy.addCoupon')}}</p>
              </div>
            </div>
            <div class="fontColor">{{$t('groupBuy.consolationPrizeComment2')}}</div>
          </el-card>
        </el-form-item>
        <el-form-item
          :label="$t('lotteryDraw.goodsId') + '：'"
          prop="goodsIds"
        >
          <div>
            <el-button
              v-if="!this.isEdite"
              class="el-icon-plus"
              size="small"
              @click="showChoosingGoods"
            >{{ $t('lotteryDraw.selectGood') }}</el-button>
            <span
              class="tips "
              style="color: #5a8bff"
              @click="onlyShowChoosingGoods"
            >{{ $t('lotteryDraw.goodsTip1') }}{{this.goodsRow.length}}{{ $t('lotteryDraw.goodsTip2') }}</span>
          </div>

          <div v-if="form.goodsIds.length > 0">
            <el-table
              class="version-manage-table"
              header-row-class-name="tableClss"
              :data="goodsRow"
              border
              style="width: 100%; margin-top: 10px;"
            >
              <el-table-column
                :label="$t('lotteryDraw.goodsName')"
                prop="goodsName"
                align="center"
              ></el-table-column>
              <el-table-column
                :label="$t('lotteryDraw.goodsPrice')"
                prop="shopPrice"
                align="center"
              ></el-table-column>

              <el-table-column
                :label="$t('lotteryDraw.goodsNumber')"
                prop="goodsNumber"
                align="center"
              ></el-table-column>
              <el-table-column
                :label="$t('lotteryDraw.option')"
                align="center"
                v-if="!this.isEdite"
              >
                <template slot-scope="scope">
                  <span
                    @click="deleteGoods(scope.$index)"
                    style="color: #5a8bff;cursor: pointer;"
                  >{{ $t('lotteryDraw.delete') }}</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-form-item>
      </el-form>

    </div>

    <!-- 底部 -->
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        :disabled="submitStatus"
        @click="saveClickHandler"
      >{{ $t('seckill.save') }}</el-button>
    </div>

    <!--添加优惠券-->
    <addCouponDialog
      @handleToCheck="handleToCheck"
      :tuneUpCoupon="showCouponDialog"
      :couponBack="couponIdList"
    />

    <!--添加商品弹窗-->
    <!-- :loadProduct="true" -->
    <choosingGoods
      :checkedNumMax="20"
      :chooseGoodsBack="form.goodsIds"
      :tuneUpChooseGoods="isShowChoosingGoodsDialog"
      :onlyShowChooseGoods="isOnlyShowChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
    />

  </div>
</template>
<script>
// 引入组件
import addCouponDialog from '@/components/admin/addCouponDialog'
import choosingGoods from '@/components/admin/choosingGoods'
import { addLotteryDraw, getLotteryDetail, updateLotteryDraw } from '@/api/admin/marketManage/lotteryDraw.js'
import { getSelectGoods } from '@/api/admin/marketManage/distribution.js'
import { updateCoupon } from '@/api/admin/marketManage/couponList.js'
export default {
  components: {
    addCouponDialog,
    choosingGoods
  },
  props: ['isEdite', 'editId'],
  filters: {
    formatLeastConsume (useConsumeRestrict, leastConsume) {
      if (useConsumeRestrict === 0) {
        return `不限制`
      } else {
        return `满${leastConsume}元可用`
      }
    }
  },
  data () {
    // 自定义校验
    var validatePayMoney = (rule, value, callback) => {
      var re = /^\d+(\.\d{1,2})?$/
      if (!value) {
        callback(new Error('请填写商品金额'))
      } else if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else {
        callback()
      }
    }
    return {
      // 表单
      form: {
        name: '', // 活动名称
        validity: '', // 有效期
        startTime: '', // 开始时间
        endTime: '', // 结束时间
        minJoinNum: 100, // 奖池最小人数
        joinLimit: 1, // 最大参团数量
        payMoney: 5, // 商品金额
        openLimit: 1, // 最大开团数量
        limitAmount: 5, // 最小成团人数
        toNumShow: 100, // 最小展示人数
        rewardCouponIds: [], // 拼团失败送优惠券id
        goodsIds: [] // 参与活动的商品id
      },
      // 校验表单
      fromRules: {
        name: [
          { required: true, message: '请填写活动名称', trigger: 'blur' }
        ],
        validity: [
          { required: true, message: '请填写有效期', trigger: 'change' }
        ],
        minJoinNum: [
          { required: true, message: '请填写奖池最少人数', trigger: 'blur' }
        ],
        payMoney: [
          { validator: validatePayMoney, trigger: 'blur' }
        ],
        joinLimit: [
          { required: true, message: '请填写最大参团数量', trigger: 'blur' }
        ],
        openLimit: [
          { required: true, message: '请填写最大开团数量', trigger: 'blur' }
        ],
        limitAmount: [
          { required: true, message: '请填写最小成团人数', trigger: 'blur' }
        ],
        toNumShow: [
          { required: true, message: '请填写最小展示人数', trigger: 'blur' }
        ],
        goodsIds: [
          { required: true, message: '请选择活动商品', trigger: 'change' }
        ]
      },
      submitStatus: false, // 提交

      showCouponDialog: false, // 添加优惠券弹窗
      rewardCouponList: [], // 优惠券列表
      couponIdList: [],
      imgHost: `${this.$imageHost}`,

      isShowChoosingGoodsDialog: false, // 商品弹窗
      isOnlyShowChooseGoods: false,
      goodsRow: [] // 活动商品

    }
  },
  mounted () {
    // 编辑初始化
    if (this.isEdite === true) {
      this.editLotteryInit()
    }
  },
  watch: {
    lang () {

    }
  },
  methods: {
    // 编辑初始化
    editLotteryInit () {
      getLotteryDetail(this.editId).then((res) => {
        if (res.error === 0) {
          this.form = res.content
          // 有效期
          this.form.validity = [this.form.startTime, this.form.endTime]

          // 商品信息
          this.form.goodsIds.forEach((item, index) => {
            this.getGoodsInfo(item)
          })

          // 优惠券信息
          this.form.couponIds.forEach((item, index) => {
            this.getCouponInfo(item)
          })

          console.log(this.goodsRow)
        }
      })
    },

    // 获取商品信息
    getGoodsInfo (id) {
      getSelectGoods({ goodsId: id }).then((res) => {
        if (res.error === 0) {
          this.goodsRow.push(res.content)
        }
      })
    },

    // 获取优惠券信息
    getCouponInfo (id) {
      updateCoupon(id).then((res) => {
        if (res.error === 0) {
          this.rewardCouponList.push(res.content[0])
        }
      })
    },

    // 保存秒杀活动
    saveClickHandler () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.submitStatus = true

          // 有效期
          this.form.startTime = this.form.validity[0]
          this.form.endTime = this.form.validity[1]
          console.log(this.form)

          if (this.isEdite === false) {
            // 添加拼团抽奖
            addLotteryDraw(this.form).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: this.$t('lotteryDraw.addSuccess') })
                this.$emit('addLotterySubmit')
              } else {
                this.$message.warning({ message: res.message })
              }
            })
          } else {
            // 编辑拼团抽奖
            updateLotteryDraw(this.form).then((res) => {
              if (res.error === 0) {
                this.$message.success({ message: this.$t('lotteryDraw.editSuccess') })
                this.$emit('addLotterySubmit')
              } else {
                this.$message.warning({ message: res.message })
              }
            })
          }
        }
      })
      this.submitStatus = false
    },

    // 优惠券点击事件
    handleToCallDialog () {
      this.showCouponDialog = !this.showCouponDialog
    },

    // 确认选择优惠券-新增
    handleToCheck (data, index) {
      console.log(data, 'coupon data---', index, 'index---')
      this.couponIdList = data.map(item => item.id)
      if (this.rewardCouponList.length < 5) {
        this.rewardCouponList = data
        this.form.rewardCouponIds = []
        this.rewardCouponList.map((item, index) => {
          this.form.rewardCouponIds.push(item.id)
        })
      } else {
        this.$message.warning('优惠券不能超过五张')
        return false
      }
    },

    // 删除鼓励奖优惠券图片
    deleteCouponImg (index) {
      this.rewardCouponList.splice(index, 1)
      this.form.rewardCouponIds.splice(index, 1)
      this.couponIdList.splice(index, 1)
    },

    // 选择商品弹窗
    showChoosingGoods () {
      this.isOnlyShowChooseGoods = false
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 选择商品弹窗-部分显示
    onlyShowChoosingGoods () {
      this.isOnlyShowChooseGoods = true
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },

    // 商品弹窗的回调函数
    choosingGoodsResult (row) {
      this.form.goodsIds = []
      this.goodsRow = row
      this.form.goodsIds = []
      console.log('商品弹窗行信息回显', row)
      this.goodsRow.forEach((item, index) => {
        this.form.goodsIds.push(item.goodsId)
        // item.name = item.goodsName + item.prdDesc
      })
    },

    // 删除商品
    deleteGoods (index) {
      this.goodsRow.splice(index, 1)
      this.form.goodsIds.splice(index, 1)
    }

  }
}
</script>
<style scoped>
.container {
  margin-top: 10px;
  padding: 10px;
  margin-bottom: 100px;
  background: #fff;
}
.footer {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 99;
}
.inputWidth {
  width: 170px;
}
.tips {
  color: #999;
}
.box-card {
  width: 630px;
  background: #f5f5f5;
}
.fontColor {
  color: #606266;
}
.middleContainer {
  display: flex;
}
.rewardCouponInfo {
  display: inline-block;
  position: relative;
  width: 100px;
  height: 96px;
  margin-bottom: 10px;
  background: #fff;
  border: 1px solid #e4e4e4;
  cursor: pointer;
  text-align: center;
  border-radius: 10px;
}
.couponImgWrapper {
  width: 100%;
  height: 100%;
  border: 1px solid #fbb;
  border-radius: 10px;
}
.coupon_list_top {
  margin-top: 10px;
  color: #f60;
}
.coupon_center_limit {
  height: 20px;
  color: #f60;
  font-size: 12px !important;
}
.coupon_center_number {
  height: 20px;
  color: #fbb;
}
.coupon_list_bottom {
  height: 24px;
  line-height: 30px;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
  color: #fff;
  background: #f66;
  font-size: 12px;
  /* background-image: url("http://mpdevimg2.weipubao.cn/image/admin/coupon_border.png"); */
  background-repeat: repeat-x;
}
.deleteIcon {
  position: relative;
  width: 17px;
  height: 17px;
  top: -110px;
  left: 45px;
  cursor: pointer;
  opacity: 0.8;
  /* color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  text-align: center; */
}
.textDesc {
  line-height: normal;
  margin-top: -38px;
  color: #999;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
</style>
