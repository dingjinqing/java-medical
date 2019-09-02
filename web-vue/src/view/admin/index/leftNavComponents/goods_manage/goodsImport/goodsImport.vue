<template>
  <div>
    <areaLinkage @areaData="handleAreaData" />
  </div>
</template>
<script>
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
export default {
  components: { areaLinkage },
  methods: {
    handleAreaData (val) {
      console.log(val)
    }
  }
}
</script>
