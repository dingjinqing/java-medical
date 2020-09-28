<template>
  <div class="inline-img">
    <el-image
      ref="elImg"
      class="img-view"
      :src="src"
      :preview-src-list="[src]"
    ></el-image>
    <el-button
      type="text"
      style="margin-left:10px;"
      @click="handleShowImg"
    >{{text}}</el-button>
  </div>
</template>
<script>
export default {
  name: 'imageView',
  props: {
    src: {
      type: String,
      required: true
    },
    text: {
      type: String,
      required: true
    }
  },
  data () {
    return {}
  },
  methods: {
    handleShowImg () {
      const that = this
      that.$refs.elImg.clickHandler()
      that.$nextTick(() => {
        const mask = document.querySelector('.el-image-viewer__mask')
        mask.addEventListener('click', () => {
          that.$refs.elImg.closeViewer()
        }, false)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.inline-img {
    display: inline-block;
    .img-view {
    width: 0;
    height: 0;
        /deep/ .el-image-viewer__btn {
            color: white;
        }
    }
}

</style>
