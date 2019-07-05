<template>
  <div>
    <el-dialog
      title="浏览图片"
      :visible.sync="dialogTableVisible"
      width="825px"
    >
      <div class="dialog_top">
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-remove="handleRemove"
          :on-success="handleSuccess"
          :before-remove="beforeRemove"
          multiple
          :limit="3"
          :on-exceed="handleExceed"
        >
          <el-button
            size="small"
            type="primary"
          >上传图片</el-button>
          <div
            slot="tip"
            class="tips"
          >
            <img :src="imgUrl[0].img_1">
            上传图片支持jpeg、jpg、png、bmp格式，为保障前端加载顺利，单张图片大小不能超过5M</div>
        </el-upload>
      </div>
      <div class="dialog_middle">

      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogTableVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {
  data () {
    return {
      dialogTableVisible: false,
      imgUrl: [
        {
          img_1: this.$imageHost + '/image/admin/notice_img.png'
        }
      ]
    }
  },
  mounted () {
    // accountSettings组件控制本组件弹窗
    this.$http.$on('dtVisible', () => {
      this.dialogTableVisible = true
    })
  },
  methods: {
    // 文件上传成功后的钩子
    handleSuccess () {

    },
    // 删除前的钩子
    beforeRemove () {

    },
    // 图片删除钩子
    handleRemove () {

    },
    // 文件数量超出限制钩子
    handleExceed () {

    }
  }
}
</script>
<style scoped>
.tips {
  height: 30px;
  line-height: 30px;
  padding: 0 20px;
  color: #666;
  font-size: 12px;
  background-color: #fff7eb;
  border: 1px solid #ffd5a3;
  display: flex;
  align-items: center;
}
.tips img {
  margin-right: 10px;
}
.dialog_middle {
  height: 320px;
}
</style>
