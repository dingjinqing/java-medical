<template>
  <div>
    <div class="content">
      <div
        class="top_container"
        v-if="topHiddenFlag"
      >
        <div class="top_left">
          <div>{{topName}}：</div>
          <el-input
            v-model="pageName"
            :placeholder="(classificationFlag===2)?'请输入品牌名称':'请输入标签名称'"
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
            <td>{{topName}}</td>

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
            <td
              v-if="!topHiddenFlag"
              :style="'padding-left:'+item.level*30+'px'"
            >{{(classificationFlag===0)?item.catName:item.sortName}}<img
                v-if="item.hasChild?true:false"
                :src="imgIndex===index&&item.imgFlag?leftImg[1].img:leftImg[0].img"
                @click="handleImg(index)"
              ></td>
            <td
              v-else
              class="isLeft"
              :class="isCenterFlag?'tdCenter':''"
            >
              <!-- <img
                v-if="!isCenterFlag"
                :src="tdHiddenImg"
              > -->
              <span>{{(classificationFlag===2)?item.brandName:item.name}}</span>

            </td>
            <td class="tb_decorate_a">
              pages/searchs/search?{{(classificationFlag===0)?('cat_id='+item.catId):(classificationFlag===1)?('sort_id='+item.sortId):(classificationFlag===2?('brand_id='+item.id):('label_id='+item.id))}}
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
import { cateListApi } from '@/api/admin/selectLinksApi/selectLinksApi'
import { goodsBrandPageListApi } from '@/api/admin/goodsManage/brandManagement/brandManagement'
import { getGoodsLabelPageList } from '@/api/admin/goodsManage/goodsLabel/goodsLabel'
import { mapGetters, mapActions } from 'vuex'
export default {
  data () {
    return {
      trList: [],
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
      pageName: '',
      topHiddenFlag: false,
      tdHiddenImg: this.$imageHost + '/upload/7467397/image/20190507/crop_N7Fu7EaKRtaZri18.gif',
      classificationName: '分类名称',
      topName: '',
      isCenterFlag: '',
      catData: null,
      classificationFlag: null
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
      this.trList = []
      this.clickIindex = null
      console.log(newData)
      if (newData.levelIndex === 2) {
        switch (newData.index) {
          case 0: this.classificationName = '分类名称'
            this.classificationFlag = 0
            this.topHiddenFlag = false
            let params = {
              'needSysCategory': true
            }
            cateListApi(params).then(res => {
              console.log(res)
              if (res.error === 0) {
                this.catData = this._disposeGoodsSortAndCatData(res.content.goodsCategories, 'catId')
                console.log(this.catData)
                let arr = []
                this.catData.forEach((item, index) => {
                  if (item.level === 0) arr.push(item)
                })
                this.trList = arr
              }
            })
            break
          case 1: this.classificationName = '名称'
            this.classificationFlag = 1
            this.topHiddenFlag = false
            let needGoodsSortParams = {
              'needGoodsSort': true
            }
            cateListApi(needGoodsSortParams).then(res => {
              console.log(res)
              if (res.error === 0) {
                this.catData = this._disposeGoodsSortAndCatData(res.content.goodsSorts, 'sortId')
                console.log(this.catData)
                let arr = []
                this.catData.forEach((item, index) => {
                  if (item.level === 0) arr.push(item)
                })
                this.trList = arr
              }
            })
            break
          case 2: this.topName = '品牌名称'
            this.topHiddenFlag = true
            this.isCenterFlag = false
            this.classificationFlag = 2
            let needGoodsBrandParams = {
              'needGoodsBrand': true
            }
            cateListApi(needGoodsBrandParams).then(res => {
              console.log(res)
              if (res.error === 0) {
                this.trList = res.content.goodsBrands
              }
            })
            break
          case 3: this.topName = '标签名称'
            this.topHiddenFlag = true
            this.isCenterFlag = true
            this.classificationFlag = 3
            let goodsLabelsParams = {
              'needGoodsLabel': true
            }
            cateListApi(goodsLabelsParams).then(res => {
              console.log(res)
              if (res.error === 0) {
                this.trList = res.content.goodsLabels
              }
            })
            break
        }
      }
    },
    // 向下点击
    handleImg (index) {
      console.log(this.trList[index])
      if (this.trList[index]) {
        let arr = []
        if (this.classificationFlag === 0) {
          arr = this.catData.filter((itemC, indexC) => {
            return itemC.parentId === this.trList[index].catId
          })
        } else {
          arr = this.catData.filter((itemC, indexC) => {
            return itemC.parentId === this.trList[index].sortId
          })
        }

        console.log(arr)
        this.imgIndex = index
        this.trList[index].imgFlag = !this.trList[index].imgFlag
        if (this.trList[index].imgFlag) {
          arr.forEach((itemB, indexB) => {
            this.trList.splice((index + 1), 0, itemB)
          })
        } else {
          console.log('触发')
          this.trList.splice((index + 1), arr.length)
        }
      }
    },
    // 行选中高亮
    handleClick (index, item) {
      this.clickIindex = index
      console.log('选中', item)
      let path = ''
      switch (this.classificationFlag) {
        case 0: path = 'pages/searchs/search/' + 'cat_id=' + this.trList[index].catId
          break
        case 1: path = 'pages/searchs/search/' + 'sort_id=' + this.trList[index].sortId
          break
        case 2: path = 'pages/searchs/search/' + 'brand_id=' + this.trList[index].id
          break
        case 3: path = 'pages/searchs/search/' + 'label_id=' + this.trList[index].id
          break
      }
      this.choisePagePath(path)
    },
    // 搜索
    handleSearch () {
      if (this.classificationFlag === 2) {
        let brandParams = {
          brandName: this.pageName
        }
        goodsBrandPageListApi(brandParams).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.trList = res.content.dataList
          }
        })
      } else {
        let labelParams = {
          labelName: this.pageName
        }
        getGoodsLabelPageList(labelParams).then(res => {
          if (res.error === 0) {
            this.trList = res.content.dataList
          }
        })
      }
      console.log(this.pageName, this.value)
    },
    _disposeGoodsSortAndCatData (data, idName) {
      console.log(data)
      let retObj = {}
      for (let i = 0; i < data.length; i++) {
        let item = data[i]
        // 是否自身节点被创建过（子节点先遍历到了）
        let selfItem = retObj[item[idName]]
        if (selfItem === undefined) {
          // 未遍历到则初始化自己
          retObj[item[idName]] = { 'item': item, children: [] }
          selfItem = retObj[item[idName]]
        } else {
          // 已创建过，（因提前遍历了子节点而创建）
          selfItem.item = item
        }
        let parentItem = retObj[item.parentId]
        // 有父亲直接插入
        if (parentItem !== undefined) {
          parentItem.children.push(selfItem)
        } else {
          // 没有则创建临时父亲
          retObj[item.parentId] = { 'item': null, children: [selfItem] }
        }
      }
      let retArr = []
      if (data.length === 0) {
        return retArr
      }
      let rootArr = retObj['0'].children
      for (let i = 0; i < rootArr.length; i++) {
        let retItem = rootArr[i]
        retArr.push(retItem.item)
        if (retItem.children.length > 0) {
          rootArr.splice(i + 1, 0, ...(retItem.children))
        }
      }
      console.log(retArr)
      retArr.forEach((item, index) => {
        item.imgFlag = false
      })
      return retArr
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
  text-align: left;
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
.tdCenter {
  text-align: center;
}
</style>
