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

              <div
                v-for="(item,key) in listLeft"
                :key="key"
                class="picTextConDivList"
                v-drag
              >
                <img :src="item.name">
                {{item.value}}

              </div>

            </el-tab-pane>
          </el-tabs>
        </div>
        <div class="decMiddle">
          <div class="decTop"></div>
          <div class="decContent">
          </div>
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
export default {
  components: {
    vuescroll
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
        name: this.$imageHost + '/image/admin/new_shop_beautify/pin_integration.png',
        value: '瓜分积分'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/pin_integration.png',
        value: '瓜分积分2'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/pin_integration.png',
        value: '瓜分积分3'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/pin_integration.png',
        value: '瓜分积分3'
      }, {
        name: this.$imageHost + '/image/admin/new_shop_beautify/pin_integration.png',
        value: '瓜分积分4'
      }],
      positionX: 0,
      positionY: 0
    }
  },
  directives: {
    drag: {
      bind: function (el, binding) {
        let oDiv = el
        oDiv.onmousedown = (e) => {
          console.log(e)
          // 算出鼠标相对元素的位置
          // let disX = e.clientX - oDiv.offsetLeft
          // let disY = e.clientY - oDiv.offsetTop
          // document.onmousemove = (e) => {
          //   // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
          //   let left = e.clientX - disX - 10
          //   let top = e.clientY - disY - 20

          //   // 绑定元素位置到positionX和positionY上面
          //   // this.positionX = top
          //   // this.positionY = left
          //   console.log(binding)
          // 移动当前元素
          //   oDiv.style.left = left + 'px'
          //   oDiv.style.top = top + 'px'
          // }
          // document.onmouseup = (e) => {
          //   document.onmousemove = null
          //   document.onmouseup = null
          // }
        }
      }
    }
  },
  methods: {
    changeData (x, y) {
      console.log(x, y)
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
        padding: 0 2px;

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
          background: #eee;
          height: 100%;
        }
      }
    }
  }
  .list-group {
    height: 100%;
  }
}
</style>
