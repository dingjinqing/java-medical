<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!-- 列表模块 -->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <!-- 模块编辑区 -->
      <div class="bargain_content">
        <ul
          v-if="data.list_style == 0"
          class="bargain_default_ul double_column"
        >
          <li
            class="bargain_default_li"
            v-show="data.bargain_goods && data.bargain_goods.length === 0"
          >
            <div
              class="bargain_default_img"
              :style="'background-image:url('+ $imageHost +'/image/admin/shop_beautify/decorate_model.png)'"
            >
            </div>
            <div class="bargain_default_info">
              <div class="bargain_info_head">
                <span
                  class="bargain_price new_price"
                  :style="'color:' + bgColor"
                >￥<span style="font-size: 18px;">0</span></span>
                <span class="bargain_old">￥0</span>
              </div>
              <div class="bargain_info_name"></div>
              <div class="bargain_info_bottom">
                <span class="bargain_num">{{$t('bargain.onlyLeft')}}10{{$t('bargain.pieces')}}</span>
                <span
                  class="bargin_free_btn"
                  :style="'background: ' + bgColor"
                >{{$t('bargain.goPrice')}}</span>
              </div>
            </div>
          </li>
          <li
            class="bargain_default_li"
            v-for="(good,index) in data.bargain_goods"
            :key="index"
          >
            <div
              class="bargain_default_img"
              :style="'background-image:url('+ $imageHost +'/image/admin/shop_beautify/decorate_model.png)'"
            >
              <el-image
                :src="$imageHost + '/' + good.goods_img"
                style="width: 100%; height: 150px;"
              ></el-image>
              <div
                v-if="data.goods_count_down"
                class="bargain_time_down"
              >
                <div>{{$t('bargain.endOfLeft')}}</div>
                <p>{{good.act_end_time | endTimeFmt}}</p>
              </div>
            </div>
            <div class="bargain_default_info">
              <div class="bargain_info_head">
                <span
                  class="bargain_price"
                  :style="'color: '+ bgColor"
                >￥<span style="font-size: 18px;">{{good.expectation_price}}</span></span>
                <span
                  v-if="data.goods_price"
                  class="bargain_old"
                >￥{{good.goods_price}}</span>
              </div>
              <div class="bargain_info_name">{{good.goods_name}}</div>
              <div class="bargain_info_bottom">
                <span class="bargain_num">{{$t('bargain.onlyLeft')}}{{good.bargain_num}}{{$t('bargain.pieces')}}</span>
                <span
                  v-if="data.free_btn"
                  class="bargin_free_btn"
                  :style="'background:' + bgColor"
                >{{$t('bargain.goPrice')}}</span>
              </div>
            </div>
          </li>
        </ul>
        <ul
          v-else
          class="bargain_default_ul single_column"
        >
          <li
            class="bargain_default_li"
            v-show="data.bargain_goods && data.bargain_goods.length === 0"
          >
            <div
              class="bargain_default_img"
              :style="'background-image:url('+ $imageHost +'/image/admin/shop_beautify/decorate_model.png)'"
            >
            </div>
            <div class="bargain_default_info">
              <div class="bargain_info_head">
                <span class="bargain_num">{{$t('bargain.onlyLeft')}}10{{$t('bargain.pieces')}}</span>
              </div>
              <div class="bargain_info_bottom">
                <div class="bargain_price_wrap">
                  <span
                    class="bargain_price"
                    :style="'color:' + bgColor"
                  >￥<span style="font-size: 18px;">0</span></span>
                  <span class="bargain_old">￥0</span>
                </div>
                <span
                  class="bargin_free_btn"
                  :style="'background:'+bgColor"
                >{{$t('bargain.goPrice')}}</span>
              </div>
            </div>
          </li>
          <li
            class="bargain_default_li"
            v-for="(good,index) in data.bargain_goods"
            :key="index"
          >
            <div
              class="bargain_default_img"
              :style="'background-image:url('+ $imageHost +'/image/admin/shop_beautify/decorate_model.png)'"
            >
              <el-image
                :src="$imageHost+ '/' + good.goods_img"
                style="width: 100%; height: 100%;"
              ></el-image>
              <div class="bargain_time_down">
                <div>{{$t('bargain.endOfLeft')}}</div>
                <p>{{good.act_end_time | endTimeFmt}}</p>
              </div>
            </div>
            <div class="bargain_default_info">
              <div
                v-if="data.goods_count_down"
                class="bargain_info_head"
              >
                <div class="bargain_info_name">{{good.goods_name}}</div>
                <span class="bargain_num">{{$t('bargain.onlyLeft')}}{{good.bargain_num}}{{$t('bargain.pieces')}}</span>
              </div>
              <div class="bargain_info_bottom">
                <div class="bargain_price_wrap">
                  <span
                    class="bargain_price"
                    :style="'color:'+bgColor"
                  >￥<span style="font-size: 18px;">{{good.expectation_price}}</span></span>
                  <span
                    v-if="data.goods_price"
                    class="bargain_old"
                  >￥{{good.goods_price}}</span>
                </div>
                <span
                  v-if="data.free_btn"
                  class="bargin_free_btn"
                  :style="'background:'+bgColor"
                >{{$t('bargain.goPrice')}}</span>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <!-- 模块编辑区结束 -->
      <!-- 移动操作工具栏 -->
      <div
        class="item_module_title"
        :style="hoverTips?'width:140px':''"
      >
        <span>{{$t('bargain.bargain')}}</span>
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
import vm from '@/main'
export default {
  props: {
    flag: Number, // 模块公共
    nowRightShowIndex: Number, // 模块公共
    middleHereFlag: Boolean, // 模块公共
    backData: Object // 模块公共,左侧传递过来的数据
  },
  data () {
    return {
      activeBorder: false, // 模块公共
      activeSetHere: false, // 模块公共
      hoverTips: 'hoverTips', // 英文适配  模块公共
      // 模块私有
      data: {
        module_name: 'm_bargain',
        list_style: '0',
        goods_price: true,
        goods_count_down: true,
        free_btn: true,
        bargain_goods: []
      },
      bgColor: '' // 主题色
    }
  },
  filters: {
    endTimeFmt: function (val) {
      let date = new Date(val).getTime()
      let now = new Date()
      let time1 = date - now // 相差毫秒数
      if (time1 <= 0) {
        return vm.$t('bargain.over')
      }
      let oneDay = 24 * 60 * 60 * 1000
      let days = Math.floor(time1 / oneDay)
      let time2 = time1 - days * oneDay // 1天，相差毫秒数
      let hours = Math.floor(time2 / (60 * 60 * 1000))
      let time3 = time2 - hours * (60 * 60 * 1000) // 1 小时，相差毫秒数
      let minutes = Math.floor(time3 / (60 * 1000))
      let time4 = time3 - minutes * 60 * 1000
      let seconds = Math.floor(time4 / 1000)
      console.log('date:', val, days, hours, minutes, seconds)
      return days + vm.$t('bargain.day') + hours + vm.$t('bargain.hour') + minutes + vm.$t('bargain.minute')
    }
  },
  watch: {
    nowRightShowIndex (newData) { // 模块公共
      if (this.flag === newData) {
        this.activeBorder = true
      } else {
        this.activeBorder = false
      }
    },
    activeSetHere (newData) { // 模块公共
      if (newData) {
        this.$emit('middleDragData', this.flag)
      }
    },
    activeBorder (newData) { // 模块公共
      if (newData) {
        this.$http.$emit('nowHightLightModules', this.flag)
      }
    },
    middleHereFlag (newData) { // 模块公共
      if (newData) {
        this.activeSetHere = true
      } else {
        this.activeSetHere = false
      }
    },
    // 右侧模块点击传回中间当前高亮模块的数据
    backData: { // 模块公共
      handler (newData) {
        if (newData) {
          console.log(newData)
          if (newData.bargain_goods.length) {
            this.data = newData
          }
        }
      },
      immediate: true,
      deep: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault() // 模块公共
    // 初始化数据
    this.defaultData() // 模块公共

    this.bgColor = localStorage.getItem('V-backgroundColor') || '#f66'
  },
  methods: {
    defaultData () { // 模块公共
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
    handleToClickIcon (flag) { // 模块公共
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
    mouseOver () { // 模块公共
      this.$emit('middleDragData', this.flag)
    }
  }
}
</script>

<style lang="scss" scoped>
@import "@/style/admin/decorationModules.scss"; // 模块公共
.bargain_content {
  box-sizing: border-box;
  .bargain_default_ul {
    padding: 5px;
    background: #f5f5f5;
  }
  .double_column {
    display: flex;
    flex-wrap: wrap;
    .bargain_default_li {
      flex-basis: 50%;
      width: 50%;
      padding: 1px;
      .bargain_default_img {
        height: 150px;
        position: relative;
        background: #eaf2ff;
        background-repeat: no-repeat;
        background-position: center center;
        background-size: 24%;
        .bargain_time_down {
          position: absolute;
          width: 100%;
          text-align: center;
          padding: 5px 0;
          bottom: 0;
          background: rgba(0, 0, 0, 0.3);
          color: #fff;
          font-size: 12px;
        }
      }
      .bargain_default_info {
        background: #fff;
        padding: 5px;
        .bargain_info_head {
          .bargain_price {
            color: #f66;
            font-size: 12px;
            vertical-align: bottom;
          }
          .new_price {
            color: rgb(91, 158, 164);
          }
          .bargain_old {
            color: #999999;
            text-decoration: line-through;
            font-size: 12px;
            margin-left: 2px;
            width: 40px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            margin-top: 0px;
            vertical-align: bottom;
          }
        }
        .bargain_info_name {
          min-height: 38px;
          margin: 5px 0;
          width: 100%;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }
        .bargain_info_bottom {
          display: flex;
          justify-content: space-between;
          .bargain_num {
            font-size: 12px;
            color: #999;
            float: left;
            line-height: 25px;
          }
          .bargin_free_btn {
            background: linear-gradient(
              to right,
              rgba(241, 35, 55, 0.8),
              rgb(241, 35, 55)
            );
            width: 60px;
            height: 25px;
            display: inline-block;
            text-align: center;
            line-height: 25px;
            padding: 0;
            color: #fff;
            font-size: 12px;
            border-radius: 25px;
          }
          .new_back {
            background: linear-gradient(
              to right,
              rgba(91, 158, 164, 0.8),
              rgb(91, 158, 164)
            );
          }
        }
      }
    }
  }
  .single_column {
    .bargain_default_li {
      display: flex;
      width: 100%;
      padding: 5px;
      background: #fff;
      margin-bottom: 5px;
      .bargain_default_img {
        width: 100px;
        height: 100px;
        position: relative;
        background: #eaf2ff;
        background-repeat: no-repeat;
        background-position: center center;
        background-size: 24%;
        .bargain_time_down {
          position: absolute;
          width: 100%;
          text-align: center;
          padding: 5px 0;
          bottom: 0;
          background: rgba(0, 0, 0, 0.3);
          color: #fff;
          font-size: 12px;
        }
      }
      .bargain_default_info {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        width: 100%;
        align-content: space-between;
        padding-left: 5px;
        .bargain_info_head {
          width: 100%;
          .bargain_info_name {
            margin-top: 5px;
            font-size: 14px;
            vertical-align: top;
          }
          .bargain_num {
            font-size: 12px;
            color: #999;
            line-height: 25px;
          }
        }
        .bargain_info_bottom {
          display: flex;
          justify-content: space-between;
          .bargain_price_wrap {
            line-height: 25px;
            .bargain_price {
              color: #f66;
              vertical-align: bottom;
            }
            .new_price {
              color: rgb(91, 158, 164);
            }
            .bargain_old {
              color: #999999;
              text-decoration: line-through;
              font-size: 12px;
              margin-left: 2px;
              width: 40px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              margin-top: 0px;
              vertical-align: bottom;
            }
          }
          .bargin_free_btn {
            background: linear-gradient(
              to right,
              rgba(241, 35, 55, 0.8),
              rgb(241, 35, 55)
            );
            width: 60px;
            height: 25px;
            display: inline-block;
            text-align: center;
            line-height: 25px;
            padding: 0;
            color: #fff;
            font-size: 12px;
            border-radius: 25px;
          }
          .new_back {
            background: linear-gradient(
              to right,
              rgba(91, 158, 164, 0.8),
              rgb(91, 158, 164)
            );
          }
        }
      }
    }
  }
}
</style>
