<template>
  <div class="content">
    <div class="main">
      <el-form
        :model="ruleForm"
        label-width="150px"
        labelPosition='right'
      >
        <el-form-item
          label="活动名称:"
          prop=""
        >
          <el-input></el-input>
          <span>查看活动规则</span>
        </el-form-item>
        <el-form-item
          label="活动有效期："
          prop=""
        >
          <el-input></el-input>
          <span>至</span>
          <el-input></el-input>
        </el-form-item>
        <el-form-item
          label="奖励类型："
          prop=""
        >
          <el-radio
            v-model="radio"
            label="1"
          >
            赠送商品
          </el-radio>
          <el-radio
            v-model="radio"
            label="2"
          >折扣商品</el-radio>
          <el-radio
            v-model="radio"
            label="3"
          >赠送优惠券</el-radio>
          <div style="width:120px;height:30px;line-height:30px;text-align:center;border:1px solid #ccc">+选择商品</div>
          <div></div>
        </el-form-item>
        <el-form-item
          label="奖励设置："
          prop=""
        >
        </el-form-item>
        <el-form-item
          label="奖励有效期："
          prop=""
        >
          <div style="display:flex">
            <el-input size="small"></el-input>
            <el-select
              size="small"
              v-model="value"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <div class="">用户获得奖励后在有效期内未领取则奖励失效，不可再领取</div>
          </div>
        </el-form-item>
        <el-form-item
          label="当次助力值："
          prop=""
        >
          <el-radio
            v-model="radio"
            label="1"
          >平均值</el-radio>
          <el-radio
            v-model="radio"
            label="2"
          >随机助力值</el-radio>
          <span>查看规则</span>
        </el-form-item>
        <el-form-item
          label="所需助力值："
          prop=""
        >
          <div style="display:flex">
            <el-input></el-input>
            <div>用户发起抢购活动，助力值达到要求则助力成功，可领取奖励，建议填写大于100的整数</div>
          </div>
        </el-form-item>
        <el-form-item
          label="所需助力次数："
          prop=""
        >
          <div style="display:flex">
            <el-input></el-input>
            <div>活动需要好友帮忙助力的总次数</div>
          </div>
        </el-form-item>
        <el-form-item
          label="发起次数限制："
          prop=""
        >
          <div style="display:flex">
            <span>用户在</span>
            <el-input></el-input>
            <el-select
              size="small"
              v-model="value"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <span>内最多可发起</span>
            <el-input></el-input>次
            <div>用户在某时间段内最多可发起抢购活动的次数，填写0表示不限制</div>
          </div>
        </el-form-item>
        <el-form-item
          label="分享增加助力机会："
          prop=""
        >
          <div style="display:flex">
            <span>好友可通过分享获得最多</span>
            <el-input></el-input>
            <span>次助力机会</span>
            <span>次助力机会</span>
            <div>用户在某时间段内最多可发起抢购活动的次数，填写0表示不限制</div>
          </div>
        </el-form-item>
        <el-form-item
          label="好友助力条件："
          prop=""
        >
          <el-radio
            v-model="radio"
            label="1"
          >可不授权个人信息</el-radio>
          <el-radio
            v-model="radio"
            label="2"
          >需要授权个人信息</el-radio>
          <span>好友帮忙助力时，是否需要授权个人信息（头像+昵称）</span>
        </el-form-item>
        <el-form-item
          label="助力失败赠送："
          prop=""
        >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      ruleForm: {},
      radio: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
      value: ''
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
    .el-input {
      width: 200px;
    }
  }
}
</style>
