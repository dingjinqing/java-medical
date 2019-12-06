<template>
  <div class="indexMobile">
    <swiper :options="swiperOption">
      <swiper-slide
        v-for="(item,index) in swiperImgData"
        :key="index"
      >
        <img :src="item.imgUrl">
      </swiper-slide>
    </swiper>
    <div class="solution">
      <h1>{{$t('indexMobile.solution')}}</h1>
      <div class="solutionMain">
        <img :src="$imageHost+'/image/admin/official/part22.jpg'">
      </div>
    </div>
    <swiper :options="swiperAdvantageOption">
      <swiper-slide
        v-for="(item,index) in advantageData"
        :key="index"
      >
        <img :src="item.imgUrl">
      </swiper-slide>
      <div
        class="swiper-pagination"
        slot="pagination"
      ></div>
    </swiper>
    <div class="solution entrance">
      <h1>{{$t('indexMobile.flowInlet')}}</h1>
      <div class="solutionMain">
        <img :src="$imageHost+'/image/admin/official/part4.png'">
      </div>
    </div>
    <div class="solution case">
      <h1>{{$t('indexMobile.case')}}</h1>
      <md-card>
        <md-card-media class="swiper-inner">
          <swiper :options="caseOption">
            <swiper-slide
              v-for="(item,index) in caseUrlData"
              :key="index"
            >
              <img :src="item.imgUrl">
              <div class="title">{{item.divText}}</div>
              <p class="content">{{item.pText}}</p>
            </swiper-slide>
          </swiper>
        </md-card-media>
      </md-card>
    </div>
    <div class="footer">
      <div class="footerTop">
        <p>TEL：400-010-1039 7*24{{$t('indexMobile.hourlyServiceHotline')}}</p>
        <p>{{$t('indexMobile.address')}}</p>
      </div>
      <div class="footerBottom">
        <img :src="$imageHost+'/image/admin/official/weixin.jpg'">
        <p>{{$t('indexMobile.bottomText')}}</p>
      </div>
    </div>
  </div>
</template>
<script>
import 'swiper/dist/css/swiper.css'
import vueSwiper from 'vue-awesome-swiper'
import Vue from 'vue'
Vue.use(vueSwiper)
export default {
  data () {
    return {
      swiperOption: {
        autoplay: {
          delay: 3000, // 自动切换的时间间隔，单位ms
          disableOnInteraction: false // 用户操作swiper之后，是否禁止autoplay。
        },
        loop: true
      },
      swiperImgData: [
        {
          imgUrl: this.$imageHost + '/image/admin/official/part1_2.jpg'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/official/part1_1.jpg'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/official/part1_2.jpg'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/official/part1_1.jpg'
        }
      ],
      swiperAdvantageOption: {
        autoplay: {
          delay: 3000, // 自动切换的时间间隔，单位ms
          disableOnInteraction: false // 用户操作swiper之后，是否禁止autoplay。
        },
        loop: true,
        pagination: {
          el: '.swiper-pagination'
        }
      },
      advantageData: [
        {
          imgUrl: this.$imageHost + '/image/admin/official/part3_1.png'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/official/part3_2.png'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/official/part3_3.png'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/official/part3_4.png'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/official/part3_5.png'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/official/part3_6.png'
        }
      ],
      caseOption: {
        slidesPerView: 'auto',
        spaceBetween: 30,
        loop: true
      }
    }
  },
  computed: {
    caseUrlData () {
      return this.$t('indexMobile.caseUrlData')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  }
}
</script>
<style lang="scss" scoped>
.indexMobile {
  position: absolute;
  right: 0;
  left: 0;
  top: 0;
  bottom: 0;
  overflow-y: auto;
  background-color: #e5e9f2;
  img {
    width: 100%;
    float: left;
  }
  .solution {
    background: #fff;
    padding: 20px 0px;
    img {
      float: none;
    }
    h1 {
      font-size: 15px;
      font-weight: normal;
      text-align: center;
    }
  }
  .entrance {
    background: #f7f7f7;
  }
  .case {
    /deep/ .swiper-container {
      padding-left: 5%;
    }
    h1 {
      margin-bottom: 15px;
    }
    .swiper-slide {
      width: 80%;
      padding: 8px 8px 12px;
      border: 1px solid #e6e2e7;
      text-align: left;
      box-shadow: 0px 0px 8px #ccc;
    }
    .title {
      font-size: 14px;
      color: #000;
      margin: 12px 0;
    }
    .content {
      color: #666;
      font-size: 12px;
      min-height: 48px;
      line-height: 16px;
    }
  }
  .footer {
    background: #27282c;
    color: #fff;
    .footerTop {
      border-bottom: 1px solid #54565b;
      padding: 0 0 20px 20px;
      p {
        padding-top: 20px;
        font-size: 13px;
      }
    }
    .footerBottom {
      padding: 25px 20px;
      text-align: center;
      font-size: 13px;
      img {
        width: 100px;
        float: none;
      }
      p {
        margin-top: 18px;
        color: #54565b;
      }
    }
  }
}
</style>
