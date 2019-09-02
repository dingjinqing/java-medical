<template>
  <div class="">
    <el-card>
      <headerTabs :labels="labels" />
    </el-card>

  </div>
</template>
<script>
import headerTabs from '@/components/admin/headerTabs/headerTabs'
export default {
  name: '',
  components: {
    headerTabs
  },
  data () {
    return {
      labels: [
        {
          label: `运费模板列表`,
          name: `deliverTemplateList`
        },
        {
          label: `重量运费模板列表`,
          name: `deliverTemplateWeightList`
        },
        {
          label: `添加运费模板`,
          name: `deliverTemplateAdd`
        },
        {
          label: `添加重量运费模板`,
          name: `deliverTemplateWeightAdd`
        }
      ]
    }
  }
}
</script>
