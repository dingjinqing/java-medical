<template>
  <div class="shopStyleContent">
    <div class="shopStyleContent_main">
      <div class="color_title">
        <div style="padding-left: 20px;">店铺配色方案：</div>
        <div
          class="shop_color"
          v-for="(item,index) in colorDataList"
          :key="index"
          :class="choiseId===index?'clickColor':''"
          @click="clickColor(index)"
        >
          <span>{{item.title}}</span>
          <span
            class="ps_color middle_span"
            :style="item.colorLeft"
            :class="item.id===index?'choiseColor':''"
          ></span>
          <span
            class="ps_color"
            :style="item.colorRight"
            :class="item.id===index?'choiseColor':''"
          ></span>
        </div>
      </div>
      <div class="color_content">
        <div style="padding-left: 22px">当前配色方案示例：</div>
        <div class="shop_img">
          <div
            class="starClass fa fa-star"
            :style="colorLeft_color"
          ></div>
          <div
            class="leftCountClss"
            :style="colorLeft_color"
          ><span>￥</span>1399</div>
          <div
            class="couponLeft"
            :style="colorLeft"
          >满200减10</div>
          <div
            class="couponLeft c_right"
            :style="colorLeft"
          >满300减20</div>
          <div
            class="addCatClass"
            :style="btnLeft_background"
          >加入购物车</div>
          <div
            class="addCatClass buyClass"
            :style="btnRight_background"
          >立即购买</div>
        </div>
        <div class="shop_img">
          <div
            class="middle_top_count"
            :style="colorLeft_color"
          >￥960.00</div>
          <div
            class="middle_color"
            :style="middleLeft"
          >黑色</div>
          <div
            class="middle_size"
            :style="middleLeft"
          >s</div>
          <div
            class="middle_addCar"
            :style="btnLeft_background"
          >加入购物车</div>
          <div
            class="middle_buy"
            :style="btnRight_background"
          >立即购买</div>
        </div>
        <div class="shop_img">
          <div
            class="right_time"
            :style="colorLeft_color"
          >0天0时29分35</div>
          <div
            class="right_kd"
            :style="rightBorder"
          >快递</div>
          <div
            class="right_count"
            :style="colorLeft_color"
          >￥98.00</div>
          <div
            class="right_d fa fa-check-square"
            :style="colorLeft_color"
          ></div>
          <div
            class="bottom_count"
            :style="colorLeft_color"
          >减10元</div>
          <div
            class="bottom_left_count"
            :style="colorLeft_color"
          >应付总额：0元</div>
          <div
            class="bottom_submit"
            :style="btnRight_background"
          >提交订单</div>
        </div>
      </div>
      <div class="footer">
        <div class="save">保存</div>
      </div>
    </div>

  </div>
