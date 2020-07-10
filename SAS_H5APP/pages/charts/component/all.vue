<template>
	<view>
		<view>
			<view class="qiun-bg-white qiun-title-bar qiun-common-mt" >
				<view class="qiun-title-dot-light">本年累计</view>
			</view>
			<view class="qiun-charts3">
				<canvas canvas-id="canvasArcbar1" id="canvasArcbar1" class="charts3"></canvas>
				<canvas canvas-id="canvasArcbar2" id="canvasArcbar2" class="charts3"></canvas>
				<view class="arcbarDiv">
					<view  class="arcbar-div">
						<view class="arcbarSpan">接单：{{ all }}</view>
						<view class="arcbarSpan">完成：{{ completed }}</view>
					</view>
					<view  class="arcbar-div">
						<view class="arcbarSpan">接单：{{ all }}</view>
						<view class="arcbarSpan">待回访：{{ visit }}</view>
					</view>
				</view>
			</view>
		</view>
		<view>
			<view class="qiun-bg-white qiun-title-bar qiun-common-mt" >
				<view class="qiun-title-dot-light">年度接单排名</view>
			</view>
			<view class="qiun-charts" >
				<canvas canvas-id="canvasColumn1" id="canvasColumn1" class="charts" @touchstart="touchColumn1"></canvas>
			</view>
		</view>
		<view>
			<view class="qiun-bg-white qiun-title-bar qiun-common-mt" >
				<view class="qiun-title-dot-light">年度完成排名</view>
			</view>
			<view class="qiun-charts" >
				<canvas canvas-id="canvasColumn2" id="canvasColumn2" class="charts" @touchstart="touchColumn2"></canvas>
			</view>
		</view>
	</view>
</template>

