<template>
  <div class="goodsMainPic">
    <ul class="pictures">
      <li
        v-for="(item,index) in imgLists"
        :key="index"
        class="oneImg"
        v-show="isShowPic"
      >
        <el-image
          style="width: 80px; height: 80px"
          :src="item.url"
          fit="fill"
        ></el-image>
      </li>
      <li
        class="oneImg"
        @click="handleShowDialog"
      >
        <img
          src="../../../../../../assets/image/admin/add_img.png"
          alt=""
        >
      </li>
    </ul>
    <ImageDalog
      pageIndex='pictureSpace'
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>
<script>
import ImageDalog from '@/components/admin/imageDalog'

export default {
  components: { ImageDalog },
  data() {
    return {
      src: '',
      isShowPic: false,

      imgLists: []
    }
  },
  methods: {
    handleShowDialog() {
      this.$http.$emit('dtVisible')
    },
    handleSelectImg(res) {
      console.log(res)

      this.imgLists.push({
        url: res
      })

      this.isShowPic = true
    }
  }
}
</script>
<style lang="css">
.oneImg {
  float: left;
  margin: 0 5px;
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
