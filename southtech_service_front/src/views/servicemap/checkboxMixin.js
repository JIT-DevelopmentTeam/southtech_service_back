import Vue from 'vue'
import {getAction} from '@/api/manage'

export const checkboxMixin = {
  methods: {
    checkboxSel(obj, markers) {
      if (obj.checked) {
        markers.push(obj);
        this.center = [obj.longitude, obj.latitude];
      } else {
        if (this.window != '') {
          this.window.visible = false;
        }
        let index = markers.indexOf(obj);
        if (index > -1) {
          markers.splice(index, 1);
        }
        if (markers.length > 0) {
          let o = markers[markers.length - 1];
          this.center = [o.longitude, o.latitude];
        } else {
          this.center = this.oldCenter;
        }
      }
    },
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
        //写后台加载数据的函数
        console.log("距顶部" + scrollTop + "可视区高度" + windowHeight + "滚动条总高度" + scrollHeight);
        setTimeout(() => {
          switch (type) {
            case 'ticket':
              this.tPageNo += 1;
              console.log(this.tPageNo)
              this.loadTicketData(this.tPageNo);
              break;
            case 'enginer':
              this.ePageNo += 1;
              break
          }
          loading = false;
        }, 2000)
      }
    },
    loadTicketData(pageNo) {
      this.loadData('/client/client/listPageByWorkOrder',pageNo,'ticket',this.ticketList)
    },
    loadData(url,pageNo,type,list) {
      getAction(url, {pageNo: pageNo, pageSize: this.pageSize})
        .then(res => {
          console.log("-->>", res);
          let result = res.result;
          for (let i = 0; i < result.length; i++) {
            if (type == 'ticket') {
              var events = new Object();
              events.click = () => {
                this.windows.forEach(window => {
                  window.visible = false;
                });
                this.window = this.windows[i];
                this.$nextTick(() => {
                  this.window.visible = true;
                });
              }
              result[i].events = events
              var html = `<div>名称：${result[i].name}</div><div>地址：${result[i].province + result[i].city + result[i].area + result[i].community + result[i].address}</div>`;
              this.windows.push({
                position: [result[i].longitude, result[i].latitude],
                content: html,
                visible: false
              })
            }
            list.push(result[i]);
          }
        })
        .catch(err => {
        })
    }
  }
}