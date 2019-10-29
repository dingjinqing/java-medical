<template>
  <div class="addPayRewardAct">
    <div class="addPayRewardAct_main">

      <!-- 左侧内容区域 -->
      <div class="leftContent">
        <!-- 标题图片部分 -->
        <div class="titles">
          <img
            :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'"
            alt=""
          >
        </div>
        <div class="carousel">
          <el-carousel
            trigger="click"
            height="573px"
            arrow="never"
            :autoplay="false"
          >
            <el-carousel-item
              v-for="(item,index) in carouselList"
              :key="index"
            >
              <img
                :src="item.src"
                alt=""
                style="height: 100%; width: 100%;"
              >
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="rightContent">
        <!-- 活动配置区域 -->
        <section class="container">
          <div class="title">活动配置</div>
          <el-form
            label-position="right"
            label-width="100px"
          >
            <el-form-item
              label="活动名称："
              style="margin-top:15px"
            >
              <el-input
                size="small"
                style="width: 200px"
                placeholder="最多支持10个字"
              ></el-input>
            </el-form-item>

            <el-form-item label="活动有效期：">
              <div>
                <el-radio-group v-model="timeRange">
                  <div style="display: flex">
                    <el-radio label="1">固定时间</el-radio>
                    <div style="display: flex">
                      <el-date-picker
                        v-model="value"
                        type="datetime"
                        placeholder="选择开始时间"
                        style="width:120px"
                        size="small"
                      >
                      </el-date-picker>
                      <div>至</div>
                      <el-date-picker
                        v-model="value1"
                        type="datetime"
                        placeholder="选择结束时间"
                        style="width:120px"
                        size="small"
                      >
                      </el-date-picker>
                    </div>
                  </div>
                  <div>
                    <el-radio label="2">永久有效</el-radio>
                  </div>
                </el-radio-group>
              </div>
            </el-form-item>

            <el-form-item label="优先级：">
              <el-input
                size="small"
                style="width:200px"
              ></el-input>
              <div style="line-height:30px;font-size:12px">用于区分不同支付有礼活动的优先级，请填写正整数，数值越大优先级越高</div>
            </el-form-item>

            <el-form-item
              label="触发条件："
              class="triggerCondition"
            >
              <span>以下条件为"且"的关系</span>
              <div>
                <span>商品条件：</span>
                <el-radio-group v-model="goods">
                  <el-radio label="1">全部商品</el-radio>
                  <el-radio label="2">部分商品</el-radio>
                </el-radio-group>
                <div
                  v-if="goods==='2'"
                  class="partGoods"
                >
                  <div>+ 选择商品</div>
                  <div>+ 选择平台分类</div>
                  <div>+ 选择商家分类</div>
                </div>
              </div>
              <div>
                <span>支付条件：</span>
                <span>每笔订单满</span>
                <el-input
                  size="small"
                  style="width: 100px"
                ></el-input>
                <span>元，参与活动</span>
              </div>
            </el-form-item>

            <el-form-item label="参与限制：">
              <div style="display: flex">
                <span>每个用户可参加</span>
                <el-input
                  size="small"
                  style="width: 100px"
                ></el-input>
                <span>次活动</span>
              </div>
            </el-form-item>
          </el-form>
        </section>

        <!-- 支付奖励区域 -->
        <section class="container">
          <div class="title">支付奖励
            <!-- <div class="name">支付奖励</div>
            <div>
              <span>最多可添加5次支付的奖励</span>
              <div>+添加奖励</div>
            </div> -->
          </div>
          <el-form
            label-width="100px"
            label-position="right"
          >
            <!-- <el-form-item :label="第+(index+1)+次支付奖励"></el-form-item> -->
            <el-form-item label="支付奖励：">
              <el-radio-group v-model="payReward">
                <el-radio label="1">无奖品</el-radio>
                <el-radio label="2">普通优惠券</el-radio>
                <el-radio label="3">分裂优惠券</el-radio>
                <el-radio label="4">幸运大抽奖</el-radio>
                <el-radio label="5">余额</el-radio>
                <el-radio label="6">奖品</el-radio>
                <el-radio label="7">积分</el-radio>
                <el-radio label="8">自定义</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item
              v-if="payReward==='2'"
              label="普通优惠券"
            >
              <div class="coupon">
                <img
                  :src="$imageHost+'/image/admin/shop_beautify/add_decorete.png'"
                  alt=""
                >
                <p>添加优惠券</p>
              </div>
              <div class="textTips">最多可以添加5张优惠券，已过期和已停用的优惠券不能添加</div>
            </el-form-item>

            <el-form-item
              v-if="payReward==='3'"
              label="分裂优惠券"
            >
              <div class="coupon">
                <img
                  :src="$imageHost+'/image/admin/shop_beautify/add_decorete.png'"
                  alt=""
                >
                <p>添加优惠券</p>
              </div>
              <div class="textTips">最多可以添加1张优惠券，已过期和已停用的优惠券不能添加</div>
            </el-form-item>

            <el-form-item
              v-if="payReward==='4'"
              label="幸运大抽奖"
            >
              <el-select
                size="small"
                style="width: 120px"
                v-model="activity"
                placeholder="请选择抽奖活动"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item
              v-if="payReward==='5'"
              label="余额："
            >
              <el-input
                size="small"
                style="width:120px"
                placeholder="请输入余额"
              ></el-input>
            </el-form-item>

            <el-form-item
              v-if="payReward==='6'"
              label="奖品："
            >
              <div class="addGoods">+&nbsp;添加奖品</div>
            </el-form-item>
            <el-form-item
              v-if="payReward==='6'"
              label="赠品有效期："
            >
              <div>
                <el-input
                  size="small"
                  style="width:100px"
                ></el-input>
                <span>天</span>
                <div>中奖用户需在有效期内领取，过期后将无法领取</div>
              </div>
            </el-form-item>

            <el-form-item
              v-if="payReward==='7'"
              label="积分"
            >
              <el-input
                size="small"
                style="width:120px"
                placeholder="请输入积分"
              ></el-input>
            </el-form-item>

            <el-form-item
              v-if="payReward==='8'"
              label="活动图片："
            >
              <div style="display: flex">
                <div class="size">
                  <img
                    :src="$imageHost+'/image/admin/btn_add.png'"
                    alt=""
                  >
                </div>
                <div style="margin-top:10px">建议尺寸：560px * 700px</div>
              </div>
            </el-form-item>
            <el-form-item
              v-if="payReward==='8'"
              label="设置链接："
            >
              <el-input
                size="small"
                style="width:200px"
              ></el-input>
              <span>选择链接</span>
            </el-form-item>

            <el-form-item
              v-if="payReward !== '1'"
              label="奖品份数："
            >
              <div>
                <el-input
                  size="small"
                  style="width:100px"
                ></el-input>
                <span>份(已发0份) </span>
                <span>填写0表示不限制</span>
                <div>发放人数达到奖品份数，后续用户无法再获取支付奖励</div>
              </div>

            </el-form-item>

          </el-form>

        </section>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      carouselList: [
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift1.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift2.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift3.jpg' }
      ],
      timeRange: '1',
      value: '',
      value1: '',
      goods: '1',
      payReward: '1',
      activity: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }]
    }
  }
}

