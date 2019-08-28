<template>
  <div class="content">
    <div class="main">
      <el-form
        :model="ruleForm"
        label-width="150px"
        labelPosition='right'
      >
        <el-form-item
          label="活动名称："
          prop=""
        >
          <el-input
            size="small"
            placeholder="请填写活动名称"
            class="morelength"
          ></el-input>
          <span style="margin-left: 10px">查看活动规则</span>
        </el-form-item>
        <el-form-item
          label="活动有效期："
          prop=""
        >
          <el-date-picker
            v-model="value1"
            type="datetime"
            placeholder="选择日期时间"
            class="morelength"
            size="small"
          >
          </el-date-picker>
          <span style="margin: 0 5px">至</span>
          <el-date-picker
            v-model="value2"
            type="datetime"
            placeholder="选择日期时间"
            class="morelength"
            size="small"
          >
          </el-date-picker>
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
          <div class="chooseGoods">+选择商品</div>
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
              style="margin: 0 10px; width: 90px"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <div class="gray">用户获得奖励后在有效期内未领取则奖励失效，不可再领取</div>
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
            <el-input
              size="small"
              style="margin-right: 10px"
            ></el-input>
            <div class="gray">用户发起抢购活动，助力值达到要求则助力成功，可领取奖励，建议填写大于100的整数</div>
          </div>
        </el-form-item>
        <el-form-item
          label="所需助力次数："
          prop=""
        >
          <div style="display:flex">
            <el-input
              size="small"
              style="margin-right: 10px"
            ></el-input>
            <div class="gray">活动需要好友帮忙助力的总次数</div>
          </div>
        </el-form-item>
        <el-form-item
          label="发起次数限制："
          prop=""
        >
          <div style="display:flex">
            <span>用户在</span>
            <el-input
              style="margin: 0 5px"
              size="small"
            ></el-input>
            <el-select
              size="small"
              v-model="value"
              style="margin-right:5px; width: 90px"
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
            <el-input
              size="small"
              style="margin:0 5px"
            ></el-input>次
            <div
              style="margin-left:10px"
              class="gray"
            >用户在某时间段内最多可发起抢购活动的次数，填写0表示不限制</div>
          </div>
        </el-form-item>
        <el-form-item
          label="分享增加助力机会："
          prop=""
        >
          <div style="display:flex">
            <span>好友可通过分享获得最多</span>
            <el-input
              style="margin:0 5px"
              size="small"
            ></el-input>
            <span>次助力机会</span>
            <div
              style="margin-left: 10px"
              class="gray"
            >好友通过帮忙分享可获得的助力次数，除分享获得助力次数外，默认每人最少1次助力机会 </div>
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
          <span class="gray">好友帮忙助力时，是否需要授权个人信息（头像+昵称）</span>
        </el-form-item>
        <el-form-item
          label="助力失败赠送："
          prop=""
        >
          <el-radio
            v-model="radio"
            label="1"
          >
            不赠送
          </el-radio>
          <el-radio
            v-model="radio"
            label="2"
          >优惠券</el-radio>
          <el-radio
            v-model="radio"
            label="3"
          >积分</el-radio>
        </el-form-item>
        <el-collapse>
          <el-collapse-item>
            <template slot="title">
              展开更多配置
            </template>
            <el-form-item
              label="活动分享："
              prop=""
            >
              <div>
                <el-radio
                  v-model="radio"
                  label="1"
                >
                  默认样式
                  <span>分享预览</span>
                  <span>海报预览</span>
                </el-radio>
              </div>
              <div>
                <el-radio
                  v-model="radio"
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
                      v-model="radio"
                      label="1"
                    >活动商品信息图</el-radio>
                    <div style="margin: 10px 0 0 60px">
                      <el-radio
                        v-model="radio"
                        label="2"
                      >自定义图片</el-radio>
                    </div>
                    <!-- <div style="margin-left: 60px">
                      <img
                        src=""
                        alt=""
                      >
                    </div> -->
                  </div>
                </el-radio>
              </div>
            </el-form-item>
          </el-collapse-item>
        </el-collapse>
      </el-form>
    </div>

    <div class="footer">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
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
      value: '',
      value1: '',
      value2: ''
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
  position: relative;
  overflow-y: auto;
  padding-bottom: 50px;
  .main {
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    .el-form-item {
      margin-bottom: 16px;
    }
    .el-input {
      width: 90px;
    }
    .morelength {
      width: 200px;
    }
    .chooseGoods {
      width: 120px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      border: 1px solid #ccc;
    }
    .gray {
      color: #999;
    }
  }
}

.footer {
  padding: 10px 0px 10px 0px;
  text-align: center;
  background: #f8f8f8;
  margin-top: 10px;
  position: fixed;
  bottom: 0;
  z-index: 1;
  width: 100%;
}
</style>
