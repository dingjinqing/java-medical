<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('magicMap.rightTitle')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <!--选择模板-->
        <div class="selectTemplate">
          <span :class="magic">{{$t('magicMap.selectTemplate')}}：</span>
          <div class="templateDiv">
            <div
              class="img_list"
              v-for="(item,index) in $t('magicMap.selectTemplateList')"
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
          v-if="$t('magicMap.selectTemplateList')[0].list[7].isChecked"
        >
          <span style="height:32px;line-height:32px;display:inline-block">{{$t('magicMap.cubeDensity')}}：</span>
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
            <span>{{$t('magicMap.layout')}}：</span>
            <div
              class="layoutDiv"
              style="display:flex;"
              v-if='nowTemplateClickIndex===0'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="(item.styleData[0].img_url?'background:url('+item.styleData[0].img_url+') no-repeat;background-size:cover;':'')+';height:152px;width:152px;line-height:152px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              ><span
                  v-if="!(item.styleData[0].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[0].size}}{{$t('magicMap.pixel')}}</span></div>
              <div
                @click="handleToClickLayout(index,1)"
                :style="(item.styleData[1].img_url?'background:url('+item.styleData[1].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')"
              ><span
                  v-if="!(item.styleData[1].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[1].size}}{{$t('magicMap.pixel')}}</span></div>
            </div>
            <div
              class="layoutDiv layoutSecond"
              style="display:flex;"
              v-if='nowTemplateClickIndex===1'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="(item.styleData[0].img_url?'background:url('+item.styleData[0].img_url+') no-repeat;background-size:cover;':'')+'height:102px;width:102px;line-height:102px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              ><span
                  v-if="!(item.styleData[0].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[0].size}}{{$t('magicMap.pixel')}}</span></div>
              <div
                @click="handleToClickLayout(index,1)"
                :style="(item.styleData[1].img_url?'background:url('+item.styleData[1].img_url+') no-repeat;background-size:cover;':'')+'height:102px;width:102px;line-height:102px;margin-left:-1px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc;':'')"
              ><span
                  v-if="!(item.styleData[1].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[1].size}}{{$t('magicMap.pixel')}}</span></div>
              <div
                @click="handleToClickLayout(index,2)"
                :style="(item.styleData[2].img_url?'background:url('+item.styleData[2].img_url+') no-repeat;background-size:cover;':'')+'height:102px;width:102px;line-height:102px;margin-left:-1px;'+(item.styleData[2].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[1].isChecked?'border-left:1px solid #6e86cc':'')"
              ><span
                  v-if="!(item.styleData[2].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[2].size}}{{$t('magicMap.pixel')}}</span></div>
            </div>
            <div
              class="layoutDiv layoutSecond"
              style="display:flex;"
              v-if='nowTemplateClickIndex===2'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="(item.styleData[0].img_url?'background:url('+item.styleData[0].img_url+') no-repeat;background-size:cover;':'')+'height:77px;width:77px;line-height:77px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              ><span
                  v-if="!(item.styleData[0].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[0].size}}{{$t('magicMap.pixel')}}</span></div>
              <div
                @click="handleToClickLayout(index,1)"
                :style="(item.styleData[1].img_url?'background:url('+item.styleData[1].img_url+') no-repeat;background-size:cover;':'')+'height:77px;width:77px;line-height:77px;margin-left:-1px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc;':'')"
              ><span
                  v-if="!(item.styleData[1].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[1].size}}{{$t('magicMap.pixel')}}</span></div>
              <div
                @click="handleToClickLayout(index,2)"
                :style="(item.styleData[2].img_url?'background:url('+item.styleData[2].img_url+') no-repeat;background-size:cover;':'')+'height:77px;width:77px;line-height:77px;margin-left:-1px;'+(item.styleData[2].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[1].isChecked?'border-left:1px solid #6e86cc':'')"
              ><span
                  v-if="!(item.styleData[2].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[2].size}}{{$t('magicMap.pixel')}}</span></div>
              <div
                @click="handleToClickLayout(index,3)"
                :style="(item.styleData[3].img_url?'background:url('+item.styleData[3].img_url+') no-repeat;background-size:cover;':'')+'height:77px;width:77px;line-height:77px;margin-left:-1px;'+(item.styleData[3].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[2].isChecked?'border-left:1px solid #6e86cc':'')"
              ><span
                  v-if="!(item.styleData[3].img_url)"
                  style="width:auto"
                >{{$t('magicMap.width')}}{{item.styleData[3].size}}{{$t('magicMap.pixel')}}</span></div>
            </div>

            <div
              class="layoutDiv layoutSecond"
              style="display:flex;flex-wrap:wrap"
              v-if='nowTemplateClickIndex===3'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="(item.styleData[0].img_url?'background:url('+item.styleData[0].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;margin-bottom:-1px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              >
                <p
                  v-if="!(item.styleData[0].img_url)"
                  style="height:12px"
                >{{item.styleData[0].size}}{{$t('magicMap.pixel')}}</p>
                <p
                  v-if="!(item.styleData[0].img_url)"
                  style="height:12px;margin-top:5px"
                >{{$t('magicMap.orEquivalent')}}</p>
              </div>
              <div
                @click="handleToClickLayout(index,1)"
                :style="(item.styleData[1].img_url?'background:url('+item.styleData[1].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;margin-left:-1px;margin-bottom:-1px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc;':'')"
              >
                <p
                  v-if="!(item.styleData[1].img_url)"
                  style="height:12px"
                >{{item.styleData[1].size}}{{$t('magicMap.pixel')}}</p>
                <p
                  v-if="!(item.styleData[1].img_url)"
                  style="height:12px;margin-top:5px"
                >{{$t('magicMap.orEquivalent')}}</p>
              </div>
              <div
                @click="handleToClickLayout(index,2)"
                :style="(item.styleData[2].img_url?'background:url('+item.styleData[2].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;'+(item.styleData[2].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-top:1px solid #6e86cc':'')"
              >
                <p
                  v-if="!(item.styleData[2].img_url)"
                  style="height:12px"
                >{{item.styleData[2].size}}{{$t('magicMap.pixel')}}</p>
                <p
                  v-if="!(item.styleData[2].img_url)"
                  style="height:12px;margin-top:5px"
                >{{$t('magicMap.orEquivalent')}}</p>
              </div>
              <div
                @click="handleToClickLayout(index,3)"
                :style="(item.styleData[3].img_url?'background:url('+item.styleData[3].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;margin-left:-1px;'+(item.styleData[3].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[1].isChecked?'border-top:1px solid #6e86cc':'')+(item.styleData[2].isChecked?'border-left:1px solid #6e86cc':'')"
              >
                <p
                  v-if="!(item.styleData[3].img_url)"
                  style="height:12px"
                >{{item.styleData[3].size}}{{$t('magicMap.pixel')}}</p>
                <p
                  v-if="!(item.styleData[3].img_url)"
                  style="height:12px;margin-top:5px"
                >{{$t('magicMap.orEquivalent')}}</p>
              </div>
            </div>

            <div
              class="layoutDiv layoutSecond"
              style="display:flex;flex-wrap:wrap;"
              v-if='nowTemplateClickIndex===4'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="(item.styleData[0].img_url?'background:url('+item.styleData[0].img_url+') no-repeat;background-size:cover;':'')+'height:302px;width:152px;line-height:302px;display:flex;flex-direction: column;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              >
                <p
                  v-if="!(item.styleData[0].img_url)"
                  style="height:12px"
                >{{item.styleData[0].size}}{{$t('magicMap.pixel')}}</p>
                <p
                  v-if="!(item.styleData[0].img_url)"
                  style="height:12px;margin-top:5px"
                >{{$t('magicMap.orEquivalent')}}</p>
              </div>
              <div style="border:none;">
                <div
                  @click="handleToClickLayout(index,1)"
                  :style="(item.styleData[1].img_url?'background:url('+item.styleData[1].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;margin-left:-1px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc;':'')"
                >
                  <p
                    v-if="!(item.styleData[1].img_url)"
                    style="height:12px"
                  >{{item.styleData[1].size}}{{$t('magicMap.pixel')}}</p>
                  <p
                    v-if="!(item.styleData[1].img_url)"
                    style="height:12px;margin-top:5px"
                  >{{$t('magicMap.orEquivalent')}}</p>
                </div>
                <div
                  @click="handleToClickLayout(index,2)"
                  :style="(item.styleData[2].img_url?'background:url('+item.styleData[2].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;margin-top:-1px;margin-left:-1px;'+(item.styleData[2].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[1].isChecked?'border-top:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc':'')"
                >
                  <p
                    v-if="!(item.styleData[2].img_url)"
                    style="height:12px"
                  >{{item.styleData[2].size}}{{$t('magicMap.pixel')}}</p>
                  <p
                    v-if="!(item.styleData[2].img_url)"
                    style="height:12px;margin-top:5px"
                  >{{$t('magicMap.orEquivalent')}}</p>
                </div>
              </div>

            </div>

            <div
              class="layoutDiv layoutSecond"
              style=""
              v-if='nowTemplateClickIndex===5'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="(item.styleData[0].img_url?'background:url('+item.styleData[0].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:302px;line-height:152px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              >
                <p
                  v-if="!(item.styleData[0].img_url)"
                  style="height:12px"
                >{{item.styleData[0].size}}{{$t('magicMap.pixel')}}</p>
                <p
                  v-if="!(item.styleData[0].img_url)"
                  style="height:12px;margin-top:5px"
                >{{$t('magicMap.orEquivalent')}}</p>
              </div>
              <div style="border:none;display:flex;background:#fff">
                <div
                  @click="handleToClickLayout(index,1)"
                  :style="(item.styleData[1].img_url?'background:url('+item.styleData[1].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;margin-top:-1px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-top:1px solid #6e86cc;':'')"
                >
                  <p
                    v-if="!(item.styleData[1].img_url)"
                    style="height:12px"
                  >{{item.styleData[1].size}}{{$t('magicMap.pixel')}}</p>
                  <p
                    v-if="!(item.styleData[1].img_url)"
                    style="height:12px;margin-top:5px"
                  >{{$t('magicMap.orEquivalent')}}</p>
                </div>
                <div
                  @click="handleToClickLayout(index,2)"
                  :style="(item.styleData[2].img_url?'background:url('+item.styleData[2].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;margin-top:-1px;margin-left:-1px;'+(item.styleData[2].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[1].isChecked?'border-left:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-top:1px solid #6e86cc':'')"
                >
                  <p
                    v-if="!(item.styleData[2].img_url)"
                    style="height:12px"
                  >{{item.styleData[2].size}}{{$t('magicMap.pixel')}}</p>
                  <p
                    v-if="!(item.styleData[2].img_url)"
                    style="height:12px;margin-top:5px"
                  >{{$t('magicMap.orEquivalent')}}</p>
                </div>
              </div>

            </div>

            <div
              class="layoutDiv layoutSecond"
              style="display:flex;"
              v-if='nowTemplateClickIndex===6'
            >
              <div
                @click="handleToClickLayout(index,0)"
                :style="(item.styleData[0].img_url?'background:url('+item.styleData[0].img_url+') no-repeat;background-size:cover;':'')+'height:302px;width:152px;line-height:302px;'+(item.styleData[0].isChecked?'border:1px solid #6e86cc;':'')"
              >
                <p
                  v-if="!(item.styleData[0].img_url)"
                  style="height:12px"
                >{{item.styleData[0].size}}{{$t('magicMap.pixel')}}</p>
                <p
                  v-if="!(item.styleData[0].img_url)"
                  style="height:12px;margin-top:5px"
                >{{$t('magicMap.orEquivalent')}}</p>
              </div>
              <div style="border:none;background:#fff">
                <div
                  @click="handleToClickLayout(index,1)"
                  :style="(item.styleData[1].img_url?'background:url('+item.styleData[1].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:152px;line-height:152px;margin-left:-1px;'+(item.styleData[1].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc;':'')"
                >
                  <p
                    v-if="!(item.styleData[1].img_url)"
                    style="height:12px"
                  >{{item.styleData[1].size}}{{$t('magicMap.pixel')}}</p>
                  <p
                    v-if="!(item.styleData[1].img_url)"
                    style="height:12px;margin-top:5px"
                  >{{$t('magicMap.orEquivalent')}}</p>
                </div>
                <div style="display:flex;border:none">
                  <div
                    @click="handleToClickLayout(index,2)"
                    :style="(item.styleData[2].img_url?'background:url('+item.styleData[2].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:77px;line-height:152px;margin-top:-1px;margin-left:-1px;display: flex;flex-direction: column;justify-content: center;'+(item.styleData[2].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[0].isChecked?'border-left:1px solid #6e86cc;':'')+(item.styleData[1].isChecked?'border-top:1px solid #6e86cc;':'')"
                  >
                    <p
                      v-if="!(item.styleData[2].img_url)"
                      style="height:12px;line-height: 12px"
                    >{{item.styleData[2].size}}{{$t('magicMap.pixel')}}</p>
                    <p
                      v-if="!(item.styleData[2].img_url)"
                      style="height:12px;margin-top:5px;line-height: 12px"
                    >{{$t('magicMap.orEquivalent')}}</p>
                  </div>
                  <div
                    @click="handleToClickLayout(index,3)"
                    :style="(item.styleData[3].img_url?'background:url('+item.styleData[3].img_url+') no-repeat;background-size:cover;':'')+'height:152px;width:77px;line-height:152px;margin-top:-1px;margin-left:-1px;display: flex;flex-direction: column;justify-content: center;'+(item.styleData[3].isChecked?'border:1px solid #6e86cc':'')+(item.styleData[1].isChecked?'border-top:1px solid #6e86cc':'')+(item.styleData[2].isChecked?'border-left:1px solid #6e86cc':'')"
                  >
                    <p
                      v-if="!(item.styleData[3].img_url)"
                      style="height:12px;line-height: 12px"
                    >{{item.styleData[3].size}}{{$t('magicMap.pixel')}}</p>
                    <p
                      v-if="!(item.styleData[3].img_url)"
                      style="height:12px;margin-top:5px;line-height: 12px"
                    >{{$t('magicMap.orEquivalent')}}</p>
                  </div>
                </div>

              </div>

            </div>
            <!--模板选择自定义后出现的模块区域-->
            <div
              class="layoutDiv layoutSecond"
              v-if='nowTemplateClickIndex===7'
            >
              <div
                style="border:none"
                :style="density==='0'?'width:308px':''"
              >
                <LayoutTable
                  @handleToGetTabelData='handleToGetTabelData'
                  @handleToGetLinkpathImgUrl='handleToGetLinkpathImgUrl'
                  :density='Number(density)'
                  :jumpLink='linkInput'
                  :imgUrl='imgUrl'
                  :customModulesBackData='customModulesBackData'
                />

              </div>
            </div>

            <!--end-->
          </div>

        </div>
        <div class="selectTemplate">
          <span></span>
          <div style="margin-top:10px;color:#666">
            {{$t('magicMap.tableTips')}}
          </div>

        </div>
        <!--选择图片-->
        <div class="selectTemplate addPicContainer">
          <span :class="magic">{{$t('magicMap.selectPictures')}}：</span>
          <div>
            <div
              class="addPic"
              @click="handlrToCallAddImgDialog()"
            >
              <div
                v-if="!imgUrl"
                class="iconZb"
                :style="'background:url('+$imageHost+'/image/admin/shop_beautify/add_decorete.png'+') center center / 65% 65% no-repeat'"
              >

              </div>
              <img
                v-if="imgUrl"
                :src="imgUrl"
              >
              <div
                class="bottomTips"
                v-if="imgUrl"
                style="background: rgba(0, 0, 0, 0.5)"
              >
                <span :style="'background:url('+$imageHost+'/image/admin/shop_beautify/add_decorete.png'+') center center / 65% 65% no-repeat'"></span>
                <i>{{$t('magicMap.modify')}}</i>
              </div>

            </div>
          </div>
          <span
            class="sizeTips"
            style="font-size:12px;color:#333;margin-left:10px;"
            v-if="sizeTipsText"
          >{{$t('magicMap.recommendeDimensions')}}：{{sizeTipsText}}{{$t('magicMap.pixelEquivalent')}}</span>
        </div>
        <!--图片跳转链接-->
        <div class="selectTemplate linkPathContainer">
          <span :class="magic">{{$t('magicMap.linkTitle')}}：</span>
          <div class="linkPathDiv">
            <el-input
              size="small"
              v-model="linkInput"
            ></el-input>
            <el-button
              size="small"
              @click="handleToCallSelectLink()"
            >{{$t('magicMap.selectLink')}}</el-button>
          </div>
        </div>
        <div style="color:#999;margin:10px 0 0 10px">
          {{$t('magicMap.bottomTips')}}
        </div>
      </div>
      <!--end-->
    </div>
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp='addImgTuneUp'
      @handleSelectImg='handleSelectImg'
    />
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath='selectLinkPath'
    />
  </div>
