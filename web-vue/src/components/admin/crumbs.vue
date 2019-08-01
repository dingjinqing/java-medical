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
    ...mapGetters(['crumbsTitle', 'membershipdetailflag']),
    crumbsTitle_ () {
      return this.crumbsTitle
    },
    membershipdetailflag_ () {
      return this.membershipdetailflag
    }
  },
  watch: {
    crumbsTitle_ (newData, oldData) {
      console.log(newData)
      this.handleTitle(newData)
    },
    membershipdetailflag_ (newData, name) {
      switch (newData) {
        case '0':
          this.title = [
            '会员列表'
          ]
          break
        case 'memberDetail':
          this.title = [
            '会员编辑信息'
          ]
          break
        case 'receiveDetail':
          this.title = [
            '会员列表', name + '-会员卡领取明细'
          ]
          break
        case 'balanceDetail':
          this.title = [
            '会员列表', name + '-会员卡余额明细'
          ]
          break
        case 'integralDetail':
          this.title = [
            '会员列表', name + '-积分明细'
          ]
          break
      }
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
