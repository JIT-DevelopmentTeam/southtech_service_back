<template>
	<view>
		<view class="qiun-bg-white qiun-title-bar qiun-common-mt" >
			<view class="qiun-title-dot-light">本年累计</view>
		</view>
		<view class="qiun-charts3">
			<canvas canvas-id="canvasArcbar2" id="canvasArcbar2" class="charts3"></canvas>
			<view class="arcbarDiv">
				<view class="arcbarSpan">接单量：{{ all }}</view>
				<view class="arcbarSpan">完成量：{{ completed }}</view>
			</view>
		</view>
	</view>
</template>

<script>
	import uCharts from '@/utils/u-charts.js';
	import {dataStatistics} from '@/api/Ticket.js';
	var _self;
	var canvaArcbar2;
	
	export default {
		name: 'my',
		data() {
			return {
				cWidth3:'',
				cHeight3:'',
				pixelRatio:1,
				arcbarWidth:'',//圆弧进度图，进度条宽度,此设置可使各端宽度一致
				all:0,
				completed:0
			}
		},
		created() {
			_self = this;
			this.cWidth3=uni.upx2px(350);//这里要与样式的宽高对应
			this.cHeight3=uni.upx2px(350);//这里要与样式的宽高对应
			this.arcbarWidth=uni.upx2px(30);
			this.getServerData();
		},
		methods: {
			getServerData(){
				let Arcbar2={series:[
					{
						name: '完成率',
						data: 0.00,
						color: '#406bdc'
					}
				]};
				dataStatistics({userId: _self.$store.getters['getUserId']}).then(res => {
					if (res.data.code === 200) {
						if (res.data.result.myCompleted !== 0 || res.data.result.myAll !== 0) {
							Arcbar2.series[0].data = res.data.result.myCompleted / res.data.result.myAll;
						}
						_self.all = res.data.result.myAll;
						_self.completed = res.data.result.myCompleted;
						_self.showArcbar2("canvasArcbar2",Arcbar2);
						
					}
				})
			},
			showArcbar2(canvasId,chartData){
				canvaArcbar2=new uCharts({
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