</template>
<script>
export default {
  data () {
    return {
      colorDataList: [
        { title: '配色1', colorLeft: 'background: #ff6666;', colorRight: 'background: #fee7e7', id: '', choiseId: '' },
        { title: '配色2', colorLeft: 'background: #e53e24;', colorRight: 'background: #f2ad3c', id: '', choiseId: '' },
        { title: '配色3', colorLeft: 'background: #7e56c5;', colorRight: 'background: #333333', id: '', choiseId: '' },
        { title: '配色4', colorLeft: 'background: #09bb07;', colorRight: 'background: #333333', id: '', choiseId: '' },
        { title: '配色5', colorLeft: 'background: #4a90e2;', colorRight: 'background: #dbe9f9', id: '', choiseId: '' },
        { title: '配色6', colorLeft: 'background: #feb609;', colorRight: 'background: #333333', id: '', choiseId: '' },
        { title: '自定义', colorLeft: 'background: #fff;', colorRight: 'background: #fff', id: 6, choiseId: '' }
      ],
      choiseId: '',
      colorLeft_color: 'color: rgb(255, 102, 102)',
      colorLeft: 'background:rgba(255, 102, 102, 0.2);color: rgb(255, 102, 102);border: 1px solid rgba(255, 102, 102, 0.4)',
      middleLeft: 'color: rgb(255, 102, 102);border: 1px solid rgba(255, 102, 102, 0.4)',
      rightBorder: 'border-bottom: 1px solid rgba(255, 102, 102, 0.4); color: rgb(255, 102, 102)',
      btnLeft_background: 'background: rgb(255, 209, 209)',
      btnRight_background: 'background: rgb(255, 102, 102)'
    }
  },
  methods: {
    // 选择配色方案
    clickColor (index) {
      this.choiseId = index
      this.changeColor(index)
    },
    // 改变颜色
    changeColor (index) {
      console.log(index)
      switch (index) {
        case 0:
          this.colorLeft_color = 'color: rgb(255, 102, 102)'
          this.colorLeft = 'background:rgba(255, 102, 102, 0.2);color: rgb(255, 102, 102);border: 1px solid rgba(255, 102, 102, 0.4)'
          this.middleLeft = 'color: rgb(255, 102, 102);border: 1px solid rgba(255, 102, 102, 0.4)'
          this.btnLeft_background = 'background: rgb(255, 209, 209)'
          this.btnRight_background = 'background: rgb(255, 102, 102)'
          this.rightBorder = 'border-bottom: 1px solid rgba(255, 102, 102, 0.4); color: rgb(255, 102, 102)'
          break
        case 1:
          this.colorLeft_color = 'color: rgb(229, 62, 36)'
          this.colorLeft = 'background:rgba(229, 62, 36, 0.2);color: rgb(229, 62, 36);border: 1px solid rgba(229, 62, 36, 0.4)'
          this.middleLeft = 'color: rgb(229, 62, 36);border: 1px solid rgba(229, 62, 36, 0.4)'
          this.btnLeft_background = 'background: rgb(242, 173, 60)'
          this.btnRight_background = 'background: rgb(229, 62, 36)'
          this.rightBorder = 'border-bottom: 1px solid rgba(229, 62, 36, 0.4); color: rgb(229, 62, 36)'
          break
        case 2:
          this.colorLeft_color = 'color: rgb(126, 86, 197)'
          this.colorLeft = 'background:rgba(126, 86, 197, 0.2);color: rgb(126, 86, 197);border: 1px solid rgba(126, 86, 197, 0.4)'
          this.middleLeft = 'color: rgb(126, 86, 197);border: 1px solid rgba(126, 86, 197, 0.4)'
          this.btnLeft_background = 'background: rgb(51, 51, 51)'
          this.btnRight_background = 'background: rgb(126, 86, 197)'
          this.rightBorder = 'border-bottom: 1px solid rgba(126, 86, 197, 0.4); color: rgb(126, 86, 197)'
          break
        case 3:
          this.colorLeft_color = 'color: rgb(9, 187, 7)'
          this.colorLeft = 'background:rgba(9, 187, 7, 0.2);color: rgb(9, 187, 7);border: 1px solid rgba(9, 187, 7, 0.4)'
          this.middleLeft = 'color: rgb(9, 187, 7);border: 1px solid rgba(9, 187, 7, 0.4)'
          this.btnLeft_background = 'background: rgb(51, 51, 51)'
          this.btnRight_background = 'background: rgb(9, 187, 7)'
          this.rightBorder = 'border-bottom: 1px solid rgba(9, 187, 7, 0.4); color: rgb(9, 187, 7)'
          break
        case 4:
          this.colorLeft_color = 'color: rgb(74, 144, 226)'
          this.colorLeft = 'background:rgba(74, 144, 226, 0.2);color: rgb(74, 144, 226);border: 1px solid rgba(74, 144, 226, 0.4)'
          this.middleLeft = 'color: rgb(74, 144, 226);border: 1px solid rgba(74, 144, 226, 0.4)'
          this.btnLeft_background = 'background: rgb(183, 211, 243)'
          this.btnRight_background = 'background: rgb(74, 144, 226)'
          this.rightBorder = 'border-bottom: 1px solid rgba(74, 144, 226, 0.4); color: rgb(74, 144, 226)'
          break
        case 5:
          this.colorLeft_color = 'color: rgb(254, 182, 9)'
          this.colorLeft = 'background:rgba(254, 182, 9, 0.2);color: rgb(254, 182, 9);border: 1px solid rgba(254, 182, 9, 0.4)'
          this.middleLeft = 'color: rgb(254, 182, 9);border: 1px solid rgba(254, 182, 9, 0.4)'
          this.btnLeft_background = 'background: rgb(51, 51, 51)'
          this.btnRight_background = 'background: rgb(254, 182, 9)'
          this.rightBorder = 'border-bottom: 1px solid rgba(254, 182, 9, 0.4); color: rgb(254, 182, 9)'
          break
        case 6:
          this.colorLeft_color = 'color: rgb(255, 102, 102)'
          this.colorLeft = 'background:rgba(255, 102, 102, 0.2);color: rgb(255, 102, 102);border: 1px solid rgba(255, 102, 102, 0.4)'
          this.middleLeft = 'color: rgb(255, 102, 102);border: 1px solid rgba(255, 102, 102, 0.4)'
          this.btnLeft_background = 'background: rgb(255, 209, 209)'
          this.btnRight_background = 'background: rgb(255, 102, 102)'
          this.rightBorder = 'border-bottom: 1px solid rgba(255, 102, 102, 0.4); color: rgb(255, 102, 102)'
          break
      }
    }
  }
}
</script>
<style scoped>
.shopStyleContent {
  padding: 10px;
  min-width: 1400px;
  font-size: 14px;
  height: 100%;
}
.shopStyleContent_main {
  position: relative;
  background-color: #fff;
  height: 100%;
}
.color_title {
  width: 1120px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}
