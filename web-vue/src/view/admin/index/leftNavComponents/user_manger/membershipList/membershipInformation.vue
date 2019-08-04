<template>
  <div class="membersDetailContent">
    <div class="membersDetailContentMain">
      <div class="topContainer">
        <div class="titleEdit"><span>基本信息</span><span @click="handleBaseInfo()">编辑</span></div>
        <div class="topMain">
          <div class="headDiv">
            <img :src="headeImgUrl">
          </div>
          <div class="headRightDiv">
            <ul>
              <li>
                <div class="userName">昵称：用户12813</div>
              </li>
              <li>
                <div>真实姓名：未知</div>
                <div>邀请人：暂无<span
                    class="modifyLinkPerson"
                    @click="hanldeModifyPerson()"
                  >修改联系人</span></div>
                <div>成为客户：2019-08-01(1天内)</div>
              </li>
              <li>
                <div>最近浏览：2019-08-01(1天内)</div>
                <div>手机号：未知</div>
                <div>OpenID：o-2MM5JL0q5cpkqdLfHHaJ7noDkw</div>
              </li>
              <li>
                <div>WxUnionID： 未知</div>
                <div>累积获得积分：0</div>
                <div>累积消费金额：0</div>
              </li>
              <li>
                <div>地址：暂未添加</div>
              </li>
            </ul>

            <ul
              class="hiddenUl"
              v-if="hiddenUlFlag"
            >
              <li>
                <div>来源渠道：后台</div>
                <div>生日：未知</div>
                <div>教育程度：未知</div>
                <div>常住地：未知</div>
                <div>身份证：未知</div>
              </li>
              <li>
                <div>所属行业：未知</div>
                <div>婚姻状况：未知</div>
                <div>月收入：未知</div>
                <div>性别：未知</div>
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
      <div class="titleEdit"><span>标签信息</span><span @click="handleLabelEditOpen()">编辑</span></div>
      <div class="labelList">
        <span
          v-for="(item,index) in lebalDataList"
          :key="index"
          class="lebalSpan"
        >{{item}}<i
            @click="hanldeToDelLabel(index)"
            class="fa fa-remove"
          ></i></span>
      </div>
    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>资产信息</span></div>
      <ul class="assetsUl">
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
            >设置</span>
            <span style="margin-top:10px;color:#5A8BFF;cursor: pointer;display:block">{{item.num}}</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="topContainer">
      <div class="titleEdit"><span>交易统计</span><span>订单列表</span></div>
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
      <div class="titleEdit"><span>分销统计</span></div>
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
        title="编辑"
        :visible.sync="baseInfoDialogVisible"
        width="30%"
        :modal-append-to-body="false"
      >
        <div
          class="balanceDialogDiv"
          style="margin-bottom:30px"
        >
          <div>
            <span>性别</span>
            <el-select
              v-model="GenderValue"
              placeholder="请选择"
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
          </div>
          <div style="margin-top:10px">
            <span>生日</span>
            <el-date-picker
              v-model="birthdayVal"
              type="date"
              placeholder="选择日期"
              default-value="2010-10-01"
              size="small"
            >
            </el-date-picker>
          </div>
          <div
            class="name"
            style="margin-top:10px"
          >
            <span>真实姓名</span>
            <el-input
              size="small"
              v-model="nameInput"
              placeholder="请输入内容"
            ></el-input>
          </div>
          <div style="margin-top:10px">
            <span>所在地</span>
            <ProAndUrbA />
          </div>
          <div style="margin-top:10px">
            <span>婚姻状况</span>
            <el-select
              v-model="MarriageValue"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in MarriageValueOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div style="margin-top:10px">
            <span>月收入</span>
            <el-select
              v-model="incomeValue"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in incomeValueOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div
            style="margin-top:10px"
            class="name"
          >
            <span>身份证</span>
            <el-input
              size="small"
              v-model="IDInput"
              placeholder="请输入内容"
            ></el-input>
          </div>
          <div style="margin-top:10px">
            <span>教育程度</span>
            <el-select
              v-model="educationValue"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in educationValueOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div style="margin-top:10px">
            <span>所在行业</span>
            <el-select
              v-model="industryValue"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in industryValueOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="baseInfoDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="baseInfoDialogVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--修改邀请人弹窗-->
    <div class="baseInfo">
      <el-dialog
        title="选择用户"
        :visible.sync="modifypersonDialogVisible"
        width="50%"
        :modal-append-to-body="false"
      >
        <div
          class="modifypersonDiv"
          style="margin-bottom:30px"
        >
          <div class="modifypersonDivTop">
            <div>
              <span>昵称</span>
              <el-input
                size="small"
                v-model="nameInput"
                placeholder="请输入内容"
              ></el-input>
            </div>
            <div>
              <span style="width:80px">手机号</span>
              <el-input
                size="small"
                v-model="nameInput"
                placeholder="请输入内容"
              ></el-input>
            </div>
            <div>
              <span style="width:90px">真实姓名</span>
              <el-input
                size="small"
                v-model="nameInput"
                placeholder="请输入内容"
              ></el-input>
            </div>
            <el-button
              type="primary"
              size="small"
            >搜索</el-button>
          </div>
          <!--底部表格-->
          <div
            class="content"
            v-if="page_one"
          >
            <table width='100%'>
              <thead>
                <tr>
                  <td>用户ID</td>
                  <td>昵称</td>
                  <td>手机号</td>
                  <td>真实姓名</td>

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
                  <td>{{item.title}}</td>
                  <td>{{item.title}}</td>
                  <td>{{item.title}}</td>
                  <td class="link">{{item.status}}</td>
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
          <el-button @click="modifypersonDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="modifypersonDialogVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--标签信息编辑弹窗-->
    <div class="balanceDialo">
      <el-dialog
        title="标签"
        :visible.sync="labelEditDialogVisible"
        width="25%"
        :modal-append-to-body="false"
      >
        <div
          class="labelEditDialogDiv"
          style="margin-bottom:30px"
        >
          <div>请选择标签</div>
          <el-select
            v-model="labelEditValue"
            placeholder="请选择"
            size="small"
            @change="handleLebelEdit()"
          >
            <el-option
              v-for="item in labelEditValueOptions"
              :key="item.label"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="labelEditDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="labelEditDialogVisible = false"
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
                  <span>{{item}}<i
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
                      <td style="white-space: nowrap;">{{item.title}}</td>
                      <td>{{item.time}}</td>
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
          <el-button @click="memberCardT1DialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="memberCardT1DialogVisible = false"
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
          <el-button @click="memberLabelVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="memberLabelVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import ProAndUrbA from '@/components/system/proAndUrbA'
