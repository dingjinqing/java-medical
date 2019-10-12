<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!--优惠卷模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <div
        v-if="data.coupon_arr.length>1"
        class="coupon_module"
      >
        <div
          v-for="(item,index) in data.coupon_arr"
          :key="index"
          class="coupon_list"
          :style="'border-color: rgba(255, 102, 102, 0.4);'+(data.coupon_arr.length===2&&index===0?'margin-right:2%;':'')+(data.coupon_arr.length>=3&&index===1?'margin:0 2%;':'')+(data.coupon_arr.length===2?'width:50%;':'')+(data.coupon_arr.length>=3?'width:33%;':'')+(index>2?'display:none':'')"
        >
          <div
            class="coupon_list_top"
            style="color: rgb(255, 102, 102);"
          >
            {{item.act_code==='discount'?'':'¥'}}<span>{{item.denomination}}<i style="font-size:14px">{{item.actCode==='discount'?'折':''}}</i></span>
          </div>
          <div class="coupon_list_center">
            <div
              class="coupon_center_limit"
              style="color: rgb(255, 102, 102);"
            >{{item.consume_text}}</div>
            <div
              class="coupon_center_number"
              style="color: rgba(255, 102, 102, 0.4);"
            >{{item.receive_text}}</div>
          </div>
          <div
            class="coupon_list_bottom new_back"
            style="background-color: rgb(255, 102, 102);"
          >
            {{item.use_score==='0'?'领取':item.score_number+'积分 兑换'}}
          </div>
        </div>

      </div>
      <!--单个优惠卷布局模块-->
      <div
        class="coupon_module"
        v-else
      >
        <div class="singleContainer">
          <div class="singleLeft">
            <div>
              {{data.coupon_arr[0].act_code==='discount'?'':'¥'}}<span style="font-size:20px">{{data.coupon_arr[0].denomination}}<i style="font-size:14px">{{data.coupon_arr[0].act_code==='discount'?'折':''}}</i></span>

            </div>
          </div>
          <div class="singleMiddle">
            <div>{{data.coupon_arr[0].consume_text}}</div>
            <div>{{data.coupon_arr[0].receive_text}}</div>
          </div>
          <div
            class="singleLeftRight"
            :style="'background:#f66 url('+$imageHost+'/image/admin/coupon_border_y.png) repeat-y left'"
          >
            {{data.coupon_arr[0].use_score==='0'?'领取':data.coupon_arr[0].score_number+'积分 兑换'}}
          </div>
        </div>

      </div>
      <!--end-->
      <div class="item_module_title">
        <span>优惠卷</span>
      </div>
      <div class="item_operation">
        <img
          class="up_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_up_use.png'"
          @click.stop="handleToClickIcon(0)"
        >
        <img
          class="down_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
          @click.stop="handleToClickIcon(1)"
        >
        <img
          class="del_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
          @click.stop="handleToClickIcon(2)"
        >
      </div>
    </div>
    <!--end-->
    <!--放这里-->
    <div
      class="setHere activeSetHere"
      :class="activeSetHere?'middleModulesActive':''"
    >
      放这里
    </div>
  </div>
