<template>
  <div>
    <div class="content">
      <div
        class="top_container"
        v-if="topHiddenFlag"
      >
        <div class="top_left">
          <div>品牌名称：</div>
          <el-input
            v-model="pageName"
            placeholder="请输入品牌名称"
            size="mini"
          ></el-input>
          <div class="top_right">
            <el-button
              type="primary"
              size="mini"
              @click="handleSearch()"
            >搜索</el-button>
          </div>
        </div>
      </div>
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
            @click="handleClick(index,item)"
          >
            <td v-if="!topHiddenFlag">{{item.title}}<img
                v-if="item.children?true:false"
                :src="imgIndex===index&&imgFlag?leftImg[1].img:leftImg[0].img"
                @click="handleImg(index)"
              ></td>
            <td
              v-else
              class="isLeft"
            >
              <img :src="tdHiddenImg">
              <span>范思哲</span>

            </td>
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
          spanId: '',
          children: [
            {
              title: '456',
              path: 'pages/index/index'
            },
            {
              title: 'lalala',
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
            },
            {
              title: '789123',
              path: 'pages/index/index'
            },
            {
              title: 'aaa',
              path: 'pages/index/index'
            }
          ]
        },
        {
          title: '子页',
          path: 'pages/cart/cart',
          spanId: '',
          children: [
            {
              title: 'zzzzz',
              path: 'pages/index/index'
            }
          ]
        }

      ],
      clickIindex: null,
      tbodyFlag: true,
      leftImg: [
        {
          img: this.$imageHost + '/image/admin/shop_deco/icon_down.png'
        },
        {
          img: this.$imageHost + '/image/admin/shop_deco/icon_up.png'
        }
      ],
      imgIndex: null,
      imgFlag: false,
      pageIndex: 0,
      pageName: '',
      topHiddenFlag: false,
      tdHiddenImg: this.$imageHost + '/upload/7467397/image/20190507/crop_N7Fu7EaKRtaZri18.gif'
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
      if (newData.levelIndex === 2) {
        this.pageIndex = newData.index
        if (newData.index !== 0 && newData.index !== 1 && newData.index !== 3) {
          this.topHiddenFlag = true
        } else {
          this.topHiddenFlag = false
        }
      }
    },
    // 向下点击
    handleImg (index) {
      console.log(this.trList[index].children)
      if (this.trList[index].children) {
        this.imgFlag = !this.imgFlag
        this.imgIndex = index
        console.log(this.imgIndex, this.imgFlag)
        this.hiddenFlag = !this.hiddenFlag
        if (this.imgFlag === false) {
          console.log(this.trList[index].children.length)
          this.trList.splice(index + 1, this.trList[index].children.length)
          console.log(this.trList)
          return
        }
        console.log(index)
        let index_ = index
        this.trList[index].children.map((item, index) => {
          this.trList.splice(index_ + 1, 0, item)
        })
        console.log(this.trList)
        this.imgIndex = index
      }
    },
    // 行选中高亮
    handleClick (index, item) {
      this.clickIindex = index
      console.log('选中', item)
      this.choisePagePath(this.trList[index].path)
    },
    // 搜索
    handleSearch () {
      console.log(this.pageName, this.value)
    }
  }
}
</script>
<style scoped>
.top_container {
  /* display: flex; */
  /* justify-content: space-around; */
  padding-bottom: 10px;
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
/* .top_container {
  padding-bottom: 10px;
}
.top_container {
  display: flex;
  justify-content: space-around;
} */
.top_left {
  display: flex;
  align-items: center;
  /* margin-left: 7px; */
}
.top_right {
  margin-left: 10px;
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
.isLeft {
  text-align: left !important;
}
.isLeft img {
  width: 40px;
}
.isLeft span {
  display: inline-block;
  vertical-align: top;
  margin-top: 9px;
  margin-left: 5px;
}
</style>
