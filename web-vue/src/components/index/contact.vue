<template>
  <div class="Contact">
    <div class="tool_bar">
      <div
        class="tool_bar_list"
        @mouseenter="con_enter(index)"
        @mouseleave="con_leave(index)"
        v-for="(item,index) in contactData"
        :key="index"
      >
        <a :href="item.ahref">
          <span
            class="bar_span"
            :class="[item.caontact_class,item.show_hidden_class]"
          >{{item.phonenum}}</span>
          <span class="bar_block">
            <img
              :src="item.imgUrl"
              alt=""
            >
            {{item.title}}
          </span>
        </a>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      contactData: '',
      contactData_en: [
        { show_hidden_class: 'show_hidden_class', caontact_class: '', phonenum: 'customer service telephone numbers:400-010-1039', title: 'customer service telephone numbers', ahref: '##', imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/tel.png' },
        { show_hidden_class: '', caontact_class: '', phonenum: 'ConsultationQQ:3003715029', title: 'QQ Consultation', ahref: 'http://wpa.qq.com/msgrd?v=3&amp;uin=3003715029&amp;site=qq', imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/QQ.png' }
      ],
      contactData_cn: [
        { caontact_class: '', phonenum: '客服电话:400-010-1039', title: '客服电话', ahref: '##', imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/tel.png' },
        { caontact_class: '', phonenum: '咨询QQ:3003715029', title: 'QQ咨询', ahref: 'http://wpa.qq.com/msgrd?v=3&amp;uin=3003715029&amp;site=qq', imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/QQ.png' }
      ]
    }
  },
  mounted () {
    let that = this
    this.langDefault()
    this.$http.$on('lang_change', function (data) {
      if (data === 0) {
        that.contactData = that.contactData_en
      } else if (data === 1) {
        that.contactData = that.contactData_cn
      }
    })
  },
  methods: {
    // 初始化语言
    langDefault () {
      console.log(localStorage.getItem('WEPUBAO_LANGUAGE'))
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en') {
        this.contactData = this.contactData_en
        return
      }
      this.contactData = this.contactData_cn
    },
    // 鼠标划入事件
    con_enter (index) {
      this.contactData[index].caontact_class = 'caontact_class'
    },
    // 鼠标划出事件
    con_leave (index) {
      this.contactData[index].caontact_class = ''
    }
  }
}
</script>
<style scoped>
.tool_bar {
  display: block;
  position: fixed;
  right: 0;
  bottom: 30%;
  z-index: 100;
}
.tool_bar_list {
  width: 60px;
  height: auto;
  margin-bottom: 5px;
  cursor: pointer;
  padding: 0;
  position: relative;
  text-align: center;
  font-size: 12px;
}
.tool_bar_list a {
  text-decoration: none;
  display: block;
}
.bar_span {
  display: inline-block;
  height: auto;
  width: 189px;
  line-height: 57px;
  text-align: center;
  position: absolute;
  top: 8px;
  left: -200px;
  color: #5a8bff;
  background: url(../../assets/indexImg/tel_border.png?v=1) no-repeat;
  background-size: 100% 100%;
  -webkit-transition: all 1s;
  -moz-transition: all 1s;
  transition: all 1s;
  display: none;
  font-size: 14px;
}
.bar_block {
  display: inline-block;
  width: 63px;
  height: auto;
  background: rgba(153, 153, 153, 0.5);
  color: #fff;
  position: relative;
  z-index: 22;
  padding: 0 2px 12px;
  word-wrap: break-word;
  text-align: center;
}
.bar_block img {
  display: block;
  margin: 10px auto 1px;
}
.caontact_class {
  display: block !important;
  width: 226px !important;
  left: -223px !important;
}
.show_hidden_class {
  line-height: 35px !important;
}
</style>
