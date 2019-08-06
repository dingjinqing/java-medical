<template>
  <div class="membershioListContent">
    <div class="brandManagementContent_main">
      <ul>
        <li class="li">
          <div class="liNav">
            <div class="phoneClass">{{$t('membershipIntroduction.phoneNum')}}</div>
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
            <span>{{$t('membershipIntroduction.wechatNickname')}}</span>
            <el-input
              v-model="vxName"
              :placeholder="$t('membershipIntroduction.placeWXNameNum')"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span>{{$t('membershipIntroduction.source')}}</span>
            <el-select
              v-model="sourceValue"
              :placeholder="$t('membershipIntroduction.placeChoise')"
              size="small"
            >
              <el-option
                v-for="item in sourceOptions"
                :key="item.value"
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
            <span>{{$t('membershipIntroduction.membershipCard')}}</span>
            <el-select
              v-model="membershipCardVal"
              :placeholder="$t('membershipIntroduction.placeChoise')"
              size="small"
            >
              <el-option
                v-for="item in membershipCardOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
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
            >{{$t('membershipIntroduction.label')}}</span>
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
            <span>{{$t('membershipIntroduction.registrationTime')}}</span>
            <el-date-picker
              v-model="datePickerVal"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.Starttime')"
              :end-placeholder="$t('membershipIntroduction.Endtime')"
              value-format='yyyy-MM-dd'
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
            <span>{{$t('membershipIntroduction.inviter')}}</span>
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
            <span>{{$t('membershipIntroduction.reacord')}}</span>
            <el-date-picker
              v-model="datePickerVal_one"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.startdata')"
              :end-placeholder="$t('membershipIntroduction.enddate')"
              value-format='yyyy-MM-dd'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="hiddenRight">
            <span>{{$t('membershipIntroduction.PassengerUnitPrice')}}</span>
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
            <span>{{$t('membershipIntroduction.behavior')}}</span>
            <el-date-picker
              v-model="datePickerVal_two"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.startdata')"
              :end-placeholder="$t('membershipIntroduction.enddate')"
              value-format='yyyy-MM-dd'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="hiddenRight">
            <span>{{$t('membershipIntroduction.purchasetimes')}}</span>
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
            <span>{{$t('membershipIntroduction.transaction')}}</span>
            <el-date-picker
              v-model="datePickerVal_two"
              type="daterange"
              :range-separator="$t('membershipIntroduction.to')"
              :start-placeholder="$t('membershipIntroduction.startdata')"
              :end-placeholder="$t('membershipIntroduction.enddate')"
              value-format='yyyy-MM-dd'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="brand_title">
            <span class="nameClass">{{$t('membershipIntroduction.designatedgoods')}}</span>
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
              <td>{{$t('membershipIntroduction.inviter')}}</td>
              <td style="width:11%">{{$t('membershipIntroduction.Balance')}}</td>
              <td style="width:11%">{{$t('membershipIntroduction.integral')}}</td>
              <td style="width:11%">{{$t('membershipIntroduction.membershipCard')}}</td>
              <td style="width:10%">{{$t('membershipIntroduction.source')}}</td>
              <td>{{$t('membershipIntroduction.registrationTime')}}</td>
              <td>{{$t('membershipIntroduction.operation')}}</td>
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
              <td :class="isCenterFlag?'tdCenter':''">
                <span
                  @click="hanldeToDetail()"
                  style="color: #5A8BFF;cursor:pointer"
                >{{item.userName}}</span>

              </td>
              <td class="tb_decorate_a">
                {{item.mobile}}
              </td>
              <td class="tb_decorate_a">
                {{item.inviteUserName}}
              </td>
              <td class="tb_decorate_a">
                <span class="plusSpan">{{item.account}}</span>
                <img
                  @click="handlebalanceDialog(0,item.account,item.userId)"
                  :src="plusImg"
                >
              </td>
              <td class="tb_decorate_a">
                <span class="plusSpan">{{item.score}}</span>
                <img
                  @click="handlebalanceDialog(1,item.score,item.userId)"
                  :src="plusImg"
                >
              </td>
              <td class="tb_decorate_a">
                <div class="member">
                  <span>{{item.source}}</span>
                  <div>
                    <span @click="handleSetUp()">{{$t('membershipIntroduction.setup')}}</span>
                    <span
                      @click="handleToTurnMore('receiveDetail',item.userName)"
                      style="margin-top:8px"
                    >{{$t('membershipIntroduction.more')}}</span>
                  </div>
                </div>
              </td>
              <td class="tb_decorate_a">
                {{item.source}}
              </td>
              <td class="tb_decorate_a">
                {{item.createTime}}

              </td>
              <td class="tb_decorate_a">
                <div class="lastDiv">
                  <span @click="handleToTurnMore('balanceDetail',item.userName)">{{$t('membershipIntroduction.Balancedetails')}}</span>
                  <span @click="handleToTurnMore('integralDetail',item.userName)">{{$t('membershipIntroduction.Integraldetails')}}</span>
                  <span @click="handleNoLanding()">{{$t('membershipIntroduction.Nolanding')}}</span>
                </div>
                <div
                  class="lastDiv"
                  style="margin-top:5px"
                >
                  <span @click="handleToLabel()">{{$t('membershipIntroduction.Labeling')}}</span>
                  <span @click="hanldeToDetail()">{{$t('membershipIntroduction.Seedetails')}}</span>
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
        <div class="tableFooter">
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
                  v-for="item in options_one"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 10px">
              <el-select
                v-model="value_two"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(1)"
              >
                <el-option
                  v-for="item in options_two"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 10px">
              <el-select
                v-model="value_three"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(2)"
              >
                <el-option
                  v-for="item in options_three"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 10px">
              <el-select
                v-model="value_four"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(3)"
              >
                <el-option
                  v-for="item in options_four"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 10px">
              <el-select
                v-model="value_five"
                :placeholder="$t('membershipIntroduction.placeChoise')"
                size="small"
                @change="handleFooterSelect(4)"
              >
                <el-option
                  v-for="item in options_five"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
          </div>
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
    <ChoosingGoods />
    <!--修改余额&修改积分弹窗-->
    <div
      class="balanceDialo"
      v-for="(item,index) in addDialogData"
      :key="index"
    >
      <el-dialog
        :title="item.title"
        :visible.sync="balanceDialogVisible"
        width="40%"
        :modal-append-to-body="false"
      >
        <div class="balanceDialogDiv">
          <div class="bD_div">
            <span>{{item.presentText}}：</span>
            <span>{{item.persentMoney}}</span>
          </div>
          <div class="bD_div">
            <span>{{item.addText}}：</span>
            <el-input
              v-model="balanceDialogInput"
              placeholder="请输入内容"
              size="small"
            ></el-input>
            <span>{{item.tips}}</span>
          </div>
          <div class="bD_div">
            <span>{{item.bzText}}：</span>
            <el-input
              v-model="balanceDialogBottomInput"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="balanceDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="hanldemodifySure()"
          >确 定</el-button>
        </span>
      </el-dialog>
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
          <div class="setUpDialogMain">
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
                      placeholder="请选择"
                      size="mini"
                      @change="handleSetUpSelect(0)"
                    >
                      <el-option
                        v-for="item in setUpoptionsOne"
                        :key="item.value"
                        :label="item.label"
                        :value="item.label"
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
                      placeholder="请选择"
                      size="mini"
                      @change="handleSetUpSelect(1)"
                    >
                      <el-option
                        v-for="item in setUpoptionsTwo"
                        :key="item.value"
                        :label="item.label"
                        :value="item.label"
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
                      placeholder="请选择"
                      size="mini"
                      @change="handleSetUpSelect(2)"
                    >
                      <el-option
                        v-for="item in setUpoptionsThree"
                        :key="item.value"
                        :label="item.label"
                        :value="item.label"
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
          <el-button @click="setUpDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="setUpDialogVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--禁止登陆弹窗-->
    <div class="balanceDialo">
      <el-dialog
        title="禁止登陆"
        :visible.sync="noLandingDialogVisible"
        width="40%"
        :modal-append-to-body="false"
      >
        <div
          class="balanceDialogDiv"
          style="margin-bottom:30px"
        >
          <span style="color:#f66">提示：</span>
          <span>禁止登陆后会员将不能登陆了，确定禁止登陆吗?</span>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="noLandingDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="noLandingDialogVisible = false"
          >确 定</el-button>
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
          class="balanceDialogDiv labelDialogDiv"
          style="margin-bottom:30px"
        >
          <span style="line-height:15px;font-size:12px;color:#a3a3a3;display:block;margin-bottom:10px">一个用户最多可以打5个标签，超过数量的标签将不再被添加给该用户</span>
          <el-select
            v-model="labelDialogInput"
            multiple
            placeholder="请选择"
          >
            <el-option
              v-for="item in hitLabeloptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="labelDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="labelDialogVisible = false"
          >确 定</el-button>
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
        <el-button @click="deletePersondialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="deletePersondialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { membershipListRequest, accountAddRequest } from '@/api/admin/membershipList.js'
