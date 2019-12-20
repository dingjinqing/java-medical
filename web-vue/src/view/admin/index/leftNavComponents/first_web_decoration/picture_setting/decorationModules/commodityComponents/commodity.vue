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
          <!--隐藏模块  文字标题选中显示-->
          <div>
            <div
              class="hiddenTitle"
              :style="data.tit_center?'justify-content:center':''"
              v-if="data.goods_module_title!=='0'&&(data.title || data.img_url || data.img_title_url)"
            >
              <img
                v-if="data.goods_module_title==='1'&&data.img_url"
                style="height:30px;margin-right:15px"
                :src="data.img_url"
              >
              {{data.title}}

            </div>
            <div v-if="data.goods_module_title==='2'&&data.img_title_url">
              <img
                class="img_title"
                :src="data.img_title_url"
              >
            </div>
          </div>

          <!--end-->
          <!--无商品占位-->
          <div
            v-if="!goodsFlag"
            style="height:200px;line-height:200px;text-align:center"
          >
            {{$t('commodity.noData')}}
          </div>
          <!--有商品-->
          <ul
            v-if="goodsFlag"
            :style="(data.col_type==='1' || data.col_type==='2')?'display: flex;flex-wrap: wrap;':data.col_type==='3'?'display: flex;flex-wrap: nowrap;':''+data.goods_module_bg==='1'?`background:${data.goods_bg_color}`:''"
          >
            <li
              v-for="(item,index) in data.goodsListData"
              :key="index"
              :style="data.col_type==='2'?'width:33%;':data.col_type==='0'?'width:100%':data.col_type==='4'?'width:100%;':''"
            >
              <div
                class="listClass"
                :style="data.if_radius==='1'?'border-radius:8px;':''+data.goods_module_style==='0'?'':data.goods_module_style==='1'?'box-shadow: 0 0 10px 3px #ddd;border: 1px solid transparent !important;':'border: 1px solid #eee !important;'"
              >
                <div
                  class="containter"
                  :style="(data.col_type==='1' || data.col_type==='2')?'display:block;height:auto':(data.col_type==='3' || data.col_type==='0')?'display: flex;flex-direction: column;height:auto':''"
                >
                  <div
                    class="commodityTop"
                    :style="data.col_type==='2'?'height:auto':data.col_type==='0'?'max-height:163px;height:auto;overflow:hidden':data.col_type==='4'?'margin-right:5px;max-height:145px':''"
                  >
                    <div class="label">
                      <!--左上角图形-->
                      <!--限时降价图形-->
                      <div
                        class="labelStyle1"
                        style="height:auto;width:44px"
                        v-if="(item.label?item.label.listPattern:-1)===1&&data.hide_label==='1'"
                      >
                        <span style="display: block;word-break: break-all;width: 34px;white-space: pre-wrap;">{{item.label.name}}</span>
                      </div>
                      <div
                        class="labelStyle2"
                        v-if="(item.label?item.label.listPattern:-1)===2&&data.hide_label==='1'"
                        :style="`background:linear-gradient(to right,${bgColor},${bgColor})`"
                      >
                        <span style="display: inline;">{{item.label.name}}</span>
                      </div>
                      <div
                        class="label newGoods"
                        v-if="(item.label?item.label.listPattern:-1)===3&&data.hide_label==='1'"
                      >
                        <span>{{item.label.name}}</span>
                      </div>
                      <div
                        class="labelStyle3"
                        v-if="(item.label?item.label.listPattern:-1)===4&&data.hide_label==='1'"
                        :style="`background:linear-gradient(to right,${bgColor},${bgColor})`"
                      >
                        <span style="display: inline-block;">{{item.label.name}}</span>
                      </div>
                    </div>
                    <!--活动类型标签-->
                    <div
                      class="goodsActivities"
                      :style="data.col_type==='2'?'width:100%':data.col_type==='0'?'width:100%;':data.col_type==='4'?'width:145px':data.col_type==='1'?'width:163px':data.col_type==='3'?'width:128px':''"
                    >
                      <!--多人开团`````````````````-->
                      <div
                        class="activity"
                        v-if="item.noShowActFlag === 1"
                      >
                        {{$t('commodity.setuProvince')}}{{item.discountPrice}}{{$t('commodity.element')}}
                      </div>
                      <!---->
                      <!--砍价````````````````````-->
                      <div
                        class="activity"
                        style="width:85px"
                        v-if="item.noShowActFlag === 3"
                      >
                        {{item.realPrice}}{{$t('commodity.yuanChopAway')}}
                      </div>
                      <!--限时降价`````````````````-->
                      <div
                        class="activity"
                        style="width:100%;border-radius:0"
                        v-if="item.noShowActFlag === 6"
                      >
                        {{$t('commodity.timeLimitedPrice')}}
                      </div>
                      <!--首单特惠-->
                      <div
                        class="activity"
                        style="width:100%;border-radius:0"
                        v-if="item.noShowActFlag === 18"
                      >
                        {{$t('commodity.newRecruitsExclusive')}}
                      </div>
                      <!--会员专享-->
                      <div
                        class="activity"
                        style="width:100%;border-radius:0;background: -webkit-linear-gradient(left,#eed9ad,#e7c38a)"
                        v-if="item.noShowActFlag === 22"
                      >
                        {{$t('commodity.memberExclusive')}}
                      </div>
                    </div>
                    <!--end-->
                    <img
                      :style="data.col_type==='2'?'width:100%;height:auto':data.col_type==='0'?'width:100%;height:auto;':data.col_type==='4'?'width:145px;height:145px;max-height:145px':data.col_type==='1'?'width:163px;height:163px;max-height:163px':data.col_type==='3'?'width:128px':''"
                      :src="item.goodsImg"
                    >
                  </div>
                  <div
                    class="commodityBottom"
                    :style="data.col_type!=='4'?'padding-top:0':''"
                  >
                    <div class="bottomHead">
                      <div
                        class="goodsNameClass"
                        v-if="data.hide_name==='1'"
                      >{{item.goodsName}}</div>
                      <div
                        class="activityContainer"
                        :style="(data.col_type==='4'||data.col_type==='1')?'display:flex':''"
                      >
                        <div
                          :style="((data.col_type==='4'||data.col_type==='1')&&indexC===1)?'margin-left:5px':'margin-top:5px'"
                          v-for="(itemC,indexC) in item.goodsActivities"
                          :key="indexC"
                          class="activitySpan"
                        >
                          <span :style="((data.col_type==='2'||data.col_type==='0')?'max-width:100%':data.col_type==='4'?'max-width:145px':data.col_type==='1'?'max-width:163px':data.col_type==='3'?'max-width:128px':'')+`;color:${bgColor};border-color:${bgColor}`">{{itemC.activityType===1?$t('commodity.assemble'):itemC.activityType===3?$t('commodity.bargain'):itemC.activityType===5?$t('commodity.seckill'):itemC.activityType===6?$t('commodity.limitedPriceReduction'):itemC.activityType===10?$t('commodity.advanceSale'):itemC.activityType===18?$t('commodity.firstSpecialOffer'):itemC.activityType===19?'支付有礼':(itemC.activityType===20)&&(itemC.actCode==='voucher')&&(itemC.useConsumeRestrict===1)?`${$t('commodity.full')}${itemC.leastConsume}${$t('commodity.reduce')}￥${itemC.denomination}`:(itemC.activityType===20)&&(itemC.actCode==='voucher')&&(itemC.useConsumeRestrict===0)?`${$t('commodity.volumeReduction')}￥${itemC.denomination}`:(itemC.activityType===20)&&(itemC.actCode==='discount')(itemC.useConsumeRestrict===1)?`${$t('commodity.full')}${itemC.leastConsume}${$t('commodity.hit')}${itemC.denomination}${$t('commodity.fracture')}`:(itemC.activityType===20)&&(itemC.actCode==='discount')(itemC.useConsumeRestrict===0)?`${$t('commodity.discountRoll')}${itemC.denomination}${$t('commodity.fracture')}`:itemC.activityType===21?`${$t('commodity.full')}${$t('commodity.reduce')}`:itemC.activityType===22?$t('commodity.membershipPrice'):itemC.activityType===23?$t('commodity.membershipExclusive'):''}}</span>
                        </div>
                      </div>

                    </div>
                    <div
                      class="bottomFooter"
                      :style="data.col_type!=='4' ?'display:flex;flex-direction: row;height:auto':''"
                    >
                      <span
                        :style="`color:${bgColor}`"
                        v-if="data.hide_price === '1'"
                      >￥{{Number(item.realPrice).toFixed(2)}}</span>
                      <span
                        style="text-decoration: line-through;color: #c0c0c0"
                        v-if="data.col_type!=='2'&&data.other_message==='1'"
                      >{{Number(item.linePrice).toFixed(2)}}</span>
                      <!--购买按钮-->
                      <i
                        class="iconfont icontianjia icon_font_size new_class"
                        :style="`color:${bgColor}`"
                        v-if="data.cart_btn === '1'&&data.cart_btn_choose==='0'"
                      ></i>
                      <i
                        class="iconfont icongouwuche1 icon_font_size new_class"
                        :style="`color:${bgColor}`"
                        v-if="data.cart_btn === '1'&&data.cart_btn_choose==='1'"
                      ></i>
                      <i
                        class="right_buy new_back"
                        :style="data.col_type==='2'?`width:44px;height:22px;line-height:22px;backgroundColor:${bgColor}`:`backgroundColor:${bgColor}`"
                        v-if="data.cart_btn === '1'&&data.cart_btn_choose==='2'"
                      >
                        {{$t('commodity.grabAtOnce')}}
                      </i>
                      <i
                        class="cart_buy"
                        :style="data.col_type==='2'?`width:44px;height:22px;line-height:22px;color:${bgColor};border-color:${bgColor}`:`color:${bgColor};border-color:${bgColor}`"
                        v-if="data.cart_btn === '1'&&data.cart_btn_choose==='3'"
                      >{{$t('commodity.purchase')}}</i>
                    </div>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>

      </div>
      <!--模块编辑区结束-->
      <div class="item_module_title">
        <span>{{$t('commodity.commodity')}}</span>
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
      // 模块私有
      goodsDataList: [
        {
          goodsName: '商品修改测试1',
          imgUrl: this.$imageHost + '/image/admin/0a2kFnVCg46fdTNw.jpeg',
          price: 112,
          labelUrl: this.$imageHost + '/image/admin/crop_2Slxp6DbLukZ1EJl.png',
          isNewGoods: '0'
        },
        {
          goodsName: '商品修改测试2',
          imgUrl: this.$imageHost + '/image/admin/0a2kFnVCg46fdTNw.jpeg',
          price: 544,
          labelUrl: this.$imageHost + '/image/admin/crop_2Slxp6DbLukZ1EJl.png',
          isNewGoods: '1'
        },
        {
          goodsName: '商品修改测试3',
          imgUrl: this.$imageHost + '/image/admin/crop_wlEjGAPFNMXl1EVr.jpeg',
          price: 323,
          labelUrl: this.$imageHost + '/image/admin/crop_2Slxp6DbLukZ1EJl.png',
          isNewGoods: '2'
        },
        {
          goodsName: '商品修改测试4',
          imgUrl: this.$imageHost + '/image/admin/crop_wlEjGAPFNMXl1EVr.jpeg',
          price: 334,
          labelUrl: this.$imageHost + '/image/admin/crop_2Slxp6DbLukZ1EJl.png',
          isNewGoods: '3'
        }
      ],
      goodsFlag: false,
      bgColor: '',
      noShowActFlag: null, // 当前图片底部显示的活动
      // 显示数据
      data: {
        goodsListData: []
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
    // 右侧模块点击传回中间当前高亮模块的数据
    backData: {
      handler (newData) {
        console.log(newData)
        if (newData) {
          this.data = newData
          this.$nextTick(() => {
            let arr = JSON.parse(JSON.stringify(newData.goodsListData))
            if (arr.length) {
              this.goodsFlag = true
              // 处理显示活动
              this.handleToActivity(newData)
            } else {
              this.goodsFlag = false
            }
          })
        }
        console.log(newData, this.goodsFlag)
      },
      immediate: true,
      deep: true
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
      this.bgColor = localStorage.getItem('V-backgroundColor') || 'rgb(255, 102, 102)'
      console.log(this.bgColor)
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
    },
    // 处理显示活动
    handleToActivity (newData) {
      console.log(newData)
      let goodSData = newData.goodsListData
      goodSData.forEach((item, index) => {
        console.log(item)
        // 处理每一个商品当前要显示的活动
        let arr = []
        if (item.goodsActivities.length > 2) {
          console.log(item)
          arr[0] = item.goodsActivities[0]
          arr[1] = item.goodsActivities[1]
          item.goodsActivities = arr
        }
        this.handleToEveryGoods(item, index, goodSData)
      })
    },
    // 处理每一个商品中要显示的活动
    handleToEveryGoods (item, index, goodSData) {
      // 秒杀 、一口价、支付有礼、加价购均不显示
      item.goodsActivities.forEach((itemC, indeC) => {
        console.log(itemC)
        switch (itemC.activityType) {
          case 1:
            this.data.goodsListData[index]['noShowActFlag'] = 1
            break
          case 3:
            this.data.goodsListData[index]['noShowActFlag'] = 3
            break
          case 6:
            this.data.goodsListData[index]['noShowActFlag'] = 6
            break
          case 18:
            this.data.goodsListData[index]['noShowActFlag'] = 18
            break
          case 23:
            this.data.goodsListData[index]['noShowActFlag'] = 23
            break

        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/style/admin/decorationModules.scss";
@import "@/assets/aliIcon/iconfont.scss";
.commodity {
  .hiddenTitle {
    height: 55px;
    line-height: 55px;
    padding-left: 10px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }
  .img_title {
    display: inline-block;
    height: 374px;
    width: 100%;
  }
  ul {
    li {
      width: 50%;
      padding: 10px 10px;
      .listClass {
        background: #fff;
      }
      .containter {
        display: flex;
        height: 145px;
        .commodityTop {
          position: relative;
          // height: 145px;
          .label {
            position: absolute;
            top: 0px;
            left: 0px;
            img {
              width: 60px;
            }
            .labelStyle1 {
              white-space: nowrap;
              position: absolute;
              left: 0px;
              top: 0px;
              height: 42.16px;
              background: url("../../../../../../../../assets/adminImg/label-three.png")
                no-repeat;
              background-size: 100% 100%;
              text-align: center;
              font-size: 12px;
              color: white;
              padding-left: 6px;
              padding-right: 5px;
              display: flex;
              align-items: center;
              justify-content: center;
              line-height: 15px;
              padding-bottom: 5px;
              overflow: hidden;
              word-break: break-all;
            }
            .labelStyle2 {
              position: absolute;
              left: 0px;
              top: 2px;
              text-align: center;
              width: 50px;
              height: 50px;
              display: flex;
              line-height: 15px;
              align-items: center;
              justify-content: center;
              border: 3px solid rgba(255, 255, 255, 0.2);
              border-radius: 60px;
              font-size: 12px;
              color: white;
              overflow: hidden;
              word-break: break-all;
              padding-left: 9px;
              padding-right: 8px;
              span {
                word-break: break-all;
                text-align: center;
              }
            }
            .labelStyle3 {
              position: absolute;
              left: 0px;
              top: 0px;
              height: 28px;
              font-size: 12px;
              color: white;
              border-bottom-right-radius: 14px;
              display: flex;
              align-items: center;
              justify-content: center;
              line-height: 15px;
              padding-left: 5px;
              padding-right: 5px;
              span {
                white-space: nowrap;
              }
            }
          }
          .goodsActivities {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            .activity {
              width: 120px;
              font-size: 12px;
              text-align: center;
              border-radius: 0 80px 80px 0;
              padding: 5px 0;
              // height: 25px;
              // line-height: 25px;
              background: -webkit-linear-gradient(
                left,
                rgba(91, 158, 164, 0.8),
                rgba(91, 158, 164, 1)
              );
              color: #fff;
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
          width: 100%;
          .bottomHead {
            // height: 35px;
            white-space: normal;
            word-break: break-all;
            margin-bottom: 10px;
            .goodsNameClass {
              overflow: hidden;
              white-space: nowrap;
              text-overflow: ellipsis;
              width: 103px;
            }
            div {
              white-space: nowrap;
            }
            .activityContainer {
              margin-top: 50px;
              div:nth-of-type(1) {
                font-size: 12px;

                span {
                  padding: 1px 4px;
                  border-radius: 2px;
                  border: 1px solid rgb(64, 128, 128);
                  color: rgb(64, 128, 128);
                }
              }
              div:nth-of-type(2) {
                font-size: 12px;
                margin-top: 5px;
                span {
                  padding: 1px 4px;
                  border-radius: 2px;
                  border: 1px solid rgb(64, 128, 128);
                  color: rgb(64, 128, 128);
                }
              }
            }

            span {
              display: inline-block;
              height: auto;
              word-break: break-all;
              white-space: pre-wrap;
            }
          }
          .bottomFooter {
            display: flex;
            justify-content: space-between;
            flex-direction: column;
            height: 40px;
            position: relative;
            width: 100%;
            min-height: 23px;
            span {
              margin: 0 2px 0px 0;
              min-height: 17px;
              &:nth-of-type(1) {
                color: rgb(64, 128, 128);
              }
            }
            .new_class {
              position: relative;
              top: 2px;
              font-size: 23px !important;
            }
            .icon_font_size {
              position: absolute;
              top: -3px;
              right: 5px;
            }
            .right_buy {
              width: 70px;
              height: 30px;
              text-align: center;
              line-height: 30px;
              background: rebeccapurple;
              color: white;
              font-size: 12px;
              border-radius: 15px;
              display: inline-block;
              position: absolute;
              top: -3px;
              right: 5px;
            }
            .cart_buy {
              width: 55px;
              height: 30px;
              text-align: center;
              line-height: 30px;
              border: 1px solid rebeccapurple;
              color: rebeccapurple;
              font-size: 12px;
              border-radius: 15px;
              background: white;
              display: inline-block;
              position: absolute;
              top: -3px;
              right: 5px;
            }
          }
        }
      }
    }
  }
}
</style>
