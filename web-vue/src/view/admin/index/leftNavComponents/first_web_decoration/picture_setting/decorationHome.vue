<template>
  <div class="decHomeContainer">
    <div class="decHomeMain">
      <div class="top">
        <span>组件库</span><span>可拖拽使用</span>
      </div>
      <div class="content">
        <div class="decLeft">
          <el-tabs
            :stretch='true'
            v-model="activeName"
          >
            <el-tab-pane
              label="图文组件"
              name="first"
            >
              <vue-scroll
                :ops="ops"
                style="height:520px"
              >
                <div class="picTextConDiv">
                  <div
                    v-for="(item,index) in pivTextConArr"
                    :key="index"
                    class="picTextConDivList"
                  >
                    <img
                      :src="item.imgUrl"
                      :alt="item.text"
                    >
                    <div style="height:24px;margin-top:-10px">{{item.text}}</div>
                  </div>
                </div>
              </vue-scroll>
            </el-tab-pane>
            <el-tab-pane
              label="商品组件"
              name="second"
            >
              <vue-scroll
                :ops="ops"
                style="height:520px"
              >
                <div class="picTextConDiv">
                  <div
                    v-for="(item,index) in goodsTextConArr"
                    :key="index"
                    class="picTextConDivList"
                  >
                    <img
                      :src="item.imgUrl"
                      :alt="item.text"
                    >
                    <div style="height:24px;margin-top:-10px">{{item.text}}</div>
                  </div>
                </div>
              </vue-scroll>
            </el-tab-pane>
            <el-tab-pane
              label="营销组件"
              name="third"
            >
              <div id="listLeft">
                <div
                  v-for="(item,key) in listLeft"
                  :key="key"
                  class="picTextConDivList third_drag"
                >
                  <img :src="item.name">
                  {{item.value}}

                </div>
              </div>

            </el-tab-pane>
          </el-tabs>
        </div>
        <div class="decMiddle">
          <div class="decTop"></div>
          <vue-scroll
            :ops="ops"
            style="height:510px"
          >
            <div class="decContent">

              <div
                class="drag_area"
                :class="zbFlag?'zwHeight':''"
              >
                <!--放这里-->
                <div
                  class="hereDaily"
                  :class="topAreaFlag?'setHere':''"
                  @mouseover="dragTopOver"
                  @mouseout="dragTopOut"
                  v-if="isDragging"
                >
                  <span :class="topAreaFlag?'setHereSpan':''">放这里</span>
                </div>
                <!--占位提示-->
                <div
                  class="zbTips"
                  v-if="!showModulesList.length"
                >
                  <div class="drag_notice">拖拽左侧模块进行装修</div>
                </div>
                <!--拖拽区域-->
                <draggable
                  class="list-group"
                  element="div"
                  v-model="showModulesList"
                  :options="dragOptions"
                  @start="handleToStart"
                  @end="handleToEnd"
                >
                  <!--模块列表-->
                  <div
                    v-for="(item,index) in showModulesList"
                    :key="index"
                  >
                    <!--会员列表模块-->
                    <div @click="handleToClickModule(index)">
                      <MembershipCard
                        v-show="item===1"
                        :index="1"
                        :flag="index"
                      />
                    </div>

                    <!--优惠卷模块-->
                    <div @click="handleToClickModule(index)">
                      <Coupon
                        v-show="item===2"
                        :index="2"
                        :flag="index"
                      />
                    </div>

                  </div>
                  <!--模块列表结束-->
                </draggable>
              </div>

            </div>
          </vue-scroll>
        </div>
        <!-- <div class="decRight">
          3
        </div> -->
      </div>
    </div>
    <!--保存-->
    <div class="footer">
      <div>
        <el-button
          type="primary"
          size="small"
          @click="handleToFooter(0)"
        >保存并发布</el-button>
        <el-button
          size="small"
          @click="handleToFooter(1)"
        >保存为草稿</el-button>
        <el-button
          size="small"
          @click="handleToFooter(2)"
        >预览效果</el-button>
      </div>
    </div>
  </div>
</template>
<script>

