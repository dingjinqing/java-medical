<template>
  <div class="areaLinkage">
    <section class="container">
      <!-- 选择省 -->
      <el-select
        style="width:150px"
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
        style="width:150px"
        size="small"
        @change="choseCity"
        v-model="values.city"
        :placeholder="placeholder.city"
      >
        <el-option value="">请选择市</el-option>
        <el-option
          v-for="item in this.city"
          :key="item.cityId"
          :label="item.cityName"
          :value="item.cityId"
        >
        </el-option>
      </el-select>
      <!-- 选择区县 -->
      <el-select
        style="width:150px"
        size="small"
        @change="choseDistrict"
        v-model="values.district"
        :placeholder="placeholder.district"
      >
        <el-option value="">请选择区县</el-option>
        <el-option
          v-for="item in this.areaDistrict"
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
import { getAreaSelect } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
export default {
  name: 'areaLinkage',
  data () {
    return {
      placeholder: {
        province: `请选择省`,
        city: `请选择市`,
        district: `请选择区县`
      },
      values: {
        province: ``,
        city: ``,
        district: ``
      },
      province: [],
      city: [],
      areaDistrict: []
    }
  },
  computed: {
    area () {
      return this.values
    }
  },
  created () {
    this.getData()
  },
  methods: {
    // 获取省市区弹窗
    getData () {
      getAreaSelect().then(res => {
        // console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.province = content
        }
      }).catch(err => console.log(err))
    },
    // 选择省份
    choseProvince (val) {
      if (val === ``) return
      // console.log(this.province)
      // console.log(val)
      this.values.city = ``
      this.values.district = ``
      this.city = this.province.find((item, index) => val === item['provinceId'])['areaCity']
      this.$emit('areaData', this.area)
    },
    // 选择市
    choseCity (val) {
      // console.log(val)
      // console.log(this.city)
      if (val === ``) return
      this.areaDistrict = this.city.find((item, index) => val === item['cityId'])['areaDistrict']
      // console.log(this.areaDistrict)
      this.$emit('areaData', this.area)
    },
    choseDistrict () {
      this.$emit('areaData', this.area)
    }
  }

}
</script>
