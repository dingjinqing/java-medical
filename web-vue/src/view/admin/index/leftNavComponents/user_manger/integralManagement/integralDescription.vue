<template>
  <div class="integralDescription">
    <div class="integralDescriptionMain">
      <div class="container">
        <div class="leftDiv">
          <div class="leftTitle"></div>
          <img :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'">
          <div class="leftContent">
            <div
              class="show_pass"
              v-html="editMsg"
            >
            </div>
          </div>
        </div>
        <div class="rightDiv">
          <div class="rightTop">
            <span>{{$t("scoreCfg.pageContent")}}：</span>
            <span
              @click="handleToUseTemplate()"
              style="color:#5a8bff;cursor:pointer"
            >{{$t("scoreCfg.useTemplate")}}</span>
          </div>
          <div class="rightContent">
            <div>
              <!--编辑器-->
              <TinymceEditor
                v-model="editMsg"
                :disabled="disabled"
                @onClick="onClick"
                @input="input"
                ref="editor"
              />
            </div>

          </div>
        </div>
      </div>

    </div>

    <!--保存-->
    <SaveComponent />
  </div>
</template>
<script>
import { saveScoreDocumentUpdate, scoreCopywritingRequest } from '@/api/admin/memberManage/scoreManage/scoreCfg.js'
export default {
  components: {
    SaveComponent: () => import('./saveComponent'),
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  data () {
    return {
      example: null, // 展示示例
      editMsg: '',
      disabled: false,
      templateData: null
    }
  },
  created () {
    this.loadDefaultData()
  },
  watch: {

    '$store.state.util.integralDataNotice' (newData) {
      let obj = {
        editMsg: this.editMsg
      }
      console.log(obj)
      // 编写节分说明
    },
    lang () {
      this.templateData = this.$t('scoreCfg.templateData')
    }
  },
  mounted () {
    this.langDefault()
    this.$http.$on('saveIntegralDescription', res => {
      console.log('save score description')

      saveScoreDocumentUpdate({ document: this.editMsg }).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('memberCard.saveSuccess'))
        }
      })
    })
  },
  methods: {
    // 加载默认数据
    loadDefaultData () {
      this.example = this.templateData
      scoreCopywritingRequest().then(res => {
        if (res.error === 0) {
          this.editMsg = res.content.document
          if (this.editMsg) {
            this.example = this.editMsg
          }
        }
      })
    },
    // 鼠标单击的事件
    onClick (e, editor) {
      console.log(e, editor)
      this.example = this.editMsg
    },
    // 清空内容
    clear () {
      this.$refs.editor.clear()
    },
    // 编辑器输入内容获取
    input (val) {
      console.log(val)
    },
    // 点击使用模板文案
    handleToUseTemplate () {
      this.editMsg = this.templateData
    }
  }
}
</script>
<style lang="scss" scoped>
.integralDescription {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  min-height: 600px;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;

  .integralDescriptionMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    height: 100%;
    display: flex;
    justify-content: center;
    .container {
      display: flex;
      .leftDiv {
        position: relative;
        width: 330px;
        height: 600px;
        border: 1px solid #ededed;
        margin-right: 10px;
        img {
          display: inline-block;
          width: 330px;
          height: 55px;
        }
        .leftTitle {
          width: 100%;
          position: absolute;
          top: 0;
          height: 55px;
          color: #fff;
          text-align: center;
          line-height: 70px;
        }
        .leftContent {
          .show_pass {
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
        }
      }
      .rightDiv {
        width: 600px;
        padding: 20px 25px;
        height: 630px;
        background: #f8f8f8;
        border: 1px solid #ededed;
        .rightTop {
          margin-bottom: 20px;
        }
        .rightContent {
          width: 100%;
          display: flex;
          justify-content: flex-end;
          div {
            width: 472px;
          }
        }
      }
    }
  }
}
</style>
