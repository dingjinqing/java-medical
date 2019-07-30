/* eslint-disable vue/no-dupe-keys */
<template>
  <div class="steps">
    <!-- 步骤条 -->
    <el-steps
      :space="200"
      :active="step"
      class="step"
    >
      <el-step
        title="编辑基本信息"
        description=""
      ></el-step>
      <el-step
        title="编辑商品详情"
        description=""
      ></el-step>
      <el-step
        title="编辑分销信息"
        description=""
      ></el-step>
      <transition name="fade">
        <router-view class="view"></router-view>
      </transition>
    </el-steps>
    <!-- footer-btn-group-->
    <div class="footer-btn-group">
      <el-button
        @click.native.prevent="handleToList"
        v-show="toList"
        type="primary"
      >保存后返回列表</el-button>
      <el-button
        @click.native.prevent="handlePreStep"
        v-show="preStep"
      >上一步</el-button>
      <el-button
        @click.native.prevent="handleNextStep"
        v-show="nextStep"
      >下一步</el-button>
      <el-button
        @click.native.prevent="handleAddAfterSaving"
        v-show="addAfterSaving"
        type="primary"
      >保存后继续添加</el-button>
      <el-button
        @click.native.prevent="handlePreview"
        v-show="preview"
        type="primary"
      >保存后预览商品</el-button>
    </div>
  </div>
</template>
<script>
import $ from 'jquery'
export default {
  name: 'addingGoodsSteps',
  data () {
    return {
      isRouter: false,
      step: 1,
      toList: false,
      preStep: false,
      nextStep: false,
      addAfterSaving: false,
      preview: false
    }
  },
  methods: {
    handleToList () {
      console.log('保存后返回列表')
    },
    handlePreStep () {
      console.log('上一步')
      this.$router.go(-1)
      this.step--
      this.goStep(this.step)
      $('html,body').animate({ scrollTop: 0 }, 500)
    },
    handleNextStep () {
      console.log('下一步')
      this.$router.push('/addingGoodsSteps/step' + (this.step + 1))
      var _this = this
      setTimeout(function () {
        if (_this.isRouter) {
          _this.step++
          _this.goStep(_this.step)
        }
      })
      $('html,body').animate({ scrollTop: 0 }, 500)
    },
    handleAddAfterSaving () {
      console.log('保存后继续添加')
    },
    handlePreview () {
      console.log('保存后预览')
    },
    goStep (n) {
      switch (n) {
        case 1:
          this.toList = true; this.preStep = false; this.nextStep = true; this.addAfterSaving = false; this.preview = false
          break
        case 2:
          this.toList = true; this.preStep = true; this.nextStep = true; this.addAfterSaving = true; this.preview = true
          break
        case 3:
          this.toList = true; this.preStep = true; this.nextStep = false; this.addAfterSaving = true; this.preview = true
          break
      }
    }
  },
  watch: {
    '$route': function (to, from) {
      this.isRouter = true
    }
  }
}
</script>
<style scoped>
.steps {
  border: solid 2px red;
}
</style>
