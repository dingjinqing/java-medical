<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部优惠券"
          name="first"
        ></el-tab-pane>
        <el-tab-pane
          label="进行中"
          name="second"
        ></el-tab-pane>
        <el-tab-pane
          label="未开始"
          name="third"
        ></el-tab-pane>
        <el-tab-pane
          label="已过期"
          name="fourth"
        ></el-tab-pane>
        <el-tab-pane
          label="已停用"
          name="fifth"
        ></el-tab-pane>
      </el-tabs>
      <div class="add_coupon_content">
        <div class="coupon_content">
          <div class="fl content_left">
            <div class="fl_title">
              <div>优惠券</div>
            </div>
            <div class="info">
              <div class="info_top">
                <div
                  class="vip_exclusive"
                  style="display:none;"
                >会员专享</div>
                <div class="coupon_name">{{coupon_name_input ? coupon_name_input : '优惠券名称'}}</div>
                <div class="coupon_vou">
                  <span v-if="preferential_type === '0'">￥{{denomination ? denomination : '0.00'}}</span>
                  <span v-else>{{denomination2?denomination2:'0'}}折</span>
                </div>
                <div class="coupon_dis"></div>
              </div>
            </div>
            <div class="info_mid">
              <div class="clearfix">
                <span class="sub_title">有效日期</span>
                <span class=date>
                  <span v-if="available_period === '0'">{{coupon_date_datetimerange}}</span>
                  <span v-else>{{coupon_date_info}}</span>
                </span>
              </div>
              <div>
                <span class="sub_title">使用限制</span>
                <span
                  class="all"
                  v-if="using_limit==='0'"
                >无限制</span>
                <span v-else>订单满<span>{{least_consume?least_consume:'0'}}</span>元可用</span>
                <span
                  class="part"
                  v-if="radio_goods === '1'"
                >部分商品可用</span>
              </div>
            </div>
            <div class="info_bot">
              <div
                class="code"
                v-if="validation_code != ''"
              >请输入领取码</div>
              <div class="use">立即使用</div>
              <div>
                <span class="sub_title">使用说明</span>
                <div class="instruction">{{use_explain?use_explain:'暂无使用说明'}}</div>
              </div>
            </div>
          </div>
          <div class="content_right">
            <div class="coupon_info">
              <div class="coupon_info_title">优惠券基础信息</div>
              <ul>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>优惠券名称：
                  </div>
                  <div class="ft">
                    <el-input
                      size="small"
                      class="coupon_name_input"
                      placeholder="最多输入10个字"
                      v-model="coupon_name_input"
                      maxlength="10"
                    ></el-input>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>优惠类型：
                  </div>
                  <div class="to_choose">
                    <p>
                      <el-radio
                        v-model="preferential_type"
                        label="0"
                      >指定金额</el-radio>
                      <span>面值：<el-input
                          v-model="denomination"
                          size="small"
                          class="small_input"
                        ></el-input> 元</span>
                    </p>

                    <p>
                      <el-radio
                        v-model="preferential_type"
                        label="1"
                      >折扣<el-input
                          v-model="denomination2"
                          size="small"
                          class="small_input"
                        ></el-input>
                        折</el-radio>

                    </p>

                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>使用门槛：
                  </div>
                  <div>
                    <p>
                      <el-radio
                        v-model="using_limit"
                        label="0"
                      >不限制</el-radio>
                    </p>
                    <p>
                      <el-radio
                        v-model="using_limit"
                        label="1"
                      >满<el-input
                          size="small"
                          v-model="least_consume"
                          class="small_input"
                        ></el-input>
                        元可用
                      </el-radio>
                    </p>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>是否需要兑换：
                  </div>
                  <div>
                    <el-radio
                      v-model="redeem_gift"
                      label="0"
                    >不需要</el-radio>
                    <el-radio
                      v-model="redeem_gift"
                      label="1"
                    >需要兑换</el-radio>
                    <p v-if="redeem_gift == 1">
                      <el-input
                        v-model="score_number"
                        class="small_input"
                        size="small"
                      ></el-input>
                      积分兑换
                    </p>
                  </div>
                </li>
              </ul>
            </div>
            <div class="coupon_info">
              <div class="coupon_info_title">基本规则</div>
              <ul>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>每人限领：
                  </div>
                  <div class="ft">
                    <el-select
                      v-model="get_limit_select"
                      size="small"
                    >
                      <el-option
                        v-for="(item, index) in get_limit"
                        :key="index"
                        :value="index"
                        :label="item"
                      ></el-option>
                    </el-select>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    会员专享：
                  </div>
                  <div>
                    <p>
                      <el-checkbox
                        v-model="is_exclusive"
                        label="用户持有会员卡才可以参与活动"
                      ></el-checkbox>
                    </p>
                    <div v-if="is_exclusive">
                      <el-select
                        v-model="choose_card_list"
                        placeholder="请选择会员卡"
                        multiple
                        size="small"
                      >
                        <el-option
                          v-for="item in card_list"
                          :key="item.id"
                          :label="item.card_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                      <span class="card_links">
                        <a>刷新</a><span> | </span>
                        <a>新建会员卡</a><span> | </span>
                        <a>管理会员卡</a>
                      </span>
                    </div>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>会员专享：
                  </div>
                  <div>
                    <p>
                      <el-radio
                        v-model="available_period"
                        label="0"
                      >固定日期</el-radio>
                    </p>
                    <p
                      v-if='available_period==="0"'
                      style="margin:15px 0;"
                    >
                      <el-date-picker
                        v-model="coupon_date"
                        type="datetimerange"
                        format="yyyy-MM-dd HH:mm:ss"
                        range-separator="-"
                        start-placeholder="生效时间"
                        end-placeholder="过期时间"
                        size="small"
                      >
                      </el-date-picker>
                    </p>
                    <p>
                      <el-radio
                        v-model="available_period"
                        label="1"
                      >领券开始 <el-input
                          v-model="coupon_date_day"
                          placeholder=""
                          size="small"
                          class="small_input"
                        ></el-input> 天 <el-input
                          v-model="coupon_date_hour"
                          placeholder=""
                          size="small"
                          class="small_input"
                        ></el-input> 小时 <el-input
                          v-model="coupon_date_minite"
                          placeholder=""
                          size="small"
                          class="small_input"
                        ></el-input> 分钟内有效 </el-radio>
                    </p>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>发放总量：
                  </div>
                  <div>
                    <el-input
                      v-model="total_amount"
                      placeholder=""
                      size="small"
                      class="small_input"
                    ></el-input>张
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    领取码：
                  </div>
                  <div>
                    <el-input
                      v-model="validation_code"
                      placeholder=""
                      size="small"
                      class="small_input"
                    ></el-input>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    <em>*</em>可使用商品：
                  </div>
                  <div>
                    <p>
                      <el-radio
                        v-model="radio_goods"
                        label="0"
                      >全部商品</el-radio>
                    </p>
                    <p>
                      <el-radio
                        v-model="radio_goods"
                        label="1"
                      >指定商品</el-radio>
                    </p>
                    <div v-if="radio_goods === '1'">
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
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    是否隐藏：
                  </div>
                  <div style="display:flex">
                    <div>
                      <el-radio
                        v-model="suit_goods"
                        label="0"
                      >否</el-radio>
                      <el-radio
                        v-model="suit_goods"
                        label="1"
                      >是</el-radio>
                    </div>
                    <span style="flex:1;padding-left:15px;color:red;">
                      隐藏则不显示在前端商品详情页。否则显示到前端商品详情页可以供用户领取。
                    </span>
                  </div>
                </li>
                <li class="content_right_li clearfix">
                  <div class="content_left_title">
                    使用说明：
                  </div>
                  <div>
                    <el-input
                      type="textarea"
                      :rows="5"
                      v-model="use_explain"
                      placeholder="请输入使用说明"
                      resize="none"
                    ></el-input>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
    </div>
    <!--选择商品弹窗-->
    <ChoosingGoods />
    <!--指定商品添加商品分类弹窗-->
    <AppointBusDialog />
    <!--指定商品添加平台分类弹窗-->
    <AppointBrandDialog />
  </div>
