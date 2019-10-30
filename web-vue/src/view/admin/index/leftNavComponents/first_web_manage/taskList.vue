<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="tabClickHandler"
        :lazy="true"
      >
        <el-tab-pane
          v-for="(item, index) in tabInfo"
          :key="index"
          :label="item.title"
          :name="item.name"
        ></el-tab-pane>
      </el-tabs>

      <div>
        <p class="type_title">
          <img
            :src="image + '/image/admin/task_shop.png'"
            alt=""
          >店铺
        </p>
        <ul class="type_content">
          <li style="display: flex;">
            <span class="tips">开店必备</span>
            <span class=" task_list_desc">已注册小程序</span>
            <a
              href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=1889&extra=page%3D1%26filter%3Dsortid%26sortid%3D19"
              target="_blank"
            >查看教程</a>
          </li>
        </ul>
      </div>
      <div>
        <p class="type_title">
          <img
            :src="image + '/image/admin/task_goods.png'"
            alt=""
          >商品
        </p>
        <ul class="type_content">
          <li style="display: flex;">
            <span class="tips">任务</span>
            <span class=" task_list_desc">已设置运费模板</span>
          </li>
        </ul>
      </div>
      <div>
        <p class="type_title">
          <img
            :src="image + '/image/admin/task_order.png'"
            alt=""
          >订单
        </p>
      </div>
      <div>
        <p class="type_title">
          <img
            :src="image + '/image/admin/task_market.png'"
            alt=""
          >营销
        </p>
      </div>

    </div>
  </div>
</template>
<script>
// 引入组件
import { shopAssistantRequest } from '@/api/admin/survey.js'
export default {
  components: {
  },
  data () {
    return {
      image: 'http://mpdevimg2.weipubao.cn',
      tabSwitch: '1',
      tabInfo: [{
        title: '待完成',
        name: '1'
      }, {
        title: '已完成',
        name: '2'
      }]
    }
  },
  mounted () {
    // 初始化数据
    this.handleClick()
  },
  methods: {
    // 店铺助手列表
    handleClick () {
      let obj = {
        isAuthOk: 1,
        storeSizeNum: 5,
        commentOver: 3,
        deliverOver: 3,
        refundOver: 3,
        applyOver: 3,
        examineOver: 2,
        couponSizeNum: 10
      }
      shopAssistantRequest(obj).then((res) => {
        if (res.error === 0) {
          this.storeList = res.content
        }
      })
    },

    // tab栏切换
    tabClickHandler () {
      this.handleClick()
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    .wrapper {
      .el-button {
        margin-left: 5px;
      }
    }
  }
}
.type_title {
  font-weight: 600;
  line-height: 40px;
}
.type_title img {
  vertical-align: middle;
  margin-right: 6px;
}
.type_content {
  padding-left: 20px;
}
.type_content li {
  display: flex;
  align-items: center;
  line-height: 42px;
  border-bottom: 1px solid #e5e5e5;
}
.type_content li .task_list_desc {
  flex: 1;
  margin-left: 4px;
}
.type_content li .tips {
  font-size: 12px;
  border: 1px solid #5a8bff;
  color: #5a8bff;
  border-radius: 10px;
  letter-spacing: 1px;
  padding: 0 4px 0 5px;
  line-height: 16px;
}
.type_content li a {
  margin-left: 15px;
  color: #5a8bff;
  text-decoration: none;
}
</style>
