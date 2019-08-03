<template>
  <div class="addingGoodsFooter">
    <section class="addingGoodsFooter">
      <el-button
        type="primary"
        size="small"
        v-show="toList"
        @click.native.prevent="handleToList"
      >保存后返回列表</el-button>
      <el-button
        size="small"
        v-show="preStep"
        @click.native.prevent="handlePreStep"
      >上一步</el-button>
      <el-button
        size="small"
        v-show="nextStep"
        @click.native.prevent="handleNextStep"
      >下一步</el-button>
      <el-button
        type="primary"
        size="small"
        v-show="addAfterSaving"
        @click.native.prevent="handleAddAfterSaving"
      >保存后继续添加</el-button>
      <el-button
        type="primary"
        size="small"
        v-show="preview"
        @click.native.prevent="handlePreview"
      >保存后预览商品</el-button>
    </section>
  </div>
</template>
<script>

export default {
  name: 'addingGoodsFooter',
  props: {
    active: Number
  },
  created () {
    this.nextStep = true
  },
  data () {
    return {
      val: '',
      toList: true,
      preStep: false,
      nextStep: false,
      addAfterSaving: false,
      preview: false
    }
  },
  methods: {
    handleToList () {

    },
    handlePreStep () {
      this.$emit('pre')
    },
    handleNextStep () {
      this.$emit('next')
      this.preStep = true
      this.addAfterSaving = true
      this.preview = true
    },
    handleAddAfterSaving () {

    },
    handlePreview () {

    }
  }
}
</script>
<style scoped>
</style>