import { mapActions } from 'vuex'
import ChoosingGoods from '@/components/admin/choosingGoods'
import SetUpMemCDialog from '@/view/admin/index/leftNavComponents/user_manger/membershipList/setUpMemCDialog'
import SelectingUsersDialog from '@/view/admin/index/leftNavComponents/user_manger/membershipList/selectingUsersDialog'
export default {
  components: { ChoosingGoods, SetUpMemCDialog, SelectingUsersDialog },
  data () {
    return {
      minixLabel: '',
      memberListliNav: '',
      deletePersondialogVisible: false,
      deletePersonText: '',
      phoneNum: '',
      vxName: '',
      inviteUserName: '',
      sourceOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      sourceValue: '',
      membershipCardOptions: [{
        value: '选项1',
        label: '会员1'
      }, {
        value: '选项2',
        label: '会员2'
      }, {
        value: '选项3',
        label: '会员3'
      }],
      noImg: 'http://mpimg2.weipubao.cn/image/admin/no_data.png',
      membershipCardVal: '',
      labelVal: '',
      datePickerVal: '',
      checkPhone: false,
      checkIntegr: false,
      balance: false,
      membershipCard: false,
      noLanding: false,
      importMembership: false,
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
      options_one: this.$t('membershipIntroduction.options_one'),
      options_two: this.$t('membershipIntroduction.options_two'),
      options_three: this.$t('membershipIntroduction.options_three'),
      options_four: this.$t('membershipIntroduction.options_four'),
      options_five: this.$t('membershipIntroduction.options_five'),
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
      balanceDialogData: [
        {
          title: '修改金额',
          presentText: '当前金额',
          persentMoney: '',
          addText: '增加金额',
          tips: '（*当余额为正时，增加余额；余额为负时，减少余额*）',
          bzText: '增加备注'
        }
      ],
      integralDialogData: [
        {
          title: '修改积分',
          presentText: '当前积分',
          persentMoney: '',
          addText: '增加积分',
          tips: '（*当积分为正时，增加积分；积分为负时，减少积分*）',
          bzText: '增加备注'
        }
      ],
      allCheckFlag: false,
      setUpDialogVisible: false,
      setUpFalg_1: true,
      setUpValOne: '',
      setUpoptionsOne: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      setUpFalg_2: true,
      setUpValTwo: '',
      setUpoptionsTwo: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      setUpFalg_3: true,
      setUpValThree: '',
      setUpoptionsThree: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      setUpSelectVal_one: [],
      setUpSelectVal_two: [],
      setUpSelectVal_three: [],
      noLandingDialogVisible: false,
      labelDialogVisible: false,
      labelDialogInput: '',
      totalNum: null,
      pageCount: '',
      userId: '',
      minx_bottom_select: '',
      hitLabeloptions: [
        {
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        },
        {
          value: '选项4',
          label: '帅飞'
        },
        {
          value: '选项5',
          label: '拉拉'
        },
        {
          value: '选项6',
          label: '嘿嘿'
        }
      ],
      memberListliLast: '',
      specielNav: '',
      specialliNavTwo: ''
    }
  },
  watch: {
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
      console.log(newData)
      if (newData.length === 6) {
        this.labelDialogInput.splice(5, 1)
        this.$message.error('一个用户最多可以标记5个标签')
      }
    }
  },
  mounted () {
    this.restaurants = this.loadAll()
    // 初始化语言
    this.langDefault()
    // 初始化会员列表数据
    this.defaultTabelListData()
  },
  methods: {
    ...mapActions(['ToTurnMemberShipDetail', 'toHandleSetUpMemDialog', 'toHandleSelectingUsersDialog']),
    defaultTabelListData () {
      let obj = {
        'source': '',
        'username': this.vxName,
        'inviteUserName': this.inviteUserName,
        'currentPage': this.currentPage3,
        'pageRows': '20',
        'mobile': this.phoneNum,
        'createTime': this.datePickerVal
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
          console.log(this.trList)

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
    // 筛选按钮
    handleScreen () {
      this.defaultTabelListData()
    },
    querySearch (queryString, cb) {
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter (queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    loadAll () {
      return [
        { 'value': '三全鲜食（北新泾店）', 'address': '长宁区新渔路144号' },
        { 'value': 'Hot honey 首尔炸鸡（仙霞路）', 'address': '上海市长宁区淞虹路661号' },
        { 'value': '新旺角茶餐厅', 'address': '上海市普陀区真北路988号创邑金沙谷6号楼113' },
        { 'value': '泷千家(天山西路店)', 'address': '天山西路438号' },
        { 'value': '胖仙女纸杯蛋糕（上海凌空店）', 'address': '上海市长宁区金钟路968号1幢18号楼一层商铺18-101' },
        { 'value': '贡茶', 'address': '上海市长宁区金钟路633号' }
      ]
    },
    // 选中输入框建议列表项
    handleSelect (item) {
      console.log(item)
    },
    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    // 点击选择商品按钮
    handleClickChoiseGood () {
      this.$http.$emit('choosingGoodsFlag', true)
    },
    // 当前页发生变化
    handleCurrentChange () {
      this.defaultTabelListData()
    },
    // 会员列表表格选中
    handleClick () {
      console.log(123)
      // this.trList[index].ischecked = true
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
      console.log(flag, 1)
    },
    // 全部checkbox选中
    handleAllcheck () {
      this.allCheckFlag = false
    },
    // 控制修改余额弹窗
    handlebalanceDialog (index, item, id) {
      console.log(index)
      if (index === 0) {
        this.balanceDialogData[0].persentMoney = item
        this.addDialogData = this.balanceDialogData
      } else {
        this.integralDialogData[0].persentMoney = item
        this.addDialogData = this.integralDialogData
      }
      this.balanceDialogVisible = true
      this.userId = id
    },
    // 修改余额弹窗确认按钮
    hanldemodifySure () {
      let obj = {
        'userId': this.userId,
        'account': this.addDialogData[0].persentMoney,
        'remark': this.balanceDialogBottomInput,
        'amount': parseInt(this.balanceDialogInput)
      }
      accountAddRequest(obj).then((res) => {
        console.log(res)

        if (res) {
          this.defaultTabelListData()
        }
      })
      this.balanceDialogVisible = false
    },
    // 表格底部下拉框选中事件
    handleFooterSelect (index) {
      console.log(index, this.value_five)
      if (index === 0) {
        console.log(this.value_one)
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
      console.log(flag)
      if (flag.length === 0) {
        this.$message('请选择会员')
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
    // 表格设置点击
    handleSetUp () {
      this.setUpDialogVisible = true
    },
    // 切换会员设置弹窗中的添加与下拉框
    handleToChangeSetUpAdd (index) {
      switch (index) {
        case 0:
          this.setUpFalg_1 = false
          this.setUpValOne = ''
          break
        case 1:
          this.setUpFalg_2 = false
          break
        case 2:
          this.setUpFalg_3 = false
          break
      }
    },
    // 会员设置里删除cion点击
    handleReadd (type, index) {
      console.log(index)
      if (type === 0) {
        switch (index) {
          case -1:
            this.setUpFalg_1 = true
            break
          default: this.setUpSelectVal_one.splice(index, 1)
        }
      } else if (type === 1) {
        switch (index) {
          case -1:
            this.setUpFalg_2 = true
            break
          default: this.setUpSelectVal_two.splice(index, 1)
        }
      } else {
        switch (index) {
          case -1:
            this.setUpFalg_3 = true
            break
          default: this.setUpSelectVal_three.splice(index, 1)
        }
      }
    },
    // 会员设置弹窗下拉框选中事件
    handleSetUpSelect (index) {
      console.log(index)
      switch (index) {
        case 0:
          this.setUpFalg_1 = true
          this.setUpSelectVal_one.push({ text: this.setUpValOne })
          break
        case 1:
          this.setUpFalg_2 = true
          this.setUpSelectVal_two.push({ text: this.setUpValTwo })
          break
        case 2:
          this.setUpFalg_3 = true
          this.setUpSelectVal_three.push({ text: this.setUpValThree })
      }
    },
    // 禁止登陆点击
    handleNoLanding () {
      this.noLandingDialogVisible = true
    },
    // 打标签点击
    handleToLabel () {
      this.labelDialogInput = []
      this.labelDialogVisible = true
    },
    // 打标签弹窗内的输入框建议处理事件
    handleLabelSelect () {

    },
    // 跳转到会员详情
    hanldeToDetail () {
      // this.ToTurnMemberShipDetail('memberDetail')
      this.$router.push({
        name: 'membershipInformation'
      })
    },
    // 点击表格中更多&&余额明细&&积分明细
    handleToTurnMore (params, name) {
      console.log(name)
      console.log(params)
      switch (params) {
        case 'receiveDetail':
          this.$router.push({
            path: '/admin/home/main/receiveDetail',
            query: {
              name: name
            }
          })
          break
        case 'balanceDetail':
          this.$router.push({
            path: '/admin/home/main/balanceDetail',
            query: {
              name: name
            }
          })
          break
        case 'integralDetail':
          this.$router.push({
            path: '/admin/home/main/integralDetail',
            query: {
              name: name
            }
          })
          break
      }

      // this.ToTurnMemberShipDetail(params)
    }

  }
}
</script>
<style scoped>
.membershioListContent {
  padding: 10px;
  padding-bottom: 68px;
  /* padding-right: 23px; */
  min-width: 1300px;
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
  padding: 15px 25px;
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
  width: 80px;
  line-height: 30px;
  height: 30px;
  text-align: right;
  color: #333;
  margin-right: 25px;
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
.uls span {
  width: 56px;
}
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
ul {
  padding-left: 30px;
}
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
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
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
  font-size: 12px;
}
.plusSpan {
  /* display: inline-block;
  margin-top: -3px; */
  position: relative;
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
  height: 100px;
  display: flex;
}
.footer_t {
  overflow: hidden;
  padding-left: 30px;
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
  margin-left: 15px;
  cursor: pointer;
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
}
.lastDiv {
  padding: 5px 0 5px 50px;
  text-align: left;
  color: #5a8bff;
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
  line-height: 30px;
  height: 30px;
  text-align: right;
  color: #333;
  margin-right: 25px;
}
.labelClass {
  width: 42px !important;
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
.labelDialogDiv .el-input__inner {
  width: 340px !important;
}
</style>
