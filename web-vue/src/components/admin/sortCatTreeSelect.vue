<template>
  <div style="display: inline-block;">
    <el-form
      ref="treeSelect"
      :inline="true"
      :label-width="treeStyle.labelWidth"
    >
      <el-form-item :label="selectLabelName+'：'">
        <el-select
          :style="treeStyle.selectStyle"
          :size="treeStyle.selectSize"
          v-model="selectedId"
          @change="selectChange"
        >

          <template v-if="treeType === 'sort'">
            <el-option
              :label="$t('allGoods.allGoodsHeaderData.chooseSort')"
              :value="null"
            />
            <el-option
              v-for="(item,index) in selectOptions"
              :label="filterGoodsInfo.needGoodsNum?item.sortName+' ('+item.goodsSumNum+')':item.sortName"
              :value="item.sortId"
              :key="index"
              :style="{paddingLeft: (item.level+1)*20+'px'}"
            />
          </template>
          <template v-else>
            <el-option
              :label="$t('allGoods.allGoodsHeaderData.chooseCategory')"
              :value="null"
            />
            <el-option
              v-for="(item,index) in selectOptions"
              :label="filterGoodsInfo.needGoodsNum?item.catName+' ('+item.goodsSumNum+')':item.catName"
              :value="item.catId"
              :key="index"
              :style="{paddingLeft: (item.level+1)*20+'px'}"
            />
          </template>
        </el-select>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// api导入
import { getGoodsFilterItem } from '@/api/admin/goodsManage/allGoods/allGoods'
// util导入
import { autoConvertDataFromArrayToTreeOrderArray } from '@/util/goodsSortCatUtil'
export default {
  name: 'sortCatTreeSelect',
  watch: {
    lang () {
      if (this.treeType === 'sort') {
        this.selectLabelName = this.$t('allGoods.allGoodsHeaderData.sort')
      } else {
        this.selectLabelName = this.$t('allGoods.allGoodsHeaderData.category')
      }
    }
  },
  props: {
    /* 是否需要查询商品数量相关信息 needGoodsNum：true是 false 否，isOnSale：1查询在售商品，0查询下架商品，
    isSaleOut: true查询售罄，false查询有货，selectType：1 以商品数量为单位查询数量，2 以规格为单位查询数量  */
    filterGoodsInfo: {
      type: Object,
      default: function () {
        return {
          needGoodsNum: true,
          isOnSale: 1,
          isSaleOut: false,
          selectType: 1
        }
      }
    },
    /* sort:平台分类树 cat:商家分类树 */
    treeType: {
      type: String,
      default: 'sort'
    },
    /* 树形结构的样式设置 */
    treeStyle: {
      type: Object,
      default: function () {
        return {
          selectSize: 'small',
          labelWidth: '120px',
          selectStyle: {
            width: '170px'
          }
        }
      }
    },
    /* mounted时自动调用loadData请求数据 */
    autoLoad: {
      type: Boolean,
      default: true
    },
    selectedId: {
      type: Number,
      default: () => null
    }
  },
  computed: {
    filterItemData: function () {
      let retData = {}

      if (this.filterGoodsInfo.needGoodsNum === true) {
        retData = {
          ...this.filterGoodsInfo
        }
      }

      if (this.treeType === 'sort') {
        retData.needGoodsSort = true
      } else {
        retData.needSysCategory = true
      }

      return retData
    }
  },
  data: function () {
    return {
      /* 下拉树左侧显示的文字 */
      selectLabelName: this.$t('allGoods.allGoodsHeaderData.chooseSort'),
      selectOptions: []
    }
  },
  methods: {
    selectChange () {
      this.$emit('update:selectedId', this.selectedId)
    },
    /* 获取选中项的文本内容 */
    getSelectedText () {
      if (this.selectedId === null) {
        return this.selectType === 'sort' ? this.$t('allGoods.allGoodsHeaderData.chooseSort') : this.$t('allGoods.allGoodsHeaderData.chooseCategory')
      }
      let itemId = 'sortId'
      let itemName = 'sortName'
      if (this.treeType === 'cat') {
        itemId = 'catId'
        itemName = 'catName'
      }

      for (let i = 0; i < this.selectOptions.length; i++) {
        if (this.selectOptions[i][itemId] === this.selectedId) {
          return this.selectOptions[i][itemName]
        }
      }
      return null
    },
    /* 设置选中项id */
    setSelectedId (itemId) {
      this.selectedId = itemId
      this.$emit('update:selectedId', this.selectedId)
    },
    loadData () {
      let itemId
      let itemName
      if (this.treeType === 'sort') {
        itemId = 'sortId'
        itemName = 'goodsSorts'
      } else {
        itemId = 'catId'
        itemName = 'goodsCategories'
      }
      let me = this
      return getGoodsFilterItem(this.filterItemData).then(res => {
        me.selectOptions = autoConvertDataFromArrayToTreeOrderArray(res.content[itemName], itemId)
      })
    },
    clearData () {
      this.selectedId = null
      this.selectChange()
    }
  },
  mounted () {
    this.langDefault()
    if (this.autoLoad) {
      this.loadData()
    }
  }
}
</script>

<style scoped>
</style>
