<template>
  <div class="footer">
    <ul class="footer_ul clearfix" style="display:flex;justify-content:center">
      <!-- <li>
        <img :src="imageUrlData[0].img_1">
        <p>{{$t('footer.title')}}</p>
      </li> -->
      <li style="float:none;width:35%">
        <span>{{$t('footer.link')}}</span>
        <p>TEL：400-010-1039&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{$t('footer.server')}}</p>
        <p>QQ：3003715029</p>
        <br>
        <span>{{$t('footer.adress')}}</span>
        <p>{{$t('footer.detail_adress')}}</p>
      </li>
      <!-- <li>
        <img
          :src="imageUrlData[1].img_2"
          width="100px"
        >
        <p>{{$t('footer.content')}}</p>
      </li> -->
    </ul>
    <!-- <div class="friend_link">
      {{$t('footer.footer_con_1')}}：
      <a
        href="http://www.wangdian.cn/"
        target="_blank"
      >{{$t('footer.footer_con_2')}}</a>
      |
      <a
        href="http://www.vpubao.com.cn/index.php?c=store&amp;m=b2c"
        target="_blank"
      >{{$t('footer.footer_con_3')}}</a>
      |
      <a
        href="http://www.vpubao.com.cn/index.php?c=store&amp;m=b2b2c"
        target="_blank"
      >{{$t('footer.footer_con_4')}}</a>
      |
      <a
        href="http://pos.vpubao-mall.com/"
        target="_blank"
      >{{$t('footer.footer_con_5')}}</a>
      |
      <a
        href="http://eshop002.vpubao.com.cn/index.php?c=main"
        target="_blank"
      >{{$t('footer.footer_con_6')}}</a>
      |
      <a
        href="http://www.wangdian.cn/pc/erp.html"
        target="_blank"
      >ERP</a>

      |
      <a
        href="http://www.wangdian.cn/pc/wms.html"
        target="_blank"
      >WMS</a>
      |
      <a
        href="http://www.wangdian.cn/pc/o2o.html"
        target="_blank"
      >O2O</a>
      |
      <a
        href="http://www.wangdian.cn/pc/data.html"
        target="_blank"
      >{{$t('footer.footer_con_7')}}</a>
    </div> -->
    <div class="footer_div">
      {{$t('footer.footer_con_8')}} &nbsp;
      {{$t('footer.footer_con_9')}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      {{$t('footer.footer_con_10')}}-1
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      imageUrlData: [
        {
          img_1: this.$imageHost + '/image/admin/official/wdt_logo.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/official/weixin.jpg'
        }
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
.footer {
  background: #27282c;
  color: #68696d;
  padding: 10px 100px 30px;
}
.footer_ul {
  padding: 40px 0;
  width: 900px;
  margin: 0 auto;
  overflow: hidden;
}
.footer_ul li:nth-of-type(1) {
  text-align: left;
}
.footer_ul li:nth-of-type(2) {
  text-align: left;
  width: 40%;
  border-right: 1px solid #3d3e41;
  border-left: 1px solid #3d3e41;
  padding-left: 23px;
  box-sizing: border-box;
}
.footer_ul li:nth-of-type(3) {
  padding-left: 30px;
  box-sizing: border-box;
}
.footer_ul li {
  width: 30%;
  color: #666;
  text-align: center;
  float: left;
}
.footer_ul li img {
  margin-bottom: 10px;
}
.footer_ul li p {
  margin: 5px 0 0;
}
.friend_link {
  border-top: 1px solid #3d3e41;
  padding: 30px 0px 10px;
  width: 900px;
  margin: 0 auto;
  text-align: center;
  padding-bottom: 20px;
  color: #666;
}
.friend_link a {
  color: #666;
  text-decoration: none;
}
.footer_div {
  width: 900px;
  margin: 0 auto;
  text-align: center;
  padding-bottom: 20px;
  color: #666;
}
</style>
