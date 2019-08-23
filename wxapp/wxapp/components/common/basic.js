import { nav } from "../../common/util/nav.js"

export var basic = {
  properties: {
    imageUrl: {
      type: String,
      value: nav.getImageUrl("")
    },
    baseUrl: {
      type: String,
      value: nav.getUrl("")
    }
  },
  attached(){
    console.log("basic attach");
  }
};