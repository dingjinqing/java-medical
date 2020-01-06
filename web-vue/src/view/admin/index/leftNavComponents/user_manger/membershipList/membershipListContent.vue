<template>
  <div class="membershioListContent">
    <div class="brandManagementContent_main">
      <ul>
        <li class="li">
          <div class="liNav">
            <div class="phoneClass">{{$t('membershipIntroduction.phoneNum')}}：</div>
            <el-input
              v-model="phoneNum"
              :placeholder="$t('membershipIntroduction.placePhoneNum')"
              size="small"
            ></el-input>
          </div>
          <div
            class="liNav"
            :class="memberListliNav"
          >
            <span>{{$t('membershipIntroduction.wechatNickname')}}：</span>
            <el-input
              v-model="vxName"
              :placeholder="$t('membershipIntroduction.placeWXNameNum')"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span>{{$t('membershipIntroduction.source')}}：</span>
            <el-select
              v-model="sourceValue"
              :placeholder="$t('membershipIntroduction.placeChoise')"
              size="small"
            >
              <el-option
                v-for="(item,index) in sourceOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div
            class="liNav"
            :class="memberListliLast"
          >
            <span>{{$t('membershipIntroduction.membershipCard')}}：</span>
            <el-select
              v-model="membershipCardVal"
              :placeholder="$t('membershipIntroduction.placeChoise')"
              size="small"
            >
              <el-option
                v-for="(item,index) in membershipCardOptions"
                :key="index"
                :label="item.cardName"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
        </li>
      </ul>
      <ul class="uls">
        <li>
          <div
            class="liNav"
            :class="specielNav"
          >
            <span
              :class="minixLabel"
              class="labelClass"
            >{{$t('membershipIntroduction.label')}}：</span>
            <el-autocomplete
              v-model="labelVal"
              :placeholder="$t('membershipIntroduction.placeinpuLabel')"
              :fetch-suggestions="querySearch"
              size="small"
              @select="handleSelect"
            ></el-autocomplete>
          </div>
        </li>
        <li>
          <div
            class="liNav date"
            :class="specialliNavTwo"
          >
            <span>{{$t('membershipIntroduction.registrationTime')}}：</span>
            <el-date-picker
              v-model="datePickerVal"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
              value-format='yyyy-MM-dd HH:mm:ss'
              :default-time="['00:00:00','23:59:59']"
              size="small"
            >
            </el-date-picker>
          </div>
        </li>
        <li>
          <div
            class="liNav"
            :class="memberListliLast"
          >
            <span>{{$t('membershipIntroduction.inviter')}}：</span>
            <el-input
              v-model="inviteUserName"
              :placeholder="$t('membershipIntroduction.coverName')"
              size="small"
            ></el-input>
          </div>
        </li>
      </ul>
      <ul
        class="hiddenUl"
        v-if='!arrorFlag'
      >
        <li>
          <div>
            <span>{{$t('membershipIntroduction.reacord')}}：</span>
            <el-date-picker
              v-model="datePickerVal_one"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.startdata')"
              :end-placeholder="$t('membershipIntroduction.enddate')"
              value-format='yyyy-MM-dd HH:mm:ss'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="hiddenRight">
            <span>{{$t('membershipIntroduction.PassengerUnitPrice')}}：</span>
            <el-input
              v-model="unitPriceLeft"
              :placeholder="$t('membershipIntroduction.Pleasecontent')"
              size="small"
            ></el-input>
            <i>{{$t('membershipIntroduction.to')}}</i>
            <el-input
              v-model="unitPriceRight"
              :placeholder="$t('membershipIntroduction.Pleasecontent')"
              size="small"
            ></el-input>
          </div>
        </li>
        <li>
          <div>
            <span>{{$t('membershipIntroduction.behavior')}}：</span>
            <el-date-picker
              v-model="datePickerVal_two"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.startdata')"
              :end-placeholder="$t('membershipIntroduction.enddate')"
              value-format='yyyy-MM-dd HH:mm:ss'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="hiddenRight">
            <span>{{$t('membershipIntroduction.purchasetimes')}}：</span>
            <el-input
              v-model="frequencyLeft"
              :placeholder="$t('membershipIntroduction.Pleasecontent')"
              size="small"
            ></el-input>
            <i>{{$t('membershipIntroduction.to')}}</i>
            <el-input
              v-model="frequencyRight"
              :placeholder="$t('membershipIntroduction.Pleasecontent')"
              size="small"
            ></el-input>
          </div>
        </li>
        <li class="specialLi">
          <div>
            <span>{{$t('membershipIntroduction.transaction')}}：</span>
            <el-date-picker
              v-model="datePickerVal_three"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.startdata')"
              :end-placeholder="$t('membershipIntroduction.enddate')"
              value-format='yyyy-MM-dd HH:mm:ss'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="brand_title">
            <span class="nameClass">{{$t('membershipIntroduction.designatedgoods')}}：</span>
            <div
              class="choiseDivClass"
              @click="handleClickChoiseGood()"
            >
              <img :src="choiseGoodImgUrl">
              {{$t('membershipIntroduction.choiseGoods')}}
            </div>
          </div>
        </li>
      </ul>
      <ul class="ulsThree">
        <li>
          <el-checkbox v-model="checkPhone">{{$t('membershipIntroduction.phoneNum')}}</el-checkbox>
          <el-checkbox v-model="checkIntegr">{{$t('membershipIntroduction.integral')}}</el-checkbox>
          <el-checkbox v-model="balance">{{$t('membershipIntroduction.Balance')}}</el-checkbox>
          <el-checkbox v-model="membershipCard">{{$t('membershipIntroduction.membershipCard')}}</el-checkbox>
          <el-checkbox v-model="noLanding">{{$t('membershipIntroduction.banLogin')}}</el-checkbox>
          <el-checkbox v-model="importMembership">{{$t('membershipIntroduction.importMembers')}}</el-checkbox>
        </li>
        <li>
          <el-button
            type="primary"
            size="small"
            @click="handleScreen()"
          >{{$t('membershipIntroduction.screen')}}</el-button>
          &nbsp;
          <el-button
            type="info"
            size="small"
            plain
          >{{$t('membershipIntroduction.membershipExport')}}</el-button>
        </li>
      </ul>

      <ul class="arrowUl">
        <li>
          <div @click="handleToChangeArror()">
            <div
              v-if="arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >{{$t('membershipIntroduction.more')}}&nbsp;<img :src="ArrowArr[0].img_1"></div>
            <div
              v-if="!arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >{{$t('membershipIntroduction.retract')}}&nbsp;<img :src="ArrowArr[1].img_2"></div>
          </div>
        </li>
      </ul>
    </div>

    <!--底部表格-->
    <div class="brandManagementContent_bottom">
      <div class="table_container">
        <table width='100%'>
          <thead>
            <tr>
              <td style="width:8%">ID</td>
              <td style="width:10%">{{$t('membershipIntroduction.nickname')}}</td>
              <td style="width:8%">{{$t('membershipIntroduction.phoneNum')}}</td>
              <td style="width:8%">{{$t('membershipIntroduction.inviter')}}</td>
              <td style="width:7%">{{$t('membershipIntroduction.Balance')}}</td>
              <td style="width:7%">{{$t('membershipIntroduction.integral')}}</td>
              <td style="width:8%">{{$t('membershipIntroduction.membershipCard')}}</td>
              <td style="width:7%">{{$t('membershipIntroduction.source')}}</td>
              <td style="width:10%">{{$t('membershipIntroduction.registrationTime')}}</td>
              <td style="width:15%">{{$t('membershipIntroduction.operation')}}</td>
            </tr>
          </thead>
          <tbody v-if="tbodyFlag">
            <tr
              v-for="(item,index) in trList"
              :key="index"
            >
              <td>
                <div class="tdCenter sp_">
                  <el-checkbox
                    @change='handleClick()'
                    v-model="item.ischecked"
                  ></el-checkbox>
                  <span>{{item.userId}}</span>
                </div>

              </td>
              <td :class="isCenterFlag?'tdCenter':''" style="width: 150px;">
                <span
                  @click="hanldeToDetail(item.userId)"
                  style="color: #5A8BFF;cursor:pointer;width: 100px;word-break: break-all;display:inline-block;"
                >{{item.userName}}</span>

              </td>
              <td class="tb_decorate_a">
                {{item.mobile}}
              </td>
              <td class="tb_decorate_a">
                {{item.inviteUserName}}
              </td>
              <td class="tb_decorate_a">
                <div class="mAccountDiv">
                  <span class="plusSpan">{{item.account}}</span>
                  <img
                    @click="handlebalanceDialog(0,item.account,item.userId)"
                    :src="plusImg"
                  >
                </div>
              </td>
              <td class="tb_decorate_a">
                <div class="mScoreDiv">
                  <span class="plusSpan">{{item.score}}</span>
                  <img
                    @click="handlebalanceDialog(1,item.score,item.userId)"
                    :src="plusImg"
                  >
                </div>
              </td>
              <td class="tb_decorate_a">
                <div class="member">
                  <span style="text-align: left;line-height: 20px; margin-right: 5px;">{{item.cardName}}</span>
                  <div>
                    <span style="margin-top: 5px;" @click="handleSetUp(item.userId)">{{$t('membershipIntroduction.setup')}}</span>
                    <span
                      @click="handleToTurnMore('receiveDetail',item.userName,item.userId)"
                      style="margin-top:8px;margin-bottom: 0px;"
                    >{{$t('membershipIntroduction.more')}}</span>
                  </div>
                </div>
              </td>
              <td class="tb_decorate_a">
                {{item.sourceName}}
              </td>
              <td class="tb_decorate_a">
                {{item.createTime}}

              </td>
              <td class="tb_decorate_a">
                <div class="lastDiv">
                  <span @click="handleToTurnMore('balanceDetail',item.userName,item.userId)">{{$t('membershipIntroduction.Balancedetails')}}</span>
                  <span @click="handleToTurnMore('integralDetail',item.userName,item.userId)">{{$t('membershipIntroduction.Integraldetails')}}</span>

                  <span
                    v-if="item.delFlag == 0"
                    @click="handleNoLanding(item.userId,1)"
                  >{{$t('membershipIntroduction.Nolanding')}}</span>
                  <span
                    v-else-if="item.delFlag == 1"
                    @click="handleNoLanding(item.userId,0)"
                  >{{$t('membershipIntroduction.ResumeLogin')}}</span>
                </div>
                <div
                  class="lastDiv"
                  style="margin-top:5px"
                >
                  <span @click="handleToLabel(item.userId)">{{$t('membershipIntroduction.Labeling')}}</span>
                  <span @click="hanldeToDetail(item.userId)">{{$t('membershipIntroduction.Seedetails')}}</span>
                </div>
              </td>
            </tr>
          </tbody>

        </table>
        <div
          class="noData"
          v-if="!tbodyFlag"
        >
          <img :src="noImg">
          <span>暂无相关数据</span>
        </div>
        <!--表格底部-->
        <div class="tableFooter" style="height: 30px;">
          <div class="footer_t">

            <el-checkbox
              v-model="allChecked"
              @change="handleAllcheck()"
            >{{$t('membershipIntroduction.Allelection')}}</el-checkbox>
            <div
              class="bottom_select_one"
              :class="minx_bottom_select"
            >
              <el-select
                v-model="value_one"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(0)"
              >
                <el-option
                  v-for="(item,index) in options_one"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 5px">
              <el-select
                v-model="value_two"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(1)"
              >
                <el-option
                  v-for="(item,index) in options_two"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 5px">
              <el-select
                v-model="value_three"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(2)"
              >
                <el-option
                  v-for="(item,index) in options_three"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 5px">
              <el-select
                v-model="value_four"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(3)"
              >
                <el-option
                  v-for="(item,index) in options_four"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 5px">
              <el-select
                v-model="value_five"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(4)"
              >
                <el-option
                  v-for="(item,index) in options_five"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
          </div>
        </div>
        <div class="tableFooter">
          <div class="footer_b">
            <span>{{$t('membershipIntroduction.Currentpage')}}{{this.currentPage3}}/{{this.pageCount}}，{{$t('membershipIntroduction.TotalRecords')}}{{this.totalNum}}{{$t('membershipIntroduction.strip')}}</span>
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage3"
              :page-size="20"
              layout="prev, pager, next, jumper"
              :total="totalNum"
            >
            </el-pagination>
          </div>
        </div>
      </div>
    </div>
    <!-- 选择商品弹窗 -->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="chooseGoodsBack"
    />
    <!--修改余额&修改积分弹窗-->
    <div class="balanceDialo">
      <ModifyData
        :model="modifyDialogData"
        :userId="userId"
        @submitRes="hanldeModifyData"
        >
        </ModifyData>
    </div>
    <!--设置会员卡弹窗-->
    <div class="balanceDialo setUpDialog">
      <el-dialog
        title="设置会员卡"
        :visible.sync="setUpDialogVisible"
        width="31%"
        :modal-append-to-body="false"
      >
        <div class="setUpDialogDiv">
          <p>你可以在这里编辑该会员的会员卡信息，添加/删除会员卡，注意：需要激活的会员卡直接发放到用户</p>
          <div
            class="setUpDialogMain"
            style="overflow-y: auto;height: 500px"
          >
            <div class="setUpcommon">
              <span>普通会员卡</span>
              <div class="setUpContainer">
                <div
                  class="hy_common selectDataClass"
                  v-for="(item,index) in setUpSelectVal_one"
                  :key="index"
                >
                  <div>
                    {{item.text}}
                  </div>
                  <img
                    style="cursor:pointer"
                    @click="handleReadd(0,index)"
                    src="../../../../../../assets/adminImg/hy_icon_delete.png"
                  >
                </div>

                <div
                  class="hy_common"
                  :class="setUpFalg_1?'':'hyHiddenClass'"
                  v-if="!setUpFalg_1"
                >
                  <div>
                    <el-select
                      v-model="setUpValOne"
                      :placeholder="$t('membershipIntroduction.placeChoise')"
                      size="mini"
                      @change="handleSetUpSelect(0)"
                    >
                      <el-option
                        v-for="(item,index) in setUpoptionsOne"
                        :key="index"
                        :label="item.cardName"
                        :value="item"
                        :disabled="item.disabled"
                      >
                      </el-option>
                    </el-select>
                  </div>
                  <img
                    @click="handleReadd(0,-1)"
                    style="cursor:pointer"
                    src="../../../../../../assets/adminImg/hy_icon_delete.png"
                  >
                </div>
                <div
                  class="hy_common"
                  v-if="setUpFalg_1"
                >
                  <span
                    class="setUpAdd"
                    @click="handleToChangeSetUpAdd(0)"
                  >添加</span>
                </div>
              </div>

            </div>
            <div class="setUpcommon">
              <span>限次会员卡</span>
              <div class="setUpContainer">
                <div
                  class="hy_common selectDataClass"
                  v-for="(item,index) in setUpSelectVal_two"
                  :key="index"
                >
                  <div>
                    {{item.text}}
                  </div>
                  <img
                    style="cursor:pointer"
                    @click="handleReadd(1,index)"
                    src="../../../../../../assets/adminImg/hy_icon_delete.png"
                  >
                </div>

                <div
                  class="hy_common"
                  :class="setUpFalg_2?'':'hyHiddenClass'"
                  v-if="!setUpFalg_2"
                >
                  <div>
                    <el-select
                      v-model="setUpValTwo"
                      :placeholder="$t('membershipIntroduction.placeChoise')"
                      size="mini"
                      @change="handleSetUpSelect(1)"
                    >
                      <el-option
                        v-for="(item,index) in setUpoptionsTwo"
                        :key="index"
                        :label="item.cardName"
                        :value="item"
                      >
                      </el-option>
                    </el-select>
                  </div>
                  <img
                    @click="handleReadd(1,-1)"
                    style="cursor:pointer"
                    src="../../../../../../assets/adminImg/hy_icon_delete.png"
                  >
                </div>
                <div
                  class="hy_common"
                  v-if="setUpFalg_2"
                >
                  <span
                    class="setUpAdd"
                    @click="handleToChangeSetUpAdd(1)"
                  >添加</span>
                </div>
              </div>

            </div>
            <div class="setUpcommon setUpBottomDiv">
              <span>等级会员卡</span>
              <div class="setUpContainer">
                <div
                  class="hy_common selectDataClass"
                  v-for="(item,index) in setUpSelectVal_three"
                  :key="index"
                >
                  <div>
                    {{item.text}}
                  </div>
                  <img
                    style="cursor:pointer"
                    @click="handleReadd(2,index)"
                    src="../../../../../../assets/adminImg/hy_icon_delete.png"
                  >
                </div>

                <div
                  class="hy_common"
                  :class="setUpFalg_3?'':'hyHiddenClass'"
                  v-if="!setUpFalg_3"
                >
                  <div>
                    <el-select
                      v-model="setUpValThree"
                      :placeholder="$t('membershipIntroduction.placeChoise')"
                      size="mini"
                      @change="handleSetUpSelect(2)"
                    >
                      <el-option
                        v-for="(item,index) in setUpoptionsThree"
                        :key="index"
                        :label="item.cardName"
                        :value="item"
                        :disabled="item.disabled"
                      >
                      </el-option>
                    </el-select>
                  </div>
                  <img
                    @click="handleReadd(2,-1)"
                    style="cursor:pointer"
                    src="../../../../../../assets/adminImg/hy_icon_delete.png"
                  >
                </div>
                <div
                  class="hy_common"
                  v-if="setUpFalg_3"
                >
                  <span
                    class="setUpAdd"
                    @click="handleToChangeSetUpAdd(2)"
                  >添加</span>
                </div>
              </div>

            </div>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="setUpDialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="setUpCardForMember()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--禁止登陆弹窗-->
    <div class="balanceDialo">

      <!-- 禁止登录弹窗 -->
      <el-dialog
        :title="$t('membershipIntroduction.Nolanding')"
        :visible.sync="noLandingDialogVisible"
        width="40%"
        :modal-append-to-body="false"
      >
        <div
          class="balanceDialogDiv"
          style="margin-bottom:30px"
        >
          <span style="color:#f66">{{ $t('membershipIntroduction.prompt') }}：</span>
          <span>{{ $t('membershipIntroduction.NoLandingPrompt') }}</span>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="noLandingDialogVisible = false"
          >{{ $t('membershipIntroduction.cancel') }}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="changeLoginStatus"
          >{{ $t('membershipIntroduction.centain') }}</el-button>
        </span>
      </el-dialog>
      <!-- 恢复登录弹窗 -->
      <el-dialog
        :title="$t('membershipIntroduction.ResumeLogin')"
        :visible.sync="resumeLoginVisible"
        width="40%"
        :modal-append-to-body="false"
      >
        <div
          class="balanceDialogDiv"
          style="margin-bottom:30px"
        >
          <span style="color:#f66">{{ $t('membershipIntroduction.prompt') }}：</span>
          <span>{{ $t('membershipIntroduction.ResumeLoginPrompt') }}</span>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="resumeLoginVisible = false"
          >{{ $t('membershipIntroduction.cancel') }}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="changeLoginStatus"
          >{{ $t('membershipIntroduction.centain') }}</el-button>
        </span>
      </el-dialog>
    </div>
    <!--打标签-->
    <div class="balanceDialo">
      <el-dialog
        title="标签"
        :visible.sync="labelDialogVisible"
        width="25%"
        :modal-append-to-body="false"
      >
        <div
          class="balanceDialogDiv"
          style="margin-bottom:30px"
        >
          <span style="line-height:15px;font-size:12px;color:#a3a3a3;display:block;margin-bottom:10px">一个用户最多可以打5个标签，超过数量的标签将不再被添加给该用户</span>
          <el-select
            filterable
            v-model="labelDialogInput"
            multiple
            size="small"
            :placeholder="$t('membershipIntroduction.placeChoise')"
          >
            <el-option
              v-for="(item,index) in tagSource"
              :key="index"
              :label="item.value"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="labelDialogVisible = false"
          >{{ $t('membershipIntroduction.cancel') }}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="setTagForMember"
          >{{ $t('membershipIntroduction.centain') }}</el-button>
        </span>
      </el-dialog>
    </div>
    <SetUpMemCDialog />
    <!--选择用户弹窗-->
    <SelectingUsersDialog />
    <!--删除选中人弹窗-->
    <el-dialog
      title="提醒"
      :visible.sync="deletePersondialogVisible"
      width="30%"
    >
      <span>{{deletePersonText}}</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="deletePersondialogVisible = false"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="deletePersondialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { membershipListRequest, allUserCardRequest, allSourceRequest, allTagRequest, getTagForMemberRequest, setTagForMemberRequest, loginStatusRequest } from '@/api/admin/membershipList.js'
