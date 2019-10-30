<template>
  <div>

    <div class="title">
      <el-tooltip
        class="item"
        effect="light"
        content="营业"
        placement="bottom-start"
      >
        <div class="fl">
          <img
            class="shop_defu"
            style="width: 44px;border: 1px solid #fff;"
            src="http://mpdevimg2.weipubao.cn/upload/0/image/20190710/crop_wTyAKWD7fyizqfv9.jpeg"
            alt=""
          >
          <img
            class="shop_type"
            src="http://mpdevimg2.weipubao.cn/image/admin/img_home/type_open.png"
            alt=""
          >
        </div>
      </el-tooltip>

      <span>微铺宝电商运营</span>
      <el-tooltip
        effect="light"
        placement="bottom-start"
      >
        <div slot="content">
          <div class="system_info_content_top">当前版本为<span class="version_name">旗舰版</span>，有效期至：<span class="expire_time">2020-03-05</span></div>
          <div class="system_info_content_bottom">
            <el-button
              type="primary"
              size="mini"
            >我要续费</el-button>
            <el-button
              type="primary"
              size="mini"
            >版本升级</el-button>
          </div>
        </div>
        <span class="title_type_par">
          <span class="title_type">旗舰版</span>
        </span>
      </el-tooltip>
      <div class="title_share">
        <img
          src="http://mpdevimg2.weipubao.cn/image/admin/img_home/share_shop.png"
          alt=""
        >分享店铺
        <span
          class="share_span"
          style="display: none;"
        >
          <img
            src="http://mpdevimg2.weipubao.cn/image/admin/img_home/img_sj.png"
            alt=""
          >
          <span class="share_span_top">
            <span>扫一扫，分享给好友吧~</span>
            <img
              class="qrcode"
              src="http://mpdevimg2.weipubao.cn/upload/4748160/qrcode/1/T1P0_20191025150038.jpg"
              alt=""
            >
            <a
              href="http://mpdevimg2.weipubao.cn/upload/4748160/qrcode/1/T1P0_20191025150038.jpg"
              download
              class="down_qrcode"
            >下载二维码</a>
          </span>
          <span class="share_link">
            <input type="text">
            <button class="btn_copy">复制</button>
          </span>
        </span>
      </div>
    </div>

    <div class="main-container">
      <div class="over-left">
        <div class="left-agency">
          <div class="left-title">
            待办事项
            <span
              class="custom_title"
              @click="customizeHandler"
            >自定义</span>
            <div class="one_piece">
              <!-- <span>🇱 ⁶⁶⁶已关注公众号，可实时接收消息通知</span>
              <a
                href="javascript:void(0);"
                class="bind_action"
              >解除绑定</a> -->
              <bindAccount />
            </div>
          </div>
          <div class="left-order-content">
            <div
              class="new_order"
              v-for="(item, index) in checkList"
              :key="index"
              v-if="item.isCheck === true"
            >
              <a href="javascript:void(0);">
                <div class="order_top">{{ item.num }}</div>
                <p>{{ item.label }}</p>
              </a>
            </div>
          </div>
        </div>
        <div class="left-data">
          <div
            class="left-title clearfix"
            style="margin-bottom: 30px"
          >
            <span>数据展示</span>
            <el-tooltip
              effect="dark"
              placement="right"
            >
              <div
                slot="content"
                style="line-height: 1.5"
              >
                访问人数：统计时间内，店铺所有页面（包括店铺主页、单品页、会员主页等）被访问的去重人数，一个人在统计时间范围内访问多次只记为一个<br />
                下单笔数：统计时间内，下单成功的订单数，一个订单对应唯一一个订单号<br />
                下单人数：统计时间内，成功下单的客户数，一人多次下单记为一人（不剔除退款订单）<br />
                支付订单：统计时间内，成功付款的订单数，一个订单对应唯一一个订单号（拼团在成团时计入付款订单，货到付款在发货时计入付款订单，不剔除退款订单）<br />
                支付金额(元)：统计时间内，所有付款订单金额之和（包括微信支付、余额、积分、会员卡，拼团在成团时计入付款金额，货到付款在发货时计入付款金额，不剔除退款金额）<br />
                访问-下单转化率：统计时间内，下单人数/访客数<br />
                下单-支付转化率：统计时间内，付款人数/下单人数<br />
                访问-支付转化率：统计时间内，付款人数/访客数<br />
              </div>
              <i class="item-image">
                <img
                  src="http://mpdevimg2.weipubao.cn/image/admin/analysis_tishi.png"
                  alt=""
                  width="14"
                  height="14"
                  style="vertical-align: middle;margin-bottom: 2px;"
                >
              </i>
            </el-tooltip>
            <div style="display:inline-block;float:right;">
              <el-select
                v-model="screeningTime"
                @change="dateChangeHandler"
                size="mini"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </div>

          </div>
          <div class="left-data-content">
            <div class="left-datas">
              <div class="single-data">
                <h4>访问人数</h4>
                <h3>{{ dataContent.userVisitNum }}</h3>
              </div>
              <div class="single-data">
                <h4>单笔下单</h4>
                <h3>{{ dataContent.orderNum }}</h3>
              </div>
              <div class="single-data">
                <h4>下单人数</h4>
                <h3>{{ dataContent.orderUserNum }}</h3>
              </div>
              <div class="single-data">
                <h4>支付单数(笔数)</h4>
                <h3>{{ dataContent.paidOrderNum }}</h3>
              </div>
              <div class="single-data">
                <h4>支付金额(元)</h4>
                <h3>{{ dataContent.totalPaidSum }}</h3>
              </div>
              <div class="single-data">
                <h4>付款人数</h4>
                <h3>{{ dataContent.paidUserNum }}</h3>
              </div>
            </div>
            <div class="right-datas">
              <div class="data-img clearfix">
                <div class="fw-app">
                  <div class="data-title">访问-支付转化率</div>
                  <div class="data-text">{{ dataContent.uv2paid }}%</div>
                </div>
                <div class="fw-xd">
                  <div class="data-title">访问-下单转化率</div>
                  <div class="data-text">{{ dataContent.uv2order }}%</div>
                </div>
                <div class="xd-app">
                  <div class="data-title">下单-支付转化率</div>
                  <div class="data-text">{{ dataContent.order2paid }}%</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="left-function">
          <div class="left-title">功能推荐</div>
          <div class="function-content">
            <a
              href="javascript:void(0);"
              class="single-func"
              v-for="(item, index) in functionList"
              :key="index"
            >
              <img
                :src="item.icon"
                alt=""
              >
              <span :style="{'position':(index === 1 || index === 5) ? 'relative':''}">{{ item.title }}
                <img
                  v-if="index === 1"
                  style="position: absolute;left: 40px"
                  src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/Hot.png"
                  alt=""
                >
                <img
                  v-if="index === 5"
                  style="position: absolute;left: 75px"
                  src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/Hot.png"
                  alt=""
                >
              </span>
            </a>
            <!-- <a
              href="javascript:void(0);"
              class="single-func"
            >
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png"
                alt=""
              >
              <span>多人拼团</span>
            </a>
            <a
              href="javascript:void(0);"
              class="single-func"
            >
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png"
                alt=""
              >
              <span style="position: relative">
                分销
                <img
                  style="position: absolute;left: 40px"
                  src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/Hot.png"
                  alt=""
                >
              </span>
            </a>
            <a
              href="javascript:void(0);"
              class="single-func"
            >
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png"
                alt=""
              >
              <span>多人拼团</span>
            </a>
            <a
              href="javascript:void(0);"
              class="single-func"
            >
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png"
                alt=""
              >
              <span>多人拼团</span>
            </a>
            <a
              href="javascript:void(0);"
              class="single-func"
            >
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png"
                alt=""
              >
              <span>多人拼团</span>
            </a>
            <a
              href="javascript:void(0);"
              class="single-func"
            >
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png"
                alt=""
              >
              <span>多人拼团</span>
            </a>
            <a
              href="javascript:void(0);"
              class="single-func"
            >
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png"
                alt=""
              >
              <span>多人拼团</span>
            </a>
            <a
              href="javascript:void(0);"
              class="single-func"
            >
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png"
                alt=""
              >
              <span>多人拼团</span>
            </a> -->
          </div>
        </div>
        <div class="left-store">
          <div class="left-title">店铺助手</div>
          <div class="task_content clearfix">
            <div class="task_left">
              <div class="progress_content">
                <div class="progress_wrapper"></div>
                <svg class="progress-inner">
                  <circle
                    cx="70"
                    cy="70"
                    style="stroke: rgb(214, 231, 255); stroke-width: 20; stroke-dasharray: 380; stroke-dashoffset: 149px; transition: stroke-dashoffset 0.4s cubic-bezier(0.08, 0.82, 0.17, 1) 0s;"
                  ></circle>
                </svg>
                <div class="progress-info">
                  <div class="status-text">
                    <p><span class="status-text_count">9</span>项</p>
                    <p>待处理</p>
                  </div>
                </div>
              </div>
              <button class="task_test_btn">刷新</button>
            </div>
            <div class="task_right">
              <div class="task_type clearfix">
                <ul class="type_ul clearfix">
                  <li>全部</li>
                  <li>全部</li>
                  <li>全部</li>
                  <li>全部</li>
                </ul>
                <a
                  href="javascript:void(0);"
                  class="view_more"
                >查看更多</a>
              </div>
              <div class="task_list_content">
                <div class="task_list">
                  <div class="task_list_item">
                    <span class="tips ff4444">提醒</span>
                    <span class="task_list_desc">“专享商品”会员卡有1个会员卡激活申请超过2天未处理</span>
                    <a href="javascript:void(0);">前往</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="over-right">
        <div class="right-notice">
          <div class="right-title">
            <span>公告</span>
            <a
              href="javascript:void(0);"
              class="gengduo"
            >更多
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/go.png"
                alt=""
              >
            </a>
          </div>
          <div class="one-zx">
            <div
              class="single-zx"
              v-for="(item, index) in noticeList"
              :key="index"
              @click="noticeDetail(item.articleId)"
            >
              <span class="circle"></span>
              <a
                href="javascript:void(0);"
                class="zx-text"
              >{{ item.title }}</a>
              <span class="zx-time">{{ item.time }}</span>
            </div>
          </div>
        </div>
        <div class="right-calendar">
          <div class="right-title">
            <span>营销日历</span>
            <a href="javascript:void(0);">
              <img
                src="http://mpdevimg2.weipubao.cn/image/admin/new_ov/calendar_icon.png"
                alt=""
              >
            </a>
          </div>
          <div class="calendar_line"></div>
          <div class="flex_calendar_box">
            <a href="javascript:void(0);">
              <p class="data">10-24</p>
              <p class="act_name">叶已纷黄，霜降已至</p>
              <p class="remaining_time">剩<span>0</span>天</p>
              <div class="dot"></div>
            </a>
            <a href="javascript:void(0);">
              <p class="data">10-24</p>
              <p class="act_name">叶已纷黄，霜降已至</p>
              <p class="remaining_time">剩<span>0</span>天</p>
              <div class="dot"></div>
            </a>
          </div>
        </div>
        <div class="right-carousel">
          <el-carousel
            height="200px"
            arrow="never"
            ref="carousel"
            indicator-position="none"
            @change="carouselChange"
          >
            <el-carousel-item
              style="height: 100%; width: 100%;"
              v-for="(item, index) in carouselList"
              :key="index"
            >
              <a
                :href="item.link"
                target="_blank"
              >
                <img
                  style="height: 100%; width: 100%;"
                  :src="item.img"
                  alt=""
                >
              </a>
            </el-carousel-item>
          </el-carousel>
          <div class="rounds">
            <ul>
              <li
                v-for="(item, index) in carouselList"
                :key="index"
                :class="indValue === index ? 'active' : '' "
                @click="indexClickHandler(index)"
              ></li>
            </ul>
          </div>

        </div>
        <div class="right-serve">
          <div class="right-title">更多服务</div>
          <div class="two-zx">
            <a
              class="single-icon"
              target="_blank"
              :href="item.link"
              v-for="(item, index) in serveList"
              :key="index"
            >
              <div class="icon-img">
                <img
                  :src="item.icon"
                  alt=""
                >
              </div>
              <div class="icon-name">{{ item.title }}</div>
            </a>

          </div>
        </div>
      </div>
    </div>

    <!-- 自定义代办事项 -->
    <el-dialog
      title="自定义代办事项"
      :visible.sync="dataDialog"
      :close-on-click-modal="false"
      :before-close="closeCheckHandler"
      center
      width="40%"
    >
      <p style="color: #999;font-size: 14px;">需选择5个待办事项</p>
      <el-checkbox-group
        v-model="checkData"
        @change="changeCheck"
        :max='5'
        style="margin-top: 20px;width: 100%;"
      >
        <el-checkbox
          v-for="item in checkList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
          style="margin-bottom: 10px;width: 25%;"
        ></el-checkbox>
      </el-checkbox-group>
    </el-dialog>

  </div>
