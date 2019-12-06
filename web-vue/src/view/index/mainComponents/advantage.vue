<template>
  <div class="advan">
    <div class="light_head">
      <h4><span style="color: #fff;">{{$t('messages.index_advantage_top_title')}}</span></h4>
      <div>{{$t('messages.index_advantage_top_content')}}</div>
    </div>
    <div id="slider">
      <div class="scroll">
        <div
          class="swiper-pagination"
          slot="pagination"
        ></div> <!-- 标页码 -->
        <swiper :options="swiperOption">
          <swiper-slide>
            <div class="advan_li_left">
              <img
                :src="imageUrlData[0].image_1"
                alt=""
              >
            </div>
            <div class="advan_li_right">
              <span></span>
              <h2>{{$t('messages.index_advantage_title_1')}}</h2>
              <p>
                {{$t('messages.index_advantage_content_1')}}
              </p>
              <p>
                {{$t('messages.index_advantage_content_2')}}
              </p>
              <p>
                {{$t('messages.index_advantage_content_3')}}
              </p>
              <p>
                {{$t('messages.index_advantage_content_4')}}
              </p>
              <p style="width: 500px;">
                {{$t('messages.index_advantage_content_5')}}
                {{$t('messages.index_advantage_content_6')}}
                <a
                  style="cursor:pointer"
                  @click="to_apply()"
                >{{$t('messages.index_advantage_title_2')}}</a>
              </p>
            </div>
          </swiper-slide>
          <swiper-slide>
            <div class="advan_li_left">
              <img
                :src="imageUrlData[1].image_2"
                alt=""
              >
            </div>
            <div class="advan_li_right">
              <span></span>
              <h2>{{$t('messages.index_advantage_2_title_1')}}</h2>
              <p>
                {{$t('messages.index_advantage_2_content_1')}}
              </p>
              <p>
                {{$t('messages.index_advantage_2_content_2')}}
              </p>
              <p>
                {{$t('messages.index_advantage_2_content_3')}}
              </p>
              <a
                style="cursor:pointer"
                @click="to_apply()"
              >{{$t('messages.index_advantage_2_title_2')}}</a>
            </div>
          </swiper-slide>
          <swiper-slide>
            <div class="advan_li_left">
              <img
                :src="imageUrlData[2].image_3"
                alt=""
              >
            </div>
            <div class="advan_li_right">
              <span></span>
              <h2>{{$t('messages.index_advantage_3_title_1')}}</h2>
              <p>
                {{$t('messages.index_advantage_3_content_1')}}
              </p>
              <p>
                {{$t('messages.index_advantage_3_content_2')}}
              </p>
              <p>
                {{$t('messages.index_advantage_3_content_3')}}
              </p>
              <a
                style="cursor:pointer"
                @click="to_apply()"
              >{{$t('messages.index_advantage_3_title_2')}}</a>
            </div>
          </swiper-slide>
          <swiper-slide>
            <div class="advan_li_left">
              <img
                :src="imageUrlData[3].image_4"
                alt=""
              >
            </div>
            <div class="advan_li_right">
              <span></span>
              <h2>{{$t('messages.index_advantage_4_title_1')}}</h2>
              <p>
                {{$t('messages.index_advantage_4_content_1')}}
              </p>
              <p>
                {{$t('messages.index_advantage_4_content_2')}}
              </p>
              <p>
                {{$t('messages.index_advantage_4_content_3')}}
              </p>
              <a
                style="cursor:pointer"
                @click="to_apply()"
              >{{$t('messages.index_advantage_4_title_2')}}</a>
            </div>
          </swiper-slide>
          <swiper-slide>
            <div class="advan_li_left">
              <img
                :src="imageUrlData[4].image_5"
                alt=""
              >
            </div>
            <div class="advan_li_right">
              <span></span>
              <h2>{{$t('messages.index_advantage_5_title_1')}}</h2>
              <p>
                {{$t('messages.index_advantage_5_content_1')}}
              </p>
              <p>
                {{$t('messages.index_advantage_5_content_2')}}
              </p>
              <p>
                {{$t('messages.index_advantage_5_content_3')}}
              </p>
              <a
                style="cursor:pointer"
                @click="to_apply()"
              >{{$t('messages.index_advantage_5_title_2')}}</a>
            </div>
          </swiper-slide>
          <swiper-slide>
            <div class="advan_li_left">
              <img
                :src="imageUrlData[5].image_6"
                alt=""
              >
            </div>
            <div class="advan_li_right">
              <span></span>
              <h2>{{$t('messages.index_advantage_6_title_1')}}</h2>
              <p>
                {{$t('messages.index_advantage_6_content_1')}}
              </p>
              <p>
                {{$t('messages.index_advantage_6_content_2')}}
              </p>
              <a
                style="cursor:pointer"
                @click="to_apply()"
              >{{$t('messages.index_advantage_6_title_2')}}</a>
            </div>
          </swiper-slide>
        </swiper>
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
          stopOnLastSlide: false, // 当切换到最后一个slide时停止自动切换
          disableOnInteraction: false, // 用户操作swiper之后，是否禁止autoplay。
          waitForTransition: true // 等待过渡完毕。自动切换会在slide过渡完毕后才开始计时。
        },
        // 分页器设置
        pagination: {
          el: '.swiper-pagination',
          clickable: true,
          renderBullet: function (index, className) {
            let obj = {}
            if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en_US') {
              obj.activitis = 'Marketing activities'
              obj.management = 'Membership management'
              obj.store = 'Store management'
              obj.decoration = 'Formwork decoration'
              obj.statistics = 'data statistics'
              obj.trading = 'Online Trading'
            } else {
              obj.activitis = '营销活动'
              obj.management = '会员管理'
              obj.store = '门店管理'
              obj.decoration = '模板装修'
              obj.statistics = '数据统计'
              obj.trading = '在线交易'
            }
            var text
            switch (index) {
              case 0: text = obj.activitis; break
              case 1: text = obj.management; break
              case 2: text = obj.store; break
              case 3: text = obj.decoration; break
              case 4: text = obj.statistics; break
              case 5: text = obj.trading; break
            }
            return '<span class="' + className + '">' + text + '</span>'
          }
        }
      },
      imageUrlData: [
        { image_1: this.$imageHost + '/image/admin/official/advan_1.png?v=1' },
        { image_2: this.$imageHost + '/image/admin/official/advan_2.png?v=1' },
        { image_3: this.$imageHost + '/image/admin/official/advan_3.png?v=1' },
        { image_4: this.$imageHost + '/image/admin/official/advan_4.png?v=1' },
        { image_5: this.$imageHost + '/image/admin/official/advan_5.png?v=1' },
        { image_6: this.$imageHost + '/image/admin/official/advan_6.png?v=1' }
      ]
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {

    // 跳转到申请试用页面
    to_apply () {
      this.$router.push({
        name: 'indexHomeOntrial'
      })
    }
  }
}
</script>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
.advan {
  padding: 1px 0 100px;
  width: 100%;
  background: url("../../../assets/indexImg/advan_back.png") no-repeat;
}
.advan .light_head,
.advan .light_head div {
  color: #fff;
}
.light_head {
  text-align: center;
  padding-top: 1px;
}
.light_head h4 {
  margin: 70px 0 0px 0;
  font-size: 30px;
  color: #333;
  font-weight: normal;
}
.advan_li_left {
  float: left;
}
.advan_li_right {
  width: 500px;
  float: left;
  padding-top: 30px;
  margin-left: 40px;
  color: #fff;
}
.advan_li_right span {
  display: block;
  width: 27px;
  height: 5px;
  background: #7edfdd;
  margin: 13px 0 18px;
}
.advan_li_right h2 {
  margin: 0 0 20px;
  font-size: 24px;
}
.advan_li_right p {
  margin-bottom: 10px;
  font-size: 16px;
  background: url("../../../assets/indexImg/avan_circle.png") 1px 8px no-repeat;
  padding-left: 16px;
}
.advan_li_right a {
  display: block;
  width: 135px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  border: 1px solid #fff;
  color: #fff;
  font-size: 16px;
  margin-top: 30px;
  border-radius: 8px;
}
.advan_li_right a:hover {
  background: rgba(255, 255, 255, 0.2);
  text-decoration: none;
}
.light_head h4 span {
  display: inline-block;
  padding-bottom: 20px;
  box-sizing: border-box;
}
.light_head div,
.about_prompt {
  color: #666;
  font-size: 16px;
  margin-bottom: 20px;
}
#slider {
  width: 1200px;
  margin: 70px auto 0;
}
</style>

<style>
/* 重写标页码样式 */
.advan .swiper-pagination .swiper-pagination-bullet {
  color: #fff;
  display: inline-block;
  flex: 1;
  height: 100%;
  line-height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 35px;
  text-align: center;
  border-right: 1px solid #fff;
  cursor: pointer;
  border-radius: 0px;
  background-color: #7facf3;
  opacity: 1;
  padding: 10px 15px;
  box-sizing: border-box;
}
/* 重写标页码选中时样式 */
.advan .swiper-pagination-bullet-active {
  background-color: #fff !important;
  color: #5a8bff !important;
  opacity: 1;
}
.advan .swiper-pagination {
  /* margin-top: -50px; */
  width: 631px;
  margin: 0px auto 0px;
  border: 1px solid #fff;
  border-radius: 5px;
  color: #fff;
  display: flex;
  height: auto;
  position: absolute;
  left: 30%;
}
.advan .swiper-container {
  position: relative;
  top: 100px;
}
</style>
