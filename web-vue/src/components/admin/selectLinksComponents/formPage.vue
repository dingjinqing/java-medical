<template>
  <div>
    <div
      class="content"
      v-if="page_one"
    >
      <table width='100%'>
        <thead>
          <tr>
            <td>{{$t('selectLinks.name')}}</td>
            <td>{{$t('selectLinks.businessStatus')}}</td>
            <td>{{$t('selectLinks.link')}}</td>
          </tr>
        </thead>
        <tbody v-if="tbodyFlag">
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index)"
          >
            <td>{{item.storeName}}</td>
            <td class="link">{{item.businessState===1?$t('selectLinks.inBusiness'):$t('selectLinks.goOutOfBusiness')}}</td>
            <td class="tb_decorate_a">
              pages/storeinfo/storeinfo?id={{item.storeId}}
            </td>
          </tr>
        </tbody>

      </table>
      <div
        class="noData"
        v-if="!tbodyFlag"
      >
        <img :src="noImg">
        <span>{{$t('selectLinks.noDataAvailable')}}</span>
      </div>
    </div>
    <div
      class="content_two"
      v-else
    >
      <table width='100%'>
        <thead>
          <tr>
            <td>{{$t('selectLinks.name')}}</td>

            <td>{{$t('selectLinks.link')}}</td>
          </tr>
        </thead>
        <tbody v-if="tbodyFlag">
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index)"
          >
            <td>{{item.pageName}}</td>

            <td class="tb_decorate_a">
              pages/form/form?page_id={{item.pageId}}
            </td>
          </tr>
        </tbody>

      </table>
      <div
        class="noData"
        v-if="!tbodyFlag"
      >
        <img :src="noImg">
        <span>{{$t('selectLinks.noDataAvailable')}}</span>
      </div>
    </div>
  </div>
</template>
<script>
import { formListApi, storeListApi } from '@/api/admin/selectLinksApi/selectLinksApi'
import { mapGetters, mapActions } from 'vuex'
export default {
  data () {
    return {
      trList: [],
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
      console.log(newData)
      // 初始化
      this.defaultData(newData)
    }
  },
  mounted () {
    // 初始化
    this.defaultData(this.selectlinksLevelOneBottom)
    // 初始化语言
    this.langDefault()
  },
  methods: {
    ...mapActions(['choisePagePath']),
    defaultData (newData) {
      console.log(newData)
      if (newData === 6) {
        this.page_one = false
        formListApi().then(res => {
          if (res.error === 0) {
            if (!res.content.length) {
              this.tbodyFlag = false
            } else {
              this.tbodyFlag = true
            }
            this.trList = res.content
          }
          console.log(res)
        })
      } else {
        this.page_one = true
        storeListApi().then(res => {
          if (res.error === 0) {
            if (!res.content.dataList.length) {
              this.tbodyFlag = false
            } else {
              this.tbodyFlag = true
            }
            this.trList = res.content.dataList
          }
          console.log(res)
        })
      }
      console.log(this.page_one)
    },
    // 行选中高亮
    handleClick (index) {
      this.clickIindex = index
      let path = `pages/storeinfo/storeinfo?id=${this.trList[index].storeId}`
      this.$emit('handleToGetDetailData', this.trList[index])
      this.choisePagePath(path)
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