</template>
<script>
// 引入组件
import bindAccount from './overviewBindAccount.vue'
import { toDoItemRequest, dataRequest, shopAssistantRequest, noticeListRequest, noticeDetailRequest } from '@/api/admin/survey.js'
export default {
  components: {
    bindAccount
  },
  data () {
    return {
      dataDialog: false, // 自定义事项弹框
      // 选中自定义事项
      checkData: [
        '待发货订单',
        '待处理退款退货',
        '已售罄商品',
        '商品评价待审核',
        '待提货订单'
      ],
      // 自定义列表
      checkList: [{
        value: 1,
        label: '待发货订单',
        isCheck: false
      }, {
        value: 2,
        label: '待处理退款退货',
        isCheck: false
      }, {
        value: 3,
        label: '已售罄商品',
        isCheck: false
      }, {
        value: 4,
        label: '商品评价待审核',
        isCheck: false
      }, {
        value: 5,
        label: '待提货订单',
        isCheck: false
      }, {
        value: 6,
        label: '分销员待审核',
        isCheck: false
      }, {
        value: 7,
        label: '会员卡激活待审核',
        isCheck: false
      }, {
        value: 8,
        label: '分销提现待审核',
        isCheck: false
      }, {
        value: 9,
        label: '服务评价待审核',
        isCheck: false
      }],
      screeningTime: 1, // 数据日期范围
      // 数据日期列表
      options: [{
        value: 1,
        label: '今日'
      }, {
        value: 2,
        label: '昨日'
      }, {
        value: 7,
        label: '近一周'
      }, {
        value: 30,
        label: '近一个月'
      }, {
        value: 90,
        label: '近三个月'
      }],
      // 数据列表信息
      dataContent: {},
      // 功能列表
      functionList: [{
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/drpt.png',
        title: '多人砍价'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/fx.png',
        title: '分销'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/hyzl.png',
        title: '好友助力'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/hdyl.png',
        title: '开屏有礼'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/kj.png',
        title: '砍价'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/ptcj.png',
        title: '拼团抽奖'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/yhqlb.png',
        title: '优惠券礼包'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/zfyl.png',
        title: '支付有礼'
      }],
      // 店铺列表
      storeList: {},
      // 公告列表
      noticeList: [],
      // 轮播图数据
      carouselList: [{
        id: '1',
        img: 'http://mpdevimg2.weipubao.cn/image/admin/overview_banner/banner1.jpg',
        link: ''
      }, {
        id: '2',
        img: 'http://mpdevimg2.weipubao.cn/image/admin/overview_banner/banner2.jpg',
        link: 'http://www.wangdian.cn/'
      }, {
        id: '3',
        img: 'http://mpdevimg2.weipubao.cn/image/admin/overview_banner/banner3.jpg',
        link: 'http://pos.wangdian.cn/'
      }],
      indValue: '', // 轮播的索引
      // 服务列表
      serveList: [{
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/wangdian.png',
        title: '旺店通ERP',
        link: 'http://www.wangdian.cn/pc/erpCompany.html'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/pos.png',
        title: '微铺宝POS',
        link: 'http://pos.wangdian.cn/'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/ekuai.png',
        title: 'E快帮ERP',
        link: 'http://www.ekbyun.com/'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/dashuju.png',
        title: '大数据',
        link: 'http://www.wangdian.cn/pc/data.html'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/020.png',
        title: 'O2O',
        link: 'http://www.wangdian.cn/pc/o2o.html'
      }, {
        icon: 'http://mpdevimg2.weipubao.cn/image/admin/new_ov/wms.png',
        title: '旺店通WMS',
        link: 'http://www.wangdian.cn/pc/wms.html'
      }]
    }
  },
  mounted () {
    // 初始化数据
    this.getTodoDate()
    this.getShowData()
    this.getStoreData()
    this.getNoticeList()
  },
  methods: {
    // 代办事项
    getTodoDate () {
      toDoItemRequest().then((res) => {
        let data = res.content
        if (res.error === 0) {
          for (var i = 0; i < this.checkList.length; i++) {
            if (this.checkList[i].label === '待发货订单') {
              this.checkList[i].num = data.toBeDelivered
            } else if (this.checkList[i].label === '待处理退款退货') {
              this.checkList[i].num = data.refunds
            } else if (this.checkList[i].label === '已售罄商品') {
              this.checkList[i].num = data.soldOutGoods
            } else if (this.checkList[i].label === '商品评价待审核') {
              this.checkList[i].num = data.productEvaluationPr
            } else if (this.checkList[i].label === '待提货订单') {
              this.checkList[i].num = data.pendingOrder
            } else if (this.checkList[i].label === '分销员待审核') {
              this.checkList[i].num = data.distributorPr
            } else if (this.checkList[i].label === '会员卡激活待审核') {
              this.checkList[i].num = data.membershipCardPr
            } else if (this.checkList[i].label === '分销提现待审核') {
              this.checkList[i].num = data.distributionWithdrawalPr
            } else if (this.checkList[i].label === '服务评价待审核') {
              this.checkList[i].num = data.serviceEvaluationPr
            }
            this.checkList[i].isCheck = false
            for (var j = 0; j < this.checkData.length; j++) {
              if (this.checkList[i].label === this.checkData[j]) {
                this.checkList[i].isCheck = true
              }
            }
          }
        }
      })
    },

    // 切换代办事项
    changeCheck (val) {
      if (val.length !== 5) {
        this.$message.warning({ message: '请选择5项待办事项' })
      }
    },

    // 关闭弹窗
    closeCheckHandler (done) {
      if (this.checkData.length === 5) {
        done()
        this.getTodoDate()
      } else {
        this.$message.warning({ message: '请选择5项待办事项' })
      }
    },

    // 自定义事项
    customizeHandler () {
      this.dataDialog = true
    },

    // 数据展示
    getShowData () {
      dataRequest({ screeningTime: this.screeningTime }).then((res) => {
        if (res.error === 0) {
          this.dataContent = res.content
        }
      })
    },

    // 数据日期切换
    dateChangeHandler (value) {
      this.screeningTime = value
      this.showData()
    },

    // 店铺助手
    getStoreData () {
      shopAssistantRequest({
        shopId: Number(localStorage.getItem('V-ShopId')),
        sysId: 1,
        isAuthOk: 1
      }).then((res) => {
        if (res.error === 0) {
          this.storeList = res.content
        }
      })
    },

    // 公告查询
    getNoticeList () {
      let obj = {
        'categoryId': '',
        'status': '',
        'keywords': '',
        'sortName': '',
        'page': {
          'currentPage': '1',
          'pageRows': '20'
        }
      }
      noticeListRequest(obj).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList.slice(0, 6))
        }
      })
    },

    // 公告时间格式处理
    handleData (data) {
      data.forEach(item => {
        item.time = item.updateTime.substring(5, 10)
      })
      this.noticeList = data
    },

    // 公告详情
    noticeDetail (id) {
      noticeDetailRequest({ articleId: id }).then((res) => {
        if (res.error === 0) {
          console.log(res.content)
        }
      })
    },

    // 切换轮播图
    carouselChange (index) {
      this.indValue = index
    },

    // 索引切换
    indexClickHandler (index) {
      this.$refs.carousel.setActiveItem(index)
      this.indValue = index
    }

  }
}
</script>
<style lang="scss">
.title {
  background: #f5f5f5 !important;
  position: relative;

  width: 100%;
  height: 55px;
  line-height: 55px;
  padding-left: 25px;
  font-size: 16px;
  color: #333;
  background: #fff;
  padding-right: 25px;
}

