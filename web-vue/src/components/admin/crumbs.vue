<template>
  <div class="container">
    <span>{{mTitle[0]}}</span>
    <span
      style="color:#666"
      v-for="(item,index) in title"
      :key="index"
    >/ {{item}}</span>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  props: ['mTitle'],
  data () {
    return {
      title: ''
    }
  },
  computed: {
    ...mapGetters(['crumbsTitle']),
    crumbsTitle_ () {
      return this.crumbsTitle
    }
  },
  watch: {
    crumbsTitle_ (newData, oldData) {
      console.log(newData)
      this.handleTitle(newData)
    }
  },
  mounted () {
    console.log(this.mTitle)
    let arr = this.mTitle.filter((item, index) => {
      return index !== 0
    })
    console.log(arr)
    this.title = arr
  },
  methods: {
    // vuextitle
    handleTitle (data) {
      let arr = data.filter((item, index) => {
        return index !== 0
      })
      this.title = arr
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
