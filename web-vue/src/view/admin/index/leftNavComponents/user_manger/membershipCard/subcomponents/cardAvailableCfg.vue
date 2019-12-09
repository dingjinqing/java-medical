<template>
  <div class="card-available-div">
    <el-form
      status-icon
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item
        :label="$t('memberCard.cardStartUse')"
        class="available-item"
      >
        <el-radio
          v-model="data.flag"
          label="1"
        >
          {{$t('memberCard.startUse')}}
        </el-radio>
        <el-radio
          v-model="data.flag"
          label="2"
        >
          {{$t('memberCard.stopUse')}}
        </el-radio>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  props: {
    val: {
      type: Object,
      required: true
    }
  },
  computed: {
    data: {
      get () {
        return this.val
      },
      set () {
        this.$emit('input', this.data)
      }
    }
  },
  watch: {
    'data.flag': {
      handler (newName, oldName) {
        this.val.flag = newName
        this.data = this.val
      },
      immediate: true
    }
  }
}
</script>
<style scoped lang="scss">
.card-available-div {
  .available-item {
    padding-left: 100px;
  }
}
</style>
