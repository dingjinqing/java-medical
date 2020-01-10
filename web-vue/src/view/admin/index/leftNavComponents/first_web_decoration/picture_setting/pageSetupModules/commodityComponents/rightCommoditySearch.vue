<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('commoditySearch.searchTitle')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="container">
          <span>{{$t('commoditySearch.frameStyle')}}：</span>
          <el-radio
            v-model="data.search_style"
            label="0"
          >{{$t('commoditySearch.square')}}</el-radio>
          <el-radio
            v-model="data.search_style"
            label="1"
          >{{$t('commoditySearch.circular')}}</el-radio>
        </div>
        <div class="container">
          <span>{{$t('commoditySearch.frameHeight')}}：</span>
          <el-radio
            v-model="data.search_font"
            label="0"
          >{{$t('commoditySearch.high')}}</el-radio>
          <el-radio
            v-model="data.search_font"
            label="1"
          >{{$t('commoditySearch.in')}}</el-radio>
          <el-radio
            v-model="data.search_font"
            label="2"
          >{{$t('commoditySearch.low')}}</el-radio>
        </div>
        <div class="container color">
          <span>{{$t('commoditySearch.frameColor')}}：</span>
          <el-color-picker
            v-model="data.box_color"
            show-alpha
            :predefine="predefineColors"
            size="small"
          >
          </el-color-picker>
          <span style="margin-left:5px">
            <el-button
              plain
              size="small"
              @click="handleToReset(0)"
            >{{$t('commoditySearch.reset')}}</el-button>
          </span>

        </div>
        <div class="container color">
          <span>{{$t('commoditySearch.backgroundColor')}}：</span>
          <el-color-picker
            v-model="data.back_color"
            show-alpha
            :predefine="predefineColors"
            size="small"
          >
          </el-color-picker>
          <span style="margin-left:5px">
            <el-button
              plain
              size="small"
              @click="handleToReset(1)"
            >{{$t('commoditySearch.reset')}}</el-button>
          </span>

        </div>
        <div class="container">
          <span>{{$t('commoditySearch.merchantClassification')}}：</span>
          <el-radio
            v-model="data.search_sort"
            label="0"
          >{{$t('commoditySearch.noDisplay')}}</el-radio>
          <el-radio
            v-model="data.search_sort"
            label="1"
          >{{$t('commoditySearch.display')}}</el-radio>
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
        <div class="container">
          <span>显示位置：</span>
          <el-radio
            v-model="data.search_position"
            label="0"
          >固定位置</el-radio>
          <el-radio
            v-model="data.search_position"
            label="1"
          >滚动到顶部固定</el-radio>
        </div>
      </div>
      <!--模块私有end-->
    </div>
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
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
        '#FF0000'
      ],
      data: {
        'module_name': 'm_goods_search', // 模块名称
        'search_style': '1', // 框体样式
        'search_font': '1', // 框体高度
        'box_color': '#eee', // 框体颜色
        'back_color': '#fff', // 背景颜色
        'search_sort': '0', // 商家分类是否显示
        'sort_bg_color': '#666666' // 图标颜色
      },
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
        let turnToString = this.handleToTurnNumToStr(this.modulesData)
        console.log(turnToString)
        this.data = turnToString
      },
      immediate: true
    },
    // 监听数据变换
    data: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
    // 点击重置
    handleToReset (index) {
      switch (index) {
        case 0:
          this.data.box_color = null
          break
        case 1:
          this.data.back_color = null
          break
        case 2:
          this.data.sort_bg_color = null
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
        /deep/ .el-radio {
          margin-right: 10px;
        }
        /deep/ .colorBtn {
          width: 50px;
          height: 20px;
          border: 1px solid #000;
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
