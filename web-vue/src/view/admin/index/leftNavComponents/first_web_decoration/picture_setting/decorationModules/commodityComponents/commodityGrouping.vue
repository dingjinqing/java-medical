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
      <!--菜单样式为顶部展示商品分组-->
      <div
        class="commodityGrouping"
        v-if="data.position_style==='0'"
      >
        <div class="commodityGroupingMain">
          <div
            class="groupList"
            v-for="(item,index) in showNav.filter((item,index)=>data.group_display==='0'?index!==0:index!==-1)"
            :class="index===4?'greater':''"
            :key="index"
            :style="index===0?'border-bottom: 1px solid rgb(177, 78, 105);color:rgb(177, 78, 105)':index>4?'display:none':''"
          >
            {{item}}

          </div>

        </div>
        <ul
          v-if="data.sort_group_arr.length"
          :style="((data.shop_style==='1' || data.shop_style==='2')?'display: flex;flex-wrap: wrap;':data.shop_style==='4'?'display:flex':'')+(data.goods_module_bg==='1'?`;background:${data.goods_bg_color}`:'')"
        >
          <li
            v-for="(item,index) in goodsData"
            :key="index"
            :style="data.shop_style==='2'?'width:33.33%;':data.shop_style==='4'?'width:158px':data.shop_style==='0'?'width:100%;':data.shop_style==='3'?'width:100%':''"
          >
            <div
              class="listClass"
              :style="(data.module_style==='2'?'box-shadow: 0 0 10px 3px #ddd;border: 1px solid transparent !important;':data.module_style==='3'?'border: 1px solid #eee !important;':'')+(data.if_radius==='1'?'border-radius:8px;':'')"
            >
              <div
                class="containter"
                :style="(data.shop_style==='1' || data.shop_style==='2')?'display:block;height:auto': data.shop_style==='0'?'display: flex;flex-direction: column;height:auto':data.shop_style==='3'?'display:flex':data.shop_style==='4'?'display: flex;flex-direction: column;height:auto':''"
              >
                <div
                  class="commodityTop"
                  :style="data.shop_style==='2'?'height:auto':data.shop_style==='4'?'width:140px;height:140px;':data.shop_style==='1'?'margin-right:5px;':''"
                >
                  <div class="label">
                    <!--左上角图形-->
                    <!--限时降价图形-->
                    <div
                      class="labelStyle1"
                      style="height:auto;width:44px"
                      v-if="(item.label?item.label.listPattern:-1)===1"
                    >
                      <span style="display: block;word-break: break-all;width: 34px;white-space: pre-wrap;">{{item.label.name}}</span>
                    </div>
                    <div
                      class="labelStyle2"
                      v-if="(item.label?item.label.listPattern:-1)===2"
                      :style="`background:linear-gradient(to right,${bgColor},${bgColor})`"
                    >
                      <span style="display: inline;">{{item.label.name}}</span>
                    </div>
                    <div
                      class="label newGoods"
                      v-if="(item.label?item.label.listPattern:-1)===3"
                    >
                      <span>{{item.label.name}}</span>
                    </div>
                    <div
                      class="labelStyle3"
                      v-if="(item.label?item.label.listPattern:-1)===4"
                      :style="`background:linear-gradient(to right,${bgColor},${bgColor})`"
                    >
                      <span style="display: inline-block;">{{item.label.name}}</span>
                    </div>
                  </div>
                  <img
                    :style="data.shop_style==='0'?'width:100%;height:auto':data.shop_style==='4'?'width:100%;height:auto;':data.shop_style==='2'?'width:102px;height:102px;':data.shop_style==='1'?'width:163px;height:163px;max-height:163px':data.shop_style==='3'?'width:128px':''"
                    :src="item.goodsImg"
                  >
                </div>
                <div
                  class="commodityBottom"
                  :style="data.shop_style==='3'?'padding-left:10px':data.shop_style!=='1'?'padding-top:0':''"
                >
                  <div class="bottomHead">
                    <div
                      class="goodsNameClass"
                      v-if="data.show_name === '0'"
                    >{{item.goodsName}}</div>
                    <div :style="data.show_name!=='0'?'height:14px':''"></div>
                    <div
                      class="activityContainer"
                      :style="(data.shop_style==='0')?'display:flex;margin-top:0':data.shop_style==='1'?'display:flex;margin-top:0':data.shop_style==='2'?'display:flex':data.shop_style==='3'?'margin-top:50px;display:flex':'display:flex'"
                    >
                      <div
                        :style="indexC===0?'':'margin-top:0'+((data.shop_style==='0'||data.shop_style==='1')&&indexC===1)?'margin-left:5px':data.shop_style!=='3'?'margin-top:5px;margin-top:0':''"
                        v-for="(itemC,indexC) in item.goodsActivities"
                        :key="indexC"
                        class="activitySpan"
                      >
                        <span
                          v-if="itemC.activityType!==0"
                          :style="((data.shop_style==='2'||data.shop_style==='4')?'max-width:100%;white-space: nowrap;margin-top:0;':data.shop_style==='1'?'max-width:145px':data.shop_style==='1'?'max-width:163px':data.shop_style==='3'?'max-width:128px':'')+`;color:${bgColor};border-color:${bgColor}`"
                        >{{itemC.activityType===1?$t('commodity.assemble'):itemC.activityType===3?$t('commodity.bargain'):itemC.activityType===5?$t('commodity.seckill'):itemC.activityType===6?$t('commodity.limitedPriceReduction'):itemC.activityType===10?$t('commodity.advanceSale'):itemC.activityType===18?$t('commodity.firstSpecialOffer'):(itemC.activityType===19)&&(itemC.actCode==='voucher')&&(itemC.useConsumeRestrict===1)?`${$t('commodity.full')}${itemC.leastConsume}${$t('commodity.reduce')}￥${itemC.denomination}`:(itemC.activityType===19)&&(itemC.actCode==='voucher')&&(itemC.useConsumeRestrict===0)?`${$t('commodity.volumeReduction')}￥${itemC.denomination}`:(itemC.activityType===19)&&(itemC.actCode==='discount')(itemC.useConsumeRestrict===1)?`${$t('commodity.full')}${itemC.leastConsume}${$t('commodity.hit')}${itemC.denomination}${$t('commodity.fracture')}`:(itemC.activityType===19)&&(itemC.actCode==='discount')(itemC.useConsumeRestrict===0)?`${$t('commodity.discountRoll')}${itemC.denomination}${$t('commodity.fracture')}`:itemC.activityType===20?`${$t('commodity.full')}${$t('commodity.reduce')}`:itemC.activityType===21?$t('commodity.membershipPrice'):itemC.activityType===22?'会员专享':''}}</span>
                      </div>
                    </div>

                  </div>
                  <div
                    class="bottomFooter"
                    :style="data.shop_style==='3'?'display:flex;flex-direction: row;':'display:flex;flex-direction: row;height:auto'"
                  >
                    <span
                      :style="`color:${bgColor};`"
                      v-if="data.show_price==='0'"
                    >￥{{Number(item.realPrice).toFixed(2)}}</span>
                    <span
                      style="text-decoration: line-through;color: #c0c0c0"
                      v-if="data.show_market==='1'&&data.other_message==='0'"
                    >{{Number(item.linePrice).toFixed(2)}}</span>
                    <span
                      style="text-decoration: line-through;color: #c0c0c0"
                      v-if="data.show_market==='2'&&data.other_message==='0'"
                    >{{Number(item.goodsSaleNum).toFixed(2)}}</span>
                    <span
                      style="text-decoration: line-through;color: #c0c0c0"
                      v-if="data.show_market==='3'&&data.other_message==='0'"
                    >{{Number(item.goodsNumber).toFixed(2)}}</span>
                    <!--购买按钮-->
                    <i
                      class="iconfont icontianjia icon_font_size new_class"
                      :style="`color:${bgColor};`+`${data.shop_style==='3'?'position:static':''}`"
                      v-if="data.cart_btn==='0'&&data.cart_btn_choose === '0'"
                    ></i>
                    <i
                      class="iconfont icongouwuche1 icon_font_size new_class"
                      :style="`color:${bgColor};`+`${data.shop_style==='3'?'position:static':''}`"
                      v-if="data.cart_btn==='0'&&data.cart_btn_choose === '1'"
                    ></i>
                    <i
                      class="right_buy new_back"
                      :style="data.shop_style==='2'?`width:44px;height:22px;line-height:22px;backgroundColor:${bgColor}`:`backgroundColor:${bgColor};`+`${data.shop_style==='3'?'position:static':''}`"
                      v-if="data.cart_btn==='0'&&data.cart_btn_choose==='2'"
                    >
                      {{$t('commodity.grabAtOnce')}}
                    </i>
                    <i
                      class="cart_buy"
                      :style="data.shop_style==='2'?`width:44px;height:22px;line-height:22px;color:${bgColor};border-color:${bgColor}`:`color:${bgColor};border-color:${bgColor};`+`${data.shop_style==='3'?'position:static':''}`"
                      v-if="data.cart_btn==='0'&&data.cart_btn_choose==='3'"
                    >{{$t('commodity.purchase')}}</i>
                  </div>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <!--菜单样式为左侧展示商品分组-->
      <div
        v-else
        class="leftShowModule commodityGrouping"
      >
        <!--左侧导航条-->
        <div class="left">
          <div
            class="navList"
            v-for="(item,index) in showNav.filter((item,index)=>index!==0)"
            :key="index"
            :style="!index?'background: #fff;':''"
          >
            {{item}}
          </div>
        </div>
        <!--右侧商品列表-->
        <div class="right">
          <div class="rightTop">
            {{showNav.filter((item,index)=>index!==0)[0]}}
          </div>
          <ul
            v-if="data.sort_group_arr.length"
            :style="(data.goods_module_bg==='1'?`background:${data.goods_bg_color}`:'')+';padding: 10px;overflow: hidden;'"
          >
            <li
              v-for="(item,index) in goodsData"
              :key="index"
              style="width:100%"
            >
              <div
                class="listClass"
                :style="(data.module_style==='2'?'box-shadow: 0 0 10px 3px #ddd;border: 1px solid transparent !important;':data.module_style==='3'?'border: 1px solid #eee !important;':'')+(data.if_radius==='1'?'border-radius:8px;':'')+';overflow:hidden'"
              >
                <div
                  class="containter"
                  style="display:flex;padding:0;height:128px"
                >
                  <div class="commodityTop">
                    <div class="label">
                      <!--左上角图形-->
                      <!--限时降价图形-->
                      <div
                        class="labelStyle1"
                        style="height:auto;width:44px"
                        v-if="(item.label?item.label.listPattern:-1)===1"
                      >
                        <span style="display: block;word-break: break-all;width: 34px;white-space: pre-wrap;">{{item.label.name}}</span>
                      </div>
                      <div
                        class="labelStyle2"
                        v-if="(item.label?item.label.listPattern:-1)===2"
                        :style="`background:linear-gradient(to right,${bgColor},${bgColor})`"
                      >
                        <span style="display: inline;">{{item.label.name}}</span>
                      </div>
                      <div
                        class="label newGoods"
                        v-if="(item.label?item.label.listPattern:-1)===3"
                      >
                        <span>{{item.label.name}}</span>
                      </div>
                      <div
                        class="labelStyle3"
                        v-if="(item.label?item.label.listPattern:-1)===4"
                        :style="`background:linear-gradient(to right,${bgColor},${bgColor})`"
                      >
                        <span style="display: inline-block;">{{item.label.name}}</span>
                      </div>
                    </div>
                    <img
                      style="width:128px"
                      :src="item.goodsImg"
                    >
                  </div>
                  <div
                    class="commodityBottom"
                    style="padding-left:10px"
                  >
                    <div class="bottomHead">
                      <div
                        class="goodsNameClass"
                        v-if="data.show_name==='0'"
                      >{{item.goodsName}}</div>
                      <div :style="data.show_name !=='0'?'height:14px':''"></div>
                      <div
                        class="activityContainer"
                        :style="'display:flex;'+(!data.show_name==='0'?'margin: 10px 0 50px;':'margin-top:50px;')"
                      >
                        <div
                          :style="indexC===0?'margin-right:5px;':'margin-top:0px;'"
                          v-for="(itemC,indexC) in item.goodsActivities"
                          :key="indexC"
                          class="activitySpan"
                        >
                          <span
                            v-if="itemC.activityType!==0"
                            :style="((data.shop_style==='2'||data.shop_style==='4')?'max-width:100%;white-space: nowrap;margin-top:0;':data.shop_style==='1'?'max-width:145px':data.shop_style==='1'?'max-width:163px':data.shop_style==='3'?'max-width:128px':'')+`;color:${bgColor};border-color:${bgColor}`"
                          >{{itemC.activityType===1?$t('commodity.assemble'):itemC.activityType===3?$t('commodity.bargain'):itemC.activityType===5?$t('commodity.seckill'):itemC.activityType===6?$t('commodity.limitedPriceReduction'):itemC.activityType===10?$t('commodity.advanceSale'):itemC.activityType===18?$t('commodity.firstSpecialOffer'):(itemC.activityType===19)&&(itemC.actCode==='voucher')&&(itemC.useConsumeRestrict===1)?`${$t('commodity.full')}${itemC.leastConsume}${$t('commodity.reduce')}￥${itemC.denomination}`:(itemC.activityType===19)&&(itemC.actCode==='voucher')&&(itemC.useConsumeRestrict===0)?`${$t('commodity.volumeReduction')}￥${itemC.denomination}`:(itemC.activityType===19)&&(itemC.actCode==='discount')(itemC.useConsumeRestrict===1)?`${$t('commodity.full')}${itemC.leastConsume}${$t('commodity.hit')}${itemC.denomination}${$t('commodity.fracture')}`:(itemC.activityType===19)&&(itemC.actCode==='discount')(itemC.useConsumeRestrict===0)?`${$t('commodity.discountRoll')}${itemC.denomination}${$t('commodity.fracture')}`:itemC.activityType===20?`${$t('commodity.full')}${$t('commodity.reduce')}`:itemC.activityType===21?$t('commodity.membershipPrice'):itemC.activityType===22?'会员专享':''}}</span>
                        </div>
                      </div>

                    </div>
                    <div
                      class="bottomFooter"
                      style="display:flex;width:100%;flex-direction:row;width:123px"
                    >
                      <span
                        :style="`color:${bgColor};`"
                        v-if="data.show_price==='0'"
                      >￥{{Number(item.realPrice).toFixed(2)}}</span>
                      <span
                        v-if="data.show_price!=='0'"
                        style="height:14px"
                      ></span>
                      <span
                        style="text-decoration: line-through;color: #c0c0c0"
                        v-if="data.show_market==='1'&&data.other_message==='0'"
                      >{{Number(item.linePrice).toFixed(2)}}</span>
                      <span
                        style="text-decoration: line-through;color: #c0c0c0"
                        v-if="data.show_market==='2'&&data.other_message==='0'"
                      >{{Number(item.goodsSaleNum).toFixed(2)}}</span>
                      <span
                        style="text-decoration: line-through;color: #c0c0c0"
                        v-if="data.show_market==='3'&&data.other_message==='0'"
                      >{{Number(item.goodsNumber).toFixed(2)}}</span>
                      <!--购买按钮-->
                      <i
                        class="iconfont icontianjia icon_font_size new_class"
                        :style="`color:${bgColor};`+'position:static'"
                        v-if="data.cart_btn==='0'&&data.cart_btn_choose === '0'"
                      ></i>
                      <i
                        class="iconfont icongouwuche1 icon_font_size new_class"
                        :style="`color:${bgColor};`+'position:static'"
                        v-if="data.cart_btn==='0'&&data.cart_btn_choose === '1'"
                      ></i>
                      <i
                        class="right_buy new_back"
                        style="`position:static;backgroundColor:${bgColor};`"
                        v-if="data.cart_btn==='0'&&data.cart_btn_choose==='2'"
                      >
                        {{$t('commodity.grabAtOnce')}}
                      </i>
                      <i
                        class="cart_buy"
                        :style="`position:static;color:${bgColor};border-color:${bgColor};`"
                        v-if="data.cart_btn==='0'&&data.cart_btn_choose==='3'"
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
        <span>商品分组</span>
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
import { getGoodsGroupData } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting.js'
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  mixins: [decMixins],
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
      data: {
        sort_group_arr: []
      },
      groupTextData: ['全部', '分组一', '分组二', '分组三', '分组四'],
      showNav: [],
      bgColor: '',
      goodsData: [] // 商品信息
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
          console.log(newData)
          let turnToString = this.handleToTurnNumToStr(newData)
          console.log(turnToString)
          this.$nextTick(() => {
            turnToString.shop_style = JSON.stringify(Number(turnToString.shop_style) - 1)
            console.log(turnToString.shop_style)
            this.data = turnToString
            this.handleToInit(this.data)
          })

          // 处理初始数据
        }
      },
      immediate: true,
      deep: true
    }
  },
  mounted () {
    this.langDefault() // 初始化语言
    this.bgColor = localStorage.getItem('V-backgroundColor') || 'rgb(255, 102, 102)'
    // 初始化数据
    this.defaultData()
  },
  methods: {
    // 处理初始数据
    handleToInit (data) {
      let sortGroupArr = data.sort_group_arr
      console.log(sortGroupArr, data)
      let length = sortGroupArr.length
      if (length) {
        let arr = [this.groupTextData[0]]
        console.log(this.groupTextData[0])
        sortGroupArr.forEach((item, index) => {
          arr.push(item.sort_name)
        })
        this.showNav = arr
      } else {
        this.showNav = this.groupTextData
      }
      // 商品数据获取
      this.handleToRequestGoodsData(data)
      console.log(this.showNav)
    },
    // 商品数据请求
    handleToRequestGoodsData (data) {
      console.log(data)
      let groupData = JSON.parse(JSON.stringify(data.sort_group_arr))
      let arr = []
      groupData.forEach((item, idnex) => {
        let obj = {}
        obj['sort_id'] = item.sort_id
        obj['sort_type'] = item.sort_type
        obj['group_goods_id'] = item.group_goods_id ? item.group_goods_id : null
        obj['is_all'] = item.is_all
        arr.push(obj)
      })
      console.log(arr)
      if (!arr.length) return
      let obj = {
        'position_style': Number(data.position_style),
        'group_display': Number(data.group_display),
        'sort_group_arr': arr
      }
      getGoodsGroupData(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.goodsData = res.content
          // 处理活动
          res.content.forEach((item, index) => {
            console.log(item)
            // 处理每一个商品当前要显示的活动
            let arr = []
            if (item.goodsActivities.length > 2) {
              console.log(item)
              arr[0] = item.goodsActivities[0]
              arr[1] = item.goodsActivities[1]
              item.goodsActivities = arr
            }
            this.handleToActivities(item, index)
          })
          console.log(this.goodsData)
        }
      })
    },
    // 处理活动
    handleToActivities (item, index) {
      item.goodsActivities.forEach((itemC, indeC) => {
        console.log(itemC)
        switch (itemC.activityType) {
          case 1:
            this.goodsData[index]['noShowActFlag'] = 1
            break
          case 3:
            this.goodsData[index]['noShowActFlag'] = 3
            break
          case 6:
            this.goodsData[index]['noShowActFlag'] = 6
            break
          case 18:
            this.goodsData[index]['noShowActFlag'] = 18
            break
          case 23:
            this.goodsData[index]['noShowActFlag'] = 23
            break
        }
      })
    },
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
@import "@/assets/aliIcon/iconfont.scss";
.commodityGrouping {
  padding: 5px 0px;
  .commodityGroupingMain {
    max-width: 379px;
    overflow: hidden;
    height: 44px;
    line-height: 44px;
    // border: 1px solid #eeeeee;
    width: 100%;
    display: flex;
    .groupList {
      height: 44px;
      line-height: 44px;
      text-align: center;
      color: #333;
      border-top: 1px solid #eeeeee;
      border-bottom: 1px solid #eeeeee;
      width: 82px;
      flex: 1;
    }
    .greater {
      flex: none;
      text-align: right;
      width: 28px;
    }
  }
  ul {
    overflow: hidden;
    padding: 5px;
    li {
      width: 50%;
      .listClass {
        background: #fff;
        margin: 5px;
      }
      .containter {
        display: flex;
        height: 145px;
        padding: 5px;

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
          padding-top: 10px;
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
                // margin-top: 5px;
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
.leftShowModule {
  display: flex;
  height: 506px;
  .left {
    width: 23%;
    height: 100%;
    overflow-y: hidden;
    background: #f5f5f5;
    .navList {
      width: 100%;
      font-size: 14px;
      color: #666;
      text-align: center;
      padding: 17px 13px 17px 11px;
    }
  }
  .right {
    flex: 1;
    .rightTop {
      width: 100%;
      padding: 17px 15px 5px 13px;
      font-size: 16px;
    }
  }
}
</style>
