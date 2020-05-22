<template>
  <div class="evaluationManagement">
    <evaluationManagement />
  </div>
</template>
<script>
export default {
  components: {
    evaluationManagement: () => import('./evaluationManagementContent')
  },
  data () {
    return {
      isShow: true
    }
  },
  mounted () {

  },
  methods: {
  }
}
</script>
<style scoped>
</style>
