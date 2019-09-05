<!--
* 添加砍价活动页面
*
* @author:赵鑫
-->

<template>
  <div class="bargainAct">
    <div class="bargainContent">
      <div class="bargainActMain">
        <!-- 公共部分 -->
        <el-form
          label-width="150px"
          labelPosition='right'
        >
          <el-form-item
            label="活动类型："
            prop=""
          >
            <el-radio-group
              :disabled="isEditFlag"
              v-model="param.bargainType"
              size="medium"
            >
              <el-radio :label='0'>砍到指定金额计算</el-radio>
              <el-radio :label='1'>砍到任意金额计算</el-radio>
            </el-radio-group>
            <span style="margin-left: 15px;">保存后不可编辑</span>
          </el-form-item>

          <el-form-item
            label="活动名称："
            prop=""
          >
            <el-input
              v-model="param.bargainName"
              size="small"
              style="width:200px;"
              placeholder="请输入活动名称"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="有效期："
            prop=""
          >
            <el-date-picker
              v-model="effectiveDate"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              size="small"
            >
            </el-date-picker>
          </el-form-item>

          <el-form-item
            label="活动商品："
            prop=""
          >
            <div
              v-if="!isEditFlag"
              @click="showChoosingGoods"
              class="choose"
            >
              <img
                :src="srcList.src3"
                alt=""
              >
              <p v-if="this.goodsRow.length == 0">选择商品</p>
              <p v-else>重新选择</p>
            </div>
            <div class="fontColor">所有参与砍价的商品，均需要用户将价格砍到底价后才可以砍价成功，
              若某商品同一时间段内同时参与了砍价和拼团活动，则优先进行砍价活动</div>
            <el-table
              :data="this.goodsRow"
              :hidden="this.goodsRow.length == 0?true:false"
            >
              <el-table-column
                prop="goodsName"
                label="商品名称"
                align="center"
              >
                <template slot-scope="scope">
                  <img :src="scope.row.goodsImg">
                  <span>{{scope.row.goodsName}}</span>
                </template>

              </el-table-column>
              <el-table-column
                prop="goodsNumber"
                label="商品原库存"
                align="center"
              ></el-table-column>
              <el-table-column
                label="砍价库存"
                align="center"
              >
                <template slot-scope="scope">
                  <el-input-number
                    v-model="param.stock"
                    size="small"
                    style="width:120px"
                    :min="1"
                    :max="scope.row.goodsNumber"
                  >
                  </el-input-number>
                </template>
              </el-table-column>
              <el-table-column
                prop="shopPrice"
                label="商品原价"
                align="center"
              ></el-table-column>
              <el-table-column
                v-if="param.bargainType == 0"
                prop="shopPrice"
                label="砍价底价"
                align="center"
              >
                <template slot-scope="scope">
                  <el-input-number
                    v-model="param.expectationPrice"
                    size="small"
                    style="width:120px"
                    :min="0"
                    :max="scope.row.shopPrice"
                  >
                  </el-input-number>
                  (默认0元)
                </template>
              </el-table-column>
              <el-table-column
                v-else
                prop="shopPrice"
                label="结算金额"
                align="center"
              >
                <template slot-scope="scope">
                  <el-input-number
                    :disabled="isEditFlag"
                    v-model="param.floorPrice"
                    size="small"
                    style="width:120px"
                    :min="0"
                    :max="scope.row.shopPrice"
                  >
                  </el-input-number>
                  至
                  <el-input-number
                    :disabled="isEditFlag"
                    v-model="param.expectationPrice"
                    size="small"
                    style="width:120px"
                    :min="0"
                    :max="scope.row.shopPrice"
                  >
                  </el-input-number>
                  (默认0元)保存后不可编辑
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>

          <!-- 砍到指定金额计算部分内容区域 -->
          <div v-if="this.param.bargainType==0">
            <!-- <el-form-item
              label="帮砍设置："
              prop=""
            >
              <el-checkbox v-model="checked">帮砍好友需要授权手机号才可以参与砍价</el-checkbox>
            </el-form-item> -->

            <el-form-item
              label="运费设置："
              prop=""
            >
              <el-radio-group v-model="param.freeFreight">
                <el-radio :label="1">免运费</el-radio>
                <el-radio :label="0">使用原商品运费模板</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="期望参与砍价人次：">
              <el-input-number
                :disabled="isEditFlag"
                v-model="param.expectationNumber"
                size="small"
                style="width:150px"
                :min="3"
              >
              </el-input-number>&nbsp;人
              <span style="margin-left:10px">(期望人次最少为3)</span>
              <div class="fontColor">填写人数为发起人发起砍价后，预计将价格砍到底价时需要参与砍价活动帮助该发起人进行砍价的用户数，
                默认为100，保存后不可编辑</div>
            </el-form-item>

            <el-form-item label="商品首次砍价可砍价比例区间：">
              <div style="display: flex">
                <el-input-number
                  v-model="param.bargainMin"
                  size="small"
                  style="width:150px"
                  :min="0"
                  :max="50"
                ></el-input-number>&nbsp;%&nbsp;至&nbsp;
                <el-input-number
                  v-model="param.bargainMax"
                  size="small"
                  style="width:150px"
                  :min="0"
                  :max="50"
                ></el-input-number>&nbsp;%
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

          <!-- 砍到任意金额计算部分 -->
          <div v-if="this.param.bargainType==1">
            <el-form-item
              label="单次帮砍金额"
              prop=""
            >
              <el-radio-group v-model="param.bargainMoneyType">
                <el-radio :label='0'>固定金额
                  <el-input-number
                    v-model="param.bargainFixedMoney"
                    size="small"
                    style="width:150px"
                  ></el-input-number>元
                </el-radio>
                <br>
                <el-radio :label='1'>随机金额
                  <el-input-number
                    v-model="param.bargainMinMoney"
                    size="small"
                    style="width:150px"
                  ></el-input-number>元
                  <span>至</span>
                  <el-input-number
                    v-model="param.bargainMaxMoney"
                    size="small"
                    style="width:150px"
                  ></el-input-number>元之间取随机数
                </el-radio>
              </el-radio-group>
            </el-form-item>

            <!-- <el-form-item
              label="帮砍设置："
              prop=""
            >
              <el-checkbox v-model="checked">帮砍好友需要授权手机号才可以参与砍价</el-checkbox>
            </el-form-item> -->

            <el-form-item
              label="运费设置："
              prop=""
            >
              <el-radio-group v-model="param.freeFreight">
                <el-radio :label='1'>免运费</el-radio>
                <el-radio :label='0'>使用原商品运费模板</el-radio>
              </el-radio-group>
            </el-form-item>
          </div>

          <!-- 公共更多配置模块部分 -->
          <el-form-item
            label="好友砍价优惠券："
            prop=""
          >
            <el-card class="box-card">
              <div class="fontColor">向帮忙砍价的用户赠送优惠券，可促使帮砍用户在店铺内下单，提高交易量。</div>
              <span>{{mrkingVoucherId}}</span>
              <div
                @click="handleToCallDialog1()"
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
              <span>{{rewardCouponId}}</span>
              <div
                @click="handleToCallDialog2()"
                class="addInfo"
              >
                <img
                  :src="srcList.src3"
                  alt=""
                >
                <p class="fontColor">添加优惠券</p>
              </div>
              <!-- <addCoupon /> -->
              <div class="fontColor">最多添加5张优惠券，已过期和已停用的优惠券不能添加</div>
            </el-card>
          </el-form-item>

          <!-- 引入活动分享模块 -->
          <actShare :shareConfig="shareConfig" />

        </el-form>

      </div>
    </div>
    <div class="footer">
      <el-button
        @click="isEditFlag?updateSubmit():addSubmit()"
        type="primary"
        size="small"
      >保存</el-button>
    </div>
    <!--添加优惠卷-->
    <AddCouponDialog @handleToCheck="handleToCheck" />
    <!--商品选择-->
    <choosingGoods @resultGoodsRow="choosingGoodsResult" />
  </div>
