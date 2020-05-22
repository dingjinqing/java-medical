<template>
  <div class="aboutus_container">
    <div class="banner"></div>
    <div class="container">
      <div class="con_introduce">
        <div class="intr_content">
          <h2 class="common_h2">{{$t('aboutUs.title')}}</h2>
          <ul>
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
          </ul>
          <div class="content">

            <p>{{$t('aboutUs.p_1')}}</p>
            <p>{{$t('aboutUs.p_2')}}</p>
            <p>{{$t('aboutUs.p_3')}}</p>
            <p>{{$t('aboutUs.p_4')}}</p>

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
  height: 643px;
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
