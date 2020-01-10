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
                ref="editor"
              />
            </div>
          </div>
        </div>
      </div>

    </div>

    <!--保存-->
    <div class="footer">
      <div
        class="save"
        @click="handleToSave()"
      >{{$t('shopStyle.saveText')}}</div>
    </div>
  </div>
</template>
<script>
import { saveScoreDocumentUpdate, scoreCopywritingRequest } from '@/api/admin/memberManage/scoreManage/scoreCfg.js'
export default {
  components: {
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  data () {
    return {
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
  },
  methods: {
    // 初始化数据
    loadDefaultData () {
      scoreCopywritingRequest().then(res => {
        if (res.error === 0) {
          this.editMsg = res.content.document
        }
      })
    },

    // 清空内容
    clear () {
      this.$refs.editor.clear()
    },

    // 点击使用模板文案
    handleToUseTemplate () {
      this.editMsg = this.templateData
    },

    // 保存
    handleToSave () {
      saveScoreDocumentUpdate({ document: this.editMsg }).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('memberCard.saveSuccess'))
        }
      })
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
</style>
