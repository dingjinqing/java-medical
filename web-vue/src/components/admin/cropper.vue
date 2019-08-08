<template>
  <div>
    <!--图片裁剪-->
    <div class="vue-cropper">
      <el-dialog
        title="裁剪图片"
        :visible.sync="dialogVisible"
        width="35%"
      >
        <div class="CropperContainer">
          <div class="CropperTop">
            <div class="topDiv">
              <div>裁剪图片宽度：</div>
              <el-input
                v-model="cropperTopInput_one"
                size="mini"
                @blur="handleBlur(0)"
                :disabled="true"
              ></el-input>
            </div>
            <div class="topDiv">
              <div>裁剪图片高度：</div>
              <el-input
                v-model="cropperTopInput_two"
                size="mini"
                @blur="handleBlur(1)"
                :disabled="true"
              ></el-input>
            </div>
            <div class="topDiv">
              <div>裁剪图片比例：</div>
              <el-input
                v-model="cropperTopInput_three"
                size="mini"
                @blur="handleBlur(2)"
                :disabled="true"
              ></el-input>
            </div>
          </div>
          <div class="tips">
            裁剪后图片高为空，裁剪为实际宽高存储
          </div>
          <div class="middleCOntainer">
            <div class="CropperMiddle">
              <vueCropper
                ref="cropper"
                :img="option.img"
                :outputSize="option.size"
                :outputType="option.outputType"
                :info="false"
                :full="option.full"
                :canMove="option.canMove"
                :canMoveBox="option.canMoveBox"
                :original="option.original"
                :autoCrop="option.autoCrop"
                :fixedNumber="option.fixedNumber"
                :centerBox="option.centerBox"
                :infoTrue="option.infoTrue"
                :fixedBox="option.fixedBox"
                :autoCropWidth="option.autoCropWidth"
                :autoCropHeight="option.autoCropHeight"
                :enlarge="option.enlarge"
                :fixed='true'
                @realTime="realTime"
                @cropMoving='cropMoving'
              ></vueCropper>
            </div>
            <div
              class="show-preview"
              :style="{'width': previews.w + 'px', 'height': previews.h + 'px',  'overflow': 'hidden','margin': '5px'}"
            >
              <div :style="previews.div">
                <img
                  :src="option.img"
                  :style="previews.img"
                >
              </div>
            </div>
          </div>

        </div>

        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="handleSave()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import { imgsCropperRequest } from '@/api/admin/tree.js'
import { picSpaceimgsCropperRequest } from '@/api/admin/pictureSpace.js'
export default {
  data () {
    return {
      dialogVisible: false,
      option: {
        img: 'http://localhost:8080/static/image/admin/official/channel/06.png',
        size: 1,
        outputType: 'jpeg',
        canScale: false, // 图片是否允许滚轮缩放
        autoCrop: true, // 是否默认生成截图框
        fixedBox: false, // 固定截图框大小 不允许改变
        fixed: true, // 是否开启截图框宽高固定比例
        fixedNumber: [1, 1], // 截图框的宽高比例
        full: true, // 是否输出原图比例的截图
        canMoveBox: true, // 截图框能否拖动
        original: false, // 上传图片按照原始比例渲染
        centerBox: true, // 截图框是否被限制在图片里面
        infoTrue: true, // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
        autoCropWidth: 80,
        autoCropHeight: 80,
        enlarge: 1
      },
      previews: {},
      cropperTopInput_one: 52,
      cropperTopInput_two: 52,
      cropperTopInput_three: '1',
      cropMovingX: '',
      cropMovingY: '',
      imgPath: '',
      imgCatId: '',
      imgID: '',
      cropperFlag: ''
    }
  },
  computed: {
    ...mapGetters(['cropperFlag']),
    cropperFlag_ () {
      return this.cropperFlag
    }
  },
  watch: {
    cropperFlag_: {
      handler (obj) {
        console.log(obj, 111)
        if (obj === null) return
        this.dialogVisible = true
        // this.option.img = url
        this.imgPath = obj.path
        this.imgCatId = obj.catid
        this.imgID = obj.imgid
        this.cropperFlag = obj.index
      },
      immediate: true
    }
  },
  methods: {

    // 预览相关
    realTime (data) {
      this.previews = data
    },
    // 图片裁剪输入框失焦综合处理
    handleBlur (index) {
      switch (index) {
        case 0:
          this.option.autoCropWidth = this.cropperTopInput_one
          break
        case 1:
          this.option.autoCropHeight = this.cropperTopInput_two
          break
      }
      this.cropperTopInput_three = this.cropperTopInput_one / this.cropperTopInput_two
      console.log(index, this.option.autoCropWidt, this.option.autoCropHeight)
    },
    // 裁剪图片保存
    handleSave () {
      console.log(this.imgPath, this.cropperTopInput_one, this.cropperTopInput_two, this.cropMovingX, this.cropMovingY, this.previews.img.width, this.previews.img.height, 98, this.imgCatId, this.imgID)
      console.log(this.previews)
      let obj = {
        remoteImgPath: this.imgPath,
        cropWidth: this.cropperTopInput_one,
        cropHeight: this.cropperTopInput_two,
        x: this.cropMovingX,
        y: this.cropMovingY,
        w: this.previews.w,
        h: this.previews.h,
        imgScaleW: 98,
        imgCatId: this.imgCatId,
        remoteImgId: this.imgID
      }
      if (this.cropperFlag === 0) {
        imgsCropperRequest(obj).then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.$store.commit('TOCHANGE_AUTOREFRESH', this.imgID)
          }
        })
      } else {
        picSpaceimgsCropperRequest(obj).then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.$store.commit('TOCHANGE_AUTOREFRESHPICSPACE', this.imgID)
          }
        })
      }

      this.dialogVisible = false
    },
    cropMoving (data) {
      console.log(data)
      this.cropMovingX = data.axis.x1 - 25.70224719101124
      this.cropMovingY = data.axis.y1
    }
  }
}
</script>
<style scoped lang="scss">
//裁剪样式
.vue-cropper {
  .CropperContainer {
    display: flex;
    flex-direction: column;
  }
  .CropperTop {
    display: flex;
    margin-bottom: 10px;
    .topDiv {
      display: flex;
      height: 28px;
      line-height: 28px;
      &:nth-of-type(2) {
        margin: 0 10px;
      }
      div {
        white-space: nowrap;
      }
      /deep/ .el-input__inner {
        width: 52px;
      }
    }
  }
  .tips {
    color: #c09853;
    margin-bottom: 10px;
  }
  .middleCOntainer {
    display: flex;
  }
  .CropperMiddle {
    height: 150px;
    width: 150px;
    /deep/ .vue-cropper {
      background-image: none;
    }
  }
  /deep/ .el-dialog__header {
    background: #f3f3f3;
    height: 42px;
    text-align: center;

    padding: 10px;
    span {
      font-size: 14px;
      line-height: 14px;
    }
    .el-dialog__headerbtn {
      top: 13px;
    }
  }
  .cropper {
    width: auto;
    height: 150px;
    width: 150px;
  }
  .CropperContainer {
    display: flex;
  }
}
</style>
