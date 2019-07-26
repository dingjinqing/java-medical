<template>
  <div>
    <Crumbs :mTitle="M_title" />
    <brandManagementContent
      v-if="componentsFlag===1?true:false"
      @turnComponents='handleTurnComponents'
      :turnIndex="turnIndex"
    />
    <addBrand
      v-if="componentsFlag===2?true:false"
      @turnComponents='handleTurnComponents'
    />
  </div>
</template>
<script>
import Crumbs from '@/components/admin/crumbs'
import brandManagementContent from '@/view/admin/index/leftNavComponents/goods_manage/brandManagement/brandManagementContent'
import addBrand from '@/view/admin/index/leftNavComponents/goods_manage/brandManagement/addBrand'
export default {
  components: { Crumbs, brandManagementContent, addBrand },
  data () {
    return {
      M_title: [
        '商品管理', '品牌管理'
      ],
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
