<template>
  <el-dialog
    :visible.sync="dialogVisiable"
    :title="$t('memberCard.customActionOpt')"
    width="30%">
      <div class="container">
        <div class="top">
            <span>{{$t('memberCard.choosedType')}}:</span>
            <el-radio v-model="action.type" :label="0">{{$t('memberCard.single')}}</el-radio>
            <el-radio v-model="action.type" :label="1">{{$t('memberCard.multiple')}}</el-radio>
            <el-radio v-model="action.type" :label="2">{{$t('memberCard.text')}}</el-radio>
            <el-radio v-model="action.type" :label="3">{{$t('memberCard.picture')}}</el-radio>
        </div>
        <div class="center">
          <el-form :model="action" :inline-message="true" ref="content">
            <div class="title ruleTitle">
              <el-form-item prop="title" :rules="myRules.titleRule">
                  <span class="left-title">{{$t('memberCard.titleTwo')}}:</span>
                  <span class="input">
                      <el-input v-model="action.title" :placeholder="$t('memberCard.inputTitle')" size="small"></el-input>
                  </span>
                </el-form-item>
                <div class="tip">{{$t('memberCard.mostTwenty')}}</div>
            </div>
            <div v-if="showPicture">
              <el-form-item prop="pictureNumber">
                <span class="left-title">{{$t('memberCard.uploadPic')}}:</span>
                <el-select v-model="action.pictureNumber" size="small" style="width: 110px;">
                    <el-option v-for="item in pictureNumOptions"
                      :key="item.id"
                      :label="item.label"
                      :value="item.id">
                    </el-option>
                </el-select>
                <span class="tip">{{$t('memberCard.mostPictureNum')}}</span>
               </el-form-item>
            </div>
            <div  v-if="showContent">
              <div v-for="(value,index) in action.content"
                  :key="index"
                  class="ruleTitle">
                  <el-form-item :prop="'content.'+index" :rules="myRules.contentRule">
                      <span class="left-title">{{$t('memberCard.option')}}{{index+1}}:</span>
                      <span class="input"><el-input v-model="action.content[index]" size="small"></el-input></span>
                  </el-form-item>
                  <i v-if="index>1" @click="deleteContent(index)"
                        class="el-icon-delete my-icon-style">
                  </i>
              </div>
              <div>
                  <span class="left-title"></span>
                  <el-button size="small" @click="addContent">{{$t('memberCard.addOption')}}</el-button>
                  <span class="tip">{{$t('memberCard.mostTenOption')}}</span>
              </div>
            </div>
          </el-form>

        </div>
        <div class="bottom">
          <span class="left-title">{{$t('memberCard.conditionChecked')}}:</span>
          <el-checkbox v-model="action.conditionChecked" :disabled="action.type===0"></el-checkbox>
          <span class="tip">{{$t('memberCard.mustInput')}}</span>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisiable = false">{{$t('memberCard.cancel')}}</el-button>
        <el-button size="small" type="primary" @click="addCustomAction">{{$t('memberCard.sure')}}</el-button>
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
          conditionChecked: true,
          pictureNumber: 1
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
        this.action.title = null
        this.$refs['content'].resetFields()
        this.$emit('update:visiable', val)
      }
    },
    showContent () {
      return this.contentShowItems.includes(this.action.type)
    },
    showPicture () {
      return this.action.type === 3
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
        this.action = JSON.parse(JSON.stringify(this.customAction))
        // 默认为创建新激活项
        this.createFlag = true
        if (this.action.title !== null) {
          this.createFlag = false
        }
      } else {
        // 结束清理数据
        this.$refs['content'].resetFields()
      }
    },
    lang () {
      this.initPictureNumOptions()
      this.myRules.contentRule[0].message = this.$t('memberCard.inputOptionContent')
      this.myRules.titleRule[0].message = this.$t('memberCard.inputTitleContent')
    }
  },
  data () {
    return {
      action: {type: 0},
      MAX_SIZE: 10,
      createFlag: true,
      myRules: {
        contentRule: [{required: true, message: '', trigger: 'blur'}],
        titleRule: [
          {required: true, message: '', trigger: 'blur'},
          {pattern: /^.{1,20}$/, message: '最多可填20个字'}
        ]
      },
      contentShowItems: [0, 1],
      pictureNumOptions: []
    }
  },
  mounted () {
    this.langDefault()
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
          if (res.type === 2 || res.type === 3) {
            delete res.content
          }
          if (res.type !== 3) {
            delete res.pictureNumber
          }
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
    },
    initPictureNumOptions () {
      this.pictureNumOptions = Array(6).fill(1).map((val, index) => {
        return {
          id: index + 1,
          label: val + index + this.$t('memberCard.unitZhang')
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
      align-items: center;
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
