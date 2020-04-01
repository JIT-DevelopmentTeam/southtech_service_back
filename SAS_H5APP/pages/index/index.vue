<template>
	<view class="content">
		<view v-for="(ditem,index) in dataList" :key="index">
			<view v-if="ditem.show == undefined || ditem.show">
				<nav-div :title="ditem.title">
					<view v-for="(titem,index) in ditem.textList" :key="index">
						<nav-icon :text="titem.text" :icon="titem.icon" :color-style="titem.color" @clickItem="clickItem(titem.pageType, titem.url)"></nav-icon>
					</view>
				</nav-div>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name:'index',
		data() {
			return {
				dataList: [{
						title: "服务管理",
						textList: [{
								text: "客户管理",
								icon: "icontongxunlu",
								color: "#97f079",
								pageType: 'page',
								url: ''
							},
							{
								text: "工单管理",
								icon: "icongongdan",
								color: "#79ddee",
								pageType: 'tab',
								url: '/pages/mytask/index'
							},
							{
								text: "客户应收",
								icon: "iconweibaogongdan",
								color: "#d679ef",
								pageType: 'page',
								url: ''
							},
							{
								text: "故障代报",
								icon: "iconrizhiyichang",
								color: "#be78f2",
								pageType: 'page',
								url: '/pages/failuredeclaration/FailureDeclaration'
							}
						]
					},
					{
						title: "日常管理",
						textList: [{
								text: "新增签到",
								icon: "iconqiandao",
								color: "#9679f0",
								pageType: 'page',
								url: ''
							},
							{
								text: "工作汇报",
								icon: "iconzhuanxierizhi",
								color: "#f37ae5",
								pageType: 'page',
								url: ''
							},
							{
								text: "任务管理",
								icon: "iconzhuzhuangtu",
								color: "#7a9df2",
								pageType: 'page',
								url: ''
							},
							{
								text: "流程申报",
								icon: "iconbiangeng",
								color: "#7bf199",
								pageType: 'page',
								url: ''
							}
						]
					},
					{
						title: "辅助支持",
						textList: [{
								text: "知识库",
								icon: "iconzhishi",
								color: "#a9ef7b",
								pageType: 'page',
								url: ''
							},
							{
								text: "知识检索",
								icon: "iconrongyu",
								color: "#74f1f1",
								pageType: 'page',
								url: ''
							},
							{
								text: "附近客户",
								icon: "icondingwei",
								color: "#f28f79",
								pageType: 'page',
								url: ''
							},
							{
								text: "操作规程",
								icon: "iconhetongdingdan",
								color: "#7ef079",
								pageType: 'page',
								url: ''
							}
						]
					},
					{
						title: "其他功能",
						textList: [{
								text: "贡献榜",
								icon: "iconkaoqintongji1",
								color: "#f1bb7a",
								pageType: 'page',
								url: ''
							},
							{
								text: "库存查询",
								icon: "iconfuwuqi",
								color: "#7aeeef",
								pageType: 'page',
								url: ''
							},
							{
								text: "产品追溯",
								icon: "iconbanben",
								color: "#a9f07b",
								pageType: 'page',
								url: ''
							},
							{
								text: "消息管理",
								icon: "iconxiaoxi",
								color: "#7886f1",
								pageType: 'page',
								url: ''
							}
						]
					},
					{
						title: "审核",
						show: false,
						textList: [{
								text: "客户用户审核",
								icon: "iconwodegongdan",
								color: "#d81e06",
								pageType: 'page',
								url: ''
							}
						]
					}
				]
			}
		},
		components: {
			navDiv: () => import('@/components/nav-div/nav-div.vue'),
			navIcon: () => import('@/components/nav-icon/nav-icon.vue')
		},
		onLoad() {
			this.$store.dispatch("GET_CODE", this.$corpId).then(async res => {
				await this.$store.dispatch("GET_USER_INFO")
			})
		},
		methods: {
			clickItem(pageType, url) {
				if (pageType === 'tab') {
					uni.switchTab({
						url: url
					})
				} else {
					uni.navigateTo({
						url: url
					})
				}
			}
		}
	}
</script>

<style scoped>
</style>
