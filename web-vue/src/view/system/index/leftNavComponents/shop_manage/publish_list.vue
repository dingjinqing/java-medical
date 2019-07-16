<t<template>
  <div class="content">
    <div class="name">
      <span>系统管理后台</span>
      <span class="line">/</span>
      <span class="shoper">商家账号添加</span>
    </div>
    <!-- <el-tabs type="border-card">
      <el-tab-pane label="商家账号列表" class="list">
      </el-tab-pane>
      <el-tab-pane label="商家账号添加">商家账号添加</el-tab-pane>
    </el-tabs> -->
    <div class="account-info">
      <div class="account-list">商家账号列表</div>
      <div class="account-add">商家账号添加</div>
    </div>
    <el-card class="box-card" :body-style="{ padding: '15px' }">
      <div v-for="item in title" :key="item.text" class="text item">
        {{ item.text }}
        {{item.text === '审核状态' ? hello : <input />}}
        <!-- {{item.text === '审核状态' ? hello : <input />}} -->
      </div>
      <el-button type="primary" class="button" size="mini">保存</el-button>
    </el-card>
  </div>
</template>

<script>
export default {
  // components: { Header, shopManager },
  data () {
    return {
      title: [
        { text: '用户名' },
        { text: '密码' },
        { text: '昵称' },
        { text: '审核状态' },
        { text: '最大SKU数量' },
        { text: '最大店铺数量' },
        { text: '首次续费时间' },
        { text: '到期时间' },
        { text: '手机号' },
        { text: '公司名称' },
        { text: '销售员' },
        { text: '地理位置' },
        { text: '详细地址' },
        { text: '初始销量' },
        { text: '添加评价' },
      ]
    }
  },
  methods: {
    add1: function () {
      console.log(123)
    }
  }
}
</script>

<style scoped>
  .content {
    width: 100%;
    margin: 10px 5px;
    height: 700px;
    width: 1376px;
    background: #e6e9f0;
    overflow-y:scroll;
    overflow-x:hidden;
  }
  .name {
    padding: 8px 15px;
    background: #f5f5f5;
    border-radius: 2px;
    margin-bottom: 18px;
  }
  .name span {
    margin-right: 5px;
  }
  .line,
  .shoper {
    color: #ccc;
  }
  .account-info {
    display: flex;
    border: 1px solid #ddd;
    background: #ccc;
  }
  .account-list,
  .account-add {
    padding: 9px 10px 10px 10px;
    text-align: center;
    font-weight: 700;
    background: #fff;
  }
  .box-card {
    margin: 8px 0px;
    background-color: #fff;
    border: 1px solid #fff;
    padding: 15px;
    overflow: hidden;
  }
  .info {
    padding: 8px 10px;
    margin-right: 50px;
  }
  .input-wrapper {
    height: 30px;
    line-height: 30px;
    width: 200px;
    padding: 8px 10px;
  }
  .el-input__inner {
    width: 100%;
    height: 100%;
  }
  .wrapper {
    display: flex;
    border-top: 1px solid #ccc;
    border-bottom: 1px solid #ccc;
  }
  .info {
    height: 30px;
    line-height: 30px;
    margin-right: 20px;
  }
  .input {
    height: 30px;
    width: 150px;
  }

   .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
    border-top: 1px solid #ccc;
  }

  .button {
    margin-bottom: 50px;
  }
</style>