.fl {
  float: left;
  position: relative;
  margin-top: -1px;
  cursor: pointer;
}

.shop_type {
  position: absolute;
  right: 0;
  bottom: 0;
}

.title .fl > span img {
  position: absolute;
  right: 0;
  bottom: -8px;
  z-index: 9;
}

.title .fl > span > span {
  position: absolute;
  right: -33px;
  bottom: -33px;
  display: inline-block;
  width: 55px;
  height: 26px;
  line-height: 26px;
  background: #fff;
  text-align: center;
  border: 1px solid #ddd;
  font-size: 12px;
}

.title > span {
  color: #333;
  font-size: 16px;
  display: inline-block;
  margin-left: 20px;
}

.title_type_par {
  position: relative;
  height: 35px;
}

.title .title_type {
  background: #457bf9;
  color: #fff;
  font-size: 14px;
  width: 60px;
  height: 23px;
  line-height: 23px;
  text-align: center;
  -webkit-border-radius: 12px;
  -moz-border-radius: 12px;
  border-radius: 12px;
  cursor: pointer;
  display: inline-block;
}

.system_shadow {
  top: 22px;

  position: absolute;
  left: 19px;
  z-index: 109;
  top: 29px;
  display: none;
}

.system_info_content {
  font-size: 12px;
  top: 34px;

  width: 300px;
  padding: 10px;
  background: #fff;
  box-shadow: 0px 0px 10px #f0f0f0;
  position: absolute;
  z-index: 100;
  display: none;
}

