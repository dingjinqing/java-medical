<template>
  <div class="frontEndDisplay">
    <div class="frontEndDisplayMain">
      <div class="frontEndTop">
        <img :src="$imageHost+'/image/admin/notice_img.png'">
        <span>{{$t('scoreCfg.frontEndDesc')}}</span>
      </div>
      <div class="content">

        <div class="left">
          <img
            :src="$imageHost+'/image/admin/score_qd.png'"
            style="width: 100%;"
          >
          <div
            class="show_area"
            @click="handleToDirectDecorate(templateList)"
            v-if="templateList"
          >
            {{$t('scoreCfg.alreadyTemplate')}}：<span>{{templateName}}</span>
          </div>
          <div
            class="show_area hidden"
            v-else
          >
            <div>
              <h1>{{$t('scoreCfg.infoOne')}}</h1>
              <p>{{$t('scoreCfg.infoTwo')}}</p>
              <p>{{$t('scoreCfg.infoThree')}}</p>
            </div>
          </div>

          <div class="score_get">{{$t('scoreCfg.getAndPayDetail')}}</div>
          <div class="score_set_bottom">
            <div class="score_set_content">
              <div class="score_setDiv">{{$t('scoreCfg.signGetScoreEx')}}</div>
              <div class="score_setDiv">2019-04-29 12:00:00</div>
              <div class="score_setDiv">+1</div>
            </div>
          </div>
        </div>
        <div class="right">
          <div class="right_div">
            <div class="r_top">
              <span>{{$t('scoreCfg.scoreIntro')}}</span>
              <span>{{$t('scoreCfg.scoreIntroDesc')}}</span>
            </div>
            <div class="r_bottom">
              <el-button
                type="primary"
                size="small"
                @click="handleToRightBtn(0)"
              >{{$t('scoreCfg.editScore')}}</el-button>
            </div>

          </div>
          <div class="right_div">
            <div class="r_top">
              <span>{{$t('scoreCfg.defineBySelf')}}</span>
              <span>{{$t('scoreCfg.show')}}</span>
            </div>
            <div class="r_bottom">
              <div
                class="templateList"
                v-if="templateList"
              >
                <span>{{templateName}}</span>
                <img
                  @click="handleTodel()"
                  :src="$imageHost+'/image/admin/icon_delete.png'"
                  style="cursor:pointer"
                >
              </div>
              <div>
                <el-button
                  plain
                  size="small"
                  @click="handleChooseTemplate"
                >{{$t('scoreCfg.chooseTemplate')}}</el-button>
              </div>
              <!-- <div>
                <el-button
                  plain
                  size="small"
                  @click="handleToRightBtn(1)"
                >{{$t('scoreCfg.chooseTemplate')}}</el-button>
              </div> -->
              <span
                @click="handleToRightBtn(2)"
                class="common"
              >{{$t('scoreCfg.refresh')}}</span>
              <span
                @click="handleToRightBtn(3)"
                class="common"
              >{{$t('scoreCfg.addTemplate')}}</span>
            </div>

          </div>
        </div>
      </div>
    </div>

    <!-- 底部 -->
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        @click="saveScoreHandler"
      >保存</el-button>
    </div>

    <!--选择运费模板-->
    <!-- <SelectTemplateDialog @handleToSendData="handleToSendData" /> -->

    <!-- 选择模板弹窗 -->
    <SelectTemplate
      :tuneUpSelectTemplate='tuneUpSelectTemplate'
      @handleSelectTemplate='handleSelectTemplate'
      ref="templateRefresh"
    />
  </div>