export default {
  components: { ProAndUrbA },
  data () {
    return {
      headeImgUrl: this.$imageHost + '/image/admin/head_icon.png',
      assetsData: [
        {
          img: this.$imageHost + '/image/admin/asset1.png',
          cardName: '普通卡',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset2.png',
          cardName: '限次卡',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/grade_card_icon.png',
          cardName: '等级卡',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset3.png',
          cardName: '储值余额',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset5.png',
          cardName: '可用积分',
          num: 0,
          numColor: 1,
          haveSetUp: true
        },
        {
          img: this.$imageHost + '/image/admin/asset4.png',
          cardName: '可用优惠券数',
          num: 0,
          numColor: 1,
          haveSetUp: false
        }
      ],
      transactionData: [
        {
          title: '最近下单时间',
          content: '暂未下单'
        },
        {
          title: '客单价',
          content: '￥ 0.00'
        },
        {
          title: '累计下单金额',
          content: '暂未下单'
        },
        {
          title: '累计消费订单数',
          content: '￥ 0.00'
        },
        {
          title: '累计退款',
          content: '0'
        },
        {
          title: '累计退款订单数',
          content: '￥ 0.00'
        }

      ],
      distributionData: [
        {
          title: '获返利订单数量',
          content: '暂未'
        },
        {
          title: '返利商品总金额(元)',
          content: '0.00'
        },
        {
          title: '获返利佣金总金额(元)',
          content: '0.00'
        },
        {
          title: '已提现佣金总金额(元)',
          content: '0.00'
        },
        {
          title: '下级用户数',
          content: '0'
        },
        {
          title: '分销员等级',
          content: '分销员测试'
        },
        {
          title: '分销员分组',
          content: '/'
        }
      ],
      hiddenUlFlag: false,
      checkMoreText: '查看更多',
      baseInfoDialogVisible: false,
      GenderValue: '',
      GenderValueOptions: [
        {
          value: '选项1',
          label: '男'
        }, {
          value: '选项2',
          label: '女'
        }
      ],
      birthdayVal: '',
      nameInput: '',
      MarriageValue: '',
      MarriageValueOptions: [
        {
          value: '选项1',
          label: '已婚'
        }, {
          value: '选项2',
          label: '未婚'
        },
        {
          value: '选项3',
          label: '保密'
        }
      ],
      incomeValue: '',
      incomeValueOptions: [
        {
          value: '选项1',
          label: '2000元以下'
        }, {
          value: '选项2',
          label: '2000-3999元'
        },
        {
          value: '选项3',
          label: '4000-5999'
        },
        {
          value: '选项1',
          label: '6000-7999'
        }, {
          value: '选项2',
          label: '8000元以上'
        }
      ],
      IDInput: '',
      educationValue: '',
      educationValueOptions: [
        {
          value: '选项1',
          label: '初中'
        }, {
          value: '选项2',
          label: '高中'
        },
        {
          value: '选项3',
          label: '中专'
        },
        {
          value: '选项1',
          label: '大专'
        }, {
          value: '选项2',
          label: '本科'
        },
        {
          value: '选项3',
          label: '硕士'
        },
        {
          value: '选项1',
          label: '博士'
        }, {
          value: '选项2',
          label: '其它'
        }
      ],
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
      trList: [
        {
          title: '111',
          path: 'pages/index/index',
          classification: '分类1',
          status: '营业中',
          spanId: ''
        },
        {
          title: '门店列表页',
          path: 'pages/storelist/storelist',
          spanId: '',
          classification: '分类2',
          status: '歇业中'
        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          classification: '分类3',
          spanId: '',
          status: '营业中'
        }

      ],
      trListTwo: [
        {
          title: '省钱月卡',
          time: '2019-06-03 16:11:20',
          content: '会员折扣5.00折；会员专享商品；积分奖励；',
          index: ''
        },
        {
          title: '省钱月卡2',
          time: '2019-06-03 16:11:20',
          content: '会员折扣5.00折；会员专享商品；积分奖励；',
          index: ''
        },
        {
          title: '省钱月卡3',
          time: '2019-06-03 16:11:20',
          content: '会员折扣5.00折；会员专享商品；积分奖励；',
          index: ''
        },
        {
          title: '省钱月卡4',
          time: '2019-06-03 16:11:20',
          content: '会员折扣5.00折；会员专享商品；积分奖励；',
          index: ''
        },
        {
          title: '省钱月卡5',
          time: '2019-06-03 16:11:20',
          content: '会员折扣5.00折；会员专享商品；积分奖励；',
          index: ''
        }
      ],
      clickIindex: null,
      noImg: 'http://mpimg2.weipubao.cn/image/admin/no_data.png',
      labelEditDialogVisible: false,
      labelEditValue: '',
      labelEditValueOptions: [
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
      lebalDataList: ['计算机软件1', '计算机硬件2', '计算机硬件3'],
      memberCardT1DialogVisible: false,
      cardLlabelsdATa: [],
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
  methods: {
    // 点击查看更多
    handleCheckMore () {
      this.hiddenUlFlag = !this.hiddenUlFlag
      if (this.hiddenUlFlag === true) {
        this.checkMoreText = '收起'
      } else {
        this.checkMoreText = '查看更多'
      }
      console.log(this.hiddenUlFlag)
    },
    // 基本信息编辑弹窗
    handleBaseInfo () {
      this.baseInfoDialogVisible = true
    },
    // 行点击
    handleClick (index) {
      this.clickIindex = index
    },
    // 点击修改联系人
    hanldeModifyPerson () {
      this.modifypersonDialogVisible = true
    },
    // 点击标签信息编辑
    handleLabelEditOpen () {
      this.labelEditDialogVisible = true
    },
    // 标签信息编辑弹窗选中值
    handleLebelEdit () {
      this.lebalDataList.push(this.labelEditValue)
      this.labelEditDialogVisible = false
    },
    // 点击添加新卡
    hanldeToTurnRows () {
      this.newCardsT1Flag = !this.newCardsT1Flag
      this.memberTableFlag = !this.memberTableFlag
    },
    // 点击设置会员卡里卡片删除icon
    hanldeToDelCard (index) {
      this.cardLlabelsdATa.splice(index, 1)
    },
    // 设置会员卡弹窗表格选中
    handleClickMemberCard (index) {
      this.trListTwo[index].index = index
      this.cardLlabelsdATa.push(this.trListTwo[index].title)
    },
    // 资产信息点击设置
    handleSetUp (index) {
      switch (index) {
        case 0:
          this.memberCardT1DialogVisible = true
          break
        case 1:
          this.memberCardT1DialogVisible = true
          break
        case 2:
          this.memberCardT1DialogVisible = true
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
    // 标签列表子项删除
    hanldeToDelLabel (index) {
      this.lebalDataList.splice(index, 1)
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
  min-width: 1300px;
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
</style>
<style>
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
