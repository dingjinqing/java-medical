<template>
  <div class="deliverTemplateWeightList">
    <!-- 重量列表 -->
    <deliverTemplateTable :listType="weightList" />
  </div>
</template>
<script>
import deliverTemplateTable from './deliverTemplateTable'
export default {
  name: `deliverTemplateWeightList`,
  components: { deliverTemplateTable }

}
</script>
