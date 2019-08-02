<template>
  <div class="addingGoodsContent">
    <!-- 卡片 -->
    <el-card class="box-card">
      <!-- 步骤条  -->
      <el-steps
        :active="active"
        finish-status="finish"
        simple
        style="margin-top: 0px"
      >

        <el-step
          @click.native="toFirstPage"
          :title="title1"
          icon="el-icon-edit"
        >
        </el-step>

        <el-step
          @click.native="toSecondPage"
          :title="title2"
          icon="el-icon-edit"
        ></el-step>

        <el-step
          @click.native="toPageThree"
          :title="title3"
          icon="el-icon-edit"
        ></el-step>

      </el-steps>
      <!-- 编辑基本信息|编辑商品详情|编辑分销信息 -->
      <div v-if="active === 1">
        <addingGoodsBasicInfo
          :active='active'
          @toSecondPage='changeActive'
        />
      </div>
      <div v-else-if="active === 2">
        <addingGoodsDetails
          @toFirst='toFirst'
          @toThird='toThird'
        />
      </div>
      <div v-else-if="active === 3">
        <addingGoodsDistributionInfo @toPre='toPre' />
      </div>
      <div v-else>
        Not Component
      </div>
    </el-card>

  </div>
</template>
<script>
import addingGoodsDetails from '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsDetails'
import addingGoodsBasicInfo from '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsBasicInfo'
import addingGoodsDistributionInfo from '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsDistributionInfo'
export default {
  components: { addingGoodsBasicInfo, addingGoodsDetails, addingGoodsDistributionInfo },
  data () {
    return {
      active: 1,
      title1: '编辑商品信息',
      title2: '编辑商品详情',
      title3: '编辑分销信息'
    }
  },
  methods: {
    toPre () {
      this.active = 2
    },
    toFirst () {
      this.active = 1
    },
    toThird () {
      this.active = 3
    },
    changeActive () {
      this.active = 2
    },
    toFirstPage () {
      this.active = 1
    },
    toSecondPage () {
      this.active = 2
    },
    toPageThree () {
      this.active = 3
    }
  }
}
</script>

<style scoped>
.addingGoodsContent {
  padding: 10px;
}
.box-card {
  margin-bottom: 38px;
}
</style>
