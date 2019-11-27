<template>
  <div class="searchConfig">
    <div class="searchConfig_main">

      <!-- 左侧内容区域 -->
      <div class="leftContent">
        <!-- 商品搜索图片部分 -->
        <div class="top">
          <img
            :src="$imageHost+'/image/admin/search_config_left1.png'"
            alt=""
          >
        </div>

        <section class="searchContent">
          <!-- 搜索历史 -->
          <div
            class="searchHistory"
            v-if="isOpenHistory === true"
          >
            <div class="searchTitle">{{$t('searchConfig.searchHistory.title')}}</div>
            <div class="searchItem clearfix">
              <div>{{$t('searchConfig.searchHistory.content1')}}</div>
              <div>{{$t('searchConfig.searchHistory.content2')}}</div>
              <div>{{$t('searchConfig.searchHistory.content3')}}</div>
              <div>{{$t('searchConfig.searchHistory.content4')}}</div>
              <div>{{$t('searchConfig.searchHistory.content5')}}</div>
            </div>
          </div>
          <!-- 热门搜索 -->
          <div
            class="searchHistory hotSsearch"
            v-if="isOpenHotWords === true"
          >
            <div class="searchTitle">{{$t('searchConfig.hotSearch.title')}}</div>
            <div class="searchItem clearfix">
              <div
                v-for="(item,index) in hotWordsList"
                :key="index"
              >{{item.length > 0 ? item : '' }}</div>
            </div>
          </div>
        </section>

        <!-- 猜你喜欢 -->
        <div class="like">
          <img
            :src="$imageHost+'/image/admin/ucconfig_bg.jpg'"
            alt=""
          >
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="rightContent">

        <!-- 默认搜索区域 -->
        <section class="infoArea">
          <div class="infoTitle">{{$t('searchConfig.defaultSearch.title')}}</div>
          <div class="infoContent">
            <el-radio-group v-model="titleAction">
              <div class="noSetting">
                <el-radio :label="1">{{$t('searchConfig.defaultSearch.setting')}}
                  <span>{{$t('searchConfig.defaultSearch.show')}}</span>
                </el-radio>
                <el-radio :label="2">{{$t('searchConfig.defaultSearch.allGoods')}}</el-radio>
              </div>
              <div class="custom">
                <el-radio :label="3">{{$t('searchConfig.defaultSearch.custom')}}<el-input
                    v-model="titleCustom"
                    size="small"
                    style="width:180px;margin-left:12px"
                  ></el-input>
                </el-radio>
              </div>
            </el-radio-group>
          </div>
        </section>

        <!-- 搜索历史 -->
        <section class="infoArea">
          <div class="infoTitle">{{$t('searchConfig.rightSearchHistory.title')}}</div>
          <div class="infoContent">
            <el-switch
              v-model="isOpenHistory"
              active-color="#f7931e"
            >
            </el-switch>
            <span :style="{'margin-left': '20px', 'color': '#606266'}">
              {{this.isOpenHistory === true ? $t('searchConfig.rightSearchHistory.on') : $t('searchConfig.rightSearchHistory.off')}}
            </span>
            <span class="ifUse">{{$t('searchConfig.rightSearchHistory.tips')}}</span>
          </div>
        </section>

        <!-- 搜索热词 -->
        <section class="infoArea">
          <div class="infoTitle">{{$t('searchConfig.searchHotWords.title')}}</div>
          <div class="infoContent">
            <el-switch
              v-model="isOpenHotWords"
              active-color="#f7931e"
            >
            </el-switch>
            <span :style="{'margin-left': '20px', 'color': '#606266'}">
              {{this.isOpenHotWords === true ? $t('searchConfig.searchHotWords.on') : $t('searchConfig.searchHotWords.off')}}
            </span>
            <span class="ifUse">{{$t('searchConfig.searchHotWords.tips')}}</span>
            <!-- 添加热词按钮 -->
            <el-button
              type="primary"
              size="small"
              class="btn"
              @click="handleBtn()"
            >+&nbsp;{{$t('searchConfig.searchHotWords.addWords')}}</el-button>
            <div
              v-for="(item, index) in hotWordsList"
              :key="index"
              :style="{'margin-top': '10px'}"
            >
              <span :style="{'margin-right': '10px'}">
                {{$t('searchConfig.searchHotWords.hotWords')}}{{index+1}}：
              </span>
              <el-input
                v-model="hotWordsList[index]"
                size="small"
                style="width: 180px"
              ></el-input>
              <span
                v-if="index > 0"
                @click="handleDelete(index)"
                style="color:#5a8bff;margin-left:15px;cursor:pointer"
              >{{$t('searchConfig.searchHotWords.delete')}}</span>
            </div>

          </div>
        </section>

        <div class="remarks">{{$t('searchConfig.remarks')}}</div>
        <div class="recommon">
          <span @click="handleNewGoods">{{$t('searchConfig.recommon.newGoods')}}</span>
          |
          <span @click="handleGoodsManage">{{$t('searchConfig.recommon.goodsManage')}}</span>
        </div>
      </div>
    </div>

    <div class="save">
      <el-button
        size="small"
        type="primary"
        @click="submitData"
      >{{$t('searchConfig.save')}}
      </el-button>
    </div>
  </div>
