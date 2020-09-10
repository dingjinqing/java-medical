<template>
    <div class="total-content">
        <div class="container">
            <anchorPointsMoneyReport class="distance" />
            <anchorPointsCountReport class="distance" />
        </div>
    </div>
</template>
<script>
import anchorPointsCountReport from './anchorPointsCountReport'
import anchorPointsMoneyReport from './anchorPointsMoneyReport'

export default {
  components: {anchorPointsCountReport, anchorPointsMoneyReport},
  data () {
    return {}
  }
}
</script>
<style lang="scss" scoped>
    .total-content {
        font-size: 14px;
        margin-top: 10px;
        .container {
            position: relative;
            .distance {
                margin-left: 10px;
                margin-right: 10px;
                margin-bottom: 10px;
            }
        }
    }
</style>
