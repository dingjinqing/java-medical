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
  </div>
</template>
<script>
export default {
  props: {
    pageSet: Object
  },
  data () {
    return {
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
        'notice_name': '',
        'font_color': '#ffffff',
        'bg_color': '#ff6666',
        'bg_img': '',
        'set_own_link': 0,
        'custom_btn_name': '',
        'custom_link_path': '',
        'custom_link_name': '',
        'send_coupon': 0,
        'send_score': 0,
        'authorized_name': 0,
        'authorized_mobile': 0
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
  }
}
</style>
