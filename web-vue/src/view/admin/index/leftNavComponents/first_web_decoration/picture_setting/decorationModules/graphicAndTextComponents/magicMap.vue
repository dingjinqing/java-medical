<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!--列表模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <!--模块编辑区-->
      <div class="magicMap">
        <!--右侧配置无选择占位-->
        <div
          class="seizeASeat"
          v-if="!isHaveImgUrlAll"
        >
          点击编辑魔方
        </div>
        <!--右侧配置有数据-->
        <!---->
        <table
          class="gridtable"
          width='380px'
          :style="('height:'+tableHeight+'px')"
          v-else
        >
          <tbody>
            <tr
              v-for="(item,index) in columnData"
              :key="index"
              :style="'height:'+trHeight"
            >
              <td
                :style="(item['0Dis']?'display:none;':'')"
                :colspan="item['0Col']"
                :rowspan="item['0Row']"
              >
                <div :style="item['0img_url']?'background:url('+item['0img_url']+') no-repeat;background-size:cover;':''">

                </div>
              </td>
              <td
                :style="(item['1Dis']?'display:none;':'')"
                :colspan="item['1Col']"
                :rowspan="item['1Row']"
              >
                <div :style="item['1img_url']?'background:url('+item['1img_url']+') no-repeat;background-size:cover;':''">

                </div>
              </td>
              <td
                :style="(item['2Dis']?'display:none;':'')"
                :colspan="item['2Col']"
                :rowspan="item['2Row']"
              >
                <div :style="item['2img_url']?'background:url('+item['2img_url']+') no-repeat;background-size:cover;':''">

                </div>
              </td>
              <td
                :style="(item['3Dis']?'display:none;':'')"
                :colspan="item['3Col']"
                :rowspan="item['3Row']"
              >
                <div :style="item['3img_url']?'background:url('+item['3img_url']+') no-repeat;background-size:cover;':''">

                </div>
              </td>

              <td
                v-if="density===1||density===2||density===3"
                :style="(item['4Dis']?'display:none;':'')"
                :colspan="item['4Col']"
                :rowspan="item['4Row']"
              >
                <div :style="item['4img_url']?'background:url('+item['4img_url']+') no-repeat;background-size:cover;':''">

                </div>
              </td>

              <td
                v-if="density===2||density===3"
                :style="(item['5Dis']?'display:none;':'')"
                :colspan="item['5Col']"
                :rowspan="item['5Row']"
              >
                <div :style="item['5img_url']?'background:url('+item['5img_url']+') no-repeat;background-size:cover;':''">

                </div>
              </td>

              <td
                v-if="density===3"
                :style="(item['6Dis']?'display:none;':'')"
                :colspan="item['6Col']"
                :rowspan="item['6Row']"
              >
                <div :style="item['6img_url']?'background:url('+item['6img_url']+') no-repeat;background-size:cover;':''">

                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!--模块编辑区结束-->
      <div
        class="item_module_title"
        :style="hoverTips?'width:140px':''"
      >
        <span>魔方多图</span>
      </div>
      <div class="item_operation">
        <img
          class="up_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_up_use.png'"
          @click.stop="handleToClickIcon(0)"
        >
        <img
          class="down_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
          @click.stop="handleToClickIcon(1)"
        >
        <img
          class="del_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
          @click.stop="handleToClickIcon(2)"
        >
      </div>
    </div>
    <!--放这里-->
    <div
      class="setHere activeSetHere"
      :class="activeSetHere?'middleModulesActive':''"
    >
      {{$t('commoditySearch.putItHere')}}
    </div>
  </div>
</template>
<script>

