<template>
  <div class="shop_container">
    <div class="main">
      <div class="main_top">
        全部店铺
        <img
          :src="imageUrl[0].img_1"
          style="margin-left: 5px;"
        >
      </div>
      <div class="main_content">
        <ul>
          <li>
            <div>
              <div class="shop_state">
              </div>
              <div class="shop_logo">
                <img
                  :src="imageUrl[1].img_2"
                  class="shop_img_default"
                >
              </div>
              <div class="title">旺店通小程序案例</div>
              <p>有效期：2018-04-02 ~ 2020-08-01</p>
              <span class="title_type">旗舰版 </span>
            </div>
          </li>
          <li>
            <div>
              <div class="shop_state">
              </div>
              <div class="shop_logo">
                <img
                  :src="imageUrl[1].img_2"
                  class="shop_img_default"
                >
              </div>
              <div class="title">旺店通小程序案例</div>
              <p>有效期：2018-04-02 ~ 2020-08-01</p>
              <span class="title_type">旗舰版 </span>
            </div>
          </li>
        </ul>
        <div class="mian_ad">
          <div>
            <img :src="imageUrl[2].img_3"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      imageUrl: [
        { img_1: this.$imageHost + '/image/admin/expand.png' },
        { img_2: this.$imageHost + '/upload/7467397/image/20181127/crop_u4Diqvh5HbY9QB0b.jpeg' },
        { img_3: this.$imageHost + '/image/admin/ad_img.png' }
      ]
    }
  },
  methods: {}
}
</script>
<style scoped>
.shop_container {
  background-color: #fff;
  width: 75%;
  margin: 20px auto;
  padding: 0 100px 30px;
  box-shadow: 0 5px 16px rgba(0, 0, 0, 0.2);
}
.main_top {
  padding: 10px 0px;
  font-size: 14px;
}
.main_top img {
  vertical-align: middle;
}
.shop_state {
  height: 40px;
  line-height: 40px;
  color: #999;
  font-size: 14px;
  background: #fff;
}
.shop_logo {
  padding: 20px 0 40px;
}
.shop_img_default {
  width: 60px;
  border-radius: 100%;
}
.main_content ul {
  margin-bottom: 5%;
  overflow: hidden;
}
.main_content ul li {
  margin-bottom: 20px;
  position: relative;
  width: 30%;
  height: 260px;
  border: 1px solid #ccc;
  text-align: center;
  font-size: 18px;
  color: #333;
  margin: 0 1%;
  float: left;
}
.main_content p {
  font-size: 14px;
  color: #666;
  margin: 10px 0px;
}
.title {
  height: 24px;
  line-height: 24px;
}
.title_type {
  background: #457bf9;
  color: #fff;
  font-size: 14px;
  width: 54px;
  height: 20px;
  line-height: 20px;
  text-align: center;
  border-radius: 3px;
  cursor: pointer;
  display: inline-block;
}
.mian_ad {
  text-align: center;
  color: #333;
}
</style>