.system_info_content_top {
  width: 100%;
  border-bottom: 1px solid #eee;
  line-height: 2;
}

.system_info_content_bottom {
  text-align: center;
  margin: 10px 0;
}

// .system_info_content_top {
//   padding-bottom: 10px;
//   border-bottom: 1px solid #eee;
//   line-height: 20px;
// }

// .system_info_content_bottom {
//   text-align: center;
// }

// .system_info_content_bottom a {
//   display: inline-block;
//   margin: 10px auto 0;
//   width: 70px;
//   text-align: center;
//   height: 30px;
//   line-height: 30px;
//   background: #5a8bff;
//   color: #fff;
//   cursor: pointer;
//   font-size: 12px;
// }

// .system_info_content_bottom a:first-child {
//   margin-right: 20px;
// }

.title_share {
  width: 120px;

  float: right;
  padding-right: 25px;
  font-size: 14px;
  cursor: pointer;
  width: 102px;
  position: relative;
}

.title_share .share_span {
  right: 10px;
  z-index: 99;

  padding: 15px 12px;
  border: 1px solid #eee;
  background: #fff;
  font-size: 14px;
  position: absolute;
  right: 0;
  top: 50px;
  width: 285px;
  text-align: center;
  display: none;
}

.share_sj {
  position: absolute;
  right: 75px;
  top: -7px;
}

