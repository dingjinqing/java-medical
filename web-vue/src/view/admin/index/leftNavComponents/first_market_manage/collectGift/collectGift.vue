<template>
  <div class="collectGift">
    <div class="main">
      <div class="top">
        <!-- 左边 文字 -->
        <div class="left">
          <div class="top1">收藏有礼</div>
          <div class="top2">引导用户将您的店铺小程序添加至微信”我的小程序“可有效提升店铺小程序打开率，用户活跃度等多项指标。</div>
          <div>注：1、由于微信的限制，系统无法获知用户是否已收藏小程序，此功能仅作为引导性提示。</div>
          <div style="margin-top: 10px;margin-bottom: 10px;padding-left: 28px">2、会出现用户未收藏或取消收藏小程序也获得收藏奖励的情况，请知悉。</div>
        </div>
        <!-- 右边 开关配置 -->
        <div class="right">
          <el-switch
            v-model="switchValue"
            active-color="#F7931E"
            inactive-color="#ddd"
          >
          </el-switch>
          <span style="color: rgb(153, 153, 153);">{{this.switchValue === true ? '已开启' : '已关闭' }}</span>
        </div>
      </div>

      <!-- 下半部分 -->
      <div
        class="mid"
        v-show="this.switchValue"
      >
        <!-- 轮播图 -->
        <div class="midleft">

        </div>
        <!-- 活动内容 -->
        <div class="midright">
          <el-form
            label-width="150px"
            labelPosition='right'
            :rules="rules"
            :model="form"
            size="small"
            ref="form"
          >
            <!-- 活动时间 -->
            <el-form-item
              label="活动时间"
              prop="actTime"
            >
              <div class="block">
                <el-date-picker
                  v-model="form.actTime"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
              </div>
            </el-form-item>
            <!-- 图标 -->
            <el-form-item
              label="图标"
              prop="logo"
            >
              <el-radio
                v-model="form.logo"
                label="0"
              >默认</el-radio>
              <el-radio
                v-model="form.logo"
                label="1"
              >自定义</el-radio>
              <!-- 图片弹窗 -->
              <div
                style="display: flex;align-items: center;flex-wrap: wrap;"
                v-if="this.form.logo==1"
              >
                <span
                  @click="deleteGoodsImg()"
                  v-if="this.srcList.src !==`${this.$imageHost}/image/admin/add_img.png`"
                  class="deleteIcon"
                >×</span>
                <div
                  @click="addGoodsImg"
                  class="ImgWrap"
                >
                  <el-image
                    style="width: 80px; height: 80px"
                    :src="srcList.src"
                    fit="scale-down"
                  ></el-image>
                </div>
                <span class="inputTip">
                  建议尺寸：150px * 150px
                </span>
              </div>
            </el-form-item>
            <!-- 收藏奖励 -->
            <el-form-item
              label="收藏奖励"
              prop="reward"
            >
              <div>
                <!-- 积分 -->
                <el-checkbox
                  label="积分"
                  v-model="integral"
                ></el-checkbox>
                <el-input
                  size="small"
                  style="width: 180px"
                  v-model="score"
                ></el-input>
                积分
                <br>
                <!-- 优惠券 -->
                <el-checkbox
                  label="优惠券"
                  v-model="coupon"
                ></el-checkbox>
                <div class="middleContainer">
                  <div>
                    <div
                      v-for="(item,index) in couponData"
                      :key="index"
                      class="addInfo"
                      style="margin-right: 5px;"
                    >
                      <section
                        class="couponImgWrapper"
                        style="line-height:normal"
                      >
                        <div class="coupon_list_top">
                          <span>￥</span>
                          <span class="number">{{item.denomination}}</span>
                        </div>
                        <div class="coupon_center_limit">{{item.useConsumeRestrict | formatLeastConsume(item.leastConsume)}}</div>
                        <div class="coupon_center_number">剩余{{item.surplus}}张</div>
                        <div
                          class="coupon_list_bottom"
                          style="font-size:12px"
                        >领取</div>
                      </section>
                      <span
                        @click="deleteCouponImg(index)"
                        class="deleteIcon"
                      >×</span>
                    </div>
                  </div>
                  <div
                    class="addInfo"
                    @click="handleToCallDialog()"
                    v-if="couponData.length < 5"
                  >
                    <el-image
                      fit="scale-down"
                      :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                      style="width: 78px;height:78px;cursor:pointer"
                    ></el-image>
                  </div>
                </div>
              </div>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <!-- 图片弹窗 -->
      <ImageDalog
        pageIndex='pictureSpace'
        :tuneUp="showImageDialog"
        @handleSelectImg='imgDialogSelectedCallback'
      />
      <!--添加优惠卷弹窗-->
      <addCouponDialog
        @checkReturnFormat="handleToCheck"
        :tuneUpCoupon="showCouponDialog"
        :couponBack="couponIdList"
      />
    </div>
    <!-- 保存修改 -->
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        @click="submit"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import ImageDalog from '@/components/admin/imageDalog'
import addCouponDialog from '@/components/admin/addCouponDialog'
import { collectGiftSelect, collectGiftUpdate } from '@/api/admin/marketManage/collectGift.js'
import { getCouponSelectComponentData } from '@/api/admin/marketManage/couponGive.js'

