<template>
  <div class="payContent">
    <div>
      <div class="sexAndAge">
        <el-form label-position="top">
          <el-form-item :label="$t('userportrait.area')">
            <el-select
              v-model="userNum"
              :placeholder="$t('userportrait.please')"
              size="mini"
              style="width:160px"
              @change="changeUserNum"
            >
              <el-option
                v-for="item in userNumOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>

            <el-select
              v-model="type"
              :placeholder="$t('userportrait.please')"
              size="mini"
              style="width:160px"
              @change="search"
            >
              <el-option
                v-for="item in visitTrendOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <span class="itemDate">{{this.startDate}}</span>
            <span>-</span>
            <span class="itemDate">{{this.endDate}}</span>
          </el-form-item>
        </el-form>
        <el-row>
          <el-col :span="12">
            <div>
              <ve-map
                :legend-visible="false"
                :data="chartDataSex"
                :settings="chartSettings"
                :visual-map="visualMap"
                :extend="chartExtend"
                ref="chart1"
              ></ve-map>
            </div>
          </el-col>
          <el-col :span="12">
            <div>
              <el-table
                :data="chartDataCity"
                border
                style="width: 100%"
                :default-sort="{prop: 'value', order: 'descending'}"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}"
              >
                <el-table-column
                  prop="name"
                  :label="$t('userportrait.province')"
                  width="180"
                >
                </el-table-column>
                <el-table-column
                  prop="value"
                  :label="$t('userportrait.userNumber')"
                  sortable
                >
                  <template slot-scope="scope">
                    <div>
                      <span style="width: 15%;float: left;">{{scope.row.value}}</span>
                      <div>
                        <el-progress
                          :text-inside="true"
                          :stroke-width="26"
                          :percentage="scope.row.value"
                          :show-text="false"
                        ></el-progress>
                      </div>
                    </div>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="inner"></div>
    </div>
    <div class="allcity">

      <el-form
        label-position="top"
        label-width="80px"
      >

        <el-form-item>
          <!-- 选择省 -->
          <el-select
            style="width:220px"
            size="small"
            @change="choseProvince"
            v-model="values.province"
            :placeholder="$t('userportrait.all')"
          >
            <el-option value="">{{$t('userportrait.all')}}</el-option>
            <el-option
              v-for="item in province"
              :key="item.provinceId"
              :label="item.provinceName"
              :value="item.provinceId"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div>
        <el-table
          :data="cityTableData2"
          style="width: 100%"
          border
          :default-sort="{prop: 'value', order: 'descending'}"
          :header-cell-style="{background:'#eef1f6',color:'#606266'}"
        >
          <el-table-column
            prop="name"
            :label="$t('userportrait.city')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="value"
            :label="$t('userportrait.userNumber')"
            sortable
          >
            <template slot-scope="scope">
              <div>
                <span style="width: 15%;float: left;">{{scope.row.value}}</span>
                <div>
                  <el-progress
                    :text-inside="true"
                    :stroke-width="26"
                    :percentage="scope.row.value"
                    :show-text="false"
                  ></el-progress>
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import { getPortraitRequest } from '@/api/admin/basicConfiguration/userportrait.js'
import { getAreaSelect } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
import VCharts from 'v-charts'
export default {
  components: {
    VCharts
  },
  data () {
    this.chartSettings = {
      labelMap: {
        'value': this.$t('userportrait.quantity')
      },
      label: false,
      itemStyle: {
        normal: {
          borderColor: '#00f'
        }
      },
      aspectScale: 0.75,
      zoom: 1.2
    }
    this.visualMap = {
      inverse: true,
      show: true,
      type: 'continuous', // 'piecewise',
      realtime: false, // 拖拽是否实施更新
      min: 0,
      max: 34,
      calculable: false,
      text: ['高', '低'],
      inRange: {
        color: ['#afe8ff', '#2a99c9']
      },
      align: 'top',
      orient: 'horizontal'
    }
    this.chartExtend = {
      legend: {
        show: false // 图例展示
      },
      series: {
        showLegendSymbol: false, // 去掉地图小圆点
        silent: false, // 详情展示
        itemStyle: {
          normal: {
            areaColor: '#cde1f5',
            borderColor: '#fff'
          },
          emphasis: {
            areaColor: '#fde166'
          }
        },
        label: {
          normal: {
            show: false,
            position: 'inside',
            textStyle: {
              color: 'rgba(0, 0, 0, 0)'
            }
          },
          emphasis: {
            show: false,
            textStyle: {
              color: 'rgb(249, 249, 249)'
            }
          }
        },
        zoom: 1.2
      }
    }
    return {
      type: 0,
      userNum: 1,
      startDate: null,
      endDate: null,
      tableData: null,
      cityTableData: null,
      cityTableData2: [],
      chartDataSex: {
        columns: ['name', 'value'],
        rows: []
      },
      rows1: [],
      chartDataCity: [],
      userNumOptions: this.$t('userportrait.userNumOptions'),
      visitTrendOptions: this.$t('userportrait.visitTrendOptions'),
      province: [],
      values: {
        province: ``
      }
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.defaluteData()
  },
  watch: {
    lang () {
      this.defaluteData()
      this.userNumOptions = this.$t('userportrait.userNumOptions')
      this.visitTrendOptions = this.$t('userportrait.visitTrendOptions')
    },
    rows1 (data) {
      this.$nextTick(_ => {
        this.$refs.chart1.echarts.resize()
      })
    }
  },
  methods: {
    defaluteData () {
      this.search()
      this.getData()
    },
    search () {
      console.log(this.date)
      let params = {
        'type': this.type
      }
      getPortraitRequest(params).then((res) => {
        console.log('res-----------------------------------')
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content
          this.startDate = res.content.startDate
          this.endDate = res.content.endDate
          var needData = null
          if (this.userNum === 1) {
            // 活跃用户
            needData = res.content.activeUser
          } if (this.userNum === 2) {
            // 新增用户
            needData = res.content.newAddUser
          }
          var chartData = needData.province
          var cityData = needData.city
          this.rows1 = chartData
          this.chartDataSex.rows = chartData
          this.chartDataCity = chartData
          this.cityTableData = cityData
          this.cityTableData2 = cityData
          console.log('chartDataSex')
          console.log(this.chartDataSex)
          console.log('chartDataCity')
          console.log(this.chartDataCity)
          this.showCity()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 性别及年龄分布 用户变化
    changeUserNum () {
      var needData = null
      if (this.userNum === 1) {
        // 活跃用户
        needData = this.tableData.activeUser
      } if (this.userNum === 2) {
        // 新增用户
        needData = this.tableData.newAddUser
      }
      var chartData = needData.province
      var cityData = needData.city
      this.chartDataSex.rows = chartData
      this.chartDataCity = chartData
      this.cityTableData = cityData
      this.cityTableData2 = cityData
      this.showCity()
    },
    getData () {
      getAreaSelect().then(res => {
        // console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.province = content
        }
      }).catch(err => console.log(err))
    },
    choseProvince (val) {
      if (val === ``) {
        this.cityTableData2 = this.cityTableData
      }
      // console.log(this.province)
      // console.log(val)
      this.values.city = ``
      this.values.district = ``
      this.city = this.province.find((item, index) => val === item['provinceId'])['areaCity']
      this.showCity()
    },
    showCity () {
      console.log('province')
      console.log(this.values.province)
      if (this.values.province === ``) {
        console.log('返回')
        return
      }
      this.cityTableData2 = []
      var ccc = []
      console.log('city')
      console.log(this.city)
      console.log(this.cityTableData)
      for (let i = 0; i < this.cityTableData.length; i++) {
        for (let n = 0; n < this.city.length; n++) {
          if (this.city[n].cityName.indexOf(this.cityTableData[i].name) !== -1) {
            ccc.push(this.cityTableData[i])
          }
        }
      }
      this.cityTableData2 = ccc
      console.log(ccc)
    }

  }
}

</script>
<style lang="scss" scoped>
.payContent {
  padding: 10px 20px 0 20px;
  font-size: 14px;
  width: 100%;
}
.sexAndAge {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.itemDate {
  color: #9a9a9a;
}
.buttomTitle {
  width: 30px;
  padding-left: 46%;
}
.allcity {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.inner {
  position: relative;
  padding: 10px 20px 0 20px;
}
</style>
