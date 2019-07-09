const myMixin = {
  methods: {
    // 初始化语言
    langDefault () {
      console.log(localStorage.getItem('WEPUBAO_LANGUAGE'))
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en_US') {
        this.$i18n.locale = 'en'
        this.Recommend_class = 'Recommend_class'

        this.lang_with = 'width:63px'
        this.langData_show = this.langData_en
        this.loginData_show = this.loginData_en
        this.mar_class = 'mar_class'

        this.imgUrlData = this.imgUrlData_en

        this.contactData = this.contactData_en

        // admin contact
        this.en_phone = 'admin_en_phone_width'
        this.en_line = 'admin_en_onLine_width'
        // admin menu width
        this.menu_width = 'admin_menu_width'

        // admin head width
        this.modifyWidth = 'headWidth'

        // admin 修改头像遮罩span高度适配
        this.maskspanheight = 'maskspanheight'

        // admin imageDalog tips 行高
        this.imageDalogTip_lineHeight = 'imageDalogTip_lineHeight'

        // admin imagrDalog p 适配
        this.imageDalog_p_height = 'imageDalog_p_height'

        // admin imageDalog totle适配
        this.admin_imageDalog_totle = 'admin_imageDalog_totle'
      } else {
        this.$i18n.locale = 'cn'
        this.Recommend_class = ''

        this.langData_show = this.langData_cn
        this.loginData_show = this.loginData_cn
        this.mar_class = ''

        this.imgUrlData = this.imgUrlData_cn

        this.contactData = this.contactData_cn

        // admin contact
        this.en_phone = ''
        this.en_line = ''
        // admin menu width
        this.menu_width = ''

        // admin head width
        this.modifyWidth = ''

        // admin 修改头像遮罩span高度适配
        this.maskspanheight = ''

        // admin imageDalog tips 行高
        this.imageDalogTip_lineHeight = ''

        // admin imagrDalog p 适配
        this.imageDalog_p_height = ''

        // admin imageDalog totle适配
        this.admin_imageDalog_totle = ''
      }
    }
  }
}
export default myMixin
