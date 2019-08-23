<template>
  <div class="content">
    <div>
      <integrationAdd />
    </div>
  </div>
</template>
<script>
import integrationAdd from './groupIntegrationDetail'
export default {
  components: {
    integrationAdd
  },
  data () {
    return {

    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
}
</style>
