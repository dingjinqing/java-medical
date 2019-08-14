<template>
  <div>
    <div class="top">
      <span>营销活动/ {{this.navText}}</span>
    </div>
    <div class="content">
      <table width='100%'>
        <thead>
          <tr>
            <td>名称</td>
            <td>有效期</td>
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
            <td class="link">{{item.date}}</td>
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
          date: ' 2019-07-12 11:07:34至2019-08-10 11:07:35',
          spanId: ''
        },
        {
          title: '门店列表页',
          path: 'pages/storelist/storelist',
          spanId: '',
          date: ' 2019-07-18 11:04:06至2019-08-10 11:04:08'
        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          date: ' 2019-07-18 11:04:06至2019-08-10 11:04:08',
          spanId: ''
        }

      ],
      clickIindex: null,
      tbodyFlag: true,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      navText: ''
    }
  },
  computed: {
    ...mapGetters(['selectlinksIndex']),
    selectlinksIndex_ () {
      console.log(this.selectlinksIndex)
      return this.selectlinksIndex
    }
  },
  watch: {
    selectlinksIndex_: {
      handler (newData, oldData) {
        console.log(newData)
        // 初始化数据
        this.defaultData(newData)
      },
      immediate: true
    }
  },
  methods: {
    ...mapActions(['choisePagePath']),
    defaultData (newData) {
      console.log(newData)
      if (newData.levelIndex === 1) {
        this.navText = newData.navText
      }
      console.log(newData)
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
.top {
  padding: 10px;
  font-size: 14px;
  color: #333;
}
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
</style>
