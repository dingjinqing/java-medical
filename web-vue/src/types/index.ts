export interface GoodsDeliverTemplateLimitParamType {
  limit_deliver_area: number;
  area_list: number;
  area_text: string;
  first_num: number;
  first_fee: number;
  continue_num: number;
  continue_fee: number;
}
export interface GoodsDeliverTemplateAreaParamType {
  area_list: string;
  area_text: string;
  first_num: number;
  first_fee: number;
  continue_num: number;
  continue_fee: number;
}
export interface GoodsDeliverTemplateFeeParamType {
  has_fee_0_condition: number;
}
export interface GoodsDeliverTemplateFeeConditionParamType {
  area_list: string;
  area_text: string;
  fee_0_condition: number;
  fee_0_con1_num?: number;
  fee_0_con2_num?: number;
  fee_0_con3_num?: number;
  fee_0_con3_fee?: number;
}
export interface DeliveryType {
  templateName: string;
  flag?: number;
  goodsDeliverTemplateLimitParam: GoodsDeliverTemplateLimitParamType;
  goodsDeliverTemplateAreaParam: GoodsDeliverTemplateAreaParamType[];
  goodsDeliverTemplateFeeParam: GoodsDeliverTemplateFeeParamType;
  goodsDeliverTemplateFeeConditionParam: GoodsDeliverTemplateFeeConditionParamType[];
}
/**
 * LocatTP的checkList返回的函数的数据类型
 */
export interface LocatTPType {
  areaList: string[];
  checkList: number[];
  idList: number[];
  innerObj: {
    Symbol(): number
  };
  showArr: string[];
}
