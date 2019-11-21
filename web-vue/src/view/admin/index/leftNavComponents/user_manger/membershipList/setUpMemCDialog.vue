<template>
  <div>
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
            <div class="ordinary">
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
                        @click="hanldeToDelCard(index,0)"
                        class="fa fa-remove"
                      ></i></span>
                  </div>
                </div>
                <div
                  class="memberCardT1MainRight"
                  @click="hanldeToTurnRows(0)"
                >
                  <span>添加新卡</span>
                  <i :class="newCardsT1Flag?'newCardsT1':'newCardsT2'"></i>
                </div>
              </div>
              <div
                class="memberCardT1Footer"
                v-if="memberTableFlag"
              >
                <div class="content">
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
                          <span @click="handleClickMemberCard(index,0)">{{item.index===index?'-':'添加'}}</span>
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
              </div>

            </div>

            <div class="limitTimes">
              <div class="cardName">
                <i>1</i>
                限次会员卡
              </div>
              <div class="memberCardT1Main">
                <div>已选：</div>
                <div class="memberCardT1MainCardsMiddle">
                  <div
                    class="memberCardT1MainCards"
                    v-for="(item,index) in limitTimesdATa"
                    :key="index"
                  >
                    <span>{{item}}<i
                        @click="hanldeToDelCard(index,1)"
                        class="fa fa-remove"
                      ></i></span>
                  </div>
                </div>
                <div
                  class="memberCardT1MainRight"
                  @click="hanldeToTurnRows(1)"
                >
                  <span>添加新卡</span>
                  <i :class="newCardsT2Flag?'newCardsT1':'newCardsT2'"></i>
                </div>
              </div>
              <div
                class="memberCardT1Footer"
                v-if="memberTablemiddleFlag"
              >
                <div class="content">
                  <table width='100%'>
                    <thead>
                      <tr>
                        <td>卡名称</td>
                        <td>创建时间</td>
                        <td>卡权益</td>
                        <td></td>
                      </tr>
                    </thead>
                    <tbody v-if="tbodyFlagmiddleFlag">
                      <tr
                        v-for="(item,index) in tbodyFlagmiddle"
                        :key="index"
                        :class="item.index===index?'clickClass':''"
                      >
                        <td style="white-space: nowrap;">{{item.title}}</td>
                        <td>{{item.time}}</td>
                        <td class="link">{{item.content}}</td>
                        <td class="lastTd">
                          <span @click="handleClickMemberCard(index,1)">{{item.index===index?'-':'添加'}}</span>
                        </td>
                      </tr>
                    </tbody>

                  </table>
                  <div
                    class="noData"
                    v-if="!tbodyFlagmiddleFlag"
                  >
                    <img :src="noImg">
                    <span>暂无相关数据</span>
                  </div>
                </div>
              </div>

            </div>

            <div class="GradeCard">
              <div class="cardName">
                <i>1</i>
                等级会员卡
              </div>
              <div class="memberCardT1Main">
                <div>已选：</div>
                <div class="memberCardT1MainCardsMiddle">
                  <div
                    class="memberCardT1MainCards"
                    v-for="(item,index) in GradeCarddATa"
                    :key="index"
                  >
                    <span>{{item}}<i
                        @click="hanldeToDelCard(index,2)"
                        class="fa fa-remove"
                      ></i></span>
                  </div>
                </div>
                <div
                  class="memberCardT1MainRight"
                  @click="hanldeToTurnRows(2)"
                >
                  <span>添加新卡</span>
                  <i :class="newCardsT3Flag?'newCardsT1':'newCardsT2'"></i>
                </div>
              </div>
              <div
                class="memberCardT1Footer"
                v-if="memberTableBottomFlag"
              >
                <div class="content">
                  <table width='100%'>
                    <thead>
                      <tr>
                        <td>卡名称</td>
                        <td>创建时间</td>
                        <td>卡权益</td>
                        <td></td>
                      </tr>
                    </thead>
                    <tbody v-if="tbodyFlagBottomFlag">
                      <tr
                        v-for="(item,index) in tbodyFlagBottomData"
                        :key="index"
                        :class="item.index===index?'clickClass':''"
                      >
                        <td style="white-space: nowrap;">{{item.title}}</td>
                        <td>{{item.time}}</td>
                        <td class="link">{{item.content}}</td>
                        <td class="lastTd">
                          <span @click="handleClickMemberCard(index,2)">{{item.index===index?'-':'添加'}}</span>
                        </td>
                      </tr>
                    </tbody>

                  </table>
                  <div
                    class="noData"
                    v-if="!tbodyFlagBottomFlag"
                  >
                    <img :src="noImg">
                    <span>暂无相关数据</span>
                  </div>
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
            @click="memberCardT1DialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="memberCardT1DialogVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  data () {
    return {
      memberCardT1DialogVisible: false,
      cardLlabelsdATa: [],
      newCardsT1Flag: true,
      memberTableFlag: false,
      tbodyFlag: true,
      tbodyFlagTwo: true,
      noImg: this.$imageHost + '/image/admin/no_data.png',
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
      limitTimesdATa: [],
      newCardsT2Flag: true,
      tbodyFlagmiddle: [
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
      tbodyFlagmiddleFlag: true,
      GradeCarddATa: [],
      newCardsT3Flag: true,
      tbodyFlagBottomFlag: true,
      memberTablemiddleFlag: true,
      memberTableBottomFlag: true,
      tbodyFlagBottomData: [
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
      ]
    }
  },
  computed: {
    ...mapGetters(['toHandleSetUpMemflag']),
    toHandleSetUpMemflag_ () {
      return this.toHandleSetUpMemflag
    }
  },
  watch: {
    toHandleSetUpMemflag_ (newData) {
      console.log(newData)
      this.memberCardT1DialogVisible = newData
    },
    memberCardT1DialogVisible (newData) {
      if (newData === false) {
        this.toHandleSetUpMemDialog(false)
      }
    }
  },
  methods: {
    ...mapActions(['toHandleSetUpMemDialog']),
    // 点击添加新卡
    hanldeToTurnRows (index) {
      switch (index) {
        case 0:
          this.newCardsT1Flag = !this.newCardsT1Flag
          this.memberTableFlag = !this.memberTableFlag
          break
        case 1:
          this.newCardsT2Flag = !this.newCardsT2Flag
          this.memberTablemiddleFlag = !this.memberTablemiddleFlag
          break
        case 2:
          this.newCardsT3Flag = !this.newCardsT3Flag
          this.memberTableBottomFlag = !this.memberTableBottomFlag
      }
    },
    // 点击设置会员卡里卡片删除icon
    hanldeToDelCard (index, flag) {
      switch (flag) {
        case 0:
          this.cardLlabelsdATa.splice(index, 1)
          break
        case 1:
          this.limitTimesdATa.splice(index, 1)
          break
        case 2:
          this.GradeCarddATa.splice(index, 1)
      }
    },
    // 设置会员卡弹窗表格选中
    handleClickMemberCard (index, flag) {
      switch (flag) {
        case 0:
          this.trListTwo[index].index = index
          this.cardLlabelsdATa.push(this.trListTwo[index].title)
          break
        case 1:
          this.tbodyFlagmiddle[index].index = index
          this.limitTimesdATa.push(this.tbodyFlagmiddle[index].title)
          break
        case 2:
          this.tbodyFlagBottomData[index].index = index
          this.GradeCarddATa.push(this.tbodyFlagBottomData[index].title)
      }
    }
  }
}
</script>
<style scoped>
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
.MemberdialogContainer {
  height: 300px;
  overflow-y: auto;
  overflow-x: hidden;
}
.lastTd span {
  white-space: nowrap;
  color: #5a8bff;
  cursor: pointer;
}
.memberCardT1Footer {
  margin-top: 10px;
}
</style>
