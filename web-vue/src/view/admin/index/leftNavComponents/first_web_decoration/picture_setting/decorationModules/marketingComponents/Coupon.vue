<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!--优惠券模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <div
        v-if="!isRightPageChangeFlga"
        class="coupon_module"
      >
        <div
          v-for="(item,index) in data.coupon_arr"
          :key="index"
          class="coupon_list"
          :style="'border-color:'+backgroundColor+';'+(data.coupon_arr.length===2&&index===0?'margin-right:2%;':'')+(data.coupon_arr.length>=3&&index===1?'margin:0 2%;':'')+(data.coupon_arr.length===2?'width:50%;':'')+(data.coupon_arr.length>=3?'width:33%;':'')+(index>2?'display:none':'')"
        >
          <div
            class="coupon_list_top"
            :style="'color:'+backgroundColor+';'"
          >
            {{item.act_code==='discount'?'':'¥'}}<span>{{item.denomination}}<i style="font-size:14px">{{item.act_code==='discount'?$t('coupon.fracture'):''}}</i></span>
          </div>
          <div class="coupon_list_center">
            <div
              class="coupon_center_limit"
              :style="'color:'+backgroundColor+';'"
            >{{item.consume_text}}</div>
            <div
              class="coupon_center_number"
              :style="'color:'+backgroundColorTransparent+';'"
            >
              <div>{{item.limitSurplusFlag===1?$t('coupon.unlimitedInventory'):item.receive_text}}</div>
            </div>
          </div>
          <div
            class="coupon_list_bottom new_back"
            :style="'border-color:'+backgroundColor+';background-color:'+backgroundColor"
          >
            {{item.use_score===0?$t('coupon.receive'):item.score_number+$t('coupon.integral')}}
          </div>
        </div>

      </div>
      <!--单个优惠券布局模块-->
      <div
        class="coupon_module"
        v-else
      >
        <div
          class="singleContainer"
          :style="'border-color:'+backgroundColor"
        >
          <div
            class="singleLeft"
            :style="'color:'+backgroundColor"
          >
            <div>
              {{data.coupon_arr[0].act_code==='discount'?'':'¥'}}<span style="font-size:20px">{{data.coupon_arr[0].denomination}}<i style="font-size:14px">{{data.coupon_arr[0].act_code==='discount'?$t('coupon.fracture'):''}}</i></span>

            </div>
          </div>
          <div class="singleMiddle">
            <div :style="'color:'+backgroundColor">{{data.coupon_arr[0].consume_text}}</div>
            <div :style="'color:'+backgroundColorTransparent">{{data.coupon_arr[0].limitSurplusFlag===1?$t('coupon.unlimitedInventory'):data.coupon_arr[0].receive_text}}</div>
          </div>
          <div
            class="singleLeftRight"
            :style="'background:'+backgroundColor+' url('+$imageHost+'/image/admin/coupon_border_y.png) repeat-y left;'"
          >
            {{data.coupon_arr[0].use_score==='0'?$t('coupon.receive'):data.coupon_arr[0].score_number+$t('coupon.integral')}}
          </div>
        </div>

      </div>
      <!--end-->
      <div class="item_module_title">
        <span>{{$t('coupon.coupon')}}</span>
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
      {{$t('commoditySearch.putItHere')}}
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
      firstInter: false,
      // 模块私有数据
      backgroundColor: '',
      backgroundColorTransparent: '',
      defaultData: {}, // 占位数据
      isRightPageChangeFlga: false, // 是否用单个文件布局
      // 保存数据
      data: {
        coupon_arr: [
          {
            'act_code': 'voucher',
            'denomination': 'xx',
            'consume_text': '满xx使用',
            'receive_text': '剩余xx张',
            'coupon_id': '',
            'use_score': '0',
            'score_number': 'xx',
            'limitSurplusFlag': ''
          }
        ]
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
        this.$nextTick(() => {
          console.log(newData)
          if (newData.coupon_arr.length > 1) {
            console.log(1)
            this.data = newData
            this.isRightPageChangeFlga = false
          } else if (newData.coupon_arr.length === 1) {
            this.isRightPageChangeFlga = true
            this.data = newData
          } else {
            console.log(0)
            this.isRightPageChangeFlga = false
            this.data = this.defaultData
          }
        })
        this.firstInter = false

        // if (newData.coupon_arr.length === 1) {
        //   this.isRightPageChangeFlga = true
        // } else {
        //   this.isRightPageChangeFlga = false
        // }
      },
      deep: true,
      immediate: true
    },
    lang () {
      if (!this.firstInter) return
      let obj = {}
      obj['coupon_arr'] = this.$t('coupon.coupon_arr')
      console.log(obj)
      this.defaultData = obj
      this.handleDefaultData()
    }
  },
  mounted () {
    this.firstInter = true
    // 初始化语言
    this.langDefault()
  },
  methods: {
    handleDefaultData () {
      this.data = this.defaultData
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
      console.log(localStorage.getItem('V-backgroundColor'))
      this.backgroundColor = localStorage.getItem('V-backgroundColor') || 'rgb(255, 102, 102)'
      this.backgroundColorTransparent = this.backgroundColor.split(')')[0] + ',0.4)'
      console.log(this.backgroundColor, this.backgroundColorTransparent)
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
      .coupon_center_limit {
        margin: 5px 0;
      }
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
