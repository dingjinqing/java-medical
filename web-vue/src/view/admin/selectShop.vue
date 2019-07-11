<template>
  <div class="shop_container">
    <div class="main">
      <div class="main_top">
        {{$t('selectShop.allShop')}}
        <img
          :src="imageUrl[0].img_1"
          style="margin-left: 5px;"
        >
      </div>
      <div class="main_content">
        <ul>
          <li
            v-for="(item,index) in shop_list"
            :key="index"
            @mouseenter="enter(index)"
            @mouseleave="leave(index)"
            :class="shop_list_index === index?'shop_li_style':''"
            style="margin-bottom:20px"
            @click="handle_to_shop()"
          >
            <div>
              <div class="shop_state">
              </div>
              <div class="shop_logo">
                <img
                  :src="item.shopAvatar"
                  class="shop_img_default"
                >
              </div>
              <div class="title">{{item.shopName}}</div>
              <p>{{$t('selectShop.data')}}：{{item.created}} ~ {{item.expireTime}}</p>
              <span class="title_type">{{item.shopType}}</span>
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
import { shopListRequest } from '@/api/admin/shopsPages.js'
export default {
  data () {
    return {
      imageUrl: [
        { img_1: this.$imageHost + '/image/admin/expand.png' },
        { img_2: this.$imageHost + '/upload/7467397/image/20181127/crop_u4Diqvh5HbY9QB0b.jpeg' },
        { img_3: this.$imageHost + '/image/admin/ad_img.png' }
      ],
      shop_list: [],
      shop_list_index: ''
    }
  },
  mounted () {
    this.getAllshopsData()
  },
  methods: {
    getAllshopsData () {
      shopListRequest().then((res) => {
        res.content.dataList.map((item, index) => {
          if (item.created) item.created = item.created.split(' ')[0]
          if (item.expireTime) item.expireTime = item.expireTime.split(' ')[0]
          switch (item.shopType) {
            case 'v1':
              item.shopType = '体验版'
              break
            case 'v2':
              item.shopType = '基础版'
              break
            case 'v3':
              item.shopType = '高级版'
              break
            case 'v4':
              item.shopType = '旗舰版'
              break
          }
        })
        this.shop_list = res.content.dataList
        console.log(res)
      })
    },
    // 鼠标划入
    enter (index) {
      this.shop_list_index = index
    },
    // 鼠标划出
    leave (index) {
      this.shop_list_index = ''
    },
    // 店铺点击
    handle_to_shop () {
      this.$router.push({
        name: 'overviewOfMall'
      })
    }
  }

}
</script>
<style scoped>
.shop_container {
  background-color: #fff;
  width: 75%;
  margin: 20px auto;
  padding: 0 100px 30px;
  box-shadow: 0 5px 16px rgba(0, 0, 0, 0.2);
  margin-top: 107px;
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
  cursor: pointer;
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
.shop_li_style {
  border-top: 2px solid #f7931e !important;
  box-shadow: 0 5px 16px rgba(0, 0, 0, 0.2);
}
/* :class="shop_list_index == index?'shop_li_style':''" */
</style>
