<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
@import url("./static/iconfont/iconfont.css");
@import './common/uni.css';

.content {
	background-color: #f5f6f8;
	width: 100%;
	height: calc(100vh - 50px - env(safe-area-inset-bottom));
	padding-top: 1px;
}
uni-page-wrapper {
	background-color: #f5f6f8;
}

.uni-grid-item {
	width: 81px;
}

@supports (bottom: env(safe-area-inset-bottom)){
	body,
	.uni-tabbar{
		padding-bottom: constant(safe-area-inset-bottom);
		padding-bottom: env(safe-area-inset-bottom);
	}
}
</style>
