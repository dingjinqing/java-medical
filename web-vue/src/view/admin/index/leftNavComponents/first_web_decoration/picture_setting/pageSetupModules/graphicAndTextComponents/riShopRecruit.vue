<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{ $t('shopRecruit.shopTitle') }}<span style="margin-left: 3%;color: #9a9a9a;">{{ $t('shopRecruit.titleTip') }}</span></h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="container">
          <span>{{ $t('shopRecruit.shopName') }}：</span>
          <el-input
            size="small"
            :maxlength="20"
            v-model="data.shop_name"
          ></el-input>
          <span class="tip">{{ $t('shopRecruit.nameTip') }}</span>
        </div>
        <div class="container">
          <span>{{ $t('shopRecruit.shopNotice') }}：</span>
          <el-input
            size="small"
            :maxlength="30"
            v-model="data.shop_notice"
          ></el-input>
          <span class="tip">{{ $t('shopRecruit.noticeTip') }}</span>
        </div>
        <div class="container">
          <div class="label">{{ $t('shopRecruit.shopBg') }}：</div>
          <div class="defaultBg">
            <span>{{ $t('shopRecruit.defaultBg') }}：</span>
            <el-select
              size="small"
              v-model="value"
              @change="selectChange"
            >
              <el-option
                v-for="(item, index) in options"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>

          <div class="customBg">
            <span>{{ $t('shopRecruit.customBg') }}：</span>
            <!--添加图片占位-->
            <div @click="handleToCallImgDialog()">
              <img
                v-if="data.bg_url === ''"
                :src="imageHost+'/image/admin/shop_beautify/add_decorete.png'"
                alt=""
              >
              <img
                v-if="data.bg_url !== ''"
                :src="imageHost + data.bg_url"
                alt=""
                style="width: 200px; height: 100px;"
              >
            </div>
            <!--添加图片占位end-->
          </div>

        </div>

        <div
          class="container color"
          v-if="data.search_sort==='1'"
        >
          <span>{{$t('commoditySearch.iconColor')}}：</span>
          <span class="colorSelect">
            <el-color-picker
              v-model="data.sort_bg_color"
              show-alpha
              :predefine="predefineColors"
            >
            </el-color-picker>
          </span>
          <span style="margin-left:5px">
            <el-button
              plain
              size="small"
              @click="handleToReset(2)"
            >{{$t('commoditySearch.reset')}}</el-button>
          </span>

        </div>
      </div>
      <!--模块私有end-->
    </div>
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex='imageDalog'
      :tuneUp='tuneUp'
      :isDraggable='isDraggable'
      :imageSize='imageSize'
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import { shopInfoRequest } from '@/api/admin/survey.js'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog') // 选择图片弹窗
  },
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      data: {
        'module_name': 'm_shop', // 模块名称
        'box_color': '#eee', // 框体颜色
        'back_color': '#fff', // 背景颜色
        'search_sort': '0', // 商家分类是否显示
        'sort_bg_color': '#666666' // 图标颜色
      },
      // imageHost: this.$imageHost,
      imageHost: 'http://jmpdevimg.weipubao.cn/',
      tuneUp: false, //  调起添加图片弹窗flag
      imageSize: [200, 200], // 调起添加图片宽高
      isDraggable: false, // 添加商品弹窗是否开启多选底部可拖拽状态
      isAddImgOrChangeFlga: false, // true为添加图片  false为更换列表项中的图片
      moduleSaveData: {
        is_preview: '0' //  预览原图radio
      },
      value: '/image/admin/shop_beautify/beau1.png',
      options: [{
        label: '背景图1',
        value: '/image/admin/shop_beautify/beau1.png'
      }, {
        label: '背景图2',
        value: '/image/admin/shop_beautify/beau2.png'
      }, {
        label: '背景图3',
        value: '/image/admin/shop_beautify/beau3.png'
      }, {
        label: '背景图4',
        value: '/image/admin/shop_beautify/beau4.png'
      }, {
        label: '背景图5',
        value: '/image/admin/shop_beautify/beau5.png'
      }],
      predefineColors: [ // 颜色选择器预定义颜色池
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ],
      defaultColorBorder: '#eee',
      defaultColorBorderBg: '#fff',
      defaultColorBorderIcon: '#666666'
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        this.data = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    modulesData: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  mounted () {
    this.getShopInfo()
  },
  methods: {
    // 获取店铺信息
    getShopInfo () {
      shopInfoRequest().then((res) => {
        if (res.error === 0) {
          this.data.shop_name = res.content.shopName
          this.data.shop_bg_path = res.content.shopAvatar
        }
      })
    },

    // 切换背景图
    selectChange (val) {
      this.data.bg_url = val
    },
    // 点击添加图片
    handleToCallImgDialog () {
      this.tuneUp = !this.tuneUp
    },
    // 添加图片弹窗选中图片数据回传
    handleSelectImg (imgData) {
      console.log(imgData)
      this.data.bg_url = imgData.imgPath
      // if (this.isAddImgOrChangeFlga) {
      //   imgData.forEach((item, index) => {
      //     let obj = {
      //       'image': item.imgUrl,
      //       'width': item.imgWidth, // 图片宽度
      //       'height': item.imgHeight, // 图片高度
      //       'title': '', // 文本
      //       'link': '', //   链接
      //       'can_show': '0', //  显示设置raido
      //       'whetherToExpand': '0'
      //     }
      //     this.moduleSaveData.image_list.push(obj)
      //   })
      // } else {
      //   console.log(imgData)
      //   this.moduleSaveData.image_list[this.changeListImgIndex].image = imgData.imgUrl
      // }
    },
    // 点击重置
    handleToReset (index) {
      switch (index) {
        case 0:
          this.data.box_color = '#eee'
          break
        case 1:
          this.data.back_color = '#fff'
          break
        case 2:
          this.data.sort_bg_color = '#666666'
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    //模块私有样式  --------------
    .main {
      margin-top: 20px;
      .container {
        margin-bottom: 20px;
        .el-input {
          width: 180px;
        }
        .tip {
          margin-left: 3%;
          color: #9a9a9a;
        }
        .el-select {
          width: 120px;
        }
        .label {
          width: 70px;
          display: inline-block;
        }
        .defaultBg {
          display: inline-block;
        }
        .customBg {
          margin-left: 70px;
          margin-top: 20px;
          div {
            display: inline-block;
            width: 80px;
            height: 80px;
            line-height: 80px;
            background: #fff;
            border: 1px solid #e4e4e4;
            text-align: center;
            cursor: pointer;
          }
        }
      }
      .color {
        display: flex;
        span:nth-of-type(1) {
          height: 32px;
          line-height: 32px;
        }
        .colorSelect {
          margin-left: 5px;
          background-color: #fff;
          border: 1px solid #ccc;
          /deep/ .m-colorPicker {
            display: flex;
            justify-content: center;
            align-items: center;
            .open {
              margin-top: 60px;
              z-index: 10000;
            }
          }
        }
      }
    }
    //end
  }
}
</style>
