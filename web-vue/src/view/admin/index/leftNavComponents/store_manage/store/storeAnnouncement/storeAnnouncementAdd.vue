<template>
  <div class="container">
    <div class="content">
      <el-form
        size="small"
        label-width="110px"
        ref="announcementForm"
        :model="form"
        :rules="formRules"
      >
        <el-form-item
          label="标题："
          prop="title"
        >
          <el-input
            class="form-input"
            placeholder="请填写标题"
            v-model="form.title"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="是否发布："
          prop="status"
        >
          <el-radio-group v-model="form.status">
            <el-radio :label="1">发布</el-radio>
            <el-radio :label="0">未发布</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="关键词："
          prop="keyword"
        >
          <el-input
            class="form-input"
            placeholder="请输入关键词"
            v-model="form.keyword"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="摘要："
          prop="desc"
        >
          <el-input
            type="textarea"
            rows="3"
            style="width:300px;"
            v-model="form.desc"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="内容："
          prop="content"
        >
          <tinymceEditor
            v-model="form.content"
            style="width:750px;"
          ></tinymceEditor>
        </el-form-item>
      </el-form>
    </div>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        @click="saveHandle"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import { announcementAddApi, announcementDetailApi, announcementUpdateApi } from '@/api/admin/storeManage/storeAnnouncement.js'
export default {
  components: {
    tinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  data () {
    return {
      form: {
        title: '',
        keyword: '',
        desc: '',
        content: '',
        status: 1
      },
      formRules: {
        title: [{ required: true, message: '请填写标题', trigger: 'blur' }]
      }
    }
  },
  mounted () {
    if (this.$route.query.articleId) {
      this.initData()
    }
  },
  methods: {
    initData () {
      let id = this.$route.query.articleId
      announcementDetailApi({
        articleId: id
      }).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.form = Object.assign({}, this.form, res.content)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    editorInputHandle (e) {
      console.log(e)
    },
    saveHandle () {
      let that = this
      this.$refs.announcementForm.validate(valid => {
        console.log(valid)
        let articleId = that.$route.query.articleId
        let params = Object.assign({}, this.form)
        if (!articleId) {
          announcementAddApi(params).then(res => {
            if (res.error === 0) {
              that.$message.success('保存成功')
              that.$router.push({
                path: '/admin/home/main/store/storeAnnouncement'
              })
            } else {
              this.$message.error(res.message)
            }
          })
        } else {
          params.articleId = articleId
          announcementUpdateApi(params).then(res => {
            if (res.error === 0) {
              that.$message.success('更新成功')
              that.$router.push({
                path: '/admin/home/main/store/storeAnnouncement'
              })
            } else {
              this.$message.error(res.message)
            }
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  margin-bottom: 50px;
  .content {
    background: #fff;
    padding: 18px 33px;
    .form-input {
      width: 170px;
    }
  }
  .footer {
    position: fixed;
    left: 150px;
    bottom: 0;
    width: calc(100% - 150px);
    line-height: 50px;
    border-top: 1px solid #f2f2f2;
    background: #f8f8fa;
    text-align: center;
  }
}
</style>
