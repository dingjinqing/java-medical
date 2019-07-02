<template>
  <div class="pro_highlights">
    <div class="light_head">
      <h4><span>{{$t('industry.title_top')}}</span></h4>
      <div>{{$t('industry.content')}}</div>
    </div>

    <ul class="light_four">
      <li
        v-for="(item,index) in imgUrlData"
        :key="index"
      >
        <img
          :src="item.imgUrl"
          @mouseenter="enter(index)"
          @mouseleave="leave(index)"
        >
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data () {
    return {
      imgUrlData: [
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover1.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover2.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover3.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover4.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover5.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover6.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover7.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover8.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover9.png?v=1' },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/cover10.png?v=1' }
      ]
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    // 初始化语言
    langDefault () {
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en_US') {
        this.$i18n.locale = 'en'
      } else {
        this.$i18n.locale = 'cn'
      }
    },
    // 图片划入事件
    enter (index) {
      var ind = index + 1
      this.imgUrlData[index].imgUrl = 'http://mpimg2.weipubao.cn//image/admin/official/cover_hover' + ind + '.png?v=1'
    },
    // 图片划出事件
    leave (index) {
      var ind = index + 1
      this.imgUrlData[index].imgUrl = 'http://mpimg2.weipubao.cn//image/admin/official/cover' + ind + '.png?v=1'
    }
  }
}
</script>
<style scoped>
.pro_highlights {
  min-width: 1024px;
  padding: 0 0px 20px;
}
.light_head {
  text-align: center;
  padding-top: 1px;
}
.light_head h4 {
  margin: 70px 0 0px 0;
  font-size: 30px;
  color: #333;
}
.light_head div,
.about_prompt {
  color: #666;
  font-size: 16px;
  margin-bottom: 45px;
}
.light_head h4 span {
  display: inline-block;
  padding-bottom: 20px;
}
.light_four {
  width: 1024px;
  margin: 0 auto;
}
.light_four li {
  padding: 25px 10px;
  text-align: center;
  cursor: pointer;
  float: left;
  list-style: none;
}
.light_four li img {
  width: 170px;
}
.light_bottom li {
  padding-top: 0;
}
</style>
