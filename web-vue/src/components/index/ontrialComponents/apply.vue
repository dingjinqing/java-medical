<template>
  <div class="p_apply">
    <div class="p_apply_free">
      <div class="apply_left">
        <div class="tiao"></div>
        <h5 class="p_h2">{{$t('apply.title')}}</h5>
        <div class="input_icon">
          <input
            type="contact"
            :placeholder="placeholder_name"
            name="contact"
            v-model="username"
          >
          <span>{{$t('apply.name')}}</span>
        </div>
        <div class="input_icon">
          <input
            type="mobile"
            :placeholder="placeholder_tel"
            name="mobile"
            onkeyup="value=value.replace(/[^\d\-]/g,'')"
            v-model="phonenum"
          >
          <span>{{$t('apply.mobile')}}</span>
        </div>
        <div
          class="p_apply_submit"
          @click="handlesubmit()"
        >{{$t('apply.submit')}}</div>
      </div>
      <div class="apply_right">
        <img
          src="http://mpimg2.weipubao.cn/image/admin/index_circle.png"
          alt=""
        >
        <h3>{{$t('apply.content')}}</h3>
        <h4>{{$t('apply.content_bottom')}}</h4>
      </div>
    </div>
  </div>
</template>
<script>
import { applyrequest } from '@/api/index/apply.js'
export default {
  data () {
    return {
      username: '',
      phonenum: '',
      placeholder_name: '请填写您的姓名',
      placeholder_tel: '请填写手机号'
    }
  },
  mounted () {
    this.$http.$emit('to_rej')
    let that = this
    this.langDefault()
    that.$http.$on('lang_change', function (data) {
      console.log(data)
      if (data === 0) {
        that.placeholder_name = 'Please fill in your name'
        that.placeholder_tel = 'Please fill in your cell phone number'
        return
      }
      that.placeholder_name = '请填写您的姓名'
      that.placeholder_tel = '请填写手机号'
    })
  },
  methods: {
    // 初始化语言
    langDefault () {
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en') {
        this.$i18n.locale = 'en'
        this.placeholder_name = 'Please fill in your name'
        this.placeholder_tel = 'Please fill in your cell phone number'
      } else {
        this.$i18n.locale = 'cn'
      }
    },
    handlesubmit () {
      console.log(this.username, this.phonenum)
      console.log(applyrequest)
      // let obj = {
      //   contact: this.username,
      //   mobile: this.phonenum
      // }
      applyrequest().then((res) => {
        console.log(res)
      })
    }
  }
}
</script>
<style scoped>
.p_apply {
  background: url(../../../assets/applyImg/apply_back.png) no-repeat;
  padding: 45px 0;
}
.p_apply_free {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: #666;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(219, 188, 245, 0.35);
  padding: 20px;
  width: 1000px;
  margin: 0 auto;
}
.apply_left,
.apply_right {
  width: 50%;
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.tiao {
  border: 1px solid #5a8bff;
  width: 20px;
  left: -140px;
  position: relative;
  margin-bottom: 5px;
}
.p_h2 {
  font-size: 18px;
  color: #333;
  margin-bottom: 25px;
  width: 300px;
  font-weight: normal;
}
.input_icon {
  position: relative;
}
.apply_left input {
  width: 300px;
  height: 50px;
  margin-bottom: 20px;
  padding-left: 70px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
.input_icon span {
  font-size: 14px;
  display: inline-block;
  width: 55px;
  height: 16px;
  line-height: 16px;
  color: #333;
  position: absolute;
  text-align: center;
  top: 17px;
  left: 5px;
  border-right: 1px solid #ccc;
}
.p_apply_free h3 {
  font-size: 16px;
  color: #333;
  margin-bottom: 5px;
  margin-top: 5px;
}
.p_apply_free h4 {
  font-size: 14px;
  color: #666;
}
.p_apply_submit {
  width: 300px;
  height: 40px;
  line-height: 40px;
  color: #fff;
  background: #5a8bff;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  margin: 0 auto;
  text-align: center;
  cursor: pointer;
}
</style>
