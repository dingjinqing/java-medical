<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{ $t('mapModule.mapTitle') }}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div
          class="container"
          style="overflow: hidden;"
        >
          <div class="leftContent">{{ $t('mapModule.mapAddress') }}：</div>
          <div class="rightContent">
            <div class="areaContent">
              <areaLinkage
                :areaCode="areaObj"
                @areaData="handleAreaData"
                style="width: 100%;"
              />
            </div>
            <div class="position">
              <el-input
                v-model="data.address"
                size="small"
                style="width: 200px;"
              ></el-input>
              <el-button
                type="primary"
                size="small"
                @click="getLocation()"
              >{{ $t('mapModule.mapLocation') }}</el-button>
            </div>
            <div
              class="mapContent"
              ref="riMapContainer"
            >

            </div>
          </div>

        </div>
        <div class="container">
          <span style="display: inline-block;width: 70px;text-align: right;">{{ $t('mapModule.map') }}：</span>
          <el-radio-group v-model="data.map_show">
            <el-radio :label="0">{{ $t('mapModule.mapNotShow') }}</el-radio>
            <el-radio :label="1">{{ $t('mapModule.mapShow') }}</el-radio>
          </el-radio-group>
        </div>

      </div>
      <!--模块私有end-->
    </div>
  </div>
</template>
<!-- 腾讯地图 -->
<script charset="utf-8" src="https://map.qq.com/api/js?v=2.exp&key=YPOBZ-DNIKF-Y6KJM-NDW7D-VYIFZ-QEBIO"></script>
<script>
import chinaData from '@/assets/china-data'
import { deepCloneObj } from '@/util/deepCloneObj'
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  components: {
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue'), // 省市区下拉框
  },
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
      areaDataList: [],
      areaObj: {
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      },
      defaultColorBorder: '#eee',
      defaultColorBorderBg: '#fff',
      defaultColorBorderIcon: '#666666',
      geocoder: null,
      marker: null,
      map: null
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        this.data = this.modulesData

        // 省市区数据回显
        this.areaObj.provinceCode = this.data.province_code
        this.areaObj.cityCode = this.data.city_code
        this.areaObj.districtCode = this.data.area_code
        console.log(this.areaObj)

        // 初始化地图
        this.$nextTick(() => {
          this.initMap(this.data.latitude, this.data.longitude)
        })
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
  mounted () {
    this.areaDataList = deepCloneObj(chinaData)
  },
  methods: {
    // 省市区
    handleAreaData (val) {
      console.log(val)
      this.data.province_code = val.province
      this.data.city_code = val.city
      this.data.area_code = val.district

      this.areaDataList.forEach(item => {
        if (val.province === item.provinceId) {
          this.data.province = item.provinceName
          item.areaCity.forEach(item2 => {
            if (val.city === item2.cityId) {
              this.data.city = item2.cityName
              item2.areaDistrict.forEach(item3 => {
                if (val.district === item3.districtId) {
                  this.data.area = item3.districtName
                }
              })
            }
          })
        }
      })

    },
    // 加载地图
    initMap (latitude, longitude) {
      // 定义map变量 调用 qq.maps.Map() 构造函数   获取地图显示容器
      this.map = new qq.maps.Map(this.$refs.riMapContainer, {
        center: new qq.maps.LatLng(latitude, longitude), // 地图的中心地理坐标。
        zoom: 13 // 缩放等级
      })
      // 调用地址解析类
      let that = this
      this.geocoder = new qq.maps.Geocoder({
        complete: function (result) {
          that.map.setCenter(result.detail.location)
          that.marker = new qq.maps.Marker({
            map: that.map,
            position: result.detail.location
          })
          that.$set(that.data, 'latitude', result.detail.location.lat)
          that.$set(that.data, 'longitude', result.detail.location.lng)
        }
      })
      qq.maps.event.addListener(this.map, 'click', function (e) {
        if (that.marker) {
          that.marker.setMap(null)
        }
        that.marker = new qq.maps.Marker({
          position: e.latLng,
          map: that.map
        })
      })
      if (latitude && longitude) {
        that.map.panTo(new qq.maps.LatLng(latitude, longitude))
        if (that.marker) {
          that.marker.setMap(null)
        }
        that.marker = new qq.maps.Marker({
          position: new qq.maps.LatLng(latitude, longitude),
          map: that.map
        })
      }
    },

    // 点击查询地址
    getLocation () {
      var fullAddress = this.data.province + ',' + this.data.city + ',' + this.data.area + ',' + this.data.address
      // 通过getLocation();方法获取位置信息值
      this.geocoder.getLocation(fullAddress)
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