</template>

<script>
import { querySearchConfig, modifySearchConfig } from '@/api/admin/smallProgramManagement/searchConfig/searchConfig'

export default {
  created () {
    this.fetchData()
  },
  mounted () {
    this.langDefault()
  },
  data () {
    return {
      titleAction: 1,
      isOpenHistory: true,
      isOpenHotWords: false,
      titleCustom: '',
      hotWords: [],
      hotWordsList: [],
      itemList: []
    }
  },
  methods: {
    fetchData () {
      querySearchConfig().then(res => {
        console.log(res)
        let content = { ...res.content }
        this.titleAction = content.title_action
        this.titleCustom = content.title_custom
        this.isOpenHistory = Boolean(content.is_open_history)
        console.log(this.isOpenHistory)
        this.isOpenHotWords = Boolean(content.is_open_hot_words)
        console.log(this.isOpenHotWords)
        this.hotWordsList = content.hot_words
        // console.log(this.titleAction, this.title_custom, this.is_open_history, this.is_open_hot_words, this.hot_words)
      })
    },
    // 添加热词事件
    handleBtn () {
      if (this.hotWordsList.length > 9) {
        this.$message.warning(this.$t('searchConfig.maxHotWords'))
      } else {
        let obj = ''
        this.hotWordsList.push(obj)
        console.log(this.hotWordsList)
      }
    },
    // 删除热词
    handleDelete (index) {
      console.log(this.hotWordsList)
      this.hotWordsList.splice(index, 1)
      console.log(index)
    },
    handleNewGoods () {
      this.$router.push({
        path: '/admin/home/main/goodsManage/goodsRecommend/add'
      })
    },
    handleGoodsManage () {
      this.$router.push({
        path: '/admin/home/main/goodsManage/goodsRecommend'
      })
    },
    // 保存按钮 -> 提交数据
    submitData () {
      if (this.titleAction !== 3) {
        this.titleCustom = ''
      }
      if (this.titleAction === 3 && this.isEmpty(this.titleCustom)) {
        this.$message.error(this.$t('searchConfig.titleCustomError'))
        return
      }
      let params = {
        title_action: this.titleAction,
        title_custom: this.titleCustom,
        is_open_history: Number(this.isOpenHistory),
        is_open_hot_words: Number(this.isOpenHotWords),
        hot_words: this.hotWordsList
      }
      modifySearchConfig(JSON.stringify(params)).then(res => {
        if (res.error === 0) {
          this.fetchData()
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      }).catch(err => console.log(err))
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.searchConfig {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  position: relative;
  .searchConfig_main {
    display: flex;
    justify-content: center;
    background: #fff;
    padding: 30px 0 96px;
    .leftContent {
      width: 382px;
      min-height: 630px;
      border: 1px solid #ccc;
      background: #f5f5f5;
      .top > img {
        width: 380px;
      }
      .searchContent {
        width: 100%;
        margin: 0px auto 10px;
        background: #fff;
        padding: 10px;
        .searchHistory {
          .searchTitle {
            font-size: 12px;
            font-weight: bold;
          }
          .searchItem {
            div {
              padding: 8px;
              background: #f5f5f5;
              font-size: 10px;
              float: left;
              margin-right: 10px;
              margin-top: 10px;
            }
          }
          .clearfix:after {
            display: block;
            content: "";
            clear: both;
          }
        }
        .hotSsearch {
          margin-top: 10px;
        }
      }
      .like > img {
        width: 380px;
      }
    }
    .rightContent {
      position: relative;
      width: 480px;
      margin-left: 30px;
      font-size: 14px;
      .infoArea {
        width: 100%;
        border: 1px solid #efefef;
        margin-bottom: 10px;
        .infoTitle {
          padding: 0 12px;
          line-height: 40px;
          color: #000;
          background: #f8f8f8;
        }
        .infoContent {
          border-top: 1px solid #efefef;
          padding: 20px 10px;
          .noSetting {
            margin-bottom: 20px;
          }
          .ifUse {
            color: #999;
            margin-left: 20px;
          }
          .btn {
            margin: 20px 0;
          }
        }
      }
      .remarks {
        margin-bottom: 10px;
        color: #999;
      }
      .recommon {
        color: #5a8bff;
        cursor: pointer;
      }
    }
  }
  .save {
    border-top: 1px solid #f2f2f2;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    bottom: 0;
    z-index: 2;
    right: 20px;
    left: 160px;
    height: 50px;
    background: #f8f8fa;
    // margin-left: -20px;
  }
}
</style>
