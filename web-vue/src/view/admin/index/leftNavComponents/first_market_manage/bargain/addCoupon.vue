<template>
  <div class="addCoupon">
    <ul class="pictures">
      <li
        v-for="(item,index) in imgLists"
        :key="index"
        class="oneImg"
      >
        <el-image
          style="width: 80px; height: 80px"
          :src="item"
          fit="fill"
        ></el-image>
      </li>
      <li
        class="oneImg"
        @click="handleToCallDialog"
      >
        <img
          :src="src"
          alt=""
        >
      </li>
    </ul>
  </div>
</template>
<script>

export default {

  data () {
    return {
      src: `${this.$imageHost}/image/admin/add_img.png`,
      imgLists: []
    }
  },
  methods: {
    handleSelectImg (res) {
      console.log(res)

      // if (res != null) {
      //   this.imgLists.push(res)
      //   this.$emit('imgListChange', this.imgLists.slice())
      // }
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
