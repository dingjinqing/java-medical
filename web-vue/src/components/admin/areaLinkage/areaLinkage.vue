<template>
  <div class="areaLinkage">
    <section class="container">
      <!-- 选择省 -->
      <el-select
        style="width:220px"
        size="small"
        @change="choseProvince"
        v-model="values.province"
        :placeholder="placeholder.province"
      >
        <el-option value="">请选择省</el-option>
        <el-option
          v-for="item in province"
          :key="item.provinceId"
          :label="item.provinceName"
          :value="item.provinceId"
        >
        </el-option>
      </el-select>
      <!-- 选择市 -->
      <el-select
        style="width:220px"
        size="small"
        @change="choseCity"
        v-model="values.city"
        :placeholder="placeholder.city"
      >
        <el-option value="">请选择市</el-option>
        <el-option
          v-for="item in city"
          :key="item.cityId"
          :label="item.cityName"
          :value="item.cityId"
        >
        </el-option>
      </el-select>
      <!-- 选择区县 -->
      <el-select
        style="width:220px"
        size="small"
        @change="choseDistrict"
        v-model="values.district"
        :placeholder="placeholder.district"
      >
        <el-option value="">请选择区县</el-option>
        <el-option
          v-for="item in areaDistrict"
          :key="item.districtId"
          :label="item.districtName"
          :value="item.districtId"
        >
        </el-option>
      </el-select>
    </section>
    <!-- <section>{{this.area}}</section> -->
  </div>
</template>
<script>
import chinaData from '@/assets/china-data'
import { deepCloneObj } from '@/util/deepCloneObj'
// import { getAreaSelect } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
export default {
  name: 'areaLinkage',
  props: {
    provinceCode: [String, Number],
    cityCode: [String, Number],
    districtCode: [String, Number]
  },
  data () {
    return {
      placeholder: {
        province: `请选择省`,
        city: `请选择市`,
        district: `请选择区县`
      },
      values: {
        province: null,
        city: null,
        district: null
      },
      areas: {
        province: {},
        city: {},
        district: {}
      },
      province: [],
      city: [],
      areaDistrict: [],

      editType: false
    }
  },
  computed: {
    area () {
      return this.values
    }
  },
  watch: {
    // 编辑数据回显
    provinceCode (val) {
      this.editType = true
      this.values.province = Number(val)
      this.choseProvince(this.values.province)
    }
  },
  mounted () {
    // this.getData()
    this.initHandler()
  },
  methods: {
    // 初始化数据
    initHandler () {
      this.province = deepCloneObj(chinaData)
    },

    // 获取省市区弹窗
    // getData () {
    //   let that = this
    //   getAreaSelect().then(res => {
    //     // console.log(res)
    //     const { error, content } = res
    //     if (error === 0) {
    //       this.province = content
    //       if (that.provinceCode) {
    //         that.$set(that.values, 'province', Number(that.provinceCode))
    //         that.choseProvince(Number(that.provinceCode))
    //         that.choseCity(Number(that.cityCode))
    //         if (that.cityCode) {
    //           that.$set(that.values, 'city', Number(that.cityCode))
    //         }
    //         that.choseDistrict(Number(that.districtCode))
    //         if (that.districtCode) {
    //           that.$set(that.values, 'district', Number(that.districtCode))
    //         }
    //       }
    //     }
    //   }).catch(err => console.log(err))
    // },

    // 选择省份
    choseProvince (val) {
      // if (val === ``) return
      if (val) {
        this.city = this.province.find((item, index) => val === item['provinceId'])['areaCity']
        if (this.editType === true) {
          this.values.city = Number(this.cityCode)
          this.choseCity(this.values.city)
        } else {
          this.values.city = ``
          this.values.district = ``
        }
      } else {
        this.city = []
      }

      let province = this.province.find(data => val === data.provinceId)
      if (province) {
        this.areas.province = {
          id: province.provinceId,
          name: province.provinceName
        }
      } else {
        this.areas.province = {
          id: '',
          name: ''
        }
      }
      this.areas.city = {}
      this.areas.district = {}
      this.$emit('areaData', this.area)
      this.$emit('areaChange', this.areas)
    },

    // 选择市
    choseCity (val) {
      // if (val === ``) return

      if (val) {
        this.areaDistrict = this.city.find((item, index) => val === item['cityId'])['areaDistrict']
        if (this.editType === true) {
          this.values.district = Number(this.districtCode)
          this.choseDistrict(this.values.district)
        } else {
          this.values.district = ``
        }
      } else {
        this.areaDistrict = []
      }

      let city = this.city.find(data => val === data.cityId)
      if (city) {
        this.areas.city = {
          id: city.cityId,
          name: city.cityName
        }
      } else {
        this.areas.city = {
          id: '',
          name: ''
        }
      }
      this.areas.district = {}
      this.$emit('areaData', this.area)
      this.$emit('areaChange', this.areas)
    },

    choseDistrict (val) {
      this.editType = false

      let district = this.areaDistrict.find(data => val === data.districtId)
      if (district) {
        this.areas.district = {
          id: district.districtId,
          name: district.districtName
        }
      } else {
        this.areas.district = {
          id: '',
          name: ''
        }
      }
      this.$emit('areaData', this.area)
      this.$emit('areaChange', this.areas)
    }
  }
}
</script>
<style lang="scss" scoped>
.container {
  display: flex;
}
</style>
