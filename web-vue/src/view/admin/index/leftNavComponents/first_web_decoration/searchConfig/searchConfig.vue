<template>
  <div class="searchConfig">
    <div class="searchConfig_main">
      <!-- 左侧内容区域 -->
      <div class="leftContent">
        <!-- 商品搜索图片部分 -->
        <div class="top">
          <img
            src="http://mpdevimg2.weipubao.cn/image/admin/search_config_left1.png"
            alt=""
          >
        </div>

        <section class="searchContent">
          <!-- 搜索历史 -->
          <div class="searchHistory">
            <div class="searchTitle">搜索历史</div>
            <div class="searchItem clearfix">
              <div>热水壶保温壶</div>
              <div>瓜子花生八宝粥</div>
              <div>啤酒饮料矿泉水</div>
              <div>春季时尚汇女装</div>
              <div>2019年新款女装红色丽人春季精选</div>
            </div>
          </div>
          <!-- 热门搜索 -->
          <div class="searchHistory hotSsearch">
            <div class="searchTitle">热门搜索</div>
            <div class="searchItem clearfix">
              <div>热水壶保温壶</div>
              <div>瓜子花生八宝粥</div>
              <div>啤酒饮料矿泉水</div>
              <div>春季时尚汇女装</div>
              <div>2019年新款女装红色丽人春季精选</div>
            </div>
          </div>
        </section>

        <!-- 猜你喜欢 -->
        <div class="like">
          <img
            src="http://mpdevimg2.weipubao.cn/image/admin/ucconfig_bg.jpg"
            alt=""
          >
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="rightContent">

        <!-- 默认搜索区域 -->
        <section class="infoArea">
          <div class="infoTitle">默认搜索</div>
          <div class="infoContent">
            <el-radio-group v-model="setting">
              <div class="noSetting">
                <el-radio label="1">不设置<span>(前端显示"请输入商品关键字")</span></el-radio>
                <el-radio label="2">全部商品</el-radio>
              </div>
              <div class="custom">
                <el-radio>自定义<el-input
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
          <div class="infoTitle">默认历史</div>
          <div class="infoContent">
            <el-switch
              v-model="history"
              active-color="#13ce66"
              inactive-color="#ff4949"
            >
              {{this.hotWord === true ? '已开启' : '已关闭'}}
            </el-switch>
            <span class="ifUse">启用后，可方便用户搜索</span>
          </div>
        </section>

        <!-- 搜索热词 -->
        <section class="infoArea">
          <div class="infoTitle">搜索热词</div>
          <div class="infoContent">
            <el-switch
              v-model="hotWord"
              active-color="#13ce66"
              inactive-color="#ff4949"
            >
              <span>{{hotWord === true ? '已开启' : '已关闭'}}</span>
            </el-switch>
            <span class="ifUse">启用后，引导用户购买热搜商品 最多可添加十条</span>
            <!-- 添加热词按钮 -->
            <el-button
              type="primary"
              size="small"
              class="btn"
              @click="handleBtn"
            >+&nbsp;添加热词</el-button>
            <div>
              <span>热词1： </span>
              <el-input
                size="small"
                style="width: 180px"
              ></el-input>
            </div>
            <div
              v-for="item in hotWordList"
              :key="item.title"
            >
              <span>{{item.title}}：</span>
              <el-input
                size="small"
                style="width: 180px"
              ></el-input>
              <span @click="handleDelete()">删除</span>
            </div>

          </div>
        </section>

        <div class="remarks">注：”猜你喜欢“请在”商品管理“-”商品推荐“中配置</div>
        <div class="recommon">
          <span>新建商品推荐</span>
          |
          <span>商品管理推荐</span>
        </div>
      </div>
    </div>

    <div class="save">
      <el-button
        size="small"
        type="primary"
        @click="submitData"
      >保存
      </el-button>
    </div>
  </div>
</template>

<script>
import { querySearchConfig } from '@/api/admin/smallProgramManagement/searchConfig/searchConfig'

export default {
  created () {
    this.fetchData()
  },
  mounted () {
    this.langDefault()
  },
  data () {
    return {
      setting: '1',
      history: true,
      hotWord: true,
      hotWordList: [
        { title: '热词2' },
        { title: '热词3' },
        { title: '热词4' },
        { title: '热词5' },
        { title: '热词6' }
      ]
    }
  },
  methods: {
    fetchData () {
      querySearchConfig().then(res => {
        console.log(res)
      })
    },
    handleBtn () {
      console.log(1111)
    },
    handleDelete () {

    },
    submitData () {
      console.log(222)
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
            margin-left: 30px;
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
    width: 88%;
    height: 50px;
    background: #f8f8fa;
    margin-left: -20px;
  }
}
</style>