<script>
	import uCharts from '@/utils/u-charts.js';
	import {dataStatistics, ranking} from '@/api/Ticket.js';
	var _self;
	var canvaArcbar1;
	var canvaArcbar2;
	var canvaColumn1=null;
	var canvaColumn2=null;
	
	export default {
		name: 'all',
		data() {
			return {
				cWidth3:'',
				cHeight3:'',
				pixelRatio:1,
				arcbarWidth:'',//圆弧进度图，进度条宽度,此设置可使各端宽度一致
				all: 0,
				completed: 0,
				visit: 0,
				cWidth:'',
				cHeight:'',
			}
		},
		created() {
			_self = this;
			this.cWidth3=uni.upx2px(300);//这里要与样式的宽高对应
			this.cHeight3=uni.upx2px(300);//这里要与样式的宽高对应
			this.arcbarWidth=uni.upx2px(30);
			this.cWidth=uni.upx2px(750);
			this.cHeight=uni.upx2px(500);
			this.getServerData();
		},
		methods: {
			getServerData(){
				let Arcbar1={series:[
					{
						data: 0.00,
						color: '#406bdc'
					}
				]};
				dataStatistics({userId: _self.$store.getters['getUserId'], status: "6"}).then(res => {
					if (res.data.code === 200) {
						if (res.data.result.completed !== 0 || res.data.result.all !== 0) {
							Arcbar1.series[0].data = res.data.result.completed / res.data.result.all;
						}
						_self.all = res.data.result.all;
						_self.completed = res.data.result.completed;
						_self.showArcbar1("canvasArcbar1",Arcbar1);
						
					}
				})
				let Arcbar2={series:[
					{
						data: 0.00,
						color: '#406bdc'
					}
				]};
				dataStatistics({userId: _self.$store.getters['getUserId'], status: "2"}).then(res => {
					if (res.data.code === 200) {
						if (res.data.result.completed !== 0 || res.data.result.all !== 0) {
							Arcbar2.series[0].data = res.data.result.completed / res.data.result.all;
						}
						_self.visit = res.data.result.completed;
						_self.showArcbar2("canvasArcbar2",Arcbar2);
						
					}
				})
				let Column1 = {categories:[],series:[{data:[]}],max: 0};
				ranking({status: ""}).then(res => {
					if (res.data.code === 200) {
						let arr = res.data.result;
						arr.forEach(i => {
							if (i.data > Column1.max) {
								Column1.max = i.data;
							}
							Column1.categories.push(i.name);
							Column1.series[0].data.push(i.data);
						})
						_self.showColumn1("canvasColumn1",Column1);
					}
				})
				let Column2 = {categories:[],series:[{data:[]}],max: 0};
				ranking({status: "6"}).then(res => {
					if (res.data.code === 200) {
						let arr = res.data.result;
						arr.forEach(i => {
							if (i.data > Column2.max) {
								Column2.max = i.data;
							}
							Column2.categories.push(i.name);
							Column2.series[0].data.push(i.data);
						})
						_self.showColumn2("canvasColumn2",Column2);
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
			showColumn1(canvasId,chartData){
				canvaColumn1=new uCharts({
					$this:_self,
					canvasId: canvasId,
					type: 'column',
					legend:{show:false},
					fontSize:11,
					background:'#FFFFFF',
					pixelRatio:_self.pixelRatio,
					animation: true,
					categories: chartData.categories,
					series: chartData.series,
					xAxis: {
						disableGrid:true,
					},
					yAxis: {
						//disabled:true
						min:1,
						max: chartData.max+5
					},
					dataLabel: true,
					width: _self.cWidth*_self.pixelRatio,
					height: _self.cHeight*_self.pixelRatio,
					extra: {
						column: {
							type:'group',
							width: _self.cWidth*_self.pixelRatio*0.45/chartData.categories.length
						}
					  }
				});
				
			},
			showColumn2(canvasId,chartData){
				canvaColumn2=new uCharts({
					$this:_self,
					canvasId: canvasId,
					type: 'column',
					legend:{show:false},
					fontSize:11,
					background:'#FFFFFF',
					pixelRatio:_self.pixelRatio,
					animation: true,
					categories: chartData.categories,
					series: chartData.series,
					xAxis: {
						disableGrid:true,
					},
					yAxis: {
						//disabled:true
						min:1,
						max: chartData.max+5
					},
					dataLabel: true,
					width: _self.cWidth*_self.pixelRatio,
					height: _self.cHeight*_self.pixelRatio,
					extra: {
						column: {
							type:'group',
							width: _self.cWidth*_self.pixelRatio*0.45/chartData.categories.length
						}
					  }
				});
				
			},
			touchColumn1(e){
				canvaColumn1.showToolTip(e, {
					format: function (item, category) {
						if(typeof item.data === 'object'){
							return category + ': ' + item.data.value 
						}else{
							return category + ': ' + item.data 
						}
					}
				});
			},
			touchColumn2(e){
				canvaColumn2.showToolTip(e, {
					format: function (item, category) {
						if(typeof item.data === 'object'){
							return category + ': ' + item.data.value 
						}else{
							return category + ': ' + item.data 
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
		height: 420upx;
		// background-color: #FFFFFF;
		// position: relative;
		margin-left: 50upx;
	}

	.charts3 {
		width: 350upx;
		height: 300upx;
		// margin-left: 50upx;
		display: inline-block;
	}
	
	
	.qiun-title-dot-light {
		color: blue;
		font-size: 40upx;
		margin-left: 20upx;
	}
	
	.arcbarSpan {
		color: blue;
		display: inline;
		margin-left: 20upx;
	}
	
	.arcbarDiv {
		margin-left: -90upx;
	}
	
	.arcbar-div {
		display: inline;
		margin-left: 90upx;
	}
	
	page{background:#F2F2F2;width: 750upx;overflow-x: hidden;}
	.qiun-padding{padding:2%; width:96%;}
	.qiun-wrap{display:flex; flex-wrap:wrap;}
	.qiun-rows{display:flex; flex-direction:row !important;}
	.qiun-columns{display:flex; flex-direction:column !important;}
	.qiun-common-mt{margin-top:10upx;}
	.qiun-bg-white{background:#FFFFFF;}
	.qiun-title-bar{width:96%; padding:10upx 2%; flex-wrap:nowrap;}
	.qiun-title-dot-light{border-left: 10upx solid #0ea391; padding-left: 10upx; font-size: 32upx;color: #000000}
	.qiun-charts{width: 750upx; height:500upx;background-color: #FFFFFF;}
	.charts{width: 750upx; height:500upx;background-color: #FFFFFF;}
</style>
