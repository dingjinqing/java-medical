<template>
  <div class="bottomNavigationContent">
    <div class="bottomNavigationContent_main">

      <!-- 左侧内容start -->
      <div class="cententLleft">
        <div class="cententLleft_title"></div>
        <!-- <div class="cententLleft_bottom"> -->
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
        <!-- </div> -->
      </div>
      <!-- 左侧内容end  -->

      <!-- 活动信息部分 -->
      <div class="contentRight">
        <div class="actInfo">活动信息</div>
        <el-form
          label-position="right"
          label-width="100px"
        >
          <el-form-item label="活动名称：">
            <el-input
              size="mini"
              style="width:200px"
            ></el-input>
          </el-form-item>
          <el-form-item label="活动有效期：">
            <el-radio-group v-model="radio">
              <div style="display:flex">
                <el-radio label="1">固定时间</el-radio>
                <div style="margin-left: 10px">
                  <el-date-picker
                    v-if="radio==='1'"
                    style="width: 300px;"
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
              <el-radio label="2">永久有效</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="优先级：">
            <el-input
              size="mini"
              style="width:200px"
            ></el-input>
            <div>用于区分不同分享有礼活动的优先级，请填写正整数，数值越大优先级越高</div>
          </el-form-item>
          <el-form-item label="触发条件：">
            <span>用户分享</span>
            <el-radio-group v-model="trigger">
              <el-radio label="1">全部商品</el-radio>
              <el-radio label="2">指定商品</el-radio>
              <el-radio label="3">实际访问量较少商品</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div
          class="selectGoods"
          v-if="trigger==='2'"
        >+ 选择商品</div>
        <div
          v-if="trigger==='3'"
          style="margin-left:163px"
        >
          <span>实际访问量少于</span>
          <el-input
            size="mini"
            style="width:50px"
          ></el-input> 条的商品
        </div>
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
              @click="handleRules"
            >+ 添加规则</span>
          </div>
        </div>
        <el-form>
          <el-form-item>
            <el-checkbox v-model="checked">仅邀请未访问过店铺的用户有效</el-checkbox>
          </el-form-item>
          <section
            v-for="item in rules.slice(0,3)"
            :key="item.level"
          >
            <el-form-item :label='item.level'>
              <div>{{item.invite}} <el-input
                  size="mini"
                  style="width:60px"
                ></el-input> {{item.person}} <span style="color:#999">{{item.personNumber}}</span>
                <i
                  class="el-icon-delete"
                  style="color:#409eff;cursor:pointer"
                  @click="deleteItem"
                ></i>
              </div>
              <div style="margin-left:43px">{{item.willGet}}
                <el-radio-group v-model="willGet">
                  <el-radio label="1">{{item.integral}}</el-radio>
                  <el-radio label="2">{{item.coupon}}</el-radio>
                  <el-radio label="3">{{item.luckyDraw}}</el-radio>
                </el-radio-group>
              </div>
              <div
                style="margin-left:43px"
                v-if="willGet === '1'"
              >积分：
                <el-input
                  size="mini"
                  style="width: 150px"
                ></el-input>
              </div>

              <div v-if="willGet === '2'">
                <div style="margin-left:43px;margin-top: 10px;display:flex">
                  <div style="height:30px;line-height:30px">优惠券：</div>
                  <el-select
                    style="width: 150px;"
                    size="mini"
                    v-model="gift"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
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
                <div style="margin-left: 120px">优惠券可用库存{{num}}份数</div>
              </div>

              <div v-if="willGet === '3'">
                <div style="margin-left:43px;margin-top: 10px;display:flex">
                  <div style="height:30px;line-height:30px">幸运大抽奖：</div>
                  <el-select
                    style="width: 150px;"
                    size="mini"
                    v-model="gift"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
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
                {{item.rewardTimes}}
                <el-input
                  size="mini"
                  style="width:150px"
                ></el-input> {{item.times}}</div>
            </el-form-item>
          </section>
        </el-form>

      </div>

      <!--保存-->
      <div class="footer">
        <div
          class="save"
          @click="saveShopStyle()"
        >{{$t('shopStyle.saveText')}}</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
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
      radio: '',
      trigger: '',
      willGet: '1',
      gift: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
      checked: '',
      value: '',
      num: 0,
      effectiveDate: '',
      rules: [
        { level: '一级', invite: '邀请满', person: '人', personNumber: '可填写1-5人', willGet: '可获得', integral: '积分', coupon: '优惠券', luckyDraw: '幸运大抽奖', rewardTimes: '奖品份数：', times: '份', type: 1 },
        { level: '二级', invite: '邀请满', person: '人', personNumber: '可填写1-5人', willGet: '可获得', integral: '积分', coupon: '优惠券', luckyDraw: '幸运大抽奖', rewardTimes: '奖品份数：', times: '份', type: 2 },
        { level: '三级', invite: '邀请满', person: '人', personNumber: '可填写1-5人', willGet: '可获得', integral: '积分', coupon: '优惠券', luckyDraw: '幸运大抽奖', rewardTimes: '奖品份数：', times: '份', type: 3 }
      ],
      flag: false
    }
  },
  methods: {
    handleRules () {
      console.log(111)
    },
    deleteItem () {

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
