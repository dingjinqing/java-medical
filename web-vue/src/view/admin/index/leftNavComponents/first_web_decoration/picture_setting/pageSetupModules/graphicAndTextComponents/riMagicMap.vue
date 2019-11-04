<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>魔方多图模块</h2>
      <!--模块私有区域-->
      <div class="main">
        <!--选择模板-->
        <div class="selectTemplate">
          <span>选择模板：</span>
          <div class="templateDiv">
            <div
              class="img_list"
              v-for="(item,index) in selectTemplateList"
              :key="index"
            >
              <div
                class="commonDiv twice_line"
                :style="item.list[0].isChecked?'border-color:#5c81f4':''"
                @click="handleToClickTemplate(0)"
              >
                <div class="topDiv">
                  <div></div>
                  <div></div>
                </div>
                <p>{{item.list[0].text}}</p>
              </div>
              <div
                class="commonDiv third_line"
                :style="item.list[1].isChecked?'border-color:#5c81f4':''"
                @click="handleToClickTemplate(1)"
              >
                <div class="topDiv">
                  <div></div>
                  <div></div>
                  <div></div>
                </div>
                <p>{{item.list[1].text}}</p>
              </div>
              <div
                class="commonDiv fourth_line"
                :style="item.list[2].isChecked?'border-color:#5c81f4':''"
                @click="handleToClickTemplate(2)"
              >
                <div class="topDiv">
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                </div>
                <p>{{item.list[2].text}}</p>
              </div>
              <div
                class="commonDiv two_lines"
                :style="item.list[3].isChecked?'border-color:#5c81f4':''"
                @click="handleToClickTemplate(3)"
              >
                <div class="topDiv twoLinesDiv">
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                </div>
                <p>{{item.list[3].text}}</p>
              </div>
              <div
                class="commonDiv one_two_left"
                :style="item.list[4].isChecked?'border-color:#5c81f4':''"
                @click="handleToClickTemplate(4)"
              >
                <div class="topDiv oneTwoLeft">
                  <div class="special"></div>
                  <div class="oneTwoLeftLi">
                    <div></div>
                    <div></div>
                  </div>
                </div>
                <p>{{item.list[4].text}}</p>
              </div>
              <div
                class="commonDiv blue_border"
                :style="item.list[5].isChecked?'border-color:#5c81f4':''"
                @click="handleToClickTemplate(5)"
              >
                <div class="blueBorderTop"></div>
                <div class="blueBorderBottom">
                  <div></div>
                  <div></div>
                </div>
                <p>{{item.list[5].text}}</p>
              </div>
              <div
                class="commonDiv one_three"
                :style="item.list[6].isChecked?'border-color:#5c81f4':''"
                @click="handleToClickTemplate(6)"
              >
                <div style="display:flex">
                  <div style="height: 56px;width: 44%;margin-left: 4%;margin-top: 5px;background: #e9f8fd;"></div>
                  <div class="special_one">
                    <div style="width:94%;height:25px;"></div>
                    <div style="margin-right:6%"></div>
                    <div></div>
                  </div>
                </div>
                <p>{{item.list[6].text}}</p>
              </div>
              <div
                class="commonDiv own_define"
                :style="item.list[7].isChecked?'border-color:#5c81f4':''"
                @click="handleToClickTemplate(7)"
              >
                <div class="lastLi">
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                  <div></div>
                </div>
                <p>{{item.list[7].text}}</p>
              </div>
            </div>
          </div>
        </div>
        <!--选择模板中的自定义显示的魔方密度隐藏模块-->
        <div
          class="selectTemplate density"
          v-if="selectTemplateList[0].list[7].isChecked"
        >
          <span style="height:32px;line-height:32px;display:inline-block">魔方密度：</span>
          <div>
            <el-select
              v-model="density"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in densitySelectData"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <!--布局-->
        <div
          class="selectTemplate"
          v-for="(item,index) in layoutData"
          :key="index"
        >
          <div
            class="layout"
            v-if='nowTemplateClickIndex===index'
          >
            <span>布局：</span>
            <div
              class="layoutDiv"
              style="display:flex;"
              v-if='nowTemplateClickIndex===0'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="'height:152px;width:152px;line-height:152px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              >宽度{{item.styleData[0].size}}像素</div>
              <div
                @click="handleToClickLayout(index,1)"
                :style="'height:152px;width:152px;line-height:152px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')"
              >宽度{{item.styleData[1].size}}像素</div>
            </div>
            <div
              class="layoutDiv layoutSecond"
              style="display:flex;"
              v-if='nowTemplateClickIndex===1'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="'height:102px;width:102px;line-height:102px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              >宽度{{item.styleData[0].size}}像素</div>
              <div
                @click="handleToClickLayout(index,1)"
                :style="'height:102px;width:102px;line-height:102px;margin-left:-1px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc;':'')"
              >宽度{{item.styleData[1].size}}像素</div>
              <div
                @click="handleToClickLayout(index,2)"
                :style="'height:102px;width:102px;line-height:102px;margin-left:-1px;'+(item.styleData[2].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[1].isChecked?'border-left:1px solid #6e86cc':'')"
              >宽度{{item.styleData[2].size}}像素</div>
            </div>
            <div
              class="layoutDiv layoutSecond"
              style="display:flex;"
              v-if='nowTemplateClickIndex===2'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="'height:152px;width:152px;line-height:152px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              >宽度{{item.styleData[0].size}}像素</div>
              <div
                @click="handleToClickLayout(index,1)"
                :style="'height:152px;width:152px;line-height:152px;margin-left:-1px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc;':'')"
              >宽度{{item.styleData[1].size}}像素</div>
              <div
                @click="handleToClickLayout(index,2)"
                :style="'height:152px;width:152px;line-height:152px;margin-left:-1px;'+(item.styleData[2].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[1].isChecked?'border-left:1px solid #6e86cc':'')"
              >宽度{{item.styleData[2].size}}像素</div>
              <div
                @click="handleToClickLayout(index,3)"
                :style="'height:152px;width:152px;line-height:152px;margin-left:-1px;'+(item.styleData[3].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[2].isChecked?'border-left:1px solid #6e86cc':'')"
              >宽度{{item.styleData[3].size}}像素</div>
            </div>

          </div>
        </div>
      </div>
      <!--end-->
    </div>
  </div>
