<template>
   <div class="auth-list">
      <div class="menu-list pd-10 bg-white mt-10">
        <el-select
          class="fll selected-id"
          v-model="selectedId"
          placeholder="请选择模板id"
          size="small"
        >
          <el-option v-for="(item, index) in selectIdOpt"
                     :key="index"
                     :value="item.value"
                     :label="item.label"
          />
        </el-select>
        <el-select
          class="fll selected-id ml-6"
          v-model="selectedAuth"
          placeholder="请选择是否授权"
          size="small"
        >
          <el-option v-for="(item, index) in selectIdOpt"
                     :key="index"
                     :value="item.value"
                     :label="item.label"
          />
        </el-select>

      </div>
   </div>
</template>

<script>
export default {
  name: 'authList',
  data () {
    return {
      selectedId: '',
      selectedAuth: '',
      selectIdOpt: [
        {
          value: '1',
          label: '1.0.0'
        },
        {
          value: '2',
          label: '2.0.0'
        }
      ]
    }
  }
}
</script>

<style scoped lang="scss">
.selected-id {
  width: 140px;
}
</style>
