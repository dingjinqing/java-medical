<template>
  <!-- 会员导入 -->
  <div class="member-introduction-page">
    <el-dialog
      :title="$t('memberIntroductionDialog.memberImport')"
      :visible.sync="dialogVisible"
      width="700"
      custom-class="mi-dialog"
      v-loading="loading"
    >
      <div class="mi-content">
        <h2 class="mi-title">{{$t('memberIntroductionDialog.templateDownload')}}</h2>
        <div class="mi-model">
          {{$t('memberIntroductionDialog.downloadTemplate')}}<el-button
            type="text"
            @click="downloadTemplate"
          >{{$t('memberIntroductionDialog.template')}}</el-button>
          <div>
            <el-checkbox
              v-model="importInfo.cardCheck"
              :checked="!!importInfo.cardCheck"
            >{{$t('memberIntroductionDialog.bulkCard')}}</el-checkbox>
            <div
              class="mi-model-con"
              v-if="importInfo.cardCheck"
            >
              <ul>
                <li>
                  {{$t('memberIntroductionDialog.ordinaryCard')}}
                  <el-select
                    size="small"
                    v-model="importInfo.regularCard"
                  >
                    <el-option
                      :label="$t('memberIntroductionDialog.psCard')"
                      value=""
                    ></el-option>
                    <el-option
                      v-for="(card,index) in setUpoptionsOne"
                      :key="index"
                      :label="card.cardName"
                      :value="card.id"
                    ></el-option>
                  </el-select>
                </li>
                <li>
                  {{$t('memberIntroductionDialog.limitedCard')}}
                  <el-select
                    size="small"
                    v-model="importInfo.limitCard"
                  >
                    <el-option
                      :label="$t('memberIntroductionDialog.psCard')"
                      value=""
                    ></el-option>
                    <el-option
                      v-for="(card,index) in setUpoptionsTwo"
                      :key="index"
                      :label="card.cardName"
                      :value="card.id"
                    ></el-option>
                  </el-select>
                </li>
                <li>
                  {{$t('memberIntroductionDialog.levelCard')}}
                  <el-select
                    size="small"
                    v-model="importInfo.levelCard"
                  >
                    <el-option
                      :label="$t('memberIntroductionDialog.psCard')"
                      value=""
                    ></el-option>
                    <el-option
                      v-for="(card,index) in setUpoptionsThree"
                      :key="index"
                      :label="card.cardName"
                      :value="card.id"
                    ></el-option>
                  </el-select>
                </li>
              </ul>
              <p class="tips">{{$t('memberIntroductionDialog.afterImport')}}</p>
            </div>
          </div>
          <div style="margin-top: 15px;">
            <el-checkbox
              v-model="importInfo.tagCheck"
              :checked="!!importInfo.tagCheck"
            >{{$t('memberIntroductionDialog.batchTag')}}</el-checkbox>
            <div
              class="mi-model-con"
              v-if="importInfo.tagCheck"
            >
              <span>{{$t('memberIntroductionDialog.chooseTag')}}</span>
              <el-select
                size="small"
                v-model="importInfo.tagId"
              >
                <el-option
                  :label="$t('memberIntroductionDialog.psTag')"
                  value=""
                ></el-option>
                <el-option
                  v-for="(tag, index) in userTags"
                  :key="index"
                  :label="tag.value"
                  :value="tag.id"
                ></el-option>
              </el-select>
              <p class="tips">{{$t('memberIntroductionDialog.afterActivating')}}</p>
            </div>
          </div>
          <div style="margin-top: 15px;">
            <el-checkbox
              v-model="importInfo.groupCheck"
              :checked="!!importInfo.tagCheck"
            >{{$t('memberIntroductionDialog.setDistributor')}}</el-checkbox>
            <div
              class="mi-model-con"
              v-if="importInfo.groupCheck"
            >
              {{$t('memberIntroductionDialog.selectGroup')}}
              <el-select
                size="small"
                v-model="importInfo.groupId"
              >
                <el-option
                  :label="$t('memberIntroductionDialog.unclassified')"
                  value=""
                ></el-option>
                <el-option
                  v-for="group in userGroup"
                  :key="group.id"
                  :label="group.groupName"
                  :value="group.id"
                ></el-option>
              </el-select>
              <p class="tips">{{$t('memberIntroductionDialog.afterGroup')}}</p>
            </div>
          </div>
        </div>
        <h2 class="mi-title">{{$t('memberIntroductionDialog.dataImport')}}</h2>
        <div class="mi-model">
          <div class="mi-flex">
            <span class="flex-title">{{$t('memberIntroductionDialog.uploadFiles')}}</span>
            <el-input
              v-model="importInfo.filename"
              size="small"
              style="width:170px;"
              readonly
            ></el-input>
            <el-upload
              ref="importUpload"
              action=""
              class="mi-upload"
              accept=".xls,.xlsx"
              :on-change="uploadChangeHandle"
              :before-upload="beforeUploadHandle"
              :auto-upload="false"
              :show-file-list="false"
              :limit="2"
              :data="uploadData"
              :file-list="fileList"
              :http-request="uploadFile"
              :on-exceed="exceedHandle"
            >
              <el-button
                slot="trigger"
                size="small"
              >{{$t('memberIntroductionDialog.browse')}}...</el-button>
            </el-upload>
          </div>
          <div class="mi-flex">
            <span class="flex-title">{{$t('memberIntroductionDialog.importRule')}}</span>
            <ol>
              <li>{{$t('memberIntroductionDialog.one')}}</li>
              <li>{{$t('memberIntroductionDialog.two')}}</li>
            </ol>
          </div>
        </div>
      </div>
      <div
        slot="footer"
        class="mi-footer"
      >
        <el-button
          size="small"
          type="primary"
          @click="submitImport"
        >{{$t('memberIntroductionDialog.import')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { getTemplate, getDistributionGroup, importInsert } from '@/api/admin/memberManage/membershipIntroduction.js'
import { getAllMemberCardByClassRequest } from '@/api/admin/memberManage/memberCard.js'
import { allTagRequest } from '@/api/admin/membershipList.js'
export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      importInfo: {
        filename: ''
      },
      loading: false,
      setUpoptionsOne: [], // 普通会员卡数据
      setUpoptionsTwo: [], // 限次会员卡数据
      setUpoptionsThree: [], // 等级会员卡数据
      userTags: [], // 标签数据
      userGroup: [], // 分销员分组数据
      uploadData: {}, // 上传文件额外传参
      fileList: [] // 文件列表
    }
  },
  computed: {
    dialogVisible: {
      get () {
        return this.visible
      },
      set (val) {
        this.$emit('update:visible', val)
      }
    }
  },
  watch: {
    dialogVisible: function (val) {
      if (val) {
        this.importInfo = Object.assign({}, {
          filename: '',
          cardCheck: false, // 再赋值解决checkbox不更新的问题
          tagCheck: false,
          groupCheck: false
        })
      }
    }
  },
  mounted () {
    this.initSelects()
  },
  methods: {
    initSelects () {
      this.getCardData()
      this.getTagData()
      this.getGroupData()
    },
    // 获取会员卡下拉数据
    getCardData () {
      getAllMemberCardByClassRequest().then(res => {
        if (res.error === 0) {
          // 普通会员卡下拉数据
          this.setUpoptionsOne = res.content.normalCard
          // 限次会员卡下拉数据
          this.setUpoptionsTwo = res.content.limitNumCard
          // 等级会员卡下拉数据
          this.setUpoptionsThree = res.content.rankCard
        }
      })
    },
    // 获取标签下拉数据
    getTagData () {
      allTagRequest().then(res => {
        if (res.error === 0) {
          this.userTags = res.content
        }
      })
    },
    // 获取分销员分组下拉
    getGroupData () {
      getDistributionGroup().then(res => {
        if (res.error === 0) {
          this.userGroup = res.content
        }
      })
    },
    // 下载会员导入模板
    downloadTemplate () {
      this.loading = true
      getTemplate().then(res => {
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch((err, data) => {
        console.error('err:', err)
        this.loading = false
      })
    },
    beforeUploadHandle (file) {
      let isXls = file.type === 'application/vnd.ms-excel application/x-excel'
      let isXlsx = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      if (!isXls && !isXlsx) {
        this.$message.warning('上传文件只支持xls、xlsx格式！')
        return false
      }
      return true
    },
    // 超出限制
    exceedHandle (file, fileList) {
      this.$refs.importUpload.clearFiles()
      this.fileList = [file[0]]
      this.$set(this.importInfo, 'filename', file[0].name)
      this.$set(this.importInfo, 'file', file[0])
    },
    // 添加文件、上传成功和上传失败时都会被调用
    uploadChangeHandle (file, fileList) {
      this.fileList = [fileList[fileList.length - 1]]
      this.$set(this.importInfo, 'filename', file.name)
      this.$set(this.importInfo, 'file', file.raw)
    },
    // 点击导入按钮
    submitImport () {
      let that = this
      let cardId = []
      let importInfo = this.importInfo
      if (importInfo.regularCard) {
        cardId.push(importInfo.regularCard)
      } else if (importInfo.limitCard) {
        cardId.push(importInfo.limitCard)
      } else if (importInfo.levelCard) {
        cardId.push(importInfo.levelCard)
      }
      cardId = cardId.join(',')
      if (!this.importInfo.filename) {
        this.$message.warning(this.$t('memberIntroductionDialog.psFile'))
        return false
      }
      this.uploadData = {
        cardId: cardId,
        tagId: this.importInfo.tagId || '',
        groupId: this.importInfo.groupId || ''
      }
      this.$nextTick(() => {
        that.$refs.importUpload.submit()
        // that.uploadFile()
      })
    },
    uploadFile () {
      let that = this
      that.loading = true
      let formdata = new FormData()
      formdata.append('file', this.importInfo.file)
      formdata.append('cardId', this.uploadData.cardId)
      formdata.append('tagId', this.uploadData.tagId)
      formdata.append('groupId', this.uploadData.groupId)
      importInsert(formdata).then(res => {
        that.loading = false
        if (res.error === 0) {
          that.$message.success(that.$t('memberIntroductionDialog.uploadSuccess'))
          that.dialogVisible = false
        } else {
          that.$message.error(that.$t('memberIntroductionDialog.uploadFail'))
        }
        that.fileList = []
      }).catch(err => {
        that.loading = false
        throw err
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.member-introduction-page {
  color: #333;
  .mi-content {
    max-height: 400px;
    overflow-y: auto;
  }
  .mi-title {
    margin-bottom: 14px;
  }
  .mi-model {
    background-color: #f5f5f5;
    border-radius: 6px;
    margin-bottom: 14px;
    padding: 10px 16px 16px;
  }
  .mi-model-con {
    padding-left: 23px;
    line-height: 40px;
  }
  .mi-footer {
    text-align: center;
  }
  .mi-upload {
    display: inline-block;
  }
  .mi-flex {
    display: flex;
    .flex-title {
      display: block;
      width: 80px;
      line-height: 32px;
    }
    ol {
      flex: 1;
      padding-top: 5px;
      line-height: 20px;
    }
  }
  .tips {
    line-height: 24px;
    color: #999;
  }
}
</style>
