<template>
	<view  class="fl-picker" @tap.stop="!disabled ? showPicker() : ''">
		<view class="box">
			<view class="label-left" v-show="show">
				当前选择
			</view>
			<view class="value-right">
				<slot></slot>
			</view>
		</view>
		<uni-popup ref="popup" :type="typeStyle" :maskClick='maskClick'>
			<view class="picker" catchtouchmove="true">
				<!-- 标题 -->
				<view class="title">
						 <text class="cancel" @tap.stop="closePicker">关闭</text>
						 <text class="ok" v-show="open == '1'" @tap.stop="okClick">确定</text>
				</view>
				<!-- 数据列表 -->
				<view class="list" v-if="listArr.length>0">
					 <view 
						v-for="(item,index) in listArrNew" 
						:key="index" 
						@tap="itemClick(item)"
						:class="{'active':item.select}"
						>{{item.name}}</view>
				</view> 
				<view class="list" v-else>
					 未接收到数据
				</view>
				<!-- 底部说明 -->
				<view class="mess" v-if="mess">
					{{mess}}
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		name: 'fl-picker',
		props: {
			mess: String, //备注说明
			listArr:{ //数据
			  type: Array,
			  default() { return []; }
			},	
			defaultArr: { //默认选中的内容
				type: String,
				default: '' 
			},
			type: { //多选 单选  multiple single 
				type: String,
				default: 'single' 
			},
					
			disabled: { //是否为禁用状态
				type: [String, Boolean],
				default: false
			},
			
			maskClick: {
				type: Boolean,
				default: false
			},
			typeStyle: {
				type: String,
				default: 'bottom'
			},
			open: {
				type: String,
				default: '0'
			},
			show:{
				type: Boolean,
				default: true
			}
		},		
		data() {
			return {
				listArrNew:{},//记录选中的内容
			}
		},
		components: {
			uniPopup: () => import('@dcloudio/uni-ui/lib/uni-popup/uni-popup.vue'),
		},
		watch: {
			//这里需要监听默认值的改变，重新初始化
		    defaultArr(val) {
				this.initValue();
		    }
		},
		methods: {
			showPicker(){
				this.$refs.popup.open()
			},
			//关闭
			closePicker(){				
				this.$refs.popup.close()
				//点遮罩 点取消关闭说明用户不想修改，所以这里对数据进行初始化
				this.initValue(); 
			},
			//组件数据初始化  进入时、关闭时调用初始化
			initValue(){
				//默认值
				let defaultArr = this.defaultArr.split(",");
				
				//将传进来的数组转成对象使用	
				var temp = {}
				for(let item in this.listArr){
					let defalutSelect = false;
					if(defaultArr.length>0 && defaultArr.indexOf(this.listArr[item].value)!=-1) defalutSelect = true;
					temp[item] = {index:item,name:this.listArr[item].value,select:defalutSelect,value:this.listArr[item].key}
				}
				this.listArrNew = temp;
			},
			//选中元素
			itemClick(item) {
				if(item.select){
					item.select = false
				}else{
					item.select = true
				}
				//如果是单选需要清除其选中的项
				if(this.type=="single"){
					for (let item1 in this.listArrNew) {
						var obj = this.listArrNew[item1];
						if( obj.name != item.name) obj.select = false;
					}
				}
			},
			//点击确定
			okClick(){
				var data = {},list = {},textStr = "",indexStr = "",valueStr = "";
				for (let item in this.listArrNew) {
					var obj = this.listArrNew[item];
					if( obj.select){
						list[obj.index] = obj.name
						indexStr += (indexStr==""?"":",") + obj.index
						textStr += (textStr==""?"":",") + obj.name
						valueStr += (valueStr==""?"":",") + obj.value
					}
				}
				data['list'] = list
				data['indexStr'] = indexStr
				data['textStr'] = textStr
				data['valueStr'] = valueStr
				
				this.$emit('click',data)
				//确定后更新默认初始值,这样再次进入默认初值就是最后选择的
				// this.defaultArr = textStr;
				//关闭
				this.closePicker();
			}
		},
		mounted() {
			this.initValue()
		}
	}
</script>

<style lang="scss">
	.fl-picker {
		
		.box {
			width: 100%;
		}
		
		.label-left {
			display: inline;
			width: 10%;
			margin-right: 20rpx;
			font-size: 25upx;
		}
		
		.value-right {
			display: inline;
			width: 90%;
		}
		
		.picker {
			background-color: #FFF;
			color: #666;
			height: 200px;
			
			.title{
				display: flex;
				justify-content: space-between;
				border-bottom: solid 1px #efefef;
				padding:0upx 20upx;
				line-height: 70upx;
				font-size: 28upx;
				font-weight: bold;
				//确定按钮颜色
				.ok{
					color: blue;
				}
			}
			
			.list{
				padding:0upx 10upx;
				padding: 20upx 0upx;
				display: flex;
				flex-flow: wrap;
				//列表元素样式
				view{
					background: #efefef;
				    text-align: center;
				    margin:10upx;
					padding:6upx 20upx;
					border-radius:6upx; 
					color:#777;
					font-size: 30rpx;
				}
				//列表元素选中的样式
				.active{
					background:blue;
					color:#fff;
				}
			}
			
			.mess{
				padding:0upx 20upx;
				margin-bottom: 20upx;
				text-align:left;
			}
		}
		
	}
</style>