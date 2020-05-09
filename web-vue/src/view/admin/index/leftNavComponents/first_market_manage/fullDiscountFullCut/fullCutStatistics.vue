<template>
  <div class="container">
    <div class="main">
      <div class="content">
        <div class="header">
          <span>筛选日期：</span>
          <el-date-picker
            type="datetime"
            format="yyyy-MM-dd hh:mm:ss"
            size="small"
          ></el-date-picker>
          <span>至</span>
          <el-date-picker
            type="datetime"
            format="yyyy-MM-dd hh:mm:ss"
            size="small"
          ></el-date-picker>
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
        <div class="num-list">
          <ul class="fc-statics-list">
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_blue.png);'"
            >
              <div class="fc-statics-title">
                <span>活动实付总金额（元）</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #5A8BFF;"
              >316.00</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_pink.png);'"
            >
              <div class="fc-statics-title">
                <span>活动优惠总金额(元)</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #fc6181;"
              >20.00</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_orange.png);'"
            >
              <div class="fc-statics-title">
                <span>费效比</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #fdb64a;"
              >6.33%</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_yellow.png);'"
            >
              <div class="fc-statics-title">
                <span>付款订单数</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #ff9f7f;"
              >2</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_aqua.png);'"
            >
              <div class="fc-statics-title">
                <span>付款商品件数</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #32c5e9;"
              >2</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_green.png);'"
            >
              <div class="fc-statics-title">
                <span>新成交用户数</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #3dcf9a;"
              >0</div>
            </li>
            <li
              class="fc-statics-item"
              :style="'background-image: url('+$imageHost+'/image/admin/any_coner/any_coner_purple.png);'"
            >
              <div class="fc-statics-title">
                <span>老成交用户数</span>
                <img
                  class="fc-statics-search-icon"
                  :src="$imageHost+'/image/admin/analysis_tishi.png'"
                  alt=""
                >
              </div>
              <div
                class="fc-statics-num"
                style="color: #8379f7;"
              >2</div>
            </li>
          </ul>
        </div>
        <div class="echarts"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: []
    }
  },
  mounted () { },
  methods: {
    initDataList () { }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  color: #333;
  font: 14px Helvetica Neue, Helvetica, PingFang SC, \5fae\8f6f\96c5\9ed1,
    Tahoma, Arial, sans-serif;
  .main {
    padding: 30px 25px;
    background: #fff;
  }
  .content {
    width: 85%;
    margin: 0 auto;
  }
  .header {
    margin-bottom: 30px;
  }
}
.fc-statics-list {
  width: 100%;
  display: flex;
  overflow: hidden;
  flex-wrap: wrap;
}
.fc-statics-item {
  flex: 0 0 195px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 130px;
  border: 1px solid #eee;
  background-size: 39px 44px;
  background-position: left bottom;
  background-repeat: no-repeat;
}
.fc-statics-num {
  margin-top: 15px;
  font-size: 30px;
}
.fc-statics-search-icon {
  position: relative;
  top: 3px;
  cursor: pointer;
}
</style>
