<template>
  <div
    class="tinymceEditor"
    :class="special"
  >
    <!-- <div v-if="flag"> -->
    <Editor
      :key="1"
      id="cnTinymce"
      v-model="myValue"
      :init="cnInit"
      :disabled="disabled"
      @onClick="onClick"
    >
    </Editor>
  </div>
</template>
<script>
import tinymce from 'tinymce/tinymce'
import Editor from '@tinymce/tinymce-vue'
import 'tinymce/themes/silver/theme'
import 'tinymce/plugins/image'
import 'tinymce/plugins/media'
import 'tinymce/plugins/table'
import 'tinymce/plugins/lists'
// import 'tinymce/plugins/contextmenu'
import 'tinymce/plugins/wordcount'
import 'tinymce/plugins/colorpicker'
// import 'tinymce/plugins/textcolor'

export default {
  name: 'tinymceEditor',
  components: { tinymce, Editor },
  props: {
    // 传入一个value，使组件支持v-model绑定
    value: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    plugins: {
      type: [String, Array],
      default: 'lists image media table  wordcount '
    },
    toolbar: {
      type: [String, Array],
      default: ' forecolor  |undo redo   | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | lists image media table | removeformat'
    },
    height: {
      type: Number,
      default: 450
    }
  },
  inject: ['reload'],
  data () {
    return {
      flag: true,
      myValue: this.value,
      special: '', // 装修内的左文右图特定高度
      langName: 'zh_CN',
      cnInit: {
        selector: '#cnTinymce',
        language_url: `http://${window.location.host}/static/tinymce/tinymce_languages/langs/zh_CN.js`,
        language: 'zh_CN',
        height: 450,
        skin_url: `http://${window.location.host}/static/tinymce/skins/ui/oxide`,
        plugins: this.plugins,
        toolbar: this.toolbar,
        branding: false,
        menubar: false,
        // 此处为图片上传处理函数，这个直接用了base64的图片形式上传图片，
        // 如需ajax上传可参考https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_handler
        images_upload_handler: (blobInfo, success, failure) => {
          console.log(blobInfo.filename(), blobInfo.uri(), blobInfo)
          const img = 'data:image/jpeg;base64,' + blobInfo.base64()
          console.log(img)
          success(img)
        }
      }
    }
  },
  mounted () {
    // this.init.language_url = `${window.location.host}/static/tinymce/tinymce_languages/langs/zh_CN.js`
    // this.init.skin_url = `${window.location.host}/static/tinymce/skins/ui/oxide`
    console.log(window.location.host)
    // 初始化语言
    this.langDefault()
    tinymce.init(this.cnInit)
    // this.myHeight = this.height
    console.log(this.height)
    if (this.height === 100) {
      this.special = 'special'
    } else {
      this.special = ''
    }
    this.$forceUpdate()
  },
  methods: {
    // 添加相关的事件，可用的事件参照文档=> https://github.com/tinymce/tinymce-vue => All available events
    // 需要什么事件可以自己增加
    onClick (e) {
      this.$emit('onClick', e, tinymce)
    },
    // 可以添加一些自己的自定义事件，如清空内容
    clear () {
      this.myValue = ''
    }
  },
  watch: {
    value (newValue) {
      this.myValue = newValue
    },
    myValue (newValue) {
      this.$emit('input', newValue)
    },
    lang (type) {
      tinymce.EditorManager.execCommand('mceRemoveEditor', true, 'cnTinymce')
      if (type === 'zh_CN') {
        this.cnInit.language = 'zh_CN'
      } else {
        delete this.cnInit.language
        delete this.cnInit.language_url
      }
      tinymce.EditorManager.execCommand('mceAddEditor', true, 'cnTinymce')
    }
  }
}
</script>
<style lang="scss" scoped>
.special {
  /deep/ .tox-tinymce {
    height: 205px !important;
  }
}
</style>
