<template>
  <div class="addingGoodsDetails">
    <section class="con">
      <!-- 编辑商品详情的左侧组件 -->
      <addingGoodsDetailsLeft class="left" />
      <!-- 编辑商品详情的右侧组件 -->
      <addingGoodsDetailsRight class="right" />
    </section>
  </div>
</template>
<script>
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

  },
  watch: {

  },
  methods: {

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
.details {
  margin-top: 35px;
}
.con {
  display: flex;
  justify-content: center;
  /* align-items: center; */
  margin-top: 30px;
}
.left {
  margin-right: 10px;
}
.right {
  margin-left: 10px;
}
</style>
