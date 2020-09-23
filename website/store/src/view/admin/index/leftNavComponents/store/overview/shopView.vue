<template>
  <div class="view-container">
    <div class="top-content">
      <div class="left-content">
        <div class="content-item">
          <div class="item-title">
            <span class="title">待办事项</span>
            <div v-if="bindData.isBind === 0">
              关注公众号，实时接收消息通知
              <el-button class="btn_follow" @click="handleBind">关注</el-button>
            </div>
            <div v-if="bindData.isBind === 1">
              {{ bindData.nickName }}已关注公众号，可实时接收消息通知
              <el-button class="btn_follow" @click="handleCancelBind"
                >解除绑定</el-button
              >
            </div>
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
      <div class="right-content" v-if="articleList.length">
        <div class="content-item">
          <div class="item-title">
            <span class="title">公告</span>
            <a class="gengduo"
              ><span @click="toList">更多</span>
              <img :src="image + '/image/admin/new_ov/go.png'" alt="" />
            </a>
          </div>
          <div class="list-content">
            <div
              class="list-item"
              v-for="articleItem in articleList"
              :key="articleItem.articleId"
              @click="noticeDetail(articleItem.articleId)"
            >
              <span class="dot"></span>
              <span class="text">{{ articleItem.title }}</span>
              <span class="time">{{ articleItem.updateTime }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="bottom-content">
      <div class="content-item">
        <div class="item-title">
          <span class="title">热销药品</span>
        </div>
        <!-- <div class="filters">
          <div class="filters_item">
            <span>科室：</span>
            <el-select
              v-model="docterPerformanceParams.departmentId"
              filterable
              size="small"
            >
              <el-option
                v-for="item in departmentList"
                :key="item.id"
                :value="item.id"
                :label="item.name"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <el-date-picker
              v-model="docterPerformanceParams.startTime"
              type="datetime"
              placeholder="开始时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              class="middle_input"
              size="small"
            />
            至
            <el-date-picker
              v-model="docterPerformanceParams.endTime"
              type="datetime"
              placeholder="结束时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              class="middle_input"
              default-time="23:59:59"
              size="small"
            />
          </div>
          <div class="filters_item">
            <el-button
              type="primary"
              @click="filterTable"
              size="small"
              style="margin-left: 20px"
              >搜索</el-button
            >
          </div>
        </div> -->
        <div class="table-content">
          <el-table :data="goodsTable" border header-row-class-name="tableClss">
            <el-table-column
              prop="name"
              label="药品名称"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="desc"
              label="规格明细"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="vendor"
              label="生产厂家"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="num"
              label="销售数量"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="money"
              label="销售金额"
              align="center"
            ></el-table-column>
          </el-table>
          <pagination :page-params.sync="goodsTablePageParams" />
        </div>
      </div>
    </div>
    <el-dialog
      title="关注公众号"
      :visible.sync="centerDialogVisible"
      width="30%"
    >
      <span>用手机扫下方二维码关注公众号，及时接收新订单提醒</span>

      <div style="text-align: center">
        <img :src="this.imgsrc" style="width: 100px; padding-top: 23px" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getArticleList, getOrderNum, getUnfilledOrderNum, getAllStoreList, getBindStatus, getQrCode, setBind } from '@/api/store/store'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      storeList: [],
      articleList: [],
      waitDeliverNum: 0,
      waitVerifyNum: 0,
      searchTimeType: '-1',
      searchStoreId: null,
      totalNum: {},
      timeList: [
        { value: '-1', label: '今日' },
        { value: '1', label: '昨日' },
        { value: '7', label: '近一周' },
        { value: '30', label: '近一个月' },
        { value: '90', label: '近三个月' }
      ],
      bindData: {},
      centerDialogVisible: false,
      imgsrc: null,
      goodsTable: [
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' },
        { name: '头孢', desc: '大', vendor: '六厂', num: '10', money: '1000' }
      ],
      goodsTablePageParams: {}
    }
  },
  mounted () {
    this.getUnfilledNum()
    this.getArticle()
    this.getStoreList()
    this.getBindStatus()
  },
  methods: {
    getArticle () {
      getArticleList({ status: 1 }).then(res => {
        if (res.error === 0) {
          this.articleList = res.content.dataList
        }
      })
    },
    getBindStatus () {
      getBindStatus().then(res => {
        if (res.error === 0) {
          this.bindData = res.content
        }
      })
    },
    handleBind () {
      getQrCode().then(res => {
        if (res.error === 0) {
          this.imgsrc = res.content
          this.centerDialogVisible = true
        } else {
          this.$message.error(res.message)
        }
      })
    },
    handleCancelBind () {
      this.$confirm('是否确认解除绑定', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        setBind({ act: 'del_bind' }).then(res => {
          if (res.error === 0) {
            this.bindData.isBind = 0
          } else {
            this.$message.error({
              message: res.message
            })
          }
        })
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
    },
    // 公告详情
    noticeDetail (id) {
      let routeUrl = this.$router.resolve({ path: '/admin/home/shopMain', query: { id: id, change_components: '8' } })
      window.open(routeUrl.href, '_blank')
    },
    toList () {
      let routeUrl = this.$router.resolve({
        path: '/admin/home/shopMain',
        query: {
          change_components: '7'
        }
      })
      window.open(routeUrl.href, '_blank')
    }
  }
}
</script>

<style lang="scss" scoped>
.view-container {
  padding: 10px;
  display: flex;
  flex-direction: column;
  > div.top-content {
    display: flex;
    flex-direction: row;
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
    flex: 0.4;
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
        cursor: pointer;
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
  .bottom-content {
    margin-top: 10px;
  }
  .left-content,
  .right-content,
  .bottom-content {
    > .content-item {
      padding: 20px;
      background-color: #fff;
      > .item-title {
        margin-bottom: 10px;
        display: flex;
        .gengduo {
          font-size: 12px;
          color: #666;
          display: inline-block;
          float: right;
          font-weight: 400;
          cursor: pointer;
        }
        > .title {
          font-size: 16px;
          font-weight: 600;
          color: #333;
          margin-right: auto;
        }
        /deep/ .btn_follow {
          border: 1px solid #5a8bff;
          color: #5a8bff;
          padding: 5px 10px;
          border-radius: 2px;
          margin-left: 10px;
        }
      }
      + .content-item {
        margin-top: 10px;
      }
    }
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
}
</style>
