<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!--列表模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <!--模块编辑区-->
      <div class="integralExchange">
        <div class="integralExchangeMain">
          <ul :style="modulesData.list_styles===1?'display:block':'display:flex'">
            <li
              v-for="(item,index) in showData"
              :key="index"
              :style="(modulesData.list_styles===1?'display:flex;width:100%;':'display:block;')+((index !==0&&index!==1)?'margin-top:5px;':'')"
            >
              <div
                class="liTop"
                :style="(!item.goodsImg?'background:#eaf2ff url('+$imageHost+'/image/admin/shop_beautify/decorate_model.png) center no-repeat':'backgroundColor:#fff')+(modulesData.list_styles===1?';width:100px;height:100px;padding:5px':'')"
              >
                <img :src="item.goodsImg">
              </div>
              <div
                class="liBottom"
                :style="modulesData.list_styles===1?'flex:1':''"
              >
                <div class="goodsName">{{item.goodsName}}</div>
                <div
                  class="integral_info_head "
                  :style="modulesData.list_styles===1?'margin-top:37px':''"
                >
                  <div class="goodsPrice">
                    <div class="integral_price ">
                      ￥{{item.money}} + {{item.score}} <i style="font-size:12px">{{$t('integralExchange.integral')}}</i>
                    </div>
                  </div>
                </div>
                <div class="goods_prices_bottom">
                  <div
                    class="orignakl_orice"
                    v-if="modulesData.show_goods_price"
                  >￥{{item.prdPrice}}</div>
                  <div
                    v-else
                    style="height:25px"
                  ></div>
                  <div
                    class="btn_convert "
                    :style="columnFlag?'width:100px':''"
                  >{{$t('integralExchange.toExchange')}}</div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <!--模块编辑区结束-->
      <div
        class="item_module_title"
        :style="hoverTips?'width:140px':''"
      >
        <span>{{$t('commoditySearch.commodity')}}</span>
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
    flag: Number, // 模块公共
    nowRightShowIndex: Number, // 模块公共
    middleHereFlag: Boolean, // 模块公共
    backData: Object // 模块公共
  },
  data () {
    return {
      activeBorder: false, // 模块公共
      activeSetHere: false, // 模块公共
      hoverTips: 'hoverTips', // 英文适配  模块公共
      occupyingData: [ // 占位数据
        {
          'goodsImg': '',
          'goodsName': '积分商城名称-1',
          'prdPrice': '1000.00',
          'money': '200.00',
          'score': '400000'
        },
        {
          'goodsImg': '',
          'goodsName': '积分商城名称-2',
          'prdPrice': '1000.00',
          'money': '200.00',
          'score': '400000'
        }
      ],
      showData: [], //  显示数据
      // 模块私有
      modulesData: {

      },
      columnFlag: false
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
      console.log(newData)
      if (newData) {
        this.$emit('middleDragData', this.flag)
      }
    },
    activeBorder (newData) { // 模块公共
      console.log(newData, this.index)
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
          this.modulesData = newData
          if (newData.integral_goods.length) {
            this.showData = newData.integral_goods
          } else {
            this.showData = this.occupyingData
          }
        }
        console.log(newData)
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

.integralExchange {
  background: rgb(238, 238, 238);
  .integralExchangeMain {
    ul {
      padding: 5px;
      background: #f5f5f5;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      li {
        width: 49%;
        .liTop {
          overflow: hidden;
          position: relative;
          height: 150px;
          overflow: hidden;
          position: relative;
          background-size: 24% !important;
          img {
            max-width: 100%;
          }
        }
        .liBottom {
          background: #fff;
          padding: 5px;
          .goodsName {
            width: 100%;
            margin-bottom: 5px;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
          }
          .integral_info_head {
            .goodsPrice {
              width: 100%;
              white-space: nowrap;
              text-overflow: ellipsis;
              overflow: hidden;
              font-size: 12px;
              .integral_price {
                width: 100%;
                white-space: nowrap;
                text-overflow: ellipsis;
                overflow: hidden;
                font-size: 14px;
                color: rgb(126, 86, 197);
              }
            }
          }
          .goods_prices_bottom {
            display: flex;
            justify-content: space-between;
            .orignakl_orice {
              line-height: 25px;
              text-decoration: line-through;
              color: #999;
            }
            .btn_convert {
              width: 60px;
              height: 25px;
              display: inline-block;
              text-align: center;
              line-height: 25px;
              border-radius: 25px;
              color: #fff !important;
              font-size: 12px;
              background: linear-gradient(
                to right,
                rgba(126, 86, 197, 0.8),
                rgb(126, 86, 197)
              );
            }
          }
        }
      }
    }
  }
}
</style>