</template>
<script>
export default {
  props: {
    flag: Number,
    nowRightShowIndex: Number,
    middleHereFlag: Boolean,
    backData: Object
  },
  data () {
    return {
      activeBorder: null,
      activeSetHere: false,
      // 模块私有数据
      defaultData: {
        'coupon_arr': [ // 默认占位数据
          {
            'act_code': 'voucher', // 是否是打折卷  discount：打折卷   voucher不是打折卷
            'denomination': 'xx', // 面额
            'consume_text': '满xx使用', // 使用门槛
            'receive_text': '剩余xx张', // 卡卷剩余数
            'coupon_id': '', // 优惠卷id
            'use_score': '0', // 是否可以积分兑换
            'score_number': 'xx' // 需要积分数
          },
          {
            'act_code': 'voucher',
            'denomination': 'xx',
            'consume_text': '满xx使用',
            'receive_text': '剩余xx张',
            'coupon_id': '',
            'use_score': '0',
            'score_number': 'xx'
          },
          {
            'act_code': 'voucher',
            'denomination': 'xx',
            'consume_text': '满xx使用',
            'receive_text': '剩余xx张',
            'coupon_id': '',
            'use_score': '0',
            'score_number': 'xx'
          }
        ]
      },
      // 保存数据
      data: {

      }
    }
  },
  watch: {
    nowRightShowIndex (newData) {
      if (this.flag === newData) {
        this.activeBorder = true
      } else {
        this.activeBorder = false
      }
    },
    activeSetHere (newData) {
      console.log(newData)
      if (newData) {
        this.$emit('middleDragData', this.flag)
      }
    },
    activeBorder (newData) {
      if (newData) {
        this.$http.$emit('nowHightLightModules', this.flag)
      }
    },
    middleHereFlag (newData) {
      if (newData) {
        this.activeSetHere = true
      } else {
        this.activeSetHere = false
      }
    },
    backData: {
      handler (newData) {
        console.log(newData)
        if (newData.coupon_arr.length) {
          this.data = newData
        } else {
          this.data = this.defaultData
        }
      },
      immediate: true,
      deep: true
    }
  },
  mounted () {
    // 初始化数据
    this.handleDefaultData()
  },
  methods: {
    handleDefaultData () {
      // 点击各模块触发事件
      this.$http.$on('modulesClick', res => {
        console.log(this.flag, res)
        if (this.flag === res) {
          this.activeBorder = true
        } else {
          this.activeBorder = false
        }
        console.log(this.flag, res)
      })
    },
    // 移上、移下、删除统一处理事件
    handleToClickIcon (flag) {
      console.log(flag)
      let obj = {
        direction: '',
        flag: this.flag
      }
      switch (flag) {
        case 0:
          obj.direction = 'up'
          break
        case 1:
          obj.direction = 'down'
          break
        case 2:
          obj.direction = 'delete'
          break
      }
      this.$emit('handleToClickIcon', obj)
    },
    // 模块划过
    mouseOver () {
      // console.log(this.flag)
      this.$emit('middleDragData', this.flag)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/style/admin/decorationModules.scss";
.coupon_module {
  padding: 10px;
  background: #fff;
  display: flex;
  justify-content: space-between;
  .coupon_list {
    height: 100%;
    border: 1px solid #fbb;
    -webkit-border-radius: 110px;
    -moz-border-radius: 10px;
    border-radius: 10px;
    text-align: center;
    overflow: hidden;
    position: relative;
    bottom: -1px;
    .coupon_list_top {
      margin-top: 10px;
      color: #f66;
      font-size: 14px;
      span {
        font-size: 20px;
        font-weight: bold;
        display: inline-block;
      }
    }
    .coupon_list_center {
      height: 40px;
      color: #f66;
      font-size: 12px;
    }
    .coupon_list_bottom {
      font-size: 12px;
      background: #f66
        url(../../../../../../../../assets/adminImg/coupon_border.png) repeat-x
        top;
      -webkit-background-size: 12px;
      background-size: 12px;
      height: 24px;
      line-height: 30px;
      color: #fff;
    }
  }
  .singleContainer {
    border: 1px solid #fbb;
    height: 100px;
    border-radius: 10px;
    width: 100%;
    display: flex;
    overflow: hidden;
    .singleLeft {
      width: 30%;
      display: flex;
      justify-content: center;
      align-items: center;
      color: #f66;
    }
    .singleMiddle {
      width: 40%;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      div:nth-of-type(1) {
        color: #f66;
      }
      div:nth-of-type(2) {
        color: #fbb;
      }
    }
    .singleLeftRight {
      width: 30%;
      background: #f66 url(/image/admin/coupon_border_y.png) repeat-y left;
      background-size: 8px;
      color: #fff;
      height: 100px;
      line-height: 100px;
      text-align: center;
      font-size: 12px;
    }
  }
}
</style>
