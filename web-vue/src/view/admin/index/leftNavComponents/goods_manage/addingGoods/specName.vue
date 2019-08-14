<template>
  <div class="specName">
    规格名：
    <span class="wrap">
      <el-input
        class="input-new-tag"
        v-model="specName"
        ref="specName"
        size="small"
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
      >
      </el-input>
      <img
        src="../../../../../../assets/image/admin/icon_delete.png"
        alt="icon_delete"
        class="icon_delete"
        @click="handleHide"
      >
    </span>

  </div>
</template>
<script>
export default {
  name: 'specName',
  created () {

  },
  data () {
    return {
      specName: ''

    }
  },
  methods: {
    handleHide () {
      this.$emit('hide')
    },
    handleInputConfirm () {
      // console.log(this.specName)
      this.$emit('sendSpecName', this.specName)
    }
  }
}
</script>
<style scoped>
.wrap {
  position: relative;
  display: inline-block;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
.icon_delete {
  position: absolute;
  cursor: pointer;
  right: -4px;
  top: -4px;
  vertical-align: middle;
}
</style>
