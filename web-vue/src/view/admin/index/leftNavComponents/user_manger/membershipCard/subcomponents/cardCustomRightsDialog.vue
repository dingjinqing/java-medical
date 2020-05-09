<template>
  <div>
    <el-dialog
      :visible.sync="dialogVisiable"
      title="自定义权限"
      width="31%"
    >
      <el-form
        :inline-message="true"
        :model="$data"
        ref="ruleForm"
      >
        <el-form-item
          label="权益名称:"
          prop="right.crightName"
          :rules="myRule.crightName"
        >
          <el-input
            v-model="$data.right.crightName"
            size="small"
            style="width: 165px;"
          ></el-input>
          <span class="tips">最多可填20个字</span>
        </el-form-item>
        <el-form-item
          label="权益图标:"
          :rules="[{required: true}]"
        >
          <span
            class="add-image"
            @click="addImg"
          >
            <img
              :src="getImgSrc"
              :class="{'img-wh': whFlag}"
            >
          </span>
          <span class="tips">建议尺寸：30*30像素</span>
          <span class="error-tip" v-show="iconError">不能为空</span>
        </el-form-item>
        <el-form-item
          label="权益说明:"
          prop="right.crightContent"
          :rules="myRule.crightContent"
          class="right-content"
        >
          <div class="content">
            <el-input
              type="textarea"
              :rows="5"
              maxlength="200"
              show-word-limit
              placeholder="请输入内容"
              v-model="$data.right.crightContent"
              style="width: 300px;"
            >
            </el-input>
          </div>
        </el-form-item>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleRights"
          size="small"
        >确 定</el-button>
        <el-button
          @click="dialogVisiable = false"
          size="small"
        >取 消</el-button>
      </div>
    </el-dialog>
    <ImageDialog
      pageIndex='userCardAdd'
      :tuneUp="imgDisable"
      :imageSize="[30, 30]"
      @handleSelectImg="setImg"
    />
  </div>
</template>
<script>
export default {
  components: {
    ImageDialog: () => import('@/components/admin/imageDalog')
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    crightName: {
      type: String,
      default: null
    },
    crightImage: {
      type: String,
      default: null
    },
    crightContent: {
      type: String,
      default: null
    }
  },
  computed: {
    getImgSrc () {
      if (this.right.crightImage) {
        return this.right.crightImage
      }
      return this.$imageHost + '/image/admin/shop_beautify/add_decorete.png'
    },
    dialogVisiable: {
      get () {
        return this.visible
      },
      set (val) {
        this.$emit('update:visible', val)
      }
    }
  },
  watch: {
    visible (val) {
      if (val) {
        this.initRightData()
      }
    }
  },
  data () {
    return {
      right: {
        'crightName': null,
        'crightImage': null,
        'crightContent': null
      },
      // 图片弹窗
      imgDisable: false,
      imgPath: null,
      // 图片是否百分之百
      whFlag: false,
      // 是否是返回新对象
      createNewFlag: false,
      iconError: false,
      myRule: {
        crightName: [
          {
            required: true,
            message: '不能为空'
          },
          {
            pattern: /^.{1,20}$/,
            message: '最多可填20个字'
          }
        ],
        crightContent: [
          {
            required: true,
            message: '不能为空'
          }
        ]
      }
    }
  },
  methods: {
    initRightData () {
      this.createNewFlag = !this.crightName
      this.whFlag = Boolean(this.crightImage)
      this.right.crightName = this.crightName
      this.right.crightImage = this.crightImage
      this.right.crightContent = this.crightContent
    },
    addImg () {
      this.imgDisable = !this.imgDisable
    },
    setImg (res) {
      console.log(res.imgUrl)
      if (res && res.imgUrl) {
        this.right.crightImage = res.imgUrl
      }
      this.whFlag = Boolean(res && res.imgUrl)
    },
    handleRights () {
      this.iconError = !this.right.crightImage
      this.$refs['ruleForm'].validate((valid) => {
        if (valid && !this.iconError) {
          this.generateNewData()
        }
      })
    },
    generateNewData () {
      if (this.createNewFlag) {
        // create
        this.$emit('createNewRight', { ...this.right })
      } else {
        // update
        for (const key in this.right) {
          this.$emit(`update:${key}`, this.right[key])
        }
      }

      this.dialogVisiable = this.createNewFlag = false
    }
  }
}
</script>
<style scoped lang="scss">
.add-image {
  display: inline-block;
  background: #fff;
  border: 1px solid #e4e4e4;
  width: 70px;
  height: 70px;
  text-align: center;
  line-height: 70px;
  margin-top: 10px;
  img {
    // width: 100%;
    // height: 100%;
    vertical-align: middle;
  }
  cursor: pointer;
}
.img-wh {
  width: 100%;
  height: 100%;
}
.add-image:hover {
  background: #f5f3f3;
}
.tips {
  margin-left: 10px;
  color: #999;
}
.right-content {
  .content {
    margin-top: 10px;
  }
}
.error-tip{
  color: #F56C6C;
  font-size: 12px;
  margin-left: 10px;
}
</style>
