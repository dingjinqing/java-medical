<template>
  <div class="deliverContent">
    <el-card>
      <deliverHeader />
      <deliverMain />
    </el-card>
  </div>
</template>
<script>
import deliverMain from './deliverMain'
import deliverHeader from './deliverHeader'
export default {
  name: 'deliverContent',
  components: { deliverHeader, deliverMain },
  data () {
    return {

    }
  }
}
</script>
<style scoped>
.deliverContent {
  padding: 10px;
  height: 1119px;
}
</style>
