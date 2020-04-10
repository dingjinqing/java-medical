<template>
  <el-dialog
    :visible.sync="dialogVisiable"
    title="自定义激活项"
    width="30%">
      <div class="container">
        <div class="top">
            <span>选项类型:</span>
            <el-radio v-model="action.type" :label="0">单选</el-radio>
            <el-radio v-model="action.type" :label="1">多选</el-radio>
            <el-radio v-model="action.type" :label="2">文本</el-radio>
        </div>
        <div class="center">
          <el-form :model="action" :inline-message="true" ref="content">
            <div class="title ruleTitle">
              <el-form-item prop="title" :rules="myRules.titleRule">
                  <span class="left-title">标题:</span>
                  <span class="input">
                      <el-input v-model="action.title" placeholder="请输入标题" size="small"></el-input>
                  </span>
                </el-form-item>
                <div class="tip">最多可填写20个字</div>
            </div>
            <div  v-if="showContent">
              <div v-for="(value,index) in action.content"
                  :key="index"
                  class="ruleTitle">
                  <el-form-item :prop="'content.'+index" :rules="myRules.contentRule">
                      <span class="left-title">选项{{index+1}}:</span>
                      <span class="input"><el-input v-model="action.content[index]" size="small"></el-input></span>
                  </el-form-item>
                  <i v-if="index>1" @click="deleteContent(index)"
                        class="el-icon-delete my-icon-style">
                  </i>
              </div>
              <div>
                  <span class="left-title"></span>
                  <el-button size="small" @click="addContent">添加选项</el-button>
                  <span class="tip">最多可添加10个选项</span>
              </div>
            </div>
          </el-form>

        </div>
        <div class="bottom">
          <span class="left-title">条件校验:</span>
          <el-checkbox v-model="action.conditionChecked" :disabled="action.type===0"></el-checkbox>
          <span class="tip">必填</span>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisiable = false">取 消</el-button>
        <el-button size="small" type="primary" @click="addCustomAction">确 定</el-button>
      </div>
  </el-dialog>
</template>

<script>
export default {
  props: {
    visiable: {
      type: Boolean,
      default: false
    },
    customAction: {
      type: Object,
      default: () => {
        return {
          type: 0,
          title: null,
          content: [null, null],
          conditionChecked: true
        }
      }
    }
  },
  computed: {
    dialogVisiable: {
      get () {
        return this.visiable
      },
      set (val) {
        this.$emit('update:visiable', val)
      }
    },
    showContent () {
      return this.action.type !== 2
    }
  },
  watch: {
    'action.type' () {
      if (this.createFlag) {
        this.$refs['content'].resetFields()
      }
    },
    dialogVisiable (val) {
      if (val) {
        // 开始初始化数据
        console.log(this.customAction)
        this.action = JSON.parse(JSON.stringify(this.customAction))
        if (this.action.title !== null) {
          this.createFlag = false
        }
      } else {
        // 结束清理数据
        this.$refs['content'].resetFields()
      }
    }
  },
  data () {
    return {
      action: {type: 0},
      MAX_SIZE: 10,
      createFlag: true,
      myRules: {
        contentRule: [{required: true, message: '请输入选项内容', trigger: 'blur'}],
        titleRule: [{required: true, message: '请输入自定义标题', trigger: 'blur'}]
      }
    }
  },
  methods: {
    addContent () {
      if (this.action.content.length >= this.MAX_SIZE) {
        this.$message.warning('最多可添加10个选项')
      } else {
        this.action.content.push(null)
      }
    },
    deleteContent (index) {
      this.action.content.splice(index, 1)
    },
    addCustomAction () {
      this.$refs['content'].validate((valid) => {
        if (valid) {
          let res = JSON.parse(JSON.stringify(this.action))
          if (this.createFlag) {
            // 新建
            this.$emit('setNewCustomAction', res)
          } else {
            // 更新
            this.$emit(`update:customAction`, res)
          }
          this.dialogVisiable = false
        } else {
          this.$message.warning('请填写信息')
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
$content-width: 100%;
$title-width: 80px;
$interval: 10px;
$tip-color: #999;
$input-width: 150px;
$container-height: 263px;
/deep/ .el-dialog__body {
    padding-bottom: 0px;
}
/deep/ .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner{
    background-color: #409EFF;
    border-color: #409EFF;
}

.container{
  height: $container-height;
  overflow-y: auto;
}
.center,
.bottom{
     margin-bottom: $interval;
    .title{
        width: $content-width;
        white-space: nowrap;
    }
    .ruleTitle{
      display: flex;
      flex-direction: row;
    }
    .left-title{
        display: inline-block;
        width: $title-width;
        margin-right: $interval;
        text-align: right;
    }
    .input{
        display: inline-block;
        width: $input-width;
    }
    .tip{
        margin-left: $interval;
        color: $tip-color;
    }
}
.my-icon-style{
  color: #5a8bff;
  cursor: pointer;
  margin-left: $interval;
}
</style>
