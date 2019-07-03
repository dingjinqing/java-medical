<template>
  <div class="contact_container">
    <div class="container">
      <h2 class="common-h2">{{$t('contact.title')}}</h2>
      <!--<img src="__STATIC__/index/img/about-us/introduce/07.png" alt="">-->
      <div class="efficiency">
        <div>
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:85px;right:390px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>

        <div style="top:96px;right:423px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:120px;right:336px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>

        <div style="top:126px;right:390px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>

        <div style="top:132px;right:502px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:166px;right:429px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:172px;right:352px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:202px;right:373px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:210px;right:318px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:210px;right:590px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:226px;right:481px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:246px;right:326px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:302px;right:346px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:346px;right:436px;">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
        <div style="top:352px;right:392px">
          <span class="pulse delay-06"></span>
          <span class="pulse delay-05"></span>
          <span class="pulse delay-04"></span>
        </div>
      </div>
      <div class="bottom">
        <span>：{{$t('contact.adress')}}</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
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
.contact_container {
  height: 1023px;
  background: #f6f7fa;
  text-align: center;
  padding-top: 70px;
}
.container {
  position: relative;
  background: url(../../../assets/aboutUsImg/07.png) center center no-repeat;
  height: 875px;
  padding: 0;
}
.contact_container h2 {
  font-size: 22px;
}
.efficiency {
  position: relative;
  margin-top: 223px;
}
.efficiency div {
  position: absolute;
  top: 38px;
  right: 398px;
}
@keyframes warn {
  from {
    -webkit-transform: scale3d(0.2, 0.2, 0.2);
    transform: scale3d(0.2, 0.2, 0.2);
  }

  to {
    -webkit-transform: scale3d(1, 1, 1);
    transform: scale3d(1, 1, 1);
  }
}

@-webkit-keyframes warn /* Safari 与 Chrome */ {
  from {
    -webkit-transform: scale3d(0.2, 0.2, 0.2);
    transform: scale3d(0.2, 0.2, 0.2);
  }

  to {
    -webkit-transform: scale3d(1, 1, 1);
    transform: scale3d(1, 1, 1);
  }
}
.pulse {
  position: absolute;
  top: 1px;
  left: -254px;
  height: 88px;
  width: 88px;
  border: 2px solid #2d79ff;
  border-radius: 48px;
  box-shadow: 0 0 12px #2d79ff, 0 0 20px #2d79ff inset;
  opacity: 0.4;
  animation: warn 2s ease-out 2s infinite;
  background: 0 0;
}
.delay-06 {
  animation-delay: 2s;
}
.delay-05 {
  animation-delay: 1s;
}
.delay-04 {
  animation-delay: 1.5s;
}
.bottom {
  position: absolute;
  bottom: 60px;
  width: 100%;
  text-align: center;
}
.bottom span {
  font-size: 20px;
  color: #3d80f5;
  position: relative;
  display: inline-block;
}
.bottom span::before {
  content: url(../../../assets/aboutUsImg/08.png);
  position: absolute;
  margin-left: -30px;
  top: 1px;
}
</style>
