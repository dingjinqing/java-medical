<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>商品搜索模块</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="container">
          <span>框体样式：</span>
          <el-radio
            v-model="data.search_style"
            label="0"
          >方形</el-radio>
          <el-radio
            v-model="data.search_style"
            label="1"
          >圆形</el-radio>
        </div>
        <div class="container">
          <span>框体高度：</span>
          <el-radio
            v-model="data.search_font"
            label="0"
          >高</el-radio>
          <el-radio
            v-model="data.search_font"
            label="1"
          >中</el-radio>
          <el-radio
            v-model="data.search_font"
            label="2"
          >低</el-radio>
        </div>
        <div class="container color">
          <span>框体颜色：</span>
          <span class="colorSelect">
            <colorPicker
              v-model="data.box_color"
              :defaultColor="defaultColorBorder"
              style="width:60px;height:30px;"
            />
          </span>
          <span style="margin-left:5px">
            <el-button
              plain
              size="small"
              @click="handleToReset(0)"
            >重置</el-button>
          </span>

        </div>
        <div class="container color">
          <span>背景颜色：</span>
          <span class="colorSelect">
            <colorPicker
              v-model="data.back_color"
              :defaultColor="defaultColorBorderBg"
              style="width:60px;height:30px;"
            />
          </span>
          <span style="margin-left:5px">
            <el-button
              plain
              size="small"
              @click="handleToReset(1)"
            >重置</el-button>
          </span>

        </div>
        <div class="container">
          <span>商家分类：</span>
          <el-radio
            v-model="data.search_sort"
            label="0"
          >不显示</el-radio>
          <el-radio
            v-model="data.search_sort"
            label="1"
          >显示</el-radio>
        </div>

        <div
          class="container color"
          v-if="data.search_sort==='1'"
        >
          <span>图标颜色：</span>
          <span class="colorSelect">
            <colorPicker
              v-model="data.sort_bg_color"
              :defaultColor="defaultColorBorderIcon"
              style="width:60px;height:30px;"
            />
          </span>
          <span style="margin-left:5px">
            <el-button
              plain
              size="small"
              @click="handleToReset(2)"
            >重置</el-button>
          </span>

        </div>
      </div>
      <!--end-->
    </div>
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
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
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        this.data = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    data: {
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
    //模块私有样式
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