</template>

<script>
import addCoupon from './addCoupon'
import actShare from './actShare'
import AddCouponDialog from '@/components/admin/addCouponDialog'
import choosingGoods from '@/components/admin/choosingGoods'
import { addBargain, getBargainByIsd, updateBargain } from '@/api/admin/marketManage/bargain.js'

export default {
  components: { addCoupon, actShare, AddCouponDialog, choosingGoods },
  mounted () {
    if (this.$route.query.id > 0) {
      // 编辑砍价活动
      this.actId = this.$route.query.id
      // 编辑时部分信息不可修改
      this.isEditFlag = true
      // 点击编辑按钮进来，初始化页面数据
      let SimpleBargainParam = {
        'id': this.$route.query.id
      }
      getBargainByIsd(SimpleBargainParam).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.param = res.content
          this.effectiveDate = []
          this.effectiveDate.push(res.content.startTime)
          this.effectiveDate.push(res.content.endTime)
          this.mrkingVoucherId = res.content.mrkingVoucherList
          this.rewardCouponId = res.content.rewardCouponList
          this.goodsRow.push(res.content.goods)
        }
      })
    }
  },
  data () {
    return {
      // 向帮忙砍价的用户赠送优惠券
      mrkingVoucherId: [],
      // 砍价失败后向买家赠送优惠券
      rewardCouponId: [],
      // 优惠券弹窗区分，1鼓励奖，0好友砍价优惠券
      dialogFlag: 1,
      effectiveDate: '',
      goodsRow: [],
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/bagain_pictorial.jpg`,
        src3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        imageUrl: ``
      },
      param: {
        // 默认值
        bargainType: 0,
        freeFreight: 1,
        expectationNumber: 100,
        bargainMoneyType: 0,
        stock: 0,
        floorPrice: 0
      },
      shareConfig: {
        'share_action': 1,
        'share_doc': '',
        'share_img_action': 1,
        'share_img': ''
      },
      isEditFlag: false,
      actId: null
    }
  },
  methods: {
    // 选择优惠券弹窗-帮忙砍价的用户
    handleToCallDialog1 () {
      this.dialogFlag = 0
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.mrkingVoucherId
      }
      this.$http.$emit('V-AddCoupon', obj)
    },
    // 选择优惠券弹窗-砍价失败后向买家赠送
    handleToCallDialog2 () {
      this.dialogFlag = 1
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.rewardCouponId
      }
      this.$http.$emit('V-AddCoupon', obj)
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data) {
      console.log(data)
      if (this.dialogFlag === 1) {
        this.rewardCouponId = data
      } else {
        this.mrkingVoucherId = data
      }
    },
    // 选择商品弹窗
    showChoosingGoods () {
      this.$http.$emit('choosingGoodsFlag', true, 'choiseOne')
    },
    //  选择商品弹窗确认
    choosingGoodsResult (row) {
      this.param.goodsId = row.goodsId
      this.goodsRow = []
      this.goodsRow.push(row)
      this.param.stock = this.goodsRow[0].goodsNumber
    },
    addSubmit () {
      this.param.shareConfig = this.shareConfig
      this.param.startTime = this.effectiveDate[0]
      this.param.endTime = this.effectiveDate[1]
      this.param.mrkingVoucherId = this.getCouponIdsString(this.mrkingVoucherId)
      this.param.rewardCouponId = this.getCouponIdsString(this.rewardCouponId)
      addBargain(this.param).then((res) => {
        if (res.error === 0) {
          this.$message({
            type: 'success',
            message: '保存成功!'
          })
          this.$router.push({
            name: 'bargain'
          })
        } else {
          this.$message({
            type: 'fail',
            message: '保存失败!'
          })
        }
      })
    },
    updateSubmit () {
      // 更新活动
      this.param.id = this.actId
      this.param.shareConfig = this.shareConfig
      this.param.startTime = this.effectiveDate[0]
      this.param.endTime = this.effectiveDate[1]
      this.param.mrkingVoucherId = this.getCouponIdsString(this.mrkingVoucherId)
      this.param.rewardCouponId = this.getCouponIdsString(this.rewardCouponId)
      updateBargain(this.param).then((res) => {
        if (res.error === 0) {
          this.$message({
            type: 'success',
            message: '更新成功!'
          })
          this.$router.push({
            name: 'bargain'
          })
        } else {
          this.$message({
            type: 'fail',
            message: '更新失败!'
          })
        }
      })
    },
    getCouponIdsString (data) {
      let res = ''
      data.forEach((item, index) => {
        if (index == 0) {
          res += item.id
        } else {
          res += ',' + item.id
        }
      })
      return res
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
