<template>
  <div class="addingGoodsDetails">
    <!-- 编辑商品详情的左侧组件 -->
    <addingGoodsDetailsLeft :getParams="getParams" />
    <!-- 编辑商品详情的右侧组件 -->
    <addingGoodsDetailsRight />
    <!-- 编辑商品详情的底部按钮 -->

    <section class="addingGoodsFooter">
      <el-button
        class="btn"
        type="primary"
        size="small"
        @click.native.prevent="handleToList"
      >保存后返回列表</el-button>
      <el-button
        class="btn"
        size="small"
        @click.native.prevent="handlePreStep"
      >上一步</el-button>
      <el-button
        size="small"
        class="btn"
        @click.native.prevent="handleNextStep"
      >下一步</el-button>
      <el-button
        type="primary"
        size="small"
        class="btn"
        @click.native.prevent="handleAddAfterSaving"
      >保存后继续添加</el-button>
      <el-button
        type="primary"
        size="small"
        class="btn"
        @click.native.prevent="handlePreview"
      >保存后预览商品</el-button>
    </section>
    <el-button @click="test">测试按钮</el-button>
  </div>
</template>
<script>
// import Tinymce from '@/components/admin/Tinymce'
import addingGoodsDetailsLeft from './addingGoodsDetailsLeft'
import addingGoodsDetailsRight from './addingGoodsDetailsRight'
export default {
  name: 'addingGoodsDetails',
  components: { addingGoodsDetailsLeft, addingGoodsDetailsRight },
  data () {
    return {

    }
  },
  created () {

  },
  computed: {
    getParams () {
      return this.$route.query
    }
  },
  watch: {

  },
  methods: {
    test () {
      console.log(this.getParams)
    },
    handleToList () {

    },
    handlePreStep () {
      this.$router.push(
        { path: 'basic', query: {} }
      )
    },
    handleNextStep () {
      this.$emit('toThird')
    },
    handleAddAfterSaving () {

    },
    handlePreview () {

    }

  }
}
</script>
<style scoped>
.addingGoodsFooter {
  border-top: 1px solid #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  z-index: 2;
  width: 88%;
  height: 50px;
  background: #f8f8fa;
  margin-left: -20px;
}
.btn {
  margin: 0 10px;
}
</style>
