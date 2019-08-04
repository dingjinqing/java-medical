<template>
  <div>
    <brandManagementContent
      @turnComponents='handleTurnComponents'
      :turnIndex="turnIndex"
    />
    <!-- <addBrand @turnComponents='handleTurnComponents' /> -->
  </div>
</template>
<script>
import brandManagementContent from '@/view/admin/index/leftNavComponents/goods_manage/brandManagement/brandManagementContent'
import addBrand from '@/view/admin/index/leftNavComponents/goods_manage/brandManagement/addBrand'
export default {
  components: { brandManagementContent, addBrand },
  data () {
    return {
      componentsFlag: 1,
      turnIndex: null
    }
  },
  mounted () {

  },
  methods: {
    // 切换组件
    handleTurnComponents ({ index, turnIndex }) {
      if (turnIndex === 1) {
        this.turnIndex = 1
      }
      this.componentsFlag = index
    }
  }
}
</script>
<style scoped>
</style>
