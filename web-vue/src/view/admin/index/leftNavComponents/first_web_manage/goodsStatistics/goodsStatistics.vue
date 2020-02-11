<template>
  <div class="goodsSytle">
    商品统计页面
    <div class="table">
      <table class="table_content">
        <thead>
          <tr>
            <th></th>
            <th
              v-for="(item, index) in refDate"
              :key="index"
            >{{item}}</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(item, index) in tableData"
            :key="index"
          >
            <td>{{goodsName[index]}}</td>
            <td
              v-for="(item1, index1) in item"
              :key="index1"
            >{{item1}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      refDate: ['2020-10-10', '2020-10-11', '2020-10-12'],
      goodsName: ['花露水', '毛衣', '手机'],
      tableData: [
        [12, 13, 14],
        [13, 14, 12],
        [13, 14, 12]
      ],
      search: ''
    }
  },
  methods: {
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (index, row) {
      console.log(index, row)
    }
  }
}

</script>
<style lang="scss" scoped>
.goodsSytle {
  .table {
    margin-top: 20px;
    border: 1px solid #efedee;
    .table_content {
      width: 100%;
      tr {
        th {
          height: 60px;
          line-height: 60px;
          background: #f5f5f5;
          border: 1px solid #efedee;
        }
      }
      td {
        text-align: center;
        height: 60px;
        line-height: 60px;
        border: 1px solid #efedee;
      }
      .out {
        width: 200px;
        height: 60px;
        position: relative;
      }
      .out::before {
        content: "";
        position: absolute;
        left: 25px;
        top: 10px;
        width: 74%;
        box-sizing: border-box;
        border-bottom: 1px solid #e5e5e5;
        transform-origin: bottom center;
        transform: rotateZ(16deg) scale(1.414);
      }
    }
  }
}
</style>