</script>
<style lang="scss" scoped>
.addPayRewardAct {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  font-size: 14px;
  .addPayRewardAct_main {
    position: relative;
    display: flex;
    justify-content: center;
    background: #fff;
    padding: 40px 0 96px;
    .leftContent {
      width: 323px;
      height: 573px;
      border: 1px solid #ccc;
      background: #f5f5f5;
      .titles {
        height: 55px;
        img {
          width: 323px;
        }
      }
      .carousel {
        width: 323px;
        height: 55px;
      }
    }
    .rightContent {
      margin-left: 20px;
      .container {
        border: 1px solid #e5e5e5;
        background: #f8f8f8;
        width: 550px;
        padding-left: 10px;
        border-radius: 4px;
        margin-bottom: 10px;
        .title {
          height: 40px;
          line-height: 40px;
          border-bottom: 1px solid #eee;
        }

        /deep/ .el-form-item {
          margin-bottom: 16px;
        }
        .triggerCondition {
          .partGoods {
            margin-left: 73px;
            div {
              width: 120px;
              height: 30px;
              line-height: 30px;
              text-align: center;
              color: #5a8bff;
              border: 1px solid #ccc;
              background: #fff;
              cursor: pointer;
              margin: 10px 0;
              margin-bottom: 10px;
            }
          }
        }
        .coupon {
          background: #fff;
          border: 1px solid #e4e4e4;
          text-align: center;
          padding: 13px 0;
          cursor: pointer;
          float: none;
          width: 100px;
          height: 90px;
          img {
            margin-top: 10px;
          }
          p {
            color: #999;
            font-size: 12px;
            margin-top: -10px;
          }
        }
        .textTips {
          color: #999;
          margin-top: 10px;
          font-size: 12px;
        }
        .addGoods {
          width: 120px;
          height: 30px;
          line-height: 30px;
          text-align: center;
          color: #5a8bff;
          border: 1px solid #ccc;
          background: #fff;
          cursor: pointer;
        }
        .size {
          width: 70px;
          height: 70px;
          box-shadow: 0 0 0 #fff;
          color: #9a9a9a;
          border: none;
          margin-right: 10px;
        }
      }
    }
  }
}
</style>
