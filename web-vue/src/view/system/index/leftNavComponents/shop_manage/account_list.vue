<template>
  <div class="content">
    <div class="name">
      <span>系统管理后台</span>
      <span>/</span>
      <span>商家账号添加</span>
    </div>
    <div class="account-info">
      <div class="account-list">商家账号列表</div>
      <div class="account-add">商家账号添加</div>
    </div>
    <el-card class="box-card">
      <div class="card-content">
        <el-select v-model="value" placeholder="选择审核状态" style=}}>
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-input v-model="input1" placeholder="请输入用户名、昵称" class="input userName"></el-input>
        <el-input v-model="input2" placeholder="请输入公司名称" class="input companyName"></el-input>
        <el-button type="primary" @click="add1">搜索</el-button>
      </div>
    </el-card>
    <el-card class="box-card table-card">
      <el-table
        :data="tableData"
        style="width: 100%"
        height="400"
        border>
        <el-table-column prop="username" label="用户名" width="110"></el-table-column>
        <el-table-column prop="nickname" label="昵称" width="110"></el-table-column>
        <el-table-column prop="company" label="公司名称" width="110"></el-table-column>
        <el-table-column prop="checkstate" label="审核状态" width="110"></el-table-column>
        <el-table-column prop="shoplevel" label="店铺等级" width="110"></el-table-column>
        <el-table-column prop="shopcount" label="店铺总数" width="110"></el-table-column>
        <el-table-column prop="addtime" label="添加时间" width="110"></el-table-column>
        <el-table-column prop="pay" label="首次续费" width="110"></el-table-column>
        <el-table-column prop="endtime" label="到期时间" width="110"></el-table-column>
        <el-table-column prop="totalmoney" label="续费总额" width="110"></el-table-column>
        <el-table-column prop="mobile" label="手机号" width="110"></el-table-column>
        <el-table-column prop="operation" label="操作" width="110"></el-table-column>
      </el-table>
    </el-card>
    <div class="footer">
      <span>每页10行记录，当前页面：1，总页数：1，总记录数为：13</span>
      <el-pagination
        style='display: inline'
        :page-size="20"
        :pager-count="11"
        layout="prev, pager, next"
        :total="1000">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  // components: { Header, shopManager },
  data () {
    return {
      options: [
        { value: '选项1', label: '申请中' },
        { value: '选项2', label: '审核通过' },
        { value: '选项3', label: '审核不通过' },
        { value: '选项4', label: '已禁用' }
      ],
      value: '',
      input1: '',
      input2: '',
      tableData: [
        {
          username: '赵鑫',
          nickname: 'true',
          company: '掌上先机',
          checkstate: 'pass',
          shoplevel: 4,
          shopcount: 90,
          addtime: '2019-07-01',
          pay: 200,
          endtime: '2020-07-01',
          totalmoney: 9999,
          mobile: 18834567890,
          operation: '操作'
        },
        {
          username: '赵鑫',
          nickname: 'true',
          company: '掌上先机',
          checkstate: 'pass',
          shoplevel: 4,
          shopcount: 90,
          addtime: '2019-07-01',
          pay: 200,
          endtime: '2020-07-01',
          totalmoney: 9999,
          mobile: 18834567890,
          operation: '操作'
        },
        {
          username: '赵鑫',
          nickname: 'true',
          company: '掌上先机',
          checkstate: 'pass',
          shoplevel: 4,
          shopcount: 90,
          addtime: '2019-07-01',
          pay: 200,
          endtime: '2020-07-01',
          totalmoney: 9999,
          mobile: 18834567890,
          operation: '操作'
        },
        {
          username: '赵鑫',
          nickname: 'true',
          company: '掌上先机',
          checkstate: 'pass',
          shoplevel: 4,
          shopcount: 90,
          addtime: '2019-07-01',
          pay: 200,
          endtime: '2020-07-01',
          totalmoney: 9999,
          mobile: 18834567890,
          operation: '操作'
        },
        {
          username: '赵鑫',
          nickname: 'true',
          company: '掌上先机',
          checkstate: 'pass',
          shoplevel: 4,
          shopcount: 90,
          addtime: '2019-07-01',
          pay: 200,
          endtime: '2020-07-01',
          totalmoney: 9999,
          mobile: 18834567890,
          operation: '操作'
        },
        {
          username: '赵鑫',
          nickname: 'true',
          company: '掌上先机',
          checkstate: 'pass',
          shoplevel: 4,
          shopcount: 90,
          addtime: '2019-07-01',
          pay: 200,
          endtime: '2020-07-01',
          totalmoney: 9999,
          mobile: 18834567890,
          operation: '操作'
        }
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
  }
  .name {
    padding: 8px 15px;
    background: #f5f5f5;
    border-radius: 2px;
    margin-bottom: 18px;
  }
  .name span {
    margin-right: 5px;
    font-size: 14px;
    line-height: 24px;
    height: 24px;
    color: #999;
  }
  .name span:nth-child(1) {
    color: #333;
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
    border: 1px solid #fff;
    padding: 15px;
  }
  .card-content {
    /* display: flex; */
    height: 30px;
  }
  .el-card__body {
    padding: 15px !important;
  }
  .el-select {
    width: 200px;
    margin-right: 5px;
  }
  .el-option {
    font-size: 10px;
  }
  .input {
    width: 150px;
    margin-right: 5px;
  }
  .footer {
    display: flex;
    margin-top: 10px;
    align-items: center;
    justify-content: flex-end;
  }
  .table-card {
    height: 400px;
    margin-bottom: 20px;
  }
</style>
