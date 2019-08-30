<template>
  <div class="bargainAct">
    <div class="bargainContent">
      <div class="bargainActMain">
        <el-form
          label-width="150px"
          labelPosition='right'
        >
          <el-form-item
            label="活动类型："
            prop=""
          >
            <el-radio-group
              v-model="param.bargainType"
              size="medium"
            >
              <el-radio label="1">砍到指定金额计算</el-radio>
              <el-radio label="2">砍到任意金额计算</el-radio>
            </el-radio-group>

            <!-- <el-radio-group
              v-model="param.bargainType"
              size="medium"
            >
              <el-radio label="砍到指定金额计算"></el-radio>
              <el-radio label="砍到任意金额计算"></el-radio>
            </el-radio-group> -->
            <span style="margin-left: 10px;">保存后不可编辑</span>
          </el-form-item>

          <el-form-item
            label="活动名称："
            prop=""
          >
            <el-input
              size="small"
              style="width:200px;"
              placeholder="请输入活动名称"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="有效期："
            prop=""
          >
            <div style="display:flex">
              <el-date-picker
                size="small"
                v-model="value1"
                type="datetime"
                placeholder="选择日期时间"
              >
              </el-date-picker>
              <span style="margin: 0 5px">至</span>
              <el-date-picker
                size="small"
                v-model="value1"
                type="datetime"
                placeholder="选择日期时间"
              >
              </el-date-picker>
            </div>
          </el-form-item>

          <el-form-item
            label="活动商品："
            prop=""
          >
            <div class="choose">
              <img
                :src="srcList.src3"
                alt=""
              >
              <p>选择商品</p>
            </div>
            <div class="fontColor">所有参与砍价的商品，均需要用户将价格砍到底价后才可以砍价成功，
              若某商品同一时间段内同时参与了砍价和拼团活动，则优先进行砍价活动</div>
          </el-form-item>

          <div v-if="this.param.bargainType==1">
            <el-form-item
              label="帮砍设置："
              prop=""
            >
              <el-checkbox v-model="checked">帮砍好友需要授权手机号才可以参与砍价</el-checkbox>
            </el-form-item>

            <el-form-item
              label="运费设置："
              prop=""
            >
              <el-radio
                v-model="param.freeFreight"
                label="1"
              >免运费</el-radio>
              <el-radio
                v-model="param.freeFreight"
                label="2"
              >使用原商品运费模板</el-radio>
            </el-form-item>

            <el-form-item label="期望参与砍价人次：">
              <el-input
                size="small"
                style="width:90px"
              ></el-input>&nbsp;人
              <span style="margin-left:10px">(期望人次最少为3)</span>
              <div class="fontColor">填写人数为发起人发起砍价后，预计将价格砍到底价时需要参与砍价活动帮助该发起人进行砍价的用户数，
                默认为100，保存后不可编辑</div>
            </el-form-item>

            <el-form-item label="商品首次砍价可砍价比例区间：">
              <div style="display: flex">
                <el-input
                  size="small"
                  style="width:90px"
                ></el-input>&nbsp;%&nbsp;至&nbsp;
                <el-input
                  size="small"
                  style="width:90px"
                ></el-input>&nbsp;%
                <span style="margin-left:10px">(比例必须在0~50%之间)</span>
              </div>
              <div
                class="fontColor"
                style="line-height:24px;margin-top:10px"
              >用户发起砍价后，首次砍价可以砍掉的金额占商品价格的比例 ，该比例在填写区间内随机产生。
                不填写则按照系统规则计算， 默认为空，为空表示不填写。 例如填写20%到50%，商品价格为100元，则用户发起砍价，
                首次给自己砍价时，系统会随机取该 比例区间数字，例如随机为35%， 则该用户发起砍价后首次砍价金额为100*35%*（系统砍价系数），
                系统砍价系数按照系统逻辑计算。若系统砍价系数为0.5，则本次砍价金额为100*35%*0.5=17.5元。 即该用户给自己 可砍掉17.5元。</div>
            </el-form-item>
          </div>

          <div v-if="this.param.bargainType==2">
            <el-form-item
              label="单次帮砍金额"
              prop=""
            >
              <el-radio
                v-model="param.bargainMoney"
                label="1"
              >固定金额
                <el-input
                  size="small"
                  style="width:90px"
                ></el-input>元
              </el-radio>
              <br>
              <el-radio
                v-model="param.bargainMoney"
                label="2"
              >随机金额
                <el-input
                  size="small"
                  style="width:90px"
                ></el-input>元
                <span>至</span>
                <el-input
                  size="small"
                  style="width:90px"
                ></el-input>元之间取随机数
              </el-radio>
            </el-form-item>

            <el-form-item
              label="帮砍设置："
              prop=""
            >
              <el-checkbox v-model="checked">帮砍好友需要授权手机号才可以参与砍价</el-checkbox>
            </el-form-item>

            <el-form-item
              label="运费设置："
              prop=""
            >
              <el-radio
                v-model="param.freeFreight"
                label="1"
              >免运费</el-radio>
              <el-radio
                v-model="param.freeFreight"
                label="2"
              >使用原商品运费模板</el-radio>
            </el-form-item>
          </div>

          <el-form-item
            label="好友砍价优惠券："
            prop=""
          >
            <el-card class="box-card">
              <div class="fontColor">向帮忙砍价的用户赠送优惠券，可促使帮砍用户在店铺内下单，提高交易量。</div>
              <div
                @click="submit"
                class="addInfo"
              >
                <img
                  :src="srcList.src3"
                  alt=""
                >
                <p class="fontColor">添加优惠券</p>
              </div>
              <div class="fontColor">最多添加5张优惠券，已过期和已停用的优惠券不能添加</div>
            </el-card>
          </el-form-item>

          <el-form-item
            label="鼓励奖："
            prop=""
          >
            <el-card class="box-card">
              <div class="fontColor">买家砍价失败后给予一定奖励，可提升买家复购</div>
              <addCoupon />
              <div class="fontColor">最多添加5张优惠券，已过期和已停用的优惠券不能添加</div>
            </el-card>
          </el-form-item>

          <actShare :shareConfig="shareConfig" />

          <!-- <el-form-item
            label="活动分享："
            prop=""
          >
            <div>
              <el-radio
                v-model="param.actShare"
                label="1"
              >
                <span>默认样式</span>
                <el-popover
                  placement="right-start"
                  width="220"
                  trigger="hover"
                >
                  <el-image :src="srcList.src1"></el-image>
                  <el-button
                    slot="reference"
                    type="text"
                    style="margin: 0 20px"
                  >查看示例</el-button>
                </el-popover>
                <el-popover
                  placement="right-start"
                  width="220"
                  trigger="hover"
                >
                  <el-image :src="srcList.src2"></el-image>
                  <el-button
                    slot="reference"
                    type="text"
                  >下载海报</el-button>
                </el-popover>
              </el-radio>
            </div>
            <div>
              <el-radio
                v-model="param.actShare"
                label="2"
              >
                自定义样式
                <div style="margin: 15px 0">
                  <span>文案：</span>
                  <el-input
                    size="small"
                    style="width:200px"
                  ></el-input>
                </div>
                <div>
                  <span>分享图：</span>
                  <el-radio
                    v-model="param.imgShare"
                    label="1"
                  >活动商品信息图</el-radio>
                  <div style="margin: 10px 0 0 60px">
                    <el-radio
                      v-model="param.imgShare"
                      label="2"
                    >自定义图片</el-radio>
                  </div>
                  <div style="margin: 10px 0 0 60px; display:flex">
                    <div
                      class="selectPic"
                      @click="handleShowDialog"
                    >
                      <img
                        class="recPic"
                        :src="srcList.src3"
                        alt=""
                      >
                    </div>
                    <span style="margin: 30px 0 0 30px">建议尺寸：800*800像素</span>
                  </div>
                </div>
              </el-radio>
              <ImageDalog
                pageIndex='pictureSpace'
                @handleSelectImg='handleSelectImg'
              />
            </div>
          </el-form-item> -->

        </el-form>

      </div>
    </div>
    <div class="footer">
      <el-button
        @click="submit"
        type="primary"
        size="small"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import addCoupon from './addCoupon'