</template>
<script>
export default {
  components: {
    LayoutTable: () => import('./layoutTable'), // 自定义布局表格组件
    ImageDalog: () => import('@/components/admin/imageDalog'), // 选择图片弹窗
    SelectLinks: () => import('@/components/admin/selectLinks') //  选择链接弹窗
  },
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      // selectTemplateList: [// 选择模板数据
      //   {
      //     list: [
      //       {
      //         isChecked: true,
      //         text: '1行2个'
      //       },
      //       {
      //         isChecked: false,
      //         text: '1行3个'
      //       },
      //       {
      //         isChecked: false,
      //         text: '1行4个'
      //       },
      //       {
      //         isChecked: false,
      //         text: '2行2个'
      //       },
      //       {
      //         isChecked: false,
      //         text: '1左2右'
      //       },
      //       {
      //         isChecked: false,
      //         text: '1上2下'
      //       },
      //       {
      //         isChecked: false,
      //         text: '1左3右'
      //       },
      //       {
      //         isChecked: false,
      //         text: '自定义'
      //       }
      //     ]
      //   }
      // ],
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
              isChecked: false,
              'x': 1,
              'y': 1,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375',
              isChecked: true,
              'x': 1,
              'y': 3,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            }
          ]
        },
        {
          styleData: [
            {
              size: '250',
              isChecked: false,
              'x': 1,
              'y': 1,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '250',
              isChecked: false,
              'x': 1,
              'y': 3,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '250',
              isChecked: true,
              'x': 1,
              'y': 5,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            }
          ]
        },
        {
          styleData: [
            {
              size: '188',
              isChecked: false,
              'x': 1,
              'y': 1,
              'rows': 1,
              'cols': 1,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '188',
              isChecked: false,
              'x': 1,
              'y': 2,
              'rows': 1,
              'cols': 1,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '188',
              isChecked: false,
              'x': 1,
              'y': 3,
              'rows': 1,
              'cols': 1,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '188',
              isChecked: true,
              'x': 1,
              'y': 4,
              'rows': 1,
              'cols': 1,
              'img_url': '',
              'jump_link': ''
            }
          ]
        },
        {
          styleData: [
            {
              size: '375',
              isChecked: false,
              'x': 1,
              'y': 1,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375',
              isChecked: false,
              'x': 1,
              'y': 3,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375',
              isChecked: false,
              'x': 3,
              'y': 1,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375',
              isChecked: true,
              'x': 3,
              'y': 3,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            }
          ]
        },
        {
          styleData: [
            {
              size: '375X750',
              isChecked: false,
              'x': 1,
              'y': 1,
              'rows': 4,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375',
              isChecked: false,
              'x': 1,
              'y': 3,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375',
              isChecked: true,
              'x': 3,
              'y': 3,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            }
          ]
        },
        {
          styleData: [
            {
              size: '750X375',
              isChecked: false,
              'x': 1,
              'y': 1,
              'rows': 2,
              'cols': 4,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375',
              isChecked: false,
              'x': 3,
              'y': 1,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375',
              isChecked: true,
              'x': 3,
              'y': 3,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            }
          ]
        },
        {
          styleData: [
            {
              size: '375X750',
              isChecked: false,
              'x': 1,
              'y': 1,
              'rows': 4,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '375X375',
              isChecked: false,
              'x': 1,
              'y': 3,
              'rows': 2,
              'cols': 2,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '188X375',
              isChecked: false,
              'x': 3,
              'y': 3,
              'rows': 2,
              'cols': 1,
              'img_url': '',
              'jump_link': ''
            },
            {
              size: '188X375',
              isChecked: true,
              'x': 3,
              'y': 4,
              'rows': 2,
              'cols': 1,
              'img_url': '',
              'jump_link': ''
            }
          ]
        },
        { // 自定义
          timesNumber: [4, 5, 6, 7]
        }
      ],
      nowTemplateClickIndex: 0, // 当前选中的模板index
      nowLayutIndex: 1, //  当前选中的布局单元格
      linkInput: '', // 图片跳转链接input
      addImgTuneUp: false, // 添加图片弹窗flag
      imgUrl: null, // 当前显示的图片路径
      tuneUpSelectLink: false, // 选择链接弹窗调起flag
      firstInitNotEliminate: false, // 首次切换不清除当前模板数据
      customModulesBackData: null, // 自定义模块回显
      magic: 'magic', // 英文适配
      sizeTipsText: '', // 图片建议尺寸
      moduleSaveData: { // 模块保存数据

      }

    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        console.log(newData)
        console.log(this.moduleSaveData)
        this.$nextTick(() => {
          this.moduleSaveData = this.modulesData
          if (this.$route.query.pageId === -1) {
            console.log('sssss')
            this.handleToSaveDataImgInfo(0, true)
          } else {
            // 回显处理
            console.log(this.modulesData)
            this.handleToEchoDisplay(this.modulesData)
          }
        })

        console.log(this.nowTemplateClickIndex)
      },
      immediate: true
    },
    // 监听数据变化
    moduleSaveData: {
      handler (newData) {
        console.log(newData, '触发', this.nowTemplateClickIndex)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    },
    // 监听当前选中模板类型
    nowTemplateClickIndex (newData) {
      console.log(newData)
      // 处理保存数据中的data字段数据 即图片坐标路径信息
      let flag = this.firstInitNotEliminate

      this.handleToSaveDataImgInfo(newData, flag)
      // 处理尺寸提示文本

      console.log(newData)
      if (newData !== 7) {
        let len = this.layoutData[newData].styleData.length - 1
        this.sizeTipsText = this.layoutData[newData].styleData[len].size
      } else {
        this.sizeTipsText = ''
      }
    },
    // 监听图片跳转路径值变化
    linkInput (newData) {
      if (this.nowTemplateClickIndex !== 7) {
        console.log(this.nowTemplateClickIndex)
        this.handleToSaveDataImgInfo(this.nowTemplateClickIndex, false)
        this.layoutData[this.nowTemplateClickIndex].styleData[this.nowLayutIndex].jump_link = newData
      } else {
        this.layoutData[this.nowTemplateClickIndex].styleData[this.nowLayutIndex].jump_link = newData
      }
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始选择模板
    console.log(this.$route.query.pageId)

    console.log(this.nowTemplateClickIndex)
    if (this.nowTemplateClickIndex === 0) {
      this.sizeTipsText = '375'
      console.log(this.sizeTipsText)
    }

    // if (this.$route.query.pageId === -1) {
    //   this.handleToSaveDataImgInfo(0, true)
    // } else {
    //   // 回显处理
    //   this.handleToEchoDisplay(this.modulesData)
    // }
  },
  methods: {
    // 点击选择模板子项触发事件
    handleToClickTemplate (index, flag) {
      this.$t('magicMap.selectTemplateList')[0].list.forEach((item, index) => {
        item.isChecked = false
      })
      this.nowTemplateClickIndex = index
      // console.log(index, this.layoutData[index].styleData)
      if (index !== 7) {
        this.nowLayutIndex = this.layoutData[index].styleData.length - 1
      }

      console.log(this.nowTemplateClickIndex)
      this.$t('magicMap.selectTemplateList')[0].list[index].isChecked = true
      this.moduleSaveData.table_type = index + 1
      if (!flag) {
        this.moduleSaveData.data = {}
      }

      // this.handleToSaveDataImgInfo(index, true)
      // 处理保存数据中 table_size 字段
      this.handleToTableSize(index)
    },
    // 点击布局模块子项
    handleToClickLayout (flag, index) {
      this.layoutData[flag].styleData.forEach((item, index) => {
        item.isChecked = false
      })
      this.layoutData[flag].styleData[index].isChecked = true
      this.nowLayutIndex = index
      this.imgUrl = this.layoutData[flag].styleData[index].img_url
      console.log(this.layoutData[flag].styleData[index].size)
      this.sizeTipsText = this.layoutData[flag].styleData[index].size
      console.log(this.layoutData[flag].styleData[index].jump_link)
      this.linkInput = this.layoutData[flag].styleData[index].jump_link
    },
    // 调起添加图片弹窗
    handlrToCallAddImgDialog () {
      this.addImgTuneUp = !this.addImgTuneUp
    },
    // 图片弹窗选中图片后回传
    handleSelectImg (data) {
      console.log(data)
      this.imgUrl = data.imgUrl
      // this.nowTemplateClickIndex  this.nowLayutIndex
      this.layoutData[this.nowTemplateClickIndex].styleData[this.nowLayutIndex].img_url = data.imgUrl
      if (this.nowTemplateClickIndex !== 7) {
        this.handleToSaveDataImgInfo(this.nowTemplateClickIndex, false)
      }
    },
    // 调起选择链接弹窗
    handleToCallSelectLink () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗选中链接路径回传
    selectLinkPath (path) {
      console.log(path)
      this.linkInput = path
    },
    // 处理保存数据中的data字段数据 即图片坐标路径信息
    handleToSaveDataImgInfo (type, flag) {
      console.log(type, flag, '触发')
      let num = null
      switch (type) {
        case 0:
          num = 2
          break
        case 1:
          num = 3
          break
        case 2:
          num = 4
          break
        case 3:
          num = 4
          break
        case 4:
          num = 3
          break
        case 5:
          num = 3
          break
        case 6:
          num = 4
          break
      }
      console.log(this.layoutData)
      // 清空imgUrl 数据
      if (flag) {
        this.layoutData.forEach((item, index) => {
          console.log(item)
          if (index === 7) return
          item['styleData'].forEach((itemC, indexC) => {
            itemC.img_url = ''
            itemC.jump_link = ''
          })
        })
        this.imgUrl = null
        this.linkInput = null
      }
      this.firstInitNotEliminate = true
      // 重新填入数据
      let obj = {}
      for (let index = 0; index < num; index++) {
        obj[`block_${index}`] = {
          'name': `block_${index}`,
          'x': this.layoutData[type].styleData[index]['x'],
          'y': this.layoutData[type].styleData[index]['y'],
          'rows': this.layoutData[type].styleData[index]['rows'],
          'cols': this.layoutData[type].styleData[index]['cols'],
          'img_url': this.layoutData[type].styleData[index]['img_url'],
          'jump_link': this.layoutData[type].styleData[index]['jump_link']
        }
      }
      console.log(obj)
      this.modulesData.data = obj
    },
    // 自定义布局中当前高亮单元格的跳转链接路径和图片信息回传
    handleToGetLinkpathImgUrl (res) {
      this.linkInput = res.jump_link
      this.imgUrl = res.img_url
      // 计算建议图片尺寸
      let w = res.cols * 188
      let h = res.rows * 188
      this.sizeTipsText = w + 'x' + h
      console.log(res)
    },
    // 获取自定义布局操作回传的数据
    handleToGetTabelData ({ obj, isAllCheckFull }) {
      console.log(obj, isAllCheckFull)
      this.moduleSaveData.data = obj
      this.moduleSaveData.isAllCheckFull = isAllCheckFull
      this.handleToSelectLastTemplate()
    },
    // 处理保存数据中table_size字段
    handleToTableSize (index) {
      switch (index) {
        case 0:
          this.moduleSaveData.table_size = {
            rows: 2,
            cols: 4
          }
          break
        case 1:
          this.moduleSaveData.table_size = {
            rows: 1,
            cols: 3
          }
          console.log(this.moduleSaveData)
          break
        case 2:
          this.moduleSaveData.table_size = {
            rows: 1,
            cols: 4
          }
          break
        case 3:
          this.moduleSaveData.table_size = {
            rows: 4,
            cols: 4
          }
          break
        case 4:
          this.moduleSaveData.table_size = {
            rows: 4,
            cols: 4
          }
          break
        case 5:
          this.moduleSaveData.table_size = {
            rows: 4,
            cols: 4
          }
          break
        case 6:
          this.moduleSaveData.table_size = {
            rows: 4,
            cols: 4
          }
          break
        case 7:
          this.handleToSelectLastTemplate()
          break
      }
    },
    // 选中自定义布局处理table_size字段
    handleToSelectLastTemplate () {
      switch (Number(this.density)) {
        case 0:
          this.moduleSaveData.table_size = {
            rows: 4,
            cols: 4
          }
          break
        case 1:
          this.moduleSaveData.table_size = {
            rows: 5,
            cols: 5
          }
          break
        case 2:
          this.moduleSaveData.table_size = {
            rows: 6,
            cols: 6
          }
          break
        case 3:
          this.moduleSaveData.table_size = {
            rows: 7,
            cols: 7
          }
          break
      }
    },
    // 编辑回显处理
    handleToEchoDisplay (backData) {
      console.log(backData)
      if (backData.table_type !== 8) { // 当回显的模板不是最后一个模板
        console.log(backData.data)
        this.$nextTick(() => {
          Object.keys(backData.data).forEach((item, index) => {
            console.log((backData.table_type - 1), index, backData.data[item].img_url)
            this.layoutData[(backData.table_type - 1)].styleData[index].img_url = backData.data[item].img_url
            this.layoutData[(backData.table_type - 1)].styleData[index].jump_link = backData.data[item].jump_link
            console.log(this.layoutData[(backData.table_type - 1)].styleData[index].img_url)
          })
        })

        console.log((backData.table_type - 1))
        console.log(this.layoutData[3])
      } else {
        switch (backData.table_size.rows) {
          case 4:
            this.density = '0'
            break
          case 5:
            this.density = '1'
            break
          case 6:
            this.density = '2'
            break
          case 7:
            this.density = '3'
            break
        }
        this.customModulesBackData = backData.data
      }
      this.handleToClickTemplate((this.modulesData.table_type - 1), true)
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
      margin-bottom: 10px;
    }
    //模块私有样式
    .main {
      .selectTemplate {
        display: flex;
        // margin: 10px 0;
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
            p {
              font-size: 12px;
            }
          }
          span {
            width: 35%;
          }
        }
      }
      .density {
        margin-bottom: 10px;
        /deep/ .el-input {
          width: 50%;
        }
      }
      .addPicContainer {
        margin-top: 10px;
        span {
          height: 70px;
          line-height: 70px;
        }
        .sizeTips {
          width: 195px;
          word-break: break-all;
          line-height: 15px;
          text-align: left;
          display: flex;
          align-items: center;
        }
        .addPic {
          position: relative;
          cursor: pointer;
          .iconZb {
            width: 70px;
            height: 70px;
            border: 1px solid #e5e5e5;
          }
          img {
            max-width: 70px;
            max-height: 70px;
          }
          .bottomTips {
            width: 100%;
            height: 20px;
            position: absolute;
            bottom: 0;
            left: 0;

            span {
              display: inline-block;
              width: 100%;
              height: 100%;
              z-index: 100;
            }
            i {
              position: absolute;
              text-align: center;
              left: 14px;
              bottom: 15%;
              color: #fff;
            }
          }
        }
      }
      .linkPathContainer {
        margin-top: 10px;
        span {
          height: 32px;
          line-height: 32px;
        }
        .linkPathDiv {
          /deep/ .el-input {
            width: 200px;
          }
        }
      }
      .magic {
        line-height: 20px !important;
        text-align: center !important;
        padding-left: 23px !important;
      }
    }
    //end
  }
}
</style>
