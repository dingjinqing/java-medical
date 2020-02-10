<template>
  <!-- 秒杀 -->
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <!-- 模块编辑区 -->
      <div class="spike_content">
        <ul
          class="spike_ul vertical_column"
          v-if="data.list_style == '0'"
        >
          <li
            v-if="data.seckill_goods && data.seckill_goods.length === 0"
            class="spike_li"
          >
            <div
              class="spike_img_wrap"
              :style="'background-image:url(' + $imageHost+ '/image/admin/shop_beautify/decorate_model.png)'"
            >
            </div>
            <div class="spike_bottom">
              <h3>{{$t('spike.spikeName')}}</h3>
              <div>
                <span
                  class="spike_mark"
                  :style="'border:1px solid '+themeColor + ';color:'+themeColor+';'"
                >{{$t('spike.secondKill')}}</span>
                <span
                  class="spike_price"
                  :style="'color:'+themeColor"
                >￥<span style="font-size:18px;">0</span></span>
                <span class="spike_old_price">￥<span>0</span></span>
              </div>
            </div>
          </li>
          <li
            v-for="good in data.seckill_goods"
            :key="good.goods_id"
            class="spike_li"
          >
            <div
              class="spike_img_wrap"
              :style="'background-image:url(' + $imageHost+ '/image/admin/shop_beautify/decorate_model.png)'"
            >
              <el-image
                :src="good.goods_img"
                style="width:100%;height:100%;"
                mode="contain"
              ></el-image>
              <div
                v-if="data.goods_count_down"
                class="spike_countdown"
              >
                <div v-if="new Date(good.act_begin_time) < new Date()">
                  <p>{{$t('spike.endOfLeft')}}</p>
                  <p>{{good.act_end_time | endTimeFmt}}</p>
                </div>
                <div v-else>
                  <p>{{$t('spike.beginTime')}}</p>
                  <p>{{good.act_begin_time}}</p>
                </div>
              </div>
            </div>
            <div class="spike_bottom">
              <h3>{{good.goods_name}}</h3>
              <div>
                <span
                  class="spike_mark"
                  :style="'border:1px solid '+themeColor + ';color:'+themeColor+';'"
                >{{$t('spike.secondKill')}}</span>
                <span
                  class="spike_price"
                  :style="'color:'+themeColor"
                >￥<span style="font-size:18px;">{{good.sec_price}}</span></span>
                <span
                  v-if="data.goods_price"
                  class="spike_old_price"
                >￥<span>{{good.goods_price}}</span></span>
              </div>
            </div>
          </li>
        </ul>
        <ul
          class="spike_ul horizontal_row"
          v-if="data.list_style == '1'"
        >
          <li
            class="spike_li"
            v-show="data.seckill_goods && data.seckill_goods.length === 0"
          >
            <div
              class="spike_img_wrap"
              :style="'background-image:url(' + $imageHost+ '/image/admin/shop_beautify/decorate_model.png)'"
            >
            </div>
            <div class="spike_right">
              <h3>{{$t('spike.spikeName')}}</h3>
              <div>
                <span
                  class="spike_mark"
                  :style="'border:1px solid '+themeColor + ';color:'+themeColor+';'"
                >{{$t('spike.secondKill')}}</span>
              </div>
              <p
                class="spike_price"
                :style="'color:'+themeColor"
              >￥<span style="font-size:18px;">0</span></p>
              <p
                v-if="data.goods_price"
                class="spike_old_price"
              >￥<span>0</span></p>
              <div class="spike_right_bottom">
                <span class="spike_to">{{$t('spike.goToBuy')}}</span>
                <div class="spike_to_tip">
                  <div class="spike_to_info">{{$t('spike.sold')}}50%</div>
                  <el-progress
                    class="spike_progress"
                    :style="'color:' + themeColor"
                    :percentage="50"
                    :show-text="false"
                  ></el-progress>
                </div>
              </div>
            </div>
          </li>
          <li
            class="spike_li"
            v-for="good in data.seckill_goods"
            :key="good.goods_id"
          >
            <div
              class="spike_img_wrap"
              :style="'background-image:url(' + $imageHost+ '/image/admin/shop_beautify/decorate_model.png)'"
            >
              <el-image
                :src="good.goods_img"
                style="width:100%; height:100%;"
                mode="contain"
              ></el-image>
              <div
                v-if="data.goods_count_down"
                class="spike_countdown"
              >
                <div v-if="new Date(good.act_begin_time) < new Date()">
                  <p>{{$t('spike.endOfLeft')}}</p>
                  <p>{{good.act_end_time | endTimeFmt}}</p>
                </div>
                <div v-else>
                  <p>{{$t('spike.beginTime')}}</p>
                  <p>{{good.act_begin_time}}</p>
                </div>
              </div>
            </div>
            <div class="spike_right">
              <h3>{{good.goods_name}}</h3>
              <div>
                <span
                  class="spike_mark"
                  :style="'border:1px solid '+themeColor + ';color:'+themeColor+';'"
                >{{$t('spike.secondKill')}}</span>
              </div>
              <p
                class="spike_price"
                :style="'color:'+themeColor"
              >￥<span style="font-size:18px;">{{good.sec_price}}</span></p>
              <p class="spike_old_price">￥<span>{{good.goods_price}}</span></p>
              <div class="spike_right_bottom">
                <span class="spike_to">{{$t('spike.goToBuy')}}</span>
                <div class="spike_to_tip">
                  <div class="spike_to_info">{{$t('spike.sold')}}{{good.salePercent * 100}}%</div>
                  <el-progress
                    class="spike_progress"
                    :style="'color:' + themeColor"
                    :percentage="1 - good.salePercent"
                    :show-text="false"
                  ></el-progress>
                </div>
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
        <span>{{$t('spike.secondKill')}}</span>
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
      themeColor: '', // 主题色
      data: {
        module_name: 'm_seckill',
        list_style: '0',
        goods_price: true,
        goods_count_down: true,
        seckill_goods: []
      }
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault() // 模块公共
    // 初始化数据
    this.defaultData() // 模块公共

    this.themeColor = localStorage.getItem('V-backgroundColor') || 'rgb(91, 158, 164)'
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
        console.log(newData)
        if (newData) {
          if (newData.seckill_goods) {
            newData.seckill_goods.forEach(function (item, i) {
              if (!Number(item.seckill_num)) {
                item.salePercent = 0
              } else {
                item.salePercent = Number((Number(item.sale_num) + Number(item.base_sale)) / (Number(item.seckill_num) + Number(item.base_sale))).toFixed(4) || 0
              }
            })
          }
          this.data = newData
        }
      },
      immediate: true,
      deep: true
    }
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
.spike_content {
  box-sizing: border-box;
  color: #333;
  .spike_ul {
    padding: 5px;
    background: #f5f5f5;
  }
  .vertical_column {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    .spike_li {
      width: 49%;
      flex-basis: 49%;
      .spike_img_wrap {
        height: 150px;
        background-color: #eaf2ff;
        background-position: center;
        background-repeat: no-repeat;
        background-size: 24%;
        position: relative;
        .spike_countdown {
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
      .spike_bottom {
        width: 100%;
        background: #fff;
        padding: 5px;
        h3 {
          width: 100%;
          margin-bottom: 5px;
          white-space: nowrap;
          text-overflow: ellipsis;
          overflow: hidden;
          font-size: 14px;
        }
        div {
          height: 22px;
          line-height: 22px;
        }
        .spike_mark {
          color: #f66;
          border: 1px solid #f66;
          padding: 0px 5px;
          height: 14px;
          margin-right: 2px;
          font-size: 14px;
          line-height: 14px;
          border-radius: 2px;
        }
        .spike_price {
          color: #f66;
          font-size: 12px;
          line-height: 14px;
          vertical-align: bottom;
        }
        .spike_old_price {
          color: #999;
          font-size: 12px;
          text-decoration: line-through;
          margin-left: 3px;
          line-height: 14px;
          vertical-align: bottom;
        }
      }
    }
  }
  .horizontal_row {
    .spike_li {
      width: 100%;
      padding: 5px;
      background: #fff;
      display: flex;
      .spike_img_wrap {
        width: 100px;
        height: 100px;
        background-color: #eaf2ff;
        background-position: center;
        background-repeat: no-repeat;
        background-size: 24%;
        position: relative;
        flex: none;
        .spike_countdown {
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
      .spike_right {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding-left: 5px;
        position: relative;
        overflow: hidden;
        h3 {
          margin-bottom: 5px;
          white-space: nowrap;
          text-overflow: ellipsis;
          overflow: hidden;
          font-size: 14px;
        }
        div {
          line-height: 22px;
        }
        .spike_mark {
          color: #f66;
          border: 1px solid #f66;
          padding: 0px 5px;
          height: 14px;
          margin-right: 2px;
          font-size: 14px;
          line-height: 14px;
          border-radius: 2px;
        }
        .spike_price {
          color: #f66;
          font-size: 12px;
          line-height: 14px;
          vertical-align: bottom;
        }
        .spike_old_price {
          color: #999;
          font-size: 12px;
          text-decoration: line-through;
          margin-left: 3px;
          line-height: 14px;
          vertical-align: bottom;
        }
        .spike_right_bottom {
          position: absolute;
          bottom: 0;
          right: 0;
          width: 125px;
          .spike_to {
            display: block;
            float: right;
            background: linear-gradient(
              to right,
              rgba(91, 158, 164, 0.8),
              rgb(91, 158, 164)
            );
            width: 60px;
            height: 25px;
            text-align: center;
            line-height: 25px;
            border-radius: 25px;
            font-size: 12px;
            color: #fff;
          }
          .spike_to_tip {
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            .spike_to_info {
              flex: 1;
              color: #999;
              font-size: 12px;
              margin-right: 5px;
              text-align: right;
            }
            .spike_progress {
              width: 60px;
              flex: none;
            }
          }
        }
      }
    }
  }
}
</style>
