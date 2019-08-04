<template>
  <div class="container">
    <span>{{titleLeft}}</span>
    <span
      style="color:#666"
      v-for="(item,index) in titleList"
      :key="index"
    ><i v-if="index !==0">/ {{item}}</i></span>
  </div>
</template>
<script>
export default {
  data () {
    return {
      titleLeft: '',
      titleList: ''
    }
  },
  watch: {
    '$route.name' (newData) {
      console.log(newData)
      this.changeText()
    }
  },
  mounted () {
    this.langDefault()
    this.changeText()
  },
  methods: {
    // 更改数据
    changeText () {
      let data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle)))
      console.log(data, this.$route)
      if (data[1] === '会员列表' && data[2]) {
        data[2] = this.$route.query.name + '-' + data[2]
      }
      this.titleLeft = data[0]
      this.titleList = data
    }
  }

}
</script>
<style scoped>
.container {
  height: 55px;
  line-height: 55px;
  padding-left: 25px;
  color: #333;
  background: #fff;
}
</style>
