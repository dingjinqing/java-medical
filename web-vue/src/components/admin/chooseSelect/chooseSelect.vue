<template>
  <div class="chooseSelect">
    <div>
      <el-checkbox v-model="onClickCard">{{text.title}}</el-checkbox>
      <el-select
        size="small"
        style="width:160px;margin-right:6px"
        v-model="cardValue"
        :placeholder="text.placeholder"
        @change="getIdList($event)"
      >
        <el-option
          :label="text.placeholder"
          :value="text.placeholder"
        ></el-option>
        <el-option
          v-for="item in cardList"
          :key="item.id"
          :label="item.cardName?item.cardName:item.value"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <span>{{text.txt}}</span>
    </div>
    <!-- 已选的会员卡 -->
    <div
      class="selectedCard"
      v-if="cardIdsList.length>0"
    >
      <span>已选：</span>
      <div
        class="oneCardWraper"
        v-for="(item) in cardIdsList"
        :key="item.id"
      >
        <span class="oneCard">
          {{item.cardName?item.cardName:item.value}}
        </span>
        <el-image
          :src="urls.url4"
          class="oneCardDel"
          @click="handleDelOne(item.id)"
        ></el-image>
      </div>
    </div>
  </div>
</template>

<script>
// 引入api请求
import { allCardApi, allTagApi } from '@/api/admin/marketManage/messagePush'
export default {
  name: `chooseSelect`,
  props: {
    text: {
      type: Object
    }
  },
  data () {
    return {
      cardList: [
        // { id: 4, value: 'Javascipt语言' }
      ],
      cardIdsList: [],
      cardValue: this.text.placeholder,
      urls: {
        url4: `${this.$imageHost}/image/admin/icon_delete.png`

      }
    }
  },
  created () {
    this.initData()
  },
  watch: {

  },
  methods: {
    // cardList赋值
    formatCardList (content) {
      // console.log(this.text.placeholder)
      switch (this.text.placeholder) {
        case `请选择会员卡`:
          this.cardList = content
          break
        case `请选择会员标签`:
          this.cardList = content
          break
        default:
          break
      }
    },
    // 初始化获取数据
    initData () {
      allCardApi().then(res => {
        // console.log(res)
        const { error, content } = res
        if (error === 0) {
          // this.cardList = content
          this.formatCardList(content)
        }
      }).catch(err => console.log(err))

      allTagApi().then(res => {
        // console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.formatCardList(content)
        }
      }).catch(err => console.log(err))
    },
    getIdList (val) {
      console.log(val)
      if (val !== this.text.placeholder) {
        const res = this.cardList.find((ele) => ele.id === val)
        // console.log(res)
        this.cardIdsList.push(res)
        // console.log(val)
        // console.log(this.cardList)
        this.cardList = this.cardList.filter((item) => item.id !== val)
        // console.log(this.cardList)
        this.cardValue = this.text.placeholder
      }
    },

    // 删除每个
    handleDelOne (id) {
      console.log(id)
      const res = this.cardIdsList.find((item) => item.id === id)
      this.cardIdsList = this.cardIdsList.filter((item) => item.id !== id)
      this.cardList.push(res)
    }
  }
}
</script>

<style lang="scss" scoped>
.selectedCard {
  border: 1px solid #eee;
  width: 382px;
  min-height: 56px;
  margin: 10px 0;
  display: flex;
  flex-wrap: wrap;
  .oneCardWraper {
    position: relative;
    margin: 2px 6px;
    .oneCard {
      padding: 0 10px;
      min-width: 70px;
      // margin: 10px;
      // display: flex;
      text-align: center;
      line-height: 24px;
      background-color: #fff;
      height: 24px;
      border: 1px solid #ccc;
      display: inline-block;
    }
    .oneCardDel {
      position: absolute;
      right: -5px;
      top: -5px;
      cursor: pointer;
    }
  }
}
</style>
