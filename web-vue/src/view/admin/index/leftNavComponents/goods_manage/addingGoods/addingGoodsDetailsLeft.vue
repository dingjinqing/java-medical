<template>
  <div class="addingGoodsDetailsLeft">
    <section class="wrap">
      <section class="top">
        <span class="title">商品详情</span>
      </section>
      <section class="con">
        <section class="img">
          <el-image
            :src="src"
            class="img_con"
          >
            <div
              slot="placeholder"
              class="image-slot"
            >
              加载中<span class="dot">...</span>
            </div>
          </el-image>
        </section>
        <section class="proName">{{goodsName}}</section>
        <section class="proPri">¥ {{prdPrice}}</section>
        <section class="proNum">库存：{{prdNumber}}</section>
      </section>
    </section>

  </div>
</template>
<script>
/* eslint-disable */
export default {
  name: 'addingGoodsDetailsLeft',
  props: ['getParams'],
  created() {

  },
  data() {
    return {
      src: this.getParams.goodsImg,
      goodsName: this.getParams.goodsName,
      prdPrice: this.getParams.prdPrice,
      prdNumber: this.getParams.prdNumber
    }
  },
  methods: {

  }
}
</script>
<style scoped>
.wrap {
  border: 1px solid #e5e5e5;
  width: 386px;
  height: 616px;
  padding: 5px;
}
.top {
  width: 375px;
  height: 64px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: url(../../../../../../assets/image/admin/new_shop_beautify/page_name.png)
    no-repeat;
}
.title {
  display: inline-block;
  margin-top: 20px;
  font-size: 16px;
  color: #000;
  font-weight: 800;
  box-sizing: border-box;
}
.img {
  width: 380px;
  height: 380px;
}
.img_con {
  width: 100%;
  height: 100%;
}
.proName {
  color: #333333;
}
.proPri {
  color: #ff6666;
  font-size: 18px;
}
.proNum {
  color: #a2a2a2;
}
</style>