export default {
  props: {
    flag: Number,
    nowRightShowIndex: Number,
    middleHereFlag: Boolean,
    backData: Object
  },
  data () {
    return {
      activeBorder: false,
      activeSetHere: false,
      hoverTips: 'hoverTips', // 英文适配
      columnData: [], // 表格数据
      arrFour: [0, 1, 2, 3, 4, 5, 6], // 表格行计数
      density: null, // 滴8个模板规格
      isHaveImgUrlAll: false, // 数据中是否存在图片路径
      tableHeight: null, // 表格动态高度
      // 模块私有
      moduleSaveData: {

      }
    }
  },
  watch: {
    nowRightShowIndex (newData) {
      if (this.flag === newData) {
        this.activeBorder = true
      } else {
        this.activeBorder = false
      }
    },
    activeSetHere (newData) {
      console.log(newData)
      if (newData) {
        this.$emit('middleDragData', this.flag)
      }
    },
    activeBorder (newData) {
      console.log(newData, this.index)
      if (newData) {
        this.$http.$emit('nowHightLightModules', this.flag)
      }
    },
    middleHereFlag (newData) {
      if (newData) {
        this.activeSetHere = true
      } else {
        this.activeSetHere = false
      }
    },
    // 右侧模块点击传回中间当前高亮模块的数据
    backData: {
      handler (newData) {
        if (newData) {
          // 初始化处理右侧传过来的数据
          this.handleToInit(newData)
          this.moduleSaveData = newData
        }
        console.log(newData)
      },
      immediate: true,
      deep: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      // 点击各模块触发事件
      this.$http.$on('modulesClick', res => {
        console.log(this.flag, res)
        if (this.flag === res) {
          this.activeBorder = true
        } else {
          this.activeBorder = false
        }
        console.log(res)
      })
      // 初始化数据
    },
    // 移上、移下、删除统一处理事件
    handleToClickIcon (flag) {
      console.log(flag)
      let obj = {
        direction: '',
        flag: this.flag
      }
      switch (flag) {
        case 0:
          obj.direction = 'up'
          break
        case 1:
          obj.direction = 'down'
          break
        case 2:
          obj.direction = 'delete'
          break
      }
      this.$emit('handleToClickIcon', obj)
    },
    // 模块划过
    mouseOver () {
      this.$emit('middleDragData', this.flag)
    },
    // 当右侧配置传过来数据时初始化处理函数
    handleToInit (newData) {
      // 判断是数据中的data字段里的单元数据是否都有图片路径  isHaveImgUrlAll
      this.isHaveImgUrlAll = false
      Object.keys(newData.data).forEach((item, index) => {
        if (newData.data[item].img_url) {
          this.isHaveImgUrlAll = true
        }
      })
      console.log(newData)
      if (newData.table_type === 8) {
        this.columnData = []
        this.tableHeight = 380
        switch (newData.table_size['cols']) {
          case 4:
            this.arrFour = [0, 1, 2, 3]
            this.trHeight = '75px'
            this.density = 0
            break
          case 5:
            this.arrFour = [0, 1, 2, 3, 4]
            this.trHeight = '60px'
            this.density = 1
            break
          case 6:
            this.arrFour = [0, 1, 2, 3, 4, 5]
            this.trHeight = '50px'
            this.density = 2
            break
          case 7:
            this.arrFour = [0, 1, 2, 3, 4, 5, 6]
            this.trHeight = '42px'
            this.density = 3
        }
        // 初始构建渲染数据结构
        this.handleToStructureData(newData, this.arrFour, this.arrFour)
      } else {
        console.log(newData)
        this.handleToNotEightTemplate(newData)
      }
    },
    // 初始化构建数据
    handleToStructureData (newData, rows, cols) {
      let obj1 = {}
      console.log(rows, cols)
      cols.forEach((item, index) => {
        obj1[`${item}Row`] = '1' // 控制行合并
        obj1[`${item}Col`] = '1' // 控制列合并
        obj1[`${item}Dis`] = false // 控制显示隐藏
        obj1[`${item}img_url`] = '' // 保存数据使用
      })
      rows.forEach((item, index) => {
        let obj = JSON.parse(JSON.stringify(obj1))
        this.columnData.push(obj)
      })
      console.log(this.columnData, newData)
      console.log(JSON.stringify(newData.data))

      // this.columnData.forEach((item, index) => {
      //   this.arrFour.forEach((itemC, indexC) => {
      //     item[`${itemC}Dis`] = true
      //   })
      // })
      this.$nextTick(() => {
        console.log(this.columnData, newData.data)
        // 把传递的数据对应的单元格Dis设为dalse
        Object.keys(newData.data).forEach((item, index) => {
          console.log(newData.data[item])
          console.log((newData.data[item].x - 1), (newData.data[item].y - 1))
          this.columnData[(newData.data[item].x - 1)][`${(newData.data[item].y - 1)}Dis`] = false
          this.columnData[(newData.data[item].x - 1)][`${(newData.data[item].y - 1)}Row`] = newData.data[item].rows
          this.columnData[(newData.data[item].x - 1)][`${(newData.data[item].y - 1)}Col`] = newData.data[item].cols
          this.columnData[(newData.data[item].x - 1)][`${(newData.data[item].y - 1)}img_url`] = newData.data[item].img_url
        })
        // 处理需要隐藏的模块
        this.handleToNeedHideCells(newData)
        console.log(this.columnData)
      })
    },
    handleToNeedHideCells (newData) {
      Object.keys(newData.data).forEach((item, index) => {
        let mergeRowNum = null // 合并行数
        if (newData.table_type === 2) {
          mergeRowNum = 1
        } else {
          mergeRowNum = newData.data[item].rows // 合并行数
        }

        let mergeRowi = 0
        let colTotal = newData.data[item].cols // 合并列数
        let timesRowStart = JSON.parse(JSON.stringify((newData.data[item].x - 1)))
        let coli = null
        for (mergeRowi; mergeRowi < mergeRowNum; mergeRowi++) { // 行循环
          if (timesRowStart === (newData.data[item].x - 1)) {
            coli = 1
          } else {
            coli = 0
          }
          console.log(this.columnData)
          let timesColStart = JSON.parse(JSON.stringify((newData.data[item].y - 1)))
          console.log(timesRowStart, colTotal)
          for (coli; coli <= (colTotal - 1); coli++) { // 列循环
            console.log(coli)
            if (timesRowStart === (newData.data[item].x - 1)) {
              timesColStart++
              console.log(timesRowStart, timesColStart)
              this.columnData[timesRowStart][`${timesColStart}Dis`] = true
            } else {
              console.log(this.columnData, timesRowStart, timesColStart, coli, colTotal)
              this.columnData[timesRowStart][`${timesColStart}Dis`] = true
              timesColStart++
            }
          }
          timesRowStart++
        }
      })

      console.log(this.columnData)
    },
    // 非第8模板
    handleToNotEightTemplate (newData) {
      console.log(newData)
      this.columnData = []
      let rowsNumber = newData.table_size['rows']
      let colsNumber = newData.table_size['cols']
      let arr1 = []
      let arr2 = []
      if (colsNumber === 3) {
        arr2 = [0, 1, 2]
      } else {
        arr2 = [0, 1, 2, 4]
      }
      switch (rowsNumber) {
        case 1:
          arr1 = [0]
          this.tableHeight = 77
          break
        case 2:
          arr1 = [0, 1]
          this.tableHeight = 77 * 2
          break
        case 4:
          arr1 = [0, 1, 2, 3]
          this.tableHeight = 77 * 4
      }
      console.log(rowsNumber, colsNumber, arr1, arr2)
      this.density = 0
      if (newData.table_type === 2) {
        arr2 = [0, 1, 2, 4, 5, 6]
        this.trHeight = '102px'
      } else {
        this.trHeight = '77px'
      }
      this.handleToStructureData(newData, arr1, arr2)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/style/admin/decorationModules.scss";

.magicMap {
  .seizeASeat {
    height: 125px;
    width: 100%;
    line-height: 125px;
    background: #e8efff;
    text-align: center;
    color: #5a8bff;
    font-size: 14px;
  }
  .gridtable {
    border-collapse: separate;
    table-layout: fixed;
    // border: 1px solid #dddddd;
    tr {
      // height: 75px;
      td {
        cursor: pointer;
        // background-color: #fff;
        // border: 1px solid #dddddd;
        padding-bottom: none;
        vertical-align: middle !important;
        position: relative;
        div {
          width: 100%;
          height: 100%;
        }
      }
    }
  }
}
</style>
