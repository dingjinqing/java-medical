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
            >商品组件</el-tab-pane>
            <el-tab-pane
              label="营销组件"
              name="third"
            >营销组件</el-tab-pane>
          </el-tabs>
        </div>
        <!-- <div class="decMiddle">
          2
        </div>
        <div class="decRight">
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
      activeName: 'first',
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
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_rotation.png',
          text: '电话模块'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_rotation.png',
          text: '店招设置'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_rotation.png',
          text: '地图模块'
        }
      ]
    }
  },
  methods: {

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
      .decLeft {
        width: 254px;
        margin: 2px;
        padding: 0 2px;
        .picTextConDiv {
          // min-height: 520px;
          // height: 520px;
          // overflow-y: auto;
          .picTextConDivList {
            float: left;
            border: 1px solid #e5e5e5;
            background: #f8f8f8;
            border-radius: 5px;
            line-height: 24px;
            width: 100px;
            height: 84px;
            margin: 5px 0 10px 10px;
            cursor: move;
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
          }
        }
      }
    }
  }
}
</style>
