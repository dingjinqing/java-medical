<template>
  <div class="container overview-page">
    <div class="header">
      <div class="h-l">system <span class="title-type">管理员</span></div>
      <div class="h-r"></div>
    </div>
    <div class="content">
      <div class="module">
        <div class="module-header">
          <h2>账户统计</h2>
        </div>
        <div class="module-con">
          <ul class="account-list">
            <li>
              <div
                class="account"
                style="color:#2eb8e6;"
              >32</div>
              <div class="account-title">账户总数</div>
            </li>
            <li>
              <div
                class="account"
                style="color: #f3bd51;"
              >71</div>
              <div class="account-title">店铺总数</div>
            </li>
            <li>
              <div
                class="account"
                style="color: #fe6c6c;"
              >40</div>
              <div class="account-title">有效店铺</div>
            </li>
            <li>
              <div class="account">40</div>
              <div class="account-title">使用中</div>
            </li>
            <li>
              <div class="account">3</div>
              <div class="account-title">将过期</div>
            </li>
            <li>
              <div class="account">31</div>
              <div class="account-title">将过期</div>
            </li>
            <li>
              <div class="account">1</div>
              <div class="account-title">已禁用</div>
            </li>
            <li>
              <div class="account">20</div>
              <div class="account-title">授权成功</div>
            </li>
            <li>
              <div class="account">6</div>
              <div class="account-title">未授权成功</div>
            </li>
            <li>
              <div class="account">11</div>
              <div class="account-title">开团支付</div>
            </li>
            <li>
              <div class="account">15</div>
              <div class="account-title">未开通支付</div>
            </li>
          </ul>
          <div>
            <span>时间：</span>
            <el-date-picker
              type="date"
              size="small"
              style="width:150px;"
            ></el-date-picker>
            <span>-</span>
            <el-date-picker
              type="date"
              size="small"
              style="width:150px;"
            ></el-date-picker>
            <el-button
              type="primary"
              size="small"
            >搜索</el-button>
          </div>
        </div>
      </div>
      <div class="module">
        <div class="module-header">
          <h2>用户统计 <span>用户总数：</span><span>14321</span></h2>
        </div>
        <div class="module-con">
          <div class="module-con-left">
            <v-chart :options="userStatistics"></v-chart>
          </div>
          <div class="module-con-right"></div>
        </div>
      </div>
      <div class="module">
        <div class="module-header">
          <h2>订单统计 <span>订单总数：</span><span>9402</span></h2>
        </div>
        <div class="module-con">
          <div class="module-con-left"></div>
          <div class="module-con-right"></div>
        </div>
      </div>
      <div class="module">
        <div class="module-header clearfix">
          <h2>订单支付统计</h2>
          <ul class="pay-ul clearfix">
            <li>微信：<span>233</span></li>
            <li>余额：<span>666</span></li>
            <li>卡余额：<span>222</span></li>
            <li>积分：<span>233</span></li>
          </ul>
        </div>
        <div class="module-con">
          <div class="module-con-left"></div>
          <div class="module-con-right"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'overview',
  data () {
    return {
      // 用户统计
      userStatistics: {

      }
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  margin-top: 10px;
  background: #f5f5f5;
  color: #333;
  font: 14px Helvetica Neue, Helvetica, PingFang SC, \5fae\8f6f\96c5\9ed1,
    Tahoma, Arial, sans-serif;
  .header {
    display: flex;
    justify-content: space-between;
    padding: 10px;
  }
  .content {
    padding: 0 10px;
    background: #fff;
  }
  .clearfix {
    &:after {
      content: ".";
      display: block;
      height: 0;
      clear: both;
      visibility: hidden;
    }
  }
}
.overview-page {
  .title-type {
    display: inline-block;
    margin-left: 20px;
    padding: 4px 10px;
    border-radius: 12px;
    cursor: pointer;
    background: #457bf9;
    font-size: 14px;
    color: #fff;
  }
  .module {
    width: 100%;
    .module-header {
      height: 50px;
      line-height: 50px;
      border-bottom: 1px solid #e6e9f0;
    }
    h2 {
      font-size: 20px;
      font-weight: bold;
      float: left;
    }
    .pay-ul {
      float: left;
      margin-left: 20px;
      font-size: 20px;
      font-weight: bold;
      li {
        float: left;
      }
    }
    .module-con {
      flex: 1;
      display: flex;
      width: 100%;
    }
    .module-con-left {
      flex: 1;
    }
    .module-con-right {
      flex: 1;
    }
  }
  .account-list {
    display: flex;
    padding: 43px 0;
    li {
      flex: 1;
      text-align: center;
      cursor: pointer;
      &:not(:last-child) {
        border-right: 1px solid #e6e9f0;
      }
    }
    .account {
      margin: 10px 0 15px;
      font-size: 36px;
    }
    .account-title {
      font-size: 18px;
    }
  }
}
</style>
