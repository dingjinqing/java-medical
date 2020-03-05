<template>
  <div class="riName">
    <div class="riNameMain">
      <h2>{{$t('textModule.rightTextTips')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <TinymceEditor
          ref='editor'
          @input="handleToGetText"
          :value='modulesSaveData.rich_text'
        />
      </div>
    </div>
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  components: {
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      imageTuneUp: false, // 图片选择弹窗调起
      modulesSaveData: {
        'rich_text': ''
      } // 模块保存数据
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        if (this.modulesData !== -1) {
          this.modulesSaveData = this.modulesData
        }
      },
      immediate: true
    },
    // 监听数据变换
    modulesSaveData: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
    // 获取富文本编译器输入的内容
    handleToGetText (res) {
      this.modulesSaveData.rich_text = res
      console.log(this.modulesSaveData.rich_text)
    }
  }
}
</script>
<style lang="scss" scoped>
.riName {
  .riNameMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    //模块私有样式  --------------
    .main {
      margin-top: 20px;
    }
    //end
  }
}
</style>
