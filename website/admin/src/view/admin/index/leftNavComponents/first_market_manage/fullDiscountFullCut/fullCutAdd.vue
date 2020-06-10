<template>
  <div class="addActivity">
    <div class="addActivity_main">
      <div class="nav_list">
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            :label="$t('fullCuti18n.allAct')"
            name="0"
          ></el-tab-pane>
          <el-tab-pane
            :label="$t('fullCuti18n.process')"
            name="1"
          ></el-tab-pane>
          <el-tab-pane
            :label="$t('fullCuti18n.notStart')"
            name="2"
          ></el-tab-pane>
          <el-tab-pane
            :label="$t('fullCuti18n.expired')"
            name="3"
          ></el-tab-pane>
          <el-tab-pane
            :label="$t('fullCuti18n.terminated')"
            name="4"
          ></el-tab-pane>
          <el-tab-pane
            :label="getTabName()"
            name="5"
          ></el-tab-pane>
        </el-tabs>
      </div>

      <div class="full_cut_content">
        <el-form
          label-width="120px"
          :rules="rules"
          :model="params"
          ref="fullCutAddForm"
          label-suffix="："
        >
          <!-- 活动名称 -->
          <el-form-item
            :label="$t('fullCuti18n.actName')"
            prop="actName"
          >
            <el-input
              v-model="params.actName"
              :placeholder="$t('fullCuti18n.peActName')"
              size="small"
              class="form_input"
            ></el-input>
          </el-form-item>

          <!-- 活动优先级 -->
          <el-form-item
            :label="$t('fullCuti18n.actPriority')"
            prop="strategyPriority"
          >
            <el-input-number
              v-model="params.strategyPriority"
              :placeholder="$t('fullCuti18n.peActPri')"
              class="form_input"
              size="small"
              :min="1"
              :precision="0"
            ></el-input-number>
            <p class="form_tip">{{$t('fullCuti18n.priorityReason')}}</p>
          </el-form-item>

          <!-- 活动类型 -->
          <el-form-item
            :label="$t('fullCuti18n.actType')"
            prop="type"
          >
            <el-radio-group
              v-model="params.type"
              :disabled="!!params.id"
            >
              <el-radio label="2">{{$t('fullCuti18n.fullMinus')}}</el-radio>
              <el-radio label="1">{{$t('fullCuti18n.efr')}}</el-radio>
              <el-radio label="3">{{$t('fullCuti18n.fullFold')}}</el-radio>
              <el-radio label="4">{{$t('fullCuti18n.xth')}}</el-radio>
            </el-radio-group>
            <p class="form_tip actTypeSaveTips">{{$t('fullCuti18n.unedit')}}</p>
          </el-form-item>

          <!-- 满减金额 -->
          <el-form-item
            :label="$t('fullCuti18n.fullReduceAmount')"
            prop="conditionAddParams"
          >
            <div v-if="params.type == 2">
              <el-radio
                label="1"
                v-model="fullDeduction"
                :disabled="!!params.id"
              >{{$t('fullCuti18n.fullAmount')}}</el-radio>
              <div
                v-show="fullDeduction == '1'"
                v-for="(item, index) in conditionAddParams2"
                :key="'info'+ index"
              >
                <div
                  class="flex-block"
                  v-if="item.fullMoney !== null && item.fullMoney !== undefined"
                >
                  <span>{{$t('fullCuti18n.full')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams2[index].fullMoney"
                    :disabled="fullDeduction == 2 || !!params.id"
                  ></el-input>&nbsp;{{$t('fullCuti18n.yuan')}}，
                  <span>{{$t('fullCuti18n.less')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams2[index].reduceMoney"
                    :disabled="fullDeduction == 2  || !!params.id"
                  ></el-input>&nbsp;
                  <span>{{$t('fullCuti18n.yuan')}}</span>
                  <div
                    class="iconAdd"
                    v-if="!params.id"
                  >
                    <img
                      v-if="index === 0"
                      :src="$imageHost + '/image/admin/sign_jia.png'"
                      alt=""
                      @click="addFullCutItemA()"
                    >
                    <img
                      v-else
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="deleteFullCutItemA(index)"
                    >
                  </div>
                </div>
              </div>
              <div v-show="fullDeduction == '2'">
                <div class="flex-block">
                  <span>{{$t('fullCuti18n.full')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 2  || !!params.id"
                  ></el-input>&nbsp;{{$t('fullCuti18n.yuan')}}，
                  <span>{{$t('fullCuti18n.less')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 2  || !!params.id"
                  ></el-input>&nbsp;
                  <span>{{$t('fullCuti18n.yuan')}}</span>
                  <div
                    class="iconAdd"
                    v-if="!params.id"
                  >
                    <img :src="$imageHost + '/image/admin/sign_jia.png'">
                  </div>
                </div>
              </div>
              <el-radio
                label="2"
                v-model="fullDeduction"
                :disabled="!!params.id"
              >{{$t('fullCuti18n.fullNum')}}</el-radio>
              <div
                v-show="fullDeduction == '2'"
                v-for="(item, index) in conditionAddParams2"
                :key="'info2'+index"
              >
                <div
                  class="flex-block"
                  v-if="item.amount !== null && item.amount !== undefined"
                >
                  <span>{{$t('fullCuti18n.full')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams2[index].amount"
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>&nbsp;{{$t('fullCuti18n.piece')}}，
                  <span>{{$t('fullCuti18n.less')}}</span>
                  &nbsp;<el-input
                    v-if="fullDeduction == 1"
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>
                  <el-input
                    v-else
                    class="form_input"
                    size="small "
                    v-model="conditionAddParams2[index].reduceMoney"
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>&nbsp;
                  <span>{{$t('fullCuti18n.yuan')}}</span>
                  <div
                    class="iconAdd"
                    v-if="!params.id"
                  >
                    <img
                      v-if="index === 0"
                      :src="$imageHost + '/image/admin/sign_jia.png'"
                      alt=""
                      @click="addFullCutItemB()"
                    >
                    <img
                      v-else
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="deleteFullCutItemB(index)"
                    >
                  </div>
                </div>
              </div>
              <div v-show="fullDeduction != '2'">
                <div class="flex-block">
                  <span>{{$t('fullCuti18n.full')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>&nbsp;{{$t('fullCuti18n.piece')}}，
                  <span>{{$t('fullCuti18n.less')}}</span>
                  &nbsp;
                  <el-input
                    class="form_input"
                    size="small "
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>&nbsp;
                  <span>{{$t('fullCuti18n.yuan')}}</span>
                  <div
                    class="iconAdd"
                    v-if="!params.id"
                  >
                    <img :src="$imageHost + '/image/admin/sign_jia.png'">
                  </div>
                </div>
              </div>
            </div>
            <div v-else-if="params.type == 1">
              <el-radio
                label="1"
                v-model="fullDeduction"
                :disabled="!!params.id"
              >{{$t('fullCuti18n.fullAmount')}}</el-radio>
              <div class="flex-block">
                <span>{{$t('fullCuti18n.perFull')}}</span>
                &nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams1[0].fullMoney"
                  :disabled="fullDeduction == 2  || !!params.id"
                ></el-input>&nbsp;{{$t('fullCuti18n.yuan')}}，
                <span>{{$t('fullCuti18n.less')}}</span>
                &nbsp;<el-input
                  v-if="fullDeduction == 2"
                  class="form_input"
                  size="small"
                  :disabled="fullDeduction == 2  || !!params.id"
                ></el-input>
                <el-input
                  v-else
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams1[0].reduceMoney"
                  :disabled="fullDeduction == 2  || !!params.id"
                ></el-input>&nbsp;
                <span>{{$t('fullCuti18n.yuan')}}</span>
              </div>
              <el-radio
                label="2"
                v-model="fullDeduction"
                :disabled="!!params.id"
              >{{$t('fullCuti18n.fullNum')}}</el-radio>
              <div class="flex-block">
                <span>{{$t('fullCuti18n.perFull')}}</span>
                &nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams1[0].amount"
                  :disabled="fullDeduction == 1  || !!params.id"
                ></el-input>&nbsp;{{$t('fullCuti18n.piece')}}，
                <span>{{$t('fullCuti18n.less')}}</span>
                &nbsp;<el-input
                  v-if="fullDeduction == 1"
                  class="form_input"
                  size="small"
                  :disabled="fullDeduction == 1  || !!params.id"
                ></el-input>
                <el-input
                  v-else
                  class="form_input"
                  size="small "
                  v-model="conditionAddParams1[0].reduceMoney"
                  :disabled="fullDeduction == 1  || !!params.id"
                ></el-input>&nbsp;
                <span>{{$t('fullCuti18n.yuan')}}</span>
              </div>
              <!-- <span>{{$t('fullCuti18n.perFull')}}</span>
              &nbsp;<el-input
                class="form_input"
                size="small"
                v-model="conditionAddParams1.fullMoney"
              ></el-input>&nbsp;{{$t('fullCuti18n.yuan')}}，
              <span>{{$t('fullCuti18n.less')}}</span>
              &nbsp;<el-input
                class="form_input"
                size="small"
                v-model="conditionAddParams1.reduceMoney"
              ></el-input>&nbsp;{{$t('fullCuti18n.yuan')}} -->
            </div>
            <div v-else-if="params.type == 3">
              <el-radio
                label="1"
                v-model="fullDeduction"
                :disabled="!!params.id"
              >{{$t('fullCuti18n.fullAmount')}}</el-radio>
              <div
                v-show="fullDeduction == '1'"
                v-for="(item, index) in conditionAddParams3"
                :key="'info1'+index"
              >
                <div
                  class="flex-block"
                  v-if="item.fullMoney !== null && item.fullMoney !== undefined"
                >
                  <span>{{$t('fullCuti18n.full')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams3[index].fullMoney"
                    :disabled="fullDeduction == 2  || !!params.id"
                  ></el-input>&nbsp;{{$t('fullCuti18n.yuan')}}，
                  <span>{{$t('fullCuti18n.hit')}}</span>
                  &nbsp;
                  <el-input
                    v-if="fullDeduction == 2"
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 2  || !!params.id"
                  ></el-input>
                  <el-input
                    v-else
                    class="form_input"
                    size="small "
                    v-model="conditionAddParams3[index].discount"
                    :disabled="fullDeduction == 2  || !!params.id"
                  ></el-input>&nbsp;
                  <span>{{$t('fullCuti18n.fold')}}</span>
                  <div
                    class="iconAdd"
                    v-if="!params.id"
                  >
                    <img
                      v-if="index === 0"
                      :src="$imageHost + '/image/admin/sign_jia.png'"
                      alt=""
                      @click="addFullCutItemC()"
                    >
                    <img
                      v-else
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="deleteFullCutItemC(index)"
                    >
                  </div>
                </div>
              </div>
              <div v-show="fullDeduction != '1'">
                <div class="flex-block">
                  <span>{{$t('fullCuti18n.full')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 2  || !!params.id"
                  ></el-input>&nbsp;{{$t('fullCuti18n.yuan')}}，
                  <span>{{$t('fullCuti18n.hit')}}</span>
                  &nbsp;
                  <el-input
                    class="form_input"
                    size="small "
                    :disabled="fullDeduction == 2  || !!params.id"
                  ></el-input>&nbsp;
                  <span>{{$t('fullCuti18n.fold')}}</span>
                  <div
                    class="iconAdd"
                    v-if="!params.id"
                  >
                    <img :src="$imageHost + '/image/admin/sign_jia.png'">
                  </div>
                </div>
              </div>
              <el-radio
                label="2"
                v-model="fullDeduction"
                :disabled="!!params.id"
              >{{$t('fullCuti18n.fullNum')}}</el-radio>
              <div
                v-show="fullDeduction == '2'"
                v-for="(item, index) in conditionAddParams3"
                :key="'info3'+index"
              >
                <div
                  class="flex-block"
                  v-if="item.amount !== null && item.amount !== undefined"
                >
                  <span>{{$t('fullCuti18n.full')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams3[index].amount"
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>&nbsp;{{$t('fullCuti18n.piece')}}，
                  <span>{{$t('fullCuti18n.hit')}}</span>
                  &nbsp;
                  <el-input
                    v-if="fullDeduction == 1"
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>
                  <el-input
                    v-else
                    class="form_input"
                    size="small "
                    v-model="conditionAddParams3[index].discount"
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>&nbsp;
                  <span>{{$t('fullCuti18n.fold')}}</span>
                  <div
                    class="iconAdd"
                    v-if="!params.id"
                  >
                    <img
                      v-if="index === 0"
                      :src="$imageHost + '/image/admin/sign_jia.png'"
                      alt=""
                      @click="addFullCutItemD()"
                    >
                    <img
                      v-else
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="deleteFullCutItemD(index)"
                    >
                  </div>
                </div>
              </div>
              <div v-show="fullDeduction != '2'">
                <div class="flex-block">
                  <span>{{$t('fullCuti18n.full')}}</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>&nbsp;{{$t('fullCuti18n.piece')}}，
                  <span>{{$t('fullCuti18n.hit')}}</span>
                  &nbsp;
                  <el-input
                    class="form_input"
                    size="small "
                    :disabled="fullDeduction == 1  || !!params.id"
                  ></el-input>&nbsp;
                  <span>{{$t('fullCuti18n.fold')}}</span>
                  <div
                    class="iconAdd"
                    v-if="!params.id"
                  >
                    <img :src="$imageHost + '/image/admin/sign_jia.png'">
                  </div>
                </div>
              </div>
            </div>
            <div v-else-if="params.type == 4">
              <div>
                {{$t('fullCuti18n.no')}}&nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams4[0].amount"
                  :disabled="!!params.id"
                ></el-input>&nbsp;{{$t('fullCuti18n.piece')}}，
                {{$t('fullCuti18n.hit')}}&nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams4[0].discount"
                  :disabled="!!params.id"
                ></el-input>&nbsp;{{$t('fullCuti18n.fold')}}
              </div>
            </div>
          </el-form-item>

          <!-- 有效期 -->
          <el-form-item
            :label="$t('fullCuti18n.validity')"
            prop="timeInterval"
          >
            <el-date-picker
              v-model="params.timeInterval"
              type="datetimerange"
              size="small"
              :range-separator="$t('fullCuti18n.to')"
              :start-placeholder="$t('fullCuti18n.startTime')"
              :end-placeholder="$t('fullCuti18n.endTime')"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00', '23:59:59']"
              :disabled="!!params.id"
            ></el-date-picker>
          </el-form-item>

          <!-- 活动商品 -->
          <el-form-item
            :label="$t('fullCuti18n.eventGoods')"
            prop="actType"
          >
            <el-radio
              v-model="params.actType"
              label="0"
              :disabled="!!params.id"
            >{{$t('fullCuti18n.allProducts')}}</el-radio>
            <br>
            <el-radio
              v-model="params.actType"
              label="1"
              :disabled="!!params.id"
            >{{$t('fullCuti18n.designGoods')}}</el-radio>

            <div v-if="params.actType === '1'">
              <div
                class="add_goods_btn"
                @click="chooseGoodsHandler"
                v-if="!params.id"
              > + {{$t('fullCuti18n.selectGoods')}}</div>
              <span
                @click="onlyChooseGoodsHandler"
                style="color: #e4393c"
              >{{$t('adSharePolite.alreadyChoose')}}{{this.selectedGoodsIdList.length}}{{$t('adSharePolite.goods')}}</span>
              <div
                class="goods_area"
                v-if="goodsList && goodsList.length > 0"
              >
                <table
                  class="goods_table"
                  width="100%"
                >
                  <thead>
                    <tr>
                      <th width="45%">{{$t('fullCuti18n.goodsName')}}</th>
                      <th width="15%">{{$t('fullCuti18n.price')}}</th>
                      <th width="15%">{{$t('fullCuti18n.inStock')}}</th>
                      <th width="15%">{{$t('fullCuti18n.operate')}}</th>
                    </tr>
                  </thead>
                  <tbody class="tbody">
                    <tr
                      v-for="(item, index) in goodsList"
                      :key="item.name"
                    >
                      <td>
                        <div class="goods_info">
                          <div class="goods_img">
                            <img
                              :src="item.goodsImg"
                              alt=""
                            >
                          </div>
                          <div class="goods_name">{{item.goodsName}}</div>
                        </div>
                      </td>
                      <td>￥{{item.shopPrice}}</td>
                      <td>{{item.goodsNumber}}</td>
                      <td>
                        <span
                          v-if="!params.id"
                          class="edit-icon"
                          @click="deleteGoods(index)"
                        >{{$t('fullCuti18n.delete')}}</span>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <div
                class="add_goods_btn"
                @click="selectPlatform"
                v-if="!params.id"
              > + {{$t('fullCuti18n.slClassify')}}</div>
              <table
                class="brand_table"
                v-if="platformList && platformList.length"
              >
                <tbody>
                  <tr>
                    <th
                      style="border-bottom: 1px solid #ddd"
                      width="100%"
                    >
                      {{$t('fullCuti18n.platformClassify')}}
                      <div class="operate">
                        <span
                          class="edit-icon edit"
                          @click="editPlateformClassification"
                          v-if="!params.id"
                        >{{$t('fullCuti18n.edit')}}</span>
                        <span
                          class="delete"
                          @click="deletePlateformClassfication"
                          v-if="!params.id"
                        >{{$t('fullCuti18n.delete')}}</span>
                      </div>
                    </th>
                  </tr>
                  <tr>
                    <div class="exampleWrapper">
                      <span class="example">{{$t('fullCuti18n.example')}}：</span>
                      <span class="first_cat">{{$t('fullCuti18n.firstC')}}</span>
                      <span class="second_cat">{{$t('fullCuti18n.secondC')}}</span>
                      <span class="third_cat">{{$t('fullCuti18n.thirdC')}}</span>
                    </div>
                    <div
                      class="bussinessGoodsName"
                      v-for="(item, index) in platformList"
                      :key="index"
                    >
                      <span :class="{'first_cat': item.level === 0, 'second_cat': item.level === 1, 'third_cat': item.level === 2}">{{item.catName}}</span>
                    </div>
                  </tr>
                </tbody>
              </table>

              <div
                class="add_goods_btn"
                @click="selectBussiness"
                v-if="!params.id"
              > + {{$t('fullCuti18n.selectC')}}</div>
              <table
                class="brand_table"
                v-if="bussinessList && bussinessList.length > 0"
              >
                <tbody>
                  <tr>
                    <th
                      style="border-bottom: 1px solid #ddd"
                      width="100%"
                    >
                      {{$t('fullCuti18n.businessC')}}
                      <div class="operate">
                        <span
                          class="edit"
                          @click="editBussinessClassification"
                          v-if="!params.id"
                        >{{$t('fullCuti18n.edit')}}</span>
                        <span
                          class="delete"
                          @click="deleteBussinessClassfication"
                          v-if="!params.id"
                        >{{$t('fullCuti18n.delete')}}</span>
                      </div>
                    </th>
                  </tr>
                  <tr>
                    <div class="exampleWrapper">
                      <span class="example">{{$t('fullCuti18n.example')}}：</span>
                      <span class="first_cat">{{$t('fullCuti18n.firstC')}}</span>
                      <span class="second_cat">{{$t('fullCuti18n.secondC')}}</span>
                      <span class="third_cat">{{$t('fullCuti18n.thirdC')}}</span>
                    </div>
                    <div
                      class="bussinessGoodsName"
                      v-for="(item, index) in bussinessList"
                      :key="index"
                    >
                      <span :class="{'first_cat': item.level === 0, 'second_cat': item.level === 1, 'third_cat': item.level === 2}">{{item.sortName}}</span>
                    </div>
                  </tr>
                </tbody>
              </table>

              <!-- <div
                class="add_goods_btn"
                @click="selectGoodsLabel"
                v-if="!params.id"
              > + 选择商品标签</div> -->
              <table
                class="brand_table"
                v-if="labelNameList && labelNameList.length > 0"
              >
                <tbody>
                  <tr>
                    <th
                      style="border-bottom: 1px solid #ddd"
                      width="100%"
                    >
                      {{$t('fullCuti18n.sdLabel')}}
                      <div class="operate">
                        <span
                          class="edit"
                          @click="editSelectedLabel"
                          v-if="!params.id"
                        >{{$t('fullCuti18n.edit')}}</span>
                        <span
                          class="delete"
                          @click="deleteSelectedLabel"
                          v-if="!params.id"
                        >{{$t('fullCuti18n.delete')}}</span>
                      </div>
                    </th>
                  </tr>
                  <tr>
                    <div
                      class="goodsName"
                      v-for="(item, index) in labelNameList"
                      :key="index"
                    >
                      <span>{{item.name}}</span>
                    </div>
                  </tr>
                </tbody>
              </table>

              <div
                class="add_goods_btn"
                @click="seclectGoodsBrand"
                v-if="!params.id"
              > + {{$t('fullCuti18n.choBrand')}}</div>
              <table
                class="brand_table"
                v-if="goodsNameList && goodsNameList.length > 0"
              >
                <tbody>
                  <tr>
                    <th
                      style="border-bottom: 1px solid #ddd"
                      width="100%"
                    >
                      {{$t('fullCuti18n.selectedBrand')}}
                      <div class="operate">
                        <span
                          class="edit"
                          @click="editSelectedBrand"
                          v-if="!params.id"
                        >{{$t('fullCuti18n.edit')}}</span>
                        <span
                          class="delete"
                          @click="deleteSelectedBrand"
                          v-if="!params.id"
                        >{{$t('fullCuti18n.delete')}}</span>
                      </div>
                    </th>
                  </tr>
                  <tr>
                    <div
                      class="goodsName"
                      v-for="(item, index) in goodsNameList"
                      :key="index"
                    >
                      <span>{{item.brandName}}</span>
                    </div>
                  </tr>
                </tbody>
              </table>
            </div>
          </el-form-item>

          <!-- 会员专享活动 -->
          <el-form-item :label="$t('fullCuti18n.mea')">
            <el-checkbox
              v-model="checkCard"
              :disabled="!!params.id"
            >{{$t('fullCuti18n.memberPrivilege')}}</el-checkbox>
            <br>
            <el-form-item
              v-if="checkCard"
              prop="memberCards"
            >
              <el-select
                v-model="memberCardInfo"
                :placeholder="$t('fullCuti18n.pls')"
                size="small"
                :multiple='true'
                @change="getMemberCardName"
                style="float:left;"
                :disabled="!!params.id"
              >
                <el-option
                  v-for="item in memberCardNameList"
                  :key="item.id"
                  :label="item.cardName"
                  :value="item.id"
                ></el-option>
              </el-select>
              <div style="padding-left: 10px; float:left;">
                <el-button
                  type="text"
                  @click="memberCardActivityName"
                >{{$t('fullCuti18n.refresh')}}</el-button>
                <el-divider direction="vertical"></el-divider>
                <!-- <el-button>新建会员卡</el-button> -->
                <router-link
                  class="link"
                  to="/admin/home/main/normalCardDetail?cardType=0"
                >{{$t('fullCuti18n.newMC')}}</router-link>
                <el-divider direction="vertical"></el-divider>
                <!-- <el-button>管理会员卡</el-button> -->
                <router-link
                  class="link"
                  to="/admin/home/main/user_card"
                >{{$t('fullCuti18n.mMC')}}</router-link>
              </div>
            </el-form-item>
          </el-form-item>
        </el-form>
      </div>

      <!-- 选择商品弹窗 -->
      <ChoosingGoods
        :tuneUpChooseGoods='tuneUpChooseGoodsDialog'
        :chooseGoodsBack="selectedGoodsIdList"
        :onlyShowChooseGoods="isOnlyShowChooseGoods"
        @resultGoodsDatas="returnGoodsData"
      />

      <!-- 选择商家分类弹窗  -->
      <AddingBusClassDialog
        :dialogVisible.sync="tuneUpBussDialog"
        :backDataArr='bussinessIdList'
        :classFlag="1"
        @BusClassTrueDetailData="returnBusinessData"
      />

      <!-- 选择平台分类弹窗 -->
      <AddingBusClassDialog
        :dialogVisible.sync="tuneUpPlatformDialog"
        :backDataArr='platformIdList'
        :classFlag="2"
        @BusClassTrueDetailData="returnPlateformData"
      />

      <!-- 选择商品标签 -->
      <SelectGoodsLabel
        :callAddProductLabel.sync="tuneUpSelectGoodsLabelDialog"
        :brandBackData="labelNameIdList"
        @handleToGetBackData="returnGoodsLabelData"
      />

      <!-- 选择商品品牌弹窗 -->
      <AddBrandDialog
        :callAddBrand.sync="tuneUpBrandDialog"
        :brandBackData="goodsBrandIdList"
        @handleToGetBackData="returnGoodsBrandData"
      />
    </div>

    <div class="save_button">
      <el-button
        type="primary"
        size="small"
        @click="submit"
      >{{$t('fullCuti18n.save')}}</el-button>
    </div>
  </div>
</template>

<script>
import { addFullCutActivityApi, updateFullCut, memberCardActivityName, getOneFullCutActivityInfo } from '@/api/admin/marketManage/fullDiscountFullCut'
import ChoosingGoods from '@/components/admin/choosingGoods'
import AddingBusClassDialog from '@/components/admin/addingBusClassDialog'
import AddBrandDialog from '@/components/admin/addBrandDialog'
import SelectGoodsLabel from '@/components/admin/addProductLabel'

export default {
  components: { ChoosingGoods, AddingBusClassDialog, AddBrandDialog, SelectGoodsLabel },
  data () {
    let that = this
    // 自定义校验规则
    var validateLevel = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (!value) {
        callback(new Error(that.$t('fullCuti18n.fdfr')))
      } else if (!re.test(value)) {
        callback(new Error(that.$t('fullCuti18n.actLevInteger')))
      }
      callback()
    }
    var validateActType = (rule, value, callback) => {
      if (value === '1' && (that.goodsList.length === 0 && that.goodsNameList.length === 0 && that.labelNameList.length === 0 && that.bussinessList.length === 0 && that.platformList.length === 0)) {
        callback(new Error(that.$t('fullCuti18n.pslProductInfo')))
      } else {
        callback()
      }
    }
    function validateCheckCard (rule, value, callback) {
      if (that.checkCard) {
        if (that.memberCardInfo === '' || (that.memberCardInfo && that.memberCardInfo.length === 0)) {
          callback(new Error(that.$t('fullCuti18n.pslexcMC')))
        }
        callback()
      }
    }
    function validateConditionAdd (rule, value, callback) {
      let conditionAddParams = that['conditionAddParams' + that.params.type]
      if (!conditionAddParams || conditionAddParams.length <= 0) {
        callback(new Error(that.$t('fullCuti18n.psetRule')))
      }
      conditionAddParams.forEach(item => {
        if (!((item.reduceMoney && item.fullMoney) || (item.amount && item.reduceMoney) || (item.fullMoney && item.discount) || (item.amount && item.discount))) {
          callback(new Error(that.$t('fullCuti18n.ppRule')))
        }
      })
      callback()
    }
    return {
      params: {
        actName: '',
        strategyPriority: '',
        id: '',
        type: '2',
        condition: [],
        timeInterval: [],
        startTime: '',
        endTime: '',
        status: '',
        recommendGoods: null,
        memberCards: null,
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: '',
        actType: '0'
      },
      activeName: '5',
      fullDeduction: '1',
      aaa: 11,
      memberCardInfo: '',
      memberCardNameList: [], // 会员专享活动
      memberCardNameIdLidt: [],
      cardId: [], // 会员专享改变时的id值
      flag: null,
      conditionAddParams: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 满x元减x元
      conditionAddParams1: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 满x件减x元
      conditionAddParams2: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 满x元打x折
      conditionAddParams3: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 满x件打x折
      conditionAddParams4: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 选择商品
      tuneUpChooseGoodsDialog: false,
      isOnlyShowChooseGoods: false,
      selectedGoodsIdList: [],
      goodsList: [],
      // 选择商品品牌
      tuneUpBrandDialog: false,
      goodsNameList: [],
      goodsBrandIdList: [],
      // 选择商品标签
      tuneUpSelectGoodsLabelDialog: false,
      labelNameList: [],
      labelNameIdList: [],
      // 选择商家分类
      tuneUpBussDialog: false,
      bussinessList: [],
      bussinessIdList: [],
      // 选择平台分类
      tuneUpPlatformDialog: false,
      platformList: [],
      platformIdList: [],
      checkCard: false, // 是否选择会员卡

      rules: {
        actName: [{ required: true, message: that.$t('fullCuti18n.peActName'), trigger: 'blur' }],
        strategyPriority: { required: true, validator: validateLevel, trigger: 'blur' },
        type: [{ required: true, message: that.$t('fullCuti18n.pslActType'), trigger: 'change' }],
        timeInterval: [{ required: true, message: that.$t('fullCuti18n.pslTime'), trigger: 'blur' }],
        actType: { required: true, validator: validateActType, trigger: 'change' },
        conditionAddParams: [{ required: true, validator: validateConditionAdd, trigger: 'blur' }],
        memberCards: [{ validator: validateCheckCard, trigger: 'change' }]
      }
    }
  },
  watch: {
    'params.timeInterval': function (newVal) {
      this.params.startTime = newVal[0]
      this.params.endTime = newVal[1]
    },
    // 监听满金额满件数切换
    fullDeduction: function (newVal) {
      let type = this.params.type
      this['conditionAddParams' + type] = [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }]
    }
  },
  mounted () {
    this.memberCardActivityName()
    console.log(this.$route.query, 'this.$router.id')
    if (this.$route.query.id > 0) {
      this.params.id = this.$route.query.id
      this.fetchCurrentActivityData()
    }
  },
  methods: {
    handleClick (tab) {
      console.log(tab, 'tabChange')
      this.$nextTick(() => {
        if (tab.index !== '5') {
          this.$router.push({
            path: '/admin/home/main/fullDiscountFullCut',
            query: {
              tabIndex: tab.index
            }
          })
        }
      })
    },
    memberCardActivityName () {
      memberCardActivityName().then(res => {
        console.log(res, 'memberCardActivityName')
        this.memberCardNameList = res.content
        this.memberCardNameIdLidt = res.content.map(item => item.id)
        console.log(this.memberCardNameIdLidt, 'memberCardNameIdLidt')
      })
    },
    getMemberCardName (val) {
      console.log(val, 'get value')
      this.cardId = val
      console.log(this.cardId, 'saveMemberIdList')
    },
    getTabName () {
      if (this.$route.query.id) {
        return this.$t('fullCuti18n.mAct')
      } else {
        return this.$t('fullCuti18n.addfrAct')
      }
    },
    // 同步满折满减规则
    syncConditionAddParams () {
      let type = this.params.type
      let conditionAddParams = this['conditionAddParams' + type]
      this.conditionAddParams = conditionAddParams.filter((item, index) => {
        return (item.reduceMoney && item.fullMoney) || (item.amount && item.reduceMoney) || (item.fullMoney && item.discount) || (item.amount && item.discount)
      })
    },
    // 提交
    submit () {
      let that = this
      if (that.params.id) {
        // 更新满折满减活动
        let obj = {
          id: that.params.id,
          actName: that.params.actName,
          strategyPriority: that.params.strategyPriority
        }
        updateFullCut(obj).then(res => {
          console.log(res)
          if (res.error === 0) {
            that.$message.success(that.$t('fullCuti18n.updateS'))
            that.$router.replace('/admin/home/main/fullDiscountFullCut')
          } else {
            that.$message.warning(that.$t('fullCuti18n.updateF'))
          }
        })
      } else {
        // 校验
        that.$refs.fullCutAddForm.validate((valid) => {
          if (valid) {
            that.syncConditionAddParams()
            if (this.conditionAddParams.length === 0) {
              this.$message.warning(that.$t('fullCuti18n.psetRule'))
              return false
            }
            let obj = {
              actName: this.params.actName,
              type: this.params.type,
              actType: this.params.actType,
              conditionAddParams: this.conditionAddParams,
              startTime: this.params.startTime,
              endTime: this.params.endTime,
              strategyPriority: this.params.strategyPriority, // 优先级
              recommendGoodsId: String(this.selectedGoodsIdList), // 指定商品
              recommendCatId: String(this.platformIdList), // 指定平台
              recommendSortId: String(this.bussinessIdList), // 指定商家
              recommendBrandId: String(this.goodsBrandIdList), // 指定品牌
              cardId: String(this.cardId) // 会员专享活动
            }
            console.log(obj)
            // 添加满折满减活动
            addFullCutActivityApi(obj).then(res => {
              console.log(res)
              if (res.error === 0) {
                that.$message.success(that.$t('fullCuti18n.saveS'))
                that.$router.replace('/admin/home/main/fullDiscountFullCut')
              } else {
                that.$message.warning(that.$t('fullCuti18n.saveF'))
              }
            })
          }
        })
      }
    },
    // 添加满减：满X元减X元
    addFullCutItemA () {
      let obj = {
        'fullMoney': '',
        'reduceMoney': ''
      }
      this.conditionAddParams2.push(obj)
    },
    // 添加满减：满X元减X元
    deleteFullCutItemA (index) {
      if (index <= 0) return
      this.conditionAddParams2.splice(index, 1)
    },
    // 添加满减：满X件减X元
    addFullCutItemB () {
      let obj = {
        'amount': '',
        'reduceMoney': ''
      }
      this.conditionAddParams2.push(obj)
    },
    // 删除满减：满X件减X元
    deleteFullCutItemB (index) {
      if (index <= 0) return
      this.conditionAddParams2.splice(index, 1)
    },
    // 添加满折：满元打折
    addFullCutItemC () {
      let obj = {
        'fullMoney': '',
        'discount': ''
      }
      this.conditionAddParams3.push(obj)
    },
    // 删除满折：满元打折
    deleteFullCutItemC (index) {
      this.conditionAddParams3.splice(index, 1)
    },
    // 添加满折：满件打折
    addFullCutItemD () {
      let obj = {
        'amount': '',
        'discount': ''
      }
      this.conditionAddParams3.push(obj)
    },
    // 删除满折：满件打折
    deleteFullCutItemD (index) {
      this.conditionAddParams3.splice(index, 1)
    },
    // 选择商品数据处理
    chooseGoodsHandler () {
      this.isOnlyShowChooseGoods = false
      this.tuneUpChooseGoodsDialog = !this.tuneUpChooseGoodsDialog
    },
    // 选择商品数据处理-部分
    onlyChooseGoodsHandler () {
      if (this.params.id) {
        return false
      }
      this.isOnlyShowChooseGoods = true
      this.tuneUpChooseGoodsDialog = !this.tuneUpChooseGoodsDialog
    },
    returnGoodsData (val) {
      console.log(val, 'goodsInfo')
      this.goodsList = val
      this.selectedGoodsIdList = val.map(item => item.goodsId)
    },
    deleteGoods (index) {
      console.log(index)
      this.goodsList.splice(index, 1)
      this.selectedGoodsIdList.splice(index, 1)
    },

    // 选择平台、商家分类数据处理
    selectPlatform () {
      this.tuneUpPlatformDialog = !this.tuneUpPlatformDialog
    },
    // 选择平台分类回调
    returnPlateformData (val) {
      console.log(val, 'platform data')
      this.platformList = val
      this.platformIdList = val.map(item => item.catId)
    },
    editPlateformClassification () {
      this.tuneUpPlatformDialog = !this.tuneUpPlatformDialog
    },
    deletePlateformClassfication () {
      this.platformList = []
      this.platformIdList = []
    },

    selectBussiness () {
      this.tuneUpBussDialog = !this.tuneUpBussDialog
    },
    // 商家分类弹窗回调
    returnBusinessData (val) {
      console.log(val, 'buiness data--')
      this.bussinessList = val
      this.bussinessIdList = val.map(item => item.sortId)
    },
    editBussinessClassification () {
      this.tuneUpBussDialog = !this.tuneUpBussDialog
    },
    deleteBussinessClassfication () {
      this.bussinessList = []
      this.bussinessIdList = []
    },

    // 选择商品品牌数据处理
    seclectGoodsBrand () {
      this.tuneUpBrandDialog = !this.tuneUpBrandDialog
    },
    returnGoodsBrandData (val) {
      this.goodsNameList = val
      let idList = val.map(item => item.id)
      this.goodsBrandIdList = idList
    },
    editSelectedBrand () {
      this.tuneUpBrandDialog = !this.tuneUpBrandDialog
    },
    deleteSelectedBrand () {
      this.goodsNameList = []
      this.idList = []
    },

    // 选择商品标签数据处理
    selectGoodsLabel () {
      this.tuneUpSelectGoodsLabelDialog = true
    },
    returnGoodsLabelData (val) {
      console.log(val, 'label id')
      this.labelNameList = val
      let labelIdList = val.map(item => item.id)
      this.labelNameIdList = labelIdList
    },
    editSelectedLabel () {
      this.tuneUpSelectGoodsLabelDialog = !this.tuneUpSelectGoodsLabelDialog
    },
    deleteSelectedLabel () {
      this.labelNameList = []
      this.labelNameIdList = []
    },
    // 初始化数据
    fetchCurrentActivityData () {
      let that = this
      getOneFullCutActivityInfo({ id: this.params.id }).then(res => {
        if (res.error === 0) {
          let content = res.content
          that.params = Object.assign({}, that.params, content)
          // 优惠规则
          let type = content.type
          that.params.type = String(type)
          content.condition.forEach(obj => {
            for (const key in obj) {
              if (obj.hasOwnProperty(key)) {
                const value = obj[key]
                if (!value) {
                  obj[key] = ''
                }
              }
            }
          })
          if (content.condition[0].fullMoney) {
            that.fullDeduction = '1'
          } else if (content.condition[0].amount) {
            that.fullDeduction = '2'
          }
          // 给fullDeduction赋值会触发watch
          that.$nextTick(() => {
            that.conditionAddParams = content.condition || [{
              fullMoney: '',
              reduceMoney: '',
              amount: '',
              discount: ''
            }]
            that['conditionAddParams' + type] = content.condition || [{
              fullMoney: '',
              reduceMoney: '',
              amount: '',
              discount: ''
            }]
          })
          // 有效期
          that.params.timeInterval = [content.startTime, content.endTime]
          // 活动范围类型
          that.params.actType = String(content.actType)
          // 指定商品
          that.selectedGoodsIdList = content.recommendGoodsIds || []
          that.goodsList = content.recommendGoods || []
          // 指定平台分类
          that.platformList = content.recommendCat || []
          that.platformIdList = content.recommendCatIds || []
          // 指定商家分类
          that.bussinessList = content.recommendSort || []
          that.bussinessIdList = content.recommendSortIds || []
          // 指定商品标签
          // 指定商品品牌
          that.goodsNameList = content.recommendBrand || []
          that.goodsBrandIdList = content.recommendBrandIds || []
          // 指定会员卡
          that.memberCardInfo = content.cardIds || []
          if (content.cardIds && content.cardIds.length > 0) {
            that.checkCard = true
          }
        }
      }).catch(err => console.log(err))
    }
  }
}

</script>
<style lang="scss" scoped>
.addActivity {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .flex-block {
    display: flex;
    margin-left: 25px;
  }
  .edit-icon {
    cursor: pointer;
  }
  .first_cat {
    border: 1px solid #b9d2ff !important;
    background-color: #f0f5ff !important;
  }
  .second_cat {
    margin-left: 15px;
    border: 1px solid #ffe2b8 !important;
    background-color: #fffaf2 !important;
  }
  .third_cat {
    margin-left: 15px;
    border: 1px solid #ffc0cc !important;
    background-color: #fff6f8 !important;
  }
  .link {
    color: #409eff;
    text-decoration: none;
  }
  .addActivity_main {
    background-color: #fff;
    padding: 15px;
    margin-bottom: 55px;
    .full_cut_content {
      .form_input {
        width: 185px;
      }
      .form_tip {
        display: inline;
        color: #999;
        margin-left: 20px;
      }
      .actTypeSaveTips {
        margin-left: 50px;
      }
      .iconAdd {
        margin: 3px 0 0 10px;
        cursor: pointer;
      }
      .add_goods_btn {
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5a8bff;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin: 20px 0;
      }
      .goods_area {
        max-width: 528px;
        min-height: 100px;
        padding-right: 10px;
        overflow-x: hidden !important;
        .goods_table {
          border: 1px solid #ddd;
          thead tr {
            background: #f8f8f8;
            font-weight: bold;
            color: #333;
            text-align: center;
            th {
              border: 1px solid #ddd;
            }
          }
          tbody tr {
            text-align: center;
            td {
              padding: 10px;
              border: 1px solid #ddd;
              .goods_info {
                display: flex;
                .goods_img {
                  width: 40px;
                  height: 40px;
                  margin-right: 10px;
                  img {
                    width: 100%;
                    height: 100%;
                  }
                }
              }
            }
          }
        }
      }
      .goods_area::-webkit-scrollbar {
        width: 4px;
        height: 4px;
      }
      .goods_area::-webkit-scrollbar-track {
        background: #fff;
        border-radius: 2px;
      }
      .goods_area::-webkit-scrollbar-thumb {
        background: #dddddd;
        border-radius: 2px;
      }
      .goods_area::-webkit-scrollbar-thumb:hover {
        background: #747474;
      }
      .goods_area::-webkit-scrollbar-corner {
        background: #fff;
      }
      .platformClassification {
        max-width: 528px;
        border: 1px solid #ddd;
        thead tr {
          background: #f8f8f8;
        }
      }
      .brand_table {
        width: 528px;
        margin-bottom: 10px;
        border: 1px solid #ddd;
        background: #fff;
        th {
          padding: 10px 5px;
          text-align: center;
          background-color: #f8f8f8;
          line-height: 20px;
          .operate {
            margin-right: 6px;
            float: right;
            span {
              cursor: pointer;
            }
            .edit {
              margin-right: 10px;
            }
          }
        }
        .exampleWrapper {
          line-height: 50px;
          height: 50px;
          border-bottom: 1px solid #eee;
          .example {
            margin-left: 20px;
          }
          span {
            padding: 4px 10px;
            border-radius: 20px;
            color: #666;
          }
        }
        .goodsName {
          display: inline-block;
          span {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 5px;
            margin-left: 10px;
            display: inline-block;
            line-height: 20px;
          }
        }
        .bussinessGoodsName {
          display: inline-block;
          padding: 0 0 16px 20px;
          span {
            display: inline-block;
            padding: 4px 10px;
            margin: 16px 10px 0 0;
            border-radius: 20px;
            line-height: 35px;
            color: #666;
            border: 1px solid #b9d2ff;
            background-color: #f0f5ff;
          }
        }
      }
    }
  }
  .save_button {
    position: fixed;
    bottom: 0;
    right: 27px;
    left: 160px;
    text-align: center;
    background: #fff;
    padding: 10px;
  }
}
</style>
