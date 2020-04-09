<template>
  <el-dialog
    :visible.sync="dialogVisiable"
    title="自定义激活项"
    width="30%">
    <div class="top">
        <span>选项类型:</span>
        <el-radio v-model="type" :label="0">单选</el-radio>
        <el-radio v-model="type" :label="1">多选</el-radio>
        <el-radio v-model="type" :label="2">文本</el-radio>
    </div>
    <div class="center">
        <div class="title">
            <span class="left-title">标题:</span>
            <span class="input">
                <el-input v-model="content[0].value" placeholder="请输入标题" size="small"></el-input>
            </span>
            <span class="tip">最多可填写20个字</span>
        </div>
        <div v-if="type===0">
            <div v-for="item in content.slice(1)"
                :key="item.index">
                <span class="left-title">选项{{item.index}}:</span>
                <span class="input"><el-input v-model="item.value" size="small"></el-input></span>
            </div>
        </div>
        <div>
            <span class="left-title"></span>
            <el-button size="small" @click="addContent">添加选项</el-button>
            <span class="tip">最多可添加10个选项</span>
        </div>
        <div></div>

    </div>
    <div class="bottom"></div>
    <div slot="footer" class="dialog-footer">
       <el-button @click="dialogVisiable = false">取 消</el-button>
       <el-button type="primary" @click="dialogVisiable = false">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  props: {
    visiable: {
      type: Boolean,
      default: false
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
    }
  },
  data () {
    return {
      type: 0,
      content: [
        {
          index: 0,
          value: null
        },
        {
          index: 1,
          value: null
        },
        {
          index: 2,
          value: null
        }
      ],
      MAX_SIZE: 11
    }
  },
  methods: {
    addContent () {
      if (this.content.length >= this.MAX_SIZE) {
        this.$message.warning('最多可添加10个选项')
      } else {
        this.content.push(
          {
            index: this.content.length,
            value: null
          }
        )
      }
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
.center{
     margin-bottom: $interval;
    .title{
        width: $content-width;
        white-space: nowrap;
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
</style>
