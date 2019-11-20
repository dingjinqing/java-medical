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
            @click="handle_to_shop(item)"
          >
            <div>
              <div
                class="shop_state"
                :class="item.bgColor?'noUse':''"
                :style="item.tipStatus===1||item.tipStatus===2?'color:#f7931e':''"
              >
                <img :src="item.imgUrl">
                <span v-if="item.tipStatus===1">{{$t('selectShop.prohibited')}}</span>
                <span v-if="item.tipStatus===2">{{$t('selectShop.notopen')}}</span>
                <span v-if="item.tipStatus===3">{{$t('selectShop.expired')}}</span>
              </div>
              <div class="shop_logo">
                <img
                  :src="item.shopAvatar===null?imageUrl[3].img_def:item.shopAvatar"
                  class="shop_img_default"
                >
              </div>
              <div class="title">{{item.shopName}}</div>
              <p>{{$t('selectShop.data')}}：{{item.created}} ~ {{item.expireTime}}</p>
              <span class="title_type">
                <span
                  class="shopType"
                  v-if="item.shopType==='v1'"
                >{{$t('selectShop.trial')}}</span>
                <span
                  class="shopType"
                  v-if="item.shopType==='v2'"
                >{{$t('selectShop.basic')}}</span>
                <span
                  class="shopType"
                  v-if="item.shopType==='v3'"
                >{{$t('selectShop.advanced')}}</span>
                <span
                  class="shopType"
                  v-if="item.shopType==='v4'"
                >{{$t('selectShop.ultimate')}}</span>
              </span>
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
import { shopListRequest, changeShopRequest } from '@/api/admin/shopsPages.js'
export default {
  data () {
    return {
      imageUrl: [
        { img_1: this.$imageHost + '/image/admin/expand.png' },
        { img_2: this.$imageHost + '/upload/7467397/image/20181127/crop_u4Diqvh5HbY9QB0b.jpeg' },
        { img_3: this.$imageHost + '/image/admin/ad_img.png' },
        { img_def: this.$imageHost + '/image/admin/shop_logo_default.png' }
      ],
      shop_list: [],
      shop_list_index: '',
      fullscreenLoading: false,
      loading: '',
      shopData: null
    }
  },
  created () {
    this.loading = this.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  },
  mounted () {
    console.log('ceshi1')
    // 初始化语言
    this.langDefault()
    console.log('ceshi2')
    this.getAllshopsData()
  },
  methods: {
    getAllshopsData () {
      console.log('ceshi')
      shopListRequest().then((res) => {
        console.log(res)
        if (!res) {
          this.loading.close()
          return
        }
        this.shopData = res.content.dataList
        this.shopData.map((item, index) => {
          if (item.created) item.created = item.created.split(' ')[0]
          if (item.expireTime) item.expireTime = item.expireTime.split(' ')[0]
          if (item.isEnabled) {
            item.tipStatus = 1
            item.imgUrl = this.$imageHost + '/image/admin/no_use.png'
            item.bgColor = true
          } else if (!item.businessState) {
            item.tipStatus = 2
            item.imgUrl = this.$imageHost + '/image/admin/no_business.png'
            item.bgColor = true
          } else if (item.expireTimeStatus === '1') {
            item.tipStatus = 3
            item.imgUrl = this.$imageHost + '/image/admin/no_time.png'
            item.bgColor = true
          } else {
            item.tipStatus = 0
            item.bgColor = false
          }
          console.log(res.content.dataList)
        })
        this.shop_list = res.content.dataList
        this.loading.close()
      })
        .catch((err) => {
          this.loading.close()
          console.log(err)
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
    handle_to_shop (data) {
      let obj = {
        shopId: data.shopId
      }
      console.log(data)
      localStorage.setItem('V-Currency', 'CNY')
      localStorage.setItem('V-ShopId', data.shopId)
      changeShopRequest(obj).then((res) => {
        console.log(res)
        const { error, message } = res

        if (error === 0) {
          this.$router.push({
            name: 'shop_view'
          })
        } else {
          this.$message({
            showClose: true,
            message: message,
            type: 'error'
          })
        }
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
.main {
  min-width: 100%;
}
.main_top {
  padding: 10px 0px;
  font-size: 14px;
}
.main_top img {
  vertical-align: middle;
}
.shop_state.shop_state {
  height: 40px;
  line-height: 40px;
  color: #999;
  font-size: 14px;
  background: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}
.shop_state img {
  margin-right: 5px;
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
.shop_container .noUse {
  background-color: #fef4e8;
}
.shopType {
  padding: 5px;
}
/* :class="shop_list_index == index?'shop_li_style':''" */
</style>
