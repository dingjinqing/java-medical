<template>
  <div>
    <!-- <Crumbs :mTitle="M_title" /> -->

    <MembershipListContent v-if="flagIndex===0" />
    <MembershipInformation v-if="flagIndex===1" />
    <ReceiveDetail v-if="flagIndex===2" />
    <BalanceDetail v-if="flagIndex===3" />
    <IntegralDetail v-if="flagIndex===4" />

  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import MembershipListContent from '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipListContent'
import MembershipInformation from '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipInformation'
import ReceiveDetail from '@/view/admin/index/leftNavComponents/user_manger/membershipList/receiveDetail'
import BalanceDetail from '@/view/admin/index/leftNavComponents/user_manger/membershipList/balanceDetail'
import IntegralDetail from '@/view/admin/index/leftNavComponents/user_manger/membershipList/integralDetail'
export default {
  components: { MembershipListContent, MembershipInformation, ReceiveDetail, BalanceDetail, IntegralDetail },
  data () {
    return {
      flagIndex: 0
    }
  },
  computed: {
    ...mapGetters(['membershipdetailflag']),
    membershipdetailflag_ () {
      return this.membershipdetailflag
    }
  },
  watch: {
    membershipdetailflag_ (newData) {
      console.log(newData, 1)
      switch (newData) {
        case '0':
          this.flagIndex = 0
          break
        case 'memberDetail':
          console.log(1)
          this.flagIndex = 1
          break
        case 'receiveDetail':
          this.flagIndex = 2
          break
        case 'balanceDetail':
          this.flagIndex = 3
          break
        case 'integralDetail':
          this.flagIndex = 4
          break
      }
    }
  },
  mounted () {
    // 标签模块跳转
    if (this.$route.query.name) {
      console.log(this.$route.query.name)
    }

    console.log(this.flagIndex)
    console.log(this.membershipdetailflag, this.flagIndex)
    switch (this.membershipdetailflag) {
      case 'memberDetail':
        console.log(1)
        this.flagIndex = 1
        break
      case 'receiveDetail':
        this.flagIndex = 2
        break
      case 'balanceDetail':
        this.flagIndex = 3
        break
      case 'integralDetail':
        this.flagIndex = 4
        break
    }
    // this.flagIndex = this.membershipdetailflag
  },
  beforeCreate () {
    console.log('beforeCreate.')
  },
  created () {
  }
}
</script>
<style scoped>
</style>