import { getAllMemberCardByClassRequest, setCardForMemberRequest, getAllAvailableMemberCardRequest } from '@/api/admin/memberManage/memberCard.js'
import { mapActions } from 'vuex'
import ChoosingGoods from '@/components/admin/choosingGoods'
import SetUpMemCDialog from '@/view/admin/index/leftNavComponents/user_manger/membershipList/setUpMemCDialog'
import SelectingUsersDialog from '@/view/admin/index/leftNavComponents/user_manger/membershipList/selectingUsersDialog'
import ModifyData from './modifyData'
export default {
  components: { ChoosingGoods, SetUpMemCDialog, SelectingUsersDialog, ModifyData },
  props: ['labelText'],
  data () {
    return {
      modifyDialogData: {
        title: null,
        presentText: null,
        addText: null,
        tips: null,
        bzText: null,
        persentMoney: '',
        index: 0, // 0 余额，1 积分
        visiable: false
      },

      minixLabel: '',
      memberListliNav: '',
      deletePersondialogVisible: false,
      deletePersonText: '',
      phoneNum: '',
      vxName: '',
      inviteUserName: '',
      sourceOptions: [],
      sourceValue: '',
      membershipCardOptions: [],
      noImg: this.$imageHost + '/image/admin/no_data.png',
      membershipCardVal: '',
      labelVal: '',
      datePickerVal: '',
      checkPhone: false,
      checkIntegr: false,
      balance: false,
      membershipCard: false,
      noLanding: false,
      importMembership: false,
      goodsIdsArr: [],
      datePickerVal_one: '',
      datePickerVal_two: '',
      datePickerVal_three: '',
      frequencyLeft: '',
      frequencyRight: '',
      ArrowArr: [
        {
          img_1: this.$imageHost + '/image/admin/show_more.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/hid_some.png'
        }
      ],
      arrorFlag: true,
      unitPriceLeft: '',
      unitPriceRight: '',
      choiseGoodImgUrl: this.$imageHost + '/image/admin/icon_jia.png',
      tbodyFlag: true,
      trList: [

      ],
      clickIindex: '',
      isCenterFlag: '',
      allChecked: false,
      options_one: '',
      options_two: '',
      options_three: '',
      options_four: '',
      options_five: '',
      currentPage3: 1,
      value_one: '0',
      value_two: '0',
      value_three: '0',
      value_four: '0',
      value_five: '0',
      plusImg: this.$imageHost + '/image/admin/add_some.png',
      balanceDialogVisible: false,
      balanceDialogInput: '',
      balanceDialogBottomInput: '',
      addDialogData: [],
      balanceDialogData: '',
      integralDialogData: '',
      allCheckFlag: false,
      setUpDialogVisible: false,
      setUpFalg_1: true,
      setUpValOne: '', // 普通会员卡下拉框每次选中的值
      setUpoptionsOne: [], // 普通会员卡下拉数据
      setUpFalg_2: true,
      setUpValTwo: '',
      setUpoptionsTwo: [], // 限次会员卡下拉数据
      setUpFalg_3: true,
      setUpValThree: '',
      setUpoptionsThree: [], // 等级会员卡下拉数据
      setUpSelectVal_one: [], // 普通会员卡要提交的会员卡列表
      setUpSelectVal_two: [], // 限次会员卡要提交的会员卡列表
      setUpSelectVal_three: [], // 等级会员卡要提交的会员卡列表
      setUpSelectVal_oneTmp: 0, // 普通会员卡要提交的会员卡列表
      setUpSelectVal_twoTmp: 0, // 限次会员卡要提交的会员卡列表
      setUpSelectVal_threeTmp: 0, // 等级会员卡要提交的会员卡列表
      noLandingDialogVisible: false, // 禁止登录弹窗控制
      resumeLoginVisible: false, // 恢复登录弹窗控制
      labelDialogVisible: false,
      labelDialogInput: '',
      totalNum: null,
      pageCount: '',
      userId: null,
      minx_bottom_select: '',
      hitLabeloptions: [],
      memberListliLast: '',
      specielNav: '',
      specialliNavTwo: '',
      tagSource: [],
      tagUserId: '', // 打标签时临时存放的id
      cardUserId: '', // 设置会员卡时临时存放的id
      tuneUpChooseGoods: false,
      singleElection: true,
      chooseGoodsBack: []
    }
  },
  watch: {
    lang () {
      this.balanceDialogData = this.$t('membershipIntroduction.balanceDialogData')
      this.integralDialogData = this.$t('membershipIntroduction.integralDialogData')
      this.sourceOptions = this.$t('membershipIntroduction.userFromSource')
      // 初始化会员列表数据
      this.defaultTabelListData()
      // 初始化会员卡下拉框列表
      this.getAllUserCard()
      // 初始化来源下拉框列表
      this.getAllSource()
      // 初始化标签数据
      this.getAllTag()
    },
    allChecked (newData) {
      if (newData === true) {
        this.trList.map((item, index) => {
          item.ischecked = true
        })
      } else {
        if (this.allCheckFlag === false) {
          this.trList.map((item, index) => {
            item.ischecked = false
          })
        }
      }
    },
    labelDialogInput (newData) {
      if (newData.length === 6) {
        this.labelDialogInput.splice(5, 1)
        this.$message.error(this.$t('membershipIntroduction.tagError'))
      }
    },
    '$store.goodsManagement.state.goodsIds' (newData) {
    }
  },
  created () {
    // console.log(this.$route.params.tagName)
    // this.labelVal = this.$route.params.tagName
    console.log('会员列表 created ')
    // 初始化会员列表数据
    this.defaultTabelListData()
    // 初始化会员卡下拉框列表
    this.getAllUserCard()
    // 初始化标签数据
    this.getAllTag()
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.$http.$on('choseGoodsId', res => {
      this.goodsIdsArr = res
    })
  },
  methods: {
    ...mapActions(['ToTurnMemberShipDetail', 'toHandleSetUpMemDialog', 'toHandleSelectingUsersDialog']),
    // 初始化会员列表数据
    defaultTabelListData () {
      if (this.labelText) {
        this.labelVal = this.labelText
      }
      this.options_one = this.$t('membershipIntroduction.options_one')
      this.options_two = this.$t('membershipIntroduction.options_two')
      this.options_three = this.$t('membershipIntroduction.options_three')
      this.options_four = this.$t('membershipIntroduction.options_four')
      this.options_five = this.$t('membershipIntroduction.options_five')
      let obj = {
        'mobile': String(this.phoneNum).trim(),
        'username': this.vxName,
        'source': this.sourceValue,
        'cardId': this.membershipCardVal,
        'tagName': this.labelVal,
        'createTime': this.datePickerVal[0],
        'endTime': this.datePickerVal[1],
        'inviteUserName': this.inviteUserName,
        'loginStartTime': this.datePickerVal_one[0],
        'loginEndTime': this.datePickerVal_one[1],
        'cartStartTime': this.datePickerVal_two[0],
        'cartEndTime': this.datePickerVal_two[1],
        'buyStartTime': this.datePickerVal_three[0],
        'buyEndTime': this.datePickerVal_three[1],
        'unitPriceLow': this.unitPriceLeft,
        'unitPriceHight': this.unitPriceRight,
        'buyCountLow': this.frequencyLeft,
        'buyCountHight': this.frequencyRight,
        'goodsId': this.goodsIdsArr,
        'currentPage': this.currentPage3,
        'pageRows': '20'
      }
      membershipListRequest(obj).then((res) => {
        if (res) {
          if (res.content.dataList.length === 0) {
            this.tbodyFlag = false
            return
          }
          this.tbodyFlag = true
          this.trList = res.content.dataList
          this.trList.map((item, index) => {
            item.ischecked = false
          })
          this.totalNum = res.content.page.totalRows
          this.pageCount = res.content.page.pageCount
          this.options_one[2].label = this.$t('membershipIntroduction.Forscreened') + res.content.page.totalRows + this.$t('membershipIntroduction.people') + this.$t('membershipIntroduction.Nolanding')
          this.options_two[2].label = this.$t('membershipIntroduction.Forscreened') + res.content.page.totalRows + this.$t('membershipIntroduction.people') + this.$t('membershipIntroduction.Labeling')
          this.options_three[2].label = this.$t('membershipIntroduction.Forscreened') + res.content.page.totalRows + this.$t('membershipIntroduction.people') + this.$t('membershipIntroduction.Hairpin')
          this.options_four[2].label = this.$t('membershipIntroduction.Forscreened') + res.content.page.totalRows + this.$t('membershipIntroduction.people') + this.$t('membershipIntroduction.Hairpin')
          this.options_five[2].label = this.$t('membershipIntroduction.Forscreened') + res.content.page.totalRows + this.$t('membershipIntroduction.people') + this.$t('membershipIntroduction.Revisioninviters')
          this.options_five[4].label = this.$t('membershipIntroduction.Forscreened') + res.content.page.totalRows + this.$t('membershipIntroduction.people') + this.$t('membershipIntroduction.Deleteinviter')
        }
      })
    },
    // 获取会员卡
    getAllUserCard () {
      allUserCardRequest().then(res => {
        this.membershipCardOptions = res.content
      })
    },
    // 获取来源
    getAllSource () {
      allSourceRequest().then(res => {
        this.sourceOptions = this.sourceOptions.concat(res.content)
        console.log(this.sourceOptions)
      })
    },
    // 获取标签
    getAllTag () {
      allTagRequest().then(res => {
        console.log(res.content)
        this.tagSource = res.content
      })
    },
    // 筛选按钮
    handleScreen () {
      this.defaultTabelListData()
    },
    querySearch (queryString, cb) {
      var results = this.tagSource
      setTimeout(() => {
        cb(results)
      }, 1000 * Math.random())
    },
    createFilter (queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    // 选中输入框建议列表项
    handleSelect (item) {
    },
    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    // 点击选择商品按钮
    handleClickChoiseGood () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
      this.chooseGoodsBack = [11]
    },
    // 当前页发生变化
    handleCurrentChange () {
      this.defaultTabelListData()
    },
    // 会员列表表格选中
    handleClick () {
      let flag = this.trList.filter((item, index) => {
        return item.ischecked === false
      })
      if (flag.length === 0) {
        this.allChecked = true
      } else {
        this.allCheckFlag = true
        this.allChecked = false
      }
      this.$forceUpdate()
    },
    // 全部checkbox选中
    handleAllcheck () {
      this.allCheckFlag = false
    },
    // 控制修改余额-积分弹窗
    handlebalanceDialog (index, item, id) {
      let val = null
      console.log(index)
      if (index === 0) {
        this.balanceDialogData[0].persentMoney = item
        val = this.balanceDialogData[0]
      } else if (index === 1) {
        this.integralDialogData[0].persentMoney = item
        val = this.integralDialogData[0]
      }
      console.log('val->', val)
      // copy value by same key
      Object.keys(this.modifyDialogData).forEach(key => {
        if (!(!val[key] && val[key] !== 0)) {
          this.modifyDialogData[key] = val[key]
        }
      })
      this.modifyDialogData.visiable = true
      this.userId = id
      console.log(this.modifyDialogData)
    },

    hanldeModifyData (res) {
      if (res) {
        this.defaultTabelListData()
      }
      this.modifyDialogData.visiable = false
    },
    // 表格底部下拉框选中事件
    handleFooterSelect (index) {
      if (index === 0) {
        switch (this.value_one) {
          case '1':
            this.handlePdIsChecked('0')
            break
          case '2':
            this.noLandingDialogVisible = true
        }
      } else if (index === 1) {
        switch (this.value_two) {
          case '1':
            this.handlePdIsChecked('1')
            break
          case '2':
            this.labelDialogVisible = true
        }
      } else if (index === 2) {
        switch (this.value_three) {
          case '1':
            this.handlePdIsChecked('2')
            break
          case '2':
            this.toHandleSetUpMemDialog(true)
        }
      } else if (index === 3) {
        switch (this.value_four) {
          case '1':
            this.handlePdIsChecked('3')
            break
          case '2':
            this.integralDialogData[0].persentMoney = '-300'
            this.integralDialogData[0].presentText = '当前最低积分'
            this.addDialogData = this.integralDialogData
            this.balanceDialogVisible = true
        }
      } else if (index === 4) {
        switch (this.value_five) {
          case '1':
            this.handlePdIsChecked('4')
            break
          case '2':
            this.toHandleSelectingUsersDialog(true)
            break
          case '3':
            this.deletePersondialogVisible = true
            this.deletePersonText = '确认要删除吗？'
            break
          case '4':
            this.deletePersondialogVisible = true
            this.deletePersonText = '确认要删除所有的邀请人吗？'
            break
        }
      }
    },
    // 表格底部下拉框选中ischecked判断函数
    handlePdIsChecked (index) {
      let flag = this.trList.filter((item, index) => {
        return item.ischecked === true
      })
      if (flag.length === 0) {
        this.$message.warning('请选择会员')
        switch (index) {
          case '0':
            this.value_one = '0'
            break
          case '1':
            this.value_two = '0'
            break
          case '2':
            this.value_three = '0'
            break
          case '3':
            this.value_four = '0'
            break
          case '4':
            this.value_five = '0'
            break
        }
      } else {
        switch (index) {
          case '0':
            this.noLandingDialogVisible = true
            break
          case '1':
            this.labelDialogVisible = true
            break
          case '2':
            this.toHandleSetUpMemDialog(true)
            break
          case '3':
            this.integralDialogData[0].persentMoney = '-300'
            this.integralDialogData[0].presentText = '当前最低积分'
            this.addDialogData = this.integralDialogData
            this.balanceDialogVisible = true
            break
          case '4':
            this.toHandleSelectingUsersDialog(true)
            break
        }
      }
    },
    // 清空设置会员卡数据
    clearnSetMemberCardData () {
      this.setUpSelectVal_one = []
      this.setUpSelectVal_two = []
      this.setUpSelectVal_three = []
      this.setUpValOne = null
      this.setUpValTwo = null
      this.setUpValThree = null
      this.setUpSelectVal_oneTmp = 0
      this.setUpSelectVal_twoTmp = 0
      this.setUpSelectVal_threeTmp = 0
    },
    // 再提交到数据库之前，除去查询出来的值
    deleteOriginCardInfo () {
      while (this.setUpSelectVal_oneTmp-- > 0) {
        this.setUpSelectVal_one.splice(0, 1)
      }

      while (this.setUpSelectVal_twoTmp-- > 0) {
        this.setUpSelectVal_two.splice(0, 1)
      }

      while (this.setUpSelectVal_threeTmp-- > 0) {
        this.setUpSelectVal_three.splice(0, 1)
      }
    },
    // 会员卡弹窗控制
    handleSetUp (id) {
      // 保存该用户id
      this.cardUserId = id
      // 清理缓冲数据
      this.clearnSetMemberCardData()
      // api请求数据所有会员卡
      getAllMemberCardByClassRequest().then(res => {
        if (res.error === 0) {
          // 普通会员卡下拉数据
          this.setUpoptionsOne = res.content.normalCard
          // 限次会员卡下拉数据
          this.setUpoptionsTwo = res.content.limitNumCard
          // 等级会员卡下拉数据
          this.setUpoptionsThree = res.content.rankCard
        }
      })
      // 获取会员所有的可用的会员卡
      getAllAvailableMemberCardRequest(id).then(res => {
        if (res.error === 0) {
          // 设置缓冲区
          res.content.forEach(item => {
            let obj = { text: item.cardName, id: item.id }
            switch (item.cardType) {
              case 0:
                this.setUpSelectVal_one.push(obj)
                this.setUpSelectVal_oneTmp++
                break
              case 1:
                this.setUpSelectVal_two.push(obj)
                this.setUpSelectVal_twoTmp++
                break
              case 2:
                this.setUpSelectVal_three.push(obj)
                this.setUpSelectVal_threeTmp++
                break
              default:
                break
            }
          })
        }
      })
      this.setUpDialogVisible = true
    },
    // 处理提交为会员设置会员卡逻辑
    setUpCardForMember () {
      this.setUpDialogVisible = false
      // 清空已经拥有的会员卡
      this.deleteOriginCardInfo()
      // 处理会员卡信息
      let cardId = this.setUpSelectVal_one.map(({ id }) => id)
      cardId = cardId.concat(this.setUpSelectVal_two.map(({ id }) => id))
      cardId = cardId.concat(this.setUpSelectVal_three.map(({ id }) => id))
      let obj = {
        'userIdList': [this.cardUserId],
        'cardIdList': cardId
      }
      setCardForMemberRequest(obj).then(res => {
        if (res.error === 0) {
          this.getSuccessMessagePrompt()
          // 重新加载会员列表数据
          this.defaultTabelListData()
        }
      })
    },
    // 切换会员设置弹窗中的添加与下拉框
    handleToChangeSetUpAdd (index) {
      switch (index) {
        case 0:
          this.setUpFalg_1 = false
          this.setUpValOne = ''
          this.handleNormalCardShow()
          break
        case 1:
          this.setUpFalg_2 = false
          this.setUpValTwo = ''
          break
        case 2:
          this.setUpFalg_3 = false
          this.setUpValThree = ''
          this.handleGradeCardShow()
          break
      }
    },
    // 会员设置里删除cion点击
    handleReadd (type, index) {
      if (type === 0) {
        switch (index) {
          case -1:
            this.setUpFalg_1 = true
            break
          default:
            if (this.setUpSelectVal_oneTmp > index) {
              this.setUpSelectVal_oneTmp--
            }
            this.setUpSelectVal_one.splice(index, 1)
        }
      } else if (type === 1) {
        switch (index) {
          case -1:
            this.setUpFalg_2 = true
            break
          default:
            if (this.setUpSelectVal_twoTmp > index) {
              this.setUpSelectVal_twoTmp--
            }
            this.setUpSelectVal_two.splice(index, 1)
        }
      } else {
        switch (index) {
          case -1:
            this.setUpFalg_3 = true
            break
          default:
            if (this.setUpSelectVal_threeTmp > index) {
              this.setUpSelectVal_threeTmp--
            }
            this.setUpSelectVal_three.splice(index, 1)
        }
      }
    },
    handleNormalCardShow () {
      this.setUpoptionsOne.forEach(itemA => {
        itemA.disabled = false
        this.setUpSelectVal_one.forEach(itemB => {
          if (Number(itemA.id) === Number(itemB.id)) {
            itemA.disabled = true
          }
        })
      })
    },
    handleGradeCardShow () {
      if (this.setUpSelectVal_three.length > 0) {
        this.setUpoptionsThree.forEach(itemA => {
          itemA.disabled = true
        })
      } else {
        this.setUpoptionsThree.forEach(itemA => {
          itemA.disabled = false
        })
      }
    },
    // 会员设置弹窗下拉框选中事件
    handleSetUpSelect (index) {
      switch (index) {
        case 0:
          // 普通会员卡
          this.setUpFalg_1 = true
          this.setUpSelectVal_one.push({ text: this.setUpValOne.cardName, id: this.setUpValOne.id })
          break
        case 1:
          // 限次会员卡
          this.setUpFalg_2 = true
          this.setUpSelectVal_two.push({ text: this.setUpValTwo.cardName, id: this.setUpValTwo.id })
          break
        case 2:
          // 等级会员卡
          this.setUpFalg_3 = true
          this.setUpSelectVal_three.push({ text: this.setUpValThree.cardName, id: this.setUpValThree.id })
      }
    },
    // 禁止登录 || 恢复登录
    handleNoLanding (id, status) {
      this.userId = id
      if (status === 1) {
        this.noLandingDialogVisible = true
      } else if (status === 0) {
        this.resumeLoginVisible = true
      }
    },

    // 改变用户登录状态
    changeLoginStatus () {
      var isDelete
      if (this.noLandingDialogVisible) {
        isDelete = 1
        this.noLandingDialogVisible = false
      } else if (this.resumeLoginVisible) {
        isDelete = 0
        this.resumeLoginVisible = false
      }
      let obj = {
        'userIdList': [this.userId],
        'isDelete': isDelete
      }
      // 请求api
      loginStatusRequest(obj).then(res => {
        if (res.error === 0) {
          // 消息框
          this.getSuccessMessagePrompt()
          // 清空userid
          this.userId = null
          // 设置成功，重新加载页面
          this.defaultTabelListData()
        }
      })
    },
    // 打标签
    setTagForMember () {
      // 关闭打标签弹窗
      this.labelDialogVisible = false
      let obj = {
        'userIdList': [this.tagUserId],
        'tagIdList': this.labelDialogInput
      }
      setTagForMemberRequest(obj).then(res => {
        if (res.error === 0) {
          // 提示框
          this.getSuccessMessagePrompt()
          // 清空tagUserId
          this.tagUserId = null
        }
      })
    },
    // 获取用户标签
    handleToLabel (userId) {
      // 获取当前用户所标记的标签
      let obj = {
        'userId': userId
      }
      // 异步请求
      getTagForMemberRequest(obj).then(res => {
        if (res.error === 0) {
          // 设置默认标签列表
          this.labelDialogInput = res.content.map(({ id }) => id)
          // 存储标签
          this.tagUserId = userId
        }
      })
      // 渲染到ui组件

      this.labelDialogInput = []
      this.labelDialogVisible = true
    },
    // 打标签弹窗内的输入框建议处理事件
    handleLabelSelect () {

    },
    // 跳转到会员详情
    hanldeToDetail (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: userId
        }
      })
    },
    // 成功消息弹框
    getSuccessMessagePrompt () {
      var message = this.$t('membershipIntroduction.success')
      this.$message.success({
        showClose: true,
        message: message,
        type: 'success'
      })
    },
    // 失败消息弹框
    getFailMessagePrompt () {
      var message = this.$t('membershipIntroduction.error')
      this.$message({
        showClose: true,
        message: message,
        type: 'error'
      })
    },
    // 点击表格中更多&&余额明细&&积分明细
    handleToTurnMore (params, name, id) {
      switch (params) {
        case 'receiveDetail':
          this.$router.push({
            path: '/admin/home/main/receiveDetail',
            query: {
              id: id,
              name: name
            }
          })
          break
        case 'balanceDetail':
          this.$router.push({
            path: '/admin/home/main/balanceDetail',
            query: {
              name: name,
              id: id
            }
          })
          break
        case 'integralDetail':
          this.$router.push({
            path: '/admin/home/main/integralDetail',
            query: {
              name: name,
              id: id
            }
          })
          break
      }
    }
  }
}
</script>
<style scoped>
.membershioListContent {
  padding: 10px;
  padding-bottom: 5px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.brandManagementContent_main {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
  padding: 15px 15px;
}
.brandManagementContent_main > ul {
  padding-left: 15px;
}
.li {
  padding: 8px 0;
  display: flex;
}
.liNav {
  width: 280px;
  display: flex;
}
.liNav span {
  display: block;
  width: 70px;
  line-height: 30px;
  height: 30px;
  text-align: left;
  color: #333;
}
.liNav .el-input {
  flex: 1;
}
.uls {
  margin-top: 10px;
  display: flex;
}
.ulsThree {
  margin-top: 15px;
  display: flex;
  padding-left: 40px;
}
.ulsThree li:nth-of-type(2) {
  margin-left: 152px;
}
/* .uls span {
  width: 56px;
} */
.date {
  width: 455px;
}
.arrowUl {
  margin-top: 15px;
  display: flex;
  justify-content: center;
}
.hiddenUl {
  margin-top: 20px;
  padding: 0 65px 0 15px;
}
.hiddenUl span {
  margin-right: 30px;
}
.hiddenUl li {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
.hiddenRight {
  display: flex;
}
.hiddenRight span,
i {
  white-space: nowrap;
  height: 40px;
  line-height: 40px;
}
.hiddenRight i {
  display: inline-block;
  margin: 0 10px;
}
.specialLi {
  margin-right: 193px;
}
.brand_title {
  display: flex;
  justify-content: flex-start;
}
.nameClass {
  white-space: nowrap;
  margin: 0 5px;
}
.choiseDivClass {
  width: 138px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  color: #5a8bff;
  border: 1px solid #ccc;
  cursor: pointer;
  display: inline-block;
}
.brandManagementContent_bottom {
  margin-top: 10px;
  padding: 10px;
  background: #fff;
}

.clickClass {
  background-color: #eee !important;
}
/* ul {
  padding-left: 30px;
} */
ul li {
  line-height: 30px;
  display: flex;
  white-space: nowrap;
  /* margin-top: 30px; */
}
ul li:nth-of-type(1) {
  margin-top: 0;
}
.choiseDialog {
  overflow-y: auto;
}
.choiseDialog ul {
  display: flex;
  margin-top: 10px;
}
.choiseDialog ul li {
  margin-top: 0;
}
.choiseDialog ul li:nth-of-type(2) {
  margin: 0 30px;
}
.choiseDialog ul li:nth-of-type(3) {
  margin-right: 30px;
}
.middleBbtnDiv {
  padding: 10px 30px;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
  width: 100%;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
thead td {
  background: #f5f5f5;
  text-align: center;
  color: #000;
  font-weight: bold;
  padding: 8px 10px;
  vertical-align: middle !important;
  height: 36px;
}

thead td:nth-of-type(1) {
  clear: both;
  overflow: hidden;
  /* display: flex;
  align-items: center; */
}
thead td:nth-of-type(1) .tdTopText {
  float: left;
  margin-left: 3px;
}
.sp_ {
  display: flex;
  justify-content: center;
}
.sp_ span {
  margin-left: 10px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 7px;
  vertical-align: middle !important;
  text-align: center;
  font-size: 14px;
}
.plusSpan {
  /* display: inline-block;
  margin-top: -3px; */
  /* position: relative; */
  vertical-align: middle;
  text-align: center;
  top: -3px;
}
img {
  margin-left: 10px;
}
.tdCenter {
  text-align: center;
}
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
.clickClass {
  background-color: #eee !important;
}

.tdCenter {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
}
.level_1 {
  margin-left: 10px;
}
.level_2 {
  margin-left: 15px;
}
.tableFooter {
  height: 50px;
  display: flex;
}
.footer_t {
  overflow: hidden;
  padding-left: 10px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.footer_b {
  overflow: hidden;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  flex: 1;
  height: 60px !important;
}
.tb_decorate_a img {
  margin-left: 0px;
  cursor: pointer;
}
.mAccountDiv{
  display: flex;
  width: 100px;
}

.mAccountDiv > span{
  width: 95px;
}

.mScoreDiv{
  display: flex;
  /* justify-content: space-between; */
  width: 90px;
}
.mScoreDiv > span{
  width: 80px;
  text-align: center;
}

.member {
  display: flex;
  justify-content: space-between;
}
.member > div {
  display: flex;
  flex-direction: column;
  color: #5a8bff;
}
.member > div > span {
  cursor: pointer;
  white-space: nowrap;
}
.lastDiv {
  padding: 5px 0 5px 0px;
  text-align: center;
  color: #5a8bff;
  white-space: nowrap;
}
.lastDiv span {
  cursor: pointer;
  margin-right: 10px;
}
.bD_div {
  margin-bottom: 10px;
  display: flex;
}
.bD_div span {
  white-space: nowrap;
  height: 32px;
  line-height: 32px;
}
.setUpDialogDiv {
  overflow-x: hidden;
  overflow-y: auto;
}
.setUpDialogDiv p {
  color: #999999;
  font-size: 12px;
  margin-bottom: 10px;
  line-height: 14px;
}
.setUpcommon {
  padding-left: 20px;
  /* height: 30px; */
  line-height: 30px;
  display: flex;
}
.setUpBottomDiv {
  margin-bottom: 130px;
}
.hy_common {
  width: 300px;
  height: 35px;
  margin-left: 10px;
  padding-left: 10px;
}
.setUpAdd {
  color: #5a8bff;
  cursor: pointer;
}
.hyHiddenClass {
  background-color: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-right: 10px;
}
.setUpContainer {
  width: 300px;
}
.selectDataClass {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f5f5f5;
  padding-right: 10px;
}
.selectDataClass img {
  height: 15px;
}
.phoneClass {
  width: 70px;
  line-height: 30px;
  height: 30px;
  text-align: left;
  color: #333;
}
/* .labelClass {
  width: 42px !important;
} */
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
.memberListliNav {
  margin-left: 50px;
}
.memberListliNav span {
  width: 100px;
}
.memberListliLast {
  margin-left: 10px;
}
.memberListliLast span {
  width: 100px;
}
.minixLabel {
  width: 174px !important;
}
.specielNav {
  width: 294px;
}
.specialliNavTwo {
  width: 500px;
}
.specialliNavTwo span {
  width: 100px;
  margin-left: 35px;
  margin-right: 20px;
}
.bottom_select_one {
  margin-left: 50px;
}
.minx_bottom_select {
  margin-left: 88px;
}
</style>
<style>
.liNav .el-input__inner {
  width: 150px !important;
}
.hiddenRight .el-input__inner {
  width: 150px !important;
}
.date .el-input__inner {
  width: 350px !important;
}
.ulsThree .el-button {
  padding: 9px 15px !important;
  font-size: 12px !important;
  border-radius: 3px !important;
}
.footer_t .el-input__inner {
  width: 140px !important;
}
.footer_b .el-pagination {
  display: inline-block;
}
.el-dialog__header {
  background-color: #f3f3f3 !important;
  text-align: center !important;
}
.el-dialog__header .el-dialog__title {
  font-size: 14px !important;
}
.balanceDialo .el-dialog__body {
  padding-bottom: 0 !important;
}
.balanceDialo .el-dialog__footer {
  border-top: 1px solid #eee;
}
.setUpDialog .el-dialog__body {
  padding-top: 10px !important;
}
.hy_common .el-input__inner {
  width: 168px !important;
}
</style>
