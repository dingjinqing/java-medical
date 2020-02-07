<template>
  <div class="membersDetailContent">
    <div class="membersDetailContentMain">
      <div class="topContainer">
        <div class="titleEdit"><span>{{$t('membershipIntroduction.Essentialinformation')}}</span><span @click="handleBaseInfo()">{{$t('membershipIntroduction.Towrite')}}</span></div>
        <div class="topMain">
          <div class="headDiv">
            <img :src="headeImgUrl">
          </div>
          <div class="headRightDiv">
            <ul>
              <li>
                <div class="userName">{{$t('membershipIntroduction.nickname')}}：{{ this.memberBasicInfo.username }}</div>
              </li>
              <li>
                <div>{{$t('membershipIntroduction.Realname')}}：
                  <span v-if='this.memberBasicInfo.realName'> {{ this.memberBasicInfo.realName }} </span>
                  <span v-else> {{$t('membershipIntroduction.unknown')}} </span>
                </div>
                <div>{{$t('membershipIntroduction.inviter')}}：
                  <span v-if='this.memberBasicInfo.inviteUserName'> {{ this.memberBasicInfo.inviteUserName }} </span>
                  <span v-else>{{$t('membershipIntroduction.notExists')}}</span>
                  <span
                    class="modifyLinkPerson"
                    @click="hanldeModifyPerson()"
                  >{{$t('membershipIntroduction.Modifycontacts')}}</span></div>
                <div>{{$t('membershipIntroduction.Becomeacustomer')}}：{{ this.memberBasicInfo.createTime }}</div>
              </li>
              <li>
                <div>{{$t('membershipIntroduction.Recentbrowsing')}}：
                  <span v-if="this.memberBasicInfo.updateTime">{{ this.memberBasicInfo.updateTime }}</span>
                  <span v-else> {{$t('membershipIntroduction.unknown')}} </span>
                </div>
                <div>{{$t('membershipIntroduction.phoneNum')}}：
                  <span v-if="this.memberBasicInfo.mobile"> {{this.memberBasicInfo.mobile}} </span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span></div>
                <div>OpenID：
                  <span v-if="this.memberBasicInfo.wxOpenid">{{ this.memberBasicInfo.wxOpenid }}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
              </li>
              <li>
                <div>WxUnionID：
                  <span v-if="this.memberBasicInfo.wxUnionId">{{ this.memberBasicInfo.wxUnionId }}</span>
                  <span v-else>{{ this.memberBasicInfo.unknown }}</span>
                </div>
                <div>{{$t('membershipIntroduction.Accumulateintegrals')}}：
                  <span v-if="this.memberBasicInfo.totalScore">{{ this.memberBasicInfo.totalScore }}</span>
                  <span v-else>0</span>
                </div>
                <div>{{$t('membershipIntroduction.Cumulativeamount')}}：
                  <span v-if="this.memberBasicInfo.totalConsumpAmount">{{ this.memberBasicInfo.totalConsumpAmount }}</span>
                  <span v-else>0</span>
                </div>
              </li>
              <li>
                <div>{{$t('membershipIntroduction.address')}}：
                  <span v-if='addressListLength'>
                    <ul style="font-size: 12px;margin-top: 3px;">
                      <li
                        v-for="(item,index) in this.memberBasicInfo.addressList"
                        :key="index"
                      >
                        {{item}}
                      </li>
                    </ul>

                  </span>
                  <span v-else>{{$t('membershipIntroduction.notAddYet')}}</span></div>
              </li>
            </ul>

            <ul
              class="hiddenUl"
              v-if="hiddenUlFlag"
            >
              <li>
                <div>{{$t('membershipIntroduction.Sourcechannel')}}：
                  <span v-if='this.memberBasicInfo.source'>{{this.memberBasicInfo.source}}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
                <div>{{$t('membershipIntroduction.Birthday')}}：
                  <span v-if='this.memberBasicInfo.birthdayYear && this.memberBasicInfo.birthdayMonth && this.memberBasicInfo.birthdayDay'>{{this.memberBasicInfo.birthdayYear}}-{{this.memberBasicInfo.birthdayMonth}}-{{ this.memberBasicInfo.birthdayDay }}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
                <div>{{$t('membershipIntroduction.Educationlevel')}}：
                  <span v-if='this.memberBasicInfo.education'>{{this.memberBasicInfo.education}}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
                <div>{{$t('membershipIntroduction.PermanentResidence')}}：
                  <span v-if='this.memberBasicInfo.provinceName || this.memberBasicInfo.cityName || this.memberBasicInfo.distictName'>
                    {{ this.memberBasicInfo.provinceName }} {{ this.memberBasicInfo.cityName }} {{this.memberBasicInfo.distictName}}
                  </span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
                <div>{{$t('membershipIntroduction.ID')}}：
                  <span v-if="this.memberBasicInfo.cid">{{ this.memberBasicInfo.cid }}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
              </li>
              <li>
                <div>{{$t('membershipIntroduction.indursty')}}：
                  <span v-if="this.memberBasicInfo.industryInfo">{{ this.memberBasicInfo.industryInfo }}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
                <div>{{$t('membershipIntroduction.Maritalstatus')}}：
                  <span v-if="this.maritalStatus">{{ this.maritalStatus}}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
                <div>{{$t('membershipIntroduction.monthlyincome')}}：
                  <span v-if="this.memberBasicInfo.monthlyIncome">{{ this.memberBasicInfo.monthlyIncome }}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
                <div>{{$t('membershipIntroduction.Gender')}}：
                  <span v-if="this.memberBasicInfo.sex">{{ this.memberBasicInfo.sex }}</span>
                  <span v-else>{{$t('membershipIntroduction.unknown')}}</span>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <div
          class="footer"
          @click="handleCheckMore()"
        >
          <div class="footerMain">
            {{this.checkMoreText}}
          </div>
        </div>
      </div>

    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>{{$t('membershipIntroduction.Labelinformation')}}</span><span @click="handleLabelEditOpen()">{{$t('membershipIntroduction.Towrite')}}</span></div>
      <div class="labelList">
        <span
          v-for="(item,index) in lebalDataList"
          :key="index"
          class="lebalSpan"
        >{{item.value}}<i
            @click="handleToDelLabel(item.id)"
            class="fa fa-remove"
          ></i></span>
      </div>
    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>{{$t('membershipIntroduction.AssetInformation')}}</span></div>
      <ul
        class="assetsUl"
        :class="assetsUl"
      >
        <li
          v-for="(item,index) in assetsData"
          :key="index"
        >
          <div class="assetsUlLeft">
            <img :src="item.img">
          </div>
          <div class="assetsUlRight">
            <span>{{item.cardName}}</span>
            <span v-if="item.haveSetUp === true ?true:false">|</span>
            <span
              v-if="item.haveSetUp === true ?true:false"
              style="color:#5A8BFF;cursor: pointer;"
              @click="handleSetUp(index)"
            >{{$t('membershipIntroduction.setup')}}</span>
            <span style="margin-top:10px;color:#5A8BFF;cursor: pointer;display:block">{{item.num}}</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>{{$t('membershipIntroduction.Transactionstatistics')}}</span><span @click="jumpToOrderPage">{{$t('membershipIntroduction.OrderList')}}</span></div>
      <div class="transactionDiv">
        <div
          style="flex:1"
          v-for="(item,index) in transactionData"
          :key="index"
          :class="index!==0?'borderLeft':''"
        >
          <p>{{item.title}}</p>
          <div class="transactionBottom">{{item.content}}</div>
        </div>
      </div>
    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>{{$t('membershipIntroduction.distributionStatistic')}}</span></div>
      <div class="transactionDiv">
        <div
          style="flex:1"
          v-for="(item,index) in distributionData"
          :key="index"
          :class="index!==0?'borderLeft':''"
        >
          <p>{{item.title}}</p>
          <div class="transactionBottom">{{item.content}}</div>
        </div>
      </div>
    </div>
    <!--基本信息编辑弹窗-->
    <div class="baseInfo">
      <el-dialog
        :title="$t('membershipIntroduction.Towrite')"
        :visible.sync="baseInfoDialogVisible"
        width="40%"
        :modal-append-to-body="false"
      >
        <div
          class="balanceDialogDiv"
          style="margin-bottom:30px"
        >
          <el-form
            label-position="right"
            label-width="130px"
            size="small"
          >
            <el-form-item :label="$t('membershipIntroduction.Gender')">
              <el-col :span="12">
                <el-select
                  v-model="GenderValue"
                  :placeholder="$t('membershipIntroduction.placeChoise') "
                  size="small"
                >
                  <el-option
                    v-for="item in GenderValueOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('membershipIntroduction.Birthday')">
              <el-col :span="10">
                <el-date-picker
                  v-model="birthdayVal"
                  type="date"
                  :placeholder="$t('membershipIntroduction.chooseDate')"
                  value-format='yyyy-MM-dd'
                  size="small"
                >
                </el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('membershipIntroduction.Realname')">
              <el-col :span="12">
                <el-input
                  size="small"
                  v-model="nameInput"
                  :placeholder="$t('membershipIntroduction.Pleasecontent')"
                ></el-input>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('membershipIntroduction.localtion')">
              <ProAndUrbA @handleToGetProCode="handleToGetProCode" />
            </el-form-item>
            <el-form-item :label="$t('membershipIntroduction.Maritalstatus')">
              <el-col :span="12">
                <el-select
                  v-model="MarriageValue"
                  :placeholder="$t('membershipIntroduction.placeChoise')"
                  size="small"
                >
                  <el-option
                    v-for="(item,index) in MarriageValueOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('membershipIntroduction.monthlyincome')">
              <el-col :span="12">
                <el-select
                  v-model="incomeValue"
                  :placeholder="$t('membershipIntroduction.placeChoise')"
                  size="small"
                >
                  <el-option
                    v-for="(item,index) in incomeValueOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('membershipIntroduction.ID')">
              <el-col :span="12">
                <el-input
                  size="small"
                  v-model="IDInput"
                  :placeholder="$t('membershipIntroduction.Pleasecontent')"
                ></el-input>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('membershipIntroduction.Educationlevel')">
              <el-col :span="12">
                <el-select
                  v-model="educationValue"
                  :placeholder="$t('membershipIntroduction.placeChoise')"
                  size="small"
                >
                  <el-option
                    v-for="(item,index) in educationValueOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('membershipIntroduction.industry')">
              <el-col :span="12">
                <el-select
                  v-model="industryValue"
                  :placeholder="$t('membershipIntroduction.placeChoise')"
                  size="small"
                >
                  <el-option
                    v-for="(item,index) in industryValueOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-col>
            </el-form-item>
          </el-form>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="baseInfoDialogVisible = false"
          >{{ $t('membershipIntroduction.cancel') }}</el-button>
          <el-button
            size="small"
            type="primary"
            @click="handleUserDialogSure()"
          >{{ $t('membershipIntroduction.centain') }}</el-button>
        </span>
      </el-dialog>
    </div>
    <!--修改邀请人弹窗-->
    <div class="baseInfo">
      <el-dialog
        :title="$t('membershipIntroduction.chooseUser')"
        :visible.sync="modifypersonDialogVisible"
        width="800px"
        :modal-append-to-body="false"
      >
        <div
          class="modifypersonDiv"
          style="margin-bottom:30px"
        >
          <div class="modifypersonDivTop">
            <div>
              <span>{{ $t('membershipIntroduction.nickname') }}</span>
              <el-input
                size="small"
                v-model="userNameInput"
                :placeholder="$t('membershipIntroduction.Pleasecontent')"
              ></el-input>
            </div>
            <div>
              <span style="width:80px">{{ $t('membershipIntroduction.phoneNum') }}</span>
              <el-input
                size="small"
                v-model="mobileInput"
                :placeholder="$t('membershipIntroduction.Pleasecontent')"
              ></el-input>
            </div>
            <div>
              <span style="width:90px">{{ $t('membershipIntroduction.Realname') }}</span>
              <el-input
                size="small"
                v-model="realNameInput"
                :placeholder="$t('membershipIntroduction.Pleasecontent')"
              ></el-input>
            </div>
            <el-button
              type="primary"
              size="small"
              @click="getUserTabelListData"
            >{{ $t('membershipIntroduction.search')}}</el-button>
          </div>
          <!--底部表格-->
          <div
            class="content"
            v-if="page_one"
          >
            <table width='100%'>
              <thead>
                <tr>
                  <td>{{ $t('membershipIntroduction.userId') }}</td>
                  <td>{{ $t('membershipIntroduction.nickname') }}</td>
                  <td>{{ $t('membershipIntroduction.phoneNum') }}</td>
                  <td>{{ $t('membershipIntroduction.Realname') }}</td>

                </tr>
              </thead>
              <tbody v-if="tbodyFlag">
                <tr
                  v-for="(item,index) in trList"
                  :key="index"
                  :class="clickIindex===item.userId?'clickClass':''"
                  @click="handleClick(item.userId)"
                >

                  <td>{{item.userId}}</td>
                  <td>{{item.userName}}</td>
                  <td>{{item.mobile}}</td>
                  <td>{{item.realName}}</td>
                </tr>
              </tbody>

            </table>
            <div
              class="noData"
              v-if="!tbodyFlag"
            >
              <img :src="noImg">
              <span>{{ $t('membershipIntroduction.noData')}}</span>
            </div>
          </div>
          <div
            class="content_two"
            v-else
          >
            <table width='100%'>
              <thead>
                <tr>
                  <td>名称</td>

                  <td>链接</td>
                </tr>
              </thead>
              <tbody v-if="tbodyFlag">
                <tr
                  v-for="(item,index) in trList"
                  :key="index"
                  :class="clickIindex===index?'clickClass':''"
                  @click="handleClick(index)"
                >
                  <td>{{item.title}}</td>

                  <td class="tb_decorate_a">
                    {{item.path}}
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
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="modifypersonDialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="handleUserDialogSure()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--标签信息编辑弹窗-->
    <div class="balanceDialo">
      <el-dialog
        :title="$t('membershipIntroduction.label')"
        :visible.sync="labelEditDialogVisible"
        width="25%"
        :modal-append-to-body="false"
      >
        <div
          class="labelEditDialogDiv"
          style="margin-bottom:30px"
        >
          <div>{{ $t('membershipIntroduction.chooseTag') }}</div>
          <el-select
            size="small"
            v-model="labelEditValue"
            :placeholder="$t('membershipIntroduction.placeChoise')"
            multiple
          >
            <el-option
              v-for="(item,index) in labelEditValueOptions"
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
            @click="labelEditDialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="handleTagDialogSure()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--设置会员卡弹窗类别一-->
    <div class="balanceDialo">
      <el-dialog
        title="设置会员卡"
        :visible.sync="memberCardT1DialogVisible"
        width="35%"
        :modal-append-to-body="false"
      >
        <div
          class="labelEditDialogDiv"
          style="margin-bottom:30px"
        >
          <div class="MemberdialogContainer">

            <div class="memberCardT1DialogTop">
              <span>可以在这里编辑该会员的会员卡信息，添加会员卡，注意需要激活的会员卡将直接发放到用户。</span>
            </div>
            <div class="cardName">
              <i>1</i>
              普通会员卡
            </div>
            <div class="memberCardT1Main">
              <div>已选：</div>
              <div class="memberCardT1MainCardsMiddle">
                <div
                  class="memberCardT1MainCards"
                  v-for="(item,index) in cardLlabelsdATa"
                  :key="index"
                >
                  <span>{{item.cardName}}<i
                      @click="hanldeToDelCard(index)"
                      class="fa fa-remove"
                    ></i></span>
                </div>
              </div>
              <div
                class="memberCardT1MainRight"
                @click="hanldeToTurnRows()"
              >
                <span>添加新卡</span>
                <i :class="newCardsT1Flag?'newCardsT1':'newCardsT2'"></i>
              </div>
            </div>
            <div
              class="memberCardT1Footer"
              v-if="memberTableFlag"
            >
              <div
                class="content"
                v-if="page_two"
              >
                <table width='100%'>
                  <thead>
                    <tr>
                      <td>卡名称</td>
                      <td>创建时间</td>
                      <td>卡权益</td>
                      <td></td>
                    </tr>
                  </thead>
                  <tbody v-if="tbodyFlagTwo">
                    <tr
                      v-for="(item,index) in trListTwo"
                      :key="index"
                      :class="item.index===index?'clickClass':''"
                    >
                      <td style="white-space: nowrap;">{{item.cardName}}</td>
                      <td>{{item.startTime}}</td>
                      <td class="link">{{item.content}}</td>
                      <td class="lastTd">
                        <span @click="handleClickMemberCard(index)">{{item.index===index?'-':'添加'}}</span>
                      </td>
                    </tr>
                  </tbody>

                </table>
                <div
                  class="noData"
                  v-if="!tbodyFlagTwo"
                >
                  <img :src="noImg">
                  <span>暂无相关数据</span>
                </div>
              </div>
              <div
                class="content_two"
                v-else
              >
                <table width='100%'>
                  <thead>
                    <tr>
                      <td>名称</td>

                      <td>链接</td>
                    </tr>
                  </thead>
                  <tbody v-if="tbodyFlag">
                    <tr
                      v-for="(item,index) in trList"
                      :key="index"
                      :class="clickIindex===index?'clickClass':''"
                      @click="handleClick(index)"
                    >
                      <td>{{item.title}}</td>

                      <td class="tb_decorate_a">
                        {{item.path}}
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
            @click="closeMemberCardDialog()"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="handleForSetMemberCard()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--设置会员卡弹窗类别二-->
    <div
      class="balanceDialo"
      v-for="(item,index) in addDialogData"
      :key="index"
    >
      <el-dialog
        title="修改余额"
        :visible.sync="memberLabelVisible"
        width="40%"
        :modal-append-to-body="false"
      >
        <div
          class="labelEditDialogDiv"
          style="margin-bottom:30px"
        >
          <div class="balanceDialogDiv">
            <div class="bD_div">
              <span>{{item.presentText}}：</span>
              <span>{{item.persentMoney}}</span>
            </div>
            <div class="bD_div specialAddMoney">
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
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="memberLabelVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="memberLabelVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import ProAndUrbA from '@/components/system/proAndUrbA'
import { getAllIndustryRequest, membershipListRequest, memberInfoRequest, getTagForMemberRequest, allTagRequest, setTagForMemberRequest, updateMemberInfoRequest } from '@/api/admin/membershipList.js'
import { getMemberCard, getAllAvailableMemberCardRequest, setCardForMemberRequest } from '@/api/admin/memberManage/memberCard.js'
export default {
  components: { ProAndUrbA },
  data () {
    return {
      userId: '', // 用户id
      memberBasicInfo: {}, //  会员基本信息
      transStatistic: {}, // 会员交易统计
      maritalStatus: '', // 婚姻状况
      userNameInput: '', // 用户输入->昵称
      mobileInput: '', // 用户输入->手机号
      realNameInput: '', // 用户输入->真实姓名
      addressListLength: '', // 地址长度
      assetsUl: '',
      headeImgUrl: this.$imageHost + '/image/admin/head_icon.png',
      assetsData: [],
      transactionData: '',
      distributionData: '',
      hiddenUlFlag: false,
      checkMoreText: '',
      baseInfoDialogVisible: false,
      GenderValue: '',
      GenderValueOptions: [], // 性别下拉选项
      birthdayVal: '',
      nameInput: '',
      MarriageValue: '',
      MarriageValueOptions: [], // 婚姻状况下拉选项
      incomeValue: '',
      incomeValueOptions: [],
      IDInput: '',
      educationValue: '',
      educationValueOptions: [], // 受教育程度下拉框
      industryValue: '',
      industryValueOptions: [
        {
          value: '选项1',
          label: '计算机硬件及网络设备'
        }, {
          value: '选项2',
          label: '计算机软件'
        },
        {
          value: '选项3',
          label: 'IT服务(系统/数据/维护/多领域经营)'
        }

      ],
      modifypersonDialogVisible: false,
      page_one: true,
      page_two: true,
      tbodyFlag: false,
      tbodyFlagTwo: true,
      trList: [],
      trListTwo: [],
      clickIindex: null,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      labelEditDialogVisible: false,
      labelEditValue: [], // 用户标签Id的列表缓冲
      labelEditValueOptions: [], // 所有标签裂变
      lebalDataList: [], // 用户标签的列表
      lebalDataIdList: [], // 用户标签Id的列表
      memberCardT1DialogVisible: false,
      cardLlabelsdATa: [], // 临时缓冲
      normalCardTmp: [], // 普通会员卡
      normalTmpSize: 0,
      limitCardTmp: [], // 限次会员卡
      limitTmpSize: 0,
      rankCardTmp: [], // 等级会员卡
      cardDialogFlag: null, // 会员卡弹窗目前的类型
      rankTmpSize: 0,
      newCardsT1Flag: true,
      clickIindexTwo: null,
      memberTableFlag: false,
      addDialogData: [],
      balanceDialogInput: '',
      balanceDialogBottomInput: '',
      memberLabelVisible: false,
      balanceDialogData: [
        {
          title: '修改金额',
          presentText: '当前金额',
          persentMoney: '0.00',
          addText: '增加金额',
          tips: '（*当余额为正时，增加余额；余额为负时，减少余额*）',
          bzText: '增加备注'
        }
      ],
      integralDialogData: [
        {
          title: '修改积分',
          presentText: '当前积分',
          persentMoney: '0.00',
          addText: '增加积分',
          tips: '（*当积分为正时，增加积分；积分为负时，减少积分*）',
          bzText: '增加备注'
        }
      ]
    }
  },
  created () {
    // 从路由获取userId
    this.userId = this.$route.query.userId
  },
  watch: {
    lang () {
      // 加载数据

      this.loadMemberInfo()
    }
  },
  mounted () {
    // 初始化语言

    this.langDefault()
  },
  methods: {
    defaultMessage () {
      this.transactionData = this.$t('membershipIntroduction.transactionData')
      this.distributionData = this.$t('membershipIntroduction.distributionData')
      this.checkMoreText = this.$t('membershipIntroduction.Seemore')
      // 性别
      this.GenderValueOptions = this.$t('membershipIntroduction.GenderValueOptions')
      // 婚姻状况
      this.MarriageValueOptions = this.$t(`membershipIntroduction.MarriageValueOptions`)
      // 月收入
      this.incomeValueOptions = this.$t(`membershipIntroduction.incomeValueOptions`)
      // 受教育程度
      this.educationValueOptions = this.$t(`membershipIntroduction.educationValueOptions`)

      this.assetsData = [
        {
          img: this.$imageHost + '/image/admin/asset1.png',
          cardName: this.$t('membershipIntroduction.assetsData[0]'),
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset2.png',
          cardName: this.$t('membershipIntroduction.assetsData[1]'),
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/grade_card_icon.png',
          cardName: this.$t('membershipIntroduction.assetsData[2]'),
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset3.png',
          cardName: this.$t('membershipIntroduction.assetsData[3]'),
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset5.png',
          cardName: this.$t('membershipIntroduction.assetsData[4]'),
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset4.png',
          cardName: this.$t('membershipIntroduction.assetsData[5]'),
          num: 0,
          numColor: 1,
          haveSetUp: false
        }
      ]
    },
    // 加载用户数据
    loadMemberInfo () {
      this.defaultMessage()
      memberInfoRequest(this.userId).then(res => {
        console.log(res)

        if (res.error === 0) {
          console.log(res.content)
          console.log(res.content.memberBasicInfo.industryInfo)
          // 设置值 基本信息
          this.memberBasicInfo = res.content.memberBasicInfo
          if (this.memberBasicInfo.userAvatar) {
            this.headeImgUrl = this.memberBasicInfo.userAvatar
          }
          console.log(this.memberBasicInfo)
          this.addressListLength = this.memberBasicInfo.addressList.length
          // 交易统计
          this.transStatistic = res.content.transStatistic
          console.log(this.transStatistic)

          // 处理时间
          if (this.memberBasicInfo.createTime) {
            this.memberBasicInfo.createTime = this.memberBasicInfo.createTime.split(' ')[0]
          }
          if (this.memberBasicInfo.updateTime) {
            this.memberBasicInfo.updateTime = this.memberBasicInfo.updateTime.split(' ')[0]
          }

          // 处理婚姻状况
          let status = this.memberBasicInfo.maritalStatus
          //  1-未婚2-已婚3-保密
          if (status) {
            this.maritalStatus = this.$t('membershipIntroduction.maritalStatus')[status - 1]
          } else {
            this.maritalStatus = null
          }
          console.log(status)
          console.log(this.maritalStatus)

          // 性别
          if (this.memberBasicInfo.sex) {
            let sexArr = this.$t('membershipIntroduction.sex')
            let map = new Map(sexArr)
            let sex = map.get(this.memberBasicInfo.sex)
            this.memberBasicInfo.sex = sex
            console.log(sex)
          }
          console.log(this.memberBasicInfo.monthlyIncome)
          // 月收入
          let tmp = String(this.memberBasicInfo.monthlyIncome)
          if (this.memberBasicInfo.monthlyIncome) {
            let monthIncomeArr = this.$t('membershipIntroduction.incomeValueOptions')
            for (let i of monthIncomeArr) {
              console.log(i)
              if (tmp === i.value) {
                this.memberBasicInfo.monthlyIncome = i.label
                break
              }
            }
          }
          // 用户标签信息
          this.handleToLabel()
          // 交易 统计
          this.dealWithTransactionData()
          // 分销 统计
          this.dealWithdDistributionData()
        }
      })
      this.getAllAvailableMemberCard()
    },
    closeMemberCardDialog () {
      this.memberCardT1DialogVisible = false
      this.clearCardTmpData()
    },
    getAllAvailableMemberCard () {
      // 用户持有的会员卡
      getAllAvailableMemberCardRequest(this.userId).then(res => {
        if (res.error === 0) {
          // 清空缓冲数据
          this.clearCardTmpData()
          for (let card of res.content) {
            switch (card.cardType) {
              case 0:
                this.normalCardTmp.push(card) // 普通会员卡
                break
              case 1:
                this.limitCardTmp.push(card) // 限次会员卡
                break
              case 2:
                this.rankCardTmp.push(card) // 等级会员卡
                break
              default:
                break
            }
          }

          // 设置会员卡拥有的数量
          this.assetsData[0].num = this.normalCardTmp.length
          this.assetsData[1].num = this.limitCardTmp.length
          this.assetsData[2].num = this.rankCardTmp.length
          this.normalTmpSize = this.normalCardTmp.length
          this.limitTmpSize = this.limitCardTmp.length
          this.rankTmpSize = this.rankCardTmp.length
          console.log(this.assetsData)
        }
      })
    },
    // 清空设置会员卡的相关缓冲数据
    clearCardTmpData () {
      this.trListTwo = []
      this.normalCardTmp = []
      this.limitCardTmp = []
      this.rankCardTmp = []
      this.rankTmpSize = 0
      this.limitTmpSize = 0
      this.normalTmpSize = 0
    },
    // 标签弹窗确定按键
    handleTagDialogSure () {
      this.labelEditDialogVisible = false
      this.setTagForMember()
      // 重新加载
      this.loadMemberInfo()
    },
    // 打标签
    setTagForMember () {
      // 关闭打标签弹窗
      let obj = {
        'userIdList': [this.userId],
        'tagIdList': this.labelEditValue
      }
      console.log(obj)
      setTagForMemberRequest(obj).then(res => {
        console.log(res.error)
        if (res.error === 0) {
          // 提示框
          this.getSuccessMessagePrompt()
        }
      })
    },

    // 获取用户标签
    handleToLabel () {
      // 获取当前用户所标记的标签
      let obj = {
        'userId': this.userId
      }
      console.log(obj)
      // 异步请求
      getTagForMemberRequest(obj).then(res => {
        if (res.error === 0) {
          console.log('查询成功')
          console.log(res)
          // 设置默认标签列表

          this.lebalDataIdList = res.content.map(({ id }) => id)
          this.lebalDataList = res.content
          console.log(this.labelDialogInput)
        }
      })
    },
    // 将表示单位转化为可读
    changeUnit (unit) {
      switch (unit) {
        case 'D':
          return this.$t('membershipIntroduction.day')
        case 'M':
          return this.$t('membershipIntroduction.month')
        case 'Y':
          return this.$t('membershipIntroduction.year')
      }
    },
    // 交易统计
    dealWithTransactionData () {
      // 最近下单时间
      console.log(this.transStatistic.lastAddOrder)
      if (this.transStatistic.lastAddOrder !== '0') {
        let lastAddOrderArr = this.transStatistic.lastAddOrder.split('-')
        let num = lastAddOrderArr[0]
        let unit = this.changeUnit(lastAddOrderArr[1])
        this.changeUnit(unit)
        this.transactionData[0].content = `${num}${unit}`
        console.log(this.transactionData[0].content)
      }

      // 客单价
      if (this.memberBasicInfo.unitPrice) {
        let flag = String(this.transactionData[1].content).slice(0, 1)

        this.transactionData[1].content = `${flag} ${this.memberBasicInfo.unitPrice}`
      }

      // 累计下单金额
      if (this.transStatistic.orderMoney) {
        this.transactionData[2].content = `￥ ${this.transStatistic.orderMoney}`
      }

      // 累计消费订单数
      if (this.transStatistic.orderNum) {
        this.transactionData[3].content = this.transStatistic.orderNum
      }
      // 累计退款
      if (this.transStatistic.returnOrderMoney) {
        this.transactionData[4].content = `￥ ${this.transStatistic.returnOrderMoney}`
      }
      // 累计退款订单数
      if (this.transStatistic.returnOrderNum) {
        this.transactionData[5].content = this.transStatistic.returnOrderNum
      }
    },
    // 分销 统计
    dealWithdDistributionData () {
      // 获返利订单数量
      if (this.transStatistic.rebateOrderNum) {
        this.distributionData[0].content = this.transStatistic.rebateOrderNum
      }
      // 返利商品总金额(元)
      if (this.transStatistic.totalCanFanliMoney) {
        this.distributionData[1].content = this.transStatistic.totalCanFanliMoney
      }
      // 获返利佣金总金额(元)
      if (this.transStatistic.rebateMoney) {
        this.distributionData[2].content = this.transStatistic.rebateMoney
      }
      /**  已提现佣金总金额(元) */
      if (this.transStatistic.withdrawCash) {
        this.distributionData[3].content = this.transStatistic.withdrawCash
      }
      // 下级用户数
      if (this.transStatistic.sublayerNumber) {
        this.distributionData[4].content = this.transStatistic.sublayerNumber
      }
      // 分销员等级
      if (this.transStatistic.levelName) {
        this.distributionData[5].content = this.transStatistic.levelName
      }
      // 分销员分组
      if (this.transStatistic.groupName) {
        this.distributionData[6].content = this.transStatistic.groupName
      }
    },
    // 点击查看更多
    handleCheckMore () {
      this.hiddenUlFlag = !this.hiddenUlFlag
      if (this.hiddenUlFlag === true) {
        this.checkMoreText = this.$t('membershipIntroduction.retract')
      } else {
        this.checkMoreText = this.$t('membershipIntroduction.Seemore')
      }
      console.log(this.hiddenUlFlag)
    },
    // 基本信息编辑弹窗
    handleBaseInfo () {
      // 清空数据
      this.clearInputData()
      this.baseInfoDialogVisible = true
      // 获取所有行业信息
      getAllIndustryRequest().then(res => {
        if (res.error === 0) {
          // 赋值行业信息
          this.industryValueOptions = res.content
        }
      })
    },
    // 行点击
    handleClick (id) {
      console.log(id)
      this.clickIindex = id
    },
    // 处理缓冲区，防止提交重复
    dealWithCardLlabelBeforeSubmit () {
      switch (this.cardDialogFlag) {
        case 0:
          while (this.normalTmpSize > 0) {
            this.cardLlabelsdATa.splice(0, 1)
            this.normalTmpSize--
          }
          break
        case 1:
          while (this.limitTmpSize > 0) {
            this.cardLlabelsdATa.splice(0, 1)
            this.limitTmpSize--
          }
          break
        case 2:
          while (this.rankTmpSize > 0) {
            this.cardLlabelsdATa.splice(0, 1)
            this.rankTmpSize--
          }
          break
        default:
          break
      }
    },
    // 处理提交会员卡设置
    handleForSetMemberCard () {
      // 关闭弹窗
      this.memberCardT1DialogVisible = false
      this.dealWithCardLlabelBeforeSubmit()
      let lst = this.cardLlabelsdATa.map(({ id }) => id)
      let obj = {
        'userIdList': [this.userId],
        'cardIdList': lst
      }
      console.log(obj)
      setCardForMemberRequest(obj).then(res => {
        if (res.error === 0) {
          // 成功
          this.getSuccessMessagePrompt()
          this.loadMemberInfo()
        }
      })
    },
    // 修改邀请人处理逻辑
    handleUserDialogSure () {
      console.log(this.birthdayVal)
      let tmp = this.birthdayVal.split('-')
      console.log(tmp)
      this.modifypersonDialogVisible = false
      this.baseInfoDialogVisible = false
      let year, month, day
      if (this.birthdayVal) {
        [year, month, day] = this.birthdayVal.split('-')
      }
      let obj = {
        'userId': this.userId,
        'inviteId': this.clickIindex,
        'maritalStatus': this.MarriageValue,
        'sex': this.GenderValue,
        'birthdayYear': year,
        'birthdayMonth': month,
        'birthdayDay': day,
        'realName': this.nameInput,
        'monthlyIncome': this.incomeValue,
        'cid': this.IDInput,
        'education': this.educationValue,
        'industory': this.industryValue
      }
      console.log(obj)

      updateMemberInfoRequest(obj).then(res => {
        if (res.error === 0) {
          // 成功
          // 重新加载数据
          this.loadMemberInfo()
          this.getSuccessMessagePrompt()
        }
      })
    },

    // 点击修改联系人
    hanldeModifyPerson () {
      // 清空弹出框的输入数据
      this.clearInputData()
      this.modifypersonDialogVisible = true
      this.getUserTabelListData()
    },
    // 获取会员用户
    getUserTabelListData () {
      let obj = {
        'mobile': String(this.mobileInput).trim(),
        'username': this.userNameInput,
        'realName': this.realNameInput
      }
      console.log(obj)
      membershipListRequest(obj).then(res => {
        if (res.error === 0) {
          this.trList = res.content.dataList.reverse()
          this.tbodyFlag = true
        }
      })
    },
    // 删除用户输入的查询信息
    clearInputData () {
      this.mobileInput = null
      this.userNameInput = null
      this.realNameInput = null

      this.MarriageValue = null
      this.GenderValue = null

      this.nameInput = null
      this.incomeValue = null
      this.IDInput = null
      this.educationValue = null
      this.industryValue = null
    },
    // 获取标签
    getAllTag () {
      console.log('-------------获取所有标签---------------------')
      allTagRequest().then(res => {
        console.log(res.content)
        this.labelEditValueOptions = res.content
      })
    },

    // 点击标签信息编辑
    handleLabelEditOpen () {
      if (this.labelEditValueOptions) {
        this.getAllTag()
      }
      // 将用户实际的标签id加入缓冲
      this.labelEditValue = this.lebalDataIdList
      this.labelEditDialogVisible = true
    },

    // 点击添加新卡
    hanldeToTurnRows () {
      this.newCardsT1Flag = !this.newCardsT1Flag
      this.memberTableFlag = !this.memberTableFlag
    },
    // 点击设置会员卡里卡片删除icon
    hanldeToDelCard (index) {
      if (this.cardDialogFlag === 0 && this.normalTmpSize > index) {
        // 普通会员卡
        this.cardLlabelsdATa.splice(index, 1)
        this.normalTmpSize--
      } else if (this.cardDialogFlag === 1 && this.limitTmpSize > index) {
        // 限次会员卡
        this.cardLlabelsdATa.splice(index, 1)
        this.limitTmpSize--
      } else if (this.cardDialogFlag === 2 && this.rankTmpSize > index) {
        // 等级会员卡
        this.cardLlabelsdATa.splice(index, 1)
        this.rankTmpSize--
      } else {
        let tmp = this.cardLlabelsdATa[index]
        this.cardLlabelsdATa.splice(index, 1)

        for (let card of this.trListTwo) {
          if (card.id === tmp.id) {
            card.index = null
          }
        }
      }
    },
    // 设置会员卡弹窗表格选中
    handleClickMemberCard (index) {
      if (this.trListTwo[index].index === index) {
        return
      }
      console.log(this.cardLlabelsdATa)
      this.trListTwo[index].index = index
      this.cardLlabelsdATa.push(this.trListTwo[index])
      console.log(this.cardLlabelsdATa)
      console.log(this.trListTwo)
    },
    // 资产信息点击设置
    handleSetUp (index) {
      if ([0, 1, 2].includes(index)) {
        console.log('正在设置会员卡')
        // 调用接口获取数据
        this.getMemberCardForData(index)
        this.cardDialogFlag = index
      }
      switch (index) {
        case 0:
          this.memberCardT1DialogVisible = true
          this.cardLlabelsdATa = this.normalCardTmp
          break
        case 1:
          this.memberCardT1DialogVisible = true
          this.cardLlabelsdATa = this.limitCardTmp
          break
        case 2:
          this.memberCardT1DialogVisible = true
          this.cardLlabelsdATa = this.rankCardTmp
          break
        case 3:
          console.log(3)
          this.memberLabelVisible = true
          this.addDialogData = this.balanceDialogData

          break
        case 4:
          this.memberLabelVisible = true
          this.addDialogData = this.integralDialogData
          break
      }
    },
    /** 后台请求api获取会员卡数据 */
    getMemberCardForData (type) {
      let obj = {
        'cardType': type
      }
      console.log(obj)
      getMemberCard(obj).then(res => {
        if (res.error === 0) {
          console.log(res.content)
          this.trListTwo = res.content.dataList
          // 处理会员卡的卡权益
          this.dealWithCardBehavior()
        }
      })
    },
    // 处理会员卡的卡权益
    dealWithCardBehavior () {
      for (let card of this.trListTwo) {
        let content = ''
        console.log(card)
        console.log(card.powerCount, typeof card.powerCount)
        // 会员折扣
        if (card.powerCount === 1) {
          content += `${this.$t('membershipIntroduction.memberCount')}${card.disCount}${this.$t('membershipIntroduction.disCount')}`
        }

        // 会员专享商品
        if (card.powerPayOwnGood === 'on') {
          content += this.$t('membershipIntroduction.powerPayOwnGood')
        }

        // 充值奖励
        if (card.powerCard === 1) {
          content += this.$t('membershipIntroduction.powerCard')
        }

        // 积分奖励
        if (card.powerScore === 1) {
          content += this.$t('membershipIntroduction.powerScore')
        }

        // 门店兑换次数
        if (card.count > 0) {
          content += `${this.$t('membershipIntroduction.storeExchange')}${card.count}${this.$t('membershipIntroduction.times')}`
        }

        // 商品兑换次数
        if (card.exchangCount > 0) {
          content += `${this.$t('membershipIntroduction.goodsExchange')}${card.exchangCount}${this.$t('membershipIntroduction.times')}`
        }

        card.content = content
      }
      console.log(this.trListTwo)
    },
    // 标签列表子项删除
    handleToDelLabel (id) {
      // 删除用户的标签根据标签id
      this.labelEditValue = this.lebalDataIdList
      let index = this.labelEditValue.indexOf(id)
      this.labelEditValue.splice(index, 1)
      // 更新标签
      this.setTagForMember()
      // 重新加载
      this.loadMemberInfo()
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
    // 省市区数据回传
    handleToGetProCode (data) {
      console.log(data)
    },
    jumpToOrderPage () {
      this.$router.push({
        name: 'order',
        query: {
          userId: this.userId,
          userName: this.memberBasicInfo.username
        }
      })
    }
  }
}
</script>

<style scoped>
.lebalSpan {
  display: inline-block;
  min-width: 60px;
  padding: 0 3px;
  text-align: center;
  height: 20px;
  line-height: 20px;
  border: 1px solid #999;
  border-radius: 2px;
  font-size: 12px;
  color: #444;
  margin-right: 15px;
  position: relative;
}
.lebalSpan i {
  position: absolute;
  top: -6px;
  right: -6px;
  cursor: pointer;
}
.membersDetailContent {
  padding: 10px;
  padding-bottom: 68px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.membersDetailContentMain {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
}
.titleEdit {
  padding: 0 25px;
  height: 50px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #e6e9f0;
}
.titleEdit span:nth-of-type(2) {
  font-size: 12px;
  color: #5a8bff;
  margin-left: 12px;
  cursor: pointer;
}
.topMain {
  padding: 20px 50px;
  display: flex;
}
.headDiv img {
  width: 54px;
  height: 54px;
}
.headRightDiv {
  margin-left: 25px;
}
.headRightDiv ul li {
  line-height: 25px;
  display: flex;
  font-size: 12px;
  color: #777;
}
.headRightDiv ul li div {
  width: 300px;
}
.headRightDiv .userName {
  font-size: 15px;
  color: #333;
  line-height: 30px;
}
.modifyLinkPerson {
  color: #0e70ca;
  padding-left: 10px;
  cursor: pointer;
}
.footer {
  height: 41px;
  padding: 0 20px;
}
.footerMain {
  height: 41px;
  line-height: 41px;
  text-align: center;
  border-top: 1px solid #eee;
  color: #5a8bff;
  cursor: pointer;
}
.topContainer {
  background-color: #fff;
  margin-top: 10px;
}
.labelList {
  padding: 20px;
}
.assetsUl {
  padding: 30px 20px;
  display: flex;
}
.assetsUl li {
  margin-right: 20px;
  width: 172px;
  height: 100px;
  border: 1px solid #eee;
  box-shadow: 0px 0px 10px #f3f3f3;
  padding: 30px 0px 0 12px;
  display: flex;
}
.assetsUlRight {
  margin-left: 10px;
  font-size: 12px;
}
.assetsUlRight span:nth-of-type(4) {
  display: block;
}
.transactionDiv {
  padding: 40px 0;
  display: flex;
}
.transactionDiv p {
  text-align: center;
}
.transactionBottom {
  margin-top: 25px;
  font-size: 22px;
  color: #5a8bff;
  text-align: center;
}
.borderLeft {
  border-left: 1px solid #eee;
}
.hiddenUl {
  margin-top: 10px;
}
.hiddenUl li div {
  width: 195px !important;
}
.balanceDialogDiv > div {
  display: flex;
}
.balanceDialogDiv > div > span {
  line-height: 32px;
  height: 32px;
  display: block;
  width: 56px;
  text-align: right;
  margin-right: 5px;
}
.name > span {
  width: 65px !important;
}
.modifypersonDivTop,
.modifypersonDivTop > div {
  display: flex;
}
.modifypersonDivTop > div > span {
  line-height: 32px;
  height: 32px;
  display: block;
  width: 56px;
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
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
.clickClass {
  background-color: #eee !important;
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
/* thead td:nth-of-type(1) {
  width: 220px;
}
thead td:nth-of-type(2) {
  width: 104px;
} */

tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 10px;
  vertical-align: middle !important;
  text-align: center;
}
.content_two td:nth-of-type(2) {
  width: 490px !important;
}
.content {
  margin-top: 10px;
}
.labelEditDialogDiv > div:nth-of-type(1) {
  color: #a3a3a3;
  font-size: 12px;
  margin-bottom: 10px;
}
.memberCardT1DialogTop {
  border-radius: 3px;
  padding: 12px 24px;
  background-color: #ebf1ff;
}
.memberCardT1DialogTop span {
  font-size: 14px;
  line-height: 24px;
  color: #5a8bff;
}
.cardName {
  margin-top: 10px;
}
.cardName i {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: #5a8bff;
  text-align: center;
  line-height: 20px;
  color: #fff;
  font-style: normal;
  display: inline-block;
}
.memberCardT1Main {
  display: flex;
  padding: 5px 0 0 25px;
}
.memberCardT1MainCardsMiddle {
  flex: 1;
}
.memberCardT1MainCards {
  padding: 0 8px;
  border-radius: 2px;
  background-color: #f5f5f5;
  margin-left: 4px;
  margin-bottom: 10px;
  line-height: 24px;
  position: relative;
  white-space: nowrap;
  float: left;
  margin-right: 7px;
}
.memberCardT1MainCards i {
  position: absolute;
  right: -7px;
  top: -7px;
  font-size: 15px;
  color: #999;
  cursor: pointer;
}
.memberCardT1MainRight {
  color: #5a8bff;
  cursor: pointer;
}
.newCardsT1 {
  width: 7px;
  height: 7px;
  border-top: 1px solid #5a8bff;
  border-right: 1px solid #5a8bff;
  transform: rotate(135deg);
  display: inline-block;
  vertical-align: super;
  cursor: pointer;
  position: relative;
  top: 2px;
  margin-right: 3px;
}
.newCardsT2 {
  width: 7px;
  height: 7px;
  border-top: 1px solid #5a8bff;
  border-right: 1px solid #5a8bff;
  transform: rotate(-45deg);
  display: inline-block;
  vertical-align: middle;
  position: relative;
  top: 1px;
  margin-right: 3px;
}
.lastTd span {
  white-space: nowrap;
  color: #5a8bff;
  cursor: pointer;
}
.MemberdialogContainer {
  height: 300px;
  overflow-y: auto;
  overflow-x: hidden;
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
.assetsUl li {
  width: 205px !important;
}
</style>
<style>
.balanceDialogDiv .el-input__inner {
  width: 170px !important;
}
.baseInfo .el-dialog__body {
  padding-bottom: 0 !important;
}
.baseInfo .el-dialog__footer {
  border-top: 1px solid #eee;
}
.name .el-input__inner {
  width: 185px !important;
}
.modifypersonDivTop .el-input__inner {
  width: 140px !important;
}
.baseInfo .distpicker-address-wrapper select {
  height: 30px !important;
  padding: 0 10px !important;
  font-size: 12px !important;
}
.specialAddMoney .el-input__inner {
  width: 100px !important;
}
.specialAddMoney .el-input {
  width: 100px !important;
}
</style>
