<template>
  <div class="work_container">
    <div class="container">
      <h2 class="common-h2">{{$t('environment')}}</h2>
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
    // 初始化语言
    langDefault () {
      console.log(localStorage.getItem('WEPUBAO_LANGUAGE'))
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en') {
        this.$i18n.locale = 'en'
        return
      }
      this.$i18n.locale = 'cn'
    }
  }
}
</script>
<style scoped>
.work_container {
  height: 662px;
  background: url(../../../assets/aboutUsImg/06.png) center center no-repeat;
  text-align: center;
  padding-top: 70px;
}
.container {
  width: 1166px;
  margin: 0 auto;
  padding: 0 13px;
}
.container h2 {
  font-size: 22px;
}
</style>
