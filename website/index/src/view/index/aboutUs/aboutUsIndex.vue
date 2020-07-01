<template>
  <div class="aboutus_container">
    <div class="banner"></div>
    <div class="container">
      <div class="con_introduce">
        <div class="intr_content">
          <h2 class="common_h2">{{$t('aboutUs.title')}}</h2>
          <!-- <ul>
            <li>
              <h2>10000+</h2>
              {{$t('aboutUs.tiliang')}}
            </li>
            <li :style="imageUrlData[0]">
              <h2>100%</h2>
              {{$t('aboutUs.zengzhang')}}
            </li>
            <li :style="imageUrlData[1]">
              <h2>56.16%</h2>
              {{$t('aboutUs.kehu')}}
            </li>
            <li :style="imageUrlData[2]">
              <h2>91.3%</h2>
              {{$t('aboutUs.xufei')}}
            </li>
            <li :style="imageUrlData[3]">
              <h2>CMMI3</h2>
              {{$t('aboutUs.renzheng')}}
            </li>
          </ul> -->
          <div class="content">
            <p>天津掌商先机网络科技有限公司，零售云服务提供商，基于云计算SaaS服务模式，以体系化解决方案，助力零售企业数字化智能化管理升级，成就企业规模化发展之路。</p>
            <p>2017年，店家易成立于天津，之后在北京、上海、广州、杭州、义乌等25个省市设立了分支机构，员工近1000人，服务范围辐射全国并延伸至海外。 凭借技术创新、产品创新、服务创新和市场创新，店家易实现了每年100%以上的客户增长；10万+客户涵盖了中粮、强生、3M、百威、周黑鸭、MG小象、水密码等世界500强、上市公司、知名品牌、TOP商家……2017全年交易额近万亿。 规模化客户及头部客户优势给予了店家易更高更全的行业视角，市场敏感度及强执行力。 店家易创始团队来自互联网上市公司技术管理层，产品及研发团队占比35%以上，核心成员皆为清华、北大、北邮等985大学研究生，拥有ACM、数学建模等重量级竞赛获奖经历，具有千万用户运营经验。多年来店家易构建了内外部“互联网、电商、管理”等资源池，“技术流”行业大咖云集。</p>
            <p>未来，店家易仍将继续秉持以人为本、客户至上、持续创新、产业共赢的发展理念，打造电商一体化零售企业服务生态。</p>
            <!-- <p>{{$t('aboutUs.p_1')}}</p>
            <p>{{$t('aboutUs.p_2')}}</p>
            <p>{{$t('aboutUs.p_3')}}</p>
            <p>{{$t('aboutUs.p_4')}}</p> -->

          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      imageUrlData: [
        { imgUrl: 'background: url(' + this.$imageHost + '/image/admin/official/introduce/02.png' + ') center center no-repeat' },
        { imgUrl: 'background: url(' + this.$imageHost + '/image/admin/official/introduce/03.png' + ') center center no-repeat' },
        { imgUrl: 'background: url(' + this.$imageHost + '/image/admin/official/introduce/04.png' + ') center center no-repeat' },
        { imgUrl: 'background: url(' + this.$imageHost + '/image/admin/official/introduce/01.png' + ') center center no-repeat' }
      ]
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {

  }
}
</script>
<style scoped>
h2 {
  font-size: 22px;
}
li {
  margin-left: 104px;
}
li:nth-of-type(1) {
  margin-left: 0;
}
.aboutus_container {
  background-color: #fff;
}
.banner {
  background: url(../../../assets/aboutUsImg/banner.png) center center no-repeat;
  height: 250px;
}
.container {
  width: 100%;
  margin-top: 70px;
}
.con_introduce {
  height: 343px;
  text-align: center;
}
.intr_content {
  width: 1166px;
  margin: 0 auto;
  padding: 0 13px;
}
.intr_content ul {
  width: 1123px;
  margin: 0 auto;
  margin-top: 70px;
  overflow: hidden;
}
.intr_content ul li {
  float: left;
  width: 141px;
  height: 124px;
  color: #333;
  font-size: 16px;
  background: url(../../../assets/aboutUsImg/01.png) center center no-repeat;
}
.intr_content ul li h2 {
  font-weight: 600;
  margin-top: 33px;
  margin-bottom: 2px;
}
.content {
  text-align: left;
  margin-top: 50px;
  line-height: 25px;
  font-size: 14px;
}
.content p {
  text-indent: 2em;
}
</style>
