<template>
  <div>
    <!--图片裁剪-->
    <div class="vue-cropper">
      <el-dialog
        title="裁剪图片"
        :visible.sync="dialogVisible"
        width="35%"
        :append-to-body='true'
        :modal-append-to-body='false'
      >
        <div class="CropperContainer">
          <div class="CropperTop">
            <div class="topDiv">
              <div>裁剪图片宽度：</div>
              <el-input
                v-model="cropperTopInput_one"
                size="mini"
                @blur="handleBlur(0)"
                :disabled="disabled"
              ></el-input>
            </div>
            <div class="topDiv">
              <div>裁剪图片高度：</div>
              <el-input
                v-model="cropperTopInput_two"
                size="mini"
                @blur="handleBlur(1)"
                :disabled="disabled"
              ></el-input>
            </div>
            <div class="topDiv">
              <div>裁剪图片比例：</div>
              <el-input
                v-model="cropperTopInput_three"
                size="mini"
                @blur="handleBlur(2)"
                :disabled="disabled"
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
                :canScale='option.canScale'
                :fixed='true'
                @realTime="realTime"
                @cropMoving='cropMoving'
                @imgMoving='imgMoving'
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
import vueCropper from '@/components/admin/vue-cropper/vue-cropper'
import { mapGetters } from 'vuex'
import { imgsCropperRequest } from '@/api/admin/tree.js'
import { picSpaceimgsCropperRequest } from '@/api/admin/pictureSpace.js'
export default {
  props: {
    imageSize: { // 有上层传来的图片限制尺寸
      type: Array,
      default: () => [52, 52]
    },
    allowToInput: { // 图片空间裁剪用户可以自定义输入
      type: Boolean,
      default: () => false
    },
    cropperFlagF: {
      type: Number,
      default: () => 0
    }
  },
  components: {
    vueCropper
  },
  data () {
    return {
      dialogVisible: false,
      option: {
        img: this.$imageHost + '/image/admin/official/channel/06.png',
        size: 1,
        outputType: 'jpeg',
        canScale: false, // 图片是否允许滚轮缩放
        autoCrop: true, // 是否默认生成截图框
        fixedBox: false, // 固定截图框大小 不允许改变
        fixed: true, // 是否开启截图框宽高固定比例
        fixedNumber: [1, 1], // 截图框的宽高比例
        full: false, // 是否输出原图比例的截图
        canMoveBox: true, // 截图框能否拖动
        original: false, // 上传图片按照原始比例渲染
        centerBox: true, // 截图框是否被限制在图片里面
        infoTrue: true, // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
        autoCropWidth: 80,
        autoCropHeight: 80,
        enlarge: 1,
        canMove: false
      },
      previews: {},
      cropperTopInput_one: 52,
      cropperTopInput_two: 52,
      cropperTopInput_three: 1,
      cropMovingX: '',
      cropMovingY: '',
      imgPath: '',
      imgCatId: '',
      imgID: '',
      cropperFlagP: '',
      disabled: true, // 用户是否可以自定义输入
      saveScaleW: null // 保存缩放后的背景图片宽度
    }
  },
  computed: {
    ...mapGetters(['cropperFlag']),
    cropperFlag_ () {
      return this.cropperFlag
    }
  },
  watch: {
    cropperFlag_ (obj) {
      console.log(obj, 111)
      if (obj === null) return
      this.dialogVisible = true
      // this.option.img = url
      this.imgPath = obj.path
      this.imgCatId = obj.catid
      this.imgID = obj.imgid
      this.cropperFlagP = obj.index
      this.option.img = obj.url
      if (obj.imgWidth > 150) {
        this.saveScaleW = 150
      } else {
        this.saveScaleW = obj.imgWidth
      }
    },
    imageSize: {
      handler (newData) {
        console.log(newData)
        if (newData) {
          this.cropperTopInput_one = newData[0]
          this.cropperTopInput_two = newData[1]
        }
      },
      immediate: true
    },
    cropperTopInput_one (newData) {
      if (newData === '') {
        this.option.fixedNumber[0] = 1
      } else {
        this.option.fixedNumber[0] = newData
      }
    },
    cropperTopInput_two (newData) {
      if (newData === '') {
        this.option.fixedNumber[1] = 1
      } else {
        this.option.fixedNumber[1] = newData
      }
    }
  },
  mounted () {
    if (this.allowToInput) {
      this.disabled = false
      this.cropperTopInput_one = ''
      this.cropperTopInput_two = ''
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
        case 2:

          if (this.cropperTopInput_one && !this.cropperTopInput_two) {
            this.cropperTopInput_two = this.cropperTopInput_one / this.cropperTopInput_three
          } else if (!this.cropperTopInput_one && this.cropperTopInput_two) {
            this.cropperTopInput_one = this.cropperTopInput_two * this.cropperTopInput_three
          } else if (!this.cropperTopInput_one && !this.cropperTopInput_two) {
            this.cropperTopInput_one = 80 * this.cropperTopInput_three
            this.cropperTopInput_two = 80
          } else {
            this.cropperTopInput_two = this.cropperTopInput_one / this.cropperTopInput_three
          }

          let W = this.cropperTopInput_one
          let H = this.cropperTopInput_two
          this.option.autoCropWidth = W
          this.option.autoCropHeight = H
          console.log(W, H)
          this.option.fixedNumber[0] = W
          this.option.fixedNumber[1] = H
      }
      if (this.cropperTopInput_one && this.cropperTopInput_two) {
        this.cropperTopInput_three = this.cropperTopInput_one / this.cropperTopInput_two
      }
      console.log(index, this.option.autoCropWidt, this.option.autoCropHeight)
    },
    // 裁剪图片保存
    handleSave () {
      // console.log(this.imgPath, this.cropperTopInput_one, this.cropperTopInput_two, this.cropMovingX, this.cropMovingY, this.previews.img.width, this.previews.img.height, 98, this.imgCatId, this.imgID)
      // console.log(this.previews)
      console.log(this.cropMovingX, this.cropMovingY, this.cropperTopInput_one, this.cropperTopInput_two, this.previews.w, this.previews.h)
      let obj = {
        remoteImgPath: this.imgPath,
        cropWidth: this.cropperTopInput_one,
        cropHeight: this.cropperTopInput_two,
        x: this.cropMovingX,
        y: this.cropMovingY,
        w: this.previews.w,
        h: this.previews.h,
        imgScaleW: this.saveScaleW,
        imgCatId: this.imgCatId,
        remoteImgId: this.imgID
      }
      console.log(this.cropperFlagF)
      if (this.cropperFlagF !== 1) {
        imgsCropperRequest(obj).then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.$store.commit('TOCHANGE_AUTOREFRESH', this.imgID)
            this.$emit('handleToResetPage')
          }
        })
      } else {
        picSpaceimgsCropperRequest(obj).then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.$emit('handleToResetPage')
            this.$store.commit('TOCHANGE_AUTOREFRESHPICSPACE', this.imgID)
          }
        })
      }

      this.dialogVisible = false
    },
    // 图片移动
    imgMoving (data) {
      console.log(data)
    },
    // 裁剪框移动
    cropMoving (data) {
      console.log(this.$refs.cropper.goAutoCrop)
      console.log(data)
      let left = data.axis.x1 - this.$refs.cropper.getImgAxis().x1
      let top = data.axis.y1 - this.$refs.cropper.getImgAxis().y1
      // 处理裁剪比例等数据
      console.log(Math.floor(left), '-', Math.floor(top))
      this.cropMovingX = Math.floor(left)
      this.cropMovingY = Math.floor(top)
    }
  }
}
</script>
<style lang="scss">
//裁剪样式
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
</style>
