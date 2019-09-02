<template>
  <div>
    <areaLinkage @areaData="handleAreaData" />
  </div>
</template>
<script>
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
export default {
  components: { areaLinkage },

  data () {
    return {
      province: ``,
      district: ``,
      city: ``
    }
  },

  methods: {
    data2 () {
      const res = this.data1()
      console.log(res)
    },
    handleAreaData (val) {
      this.province = val['province']
    },
    data1 () {
      return {

      }
    }
  }
}
</script>
