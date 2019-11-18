
<template>
  <div class="userStatistics">
    <div class="userContainer">
      <overviewAndTrend class="distance" />
      <userActive class="distance" />
      <member class="distance" />
      <userAnalysis class="distance" />
      <userBuy />
    </div>
  </div>
</template>

<script>
import overviewAndTrend from './overviewAndTrend'
import userActive from './userActive'
import member from './member'
import userAnalysis from './userAnalysis'
import userBuy from './userBuy'

export default {
  components: { overviewAndTrend, userActive, member, userAnalysis, userBuy },
  data () {
    return {

    }
  }
}

</script>
<style lang="scss" scoped>
.userStatistics {
  font-size: 14px;
  .userContainer {
    position: relative;
    .distance {
      margin-bottom: 10px;
    }
  }
}
</style>
