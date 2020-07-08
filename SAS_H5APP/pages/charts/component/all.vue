<template>
	<view>
		<view>
			<view class="qiun-bg-white qiun-title-bar qiun-common-mt" >
				<view class="qiun-title-dot-light">本年累计</view>
			</view>
			<view class="qiun-charts3">
				<canvas canvas-id="canvasArcbar1" id="canvasArcbar1" class="charts3"></canvas>
				<view class="arcbarDiv">
					<view class="arcbarSpan">接单量：{{ all }}</view>
					<view class="arcbarSpan">完成量：{{ completed }}</view>
				</view>
			</view>
		</view>
		<view>
			<view class="qiun-bg-white qiun-title-bar qiun-common-mt" >
				<view class="qiun-title-dot-light">年度接单排名</view>
			</view>
			<view class="qiun-charts" >
				<canvas canvas-id="canvasFunnel1" id="canvasFunnel1" class="charts" :width="cWidth*pixelRatio" :height="cHeight*pixelRatio" :style="{'width':cWidth+'px','height':cHeight+'px'}" @touchstart="touchFunnel1"></canvas>
			</view>
		</view>
		<view>
			<view class="qiun-bg-white qiun-title-bar qiun-common-mt" >
				<view class="qiun-title-dot-light">年度完成排名</view>
			</view>
			<view class="qiun-charts" >
				<canvas canvas-id="canvasFunnel2" id="canvasFunnel2" class="charts" :width="cWidth*pixelRatio" :height="cHeight*pixelRatio" :style="{'width':cWidth+'px','height':cHeight+'px'}" @touchstart="touchFunnel2"></canvas>
			</view>
		</view>
	</view>
</template>

