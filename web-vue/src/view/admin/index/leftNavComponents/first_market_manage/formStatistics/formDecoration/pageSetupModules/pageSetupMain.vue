<template>
  <div class="pageSetupMain">
    <div class="listContainer">
      <span>标题：</span>
      <el-input
        size="small"
        v-model="ruleForm.page_name"
      ></el-input>
    </div>
    <div class="listContainer">
      <span>有效期：</span>
      <el-radio
        v-model="ruleForm.is_forever_valid"
        label="1"
      >永久有效</el-radio>
      <el-radio
        v-model="ruleForm.is_forever_valid"
        label="0"
      >固定日期</el-radio>
    </div>
    <!--选中固定日期显示的隐藏模块-->
    <div
      class="listContainer"
      v-if="ruleForm.is_forever_valid==='0'"
    >
      <span></span>
      <div class="fixedDate">
        <div class="timeContainer">
          <div class="timeTitle">开始时间</div>
          <el-date-picker
            size="small"
            v-model="ruleForm.start_time"
            type="datetime"
            default-time="12:00:00"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <div
          class="timeContainer"
          style="margin-top:10px"
        >
          <div class="timeTitle">结束时间</div>
          <el-date-picker
            size="small"
            v-model="ruleForm.start_time"
            type="datetime"
            default-time="12:00:00"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
      </div>
    </div>
    <div class="listContainer">
      <span>提交次数限制：</span>
      <el-radio
        v-model="ruleForm.post_times"
        label="1"
      >不限次数</el-radio>
      <el-radio
        v-model="ruleForm.post_times"
        label="0"
      >限制次数</el-radio>
    </div>
    <!--选中限制次数显示的隐藏模块-->
    <div
      class="listContainer"
      v-if="ruleForm.post_times==='0'"
    >
      <span></span>
      <div
        class="fixedDate"
        style="width:308px"
      >
        <div class="top">
          <div class="leftName">每天</div>
          <el-input
            v-model="ruleForm.day_times"
            size="small"
          ></el-input>
          <div class="leftName">,累计</div>
          <el-input
            v-model="ruleForm.total_times"
            size="small"
          ></el-input>
        </div>
        <div class="tips">
          默认1次，0代表不限制，累计次数必须大于每天次数
        </div>
      </div>
    </div>
    <div class="listContainer">
      <span>总反馈数量限制：</span>
      <div class="totleNume">达<el-input
          size="small"
          v-model="ruleForm.get_times"
        ></el-input>次后不可提交</div>
    </div>
    <div class="listContainer">
      <span></span>
      <div style="color:#999">默认为0，0代表不限制</div>
    </div>
    <div class="listContainer">
      <span>底部导航：</span>
      <el-radio
        v-model="ruleForm.has_bottom"
        label="1"
      >添加</el-radio>
      <el-radio
        v-model="ruleForm.has_bottom"
        label="0"
      >不添加</el-radio>
    </div>
    <div class="listContainer">
      <span>提交按钮文字：</span>
      <el-input
        size="small"
        v-model="ruleForm.notice_name"
        placeholder="请输入提交按钮文字"
      ></el-input>
    </div>
    <div class="listContainer">
      <span>提交按钮文字颜色：</span>
      <el-color-picker
        v-model="ruleForm.font_color"
        show-alpha
        :predefine="predefineColors"
      >
      </el-color-picker>
    </div>
    <div class="listContainer">
      <span>提交按钮背景颜色：</span>
      <el-color-picker
        v-model="ruleForm.bg_color"
        show-alpha
        :predefine="predefineColors"
      >
      </el-color-picker>
    </div>
    <div class="listContainer">
      <span>表单海报背景图：</span>
      <div class="bottomDiv">
        <div class="bgBottom">

          <img
            v-if="!ruleForm.bg_img"
            :src="$imageHost+'/image/admin/add_img_bg.png'"
            class="bgImgDiv"
            @click="handleToAddImg()"
          />
          <img
            v-else
            style="width:100%;height:40px"
            :src="ruleForm.bg_img"
            @click="handleToAddImg()"
          >
        </div>
        <span class="sharePic">{{$t('pageSetUp.recommendedDimensions')}}:800*800像素</span>
      </div>
    </div>
    <div class="listContainer">
      <span>提交自定义跳转：</span>
      <span style="color:#999;width:210px">提交成功可引导用户浏览其他内容</span>
    </div>
    <div class="listContainer">
      <span></span>
      <el-checkbox v-model="ruleForm.set_own_link">自定义跳转</el-checkbox>
      <div class="toCheck">查看示例
        <div class="examHidden">
          <img :src="$imageHost+'/image/admin/share/form_success_exapmle.jpg'">
        </div>
      </div>
    </div>
    <div class="listContainer">
      <span></span>
      <div class="customLinks">
        <div class="customLinksList">
          <div>按钮名称：</div>
          <el-input
            size="small"
            v-model="ruleForm.custom_btn_name"
          ></el-input>
        </div>
        <div class="customLinksList">
          <div>跳转链接：</div>
          <div class="toChoiseLink">选择跳转链接</div>
        </div>
      </div>
    </div>
    <div class="listContainer">
      <span>用户授权：</span>
      <el-checkbox v-model="ruleForm.authorized_name">授权手机号</el-checkbox>
      <el-checkbox v-model="ruleForm.authorized_mobile">授权用户信息</el-checkbox>
    </div>
    <div class="listContainer">
      <span></span>
      <div style="color:#999;width:275px">勾选后好友需要先完成授权才能够提交表单</div>
    </div>
    <div class="listContainer linContainer">
      <div class="line"></div>
      <div class="content">参与奖励</div>
      <div class="line"></div>
    </div>
    <div class="listContainer">
      <el-checkbox v-model="ruleForm.send_coupon">参与送优惠卷</el-checkbox>
    </div>
    <!--选中参与优惠卷显示的隐藏模块-->
    <div
      class="listContainer"
      v-if="ruleForm.send_coupon"
    >
      <div class="sendCouponHidden">
        <div class="bgCoupon">
          <img
            :src="$imageHost+'/image/admin/shop_beautify/add_decorete.png'"
            class="bgImgDiv"
            @click="handleToAddImg()"
          />
          <p>添加优惠卷</p>
        </div>
      </div>
    </div>
    <div class="listContainer">
      <div style="color:#999;width:400px;font-size:12px;padding-left:25px">最多可以添加5张优惠券，已过期和已停用的优惠券不能添加</div>
    </div>
    <div class="listContainer sendScore">
      <el-checkbox v-model="ruleForm.send_score">参与送积分</el-checkbox>
      &nbsp;&nbsp;
      <el-input
        size="small"
        v-model="ruleForm.send_score_number"
      ></el-input>&nbsp;&nbsp;分
    </div>
  </div>