.color_title div {
  width: 120px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.color_title span {
  display: block;
}
.shop_color {
  background: #f5f5f5;
  color: #666;
  border-radius: 4px;
  cursor: pointer;
}
.ps_color {
  width: 24px;
  height: 24px;
}
.middle_span {
  margin-left: 4px;
}
.choiseColor {
  border: 1px dashed #cccccc;
}
.clickColor {
  background: #fff;
  border: 1px solid #5a8bff;
}
.color_content {
  width: 1005px;
  display: flex;
  justify-content: space-around;
  margin-top: 10px;
}
.shop_img {
  width: 265px;
  height: 463px;
  margin-top: 5px;
  position: relative;
}

.color_content > div:nth-of-type(2) {
  background-image: url(../../../../../../assets/adminImg/shop_style11.png);
}
.color_content > div:nth-of-type(3) {
  background-image: url(../../../../../../assets/adminImg/shop_style22.png);
}
.color_content > div:nth-of-type(4) {
  background-image: url(../../../../../../assets/adminImg/shop_style33.png);
}
.starClass {
  position: absolute;
  right: 20px;
  top: 301px;
  font-size: 14px;
  /* color: rgb(255, 102, 102); */
}
.leftCountClss {
  position: absolute;
  left: 10px;
  top: 345px;
  font-size: 16px;
  /* color: rgb(255, 102, 102); */
  font-family: Helvetica Neue, Helvetica, PingFang SC, \5fae\8f6f\96c5\9ed1,
    Tahoma, Arial, sans-serif;
}
.leftCountClss span {
  font-size: 12px;
}
.couponLeft {
  /* color: rgb(255, 102, 102); */
  /* background: rgba(255, 102, 102, 0.2);
   */
  /* border: 1px solid rgba(255, 102, 102, 0.4); */
  position: absolute;
  left: 40px;
  bottom: 46px;
  font-size: 10px !important;
  width: 70px;
  padding: 1px;
  text-align: center;
  border-radius: 1px;
  line-height: 16px;
  transform: scale(0.7);
}
.c_right {
  left: 94px !important;
}
.addCatClass {
  /* background: rgb(255, 209, 209); */
  position: absolute;
  bottom: 9px;
  left: 61px;
  width: 131px;
  height: 45px;
  line-height: 45px;
  font-size: 14px;
  text-align: center;
  color: white;
  transform: scale(0.65);
}
.buyClass {
  left: 146px !important;
  /* background-color: rgb(255, 102, 102); */
}
.middle_top_count {
  position: absolute;
  font-size: 18px;
  top: 203px;
  left: 85px;
}
.middle_color {
  position: absolute;
  left: 65px;
  top: 278px;
  width: 55px;
  font-size: 10px;
  height: 23px;
  line-height: 23px;
  /* color: rgb(126, 86, 197); */
  /* border: 1px solid rgb(126, 86, 197); */
  transform: scale(0.7);
  text-align: center;
}
.middle_size {
  position: absolute;
  left: 10px;
  bottom: 108px;
  width: 55px;
  font-size: 18px;
  height: 23px;
  line-height: 23px;
  /* color: rgb(126, 86, 197); */
  /* border: 1px solid rgb(126, 86, 197); */
  transform: scale(0.7);
  text-align: center;
}
.middle_addCar {
  /* background: rgb(51, 51, 51); */
  color: #fff;
  position: absolute;
  bottom: 14px;
  left: -8px;
  width: 156px;
  height: 36px;
  line-height: 36px;
  text-align: center;
  font-size: 10px;
  transform: scale(0.8);
}
.middle_buy {
  /* background: red; */
  color: #fff;
  position: absolute;
  bottom: 14px;
  left: 115px;
  width: 156px;
  height: 36px;
  line-height: 36px;
  text-align: center;
  font-size: 10px;
  transform: scale(0.8);
}
.right_time {
  position: absolute;
  top: 53px;
  left: 64px;
  font-size: 10px;
  transform: scale(0.7);
  /* color: rgb(126, 86, 197); */
}
.right_kd {
  position: absolute;
  top: 66px;
  left: 9px;
  transform: scale(0.8);
  width: 140px;
  text-align: center;
  height: 40px;
  line-height: 40px;
  /* color: rgb(126, 86, 197);
  border-bottom: 1px solid rgb(126, 86, 197); */
}
.right_count {
  position: absolute;
  top: 218px;
  left: 68px;
  transform: scale(0.8);
}
.right_d {
  position: absolute;
  top: 283px;
  left: 16px;
  font-size: 13px;
}
.bottom_count {
  position: absolute;
  bottom: 70px;
  right: 11px;
  font-size: 10px;
  transform: scale(0.8);
}
.bottom_left_count {
  position: absolute;
  bottom: 22px;
  left: 112px;
  font-size: 10px;
  transform: scale(0.8);
}
.bottom_submit {
  position: absolute;
  bottom: 12px;
  right: -2px;
  font-size: 10px;
  transform: scale(0.8);
  width: 88px;
  height: 40px;
  line-height: 40px;
  /* background-color: red; */
  text-align: center;
  color: #fff;
}
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: absolute;
  z-index: 2;
  bottom: 45px;
  width: 100%;
}
.save {
  width: 70px;
  height: 30px;
  line-height: 30px;
  border: none;
  background: #5a8bff;
  color: #fff;
  margin: auto;
  cursor: pointer;
}
</style>
