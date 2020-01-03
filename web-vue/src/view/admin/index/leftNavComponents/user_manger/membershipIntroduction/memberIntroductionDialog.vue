<template>
  <!-- 会员导入 -->
  <div class="member-introduction-page">
    <el-dialog
      title="会员导入"
      :visible.sync="dialogVisible"
      width="700"
      custom-class="mi-dialog"
    >
      <div class="mi-content">
        <h2 class="mi-title">第一步：模板下载</h2>
        <div class="mi-model">
          下载模板：<el-button type="text">会员导入文件模板</el-button>
          <div>
            <el-checkbox>批量发放会员卡</el-checkbox>
            <div class="mi-model-con">
              <ul>
                <li>
                  普通会员卡
                  <el-select
                    size="small"
                    v-model="importInfo.memberCard"
                  >
                    <el-option
                      label="请选择会员卡"
                      value=""
                    ></el-option>
                  </el-select>
                </li>
                <li>
                  限次会员卡
                  <el-select
                    size="small"
                    v-model="importInfo.memberCard"
                  >
                    <el-option
                      label="请选择会员卡"
                      value=""
                    ></el-option>
                  </el-select>
                </li>
                <li>
                  等级会员卡
                  <el-select
                    size="small"
                    v-model="importInfo.memberCard"
                  >
                    <el-option
                      label="请选择会员卡"
                      value=""
                    ></el-option>
                  </el-select>
                </li>
              </ul>
              <p class="tips">导入并激活后，将自动给被导入的用户发放会员卡</p>
            </div>
          </div>
          <div>
            <el-checkbox>批量打标签</el-checkbox>
            <div class="mi-model-con">
              <span>选择标签</span>
              <el-select
                size="small"
                v-model="importInfo.label"
              >
                <el-option
                  label="请选择标签"
                  value=""
                ></el-option>
              </el-select>
              <p class="tips">导入并激活后，将自动给被导入的用户打标签</p>
            </div>
          </div>
          <div>
            <el-checkbox>批量设置分销员分组</el-checkbox>
            <div class="mi-model-con">
              选择分组
              <el-select
                size="small"
                v-model="importInfo.group"
              >
                <el-option
                  label="未分组"
                  value=""
                ></el-option>
              </el-select>
              <p class="tips">导入并激活后，将自动给被导入的用户设置分销员分组</p>
            </div>
          </div>
        </div>
        <h2 class="mi-title">第二步：数据导入</h2>
        <div class="mi-model">
          <div class="mi-flex">
            <span class="flex-title">上传文件：</span>
            <el-input
              size="small"
              style="width:170px;"
            ></el-input>
            <el-upload
              action=""
              class="mi-upload"
              accept=".xls,.xlsx"
              :before-upload="beforeUploadHandle"
              :limit="1"
            >
              <el-button size="small">选择文件</el-button>
            </el-upload>
          </div>
          <div class="mi-flex">
            <span class="flex-title">导入规则：</span>
            <ol>
              <li>1、文件当前仅支持excel格式。</li>
              <li>2、导入以手机号为唯一标识，系统中已有手机号无法导入，请严格按照模板文件格式填写用户信息。</li>
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
        >导入</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
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

      }
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
  methods: {
    beforeUploadHandle (file) {
      console.log(file)
      let isXls = file.type === 'application/vnd.ms-excel application/x-excel'
      let isXlsx = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      if (!isXls && !isXlsx) {
        this.$message.warning('上传文件只支持xls、xlsx格式！')
        return false
      }
      return true
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
}
</style>