export default {
  components: {
    ImageDalog,
    addCouponDialog
  },
  inject: ['reload'],
  data () {
    // 自定义校验规则
    var validateLogo = (rule, value, callback) => {
      if (value === '1') {
        if (this.srcList.src === `${this.$imageHost}/image/admin/add_img.png`) {
          callback(new Error('请选择自定义图标'))
        } else { callback() }
      } else { callback() }
    }
    var validateReward = (rule, value, callback) => {
      if (this.integral === false && this.coupon === false) {
        callback(new Error('请至少选择一种奖励方式'))
      } else if (this.integral === true && this.score === '' && this.coupon === false) {
        callback(new Error('请输入奖励积分'))
      } else if (this.integral === true && this.score === '' && this.coupon === true && this.couponId !== '') {
        callback(new Error('请输入奖励积分'))
      } else if (this.coupon === true && this.couponId === '' && this.integral === false) {
        callback(new Error('请选择优惠券'))
      } else if (this.coupon === true && this.couponId === '' && this.integral === true && this.score !== '') {
        callback(new Error('请选择优惠券'))
      } else if (this.integral === true && this.score === '' && this.coupon === true && this.couponId === '') {
        callback(new Error('请输入奖励积分,请选择优惠券'))
      } else {
        callback()
      }
    }
    return {
      // 开关
      switchValue: true,
      // 表单内容
      form: {
        actTime: [],
        logo: '0',
        reward: ''
      },
      // 表单校验
      rules: {
        actTime: [
          { required: true, message: `请选择活动时间`, trigger: 'blur' }
        ],
        logo: [
          { validator: validateLogo, trigger: 'change' }
        ],
        reward: [
          { validator: validateReward, trigger: 'change' }
        ]
      },

      // 图片弹窗
      srcList: {
        src: `${this.$imageHost}/image/admin/add_img.png`
      },
      showImageDialog: false,
      // 复选框
      score: '',
      scoreTemp: '',
      // 优惠卷弹窗
      couponDialogFlag: false,
      couponData: [],
      couponId: '',
      showCouponDialog: false,
      couponIdList: [],
      imgHost: `${this.$imageHost}`,
      checkedData: [],
      integral: false,
      coupon: false,
      // 优惠券回显相关
      data: ''
    }
  },
  created () {
    this.selectInfo()
  },
  mounted () {
    // 初始化国际化语言
    this.langDefault()
  },
  methods: {
    selectInfo () {
      collectGiftSelect().then(res => {
        if (res.error === 0) {
          console.log('收藏有礼配置为：', res.content)
          if (res.content.on_off === 0) {
            this.switchValue = false
          } else { this.switchValue = true }
          this.form.actTime.push(res.content.start_time)
          this.form.actTime.push(res.content.end_time)
          this.form.logo = `${res.content.collect_logo}`
          this.srcList.src = res.content.collect_logo_src
          if (res.content.score !== null) {
            this.integral = true
            this.score = res.content.score
          }
          if (res.content.coupon_ids !== null) {
            this.coupon = true
            this.couponId = res.content.coupon_ids
            this.couponData = res.content.coupon_ids.split(',')
          }
          let param = {
            'actName': ''
          }
          getCouponSelectComponentData(param).then((res) => {
            if (res.error === 0) {
              this.dialogData = res.content
              this.dialogData.map((item, index) => {
                this.$set(item, 'ischeck', false)
              })
              this.dialogData.forEach((item, index) => {
                item.ischeck = false
                this.couponData.forEach((itemC, indexC) => {
                  if (item.id === Number(itemC)) {
                    item.ischeck = true
                  }
                })
              })
              let arr = []
              this.dialogData.forEach((item, index) => {
                if (item.ischeck) arr.push(item)
              })
              this.formatCoupon(arr)
              this.couponData = arr
            }
          })
        }
      })
    },
    formatCoupon (data) {
      let couponArr = []
      let couponData = {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      }
      data.map(item => {
        couponArr.push(Object.assign({}, item, { send_num: '', coupon_set: couponData }))
      })
      return couponArr
    },
    submit () {
      this.scoreTemp = this.score
      if (this.form.logo === '0') {
        this.srcList.src = `${this.$imageHost}/image/admin/add_img.png`
      }
      if (this.integral === false) {
        this.scoreTemp = null
      }
      if (this.coupon === false) {
        this.couponId = null
      }
      let submitParam = {
        'on_off': Number(this.switchValue),
        'start_time': this.form.actTime[0],
        'end_time': this.form.actTime[1],
        'collect_logo': Number(this.form.logo),
        'collect_logo_src': this.srcList.src,
        'score': this.scoreTemp,
        'coupon_ids': this.couponId
      }
      console.log('入参：', submitParam)
      this.$refs['form'].validate((valid) => {
        if (valid) {
          collectGiftUpdate(submitParam).then(res => {
            if (res.error === 0) {
              console.log('保存成功')
              this.reload()
            } else console.log('保存失败', res)
          })
        } else {
          console.log('未通过校验')
          return false
        }
      })
    },
    // 优惠卷回调
    handleToCheck (data) {
      console.log('coupon', data)
      let couponKey = []
      data.map((item) => {
        couponKey.push(item.id)
      })
      this.couponData = data
      this.couponId = couponKey.toString()
      console.log('conponId', couponKey.toString())
      console.log('conponData', this.couponData)
    },
    // 删除优惠券图片
    deleteCouponImg (index) {
      this.couponIdTemp = this.couponId.split(',')
      console.log('before couponIdTemp:', this.couponIdTemp)
      this.couponIdTemp.splice(index, 1)
      console.log('after couponIdTemp:', this.couponIdTemp)
      this.couponId = this.couponIdTemp.toString()
      this.couponData.splice(index, 1)
    },
    // 选择优惠券弹窗-
    handleToCallDialog () {
      this.dialogFlag = 0
      this.couponIdList = this.getCouponIdsArray(this.couponData)
      this.showCouponDialog = !this.showCouponDialog
    },
    getCouponIdsArray (data) {
      let res = []
      data.forEach((item, index) => {
        res.push(item.id)
      })
      return res
    },
    // 活动分享 -- 添加图片点击事件，弹出图片选择组件
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    imgDialogSelectedCallback (src) {
      this.srcList.src = src.imgUrl
    },
    // 删除图片
    deleteGoodsImg () {
      this.srcList.src = `${this.$imageHost}/image/admin/add_img.png`
    }
  },
  filters: {
    formatLeastConsume (useConsumeRestrict, leastConsume) {
      if (useConsumeRestrict === 0) {
        return `不限制`
      } else {
        return `满${leastConsume}元可用`
      }
    }
  }

}
</script>
<style lang="scss" scoped>
.collectGift {
  padding: 10px;
  .main {
    padding: 10px;
    background: #fff;
    .top {
      background-color: #f2f2f2;
      height: 120px;
      padding: 10px;
      font-family: "微软雅黑";
      font-size: 14px;
      display: flex;
      justify-content: space-between;
      .left {
        .top1 {
          font-size: 20px;
          margin-bottom: 10px;
          font-weight: bold;
        }
        .top2 {
          font-size: 14px;
          margin-bottom: 10px;
          font-weight: bold;
        }
      }
    }

    .mid {
      display: flex;
      .midleft {
        background: rgb(219, 216, 216);
        width: 500px;
        height: 500px;
      }
      .midright {
        margin-top: 20px;
      }
    }
  }

  .footer {
    padding: 10px 10px 10px 10px;
    text-align: center;
    background: #f8f8f8;
    margin-top: 10px;
    position: fixed;
    bottom: 0;
    z-index: 1;
    width: 90%;
  }
}
.ImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
}
.deleteIcon {
  width: 17px;
  height: 17px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 17px;
  text-align: center;
  position: relative;
  top: -41px;
  right: -95px;
  cursor: pointer;
  opacity: 0.8;
}
.ImgWrap .moveIcon {
  width: 17px;
  height: 17px;
  display: none;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  line-height: 17px;
  text-align: center;
  position: absolute;
  bottom: 0px;
  cursor: pointer;
  opacity: 0.8;
}
.ImgWrap:hover .moveIcon {
  display: block;
}
.middleContainer {
  display: flex;
  .deleteIcon {
    position: relative;
    width: 17px !important;
    height: 17px;
    line-height: 17px;
    top: -118px;
    left: 45px;
    cursor: pointer;
    opacity: 0.8;
    color: #fff;
    background: #ccc;
    border: 1px solid #ccc;
    border-radius: 50%;
    text-align: center;
  }
}
.addInfo {
  display: inline-block;
  position: relative;
  width: 100px;
  height: 101px;
  margin-bottom: 10px;
  background: #fff;
  border: 1px solid #e4e4e4;
  cursor: pointer;
  text-align: center;
  img {
    margin-top: 10px;
  }
  p {
    line-height: normal;
    margin-top: -30px;
    color: #999;
  }
  .couponImgWrapper {
    width: 100%;
    height: 100%;
    border: 1px solid #fbb;
    border-radius: 10px;
    .coupon_list_top {
      margin-top: 10px;
      color: #f60;
      :nth-of-type(2) {
        font-size: 20px;
        font-weight: bold;
      }
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
      background-image: url("http://mpdevimg2.weipubao.cn/image/admin/coupon_border.png");
      background-repeat: repeat-x;
    }
  }
}
</style>