</template>
<script>
import { scorePageIdUpdate, getScoreConfigRequest } from '@/api/admin/memberManage/scoreManage/scoreCfg.js'
export default {
  components: {
    // SelectTemplateDialog: () => import('./selectTemplateDialog'),
    SelectTemplate: () => import('@/components/admin/selectTemplate') // 选择模板弹窗

  },
  data () {
    return {
      templateList: null, // 模板id
      templateName: null, // 模板名称
      tuneUpSelectTemplate: false
    }
  },
  watch: {
    // '$store.state.util.integralDataNotice' (newData) {
    //   alert('iiiiii')
    //   let obj = {
    //     templateList: this.templateList
    //   }
    //   console.log(obj)
    //   this.$emit('toNoticeSend', obj, 1)
    // }
  },
  mounted () {
    this.loadDefaultData()
  },
  methods: {
    // 1 - 加载默认的数据
    loadDefaultData () {
      getScoreConfigRequest().then(res => {
        if (res.error === 0) {
          this.templateName = res.content.pageName
          this.templateList = res.content.scorePageId
        }
      })
    },

    // 保存前端展示设置
    saveScoreHandler () {
      scorePageIdUpdate({
        scorePageId: this.templateList
      }).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('memberCard.auditOption'))
        }
      })
    },

    // 2 - 保存数据
    // updateScorePageId () {
    //   let obj = {
    //     'scorePageId': this.templateList
    //   }
    //   console.log(obj)
    //   scorePageIdUpdate(obj).then(res => {
    //     if (res.error === 0) {
    //       // success
    //       this.$message.success(this.$t('memberCard.auditOption'))
    //     }
    //   })
    // },

    // 右侧按钮点击汇总
    handleToRightBtn (flag) {
      switch (flag) {
        case 0:
          this.$router.push({
            name: 'integralDescription'
          })
          break
        case 1:
          console.log(111)
          this.$http.$emit('choiseTemplateDialog', true)
          break
        case 2:
          this.$refs.templateRefresh.getTemplateData()
          this.$nextTick(() => {
            this.$message.success('刷新成功')
          })
          break
        case 3:
          this.$router.push({
            path: '/admin/home/main/decorationHome',
            query: {
              pageId: -1
            }
          })
          break
      }
    },
    // 模板删除icon点击
    handleTodel () {
      this.templateList = ''
    },
    // 选择模板弹窗选中数据
    // handleToSendData (res) {
    //   console.log(res)
    //   this.templateList = res.userID
    // },
    // 调起选择模板弹窗
    handleChooseTemplate () {
      this.tuneUpSelectTemplate = !this.tuneUpSelectTemplate
    },
    // 选择模板弹窗数据回显
    handleSelectTemplate (data) {
      this.templateList = data.pageId
      this.templateName = data.pageName
    },
    handleToDirectDecorate (val) {
      // TODO 等小程序管理->页面装修->自定义页面装修完成
      this.$router.push({
        path: '/admin/home/main/decorationHome',
        query: {
          pageId: val
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.frontEndDisplay {
  width: 100%;
  .frontEndDisplayMain {
    position: relative;
    background-color: #fff;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    .frontEndTop {
      // width: 70%;
      width: 845px;
      height: 40px;
      line-height: 40px;
      border: 1px solid #f2e1c8;
      background: #fff7ec;
      color: #666;
      padding-left: 12px;
      margin: 20px auto;
    }
    .content {
      // width: 70%;
      width: 845px;
      overflow: hidden;
      margin: 0 auto;
      margin-bottom: 70px;
      .left {
        // width: 40%;
        width: 350px;
        border: 1px solid #ccc;
        background: #eee;
        position: relative;
        float: left;
        .show_area {
          width: 100%;
          height: 150px;
          display: flex;
          align-items: center;
          justify-content: center;
          background: #fff;
          border: 1px dashed #5a8bff;
          div {
            display: flex;
            justify-content: center;
            flex-direction: column;
            align-items: center;
            h1 {
              font-size: 14px;
              color: #333;
              margin-bottom: 5px;
            }
            p {
              font-size: 12px;
              color: #999;
              margin-top: 5px;
            }
          }
          span {
            cursor: pointer;
            color: #5a8bff;
          }
        }
        .score_get {
          width: 100%;
          height: 40px;
          line-height: 40px;
          text-align: center;
          background: #fff;
          border-bottom: 1px solid #ccc;
        }
        .score_set_bottom {
          width: 100%;
          height: 150px;
          background: #fff;
          position: relative;
          .score_set_content {
            width: 95%;
            height: 65px;
            margin: 0 auto;
            border-bottom: 1px solid #ccc;
            .score_setDiv {
              height: 25px;
              line-height: 25px;
              padding: 10px;
            }
            .score_setDiv:nth-of-type(1) {
              color: #000;
            }
            .score_setDiv:nth-of-type(2) {
              color: #666;
            }
            .score_setDiv:nth-of-type(3) {
              position: absolute;
              right: 10px;
              top: 1px;
              color: #ff6666;
              font-size: 16px;
            }
          }
        }
      }
      .right {
        // width: 57%;
        width: 480px;
        float: right;
        margin-left: 15px;
        // margin-left: 3%;
        background: #f8f8f8;
        padding: 15px 12px 22px;
        border-radius: 3px;
        .right_div {
          border: 1px solid #eee;
          margin-bottom: 20px;
          .r_top {
            background: #f5f5f5;
            padding: 10px;
            span:nth-of-type(1) {
              color: #333;
              font-weight: 600;
              padding-right: 20px;
            }
            span:nth-of-type(2) {
              color: #999;
              font-size: 12px;
              border-bottom: 1px solid #eee;
            }
          }
          .r_bottom {
            padding: 10px;
            overflow: hidden;
            display: flex;
            .templateList {
              min-width: 40px;
              height: 25px;
              font-size: 14px;
              line-height: 22px;
              margin: 0 15 0 15px;
              position: relative;
              padding-right: 18px;
              display: flex;
              align-items: center;
              span {
                display: inline-block;
                margin-right: 5px;
              }
            }
            .common {
              display: inline-block;
              height: 32px;
              line-height: 32px;
              color: #5a8bff;
              margin-left: 10px;
              cursor: pointer;
            }
          }
        }
      }
    }
  }
  .footer {
    height: 50px;
    line-height: 50px;
    text-align: center;
    background: #fff;
    border-top: 1px solid #e4e7ed;
  }
}
</style>
