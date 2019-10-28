<template>
  <div class="goodsRecommend">
    <div class="filters">
      <div class="filter_left">
        <el-input
          v-model="recommendTitle"
          placeholder="请输入模板名称"
          class="default_input"
          size="small"
        ></el-input>
        <el-button
          type="primary"
          size="small"
        >筛选</el-button>
      </div>
      <el-button
        type="primary"
        size="small"
      >新建商品推荐模板</el-button>
    </div>
    <div class="table_box">
      <el-table
        v-loading="loading"
        :data="dataList"
        style="width:100%;"
        border
        :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
        :cell-style="{
            'text-align':'center'
          }"
      >
        <el-table-column
          prop="recommendTitle"
          label="模板名称"
        ></el-table-column>
        <el-table-column
          prop="updateTime"
          label="更新时间"
        ></el-table-column>
        <el-table-column label="应用页面">
          <template slot-scope="scope">
            <div class="used_page">
              <div
                class="page_left"
                v-if="scope.row.usedPage"
              >
                <p
                  v-for="(item,index) in scope.row.usedPage"
                  :key="index"
                >{{usedPage[item]}}</p>
              </div>
              <div class="page_right">
                <span>设置</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="模板状态">
          <template slot-scope="scope">
            {{scope.row.isStart | statusFilter}}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <div class="operation">
              <p>编辑</p>
              <p>启用</p>
              <p>删除</p>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      recommendTitle: null,
      loading: false,
      dataList: null,
      usedPage: {
        cart: '购物车页',
        orderlist: '订单列表页',
        bargainitem: '砍价活动页',
        groupbuyitem: '参团活动页',
        search: '商品列表页',
        payment: '支付完成页',
        order_complete: '订单完成页',
        new_search: '商品搜索页',
        item: '商品详情页'
      }
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      this.dataList = [
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'search', 'new_search'] },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'search', 'new_search'] },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: false, usedPage: ['item', 'order_complete', 'new_search'] },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: false, usedPage: ['item', 'search', 'new_search'] },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'groupbuyitem', 'new_search'] },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'search', 'new_search'] },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true, usedPage: ['item', 'search', 'new_search'] },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: false, usedPage: ['item', 'search', 'new_search'] },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true },
        { recommendTitle: '智能推荐', updateTime: '2018-10-23 11:11:11', isStart: true }
      ]
    }
  },
  filters: {
    statusFilter (value) {
      return value ? '已启用' : '已停用'
    }
  }
}
</script>
<style lang="scss" scoped>
.goodsRecommend {
  padding: 10px;
}
.filters {
  display: flex;
  padding: 10px;
  background-color: #fff;
  .filter_left {
    flex: 1;
  }
}
.table_box {
  background-color: #fff;
  padding: 10px;
  margin-top: 10px;
}
.used_page {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  .page_left {
    display: flex;
    flex-direction: column;
  }
  .page_right {
    color: #409eff;
    cursor: pointer;
  }
}
.operation {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  > p {
    cursor: pointer;
    color: #409eff;
  }
}
.default_input {
  width: 180px;
}
</style>
