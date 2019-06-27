<template>
  <div class="case_container">
    <div class="light_head">
      <h4><span>{{$t('case.title')}}</span></h4>
      <div>{{$t('case.content')}}</div>
    </div>
    <ul class="case_ul clearfix">
      <li
        :class="item.class_active"
        @mouseenter="enter(index)"
        @mouseleave="leave(index)"
        v-for="(item,index) in imgUrlData"
        :key="index"
      >
        <img
          :src="item.imgUrl"
          alt=""
          class="case_img"
        >
        <div>{{item.title}}</div>
        <p>
          {{item.content}}
        </p>
        <div
          class="case_hover "
          :class="item.hidden_class"
        >
          <img
            :src="item.hiddenImg"
            style="display: block"
          >
          <div>{{item.title}}</div>
        </div>
      </li>
    </ul>
  </div>
</template>
<script>
export default {
  data () {
    return {
      imgUrlData: '',
      imgUrlData_cn: [
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry1.png', title: '品牌小程序', content: '平台一体化 数据多维度分析 各行各业全面兼容', hiddenImg: 'http://miniimg.cn/image/admin/official/code1.png', hidden_class: '', class_active: '' },
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry2.png', title: '婚纱摄影', content: '样片展示 报价获取 优惠折扣 晒单评论 品牌推广', hiddenImg: 'http://miniimg.cn/image/admin/official/code2.png', hidden_class: '', class_active: '' },
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry3.png?v=1', title: '服装', content: '线上展示 砍价拼团 折扣优惠 会员管理 可视化装修', hiddenImg: 'http://miniimg.cn/image/admin/official/code3.png', hidden_class: '', class_active: '' },
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry4.png?v=1', title: '数码', content: '活动多样 商品展示 限时抢购 秒杀抽奖 在线交易', hiddenImg: 'http://miniimg.cn/image/admin/official/code4.png', hidden_class: '', class_active: '' },
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry5.png', title: '新美业', content: '在线预约 门店买单 技师管理 会员营销 活动推广', hiddenImg: 'http://miniimg.cn/image/admin/official/code5.png', hidden_class: '', class_active: '' }
      ],
      imgUrlData_en: [
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry1.png', title: 'Brand applet', content: 'Multi-Dimensional Analysis of Platform Integration Data Compatibility in All walks of life', hiddenImg: 'http://miniimg.cn/image/admin/official/code1.png', hidden_class: '', class_active: '' },
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry2.png', title: 'Wedding photography', content: 'Comment on Brand Promotion by Sample Display Quotation for Preferential Discount', hiddenImg: 'http://miniimg.cn/image/admin/official/code2.png', hidden_class: '', class_active: '' },
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry3.png?v=1', title: 'Clothing', content: 'On-line display of bargaining and group discount concessional membership management visual decoration', hiddenImg: 'http://miniimg.cn/image/admin/official/code3.png', hidden_class: '', class_active: '' },
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry4.png?v=1', title: 'Digital', content: 'Activities Various Commodities Show Time-Limited Buy Second Kill Draw Online Trading', hiddenImg: 'http://miniimg.cn/image/admin/official/code4.png', hidden_class: '', class_active: '' },
        { imgUrl: 'http://miniimg.cn/image/admin/official/industry5.png', title: 'New Aesthetics', content: 'Online booking shop billing technicians manage member marketing activities promotion', hiddenImg: 'http://miniimg.cn/image/admin/official/code5.png', hidden_class: '', class_active: '' }
      ]
    }
  },
  mounted () {
    let that = this
    this.langDefault()
    this.$http.$on('lang_change', function (data) {
      if (data === 0) {
        that.imgUrlData = that.imgUrlData_en
      } else if (data === 1) {
        that.imgUrlData = that.imgUrlData_cn
      }
    })
  },
  methods: {
    // 初始化语言
    langDefault () {
      console.log(localStorage.getItem('WEPUBAO_LANGUAGE'))
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en') {
        this.imgUrlData = this.imgUrlData_en
        return
      }
      this.imgUrlData = this.imgUrlData_cn
    },
    // 图片划入事件
    enter (index) {
      this.imgUrlData[index].hidden_class = 'show_img'
      this.imgUrlData[index].class_active = 'li_active'
    },
    // 图片划出事件
    leave (index) {
      this.imgUrlData[index].hidden_class = ''
      this.imgUrlData[index].class_active = ''
    }
  }
}
</script>
<style scoped>
.case_container {
  min-width: 1024px;
  padding: 0 0px 20px;
}
.light_head {
  text-align: center;
  padding-top: 1px;
}
.light_head h4 {
  margin: 70px 0 0px 0;
  font-size: 30px;
  color: #333;
}
.light_head div,
.about_prompt {
  color: #666;
  font-size: 16px;
  margin-bottom: 20px;
}
.light_head h4 span {
  display: inline-block;
  padding-bottom: 20px;
}
.case_ul {
  margin: 70px auto 50px;
  width: 1024px;
  display: flex;
}
.case_ul li {
  flex: 1;
  height: auto;
  border: 1px solid #eee;
  background: #fff;
  padding: 13px;
  position: relative;
  overflow: hidden;
  margin-right: 10px;
  text-align: center;
  cursor: pointer;
}
.case_img {
  margin: 30px 0;
}
.case_ul li div {
  font-size: 18px;
  color: #333;
  margin: 10px 0;
}
.case_ul li p {
  color: #666;
  margin: 0;
  overflow: hidden;
  font-size: 14px;
}
.case_ul li .case_hover {
  margin: 0;
  padding: 25px 18px;
}
.case_hover {
  width: 100%;
  height: 100%;
  background: #f9fbff;
  position: absolute;
  top: 0;
  left: 300px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  transition: all 0.5s;
  left: 300px;
}
.show_img {
  left: 0px;
}
.li_active {
  border: 1px solid #c8dbff;
  box-shadow: 0px 0px 13px #e7f0ff;
}
</style>
