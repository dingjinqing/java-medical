<template>
  <div class="addPayRewardAct">
    <div class="addPayRewardAct_main">

      <!-- 左侧内容区域 -->
      <div class="leftContent">
        <!-- 标题图片部分 -->
        <div class="title">
          <img
            :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'"
            alt=""
          >
        </div>
        <div class="carousel">
          <el-carousel
            trigger="click"
            height="573px"
            arrow="never"
          >
            <el-carousel-item
              v-for="(item,index) in carouselList"
              :key="index"
            >
              <img
                :src="item.src"
                alt=""
                style="height: 100%; width: 100%;"
              >
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="rightContent"></div>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      carouselList: [
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift1.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift2.jpg' },
        { src: 'http://mpdevimg2.weipubao.cn/image/admin/pay_gift3.jpg' }
      ]
    }
  }
}

</script>
<style lang="scss" scoped>
.addPayRewardAct {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  font-size: 14px;
  .addPayRewardAct_main {
    position: relative;
    display: flex;
    justify-content: center;
    background: #fff;
    padding: 40px 0 96px;
    .leftContent {
      width: 323px;
      min-height: 630px;
      border: 1px solid #ccc;
      background: #f5f5f5;
      .title {
        height: 55px;
        img {
          width: 323px;
        }
      }
      .carousel {
        width: 323px;
        height: 55px;
      }
    }
    .rightContent {
      margin-left: 20px;
    }
  }
}
</style>
