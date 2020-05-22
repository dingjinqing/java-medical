<template>
  <div class="batchSetup">
    <el-dialog
      :title="$t('allGoods.batchDialog.dialogTitle')"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="53%"
      :before-close="handleToCloseDialog"
    >
      <div class="batchSetupMain">
        <ul class="left">
          <li
            v-for="(item,index) in $t('allGoods.batchDialog.liData')"
            :key="index"
            @click="handleClickLeftLi(index)"
          >
            <span :class="nowIndex===index?'hover':''">{{item}}</span>
          </li>
        </ul>
        <div class="right">
          <div class="br_title">
            {{$t('allGoods.batchDialog.isCheck')}}：{{checkGoodsData.length}}{{$t('allGoods.bottomOptions.commodity')}}<i>{{$t('allGoods.batchDialog.rightTitleTip')}}</i>
          </div>
          <!--右侧动态内容-->
          <div class="dynamic">
            <!--商品价格-->
            <div
              v-if="nowIndex===0"
              class="price"
            >
              <div
                @click="handleToClickPriceTip()"
                class="title"
                style="cursor:pointer"
              >{{$t('allGoods.batchDialog.goodsPriceTips1')}}：<i>{{$t('allGoods.batchDialog.goodsPriceTips2')}}</i><i>{{$t('allGoods.batchDialog.goodsPriceTips3')}}</i><i>{{$t('allGoods.batchDialog.goodsPriceTips4')}}</i><span>{{$t('allGoods.batchDialog.goodsPriceTips5')}}</span></div>
              <div class="bottom">
                <el-table
                  class="version-manage-table"
                  header-row-class-name="tableClss"
                  :data="goodsPriceShowData"
                  border
                  style="width: 100%"
                >
                  <el-table-column
                    prop="goodsName"
                    :label="$t('allGoods.batchDialog.commoditySpecification')"
                    align="center"
                    width="160"
                  >
                    <template slot-scope="scope">
                      <div class="goodsDiv">
                        <div class="item_goods_img">
                          <img :src="scope.row.goodsImg">
                        </div>
                        <div class="goods_message">
                          <div class="item_goods_name">{{scope.row.goodsName}}</div>
                          <div class="item_goods_desc">
                            <span>{{scope.row.prdDesc}}</span>
                          </div>
                        </div>

                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="shopPrice"
                    :label="$t('allGoods.batchDialog.originalPrice')"
                    align="center"
                  >

                  </el-table-column>
                  <el-table-column
                    :label="$t('allGoods.batchDialog.discount')"
                    align="center"
                    prop="discountInputVal"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            v-model="scope.row.discountInputVal"
                            size="small"
                            @blur="handleToBlur(scope,0)"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;{{$t('allGoods.batchDialog.fracture')}}
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="goodsName"
                    :label="$t('allGoods.batchDialog.goodsPriceTips3')"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            v-model="scope.row.priceIncreaseVal"
                            size="small"
                            @blur="handleToBlur(scope,1)"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;{{$t('allGoods.batchDialog.element')}}
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="goodsName"
                    :label="$t('allGoods.batchDialog.goodsPriceTips4')"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            @blur="handleToBlur(scope,2)"
                            v-model="scope.row.priceRevisionVal"
                            size="small"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;{{$t('allGoods.batchDialog.element')}}
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>

            </div>
            <!--商家分类-->
            <div
              v-if="nowIndex===1"
              class="classification"
            >
              <sortCatTreeSelect
                ref="sortTree"
                :filterGoodsInfo="initSortCatParams"
                treeType="sort"
                :selectedId.sync="sortId"
                :labelStyle="true"
              />
            </div>
            <!--运费模板-->
            <div
              v-if="nowIndex===2"
              class="template"
            >
              <div class="title">
                <div class="name">{{$t('allGoods.batchDialog.freightTemplate')}}：</div>
                <el-select
                  v-model="templateValue"
                  size="small"
                  @change="handleToQueryTemOption"
                >
                  <el-option
                    v-for="item in templateOptions"
                    :key="item.deliverTemplateId"
                    :label="item.templateName"
                    :value="item.deliverTemplateId"
                  >
                  </el-option>
                </el-select>
                <div class="rightOptions">
                  <span @click="handleToClickTemplate(0)">{{$t('allGoods.batchDialog.refresh')}}</span>|<span
                    @click="handleToClickTemplate(1)"
                    style="width:80px"
                  >{{$t('allGoods.batchDialog.newTemplate')}}</span>|<span
                    @click="handleToClickTemplate(2)"
                    style="width:80px"
                  >{{$t('allGoods.batchDialog.templateManagement')}}</span>
                </div>
              </div>
              <!--选中运费模板后显示模块-->
              <div
                class="hiddenTemplate"
                v-if="templateValue!==null&&JSON.stringify(templateShowContentData)!=='{}'"
              >
                <div class="content">
                  <div class="top">
                    <span>{{templateValue===0?$t('allGoods.batchDialog.unifiedFreight'):templateShowContentData&&templateShowContentData.limitParam.limit_deliver_area===1?$t('allGoods.batchDialog.distributableArea'):`${$t('allGoods.batchDialog.regionsOfTheCountry')}：${templateShowContentData.limitParam.first_num} ${$t('allGoods.batchDialog.inPiece')}${templateShowContentData.limitParam.first_fee}${$t('allGoods.batchDialog.perIncrease')}${templateShowContentData.limitParam.continue_num}件，加${templateShowContentData.limitParam.continue_fee}${$t('allGoods.batchDialog.element')}`}}</span>
                    <span
                      @click="handelToTurnTemDetail(templateShowContentData)"
                      class="toDetail"
                    >{{$t('allGoods.batchDialog.viewDetails')}}</span>
                  </div>
                  <div
                    class="bottomContent"
                    v-if="templateValue!==0&&templateShowContentData.areaParam.length"
                  >
                    <div class="title">{{$t('allGoods.batchDialog.specify')}}:</div>
                    <div
                      v-for="(itemP,indexP) in templateShowContentData.areaParam"
                      :key="indexP"
                    >
                      <div class="hiddencontent"><i
                          v-for="(item,index) in itemP.area_text"
                          :key="index"
                        >{{item}}</i>:{{itemP.first_num}}{{$t('allGoods.batchDialog.inPiece')}}{{itemP.first_fee}}{{$t('allGoods.batchDialog.perIncrease')}}{{itemP.continue_num}}{{$t('allGoods.batchDialog.partsPlus')}}{{itemP.continue_fee}}{{$t('allGoods.batchDialog.element')}}</div>
                    </div>

                  </div>
                  <div
                    class="bottomContent"
                    v-if="templateValue!==0&&templateShowContentData.has_fee_0_condition===1&&templateShowContentData.feeConditionParam.length"
                  >
                    <div class="title">{{$t('allGoods.batchDialog.freightDelivery')}}</div>
                    <div
                      v-for="(itemP,indexP) in templateShowContentData.feeConditionParam"
                      :key="indexP"
                    >
                      <div class="hiddencontent"><i
                          v-for="(item,index) in itemP.area_text"
                          :key="index"
                        >{{item}}</i>:{{itemP.fee_0_condition===1?`${$t('allGoods.batchDialog.full')}${itemP.fee_0_con1_num}${$t('allGoods.batchDialog.packageMail')}`:itemP.fee_0_condition===2?`${$t('allGoods.batchDialog.full')}${itemP.fee_0_con2_num}${$t('allGoods.batchDialog.yuanBaoPost')}`:`${$t('allGoods.batchDialog.full')}${itemP.fee_0_con3_num}${$t('allGoods.batchDialog.piece')}，${itemP.fee_0_con3_fee}${$t('allGoods.batchDialog.yuanBaoPost')}`}}</div>
                    </div>
                  </div>
                </div>

              </div>
            </div>
            <!--限购数量-->
            <div
              v-if="nowIndex===3"
              class="limitNum"
            >
              <div class="content">
                <span>{{$t('allGoods.batchDialog.minimumLimit')}}</span>
                <el-input
                  v-model="MinPurchaseInputVal"
                  size="small"
                  onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                ></el-input>
                <i>{{$t('allGoods.batchDialog.purchaseQuantity')}}</i>
              </div>
              <div
                class="content"
                style="margin-top:10px"
              >
                <span>{{$t('allGoods.batchDialog.maximum')}}</span>
                <el-input
                  v-model="MaxPurchaseInputVal"
                  size="small"
                  onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                ></el-input>
                <i>{{$t('allGoods.batchDialog.purchaseQuantity')}}</i>
              </div>
            </div>
            <!--上架时间-->
            <div
              v-if="nowIndex===4"
              class="onSaleTime"
            >
              <div class="onSaleTimeLeft">
                {{$t('allGoods.batchDialog.lowerFrames')}}
              </div>
              <div class="onSaleTimeRight">
                <el-radio
                  v-model="onSaleRadio"
                  label="1"
                >{{$t('allGoods.batchDialog.noModification')}}</el-radio>
                <el-radio
                  v-model="onSaleRadio"
                  label="2"
                >{{$t('allGoods.batchDialog.marketImmediately')}}</el-radio>
                <div class="custom">
                  <el-radio
                    v-model="onSaleRadio"
                    label="3"
                  >{{$t('allGoods.batchDialog.customListing')}}</el-radio>
                  <div class="tips">
                    <el-date-picker
                      size="small"
                      v-model="customTime"
                      type="datetime"
                      :placeholder="$t('allGoods.batchDialog.selectDateTime')"
                      default-time="12:00:00"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                    >
                    </el-date-picker>
                    <span style="margin-left:10px">{{$t('allGoods.batchDialog.timeOfListing')}}</span>
                  </div>

                </div>

                <el-radio
                  v-model="onSaleRadio"
                  label="4"
                >{{$t('allGoods.batchDialog.putIntoWarehouse')}}</el-radio>
              </div>
            </div>
            <!--商品详情-->
            <div
              v-if="nowIndex===5 || nowIndex===7"
              class="commodityDetails"
            >
              <div
                class="temPosition"
                v-if="nowIndex===5"
              >
                <span>{{$t('allGoods.batchDialog.templateLocation')}}</span>
                <el-radio
                  v-model="goodsRadio"
                  label="1"
                >{{$t('allGoods.batchDialog.noModification')}}</el-radio>
                <el-radio
                  v-model="goodsRadio"
                  label="2"
                >{{$t('allGoods.batchDialog.customContentOn')}}</el-radio>
                <el-radio
                  v-model="goodsRadio"
                  label="3"
                >{{$t('allGoods.batchDialog.detailsArOn')}}</el-radio>
              </div>
              <div class="customContent">
                <div class="customTitle">
                  {{nowIndex===5?$t('allGoods.batchDialog.customContent'):$t('allGoods.batchDialog.brand')}}
                </div>
                <div class="customMiddle">
                  <div style="color:#999">{{$t('allGoods.batchDialog.brandTips')}}</div>
                  <div class="customFooter">
                    <el-button
                      size="small"
                      @click="handleToClickCustom(0)"
                    >{{nowIndex===5?$t('allGoods.batchDialog.selectTemplate'):$t('allGoods.batchDialog.chooseBrand')}}</el-button>
                    <div class="rightContent">
                      <span @click="handleToClickCustom(1)">{{$t('allGoods.batchDialog.refresh')}}</span>|<span
                        @click="handleToClickCustom(2)"
                        style="width:80px"
                      >{{nowIndex===5?$t('allGoods.batchDialog.addTemplate'):$t('allGoods.batchDialog.newBrand')}}</span>|<span
                        @click="handleToClickCustom(3)"
                        style="width:80px"
                      >{{nowIndex===5?$t('allGoods.batchDialog.managementTemplate'):$t('allGoods.batchDialog.managementBrand')}}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!--商品详情和商品品牌公共表格-->
            <div v-if="(nowIndex===5&&isShowCommonTableFive) || (nowIndex===7&&isShowCommonTableSeven)">
              <div class="tatle">
                <div class="tableLeft">
                  <span>{{nowIndex===5?$t('allGoods.batchDialog.pageName'):$t('allGoods.batchDialog.brandName')}}</span>
                  <el-input
                    v-if="nowIndex===5"
                    size="small"
                    v-model="tableInput[0]"
                    :placeholder="$t('allGoods.batchDialog.enterPageName')"
                  ></el-input>
                  <el-input
                    v-if="nowIndex===7"
                    size="small"
                    v-model="tableInput[1]"
                    :placeholder="$t('allGoods.batchDialog.enterBrandName')"
                  ></el-input>
                </div>
                <div class="tableRight">
                  <span>{{nowIndex===5?$t('allGoods.batchDialog.pageClassification'):$t('allGoods.batchDialog.brandClassification')}}</span>
                  <el-select
                    size="small"
                    v-if="nowIndex===5"
                    v-model="commonTableValue[0]"
                  >
                    <el-option
                      v-for="item in commonTableOptionsFive"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                  <el-select
                    size="small"
                    v-if="nowIndex===7"
                    v-model="commonTableValue[1]"
                  >
                    <el-option
                      v-for="item in commonTableOptionsSeven"
                      :key="item.classifyId"
                      :label="item.classifyName"
                      :value="item.classifyId"
                    >
                    </el-option>
                  </el-select>
                  <el-button
                    size="small"
                    type="primary"
                    @click="handleToClickSearch()"
                  >{{$t('allGoods.batchDialog.search')}}</el-button>
                </div>
              </div>
              <!--表格-->
              <div
                class="commonTable"
                v-if="nowIndex===5"
                :key="1"
              >
                <el-table
                  class="version-manage-table"
                  header-row-class-name="tableClss"
                  :data="commonTableDataFive"
                  border
                  style="width: 100%"
                  @current-change="handleCurrentChangeFive"
                >
                  <el-table-column
                    prop="pageName"
                    :label="$t('allGoods.batchDialog.pageName')"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="createTime"
                    :label="$t('allGoods.batchDialog.creationTime')"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="pageType"
                    :label="$t('allGoods.batchDialog.homePage')"
                    align="center"
                  >
                    <template slot-scope="scope">
                      {{scope.row.pageType===1?$t('allGoods.batchDialog.yes'):$t('allGoods.batchDialog.no')}}
                    </template>
                  </el-table-column>
                </el-table>
                <div class="footer">
                  <el-pagination
                    @current-change="handleDetailCurrentChange"
                    :current-page.sync="pageDataFive.currentPage"
                    :page-size="20"
                    layout="prev, pager, next, jumper"
                    :total="pageDataFive.totalRows"
                  >
                  </el-pagination>
                </div>
              </div>
              <div
                class="commonTable"
                v-if="nowIndex===7"
                :key="2"
              >
                <el-table
                  class="version-manage-table"
                  header-row-class-name="tableClss"
                  :data="commonTableDataSeven"
                  border
                  highlight-current-row
                  style="width: 100%"
                  @current-change="handleCurrentChangeSeven"
                >
                  <el-table-column
                    prop="brandName"
                    :label="$t('allGoods.batchDialog.brandName')"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="classifyName"
                    :label="$t('allGoods.batchDialog.brandClassification')"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="createTime"
                    :label="$t('allGoods.batchDialog.creationTime')"
                    align="center"
                  >
                  </el-table-column>
                </el-table>
                <div class="footer">
                  <el-pagination
                    @current-change="handleCurrentChange"
                    :current-page.sync="pageDataSeven.currentPage"
                    :page-size="20"
                    layout="prev, pager, next, jumper"
                    :total="pageDataSeven.totalRows"
                  >
                  </el-pagination>
                </div>
              </div>

            </div>
            <!--商品标签-->
            <div
              class="commodityDetails"
              v-if="nowIndex===6"
            >
              <div class="customContent">
                <div class="customMiddle">
                  <div
                    class="customFooter"
                    style="margin-top:0"
                  >
                    <div class="label">{{$t('allGoods.batchDialog.goodsLabel')}}</div>
                    <el-select
                      v-model="labelValue"
                      size="small"
                      @change="handleToSelect"
                    >
                      <el-option
                        v-for="item in labelOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      >
                      </el-option>
                    </el-select>
                    <div
                      class="rightContent"
                      style="width:auto"
                    >
                      <span @click="handleToClickCustom(1)">{{$t('allGoods.batchDialog.refresh')}}</span>|<span
                        @click="handleToClickCustom(2)"
                        style="padding:0 10px;white-space:nowrap;display: inline-block;width:auto"
                      >{{$t('allGoods.batchDialog.newProductLabel')}}</span>|<span
                        @click="handleToClickCustom(3)"
                        style="padding:0 10px;white-space:nowrap;display: inline-block;width:auto"
                      >{{$t('allGoods.batchDialog.manageProductLabels')}}</span>
                    </div>
                  </div>
                </div>
              </div>
              <!--选中商品标签后显示模块-->
              <div
                class="showLabelContent"
                v-if="labelValueCheckArr.length"
              >
                <div class="labelLeft">
                  {{$t('allGoods.batchDialog.selected')}}
                </div>
                <div class="labelRight">
                  <div
                    v-for="(item,index) in labelValueCheckArr"
                    :key="index"
                    class="list"
                  >{{item.name}}<img
                      @click="handleToClickDel(index,item)"
                      :src="$imageHost+'/image/admin/icon_delete.png'"
                      class="label-delete"
                    ></div>

                </div>
              </div>
            </div>
            <!--会员专享-->
            <div
              v-if="nowIndex===8"
              class="membershipExclusive"
            >
              <div class="membershipTop">
                <div style="margin-bottom:20px">{{$t('allGoods.batchDialog.memberExclusive')}}</div>
                <div>
                  <el-radio
                    v-model="membershipTopRadio"
                    label="1"
                  >{{$t('allGoods.batchDialog.memberExclusiveTips')}}</el-radio>
                  <el-radio
                    v-model="membershipTopRadio"
                    label="2"
                  >{{$t('allGoods.batchDialog.memberExclusiveTipsBottom')}}</el-radio>
                </div>
              </div>
              <div
                class="membershipBottom"
                v-if="membershipTopRadio==='1'"
              >
                <div class="membershipTitle">
                  <span>{{$t('allGoods.batchDialog.membershipCard')}}</span>
                  <i>{{$t('allGoods.batchDialog.membershipCardTips')}}</i>
                </div>
                <div class="membershipMiddle">
                  <el-select
                    v-model="membershipValue"
                    size="small"
                    @change="handleToMemberSelect"
                  >
                    <el-option
                      v-for="item in membershipOptions"
                      :key="item.id"
                      :label="item.cardName"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                  <div
                    class="rightContent"
                    style="width:auto;color:#5a8bff"
                  >
                    <span
                      @click="handleToClickCustom(1)"
                      style="padding:0 10px;cursor:pointer"
                    >{{$t('allGoods.batchDialog.refresh')}}</span>|<span
                      @click="handleToClickCustom(2)"
                      style="cursor:pointer;padding:0 10px;white-space:nowrap;display: inline-block;width:auto"
                    >{{$t('allGoods.batchDialog.newMembershipCard')}}</span>|<span
                      @click="handleToClickCustom(3)"
                      style="cursor:pointer;padding:0 10px;white-space:nowrap;display: inline-block;width:auto"
                    >{{$t('allGoods.batchDialog.manageMembership')}}</span>
                  </div>
                </div>
                <div style="color:#999">{{$t('allGoods.batchDialog.manageMembershipTip')}}</div>
                <!--选中会员卡后显示模块-->
                <div
                  class="showLabelContent"
                  v-if="membershipValueCheckArr.length"
                >
                  <div class="labelLeft">
                    {{$t('allGoods.batchDialog.selected')}}
                  </div>
                  <div class="labelRight">
                    <div
                      v-for="(item,index) in membershipValueCheckArr"
                      :key="index"
                      class="list"
                    >{{item.cardName}}<img
                        @click="handleToClickMemberDel(index,item)"
                        :src="$imageHost+'/image/admin/icon_delete.png'"
                        class="label-delete"
                      ></div>

                  </div>
                </div>
              </div>
            </div>
            <!--发货地-->
            <div
              v-if="nowIndex===9"
              class="placeOfDelivery"
            >
              <div class="placeOfDeliveryContent">
                <div class="name">{{$t('allGoods.batchDialog.placeOfShipment')}}</div>
                <el-input
                  v-model="placeOfDeliveryInput"
                  :maxlength="15"
                  :placeholder="$t('allGoods.batchDialog.upWords')"
                  size="small"
                ></el-input>
              </div>
            </div>
          </div>
          <!--右侧动态内容end-->
        </div>
      </div>
      <!--编辑离开提示弹窗-->
      <el-dialog
        width="30%"
        :title="$t('allGoods.batchDialog.remind')"
        :visible.sync="innerVisible"
        append-to-body
      >
        <span style="width:100%;text-align:center;display: block">{{$t('allGoods.batchDialog.exitEditing')}}</span>
        <div slot="footer">
          <el-button @click="innerVisible = false">{{$t('allGoods.batchDialog.cancel')}}</el-button>
          <el-button @click="handleToCloseInnerDialog()">{{$t('allGoods.batchDialog.sure')}}</el-button>
        </div>
      </el-dialog>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleToSave()"
        >{{$t('allGoods.batchDialog.save')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getGoodsInfosByGoodIds, getGoodsFilterItem, batchOperateGoods } from '@/api/admin/goodsManage/allGoods/allGoods'
import { deliverTemplateNameListApi, getDeliverTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
import { brandAllGetRequest, classificationSelectRequest } from '@/api/admin/brandManagement'
import { getPageCate, pageList } from '@/api/admin/decoration/pageSet'
import { getExclusiveCardList } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
export default {
  components: {
    sortCatTreeSelect: () => import('@/components/admin/sortCatTreeSelect') // 商家分类
  },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    checkGoodsData: { // 已选中的商品
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      liData: ['商品价格', '商家分类', '运费模板', '限购数量', '上架时间', '商品详情', '商品标签', '商品品牌', '会员专属', '发货地'],
      nowIndex: 0,
      innerVisible: false, // 内层
      judgeIsEdit: false, // 判断商品价格是否被修改过
      initSortCatParams: {
        needGoodsNum: false
      },
      sortId: null,
      templateOptions: [],
      templateValue: null, // 运费模板selectVal
      MinPurchaseInputVal: null, // 最小限购数量
      MaxPurchaseInputVal: null, // 最大限购数量
      onSaleRadio: '1', // 上架时间radio
      customTime: '', // 自定义上架售卖时间
      goodsRadio: '1', // 商品详情头部radio
      isShowCommonTableFive: false, // 商品详情和商品品牌公共表格显示
      tableInput: ['', null], // 公共表格表头input值
      commonTableValue: [null, null], // 商品详情和商品品牌公共selectVal
      commonTableOptionsFive: [], // 商品详情和商品品牌公共selectOptions
      commonTableOptionsSeven: [], // 商品详情和商品品牌公共selectOptions
      commonTableDataFive: [], // 公共表格数据
      commonTableDataSeven: [], // 商品品牌列表数据
      isShowCommonTableSeven: false, // 控制显示
      labelValue: null,
      labelOptions: [],
      labelValueCheckArr: [], // 商品标签下拉框选中集合
      membershipTopRadio: '', // 会员专享radio
      membershipValue: null,
      membershipOptions: [],
      membershipValueCheckArr: [], // 会员专享下拉框选中集合
      placeOfDeliveryInput: '', // 发货地
      goodsPriceShowData: [], // 商品价格模块渲染数据
      templateShowContentData: {}, // 选中模板后显示的数据
      turnType: 0, // 跳转模板详情type值
      pageDataFive: {
        currentPage: 1,
        total: 0
      },
      pageDataSeven: {
        currentPage: 1,
        total: 0
      },
      tableClassifyClickRow: null, // 模板表格选中
      tableBrandClickRow: null, // 商品品牌表格选中值
      nowOptionIndex: -1, // 当前操作的行下标
      nowInputValFlag: ''// 当前操作的格子flag
    }
  },
  watch: {
    dialogVisible (newData) {
      console.log(newData)
      if (newData) {
        console.log(this.checkGoodsData)

        this.judgeIsEdit = false
        this.handleToInit()
      }
    },
    customTime (newData) {
      console.log(newData)
    }
  },
  mounted () {
    console.log(new Date().format('yyyy-MM-dd hh:mm:ss'))
  },
  methods: {
    // 初始请求数据
    handleToInit () {
      this.goodsPriceShowData = [] // 初始清空商品价格列表数据
      let params = []
      this.checkGoodsData.forEach((item, index) => {
        params.push(item.goodsId)
      })
      // 商品价格模块初始列表数据
      getGoodsInfosByGoodIds({ goodsIds: params }).then((res) => {
        console.log(res)
        if (res.error === 0) {
          res.content.forEach((item, index) => {
            if (item.isDefaultPrd) {
              this.goodsPriceShowData.push(item)
            } else {
              item.goodsSpecProducts.forEach((itemC, indexC) => {
                console.log(itemC)
                let data = JSON.parse(JSON.stringify(item))
                let obj = {}
                obj = Object.assign(data, itemC)
                console.log(item, itemC)
                console.log(obj)
                this.goodsPriceShowData.push(obj)
              })
            }
          })
          this.goodsPriceShowData.forEach((item, index) => {
            this.$set(this.goodsPriceShowData[index], 'priceRevisionVal', '')
            this.$set(this.goodsPriceShowData[index], 'priceIncreaseVal', '')
            this.$set(this.goodsPriceShowData[index], 'discountInputVal', '')
          })
        }
      })
      console.log(this.goodsPriceShowData)
      // 运费模板模块
      this.handleToQueryTemplate()
      // 商品品牌列表数据查询
      this.handleToQueryBrandSelect()
      // 商品品牌表格数据
      this.handdleToQueryBrandList()
      // 商品详情下拉框数据
      this.handleDetailSelectData()
      // 商品详情表格数据
      this.handleToDetailTableData()
      // 商品标签
      this.handleToQueryLabel()
      // 会员专属
      this.handleToQueryCardList()
    },
    // 商品价格点击tips
    handleToClickPriceTip () {
      console.log(this.nowOptionIndex)
      if (this.nowOptionIndex === -1) return
      console.log(this.nowOptionIndex, this.nowInputValFlag, this.goodsPriceShowData)
      this.goodsPriceShowData.forEach((item, index) => {
        console.log(item)
        if (!item.priceRevisionVal && !item.priceRevisionVal !== 0) {
          console.log(item)
          let obj = {
            $index: index,
            row: item
          }
          switch (this.nowInputValFlag) {
            case 0:
              item.discountInputVal = this.goodsPriceShowData[this.nowOptionIndex].discountInputVal
              break
            case 1:
              item.priceIncreaseVal = this.goodsPriceShowData[this.nowOptionIndex].priceIncreaseVal
              break
            case 2:
              item.priceRevisionVal = this.goodsPriceShowData[this.nowOptionIndex].priceRevisionVal
              break
          }
          this.handleToBlur(obj, this.nowInputValFlag)
        }
      })
    },
    // 会员专属
    handleToQueryCardList () {
      getExclusiveCardList().then(res => {
        console.log(res)
        if (res.error === 0) {
          let obj = {
            id: null,
            cardName: this.$t('allGoods.batchDialog.pleaseSelectCard')
          }
          res.content.unshift(obj)
          this.membershipOptions = res.content
        }
      })
    },
    // 商品标签
    handleToQueryLabel () {
      getGoodsFilterItem({ needGoodsLabel: true }).then((res) => {
        console.log(res)
        if (res.error === 0) {
          let obj = {
            id: null,
            name: this.$t('allGoods.batchDialog.selectProductLabel')
          }
          res.content.goodsLabels.unshift(obj)
          this.labelOptions = res.content.goodsLabels
        }
      })
    },
    // 商品详情表格数据
    handleToDetailTableData () {
      let params = {
        pageName: this.tableInput[0],
        catId: this.commonTableValue[0],
        currentPage: this.pageDataFive.currentPage,
        pageRows: 20
      }
      pageList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.commonTableDataFive = res.content.dataList
          this.pageDataFive.totalRows = res.content.page.totalRows
        }
      })
    },
    // 商品详情表格当前页变化
    handleDetailCurrentChange () {
      this.handleToDetailTableData()
    },
    // 商品详情下拉框数据
    handleDetailSelectData () {
      getPageCate().then(res => {
        console.log(res)
        if (res.error === 0) {
          let obj = {
            id: null,
            name: this.$t('allGoods.batchDialog.selectClassification')
          }
          res.content.unshift(obj)
          this.commonTableOptionsFive = res.content
        }
      })
    },
    // 商品品牌列表数据查询
    handleToQueryBrandSelect () {
      classificationSelectRequest().then(res => { // 品牌下拉框数据
        console.log(res)
        if (res.error === 0) {
          let obj = {
            classifyId: null,
            classifyName: this.$t('allGoods.batchDialog.pleaseChoose')
          }
          res.content.unshift(obj)
          this.commonTableOptionsSeven = res.content
        }
      })
    },
    // 运费模板模块
    handleToQueryTemplate () {
      deliverTemplateNameListApi().then((res) => {
        console.log(res)
        if (res.error === 0) {
          let obj = {
            deliverTemplateId: null,
            templateName: this.$t('allGoods.batchDialog.noModification')
          }
          let defaultTem = {
            deliverTemplateId: 0,
            templateName: this.$t('allGoods.batchDialog.defaultTemplate')
          }
          res.content.unshift(obj, defaultTem)
          this.templateOptions = res.content
        }
      })
    },
    // 商品品牌列表数据查询
    handdleToQueryBrandList () {
      console.log(this.tableInput[1], this.commonTableValue[1])
      let params = {
        currentPage: this.pageDataSeven.currentPage,
        pageRows: 20,
        brandName: this.tableInput[1],
        classifyId: this.commonTableValue[1]
      }

      brandAllGetRequest(params).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.commonTableDataSeven = res.content.dataList
          this.pageDataSeven.totalRows = res.content.page.totalRows
        }
      })
    },
    // 商品品牌及页面详情点击搜索
    handleToClickSearch () {
      if (this.nowIndex === 7) {
        this.handdleToQueryBrandList()
      }
      if (this.nowIndex === 5) {
        this.handleToDetailTableData()
      }
    },
    // 商品模板表格选中
    handleCurrentChangeFive (val) {
      console.log(val)
      this.tableClassifyClickRow = val
    },
    // 商品品牌表格选中
    handleCurrentChangeSeven (val) {
      console.log(val)
      this.tableBrandClickRow = val
    },
    // 商品品牌列表页面变化
    handleCurrentChange () {
      this.handdleToQueryBrandList()
    },
    // 内层判断是否编辑弹窗确认事件
    handleToCloseInnerDialog () {
      this.innerVisible = false
      this.$emit('update:dialogVisible', false)
    },
    // 弹窗左侧点击
    handleClickLeftLi (index) {
      console.log(index)
      this.nowIndex = index
    },
    // 商品价格输入框失焦
    handleToBlur (scope, flag) {
      console.log(scope, flag)
      let initPrice = scope.row.shopPrice
      console.log(scope)
      this.nowOptionIndex = scope.$index // 当前操作的行下标
      this.nowInputValFlag = flag // 当前操作的格子flag
      let reg = /[^\d.]/g
      switch (flag) {
        case 0:
          console.log()
          if (reg.test(scope.row.discountInputVal)) return
          this.judgeIsEdit = true // 判断是否编辑过
          let nowPrice = (initPrice * (Number(scope.row.discountInputVal) / 10)).toFixed(2)
          this.$set(this.goodsPriceShowData[scope.$index], 'priceRevisionVal', nowPrice)
          this.$set(this.goodsPriceShowData[scope.$index], 'priceIncreaseVal', (nowPrice - initPrice).toFixed(2))
          console.log(this.goodsPriceShowData[scope.$index])
          break
        case 1:
          if (reg.test(scope.row.priceIncreaseVal)) return
          this.judgeIsEdit = true// 判断是否编辑过
          let nowPrice2 = (initPrice + Number(scope.row.priceIncreaseVal)).toFixed(2)
          this.$set(this.goodsPriceShowData[scope.$index], 'priceRevisionVal', nowPrice2)
          this.$set(this.goodsPriceShowData[scope.$index], 'discountInputVal', ((nowPrice2 / initPrice) * 10).toFixed(2))
          break
        case 2:
          if (reg.test(scope.row.priceRevisionVal)) return
          this.judgeIsEdit = true// 判断是否编辑过
          let nowPrice3 = (Number(scope.row.priceRevisionVal) - initPrice).toFixed(2)
          this.$set(this.goodsPriceShowData[scope.$index], 'priceIncreaseVal', nowPrice3)
          this.$set(this.goodsPriceShowData[scope.$index], 'discountInputVal', ((Number(scope.row.priceRevisionVal) / initPrice) * 10).toFixed(2))
          break
      }

      console.log(scope.row)
    },
    // 保存点击
    handleToSave () {
      let params = {}
      console.log('触发', this.nowIndex)
      params.goodsIds = []
      this.checkGoodsData.forEach((item, index) => {
        params.goodsIds.push(item.goodsId)
      })
      switch (this.nowIndex) {
        case 0:
          params.godsPriceNumber = {}
          console.log(this.goodsPriceShowData)
          this.goodsPriceShowData.forEach((item, index) => {
            if (!params.godsPriceNumber[item.goodsId]) params.godsPriceNumber[item.goodsId] = []
            let obj = {
              prdId: item.prdId
            }
            if (item.priceRevisionVal) {
              obj.shopPrice = Number(item.priceRevisionVal)
            } else {
              obj.shopPrice = item.shopPrice
            }
            params.godsPriceNumber[item.goodsId].push(obj)
          })
          console.log(this.checkGoodsData)
          break
        case 1:
          params.sortId = this.sortId
          break
        case 2:
          params.deliverTemplateId = this.templateValue
          break
        case 3:
          params.limitBuyNum = this.MinPurchaseInputVal
          params.limitMaxNum = this.MaxPurchaseInputVal
          break
        case 4:
          switch (this.onSaleRadio) {
            case '1':
              params.isOnSale = null
              break
            case '2':
              params.isOnSale = 1
              break
            case '3':
              params.isOnSale = 0
              params.saleType = 1
              params.saleTime = this.customTime
              break
            case '4':
              params.isOnSale = 0
              params.saleType = 0
              break
          }
          break
        case 5:
          switch (this.goodsRadio) {
            case '1':
              params.isPageUp = null
              break
            case '2':
              params.isPageUp = 1
              break
            case '3':
              params.isPageUp = 0
              break
          }
          if (this.tableClassifyClickRow !== null) {
            params.goodsPageId = this.tableClassifyClickRow.pageId
          }
          break
        case 6:
          console.log(this.labelValueCheckArr)
          params.goodsLabels = []
          this.labelValueCheckArr.forEach((item, index) => {
            params.goodsLabels.push(item.id)
          })
          break
        case 7:
          if (this.tableBrandClickRow !== null) {
            params.brandId = this.tableBrandClickRow.id
          }
          break
        case 8:
          if (this.membershipTopRadio === '1') {
            params.isCardExclusive = 1
            params.cardIds = []
            console.log(this.membershipValueCheckArr)
            this.membershipValueCheckArr.forEach((item, index) => {
              params.cardIds.push(item.id)
            })
          } else if (this.membershipTopRadio === '2') {
            params.isCardExclusive = 0
          }
          break
        case 9:
          params.deliverPlace = this.placeOfDeliveryInput
          break
      }
      console.log(params)
      batchOperateGoods(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success({
            message: this.$t('allGoods.batchDialog.saveSuccessfully'),
            showClose: true
          })
        } else {
          this.$message.error({
            message: this.$t('allGoods.batchDialog.saveFailed'),
            showClose: true
          })
        }
      })
      // this.$emit('update:dialogVisible', false)
    },
    // 父弹窗右上角点击关闭icon
    handleToCloseDialog () {
      console.log('2222')
      if (this.judgeIsEdit) {
        this.innerVisible = true
      } else {
        this.$emit('update:dialogVisible', false)
      }
    },
    // 运费模板右侧文字点击综合处理
    handleToClickTemplate (flag) {
      switch (flag) {
        case 0:
          this.handleToQueryTemplate()
          break
        case 1:
          this.$router.push({
            name: 'deliverTemplateAdd'
          })
          break
        case 2:
          this.$router.push({
            name: 'deliverTemplateList'
          })
          break
      }
    },
    // 商品详情自定义内容中部综合点击处理
    handleToClickCustom (flag) {
      switch (flag) {
        case 0:
          console.log(flag)
          if (this.nowIndex === 5) {
            this.isShowCommonTableFive = true
          }
          if (this.nowIndex === 7) {
            this.isShowCommonTableSeven = true
          }

          break
        case 1:
          if (this.nowIndex === 5) {
            this.handleDetailSelectData()
            this.handleToDetailTableData()
          }
          if (this.nowIndex === 6) {
            this.handleToQueryLabel()
          }
          if (this.nowIndex === 7) {
            this.handleToQueryBrandSelect()
            this.handdleToQueryBrandList()
          }
          if (this.nowIndex === 8) {
            this.handleToQueryCardList()
          }
          break
        case 2:
          if (this.nowIndex === 5) {
            this.$router.push({
              path: '/admin/home/main/decorationHome',
              query: {
                pageId: -1
              }
            })
          }
          if (this.nowIndex === 6) {
            this.$router.push({
              name: 'addGoodsLabel'
            })
          }
          if (this.nowIndex === 7) {
            this.$router.push({
              name: 'addBrand'
            })
          }
          if (this.nowIndex === 8) {
            this.$router.push({
              path: '/admin/home/main/normalCardDetail',
              query: {
                cardType: 0
              }
            })
          }
          break
        case 3:
          if (this.nowIndex === 5) {
            this.$router.push({
              name: 'picture_setting'
            })
          }
          if (this.nowIndex === 6) {
            this.$router.push({
              name: 'label'
            })
          }
          if (this.nowIndex === 7) {
            this.$router.push({
              name: 'brand'
            })
          }
          if (this.nowIndex === 8) {
            this.$router.push({
              name: 'user_card'
            })
          }
          break
      }
    },
    // 商品标签select选中值变化
    handleToSelect (val) {
      console.log(val)
      if (val === null) return
      let data = JSON.parse(JSON.stringify(this.labelOptions))
      data.forEach((item, index) => {
        if (item.id === val) {
          this.labelValueCheckArr.push(item)
          this.labelOptions.splice(index, 1)
        }
      })
      this.labelValue = null
    },
    // 商品标签点击icon删除
    handleToClickDel (index, item) {
      this.labelOptions.push(item)
      this.labelValueCheckArr.splice(index, 1)
    },
    // 会员卡选中值变化
    handleToMemberSelect (val) {
      if (val === null) return
      let data = JSON.parse(JSON.stringify(this.membershipOptions))
      data.forEach((item, index) => {
        if (item.id === val) {
          this.membershipValueCheckArr.push(item)
          this.membershipOptions.splice(index, 1)
        }
      })
      this.membershipValue = null
    },
    // 点击删除
    handleToClickMemberDel (index, item) {
      this.membershipOptions.push(item)
      this.membershipValueCheckArr.splice(index, 1)
    },
    // 运费模板下拉框值变化查询详细模板信息
    handleToQueryTemOption (val) {
      console.log(val)
      if (val === 0) {
        this.templateShowContentData = { a: 1 }
      } else {
        getDeliverTemplateApi({ deliverTemplateId: val }).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.turnType = res.content.flag
            let obj = {}
            obj = res.content.content
            this.templateShowContentData = obj
            console.log(this.templateShowContentData)
          }
        })
      }
    },
    // 跳转模板详情页
    handelToTurnTemDetail (to) {
      console.log(to, this.templateValue)
      this.$router.push({
        path: '/admin/home/main/goodsManage/deliverTemplate/deliverTemplateUpdate',
        query: {
          deliverTemplateId: this.templateValue,
          type: this.turnType
        }
      })
    }
  }
}
</script>
<style scoped lang="scss">
.batchSetup {
  /deep/ .el-dialog__header {
    text-align: left;
  }
  /deep/ .el-dialog__body {
    padding: 0;
  }
  .batchSetupMain {
    height: 100%;
    display: flex;
    .left {
      width: 100px;
      border-right: 1px solid #eee;
      font-size: 14px;
      li {
        text-align: center;
        margin-top: 20px;
        span {
          display: inline-block;
          padding-bottom: 10px;
          text-decoration: none;
          color: #333;
          border-bottom: 1px solid #fff;
          font-size: 14px;
          cursor: pointer;
          &:hover {
            color: #5a8bff;
          }
        }
      }
      .hover {
        color: #5a8bff;
        border-bottom: 1px solid #5a8bff;
      }
    }
    .right {
      width: 100%;
      padding: 10px 20px 0 20px;
      .br_title {
        background: #f5f5f5;
        color: #333;
        font-size: 13px;
        margin-bottom: 20px;
        height: 40px;
        line-height: 40px;
        width: 96%;
        padding-left: 10px;
        i {
          color: #999;
          margin-left: 30px;
        }
      }
      .dynamic {
        width: 100%;
        height: 100%;
        // padding-bottom: 60px;
        .price {
          height: 100%;
          .title {
            margin-bottom: 20px;
            i {
              color: #5a8bff;
              margin-right: 10px;
            }
            span {
              color: #999;
            }
          }
          .bottom {
            height: 337px;
            overflow-y: auto;
            .goodsDiv {
              display: flex;
              .item_goods_img {
                width: 40px;
                height: 40px;
                margin-right: 5px;
                img {
                  width: 100%;
                  height: 100%;
                }
              }
              .goods_message {
                width: 90px;
                height: 40px;
                display: flex;
                flex-direction: column;
                justify-content: space-around;
                text-align: left;
                font-size: 13px;
                .item_goods_name {
                  width: 100%;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                  overflow: hidden;
                  color: #000;
                }
                .item_goods_desc {
                  color: #666;
                  text-overflow: ellipsis;
                  overflow: hidden;
                  white-space: nowrap;
                  width: 100%;
                  font-size: 12px;
                }
              }
            }

            .discount {
              display: flex;
              align-items: center;
              /deep/ .el-input {
                width: 80px;
              }
            }
          }
          /deep/ .tableClss th {
            background-color: #f5f5f5;
            border: none;
            height: 36px;
            font-weight: bold;
            color: #000;
            padding: 8px 10px;
          }
        }
        .classification {
          /deep/ .el-form-item__label {
            width: 83px !important;
          }
        }
        .template {
          .title {
            display: flex;
            .name {
              display: flex;
              align-items: center;
            }
            .rightOptions {
              display: flex;
              width: 230px;
              color: #5a8bff;
              align-items: center;
              span {
                display: inline-block;
                width: 60px;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                padding: 0 10px;
              }
            }
          }
          .hiddenTemplate {
            padding: 10px 0 0 70px;
            .content {
              width: 455px;
              background: #f5f5f5;
              padding: 10px 10px;
              max-height: 330px;
              overflow-y: auto;
              .top {
                display: flex;
                justify-content: space-between;
                .toDetail {
                  color: #5a8bff;
                  cursor: pointer;
                }
              }
              .bottomContent {
                border-top: 1px solid #e6e6e6;
                margin-top: 10px;
                padding-top: 8px;
                .title {
                  margin-bottom: 8px;
                }
                .hiddencontent {
                  line-height: 18px;
                  text-align: justify;
                }
              }
            }
          }
        }
        .limitNum {
          .content {
            display: flex;
            /deep/ .el-input {
              width: 80px;
              margin: 0 10px;
            }
            span,
            i {
              display: flex;
              align-items: center;
            }
            i {
              color: #999;
            }
          }
        }
        .onSaleTime {
          display: flex;
          .onSaleTimeRight {
            display: flex;
            flex-direction: column;
            /deep/ .el-radio {
              margin-bottom: 20px;
            }
            .custom {
              display: flex;
              align-items: center;
              .tips {
                display: flex;
                align-items: center;
                position: relative;
                top: -10px;
              }
            }
          }
        }
        .commodityDetails {
          .temPosition {
            display: flex;
            margin-bottom: 20px;
          }
          .customContent {
            display: flex;
            .customMiddle {
              display: flex;
              flex-direction: column;
            }
            .customFooter {
              display: flex;
              margin-top: 20px;
              .label {
                display: flex;
                align-items: center;
              }
              .rightContent {
                display: flex;
                width: 230px;
                color: #5a8bff;
                align-items: center;
                span {
                  display: inline-block;
                  width: 60px;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                  cursor: pointer;
                  padding: 0 10px;
                }
              }
            }
          }
        }
        .tatle {
          display: flex;
          padding: 20px 0 10px 10px;
          /deep/ .el-input {
            width: 140px;
            margin-left: 5px;
          }
          /deep/ .el-button {
            margin-left: 10px;
          }
          .tableLeft {
            display: flex;
            margin-right: 30px;
            span {
              white-space: nowrap;
              display: flex;
              align-items: center;
            }
          }
          .tableRight {
            display: flex;
            span {
              white-space: nowrap;
              display: flex;
              align-items: center;
            }
          }
        }
        .commonTable {
          width: 90%;
          height: 210px;
          overflow-y: auto;
          /deep/ .tableClss th {
            background-color: #f5f5f5;
            border: none;
            height: 36px;
            font-weight: bold;
            color: #000;
            padding: 8px 10px;
          }
        }
        .membershipExclusive {
          .membershipBottom {
            margin-top: 20px;
            .membershipTitle {
              i {
                color: #999;
                margin-left: 10px;
                display: inline-block;
              }
            }
            .membershipMiddle {
              display: flex;
              margin: 20px 0;
              /deep/ .el-input {
                width: 150px;
              }
              .rightContent {
                display: flex;
                align-items: center;
                margin-left: 10px;
              }
            }
            .showLabelContent {
              padding-left: 10px;
              background: #f5f5f5;
              margin-top: 20px;
            }
          }
        }
        .placeOfDelivery {
          .placeOfDeliveryContent {
            display: flex;
            .name {
              display: flex;
              align-items: center;
            }
            /deep/ .el-input {
              width: 180px;
            }
          }
        }
      }
    }
    .showLabelContent {
      padding: 10px 0 0 70px;
      display: flex;
      .labelLeft {
        display: flex;
        padding-top: 4px;
      }
      .labelRight {
        width: 350px;
        display: flex;
        flex-wrap: wrap;
        .list {
          padding: 0 3px;
          height: 22px;
          line-height: 22px;
          text-align: center;
          margin-left: 10px;
          border: 1px solid #ccc;
          margin-bottom: 10px;
          position: relative;
          img {
            position: absolute;
            right: -10px;
            top: -7px;
            cursor: pointer;
          }
        }
      }
    }
  }
  .dialog-footer {
    display: block;
    width: 100%;
    text-align: center;
  }
}
</style>
