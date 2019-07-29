<template>
  <div>
    <Crumbs :mTitle="M_title" />
    <MembershipListContent />
  </div>
</template>
<script>
import Crumbs from '@/components/admin/crumbs'
import MembershipListContent from '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipListContent'
export default {
  components: { Crumbs, MembershipListContent },
  data () {
    return {
      M_title: [
        '会员管理', '会员列表'
      ]
    }
  }
}
</script>
<style scoped>
</style>
