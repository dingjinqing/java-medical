<template>
  <div
    :class="'container '+(titleList.length>2?'canClick':'')"
    v-if="isSurvey"
  >
    <span @click="handleToClickCrumb(titleLeft,true)">{{titleLeft}}</span>
    <span
      @click="handleToClickCrumb(item,false,index)"
      :style="index===(titleList.length-1)?'cursor:auto;text-decoration: none;color:#666;':'color:#666;'"
      v-for="(item,index) in titleList"
      :key="index"
    ><i v-if="index !==0"> / {{item}}</i></span>
    <span class="showLink">
      <el-link
        v-if="$route.name == 'pin_integration'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2136&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
      >瓜分积分使用教程</el-link>
      <el-link
        v-if="$route.name == 'friend_pay'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2116&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
        type="primary"
        :underline="false"
        target="_blank"
      >好用代付使用教程</el-link>
      <el-link
        v-if="$route.name == 'formStatistical'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=65&extra=page=1&filter=sortid&sortid=15"
        type="primary"
        :underline="false"
        target="_blank"
      >表单统计使用教程</el-link>
    </span>
  </div>
</template>
<script>
export default {
  data () {
    return {
      titleLeft: '',
      titleList: '',
      lang: '',
      isSurvey: true, // 若是概况里的商城概览则隐藏面包屑
      isLink: false, // 瓜分积分显示的一个跳转链接
      nameArr: ['addRecommend', 'addGoodsLabel', 'updateGoodsLabel', 'store_storemanage_reservation', 'addBrand', 'ordinary_coupon_receive_details', 'feedbackList', 'formDecorationHome', ' pin_group_activityEffectData', 'bargain_effect_data', 'bargain_bargain_user', ' bargain_get_newuser_detail', 'bargain_order_list', 'bargain_activity', ' pin_group_refundFailureOrder', ' pin_group_newUserDetail', 'pin_group_orderList', 'pin_group_detailList', 'group_draw_effect', 'group_draw_group', 'group_draw_user', 'group_draw_order', 'promote_activity', 'promote_receive_details', 'promote_launch_details', 'promote_participate_details', 'promote_effect_data', 'lottery_activity_detail', 'lottery_activity_newUserList', 'open_screen_detail', 'store_storemanage_reservation_detail', 'store_storemanage_reservation_record', 'store_verification_list', 'sec_kill_order', 'sec_kill_detail', 'sec_kill_user', 'sec_kill_effect', 'group_draw_detail', 'gift_detail_view', 'gift_add_view', 'newUserDetails', 'pointsUserList', 'pointsExchangeOrder', 'template_detail', 'add_message', 'send_record', 'gift_edit_view'], // name池,
      turnArr: ['recommend', 'label', 'label', 'store_list', 'brand', 'ordinary_coupon', 'formStatistical', 'formStatistical', 'pin_group', 'bargain', 'bargain', 'bargain', 'bargain', 'bargain', 'pin_group', 'pin_group', 'pin_group', 'pin_group', 'group_draw', 'group_draw', 'group_draw', 'group_draw', 'promote', 'promote', 'promote', 'promote', 'lottery_activity', 'lottery_activity', 'market_gifted', 'store_list', 'store_list', 'store_list', 'sec_kill', 'sec_kill', 'sec_kill', 'sec_kill', 'group_draw', 'gift', 'gift', 'integralExchangeHome', 'integralExchangeHome', 'integralExchangeHome', 'integralExchangeHome', 'all_message_push', 'all_message_push', 'all_message_push', 'gift']// 跳转池
    }
  },
  watch: {
    lang (newData) {
      console.log(newData)
      this.changeText()
    },
    '$route.name' (newData, oldData) {
      console.log(newData, oldData)
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
        // if (routeName === 'pin_integration' || routeName === 'friend_pay') {
        //   this.isLink = true
        // } else {
        //   this.isLink = false
        // }
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
      // 处理积分兑换子页面面包屑
      this.handleToOointExchange(data)
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
    handleToClickCrumb (name, flag, index) {
      console.log(name, flag, this.$route, this.nameArr.length, this.turnArr.length)
      if (this.titleList.length < 3) return
      if (index === (this.titleList.length - 1)) return
      if (flag) {
        if (this.$route.meta.meta === 'user_manger') {
          this.$router.push({
            name: this.$route.meta.category
          })
        } else if (this.$route.meta.meta === 'first_market_manage') {
          this.$router.push({
            name: 'first_market_manage'
          })
        } else {
          console.log(this.$route)
          this.$router.push({
            name: this.turnArr[this.nameArr.indexOf(this.$route.name)]
          })
        }
      } else {
        if (this.$route.name === 'distribution_info') {
          this.$http.$emit('toChangeActiveName', true)
        } else if (this.$route.meta.meta === 'user_manger') {
          this.$router.push({
            name: this.$route.meta.category
          })
        } else if (this.$route.name === 'feedbackDetails') {
          console.log(name)
          if (name === '反馈列表') {
            this.$router.push({
              name: 'feedbackList'
            })
          } else if (name === '表单统计') {
            this.$router.push({
              name: 'formStatistical'
            })
          }
        } else {
          console.log(this.$route)
          if (name === '表单统计') {
            this.$router.push({
              name: 'formStatistical'
            })
          } else if (name === '砍价') {
            this.$router.push({
              name: 'bargain'
            })
          } if (name === '预约管理') {
            this.$router.push({
              name: 'store_storemanage_reservation'
            })
          } else {
            console.log(this.$route.name, this.nameArr.indexOf(this.$route.name), this.turnArr[this.nameArr.indexOf(this.$route.name)])
            if (name === '发起砍价用户列表') {
              this.$router.push({
                name: 'bargain_bargain_user'
              })
            } else if (name === '砍价') {
              this.$router.push({
                name: 'bargain'
              })
            } else {
              this.$router.push({
                name: this.turnArr[this.nameArr.indexOf(this.$route.name)]
              })
            }
          }
        }
      }
      console.log(name, this.$route)
      //
    },
    // 处理积分兑换子页面面包屑
    handleToOointExchange (data) {
      console.log(data)
      if (this.$route.name === 'pointsExchangeOrder') {
        let str = this.$route.query.activityName + ' - ' + '积分兑换订单'
        data.push(str)
        this.titleLeft = data[0]
        this.titleList = data
      } else if (this.$route.name === 'newUserDetails') {
        let str = this.$route.query.activityName + ' - ' + '获取新用户明细'
        data.push(str)
        this.titleLeft = data[0]
        this.titleList = data
      }
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
  overflow: hidden;
}
.canClick {
  span {
    cursor: pointer;
    &:hover {
      text-decoration: underline;
    }
  }
}
.showLink {
  float: right;
  margin-right: 2%;
}
.crumbs-right {
  float: right;
}
</style>
