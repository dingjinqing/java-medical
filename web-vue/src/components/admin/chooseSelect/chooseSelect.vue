<template>
  <div class="chooseSelect">
    <!-- 请选择会员卡 -->
    <div>
      <div>
        <el-checkbox
          label="持有"
          v-model="onClickCard"
          @change="handleOnClickCardChange"
        ></el-checkbox>
        <el-select
          :disabled="!onClickCard"
          size="small"
          style="width:160px;margin-right:6px"
          v-model="cardValue"
          placeholder="请选择会员卡"
          @change="getIdList($event)"
        >
          <el-option
            label="请选择会员卡"
            value="请选择会员卡"
          ></el-option>
          <el-option
            v-for="item in cardList"
            :key="item.id"
            :label="item.cardName?item.cardName:item.value"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <span>会员卡人群</span>
      </div>
      <!-- 已选的 -->
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
    <!-- 请选择会员标签 -->
    <div style="margin:10px 0">
      <div>
        <div>
          <el-checkbox
            label="属于"
            v-model="onClickTag"
            @change="handleOnClickTagChange"
          ></el-checkbox>
          <el-select
            :disabled="!onClickTag"
            size="small"
            style="width:160px;margin-right:6px"
            v-model="tagValue"
            placeholder="请选择会员标签"
            @change="getTagIdList($event)"
          >
            <el-option
              label="请选择会员标签"
              value="请选择会员标签"
            ></el-option>
            <el-option
              v-for="item in tagList"
              :key="item.id"
              :label="item.cardName?item.cardName:item.value"
              :value="item.id"
            >
            </el-option>
          </el-select>
          <span>标签人群</span>
        </div>
        <!-- 已选的 -->
        <div
          class="selectedCard"
          v-if="tagIdsList.length>0"
        >
          <span>已选：</span>
          <div
            class="oneCardWraper"
            v-for="(item) in tagIdsList"
            :key="item.id"
          >
            <span class="oneCard">
              {{item.value}}
            </span>
            <el-image
              :src="urls.url4"
              class="oneCardDel"
              @click="handleDelTag(item.id)"
            ></el-image>
          </div>
        </div>
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
      onClickCard: false,
      onClickTag: false,

      cardList: [
        // { id: 4, value: 'Javascipt语言' }
      ],
      tagList: [

      ],
      cardIdsLists: [],
      cardIdsList: [], // 会员卡ID集合
      tagIdsList: [], // 标签ID集合
      tagIdLists: [],
      cardValue: `请选择会员卡`,
      tagValue: `请选择会员标签`,
      urls: {
        url4: `${this.$imageHost}/image/admin/icon_delete.png`
      }
    }
  },
  created () {
    this.initData()
  },
  mounted () {
  },

  methods: {
    /**
     * 勾选指定会员卡人群发生变化的时候
     */
    handleOnClickCardChange (val) {
      // this.onClickCard = !this.onClickCard
      this.$emit('chooseSelectVal', { tagIdList: this.tagIdLists, onClickTag: this.onClickTag, onClickCard: this.onClickCard, cardIdsList: this.cardIdsLists })
    },
    /**
     * 勾选指定标签人群变化的时候
     */
    handleOnClickTagChange (val) {
      // this.onClickTag = !this.onClickTag
      this.$emit('chooseSelectVal', { tagIdList: this.tagIdLists, onClickTag: this.onClickTag, onClickCard: this.onClickCard, cardIdsList: this.cardIdsLists })
    },
    // 初始化获取数据
    initData () {
      // 会员卡数据
      allCardApi().then(res => {
        const { error, content } = res
        if (error === 0) {
          this.cardList = content
        }
      }).catch(err => console.log(err))
      // 会员标签数据
      allTagApi().then(res => {
        const { error, content } = res
        if (error === 0) {
          this.tagList = content
        }
      }).catch(err => console.log(err))
    },
    getIdList (val) {
      if (val !== `请选择会员卡`) {
        const res = this.cardList.find((ele) => ele.id === val)
        this.cardIdsList.push(res)
        this.cardList = this.cardList.filter((item) => item.id !== val)
        this.cardValue = `请选择会员卡`
        // 派发cardIdsList
        let cardIdsLists = []
        this.cardIdsList.forEach(item => {
          cardIdsLists.push(item.id)
        })
        this.cardIdsLists = cardIdsLists
        this.$emit('chooseSelectVal', { tagIdList: this.tagIdLists, onClickTag: this.onClickTag, onClickCard: this.onClickCard, cardIdsList: this.cardIdsLists })
      }
    },
    getTagIdList (val) {
      if (val !== `请选择会员标签`) {
        const res = this.tagList.find((ele) => ele.id === val)
        this.tagIdsList.push(res)
        this.tagList = this.tagList.filter((item) => item.id !== val)
        this.tagValue = `请选择会员标签`
        // 派发tagIdList
        let tagIdLists = []
        this.tagIdsList.forEach(item => {
          tagIdLists.push(item.id)
        })
        this.tagIdLists = tagIdLists

        this.$emit('chooseSelectVal', { tagIdList: this.tagIdLists, onClickTag: this.onClickTag, onClickCard: this.onClickCard, cardIdsList: this.cardIdsLists })
      }
    },
    // 删除每个
    handleDelOne (id) {
      if (this.onClickCard === false) {
        return
      }
      const res = this.cardIdsList.find((item) => item.id === id)
      this.cardIdsList = this.cardIdsList.filter((item) => item.id !== id)
      this.cardList.push(res)
      // 派发id
      // 派发cardIdsList
      let cardIdsLists = []
      this.cardIdsList.forEach(item => {
        cardIdsLists.push(item.id)
      })
      this.cardIdsLists = cardIdsLists
      this.$emit('chooseSelectVal', { tagIdList: this.tagIdLists, onClickTag: this.onClickTag, onClickCard: this.onClickCard, cardIdsList: this.cardIdsLists })
    },

    handleDelTag (id) {
      if (this.onClickTag === false) {
        return
      }
      const res = this.tagIdsList.find((item) => item.id === id)
      this.tagIdsList = this.tagIdsList.filter((item) => item.id !== id)
      this.tagList.push(res)
      // 派发tagIdList
      let tagIdLists = []
      this.tagIdsList.forEach(item => {
        tagIdLists.push(item.id)
      })
      this.tagIdLists = tagIdLists

      this.$emit('chooseSelectVal', { tagIdList: this.tagIdLists, onClickTag: this.onClickTag, onClickCard: this.onClickCard, cardIdsList: this.cardIdsLists })
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
