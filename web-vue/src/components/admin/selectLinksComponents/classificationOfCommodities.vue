<template>
  <div>
    <div class="content">
      <table width='100%'>
        <thead>
          <tr>
            <td>分类名称</td>

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
            <td>{{item.title}}<img
                v-if="item.children?true:false"
                :src="leftImg"
                @click="handleImg(index)"
              ></td>

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
export default {
  data () {
    return {
      trList: [
        {
          title: '111',
          path: 'pages/index/index',
          spanId: '',
          children: [
            {
              title: '456',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '门店列表页',
          path: 'pages/storelist/storelist',
          spanId: ''
        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          spanId: '',
          children: [
            {
              title: '789',
              path: 'pages/index/index'
            }
          ]
        }

      ],
      clickIindex: null,
      tbodyFlag: true,
      leftImg: this.$imageHost + '/image/admin/shop_deco/icon_down.png'
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      console.log(2)
      this.$http.$on('classificationOfCommodities', (res) => {
        console.log(res)
      })
    },
    // 向下点击
    handleImg (index) {
      console.log(this.trList[index].children)
      this.trList.splice(index + 1, 0, this.trList[index].children[0])
    },
    // 行选中高亮
    handleClick (index) {
      this.clickIindex = index
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
.top_container {
  padding-bottom: 10px;
}
.top_container {
  display: flex;
  justify-content: space-around;
}
.top_left {
  display: flex;
  align-items: center;
  /* margin-left: 7px; */
}
.top_middle {
  display: flex;
  align-items: center;
}

.clickClass {
  background-color: #eee !important;
}
.spanClass {
  display: block !important;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
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
  width: 120px;
}
thead td:nth-of-type(2) {
  width: 175px;
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
img {
  margin-left: 10px;
}
</style>