</template>
<script>
export default {
  props: {
    pageSet: Object
  },
  data () {
    return {
      predefineColors: [ // 颜色选择器预定义颜色池
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#FF0000'
      ],
      ruleForm: {
        'page_name': '', // 标题input值
        'is_forever_valid': '1', // 有效期radio
        'has_bottom': '0', // 底部导航radio
        'start_time': '', // 开始时间
        'end_time': '', // 结束时间
        'post_times': '1', // 提交次数限制radio
        'day_times': '1', // 每天input值
        'total_times': '1', // 累计input值
        'get_times': '0', // 总反馈数量限制input值
        'notice_name': '', // 提交按钮文字
        'font_color': '#ffffff', // 提交按钮文字颜色
        'bg_color': '#ff6666', // 提交按钮背景颜色
        'bg_img': '', // 表单海报背景图片
        'set_own_link': 0, // 自定义跳转checked
        'custom_btn_name': '', // 自定义按钮名称
        'custom_link_path': '', // 跳转链接
        'custom_link_name': '', // 跳转链接名称
        'send_coupon': 0, // 参与送优惠卷checkbox
        'send_coupon_list': [
          // {
          //   "act_code":"voucher",
          //   "coupon_id":"517",
          //   "use_score":"0",
          //   "score_number":"",
          //   "denomination":"10",
          //   "random_min":"0",
          //   "random_max":"0",
          //   "consume_text":"无门槛",
          //   "receive_text":"库存不限制",
          //   "coupon_name":"测试标签"
          // }
        ], // 选择的优惠卷数据列表
        'send_score': 0, // 参与送积分选中checkbox
        'send_score_number': null, // 输入的送积分input值
        'authorized_name': 0, // 授权手机号
        'authorized_mobile': 0 // 授权用户信息
      }
    }
  },
  watch: {
    lang () {
    },
    pageSet: {
      handler (newData) {
        console.log(newData)
        // 中部传递过来的初始表单配置数据初始回显
      },
      immediate: true,
      deep: true
    },
    ruleForm: {
      handler (newData) {
        console.log(newData)
        this.$emit('hanelToPageSet', newData)
      },
      deep: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 表单海报背景图点击添加图片
    handleToAddImg () {

    }
  }
}
</script>
<style lang="scss" scoped>
.pageSetupMain {
  font-size: 14px;
  .listContainer {
    display: flex;
    margin-bottom: 20px;
    span {
      display: flex;
      width: 140px;
      justify-content: flex-end;
      align-items: center;
    }
    /deep/ .el-input {
      width: 150px;
    }
    .fixedDate {
      width: 280px;
      background: #fff;
      padding: 10px;
      border: 1px solid #ddd;
      font-size: 13px;
      .timeContainer {
        display: flex;
        .timeTitle {
          display: flex;
          align-items: center;
          margin-right: 10px;
        }
      }
      .top {
        display: flex;
        /deep/ .el-input {
          width: 80px;
        }
        .leftName {
          display: flex;
          align-items: center;
          margin-right: 5px;
        }
      }
      .tips {
        font-size: 12px;
        color: #999;
        margin-top: 10px;
      }
    }
    .totleNume {
      /deep/ .el-input {
        width: 80px;
        margin: 0 5px;
      }
    }
    .bottomDiv {
      display: flex;
      .bgBottom {
        width: 70px;
        height: 70px;
        display: flex !important;
        justify-content: center;
        align-items: center;
        border: 1px solid #ccc;
        .bgImgDiv {
          width: 47px;
          height: 44px;

          cursor: pointer;
        }
      }
      .sharePic {
        color: #999;
        width: 155px;
      }
      /deep/ .el-button {
        margin-top: 10px;
      }
    }
    .bgCoupon {
      background: #fff;
      border: 1px solid #e4e4e4;
      text-align: center;
      width: 100px;
      border-radius: 3px;
      padding: 13px 0;
      cursor: pointer;
      p {
        color: #999;
        font-size: 12px;
        margin: 8px 0 0 0;
      }
    }
    .toCheck {
      color: #5a8bff;
      cursor: pointer;
      display: flex;
      align-items: center;
      margin-left: 5px;
      position: relative;
      .examHidden {
        display: none;
        position: absolute;
        left: -254px;
        top: -50px;
        padding: 20px;
        background-color: #fff;
        border-radius: 5px;
        z-index: 100;
        img {
          width: 200px;
          height: 355.74px;
          border: 1px solid #eee;
        }
        &::before {
          content: " ";
          position: absolute;
          top: 48px;
          right: -8px;
          width: 14px;
          height: 14px;
          background-color: #fff;
          transform: rotate(-45deg);
          z-index: 4;
          box-shadow: 3px 3px 4px #e5e5e5;
        }
      }
      &:hover {
        .examHidden {
          display: block !important;
        }
      }
    }
    .customLinks {
      .customLinksList {
        margin-bottom: 20px;
        display: flex;
        align-items: center;
        .toChoiseLink {
          cursor: pointer;
          color: #5a8bff;
        }
      }
    }
    .line {
      width: 35%;
      height: 1px;
      background: #eee;
    }
    .content {
      width: 20%;
      text-align: center;
    }
    .sendCouponHidden {
      border: 1px solid #eee;
      background: #fff;
      width: 360px;
      padding: 10px;
      margin-left: 15px;
    }
  }
  .sendScore {
    align-items: center;
  }
  .linContainer {
    display: flex;
    align-items: center;
  }
}
</style>
