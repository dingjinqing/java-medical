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
      <div class="commodity">
        <div>
          <ul>
            <li
              v-for="(item,index) in goodsDataList"
              :key="index"
            >
              <div class="containter">
                <div class="commodityTop">
                  <div class="label">
                    <img
                      v-if="!item.isNewGoods"
                      :src="item.labelUrl"
                    >
                    <div
                      class="label newGoods"
                      v-else
                    >
                      <span>新品</span>
                    </div>
                  </div>
                  <img :src="item.imgUrl">
                </div>
                <div class="commodityBottom">
                  <div class="bottomHead">
                    <div>{{item.goodsName}}</div>
                    <div>
                      <span>领券减￥100</span>
                    </div>
                  </div>
                  <div class="bottomFooter">
                    <span>￥{{item.price}}</span>
                    <span>￥0.00</span>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>

      </div>
      <!--模块编辑区结束-->
      <div class="item_module_title">
        <span>商品</span>
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
      activeBorder: false,
      activeSetHere: false,
      // 模块私有
      goodsDataList: [
        {
          goodsName: '商品修改测试1',
          imgUrl: this.$imageHost + '/image/admin/0a2kFnVCg46fdTNw.jpeg',
          price: 112,
          labelUrl: this.$imageHost + '/image/admin/crop_2Slxp6DbLukZ1EJl.png',
          isNewGoods: false
        },
        {
          goodsName: '商品修改测试2',
          imgUrl: this.$imageHost + '/image/admin/0a2kFnVCg46fdTNw.jpeg',
          price: 544,
          labelUrl: this.$imageHost + '/image/admin/crop_2Slxp6DbLukZ1EJl.png',
          isNewGoods: false
        },
        {
          goodsName: '商品修改测试3',
          imgUrl: this.$imageHost + '/image/admin/crop_wlEjGAPFNMXl1EVr.jpeg',
          price: 323,
          labelUrl: this.$imageHost + '/image/admin/crop_2Slxp6DbLukZ1EJl.png',
          isNewGoods: true
        },
        {
          goodsName: '商品修改测试4',
          imgUrl: this.$imageHost + '/image/admin/crop_wlEjGAPFNMXl1EVr.jpeg',
          price: 334,
          labelUrl: this.$imageHost + '/image/admin/crop_2Slxp6DbLukZ1EJl.png',
          isNewGoods: true
        }
      ]

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
    // 右侧模块点击传回中间当前高亮模块的数据
    backData: {
      handler (newData) {
        if (newData) {

        }
        console.log(newData)
      },
      immediate: true
    }
  },
  mounted () {
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

.commodity {
  ul {
    // display: flex;
    // flex-wrap: wrap;
    li {
      width: 50%;
      padding: 8px 10px;
      .containter {
        display: flex;
        height: 145px;
        .commodityTop {
          position: relative;
          height: 145px;
          .label {
            position: absolute;
            top: 0px;
            left: 0px;
            img {
              width: 60px;
            }
          }
          .newGoods {
            position: absolute;
            left: 2px;
            top: 2px;
            text-align: center;
            width: 22px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: transparent;
            font-size: 12px;
            padding-left: 5px;
            padding-right: 5px;
            border-radius: 1px;
            line-height: 15px;
            word-break: break-all;
            color: rgb(64, 128, 128);
            border: 1px solid rgb(64, 128, 128);
          }
          img {
            max-height: 145px;
          }
        }
        .commodityBottom {
          // height: 72px;
          padding: 10px 10px 0 0;
          height: 100%;
          .bottomHead {
            // height: 35px;
            white-space: normal;
            word-break: break-all;
            margin-bottom: 10px;
            div {
              white-space: nowrap;
            }
            div:nth-of-type(2) {
              font-size: 12px;
              margin-top: 5px;
              margin-top: 50px;
              span {
                padding: 1px 4px;
                border-radius: 2px;
                border: 1px solid rgb(64, 128, 128);
                color: rgb(64, 128, 128);
              }
            }
          }
          .bottomFooter {
            display: flex;
            justify-content: space-between;
            flex-direction: column;
            height: 40px;
            span {
              margin: 0 2px 0px 0;
              min-height: 17px;
              &:nth-of-type(1) {
                color: rgb(64, 128, 128);
              }
              &:nth-of-type(2) {
                color: #c0c0c0;
                text-decoration: line-through;
              }
            }
          }
        }
      }
    }
  }
}
</style>
