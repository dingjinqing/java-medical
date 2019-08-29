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
    },
    '$store.state.crumbs.cardholderData' (newData) {
      localStorage.setItem('V-UserCardCrumb', JSON.stringify(newData))
      this.handleToData(newData)
    },
    '$store.state.crumbs.refillDetails' (newData) {
      localStorage.setItem('V-UserCardCrumb', JSON.stringify(newData))
      this.handleToRefllDet(newData)
    }
  },
  mounted () {
    this.langDefault()
    this.changeText()
  },
  methods: {
    // 特例更改数据
    changeText () {
      let data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle)))
      console.log(data, this.$route)
      if (data[1] === '会员列表' && data[2]) {
        data[2] = this.$route.query.name + '-' + data[2]
      }
      if (this.$route.name === 'Cardholder' || this.$route.name === 'receivingDetails' || this.$route.name === 'activateAudit' || this.$route.name === 'viewOrders') {
        let localData = JSON.parse(localStorage.getItem('V-UserCardCrumb'))
        if (localData) {
          this.handleToData(localData)
        }
        console.log(localData)
      } else if (this.$route.name === 'refillDetails') {
        let localData = JSON.parse(localStorage.getItem('V-UserCardCrumb'))
        this.handleToRefllDet(localData)
      } else {
        this.titleLeft = data[0]
        this.titleList = data
      }
    },
    // 处理数据
    handleToData (newData) {
      console.log(newData)
      if (newData.type === '激活审核') {
        this.titleList = ['会员卡', `${newData.item.cardName}-激活审核`]
      } else {
        switch (newData.flag) {
          case 0:
            if (newData.type === '持卡会员') {
              this.titleList = ['普通会员卡', `(${newData.item.cardName})持卡会员列表`]
            } else if (newData.type === '查看订单') {
              this.titleList = ['普通会员卡', `${newData.item.cardName}-会员卡订单`]
            } else {
              this.titleList = ['普通会员卡', `${newData.item.cardName}-领取详情`]
            }

            break
          case 1:
            if (newData.type === '持卡会员') {
              this.titleList = ['限次会员卡', `(${newData.item.cardName})持卡会员列表`]
            } else if (newData.type === '查看订单') {
              this.titleList = ['限次会员卡', `${newData.item.cardName}-会员卡订单`]
            } else {
              this.titleList = ['限次会员卡', `${newData.item.cardName}-领取详情`]
            }
            break
          case 2:
            if (newData.type === '持卡会员') {
              this.titleList = ['等级会员卡', `(${newData.item.cardName})持卡会员列表`]
            } else if (newData.type === '查看订单') {
              this.titleList = ['等级会员卡', `${newData.item.cardName}-会员卡订单`]
            } else {
              this.titleList = ['等级会员卡', `${newData.item.cardName}-领取详情`]
            }
        }
      }

      let data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle)))
      this.titleLeft = data.concat(this.titleList)[0]
      this.titleList = data.concat(this.titleList)
    },
    // 处理会员充值明细数据
    handleToRefllDet (newData) {
      console.log(newData)
      this.titleList = [`${newData.item.cardName} -- 会员卡充值明细`]
      let data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle)))
      this.titleLeft = data.concat(this.titleList)[0]
      this.titleList = data.concat(this.titleList)
      console.log(data, this.titleList)
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
