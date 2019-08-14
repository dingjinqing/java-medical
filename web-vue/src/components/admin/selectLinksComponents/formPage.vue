<template>
  <div>
    <div
      class="content"
      v-if="page_one"
    >
      <table width='100%'>
        <thead>
          <tr>
            <td>名称</td>
            <td>营业状态</td>
            <td>链接</td>
          </tr>
        </thead>
        <tbody v-if="tbodyFlag">
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index)"
          >
            <td>{{item.title}}</td>
            <td class="link">{{item.status}}</td>
            <td class="tb_decorate_a">
              {{item.path}}
            </td>
          </tr>
        </tbody>

      </table>
      <div
        class="noData"
        v-if="!tbodyFlag"
      >
        <img :src="noImg">
        <span>暂无相关数据</span>
      </div>
    </div>
    <div
      class="content_two"
      v-else
    >
      <table width='100%'>
        <thead>
          <tr>
            <td>名称</td>

            <td>链接</td>
          </tr>
        </thead>
        <tbody v-if="tbodyFlag">
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index)"
          >
            <td>{{item.title}}</td>

            <td class="tb_decorate_a">
              {{item.path}}
            </td>
          </tr>
        </tbody>

      </table>
      <div
        class="noData"
        v-if="!tbodyFlag"
      >
        <img :src="noImg">
        <span>暂无相关数据</span>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  data () {
    return {
      trList: [
        {
          title: '111',
          path: 'pages/index/index',
          classification: '分类1',
          status: '营业中',
          spanId: ''
        },
        {
          title: '门店列表页',
          path: 'pages/storelist/storelist',
          spanId: '',
          classification: '分类2',
          status: '歇业中'
        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          classification: '分类3',
          spanId: '',
          status: '营业中'
        }

      ],
      clickIindex: null,
      tbodyFlag: true,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      page_one: false
    }
  },
  computed: {
    ...mapGetters(['selectlinksLevelOneBottom']),
    selectlinksLevelOneBottom_ () {
      return this.selectlinksLevelOneBottom
    }
  },
  watch: {
    selectlinksLevelOneBottom_ (newData, oldData) {
      // 初始化
      this.defaultData(newData)
    }

  },
  methods: {
    ...mapActions(['choisePagePath']),
    defaultData (newData) {
      console.log(newData)
      if (newData === 6) {
        this.page_one = false
      } else {
        this.page_one = true
      }
      console.log(this.page_one)
    },
    // 行选中高亮
    handleClick (index) {
      this.clickIindex = index
      this.choisePagePath(this.trList[index].path)
    }
  }
}
</script>
<style scoped>
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 650px; */
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.noData span {
  margin: 10px;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
.clickClass {
  background-color: #eee !important;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}
thead td:nth-of-type(1) {
  width: 220px;
}
thead td:nth-of-type(2) {
  width: 104px;
}

tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 10px;
  vertical-align: middle !important;
  text-align: center;
}
.content_two td:nth-of-type(2) {
  width: 490px !important;
}
</style>