import actShare from './actShare'
// import { addBargain } from '@/api/admin/marketManage/bargain.js'

export default {
  components: { addCoupon, actShare },
  mounted () {
    console.log(this.shareConfig)
  },
  data () {
    return {
      value1: '',
      checked: '',
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`,
        src3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        imageUrl: ``
      },
      param: {
        bargainType: '活动类型',
        freeFreight: '运费设置',
        actShare: '活动分享',
        bargainMoney: '单次帮砍金额'
      },
      imgLists: [],
      shareConfig: {
        'share_action': 1,
        'share_doc': '',
        'share_img_action': 1,
        'share_img': ''
      }
    }
  },
  methods: {
    handleShowDialog () {
      this.$http.$emit('dtVisible')
    },
    handleSelectImg (res) {
      if (res != null) {
        console.log(res)
        this.srcList.src3 = res
      }
    },
    submit () {
      console.log(this.shareConfig)
      // this.coupon_info.forEach(element => {
      //   var voucher = {}
      //   voucher.voucherId = element.id
      //   voucher.totalAmount = element.send_num
      //   voucher.immediatelyGrantAmount = element.coupon_set.immediatelyGrantAmount
      //   voucher.timingAmount = element.coupon_set.timingAmount
      //   voucher.timingEvery = element.coupon_set.timingEvery
      //   voucher.timingTime = element.coupon_set.timingTime
      //   voucher.timingUnit = element.coupon_set.timingUnit
      //   this.param.couponPackVoucher.push(voucher)
      // })
      // this.param.startTime = this.effectiveDate[0]
      // this.param.endTime = this.effectiveDate[1]
      // addBargain(this.param).then((res) => {
      //   if (res.error === 0) {
      //     this.$message({
      //       type: 'success',
      //       message: '保存成功!'
      //     })
      //     this.$router.push({
      //       name: 'coupon_package'
      //     })
      //   } else {
      //     this.$message({
      //       type: 'fail',
      //       message: '保存失败!'
      //     })
      //   }
      // })
    }
  }
}

</script>
<style lang="scss" scoped>
.bargainAct {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  .bargainContent {
    padding: 10px;
    .bargainActMain {
      position: relative;
      background-color: #fff;
      padding: 10px 20px;
      .box-card {
        width: 630px;
        background-color: #f5f5f5;
        .addInfo {
          display: inline-block;
          width: 100px;
          height: 98px;
          margin-bottom: 10px;
          background: #fff;
          border: 1px solid #e4e4e4;
          padding: 13px 0;
          cursor: pointer;
          text-align: center;
          img {
            margin-top: 10px;
          }
          p {
            margin-top: -15px;
          }
        }
      }
      .choose {
        display: inline-block;
        width: 70px;
        height: 70px;
        margin-bottom: 10px;
        background: #fff;
        border: 1px solid #e4e4e4;
        cursor: pointer;
        text-align: center;
        img {
          margin-top: 12px;
        }
        p {
          margin-top: -18px;
          font-size: 12px;
          color: #999;
        }
      }
      .selectPic {
        // display: flex;
        width: 70px;
        height: 70px;
        line-height: 70px;
        text-align: center;
        border: 1px solid #e4e4e4;
        .recPic {
          width: 80px;
          height: 80px;
          background-repeat: no-repeat;
        }
      }
    }
    .fontColor {
      color: #999;
    }
  }
  .el-form-item {
    margin-bottom: 15px;
  }
  .footer {
    width: 100%;
    height: 50px;
    padding: 10px 0;
    background: #f8f8f8;
    text-align: center;
  }
}
</style>
