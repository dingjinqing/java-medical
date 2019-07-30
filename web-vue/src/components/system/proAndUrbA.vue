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
  component: { VDistpicker },
  data () {
    return {
      temp: {
        address__province: '北京市',
        address__city: '北京市',
        address__dist: '东城区'
      }
    }
  },
  methods: {
    onSelected (data) {
      console.log(data)
      // this.temp.address__province = data.province.value
      // this.temp.address__city = data.city.value
      // this.temp.address__dist = data.area.value
    }
  }

}
</script>
<style scoped>
</style>
