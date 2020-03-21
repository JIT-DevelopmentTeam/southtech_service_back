<template>
	<view>
		<view class="imgs">
			<!-- file -->
			<view class="file" v-for="(item, key) in list" :key="key" v-if="item.type == 'file'">
				<view class="noImg" @click="false ? downLoad(item.url) : ''" @longpress="deleteItem(key)">{{ item.fileName }}</view>
				<progress :percent="item.process" activeColor="#67C23A" :backgroundColor="item.process == 100 || item.process == undefined ? '#67C23A' : '#F56C6C'" stroke-width="3" v-if="mode == 'create' && showProcess" />
				<view v-show="stageStatus != 1">
					<view class="del" @tap="deleteItem(key)">×</view>
				</view>
			</view>
			<!-- add button -->
			<view v-show="stageStatus != 1" style="width: 100%;">
				<button class="btn" type="primary" @click="chooseFile">上传文件</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {delUploadFile} from '@/api/Ticket.js'
	import {guid} from '@/utils/common.js'
	
	export default {
		props: {
			attachmentList: {
				type: Array //附件列表
			},
			mode: {
				type: String //模式： create => 可新增或编辑附件 不填或其他 => 只能查看附件
			},
			showProcess: {
				type: Boolean,
				default: true //是否显示进度，默认显示
			},
			header: {
				type: Object //上传图片到服务器时，HTTP 请求 Header
			},
			limit: {
				type: Number, //限制可上传的图片数量
				default: 9
			},
			fileKeyName: {
				type: String,
				default: 'file' //用于在服务端通过自定义key值获取该文件数据
			},
			canUploadFile: {
				type: Boolean,
				default: false
			},
			stageStatus: {	// 阶段完成状态（为 1 就是已完成，只能查看；为 0 就是继续，可编辑）
				type: String
			},
			fileArr: {	// 上传文件后 后端返回的id和name（查看的时候才用到）
				type: Array
			}
		},
		computed: {
			list() {
				return this.attachmentList;
			}
		},
		data() {
			return {
				fileList:[],
				uploadFileUrl: this.$IP + '/f/mobile/upload/uploadFile', //替换成你的后端接收文件地址
			};
		},
		methods: {
			previewImg(url) {
				uni.previewImage({
					current: 0,
					urls: [url]
				});
			},
			downLoad(url) {
				uni.showModal({
					title: '确定要下载此附件吗',
					content: ' ',
					success: res => {
						if (res.confirm) {
							uni.showLoading({
								title: '下载中,请稍后'
							});
							console.log(url);
							uni.downloadFile({
								url: url,
								success: res => {
									var tempFile = res.tempFilePath;
									uni.saveFile({
										tempFilePath: res.tempFilePath,
										success: res => {
											var savedFilePath = res.savedFilePath;
											uni.hideLoading();
											uni.showToast({
												title: '保存成功，路径为' + savedFilePath
											});
											uni.openDocument({
												filePath: savedFilePath,
												success: function(res) {
													console.log(res);
												}
											});
										}
									});
								},
								fail: res => {
									console.log(res);
									uni.hideLoading();
									uni.showToast({
										title: '下载失败',
										icon: 'none'
									});
								}
							});
							setTimeout(function() {
								uni.hideLoading();
							}, 20000);
							// downloadTask.onProgressUpdate((res) => {
							//     console.log('下载中,进度' +  res.progress)
							// });
						}
					}
				});
			},
			deleteItem(index) {
				uni.showModal({
					title: '提示',
					content: '确定要删除此项吗？',
					success: res => {
						if (res.confirm) {
							if (this.list[index].process != 100) {
								typeof this.list[index].uploadTask != 'undefined' && this.list[index].uploadTask.abort();
							}
							var id
							this.fileList.forEach((i, idx) => {
								if (i.id == this.list[index].id) {
									uni.showToast({
										title: '删除成功',
										icon: 'none'
									});
									id = i.id
									this.fileList.splice(idx, 1)
								}
							})
							this.list.splice(index, 1);
							this.$forceUpdate();
							this.$emit('deleteSuccess', res,this.fileList, id);
							this.$emit('update:attachmentList', this.list);
						}
					}
				});
			},
			async chooseFile() {
				if (this.limit != null && !isNaN(this.limit)) {
					if (this.list.length >= this.limit) {
						uni.showToast({
							title: '已达到最大上传数量',
							icon: 'none'
						});
						return;
					}
				}
				var canUploadFile = this.canUploadFile;
				// 非APP平台不可以上传文件
				// #ifndef APP-PLUS || MP-WEIXIN
				canUploadFile = false;
				// #endif
				// #ifdef APP-PLUS
				// APP 需调用uniapp插件市场的chuck-filemanager插件，可以选择试用，然后在mainfest.json中勾选该插件，既可在自定义基座中运行
				// chuck-filemanager插件地址 https://ext.dcloud.net.cn/plugin?id=680
				if (canUploadFile) {
					const open = uni.requireNativePlugin('chuck-filemanager');
					var temp = await new Promise(resoleve => {
						open.onIntent('996', path => {
							resoleve(path);
						});
					})
					var tempFiles = ['file:///' + temp];
				}
				// #endif
				if (!canUploadFile) {
					var temps = await uni.chooseImage({
						count: this.limit == null || this.limit - this.list.length > 9 ? 9 : 9 - this.limit,
						sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
						sourceType: ['album'] //从相册选择
					});
					if (typeof temps[1] == 'undefined') {
						return;
					}
					var tempFileList = temps[1].tempFiles
					var tempFiles = []
					for (var i = 0; i < tempFileList.length; i++) {
						var obj = {id: guid(), path: tempFileList[i].path, name: tempFileList[i].name, size: tempFileList[i].size}
						tempFiles.push(obj)
					}
					console.log(tempFiles);
				} else {
					// #ifdef MP-WEIXIN
					var res = await uni.showActionSheet({
						itemList: ['选择图片', '选择文件']
					})
					if (res[1].tapIndex == 0) {
						var temps = await uni.chooseImage({
							count: this.limit == null || this.limit - this.list.length > 9 ? 9 : 9 - limit,
							sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
							sourceType: ['album'] //从相册选择
						});
						if (typeof temps[1] == 'undefined') {
							return;
						}
						var tempFiles = temps[1].tempFilePaths;
					} else {
						//微信从客户端选择文件
						var temps = await new Promise((resolve, reject) => {
							wx.chooseMessageFile({
								count: this.limit == null || this.limit - this.list.length > 9 ? 9 : 9 - limit,
								type: 'file',
								success(res) {
									// tempFilePath可以作为img标签的src属性显示图片
									const tempFilePaths = res.tempFiles;
									resolve(tempFilePaths);
								}
							})
						})
						
						var tempFiles = temps.map((ele, index) => {
							return ele.path;
						})
						
						var fileNames = temps.map((ele, index) => {
							return ele.name;
						})
					}
					// #endif
				}
		
				for (let i in tempFiles) {
					let id = tempFiles[i].id
					let path = tempFiles[i].path;
					var fileName = tempFiles[i].name;
					let index = this.list.length;
					// 开始上传，先暂存文件
					this.list.push({
						id: id,
						fileName: fileName,
						url: path,
						type: this.isImg(path) ? 'image' : 'file',
						index: index,
						uploadTask: uploadTask,
						process: 0
					});
					this.$forceUpdate();
					var uploadTask = await uni.uploadFile({
						url: this.uploadFileUrl,
						filePath: path,
						name: this.fileKeyName,
						header: this.header,
						formData: {
							id : id
						},
						success: res => {
							 let json=JSON.parse(res.data);
							 var  fileEntity =json.body.filesEntity;
							 this.fileList.push(fileEntity);
							 //var entityList= {"id":fileEntity.id,"fileName":fileEntity.fileName,"path":fileEntity.path};
							 /* this.fileList.push(entityList);
							 console.log("-----"+this.fileList); */
							// 上传完成后处理
							this.$emit('uploadSuccess', res,this.fileList);
							if (res.statusCode  == 200) {
								console.log('file', '上传成功');
								this.$set(this.list[index], 'process', 100);
								this.$emit('update:attachmentList', this.list);
								this.$forceUpdate();
							} else {
								
							}
						}
					});
					uploadTask.onProgressUpdate(res => {
						//此接口不显示真实进度， 所以需要特殊处理
						if (res.progress < 90) {
							this.$set(this.list[index], 'process', res.progress);
							this.$forceUpdate();
						}
					});
				}
			},
			//根据文件名，返回时是否是图片类型
			isImg(filePath) {
				let index = filePath.lastIndexOf('.');
				let ext = filePath.substr(index + 1);
				var temp = ['docx', 'doc', 'doc', 'pdf', 'txt', 'ppt', 'pptx', 'zip','rar'].indexOf(ext.toLowerCase()) !== -1;
				return temp;
			}
		},
		mounted() {
			this.fileList = this.fileArr
		}
	};
</script>

<style lang="less" scoped>
	.imgs {
		display: flex;
		flex-wrap: wrap;
		width: calc(100% + 15rpx);
		& .file {
			min-width: calc(100% - 15rpx);
			border: 1px solid #ccc;
			// border-radius: 10upx;
			box-sizing: border-box;
			margin-top: 20upx;
			position: relative;
			& .noImg {
				padding: 20rpx;
				display: flex;
				justify-content: center;
				text-align: left;
				width: 100%;
				font-size: 26upx;
				// flex-wrap: wrap;
				color: #999;
				word-break: break-all;
				box-sizing: border-box;
			}
		}
		progress {
			margin-top: -6rpx;
			border-radius: 20rpx;
		}
		.del {
			position: absolute;
			width: 35rpx;
			height: 35rpx;
			background: #f56c6c;
			color: #fff;
			top: 0;
			text-align: center;
			right: 0;
			line-height: 35rpx;
			font-size: 30rpx;
			z-index: 100;
		}
		.btn {
			width: 100%;
			margin: 20upx 0;
			color: #007AFF;
			background-color: #FFFFFF;
			border: #007AFF 2upx solid;
		}
	}
</style>