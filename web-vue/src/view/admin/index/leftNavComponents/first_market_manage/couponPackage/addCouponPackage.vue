<template>
  <div class="content">
    <div class="main">
      <div class="coupon_settings">
        <!-- 左侧内容 -->
        <div class="left_preview">
          <div
            class="left_top"
            :style="`background-image: url(${$imageHost}/image/admin/cou_top_bg.png)`"
          >
            优惠券礼包
          </div>
          <div class="left_content">
            <div class="content_bg">
              <img :src="$imageHost + '/image/admin/cou_package_bg.png'">
            </div>
            <div class="content_info">
              <div class="text_title">{{param.packName?param.packName:'优惠券礼包'}}</div>
              <div class="package_info">
                <div class="package_title">
                  ——— <span>优惠券礼包</span> ———
                </div>
                <div class="coupon_info">
                  <div
                    class="coupon_item"
                    v-for="(item, index) in coupon_info"
                    :key="index"
                  >
                    <div class="coupon_left">
                      <div
                        class="coupon_price"
                        v-if="item.actCode == 'voucher'"
                      >￥<span>{{item.denomination}}</span></div>
                      <div
                        class="coupon_price"
                        v-else
                      ><span>{{item.denomination}}</span>折</div>
                      <div class="coupon_rule">{{item.useConsumeRestrict > 0? `满${item.leastConsume}元可用`  : `不限制`}}</div>
                    </div>
                    <div class="coupon_middle">
                      <img
                        :src="$imageHost + '/image/admin/cou_midd_icon.png'"
                        alt=""
                      >
                    </div>
                    <div class="coupon_right">
                      <div class="coupon_name">{{item.actName}}</div>
                      <div class="coupon_limits">全部商品可用></div>
                      <div class="coupon_time">2019-08-21--2019-08-31</div>
                      <div class="coupon_icon">{{item.send_num ?item.send_num:0}}张</div>
                    </div>
                  </div>
                </div>
                <div class="btn_get">立即领取</div>
              </div>
              <div class="package_rule">
                <div class="rule_title">活动规则</div>
                <div
                  class="rule_info"
                  v-html="crlfFormat"
                ></div>
              </div>
            </div>
          </div>
        </div>
        <!-- 左侧内容end -->
        <!-- 右侧内容 -->
        <div class="right_settings">
          <div class="set_box">
            <p class="set_title">基础设置</p>
            <div class="set_item">
              <div class="item_title">
                <em>*</em> 活动名称：
              </div>
              <div class="item_right">
                <el-input
                  v-model="param.actName"
                  placeholder="最多支持10个字"
                  size="small"
                  class="default_input"
                  maxlength="10"
                ></el-input>
                <span class="item_tips">只作为商家记录使用，用户不会看到这个名称</span>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 有效期：</div>
              <div class="item_right">
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
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 礼包名称：</div>
              <div class="item_right">
                <el-input
                  v-model="param.packName"
                  placeholder="最多支持8个字"
                  size="small"
                  class="default_input"
                  maxlength="8"
                ></el-input>
                <span class="item_tips">展示在小程序活动页，最多可填写8个汉字</span>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 礼包内容：</div>
              <div class="item_right">
                <a
                  style="color:#409eff;font-size:12px;cursor: pointer;"
                  @click="handleToCallDialog"
                >添加优惠券</a>
                <span class="item_tips">最多可添加10种优惠券，每种优惠券最多送6张</span>
              </div>
            </div>
            <div
              class="coupon_set_box"
              v-if="coupon_info.length"
            >
              <div class="coupon_set_table">
                <el-table
                  :data="coupon_info"
                  border
                  style="width: 100%"
                >
                  <el-table-column label="优惠券信息">
                    <template slot-scope="scope">
                      <div class="coupon_info">
                        <span class="coupon_name">{{scope.row.actName}}</span>
                        <div
                          class="coupon_price"
                          v-if="scope.row.actCode == 'voucher'"
                        >￥<span>{{scope.row.denomination}}</span></div>
                        <div
                          class="coupon_price"
                          v-else
                        ><span>{{scope.row.denomination}}</span>折</div>
                        <div class="coupon_rule">{{scope.row.useConsumeRestrict > 0? `满${scope.row.leastConsume}元可用`  : `不限制`}}</div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="发券数量"
                    width="120"
                  >
                    <template slot-scope="scope">
                      <div>
                        <el-input-number
                          v-model="scope.row.send_num"
                          size="small"
                          :min="1"
                          :max="6"
                          style="width:100px;"
                        ></el-input-number>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="发放策略"
                    width="180"
                  >
                    <template slot-scope="scope">
                      <div class="coupon_set_p">
                        <p v-if="scope.row.coupon_set.immediatelyGrantAmount > 0">领包后立即发放{{scope.row.coupon_set.immediatelyGrantAmount}}张</p>
                        <p v-if="scope.row.coupon_set.timingEvery > 0 && scope.row.coupon_set.timingAmount > 0">
                          <span v-if="scope.row.coupon_set.timingUnit ==='0'">领包后每{{scope.row.coupon_set.timingEvery}}天发放{{scope.row.coupon_set.timingAmount}}张</span>
                          <span v-if="scope.row.coupon_set.timingUnit ==='1'">领包后每{{scope.row.coupon_set.timingEvery}}周的周{{scope.row.coupon_set.timingTime}}发放{{scope.row.coupon_set.timingAmount}}张</span>
                          <span v-if="scope.row.coupon_set.timingUnit ==='2'">领包后每{{scope.row.coupon_set.timingEvery}}个月的{{scope.row.coupon_set.timingTime}}号发放{{scope.row.coupon_set.timingAmount}}张</span>
                        </p>
                        <a
                          style="color:#409eff;cursor: pointer;"
                          @click.prevent="handleCouponSet(scope)"
                        >{{scope.row.coupon_set.immediatelyGrantAmount > 0 || (scope.row.coupon_set.timingEvery > 0 && scope.row.coupon_set.timingTime > 0) ? '重新设置' : '设置'}}</a>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="操作"
                    width="60"
                  >
                    <template slot-scope="scope">
                      <div style="text-align:center">
                        <a
                          style="color:#409eff;cursor: pointer;"
                          @click.prevent="handleCouponDel(scope.$index)"
                        >删除</a>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <div class="bottom_tips_box">
                <span>注：</span>
                <div class="tips_content">
                  <p>1.优惠券包发放的优惠券不占用原优惠券库存；</p>
                  <p>2.任意一张优惠券过期或失效，则不会展示在券礼包中，用户可以设置的价格购买券包中其他优惠券；</p>
                  <p>3.若所有优惠券全部过期或失效，则该礼包失效，用户不可领取。</p>
                </div>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 每人限领礼包数量：</div>
              <div class="item_right">
                <el-input
                  v-model="param.limitGetTimes"
                  placeholder=""
                  size="small"
                  class="small_input"
                ></el-input>
                <span class="item_tips">单个用户可以领取该礼包的数量，填写0表示不限制</span>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 礼包发放数量：</div>
              <div class="item_right">
                <el-input
                  v-model="param.totalAmount"
                  placeholder=""
                  size="small"
                  class="small_input"
                ></el-input>
                <span class="item_tips">优惠券包发放的总数量</span>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 礼包领取方式：</div>
              <div class="item_right">
                <p>
                  <el-radio
                    v-model="param.accessMode"
                    label="0"
                  >现金购买</el-radio>
                  <el-radio
                    v-model="param.accessMode"
                    label="1"
                  >积分购买</el-radio>
                  <el-radio
                    v-model="param.accessMode"
                    label="2"
                  >直接领取</el-radio>
                </p>
                <p class="package_get_choose">
                  <span v-if="param.accessMode==='0'">
                    需支付：<el-input
                      v-model="param.accessCost"
                      class="small_input"
                      size="small"
                    ></el-input> 元，
                  </span>
                  <span v-if="param.accessMode==='1'">
                    需支付：<el-input
                      v-model="param.accessCost"
                      class="small_input"
                      size="small"
                    ></el-input> 积分，
                  </span>
                  <span v-if="param.accessMode!='2'">
                    当前已选优惠券可优惠金额总和为 0.00元
                  </span>
                </p>
              </div>
            </div>
            <div class="set_item">
              <div class="item_title"><em>*</em> 活动规则：</div>
              <div class="item_right">
                <el-input
                  v-model="param.actRule"
                  placeholder="请输入活动规则"
                  type="textarea"
                  :rows="5"
                  resize="none"
                ></el-input>
              </div>
            </div>
          </div>
        </div>
        <!-- 右侧内容end -->
      </div>
    </div>
    <div class="footer">
      <el-button
        @click="submit"
        type="primary"
        size="small"
      >保存</el-button>
    </div>
    <!--添加优惠卷-->
    <AddCouponDialog
      origin="couponPackage"
      @handleToCheck="handleToCheck"
    />

    <!-- 设置优惠券内容 -->
    <el-dialog
      :visible.sync="couponSetDialogFlag"
      title="设置发放策略"
      custom-class="couponSetDialog"
      center
    >
      <div class="coupon_info_set">
        <p>领取后立即发放 <el-input-number
            v-model="coupon_set.immediatelyGrantAmount"
            placeholder=""
            size="small"
            style="width:100px;"
          ></el-input-number> 张</p>
        <p>领取后每 <el-input-number
            v-model="coupon_set.timingEvery"
            placeholder=""
            size="small"
            style="width:100px;"
          ></el-input-number>
          <el-select
            v-model="coupon_set.timingUnit"
            size="small"
            style="width:100px;"
          >
            <el-option
              v-for="item in coupon_set_date"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </p>
        <p style="padding-left: 28px;">
          <span v-if="coupon_set.timingUnit === '1'">
            每周
            <el-select
              v-model="coupon_set.timingTime"
              size="small"
              style="width:100px;"
            >
              <el-option
                v-for="(item,index) in 7"
                :key="index"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
          </span>
          <span v-else-if="coupon_set.timingUnit === '2'">
            每月
            <el-select
              v-model="coupon_set.timingTime"
              size="small"
              style="width:100px;"
            >
              <el-option
                v-for="(item,index) in 31"
                :key="index"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
            号
          </span>
          <span></span>
          发放 <el-input-number
            v-model="coupon_set.timingAmount"
            placeholder=""
            size="small"
            style="width:100px;"
          ></el-input-number> 张</p>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          @click="couponSetDialogFlag = false"
          size="small"
        >取 消</el-button>
        <el-button
          type="primary"
          @click="confrimCouponSet()"
          size="small"
        >确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addCouponPackage } from '@/api/admin/marketManage/couponPackage.js'
