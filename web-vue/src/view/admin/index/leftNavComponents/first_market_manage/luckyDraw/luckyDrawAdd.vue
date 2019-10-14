<template>
  <div class="luckyDrawAdd">
    <el-card>
      <div class="mainContent">
        <div class="mainContentLeft">
          <section class="drawContent">
            <div class="top"></div>
            <div class="drawBtn">立即抽奖</div>
            <div class="actRules"></div>
          </section>
        </div>

        <div class="mainContentRight">
          <!-- 活动信息 -->
          <div class="boxWrapper">
            <div class="textDesc">活动信息</div>
            <el-form label-width="100px">
              <el-form-item label="活动名称：">
                <el-input
                  size="small"
                  placeholder="最多支持10个字"
                  style="width: 180px;"
                ></el-input>
              </el-form-item>

              <el-form-item label="活动有效期：">
                <div>
                  <span>生效时间：</span>
                  <el-input
                    size="small"
                    style="width: 180px"
                  ></el-input>
                </div>
                <div>
                  <span>过期时间：</span>
                  <el-input
                    size="small"
                    style="width: 180px"
                  ></el-input>
                </div>
              </el-form-item>

              <el-form-item label="活动说明：">
                <el-input
                  type="textarea"
                  :rows="4"
                  placeholder="请输入内容"
                  style="width: 300px;height: 100px;"
                ></el-input>
              </el-form-item>
            </el-form>
          </div>

          <!-- 规则设置 -->
          <div class="boxWrapper">
            <div class="textDesc">规则设置</div>
            <el-form label-width="100px">
              <el-form-item
                label="活动规则："
                prop=""
              >
                <el-radio-group v-model="times">
                  <el-radio :label="1">每人N次</el-radio>
                  <el-radio :label="2">每人每天N次</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="免费抽奖：">
                <el-input
                  size="small"
                  placeholder="为空表示不限制"
                  style="width:125px"
                ></el-input>
                <span class="grayFont">用户可免费抽奖几次</span>
              </el-form-item>

              <el-form-item label="分享抽奖：">
                <el-radio-group v-model="shareLuckyDraw">
                  <el-radio :label="1">允许</el-radio>
                  <el-radio :label="2">不允许</el-radio>
                </el-radio-group>
                <div v-if="shareLuckyDraw === 1">
                  <span>用户无免费抽奖机会时可通过分享给好友获得抽奖机会</span>
                  <div>
                    分享最多获得
                    <el-input
                      size="small"
                      placeholder="为空表示不限制"
                      style="width:125px"
                    ></el-input>
                    次抽奖机会
                  </div>
                </div>
              </el-form-item>

              <el-form-item label="付费抽奖">
                <el-radio-group v-model="payLuckyDraw">
                  <el-radio :label="1">允许</el-radio>
                  <el-radio :label="2">不允许</el-radio>
                </el-radio-group>
                <div v-if="payLuckyDraw===1">
                  <span>用户无法通过分享获取抽奖机会时可通过消耗积分获得抽奖机会</span>
                  <div>
                    每次抽奖消耗积分：
                    <el-input
                      size="small"
                      placeholder="为空表示不消耗积分"
                      style="width:125px"
                    ></el-input>
                    用户每次参与需要消耗积分数
                  </div>
                  <div>
                    付费最多获取
                    <el-input
                      size="small"
                      placeholder="为空表示不限制"
                      style="width:125px"
                    ></el-input>
                    次抽奖机会
                  </div>
                </div>
              </el-form-item>

              <el-form-item label="未中奖赠送积分：">
                <el-input
                  size="small"
                  placeholder="为空表示不赠送积分"
                  style="width: 185px"
                ></el-input>
                <span>仅送给未中奖用户</span>
                <section class="upInfo">
                  <div class="upIcons">
                    <div class="leftContent">
                      <img
                        src=""
                        alt=""
                      >
                    </div>
                    <div class="rightContent">
                      <div class="operate">
                        <span>修改</span>
                        <span class="operateBtn fixstyle">上传未中奖图表</span>
                        <span class="operateBtn">清空</span>
                      </div>
                      <p>仅支持jpg/png/尺寸80*80 不超过1M</p>
                    </div>
                  </div>
                  <div class="iconDesc">
                    <span>icon描述：</span>
                    <el-input
                      size="small"
                      placeholder="最多可填写4个字"
                      style="width: 180px"
                    ></el-input>
                  </div>
                </section>
              </el-form-item>
            </el-form>
          </div>

          <!-- 抽奖设置 -->
          <div class="boxWrapper">
            <div class="textDesc">抽奖设置</div>
            <p class="drawSetting">不同等级分别设置不同的奖项、每个奖项的份数和中奖概率，四个等级中奖概率之和小于等于100%。</p>
            <p class="drawSetting">例如：一等奖1份，中奖概率为2%；二等奖2份，中奖概率为3%；三等奖3份，中奖概率为4%；四等奖4份，中奖概率为5%。则用户A抽奖时，中奖概率为（2%+3%+4%+5%）=14%。</p>
            <!-- 一等奖~四等奖 tab切换 -->
            <el-tabs
              type="border-card"
              class="tabs"
            >
              <el-tab-pane label="一等奖">
                <el-form label="100px">
                  <el-form-item label="中奖概率：">
                    <el-input
                      size="small"
                      style="width:120px"
                    ></el-input>&nbsp;&nbsp;%
                  </el-form-item>

                  <el-form-item label="选择奖品：">
                    <el-radio-group v-model="gift">
                      <el-radio :label="1">积分</el-radio>
                      <el-radio :label="2">用户余额</el-radio>
                      <el-radio :label="3">优惠券</el-radio>
                      <el-radio :label="4">赠品</el-radio>
                      <el-radio :label="5">自定义</el-radio>
                    </el-radio-group>
                    <section style="margin-left: 81px;">
                      <div v-if="gift===1">
                        <span>赠送积分：</span>
                        <el-input
                          size="small"
                          placeholder="请填写积分数"
                          style="width: 120px"
                        ></el-input>
                      </div>
                      <div v-if="gift===2">
                        <span>赠送余额：</span>
                        <el-input
                          size="small"
                          placeholder="请填写余额数"
                          style="width: 120px"
                        ></el-input>
                      </div>
                      <div v-if="gift===3">
                        <span>优惠券：</span>
                        <el-select
                          v-model="couponOptions"
                          size="small"
                          style="width: 120px"
                        >
                          <el-option
                            v-for="item in options"
                            :key="item.value"
                            :value="item.value"
                            :label="item.label"
                          ></el-option>
                        </el-select>
                        <span>刷新</span>
                        |
                        <span>新建</span>
                        <p>优惠券可用库存{{1}}份数</p>
                      </div>
                      <div v-if="gift===4">
                        <div>
                          <span>赠送赠品：</span>
                          <span style="border: 1px solid #ccc;"> + 选择商品</span>
                        </div>
                        <div>
                          赠品有效期：
                          <el-input
                            size="small"
                            style="width:100px"
                          ></el-input>
                          天
                          <p style="margin-left:80px;">中奖用户需在有效期内领取，过期后将无法领取</p>
                        </div>
                      </div>
                      <div v-if="gift===5">
                        <span>自定义：</span>
                        <el-input
                          size="small"
                          placeholder="请填写自定义文字"
                          style="width: 150px"
                        ></el-input>
                      </div>
                      <div>
                        <span>奖品份数：</span>
                        <el-input
                          size="small"
                          placeholder="请填写积分数"
                          style="width: 120px"
                        ></el-input>&nbsp;份
                        <p>奖品份数限制中奖人数，中奖人数达到奖品份数，则后续抽奖用户不再中奖</p>
                        <p>份数为空则不设此奖项，即该奖项中奖概率为0</p>
                      </div>
                    </section>

                  </el-form-item>
                </el-form>
              </el-tab-pane>
              <el-tab-pane label="二等奖">2</el-tab-pane>
              <el-tab-pane label="三等奖">3</el-tab-pane>
              <el-tab-pane label="四等奖">4</el-tab-pane>
            </el-tabs>

            <!-- 一等奖~四等奖 icon图表及其文字设置 -->
            <div class="levelBoxSetting">
              <section class="upInfo levelUpInfo">
                <div class="upIcons">
                  <div class="leftContent">
                    <img
                      src=""
                      alt=""
                    >
                  </div>
                  <div class="rightContent">
                    <div class="operate">
                      <span>修改</span>
                      <span class="operateBtn fixstyle">上传未中奖图表</span>
                      <span class="operateBtn">清空</span>
                    </div>
                    <p>仅支持jpg/png/尺寸80*80 不超过1M</p>
                  </div>
                </div>
                <div class="iconDesc">
                  <span>icon描述：</span>
                  <el-input
                    size="small"
                    placeholder="最多可填写4个字"
                    style="width: 180px"
                  ></el-input>
                </div>
              </section>
            </div>
          </div>
        </div>
      </div>

      <div class="save">
        <el-button
          size="small"
          type="primary"
        >保存</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: `luckyDrawAdd`,
  data () {
    return {
      times: 1,
      shareLuckyDraw: 2,
      payLuckyDraw: 2,
      gift: 4,
      couponOptions: '',
      options: [
        { value: '选项1', label: '前端' },
        { value: '选项2', label: '后端' },
        { value: '选项3', label: '产品经理' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.luckyDrawAdd {
  .mainContent {
    display: flex;
    justify-content: center;
    padding-bottom: 60px;
    .mainContentLeft {
      width: 320px;
      height: 720px;
      border: 1px solid #ccc;
      background: #eee;
      overflow-x: hidden;
      .top {
        height: 55px;
        width: 100%;
        background: url(../../../../../../assets/adminImg/phone_tops.png)
          no-repeat;
      }
      .drawBtn {
        height: 490px;
        width: 330px;
        background-size: 330px 405px;
        overflow-x: hidden;
        // background: url(。。、/image/admin/icon_lottery/lo_bg3.jpg) no-repeat;
      }
    }
    .mainContentRight {
      margin-left: 30px;
      width: 630px;
      .boxWrapper {
        border: 1px solid #e5e5e5;
        background: #f8f8f8;
        padding: 0 12px 22px;
        border-radius: 3px;
        margin-bottom: 10px;
        .textDesc {
          line-height: 40px;
          border-bottom: 1px solid #e5e5e5;
        }
        .upInfo {
          width: 350px;
          border: 1px solid #c0c4cc;
          background: #fff;
          border-radius: 3px;
          padding: 10px 12px;
          .upIcons {
            display: flex;
            padding-bottom: 10px;
            border-bottom: 1px solid #c0c4cc;
            margin-bottom: 10px;
            .leftContent {
              width: 70px;
              height: 70px;
              border: 1px solid #c0c4cc;
              img {
                width: 70px;
                height: 70px;
              }
            }
            .rightContent {
              height: 70px;
              background: #fff;
              .operate {
                display: flex;
                margin-left: 10px;
                height: 30px;
                line-height: 30px;
                :nth-of-type(1) {
                  color: red;
                  cursor: pointer;
                }
                .operateBtn {
                  display: block;
                  height: 30px;
                  border: 1px solid #ccc;
                  cursor: pointer;
                }
                .fixstyle {
                  margin: 0 13px;
                }
              }
              p {
                margin: 0 0 0 10px;
                bottom: 5px;
                color: #999;
              }
            }
          }
        }
        .drawSetting {
          color: #999;
          padding-top: 10px;
          line-height: 24px;
        }
        .tabs {
          margin-top: 10px;
        }
        .levelBoxSetting {
          width: 602px;
          margin-top: 10px;
          .levelUpInfo {
            width: 602px;
          }
        }
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
</style>>