</template>
<script>
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      selectTemplateList: [// 选择模板数据
        {
          list: [
            {
              isChecked: true,
              text: '1行2个'
            },
            {
              isChecked: false,
              text: '1行3个'
            },
            {
              isChecked: false,
              text: '1行4个'
            },
            {
              isChecked: false,
              text: '2行2个'
            },
            {
              isChecked: false,
              text: '1左2右'
            },
            {
              isChecked: false,
              text: '1上2下'
            },
            {
              isChecked: false,
              text: '1左3右'
            },
            {
              isChecked: false,
              text: '自定义'
            }
          ]
        }
      ],
      density: '0', // 魔方密度select value数据
      densitySelectData: [{ // 魔方密度select数据
        value: '0',
        label: '4X4'
      }, {
        value: '1',
        label: '5X5'
      }, {
        value: '2',
        label: '6X6'
      }, {
        value: '3',
        label: '7X7'
      }],
      layoutData: [ // 布局子项数据
        {
          styleData: [
            {
              size: '375',
              isChecked: false
            },
            {
              size: '375',
              isChecked: true
            }
          ]
        },
        {
          styleData: [
            {
              size: '250',
              isChecked: false
            },
            {
              size: '250',
              isChecked: false
            },
            {
              size: '250',
              isChecked: true
            }
          ]
        },
        {
          styleData: [
            {
              size: '188',
              isChecked: false
            },
            {
              size: '188',
              isChecked: false
            },
            {
              size: '188',
              isChecked: true
            },
            {
              size: '188',
              isChecked: true
            }
          ]
        },
        {
          styleData: [
            {
              size: '375',
              isChecked: false
            },
            {
              size: '375',
              isChecked: false
            },
            {
              size: '375',
              isChecked: true
            },
            {
              size: '375',
              isChecked: true
            }
          ]
        }
      ],
      nowTemplateClickIndex: 0, // 当前选中的模板index
      moduleSaveData: { // 模块保存数据

      }

    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        this.moduleSaveData = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    data: {
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
    // 点击选择模板子项触发事件
    handleToClickTemplate (index) {
      this.selectTemplateList[0].list.forEach((item, index) => {
        item.isChecked = false
      })
      this.nowTemplateClickIndex = index
      this.selectTemplateList[0].list[index].isChecked = true
    },
    // 点击布局模块子项
    handleToClickLayout (flag, index) {
      this.layoutData[flag].styleData.forEach((item, index) => {
        item.isChecked = false
      })
      this.layoutData[flag].styleData[index].isChecked = true
    }
  }
}
</script>
<style lang="scss" scoped>
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    //模块私有样式
    .main {
      .selectTemplate {
        display: flex;
        margin: 10px 0;
        span {
          width: 26%;
          display: inline-block;
          text-align: right;
        }
        .templateDiv {
          width: 72%;
          display: flex;
          flex-wrap: wrap;
          .img_list {
            display: flex;
            flex-wrap: wrap;
            flex: 1;
            .commonDiv {
              border: 1px solid #e5e5e5;
              background: #fff;
              margin-right: 3%;
              height: 90px;
              margin-bottom: 10px;
              width: 30%;
              display: flex;
              flex-direction: column;
            }
            p {
              width: 100%;
              text-align: center;
              margin-bottom: 0;
              flex: 1;
              margin-top: 10px;
            }
            .twice_line,
            .third_line,
            .fourth_line,
            .two_lines {
              .topDiv {
                display: flex;
                div {
                  background: #e9f8fd;
                  width: 44%;
                  margin-left: 4%;
                  height: 40px;
                  margin-top: 15px;
                }
              }
            }
            .third_line {
              .topDiv {
                div {
                  width: 28%;
                }
              }
            }
            .fourth_line {
              .topDiv {
                div {
                  width: 20%;
                }
              }
            }
            .twoLinesDiv {
              flex-wrap: wrap;
              div {
                width: 44%;
                margin-left: 4%;
                height: 25px !important;
                margin-top: 5px !important;
              }
            }
            .oneTwoLeft {
              display: flex;
              .special {
                height: 56px;
                width: 44px;
                background: #e9f8fd;
                margin-left: 4%;
                margin-top: 5px;
              }
              .oneTwoLeftLi {
                flex: 1;
                div {
                  width: 90%;
                  margin-left: 6%;
                  height: 25px;
                  margin-top: 5px;
                  background: #e9f8fd;
                }
              }
            }
            .blue_border {
              .blueBorderTop {
                width: 92%;
                margin-left: 4%;
                height: 25px;
                margin-top: 5px;
                background: #e9f8fd;
              }
              .blueBorderBottom {
                height: 25px;
                display: flex;
                div {
                  height: 25px;
                  width: 44%;
                  background: #e9f8fd;
                  margin-left: 4%;
                  margin-top: 5px;
                }
              }
            }
            .one_three {
              display: flex;
              .special_one {
                display: flex;
                flex-wrap: wrap;
                flex: 1;
                margin-left: 4%;
                margin-top: 5px;
                div {
                  width: 44%;
                  height: 25px;
                  background: #e9f8fd !important;
                }
              }
            }
            .own_define {
              .lastLi {
                display: flex;
                flex-wrap: wrap;
                width: 100%;
                height: auto;
                div {
                  width: 20%;
                  margin-left: 4%;
                  height: 15px;
                  margin-top: 5px;
                  background: #e9f8fd !important;
                }
              }
            }
          }
        }
        .layout {
          text-align: center;
          display: flex;
          width: 100%;
          .layoutDiv {
            font-size: 12px;
            width: 100%;
            div {
              background-color: #eaf0ff;
              color: #838cd1;
              border: 1px solid #cedbff;
              cursor: pointer;
            }
          }
          span {
            width: 35%;
          }
          .layoutSecond {
          }
        }
      }
      .density {
        /deep/ .el-input {
          width: 50%;
        }
      }
    }
    //end
  }
}
</style>
