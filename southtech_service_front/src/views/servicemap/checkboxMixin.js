import Vue from 'vue'
import {getAction} from '@/api/manage'

export const checkboxMixin = {
  methods: {
    handleScroll(e, type) {
      //变量scrollTop是滚动条滚动时，距离顶部的距离
      var scrollTop = parseInt(e.target.scrollTop);
      //变量windowHeight是可视区的高度
      var windowHeight = e.target.clientHeight;
      //变量scrollHeight是滚动条的总高度
      var scrollHeight = e.target.scrollHeight;
      var loading = false;
      //滚动条到底部的条件
      if (scrollTop + windowHeight == scrollHeight) {
        if (loading) return;
        loading = true;
        switch (type) {
          case 'ticket':
            this.tspinning = true;
            break;
          case 'enginer':
            this.espinning = true;
            break;
        }
        //写后台加载数据的函数
        console.log("距顶部" + scrollTop + "可视区高度" + windowHeight + "滚动条总高度" + scrollHeight);
        setTimeout(() => {
          switch (type) {
            case 'ticket':
              this.tPageNo += 1;
              console.log(this.tPageNo)
              this.loadTicketData(this.tPageNo);
              this.tspinning = false;
              break;
            case 'enginer':
              this.ePageNo += 1;
              this.espinning = false;
              break
          }
          loading = false;
        }, 2000)
      }
    },
  }
}