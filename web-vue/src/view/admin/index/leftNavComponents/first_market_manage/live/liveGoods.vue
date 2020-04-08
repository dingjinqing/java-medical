<template>
  <div class="content">
    <el-table
      :data="goodList">
      <el-table-column
        prop="name"
        label="商品信息"
        width="130px">
        <template slot-scope="scope">
          <img :src="scope.row.coverImg" style="width: 50px;height: 50px;float: left;" />
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="goodsSn"
        label="商品货号">
      </el-table-column>
      <el-table-column
        prop="shopPrice"
        label="店铺售价（元）">
      </el-table-column>
      <el-table-column
        prop="price"
        label="直播售价（元） ">
      </el-table-column>
      <el-table-column
        prop="goodsNumber"
        label="库存">
      </el-table-column>
      <el-table-column
        prop="sortName"
        label="商家分类">
      </el-table-column>
      <el-table-column
        prop="goodsTag"
        label="商品标签">
        <template slot-scope="scope">
          <div v-for="item in scope.row.goodsTag" :key="item.id">
            {{item.name}}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="brandName"
        label="品牌">
      </el-table-column>
    </el-table>
  </div>
</template>
<script>

import { getGoodsList } from '@/api/admin/marketManage/live'
export default {
  components: {
  },
  props: {
    liveId: {
      type: Number,
      default: 0
    }
  },
  mounted () {
    this.langDefault()
    this.showGoods(this.liveId)
  },
  data () {
    return {
      goodList: []
    }
  },
  methods: {
    showGoods (data) {
      if (data > 0) {
        getGoodsList(data).then(res => {
          if (res.error === 0) {
            this.goodList = res.content
            this.dialogTableVisible = true
          }
        })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  margin: 0,auto;
}
</style>
