<template>
  <div class="addBrandDialog">
    <div class="addBrandDialogMain">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
      >
        <el-dialog
          :title="$t('memberCard.generateCode')"
          :visible.sync="dialogVisible2"
          width="530px"
          v-if="receiveAction===1"
        >
          <div class="top">
            <el-radio
              v-model="action"
              label="1"
            >{{$t('memberCard.autoGenerateCode')}}</el-radio>
            <span style="margin-left: 20px;">{{$t('memberCard.autoGenerateMsg')}}</span>
          </div>
          <div
            v-if="action === '1'"
            style="margin-left: -100px;"
          >
            <el-form-item
              class="contentList"
              prop="codePrefix"
            >
              <div class="contentList">
                <span style="color:#333">{{$t('memberCard.codePre')}}:</span>
                <el-input
                  size="small"
                  v-model="ruleForm.codePrefix"
                  maxlength="4"
                ></el-input>
                <span>{{$t('memberCard.codeLimitMsg')}}</span>
              </div>
            </el-form-item>
            <el-form-item
              class="contentList"
              prop="codeSize"
            >
              <div class="contentList">
                <span style="color:#333">{{$t('memberCard.codeLimit')}}:</span>
                <el-input
                  size="small"
                  v-model="ruleForm.codeSize"
                ></el-input>
                <span>{{$t('memberCard.codeNoKnowLimit')}}</span>
              </div>
            </el-form-item>
            <el-form-item
              class="contentList"
              prop="number"
            >
              <div
                class="contentList"
                style="margin-left:15px"
              >
                <span style="color:#333">{{$t('memberCard.codeNum')}}:</span>
                <el-input
                  size="small"
                  v-model="ruleForm.number"
                ></el-input>
              </div>
            </el-form-item>
          </div>
          <div class="top footer">
            <el-radio
              v-model="action"
              label="2"
            >{{$t('memberCard.importPickCode')}}</el-radio>
            <span>{{$t('memberCard.needImportCode')}}</span>
          </div>
          <div
            class="bottomHidden"
            v-if="action === '2'"
          >
            <div>
              <span>{{$t('memberCard.first')}}:</span>
              <span><a @click="getTemplate">{{$t('memberCard.downLoadTemp')}}</a></span>
            </div>
            <div>
              <span>{{$t('memberCard.second')}}:</span>
              <span>{{$t('memberCard.importPickCode')}}</span>
            </div>
            <div class="handleToUpload">
              <span>{{$t('memberCard.uploadFiles')}}</span>
            <el-input
              v-model="ruleForm.filename"
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
              :file-list="fileList"
              :on-exceed="exceedHandle"
            >
              <el-button
                slot="trigger"
                size="small"
              >{{$t('memberIntroductionDialog.browse')}}...</el-button>
            </el-upload>
            </div>

          </div>
          <span
            slot="footer"
            class="dialog-footer"
          >
            <el-button @click="cancelDialog">{{$t('memberCard.cancel')}}</el-button>
            <el-button
              type="primary"
              @click="handleSure"
            >{{$t('memberCard.sure')}}</el-button>
          </span>
        </el-dialog>

        <!-- 卡号+密码 -->
        <el-dialog
          :title="$t('memberCard.cardNumPwd')"
          :visible.sync="dialogVisible2"
          width="30%"
          v-if="receiveAction===2"
        >

          <div class="top">
            <el-radio
              v-model="action"
              label="1"
            >{{$t('memberCard.autoCardNumPwd')}}</el-radio>
            <span style="margin-left: 20px;"> {{$t('memberCard.autoCardNumPwdMsg')}}</span>
          </div>
          <div
            v-if="action === '1'"
            style="margin-left: -100px;"
          >
            <el-form-item class="contentList">
              <span style="color:#333">{{$t('memberCard.cardPrd')}}:</span>
              <el-input
                size="small"
                v-model="ruleForm.codePrefix"
                maxlength="4"
              ></el-input>
              <span>{{$t('memberCard.cardPrdMsg')}}</span>
            </el-form-item>
            <el-form-item
              class="contentList"
              prop="codeSize"
            >
              <div class="contentList">
                <span style="color:#333">{{$t('memberCard.cardNoLimit')}}:</span>
                <el-input
                  size="small"
                  v-model="ruleForm.codeSize"
                ></el-input>
                <span>{{$t('memberCard.cardNoLimitMsg')}}</span>
              </div>
            </el-form-item>
            <el-form-item
              class="contentList"
              prop="cardPwdSize"
            >
              <div class="contentList">
                <span style="color:#333">{{$t('memberCard.pwdNoLimit')}}:</span>
                <el-input
                  size="small"
                  v-model="ruleForm.cardPwdSize"
                ></el-input>
                <span>{{$t('memberCard.pwdNoSum')}}</span>
              </div>
            </el-form-item>
            <el-form-item
              class="contentList"
              prop="number"
            >
              <div class="contentList">
                <span style="color:#333">{{$t('memberCard.receiveAmount')}}:</span>
                <el-input
                  size="small"
                  v-model="ruleForm.number"
                ></el-input>
              </div>
            </el-form-item>
          </div>
          <div class="top footer">
            <el-radio
              v-model="action"
              label="2"
            >{{$t('memberCard.importCodeNoPwd')}}</el-radio>
            <span>{{$t('memberCard.needImportCodeNoPwd')}}</span>
          </div>
          <div
            class="bottomHidden"
            v-if="action === '2'"
          >
            <div>
              <span>{{$t('memberCard.first')}}:</span>
              <span><a @click="getPwdTemplate">{{$t('memberCard.downLoadTemp')}}</a></span>
            </div>
            <div>
              <span>{{$t('memberCard.second')}}:</span>
              <span>{{$t('memberCard.importCodeNoPwd')}}</span>
            </div>
            <div class="handleToUpload">
              <span>{{$t('memberCard.uploadFiles')}}</span>
            <el-input
              v-model="ruleForm.filename"
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
              :file-list="fileList"
              :on-exceed="exceedHandle"
            >
              <el-button
                slot="trigger"
                size="small"
              >{{$t('memberIntroductionDialog.browse')}}...</el-button>
            </el-upload>
            </div>

          </div>
          <span
            slot="footer"
            class="dialog-footer"
          >
            <el-button @click="cancelDialog">{{$t('memberCard.cancel')}}</el-button>
            <el-button
              type="primary"
              @click="handleSure"
            >{{$t('memberCard.sure')}}</el-button>
          </span>
        </el-dialog>
      </el-form>
    </div>

  </div>
