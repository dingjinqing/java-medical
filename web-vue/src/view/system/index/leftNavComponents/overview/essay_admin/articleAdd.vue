<template>
  <div class="experience-version">
    <div class="main">
      <el-form
        ref="form"
        :model="form"
        label-width="80px"
      >
        <el-form-item label="标题">
          <el-input
            size="small"
            style="width: 300px;"
            v-model="form.title"
          ></el-input>
        </el-form-item>
        <el-form-item label="文章分类">
          <el-select
            size="small"
            v-model="form.categoryId"
            placeholder="请文章分类"
          >
            <el-option
              v-for="item in categoryOptions"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.categoryId"
            >
            </el-option>
          </el-select>
          <el-link
            :underline="false"
            @click="toArticleTypeList"
          >管理文章分类</el-link>
        </el-form-item>
        <el-form-item label="是否推荐">
          <el-checkbox
            size="small"
            v-model="form.isRecommend"
          >是否推荐</el-checkbox>
        </el-form-item>
        <el-form-item label="是否发布">
          <el-checkbox
            size="small"
            v-model="form.status"
          >是否发布</el-checkbox>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input
            size="small"
            style="width: 300px;"
            v-model="form.keyword"
          ></el-input>
        </el-form-item>
        <el-form-item label="摘要">
          <el-input
            style="width: 40%;"
            :autosize="{ minRows: 5}"
            type="textarea"
            v-model="form.desc"
          ></el-input>
        </el-form-item>
        <el-form-item label="文章封面">
          <div style="display: flex;align-items: center;flex-wrap: wrap;">
            <div
              v-for="(item,index) in form.goodsImgs"
              :key="index"
              class="goodsImgWrap"
            >
              <el-image
                fit="cover"
                :src="item.imgUrl"
                style="width: 78px; height: 78px;"
              ></el-image>
              <span
                @click="deleteGoodsImg(index)"
                class="deleteIcon"
              >×</span>
            </div>
            <div
              class="goodsImgWrap"
              @click="addGoodsImg"
            >
              <el-image
                fit="scale-down"
                :src="imgHost+'/image/admin/add_img.png'"
                style="width: 78px; height: 78px;cursor: pointer;"
              />
            </div>
            <span class="inputTip">
              建议尺寸：290*220像素
            </span>
          </div>
        </el-form-item>

        <el-form-item label="内容">
          <TinymceEditor
            style="width: 80%;"
            v-model="form.content"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="add"
          >保存</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
      <ImageDalog
        :tuneUp="showImageDialog"
        pageIndex='pictureSpace'
        :isDraggable="true"
        :imageSize="[290,220]"
        @handleSelectImg='imgDialogSelectedCallback'
      />
    </div>
  </div>
</template>

<script>
import { getCategoryRequest, addArticleRequest, getArticleRequest, updateArticleRequest } from '@/api/system/essayAdmin.js'
import TinymceEditor from '@/components/admin/tinymceEditor/tinymceEditor'
import ImageDalog from '@/components/admin/imageDalog'
export default {
  name: 'articleAdd',
  components: {
    TinymceEditor,
    ImageDalog
  },
  props: {
    // 传入一个value，使组件支持v-model绑定
    isEdit: {
      type: Boolean,
      default: false
    },
    articleId: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      categoryOptions: [{
      }],
      form: {
        goodsImgs: [],
        isRecommend: false,
        status: false,
        categoryId: 1,
        content: null,
        desc: null,
        keyword: null,
        title: null
      },
      showImageDialog: false,
      imgHost: `${this.$imageHost}`
    }
  },
  mounted () {
    this.searchCategory()
  },
  methods: {
    addGoodsImg () {
      let imgLength = this.form.goodsImgs.length
      if (imgLength > 0) {
        this.$message.warning('最多上传一张照片')
      } else {
        this.showImageDialog = !this.showImageDialog
      }
    },
    imgDialogSelectedCallback (imgObjs) {
      if (imgObjs == null || imgObjs.length === 0) {
        return
      }
      if (imgObjs.length > 0) {
        imgObjs.length = 1
      }
      imgObjs.forEach(imgObj => {
        this.form.goodsImgs.push({ imgPath: imgObj.imgPath, imgUrl: imgObj.imgUrl })
      })
      let imgLength = this.form.goodsImgs.length
      if (imgLength > 1) {
        this.$message.warning('最多上传一张照片')
      }
    },
    deleteGoodsImg (index) {
      this.form.goodsImgs.splice(index, 1)
    },
    onSubmit () {
      console.log('submit!')
    },
    searchCategory () {
      getCategoryRequest(this.mainData).then((res) => {
        console.log('分类')
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.categoryOptions = content
          if (this.isEdit) {
            // 是编辑
            this.getArticleOption()
          }
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
    },
    add () {
      if (this.form.isRecommend === true) {
        this.form.isRecommend = 1
      } else {
        this.form.isRecommend = 0
      }
      if (this.form.status === true) {
        this.form.status = 1
      } else {
        this.form.status = 0
      }
      console.log(this.form)
      if (this.isEdit) {
        console.log('更新啦')
        this.updateArticleOption()
      } else {
        console.log('生成啦')
        this.addArticleOption()
      }
    },
    addArticleOption () {
      console.log(this.form)
      addArticleRequest(this.form).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
          this.toFirest()
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('失败')
      })
    },
    toArticleTypeList () {
      let params = {
        'flag': 2
      }
      this.$emit('show', params)
    },
    getArticleOption () {
      let params = {
        'articleId': this.articleId
      }
      getArticleRequest(params).then((res) => {
        console.log('编辑')
        console.log(res)
        if (res.error === 0) {
          this.form.articleId = res.content.articleId
          this.form.categoryId = res.content.categoryId
          this.form.categoryName = res.content.categoryName
          this.form.content = res.content.content
          this.form.desc = res.content.desc
          this.form.isRecommend = res.content.isRecommend === 1
          this.form.keyword = res.content.keyword
          this.form.status = res.content.status === 1
          this.form.title = res.content.title
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('失败')
      })
    },
    updateArticleOption () {
      console.log('更新')
      console.log(this.form)
      updateArticleRequest(this.form).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
          this.toFirest()
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('失败')
      })
    },
    toFirest () {
      let params = {
        'flag': 1
      }
      this.$emit('show', params)
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  background: #fff;
  padding-top: 15px;
  padding-bottom: 11px;
  padding-left: 10px;
}
.goodsImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
}
.goodsImgWrap .deleteIcon {
  width: 17px;
  height: 17px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 17px;
  text-align: center;
  position: absolute;
  top: -8px;
  right: -8px;
  cursor: pointer;
  opacity: 0.8;
}
.experience-version {
  background: #fff;
}
.select-menu {
  display: flex;
  padding: 10px;
  background: #fff;
}
.select-input {
  width: 200px;
}
.footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.footer > span {
  font-size: 14px;
}
.useSpan {
  font-weight: 700;
  color: #fff;
  padding: 4px;
  border-radius: 5px;
  background-color: #739e73;
}
</style>