export default {
  components: {
    AddCouponDialog: () => import('@/view/admin/index/leftNavComponents/user_manger/membershipCard/addCouponDialog')
  },
  data () {
    return {
      param: {
        actName: '',
        packName: '',
        limitGetTimes: '',
        totalAmount: '',
        actRule: '',
        accessMode: '0',
        couponPackVoucher: []

      },
      effectiveDate: '',
      coupon_info: [],
      couponDialogFlag: false,
      couponSetDialogFlag: false,
      accessCost: {
        access_cost1: '',
        access_cost2: ''
      },
      coupon_set: {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      },

      coupon_set_date: [
        {
          value: '0',
          label: '天'
        }, {
          value: '1',
          label: '自然周'
        }, {
          value: '2',
          label: '自然月'
        }
      ],
      target: null
    }
  },
  methods: {
    // 选择优惠券弹窗
    handleToCallDialog () {
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.coupon_info
      }
      this.$http.$emit('V-AddCoupon', obj)
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data) {
      let couponArr = this.formatCoupon(data)
      let oldArr = this.unique([...this.coupon_info, ...couponArr], 'id')
      let couponKey = []
      couponArr.map((item) => {
        couponKey.push(item.id)
      })
      this.coupon_info = oldArr.filter((item) => {
        return couponKey.includes(item.id)
      })
      console.log(this.coupon_info)
    },
    // 添加优惠券初始项
    formatCoupon (data) {
      let arry = []
      let couponData = {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      }
      data.map(item => {
        arry.push(Object.assign({}, item, { send_num: '', coupon_set: couponData }))
      })
      console.log(arry)
      return arry
    },
    // 设置优惠券内容弹窗
    handleCouponSet (scope) {
      let target = this.coupon_info[scope.$index]
      this.coupon_set = JSON.parse(JSON.stringify(target.coupon_set))
      this.couponSetDialogFlag = true
      this.target = scope.$index
    },
    // 确认设置优惠券
    confrimCouponSet () {
      this.coupon_info[this.target].coupon_set = JSON.parse(JSON.stringify(this.coupon_set))
      this.couponSetDialogFlag = false
    },
    // 删除
    handleCouponDel (index) {
      this.$confirm('是否删除该优惠券？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.coupon_info.splice(index, 1)
      }).catch(() => {

      })
    },
    // 同id去重
    unique (arr, key) {
      let map = new Map()
      arr.forEach((item, index) => {
        if (!map.has(item[key])) {
          map.set(item[key], item)
        }
      })
      return [...map.values()]
    },
    submit () {
      this.coupon_info.forEach(element => {
        var voucher = {}
        voucher.voucherId = element.id
        voucher.totalAmount = element.send_num
        voucher.immediatelyGrantAmount = element.coupon_set.immediatelyGrantAmount
        voucher.timingAmount = element.coupon_set.timingAmount
        voucher.timingEvery = element.coupon_set.timingEvery
        voucher.timingTime = element.coupon_set.timingTime
        voucher.timingUnit = element.coupon_set.timingUnit
        this.param.couponPackVoucher.push(voucher)
      })
      this.param.startTime = this.effectiveDate[0]
      this.param.endTime = this.effectiveDate[1]
      addCouponPackage(this.param).then((res) => {
        if (res.error === 0) {
          this.$message({
            type: 'success',
            message: '保存成功!'
          })
          this.$router.push({
            name: 'coupon_package'
          })
        } else {
          this.$message({
            type: 'fail',
            message: '保存失败!'
          })
        }
      })
    }
  },
  computed: {
    crlfFormat () {
      return this.param.actRule.replace(/\n/g, '<br />')
    }
  },
  mounted () {

  }
}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  background-color: #fff;
  margin: 10px;
  margin-bottom: 62px;
  .coupon_settings {
    max-width: 960px;
    margin: 0 auto;
    display: flex;
    .left_preview {
      width: 310px;
      // max-height: 600px;
      overflow-y: auto;
      &::-webkit-scrollbar {
        width: 9px;
        height: 9px;
      }
      &::-webkit-scrollbar-track {
        border-radius: 8px;
        background-color: rgba(0, 0, 0, 0);
      }

      &::-webkit-scrollbar-track:hover {
        background-color: rgba(0, 0, 0, 0.06);
      }

      &::-webkit-scrollbar-track:active {
        background-color: rgba(0, 0, 0, 0.1);
      }

      &::-webkit-scrollbar-thumb {
        border-radius: 8px;
        background-color: rgba(0, 0, 0, 0.1);
      }

      &::-webkit-scrollbar-thumb:hover {
        background-color: rgba(0, 0, 0, 0.4);
      }

      &::-webkit-scrollbar-thumb:active {
        background: rgba(0, 0, 0, 0.6);
      }
      .left_top {
        width: 100%;
        height: 55px;
        color: #000;
        background: url("$imageHost + '/image/admin//cou_top_bg.png") no-repeat
          100%/100%;
        text-align: center;
        line-height: 75px;
      }
      .left_content {
        width: 100%;
        min-height: 450px;
        background: linear-gradient(left, #ff5666, #e6558e);
        position: relative;
        padding-bottom: 20px;
        .content_bg {
          width: 100%;
          position: absolute;
          > img {
            width: 100%;
          }
        }
        .content_info {
          position: relative;
          z-index: 2;
          .text_title {
            font-size: 35px;
            color: #fff;
            text-align: center;
            padding-top: 60px;
            font-weight: bold;
            font-style: italic;
          }
          .package_info {
            background: #f0a6b5;
            margin: 0 auto;
            width: 97%;
            margin-top: 200px;
            overflow: hidden;
            .package_title {
              line-height: 44px;
              color: #fff;
              font-size: 14px;
              text-align: center;
              > span {
                display: inline-block;
                margin: 0 10px;
                position: relative;
                &::before {
                  content: "";
                  width: 4px;
                  height: 4px;
                  background-color: #fff;
                  border-radius: 50%;
                  position: absolute;
                  top: 22px;
                  left: -14px;
                }
                &::after {
                  content: "";
                  width: 4px;
                  height: 4px;
                  background-color: #fff;
                  border-radius: 50%;
                  position: absolute;
                  top: 22px;
                  right: -14px;
                }
              }
            }
            .coupon_info {
              display: flex;
              flex-direction: column;
              width: 97%;
              margin: 0 auto;
              margin-bottom: -10px;
              .coupon_item {
                margin-bottom: 10px;
                height: 100px;
                display: flex;
                > div {
                  background-color: #fff;
                  height: 100px;
                }
                .coupon_left {
                  width: 100px;
                  text-align: center;
                  display: flex;
                  flex-direction: column;
                  justify-content: center;
                  align-items: center;
                  .coupon_price {
                    color: #f66;
                    margin-bottom: 10px;
                    > span {
                      font-size: 18px;
                      font-weight: 600;
                    }
                  }
                  .coupon_rule {
                    color: #999;
                    font-size: 14px;
                  }
                }
                .coupon_middle {
                  width: 25px;
                  background: none;
                  > img {
                    width: 100%;
                    height: 100%;
                  }
                }
                .coupon_right {
                  flex: 1;
                  display: flex;
                  flex-direction: column;
                  justify-content: center;
                  align-items: flex-start;
                  padding-left: 10px;
                  position: relative;
                  overflow: hidden;
                  .coupon_name {
                    font-weight: bold;
                    font-size: 14px;
                    margin-bottom: 5px;
                    width: 100%;
                    text-overflow: ellipsis;
                    overflow: hidden;
                    white-space: nowrap;
                  }
                  .coupon_limits {
                    margin-bottom: 15px;
                    font-size: 13px;
                  }
                  .coupon_time {
                    color: #999;
                    font-size: 12px;
                  }
                  .coupon_icon {
                    position: absolute;
                    right: -15px;
                    top: 8px;
                    background: #fead2d;
                    width: 64px;
                    font-size: 12px;
                    color: #fff;
                    transform: rotate(40deg);
                    text-align: center;
                  }
                }
              }
            }
            .btn_get {
              width: 280px;
              line-height: 34px;
              text-align: center;
              color: #c93f0e;
              background: linear-gradient(top, #ffd47b, #fcc02a);
              border-radius: 30px;
              font-weight: bold;
              font-size: 13px;
              margin: 10px auto;
            }
          }
          .package_rule {
            background: #f0a6b5;
            margin: 0 auto;
            width: 97%;
            margin-top: 20px;
            overflow: hidden;
            padding: 10px;
            > .rule_title {
              color: #fff;
              font-size: 14px;
              font-weight: 600;
            }
            > .rule_info {
              word-break: break-all;
              color: #fff;
              font-size: 14px;
              font-weight: 600;
            }
          }
        }
      }
    }
    .right_settings {
      flex: 1;
      margin-left: 15px;
      .set_box {
        border: 1px solid #e5e5e5;
        background: #f8f8f8;
        padding: 0 12px 22px;
        border-radius: 3px;
        .set_title {
          border-bottom: 1px solid #e5e5e5;
          line-height: 40px;
          font-size: 14px;
        }
        .set_item {
          display: flex;
          margin-top: 15px;
          > .item_title {
            width: 150px;
            font-size: 14px;
            text-align: right;
            line-height: 32px;
            color: #333;
            > em {
              color: red;
            }
          }
          > .item_right {
            flex: 1;
            line-height: 32px;
            > .item_tips {
              color: #999;
              font-size: 12px;
            }
            > .package_get_choose {
              font-size: 14px;
              color: #333;
            }
          }
        }
        > .coupon_set_box {
          padding-left: 80px;
          > .coupon_set_table {
            .coupon_info {
              display: flex;
              flex-direction: column;
              justify-content: space-between;
              align-items: center;
              min-width: 0;
              .coupon_rule {
                color: #999;
                font-size: 12px;
              }
              .coupon_price {
                color: #f66;
                font-size: 12px;
                > span {
                  font-size: 14px;
                  font-weight: 600;
                }
              }
              .coupon_name {
                font-weight: bold;
                font-size: 12px;
                text-overflow: ellipsis;
                overflow: hidden;
                white-space: nowrap;
                min-width: 0;
              }
            }
          }
          > .bottom_tips_box {
            font-size: 12px;
            color: #999;
            padding-left: 20px;
            display: flex;
            line-height: 32px;
            > .tips_content {
              flex: 1;
            }
          }
        }
      }
    }
  }
}
.coupon_set_p {
  text-align: center;
  > p {
    font-size: 12px;
  }
}
.default_input {
  width: 150px;
}
.small_input {
  width: 80px;
}
.coupon_info_set {
  display: flex;
  flex-direction: column;
  margin-bottom: -15px;
  > p {
    margin-bottom: 15px;
  }
}
.content {
  /deep/ .couponSetDialog {
    width: 390px;
    .el-dialog__header {
      background: #f3f3f3;
      padding-top: 10px;
      .el-dialog__title {
        font-size: 14px;
      }
      .el-dialog__headerbtn {
        top: 10px;
      }
    }
  }
}
.footer {
  position: fixed;
  bottom: 0;
  left: 160px;
  right: 10px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  z-index: 3;
}
</style>
