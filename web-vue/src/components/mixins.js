const myMixin = {
  methods: {
    // 初始化语言
    langDefault () {
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en_US') {
        this.$i18n.locale = 'en'
        this.Recommend_class = 'Recommend_class'

        this.lang_with = 'width:63px'
        this.langData_show = this.langData_en
        this.loginData_show = this.loginData_en
        this.mar_class = 'mar_class'

        this.imgUrlData = this.imgUrlData_en

        this.contactData = this.contactData_en
      } else {
        this.$i18n.locale = 'cn'
        this.Recommend_class = ''

        this.langData_show = this.langData_cn
        this.loginData_show = this.loginData_cn
        this.mar_class = ''

        this.imgUrlData = this.imgUrlData_cn

        this.contactData = this.contactData_cn
      }
    }
  }
}
export default myMixin
