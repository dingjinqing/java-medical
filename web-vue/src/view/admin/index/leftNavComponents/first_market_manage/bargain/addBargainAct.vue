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
            <el-radio
              v-model="radio"
              label="1"
            >砍到指定金额计算</el-radio>
            <el-radio
              v-model="radio"
              label="2"
            >砍到任意金额计算</el-radio>
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
            <div><img
                src=""
                alt=""
              ></div>
            <div>所有参与砍价的商品，均需要用户将价格砍到底价后才可以砍价成功，
              若某商品同一时间段内同时参与了砍价和拼团活动，则优先进行砍价活动</div>
          </el-form-item>

          <el-form-item
            label="帮砍设置："
            prop=""
          >
            <el-checkbox v-model="checked">帮砍好友需要授权手机号才可以参与砍价</el-checkbox>
          </el-form-item>

        </el-form>

      </div>
    </div>

    <!-- <div class="footer">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
    </div> -->
  </div>
</template>

<script>

export default {
  data () {
    return {
      text: [
        { name: '指定' },
        { name: '任意' }
      ],
      radio: '',
      value1: '',
      checked: ''
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
    }
  }
  .footer {
    position: fixed;
    width: 100%;
    height: 50px;
    padding: 10px 0;
    background: #f8f8f8;
    text-align: center;
  }
}
</style>
