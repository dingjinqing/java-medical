<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>地图模块</h2>
      <!--模块私有区域-->
      <div class="main">
        <div
          class="container"
          style="overflow: hidden;"
        >
          <div class="leftContent">显示地址：</div>
          <div class="rightContent">
            <div class="areaContent">
              <areaLinkage
                @areaData="handleAreaData()"
                style="width: 100%;"
              />
            </div>
            <div class="position">
              <el-input
                v-model="data.address"
                style="width: 200px;"
              ></el-input>
              <el-button
                type="primary"
                size="small"
              >地图定位</el-button>
            </div>
            <div class="mapContent">

            </div>
          </div>

        </div>
        <div class="container">
          <span style="display: inline-block;width: 70px;text-align: right;">地图：</span>
          <el-radio-group v-model="data.map_show">
            <el-radio label="0">不显示</el-radio>
            <el-radio label="1">显示</el-radio>
          </el-radio-group>
        </div>

      </div>
      <!--模块私有end-->
    </div>
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue' // 省市区下拉框
Vue.use(vcolorpicker)
export default {
  components: { areaLinkage },
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
        '#c7158577'
      ],
      data: {
        'module_name': 'm_map', // 模块名称
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
        this.data = this.modulesData
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
    // 省市区
    handleAreaData (data) {
      console.log(data)
      // this.data.province_code = data.province
      // this.data.city_code = data.city
      // this.data.area_code = data.district
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
        .leftContent {
          width: 70px;
          float: left;
          margin-top: 10px;
        }
        .rightContent {
          float: left;
          .areaContent {
            display: inlink;
            width: 420px;
            margin-bottom: 20px;
          }
          .position {
            margin-bottom: 20px;
          }
          .mapContent {
            width: 340px;
            height: 260px;
            border: 1px solid #ccc;
            margin-bottom: 20px;
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
