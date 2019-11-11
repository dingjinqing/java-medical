<template>
  <div class="userStatistics">
    <div class="userContainer">
      <overviewAndTrend style="margin-bottom: 10px;" />
      <userActive style="margin-bottom: 10px;" />
      <member style="margin-bottom: 10px;" />
      <userAnalysis />
    </div>
  </div>
</template>

<script>
import overviewAndTrend from './overviewAndTrend'
import userActive from './userActive'
import member from './member'
import userAnalysis from './userAnalysis'

export default {
  components: { overviewAndTrend, userActive, member, userAnalysis },
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
    padding: 10px;
    position: relative;
    .top {
      margin-bottom: 10px;
    }
  }
}
</style>