.title_share span {
  display: inline-block;
}

.share_span .share_span_top {
  width: 100%;
  border-bottom: 1px solid #eee;
  line-height: 0;
  padding-bottom: 10px;
}

.share_span .share_span_top > span {
  color: #000;
  font-weight: bold;
  font-size: 14px;
  height: 30px;
  line-height: 30px;
}

.share_span .share_span_top img {
  display: block;
  margin: 0 auto;
}

.share_span .share_span_top a {
  color: #999;
  font-size: 13px;
  display: inline-block;
  height: 30px;
  line-height: 30px;
}

.title_share span {
  display: inline-block;
}

.share_link {
  padding-top: 15px;
  width: 100%;
}

.share_link input {
  background: #f7f7f7;
  border: 1px solid #f2f2f2;
  height: 35px;
  width: 220px;
  padding-left: 8px;
  float: left;
  font-size: 13px;
  color: #666;
}

.share_link button {
  float: right;
  color: #5a8bff;
  background: #fff;
  border: none;
  height: 35px;
  line-height: 35px;
}

img {
  // vertical-align: middle;
}

.shop_defu {
  border-radius: 100%;
}

.shop_type {
  position: absolute;
  right: 0;
  bottom: 0;
}

.main-container {
  padding: 10px;
}

.over-left {
  width: 74%;
  float: left;
}

