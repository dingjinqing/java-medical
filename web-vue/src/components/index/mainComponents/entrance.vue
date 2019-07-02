<template>
  <div class="en_contariner">
    <div class="en_box">
      <div class="light_head">
        <h4><span>{{$t('entrance.title')}}</span></h4>
        <div>{{$t('entrance.content')}}</div>
      </div>
      <div class="flow_img">
        <img
          src="http://mpimg2.weipubao.cn/image/admin/official/flow_enter1.png"
          class="flow_enter1 animated"
          :class="imgActive"
        >
        <img
          src="http://mpimg2.weipubao.cn/image/admin/official/flow_enter4.png"
          class="flow_enter2"
        >
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      imgActive: '',
      flag: 'false'
    }
  },
  mounted () {
    window.addEventListener('scroll', this.handleScroll)
  },
  methods: {
    handleScroll () {
      let scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      if (scrollTop > 2100) {
        if (this.flag === true) {
          return
        }
        this.imgActive = 'bounceInUp img_active'
        this.flag = true
      }
    }
  }
}
</script>
<style scoped>
.en_contariner {
  padding-top: 410px;
  text-align: center;
  padding-bottom: 70px;
  min-width: 1024px;
}
.en_box {
  background-color: #f7f7f7;
}
.light_head h4 {
  margin: 70px 0 0px 0;
  font-size: 30px;
  color: #333;
  padding-top: 70px;
}
.light_head div,
.about_prompt {
  color: #666;
  font-size: 16px;
  margin-bottom: 70px;
}
.light_head h4 span {
  display: inline-block;
  padding-bottom: 20px;
}
.flow_img {
  position: relative;
  width: 1100px;
  margin: 0 auto;
}
.flow_enter1 {
  left: 413px;
  position: absolute;
  bottom: 16%;
  opacity: 0;
}
.flow_enter2 {
  margin-top: 80px;
}
.img_active {
  opacity: 1 !important;
}
</style>
