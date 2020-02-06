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
  data () {
    return {
      temp: {
        address__province: '北京市',
        address__city: '北京城区',
        address__dist: '东城区'
      }
    }
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
