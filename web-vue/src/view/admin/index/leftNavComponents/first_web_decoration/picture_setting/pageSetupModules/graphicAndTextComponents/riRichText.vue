<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('textModule.rightTextTips')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="content">
          <TinymceEditor
            ref='editor'
            @input="handleToGetText"
            :value='modulesData.rich_text'
          />
        </div>
      </div>
      <!--模块私有end-->
    </div>
  </div>
</template>
<script>
export default {
  components: {
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      moduleSaveData: {
        'rich_text': ''
      },
      flag: true
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        this.moduleSaveData = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    moduleSaveData: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 获取富文本编译器输入的内容
    handleToGetText (res) {
      this.moduleSaveData.rich_text = res
      console.log(this.moduleSaveData.rich_text)
    }
  }
}
</script>
<style lang="scss" scoped>
.rightCommodity {
  .rightCommodityMain {
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