<script>
	import uCharts from '@/utils/u-charts.js';
	import {dataStatistics, ranking} from '@/api/Ticket.js';
	var _self;
	var canvaArcbar1;
	var canvaFunnel1=null;
	var canvaFunnel2=null;
	
	export default {
		name: 'all',
		data() {
			return {
				cWidth3:'',
				cHeight3:'',
				pixelRatio:1,
				arcbarWidth:'',//圆弧进度图，进度条宽度,此设置可使各端宽度一致
				textarea:'',
				cWidth:'',
				cHeight:'',
				all: 0,
				completed: 0
			}
		},
		created() {
			_self = this;
			this.cWidth3=uni.upx2px(350);//这里要与样式的宽高对应
			this.cHeight3=uni.upx2px(350);//这里要与样式的宽高对应
			this.arcbarWidth=uni.upx2px(30);
			this.cWidth=uni.upx2px(750);
			this.cHeight=uni.upx2px(500);
			this.getServerData();
		},
		methods: {
			getServerData(){
				let Arcbar1={series:[
					{
						name: '完成率',
						data: 0.00,
						color: '#406bdc'
					}
				]};
				dataStatistics({userId: _self.$store.getters['getUserId']}).then(res => {
					if (res.data.code === 200) {
						if (res.data.result.completed !== 0 || res.data.result.all !== 0) {
							Arcbar1.series[0].data = res.data.result.completed / res.data.result.all;
						}
						_self.all = res.data.result.all;
						_self.completed = res.data.result.completed;
						_self.showArcbar1("canvasArcbar1",Arcbar1);
						
					}
				})
				let Funnel1={series:[]};
				ranking({status: ""}).then(res => {
					if (res.data.code === 200) {
						Funnel1.series = res.data.result;
						_self.showFunnel1("canvasFunnel1",Funnel1);
					}
				})
				let Funnel2={series:[]};
				ranking({status: "6"}).then(res => {
					if (res.data.code === 200) {
						Funnel2.series = res.data.result;
						_self.showFunnel2("canvasFunnel2",Funnel2);
					}
				})
			},
			showArcbar1(canvasId,chartData){
				canvaArcbar1=new uCharts({
					$this:_self,
					canvasId: canvasId,
					type: 'arcbar',
					fontSize:11,
					legend:{show:false},
					background:'#FFFFFF',
					pixelRatio:_self.pixelRatio,
					series: chartData.series,
					animation: true,
					width: _self.cWidth3*_self.pixelRatio,
					height: _self.cHeight3*_self.pixelRatio,
					dataLabel: true,
					title: {
						name: Math.round(chartData.series[0].data*100)+'%',//这里我自动计算了，您可以直接给任意字符串
						color: chartData.series[0].color,
						fontSize: 25*_self.pixelRatio
					},
					subtitle: {
						name: chartData.series[0].name,//这里您可以直接给任意字符串
						color: '#666666',
						fontSize: 15*_self.pixelRatio
					},
					extra: {
						arcbar:{
							type:'circle',//整圆类型进度条图
							width: _self.arcbarWidth*_self.pixelRatio,//圆弧的宽度
							startAngle:0.5//整圆类型只需配置起始角度即可
						}
					}
				});
				
			},
			showFunnel1(canvasId,chartData){
				canvaFunnel1=new uCharts({
					$this:_self,
					canvasId: canvasId,
					type: 'funnel',
					fontSize:11,
					padding:[15,15,0,15],
					legend:{
						show:true,
						padding:5,
						lineHeight:11,
						margin:0,
					},
					background:'#FFFFFF',
					pixelRatio:_self.pixelRatio,
					series: chartData.series,
					animation: true,
					width: _self.cWidth*_self.pixelRatio,
					height: _self.cHeight*_self.pixelRatio,
					dataLabel: false,
					extra: {
						funnel: {
			  border:true,
			  borderWidth:2,
			  borderColor:'#FFFFFF'
						}
					},
				});
			},
			showFunnel2(canvasId,chartData){
				canvaFunnel2=new uCharts({
					$this:_self,
					canvasId: canvasId,
					type: 'funnel',
					fontSize:11,
					padding:[15,15,0,15],
					legend:{
						show:true,
						padding:5,
						lineHeight:11,
						margin:0,
					},
					background:'#FFFFFF',
					pixelRatio:_self.pixelRatio,
					series: chartData.series,
					animation: true,
					width: _self.cWidth*_self.pixelRatio,
					height: _self.cHeight*_self.pixelRatio,
					dataLabel: false,
					extra: {
						funnel: {
			  border:true,
			  borderWidth:2,
			  borderColor:'#FFFFFF'
						}
					},
				});
			},
			touchFunnel1(e){
				canvaFunnel1.showToolTip(e, {
					format: function (item) {
						return item.name + ':' + item.data 
					}
				});
				canvaFunnel1.touchLegend(e,{animation:true});
			},
			touchFunnel2(e){
				canvaFunnel2.showToolTip(e, {
					format: function (item) {
						return item.name + ':' + item.data 
					}
				});
				canvaFunnel2.touchLegend(e,{animation:true});
			},
		}
	}
</script>

<style lang="scss">
/*样式的width和height一定要与定义的cWidth和cHeight相对应*/
	.qiun-charts3 {
		width: 750upx;
		height: 460upx;
		background-color: #FFFFFF;
		position: relative;
	}

	.charts3 {
		width: 750upx;
		height: 350upx;
		margin-left: 200upx;
	}
	
	/*样式的width和height一定要与定义的cWidth和cHeight相对应*/
		.qiun-charts {
			width: 750upx;
			height: 560upx;
			background-color: #FFFFFF;
		}
		
		.charts {
			width: 750upx;
			height: 500upx;
			background-color: #FFFFFF;
		}
	
	.qiun-title-dot-light {
		color: blue;
		font-size: 40upx;
		margin-left: 20upx;
	}
	
	.arcbarSpan {
		color: blue;
		display: inline;
		margin-left: 50upx;
	}
	
	.arcbarDiv {
		margin-left: 170upx;
	}
</style>
