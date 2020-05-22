<template>
  <div class="riName">
    <div class="riNameMain">
      <!--模块私有区域-->
      <h2>{{$t('shopNotices.topTips')}}</h2>
      <div class="main">
        <div class="content">
          <p
            class="title"
            style="color:#333"
          >{{$t('shopNotices.title')}}</p>
          <div class="text">
            <p>{{$t('shopNotices.p1')}}</p>
            <p>{{$t('shopNotices.p2')}}</p>
            <p>{{$t('shopNotices.p3')}}</p>
            <p>{{$t('shopNotices.p4')}}</p>
            <p>{{$t('shopNotices.p5')}}</p>
            <p>{{$t('shopNotices.p6')}}</p>
            <p>{{$t('shopNotices.p7')}}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      imageTuneUp: false, // 图片选择弹窗调起
      modulesSaveData: {

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
      .content {
        .title {
          margin: 20px 0;
        }
        .text {
          color: #999;
          font-size: 12px;
          line-height: 25px;
        }
      }
    }
    //end
  }
}
</style>