.left-agency,
.left-data,
.left-function,
.left-store {
  width: 100%;
  background-color: #fff;
  border-radius: 2px;
  padding: 20px 20px;
}

.left-agency {
  height: 180px;
}

.left-title {
  font-size: 16px;
  color: #333;
  font-weight: 600;
  margin-bottom: 20px;
  position: relative;
}

.el-tooltip__popper {
  max-width: 600px;
}

.custom_title {
  float: none;
  margin-left: 20px;

  color: #5a8bff;
  font-weight: 400;
  font-size: 14px;
  line-height: 21px;
  cursor: pointer;
}

.one_piece {
  float: right;
  font-size: 14px;
  color: #333;
  font-weight: normal;
}

.one_piece a {
  border: 1px solid #5a8bff;
  color: #5a8bff;
  padding: 5px 10px;
  border-radius: 2px;
  margin-left: 10px;
}

.left-order-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.new_order {
  text-align: center;
  width: 18%;
}

a {
  color: #333;
  text-decoration: none;
}

.left-order-content > .new_order:nth-child(1) > a .order_top {
  background-color: #dfecff;
  color: #5a8bff;
}

.left-order-content > .new_order:nth-child(2) > a .order_top {
  background-color: #ffdee4;
  color: #fc6181;
}

.left-order-content > .new_order:nth-child(3) > a .order_top {
  background-color: #fff0c4;
  color: #fdb64a;
}

.left-order-content > .new_order:nth-child(4) > a .order_top {
  background-color: #dfecff;
  color: #5a8bff;
}

