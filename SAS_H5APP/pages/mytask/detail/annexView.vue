<template>
	<view class="backGround">
		<view class="pack">
			<chooseImage :num="9" :isSave="false" :imageList="imageList" @uploadPhotoSuccess="uploadPhotoSuccess"
			 @deletePhotoSuccess="deletePhotoSuccess" stageStatus="1" />
		</view>
	</view>
</template>

<script>
	import { getWorkOrderAnnex } from '@/api/Ticket.js'
	
	export default {
		name: "annexView",
		data() {
			return {
				id: '',
				imageList: [],
			}
		},
		components: { 
			chooseImage: () => import('@/components/xyz-choose-image/xyz-choose-image.vue')
		},
		onLoad(option) {
			this.id = option.ticketId
			getWorkOrderAnnex({ticketId: this.id}).then(res => {
				let list = res.data.result;
				for (var i = 0; i < list.length; i++) {
					let obj = {path: this.$IP + list[i]}
					this.imageList.push(obj);
				}
			})
		},
		computed: {
			
		},
		methods: {
			uploadPhotoSuccess(entityList) {
				
			},
			deletePhotoSuccess(entityList, id) {
				
			},
		}
	}
</script>

<style>
	.backGround {
		width: 100%;
		height: auto;
		background-color: #FFFFFF;
	}
	.pack {
		margin: 40rpx 40rpx 40rpx 40rpx;
		width: auto;
		height: auto;
	}
</style>