</template>
<script>
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods'),
    AppointBusDialog: () => import('@/view/admin/layout/addingBusClassDialog'),
    AppointBrandDialog: () => import('@/view/admin/layout/brandDialog')
  },
  data () {
    return {
      tableData: [],
      activeName: 'second',
      currentPage: 1,
      coupon_name_input: '',
      preferential_type: '0',
      using_limit: '0',
      redeem_gift: '0',
      get_limit: ['不限制', 1, 2, 3, 4, 5, 8, 10, 20],
      get_limit_select: 0,
      is_exclusive: false,
      card_list: [
        { id: 1, card_name: '翻车现场' },
        { id: 2, card_name: '翻车现场2' },
        { id: 3, card_name: '翻车现场3' },
        { id: 4, card_name: '翻车现场4' },
        { id: 5, card_name: '翻车现场5' },
        { id: 6, card_name: '翻车现场6' }
      ],
      choose_card_list: [],
      available_period: '0',
      coupon_date: null,
      coupon_date_day: '',
      coupon_date_hour: '',
      coupon_date_minite: '',
      total_amount: '',
      validation_code: '',
      radio_goods: '0',
      suit_goods: '0',
      use_explain: '',
      denomination: null,
      denomination2: null,
      least_consume: null,
      score_number: null,
      AtreeType: null,
      noneBlockDiscArr: [
        {
          name: '添加商品',
          num: '1'
        },
        {
          name: '添加商品分类',
          num: ''
        },
        {
          name: '添加平台分类',
          num: '2'
        }
      ]
    }
  },
  methods: {
    dataDefalut () {
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
    },
    // 点击指定商品出现的添加类弹窗汇总
    hanldeToAddGoodS (index) {
      console.log(index)
      switch (index) {
        case 0:
          this.$http.$emit('choosingGoodsFlag', index)
          break
        case 1:
          this.AtreeType = 1
          this.$http.$emit('AaddingBusClassDialog', index)
          break
        case 2:
          this.AtreeType = 2
          this.$http.$emit('AuserBrandDialog', index)
      }
    }
  },
  filters: {
  },
  computed: {
    coupon_date_info () {
      if (this.coupon_date_day || this.coupon_date_hour || this.coupon_date_minite) {
        return `领券日开始${this.coupon_date_day ? this.coupon_date_day + '天' : ''}${this.coupon_date_hour ? this.coupon_date_hour + '时' : ''}${this.coupon_date_minite ? this.coupon_date_minite + '分' : ''}内有效`
      } else {
        return `领券日开始X天X时X分内有效`
      }
    },
    coupon_date_datetimerange () {
      if (this.coupon_date) {
        let dateStr = ''
        this.coupon_date.map(item => {
          let date = new Date(item)
          let year = date.getFullYear()
          let month = (date.getMonth() + 1).toString().padStart(2, '0')
          let day = date.getDate().toString().padStart(2, '0')
          let hour = date.getHours().toString().padStart(2, '0')
          let minute = date.getMinutes().toString().padStart(2, '0')
          let second = date.getSeconds().toString().padStart(2, '0')
          dateStr += `${year}-${month}-${day} ${hour}:${minute}:${second} `
        })
        return dateStr
      } else {
        return `xxxx-xx-xx xx:xx:xx xxxx-xx-xx xx:xx:xx`
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    margin-bottom: 52px;
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
  }
}
.main_coupon {
  padding: 0 20px 20px;
  background: #fff;
  width: 100%;
}
.coupon_content {
  display: flex;
  width: 950px;
  margin: 0 auto;
}
.add_coupon_content .coupon_content .content_left {
  width: 323px;
  height: 510px;
  border: 1px solid #ccc;
  background: #eee;
}
.fl_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  color: white;
  text-align: center;
}
.fl_title div {
  padding-top: 25px;
  font-size: 15px;
}
.info {
  width: 95%;
  margin: 10px auto 0px auto;
  background: white;
}
.info_mid,
.info_bot {
  background: white;
  margin: 0px 10px;
  color: #666;
  font-size: 13px;
}
.info_bot {
  padding: 10px;
}
.vip_exclusive {
  position: absolute;
  top: 10px;
  left: 10px;
  width: 70px;
  line-height: 5px;
  color: #fff;
  border-radius: 15px;
  background-color: rgba(0, 0, 0, 0.1);
  font-size: 12px;
}
.info_top {
  padding: 10px;
  color: white;
  background: url(../../../../../../assets/adminImg/coupon_bg.png) left top
    no-repeat;
  height: 100px;
  background-size: 100% 100%;
  text-align: center;
  font-size: 15px;
}
.info_top div {
  padding: 10px;
}
.info_mid,
.info_bot {
  background: white;
  margin: 0px 10px;
  color: #666;
  font-size: 13px;
}
.info_mid div {
  border-bottom: 1px solid #ddd;
  padding: 15px;
}
.sub_title {
  color: #aaa;
}
.date {
  width: 75%;
  float: right;
}
.small_input {
  width: 70px;
}
.info_bot {
  padding: 10px;
}
.code {
  width: 60%;
  margin: 0 auto;
  text-align: center;
  background: #f6f6f6;
  border-radius: 5px;
  padding: 6px;
  color: #aaa;
}
.use {
  border: 1px solid #ff6666;
  border-radius: 5px;
  padding: 5px;
  width: 30%;
  text-align: center;
  margin: 15px auto;
  color: #ff6666;
}
.instruction {
  background-color: #f6f6f6;
  min-height: 50px;
  margin: 10px auto;
  border-radius: 5px;
  padding: 5px;
  font-size: 12px;
}
.add_coupon_content .coupon_content .content_right {
  flex: 1;
  margin-left: 15px;
}
.coupon_info_title {
  border-bottom: 1px solid #e5e5e5;
  line-height: 40px;
}
.coupon_info {
  border: 1px solid #e5e5e5;
  background: #f8f8f8;
  padding: 0 12px 22px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
  margin-bottom: 10px;
  > ul {
    display: flex;
    flex-direction: column;
    > li {
      display: flex;
      line-height: 32px;
      > .content_left_title {
        width: 130px;
        text-align: right;
        line-height: 32px;
      }
    }
  }
}
.card_links > a {
  color: #409eff;
}
.content_right_li {
  padding: 10px 0 0 0;
}
.content_right_li > div:nth-child(2) {
  flex: 1;
}
.content_right_li em {
  color: #f66;
}
.content_right_li .fl:first-child {
  width: 110px;
  text-align: right;
}
.ft {
  width: 110px;
  float: left;
  line-height: 30px;
  margin-right: 15px;
}
.coupon_name_input {
  width: 160px;
}
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
</style>
