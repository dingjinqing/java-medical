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
            v-model="radio"
            label="1"
          >自动生成领取码</el-radio>
          <span>将随机生成xxx个唯一领取码，领取码由数字+字母组成</span>
        </div>
        <div v-if="radio === '1'">
          <div class="contentList">
            <span style="color:#333">领取码前缀:</span>
            <el-input
              size="small"
              v-model="input_one"
            ></el-input>
            <span>0-4个数字或字母</span>
          </div>
          <div class="contentList">
            <span style="color:#333">领取码位数:</span>
            <el-input
              size="small"
              v-model="input_two"
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
              v-model="input_three"
            ></el-input>
          </div>
        </div>
        <div class="top footer">
          <el-radio
            v-model="radio"
            label="2"
          >导入领取码</el-radio>
          <span>需导入已有领取码</span>
        </div>
        <div
          class="bottomHidden"
          v-if="radio === '2'"
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
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="dialogVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>
<script>
export default {
  data () {
    return {
      dialogVisible: false,
      radio: '2',
      input_one: '',
      input_two: '',
      input_three: '',
      fileList: [],
      fileName: ''
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
    // 上传前得钩子
    beforeUpload (file) {
      console.log(file)
      this.fileName = file.name
      return false
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
