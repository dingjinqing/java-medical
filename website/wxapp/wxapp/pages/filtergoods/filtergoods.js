// pages/filtergoods/filtergoods.js
global.wxPage({ 
  /** * 页面的初始数据 */ 
  data: { 
    list: [
      { listName: '活动商品', 
        item: [{ 
          itemName: '多人拼团'
        }, { 
          itemName: '砍价'
        }, {
          itemName: '营运大抽奖'
        }] 
        }, 
      { 
        listName: '商品标签', 
        item: [{ 
          itemName: '双十一'
        }, { 
          itemName: '限时折扣'
        },{
          itemName: '促销'
        }] 
      }
    ] 
  }, 
  //点击最外层列表展开收起 
  listTap(e){
    let Index = e.currentTarget.dataset.parentindex, //获取点击的下标值
    list=this.data.list; 
    list[Index].show = !list[Index].show || false;//变换其打开、关闭的状态
    this.setData({ 
      list 
    }); 
  }, 
  //让所有的展开项，都变为收起 
  packUp(data,index){ 
    for (let i = 0, len = data.length; i < len; i++) {
      //其他最外层列表变为关闭状态 
      if(index!=i){ 
        data[i].show = false; 
        for (let j=0;j<data[i].item.length;j++){
          //其他所有内层也为关闭状态 
          data[i].item[j].show=false; 
        } 
      } 
    } 
  }, 
  onLoad: function (data,index) { 
    // 默认为展开状态
    for (let i = 0, len = data.length; i < len; i++) {
      if (index === i) {
        data[i].show = true;
        
      }
    } 
  },
})
