<template>
  <div class="shopStyleContent">
    <div class="shopStyleContent_main">
<<<<<<< HEAD
      <div class="color_title">
        <div
          style="padding-left: 20px;"
          :class="en_ch_title_width_one"
        >{{$t('shopStyle.title')}}</div>
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
            v-if="index===6?false:true"
          ></span>
          <span
            class="ps_color"
            :style="item.colorRight"
            :class="item.id===index?'choiseColor':''"
            v-if="index===6?false:true"
          ></span>

          <el-color-picker
            v-model="colorLeft_"
            :predefine="predefineColors"
            v-if="index===6?true:false"
            size='small'
            @change="headleChangeColorLeft()"
          >
          </el-color-picker>
          <el-color-picker
            v-model="colorRight"
            :predefine="predefineColors"
            v-if="index===6?true:false"
            size='small'
            @change="headleChangeColorRight()"
=======
      <div>
        <div class="color_title">
          <div
            style="padding-left: 20px;"
            :class="en_ch_title_width_one"
          >{{$t('shopStyle.title')}}</div>
          <div
            class="shop_color"
            v-for="(item,index) in colorDataList"
            :key="index"
            :class="choiseId===index?'clickColor':''"
            @click="clickColor(index)"
>>>>>>> f22a1c696... 增加店铺风格自定义提示
          >
            <span>{{item.title}}</span>
            <span
              class="ps_color middle_span"
              :style="item.colorLeft"
              :class="item.id===index?'choiseColor':''"
              v-if="index===6?false:true"
            ></span>
            <span
              class="ps_color"
              :style="item.colorRight"
              :class="item.id===index?'choiseColor':''"
              v-if="index===6?false:true"
            ></span>

            <el-color-picker
              v-model="colorLeft_"
              show-alpha
              :predefine="predefineColors"
              v-if="index===6?true:false"
              size='small'
              @change="headleChangeColorLeft()"
            >
            </el-color-picker>
            <el-color-picker
              v-model="colorRight"
              show-alpha
              :predefine="predefineColors"
              v-if="index===6?true:false"
              size='small'
              @change="headleChangeColorRight()"
            >
            </el-color-picker>
          </div>
        </div>

        <div class="tips">
          提示：自定义风格不要设置白色
        </div>
      </div>

      <div class="color_content">
        <div
          style="padding-left: 22px"
          :class="en_ch_title_width_two"
        >{{$t('shopStyle.exampleTitle')}}</div>
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
          >{{$t('shopStyle.left_title_1')}}</div>
          <div
            class="couponLeft c_right"
            :style="colorLeft"
          >{{$t('shopStyle.left_title_2')}}</div>
          <div
            class="addCatClass"
            :style="btnLeft_background"
          >{{$t('shopStyle.addCarText')}}</div>
          <div
            class="addCatClass buyClass"
            :style="btnRight_background"
          >{{$t('shopStyle.buyText')}}</div>
        </div>
        <div class="shop_img">
          <div
            class="middle_top_count"
            :style="colorLeft_color"
          >￥960.00</div>
          <div
            class="middle_color"
            :style="middleLeft"
          >{{$t('shopStyle.middle_Text_1')}}</div>
          <div
            class="middle_size"
            :style="middleLeft"
          >s</div>
          <div
            class="middle_addCar"
            :style="btnLeft_background"
          >{{$t('shopStyle.addCarText')}}</div>
          <div
            class="middle_buy"
            :style="btnRight_background"
          >{{$t('shopStyle.buyText')}}</div>
        </div>
        <div class="shop_img">
          <div
            class="right_time"
            :style="colorLeft_color"
          >{{$t('shopStyle.right_time')}}</div>
          <div
            class="right_kd"
            :style="rightBorder"
          >{{$t('shopStyle.right_kd')}}</div>
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
          >{{$t('shopStyle.right_count_1')}}</div>
          <div
            class="bottom_left_count"
            :style="colorLeft_color"
          >{{$t('shopStyle.right_count_2')}}</div>
          <div
            class="bottom_submit"
            :style="btnRight_background"
          >{{$t('shopStyle.right_submit')}}</div>
        </div>
      </div>

    </div>
    <div class="footer">
      <div
        class="save"
        @click="saveShopStyle()"
      >{{$t('shopStyle.saveText')}}</div>
    </div>
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
import { shopStyleGetRequest, shopStyleModifyRequest } from '@/api/admin/shopStyle.js'
Vue.use(vcolorpicker)
export default {
  data () {
    return {
      colorLeft_: '',
      colorRight: '',
      colorDataList: [],
      choiseId: '',
      colorLeft_color: 'color: rgba(255, 102, 102,1)',
      colorLeft: 'background:rgba(255, 102, 102, 0.2);color: rgba(255, 102, 102,1);border: 1px solid rgba(255, 102, 102, 0.4)',
      middleLeft: 'color: rgba(255, 102, 102,1);border: 1px solid rgba(255, 102, 102, 0.4)',
      rightBorder: 'border-bottom: 1px solid rgba(255, 102, 102, 0.4); color: rgba(255, 102, 102,1)',
      btnLeft_background: 'background: rgba(255, 209, 209,1)',
      btnRight_background: 'background: rgba(255, 102, 102,1)',
      // 自定义颜色
      custom_colorLeft_color: '',
      custom_colorLeft: '',
      custom_middleLeft: '',
      custom_rightBorder: '',
      custom_btnLeft_background: '',
      custom_btnRight_background: '',
      defaultColorleft: '',
      defaultColorright: '',
      en_ch_title_width_one: '',
      en_ch_title_width_two: '',
      predefineColors: [ // 颜色选择器预定义颜色池
        'rgba(255,69,0,1)',
        'rgba(255,140,0,1)',
        'rgba(255,215,0,1)',
        'rgba(144,238,144,1)',
        'rgba(0,206,209,1)',
        'rgba(30,144,255,1)',
        'rgba(199,21,133,1)',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0,1)'
      ]
    }
  },
  watch: {
    lang () {
      this.colorDataList = this.$t('shopStyle.topTitleList')
    },
    btnRight_background (newData) {
      console.log(newData)
      if (newData) {
        let color = newData.split(':')[1]
        console.log(color)
        localStorage.setItem('V-backgroundColor', color)
      }
    }
  },
  mounted () {
    // 初始化颜色查询
    this.getShopStyle()
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 初始化颜色查询
    getShopStyle () {
      shopStyleGetRequest().then((res) => {
        console.log(res)
        if (res.content && res.content.shopStyleId) {
          this.choiseId = res.content.shopStyleId
          if (res.content && res.content.shopStyleId === 6) {
            this.choiseId = res.content.shopStyleId
            let leftColor = res.content.shopStyleValue.split(',')[0] + ',' + res.content.shopStyleValue.split(',')[1] + ',' + res.content.shopStyleValue.split(',')[2] + ',' + res.content.shopStyleValue.split(',')[3]
            let RightColor = res.content.shopStyleValue.split(',')[4] + ',' + res.content.shopStyleValue.split(',')[5] + ',' + res.content.shopStyleValue.split(',')[6] + ',' + res.content.shopStyleValue.split(',')[7]
            this.colorLeft_ = leftColor
            this.colorRight = RightColor
            console.log(leftColor, RightColor)
            // 配色选项初始化
            this.defaultColorleft = leftColor
            this.defaultColorright = RightColor
            this.colorLeft_color = 'color:' + leftColor
            this.colorLeft = 'background:' + this.ToRgba(leftColor, 0.2) + ';color:' + leftColor + ';border:1px solid ' + this.ToRgba(leftColor, 0.4)
            this.middleLeft = 'color:' + leftColor + ';border: 1px solid' + this.ToRgba(leftColor, 0.4)
            this.rightBorder = 'border-bottom: 1px solid ' + this.ToRgba(leftColor, 0.4) + '; color:' + leftColor
            console.log(this.rightBorder)
            this.btnRight_background = 'background:' + leftColor
            this.btnLeft_background = 'background:' + RightColor
            // 自定义颜色存储
            this.custom_colorLeft_color = this.colorLeft_color
            this.custom_colorLeft = this.colorLeft
            this.custom_middleLeft = this.middleLeft
            this.custom_rightBorder = this.rightBorder
            this.custom_btnLeft_background = this.btnLeft_background
            this.custom_btnRight_background = this.btnRight_background
          } else {
            this.changeColor(res.content.shopStyleId)
          }
        }
        console.log(res.content.shopStyleId)
        this.clickColor(res.content.shopStyleId)
        console.log(res.content)
      })
    },
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
          this.colorLeft_color = this.custom_colorLeft_color
          this.colorLeft = this.custom_colorLeft
          this.middleLeft = this.custom_middleLeft
          this.btnLeft_background = this.custom_btnLeft_background
          this.btnRight_background = this.custom_btnRight_background
          this.rightBorder = this.custom_rightBorder
          break
      }
    },
    // 自定义颜色改Left
    headleChangeColorLeft () {
      console.log(this.colorLeft_)
      console.log(this.colorLeft_color)
      this.ToRgba(this.colorLeft_, 0.2)
      this.colorLeft_color = 'color:' + this.colorLeft_
      this.colorLeft = 'background:' + this.ToRgba(this.colorLeft_, 0.2) + ';color:' + this.colorLeft_ + ';border:1px solid ' + this.ToRgba(this.colorLeft_, 0.4)
      this.middleLeft = 'color:' + this.colorLeft_ + ';border: 1px solid' + this.ToRgba(this.colorLeft_, 0.4)
      this.rightBorder = 'border-bottom: 1px solid ' + this.ToRgba(this.colorLeft_, 0.4) + '; color:' + this.colorLeft_
      this.btnRight_background = 'background:' + this.colorLeft_
      // 自定义颜色存储
      this.custom_colorLeft_color = this.colorLeft_color
      this.custom_colorLeft = this.colorLeft
      this.custom_middleLeft = this.middleLeft
      this.custom_rightBorder = this.rightBorder

      this.custom_btnRight_background = this.btnRight_background

      this.choiseId = 6
    },
    // 自定义颜色改Right
    headleChangeColorRight () {
      console.log(this.colorRight)
      this.btnLeft_background = 'background:' + this.colorRight
      this.custom_btnLeft_background = this.btnLeft_background
      this.choiseId = 6
    },
    // 16进制转换rgba
    ToRgba (str, n) {
      // 十六进制颜色值的正则表达式
      var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/
      var sColor = str.toLowerCase()
      // 十六进制颜色转换为RGB格式
      if (sColor && reg.test(sColor)) {
        if (sColor.length === 4) {
          var sColorNew = '#'
          for (let i = 1; i < 4; i += 1) { // 例如：#eee,#fff等
            sColorNew += sColor.slice(i, i + 1).concat(sColor.slice(i, i + 1))
          }
          sColor = sColorNew
        }
        // 处理六位颜色值
        var sColorChange = []
        for (let i = 1; i < 7; i += 2) {
          sColorChange.push(parseInt('0x' + sColor.slice(i, i + 2)))
        }
        return 'rgba(' + sColorChange.join(',') + ',' + n + ')'
      } else {
        return sColor
      }
    },
    // 保存
    saveShopStyle () {
      let saveLeftColor = this.btnLeft_background.split(':')[1]
      let saveRightColor = this.btnRight_background.split(':')[1]
      let obj = {
        'shopStyleId': this.choiseId,
        'shopStyleValue': saveLeftColor + ',' + saveRightColor
      }
      console.log(obj)
      shopStyleModifyRequest(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success({
            message: '保存成功',
            type: 'success',
            showClose: true
          })
        }
        console.log(res)
      }).catch(() => {
        this.$message.error('保存失败')
      })
    }
  }
}
</script>
<style scoped>
.shopStyleContent {
  padding: 10px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 650px;
  position: relative;
}
.shopStyleContent_main {
  height: 100%;
  background-color: #fff;
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
  position: relative;
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
  margin-bottom: 50px;
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
.en_ch_title_width_one {
  width: 150px !important;
}
.en_ch_title_width_two {
  width: 235px !important;
}
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: fixed;
  z-index: 2;
  bottom: 0;
  padding: 10px 0;
  left: 0;
  right: 0;
  margin-right: 10px;
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
/* .rightColor {
  right: 235px !important;
} */
</style>
<style>
.shopStyleContent .m-colorPicker {
  position: absolute !important;
  right: 36px;
  top: 6px;
  width: 24px !important;
  height: 24px !important;
}
.shopStyleContent .colorBtn {
  /* width: 100% !important;
  height: 100% !important;
  opacity: 0; */
  width: 24px !important;
  height: 24px !important;
  z-index: 10;
  border: 1px dashed #cccccc;
}
.shopStyleContent .box.open {
  z-index: 1000;
}
</style>
<style lang="scss" scoped>
/deep/ .el-color-picker {
  width: 34px !important;
}
.tips {
  width: 1120px;
  height: 27px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  color: #999;
}
</style>
