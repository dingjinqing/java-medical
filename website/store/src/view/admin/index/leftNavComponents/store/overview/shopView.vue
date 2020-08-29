<template>
  <div class="view-container">
    <div class="left-content">
      <div class="content-item">
        <div class="item-title">
          <span class="title">待办事项</span>
        </div>
        <div class="module-content">
          <div
            class="module-item"
            :style="'background:url('+$imageHost+'/image/store/overview/so_blue.png) no-repeat 95% 90%,-webkit-linear-gradient(left, #dfecff, #b2cbff);'"
          >
            <span class="num">0</span>
            <span class="title">待核销自提订单</span>
          </div>
          <div
            class="module-item"
            :style="'background:url('+$imageHost+'/image/store/overview/so_orange.png) no-repeat 95% 90%,-webkit-linear-gradient(left, #fff6da, #ffe2af);'"
          >
            <span class="num">0</span>
            <span class="title">待发货订单</span>
          </div>
          <div
            class="module-item"
            :style="'background:url('+$imageHost+'/image/store/overview/so_pink.png) no-repeat 95% 90%,-webkit-linear-gradient(left, #ffe9ed, #ffcfd8);'"
          >
            <span class="num">0</span>
            <span class="title">待核销服务</span>
          </div>
        </div>
      </div>
      <div class="content-item">
        <div class="item-title">
          <span class="title">数据看板</span>
        </div>
        <div class="data-content">
          <div class="data-item">
            <img
              :src="$imageHost + '/image/store/overview/so2.png'"
              alt=""
            >
            <div class="desc">
              <span class="title">下单人数</span>
              <span class="num">0</span>
            </div>
          </div>
          <div class="data-item">
            <img
              :src="$imageHost + '/image/store/overview/so3.png'"
              alt=""
            >
            <div class="desc">
              <span class="title">付款人数</span>
              <span class="num">0</span>
            </div>
          </div>
          <div class="data-item">
            <img
              :src="$imageHost + '/image/store/overview/so4.png'"
              alt=""
            >
            <div class="desc">
              <span class="title">下单数</span>
              <span class="num">0</span>
            </div>
          </div>
          <div class="data-item">
            <img
              :src="$imageHost + '/image/store/overview/so5.png'"
              alt=""
            >
            <div class="desc">
              <span class="title">支付单数</span>
              <span class="num">0</span>
            </div>
          </div>
          <div class="data-item">
            <img
              :src="$imageHost + '/image/store/overview/so6.png'"
              alt=""
            >
            <div class="desc">
              <span class="title">消费金额</span>
              <span class="num">0</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="right-content">
      <div class="content-item">
        <div class="item-title">
          <span class="title">公告</span>
        </div>
        <div class="list-content">
          <div class="list-item">
            <span class="dot"></span>
            <span class="text">门店功能更新啦</span>
            <span class="time">03-13</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>
.view-container {
  padding: 10px;
  display: flex;
  > div {
    display: flex;
    flex-direction: column;
  }
  .left-content {
    flex: 1;
    .module-content {
      display: flex;
      height: 150px;
      .module-item {
        flex: 1;
        display: flex;
        padding: 20px;
        box-sizing: border-box;
        justify-content: center;
        flex-direction: column;
        cursor: pointer;
        > .title {
          margin-top: 20px;
          font-size: 14px;
          color: #666;
        }
        > .num {
          font-size: 34px;
        }
        &:nth-child(1) {
          background: -webkit-linear-gradient(left, #dfecff, #b2cbff);
          > .num {
            color: #5a8bff;
          }
        }
        &:nth-child(2) {
          background: -webkit-linear-gradient(left, #fff6da, #ffe2af);
          > .num {
            color: #fdb64a;
          }
        }
        &:nth-child(3) {
          background: -webkit-linear-gradient(left, #ffe9ed, #ffcfd8);
          > .num {
            color: #fc6181;
          }
        }
        + .module-item {
          margin-left: 20px;
        }
      }
    }
    .data-content {
      display: flex;
      flex-wrap: wrap;
      .data-item {
        display: flex;
        width: 33.33%;
        height: 130px;
        align-items: center;
        > img {
          width: 80px;
          height: 80px;
          margin-right: 15px;
        }
        .desc {
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          height: 56px;
          .title {
            color: #666;
            font-size: 14px;
          }
          .num {
            color: #333;
            font-size: 20px;
          }
        }
      }
    }
  }
  .right-content {
    width: 30%;
    margin-left: 10px;
    .list-content {
      display: flex;
      flex-direction: column;
      .list-item {
        height: 32px;
        display: flex;
        align-items: center;
        border-bottom: 1px solid #eee;
        .dot {
          width: 6px;
          height: 6px;
          border-radius: 50%;
          background-color: #999;
          margin: 0 15px;
        }
        .text {
          flex: 1;
        }
        .time {
          color: #333;
        }
      }
    }
  }
  .left-content,
  .right-content {
    > .content-item {
      padding: 20px;
      background-color: #fff;
      > .item-title {
        margin-bottom: 10px;
        > .title {
          font-size: 16px;
          font-weight: 600;
          color: #333;
        }
      }
      + .content-item {
        margin-top: 10px;
      }
    }
  }
}
</style>
