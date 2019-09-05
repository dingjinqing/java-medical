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

              <div class="drag_area">
                <!--模块列表-->
                <div
                  v-for="(item,index) in showModulesList"
                  :key="index"
                >
                  <!--会员列表模块-->

                  <MembershipCard
                    v-show="item===1"
                    :index="1"
                    :flag="index"
                  />

                  <!--优惠卷模块-->
                  <Coupon
                    v-show="item===2"
                    :index="2"
                    :flag="index"
                  />
                </div>
                <!--模块列表结束-->
              </div>

            </div>
          </vue-scroll>
        </div>
        <!-- <div class="decRight">
          3
        </div> -->
      </div>
    </div>

  </div>
</template>
<script>
import vuescroll from 'vuescroll'
import $ from 'jquery'
require('webpack-jquery-ui')
require('webpack-jquery-ui/css')
export default {
  components: {
    vuescroll,
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
      insertModulesId: -1
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
      let this_ = this
      // 模块拖拽
      $('.third_drag').draggable({
        appendTo: '.decLeft',
        helper: 'clone',
        start: function () {
        },
        drag: function (ev, ui) {
          // console.log($(ui.helper).offset())
          console.log(ev)
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
    },
    // 拖拽开始start处理函数
    highlignt_row_item (pos) {
      let this_ = this
      let p = $('.drag_area').offset()
      console.log(p, '--', pos, '--', $('.drag_area').width(), $('.drag_area').height())
      if (pos.left > p.left && pos.top > p.top &&
        pos.left < p.left + $('.drag_area').width() &&
        pos.top < p.top + $('.drag_area').height()) {
        console.log('ssssss')

        $('.modules').each(function (idx, item) {
          p = $(this).offset()
          if (pos.left > p.left && pos.top > p.top &&
            pos.left < p.left + $(this).width() &&
            pos.top < p.top + $(this).height()
          ) {
            this_.insertModulesId = $(this)[0].__vue__.flag
            console.log($(this)[0].__vue__.flag)
            $('.modules').removeClass('placeholder')
            $(this).addClass('placeholder')
          }
        })
      }
    },
    // 模块拖拽接收
    handleToAcceptDrag () {
      let this_ = this
      $('.decContent').droppable({
        activeClass: 'ui-state-default',
        hoverClass: 'ui-state-hover',
        accept: '.third_drag',
        drop: function (event, ui) {
          // console.log(ui.draggable[0].innerText)
          // console.log(this_.dataId)
          console.log(this_.insertModulesId, '--', this_.showModulesList)
          let insert = this_.insertModulesId + 1
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
        }
      }
    }
  }
  .list-group {
    height: 100%;
  }
}
</style>
