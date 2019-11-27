<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!--会员卡模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <div class="carModule">
        <div
          class="card_back_module"
          :style="carData.bgType===0?`backgroundColor:${carData.bgColor}`:`backgroundImage:url('${$imageHost}/${carData.bgImg}')`"
        >
          <div class="card_type">{{carData.cardType===0?'普通卡':carData.cardType===1?'限次卡':'等级卡'}}</div>
          <div class="card_content clearfix">
            <div class="card_shop_icon">
              <img :src="$imageHost+'/image/admin/shop_def_y.png'">
            </div>
            <div class="card_content_right">
              <div>{{carData.cardName}}</div>
              <p>{{carData.legal}}</p>
              <p>{{carData.exchangCountLegal}}</p>
            </div>
            <!-- <div
                      class="card_pay_fee"
                      style="display: none;"
                    >￥0.00元</div> -->
          </div>
          <div class="card_bottom">
            {{$t('membershipCard.moreEquity')}}<span>{{$t('membershipCard.collarCard')}}</span>
          </div>
        </div>
      </div>
      <div
        :style="hoverTips?'width:140px':''"
        class="item_module_title"
      >
        <span>{{$t('membershipCard.membershipCard')}}</span>
      </div>
      <div class="item_operation">
        <img
          class="up_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_up_use.png'"
          @click.stop="handleToClickIcon(0)"
        >
        <img
          class="down_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
          @click.stop="handleToClickIcon(1)"
        >
        <img
          class="del_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
          @click.stop="handleToClickIcon(2)"
        >
      </div>
    </div>
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
      activeBorder: false,
      activeSetHere: false,
      hoverTips: 'hoverTips', // 英文适配
      carData: {
        backgroundColor: '#ecc98f',
        bgImgUrl: ''
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
      console.log(newData, this.index)
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
        if (newData) {
          console.log(newData)
          this.carData = newData
        }
        console.log(newData)
      },
      immediate: true,
      deep: true
    },
    lang () {
      switch (this.carData.card_type) {
        case '0':
          this.carClass = this.$t('membershipCard.ordinaryCard')
          break
        case '1':
          this.carClass = this.$t('membershipCard.limitCard')
          break
        case '2':
          this.carClass = this.$t('membershipCard.gradeCard')
      }
      this.carData.card_name = this.$t('membershipCard.cardName')
      this.carData.card_state = this.$t('membershipCard.cardState')
      this.carData.receive_day = this.$t('membershipCard.receiveDay')
      this.carData.legal = this.$t('membershipCard.legal')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      // 点击各模块触发事件
      this.$http.$on('modulesClick', res => {
        console.log(this.flag, res)
        if (this.flag === res) {
          this.activeBorder = true
        } else {
          this.activeBorder = false
        }
        console.log(res)
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
      this.$emit('middleDragData', this.flag)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/style/admin/decorationModules.scss";
.carModule {
  padding: 12px;
  .card_back_module {
    width: 100%;
    -webkit-border-radius: 8px;
    -moz-border-radius: 8px;
    border-radius: 8px;
    background: #ecc98f;
    color: #fff;
    padding: 0 20px;
    position: relative;
    overflow: hidden;
    font-size: 14px;
    .card_type {
      position: absolute;
      top: 0;
      right: 0;
      background: rgba(0, 0, 0, 0.1);
      width: 60px;
      // height: 20px;
      text-align: center;
      line-height: 20px;
      border-bottom-left-radius: 8px;
      font-size: 12px;
    }
    .card_content {
      padding: 20px 0;
      border-bottom: 1px solid #fff;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      position: relative;
      overflow: hidden;
      .card_shop_icon {
        img {
          display: inline-block;
          width: 100%;
        }
        float: left;
        width: 40px;
        height: 40px;
        -webkit-border-radius: 100%;
        -moz-border-radius: 100%;
        border-radius: 100%;
        overflow: hidden;
      }
      .card_content_right {
        float: left;
        margin-left: 10px;
        p {
          font-size: 12px;
          margin: 5px 0 0 0;
        }
      }
    }
    .card_bottom {
      text-align: right;
      line-height: 20px;
      padding: 6px 0;
      font-size: 12px;
      span {
        display: inline-block;
        border: 1px solid #fff;
        border-radius: 12px;
        height: 20px;
        line-height: 20px;
        padding: 0 10px;
        margin-left: 5px;
      }
    }
  }
}
</style>