</template>
<script>
import {createReceiveBatchRequest, getReceiveBatchRequest, getExportExcel, importInsertExcel, getPwdExportExcel} from '@/api/admin/memberManage/memberCard.js'
import { download } from '@/util/excelUtil.js'
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: () => false
    },
    batchName: {
      type: String,
      default: () => ''
    },
    batchId: {
      type: Number,
      default: () => null
    },
    receiveAction: {
      type: Number,
      default: () => 1
    },
    batchIdStr: {
    }
  },
  data () {
    let validateCodeSize = (rule, value, callback) => {
      if (this.ruleForm.action === '1') {
        if (value > 5 && value < 13) {
          callback()
        } else {
          callback(new Error(this.$t('memberCard.errorMsg1')))
        }
      } else {
        callback()
      }
    }

    let validateCardPwdSize = (rule, value, callback) => {
      if (this.ruleForm.action === '1') {
        if (this.receiveAction === 2) {
          if (Number(value) > 0) {
            callback()
          } else if (value === null || value === undefined) {
            callback(new Error(this.$t('memberCard.errorMsg1')))
          } else {
            callback(new Error(this.$t('memberCard.errorMsg1')))
          }
        }
      }
    }

    let validateCardNumber = (rule, value, callback) => {
      if (this.ruleForm.action === '1') {
        if (Number(value) > 0) {
          callback()
        } else {
          callback(new Error(this.$t('memberCard.errorMsg2')))
        }
      } else {
        callback()
      }
    }

    return {
      dialogVisible2: false,
      action: '1',
      fileList: [],
      fileName: '',
      ruleForm: {
        action: '1',
        codePrefix: '',
        codeSize: '',
        cardPwdSize: '',
        number: '',
        fileList: [],
        fileName: ''
      },
      rules: {
        codeSize: [
          { validator: validateCodeSize, trigger: 'blur' }
        ],
        cardPwdSize: [
          { validator: validateCardPwdSize, trigger: 'blur' }
        ],
        number: [
          { validator: validateCardNumber, trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    dialogVisible (data) {
      this.dialogVisible2 = true
      this.clearData()
      console.log(data)
      console.log(this.batchName)
      console.log(this.batchId)
      if (data) {
        if (this.batchId) {
          this.getBatch(this.batchId)
        }
      }
    }
  },
  mounted () {
    // 初始化数据
    this.defalutData()
    this.langDefault()
  },
  methods: {
    cancelDialog () {
      this.dialogVisible2 = false
      this.clearData()
    },
    defalutData () {
      this.$http.$on('CallCodeDialog', res => {
        this.dialogVisible = true
      })
    },
    clearData () {
      this.action = '1'
      this.ruleForm.codePrefix = ''
      this.ruleForm.codeSize = ''
      this.ruleForm.number = ''
      this.ruleForm.cardPwdSize = ''
      this.ruleForm.filename = null
    },
    getBatch (id) {
      getReceiveBatchRequest(id).then(res => {
        console.log(res)
        if (res.error === 0) {
          let data = res.content[0]
          this.action = String(data.action)
          this.ruleForm.codePrefix = data.codePrefix
          this.ruleForm.codeSize = data.codeSize
          this.ruleForm.cardPwdSize = data.cardPwdSize
          this.ruleForm.number = data.number
        }
      })
    },
    // 上传前得钩子
    beforeUpload (file) {
      console.log(file)
      this.fileName = file.name
      return false
    },
    handleSure () {
      this.$refs.ruleForm.validate((valid) => {
        // check input
        if (valid) {
          let data = {
            receiveAction: this.receiveAction,
            action: this.action,
            batchName: this.batchName,
            codePrefix: this.ruleForm.codePrefix,
            codeSize: this.ruleForm.codeSize,
            cardPwdSize: this.ruleForm.cardPwdSize,
            number: this.ruleForm.number
          }
          console.log(data)
          if (this.action === '1') {
            this.generateReceiveBatch(data)
          }
          if (this.action === '2') {
            console.log('上传文件')
            this.uploadFile()
          }
          if (this.action === null) {
            console.log(this.action)
            console.log('提交失败1')
            this.$message.warning(this.$t('memberCard.errorMsg3'))
            return false
          }
        } else {
          console.log('提交失败2')
          return false
        }
      })
    },
    generateReceiveBatch (data) {
      createReceiveBatchRequest(data).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$emit('generateReceiveCodeId', res.content.batchId)
        } else {
          this.$emit('generateReceiveCodeId', null)
        }
        this.dialogVisible2 = false
        this.clearData()
      })
    },
    getTemplate () {
      getExportExcel().then(res => {
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch((err, data) => {
        console.error('err:', err)
        this.loading = false
      })
    },
    getPwdTemplate () {
      getPwdExportExcel().then(res => {
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch((err, data) => {
        console.error('err:', err)
        this.loading = false
      })
    },
    uploadChangeHandle (file, fileList) {
      this.fileList = [fileList[fileList.length - 1]]
      this.$set(this.ruleForm, 'filename', file.name)
      this.$set(this.ruleForm, 'file', file.raw)
    },
    beforeUploadHandle (file) {
      let isXls = /\.(xls|xlsx|csv)$/.test(file.name)
      if (!isXls) {
        this.$message.warning(this.$t('memberCard.errorMsg4'))
        this.fileList = []
        this.$set(this.ruleForm, 'filename', '')
        this.$set(this.ruleForm, 'file', '')
        return false
      }
      return true
    },
    exceedHandle (file, fileList) {
      this.$refs.importUpload.clearFiles()
      this.fileList = [file[0]]
      this.$set(this.ruleForm, 'filename', file[0].name)
      this.$set(this.ruleForm, 'file', file[0])
    },
    uploadFile () {
      if (!this.ruleForm.filename) {
        this.$message.warning(this.$t('memberIntroductionDialog.psFile'))
        return false
      }
      console.log('this.batchIdStr1:' + this.batchIdStr.batchIdStr1)
      console.log('this.batchIdStr2:' + this.batchIdStr.batchIdStr2)
      let formdata = new FormData()
      formdata.append('batchName', this.batchName)
      formdata.append('receiveAction', this.receiveAction)
      formdata.append('action', this.action)
      formdata.append('file', this.ruleForm.file)
      if (this.receiveAction === 1) {
        formdata.append('batchIdStr', this.batchIdStr.batchIdStr1)
      }
      if (this.receiveAction === 2) {
        formdata.append('batchIdStr', this.batchIdStr.batchIdStr2)
      }
      importInsertExcel(formdata).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('memberCard.errorMsg5'))
          this.$emit('generateReceiveCodeId', res.content)
          this.fileList = []
          this.dialogVisible2 = false
        } else {
          this.$message.error(res.message)
          this.$emit('generateReceiveCodeId', null)
        }
        this.fileList = []
      }).catch(err => {
        throw err
      })
    }

  }
}
</script>
<style lang="scss" scoped>
.addBrandDialog {
  /deep/ .el-dialog__header {
    text-align: center;
    background: #f3f3f3;
  }
  .top {
    /deep/ .el-radio__label {
      color: #333;
    }
    span {
      display: inline-block;
      color: #999;
      margin-left: -20px;
    }
  }
  /deep/ .el-form-item {
    margin-bottom: 0px;
  }

  .contentList {
    margin-top: 10px;
    display: flex;
    align-items: center;

    /deep/ .el-input {
      width: 143px;
      margin: 0 10px;
    }
    span {
      white-space: nowrap;
      color: #999;
    }
  }
  .footer {
    margin-top: 20px;
  }
  .bottomHidden {
    padding-left: 10px;
    div {
      margin: 15px;
    }
    & div:nth-of-type(1) {
      span:nth-of-type(2) {
        cursor: pointer;
        a {
          color: #5a8bff;
        }
      }
    }
    .handleToUpload {
      margin-top: -18px;
      display: flex;
      align-items: center;
      /deep/ .upload-demo {
        margin: 0;
      }
      .pathClass {
        width: 168px;
        line-height: 28px;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        border: 1px solid #ddd;
        height: 32px;
        margin-right: 0;
      }
    }
  }
}
</style>
