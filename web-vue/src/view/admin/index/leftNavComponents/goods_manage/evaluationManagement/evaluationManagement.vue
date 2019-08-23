<template>
  <div class="evaluationManagement">
    <el-button @click="handleShare">分享</el-button>
    <!-- <testDialog
      :isShow="isShow"
      @test="handleTest"
    /> -->
  </div>
</template>
<script>
// import testDialog from './testDialog'
export default {
  // components: { testDialog },
  data () {
    return {
      isShow: true
    }
  },
  mounted () {

  },
  methods: {
    handleShare () {

    },
    handleTest (val) {
      // console.log(val)
    }
  }
}
</script>
<style scoped>
</style>
