<template>
  <div class="container">
    <div class="top">
      <h4>
        <span>{{$t('messages.index_application_title_1')}}</span>
      </h4>
      <div>{{$t('messages.index_application_content_1')}}</div>
    </div>
    <ul class="light_four">
      <li class=" ">
        <img
          :src="imageUrlData[0].image_1"
          class=""
        >
        <div>{{$t('messages.index_application_title_2')}}</div>
        <p>{{$t('messages.index_application_content_2')}}</p>
      </li>
      <li class=" ">
        <img
          :src="imageUrlData[1].image_2"
          class=""
        >
        <div>{{$t('messages.index_application_title_3')}}</div>
        <p>{{$t('messages.index_application_content_3')}}</p>
      </li>
      <li class=" ">
        <img
          :src="imageUrlData[2].image_3"
          class=""
        >
        <div>{{$t('messages.index_application_title_4')}}</div>
        <p>{{$t('messages.index_application_content_4')}}</p>
      </li>
      <li class=" ">
        <img
          :src="imageUrlData[3].image_4"
          class=""
        >
        <div>{{$t('messages.index_application_title_5')}}</div>
        <p>{{$t('messages.index_application_content_5')}}</p>
      </li>
    </ul>
  </div>
</template>
<script>
export default {
  data () {
    return {
      imageUrlData: [
        { image_1: this.$imageHost + '/image/admin/official/marketing1.png' },
        { image_2: this.$imageHost + '/image/admin/official/marketing2.png' },
        { image_3: this.$imageHost + '/image/admin/official/marketing3.png' },
        { image_4: this.$imageHost + '/image/admin/official/marketing4.png' }

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
div {
  box-sizing: border-box;
}
.container {
  min-width: 100%;
  padding: 0 0px 20px;
}
.top {
  text-align: center;
  padding-top: 1px;
}
.top h4 {
  margin: 70px 0 0px 0;
  font-size: 30px;
  color: #333;
  font-weight: normal;
}
.top h4 span {
  display: inline-block;
  padding-bottom: 20px;
  font-family: 微软雅黑 !important;
}
.top div {
  color: #666;
  font-size: 16px;
  margin-bottom: 20px;
}
.light_four {
  width: 1024px;
  display: flex;
  margin: 0 auto;
}
.light_four li {
  flex: 1;
  padding: 50px 10px;
  text-align: center;
  cursor: pointer;
  position: relative;
  list-style: none;
  float: left;
}
.light_four li p {
  color: #999;
  font-size: 14px;
  text-align: left;
  width: 200px;
  margin: 0 auto;
  text-align: justify;
}
.light_four li div {
  font-size: 16px;
  color: #333;
  margin: 30px 0 20px;
}
</style>
