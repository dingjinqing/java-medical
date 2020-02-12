<template>
  <div>
    <v-distpicker
      :province="temp.address__province"
      :city="temp.address__city"
      :area="temp.address__dist"
      @selected="onSelected"
    ></v-distpicker>
  </div>
</template>
<script>
import VDistpicker from 'v-distpicker'
export default {
  components: { VDistpicker },
  props: {
    address: {
      type: Object
    }
  },
  data () {
    return {
      temp: {
        address__province: '北京市',
        address__city: '北京城区',
        address__dist: '东城区'
      }
    }
  },
  mounted () {
    this.temp.address__province = this.address.provinceName || this.temp.address__province
    this.temp.address__city = this.address.cityName || this.temp.address__city
    this.temp.address__dist = this.address.distictName || this.temp.address__dist
  },
  methods: {
    onSelected (data) {
      console.log(data)
      let flag = true
      Object.keys(data).forEach((item, index) => {
        if (!data[item].code) flag = false
      })
      if (!flag) return
      console.log(data)
      this.temp.address__province = data.province.value
      this.temp.address__city = data.city.value
      this.temp.address__dist = data.area.value
      this.$emit('handleToGetProCode', data)
    }
  }

}
</script>
<style scoped>
</style>
