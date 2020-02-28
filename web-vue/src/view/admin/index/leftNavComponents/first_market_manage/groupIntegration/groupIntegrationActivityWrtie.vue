<template>
  <div class="content">
    <el-row>
      <el-col :span="4"></el-col>
      <el-col :span="16">
        <el-row>
          <el-col :span="24">
            <div class="warnThing">
              <el-alert
                title="注：请按活动设置如实编辑活动规则。编辑后的活动规则文本将替换系统默认规则文本，默认规则文本中的活动参数将失效"
                type="warning"
                show-icon
                center
                :closable='false'>
             </el-alert>
          </div>
          </el-col>
        </el-row>
        <el-row style="margin-top: 15px;">
          <el-col :span="7">
            <div>
               <img style="width: 100%;" :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'">
            </div>
            <div
              class="showMsg"
              v-html="editMsg"
            >
            </div>
          </el-col>
          <el-col :span="17">
            <div class="right">
              <el-form ref="form" label-width="80px">
                <el-form-item label="页面内容:">
                  <el-radio-group v-model="isUseDefault" @change="changeRadio">
                      <el-radio :label="1">使用模板文案</el-radio>
                      <el-radio :label="0">自定义模板</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-form>
              <div class="innerRight">
                <TinymceEditor
                  v-model="editMsg"
                  :disabled="disabled"
                  ref="editor"
                />
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
                <div class="footer">
                  <div
                    class="save"
                    @click="saveMsg"
                  >保存</div>
                </div>
          </el-col>
        </el-row>
      </el-col>
     <el-col :span="4"></el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  components: {
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  props: ['sendMsg'],
  mounted () {
    // 初始化数据
    this.langDefault()
    this.setValue()
    this.defalutValue()
  },
  data () {
    return {
      editMsg: null,
      isUseDefault: 0,
      disabled: false,
      msg: this.$t('scoreCfg.templateData')
    }
  },
  methods: {
    setValue () {
      if (!this.isEmpty(this.sendMsg)) {
        console.log('收到的值' + this.sendMsg)
        this.isUseDefault = this.sendMsg.is_use_default
        this.editMsg = this.sendMsg.document
      }
    },
    defalutValue () {
      let value = this.isUseDefault
      if (value === 1) {
        this.editMsg = this.msg
      }
      if (value === 0) {
        this.editMsg = null
      }
    },
    changeRadio () {
      console.log(this.isUseDefault)
      this.defalutValue()
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    checkInfo () {
      if (this.isEmpty(this.isUseDefault)) {
        this.$message.warning('请选择页面内容')
      }
      if (this.isEmpty(this.editMsg)) {
        this.$message.warning('请填写页面内容')
      }
    },
    saveMsg () {
      let msg = {
        'document': this.editMsg,
        'is_use_default': String(this.isUseDefault)
      }
      this.$emit('ActivityMsg', msg)
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  .el-col {
  min-height: 1px
}
}
.showMsg {
  border: 1px solid #ededed;
  border-top: 1px solid #f2f2f2;
  p {
    line-height: 20px;
  }
  padding: 0 8px;
  overflow-y: auto;
  height: 540px;
  .headline {
    font-weight: bold;
  }
}
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: fixed;
  z-index: 2;
  bottom: 0;
  padding: 10px 0;
  left: 0;
  right: 0;
  margin-right: 1%;
  .save {
    width: 70px;
    height: 30px;
    line-height: 30px;
    border: none;
    background: #5a8bff;
    color: #fff;
    margin: auto;
    cursor: pointer;
  }
}
.right{
  margin-left: 22px;
  height: 595px;
  border: 1px solid #ededed;
  background-color: #f8f8fa;
}
.innerRight{
  margin: 9px
}
.warnThing{
  border: 1px solid #ffd5a3;
}
</style>