.left-order-content > .new_order:nth-child(5) > a .order_top {
  background-color: #ffdee4;
  color: #fc6181;
}

.order_top {
  line-height: 56px;
  border-radius: 6px;
  font-size: 30px;
}

.new_order > a > p {
  margin-top: 12px;
  color: #666666;
}

.left-data {
  height: 230px;
  margin-top: 10px;
}

.left-data-content {
  display: flex;
  justify-content: flex-start;
}

.left-datas {
  width: 50%;
  height: 145px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: wrap;
}

.single-data {
  width: 30%;
  height: 60px;
  margin-right: 10px;
}

.single-data h4 {
  font-size: 12px;
  color: #666;
  margin-bottom: 5px;
}

.single-data h3 {
  font-size: 24px;
  color: #5a8bff;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.right-datas {
  width: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.data-img {
  margin-bottom: 10px;
  position: relative;
  background: url(http://mpdev.weipubao.cn/image/admin/new_ov/apply_order.png)
    no-repeat;
  width: 206px;
  height: 126px;
  margin-right: 30px;
}

.clearfix {
  zoom: 1;
}

.fw-app {
  position: absolute;
  left: -110px;
  top: 50px;
}

.data-title {
  font-size: 12px;
  color: #666;
  margin-bottom: 3px;
}

.data-text {
  font-size: 12px;
  color: #f96a6b;
  text-align: center;
}

.fw-xd {
  position: absolute;
  right: -100px;
  top: 20px;
}

.xd-app {
  position: absolute;
  bottom: 25px;
  right: -90px;
}

.left-function {
  margin-top: 10px;
}

.function-content {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  width: 90%;
  margin-bottom: -20px;
}

.single-func {
  width: 25%;
  margin-bottom: 20px;
  cursor: pointer;
}

img {
  vertical-align: middle;
}

.single-func span {
  padding-left: 8px;
}

.left-store {
  margin-top: 10px;
}

.task_left {
  width: 195px;
  height: 257px;
  float: left;
}

.progress_content {
  width: 140px;
  height: 140px;
  margin: auto;
  position: relative;
  margin-top: 50px;
}

.progress_wrapper {
  width: 100%;
  height: 100%;
  border-radius: 140px;
  border-width: 19px;
  border-color: #5a8bff;
  border-style: solid;
  box-sizing: border-box;
}

svg:not(:root) {
  overflow: hidden;
}

.progress-inner {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.progress-inner circle {
  stroke-linecap: butt;
  fill: none;
  transition: stroke-dashoffset 0.4s cubic-bezier(0.08, 0.82, 0.17, 1) 0s;
  transform: rotate(-90deg);
  transform-origin: center center;
}

.progress-info {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  text-align: center;
  line-height: 140px;
}

.progress-info > .status-text {
  display: flex;
  height: 100%;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  line-height: 14px;
}

.progress-info > .status-text > p {
  font-size: 14px;
  color: #999;
}

.progress-info > .status-text > p > .status-text_count {
  font-size: 30px;
  color: #ff4444;
}

.task_right {
  height: 257px;
  margin-left: 200px;
}

.task_type {
  border-bottom: 1px solid #e5e5e5;
  line-height: 39px;
  height: 41px;
}

.type_ul {
  float: left;
}

.view_more {
  color: #5a8bff;
  float: right;
}

.type_ul li {
  float: left;
  width: 70px;
  text-align: center;
  cursor: pointer;
  position: relative;
  list-style: none;
}

.type_ul li.active {
  border-bottom: 2px solid #5a8bff;
}

.task_right a:link,
.task_right a:visited,
.task_right a:hover,
.task_right a:active {
  color: #5a8bff;
  margin-left: 30px;
}

.task_list_content {
  height: 100%;
  max-height: 215px;
  overflow-y: auto;
  padding-right: 10px;
}

.task_list_content .task_list {
  padding-bottom: 12px;
}

.task_list_content .task_list:first-child {
  border-top: 0;
}

.task_list .task_list_item {
  margin-top: 12px;
  line-height: 18px;
  display: flex;
  align-items: center;
}

.tips.ff4444 {
  color: #ff4444;
  border-color: #ff4444;
}

.task_list_item .tips {
  font-size: 12px;
  border: 1px solid #5a8bff;
  color: #5a8bff;
  border-radius: 10px;
  letter-spacing: 1px;
  padding: 0 4px 0 5px;
  line-height: 16px;
}

.task_list_item .task_list_desc {
  flex: 1;
  margin-left: 4px;
  font-size: 14px;
  line-height: 18px;
}

.task_right a:link,
.task_right a:visited,
.task_right a:hover,
.task_right a:active {
  color: #5a8bff;
  margin-left: 30px;
}

.task_test_btn {
  display: block;
  width: 100px;
  line-height: 30px;
  margin: 20px auto 0;
  border: 1px solid #5a8bff;
  background-color: #fff;
  color: #5a8bff;
  border-radius: 4px;
}

.over-right {
  width: 25%;
  float: left;
  margin-left: 10px;
}

.right-notice {
  width: 100%;
  height: 250px;
  background-color: #fff;
  margin-bottom: 10px;
  padding: 10px 15px;
}

.right-title {
  font-size: 16px;
  color: #333;
  font-weight: 600;
  margin-bottom: 17px;
}

.gengduo {
  font-size: 12px;
  color: #666;
  display: inline-block;
  float: right;
  font-weight: 400;
}

.one-zx {
  width: 100%;
  height: 85%;
  overflow: hidden;
}

.single-zx {
  width: 100%;
  height: 32px;
  border-bottom: 1px solid #eee;
  font-size: 12px;
  color: #666;
  line-height: 32px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.single-zx span {
  display: inline-block;
}

.circle {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background-color: #666;
}

.zx-text {
  padding-left: 0px;
  cursor: pointer;
  width: 66%;
  display: inline-block;
  height: 32px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.zx-time {
  width: 13%;
  float: right;
  line-height: 32px;
  height: 32px;
}

.right-calendar {
  background-color: #fff;
  width: 100%;
  padding: 18px;
  margin-bottom: 10px;
}

.right-calendar > .right-title > a {
  float: right;
}

.calendar_line {
  height: 2px;
  background-color: #f66;
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 20px;
}

.flex_calendar_box {
  display: flex;
}

.flex_calendar_box > a {
  flex: 1;
  background-color: #fff;
  border-radius: 6px;
  border: 1px solid #eee;
  text-align: center;
  padding-bottom: 10px;
  position: relative;
  min-width: 0;
}

.flex_calendar_box > a > .data {
  line-height: 24px;
  background-color: #f66;
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
  margin: -1px -1px 0 -1px;
  color: #fff;
}

.flex_calendar_box > a > p {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.flex_calendar_box > a > .act_name {
  line-height: 30px;
  color: #333;
}

.flex_calendar_box > a > .remaining_time {
  line-height: 24px;
  color: #333;
}

.flex_calendar_box > a > .dot {
  width: 12px;
  height: 12px;
  position: absolute;
  background-color: #ff6666;
  border: 3px solid rgba(255, 210, 210, 0.8);
  border-radius: 50%;
  left: 50%;
  top: -28.5px;
  transform: translate(-50%);
}

.right-carousel {
  width: 100%;
  height: 200px;
  background-color: #fff;
  margin-bottom: 10px;

  position: relative;
}

.right-carousel .rounds {
  width: 100%;
  position: absolute;
  bottom: 10px;
  left: 50%;
  margin-left: -22px;
  text-align: center;
  z-index: 999;
}

.right-carousel .rounds ul li {
  float: left;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-right: 7px;
  border: 1px solid #fff;
}

.right-carousel .rounds .active {
  background: #fff;
}

.right-serve {
  width: 100%;
  height: 270px;
  background-color: #fff;
  margin-bottom: 10px;
  padding: 10px 15px;
}

.two-zx {
  width: 100%;
  height: 85%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  margin-top: 20px;
}

.single-icon {
  width: 33.33%;
  height: 100px;
  cursor: pointer;
}

.single-icon {
  width: 33.33%;
  height: 100px;
  cursor: pointer;
}

.icon-img {
  margin-bottom: 10px;
  text-align: center;
}

.icon-name {
  font-size: 12px;
  color: #333;
  text-align: center;
}

// .one_piece {
//   margin: 0;
//   padding: 0;
//   float: right;
//   font-size: 14px;
//   color: #333;
//   background-color: #fff;
//   font-weight: normal;
// }
// /deep/ .btn_follow {
//   border: 1px solid #5a8bff;
//   color: #5a8bff;
//   padding: 5px 10px;
//   border-radius: 2px;
//   margin-left: 10px;
// }
// .off-area {
//   display: flex;
//   flex-direction: column;
//   align-items: center;
//   justify-content: center;
//   color: #666;
// }
</style>
