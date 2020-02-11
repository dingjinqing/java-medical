<template>
  <div
    :class="'container '+(titleList.length>2?'canClick':'')"
    v-if="isSurvey"
  >
    <span @click="handleToClickCrumb(titleLeft,true)">{{titleLeft}}</span>
    <span
      @click="handleToClickCrumb(item)"
      :style="index===2?'cursor:auto;text-decoration: none;color:#666;':'color:#666;'"
      v-for="(item,index) in titleList"
      :key="index"
    ><i v-if="index !==0"> / {{item}}</i></span>
  </div>
</template>
<script>
export default {
  data () {
    return {
      titleLeft: '',
      titleList: '',
      lang: '',
      isSurvey: true // 若是概况里的商城概览则隐藏面包屑
    }
  },
  watch: {
    lang (newData) {
      console.log(newData)
      this.changeText()
    },
    '$route.name' (newData) {
      console.log(newData)
      this.changeText(newData)
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
    console.log(this.$route)
    this.changeText(this.$route.name)
  },
  methods: {
    // 特例更改数据
    changeText (routeName) {
      // 如果是概况则隐藏面包屑
      console.log(routeName)
      if (routeName) {
        if (routeName === 'shop_view') {
          this.isSurvey = false
        } else {
          this.isSurvey = true
        }
      }
      // console.log(this.$t(`${this.$route.meta.crumbTitle}`))
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
        console.log(localData)
        this.handleToRefllDet(localData)
      } else if (this.$route.name === 'distribution_info') {
        data.push('分销配置')
        console.log(data)
        this.titleLeft = data[0]
        this.titleList = data
      } else {
        this.titleLeft = data[0]
        this.titleList = data
      }
      // 分销员

      this.$http.$on('distributionTap', (index) => {
        console.log(index)
        switch (index) {
          case '0':
            data[2] = '分销配置'
            console.log(data)
            break
          case '1':
            data[2] = '分销员等级配置'
            console.log(data)
            break
          case '2':
            data[2] = '返利策略配置'
            break
          case '3':
            data[2] = '分销员列表'
            break
          case '4':
            data[2] = '分销员分组'
            break
          case '5':
            data[2] = '佣金统计'
            break
          case '6':
            data[2] = '商品返利统计'
            break
          case '7':
            data[2] = '返利提现审核'
            break
          case '8':
            data[2] = '分销员审核'
            break
          case '9':
            data[2] = '分销推广语'
            break
        }
        this.titleList = data
      })
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
    },
    handleToClickCrumb (name, flag) {
      console.log(this.$route)
      if (flag) {
        if (this.$route.meta.meta === 'user_manger') {
          this.$router.push({
            name: this.$route.meta.category
          })
        } else {
          this.$router.push({
            name: this.$route.meta.meta
          })
        }
      } else {
        if (this.$route.name === 'distribution_info') {
          this.$http.$emit('toChangeActiveName', true)
        } else if (this.$route.meta.meta === 'user_manger') {
          this.$router.push({
            name: this.$route.meta.category
          })
        } else {
          this.$router.push({
            name: this.$route.meta.meta
          })
        }
      }
      console.log(name, this.$route)
    }
  }

}
</script>
<style scoped lang="scss">
.container {
  height: 55px;
  line-height: 55px;
  padding-left: 25px;
  color: #333;
  background: #fff;
}
.canClick {
  span {
    cursor: pointer;
    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
