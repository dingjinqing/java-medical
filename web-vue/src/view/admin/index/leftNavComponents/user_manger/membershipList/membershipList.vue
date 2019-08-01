<template>
  <div>
    <Crumbs :mTitle="M_title" />
    <MembershipListContent v-if="flagIndex===0" />
    <MembershipInformation v-if="flagIndex===1" />
    <ReceiveDetail v-if="flagIndex===2" />
    <BalanceDetail v-if="flagIndex===3" />
    <IntegralDetail v-if="flagIndex===4" />
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import Crumbs from '@/components/admin/crumbs'
import MembershipListContent from '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipListContent'
import MembershipInformation from '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipInformation'
import ReceiveDetail from '@/view/admin/index/leftNavComponents/user_manger/membershipList/receiveDetail'
import BalanceDetail from '@/view/admin/index/leftNavComponents/user_manger/membershipList/balanceDetail'
import IntegralDetail from '@/view/admin/index/leftNavComponents/user_manger/membershipList/integralDetail'
export default {
  components: { Crumbs, MembershipListContent, MembershipInformation, ReceiveDetail, BalanceDetail, IntegralDetail },
  data () {
    return {
      M_title: [
        '会员管理', '会员列表'
      ],
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
    // this.
  }
}
</script>
<style scoped>
</style>
