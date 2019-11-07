<template>
  <div class="layoutTableMain">
    <table
      class="gridtable"
      width='100%'
    >
      <!--rowspan: 1,
       colspan: 1,-->
      <tbody>
        <tr
          v-for="(item,index) in columnData"
          :key="index"
        >
          <td
            :style="((item['0IsChecked']||item['0IsSlide'])?'backgroundColor:#eaf0ff;':'')+(item['0IsBorder']?'borderColor:#6e86cc':'')+(item['0Dis']?'display:none':'')"
            @click="handleToClick(index,0)"
            :colspan="item['0Col']"
            :rowspan="item['0Row']"
            @mouseenter="enter(index,0)"
            @mouseleave="leave(index,0)"
          ><span v-if="!item['0IsBorder']">+</span></td>
          <td
            :style="((item['1IsChecked']||item['1IsSlide'])?'backgroundColor:#eaf0ff;':'')+(item['1IsBorder']?'borderColor:#6e86cc':'')+(item['1Dis']?'display:none':'')"
            @click="handleToClick(index,1)"
            :colspan="item['1Col']"
            :rowspan="item['1Row']"
            @mouseenter="enter(index,1)"
            @mouseleave="leave(index,1)"
          ><span v-if="!item['1IsBorder']">+</span></td>
          <td
            :style="((item['2IsChecked']||item['2IsSlide'])?'backgroundColor:#eaf0ff;':'')+(item['2IsBorder']?'borderColor:#6e86cc':'')+(item['2Dis']?'display:none':'')"
            @click="handleToClick(index,2)"
            :colspan="item['2Col']"
            :rowspan="item['2Row']"
            @mouseenter="enter(index,2)"
            @mouseleave="leave(index,2)"
          ><span v-if="!item['2IsBorder']">+</span></td>
          <td
            :style="((item['3IsChecked']||item['3IsSlide'])?'backgroundColor:#eaf0ff;':'')+(item['3IsBorder']?'borderColor:#6e86cc':'')+(item['3Dis']?'display:none':'')"
            @click="handleToClick(index,3)"
            :colspan="item['3Col']"
            :rowspan="item['3Row']"
            @mouseenter="enter(index,3)"
            @mouseleave="leave(index,3)"
          ><span v-if="!item['3IsBorder']">+</span></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  props: {
    density: { // 控制布局类型flag
      type: Number,
      default: () => 0
    }
  },
  data () {
    return {
      timesNumber: [4, 5, 6, 7], // 行布局类型尺寸
      columnData: [], // 表格数据
      arrFour: [0, 1, 2, 3], // 4X4数据
      isOpenLayout: false, // 是否开始布局
      nowCheckedArr: [], // 当前选中的项集合
      firstClickRowIndex: null, // 第一次选中的项所在的行
      lastClickRowIndex: null, //  最后一次选中的项所在的行
      firstClickColindex: null, // 第一次选中的项在的列
      lastClickColindex: null, // 最后一次选中的项所在的列
      choiseCellNumber: 1, // 点击次数标识
      isOpenSlide: false
    }
  },
  mounted () {
    //  初始化数据
    this.defaultData()
  },
  methods: {
    // 初始化数据
    defaultData () {
      // 4X4
      let obj1 = {}
      this.arrFour.forEach((item, index) => {
        obj1[`${item}Row`] = '1' // 控制行合并
        obj1[`${item}Col`] = '1' // 控制列合并
        obj1[`${item}Dis`] = false // 控制显示隐藏
        obj1[`${item}IsChecked`] = false // 控制背景色
        obj1[`${item}IsBorder`] = false // 控制高亮
        obj1[`${item}IsSlide`] = false // 控制滑动显示背景色
      })
      this.arrFour.forEach((item, index) => {
        let obj = JSON.parse(JSON.stringify(obj1))
        this.columnData.push(obj)
      })
      console.log(this.columnData)
    },
    // 点击表格单元
    handleToClick (index, item) {
      this.handleToScreen() // 首次筛选判断当前点击是不是首次点击
      if (this.choiseCellNumber === 1) {
        this.columnData[index][`${item}IsChecked`] = true
        this.isOpenSlide = true
        this.choiseCellNumber = 2
        this.nowCheckedArr.push(`${item}`)
        this.firstClickRowIndex = index//  记录第一次点击的行
        this.firstClickColindex = item //  记录第一次点击的列
      } else {
        this.isOpenSlide = false
        this.lastClickRowIndex = index // 记录最后一次点击的行
        this.lastClickColindex = item // 记录最后一次点击的列
        if (this.firstClickRowIndex === index) { // 判断第二次点击是否点击的还是同行
          this.columnData[index][`${item}IsChecked`] = true
          this.handleToScreen()
          console.log(this.nowCheckedArr)
          if (this.firstClickColindex === item) { // 判断第二次点击的是否是同一个单元格
            // 处理两次点击同行同一单元格事件
            console.log(item)
            this.columnData[index][`${item}IsBorder`] = true // 若点击的是同一个则其高亮
            console.log(this.columnData)
            this.nowCheckedArr = []
            console.log(this.nowCheckedArr)
          } else {
            console.log(this.nowCheckedArr)
            // 处理两次点击同行不同单元格事件
            let flag = this.handleToSameRowDifferentCell(index, item)
            if (!flag) return
          }
        } else { // 若第二次点击的是不同行的单元格
          let flag = this.handleToDiffRowDiffCell(index, item)
          if (!flag) return
        }
        this.choiseCellNumber = 1
      }
      console.log(this.nowCheckedArr)
      // 当前已选单元格数量
    },
    // 筛选出当前表格数据项选中的项
    handleToScreen () {
      this.nowCheckedArr = []
      this.columnData.forEach((itemC, indexC) => {
        let isCheckedArr = this.arrFour.filter((itm, ind) => {
          console.log(itemC[`${itm}IsChecked`])
          return itemC[`${itm}IsChecked`]
        })
        console.log(isCheckedArr)
        this.nowCheckedArr = this.nowCheckedArr.concat(isCheckedArr)
        console.log(this.nowCheckedArr)
      })
    },
    // 处理点击同行不同单元格函数
    handleToSameRowDifferentCell (index, item) {
      // 判断是否有效
      let { startRow, startCol, lastRow, lastCol, firstClickColindex } = this.handleToScreenPoints(index, item)
      let isEffective = this.handleToJudgeIsEffective(startRow, startCol, lastRow, lastCol)
      if (!isEffective) {
        this.$message.error({
          message: '请选择有效的正方形或长方形区域',
          showClose: true,
          duration: 1000
        })
        return false
      }
      // let firstClickColindex = Number(this.firstClickColindex)
      let NumItem = Number(item)
      let startingPoint = null // 起点
      if (firstClickColindex > NumItem) {
        startingPoint = NumItem
      } else {
        startingPoint = firstClickColindex
      }
      console.log(index, item, this.firstClickColindex)
      this.columnData[index][`${startingPoint}Col`] = (Math.abs(NumItem - firstClickColindex) + 1) // 第一次点击的单元格合并的列数
      this.columnData[index][`${startingPoint}IsBorder`] = true // 第一次点击的单元格高亮
      console.log((NumItem - firstClickColindex))
      let total = Math.abs(NumItem - firstClickColindex)
      let i = 1
      console.log(NumItem, firstClickColindex, i, total)
      for (i; i <= total; i++) { // 将第一次点的单元格和最后一次点的单元格之间的单元格全部隐藏
        console.log(i)
        startingPoint++
        console.log(startingPoint)
        this.columnData[index][`${startingPoint}Dis`] = true
      }
      // 第二次点击完重置
      this.nowCheckedArr = []
      console.log(this.columnData)
      return true
    },
    // 处理点击不同行不同单元格函数
    handleToDiffRowDiffCell (index, item) {
      console.log(this.firstClickRowIndex, this.firstClickColindex, index, item)
      let { startRow, startCol, lastRow, lastCol } = this.handleToScreenPoints(index, item)
      console.log(startRow, startCol, lastRow, lastCol)
      // 判断区域是否有效
      let isEffective = this.handleToJudgeIsEffective(startRow, startCol, lastRow, lastCol)
      if (!isEffective) {
        this.$message.error({
          message: '只能选择正方形或长方形区域',
          showClose: true,
          duration: 1000
        })
        return false
      }
      this.columnData[startRow][`${startCol}IsBorder`] = true // 起点单元格高亮
      this.columnData[startRow][`${startCol}IsChecked`] = true // 起始单元格高亮
      let startRowCopy = JSON.parse(JSON.stringify(startRow)) // 起始行原始保存
      let lastRowCopy = JSON.parse(JSON.stringify(lastRow)) // 起始行最后点行原始保存
      let startColCopy = JSON.parse(JSON.stringify(startCol)) // 其实列保存
      /*
      *循环处理每行合并列
      */
      let mergeRowNum = lastRow - startRow + 1 // 合并行数
      let mergeRowi = 0
      let colTotal = lastCol - startCol + 1 // 合并列数
      this.columnData[startRow][`${startCol}Col`] = colTotal
      console.log(mergeRowi, mergeRowNum, colTotal)
      let timesRowStart = JSON.parse(JSON.stringify(startRow))
      let coli = null
      console.log(this.columnData)
      for (mergeRowi; mergeRowi < mergeRowNum; mergeRowi++) { // 行循环
        if (timesRowStart === startRow) {
          coli = 1
        } else {
          coli = 0
        }
        console.log(this.columnData)
        let timesColStart = JSON.parse(JSON.stringify(startCol))
        console.log(timesRowStart, colTotal)
        for (coli; coli <= (colTotal - 1); coli++) { // 列循环
          console.log(coli)
          if (timesRowStart === startRow) {
            timesColStart++
            console.log(timesRowStart, timesColStart)
            this.columnData[timesRowStart][`${timesColStart}Dis`] = true
          } else {
            console.log(timesRowStart, timesColStart, coli, colTotal)
            this.columnData[timesRowStart][`${timesColStart}Dis`] = true
            timesColStart++
          }
        }
        timesRowStart++
      }
      console.log(this.columnData)
      // 合并行
      console.log(startRowCopy)
      this.columnData[startRowCopy][`${startColCopy}Row`] = (lastRowCopy - startRowCopy + 1) // 合并行数
      // 第二次点击完重置
      this.nowCheckedArr = []
      return true
    },
    // 判断第二次点击是否是有效位置
    handleToJudgeIsEffective (startRow, startCol, lastRow, lastCol) {
      console.log(this.columnData)
      let flag = true // 是否有效
      // 判断是所选区域内是否有已经隐藏的模块
      for (startRow; startRow <= lastRow; startRow++) { // 循环行
        for (startCol; startCol <= lastCol; startCol++) { // 列循环
          if (this.columnData[startRow][`${startCol}Dis`]) { // 若区域中间有隐藏的单元格则说明不合法
            flag = false
            console.log(111)
            this.columnData[this.lastClickRowIndex][`${this.lastClickColindex}IsChecked`] = false
          }
          if (this.columnData[startRow][`${startCol}IsBorder`]) {
            flag = false
          }
        }
      }
      if (this.columnData[this.lastClickRowIndex][`${this.lastClickColindex}IsBorder`]) { // 如果第二次点中的单元格高亮则恢复其背景色
        this.columnData[this.lastClickRowIndex][`${this.lastClickColindex}IsChecked`] = true
      }
      console.log(flag)
      return flag
    },
    // 鼠标移入
    enter (row, col) {
      if (this.isOpenSlide) {
        // this.columnData[row][`${col}IsSlide`] = true
      }
    },
    // 鼠标移出
    leave (row, col) {
      if (this.isOpenSlide) {
        // this.columnData[row][`${col}IsSlide`] = false
      }
    },
    // 筛选出所选区域左上点和右下点坐标
    handleToScreenPoints (index, item) {
      let firstClickRowIndex = Number(this.firstClickRowIndex)
      let firstClickColindex = Number(this.firstClickColindex)
      let startRow = null // 起点行
      let startCol = null // 起点列
      let lastRow = null // 终点行
      let lastCol = null // 终点列
      if (firstClickRowIndex > index) {
        startRow = index
        lastRow = firstClickRowIndex
      } else {
        startRow = firstClickRowIndex
        lastRow = index
      }
      if (firstClickColindex > item) {
        startCol = item
        lastCol = firstClickColindex
      } else {
        startCol = firstClickColindex
        lastCol = item
      }
      return {
        startRow,
        startCol,
        lastRow,
        lastCol,
        firstClickColindex,
        firstClickRowIndex
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.layoutTableMain {
  border: none !important;
  .gridtable {
    table-layout: fixed;
    tr {
      height: 75px;
      td {
        cursor: pointer;
        background-color: #fff;
        border: 1px solid #dddddd;
        padding-bottom: none;
        vertical-align: middle !important;
        span {
          display: inline-block;
        }
      }
    }
  }
}
</style>
