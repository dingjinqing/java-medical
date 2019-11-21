<template>
  <div class="addBrandDialog">
    <div class="addBrandDialogMain">
      <el-dialog
        title="领取码"
        :visible.sync="dialogVisible"
        width="30%"
      >
        <div class="top">
          <el-radio
            v-model="action"
            label="1"
          >自动生成领取码</el-radio>
          <span>将随机生成xxx个唯一领取码，领取码由数字+字母组成</span>
        </div>
        <div v-if="action === '1'">
          <div class="contentList">
            <span style="color:#333">领取码前缀:</span>
            <el-input
              size="small"
              v-model="codePrefix"
            ></el-input>
            <span>0-4个数字或字母</span>
          </div>
          <div class="contentList">
            <span style="color:#333">领取码位数:</span>
            <el-input
              size="small"
              v-model="codeSize"
            ></el-input>
            <span>领取码组成未知数，限制6-12位</span>
          </div>
          <div
            class="contentList"
            style="margin-left:15px"
          >
            <span style="color:#333">领取数量:</span>
            <el-input
              size="small"
              v-model="number"
            ></el-input>
          </div>
        </div>
        <div class="top footer">
          <el-radio
            v-model="action"
            label="2"
          >导入领取码</el-radio>
          <span>需导入已有领取码</span>
        </div>
        <div
          class="bottomHidden"
          v-if="action === '2'"
        >
          <div>
            <span>第一步:</span>
            <span><a href="http://mpdev.weipubao.cn/doc/会员卡领取码模板.xls">下载导入模板</a></span>
          </div>
          <div>
            <span>第二步:</span>
            <span>导入领取码</span>
          </div>
          <div class="handleToUpload">
            <span>上传文件：</span>
            <div class="pathClass">
              {{fileName}}
            </div>
            <el-upload
              class="upload-demo"
              action=""
              :limit="1"
              :before-upload="beforeUpload"
              :show-file-list='false'
            >
              <el-button
                size="small"
                type="primary"
              >点击上传</el-button>

            </el-upload>
          </div>

        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="$emit('update:dialogVisible', false)">取 消</el-button>
          <el-button
            type="primary"
            @click="handleSure"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>
<script>
import { createReceiveBatchRequest, getReceiveBatchRequest } from '@/api/admin/memberManage/memberCard.js'
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
    }
  },
  data () {
    return {
      action: '1',
      codePrefix: '',
      codeSize: '',
      number: '',
      fileList: [],
      fileName: ''
    }
  },
  watch: {
    dialogVisible (data) {
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
  },
  methods: {
    defalutData () {
      this.$http.$on('CallCodeDialog', res => {
        this.dialogVisible = true
      })
    },
    clearData () {
      this.action = '1'
      this.codePrefix = ''
      this.codeSize = ''
      this.number = ''
    },
    getBatch (id) {
      getReceiveBatchRequest(id).then(res => {
        console.log(res)
        if (res.error === 0) {
          let data = res.content[0]
          this.action = String(data.action)
          this.codePrefix = data.codePrefix
          this.codeSize = data.codeSize
          this.number = data.number
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
      let data = {
        action: this.action,
        batchName: this.batchName,
        codePrefix: this.codePrefix,
        codeSize: this.codeSize,
        number: this.number
      }

      console.log(data)
      this.generateReceiveBatch(data)
    },

    generateReceiveBatch (data) {
      createReceiveBatchRequest(data).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$emit('generateReceiveCodeId', res.content.batchId)
        } else {
          this.$emit('generateReceiveCodeId', null)
        }
        this.$emit('update:dialogVisible', false)
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
    margin-top: 10px;
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
