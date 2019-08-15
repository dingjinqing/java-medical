<template>
  <div class="addingGoodsContent">
    <!-- 主要内容区域 -->
    <el-card class="box-card">
      <!-- 步骤条  -->
      <section class="header">
        <el-steps
          :active="active"
          finish-status="finish"
          simple
          style="margin-top: 0px"
        >
          <el-step
            @click.native="basicInfo"
            :title="title1"
            icon="el-icon-edit"
          >
          </el-step>
          <el-step
            @click.native="goodsDetails"
            :title="title2"
            icon="el-icon-edit"
          ></el-step>
          <el-step
            @click.native="otherInfo"
            :title="title3"
            icon="el-icon-edit"
          ></el-step>
        </el-steps>
      </section>
      <!-- 编辑基本信息|编辑商品详情|编辑分销信息 -->
      <!-- 路由匹配到的组件将渲染在这里 -->
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </el-card>

  </div>
</template>
<script>
import addingGoodsDetails from '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsDetails'
import addingGoodsProductInfo from './addingGoodsProductInfo'
import addingGoodsDistributionInfo from '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsDistributionInfo'
export default {
  components: { addingGoodsProductInfo, addingGoodsDetails, addingGoodsDistributionInfo },
  data () {
    return {
      active: 1,
      title1: '编辑商品信息',
      title2: '编辑商品详情',
      title3: '编辑分销信息'
    }
  },
  watch: {
    '$route': function (to, from) {
      console.log(to)
      switch (to.name) {
        case 'basic':
          this.active = 1
          break
        case 'details':
          this.active = 2
          break
        case 'distribution':
          this.active = 3
          break
      }
    }
  },
  methods: {
    basicInfo () {
      this.$router.push(
        { path: 'basic', query: {} }
      )
    },
    goodsDetails () {
      this.$router.push(
        { path: 'details', query: {} }
      )
    },
    otherInfo () {
      this.$router.push(
        { path: 'distribution', query: {} }
      )
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
