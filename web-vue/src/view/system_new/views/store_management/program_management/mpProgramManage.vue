<template>
  <div class="mpProgramManage">
    <breadcrumb />
    <programMange />
  </div>
</template>

<script>
import breadcrumb from '../../../layout/components/breadcrumb'
import programMange from './programManage'

export default {
  name: 'mpProgramManage',
  components: {
    breadcrumb,
    programMange
  },
  data () {
    return {}
  }
}
</script>

<style scoped lang="scss">
</style>
