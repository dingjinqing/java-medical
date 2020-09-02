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
            :style="
              'background:url(' +
              $imageHost +
              '/image/store/overview/so_blue.png) no-repeat 95% 90%,-webkit-linear-gradient(left, #dfecff, #b2cbff);'
            "
          >
            <span class="num">{{ waitVerifyNum }}</span>
            <span class="title">待核销自提订单</span>
          </div>
          <div
            class="module-item"
            :style="
              'background:url(' +
              $imageHost +
              '/image/store/overview/so_orange.png) no-repeat 95% 90%,-webkit-linear-gradient(left, #fff6da, #ffe2af);'
            "
          >
            <span class="num">{{ waitDeliverNum }}</span>
            <span class="title">待发货订单</span>
          </div>
          <!-- <div
            class="module-item"
            :style="
              'background:url(' +
              $imageHost +
              '/image/store/overview/so_pink.png) no-repeat 95% 90%,-webkit-linear-gradient(left, #ffe9ed, #ffcfd8);'
            "
          >
            <span class="num">0</span>
            <span class="title">待核销服务</span>
          </div> -->
        </div>
      </div>
      <div class="content-item">
        <div class="item-title">
          <span class="title">数据看板</span>
          <div>
            查询时间：
            <el-select v-model="searchTimeType" @change="selectChange">
              <el-option
                v-for="item in timeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                size="small"
              ></el-option>
            </el-select>
          </div>
          <div>
            门店：
            <el-select v-model="searchStoreId" @change="selectChange">
              <el-option
                v-for="item in storeList"
                :key="item.label"
                :label="item.label"
                :value="item.value"
                size="small"
              ></el-option>
            </el-select>
          </div>
        </div>
        <div class="data-content">
          <div class="data-item">
            <img :src="$imageHost + '/image/store/overview/so2.png'" alt="" />
            <div class="desc">
              <span class="title">下单人数</span>
              <span class="num">{{ totalNum.orderUserNum || 0 }}</span>
            </div>
          </div>
          <div class="data-item">
            <img :src="$imageHost + '/image/store/overview/so3.png'" alt="" />
            <div class="desc">
              <span class="title">付款人数</span>
              <span class="num">{{ totalNum.orderPayUserNum || 0 }}</span>
            </div>
          </div>
          <div class="data-item">
            <img :src="$imageHost + '/image/store/overview/so4.png'" alt="" />
            <div class="desc">
              <span class="title">下单数</span>
              <span class="num">{{ totalNum.orderNum || 0 }}</span>
            </div>
          </div>
          <div class="data-item">
            <img :src="$imageHost + '/image/store/overview/so5.png'" alt="" />
            <div class="desc">
              <span class="title">支付单数</span>
              <span class="num">{{ totalNum.orderPayNum || 0 }}</span>
            </div>
          </div>
          <div class="data-item">
            <img :src="$imageHost + '/image/store/overview/so6.png'" alt="" />
            <div class="desc">
              <span class="title">消费金额</span>
              <span class="num">{{ totalNum.totalPaidMoney || '0.00' }}</span>
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
          <div
            class="list-item"
            v-for="articleItem in articleList"
            :key="articleItem.articleId"
          >
            <span class="dot"></span>
            <span class="text">{{ articleItem.title }}</span>
            <span class="time">{{ articleItem.updateTime }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getArticleList, getOrderNum, getUnfilledOrderNum, getAllStoreList } from '@/api/store/store'
export default {
  data () {
    return {
      storeList: [],
      articleList: [],
      waitDeliverNum: 0,
      waitVerifyNum: 0,
      searchTimeType: '1',
      searchStoreId: null,
      totalNum: {},
      timeList: [
        { value: '1', label: '今日' },
        { value: '2', label: '昨日' },
        { value: '3', label: '近一周' },
        { value: '4', label: '近一个月' },
        { value: '5', label: '近三个月' }
      ]
    }
  },
  mounted () {
    this.getUnfilledNum()
    this.getArticle()
    this.getStoreList()
  },
  methods: {
    getArticle () {
      getArticleList({ status: 1 }).then(res => {
        if (res.error === 0) {
          this.articleList = res.content.dataList
        }
      })
    },
    getNum () {
      getOrderNum({ storeId: this.searchStoreId, type: this.searchTimeType }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.totalNum = { ...res.content }
        }
      })
    },
    getUnfilledNum () {
      getUnfilledOrderNum().then(res => {
        if (res.error === 0) {
          this.waitDeliverNum = res.content.waitDeliverNum
          this.waitVerifyNum = res.content.waitVerifyNum
        }
      })
    },
    getStoreList () {
      getAllStoreList().then(res => {
        if (res.error === 0) {
          this.storeList = res.content
          this.searchStoreId = res.content[0].value
          this.getNum()
        }
      })
    },
    selectChange () {
      this.getNum()
    }
  }
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
      height: 310px;
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
        display: flex;
        > .title {
          font-size: 16px;
          font-weight: 600;
          color: #333;
          margin-right: auto;
        }
      }
      + .content-item {
        margin-top: 10px;
      }
    }
  }
}
</style>