import vuescroll from 'vuescroll'
import draggable from 'vuedraggable'
import Vue from 'vue'
import 'vuescroll/dist/vuescroll.css'
import $ from 'jquery'
Vue.use(vuescroll)
require('webpack-jquery-ui')
require('webpack-jquery-ui/css')
export default {
  components: {
    vuescroll,
    draggable,
    MembershipCard: () => import('./decorationModules/membershipCard'),
    Coupon: () => import('./decorationModules/Coupon')
  },
  data () {
    return {
      ops: {
        vuescroll: {
          mode: 'native'
        },
        scrollPanel: {},
        rail: {
          keepShow: true
        },
        bar: {
          hoverStyle: true,
          onlyShowBarOnScroll: false, // 是否只有滚动的时候才显示滚动条
          background: '#eee'
        }
      },
      activeName: 'third',
      pivTextConArr: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_rotation.png',
          text: '轮播图'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_guide.png',
          text: '图片导航'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_img_advertist.png',
          text: '图片广告'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_imgwindow.png',
          text: '魔方多图'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/hot-area.png',
          text: '图片热区'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/text_image.png',
          text: '左图右文'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_text.png',
          text: '文本模块'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_rich_text.png',
          text: '富文本'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_helpblank.png',
          text: '辅助空白'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_helpline.png',
          text: '辅助线'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_title.png',
          text: '标题模块'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/icon_video.png',
          text: '视频模块'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/shop_announce.png',
          text: '店铺公告'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/official_account.png',
          text: '公众号'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/service.png',
          text: '客服模块'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/drag_phone.png',
          text: '电话模块'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_navigation.png',
          text: '店招设置'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/icon_map.png',
          text: '地图模块'
        }
      ],
      goodsTextConArr: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/deco_goods.png',
          text: '商品'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_search.png',
          text: '商品搜索'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/goods_group.png',
          text: '商品分组'
        }
      ],
      marketingTextConArr: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/deco_card.png',
          text: '会员卡'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/deco_voucher.png',
          text: '优惠卷'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/bargain.png',
          text: '砍价'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/icon_integral_deco.png',
          text: '积分兑换'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/secKill.png',
          text: '秒杀'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/fight_group.png',
          text: '拼团抽奖'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/pin_integration.png',
          text: '瓜分积分'
        }
      ],
      isDragging: false,
      listLeft: [{
        name: this.$imageHost + '/image/admin/new_shop_beautify/deco_card.png',
        value: '会员卡'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/deco_voucher.png',
        value: '优惠卷'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/bargain.png',
        value: '砍价'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/icon_integral_deco.png',
        value: '积分兑换'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/secKill.png',
        value: '秒杀'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/fight_group.png',
        value: '拼团抽奖'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/pin_integration.png',
        value: '瓜分积分'
      }],
      showModulesList: [],
      insertModulesId: -1,
      drag_flag: false,
      oldIndex: -1,
      newIndex: -1,
      oldElement: null,
      zbFlag: false,
      topAreaFlag: false,
      nowRightShowIndex: null
    }
  },
  watch: {
    showModulesList (newData) {
      console.log(newData)
      if (newData.length === 1) {
        this.nowRightShowIndex = newData[0]
      }
      if (newData.length) {
        this.zbFlag = true
      } else {
        this.zbFlag = false
      }
    },
    nowRightShowIndex (newData) {
      console.log(newData, this.activeName)
    }
  },
  computed: {
    dragOptions () {
      return {
        animation: 0,
        group: {
          name: 'description',
          pull: 'clone',
          put: false
        },
        forceFallback: true,
        fallbackClass: 'active',
        sort: false
      }
    }
  },
  mounted () {
    // 初始化数据
    this.$nextTick(() => {
      this.init_drag_event()
    })
  },
  methods: {
    // 初始化拖拽事件
    init_drag_event () {
      // 模块icon点击数据接收统一处理
      this.handleTohandleIconClick()
      // 中间区域拖拽插入数据处理
      this.handleToMiddleDragData()
      let this_ = this
      // 左侧模块向中间区域拖拽
      $('.third_drag').draggable({
        appendTo: '.decLeft',
        helper: 'clone',
        start: function () {
        },
        drag: function (ev, ui) {
          // console.log($(ui.helper).offset())
          // console.log(ev)
          this_.highlignt_row_item($(ui.helper).offset())
        },
        stop: function () {
          let last = this_.showModulesList
          setTimeout(() => {
            this_.$http.$emit('decCard', last)
          }, 50)
          $('.modules').removeClass('placeholder')
        },
        zIndex: 10000 // 拖动位置在拖放区域上方
      })
      this.handleToAcceptDrag()
      // 接收当前高亮模块参数
      this.$http.$on('nowHightLightModules', res => {
        console.log(res)
        this.nowRightShowIndex = res
      })
    },
    // 左侧模块拖拽开始start处理函数
    highlignt_row_item (pos) {
      let this_ = this
      let p = $('.drag_area').offset()
      console.log(p, '--', pos, '--', $('.drag_area').width(), $('.drag_area').height())
      if (pos.left > p.left && pos.top > p.top &&
        pos.left < p.left + $('.drag_area').width() &&
        pos.top < p.top + $('.drag_area').height()) {
        // console.log('ssssss')

        $('.modules').each(function (idx, item) {
          p = $(this).offset()
          if (pos.left > p.left && pos.top > p.top &&
            pos.left < p.left + $(this).width() &&
            pos.top < p.top + $(this).height()
          ) {
            this_.insertModulesId = $(this)[0].__vue__.flag
            // console.log($(this)[0].__vue__.flag)
            $('.modules').removeClass('placeholder')
            $(this).addClass('placeholder')
          }
        })
      }
    },
    // 中间模块拖拽接收
    handleToAcceptDrag () {
      let this_ = this
      // console.log('test')
      $('.decContent').droppable({
        activeClass: 'ui-state-default',
        hoverClass: 'ui-state-hover',
        accept: '.third_drag',
        drop: function (event, ui) {
          // console.log(ui.draggable[0].innerText)
          // console.log(this_.dataId)
          console.log(this_.insertModulesId, '--', this_.showModulesList)
          let insert = this_.insertModulesId + 1
          // 判断id是否为-1，若是则不插入数组中
          switch (ui.draggable[0].innerText) {
            case '会员卡':
              if (this_.insertModulesId === -1) {
                this_.showModulesList.push(1)
              } else {
                this_.showModulesList.splice(insert, 0, 1)
                console.log(this_.showModulesList)
              }

              break
            case '优惠卷':
              if (this_.insertModulesId === -1) {
                this_.showModulesList.push(2)
              } else {
                this_.showModulesList.splice(insert, 0, 2)
              }
          }
        }
      })
    },
    // 中间区域元素开始拖动时处理函数
    handleToStart ({ oldIndex }) {
      console.log(oldIndex)
      let newArr = JSON.parse(JSON.stringify(this.showModulesList))
      this.oldElement = newArr[oldIndex]
      this.oldIndex = oldIndex
      console.log(newArr.length, oldIndex)
      // let arrLength = newArr.length - 1
      newArr[oldIndex] = -1
      console.log(newArr)
      this.showModulesList = newArr

      let data = newArr
      setTimeout(() => {
        this.$http.$emit('decCard', data, oldIndex)
      }, 100)

      this.isDragging = true
    },
    // 中间区域元素停止拖动是处理函数
    handleToEnd (e) {
      let this_ = this
      console.log(this.oldIndex, this.newIndex, this.oldElement)
      let newArr = JSON.parse(JSON.stringify(this.showModulesList))
      let insertIndex = this.newIndex + 1
      if (this.topAreaFlag) {
        newArr.unshift(this.oldElement)
      } else {
        newArr.splice(insertIndex, 0, this.oldElement)
      }

      console.log(newArr)
      let newArrMiddle = JSON.parse(JSON.stringify(newArr))

      newArr.forEach((item, index) => {
        if (item === -1) {
          newArrMiddle.splice(index, 1)
        }
      })
      this.showModulesList = newArrMiddle
      this.isDragging = false
      let data = newArrMiddle
      setTimeout(() => {
        this_.$http.$emit('decCard', data, -1)
      }, 100)
    },
    // 中间区域模块icon点击数据接收统一处理
    handleTohandleIconClick () {
      console.log(1111)
      this.$http.$on('handleDragIconClick', ({ direction, flag }) => {
        let newArr = JSON.parse(JSON.stringify(this.showModulesList))
        console.log(newArr, flag)
        switch (direction) {
          case 'up':
            console.log(newArr, '--', flag)
            // 顶部判断
            if (flag === 0) return
            let temp = newArr[(flag - 1)]
            newArr[(flag - 1)] = newArr[flag]
            newArr[flag] = temp
            let arrFliter = newArr.filter(item => {
              return item
            })
            // 改变边框
            let index = flag - 1
            console.log(newArr)
            this.showModulesList = arrFliter
            let data = this.showModulesList
            this.$http.$emit('decCard', data, -1)
            this.$http.$emit('modulesClick', index)
            console.log(newArr, '--' + this.showModulesList)
            break
          case 'down':
            console.log(newArr, '--', flag, '123123123')
            let temp2 = newArr[(flag + 1)]
            // 底部判断
            if ((newArr.length - 1) === flag) return
            newArr[(flag + 1)] = newArr[flag]
            newArr[flag] = temp2
            let arrFliterD = newArr.filter(item => {
              return item
            })
            let indexD = flag + 1
            console.log(arrFliterD)
            this.showModulesList = arrFliterD
            let dataD = this.showModulesList
            this.$http.$emit('decCard', dataD, -1)
            this.$http.$emit('modulesClick', indexD)
            break
          case 'delete':
            console.log(newArr, flag)
            newArr.splice(flag, 1)
            console.log(newArr)
            this.showModulesList = newArr
            this.$http.$emit('modulesClick', (newArr.length - 1))
            break
        }
      })
    },
    // 中间区域拖拽插入数据处理
    handleToMiddleDragData () {
      this.$http.$on('middleDragData', res => {
        // console.log(res)
        this.newIndex = res
      })
    },
    // 顶部滑动
    dragTopOver () {
      this.topAreaFlag = true
    },
    // 顶部划出
    dragTopOut () {
      this.topAreaFlag = false
    },
    // 底部点击统一处理
    handleToFooter (flag) {
      switch (flag) {
        case 0:
          break
        case 1:
          break
        case 2:
      }
    },
    handleToClickModule (index) {
      console.log(index)
      this.$http.$emit('modulesClick', index)
    }
  }
}
</script>
<style scoped lang="scss">
.decHomeContainer {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .decHomeMain {
    height: 100%;
    position: relative;
    background-color: #fff;
    padding: 10px 20px 0 20px;
    .top {
      margin-bottom: 12px;
      span:nth-of-type(2) {
        padding-left: 5px;
        color: #999;
      }
    }
    .content {
      display: flex;
      justify-content: flex-start;
      .decLeft,
      .decMiddle {
        width: 254px;
        margin: 2px;
        // padding: 0 2px;
        .hereDaily {
          height: 5px;
          span {
            display: none;
          }
          .setHereSpan {
            display: block;
          }
        }
        .setHere {
          height: 40px;
          text-align: center;
          line-height: 40px;
          background-color: #8baeff;
          font-size: 14px;
          color: #fff;
          border: 1px dashed #2589ff;
        }
        .picTextConDivList {
          float: left;
          border: 1px solid #e5e5e5;
          background: #f8f8f8;
          border-radius: 5px;
          line-height: 24px;
          width: 100px;
          height: 84px;
          margin: 5px 0 10px 14px;
          cursor: move;
          position: relative;
          display: flex;
          justify-content: center;
          align-items: center;
          flex-direction: column;
          z-index: 10000;
        }
      }
      .decMiddle {
        width: 385px;
        border: 1px solid #e5e5e5;
        margin-left: 30px;
        .decTop {
          height: 64px;
          background: url(../../../../../../assets/adminImg/page_name.png)
            no-repeat;
        }
        .decContent {
          height: 510px;
          background: #fff;
          position: relative;
          // .zwHeight {
          //   // height: 100%;
          // }
          .zbTips {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            .drag_notice {
              width: 90%;
              margin: 10px auto 0;
              border: 1px dashed #d0d0d0;
              background: #fff;
              padding: 50px 0;
              text-align: center;
              color: #9f9f9f;
              font-size: 15px;
            }
          }
        }
      }
    }
  }
  .list-group {
    height: 100%;
  }
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
  }
}
.active {
  z-index: 100000 !important;
}
</style>
