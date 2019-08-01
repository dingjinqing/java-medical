<template>
  <div class="membersDetailContent">
    <div class="membersDetailContentMain">
      <div class="topContainer">
        <div class="titleEdit"><span>基本信息</span><span>编辑</span></div>
        <div class="topMain">
          <div class="headDiv">
            <img :src="headeImgUrl">
          </div>
          <div class="headRightDiv">
            <ul>
              <li>
                <div class="userName">昵称：用户12813</div>
              </li>
              <li>
                <div>真实姓名：未知</div>
                <div>邀请人：暂无<span class="modifyLinkPerson">修改联系人</span></div>
                <div>成为客户：2019-08-01(1天内)</div>
              </li>
              <li>
                <div>最近浏览：2019-08-01(1天内)</div>
                <div>手机号：未知</div>
                <div>OpenID：o-2MM5JL0q5cpkqdLfHHaJ7noDkw</div>
              </li>
              <li>
                <div>WxUnionID： 未知</div>
                <div>累积获得积分：0</div>
                <div>累积消费金额：0</div>
              </li>
              <li>
                <div>地址：暂未添加</div>
              </li>
            </ul>

            <ul
              class="hiddenUl"
              v-if="hiddenUlFlag"
            >
              <li>
                <div>来源渠道：后台</div>
                <div>生日：未知</div>
                <div>教育程度：未知</div>
                <div>常住地：未知</div>
                <div>身份证：未知</div>
              </li>
              <li>
                <div>所属行业：未知</div>
                <div>婚姻状况：未知</div>
                <div>月收入：未知</div>
                <div>性别：未知</div>
              </li>
            </ul>
          </div>
        </div>
        <div
          class="footer"
          @click="handleCheckMore()"
        >
          <div class="footerMain">
            {{this.checkMoreText}}
          </div>
        </div>
      </div>

    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>标签信息</span><span>编辑</span></div>
      <div class="labelList">
        <span></span>
      </div>
    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>资产信息</span></div>
      <ul class="assetsUl">
        <li
          v-for="(item,index) in assetsData"
          :key="index"
        >
          <div class="assetsUlLeft">
            <img :src="item.img">
          </div>
          <div class="assetsUlRight">
            <span>{{item.cardName}}</span>
            <span>|</span>
            <span
              v-if="item.haveSetUp === true ?true:false"
              style="color:#5A8BFF;cursor: pointer;"
            >设置</span>
            <span style="margin-top:10px;color:#5A8BFF;cursor: pointer;display:block">{{item.num}}</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>交易统计</span><span>订单列表</span></div>
      <div class="transactionDiv">
        <div
          style="flex:1"
          v-for="(item,index) in transactionData"
          :key="index"
          :class="index!==0?'borderLeft':''"
        >
          <p>{{item.title}}</p>
          <div class="transactionBottom">{{item.content}}</div>
        </div>
      </div>
    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>分销统计</span></div>
      <div class="transactionDiv">
        <div
          style="flex:1"
          v-for="(item,index) in distributionData"
          :key="index"
          :class="index!==0?'borderLeft':''"
        >
          <p>{{item.title}}</p>
          <div class="transactionBottom">{{item.content}}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      headeImgUrl: this.$imageHost + '/image/admin/head_icon.png',
      assetsData: [
        {
          img: this.$imageHost + '/image/admin/asset1.png',
          cardName: '普通卡',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset2.png',
          cardName: '限次卡',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/grade_card_icon.png',
          cardName: '等级卡',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset3.png',
          cardName: '储值余额',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset5.png',
          cardName: '可用积分',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset4.png',
          cardName: '可用优惠券数',
          num: 0,
          numColor: 1,
          haveSetUp: false
        }
      ],
      transactionData: [
        {
          title: '最近下单时间',
          content: '暂未下单'
        },
        {
          title: '客单价',
          content: '￥ 0.00'
        },
        {
          title: '累计下单金额',
          content: '暂未下单'
        },
        {
          title: '累计消费订单数',
          content: '￥ 0.00'
        },
        {
          title: '累计退款',
          content: '0'
        },
        {
          title: '累计退款订单数',
          content: '￥ 0.00'
        }

      ],
      distributionData: [
        {
          title: '获返利订单数量',
          content: '暂未'
        },
        {
          title: '返利商品总金额(元)',
          content: '0.00'
        },
        {
          title: '获返利佣金总金额(元)',
          content: '0.00'
        },
        {
          title: '已提现佣金总金额(元)',
          content: '0.00'
        },
        {
          title: '下级用户数',
          content: '0'
        },
        {
          title: '分销员等级',
          content: '分销员测试'
        },
        {
          title: '分销员分组',
          content: '/'
        }
      ],
      hiddenUlFlag: false,
      checkMoreText: '查看更多'
    }
  },
  methods: {
    // 点击查看更多
    handleCheckMore () {
      this.hiddenUlFlag = !this.hiddenUlFlag
      if (this.hiddenUlFlag === true) {
        this.checkMoreText = '收起'
      } else {
        this.checkMoreText = '查看更多'
      }
      console.log(this.hiddenUlFlag)
    }
  }

}
</script>
<style scoped>
.membersDetailContent {
  padding: 10px;
  padding-bottom: 68px;
  /* padding-right: 23px; */
  min-width: 1300px;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.membersDetailContentMain {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
}
.titleEdit {
  padding: 0 25px;
  height: 50px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #e6e9f0;
}
.titleEdit span:nth-of-type(2) {
  font-size: 12px;
  color: #5a8bff;
  margin-left: 12px;
  cursor: pointer;
}
.topMain {
  padding: 20px 50px;
  display: flex;
}
.headDiv img {
  width: 54px;
  height: 54px;
}
.headRightDiv {
  margin-left: 25px;
}
.headRightDiv ul li {
  line-height: 25px;
  display: flex;
  font-size: 12px;
  color: #777;
}
.headRightDiv ul li div {
  width: 300px;
}
.headRightDiv .userName {
  font-size: 15px;
  color: #333;
  line-height: 30px;
}
.modifyLinkPerson {
  color: #0e70ca;
  padding-left: 10px;
  cursor: pointer;
}
.footer {
  height: 41px;
  padding: 0 20px;
}
.footerMain {
  height: 41px;
  line-height: 41px;
  text-align: center;
  border-top: 1px solid #eee;
  color: #5a8bff;
  cursor: pointer;
}
.topContainer {
  background-color: #fff;
  margin-top: 10px;
}
.labelList {
  padding: 20px;
}
.assetsUl {
  padding: 30px 20px;
  display: flex;
}
.assetsUl li {
  margin-right: 20px;
  width: 172px;
  height: 100px;
  border: 1px solid #eee;
  box-shadow: 0px 0px 10px #f3f3f3;
  padding: 30px 0px 0 12px;
  display: flex;
}
.assetsUlRight {
  margin-left: 10px;
  font-size: 12px;
}
.assetsUlRight span:nth-of-type(4) {
  display: block;
}
.transactionDiv {
  padding: 40px 0;
  display: flex;
}
.transactionDiv p {
  text-align: center;
}
.transactionBottom {
  margin-top: 25px;
  font-size: 22px;
  color: #5a8bff;
  text-align: center;
}
.borderLeft {
  border-left: 1px solid #eee;
}
.hiddenUl {
  margin-top: 10px;
}
.hiddenUl li div {
  width: 195px !important;
}
</style>
