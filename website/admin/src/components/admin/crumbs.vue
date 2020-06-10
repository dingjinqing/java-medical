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
        v-if="$route.name == 'distribution_info'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=50&extra=page=1&filter=sortid&sortid=15"
      >分销使用教程</el-link>
      <el-link
        v-if="$route.name == 'gift'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2118&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
      >赠品使用教程</el-link>
      <el-link
        v-if="$route.name == 'promote'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=736&extra=page=1&filter=sortid&sortid=15"
      >好友助力使用教程</el-link>
      <el-link
        v-if="$route.name == 'sec_kill'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=69"
      >秒杀活动使用教程</el-link>
      <el-link
        v-if="$route.name == 'ordinary_coupon'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=52&extra=page=1&filter=sortid&sortid=15"
      >普通优惠券使用教程</el-link>
      <el-link
        v-if="$route.name == 'free_ship'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2120&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
      >满包邮使用教程</el-link>
      <el-link
        v-if="$route.name == 'group_draw'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2135&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
      >拼团抽奖使用教程</el-link>
      <el-link
        v-if="$route.name == 'reduce_price'"
        :underline="false"
        type="primary"
        target="_blank"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2112&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
      >限时降价使用教程</el-link>
      <el-link
        v-if="$route.name == 'insteadpay'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2116&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
        type="primary"
        :underline="false"
        target="_blank"
      >好友代付使用教程</el-link>
      <el-link
        v-if="$route.name == 'form_decoration'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=65&extra=page=1&filter=sortid&sortid=15"
        type="primary"
        :underline="false"
        target="_blank"
      >表单统计使用教程</el-link>
      <el-link
        v-if="$route.name == 'live_broadcast'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2222&fromuid=1"
        type="primary"
        :underline="false"
        target="_blank"
      >直播使用教程</el-link>
      <el-link
        v-if="$route.name == 'share_award'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2133&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
        type="primary"
        :underline="false"
        target="_blank"
      >分享有礼使用教程</el-link>
      <el-link
        v-if="$route.name == 'pin_group'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=48&extra=page=1&filter=sortid&sortid=15"
        type="primary"
        :underline="false"
        target="_blank"
      >多人拼团使用教程</el-link>
      <el-link
        v-if="$route.name == 'lottery_activity'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=51&extra=page=1&filter=sortid&sortid=15"
        type="primary"
        :underline="false"
        target="_blank"
      >幸运大抽奖使用教程</el-link>
      <el-link
        v-if="$route.name == 'kanjia'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=49&extra=page=1&filter=sortid&sortid=15"
        type="primary"
        :underline="false"
        target="_blank"
      >砍价活动使用教程</el-link>
      <el-link
        v-if="$route.name == 'package'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2114&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
        type="primary"
        :underline="false"
        target="_blank"
      >打包一口价使用教程</el-link>
      <el-link
        v-if="$route.name == 'integral_convert'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=54&extra=page=1&filter=sortid&sortid=15"
        type="primary"
        :underline="false"
        target="_blank"
      >积分兑换使用教程</el-link>
      <el-link
        v-if="$route.name == 'comment_gift'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2121&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
        type="primary"
        :underline="false"
        target="_blank"
      >评价有礼使用教程</el-link>
      <el-link
        v-if="$route.name == 'market_act_give'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=53&extra=page=1&filter=sortid&sortid=15"
        type="primary"
        :underline="false"
        target="_blank"
      >定向发券使用教程</el-link>
      <el-link
        v-if="$route.name == 'all_message_push'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=66"
        type="primary"
        :underline="false"
        target="_blank"
      >消息推送使用教程</el-link>
      <el-link
        v-if="$route.name == 'coupon_package'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=2127&extra=page%3D1%26filter%3Dsortid%26sortid%3D15"
        type="primary"
        :underline="false"
        target="_blank"
      >优惠卷礼包使用教程</el-link>
      <el-link
        v-if="$route.name == 'payreward'"
        href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=61&extra=page=1&filter=sortid&sortid=15"
        type="primary"
        :underline="false"
        target="_blank"
      >支付有礼使用教程</el-link>
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
      nameArr: ['addRecommend', 'addGoodsLabel', 'updateGoodsLabel', 'store_storemanage_reservation', 'addBrand', 'ordinary_coupon_receive_details', 'feedbackList', 'formDecorationHome', ' pin_group_activityEffectData', 'bargain_effect_data', 'bargain_bargain_user', ' bargain_get_newuser_detail', 'bargain_order_list', 'bargain_activity', ' pin_group_refundFailureOrder', ' pin_group_newUserDetail', 'pin_group_orderList', 'pin_group_detailList', 'group_draw_effect', 'group_draw_group', 'group_draw_user', 'group_draw_order', 'promote_activity', 'promote_receive_details', 'promote_launch_details', 'promote_participate_details', 'promote_effect_data', 'lottery_activity_detail', 'lottery_activity_newUserList', 'open_screen_detail', 'store_storemanage_reservation_detail', 'store_storemanage_reservation_record', 'store_verification_list', 'sec_kill_order', 'sec_kill_detail', 'sec_kill_user', 'sec_kill_effect', 'group_draw_detail', 'gift_detail_view', 'gift_add_view', 'newUserDetails', 'pointsUserList', 'pointsExchangeOrder', 'template_detail', 'add_message', 'send_record', 'gift_edit_view', 'first_special_order', 'couponGive_receive_details', 'couponGive_grant_coupons', 'presale_order_detail_view', 'presale_detail_view', 'package_price_order', 'package_price_detail', 'addCalendarMain', 'feedbackStatistics', 'group_integration_detail', 'group_integration_success', 'group_integration_analysis', 'coupon_Package_add', 'coupon_Package_receive_details', 'coupon_Package_order_details', 'store_announcement_add', 'ordinary_coupon_user_detail'], // name池,
      turnArr: ['recommend', 'label', 'label', 'store_list', 'brand', 'ordinary_coupon', 'form_decoration', 'form_decoration', 'pin_group', 'kanjia', 'kanjia', 'kanjia', 'kanjia', 'kanjia', 'pin_group', 'pin_group', 'pin_group', 'pin_group', 'group_draw', 'group_draw', 'group_draw', 'group_draw', 'promote', 'promote', 'promote', 'promote', 'promote', 'lottery_activity', 'lottery_activity', 'market_gifted', 'store_list', 'store_list', 'store_list', 'sec_kill', 'sec_kill', 'sec_kill', 'sec_kill', 'group_draw', 'gift', 'gift', 'integral_convert', 'integral_convert', 'integral_convert', 'all_message_push', 'all_message_push', 'all_message_push', 'gift', 'first_special', 'market_act_give', 'market_act_give', 'presale', 'presale', 'package', 'package', 'calendar', 'form_decoration', 'pin_integration', 'pin_integration', 'pin_integration', 'coupon_package', 'coupon_package', 'coupon_package', 'store_announcement', 'ordinary_coupon']// 跳转池
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
        console.log(this.isSurvey)
        // if (routeName === 'pin_integration' || routeName === 'friend_pay') {
        //   this.isLink = true
        // } else {
        //   this.isLink = false
        // }
      }
      // console.log(this.$t(`${this.$route.meta.crumbTitle}`))
      let data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle)))
      if (Array.isArray(this.$route.meta.crumbTitle) && this.$route.meta.crumbTitle[0].indexOf('add') > -1) {
        if (this.$route.query.id) {
          data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle[1])))
        } else {
          data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle[0])))
        }
      }
      console.log(data, this.$route)
      if (data[1] === '会员列表' && data[2]) {
        data[2] = this.$route.query.name + '-' + data[2]
      }
      if (data[0] === '订单管理' && data[1] === '全部订单' && this.$route.query.userName) {
        data[1] = this.$route.query.userName + '-' + data[1]
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
        console.log(data)
        this.titleLeft = data[0]
        this.titleList = data
      }
      // 分销员
      this.$http.$on('distributionTap', (index) => {
        console.log(index)
        switch (index) {
          case 0:
            data[2] = '分销配置'
            console.log(data)
            break
          case 1:
            data[2] = '分销员等级配置'
            console.log(data)
            break
          case 2:
            data[2] = '返利策略配置'
            break
          case 3:
            data[2] = '分销员列表'
            break
          case 4:
            data[2] = '分销员分组'
            break
          case 5:
            data[2] = '佣金统计'
            break
          case 6:
            data[2] = '商品返利统计'
            break
          case 7:
            data[2] = '返利提现审核'
            break
          case 8:
            data[2] = '分销员审核'
            break
          case 9:
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
      if (Array.isArray(this.$route.meta.crumbTitle) && this.$route.meta.crumbTitle[0].indexOf('add') > -1) {
        if (this.$route.query.id) {
          data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle[1])))
        } else {
          data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle[0])))
        }
      }
      this.titleLeft = data.concat(this.titleList)[0]
      this.titleList = data.concat(this.titleList)
    },
    // 处理会员充值明细数据
    handleToRefllDet (newData) {
      console.log(newData)
      this.titleList = [`${newData.item.cardName} -- 会员卡充值明细`]
      let data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle)))
      if (Array.isArray(this.$route.meta.crumbTitle) && this.$route.meta.crumbTitle[0].indexOf('add') > -1) {
        if (this.$route.query.id) {
          data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle[1])))
        } else {
          data = JSON.parse(JSON.stringify(this.$t(this.$route.meta.crumbTitle[0])))
        }
      }
      this.titleLeft = data.concat(this.titleList)[0]
      this.titleList = data.concat(this.titleList)
      console.log(data, this.titleList)
    },
    handleToClickCrumb (name, flag, index) {
      console.log(name, flag, this.$route, this.nameArr.length, this.turnArr.length)
      let routeName = this.$route.name
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
              name: 'form_decoration'
            })
          }
        } else if (routeName === 'add_increase_purchase' || routeName === 'edit_increase_purchase' || routeName === 'purchase_redemption_order' || routeName === 'purchase_redemption_detail') {
          this.$router.push({
            name: 'purchase_price'
          })
        } else if (routeName === 'activityDetails') {
          this.$router.push({
            name: 'payreward'
          })
        } else if (routeName === 'share_polite_detail') {
          this.$router.push({
            name: 'share_award'
          })
        } else if (routeName === 'share_polite_add') {
          this.$router.push({
            name: 'share_award'
          })
        } else if (routeName === 'bargain_activity') {
          this.$router.push({
            name: 'kanjia'
          })
        } else {
          console.log(this.$route)
          if (name === '表单统计') {
            this.$router.push({
              name: 'form_decoration'
            })
          } else if (this.$route.meta.category === 'kanjia') {
            this.$router.push({
              name: 'kanjia'
            })
          } else if (name === '限时降价') {
            this.$router.push({
              name: 'reduce_price'
            })
          } else if (name === '预约管理') {
            this.$router.push({
              name: 'store_storemanage_reservation'
            })
          } else if (this.$route.name === 'fullCutOrder' || this.$route.name === 'fullCutStatistics') {
            this.$router.push({
              name: 'full_cut'
            })
          } else if (this.$route.matched[2].name === 'store_storemanage_list') {
            this.$router.push({
              name: 'store_list'
            })
          } else {
            console.log(this.$route.name, this.nameArr.indexOf(this.$route.name), this.turnArr[this.nameArr.indexOf(this.$route.name)])
            if (name === '发起砍价用户列表') {
              this.$router.push({
                name: 'bargain_bargain_user'
              })
            } else {
              console.log(this.$route.name, this.nameArr.indexOf(this.$route.name), this.turnArr[this.nameArr.indexOf(this.$route.name)])
              if (this.nameArr.indexOf(this.$route.name) === -1 && this.$route.meta.category) {
                this.$router.push({
                  name: this.$route.meta.category
                })
                return false
              }
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
      } else if (this.$route.name === 'addCalendarMain') {
        let str = ''
        if (this.$route.query.isAdd) {
          str = '添加事件'
        } else {
          str = '编辑事件'
        }
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
  z-index: 30;
  // overflow: hidden;
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
