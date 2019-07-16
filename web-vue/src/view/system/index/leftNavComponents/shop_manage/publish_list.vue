<template>
  <div class="content">
    <div class="name">
      <span>系统管理后台</span>
      <span class="line">/</span>
      <span class="shoper">商家账号添加</span>
    </div>
    <div class="account-info">
      <div class="account-list">商家账号列表</div>
      <div class="account-add">商家账号添加</div>
    </div>
    <el-card class="box-card" :body-style="{ padding: '15px' }">
      <div v-for="item in title" :key="item.text" class="text item">
        {{ item.text }}
      </div>
      <el-button type="primary" class="button" size="mini">保存</el-button>
    </el-card>
    <!-- <div>
      <el-form ref="form" :model="form" label-width="100px" @submit.prevent="onSubmit" style="margin:20px;width:20%;min-width:200px;">
        <el-form-item label="用户名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select v-model="form.state" placeholder="请选择状态">
            <el-option label="申请中" value="on"></el-option>
            <el-option label="审核通过" value="pass"></el-option>
            <el-option label="审核不通过" value="off"></el-option>
            <el-option label="已禁用" value="no"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最大SKU数量">
          <el-input v-model="form.skunumber"></el-input>
        </el-form-item>
        <el-form-item label="最大店铺数量">
          <el-input v-model="form.shopnumber"></el-input>
        </el-form-item>
        <el-form-item label="首次续费时间">
          <div class="block">
            <el-date-picker
              v-model="value1"
              type="datetime"
              placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="到期时间">
          <div class="block">
            <el-date-picker
              v-model="value2"
              type="datetime"
              placeholder="选择日期时间">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="公司名称">
          <el-input v-model="form.company"></el-input>
        </el-form-item>
        <el-form-item label="销售员">
          <el-input v-model="form.salesman"></el-input>
        </el-form-item>
        <el-form-item label="地理位置">
          <el-switch on-text="" off-text=""></el-switch>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="初始销量">
          <el-switch
            active-text="已开启"
            inactive-text="已关闭"
            active-color="#f7931e"
            inactive-color="#ddd"
            v-model="form.state">
          </el-switch>
        </el-form-item>
        <el-form-item label="添加评价">
          <el-switch
            active-text="已开启"
            inactive-text="已关闭"
            active-value="1"
            inactive-value="0"
            active-color="#f7931e"
            inactive-color="#ddd"
            v-model="form.delivery"
            @change="changeSwitch(form.delivery)"
            >
          </el-switch>
        </el-form-item>
      </el-form>
    </div> -->
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
        { text: '添加评价' }
      ],
      value: true
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
    float: right
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
