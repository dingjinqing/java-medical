<template>
  <div class="modules">
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <div>我要砍价</div>
    </div>
    <!--放这里-->
    <div
      class="setHere activeSetHere"
      :class="activeSetHere?'middleModulesActive':''"
    >
      {{$t('commoditySearch.putItHere')}}
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {}
  }
}
</script>
