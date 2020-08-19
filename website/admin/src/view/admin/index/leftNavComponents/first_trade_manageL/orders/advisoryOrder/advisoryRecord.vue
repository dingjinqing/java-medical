<template>
  <div class="main-container">
    <div class="content">
      <div class="wrap">
        <div
          class="title"
          :style="'background:url('+ $imageHost +'/image/admin/shop_beautify/phone_tops.png) 100%/100% no-repeat;'"
        ></div>
        <div class="wrap-content">
          <template v-for="(item,index) in recordList">
            <div
              class="user_con"
              :class="{'con_left':!item.doctor,'con_right':item.doctor}"
              :key="index"
            >
              <div
                class="user_icon"
                :class="{'user_left':!item.doctor,'user_right':item.doctor}"
              >
                <img
                  :src="!item.doctor ? $imageHost + '/image/wxapp/user_default_icon.png' : $imageHost + '/image/wxapp/doctor_default_icon.png'"
                  alt=""
                >
              </div>
              <div
                class="origin_message"
                :class="{'origin_left':!item.doctor,'origin_right':item.doctor}"
                v-if="item.type === 0"
              >
                {{JSON.parse(item.message).content}}
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getRecordList } from '@/api/admin/orderManage/order.js'
export default {
  data () {
    return {
      recordList: []
    }
  },
  mounted () {
    this.getRecord()
  },
  methods: {
    getRecord () {
      getRecordList({ orderSn: this.$route.query.orderSn }).then(res => {
        if (res.error === 0) {
          this.recordList = res.content
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.main-container {
  padding: 10px;
  .content {
    background-color: #fff;
    height: 100%;
    position: relative;
    .wrap {
      border: 1px solid #ccc;
      background: #f5f5f5;
      position: absolute;
      left: 50%;
      margin-left: -176px;
      top: 20px;
      bottom: 20px;
      width: 352px;
      .title {
        height: 55px;
        color: white;
        text-align: center;
      }
      .wrap-content {
        background-color: #f5f5f5;
        position: absolute;
        top: 55px;
        bottom: 0;
        left: 0;
        right: 0;
        padding: 10px 0;
      }
    }
  }
}
.user_icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
}

.user_icon img {
  width: 100%;
  height: 100%;
}

.user_left {
  margin-left: 15px;
}

.user_right {
  margin-right: 15px;
}

.user_con {
  display: flex;
  margin-bottom: 15px;
}

.con_right {
  flex-direction: row-reverse;
}
.origin_message {
  max-width: 225px;
  min-height: 25px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  padding: 10px;
  position: relative;
  word-wrap: break-word;
  word-break: break-all;
}
.origin_left {
  background: #fff;
  font-size: 16px;
  color: #333;
  margin-left: 10px;
}
.origin_left::before {
  content: '';
  width: 0;
  height: 0;
  position: absolute;
  top: 17px;
  left: -5px;
  border-top: solid 5px transparent;
  border-right: solid 5px #fff;
  border-bottom: solid 5px transparent;
}
.origin_right {
  background: #26c4bc;
  font-size: 16px;
  color: #fff;
  margin-right: 10px;
}
.origin_right::before {
  content: '';
  width: 0;
  height: 0;
  position: absolute;
  top: 17px;
  right: -5px;
  border-top: solid 5px transparent;
  border-left: solid 5px #26c4bc;
  border-bottom: solid 5px transparent;
}
</style>
